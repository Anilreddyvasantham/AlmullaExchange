package com.amg.exchange.treasury.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.remittance.model.AuthorizationExchangeRateApprovalView;
import com.amg.exchange.remittance.model.ExchangeRateApprovalDetModel;
import com.amg.exchange.remittance.model.UnApprovedExchangeRatesView;
import com.amg.exchange.remittance.model.ViewExchangeRateAppDetails;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.DeliveryMode;
import com.amg.exchange.treasury.model.DeliveryModeDesc;
import com.amg.exchange.treasury.model.ExchangeRate;
import com.amg.exchange.treasury.model.ExchangeRateApproval;
import com.amg.exchange.treasury.model.RemittanceModeDescription;
import com.amg.exchange.treasury.model.ServiceMasterDesc;
import com.amg.exchange.treasury.model.ViewUnApprovedCashRate;
import com.amg.exchange.util.AMGException;

public interface IRatesUpdateService<T> {

	/*public List<ServiceIndicator> getServiceIndicatorList();*/

	public String getCountryName(BigDecimal countryId);
	
	public String getServiceName(BigDecimal languageId,BigDecimal serviceId);
	
	public String getBranchName(BigDecimal branchId);
	
	public List<CountryBranch> getCountryBranchList(BigDecimal countryId);

	public List<BankApplicability> getAllCorespodingBankList();

	public void saveRecord(ExchangeRate exchageRateObj);

	public List<ServiceMasterDesc> getServiceMastersList(BigDecimal languageId);
	
	public List<RemittanceModeDescription> getRemittanceMastersList(BigDecimal langugeId);
	
	public List<DeliveryMode> getDeliveryModeMastersList();

	//new service created for deliveryModeDesc
	public List<DeliveryModeDesc> lstDeliveryMode(BigDecimal languageId);
	
	public String getdeliveryName(BigDecimal deliveryId);

	public List<ExchangeRate> getAllExchageRates();

	public List<ExchangeRate> exchangeRateList(BigDecimal countryId,BigDecimal currencyId,BigDecimal bankId,BigDecimal serviceId,BigDecimal branchId,BigDecimal remittanceMode,BigDecimal DeliveryMode);

	public void saveExchangeRateApproval(ExchangeRateApproval exchangeApp);

	public void deleteExchangeRecord(BigDecimal exchageRateObj);
	
	public List<ExchangeRateApprovalDetModel> getAllExchageRatesBasedOnCountryandCurrency(BigDecimal currencyId, BigDecimal countryId);

	public BigDecimal getExchageRateApprovalPk(BigDecimal countryId,BigDecimal currencyId,BigDecimal bankId,BigDecimal serviceId,BigDecimal branchId,BigDecimal remittanceMode,BigDecimal DeliveryMode);

	public List<UnApprovedExchangeRatesView> getAllExchageRatesFromUnApprovedExchangeRateView();
	
	public List<AuthorizationExchangeRateApprovalView> getAllExchageRatesAuthorizedList();
	
	public void saveApprovedExchangedRate(HashMap<String,Object> saveAprroveRecords) throws AMGException;
	
	public List<ViewUnApprovedCashRate> getAllCashExchageRatesFromViewUnApprovedCashRate();
	
	public void saveApprovedCashExchangedRate(HashMap<String,Object> saveAprroveRecords) throws AMGException;

	/** Added by Rabil on 10/04/2016 */
	public List<ViewExchangeRateAppDetails> getAllExchageRatesBasedOnCountryandCurrencyAndBrancWise(BigDecimal currencyId, BigDecimal countryId,BigDecimal branchId);
	
	public List<ViewExchangeRateAppDetails> getAllExchageRatesCountryBasedOnCurrency(BigDecimal currencyId);
	
	public List<ExchangeRateApprovalDetModel> getAllExchageRatesBasedOnCountryandCurrencyandBranch(BigDecimal currencyId, BigDecimal countryId, BigDecimal branchId);
}
