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
<title>测试界面</title>
</head>
<body class="layui-layout-body">
<%-- ${pageContext.request.contextPath }/warnningMessageDeleteServlet --%>
<form action="" method="post">
 <button id="delBt" class="layui-btn layui-btn-sm" lay-submit lay-filter="delBt">删除按钮</button>
<!-- <div> -->
<!-- 	<button class="layui-btn">删除</button> -->
<!-- </div> -->
<div>
	<table class="layui-table">
		<colgroup>
    		<col width="240"><col>
  		</colgroup>
		<thead>
			<tr>
				<th>报警信息id</th>
				<th>集控器地址</th>
<!-- 				<th>集控器地址&nbsp;& -->
<!-- 					<input type="checkbox" id="chAll" onclick="checkOrCancelAll();"</th> -->
				<th>节点地址</th>
				<th>报警原因</th>
				<th>报警时间</th>
		    </tr>
		</thead>
		<tbody id = "tbody">
				<!-- 插入报警信息表格，动态刷新 -->
		</tbody>
	</table>
</div>
</form> 
<script type="text/javascript">
layui.use('form', function() {
	var form = layui.form;
	//监听提交
	  form.on('submit(delBt)', function(data){
		  delWM('${pageContext.request.contextPath }/getWmServlet',${userid});
	    return false;
	  });
});	
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
function warnningAJAXRequest() {
	//1.创建请求
	var req = getXMLHttpRequest();
	//4.服务器处理
	req.onreadystatechange = function() {
		if (req.readyState == 4) {// 请求成功
			if (req.status == 200) {// 服务器响应成功,动态获取报警信息表格
				var json = JSON.parse(req.responseText);
				var alarmList = json.alarmList;
				var alarm;
				var msTime
				var inner = "";
				//从后台获取对象
				for(var i = 0; i < alarmList.length; i++){//for循环信息对象，生成表格
					alarm = alarmList[i];
					//alert(alarm.deviceMac);
					msTime = new Date(alarm.date);
					dateTime = msTime.toLocaleString();
					inner = inner + "<tr><td>" + alarm.id + "</td>\
					<td>" + alarm.deviceMac + "</td>\
					<td>" + alarm.nodeAddr + "</td>\
					<td>";
					if (alarm.type == 1) {
						inner = inner + "节点过功率，请及时处理";
					} if(alarm.type == 2) {
						inner = inner + "集控器连接失败，请及时处理";
					}
					inner = inner + "</td>\
					<td>" + dateTime + "</td>\
				</tr>";
				}
				document.getElementById("tbody").innerHTML = inner;
			}
		}
	}
	//1.建立链接
	req.open("get","${pageContext.request.contextPath }/getWarnningMessageServlet?userid=${userid}");
	//2.发送请求
	req.send(null);
}

window.onload = function() {
	warnningAJAXRequest();
}
setInterval(warnningAJAXRequest,1000*2);
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/admin/js/warnningMessage.js"></script> 
</body>
</html>