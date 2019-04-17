package com.waho.domain;

import java.util.Date;

public class Alarm {
	
	/**
	 * 异常报警原因：
	 * 1. 过功率
	 * 2. 连接失败
	 */
	public static final int ALARM_OVERLOAD = 1; 
	
	public static final int ALARM_DISCONNECT = 2;
	
	public static final int LIGHT1_OVERLOAD_VOLTAGE = 200;
	
	public static final int LIGHT2_OVERLOAD_VOLTAGE = 100;
	
	public static final int TOTAL_OVERLOAD_VOLTAGE = 300;

	/**
	 * 报警id，primary key
	 */
	private int id;
	
	/**
	 * 报警时间.(报警信息生成的时间)
	 */
	private Date date;
	
	/**
	 * 集控器设备的mac地址
	 */
	private String deviceMac;
	
	/**
	 * 报警的节点地址
	 */
	private String nodeAddr;
	
	/**
	 * 报警信息类型
	 */
	private int type;

	public Alarm() {
		super();
	}
	
	public Alarm(Date date, String deviceMac, String nodeAddr, int type) {
		super();
		this.setDate(date);
		this.setDeviceMac(deviceMac);
		this.setNodeAddr(nodeAddr);
		this.setType(type);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDeviceMac() {
		return deviceMac;
	}

	public void setDeviceMac(String deviceMac) {
		this.deviceMac = deviceMac;
	}

	public String getNodeAddr() {
		return nodeAddr;
	}

	public void setNodeAddr(String nodeAddr) {
		this.nodeAddr = nodeAddr;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Alarm [id=" + id + ", time=" + date + ", deviceMac=" + deviceMac + ", nodeAddr=" + nodeAddr + ", type="
				+ type + "]";
	}
	
}
