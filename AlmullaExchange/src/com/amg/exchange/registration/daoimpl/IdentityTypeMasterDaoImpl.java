package com.amg.exchange.registration.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.registration.dao.IIdentityTypeMasterDao;
import com.amg.exchange.registration.model.IdentityTypeMaster;
import com.amg.exchange.util.Constants;

@SuppressWarnings("serial")
@Repository
public class IdentityTypeMasterDaoImpl<T> extends CommonDaoImpl<T> implements IIdentityTypeMasterDao<T>, Serializable {

	@SuppressWarnings("unchecked")
	@Override
	public List<IdentityTypeMaster> view() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(IdentityTypeMaster.class, "identityTypeMaster");
		
		return (List<IdentityTypeMaster>) findAll(detachedCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IdentityTypeMaster> getIdentityStatus(BigDecimal countryId,
			BigDecimal businessComponentId) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(IdentityTypeMaster.class, "identityTypeMaster");
		
		detachedCriteria.add(Restrictions.eq("applicationCountryId", countryId));
		detachedCriteria.add(Restrictions.eq("businessComponentId", businessComponentId));
	
		return (List<IdentityTypeMaster>) findAll(detachedCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IdentityTypeMaster> getUnApprovedList() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(IdentityTypeMaster.class, "identityTypeMaster");
		detachedCriteria.add(Restrictions.eq("identityTypeMaster.isActive",Constants.U));
		return (List<IdentityTypeMaster>) findAll(detachedCriteria);
	}

	@Override
	public void approve(BigDecimal identityTypeMasterPk, String userName,Date currentDate) {
		IdentityTypeMaster identityMaster=(IdentityTypeMaster) getSession().get(IdentityTypeMaster.class, identityTypeMasterPk);
		identityMaster.setIsActive(Constants.Yes);
		identityMaster.setApprovBy(userName);
		identityMaster.setApprovDate(currentDate);
		identityMaster.setRemarks(null);
		getSession().update(identityMaster);
		
	}

	@Override
	public void delete(BigDecimal identityTypeMasterPk) {
		IdentityTypeMaster identityMaster=(IdentityTypeMaster) getSession().get(IdentityTypeMaster.class, identityTypeMasterPk);
        getSession().delete(identityMaster);
		
	}

	@Override
	public void softDelete(BigDecimal identityTypeMasterPk, String userName,
			Date currentDate, String remarks) {
		IdentityTypeMaster identityMaster=(IdentityTypeMaster) getSession().get(IdentityTypeMaster.class, identityTypeMasterPk);
		identityMaster.setIsActive(Constants.D);
		identityMaster.setApprovBy(null);
		identityMaster.setApprovDate(null);
		identityMaster.setModifiedBy(userName);
		identityMaster.setModifiedDate(currentDate);
		identityMaster.setRemarks(remarks);
		getSession().update(identityMaster);
		
	}

	@Override
	public void activate(BigDecimal identityTypeMasterPk, String userName,
			Date currentDate) {
		IdentityTypeMaster identityMaster=(IdentityTypeMaster) getSession().get(IdentityTypeMaster.class, identityTypeMasterPk);
		identityMaster.setIsActive(Constants.U);
		identityMaster.setApprovBy(null);
		identityMaster.setApprovDate(null);
		identityMaster.setRemarks(null);
		identityMaster.setModifiedBy(userName);
		identityMaster.setModifiedDate(currentDate);
		getSession().update(identityMaster);
		
	}


}
