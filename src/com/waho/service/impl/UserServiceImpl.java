package com.waho.service.impl;

import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.alibaba.fastjson.JSON;
import com.ndktools.javamd5.Mademd5;
import com.ndktools.javamd5.core.MD5;
import com.waho.dao.AlarmDao;
import com.waho.dao.DeviceDao;
import com.waho.dao.NodeDao;
import com.waho.dao.RecordDao;
import com.waho.dao.TimingDao;
import com.waho.dao.TimingPlanDao;
import com.waho.dao.UserDao;
import com.waho.dao.UserMessageDao;
import com.waho.dao.impl.AlarmDaoImpl;
import com.waho.dao.impl.DeviceDaoImpl;
import com.waho.dao.impl.NodeDaoImpl;
import com.waho.dao.impl.RecordDaoImpl;
import com.waho.dao.impl.TimingDaoImpl;
import com.waho.dao.impl.TimingPlanDaoImpl;
import com.waho.dao.impl.UserDaoImpl;
import com.waho.dao.impl.UserMessageDaoImpl;
import com.waho.domain.Alarm;
import com.waho.domain.Device;
import com.waho.domain.Node;
import com.waho.domain.PageBean;
import com.waho.domain.Record;
import com.waho.domain.SocketCommand;
import com.waho.domain.Timing;
import com.waho.domain.TimingPlan;
import com.waho.domain.User;
import com.waho.domain.UserMessage;
import com.waho.service.UserService;
import com.waho.domain.SendJMail;

public class UserServiceImpl implements UserService {

	@Override
	public Map<String, Object> login(String username, String password) {
		UserDao userDao = new UserDaoImpl();
		Map<String, Object> resultMap = null;
		User user;
		try {
			user = userDao.selectUserByUsernameAndPassword(username, password);
			if (user != null) {
				resultMap = new HashMap<String, Object>();        
				resultMap.put("user", user);
				// 查询用户相关的设备数据
				DeviceDao deviceDao = new DeviceDaoImpl();
				List<Device> devices = deviceDao.selectDeviceByUserid(user.getId());
				resultMap.put("devices", devices);
			
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultMap;
	}

	@Override
	public List<Node> getNodesByDeviceid(int deviceid) {
		NodeDao nodeDao = new NodeDaoImpl();
		List<Node> list = null;
		try {
			list = nodeDao.selectNodesByDeviceid(deviceid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
    
	@Override
	public PageBean getNodesPageByDeviceid(int deviceid,int currentPage,int pageSize) {
		NodeDao nodeDao = new NodeDaoImpl();
		List<Node> list = null;
		PageBean pb = new PageBean();
		try {
			list = nodeDao.selectNodesByDeviceid(deviceid);  
			
			//可用节点靠前排列
			for(int i = 0; i < list.size()-1; i++) {
				for(int j = 0; j < list.size()-1-i; j++ ) {
					if(list.get(j).isLight1State() || list.get(j).isLight2State()) {
						//Collections.swap(list, j, j);
					}else {
						Collections.swap(list, j, j+1);
					}
				}
			}
			int count = list.size();
			//把5个变量封装到PageBean中，做为返回值
			pb.setCount(count);
			pb.setCurrentPage(currentPage);
			pb.setPageSize(pageSize);
			pb.setTotalPage((int)Math.ceil(count*1.0/pb.getPageSize()));
			pb.setStar((pb.getCurrentPage() - 1) * pb.getPageSize());
			//将节点分页显示
			if(pb.getTotalPage()!=0) {
				pb.setNodes(list.subList(pb.getStar(), 
						count-pb.getStar()>pb.getPageSize()?pb.getStar()+pb.getPageSize():count));
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pb;
	}
	
	
	@Override
	public PageBean refreshNodesPageByDeviceid(int deviceid, int currentPage, int pageSize) {
		NodeDao nodeDao = new NodeDaoImpl();
		List<Node> list = null;
		PageBean pb = new PageBean();
		try {
			list = nodeDao.selectNodesByDeviceid(deviceid); 
			
			//可用节点靠前排列
			for(int i = 0; i < list.size()-1; i++) {
				for(int j = 0; j < list.size()-1-i; j++ ) {
					if(list.get(j).isLight1State() || list.get(j).isLight2State()) {
					}else {
						Collections.swap(list, j, j+1);
					}
				}
			}
			
			
			int count = list.size();
			//把5个变量封装到PageBean中，做为返回值
			pb.setCount(count);
			pb.setCurrentPage(currentPage);
			pb.setPageSize(pageSize);
			pb.setTotalPage((int)Math.ceil(count*1.0/pb.getPageSize()));
			pb.setStar((pb.getCurrentPage() - 1) * pb.getPageSize());
			//将节点分页显示
			if(pb.getTotalPage()!=0) {
				pb.setNodes(list.subList(pb.getStar(), 
						count-pb.getStar()>pb.getPageSize()?pb.getStar()+pb.getPageSize():count));
			}		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pb;
	}

	@Override
	public void userWriteNodeCmd(int nodeid, String light1State, String light2State, String light1PowerPercent,
			String light2PowerPercent) {
		NodeDao nodeDao = new NodeDaoImpl();
		DeviceDao deviceDao = new DeviceDaoImpl();
		UserMessageDao userMDao = new UserMessageDaoImpl();
		try {
			Node node = nodeDao.selectNodeById(nodeid);
			Device device = deviceDao.selectDeviceById(node.getDeviceid());
			//写入用户指令信息
			UserMessage um = new UserMessage();
			um.setUserid(device.getUserid());
			um.setDeviceMac(device.getDeviceMac());
			um.setExecuted(false);
			um.setCommand(UserMessage.CMD_WRITE_NODE_STATE);
			// 1、将指令信息封装成指令对象
			um.setData(SocketCommand.GenerateWriteNodeStateCommandData(node.getNodeAddr(), light1State, light2State,
					light1PowerPercent, light2PowerPercent));
//			if (um.getData() != null) {
//				um.setLen((byte) um.getData().length + SocketCommand.LENGTH_WITHOUT_DATA);
//			} else {
//				um.setLen(SocketCommand.LENGTH_WITHOUT_DATA);
//			}
			// 2、写入数据库
			userMDao.insertUserMessage(um);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void userWriteBroadcastCmd(int deviceid, String light1State, String light2State, String light1PowerPercent,
			String light2PowerPercent) {
		DeviceDao deviceDao = new DeviceDaoImpl();
		UserMessageDao userMDao = new UserMessageDaoImpl();
		try {
			Device device = deviceDao.selectDeviceById(deviceid);
			//将用户指令写入用户信息数据库
			UserMessage um = new UserMessage();
			um.setUserid(device.getUserid());
			um.setDeviceMac(device.getDeviceMac());
			um.setExecuted(false);
			um.setCommand(UserMessage.CMD_BROADCAST_WRITE_STATE);
			// 1、将指令信息封装成指令对象
			um.setData(SocketCommand.GenerateBroadcastWriteStateCommandData(light1State, light2State, light1PowerPercent,
					light2PowerPercent));
//			if (um.getData() != null) {
//				um.setLen((byte) um.getData().length + SocketCommand.LENGTH_WITHOUT_DATA);
//			} else {
//				um.setLen(SocketCommand.LENGTH_WITHOUT_DATA);
//			}
			// 2、写入数据库
			userMDao.insertUserMessage(um);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void refreshNodeDataById(int nodeid) {
		// 查询节点信息，获取集控器mac地址，拼接查询指令，写入数据库
		NodeDao nodeDao = new NodeDaoImpl();
		try {
			Node node = nodeDao.selectNodeById(nodeid);
			if (node != null) {
				DeviceDao deviceDao = new DeviceDaoImpl();
				Device device = deviceDao.selectDeviceById(node.getDeviceid());
				if (device != null) {
					UserMessage um = new UserMessage();
					um.setCommand(SocketCommand.CMD_READ_NODE_STATE);
					um.setDeviceMac(device.getDeviceMac());
					um.setUserid(device.getUserid());
					um.setExecuted(false);
					um.setData(SocketCommand.GenerateReadNodeStateCommandData(node.getNodeAddr()));
//					if (um.getData() != null) {
//						um.setLen((byte) um.getData().length + SocketCommand.LENGTH_WITHOUT_DATA);
//					} else {
//						um.setLen(SocketCommand.LENGTH_WITHOUT_DATA);
//					}
					UserMessageDao umDao = new UserMessageDaoImpl();
					umDao.insertUserMessage(um);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
		

	@Override
	public void userWriteNodesRegisterOpenCmd(int deviceid, Date startTime, int keepMinutes) {
		DeviceDao deviceDao = new DeviceDaoImpl();
		UserMessageDao userMDao = new UserMessageDaoImpl();
		try {
			Device device = deviceDao.selectDeviceById(deviceid);
			UserMessage um = new UserMessage();
			um.setUserid(device.getUserid());
			um.setDeviceMac(device.getDeviceMac());
			um.setExecuted(false);
			um.setCommand(UserMessage.CMD_NODE_REG_OPEN);
			// 1、将指令信息封装成指令对象
			um.setData(SocketCommand.GenerateNodesRegisterOpenCommandData(startTime, keepMinutes));
//			if (um.getData() != null) {
//				um.setLen((byte) um.getData().length + SocketCommand.LENGTH_WITHOUT_DATA);
//			} else {
//				um.setLen(SocketCommand.LENGTH_WITHOUT_DATA);
//			}
//			um.setInfoDomain(SocketCommand.parseBytesToHexString(Protocol3762Handler.getInfoDomain(um.getData()), 6));
			// 2、写入数据库
			userMDao.insertUserMessage(um);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean addDeviceToUserByDeviceMac(String deviceMac, int userid) {
		DeviceDao deviceDao = new DeviceDaoImpl();
		Device device = null;
		boolean result = false;
		try {
			device = deviceDao.selectDeviceByDeviceMac(deviceMac);    
			device.setUserid(userid);   //设置一个新的userid
			if(device != null) {
			   if( deviceDao.updateDeviceuseridByDeviceMac(device) != 0 ) {
				   result = true;
				}
			}
		} catch (Exception e) {
		}
		return result;
	}

	@Override
	public boolean removeDeviceByDeviceName(String[] arrdeviceName,int userid) {
		boolean result = false;
		DeviceDao deviceDao = new DeviceDaoImpl();
		int sum = 0;
		for(int i = 0; i < arrdeviceName.length; i++) {
			String deviceName = arrdeviceName[i];
			try {
				Device device = deviceDao.selectDeviceByDeviceName(deviceName);
				if(device != null) {  //查询到集控器的信息后，将集控器从当前用户删除
					if( deviceDao.updateDeviceuseridByDeviceName(device) != 0 ) {
						sum = sum + 1;
						device.setDeviceName(device.getDeviceMac());
						deviceDao.updateDeviceNameByDeviceId(device);
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(sum == arrdeviceName.length ) {
			result = true;
		}
		return result;
	}

	@Override
	public List<Device> getDevicesByUserid(int userid) {
		DeviceDao deviceDao = new DeviceDaoImpl();
		List<Device> list = null;
		try {
			list = deviceDao.selectDeviceByUserid(userid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean checkUserByUsername(String username) {
		UserDao userDao = new UserDaoImpl();
		User user;
		boolean result4 =false;
		try {
			user = userDao.selectUserByUsername(username);
			if(user != null) {
				result4 =true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result4;
	}

	@Override
	public boolean registUser(String username, String password, String email, String phone) {
		UserDao userDao = new UserDaoImpl();
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setEmail(email);
		user.setPhone(phone);
		user.setOperateNum(0);
		boolean result5 = false;
		try {
			if(userDao.insertUser(user) != 0) {
				result5 = true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result5;
	}

	@Override
	public void addNodesCmd(int deviceid,String[] nodeAddrString) {
			int num = nodeAddrString.length;
			DeviceDao deviceDao = new DeviceDaoImpl();
			UserMessage um = new UserMessage();
		    Device device;
			try {
				device = deviceDao.selectDeviceById(deviceid);
				//1.添加指令信息入数据库
				um.setUserid(device.getUserid());
				um.setDeviceMac(device.getDeviceMac());
				um.setCommand(UserMessage.CMD_ADD_NODE);
				um.setExecuted(false);
				//2.添加节点的节点信息封装到数据库
				um.setData(SocketCommand.GenerateAddNodesCommandDate(num,nodeAddrString));
				UserMessageDao umDao = new UserMessageDaoImpl();
				umDao.insertUserMessage(um);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
	 }

	@Override //先从数据库删除，等到集控器回复删除指令后不做任任何处理，若删除节点失败，集控器再次上电时，被删除节点重新插入到数据库
	public int delNodesCmd(int deviceid, String[] nodeAddr) {
		DeviceDao deviceDao = new DeviceDaoImpl();
		NodeDao nodeDao = new NodeDaoImpl();
		UserMessage um = new UserMessage();
		Node  node = new Node();
		int sum = 0;
		int num = nodeAddr.length;
	    try {
	        Device device = deviceDao.selectDeviceById(deviceid);
	      //封装指令信息进入数据库
	        um.setUserid(device.getUserid());
			um.setDeviceMac(device.getDeviceMac());
			um.setCommand(UserMessage.CMD_DELETE_NODE);
			um.setExecuted(false);
			um.setData(SocketCommand.GenerateDelNodesCommandDate(num,nodeAddr));
			UserMessageDao umDao = new UserMessageDaoImpl();
			umDao.insertUserMessage(um);
	    	//删除节点
	    	for(int i=0; i<nodeAddr.length; i++) {
	    		String nodeAddr1 = nodeAddr[i];
	    		node.setNodeAddr(nodeAddr1);
	    		node = nodeDao.selectNodeByNodeAddr(nodeAddr1);
	    		int x = nodeDao.deletNodesByNodeAddr(node);			
	    		sum = sum + x;
	    	}
	    	// 更新数据库
	    	device.setCurrentNodes(device.getCurrentNodes()-sum);
	    	deviceDao.updateDeviceCurrentNodes(device);	
	    } catch (Exception e) {
	    	// TODO Auto-generated catch block
	    	e.printStackTrace();
	    }
	    return sum;   
	}

	@Override
	public User getUserMessage(int userid) {
		User user = new User();
		UserDao ud = new UserDaoImpl();
		try {
			user = ud.selectUserByUserid(userid);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public boolean nodeRename(String nodeName,int nodeid) {
		Node node = null;
		NodeDao nodeDao = new NodeDaoImpl();
		boolean result = false;
		try {
			node = nodeDao.selectNodeById(nodeid);
			node.setId(nodeid);
			node.setNodeName(nodeName);
			if( node != null) {
				if(nodeDao.updateNodeNameByNodeId(node) != 0) {
					result = true;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean deviceRename(int deviceid, String deviceRename) {
		DeviceDao deviceDao = new DeviceDaoImpl();
		Device device = null;
		boolean result = false;
		try {
			device  = deviceDao.selectDeviceById(deviceid);
			device.setDeviceName(deviceRename);
			if(device != null) {
				if(deviceDao.updateDeviceNameByDeviceId(device) != 0) {
					result = true;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean updateUserPassword(int userid, String newPassword) {
		UserDao userDao = new UserDaoImpl();
		boolean result = false;
		try {
			User user = userDao.selectUserByUserid(userid);
			user.setPassword(newPassword);
			int num = userDao.updateUserPasswordByPassword(user);
			if(num != 0) {
				result = true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Node serachNode(int deviceid,String nodeAddr) {
		NodeDao nodeDao = new NodeDaoImpl();
		List<Node> list = null;
		Node node = null;
		try {
			 list = nodeDao.selectNodesByDeviceid(deviceid);
			 for(Node result:list) {
				 if(nodeAddr.equals(result.getNodeAddr())) {
					 node = result;
					 break;
				 }
			 }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return node;
	}

	@Override
	public Map<String, Object> DateRangeNodeChartMessage(String nodeAddr,String date) {
    	NodeDao nDao = new NodeDaoImpl();
    	RecordDao rDao = new RecordDaoImpl();
    	Map<String, Object> recordMap = null;
    	String startDateStr = date.substring(0, 11);
    	String endDateStr = date.substring(14, 25);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy年MM月dd日");
    	try {
    		Date startDate = simpleDateFormat.parse(startDateStr);
			Date endDate = simpleDateFormat.parse(endDateStr);
    		//1.获取集控器Mac地址
    		Node node = nDao.selectNodeByNodeAddr(nodeAddr);
    		String deviceMac = node.getDeviceMac();
    		//2.根据时间范围查询该时间段内所有节点的记录
    		ArrayList<Record> powerRecord = new ArrayList<Record>();
			List<Record> record = rDao.selectTimeRangeNodePowerMessage(startDate,endDate);
			//3.从所有记录中判断得到当前节点的所有记录
			if(record != null) {
				for(int i = 0; i < record.size(); i++) {
					recordMap = new HashMap<String, Object>();
					Record newRecord = record.get(i);
					if(deviceMac.equals(newRecord.getDeviceMac()) && 
							nodeAddr.equals(newRecord.getNodeAddr())) {
						powerRecord.add(newRecord);
					}
					recordMap.put("powerRecord", powerRecord);
				}
			}
			//获取powerRecord中节点相邻状态相反的时间添加进timelist集合中
			ArrayList<Date> timelist = new ArrayList<Date>();
		    for(int num = 0; num < powerRecord.size(); num++) {
		    	boolean record0State1 = powerRecord.get(num).isLight1State();
			    boolean record0State2 = powerRecord.get(num).isLight2State();	 
		    	if(record0State1 || record0State2) { 
					timelist.add(powerRecord.get(num).getDate());  //开灯时间添加到timelist
					int x = num+1; 
					boolean result = false;
					for (int j = x; j < powerRecord.size(); j++) {
						boolean recordState1 = powerRecord.get(j).isLight1State();
						boolean recordState2 = powerRecord.get(j).isLight2State();
						if((recordState1 || recordState2) == result) {
							Date time = powerRecord.get(j).getDate(); 
							timelist.add(time); //距离开灯时间最近的一次关灯时间添加到timelist
							result = !result;
							}
						}
		    		}
		    	// 当这段时间内的最后一次操作是开灯时，将开灯状态操作的时间从集合内移除。
		    	if(timelist.size()%2 == 1) {
		    		timelist.remove(timelist.size()-1);
		    	}
		    	recordMap.put("timeRecord", timelist);
		    	//重要，内循环已得到所有的开关时间记录，无需再执行外循环了，直接break
		    	break; 
		      }		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return recordMap;
	}

	@Override
	public Map<String, Object> getWarnningMessageById(int userid) {
		Map<String, Object> alarmMap = null;
		AlarmDao aDao = new AlarmDaoImpl();
		DeviceDao dDao = new DeviceDaoImpl();
 		//List<Alarm> alarm;
 		List<Alarm> alarmRecord = null;
 		List<String>deviceMacList = null;
 		List<String>nodeAddrList = null;
 		int total = 0;
			try {
				List<Device> list = dDao.selectDeviceByUserid(userid);
				alarmMap = new HashMap<String, Object>();
				alarmRecord = new ArrayList<>();
				deviceMacList = new ArrayList<>();
				nodeAddrList = new ArrayList<>();
			   /* 此方案存在bug,当集控器在添加到其他用户时，
			    * 集控器在当前账户未发生的报警也将显示在当前账户，故选择用userid查找
			    * //1.根据集控器查询该用户下的所有报警信息，并放在一个集合中
				for(Device device:list) {
					alarm = aDao.selectAlarmRecordByDeviceMac(device.getDeviceMac());
					int num = alarm.size();
					total = total + num;
					alarmRecord.addAll(alarm);
					deviceMacList.add(device.getDeviceMac());
				}
				*/
				//1.根据用户id查询该用户下的所有报警信息，并放在一个集合中
				alarmRecord = aDao.selectAlarmRecordByUserid(userid);
				//2.冒泡法进行日期排序：较近的日期靠前
				for(int i = 0;i < alarmRecord.size()-1;i++) {//外层循环控制排序趟数
					for(int j = 0; j < alarmRecord.size()-1-i; j++ ) {//内层循环控制每一趟排序多少次
						Date date1 = alarmRecord.get(j).getDate();
						Date date2 = alarmRecord.get(j+1).getDate();
						if(date1.before(date2)) {
							 Collections.swap(alarmRecord, j, j + 1);//集合中j和j+1位交换位置
						}
					}
				}
			
				//3.获取记录中节点的种类、集控器地址种类
				for(Device device:list) {
					deviceMacList.add(device.getDeviceMac());
				}
				for(int i = 0;i < alarmRecord.size();i++) {
					String nodeAddr = alarmRecord.get(i).getNodeAddr();
					if(nodeAddrList.contains(nodeAddr)) {
						break;
					}else {
						nodeAddrList.add(nodeAddr);
					}
				}
				alarmMap.put("deviceMacList",deviceMacList);
				alarmMap.put("nodeAddrList",nodeAddrList);
				alarmMap.put("alarmList", alarmRecord);
				alarmMap.put("warnningNum", alarmRecord.size()); 
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return alarmMap;
	}

	
	@Override
	public Map<String, Object> delWarnningMessage(String[] alarmId,int userid) {
		Map<String, Object> alarmMap = new HashMap<String, Object>();
		List<String>deviceMacList = new ArrayList<>();
 		List<String>nodeAddrList = new ArrayList<>();
		AlarmDao aDao = new AlarmDaoImpl();
		DeviceDao dDao = new DeviceDaoImpl();
		String id;
		int num = 0;
		try {
			
			for(int i = 0; i < alarmId.length; i++) {
			    id = alarmId[i];
				if(aDao.deleteById(Integer.parseInt(id)) == 1) {
					num = num + 1;
				}
			}
			//1.获取用户下所有报警记录
			List<Alarm> alarmRecord = aDao.selectAlarmRecordByUserid(userid);
			List<Device> device = dDao.selectDeviceByUserid(userid);
			int total = alarmRecord.size();
			for(Device obj:device) {
				deviceMacList.add(obj.getDeviceMac());
			}
			
			//2.冒泡法进行日期排序：较近的日期靠前
			for(int i = 0;i < alarmRecord.size()-1;i++) {//外层循环控制排序趟数
				for(int j = 0; j < alarmRecord.size()-1-i; j++ ) {//内层循环控制每一趟排序多少次
					Date date1 = alarmRecord.get(j).getDate();
					Date date2 = alarmRecord.get(j+1).getDate();
					if(date1.before(date2)) {
						 Collections.swap(alarmRecord, j, j + 1);//集合中j和j+1位交换位置
					}
				}
			}
			
			//3.获取记录中节点的种类、集控器地址种类
			for(int i = 0;i < alarmRecord.size();i++) {
				String nodeAddr = alarmRecord.get(i).getNodeAddr();
				if(nodeAddrList.contains(nodeAddr)) {
					break;
				}else {
					nodeAddrList.add(nodeAddr);
				}
			}
			alarmMap.put("alarmList", alarmRecord);
			alarmMap.put("warnningNum", total); 
			alarmMap.put("delNum",num); 
			alarmMap.put("deviceMacList",deviceMacList);
			alarmMap.put("nodeAddrList",nodeAddrList);
			 
			
		} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		return alarmMap;
	}

	@Override
	public Map<String, Object> getWarnningMessageAndNumById(int userid) {
		Map<String, Object> alarmMap = new HashMap<String, Object>();
		AlarmDao aDao = new AlarmDaoImpl();
 		List<Alarm> alarmRecord = new ArrayList<>();
			try {
				alarmRecord = aDao.selectAlarmRecordByUserid(userid);
				int total = alarmRecord.size();
				//1.冒泡法进行日期排序：较近的日期靠前
				for(int i = 0;i < alarmRecord.size()-1;i++) {//外层循环控制排序趟数
					for(int j = 0; j < alarmRecord.size()-1-i; j++ ) {//内层循环控制每一趟排序多少次
						Date date1 = alarmRecord.get(j).getDate();
						Date date2 = alarmRecord.get(j+1).getDate();
						if(date1.before(date2)) {
							 Collections.swap(alarmRecord, j, j + 1);//集合中j和j+1位交换位置
						}
					}
				}
				
				alarmMap.put("alarmList", alarmRecord);
				alarmMap.put("warnningNum", total); 
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return alarmMap;
	}

	@Override
	public PageBean returnNodesPageByReturnButton(String nodeAddr,int deviceId) {
		NodeDao nDao = new NodeDaoImpl();
		PageBean pb = new PageBean();
		try {
			List<Node> list = nDao.selectNodesByDeviceid(deviceId);
			//将可用节点靠前排列
			for(int i = 0; i < list.size()-1; i++) {
				for(int j = 0; j < list.size()-1-i; j++ ) {
					if(list.get(j).isLight1State() || list.get(j).isLight2State()) {
					}else {
						Collections.swap(list, j, j+1);
					}
				}
			}
			int pageSize = 15;
			int count = list.size();
			int currentPage = 1;
			/*获得currentPage的值，返回nodeAddr所在的页面
			 * for(int i = 0; i < list.size(); i++) {
				if(list.get(i).getNodeAddr() == nodeAddr) {
					int num = i;
				}
			}
			if(num != 0) {
				if((num % pageSize) == 0) {
					currentPage = num / pageSize; //整除时，currentPage为商
				}else {
					currentPage = (num / pageSize) + 1; //有余数时，currentPage为商+1
				}
			}else {
				currentPage = 1;
			}*/
			pb.setCount(count);
			pb.setCurrentPage(currentPage);
			pb.setPageSize(pageSize);
			pb.setTotalPage((int)Math.ceil(count*1.0/pb.getPageSize()));
			pb.setStar((pb.getCurrentPage() - 1) * pb.getPageSize());
			//将节点分页显示
			if(pb.getTotalPage()!=0) {
				pb.setNodes(list.subList(pb.getStar(), 
						count-pb.getStar()>pb.getPageSize()?pb.getStar()+pb.getPageSize():count));
			}	
 			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pb;
	}

	@Override
	public Map<String, Object> serachWarnningMessageByFactor(int userid, String deviceMac, String nodeAddr, String typeStr,
			String date) {
		DeviceDao dDao = new DeviceDaoImpl();
		AlarmDao aDao = new AlarmDaoImpl();
		Map<String, Object> alarmMap = new HashMap<String, Object>();
		List<Alarm> alarmRecord = new ArrayList<>();
		int type = 0;
		//1.判断查询条件，根据查询条件查询报警记录
			try {
				if(deviceMac != null && deviceMac != "请选择！") {
					//System.out.println("进入deviceMac");
					alarmRecord = aDao.selectAlarmRecordByDeviceMac(deviceMac);
					
				}if(nodeAddr != null && nodeAddr != "请选择！") {
					//System.out.println("进入nodeAddr");
					alarmRecord = aDao.selectAlarmRecordByNodeAddrAndUserid(nodeAddr,userid);
					
				}if(typeStr != null && typeStr != "请选择！") {
					//System.out.println("进入typeStr");
					switch(typeStr){
						case "节点过功率" :
							type = 1;
							break; 
						case "集控器连接失败" :
							type = 2;
							break; 
						default :
							break;
					}
					alarmRecord = aDao.selectAlarmRecordByTypeAndUserid(type,userid);
					
				}if(date != null && date != "请选择！") {
					alarmRecord = aDao.selectAlarmRecordByUserid(userid);
				}	
				//2.冒泡法进行日期排序：较近的日期靠前
				if(date == null || date.equals("正序")) {
					for (int i = 0; i < alarmRecord.size() - 1; i++) {// 外层循环控制排序趟数
						for (int j = 0; j < alarmRecord.size() - 1 - i; j++) {// 内层循环控制每一趟排序多少次
							Date date1 = alarmRecord.get(j).getDate();
							Date date2 = alarmRecord.get(j + 1).getDate();
							if (date1.before(date2)) {
								Collections.swap(alarmRecord, j, j + 1);
							}
						}
				}
				}else {
					for (int i = 0; i < alarmRecord.size() - 1; i++) {// 外层循环控制排序趟数
						for (int j = 0; j < alarmRecord.size() - 1 - i; j++) {// 内层循环控制每一趟排序多少次
							Date date1 = alarmRecord.get(j).getDate();
							Date date2 = alarmRecord.get(j + 1).getDate();
							if (date2.before(date1)) {
								Collections.swap(alarmRecord, j, j + 1);
							}
						}
					}
				}
				//3.获得nodeAddrList、deviceMacList
				List<Alarm> list = aDao.selectAlarmRecordByUserid(userid);
				List<String> nodeAddrList = new ArrayList<>();
				List<String> deviceMacList = new ArrayList<>();
				List<Device> device = dDao.selectDeviceByUserid(userid);
				for(Device obj:device) {
					deviceMacList.add(obj.getDeviceMac());
				}
				for(Alarm obj:list) {
					nodeAddr = obj.getNodeAddr();	
					deviceMac = obj.getDeviceMac();
					if(nodeAddrList.contains(nodeAddr)) {
					}else {
						nodeAddrList.add(nodeAddr);
					}
				}
				alarmMap.put("deviceMacList",deviceMacList);
				alarmMap.put("nodeAddrList",nodeAddrList);
				alarmMap.put("alarmList", alarmRecord);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return alarmMap;
		
	}

	@Override
	public PageBean searchNodeErrReturnNodePage(int deviceid, int currentPage, int pageSize) {
		NodeDao nodeDao = new NodeDaoImpl();
		List<Node> list = null;
		PageBean pb = new PageBean();
		try {
			list = nodeDao.selectNodesByDeviceid(deviceid); 
			//1.将可用节点靠前排列
			for(int i = 0; i < list.size()-1; i++) {
				for(int j = 0; j < list.size()-1-i; j++ ) {
					if(list.get(j).isLight1State() || list.get(j).isLight2State()) {
					}else {
						Collections.swap(list, j, j+1);
					}
				}
			}
			int count = list.size();
			//2.把5个变量封装到PageBean中，做为返回值
			pb.setCount(count);
			pb.setCurrentPage(currentPage);
			pb.setPageSize(pageSize);
			pb.setTotalPage((int)Math.ceil(count*1.0/pb.getPageSize()));
			pb.setStar((pb.getCurrentPage() - 1) * pb.getPageSize());
			//3.将节点分页显示
			if(pb.getTotalPage()!=0) {
				pb.setNodes(list.subList(pb.getStar(), 
						count-pb.getStar()>pb.getPageSize()?pb.getStar()+pb.getPageSize():count));
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pb;
	}

	@Override
	public List<TimingPlan> getTimingPlanResultByUserid(int userid) {
		TimingPlanDao timingPlanDao = new TimingPlanDaoImpl();
		List<TimingPlan> timingPlanList = new  ArrayList<>();
		//sTimingDao timingDao = new TimingDaoImpl();
		//Map<String,Object> result =  new HashMap<String, Object>();
		try {
			//List<Timing> timingList = new  ArrayList<>();
			//1.根据用户id查询所有已进行定时设置的集控器定时计划
		    timingPlanList = timingPlanDao.selectTimingPlanByUserid(userid);
			//2.timingPlanList存入result中
			//result.put("plan",timingPlanList);
			//3.遍历timingPlanList，查询计划内的单灯控制设置
		/*	for(TimingPlan obj:timingPlanList) {
				int planid = obj.getId();
				List<Timing> list = timingDao.selectTimingByTimingPlanId(planid);
				timingList.addAll(list);
			}
			result.put("timing",timingList);*/
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return timingPlanList;
	}

	@Override
	public Map<String, Object> newAddTimingPlan(String planName,String deviceMac,String date) {
		Map<String, Object> resultMap = new HashMap<String, Object>();       
		TimingPlanDao tpd = new TimingPlanDaoImpl();
		DeviceDao dd = new DeviceDaoImpl();
		List<Device> devices = new ArrayList<>();
		int x = 0;
		//1.获取起始和结束的月份
		String startDate = date.substring(5,7);//0-8(5,7)
		String endDate = date.substring(16,18);//11-19(16,18)	
		try {
			//2.获取集控器
			Device device = dd.selectDeviceByDeviceMac(deviceMac);
			//3.判断计划是否重命
			TimingPlan plan = tpd.selectTimingPlanByName(planName);
			if(plan == null) {
				//4.数据库中写入新的计划
				TimingPlan timingPlan = new TimingPlan(); 
				timingPlan.setName(planName);
				timingPlan.setUserid(device.getUserid());
				timingPlan.setDeviceMac(deviceMac);
				timingPlan.setStartDate(Integer.parseInt(startDate));
				timingPlan.setEndDate(Integer.parseInt(endDate));
				timingPlan.setDoState(false);
				x = tpd.insert(timingPlan);
			}
			devices = dd.selectDeviceByUserid(device.getUserid());
			resultMap.put("devices",devices);
			resultMap.put("result",x);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultMap;
	}

	@Override
	public List<Device> timingPlanGetDevicesByUserid(int userid) {
		DeviceDao dDao = new DeviceDaoImpl();
		List<Device> devices = new ArrayList<>();
		try {
			 devices = dDao.selectDeviceByUserid(userid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return devices;
	}

	@Override
	public void addTimingByPlanId(int planid, String light1State, String light1PowerPercent, String light2State,
			String light2PowerPercent, Time time) {
		UserMessageDao userMDao = new UserMessageDaoImpl();
		TimingPlanDao tpd = new TimingPlanDaoImpl();
		TimingDao timingDao = new TimingDaoImpl();
		try {
			TimingPlan result = tpd.selectTimingPlanByTimingPlanId(planid);
		/*	//将用户指令写入用户信息数据库
			UserMessage um = new UserMessage();
			um.setUserid(result.getUserid());
			um.setDeviceMac(result.getDeviceMac());
			um.setExecuted(false);
			um.setCommand(UserMessage.CMD_BROADCAST_WRITE_STATE);
			// 1、将指令信息封装成指令对象
			um.setData(SocketCommand.GenerateBroadcastTimingWriteStateCommandData(light1State, light2State, light1PowerPercent,
					light2PowerPercent));
			// 2、写入数据库
			try {
				userMDao.insertUserMessage(um);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			String deviceMac = result.getDeviceMac();
			String planName = result.getName();
			boolean light1S;
			boolean light2S;
			//新建timing对象，设置参数,
			Timing timing = new Timing();
			if(light1State != null) {
				light1S = true;
			}else {
				light1S = false;
			}
			if(light2State != null) {
				light2S = true;
			}else {
				light2S = false;
			}
			timing.setLight1State(light1S);
			timing.setLight2State(light2S);
			timing.setDeviceMac(deviceMac);
			timing.setLight1PowerPercent(Integer.parseInt(light1PowerPercent));
			timing.setLight2PowerPercent(Integer.parseInt(light2PowerPercent));
			timing.setPlanName(planName);
			timing.setPlanid(planid);
			timing.setTime(time);
			timing.setFlag(false);
			timingDao.insert(timing);	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<Timing> searchTimingBroadcast(String planName) {
		List<Timing> list = new ArrayList<Timing>();
		TimingDao timingDao = new TimingDaoImpl();
		try {
			list = timingDao.selectTimingByTimingPlanName(planName);
			/*for(Timing obj:list) {
				Time time = obj.getTime();
				System.out.println("time:"+time);
			}*/
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int delTimingPlanById(String[] arr) {
		TimingPlanDao tpd = new TimingPlanDaoImpl();
		int num = 0;
		TimingDao timingDao = new TimingDaoImpl(); 
		try {
			for(int i = 0; i < arr.length; i++) {
				int planid = Integer.parseInt(arr[i]);
				//1.删除计划与计划内的定时广播
				timingDao.deleteTimingByPlanId(planid);
			    int x = tpd.deleteTimingPlanByPlanId(planid);
			    if(x > 0) {
			    	num++;
			    }
			    //2.根据用户id查询所有已进行定时设置的集控器定时计划
			    //timingPlanList = tpd.selectTimingPlanByUserid(plan.getUserid());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return num;
	}

	@Override
	public List<TimingPlan> implementTimingPlanByidAndUserid(int planid, int userid) {
		TimingPlanDao tpd = new TimingPlanDaoImpl();
		DeviceDao deviceDao = new DeviceDaoImpl(); 
		List<TimingPlan> timingPlanList = new  ArrayList<>();
		try {
			//1.集控器处于在线状态，更改计划状态为可执行
			TimingPlan timingPlan = tpd.selectTimingPlanByTimingPlanId(planid);
			timingPlan.setDoState(true);
			Device device = deviceDao.selectDeviceByDeviceMac(timingPlan.getDeviceMac());
			if(device.isOnline()) {
				tpd.updateTimingPlanStateByTimingPlan(timingPlan);
			}
			 timingPlanList = tpd.selectTimingPlanByUserid(userid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return timingPlanList;
	}

	@Override
	public List<TimingPlan> stopTimingPlanById(int planid,int userid) {
		TimingPlanDao tpd = new TimingPlanDaoImpl();
		List<TimingPlan> timingPlanList = new  ArrayList<>();
		TimingPlan timingPlan;
		try {
			timingPlan = tpd.selectTimingPlanByTimingPlanId(planid);
			//设置计划状态为停止状态
			timingPlan.setDoState(false);
			tpd.updateTimingPlanStateByTimingPlan(timingPlan);
			//查找该用户下的所有计划集合
			timingPlanList = tpd.selectTimingPlanByUserid(userid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return timingPlanList;
	}

	@Override
	public List<Timing> delTimingBroadcast(String[] listTiming) { 
		//Map<String, List<Timing>> result = new HashMap<String,List<Timing>>();
		List<Timing> result = new ArrayList<Timing>();
		TimingDao timingDao = new TimingDaoImpl();
		TimingPlanDao timingPlanDao = new TimingPlanDaoImpl();
		int num = 0;
		try {
			Timing timing = timingDao.selectTimingByTimingId(Integer.parseInt(listTiming[0]));
			TimingPlan plan = timingPlanDao.selectTimingPlanByTimingPlanId(timing.getPlanid());
			for(int i = 0; i < listTiming.length; i++) {
				String timingid = listTiming[i];
				timingDao.delTimingByTimingId(Integer.parseInt(timingid));
				num++;
			}
			if(num > 0) {
				result = timingDao.selectTimingByTimingPlanName(plan.getName());
			}
			//Timing timing = timingDao.selectTimingByTimingId(timingid);
			//TimingPlan plan = timingPlanDao.selectTimingPlanByTimingPlanId(timing.getPlanid());
			//timingDao.delTimingByTimingId(timingid);
			//result = timingDao.selectTimingByTimingPlanName(plan.getName());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override//
	public List<Timing> showDelTimingBroadcastByPlanid(int planid) {
		List<Timing> list = new ArrayList<Timing>();
		TimingDao timingDao = new TimingDaoImpl();
		TimingPlanDao timingPlanDao = new TimingPlanDaoImpl();
		try {
			TimingPlan plan = timingPlanDao.selectTimingPlanByTimingPlanId(planid);
			list = timingDao.selectTimingByTimingPlanName(plan.getName());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<TimingPlan> showDelTimingPlan(int userid) {
		TimingPlanDao timingPlanDao = new TimingPlanDaoImpl();
		List<TimingPlan> timingPlanList = new  ArrayList<>();
			//1.根据用户id查询所有已进行定时设置的集控器定时计划
		    try {
				timingPlanList = timingPlanDao.selectTimingPlanByUserid(userid);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    return timingPlanList;
		}

	@Override
	public Object sendVerificationCodeToEmail(String email) {
		Map<String,Object> result = new HashMap<String,Object>();
		String error = ""; //用于返回前端的错误字段
		User admin = null;
		UserDao userDao = new UserDaoImpl();
		//实例化一个发送邮件的对象
		SendJMail mySendMail = new SendJMail();
		
	    //设置随机验证码
		String verifyCode = "";
		Random random = new Random();
		for(int index = 0; index < 6; index++) {
			verifyCode+=random.nextInt(10);
		};
		
		try {
			
			admin = userDao.selectByEmail(email);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		if(admin != null) { //用户存在 
			if(admin.getOperateNum() == 0) {  //初次获取验证码	
				//发送邮件
				String emailMsg = 
						"Dear user, you are in the process of password retrieval to obtain the verification code." + 
					    "<br/>This verification code is " + verifyCode + ".If you do not operate yourself, please ignore it." +
					    "<br/>（尊敬的用户，您正在进行密码找回获取验证码，此次验证码是"+verifyCode + "； 如非本人操作请忽略。）" ;
				
				boolean sendResult = mySendMail.SendMail(admin.getEmail(), emailMsg);
				
				//更新user数据库中的vercode和operate_num
				admin.setVercode(verifyCode);
				if(sendResult) {
					admin.setOperateNum(admin.getOperateNum() + 1); //多次获取验证码
				}
				try {
					
					userDao.updateVerCodeAndOperateNumByPrimaryKey(admin);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//设置用户
				result.put("User", admin);
				
				
			}else if(admin.getOperateNum() < 4 && admin.getOperateNum() > 0) { // 多次获取验证码
				//发送邮件
				String emailMsg = 
					"Dear user, you are in the process of password retrieval to obtain the verification code." + 
				    "<br/>This verification code is " + verifyCode + ".If you do not operate yourself, please ignore it." +
				    "<br/>（尊敬的用户，您正在进行密码找回获取验证码，此次验证码是"+verifyCode + "； 如非本人操作请忽略。）" ;
				boolean sendResult = mySendMail.SendMail(admin.getEmail(), emailMsg);
				
				//更新user数据库中的vercode和operate_num
				admin.setVercode(verifyCode);
				if(sendResult) {
					admin.setOperateNum(admin.getOperateNum()+1); //多次获取验证码
				}
				try {
		
					userDao.updateVerCodeAndOperateNumByPrimaryKey(admin);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//设置用户
				result.put("User", admin);
			
			}else { 
			    error = "您今天的次数已超过4次，请明天再操作";
					
			}
			
			
		}else { //用户不存在
		    error = "未查找到用户，该邮箱未注册用户";  //错误提示用于前端提示，不要轻易修改
		}
		
		
		result.put("error", error);
		
		return result;
	    
	}

	@Override
	public Object findPasswordByVercode(String email, String verCode) {
		Map<String,Object> result = new HashMap<String,Object>();
		String error = ""; //用于返回前端的错误字段
		User admin = null;
		UserDao userDao = new UserDaoImpl();
		
		try {
			admin = userDao.selectByEmail(email);
			if(admin != null) {
				if(admin.getVercode().equals(verCode)) { 
					result.put("user",admin);
					
				}else {
					error = "验证码错误"; //错误提示用于前端提示，不要轻易修改
				}
			
			}else {
				error = "未查找到用户，该邮箱未注册用户";  //错误提示用于前端提示，不要轻易修改
				
			}		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		result.put("error", error);
		return result; 
	}

	@Override
	public boolean userSetNewPassword(int id, String newPassword) {
		UserDao userDao = new UserDaoImpl();
		boolean result = false;
		Mademd5 md = new Mademd5();
		String password = md.toMd5(newPassword);
		
		try {
			//设置新的密码
			 result = userDao.updateUserPasswordById(id,password);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
		
	}
	
	
	
	
}
