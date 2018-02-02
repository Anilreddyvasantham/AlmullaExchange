package com.amg.exchange.registration.serviceimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.registration.dao.IUserCreationDao;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.registration.model.CustomerLogin;
import com.amg.exchange.registration.service.IUserCreationService;

@SuppressWarnings("serial")
@Service("userCreationServiceImpl")
public class UserCreationServiceImpl <T> implements IUserCreationService<T>, Serializable {
	
	@Autowired
	IUserCreationDao<T> iuserCreationDao;
   public IUserCreationDao<T> getIuserCreationDao() {
		return iuserCreationDao;
	}

	public void setIuserCreationDao(IUserCreationDao<T> iuserCreationDao) {
		this.iuserCreationDao = iuserCreationDao;
	}

	@Transactional
	@Override
	public List<CustomerLogin> searchUser(String userName) {
		// TODO Auto-generated method stub
		//return getiUserSignDao().searchUser(userName);
		return getIuserCreationDao().searchUser(userName);
	}

	@Transactional
	@Override
	public void saveOnlineUsrData(CustomerLogin customerLogin) {
		getIuserCreationDao().saveOnlineUsrData(customerLogin);
		
	}
	
	@Transactional
	@Override
	public void updateChangePassword(CustomerLogin customerLogin) {
		getIuserCreationDao().updateChangePassword(customerLogin);
		
	}
	@Transactional
	@Override
	public void updateOnlineUsrData(CustomerLogin customerLogin) {
		getIuserCreationDao().updateOnlineUsrData(customerLogin);
		
	}

	@Override
	@Transactional
	public List<CustomerIdProof> checkIdproof(BigDecimal countryId,String idNumber) {
		return getIuserCreationDao().checkIdproof(countryId,idNumber);
	}
}
