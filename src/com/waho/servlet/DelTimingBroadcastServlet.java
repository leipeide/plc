package com.waho.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.waho.domain.Timing;
import com.waho.service.UserService;
import com.waho.service.impl.UserServiceImpl;

/**
 * Servlet implementation class DelTimingBroadcastServlet
 */
@WebServlet("/delTimingBroadcastServlet")
public class DelTimingBroadcastServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DelTimingBroadcastServlet() {
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
		String[] timingId = request.getParameterValues("timingId");
		//2.处理业务逻辑
		if(timingId != null) {
			UserService us = new UserServiceImpl();
			List<Timing> timing = us.delTimingBroadcast(timingId);
			//3.分发转向
			request.setAttribute("result", timing);
			request.getRequestDispatcher("/admin/delTimingBroadcastForm.jsp").forward(request, response);
		}else {
			response.getWriter().write("未选择操作对象，请重新操作！");
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
