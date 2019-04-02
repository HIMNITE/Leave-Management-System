package com.avekshaa.service;

import java.sql.Connection;
import java.sql.Statement;

import com.avekshaa.dbutils.DataBaseUtil;
import com.avekshaa.utils.Constant;

public class AddedProject {
	String mailFrom = "pavanpurohit95@gmail.com";
	String pass = "zitaxatciqhgjtrq";

	public void added(String mangerID, String empIDs, String userName) {

		try (Connection con = DataBaseUtil.getConnection();
				Statement st = con.createStatement()) {
			empIDs = "'" + empIDs.replace(",", "','") + "'";
			String sql3 = "update " + Constant.EMPLOYEE + " set manager_id='"
					+ mangerID + "' where emp_id in(" + empIDs + ")";
			st.executeUpdate(sql3);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}