package com.waho.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.waho.service.UserService;
import com.waho.service.impl.UserServiceImpl;
import com.waho.domain.Node;
import com.waho.domain.PageBean;

/**
 * 根据集控器id获取节点信息，body跳转到nodes.jsp页面
 */
@WebServlet("/getNodesServlet")
public class GetNodesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetNodesServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		// 获取表单数据
		String deviceidString = request.getParameter("deviceid");
		String currPage = request.getParameter("currentPage"); 
		//1.每页显示数据的个数
		int pageSize = 15;
		//2.currentPage为当前页面，初次访问节点页面上时currentPage初始化设置为1
		int currentPage = 1;
		//3.非初次访问节点页面，从上一页或下一页按钮得到的数据currPage
		if(currPage != null) {
			currentPage = Integer.parseInt(currPage);
		}
		
		// 调用业务逻辑
		if (deviceidString != null) {
			int deviceid = Integer.parseInt(deviceidString);
			UserService userService = new UserServiceImpl();
			PageBean pb = userService.getNodesPageByDeviceid(deviceid,currentPage,pageSize);
			// 分发转向
			request.setAttribute("pb", pb);
			request.setAttribute("deviceid", deviceid);
			request.getRequestDispatcher("/admin/nodes.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
