package com.waho.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.waho.domain.Node;
import com.waho.domain.PageBean;
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
			String deviceidStr = request.getParameter("deviceid");
			String currentPage = request.getParameter("currentPage");
			
			//每页显示数据的个数
			int pageSize = 15;
			int deviceid = Integer.parseInt(deviceidStr);
			//2.调用业务逻辑
			UserService us = new UserServiceImpl();
		    Node node = us.serachNode(deviceid,nodeAddrStr);
		    if(node != null) {
		    	//3分发转向
		    	request.setAttribute("node", node);
		    	request.getRequestDispatcher("admin/serachShowNode.jsp").forward(request, response);
		    }else {
		    	PageBean pb = us.searchNodeErrReturnNodePage(deviceid,Integer.parseInt(currentPage),pageSize);
				//3分发转向
				request.setAttribute("pb", pb);
		    	request.setAttribute("message", "未查找到相关节点");
		    	request.setAttribute("deviceid", deviceid);
		    	request.getRequestDispatcher("admin/nodes.jsp").forward(request, response);
		    	
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
