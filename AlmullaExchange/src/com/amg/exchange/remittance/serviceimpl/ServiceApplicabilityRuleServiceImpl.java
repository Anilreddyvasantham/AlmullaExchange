package com.amg.exchange.remittance.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.remittance.dao.IServiceApplicabilityRuleDao;
import com.amg.exchange.remittance.model.ServiceApplicabilityRule;
import com.amg.exchange.remittance.model.ViewBankRuleAppField;
import com.amg.exchange.remittance.service.IServiceApplicabilityRuleService;
import com.amg.exchange.util.AMGException;
@Service("serviceApplicabilityRules")
public class ServiceApplicabilityRuleServiceImpl implements IServiceApplicabilityRuleService{

	@Autowired
	IServiceApplicabilityRuleDao iserviceApplicabilityRuleDao;

	@Transactional
	@Override
	public void saveApplicability(
			ServiceApplicabilityRule serviceApplicabilityRule) {
		iserviceApplicabilityRuleDao.saveApplicability(serviceApplicabilityRule);
	}

	@Transactional
	@Override
	public List<ServiceApplicabilityRule> searchrecord(BigDecimal appCountryId,BigDecimal country,BigDecimal currency,BigDecimal remittance,BigDecimal delivery) {
		return iserviceApplicabilityRuleDao.searchrecord(appCountryId, country, currency, remittance, delivery);
	}

	@Transactional
	@Override
	public List<ViewBankRuleAppField> getViewData() {
		return iserviceApplicabilityRuleDao.getViewData();
	}

	@Transactional
	@Override
	public List<ServiceApplicabilityRule> getViewDataServiceAppRule(BigDecimal appCountryId,BigDecimal countryId, BigDecimal currencyId, BigDecimal remitanceId, BigDecimal deliveryId) {
		return iserviceApplicabilityRuleDao.getViewDataServiceAppRule(appCountryId,countryId, currencyId, remitanceId, deliveryId);
	}

	@Transactional
	@Override
	public List<ServiceApplicabilityRule> getRecordsForApproval(BigDecimal applicationCountryId) {

		return iserviceApplicabilityRuleDao.getRecordsForApproval(applicationCountryId);
	}

	@Override
	@Transactional
	public List<ServiceApplicabilityRule> getRecordFromApproval(
			BigDecimal serviceAppRuleId) {
		return iserviceApplicabilityRuleDao.getRecordFromApproval(serviceAppRuleId);
	}

	@Override
	@Transactional
	public List<ServiceApplicabilityRule> getCopyRecord(BigDecimal appCountryId,BigDecimal remitanceId,
			BigDecimal deliveryId) {
		return iserviceApplicabilityRuleDao.getCopyRecord(appCountryId, remitanceId, deliveryId);
	}

	@Override
	@Transactional
	public List<ServiceApplicabilityRule> getRecord(BigDecimal appCountryId,
			BigDecimal country, BigDecimal currency, BigDecimal remittance,
			BigDecimal delivery) {
		return iserviceApplicabilityRuleDao.getRecord(appCountryId, country, currency, remittance, delivery);
	}

	@Override
	@Transactional
	public List<ServiceApplicabilityRule> getEnqDataServiceAppRule(BigDecimal appCountryId, BigDecimal countryId, BigDecimal currencyId, BigDecimal remitanceId, BigDecimal deliveryId) {
		return iserviceApplicabilityRuleDao.getEnqDataServiceAppRule(appCountryId,countryId, currencyId, remitanceId, deliveryId);
	}

	@Override
	@Transactional
	public List<ServiceApplicabilityRule> getFetchReCordFormDB(BigDecimal appCountryId, BigDecimal countryId,BigDecimal currencyId, BigDecimal remittanceModeId,BigDecimal deliveryId) {
		return iserviceApplicabilityRuleDao.getFetchReCordFormDB(appCountryId,countryId,currencyId,remittanceModeId,deliveryId);
	}

	@Override
	@Transactional
	public List<ServiceApplicabilityRule> getAllRecordsToFetchFromDb(BigDecimal appCountryId, BigDecimal countryId,BigDecimal currencyId, BigDecimal remittanceModeId,BigDecimal deliveryId) {
		return iserviceApplicabilityRuleDao.getAllRecordsToFetchFromDb(appCountryId,countryId,currencyId,remittanceModeId,deliveryId);
	}

	@Override
	@Transactional
	public void approvedAllRecords(ServiceApplicabilityRule serviceApplicabilityRule) {
		iserviceApplicabilityRuleDao.approvedAllRecords(serviceApplicabilityRule);
	}

	@Override
	@Transactional
	public void updateApprovalRecords(List<BigDecimal> lstApproved,String userName) {
		iserviceApplicabilityRuleDao.updateApprovalRecords(lstApproved,userName);
	}

	@Override
	@Transactional
	public List<ServiceApplicabilityRule> getAllStoringTocheckDate(String navigable,String fielType, String validation, String mandatory) {
		return iserviceApplicabilityRuleDao.getAllStoringTocheckDate(navigable,fielType,validation,mandatory);
	}

	@Override
	@Transactional
	public List<ServiceApplicabilityRule> getAllStoringTocheckDate() {
		return iserviceApplicabilityRuleDao.getAllStoringTocheckDate();
	}

	@Override
	@Transactional
	public String updateApproveRecords(List<BigDecimal> lstApproved,String userName) {
		return iserviceApplicabilityRuleDao.updateApproveRecords(lstApproved,userName);
	}

	@Override
	@Transactional
	public String checkServiceApproveMultiUser(BigDecimal serviceApplicabilityRuleId, String userName) {
		return iserviceApplicabilityRuleDao.checkServiceApproveMultiUser(serviceApplicabilityRuleId,userName);
	}

	@Override
	@Transactional
	public List<Object> isCombinationExist(BigDecimal deliveryId, BigDecimal remittanceModeId, BigDecimal countryId) {
		return iserviceApplicabilityRuleDao.isCombinationExist(deliveryId,remittanceModeId,countryId);
	}

	@Override
	@Transactional
	public List<Object> fetchrecordsforDataTable(BigDecimal countryId, BigDecimal applicationCountryId, BigDecimal currencyId, BigDecimal delivaryId,BigDecimal remittenceId) {
		return iserviceApplicabilityRuleDao.fetchrecordsforDataTable(countryId,applicationCountryId,currencyId,delivaryId,remittenceId);
	}

	@Override
	@Transactional
	public String toFetchLanguageName(BigDecimal languageId) {
		return iserviceApplicabilityRuleDao.toFetchLanguageName(languageId);
	}

	@Override
	@Transactional(rollbackFor = AMGException.class)
	public void saveServiceApplicabilityRuleList(
			List<ServiceApplicabilityRule> serviceApplicabilityRule) throws AMGException {
		iserviceApplicabilityRuleDao.saveServiceApplicabilityRuleList(serviceApplicabilityRule);
	}	

}
