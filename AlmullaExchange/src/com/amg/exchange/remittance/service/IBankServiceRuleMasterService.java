package com.amg.exchange.remittance.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import com.amg.exchange.remittance.model.BankCharges;
import com.amg.exchange.remittance.model.BankChargesMasterLog;
import com.amg.exchange.remittance.model.BankServiceRule;
import com.amg.exchange.remittance.model.TransferMode;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.DeliveryModeDesc;
import com.amg.exchange.treasury.model.RemittanceModeDescription;

public interface IBankServiceRuleMasterService {

	public void saveBankRule(BankServiceRule bankServiceRule);
	
	public void saveBankCharges(BankCharges bankCharges);

	public List<CurrencyMaster> checkCurrencyCodeExist(String currencyCode,BigDecimal countryId);

	public List<RemittanceModeDescription> getRemittance(BigDecimal appCountryID,BigDecimal countryId ,BigDecimal languageId);

	public List<DeliveryModeDesc> getDeliverFromRemittance(BigDecimal appCountryID,BigDecimal countryId,BigDecimal remittanceId,BigDecimal languageId);

	public List<BankServiceRule> getBankServiceRule(BigDecimal countryId,BigDecimal currencyId,BigDecimal bankId,BigDecimal remittanceId,BigDecimal deliverId,String isActive);

	public List<TransferMode> getTransferMode();
	
	public List<BankCharges> getBankCharges(BigDecimal bankServiceRuleId);

	public List<BankCharges> getBankChargesApprove(BigDecimal bankServiceRuleId);

	public List<BankCharges> getBankChargesForAllRecords(BigDecimal bankServiceRuleId);

	public void saveApproveList(List<BigDecimal> lstBnkCrgsApprove);
	
	public List<BankServiceRule> getBankServiceRuleEnqryFrmDB(BigDecimal countryId,BigDecimal currencyId,BigDecimal bankId);
	
	public List<BankCharges> getBankChargesEnqry(BigDecimal bankServiceRuleId);
	
	public void saveApproveList(List<BigDecimal> lstBnkCrgsApprove,String userName,BigDecimal bankServiceRuleId);
	
	public boolean saveAllDetails(HashMap<String, Object> saveAllMap)throws Exception;
	
	public void deleteRecord(BigDecimal bankServicePk);
	
	public void deleteRecordsFrmDB(BigDecimal ChargesPk);
	
	public void saveBankChargesLog(BankChargesMasterLog bankChargesLogObj);
	
	public List<DeliveryModeDesc> getDeliveryFromRemittanceAndService(BigDecimal appCountryID,BigDecimal countryId,BigDecimal remittanceId,BigDecimal languageId,BigDecimal serviceId);


}
