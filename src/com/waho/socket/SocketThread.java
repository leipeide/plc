package com.waho.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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
