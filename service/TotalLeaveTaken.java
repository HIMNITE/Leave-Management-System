package com.avekshaa.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;

import com.avekshaa.dbutils.DataBaseUtil;
import com.avekshaa.utils.Constant;

public class TotalLeaveTaken {
	public String getLeaveRemaining(String empId) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String sql = "select * from " + Constant.LEAVEAPPLY
				+ " where status='approved' and emp_id='" + empId
				+ "' and leave_from>='" + year + "-01-01'";
		int s = 0, totalLeave = 20;
		;
		String tableStr = "";

		try (Connection con = DataBaseUtil.getConnection();
				Statement stmt = con.createStatement()) {
			ResultSet rs = stmt.executeQuery(sql);
			tableStr = "<center><table border='2px' width='50' height='100'></center>";
			tableStr += "<th>Total Leave</th><th>Total Taken Leave</th><th>Remaining Leave</th><th>LOP</th><th>CompOff</th></tr>";
			while (rs.next()) {
				s += rs.getInt("no_days");
			}
			String sql1 = "select * from " + Constant.EMPLOYEE
					+ " where emp_id='" + empId + "'";
			rs = stmt.executeQuery(sql1);
			if (rs.next()) {
				totalLeave = rs.getInt("total_leave");
			}
			CompOffDaysCount cc = new CompOffDaysCount();
			int count = cc.getDays(empId);
			tableStr += "<tr>";
			tableStr += "<td>" + totalLeave + "</td>";
			tableStr += "<td>" + s + "</td>";
			if (s >= 20) {
				tableStr += "<td>" + 0 + "</td>";
			} else {
				tableStr += "<td>" + (totalLeave - s + count) + "</td>";
			}
			if ((20 - s) < 0) {
				tableStr += "<td>" + (totalLeave - s) * (-1) + "</td>";
			} else {
				tableStr += "<td>" + 0 + "</td>";

			}
			tableStr += "<td>" + count + "</td>";

			tableStr += "</tr>";

			tableStr += "</table></center>";

		} catch (Exception e) {
			e.printStackTrace();
		}
		return tableStr;
	}
}
