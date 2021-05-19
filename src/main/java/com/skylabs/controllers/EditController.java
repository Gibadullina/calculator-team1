package com.skylabs.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.skylabs.baselogic.Util;

//Сервлет для редактирования коэффициентов регионов и продукции
public class EditController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EditController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    //Обработка запроса, когда пользователь внес данные
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		
		BufferedReader reader = request.getReader();
		String rawJson = "";
		
		while(true) {
			String line = reader.readLine(); //Считываем из тела запроса данные
			if (line != null) {
				rawJson += line;
			} else break;
		}
		
		if (request.getParameter("type").equals("products")) { //Если редактировалась продукция, то сохраняем ее новые коэфициенты
			Util.SaveString("data.json", rawJson);
		}
		else if (request.getParameter("type").equals("regions")) { //Или сохраняем новые данные по регионам
			Util.SaveString("data_regions.json", rawJson);
		}
		
	}

	//Если пользователь хочет получить список региональных коэфициентов и продукции перед редактированием
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		
		String json = "";
		if (request.getParameter("type").equals("products")) { //Если продукция, то берем данные о ней
			json = Util.GetJson("data.json");
		} else if (request.getParameter("type").equals("regions")) {//Или региональные коэффициенты
			json = Util.GetJson("data_regions.json");
		}
		
		PrintWriter writer = response.getWriter();
    	writer.println(json); //Пишем результат пользователю
	}

}
