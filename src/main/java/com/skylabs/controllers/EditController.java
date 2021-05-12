package com.skylabs.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.skylabs.baselogic.Util;

/**
 * Servlet implementation class EditController
 */
public class EditController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EditController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		
		BufferedReader reader = request.getReader();
		String rawJson = "";
		
		while(true) {
			String line = reader.readLine();
			if (line != null) {
				rawJson += line;
			} else break;
		}
		
		if (request.getParameter("type").equals("products")) {
			Util.SaveString("data.json", rawJson);
		}
		else if (request.getParameter("type").equals("regions")) { 
			Util.SaveString("data_regions.json", rawJson);
		}
		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		
		String json = "";
		if (request.getParameter("type").equals("products")) {
			json = Util.GetJson("data.json");
		} else if (request.getParameter("type").equals("regions")) {
			json = Util.GetJson("data_regions.json");
		}
		
		PrintWriter writer = response.getWriter();
    	try {
    		writer.println(json);
    	} 
    	finally {
    		writer.close();
    	}
	}

}