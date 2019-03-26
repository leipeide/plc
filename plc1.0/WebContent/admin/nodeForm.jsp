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
	<form class="layui-form"
		action="${pageContext.request.contextPath }/nodeServlet" method="post">
		<input type="hidden" name="nodeid" value="${nodeid }">
		<!-- 提示：如果你不想用form，你可以换成div等任何一个普通元素 -->
		<div class="layui-form-item">
			<label class="layui-form-label">主灯开关</label>
			<div class="layui-input-block">
				<c:if test="${light1State == 'true' }">
					<input type="checkbox" name="light1State" lay-skin="switch" checked>
				</c:if>
				<c:if test="${light1State == 'false' }">
					<input type="checkbox" name="light1State" lay-skin="switch">
				</c:if>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">主灯功率</label>
			<div class="layui-input-block" style="width: 50px;">
				<input id="text1" type="text" name="light1PowerPercent" required
					lay-verify="number" value="${light1PowerPercent }"
					autocomplete="off" class="layui-input" onblur="check1(this)">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">辅灯开关</label>
			<div class="layui-input-block">
				<c:if test="${light1State == 'true' }">
					<input type="checkbox" name="light2State" lay-skin="switch" checked>
				</c:if>
				<c:if test="${light1State == 'false' }">
					<input type="checkbox" name="light2State" lay-skin="switch">
				</c:if>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">辅灯功率</label>
			<div class="layui-input-block" style="width: 50px;">
				<input id="text2" type="text" name="light2PowerPercent" required
					lay-verify="number" value="${light2PowerPercent }"
					autocomplete="off" class="layui-input" onblur="check2(this)">
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-input-block">
				<button class="layui-btn" lay-submit lay-filter="*">立即提交</button>
			</div>
		</div>
		<!-- 更多表单结构排版请移步文档左侧【页面元素-表单】一项阅览 -->
	</form>
	<script>
		layui.use('form', function() {
			var form = layui.form;
	});
	</script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath }/admin/js/nodeForm.js"></script>
</body>
</html>