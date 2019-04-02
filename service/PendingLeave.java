package com.avekshaa.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Calendar;

import com.avekshaa.dbutils.*;
import com.avekshaa.utils.Constant;

public class PendingLeave {
	public String getPendimgLeaveEmp() {
		String tableStr = "";
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		String sql = "select * from " + Constant.LEAVEAPPLY + " where status='pending' AND leave_from>='" + currentYear
				+ "-01-01'";
		GetLeaveId getKey=new GetLeaveId();
		try (Connection con = DataBaseUtil.getConnection();
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);			
			while (rs.next()) {
				tableStr += "<tr>";
				tableStr += "<td>" + rs.getString("emp_id") + "</td>";
				String key=getKey.getKeyFromlink_status(rs.getInt("leave_id"));
				System.out.println(key);
				tableStr += "<td>" + rs.getString("emp_name") + "</td>";
				tableStr += "<td>" + dateFormat.format(new java.util.Date(rs.getDate("leave_from").getTime()))+ "</td>";
				tableStr += "<td>" + dateFormat.format(new java.util.Date(rs.getDate("leave_to").getTime())) + "</td>";
				tableStr += "<td>" + rs.getString("no_days") + "</td>";
				tableStr += "<td>" + rs.getString("Reason") + "</td>";
				tableStr += "<td><a href='Approved?key=" + key +"'>Approve</a></td>";
				tableStr += "<td><a href='Reject?key=" + key + "'>Reject</a></td>";
				tableStr += "</tr>";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tableStr;

	}
}
