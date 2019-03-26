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
<script type="text/javascript"
	src="${pageContext.request.contextPath }/admin/js/jquery.min.js"></script>
<title>注册界面</title>
<style>
.main-form-div {
	margin-top: 100px;
}

.main-form-body {
	padding-top: 40px;
	padding-left: 3%;
	padding-right: 3%;
	padding-bottom: 40px;
}
</style>
</head>
<body class="layui-layout-body">
	<div class="layui-row main-form-div">
		<div class="layui-col-xs1 layui-col-sm3 layui-col-md4">
			<div class="grid-demo layui-bg-red">移动：1/12 | 平板：3/12 | 桌面：4/12</div>
		</div>
		<div class="layui-col-xs10 layui-col-sm6 layui-col-md4">
			<div class="grid-demo layui-bg-gray">
				<div class="main-form-body">
					<form class="layui-form" action="${pageContext.request.contextPath }/register1Servlet" method="post">
						<div class="layui-form-item">
							<label class="layui-form-label">用户名</label>
							<div class="layui-input-inline">
								<input type="text" name="username" value="" required
									lay-verify="required" placeholder="请输入用户名" autocomplete="off"
									class="layui-input">
									<font color="red">${ckname}</font>	
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">密码</label>
							<div class="layui-input-inline">
								<input type="password" name="password" value=""  required
									lay-verify="required" placeholder="请输入密码" autocomplete="off"
									class="layui-input"> 
								<font color="red">${ckpassword}	</font>	
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">确认密码</label>
							<div class="layui-input-inline">
								<input type="password" name="passwordrep" value="" required
									lay-verify="required" placeholder="请输入密码" autocomplete="off"
									class="layui-input">
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">邮箱</label>
							<div class="layui-input-inline">
								<input type="text" name="email" value="" required lay-verify="required"
									placeholder="请输入邮箱地址" autocomplete="off" class="layui-input">
							</div> <font color="red">${ckemail}</font>
						</div>
						<div class="layui-form-item">
							<div class="layui-input-block">
								<button name=" regname"class="layui-btn layui-btn-primary" lay-submit
									lay-filter="registerFilter" >注册</button>  <font color="red">${regtext}</font>
							</div>
						</div>
						<div class="layui-form-item">
							<div class="layui-input-block" style="text-align:right">
								已注册，请<a href="${pageContext.request.contextPath }/login1Servlet" ><font size="5"  color="red">登录</font></a>
							</div>
						</div> 
					</form>
				</div>
			</div>
		</div>
		<div class="layui-col-xs1 layui-col-sm3 layui-col-md4">
			<div class="grid-demo layui-bg-blue">移动：1/12 | 平板：3/12 |
				桌面：4/12</div>
		</div>
	</div>
	<script type="text/javascript"
		src="${pageContext.request.contextPath }/admin/js/register.js"></script>
 	<script>
		//Demo
		layui.use('form', function() {
			var form = layui.form;

			//监听提交
			//form.on('submit(registerFilter)', function(data) {
			//	layer.msg(JSON.stringify(data.field));
			//	return false;
			//});
		});
	</script>
</body>
</html>