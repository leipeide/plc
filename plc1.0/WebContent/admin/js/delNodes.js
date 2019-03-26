 layui.use('form', function(){
		var form = layui.form;
		form.on('checkbox(allChoose)', function(data) {
			//if(data.elem.checked){
				ckAll();
				form.render();
			//}
			//layer.msg(JSON.stringify(data.field));
			//return false;
		});
	});
	function ckAll(){	
		//1.获取checkbox的元素对象
		var chElt=document.getElementById("checkAll");
		//2.获取选中状态
		var checkedElt=chElt.checked;
		//3.若checked=true,将所有的复选框选中,checked=false,将所有的复选框取消
		var allCheck=document.getElementsByName("nodeAddr");
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