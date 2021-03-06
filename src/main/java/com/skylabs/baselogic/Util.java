package com.skylabs.baselogic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Path;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Util implements UtilHelper { //Вспомогательный класс для работы с IO
	
	
	//Сохраняем корневой узел JSON в файл по указанному пути
	public static void SaveJson(JSONObject root, String path) {
		try {
			FileWriter file = new FileWriter(path);
			file.write(root.toJSONString());
			file.flush();
			file.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	//Получаем строку в виде сырого JSON по указанному пути
	public static String GetJson(String path) {	
		String json = "";
		try {
			File f = new File(path);
			if (!f.exists()) {
				if (path.equals("users.json")) SaveString("users.json", DEFAULT_USERS);
				else if (path.equals("data.json")) SaveString("data.json", DEFAULT_PRODUCTS);
				else if (path.equals("data_regions.json")) SaveString("data_regions.json", DEFAULT_REGIONS);
			}
			try { //Считывание данных
				@SuppressWarnings("resource")
				BufferedReader buff = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
				String line = "";
	
			    while((line = buff.readLine()) != null) {
			       json += line;
			    }
			} 
			catch(Exception ex) {
				ex.printStackTrace();
				return "Internal error";
			}
			
			return json;
		} 
		catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	//Сохранение строки в файл по указанному пути
	public static void SaveString(String path, String str) throws IOException {
		Writer out = new BufferedWriter(new OutputStreamWriter(
		    new FileOutputStream(path), "UTF-8"));
		try {
		    out.write(str);
		} finally {
		    out.close();
		}
	}
	
	//Получение списка продуктов
	public static String[] GetProducts() {
		try {
			String[] entries;
			JSONParser parser = new JSONParser();
			JSONObject root = (JSONObject) parser.parse(Util.GetJson("data.json"));
			JSONArray users = (JSONArray) root.get("entries");
			
			entries = new String[users.size()]; //Наши продукты
			
			int i = 0;
			for(Object entry : users) {
				JSONObject entryy = (JSONObject) entry;
				entries[i] = entryy.get("name") + " - " + entryy.get("price") + " руб.";
				i++;
			}
			
			return entries;
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	//Получение коэффициентов по регионам
	public static String[] GetLocations() {
		try {
			String[] entries;
			JSONParser parser = new JSONParser();
			JSONObject root = (JSONObject) parser.parse(Util.GetJson("data_regions.json"));
			JSONArray users = (JSONArray) root.get("entries");
			
			entries = new String[users.size()]; //Наши региональные	 коэффициенты
			
			int i = 0;
			for(Object entry : users) {
				JSONObject entryy = (JSONObject) entry;
				entries[i] = entryy.get("name") + "";
				i++;
			}
			return entries;
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
