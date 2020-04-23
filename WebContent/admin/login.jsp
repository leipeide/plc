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
	padding-top: 18%;
	padding-left: 24%;
	padding-right: 10%;
	padding-bottom: 40px;
/* 	border:1px solid #000; */
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
							<button style="width:263px;margin-left:0px;" class="layui-btn">登录</button>
							<div style="margin-top:20px;"> 
								<a style="float:left;margin-left:30px;color:red"
									href="${pageContext.request.contextPath }/registerServlet">注册</a>
								<a style="float:left;margin-left:130px;color:red"
									href="javascript:;" onclick="goToFindPasswordPage()">忘记密码？</a>
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
		
		//进入找回密码页面
		function goToFindPasswordPage(){
			location.href = "${pageContext.request.contextPath }/admin/findPasswordForm.jsp";
			
		}
	</script>
</body>
</html>