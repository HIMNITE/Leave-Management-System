package com.avekshaa.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Calendar;

import com.avekshaa.dbutils.DataBaseUtil;
import com.avekshaa.utils.CommonUtiles;
import com.avekshaa.utils.Constant;

public class Rejectleaveofemp {
	public String get() {
		String tableStr = "";
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String sql = "select * from " + Constant.LEAVEAPPLY + " where status='Reject' AND leave_from>='" + year
				+ "-01-01'";
		
		try (Connection con = DataBaseUtil.getConnection();
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
			while (rs.next()) {
				tableStr += "<tr>";
				tableStr += "<td>" + rs.getString(1) + "</td>";
				tableStr += "<td>" + rs.getString(3) + "</td>";
				tableStr += "<td>" + dateFormat.format(new java.util.Date(rs.getDate("leave_from").getTime()))+ "</td>";
				tableStr += "<td>" + dateFormat.format(new java.util.Date(rs.getDate("leave_to").getTime())) + "</td>";
				tableStr += "<td>" + rs.getString(7) + "</td>";
				tableStr += "<td><a href='AfterRejectApprove?leave_id=" + rs.getString(2) + "'>Approve</a></td>";
				tableStr += "</tr>";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tableStr;

	}

}
