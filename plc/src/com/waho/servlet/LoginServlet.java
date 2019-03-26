package com.waho.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.concurrent.Delayed;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jasper.tagplugins.jstl.core.Out;

import com.ndktools.javamd5.Mademd5;
import com.oracle.jrockit.jfr.RequestableEvent;
import com.waho.domain.User;
import com.waho.service.UserService;
import com.waho.service.impl.UserServiceImpl;

/**
 * 登录servlet
 */
@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");   //设置对客户端请求进行重新编码的编码
		response.setContentType("text/html; charset=UTF-8"); //指定对服务器响应进行重新编码的编码
		
		// 获取表单数据
		String username = request.getParameter("username");
		Mademd5 md = new Mademd5();//创建MD5加密对象
		String passwordUnMD5 = request.getParameter("password");
		String password = null;
		if (passwordUnMD5 != null) {
			password = md.toMd5(passwordUnMD5); //对密码进行加密
			// 调用业务逻辑
			UserService userService = new UserServiceImpl();
			Map<String, Object> result = userService.login(username,password);
			// 分发转向
			if (null == result) {
				// 跳转error页面 or 返回错误信息
				request.getRequestDispatcher("/admin/loginErr.jsp").forward(request, response);
			} else {
				//3.创建Session对象保存User对象
		        request.getSession().setAttribute("result", result);
				//request.setAttribute("result", result);
				request.getRequestDispatcher("/admin/home.jsp").forward(request, response);
			}
		} else {
			request.getRequestDispatcher("/admin/loginErr.jsp").forward(request, response);
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
