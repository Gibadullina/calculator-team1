package com.skylabs.controllers;

import com.skylabs.baselogic.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/CalculatorController")
public class CalculatorController extends HttpServlet implements IController {
	private static final long serialVersionUID = 1L;
       
    public CalculatorController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    String processData(String rawJson) {
    	try {
	    	JSONParser parser = new JSONParser();
	    	JSONObject json = (JSONObject)parser.parse(rawJson);    	
	    	
	    	//Base logic
	    	double m, perc, c, nt, pt, lt, e = 0;
	    	
	    	perc = Double.parseDouble((String)json.get("ndfl"));
	    	c = Double.parseDouble((String)json.get("production"));
	    	nt = Double.parseDouble((String)json.get("count"));
	    	pt = Double.parseDouble((String)json.get("prize"));
	    	lt = Double.parseDouble((String)json.get("normal"));
	    	long indexProduct = (long)json.get("val");
	    	long indexCoeff = (long)json.get("location");
	    	boolean useMrot = (boolean)json.get("mrot");
	    	
	    	try {
			    parser = new JSONParser();
				JSONObject root = (JSONObject) parser.parse(Util.GetJson("data.json"));
				JSONArray users = (JSONArray) root.get("entries");

				e = Double.parseDouble(((JSONObject)users.get((int)indexProduct)).get("price").toString());
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
	    	
	    	double result = Solver.Solve(perc, c, nt, pt, lt, e, (int)indexCoeff, useMrot);
	    	
	    	return "Заработная плата " + json.get("fio") + " составит " + result + " у.е.";
    	}
    	catch(Exception ex) {
    		return ex.getMessage();
    	}
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		BufferedReader reader = request.getReader();
		String result = "";
		
		while(true) {
			String line = reader.readLine();
			if (line != null) {
				result += line;
			} else break;
		}
		try {
			result = processData(result);
		}
		catch(Exception ex) {
			result = ex.getMessage();
		}
		
		PrintWriter writer = response.getWriter();
    	try {
    		writer.println(result);
    	} 
    	finally {
    		writer.close();
    	}
	}

}
