package com.skylabs.controllers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.skylabs.baselogic.Util;

//Сервлет для регистрации
@WebServlet("/RegisterController")
public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RegisterController() {
        super();
    }
    
    //Процесс обработки запроса на регистрацию нового пользователя
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		
		String username = request.getParameter("username"); //Получаем параметры из запроса
		String password = request.getParameter("password");
		
		PrintWriter writer = response.getWriter();
		
		if (Register(username, password)) { //Пытаемся зарегестрировать
			try {
	        	writer.println("Аккаунт с ником "+username+" успешно создан!");
	        } finally {
	            writer.close();  
	        }
		}
		else { //Если пользователь уже такой есть или что-то пошло не так
	        try {
	        	writer.println("Не удается создать аккаунт с логином "+username+"!");
	        } finally {
	            writer.close();  
	        }
		}
	}
	
	//Регистрация нового пользователя. True - успех, False - нет
	@SuppressWarnings("unchecked")
	boolean Register(String username, String password) {
		String json = Util.GetJson("users.json"); //Получаем список пользователей
		
		try {
			JSONParser parser = new JSONParser();
			JSONObject root = (JSONObject) parser.parse(json);
			JSONArray users = (JSONArray) root.get("users");
			
			for(Object entry : users) {
				JSONObject user = (JSONObject) entry;
				if (user.get("username").equals(username)) return false;	
			}
			
			JSONObject new_user = new JSONObject(); //Создаем новый узел JSON для пользоваетеля и записываем туда данные
			new_user.put("username", username);
			new_user.put("password", password);
			new_user.put("admin", "false");
			
			users.add(new_user); //Добавляем нового пользователя в узел пользователей JSON
			
			JSONObject new_root = new JSONObject();
			new_root.put("users", users);
			
			Util.SaveJson(new_root, "users.json"); //Сохраняем обновленный список пользователей в файл
			
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
