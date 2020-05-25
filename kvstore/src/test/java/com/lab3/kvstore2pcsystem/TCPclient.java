package com.lab3.kvstore2pcsystem;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class TCPclient {
	public static void main(String[] args) throws IOException{
		//创建客户端Socket对象  这一步如果成功 说明创建连接成功
		Socket s = new Socket("192.168.1.3", 23333);
		System.out.println("本机: "+s.getLocalAddress().getHostAddress()+": "+s.getLocalPort());
		System.out.println("服务器："+s.getRemoteSocketAddress());
		
//		System.out.println("input the text you want to send:");
//		Scanner scanner = new Scanner(System.in);
//		String nextLine = scanner.nextLine();
		
		//获取输出流
		OutputStream os = s.getOutputStream();
		
		//输出数据
//		os.write(nextLine.getBytes());
		os.write("*4\r\n$3\r\nSET\r\n$7\r\nCS06142\r\n$5\r\nCloud\r\n$9\r\nComputing\r\n".getBytes());
		//释放资源
		os.close();
		s.close();
		//Connection refused: connect  TCP协议一定要先开启服务器
	}
}