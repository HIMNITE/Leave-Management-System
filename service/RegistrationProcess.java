package com.avekshaa.service;

import java.sql.Connection;
import java.sql.PreparedStatement;


import com.avekshaa.dbutils.*;
import com.avekshaa.utils.CommonUtiles;
import com.avekshaa.utils.Constant;

public class RegistrationProcess {
	public void submitEmp(String empId, String empName,
			String personalEmail, String companyEmail, String mobileNo,
			String dob, String gender, String designation, String doj,
			String address, String city, String state,String password,String randomKey) {
		String sql2 = "insert into "
				+ Constant.EMPLOYEE
				+ " (emp_id,emp_name,personal_email,company_email,mobile_no,dob,gender,designation,doj,address,city,state,password) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try (Connection con = DataBaseUtil.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql2)) {
			java.sql.Date dateOfBirth = java.sql.Date.valueOf(dob);// to dob
			java.sql.Date dateOfJoining = java.sql.Date.valueOf(doj);
			pstmt.setString(1, empId);
			pstmt.setString(2, empName);
			pstmt.setString(3, personalEmail);
			pstmt.setString(4, companyEmail);
			pstmt.setString(5, mobileNo);
			pstmt.setDate(6, dateOfBirth);
			pstmt.setString(7, gender);
			pstmt.setString(8, designation);
			pstmt.setDate(9, dateOfJoining);
			pstmt.setString(10, address);
			pstmt.setString(11, city);
			pstmt.setString(12, state);
			pstmt.setString(13, password);
			int result = pstmt.executeUpdate();
			if(result == 1){
				CommonUtiles.updateVarification(randomKey);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
