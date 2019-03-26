<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%-- <meta http-equiv="refresh" content="3;url=${pageContext.request.contextPath }/login1Servlet">  --%>
<title>错误页面</title>
</head>
<body>
 	<font size="4">账号或密码输入错误，<span id="span" style="color:red;">3</span> 秒后重新登录！</font>
</body>
<script type="text/javascript">
	var time = 3;
	var timer = setInterval(timer,1000);
	function timer(){
		var span = document.getElementById("span");
		if(time>=1){
			span.innerHTML = time;
			time--;
		}else{
			clearInterval(timer);
			window.open("${pageContext.request.contextPath }/login1Servlet");
			//location.href="${pageContext.request.contextPath }/login1Servlet";
		}
	}
</script>
</html>