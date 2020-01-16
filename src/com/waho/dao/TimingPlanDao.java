package com.waho.dao;

import java.sql.SQLException;
import java.util.List;

import com.waho.domain.TimingPlan;
import com.waho.domain.User;

public interface TimingPlanDao {
	/**
	 * 根据用户id查询所有定时计划
	 * @param userid
	 * @return
	 */
	List<TimingPlan> selectTimingPlanByUserid(int userid) throws SQLException;
	/**
	 * 新增计划
	 * @param timingPlan
	 * @return
	 */
	int insert(TimingPlan timingPlan)throws SQLException;
	/**
	 * 根据计划id查询计划
	 * @param planid
	 * @return
	 */
	TimingPlan selectTimingPlanByTimingPlanId(int planid) throws SQLException;
	/**
	 * 根据计划id和集控器mac地址删除计划
	 * @param planid
	 * @param deviceMac
	 */
	int deleteTimingPlanByPlanId(int planid)  throws SQLException;
	/**
	 * 根据计划id设置计划执行状态
	 * @param planid
	 * @return
	 * @throws SQLException
	 */
	int updateTimingPlanStateByTimingPlan(TimingPlan timingPlan)throws SQLException;
	/**
	 * 根据集控器地址查询计划
	 * @param deviceMac
	 * @return
	 * @throws SQLException
	 */
	List<TimingPlan> selectTimingPlanByDeviceMac(String deviceMac)throws SQLException;
	/**
	 * 根据计划名称查找计划
	 * @param planName
	 * @return
	 */
	TimingPlan selectTimingPlanByName(String planName) throws SQLException;
	
	

}
