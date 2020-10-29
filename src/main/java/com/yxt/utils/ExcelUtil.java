package com.yxt.utils;
/** 

* @author 作者 沙陌 

* @version 创建时间：2020年8月2日 下午1:43:23 

*/

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {
	private static Logger logger=Logger.getLogger(ExcelUtil.class);
	public XSSFWorkbook excelWorkbook;//整个excel文档对象
	public XSSFSheet xssfSheet;//某个工作表对象
	public Row row;//某个行对象
	public Cell cell;//某个单元格对象
	
	public ExcelUtil(String excelPath) throws Exception {
		FileInputStream fileInputStream=new FileInputStream(new File(excelPath));
		this.excelWorkbook=new XSSFWorkbook(fileInputStream);
	}
	//在某个sheet工作表的第几行第几列，这样就确定了是哪一个单元格
	public String getCellData(String sheetName,int rownum,int colnum) {
		String cellvalue="";
		try {
			//1.得到sheet工作表对象
			xssfSheet=excelWorkbook.getSheet(sheetName);
			//2.得到行对象
			row=xssfSheet.getRow(rownum);
			//3. 得到单元格对象
			cell=row.getCell(colnum);
			if (cell.getCellType()==XSSFCell.CELL_TYPE_BOOLEAN) {
				cellvalue=String.valueOf(cell.getBooleanCellValue());
			}else if (cell.getCellType()==XSSFCell.CELL_TYPE_STRING) {
				cellvalue=cell.getStringCellValue();
			}else if (cell.getCellType()==XSSFCell.CELL_TYPE_NUMERIC) {
				short format = cell.getCellStyle().getDataFormat();
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					Date d = cell.getDateCellValue();
					DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
					cellvalue = formater.format(d);
				} else if (format == 14 || format == 31 || format == 57 || format == 58) {
					// 日期
					DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
					Date date = DateUtil.getJavaDate(cell.getNumericCellValue());
					cellvalue = formater.format(date);
				} else if (format == 20 || format == 32) {
					// 时间
					DateFormat formater = new SimpleDateFormat("HH:mm");
					Date date = DateUtil.getJavaDate(cell.getNumericCellValue());
					cellvalue = formater.format(date);
				} else {
					DecimalFormat df = new DecimalFormat("0");
					cellvalue = df.format(cell.getNumericCellValue());
				}
			}else if (cell.getCellType()==XSSFCell.CELL_TYPE_BLANK) {
				cellvalue="";
			}
			logger.info("读取【"+sheetName+"】的第"+rownum+"行第"+colnum+"列的值是："+cellvalue);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e);
			new RuntimeException(e);
		}

		//cellvalue = cell.getStringCellValue();
		//System.out.println(cellvalue);
		return cellvalue;
		
	}
	public Object[][] getSheetData(String sheetName) {
		//循环去读这个sheet的行和列
		int rowCount = excelWorkbook.getSheet(sheetName).getLastRowNum();
		int colCount = excelWorkbook.getSheet(sheetName).getRow(0).getLastCellNum();
//		System.out.println(rowCount);
//		System.out.println(colCount);
		Object[][] data=new Object[rowCount][colCount];
		for (int i = 1; i <=rowCount; i++) {
			for (int j = 0; j < colCount; j++) {
				String cellData = getCellData(sheetName,i,j);
				//System.out.println(cellData);
				data[i-1][j]=cellData;
			}
		}
		return data;
	}
	public void close() {
		try {
			excelWorkbook.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		ExcelUtil excelUtil=new ExcelUtil("src/main/resources/dataPermission/addDataPermissiondata.xlsx");
		//excelUtil.getCellData("新建产品", 1, 2);
		Object[][] sheetData = excelUtil.getSheetData("新增数据权限");
		for (int i = 0; i < sheetData.length; i++) {
			for (int j = 0; j < sheetData[i].length; j++) {
				System.out.println(sheetData[i][j]);
			}
		}
	}
	
}
