package com.amg.exchange.registration.daoimpl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.registration.dao.IUserSignUpDao;
import com.amg.exchange.registration.model.CustomerLogin;

@SuppressWarnings("serial")
@Repository
public class UserSignUpDaoImpl <T> extends CommonDaoImpl<T> implements IUserSignUpDao<T>, Serializable{

	@SuppressWarnings("unchecked")
	@Override 
	public List<CustomerLogin> searchUser(String userName) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(CustomerLogin.class);
		dCriteria.add(Restrictions.eq("userName", userName).ignoreCase() );
		return (List<CustomerLogin>)findAll(dCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void saveOnlineUsrData(CustomerLogin signup) {
		save((T) signup );
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateChangePassword(CustomerLogin customerLogin) {
		try{
		update((T) customerLogin );
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
