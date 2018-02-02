package com.amg.exchange.remittance.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public interface IPersonalRemittanceDao {
	public CustomerIdProof getCustomerList(String identityType);

	public List<BeneficaryStatus> getBeneficaryStatusList();

	public List<CustomerIdProof> getCustomerDetails(String customerId, BigDecimal cardType);

	public List<Relations> getRelationsList();

	public List<CurrencyMaster> getCurrencyList(BigDecimal countryId);

	public List<BankMaster> getbanklist(BigDecimal currencyId);

	public List<BankBranch> getBankbranchlist(BigDecimal bankId);

	public List<CountryMasterDesc> getCountryList(BigDecimal languageId);

	// For save
	public void saveBeneficaryMaster(BeneficaryMaster beneficaryMaster);

	public void saveBeneficaryRelationship(BeneficaryRelationship beneficaryRelationship);

	public void saveBeneficaryTelephone(BeneficaryContact beneficaryTelaphone);

	public void savebeneficaryAccount(BeneficaryAccount beneficaryAccount);

	public List<BeneficaryAccount> getCustomerBeneficaryDetails(BigDecimal masterseqId);

	public List<BeneficaryRelationship> isCoustomerExistInDB(BigDecimal customerId);

	public List<CustomerIdProof> getCustomerDetails(String customerId);

	public List<BeneficaryContact> getTelephoneDetails(BigDecimal masterId);

	public List<BeneficaryMaster> getAllMasterValues(BigDecimal masterId);

	public List<PaymentModeDesc> getPaymentModeDetails(BigDecimal languageId);

	public List<BankBranch> getALLListBankBranch(BigDecimal countryid, BigDecimal Bankid);

	public List<RoutingDetails> getSpecificListBankBranch(BigDecimal countryid, BigDecimal Bankid);

	public List<BigDecimal> getExchangeRateAllValues(BigDecimal appcountryId, BigDecimal routingcountry, BigDecimal branchId, BigDecimal companyId, BigDecimal routingbankId, BigDecimal serviceId, BigDecimal deliveryId, BigDecimal remitId, BigDecimal foreigncurrencyId, BigDecimal currencyId,
			BigDecimal customerId, String customertypeId, String loyaltypt, String spldeal, String oversea, BigDecimal fcamount, String spotrate, String cashround) throws AMGException;

	public void saveSpecialRateRequest(SpecialRateRequest specialRateRequest);

	public Map<String, ServiceApplicabilityRule> getComponentBehavior(List<String> componentList, BigDecimal applicationCountryId, BigDecimal currencyId, BigDecimal remittanceModeId, BigDecimal deliveryModeId);

	public List<ServiceApplicabilityRule> getDynamicLevel(BigDecimal applicationCountryId, BigDecimal countryId, BigDecimal currencyId, BigDecimal remittanceModeId, BigDecimal deliveryModeId);

	public List<CustomerSpecialDealRequest> getSpecialRequestFcAmountCalList(BigDecimal customerId, BigDecimal applicationCountryId, BigDecimal bankId, BigDecimal bankCountryId);

	public void calculateRemittanceAmount(BigDecimal specialCustomerPk, BigDecimal utilizedAmount);

	// By Subramanian For Telephone Validation
	public List<BeneficaryAccount> getBeneficaryAccountDetails(BigDecimal beneficiaryMasterSeqId);

	public List<BeneficaryContact> isCoustomerTelephoneExistInDB(BigDecimal telephoneNumber);

	public List<BeneficaryMaster> getBeneficaryMasterDetails(BigDecimal beneficiaryMasterSeqId);

	public List<BeneficaryRelationship> isBenificaryRelationExistInDB(BigDecimal beneficaryMasterId);

	public List<BeneficaryContact> isTelephoneExistInDB(BigDecimal telephoneNumber);

	// desc data Ramakrishna
	public List<RemittanceModeDescription> listRemittanceDesc(BigDecimal langId);

	public List<DeliveryModeDesc> lstDeliveryMode(BigDecimal langId);

	// Final Save to Remittance Application
	public void saveRemittanceApplication(RemittanceApplication remittanceApplication);

	public List<RemittanceApplication> getRemittanceApplicationAllDetails(BigDecimal customerid);

	public List<BeneficaryAccount> isBankAccountNumberExistInDb(String bankAccountNumber, BigDecimal countryId, BigDecimal BankId, BigDecimal bankBranchId);

	public List<BeneficaryAccount> isBeneficaryAccountExistInDb(BigDecimal beneficaryMasterSeqId);

	// public List<BeneficaryRelationship>
	// isBeneficaryAccountCustomerExistInDb(BigDecimal
	// beneficaryMasterSeqId,BigDecimal customerId);
	// get utilized amount from remittance Application table -- rahamath
	public BigDecimal getUtilizedAmount(BigDecimal customerId, BigDecimal applicationCountryId, BigDecimal bankId, BigDecimal bankCountryId);

	// Added by Nazish
	public void saveRemittanceAppBeneficiary(RemittanceAppBenificiary remittanceAppBen);

	public void saveAdditionalInsData(AdditionalInstructionData additionalInsData);

	public void saveRemitAppAml(RemitApplAml remitAppAml);

	public List<AdditionalBankRuleMap> getDynamicLevelMatch(BigDecimal countryId, String flexiField);

	// Added by subramanian
	public List<ShoppingCartDetails> getShoppingCartDetails(BigDecimal customerNo);

	// save data to Collect Table
	public void saveCollectTableData(Collect collect);

	// save data to CollectDetails Table
	public void saveCollectDetailTableData(CollectDetail collectDetail);

	public List<SpecialRateRequest> getDocumentSeriality();

	// update ex_receipt_payment_app table data
	public void updateReceiptPaymentTableData(BigDecimal receiptPaymentPk,String status);

	public BigDecimal getReceiptPaymentTablePk(BigDecimal customerId, BigDecimal documentNo);

	// update ex_receipt_payment_app table data
	public void updateRemittanceApplicationTableData(BigDecimal remitanceAppPk,String status);

	//public BigDecimal getRemittanceApplicationPk(BigDecimal customerId, BigDecimal documentNo);

	// update ex_foreigycurrency_adjust_app table data
	public void updateForeignCurrencyAdjustAppTableData(BigDecimal foreignCurrencyAdjustAppPk,String status);

	public List<ForeignCurrencyAdjustApp> getForeignCurrencyAdjustAppPk(BigDecimal customerId, BigDecimal documentNo);

	// save data to ForeignCurrencyAdjust
	public void saveForeignCurrencyAdjust(ForeignCurrencyAdjust foreignCurrencyAdjust);

	// start by subramanian 31/01/2015
	public List<Object> isAmlTranxAmountCheck(BigDecimal appcountryId, BigDecimal beneCountryId, BigDecimal customerId, BigDecimal benificiarySeqId, BigDecimal fcamount) throws AMGException;

	public HashMap<String, String> isAmlTranxAmountCheckForRemittance(BigDecimal appcountryId, BigDecimal beneCountryId, BigDecimal customerId, BigDecimal benificiarySeqId, BigDecimal fcamount) throws AMGException;

	public List<AmlLimit> getAmlLimitCheckList();

	// end by subramanian 31/01/2015
	public List<CurrencyMaster> getCurrencyMasterList(BigDecimal countryId, BigDecimal currencyId);

	public List<AdditionalBankRuleAddData> getAdditionalBankData(BigDecimal bankId, BigDecimal additionalBankFieldId);

	// Telephone number existing checking with customer id
	public Map<String, Object> checkTelephoneExistWithCustId(BigDecimal telephoneNo, BigDecimal customerID, String teleCountryID);

	// Relation existing checking with customer id
	public List<BeneficaryRelationship> checkRelationExistWithCustId(BigDecimal relationId, BigDecimal customerID);

	// populate Telephone countryCode based on nationality
	public BigDecimal populateTelephoneCountry(BigDecimal countryId);

	public List<BankBranch> getBankBranchList(BigDecimal countryId, BigDecimal BankId, BigDecimal formBranchCode, BigDecimal toBranchCode);

	public List<BeneCountryService> getBeneCountryServiceList(BigDecimal countryId);

	public List<BeneServiceExceptionSetup> getBeneServiceExceptionSetupList(BigDecimal countryId, BigDecimal BankId, BigDecimal formBranchCode, BigDecimal toBranchCode);

	public List<BeneCountryService> getBeneCountryAllServiceList(BigDecimal countryId, BigDecimal languageId, BigDecimal appCountryId);

	public List<RemittanceModeDescription> getRemittancDesc(BigDecimal remiId);

	public List<DeliveryModeDesc> getDeliveryModeDesc(BigDecimal deliveryId);

	public List<Object> getCountryServiceList(BigDecimal appCountryId, BigDecimal languageId, BigDecimal countryId);

	public void saveBeneServiceExceptionSetup(BeneServiceExceptionSetup beneServiceException);

	public List<Object> getBeneExceptionSetupList(BigDecimal countryId, BigDecimal languageId, BigDecimal appCountryId);

	public boolean getBeneServiceExceptionSetup(BigDecimal countryId, BigDecimal branchId, BigDecimal deliveryId, BigDecimal remittanceId);

	public List<BeneServiceExceptionSetup> getBeneExceptionSetupAllList();

	public List<RemittanceModeDescription> listRemittanceDesc(BigDecimal remittanceId, BigDecimal langId);

	public List<DeliveryModeDesc> lstDeliveryMode(BigDecimal deliveryId, BigDecimal langId);

	public List<BeneServiceExceptionSetup> getBeneExceptionSetupList(BigDecimal exceptionSetupId);

	public List<BeneServiceExceptionSetup> getExceptionSetupListForActiveInactive();

	public List<BeneServiceExceptionSetup> getBeneDeleteServiceExceptionSetupList(BigDecimal countryId, BigDecimal BankId, BigDecimal formBranchCode, BigDecimal toBranchCode);

	public List<RemittanceApplication> getRemittanceTransactionList(BigDecimal transferNo);

	public String getRemittanceServiceRuleName(BigDecimal countryId, BigDecimal currencyId, BigDecimal bankId, BigDecimal remittanceId, BigDecimal deliveryId);

	public List<BankAccountLength> getBankAccountLengthByBank(BigDecimal bankId);

	public boolean getBanKBranchFromBeneExceptionSetup(BigDecimal countryId, BigDecimal bankId, BigDecimal branchId);

	public List<BeneServiceExceptionSetup> getExceptionSetupListForEnquiry(BigDecimal countryId, BigDecimal bankId, BigDecimal currencyId);

	// added @koti 25/03/2015 to get the currency form view
	public List<ViewBeneServiceCurrency> getViewBeneCurrency(BigDecimal benifisCountryId);

	public String getBankBranchName(BigDecimal bankBranchId);

	public String getBranchName(BigDecimal countryId, BigDecimal bankId, BigDecimal bankBranchId);

	public List<ServiceMasterDesc> fetchCashServiceId(String serviceDesc, BigDecimal langId);

	public List<PopulateData> lstOfRemittance(BigDecimal routingCountryId, BigDecimal beneficaryCurrencyId, BigDecimal serviceId, BigDecimal routingBankId);

	public List<PopulateData> lstOfDelivery(BigDecimal routingCountryId, BigDecimal beneficaryCurrencyId, BigDecimal serviceId, BigDecimal routingBankId);

	public void updateRemitTranxTable(Collect collect, List<ShoppingCartDataTableBean> lstToUpdate);

	public void saveUpdateCustSplDealUtilizedAmount(BigDecimal custSplDealId, BigDecimal utilizedAmount);

	public void deleteShoppingCartByApplId(BigDecimal remittanceApplicationId);

	// Check User exist in FS Customer or FS Employee
	public HashMap<String, Object> findUserCustomerOrEmployee(String userName, String customertype);

	public List<RelationsDescription> getRelationsDescriptionList(BigDecimal languageId);

	public void updatePayIdPayTokenInAppTrnx(List<ShoppingCartDataTableBean> lstSelected, String payToken, String payId);

	public List<ShoppingCartDetails> getRecordsRemittanceApplication(BigDecimal documentNo);

	public List<RemittanceApplicationView> getRecordsRemittanceReceipt(BigDecimal documentNo);
	public List<RemittanceApplicationView> getRecordsForRemittanceReceiptReport(BigDecimal documentNo,BigDecimal financeYear,String documentCode);
	
	public List<PurposeOfRemittanceView> getPurposeOfRemittance(BigDecimal documentNo, BigDecimal documentYear);
	public List<RemittanceApplicationPurpose> getPurposeOfRemittanceForApplication(BigDecimal applicationCountryId,BigDecimal documentFinancialYear,BigDecimal documentId,BigDecimal documentNo);
	public List<ServiceGroupMasterDesc> fetchCashServiceGorupId(String cASHNAME, BigDecimal languageId);

	public List<ServiceGroupMasterDesc> getAllServiceGroupDesc(BigDecimal languageId);

	// checking bank indicator and bank
	public boolean bankIndcheckingbank(String bankInd, BigDecimal bankId);

	// to fetch service id by beneficiary country service
	public List<BeneCountryService> getServiceIdBeneCountrySer(BigDecimal beneficaryCountryId, BigDecimal beneficaryCurrencyId, BigDecimal serviceId, BigDecimal bankId);

	public List<CutomerDetailsView> getCustomerDetails(BigDecimal customerId);

	public List<BeneficaryRelationship> getCustomerBeneficaryDetailswithRelations(BigDecimal beneficaryMasterSeqId);

	public List<BeneficaryRelationship> isBenificaryRelationExist(BigDecimal beneficaryMasterSeqId, BigDecimal beneficaryAccountSeqId);

	public Map<String, Object> checkTelephoneExist(BigDecimal telephoneNumber, String countryCode, String string);

	public List<BeneficaryAccount> isBankAccountNumberExist(BigDecimal bankAccountNumber, BigDecimal benifisBankId, BigDecimal benifisCurrencyId, BigDecimal benifisCountryId, BigDecimal servicebankBranchId);

	public void updateBeneficaryData(BeneficaryMaster beneficaryMaster, BeneficaryAccount account, BeneficaryRelationship beneficaryRelationship, BeneficaryContact contact) throws Exception;

	public List<BeneficaryAccount> getCustomerBeneficaryDetailswithAll(BigDecimal benificaryMasterId, String type);

	public BigDecimal getserviceGroupforCash(BigDecimal languageId);

	public Map<String, Object> checkTelephoneExistWithCustIdwithPhone(BigDecimal mobileNumber, BigDecimal customerNo, String countryCode, String type);

	public List<BeneficaryRelationship> getRelationshipRecord(BigDecimal benificaryMasterId, BigDecimal beneficaryAccountSeqId);

	public List<BeneficaryAccount> getCustomerBeneficaryDetailswithAccountNO(BigDecimal benificaryMasterId, String accountNo, String type);

	public List<BeneficaryAccount> isCashAccountExist(BigDecimal benifisBankId, BigDecimal benifisCurrencyId, BigDecimal benifisCountryId, BigDecimal servicebankBranchId);

	public BigDecimal getbankIndicator(String bankIndicatorAgentBank, BigDecimal languageId);

	public BigDecimal roundingTotalNetAmountbyFunction(BigDecimal applcountryId, BigDecimal totalNetAmount, String roundstatus) throws AMGException;

	public void saveApplicationTransactionModifiedRecordByRound(ShoppingCartDataTableBean lstShoppingCart);

	public void updateApplTrnxError(List<RemittanceApplication> lstShoppingCart, String status);

	public List<RemittanceApplication> fetchRemitApplTrnxRecordsByPayId(String paymentId);

	public void updatePayTokenNull(List<RemittanceApplication> lstPayIdDetails, String status);

	public void callProcedureToCancelRecordsinApplTrnx(String paymentId, String status) throws AMGException;

	public List<AdditionalBankDetailsView> fetchAdditionalDetails(BigDecimal additionalBankRuleId);

	public String getCustomerType(BigDecimal componentId);

	public void saveAuthorizedLog(AuthorizedLog authorizedLog);

	public HashMap<String, String> getRoutingBankSetupDetails(HashMap<String, String> inputValue) throws AMGException;

	public List<PopulateData> getRoutingCountryList(BigDecimal appLicationCountryId, BigDecimal beneBankId, BigDecimal beneBankBranchId, BigDecimal countryId, BigDecimal beneCurrencyId, BigDecimal serviceMasterId, BigDecimal languageId);

	public List<PopulateData> getRoutingBankList(BigDecimal appLicationCountryId, BigDecimal beneBankId, BigDecimal beneBankBranchId, BigDecimal countryId, BigDecimal beneCurrencyId, BigDecimal serviceMasterId, BigDecimal routingCountryId, Boolean ttCheck);

	public List<PopulateData> getRoutingBranchList(BigDecimal appLicationCountryId, BigDecimal beneBankId, BigDecimal beneBankBranchId, BigDecimal countryId, BigDecimal beneCurrencyId, BigDecimal serviceMasterId, BigDecimal routingCountryId, BigDecimal routingbankId, BigDecimal remitId, BigDecimal deliveryId);

	public List<PopulateData> getRemittanceListByCountryBank(BigDecimal appLicationCountryId, BigDecimal beneBankId, BigDecimal beneBankBranchId, BigDecimal countryId, BigDecimal beneCurrencyId, BigDecimal serviceMasterId, BigDecimal routingCountryId, BigDecimal routingbankId, BigDecimal languageId);

	public List<PopulateData> getDeliveryListByCountryBankRemit(BigDecimal appLicationCountryId, BigDecimal beneBankId, BigDecimal beneBankBranchId, BigDecimal countryId, BigDecimal beneCurrencyId, BigDecimal serviceMasterId, BigDecimal routingCountryId, BigDecimal routingbankId, BigDecimal remittanceId, BigDecimal languageId);

	public String getAdditionalCheckProcedure(BigDecimal appLicationCountryId, BigDecimal customerId, BigDecimal branchId, BigDecimal beneId, BigDecimal beneCountryId, BigDecimal beneBankId, BigDecimal beneBankBranchId, String beneAccountNo, BigDecimal serviceMasterId,
			BigDecimal routingCountryId, BigDecimal routingBankId, BigDecimal routingBankBranchId, BigDecimal remittanceModeId, BigDecimal deliveryModeId, BigDecimal sourceOfIncomeId, BigDecimal exchangeRateApplied, BigDecimal localCommisionCurrencyId,
			BigDecimal localCommisionAmount, BigDecimal localChargeCurrencyId, BigDecimal localchargeAmount, BigDecimal localDelivCurrencyId, BigDecimal localDeliAmount, BigDecimal serviceProvider, BigDecimal foreignCurrencyId, BigDecimal foreignTrnxAmount,
			BigDecimal localNetCurrecnyId, BigDecimal localNetTrnxAmount, String beneSwiftBank1, String beneSwiftBank2, String errorMessage) throws AMGException;

	public HashMap<String, String> getRemittanceDeliveryFromRecheckApplicationProcedure(BigDecimal appLicationCountryId, BigDecimal customerId, BigDecimal branchId, BigDecimal beneId, BigDecimal beneCountryId, BigDecimal beneBankId, BigDecimal beneBankBranchId, BigDecimal beneAccountNo,
			BigDecimal serviceMasterId, BigDecimal routingCountryId, BigDecimal routingBankId, BigDecimal routingBankBranchId, BigDecimal remittanceModeId, BigDecimal deliveryModeId, BigDecimal exchangeRateApplied, BigDecimal localCommisionCurrencyId,
			BigDecimal localCommisionAmount, BigDecimal localChargeCurrencyId, BigDecimal localchargeAmount, BigDecimal localDelivCurrencyId, BigDecimal localDeliAmount, BigDecimal foreignCurrencyId, BigDecimal foreignTrnxAmount, BigDecimal localNetCurrecnyId,
			BigDecimal localNetTrnxAmount) throws AMGException;

	public HashMap<String, String> toFetchDetilaFromAddtionalBenficiaryDetails(BigDecimal beneficaryMasterId, BigDecimal beneficaryBankId, BigDecimal beneficaryBankBranchId, BigDecimal beneAccNumSeqId, BigDecimal routingCountry, BigDecimal routingBank, BigDecimal routingBranch, BigDecimal serviceTypeId, BigDecimal applicationId,
			BigDecimal currencyId, BigDecimal remitMode, BigDecimal deliveryMode) throws AMGException;

	public String toFtechPurtherInstractionErrorMessaage(BigDecimal applicationCountyId, BigDecimal routingCountryId, BigDecimal routingBankId, BigDecimal currencyId, BigDecimal remittanceId, BigDecimal deliveryId, String furtherInstruction,BigDecimal beneBankCountryId) throws AMGException;

	public HashMap<String, String> toFtechSwiftBankProcedure(BigDecimal applicationCountryId, BigDecimal routingCountryId, BigDecimal currencyId, BigDecimal remittanceId, BigDecimal deliveryId, String fieldName, String beneficiarySwiftBank,BigDecimal beneBankCountryId)throws AMGException;

	public BigDecimal toFetchBankIdBasedOnBankName(String bankName);

	public BigDecimal toFetchBankBranchIdBasedOnBankBranchName(String branchName);

	public List<PopulateData> fetchingViewSwiftMasterByCountryId(BigDecimal beneCountyId);

	public List<CollectionDetailView> getCollectionDetailForRemittanceReceipt(BigDecimal companyId ,BigDecimal documentNumber,BigDecimal financeYear,BigDecimal documentCode);
	public List<CollectionPaymentDetailsView> getCollectionPaymentDetailForRemittanceReceipt(BigDecimal companyId ,BigDecimal documentNumber,BigDecimal financeYear,BigDecimal documentCode);

	public HashMap<String, String> getExchangeRateValues(HashMap<String, String> inputValues) throws AMGException;

	//public BigDecimal getBeneficaryAccountId(BigDecimal beneAccNum);

	//Added by Rabil
	public BigDecimal getBeneficaryAccountId(String beneAccNum,BigDecimal bankId,BigDecimal branchId,BigDecimal currencyId);

	public void saveAllApplTransaction(HashMap<String, Object> mapAllDetailPersonalRemittanceApplSave) throws AMGException;


	public String getExCheckCashLimitProcedure(BigDecimal applicationCountyId,BigDecimal customerId,BigDecimal paymentModeId,BigDecimal colleAmount) throws AMGException;//EX_CHECK_CASH_LIMIT

	public List<AdditionalBankDetailsView> getAmiecDetails(BigDecimal currencyId, BigDecimal bankId, BigDecimal remittanceModeId,BigDecimal deleveryModeId,BigDecimal countryId, String flexiField);

	public HashMap<String ,String> exPBankIndicatorsProcedureCheck(HashMap<String ,String> inputValues,List<AddAdditionalBankData> listAdditionalBankDataTable ) throws AMGException;

	public List<AdditionalDataDisplayView> getAdditionalDataFromServiceApplicability(BigDecimal applicationCountryId, BigDecimal countryId, BigDecimal currencyId, BigDecimal remittanceModeId, BigDecimal deliveryModeId) throws AMGException;;

	public HashMap<String,String>  getSwitInstrustionFromServiceApplicability(HashMap<String,String> inputValues) throws AMGException;

	public List<Employee> getEmployeeDetails(String userName);

	public List<CustomerDeclerationView> getCustomerDeclerationData(BigDecimal applicationCountryId, BigDecimal documentFinanceYear, BigDecimal documentid, BigDecimal documentNo);

	public List<CustomerRemittanceTransactionView> getBeneficaryTxnInquiryList(BigDecimal customerId);

	//Added by Rabil on 02/12/2015

	public HashMap<String,String> getValidateCustomerProcedure(BigDecimal applicationCountryId,BigDecimal customerId,String userName,String usertype);

	public String getValidateBeneficiaryProcedure(BigDecimal applicationCountryId,BigDecimal customerId,String userName,BigDecimal beneMasSeqId,BigDecimal beneAccNumSeqId);

	public String getBannedNameCheckProcedure(BigDecimal applicationCountryId,String englishName,String localName);


	//Added on 08/12/2015

	public List<PopulateData> getServiceList(BigDecimal appLicationCountryId, BigDecimal beneBankId, BigDecimal beneBankBranchId, BigDecimal countryId, BigDecimal beneCurrencyId,String serviceGroupCode);

	public List<CurrencyWiseDenomination> getCurrencyDenominations(BigDecimal currencyId,BigDecimal countryId);

	public List<ViewStock> toCheckStockForView(BigDecimal countryId, String userName, String countryBranchId, BigDecimal companyId, String currecnId);

	public List<DebitAutendicationView> getdebitAutendicationList();

	public List<DebitAutendicationView> getdebitAutendicationListByUserId(String colAuthorizedby, String pwd);

	//Added by Rabil  on 15/12/2015
	public HashMap<String,String> getBannedBankCheckProcedure(BigDecimal appLicationCountryId,BigDecimal beneBankId,BigDecimal beneMasSeqId);

	public String insertEMOSLIVETransfer(BigDecimal applicationCountryId, BigDecimal companyId, BigDecimal documentCode, BigDecimal documentFinanceYear, BigDecimal documentNo) throws AMGException;

	public HashMap<String, String> getloyalityPointsFromProcedure(BigDecimal customerRef,Date documentDate);

	public List<PendingTransferRequestEnquiryView> getAllRecordsFromApplicationDetailView(BigDecimal countryBranchId);

	public List<CustomerIdProof> getCustomerDetailsThroughCusReg(String customerId);

	public PaymentModeDesc getvoucherModeId(String v, BigDecimal languageId);

	public HashMap<String, Object> saveAllRemittanceTransaction(HashMap<String, Object> mapAllDetailPersonalRemittanceSave) throws AMGException;

	public BigDecimal saveTempCollectionwithDetailsandTempCurrencyAdjust(TempCollection tempCollection, List<TempCollectDetail> tempDetailsList, List<TempForeignCurrencyAdjust> tempAdjustList,List<AuthorizedLog> lstAuthenStoredRecords) throws Exception;

	//public BigDecimal getRemittanceApplicationstatus(BigDecimal customerId, BigDecimal documentNo);

	public BigDecimal getReceiptPaymentTableStatus(BigDecimal customerId, BigDecimal documentNo);
	
	
	public List<PendingTransferRequestEnquiryView> getAllRecordsFromApplicationDetailView(BigDecimal countryBranchId,Date startDate , Date endDate,BigDecimal customerReference);
	public String declartionLimitCheck(BigDecimal applicationCountryId, BigDecimal companyId, BigDecimal customerId, Date documentDate, String paymentMode)  throws AMGException ;

	/** Added by Rabil to store Kent_Log */
	public void saveKnetLogDetails(KnetLog knetLog);
	
	public List<BeneficaryAccount> getCashProductDetails(BigDecimal beneficaryAccountSeqId) throws  AMGException;
	
	public List<PopulateData> getRemittanceListByCountryBankForCash(BigDecimal serviceGroupId) throws AMGException;
	
	public List<PopulateData> getDeliverylistByRemitIdForCash(BigDecimal remittanceModeId) throws AMGException;
	
	public HashMap<BigDecimal, BigDecimal> beneficiaryCount(List<BigDecimal> lstBeneId);
	
	public List<AuthicationLimitCheckView> parameterLimitCheckForSameBene();
	
	public List<AuthicationLimitCheckView> parameterDeTails_AUTH_View(String authorization_Type);
	
	public List<CustomerSpecialDealAppl> fetchAllCustSplDealByApplTrnxDoc(BigDecimal applTrnxApplDocNum);
	
	//Update customer email id if change
	public int updtaeCustEmail(BigDecimal customerId,String custemailId)throws  AMGException;
	
	//Get Email info from Application setup
	public List<ApplicationSetup> getEmailFromAppSetup(BigDecimal companyId,BigDecimal applicationCountryId)throws  AMGException;
	
	public HashMap<String, String> getRemitExchangeEquivalentAount(HashMap<String, String> inputValues) throws AMGException;
	
	public List<BenificiaryListView> getBeneficiaryCountryList(BigDecimal cutomerNo,BigDecimal beneCountryId);
	public List<PopulateData> getBeneficaryList(BigDecimal customerNo);
	public String getCountryCode(BigDecimal countryId);
	public BigDecimal getLastEmosRemittanceCountry(BigDecimal customerNo) throws AMGException;
	
	public String getBeneficiaryStatusForEdit(HashMap<String, String> inputValues) throws AMGException;
	
	//public void saveBeneficiaryEdit(HashMap<String, String> inputValues,BeneficaryMasterLog beneficaryMasterLog) throws Exception;
	
	//public void saveBeneficiaryContactEdit(HashMap<String, String> inputValues,BeneficaryMasterLog beneficaryMasterLog) throws Exception;
	
	public List<BeneficaryContact> getTelePhoneSeqId(BigDecimal beneficaryMasterSeqId);
	public List<BeneficaryContact> getTelePhoneSeqIdBasedOnNum(BigDecimal telephoneNumberSelect, BigDecimal beneficaryMasterSeqId);
	public List<BeneficaryContact> getMobileSeqId(BigDecimal beneficaryMasterSeqId);
	
	//Added by Rabil 09/03/2016.
	public HashMap<String, String> getSpecialApprovalCheck(HashMap<String, String> inputValues) throws AMGException; 
	
	public List<RemittanceBranchWiseEnquiryView> showRemittanceBranchWise(BigDecimal branchId,String userName,Date documentDate);
	
	public List<BeneficaryMaster> isBeneficaryMasterExistInDb(String firstName , String secondName,BigDecimal beneCountryId);
	
	public HashMap<String, String> pValidateCustomerTelephoneDetails(HashMap<String, String> fetchContactDetails) throws AMGException ;
	
	public void deleteBeneAccountRecord(BigDecimal beneAccountSeqId,BigDecimal beneRelationSeqId,String status );
	
	public List<PopulateData> getRemittanceListByCountryBankForCashProduct(BigDecimal appLicationCountryId, BigDecimal beneBankId, BigDecimal beneBankBranchId, BigDecimal countryId, BigDecimal beneCurrencyId, BigDecimal serviceMasterId, BigDecimal routingCountryId, BigDecimal routingbankId, BigDecimal routingbankBankBranchId, BigDecimal languageId);
	
	public List<PopulateData> getDeliveryListByCountryBankRemitForCashProduct(BigDecimal appLicationCountryId, BigDecimal beneBankId, BigDecimal beneBankBranchId, BigDecimal countryId, BigDecimal beneCurrencyId, BigDecimal serviceMasterId, BigDecimal routingCountryId, BigDecimal routingbankId, BigDecimal routingbankBankBranchId, BigDecimal remittanceId, BigDecimal languageId);
	
	public void deleteShoppingCartForFCSale(ShoppingCartDataTableBean shoppingCartData);
	
	public List<ViewSubAgent> fetchSubAgentsForICash(HashMap<String, String> fetchSubAgentForICASH);
	
	public Boolean checkEFTAndTTForICASHProduct(HashMap<String, String> checkEFTAndTTForICASH);
	
	public List<ViewHODirectEFT> fetchAgentforEFTICASHProduct(HashMap<String, String> fetchAgentEFTAndTTForICASH);
	
	public List<ViewHODirectInDirect> fetchAgentforTTICASHProduct(HashMap<String, String> fetchAgentEFTAndTTForICASH);
	
	public List<ViewStatesForICASH> fetchStateForICash(HashMap<String, String> fetchStateForICASH);
	
	public String checkICASHProduct(BigDecimal applicationCountryId, BigDecimal companyId, BigDecimal collectionDocumentCode, BigDecimal collectionDocumentYear, BigDecimal collectionDocumentNo, BigDecimal bankId) throws AMGException;
	
	public List<RemittanceTransaction> fetchRemittanceTransactionDetails(BigDecimal applicationCountryId,BigDecimal companyId,BigDecimal financeYear,BigDecimal documentNumber);
	
	public List<ProductGroup> fetchProductGroup(BigDecimal bankId , BigDecimal currencyId);
	
	public void saveProductGroup(List<ProductGroup> lstProdGroup) throws AMGException;
	
	public void updateProductGroupIsActive(BigDecimal productGroupId, String status);
	public void updateDeclarationIndicator(BigDecimal tempcolloctionPk) ;
	public void updateDeclarationIndicatorTotal(BigDecimal tempcolloctionPk);
	public List<Collect>  checkDeclarationIndicatorsInCollectionTable(
			BigDecimal collctionDocNo,BigDecimal collectionYear);
	//Added by Rabil 24/07/2016
	public BigDecimal getLoyaltyPointFromFunction(BigDecimal applicationcountryId,BigDecimal cutomerRefernce);
	
	//Added By Koti 09/08/2016
	public List<AdditionalDataDisplayView> tofetchRenderAddtionalDetails(BigDecimal appcountryId, String flexField);
	
	public void updateBeneMasterDetails(BigDecimal masterId,String beneHouseNo, String beneFlatNo, String beneStreetNo);
	
	public HashMap<String, String> toValidateBeneAddrMandCheck(HashMap<String, String> inputValuesBeneAdd) throws AMGException;
	
	public List<BeneficaryMaster> toFetchbeneHouseDetails(BigDecimal beneficaryMasterSeqId);
	
	public List<SwiftMaster> getSwiftCodeSearch(BigDecimal countryId,
			String swiftCode, String swiftBankName);
	
	public String getSwiftBankNameFromSwiftBic(String swiftBic);
	public void deleteBeneAccountRecordPesonal(BigDecimal beneAccountSeqId,
			BigDecimal beneRelationSeqId, String status, String remarks);
	
	//Added By Koti 24/08/2016
	//public void saveBeneficiaryEditManager(HashMap<String, String> inputValues,BeneficaryMasterLog beneficaryMasterLog) throws Exception;

	public String toFetchRoleName(BigDecimal roleId);

	public void saveBeneficiaryContactEdit(HashMap<String, String> inputValues) throws Exception;

	public void saveBeneficiaryEditManager(HashMap<String, String> inputValues)throws Exception;

	public void saveBeneficiaryEdit(HashMap<String, String> inputValues)throws Exception;
	
	public List<CustomerIdProof> getCustomerDetailsFromCustomerId(String civilId, BigDecimal cardType, BigDecimal customerId);
	public List<AllBeneficiaryView> getAllBeneficiaryt(BigDecimal cutomerNo,BigDecimal beneCountryId);
	
	public Boolean checkCorporateBranchForSignature(BigDecimal countryBranchId);
	
	public HashMap<String,Object> fetchTempCollection(BigDecimal documentCode , BigDecimal cutomerId, BigDecimal trnxAmt);
	
	public void saveRefundTempCurrencyAdjust(List<TempForeignCurrencyAdjust> tempAdjustList);
	
	public List<CustomerIdProof> getCustomerDetailsActiveRec(String customerId, BigDecimal cardType);
	
	public String validateDebitCardNo(HashMap<String, Object> inputValues) throws AMGException;
	
	public List<RemittanceApplicationView> fetchRemitTrnxViewByPin(String pinNo);
	
	public List<BenificiaryListView> getBeneficiaryDtFromView(BigDecimal cutomerNo,BigDecimal beneCountryId,BigDecimal beneMasterSeqId,BigDecimal currencyId,String bankCode,BigDecimal beneAccountSeqId);
	
	//For Company information ADDED BY VISWA --START
	public List<CompanyMaster> getCompanyMaster(BigDecimal companyId);
	//For Company information ADDED BY VISWA --END
	
	public HashMap<String, Object> fetchRemitAmlTrnxDetails(BigDecimal remittanceTrnxId,BigDecimal authType);
	
	public List<IntraTrnxModel> fetchRemitTrnxByPin(String pinNo);
	
	public void updateRemittanceApplication(BigDecimal customerId,String status);
	
	public List<PopulateData> getServiceListForPO(BigDecimal appLicationCountryId, BigDecimal beneBankId, BigDecimal beneBankBranchId, BigDecimal countryId, BigDecimal beneCurrencyId,String serviceGroupCode,BigDecimal routingCountryId ,BigDecimal routingBankId);
	
	public HashMap<String, Object> fetchLocalAuthDetails(BigDecimal documentId,BigDecimal documentNo,BigDecimal documentfyr,String authType);
	
	public BigDecimal getCurrencyId(BigDecimal countryId);
	
	public void saveCustomerMobileLog(CustomerMobileLogModel custMobLog);
	
	public List<CustomerIdProof> getCustomerTypeByCustomer(BigDecimal customerId);
	
	public boolean chkRoutingSetup(HashMap<String, Object> routingSetUp) throws AMGException;
	
	public List<ViewAmtbCoupon> getAmtbCouponFromView(String IdNumber);
	
	public ViewAmtbCoupon getAmtbCouponAmountFromView(BigDecimal couponNo,String IdNumber);
	
	public BigDecimal getRemittanceApplicationstatus(BigDecimal customerId,BigDecimal documentNo,BigDecimal documentId,String status,BigDecimal documentFinanceYearId,BigDecimal companyId);
	
	public BigDecimal getRemittanceApplicationPk(BigDecimal customerId,BigDecimal documentNo,BigDecimal documentId,BigDecimal documentFinanceYearId,BigDecimal companyId);
	
	public HashMap<String,Object> getRoutingSetupForCashProduct(HashMap<String, Object> inputValues);
	
	public String getExCheckRefundCashLimitProcedure(BigDecimal applicationCountyId,BigDecimal customerId,BigDecimal paymentModeId,BigDecimal colleAmount) throws AMGException;//EX_REFUND_CASH_LIMIT  
}
