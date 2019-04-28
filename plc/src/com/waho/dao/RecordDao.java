package com.waho.dao;

import java.util.Date;
import java.util.List;

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
	/**
	 * 向表中插入一条记录
	 * @param record
	 * @return
	 * @throws Exception
	 */
	int insert(Record record) throws Exception;
	/**
	 * 根据当前时间查取24小时内所有节点记录
	 * @param startDate
	 * @param endDate 
	 * @return
	 * @throws Exception
	 */
	List<Record> selectOneDayNodePowerMessage(Date startDate, Date endDate) throws Exception;

}
