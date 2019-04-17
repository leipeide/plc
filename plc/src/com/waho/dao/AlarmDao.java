package com.waho.dao;

import java.util.List;

import com.waho.domain.Alarm;
import com.waho.domain.Device;

public interface AlarmDao {
	/**
	 * 插入报警信息
	 * @param alarm
	 * @return
	 * @throws Exception
	 */
	int insert(Alarm alarm) throws Exception;
	/**
	 * 更据deviceMac/nodeAddr/alarmDisconnect查找报警信息
	 * @param deviceMac
	 * @param nodeAddr
	 * @param alarmDisconnect
	 * @return
	 * @throws Exception
	 */
	Alarm selectByDeviceMacAndNodeAddrAndType(String deviceMac, String nodeAddr, int alarmDisconnect) throws Exception;
	/**
	 * 根据报警信息id删除报警信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	int deleteById(int id) throws Exception;
	/**
	 * 查找所有报警信息
	 * @param deviceMac 
	 * @return
	 * @throws Exception
	 */
	List<Alarm> selectAlarmRecordByDeviceMac(String deviceMac) throws Exception;
}
