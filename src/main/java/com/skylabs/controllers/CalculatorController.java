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

//Сервлет для расчётов
@WebServlet("/CalculatorController")
public class CalculatorController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CalculatorController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    //Процесс обработки данных
    String processData(String rawJson) {
    	try {
	    	JSONParser parser = new JSONParser();
	    	JSONObject json = (JSONObject)parser.parse(rawJson); //Парсим данные в нужный формат   	
	    	
	    	//Base logic
	    	double perc, c, nt, pt, lt, e = 0, k = 0;
	    	
	    	//Последовательно берем данные из JSON
	    	perc = Double.parseDouble(String.valueOf(json.get("ndfl")));
	    	pt = Double.parseDouble(String.valueOf(json.get("prize")));
	    	c = (long)json.get("production");
	    	nt = (long)json.get("count");	  	
	    	lt = (long)json.get("normal");
	    	long indexProduct = (long)json.get("val");
	    	long indexCoeff = (long)json.get("location");
	    	boolean useMrot = (boolean)json.get("mrot");
	    	
	    	//Здесь получаем коэффициенты по продукции и регионам из data.json и data_regions.json посредством дополнительных парсингов
	    	try {
			    parser = new JSONParser();
				JSONObject root = (JSONObject) parser.parse(Util.GetJson("data.json"));
				JSONArray users = (JSONArray) root.get("entries");

				e = Double.parseDouble(((JSONObject)users.get((int)indexProduct)).get("price").toString());
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
	    	
	    	try {
			    parser = new JSONParser();
				JSONObject root = (JSONObject) parser.parse(Util.GetJson("data_regions.json"));
				JSONArray users = (JSONArray) root.get("entries");

				k = Double.parseDouble(((JSONObject)users.get((int)indexCoeff)).get("price").toString());
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
	    	
	    	double result = Solver.Solve(perc, c, nt, pt, lt, e, k, useMrot); //Делаем расчёт
	    	
	    	return "Заработная плата " + json.get("fio") + " составит " + result + " у.е.";
    	}
    	catch(Exception ex) {
    		return ex.getMessage();
    	}
    }

    //Обработка запроса POST
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");	//Нормализуем кодировку
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		
		BufferedReader reader = request.getReader();
		String result = "";
		
		while(true) {
			String line = reader.readLine(); //Берем данные из тела запроса
			if (line != null) {
				result += line;
			} else break;
		}
		try {
			result = processData(result); //Высчитываем заработную плату
		}
		catch(Exception ex) {
			result = ex.getMessage();
		}
		
		PrintWriter writer = response.getWriter();
    	try {
    		writer.println(result); //Отправляем результат
    	} 
    	finally {
    		writer.close();
    	}
	}

}
