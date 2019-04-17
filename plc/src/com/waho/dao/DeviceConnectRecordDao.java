package com.waho.dao;

import com.waho.domain.DeviceConnectRecord;

public interface DeviceConnectRecordDao {

	DeviceConnectRecord selectLastRecordByDeviceMac(String deviceMac) throws Exception;

	int insert(DeviceConnectRecord dcr) throws Exception;

}
