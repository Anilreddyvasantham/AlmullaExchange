package com.amg.exchange.treasury.dao;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.remittance.model.BeneficaryAccount;
import com.amg.exchange.remittance.model.BeneficaryRelationship;
import com.amg.exchange.treasury.model.BeneCountryService;
import com.amg.exchange.treasury.model.DeliveryModeDesc;
import com.amg.exchange.treasury.model.RemittanceModeDescription;
import com.amg.exchange.treasury.model.ServiceMasterDesc;

public interface IBeneCountryDao<T> {
	public List<CountryMaster> lstCountrs(BigDecimal countryId);

	public List<ServiceMasterDesc> lstServices(BigDecimal languageId);

	public List<RemittanceModeDescription> lstRemitancemode(BigDecimal serviceId, BigDecimal languageId);

	public List<DeliveryModeDesc> lstDeliveryMode(BigDecimal languageId);

	public void save(BeneCountryService beneCountryservice);

	public List<BeneCountryService> validateBene(BigDecimal applId, BigDecimal countryId, BigDecimal currncyId, BigDecimal serviceId, BigDecimal remitanceId, BigDecimal deliveryId);

	public List<BeneCountryService> validateBenedata();

	public String getCurrency(BigDecimal currencyID);

	public String getRemitanceCode(BigDecimal remitanceId);

	public String getDeliveryCode(BigDecimal deliveryId);

	public List<BeneCountryService> viewAllRecords(BigDecimal applId, BigDecimal countryId, BigDecimal currncyId,BigDecimal serviceId);

	public void update(BeneCountryService beneCountryservice);

	public List<BeneCountryService> approvalBeanList(BigDecimal applId);

	public void delete(BeneCountryService benecountryservice);

	public void delete(BigDecimal bnCntryservicepk);
	
	public String approveRecord(BigDecimal bnCntryservicepk, String userName);
	
	public List<BeneficaryAccount> checkDuplicateForCash(BigDecimal masterSeqId,BigDecimal bankCountryId,BigDecimal serviceGroupId, BigDecimal serviceProviderBankId, BigDecimal currencyId, BigDecimal agentBankId,BigDecimal agentBankBranchId);
	
	public List<BeneficaryAccount> checkDuplicateForBankingChannel(BigDecimal masterSeqId,BigDecimal bankCountryId,BigDecimal serviceGroupId, BigDecimal bankId, BigDecimal bankBranchId, String accountNumber, BigDecimal currencyId);
	
	public List<BeneficaryRelationship> checkBenificaryRelationExist(BigDecimal beneficaryMasterSeqId, BigDecimal beneficaryAccountSeqId);
}
