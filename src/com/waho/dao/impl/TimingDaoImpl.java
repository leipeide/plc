package com.waho.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.waho.dao.TimingDao;
import com.waho.domain.Timing;
import com.waho.util.C3P0Utils;

public class TimingDaoImpl implements TimingDao{

	@Override
	public Timing selectTimingByTimingPlanId(int planid) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		return qr.query("select *from timing where planid=?", new BeanHandler<Timing>(Timing.class), planid);
	}

	@Override
	public int insert(Timing timing) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		return qr.update(
				"INSERT INTO timing (`planName`, `deviceMac`, `light1State`, `light2State`, `light1PowerPercent`, `light2PowerPercent`, `time`, `planid`, `flag`) VALUES (?,?,?,?,?,?,?,?,?)",
				timing.getPlanName(),timing.getDeviceMac(),timing.isLight1State(),
				timing.isLight2State(),timing.getLight1PowerPercent(),timing.getLight2PowerPercent(),
				timing.getTime(),timing.getPlanid(),timing.isFlag());
	}

	@Override
	public List<Timing> selectTimingByTimingPlanName(String planName) throws SQLException{
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		return (List<Timing>) qr.query("select *from timing where planName=?", new BeanListHandler<Timing>(Timing.class), planName);
	}

	@Override
	public int deleteTimingByPlanId(int planid) throws SQLException {
			QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
			return qr.update("delete from timing where planid=?",planid);
	}

	@Override
	public int delTimingByTimingId(int id) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		return qr.update("delete from timing where id=?",id);
		
	}

	@Override
	public Timing selectTimingByTimingId(int timingid) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		return qr.query("select *from timing where id=?", new BeanHandler<Timing>(Timing.class), timingid);
	}

	@Override
	public void updateTimingFlagByTimingId(Timing timing) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
	    qr.update("UPDATE timing SET flag=? WHERE id=?",timing.isFlag(),timing.getId());
		
	}
	
	
	
}
