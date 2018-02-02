package com.amg.exchange.registration.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.amg.exchange.registration.model.IdentityTypeMaster;

public interface IIdentityTypeMasterDao<T> {

	public List<IdentityTypeMaster> view();
	public List<IdentityTypeMaster> getIdentityStatus(BigDecimal countryId, BigDecimal businessComponentId);
	public List<IdentityTypeMaster> getUnApprovedList();
	public void approve(BigDecimal identityTypeMasterPk, String userName, Date currentDate);
	public void delete(BigDecimal identityTypeMasterPk);
	public void softDelete(BigDecimal identityTypeMasterPk, String userName, Date currentDate, String remarks);
	public void activate(BigDecimal identityTypeMasterPk, String userName, Date currentDate);
}
