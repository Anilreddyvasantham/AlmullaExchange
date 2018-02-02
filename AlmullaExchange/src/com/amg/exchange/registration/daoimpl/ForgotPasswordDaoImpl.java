package com.amg.exchange.registration.daoimpl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.registration.dao.IForgotPasswordDao;
import com.amg.exchange.registration.model.CustomerLogin;

@SuppressWarnings("serial")
@Repository
public class ForgotPasswordDaoImpl <T> extends CommonDaoImpl<T> implements IForgotPasswordDao<T>, Serializable{

	@Override
	@SuppressWarnings("unchecked")
	public List<CustomerLogin> getCustomerDetail(String username) {

		DetachedCriteria criteria = DetachedCriteria.forClass(CustomerLogin.class, "customerLogin");
		criteria.add(Restrictions.eq("customerLogin.userName", username).ignoreCase());
		
		criteria.setFetchMode("customerLogin.fsBizComponentDataBySecurityQuestion1", FetchMode.JOIN);
		criteria.createAlias("customerLogin.fsBizComponentDataBySecurityQuestion1", "fsBizComponentDataBySecurityQuestion1", JoinType.LEFT_OUTER_JOIN);
		
		criteria.setFetchMode("customerLogin.fsBizComponentDataBySecurityQuestion2", FetchMode.JOIN);
		criteria.createAlias("customerLogin.fsBizComponentDataBySecurityQuestion2", "fsBizComponentDataBySecurityQuestion2", JoinType.LEFT_OUTER_JOIN);
		
		criteria.setFetchMode("customerLogin.fsBizComponentDataBySecurityQuestion3", FetchMode.JOIN);
		criteria.createAlias("customerLogin.fsBizComponentDataBySecurityQuestion3", "fsBizComponentDataBySecurityQuestion3", JoinType.LEFT_OUTER_JOIN);
		
		criteria.setFetchMode("customerLogin.fsBizComponentDataBySecurityQuestion4", FetchMode.JOIN);
		criteria.createAlias("customerLogin.fsBizComponentDataBySecurityQuestion4", "fsBizComponentDataBySecurityQuestion4", JoinType.LEFT_OUTER_JOIN);
		
		criteria.setFetchMode("customerLogin.fsBizComponentDataBySecurityQuestion5", FetchMode.JOIN);
		criteria.createAlias("customerLogin.fsBizComponentDataBySecurityQuestion5", "fsBizComponentDataBySecurityQuestion5", JoinType.LEFT_OUTER_JOIN);
		
		return (List<CustomerLogin>)criteria.getExecutableCriteria(getSession()).list();
	}

	@SuppressWarnings("unchecked") 
	@Override
	public List<CustomerLogin> getCustomerDetail(String username, String emailid) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(CustomerLogin.class, "customerLogin");
		criteria.add(Restrictions.eq("customerLogin.userName", username).ignoreCase());
		criteria.add(Restrictions.eq("customerLogin.email", emailid).ignoreCase());
		
		return (List<CustomerLogin>)criteria.getExecutableCriteria(getSession()).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setNewPassword(CustomerLogin customerLogin) {

		update((T)customerLogin);
	}

}
