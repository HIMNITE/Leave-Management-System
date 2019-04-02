package com.avekshaa.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;

import com.avekshaa.dbutils.DataBaseUtil;

public class CommonUtiles {

	public static String camelCase(String str) {
		StringBuilder builder = new StringBuilder(str);
		boolean isLastSpace = true;
		for (int i = 0; i < builder.length(); i++) {
			char ch = builder.charAt(i);

			if (isLastSpace && ch >= 'a' && ch <= 'z') {
				builder.setCharAt(i, (char) (ch + ('A' - 'a')));
				isLastSpace = false;
			} else if (ch != ' ') {
				isLastSpace = false;
			} else {
				isLastSpace = true;
			}
		}
		return builder.toString();
	}

	public static String getSaltString() {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 18) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;
	}

	/** insert the random key it not present */
	public static String insertURLRandomKey(String desg) {
		String key = "";
		try {
			Connection con = DataBaseUtil.getConnection();
			Statement stm = con.createStatement();

			boolean flag = true;
			while (flag) {
				key = getSaltString();
				String sqlQuery = "select * from " + Constant.REGISTRATION_ID
						+ " where reg_key='" + key + "'";
				ResultSet rs = stm.executeQuery(sqlQuery);
				if (!rs.next()) {
					// insert into db
					long timestamp = System.currentTimeMillis();
					sqlQuery = "insert into " + Constant.REGISTRATION_ID
							+ " values('" + key + "'," + timestamp
							+ ",'notvarified','" + desg + "')";
					stm.execute(sqlQuery);
					break;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return key;
	}

	public static String isVarified(String key) {
		String statusAndDesg = "";
		try {
			Connection con = DataBaseUtil.getConnection();
			Statement stm = con.createStatement();
			String sqlQuery = "select status,designation from "
					+ Constant.REGISTRATION_ID + " where reg_key='" + key + "'";
			ResultSet rs = stm.executeQuery(sqlQuery);

			while (rs.next()) {
				statusAndDesg = rs.getString("status") + "-"
						+ rs.getString("designation");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusAndDesg;
	}

	public static boolean isLinkVarified(String key) {
		String status = "";
		String sqlQuery = "select status from " + Constant.REGISTRATION_ID
				+ " where reg_key='" + key + "'";
		boolean flag = true;
		try (Connection con = DataBaseUtil.getConnection();
				Statement stm = con.createStatement();
				ResultSet rs = stm.executeQuery(sqlQuery);) {

			while (rs.next()) {
				status = rs.getString("status");
			}
			if ("varified".equalsIgnoreCase(status)) {
				flag = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public String getDesktopPath() {
		String path = "";
		try {
			String desktopPath = System.getProperty("user.home") + "/Desktop";

			path = desktopPath.replace("\\", "/");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return path;
	}

	public static boolean isLinkVarifiedLeave(String key) {
		String status = "";
		String sqlQuery = "select status from " + Constant.LINK_STATUS
				+ " where link_key='" + key + "'";
		boolean flag = true;
		try (Connection con = DataBaseUtil.getConnection();
				Statement stm = con.createStatement();
				ResultSet rs = stm.executeQuery(sqlQuery);) {

			while (rs.next()) {
				status = rs.getString("status");
			}
			if ("varified".equalsIgnoreCase(status)) {
				flag = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static void updateVarification(String key) {
		try {
			Connection con = DataBaseUtil.getConnection();
			Statement stm = con.createStatement();
			String query = "update " + Constant.REGISTRATION_ID
					+ " SET status='varified' where reg_key='" + key + "'";
			stm.execute(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void insertKeyForLeave(String key, int leaveId) {
		try (Connection con = DataBaseUtil.getConnection();
				Statement st = con.createStatement()) {
			String sql = "insert into " + Constant.LINK_STATUS
					+ " (link_key,status,leave_id) values('" + key
					+ "', 'notvarified' ," + leaveId;
			st.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void insertKeyForCompoff(String key, int compoffId) {
		try (Connection con = DataBaseUtil.getConnection();
				Statement st = con.createStatement()) {
			String sql = "insert into " + Constant.LINK_STATUS
					+ " (link_key,status,compoff_id) values('" + key
					+ "', 'notvarified' ," + compoffId;
			st.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** update the random key for leave */
	public static void updateVarificationLink(String key) {
		try {
			Connection con = DataBaseUtil.getConnection();
			Statement stm = con.createStatement();
			String query = "update " + Constant.LINK_STATUS
					+ " SET status='varified' where link_key='" + key + "'";
			stm.execute(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** insert the random key for leave it not present */
	public static String insertURLRandomKeyForLeave(int leaveId) {
		String key = "";
		try {
			Connection con = DataBaseUtil.getConnection();
			Statement stm = con.createStatement();
			String status = "notvarified";
			boolean flag = true;
			while (flag) {
				key = getSaltString();
				String sqlQuery = "select * from " + Constant.LINK_STATUS
						+ " where link_key='" + key + "'";
				ResultSet rs = stm.executeQuery(sqlQuery);
				if (!rs.next()) {
					// insert into db
					sqlQuery = "insert into " + Constant.LINK_STATUS
							+ "(link_key,status,leave_id) values('" + key
							+ "','" + status + "'," + leaveId + ")";
					stm.execute(sqlQuery);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return key;
	}
	/** Method for Generate key For CompOff Apply*/
	public static String insertURLRandomKeyForCompOff(int CompOffId) {
		String key = "";
		try {
			Connection con = DataBaseUtil.getConnection();
			Statement stm = con.createStatement();
	        String status="notvarified";
			boolean flag = true;
			while (flag) {
				key = getSaltString();
				String sqlQuery = "select * from " + Constant.LINK_STATUS+ " where link_key='" + key + "'";
				ResultSet rs = stm.executeQuery(sqlQuery);
				if (!rs.next()) {
					// insert into db
					sqlQuery = "insert into " + Constant.LINK_STATUS+ "(link_key,status,compoff_id) values('" + key + "','" + status+ "',"+CompOffId + ")";
					stm.execute(sqlQuery);
					break;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return key;
	}

}