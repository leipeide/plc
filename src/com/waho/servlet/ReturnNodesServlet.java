package com.waho.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.waho.dao.NodeDao;
import com.waho.dao.impl.NodeDaoImpl;
import com.waho.domain.Node;
import com.waho.domain.PageBean;
import com.waho.service.UserService;
import com.waho.service.impl.UserServiceImpl;

/**
 * Servlet implementation class ReturnNodesServlet
 */
@WebServlet("/returnNodesServlet")
public class ReturnNodesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReturnNodesServlet() {
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
		String nodeAddr = request.getParameter("nodeAddr");
		//2.调用业务逻辑(点击图表页面的“返回”按钮，根据nodeAddr返回到节点页面)
		if (nodeAddr != null) {
			NodeDao nDao = new NodeDaoImpl();
			try {
				Node node = nDao.selectNodeByNodeAddr(nodeAddr);
				UserService userService = new UserServiceImpl();
				PageBean pb = userService.returnNodesPageByReturnButton(nodeAddr,node.getDeviceid());
				//3.分发转向
				request.setAttribute("pb", pb);
				request.setAttribute("deviceid", node.getDeviceid());
				request.getRequestDispatcher("/admin/nodes.jsp").forward(request, response);	
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}else {
			response.getWriter().write("请重新操作！");
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
