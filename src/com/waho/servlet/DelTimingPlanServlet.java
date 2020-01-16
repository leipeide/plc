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
 * Servlet implementation class DelTimingBroadcastServlet
 */
@WebServlet("/delTimingPlanServlet")
public class DelTimingPlanServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DelTimingPlanServlet() {
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
		String[] arr = request.getParameterValues("planId");
		if(arr != null) {
			//2.处理业务逻辑
			UserService us = new UserServiceImpl();
			int result = us.delTimingPlanById(arr);
				if(result > 0) {
					//3.分发转向
					response.getWriter().write("删除"+result+"个计划！");
				}else {
					response.getWriter().write("删除失败，请重新操作！");
				}
		}else {
			response.getWriter().write("未选择操作对象，请重新操作！");
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
