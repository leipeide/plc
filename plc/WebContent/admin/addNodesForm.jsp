<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/layui/css/layui.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/layui/layui.js"></script>
<title>Insert title here</title>
</head>
<body>
	<div><form class="layui-form" action="${pageContext.request.contextPath}/addNodesServlet?deviceid=${deviceid}" method="post">
		<div class="layui-form-item">
  		   <label class="layui-form-label">添加节点</label>
   			 <div class="layui-input-inline">
     			 <select id="selected" name="selected" lay-filter="Filter" lay-verify=""  style =width:50px;>
       				 <option value="0">请选择添加数量</option>
       				 <option value="1">1</option>
        			 <option value="2">2</option>
        			 <option value="3">3</option>
        			 <option value="4">4</option>
       				 <option value="5">5</option>
      				 <option value="6">6</option>
        			 <option value="7">7</option>
       				 <option value="8">8</option>
       				 <option value="9">9</option>
        			 <option value="10">10</option>
                 </select></div>    
         </div>
         
         <div class="layui-form-item">
         	<label class='layui-form-label'>节点地址</label>
         	<div id="textdiv">
					<!--<label class='layui-form-label'>节点地址</label> -->
				</div>
         </div>
         
         <div class="layui-form-item">
		 	<div class="layui-input-block">
				<button name="nodeAddrBt"  class="layui-btn layui-btn-sm">立即提交</button></div>
		</div>
</form></div> 

 <script type="text/javascript"
		src="${pageContext.request.contextPath }/admin/js/addNodes.js"></script>
</body>
</html>