package com.waho.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.waho.service.UserService;
import com.waho.service.impl.UserServiceImpl;

/**
 * Servlet implementation class SerachWMServlet
 */
@WebServlet("/serachWMServlet")
public class SerachWMServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SerachWMServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */ 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		//1.后获取表单数据
		String userid = request.getParameter("userid");
		String deviceMac  = request.getParameter("selectDeviceMac");
		String nodeAddr  = request.getParameter("selectNodeAddr");
		String type  = request.getParameter("selectType");
		String date  = request.getParameter("selectDate");
		//2.调用业务逻辑
		if(deviceMac != null || nodeAddr != null || type != null || date != null) {
			UserService us = new UserServiceImpl();
			Map<String, Object> alarmMap = us.serachWarnningMessageByFactor(Integer.parseInt(userid),deviceMac,nodeAddr,type,date);
			//System.out.println(JSON.toJSONString(alarmMap));
			//3.分发转向
			request.setAttribute("userid", userid);
			request.setAttribute("alarmMap", alarmMap);
			request.getRequestDispatcher("/admin/warnningMessage.jsp").forward(request, response);
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
