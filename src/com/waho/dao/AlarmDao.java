package com.waho.dao;

import java.util.List;

import com.waho.domain.Alarm;

public interface AlarmDao {
	/**
	 * 插入报警信息
	 * @param alarm
	 * @return
	 * @throws Exception
	 */
	int insert(Alarm alarm) throws Exception;
	/**
	 * 更据deviceMac/nodeAddr/type查找报警信息
	 * @param deviceMac
	 * @param nodeAddr
	 * @param alarmDisconnect
	 * @return
	 * @throws Exception
	 */
	 List<Alarm>  selectByDeviceMacAndNodeAddrAndType(String deviceMac, String nodeAddr, int alarmDisconnect) throws Exception;
	/**
	 * 根据报警信息id删除报警信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	int deleteById(int id) throws Exception;
	/**
	 * 根据集控器Mac地址查找报警信息
	 * @param deviceMac 
	 * @return
	 * @throws Exception
	 */
	List<Alarm> selectAlarmRecordByDeviceMac(String deviceMac) throws Exception;
	/**
	 * 根据节点地址和用户id查找报警信息
	 * @param nodeAddr
	 * @param userid
	 * @return
	 */
	List<Alarm> selectAlarmRecordByNodeAddrAndUserid(String nodeAddr, int userid) throws Exception;
	/**
	 * 根据报警类型和用户id查找报警信息
	 * @param type
	 * @param userid
	 * @return
	 */
	List<Alarm> selectAlarmRecordByTypeAndUserid(int type, int userid) throws Exception;
	/**
	 * 查询该用户所有的报警记录
	 * @param userid
	 * @return
	 */
	List<Alarm> selectAlarmRecordByUserid(int userid) throws Exception;
	/**
	 * 根据报警信息id查找报警信息
	 * @param string
	 * @return
	 * @throws Exception
	 */
	Alarm selectAlarmRecordByAlarmId(String id) throws Exception;
	/**
	 * 根据deviceMac/type查找报警信息
	 * @param deviceMac
	 * @param alarmDisconnect
	 * @return
	 * @throws Exception
	 */
	List<Alarm> selectByDeviceMacAndType(String deviceMac, int alarmDisconnect) throws Exception;
	
}
