package com.avekshaa.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.avekshaa.dbutils.DataBaseUtil;

public class GetEmail {//project_id
	public String getEmail(String empId) {
		String mEmail = "amit.raikwar@avekshaa.com";
		String sql = "select company_email from employee where designation='Manager' and emp_id IN (select manager_id from employee where emp_id ='"+empId+"')";
		try (Connection con = DataBaseUtil.getConnection();
				Statement st = con.createStatement()) {
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				mEmail = rs.getString("company_email");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mEmail;
	}

	public String getHREmail() {
		String hrEmail = "amit.raikwar@avekshaa.com";
		String sql = "select company_email from employee where designation='HR'";
		try (Connection con = DataBaseUtil.getConnection();
				Statement st = con.createStatement()) {
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				hrEmail = rs.getString("company_email");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hrEmail;
	}

}
