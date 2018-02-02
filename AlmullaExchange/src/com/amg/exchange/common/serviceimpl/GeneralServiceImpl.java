package com.amg.exchange.common.serviceimpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.bean.RoutingCountry;
import com.amg.exchange.common.dao.IGeneralDao;
import com.amg.exchange.common.dto.BankMasterDTO;
import com.amg.exchange.common.model.BizComponentConfDetail;
import com.amg.exchange.common.model.BizComponentData;
import com.amg.exchange.common.model.BizComponentDataDesc;
import com.amg.exchange.common.model.BusinessComponent;
import com.amg.exchange.common.model.CityMaster;
import com.amg.exchange.common.model.CityMasterDesc;
import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.model.DistrictMaster;
import com.amg.exchange.common.model.DistrictMasterDesc;
import com.amg.exchange.common.model.LanguageType;
import com.amg.exchange.common.model.RuleApplicationMaster;
import com.amg.exchange.common.model.RulePageMaster;
import com.amg.exchange.common.model.StateMaster;
import com.amg.exchange.common.model.StateMasterDesc;
import com.amg.exchange.common.service.ICommonService;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.common.service.IMutualService;
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

@SuppressWarnings("serial")
@Service("generalServiceImpl")
public class GeneralServiceImpl<T> implements java.io.Serializable, IGeneralService<T>,ICommonService<T>, IMutualService<T>{

	@Autowired
	IGeneralDao<T> generalDao;

	public IGeneralDao<T> getGeneralDao() {
		return generalDao;
	}

	public void setGeneralDao(IGeneralDao<T> generalDao) {
		this.generalDao = generalDao;
	}

	@Override
	public List<CityMaster> getCity(BigDecimal districtId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CountryMaster> getCountry() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StateMaster> getState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CompanyMaster> getCompany(BigDecimal countryId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StateMaster> getState(BigDecimal countryId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DistrictMaster> getDistrict(BigDecimal stateId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(T entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(T entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(T entity) {
		// TODO Auto-generated method stub

	}

	@Transactional
	@Override
	public List<CountryMasterDesc> getCountryList(BigDecimal languageId) { 

		return getGeneralDao().getCountryList(languageId);
	}

	@Transactional
	@Override
	public List<CountryMasterDesc> getNationalityList(BigDecimal languageId) {

		return getGeneralDao().getNationalityList(languageId);
	}

	@Transactional
	@Override
	public List<StateMasterDesc> getStateList(BigDecimal languageId, BigDecimal countryId) {

		return getGeneralDao().getStateList(languageId, countryId);
	}

	@Transactional
	@Override
	public List<DistrictMasterDesc> getDistrictList(BigDecimal languageId, BigDecimal countryId, BigDecimal stateId) {

		return getGeneralDao().getDistrictList(languageId, countryId, stateId);
	}

	@Transactional
	@Override
	public List<CityMasterDesc> getCityList(BigDecimal languageId, BigDecimal countryId, BigDecimal stateId, BigDecimal districtId) {

		return getGeneralDao().getCityList(languageId, countryId, stateId, districtId);
	}

	@Override
	@Transactional
	public List<StateMasterDesc> getStateList(BigDecimal languageId) {

		return getGeneralDao().getStateList(languageId);
	}

	@Override
	@Transactional
	public List<DistrictMasterDesc> getDistrictList(BigDecimal languageId) {

		return getGeneralDao().getDistrictList(languageId);
	}

	@Override
	@Transactional
	public List<CityMasterDesc> getCityList(BigDecimal languageId) {

		return getGeneralDao().getCityList(languageId); 
	}

	@Override
	@Transactional
	public List<BusinessComponent> getAllComponentList() {

		return getGeneralDao().getAllComponentList();
	}

	@Override
	@Transactional
	public List<RuleApplicationMaster> getAllApplicationList() {

		return getGeneralDao().getAllApplicationList(); 
	}

	@Override
	@Transactional
	public List<CompanyMasterDesc> getAllCompanyList(BigDecimal languageId) {

		return getGeneralDao().getAllCompanyList(languageId);
	}

	@Override
	@Transactional
	public List<RulePageMaster> getAllPageList() {

		return getGeneralDao().getAllPageList(); 
	}

	@Override
	@Transactional
	public BigDecimal getPageId(String pageName) {

		return getGeneralDao().getPageId(pageName);
	}

	@Override
	@Transactional
	public Map<String, BizComponentConfDetail> getComponentBehavior(
			List<String> componentList, BigDecimal levelId, 
			BigDecimal applicationId, BigDecimal companyId,
			BigDecimal countryId, BigDecimal pageId) {

		return getGeneralDao().getComponentBehavior(componentList, levelId, applicationId, companyId, countryId, pageId);
	} 

	@Override
	@Transactional
	public Map<BigDecimal, String> getAllComponentComboData(
			BigDecimal componentConfId, BigDecimal languageId) {

		return getGeneralDao().getAllComponentComboData(componentConfId, languageId);
	}

	@Override
	@Transactional
	public Map<BigDecimal, String> getAllCountry(BigDecimal languageId) {

		return getGeneralDao().getAllCountry(languageId);
	}

	@Override
	@Transactional
	public Map<BigDecimal, String> getAllNationality(BigDecimal languageId) {

		return getGeneralDao().getAllNationality(languageId);
	}

	@Override
	@Transactional
	public List<LanguageType> getAllLanguages() {

		return getGeneralDao().getAllLanguages();
	}

	@Override
	@Transactional
	public Map<String, BizComponentConfDetail> getComponentBehaviorNew(List<String> componentList, BigDecimal levelId, BigDecimal applicationId, BigDecimal companyId, BigDecimal countryId, BigDecimal pageId) {

		return getGeneralDao().getComponentBehaviorNew(componentList, levelId, applicationId, companyId, countryId, pageId);
	}

	@Override
	@Transactional
	public List<BankMaster> getAllBankList(BigDecimal languageId,BigDecimal countryId) {
		return getGeneralDao().getAllBankList(languageId,countryId);
	} 

	@Override
	@Transactional
	public List<BankBranch> getBankBranchList(BigDecimal countryId,BigDecimal bankId) {
		return getGeneralDao().getBankBranchList(countryId,bankId);
	}

	@Override
	@Transactional
	public Map<BigDecimal, String> getComponentData(String component,
			BigDecimal levelId, BigDecimal applicationId, BigDecimal companyId,
			BigDecimal countryId, BigDecimal pageId, BigDecimal languageId) {

		return getGeneralDao().getComponentData(component, levelId, applicationId, companyId, countryId, pageId, languageId);
	}

	@Override
	@Transactional
	public String getCivilIdStatus(BigDecimal civilID) {

		return getGeneralDao().getCivilIdStatus(civilID);
	}

	@Override
	@Transactional
	public BizComponentDataDesc getComponentId(String inputString, BigDecimal langId) {

		return getGeneralDao().getComponentId(inputString, langId);
	}

	@Override
	@Transactional
	public BizComponentData getComponentDesc(BigDecimal inputId, BigDecimal langId) {

		return getGeneralDao().getComponentDesc(inputId, langId);
	}

	//added by Nazish For list of business country
	@Transactional
	@Override
	public List<CountryMasterDesc> getBusinessCountryList(BigDecimal languageId) { 

		return getGeneralDao().getBusinessCountryList(languageId);
	}

	@Override
	@Transactional
	public List<BankAccountDetails> getBankAccountDetailsList(BigDecimal bankId) {

		return getGeneralDao().getBankAccountDetailsList(bankId);
	}

	@Override
	@Transactional
	public String getMessage(BigDecimal countryId, BigDecimal languageId) {
		return getGeneralDao().getMessage(countryId, languageId);
	}

	@Override
	@Transactional
	public List<CurrencyMaster> getCurrencyList() {

		return getGeneralDao().getCurrencyList();
	}

	@Override
	@Transactional
	public List<BankMaster> getBankList(BigDecimal countryId) {
		return getGeneralDao().getBankList(countryId);
	}

	@Override
	@Transactional
	public List<CountryBranch> getBranchDetails(BigDecimal countryId) {
		return getGeneralDao().getBranchDetails(countryId);
	}

	@Override
	@Transactional
	public List <CountryBranch> getBranchDetailsForToLocation(BigDecimal countryId,BigDecimal countryBranchId) {
		return getGeneralDao().getBranchDetailsForToLocation(countryId,countryBranchId);
	}

	@Override
	@Transactional
	public List <CountryBranch> getBranchDetailsFromBeneStatus(BigDecimal countryId,BigDecimal countryBranchId) {
		return getGeneralDao().getBranchDetailsFromBeneStatus(countryId,countryBranchId);
	}

	@Override
	@Transactional
	public List<RoleMaster> getRole() {
		return getGeneralDao().getRole();
	}

	@Override
	@Transactional
	public BigDecimal callSaleProjection(BigDecimal companyId,BigDecimal exchangeCountry,	BigDecimal bankCountry, BigDecimal currency, BigDecimal bankId,	BigDecimal noOfDays) {
		return getGeneralDao().callSaleProjection(companyId,exchangeCountry, bankCountry, currency, bankId, noOfDays);
	}

	@Override
	@Transactional
	public BigDecimal callValueDatedTransaction(BigDecimal companyId,BigDecimal exchangeCountry, BigDecimal bankCountry, BigDecimal currency, BigDecimal bankId, BigDecimal noOfDays) {
		return getGeneralDao().callValueDatedTransaction(companyId,exchangeCountry, bankCountry, currency, bankId, noOfDays);
	}

	@Override
	@Transactional
	public String getCountryName(BigDecimal countryId) {
		return getGeneralDao().getCountryName(countryId);
	}

	@Override
	@Transactional
	public List<BankCountryPopulationBean> getAllBankContry() {
		return getGeneralDao().getAllBankContry();
	} 
	@Override
	@Transactional
	public List<CurrencyMaster> getCountryCurrencyList(BigDecimal countryId) {

		return getGeneralDao().getCountryCurrencyList(countryId);
	}

	@Transactional
	@Override
	public List<BankMaster> getBankAccordingToBankCountry(BigDecimal aplicationCountry, BigDecimal countryId) {
		return getGeneralDao().getBankAccordingToBankCountry(aplicationCountry, countryId);
	}
	@Transactional
	@Override
	public List<BankMaster> getAllBankListFromBankMaster() {
		// TODO Auto-generated method stub
		return getGeneralDao().getAllBankListFromBankMaster();
	}
	@Transactional
	@Override
	public String getCountryName(BigDecimal languageId, BigDecimal countryId) {
		return getGeneralDao().getCountryName(languageId, countryId);
	}

	@Override
	@Transactional
	public List<ArticleMaster> getArtilces() {
		return getGeneralDao().getArtilces();
	}

	@Override
	@Transactional
	public List<ArticleDetails> getLevelData(BigDecimal articleId) {
		return getGeneralDao().getLevelData(articleId);
	}

	@Override
	@Transactional
	public List<IncomeRangeMaster> getIncomeRange(BigDecimal countryId,BigDecimal articleDetailId) {
		return getGeneralDao().getIncomeRange(countryId,articleDetailId);
	}

	@Override
	@Transactional
	public List<BankMaster> getAllBankCountryList() {
		return getGeneralDao().getAllBankCountryList();
	}

	@Override
	@Transactional
	public List<BankCountryPopulationBean> getBankContry(BigDecimal applicationCountryId) {
		return getGeneralDao().getBankContry(applicationCountryId);
	}
	//added By Nazish 13-11-2014 for Fx Deal
	@Override
	@Transactional
	public List<CompanyMasterDesc> getCompanyList(BigDecimal companyId,BigDecimal languageId) {
		return getGeneralDao().getCompanyList(companyId,languageId);
	}

	@Override
	@Transactional
	public List<Document> getDocument(BigDecimal id,BigDecimal languageId) {
		return getGeneralDao().getDocument(id,languageId);
	}

	@Override
	@Transactional
	public List<UserFinancialYear> getDealYear(Date currentDate) {
		return getGeneralDao().getDealYear(currentDate);
	}

	@Override
	@Transactional
	public String getNextDocumentReferenceNumber(int countryId, int companyId, int documentId, int financialYear, String processIn,BigDecimal branchCode) {
		return getGeneralDao().getNextDocumentReferenceNumber(countryId,companyId,documentId,financialYear,processIn,branchCode);
	}

	@Transactional
	@Override
	public List<BankAccountDetails> getAccountNumber(BigDecimal bankId,	BigDecimal currencyId) {
		return getGeneralDao().getAccountNumber(bankId, currencyId);
	}

	//Added by RamMohan For Getting FC Amount
	@Override
	@Transactional
	public BigDecimal getForeignCurrencyBalanceFromAccDetailID(String accountDetailId)
	{
		return getGeneralDao().getForeignCurrencyBalanceFromAccDetailID(accountDetailId);
	}

	@Override
	@Transactional
	public List<Customer> getMobileCheck(BigDecimal countryId,String mobileNo) {
		return getGeneralDao().getMobileCheck(countryId,mobileNo);
	}

	@Override
	@Transactional
	public List<Customer> getEmailCheck(String email) {
		return getGeneralDao().getEmailCheck(email);
	}

	@Override
	@Transactional
	public String getStateName(BigDecimal languageId, BigDecimal countryId,
			BigDecimal stateId) {
		return getGeneralDao().getStateName(languageId, countryId, stateId);
	}

	@Override
	@Transactional
	public String getDistrictName(BigDecimal languageId, BigDecimal countryId,
			BigDecimal stateId, BigDecimal districtId) {
		return getGeneralDao().getDistrictName(languageId, countryId, stateId, districtId);
	}

	@Override
	@Transactional
	public String getCityName(BigDecimal languageId, BigDecimal countryId,
			BigDecimal stateId, BigDecimal districtId, BigDecimal cityId) {
		return getGeneralDao().getCityName(languageId, countryId, stateId, districtId, cityId);
	}

	@Override
	@Transactional
	public String getTitleName(BigDecimal languageId,BigDecimal title) {
		return getGeneralDao().getTitleName(languageId,title);
	}

	@Override
	@Transactional
	public String getNationalityName(BigDecimal languageId,BigDecimal countryId) {
		return getGeneralDao().getNationalityName(languageId,countryId);
	}

	@Override
	@Transactional
	public String getArticleName(BigDecimal articleId) {
		return getGeneralDao().getArticleName(articleId);
	}

	@Override
	@Transactional
	public String getLevelName(BigDecimal level) {
		return getGeneralDao().getLevelName(level);
	}

	@Override
	@Transactional
	public String getIncomeRangeName(BigDecimal income) {
		return getGeneralDao().getIncomeRangeName(income);
	}

	@Override
	@Transactional
	public String getEmplTypeName(BigDecimal languageId,BigDecimal employment) {
		return getGeneralDao().getEmplTypeName(languageId,employment);
	}

	@Override
	@Transactional
	public String getProfessionName(BigDecimal languageId,BigDecimal profession) {
		return getGeneralDao().getProfessionName(languageId,profession);
	}

	@Override
	@Transactional
	public List<CustomerLogin> getEmailCheckUser(String email) {
		return getGeneralDao().getEmailCheckUser(email);
	}

	@Override
	@Transactional
	public String getSearchIdType(BigDecimal languageId,BigDecimal idType){
		return getGeneralDao().getSearchIdType(languageId, idType);

	}
	//To get CB,IB,LB,Name :RamMohan,Date 10-Nov-2014
	@Override
	@Transactional
	public List<BankApplicability> getCoresBankLocalBankInterBankList(BigDecimal countryId,BigDecimal getBankCountry) {
		return getGeneralDao().getCoresBankLocalBankInterBankList(countryId,getBankCountry);
	}

	@Override
	@Transactional
	public List<BankApplicability> getCoresBankList(BigDecimal countryId){
		return getGeneralDao().getCoresBankList(countryId);
	}

	@Override
	@Transactional
	public List<BankApplicability> getCorrespondingLocalFundingBanks(BigDecimal countryId) {
		return getGeneralDao().getCorrespondingLocalFundingBanks(countryId);
	}

	@Override
	@Transactional
	public List<BankMaster> getCorrespondingBanks(BigDecimal countryId){
		return getGeneralDao().getCorrespondingBanks(countryId);
	}

	//To get LB,Name :RamMohan,Date 11-Nov-2014
	@Override
	@Transactional
	public List<BankApplicability> getLocalBankList(BigDecimal countryId)
	{
		return getGeneralDao().getLocalBankList(countryId);
	}

	@Override
	@Transactional
	public List<AccountBalance> getBankBalance(String accountNo) {
		return getGeneralDao().getBankBalance(accountNo);
	}

	@Override
	@Transactional
	public BigDecimal getAvgRateFromAccountBalanceTable(BigDecimal accountDetailId) {
		return getGeneralDao().getAvgRateFromAccountBalanceTable(accountDetailId);
	}

	@Override
	@Transactional
	public List<BankApplicability> getBankListbyIndicator(BigDecimal countryId,BigDecimal indicator)
	{
		return getGeneralDao().getBankListbyIndicator(countryId,indicator);
	}

	@Override
	@Transactional
	public List<BankApplicability> getBankListbyIndicatoronly(BigDecimal indicator,BigDecimal companyId)
	{
		return getGeneralDao().getBankListbyIndicatoronly(indicator,companyId);
	}

	@Transactional
	@Override
	public String getBankName(BigDecimal bankId) {
		return getGeneralDao().getBankName(bankId);
	}

	@Transactional
	@Override
	public String getCurrencyName(BigDecimal currencyId) {
		return getGeneralDao().getCurrencyName(currencyId);
	}

	@Transactional
	@Override
	public String getValidity(BigDecimal countryId,
			BigDecimal businessComponentId) {
		return  getGeneralDao().getValidity(countryId, businessComponentId);
	}

	@Override
	@Transactional
	public List<RemittanceModeDescription> getRemittanceList(
			BigDecimal languageId) {

		return getGeneralDao().getRemittanceList(languageId);
	}

	@Override
	@Transactional
	public List<DeliveryModeDesc> getDeliveryModeList(BigDecimal languageId) {
		return getGeneralDao().getDeliveryModeList(languageId);
	}

	@Override
	@Transactional
	public List<BizComponentDataDesc> getComponentNameIndividual(
			BigDecimal languageId, BigDecimal componentId) {

		return getGeneralDao().getComponentNameIndividual(languageId, componentId);
	}

	@Override
	@Transactional
	public List<BizComponentDataDesc> getComponentNameCorporate(
			BigDecimal languageId, BigDecimal componentId) {

		return getGeneralDao().getComponentNameCorporate(languageId, componentId);
	}

	@Override
	@Transactional
	public List<RoutingCountry> getAllRoutingCountryList(BigDecimal beneficaryCountryId,BigDecimal beneficaryCurrencyId,BigDecimal serviceId,BigDecimal bankId) {
		return getGeneralDao().getAllRoutingCountryList(beneficaryCountryId,beneficaryCurrencyId,serviceId,bankId);
	}

	@Override
	@Transactional
	public List<RoutingCountry> getAllRoutingBankList(BigDecimal routingCountryId,BigDecimal beneficaryCurrencyId,BigDecimal serviceId,BigDecimal bankId) {
		return getGeneralDao().getAllRoutingBankList(routingCountryId,beneficaryCurrencyId,serviceId,bankId);
	}

	@Override
	@Transactional
	public List<RoutingHeader> getAllSpecificList(BigDecimal routingCountryId,BigDecimal beneficaryCurrencyId,BigDecimal serviceId,BigDecimal routingbankId) {
		return getGeneralDao().getAllSpecificList(routingCountryId,beneficaryCurrencyId,serviceId,routingbankId);
	}

	@Override
	@Transactional
	public List<ServiceMasterDesc> getAllServiceDesc(BigDecimal languageId) {
		return getGeneralDao().getAllServiceDesc(languageId);
	}

	@Override
	@Transactional
	public List<SpecialRateRequest> getSpotRateDetails(BigDecimal customerId,BigDecimal bankId,BigDecimal companyId, BigDecimal documentId, BigDecimal financialyear, String documentNo, BigDecimal appcountryId) {
		return getGeneralDao().getSpotRateDetails(customerId,bankId,companyId,documentId,financialyear,documentNo,appcountryId);
	}

	@Override
	@Transactional
	public List<Employee> getEmployeelist(BigDecimal countryId,BigDecimal branchId,BigDecimal roleId) {
		// TODO Auto-generated method stub
		return getGeneralDao().getEmployeelist(countryId,branchId,roleId);
	}

	@Override
	@Transactional
	public List<RateAlertFrequency> frequencyDetailsLst(BigDecimal languageId) {
		// TODO Auto-generated method stub
		return getGeneralDao().frequencyDetailsLst(languageId);
	}

	@Override
	@Transactional
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
			String sClientID, String sClientSearchType,String amlStatus) {
		return "";
	}

	@Override
	@Transactional
	public List<CountryMasterDesc> getCountryListExceptAppCountry(BigDecimal languageId, BigDecimal countryId) {

		return getGeneralDao().getCountryListExceptAppCountry(languageId, countryId);
	}

	@Override
	@Transactional
	public Date getValidExpiryDate(String civilId) {
		return getGeneralDao().getValidExpiryDate(civilId);
	}

	@Override
	@Transactional
	public List<CustomerIdProof> getCustomerIdBasedOnCivilId(String civilId) {
		return getGeneralDao().getCustomerIdBasedOnCivilId(civilId);
	}

	@Override
	@Transactional
	public String getCustomerNameBasedOnCustomerId(BigDecimal customerId) {
		return getGeneralDao().getCustomerNameBasedOnCustomerId(customerId);
	}



	@Override
	@Transactional
	public List<BankMaster> getAllBankCodeFromBankMaster(String bankCode) {
		return getGeneralDao().getAllBankCodeFromBankMaster(bankCode);
	}

	@Override
	@Transactional
	public String getTelephoneCountryBasedOnNationality(BigDecimal bankCode) {
		return getGeneralDao().getTelephoneCountryBasedOnNationality(bankCode);
	}

	@Override
	@Transactional
	public BigDecimal getOccupationId(BigDecimal customerId) {
		return getGeneralDao().getOccupationId(customerId);
	}

	@Override
	@Transactional
	public String getOccupationDesc(BigDecimal occupationID,BigDecimal languageId) {
		return getGeneralDao().getOccupationDesc(occupationID,languageId);
	}


	// Start by subramanian 
	@Override
	@Transactional
	public String getCountryCode(BigDecimal countryId){
		return getGeneralDao().getCountryCode(countryId);
	}
	@Override
	@Transactional
	public String getCurrencyCode(BigDecimal currencyId){
		return getGeneralDao().getCurrencyCode(currencyId);
	}
	@Override
	@Transactional
	public String getBankCode(BigDecimal bankId){
		return getGeneralDao().getBankCode(bankId);
	}
	@Override
	@Transactional
	public BigDecimal getBankBranchCode(BigDecimal bankBranchId){
		return getGeneralDao().getBankBranchCode(bankBranchId);
	}
	@Override
	@Transactional
	public String getRemittanceCode(BigDecimal remittanceModeId){
		return getGeneralDao().getRemittanceCode(remittanceModeId);
	}
	@Override
	@Transactional
	public String getDeliveryCode(BigDecimal deliveryId){
		return getGeneralDao().getDeliveryCode(deliveryId);
	}

	// End Subramanian
	@Override
	@Transactional
	public List<Stock> stockByCountryBranch(BigDecimal countryBranchId){
		return getGeneralDao().stockByCountryBranch(countryBranchId);
	}

	@Override
	@Transactional
	public void saveOrUpdate(T entity) {
		getGeneralDao().saveOrUpdate(entity);

	}
	@Override
	@Transactional
	public List<Date> getSysDate(BigDecimal appCountryId){
		return getGeneralDao().getSysDate(appCountryId);
	}

	@Override
	@Transactional
	public Date getSysDateTimestamp(BigDecimal countryId) {
		return getGeneralDao().getSysDateTimestamp(countryId);
	}

	/*@Override
	@Transactional
	public Connection getDataSourceFromHibernateSession() {
		return  getGeneralDao().getDataSourceFromHibernateSession();
	}*/

	@Override
	@Transactional
	public   List<AccountBalance> getExchangeRateFromAccBal(BigDecimal bankId,BigDecimal currencyId, String accountNo){
		return getGeneralDao().getExchangeRateFromAccBal(bankId,currencyId,accountNo);
	}

	@Override
	@Transactional
	public void saveOrUpdateWithPartialSave(T entity) {
		getGeneralDao().saveOrUpdateWithPartialSave(entity);				
	}

	@Override
	@Transactional
	public List<RoleMaster> getRoleList(BigDecimal roleId) {
		return getGeneralDao().getRoleList(roleId);
	}

	@Override
	@Transactional
	public List<CountryMaster> getCountryAlphaList(BigDecimal countryId) {
		return getGeneralDao().getCountryAlphaList(countryId);
	}


	@Override
	@Transactional
	public BigDecimal getCountryIdBasedOnCountryAlpha2Code(String alphaCode) {
		return getGeneralDao().getCountryIdBasedOnCountryAlpha2Code(alphaCode);
	}

	@Override
	@Transactional
	public BigDecimal getApplicationIdBasedApplicationCode(String applicationCode){
		return getGeneralDao().getApplicationIdBasedApplicationCode(applicationCode);
	}

	@Override
	@Transactional
	public String getCurrencyQuote(BigDecimal currencyId){
		return getGeneralDao().getCurrencyQuote(currencyId);
	}


	@Override
	@Transactional
	public String getCurrencyQuoteBasedOnAlphaCode(String alphaTwoCode){
		return getGeneralDao().getCurrencyQuoteBasedOnAlphaCode(alphaTwoCode);
	}

	@Override
	@Transactional
	public List<CurrencyMaster> getCurrency(BigDecimal currencyId){
		return getGeneralDao().getCurrency(currencyId);
	}

	@Override
	@Transactional
	public String callGetAmountInTextFunction(String arg1, BigDecimal arg2, BigDecimal arg3){
		return getGeneralDao().callGetAmountInTextFunction(arg1, arg2,arg3);
	}

	@Override
	@Transactional
	public BigDecimal getDenomiationId(String quoteName){

		return getGeneralDao().getDenomiationId(quoteName);
	}

	@Override
	@Transactional
	public String validateMobileTelephone(String countryAlphaCode,
			String contactNo, String noType) {

		return getGeneralDao().validateMobileTelephone(countryAlphaCode, contactNo, noType);
	}

	@Override
	@Transactional
	public List<Employee> getEmployeeDetail(BigDecimal employeeId){

		return getGeneralDao().getEmployeeDetail(employeeId);
	}

	@Override
	@Transactional
	public List<BigDecimal> getAllServiceDescByCountry(BigDecimal countryid){
		return getGeneralDao().getAllServiceDescByCountry(countryid);
	}
	/*@Override
			@Transactional
			public List<BeneCountryService> getAllServiceDescByCountry(BigDecimal countryid){

				return getGeneralDao().getAllServiceDescByCountry(countryid);
			}*/
	@Override
	@Transactional
	public List<ServiceMasterDesc> getServiceDesc(BigDecimal languageId, BigDecimal serviceId){
		return getGeneralDao().getServiceDesc(languageId,serviceId);
	}

	@Override
	@Transactional
	public List<BankMasterDTO> getCoresBankListForApplicationCountry(
			BigDecimal countryId) {

		return getGeneralDao().getCoresBankListForApplicationCountry(countryId);
	}

	@Override
	@Transactional
	public BigDecimal getCompanyIdFromCode(BigDecimal companyCode) {

		return getGeneralDao().getCompanyIdFromCode(companyCode);
	}

	@Override
	@Transactional
	public BigDecimal getCountriIdFromCode(String countryCode) {

		return getGeneralDao().getCountriIdFromCode(countryCode);
	}

	@Override
	@Transactional
	public String  getCountryName(String countryCode){

		return getGeneralDao().getCountryName(countryCode);
	}

	@Override
	@Transactional
	public String getBankName(String bankCode){

		return getGeneralDao().getBankName(bankCode);
	}



	@Override
	@Transactional
	public String getCustomerNameCustomerId(BigDecimal customerId) {
		return getGeneralDao().getCustomerNameCustomerId(customerId);
	}

	@Override
	@Transactional
	public List<BankMasterDTO> getBeneBankListForApplicationCountry(
			BigDecimal countryId) {
		// TODO Auto-generated method stub
		return getGeneralDao().getBeneBankListForApplicationCountry(countryId);
	}

	@Override
	@Transactional
	public List<ViewBankDetails> getLocalBankListFromView(
			BigDecimal countryId) {
		// TODO Auto-generated method stub
		return getGeneralDao().getLocalBankListFromView(countryId);
	}

	@Override
	@Transactional
	public List<ViewActiveCustomerCheck> getActiveCustomerFromView(BigDecimal countryId, BigDecimal customerId) {
		// TODO Auto-generated method stub
		return getGeneralDao().getActiveCustomerFromView(countryId, customerId);
	}

	@Override
	@Transactional
	public HashMap<String, String> getNextDocumentReferenceNumbers(int countryId, int companyId, int documentId, int dealYear, String processIn, BigDecimal countryBranchCode)throws AMGException{
		return getGeneralDao().getNextDocumentReferenceNumbers(countryId,companyId,documentId,dealYear,processIn,countryBranchCode);
	}

	@Override
	@Transactional
	public HashMap<String, String> getNextDocumentRefNumber(
			int countryId, int companyId, int documentId,
			int financialYear, String processIn, BigDecimal branchId)
					throws AMGException {
		return getGeneralDao().getNextDocumentRefNumber(countryId, companyId, documentId, financialYear, processIn, branchId);
	}

	@Override
	@Transactional
	public Date getValidExpiryDateforFCSale(String idNumber) {
		return getGeneralDao().getValidExpiryDateforFCSale(idNumber);
	}

	@Override
	@Transactional
	public List<Customer> getCustomerDeatilsBasedOnRef(BigDecimal custRef) {
		return getGeneralDao().getCustomerDeatilsBasedOnRef(custRef);
	}

	@Override
	@Transactional
	public List<Employee> getAllEmployeeListBasedonLocation(BigDecimal branchId) {
		return getGeneralDao().getAllEmployeeListBasedonLocation(branchId);
	}

	@Override
	@Transactional
	public List<BankMaster> getBankDetailsList(BigDecimal languageId,BigDecimal countryId, BigDecimal bankId) {
		return getGeneralDao().getBankDetailsList(languageId, countryId, bankId);
	}

	@Override
	@Transactional
	public List<CountryBranch> getCountryBranchLocCode(
			BigDecimal countryBranchId) {
	 
		return  getGeneralDao().getCountryBranchLocCode(countryBranchId);
	}

	@Override
	@Transactional
	public List<ViewBankDetails> getLocalBankListFromView(BigDecimal countryId,
			BigDecimal bankId) {
 
		return getGeneralDao().getLocalBankListFromView(countryId,bankId);
	}

	@Override
	@Transactional
	public List<CustomerIdproofView> getCustomerIdProofDetailsFromView(
			BigDecimal customerId) {
		 
		return  getGeneralDao().getCustomerIdProofDetailsFromView(customerId);
	}

	@Override
	@Transactional
	public List<BankBranch> getBranchListBasedOnBank(BigDecimal bankid) {
	 
		return getGeneralDao().getBranchListBasedOnBank(bankid);
	}

	
	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void saveOrUpdateCustomerAndIdProof(Customer customerinfo,
			CustomerIdProof idProof) throws Exception{
		getGeneralDao().saveOrUpdateCustomerAndIdProof(customerinfo, idProof);
		
	}

	@Override
	@Transactional
	public void saveOrUpdateLoginLogoutHistory(
			LoginLogoutHistory loginLogoutHistory) {
	
		getGeneralDao().saveOrUpdateLoginLogoutHistory(loginLogoutHistory);
	}

	@Override
	@Transactional
	public BigDecimal getCurrencyIDFromQuote(String currencyQuote) {
		
		return getGeneralDao().getCurrencyIDFromQuote(currencyQuote);
	}

	@Override
	@Transactional
	public List<ViewAmtbCouponReport> getListFromAmtbCouponReport(AmtbCouponDT amtb) {
		// TODO Auto-generated method stub
		return getGeneralDao().getListFromAmtbCouponReport(amtb);
	}

	/*@Override
	@Transactional
	public void saveOrUpdateLoginLogoutHistoryDetails(BigDecimal countryId,
			String loginType, String userName, String visitedPage,
			Timestamp currentTime) {
		getGeneralDao().saveOrUpdateLoginLogoutHistoryDetails(countryId, loginType, userName, visitedPage, currentTime);
		
	}*/
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void saveOrUpdateCustomerAndIdProofAndMobile(Customer customerinfo,
			CustomerIdProof idProof, CustomerMobileLogModel CustomerMobile)
			throws Exception {
		getGeneralDao().saveOrUpdateCustomerAndIdProofAndMobile(customerinfo, idProof, CustomerMobile);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void saveOrUpdateCustomerAndMobile(Customer customerinfo,
			CustomerMobileLogModel CustomerMobile) throws Exception {
		getGeneralDao().saveOrUpdateCustomerAndMobile(customerinfo, CustomerMobile);
	}
	
	@Override
	@Transactional
	public List<BankApplicability> getBeneBankList(BigDecimal countryId) {
		// TODO Auto-generated method stub
		return getGeneralDao().getBeneBankList(countryId);
	}

	@Override
	@Transactional
	public List<Employee> getEmployeeDetailByEmpID(BigDecimal employeeId) {
		return getGeneralDao().getEmployeeDetailByEmpID(employeeId);
	}

	@Override
	@Transactional
	public boolean checkCorporateUser(BigDecimal countryId,BigDecimal countryBranchId, String userName) {
		return getGeneralDao().checkCorporateUser(countryId, countryBranchId, userName);
	}


}