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
<title>登录界面</title>
<style>
html {
	background: url('<%=request.getContextPath()%>/admin/picture/loginbg.jpg')
		no-repeat center center fixed;
	-webkit-background-size: cover;
	-moz-background-size: cover;
	-o-background-size: cover;
	background-size: cover;
}
.main-form-div {
	margin-top: 100px;
}
.main-form-body {
	padding-top: 40px;
	padding-left: 10%;
	padding-right: 10%;
	padding-bottom: 40px;
}
</style>
</head>
<body class="layui-layout-body">
	<div class="layui-row main-form-div">
		<div class="layui-col-xs1 layui-col-sm3 layui-col-md4">
			<div class="grid-demo layui-bg-red" style="visibility:hidden">移动：1/12 | 平板：3/12 | 桌面：4/12</div>
		</div>
		<div class="layui-col-xs10 layui-col-sm6 layui-col-md4">
			<div class="grid-demo layui-bg-#F0F0F0">
				<div class="main-form-body">
					<form name="formname" id="formid" class="layui-form"
						action="${pageContext.request.contextPath }/loginServlet"
						method="post">
						<div class="layui-form-item">
							<label class="layui-form-label">用户名</label>
							<div class="layui-input-inline">
								<input type="text" name="username" value="" required
									lay-verify="required" placeholder="请输入用户名" autocomplete="off"
									class="layui-input">
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">密码</label>
							<div class="layui-input-inline">
								<input type="password" name="password" value="" required
									lay-verify="required" placeholder="请输入密码" autocomplete="off"
									class="layui-input">
							</div>
						</div>
						<div class="layui-form-item">
							<div class="layui-input-block">
								<button class="layui-btn layui-btn-primary" lay-submit
									lay-filter="loginFilter">登录</button>
<%-- 								<font color="red">${cklog}</font> --%>
								<a class="layui-btn layui-btn-primary" style="margin-left:50px;"
									href="${pageContext.request.contextPath }/registerServlet">注册</a>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="layui-col-xs1 layui-col-sm3 layui-col-md4">
			<div class="grid-demo layui-bg-red" style="visibility:hidden">移动：1/12 | 平板：3/12 |
				桌面：4/12</div>
		</div>
	</div>
	<script>
		layui.use(['element','form'], function() {
			var form = layui.form;
			var element = layui.element;
		});
	</script>
</body>
</html>