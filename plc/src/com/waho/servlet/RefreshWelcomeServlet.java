package com.waho.servlet;

import java.io.IOException;
import java.util.Date;
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
 * Servlet implementation class RefreshWelcomeServlet
 */
@WebServlet("/refreshWelcomeServlet")
public class RefreshWelcomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RefreshWelcomeServlet() {
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
		//Date myDate = new Date();
	    //String mytime=myDate.toLocaleString();
		//System.out.println(mytime+":2019");
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		//System.out.println(":2019"+username);
		//System.out.println(":2019"+password);
		// 调用业务逻辑
		UserService userService = new UserServiceImpl();
		Map<String, Object> result = userService.login(username, password);
		
		//System.out.println("23"+JSON.toJSONString(result));	
		response.getWriter().write(JSON.toJSONString(result));
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
