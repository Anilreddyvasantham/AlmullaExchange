package com.amg.exchange.registration.serviceimpl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.registration.dao.IForgotPasswordDao;
import com.amg.exchange.registration.model.CustomerLogin;
import com.amg.exchange.registration.service.IForgotPasswordService;

@SuppressWarnings("serial")
@Service("forgotPasswordServiceImpl")
public class ForgotPasswordServiceImpl <T> implements IForgotPasswordService<T>, Serializable {
	
	@Autowired
	IForgotPasswordDao<T> forgotPasswordDao;

	public IForgotPasswordDao<T> getForgotPasswordDao() {
		return forgotPasswordDao;
	}

	public void setForgotPasswordDao(IForgotPasswordDao<T> forgotPasswordDao) {
		this.forgotPasswordDao = forgotPasswordDao;
	}

	@Override
	@Transactional
	public List<CustomerLogin> getCustomerDetail(String username) {

		return getForgotPasswordDao().getCustomerDetail(username);
	}

	@Override
	@Transactional
	public List<CustomerLogin> getCustomerDetail(String username, String emailid) {

		return getForgotPasswordDao().getCustomerDetail(username, emailid);
	}

	@Override
	@Transactional
	public void setNewPassword(CustomerLogin customerLogin) {

		getForgotPasswordDao().setNewPassword(customerLogin); 
	}
}
