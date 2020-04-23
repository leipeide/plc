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
	padding-top: 5%;
	padding-left: 24%;
	padding-right: 3%;
	padding-bottom: 40px;
	
}
.usernameInput{ 
	background: url('<%=request.getContextPath()%>/admin/picture/蓝色用户.png')no-repeat;
	background-size: 25px 25px;
 	background-position: 5px 4px; 
 	background-color: #ffffff;
 	padding:8px 10px 8px 40px; 
	width:210px;
	height:20px;
}

.passwordInput{ 
	background: url('<%=request.getContextPath()%>/admin/picture/蓝色密码.png')no-repeat;
	background-size: 25px 25px;
 	background-position: 5px 4px; 
 	background-color: #ffffff;
 	padding:8px 10px 8px 40px; 
	width:210px;
	height:20px;
}
.repasswordInput{ 
	background: url('<%=request.getContextPath()%>/admin/picture/蓝色密码.png')no-repeat;
	background-size: 25px 25px;
 	background-position: 5px 4px; 
 	background-color: #ffffff;
 	padding:8px 10px 8px 40px; 
	width:210px;
	height:20px;
}

.emailInput{ 
	background: url('<%=request.getContextPath()%>/admin/picture/蓝色邮箱.png')no-repeat;
	background-size: 25px 25px;
 	background-position: 5px 4px; 
 	background-color: #ffffff;
 	padding:8px 10px 8px 40px; 
	width:210px;
	height:20px;
}
.phoneInput{
	background: url('<%=request.getContextPath()%>/admin/picture/蓝色手机.png')no-repeat;
	background-size: 25px 25px;
 	background-position: 5px 4px; 
 	background-color: #ffffff;
 	padding:8px 10px 8px 40px; 
	width:210px;
	height:20px;
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
					<form class="layui-form"
						action="${pageContext.request.contextPath }/register1Servlet"
						method="post">
						<div class="layui-form-item" style="margin-left:5%;">
							<font color="red">${RegistFailed}</font>
						</div>
						<div class="layui-form-item">
							<div class="layui-input-inline">
								<input type="text" name="username" placeholder="用户名"
									required lay-verify="required" autocomplete="off"
									class="usernameInput">
							</div>
						</div>
						<div class="layui-form-item">
							<div class="layui-input-inline">
								<input type="password" name="password" placeholder="密码"
									required lay-verify="required" autocomplete="off"
									class="passwordInput">
							</div>
						</div>
						<div class="layui-form-item">
							<div class="layui-input-inline">
								<input type="password" name="passwordrep" required
									lay-verify="required" placeholder="确认密码" autocomplete="off"
									class="repasswordInput">
							</div>
						</div>
						<div class="layui-form-item">
							<div class="layui-input-inline">
								<input type="text" name="email" required lay-verify="email"
									placeholder="邮箱" autocomplete="off"
									class="emailInput">
							</div>
						</div>
						<div class="layui-form-item">
							<div class="layui-input-inline">
								<input type="text" name="phone" required lay-verify="phone"
									placeholder="手机" autocomplete="off"
									class="phoneInput">
							</div>
						</div>
						<div class="layui-form-item">
							<button name="regname" class="layui-btn" style="width:263px;"
								lay-submit lay-filter="registerFilter">注册</button>
							<font color="red">${regtext}</font>
						</div>
						<div class="layui-form-item">
							<div class="layui-input-block" style="text-align: right">
								已注册，请<a href="${pageContext.request.contextPath }/login1Servlet"><font
									size="5" color="red">登录</font></a>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="layui-col-xs1 layui-col-sm3 layui-col-md4">
			<div class="grid-demo layui-bg-blue" style="visibility:hidden">移动：1/12 | 平板：3/12 |
				桌面：4/12</div>
		</div>
	</div>
<%-- 	<script type="text/javascript" src="${pageContext.request.contextPath }/admin/js/register.js"></script> --%>
	<script>
		layui.use('form', function() {
			var form = layui.form;
		});
	</script>
</body>
</html>