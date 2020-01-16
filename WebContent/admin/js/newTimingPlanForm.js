/**
 * 点击取消按钮，取消select  canledar input 
 * @returns
 */
function calNewPlan() { //select未完成
	var inputPlanName = document.getElementById("inputPlanName");
	var select = document.getElementById("select");
	var calendar = document.getElementById("calendar");
	inputPlanName.value = "请输入计划名称";
	calendar.value = "请选择";
	select.value = "请选择";
}
/**
 * 点击提交按钮，提交信息至servlet
 */
function subNewPlan(url){
	var inputPlanName = document.getElementById("inputPlanName");
	var select = document.getElementById("select");
	var calendar = document.getElementById("calendar");
	if((inputPlanName.value == "") || (select.value == "") || (calendar.value =="")){
		layui.use('layer', function() {
			var layer = layui.layer;
			layer.alert('信息填写不完整!', function(index){
				  //do something
				  layer.close(index);
				});       
		});
	}else{
		var deviceMac = select.value;
		var planName = inputPlanName.value;
		var Date = calendar.value;
		location.href = url + "?planName=" + planName +"&deviceMac="+deviceMac + "&Date="+Date;
	}
}

