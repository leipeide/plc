package com.waho.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.waho.dao.RecordDao;
import com.waho.domain.Node;
import com.waho.domain.Record;
import com.waho.util.C3P0Utils;

public class RecordDaoImpl implements RecordDao {
	@Override
	public Record selectLastRecordByNodeAddrAndDeviceMac(String nodeAddr, String deviceMac) throws Exception {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		return qr.query("select * from record where nodeAddr=? and deviceMac=? order by id DESC limit 1",
				new BeanHandler<Record>(Record.class), nodeAddr, deviceMac);
	}

	@Override
	public int insert(Record record) throws Exception {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		return qr.update(
				"INSERT INTO `plc`.`record` (`date`, `deviceMac`, `nodeAddr`, `light1State`, `light2State`, `light1PowerPercent`, `light2PowerPercent`, `light1Power`, `light2Power`, `power`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
				record.getDate(), record.getDeviceMac(), record.getNodeAddr(), record.isLight1State(),
				record.isLight2State(), record.getLight1PowerPercent(), record.getLight2PowerPercent(),
				record.getLight1Power(), record.getLight2Power(), record.getPower());
	}

	@Override
	public List<Record> selectOneDayNodePowerMessage(Date startDate,Date endDate) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		return qr.query("select * from record where date>=? AND date<=?", new BeanListHandler<Record>(Record.class),startDate,endDate);	
	   
	} 

	
}
