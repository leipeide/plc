package com.waho.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.waho.dao.AlarmDao;
import com.waho.dao.DeviceDao;
import com.waho.dao.NodeDao;
import com.waho.dao.RecordDao;
import com.waho.dao.UserDao;
import com.waho.dao.UserMessageDao;
import com.waho.dao.impl.AlarmDaoImpl;
import com.waho.dao.impl.DeviceDaoImpl;
import com.waho.dao.impl.NodeDaoImpl;
import com.waho.dao.impl.RecordDaoImpl;
import com.waho.dao.impl.UserDaoImpl;
import com.waho.dao.impl.UserMessageDaoImpl;
import com.waho.domain.Alarm;
import com.waho.domain.Device;
import com.waho.domain.Node;
import com.waho.domain.PageBean;
import com.waho.domain.Record;
import com.waho.domain.SocketCommand;
import com.waho.domain.User;
import com.waho.domain.UserMessage;
import com.waho.service.UserService;

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
		// 根据nodeid获取设备信息
		NodeDao nodeDao = new NodeDaoImpl();
		DeviceDao deviceDao = new DeviceDaoImpl();
		UserMessageDao userMDao = new UserMessageDaoImpl();
		try {
			Node node = nodeDao.selectNodeById(nodeid);
			Device device = deviceDao.selectDeviceById(node.getDeviceid());
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
		// TODO Auto-generated method stub
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
		boolean result1 = false;
		try {
			device = deviceDao.selectDeviceByDeviceMac(deviceMac);    
			device.setUserid(userid);   //设置一个新的userid
			if(device != null) {
			   if( deviceDao.updateDeviceuseridByDeviceMac(device) != 0 ) {
				   result1 = true;
				}
			}
		} catch (Exception e) {
		}
		return result1;
	}

	@Override
	public boolean removeDeviceByDeviceName(String[] arrdeviceName,int userid) {
		boolean result2 = false;
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
			result2 = true;
		}
		return result2;
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
	public boolean registUser(String username,String password,String email) {
		UserDao userDao = new UserDaoImpl();
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setEmail(email);
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
				um.setUserid(device.getUserid());
				um.setDeviceMac(device.getDeviceMac());
				um.setCommand(UserMessage.CMD_ADD_NODE);
				um.setExecuted(false);
				um.setData(SocketCommand.GenerateAddNodesCommandDate(num,nodeAddrString));
				UserMessageDao umDao = new UserMessageDaoImpl();
				umDao.insertUserMessage(um);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
	 }

	@Override
	public int delNodesCmd(int deviceid, String[] nodeAddr) {
		DeviceDao deviceDao = new DeviceDaoImpl();
		NodeDao nodeDao = new NodeDaoImpl();
		Node  node = new Node();
		int sum = 0;
		try {
		    Device device = deviceDao.selectDeviceById(deviceid);
			//删除节点
			for(int i=0; i<nodeAddr.length; i++) {
				String nodeAddr1 = nodeAddr[i];
				node.setNodeAddr(nodeAddr1);
				node = nodeDao.selectNodeByNodeAddr(nodeAddr1);
				int x = nodeDao.deletNodesByNodeAddr(node);
				sum = sum + x;	
			}
			// 更新数据库
			//System.out.println("sum:"+sum);
			device.setCurrentNodes(device.getCurrentNodes()-sum);
			//System.out.println("当前节点数量："+device.getCurrentNodes());
			
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
			// node= nodeDao.selectNodeByNodeAddr(nodeAddr);
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
		//注意：SimpleDateFormat构造函数的样式与strDate的样式必须相符
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy年MM月dd日");
    	try {
    		Date startDate = simpleDateFormat.parse(startDateStr);
			Date endDate = simpleDateFormat.parse(endDateStr);
    		//获取集控器Mac地址
    		Node node = nDao.selectNodeByNodeAddr(nodeAddr);
    		String deviceMac = node.getDeviceMac();
    		//根据时间范围查询该时间段内所有节点的记录
    		ArrayList<Record> powerRecord = new ArrayList<Record>();
			List<Record> record = rDao.selectOneDayNodePowerMessage(startDate,endDate);
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
			
		    for(int num = 0; num < powerRecord.size(); num++) {
		    	boolean record0State1 = powerRecord.get(num).isLight1State();
			    boolean record0State2 = powerRecord.get(num).isLight2State();	 
		    	if(record0State1 || record0State2) {
					ArrayList<Date> timelist = new ArrayList<Date>();
					timelist.add(powerRecord.get(num).getDate());
					int x = num+1;
					boolean result = false;
					for (int j = x; j < powerRecord.size(); j++) {
						boolean recordState1 = powerRecord.get(j).isLight1State();
						boolean recordState2 = powerRecord.get(j).isLight2State();
						if((recordState1 || recordState2) == result) {
							Date time = powerRecord.get(j).getDate();
							timelist.add(time);
							result = !result;
						}
						recordMap.put("timeRecord", timelist);
					}
		    	}
		    	break;
		    }		
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return recordMap;
	}

	@Override//use
	public Map<String, Object> getWarnningMessageById(int userid) {
		Map<String, Object> alarmMap = null;
		AlarmDao aDao = new AlarmDaoImpl();
		DeviceDao dDao = new DeviceDaoImpl();
 		List<Alarm> alarm;
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
				//根据集控器查询该用户下的所有报警信息，并放在一个集合中
				for(Device device:list) {
					alarm = aDao.selectAlarmRecordByDeviceMac(device.getDeviceMac());
					int num = alarm.size();
					total = total + num;
					alarmRecord.addAll(alarm);
					deviceMacList.add(device.getDeviceMac());
				}
				//冒泡法进行日期排序：较近的日期靠前
				for(int i = 0;i < alarmRecord.size()-1;i++) {//外层循环控制排序趟数
					for(int j = 0; j < alarmRecord.size()-1-i; j++ ) {//内层循环控制每一趟排序多少次
						Date date1 = alarmRecord.get(j).getDate();
						Date date2 = alarmRecord.get(j+1).getDate();
						if(date1.before(date2)) {
							 Collections.swap(alarmRecord, j, j + 1);
						}
					}
				}
			
				//获取记录中节点的种类、集控器地址种类
				for(int i = 0;i < alarmRecord.size();i++) {
					//System.out.println("alarmRecord的大小"+alarmRecord.size());
					//System.out.println("进入"+i);
					String nodeAddr = alarmRecord.get(i).getNodeAddr();
					//String deviceMac = alarmRecord.get(i).getDeviceMac();
					//System.out.println("节点地址0"+nodeAddr);
					if(nodeAddrList.contains(nodeAddr)) {
						break;
					}else {
						nodeAddrList.add(nodeAddr);
						//System.out.println("节点地址1"+nodeAddr);
					}
		/*			if(deviceMacList.contains(deviceMac)) {
						break;
					}else {
						deviceMacList.add(deviceMac);
					}*/
				}
				//System.out.println("alarmRecord的大小："+JSON.toJSONString(alarmRecord));
				//System.out.println("节点长度："+nodeAddrList.size());
				alarmMap.put("deviceMacList",deviceMacList);
				alarmMap.put("nodeAddrList",nodeAddrList);
				alarmMap.put("alarmList", alarmRecord);
				alarmMap.put("warnningNum", total); 
				
				
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
			List<Alarm> alarmRecord = aDao.selectAlarmRecordByUserid(userid);
			List<Device> device = dDao.selectDeviceByUserid(userid);
			int total = alarmRecord.size();
			for(Device obj:device) {
				deviceMacList.add(obj.getDeviceMac());
			}
			
			//冒泡法进行日期排序：较近的日期靠前
			for(int i = 0;i < alarmRecord.size()-1;i++) {//外层循环控制排序趟数
				for(int j = 0; j < alarmRecord.size()-1-i; j++ ) {//内层循环控制每一趟排序多少次
					Date date1 = alarmRecord.get(j).getDate();
					Date date2 = alarmRecord.get(j+1).getDate();
					if(date1.before(date2)) {
						 Collections.swap(alarmRecord, j, j + 1);
					}
				}
			}
			
			//获取记录中节点的种类、集控器地址种类
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
		//DeviceDao dDao = new DeviceDaoImpl();
 		//List<Alarm> alarm;
 		List<Alarm> alarmRecord = new ArrayList<>();
			try {
			/*	List<Device> list = dDao.selectDeviceByUserid(userid);
				//根据集控器查询该用户下的所有报警信息，并放在一个集合中
				for(Device device:list) {
					alarm = aDao.selectAlarmRecordByDeviceMac(device.getDeviceMac());
					int num = alarm.size();
					total = total+num;
					alarmRecord.addAll(alarm);
				}*/
				
				alarmRecord = aDao.selectAlarmRecordByUserid(userid);
				int total = alarmRecord.size();
				//冒泡法进行日期排序：较近的日期靠前
				for(int i = 0;i < alarmRecord.size()-1;i++) {//外层循环控制排序趟数
					for(int j = 0; j < alarmRecord.size()-1-i; j++ ) {//内层循环控制每一趟排序多少次
						Date date1 = alarmRecord.get(j).getDate();
						Date date2 = alarmRecord.get(j+1).getDate();
						if(date1.before(date2)) {
							 Collections.swap(alarmRecord, j, j + 1);
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
		//List<Alarm> alarm = new ArrayList<>();
		List<Alarm> alarmRecord = new ArrayList<>();
		int type = 0;
			try {
				if(deviceMac != null) {
					//System.out.println("进入deviceMac");
					alarmRecord = aDao.selectAlarmRecordByDeviceMac(deviceMac);
					
				}if(nodeAddr != null) {
					//System.out.println("进入nodeAddr");
					alarmRecord = aDao.selectAlarmRecordByNodeAddrAndUserid(nodeAddr,userid);
					
				}if(typeStr != null) {
					//System.out.println("进入typeStr");
					switch(typeStr){
						case "节点过功率，请及时处理" :
							type = 1;
							break; 
						case "集控器连接失败，请及时处理" :
							type = 2;
							break; 
						default :
							break;
					}
					alarmRecord = aDao.selectAlarmRecordByTypeAndUserid(type,userid);
					
				}if(date != null) {
					//System.out.println("进入date");
					alarmRecord = aDao.selectAlarmRecordByUserid(userid);
				}	
				//冒泡法进行日期排序：较近的日期靠前
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
				//if(date.equals("倒序")) {
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
						//break;
					}else {
						nodeAddrList.add(nodeAddr);
					}
				}
				alarmMap.put("deviceMacList",deviceMacList);
				alarmMap.put("nodeAddrList",nodeAddrList);
				alarmMap.put("alarmList", alarmRecord);
				//alarmMap.put("warnningNum", list.size()); 
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return alarmMap;
		
	}
	
	
}
