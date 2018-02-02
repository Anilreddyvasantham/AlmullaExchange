package com.amg.exchange.registration.serviceimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.registration.dao.ICustomerRegistrationBranchDao;
import com.amg.exchange.registration.model.ArcmateScanMaster;
import com.amg.exchange.registration.model.ContactDetail;
import com.amg.exchange.registration.model.CorpatePartnerInfoView;
import com.amg.exchange.registration.model.CorporateCustomerInfoView;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerChangeLog;
import com.amg.exchange.registration.model.CustomerContactDetailView;
import com.amg.exchange.registration.model.CustomerDependant;
import com.amg.exchange.registration.model.CustomerEmployeeInfoView;
import com.amg.exchange.registration.model.CustomerEmploymentInfo;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.registration.model.CustomerIdproofView;
import com.amg.exchange.registration.model.CustomerImageVerification;
import com.amg.exchange.registration.model.CustomerInfoView;
import com.amg.exchange.registration.model.CustomerMobileLogModel;
import com.amg.exchange.registration.model.CustomerSponsor;
import com.amg.exchange.registration.model.DMSApplMap;
import com.amg.exchange.registration.model.DmsDocBlobUpload;
import com.amg.exchange.registration.model.DmsMas;
import com.amg.exchange.registration.model.DocumentImg;
import com.amg.exchange.registration.model.EMOSCustomer;
import com.amg.exchange.registration.model.IdentityTypeMaster;
import com.amg.exchange.registration.model.ScanIdTypeMaster;
import com.amg.exchange.registration.model.SmartCardInfo;
import com.amg.exchange.registration.model.ViewExDmsApplMap;
import com.amg.exchange.registration.model.ViewOMIDTemp;
import com.amg.exchange.registration.service.ICustomerRegistrationBranchService;
import com.amg.exchange.remittance.model.AuthorizedLog;
import com.amg.exchange.remittance.model.RelationsDescription;
import com.amg.exchange.treasury.model.ArticleMaster;
import com.amg.exchange.treasury.model.ArticleMasterDesc;
import com.amg.exchange.util.AMGException;

@SuppressWarnings("serial")
@Service("customerRegistrationServiceImpl")
public class CustomerRegistrationBranchServiceImpl<T> implements ICustomerRegistrationBranchService<T>, Serializable{

	@Autowired
	ICustomerRegistrationBranchDao<T> customerRegistrationBranchDao;
	
	public ICustomerRegistrationBranchDao<T> getCustomerRegistrationBranchDao() {
		return customerRegistrationBranchDao;
	}

	public void setCustomerRegistrationBranchDao(
			ICustomerRegistrationBranchDao<T> customerRegistrationBranchDao) {
		this.customerRegistrationBranchDao = customerRegistrationBranchDao;
	}

	@Override
	@Transactional
	public void saveCustomer(Customer customer) {
		getCustomerRegistrationBranchDao().saveCustomer(customer);
		
	}

	@Override
	@Transactional
	public void saveCustomerIdProof(CustomerIdProof proof) {
		getCustomerRegistrationBranchDao().saveCustomerIdProof(proof);
	}

	@Override
	@Transactional
	public void saveImage(DocumentImg document) {
		getCustomerRegistrationBranchDao().saveImage(document);
		
	}

	@Override
	@Transactional
	public void saveContactDetails(ContactDetail contactDetail) {
	getCustomerRegistrationBranchDao().saveContactDetails(contactDetail);
		
	}

	@Override
	@Transactional
	public void saveCustomerEmploymentInfo(CustomerEmploymentInfo customerEmp) {
		
		getCustomerRegistrationBranchDao().saveCustomerEmploymentInfo(customerEmp);
	}

	@Override
	@Transactional
	public CustomerIdProof getCustomerIdProof(String idNumber) {
		
		return null;
	}

	@Override
	@Transactional
	public List<CustomerIdProof> getCustomerIdProofLst(BigDecimal idType,String idNumber){
		return getCustomerRegistrationBranchDao().getCustomerIdProofLst(idType, idNumber);
	}

	@Override
	@Transactional
	public List<CustomerIdProof> getCustomerIdProofList(BigDecimal customerId) {
		return getCustomerRegistrationBranchDao().getCustomerIdProofList(customerId);
	}

	@Override
	@Transactional
	public List<Customer> getCustomerInfo(BigDecimal customerId) {
		return getCustomerRegistrationBranchDao().getCustomerInfo(customerId);
	}

	@Override
	@Transactional
	public List<ContactDetail> getCustomerContactDetails(BigDecimal customerId) {
		return getCustomerRegistrationBranchDao().getCustomerContactDetails(customerId);
	}

	@Override
	@Transactional
	public List<CustomerEmploymentInfo> getCustomerEmploymentInfo(
			BigDecimal customerId) {
		return getCustomerRegistrationBranchDao().getCustomerEmploymentInfo(customerId);
	}

	@Override
	@Transactional
	public List<Customer> getCustomerInfoFetch(BigDecimal customerId) {
		return getCustomerRegistrationBranchDao().getCustomerInfoFetch(customerId);
	}

	@Override
	@Transactional
	public List<CustomerIdProof> getCustomerIdProofCheck(BigDecimal idType,
			String idNumber,BigDecimal countryid) {
		return getCustomerRegistrationBranchDao().getCustomerIdProofCheck(idType, idNumber,countryid);
	}

	@Override
	@Transactional
	public void saveSmartCardInfo(SmartCardInfo smartCardDetails) {
		 getCustomerRegistrationBranchDao().saveSmartCardInfo(smartCardDetails);
		
	}

	@Override
	@Transactional
	public List<CountryMaster> getNationalityAlphaCode(String alphaCode) {
		return getCustomerRegistrationBranchDao().getNationalityAlphaCode(alphaCode);
	}

	@Override
	@Transactional
	 public List<DmsMas> getSmartCardData(String idNo) {
		return getCustomerRegistrationBranchDao().getSmartCardData(idNo);
	}
	
	
	@Override
	@Transactional
	public List<Customer> getVerificationToken(BigDecimal customerId){
		return getCustomerRegistrationBranchDao().getVerificationToken(customerId);
	}
	
	
	//added by kani begin
	
		@Override
		@Transactional
		public List<CustomerIdProof> getCustomerId(BigDecimal countryid,String customerId){
			return getCustomerRegistrationBranchDao().getCustomerId(countryid,customerId);
		}
		
		
		@Override
		@Transactional
		public List<CustomerIdProof> getCivilID(BigDecimal cusRefNumber){
			return getCustomerRegistrationBranchDao().getCivilID(cusRefNumber);
		}

		@Override
		@Transactional
		public List<Customer> getIntroducerCustId(BigDecimal introducerRefNo) {
	
			return getCustomerRegistrationBranchDao().getIntroducerCustId(introducerRefNo);
		}
		
		
		
		
		@Override
		@Transactional
		public List<CustomerIdProof> getRefCustCivilID(BigDecimal refCusId) {
	
			return getCustomerRegistrationBranchDao(). getRefCustCivilID( refCusId);
		}

		@Override
		@Transactional
		public List<CountryMasterDesc> getNationalityCode(BigDecimal languageId, String countryCode) {
			return getCustomerRegistrationBranchDao().getNationalityCode(languageId, countryCode);
		}

		@Override
		@Transactional
		public void saveSmartCardDeatils(DmsMas smartCardDetails) {
		   getCustomerRegistrationBranchDao().saveSmartCardDeatils(smartCardDetails);
			
		}

		@Override
		@Transactional(rollbackFor = Exception.class)
		public void saveCustomerRegistration(HashMap<String, Object> saveMapInfo) throws Exception{
			
			getCustomerRegistrationBranchDao().saveCustomerRegistration(saveMapInfo);
		}

		@Override
		@Transactional
		public void deleteCustContactDetails(BigDecimal custConstantPK, String userName) {
			
			getCustomerRegistrationBranchDao().deleteCustContactDetails(custConstantPK,userName);
		}

		@Override
		@Transactional
		public List<CountryMaster> getCountryAlpha2Code(BigDecimal appcountryId) {
			
			return getCustomerRegistrationBranchDao().getCountryAlpha2Code(appcountryId);
		}

		@Override
		@Transactional(rollbackFor = Exception.class)
		public void saveSmartCardCustomerRegistration(HashMap<String, Object> saveMapInfo) throws Exception {
			
			getCustomerRegistrationBranchDao().saveSmartCardCustomerRegistration(saveMapInfo);
		}
		
		@Override
		@Transactional
		public void deleteEmployeeInfo(BigDecimal employeeInfoPK) {
			
			getCustomerRegistrationBranchDao().deleteEmployeeInfo(employeeInfoPK);
		}

		@Override
		@Transactional
		public List<String> getCustomerRefOrSave(BigDecimal custIDType, String custNumber, String custType) throws AMGException {
			
			return getCustomerRegistrationBranchDao().getCustomerRefOrSave(custIDType, custNumber, custType);
		}
		
		@Override
		@Transactional
		public List<CustomerChangeLog> findCustomerChangeLog(BigDecimal customerId,String verificationToken){
			return getCustomerRegistrationBranchDao().findCustomerChangeLog(customerId,verificationToken);
		}
		
		@Override
		@Transactional
		public List<CustomerInfoView> findCustomerRegistration(BigDecimal customerId){
			return getCustomerRegistrationBranchDao().findCustomerRegistration(customerId);
		}
		
		@Override
		@Transactional
		public List<CustomerContactDetailView> findCustomerContactDetails(BigDecimal customerId){
			return getCustomerRegistrationBranchDao().findCustomerContactDetails(customerId);
		}
		
		@Override
		@Transactional
		public List<CustomerEmployeeInfoView> findCustomerEmployeeInfo(BigDecimal customerId){
			return getCustomerRegistrationBranchDao().findCustomerEmployeeInfo(customerId);
		}
		
		@Override
		@Transactional
		public List<CustomerIdproofView> findCustomerIdProof(BigDecimal customerId){
			return getCustomerRegistrationBranchDao().findCustomerIdProof(customerId);
		}
		
		@Override
		@Transactional
		public List<CorpatePartnerInfoView> findCorporatePartnerInfo(BigDecimal customerId){
			return getCustomerRegistrationBranchDao().findCorporatePartnerInfo(customerId);
		}
		
		@Override
		@Transactional
		public List<CorporateCustomerInfoView> findCorporateCustomerInfo(BigDecimal customerId){
			return getCustomerRegistrationBranchDao().findCorporateCustomerInfo(customerId);
		}
		
		@Override
		@Transactional
		public void updateVerificationToken(BigDecimal customerId) {
			
			getCustomerRegistrationBranchDao().updateVerificationToken(customerId);
		}
		
		@Override
		@Transactional
		public List<IdentityTypeMaster> getOcrList(BigDecimal idTypeId) {
			return getCustomerRegistrationBranchDao().getOcrList(idTypeId);
		}	
		

		@Override
		@Transactional
		public List<CustomerIdProof> getActiveIdProofList(BigDecimal customerid) {
			return getCustomerRegistrationBranchDao().getActiveIdProofList(customerid);
		}

		@Override
		@Transactional
		public void saveCustomerEmos(BigDecimal customerId, String emosCustomer) {
			getCustomerRegistrationBranchDao().saveCustomerEmos(customerId, emosCustomer);
			
		}

		@Override
		@Transactional
		public List<String> callProcedureUpdate(BigDecimal customerId) throws AMGException {
			return getCustomerRegistrationBranchDao().callProcedureUpdate(customerId);
		}

		@Override
		@Transactional
		public List<Customer> getCustomerData(BigDecimal countryId,String mobileNo) {
			return getCustomerRegistrationBranchDao().getCustomerData(countryId,mobileNo);
		}

		@Override
		@Transactional
		public List<ArticleMasterDesc> getArtilcesThroughCode(String articleCode,BigDecimal languageId) {
			return getCustomerRegistrationBranchDao().getArtilcesThroughCode(articleCode,languageId);
		}

		@Override
		@Transactional
		public List<RelationsDescription> getRelationsDescriptionList(BigDecimal languageId) {
			return getCustomerRegistrationBranchDao().getRelationsDescriptionList(languageId);
		}

		@Override
		@Transactional
		public List<CustomerIdProof> getRegisterId(String idNumber, BigDecimal countryId, BigDecimal customerType) {
			return getCustomerRegistrationBranchDao().getRegisterId(idNumber, countryId, customerType);
		}

		@Override
		@Transactional
		public List<CustomerDependant> getCustomerDependantList(BigDecimal customerId) {
			return getCustomerRegistrationBranchDao().getCustomerDependantList(customerId);
		}

		@Override
		@Transactional
		public List<ArticleMaster> getArticleMasterList(BigDecimal articleId) {
			return getCustomerRegistrationBranchDao().getArticleMasterList(articleId);
		}

		@Override
		@Transactional
		public List<CustomerDependant> getDependantList(BigDecimal customerId,BigDecimal dependantCustomerId) {
			return getCustomerRegistrationBranchDao().getDependantList(customerId, dependantCustomerId);
		}

		@Override
		@Transactional
		public List<CustomerDependant> checkDependantList(BigDecimal customerId,BigDecimal dependantCustomerRefNo) {
			return getCustomerRegistrationBranchDao().checkDependantList(customerId, dependantCustomerRefNo);
		}

		@Override
		@Transactional
		public void updateDependant(BigDecimal customerDependantId) {
			getCustomerRegistrationBranchDao().updateDependant(customerDependantId);
		}

		@Override
		@Transactional
		public void deleteDependant(BigDecimal customerDependantId) {
			getCustomerRegistrationBranchDao().deleteDependant(customerDependantId);
			
		}

		@Override
		@Transactional
		public BigDecimal callProcedureCustReferenceNumber(BigDecimal companyCode, String documentCode, String docum_Fin_Year, String branchId) {
			
			return getCustomerRegistrationBranchDao().callProcedureCustReferenceNumber(companyCode, documentCode, docum_Fin_Year, branchId);
		}

		@Override
		@Transactional
		public List<CustomerSponsor> getCustomerSponsorList(
				BigDecimal customerId) {
			return customerRegistrationBranchDao.getCustomerSponsorList(customerId);
		}
		
		@Override
		@Transactional
		public String getCustomerIdentity(BigDecimal cusRefNumber) {
			
			return getCustomerRegistrationBranchDao().getCustomerIdentity(cusRefNumber);
		}

		@Override
		@Transactional
		public void updateCustomerArticle(BigDecimal pkCustomerId,
				BigDecimal articleId, BigDecimal incomeRangeId) {
			getCustomerRegistrationBranchDao().updateCustomerArticle(pkCustomerId, articleId, incomeRangeId);
			
			
		}

		@Override
		@Transactional
		public void updateCustomer(BigDecimal pkCustomerId, String signature) {
			getCustomerRegistrationBranchDao().updateCustomer(pkCustomerId, signature);
		}

		@Override
		@Transactional
		public List<String> getArea(String query) {
			return getCustomerRegistrationBranchDao().getArea(query);
		}

		@Override
		@Transactional
		public void updateEmployment(BigDecimal pkEmploymentId) {
			getCustomerRegistrationBranchDao().updateEmployment(pkEmploymentId);
			
		}

		@Override
		@Transactional
		public void updateSponsor(BigDecimal pkSponsorId) {
			getCustomerRegistrationBranchDao().updateSponsor(pkSponsorId);
			
		}

		@Override
		@Transactional
		public HashMap<String, BigDecimal> getCustomerArticleInfo(BigDecimal customerId) {
			return getCustomerRegistrationBranchDao().getCustomerArticleInfo(customerId);
		}

		@Override
		@Transactional
		public BigDecimal getCustomerReference(String customerIndentity) {
			return getCustomerRegistrationBranchDao().getCustomerReference(customerIndentity);
		}

		@Override
		@Transactional
		public Map<BigDecimal, String> getAllComponentComboDataForCustomer(
				 BigDecimal languageId,
				String CustomerType,String identitiyType) {
			
			return getCustomerRegistrationBranchDao().getAllComponentComboDataForCustomer(languageId, CustomerType,identitiyType);
		}

		@Override
		@Transactional
		public List<String> getAreaforCorporate(String query) {
			return getCustomerRegistrationBranchDao().getAreaforCorporate( query);
		}

		@Override
		@Transactional
		public String getStateDistrictIDForSmart(BigDecimal countryID ,String arbicDistrict) {
			return getCustomerRegistrationBranchDao().getStateDistrictIDForSmart(countryID,arbicDistrict);
			
		}

		@Override
		@Transactional
		public String checkAdressChangeForSmartCard(String civilID) {
			
			return getCustomerRegistrationBranchDao().checkAdressChangeForSmartCard(civilID);
		}

		@Override
		@Transactional
		public boolean checkDashBoardDisplay(String civilID,BigDecimal countryId) throws AMGException{
			
			return getCustomerRegistrationBranchDao().checkDashBoardDisplay(civilID,countryId);
		}

		@Override
		@Transactional
		public HashMap<String,String> checkIntroducedByActive(BigDecimal countryId,String identitity) {
			
			return getCustomerRegistrationBranchDao().checkIntroducedByActive(countryId,identitity);
		}
		
		@Override
		@Transactional
		public String fetchCustomerNameBasedonCustomerId(BigDecimal customerId){
			
			return getCustomerRegistrationBranchDao().fetchCustomerNameBasedonCustomerId(customerId);
		}

		@Override
		@Transactional
		public BigDecimal fetchNamelength(BigDecimal countryId) {
			return getCustomerRegistrationBranchDao().fetchNamelength(countryId);
		}

		@Override
		@Transactional
		public void callSaveBlobDocsDob(BigDecimal blobId, BigDecimal docFinYear)
				throws AMGException {
			getCustomerRegistrationBranchDao().callSaveBlobDocsDob(blobId, docFinYear);
			
		}

		@Override
		@Transactional
		public List<ArcmateScanMaster> fetchArcmateMasterData(String modeOfOperation, String scanType) {
			return getCustomerRegistrationBranchDao().fetchArcmateMasterData(modeOfOperation, scanType);
		}

		@Override
		@Transactional
		public List<ScanIdTypeMaster> fetchScanIdTypeMasterData(BigDecimal idTypeId) {
			return getCustomerRegistrationBranchDao().fetchScanIdTypeMasterData(idTypeId);
	
		}
		
		@Override
		@Transactional
		public BigDecimal callTogenerateBlobID(BigDecimal docFinYear)
				throws AMGException {
			return getCustomerRegistrationBranchDao().callTogenerateBlobID(docFinYear);
		}

		@Override
		@Transactional
		public List<ViewExDmsApplMap> checkImageAvailability(BigDecimal customerId,String identityInt,BigDecimal identityTypeId, Date idExpiryDate,BigDecimal docFinYear) {
			return getCustomerRegistrationBranchDao().checkImageAvailability(customerId, identityInt, identityTypeId, idExpiryDate, docFinYear);
		}

		@Override
		@Transactional
		public List<BigDecimal> viewImage(BigDecimal customerRef ,String imageId, String idNumber ,Date idExpiryDate) throws AMGException {
			return getCustomerRegistrationBranchDao().viewImage(customerRef, imageId, idNumber, idExpiryDate);
		}

		@Override
		@Transactional
		public String getImageId(BigDecimal idTypeId) {
			return getCustomerRegistrationBranchDao().getImageId(idTypeId);
		}

		@Override
		@Transactional
		public List<DmsDocBlobUpload> getListDocTemData(BigDecimal scanYear, BigDecimal docBlobId) {
			return getCustomerRegistrationBranchDao().getListDocTemData(scanYear, docBlobId);
		}

		 
		@Override
		@Transactional
		public void saveOrUpdateContactDetails(ContactDetail contactDetails) {
			getCustomerRegistrationBranchDao().saveOrUpdateContactDetails(contactDetails);
			
		}

		@Override
		@Transactional
		public List<CustomerImageVerification> getImageStatus(
				BigDecimal customerId, String idNumber) {
			
			return getCustomerRegistrationBranchDao().getImageStatus(customerId, idNumber);
		}

		@Override
		@Transactional
		public String getBlackListCustomer(BigDecimal countryId, String engName, String localName, BigDecimal customerTypeId,
				BigDecimal identityTypeId, String identityInt) throws AMGException {
			return customerRegistrationBranchDao.getBlackListCustomer(countryId,engName,localName,customerTypeId,identityTypeId,identityInt);
		}

		@Override
		@Transactional
		public void delete(BigDecimal blobseqId) {
			customerRegistrationBranchDao.delete(blobseqId);
		}

		@Override
		@Transactional
		public List<DMSApplMap> viewImage(BigDecimal customerId,
				String identityInt, BigDecimal identityTypeId, Date idExpiryDate) {
		
			return customerRegistrationBranchDao.viewImage(customerId, identityInt, identityTypeId, idExpiryDate);
		}

		@Override
		@Transactional
		public void callBlobRemote(BigDecimal blobId, BigDecimal docFinYear)
				throws AMGException {
			
			customerRegistrationBranchDao.callBlobRemote(blobId, docFinYear);
		}

		@Override
		@Transactional
		public HashMap<String, String> callScanTableInsert(
				HashMap<String, String> inputMap, Date idExpiryDate)
				throws AMGException {
		
			return customerRegistrationBranchDao.callScanTableInsert(inputMap, idExpiryDate);
		}

		@Override
		@Transactional
		public HashMap<String, String> callDeleteUploadImage(BigDecimal blobId)
				throws AMGException {
			return customerRegistrationBranchDao.callDeleteUploadImage(blobId);
		}

		@Override
		@Transactional
		public HashMap<String, String> checkCustomerContactDetailsExist(
				BigDecimal countryId, BigDecimal stateId,
				BigDecimal districtId, BigDecimal cityId) {
			
			return customerRegistrationBranchDao.checkCustomerContactDetailsExist(countryId, stateId, districtId, cityId);
		}

		@Override
		@Transactional
		public List<Customer> checkDuplicateFSCustomer(String idNumber) {
			return customerRegistrationBranchDao.checkDuplicateFSCustomer(idNumber);
		}

		@Override
		@Transactional
		public List<EMOSCustomer> checkDuplicateEMOSCustomer(String idNumber,String componentCode) {
			 return customerRegistrationBranchDao.checkDuplicateEMOSCustomer(idNumber,componentCode);
		}

		@Override
		@Transactional
		public String getComponentCode(BigDecimal identityTpeId) {
			 return customerRegistrationBranchDao.getComponentCode(identityTpeId);
		}

		@Override
		@Transactional
		public List<ContactDetail> getCustomerContactDetailsForDuplicateCheck(BigDecimal pkCustomerId) {
			return customerRegistrationBranchDao.getCustomerContactDetailsForDuplicateCheck(pkCustomerId);
		}

		@Override
		@Transactional
		public String callProcedureUpdateCustomerFromIdProof(BigDecimal pkCustomerId)throws AMGException {
			return customerRegistrationBranchDao.callProcedureUpdateCustomerFromIdProof(pkCustomerId);
		}
		
		@Override
		@Transactional
		public boolean getOtpdetails(BigDecimal customerPk)	throws AMGException {
			return customerRegistrationBranchDao.getOtpdetails(customerPk);
		}

		@Override
		@Transactional
		public String verifyOtpNo(BigDecimal optNo,BigDecimal customerPkId,  String userName) throws AMGException {
			return customerRegistrationBranchDao.verifyOtpNo(optNo,customerPkId,userName);
		}

		@Override
		@Transactional
		public int updateOTPRetry(BigDecimal optNo,BigDecimal customerPkId, BigDecimal mobilePin) throws AMGException {
			return customerRegistrationBranchDao.updateOTPRetry(optNo, customerPkId, mobilePin);
		}

		@Override
		@Transactional
		public int getOTPRetryAttempts(BigDecimal optNo,BigDecimal customerPkId) throws AMGException {
			return customerRegistrationBranchDao.getOTPRetryAttempts(optNo, customerPkId);
		}

		@Override
		@Transactional
		public int clearOldOTPRetry(BigDecimal customerPkId)throws AMGException {
			return customerRegistrationBranchDao.clearOldOTPRetry(customerPkId);
		}

		@Override
		@Transactional
		public HashMap<String, String> callOTPSendProcedure(HashMap<String, String> inputValues) throws AMGException {
			return customerRegistrationBranchDao.callOTPSendProcedure(inputValues);
		}

		@Override
		@Transactional
		public String getMobileNoBasedOnCustomerId(BigDecimal customerPkId) throws AMGException {
			return customerRegistrationBranchDao.getMobileNoBasedOnCustomerId(customerPkId);
		}

		@Override
		@Transactional
		public boolean checkOtpVerified(BigDecimal customerPk) throws AMGException {
			return customerRegistrationBranchDao.checkOtpVerified(customerPk);
		}
		
		@Override
		@Transactional
		public List<String> callProcedureToGenerateKioskPin() throws AMGException {
			return customerRegistrationBranchDao.callProcedureToGenerateKioskPin();
		}

		@Override
		@Transactional
		public void saveAuthorizedLogForOTP(AuthorizedLog authorizedLog) {
			customerRegistrationBranchDao.saveAuthorizedLogForOTP(authorizedLog);
		}

		@Override
		@Transactional
		public HashMap<String, Object> checkOTPVerify(BigDecimal customerPk) throws AMGException {
			return customerRegistrationBranchDao.checkOTPVerify(customerPk);
		}
		
		
		@Override
		@Transactional
		public List<ViewOMIDTemp> getOMANSamrCardDetails(String userName)
				throws AMGException {
			
			return customerRegistrationBranchDao.getOMANSamrCardDetails(userName);
		}
		
		@Override
		@Transactional
		public int deleteRecordBeforeSmarCardReader(String userNmae) {
			
			return customerRegistrationBranchDao.deleteRecordBeforeSmarCardReader(userNmae);
		}
		
		@Override
		@Transactional
		public void updateCustomerSignOTP(BigDecimal pkCustomerId,
				String signature, BigDecimal otpNo, String authorizedBy, String authorizedRemarks) {
			customerRegistrationBranchDao.updateCustomerSignOTP(pkCustomerId, signature, otpNo, authorizedBy, authorizedRemarks);
		}

		@Override
		@Transactional
		public List<CustomerMobileLogModel> fetchOTPDetailByCustomer(BigDecimal customerId) {
			return customerRegistrationBranchDao.fetchOTPDetailByCustomer(customerId);
		}
		@Override
		@Transactional
		public void updateCustomerEmailVerifiedOn(BigDecimal customerId) {
			
			customerRegistrationBranchDao.updateCustomerEmailVerifiedOn(customerId);
		}

		@Override
		@Transactional
		public void updateCustomerEmailAndVerifiedOn(BigDecimal customerId,
				String customerEmailId) {
			customerRegistrationBranchDao.updateCustomerEmailAndVerifiedOn(customerId, customerEmailId);
			
		}

		@Override
		@Transactional
		public List<String> getCustomerEmailDetails(BigDecimal customerId) {
			
			return customerRegistrationBranchDao.getCustomerEmailDetails(customerId);
		}
}
