package com.waho.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ndktools.javamd5.Mademd5;
import com.waho.service.UserService;
import com.waho.service.impl.UserServiceImpl;

/**
 * Servlet implementation class Register1Servlet
 */
@WebServlet("/register1Servlet")
public class Register1Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register1Servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		//1.获取表单数据，对秘密进行加密处理
		Mademd5 md = new Mademd5();
		String password = md.toMd5(request.getParameter("password"));
		String passwordrep =md.toMd5(request.getParameter("passwordrep"));
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		//2.处理业务逻辑
		UserService us = new UserServiceImpl();
		if(us.checkUserByUsername(username)) {
			request.setAttribute("RegistFailed", "该用户已注册！");
			request.getRequestDispatcher("/admin/register.jsp").forward(request, response);
			return;
		} else if(!password.equals(passwordrep)){
			request.setAttribute("RegistFailed", "两次密码不一致！");
			request.getRequestDispatcher("/admin/register.jsp").forward(request, response);
			return;
		}else if(us.registUser(username, password, email)) {
			//response.setHeader("refresh","3;/plc");//客户端
			response.setHeader("refresh","3;/plc1");//本地测试
			response.getWriter().write("注册成功，3秒后跳转到登录页面！");
		}else {
			request.setAttribute("RegistFailed", "注册失败，请重新注册！");
			request.getRequestDispatcher("/admin/register.jsp").forward(request, response);
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
