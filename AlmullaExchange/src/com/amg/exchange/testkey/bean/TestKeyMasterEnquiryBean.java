package com.amg.exchange.testkey.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.BankAccountTypeDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.testkey.model.TestKeyMaster;
import com.amg.exchange.testkey.model.ViewTestKeyParameter;
import com.amg.exchange.testkey.service.ITestKeyForParameterForBankService;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;
@Component("testKeyMasterEnquiryBean")
@Scope("session")
public class TestKeyMasterEnquiryBean<T> implements Serializable {

	  /**
	   * 
	   */
	  private static final Logger log = Logger.getLogger(TestKeyMasterEnquiryBean.class);
	  private static final long serialVersionUID = 1L;
	  private BigDecimal testKeyParameterPk;
	  private BigDecimal applicationCountryId;
	  private BigDecimal bankId;
	  private String bankCode;
	  private String bankDescription;
	  private String accountNo;
	  private String accountNumber;
	  private String branchName;
	  private String accountDescription;
	  private BigDecimal currencyId;
	  private String currencyName;
	  private BigDecimal branchCode;
	  private String sendReceiveIndicator;
	  private BigDecimal calculationOrderNo;
	  private String calculationType;
	  private String primaryTestKey;
	  private String secondaryTestKey;
	  private String recordStatus;
	  private String serialIndicator;
	  private String endOfSerial;
	  private String createdBy;
	  private Date createdDate;
	  private String modifiedBy;
	  private Date modifiedDate;
	  private String isActive;
	  private String approvedBy;
	  private Date approvedDate;
	  private String remarks;
	  private BigDecimal bankAccountDescId;
	  private String seconaryIndicator;
	  private Boolean renderEditButton;
	  private String dynamicLabelForActivateDeactivate;
	  private String primaryTestKeyName;
	  private String countryBranchId;
	  private BigDecimal bankBranchCode;
	  private String secondaryTestKeyParameterName;
	  
	  //boolean Variables
	  private Boolean booRenderDataTable=false;
	  private Boolean booSaveOrExit=false;
	  
	  private TestkeyParameterForBankDataTable testkeyParameterDataObj=null;
	  private String errorMessage;
	  
	  private List<TestkeyParameterForBankDataTable> lstTestkeyParameterForBankDt=new CopyOnWriteArrayList<TestkeyParameterForBankDataTable>();
	  private List<TestkeyParameterForBankDataTable> lstTestkeyParameterForNewBankDt=new CopyOnWriteArrayList<TestkeyParameterForBankDataTable>();
	  private List<BankApplicability> bankMasterList = new ArrayList<BankApplicability>();
	  private List<BankAccountDetails> lstBankAccountDetails=new ArrayList<BankAccountDetails>();
	  private List<BankAccountDetails> bankAccountDtlist=new ArrayList<BankAccountDetails>();
	  private List<BankBranch> bankbranchList=new ArrayList<BankBranch>();
	  private List<ViewTestKeyParameter> lstTestKeyParameters=new ArrayList<ViewTestKeyParameter>();
	  
	  private Map<BigDecimal, String> bankMasterListMap = new HashMap<BigDecimal, String>();
	  private Map<BigDecimal, String> bankMasterListMapCode = new HashMap<BigDecimal, String>();
	  private Map<BigDecimal, String> lstBankAccountDetailsCurrencyMap = new HashMap<BigDecimal, String>();
	  private Map<BigDecimal, String> lstBankAccountDetailsCurrencyCode = new HashMap<BigDecimal, String>();
	  private Map<BigDecimal, String> lstBankAccountDetailsAccountNumber = new HashMap<BigDecimal, String>();
	  private Map<BigDecimal, String> lstBankAccountTypeDescMap = new HashMap<BigDecimal, String>();
	  private Map<BigDecimal, String> bankbranchListMap = new HashMap<BigDecimal, String>();
	  private Map<BigDecimal, String> bankbranchListMapCode = new HashMap<BigDecimal, String>();
	  SessionStateManage sessionStateManage = new SessionStateManage();
	  
	  //AutoWired Started
	  @Autowired
	  ITestKeyForParameterForBankService testKeyForParameterForBankService;
	  
	  @Autowired
	  IGeneralService<T> generalService;
	  
	  
	  public BigDecimal getTestKeyParameterPk() {
	  	  return testKeyParameterPk;
	  }
	  public void setTestKeyParameterPk(BigDecimal testKeyParameterPk) {
	  	  this.testKeyParameterPk = testKeyParameterPk;
	  }
	  public BigDecimal getApplicationCountryId() {
	  	  return applicationCountryId;
	  }
	  public void setApplicationCountryId(BigDecimal applicationCountryId) {
	  	  this.applicationCountryId = applicationCountryId;
	  }
	  public BigDecimal getBankId() {
	  	  return bankId;
	  }
	  public void setBankId(BigDecimal bankId) {
	  	  this.bankId = bankId;
	  }
	  public String getBankCode() {
	  	  return bankCode;
	  }
	  public void setBankCode(String bankCode) {
	  	  this.bankCode = bankCode;
	  }
	  public String getBankDescription() {
	  	  return bankDescription;
	  }
	  public String getAccountNo() {
	  	  return accountNo;
	  }
	  public void setAccountNo(String accountNo) {
	  	  this.accountNo = accountNo;
	  }
	  public String getAccountDescription() {
	  	  return accountDescription;
	  }
	  public void setAccountDescription(String accountDescription) {
	  	  this.accountDescription = accountDescription;
	  }
	  public void setBankDescription(String bankDescription) {
	  	  this.bankDescription = bankDescription;
	  }
	  public BigDecimal getCurrencyId() {
	  	  return currencyId;
	  }
	  public void setCurrencyId(BigDecimal currencyId) {
	  	  this.currencyId = currencyId;
	  }
	  public String getCurrencyName() {
	  	  return currencyName;
	  }
	  public void setCurrencyName(String currencyName) {
	  	  this.currencyName = currencyName;
	  }
	  public BigDecimal getBranchCode() {
	  	  return branchCode;
	  }
	  public void setBranchCode(BigDecimal branchCode) {
	  	  this.branchCode = branchCode;
	  }
	  public String getSendReceiveIndicator() {
	  	  return sendReceiveIndicator;
	  }
	  public void setSendReceiveIndicator(String sendReceiveIndicator) {
	  	  this.sendReceiveIndicator = sendReceiveIndicator;
	  }
	  public BigDecimal getCalculationOrderNo() {
	  	  return calculationOrderNo;
	  }
	  public void setCalculationOrderNo(BigDecimal calculationOrderNo) {
	  	  this.calculationOrderNo = calculationOrderNo;
	  }
	  public String getCalculationType() {
	  	  return calculationType;
	  }
	  public void setCalculationType(String calculationType) {
	  	  this.calculationType = calculationType;
	  }
	  public String getPrimaryTestKey() {
	  	  return primaryTestKey;
	  }
	  public void setPrimaryTestKey(String primaryTestKey) {
	  	  this.primaryTestKey = primaryTestKey;
	  }
	  public String getSecondaryTestKey() {
	  	  return secondaryTestKey;
	  }
	  public void setSecondaryTestKey(String secondaryTestKey) {
	  	  this.secondaryTestKey = secondaryTestKey;
	  }
	  public String getRecordStatus() {
	  	  return recordStatus;
	  }
	  public void setRecordStatus(String recordStatus) {
	  	  this.recordStatus = recordStatus;
	  }
	  public String getSerialIndicator() {
	  	  return serialIndicator;
	  }
	  public void setSerialIndicator(String serialIndicator) {
	  	  this.serialIndicator = serialIndicator;
	  }
	  public String getEndOfSerial() {
	  	  return endOfSerial;
	  }
	  public String getAccountNumber() {
	  	  return accountNumber;
	  }
	  public void setAccountNumber(String accountNumber) {
	  	  this.accountNumber = accountNumber;
	  }
	  public String getBranchName() {
	  	  return branchName;
	  }
	  public void setBranchName(String branchName) {
	  	  this.branchName = branchName;
	  }
	  public void setEndOfSerial(String endOfSerial) {
	  	  this.endOfSerial = endOfSerial;
	  }
	  public String getCreatedBy() {
	  	  return createdBy;
	  }
	  public String getCountryBranchId() {
	  	  return countryBranchId;
	  }
	  public void setCountryBranchId(String countryBranchId) {
	  	  this.countryBranchId = countryBranchId;
	  }
	  public void setCreatedBy(String createdBy) {
	  	  this.createdBy = createdBy;
	  }
	  public Date getCreatedDate() {
	  	  return createdDate;
	  }
	  public void setCreatedDate(Date createdDate) {
	  	  this.createdDate = createdDate;
	  }
	  public String getModifiedBy() {
	  	  return modifiedBy;
	  }
	  public void setModifiedBy(String modifiedBy) {
	  	  this.modifiedBy = modifiedBy;
	  }
	  public Date getModifiedDate() {
	  	  return modifiedDate;
	  }
	  public void setModifiedDate(Date modifiedDate) {
	  	  this.modifiedDate = modifiedDate;
	  }
	  public String getSeconaryIndicator() {
	  	  return seconaryIndicator;
	  }
	  public void setSeconaryIndicator(String seconaryIndicator) {
	  	  this.seconaryIndicator = seconaryIndicator;
	  }
	  public Boolean getRenderEditButton() {
	  	  return renderEditButton;
	  }
	  public void setRenderEditButton(Boolean renderEditButton) {
	  	  this.renderEditButton = renderEditButton;
	  }
	  public String getDynamicLabelForActivateDeactivate() {
	  	  return dynamicLabelForActivateDeactivate;
	  }
	  public void setDynamicLabelForActivateDeactivate(String dynamicLabelForActivateDeactivate) {
	  	  this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	  }
	  public Boolean getBooRenderDataTable() {
	  	  return booRenderDataTable;
	  }
	  public void setBooRenderDataTable(Boolean booRenderDataTable) {
	  	  this.booRenderDataTable = booRenderDataTable;
	  }
	  public Boolean getBooSaveOrExit() {
	  	  return booSaveOrExit;
	  }
	  public void setBooSaveOrExit(Boolean booSaveOrExit) {
	  	  this.booSaveOrExit = booSaveOrExit;
	  }
	  public List<TestkeyParameterForBankDataTable> getLstTestkeyParameterForBankDt() {
	  	  return lstTestkeyParameterForBankDt;
	  }
	  public void setLstTestkeyParameterForBankDt(List<TestkeyParameterForBankDataTable> lstTestkeyParameterForBankDt) {
	  	  this.lstTestkeyParameterForBankDt = lstTestkeyParameterForBankDt;
	  }
	  public List<BankApplicability> getBankMasterList() {
	  	  return bankMasterList;
	  }
	  public void setBankMasterList(List<BankApplicability> bankMasterList) {
	  	  this.bankMasterList = bankMasterList;
	  }
	  public List<BankAccountDetails> getLstBankAccountDetails() {
	  	  return lstBankAccountDetails;
	  }
	  public void setLstBankAccountDetails(List<BankAccountDetails> lstBankAccountDetails) {
	  	  this.lstBankAccountDetails = lstBankAccountDetails;
	  }
	  public List<BankBranch> getBankbranchList() {
	  	  return bankbranchList;
	  }
	  public void setBankbranchList(List<BankBranch> bankbranchList) {
	  	  this.bankbranchList = bankbranchList;
	  }
	  public Map<BigDecimal, String> getBankMasterListMap() {
	  	  return bankMasterListMap;
	  }
	  public void setBankMasterListMap(Map<BigDecimal, String> bankMasterListMap) {
	  	  this.bankMasterListMap = bankMasterListMap;
	  }
	  public Map<BigDecimal, String> getLstBankAccountDetailsCurrencyMap() {
	  	  return lstBankAccountDetailsCurrencyMap;
	  }
	  public void setLstBankAccountDetailsCurrencyMap(Map<BigDecimal, String> lstBankAccountDetailsCurrencyMap) {
	  	  this.lstBankAccountDetailsCurrencyMap = lstBankAccountDetailsCurrencyMap;
	  }
	  public Map<BigDecimal, String> getLstBankAccountTypeDescMap() {
	  	  return lstBankAccountTypeDescMap;
	  }
	  public void setLstBankAccountTypeDescMap(Map<BigDecimal, String> lstBankAccountTypeDescMap) {
	  	  this.lstBankAccountTypeDescMap = lstBankAccountTypeDescMap;
	  }
	  public Map<BigDecimal, String> getBankbranchListMap() {
	  	  return bankbranchListMap;
	  }
	  public void setBankbranchListMap(Map<BigDecimal, String> bankbranchListMap) {
	  	  this.bankbranchListMap = bankbranchListMap;
	  }
	  public List<BankAccountDetails> getBankAccountDtlist() {
	  	  return bankAccountDtlist;
	  }
	  public void setBankAccountDtlist(List<BankAccountDetails> bankAccountDtlist) {
	  	  this.bankAccountDtlist = bankAccountDtlist;
	  }
	  public List<ViewTestKeyParameter> getLstTestKeyParameters() {
	  	  return lstTestKeyParameters;
	  }
	  public void setLstTestKeyParameters(List<ViewTestKeyParameter> lstTestKeyParameters) {
	  	  this.lstTestKeyParameters = lstTestKeyParameters;
	  }
	  public BigDecimal getBankAccountDescId() {
		  	  return bankAccountDescId;
	  }
	  public void setBankAccountDescId(BigDecimal bankAccountDescId) {
		  	  this.bankAccountDescId = bankAccountDescId;
	  }
	  public String getIsActive() {
	  	  return isActive;
	  }
	  public void setIsActive(String isActive) {
	  	  this.isActive = isActive;
	  }
	  public String getApprovedBy() {
	  	  return approvedBy;
	  }
	  public void setApprovedBy(String approvedBy) {
	  	  this.approvedBy = approvedBy;
	  }
	  public Date getApprovedDate() {
	  	  return approvedDate;
	  }
	  public void setApprovedDate(Date approvedDate) {
	  	  this.approvedDate = approvedDate;
	  }
	  public TestkeyParameterForBankDataTable getTestkeyParameterDataObj() {
	  	  return testkeyParameterDataObj;
	  }
	  public void setTestkeyParameterDataObj(TestkeyParameterForBankDataTable testkeyParameterDataObj) {
	  	  this.testkeyParameterDataObj = testkeyParameterDataObj;
	  }
	  public List<TestkeyParameterForBankDataTable> getLstTestkeyParameterForNewBankDt() {
	  	  return lstTestkeyParameterForNewBankDt;
	  }
	  public void setLstTestkeyParameterForNewBankDt(List<TestkeyParameterForBankDataTable> lstTestkeyParameterForNewBankDt) {
	  	  this.lstTestkeyParameterForNewBankDt = lstTestkeyParameterForNewBankDt;
	  }
	  public String getPrimaryTestKeyName() {
	  	  return primaryTestKeyName;
	  }
	  public void setPrimaryTestKeyName(String primaryTestKeyName) {
	  	  this.primaryTestKeyName = primaryTestKeyName;
	  }
	  public String getRemarks() {
	  	  return remarks;
	  }
	  public void setRemarks(String remarks) {
	  	  this.remarks = remarks;
	  }
	  public Map<BigDecimal, String> getBankMasterListMapCode() {
	  	  return bankMasterListMapCode;
	  }
	  public void setBankMasterListMapCode(Map<BigDecimal, String> bankMasterListMapCode) {
	  	  this.bankMasterListMapCode = bankMasterListMapCode;
	  }
	  public Map<BigDecimal, String> getLstBankAccountDetailsCurrencyCode() {
	  	  return lstBankAccountDetailsCurrencyCode;
	  }
	  public void setLstBankAccountDetailsCurrencyCode(Map<BigDecimal, String> lstBankAccountDetailsCurrencyCode) {
	  	  this.lstBankAccountDetailsCurrencyCode = lstBankAccountDetailsCurrencyCode;
	  }
	  
	  public Map<BigDecimal, String> getLstBankAccountDetailsAccountNumber() {
	  	  return lstBankAccountDetailsAccountNumber;
	  }
	  public void setLstBankAccountDetailsAccountNumber(Map<BigDecimal, String> lstBankAccountDetailsAccountNumber) {
	  	  this.lstBankAccountDetailsAccountNumber = lstBankAccountDetailsAccountNumber;
	  }
	  public Map<BigDecimal, String> getBankbranchListMapCode() {
	  	  return bankbranchListMapCode;
	  }
	  public void setBankbranchListMapCode(Map<BigDecimal, String> bankbranchListMapCode) {
	  	  this.bankbranchListMapCode = bankbranchListMapCode;
	  }
	  public BigDecimal getBankBranchCode() {
	  	  return bankBranchCode;
	  }
	  public void setBankBranchCode(BigDecimal bankBranchCode) {
	  	  this.bankBranchCode = bankBranchCode;
	  }
	  public String getSecondaryTestKeyParameterName() {
	  	  return secondaryTestKeyParameterName;
	  }
	  public void setSecondaryTestKeyParameterName(String secondaryTestKeyParameterName) {
	  	  this.secondaryTestKeyParameterName = secondaryTestKeyParameterName;
	  }
	  
	  public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	//Page Navigetion
	  public void parameterForBankEnquiryPageNavigation(){
		    setBooRenderDataTable(false);
		    setBooSaveOrExit(false);
		    clearAllFields();
		    lstTestkeyParameterForBankDt.clear();
		    lstTestkeyParameterForNewBankDt.clear();
		    //form loading to populate CorespondingBank
		    try {
		    popUpCorespondingBank();
		    //try {
		    loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "TestKeyMasterEnquiry.xhtml");
			      FacesContext.getCurrentInstance().getExternalContext().redirect("../testKey/TestKeyMasterEnquiry.xhtml");      
		   /* } catch (Exception e) {
			     e.printStackTrace();
		    }*/
		    }catch(NullPointerException ne){
		    	log.info("Method Name::parameterForBankEnquiryPageNavigation"+ne.getMessage());
				  setErrorMessage("Method Name::parameterForBankEnquiryPageNavigation"); 
				    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				    return; 
			} catch (Exception exception) {
				log.info("Method Name::parameterForBankEnquiryPageNavigation"+exception.getMessage());
				setErrorMessage(exception.getMessage());
				RequestContext.getCurrentInstance().execute("exception.show();");
				return;
			}
	  }
	  
	  //form loading to populate CorespondingBank
	    public void popUpCorespondingBank(){
		      bankMasterListMap.clear();
		      bankMasterListMapCode.clear();
		  bankMasterList=testKeyForParameterForBankService.toFetchCorpondingAllBank(sessionStateManage.getCountryId());
		  for (BankApplicability bankApplicability : bankMasterList) {
			    bankMasterListMap.put(bankApplicability.getBankMaster().getBankId(), bankApplicability.getBankMaster().getBankFullName());
			    bankMasterListMapCode.put(bankApplicability.getBankMaster().getBankId(), bankApplicability.getBankMaster().getBankCode());
	  }
	    }
	  //based on bank to populate Account no
	    public void populateAccountNumbers(){
	    	try{
		      setBankDescription(bankMasterListMap.get(getBankId()));
		     // lstBankAccountDetailsAccountMap.clear();
		      lstBankAccountDetails=testKeyForParameterForBankService.toFetchAccountNumberBasedOnBank(getBankId()); 
		      /*for (BankAccountDetails bankAccountDetails : lstBankAccountDetails) {
				lstBankAccountDetailsAccountMap.put(bankAccountDetails.getBankAcctDetId(), bankAccountDetails.getBankAcctNo());
				
		    }*/
	    	 }catch(NullPointerException ne){
			    	log.info("Method Name::populateAccountNumbers"+ne.getMessage());
					  setErrorMessage("Method Name::populateAccountNumbers"); 
					    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
					    return; 
				} catch (Exception exception) {
					log.info("Method Name::populateAccountNumbers"+exception.getMessage());
					setErrorMessage(exception.getMessage());
					RequestContext.getCurrentInstance().execute("exception.show();");
					return;
				}
	    }
	  //based on Account no to get currency
	    public void populatecurrency(){
		      lstBankAccountDetailsCurrencyMap.clear();
		      lstBankAccountDetailsCurrencyCode.clear();
		      lstBankAccountDetailsAccountNumber.clear();
		      try{
		      bankAccountDtlist=testKeyForParameterForBankService.toFetchCurrencyBasedOnAccountNumber(new BigDecimal(getAccountNo()));
		      setBankAccountDescId(bankAccountDtlist.get(0).getBankAccountType().getBankAccountTypeId());
		      for (BankAccountDetails bankAccountDetails : bankAccountDtlist) {
				lstBankAccountDetailsCurrencyMap.put(bankAccountDetails.getFsCurrencyMaster().getCurrencyId(),bankAccountDetails.getFsCurrencyMaster().getCurrencyName());
				lstBankAccountDetailsCurrencyCode.put(bankAccountDetails.getFsCurrencyMaster().getCurrencyId(),bankAccountDetails.getFsCurrencyMaster().getCurrencyCode());
				lstBankAccountDetailsAccountNumber.put(bankAccountDetails.getBankAcctDetId(), bankAccountDetails.getBankAcctNo());
		      }
		     
		     List<BankAccountTypeDesc> lstBankAccountTypeDescs=testKeyForParameterForBankService.tofetchBankAccountTypeDescBasedOnAccountNo(getBankAccountDescId(),sessionStateManage.getLanguageId());
		     if(lstBankAccountTypeDescs.size()>0){
			       setAccountDescription(lstBankAccountTypeDescs.get(0).getBankAccountTypeDesc());
			     
		     }
		      }catch(NullPointerException ne){
			    	log.info("Method Name::populatecurrency"+ne.getMessage());
					  setErrorMessage("Method Name::populatecurrency"); 
					    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
					    return; 
				} catch (Exception exception) {
					log.info("Method Name::populatecurrency"+exception.getMessage());
					setErrorMessage(exception.getMessage());
					RequestContext.getCurrentInstance().execute("exception.show();");
					return;
				}
	    }
	   
	    //populate based on bank branch
	    public void populateBranch(){
		      bankbranchListMap.clear();
		      try{
		      bankbranchList=testKeyForParameterForBankService.toFetchBranchBasedOnBank(getBankId());
		      for (BankBranch bankBranch : bankbranchList) {
				bankbranchListMap.put(bankBranch.getBankBranchId(), bankBranch.getBranchFullName());
				bankbranchListMapCode.put(bankBranch.getBankBranchId(), bankBranch.getBranchCode().toPlainString());
		    }
		      }catch(NullPointerException ne){
			    	log.info("Method Name::populateBranch"+ne.getMessage());
					  setErrorMessage("Method Name::populateBranch"); 
					    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
					    return; 
				} catch (Exception exception) {
					log.info("Method Name::populateBranch"+exception.getMessage());
					setErrorMessage(exception.getMessage());
					RequestContext.getCurrentInstance().execute("exception.show();");
					return;
				}
	    }
	    //to fetch detla for view parametrs
	    public void fetchPrimaryParameters(){
	    	try{
		      lstTestKeyParameters=testKeyForParameterForBankService.tofetchAllPrimaryParameters();
		      if(lstTestKeyParameters.size()>0){
		      setLstTestKeyParameters(lstTestKeyParameters);
		      }
	    	}catch(NullPointerException ne){
		    	log.info("Method Name::fetchPrimaryParameters"+ne.getMessage());
				  setErrorMessage("Method Name::fetchPrimaryParameters"); 
				    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				    return; 
			} catch (Exception exception) {
				log.info("Method Name::fetchPrimaryParameters"+exception.getMessage());
				setErrorMessage(exception.getMessage());
				RequestContext.getCurrentInstance().execute("exception.show();");
				return;
			}
	    }
	    
	    //clearAllFields
	    public void clearAllFields(){
		      setBankCode(null);
		      setBankId(null);
		      setBankDescription(null);
		      setAccountNo(null);
		      setAccountDescription(null);
		      setCurrencyId(null);
		      setCurrencyName(null);
		      setBranchCode(null);
		      setSendReceiveIndicator(null);
		      setTestKeyParameterPk(null);
	    }
	    
	    //auto populate based on these combination to check for(application country,bank, account, branch code and send/receive indicator)
	    public void fetchdataBasedOnCombination(){
	    	try{
		      setAccountNumber(lstBankAccountDetailsAccountNumber.get(new BigDecimal(getAccountNo())));
		      if(getBankId() != null && getAccountNumber() != null && getBranchCode() != null && getSendReceiveIndicator() != null){
				lstTestkeyParameterForBankDt.clear();
		      List<TestKeyMaster> testKeyMasterList=testKeyForParameterForBankService.fetchtheRecordFromTestKeyMaster(sessionStateManage.getCountryId(),getBankId(),getAccountNumber(),getBranchCode(),getSendReceiveIndicator());
		      if(testKeyMasterList.size()>0){
				setBooRenderDataTable(true);
				setBooSaveOrExit(true);
				for (TestKeyMaster testKeyMaster : testKeyMasterList) {
					     TestkeyParameterForBankDataTable testBankDataTable=new TestkeyParameterForBankDataTable();
					     testBankDataTable.setApplicationCountryId(testKeyMaster.getFsCountryMaster().getCountryId());
					     testBankDataTable.setBankId(testKeyMaster.getExBankMaster().getBankId());
					     testBankDataTable.setBankName(bankMasterListMap.get(testKeyMaster.getExBankMaster().getBankId()));
					     testBankDataTable.setBankDescription(bankMasterListMap.get(testKeyMaster.getExBankMaster().getBankId()));
					     testBankDataTable.setAccountNo(testKeyForParameterForBankService.tofetchAccountId(testKeyMaster.getBankAccountNumber(),testKeyMaster.getExBankMaster().getBankId()).toPlainString());
					     testBankDataTable.setAccountNumber(testKeyMaster.getBankAccountNumber());
					     testBankDataTable.setAccountDescription(testKeyMaster.getAccountDesc());
					     testBankDataTable.setBranchCode(testKeyMaster.getExBankBranch().getBankBranchId());
					     testBankDataTable.setBankBranchCode(testKeyMaster.getBranchCode());
					     testBankDataTable.setBranchName(testKeyForParameterForBankService.toFetchBankBranch(testKeyMaster.getExBankBranch().getBankBranchId()));
					     testBankDataTable.setCurrencyId(testKeyMaster.getExCurrencyMaster().getCurrencyId());
					     //testBankDataTable.setCurrencyName(lstBankAccountDetailsCurrencyMap.get(testKeyMaster.getExCurrencyMaster().getCurrencyId()));
					     testBankDataTable.setCurrencyName(generalService.getCurrencyName(testKeyMaster.getExCurrencyMaster().getCurrencyId()));
					     testBankDataTable.setSeconaryIndicator(testKeyMaster.getSerialIndicator());
					     testBankDataTable.setPrimaryTestKey(testKeyMaster.getPrimaryTestKeyCode());
					     testBankDataTable.setPrimaryTestKeyName(testKeyForParameterForBankService.toFetchParameterTestKeyName(testKeyMaster.getPrimaryTestKeyCode()));
					     testBankDataTable.setSecondaryTestKey(testKeyMaster.getSecondaryTestKeyCode());
					     testBankDataTable.setTestKeyParameterPk(testKeyMaster.getTestKeyMasterId());
					     testBankDataTable.setCalculationOrderNo(testKeyMaster.getCalculationOrederNo());
					     //testBankDataTable.setPrimaryTestKeyName(testKeyMaster.getPrimaryTestKeyName());
					     testBankDataTable.setSecondaryTestKeyParameterName(testKeyForParameterForBankService.toFetchParameterTestKeyName(testKeyMaster.getSecondaryTestKeyCode()));
					     // testBankDataTable.setSerialIndicator(testKeyMaster.getSerialIndicator());
					    // testBankDataTable.setEndOfSerial(testKeyMaster.getSrRule());
					     if(testKeyMaster.getSendReceieveIndicator().equalsIgnoreCase("S")){
						       testBankDataTable.setSendReceiveIndicator("Send");     
					     }else{
						       testBankDataTable.setSendReceiveIndicator("Receieve");
					     }
					     if(testKeyMaster.getCalculationType().equalsIgnoreCase("M")){
						       testBankDataTable.setCalculationType("Multiplication");
					      }else{
							testBankDataTable.setCalculationType("Add");	
					      }
					     if(testKeyMaster.getSerialIndicator().equalsIgnoreCase("Daily")){
						       testBankDataTable.setSerialIndicator("Daily");
							      }else if (testKeyMaster.getSerialIndicator().equalsIgnoreCase("Weekly")) {
									testBankDataTable.setSerialIndicator("Weekly");      
							      }else if (testKeyMaster.getSerialIndicator().equalsIgnoreCase("Monthly")) {
									testBankDataTable.setSerialIndicator("Monthly");      
							      }
							      else if (testKeyMaster.getSerialIndicator().equalsIgnoreCase("Cont")) {
									testBankDataTable.setSerialIndicator("Cont");      
							      }else{
									testBankDataTable.setSerialIndicator(" ");  	
							      }
					     		if(testKeyMaster.getSrRule() !=null){
							      if(testKeyMaster.getSrRule().equalsIgnoreCase("N")){
									testBankDataTable.setEndOfSerial("New");
							      }else if (testKeyMaster.getSrRule().equalsIgnoreCase("R")){
									testBankDataTable.setEndOfSerial("Reuse");	
							      }
					     		}else{
								testBankDataTable.setEndOfSerial("");
							      }
					     testBankDataTable.setCreatedBy(testKeyMaster.getCreatedBy());
					     testBankDataTable.setCreatedDate(testKeyMaster.getCreatedDate());
					     testBankDataTable.setModifiedBy(testKeyMaster.getModifiedBy());
					     testBankDataTable.setModifiedDate(testKeyMaster.getModifiedDate());
					     testBankDataTable.setApprovedBy(testKeyMaster.getApprovedBy());
					     testBankDataTable.setApprovedDate(testKeyMaster.getApprovedDate());
					     testBankDataTable.setRemarks(testKeyMaster.getRemarks());
					     testBankDataTable.setIsActive(testKeyMaster.getIsActive());
					     testBankDataTable.setRenderEditButton(true);
					     testBankDataTable.setBooEditButton(false);
					     if(testBankDataTable.getIsActive().equalsIgnoreCase(Constants.Yes)){
						       testBankDataTable.setDynamicLabelForActivateDeactivate("Approved"); 
					     }else if (testBankDataTable.getIsActive().equalsIgnoreCase(Constants.D)) {
						       testBankDataTable.setDynamicLabelForActivateDeactivate("Deleted");
						    }else if (testBankDataTable.getIsActive().equalsIgnoreCase(Constants.U)&&testBankDataTable.getModifiedBy()==null&&testBankDataTable.getModifiedDate()==null&&testBankDataTable.getApprovedBy()==null&&testBankDataTable.getApprovedDate()==null&&testBankDataTable.getRemarks()==null) {
							      testBankDataTable.setDynamicLabelForActivateDeactivate("Un_Approved");
						    }else{
							      testBankDataTable.setDynamicLabelForActivateDeactivate("Un_Approved");      
						    }
					    lstTestkeyParameterForBankDt.add(testBankDataTable); 
				    }
		      }else{
				
				 RequestContext.getCurrentInstance().execute("noRecords.show();");
				 setBooRenderDataTable(false);
				 setBooSaveOrExit(false);
				 clearAllFields();
				 return;
				
		      }
		      
	    }else {
				clearAllFields();
				 setBooRenderDataTable(false);
				 setBooSaveOrExit(false);
				      RequestContext.getCurrentInstance().execute("selectAll.show();");
				      return;    
		    }
	    	}catch(NullPointerException ne){
		    	log.info("Method Name::fetchdataBasedOnCombination"+ne.getMessage());
				  setErrorMessage("Method Name::fetchdataBasedOnCombination"); 
				    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				    return; 
			} catch (Exception exception) {
				log.info("Method Name::fetchdataBasedOnCombination"+exception.getMessage());
				setErrorMessage(exception.getMessage());
				RequestContext.getCurrentInstance().execute("exception.show();");
				return;
			}       
		    
	    }
	   
	    public void exit(){
			    lstTestkeyParameterForBankDt.clear();
			    lstTestkeyParameterForNewBankDt.clear();
			    try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
				} catch (Exception e) {
					e.printStackTrace();
				}    
		  }
	


}
