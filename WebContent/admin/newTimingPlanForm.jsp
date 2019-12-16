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
<style type="text/css">
.planName {
	height: 35px;
	background-color: #D1E1F0;
	padding: 5px;
	padding-buttom:0px;
}

.inputPlanName {
	background-color: #D1E1F0;
	width: 680px;
	height: 35px;
	/* 	border: 1px solid #000; */
}

.body {
	height: 350px;
	width: 684px;
	margin-top: 0px;
	padding:3px;
	border: 5px solid #D1E1F0;
}

.label {
	height: 20px;
	width: 655px;
	padding: 5px;
/* 	border: 1px solid #000; */
}

.label1 {
	float: left;
	height: 20px;
	width: 200px;
/* 	border: 1px solid red; */
}

.label2 {
	float: right;
	height: 20px;
	width: 300px;
	margin-right: 100px;
/* 	border: 1px solid red; */
}

.setting {
	height: 250px;
	width: 665px;
/* 	border: 1px solid #000; */
}

.area1 {
	float: left;
	height: 240px;
	width: 200px;
/* 	border: 1px solid red; */
}

.area2 {
	float: right;
	height: 240px;
	width: 300px;
	margin-right: 110px;
/* 	border: 1px solid red; */
}

.button {
	float: right;
	margin: 5px;
	padding: 5px;
	padding-right: 20px;
/* 	border: 1px solid #000; */
}
</style>
</head>
<body>
	<%
	//搜索节点错误返回节点页面提示错误信息
     Object message = request.getAttribute("message");
     if(message!=null && !"".equals(message)){
  %>
	<script type="text/javascript">
      		layui.use('layer', function() {
				var layer = layui.layer;
				layer.alert('<%=message%>');  
			});
      </script>
	<%} %>
	
	<form class="layui-form">
		<div class="planName">
			<input id="inputPlanName" class="inputPlanName" type="text" 
				placeholder="请输入计划名称" autocomplete="off">
		</div>

		<div class="body">
			<div class="label">
				<label class="label1">选择集控器</label> <label class="label2">选择时间范围</label>
			</div>
			<div class="setting">
				<div class="area1">
<!-- 					required lay-verify="required" -->
					<select id="select" class="select"  lay-filter="select">
 							<option value="">请选择</option>
						<c:forEach items="${devices}" var="device">
							<option value="${device.deviceMac}">${device.deviceMac}</option>
						</c:forEach>
					</select>
				</div>
				<div class="area2">
					<div class="layui-input-inline" id="calendarArea">
						<input class="layui-input" id="calendar" type="text" style="width:200px;"
							placeholder="请选择">
					</div>
					<!-- 				</div> -->
				</div>
			</div>

			<div class="button">
				<div class="layui-btn-group">
					<button class="layui-btn" lay-submit lay-filter="cancelBtn">取消</button>
					<button class="layui-btn" lay-submit lay-filter="submitBtn">提交</button>
				</div>
			</div>
		</div>
	</form>
	<script type="text/javascript" 
		src="${pageContext.request.contextPath }/admin/js/newTimingPlanForm.js"></script>
	<script>
		layui.use(["form","laydate","layer"], function() {
			var form = layui.form;
			var layer = layui.layer;
			//1.插入日历插件
			var laydate = layui.laydate;
			laydate.render({
				elem : '#calendar',
				type : 'month',
				format: 'yyyy年MM月',
				range:true,
			});
			//2.监听按钮组
			form.on('submit(cancelBtn)',function(data){
				calNewPlan();
				return false;
			});
			form.on('submit(submitBtn)',function(data){
				subNewPlan('${pageContext.request.contextPath}/newTimingPlanServlet');
				return false;
			});
		});
	</script>
</body>
</html>