<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/layui/css/layui.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/layui/layui.js"></script>
<title>Insert title here</title>
</head>
<body><form class="layui-form"
		action="${pageContext.request.contextPath }/delectDeviceServlet" method="post">
	  	<input type="hidden" name="userid" value="${userid}"></br>
		<table class="layui-table">
			<colgroup>
				<col>
			</colgroup>
			<thead>
				<tr>
					<td>集控器列表</td>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td><input type="checkbox" id="checkAll"  lay-skin="primary" lay-filter="allChoose">全选/取消</td>
				</tr>
				<c:forEach items="${devices }" var="device">
				<tr>
					<td><input type="checkbox" name="check" title="${device.deviceName}" 
      								value="${device.deviceName}" lay-skin="primary" lay-filter="oneChoose">						
					</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		
		<div class="layui-form-item">
			<div class="layui-input-block">
				<button class="layui-btn" lay-submit lay-filter="*">立即提交</button>
			</div>
		</div>
   </form>
   <script type="text/javascript"
		src="${pageContext.request.contextPath }/admin/js/delectForm.js">
   </script>
</body>
</html>