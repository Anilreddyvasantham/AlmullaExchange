package com.amg.exchange.remittance.dao;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.registration.model.RoleMaster;
import com.amg.exchange.remittance.model.CashLimit;
import com.amg.exchange.remittance.model.CashLimitType;

public interface ICashLimitDao {
	void save(CashLimit cashLimit);

	List<CashLimitType> getCashLimitType();

	List<CashLimit> populateDetails(BigDecimal countryId, BigDecimal limitId);

	public List<CashLimit> viewbasedOnCountry(BigDecimal countryId);

	List<RoleMaster> getRoleMasterList();
}
