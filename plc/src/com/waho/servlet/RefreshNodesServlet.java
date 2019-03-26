package com.waho.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.waho.domain.PageBean;
import com.waho.service.UserService;
import com.waho.service.impl.UserServiceImpl;

/**
 * Servlet implementation class RefreshNodesServlet
 */
@WebServlet("/refreshNodesServlet")
public class RefreshNodesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RefreshNodesServlet() {
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
		/*Date myDate = new Date();
	    String mytime=myDate.toLocaleString();
		System.out.println(mytime+":你好");*/
		
		String deviceidString = request.getParameter("deviceid");
		String currPage = request.getParameter("currentPage"); //从上一页或下一页得到的数据
		int pageSize = 15;
		int currentPage = 1; //当前页面
		if(currPage != null) { // 第一次访问时currPage可能为空
			currentPage = Integer.parseInt(currPage);
		}
		//System.out.println(deviceidString);
		if (deviceidString != null) {
			int deviceid = Integer.parseInt(deviceidString);
			// 调用业务逻辑
			UserService userService = new UserServiceImpl();
			PageBean pb = userService.getNodesPageByDeviceid(deviceid,currentPage,pageSize);
			// 分发转向
			//request.setAttribute("deviceid", deviceid);
			//System.out.println("23"+JSON.toJSONString(pb));
			response.getWriter().write(JSON.toJSONString(pb));
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
