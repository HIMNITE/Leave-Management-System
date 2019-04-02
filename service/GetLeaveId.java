package com.avekshaa.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.avekshaa.dbutils.DataBaseUtil;
import com.avekshaa.utils.Constant;

public class GetLeaveId {

	public int getLeaveId(Date sqlStartDate, Date sqlendDate, String empId) {

		int leaveId = 0;
		String sql = "select leave_id from " + Constant.LEAVEAPPLY
				+ " where emp_id='" + empId + "' and leave_from ='"
				+ sqlStartDate + "' and leave_to ='" + sqlendDate + "'";

		try (Connection con = DataBaseUtil.getConnection();
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				leaveId = rs.getInt("leave_id");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return leaveId;
	}

	public int getLeaveIdFromlink_status(String key) {
		int leaveId = 0;
		String sql = "select leave_id from " + Constant.LINK_STATUS
				+ " where link_key='" + key + "'";
		try (Connection con = DataBaseUtil.getConnection();
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				leaveId = rs.getInt("leave_id");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return leaveId;
	}

	public int getComOffId(String from, String to, String empId)
			throws ParseException {

		int compOffId = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date sqlFromDate = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(
				sdf.format(new SimpleDateFormat("E MMM dd yyyy HH:mm:ss")
						.parse(from))).getTime());
		Date sqlToDate = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(
				sdf.format(new SimpleDateFormat("E MMM dd yyyy HH:mm:ss")
						.parse(to))).getTime() - 86400000);

		String sql = "select compoff_id from " + Constant.COMPOFF
				+ " where emp_id='" + empId + "' and comp_from='" + sqlFromDate
				+ "' and comp_to='" + sqlToDate + "'";
		try (Connection con = DataBaseUtil.getConnection();
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				compOffId = rs.getInt("compoff_id");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return compOffId;
	}

	public int getCompOffIdFromlink_status(String key) {
		int compOffId = 0;
		String sql = "select compoff_id from " + Constant.LINK_STATUS
				+ " where link_key='" + key + "'";
		try (Connection con = DataBaseUtil.getConnection();
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				compOffId = rs.getInt("compoff_id");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return compOffId;
	}

	public String getKeyFromlink_status(int leaveId) {
		String key = "1234";
		String sql = "select link_key from " + Constant.LINK_STATUS
				+ " where leave_id=" + leaveId;
		try (Connection con = DataBaseUtil.getConnection();
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				key = rs.getString("link_key");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("key:"+key);
		return key;
	}

}