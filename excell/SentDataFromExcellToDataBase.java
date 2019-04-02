package com.avekshaa.excell;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Statement;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.avekshaa.dbutils.DataBaseUtil;
import com.avekshaa.utils.Constant;

public class SentDataFromExcellToDataBase {

	public void holidayDataSending(String filePath) {
		File f1 = new File(filePath);

		try (Connection con = DataBaseUtil.getConnection();
				Statement statement = con.createStatement();
				XSSFWorkbook workbook = new XSSFWorkbook(
						new FileInputStream(f1));) {
			XSSFSheet sheet = null;
			int j = workbook.getNumberOfSheets();

			for (int i = 0; i < j; i++) {
				sheet = workbook.getSheetAt(i);
			}

			Iterator<Row> rowIterator = sheet.iterator();
			Row row;
			rowIterator.next();
			while (rowIterator.hasNext()) {
				row = rowIterator.next();
				String particular = row.getCell(0).getStringCellValue();
				java.util.Date date = row.getCell(1).getDateCellValue();
				Date sqlDate = new Date(date.getTime());
				String day = row.getCell(2).getStringCellValue();
				String location = row.getCell(3).getStringCellValue();
				String sql1 = "insert into " + Constant.STATICLEAVE
						+ " values('" + particular + "','" + sqlDate + "','"
						+ day + "','" + location + "')";
				statement.addBatch(sql1);
			}
			statement.executeBatch();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void upload(String filePath) {
		File leaveDetailFile = new File(filePath);
		try (Connection con = DataBaseUtil.getConnection();
				Statement statement = con.createStatement();
				XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(
						leaveDetailFile));) {
			XSSFSheet sheet = null;
			int j = workbook.getNumberOfSheets();

			for (int i = 0; i < j; i++) {
				sheet = workbook.getSheetAt(i);
			}
			Iterator<Row> rowIterator = sheet.iterator();
			Row row;
			rowIterator.next();
			while (rowIterator.hasNext()) {
				row = rowIterator.next();
				String empId = row.getCell(0).getStringCellValue();
				int leave = (int) row.getCell(1).getNumericCellValue();
				String sql1 = "insert into " + Constant.EMPLOYEE + " values('"
						+ empId + "'," + leave + ")";

				statement.addBatch(sql1);
			}
			statement.executeBatch();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
