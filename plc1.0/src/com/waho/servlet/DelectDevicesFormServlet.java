package com.waho.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.waho.domain.Device;
import com.waho.domain.Node;
import com.waho.service.UserService;
import com.waho.service.impl.UserServiceImpl;

/**
 * Servlet implementation class DelectDevicesFormServlet
 */
@WebServlet("/delectDevicesFormServlet")
public class DelectDevicesFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DelectDevicesFormServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html,charset=UTF-8");
		
		//获取表单数据
		String useridString = request.getParameter("userid");
		//能取到System.out.println("useridString"+useridString);
		
		//分发转向
		if(useridString != null) {
			int userid = Integer.parseInt(useridString);
			UserService us = new UserServiceImpl();
			List<Device> list=us.getDevicesByUserid(userid);
			
			request.setAttribute("devices", list);   
			request.setAttribute("userid", request.getParameter("userid"));
	    
			request.getRequestDispatcher("/admin/delectDevicesForm.jsp").forward(request, response);
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
