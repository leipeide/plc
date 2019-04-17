package com.waho.domain;

import java.util.Date;

/**
 * 集控器链接服务器和断开服务器时间记录。
 * @author mingxin
 *
 */
public class DeviceConnectRecord {

	/**
	 * primary key
	 */
	private int id;
	/**
	 * 时间
	 */
	private Date date;
	/**
	 * 集控器mac地址
	 */
	private String deviceMac;
	/**
	 * 操作
	 * 成功连接为true
	 * 连接断开为false
	 */
	private boolean connection;
	
	public DeviceConnectRecord() {
		super();
	}
	
	public DeviceConnectRecord(String deviceMac, Date date, boolean connection) {
		super();
		this.deviceMac = deviceMac;
		this.date = date;
		this.connection = connection;
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
	public boolean isConnection() {
		return connection;
	}
	public void setConnection(boolean connection) {
		this.connection = connection;
	}
	
	public String getDeviceMac() {
		return deviceMac;
	}

	public void setDeviceMac(String deviceMac) {
		this.deviceMac = deviceMac;
	}

	@Override
	public String toString() {
		return "DeviceConnectRecord [id=" + id + ", date=" + date + ", deviceMac=" + deviceMac + ", connection="
				+ connection + "]";
	}

	
}
