package com.avekshaa.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import com.avekshaa.dbutils.DataBaseUtil;
import com.avekshaa.service.FindManager;

public class MailSend implements Constant {
	MailSend ms = null;

	public Session getSession() {
		String host = "smtp.gmail.com";
		Properties prop = new Properties();
		prop.put("mail.smtp.host", host);
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.debug", "false");
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.port", "587");
		prop.put("mail.debug", "true");
		Session session = Session.getDefaultInstance(prop, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(EMAILID, PASSWORD);
			}
		});
		return session;
	}

	public String sendMailTtoEmployeeForPassword(String mailId, String pass) {
		if (mailId == "" || mailId == null) {
			return "Mail Delivery Failed";
		}
		try {
			Session sess = getSession();
			MimeMessage message = new MimeMessage(sess);
			InternetAddress address = new InternetAddress(mailId);
			message.setFrom(address);
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					mailId));
			message.setSubject("Password Reset");
			String text = "<html>"
					+ "<body>"
					+ "<div style=\"padding-left: 2%; padding-top: 0%;\">"
					+ "<div style=\"background-color: #f2f2f2;  width: 700px; border:none ;padding: 30px;margin: 25px;\">"
					+ "<p> Dear User, </p>"
					+ "<p> <bold> We have recieved a request to reset your password. Please change your password. </p><br>"
					+ "<p>Password:"
					+ pass
					+ " </p>"
					+ "<p> <bold>if you haven't requested to change your password, please contact our support team at amit.raikwar@avekshaa.com </p></br>"
					+ "<p> <bold>Sincerely, </p></br>"
					+ "<p> <bold>Team Avekshaa </p></br>" + "</div>" + "</div>"
					+ "</body>" + "</html>";
			message.setContent(text, "text/html");
			sess.setDebug(false);
			Transport.send(message);
			return "Mail has been sent sucessfully. Please check your Mail and Reset your Password";
		} catch (MessagingException e) {
			e.printStackTrace();
			return "Mail Delivery Failed";
		}

	}

	/** This method for send email to employee after approved compoff by manager*/
	public String sendApprovedCompoffMail(String mailId, String name) {
		if (mailId == "" || mailId == null) {
			return "Mail Delivery Failed";
		}
		try {
			Session sess = getSession();
			MimeMessage message = new MimeMessage(sess);
			InternetAddress address = new InternetAddress(mailId);
			message.setFrom(address);
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					mailId));
			message.setSubject("CompOff Has been Approved");
			String text = "<html>"
					+ "<body>"
					+ "<div style=\"padding-left: 2%; padding-top: 0%;\">"
					+ "<div style=\"background-color: AntiqueWhite;  width: 700px; border:none ;padding: 30px;margin: 25px;\">"
					+ " hello! "
					+ name
					+ " <br>"
					+ "<p>Your request for CompOff Has Been Approved by manager.</p>"
					+ "<br>" + "</div>" + "</div>" + "</body>" + "</html>";
			message.setContent(text, "text/html");
			sess.setDebug(false);
			Transport.send(message);
			return "Mail has been sent sucessfully.";
		} catch (MessagingException e) {
			return "Mail Delivery Failed";
		}
	}

	/** This method is for send email to manager after applying leave by employee*/
	public String sendMailtomanagerForLeave(String mailId, String empEmail,
			String from, String to,String dis, String name, String uniqueKey,String mobile,String deg,String reas) {
		if (mailId == "" || mailId == null) {
			return "Mail Delivery Failed";
		}
		try {
			FindManager fm = new FindManager();
			String managerName = fm.findName(mailId);
			Session sess = getSession();
			MimeMessage message = new MimeMessage(sess);
			InternetAddress address = new InternetAddress(mailId);
			message.setFrom(address);
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					mailId));
			message.setSubject("Applying for a leave " + reas);
			String text = "<html>"
					+ "<body>"
					+ "<div style=\"padding-left: 2%; padding-top: 0%;\">"
					+ "<div style=\"background-color: AntiqueWhite;  width: 700px; border:none ;padding: 30px;margin: 25px;\">"
					+ "Hi "
					+ managerName
					+ ", <br><br> I would like to kindly ask for your approval to my leave. <br>"
					+ "I want to leave due to <b>"
					+ dis
					+ ". </b><br>Leave starting on <b>"
					+ from
					+ " </b>and ending on <b>"
					+ to
					+ " </b><br>"
					+ "I appreciate your prompt response for my request.<br>"
					+ "<p> Please grant my leave</p><br>"
					+ "<a  style=\"background-color:red;color:white; padding: 15px 32px; text-decoration:none;text-align: center;font-size: 16px;cursor: pointer;\" href=\"http://"
					+ Constant.HOST
					+ "/LeaveManagementSystem/emailapprove?&key="
					+ uniqueKey
					+ "\">Approve</a>"
					+ "<a  style=\"background-color:blue;color:white; padding: 15px 32px; text-decoration:none;text-align: center;font-size: 16px;cursor: pointer;\" href=\"http://"
					+ Constant.HOST
					+ "/LeaveManagementSystem/emailrejection?&key="
					+ uniqueKey
					+ "\">Reject</a><br><br>"
					+ " <p>Thanks and rgds</p>"
					+ " "
					+ name
					+ "<br>"
					+ deg
					+ "<br>"
					+ empEmail
					+ "<br> +91 -"
					+ mobile
					+ " <br>Avekshaa Technologies (P) Ltd. | www.avekshaa.com<br>"
					+ " Business - IT Predictability and Assurance with Avekshaa<br>"
					+ " Deloitte Technology Fast 50 India | Technology Fast 500 APAC 2014<br>"
					+ " NASSCOM Emerge 50, 2013<br>"
					+ " Indian Express Top 3 Finalist, start-up<br>"
					+ "</div>"
					+ "</div>" + "</body>" + "</html>";
			message.setContent(text, "text/html");
			sess.setDebug(false);
			Transport.send(message);
			return "Mail has been sent sucessfully. Please check your Mail and Reset your Password";
		} catch (Exception e) {
			e.printStackTrace();
			return "Mail Delivery Failed";
		}
	}

	/**
	 * this for send email to manager after applying Compoff by employee
	 * 
	 * @param email
	 *            mail to be send
	 * */
	public String sendMailtomanagerForCompoff(String email, String empId,
			String from, String to, String name, String desc,String mobile,String designation, String key) {
		if (email == "" || email == null) {
			return "Mail Delivery Failed";
		}
		try {
			FindManager fm = new FindManager();
			String managerName = fm.findName(email);
			Session sess = getSession();
			MimeMessage message = new MimeMessage(sess);
			InternetAddress address = new InternetAddress(email);
			message.setFrom(address);
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					email));
			message.setSubject("Applying for Compoff ");
			String text = "<html>"
					+ "<body>"
					+ "<div style=\"padding-left: 2%; padding-top: 0%;\">"
					+ "<div style=\"background-color: #f2f2f2;  width: 700px; border:none ;padding: 30px;margin: 25px;\">"
					+ " Hi "
					+ managerName
					+ ","
					+ " <br><br> I have taken compoff "
					+ " from <b> "
					+ from
					+ " to "
					+ to
					+ ",</b> <br>"
					+ " <b>Reason for applying: </b>"
					+ desc
					+ " ."
					+ "<p> Please grant my compoff</p><br>"
					+ "<a  style=\"background-color:red;color:white; padding: 15px 32px; text-decoration:none;text-align: center;font-size: 16px;cursor: pointer;\" href=\"http://"
					+ Constant.HOST
					+ "/LeaveManagementSystem/ApproveCompOff?key="
					+ key
					+ "\">Approve</a>"
					+ "<a  style=\"background-color:blue;color:white; padding: 15px 32px; text-decoration:none;text-align: center;font-size: 16px;cursor: pointer;\" href=\"http://"
					+ Constant.HOST
					+ "/LeaveManagementSystem/rejectcomoffbyemail?key="
					+ key
					+ "\">Reject</a><br><br>"
					+ "<p>Thanks and rgds</p>"
					+ name
					+ "<br> "
					+ designation
					+ "<br>"
					+ email
					+ "<br>"
					+ " +91 -"
					+ mobile
					+ "<br>"
					+ " Avekshaa Technologies (P) Ltd. | www.avekshaa.com<br>"
					+ " Business - IT Predictability and Assurance with Avekshaa<br>"
					+ " Deloitte Technology Fast 50 India | Technology Fast 500 APAC 2014<br>"
					+ " NASSCOM Emerge 50, 2013<br>"
					+ " Indian Express Top 3 Finalist, start-up<br>"
					+ "</div>"
					+ "</div>" + "</body>" + "</html>";
			message.setContent(text, "text/html");
			sess.setDebug(false);
			Transport.send(message);
			return "Mail has been sent sucessfully. Please check your Mail and Reset your Password";
		} catch (Exception e) {
			e.printStackTrace();
			return "Mail Delivery Failed";
		}
	}

	/** This method is for send email to Hr after applying Compoff by employee*/
	public String sendMailtoHrForCompoff(String mailId, String name,
			String from, String to, String discription, String deg, String mobile,
			String email) {
		if (mailId == "" || mailId == null) {
			return "Mail Delivery Failed";
		}
		try {
			Session sess = getSession();
			MimeMessage message = new MimeMessage(sess);
			InternetAddress address = new InternetAddress(mailId);
			message.setFrom(address);
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					mailId));
			message.setSubject("Applying for a compoff");
			String text = "<html>"
					+ "<body>"
					+ "<div style=\"padding-left: 2%; padding-top: 0%;\">"
					+ "<div style=\"background-color: #f2f2f2;  width: 700px; border:none ;padding: 30px;margin: 25px;\">"
					+ "To Hr Department,<br>"
					+ "I would like to infrom to applying comp off from <b>"
					+ from
					+ " to "
					+ to
					+ " </b>"
					+ "<br> because <b>"
					+ discription
					+ ". </b>"
					+ " <br>Thanks and rgds"
					+ "<p> "
					+ name
					+ "<br>"
					+ deg
					+ "<br> "
					+ email
					+ "<br> +91 -"
					+ mobile
					+ " Avekshaa Technologies (P) Ltd. | www.avekshaa.com<br>"
					+ " Business - IT Predictability and Assurance with Avekshaa<br>"
					+ " Deloitte Technology Fast 50 India | Technology Fast 500 APAC 2014<br>"
					+ " NASSCOM Emerge 50, 2013<br>"
					+ " Indian Express Top 3 Finalist, start-up<br>"
					+ "</div>"
					+ "</div>" + "</body>" + "</html>";
			message.setContent(text, "text/html");
			sess.setDebug(false);
			Transport.send(message);
			return "Mail has been sent sucessfully. Please check your Mail and Reset your Password";
		} catch (MessagingException e) {
			e.printStackTrace();
			return "Mail Delivery Failed";
		}
	}

	/** This method is for send email to employee for Registration by Hr*/
	public String sendMailtoEmployeeForRegistration(String mailId,
			String hrEmail, String userName, String uniqueKey) {
		if (mailId == "" || mailId == null) {
			return "Mail Delivery Failed";
		}
		String ename = "";
		String sql = "select emp_name from " + Constant.EMPLOYEE
				+ " where company_email='" + mailId + "'";
		try (Connection con = DataBaseUtil.getConnection();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(sql)) {

			if (rs.next()) {
				ename = rs.getString("emp_name");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Session sess = getSession();
			MimeMessage message = new MimeMessage(sess);
			InternetAddress address = new InternetAddress(mailId);
			message.setFrom(address);
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					mailId));
			message.setSubject("Employee Registration Form");
			ename = CommonUtiles.camelCase(ename);
			String text = "<html>"
					+ "<body>"
					+ "<div style=\"padding-left: 2%; padding-top: 0%;\">"
					+ "<div style=\"background-color: AntiqueWhite;  width: 700px; border:none ;padding: 30px;margin: 25px;\">"
					+ " Hi "
					+ ename
					+ ",<br>"
					+ " This is Registration link for you.<br>"
					+ " Please register here and complete your Registration.<br>"
					+ " Ensure you give proper details.<br><br><br>"
					+ "<a  style=\"background-color:red;color:white; padding: 15px 32px; text-decoration:none;text-align: center;font-size: 16px;cursor: pointer;\" href=\"http://"
					+ Constant.HOST
					+ "/LeaveManagementSystem/Registration?key="
					+ uniqueKey
					+ "\">click here</a>"
					+ "<br><br> Thanks & Regards,<br> "
					+ userName
					+ " <br>"
					+ "+91 -8660691367<br>"
					+ " From "
					+ hrEmail
					+ " <br>"
					+ " Avekshaa Technologies (P) Ltd. | www.avekshaa.com<br>"
					+ " Business - IT Predictability and Assurance with Avekshaa<br>"
					+ " Deloitte Technology Fast 50 India | Technology Fast 500 APAC 2014<br>"
					+ " NASSCOM Emerge 50, 2013<br>"
					+ " Indian Express Top 3 Finalist, start-up<br>"
					+ "</div>"
					+ "</div>" + "</body>" + "</html>	";
			message.setContent(text, "text/html");
			sess.setDebug(false);
			Transport.send(message);
			return "Mail has been sent sucessfully.";
		} catch (MessagingException e) {
			e.printStackTrace();
			return "Mail Delivery Failed";
		}
	}

	/** This method is for send email to employee after rejection compoff*/
	public String sendRejectionCompoffMailToEmp(String mailId, String dis,
			String name) {
		if (mailId == "" || mailId == null) {
			return "Mail Delivery Failed";
		}
		try {
			Session sess = getSession();
			MimeMessage message = new MimeMessage(sess);
			InternetAddress address = new InternetAddress(mailId);
			message.setFrom(address);
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					mailId));
			message.setSubject("CompOff Has been Rejected");
			String text = "<html>"
					+ "<body>"
					+ "<div style=\"padding-left: 2%; padding-top: 0%;\">"
					+ "<div style=\"background-color: AntiqueWhite;  width: 700px; border:none ;padding: 30px;margin: 25px;\">"

					+ "<p> hello! "
					+ name
					+ " </p>"
					+ "<p>your request for CompOff Has Been Rejected by the manager because of <b>"
					+ dis + "</b> </p>" + "<p></p><br>" + "</div>" + "</div>"
					+ "</body>" + "</html>";
			message.setContent(text, "text/html");
			sess.setDebug(false);
			Transport.send(message);
			return "Mail has been sent sucessfully.";
		} catch (MessagingException e) {
			e.printStackTrace();
			return "Mail Delivery Failed";
		}
	}

	/** This method is for send email to manager for Assign project*/
	public String sendMailToManagerForAssignProject(String mailId,
			String mangerName, String projectName, String projectId,
			String empNames[], String comment, String empName) {
		if (mailId == "" || mailId == null) {
			return "Mail Delivery Failed";
		}
		try {
			Session sess = getSession();
			MimeMessage message = new MimeMessage(sess);
			InternetAddress address = new InternetAddress(mailId);
			message.setFrom(address);
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					mailId));
			message.setSubject("Project Assignment");
			String text = "<html>"
					+ "<body>"
					+ "<div style=\"padding-left: 2%; padding-top: 0%;\">"
					+ "<div style=\"background-color: AntiqueWhite;  width: 700px; border:none ;padding: 30px;margin: 25px;\">"
					+ "<p> Hi ,"
					+ mangerName
					+ "</p>"
					+ "<p> <br> This is to inform you that you have been assigned with "
					+ projectName
					+ " ("
					+ projectId
					+ " )</p>"
					+ "<p> This is about the project (in short)<br>"
					+ comment
					+ " </p>"
					+ "<p> Thses guy working with you :"
					+ Arrays.toString(empNames)
					+ " </p>"
					+ " Thanks!<br>"
					+ " Yours faithfully"
					+ "<p>"
					+ empName
					+ " </p><br>"
					+ "</div>" + "</div>" + "</body>" + "</html>";
			message.setContent(text, "text/html");
			sess.setDebug(false);
			Transport.send(message);
			return "Mail has been sent sucessfully. Please check your Mail and Reset your Password";
		} catch (Exception e) {
			e.printStackTrace();
			return "Mail Delivery Failed";
		}
	}

	/** for send email to employee for Rejection leave by Manager*/
	public String sendRejectLeaveMail(String mailId, String s, String empName) {
		if (mailId == "" || mailId == null) {
			return "Mail Delivery Failed";
		}
		try {
			Session sess = getSession();
			MimeMessage message = new MimeMessage(sess);
			InternetAddress address = new InternetAddress(mailId);
			message.setFrom(address);
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					mailId));
			message.setSubject("Leave Has been Rejected");
			String text = "<html>"
					+ "<body>"
					+ "<div style=\"padding-left: 2%; padding-top: 0%;\">"
					+ "<div style=\"background-color: AntiqueWhite;  width: 700px; border:none ;padding: 30px;margin: 25px;\">"
					+ "<p> hello "
					+ empName
					+ " ,</p>"
					+ "<p>your request for Leave Has Been Rejected by the manager because of <b>"
					+ s + " </b> </p>" + "<p></p><br>" + "</div>" + "</div>"
					+ "</body>" + "</html>";
			message.setContent(text, "text/html");
			sess.setDebug(false);
			Transport.send(message);
			return "Mail has been sent sucessfully.";
		} catch (MessagingException e) {
			e.printStackTrace();
			return "Mail Delivery Failed";
		}
	}

	/** This method is for send email to employee for Compoff applying*/
	public String sendMailToEmployeeForCompoff(String mailId, String empName) {
		if (mailId == "" || mailId == null) {
			return "Mail Delivery Failed";
		}
		try {
			Session sess = getSession();
			MimeMessage message = new MimeMessage(sess);
			InternetAddress address = new InternetAddress(mailId);
			message.setFrom(address);
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					mailId));
			message.setSubject("Applying for Compoff");
			String text = "<html>"
					+ "<body>"
					+ "<div style=\"padding-left: 2%; padding-top: 0%;\">"
					+ "<div style=\"background-color: AntiqueWhite;  width: 700px; border:none ;padding: 30px;margin: 25px;\">"
					+ "<p> Hi "
					+ empName
					+ ",</p>"
					+ "<p> <bold> Your compoff request has been sent successfully to Manager.</p></br></br>"
					+ "</div>" + "</div>" + "</body>" + "</html>";
			message.setContent(text, "text/html");
			sess.setDebug(false);
			Transport.send(message);
			return "Mail has been sent sucessfully. Please check your Mail and Reset your Password";
		} catch (MessagingException e) {
			e.printStackTrace();
			return "Mail Delivery Failed";
		}
	}

	/** This method is for send email to employee for Applying leave*/
	public String sendMailtoemployeeForLeaveApply(String mailId, String name) {
		if (mailId == "" || mailId == null) {
			return "Mail Delivery Failed";
		}
		try {
			Session sess = getSession();
			MimeMessage message = new MimeMessage(sess);
			InternetAddress address = new InternetAddress(mailId);
			message.setFrom(address);
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					mailId));
			message.setSubject("Leave Apply");
			String text = "<html>"
					+ "<body>"
					+ "<div style=\"padding-left: 2%; padding-top: 0%;\">"
					+ "<div style=\"background-color: AntiqueWhite;  width: 700px; border:none ;padding: 30px;margin: 25px;\">"
					+ "<p> Hi "
					+ name
					+ ",</p>"
					+ "<p> <bold> Your Leave apply request has been sent Successfully to Manager </p></br></br>"
					+ "</div>" + "</div>" + "</body>" + "</html>";
			message.setContent(text, "text/html");
			sess.setDebug(false);
			Transport.send(message);
			return "Mail has been sent sucessfully. Please check your Mail and Reset your Password";
		} catch (MessagingException e) {
			e.printStackTrace();
			return "Mail Delivery Failed";
		}

	}

	// for send email to Hr after Applying leave by Employee
	public String sendMailtoHrForLeave(String mailId, String name,
			String from, String to, String dis, String deg, String mobile,
			String empemail) {
		if (mailId == "" || mailId == null) {
			return "Mail Delivery Failed";
		}
		String body = "";
		try {
			long militoday = new java.util.Date().getTime();
			long milifrom = new SimpleDateFormat("E MMM dd yyyy").parse(from).getTime();
			long milito = new SimpleDateFormat("E MMM dd yyyy").parse(to).getTime();
			if (militoday <= milifrom) {

				if (milifrom == milito) {
					body = "I want to apply for planned leave on <b>" + from
							+ "</b>";
				} else {
					body = "I want to apply for planned leave from <b>" + from
							+ "</b> to <b>" + to + "</b>";
				}
			} else {
				if (milifrom == milito) {
					body = "I have taken leave on <b>" + from + "<b>";
				} else {
					body = "I have taken leave from <b>" + from + "</b> to <b>"
							+ to + "<b>";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Session sess = getSession();
			MimeMessage message = new MimeMessage(sess);
			InternetAddress address = new InternetAddress(mailId);
			message.setFrom(address);
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					mailId));
			message.setSubject("Applying for a leave");
			String text = "<html>"
					+ "<body>"
					+ "<div style=\"padding-left: 2%; padding-top: 0%;\">"
					+ "<div style=\"background-color: AntiqueWhite;  width: 700px; border:none ;padding: 30px;margin: 25px;\">"
					+ " To Hr Department,<br><br>"
					+ body
					+ " <br><b>Reason for applying leave: </b>"
					+ dis
					+ ".<br>"
					+ "Please give approval.<br><br>"
					+ " Thanks and rgds"
					+ "<br> "
					+ name
					+ "<br>"
					+ deg
					+ "<br>"
					+ empemail
					+ "<br>"
					+ " +91 -"
					+ mobile
					+ "<br><br>"
					+ " Avekshaa Technologies (P) Ltd. | www.avekshaa.com<br>"
					+ " Business - IT Predictability and Assurance with Avekshaa<br>"
					+ " Deloitte Technology Fast 50 India | Technology Fast 500 APAC 2014<br>"
					+ " NASSCOM Emerge 50, 2013<br>"
					+ " Indian Express Top 3 Finalist, start-up<br>"
					+ "</div>"
					+ "</div>" + "</body>" + "</html>";
			message.setContent(text, "text/html");
			sess.setDebug(false);
			Transport.send(message);
			return "Mail has been sent sucessfully. Please check your Mail and Reset your Password";
		} catch (MessagingException e) {
			e.printStackTrace();
			return "Mail Delivery Failed";
		}
	}

	/**This method is for send email to employee after approved leave*/
	public String sendApprovalMailToEmp(String mailId, String name) {
		if (mailId == "" || mailId == null) {
			return "Mail Delivery Failed";
		}
		try {
			Session sess = getSession();
			MimeMessage message = new MimeMessage(sess);
			InternetAddress address = new InternetAddress(mailId);
			message.setFrom(address);
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					mailId));
			message.setSubject("Leave Granted");
			String text = "<html>"
					+ "<body>"
					+ "<div style=\"padding-left: 2%; padding-top: 0%;\">"
					+ "<div style=\"background-color: AntiqueWhite;  width: 700px; border:none ;padding: 30px;margin: 25px;\">"
					+ "<p>"
					+ name
					+ " your approval for Leave Has Been granted by manager You can go for leave  </br>"
					+ "</p>" + "<p></p><br>" + "</div>" + "</div>" + "</body>"
					+ "</html>";
			message.setContent(text, "text/html");
			sess.setDebug(false);
			Transport.send(message);
			return "Mail has been sent sucessfully.";
		} catch (MessagingException e) {
			e.printStackTrace();
			return "Mail Delivery Failed";
		}
	}
}
