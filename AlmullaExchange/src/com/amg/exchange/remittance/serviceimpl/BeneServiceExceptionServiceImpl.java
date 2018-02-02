package com.amg.exchange.remittance.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.remittance.dao.IBeneServiceExceptionDao;
import com.amg.exchange.remittance.model.BeneServiceExceptionSetup;
import com.amg.exchange.remittance.service.IBeneServiceExceptionService;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.BeneCountryService;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.DeliveryModeDesc;
import com.amg.exchange.treasury.model.RemittanceModeDescription;
@SuppressWarnings("serial")
@Service("beneServiceExceptionServiceImpl")
public class BeneServiceExceptionServiceImpl implements IBeneServiceExceptionService {
	
	@Autowired
	IBeneServiceExceptionDao beneServiceExceptionDao;

	public BeneServiceExceptionServiceImpl() {
		// TODO Auto-generated constructor stub
	}
	

	public IBeneServiceExceptionDao getBeneServiceExceptionDao() {
		return beneServiceExceptionDao;
	}


	public void setBeneServiceExceptionDao(IBeneServiceExceptionDao beneServiceExceptionDao) {
		this.beneServiceExceptionDao = beneServiceExceptionDao;
	}
	@Override
	@Transactional
	public void saveBeneServiceExceptionSetup(BeneServiceExceptionSetup beneServiceException){
		getBeneServiceExceptionDao().saveBeneServiceExceptionSetup(beneServiceException);
		
	}

	@Override
	@Transactional
	public List<BankBranch> getBankBranchList(BigDecimal countryId,BigDecimal BankId,BigDecimal formBranchCode,BigDecimal toBranchCode){
		return getBeneServiceExceptionDao().getBankBranchList(countryId, BankId, formBranchCode, toBranchCode);
	}
	
	@Override
	@Transactional
	public boolean getBanKBranchFromBeneExceptionSetup(BigDecimal countryId,BigDecimal bankId,BigDecimal branchId ){
		return getBeneServiceExceptionDao().getBanKBranchFromBeneExceptionSetup(countryId, bankId, branchId);
	}
	
	@Override
	@Transactional
	public List<BeneServiceExceptionSetup> getBeneServiceExceptionSetupList(BigDecimal countryId,BigDecimal BankId,BigDecimal formBranchCode,BigDecimal toBranchCode,String isActive){
		return getBeneServiceExceptionDao().getBeneServiceExceptionSetupList(countryId, BankId, formBranchCode, toBranchCode,isActive);
	}
	
	@Override
	@Transactional
	public List<BeneServiceExceptionSetup> getBeneDeleteServiceExceptionSetupList(BigDecimal countryId,BigDecimal BankId,BigDecimal formBranchCode,BigDecimal toBranchCode,String isActive){
		return getBeneServiceExceptionDao().getBeneDeleteServiceExceptionSetupList(countryId,BankId,formBranchCode,toBranchCode,isActive);
	}
	
	@Override
	@Transactional
	public List<BeneCountryService>  getBeneCountryAllServiceList(BigDecimal countryId , String isActive){
		return getBeneServiceExceptionDao().getBeneCountryAllServiceList(countryId, isActive);
	}
	@Override
	@Transactional
	public List<DeliveryModeDesc> lstDeliveryMode(BigDecimal deliveryId ,BigDecimal langId,String isActive) {
		return getBeneServiceExceptionDao().lstDeliveryMode(deliveryId, langId,isActive);
	}
	@Override
	@Transactional
	public List<RemittanceModeDescription> listRemittanceDesc(BigDecimal remittanceId,BigDecimal langId,String isActive){
		return getBeneServiceExceptionDao().listRemittanceDesc(remittanceId,langId,isActive);
	}
	@Override
	@Transactional
	public boolean isExistBeneExceptionSetup(BigDecimal remittanceId,BigDecimal deliveryId,BigDecimal bankId,BigDecimal bankBranchId,String isActive){
		return getBeneServiceExceptionDao().isExistBeneExceptionSetup(remittanceId, deliveryId, bankId, bankBranchId,isActive);
	}
	@Override
	@Transactional
	public void activateRecord(BigDecimal exceptionSetupId, String userName){
		getBeneServiceExceptionDao().activateRecord(exceptionSetupId, userName);
		
	}
	@Override
	@Transactional
	public List<BankBranch> getBankbranchlist(BigDecimal bankId){
		return getBeneServiceExceptionDao().getBankbranchlist(bankId);
	}
	@Override
	@Transactional
	public List<CurrencyMaster> getCurrencyList(BigDecimal countryId){
		return getBeneServiceExceptionDao().getCurrencyList(countryId);
	}
	@Override
	@Transactional
	public List<RemittanceModeDescription> listRemittanceDesc(BigDecimal remittanceId,BigDecimal langId){
		return getBeneServiceExceptionDao().listRemittanceDesc(remittanceId, langId);
				
	}
	@Override
	@Transactional
	public List<DeliveryModeDesc> lstDeliveryMode(BigDecimal deliveryId ,BigDecimal langId){
		return getBeneServiceExceptionDao().lstDeliveryMode(deliveryId, langId);
	}
	@Override
	@Transactional
	public List<BeneServiceExceptionSetup> getExceptionSetupListForActiveInactive(){
		return getBeneServiceExceptionDao().getExceptionSetupListForActiveInactive();
	}
}
