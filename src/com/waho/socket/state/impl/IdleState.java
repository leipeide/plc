package com.waho.socket.state.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.waho.dao.AlarmDao;
import com.waho.dao.DeviceConnectRecordDao;
import com.waho.dao.NodeDao;
import com.waho.dao.RecordDao;
import com.waho.dao.TimingDao;
import com.waho.dao.TimingPlanDao;
import com.waho.dao.UserDao;
import com.waho.dao.UserMessageDao;
import com.waho.dao.impl.AlarmDaoImpl;
import com.waho.dao.impl.DeviceConnectRecordDaoImpl;
import com.waho.dao.impl.NodeDaoImpl;
import com.waho.dao.impl.RecordDaoImpl;
import com.waho.dao.impl.TimingDaoImpl;
import com.waho.dao.impl.TimingPlanDaoImpl;
import com.waho.dao.impl.UserDaoImpl;
import com.waho.dao.impl.UserMessageDaoImpl;
import com.waho.domain.Alarm;
import com.waho.domain.Device;
import com.waho.domain.DeviceConnectRecord;
import com.waho.domain.Node;
import com.waho.domain.Record;
import com.waho.domain.SocketCommand;
import com.waho.domain.Timing;
import com.waho.domain.TimingPlan;
import com.waho.domain.UserMessage;
import com.waho.socket.state.SocketState;
import com.waho.socket.util.SocketDataHandler;

/**
 * 空闲状态，此时设备已注册完成，信息同步也已完成，可以进行正常的控制操作。 该状态查询数据库中所有类型的用户指令，转发给集控器。
 * 同时根据集控器上报的数据，更新数据库。
 * 
 * @author mingxin
 *
 */
public class IdleState implements SocketState {
	//每5次间隔轮询一次
	private static Integer SlowTimes = 5;
	//超时报警判断的基准时间
	private static long AlarmTime = 1000 * 60 * 60 * 12;
	// 一小时（线程一秒执行一次，3秒调用一次轮询逻辑，乘以1200 == 1h）
	private static int PollingCycle = 1200;
	//日志输出调试
	private Logger logger = Logger.getLogger(this.getClass());
	//节点集合
	private List<Node> nodeList;
	//轮询时间计时
	private Integer pollCount = 0;
	//轮询查询过的节点个数
	private Integer pollSizeMemory = 0;

	private Integer slowCount = 0;

	private NodeDao nodeDao = new NodeDaoImpl();

	private RecordDao recordDao = new RecordDaoImpl();

	private AlarmDao alarmDao = new AlarmDaoImpl();

	private DeviceConnectRecordDao dcrDao = new DeviceConnectRecordDaoImpl();

	public static IdleState getInstance() {
		return new IdleState();
	}

	private IdleState() {
		super();
	}

	@Override
	public SocketState clientDataHandle(byte[] data, int length, SocketDataHandler handler, Device device,
			OutputStream out) {
		// 接受非登录操作的集控器数据，并进行相应的数据处理。
		SocketCommand sc = handler.socketDataHandle(data, length, device);
		// 如需要回复，进行回复
		if (sc != null) {
			try {
				out.write(sc.tobyteArray());
				logger.info("service to " + device.getDeviceMac() + ": "
						+ SocketCommand.parseBytesToHexString(sc.tobyteArray(), sc.tobyteArray().length));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public void userMsgHanle(Device device, OutputStream out) {
		UserMessageDao umDao = new UserMessageDaoImpl();
		try {
			//1.执行定时广播
			TimingPlanDao tpd = new TimingPlanDaoImpl();
			TimingDao td = new TimingDaoImpl();
			List<TimingPlan> timingPlan = tpd.selectTimingPlanByDeviceMac(device.getDeviceMac());
			for(TimingPlan obj:timingPlan) {
				int startMonth = obj.getStartDate();
				int endMonth = obj.getEndDate();
				if(obj.getDoState()) {//计划状态打开
					List<Timing> timing = td.selectTimingByTimingPlanName(obj.getName());
					for(Timing timingObj:timing) {
						Time t = timingObj.getTime();
						boolean flag = timingObj.isFlag();
						//1.得出执行任务的时间,此处为今天的定时时间
						Calendar calendar = Calendar.getInstance();
						calendar.set(Calendar.HOUR_OF_DAY, t.getHours()); // 控制时
						calendar.set(Calendar.MINUTE,t.getMinutes());    // 控制分
						calendar.set(Calendar.SECOND,t.getSeconds());    // 控制秒
						Date time = calendar.getTime();//获取日历时间
						Date nowDate = new Date();//获取本地时间
						int month = nowDate.getMonth()+1;//获取当前时间的月份部分(月份范围0-11)
						
						//2.每天清除标志位，设置为未执行状态flase
						Calendar calendar1 = Calendar.getInstance();
						calendar1.set(Calendar.HOUR_OF_DAY, 8); // 控制时
						calendar1.set(Calendar.MINUTE,0);    // 控制分
						calendar1.set(Calendar.SECOND,0);    // 控制秒
						Date Calendar1time = calendar1.getTime();//获取日历时间
						if((nowDate.getTime() -  Calendar1time.getTime()) >= 0 
								&& (nowDate.getTime() -  Calendar1time.getTime() <= 1000*60)) {
							timingObj.setFlag(false);
							td.updateTimingFlagByTimingId(timingObj);
						}
						
						//3.执行未执行的定时广播
						if(flag == false) {
							if(month >= startMonth && month <= endMonth) {
								if((nowDate.getTime() - time.getTime()) >= 0 && (nowDate.getTime() - time.getTime() <= 1000*60)) {
									//1.将用户指令写入用户信息数据库
									UserMessage um = new UserMessage();
									um.setUserid(obj.getUserid());
									um.setDeviceMac(timingObj.getDeviceMac());
									um.setExecuted(false);
									um.setCommand(UserMessage.CMD_BROADCAST_WRITE_STATE);
									// 2、将指令信息封装成指令对象
									boolean light1State = timingObj.isLight1State();
									boolean light2State = timingObj.isLight2State();
									String light1PowerPercent = String.valueOf(timingObj.getLight1PowerPercent());
									String light2PowerPercent = String.valueOf(timingObj.getLight2PowerPercent());
									um.setData(SocketCommand.GenerateBroadcastTimingWriteStateCommandData(light1State, light2State, light1PowerPercent, light2PowerPercent));
									try {
										// 3、写入数据库
										int x = umDao.insertUserMessage(um);
										if(x == 1) {
											timingObj.setFlag(true);
											td.updateTimingFlagByTimingId(timingObj);
										}
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}
						}
					}
				}
			}
		
			// 1、取出最后一条未执行的用户操作指令
			UserMessage um = umDao.selectUserLastUserMessageByDevice(device);
			// 2、封包发送
			if (um != null && !um.isExecuted()) {
				out.write(um.tobyteArray());
				logger.info("service to " + device.getDeviceMac() + ": "
						+ SocketCommand.parseBytesToHexString(um.tobyteArray(), um.tobyteArray().length));
				// 3、将指令状态置为已经执行，写回到数据库
				um.setExecuted(true);
				umDao.updateUserMessage(um);
				
				// 4、如果是广播控制指令，立马进行一次轮询，即轮询时间计时置为0
				if (um.getCommand() == UserMessage.CMD_BROADCAST_WRITE_STATE) {
					pollCount = 0;  //轮询时间
				}
				slowCount = 0;
			} else if (++slowCount >= SlowTimes) { // 未有指令执行，进行轮询操作（） showTimes:每5次间隔轮询一次
				slowCount = 0;// 延时操作，函数三次调用只执行一次
				nodeList = nodeDao.selectNodesByDeviceid(device.getId());
				if (nodeList != null) {
					if (pollSizeMemory != nodeList.size()) {
						pollCount = 0; //
						pollSizeMemory = nodeList.size(); //
					}
					if (pollCount < pollSizeMemory) { 
						if (pollCount >= 0) {
							Node node = nodeList.get(pollCount);
							if (node != null) {
								if (device != null) {
									um = new UserMessage();
									um.setCommand(SocketCommand.CMD_READ_NODE_STATE);
									um.setDeviceMac(device.getDeviceMac());
									um.setUserid(device.getUserid());
									um.setExecuted(false);
									um.setData(SocketCommand.GenerateReadNodeStateCommandData(node.getNodeAddr()));
									out.write(um.tobyteArray());
									logger.info("service to " + device.getDeviceMac() + ": " + SocketCommand
											.parseBytesToHexString(um.tobyteArray(), um.tobyteArray().length));
								}
							}
						}
						pollCount++;
					} else {
						pollCount = -PollingCycle;
						// 报警逻辑判断
						// 1. 遍历，根据节点列表，查询最后一条功率时间记录。
						Record record = null;
						// 获取设备在线时长
						DeviceConnectRecord dcr = dcrDao.selectLastRecordByDeviceMac(device.getDeviceMac());
						if (dcr != null && dcr.isConnection() == true) {
							for (Node node : nodeList) {
								record = recordDao.selectLastRecordByNodeAddrAndDeviceMac(node.getNodeAddr(),
										node.getDeviceMac());
								Date date = new Date();

								if (record != null) {
									// 2. 判断回复时间是否已经超过12个小时。
									if (date.getTime() - record.getDate().getTime() > AlarmTime
											&& date.getTime() - dcr.getDate().getTime() > AlarmTime) {
										// 2.1 超过12个小时,且集控器在线时长已经超过12个小时
										// 如果数据库中没有相应的报警信息，生成报警信息，type = ALARM_DISCONNECT，存入数据库。
										List<Alarm> list = alarmDao.selectByDeviceMacAndType(node.getDeviceMac(), Alarm.ALARM_DISCONNECT);
										if (list.size() == 0) {
											Alarm alarm = new Alarm(new Date(), node.getDeviceMac(), node.getNodeAddr(),
													Alarm.ALARM_DISCONNECT,um.getUserid());
											alarmDao.insert(alarm);
										}else {
											// 判断是否有相应的超时报警信息，如果有，删除
											if (list.size() > 0) {
												for(int i = 0; i < list.size(); i++) {
													Alarm alarm = list.get(i);
													alarmDao.deleteById(alarm.getId());
												}
												Alarm alarm = new Alarm(new Date(), node.getDeviceMac(), node.getNodeAddr(),
														Alarm.ALARM_DISCONNECT,um.getUserid());
												alarmDao.insert(alarm);
											}
										}
									}      //if (record != null)
								} else {
									// 未查询到节点功率记录,如果集控器链接已超过12个小时，报警
									if (date.getTime() - dcr.getDate().getTime() > AlarmTime) {
										List<Alarm> list = alarmDao.selectByDeviceMacAndType(node.getDeviceMac(), Alarm.ALARM_DISCONNECT);
										if (list.size() == 0) {
											Alarm alarm = new Alarm(new Date(), node.getDeviceMac(), node.getNodeAddr(),
													Alarm.ALARM_DISCONNECT,um.getUserid());
											alarmDao.insert(alarm);
										}else {
											// 判断是否有相应的超时报警信息，如果有，删除
											if (list.size() > 0) {
												for(int i = 0; i < list.size(); i++) {
													Alarm alarm = list.get(i);
													alarmDao.deleteById(alarm.getId());
												}
												Alarm alarm = new Alarm(new Date(), node.getDeviceMac(), node.getNodeAddr(),
														Alarm.ALARM_DISCONNECT,um.getUserid());
												alarmDao.insert(alarm);
											}
										}
									}	
								}
								
							}
						}
					}
				}
			}
			
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

