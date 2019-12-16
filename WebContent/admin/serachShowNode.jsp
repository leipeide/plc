<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" >
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/layui/css/layui.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/layui/layui.js"></script>
<title>Insert title here</title> 
</head>
<body>
	<form  action="" method="post">   
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
					<th>辅灯状态</th>
					<th>辅灯亮度</th>
					<th>节点功率</th>
					<th>单灯控制</th>
					<th>更新数据</th>
				</tr>
			</thead>
			<tbody id="table_body">
					<tr>
						<td>${node.nodeAddr }&nbsp;&nbsp;<a href="${pageContext.request.contextPath }/nodeMessageChartServlet" ><i class="layui-icon layui-icon-chart"></i></a>
						</td>
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
			</tbody>
		</table>
    </form>
<%-- 	<script type="text/javascript" src="${pageContext.request.contextPath }/admin/js/nodes.js"></script> --%>
	<script type="text/javascript">

	//3* 获取XMLHttpRequest对象
	function getXMLHttpRequest() {
		var xmlhttp;
		if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
			xmlhttp = new XMLHttpRequest();
		} else {// code for IE6, IE5
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		return xmlhttp;
	}
	//2 *节点表格内刷新AJAX方法
	function AJAXRefreshRequest(url, func) {
		var req = getXMLHttpRequest();
		req.onreadystatechange = function() {
			if (req.readyState == 4) {// 请求成功
				if (req.status == 200) {// 服务器响应成功
					func(req);
				}
			}
		}
		req.open("get", url);// 建立一个链接
		req.send(null);// 发送请求
		
	}
	//1 * 刷新节点信息
	function nodeRefresh(url) {
		// ajax请求servlet，在响应成功回调函数中调用layui透明弹窗，刷新指令发送成功
		AJAXRefreshRequest(url, function(req) {
			layui.use('layer', function() {
				var layer = layui.layer;
				layer.msg(req.responseText, {
					time : 2000, // 2s后自动关闭
				});
			});
		});
	}

	
	
	//单灯控制方法
	function nodeControl(url, nodeid, light1State, light1PowerPercent, light2State, light2PowerPercent) {
		layui.use('layer', function() {
			var layer = layui.layer;
			layer.open({
				area : [ 'auto', '350px' ],
				btnAlign : 'c',
				resize : false,
				content : url + "?nodeid=" + nodeid + "&light1State=" + light1State + "&light1PowerPercent=" + light1PowerPercent + "&light2State=" + light2State + "&light2PowerPercent=" + light2PowerPercent,
				closeBtn : 1,
				type : 2,
			    /*btn : '关闭',
				yes : function(index, layero) {
					// 按钮【按钮一】的回调
					layer.close(index);
				},*/
				cancel : function() {
					// 右上角关闭回调
					// return false 开启该代码可禁止点击该按钮关闭
				}
			});
		});
	}

	//添加节点方法
	function addNodes(url,deviceid){
				layui.use('layer', function() {
					var layer = layui.layer;
					layer.open({
						area : [ 'auto','350px'],
						offset:['0px','350px'],
						btnAlign : 'c',
						resize : false,
						content : url + "?deviceid=" + deviceid,
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

	//删除节点方法
	function delNodes(url,deviceid){
		layui.use('layer', function() {
			var layer = layui.layer;
			layer.open({
				area : [ '300px','350px'],
				offset:['0px','350px'],
				btnAlign : 'c',
				resize : false,
				content : url + "?deviceid=" + deviceid ,
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
	
	//节点重命名方法
	function reName(url,nodeid){
		layui.use('layer', function() {
			var layer = layui.layer;
			layer.open({
				area : [ 'auto','350px'],
				offset:['0px','350px'],
				btnAlign : 'c',
				resize : false,
				content : url+ "?nodeid=" + nodeid ,
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

	//控制节点页面为尾页时下一页不可用
	function  nextPage(currPage,totalPage){
		if(totalPage == 0 || totalPage == currPage){
			document.getElementById("pageBt").disabled = true;
		}else{
			document.getElementById("pageBt").disabled = false;
		}
	}
	
	</script>
</body>
</html>