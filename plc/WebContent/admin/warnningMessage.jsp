<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" >
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/layui/css/layui.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/layui/layui.js"></script>
<title>测试界面</title>
</head>
<body class="layui-layout-body">
<div>
	<table  class="layui-table">
		<colgroup>
    		<col width="150"><col>
  		</colgroup>
		<thead>
			<tr>
				<th>集控器名称</td>
				<th>节点名称</td>
				<th>节点功率</td>
				<th>最后一次通讯时间</td>
				<th style="width:100">删除报警信息&nbsp;&nbsp;
					<input type="checkbox" id="chAll" onclick="checkOrCancelAll();"></td>
		    </tr>
		</thead>
		<tbody id = "tbody">
<%-- 			<c:forEach items="result" var = warnningM> --%>
				<tr>
					<td>DeviceName</td>
					<td>NodeName</td>
					<td>NodePower</td>
					<td>ComTime</td>
					<td>删除&nbsp;&nbsp;<input type="checkbox" name="check"></td>
				</tr>
				<tr>
					<td>Device1Name</td>
					<td>Node1Name</td>
					<td>Node1Power</td>
					<td>ComTime1</td>
					<td>删除&nbsp;&nbsp;<input type="checkbox" name="check"></td>
				</tr>
<%-- 			</c:forEach>  --%>
		</tbody>
	</table>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/admin/js/warnningMessage.js"></script> 
</body>
</html>