/*function delWM(url,userid){
	layui.use('layer', function() {
		var layer = layui.layer;
		layer.open({
			area : ['350px','350px'],
			offset:['0px','350px'],
			btnAlign : 'c',
			resize : false,
			content : url + "?userid=" + userid,
			closeBtn : 1,
			type : 2,
			cancel : function() {
				// 右上角关闭回调
				location.reload();
				// return false 开启该代码可禁止点击该按钮关闭
			}
		});
	});
	
}*/
/**
 * 全选或全部不选
 * @returns
 */	
function ckAll(){	 
	//1.获取checkbox的元素对象
	var chElt=document.getElementById("checkAll");
	//2.获取选中状态
	var checkedElt=chElt.checked;
	//3.若checked=true,将所有的复选框选中,checked=false,将所有的复选框取消
	var allCheck=document.getElementsByName("id");
	//4.循环遍历取出每一个复选框中的元素
	if(checkedElt){//全选
		for(var i=0;i<allCheck.length;i++) {
			//设置复选框的选中状态
			allCheck[i].checked=true;
		}
	}else{
		//取消全选
		for(var i=0;i<allCheck.length;i++){
			allCheck[i].checked=false;
		}
	}
}

function delWM(url,userid){
	var check = document.getElementsByName("id");
	var value = [];
	//1.获取被选中的复选框value数组
	if(check){
		for(var i=0;i<check.length;i++) {
			if(check[i].checked){
				value.push(check[i].value);
			}
		}
	}
	//2.value数组中有删除对象则跳转到servlet进行删除操作，如没有选中对象，提示“请选择删除对象！”
	if(value.length){
		location.href = url + "?id=" + value +"&userid="+userid;
	} else {
		layui.use('layer', function() {
			var layer = layui.layer;
			layer.alert('请选择删除对象!');   
		});
	}
}

function selectMac(){
	var selectDeviceMac = document.getElementById("selectDeviceMac");
	var selectNodeAddr = document.getElementById("selectNodeAddr");
	var selectType = document.getElementById("selectType");
	var selectDate = document.getElementById("selectDate");       
	if(selectDeviceMac.value != ""){
		selectNodeAddr.disabled = true;
		selectType.disabled = true;
		selectDate.disabled = true;
		
	}if(selectNodeAddr.value != ""){
		selectDeviceMac.disabled = true;
		selectType.disabled = true;
		selectDate.disabled = true;
		
	}if(selectType.value != ""){
		selectDeviceMac.disabled = true;
		selectNodeAddr.disabled = true;
		selectDate.disabled = true;
		
	}if(selectDate.value != ""){
		selectDeviceMac.disabled = true;
		selectNodeAddr.disabled = true;
		selectType.disabled = true;
	}
	
}

/*
 * 核对筛选条件，若无选中条件，提示！
 */
function submitFun(url){
    var form = document.getElementById("form");
	var selectDeviceMacV = document.getElementById("selectDeviceMac").value;
	var selectNodeAddrV = document.getElementById("selectNodeAddr").value;
	var selectTypeV = document.getElementById("selectType").value;
	var selectDateV = document.getElementById("selectDate").value; 
	if(selectDeviceMacV == "" && selectNodeAddrV == "" && selectTypeV == "" && selectDateV == ""){
		layui.use('layer', function() {
			var layer = layui.layer;
			layer.alert('请选择筛选条件!');    
		});
	}else{	
		form.method = "POST";
		form.action = url;
	    form.submit();  
	}
}