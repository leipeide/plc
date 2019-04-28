package com.waho.socket.state.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.waho.dao.AlarmDao;
import com.waho.dao.DeviceConnectRecordDao;
import com.waho.dao.NodeDao;
import com.waho.dao.RecordDao;
import com.waho.dao.UserMessageDao;
import com.waho.dao.impl.AlarmDaoImpl;
import com.waho.dao.impl.DeviceConnectRecordDaoImpl;
import com.waho.dao.impl.NodeDaoImpl;
import com.waho.dao.impl.RecordDaoImpl;
import com.waho.dao.impl.UserMessageDaoImpl;
import com.waho.domain.Alarm;
import com.waho.domain.Device;
import com.waho.domain.DeviceConnectRecord;
import com.waho.domain.Node;
import com.waho.domain.Record;
import com.waho.domain.SocketCommand;
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
		// 1、取出最后一条未执行的用户操作指令
		UserMessageDao umDao = new UserMessageDaoImpl();
		try {
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
					pollCount = 0;
				}
				slowCount = 0;
			} else if (++slowCount >= SlowTimes) { // 未有指令执行，进行轮询操作（）
				slowCount = 0;// 延时操作，函数三次调用只执行一次
				nodeList = nodeDao.selectNodesByDeviceid(device.getId());
				if (nodeList != null) {
					if (pollSizeMemory != nodeList.size()) {
						pollCount = 0;
						pollSizeMemory = nodeList.size();
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
										Alarm alarm = alarmDao.selectByDeviceMacAndNodeAddrAndType(node.getDeviceMac(), node.getNodeAddr(), Alarm.ALARM_DISCONNECT);
										if (alarm == null) {
											alarm = new Alarm(new Date(), node.getDeviceMac(), node.getNodeAddr(),
													Alarm.ALARM_DISCONNECT,um.getUserid());
											alarmDao.insert(alarm);
										}
									} else {
										// 判断是否有相应的超时报警信息，如果有，删除
										Alarm alarm = alarmDao.selectByDeviceMacAndNodeAddrAndType(node.getDeviceMac(), node.getNodeAddr(), Alarm.ALARM_DISCONNECT);
										if (alarm != null) {
											alarmDao.deleteById(alarm.getId());
										}
										// 2.2 未超过12个小时，判断是否过功率，如果过功率，生成报警信息，type = ALARM_OVER_POWER，存入数据库。
										// 删除，放到CmdWriteNodeStateHandler 和 CmdReadNodeStateHandler进行过功率判断。
//										if (record.getLight1Power() > Alarm.LIGHT1_OVERLOAD_VOLTAGE
//												|| record.getLight2Power() > Alarm.LIGHT2_OVERLOAD_VOLTAGE
//												|| record.getPower() > Alarm.TOTAL_OVERLOAD_VOLTAGE) {
//											alarm = new Alarm(new Date(), node.getDeviceMac(), node.getNodeAddr(),Alarm.ALARM_OVERLOAD,um.getUserid()); //Alarm.ALARM_OVERLOAD
//											alarmDao.insert(alarm);
//										
//										}
									}
								} else {
									// 未查询到节点功率记录,如果集控器链接已超过12个小时，报警
									if (date.getTime() - dcr.getDate().getTime() > AlarmTime) {
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

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
