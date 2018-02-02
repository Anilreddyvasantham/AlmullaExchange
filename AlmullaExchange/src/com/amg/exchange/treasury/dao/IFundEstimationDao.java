package com.amg.exchange.treasury.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.amg.exchange.treasury.bean.BankCountryPopulationBean;
import com.amg.exchange.treasury.bean.CountryCurrencyPopulationBean;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.FundConsolidatedView;
import com.amg.exchange.treasury.model.FundEstimation;
import com.amg.exchange.treasury.model.FundEstimationAlert;
import com.amg.exchange.treasury.model.FundEstimationDays;
import com.amg.exchange.treasury.model.FundEstimationDetails;
import com.amg.exchange.treasury.viewModel.TreasuryEstimationDaysView;
import com.amg.exchange.treasury.viewModel.TreasuryFundEstimationDealAlertView;
import com.amg.exchange.treasury.viewModel.TreasuryFundestimationAlertView;
import com.amg.exchange.treasury.viewModel.TreasuryFundestimationView;
import com.amg.exchange.treasury.viewModel.TreasuryFundestimationViewSummary;
import com.amg.exchange.util.AMGException;

public interface IFundEstimationDao<T> {
	public String getCountryName(BigDecimal countryId);

	public List<BankCountryPopulationBean> getBankContry(BigDecimal countryId);

	public List<BankMaster> getBankAccordingToBankCountry(BigDecimal applicationCountry, BigDecimal countryId);

	public List<BankAccountDetails> getCurrencyOfBank(BigDecimal bankId);

	/*
	 * public void save(FundEstimation_Bkp fundEstimation);
	 * 
	 * public void saveAndUpadate(FundEstimation_Bkp fundEstimation);
	 */

	// Added by Rabil
	public void save(FundEstimation fundEstimation);

	public void saveAndUpadate(FundEstimation fundEstimation);

	public void saveFundEstimtaionDetails(FundEstimationDetails fundEstimationDetails);

	public void saveFundEstimationDays(FundEstimationDays fundEstimationDays);

	public void saveAndUpdateFundEstimationDays(FundEstimationDays fundEstimationDays);

	public List<FundEstimationDetails> getFundEstimationData(Date projectionDate, BigDecimal countryId, BigDecimal bankId, BigDecimal currencyId, BigDecimal applicationCountryId, BigDecimal accountNumber);

	public List<FundEstimationDays> getFundEstimationNoOfDays(Date projectionDate, BigDecimal countryId, BigDecimal bankId, BigDecimal currencyId, BigDecimal applicationCountryId, BigDecimal accountNumber);

	// public String getCurrentBalance(BigDecimal applicationCountryId,
	// BigDecimal countryId, BigDecimal bankId, BigDecimal currencyId);
	public String getCurrentBalance(String glSlNumber);

	public List<BankAccountDetails> getAccountNumber(BigDecimal bankId, BigDecimal currencyId);

	// get salesprojectionDetails
	public List<FundEstimationDays> getDaysList(BigDecimal companyId, BigDecimal applicationId, BigDecimal bankcountryId, BigDecimal ccrrencyId, BigDecimal saleprofundId, BigDecimal saleprojfundetailsId);

	// get FC Amount from TreasuryDealHeader for country, bank , currency and
	// acc no
	public BigDecimal getFCAmountfromFxDealDetails(BigDecimal bankId, BigDecimal currencyId, String accountNumberId);

	/*
	 * 05-02-2015 added by ramkrishna to get Country Name based on the language
	 * id
	 */
	public String getCountryNameDesc(BigDecimal countryId, BigDecimal languageId);

	// Get country name and country id from ViewBankCountry
	// public List<BankCountryPopulationBean> getBankContryFromView(BigDecimal
	// countryId);

	// added by Rabil
	public List<BankCountryPopulationBean> getBankContryFromView(BigDecimal countryId);

	public List<CountryCurrencyPopulationBean> getBankCurrencyFromView(BigDecimal countryId, BigDecimal bankCountryId);

	public List<TreasuryFundestimationView> getFundEstimationFromView(BigDecimal countryId, BigDecimal bankCountryId, BigDecimal currencyId);

	public List<TreasuryEstimationDaysView> getEstimationDaysFromView(BigDecimal countryId, BigDecimal bankCountryId, BigDecimal currencyId, String projectionDate);

	public List<TreasuryFundestimationAlertView> getEstimationDealAlertFromView(BigDecimal countryId, BigDecimal bankCountryId, BigDecimal currencyId, String projectionDate);

	public List<TreasuryFundEstimationDealAlertView> getEstimationDealAlert(BigDecimal countryId, BigDecimal bankCountryId, BigDecimal bankId, BigDecimal currencyId, String projectionDate, BigDecimal fundEstimationId);

	public void saveFundEstimationAlert(FundEstimationAlert fundEstimationAlert);

	public void saveAndUpadateFundEstimationAlert(FundEstimationAlert fundEstimationAlert);
	
	public List<TreasuryFundestimationView> getDetaillsForFundEstimationView(BigDecimal countryId, BigDecimal bankCountryId, BigDecimal currencyId);
	
	public List<TreasuryFundestimationViewSummary> getDetaillsForFundEstimationViewSummary(BigDecimal countryId, BigDecimal bankCountryId, BigDecimal currencyId,Date projectionDate);
	
	public BigDecimal fetchIKONRate();
	
	public List<FundConsolidatedView> fetchFundConsolidatedViewRecords(Date projectionDate,BigDecimal exchangeCountryId,BigDecimal bankCountryId,BigDecimal bankId,BigDecimal currencyId);

	/**Added by Rabil on 24/01/2016 for future projection Date */
	
	public HashMap<String , String> getExProjectionFutureProcedure(BigDecimal exchangeCountryId,BigDecimal bankCountryId,BigDecimal bankId,BigDecimal currencyId,BigDecimal serviceMasterId,Date projectionDate) throws AMGException;
	public List<FundEstimation> getFundEstimationDataForTomCash(
			BigDecimal appCountryId, BigDecimal bankCountryId,
			BigDecimal currencyId, BigDecimal bankId, Date projectionDate,
			BigDecimal fundEstimationId);
	
	
}
