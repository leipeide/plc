package com.waho.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.waho.dao.DeviceDao;
import com.waho.dao.NodeDao;
import com.waho.dao.impl.DeviceDaoImpl;
import com.waho.dao.impl.NodeDaoImpl;
import com.waho.service.UserService;
import com.waho.service.impl.UserServiceImpl;

/**
 * Servlet implementation class NodeChartServlet
 */
@WebServlet("/nodeChartServlet")
public class NodeChartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NodeChartServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		//获取表单数据
		String nodeAddr = request.getParameter("nodeAddr");
		String Date = request.getParameter("Date");
		//调用业务逻辑
		if(Date != null) {
			UserService us = new UserServiceImpl();
			Map<String, Object> recordMap = us.DateRangeNodeChartMessage(nodeAddr,Date);
			response.getWriter().write(JSON.toJSONString(recordMap));
			//System.out.println("json对象："+recordMap);
		}else {
			response.getWriter().write("请选择时间");
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
