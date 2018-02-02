package com.amg.exchange.treasury.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankIndicator;
import com.amg.exchange.treasury.model.BankIndicatorDescription;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.util.AMGException;

public interface IBankApplicabilityService<T> {

public 	void saveBankApplicabilityDetails(BankApplicability bankApplicability);
public List<BankIndicator> getBankIndicatorList();
public List<BankIndicatorDescription> getBankIndicatorDescList(BigDecimal languageId);


public List<BankApplicability> getBankApplicability(BigDecimal companyId, BigDecimal countryId, BigDecimal bankTypeId, BigDecimal bankId);

public List<BankMaster> getlist(BigDecimal bankid);

public List<BankApplicability> getBankApplicabilityForView(BigDecimal companyId, BigDecimal countryId,BigDecimal bankId);

public List<CountryMasterDesc> getCountryFromCompany(BigDecimal companyId,BigDecimal languageId);

public List<BankApplicability> getBankApplicabilityApproval(BigDecimal bankApplicabilityId);

public void  activeDeactiveRecord(BigDecimal bankApplicabilityID,String status,String remarks);
public List<BigDecimal> getCorrespondingCountryList(BigDecimal bigDecimal, String cB);
public List<BankApplicability> getBankList(BigDecimal appCountryId, String bankIndicatorCorrBank, BigDecimal countryId);
public List<BankApplicability> getApplicabilityBankList(BigDecimal countryId, String bankIndicatorCorrBank);
public List<BankApplicability> getApplicabilityBankingChennalBankList(BigDecimal countryId, String bankIndicatorAgentBank);
public HashMap<String, String> callPopulateBankApplicability(HashMap<String, String> inputValues) throws AMGException;



}
