package com.amg.exchange.treasury.serviceimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.treasury.bean.BankCountryPopulationBean;
import com.amg.exchange.treasury.bean.CountryCurrencyPopulationBean;
import com.amg.exchange.treasury.dao.IFundEstimationDao;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.FundConsolidatedView;
import com.amg.exchange.treasury.model.FundEstimation;
import com.amg.exchange.treasury.model.FundEstimationAlert;
import com.amg.exchange.treasury.model.FundEstimationDays;
import com.amg.exchange.treasury.model.FundEstimationDetails;
import com.amg.exchange.treasury.service.IFundEstimationService;
import com.amg.exchange.treasury.viewModel.TreasuryEstimationDaysView;
import com.amg.exchange.treasury.viewModel.TreasuryFundEstimationDealAlertView;
import com.amg.exchange.treasury.viewModel.TreasuryFundestimationAlertView;
import com.amg.exchange.treasury.viewModel.TreasuryFundestimationView;
import com.amg.exchange.treasury.viewModel.TreasuryFundestimationViewSummary;
import com.amg.exchange.util.AMGException;

@SuppressWarnings("serial")
@Service("fundEstimationServiceImpl")
public class FundEstimationServiceImpl<T> implements IFundEstimationService<T>, Serializable {

	@Autowired
	IFundEstimationDao<T> fundEstimationDao;

	@Transactional
	@Override
	public String getCountryName(BigDecimal countryId) {
		return fundEstimationDao.getCountryName(countryId);
	}

	@Transactional
	@Override
	public List<BankCountryPopulationBean> getBankContry(BigDecimal countryId) {
		return fundEstimationDao.getBankContry(countryId);
	}

	@Transactional
	@Override
	public List<BankMaster> getBankAccordingToBankCountry(BigDecimal aplicationCountry, BigDecimal countryId) {
		return fundEstimationDao.getBankAccordingToBankCountry(aplicationCountry, countryId);
	}

	@Transactional
	@Override
	public List<BankAccountDetails> getCurrencyOfBank(BigDecimal bankId) {
		return fundEstimationDao.getCurrencyOfBank((bankId));
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void save(FundEstimation fundEstimation) {
		fundEstimationDao.save(fundEstimation);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void saveAndUpadate(FundEstimation fundEstimation) {

		fundEstimationDao.saveAndUpadate(fundEstimation);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void saveFundEstimtaionDetails(FundEstimationDetails fundEstimationDetails) {
		fundEstimationDao.saveFundEstimtaionDetails(fundEstimationDetails);
	}

	@Transactional
	@Override
	public void saveFundEstimationDays(FundEstimationDays fundEstimationDays) {
		fundEstimationDao.saveFundEstimationDays(fundEstimationDays);
	}

	@Transactional
	@Override
	public void saveAndUpdateFundEstimationDays(FundEstimationDays fundEstimationDays) {
		fundEstimationDao.saveAndUpdateFundEstimationDays(fundEstimationDays);
	}

	@Transactional
	@Override
	public List<FundEstimationDetails> getFundEstimationData(Date projectionDate, BigDecimal countryId, BigDecimal bankId, BigDecimal currencyId, BigDecimal applicationCountryId, BigDecimal accountNumber) {
		return fundEstimationDao.getFundEstimationData(projectionDate, countryId, bankId, currencyId, applicationCountryId, accountNumber);
	}

	/*
	 * @Transactional
	 * 
	 * @Override public List<FundEstimationDays> getFundEstimationNoOfDays(Date
	 * projectionDate, BigDecimal countryId, BigDecimal bankId,BigDecimal
	 * currencyId, BigDecimal applicationCountryId, BigDecimal accountNumber) {
	 * return fundEstimationDao.getFundEstimationNoOfDays(projectionDate,
	 * countryId, bankId, currencyId, applicationCountryId, accountNumber); }
	 */

	/*
	 * @Transactional
	 * 
	 * @Override public String getCurrentBalance(BigDecimal
	 * applicationCountryId, BigDecimal countryId, BigDecimal bankId, BigDecimal
	 * currencyId) { return
	 * fundEstimationDao.getCurrentBalance(applicationCountryId, countryId,
	 * bankId, currencyId); }
	 */

	@Transactional
	@Override
	public String getCurrentBalance(String glSlNumber) {
		return fundEstimationDao.getCurrentBalance(glSlNumber);
	}

	@Transactional
	@Override
	public List<BankAccountDetails> getAccountNumber(BigDecimal bankId, BigDecimal currencyId) {
		return fundEstimationDao.getAccountNumber(bankId, currencyId);
	}

	/*
	 * @Transactional
	 * 
	 * @Override public List<FundEstimationDays>
	 * getFundestimationDays(BigDecimal countryId,BigDecimal bankId,BigDecimal
	 * currencyId, BigDecimal applicationCountryId, BigDecimal accountNumber) {
	 * // TODO Auto-generated method stub return
	 * fundEstimationDao.getDaysList(projectiondate); }
	 */

	@Transactional
	@Override
	public List<FundEstimationDays> getFundestimationDays(BigDecimal companyId, BigDecimal applicationId, BigDecimal bankcountryId, BigDecimal ccrrencyId, BigDecimal saleprofundId, BigDecimal saleprojfundetailsId) {
		// TODO Auto-generated method stub
		return fundEstimationDao.getDaysList(companyId, applicationId, bankcountryId, ccrrencyId, saleprofundId, saleprojfundetailsId);
	}

	@Transactional
	@Override
	public BigDecimal getFCAmountfromFxDealDetails(BigDecimal bankId, BigDecimal currencyId, String accountNumberId) {
		// TODO Auto-generated method stub
		return fundEstimationDao.getFCAmountfromFxDealDetails(bankId, currencyId, accountNumberId);
	}

	@Transactional
	@Override
	public String getCountryNameDesc(BigDecimal countryId, BigDecimal languageId) {
		// TODO Auto-generated method stub
		return fundEstimationDao.getCountryNameDesc(countryId, languageId);
	}

	/** Added by Rabil start */

	@Transactional
	@Override
	public List<BankCountryPopulationBean> getBankContryFromView(BigDecimal countryId) {
		return fundEstimationDao.getBankContryFromView(countryId);
	}

	@Transactional
	@Override
	public List<CountryCurrencyPopulationBean> getBankCurrencyFromView(BigDecimal countryId, BigDecimal bankCountryId) {
		return fundEstimationDao.getBankCurrencyFromView(countryId, bankCountryId);
	}

	@Transactional
	@Override
	public List<TreasuryFundestimationView> getFundEstimationFromView(BigDecimal countryId, BigDecimal bankCountryId, BigDecimal currencyId) {
		// TODO Auto-generated method stub
		return fundEstimationDao.getFundEstimationFromView(countryId, bankCountryId, currencyId);
	}

	@Transactional
	@Override
	public List<TreasuryEstimationDaysView> getEstimationDaysFromView(BigDecimal countryId, BigDecimal bankCountryId, BigDecimal currencyId, String projectionDate) {
		// TODO Auto-generated method stub
		return fundEstimationDao.getEstimationDaysFromView(countryId, bankCountryId, currencyId, projectionDate);
	}

	@Transactional
	@Override
	public List<TreasuryFundestimationAlertView> getEstimationDealAlertFromView(BigDecimal countryId, BigDecimal bankCountryId, BigDecimal currencyId, String projectionDate) {
		// TODO Auto-generated method stub
		return fundEstimationDao.getEstimationDealAlertFromView(countryId, bankCountryId, currencyId, projectionDate);
	}

	@Transactional
	@Override
	public List<TreasuryFundEstimationDealAlertView> getEstimationDealAlert(BigDecimal countryId, BigDecimal bankCountryId, BigDecimal bankId, BigDecimal currencyId, String projectionDate, BigDecimal fundEstimationId) {
		// TODO Auto-generated method stub
		return fundEstimationDao.getEstimationDealAlert(countryId, bankCountryId, bankId, currencyId, projectionDate, fundEstimationId);
	}

	@Transactional
	@Override
	public void saveFundEstimationAlert(FundEstimationAlert fundEstimationAlert) {
		fundEstimationDao.saveFundEstimationAlert(fundEstimationAlert);

	}

	@Transactional
	@Override
	public void saveAndUpadateFundEstimationAlert(FundEstimationAlert fundEstimationAlert) {
		fundEstimationDao.saveAndUpadateFundEstimationAlert(fundEstimationAlert);

	}
	
	@Transactional
	@Override
	public List<TreasuryFundestimationView> getDetaillsForFundEstimationView(BigDecimal countryId, BigDecimal bankCountryId, BigDecimal currencyId){
		return fundEstimationDao.getDetaillsForFundEstimationView(countryId,bankCountryId,currencyId);
	}

	@Transactional
	@Override
	public List<TreasuryFundestimationViewSummary> getDetaillsForFundEstimationViewSummary(BigDecimal countryId, BigDecimal bankCountryId,BigDecimal currencyId,Date projectionDate) {
		return fundEstimationDao.getDetaillsForFundEstimationViewSummary(countryId,bankCountryId,currencyId,projectionDate);
	}

	@Transactional
	@Override
	public BigDecimal fetchIKONRate() {
		return fundEstimationDao.fetchIKONRate();
	}

	@Transactional
	@Override
	public List<FundConsolidatedView> fetchFundConsolidatedViewRecords(Date projectionDate, BigDecimal exchangeCountryId,
			BigDecimal bankCountryId, BigDecimal bankId, BigDecimal currencyId) {
		return fundEstimationDao.fetchFundConsolidatedViewRecords(projectionDate, exchangeCountryId, bankCountryId, bankId, currencyId);
	}

	@Transactional
	@Override
	public HashMap<String, String> getExProjectionFutureProcedure(BigDecimal exchangeCountryId, BigDecimal bankCountryId, BigDecimal bankId,
			BigDecimal currencyId, BigDecimal serviceMasterId, Date projectionDate) throws AMGException
		{
			// TODO Auto-generated method stub
			return fundEstimationDao.getExProjectionFutureProcedure(exchangeCountryId, bankCountryId, bankId, currencyId, serviceMasterId, projectionDate);
		}

	@Override
	@Transactional
	public List<FundEstimation> getFundEstimationDataForTomCash(
			BigDecimal appCountryId, BigDecimal bankCountryId,
			BigDecimal currencyId, BigDecimal bankId, Date projectionDate,
			BigDecimal fundEstimationId) {
 
		return fundEstimationDao.getFundEstimationDataForTomCash(  appCountryId,bankCountryId,
				  currencyId,  bankId, projectionDate, fundEstimationId);
	}
	
	
}
