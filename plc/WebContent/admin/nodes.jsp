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
<style>
/*   .nav div{float:left;} */
.pagination {
	width: 100%;
	position: relative;
	bottom: 0px;
}
/*   .div-serach{width:150px; height:50px} */
/*   .div-submit{width:50px; height:50px} */
</style>
</head>
<body>
	<form id="nodeform" class="layui-form" action="" method="post">
		<div class="nav">
			<div class="layui-btn-container">
				<button id="addBt" class="layui-btn layui-btn-sm" lay-submit
					lay-filter="addBt">增加节点</button>
				<button id="delBt" class="layui-btn layui-btn-sm" lay-submit
					lay-filter="delBt">删除节点</button>
			</div>
			<!--    		<div class="div-serach"> -->
			<!--         	<input type="text" name="serach"  placeholder="请输入节点地址"  style="width:150px; height:30px"> -->
			<!--         </div> -->
			<!--         <div class="div-submit"> -->
			<!--          	<input type="submit" name="submit" value="搜索"  style="width:40px; height:30px"> -->
			<!--         </div> -->
		</div>

		<table class="layui-table">
			<colgroup>
				<col width="150">
				<col>
			</colgroup>
			<thead>
				<tr>
					<th>节点地址</th>
					<th>节点名称</th>
					<th>主灯状态</th>
					<th>主灯亮度</th>
					<th>主灯功率</th>
					<th>辅灯状态</th>
					<th>辅灯亮度</th>
					<th>辅灯功率</th>
					<th>总功率</th>
					<th>单灯控制</th>
					<th>更新数据</th>
				</tr>
			</thead>
			<tbody id="table_body">
				<!--<c:forEach items="${pb.nodes}" var="node">
					<tr>
						<td>${node.nodeAddr }</td>
						<td><a href="javascript:;"
							onclick="reName('${pageContext.request.contextPath }/nodeRenameFormServlet',${node.id})">${node.nodeName }</a></td>
						<td>${node.light1State == true ? "开" : "关" }</td>
						<td>${node.light1PowerPercent }</td>
						<td>${node.light2State == true ? "开" : "关" }</td>
						<td>${node.light2PowerPercent }</td>
						<td>${node.power }W</td>
						<td><a href="javascript:;"
							onclick="nodeControl('${pageContext.request.contextPath }/nodeFormServlet', ${node.id }, ${node.light1State }, ${node.light1PowerPercent }, ${node.light2State }, ${node.light2PowerPercent })">单灯控制</a></td>
						<td><a href="javascript:;"
							onclick="nodeRefresh('${pageContext.request.contextPath }/nodeRefreshServlet?nodeid=${node.id }')">刷新</a></td>
					</tr>
				</c:forEach>-->
			</tbody>
		</table>
	</form>

	<div class="pagination" style="text-align: center">
		<a
			href="${pageContext.request.contextPath  }/getNodesServlet?currentPage=${pb.currentPage==1?1:pb.currentPage-1}&deviceid=${deviceid}">
			<button class="layui-btn layui-btn-primary layui-btn-xs">上一页</button>
		</a>&nbsp;
		<c:if test="${pb.totalPage == '0'}">
			 	  第1页&nbsp;/&nbsp;共${pb.totalPage }页 
			 	<a
				href="${pageContext.request.contextPath  }/getNodesServlet?currentPage=${(pb.currentPage==pb.totalPage)?pb.totalPage:pb.currentPage}&deviceid=${deviceid}">
				<button id="pageBt" class="layui-btn layui-btn-primary layui-btn-xs"
					lay-submit lay-filter="pageBt"
					onclick="nextPage(${pb.currentPage},${pb.totalPage})">下一页</button>
		</c:if>
		<c:if test="${pb.totalPage != '0'}">
			  	 第${pb.currentPage }页&nbsp;/&nbsp;共${pb.totalPage }页&nbsp;          
				<a
				href="${pageContext.request.contextPath  }/getNodesServlet?currentPage=${(pb.currentPage==pb.totalPage)?pb.totalPage:pb.currentPage+1}&deviceid=${deviceid}">
				<button id="pageBt" class="layui-btn layui-btn-primary layui-btn-xs"
					lay-submit lay-filter="pageBt"
					onclick="nextPage(${pb.currentPage},${pb.totalPage})">下一页</button>
			</a>
		</c:if>
	</div>

	<script type="text/javascript"
		src="${pageContext.request.contextPath }/admin/js/nodes.js"></script>
	<script>
		layui.use('form', function() {
			var form = layui.form;
			//监听提交
			  form.on('submit(addBt)', function(data){
				  addNodes('${pageContext.request.contextPath }/addNodesFormServlet',${deviceid});
			    return false;
			  });
			
			  form.on('submit(delBt)', function(data){
				  delNodes('${pageContext.request.contextPath }/delNodesFormServlet',${deviceid});
			    return false;
			  });
		});	
	</script>

	<script type="text/javascript">
	function getXMLHttpRequest() {
		var xmlhttp;
		if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
			xmlhttp = new XMLHttpRequest();
		} else {// code for IE6, IE5
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		return xmlhttp;
	}
	/**
	 * AJAX请求
	 * 
	 * @returns
	 */
	function AJAXRequest() {
		var req = getXMLHttpRequest();
		req.onreadystatechange = function() {
			if (req.readyState == 4) {// 请求成功
				if (req.status == 200) {// 服务器响应成功
					var pbs = JSON.parse(req.responseText);
					var nodes = pbs.nodes;
					//alert(req.responseText);
					var inner = "";
					var node;
					for (var i = 0; i < nodes.length; i++) {
						node = nodes[i];
						inner = inner + "<tr><td>" + node.nodeAddr + "</td>\
						<td><a href='javascript:;'onclick=\"reName('/plc/nodeRenameFormServlet'," + node.id + ")\">" +node.nodeName +"</a></td>\
							<td>";
							if (node.light1State == true) {
								inner = inner + "开";
							} else {
								inner = inner + "关";
							}
							inner = inner + "</td>\
							<td>" + node.light1PowerPercent + "</td>\
							<td>" + node.light1Power + "</td>\
							<td>";
							if (node.light2State == true) {
								inner = inner + "开";
							} else {
								inner = inner + "关";
							}
							inner = inner + "</td>\
							<td>" + node.light2PowerPercent + "</td>\
							<td>" + node.light2Power + "</td>\
							<td>" + node.power + " W</td>\
							<td><a href='javascript:;' onclick=\"nodeControl('/plc/nodeFormServlet', " + node.id 

+"," + node.light1State + "," + node.light1PowerPercent + "," + node.light2State + "," + node.light2PowerPercent + ")\">单灯控制</a></td>\
							<td><a href='javascript:;' onclick=\"nodeRefresh('/plc/nodeRefreshServlet?nodeid=" + node.id 

+ "')\">刷新</a></td>\
						</tr>";
					}
					document.getElementById("table_body").innerHTML = inner;
				}
			}
		}
		req.open("get","${pageContext.request.contextPath }/refreshNodesServlet?deviceid=${deviceid}&currentPage=${pb.currentPage}");// 建立一个链接
		req.send(null);// 发送请求
	}

	window.onload = function() {
		AJAXRequest();
	}
	setInterval(AJAXRequest,1000*3);
	</script>
</body>
</html>