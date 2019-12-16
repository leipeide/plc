package com.waho.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 根据nodeid跳转节点控制form表单
 */
@WebServlet("/nodeFormServlet")
public class NodeFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NodeFormServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");   //设置对客户端请求进行重新编码的编码
		response.setContentType("text/html; charset=UTF-8"); //指定对服务器响应进行重新编码的编码
		//1.获取表单数据
		//2.调用业务逻辑
		//3.分发转向
		request.setAttribute("nodeid", request.getParameter("nodeid"));
		request.setAttribute("light1State", request.getParameter("light1State"));
		request.setAttribute("light1PowerPercent", request.getParameter("light1PowerPercent"));
		request.setAttribute("light2State", request.getParameter("light2State"));
		request.setAttribute("light2PowerPercent", request.getParameter("light2PowerPercent"));

		request.getRequestDispatcher("/admin/nodeForm.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
