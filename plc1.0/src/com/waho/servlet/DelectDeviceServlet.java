package com.waho.servlet;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.waho.domain.Device;
import com.waho.service.UserService;
import com.waho.service.impl.UserServiceImpl;

/**
 * Servlet implementation class DelectDeviceServlet
 */
@WebServlet("/delectDeviceServlet")
public class DelectDeviceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DelectDeviceServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		//1.获取窗口的表单数据：即集控器的id和用户id；
		String userid = request.getParameter("userid");
		String[] arrdeviceName = request.getParameterValues("check");
		// 调用业务逻辑
		UserService us = new UserServiceImpl();
		if(arrdeviceName != null) {
			 boolean result2 = us.removeDeviceByDeviceName(arrdeviceName,Integer.parseInt(userid));	
			//分发转向
			if(result2 == true) {
				response.getWriter().write("删除集控器成功");
			}else {
				response.getWriter().write("删除集控器失败");
			}
		}else{
			response.getWriter().write("删除失败，请选择集控器!");
			}
		}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
