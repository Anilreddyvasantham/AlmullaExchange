package com.amg.exchange.treasury.serviceimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.remittance.model.AuthorizationExchangeRateApprovalView;
import com.amg.exchange.remittance.model.ExchangeRateApprovalDetModel;
import com.amg.exchange.remittance.model.UnApprovedExchangeRatesView;
import com.amg.exchange.remittance.model.ViewExchangeRateAppDetails;
import com.amg.exchange.treasury.dao.IRatesUpdateDao;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.DeliveryMode;
import com.amg.exchange.treasury.model.DeliveryModeDesc;
import com.amg.exchange.treasury.model.ExchangeRate;
import com.amg.exchange.treasury.model.ExchangeRateApproval;
import com.amg.exchange.treasury.model.RemittanceModeDescription;
import com.amg.exchange.treasury.model.ServiceMasterDesc;
import com.amg.exchange.treasury.model.ViewUnApprovedCashRate;
import com.amg.exchange.treasury.service.IRatesUpdateService;
import com.amg.exchange.util.AMGException;

@Service("ratesUpdateService")
public class RatesUpdateServiceImpl implements IRatesUpdateService<Object> ,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	IRatesUpdateDao ratesUpdateDao;

	@Override
	@Transactional
	public String getCountryName(BigDecimal countryId) {
		return ratesUpdateDao.getCountryName(countryId);
	}

	@Override
	@Transactional
	public String getServiceName(BigDecimal languageId,BigDecimal serviceId) {
		return ratesUpdateDao.getServiceName(languageId,serviceId);
	}

	@Override
	@Transactional
	public String getBranchName(BigDecimal branchId) {
		return ratesUpdateDao.getBranchName(branchId);
	}

	/*@Override
	@Transactional
	public List getServiceIndicatorList() {
		return ratesUpdateDao.getServiceIndicatorList();
	}*/

	@Override
	@Transactional
	public void saveRecord(ExchangeRate exchageRateObj) {
		ratesUpdateDao.saveRecord(exchageRateObj);
	}

	@Override
	@Transactional
	public void deleteExchangeRecord(BigDecimal exchageRateObj) {
		ratesUpdateDao.deleteExchangeRecord(exchageRateObj);
	}

	@Override
	@Transactional
	public void saveExchangeRateApproval(ExchangeRateApproval exchangeApp) {
		ratesUpdateDao.saveExchangeRateApproval(exchangeApp);
	}

	@Override
	@Transactional
	public List<CountryBranch> getCountryBranchList(BigDecimal countryId) {
		return ratesUpdateDao.getCountryBranchList(countryId);
	}

	@Override
	@Transactional
	public List<BankApplicability> getAllCorespodingBankList() {
		return ratesUpdateDao.getAllCorespodingBankList();
	}

	@Override
	@Transactional
	public List<ServiceMasterDesc> getServiceMastersList(BigDecimal languageId) {

		return ratesUpdateDao.getServiceMasterList(languageId);
	}

	@Override
	@Transactional
	public List<RemittanceModeDescription> getRemittanceMastersList(BigDecimal langugeId) {
		return ratesUpdateDao.getRemittanceMastersList(langugeId);
	}

	@Override
	@Transactional
	public List<DeliveryMode> getDeliveryModeMastersList() {
		return ratesUpdateDao.getDeliveryModeMastersList();
	}

	@Override
	@Transactional
	public List<DeliveryModeDesc> lstDeliveryMode(BigDecimal languageId) {
		// TODO Auto-generated method stub
		return ratesUpdateDao.lstDeliveryMode(languageId);
	}

	@Override
	@Transactional
	public String getdeliveryName(BigDecimal deliveryId) {
		// TODO Auto-generated method stub
		return ratesUpdateDao.getdeliveryName(deliveryId);
	}

	@Override
	@Transactional
	public List<ExchangeRate> exchangeRateList(BigDecimal countryId,BigDecimal currencyId,BigDecimal bankId,BigDecimal serviceId,BigDecimal branchId,BigDecimal remittanceMode,BigDecimal deliveryMode){
		return ratesUpdateDao.exchangeRateList( countryId, currencyId,bankId, serviceId, branchId, remittanceMode,deliveryMode);
	}

	@Override
	@Transactional
	public List<ExchangeRate> getAllExchageRates(){
		return ratesUpdateDao.getAllExchageRates();
	}

	@Override
	@Transactional
	public List<ExchangeRateApprovalDetModel> getAllExchageRatesBasedOnCountryandCurrency(BigDecimal currencyId,BigDecimal countryId) {
		return ratesUpdateDao.getAllExchageRatesBasedOnCountryandCurrency(currencyId,countryId);
	}

	@Override
	@Transactional
	public BigDecimal getExchageRateApprovalPk(BigDecimal countryId,BigDecimal currencyId,BigDecimal bankId,BigDecimal serviceId,BigDecimal branchId,BigDecimal remittanceMode,BigDecimal deliveryMode){
		return ratesUpdateDao.getExchageRateApprovalPk( countryId, currencyId,bankId, serviceId, branchId, remittanceMode,deliveryMode);
	}

	@Override
	@Transactional
	public List<UnApprovedExchangeRatesView> getAllExchageRatesFromUnApprovedExchangeRateView() {
		return ratesUpdateDao.getAllExchageRatesFromUnApprovedExchangeRateView();
	}

	@Override
	@Transactional
	public List<AuthorizationExchangeRateApprovalView> getAllExchageRatesAuthorizedList() {
		return ratesUpdateDao.getAllExchageRatesAuthorizedList();
	}

	@Override
	@Transactional(rollbackFor = AMGException.class)
	public void saveApprovedExchangedRate(HashMap<String, Object> saveAprroveRecords) throws AMGException {
		ratesUpdateDao.saveApprovedExchangedRate(saveAprroveRecords);
	}

	@Override
	@Transactional
	public List<ViewUnApprovedCashRate> getAllCashExchageRatesFromViewUnApprovedCashRate() {
		return ratesUpdateDao.getAllCashExchageRatesFromViewUnApprovedCashRate();
	}

	@Override
	@Transactional(rollbackFor = AMGException.class)
	public void saveApprovedCashExchangedRate(HashMap<String, Object> saveAprroveRecords) throws AMGException {
		ratesUpdateDao.saveApprovedCashExchangedRate(saveAprroveRecords);
	}

	@Override
	@Transactional
	public List<ViewExchangeRateAppDetails> getAllExchageRatesBasedOnCountryandCurrencyAndBrancWise(BigDecimal currencyId, BigDecimal countryId,
			BigDecimal branchId) {
		return ratesUpdateDao.getAllExchageRatesBasedOnCountryandCurrencyAndBrancWise(currencyId, countryId, branchId);
	}

	@Override
	@Transactional
	public List<ViewExchangeRateAppDetails> getAllExchageRatesCountryBasedOnCurrency(BigDecimal currencyId) {
		// TODO Auto-generated method stub
		return ratesUpdateDao.getAllExchageRatesCountryBasedOnCurrency(currencyId);
	}

	@Override
	@Transactional
	public List<ExchangeRateApprovalDetModel> getAllExchageRatesBasedOnCountryandCurrencyandBranch(
			BigDecimal currencyId, BigDecimal countryId, BigDecimal branchId) {
		return ratesUpdateDao.getAllExchageRatesBasedOnCountryandCurrencyandBranch(currencyId, countryId, branchId);
	}

}
