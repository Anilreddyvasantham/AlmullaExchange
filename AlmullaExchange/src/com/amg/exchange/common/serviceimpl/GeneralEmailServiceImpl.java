/*package com.amg.exchange.common.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.amg.exchange.common.dao.IGeneralEmailDao;
import com.amg.exchange.common.service.IGeneralEmailService;

@SuppressWarnings("serial")
@Service("generalEmailServiceImpl")
public class GeneralEmailServiceImpl<T> implements java.io.Serializable,IGeneralEmailService{
	
		
	@Autowired
	IGeneralEmailDao generalEmailDao; 

	@Override
	public void sendRegistrationMail(String to, String subject, String username) {
		generalEmailDao.sendRegistrationMail(to, subject, username);
	}
	
}*/