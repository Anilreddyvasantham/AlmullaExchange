package com.amg.exchange.registration.serviceimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.registration.dao.IIdentityTypeMasterDao;
import com.amg.exchange.registration.model.IdentityTypeMaster;
import com.amg.exchange.registration.service.IIdentityTypeMasterService;

@SuppressWarnings("serial")
@Service("idenityTypeMasterServiceImpl")
public class IdentityTypeMasterServiceImpl<T> implements IIdentityTypeMasterService<T>, Serializable{

	@Autowired
	IIdentityTypeMasterDao<T> identityTypeMasterDao;
	
	@Override
	@Transactional
	public List<IdentityTypeMaster> view() {
		return identityTypeMasterDao.view();
	}

	@Override
	@Transactional
	public List<IdentityTypeMaster> getIdentityStatus(BigDecimal countryId,BigDecimal businessComponentId) {
		return identityTypeMasterDao.getIdentityStatus(countryId, businessComponentId);
	}

	@Override
	@Transactional
	public List<IdentityTypeMaster> getUnApprovedList() {
		return identityTypeMasterDao.getUnApprovedList();
	}


	@Override
	@Transactional
	public void approve(BigDecimal identityTypeMasterPk, String userName,
			Date currentDate) {
	identityTypeMasterDao.approve(identityTypeMasterPk, userName, currentDate);
		
	}

	@Override
	@Transactional
	public void delete(BigDecimal identityTypeMasterPk) {
		identityTypeMasterDao.delete(identityTypeMasterPk);
		
	}

	@Override
	@Transactional
	public void softDelete(BigDecimal identityTypeMasterPk, String userName, Date currentDate, String remarks) {
		identityTypeMasterDao.softDelete(identityTypeMasterPk, userName, currentDate, remarks);
		
	}

	@Override
	@Transactional
	public void activate(BigDecimal identityTypeMasterPk, String userName,
			Date currentDate) {
		identityTypeMasterDao.activate(identityTypeMasterPk, userName, currentDate);
		
	}

}
