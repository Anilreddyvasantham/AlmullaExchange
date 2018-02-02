package com.amg.exchange.registration.serviceimpl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.registration.dao.IUserSignUpDao;
import com.amg.exchange.registration.model.CustomerLogin;
import com.amg.exchange.registration.service.IUserSignUpService;

@SuppressWarnings("serial")
@Service("userSignUpServiceImpl")
public class IUserSignUpServiceImpl <T> implements IUserSignUpService<T>, Serializable {
	
	@Autowired
	IUserSignUpDao<T> iUserSignDao;

	public IUserSignUpDao<T> getiUserSignDao() {
		return iUserSignDao;
	}

	public void setiUserSignDao(IUserSignUpDao<T> iUserSignDao) {
		this.iUserSignDao = iUserSignDao;
	}

	@Transactional
	@Override
	public List<CustomerLogin> searchUser(String userName) {
		// TODO Auto-generated method stub
		//return getiUserSignDao().searchUser(userName);
		return getiUserSignDao().searchUser(userName);
	}

	@Transactional
	@Override
	public void saveOnlineUsrData(CustomerLogin signup) {
		getiUserSignDao().saveOnlineUsrData(signup);
		
	}
	
	@Transactional
	@Override
	public void updateChangePassword(CustomerLogin customerLogin) {
		getiUserSignDao().updateChangePassword(customerLogin);
		
	}
}
