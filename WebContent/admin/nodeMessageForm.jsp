<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/layui/css/layui.css"
	media="all">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/layui/layui.js"></script>
<script
	src="${pageContext.request.contextPath }/Highcharts-7.0.3/code/highcharts.js"></script>
<title>Insert title here</title>
<style type="text/css">
.nav {
	background-color:#D1E1F0;
	width: 1118px;
	height: 60px;
/*      border:1px solid #000;   */
}
.layui-form-label{
	float: left;
	width:60px;
	height: 30px;
	padding-left:5px;
/* 	border:1px solid #000; */
}
.div-backBtn {
	float: left;
	width:80px;
	height: 50px;
	margin-top:10px;
/* 	border:1px solid #000; */
}

.div-select {
	float: left;
	width: 350px;
	height: 50px;
	margin-top:10px;
/*     border:1px solid #000; */
}

.div-power {
	width: 800px;
	height: 420px;
 	margin-bottom: 40px;
/* 	border:1px solid #000; */
}

.div-time {
	width: 800px;
	height: 420px;
	position: relative;
/* 	border:1px solid #000; */
}
</style>
</head>
<body>
	<form 
		src="${pageContext.request.contextPath }/nodeChartServlet?nodeAddr=${nodeAddr}"
		method="post">
		<div class="nav">
			<div class="div-select">
				<label class="layui-form-label">时间范围</label> <input
					class="layui-input" id="calendar" type="text" name="calendar"
					placeholder=" 请选择范围 " style="width: 250px;">
			</div>
			<div class="div-backBtn">
				<button id="backBtn" class="layui-btn">
					<a href="${pageContext.request.contextPath }/returnNodesServlet?nodeAddr=${nodeAddr}">返回</a>
				</button>
			</div>
		</div>
		</br>
		<div>
			<div class="div-power" id="powerChart"></div>
			<div class="div-time"  id="workTimeChart"></div>
		</div>
	</form>
</body>
<script type="text/javascript">
	layui.use('laydate',function() {
					var laydate = layui.laydate;
					//插入日期选择器
					laydate.render({
							elem : '#calendar',
							range : true,
							format : 'yyyy年MM月dd日',
							done : function(value) {
							var url = "${pageContext.request.contextPath }/nodeChartServlet?nodeAddr=${nodeAddr}";
							AJAXRequest(value, url);
							},
					});
			});
</script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/admin/js/nodesMessage.js"></script>
</html>


