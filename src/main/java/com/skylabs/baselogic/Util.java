package com.skylabs.baselogic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.Scanner;

import org.json.simple.JSONObject;

public class Util {
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
		try {
			BufferedReader buff = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
			String line = "";

		    while((line = buff.readLine()) != null) {
		       json += line;
		    }
		} 
		catch(Exception ex) {
			return "Internal error";
		}
		
		return json;
	}
}
