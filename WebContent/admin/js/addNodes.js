/*
 *添加节点弹窗内内容 
 */
function funcAddNodes() {
	//1.获取select区域
	var myselect = document.getElementById("selected");
	//2.获取select选中值
	var index = myselect.selectedIndex;
	var val = myselect.options[index].value;
	//3.在textdiv区域根据select值添加等数量的文本框
	var textdiv = document.getElementById("textdiv");
	textdiv.innerHTML = "";
	if(val != null) {
		for(var i=0;i<val;i++) {
			textdiv.innerHTML += 
				"<div class='layui-input-block'>"+
				"<input type='text' name='nodeAddr' style='width:150px; height:35px;' value='' class='layui-input'>"; 
		}
	}
}


layui.use('form', function() {
	var form = layui.form;
	//监听select，进行funcAddNodes()函数
	form.on('select(Filter)', function(data) {
		funcAddNodes();
		return false;
	});
});
