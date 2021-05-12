package com.skylabs.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.skylabs.baselogic.Util;

@WebServlet("/AuthController")
public class AuthController extends HttpServlet implements IController {
	private static final long serialVersionUID = 1L;
       
    public AuthController() {
        super();
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		int check = Authorize(username, password);
		
		if (check != 0) {
			ServletContext servletContext = getServletContext();
			request.getSession().setMaxInactiveInterval(-1);
			request.getSession().setAttribute("login", "true");
			if (check == 2) request.getSession().setAttribute("admin", "true");
			else request.getSession().setAttribute("admin", "false");
			
			try {
				String[] entries;
				JSONParser parser = new JSONParser();
				JSONObject root = (JSONObject) parser.parse(Util.GetJson("data.json"));
				JSONArray users = (JSONArray) root.get("entries");
				
				entries = new String[users.size()];
				
				int i = 0;
				for(Object entry : users) {
					JSONObject entryy = (JSONObject) entry;
					entries[i] = entryy.get("name") + " - " + entryy.get("price") + " руб.";
					i++;
				}
				request.setAttribute("entries", entries);
				
				root = (JSONObject) parser.parse(Util.GetJson("data_regions.json"));
				users = (JSONArray) root.get("entries");
				
				entries = new String[users.size()];
				
				i = 0;
				for(Object entry : users) {
					JSONObject entryy = (JSONObject) entry;
					entries[i] = entryy.get("name") + "";
					i++;
				}
				request.setAttribute("entries_regions", entries);
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
	        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/engine.jsp");
	        requestDispatcher.forward(request, response);
		}
		else {
	        PrintWriter writer = response.getWriter();
	        try {
	        	writer.println("Неправильный логин или пароль!");
	        	request.getSession().setAttribute("login", false);
	        } finally {
	            writer.close();  
	        }
		}
	}
	
	int Authorize(String username, String password) {
		String json = Util.GetJson("users.json");
		
		try {
			JSONParser parser = new JSONParser();
			JSONObject root = (JSONObject) parser.parse(json);
			JSONArray users = (JSONArray) root.get("users");
			
			for(Object entry : users) {
				JSONObject user = (JSONObject) entry;
				if (user.get("username").equals(username) && user.get("password").equals(password)) {
					if ((boolean)user.get("admin").equals("true")) return 2;
					else return 1;
				}
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
		return 0;
	}

}
