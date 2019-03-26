<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
		action="${pageContext.request.contextPath }/addDeviceServlet" method="post">
        <input type="hidden" name="userid" value="${userid }">
   		<div class="layui-form-item">
			<label class="layui-form-label" style="width:100px;">集控器Mac地址</label>
			<div class="layui-input-block" style="width: 100px;">
				<input type="text" name="deviceMac" class="layui-input">
			</div>
		</div>
   		<div class="layui-form-item">
			<div class="layui-input-block">
				<button class="layui-btn layui-btn-sm" >立即提交</button>
			</div>
		</div>
   </form>
</body>
</html>