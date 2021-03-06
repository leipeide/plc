layui.use('form', function(){
	var form = layui.form;
	//监听全选复选框，调用ckAll()函数
	form.on('checkbox(allChoose)', function(data) {
			ckAll();
			form.render();
		return false;
	});
});

/**
 * 点击全选复选框选中所有复选框，再次点击取消全选
 * @returns
 */
function ckAll(){	
	//1.获取checkbox的元素对象
	var chElt=document.getElementById("checkAll");
	//2.获取选中状态
	var checkedElt=chElt.checked;
	//3.若checked=true,将所有的复选框选中,checked=false,将所有的复选框取消
	var allCheck=document.getElementsByName("check");
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
