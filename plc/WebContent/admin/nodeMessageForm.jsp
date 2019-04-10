<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" >
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/layui/css/layui.css" media="all">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/layui/layui.js"></script>
<script src="${pageContext.request.contextPath }/Highcharts-7.0.3/code/highcharts.js"></script>
<title>Insert title here</title>
<style type="text/css">
	.nav{width:600px;height:50px;border:1px;}
	.div-btn {width:100px;height:50px;float:left;}
	.div-select{width:200px;height:50px; float:left;border:1px;solid:#FFF}
	.div-power{float:left; width:500px;height:350px;}
	.div-time{float:right;width:500px;height:350px;}
</style>
</head>
<body>
	<form src="" method="post">
	<div  class="nav">
		<div class="div-btn">
			<button  class="layui-btn layui-btn-sm" onclick="backNextPage()">返回上一页</button></div>
		<div class="div-select">
 			<input class="layui-input" id="test6" type="text" placeholder="请选择日期范围"></div>
			<!--<select class="chosen-select" id="countType" lay-filter="Filter" lay-verify="" style="height:30px" onchange="getDataInfo()" >
      			<option value="MM">按月统计</option>
       			<option value="IW">按周统计</option>
       			<option value="DD">按日统计</option></select>-->
       	</div>
    </div></br>
    
	<div>
		<div class="div-power" id="container"></div>
		<div class="div-time"  id="container1"></div></div> 
	</form>
</body>

<script type="text/javascript">
layui.use('laydate', function(){ 
	  var laydate = layui.laydate;
	  //var myDate = new Date();
 	  laydate.render({ 
     	 elem: '#test6',
     	 //isInitValue: true,
     	 //value:  myDate.toLocaleDateString(), //获取当前日期,
     	 range: true, 
     	 format: 'yyyy/MM/dd',
     	 done: function(value){
 	        chartsFun(value);
 	      
 	      }, 
   	 }); 
  });
</script>
<script type="text/javascript" src="${pageContext.request.contextPath }/admin/js/nodesMessage.js"></script>
</html>

<!-- <script type="text/javascript">
// layui.use('form', function() {
// 	var form = layui.form;
// 	//监听提交
// 	chartsFun();
// 	/*form.on('select(Filter)', function(data) {
// 		chartsFun();
// 	});*/
// });
function chartsFun(value,date,endDate) {
	var selectValue = document.getElementById("countType").value;
	//alert(selectValue);
	switch (String.valueOf(selectValue)) {
	//String.valueOf(selectValue);
	case "MM":
		alert("1");
		var xtitle = 'Month/Day';
		var title_M = '近一年-12个月';
		var X_M = [ '1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月','11月', '12月' ];
		alert("月"+title_M+X_M);
		break;
	case "IW":
		var xtitle = 'Weak/Day';
		var title_M = '近12周';
		var X_M = [ '1周', '2周', '3周', '4周', '5周', '6周', '7周', '8周', '9周', '10周','11周', '12周' ];
		alert("2");
		alert("周"+title_M+X_M);
		break;
	case "DD":
		var xtitle = 'Day/hours';
		var title_M = '近2周';
		var X_M = [ '1天', '2天', '3天', '4天', '5天', '6天', '7天', '8天', '9天', '10天','11天', '12天', '13天', '14天' ];
		alert("3");
		alert("日"+title_M+X_M);
		break;
	default:
		break;
	}


	var categories = ['00:00','01:00','02:00','03:00','04:00','05:00','06:00','07:00','08:00','09:00','10:00','11:00','12:00','13:00','14:00','15:00','16:00','17:00','18:00','19:00','20:00','21:00','22:00','23:00'];
	Highcharts.setOptions({
		chart: {
			backgroundColor: {
				linearGradient: [0, 0, 500, 500],
				stops: [
					[0, 'rgb(255, 255, 255)'],
					[1, 'rgb(240, 240, 255)']
				]
			},
			borderWidth: 2,
			plotBackgroundColor: 'rgba(255, 255, 255, .9)',
			plotShadow: true,
			plotBorderWidth: 1
		}
	});
	var chart1 = new Highcharts.Chart({
		chart: {
			renderTo: 'container',
		},
		title:{
			text:'title',
		},
		xAxis: {
			title: {
				text: 'xtitle',
			},
			type: 'datetime',
			//间距，时间戳，以下表示间距为1天，如果想表示间距为1周，就这么写
			tickInterval:  24 * 3600 * 1000,
			categories:categories,
		},
		series: [{
			name: '功率/W',
			data: [29.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4,29.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4],
			//pointStart: Date.UTC(2018,4,15,0,0,0,0),
			//categories:categories,
			//pointInterval: 3600 * 1000 // one hour
		}],
			
		yAxis: {
			title: {
				text: '功率 / W'
			},
			/*pointStart: 0,
			pointInterval:50,   //设置了y轴最大值后，设置间隔不起作用*/
			//max:250,
		},
	});

	
	
//节点工作时长表	
	var chart2 = new Highcharts.Chart({
		chart: {
			renderTo: 'container1',
			type: 'column',
		},
		title: {
			text: '节点工作时长图'
		},
		xAxis: {
			title: {
				text: '时间  /  h'
			},
			type: 'datetime'
		},
		yAxis: {
			title: {
				text: '时长 / min'
			},
		}, 
		series: [{
			name: '工作时间/h',
			data: [29.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4],
			pointStart: Date.UTC(2010, 0, 1),
			pointInterval: 3600 * 1000 // one hour
		}]
	});
	

}
</script> -->
