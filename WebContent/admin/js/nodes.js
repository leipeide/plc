/**
* 节点表格刷新列，ajax刷新
 */
//3.获取XMLHttpRequest对象
function getXMLHttpRequest() {
	var xmlhttp;
	if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp = new XMLHttpRequest();
	} else {// code for IE6, IE5
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	return xmlhttp;
}
//2.ajax刷新，去servlet获取节点信息
function AJAXRefreshRequest(url, func) {
	var req = getXMLHttpRequest();
	req.onreadystatechange = function() {
		if (req.readyState == 4) {// 请求成功
			if (req.status == 200) {// 服务器响应成功
				func(req);
			}
		}
	}
	req.open("get", url);// 建立一个链接
	req.send(null);// 发送请求
	
}
//1.节点刷新，调用AJAXRefreshRequest()函数
function nodeRefresh(url) {
	// ajax请求servlet，在响应成功回调函数中调用layui透明弹窗，刷新指令发送成功
	AJAXRefreshRequest(url, function(req) {
		layui.use('layer', function() {
			var layer = layui.layer;
			layer.msg(req.responseText, {
				time : 2000, // 2s后自动关闭
			});
		});
	});
}


/**
 * 单灯控制方法
 */
function nodeControl(url, nodeid, light1State, light1PowerPercent, light2State, light2PowerPercent) {
	layui.use('layer', function() {
		var layer = layui.layer;
		layer.open({
			area : [ 'auto', '350px' ],
			btnAlign : 'c',
			resize : false,
			content : url + "?nodeid=" + nodeid + "&light1State=" + light1State + "&light1PowerPercent=" + light1PowerPercent + "&light2State=" + light2State + "&light2PowerPercent=" + light2PowerPercent,
			closeBtn : 1,
			type : 2,
		    /*btn : '关闭',
			yes : function(index, layero) {
				// 按钮【按钮一】的回调
				layer.close(index);
			},*/
			cancel : function() {
				// 右上角关闭回调
				// return false 开启该代码可禁止点击该按钮关闭
			}
		});
	});
}

/**
 * 添加节点方法
 * @param url
 * @param deviceid
 * @returns
 */
function addNodes(url,deviceid){
	layui.use('layer', function() {
		var layer = layui.layer;
		layer.open({
			area : [ 'auto','350px'],
			offset:['0px','350px'],
			btnAlign : 'c',
			resize : false,
			content : url + "?deviceid=" + deviceid,
			closeBtn : 1,
			type : 2,
			/*btn : '关闭',
			yes : function(index, layero) {
				// 按钮【按钮一】的回调
				layer.close(index);
			},*/
			cancel : function() {
				// 右上角关闭回调
				location.reload();
				// return false 开启该代码可禁止点击该按钮关闭
			}
		});
	});
}

/**
 * 删除节点方法
 * @param url
 * @param deviceid
 * @returns
 */
function delNodes(url,deviceid){
	layui.use('layer', function() {
		var layer = layui.layer;
		layer.open({
			area : [ '300px','350px'],
			offset:['0px','350px'],
			btnAlign : 'c',
			resize : false,
			content : url + "?deviceid=" + deviceid ,
			closeBtn : 1,
			type : 2,
			/*btn : '关闭',
			yes : function(index, layero) {
				// 按钮【按钮一】的回调
				layer.close(index);
			},*/
			cancel : function() {
				// 右上角关闭回调
				location.reload();
				// return false 开启该代码可禁止点击该按钮关闭
			}
		});
	});
}

/**
 * 节点重命名方法
 * @param url
 * @param nodeid
 * @returns
 */
function reName(url,nodeid){
	layui.use('layer', function() {
		var layer = layui.layer;
		layer.open({
			area : [ 'auto','350px'],
			offset:['0px','350px'],
			btnAlign : 'c',
			resize : false,
			content : url+ "?nodeid=" + nodeid ,
			closeBtn : 1,
			type : 2,
			/*btn : '关闭',
			yes : function(index, layero) {
				// 按钮【按钮一】的回调
				layer.close(index);
			},*/
			cancel : function() {
				// 右上角关闭回调
				location.reload();
				// return false 开启该代码可禁止点击该按钮关闭
			}
		});
	});
}

/**
 * 控制节点页面为尾页时下一页不可用
 * @param currPage
 * @param totalPage
 * @returns
 */
function  nextPage(currPage,totalPage){
	if(totalPage == 0 || totalPage == currPage){
		document.getElementById("pageBt").disabled=true;
	}else{
		document.getElementById("pageBt").disabled=false;
	}
}

