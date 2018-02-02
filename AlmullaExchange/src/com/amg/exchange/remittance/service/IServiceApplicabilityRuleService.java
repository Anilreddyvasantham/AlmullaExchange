package com.amg.exchange.remittance.service;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.remittance.model.ServiceApplicabilityRule;
import com.amg.exchange.remittance.model.ViewBankRuleAppField;
import com.amg.exchange.util.AMGException;

public interface IServiceApplicabilityRuleService {

	public void saveApplicability(ServiceApplicabilityRule serviceApplicabilityRule);

	public List<ServiceApplicabilityRule> searchrecord(BigDecimal appCountryId,BigDecimal country,BigDecimal currency,BigDecimal remittance,BigDecimal delivery);

	public List<ViewBankRuleAppField> getViewData();

	public List<ServiceApplicabilityRule> getViewDataServiceAppRule(BigDecimal appCountryId,BigDecimal countryId, BigDecimal currencyId, BigDecimal remitanceId, BigDecimal deliveryId);

	public List<ServiceApplicabilityRule> getRecordsForApproval(BigDecimal applicationCountryId);

	public List<ServiceApplicabilityRule> getRecordFromApproval(BigDecimal serviceAppRuleId);

	public List<ServiceApplicabilityRule> getCopyRecord(BigDecimal appCountryId,BigDecimal remitanceId, BigDecimal deliveryId);

	public List<ServiceApplicabilityRule> getRecord(BigDecimal appCountryId,BigDecimal country,BigDecimal currency,BigDecimal remittance,BigDecimal delivery);

	public List<ServiceApplicabilityRule> getEnqDataServiceAppRule(BigDecimal appCountryId, BigDecimal countryId, BigDecimal currencyId, BigDecimal remittanceModeId, BigDecimal deliveryId);

	public List<ServiceApplicabilityRule> getFetchReCordFormDB(BigDecimal appCountryId, BigDecimal countryId, BigDecimal currencyId,BigDecimal remittanceModeId, BigDecimal deliveryId);

	public List<ServiceApplicabilityRule> getAllRecordsToFetchFromDb(BigDecimal appCountryId, BigDecimal countryId, BigDecimal currencyId,BigDecimal remittanceModeId, BigDecimal deliveryId);

	public void approvedAllRecords(ServiceApplicabilityRule serviceApplicabilityRule);

	public void updateApprovalRecords(List<BigDecimal> lstApproved,String userName);
	
	public List<ServiceApplicabilityRule> getAllStoringTocheckDate(String navigable,String fielType, String validation, String mandatory);

	public List<ServiceApplicabilityRule> getAllStoringTocheckDate();

	public String updateApproveRecords(List<BigDecimal> lstApproved,String userName);

	public String checkServiceApproveMultiUser(BigDecimal serviceApplicabilityRuleId, String userName);

	public List isCombinationExist(BigDecimal deliveryId, BigDecimal remittanceModeId, BigDecimal countryId);

	public List<Object> fetchrecordsforDataTable(BigDecimal countryId, BigDecimal applicationCountryId, BigDecimal currencyId, BigDecimal delivaryId, BigDecimal remittanceModeId);

	public String toFetchLanguageName(BigDecimal languageId);
	
	public void saveServiceApplicabilityRuleList(List<ServiceApplicabilityRule> serviceApplicabilityRule) throws AMGException ;

}
