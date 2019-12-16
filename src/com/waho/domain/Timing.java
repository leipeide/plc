package com.waho.domain;

import java.sql.Time;

public class Timing {
	
	/**
	 * 主键，定时id
	 */
	private int id;
	/**
	 * 定时计划id
	 */
	private int planid;
	/**
	 * 定时计划名称
	 */
	private String planName;
	/**
	 * 集控器mac地址
	 */
	private String deviceMac;
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
	/**
	 * 指令时间
	 */
	private Time time;
	/**
	 * 执行标志位
	 */
	private boolean flag;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getPlanid() {
		return planid;
	}
	public void setPlanid(int planid) {
		this.planid = planid;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public String getDeviceMac() {
		return deviceMac;
	}
	public void setDeviceMac(String deviceMac) {
		this.deviceMac = deviceMac;
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
	public Time getTime() {
		return time;
	}
	public void setTime(Time time) {
		this.time = time;
	}
	
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	@Override
	public String toString() {
		return "Timing [id=" + id + ", planid=" + planid + ", planName=" + planName + ", deviceMac=" + deviceMac
				+ ", light1State=" + light1State + ", light2State=" + light2State + ", light1PowerPercent="
				+ light1PowerPercent + ", light2PowerPercent=" + light2PowerPercent + ", light1Power=" + light1Power
				+ ", light2Power=" + light2Power + ", power=" + power + ", time=" + time + ", flag=" + flag + "]";
	}
	
	
}
