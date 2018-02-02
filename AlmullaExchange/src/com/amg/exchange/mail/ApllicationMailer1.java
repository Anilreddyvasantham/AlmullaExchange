package com.amg.exchange.mail;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.URLName;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.registration.service.ICompanyMasterservice;
import com.amg.exchange.treasury.deal.supplier.model.ApplicationSetup;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.GMailAuthenticator;
import com.amg.exchange.util.SessionStateManage;

@SuppressWarnings("serial")
@Service("mailService1")
public class ApllicationMailer1 implements Serializable {


	private SessionStateManage session = new SessionStateManage();

	@Autowired(required = true)
	private JavaMailSender mailSender1;
	@Autowired
	ICompanyMasterservice companyMaster;

	public JavaMailSender getMailSender1() {
		return mailSender1;
	}

	public void setMailSender1(JavaMailSender mailSender1) {
		this.mailSender1 = mailSender1;
	}



	//Nagarjuna START mailid token sending	



	//@Autowired	
	//ForeignCurrencyPurchaseBean fc;
	public void sendTokenMail1(String to,String from, String subject, String customerId, String tokenNumber ,String sender,String branchId,String location1,String telephoneno,String employeename,String sendermail) {


		try{
			final MimeMessage mimeMessage = mailSender1.createMimeMessage();
			final MimeMessageHelper message = new MimeMessageHelper(mimeMessage);


			message.setTo(to);
			message.setFrom(from);
			message.setSubject(subject);


			message.setText((getTokenGenerationContent().replace("${0}", customerId)).replace("${1}", tokenNumber).replace("${2}", sender).replace("${3}",branchId).replace("${4}", location1).replace( "${5}", telephoneno).replace("${6}", employeename).replace( "${7}", sendermail), true);
			mailSender1.send(mimeMessage);
		}catch(Exception e){
			System.out.println("Mail not sent to user :: "+e);
		}
	}

	//NAGARJUNA ENDING MAIL


	private String getTokenGenerationContent(){

		StringBuffer content = new StringBuffer();

		content.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">")
		.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">")
		.append("<head>")
		.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />")
		.append("<title>Al Mulla Exchange</title>")
		.append("</head>")
		.append("<body bgcolor=\"#ededed\" style=\"padding:0; margin:0; text-align:center;\">")
		.append("<table width=\"700\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
		.append("  <tr>")
		.append("    <td align=\"left\" valign=\"middle\" bgcolor=\"#00a53d\"><img src=\"https://applications2.almullagroup.com/onlineremit/faces/images/prettyLogo.png\" width=\"264\" height=\"89\" /></td>")
		.append("  </tr>")
		.append("  <tr>")
		.append("    <td align=\"center\" valign=\"middle\" bgcolor=\"#FFFFFF\" style=\"padding:15px;\"><table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
		.append("  <tr>")
		.append("    <td align=\"center\" valign=\"middle\" style=\"border:1px solid #dcdcdc; padding:2px;\"><table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
		.append("      <tr>")
		.append("        <td align=\"center\" valign=\"middle\" style=\"font-family:Arial, Helvetica, sans-serif; font-size:24px; color:#FFF; border-bottom:2px solid #f18300; background-color:#00a53d; padding:5px;\">Al Mulla Exchange</td>")
		.append("      </tr>")
		.append("      <tr>")
		.append("        <td align=\"left\" valign=\"middle\" style=\"font-family:Arial, Helvetica, sans-serif; font-size:15px; line-height:20px; color:#666; padding:30px;\">Dear <strong style=\"color:#000000;\">${6}</strong> ,<br/><br/>")
		.append("")
		.append("")
		.append("Greetings from Al Mulla Exchange!<br/><br/>")
		.append("")
		.append("Please received this email because my transaction limit is exceeding.<br/><br/>")
		.append("")
		.append("<strong style=\"color:#333;\">Your customer id No:</strong> <strong style=\"color:#257b45;\">${0}</strong><br/><br/>")
		.append("<strong style=\"color:#333;\">Your Token No.</strong> <strong style=\"color:#257b45;\">${1}</strong><br/><br/>")
		.append("<strong style=\"color:#333;\">sender:</strong> <strong style=\"color:#257b45;\">${2}</strong><br/>")
		.append("<strong style=\"color:#333;\">sendermail:</strong> <strong style=\"color:#257b45;\">${7}</strong><br/>")
		.append("<strong style=\"color:#333;\">BranchId:</strong> <strong style=\"color:#257b45;\">${3}</strong><br/>")
		.append("<strong style=\"color:#333;\">location:</strong> <strong style=\"color:#257b45;\">${4}</strong><br/>")
		.append("<strong style=\"color:#333;\">telephoneno:</strong> <strong style=\"color:#257b45;\">${5}</strong><br/>")
		.append("")
		.append("Please <a href=\"http://www.almullagroup.com/contact.aspx\" target=\"_blank\" style=\"outline:none; color:#f18300; font-weight:bold; text-decoration:none;\">contact us</a> should you have any questions or need further assistance.<br/><br/>")
		.append("")
		.append("Thanks for register with us!</td>")
		.append("      </tr>")
		.append("    </table></td>")
		.append("  </tr>")
		.append("</table>")
		.append("</td>")
		.append("  </tr>")
		.append("  <tr>")
		.append("    <td align=\"center\" valign=\"middle\" bgcolor=\"#d7d7d7\" style=\" border-top:2px solid #018a34; font-family:Arial, Helvetica, sans-serif; font-size:14px;  color:#000; padding:10px;\"><a href=\"http://www.almullagroup.com/finance/exchange.aspx\" target=\"_blank\" style=\"outline:none; color:#000000; text-decoration:none;\">www.almullaexchange.com</a></td>")
		.append("  </tr>")
		.append("</table>")
		.append("</body>")
		.append("</html>");

		return content.toString();
	}

	public void sendTokenMail2(String to,String from, String subject, String customerId, String tokenNumber ,String sender,String branchId,String location1,String telephoneno,String employeename,String sendermail) {


		try{
			final MimeMessage mimeMessage = mailSender1.createMimeMessage();
			final MimeMessageHelper message = new MimeMessageHelper(mimeMessage);


			message.setTo(to);
			message.setFrom(from);
			message.setSubject(subject);


			message.setText((getTokenGenerationContent1().replace("${0}", customerId)).replace("${1}", tokenNumber).replace("${2}", sender).replace("${3}",branchId).replace("${4}", location1).replace( "${5}", telephoneno).replace("${6}", employeename).replace( "${7}", sendermail), true);
			mailSender1.send(mimeMessage);
		}catch(Exception e){
			System.out.println("Mail not sent to user :: "+e);
		}
	}

	//NAGARJUNA ENDING MAIL


	private String getTokenGenerationContent1(){

		StringBuffer content = new StringBuffer();

		content.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">")
		.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">")
		.append("<head>")
		.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />")
		.append("<title>Al Mulla Exchange</title>")
		.append("</head>")
		.append("<body bgcolor=\"#ededed\" style=\"padding:0; margin:0; text-align:center;\">")
		.append("<table width=\"700\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
		.append("  <tr>")
		.append("    <td align=\"left\" valign=\"middle\" bgcolor=\"#00a53d\"><img src=\"https://applications2.almullagroup.com/onlineremit/faces/images/prettyLogo.png\" width=\"264\" height=\"89\" /></td>")
		.append("  </tr>")
		.append("  <tr>")
		.append("    <td align=\"center\" valign=\"middle\" bgcolor=\"#FFFFFF\" style=\"padding:15px;\"><table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
		.append("  <tr>")
		.append("    <td align=\"center\" valign=\"middle\" style=\"border:1px solid #dcdcdc; padding:2px;\"><table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
		.append("      <tr>")
		.append("        <td align=\"center\" valign=\"middle\" style=\"font-family:Arial, Helvetica, sans-serif; font-size:24px; color:#FFF; border-bottom:2px solid #f18300; background-color:#00a53d; padding:5px;\">Al Mulla Exchange</td>")
		.append("      </tr>")
		.append("      <tr>")
		.append("        <td align=\"left\" valign=\"middle\" style=\"font-family:Arial, Helvetica, sans-serif; font-size:15px; line-height:20px; color:#666; padding:30px;\">Dear <strong style=\"color:#000000;\">${6}</strong> ,<br/><br/>")
		.append("")
		.append("")
		.append("Greetings from Al Mulla Exchange!<br/><br/>")
		.append("")
		.append("Please received this email because Customer updated  his details.<br/><br/>")
		.append("")
		.append("<strong style=\"color:#333;\">Your customer id No:</strong> <strong style=\"color:#257b45;\">${0}</strong><br/><br/>")
		.append("<strong style=\"color:#333;\">Your Token No.</strong> <strong style=\"color:#257b45;\">${1}</strong><br/><br/>")
		.append("<strong style=\"color:#333;\">sender:</strong> <strong style=\"color:#257b45;\">${2}</strong><br/>")
		.append("<strong style=\"color:#333;\">sendermail:</strong> <strong style=\"color:#257b45;\">${7}</strong><br/>")
		.append("<strong style=\"color:#333;\">BranchId:</strong> <strong style=\"color:#257b45;\">${3}</strong><br/>")
		.append("<strong style=\"color:#333;\">location:</strong> <strong style=\"color:#257b45;\">${4}</strong><br/>")
		.append("<strong style=\"color:#333;\">telephoneno:</strong> <strong style=\"color:#257b45;\">${5}</strong><br/>")
		.append("")
		.append("Please <a href=\"http://www.almullagroup.com/contact.aspx\" target=\"_blank\" style=\"outline:none; color:#f18300; font-weight:bold; text-decoration:none;\">contact us</a> should you have any questions or need further assistance.<br/><br/>")
		.append("")
		.append("Thanks for register with us!</td>")
		.append("      </tr>")
		.append("    </table></td>")
		.append("  </tr>")
		.append("</table>")
		.append("</td>")
		.append("  </tr>")
		.append("  <tr>")
		.append("    <td align=\"center\" valign=\"middle\" bgcolor=\"#d7d7d7\" style=\" border-top:2px solid #018a34; font-family:Arial, Helvetica, sans-serif; font-size:14px;  color:#000; padding:10px;\"><a href=\"http://www.almullagroup.com/finance/exchange.aspx\" target=\"_blank\" style=\"outline:none; color:#000000; text-decoration:none;\">www.almullaexchange.com</a></td>")
		.append("  </tr>")
		.append("</table>")
		.append("</body>")
		.append("</html>");

		return content.toString();
	}

	//added by Nazish
	public void sendTokenMailfromCustomer(String to,String from, String subject, String customerId, String tokenNumber ,String sender,String branchId,String location1,String telephoneno,String employeename,String sendermail) {


		try{
			final MimeMessage mimeMessage = mailSender1.createMimeMessage();
			final MimeMessageHelper message = new MimeMessageHelper(mimeMessage);


			message.setTo(to);
			message.setFrom(from);
			message.setSubject(subject);


			message.setText((getTokenGenerationContent2().replace("${0}", customerId)).replace("${1}", tokenNumber).replace("${2}", sender).replace("${3}",branchId).replace("${4}", location1).replace( "${5}", telephoneno).replace("${6}", employeename).replace( "${7}", sendermail), true);
			mailSender1.send(mimeMessage);
		}catch(Exception e){
			System.out.println("Mail not sent to user :: "+e);
		}
	}

	//NAGARJUNA ENDING MAIL


	private String getTokenGenerationContent2(){

		StringBuffer content = new StringBuffer();

		content.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">")
		.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">")
		.append("<head>")
		.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />")
		.append("<title>Al Mulla Exchange</title>")
		.append("</head>")
		.append("<body bgcolor=\"#ededed\" style=\"padding:0; margin:0; text-align:center;\">")
		.append("<table width=\"700\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
		.append("  <tr>")
		.append("    <td align=\"left\" valign=\"middle\" bgcolor=\"#00a53d\"><img src=\"https://applications2.almullagroup.com/onlineremit/faces/images/prettyLogo.png\" width=\"264\" height=\"89\" /></td>")
		.append("  </tr>")
		.append("  <tr>")
		.append("    <td align=\"center\" valign=\"middle\" bgcolor=\"#FFFFFF\" style=\"padding:15px;\"><table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
		.append("  <tr>")
		.append("    <td align=\"center\" valign=\"middle\" style=\"border:1px solid #dcdcdc; padding:2px;\"><table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
		.append("      <tr>")
		.append("        <td align=\"center\" valign=\"middle\" style=\"font-family:Arial, Helvetica, sans-serif; font-size:24px; color:#FFF; border-bottom:2px solid #f18300; background-color:#00a53d; padding:5px;\">Al Mulla Exchange</td>")
		.append("      </tr>")
		.append("      <tr>")
		.append("        <td align=\"left\" valign=\"middle\" style=\"font-family:Arial, Helvetica, sans-serif; font-size:15px; line-height:20px; color:#666; padding:30px;\">Dear <strong style=\"color:#000000;\">${6}</strong> ,<br/><br/>")
		.append("")
		.append("")
		.append("Greetings from Al Mulla Exchange!<br/><br/>")
		.append("")
		.append("Please received this email because Customer updated  his details.<br/><br/>")
		.append("")
		.append("<strong style=\"color:#333;\">Your customer id No:</strong> <strong style=\"color:#257b45;\">${0}</strong><br/><br/>")
		.append("<strong style=\"color:#333;\">Your Token No.</strong> <strong style=\"color:#257b45;\">${1}</strong><br/><br/>")
		.append("<strong style=\"color:#333;\">sender:</strong> <strong style=\"color:#257b45;\">${2}</strong><br/>")
		.append("<strong style=\"color:#333;\">sendermail:</strong> <strong style=\"color:#257b45;\">${7}</strong><br/>")
		.append("<strong style=\"color:#333;\">BranchId:</strong> <strong style=\"color:#257b45;\">${3}</strong><br/>")
		.append("<strong style=\"color:#333;\">location:</strong> <strong style=\"color:#257b45;\">${4}</strong><br/>")
		.append("<strong style=\"color:#333;\">telephoneno:</strong> <strong style=\"color:#257b45;\">${5}</strong><br/>")
		.append("")
		.append("Please <a href=\"http://www.almullagroup.com/contact.aspx\" target=\"_blank\" style=\"outline:none; color:#f18300; font-weight:bold; text-decoration:none;\">contact us</a> should you have any questions or need further assistance.<br/><br/>")
		.append("")
		.append("Thanks for register with us!</td>")
		.append("      </tr>")
		.append("    </table></td>")
		.append("  </tr>")
		.append("</table>")
		.append("</td>")
		.append("  </tr>")
		.append("  <tr>")
		.append("    <td align=\"center\" valign=\"middle\" bgcolor=\"#d7d7d7\" style=\" border-top:2px solid #018a34; font-family:Arial, Helvetica, sans-serif; font-size:14px;  color:#000; padding:10px;\"><a href=\"http://www.almullagroup.com/finance/exchange.aspx\" target=\"_blank\" style=\"outline:none; color:#000000; text-decoration:none;\">www.almullaexchange.com</a></td>")
		.append("  </tr>")
		.append("</table>")
		.append("</body>")
		.append("</html>");

		return content.toString();
	}

	public void sendMail(String to, String subject, String  verficationToken ) {

		try{
			final MimeMessage mimeMessage = mailSender1.createMimeMessage();
			final MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
			message.setTo(to);
			message.setSubject(subject);

			message.setText((getCustTokenGenerationContent().replace("${0}", verficationToken)),true);

			mailSender1.send(mimeMessage);
		}catch(Exception e){
			System.out.println("Mail not sent to user :: "+e);
		}
	}

	private String getCustTokenGenerationContent(){
		StringBuffer content = new StringBuffer();

		content.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">")
		.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">")
		.append("<head>")
		.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />")
		.append("<title>Al Mulla Exchange</title>")
		.append("</head>")
		.append("<body bgcolor=\"#ededed\" style=\"padding:0; margin:0; text-align:center;\">")
		.append("<table width=\"700\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
		.append("  <tr>")
		.append("    <td align=\"left\" valign=\"middle\" bgcolor=\"#00a53d\"><img src=\"https://applications2.almullagroup.com/onlineremit/faces/images/prettyLogo.png\" width=\"264\" height=\"89\" /></td>")
		.append("  </tr>")
		.append("  <tr>")
		.append("    <td align=\"center\" valign=\"middle\" bgcolor=\"#FFFFFF\" style=\"padding:15px;\"><table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
		.append("  <tr>")
		.append("    <td align=\"center\" valign=\"middle\" style=\"border:1px solid #dcdcdc; padding:2px;\"><table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
		.append("      <tr>")
		.append("        <td align=\"center\" valign=\"middle\" style=\"font-family:Arial, Helvetica, sans-serif; font-size:24px; color:#FFF; border-bottom:2px solid #f18300; background-color:#00a53d; padding:5px;\">Al Mulla Exchange</td>")
		.append("      </tr>")
		.append("      <tr>")
		.append("        <td align=\"left\" valign=\"middle\" style=\"font-family:Arial, Helvetica, sans-serif; font-size:15px; line-height:20px; color:#666; padding:30px;\">Dear <strong style=\"color:#000000;\">Sir</strong> ,<br/><br/>")
		.append("")
		.append("")
		.append("Greetings from Al Mulla Exchange!<br/><br/>")
		.append("")
		.append("Please received this email use the Token below<br/><br/>")
		.append("")
		.append("<strong style=\"color:#333;\">Token No.</strong> <strong style=\"color:#257b45;\">${0}</strong><br/><br/>")
		.append("")
		.append("Please <a href=\"http://www.almullagroup.com/contact.aspx\" target=\"_blank\" style=\"outline:none; color:#f18300; font-weight:bold; text-decoration:none;\">contact us</a> should you have any questions or need further assistance.<br/><br/>")
		.append("")
		.append("Thanks for register with us!</td>")
		.append("      </tr>")
		.append("    </table></td>")
		.append("  </tr>")
		.append("</table>")
		.append("</td>")
		.append("  </tr>")
		.append("  <tr>")
		.append("    <td align=\"center\" valign=\"middle\" bgcolor=\"#d7d7d7\" style=\" border-top:2px solid #018a34; font-family:Arial, Helvetica, sans-serif; font-size:14px;  color:#000; padding:10px;\"><a href=\"http://www.almullagroup.com/finance/exchange.aspx\" target=\"_blank\" style=\"outline:none; color:#000000; text-decoration:none;\">www.almullaexchange.com</a></td>")
		.append("  </tr>")
		.append("</table>")
		.append("</body>")
		.append("</html>");

		return content.toString();


	}


	public void sendMailToCustomerWithAttachment(List<ApplicationSetup> lstApplicationSetup,String to ,String subject,byte[] reportByteArray,String customerName) throws AddressException, MessagingException{


		ApplicationSetup applicationSetup =lstApplicationSetup.get(0);

		String host = applicationSetup.getEmailHost();//"homail.amgdom.com"; 
		final String user =applicationSetup.getEmailUserName();// "excorpmark7"; 
		final String pass = applicationSetup.getEmailPassword();//"7kr1213EX"; 
		String from=applicationSetup.getEmailId();
		BigDecimal port=applicationSetup.getEmailPortNo();

		Properties props= new Properties();

		props.put("mail.smtp.host", host); // Setup mail server
		props.setProperty("mail.smtp.auth","true");
		//props.put("mail.smtp.port", port);

		Session session = Session.getInstance(props,new GMailAuthenticator(user, pass));
		session.setPasswordAuthentication(new URLName("smtp",host,port.intValue(),"INBOX",user,pass), new PasswordAuthentication(user,pass));

		InternetAddress arReplTo[] = new InternetAddress[1];

		Message message = new MimeMessage(session); // Define message
		// Fill its headers
		message.setSubject(subject);
		message.setFrom(new InternetAddress("Al Mulla Exchange Online <"+from+">"));
		arReplTo[0] = new InternetAddress(from);
		message.setReplyTo(arReplTo);

		InternetAddress[] address = {new InternetAddress(to)};
		message.setRecipients(Message.RecipientType.TO, address);

		StringBuffer text =new StringBuffer();

		List<CompanyMaster> lsCompany =  companyMaster.viewMasterRecords();
		String phoneNumber = null;
		if(lsCompany != null){
			CompanyMaster company = lsCompany.get(0);
			phoneNumber = company.getTelephoneNo(); 
		}

		text.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">")
		.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">")
		.append("<head>")
		.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />")
		.append("<title>Al Mulla Exchange</title>")
		.append("</head>")
		.append("<body bgcolor=\"#ededed\" style=\"padding:0; margin:0; text-align:center;\">")
		.append("<table width=\"700\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
		.append("  <tr>")
		.append("    <td align=\"center\" valign=\"middle\" bgcolor=\"#FFFFFF\" style=\"padding:15px;\"><table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
		.append("  <tr>")
		.append("    <td align=\"center\" valign=\"middle\" style=\"border:1px solid #dcdcdc; padding:2px;\"><table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
		.append("      <tr>")
		.append("        <td align=\"center\" valign=\"middle\" style=\"font-family:Arial, Helvetica, sans-serif; font-size:24px; color:#FFF; border-bottom:2px solid #f18300; background-color:#00a53d; padding:5px;\">Al Mulla Exchange</td>")
		.append("      </tr>")
		.append("      <tr>")
		.append("        <td align=\"left\" valign=\"middle\" style=\"font-family:Arial, Helvetica, sans-serif; font-size:15px; line-height:20px; color:#666; padding:30px;\">Dear ").append(customerName).append(",<br/><br/>")
		.append("")
		.append("")
		.append("Please find the attached Remittance receipt.<br><br/>")
		.append("")
		.append("")
		.append("Thank you for visiting us.<br><br/>")
		.append("")
		.append("<strong style=\"color:#000000;\">").append("Al Mulla Exchange").append("</strong>")
		.append(",<br><br/>");
		if(phoneNumber != null){
			text.append("Contact us :")
			.append("")
			.append(phoneNumber)
			.append("<br><br/>");
		}
		text.append("")
		.append("<strong style=\"color:Green;\">Go Green.. Switch to eReceipts</strong><br/><br/>")
		.append("")
		.append("</td>")
		.append("      </tr>")
		.append("    </table></td>")
		.append("  </tr>")
		.append("</table>")
		.append("</td>")
		.append("  </tr>")
		.append("  <tr>")
		.append("    <td align=\"center\" valign=\"middle\" bgcolor=\"#d7d7d7\" style=\" border-top:2px solid #018a34; font-family:Arial, Helvetica, sans-serif; font-size:14px;  color:#000; padding:10px;\"><a href=\"http://www.almullagroup.com/finance/exchange.aspx\" target=\"_blank\" style=\"outline:none; color:#000000; text-decoration:none;\">www.almullaexchange.com</a></td>")
		.append("  </tr>").append("</table>").append("</body>").append("</html>");

		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(text.toString(), "text/html");

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);


		MimeBodyPart mbp2 = new MimeBodyPart();
		mbp2.setFileName("RemittanceReceiptReport.pdf");

		mbp2.setDataHandler(new DataHandler(new ByteArrayDataSource(reportByteArray,"application/pdf")));

		multipart.addBodyPart(mbp2);

		message.setContent(multipart);

		Transport.send(message);

	}

	public String generateContentForReport(String customerName){StringBuffer content = new StringBuffer();

	content.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">")
	.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">")
	.append("<head>")
	.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />")
	.append("<title>Al Mulla Exchange</title>")
	.append("</head>")
	.append("<body bgcolor=\"#ededed\" style=\"padding:0; margin:0; text-align:center;\">")
	.append("<table width=\"700\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
	.append("  <tr>")
	.append("    <td align=\"left\" valign=\"middle\" bgcolor=\"#00a53d\"><img src=\"https://applications2.almullagroup.com/onlineremit/faces/images/prettyLogo.png\" width=\"264\" height=\"89\" /></td>")
	.append("  </tr>")
	.append("  <tr>")
	.append("    <td align=\"center\" valign=\"middle\" bgcolor=\"#FFFFFF\" style=\"padding:15px;\"><table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
	.append("  <tr>")
	.append("    <td align=\"center\" valign=\"middle\" style=\"border:1px solid #dcdcdc; padding:2px;\"><table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
	.append("      <tr>")
	.append("        <td align=\"center\" valign=\"middle\" style=\"font-family:Arial, Helvetica, sans-serif; font-size:24px; color:#FFF; border-bottom:2px solid #f18300; background-color:#00a53d; padding:5px;\">Al Mulla Exchange</td>")
	.append("      </tr>")
	.append("      <tr>")
	.append("")
	.append("Greetings from Al Mulla Exchange!<br/><br/>")
	.append("")
	.append("Please received this email because Customer updated  his details.<br/><br/>")
	.append("")
	.append("Please <a href=\"http://www.almullagroup.com/contact.aspx\" target=\"_blank\" style=\"outline:none; color:#f18300; font-weight:bold; text-decoration:none;\">contact us</a> should you have any questions or need further assistance.<br/><br/>")
	.append("")
	.append("Thanks for register with us!</td>")
	.append("      </tr>")
	.append("    </table></td>")
	.append("  </tr>")
	.append("</table>")
	.append("</td>")
	.append("  </tr>")
	.append("  <tr>")
	.append("    <td align=\"center\" valign=\"middle\" bgcolor=\"#d7d7d7\" style=\" border-top:2px solid #018a34; font-family:Arial, Helvetica, sans-serif; font-size:14px;  color:#000; padding:10px;\"><a href=\"http://www.almullagroup.com/finance/exchange.aspx\" target=\"_blank\" style=\"outline:none; color:#000000; text-decoration:none;\">www.almullaexchange.com</a></td>")
	.append("  </tr>")
	.append("</table>")
	.append("</body>")
	.append("</html>");

	return content.toString();


	}


	public void sendMailToCustomer(String to,String from,String subject){

		try{
			final MimeMessage mimeMessage = mailSender1.createMimeMessage();
			final MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
			message.setTo(to);
			message.setSubject(subject);

			message.setText(getCustTokenGenerationContent());

			mailSender1.send(mimeMessage);
		}catch(Exception e){
			System.out.println("Mail not sent to user :: "+e);
		}
	}

	private String getPersonelRemittanceCustTokenGenerationContent(){
		StringBuffer content = new StringBuffer();

		content.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">")
		.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">")
		.append("<head>")
		.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />")
		.append("<title>Al Mulla Exchange</title>")
		.append("</head>")
		.append("<body bgcolor=\"#ededed\" style=\"padding:0; margin:0; text-align:center;\">")
		.append("<table width=\"700\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
		.append("  <tr>")
		.append("    <td align=\"left\" valign=\"middle\" bgcolor=\"#00a53d\"><img src=\"https://applications2.almullagroup.com/onlineremit/faces/images/prettyLogo.png\" width=\"264\" height=\"89\" /></td>")
		.append("  </tr>")
		.append("  <tr>")
		.append("    <td align=\"center\" valign=\"middle\" bgcolor=\"#FFFFFF\" style=\"padding:15px;\"><table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
		.append("  <tr>")
		.append("    <td align=\"center\" valign=\"middle\" style=\"border:1px solid #dcdcdc; padding:2px;\"><table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
		.append("      <tr>")
		.append("        <td align=\"center\" valign=\"middle\" style=\"font-family:Arial, Helvetica, sans-serif; font-size:24px; color:#FFF; border-bottom:2px solid #f18300; background-color:#00a53d; padding:5px;\">Al Mulla Exchange</td>")
		.append("      </tr>")
		.append("      <tr>")
		.append("        <td align=\"left\" valign=\"middle\" style=\"font-family:Arial, Helvetica, sans-serif; font-size:15px; line-height:20px; color:#666; padding:30px;\">Dear <strong style=\"color:#000000;\">Sir</strong> ,<br/><br/>")
		.append("")
		.append("")
		.append("Greetings from Al Mulla Exchange!<br/><br/>")
		.append("")
		.append("Please received this email use the Token below<br/><br/>")
		.append("")
		.append("<strong style=\"color:#333;\">Token No.</strong> <strong style=\"color:#257b45;\">${0}</strong><br/><br/>")
		.append("")
		.append("Please <a href=\"http://www.almullagroup.com/contact.aspx\" target=\"_blank\" style=\"outline:none; color:#f18300; font-weight:bold; text-decoration:none;\">contact us</a> should you have any questions or need further assistance.<br/><br/>")
		.append("")
		.append("Thanks for register with us!</td>")
		.append("      </tr>")
		.append("    </table></td>")
		.append("  </tr>")
		.append("</table>")
		.append("</td>")
		.append("  </tr>")
		.append("  <tr>")
		.append("    <td align=\"center\" valign=\"middle\" bgcolor=\"#d7d7d7\" style=\" border-top:2px solid #018a34; font-family:Arial, Helvetica, sans-serif; font-size:14px;  color:#000; padding:10px;\"><a href=\"http://www.almullagroup.com/finance/exchange.aspx\" target=\"_blank\" style=\"outline:none; color:#000000; text-decoration:none;\">www.almullaexchange.com</a></td>")
		.append("  </tr>")
		.append("</table>")
		.append("</body>")
		.append("</html>");

		return content.toString();

	}

	public void sendMailToGSMForPlaceOrder(List<ApplicationSetup> lstApplicationSetup,HashMap<String, String> inputValues) throws AddressException, MessagingException{

		ApplicationSetup applicationSetup =lstApplicationSetup.get(0);

		String host = applicationSetup.getEmailHost();//"homail.amgdom.com"; 
		final String user = applicationSetup.getEmailUserName();// "excorpmark7"; 
		final String pass = applicationSetup.getEmailPassword();//"7kr1213EX"; 
		String from = applicationSetup.getEmailId();
		BigDecimal port = applicationSetup.getEmailPortNo();

		String mailContent = getRatePleceOrderMailContentToGSM(inputValues);

		//testing
		/*String email1 = "chiranjeevi.veeravalli@almullagroup.com";
		String email2 = "chiranjeevi510@gmail.com";*/

		//testing
		/*String email1 = "kanmani.subramanian@almullagroup.com";
		String email2 = "azaharulla.khan@almullagroup.com";*/

		String email1 = "philip.john@almullagroup.com";
		String email2 = "newton.joseph@almullagroup.com";
		String email3 = "fathima.amanullah@almullagroup.com";


		String RateOffered="Place Order Information";

		Properties props= new Properties();

		props.put("mail.smtp.host", host); // Setup mail server
		props.setProperty("mail.smtp.auth","true");

		Session session = Session.getInstance(props,new GMailAuthenticator(user, pass));
		session.setPasswordAuthentication(new URLName("smtp",host,port.intValue(),"INBOX",user,pass), new PasswordAuthentication(user,pass));

		InternetAddress arReplTo[] = new InternetAddress[1];

		Message message = new MimeMessage(session); // Define message
		// Fill its headers
		message.setSubject(RateOffered);
		message.setFrom(new InternetAddress("Al Mulla Exchange <"+from+">"));
		arReplTo[0] = new InternetAddress(from);
		message.setReplyTo(arReplTo);

		InternetAddress[] address = {new InternetAddress(email1),new InternetAddress(email2),new InternetAddress(email3)};
		message.setRecipients(Message.RecipientType.TO, address);

		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(mailContent, "text/html");

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);


		message.setContent(multipart);

		Transport.send(message);

	}


	private String getRatePleceOrderMailContentToGSM(HashMap<String, String> inputValues) {
		String Name=inputValues.get("Name");
		String beneName=inputValues.get("beneName");
		String bank=inputValues.get("Bank");
		String branchName=inputValues.get("Branch Name");
		String amount=inputValues.get("Amount");
		String createdBy=inputValues.get("Craeted By");
		String createdDate=inputValues.get("Created Date");
		String companyName = null;
		if(session.getCountryAlphaTwoCode() != null && session.getCountryAlphaTwoCode().equalsIgnoreCase(Constants.KUWAIT_ALPHA_TWO_CODE)){
			companyName = Constants.COMPANY_NAME_KW;
		}else if(session.getCountryAlphaTwoCode() != null && session.getCountryAlphaTwoCode().equalsIgnoreCase(Constants.OMAN_ALPHA_TWO_CODE)){
			companyName = Constants.COMPANY_NAME_OM;
		}else if(session.getCountryAlphaTwoCode() != null && session.getCountryAlphaTwoCode().equalsIgnoreCase(Constants.BAHRAIN_ALPHA_TWO_CODE)){
			companyName = Constants.COMPANY_NAME_BH;
		}

		StringBuffer content = new StringBuffer();


		content.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">")
		.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">")
		.append("<head>")
		.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />")
		.append("<title>Al Mulla Exchange</title>")
		.append("</head>")
		.append("<body bgcolor=\"#ededed\" style=\"padding:0; margin:0; text-align:center;\">")
		.append("<table width=\"700\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
		/*.append("  <tr>")
							.append("    <td align=\"left\" valign=\"middle\" bgcolor=\"#00a53d\"><img src=\"https://applications2.almullagroup.com/onlineremit/faces/images/prettyLogo.png\" width=\"264\" height=\"89\" /></td>")
							.append("  </tr>")*/
		.append("  <tr>")
		.append("    <td align=\"center\" valign=\"middle\" bgcolor=\"#FFFFFF\" style=\"padding:15px;\"><table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
		.append("  <tr>")
		.append("    <td align=\"center\" valign=\"middle\" style=\"border:1px solid #dcdcdc; padding:2px;\"><table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
		.append("      <tr>")
		.append("        <td align=\"center\" valign=\"middle\" style=\"font-family:Arial, Helvetica, sans-serif; font-size:24px; color:#FFF; border-bottom:2px solid #f18300; background-color:#00a53d; padding:5px;\">"+companyName+"</td>")
		.append("      </tr>")
		.append("      <tr>")
		.append("        <td align=\"left\" valign=\"middle\" style=\"font-family:Arial, Helvetica, sans-serif; font-size:15px; line-height:20px; color:#666; padding:30px;\">Dear <strong style=\"color:#000000;\">Group Sales Manager</strong> ,<br/><br/>")
		.append("")
		.append("")	
		.append("Good Day.</br></br>")
		.append("One of our privileged customer has requested for a special rate through SPECIAL CUSTOMER DEAL. Please, find the details below and do the needful as earliest.<br/><br/>")
		.append("")
		.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">")
		.append("<style>" +
				"td { padding: 6px; border: 1px solid #ccc; text-align: left; }" + 
				"th { background: green; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;}" +
				"</style>")
				.append("<body>")

				.append("<table>\n")
				.append("<tr >")

				.append("<th>")
				.append("Customer Name/Reference")
				.append("</th>")

				.append("<th>")
				.append("Beneficiary Name")
				.append("</th>")

				.append("<th>")
				.append("Bank Name")
				.append("</th>")

				.append("<th>")
				.append("Branch")
				.append("</th>")

				.append("<th>")
				.append("Amount")
				.append("</th>")

				.append("<th>")
				.append("Created By")
				.append("</th>")

				.append("<th>")
				.append("Created Date")
				.append("</th>")

				.append("</tr>\n")

				.append("<tr>")

				.append("<td>")
				.append(Name)
				.append("</td>")

				.append("<td>")
				.append(beneName)
				.append("</td>")

				.append("<td>")
				.append(bank)
				.append("</td>")

				.append("<td>")
				.append(branchName)
				.append("</td>")

				.append("<td>")
				.append(amount)
				.append("</td>")

				.append("<td>")
				.append(createdBy)
				.append("</td>")

				.append("<td>")
				.append(createdDate)
				.append("</td>")

				.append("</tr>\n").append("</table>").append("</body>").append("</html>")

				.append("")
				.append("")
				.append("")
				.append("")
				.append("")
				/*.append(Constants.CUSTOMER_URL)*/
				.append("</br></br>")
				.append("</br></br>")
				.append("</br></br>")
				.append("Thanks and Regards,</br>")
				.append("Al Mulla Exchange [AMX],</br>")
				.append("Al Mulla Group,</br>")
				.append("Kuwait</br>")
				/*.append("<exch-online@almullagroup.com></br>")*/
				.append("Email : <a href=\"mailto:exch-online@almullagroup.com/\" target=\"_blank\" style=\"outline:none; color:#f18300; font-weight:bold; text-decoration:none;\">Al Mulla Exchange</a></br>")
				.append("")
				.append("--------------------------------------------------------------------------------------</br>")
				.append("This is an auto-generated email sent by Al Mulla Exchange.</br></br>")
				.append("--------------------------------------------------------------------------------------</br>")
				.append("")
				.append("")
				.append("      </td>")
				.append("      </tr>")
				.append("    </table></td>")
				.append("  </tr>")
				.append("</table>")
				.append("</td>")
				.append("  </tr>")
				.append("  <tr>")
				.append("    <td align=\"center\" valign=\"middle\" bgcolor=\"#d7d7d7\" style=\" border-top:2px solid #018a34; font-family:Arial, Helvetica, sans-serif; font-size:14px;  color:#000; padding:10px;\"><a href=\"http://www.almullagroup.com/finance/exchange.aspx\" target=\"_blank\" style=\"outline:none; color:#000000; text-decoration:none;\">www.almullaexchange.com</a></td>")
				.append("  </tr>").append("</table>").append("</body>").append("</html>");
		return content.toString();
	}


	public void sendMailToCustomerForPlaceOrderApprval(List<ApplicationSetup> lstApplicationSetup,HashMap<String, String> inputValues) throws AddressException, MessagingException{


		ApplicationSetup applicationSetup =lstApplicationSetup.get(0);

		String host = applicationSetup.getEmailHost();//"homail.amgdom.com"; 
		final String user =applicationSetup.getEmailUserName();// "excorpmark7"; 
		final String pass = applicationSetup.getEmailPassword();//"7kr1213EX"; 
		String from=applicationSetup.getEmailId();
		BigDecimal port=applicationSetup.getEmailPortNo();

		String mailContent=getPlaceOrderCoformationToCustomerEmailContent(inputValues);

		String emailId=inputValues.get("EmailId");
		String RateOffered=inputValues.get("RateOffered");
		//String user=inputValues.get("User");


		Properties props= new Properties();

		props.put("mail.smtp.host", host); // Setup mail server
		props.setProperty("mail.smtp.auth","true");
		//props.put("mail.smtp.port", port);

		Session session = Session.getInstance(props,new GMailAuthenticator(user, pass));
		session.setPasswordAuthentication(new URLName("smtp",host,port.intValue(),"INBOX",user,pass), new PasswordAuthentication(user,pass));

		InternetAddress arReplTo[] = new InternetAddress[1];

		Message message = new MimeMessage(session); // Define message
		// Fill its headers
		message.setSubject(RateOffered);
		message.setFrom(new InternetAddress("Al Mulla Exchange Online <"+from+">"));
		arReplTo[0] = new InternetAddress(from);
		message.setReplyTo(arReplTo);

		InternetAddress[] address = {new InternetAddress(emailId)};
		message.setRecipients(Message.RecipientType.TO, address);



		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(mailContent, "text/html");

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);


		message.setContent(multipart);

		Transport.send(message);

	}


	private String getPlaceOrderCoformationToCustomerEmailContent(HashMap<String, String> inputValues) {

		String amount=inputValues.get("Amount");
		String createdDate=inputValues.get("Created Date");
		String rateOfferedMgr=inputValues.get("RateOfferedMgr");
		String customerUrl=inputValues.get("customerUrl");
		String customerUniqueNumber=inputValues.get("documentNumber");
		StringBuffer content = new StringBuffer();


		content.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">")
		.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">")
		.append("<head>")
		.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />")
		.append("<title>Al Mulla Exchange</title>")
		.append("</head>")
		.append("<body bgcolor=\"#ededed\" style=\"padding:0; margin:0; text-align:center;\">")
		.append("<table width=\"700\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
		.append("  <tr>")
		.append("    <td align=\"left\" valign=\"middle\" bgcolor=\"#00a53d\"><img src=\"https://applications2.almullagroup.com/onlineremit/faces/images/prettyLogo.png\" width=\"264\" height=\"89\" /></td>")
		.append("  </tr>")
		.append("  <tr>")
		.append("    <td align=\"center\" valign=\"middle\" bgcolor=\"#FFFFFF\" style=\"padding:15px;\"><table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
		.append("  <tr>")
		.append("    <td align=\"center\" valign=\"middle\" style=\"border:1px solid #dcdcdc; padding:2px;\"><table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
		.append("      <tr>")
		.append("        <td align=\"center\" valign=\"middle\" style=\"font-family:Arial, Helvetica, sans-serif; font-size:24px; color:#FFF; border-bottom:2px solid #f18300; background-color:#00a53d; padding:5px;\">Al Mulla Exchange</td>")
		.append("      </tr>")
		.append("      <tr>")
		.append("        <td align=\"left\" valign=\"middle\" style=\"font-family:Arial, Helvetica, sans-serif; font-size:15px; line-height:20px; color:#666; padding:30px;\">Dear <strong style=\"color:#000000;\">Customer</strong> ,<br/><br/>")
		.append("")
		.append("")	
		.append("Thank you for placing the order with us for ")
		.append(amount)
		.append(" ")	
		.append("for")
		.append(" ")	
		.append(createdDate)
		.append(".")
		.append("The best rate quoted for your order is ")
		.append(rateOfferedMgr)
		.append(" ")
		.append("Please confirm the order by clicking in the below link ( link for our online)")
		.append("")
		.append("")
		.append("")
		.append("")
		.append("")
		.append("")
		.append("</br></br>")
		.append("</br></br>")
		.append("URL : <a href=\"http://10.200.4.22:7001/AlmullaExchangeService/almullagroup/almullagroup.jsf?name=Almulla\" target=\"_blank\" style=\"outline:none; color:#f18300; font-weight:bold; text-decoration:none;\">customerUrl</a></br>")
		.append("</br></br>")
		.append("</br></br>")
		.append("<strong style=\"color:#333;\">Document Number:</strong> <strong style=\"color:#257b45;\"></strong>")
		.append(customerUniqueNumber)
		.append("</br></br>")
		.append("</br></br>")
		.append("Thanks and Regards,</br>")
		.append("Al Mulla Exchange [AMX],</br>")
		.append("Al Mulla Group,</br>")
		.append("Kuwait</br>")
		.append("Email : <a href=\"mailto:exch-online@almullagroup.com/\" target=\"_blank\" style=\"outline:none; color:#f18300; font-weight:bold; text-decoration:none;\">Al Mulla Exchange</a></br>")
		.append("")
		.append("--------------------------------------------------------------------------------------</br>")
		.append("This is an auto-generated email sent by Al Mulla Exchange.</br></br>")
		.append("--------------------------------------------------------------------------------------</br>")
		.append("")
		.append("")
		.append("      </td>")
		.append("      </tr>")
		.append("    </table></td>")
		.append("  </tr>")
		.append("</table>")
		.append("</td>")
		.append("  </tr>")
		.append("  <tr>")
		.append("    <td align=\"center\" valign=\"middle\" bgcolor=\"#d7d7d7\" style=\" border-top:2px solid #018a34; font-family:Arial, Helvetica, sans-serif; font-size:14px;  color:#000; padding:10px;\"><a href=\"http://www.almullagroup.com/finance/exchange.aspx\" target=\"_blank\" style=\"outline:none; color:#000000; text-decoration:none;\">www.almullaexchange.com</a></td>")
		.append("  </tr>").append("</table>").append("</body>").append("</html>");
		return content.toString();
	}

	public void sendRateOfferedMailBranchSupportTrnx(
			List<ApplicationSetup> lstApplicationSetup,
			HashMap<String, String> inputValues) throws AddressException,
			MessagingException {

		ApplicationSetup applicationSetup = lstApplicationSetup.get(0);

		String host = applicationSetup.getEmailHost();// "homail.amgdom.com";
		final String user = applicationSetup.getEmailUserName();// "excorpmark7";
		final String pass = applicationSetup.getEmailPassword();// "7kr1213EX";
		String from = applicationSetup.getEmailId();
		BigDecimal port = applicationSetup.getEmailPortNo();

		String mailContent = getBranchSupportTrnxForCustomer(inputValues);

		String emailId = inputValues.get("emailId");
		// String RateOffered=inputValues.get("RateOffered");
		// String user=inputValues.get("User");

		Properties props = new Properties();

		props.put("mail.smtp.host", host); // Setup mail server
		props.setProperty("mail.smtp.auth", "true");
		// props.put("mail.smtp.port", port);

		Session session = Session.getInstance(props, new GMailAuthenticator(
				user, pass));
		session.setPasswordAuthentication(
				new URLName("smtp", host, port.intValue(), "INBOX", user, pass),
				new PasswordAuthentication(user, pass));

		InternetAddress arReplTo[] = new InternetAddress[1];

		Message message = new MimeMessage(session); // Define message
		// Fill its headers
		message.setSubject("RateOffered");
		message.setFrom(new InternetAddress("Al Mulla Exchange Online <" + from
				+ ">"));
		arReplTo[0] = new InternetAddress(from);
		message.setReplyTo(arReplTo);

		InternetAddress[] address = { new InternetAddress(emailId) };
		message.setRecipients(Message.RecipientType.TO, address);

		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(mailContent, "text/html");

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);

		message.setContent(multipart);

		Transport.send(message);

	}


	private String getBranchSupportTrnxForCustomer(
			HashMap<String, String> inputValues) {
		String customerUniqueNumber = inputValues.get("customerUniqueNumber");
		StringBuffer content = new StringBuffer();
		content.append(
				"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">")
				.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">")
				.append("<head>")
				.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />")
				.append("<title>Al Mulla Exchange</title>")
				.append("</head>")
				.append("<body bgcolor=\"#ededed\" style=\"padding:0; margin:0; text-align:center;\">")
				.append("<table width=\"700\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
				.append("  <tr>")
				.append("    <td align=\"left\" valign=\"middle\" bgcolor=\"#00a53d\"><img src=\"https://applications2.almullagroup.com/onlineremit/faces/images/prettyLogo.png\" width=\"264\" height=\"89\" /></td>")
				.append("  </tr>")
				.append("  <tr>")
				.append("    <td align=\"center\" valign=\"middle\" bgcolor=\"#FFFFFF\" style=\"padding:15px;\"><table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
				.append("  <tr>")
				.append("    <td align=\"center\" valign=\"middle\" style=\"border:1px solid #dcdcdc; padding:2px;\"><table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
				.append("      <tr>")
				.append("        <td align=\"center\" valign=\"middle\" style=\"font-family:Arial, Helvetica, sans-serif; font-size:24px; color:#FFF; border-bottom:2px solid #f18300; background-color:#00a53d; padding:5px;\">Al Mulla Exchange</td>")
				.append("      </tr>")
				.append("      <tr>")
				.append("        <td align=\"left\" valign=\"middle\" style=\"font-family:Arial, Helvetica, sans-serif; font-size:15px; line-height:20px; color:#666; padding:30px;\">Dear <strong style=\"color:#000000;\">Branch Manager</strong> ,<br/><br/>")
				.append("")
				.append("")
				.append("Good Day.</br></br>")
				.append("We would like to remind you that you have mandated the following standing instruction.Kindly find the below details and click on the below given link to complete the transaction..<br/><br/>")
				.append("")
				.append("<strong style=\"color:#333;\">Application Number</strong> <strong style=\"color:#257b45;\"></strong><br/><br/>")
				.append(customerUniqueNumber)
				.append("</br></br>")
				.append("</br></br>")

				.append("")
				.append("")
				.append("")
				.append("")
				/*
				 * .append(Constants.CUSTOMER_URL) .append(
				 * "URL : <a href=\"http://10.200.4.22:7001/AlmullaExchangeService/almullagroup/almullagroup.xhtml/\" target=\"_blank\" style=\"outline:none; color:#f18300; font-weight:bold; text-decoration:none;\">customerUrl</a></br>"
				 * )
				 */
				.append("</br></br>")
				.append("</br></br>")
				.append("<strong style=\"color:#333;\">Application Number</strong> <strong style=\"color:#257b45;\"></strong><br/><br/>")
				.append(customerUniqueNumber)
				.append("</br></br>")
				.append("</br></br>")
				.append("Thanks and Regards,</br>")
				.append("Al Mulla Exchange [AMX],</br>")
				.append("Al Mulla Group,</br>")
				.append("Kuwait</br>")
				/* .append("<exch-online@almullagroup.com></br>") */
				.append("Email : <a href=\"mailto:exch-online@almullagroup.com/\" target=\"_blank\" style=\"outline:none; color:#f18300; font-weight:bold; text-decoration:none;\">Al Mulla Exchange</a></br>")
				.append("")
				.append("--------------------------------------------------------------------------------------</br>")
				.append("This is an auto-generated email sent by Al Mulla Exchange.</br></br>")
				.append("--------------------------------------------------------------------------------------</br>")
				.append("")
				.append("")
				.append("      </td>")
				.append("      </tr>")
				.append("    </table></td>")
				.append("  </tr>")
				.append("</table>")
				.append("</td>")
				.append("  </tr>")
				.append("  <tr>")
				.append("    <td align=\"center\" valign=\"middle\" bgcolor=\"#d7d7d7\" style=\" border-top:2px solid #018a34; font-family:Arial, Helvetica, sans-serif; font-size:14px;  color:#000; padding:10px;\"><a href=\"http://www.almullagroup.com/finance/exchange.aspx\" target=\"_blank\" style=\"outline:none; color:#000000; text-decoration:none;\">www.almullaexchange.com</a></td>")
				.append("  </tr>").append("</table>").append("</body>")
				.append("</html>");
		return content.toString();
	}



	public void sendCustomerEmailVerification(List<ApplicationSetup> lstApplicationSetup,String to ,String subject,String customerName,BigDecimal custmerId) {
		try {


			ApplicationSetup applicationSetup =lstApplicationSetup.get(0);

			String host = applicationSetup.getEmailHost();//"homail.amgdom.com"; 
			final String user =applicationSetup.getEmailUserName();// "excorpmark7"; 
			final String pass = applicationSetup.getEmailPassword();//"7kr1213EX"; 
			String from=applicationSetup.getEmailId();
			BigDecimal port=applicationSetup.getEmailPortNo();

			Properties props= new Properties();

			props.put("mail.smtp.host", host); // Setup mail server
			props.setProperty("mail.smtp.auth","true");
			//props.put("mail.smtp.port", port);

			Session session = Session.getInstance(props,new GMailAuthenticator(user, pass));
			session.setPasswordAuthentication(new URLName("smtp",host,port.intValue(),"INBOX",user,pass), new PasswordAuthentication(user,pass));



			InternetAddress arReplTo[] = new InternetAddress[1];

			Message message = new MimeMessage(session); // Define message
			// Fill its headers
			message.setSubject(subject);
			message.setFrom(new InternetAddress("Al Mulla Exchange Online <"+from+">"));
			arReplTo[0] = new InternetAddress(from);
			message.setReplyTo(arReplTo);

			InternetAddress[] address = {new InternetAddress(to)};
			message.setRecipients(Message.RecipientType.TO, address);

			String text1 = getCusEmailVerificationContent().replace("${0}", Constants.EMAILVERFICATIONAPP);
			String text2 = text1.replace("${1}", custmerId.toString());
			String finalText = text2.replace("${2}", to);

			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(finalText, "text/html");

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			message.setContent(multipart);

			Transport.send(message);


		} catch (Exception e) {
			System.out.println("Mail not sent to user :: " + e);
		}
	}


	private String getCusEmailVerificationContent() {
		StringBuffer content = new StringBuffer();

		content.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">")
		.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">")
		.append("<head>")
		.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />")
		.append("<title>Al Mulla Exchange</title>")
		.append("</head>")
		.append("<body bgcolor=\"#ededed\" style=\"padding:0; margin:0; text-align:center;\">")
		.append("<table width=\"700\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")

		.append("  <tr>")
		.append("    <td align=\"center\" valign=\"middle\" bgcolor=\"#FFFFFF\" style=\"padding:15px;\"><table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
		.append("  <tr>")
		.append("    <td align=\"center\" valign=\"middle\" style=\"border:1px solid #dcdcdc; padding:2px;\"><table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
		.append("      <tr>")
		.append("        <td align=\"center\" valign=\"middle\" style=\"font-family:Arial, Helvetica, sans-serif; font-size:24px; color:#FFF; border-bottom:2px solid #f18300; background-color:#00a53d; padding:5px;\">Al Mulla Exchange</td>")
		.append("      </tr>")
		.append("      <tr>")
		.append("        <td align=\"left\" valign=\"middle\" style=\"font-family:Arial, Helvetica, sans-serif; font-size:15px; line-height:20px; color:#666; padding:30px;\">Dear <strong style=\"color:#000000;\">Customer</strong> ,<br/><br/>")
		.append("")
		.append("")
		.append("Greetings from Al Mulla Exchange!<br/><br/>")
		.append("")
		.append("")
		.append("Thank you for opting for email receipts")
		.append("<br/><br/>")
		.append("")
		.append("<strong style=\"color:#333;\">Please Click Below Link For Email Verification:</strong> <br/><br/>")
		.append("")
		.append("<a href=\"${0}?customerId=${1}&customerEmailId=${2}\" style=\"outline:none; color:#f18300; font-weight:bold; text-decoration:none;\">Verify E-mail</a><br/><br/>")
		.append("")
		.append("")
		.append("<strong style=\"color:Green;\">Go Green.. Switch to eReceipts</strong><br/><br/>")
		.append("")
		.append("</td>")
		.append("      </tr>")
		.append("    </table></td>")
		.append("  </tr>")
		.append("</table>")
		.append("</td>")
		.append("  </tr>")
		.append("  <tr>")
		.append("    <td align=\"center\" valign=\"middle\" bgcolor=\"#d7d7d7\" style=\" border-top:2px solid #018a34; font-family:Arial, Helvetica, sans-serif; font-size:14px;  color:#000; padding:10px;\"><a href=\"http://www.almullagroup.com/finance/exchange.aspx\" target=\"_blank\" style=\"outline:none; color:#000000; text-decoration:none;\">www.almullaexchange.com</a></td>")
		.append("  </tr>").append("</table>").append("</body>").append("</html>");

		return content.toString();
	}

	public void sendMailToSPLCUSTREQ(List<ApplicationSetup> lstApplicationSetup,HashMap<String, String> inputValues) throws AddressException, MessagingException{

		ApplicationSetup applicationSetup =lstApplicationSetup.get(0);

		String host = applicationSetup.getEmailHost();//"homail.amgdom.com"; 
		final String user =applicationSetup.getEmailUserName();// "excorpmark7"; 
		final String pass = applicationSetup.getEmailPassword();//"7kr1213EX"; 
		String from=applicationSetup.getEmailId();
		BigDecimal port=applicationSetup.getEmailPortNo();

		String mailContent=getSpecialCustomerDealRequest(inputValues);

		//testing
		/*String email1 = "chiranjeevi.veeravalli@almullagroup.com";
		String email2 = "chiranjeevi510@gmail.com";*/

		String email1 = "exch-treasury@almullagroup.com";
		String email2 = "shibu.daniel@almullagroup.com";

		String RateOffered="Special Deal Information";

		Properties props= new Properties();

		props.put("mail.smtp.host", host); // Setup mail server
		props.setProperty("mail.smtp.auth","true");

		Session session = Session.getInstance(props,new GMailAuthenticator(user, pass));
		session.setPasswordAuthentication(new URLName("smtp",host,port.intValue(),"INBOX",user,pass), new PasswordAuthentication(user,pass));

		InternetAddress arReplTo[] = new InternetAddress[1];

		Message message = new MimeMessage(session); // Define message
		// Fill its headers
		message.setSubject(RateOffered);
		message.setFrom(new InternetAddress("Al Mulla Exchange <"+from+">"));
		arReplTo[0] = new InternetAddress(from);
		message.setReplyTo(arReplTo);

		InternetAddress[] address = {new InternetAddress(email1),new InternetAddress(email2)};
		message.setRecipients(Message.RecipientType.TO, address);

		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(mailContent, "text/html");

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);


		message.setContent(multipart);

		Transport.send(message);
	}

	private String getSpecialCustomerDealRequest(HashMap<String, String> inputValues) {
		String Name=inputValues.get("Name");
		String bank=inputValues.get("Bank");
		String branchName=inputValues.get("Branch Name");
		String amount=inputValues.get("Amount");
		String createdBy=inputValues.get("Craeted By");
		String createdDate=inputValues.get("Created Date");
		String docNoYear=inputValues.get("DocumentNo_Year");

		String companyName = null;
		if(session.getCountryAlphaTwoCode() != null && session.getCountryAlphaTwoCode().equalsIgnoreCase(Constants.KUWAIT_ALPHA_TWO_CODE)){
			companyName = Constants.COMPANY_NAME_KW;
		}else if(session.getCountryAlphaTwoCode() != null && session.getCountryAlphaTwoCode().equalsIgnoreCase(Constants.OMAN_ALPHA_TWO_CODE)){
			companyName = Constants.COMPANY_NAME_OM;
		}else if(session.getCountryAlphaTwoCode() != null && session.getCountryAlphaTwoCode().equalsIgnoreCase(Constants.BAHRAIN_ALPHA_TWO_CODE)){
			companyName = Constants.COMPANY_NAME_BH;
		}

		StringBuffer content = new StringBuffer();


		content.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">")
		.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">")
		.append("<head>")
		.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />")
		.append("<title>Al Mulla Exchange</title>")
		.append("</head>")
		.append("<body bgcolor=\"#ededed\" style=\"padding:0; margin:0; text-align:center;\">")
		.append("<table width=\"700\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
		.append("  <tr>")
		.append("    <td align=\"center\" valign=\"middle\" bgcolor=\"#FFFFFF\" style=\"padding:15px;\"><table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
		.append("  <tr>")
		.append("    <td align=\"center\" valign=\"middle\" style=\"border:1px solid #dcdcdc; padding:2px;\"><table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
		.append("      <tr>")
		.append("        <td align=\"center\" valign=\"middle\" style=\"font-family:Arial, Helvetica, sans-serif; font-size:24px; color:#FFF; border-bottom:2px solid #f18300; background-color:#00a53d; padding:5px;\">"+companyName+"</td>")
		.append("      </tr>")
		.append("      <tr>")
		.append("        <td align=\"left\" valign=\"middle\" style=\"font-family:Arial, Helvetica, sans-serif; font-size:15px; line-height:20px; color:#666; padding:30px;\">Dear <strong style=\"color:#000000;\"> SHIBU DANIEL </strong> ,<br/><br/>")
		.append("")
		.append("")	
		.append("Good Day.</br></br>")
		.append("One of our privileged customer has requested for a special rate through Customer Special Deal. Please, find the details below and do the needful as earliest.<br/><br/>")
		.append("")
		.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">")
		.append("<style>" +
				"td { padding: 6px; border: 1px solid #ccc; text-align: left; }" + 
				"th { background: green; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;}" +
				"</style>")
				.append("<body>")

				.append("<table>\n")
				.append("<tr >")

				.append("<th>")
				.append("Document_No / Year")
				.append("</th>")

				.append("<th>")
				.append("Customer_Name/Reference")
				.append("</th>")

				.append("<th>")
				.append("Bank Name")
				.append("</th>")

				.append("<th>")
				.append("Branch")
				.append("</th>")

				.append("<th>")
				.append("Amount")
				.append("</th>")

				.append("<th>")
				.append("Created By")
				.append("</th>")

				.append("<th>")
				.append("Created Date")
				.append("</th>")

				.append("</tr>\n")

				.append("<tr>")

				.append("<td>")
				.append(docNoYear)
				.append("</td>")

				.append("<td>")
				.append(Name)
				.append("</td>")

				.append("<td>")
				.append(bank)
				.append("</td>")

				.append("<td>")
				.append(branchName)
				.append("</td>")

				.append("<td>")
				.append(amount)
				.append("</td>")

				.append("<td>")
				.append(createdBy)
				.append("</td>")

				.append("<td>")
				.append(createdDate)
				.append("</td>")

				.append("</tr>\n").append("</table>").append("</body>").append("</html>")

				.append("")
				.append("")
				.append("</br></br>")
				.append("</br></br>")
				.append("")
				.append("")
				.append("      </td>")
				.append("      </tr>")
				.append("    </table></td>")
				.append("  </tr>")
				.append("</table>")
				.append("</td>")
				.append("  </tr>")
				.append("  <tr>")
				.append("  </tr>").append("</table>").append("</body>").append("</html>");
		return content.toString();
	}

	public void sendMailToCustomerWithAttachmentForKnetUpload(List<ApplicationSetup> lstApplicationSetup,String to ,String subject,byte[] reportByteArray,String customerName) throws AddressException, MessagingException{


		System.out.println("To Email :"+to);
		//to = "mir.rabiluddin@almullagroup.com";

		ApplicationSetup applicationSetup =lstApplicationSetup.get(0);

		String host = applicationSetup.getEmailHost(); 
		final String user =applicationSetup.getEmailUserName(); 
		final String pass = applicationSetup.getEmailPassword(); 
		String from=applicationSetup.getEmailId();
		BigDecimal port=applicationSetup.getEmailPortNo();



		Properties props= new Properties();

		props.put("mail.smtp.host", host); // Setup mail server
		props.setProperty("mail.smtp.auth","true");


		Session session = Session.getInstance(props,new GMailAuthenticator(user, pass));
		session.setPasswordAuthentication(new URLName("smtp",host,port.intValue(),"INBOX",user,pass), new PasswordAuthentication(user,pass));

		InternetAddress arReplTo[] = new InternetAddress[1];

		Message message = new MimeMessage(session); // Define message
		// Fill its headers
		message.setSubject(subject);
		message.setFrom(new InternetAddress("Al Mulla Exchange <"+from+">"));
		arReplTo[0] = new InternetAddress(from);
		message.setReplyTo(arReplTo);

		InternetAddress[] address = {new InternetAddress(to)};
		message.setRecipients(Message.RecipientType.TO, address);

		StringBuffer content =new StringBuffer();


		content.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">")
		.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">")
		.append("<head>")
		.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />")

		.append("</head>")
		.append("<body bgcolor=\"#ededed\" style=\"padding:0; margin:0; text-align:center;\">")
		.append("<table width=\"700\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
		.append("  <tr>")
		.append("  </tr>")
		.append("  <tr>")
		.append("    <td align=\"center\" valign=\"middle\" bgcolor=\"#FFFFFF\" style=\"padding:15px;\"><table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
		.append("  <tr>")
		.append("    <td align=\"center\" valign=\"middle\" style=\"border:1px solid #dcdcdc; padding:2px;\"><table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
		.append("      <tr>")
		.append("        <td align=\"center\" valign=\"middle\" style=\"font-family:Arial, Helvetica, sans-serif; font-size:24px; color:#FFF; border-bottom:2px solid #f18300; background-color:#00a53d; padding:5px;\">Al Mulla Exchange</td>")
		.append("      </tr>")
		.append("      <tr>")
		.append("        <td align=\"left\" valign=\"middle\" style=\"font-family:Arial, Helvetica, sans-serif; font-size:15px; line-height:20px; color:#666; padding:30px;\">Dear <strong style=\"color:#000000;\">").append(customerName).append("</strong> ,<br/><br/>")
		.append("")
		.append("<br></br>")
		.append("Greetings from Al Mulla Exchange !<br></br>")
		.append("<br></br>")
		.append("You have just completed your transaction on <a href='https://applications2.almullagroup.com/amxremit'>www.amxremit.com</a> Please find attached the remittance receipt.<b>")
		.append(".</b><br/><br/>")
		.append("If you are on social media, you can follow us on facebook @ Almullaexchange. This will give you the latest updates, events and marketing promotions we have. Please continue using <a href='https://applications2.almullagroup.com/amxremit'>www.amxremit.com</a> and get bonus 250 loyalty points for every transaction.<br/><br/>")
		.append("Hope you found our service to be useful. If you have any suggestions/ comments please feel free to write to us on <a href='#'>exch-online1@almullagroup.com</a> <br/><br/>")
		.append("Thanks and Regards,<br/><br/>").append("Al Mulla Exchange")
		.append(",<br>")
		.append(" Kuwait <br/>[Email] " + from + "<br>")
		.append("</td>")
		.append("      </tr>")
		.append("    </table></td>")
		.append("  </tr>")
		.append("</table>")
		.append("</td>")
		.append("  </tr>")
		.append("  <tr>")
		.append("  </tr>").append("</table>")
		.append("</body>")
		.append("<i>This is an auto-generated email sent by " + from + " </i><br />")
		.append("</html>");
		System.out.println("MRU Knet Uplaod Email :"+content.toString());

		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(content.toString(), "text/html");

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);

		MimeBodyPart mbp2 = new MimeBodyPart();
		mbp2.setFileName("RemittanceReceipt.pdf");

		mbp2.setDataHandler(new DataHandler(new ByteArrayDataSource(reportByteArray,"application/pdf")));

		multipart.addBodyPart(mbp2);

		message.setContent(multipart);

		Transport.send(message);

	}

	public void sendMailToCustomerForRefundKnetTrnx(List<ApplicationSetup> lstApplicationSetup,String to,String subject,String customerName) throws AddressException, MessagingException{


		System.out.println("To Email :"+to);
		//to = "mir.rabiluddin@almullagroup.com";

		ApplicationSetup applicationSetup =lstApplicationSetup.get(0);

		String host = applicationSetup.getEmailHost(); 
		final String user =applicationSetup.getEmailUserName(); 
		final String pass = applicationSetup.getEmailPassword(); 
		String from=applicationSetup.getEmailId();
		BigDecimal port=applicationSetup.getEmailPortNo();

		Properties props= new Properties();

		props.put("mail.smtp.host", host); // Setup mail server
		props.setProperty("mail.smtp.auth","true");


		Session session = Session.getInstance(props,new GMailAuthenticator(user, pass));
		session.setPasswordAuthentication(new URLName("smtp",host,port.intValue(),"INBOX",user,pass), new PasswordAuthentication(user,pass));

		InternetAddress arReplTo[] = new InternetAddress[1];

		Message message = new MimeMessage(session); // Define message
		// Fill its headers
		message.setSubject(subject);
		message.setFrom(new InternetAddress("Al Mulla Exchange <"+from+">"));
		arReplTo[0] = new InternetAddress(from);
		message.setReplyTo(arReplTo);

		InternetAddress[] address = {new InternetAddress(to)};
		message.setRecipients(Message.RecipientType.TO, address);

		StringBuffer content =new StringBuffer();


		content.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">")
		.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">")
		.append("<head>")
		.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />")

		.append("</head>")
		.append("<body bgcolor=\"#ededed\" style=\"padding:0; margin:0; text-align:center;\">")
		.append("<table width=\"700\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
		.append("  <tr>")
		.append("  </tr>")
		.append("  <tr>")
		.append("    <td align=\"center\" valign=\"middle\" bgcolor=\"#FFFFFF\" style=\"padding:15px;\"><table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
		.append("  <tr>")
		.append("    <td align=\"center\" valign=\"middle\" style=\"border:1px solid #dcdcdc; padding:2px;\"><table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
		.append("      <tr>")
		.append("        <td align=\"center\" valign=\"middle\" style=\"font-family:Arial, Helvetica, sans-serif; font-size:24px; color:#FFF; border-bottom:2px solid #f18300; background-color:#00a53d; padding:5px;\">Al Mulla Exchange</td>")
		.append("      </tr>")
		.append("      <tr>")
		.append("        <td align=\"left\" valign=\"middle\" style=\"font-family:Arial, Helvetica, sans-serif; font-size:15px; line-height:20px; color:#666; padding:30px;\">Dear <strong style=\"color:#000000;\">").append(customerName).append("</strong> ,<br/><br/>")
		.append("")
		.append("")
		.append("We regret to inform you that despite several requests made to you during the payment process, to use only a Registered Debit Card, you have used a card which is different from the registered cards against your name in our system and displayed earlier to you.")
		.append("<br></br>Rather than cancel your transaction completely, and cause inconvenience to you, we have put the transaction on hold till the end of business hours tomorrow.")
		.append("<br></br>During this time, please visit any of our Branches, and present your original Civil ID and the original Debit Card to any of our counter staff,who will verify the card and proceed to register the same and also immediately release your transaction.")
		.append("<br></br>Please note that the card you used for the transaction must be in your name only. ")
		.append("<br></br>If you have chosen to use a Debit card which is not in your own name, we request you to immediately call us on 22057194,")
		.append("<br>and we will cancel the transaction and proceed to refund the value to the card holder within 3-4 working days.")
		.append("<br></br>")
		/*.append("Thank you for visiting us <b>")*/
		.append(".</b><br/><br/>")
		.append("Thanks and Regards,<br/><br/>").append("Al Mulla Exchange")
		.append(",<br>")
		.append(" Kuwait <br/>[Email] " + from + "<br>")
		.append("</td>")
		.append("      </tr>")
		.append("    </table></td>")
		.append("  </tr>")
		.append("</table>")
		.append("</td>")
		.append("  </tr>")
		.append("  <tr>")
		.append("  </tr>").append("</table>")
		.append("</body>")
		.append("<i>This is an auto-generated email sent by " + from + " </i><br />")
		.append("</html>");
		System.out.println("MRU Knet Uplaod Email :"+content.toString());

		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(content.toString(), "text/html");

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);

		MimeBodyPart mbp2 = new MimeBodyPart();
		//mbp2.setFileName("RemittanceReceipt.pdf");

		//mbp2.setDataHandler(new DataHandler(new ByteArrayDataSource(reportByteArray,"application/pdf")));

		//multipart.addBodyPart(mbp2);

		message.setContent(multipart);
		Transport.send(message);

	}


	// send place order mail to customer for confirmation
	public void sendMailToCustomerFromGSMConmfirmation(List<ApplicationSetup> lstApplicationSetup,HashMap<String, String> inputValues) throws AddressException, MessagingException{

		ApplicationSetup applicationSetup =lstApplicationSetup.get(0);

		String host = applicationSetup.getEmailHost();//"homail.amgdom.com"; 
		final String user =applicationSetup.getEmailUserName();// "excorpmark7"; 
		final String pass = applicationSetup.getEmailPassword();//"7kr1213EX"; 
		String from=applicationSetup.getEmailId();
		BigDecimal port=applicationSetup.getEmailPortNo();

		String mailContent = getCustomerDetailsRateApproved(inputValues);

		//Customer Mail Id
		String custemail = inputValues.get("EmailId");

		String RateOffered="Place Order Information";

		Properties props= new Properties();

		props.put("mail.smtp.host", host); // Setup mail server
		props.setProperty("mail.smtp.auth","true");

		Session session = Session.getInstance(props,new GMailAuthenticator(user, pass));
		session.setPasswordAuthentication(new URLName("smtp",host,port.intValue(),"INBOX",user,pass), new PasswordAuthentication(user,pass));

		InternetAddress arReplTo[] = new InternetAddress[1];

		Message message = new MimeMessage(session); // Define message
		// Fill its headers
		message.setSubject(RateOffered);
		message.setFrom(new InternetAddress("Al Mulla Exchange <"+from+">"));
		arReplTo[0] = new InternetAddress(from);
		message.setReplyTo(arReplTo);

		InternetAddress[] address = {new InternetAddress(custemail)};
		message.setRecipients(Message.RecipientType.TO, address);

		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(mailContent, "text/html");

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);

		message.setContent(multipart);

		Transport.send(message);
	}

	private String getCustomerDetailsRateApproved(HashMap<String, String> inputValues) {


		String Name = inputValues.get("Name");
		//String beneName = inputValues.get("beneName");
		String bank = inputValues.get("Bank");
		String amount = inputValues.get("Amount");
		String createdDate = inputValues.get("Created Date");
		String exchangeRate = inputValues.get("exchangeRate");

		String companyName = null;
		if(session.getCountryAlphaTwoCode() != null && session.getCountryAlphaTwoCode().equalsIgnoreCase(Constants.KUWAIT_ALPHA_TWO_CODE)){
			companyName = Constants.COMPANY_NAME_KW;
		}else if(session.getCountryAlphaTwoCode() != null && session.getCountryAlphaTwoCode().equalsIgnoreCase(Constants.OMAN_ALPHA_TWO_CODE)){
			companyName = Constants.COMPANY_NAME_OM;
		}else if(session.getCountryAlphaTwoCode() != null && session.getCountryAlphaTwoCode().equalsIgnoreCase(Constants.BAHRAIN_ALPHA_TWO_CODE)){
			companyName = Constants.COMPANY_NAME_BH;
		}

		StringBuffer content = new StringBuffer();


		content.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">")
		.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">")
		.append("<head>")
		.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />")
		.append("<title>Al Mulla Exchange</title>")
		.append("</head>")
		.append("<body bgcolor=\"#ededed\" style=\"padding:0; margin:0; text-align:center;\">")
		.append("<table width=\"700\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
		.append("  <tr>")
		.append("    <td align=\"center\" valign=\"middle\" bgcolor=\"#FFFFFF\" style=\"padding:15px;\"><table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
		.append("  <tr>")
		.append("    <td align=\"center\" valign=\"middle\" style=\"border:1px solid #dcdcdc; padding:2px;\"><table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
		.append("      <tr>")
		.append("        <td align=\"center\" valign=\"middle\" style=\"font-family:Arial, Helvetica, sans-serif; font-size:24px; color:#FFF; border-bottom:2px solid #f18300; background-color:#00a53d; padding:5px;\">"+companyName+"</td>")
		.append("      </tr>")
		.append("      <tr>")
		.append("        <td align=\"left\" valign=\"middle\" style=\"font-family:Arial, Helvetica, sans-serif; font-size:15px; line-height:20px; color:#666; padding:30px;\">Dear <strong style=\"color:#000000;\"> "+Name+" </strong> ,<br/><br/>")
		.append("")
		.append("")	
		.append("Good Day.</br></br>")
		.append("To our privileged customer, has requested for a special rate through ONLINE. Please, find the details below and complete the transaction.<br/><br/>")
		.append("")
		.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">")
		.append("<style>" +
				"td { padding: 6px; border: 1px solid #ccc; text-align: left; }" + 
				"th { background: green; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;}" +
				"</style>")
				.append("<body>")

				.append("<table>\n")
				.append("<tr >")

				.append("<th>")
				.append("Customer_Name")
				.append("</th>")

				.append("<th>")
				.append("Bank Name")
				.append("</th>")

				.append("<th>")
				.append("Amount")
				.append("</th>")

				.append("<th>")
				.append("Exchange Rate")
				.append("</th>")

				.append("<th>")
				.append("Created Date")
				.append("</th>")

				.append("</tr>\n")

				.append("<tr>")

				.append("<td>")
				.append(Name)
				.append("</td>")

				.append("<td>")
				.append(bank)
				.append("</td>")

				.append("<td>")
				.append(amount)
				.append("</td>")

				.append("<td>")
				.append(exchangeRate)
				.append("</td>")

				.append("<td>")
				.append(createdDate)
				.append("</td>")

				.append("</tr>\n").append("</table>").append("</body>").append("</html>")

				.append("")
				.append("")
				.append("</br></br>")
				.append("</br></br>")
				.append("")
				.append("")
				.append("      </td>")
				.append("      </tr>")
				.append("    </table></td>")
				.append("  </tr>")
				.append("</table>")
				.append("</td>")
				.append("  </tr>")
				.append("  <tr>")
				.append("  </tr>").append("</table>").append("</body>").append("</html>");
		return content.toString();
	}

	public void sendLoginECNumberCivilIdFail(List<ApplicationSetup> lstApplicationSetup,HashMap<String, String> inputValues) throws AddressException, MessagingException{

		ApplicationSetup applicationSetup =lstApplicationSetup.get(0);

		String host = applicationSetup.getEmailHost();//"homail.amgdom.com"; 
		final String user = applicationSetup.getEmailUserName();// "excorpmark7"; 
		final String pass = applicationSetup.getEmailPassword();//"7kr1213EX"; 
		String from = applicationSetup.getEmailId();
		BigDecimal port = applicationSetup.getEmailPortNo();

		String dbCivilId = inputValues.get("DBcivilId");
		String mailContent = null;
		String subject = null;
		
		if(dbCivilId != null){
			mailContent = fetchEcNumberCivilIDMail(inputValues);
			subject ="Login EC / Civil Id (Branch Staff) - Not Matching";
		}else{
			mailContent = fetchEcNumberCivilIDEmptyMail(inputValues);
			subject ="Login EC / Civil Id (Branch Staff) - Is Empty";
		}
		
		String email1 = "soa@almullagroup.com";
		String email2 = "deepa.mathew@almullagroup.com";
		String email3 = "sneha.sudarsanan@almullagroup.com";
		
		Properties props= new Properties();

		props.put("mail.smtp.host", host); // Setup mail server
		props.setProperty("mail.smtp.auth","true");

		Session session = Session.getInstance(props,new GMailAuthenticator(user, pass));
		session.setPasswordAuthentication(new URLName("smtp",host,port.intValue(),"INBOX",user,pass), new PasswordAuthentication(user,pass));

		InternetAddress arReplTo[] = new InternetAddress[1];

		Message message = new MimeMessage(session); // Define message
		// Fill its headers
		message.setSubject(subject);
		message.setFrom(new InternetAddress("Al Mulla Exchange <"+from+">"));
		arReplTo[0] = new InternetAddress(from);
		message.setReplyTo(arReplTo);

		InternetAddress[] address = {new InternetAddress(email1),new InternetAddress(email2),new InternetAddress(email3)};
		message.setRecipients(Message.RecipientType.TO, address);

		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(mailContent, "text/html");

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);

		message.setContent(multipart);

		Transport.send(message);

	}

	public String fetchEcNumberCivilIDMail(HashMap<String, String> inputValues){
		String ecNumber = inputValues.get("ecNo");
		String civilId = inputValues.get("civilId");
		//String partnerCivilId = inputValues.get("partnerCivilId");
		String countryBranchName = inputValues.get("countryBranchName");
		String ipAddress = inputValues.get("ipAddress");
		String companyName = null;
		
		if(session.getCountryAlphaTwoCode() != null && session.getCountryAlphaTwoCode().equalsIgnoreCase(Constants.KUWAIT_ALPHA_TWO_CODE)){
			companyName = Constants.COMPANY_NAME_KW;
		}else if(session.getCountryAlphaTwoCode() != null && session.getCountryAlphaTwoCode().equalsIgnoreCase(Constants.OMAN_ALPHA_TWO_CODE)){
			companyName = Constants.COMPANY_NAME_OM;
		}else if(session.getCountryAlphaTwoCode() != null && session.getCountryAlphaTwoCode().equalsIgnoreCase(Constants.BAHRAIN_ALPHA_TWO_CODE)){
			companyName = Constants.COMPANY_NAME_BH;
		}

		StringBuffer content = new StringBuffer();
		content.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">")
		.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">")
		.append("<head>")
		.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />")

		.append("</head>")
		.append("<body bgcolor=\"#ededed\" style=\"padding:0; margin:0; text-align:center;\">")
		.append("<table width=\"700\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
		.append("  <tr>")
		.append("  </tr>")
		.append("  <tr>")
		.append("    <td align=\"center\" valign=\"middle\" bgcolor=\"#FFFFFF\" style=\"padding:15px;\"><table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
		.append("  <tr>")
		.append("    <td align=\"center\" valign=\"middle\" style=\"border:1px solid #dcdcdc; padding:2px;\"><table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
		.append("      <tr>")
		.append("        <td align=\"center\" valign=\"middle\" style=\"font-family:Arial, Helvetica, sans-serif; font-size:24px; color:#FFF; border-bottom:2px solid #f18300; background-color:red; padding:5px;\">Al Mulla Exchange</td>")
		.append("      </tr>")
		.append("      <tr>")
		.append("        <td align=\"left\" valign=\"middle\" style=\"font-family:Arial, Helvetica, sans-serif; font-size:15px; line-height:20px; color:#666; padding:30px;\">").append("<br/><br/>")
		.append("")
		.append("") 
		.append("Employee Number <strong style=\"color:#000000;\">"+ ecNumber +"</strong> allocated to ip address <strong style=\"color:#000000;\">" + ipAddress + " </strong> from <strong style=\"color:#000000;\"> " + countryBranchName + "</strong> is trying to log in manually using an incorrect Civil Id. Civil Id being entered is <strong style=\"color:#000000;\">" + civilId  + "</strong> and is not matching with Civil Id number on our record. Please follow-up with the user immediately.")

		.append("<br></br>")
		.append("</td>")
		.append("      </tr>")
		.append("    </table></td>")
		.append("  </tr>")
		.append("</table>")
		.append("</td>")
		.append("  </tr>")
		.append("  <tr>")
		.append("  </tr>").append("</table>")
		.append("</body>")
		.append("</html>");
		return content.toString();
	}

	public String fetchEcNumberCivilIDEmptyMail(HashMap<String, String> inputValues){
		String ecNumber = inputValues.get("ecNo");
		String civilId = inputValues.get("civilId");
		//String partnerCivilId = inputValues.get("partnerCivilId");
		String countryBranchName = inputValues.get("countryBranchName");
		String ipAddress = inputValues.get("ipAddress");
		String companyName = null;

		if(session.getCountryAlphaTwoCode() != null && session.getCountryAlphaTwoCode().equalsIgnoreCase(Constants.KUWAIT_ALPHA_TWO_CODE)){
			companyName = Constants.COMPANY_NAME_KW;
		}else if(session.getCountryAlphaTwoCode() != null && session.getCountryAlphaTwoCode().equalsIgnoreCase(Constants.OMAN_ALPHA_TWO_CODE)){
			companyName = Constants.COMPANY_NAME_OM;
		}else if(session.getCountryAlphaTwoCode() != null && session.getCountryAlphaTwoCode().equalsIgnoreCase(Constants.BAHRAIN_ALPHA_TWO_CODE)){
			companyName = Constants.COMPANY_NAME_BH;
		}

		StringBuffer content = new StringBuffer();
		content.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">")
		.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">")
		.append("<head>")
		.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />")

		.append("</head>")
		.append("<body bgcolor=\"#ededed\" style=\"padding:0; margin:0; text-align:center;\">")
		.append("<table width=\"700\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
		.append("  <tr>")
		.append("  </tr>")
		.append("  <tr>")
		.append("    <td align=\"center\" valign=\"middle\" bgcolor=\"#FFFFFF\" style=\"padding:15px;\"><table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
		.append("  <tr>")
		.append("    <td align=\"center\" valign=\"middle\" style=\"border:1px solid #dcdcdc; padding:2px;\"><table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
		.append("      <tr>")
		.append("        <td align=\"center\" valign=\"middle\" style=\"font-family:Arial, Helvetica, sans-serif; font-size:24px; color:#FFF; border-bottom:2px solid #f18300; background-color:red; padding:5px;\">Al Mulla Exchange</td>")
		.append("      </tr>")
		.append("      <tr>")
		.append("        <td align=\"left\" valign=\"middle\" style=\"font-family:Arial, Helvetica, sans-serif; font-size:15px; line-height:20px; color:#666; padding:30px;\">").append("<br/><br/>")
		.append("")
		.append("") 
		.append("Employee Number <strong style=\"color:#000000;\">"+ ecNumber +"</strong> allocated to ip address <strong style=\"color:#000000;\">" + ipAddress + " </strong> from <strong style=\"color:#000000;\"> " + countryBranchName + "</strong> is trying to log in manually using an incorrect Civil Id. Civil Id being entered is <strong style=\"color:#000000;\">" + civilId  + "</strong> not available on our record. Please follow-up with the user immediately.")

		.append("<br></br>")
		.append("</td>")
		.append("      </tr>")
		.append("    </table></td>")
		.append("  </tr>")
		.append("</table>")
		.append("</td>")
		.append("  </tr>")
		.append("  <tr>")
		.append("  </tr>").append("</table>")
		.append("</body>")
		.append("</html>");
		return content.toString();
	}

	public void sendLoginOTPFail(List<ApplicationSetup> lstApplicationSetup,HashMap<String, String> inputValues) throws AddressException, MessagingException{

		ApplicationSetup applicationSetup =lstApplicationSetup.get(0);

		String host = applicationSetup.getEmailHost();//"homail.amgdom.com"; 
		final String user = applicationSetup.getEmailUserName();// "excorpmark7"; 
		final String pass = applicationSetup.getEmailPassword();//"7kr1213EX"; 
		String from = applicationSetup.getEmailId();
		BigDecimal port = applicationSetup.getEmailPortNo();

		String mailContent = getLoginUserDetails(inputValues);

		String email1 = "soa@almullagroup.com";
		String email2 = "deepa.mathew@almullagroup.com";
		String email3 = "sneha.sudarsanan@almullagroup.com";

		String subject ="Login OTP (Branch Staff) - User locked";

		Properties props= new Properties();

		props.put("mail.smtp.host", host); // Setup mail server
		props.setProperty("mail.smtp.auth","true");

		Session session = Session.getInstance(props,new GMailAuthenticator(user, pass));
		session.setPasswordAuthentication(new URLName("smtp",host,port.intValue(),"INBOX",user,pass), new PasswordAuthentication(user,pass));

		InternetAddress arReplTo[] = new InternetAddress[1];

		Message message = new MimeMessage(session); // Define message
		// Fill its headers
		message.setSubject(subject);
		message.setFrom(new InternetAddress("Al Mulla Exchange <"+from+">"));
		arReplTo[0] = new InternetAddress(from);
		message.setReplyTo(arReplTo);

		InternetAddress[] address = {new InternetAddress(email1),new InternetAddress(email2),new InternetAddress(email3)};
		message.setRecipients(Message.RecipientType.TO, address);

		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(mailContent, "text/html");

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);


		message.setContent(multipart);

		Transport.send(message);

	}

	public String getLoginUserDetails(HashMap<String, String> inputValues){
		String userName = inputValues.get("userName");
		String countryBranchName = inputValues.get("countryBranchName");
		String ipAddress = inputValues.get("ipAddress");
		String companyName = null;

		if(session.getCountryAlphaTwoCode() != null && session.getCountryAlphaTwoCode().equalsIgnoreCase(Constants.KUWAIT_ALPHA_TWO_CODE)){
			companyName = Constants.COMPANY_NAME_KW;
		}else if(session.getCountryAlphaTwoCode() != null && session.getCountryAlphaTwoCode().equalsIgnoreCase(Constants.OMAN_ALPHA_TWO_CODE)){
			companyName = Constants.COMPANY_NAME_OM;
		}else if(session.getCountryAlphaTwoCode() != null && session.getCountryAlphaTwoCode().equalsIgnoreCase(Constants.BAHRAIN_ALPHA_TWO_CODE)){
			companyName = Constants.COMPANY_NAME_BH;
		}

		StringBuffer content = new StringBuffer();
		content.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">")
		.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">")
		.append("<head>")
		.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />")

		.append("</head>")
		.append("<body bgcolor=\"#ededed\" style=\"padding:0; margin:0; text-align:center;\">")
		.append("<table width=\"700\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
		.append("  <tr>")
		.append("  </tr>")
		.append("  <tr>")
		.append("    <td align=\"center\" valign=\"middle\" bgcolor=\"#FFFFFF\" style=\"padding:15px;\"><table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
		.append("  <tr>")
		.append("    <td align=\"center\" valign=\"middle\" style=\"border:1px solid #dcdcdc; padding:2px;\"><table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
		.append("      <tr>")
		.append("        <td align=\"center\" valign=\"middle\" style=\"font-family:Arial, Helvetica, sans-serif; font-size:24px; color:#FFF; border-bottom:2px solid #f18300; background-color:red; padding:5px;\">Al Mulla Exchange</td>")
		.append("      </tr>")
		.append("      <tr>")
		.append("        <td align=\"left\" valign=\"middle\" style=\"font-family:Arial, Helvetica, sans-serif; font-size:15px; line-height:20px; color:#666; padding:30px;\">").append("<br/><br/>")
		.append("")
		.append("")
		.append("User <strong style=\"color:#000000;\">"+ userName +"</strong> from <strong style=\"color:#000000;\">" + countryBranchName + "</strong> location on " + ipAddress + " has been locked out. Please follow-up with the user immediately.")

		.append("<br></br>")
		.append("</td>")
		.append("      </tr>")
		.append("    </table></td>")
		.append("  </tr>")
		.append("</table>")
		.append("</td>")
		.append("  </tr>")
		.append("  <tr>")
		.append("  </tr>").append("</table>")
		.append("</body>")
		.append("</html>");
		return content.toString();
	}

	public void sendBeneficiaryOTPFail(List<ApplicationSetup> lstApplicationSetup,HashMap<String, String> inputValues) throws AddressException, MessagingException{

		ApplicationSetup applicationSetup =lstApplicationSetup.get(0);

		String host = applicationSetup.getEmailHost();//"homail.amgdom.com"; 
		final String user = applicationSetup.getEmailUserName();// "excorpmark7"; 
		final String pass = applicationSetup.getEmailPassword();//"7kr1213EX"; 
		String from = applicationSetup.getEmailId();
		BigDecimal port = applicationSetup.getEmailPortNo();

		String mailContent = getBeneficiaryAndUserDetails(inputValues);

		String email1 = "soa@almullagroup.com";
		String email2 = "deepa.mathew@almullagroup.com";
		String email3 = "sneha.sudarsanan@almullagroup.com";

		String subject = "Beneficiary OTP (Branch Staff)";

		Properties props= new Properties();

		props.put("mail.smtp.host", host); // Setup mail server
		props.setProperty("mail.smtp.auth","true");

		Session session = Session.getInstance(props,new GMailAuthenticator(user, pass));
		session.setPasswordAuthentication(new URLName("smtp",host,port.intValue(),"INBOX",user,pass), new PasswordAuthentication(user,pass));

		InternetAddress arReplTo[] = new InternetAddress[1];

		Message message = new MimeMessage(session); // Define message
		// Fill its headers
		message.setSubject(subject);
		message.setFrom(new InternetAddress("Al Mulla Exchange <"+from+">"));
		arReplTo[0] = new InternetAddress(from);
		message.setReplyTo(arReplTo);

		InternetAddress[] address = {new InternetAddress(email1),new InternetAddress(email2),new InternetAddress(email3)};
		message.setRecipients(Message.RecipientType.TO, address);

		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(mailContent, "text/html");

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);


		message.setContent(multipart);

		Transport.send(message);

	}

	public String getBeneficiaryAndUserDetails(HashMap<String, String> inputValues){
		String userName=inputValues.get("userName");
		String countryBranchName=inputValues.get("countryBranchName");
		String beneName=inputValues.get("beneName");
		String bankName=inputValues.get("bankName");
		String branchName=inputValues.get("branchName");
		String customerName=inputValues.get("customerName");
		String customerRef=inputValues.get("customerRef");
		String beneCountryName=inputValues.get("beneCountryName");
		String beneCurrencyName=inputValues.get("beneCurrencyName");
		String ipAddress = inputValues.get("ipAddress");
		String companyName = null;

		if(session.getCountryAlphaTwoCode() != null && session.getCountryAlphaTwoCode().equalsIgnoreCase(Constants.KUWAIT_ALPHA_TWO_CODE)){
			companyName = Constants.COMPANY_NAME_KW;
		}else if(session.getCountryAlphaTwoCode() != null && session.getCountryAlphaTwoCode().equalsIgnoreCase(Constants.OMAN_ALPHA_TWO_CODE)){
			companyName = Constants.COMPANY_NAME_OM;
		}else if(session.getCountryAlphaTwoCode() != null && session.getCountryAlphaTwoCode().equalsIgnoreCase(Constants.BAHRAIN_ALPHA_TWO_CODE)){
			companyName = Constants.COMPANY_NAME_BH;
		}

		StringBuffer content = new StringBuffer();
		content.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">")
		.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">")
		.append("<head>")
		.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />")

		.append("</head>")
		.append("<body bgcolor=\"#ededed\" style=\"padding:0; margin:0; text-align:center;\">")
		.append("<table width=\"700\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
		.append("  <tr>")
		.append("  </tr>")
		.append("  <tr>")
		.append("    <td align=\"center\" valign=\"middle\" bgcolor=\"#FFFFFF\" style=\"padding:15px;\"><table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
		.append("  <tr>")
		.append("    <td align=\"center\" valign=\"middle\" style=\"border:1px solid #dcdcdc; padding:2px;\"><table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
		.append("      <tr>")
		.append("        <td align=\"center\" valign=\"middle\" style=\"font-family:Arial, Helvetica, sans-serif; font-size:24px; color:#FFF; border-bottom:2px solid #f18300; background-color:red; padding:5px;\">Al Mulla Exchange</td>")
		.append("      </tr>")
		.append("      <tr>")
		.append("        <td align=\"left\" valign=\"middle\" style=\"font-family:Arial, Helvetica, sans-serif; font-size:15px; line-height:20px; color:#666; padding:30px;\">").append("<br/><br/>")
		.append("")
		.append("")
		.append("User <strong style=\"color:#000000;\">"+ userName +"</strong> from <strong style=\"color:#000000;\">" + countryBranchName + "</strong> location on " + ipAddress + " is trying to activate the below beneficiary with three wrong OTP verification. Please follow-up with the user immediately.")
		.append("<br></br>")
		.append("<table>\n")
		.append("<tr >")

		.append("<th>")
		.append("Customer Name/Reference")
		.append("</th>")

		.append("<th>")
		.append("Beneficiary Name")
		.append("</th>")

		.append("<th>")
		.append("Bank Name")
		.append("</th>")

		.append("<th>")
		.append("Branch Name")
		.append("</th>")

		.append("<th>")
		.append("Beneficiary Country/Currency")
		.append("</th>")

		.append("</tr>\n")

		.append("<tr>")

		.append("<td>")
		.append(customerName +" / "+ customerRef)
		.append("</td>")

		.append("<td>")
		.append(beneName)
		.append("</td>")

		.append("<td>")
		.append(bankName)
		.append("</td>")

		.append("<td>")
		.append(branchName)
		.append("</td>")

		.append("<td>")
		.append(beneCountryName +" / "+ beneCurrencyName)
		.append("</td>")

		.append("</tr>\n").append("</table>").append("</body>").append("</html>")

		.append("</td>")
		.append("      </tr>")
		.append("    </table></td>")
		.append("  </tr>")
		.append("</table>")
		.append("</td>")
		.append("  </tr>")
		.append("  <tr>")
		.append("  </tr>").append("</table>")
		.append("</body>")
		.append("</html>");
		return content.toString();
	}

}

