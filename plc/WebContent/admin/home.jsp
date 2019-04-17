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
<script type="text/javascript"
	src="${pageContext.request.contextPath }/admin/js/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/admin/js/home.js"></script>
<title>测试界面</title>
</head> 
<body class="layui-layout-body">
	<div class="layui-layout layui-layout-admin">
		<div class="layui-header">
			<div class="layui-logo">雷培德PLC灯控系统</div>
			<!-- 头部区域（可配合layui已有的水平导航） -->
			<ul class="layui-nav layui-layout-left">
				<li class="layui-nav-item"><a
					href="javascript:;" onclick="refresh()">首页</a></li>
				<li class="layui-nav-item"><a                                       
					href="${pageContext.request.contextPath}/warnningMessageServlet?userid=${result.user.id}" target="fname">报警<span class="layui-badge" id="warnning"></span></a></li>
			</ul>
			<ul class="layui-nav layui-layout-right">
				<li class="layui-nav-item"><a href="javascript:;"> <img
						src="http://t.cn/RCzsdCq" class="layui-nav-img">
						${result.user.username }</a>
					<dl id=demo class="layui-nav-child">
						<dd>
						<a href="javascript:;" onclick="um(${result.user.id})">个人资料</a>
						</dd>
					</dl></li>
				<li class="layui-nav-item"><a href="${pageContext.request.contextPath }/login1Servlet">退出登录</a></li>
			</ul>
		</div>
		<div class="layui-side layui-bg-black">
			<div class="layui-side-scroll">
				<!-- 左侧导航区域（可配合layui已有的垂直导航） -->
				<ul class="layui-nav layui-nav-tree" lay-filter="test">
					<li class="layui-nav-item layui-nav-itemed"><a class=""
						href="javascript:;">所有集控器</a>
						<dl class="layui-nav-child">
							<c:forEach items="${result.devices}" var="device">
								<dd>
									<a href="javascript:;"
									onclick="deviceOnclick('${pageContext.request.contextPath }/getNodesServlet',${device.id })">${device.deviceName }</a>
								</dd>
							</c:forEach>
						</dl></li>
					<li class="layui-nav-item">
						<a href="javascript:;"
							onclick="addDevice('${pageContext.request.contextPath }/inputdeviceservlet',${result.user.id })">添加集控器</a>
					</li>
					
					<li class="layui-nav-item">
						<a href="javascript:;"
							onclick="removeDevice('${pageContext.request.contextPath }/delectDevicesFormServlet',${result.user.id })">删除集控器</a>
					</li>
				</ul>
			</div>
		</div>
		
		<div class="layui-body">
			<!-- 内容主体区域 -->
			<div style="padding: 15px;" id="body-div">
				<iframe style="min-height: 500px" name="fname" frameborder="0"
					scrolling="no" width="100%"
					src="${pageContext.request.contextPath }/welcomeServlet?username=${result.user.username}&password=${result.user.password}"
					class="body-frame"></iframe>
		     </div>
	 	</div>

		<div class="layui-footer">
			<!-- 底部固定区域 -->
			© 雷培德PLC灯控系统
		</div>
	</div>
	
<script>
 	//A.创建XMLHttpRequest对象
 	function getXMLHttpRequest() {
 		var xmlhttp;
 		if (window.XMLHttpRequest) {
 			// code for IE7+,Firefox,Chrome,Opera,Safari
 			xmlhttp = new XMLHttpRequest();
 		} else {
 			// code for IE5,IE6,
 			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
 		}
 		return xmlhttp;
 	}
 	//B.创建回调函数，根据响应动态更新页面
 	function AJAXRequest() {
 		//1.创建请求
 		var req = getXMLHttpRequest();
 		//4.服务器处理
 		req.onreadystatechange = function() {
 			if (req.readyState == 4) {// 请求成功
 				if (req.status == 200) {// 服务器响应成功,动态获取报警信息表格
 						var warnningNum = JSON.parse(req.responseText);
 						document.getElementById("warnning").innerHTML = warnningNum;
 				}
 			}
 		}
 		//1.建立链接
 		req.open("get","${pageContext.request.contextPath }/getWarnningMessageServlet1?userid=${result.user.id}");
 		//2.发送请求
 		req.send(null);
 	}
 	window.onload = function() {
 		AJAXRequest();
 	}
 	setInterval(AJAXRequest,1000*2);
 </script>
</body>
</html>