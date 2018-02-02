package com.amg.exchange.online.serviceimpl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.online.bean.GSMPlaceOrderDataTable;
import com.amg.exchange.online.dao.IGSMPlaceOrderRateFeedDao;
import com.amg.exchange.online.model.RatePlaceOrder;
import com.amg.exchange.online.service.IGSMPlaceOrderRateFeedService;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.registration.model.ViewArea;
import com.amg.exchange.remittance.bean.PopulateData;
import com.amg.exchange.remittance.model.ExchangeRateApprovalDetModel;
import com.amg.exchange.remittance.model.PaymentModeDesc;
import com.amg.exchange.util.AMGException;

@Service("gSMPlaceOrderRateFeedServiceImpl")
@Transactional
public class GSMPlaceOrderRateFeedServiceImpl implements IGSMPlaceOrderRateFeedService {

	@Autowired
	IGSMPlaceOrderRateFeedDao gSMPlaceOrderRateFeedDao;

	@Override
	public List<RatePlaceOrder> fetchAllRecrds(BigDecimal customerId,BigDecimal countryBranchId) {
		return gSMPlaceOrderRateFeedDao.fetchAllRecrds(customerId, countryBranchId);
	}

	@Override
	public String approveAllRecords(BigDecimal rateOfferedIdPk,BigDecimal rateOffered, String custIndicator,BigDecimal dataserviceid, BigDecimal routingCountry,BigDecimal routingBank, BigDecimal remitMode,
			BigDecimal deliveryMode, BigDecimal routingBranch, String userName,String customerUniqueNumber) {
		return gSMPlaceOrderRateFeedDao.approveAllRecords(rateOfferedIdPk, rateOffered, custIndicator, dataserviceid, routingCountry, routingBank, remitMode, deliveryMode, routingBranch, userName, customerUniqueNumber);
	}

	@Override
	public List<RatePlaceOrder> fetchAllRecrdsforUnApproved(BigDecimal countryBranchId) {
		return gSMPlaceOrderRateFeedDao.fetchAllRecrdsforUnApproved(countryBranchId);
	}

	@Override
	public String toFetchServiceGroupDesc(BigDecimal languageId,BigDecimal remitType) {
		return gSMPlaceOrderRateFeedDao.toFetchServiceGroupDesc(languageId, remitType);
	}

	@Override
	public String toFetchBeneficiaryName(BigDecimal beneficiaryMasterId) {
		return gSMPlaceOrderRateFeedDao.toFetchBeneficiaryName(beneficiaryMasterId);
	}

	@Override
	public String toFetchCurrencyQtyName(BigDecimal currencyId) {
		return gSMPlaceOrderRateFeedDao.toFetchCurrencyQtyName(currencyId);
	}

	@Override
	public List<ExchangeRateApprovalDetModel> getMinMaxRate(BigDecimal countryId,BigDecimal bankId,BigDecimal currencyId) {
		return gSMPlaceOrderRateFeedDao.getMinMaxRate(countryId,bankId,currencyId);
	}

	@Override
	public List<RatePlaceOrder> CheckUnqiueNumber(String customerUniqueKeyNo) {
		return gSMPlaceOrderRateFeedDao.CheckUnqiueNumber(customerUniqueKeyNo);
	}

	@Override
	public String toFetchBranchName(BigDecimal countryBranchId) {
		return gSMPlaceOrderRateFeedDao.toFetchBranchName(countryBranchId);
	}

	@Override
	public List<RatePlaceOrder> fetchAllRecrdsUnapprovedRecrdsBasedonCusterId(BigDecimal customerId, BigDecimal countryBranchid) {
		return gSMPlaceOrderRateFeedDao.fetchAllRecrdsUnapprovedRecrdsBasedonCusterId(customerId, countryBranchid);
	}

	@Override
	public BigDecimal toFetchCustomerRef(BigDecimal customerId) {
		return gSMPlaceOrderRateFeedDao.toFetchCustomerRef(customerId);
	}

	@Override
	public BigDecimal toFetchBankBranchId(BigDecimal beneficiaryBankId,BigDecimal beneficaryMasterSeqId, BigDecimal beneficaryAccountSeqId) {
		return gSMPlaceOrderRateFeedDao.toFetchBankBranchId(beneficiaryBankId, beneficaryMasterSeqId, beneficaryAccountSeqId);
	}

	@Override
	public List<PaymentModeDesc> fetchPaymodeDesc(BigDecimal langId,
			String isActive) {
		
		return gSMPlaceOrderRateFeedDao.fetchPaymodeDesc(langId, isActive);
	}

	@Override
	public void rejectPlaceorder(BigDecimal placeorderPk) {
		gSMPlaceOrderRateFeedDao.rejectPlaceorder(placeorderPk);
		
	}

	@Override
	public BigDecimal getBeneBranchId(BigDecimal beneficiaryBankId,
			BigDecimal beneficaryMasterSeqId, String beneName) {
		// TODO Auto-generated method stub
		return gSMPlaceOrderRateFeedDao.getBeneBranchId(beneficiaryBankId, beneficaryMasterSeqId, beneName);
	}

	//new changes services starts 19052016
	@Override
	public List<Employee> toFetchEmployeeArea() {
		return gSMPlaceOrderRateFeedDao.toFetchEmployeeArea();
	}

	@Override
	public List<RatePlaceOrder> toFetchRoutingDetails(String area,BigDecimal countryBranchid, BigDecimal currencyid,
			BigDecimal customerReference, BigDecimal beneficiaryBankid,String fixRateid,BigDecimal beneficiaryCountryId,List<PopulateData> lstRoutingChecksforSearch,String eftorTT) {
		return gSMPlaceOrderRateFeedDao.toFetchRoutingDetails(area, countryBranchid, currencyid, customerReference, beneficiaryBankid, fixRateid,beneficiaryCountryId,lstRoutingChecksforSearch,eftorTT);
	}

	@Override
	public List<PopulateData> toFtechAllRoutingCountry(BigDecimal currencyid,BigDecimal languaueId,BigDecimal servicegroupId,BigDecimal benecountryId) {
		return gSMPlaceOrderRateFeedDao.toFtechAllRoutingCountry(currencyid,languaueId,servicegroupId,benecountryId);
	}

	@Override
	public List<PopulateData> toFtechAllRoutingBanks(BigDecimal currencyid,BigDecimal routingCountry) {
		return gSMPlaceOrderRateFeedDao.toFtechAllRoutingBanks(currencyid, routingCountry);
	}

	@Override
	public List<PopulateData> toFtechAllRoutingBanksBasedOncurrency(BigDecimal currencyId,BigDecimal beneficiaryCountryid,BigDecimal servicegroupId) {
		return gSMPlaceOrderRateFeedDao.toFtechAllRoutingBanksBasedOncurrency(currencyId,beneficiaryCountryid,servicegroupId);
	}

	@Override
	public String saveOrUpDate(BigDecimal rateOfferedPk,BigDecimal routingBankId, String specialOrCommonPoolIndicator,BigDecimal rateOffered,String userName) {
		return gSMPlaceOrderRateFeedDao.saveOrUpDate(rateOfferedPk, routingBankId, specialOrCommonPoolIndicator, rateOffered,userName);
	}

	@Override
	public List<ViewArea> getAreaPlace() {
		
		return gSMPlaceOrderRateFeedDao.getAreaPlace();
	}

	@Override
	public Boolean toCheckStatus(BigDecimal rateOfferedPk) {
		return gSMPlaceOrderRateFeedDao.toCheckStatus(rateOfferedPk);
	}

	@Override
	public Boolean toCheckRateOfferedForNegotiat(BigDecimal rateOfferedPk,
			BigDecimal newRateOffered) {
		
		return gSMPlaceOrderRateFeedDao.toCheckRateOfferedForNegotiat(rateOfferedPk, newRateOffered);
	}

	@Override
	public List<PopulateData> getPopulateBeneficiaryBanks(BigDecimal appCountryId,
			BigDecimal beneCountryId, BigDecimal serviceGroupId) {
		
		return gSMPlaceOrderRateFeedDao.getPopulateBeneficiaryBanks(appCountryId,beneCountryId, serviceGroupId);
	}

	@Override
	public List<PopulateData> getBasedOnCountyCurrency(BigDecimal countryId,
			BigDecimal currencyID) {
		
		return gSMPlaceOrderRateFeedDao.getBasedOnCountyCurrency(countryId, currencyID);
	}

	@Override
	public String saveOrUpDateRatePlaceOrder(List<GSMPlaceOrderDataTable> lstSaveGSM) {
		
		return gSMPlaceOrderRateFeedDao.saveOrUpDateRatePlaceOrder(lstSaveGSM);
	}

	@Override
	public HashMap<String, String> fetchActualRate(HashMap<String, Object> fetchRate) throws AMGException {
		return gSMPlaceOrderRateFeedDao.fetchActualRate(fetchRate);
	}

	@Override
	public List<PopulateData> toFtechAllRoutingBanksBasedOncurrencyServiceId(
			BigDecimal currencyId, BigDecimal beneficiaryCountryid,
			BigDecimal serviceMasterId) {
		return gSMPlaceOrderRateFeedDao.toFtechAllRoutingBanksBasedOncurrencyServiceId(currencyId, beneficiaryCountryid, serviceMasterId);
	}
		
}
