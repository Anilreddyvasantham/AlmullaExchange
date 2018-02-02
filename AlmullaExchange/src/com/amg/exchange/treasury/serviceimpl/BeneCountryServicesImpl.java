package com.amg.exchange.treasury.serviceimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.remittance.model.BeneficaryAccount;
import com.amg.exchange.remittance.model.BeneficaryRelationship;
import com.amg.exchange.treasury.dao.IBeneCountryDao;
import com.amg.exchange.treasury.model.BeneCountryService;
import com.amg.exchange.treasury.model.DeliveryModeDesc;
import com.amg.exchange.treasury.model.RemittanceModeDescription;
import com.amg.exchange.treasury.model.ServiceMasterDesc;
import com.amg.exchange.treasury.service.IBeneCountryServices;

@SuppressWarnings("serial")
@Service("benecountryserviceimpl")
public class BeneCountryServicesImpl<T> implements IBeneCountryServices<T>, Serializable {
	@Autowired
	IBeneCountryDao<T> iBeneCountryDao;

	@Transactional
	@Override
	public List<CountryMaster> lstCountrs(BigDecimal countryId) {
		return iBeneCountryDao.lstCountrs(countryId);
	}

	@Transactional
	@Override
	public List<ServiceMasterDesc> lstServices(BigDecimal languageId) {
		return iBeneCountryDao.lstServices(languageId);
	}

	@Transactional
	@Override
	public List<RemittanceModeDescription> lstRemitancemode(BigDecimal serviceId, BigDecimal languageId) {
		return iBeneCountryDao.lstRemitancemode(serviceId, languageId);
	}

	@Transactional
	@Override
	public List<DeliveryModeDesc> lstDeliveryMode(BigDecimal languageId) {
		return iBeneCountryDao.lstDeliveryMode(languageId);
	}

	@Transactional
	@Override
	public void save(BeneCountryService benecountryservice) {
		iBeneCountryDao.save(benecountryservice);
	}

	@Transactional
	@Override
	public List<BeneCountryService> validateBene(BigDecimal applId, BigDecimal countryId, BigDecimal currncyId, BigDecimal serviceId, BigDecimal remitanceId, BigDecimal deliveryId) {
		return iBeneCountryDao.validateBene(applId, countryId, currncyId, serviceId, remitanceId, deliveryId);
	}

	@Transactional
	@Override
	public List<BeneCountryService> validateBenedata() {
		return iBeneCountryDao.validateBenedata();
	}

	@Transactional
	@Override
	public String getCurrency(BigDecimal currencyId) {
		return iBeneCountryDao.getCurrency(currencyId);
	}

	@Transactional
	@Override
	public String getRemitanceCode(BigDecimal remitanceId) {
		return iBeneCountryDao.getRemitanceCode(remitanceId);
	}

	@Transactional
	@Override
	public String getDeliveryCode(BigDecimal deliveryId) {
		return iBeneCountryDao.getDeliveryCode(deliveryId);
	}

	@Override
	@Transactional
	public List<BeneCountryService> viewAllRecords(BigDecimal applId, BigDecimal countryId, BigDecimal currncyId,BigDecimal serviceId) {
		return iBeneCountryDao.viewAllRecords(applId, countryId, currncyId,serviceId);
	}

	@Override
	@Transactional
	public void update(BeneCountryService benecountryservice) {
		iBeneCountryDao.update(benecountryservice);
	}

	@Override
	@Transactional
	public List<BeneCountryService> approvalBeanList(BigDecimal applId) {
		return iBeneCountryDao.approvalBeanList(applId);
	}

	@Transactional
	@Override
	public void delete(BeneCountryService benecountryservice) {
		iBeneCountryDao.delete(benecountryservice);
	}
	
	@Transactional
	@Override
	public void delete(BigDecimal bnCntryservicepk) {
		iBeneCountryDao.delete(bnCntryservicepk);
		
	}

	@Override
	@Transactional
	public String approveRecord(BigDecimal bnCntryservicepk, String userName) {
		 
		return iBeneCountryDao.approveRecord(bnCntryservicepk,userName);
	}

	@Override
	@Transactional
	public List<BeneficaryAccount> checkDuplicateForCash(BigDecimal masterSeqId,BigDecimal bankCountryId,
			BigDecimal serviceGroupId, BigDecimal serviceProviderBankId,
			BigDecimal currencyId, BigDecimal agentBankId,
			BigDecimal agentBankBranchId) {
		return iBeneCountryDao.checkDuplicateForCash(masterSeqId,bankCountryId, serviceGroupId, serviceProviderBankId, currencyId, agentBankId, agentBankBranchId);
	}

	@Override
	@Transactional
	public List<BeneficaryAccount> checkDuplicateForBankingChannel(BigDecimal masterSeqId,BigDecimal bankCountryId,BigDecimal serviceGroupId, BigDecimal bankId, BigDecimal bankBranchId,String accountNumber, BigDecimal currencyId) {
		return iBeneCountryDao.checkDuplicateForBankingChannel(masterSeqId,bankCountryId, serviceGroupId, bankId, bankBranchId, accountNumber, currencyId);
	}

	@Override
	@Transactional
	public List<BeneficaryRelationship> checkBenificaryRelationExist(BigDecimal beneficaryMasterSeqId, BigDecimal beneficaryAccountSeqId) {
		return iBeneCountryDao.checkBenificaryRelationExist(beneficaryMasterSeqId, beneficaryAccountSeqId);
	}

	
	
}
