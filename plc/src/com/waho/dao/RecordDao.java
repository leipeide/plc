package com.waho.dao;

import com.waho.domain.Record;

public interface RecordDao {

	/**
	 * 根据节点地址和集控器mac地址，查询节点的最后一条功率统计记录
	 * @param nodeAddr
	 * @param deviceMac
	 * @return
	 * @throws Exception
	 */
	Record selectLastRecordByNodeAddrAndDeviceMac(String nodeAddr, String deviceMac) throws Exception;

	int insert(Record record) throws Exception;

}
