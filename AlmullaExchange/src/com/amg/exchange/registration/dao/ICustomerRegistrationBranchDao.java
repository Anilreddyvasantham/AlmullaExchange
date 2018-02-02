package com.amg.exchange.registration.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
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
import com.amg.exchange.remittance.model.AuthorizedLog;
import com.amg.exchange.remittance.model.RelationsDescription;
import com.amg.exchange.treasury.model.ArticleMaster;
import com.amg.exchange.treasury.model.ArticleMasterDesc;
import com.amg.exchange.util.AMGException;

public interface ICustomerRegistrationBranchDao<T> {

	public void saveCustomer(Customer customer);
	public void saveCustomerIdProof(CustomerIdProof proof);
	public void saveImage(DocumentImg document);
	public void saveContactDetails(ContactDetail contactDetail);
	public void saveCustomerEmploymentInfo(CustomerEmploymentInfo customerEmp);
	public CustomerIdProof getCustomerIdProof(String idNumber);
	public List<CustomerIdProof> getCustomerIdProofLst(BigDecimal idType, String idNumber);

	public List<CustomerIdProof> getCustomerIdProofList(BigDecimal customerId);
	public List<Customer> getCustomerInfo(BigDecimal customerId);
	public List<ContactDetail> getCustomerContactDetails(BigDecimal customerId);
	public List<CustomerEmploymentInfo> getCustomerEmploymentInfo(BigDecimal customerId);
	public List<Customer> getCustomerInfoFetch(BigDecimal customerId);

	public List<CustomerIdProof> getCustomerIdProofCheck(BigDecimal idType, String idNumber,BigDecimal countryid);

	public void saveSmartCardInfo(SmartCardInfo smartCardDetails);
	public List<CountryMaster> getNationalityAlphaCode(String alphaCode);
	public List<DmsMas> getSmartCardData(String idNo);

	public List<Customer> getVerificationToken(BigDecimal customerId);

	//Addewdd by kani begin
	public List<CustomerIdProof>  getCustomerId(BigDecimal countryid ,String idNumber);

	public List<CustomerIdProof>  getCivilID(BigDecimal cusRefNumber);

	public List<Customer>  getIntroducerCustId(BigDecimal introducerRefNo);
	
	//Get Civil ID Data
	public Map<BigDecimal, String> getAllComponentComboData(BigDecimal componentConfId, BigDecimal languageId,String CustomerType);


	public List<CustomerIdProof> getRefCustCivilID(BigDecimal refCusId);

	public List<CountryMasterDesc> getNationalityCode(BigDecimal languageId, String countryCode);

	public void saveSmartCardDeatils(DmsMas smartCardDetails);

	//save All Customer Info , Customer ID proof , Customer Contact Details and Customer Employee Details
	public void saveCustomerRegistration(HashMap<String , Object> saveMapInfo) throws Exception;
	
	//save All Customer Info , Customer ID proof , Customer Contact Details, Smart Card DMSMas and Customer Employee Details
	public void saveSmartCardCustomerRegistration(HashMap<String , Object> saveMapInfo) throws Exception;

	//delete Customer Contact Details 
	public void deleteCustContactDetails(BigDecimal custConstantPK , String userName);
	
	//get Alpha Code 2 for session Country Id
	public List<CountryMaster> getCountryAlpha2Code(BigDecimal appcountryId);
	
	//to remove record from Customer Employee Info
	public void deleteEmployeeInfo(BigDecimal employeeInfoPK);
	
	//to get customer reference number and Y or N to save the Customer
	public List<String> getCustomerRefOrSave(BigDecimal custIDType , String custNumber ,String custType) throws AMGException;
	
	public List<CustomerChangeLog> findCustomerChangeLog(BigDecimal customerId,String verificationToken);
	public List<CustomerInfoView> findCustomerRegistration(BigDecimal customerId);
	
	public List<CustomerContactDetailView> findCustomerContactDetails(BigDecimal customerId);
	public List<CustomerEmployeeInfoView> findCustomerEmployeeInfo(BigDecimal customerId);
	public List<CustomerIdproofView> findCustomerIdProof(BigDecimal customerId);
	public List<CorpatePartnerInfoView> findCorporatePartnerInfo(BigDecimal customerId);
	public List<CorporateCustomerInfoView> findCorporateCustomerInfo(BigDecimal customerId);
	

	//Update customer verification token
	public void updateVerificationToken(BigDecimal customerId);
	//NON OCR
	public List<IdentityTypeMaster> getOcrList(BigDecimal idTypeId);
	
	public List<CustomerIdProof> getActiveIdProofList(BigDecimal customerId);
	
	public void saveCustomerEmos(BigDecimal customerId, String emosCustomer);
	public List<String> callProcedureUpdate(BigDecimal customerId) throws AMGException;
	
	public List<Customer> getCustomerData(BigDecimal countryId,String mobileNo);
	public List<ArticleMasterDesc> getArtilcesThroughCode(String articleCode,BigDecimal languageId);
	public List<RelationsDescription> getRelationsDescriptionList(BigDecimal languageId);
	public List<CustomerIdProof> getRegisterId(String idNumber, BigDecimal countryId, BigDecimal customerType);
	public List<CustomerDependant> getCustomerDependantList(BigDecimal customerId);
	public List<ArticleMaster> getArticleMasterList(BigDecimal articleId);
	public List<CustomerDependant> getDependantList(BigDecimal customerId,BigDecimal dependantCustomerId);
	public List<CustomerDependant> checkDependantList(BigDecimal customerId,BigDecimal dependantCustomerRefNo);
	public void updateDependant(BigDecimal customerDependantId);
	public void deleteDependant(BigDecimal customerDependantId);
	
	public BigDecimal callProcedureCustReferenceNumber(BigDecimal companyCode, String documentCode, String docum_Fin_Year , String branchId);
	
	public List<CustomerSponsor> getCustomerSponsorList(BigDecimal customerId);
	
	//Get Customer Identity 
	public String  getCustomerIdentity(BigDecimal cusRefNumber);
	
    public void updateCustomerArticle(BigDecimal pkCustomerId,BigDecimal articleId, BigDecimal incomeRangeId);
	
	public void updateCustomer(BigDecimal pkCustomerId, String signature);
	public List<String> getArea(String query);
	public void updateEmployment(BigDecimal pkEmploymentId);
	public void updateSponsor(BigDecimal pkSponsorId);
	public HashMap<String, BigDecimal> getCustomerArticleInfo(BigDecimal customerId);
	
	//Get Customer reference number
	public BigDecimal getCustomerReference(String customerIndentity);
	
	//Get Civil ID Data
	public Map<BigDecimal, String> getAllComponentComboDataForCustomer(BigDecimal languageId,String CustomerType,String identitiyType);
	public List<String> getAreaforCorporate(String query);
	
	//Getting State ID and District ID for Smart card
	public String getStateDistrictIDForSmart(BigDecimal countryID ,String arbicDistrict);
	
	//Check address changed for smart  card
	public String checkAdressChangeForSmartCard(String civilID);
	
	//Check dash board display based on civil id
	public boolean checkDashBoardDisplay(String civilID,BigDecimal countryId) throws AMGException;
	
	//Check Customer introduced by is active or not
	public HashMap<String,String> checkIntroducedByActive(BigDecimal countryId,String identitity);
	
	public String fetchCustomerNameBasedonCustomerId(BigDecimal customerId);
	
	//added by nazish on 10-Oct-2015 Check country wise name length
	public BigDecimal fetchNamelength(BigDecimal countryId);
	
	//Transfer Images For DB SCAN
	public void callSaveBlobDocsDob(BigDecimal blobId,BigDecimal docFinYear) throws AMGException;
	
	//fetch Arcmate Constant Data
	public List<ArcmateScanMaster> fetchArcmateMasterData(String modeOfOperation, String scanType);
	public List<ScanIdTypeMaster> fetchScanIdTypeMasterData(BigDecimal idTypeId);
	
	//Call procedure for getting BLOB ID  
	public BigDecimal callTogenerateBlobID(BigDecimal docFinYear) throws AMGException;
	
	//Call view  for checking Image scanned or not
	public List<ViewExDmsApplMap> checkImageAvailability(BigDecimal customerId,String identityInt,BigDecimal identityTypeId, Date idExpiryDate,BigDecimal docFinYear);
		
		//Call procedure for view Image  
	public List<BigDecimal> viewImage(BigDecimal customerRef ,String imageId, String idNumber ,Date idExpiryDate) throws AMGException;
	
	//Call view for view ImageId  
	public String getImageId(BigDecimal idTypeId);
	
	//Call table  for getting image
	public List<DmsDocBlobUpload> getListDocTemData(BigDecimal scanYear,BigDecimal docBlobId);
 
	public void saveOrUpdateContactDetails(ContactDetail contactDetails);
	
	public List<CustomerImageVerification> getImageStatus(BigDecimal customerId, String idNumber);
	
	/** Added by Rabil on 13/04/2016 for black list customer **/
	public String getBlackListCustomer(BigDecimal countryId,String engName,String localName ,BigDecimal customerTypeId,BigDecimal identityTypeId,String identityInt) throws AMGException;
	public void delete(BigDecimal blobseqId);
	public List<DMSApplMap> viewImage(BigDecimal customerId,String identityInt,BigDecimal identityTypeId, Date idExpiryDate);
	public void callBlobRemote(BigDecimal blobId,BigDecimal docFinYear) throws AMGException;
	public HashMap<String, String> callScanTableInsert(HashMap<String, String> inputMap,Date idExpiryDate) throws AMGException;
	public HashMap<String, String> callDeleteUploadImage(BigDecimal blobId) throws AMGException;
	public HashMap<String,String> checkCustomerContactDetailsExist(BigDecimal countryId,BigDecimal stateId,BigDecimal districtId,BigDecimal cityId);
	public List<Customer> checkDuplicateFSCustomer(String idNumber);
	public List<EMOSCustomer> checkDuplicateEMOSCustomer(String idNumber, String componentCode);
	public String getComponentCode(BigDecimal identityTpeId);
	public List<ContactDetail> getCustomerContactDetailsForDuplicateCheck(BigDecimal pkCustomerId);
	
	//call procedure for updating customer with id proof details
	public String callProcedureUpdateCustomerFromIdProof(BigDecimal pkCustomerId)throws AMGException;
	
	public boolean getOtpdetails(BigDecimal customerPk)throws AMGException;
	
	public String verifyOtpNo(BigDecimal optNo,BigDecimal customerPkId,  String userName) throws AMGException;
	
	public int updateOTPRetry(BigDecimal optNo,BigDecimal customerPkId, BigDecimal mobilePin)throws AMGException;
	
	public int getOTPRetryAttempts(BigDecimal optNo,BigDecimal customerPkId)throws AMGException;
	
	public int clearOldOTPRetry(BigDecimal customerPkId)throws AMGException;
	
	public HashMap<String ,String> callOTPSendProcedure(HashMap<String ,String> inputValues)throws AMGException;
	
	public String getMobileNoBasedOnCustomerId(BigDecimal customerPkId)throws AMGException;
	
	public boolean checkOtpVerified(BigDecimal customerPk) throws AMGException;
	
	public List<String> callProcedureToGenerateKioskPin() throws AMGException;
	
	public void saveAuthorizedLogForOTP(AuthorizedLog authorizedLog);
	
	 //To get the OMAN smart card details from VW_EX_ID_TEMP	
	public List<ViewOMIDTemp> getOMANSamrCardDetails(String userName) throws AMGException;
	
	//Delete record from EX_ID_TEMP	table for OMAN SMAR CARD 
	public int deleteRecordBeforeSmarCardReader(String userNmae);
	
	public HashMap<String, Object> checkOTPVerify(BigDecimal customerPk) throws AMGException;
	
	public void updateCustomerSignOTP(BigDecimal pkCustomerId, String signature, BigDecimal otpNo, String authorizedBy, String authorizedRemarks);
	
	public List<CustomerMobileLogModel> fetchOTPDetailByCustomer(BigDecimal customerId);
	public void updateCustomerEmailVerifiedOn(BigDecimal customerId);
	public void updateCustomerEmailAndVerifiedOn(BigDecimal customerId,String customerEmailId);
	public List<String> getCustomerEmailDetails(BigDecimal customerId);
}
