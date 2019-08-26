package Utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFileUtil {
	Workbook wb	;
	public ExcelFileUtil() throws Exception{
		FileInputStream fis=new FileInputStream(System.getProperty("user.dir")+"\\TestInputs\\TestData.xlsx");
		wb=new XSSFWorkbook(fis);
	}
	
	public int rowCount(String sheetname){
		return wb.getSheet(sheetname).getLastRowNum();
	}
	
	public int colCount(String sheetname,int row){
		return wb.getSheet(sheetname).getRow(row).getLastCellNum();
	}
	
	public String getData(String sheetname,int row ,int column){
		String data="";
		if(wb.getSheet(sheetname).getRow(row).getCell(column).getCellType()==Cell.CELL_TYPE_NUMERIC){
			int celldata=(int)(wb.getSheet(sheetname).getRow(row).getCell(column).getNumericCellValue());
			data=String.valueOf(celldata);
		}
		else
		{
			data=wb.getSheet(sheetname).getRow(row).getCell(column).getStringCellValue();
		}
		return data;
	}
	
	public int getTestcaseRow(String tcid) {
		int rc= rowCount("TestData");
		int testcaseRow=-1;
		for(int j=1;j<=rc;j++) {
			if(getData("TestData",j,0).equalsIgnoreCase(tcid)){
				testcaseRow=j;
			}
		}
		return testcaseRow;
	}
	
	public void setData(String sheetname,int row, int column,String Status) throws Exception{
		Sheet sh=wb.getSheet(sheetname);
		Row rownum=sh.getRow(row);
		Cell cell=rownum.createCell(column);
		cell.setCellValue(Status);
		
		if(Status.equalsIgnoreCase("PASS")){
			//Create Cell Style
			CellStyle style=wb.createCellStyle();
			//Create Font
			Font font=wb.createFont();
			//Apply Color to the Text
			font.setColor(IndexedColors.GREEN.getIndex());
			//Apply Bold To The Text
			font.setBold(true);
			// Set Font
			style.setFont(font);
			//Set Cell Style
			rownum.getCell(column).setCellStyle(style);
		}
		else if(Status.equalsIgnoreCase("Fail")){
			//Create Cell Style
			CellStyle style=wb.createCellStyle();
			//Create Font
			Font font=wb.createFont();
			//Apply Color to the Text
			font.setColor(IndexedColors.RED.getIndex());
			//Apply Bold To The Text
			font.setBold(true);
			// Set Font
			style.setFont(font);
			//Set Cell Style
			rownum.getCell(column).setCellStyle(style);
		}
		else if(Status.equalsIgnoreCase("Not Executed")){
			//Create Cell Style
			CellStyle style=wb.createCellStyle();
			//Create Font
			Font font=wb.createFont();
			//Apply Color to the Text
			font.setColor(IndexedColors.BLUE.getIndex());
			//Apply Bold To The Text
			font.setBold(true);
			// Set Font
			style.setFont(font);
			//Set Cell Style
			rownum.getCell(column).setCellStyle(style);
		}
		FileOutputStream fos =new FileOutputStream("./TestOutput/OutPutSheet.xlsx");
		wb.write(fos);
		fos.close();
	}
		
}
