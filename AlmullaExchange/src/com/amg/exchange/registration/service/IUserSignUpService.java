package com.amg.exchange.registration.service;

import java.util.List;

import com.amg.exchange.registration.model.CustomerLogin;

public interface IUserSignUpService<T> {
	
	public List<CustomerLogin>  searchUser(String userName);
	public void saveOnlineUsrData(CustomerLogin signup);
	public void updateChangePassword(CustomerLogin customerLogin);
}
