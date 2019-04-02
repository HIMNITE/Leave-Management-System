package com.avekshaa.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.avekshaa.dbutils.*;
import com.avekshaa.utils.CommonUtiles;
import com.avekshaa.utils.Constant;
import com.avekshaa.utils.MailSend;

public class Approved {
	public void approved(String key) {
		GetLeaveId gt = new GetLeaveId();
		int leaveId = gt.getLeaveIdFromlink_status(key);
		try (Connection con = DataBaseUtil.getConnection();
				Statement stmt = con.createStatement()) {
			String sql = "update "
					+ Constant.LEAVEAPPLY
					+ " set status='approved',ReasonByManager='Approved'  where leave_id="
					+ leaveId;
			stmt.execute(sql);
			String email = "";
			String empId = "";
			String empName = "";

			String sql3 = "select emp_id from " + Constant.LEAVEAPPLY
					+ " where leave_id=" + leaveId;
			ResultSet rs = stmt.executeQuery(sql3);
			while (rs.next()) {
				empId = rs.getString("emp_id");
			}
			String sql4 = "select emp_name,company_email from " + Constant.EMPLOYEE
					+ " where emp_id='" + empId + "'";
			rs = stmt.executeQuery(sql4);
			while (rs.next()) {
				empName = rs.getString("emp_name");
				email = rs.getString("company_email");
			}
			CommonUtiles.updateVarificationLink(key);
			MailSend mail = new MailSend();
			mail.sendApprovalMailToEmp(email, empName);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void approvedById(String leaveid) {
	    int leaveId=Integer.parseInt(leaveid);
		try (Connection con = DataBaseUtil.getConnection();
				Statement stmt = con.createStatement()) {
			String sql = "update "
					+ Constant.LEAVEAPPLY
					+ " set status='approved',ReasonByManager='Approved'  where leave_id="
					+ leaveId;
			stmt.execute(sql);
			String email = "";
			String empId = "";
			String empName = "";

			String sql3 = "select emp_id from " + Constant.LEAVEAPPLY
					+ " where leave_id=" + leaveId;
			ResultSet rs = stmt.executeQuery(sql3);
			while (rs.next()) {
				empId = rs.getString("emp_id");
			}
			String sql4 = "select emp_name,company_email from " + Constant.EMPLOYEE
					+ " where emp_id='" + empId + "'";
			rs = stmt.executeQuery(sql4);
			while (rs.next()) {
				empName = rs.getString("emp_name");
				email = rs.getString("company_email");
			}
			MailSend mail = new MailSend();
			mail.sendApprovalMailToEmp(email, empName);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}