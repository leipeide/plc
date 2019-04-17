package com.waho.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.waho.service.UserService;
import com.waho.service.impl.UserServiceImpl;

/**
 * Servlet implementation class WarnningMessageDeleteServlet
 */
@WebServlet("/warnningMessageDeleteServlet")
public class WarnningMessageDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WarnningMessageDeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		// 获取表单数据
		String[] alarmId = request.getParameterValues("id");
		//调用业务逻辑
		if(alarmId != null) {
			UserService  us = new UserServiceImpl();
			Boolean result = us.delWarnningMessage(alarmId);
			//分发转向
			if(result) {
				//PrintWriter out = response.getWriter();
//		    	out.print("<link rel=\"stylesheet\"\r\n" + 
//		    			"	href=\"${pageContext.request.contextPath }/admin/layui/css/layui.css\">\r\n" + 
//		    			"<script type=\"text/javascript\"\r\n" + 
//		    			"	src=\"${pageContext.request.contextPath }/admin/layui/layui.js\"></script>"
//		    			+ "<script>layui.use('layer', function() {\r\n" + 
//		    			"alert('2364');	var layer = layui.layer;\r\n" + 
//		    			"	layer.alert('只想简单的提示');\r\n" + 
//		    			"	});");
				response.getWriter().write("删除成功");
			}else {
				response.getWriter().write("删除失败");
//				PrintWriter out = response.getWriter();
//				out.print("<script>alert('失败');"
//		    			+ "window.history.back(-1)</script>");
			}
		}else {
			response.getWriter().write("删除失败，请选择信息");
//			PrintWriter out = response.getWriter();
//	    	out.print("<script>alert('删除失败，请选择信息');"
//	    			+ "window.history.back(-1)</script>");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
