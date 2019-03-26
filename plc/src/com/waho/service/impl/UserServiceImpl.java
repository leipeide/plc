package com.waho.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.waho.dao.DeviceDao;
import com.waho.dao.NodeDao;
import com.waho.dao.UserDao;
import com.waho.dao.UserMessageDao;
import com.waho.dao.impl.DeviceDaoImpl;
import com.waho.dao.impl.NodeDaoImpl;
import com.waho.dao.impl.UserDaoImpl;
import com.waho.dao.impl.UserMessageDaoImpl;
import com.waho.domain.Device;
import com.waho.domain.Node;
import com.waho.domain.PageBean;
import com.waho.domain.SocketCommand;
import com.waho.domain.User;
import com.waho.domain.UserMessage;
import com.waho.service.UserService;
import com.waho.util.Protocol3762Handler;
import com.waho.util.Protocol645Handler;

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
		try {
			list = nodeDao.selectNodesByDeviceid(deviceid);  
			int count = list.size();
			
			//把5个变量封装到PageBean中，做为返回值
			PageBean pb = new PageBean();
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
			return pb;
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
	public boolean delNodesCmd(int deviceid, String[] nodeAddr) {
		UserMessage um = new UserMessage();
		DeviceDao deviceDao = new DeviceDaoImpl();
		NodeDao nodeDao = new NodeDaoImpl();
		Node  node = new Node();
		int num = nodeAddr.length;
		int sum = 0;
		Boolean result = false;
		
		try {//将指令写入数据库
		    Device device = deviceDao.selectDeviceById(deviceid);
			/*um.setUserid(device.getUserid());
			um.setDeviceMac(device.getDeviceMac());
			um.setCommand(SocketCommand.CMD_DELETE_NODE);
			um.setExecuted(false);
			um.setData(SocketCommand.GenerateDelNodesCommandDate(num, nodeAddr));
			UserMessageDao umDao = new UserMessageDaoImpl();
			umDao.insertUserMessage(um);*/
			
			//删除节点
			for(int i=0; i<num; i++) {
				String nodeAddr1 = nodeAddr[i];
				node.setNodeAddr(nodeAddr1);
				node = nodeDao.selectNodeByNodeAddr(nodeAddr1);
				int x = nodeDao.deletNodesByNodeAddr(node);
				if(x == 1) {
					sum=sum+1;
				}
			}
			
			// 更新数据库
			device.setCurrentNodes(device.getCurrentNodes()-sum);
			try {
				deviceDao.updateDeviceCurrentNodes(device);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if(sum == num) {
				result = true;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
		
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
	public boolean DeviceRename(int deviceid, String deviceRename) {
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
	
}
