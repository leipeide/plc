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
	<form method="post"
		action="${pageContext.request.contextPath }/servlet">
		<table class="layui-table">
			<colgroup>
				<col width="150">
				<col>
			</colgroup>
			<thead>
				<tr>
					<th>集控器地址</th>
					<th>集控器名称</th>
					<th>网络状态</th>
					<th>当前节点数量</th>
					<th>最大节点数量</th>
					<th>节点主动注册</th>
					<th>广播控制</th>
				</tr>
			</thead>
			<tbody id="table_body">
			 <!--	<c:forEach items="${result.devices }"  var="device">
				<tr>
						<td>${device.deviceMac }</td>
						<td><a href="javascript:;"
							onclick="RenameDevice('${pageContext.request.contextPath }/deviceRenameFormServlet', ${device.id })">${device.deviceName }</a></td>
						<td>${device.online == true ? "online" : "offline" }</td>
						<td>${device.currentNodes }</td>
						<td>${device.maxNodes }</td>
						<td><a href="javascript:;"
							onclick="nodesRegisterControl('${pageContext.request.contextPath }/nodesRegisterFormServlet', ${device.id })">${device.nodesRegister == true ? "ON" : "OFF" }</a></td>
						<td><a href="javascript:;"
							onclick="deviceBroadcastControl('${pageContext.request.contextPath }/broadcastFormServlet', ${device.id })">广播控制</a></td>
					</tr>
				</c:forEach>    -->
			</tbody>
		</table>
	</form>
	<script type="text/javascript"
		src="${pageContext.request.contextPath }/admin/js/welcome.js"></script>
		
	<script type="text/javascript">
	function getXMLHttpRequest() {
		var xmlhttp;
		if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
			xmlhttp = new XMLHttpRequest();
		} else {// code for IE6, IE5
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		return xmlhttp;
	}
	
	function WelcomeAJAXRequest() {
		var req = getXMLHttpRequest();
		req.onreadystatechange = function() {
			if (req.readyState == 4) {// 请求成功
				if (req.status == 200) {// 服务器响应成功
					var result = JSON.parse(req.responseText);
					var devices = result.devices;
					var inner = "";
					var device;
					for (var i = 0; i < devices.length; i++) {
						device = devices[i];
						inner = inner + "<tr>\
						<td>"+device.deviceMac+"</td>\
						<td><a href=\"javascript:;\"\
							onclick=\"RenameDevice('/plc/deviceRenameFormServlet', "+device.id+")\">"+device.deviceName+"</a></td>\
						<td>";
						if (device.online == true) {
							inner = inner + "online";
						} else {
							inner = inner + "offline";
						}
						inner = inner + "</td>\
						<td>"+device.currentNodes+"</td>\
						<td>"+device.maxNodes+"</td>\
						<td><a href=\"javascript:;\"\
							onclick=\"nodesRegisterControl('/plc/nodesRegisterFormServlet', "+device.id+")\">";
							if (device.nodesRegister == true) {
								inner = inner + "ON";
							} else {
								inner = inner + "OFF";
							}
							inner = inner + "</a></td>\
						<td><a href=\"javascript:;\"\
							onclick=\"deviceBroadcastControl('/plc/broadcastFormServlet', "+device.id+")\">广播控制</a></td>\
					</tr>";
					}
					document.getElementById("table_body").innerHTML = inner;
				}
			}
		}
		req.open("get","${pageContext.request.contextPath }/refreshWelcomeServlet?username=${result.user.username}&password=${result.user.password}");// 建立一个链接
		req.send(null);// 发送请求
	}

	window.onload = function() {
		WelcomeAJAXRequest();
	}
	
	setInterval(WelcomeAJAXRequest,1000*3);
	</script>
</body>
</html>