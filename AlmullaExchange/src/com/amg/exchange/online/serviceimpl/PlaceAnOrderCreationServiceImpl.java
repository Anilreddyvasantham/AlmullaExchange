package com.amg.exchange.online.serviceimpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.online.bean.BranchStaffGSMRateDataTable;
import com.amg.exchange.online.dao.IPlaceAnOrderCreationDao;
import com.amg.exchange.online.model.RatePlaceOrder;
import com.amg.exchange.online.model.ViewPlaceOnOrderFullInquiry;
import com.amg.exchange.online.model.ViewPlaceOnOrderInquiry;
import com.amg.exchange.online.service.IPlaceAnOrderCreationService;
import com.amg.exchange.remittance.bean.PopulateData;
import com.amg.exchange.remittance.model.BenificiaryListView;
import com.amg.exchange.treasury.model.CurrencyOtherInformation;
import com.amg.exchange.util.AMGException;
@Service
public class PlaceAnOrderCreationServiceImpl implements	IPlaceAnOrderCreationService {
	
	@Autowired
	IPlaceAnOrderCreationDao iOnlineSpecialRateRequestDao;

	@Override
	@Transactional
	public List<PopulateData> getServiceGroupList(
			BigDecimal languageId) {
 
		return iOnlineSpecialRateRequestDao.getServiceGroupList(languageId);
	}

	@Override
	@Transactional
	public List<PopulateData> getBeneAccountNumber(String beneName,BigDecimal beneCountryId,BigDecimal beneBankId ,BigDecimal serviceGroupId,BigDecimal customerRef) {
 
	  	return iOnlineSpecialRateRequestDao.getBeneAccountNumber(beneName, beneCountryId,beneBankId, serviceGroupId,customerRef);
	}

	@Override
	@Transactional
	public void saveRecord(RatePlaceOrder ratePlaceOrderObj) {
 
		iOnlineSpecialRateRequestDao.saveRecord(ratePlaceOrderObj);
	}

	@Override
	@Transactional
	public List<PopulateData> getBeneficiarNameList(BigDecimal customerId,
			BigDecimal beneCountryId,BigDecimal serviceGroupId,BigDecimal customerRef) {
		
		return iOnlineSpecialRateRequestDao.getBeneficiarNameList(customerId, beneCountryId,serviceGroupId,customerRef);
	}

	@Override
	@Transactional
	public List<PopulateData> getBeneficiarBankList(String beneName,BigDecimal serviceGroupId,BigDecimal beneCountryId,BigDecimal customerRef) {
		
		return iOnlineSpecialRateRequestDao.getBeneficiarBankList(beneName,serviceGroupId,beneCountryId,customerRef);
	}

	@Override
	@Transactional
	public HashMap<String, BigDecimal> getBeneMasterIdCurrencyId(String beneName,BigDecimal beneCountryId,BigDecimal beneBankId ,BigDecimal serviceGroupId,BigDecimal customerRef,String AccountNo) {
		return iOnlineSpecialRateRequestDao.getBeneMasterIdCurrencyId(beneName, beneCountryId, beneBankId, serviceGroupId,customerRef,AccountNo);
	}

	@Override
	@Transactional
	public boolean checkPlaceanOrderCreatedForCustomer(BigDecimal customerId) {
		
		return iOnlineSpecialRateRequestDao.checkPlaceanOrderCreatedForCustomer(customerId);
	}

	@Override
	@Transactional
	public String toFetchCurrencyQtyName(BigDecimal beneficiaryMasterId,BigDecimal beneficaryAccountSeqId,BigDecimal customerRef) {
		
		return iOnlineSpecialRateRequestDao.toFetchCurrencyQtyName(beneficiaryMasterId,beneficaryAccountSeqId,customerRef);
	}

	@Override
	@Transactional
	public List<RatePlaceOrder> duplicatecheckRecord(BigDecimal customerId,BigDecimal customerRef, BigDecimal beneficiaryBankId,Date createdDate, BigDecimal beneficiaryCountryId,String accountNumber, BigDecimal serviceGroupId,BigDecimal fcOrLocalAmount,BigDecimal beneficaryMasterSeqId,BigDecimal beneficaryAccountSeqId) {
		return iOnlineSpecialRateRequestDao.duplicatecheckRecord(customerId, customerRef, beneficiaryBankId, createdDate, beneficiaryCountryId, accountNumber, serviceGroupId, fcOrLocalAmount, beneficaryMasterSeqId, beneficaryAccountSeqId);
	}

	@Override
	@Transactional
	public List<RatePlaceOrder> fetchSpotRateRecords(BigDecimal customerNo,Date createdDate, BigDecimal masterId, BigDecimal beneficaryBankId,
			BigDecimal beneficiaryAccountSeqId, BigDecimal dataserviceid,BigDecimal databenificarycountry, BigDecimal foriegnCurrency,
			BigDecimal amountToRemit, BigDecimal customerrefno,String accountNumber,BigDecimal countryBranchId) {
		return iOnlineSpecialRateRequestDao.fetchSpotRateRecords(customerNo, createdDate, masterId, beneficaryBankId, beneficiaryAccountSeqId, dataserviceid, databenificarycountry, foriegnCurrency, amountToRemit, customerrefno,accountNumber,countryBranchId);
	}

	@Override
	@Transactional
	public void updateSpecialRateRequest(BigDecimal ratePlaceOrderId,BigDecimal amountToRemit) {
		iOnlineSpecialRateRequestDao.updateSpecialRateRequest(ratePlaceOrderId, amountToRemit);
	}

	@Override
	@Transactional
	public List<RatePlaceOrder> getActiveSpecialRateRequest(BigDecimal customerNo, Date cerateDate, BigDecimal masterId,
			BigDecimal beneficaryBankId, BigDecimal beneficiaryAccountSeqId,BigDecimal dataserviceid, BigDecimal databenificarycountry,
			BigDecimal foriegnCurrency, BigDecimal amountToRemit,BigDecimal customerrefno, String dataAccountnum,BigDecimal countryBranchId) {
		return iOnlineSpecialRateRequestDao.getActiveSpecialRateRequest(customerNo, cerateDate, masterId, beneficaryBankId, beneficiaryAccountSeqId, dataserviceid, databenificarycountry, foriegnCurrency, amountToRemit, customerrefno, dataAccountnum,countryBranchId);
	}

	@Override
	@Transactional
	public List<RatePlaceOrder> checkPlaceAnorderExist(BigDecimal customerId,
			BigDecimal countryBranchId) throws AMGException {
		
		return iOnlineSpecialRateRequestDao.checkPlaceAnorderExist(customerId, countryBranchId);
	}

	@Override
	@Transactional
	public void updatePlaceOrderRemitDocumentNo(BigDecimal ratePlaceOrderId,
			BigDecimal remittDocNo, BigDecimal remittFinYear) {
	
		iOnlineSpecialRateRequestDao.updatePlaceOrderRemitDocumentNo(ratePlaceOrderId, remittDocNo, remittFinYear);
	}

	@Override
	@Transactional
	public BigDecimal toFetchAccountSeqId(BigDecimal beneficaryMasterSeqId,String beneficiaryName, BigDecimal beneficiaryBankId) {
		return iOnlineSpecialRateRequestDao.toFetchAccountSeqId(beneficaryMasterSeqId, beneficiaryName, beneficiaryBankId);
	}

	@Override
	@Transactional
	public List<PopulateData> getBasedOnCountyCurrency(BigDecimal countryId) {
		
		return iOnlineSpecialRateRequestDao.getBasedOnCountyCurrency(countryId);
	}

	@Override
	@Transactional
	public List<PopulateData> getBeneCorespondingBankListBasedOnCountru(
			BigDecimal countryId) {
		
		return iOnlineSpecialRateRequestDao.getBeneCorespondingBankListBasedOnCountru(countryId);
	}

	@Override
	@Transactional
	public List<PopulateData> getRequestCurrency(BigDecimal bankId) {
		
		return iOnlineSpecialRateRequestDao.getRequestCurrency(bankId);
	}

	@Override
	@Transactional
	public List<PopulateData> getDestinationCurrency(
			BigDecimal bankId) {
		
		return iOnlineSpecialRateRequestDao.getDestinationCurrency(bankId);
	}

	@Override
	@Transactional
	public List<PopulateData> fetchBeneMasterDetails(String beneName,
			BigDecimal beneCountryId, BigDecimal beneBankId,
			BigDecimal serviceGroupId, BigDecimal customerRef, String AccountNo) {
		return iOnlineSpecialRateRequestDao.fetchBeneMasterDetails(beneName, beneCountryId, beneBankId, serviceGroupId, customerRef, AccountNo);
	}

	@Override
	@Transactional
	public List<PopulateData> fetchBeneMasterDetailsfromView(String beneName,
			BigDecimal beneCountryId, BigDecimal beneBankId,
			BigDecimal serviceGroupId, BigDecimal customerRef, String AccountNo) {
		return iOnlineSpecialRateRequestDao.fetchBeneMasterDetailsfromView(beneName, beneCountryId, beneBankId, serviceGroupId, customerRef, AccountNo);
	}

	@Override
	@Transactional
	public List<PopulateData> getBeneficiaryAgents(String beneName,
			BigDecimal beneCountryId, BigDecimal beneBankId,
			BigDecimal serviceGroupId, BigDecimal customerId) {
		return iOnlineSpecialRateRequestDao.getBeneficiaryAgents(beneName, beneCountryId, beneBankId, serviceGroupId, customerId);
	}

	@Override
	@Transactional
	public List<PopulateData> getBeneficiaryAgentsBranch(String beneName,
			BigDecimal beneCountryId, BigDecimal beneBankId,
			BigDecimal serviceGroupId, BigDecimal customerId, BigDecimal agentId) {
		return iOnlineSpecialRateRequestDao.getBeneficiaryAgentsBranch(beneName, beneCountryId, beneBankId, serviceGroupId, customerId, agentId);
	}

	@Override
	@Transactional
	public List<BenificiaryListView> getBeneficiaryDetails(String beneName,
			BigDecimal beneCountryId, BigDecimal beneBankId,
			BigDecimal serviceGroupId, BigDecimal customerId,
			BigDecimal agentId, BigDecimal agentbranchId, String accountNumber,BigDecimal currencyId) {
		return iOnlineSpecialRateRequestDao.getBeneficiaryDetails(beneName,beneCountryId,beneBankId,serviceGroupId,customerId,agentId,agentbranchId,accountNumber,currencyId);
	}

	@Override
	@Transactional
	public List<BenificiaryListView> getBeneficiaryDetailsForAccept(
			BranchStaffGSMRateDataTable dataTable) {
		return iOnlineSpecialRateRequestDao.getBeneficiaryDetailsForAccept(dataTable);
	}

	@Override
	@Transactional
	public List<ViewPlaceOnOrderInquiry> fetchplaceOnOrderInquiry(BigDecimal countryBranchId,String userName) throws AMGException {
		return iOnlineSpecialRateRequestDao.fetchplaceOnOrderInquiry(countryBranchId,userName);
	}

	@Override
	@Transactional
	public List<CurrencyOtherInformation> fetchCurrencyMasterOthInfo(BigDecimal currencyId) {
		return iOnlineSpecialRateRequestDao.fetchCurrencyMasterOthInfo(currencyId);
	}

	@Override
	@Transactional
	public List<RatePlaceOrder> authLimitCheck(BigDecimal customerId,
			BigDecimal customerRef, BigDecimal beneficiaryBankId,
			Date createdDate, BigDecimal beneficiaryCountryId,
			String accountNumber, BigDecimal serviceGroupId,
			BigDecimal fcOrLocalAmount, BigDecimal beneficaryMasterSeqId,
			BigDecimal beneficaryAccountSeqId) {
		return iOnlineSpecialRateRequestDao.authLimitCheck(customerId, customerRef, beneficiaryBankId, createdDate, beneficiaryCountryId, accountNumber, serviceGroupId, fcOrLocalAmount, beneficaryMasterSeqId, beneficaryAccountSeqId);
	}

	@Override
	@Transactional
	public List<ViewPlaceOnOrderFullInquiry> fetchplaceOnOrderInquiryDetails(BigDecimal countryBranchId,String userName,Date placeOrderDt1,Date placeOrderDt2)
			throws AMGException {
		return iOnlineSpecialRateRequestDao.fetchplaceOnOrderInquiryDetails(countryBranchId, userName, placeOrderDt1,placeOrderDt2);
	}

}
