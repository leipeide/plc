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
</style>
</head>
<body>
	<div>
		<form class="layui-form"
			action="${pageContext.request.contextPath}/delTimingPlanServlet"
			method="post">
			<table class="layui-table" lay-skin="line">
				<colgroup>
					<col width="100">
				</colgroup>
				<thead>
					<tr>
						<td>计划列表</td>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${result}" var="plan">
						<tr>
							
							<td><input type="checkbox" name="planId" id="planName"
								title="${plan.name}" value="${plan.id}" lay-skin="primary" 
								lay-submit lay-filter="planCheck" lay-verify="required"></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div class="layui-form-item">
				<div class="layui-input-block">
					<button class="layui-btn layui-btn-sm" lay-submit lay-filter="subBtn">立即提交</button>
				</div>
			</div>
		</form>
	</div>
	<script>
		layui.use(['form','layer'],function(){
			var form = layui.form;
			var layer = layui.layer;
			//1.监听提交
		/*	form.on('submit(subBtn)',function(data){	
				var checkbox = document.getElementsByName("planName");
				var url = "${pageContext.request.contextPath}/delTimingPlanServlet";
				var checked = checkbox.checked;
				if(checkbox.checked){
					location.href = url + "?planId=" + checkbox.value;
				}else{
					layer.msg('请选择删除对象!', {
						});  
				}
				return false;
			});*/
			//2.监听checkbox,使复选框正常显示
			form.on('checkbox(planCheck)',function(data){	
				return false;
			});
		});
	</script>
</body>
</html>