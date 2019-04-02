package com.avekshaa.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import com.avekshaa.dbutils.DataBaseUtil;
import com.avekshaa.utils.Constant;

public class Login {

	public Map<String, String> loginValues(String email, String password) {
		Map<String, String> map = new HashMap<String, String>();
		String sql = "select emp_id,designation,emp_name,mobile_no from " + Constant.EMPLOYEE
				+ " where company_email='" + email + "' AND password='"
				+ password + "'";
		try (Connection con = DataBaseUtil.getConnection();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(sql)) {
			while (rs.next()) {
				map.put("empId", rs.getString("emp_id"));
				map.put("designation", rs.getString("designation"));
				map.put("emp_name", rs.getString("emp_name"));
				map.put("mobile", rs.getString("mobile_no"));
							}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	public boolean verifyUser(String email, String password) {
		boolean flag = false;
		String sql = "select emp_id from " + Constant.EMPLOYEE
				+ " where company_email='" + email + "' and password='"
				+ password + "'";
		try (Connection con = DataBaseUtil.getConnection();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(sql)) {
			if(rs.next()) {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

}