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
.nav {
	float: left;
	background-color: #D1E1F0;
	width:99%;
/* 	width: 1120px; */
	height: 35px;
	margin: 0px 0px 10px 0px;
	padding: 10px 5px 10px 10px;
	/* 	border:1px solid #000; */
}

.newBtn {
	float: left;
	/* 	border:1px solid #000; */
}

.delBtn {
	float: left;
	margin-left: 10px;
	/* 	border:1px solid #000; */
}

.select {
	float: left;
	width:120px;
	margin-left: 10px;
	/* 	border:1px solid #000; */
}
</style>
</head>
<body>
	<form class="layui-form">
		<div class="nav">
			<div class="newBtn">
				<button class="layui-btn" lay-submit lay-filter="newBtn">新建计划</button>
			</div>
			<div class="delBtn">
				<button class="layui-btn" lay-submit lay-filter="delBtn">删除计划</button>
			</div>
			<div class="select">
				<select name="selectPlan" id="selectPlan" lay-filter="select">
					<option value="">查看计划</option>
					<c:forEach items="${timingPlan}" var="plan">
						<option value="${plan.name}">${plan.name}</option>
					</c:forEach>
				</select>
			</div>
		</div>

		<!-- 定时计划表格信息 -->
		<div class="settingPlan" id="settingPlan">
			<table class="layui-table" lay-skin="line" lay-size="sm">
				<colgroup>
					<col width="120">
					<col width="150">
					<col>
				</colgroup>
				<thead>
					<tr>
						<th>计划名称</th>
						<th>用户id</th>
						<th>集控器Mac地址</th>
						<th>开始时间</th>
						<th>结束时间</th>
						<th>执行状态</th>
						<th>添加定时</th>
						<th>删除定时广播</th>
						<th>执行设置</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${timingPlan}" var="plan">
						<tr>
							<td><a href="javascript:;"
								onclick="planRename('${pageContext.request.contextPath }
							 	/planRenameFormServlet',${plan.id},${plan.deviceMac})">
									${plan.name}</a></td>
							<td>${plan.userid}</td>
							<td>${plan.deviceMac}</td>
							<td>${plan.startDate}月</td>
							<td>${plan.endDate}月</td>
							<td>${plan.doState == true ? "执行中" : "未执行"}</td>
							<td><button class="layui-btn layui-btn-xs" type="button"
									onclick="addTiming('${pageContext.request.contextPath}/addTimingFormServlet',${plan.id})">
									<i class="layui-icon">&#xe654;</i>
								</button></td>
							<td><button class="layui-btn layui-btn-xs" type="button"
									onclick="delTimingBroadcast('${pageContext.request.contextPath}/delTimingBroadcastFormServlet',${plan.id})">
									<i class="layui-icon">&#xe640;</i>
								</button></td>
							<td><div class="layui-btn-group">
									<button class="layui-btn layui-btn-xs" type="button"
										onclick="implementPlan('${pageContext.request.contextPath}/implementTimingPlanServlet?planid=${plan.id}&userid=${plan.userid}')">
										执行</button>
									<button class="layui-btn layui-btn-xs" type="button"
										onclick="stopPlan('${pageContext.request.contextPath}/stopTimingPlanServlet?planid=${plan.id}&userid=${plan.userid}')">
										停止</button>
								</div></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>

		<!--定时设置表格信息 ，点击查看计划select该区域内出现相应计划的内容 -->
		<div class="timing" id="timing"></div>
	</form>

	<script type="text/javascript"
		src="${pageContext.request.contextPath }/admin/js/timingSettingForm.js"></script>
	<script>
		layui.use('form',function(){
			var form = layui.form;
			form.on('submit(newBtn)',function(data){
				newPlan('${pageContext.request.contextPath}/newTimingPlanFormServlet',${userid});
				return false;
			});
			form.on('submit(delBtn)',function(data){
				delPlan('${pageContext.request.contextPath}/delTimingPlanFormServlet',${userid});
				return false;
			});
			form.on('select(select)',function(data){
				showTiming("${pageContext.request.contextPath}/searchTimingServlet");
				return false;
			});
		});		
	</script>
</body>
</html>