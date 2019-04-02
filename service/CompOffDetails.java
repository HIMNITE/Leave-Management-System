package com.avekshaa.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Calendar;

import com.avekshaa.dbutils.DataBaseUtil;
import com.avekshaa.utils.Constant;

public class CompOffDetails {
	public String getCompOffDetails() {
		String tableStr = "";
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String sql = "select * from " + Constant.COMPOFF
				+ " where status='pending' AND comp_from >='" + year
				+ "-01-01'";
		try (Connection con = DataBaseUtil.getConnection();
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			DateFormat dateFormat = DateFormat
					.getDateInstance(DateFormat.MEDIUM);
			while (rs.next()) {
				tableStr += "<tr>";
				tableStr += "<td>" + rs.getString("emp_id") + "</td>";
				tableStr += "<td>" + rs.getString("emp_name") + "</td>";
				tableStr += "<td>"
						+ dateFormat.format(new java.util.Date(rs.getDate("comp_from").getTime())) + "</td>";
				tableStr += "<td>"
						+ dateFormat.format(new java.util.Date(rs.getDate("comp_to").getTime()))+ "</td>";
				tableStr += "<td>" + rs.getString("no_days") + "</td>";
				tableStr += "<td>" + rs.getString("discription") + "</td>";
				tableStr += "<td><a href='CompOffApproveByManager?compoff_id="
						+ rs.getString(2) + "' value=" + rs.getString(9)
						+ ">Approve</a></td>";
				tableStr += "<td><a href='RejectCompOff?compoff_id="
						+ rs.getString(2) + "'value=" + rs.getString(9)
						+ ">Reject</a></td>";
				tableStr += "</tr>";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tableStr;
	}
}
