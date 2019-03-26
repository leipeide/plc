package com.waho.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.waho.domain.Node;
import com.waho.service.UserService;
import com.waho.service.impl.UserServiceImpl;

/**
 * Servlet implementation class DelNodesFormServlet
 */
@WebServlet("/delNodesFormServlet")
public class DelNodesFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DelNodesFormServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		//获取表单数据
		String deviceidString = request.getParameter("deviceid");
		
		// 调用业务逻辑
		if (deviceidString != null) {
			int deviceid = Integer.parseInt(deviceidString);
			UserService userService = new UserServiceImpl();
			List<Node> list = userService.getNodesByDeviceid(deviceid);
			request.setAttribute("nodes", list);   
		//分发转向
		request.setAttribute("deviceid", request.getParameter("deviceid"));
		request.getRequestDispatcher("/admin/delNodesForm.jsp").forward(request, response);
		
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
