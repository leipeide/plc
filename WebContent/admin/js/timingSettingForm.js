layui.use('element', function() {
	var element = layui.element;

});
/**
 * 新建定时计划窗口
 * @param url
 * @returns
 */
function newPlan(url,userid) {
	layui.use('layer', function() {
		var layer = layui.layer;
		layer.open({
			area: ['700px', '600px'],
			offset:['0px','300px'],
			btnAlign: 'c',
			resize: false,
			content : url+"?userid="+userid,
			closeBtn: 1,
			type: 2,
			/*btn : '关闭',
			yes : function(index, layero) {
				// 按钮【按钮一】的回调
				layer.close(index);
			},*/
			cancel : function() {
				location.reload();
			}
		});
	});
}
/**
 * 删除计划
 * @param url
 * @param uerid
 * @returns
 */
function delPlan(url,userid) {
	layui.use('layer', function() {
		var layer = layui.layer;
		layer.open({
			area: ['300px', '350px'],
			offset:['0px','300px'],
			btnAlign: 'c',
			resize: false,
			content : url+"?userid="+userid,
			closeBtn: 1,
			type: 2,
			cancel : function() {
				location.reload();
			}
		});
	});
}

/**
 * 计划重命名
 * @param url
 * @param planid
 * @param deviceMac
 * @returns
 */
/*function planRename(url,planid,deviceMac) {
	alert("planRename");
	layui.use('layer', function() {
		var layer = layui.layer;
		layer.open({
			area: ['auto', 'auto'],
			offset:['60px','550px'],
			btnAlign: 'c',
			resize: false,
			content : url+"?planid="+planid+"&deviceMac="+deviceMac,
			closeBtn: 1,
			type: 2,
			cancel : function() {
				location.reload();
				// 右上角关闭回调
			}
		});
	});
}*/

/**
 *根据select选择在的计划显示他的定时设置
 */

function showTiming(url){
	//1.ajax请求信息
	var selectValue = document.getElementById("selectPlan").value;
	if(selectValue != null){
		AJAXRequest(url,selectValue);
	}
}
/**
 * 2. 查看计划，ajax获取信息，获取XMLHttpRequest对象
 * @returns
 */
function getXMLHttpRequest() {
	var xmlhttp;
	if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp = new XMLHttpRequest();
	} else {// code for IE6, IE5
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	return xmlhttp;
}
//2.ajax刷新，去servlet获取信息
function AJAXRequest(url,selectValue) {
	var req = getXMLHttpRequest();
	req.onreadystatechange = function() {
		if (req.readyState == 4) {// 请求成功
			if (req.status == 200) {// 服务器响应成功
				//根据和计划名称获取定时广播信息
					var result = JSON.parse(req.responseText);
					var data = result.data;
					var inner = "";
					var timing;
					document.getElementById("settingPlan").innerHTML = "<table  class=\"layui-table\" lay-skin=\"line\">" +
						"<thead><tr>" +
						"<th>计划名称</th>" +
						"<th>广播时间</th>" +
						"<th>工作状态</th>" +
						"<th>主灯功率（%）</th>" +
						"<th>辅灯功率（%）</th>" +
						"</tr></thead>" +
						"<tbody id='tbody'></tbody></table>";
					for (var i = 0; i < data.length; i++) {
						timing = data[i];
						inner = inner + "<tr>" +
						"<td>" + timing.planName + "</td>" +
						"<td>";
						if (timing.time != null) {
							//解析json数据中的时间格式并转换，获取time
							var msTime = timing.time;
							var time = new Date(msTime);
							var hour = time.getHours() < 10 ? "0" + time.getHours(): time.getHours();
							var minute = time.getMinutes() < 10 ? "0" + time.getMinutes(): time.getMinutes();
							inner = inner + hour + ":" +minute;
						} 
						inner = inner + "</td>" +
						"<td>";
						if (timing.light2State == true || timing.light1State == true) {
							inner = inner + "开灯  / 调光";
						} else {
							inner = inner + "关灯";
						}
						inner = inner + "</td>" +
						"<td>" + timing.light2PowerPercent + "</td>" +
						"<td>" + timing.light2PowerPercent + "</td>" +
						"</tr>";
					}
					document.getElementById("tbody").innerHTML = inner;
				}
		}
	}
	// 发送请求， 建立一个链接
	req.open("post", url, true);
	// POST方式需要自己设置http的请求头
	req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	// POST方式发送数据
	req.send("planName=" + selectValue);
}
/**
 * 新建计划
 * @param url
 * @param planid
 * @returns
 */
function addTiming(url,planid){
	layui.use('layer', function() {
		var layer = layui.layer;
		layer.open({
			area: ['480px', '280px'],
			//offset:['0px','300px'],
			btnAlign: 'c',
			resize: false,
			content : url+"?planid="+planid,
			closeBtn: 1,
			type: 2,
			/*btn : '关闭',
			yes : function(index, layero) {
				// 按钮【按钮一】的回调
				layer.close(index);
			},*/
			cancel : function() {
				location.reload();
			}
		});
		//return false;
	});
}
/**
 * 删除定时广播
 * @param url
 * @returns
 */
function delTimingBroadcast(url,planid) {
	layui.use('layer', function() {
		var layer = layui.layer;
		layer.open({
			area: ['650px', '400px'],
			//offset:['0px','300px'],
			btnAlign: 'c',
			resize: false,
			content : url+"?planid="+planid,
			closeBtn: 1,
			type: 2,
			cancel : function() {
				location.reload();
			}
		});
		//return false;
	});     
}

/**
 * 执行计划
 * @param url
 * @returns
 */
function implementPlan(url){
	 location.href = url;
}

/**
 * 停止计划
 * @param url
 * @returns
 */
function stopPlan(url){
	 location.href = url;
}

