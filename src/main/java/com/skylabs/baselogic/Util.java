package com.skylabs.baselogic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import org.json.simple.JSONObject;

public class Util {
	
	static final String DEFAULT_REGIONS = "{\"entries\":[{\"name\":\"Республика Башкортостан\",\"price\":1.15},{\"name\":\"Ростовская область\",\"price\":1.1},{\"name\":\"Республика Дагестан\",\"price\":1.12},{\"name\":\"Республика Калмыкия\",\"price\":1.3},{\"name\":\"Ставропольский край\",\"price\":1.15}]}";
	static final String DEFAULT_PRODUCTS = "{\"entries\":[{\"name\":\"Компрессор\",\"price\":1000},{\"name\":\"Вентилятор\",\"price\":300},{\"name\":\"Турбина\",\"price\":\"400\"},{\"name\":\"Сопло\",\"price\":100},{\"name\":\"Смеситель\",\"price\":200}]}";
	static final String DEFAULT_USERS = "{\"users\":[{\"password\":\"admin\",\"admin\":\"true\",\"username\":\"admin\"}]}";

	
	public static void SaveJson(JSONObject root, String path) {
		try(FileWriter file = new FileWriter(path)) {
			file.write(root.toJSONString());
			file.flush();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static String GetJson(String path) {	
		String json = "";
		File f = new File(path);
		try {
			if (!f.exists()) {
				if (path.equals("users.json")) SaveString("users.json", DEFAULT_USERS);
				else if (path.equals("data.json")) SaveString("data.json", DEFAULT_PRODUCTS);
				else if (path.equals("data_regions.json")) SaveString("data_regions.json", DEFAULT_REGIONS);
			}
			try {
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
	
	public static void SaveString(String path, String str) throws IOException {
		Writer out = new BufferedWriter(new OutputStreamWriter(
		    new FileOutputStream(path), "UTF-8"));
		try {
		    out.write(str);
		} finally {
		    out.close();
		}
	}
}
