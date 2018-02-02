package com.amg.exchange.common.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amg.exchange.bean.RoutingCountry;
import com.amg.exchange.common.dto.BankMasterDTO;
import com.amg.exchange.common.model.BizComponentConfDetail;
import com.amg.exchange.common.model.BizComponentData;
import com.amg.exchange.common.model.BizComponentDataDesc;
import com.amg.exchange.common.model.BusinessComponent;
import com.amg.exchange.common.model.CityMasterDesc;
import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.model.DistrictMasterDesc;
import com.amg.exchange.common.model.LanguageType;
import com.amg.exchange.common.model.RuleApplicationMaster;
import com.amg.exchange.common.model.RulePageMaster;
import com.amg.exchange.common.model.StateMasterDesc;
import com.amg.exchange.foreigncurrency.model.Stock;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.model.RateAlertFrequency;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.registration.model.CustomerIdproofView;
import com.amg.exchange.registration.model.CustomerLogin;
import com.amg.exchange.registration.model.CustomerMobileLogModel;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.registration.model.IncomeRangeMaster;
import com.amg.exchange.registration.model.LoginLogoutHistory;
import com.amg.exchange.registration.model.RoleMaster;
import com.amg.exchange.registration.model.ViewActiveCustomerCheck;
import com.amg.exchange.remittance.bean.AmtbCouponDT;
import com.amg.exchange.remittance.model.RoutingHeader;
import com.amg.exchange.remittance.model.SpecialRateRequest;
import com.amg.exchange.remittance.model.ViewAmtbCouponReport;
import com.amg.exchange.remittance.model.ViewBankDetails;
import com.amg.exchange.treasury.bean.BankCountryPopulationBean;
import com.amg.exchange.treasury.model.AccountBalance;
import com.amg.exchange.treasury.model.ArticleDetails;
import com.amg.exchange.treasury.model.ArticleMaster;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.DeliveryModeDesc;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.treasury.model.RemittanceModeDescription;
import com.amg.exchange.treasury.model.ServiceMasterDesc;
import com.amg.exchange.util.AMGException;

public interface IGeneralService<T> extends IMutualService<T>,ICommonService<T> {

	public List<CountryMasterDesc> getCountryList(BigDecimal languageId);
	public List<CountryMasterDesc> getNationalityList(BigDecimal languageId);
	public List<StateMasterDesc> getStateList(BigDecimal languageId, BigDecimal countryId);
	public List<DistrictMasterDesc> getDistrictList(BigDecimal languageId, BigDecimal countryId, BigDecimal stateId);
	public List<CityMasterDesc> getCityList(BigDecimal languageId, BigDecimal countryId, BigDecimal stateId, BigDecimal districtId);

	public List<StateMasterDesc> getStateList(BigDecimal languageId);
	public List<DistrictMasterDesc> getDistrictList(BigDecimal languageId);
	public List<CityMasterDesc> getCityList(BigDecimal languageId);

	public List<BusinessComponent> getAllComponentList();
	public List<RuleApplicationMaster> getAllApplicationList();
	public List<CompanyMasterDesc> getAllCompanyList(BigDecimal languageId);
	public List<RulePageMaster> getAllPageList();
	public BigDecimal getPageId(String pageName);
	public Map<String,BizComponentConfDetail> getComponentBehavior(List<String> componentList, BigDecimal levelId, BigDecimal applicationId, BigDecimal companyId, BigDecimal countryId, BigDecimal pageId);
	public Map<String,BizComponentConfDetail> getComponentBehaviorNew(List<String> componentList, BigDecimal levelId, BigDecimal applicationId, BigDecimal companyId, BigDecimal countryId, BigDecimal pageId);
	public Map<BigDecimal, String> getAllComponentComboData(BigDecimal componentConfId, BigDecimal languageId);
	public Map<BigDecimal, String> getAllCountry(BigDecimal languageId);
	public Map<BigDecimal, String> getAllNationality(BigDecimal languageId);
	public List<LanguageType> getAllLanguages();
	public List<BankMaster> getAllBankList(BigDecimal languageId,BigDecimal countryId);
	public List<BankBranch> getBankBranchList(BigDecimal countryId,BigDecimal bankId);
	public Map<BigDecimal, String> getComponentData(String component, BigDecimal levelId, BigDecimal applicationId, BigDecimal companyId, BigDecimal countryId, BigDecimal pageId, BigDecimal languageId);
	public String getCivilIdStatus(BigDecimal civilID);
	public BizComponentDataDesc getComponentId(String inputString, BigDecimal langId);
	public BizComponentData getComponentDesc(BigDecimal inputId, BigDecimal langId);
	//added by Nazish For list of business 

	public List<CountryMasterDesc> getBusinessCountryList(BigDecimal languageId);
	public List<BankAccountDetails> getBankAccountDetailsList(BigDecimal bankId);
	public String getMessage(BigDecimal countryId, BigDecimal languageId);
	public List<CurrencyMaster> getCurrencyList();
	public List<BankMaster> getBankList(BigDecimal countryId);
	public List <CountryBranch> getBranchDetails(BigDecimal countryId);
	public List <CountryBranch> getBranchDetailsForToLocation(BigDecimal countryId , BigDecimal countryBranchId);
	public List <CountryBranch> getBranchDetailsFromBeneStatus(BigDecimal countryId , BigDecimal countryBranchId);
	public List <CountryBranch> getCountryBranchLocCode(BigDecimal countryBranchId);
	
	public List<RoleMaster> getRole();
	public BigDecimal callSaleProjection(BigDecimal comapanyId,BigDecimal exchangeCountry, BigDecimal bankCountry, BigDecimal currency, BigDecimal bankId, BigDecimal noOfDays);
	public BigDecimal callValueDatedTransaction(BigDecimal CompanyId,BigDecimal exchangeCountry, BigDecimal bankCountry, BigDecimal currency, BigDecimal bankId, BigDecimal noOfDays);

	public String getCountryName(BigDecimal countryId);
	public List<BankCountryPopulationBean> getAllBankContry();
	public List<BankCountryPopulationBean> getBankContry(BigDecimal applicationCountryId);
	public List<CurrencyMaster> getCountryCurrencyList(BigDecimal countryId);
	public List<BankMaster> getBankAccordingToBankCountry(BigDecimal applicationCountry, BigDecimal countryId);
	public List<BankMaster> getAllBankListFromBankMaster();
	public String getCountryName(BigDecimal languageId,BigDecimal countryId);

	public List<ArticleMaster> getArtilces();
	public List<ArticleDetails> getLevelData(BigDecimal articleId);
	public List<IncomeRangeMaster> getIncomeRange(BigDecimal countryId,BigDecimal articleDetailId);
	public List<BankMaster> getAllBankCountryList();
	//Added By Nazish for FX Deal
	public List<CompanyMasterDesc> getCompanyList(BigDecimal companyId,BigDecimal languageId);
	public List<Document> getDocument(BigDecimal id,BigDecimal languageId);
	public List<UserFinancialYear> getDealYear(Date currentDate);
	public String getNextDocumentReferenceNumber(int countryId,int companyId,int documentId,int financialYear,String processIn ,BigDecimal branchId);
	public List<BankAccountDetails> getAccountNumber(BigDecimal bankId, BigDecimal currencyId);
	/*
	 * Name : RamMohanReddy
	 * Purpose :TO get FC Amount
	 * Date: 18-Nov-2014
	 */
	public BigDecimal getForeignCurrencyBalanceFromAccDetailID(String accountDetailId);
	public BigDecimal getAvgRateFromAccountBalanceTable(BigDecimal accountDetailId);

	//Added by Nazish for duplicate check mobile and Email
	public List<Customer> getMobileCheck(BigDecimal countryId,String mobileNo);
	public List<Customer> getEmailCheck(String email);
	public String getStateName(BigDecimal languageId,BigDecimal countryId,BigDecimal stateId);
	public String getDistrictName(BigDecimal languageId,BigDecimal countryId,BigDecimal stateId, BigDecimal districtId);
	public String getCityName(BigDecimal languageId,BigDecimal countryId,BigDecimal stateId, BigDecimal districtId,BigDecimal cityId);

	public String getTitleName(BigDecimal languageId,BigDecimal title);
	public String getNationalityName(BigDecimal languageId,BigDecimal countryId);
	public String getArticleName(BigDecimal articleId);
	public String getLevelName(BigDecimal level);
	public String getIncomeRangeName(BigDecimal income);
	public String getEmplTypeName(BigDecimal languageId,BigDecimal employment);

	public String getSearchIdType(BigDecimal languageId,BigDecimal idType);


	public String getProfessionName(BigDecimal languageId,BigDecimal profession);
	public List<CustomerLogin> getEmailCheckUser(String email);
	//To get CB,IB,LB,Name :RamMohan,Date 10-Nov-2014
	public List<BankApplicability> getCoresBankLocalBankInterBankList(BigDecimal countryId,BigDecimal getBankCountry);
	public List<BankApplicability> getCorrespondingLocalFundingBanks(BigDecimal countryId);
	
	public List<BankApplicability> getCoresBankList(BigDecimal countryId);
	
	public List<BankMaster> getCorrespondingBanks(BigDecimal countryId);

	//To get LB,Name :RamMohan,Date 11-Nov-2014
	public List<BankApplicability> getLocalBankList(BigDecimal countryId);
	public List<AccountBalance> getBankBalance(String accountNo);
	
	//To get the All Type of Banks From Bank Applicability According to CountryId and BanksIndicator
	public List<BankApplicability> getBankListbyIndicator(BigDecimal countryId,BigDecimal indicator);
	public List<BankApplicability> getBankListbyIndicatoronly(BigDecimal indicator,BigDecimal companyId);
	//To get Bank Name according BankId 
	public String getBankName(BigDecimal bankId);
	public String getCurrencyName(BigDecimal currencyId);
	
	//To get Validity of identity
	
	public String getValidity(BigDecimal countryId, BigDecimal businessComponentId);
	
	// To get Remittancel and Delivery  List
	
	public List<RemittanceModeDescription> getRemittanceList(BigDecimal languageId);
	public List<DeliveryModeDesc> getDeliveryModeList(BigDecimal languageId);

	// To get Customer Type from FS_BIZ Component Data Desc by Nazish

	public List<BizComponentDataDesc> getComponentNameIndividual(BigDecimal languageId,BigDecimal componentId);
	public List<BizComponentDataDesc> getComponentNameCorporate(BigDecimal languageId,BigDecimal componentId);

	//To get  all routing Country List
	public List<RoutingCountry> getAllRoutingCountryList(BigDecimal beneficaryCountryId,BigDecimal beneficaryCurrencyId,BigDecimal serviceId,BigDecimal bankId);
	//To get  all routing Bank List
	public List<RoutingCountry> getAllRoutingBankList(BigDecimal routingCountryId,BigDecimal beneficaryCurrencyId,BigDecimal serviceId,BigDecimal bankId);
	//To get  all Specific and All List
	public List<RoutingHeader> getAllSpecificList(BigDecimal routingCountryId,BigDecimal beneficaryCurrencyId,BigDecimal serviceId,BigDecimal routingbankId);
	
	public List<ServiceMasterDesc> getAllServiceDesc(BigDecimal languageId);
	public List<ServiceMasterDesc> getServiceDesc(BigDecimal languageId, BigDecimal serviceId);
	public List<BigDecimal> getAllServiceDescByCountry(BigDecimal countryid);
	
	
	
	//Get Spot rate details based on Customer ID
	public List<SpecialRateRequest> getSpotRateDetails(BigDecimal customerId,BigDecimal bankId,BigDecimal companyId, BigDecimal documentId, BigDecimal financialyear, String documentNo, BigDecimal appcountryId);
	// to get employees of country , branch with role
	public List<Employee> getEmployeelist(BigDecimal countryId,BigDecimal branchId,BigDecimal roleId);
	
	//to get Frequency Details from DB based on Lang - weekly , monthly , quarterly , half yearly , annually
	public List<RateAlertFrequency> frequencyDetailsLst(BigDecimal languageId);
	
    public List<CountryMasterDesc> getCountryListExceptAppCountry(BigDecimal languageId, BigDecimal countryId);
	
	public String amlServiceSearch(String sSearchType, String sGender,
			String sClientStatus, String sAddClient, String sUpdateUserFields,
			String sReturnComplianceRecords, String sAddressLine1,
			String sAddressLine2, String sAddressLine3, String sAddressLine4,
			String sAddressLine5, String sAddressLine6, String sAddressLine7,
			String sCategoryName, String sIsMandatory, String sIsSelected,
			String sListCode, String sListName, String sCategories,
			String sComplianceLists, String sUserField1Label,
			String sUserField1Value, String sUserField2Label,
			String sUserField2Value, String sUserField3Label,
			String sUserField3Value, String sUserField4Label,
			String sUserField4Value, String sNameLine, String sArabicName,
			String sClientID, String sClientSearchType,String amlStatus);
	//get Id expiry date based on civil id;
	public Date getValidExpiryDate(String civilId);
	//get customerId based on civilId
	public List<CustomerIdProof> getCustomerIdBasedOnCivilId(String civilId);
	//get Customer Name Based on customer id
	public String getCustomerNameBasedOnCustomerId(BigDecimal customerId);
	public String getCustomerNameCustomerId(BigDecimal customerId);
	
	//get All Bank Code From Bank Master
	public List<BankMaster> getAllBankCodeFromBankMaster(String bankCode);
	
	//get telephoneCountry Based on nationality
	public String getTelephoneCountryBasedOnNationality(BigDecimal countryId);
	
	//get occupation based on customerId
	public BigDecimal getOccupationId(BigDecimal customerId);
	//get Occupation Description Based On Occupation id
	public String getOccupationDesc(BigDecimal occupationID,BigDecimal languageId);
	
	
	// Start by subramanian 

				public String getCountryCode(BigDecimal countryId);
				public String getCurrencyCode(BigDecimal currencyId);
				public String getBankCode(BigDecimal bankId);
				public BigDecimal getBankBranchCode(BigDecimal bankBranchId);
				public String getRemittanceCode(BigDecimal remittanceModeId);
				public String getDeliveryCode(BigDecimal deliveryId);

				// End Subramanian
	
	//Cash Transfer Purpose
	public List<Stock> stockByCountryBranch(BigDecimal countryBranchId);
	
	//added by nazish for common save
	
	public void saveOrUpdate(T entity);
	
	//Added by  Rabil  ON 12 Oct 2016 to save both FS_CUSTOMER and FS_CUSTOMER_ID_PROOF
	
	public void saveOrUpdateCustomerAndIdProof(Customer customerinfo,CustomerIdProof idProof) throws Exception;
	
	
	
	
	public List<Date> getSysDate(BigDecimal appCountryId);
	public Date getSysDateTimestamp(BigDecimal countryId);
	
	//public Connection getDataSourceFromHibernateSession();
	
	public  List<AccountBalance> getExchangeRateFromAccBal(BigDecimal bankId,BigDecimal currencyId, String accountNo);

	public void saveOrUpdateWithPartialSave(T entity);
	
	public List<RoleMaster> getRoleList(BigDecimal roleId);
	
	public List<CountryMaster> getCountryAlphaList(BigDecimal countryId);
	
	
	public BigDecimal getCountryIdBasedOnCountryAlpha2Code(String alphaCode);
	
	public BigDecimal getApplicationIdBasedApplicationCode(String applicationCode);
	
	public String getCurrencyQuote(BigDecimal currencyId);
	public String getCurrencyQuoteBasedOnAlphaCode(String alphaTwoCode);
	
	public List<CurrencyMaster> getCurrency(BigDecimal currencyId);
	
	public String callGetAmountInTextFunction(String arg1, BigDecimal arg2, BigDecimal arg3);
	
	
	//GET DENOMINATION ID BASED ON CURRENCY CODE
	public BigDecimal getDenomiationId(String quoteName);
	
	//aDDED BY NAZISH FOR MOBILE AND TELEPHONE NO VALIDATION CHECK FUNCTION
	public String validateMobileTelephone(String countryAlphaCode, String contactNo, String noType);
	
	public List<Employee> getEmployeeDetail(BigDecimal employeeId);
	
	//Get corresponding bank list based on application country
	public List<BankMasterDTO> getCoresBankListForApplicationCountry(BigDecimal countryId);
	
	//Get Company id based on company code
	public BigDecimal getCompanyIdFromCode(BigDecimal companyCode);
	public List<BankMasterDTO>  getBeneBankListForApplicationCountry(BigDecimal countryId);
	
	//Get country id based on country code
	public BigDecimal getCountriIdFromCode(String countryCode);
	
	
	public String  getCountryName(String countryCode);
	public String getBankName(String bankCode);
	
	//Added by Rabil on 05102015 to get the Local bank
	public List<ViewBankDetails> getLocalBankListFromView(BigDecimal countryId);
	public List<ViewBankDetails> getLocalBankListFromView(BigDecimal countryId,BigDecimal bankId);
	
	//Added by Rabil on 19102015 to check the active customer. 
    public List<ViewActiveCustomerCheck> getActiveCustomerFromView(BigDecimal countryId,BigDecimal customerId);
    
    public HashMap<String, String> getNextDocumentReferenceNumbers(int countryId, int companyId, int documentId, int dealYear, String processIn, BigDecimal countryBranchCode) throws AMGException;
	
	public HashMap<String, String> getNextDocumentRefNumber(int countryId, int companyId, int documentId, int financialYear, String processIn, BigDecimal branchId) throws AMGException;

	public java.util.Date getValidExpiryDateforFCSale(String idNumber);
	public List<Customer> getCustomerDeatilsBasedOnRef(BigDecimal custRef);
	public List<Employee> getAllEmployeeListBasedonLocation(BigDecimal branchId);
	
	public List<BankMaster> getBankDetailsList(BigDecimal languageId,BigDecimal countryId,BigDecimal bankId);
	public List<CustomerIdproofView> getCustomerIdProofDetailsFromView(BigDecimal customerId);
	public List<BankBranch>  getBranchListBasedOnBank(BigDecimal bankid);
	
	public void saveOrUpdateLoginLogoutHistory(LoginLogoutHistory loginLogoutHistory);
	//public void saveOrUpdateLoginLogoutHistoryDetails(BigDecimal countryId,String loginType, String userName,String visitedPage,Timestamp currentTime);
	public BigDecimal getCurrencyIDFromQuote(String currencyQuote);
	//Added by Rabil
	public List<ViewAmtbCouponReport> getListFromAmtbCouponReport(AmtbCouponDT amtb);
	
	public void saveOrUpdateCustomerAndIdProofAndMobile(Customer customerinfo,CustomerIdProof idProof,CustomerMobileLogModel customerMobile) throws Exception;
	
	public void saveOrUpdateCustomerAndMobile(Customer customerinfo,CustomerMobileLogModel customerMobile) throws Exception;
	
	public List<BankApplicability> getBeneBankList(BigDecimal countryId);
	
	public List<Employee> getEmployeeDetailByEmpID(BigDecimal employeeId);
	
	public boolean checkCorporateUser(BigDecimal countryId,BigDecimal countryBranchId,String userName);
	
}