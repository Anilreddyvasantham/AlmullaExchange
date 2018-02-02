/*package com.amg.exchange.common.daoimpl;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.amg.exchange.common.dao.IGeneralEmailDao;

public class GeneralEmailDaoImpl implements IGeneralEmailDao {

	@Autowired
	JavaMailSender javaMailSender;

	@Autowired
	IGeneralEmailDao generalEmailDao;

	public void sendRegistrationMail(String to, String subject, String username) {

		try {
			final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			final MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
			message.setTo(to);
			message.setSubject(subject);
			// message.setText(getRegistrationContent().replace("${0}",
			// username), true);
			javaMailSender.send(mimeMessage);
		} catch (Exception e) {
			System.out.println("Mail not sent to user :: " + e);
		}
	}

}
*/