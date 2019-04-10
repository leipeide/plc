package com.waho.domain;

import java.util.Date;

public class Record {

	/**
	 * primary key，日志信息id
	 */
	private int id;

	/**
	 * 日志生成的时间
	 */
	private Date date;

	/**
	 * 集控器mac地址
	 */
	private String deviceMac;

	/**
	 * 节点在电力线载波网络中的地址
	 */
	private String nodeAddr;
	/**
	 * 主灯状态
	 */
	private boolean light1State;
	/**
	 * 辅灯状态
	 */
	private boolean light2State;
	/**
	 * 主灯功率百分比
	 */
	private int light1PowerPercent;
	/**
	 * 辅灯功率百分比
	 */
	private int light2PowerPercent;
	/**
	 * 主灯功率
	 */
	private int light1Power;
	/**
	 * 辅灯功率
	 */
	private int light2Power;
	/**
	 * 总功率
	 */
	private int power;

	public Record() {
		super();
	}

	public Record(Date date, Node node) {
		super();

		this.setDate(date);

		this.setDeviceMac(node.getDeviceMac());
		this.setNodeAddr(node.getNodeAddr());

		this.setLight1Power(node.getLight1Power());
		this.setLight1PowerPercent(node.getLight1PowerPercent());
		this.setLight1State(node.isLight1State());

		this.setLight2Power(node.getLight2Power());
		this.setLight2PowerPercent(node.getLight2PowerPercent());
		this.setLight2State(node.isLight2State());

		this.setPower(node.getPower());
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

	public boolean isLight1State() {
		return light1State;
	}

	public void setLight1State(boolean light1State) {
		this.light1State = light1State;
	}

	public boolean isLight2State() {
		return light2State;
	}

	public void setLight2State(boolean light2State) {
		this.light2State = light2State;
	}

	public int getLight1PowerPercent() {
		return light1PowerPercent;
	}

	public void setLight1PowerPercent(int light1PowerPercent) {
		this.light1PowerPercent = light1PowerPercent;
	}

	public int getLight2PowerPercent() {
		return light2PowerPercent;
	}

	public void setLight2PowerPercent(int light2PowerPercent) {
		this.light2PowerPercent = light2PowerPercent;
	}

	public int getLight1Power() {
		return light1Power;
	}

	public void setLight1Power(int light1Power) {
		this.light1Power = light1Power;
	}

	public int getLight2Power() {
		return light2Power;
	}

	public void setLight2Power(int light2Power) {
		this.light2Power = light2Power;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	@Override
	public String toString() {
		return "Record [id=" + id + ", time=" + date + ", deviceMac=" + deviceMac + ", nodeAddr=" + nodeAddr
				+ ", light1State=" + light1State + ", light2State=" + light2State + ", light1PowerPercent="
				+ light1PowerPercent + ", light2PowerPercent=" + light2PowerPercent + ", light1Power=" + light1Power
				+ ", light2Power=" + light2Power + ", power=" + power + "]";
	}

}
