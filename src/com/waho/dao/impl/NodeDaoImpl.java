package com.waho.dao.impl;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.waho.dao.NodeDao;
import com.waho.domain.Device;
import com.waho.domain.Node;
import com.waho.util.C3P0Utils;

public class NodeDaoImpl implements NodeDao {

	@Override
	public List<Node> selectNodesByDeviceid(int deviceid) throws Exception {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		return qr.query("select * from nodes where deviceid=?", new BeanListHandler<Node>(Node.class), deviceid);
	}

	@Override
	public Node selectNodeById(int id) throws Exception {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		return qr.query("select * from nodes where id=?", new BeanHandler<Node>(Node.class), id);
	}

	@Override
	public int insert(Node node) throws Exception {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		return qr.update(
				"INSERT INTO nodes (`deviceid`, `deviceMac`, `nodeAddr`, `nodeName`, `signal`, `relayLevel`, `agreement`, `Phase`) VALUES (?,?,?,?,?,?,?,?)",
				node.getDeviceid(), node.getDeviceMac(), node.getNodeAddr(), node.getNodeName(), node.getSignal(),
				node.getRelayLevel(), node.getAgreement(), node.getPhase());
	}

	@Override
	public int insert(List<Node> list) throws Exception {
		Object[][] params = new Object[list.size()][8];
		for (int i = 0; i < list.size(); i++) {
			params[i][0] = list.get(i).getDeviceid();
			params[i][1] = list.get(i).getDeviceMac();
			params[i][2] = list.get(i).getNodeAddr();
			params[i][3] = list.get(i).getNodeName();
			params[i][4] = list.get(i).getSignal();
			params[i][5] = list.get(i).getRelayLevel();
			params[i][6] = list.get(i).getAgreement();
			params[i][7] = list.get(i).getPhase();
		}
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		int[] result = qr.batch(
				"INSERT INTO nodes (`deviceid`, `deviceMac`, `nodeAddr`, `nodeName`, `signal`, `relayLevel`, `agreement`, `Phase`) VALUES (?,?,?,?,?,?,?,?)",
				params);
		int sum = 0;
		for (int i : result) {
			sum += i;
		}
		return sum;
	}

	@Override
	public int deletNodesByDevice(Device device) throws Exception {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		return qr.update("delete from nodes where deviceMac=?", device.getDeviceMac());
	}

	@Override
	public int deletNodesByNodeAddr(Node node) throws Exception {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
	    int x = qr.update("delete from nodes where nodeAddr=?", node.getNodeAddr());
	    return x;
	}

	@Override
	public int updateNodeStateAndPowerByNodeAddrAndDeviceid(Node node) throws Exception {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		return qr.update(
				"UPDATE nodes SET power=?,light1State=?,light1PowerPercent=?, light1Power=?,light2State=?,light2PowerPercent=?,light2Power=? WHERE nodeAddr=? and deviceid=?",
				node.getPower(), node.isLight1State(), node.getLight1PowerPercent(), node.getLight1Power(), node.isLight2State(),
				node.getLight2PowerPercent(), node.getLight2Power(), node.getNodeAddr(), node.getDeviceid());
	}

	
	@Override
	//核对是否需要添加lightPower,之前未添加power,light1Power,light2power
	public int updateNodeStateAndPowerByDeviceId(Node node) throws Exception {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		return qr.update(
				"UPDATE nodes SET power=?,light1State=?,light1PowerPercent=?,light1Power=?,light2State=?,light2PowerPercent=?,light2Power=? WHERE deviceid=?",
				node.getPower(),node.isLight1State(), node.getLight1PowerPercent(),node.getLight1Power(), node.isLight2State(), 
				node.getLight2PowerPercent(), node.getLight2Power(),node.getDeviceid());
	}

	@Override
	public Node selectNodeByNodeAddr(String nodeAddr) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		return qr.query("select * from nodes where nodeAddr=?", new BeanHandler<Node>(Node.class), nodeAddr);
	}

	@Override
	public int updateNodeNameByNodeId(Node node) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		return qr.update("UPDATE nodes SET nodeName=? WHERE id=?",node.getNodeName(),node.getId());
	}

	@Override
	public Node selectNodeByNodeaddrAndDevmac(String deviceMac, String nodeAddr) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		return qr.query("select * from nodes where nodeAddr=? and deviceMac=?", new BeanHandler<Node>(Node.class), nodeAddr, deviceMac);
	}
    
}
