package com.waho.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.waho.dao.AlarmDao;
import com.waho.domain.Alarm;
import com.waho.util.C3P0Utils;

public class AlarmDaoImpl implements AlarmDao {

	@Override
	public int insert(Alarm alarm) throws Exception {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		return qr.update("INSERT INTO `plc`.`alarm` (`date`, `deviceMac`, `nodeAddr`) VALUES (?, ?, ?)",
				alarm.getDate(), alarm.getDeviceMac(), alarm.getNodeAddr());
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

}
