package com.amg.exchange.online.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.amg.exchange.online.bean.BranchStaffGSMRateDataTable;
import com.amg.exchange.online.model.RatePlaceOrder;
import com.amg.exchange.online.model.ViewPlaceOnOrderFullInquiry;
import com.amg.exchange.online.model.ViewPlaceOnOrderInquiry;
import com.amg.exchange.remittance.bean.PopulateData;
import com.amg.exchange.remittance.model.BenificiaryListView;
import com.amg.exchange.treasury.model.CurrencyOtherInformation;
import com.amg.exchange.util.AMGException;
public interface IPlaceAnOrderCreationService {
	
	public void saveRecord(RatePlaceOrder ratePlaceOrderObj);
	
	public  List<PopulateData> getServiceGroupList(BigDecimal languageId);
	
	//public List<BenificiaryListView> getBeneAccountNumber(BigDecimal beneficiaryMasterId,BigDecimal customerId,BigDecimal beneCountryId,BigDecimal BeneBankId);

	public List<PopulateData> getBeneficiarNameList(BigDecimal customerId,BigDecimal beneCountryId,BigDecimal serviceGroupId,BigDecimal customerRef);

	public List<PopulateData> getBeneficiarBankList(String beneName,BigDecimal serviceGroupId,BigDecimal beneCountryId,BigDecimal customerRef);

	public List<PopulateData> getBeneAccountNumber(String beneName,BigDecimal beneCountryId,BigDecimal beneBankId ,BigDecimal serviceGroupId,BigDecimal customerRef);

	public HashMap<String, BigDecimal> getBeneMasterIdCurrencyId(String beneName,BigDecimal beneCountryId,BigDecimal beneBankId ,BigDecimal serviceGroupId,BigDecimal customerRef,String AccountNo);

	public boolean checkPlaceanOrderCreatedForCustomer(BigDecimal customerId);

	String toFetchCurrencyQtyName(BigDecimal beneficiaryMasterId,BigDecimal beneficaryAccountSeqId,BigDecimal customerRef);

	public List<RatePlaceOrder> duplicatecheckRecord(BigDecimal customerId,BigDecimal customerRef, BigDecimal beneficiaryBankId, Date createdDate,BigDecimal beneficiaryCountryId, String accountNumber,BigDecimal serviceGroupId, BigDecimal fcOrLocalAmount,BigDecimal beneficaryMasterSeqId,BigDecimal beneficaryAccountSeqId);

	public List<RatePlaceOrder> fetchSpotRateRecords(BigDecimal customerNo,Date createdDate, BigDecimal masterId, BigDecimal beneficaryBankId,
			BigDecimal beneficiaryAccountSeqId, BigDecimal dataserviceid,BigDecimal databenificarycountry, BigDecimal foriegnCurrency,
			BigDecimal amountToRemit, BigDecimal customerrefno,String accountNumber,BigDecimal countryBranchId);

	public void updateSpecialRateRequest(BigDecimal ratePlaceOrderId,BigDecimal amountToRemit);

	public List<RatePlaceOrder> getActiveSpecialRateRequest(BigDecimal customerNo,Date cerateDate, BigDecimal masterId, BigDecimal beneficaryBankId,
			BigDecimal beneficiaryAccountSeqId, BigDecimal dataserviceid,BigDecimal databenificarycountry, BigDecimal foriegnCurrency,
			BigDecimal amountToRemit, BigDecimal customerrefno,String dataAccountnum,BigDecimal countryBranchId);

	public List<RatePlaceOrder> checkPlaceAnorderExist(BigDecimal customerId,BigDecimal countryBranchId)throws AMGException;

	public void updatePlaceOrderRemitDocumentNo(BigDecimal ratePlaceOrderId,BigDecimal remittDocNo,BigDecimal remittFinYear);

	public BigDecimal toFetchAccountSeqId(BigDecimal beneficaryMasterSeqId,String beneficiaryName, BigDecimal beneficiaryBankId);
	
	public List<PopulateData> getBasedOnCountyCurrency(BigDecimal countryId);
	
	public List<PopulateData> getBeneCorespondingBankListBasedOnCountru(BigDecimal countryId);
	
	public List<PopulateData> getRequestCurrency(BigDecimal bankId);
	
	public List<PopulateData> getDestinationCurrency(BigDecimal bankId);
	
	public List<PopulateData> fetchBeneMasterDetails(String beneName,BigDecimal beneCountryId,BigDecimal beneBankId ,BigDecimal serviceGroupId,BigDecimal customerRef,String AccountNo);
	
	public List<PopulateData> fetchBeneMasterDetailsfromView(String beneName,BigDecimal beneCountryId,BigDecimal beneBankId ,BigDecimal serviceGroupId,BigDecimal customerRef,String AccountNo);
	
	public List<PopulateData> getBeneficiaryAgents(String beneName,BigDecimal beneCountryId,BigDecimal beneBankId ,BigDecimal serviceGroupId,BigDecimal customerId);
	
	public List<PopulateData> getBeneficiaryAgentsBranch(String beneName,BigDecimal beneCountryId,BigDecimal beneBankId ,BigDecimal serviceGroupId,BigDecimal customerId,BigDecimal agentId);
	
	public List<BenificiaryListView> getBeneficiaryDetails(String beneName,BigDecimal beneCountryId,BigDecimal beneBankId ,BigDecimal serviceGroupId,BigDecimal customerId,BigDecimal agentId ,BigDecimal agentbranchId, String accountNumber,BigDecimal currencyId);
	
	public List<BenificiaryListView> getBeneficiaryDetailsForAccept(BranchStaffGSMRateDataTable dataTable);
	
	public List<ViewPlaceOnOrderInquiry> fetchplaceOnOrderInquiry(BigDecimal countryBranchId,String userName)throws AMGException;
	
	public List<CurrencyOtherInformation> fetchCurrencyMasterOthInfo(BigDecimal currencyId);
	
	public List<RatePlaceOrder> authLimitCheck(BigDecimal customerId,BigDecimal customerRef, BigDecimal beneficiaryBankId, Date createdDate,BigDecimal beneficiaryCountryId, String accountNumber,BigDecimal serviceGroupId, BigDecimal fcOrLocalAmount,BigDecimal beneficaryMasterSeqId,BigDecimal beneficaryAccountSeqId);
	
	public List<ViewPlaceOnOrderFullInquiry> fetchplaceOnOrderInquiryDetails(BigDecimal countryBranchId,String userName,Date placeOrderDt1,Date placeOrderDt2)throws AMGException;
	
}
