package com.scxh.java1503;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

public class JsonServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=utf-8");

		String jsonstr = req.getParameter("jsonstr");

		JSONObject jsonObject = new JSONObject(jsonstr);
		String userName = jsonObject.getString("username");
		int passWord = jsonObject.getInt("password");

		System.out.println("userName :" + userName + ", passWord :" + passWord);

		PrintWriter writer = resp.getWriter();

		JSONObject resposeObject = new JSONObject();
		if (userName.equals("admin")) {
			resposeObject.put("rescode", 1);
			resposeObject.put("message", "登录成功");
			writer.print(resposeObject.toString());
		} else {
			resposeObject.put("rescode", 0);
			resposeObject.put("message", "登录失败");
			writer.print(resposeObject.toString());
		}

	}
}
