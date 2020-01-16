package com.waho.servlet;

import java.io.IOException;
import java.sql.Time;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.waho.service.UserService;
import com.waho.service.impl.UserServiceImpl;

/**
 * Servlet implementation class AddTimingServlet
 */
@WebServlet("/addTimingServlet")
public class AddTimingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddTimingServlet() {
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
		String planid = request.getParameter("planid");
		String light1State = request.getParameter("light1State");
		String light1PowerPercent = request.getParameter("light1PowerPercent");
		String light2State = request.getParameter("light2State");
		String light2PowerPercent = request.getParameter("light2PowerPercent");
		String minutes = request.getParameter("minutes");
		String hours = request.getParameter("hours");
		Time time = new Time(Integer.parseInt(hours),Integer.parseInt(minutes),0);
		//System.out.println("planid:"+planid+"---llight1State:"+light1State+"---light2State:"+light2State+"---light2PowerPercent:"+light2PowerPercent+"---light1PowerPercent"+light1PowerPercent);
		//System.out.println("TIME:"+time);
		if(planid != null) {
			UserService us = new UserServiceImpl();
			us.addTimingByPlanId(Integer.parseInt(planid),light1State,light1PowerPercent,light2State,light2PowerPercent,time);
			response.getWriter().write("提交成功!");
			return;
		}else {
			response.getWriter().write("提交失败!");
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
