package com.amg.exchange.remittance.serviceimpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.beneficiary.model.CustomerRemittanceTransactionView;
import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.model.CutomerDetailsView;
import com.amg.exchange.foreigncurrency.model.Collect;
import com.amg.exchange.foreigncurrency.model.CollectDetail;
import com.amg.exchange.foreigncurrency.model.CurrencyWiseDenomination;
import com.amg.exchange.foreigncurrency.model.ForeignCurrencyAdjust;
import com.amg.exchange.foreigncurrency.model.ForeignCurrencyAdjustApp;
import com.amg.exchange.foreigncurrency.model.TempCollectDetail;
import com.amg.exchange.foreigncurrency.model.TempCollection;
import com.amg.exchange.foreigncurrency.model.TempForeignCurrencyAdjust;
import com.amg.exchange.intercompany.model.IntraTrnxModel;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.registration.model.CustomerMobileLogModel;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.remittance.bean.AddAdditionalBankData;
import com.amg.exchange.remittance.bean.PopulateData;
import com.amg.exchange.remittance.bean.ShoppingCartDataTableBean;
import com.amg.exchange.remittance.dao.IPersonalRemittanceDao;
import com.amg.exchange.remittance.model.AdditionalBankDetailsView;
import com.amg.exchange.remittance.model.AdditionalBankRuleAddData;
import com.amg.exchange.remittance.model.AdditionalBankRuleMap;
import com.amg.exchange.remittance.model.AdditionalDataDisplayView;
import com.amg.exchange.remittance.model.AdditionalInstructionData;
import com.amg.exchange.remittance.model.AllBeneficiaryView;
import com.amg.exchange.remittance.model.AmlLimit;
import com.amg.exchange.remittance.model.AuthicationLimitCheckView;
import com.amg.exchange.remittance.model.AuthorizedLog;
import com.amg.exchange.remittance.model.BeneServiceExceptionSetup;
import com.amg.exchange.remittance.model.BeneficaryAccount;
import com.amg.exchange.remittance.model.BeneficaryContact;
import com.amg.exchange.remittance.model.BeneficaryMaster;
import com.amg.exchange.remittance.model.BeneficaryRelationship;
import com.amg.exchange.remittance.model.BeneficaryStatus;
import com.amg.exchange.remittance.model.BenificiaryListView;
import com.amg.exchange.remittance.model.CollectionDetailView;
import com.amg.exchange.remittance.model.CollectionPaymentDetailsView;
import com.amg.exchange.remittance.model.CustomerDeclerationView;
import com.amg.exchange.remittance.model.CustomerSpecialDealAppl;
import com.amg.exchange.remittance.model.DebitAutendicationView;
import com.amg.exchange.remittance.model.KnetLog;
import com.amg.exchange.remittance.model.PaymentModeDesc;
import com.amg.exchange.remittance.model.PendingTransferRequestEnquiryView;
import com.amg.exchange.remittance.model.ProductGroup;
import com.amg.exchange.remittance.model.PurposeOfRemittanceView;
import com.amg.exchange.remittance.model.Relations;
import com.amg.exchange.remittance.model.RelationsDescription;
import com.amg.exchange.remittance.model.RemitApplAml;
import com.amg.exchange.remittance.model.RemittanceAppBenificiary;
import com.amg.exchange.remittance.model.RemittanceApplication;
import com.amg.exchange.remittance.model.RemittanceApplicationPurpose;
import com.amg.exchange.remittance.model.RemittanceApplicationView;
import com.amg.exchange.remittance.model.RemittanceBranchWiseEnquiryView;
import com.amg.exchange.remittance.model.RoutingDetails;
import com.amg.exchange.remittance.model.ServiceApplicabilityRule;
import com.amg.exchange.remittance.model.ShoppingCartDetails;
import com.amg.exchange.remittance.model.SpecialRateRequest;
import com.amg.exchange.remittance.model.SwiftMaster;
import com.amg.exchange.remittance.model.ViewAmtbCoupon;
import com.amg.exchange.remittance.model.ViewBeneServiceCurrency;
import com.amg.exchange.remittance.model.ViewHODirectEFT;
import com.amg.exchange.remittance.model.ViewHODirectInDirect;
import com.amg.exchange.remittance.model.ViewStatesForICASH;
import com.amg.exchange.remittance.model.ViewStock;
import com.amg.exchange.remittance.model.ViewSubAgent;
import com.amg.exchange.remittance.service.IPersonalRemittanceService;
import com.amg.exchange.stoppayment.model.RemittanceTransaction;
import com.amg.exchange.treasury.deal.supplier.model.ApplicationSetup;
import com.amg.exchange.treasury.model.BankAccountLength;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.BeneCountryService;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.CustomerSpecialDealRequest;
import com.amg.exchange.treasury.model.DeliveryModeDesc;
import com.amg.exchange.treasury.model.RemittanceModeDescription;
import com.amg.exchange.treasury.model.ServiceGroupMasterDesc;
import com.amg.exchange.treasury.model.ServiceMasterDesc;
import com.amg.exchange.util.AMGException;

@Service("personalRemittanceService")
public class PersonalRemittanceServiceImpl implements IPersonalRemittanceService {

	@Autowired
	IPersonalRemittanceDao personalRemittanceDao;

	/**
	 * @return the personalRemittanceDao
	 */
	public IPersonalRemittanceDao getPersonalRemittanceDao() {
		return personalRemittanceDao;
	}

	/**
	 * @param personalRemittanceDao
	 *                  the personalRemittanceDao to set
	 */
	public void setPersonalRemittanceDao(IPersonalRemittanceDao personalRemittanceDao) {
		this.personalRemittanceDao = personalRemittanceDao;
	}

	@Transactional
	@Override
	public CustomerIdProof getCustomerList(String identitytype) {
		return getPersonalRemittanceDao().getCustomerList(identitytype);
	}

	@Override
	@Transactional
	public List<BeneficaryStatus> getBeneficaryStatusList() {
		return getPersonalRemittanceDao().getBeneficaryStatusList();
	}

	@Override
	@Transactional
	public List<CustomerIdProof> getCustomerDetails(String customerId, BigDecimal cardType) {
		return getPersonalRemittanceDao().getCustomerDetails(customerId, cardType);
	}

	@Override
	@Transactional
	public List<Relations> getRelationsList() {
		return getPersonalRemittanceDao().getRelationsList();
	}

	@Override
	@Transactional
	public List<BankMaster> getbanklist(BigDecimal currencyId) {
		return getPersonalRemittanceDao().getbanklist(currencyId);
	}

	@Override
	@Transactional
	public List<BankBranch> getBankbranchlist(BigDecimal bankId) {
		return getPersonalRemittanceDao().getBankbranchlist(bankId);
	}

	@Override
	@Transactional
	public List<CountryMasterDesc> getCountryList(BigDecimal languageId) {
		return getPersonalRemittanceDao().getCountryList(languageId);
	}

	@Override
	@Transactional
	public List<CurrencyMaster> getCurrencyList(BigDecimal countryId) {
		return getPersonalRemittanceDao().getCurrencyList(countryId);
	}

	// for save in beneficary master by subramanian
	@Override
	@Transactional
	public void saveBeneficiaryMaster(BeneficaryMaster beneficaryMaster) {
		getPersonalRemittanceDao().saveBeneficaryMaster(beneficaryMaster);
	}

	@Override
	@Transactional
	public void saveBeneficiaryRelationship(BeneficaryRelationship beneficaryRelationship) {
		getPersonalRemittanceDao().saveBeneficaryRelationship(beneficaryRelationship);
	}

	@Override
	@Transactional
	public void saveBeneficiaryAccount(BeneficaryAccount beneficaryAccount) {
		getPersonalRemittanceDao().savebeneficaryAccount(beneficaryAccount);
	}

	@Override
	@Transactional
	public void saveBeneficiaryTelephone(BeneficaryContact beneficaryTelaphone) {
		getPersonalRemittanceDao().saveBeneficaryTelephone(beneficaryTelaphone);
	}

	@Override
	@Transactional
	public List<BeneficaryAccount> getCustomerBeneficaryDetails(BigDecimal masterseqId) {
		return getPersonalRemittanceDao().getCustomerBeneficaryDetails(masterseqId);
	}

	@Override
	@Transactional
	public List<BeneficaryRelationship> isCoustomerExistInDB(BigDecimal customerId) {
		return getPersonalRemittanceDao().isCoustomerExistInDB(customerId);
	}

	@Override
	@Transactional
	public List<BeneficaryContact> getTelephoneDetails(BigDecimal masterId) {
		return getPersonalRemittanceDao().getTelephoneDetails(masterId);
	}

	@Override
	@Transactional
	public List<PaymentModeDesc> getPaymentModeDetails(BigDecimal languageId) {
		return personalRemittanceDao.getPaymentModeDetails(languageId);
	}

	@Override
	@Transactional
	public List<BeneficaryMaster> getAllMasterValues(BigDecimal masterId) {
		return personalRemittanceDao.getAllMasterValues(masterId);
	}

	@Override
	@Transactional
	public List<RoutingDetails> getSpecificListBankBranch(BigDecimal countryid, BigDecimal Bankid) {
		return getPersonalRemittanceDao().getSpecificListBankBranch(countryid, Bankid);
	}

	@Override
	@Transactional
	public List<BankBranch> getALLListBankBranch(BigDecimal countryid, BigDecimal Bankid) {
		return getPersonalRemittanceDao().getALLListBankBranch(countryid, Bankid);
	}

	@Override
	@Transactional
	public List<BigDecimal> getExchangeRateAllValues(BigDecimal appcountryId, BigDecimal routingcountry, BigDecimal branchId, BigDecimal companyId, BigDecimal routingbankId, BigDecimal serviceId, BigDecimal deliveryId, BigDecimal remitId, BigDecimal foreigncurrencyId, BigDecimal currencyId,
			BigDecimal customerId, String customertypeId, String loyaltypt, String spldeal, String oversea, BigDecimal fcamount, String spotrate, String cashround) throws AMGException {
		return getPersonalRemittanceDao().getExchangeRateAllValues(appcountryId, routingcountry, branchId, companyId, routingbankId, serviceId, deliveryId, remitId, foreigncurrencyId, currencyId, customerId, customertypeId, loyaltypt, spldeal, oversea, fcamount, spotrate, cashround);
	}

	@Override
	@Transactional
	public void saveSpecialRateRequest(SpecialRateRequest specialRateRequest) {
		getPersonalRemittanceDao().saveSpecialRateRequest(specialRateRequest);
	}

	@Override
	@Transactional
	public Map<String, ServiceApplicabilityRule> getComponentBehavior(List<String> componentList, BigDecimal applicationCountryId, BigDecimal currencyId, BigDecimal remittanceModeId, BigDecimal deliveryModeId) {
		return getPersonalRemittanceDao().getComponentBehavior(componentList, applicationCountryId, currencyId, remittanceModeId, deliveryModeId);
	}

	@Override
	@Transactional
	public List<ServiceApplicabilityRule> getDynamicLevel(BigDecimal applicationCountryId, BigDecimal countryId, BigDecimal currencyId, BigDecimal remittanceModeId, BigDecimal deliveryModeId) {
		return getPersonalRemittanceDao().getDynamicLevel(applicationCountryId, countryId, currencyId, remittanceModeId, deliveryModeId);
	}

	// By subramanian For telephone validation
	@Override
	@Transactional
	public List<BeneficaryAccount> getBeneficaryAccountDetails(BigDecimal beneficiaryMasterSeqId) {
		return getPersonalRemittanceDao().getBeneficaryAccountDetails(beneficiaryMasterSeqId);
	}

	@Override
	@Transactional
	public List<BeneficaryContact> isCoustomerTelephoneExistInDB(BigDecimal telephoneNumber) {
		return getPersonalRemittanceDao().isCoustomerTelephoneExistInDB(telephoneNumber);
	}

	@Override
	@Transactional
	public List<BeneficaryMaster> getBeneficaryMasterDetails(BigDecimal beneficiaryMasterSeqId) {
		return getPersonalRemittanceDao().getBeneficaryMasterDetails(beneficiaryMasterSeqId);
	}

	@Override
	@Transactional
	public List<BeneficaryRelationship> isBenificaryRelationExistInDB(BigDecimal beneficaryMasterId) {
		return getPersonalRemittanceDao().isBenificaryRelationExistInDB(beneficaryMasterId);
	}

	@Override
	@Transactional
	public List<BeneficaryContact> isTelephoneExistInDB(BigDecimal telephoneNumber) {
		return getPersonalRemittanceDao().isTelephoneExistInDB(telephoneNumber);
	}

	@Override
	@Transactional
	public List<CustomerSpecialDealRequest> getSpecialRequestFcAmountCalList(BigDecimal customerId, BigDecimal applicationCountryId, BigDecimal bankId, BigDecimal bankCountryId) {
		return getPersonalRemittanceDao().getSpecialRequestFcAmountCalList(customerId, applicationCountryId, bankId, bankCountryId);
	}

	@Override
	@Transactional
	public void calculateRemittanceAmount(BigDecimal specialCustomerPk, BigDecimal utilizedAmount) {
		getPersonalRemittanceDao().calculateRemittanceAmount(specialCustomerPk, utilizedAmount);
	}

	@Override
	@Transactional
	public List<BeneficaryAccount> isBankAccountNumberExistInDb(String bankAccountNumber, BigDecimal countryId, BigDecimal BankId, BigDecimal bankBranchId) {
		return getPersonalRemittanceDao().isBankAccountNumberExistInDb(bankAccountNumber, countryId, BankId, bankBranchId);
	}

	@Override
	@Transactional
	public List<BeneficaryAccount> isBeneficaryAccountExistInDb(BigDecimal beneficaryMasterSeqId) {
		return getPersonalRemittanceDao().isBeneficaryAccountExistInDb(beneficaryMasterSeqId);
	}

	/*
	 * @Override
	 * 
	 * @Transactional public List<BeneficaryRelationship>
	 * isBeneficaryAccountCustomerExistInDb(BigDecimal
	 * beneficaryMasterSeqId,BigDecimal customerId){ return
	 * getPersonalRemittanceDao
	 * ().isBeneficaryAccountCustomerExistInDb(beneficaryMasterSeqId
	 * ,customerId); }
	 */
	@Override
	@Transactional
	public List<RemittanceModeDescription> getremittanceDesc(BigDecimal langId) {
		return getPersonalRemittanceDao().listRemittanceDesc(langId);
	}

	@Override
	@Transactional
	public List<DeliveryModeDesc> lstDeliveryMode(BigDecimal languageId) {
		return getPersonalRemittanceDao().lstDeliveryMode(languageId);
	}

	@Override
	@Transactional
	public void saveRemittanceApplication(RemittanceApplication remittanceApplication) {
		getPersonalRemittanceDao().saveRemittanceApplication(remittanceApplication);
	}

	@Override
	@Transactional
	public List<RemittanceApplication> getRemittanceApplicationAllDetails(BigDecimal customerid) {
		return getPersonalRemittanceDao().getRemittanceApplicationAllDetails(customerid);
	}

	@Override
	@Transactional
	public BigDecimal getUtilizedAmount(BigDecimal customerId, BigDecimal applicationCountryId, BigDecimal bankId, BigDecimal bankCountryId) {
		return getPersonalRemittanceDao().getUtilizedAmount(customerId, applicationCountryId, bankId, bankCountryId);
	}

	@Override
	@Transactional
	public void saveRemittanceAppBeneficiary(RemittanceAppBenificiary remittanceAppBen) {
		getPersonalRemittanceDao().saveRemittanceAppBeneficiary(remittanceAppBen);
	}

	@Override
	@Transactional
	public void saveAdditionalInsData(AdditionalInstructionData additionalInsData) {
		getPersonalRemittanceDao().saveAdditionalInsData(additionalInsData);
	}

	@Override
	@Transactional
	public void saveRemitAppAml(RemitApplAml remitAppAml) {
		getPersonalRemittanceDao().saveRemitAppAml(remitAppAml);
	}

	@Override
	@Transactional
	public List<AdditionalBankRuleMap> getDynamicLevelMatch(BigDecimal countryId, String flexiField) {
		return getPersonalRemittanceDao().getDynamicLevelMatch(countryId, flexiField);
	}

	@Override
	@Transactional
	public List<ShoppingCartDetails> getShoppingCartDetails(BigDecimal customerNo) {
		return getPersonalRemittanceDao().getShoppingCartDetails(customerNo);
	}

	@Override
	@Transactional
	public void saveCollectTableData(Collect collect) {
		getPersonalRemittanceDao().saveCollectTableData(collect);
	}

	@Override
	@Transactional
	public void saveCollectDetailTableData(CollectDetail collectDetail) {
		getPersonalRemittanceDao().saveCollectDetailTableData(collectDetail);
	}

	@Override
	@Transactional
	public List<SpecialRateRequest> getDocumentSeriality() {
		return getPersonalRemittanceDao().getDocumentSeriality();
	}

	@Override
	@Transactional
	public void updateReceiptPaymentTableData(BigDecimal receiptPaymentPk,String status) {
		getPersonalRemittanceDao().updateReceiptPaymentTableData(receiptPaymentPk,status);
	}

	@Override
	@Transactional
	public void updateRemittanceApplicationTableData(BigDecimal remitanceAppPk,String status) {
		getPersonalRemittanceDao().updateRemittanceApplicationTableData(remitanceAppPk,status);
	}

	@Override
	@Transactional
	public BigDecimal getReceiptPaymentTablePk(BigDecimal customerId, BigDecimal documentNo) {
		return getPersonalRemittanceDao().getReceiptPaymentTablePk(customerId, documentNo);
	}

	/*@Override
	@Transactional
	public BigDecimal getRemittanceApplicationPk(BigDecimal customerId, BigDecimal documentNo) {
		return getPersonalRemittanceDao().getRemittanceApplicationPk(customerId, documentNo);
	}*/

	@Override
	@Transactional
	public void saveForeignCurrencyAdjust(ForeignCurrencyAdjust foreignCurrencyAdjust) {
		getPersonalRemittanceDao().saveForeignCurrencyAdjust(foreignCurrencyAdjust);
	}

	@Override
	@Transactional
	public void updateForeignCurrencyAdjustAppTableData(BigDecimal foreignCurrencyAdjustAppPk,String status) {
		getPersonalRemittanceDao().updateForeignCurrencyAdjustAppTableData(foreignCurrencyAdjustAppPk,status);
	}

	@Override
	@Transactional
	public List<ForeignCurrencyAdjustApp> getForeignCurrencyAdjustAppPk(BigDecimal customerId, BigDecimal documentNo) {
		return getPersonalRemittanceDao().getForeignCurrencyAdjustAppPk(customerId, documentNo);
	}

	// start by subramanian 31/01/2015
	@Override
	@Transactional
	public List<Object> isAmlTranxAmountCheck(BigDecimal appcountryId, BigDecimal beneCountryId, BigDecimal customerId, BigDecimal benificiarySeqId, BigDecimal fcamount) throws AMGException {
		return getPersonalRemittanceDao().isAmlTranxAmountCheck(appcountryId, beneCountryId, customerId, benificiarySeqId, fcamount);
	}

	@Override
	@Transactional
	public HashMap<String, String> isAmlTranxAmountCheckForRemittance(BigDecimal appcountryId, BigDecimal beneCountryId, BigDecimal customerId, BigDecimal benificiarySeqId, BigDecimal fcamount) throws AMGException {
		return getPersonalRemittanceDao().isAmlTranxAmountCheckForRemittance(appcountryId, beneCountryId, customerId, benificiarySeqId, fcamount);
	}

	@Override
	@Transactional
	public List<AmlLimit> getAmlLimitCheckList() {
		return getPersonalRemittanceDao().getAmlLimitCheckList();
	}

	// end by subramanian 31/01/2015
	@Override
	@Transactional
	public List<CurrencyMaster> getCurrencyMasterList(BigDecimal countryId, BigDecimal currencyId) {
		return getPersonalRemittanceDao().getCurrencyMasterList(countryId, currencyId);
	}

	@Override
	@Transactional
	public List<AdditionalBankRuleAddData> getAdditionalBankData(BigDecimal bankId, BigDecimal additionalBankFieldId) {
		return getPersonalRemittanceDao().getAdditionalBankData(bankId, additionalBankFieldId);
	}

	@Override
	@Transactional
	public Map<String, Object> checkTelephoneExistWithCustId(BigDecimal telephoneNo, BigDecimal customerID, String teleCountryID) {
		return getPersonalRemittanceDao().checkTelephoneExistWithCustId(telephoneNo, customerID, teleCountryID);
	}

	@Override
	@Transactional
	public List<BeneficaryRelationship> checkRelationExistWithCustId(BigDecimal relationId, BigDecimal customerID) {
		return getPersonalRemittanceDao().checkRelationExistWithCustId(relationId, customerID);
	}

	@Override
	@Transactional
	public BigDecimal populateTelephoneCountry(BigDecimal countryId) {
		return getPersonalRemittanceDao().populateTelephoneCountry(countryId);
	}

	@Override
	@Transactional
	public List<BankBranch> getBankBranchList(BigDecimal countryId, BigDecimal BankId, BigDecimal formBranchCode, BigDecimal toBranchCode) {
		return getPersonalRemittanceDao().getBankBranchList(countryId, BankId, formBranchCode, toBranchCode);
	}

	@Override
	@Transactional
	public List<BeneCountryService> getBeneCountryServiceList(BigDecimal countryId) {
		return getPersonalRemittanceDao().getBeneCountryServiceList(countryId);
	}

	@Override
	@Transactional
	public List<BeneServiceExceptionSetup> getBeneServiceExceptionSetupList(BigDecimal countryId, BigDecimal BankId, BigDecimal formBranchCode, BigDecimal toBranchCode) {
		return getPersonalRemittanceDao().getBeneServiceExceptionSetupList(countryId, BankId, formBranchCode, toBranchCode);
	}

	@Override
	@Transactional
	public List<BeneCountryService> getBeneCountryAllServiceList(BigDecimal countryId, BigDecimal languageId, BigDecimal appCountryId) {
		return getPersonalRemittanceDao().getBeneCountryAllServiceList(countryId, languageId, appCountryId);
	}

	@Override
	@Transactional
	public List<RemittanceModeDescription> getRemittancDesc(BigDecimal remiId) {
		return getPersonalRemittanceDao().getRemittancDesc(remiId);
	}

	@Override
	@Transactional
	public List<DeliveryModeDesc> getDeliveryModeDesc(BigDecimal deliveryId) {
		return getPersonalRemittanceDao().getDeliveryModeDesc(deliveryId);
	}

	@Override
	@Transactional
	public List<Object> getCountryServiceList(BigDecimal appCountryId, BigDecimal languageId, BigDecimal countryId) {
		return getPersonalRemittanceDao().getCountryServiceList(appCountryId, languageId, countryId);
	}

	@Override
	@Transactional
	public void saveBeneServiceExceptionSetup(BeneServiceExceptionSetup beneServiceException) {
		getPersonalRemittanceDao().saveBeneServiceExceptionSetup(beneServiceException);
	}

	@Override
	@Transactional
	public List<Object> getBeneExceptionSetupList(BigDecimal countryId, BigDecimal languageId, BigDecimal appCountryId) {
		return getPersonalRemittanceDao().getBeneExceptionSetupList(countryId, languageId, appCountryId);
	}

	@Override
	@Transactional
	public boolean getBeneServiceExceptionSetup(BigDecimal countryId, BigDecimal branchId, BigDecimal deliveryId, BigDecimal remittanceId) {
		return getPersonalRemittanceDao().getBeneServiceExceptionSetup(countryId, branchId, deliveryId, remittanceId);
	}

	@Override
	@Transactional
	public List<BeneServiceExceptionSetup> getBeneExceptionSetupAllList() {
		return getPersonalRemittanceDao().getBeneExceptionSetupAllList();
	}

	@Override
	@Transactional
	public List<RemittanceModeDescription> listRemittanceDesc(BigDecimal remittanceId, BigDecimal langId) {
		return getPersonalRemittanceDao().listRemittanceDesc(remittanceId, langId);
	}

	@Override
	@Transactional
	public List<DeliveryModeDesc> lstDeliveryMode(BigDecimal deliveryId, BigDecimal langId) {
		return getPersonalRemittanceDao().lstDeliveryMode(deliveryId, langId);
	}

	@Override
	@Transactional
	public List<BeneServiceExceptionSetup> getBeneExceptionSetupList(BigDecimal exceptionSetupId) {
		return getPersonalRemittanceDao().getBeneExceptionSetupList(exceptionSetupId);
	}

	@Override
	@Transactional
	public List<BeneServiceExceptionSetup> getExceptionSetupListForActiveInactive() {
		return getPersonalRemittanceDao().getExceptionSetupListForActiveInactive();
	}

	@Override
	@Transactional
	public List<BeneServiceExceptionSetup> getBeneDeleteServiceExceptionSetupList(BigDecimal countryId, BigDecimal BankId, BigDecimal formBranchCode, BigDecimal toBranchCode) {
		return getPersonalRemittanceDao().getBeneDeleteServiceExceptionSetupList(countryId, BankId, formBranchCode, toBranchCode);
	}

	@Override
	@Transactional
	public List<RemittanceApplication> getRemittanceTransactionList(BigDecimal transferNo) {
		return getPersonalRemittanceDao().getRemittanceTransactionList(transferNo);
	}

	@Override
	@Transactional
	public String getRemittanceServiceRuleName(BigDecimal countryId, BigDecimal currencyId, BigDecimal bankId, BigDecimal remittanceId, BigDecimal deliveryId) {
		return getPersonalRemittanceDao().getRemittanceServiceRuleName(countryId, currencyId, bankId, remittanceId, deliveryId);
	}

	@Override
	@Transactional
	public List<BankAccountLength> getBankAccountLengthByBank(BigDecimal bankId) {
		return getPersonalRemittanceDao().getBankAccountLengthByBank(bankId);
	}

	@Override
	@Transactional
	public boolean getBanKBranchFromBeneExceptionSetup(BigDecimal countryId, BigDecimal bankId, BigDecimal branchId) {
		return getPersonalRemittanceDao().getBanKBranchFromBeneExceptionSetup(countryId, bankId, branchId);
	}

	@Override
	@Transactional
	public List<BeneServiceExceptionSetup> getExceptionSetupListForEnquiry(BigDecimal countryId, BigDecimal bankId, BigDecimal currencyId) {
		return getPersonalRemittanceDao().getExceptionSetupListForEnquiry(countryId, bankId, currencyId);
	}

	@Override
	@Transactional
	public List<ViewBeneServiceCurrency> getViewBeneCurrency(BigDecimal benifisCountryId) {
		return getPersonalRemittanceDao().getViewBeneCurrency(benifisCountryId);
	}

	@Override
	@Transactional
	public String getBankBranchName(BigDecimal bankBranchId) {
		return getPersonalRemittanceDao().getBankBranchName(bankBranchId);
	}

	@Override
	@Transactional
	public String getBranchName(BigDecimal countryId, BigDecimal bankId, BigDecimal bankBranchId) {
		return getPersonalRemittanceDao().getBranchName(countryId, bankId, bankBranchId);
	}

	@Override
	@Transactional
	public List<ServiceMasterDesc> fetchCashServiceId(String serviceDesc, BigDecimal langId) {
		return getPersonalRemittanceDao().fetchCashServiceId(serviceDesc, langId);
	}

	@Override
	@Transactional
	public List<PopulateData> lstOfRemittance(BigDecimal routingCountryId, BigDecimal beneficaryCurrencyId, BigDecimal serviceId, BigDecimal routingBankId) {
		return getPersonalRemittanceDao().lstOfRemittance(routingCountryId, beneficaryCurrencyId, serviceId, routingBankId);
	}

	@Override
	@Transactional
	public List<PopulateData> lstOfDelivery(BigDecimal routingCountryId, BigDecimal beneficaryCurrencyId, BigDecimal serviceId, BigDecimal routingBankId) {
		return getPersonalRemittanceDao().lstOfDelivery(routingCountryId, beneficaryCurrencyId, serviceId, routingBankId);
	}

	@Override
	@Transactional
	public void updateRemitTranxTable(Collect collect, List<ShoppingCartDataTableBean> lstToUpdate) {
		getPersonalRemittanceDao().updateRemitTranxTable(collect, lstToUpdate);
	}

	@Override
	@Transactional
	public void saveUpdateCustSplDealUtilizedAmount(BigDecimal custSplDealId, BigDecimal utilizedAmount) {
		getPersonalRemittanceDao().saveUpdateCustSplDealUtilizedAmount(custSplDealId, utilizedAmount);
	}

	@Override
	@Transactional
	public void deleteShoppingCartByApplId(BigDecimal remittanceApplicationId) {
		getPersonalRemittanceDao().deleteShoppingCartByApplId(remittanceApplicationId);
	}

	@Override
	@Transactional
	public HashMap<String, Object> findUserCustomerOrEmployee(String userName, String customertype) {
		return getPersonalRemittanceDao().findUserCustomerOrEmployee(userName, customertype);
	}

	@Override
	@Transactional
	public List<RelationsDescription> getRelationsDescriptionList(BigDecimal languageId) {
		return getPersonalRemittanceDao().getRelationsDescriptionList(languageId);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updatePayIdPayTokenInAppTrnx(List<ShoppingCartDataTableBean> lstSelected, String payToken, String payId) {
		getPersonalRemittanceDao().updatePayIdPayTokenInAppTrnx(lstSelected, payToken, payId);
	}

	@Override
	@Transactional
	public List<ShoppingCartDetails> getRecordsRemittanceApplication(BigDecimal documentNo) {
		return getPersonalRemittanceDao().getRecordsRemittanceApplication(documentNo);
	}

	@Override
	@Transactional
	public List<RemittanceApplicationView> getRecordsRemittanceReceipt(BigDecimal documentNo) {
		return getPersonalRemittanceDao().getRecordsRemittanceReceipt(documentNo);
	}

	@Override
	@Transactional
	public List<RemittanceApplicationView> getRecordsForRemittanceReceiptReport(BigDecimal documentNo,BigDecimal financeYear,String documentCode){
		return getPersonalRemittanceDao().getRecordsForRemittanceReceiptReport(documentNo, financeYear, documentCode);
	}



	@Override
	@Transactional
	public List<PurposeOfRemittanceView> getPurposeOfRemittance(BigDecimal documentNo, BigDecimal documentYear){
		return getPersonalRemittanceDao().getPurposeOfRemittance(documentNo, documentYear);
	}

	@Override
	@Transactional
	public List<RemittanceApplicationPurpose> getPurposeOfRemittanceForApplication(BigDecimal applicationCountryId,BigDecimal documentFinancialYear,BigDecimal documentId,BigDecimal documentNo){
		return getPersonalRemittanceDao().getPurposeOfRemittanceForApplication(applicationCountryId,documentFinancialYear,documentId,documentNo);
	}


	@Override
	@Transactional
	public List<ServiceGroupMasterDesc> fetchCashServiceGorupId(String cASHNAME, BigDecimal languageId) {
		return getPersonalRemittanceDao().fetchCashServiceGorupId(cASHNAME, languageId);
	}

	@Override
	@Transactional
	public List<ServiceGroupMasterDesc> getAllServiceGroupDesc(BigDecimal languageId) {
		return getPersonalRemittanceDao().getAllServiceGroupDesc(languageId);
	}

	@Override
	@Transactional
	public boolean bankIndcheckingbank(String bankInd, BigDecimal bankId) {
		return getPersonalRemittanceDao().bankIndcheckingbank(bankInd, bankId);
	}

	@Override
	@Transactional
	public List<BeneCountryService> getServiceIdBeneCountrySer(BigDecimal beneficaryCountryId, BigDecimal beneficaryCurrencyId, BigDecimal serviceId, BigDecimal bankId) {
		return getPersonalRemittanceDao().getServiceIdBeneCountrySer(beneficaryCountryId, beneficaryCurrencyId, serviceId, bankId);
	}

	@Override
	@Transactional
	public List<CutomerDetailsView> getCustomerDetails(BigDecimal customerId) {
		return getPersonalRemittanceDao().getCustomerDetails(customerId);
	}

	@Override
	@Transactional
	public List<BeneficaryRelationship> getCustomerBeneficaryDetailswithRelations(BigDecimal beneficaryMasterSeqId) {
		return getPersonalRemittanceDao().getCustomerBeneficaryDetailswithRelations(beneficaryMasterSeqId);
	}

	@Override
	@Transactional
	public List<BeneficaryRelationship> isBenificaryRelationExist(BigDecimal beneficaryMasterSeqId, BigDecimal beneficaryAccountSeqId) {
		return getPersonalRemittanceDao().isBenificaryRelationExist(beneficaryMasterSeqId, beneficaryAccountSeqId);
	}

	@Override
	@Transactional
	public Map<String, Object> checkTelephoneExist(BigDecimal telephoneNumber, String countryCode, String string) {
		return getPersonalRemittanceDao().checkTelephoneExist(telephoneNumber, countryCode, string);
	}

	@Override
	@Transactional
	public List<BeneficaryAccount> isBankAccountNumberExist(BigDecimal bankAccountNumber, BigDecimal benifisBankId, BigDecimal benifisCurrencyId, BigDecimal benifisCountryId, BigDecimal servicebankBranchId) {
		return getPersonalRemittanceDao().isBankAccountNumberExist(bankAccountNumber, benifisBankId, benifisCurrencyId, benifisCountryId, servicebankBranchId);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateBeneficaryData(BeneficaryMaster beneficaryMaster, BeneficaryAccount account, BeneficaryRelationship beneficaryRelationship, BeneficaryContact contact) throws Exception {
		getPersonalRemittanceDao().updateBeneficaryData(beneficaryMaster, account, beneficaryRelationship, contact);
	}

	@Override
	@Transactional
	public List<BeneficaryAccount> getCustomerBeneficaryDetailswithAll(BigDecimal benificaryMasterId, String type) {
		return getPersonalRemittanceDao().getCustomerBeneficaryDetailswithAll(benificaryMasterId, type);
	}

	@Override
	@Transactional
	public BigDecimal getserviceGroupforCash(BigDecimal languageId) {
		return getPersonalRemittanceDao().getserviceGroupforCash(languageId);
	}

	@Override
	@Transactional
	public Map<String, Object> checkTelephoneExistWithCustIdwithPhone(BigDecimal mobileNumber, BigDecimal customerNo, String countryCode, String type) {
		return getPersonalRemittanceDao().checkTelephoneExistWithCustIdwithPhone(mobileNumber, customerNo, countryCode, type);
	}

	@Override
	@Transactional
	public List<BeneficaryRelationship> getRelationshipRecord(BigDecimal benificaryMasterId, BigDecimal beneficaryAccountSeqId) {
		return getPersonalRemittanceDao().getRelationshipRecord(benificaryMasterId, beneficaryAccountSeqId);
	}

	@Override
	@Transactional
	public List<BeneficaryAccount> getCustomerBeneficaryDetailswithAccountNO(BigDecimal benificaryMasterId, String accountNo, String type) {
		return getPersonalRemittanceDao().getCustomerBeneficaryDetailswithAccountNO(benificaryMasterId, accountNo, type);
	}

	@Override
	@Transactional
	public List<BeneficaryAccount> isCashAccountExist(BigDecimal benifisBankId, BigDecimal benifisCurrencyId, BigDecimal benifisCountryId, BigDecimal servicebankBranchId) {
		return getPersonalRemittanceDao().isCashAccountExist(benifisBankId, benifisCurrencyId, benifisCountryId, servicebankBranchId);
	}

	@Override
	@Transactional
	public BigDecimal getbankIndicator(String bankIndicatorAgentBank, BigDecimal languageId) {
		return getPersonalRemittanceDao().getbankIndicator(bankIndicatorAgentBank, languageId);
	}

	@Override
	@Transactional
	public BigDecimal roundingTotalNetAmountbyFunction(BigDecimal applcountryId, BigDecimal totalNetAmount, String roundstatus) throws AMGException {
		// TODO Auto-generated method stub
		return getPersonalRemittanceDao().roundingTotalNetAmountbyFunction(applcountryId, totalNetAmount, roundstatus);
	}

	@Override
	@Transactional
	public void saveApplicationTransactionModifiedRecordByRound(ShoppingCartDataTableBean lstShoppingCart) {
		// TODO Auto-generated method stub
		getPersonalRemittanceDao().saveApplicationTransactionModifiedRecordByRound(lstShoppingCart);
	}

	@Override
	@Transactional
	public void updateApplTrnxError(List<RemittanceApplication> lstShoppingCart, String status) {
		// TODO Auto-generated method stub
		getPersonalRemittanceDao().updateApplTrnxError(lstShoppingCart, status);
	}

	@Override
	@Transactional
	public List<RemittanceApplication> fetchRemitApplTrnxRecordsByPayId(String paymentId) {
		// TODO Auto-generated method stub
		return getPersonalRemittanceDao().fetchRemitApplTrnxRecordsByPayId(paymentId);
	}

	@Override
	@Transactional
	public void updatePayTokenNull(List<RemittanceApplication> lstPayIdDetails, String status) {
		// TODO Auto-generated method stub
		getPersonalRemittanceDao().updatePayTokenNull(lstPayIdDetails, status);
	}

	@Override
	@Transactional
	public void callProcedureToCancelRecordsinApplTrnx(String paymentId, String status) throws AMGException {
		// TODO Auto-generated method stub
		getPersonalRemittanceDao().callProcedureToCancelRecordsinApplTrnx(paymentId, status);
	}

	@Override
	@Transactional
	public List<AdditionalBankDetailsView> fetchAdditionalDetails(BigDecimal additionalBankRuleId) {
		return getPersonalRemittanceDao().fetchAdditionalDetails(additionalBankRuleId);
	}

	@Override
	@Transactional
	public String getCustomerType(BigDecimal componentId) {
		return getPersonalRemittanceDao().getCustomerType(componentId);
	}

	@Override
	@Transactional
	public void saveAuthorizedLog(AuthorizedLog authorizedLog) {
		getPersonalRemittanceDao().saveAuthorizedLog(authorizedLog);
	}

	@Override
	@Transactional
	public HashMap<String, String> getRoutingBankSetupDetails(HashMap<String, String> inputValue) throws AMGException {
		return getPersonalRemittanceDao().getRoutingBankSetupDetails(inputValue);
	}

	@Override
	@Transactional
	public List<PopulateData> getRoutingCountryList(BigDecimal appLicationCountryId, BigDecimal beneBankId, BigDecimal beneBankBranchId, BigDecimal countryId, BigDecimal beneCurrencyId, BigDecimal serviceMasterId, BigDecimal languageId) {
		return getPersonalRemittanceDao().getRoutingCountryList(appLicationCountryId, beneBankId, beneBankBranchId, countryId, beneCurrencyId, serviceMasterId, languageId);
	}

	@Override
	@Transactional
	public List<PopulateData> getRoutingBankList(BigDecimal appLicationCountryId, BigDecimal beneBankId, BigDecimal beneBankBranchId, BigDecimal countryId, BigDecimal beneCurrencyId, BigDecimal serviceMasterId, BigDecimal routingCountryId, Boolean ttCheck) {
		return getPersonalRemittanceDao().getRoutingBankList(appLicationCountryId, beneBankId, beneBankBranchId, countryId, beneCurrencyId, serviceMasterId, routingCountryId, ttCheck);
	}

	@Override
	@Transactional
	public List<PopulateData> getRoutingBranchList(BigDecimal appLicationCountryId, BigDecimal beneBankId, BigDecimal beneBankBranchId, BigDecimal countryId, BigDecimal beneCurrencyId, BigDecimal serviceMasterId, BigDecimal routingCountryId, BigDecimal routingbankId, BigDecimal remitId, BigDecimal deliveryId) {
		return getPersonalRemittanceDao().getRoutingBranchList(appLicationCountryId, beneBankId, beneBankBranchId, countryId, beneCurrencyId, serviceMasterId, routingCountryId, routingbankId, remitId, deliveryId);
	}

	@Override
	@Transactional
	public List<PopulateData> getRemittanceListByCountryBank(BigDecimal appLicationCountryId, BigDecimal beneBankId, BigDecimal beneBankBranchId, BigDecimal countryId, BigDecimal beneCurrencyId, BigDecimal serviceMasterId, BigDecimal routingCountryId, BigDecimal routingbankId, BigDecimal languageId) {
		return getPersonalRemittanceDao().getRemittanceListByCountryBank(appLicationCountryId, beneBankId, beneBankBranchId, countryId, beneCurrencyId, serviceMasterId, routingCountryId, routingbankId, languageId);
	}

	@Override
	@Transactional
	public List<PopulateData> getDeliveryListByCountryBankRemit(BigDecimal appLicationCountryId, BigDecimal beneBankId, BigDecimal beneBankBranchId, BigDecimal countryId, BigDecimal beneCurrencyId, BigDecimal serviceMasterId, BigDecimal routingCountryId, BigDecimal routingbankId, BigDecimal remittanceId, BigDecimal languageId) {
		return getPersonalRemittanceDao().getDeliveryListByCountryBankRemit(appLicationCountryId, beneBankId, beneBankBranchId, countryId, beneCurrencyId, serviceMasterId, routingCountryId, routingbankId, remittanceId, languageId);
	}

	@Override
	@Transactional
	public String getAdditionalCheckProcedure(BigDecimal appLicationCountryId, BigDecimal customerId, BigDecimal branchId, BigDecimal beneId, BigDecimal beneCountryId, BigDecimal beneBankId, BigDecimal beneBankBranchId, String beneAccountNo, BigDecimal serviceMasterId,
			BigDecimal routingCountryId, BigDecimal routingBankId, BigDecimal routingBankBranchId, BigDecimal remittanceModeId, BigDecimal deliveryModeId, BigDecimal sourceOfIncomeId, BigDecimal exchangeRateApplied, BigDecimal localCommisionCurrencyId,
			BigDecimal localCommisionAmount, BigDecimal localChargeCurrencyId, BigDecimal localchargeAmount, BigDecimal localDelivCurrencyId, BigDecimal localDeliAmount, BigDecimal serviceProvider, BigDecimal foreignCurrencyId, BigDecimal foreignTrnxAmount,
			BigDecimal localNetCurrecnyId, BigDecimal localNetTrnxAmount, String beneSwiftBank1, String beneSwiftBank2, String errorMessage) throws AMGException {

		return getPersonalRemittanceDao().getAdditionalCheckProcedure(appLicationCountryId, customerId, branchId, beneId, beneCountryId, beneBankId, beneBankBranchId, beneAccountNo, serviceMasterId, routingCountryId, routingBankId, routingBankBranchId, remittanceModeId, deliveryModeId,
				sourceOfIncomeId, exchangeRateApplied, localCommisionCurrencyId, localCommisionAmount, localChargeCurrencyId, localchargeAmount, localDelivCurrencyId, localDeliAmount, serviceProvider, foreignCurrencyId, foreignTrnxAmount, localNetCurrecnyId,
				localNetTrnxAmount, beneSwiftBank1, beneSwiftBank2, errorMessage);
	}

	@Override
	@Transactional
	public HashMap<String, String> getRemittanceDeliveryFromRecheckApplicationProcedure(BigDecimal appLicationCountryId, BigDecimal customerId, BigDecimal branchId, BigDecimal beneId, BigDecimal beneCountryId, BigDecimal beneBankId, BigDecimal beneBankBranchId, BigDecimal beneAccountNo,
			BigDecimal serviceMasterId, BigDecimal routingCountryId, BigDecimal routingBankId, BigDecimal routingBankBranchId, BigDecimal remittanceModeId, BigDecimal deliveryModeId, BigDecimal exchangeRateApplied, BigDecimal localCommisionCurrencyId,
			BigDecimal localCommisionAmount, BigDecimal localChargeCurrencyId, BigDecimal localchargeAmount, BigDecimal localDelivCurrencyId, BigDecimal localDeliAmount, BigDecimal foreignCurrencyId, BigDecimal foreignTrnxAmount, BigDecimal localNetCurrecnyId,
			BigDecimal localNetTrnxAmount) throws AMGException {
		return getPersonalRemittanceDao().getRemittanceDeliveryFromRecheckApplicationProcedure(appLicationCountryId, customerId, branchId, beneId, beneCountryId, beneBankId, beneBankBranchId, beneAccountNo, serviceMasterId, routingCountryId, routingBankId, routingBankBranchId,
				remittanceModeId, deliveryModeId, exchangeRateApplied, localCommisionCurrencyId, localCommisionAmount, localChargeCurrencyId, localchargeAmount, localDelivCurrencyId, localDeliAmount, foreignCurrencyId, foreignTrnxAmount, localNetCurrecnyId,
				localNetTrnxAmount);
	}


	@Override
	@Transactional
	public HashMap<String, String> toFetchDetilaFromAddtionalBenficiaryDetails(BigDecimal beneficaryMasterId, BigDecimal beneficaryBankId, BigDecimal beneficaryBankBranchId, BigDecimal beneAccNumSeqId, BigDecimal routingCountry, BigDecimal routingBank, BigDecimal routingBranch, BigDecimal serviceTypeId, BigDecimal applicationId,
			BigDecimal currencyId, BigDecimal remitMode, BigDecimal deliveryMode) throws AMGException {
		return getPersonalRemittanceDao().toFetchDetilaFromAddtionalBenficiaryDetails(beneficaryMasterId, beneficaryBankId, beneficaryBankBranchId, beneAccNumSeqId, routingCountry, routingBank, routingBranch, serviceTypeId, applicationId, currencyId, remitMode, deliveryMode);
	}

	@Override
	@Transactional
	public String toFtechPurtherInstractionErrorMessaage(BigDecimal applicationCountyId, BigDecimal routingCountryId, BigDecimal routingBankId, BigDecimal currencyId, BigDecimal remittanceId, BigDecimal DeliveryId, String furtherInstruction,BigDecimal beneBankCountryId) throws AMGException {
		return getPersonalRemittanceDao().toFtechPurtherInstractionErrorMessaage(applicationCountyId, routingCountryId, routingBankId, currencyId, remittanceId, DeliveryId, furtherInstruction, beneBankCountryId);
	}

	@Override
	@Transactional
	public HashMap<String, String> toFtechSwiftBankProcedure(BigDecimal applicationCountryId, BigDecimal routingCountryId, BigDecimal currencyId, BigDecimal remittanceId, BigDecimal deliveryId, String fieldName, String beneficiarySwiftBank,BigDecimal beneBankCountryId) throws AMGException {
		return getPersonalRemittanceDao().toFtechSwiftBankProcedure( applicationCountryId,  routingCountryId,  currencyId,  remittanceId,  deliveryId,  fieldName,  beneficiarySwiftBank, beneBankCountryId);
	}

	@Override
	@Transactional
	public BigDecimal toFetchBankIdBasedOnBankName(String bankName) {
		return getPersonalRemittanceDao().toFetchBankIdBasedOnBankName(bankName);
	}

	@Override
	@Transactional
	public BigDecimal toFetchBankBranchIdBasedOnBankBranchName(String branchName) {
		return getPersonalRemittanceDao().toFetchBankBranchIdBasedOnBankBranchName(branchName);
	}

	@Override
	@Transactional
	public List<PopulateData> fetchingViewSwiftMasterByCountryId(BigDecimal beneCountyId) {
		return getPersonalRemittanceDao().fetchingViewSwiftMasterByCountryId(beneCountyId);
	}

	@Override
	@Transactional
	public HashMap<String, String> getExchangeRateValues(
			HashMap<String, String> inputValues) throws AMGException {

		return getPersonalRemittanceDao().getExchangeRateValues(inputValues);
	}

	@Override
	@Transactional
	public List<CollectionDetailView> getCollectionDetailForRemittanceReceipt(BigDecimal companyId ,BigDecimal documentNumber,BigDecimal financeYear,BigDecimal documentCode){
		return getPersonalRemittanceDao().getCollectionDetailForRemittanceReceipt(companyId, documentNumber, financeYear, documentCode);
	}

	@Override
	@Transactional
	public List<CollectionPaymentDetailsView> getCollectionPaymentDetailForRemittanceReceipt(BigDecimal companyId ,BigDecimal documentNumber,BigDecimal financeYear,BigDecimal documentCode){
		return getPersonalRemittanceDao().getCollectionPaymentDetailForRemittanceReceipt(companyId,documentNumber,financeYear,documentCode);
	}

	@Override
	@Transactional
	public BigDecimal getBeneficaryAccountId(String beneAccNum,BigDecimal bankId,BigDecimal branchId,BigDecimal currencyId) { 
		return getPersonalRemittanceDao().getBeneficaryAccountId(beneAccNum, bankId, branchId, currencyId);
	}

	@Override
	@Transactional(rollbackFor = AMGException.class)
	public void saveAllApplTransaction(HashMap<String, Object> mapAllDetailPersonalRemittanceApplSave) throws AMGException{
		getPersonalRemittanceDao().saveAllApplTransaction(mapAllDetailPersonalRemittanceApplSave);
	}

	@Override
	@Transactional
	public String getExCheckCashLimitProcedure(BigDecimal applicationCountyId,
			BigDecimal customerId, BigDecimal paymentModeId,
			BigDecimal colleAmount) throws AMGException {

		return getPersonalRemittanceDao().getExCheckCashLimitProcedure(applicationCountyId, customerId, paymentModeId, colleAmount);
	}

	@Override
	@Transactional
	public List<AdditionalBankDetailsView> getAmiecDetails(BigDecimal currencyId, BigDecimal bankId, BigDecimal remittanceModeId,BigDecimal deleveryModeId,BigDecimal countryId, String flexiField) {
		return getPersonalRemittanceDao().getAmiecDetails(currencyId, bankId, remittanceModeId, deleveryModeId, countryId, flexiField);
	}

	@Override
	@Transactional
	public HashMap<String, String> exPBankIndicatorsProcedureCheck(
			HashMap<String, String> inputValues,
			List<AddAdditionalBankData> listAdditionalBankDataTable)
					throws AMGException {
		// TODO Auto-generated method stub
		return  getPersonalRemittanceDao().exPBankIndicatorsProcedureCheck(inputValues, listAdditionalBankDataTable);
	}

	@Override
	@Transactional
	public List<AdditionalDataDisplayView> getAdditionalDataFromServiceApplicability(
			BigDecimal applicationCountryId, BigDecimal countryId,
			BigDecimal currencyId, BigDecimal remittanceModeId,
			BigDecimal deliveryModeId) throws AMGException {

		return getPersonalRemittanceDao().getAdditionalDataFromServiceApplicability(applicationCountryId, countryId, currencyId, remittanceModeId, deliveryModeId);
	}

	@Override
	@Transactional
	public HashMap<String, String> getSwitInstrustionFromServiceApplicability(HashMap<String,String> inputValues)
			throws AMGException {

		return getPersonalRemittanceDao().getSwitInstrustionFromServiceApplicability(inputValues);
	}

	@Override
	@Transactional
	public List<Employee> getEmployeeDetails(String userName){

		return getPersonalRemittanceDao().getEmployeeDetails(userName);
	}

	@Override
	@Transactional
	public List<CustomerDeclerationView> getCustomerDeclerationData(BigDecimal applicationCountryId, BigDecimal documentFinanceYear, BigDecimal documentid, BigDecimal documentNo){

		return getPersonalRemittanceDao().getCustomerDeclerationData(applicationCountryId,documentFinanceYear,documentid,documentNo);
	}

	@Override
	@Transactional
	public List<CustomerRemittanceTransactionView> getBeneficaryTxnInquiryList(BigDecimal customerId){
		return getPersonalRemittanceDao().getBeneficaryTxnInquiryList(customerId);
	}

	@Override
	@Transactional
	public HashMap<String,String> getValidateCustomerProcedure(BigDecimal applicationCountryId,BigDecimal customerId,String userName,String usertype) {
		return getPersonalRemittanceDao().getValidateCustomerProcedure(applicationCountryId,customerId,userName,usertype);
	}

	@Override
	@Transactional
	public String getValidateBeneficiaryProcedure(BigDecimal applicationCountryId, BigDecimal customerId, String userName, BigDecimal beneMasSeqId,
			BigDecimal beneAccNumSeqId) {
		return getPersonalRemittanceDao().getValidateBeneficiaryProcedure(applicationCountryId, customerId, userName, beneMasSeqId, beneAccNumSeqId);
	}

	@Override
	@Transactional
	public String getBannedNameCheckProcedure(BigDecimal applicationCountryId, String englishName, String localName) {
		return getPersonalRemittanceDao().getBannedNameCheckProcedure(applicationCountryId, englishName, localName);
	}

	@Override
	@Transactional
	public List<PopulateData> getServiceList(BigDecimal appLicationCountryId, BigDecimal beneBankId, BigDecimal beneBankBranchId,
			BigDecimal countryId, BigDecimal beneCurrencyId, String serviceGroupCode) {
		return getPersonalRemittanceDao().getServiceList(appLicationCountryId,beneBankId,beneBankBranchId,countryId,beneCurrencyId,serviceGroupCode);
	}

	@Override
	@Transactional
	public List<CurrencyWiseDenomination> getCurrencyDenominations(BigDecimal currencyId,BigDecimal countryId){
		return getPersonalRemittanceDao().getCurrencyDenominations(currencyId,countryId);
	}

	@Override
	@Transactional
	public List<ViewStock> toCheckStockForView(BigDecimal countryId, String userName, String countryBranchId, BigDecimal companyId, String currecnId){
		return getPersonalRemittanceDao().toCheckStockForView(countryId, userName, countryBranchId, companyId, currecnId);
	}

	@Override
	@Transactional
	public List<DebitAutendicationView> getdebitAutendicationList() {
		return getPersonalRemittanceDao().getdebitAutendicationList();
	}

	@Override
	@Transactional
	public List<DebitAutendicationView> getdebitAutendicationListByUserId(String colAuthorizedby,String pwd) {
		return getPersonalRemittanceDao().getdebitAutendicationListByUserId(colAuthorizedby,pwd);
	}

	@Override
	@Transactional
	public HashMap<String, String> getBannedBankCheckProcedure(BigDecimal appLicationCountryId, BigDecimal beneBankId, BigDecimal beneMasSeqId) {
		return getPersonalRemittanceDao().getBannedBankCheckProcedure(appLicationCountryId,beneBankId,beneMasSeqId);
	}

	@Override
	@Transactional
	public String insertEMOSLIVETransfer(BigDecimal applicationCountryId, BigDecimal companyId, BigDecimal documentCode, BigDecimal documentFinanceYear, BigDecimal documentNo) throws AMGException {
		return getPersonalRemittanceDao().insertEMOSLIVETransfer(applicationCountryId,companyId,documentCode,documentFinanceYear,documentNo);

	}

	@Override
	@Transactional
	public HashMap<String, String> getloyalityPointsFromProcedure(BigDecimal customerRef,Date documentDate){
		return getPersonalRemittanceDao().getloyalityPointsFromProcedure(customerRef,documentDate);

	}

	@Override
	@Transactional
	public List<PendingTransferRequestEnquiryView> getAllRecordsFromApplicationDetailView(BigDecimal countryBranchId){
		return getPersonalRemittanceDao().getAllRecordsFromApplicationDetailView(countryBranchId);

	}


	@Override
	@Transactional
	public List<CustomerIdProof> getCustomerDetailsThroughCusReg(String customerId){
		return getPersonalRemittanceDao().getCustomerDetailsThroughCusReg(customerId);

	}

	@Override
	@Transactional
	public PaymentModeDesc getvoucherModeId(String v, BigDecimal languageId) {
		return getPersonalRemittanceDao().getvoucherModeId(v,languageId);
	}

	@Override
	@Transactional(rollbackFor = {AMGException.class,TransactionException.class},propagation=Propagation.REQUIRES_NEW)
	public HashMap<String, Object> saveAllRemittanceTransaction(HashMap<String, Object> mapAllDetailPersonalRemittanceSave) throws AMGException {
		return getPersonalRemittanceDao().saveAllRemittanceTransaction(mapAllDetailPersonalRemittanceSave);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public BigDecimal saveTempCollectionwithDetailsandTempCurrencyAdjust(TempCollection tempCollection, List<TempCollectDetail> tempDetailsList, List<TempForeignCurrencyAdjust> tempAdjustList,List<AuthorizedLog> lstAuthenStoredRecords) throws Exception {
		return getPersonalRemittanceDao().saveTempCollectionwithDetailsandTempCurrencyAdjust(tempCollection,tempDetailsList,tempAdjustList,lstAuthenStoredRecords);
	}

	/*@Override
	@Transactional
	public BigDecimal getRemittanceApplicationstatus(BigDecimal customerId, BigDecimal documentNo) {
		return getPersonalRemittanceDao().getRemittanceApplicationstatus(customerId,documentNo);
	}*/

	@Override
	@Transactional
	public BigDecimal getReceiptPaymentTableStatus(BigDecimal customerId, BigDecimal documentNo) {
		return getPersonalRemittanceDao().getReceiptPaymentTableStatus(customerId,documentNo);
	}


	@Override
	@Transactional
	public List<PendingTransferRequestEnquiryView> getAllRecordsFromApplicationDetailView(BigDecimal countryBranchId, Date startDate , Date endDate, BigDecimal customerReference){
		return getPersonalRemittanceDao().getAllRecordsFromApplicationDetailView(countryBranchId, startDate, endDate,customerReference);

	}

	@Override
	@Transactional
	public String declartionLimitCheck(BigDecimal applicationCountryId, BigDecimal companyId, BigDecimal customerId, Date documentDate, String paymentMode) throws AMGException {
		return getPersonalRemittanceDao().declartionLimitCheck(applicationCountryId, companyId, customerId, documentDate, paymentMode);
	}


	@Override
	@Transactional
	public void saveKnetLogDetails(KnetLog knetLog) {
		getPersonalRemittanceDao().saveKnetLogDetails(knetLog);

	}

	@Override
	@Transactional
	public List<BeneficaryAccount> getCashProductDetails(BigDecimal beneficaryAccountSeqId)
			throws AMGException {
		return getPersonalRemittanceDao().getCashProductDetails(beneficaryAccountSeqId);


	}

	@Override
	@Transactional
	public List<PopulateData> getRemittanceListByCountryBankForCash(BigDecimal serviceGroupId)
			throws AMGException {

		return getPersonalRemittanceDao().getRemittanceListByCountryBankForCash(serviceGroupId);
	}

	@Override
	@Transactional
	public List<PopulateData> getDeliverylistByRemitIdForCash(
			BigDecimal remittanceModeId) throws AMGException {

		return getPersonalRemittanceDao().getDeliverylistByRemitIdForCash(remittanceModeId);
	}

	@Override
	@Transactional
	public HashMap<BigDecimal, BigDecimal> beneficiaryCount(List<BigDecimal> lstBeneId) {
		return getPersonalRemittanceDao().beneficiaryCount(lstBeneId);
	}

	@Override
	@Transactional
	public List<AuthicationLimitCheckView> parameterLimitCheckForSameBene() {
		return getPersonalRemittanceDao().parameterLimitCheckForSameBene();
	}

	@Override
	@Transactional
	public List<AuthicationLimitCheckView> parameterDeTails_AUTH_View(String authorization_Type) {
		return getPersonalRemittanceDao().parameterDeTails_AUTH_View(authorization_Type);
	}

	@Override
	@Transactional
	public List<CustomerSpecialDealAppl> fetchAllCustSplDealByApplTrnxDoc(BigDecimal applTrnxApplDocNum) {
		return getPersonalRemittanceDao().fetchAllCustSplDealByApplTrnxDoc(applTrnxApplDocNum);
	}

	@Override
	@Transactional
	public int updtaeCustEmail(BigDecimal customerId,String custemailId) throws AMGException {
		return getPersonalRemittanceDao().updtaeCustEmail(customerId,custemailId);
	}

	@Override
	@Transactional
	public List<ApplicationSetup> getEmailFromAppSetup(BigDecimal companyId,
			BigDecimal applicationCountryId) throws AMGException {

		return getPersonalRemittanceDao().getEmailFromAppSetup(companyId, applicationCountryId);
	}

	@Override
	@Transactional
	public HashMap<String, String> getRemitExchangeEquivalentAount(
			HashMap<String, String> inputValues) throws AMGException {
		return getPersonalRemittanceDao().getRemitExchangeEquivalentAount(inputValues);
	}
	@Override
	@Transactional
	public List<BenificiaryListView> getBeneficiaryCountryList(BigDecimal cutomerNo,BigDecimal beneCountryId) {

		return getPersonalRemittanceDao().getBeneficiaryCountryList(cutomerNo,beneCountryId);
	}
	@Override
	@Transactional
	public List<PopulateData> getBeneficaryList(BigDecimal customerNo) {
		return getPersonalRemittanceDao().getBeneficaryList(customerNo);
	}

	@Override
	@Transactional
	public String getCountryCode(BigDecimal countryId) {

		return getPersonalRemittanceDao().getCountryCode(countryId);
	}

	@Override
	@Transactional
	public BigDecimal getLastEmosRemittanceCountry(BigDecimal customerNo) throws AMGException  {

		return getPersonalRemittanceDao().getLastEmosRemittanceCountry(customerNo);
	}

	@Override
	@Transactional
	public String getBeneficiaryStatusForEdit(
			HashMap<String, String> inputValues) throws AMGException {

		return getPersonalRemittanceDao().getBeneficiaryStatusForEdit(inputValues);
	}

	/*@Override
	@Transactional
	public void saveBeneficiaryEdit(HashMap<String, String> inputValues,BeneficaryMasterLog beneficaryMasterLog)
			throws Exception {
		getPersonalRemittanceDao().saveBeneficiaryEdit(inputValues,beneficaryMasterLog);

	}*/

	@Override
	@Transactional
	public List<BeneficaryContact> getTelePhoneSeqId(BigDecimal beneficaryMasterSeqId) {
		return getPersonalRemittanceDao().getTelePhoneSeqId( beneficaryMasterSeqId);
	}

	/*@Override
	@Transactional
	public void saveBeneficiaryContactEdit(HashMap<String, String> inputValues,BeneficaryMasterLog beneficaryMasterLog) throws Exception {
		getPersonalRemittanceDao().saveBeneficiaryContactEdit(inputValues,beneficaryMasterLog);

	}*/

	@Override
	@Transactional
	public List<BeneficaryContact> getTelePhoneSeqIdBasedOnNum(BigDecimal telephoneNumberSelect, BigDecimal beneficaryMasterSeqId) {
		return getPersonalRemittanceDao().getTelePhoneSeqIdBasedOnNum(telephoneNumberSelect, beneficaryMasterSeqId);
	}

	@Override
	@Transactional
	public List<BeneficaryContact> getMobileSeqId(BigDecimal beneficaryMasterSeqId) {
		return getPersonalRemittanceDao().getMobileSeqId( beneficaryMasterSeqId);
	}

	@Override
	@Transactional
	public HashMap<String, String> getSpecialApprovalCheck(HashMap<String, String> inputValues) throws AMGException {
		return getPersonalRemittanceDao().getSpecialApprovalCheck(inputValues);
	}

	@Override
	@Transactional
	public List<RemittanceBranchWiseEnquiryView> showRemittanceBranchWise(BigDecimal branchId,String userName,Date documentDate){
		return getPersonalRemittanceDao().showRemittanceBranchWise(branchId,userName,documentDate);
	}

	@Override
	@Transactional
	public List<BeneficaryMaster> isBeneficaryMasterExistInDb(String firstName,String secondName,BigDecimal beneCountryId) {
		return getPersonalRemittanceDao().isBeneficaryMasterExistInDb(firstName, secondName,beneCountryId);
	}

	@Override
	@Transactional
	public HashMap<String, String> pValidateCustomerTelephoneDetails(HashMap<String, String> fetchContactDetails) throws AMGException {
		return getPersonalRemittanceDao().pValidateCustomerTelephoneDetails(fetchContactDetails);
	}

	@Override
	@Transactional
	public void deleteBeneAccountRecord(BigDecimal beneAccountSeqId,BigDecimal beneRelationSeqId,String status) {
		getPersonalRemittanceDao().deleteBeneAccountRecord(beneAccountSeqId,beneRelationSeqId,status );
	}

	@Override
	@Transactional
	public List<PopulateData> getRemittanceListByCountryBankForCashProduct(
			BigDecimal appLicationCountryId, BigDecimal beneBankId,
			BigDecimal beneBankBranchId, BigDecimal countryId,
			BigDecimal beneCurrencyId, BigDecimal serviceMasterId,
			BigDecimal routingCountryId, BigDecimal routingbankId,
			BigDecimal routingbankBankBranchId, BigDecimal languageId) {

		return getPersonalRemittanceDao().getRemittanceListByCountryBankForCashProduct(appLicationCountryId, beneBankId, beneBankBranchId, countryId, beneCurrencyId, serviceMasterId, routingCountryId, routingbankId, routingbankBankBranchId, languageId);
	}

	@Override
	@Transactional
	public List<PopulateData> getDeliveryListByCountryBankRemitForCashProduct(
			BigDecimal appLicationCountryId, BigDecimal beneBankId,
			BigDecimal beneBankBranchId, BigDecimal countryId,
			BigDecimal beneCurrencyId, BigDecimal serviceMasterId,
			BigDecimal routingCountryId, BigDecimal routingbankId,
			BigDecimal routingbankBankBranchId, BigDecimal remittanceId,
			BigDecimal languageId) {
		return getPersonalRemittanceDao().getDeliveryListByCountryBankRemitForCashProduct(appLicationCountryId, beneBankId, beneBankBranchId, countryId, beneCurrencyId, serviceMasterId, routingCountryId, routingbankId, routingbankBankBranchId, remittanceId, languageId);
	}

	@Override
	@Transactional
	public void deleteShoppingCartForFCSale(ShoppingCartDataTableBean shoppingCartData) {
		getPersonalRemittanceDao().deleteShoppingCartForFCSale(shoppingCartData);
	}

	@Override
	@Transactional
	public List<ViewSubAgent> fetchSubAgentsForICash(HashMap<String, String> fetchSubAgentForICASH){
		return getPersonalRemittanceDao().fetchSubAgentsForICash(fetchSubAgentForICASH);
	}

	@Override
	@Transactional
	public Boolean checkEFTAndTTForICASHProduct(HashMap<String, String> checkEFTAndTTForICASH) {
		return getPersonalRemittanceDao().checkEFTAndTTForICASHProduct(checkEFTAndTTForICASH);
	}

	@Override
	@Transactional
	public List<ViewHODirectEFT> fetchAgentforEFTICASHProduct(HashMap<String, String> fetchAgentEFTAndTTForICASH) {
		return getPersonalRemittanceDao().fetchAgentforEFTICASHProduct(fetchAgentEFTAndTTForICASH);
	}

	@Override
	@Transactional
	public List<ViewHODirectInDirect> fetchAgentforTTICASHProduct(HashMap<String, String> fetchAgentEFTAndTTForICASH) {
		return getPersonalRemittanceDao().fetchAgentforTTICASHProduct(fetchAgentEFTAndTTForICASH);
	}

	@Override
	@Transactional
	public List<ViewStatesForICASH> fetchStateForICash(HashMap<String, String> fetchStateForICASH) {
		return getPersonalRemittanceDao().fetchStateForICash(fetchStateForICASH);
	}

	@Override
	@Transactional(rollbackFor = AMGException.class)
	public String checkICASHProduct(BigDecimal applicationCountryId, BigDecimal companyId, BigDecimal collectionDocumentCode, BigDecimal collectionDocumentYear, BigDecimal collectionDocumentNo, BigDecimal bankId) throws AMGException{
		return getPersonalRemittanceDao().checkICASHProduct(applicationCountryId, companyId, collectionDocumentCode, collectionDocumentYear, collectionDocumentNo, bankId);
	}

	@Override
	@Transactional
	public List<RemittanceTransaction> fetchRemittanceTransactionDetails(BigDecimal applicationCountryId,BigDecimal companyId,BigDecimal financeYear,BigDecimal documentNumber) {
		return getPersonalRemittanceDao().fetchRemittanceTransactionDetails(applicationCountryId, companyId, financeYear, documentNumber);
	}

	@Override
	@Transactional
	public List<ProductGroup> fetchProductGroup(BigDecimal bankId,BigDecimal currencyId) {
		return getPersonalRemittanceDao().fetchProductGroup(bankId, currencyId);
	}

	@Override
	@Transactional(rollbackFor = AMGException.class)
	public void saveProductGroup(List<ProductGroup> lstProdGroup) throws AMGException{
		getPersonalRemittanceDao().saveProductGroup(lstProdGroup);
	}

	@Override
	@Transactional
	public void updateProductGroupIsActive(BigDecimal productGroupId, String status) {
		getPersonalRemittanceDao().updateProductGroupIsActive(productGroupId, status);
	}
	@Override
	@Transactional
	public void updateDeclarationIndicator(BigDecimal tempcolloctionPk) {
		getPersonalRemittanceDao().updateDeclarationIndicator(tempcolloctionPk);

	}

	@Override
	@Transactional
	public void updateDeclarationIndicatorTotal(BigDecimal tempcolloctionPk) {
		// TODO Auto-generated method stub
		getPersonalRemittanceDao().updateDeclarationIndicatorTotal(tempcolloctionPk);
	}

	@Override
	@Transactional
	public List<Collect>  checkDeclarationIndicatorsInCollectionTable(
			BigDecimal collctionDocNo,BigDecimal collectionYear) {

		return getPersonalRemittanceDao().checkDeclarationIndicatorsInCollectionTable(collctionDocNo,collectionYear);
	}

	@Override
	@Transactional
	public BigDecimal getLoyaltyPointFromFunction(BigDecimal applicationcountryId, BigDecimal cutomerRefernce) {
		// TODO Auto-generated method stub
		return getPersonalRemittanceDao().getLoyaltyPointFromFunction(applicationcountryId, cutomerRefernce);
	}

	@Override
	@Transactional
	public List<AdditionalDataDisplayView> tofetchRenderAddtionalDetails(BigDecimal appcountryId, String flexField) {
		return getPersonalRemittanceDao().tofetchRenderAddtionalDetails(appcountryId, flexField);
	}

	@Override
	@Transactional
	public void updateBeneMasterDetails(BigDecimal masterId,String beneHouseNo, String beneFlatNo, String beneStreetNo) {
		getPersonalRemittanceDao().updateBeneMasterDetails(masterId, beneHouseNo, beneFlatNo, beneStreetNo);
	}

	@Override
	@Transactional
	public HashMap<String, String> toValidateBeneAddrMandCheck(HashMap<String, String> inputValuesBeneAdd) throws AMGException {
		return getPersonalRemittanceDao().toValidateBeneAddrMandCheck(inputValuesBeneAdd);
	}

	@Override
	@Transactional
	public List<BeneficaryMaster> toFetchbeneHouseDetails(BigDecimal beneficaryMasterSeqId) {
		return getPersonalRemittanceDao().toFetchbeneHouseDetails(beneficaryMasterSeqId);
	}

	@Override
	@Transactional
	public List<SwiftMaster> getSwiftCodeSearch(BigDecimal countryId,
			String swiftCode, String swiftBankName) {
		return getPersonalRemittanceDao().getSwiftCodeSearch(countryId, swiftCode, swiftBankName);
	}

	@Override
	@Transactional
	public String getSwiftBankNameFromSwiftBic(String swiftBic) {
		return getPersonalRemittanceDao().getSwiftBankNameFromSwiftBic(swiftBic);
	}

	@Override
	@Transactional
	public void deleteBeneAccountRecordPesonal(BigDecimal beneAccountSeqId,
			BigDecimal beneRelationSeqId, String status, String remarks) {
		getPersonalRemittanceDao().deleteBeneAccountRecordPesonal(beneAccountSeqId,beneRelationSeqId,status ,remarks);

	}

	/*@Override
	@Transactional
	public void saveBeneficiaryEditManager(HashMap<String, String> inputValues,
			BeneficaryMasterLog beneficaryMasterLog) throws Exception{
		getPersonalRemittanceDao().saveBeneficiaryEditManager(inputValues, beneficaryMasterLog);

	}*/

	@Override
	@Transactional
	public String toFetchRoleName(BigDecimal roleId) {
		return getPersonalRemittanceDao().toFetchRoleName(roleId);
	}

	@Override
	@Transactional
	public void saveBeneficiaryContactEdit(HashMap<String, String> inputValues) throws Exception {
		getPersonalRemittanceDao().saveBeneficiaryContactEdit(inputValues);
	}

	@Override
	@Transactional
	public void saveBeneficiaryEditManager(HashMap<String, String> inputValues) throws Exception {
		getPersonalRemittanceDao().saveBeneficiaryEditManager(inputValues);

	}

	@Override
	@Transactional
	public void saveBeneficiaryEdit(HashMap<String, String> inputValues) throws Exception {
		getPersonalRemittanceDao().saveBeneficiaryEdit(inputValues);

	}

	@Override
	@Transactional
	public List<CustomerIdProof> getCustomerDetailsFromCustomerId(String civilId, BigDecimal cardType, BigDecimal customerId) {
		return getPersonalRemittanceDao().getCustomerDetailsFromCustomerId(civilId, cardType,customerId);
	}

	@Override
	@Transactional
	public List<AllBeneficiaryView> getAllBeneficiaryt(BigDecimal cutomerNo,BigDecimal beneCountryId) {
		return getPersonalRemittanceDao().getAllBeneficiaryt(cutomerNo, beneCountryId);
	}

	@Override
	@Transactional
	public Boolean checkCorporateBranchForSignature(BigDecimal countryBranchId) {
		return getPersonalRemittanceDao().checkCorporateBranchForSignature(countryBranchId);
	}

	@Override
	@Transactional
	public HashMap<String,Object> fetchTempCollection(BigDecimal documentCode, BigDecimal cutomerId, BigDecimal trnxAmt) {
		return getPersonalRemittanceDao().fetchTempCollection(documentCode, cutomerId, trnxAmt);
	}

	@Transactional
	@Override
	public void saveRefundTempCurrencyAdjust(List<TempForeignCurrencyAdjust> tempAdjustList) {
		getPersonalRemittanceDao().saveRefundTempCurrencyAdjust(tempAdjustList);
	}

	@Transactional
	@Override
	public List<CustomerIdProof> getCustomerDetailsActiveRec(String customerId,BigDecimal cardType) {
		return getPersonalRemittanceDao().getCustomerDetailsActiveRec(customerId, cardType);
	}

	@Transactional
	@Override
	public String validateDebitCardNo(HashMap<String, Object> inputValues) throws AMGException{
		return getPersonalRemittanceDao().validateDebitCardNo(inputValues);
	}

	@Transactional
	@Override
	public List<RemittanceApplicationView> fetchRemitTrnxViewByPin(String pinNo) {
		return getPersonalRemittanceDao().fetchRemitTrnxViewByPin(pinNo);
	}

	@Override
	@Transactional
	public List<BenificiaryListView> getBeneficiaryDtFromView(BigDecimal cutomerNo,BigDecimal beneCountryId,BigDecimal beneMasterSeqId,BigDecimal currencyId,String bankCode,BigDecimal beneAccountSeqId) {
		return getPersonalRemittanceDao().getBeneficiaryDtFromView(cutomerNo,beneCountryId,beneMasterSeqId,currencyId,bankCode,beneAccountSeqId);
	}

	//For Company information ADDED BY VISWA --START
	@Transactional
	@Override
	public List<CompanyMaster> getCompanyMaster(BigDecimal companyId){
		return getPersonalRemittanceDao().getCompanyMaster(companyId);
	}
	//For Company information ADDED BY VISWA --END

	@Override
	@Transactional
	public HashMap<String, Object> fetchRemitAmlTrnxDetails(BigDecimal remittanceTrnxId,BigDecimal authType) {
		return getPersonalRemittanceDao().fetchRemitAmlTrnxDetails(remittanceTrnxId,authType);
	}

	@Override
	@Transactional
	public List<IntraTrnxModel> fetchRemitTrnxByPin(String pinNo) {
		return getPersonalRemittanceDao().fetchRemitTrnxByPin(pinNo);
	}

	@Override
	@Transactional
	public void updateRemittanceApplication(BigDecimal customerId, String status) {
		getPersonalRemittanceDao().updateRemittanceApplication(customerId, status);
	}

	@Override
	@Transactional
	public List<PopulateData> getServiceListForPO(
			BigDecimal appLicationCountryId, BigDecimal beneBankId,
			BigDecimal beneBankBranchId, BigDecimal countryId,
			BigDecimal beneCurrencyId, String serviceGroupCode,
			BigDecimal routingCountryId, BigDecimal routingBankId) {
		// TODO Auto-generated method stub
		return getPersonalRemittanceDao().getServiceListForPO(appLicationCountryId, beneBankId, beneBankBranchId, countryId, beneCurrencyId, serviceGroupCode, routingCountryId, routingBankId);
	}

	@Override
	@Transactional
	public HashMap<String, Object> fetchLocalAuthDetails(BigDecimal documentId, BigDecimal documentNo, BigDecimal documentfyr, String authType) {

		return getPersonalRemittanceDao().fetchLocalAuthDetails(documentId, documentNo, documentfyr, authType);
	}

	@Override
	@Transactional
	public BigDecimal getCurrencyId(BigDecimal countryId) {
		return getPersonalRemittanceDao().getCurrencyId(countryId);
	}

	@Override
	@Transactional
	public void saveCustomerMobileLog(CustomerMobileLogModel custMobLog) {
		getPersonalRemittanceDao().saveCustomerMobileLog(custMobLog);
	}

	@Override
	@Transactional
	public List<CustomerIdProof> getCustomerTypeByCustomer(BigDecimal customerId) {
		return getPersonalRemittanceDao().getCustomerTypeByCustomer(customerId);
	}

	@Override
	@Transactional
	public boolean chkRoutingSetup(HashMap<String, Object> routingSetUp) throws AMGException {
		return getPersonalRemittanceDao().chkRoutingSetup(routingSetUp);
	}

	@Override
	@Transactional
	public List<ViewAmtbCoupon> getAmtbCouponFromView(String IdNumber) {
		return getPersonalRemittanceDao().getAmtbCouponFromView(IdNumber);
	}

	@Override
	@Transactional
	public ViewAmtbCoupon getAmtbCouponAmountFromView(BigDecimal couponNo, String IdNumber) {
		return getPersonalRemittanceDao().getAmtbCouponAmountFromView(couponNo, IdNumber);
	}
	
	@Override
	@Transactional
	public BigDecimal getRemittanceApplicationstatus(BigDecimal customerId,BigDecimal documentNo,BigDecimal documentId,String status,BigDecimal documentFinanceYearId,BigDecimal companyId) {
		return getPersonalRemittanceDao().getRemittanceApplicationstatus(customerId, documentNo, documentId, status, documentFinanceYearId,companyId);
	}
	
	@Override
	@Transactional
	public BigDecimal getRemittanceApplicationPk(BigDecimal customerId,BigDecimal documentNo, BigDecimal documentId,BigDecimal documentFinanceYearId, BigDecimal companyId) {
		return getPersonalRemittanceDao().getRemittanceApplicationPk(customerId, documentNo, documentId, documentFinanceYearId, companyId);
	}

	@Override
	@Transactional
	public HashMap<String, Object> getRoutingSetupForCashProduct(HashMap<String, Object> inputValues) {
		return getPersonalRemittanceDao().getRoutingSetupForCashProduct(inputValues);
	}

	@Override
	@Transactional
	public String getExCheckRefundCashLimitProcedure(BigDecimal applicationCountyId, BigDecimal customerId,BigDecimal paymentModeId, BigDecimal colleAmount) throws AMGException {
		return getPersonalRemittanceDao().getExCheckRefundCashLimitProcedure(applicationCountyId, customerId, paymentModeId, colleAmount);
	}

}
