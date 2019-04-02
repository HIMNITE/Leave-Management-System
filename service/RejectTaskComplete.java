package com.avekshaa.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.avekshaa.dbutils.DataBaseUtil;
import com.avekshaa.utils.CommonUtiles;
import com.avekshaa.utils.Constant;
import com.avekshaa.utils.MailSend;

public class RejectTaskComplete {
	public void sendRejectMail(String reason, String key) {

		String email = "";
		String empId = "";
		String empName = "";
		GetLeaveId gt = new GetLeaveId();

		int leaveId = gt.getLeaveIdFromlink_status(key);
         System.out.println(leaveId);
		try (Connection con = DataBaseUtil.getConnection();
				Statement stmt = con.createStatement()) {
			String sql = "update " + Constant.LEAVEAPPLY
					+ " set ReasonByManager='" + reason + "' , status='reject' where leave_id="
					+ leaveId;
			stmt.execute(sql);
			String sql1 = "select emp_id from " + Constant.LEAVEAPPLY
					+ " where leave_id=" + leaveId;
			ResultSet rs = stmt.executeQuery(sql1);
			while (rs.next()) {
				empId = rs.getString("emp_id");
			}

			String sql2 = "select emp_name,company_email from " + Constant.EMPLOYEE
					+ " where emp_id='" + empId + "'";
			rs = stmt.executeQuery(sql2);
			while (rs.next()) {
				empName = rs.getString("emp_name");
				email = rs.getString("company_email");
			}
			CommonUtiles.updateVarificationLink(key);
		} catch (Exception e) {
			e.printStackTrace();
		}

		MailSend ms = new MailSend();
		ms.sendRejectLeaveMail(email, reason, empName);

	}

	public void sendRejectMailFromApp(String leaveIdStr, String reason, String key) {

		String email = "";
		int leaveId = Integer.parseInt(leaveIdStr);
		String empId = "", empName = "";
		try (Connection con = DataBaseUtil.getConnection();
				Statement stmt = con.createStatement()) {
			String sql = "update " + Constant.LEAVEAPPLY
					+ " set ReasonByManager='" + reason + "'where leave_id="
					+ leaveId;
			stmt.execute(sql);
			String sql1 = "select emp_id from " + Constant.LEAVEAPPLY
					+ " where leave_id=" + leaveIdStr;
			ResultSet rs = stmt.executeQuery(sql1);

			while (rs.next()) {
				empId = rs.getString("emp_id");
			}

			String sql2 = "select emp_name,company_email from " + Constant.EMPLOYEE
					+ " where emp_id='" + empId + "'";
			rs = stmt.executeQuery(sql2);
			while (rs.next()) {
				empName = rs.getString("emp_name");
				email = rs.getString("company_email");
			}
			CommonUtiles.updateVarification(key);
		} catch (Exception e) {
			e.printStackTrace();
		}

		MailSend ms = new MailSend();
		ms.sendRejectLeaveMail(email, reason, empName);

	}
}
