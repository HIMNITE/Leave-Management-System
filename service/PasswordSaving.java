package com.avekshaa.service;

import java.sql.Connection;
import java.sql.Statement;

import com.avekshaa.dbutils.*;
import com.avekshaa.utils.AES;
import com.avekshaa.utils.Constant;

public class PasswordSaving {
	public void getPassword(String email, String pass) {
		try (Connection con = DataBaseUtil.getConnection();
				Statement stmt = con.createStatement()) {
			String sql = "update " + Constant.EMPLOYEE + " set password='"
					+ pass + "'  where company_email='" + email + "'";
			stmt.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void resettingPassword(String newPassword, String emp_id,
			String oldPassword) {
		try (Connection con = DataBaseUtil.getConnection();
				Statement stmt = con.createStatement()) {
			newPassword = AES.encrypt(newPassword, Constant.SECRETKEY);
			String sql = "update " + Constant.EMPLOYEE + " set password='"
					+ newPassword + "'  where emp_id='" + emp_id
					+ "' and password='" + oldPassword + "'";
			stmt.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}