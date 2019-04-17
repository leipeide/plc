package com.waho.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.waho.dao.DeviceConnectRecordDao;
import com.waho.domain.DeviceConnectRecord;
import com.waho.util.C3P0Utils;

public class DeviceConnectRecordDaoImpl implements DeviceConnectRecordDao {

	@Override
	public DeviceConnectRecord selectLastRecordByDeviceMac(String deviceMac) throws Exception {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		return qr.query("select * from device_connect_record where deviceMac=? order by id DESC limit 1",
				new BeanHandler<DeviceConnectRecord>(DeviceConnectRecord.class), deviceMac);
	}

	@Override
	public int insert(DeviceConnectRecord dcr) throws Exception {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		return qr.update(
				"INSERT INTO `plc`.`device_connect_record` (`date`, `deviceMac`, `connection`) VALUES (?, ?, ?)",
				dcr.getDate(), dcr.getDeviceMac(), dcr.isConnection());
	}

}
