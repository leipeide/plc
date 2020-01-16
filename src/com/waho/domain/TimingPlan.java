package com.waho.domain;

public class TimingPlan {
	/**
	 * 主键，定时计划id
	 */
	private int id;
	/**
	 * 定时计划名称
	 */
	private String name;
	/**
	 * 用户id
	 */
	private int userid;
	/**
	 * 集控器mac地址
	 */
	private String deviceMac;
	/**
	 *开始月份
	 */
	private int startDate;
	/**
	 * 结束月份
	 */
	private int endDate;
	/**
	 * 执行状态
	 */
	private Boolean doState;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getDeviceMac() {
		return deviceMac;
	}
	public void setDeviceMac(String deviceMac) {
		this.deviceMac = deviceMac;
	}
	public int getStartDate() {
		return startDate;
	}
	public void setStartDate(int i) {
		this.startDate = i;
	}
	public int getEndDate() {
		return endDate;
	}
	public void setEndDate(int endDate) {
		this.endDate = endDate;
	}
	
	public Boolean getDoState() {
		return doState;
	}
	public void setDoState(Boolean doState) {
		this.doState = doState;
	}
	
	
	@Override
	public String toString() {
		return "TimingPlan [id=" + id + ", name=" + name + ", userid=" + userid + ", deviceMac=" + deviceMac
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", doState=" + doState + "]";
	}
	
}
