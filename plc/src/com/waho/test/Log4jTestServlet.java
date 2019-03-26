package com.waho.test;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 * Servlet implementation class Log4jTestServlet
 */
@WebServlet("/log4jTestServlet")
public class Log4jTestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(this.getClass());  //Logger创建，就是根据类名实例化一个静态的全局日志记录器

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Log4jTestServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//插入记录信息
		logger.info("this is my info message");    //粗粒度信息事件，突出强调应用程序的运行过程    
		logger.debug("this is my debug message"); //细粒度信息事件，对调试应用程序是非常有帮助的  
		logger.warn("this is my warn message");   //可能出现潜在错误的情形    
		logger.error("this is my error message"); //虽然发生错误事件，但仍然不影响系统的继续运行  
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
