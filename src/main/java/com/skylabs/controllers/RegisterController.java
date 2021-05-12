package com.skylabs.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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

@WebServlet("/RegisterController")
public class RegisterController extends HttpServlet implements IController {
	private static final long serialVersionUID = 1L;
       
    public RegisterController() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		PrintWriter writer = response.getWriter();
		
		if (Register(username, password)) {
			try {
	        	writer.println("Аккаунт с ником "+username+" успешно создан!");
	        } finally {
	            writer.close();  
	        }
		}
		else {
	        try {
	        	writer.println("Не удается создать аккаунт с логином "+username+"!");
	        } finally {
	            writer.close();  
	        }
		}
	}
	
	boolean Register(String username, String password) {
		String json = Util.GetJson("users.json");
		
		try {
			JSONParser parser = new JSONParser();
			JSONObject root = (JSONObject) parser.parse(json);
			JSONArray users = (JSONArray) root.get("users");
			
			for(Object entry : users) {
				JSONObject user = (JSONObject) entry;
				if (user.get("username").equals(username)) return false;	
			}
			
			JSONObject new_user = new JSONObject();
			new_user.put("username", username);
			new_user.put("password", password);
			new_user.put("admin", "false");
			
			users.add(new_user);
			
			JSONObject new_root = new JSONObject();
			new_root.put("users", users);
			
			Util.SaveJson(new_root, "users.json");
			
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
