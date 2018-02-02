package com.amg.exchange.testkey.bean;

import java.io.IOException;
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
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.testkey.model.TestKeyMaster;
import com.amg.exchange.testkey.model.ViewTestKeyParameter;
import com.amg.exchange.testkey.service.ITestKeyForParameterForBankService;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("testkeyParameterForBankBean")
@Scope("session")
public class TestkeyParameterForBankBean<T> implements Serializable {
	  private static final Logger log=Logger.getLogger(TestkeyParameterForBankBean.class);

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
 
	  private BigDecimal noOfCount;
	  private BigDecimal bankBranchCode;
	//  private String primaryTestKeyParameterName;
	  private String secondaryTestKeyParameterName;
	  
	  //boolean Variables
	  private Boolean booAdd=false;
	  private Boolean booRenderDataTable=false;
	  private Boolean booSaveOrExit=false;
	  private Boolean booseconaryIndicator=false;
	  private Boolean booendOfSerial=false;
	  private Boolean booClearPanel=false;
	  private Boolean booEditButton=false;
	  private Boolean ifEditClicked=false;
	  private Boolean booApproval=false;
	  private Boolean booRead=false;
	  private Boolean booSubmitPanel=false;
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
	  public Boolean getBooseconaryIndicator() {
	  	  return booseconaryIndicator;
	  }
	  public void setBooseconaryIndicator(Boolean booseconaryIndicator) {
	  	  this.booseconaryIndicator = booseconaryIndicator;
	  }
	  public Boolean getBooendOfSerial() {
	  	  return booendOfSerial;
	  }
	  public void setBooendOfSerial(Boolean booendOfSerial) {
	  	  this.booendOfSerial = booendOfSerial;
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
	  public Boolean getBooAdd() {
	  	  return booAdd;
	  }
	  public void setBooAdd(Boolean booAdd) {
	  	  this.booAdd = booAdd;
	  }
	  public Boolean getBooClearPanel() {
	  	  return booClearPanel;
	  }
	  public void setBooClearPanel(Boolean booClearPanel) {
	  	  this.booClearPanel = booClearPanel;
	  }
	  public Boolean getBooEditButton() {
	  	  return booEditButton;
	  }
	  public void setBooEditButton(Boolean booEditButton) {
	  	  this.booEditButton = booEditButton;
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
	  public BigDecimal getNoOfCount() {
	  	  return noOfCount;
	  }
	  public void setNoOfCount(BigDecimal noOfCount) {
	  	  this.noOfCount = noOfCount;
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
	  public Boolean getIfEditClicked() {
	  	  return ifEditClicked;
	  }
	  public void setIfEditClicked(Boolean ifEditClicked) {
	  	  this.ifEditClicked = ifEditClicked;
	  }
	  public Boolean getBooApproval() {
	  	  return booApproval;
	  }
	  public void setBooApproval(Boolean booApproval) {
	  	  this.booApproval = booApproval;
	  }
	  public Boolean getBooRead() {
	  	  return booRead;
	  }
	  public void setBooRead(Boolean booRead) {
	  	  this.booRead = booRead;
	  }
	  public Boolean getBooSubmitPanel() {
	  	  return booSubmitPanel;
	  }
	  public void setBooSubmitPanel(Boolean booSubmitPanel) {
	  	  this.booSubmitPanel = booSubmitPanel;
	  }
	  public BigDecimal getBankBranchCode() {
	  	  return bankBranchCode;
	  }
	  public void setBankBranchCode(BigDecimal bankBranchCode) {
	  	  this.bankBranchCode = bankBranchCode;
	  }
	 /* public String getPrimaryTestKeyParameterName() {
	  	  return primaryTestKeyParameterName;
	  }
	  public void setPrimaryTestKeyParameterName(String primaryTestKeyParameterName) {
	  	  this.primaryTestKeyParameterName = primaryTestKeyParameterName;
	  }*/
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
	  public void parameterForBankPageNavigation(){
		    setBooRenderDataTable(false);
		    setBooSaveOrExit(false);
		    setBooEditButton(false);
		    setBooAdd(true);
		    setBooApproval(false);
		    setBooRead(false);
		    setBooClearPanel(false);
		    setBooSubmitPanel(false);
		    setBooseconaryIndicator(false);
		    setBooendOfSerial(false);
		    clearAllFields();
		    lstTestkeyParameterForBankDt.clear();
		    lstTestkeyParameterForNewBankDt.clear();
		   // bankMasterList.clear();
		    //form loading to populate CorespondingBank
		    try {
		    popUpCorespondingBank();
		    //primaryParametersFromView
		    fetchPrimaryParameters();
		    loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "ParametersForBank.xhtml");
			      FacesContext.getCurrentInstance().getExternalContext().redirect("../testKey/ParametersForBank.xhtml");      
		   /* } catch (Exception e) {
			     e.printStackTrace();
		    }*/
		    }catch(NullPointerException ne){
		    	log.info("Method Name::parameterForBankPageNavigation"+ne.getMessage());
				  setErrorMessage("Method Name::parameterForBankPageNavigation"); 
				    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				    return; 
			} catch (Exception exception) {
				log.info("Method Name::parameterForBankPageNavigation"+exception.getMessage());
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
		      setBankDescription(bankMasterListMap.get(getBankId()));
		     // lstBankAccountDetailsAccountMap.clear();
		      try{
		      lstBankAccountDetails=testKeyForParameterForBankService.toFetchAccountNumberBasedOnBank(getBankId()); 
		      }catch(NullPointerException ne){
			    	log.info("Method Name::parameterForBankPageNavigation"+ne.getMessage());
					  setErrorMessage("Method Name::parameterForBankPageNavigation"); 
					    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
					    return; 
				} catch (Exception exception) {
					log.info("Method Name::parameterForBankPageNavigation"+exception.getMessage());
					setErrorMessage(exception.getMessage());
					RequestContext.getCurrentInstance().execute("exception.show();");
					return;
				}
		      /*for (BankAccountDetails bankAccountDetails : lstBankAccountDetails) {
				lstBankAccountDetailsAccountMap.put(bankAccountDetails.getBankAcctDetId(), bankAccountDetails.getBankAcctNo());
				
		    }*/
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
			    	log.info("Method Name::parameterForBankPageNavigation"+ne.getMessage());
					  setErrorMessage("Method Name::parameterForBankPageNavigation"); 
					    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
					    return; 
				} catch (Exception exception) {
					log.info("Method Name::parameterForBankPageNavigation"+exception.getMessage());
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
			    	log.info("Method Name::parameterForBankPageNavigation"+ne.getMessage());
					  setErrorMessage("Method Name::parameterForBankPageNavigation"); 
					    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
					    return; 
				} catch (Exception exception) {
					log.info("Method Name::parameterForBankPageNavigation"+exception.getMessage());
					setErrorMessage(exception.getMessage());
					RequestContext.getCurrentInstance().execute("exception.show();");
					return;
				}
	    }
	    //to fetch detla for view parametrs
	    public void fetchPrimaryParameters(){
		      lstTestKeyParameters=testKeyForParameterForBankService.tofetchAllPrimaryParameters();
		      if(lstTestKeyParameters.size()>0){
		      setLstTestKeyParameters(lstTestKeyParameters);
		      }
	    }
	    
	    //to Check Primary and SecondaryParameter
	    public void checkPrimaryOrSecondaryTestKeyValus(){
		      if(getPrimaryTestKey().equalsIgnoreCase(getSecondaryTestKey())){
					setBooendOfSerial(false);
					setBooseconaryIndicator(false);
					setBooApproval(false);
					setBooRead(false);
						RequestContext.getCurrentInstance().execute("duplicateRecords.show();");
						setSecondaryTestKey(null);
						setPrimaryTestKey(null);
						return; 
		    }else{
			    //  List<ViewTestKeyParameter> viewTestKeyParameterList=testKeyForParameterForBankService.tofetchAllPrimaryParametersandSecondParameters(getPrimaryTestKey(),getSecondaryTestKey());
			try{
		    	String  Tkytyp=testKeyForParameterForBankService.tofetchAllPrimaryParameters(getPrimaryTestKey());
			String secondTkytyp=testKeyForParameterForBankService.tofetchAllPrimaryParameters(getSecondaryTestKey());
			if(Tkytyp != null && secondTkytyp != null){
				  if(Tkytyp.equalsIgnoreCase("V") || secondTkytyp.equalsIgnoreCase("V")){
					    if(lstTestkeyParameterForBankDt.size() !=0){
				  setBooendOfSerial(true);
				  setBooseconaryIndicator(false);
				  setBooRenderDataTable(true);
				  setBooSaveOrExit(true);
				  setBooApproval(false);
					setBooRead(false);
					    }else{
						      setBooendOfSerial(true);
							  setBooseconaryIndicator(false);
							  setBooRenderDataTable(false);
							  setBooSaveOrExit(false);
							  setBooApproval(false);
								setBooRead(false);      
					    }
			}else{
				  if(lstTestkeyParameterForBankDt.size() !=0){ 
				  setBooendOfSerial(false);
				  setBooseconaryIndicator(true);
				  setBooRenderDataTable(true);
				  setBooSaveOrExit(true);  
				  setBooApproval(false);
					setBooRead(false);
				  }else{
					    setBooendOfSerial(false);
						  setBooseconaryIndicator(true);
						  setBooRenderDataTable(false);
						  setBooSaveOrExit(false);  
						  setBooApproval(false);
							setBooRead(false);    
				  }
			}
			}else{
			if(lstTestkeyParameterForBankDt.size()==0){
			setBooRenderDataTable(false);
			setBooSaveOrExit(false);
			setBooApproval(false);
			setBooRead(false);
			}
		    }
		    }catch(NullPointerException ne){
		    	log.info("Method Name::parameterForBankPageNavigation"+ne.getMessage());
				  setErrorMessage("Method Name::parameterForBankPageNavigation"); 
				    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				    return; 
			} catch (Exception exception) {
				log.info("Method Name::parameterForBankPageNavigation"+exception.getMessage());
				setErrorMessage(exception.getMessage());
				RequestContext.getCurrentInstance().execute("exception.show();");
				return;
			}
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
		      setPrimaryTestKey(null);
		      setSecondaryTestKey(null);
		      setSendReceiveIndicator(null);
		      setEndOfSerial(null);
		      setCalculationOrderNo(null);
		      setCalculationType(null);
		      setSeconaryIndicator(null);
		      setEndOfSerial(null);
		      setIfEditClicked(false);
		      setBooseconaryIndicator(false);
		      setBooendOfSerial(false);
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
				setBooApproval(false);
				setBooRead(false);
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
						       testBankDataTable.setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE); 
					     }else if (testBankDataTable.getIsActive().equalsIgnoreCase(Constants.D)) {
						       testBankDataTable.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
						    }else if (testBankDataTable.getIsActive().equalsIgnoreCase(Constants.U)&&testBankDataTable.getModifiedBy()==null&&testBankDataTable.getModifiedDate()==null&&testBankDataTable.getApprovedBy()==null&&testBankDataTable.getApprovedDate()==null&&testBankDataTable.getRemarks()==null) {
							      testBankDataTable.setDynamicLabelForActivateDeactivate(Constants.DELETE);
						    }else{
							      testBankDataTable.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);      
						    }
					    lstTestkeyParameterForBankDt.add(testBankDataTable); 
				    }
				if(lstTestkeyParameterForBankDt.size()!= 0){
					  lstTestkeyParameterForBankDt.addAll(lstTestkeyParameterForNewBankDt);
					  setBooRenderDataTable(true);
					  setBooSaveOrExit(true);
				}else{
					  setBooRenderDataTable(false);
					  setBooSaveOrExit(false);  
				}
				    //  lstTestkeyParameterForBankDt.addAll(lstTestkeyParameterForNewBankDt);
		      }else{
				if(lstTestkeyParameterForNewBankDt.size() != 0){
					  lstTestkeyParameterForBankDt.addAll(lstTestkeyParameterForNewBankDt);
					 setBooRenderDataTable(true);
					 setBooSaveOrExit(true);
				}else{
					  setBooRenderDataTable(false);
					  setBooSaveOrExit(false);    
				}
		      }
		      
	    }else {
				clearAllFields();
				  setBooApproval(false);
					setBooRead(false);
				      RequestContext.getCurrentInstance().execute("selectAll.show();");
				      return;    
		    }
	    }catch(NullPointerException ne){
	    	log.info("Method Name::parameterForBankPageNavigation"+ne.getMessage());
			  setErrorMessage("Method Name::parameterForBankPageNavigation"); 
			    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			    return; 
		} catch (Exception exception) {
			log.info("Method Name::parameterForBankPageNavigation"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}
			       
		    
	    }
	    public void duplicateCheckForDataTable(){
		      if(lstTestkeyParameterForBankDt.size()>0){
				for (TestkeyParameterForBankDataTable dataTable : lstTestkeyParameterForBankDt) {
					if(dataTable.getBankId().equals(getBankId())&&dataTable.getBankDescription().equalsIgnoreCase(getBankDescription())&&dataTable.getAccountNo().equalsIgnoreCase(getAccountNo())&&dataTable.getAccountDescription().equalsIgnoreCase(getAccountDescription())&&dataTable.getCurrencyId().equals(getCurrencyId())&&dataTable.getBranchCode().equals(getBranchCode())&&dataTable.getSendReceiveIndicator().equalsIgnoreCase(getSendReceiveIndicator())&&dataTable.getCalculationType().equalsIgnoreCase(getCalculationType())&&dataTable.getPrimaryTestKey().equalsIgnoreCase(getPrimaryTestKey())&&dataTable.getSecondaryTestKey().equalsIgnoreCase(getSecondaryTestKey())){
						  clearAllFields();
						  setBooApproval(false);
							setBooRead(false);
						      RequestContext.getCurrentInstance().execute("datatable.show();");
						      return;
					}
			      }
		      }
		      addRecordToDataTable();
	    }
	    
	    public void addRecordToDataTable(){
		      setBooEditButton(false);
		      setBooClearPanel(false);
			    setBooSubmitPanel(false);
		      TestkeyParameterForBankDataTable testkeyParameterForBankDTOBJ=new TestkeyParameterForBankDataTable();
		      testkeyParameterForBankDTOBJ.setApplicationCountryId(sessionStateManage.getCountryId());
		      testkeyParameterForBankDTOBJ.setBankId(getBankId());
		      testkeyParameterForBankDTOBJ.setBankDescription(getBankDescription());
		      testkeyParameterForBankDTOBJ.setAccountNo(getAccountNo());
		      testkeyParameterForBankDTOBJ.setAccountDescription(getAccountDescription());
		      testkeyParameterForBankDTOBJ.setCurrencyId(getCurrencyId());
		      testkeyParameterForBankDTOBJ.setBranchCode(getBranchCode());
		     // setBranchCode(new BigDecimal(bankbranchListMapCode.get(getBranchCode())));
		      testkeyParameterForBankDTOBJ.setBankBranchCode(new BigDecimal(bankbranchListMapCode.get(getBranchCode())));
		     // testkeyParameterForBankDTOBJ.setCalculationOrderNo(getCalculationOrderNo());
		      if(getSendReceiveIndicator().equalsIgnoreCase("S")){
		      testkeyParameterForBankDTOBJ.setSendReceiveIndicator("Send");
		      }else {
				testkeyParameterForBankDTOBJ.setSendReceiveIndicator("Receieve");	
		      }
		      if(getCalculationType().equalsIgnoreCase("1")){
		      testkeyParameterForBankDTOBJ.setCalculationType("Multiplication");
		      testkeyParameterForBankDTOBJ.setCalculationTypeForSave("M");
		      }else{
				 testkeyParameterForBankDTOBJ.setCalculationType("Add");
			      testkeyParameterForBankDTOBJ.setCalculationTypeForSave("+");
		      }
		      testkeyParameterForBankDTOBJ.setPrimaryTestKey(getPrimaryTestKey());
		      testkeyParameterForBankDTOBJ.setSecondaryTestKey(getSecondaryTestKey());
		      if(getSeconaryIndicator() != null && getEndOfSerial() != null){
		      if(getSeconaryIndicator().equalsIgnoreCase("D")){
		      testkeyParameterForBankDTOBJ.setSerialIndicator("Daily");
				testkeyParameterForBankDTOBJ.setSerialIndicatorForSave("D");
		      }else if (getSeconaryIndicator().equalsIgnoreCase("W")) {
				testkeyParameterForBankDTOBJ.setSerialIndicator("Weekly");    
				testkeyParameterForBankDTOBJ.setSerialIndicatorForSave("W");
		      }else if (getSeconaryIndicator().equalsIgnoreCase("M")) {
				testkeyParameterForBankDTOBJ.setSerialIndicator("Monthly"); 
				testkeyParameterForBankDTOBJ.setSerialIndicatorForSave("M");
		      }
		      else if (getSeconaryIndicator().equalsIgnoreCase("C")) {
				testkeyParameterForBankDTOBJ.setSerialIndicator("Cont");   
				testkeyParameterForBankDTOBJ.setSerialIndicatorForSave("C");
		      }else{
				testkeyParameterForBankDTOBJ.setSerialIndicator(" ");  	
		      }
		      if(getEndOfSerial().equalsIgnoreCase("N")){
		      testkeyParameterForBankDTOBJ.setEndOfSerial("New");
		      }else if (getEndOfSerial().equalsIgnoreCase("R")){
				testkeyParameterForBankDTOBJ.setEndOfSerial("Reuse");	
		      }else{
				testkeyParameterForBankDTOBJ.setEndOfSerial("");
		      }
		      }else{
				testkeyParameterForBankDTOBJ.setSerialIndicator(" "); 
				testkeyParameterForBankDTOBJ.setEndOfSerial("");
		      }
		      testkeyParameterForBankDTOBJ.setTestKeyParameterPk(getTestKeyParameterPk());
		      testkeyParameterForBankDTOBJ.setBankName(bankMasterListMap.get(getBankId()));
		      testkeyParameterForBankDTOBJ.setCurrencyName(lstBankAccountDetailsCurrencyMap.get(getCurrencyId()));
		      testkeyParameterForBankDTOBJ.setBranchName(bankbranchListMap.get(getBranchCode()));
		      testkeyParameterForBankDTOBJ.setAccountNumber(lstBankAccountDetailsAccountNumber.get(new BigDecimal(getAccountNo())));
		      testkeyParameterForBankDTOBJ.setPrimaryTestKeyName(testKeyForParameterForBankService.toFetchParameterTestKeyName(getPrimaryTestKey()));
		      testkeyParameterForBankDTOBJ.setSecondaryTestKeyParameterName(testKeyForParameterForBankService.toFetchParameterTestKeyName(getSecondaryTestKey()));
		      testkeyParameterForBankDTOBJ.setRenderEditButton(true);
		      testkeyParameterForBankDTOBJ.setCreatedBy(getCreatedBy());
		      testkeyParameterForBankDTOBJ.setCreatedDate(getCreatedDate());
		      if(getTestKeyParameterPk() != null){
				if(testkeyParameterForBankDTOBJ.getBankId().equals(testkeyParameterDataObj.getBankId())&&testkeyParameterForBankDTOBJ.getBankDescription().equalsIgnoreCase(testkeyParameterDataObj.getBankDescription())
				    &&testkeyParameterForBankDTOBJ.getAccountNo().equalsIgnoreCase(testkeyParameterDataObj.getAccountNo())&&testkeyParameterForBankDTOBJ.getAccountDescription().equalsIgnoreCase(testkeyParameterDataObj.getAccountDescription())
				    &&testkeyParameterForBankDTOBJ.getCurrencyId().equals(testkeyParameterDataObj.getCurrencyId())&&testkeyParameterForBankDTOBJ.getBranchCode().equals(testkeyParameterDataObj.getBranchCode())
				    &&testkeyParameterForBankDTOBJ.getSendReceiveIndicator().equalsIgnoreCase(testkeyParameterDataObj.getSendReceiveIndicator())&&testkeyParameterDataObj.getCalculationType().equalsIgnoreCase(testkeyParameterDataObj.getCalculationType())
				    &&testkeyParameterForBankDTOBJ.getPrimaryTestKey().equalsIgnoreCase(testkeyParameterDataObj.getPrimaryTestKey())&&testkeyParameterForBankDTOBJ.getSecondaryTestKey().equalsIgnoreCase(testkeyParameterDataObj.getSecondaryTestKey())){
					  testkeyParameterForBankDTOBJ.setDynamicLabelForActivateDeactivate(getDynamicLabelForActivateDeactivate());
					  testkeyParameterForBankDTOBJ.setIsActive(getIsActive());
					  testkeyParameterForBankDTOBJ.setModifiedBy(getModifiedBy());
					  testkeyParameterForBankDTOBJ.setModifiedDate(getModifiedDate());
					  testkeyParameterForBankDTOBJ.setApprovedBy(getApprovedBy());
					  testkeyParameterForBankDTOBJ.setApprovedDate(getApprovedDate());
					  testkeyParameterForBankDTOBJ.setRemarks(getRemarks());
					  testkeyParameterForBankDTOBJ.setCalculationOrderNo(getCalculationOrderNo());
				}else{
					  testkeyParameterForBankDTOBJ.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
					  testkeyParameterForBankDTOBJ.setIsActive(Constants.U);
					  testkeyParameterForBankDTOBJ.setModifiedBy(sessionStateManage.getUserName());
					  testkeyParameterForBankDTOBJ.setModifiedDate(new Date());
					  testkeyParameterForBankDTOBJ.setApprovedBy(null);
					  testkeyParameterForBankDTOBJ.setApprovedDate(null);
					  testkeyParameterForBankDTOBJ.setRemarks(null);
					  testkeyParameterForBankDTOBJ.setCalculationOrderNo(getCalculationOrderNo());
					  testkeyParameterForBankDTOBJ.setIfEditClicked(true);
				}
		      }else{
		      testkeyParameterForBankDTOBJ.setDynamicLabelForActivateDeactivate(Constants.REMOVE);
		      testkeyParameterForBankDTOBJ.setCreatedBy(sessionStateManage.getUserName());
		      testkeyParameterForBankDTOBJ.setCreatedDate(new Date());
		      testkeyParameterForBankDTOBJ.setIsActive(Constants.U);
		      testkeyParameterForBankDTOBJ.setIfEditClicked(true);
		     
		      }
		      lstTestkeyParameterForBankDt.add(testkeyParameterForBankDTOBJ);
		      if(getTestKeyParameterPk() == null){
				lstTestkeyParameterForNewBankDt.add(testkeyParameterForBankDTOBJ);
		      }
		      setBooAdd(true);
		      setBooRenderDataTable(true);
		      setBooSaveOrExit(true);
		      setBooApproval(false);
			setBooRead(false);
		      clearAllFields();
		      
	    }
	    
	    public void edit(TestkeyParameterForBankDataTable testkeyParameterForBankDtObj){
		      setTestkeyParameterDataObj(testkeyParameterForBankDtObj);
		      setTestKeyParameterPk(testkeyParameterForBankDtObj.getTestKeyParameterPk());
		      setBankId(testkeyParameterForBankDtObj.getBankId());
		      setBankDescription(testkeyParameterForBankDtObj.getBankDescription());
		      setAccountNo(testkeyParameterForBankDtObj.getAccountNo());
		      populateAccountNumbers();
		      setAccountNumber(testkeyParameterForBankDtObj.getAccountNumber());
		      setAccountDescription(testkeyParameterForBankDtObj.getAccountDescription());
		     setCurrencyId(testkeyParameterForBankDtObj.getCurrencyId());
		     populatecurrency();
		     setCurrencyName(testkeyParameterForBankDtObj.getCurrencyName());
		      setBranchCode(testkeyParameterForBankDtObj.getBranchCode());
		      populateBranch();
		      setBranchName(testkeyParameterForBankDtObj.getBranchName());
		      setBankBranchCode(testkeyParameterForBankDtObj.getBankBranchCode());
		      if(testkeyParameterForBankDtObj.getSendReceiveIndicator().equalsIgnoreCase("Send")){
				setSendReceiveIndicator("S");
				      }else{
					setSendReceiveIndicator("R");	
				      }
				     setCalculationOrderNo(testkeyParameterForBankDtObj.getCalculationOrderNo());
				      if(testkeyParameterForBankDtObj.getCalculationType().equalsIgnoreCase("Add")){
						setCalculationType("2");
				      }else{
						setCalculationType("1");	
				      }
				      if(testkeyParameterForBankDtObj.getSerialIndicator() != null && testkeyParameterForBankDtObj.getEndOfSerial() !=null){
						setBooseconaryIndicator(true);
				      if(testkeyParameterForBankDtObj.getSerialIndicator().equalsIgnoreCase("Daily")){
						setSeconaryIndicator("D");
				      }else if (testkeyParameterForBankDtObj.getSerialIndicator().equalsIgnoreCase("Weekly")) {
						setSeconaryIndicator("W");      
				      }else if (testkeyParameterForBankDtObj.getSerialIndicator().equalsIgnoreCase("Monthly")) {
						setSeconaryIndicator("M");      
				      }
				      else if (testkeyParameterForBankDtObj.getSerialIndicator().equalsIgnoreCase("Cont")) {
						setSeconaryIndicator("C");      
				      }
				      if(testkeyParameterForBankDtObj.getEndOfSerial().equalsIgnoreCase("New")){
						setEndOfSerial("N");
				      }else{
						setEndOfSerial("R");	
				      }
				      }else{
						setBooendOfSerial(true);
						setSerialIndicator("");
						setEndOfSerial("");
				      }
		      setPrimaryTestKey(testkeyParameterForBankDtObj.getPrimaryTestKey());
		      setSecondaryTestKey(testkeyParameterForBankDtObj.getSecondaryTestKey());
		      setPrimaryTestKeyName(testkeyParameterForBankDtObj.getPrimaryTestKeyParameterName());
		      setSecondaryTestKeyParameterName(testkeyParameterForBankDtObj.getSecondaryTestKeyParameterName());
		      setCreatedBy(testkeyParameterForBankDtObj.getCreatedBy());
		      setCreatedDate(testkeyParameterForBankDtObj.getCreatedDate());
		      setModifiedBy(testkeyParameterForBankDtObj.getModifiedBy());
		      setModifiedDate(testkeyParameterForBankDtObj.getModifiedDate());
		      setIsActive(testkeyParameterForBankDtObj.getIsActive());
		      setRenderEditButton(testkeyParameterForBankDtObj.getRenderEditButton());
		      setIfEditClicked(true);
		      setDynamicLabelForActivateDeactivate(testkeyParameterForBankDtObj.getDynamicLabelForActivateDeactivate());
		      lstTestkeyParameterForBankDt.remove(testkeyParameterForBankDtObj);
		      lstTestkeyParameterForNewBankDt.remove(testkeyParameterForBankDtObj);
		      if(lstTestkeyParameterForBankDt.size()==0){
				setBooRenderDataTable(false);
				setBooSaveOrExit(false);
				setBooApproval(false);
				setBooRead(false);
		      }else{
				setBooRenderDataTable(true);
				setBooSaveOrExit(true);	
				setBooEditButton(true);
				 setBooSubmitPanel(true);
				    setBooClearPanel(true);
				setBooApproval(false);
				setBooRead(false);
		      }
	    }
	    
	    public void saveTestKeyForBank(){
		      try {
			    for (TestkeyParameterForBankDataTable testkeyParameterForBankDtObj : lstTestkeyParameterForBankDt) {
			      if(testkeyParameterForBankDtObj.getIfEditClicked().equals(true)){
				      TestKeyMaster testKeyMaster=new TestKeyMaster();
			      //Application Country
			      CountryMaster countryMaster =new CountryMaster();
			      countryMaster.setCountryId(testkeyParameterForBankDtObj.getApplicationCountryId());
			      testKeyMaster.setFsCountryMaster(countryMaster);
			    
			      //Bank Branch
			      BankBranch bankBranch=new BankBranch();
			      bankBranch.setBankBranchId(testkeyParameterForBankDtObj.getBranchCode());
			      testKeyMaster.setExBankBranch(bankBranch);
			      //getBankBranchCode
			      testKeyMaster.setBranchCode(testkeyParameterForBankDtObj.getBankBranchCode());
			      
			      //BankMaster
			      BankMaster bankMaster=new BankMaster();
			      bankMaster.setBankId(testkeyParameterForBankDtObj.getBankId());
			      testKeyMaster.setExBankMaster(bankMaster);
			      //BankCode
			      testKeyMaster.setBankCode(bankMasterListMapCode.get(testkeyParameterForBankDtObj.getBankId()));
			      
			      CurrencyMaster currencyMaster= new CurrencyMaster();
			      currencyMaster.setCurrencyId(testkeyParameterForBankDtObj.getCurrencyId());
			      testKeyMaster.setExCurrencyMaster(currencyMaster);
			    //currency Code
			      testKeyMaster.setCurrencyCode(lstBankAccountDetailsCurrencyCode.get(testkeyParameterForBankDtObj.getCurrencyId()));
			      //calCulation Order No
			      List<TestKeyMaster> testKeyMasterList=testKeyForParameterForBankService.toViewForAllrecordsTofetchDb();
					if (testKeyMasterList.size() > 0) {
						  if (testkeyParameterForBankDtObj.getCalculationOrderNo() == null) {
							 int val=testKeyMasterList.size();
							    testKeyMaster.setCalculationOrederNo(testKeyMasterList.get(0).getCalculationOrederNo().add(new BigDecimal(val)));
						  } else {
							    testKeyMaster.setCalculationOrederNo(testkeyParameterForBankDtObj.getCalculationOrderNo());
						  }
					} else {
						  testKeyMaster.setCalculationOrederNo(new BigDecimal(1));
					}
			      
			      
			      //normal Varialbes
			      testKeyMaster.setTestKeyMasterId(testkeyParameterForBankDtObj.getTestKeyParameterPk());
			      testKeyMaster.setBankAccountNumber(testkeyParameterForBankDtObj.getAccountNumber());
			      testKeyMaster.setAccountDesc(testkeyParameterForBankDtObj.getAccountDescription());
			     if(testkeyParameterForBankDtObj.getSendReceiveIndicator().equalsIgnoreCase("Send")){
				       testKeyMaster.setSendReceieveIndicator("S");
			     }else{
				       testKeyMaster.setSendReceieveIndicator("R");       
			     }
			     if(testkeyParameterForBankDtObj.getCalculationType().equalsIgnoreCase("Multiplication")){
			      testKeyMaster.setCalculationType("M");
			     }else{
				       testKeyMaster.setCalculationType("+");       
			     }
			      testKeyMaster.setPrimaryTestKeyCode(testkeyParameterForBankDtObj.getPrimaryTestKey());
			      testKeyMaster.setSecondaryTestKeyCode(testkeyParameterForBankDtObj.getSecondaryTestKey());
			      testKeyMaster.setSerialIndicator(testkeyParameterForBankDtObj.getSerialIndicatorForSave());
			      if(testkeyParameterForBankDtObj.getEndOfSerial().equalsIgnoreCase("New")){
			      testKeyMaster.setSrRule("N");
			      }else if (testkeyParameterForBankDtObj.getEndOfSerial().equalsIgnoreCase("Reuse")) {
					testKeyMaster.setSrRule("N");	
			      }else{
					testKeyMaster.setSrRule("");		
			      }
			      testKeyMaster.setPrimaryTestKeyName(testkeyParameterForBankDtObj.getPrimaryTestKeyName());
			      testKeyMaster.setSecondaryTestKeyName(testkeyParameterForBankDtObj.getSecondaryTestKeyParameterName());
			      testKeyMaster.setCreatedBy(testkeyParameterForBankDtObj.getCreatedBy());
			      testKeyMaster.setCreatedDate(testkeyParameterForBankDtObj.getCreatedDate());
			      testKeyMaster.setModifiedBy(testkeyParameterForBankDtObj.getModifiedBy());
			      testKeyMaster.setModifiedDate(testkeyParameterForBankDtObj.getModifiedDate());
			      testKeyMaster.setApprovedBy(testkeyParameterForBankDtObj.getApprovedBy());
			      testKeyMaster.setApprovedDate(testkeyParameterForBankDtObj.getApprovedDate());
			      testKeyMaster.setIsActive(testkeyParameterForBankDtObj.getIsActive());
			      testKeyMaster.setRemarks(testkeyParameterForBankDtObj.getRemarks());
			      testKeyForParameterForBankService.saveTestKeyMaster(testKeyMaster);
		    } 
		 }	      
			    	   lstTestkeyParameterForBankDt.clear();
				   lstTestkeyParameterForNewBankDt.clear();
				   RequestContext.getCurrentInstance().execute("complete.show();");
				   clearAllFields();
				   setBooRenderDataTable(false);
				   setBooSaveOrExit(false);
				   setBooAdd(true);
				   setBooendOfSerial(false);
				   setBooseconaryIndicator(false);
				   setBooApproval(false);
					setBooRead(false);
		    /*} catch (Exception e) {
			   e.printStackTrace();
		    }*/
		      }catch(NullPointerException ne){
			    	log.info("Method Name::parameterForBankPageNavigation"+ne.getMessage());
					  setErrorMessage("Method Name::parameterForBankPageNavigation"); 
					    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
					    return; 
				} catch (Exception exception) {
					log.info("Method Name::parameterForBankPageNavigation"+exception.getMessage());
					setErrorMessage(exception.getMessage());
					RequestContext.getCurrentInstance().execute("exception.show();");
					return;
				}
	    }
	    
	    public void clickOnOKSave(){
			  lstTestkeyParameterForBankDt.clear();
			   lstTestkeyParameterForNewBankDt.clear();
			 clearAllFields();
			 setBooRenderDataTable(false);
			 setBooSaveOrExit(false);
			 setBooAdd(true);
			 setBooendOfSerial(false);
			   setBooseconaryIndicator(false);
			 setBooRead(false);
			 setBooApproval(false);
			 try {
				   FacesContext.getCurrentInstance().getExternalContext().redirect("../testKey/ParametersForBank.xhtml");      
		  } catch (Exception e) {
			   e.printStackTrace();
		  }
		}
	    
	    public void viewAllTestKeyBank(){
		      clearAllFields();
		      lstTestkeyParameterForBankDt.clear();
		      try {
		      List<TestKeyMaster> testKeyMasterList=testKeyForParameterForBankService.toViewForAllrecordsTofetchDb();
		      if(testKeyMasterList.size()>0){
				setBooRenderDataTable(true);
				setBooSaveOrExit(true);
				setBooApproval(false);
				setBooRead(false);
		      }else{
				setBooRenderDataTable(false);
				setBooSaveOrExit(false);
				setBooApproval(false);
				setBooRead(false);
					RequestContext.getCurrentInstance().execute("noRecords.show();");
					return;  
		      }
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
			     testBankDataTable.setBankBranchCode( testKeyMaster.getBranchCode());
			     if(testKeyMaster.getExBankBranch().getBankBranchId()!=null){
			     testBankDataTable.setBranchName(testKeyForParameterForBankService.toFetchBankBranch(testKeyMaster.getExBankBranch().getBankBranchId()));
			     }
			     testBankDataTable.setCurrencyId(testKeyMaster.getExCurrencyMaster().getCurrencyId());
			     //testBankDataTable.setCurrencyName(lstBankAccountDetailsCurrencyMap.get(testKeyMaster.getExCurrencyMaster().getCurrencyId()));
			     testBankDataTable.setCurrencyName(generalService.getCurrencyName(testKeyMaster.getExCurrencyMaster().getCurrencyId()));
			     testBankDataTable.setSeconaryIndicator(testKeyMaster.getSerialIndicator());
			     testBankDataTable.setPrimaryTestKey(testKeyMaster.getPrimaryTestKeyCode());
			     if(testKeyMaster.getPrimaryTestKeyCode()!=null){
			     testBankDataTable.setPrimaryTestKeyName(testKeyForParameterForBankService.toFetchParameterTestKeyName(testKeyMaster.getPrimaryTestKeyCode()));
			     }
			     testBankDataTable.setSecondaryTestKey(testKeyMaster.getSecondaryTestKeyCode());
			     if(testKeyMaster.getSecondaryTestKeyCode()!=null){
			     testBankDataTable.setSecondaryTestKeyParameterName(testKeyForParameterForBankService.toFetchParameterTestKeyName(testKeyMaster.getSecondaryTestKeyCode()));
			     }
			     testBankDataTable.setTestKeyParameterPk(testKeyMaster.getTestKeyMasterId());
			     testBankDataTable.setCalculationOrderNo(testKeyMaster.getCalculationOrederNo());
			     // testBankDataTable.setSerialIndicator(testKeyMaster.getSerialIndicator());
			    // testBankDataTable.setEndOfSerial(testKeyMaster.getSrRule());
			     if(testKeyMaster.getSendReceieveIndicator()!=null){
			     if(testKeyMaster.getSendReceieveIndicator().equalsIgnoreCase("S")){
				       testBankDataTable.setSendReceiveIndicator("Send");     
			     }else{
				       testBankDataTable.setSendReceiveIndicator("Receieve");
			     }
			     }
			     if(testKeyMaster.getCalculationType()!=null){
			     if(testKeyMaster.getCalculationType().equalsIgnoreCase("M")){
				       testBankDataTable.setCalculationType("Multiplication");
				       testBankDataTable.setCalculationTypeForSave("M");
			      }else{
					testBankDataTable.setCalculationType("Add");	
					 testBankDataTable.setCalculationTypeForSave("+");
			      }
			     }
			     if(testKeyMaster.getSerialIndicator()!=null){
			     if(testKeyMaster.getSerialIndicator().equalsIgnoreCase("D")){
				       testBankDataTable.setSerialIndicator("Daily");
				       testBankDataTable.setSerialIndicatorForSave("D");
					      }else if (testKeyMaster.getSerialIndicator().equalsIgnoreCase("W")) {
							testBankDataTable.setSerialIndicator("Weekly");      
						    testBankDataTable.setSerialIndicatorForSave("W");
					      }else if (testKeyMaster.getSerialIndicator().equalsIgnoreCase("M")) {
							testBankDataTable.setSerialIndicator("Monthly");   
						    testBankDataTable.setSerialIndicatorForSave("M");
					      }
					      else if (testKeyMaster.getSerialIndicator().equalsIgnoreCase("C")) {
							testBankDataTable.setSerialIndicator("Cont"); 
						    testBankDataTable.setSerialIndicatorForSave("C");
					      }else{
							testBankDataTable.setSerialIndicator(" ");  	
					      }
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
				       testBankDataTable.setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE); 
			     }else if (testBankDataTable.getIsActive().equalsIgnoreCase(Constants.D)) {
				       testBankDataTable.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
				    }else if (testBankDataTable.getIsActive().equalsIgnoreCase(Constants.U)&&testBankDataTable.getModifiedBy()==null&&testBankDataTable.getModifiedDate()==null&&testBankDataTable.getApprovedBy()==null&&testBankDataTable.getApprovedDate()==null&&testBankDataTable.getRemarks()==null) {
					      testBankDataTable.setDynamicLabelForActivateDeactivate(Constants.DELETE);
				    }else{
					      testBankDataTable.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);      
				    }
			    lstTestkeyParameterForBankDt.add(testBankDataTable); 
		    }
		      lstTestkeyParameterForBankDt.addAll(lstTestkeyParameterForNewBankDt);
		      clearAllFields();
			 // try {
				    FacesContext.getCurrentInstance().getExternalContext().redirect("../testKey/ParametersForBank.xhtml");      
		 /* } catch (Exception e) {
			   e.printStackTrace();
		  }*/
			  }catch(NullPointerException ne){
			    	log.info("Method Name::parameterForBankPageNavigation"+ne.getMessage());
					  setErrorMessage("Method Name::parameterForBankPageNavigation"); 
					    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
					    return; 
				} catch (Exception exception) {
					log.info("Method Name::parameterForBankPageNavigation"+exception.getMessage());
					setErrorMessage(exception.getMessage());
					RequestContext.getCurrentInstance().execute("exception.show();");
					return;
				}
	    }
	    
	    
	    public void checkStatus(TestkeyParameterForBankDataTable testkeyParameterDtObj){
		      if(testkeyParameterDtObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.PENDING_FOR_APPROVE)){
				      RequestContext.getCurrentInstance().execute("pending.show();");
					return;     
			    }else if(testkeyParameterDtObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.REMOVE)){
				      lstTestkeyParameterForBankDt.remove(testkeyParameterDtObj);
				      lstTestkeyParameterForNewBankDt.remove(testkeyParameterDtObj);    
			    }else if(testkeyParameterDtObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)){
				      testkeyParameterDtObj.setRemarksCheck(true);
				        setApprovedBy(testkeyParameterDtObj.getApprovedBy());
					setApprovedDate(testkeyParameterDtObj.getApprovedDate());
					RequestContext.getCurrentInstance().execute("remarks.show();");
			    }else if(testkeyParameterDtObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.ACTIVATE)){
				      testkeyParameterDtObj.setActiveRecordCheck(true);
					RequestContext.getCurrentInstance().execute("activateRecord.show();");
					return;      
			    }
			    else if (testkeyParameterDtObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DELETE)&&testkeyParameterDtObj.getModifiedBy()==null&&testkeyParameterDtObj.getModifiedDate()==null&&testkeyParameterDtObj.getApprovedBy()==null&&testkeyParameterDtObj.getApprovedDate()==null&&testkeyParameterDtObj.getRemarks()==null) {
				      testkeyParameterDtObj.setPermentDeleteCheck(true);
					RequestContext.getCurrentInstance().execute("permanentDelete.show();");
					return;			      
			    }
			    if(lstTestkeyParameterForBankDt.size()==0){
				      setBooRenderDataTable(false);
					    setBooSaveOrExit(false);
					    setBooAdd(true);
					    setBooendOfSerial(false);
					    setBooseconaryIndicator(false);
					   setBooApproval(false);
					   setBooRead(false);    
			    }
	    }
	    public void CompleteAssignedConfirmPermanentDelete(){
			    if(lstTestkeyParameterForBankDt.size()>0){
				      for (TestkeyParameterForBankDataTable testkeyParameterForBankDataTable : lstTestkeyParameterForBankDt) {
					if(testkeyParameterForBankDataTable.getPermentDeleteCheck()!=null){
						  if(testkeyParameterForBankDataTable.getPermentDeleteCheck().equals(true)){
							    deleteRecordTestKeyMaster(testkeyParameterForBankDataTable);
							    lstTestkeyParameterForBankDt.remove(testkeyParameterForBankDataTable);
						  }
					}
				      }
			    }   
		  }
	    
	    public void deleteRecordTestKeyMaster(TestkeyParameterForBankDataTable testkeyParameterForBankDataTable){
		      testKeyForParameterForBankService.deleteRecordTestKeyMaster(testkeyParameterForBankDataTable.getTestKeyParameterPk());
	    }
	    public void updateTestKeyMaster(TestkeyParameterForBankDataTable testkeyParameterForBankDtObj){
		      try {
				TestKeyMaster testKeyMaster=new TestKeyMaster();
				      //Application Country
				      CountryMaster countryMaster =new CountryMaster();
				      countryMaster.setCountryId(testkeyParameterForBankDtObj.getApplicationCountryId());
				      testKeyMaster.setFsCountryMaster(countryMaster);
				    
				      //Bank Branch
				      BankBranch bankBranch=new BankBranch();
				      bankBranch.setBankBranchId(testkeyParameterForBankDtObj.getBranchCode());
				      testKeyMaster.setExBankBranch(bankBranch);
				    
				      //getBankBranchCode
				      testKeyMaster.setBranchCode(testkeyParameterForBankDtObj.getBankBranchCode());
				      
				      //BankMaster
				      BankMaster bankMaster=new BankMaster();
				      bankMaster.setBankId(testkeyParameterForBankDtObj.getBankId());
				      testKeyMaster.setExBankMaster(bankMaster);
				      //BankCode
				      testKeyMaster.setBankCode(bankMasterListMapCode.get(testkeyParameterForBankDtObj.getBankId()));
				      
				      CurrencyMaster currencyMaster= new CurrencyMaster();
				      currencyMaster.setCurrencyId(testkeyParameterForBankDtObj.getCurrencyId());
				      testKeyMaster.setExCurrencyMaster(currencyMaster);
				    //currency Code
				      testKeyMaster.setCurrencyCode(lstBankAccountDetailsCurrencyCode.get(testkeyParameterForBankDtObj.getCurrencyId()));
				      //calCulation Order No
				      List<TestKeyMaster> testKeyMasterList=testKeyForParameterForBankService.toViewForAllrecordsTofetchDb();
				      if(testKeyMasterList.size()>0){
				      testKeyMaster.setCalculationOrederNo(testKeyMasterList.get(0).getCalculationOrederNo().add(new BigDecimal(1)));
				      }else{
						testKeyMaster.setCalculationOrederNo(new BigDecimal(1));	
				      }
				      
				      
				      //normal Varialbes
				      testKeyMaster.setTestKeyMasterId(testkeyParameterForBankDtObj.getTestKeyParameterPk());
				      testKeyMaster.setBankAccountNumber(testkeyParameterForBankDtObj.getAccountNumber());
				      testKeyMaster.setAccountDesc(testkeyParameterForBankDtObj.getAccountDescription());
				      if(testkeyParameterForBankDtObj.getSendReceiveIndicator()!=null){
				     if(testkeyParameterForBankDtObj.getSendReceiveIndicator().equalsIgnoreCase("Send")){
					       testKeyMaster.setSendReceieveIndicator("S");
				     }else{
					       testKeyMaster.setSendReceieveIndicator("R");       
				     }
				      }
 
				      testKeyMaster.setCalculationType(testkeyParameterForBankDtObj.getCalculationTypeForSave() );
				      testKeyMaster.setPrimaryTestKeyCode(testkeyParameterForBankDtObj.getPrimaryTestKey());
				      testKeyMaster.setSecondaryTestKeyCode(testkeyParameterForBankDtObj.getSecondaryTestKey());
				      testKeyMaster.setSerialIndicator(testkeyParameterForBankDtObj.getSerialIndicatorForSave());
				    if(testkeyParameterForBankDtObj.getEndOfSerial()!=null){
				      if(testkeyParameterForBankDtObj.getEndOfSerial().equalsIgnoreCase("New")){
				      testKeyMaster.setSrRule("N");
				      }else if (testkeyParameterForBankDtObj.getEndOfSerial().equalsIgnoreCase("Reuse")) {
						testKeyMaster.setSrRule("N");	
				      }else{
						testKeyMaster.setSrRule("");		
				      }
				    }
				      testKeyMaster.setPrimaryTestKeyName(testkeyParameterForBankDtObj.getPrimaryTestKeyName());
				      testKeyMaster.setSecondaryTestKeyName(testkeyParameterForBankDtObj.getSecondaryTestKey());
				      testKeyMaster.setCreatedBy(testkeyParameterForBankDtObj.getCreatedBy());
				      testKeyMaster.setCreatedDate(testkeyParameterForBankDtObj.getCreatedDate());
				      testKeyMaster.setModifiedBy(sessionStateManage.getUserName());
				      testKeyMaster.setModifiedDate(new Date());
				      testKeyMaster.setApprovedBy(null);
				      testKeyMaster.setApprovedDate(null);
				      testKeyMaster.setIsActive(Constants.D);
				      testKeyMaster.setRemarks(testkeyParameterForBankDtObj.getRemarks());
				      testKeyForParameterForBankService.saveTestKeyMaster(testKeyMaster);     
		   /* } catch (Exception e) {
			    e.printStackTrace();
		    }*/
		      }catch(NullPointerException ne){
	
			    	log.info("Method Name::parameterForBankPageNavigation"+ne.getMessage());
					  setErrorMessage("Method Name::parameterForBankPageNavigation"); 
					    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
					    return; 
				} catch (Exception exception) {

					log.info("Method Name::parameterForBankPageNavigation"+exception.getMessage());
					setErrorMessage(exception.getMessage());
					RequestContext.getCurrentInstance().execute("exception.show();");
					return;
				}
	    }
	    public void clickOkRemarks(){
			 if(getRemarks() != null && !getRemarks().equals("")){
				   for (TestkeyParameterForBankDataTable testkeyParameterForBankDataTable : lstTestkeyParameterForBankDt) {
					    if(testkeyParameterForBankDataTable.getRemarksCheck() != null){
						 if(testkeyParameterForBankDataTable.getRemarksCheck().equals(true)) {
							   testkeyParameterForBankDataTable.setRemarks(getRemarks());
							   updateTestKeyMaster(testkeyParameterForBankDataTable);
								clearAllFields();
								viewAllTestKeyBank();
								setRemarks(null);	   
						 }
					    }
			    }
			
			 }else{
			   RequestContext.getCurrentInstance().execute("remarksEmpty.show();");    
			 }
		  }
	    public void activateRecord(){
			    if(lstTestkeyParameterForBankDt.size()>0){
				      for (TestkeyParameterForBankDataTable testkeyParameterForBankDataTable : lstTestkeyParameterForBankDt) {
					if(testkeyParameterForBankDataTable.getActiveRecordCheck()!=null){
						  if(testkeyParameterForBankDataTable.getActiveRecordCheck().equals(true)){
							    conformToDeActivteTestKeyMaster(testkeyParameterForBankDataTable);    
						  }
					}
				      }
			    }
		  }
	    public void conformToDeActivteTestKeyMaster(TestkeyParameterForBankDataTable dataTable){
			    testKeyForParameterForBankService.deActivateRecord(dataTable.getTestKeyParameterPk(),sessionStateManage.getUserName());
			    viewAllTestKeyBank();
		  }
	    public void cancelRemarks() {
			setRemarks(null);
			setBooApproval(false);
			setBooRead(false);
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../testKey/ParametersForBank.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
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
	    //Approval Started
	    public void testKeyMasterApprovalPageNavigation(){
			    setBooAdd(false);
			   setBooApproval(false);
			    setBooSaveOrExit(false);
			    setBooRenderDataTable(true);
			    setBooSubmitPanel(true);
			    setBooClearPanel(true);
			    setBooRead(false);
			    popUpCorespondingBank();
			    lstTestkeyParameterForBankDt.clear();
			    lstTestkeyParameterForNewBankDt.clear();
			    fetchRecordforTestKeyMasterApproval();
			    try {
			    	loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "TestkeyMasterApproval.xhtml");
				      FacesContext.getCurrentInstance().getExternalContext().redirect("../testKey/TestkeyMasterApproval.xhtml");      
			    } catch (Exception e) {
				 e.printStackTrace();
			    }
		  }
	    
	    public void fetchRecordforTestKeyMasterApproval(){
		      lstTestkeyParameterForBankDt.clear();
			    lstTestkeyParameterForNewBankDt.clear();
			    try{
			    List<TestKeyMaster> testKeyMasterList=testKeyForParameterForBankService.toApprovalForAllrecordsTofetchDb();
			      if(testKeyMasterList.size()>0){
					 for (TestKeyMaster testKeyMaster : testKeyMasterList) {
						     TestkeyParameterForBankDataTable testBankDataTable=new TestkeyParameterForBankDataTable();
						     testBankDataTable.setApplicationCountryId(testKeyMaster.getFsCountryMaster().getCountryId());
						     testBankDataTable.setBankId(testKeyMaster.getExBankMaster().getBankId());
						     testBankDataTable.setBankName(bankMasterListMap.get(testKeyMaster.getExBankMaster().getBankId()));
						     testBankDataTable.setBankDescription(bankMasterListMap.get(testKeyMaster.getExBankMaster().getBankId()));
						     BigDecimal account = testKeyForParameterForBankService.tofetchAccountId(testKeyMaster.getBankAccountNumber(),testKeyMaster.getExBankMaster().getBankId());
						     if(account !=null){
						      testBankDataTable.setAccountNo(account.toPlainString());
						     }
						     testBankDataTable.setAccountNumber(testKeyMaster.getBankAccountNumber());
						     testBankDataTable.setAccountDescription(testKeyMaster.getAccountDesc());
						     testBankDataTable.setBranchCode(testKeyMaster.getExBankBranch().getBankBranchId());
						     testBankDataTable.setBranchName(testKeyForParameterForBankService.toFetchBankBranch(testKeyMaster.getExBankBranch().getBankBranchId()));
						     testBankDataTable.setCurrencyId(testKeyMaster.getExCurrencyMaster().getCurrencyId());
						     //testBankDataTable.setCurrencyName(lstBankAccountDetailsCurrencyMap.get(testKeyMaster.getExCurrencyMaster().getCurrencyId()));
						     testBankDataTable.setCurrencyName(generalService.getCurrencyName(testKeyMaster.getExCurrencyMaster().getCurrencyId()));
						     testBankDataTable.setSeconaryIndicator(testKeyMaster.getSerialIndicator());
						     testBankDataTable.setPrimaryTestKey(testKeyMaster.getPrimaryTestKeyCode());
						     testBankDataTable.setPrimaryTestKeyName(testKeyForParameterForBankService.toFetchParameterTestKeyName(testKeyMaster.getPrimaryTestKeyCode()));
						     testBankDataTable.setSecondaryTestKey(testKeyMaster.getSecondaryTestKeyCode());
						     testBankDataTable.setSecondaryTestKeyParameterName(testKeyForParameterForBankService.toFetchParameterTestKeyName(testKeyMaster.getPrimaryTestKeyCode()));
						     testBankDataTable.setTestKeyParameterPk(testKeyMaster.getTestKeyMasterId());
						     testBankDataTable.setCalculationOrderNo(testKeyMaster.getCalculationOrederNo());
						     // testBankDataTable.setSerialIndicator(testKeyMaster.getSerialIndicator());
						    // testBankDataTable.setEndOfSerial(testKeyMaster.getSrRule());
						     if(testKeyMaster.getSendReceieveIndicator()!=null){
						     if(testKeyMaster.getSendReceieveIndicator().equalsIgnoreCase("S")){
							       testBankDataTable.setSendReceiveIndicator("Send");     
						     }else{
							       testBankDataTable.setSendReceiveIndicator("Receieve");
						     }
						     }
						     if(testKeyMaster.getCalculationType()!=null){
						     if(testKeyMaster.getCalculationType().equalsIgnoreCase("M")){
							       testBankDataTable.setCalculationType("Multiplication");
						      }else{
								testBankDataTable.setCalculationType("Add");	
						      }
						     }
						     if(testKeyMaster.getSerialIndicator()!=null){
						     if(testKeyMaster.getSerialIndicator().equalsIgnoreCase("D")){
							       testBankDataTable.setSerialIndicator("Daily");
								      }else if (testKeyMaster.getSerialIndicator().equalsIgnoreCase("W")) {
										testBankDataTable.setSerialIndicator("Weekly");      
								      }else if (testKeyMaster.getSerialIndicator().equalsIgnoreCase("M")) {
										testBankDataTable.setSerialIndicator("Monthly");      
								      }
								      else if (testKeyMaster.getSerialIndicator().equalsIgnoreCase("Cont")) {
										testBankDataTable.setSerialIndicator("Cont");      
								      }else{
										testBankDataTable.setSerialIndicator(" ");  	
								      }
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
							       testBankDataTable.setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE); 
						     }else if (testBankDataTable.getIsActive().equalsIgnoreCase(Constants.D)) {
							       testBankDataTable.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
							    }else if (testBankDataTable.getIsActive().equalsIgnoreCase(Constants.U)&&testBankDataTable.getModifiedBy()==null&&testBankDataTable.getModifiedDate()==null&&testBankDataTable.getApprovedBy()==null&&testBankDataTable.getApprovedDate()==null&&testBankDataTable.getRemarks()==null) {
								      testBankDataTable.setDynamicLabelForActivateDeactivate(Constants.DELETE);
							    }else{
								      testBankDataTable.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);      
							    }
						    lstTestkeyParameterForBankDt.add(testBankDataTable); 
					    }
			      }
			    }catch(NullPointerException ne){
			    	log.info("Method Name::parameterForBankPageNavigation"+ne.getMessage());
					  setErrorMessage("Method Name::parameterForBankPageNavigation"); 
					    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
					    return; 
				} catch (Exception exception) {
					log.info("Method Name::parameterForBankPageNavigation"+exception.getMessage());
					setErrorMessage(exception.getMessage());
					RequestContext.getCurrentInstance().execute("exception.show();");
					return;
				}
			      }
					 
			     
	    public void approvelCheckForTestKeyMasterUser(TestkeyParameterForBankDataTable dataTable){
			    if(!(dataTable.getModifiedBy()==null?  dataTable.getCreatedBy():dataTable.getModifiedBy()).equalsIgnoreCase(sessionStateManage.getUserName()))
				{
				      //setApplicationCountryId(dataTable.getApplicationCountryId());
				      setBankId(dataTable.getBankId());
				      setBankDescription(dataTable.getBankDescription());
				      setAccountNumber(dataTable.getAccountNumber());
				      populateAccountNumbers();
				      setAccountNo(testKeyForParameterForBankService.tofetchAccountId(dataTable.getAccountNumber(),dataTable.getBankId()).toPlainString());
				      setAccountDescription(dataTable.getAccountDescription());
				      populatecurrency();
				      setCurrencyId(dataTable.getCurrencyId());
				      setCurrencyName(dataTable.getCurrencyName());
				      populateBranch();
				      setBranchCode(dataTable.getBranchCode());
				      setBranchName(dataTable.getBranchName());
				      fetchPrimaryParameters();
				      setPrimaryTestKey(dataTable.getPrimaryTestKey());
				      setPrimaryTestKeyName(dataTable.getPrimaryTestKeyName());
				      setSecondaryTestKey(dataTable.getSecondaryTestKey());
				      setSecondaryTestKeyParameterName(dataTable.getSecondaryTestKeyParameterName());
				      setTestKeyParameterPk(dataTable.getTestKeyParameterPk());
				      if(dataTable.getSendReceiveIndicator()!=null){
				      if(dataTable.getSendReceiveIndicator().equalsIgnoreCase("Send")){
						      setSendReceiveIndicator("S");     
					     }else{
						      setSendReceiveIndicator("R");
					     }
				      }
				      if(dataTable.getCalculationType()!=null){
					     if(dataTable.getCalculationType().equalsIgnoreCase("Multiplication")){
						       setCalculationType("1");
					      }else{
							setCalculationType("2");	
					      }
				      }
					     if(dataTable.getSerialIndicator() != null && dataTable.getEndOfSerial() != null){
						       setBooseconaryIndicator(true);
					     if(dataTable.getSerialIndicator().equalsIgnoreCase("Daily")){
						       		setSeconaryIndicator("D");
							      }else if (dataTable.getSerialIndicator().equalsIgnoreCase("Weekly")) {
									setSeconaryIndicator("W");      
							      }else if (dataTable.getSerialIndicator().equalsIgnoreCase("Monthly")) {
									setSeconaryIndicator("M");      
							      }
							      else if (dataTable.getSerialIndicator().equalsIgnoreCase("Cont")) {
									setSeconaryIndicator("C");      
							      }else{
									setSeconaryIndicator(" ");  	
							      }
					     		if(dataTable.getEndOfSerial() !=null){
							      if(dataTable.getEndOfSerial().equalsIgnoreCase("New")){
									setEndOfSerial("N");
							      }else if (dataTable.getEndOfSerial().equalsIgnoreCase("Reuse")){
									setEndOfSerial("R");	
							      }
					     		}else{
								setEndOfSerial("");
							      }
					     }else{
						       setEndOfSerial("");
						       setSeconaryIndicator("");
						       setBooendOfSerial(true);
					     }
					     		setCreatedBy(dataTable.getCreatedBy());
					     		setCreatedDate(dataTable.getCreatedDate());
					     		setModifiedBy(dataTable.getModifiedBy());
					     		setModifiedDate(dataTable.getModifiedDate());
					     		setIsActive(dataTable.getIsActive());
				      setBooRenderDataTable(false);
					  setBooSaveOrExit(false);
					  setBooAdd(false);
					  setBooApproval(true);
					  setBooRead(true);
				      try {
						    FacesContext.getCurrentInstance().getExternalContext().redirect("../testKey/ParametersForBank.xhtml"); 	
						} catch (Exception e) {
							e.printStackTrace();
						}     
				}else{
						RequestContext.getCurrentInstance().execute("notApproved.show();");
				}
				}
	    public void testKeyMasterApproveRecord(){
			  String approvalMsg = testKeyForParameterForBankService.checkTestKeyMasterApproveMultiUser(getTestKeyParameterPk(),sessionStateManage.getUserName());
			  if(approvalMsg.equalsIgnoreCase("Success")){
					RequestContext.getCurrentInstance().execute("approve.show();");
				 }else{
					 RequestContext.getCurrentInstance().execute("alreadyapprov.show();");
					return; 
				 }
		}
	    public void clickOnOKApprove(){
			  clearAllFields();
			  setBooAdd(false);
			  setBooRenderDataTable(false);
			  setBooSaveOrExit(false);
			  setBooApproval(false);
			  setBooRead(false);
			    try {
				      fetchRecordforTestKeyMasterApproval();
				      FacesContext.getCurrentInstance().getExternalContext().redirect("../testKey/TestkeyMasterApproval.xhtml");      
			    } catch (Exception e) {
				 e.printStackTrace();
			    }
		}
		public void clickOnOkButton(){
			  lstTestkeyParameterForBankDt.clear();
			  fetchRecordforTestKeyMasterApproval();
			  try {
				      FacesContext.getCurrentInstance().getExternalContext().redirect("../testKey/TestkeyMasterApproval.xhtml");      
			    } catch (Exception e) {
				 e.printStackTrace();
			    }
		}
		public void testKeyMasterApprovedByOhterPerson(){
			  fetchRecordforTestKeyMasterApproval();
			  try {
				      FacesContext.getCurrentInstance().getExternalContext().redirect("../testKey/TestkeyMasterApproval.xhtml");      
			    } catch (Exception e) {
				 e.printStackTrace();
			    }  
		}
		public void testKeyMasterCancel(){
			  
			  try {
				  fetchRecordforTestKeyMasterApproval();
				      FacesContext.getCurrentInstance().getExternalContext().redirect("../testKey/TestkeyMasterApproval.xhtml");      
			    /*} catch (Exception e) {
				 e.printStackTrace();
			    } */
			  }catch(NullPointerException ne){
			    	log.info("Method Name::parameterForBankPageNavigation"+ne.getMessage());
					  setErrorMessage("Method Name::parameterForBankPageNavigation"); 
					    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
					    return; 
				} catch (Exception exception) {
					log.info("Method Name::parameterForBankPageNavigation"+exception.getMessage());
					setErrorMessage(exception.getMessage());
					RequestContext.getCurrentInstance().execute("exception.show();");
					return;
				}
		}
	  /*Approval Ended */
		 public void disableSubmit(){
			    setBooSubmitPanel(true);
		  }
}
