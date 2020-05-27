package com.lab3.kvstore2pcsystem;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class TCPclient {
	public static void main(String[] args) throws IOException {
//		//创建客户端Socket对象  这一步如果成功 说明创建连接成功
//		Socket s = new Socket();
//		s.bind(new InetSocketAddress(8787));
//		s.connect(new InetSocketAddress("localhost",8001));
//		System.out.println("本机: "+s.getLocalAddress().getHostAddress()+": "+s.getLocalPort());
//		System.out.println("服务器："+s.getRemoteSocketAddress());
//
////		System.out.println("input the text you want to send:");
////		Scanner scanner = new Scanner(System.in);
////		String nextLine = scanner.nextLine();
//
//		//获取输出流
//		OutputStream os = s.getOutputStream();
//
//		//输出数据
////		os.write(nextLine.getBytes());
//		os.write("*4\r\n$3\r\nSET\r\n$7\r\nCS06142\r\n$5\r\nCloud\r\n$9\r\nComputing\r\n".getBytes());
////		os.write("*2\r\n$3\r\nGET\r\n$7\r\nCS06142\r\n".getBytes());
////		os.write("*2\r\n$3\r\nDEL\r\n$7\r\nCS06142\r\n".getBytes());
//		//释放资源
//		os.close();
//		s.close();
//		//Connection refused: connect  TCP协议一定要先开启服务器

		//1:创建客户端的套接字
		Socket s = new Socket("localhost", 8001);
		//2:获取输出流
		//第1层：字符缓冲输出流
		//第2层：字符转换输出流
		//第3层：字节输出流
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
		//3 获取输入流
		BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
		//3:写数据
//		bw.write("*4\r\n$3\r\nSET\r\n$7\r\nCS06142\r\n$5\r\nCloud\r\n$9\r\nComputing\r\n");
		bw.write("*2\r\n$3\r\nGET\r\n$7\r\nCS06142\r\n");
//		bw.write("*2\r\n$3\r\nDEL\r\n$7\r\nCS06142\r\n");
		bw.flush();
		//bw.close();  //切记，缓冲流写数据，需要刷空！！！

		//告诉服务器。客户端这边数据写入完毕
		s.shutdownOutput();

		//4:读取从服务器响应的数据
		for (int i = 0; i < 100; i++) {
			String info = br.readLine(); //阻塞式方式
			System.out.println(info);
		}

		//4:关闭套接字
		s.close();


	}
}