package com.waho.dao;

import com.waho.domain.Alarm;

public interface AlarmDao {

	int insert(Alarm alarm) throws Exception;

	Alarm selectByDeviceMacAndNodeAddrAndType(String deviceMac, String nodeAddr, int alarmDisconnect) throws Exception;

	int deleteById(int id) throws Exception;

}
