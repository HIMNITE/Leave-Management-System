package com.avekshaa.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.avekshaa.dbutils.*;
import com.avekshaa.utils.Constant;

public class Finance {
	public String getEmpLeave() {
		String tableStr = "";
		String sql = "select * from " + Constant.EMPLOYEE;
		try (Connection con = DataBaseUtil.getConnection();
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				tableStr += "<tr>";
				tableStr += "<td>" + rs.getString(1) + "</td>";
				tableStr += "<td>" + rs.getString(2) + "</td>";
				tableStr += "<td><a href='Leave?emp_id=" + rs.getString(1)
						+ "' value=" + rs.getString(5) + ">Leaves</a></td>";
				tableStr += "</tr>";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tableStr;
	}
}