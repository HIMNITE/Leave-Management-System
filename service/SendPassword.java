package com.avekshaa.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.avekshaa.dbutils.DataBaseUtil;
import com.avekshaa.utils.AES;
import com.avekshaa.utils.Constant;
import com.avekshaa.utils.MailSend;

public class SendPassword {
	public void resetpassword(String email) {
		String sql = "select password from " + Constant.EMPLOYEE
				+ " where company_email='" + email + "'";
		try (Connection con = DataBaseUtil.getConnection();
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			String pass = "";
			while (rs.next()) {
				pass = rs.getString("password");
			}
			pass = AES.decrypt(pass, Constant.SECRETKEY);
			MailSend send = new MailSend();
			send.sendMailTtoEmployeeForPassword(email, pass);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
