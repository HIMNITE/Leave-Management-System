package com.avekshaa.dbutils;

import java.sql.*;

public class DataBaseUtil implements DataBaseUtilInterface {

	public static Connection getConnection() {
		Connection con = null;
			try {
				Class.forName(DRIVER_NAME);
				con = DriverManager.getConnection(URL, USERNAME, PASS_WORD);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		return con;
	}
}