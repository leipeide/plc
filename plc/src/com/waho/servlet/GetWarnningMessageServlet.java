package com.waho.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.waho.service.UserService;
import com.waho.service.impl.UserServiceImpl;

/**
 * Servlet implementation class GetWarnningMessageServlet
 */
@WebServlet("/getWarnningMessageServlet")
public class GetWarnningMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetWarnningMessageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		//1.获取表单上数据
		String userid = request.getParameter("userid");
		//2.调用业务逻辑
		if(userid != null) {
			UserService us = new UserServiceImpl();
			Map<String, Object> alarmMap = us.getWarnningMessageById(Integer.parseInt(userid));
			response.getWriter().write(JSON.toJSONString(alarmMap));
			//System.out.println("json对象："+alarmMap);
		}else {
			response.getWriter().write("未获取用户id，请重新操作");
		}
		//3.分发转向
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
