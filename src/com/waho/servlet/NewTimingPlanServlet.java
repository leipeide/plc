package com.waho.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.waho.domain.Device;
import com.waho.service.UserService;
import com.waho.service.impl.UserServiceImpl;

/**
 * Servlet implementation class NewTimingPlanServlet
 */
@WebServlet("/newTimingPlanServlet")
public class NewTimingPlanServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewTimingPlanServlet() {
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
		String planName = request.getParameter("planName");
		String deviceMac = request.getParameter("deviceMac");
		String Date = request.getParameter("Date");
		//2.业务逻辑处理
		UserService us =  new UserServiceImpl();
		Map<String, Object> resultMap = us.newAddTimingPlan(planName,deviceMac,Date);
		List<Device> devices = null;
		for(Entry<String, Object> map : resultMap.entrySet()){
			if(map.getKey() == "devices") {
				devices = (List<Device>) map.getValue(); 
			}
		}
		for(Entry<String, Object> map : resultMap.entrySet()){
			//System.out.println(map.getKey()+"  "+map.getValue());
			if((map.getKey()).equals("result") && (map.getValue()).equals(1)) {
				//3.分发转向
				request.setAttribute("message", "新建成功");
				request.setAttribute("devices", devices);
				request.getRequestDispatcher("/admin/newTimingPlanForm.jsp").forward(request, response);
			}else {
				//3.分发转向
				request.setAttribute("message", "该计划名称已存在，请重新新建");
				request.setAttribute("devices", devices);
				request.getRequestDispatcher("/admin/newTimingPlanForm.jsp").forward(request, response);
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
