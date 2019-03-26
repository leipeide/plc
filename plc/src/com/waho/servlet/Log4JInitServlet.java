package com.waho.servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;

/**
 * log4j初始化servlet,读出配置文件
 * Servlet implementation class Log4JInitServlet
 */
public class Log4JInitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Log4JInitServlet() {
		super();
		// TODO Auto-generated constructor stub
	}
   //一个servletconfig对应着一个web.xml文本中的一段配置信息，可以通过各种方法来获得web.xml中的值
	public void init(ServletConfig config) throws ServletException {
		System.out.println("Log4JInitServlet 正在初始化 log4j日志设置信息");
		String log4jLocation = config.getInitParameter("log4j-properties-location");//通过名称log4j-properties-location获取指定初始化参数的值

		ServletContext sc = config.getServletContext();   //获取servlet上下文对象

		String str = (String) sc.getInitParameter("test");//根据配置中的名获得值
		System.out.println("str:" + str);

		if (log4jLocation == null) {
			System.err.println("*** 没有 log4j-properties-location 初始化的文件, 所以使用 BasicConfigurator初始化");
			BasicConfigurator.configure();
		} else {
			String webAppPath = sc.getRealPath("/");
			String log4jProp = webAppPath + log4jLocation;
			File yoMamaYesThisSaysYoMama = new File(log4jProp);
			if (yoMamaYesThisSaysYoMama.exists()) {
				System.out.println("使用: " + log4jProp + "初始化日志设置信息");
				PropertyConfigurator.configure(log4jProp);
			} else {
				System.err.println("*** " + log4jProp + " 文件没有找到， 所以使用 BasicConfigurator初始化");
				BasicConfigurator.configure();
			}
		}
		super.init(config);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
