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
			action="${pageContext.request.contextPath}/delTimingBroadcastServlet"
			method="post">
			<table class="layui-table" lay-skin="line">
				<colgroup>
					<col width="100">
				</colgroup>
				<thead>
					<tr>
						<td>定时广播列表</td>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${result}" var="timing">
						<tr>	
							<td><input type="checkbox" name="timingId" id="timingId"
								title="定时时间：${timing.time}；&nbsp;状态：${(timing.light2State == true || timing.light1State == true)? '开灯  / 调光' : '关灯'}；&nbsp;主灯功率（%）：${timing.light1PowerPercent}；&nbsp;辅灯功率（%）：${timing.light2PowerPercent}" 
								value="${timing.id}" lay-skin="primary"  lay-filter="timingCheck"></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div class="layui-form-item">
				<div class="layui-input-block">
					<button id = "subBtn" class="layui-btn layui-btn-sm" lay-filter="subBtn" >立即提交</button>
				</div>
			</div>
		</form>
	</div>
	<script>
		layui.use(['form','layer'],function(){
			var form = layui.form;
			var layer  = layui.layer;
		});
	/*		form.on('checkbox(timingCheck)', function(obj){
				var subBtn = document.getElementById("subBtn");
			    if(obj.data != " "){
				   subBtn.removeAttribute('disabled');
				   alert("5");
			       return false;  
			     }
			});
			form.on('submit(subBtn)',function(data){	
				var check = document.getElementsByName("timingId");
				var subBtn = document.getElementById("subBtn");
			    var Value;
				if(!check.checked){	
					 layer.alert('未选择删除对象!', function(index){
						  //do something
						  layer.close(index);
						});  
					 subBtn.disabled = true;
				 }
			});*/
	</script>
</body>
</html>