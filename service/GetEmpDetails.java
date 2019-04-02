package com.avekshaa.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONObject;

import com.avekshaa.dbutils.DataBaseUtil;
import com.avekshaa.utils.Constant;

public class GetEmpDetails {

	public List<JSONObject> leaveData;
	public String empId;

	public GetEmpDetails(String empId) {
		leaveData = new LinkedList<JSONObject>();
		this.empId = empId;
	}

	public GetEmpDetails() {
	}

	public void getStaticLeave() {
		JSONObject jsonData = null;
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		String sql = "select * from " + Constant.STATICLEAVE + " where date>='"
				+ currentYear + "-01-01'";
		try (Connection con = DataBaseUtil.getConnection();
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				jsonData = new JSONObject();
				jsonData.put("title", rs.getString("particulars"));
				jsonData.put("start", rs.getString("date"));
				jsonData.put("end", rs.getString("date"));
				if (rs.getString("location").equalsIgnoreCase("Mumbai Only")) {
					jsonData.put("textColor", "white");
					jsonData.put("color", " #510556 ");
				} else if (rs.getString("location").equalsIgnoreCase(
						"Banglore Only")) {
					jsonData.put("textColor", "black");
					jsonData.put("color", " #ee98f4 ");
					jsonData.put("font-size", " 30px ");
				} else if (rs.getString("location").equalsIgnoreCase("All")) {
					jsonData.put("textColor", "white");
					jsonData.put("color", "#AB27F1");
				}
				leaveData.add(jsonData);
			}
			getEmpLeaveDetails();
			getEmpLeaveDetailsCompOff();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getEmpLeaveDetails() {
		JSONObject jsonData = null;
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		String sql = "select * from " + Constant.LEAVEAPPLY
				+ " where emp_id ='" + empId + "' AND leave_from>='"
				+ currentYear + "-01-01'";
		try (Connection con = DataBaseUtil.getConnection();
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				jsonData = new JSONObject();
				jsonData.put("title", rs.getString("Reason"));
				jsonData.put("start", rs.getString("leave_from"));
				jsonData.put("end", rs.getDate("leave_to").getTime() + 86400000);
				String status = rs.getString("status");
				if ("pending".equalsIgnoreCase(status)) {
					jsonData.put("textColor", "black");
					jsonData.put("color", "#4253f4");
				} else if ("approved".equalsIgnoreCase(status)) {
					jsonData.put("textColor", "black");
					jsonData.put("color", "#34F41C");
				} else if ("Reject".equalsIgnoreCase(status)) {
					jsonData.put("textColor", "white");
					jsonData.put("color", "#EA4213");
				}
				leaveData.add(jsonData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getEmpLeaveDetailsCompOff() {
		JSONObject jsonData = null;
		String sql = "select * from " + Constant.COMPOFF + " where emp_id ='"
				+ empId + "'";
		try (Connection con = DataBaseUtil.getConnection();
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				jsonData = new JSONObject();
				jsonData.put("title", rs.getString("discription"));
				jsonData.put("start", rs.getString("comp_from"));
				jsonData.put("end", rs.getDate("comp_to").getTime() + 86400000);
				String status = rs.getString("status");
				if ("pending".equalsIgnoreCase(status)) {
					jsonData.put("textColor", "black");
					jsonData.put("color", "#ff0000");
				} else if ("approved".equalsIgnoreCase(status)) {
					jsonData.put("textColor", "black");
					jsonData.put("color", "#34F41C");
				} else if ("Reject".equalsIgnoreCase(status)) {
					jsonData.put("textColor", "white");
					jsonData.put("color", "#EA4213");
				}
				leaveData.add(jsonData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<java.util.Date> getStaticLeaveToDisable() {

		String sql = "select * from " + Constant.STATICLEAVE
				+ " where date>='2018-12-31'";
		ArrayList<java.util.Date> set = new ArrayList<>();
		try (Connection con = DataBaseUtil.getConnection();
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				// java.util.Date date=rs.getDate("date");
				set.add(rs.getDate("date"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return set;
	}

	public boolean getPassword(String pass, String empId) {
		String sql = "select * from " + Constant.EMPLOYEE + " where password='"
				+ pass + "' and emp_id='" + empId + "'";
		boolean flag = false;
		try (Connection con = DataBaseUtil.getConnection();
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			if (rs.next()) {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
}