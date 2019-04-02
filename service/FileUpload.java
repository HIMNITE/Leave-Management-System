package com.avekshaa.service;

import java.io.File;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.avekshaa.excell.SentDataFromExcellToDataBase;
import com.avekshaa.utils.CommonUtiles;

public class FileUpload {
	public void uploadFile(String contentType, List<?> itemList,
			ServletFileUpload upload, DiskFileItemFactory factory) {
		String fileName = null;
		File file = null;
		int maxFileSize = 5000 * 1024;
		int maxMemSize = 5000 * 1024;
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		CommonUtiles cu = new CommonUtiles();
		String filePath = cu.getDesktopPath();
		filePath = filePath + "/leaveFile";
		file = new File(filePath);
		if (!file.isDirectory()) {
			file.mkdir();
		}
		if (((contentType.indexOf("multipart/form-data")) >= 0)) {
			factory = new DiskFileItemFactory();
			factory.setSizeThreshold(maxMemSize);
			factory.setRepository(new File(cu.getDesktopPath()));
			upload = new ServletFileUpload(factory);
			upload.setSizeMax(maxFileSize);
			try {

				Iterator<?> itr = itemList.iterator();
				while (itr.hasNext()) {
					FileItem item = (FileItem) itr.next();
					if (!item.isFormField()) {
						fileName = item.getName();
						fileName = "empDetails" + currentYear
								+ fileName.substring(fileName.lastIndexOf("."));
						file = new File(filePath, fileName);
						item.write(file);
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		SentDataFromExcellToDataBase sb = new SentDataFromExcellToDataBase();
		sb.holidayDataSending(filePath + File.separator + fileName);
	}

	public void empLeaveUpload(String contentType, List<?> itemList,
			ServletFileUpload upload, DiskFileItemFactory factory) {
		String fileName = null;
		File file = null;
		int maxFileSize = 5000 * 1024;
		int maxMemSize = 5000 * 1024;
		int year = Calendar.getInstance().get(Calendar.YEAR);
		CommonUtiles cu = new CommonUtiles();
		String filePath = cu.getDesktopPath();
		filePath = filePath + "/xlFile";
		file = new File(filePath);
		if (!file.isDirectory()) {
			file.mkdir();
		}
		if (((contentType.indexOf("multipart/form-data")) >= 0)) {
			factory = new DiskFileItemFactory();
			factory.setSizeThreshold(maxMemSize);
			factory.setRepository(new File(cu.getDesktopPath()));
			upload = new ServletFileUpload(factory);
			upload.setSizeMax(maxFileSize);
			try {
				Iterator<?> itr = itemList.iterator();
				while (itr.hasNext()) {
					FileItem item = (FileItem) itr.next();
					if (!item.isFormField()) {
						fileName = item.getName();
						fileName = "leaveDetails" + year
								+ fileName.substring(fileName.lastIndexOf("."));
						file = new File(filePath, fileName);
						item.write(file);
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		SentDataFromExcellToDataBase sb = new SentDataFromExcellToDataBase();
		sb.upload(filePath + File.separator + fileName);
	}
}
