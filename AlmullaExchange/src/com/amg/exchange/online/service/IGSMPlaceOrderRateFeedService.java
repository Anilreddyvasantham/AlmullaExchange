package com.amg.exchange.online.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import com.amg.exchange.online.bean.GSMPlaceOrderDataTable;
import com.amg.exchange.online.model.RatePlaceOrder;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.registration.model.ViewArea;
import com.amg.exchange.remittance.bean.PopulateData;
import com.amg.exchange.remittance.model.ExchangeRateApprovalDetModel;
import com.amg.exchange.remittance.model.PaymentModeDesc;
import com.amg.exchange.util.AMGException;

public interface IGSMPlaceOrderRateFeedService {

	public List<RatePlaceOrder> fetchAllRecrds(BigDecimal customerId,BigDecimal countryBranchId);

	public String approveAllRecords(BigDecimal rateOfferedIdPk,BigDecimal rateOffered, String custIndicator,BigDecimal dataserviceid, BigDecimal routingCountry,BigDecimal routingBank, BigDecimal remitMode,
			BigDecimal deliveryMode, BigDecimal routingBranch, String userName,String customerUniqueNumber);

	public List<RatePlaceOrder> fetchAllRecrdsforUnApproved(BigDecimal countryBranchId);

	public String toFetchServiceGroupDesc(BigDecimal languageId,BigDecimal remitType);

	public String toFetchBeneficiaryName(BigDecimal beneficiaryMasterId);

	public String toFetchCurrencyQtyName(BigDecimal currencyId);

	public List<ExchangeRateApprovalDetModel> getMinMaxRate(BigDecimal countryId,BigDecimal bankId,BigDecimal currencyId);

	public List<RatePlaceOrder> CheckUnqiueNumber(String customerUniqueKeyNo);

	public String toFetchBranchName(BigDecimal countryBranchId);

	public List<RatePlaceOrder> fetchAllRecrdsUnapprovedRecrdsBasedonCusterId(BigDecimal customerId, BigDecimal countryBranchid);

	public BigDecimal toFetchCustomerRef(BigDecimal customerId);

	public BigDecimal toFetchBankBranchId(BigDecimal beneficiaryBankId,BigDecimal beneficaryMasterSeqId, BigDecimal beneficaryAccountSeqId);

	public List<PaymentModeDesc> fetchPaymodeDesc(BigDecimal langId,String isActive);
	
	public void rejectPlaceorder(BigDecimal placeorderPk);

	public BigDecimal getBeneBranchId(BigDecimal beneficiaryBankId,BigDecimal beneficaryMasterSeqId, String beneName);

	public List<Employee> toFetchEmployeeArea();

	public List<RatePlaceOrder> toFetchRoutingDetails(String area,BigDecimal countryBranchid, BigDecimal currencyid,BigDecimal customerReference, BigDecimal beneficiaryBankid,String fixRateid,BigDecimal beneficiaryCountryId,List<PopulateData> lstRoutingChecksforSearch,String eftorTT);

	public List<PopulateData> toFtechAllRoutingCountry(BigDecimal currencyid,BigDecimal languaueId,BigDecimal servicegroupId,BigDecimal benecountryId);

	public List<PopulateData> toFtechAllRoutingBanks(BigDecimal currencyid,BigDecimal routingCountry);

	public List<PopulateData> toFtechAllRoutingBanksBasedOncurrency(BigDecimal currencyId,BigDecimal beneficiaryCountryid,BigDecimal servicegroupId);

	public String saveOrUpDate(BigDecimal rateOfferedPk,BigDecimal routingBankId, String specialOrCommonPoolIndicator,BigDecimal rateOffered,String userName);
	
	public List<ViewArea> getAreaPlace();

	public Boolean toCheckStatus(BigDecimal rateOfferedPk);
	
	public Boolean toCheckRateOfferedForNegotiat(BigDecimal rateOfferedPk,BigDecimal newRateOffered);
	
	public List<PopulateData> getPopulateBeneficiaryBanks(BigDecimal appCountryId,BigDecimal beneCountryId,BigDecimal serviceGroupId);
	
	public List<PopulateData> getBasedOnCountyCurrency(BigDecimal countryId ,BigDecimal currencyID);
	
	public String saveOrUpDateRatePlaceOrder(List<GSMPlaceOrderDataTable> lstSaveGSM);
	
	public HashMap<String, String> fetchActualRate(HashMap<String, Object> fetchRate) throws AMGException;
	
	public List<PopulateData> toFtechAllRoutingBanksBasedOncurrencyServiceId(BigDecimal currencyId,BigDecimal beneficiaryCountryid,BigDecimal serviceMasterId);

}
