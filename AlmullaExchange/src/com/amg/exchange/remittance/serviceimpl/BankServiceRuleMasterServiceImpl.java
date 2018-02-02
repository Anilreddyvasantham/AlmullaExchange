package com.amg.exchange.remittance.serviceimpl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.remittance.dao.IBankServiceRuleMasterDao;
import com.amg.exchange.remittance.model.BankCharges;
import com.amg.exchange.remittance.model.BankChargesMasterLog;
import com.amg.exchange.remittance.model.BankServiceRule;
import com.amg.exchange.remittance.model.TransferMode;
import com.amg.exchange.remittance.service.IBankServiceRuleMasterService;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.DeliveryModeDesc;
import com.amg.exchange.treasury.model.RemittanceModeDescription;
@Service("bankServiceRulesMaster")
public class BankServiceRuleMasterServiceImpl implements IBankServiceRuleMasterService{
	@Autowired
	IBankServiceRuleMasterDao ibankServiceRuleDao;

	@Transactional
	@Override
	public void saveBankRule(BankServiceRule bankServiceRule) {
		ibankServiceRuleDao.saveBankRule(bankServiceRule);

	}

	@Transactional
	@Override
	public void saveBankCharges(BankCharges bankCharges) {
		ibankServiceRuleDao.saveBankCharges(bankCharges);

	}

	@Override
	@Transactional
	public List<CurrencyMaster> checkCurrencyCodeExist(String currencyCode,BigDecimal countryId) {

		return ibankServiceRuleDao.checkCurrencyCodeExist(currencyCode,countryId);
	}

	@Override
	@Transactional
	public List<RemittanceModeDescription> getRemittance(BigDecimal appCountryID,
			BigDecimal countryId,BigDecimal languageId) {

		return ibankServiceRuleDao.getRemittance(appCountryID, countryId,languageId);
	}

	@Override
	@Transactional
	public List<DeliveryModeDesc> getDeliverFromRemittance(
			BigDecimal appCountryID, BigDecimal countryId,
			BigDecimal remittanceId,BigDecimal languageId) {

		return ibankServiceRuleDao.getDeliverFromRemittance(appCountryID, countryId, remittanceId,languageId);
	}

	@Override
	@Transactional
	public List<BankServiceRule> getBankServiceRule(BigDecimal countryId,BigDecimal currencyId,BigDecimal bankId,BigDecimal remittanceId,BigDecimal deliverId,String isActive) {
		return ibankServiceRuleDao.getBankServiceRule(countryId, currencyId, bankId, remittanceId, deliverId, isActive);
	}

	@Override
	@Transactional
	public List<TransferMode> getTransferMode() {

		return ibankServiceRuleDao.getTransferMode();
	}

	@Override
	@Transactional
	public List<BankCharges> getBankCharges(BigDecimal bankServiceRuleId) {
		// TODO Auto-generated method stub
		return ibankServiceRuleDao.getBankCharges(bankServiceRuleId);
	}

	@Override
	@Transactional
	public List<BankCharges> getBankChargesApprove(BigDecimal bankServiceRuleId) {
		// TODO Auto-generated method stub
		return ibankServiceRuleDao.getBankChargesApprove(bankServiceRuleId);
	}

	@Override
	@Transactional
	public List<BankCharges> getBankChargesForAllRecords(
			BigDecimal bankServiceRuleId) {

		return ibankServiceRuleDao.getBankChargesForAllRecords(bankServiceRuleId);
	}

	@Override
	@Transactional
	public void saveApproveList(List<BigDecimal> lstBnkCrgsApprove) {
		ibankServiceRuleDao.saveApproveList(lstBnkCrgsApprove);
	}
	@Override
	@Transactional
	public List<BankServiceRule> getBankServiceRuleEnqryFrmDB(BigDecimal countryId,BigDecimal currencyId,BigDecimal bankId){
		return ibankServiceRuleDao.getBankServiceRuleEnqryFrmDB(countryId, currencyId, bankId);
	}
	@Override
	@Transactional
	public List<BankCharges> getBankChargesEnqry(BigDecimal bankServiceRuleId){
		return ibankServiceRuleDao.getBankChargesEnry(bankServiceRuleId);
	}

	@Override
	@Transactional
	public void saveApproveList(List<BigDecimal> lstBnkCrgsApprove,String userName,BigDecimal bankServiceRuleId) {
		ibankServiceRuleDao.saveApproveList(lstBnkCrgsApprove,userName,bankServiceRuleId);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean saveAllDetails(HashMap<String, Object> saveAllMap)throws Exception {
		return ibankServiceRuleDao.saveAllDetails(saveAllMap);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteRecord(BigDecimal bankServicePk){
		ibankServiceRuleDao.deleteRecord(bankServicePk);
	}

	@Override
	@Transactional
	public void deleteRecordsFrmDB(BigDecimal ChargesPk) {
		ibankServiceRuleDao.deleteRecordsFrmDB(ChargesPk);

	}

	@Override
	@Transactional
	public void saveBankChargesLog(BankChargesMasterLog bankChargesLogObj) {
		ibankServiceRuleDao.saveBankChargesLog(bankChargesLogObj);
	}

	@Override
	@Transactional
	public List<DeliveryModeDesc> getDeliveryFromRemittanceAndService(
			BigDecimal appCountryID, BigDecimal countryId,
			BigDecimal remittanceId, BigDecimal languageId,
			BigDecimal serviceId) {
		return ibankServiceRuleDao.getDeliveryFromRemittanceAndService(appCountryID, countryId, remittanceId, languageId, serviceId);
	}

}
