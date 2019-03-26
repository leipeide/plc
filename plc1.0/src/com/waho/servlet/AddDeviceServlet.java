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
 * Servlet implementation class AddDeviceServlet
 */
@WebServlet("/addDeviceServlet")
public class AddDeviceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddDeviceServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		//1.获取窗口的表单数据：即集控器的MAC,和userid用户id；
		String useridString = request.getParameter("userid");
		String deviceMac = request.getParameter("deviceMac");
		// 调用业务逻辑
		UserService us = new UserServiceImpl();
		int userid= Integer.parseInt(useridString);
		boolean result1 = us.addDeviceToUserByDeviceMac(deviceMac, userid);//result是device是添加成功
		// 分发转向
		if(result1 == true) {
				response.getWriter().write("添加集控器成功");
		}else {
			response.getWriter().write("添加集控器失敗");
			return;
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
