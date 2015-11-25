package com.scxh.java1503;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MerchantServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=utf-8");

		pathSystem(req);
		
		String merchantStr = readFile(req);
		
		System.out.println(merchantStr);

		
		
		PrintWriter writer = resp.getWriter();
		writer.print(merchantStr);
		writer.flush();
	}

	public void pathSystem(HttpServletRequest request){
		System.out.println(request.getServletPath());//根目录所对应的绝对路径:request.getServletPath();
		System.out.println(request.getSession().getServletContext().getRealPath("/"));
		// 文件的绝对路径    :request.getSession().getServletContext().getRealPath
		
		System.out.println(System.getProperty("user.dir"));
		
		String path = this.getServletContext().getRealPath("/");
		System.out.println(path);
		
		path = this.getServletContext().getRealPath("/res/");
		System.out.println(path);
	}

	/***
	 * 从指定文件读取字符串
	 * @return
	 */
	public String readFile(HttpServletRequest request) {
		// String path = "E:\\Workspaces\\MyEclipse 8.5\\WebProject\\WebRoot\\res\\around";
		String path = request.getSession().getServletContext().getRealPath("/")+"/res/around";
		try {
			FileInputStream is = new FileInputStream(new File(path));
			return readStrFromInputStream(is);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "";
	}

	/**
	 * 从输入流读数据
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public String readStrFromInputStream(InputStream is) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is,
				"UTF-8"));
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}
		reader.close();
		is.close();
		return sb.toString();
	}

}
