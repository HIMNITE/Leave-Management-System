package com.avekshaa.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;

import com.avekshaa.dbutils.DataBaseUtil;
import com.avekshaa.utils.Constant;

public class GetDate {
	public void getDate(Date sqlFromDate, Date sqlToDate, String company_email,
			String dis, String rea, String empId, String compoff) {

		try (Connection con = DataBaseUtil.getConnection();
				Statement st = con.createStatement()) {
			ResultSet rs = null;
			long mili1 = sqlFromDate.getTime();
			long mili2 = sqlToDate.getTime();
			long differnce = mili2 - mili1+86400000;
			long diff = (int) ((differnce) / (1000 * 60 * 60 * 24));
			long mili = mili1;
			int countWeekend = 0;
			long countHolidays = 0;
			java.util.Calendar calendar = java.util.Calendar.getInstance();
			calendar.setTimeInMillis(mili2);
			CompOffDaysCount cc = new CompOffDaysCount();
			int countCompoff = cc.getDays(empId);
			String query = "select count(*) as staticCount from " + Constant.STATICLEAVE +" where date >= '"+sqlFromDate+"' AND date <= '"+sqlToDate+"'";
			rs = st.executeQuery(query);
			while(rs.next()){
				countHolidays = rs.getLong("staticCount");
			}
			calendar.setTimeInMillis(mili);
			for (int i = 1; i <= diff; i++) {
				if(calendar.get(Calendar.DAY_OF_WEEK) == 7 || calendar.get(Calendar.DAY_OF_WEEK) == 1){
					countWeekend++;
				}
				calendar.add(Calendar.DATE, 1);
			}
			diff = (diff - countWeekend - countHolidays);
			boolean compOff = Boolean.parseBoolean(compoff);
			if (compOff && countCompoff >= diff) {
				String sql = "update " + Constant.EMPLOYEE + " set no_compoff="
						+ (countCompoff - diff) + " where emp_id='" + empId
						+ "'";
				diff = 0;
				st.execute(sql);
			} else if (compOff) {
				String sql = "update " + Constant.EMPLOYEE
						+ " set no_compoff=0 where emp_id='" + empId + "'";
				st.execute(sql);
				diff = diff - countCompoff;
			}

			String sql = "select emp_id,emp_name from " + Constant.EMPLOYEE
					+ " where company_email='" + company_email + "'";
			rs = st.executeQuery(sql);
			String eid = "", ename = "";
			if (rs.next()) {
				eid = rs.getString("emp_id");
				ename = rs.getString("emp_name");
			}
			String s6 = "pending";
			int tot = 20;
			String sql3 = "insert into "
					+ Constant.LEAVEAPPLY
					+ " (emp_id,Emp_Name,leave_from,leave_to,no_days,discription,Reason,status,total_leave) values('"
					+ eid + "','" + ename + "','" + sqlFromDate + "','"
					+ sqlToDate + "'," + diff + ",'" + dis + "','" + rea
					+ "','" + s6 + "'," + tot + ")";
			st.execute(sql3);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}