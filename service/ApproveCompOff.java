package com.avekshaa.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.avekshaa.dbutils.DataBaseUtil;
import com.avekshaa.utils.CommonUtiles;
import com.avekshaa.utils.Constant;
import com.avekshaa.utils.MailSend;

public class ApproveCompOff {

	public void approve(String compOffId, String key) {
		try (Connection con = DataBaseUtil.getConnection();
				Statement stmt = con.createStatement()) {
			String sql = "update " + Constant.COMPOFF
					+ " set status='approved',ReasonByManager='approved' where compoff_id=" + compOffId;
			stmt.execute(sql);

			String sql2 = "select no_days,emp_id from " + Constant.COMPOFF
					+ " where compoff_id=" + compOffId;
			ResultSet rs = stmt.executeQuery(sql2);
			String empId = "";
			String empName = "", email = "";
			int noDays = 1;
			int compoffDays = 1;
			while (rs.next()) {
				noDays = rs.getInt("no_days");
				empId = rs.getString("emp_id");
				
			}

			String sql3 = "select no_compoff,emp_name,company_email from " + Constant.EMPLOYEE
					+ " where emp_id='" + empId + "'";
			rs = stmt.executeQuery(sql3);
			while (rs.next()) {
				compoffDays = rs.getInt("no_compoff");
				empName = rs.getString("emp_name");
				email = rs.getString("company_email");
			}
			compoffDays += noDays;
			String sql4 = "update " + Constant.EMPLOYEE + " set no_compoff="
					+ compoffDays + " where emp_id='" + empId + "'";
			stmt.execute(sql4);
			CommonUtiles.updateVarificationLink(key);
			MailSend ms = new MailSend();
			ms.sendApprovedCompoffMail(email, empName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void approveByEmail(String key) {
		try (Connection con = DataBaseUtil.getConnection();
				Statement stmt = con.createStatement()) {
			GetLeaveId gt=new GetLeaveId();
			int compOffId=gt.getCompOffIdFromlink_status(key);
			String sql = "update " + Constant.COMPOFF
					+ " set status='approved',ReasonByManager='approved' where compoff_id=" + compOffId;
			stmt.execute(sql);

			String sql2 = "select no_days,emp_id from " + Constant.COMPOFF
					+ " where compoff_id=" + compOffId;
			ResultSet rs = stmt.executeQuery(sql2);
			String empId = "";
			String empName = "", email = "";
			int noDays = 1;
			int compoffDays = 1;
			while (rs.next()) {
				noDays = rs.getInt("no_days");
				empId = rs.getString("emp_id");
				
			}

			String sql3 = "select no_compoff,emp_name,company_email from " + Constant.EMPLOYEE
					+ " where emp_id='" + empId + "'";
			rs = stmt.executeQuery(sql3);
			while (rs.next()) {
				compoffDays = rs.getInt("no_compoff");
				empName = rs.getString("emp_name");
				email = rs.getString("company_email");
			}
			compoffDays += noDays;
			String sql4 = "update " + Constant.EMPLOYEE + " set no_compoff="
					+ compoffDays + " where emp_id='" + empId + "'";
			stmt.execute(sql4);
			CommonUtiles.updateVarificationLink(key);
			MailSend ms = new MailSend();
			ms.sendApprovedCompoffMail(email, empName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}