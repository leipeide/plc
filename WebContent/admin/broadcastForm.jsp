<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
	<form class="layui-form"
		action="${pageContext.request.contextPath }/broadcastServlet"
		method="post">
		<input type="hidden" name="deviceid" value="${deviceid }">
		<!-- 提示：如果你不想用form，你可以换成div等任何一个普通元素 -->
		<div class="layui-form-item">
			<label class="layui-form-label">主灯开关</label>
			<div class="layui-input-block">
				<input type="checkbox" name="light1State" lay-skin="switch">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">主灯功率</label>
			<div class="layui-input-block" style="width: 50px;">
				<input id="text1" type="text" name="light1PowerPercent" required
					lay-verify="required" value="80" autocomplete="off"
					class="layui-input" onchange="check1(this)">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">辅灯开关</label>
			<div class="layui-input-block">
				<input type="checkbox" name="light2State" lay-skin="switch">
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
		<div class="layui-form-item">
			<div class="layui-input-block">
				<button class="layui-btn" lay-submit>立即提交</button>
			</div>
		</div>
	</form>
	<script>
		layui.use('form', function() {
			var form = layui.form;
		});

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