package com.avekshaa.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Statement;

import com.avekshaa.dbutils.DataBaseUtil;
import com.avekshaa.utils.Constant;

public class Getcompdate {
	public void getCompData(Date sqlFromDate, Date sqlToDate, String empId,String empName ,String dis) {
		
		try (Connection con = DataBaseUtil.getConnection();
				Statement statement=con.createStatement()
				) {
			long mili1 = sqlFromDate.getTime();
			long mili2 = sqlToDate.getTime();
			long differnce = mili2 - mili1+86400000;
			int diff = (int) ((differnce) / (1000 * 60 * 60 * 24));
	       	String status = "pending";
			
			
			String sqlInsert="insert into "
					+ Constant.COMPOFF
					+ " (emp_id,Emp_Name,comp_from,comp_to,no_days,status,discription) values('"+empId+"','"+empName+"','"+sqlFromDate+"','"+sqlToDate+"',"+diff+",'"+status+"','"+dis+"')";
			statement.execute(sqlInsert);			
					} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
}