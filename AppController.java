package com.avekshaa.controller;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.avekshaa.service.AddedProject;
import com.avekshaa.service.ApproveCompOff;
import com.avekshaa.service.Approved;
import com.avekshaa.service.FileUpload;
import com.avekshaa.service.GetDate;
import com.avekshaa.service.GetEmail;
import com.avekshaa.service.GetEmpDetails;
import com.avekshaa.service.GetLeaveId;
import com.avekshaa.service.Getcompdate;
import com.avekshaa.service.PasswordSaving;
import com.avekshaa.service.RejectCompOff;
import com.avekshaa.service.RejectTaskComplete;
import com.avekshaa.service.ReleaseEmp;
import com.avekshaa.service.SendPassword;
import com.avekshaa.utils.AES;
import com.avekshaa.utils.CommonUtiles;
import com.avekshaa.utils.Constant;
import com.avekshaa.utils.DateConvertion;
import com.avekshaa.utils.MailSend;

@Controller
public class AppController {
	@RequestMapping(value = "/get/events", method = RequestMethod.GET)
	@ResponseBody
	public String getCalEvent(HttpServletRequest request,
			HttpServletResponse response) {
		String empId = request.getSession().getAttribute("empId").toString();
		GetEmpDetails getEmpDetails = new GetEmpDetails(empId);
		getEmpDetails.getStaticLeave();
		return getEmpDetails.leaveData.toString();
	}

	@RequestMapping(value = "/changePassword/getData", method = RequestMethod.POST)
	@ResponseBody
	public String showchangePassword(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		String empId = session.getAttribute("empId").toString();
		String oldPassword=request.getParameter("oldPass");
		String newPassword = request.getParameter("newPass");
		PasswordSaving ps = new PasswordSaving();
		oldPassword=AES.encrypt(oldPassword, Constant.SECRETKEY);
		ps.resettingPassword(newPassword, empId,oldPassword);
		return "changePassword";
	}

	@RequestMapping("/changePassword")
	public String showchangePassword() {
		return "changePassword";
	}

	@RequestMapping(value = "/staticData", method = RequestMethod.GET)
	@ResponseBody
	public String getSataticData(HttpServletRequest request,
			HttpServletResponse response) {
		GetEmpDetails getEmpDetails = new GetEmpDetails();
		ArrayList<java.util.Date> set = getEmpDetails.getStaticLeaveToDisable();
		return set.toString();
	}

	@RequestMapping("/getcompdate")
	@ResponseBody
	public String showgetcompate(HttpServletRequest restRequest,
			HttpServletResponse response) throws ParseException {
		HttpSession session = restRequest.getSession();
		String from = restRequest.getParameter("start");
		String to = restRequest.getParameter("end");
		String disc = restRequest.getParameter("descr");
		String empName = session.getAttribute("emp_name").toString();
		String email = session.getAttribute("company_email").toString();
		String desg = session.getAttribute("designation").toString();
		String mobile = session.getAttribute("mobile").toString();
        Getcompdate gc = new Getcompdate();
		String empId = session.getAttribute("empId").toString();
		java.util.Date utilFromDate = new SimpleDateFormat("E MMM dd yyyy HH:mm:ss").parse(from);
        Date sqlFromDate=new Date(utilFromDate.getTime());
    	java.util.Date utilToDate = new SimpleDateFormat("E MMM dd yyyy HH:mm:ss").parse(to);
        Date sqlToDate=new Date(utilToDate.getTime()-86400000);
      gc.getCompData(sqlFromDate, sqlToDate, empId,empName, disc);
		GetEmail gm = new GetEmail();
		String managerEmail = gm.getEmail(empId);
		MailSend ms = new MailSend();
		GetLeaveId id=new GetLeaveId();
		String key = CommonUtiles.insertURLRandomKeyForCompOff(id.getComOffId(from, to, empId));
		GetEmail getEmail = new GetEmail();
                String hrEmail=getEmail.getHREmail();
	    	    SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd yyyy");
        String formattedToDate = formatter.format(sqlToDate);
        hrEmail="manishgautameee@gmail.com";////
        managerEmail="princepandeyroyal1@gmail.com";//// 
        ms.sendMailtoHrForCompoff(hrEmail,empName, DateConvertion.changeDateFormate(from), formattedToDate, disc, desg,mobile, email);
		ms.sendMailtomanagerForCompoff(managerEmail, empId, DateConvertion.changeDateFormate(from), formattedToDate, empName,disc,mobile,desg ,key);
		ms.sendMailToEmployeeForCompoff(email, empName);
		return "getcompdate";
	}

	@RequestMapping("/CompOffApproveByManager")
	public String showCompOffApproveByManager(HttpServletRequest restRequest,
			HttpServletResponse response) {
		String key = "";
		String compoffId = restRequest.getParameter("compoff_id");
		ApproveCompOff ac = new ApproveCompOff();
		ac.approve(compoffId, key);
		return "CompOff";
	}

	@RequestMapping("/UploadEmpLeave")
	public String showUploadEmpLeave() {
		return "UploadEmpLeave";
	}

	@RequestMapping("/UpdateEmpLeave")
	public String showUpdateEmpLeave(HttpServletRequest restRequest,
			HttpServletResponse response) {
		try {
			String contentType = restRequest.getContentType();
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<?> list = upload.parseRequest(restRequest);
			FileUpload fu = new FileUpload();
			fu.empLeaveUpload(contentType, list, upload, factory);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "UploadEmpLeave";
	}

	@RequestMapping("/empList")
	public String showempList() {
		return "empList";
	}

	

	@RequestMapping("/CompOff")
	public String showCompOffDetail() {

		return "CompOff";
	}

	@RequestMapping("/release")
	public String showrelese(HttpServletRequest restRequest,
			HttpServletResponse response) {
		String empId = restRequest.getParameter("emp_id");
		ReleaseEmp release = new ReleaseEmp();
		release.relese(empId);
		return "empList";
	}

	@RequestMapping("/RejectCompOff")
	public String showRejectCompOff() {

		return "RejectCompOff";
	}

	@RequestMapping("/RejectMessage")
	public String showRejectMessage() {
		return "RejectMessage";
	}

	

	@RequestMapping("/leaveDetail")
	public String showLeaveDetail() {
		return "leaveDetail";
	}

	@RequestMapping("/calenderforevryone")
	public String showcalenderforevryone() {

		return "calenderforevryone";
	}

	@RequestMapping("/Employee")
	public String showEmployee() {
		return "Employee";
	}
	@RequestMapping("/ForgotPassword")
	public String showForgotPassword(HttpServletRequest restRequest,
			HttpServletResponse response) {
	String email=restRequest.getParameter("email");
	SendPassword sp=new SendPassword();
	sp.resetpassword(email);
		return "home";
	}

	@RequestMapping("/rejectcomoffbyemail")
	public String showrejectcomoffbyemail(HttpServletRequest restRequest,
			HttpServletResponse response) {
		HttpSession session = restRequest.getSession();
		String key = restRequest.getParameter("key");
		boolean flag = CommonUtiles.isLinkVarifiedLeave(key);	
		if (flag) {
			session.setAttribute("key", key);
			session.setAttribute("status", "CompOff");
			session.setAttribute("message", "Rejected");
			return "rejectcomoffbyemail";
		} else {
			return "MessageForLinkUses";
		}
	}

	@RequestMapping("/rejectCompOffbyEmailupdate")
	public String showupdate(
			HttpServletRequest restRequest, HttpServletResponse response) {
		
		HttpSession session = restRequest.getSession();
		String key = session.getAttribute("key").toString();
		String dis = restRequest.getParameter("message");
		RejectCompOff rc = new RejectCompOff();
		rc.rejectingCompOffbyEmail(dis,key);
		return "messageForUpdate";

	}

	@RequestMapping("/ApproveCompOff")
	public String showApproveCompOff(HttpServletRequest restRequest,
			HttpServletResponse response) {

		String key = restRequest.getParameter("key");
		HttpSession session = restRequest.getSession();
		boolean flag = CommonUtiles.isLinkVarifiedLeave(key);
		if (flag) {
			session.setAttribute("status", "CompOff");
			session.setAttribute("message", "Approved");
			ApproveCompOff aco = new ApproveCompOff();
			aco.approveByEmail(key);
			return "messageForUpdate";
		} else {
			return "MessageForLinkUses";
		}
	}

	@RequestMapping(value = { "/login" }, method = RequestMethod.POST)
	public String showLogin(HttpServletRequest request,
			HttpServletResponse response) {
		return "login";
	}
	@RequestMapping("/emailapprove")
	public String showemailapprove(HttpServletRequest restRequest,
			HttpServletResponse response) {
		String key = restRequest.getParameter("key");
		HttpSession session = restRequest.getSession();
		boolean flag = CommonUtiles.isLinkVarifiedLeave(key);
		if (flag) {
			session.setAttribute("status", "Leave");
			session.setAttribute("message", "Approved");
			Approved ap = new Approved();
			ap.approved(key);
			return "messageForUpdate";
		} else {
			return "MessageForLinkUses";
		}
	}
	
	@RequestMapping("/emailrejection")
	public String showemailrejection(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		String key = request.getParameter("key");
		session.setAttribute("key", key);
		boolean flag = CommonUtiles.isLinkVarifiedLeave(key);
		if (flag) {
			session.setAttribute("status", "Leave");
			session.setAttribute("message", "Rejected");
			return "emailrejection";
		} else {
			return "MessageForLinkUses";
		}
	}
	@RequestMapping("/Reject")
	public String showReject(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		String key = request.getParameter("key");
		session.setAttribute("key", key);
		return "RejectedByManager";
	}
	@RequestMapping("/")
	public String showIndex() {
		return "home";
	}

	@RequestMapping("/sendRegistrationLink")
	public String linkSending() {
		return "sendRegistrationLink";
	}

	@RequestMapping("/finaceEmpLeave")
	public String showfinaceEmpLeave() {
		return "finaceEmpLeave";
	}

	@RequestMapping("/UpdateHolidays")
	public String showUpdateHolidays() {
		return "UpdateHolidays";
	}

	@RequestMapping("/approvedleaveofemp")
	public String approvedleaveofemp() {

		return "approvedleaveofemp";
	}

	@RequestMapping("/Rejectleaveofemp")
	public String Rejectleaveofemp() {
		return "Rejectleaveofemp";
	}

	@RequestMapping("/Penadindleaveofemp")
	public String Penadindleaveofemp() {
		return "Penadindleaveofemp";
	}

	@RequestMapping("/Storefor")
	public String showStorefor() {
		return "Storefor";
	}

	@RequestMapping("/RegistrationLinkSent")
	public String showRegistrationLinkSent() {
		return "RegistrationLinkSent";
	}

	@RequestMapping("/RegistrationProcess")
	public String showRegistrationProcess() {
		return "RegistrationProcess";
	}

	@RequestMapping("/MessageForLinkUses")
	public String showMessageForLinkUses() {
		return "MessageForLinkUses";
	}

	@RequestMapping("/DeleveryHeadTask")
	public String showDeleveryHeadTask() {
		return "DeleveryHeadTask";
	}

	

	@RequestMapping("/RejectedByManager")
	public String showRejectedByManager() {
		return "RejectedByManager";
	}

	@RequestMapping("/logout")
	public String showLogout() {
		return "logout";
	}

	@RequestMapping("/addProjct")
	public String showaddProjct() {
		return "addProjct";
	}
	@RequestMapping("/error")
	public String showError() {
		return "error";
	}

	@RequestMapping("/home")
	public String showHome() {
		return "home";
	}

	@RequestMapping("/Hr")
	public String showHr() {
		return "Hr";
	}

	@RequestMapping("/Leave")
	public String showLeave() {
		return "Leave";
	}

	@RequestMapping("/manager")
	public String showManager() {
		return "manager";
	}

	@RequestMapping("/finance")
	public String showFinance() {
		return "finance";
	}

	@RequestMapping("/messageForUpdate")
	public String showmessageForUpdate() {
		return "messageForUpdate";
	}
	@RequestMapping("/AfterRejectApprove")
	public String showAfterRejectApprove(HttpServletRequest restRequest,
			HttpServletResponse response) {
		String leaveId = restRequest.getParameter("leave_id");
		Approved ap = new Approved();
		ap.approvedById(leaveId);
    	return "Rejectleaveofemp";
	}

	@RequestMapping("/Approved")
	public String showApproved(HttpServletRequest restRequest,
			HttpServletResponse response) {
		String key = restRequest.getParameter("key").toString();
		Approved ap = new Approved();
		ap.approved(key);
		return "Penadindleaveofemp";
	}

	@RequestMapping("/rejectiondiscrionmail")
	public String showrejectiondiscrionmail(HttpServletRequest restRequest,
			HttpServletResponse response) {
		HttpSession session = restRequest.getSession();
		String key = session.getAttribute("key").toString();
		String reason = restRequest.getParameter("reason");
		RejectTaskComplete rc = new RejectTaskComplete();
		rc.sendRejectMail(reason, key);
		return "messageForUpdate";
	}

	@RequestMapping("/RejectMail")
	public String showRejectMail(HttpServletRequest restRequest,
			HttpServletResponse response) {
		HttpSession session = restRequest.getSession();
		String key = session.getAttribute("key").toString();
		String reason = restRequest.getParameter("reason");
		RejectTaskComplete rj = new RejectTaskComplete();
		System.out.println(reason+key);
		rj.sendRejectMail(reason,key);
		return "Penadindleaveofemp";
	}

	@RequestMapping("/RejectCompOffSaving")
	public String showRejectCompOffSaving(HttpServletRequest restRequest,
			HttpServletResponse response) {
		String key = "";
		HttpSession session = restRequest.getSession();
		String dis = restRequest.getParameter("message");
		String compOffId = session.getAttribute("compoff_id").toString();
		RejectCompOff rc = new RejectCompOff();
		rc.rejectingCompOff(dis, compOffId, key);
		return "CompOff";
	}

	@RequestMapping("/addedProject")
	@ResponseBody
	public String showaddedProject(HttpServletRequest restRequest,
			HttpServletResponse response) {
		HttpSession session = restRequest.getSession();
		String empName = session.getAttribute("emp_name").toString();
		String managerID = restRequest.getParameter("managerID");
		String empIDs = restRequest.getParameter("empIDs");
		AddedProject ap = new AddedProject();
		ap.added(managerID, empIDs, empName);
		return "addProject";
	}

	@RequestMapping(value = "/Registration")
	public String showRegistration(HttpServletRequest request,
			@RequestParam("key") String key) {

		String[] statusAndDesg = CommonUtiles.isVarified(key).split("-");
		HttpSession session = request.getSession();
		if (statusAndDesg[0].length() == 0) {
			session.setAttribute("varifyMsg",
					"<label style=\"color: red;\">Registration ID " + key
							+ " not founds</label>");
			return "linkvarified";
		} else if ("varified".equals(statusAndDesg[0])) {
			session.setAttribute("varifyMsg", "You are already registered");
			return "linkvarified";
		} else {
			session.setAttribute("desg", statusAndDesg[1]);
			session.setAttribute("randomKey", key);
			return "Registration";
		}

	}

	@RequestMapping("/getDate")
	@ResponseBody
	public String showgetDate(HttpServletRequest restRequest,
			HttpServletResponse response) throws ParseException {
		HttpSession session = restRequest.getSession();
		String from = restRequest.getParameter("start");
		String to = restRequest.getParameter("end");
		String title = restRequest.getParameter("title");
		String disc = restRequest.getParameter("desc");
		String compoff = restRequest.getParameter("compoff");
		String name = session.getAttribute("emp_name").toString();
		String email = session.getAttribute("company_email").toString();
		String empId = session.getAttribute("empId").toString();
		String desg = session.getAttribute("designation").toString();
		String mobile = session.getAttribute("mobile").toString(); 
		GetEmpDetails details = new GetEmpDetails();
		ArrayList<java.util.Date> list = details.getStaticLeaveToDisable();
		java.util.Date utilFromDate = new SimpleDateFormat("E MMM dd yyyy HH:mm:ss").parse(from);
        Date sqlFromDate=new Date(utilFromDate.getTime());
    	java.util.Date utilToDate = new SimpleDateFormat("E MMM dd yyyy HH:mm:ss").parse(to);
        Date sqlToDate=new Date(utilToDate.getTime()-86400000);
		boolean flag = false;
		for (int i = 0; i < list.size(); i++) {
			java.util.Date staticDate = list.get(i);
			Date sqlDate = new Date(staticDate.getTime());
			if (sqlFromDate.equals(sqlDate)) {
				flag = true;
			}
		}
		if (!(sqlToDate.equals(sqlFromDate) && flag)) {
			GetDate gt = new GetDate();
			gt.getDate(sqlFromDate, sqlToDate, email, disc, title, empId, compoff);
			GetEmail gm = new GetEmail();
			String managerEmail = gm.getEmail(empId);
			GetLeaveId getId=new GetLeaveId();
			int leavId=getId.getLeaveId(sqlFromDate, sqlToDate, empId);
			String uniqueKey = CommonUtiles.insertURLRandomKeyForLeave(leavId);
			MailSend ms = new MailSend();
	            String hrEmail=gm.getHREmail();
           
            SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd yyyy");
            String formattedToDate = formatter.format(sqlToDate);
            hrEmail="manishgautameee@gmail.com";////
            managerEmail="princepandeyroyal1@gmail.com";//// 
           
            ms.sendMailtoHrForLeave(hrEmail, name, DateConvertion.changeDateFormate(from), formattedToDate, disc, desg, mobile,email);
			ms.sendMailtomanagerForLeave(managerEmail, email, DateConvertion.changeDateFormate(from), formattedToDate,disc, name,uniqueKey,mobile,desg,title);
			ms.sendMailtoemployeeForLeaveApply(email, name);
		} else {
			return "";
		}
		return "";
	}
}
