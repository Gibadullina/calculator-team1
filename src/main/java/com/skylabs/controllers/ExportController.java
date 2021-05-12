package com.skylabs.controllers;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.skylabs.baselogic.Solver;
import com.skylabs.baselogic.Util;

/**
 * Servlet implementation class ExportController
 */
@WebServlet("/ExportController")
public class ExportController extends HttpServlet implements IController {
	private static final long serialVersionUID = 1L;

	class JustForWork {
		HttpServletRequest request;
		public JustForWork(HttpServletRequest request) {
			this.request = request;
		}
		
		Object get(String param) {
			return request.getParameter(param);
		}
	}
	
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws ServletException, IOException {
    
        response.setContentType("text/plain");
        response.setHeader("Content-disposition", "attachment; filename=result.xls");
        
        try {
        	JustForWork json = new JustForWork(request);
        	JSONParser parser;
	    	String region_name = "", product_name = "";
        	
	    	//Base logic
        	double perc, c, nt, pt, lt, e = 0, k = 0;
	    	
	    	perc = Double.parseDouble(String.valueOf(json.get("ndfl")));
	    	pt = Double.parseDouble(String.valueOf(json.get("prize")));
	    	c = Long.parseLong(String.valueOf(json.get("production")));
	    	nt = Long.parseLong(String.valueOf(json.get("count")));
	    	lt = Long.parseLong(String.valueOf(json.get("normal")));
	    	long indexProduct = Long.parseLong(String.valueOf(json.get("val")));
	    	long indexCoeff = Long.parseLong(String.valueOf(json.get("location")));
	    	boolean useMrot = Boolean.parseBoolean(String.valueOf(json.get("mrot")));
	    	
	    	try {
			    parser = new JSONParser();
				JSONObject root = (JSONObject) parser.parse(Util.GetJson("data.json"));
				JSONArray users = (JSONArray) root.get("entries");

				e = Double.parseDouble(((JSONObject)users.get((int)indexProduct)).get("price").toString());
				product_name = (String)((JSONObject)users.get((int)indexProduct)).get("name");
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
	    	
	    	try {
			    parser = new JSONParser();
				JSONObject root = (JSONObject) parser.parse(Util.GetJson("data_regions.json"));
				JSONArray users = (JSONArray) root.get("entries");

				k = Double.parseDouble(((JSONObject)users.get((int)indexCoeff)).get("price").toString());
				region_name = (String)((JSONObject)users.get((int)indexCoeff)).get("name");
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
	    	
	    	double result = Solver.Solve(perc, c, nt, pt, lt, e, k, useMrot);
	    	
	    	Workbook book = new HSSFWorkbook();
			Sheet sheet = book.createSheet();
			
			Row rowInfo = sheet.createRow(0);
			Row rowFio = sheet.createRow(1);
			Row rowPost = sheet.createRow(2);
			Row rowCount = sheet.createRow(3);
			Row rowPrize = sheet.createRow(4);
			Row rowNdfl = sheet.createRow(8);
			Row rowNorma = sheet.createRow(9);
			Row rowProduct = sheet.createRow(5);
			Row rowCoeff = sheet.createRow(7);
			Row rowType = sheet.createRow(6);
			Row rowMrot = sheet.createRow(11);
			Row rowSalary = sheet.createRow(10);
			
			Cell cell0, cell1;
			
			CellStyle cellStyle = book.createCellStyle();
			Font font = book.createFont();
			font.setBold(true);
			cellStyle.setFont(font);
			
			cell0 = rowInfo.createCell(0);
			cell1 = rowInfo.createCell(1);
			cell0.setCellValue(new HSSFRichTextString("Параметр"));
			cell1.setCellValue(new HSSFRichTextString("Данные"));
			cell0.setCellStyle(cellStyle);
			cell1.setCellStyle(cellStyle);
			
			cell0 = rowFio.createCell(0);
			cell1 = rowFio.createCell(1);
			cell0.setCellValue(new HSSFRichTextString("ФИО работника"));
			cell1.setCellValue(new HSSFRichTextString((String)json.get("fio")));
			
			cell0 = rowPost.createCell(0);
			cell1 = rowPost.createCell(1);
			cell0.setCellValue(new HSSFRichTextString("Должность работника"));
			cell1.setCellValue(new HSSFRichTextString((String)json.get("state")));
			
			cell0 = rowCount.createCell(0);
			cell1 = rowCount.createCell(1);
			cell0.setCellValue(new HSSFRichTextString("Изготовленная продукция (ед)"));
			cell1.setCellValue(new HSSFRichTextString((String)json.get("production")));
			
			cell0 = rowPrize.createCell(0);
			cell1 = rowPrize.createCell(1);
			cell0.setCellValue(new HSSFRichTextString("Премия (руб)"));
			cell1.setCellValue(new HSSFRichTextString((String)json.get("prize")));
			
			cell0 = rowNdfl.createCell(0);
			cell1 = rowNdfl.createCell(1);
			cell0.setCellValue(new HSSFRichTextString("НДФЛ %"));
			cell1.setCellValue(new HSSFRichTextString((String)json.get("ndfl")));
			
			cell0 = rowNorma.createCell(0);
			cell1 = rowNorma.createCell(1);
			cell0.setCellValue(new HSSFRichTextString("Норма (ед.)"));
			cell1.setCellValue(new HSSFRichTextString((String)json.get("normal")));
			
			cell0 = rowProduct.createCell(0);
			cell1 = rowProduct.createCell(1);
			cell0.setCellValue(new HSSFRichTextString("Единица товара для премии (ед)"));
			cell1.setCellValue(new HSSFRichTextString((String)json.get("count")));
			
			cell0 = rowCoeff.createCell(0);
			cell1 = rowCoeff.createCell(1);
			cell0.setCellValue(new HSSFRichTextString("Районный коэффициент"));
			cell1.setCellValue(new HSSFRichTextString(region_name + " - " + k));
			
			cell0 = rowType.createCell(0);
			cell1 = rowType.createCell(1);
			cell0.setCellValue(new HSSFRichTextString("Расценка за единицу продукции (руб)"));
			cell1.setCellValue(new HSSFRichTextString(product_name+" - " + e));
			
			cell0 = rowMrot.createCell(0);
			cell1 = rowMrot.createCell(1);
			cell0.setCellValue(new HSSFRichTextString("Учитывался МРОТ?"));
			cell1.setCellValue((String)json.get("mrot") == "true" ? "Да" : "Нет");
			
			cell0 = rowSalary.createCell(0);
			cell1 = rowSalary.createCell(1);
			cell0.setCellValue(new HSSFRichTextString("Зарплата"));
			
			DecimalFormat df = new DecimalFormat("#.##");
			
			cell1.setCellValue(df.format(result)+" руб");
			
			sheet.autoSizeColumn(0);
			sheet.autoSizeColumn(1);
	    	
			 OutputStream out = response.getOutputStream();
			 book.write(out);
			 book.close();
		
        }
        catch(Exception ex) {
        	ex.printStackTrace();
        }
    }

}
