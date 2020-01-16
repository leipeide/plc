layui.use('element', function() {
	var element = layui.element;

});

/**
 * 左侧导航栏点击集控器名称
 * @param id
 * 集控器id
 * @returns
 */
function deviceOnclick(url,id) {
	document.getElementById('body-div').innerHTML = "<iframe style='min-height: 500px' name='fname' frameborder='0' scrolling='yes' width='100%' src='"+url+"?deviceid="+id+"' class='body-frame'></iframe>";
}
function warnningOnclick(url,id) {
	document.getElementById('body-div').innerHTML = "<iframe style='min-height: 500px' name='fname' frameborder='0' scrolling='yes' width='100%' src='"+url+"?userid="+id+"' class='body-frame'></iframe>";
	
}
function timingOnclick(url,userid) {
	document.getElementById('body-div').innerHTML = "<iframe style='min-height: 500px' name='fname' frameborder='0' scrolling='yes' width='100%' src='"+url+"?userid="+userid+"' class='body-frame'></iframe>";
}
/**
 * 点击首页实现页面刷新
 * @returns
 */
function refresh() { 
	window.location.reload();
}

/**
 * 添加集控器方法
 * @param url
 * @param userid
 * @returns
 */
function addDevice(url, userid){
			layui.use('layer', function() {
				var layer = layui.layer;
				layer.open({
					area: ['auto', 'auto'],
					offset:['60px','550px'],
					btnAlign: 'c',
					resize: false,
					content : url+"?userid="+userid,
					closeBtn: 1,
					type: 2,
					/*btn : '关闭',
					yes : function(index, layero) {
						// 按钮【按钮一】的回调
						layer.close(index);
					},*/
					cancel : function() {
						location.reload();
						// 右上角关闭回调
					}
				});
			});
		}

/**
 * 删除集控器方法
 * @param url
 * @param userid
 * @returns
 */
function removeDevice(url,userid) {
	layui.use('layer', function() {
		var layer = layui.layer;
		layer.open({
			area: ['350px', '400px'],
			offset:['60px','550px'],
			btnAlign: 'c',
			resize: false,
			content : url + "?userid=" + userid,
			closeBtn: 1,
			type: 2,
			cancel : function() {
				// 右上角关闭回调
				location.reload();
			}
		});
	});
}

/**
 * 创建form携带userid跳转到getUserMessageServlet/location跳转
 * @param userid
 * @returns
 */
function um(url){
	location.href=url;
}
