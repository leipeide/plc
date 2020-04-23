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
	padding-top: 8%;
	padding-left: 24%;
	padding-right: 10%;
	padding-bottom: 40px;
/* 	border:1px solid #000; */
}

.findEmailInput{ 
	background: url('<%=request.getContextPath()%>/admin/picture/蓝色邮箱.png')no-repeat;
	background-size: 25px 25px;
 	background-position: 5px 4px; 
 	background-color: #ffffff;
 	padding:8px 10px 8px 40px; 
	width:210px;
	height:20px;
}

.CheckInput{ 
	background: url('<%=request.getContextPath()%>/admin/picture/验证码.png')no-repeat;
	background-size: 25px 25px;
 	background-position: 5px 4px; 
 	background-color: #ffffff;
 	padding:8px 10px 8px 40px; 
    width:100px;
	height:20px;
    float:left;
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
					<form class="layui-form" action="" method=""> 
	 						<div class="layui-form-item">
	 							<div class="layui-input-inline">
	 								<h1 style="margin-left:75px;">找回密码</h1>
	 							</div>
	 						</div>
							<div class="layui-form-item" style="margin-top:40px;">
								<div class="layui-input-inline">
									<input type="text" id="findEmail" class="findEmailInput" 
									 placeholder="请输入注册时的邮箱" autocomplete="off" 
									 required lay-verify="email">
								</div>
							</div>
							<div class="layui-form-item">
								<input type="text" id="Check" class="CheckInput" autocomplete="off" 
									 placeholder="验证码" autocomplete="off" required lay-verify="required">
								<a id="getCheckCode" class="layui-btn" 
									 placeholder="验证码" autocomplete="off" 
									 onclick="getCheckCodeFunction()"
									 style="float:left;margin-left:10px;width:100px;">获取验证码</a>
							</div>	
							<div class="layui-form-item">
								<div style="margin-top:20px;"> 
									<a href="javascript:;" class="layui-btn" style="width:263px;" 
										onclick="findPasswordFunction()">找回密码</a>
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
		//判断邮箱地址合法正则
		var emailReg = /^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+/;
			
		layui.use(['element','form'], function() {
			var form = layui.form;
			var element = layui.element;
		});
		
		
		//获取验证码
		function getCheckCodeFunction(){
			layui.use(['jquery','layer'], function() {
				var $ = layui.$ //重点处
				,layer = layui.layer;
				
				var emailVal = $("#findEmail").val();
				if(!emailReg.test(emailVal)){
					layer.alert("请输入正确的邮箱");
		
				}else{
					 $.ajax({
	                      url:"${pageContext.request.contextPath}/sendVerificationCodeServlet",
	                      data:{
	                    	  'email' : emailVal,
	                    	  
	                    	},
	                      type:"Post",
	                      dataType:"json",
	                      success:function(data){
		                    	var error = data.error;
		                    	if(error == "" || error == null || error == undefined){
		                    		layer.msg("验证码已发送，请注意查收");
		                    		
		                      	}else if(error == "您今天的次数已超过4次，请明天再操作"){
									layer.alert("今天的操作次数已达上线");
									
								}else if(error == "未查找到用户，该邮箱未注册用户"){
									layer.alert("未查找到该邮箱注册的用户");
									
								}else{
									
								}
	                      },
	                      error:function(data){
	                    		layer.alert("获取失败,请重新操作",{
					 	    		title:"错误",
					 	    		closeBtn: 0,
						 	        btn : "关闭",
					 			    yes : function(index, layero) {
					 		         	 layer.close(index);
					 		        },
					 	    	}); 
	                      }
	                  });
				}
			});
				
		}
		
		
		//找回密码
		function findPasswordFunction(){
			layui.use(['jquery','layer'], function() {
				var $ = layui.$ //重点处
				,layer = layui.layer;
				
				var emailVal = $("#findEmail").val();
				var verCodeVal = $("#Check").val();
				
				if(!emailReg.test(emailVal)){
					layer.alert("请输入正确的邮箱");
					
				} else if(verCodeVal== "" || verCodeVal  == null){
					layer.alert("请输入验证码");
					
				}else{
					 $.ajax({
	                      url:"${pageContext.request.contextPath}/findPasswordServlet",
	                      data:{
	                    	  'email' : emailVal,
	                    	  'verCode' : verCodeVal
	                    	},
	                      type:"Post",
	                      dataType:"json",
	                      success:function(data){
		                    	var error = data.error;
		                    	if(error == "" || error == null || error == undefined){
		                    		//跳转到对应的重新设置密码页面
		                    		var admin = data.user;
		                    		var id = admin.id;
		                    		location.href="${pageContext.request.contextPath}/setPasswordFormServlet?userid="+id;
		                    		
		                      	}else if(error == "验证码错误"){
									layer.alert("验证码错误");
									
								}else if(error == "未查找到用户，该邮箱未注册用户"){
									layer.alert("未查找到用户，该邮箱未注册用户");
									
								}else{
									
								}
	                      },
	                      error:function(data){
	                    		layer.alert("获取失败,请重新操作",{
					 	    		title:"错误",
					 	    		closeBtn: 0,
						 	        btn : "关闭",
					 			    yes : function(index, layero) {
					 		         	 layer.close(index);
					 		        },
					 	    	}); 
	                      }
	                  });
				}
			});
			
		}
		
	</script>
</body>
</html>