package com.waho.servlet;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.waho.service.UserService;
import com.waho.service.impl.UserServiceImpl;

/**
 * Servlet implementation class WarnningMessageDeleteServlet
 */
@WebServlet("/warnningMessageDeleteServlet")
public class WarnningMessageDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WarnningMessageDeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		//1.获取表单数据
		String alarmIdS = request.getParameter("id");
		String userid = request.getParameter("userid");
		String [] alarmId = alarmIdS.split(",");
		//2.调用业务逻辑
		if(alarmId != null) {
			UserService  us = new UserServiceImpl();
			Map<String, Object> alarmMap = us.delWarnningMessage(alarmId,Integer.parseInt(userid));
			 for (Entry<String, Object> entry : alarmMap.entrySet()) {  
		           // System.out.println(entry.getKey() + ":" + entry.getValue());  
		            if(entry.getKey() == "delNum") {
		            	 int delNum = (int) entry.getValue();
		            	 if(delNum > 0) {
		            		//3.分发转向
		     				request.setAttribute("message", "成功删除"+delNum+"条数据");
		     				request.setAttribute("alarmMap", alarmMap);
		     				request.setAttribute("userid", userid);
		     				request.getRequestDispatcher("/admin/warnningMessage.jsp").forward(request, response);
		     			}else {
		     				request.setAttribute("message", "删除失败");
		     				request.setAttribute("alarmMap", alarmMap);
		     				request.setAttribute("userid", userid);
		     				request.getRequestDispatcher("/admin/warnningMessage.jsp").forward(request, response);
		     			}
		            }
		        }  
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
