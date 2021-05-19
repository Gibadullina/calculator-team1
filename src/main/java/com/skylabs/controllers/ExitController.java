package com.skylabs.controllers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//Сервлет для выхода из сессии
public class ExitController extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	//Обработка запроса выхода из сессии
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		
		//Стираем куки в сессии
		request.getSession().setAttribute("login", "false");
		request.getSession().setAttribute("admin", "false");
		request.getSession().setAttribute("username", "");
		
		PrintWriter writer = response.getWriter();
        try {
        	writer.println("1"); //Пишем о том, что все прошло успешно
        } finally {
            writer.close();  
        }
	}

}
