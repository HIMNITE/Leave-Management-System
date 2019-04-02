package com.avekshaa.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.avekshaa.dbutils.DataBaseUtil;
import com.avekshaa.utils.CommonUtiles;
import com.avekshaa.utils.Constant;
import com.avekshaa.utils.MailSend;

public class RejectCompOff {

	public void rejectingCompOff(String dis, String compOffId, String key) {

		try (Connection con = DataBaseUtil.getConnection();
				Statement stmt = con.createStatement()) {
			String sql = "update " + Constant.COMPOFF
					+ " set status='Reject',ReasonByManager='" + dis + "' where compoff_id=" + compOffId;
			stmt.execute(sql);
			String sql2 = "select no_days,emp_id from " + Constant.COMPOFF
					+ " where compoff_id=" + compOffId;
			ResultSet rs = stmt.executeQuery(sql2);
			String empId = "", empName = "", email = "";
			int noDays = 0;
			while (rs.next()) {
				noDays = rs.getInt("no_days");
				empId = rs.getString("emp_id");

			}
			String sql3 = "select emp_name,no_compoff,company_email from " + Constant.EMPLOYEE
					+ " where emp_id='" + empId + "'";
			rs = stmt.executeQuery(sql3);
			int compoffCount = 1;
			while (rs.next()) {
				empName = rs.getString("emp_name");
				compoffCount = rs.getInt("no_compoff");
				email = rs.getString("company_email");
			}
			compoffCount -= noDays;
			String sql5 = "update " + Constant.EMPLOYEE + " set no_compoff="
					+ compoffCount + " where emp_id='" + empId + "'";
			stmt.execute(sql5);
			CommonUtiles.updateVarificationLink(key);
			MailSend ms = new MailSend();
			ms.sendRejectionCompoffMailToEmp(email, dis, empName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void rejectingCompOffbyEmail(String dis, String key) {
		GetLeaveId id=new GetLeaveId();
		int compOffId=id.getCompOffIdFromlink_status(key);
		try (Connection con = DataBaseUtil.getConnection();
				Statement stmt = con.createStatement()) {
			String sql = "update " + Constant.COMPOFF
					+ " set status='Reject',ReasonByManager='" + dis + "' where compoff_id=" + compOffId;
			stmt.execute(sql);
			String sql2 = "select no_days,emp_id from " + Constant.COMPOFF
					+ " where compoff_id=" + compOffId;
			ResultSet rs = stmt.executeQuery(sql2);
			String empId = "", empName = "", email = "";
			int noDays = 0;
			while (rs.next()) {
				noDays = rs.getInt("no_days");
				empId = rs.getString("emp_id");

			}
			String sql3 = "select emp_name,no_compoff,company_email from " + Constant.EMPLOYEE
					+ " where emp_id='" + empId + "'";
			rs = stmt.executeQuery(sql3);
			int compoffCount = 1;
			while (rs.next()) {
				empName = rs.getString("emp_name");
				compoffCount = rs.getInt("no_compoff");
				email = rs.getString("company_email");
			}
			compoffCount -= noDays;
			String sql5 = "update " + Constant.EMPLOYEE + " set no_compoff="
					+ compoffCount + " where emp_id='" + empId + "'";
			stmt.execute(sql5);
			CommonUtiles.updateVarificationLink(key);
			MailSend ms = new MailSend();
			ms.sendRejectionCompoffMailToEmp(email, dis, empName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
