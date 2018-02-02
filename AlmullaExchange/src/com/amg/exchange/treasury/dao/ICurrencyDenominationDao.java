package com.amg.exchange.treasury.dao;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.foreigncurrency.model.CurrencyWiseDenomination;
import com.amg.exchange.treasury.bean.CurrencyDenominationDataTable;
import com.amg.exchange.treasury.model.CurrencyMaster;

public interface ICurrencyDenominationDao {

	public List<CurrencyWiseDenomination> getAllListToDb(BigDecimal countryId,BigDecimal currencyId);

	public List<CurrencyWiseDenomination> getAllValidateBean(BigDecimal countryId, BigDecimal currencyId, BigDecimal denomonationAmount);

	public void save(CurrencyWiseDenomination currencyWiseDenomination);

	public void deleteRecordPermanetly(BigDecimal denominationid);

	public List<CurrencyWiseDenomination> getAllRecordsTofetchDB();

	public String CountryNameList(BigDecimal countryId, BigDecimal languageId);

	public void approveRecord(BigDecimal denominationid, String userName);

	public List<CurrencyMaster> getBasedOnCountyCurrency(BigDecimal countryId);

	public String getAllDenominationDesc(BigDecimal countryId,BigDecimal currencyId);

	public String getAllDenominationcurrencyDesc(BigDecimal countryId, BigDecimal currencyId);

	public List<CurrencyWiseDenomination> getAllRecordsToApproveFromDb(BigDecimal countryId, BigDecimal currencyId);

	public void saveRecorsForApproved(CurrencyWiseDenomination currencyWiseDenomination);

	public void updateApproveRecord(List<BigDecimal> lstApproved,String userName);

	public String approvalAllRecord(List<BigDecimal> lstApproved,String userName);

	
	

	
}
