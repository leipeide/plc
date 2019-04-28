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
	width: 1000px;
	height: 50px;
	border: 1px;
	solid: #FFF
}
.div-backBtn{
	width: 30px;
	height: 50px;
	float: left;
	border: 1px;
}
.div-select {
	width: 400px;
	height: 50px;
	float: left;
	border: 1px;
}

.div-power {
	width: 800px;
	height: 420px;
	margin-left:40px;
}

.div-time {
	width: 800px;
	height: 420px;
	position: relative;
	margin:40px;
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
	 				<a href=${pageContext.request.contextPath }/returnNodesServlet?nodeAddr=${nodeAddr}>返回</a>
	 			</button>
			</div>
		</div></br>
		<div>
			<div class="div-power" id="powerChart"></div>
			<div class="div-time" id="workTimeChart"></div>
		</div>
	</form>
</body>
<script type="text/javascript">
	layui.use('laydate',function() {
				 var laydate = layui.laydate;
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


