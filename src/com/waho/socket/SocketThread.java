package com.waho.socket;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.waho.dao.UserDao;
import com.waho.dao.impl.UserDaoImpl;

/**
 * socket 线程类
 * 
 * @author zhangzhongwen
 * 
 */
public class SocketThread extends Thread {
	private ServerSocket serverSocket = null;

	public SocketThread(ServerSocket serverScoket) {
		try {
			if (null == serverSocket) {
				this.serverSocket = new ServerSocket(7002);//客户端服务器端口号
				//this.serverSocket = new ServerSocket(7005);//本地服务器端口号
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void run() {
		
		//线程开启，等待10ms以后，每隔20s执行一次
		new Timer().schedule(new TimerTask() {
			public void run() {
				//3.每天2点定时清除用户验证码和操作次数
				Date nowTime = new Date();
				Calendar calendar3 = Calendar.getInstance();
				calendar3.set(Calendar.HOUR_OF_DAY, 2); // 控制时
				calendar3.set(Calendar.MINUTE,30);    // 控制分
				calendar3.set(Calendar.SECOND,0);    // 控制秒
				Date Calendar3time = calendar3.getTime();//获取日历时间
				if((nowTime.getTime() -  Calendar3time.getTime()) >= 0 
						&& (nowTime.getTime() -  Calendar3time.getTime() <= 1000*60)) {
					
					UserDao userDao = new UserDaoImpl();
					String verCode = null;
					int operateNum = 0;
					try {
						
						userDao.clearVercodeAndOpreateNum(verCode,operateNum);
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		}, 10, 30 * 1000);// 毫秒
		
	
		
		while (!this.isInterrupted()) { //this.isInterrupted检测调用该方法的线程是否被中断，中断状态不会被清除。线程没有被中断，执行while
			try {
				Socket socket = serverSocket.accept();  //接收socket
				if (socket != null && !socket.isClosed()) {
					// 处理接受的数据---socketOperate
					new SocketOperate(socket).start();
//					socket.setSoTimeout(10 * 1000 * 1);//链接超时时长，单位毫秒，0为死等
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void closeSocketServer() {
		try {
			if (null != serverSocket && !serverSocket.isClosed()) {
				serverSocket.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}
