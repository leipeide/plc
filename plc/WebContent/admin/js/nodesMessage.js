function getXMLHttpRequest() {
		var xmlhttp;
		if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
			xmlhttp = new XMLHttpRequest();
		} else {// code for IE6, IE5
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		return xmlhttp;
}	
/**
 * ajax请求数据并调用chartsFun1
 * @param value
 * @param url
 * @returns
 */
function AJAXRequest(value, url) {
	var req = getXMLHttpRequest();
	req.onreadystatechange = function() {
		if (req.readyState == 4) {// 请求成功
			if (req.status == 200) {// 服务器响应成功
				// 返回ajax请求数据req.responseText，数据用json封装
				var json = JSON.parse(req.responseText);
				// 调用chartsFun1函数，生成图表图
				chartsFun1(json);
			}
		}
	}
	// 发送请求， 建立一个链接
	req.open("post", url, true);
	// POST方式需要自己设置http的请求头
	req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	// POST方式发送数据
	req.send("Date=" + value);
}
	
function chartsFun1(json){
	//1.获取json数据，为chart1填充数据
	var powerRecord = json.powerRecord;
	var record ;
	var power = [];
	var time = [];
	for(var i = 0; i < powerRecord.length; i++ ) {
		record = powerRecord[i];
		power.push(record.power);
		var msTime = new Date(record.date);
		dateTime = msTime.toLocaleString();
		time.push(dateTime);
		}
	//2.获取json数据，为chart2填充数据
	var timeRecord = json.timeRecord;
	//var nd = 1000 * 24 * 60 * 60;
    var nh = 1000 * 60 * 60;
    var startTime;
    var endTime;
    var diff;
    var hour;
    var yWorkTime = [];
    var xStartTime =[];
	for(var j = 0; j < timeRecord.length; j++) {
		if(j%2 == 0) {
			startTime = parseInt(timeRecord[j]);
			endTime = parseInt(timeRecord[j+1]);
			//alert("开始毫秒值"+startTime);
			//alert("结束毫秒值"+endTime);
			var msEndTime = new Date(endTime);//毫秒数转换成时间
			var msStartTime = new Date(startTime);
			//alert("开始时间"+msStartTime);
			//alert("结束"+msEndTime);
			sTime = msStartTime.toLocaleString();//将时间转成字符串
			//eTime = msEndTime.toLocaleString();
			//alert(sTime);
			//alert(eTime);
			diff = msEndTime.getTime() - msStartTime.getTime();
			//diff = endTime - startTime;
			//alert("diff1:"+diff);
			hour = diff/nh/24;
			yWorkTime.push(hour);
			xStartTime.push(sTime);
		}	
	}
	
	//3.设置图表的统一样式
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
	//4.节点工作功率图表显示
	var chart1 = new Highcharts.Chart({
		chart: {
			renderTo: 'powerChart',
		},
		title:{
			text:'节点功率图',
		},
		xAxis: {
			title: {
				text:'DateTime',
			},
			type: 'datetime',
			categories:time,//从后台获取
		},
		series: [{
			name: '功率/W',
			data:power,
		}],
		yAxis: {
			max:400,
			title: {
				text: '功率 / W'
			},
		},
	});
	//5.节点工作时长图表显示
	var chart2 = new Highcharts.Chart({
		chart: {
			renderTo: 'workTimeChart',
			type: 'column',
			//minPointLength: 10,
		},
		title: {
			text: '节点工作时长图'
		},
		xAxis: {
			title: {
				text: '时间  /  DateTime'
			},
			type: 'datetime',
			categories:xStartTime,
				
		},
		yAxis: {
			//tickPixelInterval:10,
			title: {
				text: '时长 / 天'
			},
		}, 
		series: [{
			name: '工作时间/天',
			data:yWorkTime,
		}]
	});

	
}
 
 
 