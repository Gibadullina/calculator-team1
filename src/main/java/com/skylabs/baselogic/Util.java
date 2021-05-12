package com.skylabs.baselogic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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
			ex.printStackTrace();
			return "Internal error";
		}
		
		return json;
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
