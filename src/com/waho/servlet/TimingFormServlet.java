package com.waho.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.waho.domain.TimingPlan;
import com.waho.service.UserService;
import com.waho.service.impl.UserServiceImpl;

/**
 * Servlet implementation class TimingFormServlet
 */
@WebServlet("/timingFormServlet")
public class TimingFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TimingFormServlet() {
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
		String useridStr = request.getParameter("userid");
		//2.处理业务逻辑
	    if(useridStr != null) {
	    	int userid = Integer.parseInt(useridStr);
			UserService us = new UserServiceImpl();	
			List<TimingPlan> timingPlan = us.getTimingPlanResultByUserid(userid);
			//3.分发转向
		    request.setAttribute("timingPlan", timingPlan);
		    request.setAttribute("userid", userid);
		    request.getRequestDispatcher("/admin/timingSettingForm.jsp").forward(request, response);
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
