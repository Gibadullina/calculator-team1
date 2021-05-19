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

//Сервлет для аутентификации
@WebServlet("/AuthController")
public class AuthController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AuthController() {
        super();
    }
    
    //Процесс авторизации
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); //Устанавливаем UTF-8 кодировку, чтобы было все в порядке с кириллицей
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		
		String username = request.getParameter("username"); //Считываем параметры из запроса
		String password = request.getParameter("password");
		
		int check = Authorize(username, password); //Авторизириуемся
		
		if (check != 0) { //Если авторизация успешна
			request.getSession().setMaxInactiveInterval(-1); //Устанавливаем куки через сессию, что пользователель авторизован
			request.getSession().setAttribute("login", "true");
			if (check == 2) request.getSession().setAttribute("admin", "true"); //Проверяем, не админ ли пользователь
			else request.getSession().setAttribute("admin", "false");
			
			PrintWriter writer = response.getWriter(); //Пишем результат выполнения пользователю
	        try {
	        	request.getSession().setAttribute("login", true);
	        	request.getSession().setAttribute("username", username);
	        	writer.println("1");
	        } finally {
	            writer.close();  
	        }
		}
		else { //В случае неудачи выдаем соответствующее предупреждение
	        PrintWriter writer = response.getWriter();
	        try {
	        	writer.println("Неправильный логин или пароль!");
	        	request.getSession().setAttribute("login", false);
	        	request.getSession().setAttribute("username", "");
	        } finally {
	            writer.close();  
	        }
		}
	}
	
	//Авторизация. 0 - неуспех, 1 - пользователь, 2 - админ
	int Authorize(String username, String password) {
		String json = Util.GetJson("users.json"); //Получаем JSON с пользователями
		
		try {
			JSONParser parser = new JSONParser(); 
			JSONObject root = (JSONObject) parser.parse(json); //Парсим данные в работоспособный формат
			JSONArray users = (JSONArray) root.get("users");
			
			for(Object entry : users) { //Перебираем каждого
				JSONObject user = (JSONObject) entry;
				if (user.get("username").equals(username) && user.get("password").equals(password)) { //Если логин и пароль совпали, успех
					if ((boolean)user.get("admin").equals("true")) return 2; //В случае, если пользователь является админом, возвращаем иной результат
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
