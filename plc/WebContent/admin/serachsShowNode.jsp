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
<title>Insert title here</title> 
</head>
<body>
	<form  action="" method="post">   
<!--  		<div class="layui-btn-container"> -->
<!--   			<button id="returnBt" class="layui-btn layui-btn-sm" onclick="backNextPage()">返回节点页面</button> -->
<!--    		</div> -->
		<table class="layui-table">
			<colgroup>
				<col width="150">
				<col>
			</colgroup>
			<thead>
				<tr>
					<th>节点地址</th>
					<th>节点名称</th>
					<th>主灯状态</th>
					<th>主灯亮度</th>
					<th>辅灯状态</th>
					<th>辅灯亮度</th>
					<th>节点功率</th>
					<th>单灯控制</th>
					<th>更新数据</th>
				</tr>
			</thead>
			<tbody id="table_body">
					<tr>
						<td>${node.nodeAddr }&nbsp;&nbsp;<a href="${pageContext.request.contextPath }/nodeMessageChartServlet" ><i class="layui-icon layui-icon-chart"></i></a>
						</td>
						<td><a href="javascript:;"
							onclick="reName('${pageContext.request.contextPath }/nodeRenameFormServlet',${node.id})">${node.nodeName }</a></td>
						<td>${node.light1State == true ? "开" : "关" }</td>
						<td>${node.light1PowerPercent }</td>
						<td>${node.light2State == true ? "开" : "关" }</td>
						<td>${node.light2PowerPercent }</td>
						<td>${node.power }W</td>
						<td><a href="javascript:;"
							onclick="nodeControl('${pageContext.request.contextPath }/nodeFormServlet', ${node.id }, ${node.light1State }, ${node.light1PowerPercent }, ${node.light2State }, ${node.light2PowerPercent })">单灯控制</a></td>
						<td><a href="javascript:;"
							onclick="nodeRefresh('${pageContext.request.contextPath }/nodeRefreshServlet?nodeid=${node.id }')">刷新</a></td>
					</tr>
			</tbody>
		</table>
    </form>
	<script type="text/javascript">
			function backNextPage(){
				window.history.back(-1);
			
			}
	</script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/admin/js/nodes.js">
	</script>
</body>
</html>