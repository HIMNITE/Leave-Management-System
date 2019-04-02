package com.avekshaa.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.avekshaa.dbutils.DataBaseUtil;
import com.avekshaa.utils.Constant;

public class CompOffDaysCount {
	public int getDays(String empId) {
		int noCompOff = 0;
		String sql = "select no_compoff from " + Constant.EMPLOYEE
				+ " where emp_id='" + empId + "'";
		try (Connection con = DataBaseUtil.getConnection();
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				noCompOff = rs.getInt("no_compoff");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return noCompOff;
	}
}
