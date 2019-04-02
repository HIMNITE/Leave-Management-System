package com.avekshaa.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Calendar;

import com.avekshaa.dbutils.*;
import com.avekshaa.utils.Constant;

public class Leave {
	public String getLeave(String empId) {
		String tableStr = "";
		int currenYear = Calendar.getInstance().get(Calendar.YEAR);

		String sql = "select * from " + Constant.LEAVEAPPLY + " where emp_id='"
				+ empId + "' AND leave_from>='" + currenYear +"-01-01'";
		try (Connection con = DataBaseUtil.getConnection();
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			DateFormat dateFormat = DateFormat
					.getDateInstance(DateFormat.MEDIUM);
			while (rs.next()) {
				tableStr += "<tr>";
				tableStr += "<td>" + rs.getString(1) + "</td>";
				tableStr += "<td>" + rs.getString(3) + "</td>";
				tableStr += "<td>" + dateFormat.format(new java.util.Date(rs.getDate("leave_from").getTime()))+ "</td>";
				// leave_to Format changing
				tableStr += "<td>" + dateFormat.format(new java.util.Date(rs.getDate("leave_to").getTime())) + "</td>";
				tableStr += "<td>" + rs.getString("no_days") + "</td>";
				tableStr += "<td>" + rs.getString("status") + "</td>";
				tableStr += "</tr>";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tableStr;
	}
}
