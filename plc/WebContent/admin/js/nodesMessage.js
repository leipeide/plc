function backNextPage(){
	window.history.back(-1);
}

/*
function chartsFun(value) {
	alert(value);
	var url = 
	$.ajax({
		  type:"post",
		  url:"nodePowerChartServlet",  //ajax请求的地址
		  dataType:"json",
		  //data:{"timedata":value},
		  data: '{"name": "uname", "age": 18}',
		  async:false, //发送同步请求
		  success:function (result){   //指的是请求并成功返回信息
		  //getDataToDdate(title,X,result);
			  getDataToDdate(result);
		  }
		});
}	*/	

function chartsFun(value){
	var arr=value.split("-");
	var startDate = arr[0];
	var endDate = arr[1];
	//alert(startDate+"+"+endDate); //2019/03/01 - 2019/03/29
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
			text:'节点功率图',
		},
		xAxis: {
			title: {
				text: 'DateTime',
			},
			type: 'datetime',
			categories:categories,//从后台获取
		},
		series: [{
			name: '功率/W',
			data: [29.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4,29.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4],
			//pointStart: Date.UTC(startDate),
			//categories:categories,
			//pointInterval: 3600 * 1000 // one hour
		}],
			
		yAxis: {
			title: {
				text: '功率 / W'
			},
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
 
 /*
 //首先，在xAxis中设置x轴属性：
 xAxis: {
 //表示为时间，注意大小写
 type: 'datetime',
 //间距，时间戳，以下表示间距为1天，如果想表示间距为1周，就这么写
 //7243600*1000
 tickInterval:24 * 3600 * 1000,
 //格式化时间，day,week…
 dateTimeLabelFormats: {
 day: ‘%Y-%m-%d’
 }
 }
 //然后，传入正确格式的json数据，格式为：[[时间戳,data],[时间戳,data],[时间戳,data]…]（注意：时间戳要精确到毫秒，若只精确到秒，乘以1000即可）。
 //最后，在使用ajax更新数据时这样写：
 success: function(data){
 chart…series[0].addPoint(data,true,true)
 }*/
 
 