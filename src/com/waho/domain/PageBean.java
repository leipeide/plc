package com.waho.domain;

import java.util.List;

public class PageBean {
	/**
	 * 每页的节点数量
	 */
	private Integer pageSize;
	/**
	 * 当前页面
	 */
	private int currentPage;
	/**
	 * 节点总数
	 */
	private int count;
	/**
	 * 总页数量
	 */
	private int totalPage;
	/**
	 * 每页的节点集合
	 */
	private List<Node>nodes;
	/**
	 * 每页开始数据
	 */
	private int star;
	
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public List<Node> getNodes() {
		return nodes;
	}
	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}
	public int getStar() {
		return star;
	}
	public void setStar(int star) {
		this.star = star;
	}
	
}

