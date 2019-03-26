/*package com.waho.socket.util;

import java.util.Arrays;

import com.waho.dao.DeviceDao;
import com.waho.dao.NodeDao;
import com.waho.dao.impl.DeviceDaoImpl;
import com.waho.dao.impl.NodeDaoImpl;
import com.waho.domain.Device;
import com.waho.domain.Node;
import com.waho.domain.SocketCommand;

public class CmdDelNodeHandler extends SocketDataHandler{
	private static volatile CmdDelNodeHandler instance;
	public static CmdDelNodeHandler getInstance(SocketDataHandler nextHandler) {
		if (instance == null) {
			synchronized (CmdDelNodeHandler.class) {
				if (instance == null) {
					instance = new CmdDelNodeHandler(nextHandler);
				}
			}
		}
		//System.out.println("2019你好！");
		return instance;
}
	
	private CmdDelNodeHandler(SocketDataHandler nextHandler) {
		super(nextHandler);
		this.setCmdType(SocketCommand.CMD_DELETE_NODE_REP);

	}

	@Override
	public SocketCommand socketCommandHandle(SocketCommand sc, Device device) {
		if (sc.getCommand() == this.getCmdType()) {
			SocketCommand result = new SocketCommand();
			dataHandle(sc, device);
			result.setCommand(SocketCommand.CMD_DELETE_NODE_REP);	
			return result;
			}else if (nextHandler != null) {
				return nextHandler.socketCommandHandle(sc, device);
			}
		return null;
	}

   private void dataHandle(SocketCommand sc, Device device) {
		byte nodeNumber = sc.getData()[0];
		System.out.println("删除节点个数："+nodeNumber);
		System.out.println("删除节点数据"+Arrays.toString(sc.getData()));
		byte[] msgArr = new byte[6]; 
		for (int i = 0; i < (int) nodeNumber; i++) {
			System.arraycopy(sc.getData(), 1 + i * 7, msgArr, 0, 6);
			String nodeAddr = SocketCommand.parseBytesToHexString(msgArr, msgArr.length);
			System.out.println("删除节点地址："+nodeAddr);
			NodeDao nodeDao = new NodeDaoImpl();
			Node node = new Node();
			node.setNodeAddr(nodeAddr.substring(0, 6 * 2));
			
			try {
				//删除节点
				node = nodeDao.selectNodeByNodeAddr(nodeAddr);
				nodeDao.deletNodesByNodeAddr(node);
				//更新数据库
				device.setCurrentNodes(device.getCurrentNodes() - (int)nodeNumber);
				DeviceDao devDao = new DeviceDaoImpl();
				devDao.updateDeviceCurrentNodes(device);
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		
	}

}

/**
@Override
public SocketCommand socketCommandHandle(SocketCommand sc, Device device) {
	if (sc.getCommand() == this.getCmdType()) {
		SocketCommand result = new SocketCommand();
		dataHandle(sc, device);
		result.setCommand(SocketCommand.CMD_ADD_NODE_REP);	
		return result;
		}else if (nextHandler != null) {
			return nextHandler.socketCommandHandle(sc, device);
		}
	return null;
}

private void dataHandle(SocketCommand sc, Device device) {
	byte nodeNumber = sc.getData()[0];
	byte[][] msgArr = new byte[nodeNumber][6]; 未加通信协议类型
	for (int i = 0; i < (int) nodeNumber; i++) {
		System.arraycopy(sc.getData(), 1 + i * 7, msgArr[i], 0, 6);
		System.out.println("数组msgArr:"+Arrays.toString(msgArr[i]));
	}
	// 封装到节点对象
	LinkedList<Node> list = new LinkedList<>();
	for (byte[] bs : msgArr) {
		//System.out.println("数组bs:"+Arrays.toString(bs));
		Node node = new Node();
		node.setDeviceid(device.getId());
		node.setDeviceMac(device.getDeviceMac());
		String temp = SocketCommand.parseBytesToHexString(bs, bs.length);
		node.setNodeAddr(temp.substring(0, 6 * 2));
		node.setNodeName(node.getNodeAddr());
		node.setAgreement(Integer.parseInt(temp.substring(12, 14), 16));
		list.add(node);
	}
	System.out.println("节点："+list);
	// 更新数据库
			NodeDao nodeDao = new NodeDaoImpl();
			// 读取集控器数据，根据集控器节点的数量进行添加
			device.setCurrentNodes(device.getCurrentNodes() + list.size());
			DeviceDao devDao = new DeviceDaoImpl();
			try {
				devDao.updateDeviceCurrentNodes(device);
				nodeDao.insert(list);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
}
*/