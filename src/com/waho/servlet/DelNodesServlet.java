package com.waho.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.waho.service.UserService;
import com.waho.service.impl.UserServiceImpl;

/**
 * Servlet implementation class DelNodesServlet
 */
@WebServlet("/delNodesServlet")
public class DelNodesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DelNodesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		//1.获取表单数据
		String deviceid = request.getParameter("deviceid");
		String[] nodeAddr = request.getParameterValues("nodeAddr");
		if(nodeAddr != null) {
			UserService  us = new UserServiceImpl();
			int result = us.delNodesCmd(Integer.parseInt(deviceid), nodeAddr);
			//3.分发转向
			if(result > 0) {
				response.getWriter().write("成功删除"+result+"个节点！");
			}else {
				response.getWriter().write("未删除节点，请重新操作！");
			}
		}else {
			response.getWriter().write("删除失败，请选择节点！");
		}
		  /*//2.调用业务逻辑
			if(nodeAddr != null) {
				UserService  us = new UserServiceImpl();
				us.deleteNodesCmd(Integer.parseInt(deviceid), nodeAddr);
				response.getWriter().write("提交成功！");
			}else {
				response.getWriter().write("删除失败，请选择节点！");
			}
			*/
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
