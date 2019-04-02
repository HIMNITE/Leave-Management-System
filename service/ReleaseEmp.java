package com.avekshaa.service;

import java.sql.Connection;
import java.sql.Statement;

import com.avekshaa.dbutils.DataBaseUtil;
import com.avekshaa.utils.Constant;

public class ReleaseEmp {
	public void relese(String empId) {
		try (Connection con = DataBaseUtil.getConnection();
				Statement st = con.createStatement()) {
			String sql = "update " + Constant.EMPLOYEE
					+ " set manager_id='NULL' where emp_id='" + empId + "'";
			st.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
