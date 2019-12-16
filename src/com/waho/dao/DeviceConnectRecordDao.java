package com.waho.dao;

import com.waho.domain.DeviceConnectRecord;

public interface DeviceConnectRecordDao {
	/**
	 * 根据集控器mac地址查找最后一条记录
	 * @param deviceMac
	 * @return
	 * @throws Exception
	 */
	DeviceConnectRecord selectLastRecordByDeviceMac(String deviceMac) throws Exception;
	/**
	 * 向device_connect_record中插入集控器连接记录
	 * @param dcr
	 * @return
	 * @throws Exception
	 */
	int insert(DeviceConnectRecord dcr) throws Exception;

}
