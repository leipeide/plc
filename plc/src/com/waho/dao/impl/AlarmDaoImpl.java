package com.waho.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.waho.dao.AlarmDao;
import com.waho.domain.Alarm;
import com.waho.domain.Device;
import com.waho.domain.Node;
import com.waho.domain.Record;
import com.waho.util.C3P0Utils;

public class AlarmDaoImpl implements AlarmDao {

	@Override
	public int insert(Alarm alarm) throws Exception {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		return qr.update("INSERT INTO `plc`.`alarm` (`date`, `deviceMac`, `nodeAddr`, `type`) VALUES (?, ?, ?,?)",
				alarm.getDate(), alarm.getDeviceMac(), alarm.getNodeAddr(),alarm.getType());
	}

	@Override
	public Alarm selectByDeviceMacAndNodeAddrAndType(String deviceMac, String nodeAddr, int alarmDisconnect)
			throws Exception {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		return qr.query("select * from alarm where deviceMac=? and nodeAddr=? and type=?",
				new BeanHandler<Alarm>(Alarm.class), deviceMac, nodeAddr, alarmDisconnect);
	}

	@Override
	public int deleteById(int id) throws Exception {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		return qr.update("delete from alarm where id=?", id);
	}

	@Override
	public List<Alarm> selectAlarmRecordByDeviceMac(String deviceMac) throws Exception {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		return qr.query("SELECT * FROM plc.alarm where deviceMac=?", new BeanListHandler<Alarm>(Alarm.class),deviceMac);
	}
	
	
	
}
