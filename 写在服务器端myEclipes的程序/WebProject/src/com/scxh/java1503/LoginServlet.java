package com.scxh.java1503;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		/**指明发送给接收者的实体正文的媒体类型和编码方式*/
		resp.setContentType("text/html;charset=utf-8"); // 解决响应请求中文乱码

		String userName = req.getParameter("user");
		String passWord = req.getParameter("psw");
		System.out.println("doGet userName :"+userName+ " passWord :"+passWord);
		
		/**乱码解决方法一*/
//		String user_Name = new String(userName.getBytes("ISO-8859-1"),"UTF-8");
//		System.out.println("doGet user_Name :"+user_Name+ " passWord :"+passWord);
		
		
		PrintWriter writer = resp.getWriter();
		
		if(userName.equals("admin")){
			writer.print("<html><body><h1>"+userName+"登录成功 </h1></body></html>");
		}else{
			writer.print("<html><body><h1>"+userName+"登录失败 </h1></body></html>");
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("doPost >>>>>");
		doGet(req, resp);
		
	}
	
}
