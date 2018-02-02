package com.amg.exchange.registration.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.registration.dao.IUserCreationDao;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.registration.model.CustomerLogin;

@SuppressWarnings("serial")
@Repository
public class UserCreationDaoImpl <T> extends CommonDaoImpl<T> implements IUserCreationDao<T>, Serializable{

	@SuppressWarnings("unchecked")
	@Override 
	public List<CustomerLogin> searchUser(String userName) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(CustomerLogin.class);
		dCriteria.add(Restrictions.eq("userName", userName).ignoreCase() );
		return (List<CustomerLogin>)findAll(dCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void saveOnlineUsrData(CustomerLogin customerLogin) {
		//save((T) customerLogin );
		saveOrUpdate((T) customerLogin );
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

	@SuppressWarnings("unchecked")
	@Override
	public void updateOnlineUsrData(CustomerLogin customerLogin) {
	
		update((T) customerLogin);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerIdProof> checkIdproof(BigDecimal countryId,String idNumber) {
		 DetachedCriteria dCriteria = DetachedCriteria.forClass(CustomerIdProof.class, "customerIdProof");
		 
		    dCriteria.setFetchMode("customerIdProof.fsCustomer", FetchMode.JOIN);
			dCriteria.createAlias("customerIdProof.fsCustomer", "fsCustomer", JoinType.INNER_JOIN);
			
	        dCriteria.setFetchMode("customerIdProof.fsBizComponentDataByIdentityTypeId", FetchMode.JOIN);
			dCriteria.createAlias("customerIdProof.fsBizComponentDataByIdentityTypeId", "fsBizComponentDataByIdentityTypeId", JoinType.INNER_JOIN);
			
			dCriteria.add(Restrictions.eq("customerIdProof.identityInt", idNumber));
			
			dCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			
			return (List<CustomerIdProof>) findAll(dCriteria);
	}
}
