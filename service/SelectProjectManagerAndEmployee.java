package com.avekshaa.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import com.avekshaa.dbutils.DataBaseUtil;
import com.avekshaa.utils.Constant;

public class SelectProjectManagerAndEmployee {
	public Map<String, String> showManger() {
		HashMap<String, String> managerMap = new HashMap<String, String>();
		String sql = "select emp_id ,emp_name from " + Constant.EMPLOYEE
				+ " where designation='Manager'";
		try (Connection con = DataBaseUtil.getConnection();
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				managerMap.put(rs.getString("emp_id"), rs.getString("emp_id")
						+ "(" + rs.getString("emp_name") + ")");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return managerMap;
	}

	public HashMap<String, String> showEmployee() {
		HashMap<String, String> empMap = new HashMap<String, String>();
		String sql = "select emp_id ,emp_name from "
				+ Constant.EMPLOYEE
				+ " where designation IN('Software Engineer','Senior Software Engineer','Technical Specialist') and manager_id='NULL'";
		try (Connection con = DataBaseUtil.getConnection();
				Statement stmt = con.createStatement();) {
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {

				empMap.put(rs.getString("emp_id"), rs.getString("emp_id") + "("
						+ rs.getString("emp_name") + ")");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return empMap;
	}
}
