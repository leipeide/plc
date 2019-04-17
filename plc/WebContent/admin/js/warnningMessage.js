/**
 * 选择删除报警信息
 * @returns
 */
/*function checkOrCancelAll(){
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
}*/
function delWM(url,userid){
	layui.use('layer', function() {
		var layer = layui.layer;
		layer.open({
			area : ['auto','350px'],
			//offset:['0px','350px'],
			btnAlign : 'c',
			resize : false,
			content : url + "?userid=" + userid,
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
	

