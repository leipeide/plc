/*
function getXMLHttpRequest() {
	var xmlhttp;
	if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp = new XMLHttpRequest();
	} else {// code for IE6, IE5
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	return xmlhttp;
}

function testAJAX() {
	//1.创建请求
	var req = getXMLHttpRequest();
	//4.服务器处理
	req.onreadystatechange = function() {
		if (req.readyState == 4) {// 请求成功
			if (req.status == 200) {// 服务器响应成功
				alert(req.responseText);
			}
		}
	}
	//1.建立链接
	req.open("get", "${pageContext.request.contextPath }/");
	//2.发送请求
	req.send(null);
}*/

/**
 * 左侧导航栏点击集控器名称
 * @param id
 * 集控器id
 * @returns
 */
function deviceOnclick(url,id) {
	document.getElementById('body-div').innerHTML = "<iframe style='min-height: 500px' name='fname' frameborder='0' scrolling='yes' width='100%' src='"+url+"?deviceid="+id+"' class='body-frame'></iframe>";
}


//点击首页实现页面刷新
function refresh() { 
	window.location.reload();
}


layui.use('element', function() {
	var element = layui.element;

});
function addDevice(url, userid){
			layui.use('layer', function() {
				var layer = layui.layer;
				layer.open({
					area: ['auto', 'auto'],
					offset:['60px','550px'],
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
						// 右上角关闭回调
					}
				});
			});
		}


function removeDevice(url,userid) {
	layui.use('layer', function() {
		var layer = layui.layer;
		layer.open({
			area: ['350px', '400px'],
			offset:['60px','550px'],
			btnAlign: 'c',
			resize: false,
			content : url + "?userid=" + userid,
			closeBtn: 1,
			type: 2,
			/*btn : '关闭',
			yes : function(index, layero) {
				// 按钮【按钮一】的回调
				layer.close(index);
			},*/
			cancel : function() {
				// 右上角关闭回调
				location.reload();
			}
		});
	});
}


function um(userid){
	 // 创建一个 form      
	var form1 = document.createElement("form");     
	form1.id = "form1";      
	form1.name = "form1";        
	// 添加到 body 中     
	document.body.appendChild(form1);        
	// 创建一个输入    
	var input = document.createElement("input");     
	// 设置相应参数      
	input.type = "hidden";     
	input.name = "userid";     
    input.value = userid;        
    // 将该输入框插入到 form 中     
    form1.appendChild(input);        
    // form 的提交方式      
    form1.method = "POST";      
    // form 提交路径     
    form1.action = "getUserMessageServlet";       
    // 对该 form 执行提交   
    form1.submit();     
    // 删除该 form      
    document.body.removeChild(form1);  
}