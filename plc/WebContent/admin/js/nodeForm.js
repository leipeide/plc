function check1(obj){
	value = document.getElementById("text1").value;
	if(value>100){
		obj.value=100;
	}if(value<0){
		obj.value=0;
	}
}

function check2(obj){
	value = document.getElementById("text2").value;
	if(value>100){
		obj.value=100;
	}if(value<0){
		obj.value=0;
	}
}