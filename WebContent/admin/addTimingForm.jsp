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
.light1{
	float:left;
	width:200px;
/* 	border:1px solid #000;  */
	margin-right:50px;
}
.light2{
	float:left;
	width:200px;
/* 	border:1px solid #000;  */
}
.fuhao{
	float:left;
	margin:8px;
}
.HH{
	float:left;
	width:50px;
}
.mm{
	float:left;
	width:50px;
}
.button{
	padding-left:35px;
	padding-top:10px;
/*  	border:1px solid #000;  */
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
	<form class="layui-form"
		action="${pageContext.request.contextPath }/addTimingServlet?planid=${planid}"
		method="post">
		<div class="light1">
			<div class="layui-form-item">
				<label class="layui-form-label">主灯开关</label>
<!-- 				<input  type="checkbox" name="light1State" lay-skin="switch"> -->
				<input type="checkbox" name="light1State" lay-skin="switch" lay-text="ON|OFF"> 
				
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">主灯功率</label>
				<div class="layui-input-block" style="width: 50px;">
					<input id="text1" type="text" name="light1PowerPercent" required
						lay-verify="required" value="80" autocomplete="off"
						class="layui-input" onchange="check1(this)">
				</div>
			</div>
		</div>
		<div class="light2">
		<div class="layui-form-item">
			<label class="layui-form-label">辅灯开关</label>
				<div class="layui-input-block">
<!-- 					<input type="checkbox" name="light2State" lay-skin="switch"> -->
					 <input type="checkbox" name="light2State" lay-skin="switch" lay-text="ON|OFF"> 
				</div>
			</div>
		<div class="layui-form-item">
				<label class="layui-form-label">辅灯功率</label>
				<div class="layui-input-block" style="width: 50px;">
					<input id="text2" type="text" name="light2PowerPercent" required
						lay-verify="required" value="80" autocomplete="off"
						class="layui-input" onchange="check2(this)">
				</div>
			</div>
		</div>
		
		<div class="layui-form-item">
			<div><label class="layui-form-label">定时时间</label></div>
			<div class="HH">
				<input type="text" name="hours" required
					lay-verify="required|number|hours" placeholder="HH"
					autocomplete="off" class="layui-input">
			</div>
			<div class="fuhao">:</div>
			<div class="mm">
				<input type="text" name="minutes" required
					lay-verify="required|number|minutes" placeholder="mm"
					autocomplete="off" class="layui-input">
			</div>

		</div>
		
		<div class="button">
				<button class="layui-btn" lay-submit>确定</button>
		</div>
	</form>

	<script type="text/javascript">
		layui.use('form',function(){
			var form = layui.form;
		});	
		//控制双灯调光范围在0-100以内，包含0,100。
		function check1(obj) {
			value = document.getElementById("text1").value;
			if (value > 100) {
				obj.value = 100;
			}
			if (value < 0) {
				obj.value = 0;
			}
		}
		function check2(obj) {
			value = document.getElementById("text2").value;
			if (value > 100) {
				obj.value = 100;
			}
			if (value < 0) {
				obj.value = 0;
			}
		}
	</script>
</body>
</html>