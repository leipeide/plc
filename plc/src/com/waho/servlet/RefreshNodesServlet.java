package com.waho.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.waho.domain.PageBean;
import com.waho.service.UserService;
import com.waho.service.impl.UserServiceImpl;

/**
 * Servlet implementation class RefreshNodesServlet
 */
@WebServlet("/refreshNodesServlet")
public class RefreshNodesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RefreshNodesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		// 获取表单数据
		String deviceidString = request.getParameter("deviceid");
		String currPage = request.getParameter("currentPage");
		//1.每页显示数据的个数
		int pageSize = 15;
		//2.当前页面
		int currentPage = Integer.parseInt(currPage);
		// 调用业务逻辑
		if (deviceidString != null) {
			int deviceid = Integer.parseInt(deviceidString);
			UserService userService = new UserServiceImpl();
			PageBean pb = userService.refreshNodesPageByDeviceid(deviceid,currentPage,pageSize);
			// 分发转向
			response.getWriter().write(JSON.toJSONString(pb));
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
