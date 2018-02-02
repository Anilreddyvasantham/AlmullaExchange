package com.amg.exchange.registration.service;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.registration.model.CustomerLogin;

public interface IUserCreationService<T> {
	
	public List<CustomerLogin>  searchUser(String userName);
	public void saveOnlineUsrData(CustomerLogin customerLogin);
	public void updateChangePassword(CustomerLogin customerLogin);
	public void updateOnlineUsrData(CustomerLogin customerLogin);
	public List<CustomerIdProof> checkIdproof(BigDecimal countryId,String idNumber);
}
