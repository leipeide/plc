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
<title></title>
<style type="text/css">
.delBt{
	float: left;
}
.deviceMac {
	float: left;
	width: 200px;
	height: 50px;
	padding:0px,5px;
	margin-left:5px;
/* 	border:3px solid #000 */
}

.nodeAddr {
	float: left;
	width: 200px;
	height: 50px;
    padding:0px,5px;
    margin-left:5px;
/* 	border:3px solid #000; */
}

.type {
	float: left;
	width: 200px;
	height: 50px;
	padding:0px,5px;
	margin-left:5px;
/* 	border:3px solid #000; */
}

.date {
	float: left;
	width: 200px;
	height: 50px;
	padding:0px,5px;
	margin-left:5px;
/* 	border:3px solid #000; */
}

.submit {
	float: left;
	width: 50px;
	height: 50px;
	padding:0px,5px;
	margin-left:5px;
/* 	border:3px solid #000 */
}
</style>
</head>
<body>
 <%
     Object message = request.getAttribute("message");
     if(message!=null && !"".equals(message)){
  %>
      <script type="text/javascript">
      		layui.use('layer', function() {
				var layer = layui.layer;
				layer.alert('<%=message%>');    
			});
<%--          alert("<%=message%>"); --%>
      </script>
  <%} %>

 	<form id ="form" class="layui-form" action="" method="post">
<!-- 		<button id="delBt" class="layui-btn layui-btn-sm" lay-submit lay-filter="delBt">删除报警</button> -->
        <div class="nav">
        	<div class="delBt">
				<button id="delBt" class="layui-btn layui-btn-sm" lay-submit lay-filter="delBt">删除报警</button></div>
			<div class="deviceMac">
			<label class="layui-form-label">集控器地址</label>
			<div class="layui-input-block">
				<select id="selectDeviceMac" name="selectDeviceMac" lay-verify="" lay-filter="filter" >
						<option value="">请选择</option> 
					<c:forEach items="${alarmMap.deviceMacList}" var="deviceMacList">
						<option value="${deviceMacList}">${deviceMacList}</option>
					</c:forEach>
				</select>
			</div></div>
			
	  <div class="nodeAddr">
	  			<label class="layui-form-label">节点地址</label>
				<div class="layui-input-block">
				<select id="selectNodeAddr" name="selectNodeAddr" lay-verify="" lay-filter="filter" >
 						<option value="">请选择</option>
					<c:forEach items="${alarmMap.nodeAddrList}" var="nodeAddrList">
						<option value="${nodeAddrList}">${nodeAddrList}</option>
					</c:forEach>
				</select>
			</div></div>
			
			<div class="type">
				<label class="layui-form-label">类型</label>
				<div class="layui-input-block">
				<select id="selectType" name="selectType" lay-verify="" lay-filter="filter" >
 						<option value="">请选择</option>
						<option value="节点过功率，请及时处理">节点过功率，请及时处理</option>
						<option value="集控器连接失败，请及时处理">集控器连接失败，请及时处理</option>
				</select>
			</div></div>
			
			<div class="date">
				<label class="layui-form-label">时间</label>
			 <div class="layui-input-block">
				<select id="selectDate" name="selectDate"  lay-verify="" lay-filter="filter">
						<option value="">请选择</option>
						<option value="倒序">倒序</option>
						<option value="正序">正序</option>
				</select>
			</div></div>
			
			<div class="submit">
				<button id="submit" class="layui-btn layui-btn-sm" lay-submit lay-filter="submit">提交</button></div>
			</div>		
		</div>
			
		<div>
			<table class="layui-table">
				<colgroup>
					<col width="50">
					<col width="240">
					<col width="200">
					<col width="240">
					<col width="240">
				</colgroup>
				<thead>
					<tr>
<!-- 						<th>报警信息id</th> -->
						<th><input type="checkbox" id="checkAll" lay-skin="primary" lay-filter="allChoose"></th>
						<th>集控器地址</th>
						<th>节点地址</th>
						<th>报警原因</th>
<%-- 						<th>报警时间&nbsp;&nbsp;<a href="${pageContext.request.contextPath  }/Servlet"><i class="layui-icon layui-icon-up"></i></a></th> --%>
 						<th>报警时间</th>
					</tr>
				</thead>
				<tbody id="tbody">
					<c:forEach items="${alarmMap.alarmList}"  var="alarm">
						<tr>
<%-- 							<td>${alarm.id}</td> --%>
							<td><input type="checkbox" name="id" value="${alarm.id}" lay-skin="primary"></td>
							<td>${alarm.deviceMac}</td>
							<td>${alarm.nodeAddr}</td>
							<c:if test="${alarm.type == 1}">
									<td>节点过功率，请及时处理</td>
							</c:if>
							<c:if test="${alarm.type == 2}">
									<td>集控器连接失败，请及时处理</td>
							</c:if>
							<td>${alarm.date}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</form>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/admin/js/warnningMessage.js"></script>
	<script>
	layui.use('form', function() {
		var form = layui.form;
		form.on('checkbox(allChoose)', function(data) {
			ckAll();
			form.render();//不可删除
		});
		form.on('submit(delBt)', function(data){
			 delWM('${pageContext.request.contextPath }/warnningMessageDeleteServlet',${userid});
			 return false;
			});
		form.on('select(filter)', function(data){
			selectMac();
			form.render();//不可删除
			return false;
		});
		form.on('submit(submit)', function(data){
			 submitFun('${pageContext.request.contextPath }/serachWMServlet?userid=${userid}');
			 return false;
			});
	});
	</script>
<!-- <script type="text/javascript">
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
					<td>"+ dateTime +"</td>\
				</tr>";
				}
				//document.getElementById("tbody").innerHTML = inner;
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

	<script type="text/javascript"
		src="${pageContext.request.contextPath}/admin/js/warnningMessage.js"></script>
	<script>
	layui.use('form', function() {
		var form = layui.form;
		//监听提交
		form.on('checkbox(allChoose)', function(data) {
			ckAll();
			form.render();
		});
		form.on('select()', function(data) {
			return false;
		});
	});
	</script>	 -->	
</body>
</html>