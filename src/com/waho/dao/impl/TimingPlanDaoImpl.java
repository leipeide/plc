package com.waho.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.waho.dao.TimingPlanDao;
import com.waho.domain.Device;
import com.waho.domain.Timing;
import com.waho.domain.TimingPlan;
import com.waho.util.C3P0Utils;

public class TimingPlanDaoImpl implements TimingPlanDao{

	@Override
	public List<TimingPlan> selectTimingPlanByUserid(int userid) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		return qr.query("select * from timing_plan where userid=?", new BeanListHandler<TimingPlan>(TimingPlan.class),userid);
	}

	@Override
	public int insert(TimingPlan timingPlan) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		return qr.update(
				"INSERT INTO timing_plan (`name`, `deviceMac`, `userid`, `startDate`, `endDate`, `doState`) VALUES (?,?,?,?,?,?)",
				timingPlan.getName(),timingPlan.getDeviceMac(),timingPlan.getUserid(),
				timingPlan.getStartDate(),timingPlan.getEndDate(),timingPlan.getDoState());
	}

	@Override
	public TimingPlan selectTimingPlanByTimingPlanId(int planid) throws SQLException {
			QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
			return qr.query("select *from timing_plan where id=?", new BeanHandler<TimingPlan>(TimingPlan.class), planid);
		}

	@Override
	public int deleteTimingPlanByPlanId(int planid)  throws SQLException{
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
	    return qr.update("delete from timing_plan where id=?",planid);
		
	}

	@Override
	public int updateTimingPlanStateByTimingPlan(TimingPlan timingPlan) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		return qr.update(
				"UPDATE timing_plan SET doState=? WHERE id=?",timingPlan.getDoState(),timingPlan.getId());
	}

	@Override
	public List<TimingPlan> selectTimingPlanByDeviceMac(String deviceMac) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		return qr.query("select * from timing_plan where deviceMac=?", new BeanListHandler<TimingPlan>(TimingPlan.class),deviceMac);
	}

	@Override
	public TimingPlan selectTimingPlanByName(String planName) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		return qr.query("select * from timing_plan where name=?", new BeanHandler<TimingPlan>(TimingPlan.class),planName);
	}
	
	
}
	

	
