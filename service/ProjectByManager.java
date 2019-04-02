package com.avekshaa.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.avekshaa.dbutils.DataBaseUtil;
import com.avekshaa.utils.Constant;

public class ProjectByManager {
	public String getProjectDetails(String empId) {
		String tableStr = "";
		String sql = "select emp_id,emp_name,company_email from "
				+ Constant.EMPLOYEE
				+ " where manager_id='"
				+ empId
				+ "' AND designation IN('Software Engineer','Senior Software Engineer','Technical Specialist')";
		try (Connection con = DataBaseUtil.getConnection();
				Statement stmt = con.createStatement();) {
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				tableStr += "<tr>";
				tableStr += "<td>" + rs.getString("emp_name") + "</td>";
				tableStr += "<td>" + rs.getString("company_email") + "</td>";
				tableStr += "<td><a href='release?emp_id=" + rs.getString("emp_id")
						+ "' value=" + rs.getString("emp_id") + ">Release</a></td>";
				tableStr += "</tr>";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tableStr;
	}
}
