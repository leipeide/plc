function checkOrCancelAll(){
	var chAll = document.getElementById("chAll");
	//2.获取选中状态
	var checkedAll = chAll.checked;
	//3.若checked=true,将所有的复选框选中,checked=false,将所有的复选框取消
	var oneChecked = document.getElementsByName("check");
	//4.循环遍历取出每一个复选框中的元素
	if(checkedAll){//全选
		for(var i=0;i<oneChecked.length;i++) {
			//设置复选框的选中状态
			oneChecked[i].checked=true;
		}
	}else{
		//取消全选
		for(var i=0;i<oneChecked.length;i++){
			oneChecked[i].checked=false;
		}
	}
}

/**
 * 用户报警信息显示
 * @returns
 */
//A.创建XMLHttpRequest对象
function getXMLHttpRequest() {
	var xmlhttp;
	if (window.XMLHttpRequest) {
		// code for IE7+,Firefox,Chrome,Opera,Safari
		xmlhttp = new XMLHttpRequest();
	} else {
		// code for IE5,IE6,
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	return xmlhttp;
}
//B.创建回调函数，根据响应动态更新页面
function warnningAJAXRequest() {
	//1.创建请求
	var req = getXMLHttpRequest();
	//4.服务器处理
	req.onreadystatechange = function() {
		if (req.readyState == 4) {// 请求成功
			if (req.status == 200) {// 服务器响应成功,动态获取报警信息表格
			/*
				var table = document.getElementById("tbody"); 
				table.innerHTML = inner;
				//从后台获取对象
				for(;;){//for循环信息对象，生成表格
					inner = inner + " ";
				}
			*/	
			}
		}
	}
	//1.建立链接
	req.open("get", "${pageContext.request.contextPath }/");
	//2.发送请求
	req.send(null);
}

window.onload = function() {
	warnningAJAXRequest();
}
setInterval(warnningAJAXRequest,1000*3);
