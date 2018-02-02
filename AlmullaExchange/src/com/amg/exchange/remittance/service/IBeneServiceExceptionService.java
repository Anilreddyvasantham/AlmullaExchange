package com.amg.exchange.remittance.service;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.remittance.model.BeneServiceExceptionSetup;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.BeneCountryService;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.DeliveryModeDesc;
import com.amg.exchange.treasury.model.RemittanceModeDescription;

public interface IBeneServiceExceptionService {
	
	public void saveBeneServiceExceptionSetup(BeneServiceExceptionSetup beneServiceException);
	
	public List<BankBranch> getBankBranchList(BigDecimal countryId,BigDecimal BankId,BigDecimal formBranchCode,BigDecimal toBranchCode);
	
	public boolean getBanKBranchFromBeneExceptionSetup(BigDecimal countryId,BigDecimal bankId,BigDecimal branchId );
	
	public List<BeneServiceExceptionSetup> getBeneServiceExceptionSetupList(BigDecimal countryId,BigDecimal BankId,BigDecimal formBranchCode,BigDecimal toBranchCode,String isActive);
	
	public List<BeneServiceExceptionSetup> getBeneDeleteServiceExceptionSetupList(BigDecimal countryId, BigDecimal BankId, BigDecimal formBranchCode, BigDecimal toBranchCode, String isActive);
	
	public List<BeneCountryService>  getBeneCountryAllServiceList(BigDecimal countryId,String isActive);
	
	public List<DeliveryModeDesc> lstDeliveryMode(BigDecimal deliveryId ,BigDecimal langId,String isActive) ;
	
	public List<RemittanceModeDescription> listRemittanceDesc(BigDecimal remittanceId,BigDecimal langId,String isActive);
	
	public boolean isExistBeneExceptionSetup(BigDecimal remittanceId,BigDecimal deliveryId,BigDecimal bankId,BigDecimal bankBranchId,String isActive);
	
	public void activateRecord(BigDecimal exceptionSetupId, String userName);
	
	public List<BankBranch> getBankbranchlist(BigDecimal bankId);
	
	public List<CurrencyMaster> getCurrencyList(BigDecimal countryId);
	
	public List<RemittanceModeDescription> listRemittanceDesc(BigDecimal remittanceId,BigDecimal langId);
	
	public List<DeliveryModeDesc> lstDeliveryMode(BigDecimal deliveryId ,BigDecimal langId);
	
	public List<BeneServiceExceptionSetup> getExceptionSetupListForActiveInactive();

}
