package com.avekshaa.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.avekshaa.dbutils.DataBaseUtil;
import com.avekshaa.utils.Constant;
import com.avekshaa.utils.MailSend;

public class AfterRejectApprovedMail {

	public void afterRejectApprovedMailsent(String leaveId) {
		int leaveId1 = Integer.parseInt(leaveId);
		String email = "";
		String empId = "";
		String ename = "";
		try (Connection con = DataBaseUtil.getConnection();
				Statement stmt = con.createStatement()) {
			String sql = "select emp_id from " + Constant.LEAVEAPPLY
					+ " where leave_id=" + leaveId1;
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				empId = rs.getString(1);
			}
			String sql1 = "select company_email,emp_name from " + Constant.EMPLOYEE
					+ " where emp_id='" + empId + "'";
			rs = stmt.executeQuery(sql1);
			while (rs.next()) {
				email = rs.getString("company_email");
				ename = rs.getString("emp_name");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		MailSend ms = new MailSend();
		ms.sendApprovalMailToEmp(email, ename);
	}
}
