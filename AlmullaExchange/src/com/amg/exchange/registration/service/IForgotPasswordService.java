package com.amg.exchange.registration.service;

import java.util.List;

import com.amg.exchange.registration.model.CustomerLogin;


public interface IForgotPasswordService<T> {
	
	public List<CustomerLogin> getCustomerDetail(String username);
	public List<CustomerLogin> getCustomerDetail(String username, String emailid);
	public void setNewPassword(CustomerLogin customerLogin);
}
