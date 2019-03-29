package com.waho.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.waho.domain.Node;
import com.waho.service.UserService;
import com.waho.service.impl.UserServiceImpl;

/**
 * Servlet implementation class NodeSerachServlet
 */
@WebServlet("/nodeSerachServlet")
public class NodeSerachServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NodeSerachServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			//1.获取表单数据：搜索的节点地址
			String nodeAddrStr = request.getParameter("serach");
			//2.调用业务逻辑
			UserService us = new UserServiceImpl();
		    Node node = us.serachNode(nodeAddrStr);
			//3.分发转向
		    if(node != null) {
		    	request.setAttribute("node", node);
		    	request.getRequestDispatcher("admin/serachsShowNode.jsp").forward(request, response);
		    }else {
		    	PrintWriter out = response.getWriter();
		    	out.print("<script>alert('请输入正确的节点地址');"
		    			+ "window.history.back(-1)</script>");
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
