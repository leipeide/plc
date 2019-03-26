package com.waho.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.waho.dao.NodeDao;
import com.waho.dao.impl.NodeDaoImpl;
import com.waho.domain.Node;
import com.waho.service.UserService;
import com.waho.service.impl.UserServiceImpl;

/**
 * Servlet implementation class AddNodesServlet
 */
@WebServlet("/addNodesServlet")


public class AddNodesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
   /**
    * @see HttpServlet#HttpServlet()
    */
   public AddNodesServlet() {
       super();
       // TODO Auto-generated constructor stub
   }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("null")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		//获取表单信息
		String deviceid = request.getParameter("deviceid");
		String[] nodeAddrString = request.getParameterValues("nodeAddr");
		String  selectedString = request.getParameter("selected");
		//System.out.println("select"+selectedString);
		if(Integer.parseInt(selectedString) == 0) {
			response.getWriter().write("请选择需要添加节点的数量");
			return;
		}else {
			for(int i=0;i<nodeAddrString.length;i++) {
				if(nodeAddrString[i]=="") {
					response.getWriter().write("添加失败，请完整的输入节点地址!");
					return;
				}
			}
		}
		
		/****/
		//避免同一集控器下重複添加同一節點
			NodeDao nDao = new NodeDaoImpl();
			List<Node> nodeAll;
			Collection<String> list = new ArrayList<>();
			try {
				//查找该集控器下的所有节点
				nodeAll = nDao.selectNodesByDeviceid(Integer.parseInt(deviceid));
				//获取节点集合中的节点地址属性集合
				List<String> nodeAddress = nodeAll.stream().map(Node :: getNodeAddr).collect(Collectors.toList());
				//将节点地址集合转化为数组
				String[] nodeAddressArr =new String[nodeAddress.size()]; 
				nodeAddress.toArray(nodeAddressArr);
				//判断该集控器下的节点是否包含即将要添加的节点，若该节点已存在，则不能添加
					 for(int k = 0; k < nodeAddrString.length; k++) {
						 boolean result = Arrays.asList(nodeAddressArr).contains(nodeAddrString[k]);
						 if(result) {
						 		response.getWriter().write(nodeAddrString[k]+"此节点已添加!");
						 		return;
						 	}else {
						 		list.add(nodeAddrString[k]);
						 	}
					
				}
				//调用业务逻辑
				UserService us = new UserServiceImpl();
				String[] arrNode =new String[list.size()];
				us.addNodesCmd(Integer.parseInt(deviceid),list.toArray(arrNode));
				//分发转向
				response.getWriter().write("提交成功!");
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
			}
		/***/
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
		/****/
		//避免同一集控器下重複添加同一節點
			/*NodeDao nDao = new NodeDaoImpl();
			List<Node> nodeAll;
			String[] list = null ;		
			//Collection<String> list = new ArrayList();
			try {
				nodeAll = nDao.selectNodesByDeviceid(Integer.parseInt(deviceid));
				for(int i = 0; i < nodeAll.size(); i++ ) {
					 String nodeAddress  = nodeAll.get(i).getNodeAddr();
					 System.out.println("集控器下的節點："+nodeAddress);
					 for(int k = 0; k < nodeAddrString.length; k++) {
						 System.out.println("節點"+nodeAddrString[k]);
						 	if(nodeAddress.equals(nodeAddrString[k])) {
						 		list[i]=nodeAddrString[k];
						    	//list.add(nodeAddrString[k]);
						    	//System.out.println("list[i]"+list[i]);
						    	System.out.println("2019");
						 	}if(nodeAddress == nodeAddrString[k]) {
						 		response.getWriter().write("此節點已添加!");
						 		System.out.println("2018");
						 		//return;
						 	}
					 }
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
			}
		System.out.println("有效節點地址："+list);
		/*for (int i = 0; i < list.size(); i++) {
			String[] nodeAddrStr1 = (String[]) ((ArrayList) list).get(i);
	    }*/
		/***/
		
