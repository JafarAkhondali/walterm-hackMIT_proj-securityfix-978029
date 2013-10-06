package com.hackmit.blockbuilding;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.ServerStart;

@SuppressWarnings("serial")
public class BlockBuildingServlet extends HttpServlet {
	public void doGet(HttpServletResponse resp) throws IOException {
		ServerStart ss = new ServerStart(resp);
		ss.handleConnection();
	}
}
