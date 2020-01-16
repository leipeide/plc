package com.waho.socket.util;

import java.util.Arrays;
import java.util.LinkedList;

import com.alibaba.fastjson.JSON;
import com.waho.dao.DeviceDao;
import com.waho.dao.NodeDao;
import com.waho.dao.impl.DeviceDaoImpl;
import com.waho.dao.impl.NodeDaoImpl;
import com.waho.domain.Device;
import com.waho.domain.Node;
import com.waho.domain.SocketCommand;
/**
 * 接收到删除节点指令回复后的处理
 * @author lpdyf01
 *
 */
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
			//dataHandle(sc, device); //数据处理
			result.setCommand(SocketCommand.CMD_DELETE_NODE_REP);	
			return result;
			}else if (nextHandler != null) {
				return nextHandler.socketCommandHandle(sc, device);
			}
		return null;
	}
	
}
