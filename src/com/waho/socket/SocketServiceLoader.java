package com.waho.socket;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 将socket service随tomcat启动
 * 
 * @author zhangzhongwen
 * 
 */
public class SocketServiceLoader implements ServletContextListener {      
	// socket server 线程
	private SocketThread socketThread;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		//当Servlet 容器终止Web 应用时调用该方法;在调用该方法之前，容器会先销毁所有的Servlet 和Filter 过滤器。
		if (null != socketThread && !socketThread.isInterrupted()) {
			socketThread.closeSocketServer();
			socketThread.interrupt();
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		//当Servlet 容器启动Web 应用时调用该方法。在调用完该方法之后，容器再对Filter 初始化，
		//并且对那些在Web 应用启动时就需要被初始化的Servlet 进行初始化。
		if (null == socketThread) {
			// 新建线程类
			socketThread = new SocketThread(null);
			// 启动线程
			socketThread.start();
		}
	}

}