<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/layui/css/layui.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/layui/layui.js"></script>
<title>Insert title here</title>
</head>
<body>
	<div><form class="layui-form" action="${pageContext.request.contextPath}/delNodesServlet?deviceid=${deviceid}" method="post">
		<table class="layui-table">
			<colgroup>
				<col>
			</colgroup>
			<thead>
				<tr>
					<td>节点列表</td>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td><input type="checkbox" id="checkAll"  lay-skin="primary" lay-filter="allChoose">全选/取消</td>
				</tr>
				<c:forEach items="${nodes}" var="node">
				<tr>
					<td><input type="checkbox" name="nodeAddr" title="${node.nodeAddr}" 
								value="${node.nodeAddr}" lay-skin="primary"></td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		  	      
   		<div class="layui-form-item">
			<div class="layui-input-block">
				<button class="layui-btn layui-btn-sm">立即提交</button>
			</div></div> 	
	</form></div> 
 <script type="text/javascript" 
 			src="${pageContext.request.contextPath}/admin/js/delNodes.js">

</script >
</body>
</html>
