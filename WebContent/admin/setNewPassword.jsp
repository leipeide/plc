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
					<form class="layui-form" action="" method="">
						<!-- 隐藏标签，传递用户id -->
						<input type="hidden" id="userid" value=${userid}>
						<div class="layui-form-item">
	 							<div class="layui-input-inline">
	 								<h1 style="margin-left:75px;">设置密码</h1>
	 							</div>
	 					</div>
						<div class="layui-form-item">
							<div class="layui-input-inline">
								<input type="password" name="password" id="password" placeholder="新密码"
									required lay-verify="required" autocomplete="off"
									class="passwordInput">
							</div>
						</div>
						<div class="layui-form-item">
							<div class="layui-input-inline">
								<input type="password" name="repassword" id="repassword" placeholder="确认密码"
									required lay-verify="required" autocomplete="off" onblur="checkRepassword()"
									class="passwordInput">
							</div>
						</div>
						<div class="layui-form-item">
							<button style="width:263px;margin-left:0px;" class="layui-btn">提交</button>
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
		
		//对比两次
		function checkRepassword(){
			layui.use(['jquery','layer'], function() {
				var $ = layui.$ //重点处
				,layer = layui.layer;
				
				var userid = $("#userid").val();
				var password = $("#password").val();
				var repassword = $("#repassword").val();
				if(password != repassword){
					layer.alert("两次密码不一致，请重新输入");
					$("#password").attr("value",'');//清空内容
					$("#repassword").attr("value",'');//清空内容
				}else{
					//提交函数
					 $.ajax({
	                      url:"${pageContext.request.contextPath}/setNewPasswordServlet",
	                      data:{
	                    	  'newPassword' : password,
	                    	  'id' : userid,
	                    	  
	                    	},
	                      type:"Post",
	                      dataType:"json",
	                      success:function(data){
		                    	//var error = data;
		                    	if(data == "" || data == null || data == undefined){
		                    		//跳转到对应的重新设置密码页面
		                    		layer.alert("密码设置成功，请去登录",{
					 	    		closeBtn: 0,
						 	        btn : "确定",
					 			    yes : function(index, layero) {
			                    		location.href = 
			                    			"${pageContext.request.contextPath }/admin/login.jsp";
					 		        },
					 	    	});
		                    		
		                      	}else if(data == "未获取到参数，请重新操作"){
									layer.alert("未获取到参数，密码设置失败");
									
								}else if(data == "密码设置失败"){
									layer.alert("密码设置失败，请重新操作");
									
								}else{
									
								}
	                      },
	                      error:function(data){
	                    		layer.alert("提交失败,请重新操作",{
					 	    		title:"错误",
					 	    		closeBtn: 0,
						 	        btn : "关闭",
					 			    yes : function(index, layero) {
					 		         	 layer.close(index);
					 		        },
					 	    	}); 
	                    		$("#password").attr("value",'');//清空内容
	        					$("#repassword").attr("value",'');//清空内容
	                    		
	                      }
	                  });
				}
				
			});
		}
		
	</script>
</body>
</html>