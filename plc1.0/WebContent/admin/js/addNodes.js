function funcAddNodes() {
	var myselect = document.getElementById("selected");
	var index = myselect.selectedIndex;
	var val = myselect.options[index].value;
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
	//监听提交
	form.on('select(Filter)', function(data) {
		funcAddNodes();
		//layer.msg(JSON.stringify(data.field));
		return false;
	});
});
