package com.waho.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import com.waho.domain.Node;
import com.waho.domain.Timing;
import com.waho.util.C3P0Utils;

public interface TimingDao {
	/**
	 * 根据用户定时计划id查询用户定时单灯控制指令
	 * @param planid
	 * @return
	 */
	public Timing selectTimingByTimingPlanId(int planid) throws SQLException;
	/**
	 * 创建新的定时广播控制
	 * @param timing
	 * @return
	 * @throws Exception
	 */
	public int insert(Timing timing) throws SQLException;
	/**
	 * 根据计划名称获取定时广播信息
	 * @param planName
	 */
	public List<Timing> selectTimingByTimingPlanName(String planName) throws SQLException;
	/**
	 *根据计划id删除定时广播
	 * @param planid
	 */
	public int deleteTimingByPlanId(int planid) throws SQLException;
	/**
	 * 根据定时广播id删除定时广播
	 * @param timingId
	 * @throws SQLException
	 */
	public int delTimingByTimingId(int id) throws SQLException;
	/**
	 * 根据定时广播id查找timing
	 * @param timingid
	 * @return
	 * @throws SQLException
	 */
	public Timing selectTimingByTimingId(int timingid) throws SQLException;
	/**
	 * 更加id更新定时广播执行状态
	 * @param id
	 * @throws SQLException
	 */
	public void updateTimingFlagByTimingId(Timing timing) throws SQLException;

}
