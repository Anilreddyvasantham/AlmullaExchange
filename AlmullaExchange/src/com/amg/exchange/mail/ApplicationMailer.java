package com.amg.exchange.mail;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.amg.exchange.bean.AddPartnerDetailBean;

@SuppressWarnings("serial")
@Service("mailService")
public class ApplicationMailer implements Serializable {

	@Autowired(required = true)
	private JavaMailSender mailSender;

	public JavaMailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void sendRegistrationMail(String to, String subject, String username) {

		try {
			final MimeMessage mimeMessage = mailSender.createMimeMessage();
			final MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
			message.setTo(to);
			message.setSubject(subject);

			message.setText(getRegistrationContent().replace("${0}", username), true);
			mailSender.send(mimeMessage);
		} catch (Exception e) {
			System.out.println("Mail not sent to user :: " + e);
		}
	}

	public void sendTokenMail(String to, String subject, String customerId, String tokenNumber) {

		try {
			final MimeMessage mimeMessage = mailSender.createMimeMessage();
			final MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
			message.setTo(to);
			message.setSubject(subject);

			message.setText((getTokenGenerationContent().replace("${0}", customerId)).replace("${1}", tokenNumber), true);
			mailSender.send(mimeMessage);
		} catch (Exception e) {
			System.out.println("Mail not sent to user :: " + e);
		}
	}

	public void sendMail(String to, String subject, String content) {

		try {
			final MimeMessage mimeMessage = mailSender.createMimeMessage();
			final MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
			message.setTo(to);
			message.setSubject(subject);

			message.setText(content);

			mailSender.send(mimeMessage);
		} catch (Exception e) {
			System.out.println("Mail not sent to user :: " + e);
		}
	}

	private String getRegistrationContent() {

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
				.append("        <td align=\"left\" valign=\"middle\" style=\"font-family:Arial, Helvetica, sans-serif; font-size:15px; line-height:20px; color:#666; padding:30px;\">Dear <strong style=\"color:#000000;\">${0}</strong> ,<br/><br/>")
				.append("")
				.append("")
				.append("Congratulations! You have just completed your registration with AlMulla Exchange company, the number one service provider for exchange of money, making remittances and transferring funds to your home country from Kuwait!<br/><br/>")
				.append("")
				.append("Your Online Registration for the Remittance facility has been authorized. You can now send money to your registered services, completely online.<br/><br/>")
				.append("")
				.append("<strong style=\"color:#333;\">NOTE :</strong><br/><br/>")
				.append("You can login to the Almulla Exchange website www.amxremit.com .<br/><br/>")
				.append("Please remember your password to enjoy secure and uninterrupted service.<br/><br/>")
				.append("In case of any Difficulty, Please Contact Ms Bianca on 96005259/exch-online1@almullagroup.com, Mr Mustafa on 95592686 (for Arabic), Ms. Mamta on 95592687 /exch-online@almullagroup.com (OR) Mr Philip John on 90910110.<br/><br/>")
				.append("")
				.append("<strong style=\"color:#333;\">Thanks and Regards,</strong><br/><br/>")
				.append("Al Mulla Exchange [AMX],<br/><br/>")
				.append("AlMulla Group,<br/><br/>")
				.append("Kuwait,<br/><br/>")
				.append(" [Email] exch-online@almullagroup.com <br/><br/>")
				.append("")
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

	private String getTokenGenerationContent() {

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
				.append("Greetings from Almulla Exchange!<br/><br/>")
				.append("")
				.append("You received this email because you have successfully create your account with us Please approach nearest branch to complete your proceeding.<br/><br/>")
				.append("")
				.append("<strong style=\"color:#333;\">Your customer id No:</strong> <strong style=\"color:#257b45;\">${0}</strong><br/><br/>")
				.append("<strong style=\"color:#333;\">Your Token No.</strong> <strong style=\"color:#257b45;\">${1}</strong><br/><br/>")
				.append("")
				.append("Please <a href=\"http://www.almullaexchanges.com/\" target=\"_blank\" style=\"outline:none; color:#f18300; font-weight:bold; text-decoration:none;\">contact us</a> should you have any questions or need further assistance.<br/><br/>")
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
				.append("  </tr>").append("</table>").append("</body>").append("</html>");

		return content.toString();
	}

	public void sendRegistrationInitiationMail(String to, String subject, String username) {

		try {
			final MimeMessage mimeMessage = mailSender.createMimeMessage();
			final MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
			message.setTo(to);
			message.setSubject(subject);

			message.setText(getRegistrationInitiationContent().replace("${0}", username), true);
			mailSender.send(mimeMessage);
		} catch (Exception e) {
			System.out.println("Mail not sent to user :: " + e);
		}
	}

	private String getRegistrationInitiationContent() {

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
				.append("Greetings from Almulla Exchange!<br/><br/>")
				.append("")
				.append("You received this email because you have successfully Initiated Registration Process.<br/><br/>")
				.append("")
				.append("<strong style=\"color:#333;\">Your user name :</strong> <strong style=\"color:#257b45;\">${0}</strong><br/><br/>")
				.append("")
				.append("Please <a href=\"http://www.almullagroup.com/contact.aspx\" target=\"_blank\" style=\"outline:none; color:#f18300; font-weight:bold; text-decoration:none;\">contact us</a>should you have any questions or need further assistance.<br/><br/>")
				.append("")
				.append("Thanks for registering Process!</td>")
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

	public void sendRegistrationMailtoPartner(ArrayList<AddPartnerDetailBean> partnerList, String to, String subject, String username, String crno, String compnayName) {

		try {
			final MimeMessage mimeMessage = mailSender.createMimeMessage();
			final MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
			message.setTo(to);
			message.setSubject(subject);
			String temp = getPartnerRegistrationContent(partnerList).replace("${0}", crno);
			/* String Output = temp.replace("${1}", username); */
			String Output = temp.replace("${2}", compnayName);
			String finalOutput = Output.replace("${3}", compnayName);
			message.setText(finalOutput, true);
			mailSender.send(mimeMessage);
		} catch (Exception e) {
			System.out.println("Mail not sent to user :: " + e);
		}
	}

	private String getPartnerRegistrationContent(ArrayList<AddPartnerDetailBean> partnerList) {
		StringBuffer content = new StringBuffer();
		/* StringBuffer[] tabella = new StringBuffer[ partnerList.size()]; */
		ArrayList<StringBuffer> tabella = new ArrayList<StringBuffer>();
		for (AddPartnerDetailBean bean : partnerList) {
			StringBuffer partnerContent = new StringBuffer();
			partnerContent.append("<tr>").append("<td>" + bean.getPartName() + "</td>").append("<td>" + bean.getPartnerCustomerId() + "</td>").append("<td>" + bean.getPartnerEmail() + "</td>").append("<td>" + bean.getPartnerContactNumber() + "</td>").append("</tr>");
			tabella.add(partnerContent);
		}
		StringBuffer finalPartnerList = new StringBuffer();
		for (int j = 0; j < tabella.size(); j++) {
			finalPartnerList.append(tabella.get(j));
		}
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
				.append("        <td align=\"left\" valign=\"middle\" style=\"font-family:Arial, Helvetica, sans-serif; font-size:15px; line-height:20px; color:#666; padding:30px;\">Dear Sir,</strong><br/><br/>")
				.append("")
				.append("")
				.append("Greetings from ${2}.<br/><br/>")
				.append("")
				.append("We thank you for having registered with us for your Corporate remittances. We look forward to a long and fruitful association with your Company. We have noted that the following officials are the Authorized signatories on behalf of your Company.<br><br/>")
				.append("")
				.append("<table border=\"1\" cellpadding=\"0\" cellspacing=\"0\" style=\"width: 599px; height: 124px\">")
				.append("<tr align=\"left\" style='background-color:#00a53d;color:#fff;'>")
				.append("<th style='background-color:#00a53d;color:#fff;'><font color=\"#fff\">Name</font></th>")
				.append("<th style='background-color:#00a53d;color:#fff;'><font color=\"#fff\">Customer ID</font></th>")
				.append("<th style='background-color:#00a53d;color:#fff;'><font color=\"#fff\">email</font></th>")
				.append("<th style='background-color:#00a53d;color:#fff;'><font color=\"#fff\">Phone</th></font>")
				.append("</tr>")
				.append(finalPartnerList)
				.append("</table>")
				.append("")
				.append("<br>It would be our pleasure to attend to you remittance needs. Please do not hesitate to call us at <b>1840123</b>. should you require any assistance<br/><br/>")
				.append("")
				.append("Assuring you of our best services.<br><br>Regards,<br><br>${3}</td>")
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

	public void sendComplaintMail(String to, String subject, String customerId, String userName, BigDecimal temp) {

		try {
			final MimeMessage mimeMessage = mailSender.createMimeMessage();
			final MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
			message.setTo(to);
			message.setSubject(subject);

			message.setText((getComplaintCreationContant().replace("${0}", customerId)).replace("${1}", userName).replace("${2}", temp.toString()), true);
			mailSender.send(mimeMessage);
		} catch (Exception e) {
			System.out.println("Mail not sent to user :: " + e);
		}
	}
	
	
	private String getComplaintCreationContant(){

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
				.append("With reference to you remittance transaction receipt number XXXX ,We have raised Complaint and let you know shortly.<br/><br/>")
				.append("")
				.append("<strong style=\"color:#333;\">Remittance Number :</strong> <strong style=\"color:#257b45;\">${0}</strong><br/><br/>")
				.append("")
				.append("<strong style=\"color:#333;\">FC AMOUNT :</strong> <strong style=\"color:#257b45;\">${1}</strong><br/><br/>")
				.append("")
				.append("<strong style=\"color:#333;\">Country :</strong> <strong style=\"color:#257b45;\">${2}</strong><br/><br/>")
				.append("")
				.append("<strong style=\"color:#333;\">Beneficiary :</strong> <strong style=\"color:#257b45;\">${2}</strong><br/><br/>")
				.append("")	
				.append("Please <a href=\"http://www.almullaexchanges.com/\" target=\"_blank\" style=\"outline:none; color:#f18300; font-weight:bold; text-decoration:none;\">contact us</a>Feel free to contact us at (123) 456-7890 any questions or further clarification.<br/><br/>")
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
				.append("  </tr>").append("</table>").append("</body>").append("</html>");

		return content.toString();
	}



	private String getRatePleceOrderInitlizationContent(HashMap<String, String> inputValues) {
		String Name=inputValues.get("Name");
		String beneName=inputValues.get("beneName");
		String bank=inputValues.get("Bank");
		String branchName=inputValues.get("Branch Name");
		String amount=inputValues.get("Amount");
		String createdBy=inputValues.get("Craeted By");
		String createdDate=inputValues.get("Created Date");
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
				.append("        <td align=\"left\" valign=\"middle\" style=\"font-family:Arial, Helvetica, sans-serif; font-size:15px; line-height:20px; color:#666; padding:30px;\">Dear <strong style=\"color:#000000;\">Group Sales Manager</strong> ,<br/><br/>")
				.append("")
				.append("")	
				.append("Good Day.</br></br>")
				.append("One of our privileged customer has requested for a special rate through Place Order. Please, find the details below and do the needful as earliest.<br/><br/>")
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
				.append("S.No")
				.append("</th>")
				
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
				.append("1")
				.append("</td>")
				
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
				.append("AlMulla Group,</br>")
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

	public void sendRateOfferedMailToManager(HashMap<String, String> inputValues) {
		String emailId=inputValues.get("EmailId");
		String RateOffered=inputValues.get("RateOffered");
		String user=inputValues.get("User");
		try {
			final MimeMessage mimeMessage = mailSender.createMimeMessage();
			final MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
			message.setTo(emailId);
			message.setSubject(RateOffered);
			message.setText(getRatePleceOrderInitlizationContent(inputValues).replace("${0}", user), true);
			mailSender.send(mimeMessage);
		} catch (Exception e) {
			System.out.println("Mail not sent to user :: " + e);
		}
		
	}
	
	public void sendRateOfferedMailToCustomer(HashMap<String, String> inputValues) throws Exception  {
		String emailId=inputValues.get("EmailId");
		String RateOffered=inputValues.get("RateOffered");
		String user=inputValues.get("User");
		try {
			final MimeMessage mimeMessage = mailSender.createMimeMessage();
			final MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
			message.setTo(emailId);
			message.setSubject(RateOffered);
			message.setText(getRatePleceOrderInitlizationContentForCustomer(inputValues).replace("${0}", user), true);
			mailSender.send(mimeMessage);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
	}
	
	private String getRatePleceOrderInitlizationContentForCustomer(HashMap<String, String> inputValues) {
		
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
				.append("AlMulla Group,</br>")
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

	public void sendRateOfferedBranchSupportTrnx(HashMap<String, String> inputValues) {
		String emilId=inputValues.get("emailId");
		String user=inputValues.get("User");
		try {
			final MimeMessage mimeMessage = mailSender.createMimeMessage();
			final MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
			message.setTo(emilId);
			message.setSubject("Branch Req Customer Trnx");
			message.setText(getBranchSupportTrnxForCustomer(inputValues).replace("${0}", user), true);
			mailSender.send(mimeMessage);
		} catch (Exception e) {
			System.out.println("Mail not sent to user :: " + e);
		}
		
	}

	private String getBranchSupportTrnxForCustomer(HashMap<String, String> inputValues) {
		String customerUniqueNumber=inputValues.get("customerUniqueNumber");
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
		/*.append(Constants.CUSTOMER_URL)
		.append("URL : <a href=\"http://10.200.4.22:7001/AlmullaExchangeService/almullagroup/almullagroup.xhtml/\" target=\"_blank\" style=\"outline:none; color:#f18300; font-weight:bold; text-decoration:none;\">customerUrl</a></br>")*/
		.append("</br></br>")
		.append("</br></br>")
		.append("<strong style=\"color:#333;\">Application Number</strong> <strong style=\"color:#257b45;\"></strong><br/><br/>")
		.append(customerUniqueNumber)
		.append("</br></br>")
		.append("</br></br>")
		.append("Thanks and Regards,</br>")
		.append("Al Mulla Exchange [AMX],</br>")
		.append("AlMulla Group,</br>")
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

}