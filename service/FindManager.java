package com.avekshaa.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.avekshaa.dbutils.DataBaseUtil;
import com.avekshaa.utils.Constant;

public class FindManager {
	public String findName(String email) {
		String empName = "! sir";
		String sql = "select emp_name from " + Constant.EMPLOYEE
				+ " where company_email='" + email + "'";
		try (Connection con = DataBaseUtil.getConnection();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(sql)) {
			if (rs.next()) {
				empName = rs.getString("emp_name");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return empName;
	}

}
