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

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.BankAccountTypeDesc;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.testkey.model.TestKeyMaster;
import com.amg.exchange.testkey.model.TestKeyValues;
import com.amg.exchange.testkey.service.ITestKeyForParameterForBankService;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("testKeyValuesForBankBean")
@Scope("session")
public class TestKeyValuesForBankBean<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(TestKeyValuesForBankBean.class);

	SessionStateManage sessionStateManage = new SessionStateManage();

	//AutoWired Started
	@Autowired
	ITestKeyForParameterForBankService iTestKeyForParameterForBankService;

	@Autowired
	IGeneralService<T> iGeneralService;

	private BigDecimal testKeyValueId;
	private BigDecimal applicationCountryId;
	private BigDecimal bankId;
	private String bankCode;
	private String bankDescription;
	private BigDecimal bankbranchId;
	private BigDecimal bankbranchCode;
	private String bankbranchDescription;
	private BigDecimal accountNoId;
	private String accountNo;
	private String accountDescription;
	private BigDecimal currencyId;
	private String currencyCode;
	private String currencyName;
	private String sendReceiveIndicator;
	private BigDecimal calculationOrderNo;
	private String primaryTestKeyCode;
	private BigDecimal primaryTestKeyValue;
	private BigDecimal secondaryTestKeyCode;
	private BigDecimal secondaryTestKeyCodeValue;
	private String status;
	private BigDecimal keyValue;

	private String isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;

	private boolean booRenderDataTable = false;
	private boolean booRenderApproval = false;
	private boolean booApprovalPanel = false;
	private boolean booAddPanel = false;
	private boolean booDisableButton = false;
	private String errorMessage;



	public BigDecimal getTestKeyValueId() {
		return testKeyValueId;
	}
	public void setTestKeyValueId(BigDecimal testKeyValueId) {
		this.testKeyValueId = testKeyValueId;
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
	public void setBankDescription(String bankDescription) {
		this.bankDescription = bankDescription;
	}

	public BigDecimal getBankbranchId() {
		return bankbranchId;
	}
	public void setBankbranchId(BigDecimal bankbranchId) {
		this.bankbranchId = bankbranchId;
	}

	public BigDecimal getBankbranchCode() {
		return bankbranchCode;
	}
	public void setBankbranchCode(BigDecimal bankbranchCode) {
		this.bankbranchCode = bankbranchCode;
	}

	public String getBankbranchDescription() {
		return bankbranchDescription;
	}
	public void setBankbranchDescription(String bankbranchDescription) {
		this.bankbranchDescription = bankbranchDescription;
	}

	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
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

	public String getPrimaryTestKeyCode() {
		return primaryTestKeyCode;
	}
	public void setPrimaryTestKeyCode(String primaryTestKeyCode) {
		this.primaryTestKeyCode = primaryTestKeyCode;
	}

	public BigDecimal getPrimaryTestKeyValue() {
		return primaryTestKeyValue;
	}
	public void setPrimaryTestKeyValue(BigDecimal primaryTestKeyValue) {
		this.primaryTestKeyValue = primaryTestKeyValue;
	}

	public BigDecimal getSecondaryTestKeyCode() {
		return secondaryTestKeyCode;
	}
	public void setSecondaryTestKeyCode(BigDecimal secondaryTestKeyCode) {
		this.secondaryTestKeyCode = secondaryTestKeyCode;
	}

	public BigDecimal getSecondaryTestKeyCodeValue() {
		return secondaryTestKeyCodeValue;
	}
	public void setSecondaryTestKeyCodeValue(BigDecimal secondaryTestKeyCodeValue) {
		this.secondaryTestKeyCodeValue = secondaryTestKeyCodeValue;
	}

	public BigDecimal getKeyValue() {
		return keyValue;
	}
	public void setKeyValue(BigDecimal keyValue) {
		this.keyValue = keyValue;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getAccountDescription() {
		return accountDescription;
	}
	public void setAccountDescription(String accountDescription) {
		this.accountDescription = accountDescription;
	}

	public BigDecimal getAccountNoId() {
		return accountNoId;
	}
	public void setAccountNoId(BigDecimal accountNoId) {
		this.accountNoId = accountNoId;
	}

	public BigDecimal getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}


	// for extra variable

	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getCreatedBy() {
		return createdBy;
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

	public boolean isBooRenderDataTable() {
		return booRenderDataTable;
	}
	public void setBooRenderDataTable(boolean booRenderDataTable) {
		this.booRenderDataTable = booRenderDataTable;
	}

	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public boolean isBooRenderApproval() {
		return booRenderApproval;
	}
	public void setBooRenderApproval(boolean booRenderApproval) {
		this.booRenderApproval = booRenderApproval;
	}

	public boolean isBooApprovalPanel() {
		return booApprovalPanel;
	}
	public void setBooApprovalPanel(boolean booApprovalPanel) {
		this.booApprovalPanel = booApprovalPanel;
	}

	public boolean isBooAddPanel() {
		return booAddPanel;
	}
	public void setBooAddPanel(boolean booAddPanel) {
		this.booAddPanel = booAddPanel;
	}

	public boolean isBooDisableButton() {
		return booDisableButton;
	}
	public void setBooDisableButton(boolean booDisableButton) {
		this.booDisableButton = booDisableButton;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}



	// All list Available
	private Map<BigDecimal, String> bankMasterFullNameListMap = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> bankMasterCodeListMap = new HashMap<BigDecimal, String>();
	private List<BankApplicability> correspondingBankMasterList = new ArrayList<BankApplicability>();
	private List<BankBranch> bankbranchList = new ArrayList<BankBranch>();
	private Map<BigDecimal, String> lstBankAccountTypeDescMap = new HashMap<BigDecimal, String>();
	private Map<String, String> lstBankAccountAndAccDesc = new HashMap<String, String>();
	private List<TestKeyValueAccNumDesc> lstBankAccount = new ArrayList<TestKeyValueAccNumDesc>();
	private Map<BigDecimal, String> bankbranchFullNameListMap = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, BigDecimal> bankbranchCodeListMap = new HashMap<BigDecimal, BigDecimal>();
	private List<BankAccountDetails> bankCurrencylist = new ArrayList<BankAccountDetails>();
	private List<TestKeyValuesForDataTable> addlstTestKeyValue = new CopyOnWriteArrayList<TestKeyValuesForDataTable>();
	private List<TestKeyValuesForDataTable> lstTestKeyValue = new CopyOnWriteArrayList<TestKeyValuesForDataTable>();
	private Map<BigDecimal, String> bankCurrencyFullNameListMap = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> bankCurrencyCodeListMap = new HashMap<BigDecimal, String>();
	private List<TestKeyValues> lstSaveTestKeyValue = new ArrayList<TestKeyValues>();
	private List<TestKeyMaster> primaryParameterList = new ArrayList<TestKeyMaster>();
	private List<TestKeyValuesForDataTable> lstTestKeyValueforApproval = new ArrayList<TestKeyValuesForDataTable>();


	public Map<BigDecimal, String> getBankCurrencyFullNameListMap() {
		return bankCurrencyFullNameListMap;
	}
	public void setBankCurrencyFullNameListMap(Map<BigDecimal, String> bankCurrencyFullNameListMap) {
		this.bankCurrencyFullNameListMap = bankCurrencyFullNameListMap;
	}

	public Map<BigDecimal, String> getBankCurrencyCodeListMap() {
		return bankCurrencyCodeListMap;
	}
	public void setBankCurrencyCodeListMap(Map<BigDecimal, String> bankCurrencyCodeListMap) {
		this.bankCurrencyCodeListMap = bankCurrencyCodeListMap;
	}

	public Map<BigDecimal, String> getBankMasterFullNameListMap() {
		return bankMasterFullNameListMap;
	}
	public void setBankMasterFullNameListMap(Map<BigDecimal, String> bankMasterFullNameListMap) {
		this.bankMasterFullNameListMap = bankMasterFullNameListMap;
	}

	public Map<BigDecimal, String> getBankMasterCodeListMap() {
		return bankMasterCodeListMap;
	}
	public void setBankMasterCodeListMap(Map<BigDecimal, String> bankMasterCodeListMap) {
		this.bankMasterCodeListMap = bankMasterCodeListMap;
	}

	public List<BankApplicability> getCorrespondingBankMasterList() {
		return correspondingBankMasterList;
	}
	public void setCorrespondingBankMasterList(List<BankApplicability> correspondingBankMasterList) {
		this.correspondingBankMasterList = correspondingBankMasterList;
	}

	public Map<BigDecimal, String> getBankbranchFullNameListMap() {
		return bankbranchFullNameListMap;
	}
	public void setBankbranchFullNameListMap(Map<BigDecimal, String> bankbranchFullNameListMap) {
		this.bankbranchFullNameListMap = bankbranchFullNameListMap;
	}

	public Map<BigDecimal, BigDecimal> getBankbranchCodeListMap() {
		return bankbranchCodeListMap;
	}
	public void setBankbranchCodeListMap(Map<BigDecimal, BigDecimal> bankbranchCodeListMap) {
		this.bankbranchCodeListMap = bankbranchCodeListMap;
	}

	public Map<BigDecimal, String> getLstBankAccountTypeDescMap() {
		return lstBankAccountTypeDescMap;
	}
	public void setLstBankAccountTypeDescMap(Map<BigDecimal, String> lstBankAccountTypeDescMap) {
		this.lstBankAccountTypeDescMap = lstBankAccountTypeDescMap;
	}

	public Map<String, String> getLstBankAccountAndAccDesc() {
		return lstBankAccountAndAccDesc;
	}
	public void setLstBankAccountAndAccDesc(Map<String, String> lstBankAccountAndAccDesc) {
		this.lstBankAccountAndAccDesc = lstBankAccountAndAccDesc;
	}

	public List<TestKeyValueAccNumDesc> getLstBankAccount() {
		return lstBankAccount;
	}
	public void setLstBankAccount(List<TestKeyValueAccNumDesc> lstBankAccount) {
		this.lstBankAccount = lstBankAccount;
	}

	public List<BankBranch> getBankbranchList() {
		return bankbranchList;
	}
	public void setBankbranchList(List<BankBranch> bankbranchList) {
		this.bankbranchList = bankbranchList;
	}

	public List<BankAccountDetails> getBankCurrencylist() {
		return bankCurrencylist;
	}
	public void setBankCurrencylist(List<BankAccountDetails> bankCurrencylist) {
		this.bankCurrencylist = bankCurrencylist;
	}	

	public List<TestKeyValuesForDataTable> getLstTestKeyValue() {
		return lstTestKeyValue;
	}
	public void setLstTestKeyValue(List<TestKeyValuesForDataTable> lstTestKeyValue) {
		this.lstTestKeyValue = lstTestKeyValue;
	}

	public List<TestKeyValues> getLstSaveTestKeyValue() {
		return lstSaveTestKeyValue;
	}
	public void setLstSaveTestKeyValue(List<TestKeyValues> lstSaveTestKeyValue) {
		this.lstSaveTestKeyValue = lstSaveTestKeyValue;
	}

	public List<TestKeyValuesForDataTable> getAddlstTestKeyValue() {
		return addlstTestKeyValue;
	}
	public void setAddlstTestKeyValue(List<TestKeyValuesForDataTable> addlstTestKeyValue) {
		this.addlstTestKeyValue = addlstTestKeyValue;
	}

	public List<TestKeyMaster> getPrimaryParameterList() {
		return primaryParameterList;
	}
	public void setPrimaryParameterList(List<TestKeyMaster> primaryParameterList) {
		this.primaryParameterList = primaryParameterList;
	}

	public List<TestKeyValuesForDataTable> getLstTestKeyValueforApproval() {
		return lstTestKeyValueforApproval;
	}
	public void setLstTestKeyValueforApproval(List<TestKeyValuesForDataTable> lstTestKeyValueforApproval) {
		this.lstTestKeyValueforApproval = lstTestKeyValueforApproval;
	}


	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	//Page Navigation
	public void testKeyValuesPageNavigation(){
		// clearing all data from form
		clearAll();
		try {
			//for loading to populate CorespondingBank
			popUpCorespondingBank();
			//for loading to populate Bank Account type
			popBankAccountType();
			// add panell
			setBooAddPanel(true);
			//submit panel
			setBooRenderDataTable(false);
			// for making read only while approval 
			setBooRenderApproval(false);
			setBooApprovalPanel(false);

			//try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "TestKeyValuesForBank.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../testKey/TestKeyValuesForBank.xhtml");      
			/*} catch (Exception e) {
			e.printStackTrace();
		}*/
		}catch(NullPointerException ne){
			log.info("Method Name::testKeyValuesPageNavigation"+ne.getMessage());
			setErrorMessage("Method Name::testKeyValuesPageNavigation"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		} catch (Exception exception) {
			log.info("Method Name::testKeyValuesPageNavigation"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}
	}

	//form loading to populate CorespondingBank
	public void popUpCorespondingBank(){
		bankMasterFullNameListMap.clear();
		bankMasterCodeListMap.clear();
		correspondingBankMasterList.clear();
		List<BankApplicability> bankMasterList = iTestKeyForParameterForBankService.toFetchCorpondingAllBank(sessionStateManage.getCountryId());
		if(bankMasterList.size() != 0){
			correspondingBankMasterList.addAll(bankMasterList);
			for (BankApplicability bankApplicability : bankMasterList) {
				bankMasterFullNameListMap.put(bankApplicability.getBankMaster().getBankId(), bankApplicability.getBankMaster().getBankFullName());
				bankMasterCodeListMap.put(bankApplicability.getBankMaster().getBankId(), bankApplicability.getBankMaster().getBankCode());
			}
		}

	}

	public void popBankAccountType(){
		lstBankAccountTypeDescMap.clear();
		setBankDescription(null);
		setBankCode(null);
		List<BankAccountTypeDesc> lstBankAccountType = iTestKeyForParameterForBankService.fetchAccountDescTypeBasedonLanguageId(new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"));
		if(lstBankAccountType.size() != 0){
			for (BankAccountTypeDesc bankAccountTypeDesc : lstBankAccountType) {
				lstBankAccountTypeDescMap.put(bankAccountTypeDesc.getBankAccountTypeId().getBankAccountTypeId(), bankAccountTypeDesc.getBankAccountTypeDesc());
			}
		}else{
			setBankDescription(null);
			setBankCode(null);
		}
	}

	//based on bank to populate Bank Description
	public void populateBankDescription(){
		//clear all in form
		clearAllForBank();
		try{
			setBankDescription(bankMasterFullNameListMap.get(getBankId()));
			setBankCode(bankMasterCodeListMap.get(getBankId()));
			populateAccountNumbers();
			populateBranch();
		}catch(NullPointerException ne){
			log.info("Method Name::populateBankDescription"+ne.getMessage());
			setErrorMessage("Method Name::populateBankDescription"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		} catch (Exception exception) {
			log.info("Method Name::populateBankDescription"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}
	}

	//based on bank to populate Account no
	public void populateAccountNumbers(){
		primaryParameterList.clear();
		setSendReceiveIndicator(null);
		lstBankAccount.clear();
		setAccountNoId(null);
		setAccountNo(null);
		setAccountDescription(null);
		setCurrencyId(null);

		List<BankAccountDetails> lstBankAccountList = iTestKeyForParameterForBankService.toFetchAccountNumberBasedOnBank(getBankId());

		if (lstBankAccountList.size() != 0) {

			for (BankAccountDetails bankAccountDetails : lstBankAccountList) {
				TestKeyValueAccNumDesc lstAccNumDesc = new TestKeyValueAccNumDesc();

				lstAccNumDesc.setAccountNumberId(bankAccountDetails.getBankAcctDetId());
				lstAccNumDesc.setAccountNumber(bankAccountDetails.getBankAcctNo());
				lstAccNumDesc.setAccountNumberDesc(lstBankAccountTypeDescMap.get(bankAccountDetails.getBankAccountType().getBankAccountTypeId()));

				lstBankAccount.add(lstAccNumDesc);
			}

		}

	}

	// fetch account type desc
	public void populateAccountDescription(){
		primaryParameterList.clear();
		setSendReceiveIndicator(null);
		setAccountNo(null);
		setAccountDescription(null);
		if(lstBankAccount.size() != 0){
			for (TestKeyValueAccNumDesc lstAcc : lstBankAccount) {
				if(lstAcc.getAccountNumberId().compareTo(getAccountNoId())==0){
					setAccountNo(lstAcc.getAccountNumber());
					setAccountDescription(lstAcc.getAccountNumberDesc());
				}
			}
		}else{
			setAccountNo(null);
			setAccountDescription(null);
		}

		//populate Currency based on Acc number Id
		populatecurrency();

	}

	//populate based on bank branch
	public void populateBranch(){
		primaryParameterList.clear();
		setSendReceiveIndicator(null);
		bankbranchFullNameListMap.clear();
		bankbranchCodeListMap.clear();
		bankbranchList.clear();
		setBankbranchId(null);
		setBankbranchCode(null);
		setBankbranchDescription(null);
		List<BankBranch> lstbankbranch = iTestKeyForParameterForBankService.toFetchBranchBasedOnBank(getBankId());
		if (lstbankbranch.size() != 0) {
			bankbranchList.addAll(lstbankbranch);
			for (BankBranch bankBranch : bankbranchList) {
				bankbranchFullNameListMap.put(bankBranch.getBankBranchId(), bankBranch.getBranchFullName()); 
				bankbranchCodeListMap.put(bankBranch.getBankBranchId(), bankBranch.getBranchCode());
			}
		}else{
			setBankbranchCode(null);
			setBankbranchDescription(null);
		}

	}

	//based on bank to populate Bank Description
	public void populateBankBranchDescription(){
		primaryParameterList.clear();
		setSendReceiveIndicator(null);
		setBankbranchCode(bankbranchCodeListMap.get(getBankbranchId()));
		setBankbranchDescription(bankbranchFullNameListMap.get(getBankbranchId()));
	}

	//based on Account no to get currency
	public void populatecurrency(){

		bankCurrencylist.clear();
		bankCurrencyCodeListMap.clear();
		bankCurrencyFullNameListMap.clear();
		setCurrencyId(null);
		setCurrencyCode(null);
		setCurrencyName(null);
		try{
			List<BankAccountDetails> lstCurrency = iTestKeyForParameterForBankService.toFetchCurrencyBasedOnAccountNumber(getAccountNoId());

			if (lstCurrency.size() != 0) {
				bankCurrencylist.addAll(lstCurrency);
				for (BankAccountDetails bankCurrency : bankCurrencylist) {
					bankCurrencyFullNameListMap.put(bankCurrency.getFsCurrencyMaster().getCurrencyId(), bankCurrency.getFsCurrencyMaster().getCurrencyName()); 
					bankCurrencyCodeListMap.put(bankCurrency.getFsCurrencyMaster().getCurrencyId(), bankCurrency.getFsCurrencyMaster().getCurrencyCode());
				}
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

	// fetch account type desc
	public void populateCurrencyDescription(){
		setCurrencyCode(null);
		setCurrencyName(null);
		if(bankCurrencylist.size() != 0){
			for (BankAccountDetails bankCurrency : bankCurrencylist) {
				if(bankCurrency.getFsCurrencyMaster().getCurrencyId().compareTo(getCurrencyId())==0){
					setCurrencyCode(bankCurrency.getFsCurrencyMaster().getCurrencyCode());
					setCurrencyName(bankCurrency.getFsCurrencyMaster().getCurrencyName());
				}
			}
		}else{
			setCurrencyCode(null);
			setCurrencyName(null);
		}

	}

	// Add to DataTable onclick Add
	public void addtoDataTable(){

		TestKeyValuesForDataTable lstTestKey = new TestKeyValuesForDataTable();

		lstTestKey.setTestKeyValueId(getTestKeyValueId());
		lstTestKey.setBankId(getBankId());
		lstTestKey.setBankCode(getBankCode());
		lstTestKey.setBankDescription(getBankDescription());
		lstTestKey.setAccountNoId(getAccountNoId());
		lstTestKey.setAccountNo(getAccountNo());
		lstTestKey.setAccountDescription(getAccountDescription());
		lstTestKey.setBankbranchId(getBankbranchId());
		lstTestKey.setBankbranchCode(getBankbranchCode());
		lstTestKey.setBankbranchDescription(getBankbranchDescription());
		lstTestKey.setCurrencyId(getCurrencyId());
		lstTestKey.setCurrencyCode(getCurrencyCode());
		lstTestKey.setCurrencyName(getCurrencyName());
		lstTestKey.setSendReceiveIndicator(getSendReceiveIndicator());
		if(getSendReceiveIndicator().equalsIgnoreCase("S")){
			lstTestKey.setSendReceiveFullName("SEND");
		}else{
			lstTestKey.setSendReceiveFullName("RECEIVE");
		}
		lstTestKey.setCalculationOrderNo(getCalculationOrderNo());
		lstTestKey.setPrimaryTestKeyCode(getPrimaryTestKeyCode());
		lstTestKey.setPrimaryTestKeyValue(getPrimaryTestKeyValue());
		lstTestKey.setSecondaryTestKeyCode(getSecondaryTestKeyCode());
		lstTestKey.setSecondaryTestKeyCodeValue(getSecondaryTestKeyCodeValue());
		lstTestKey.setKeyValue(getKeyValue());
		lstTestKey.setStatus(getStatus());
		if(getStatus() != null){
			if(getStatus().equalsIgnoreCase("U")){
				lstTestKey.setStatusFullName("USED");
			}else if(getStatus().equalsIgnoreCase("N")){
				lstTestKey.setStatusFullName("NEW");
			}else if(getStatus().equalsIgnoreCase("M")){
				lstTestKey.setStatusFullName("MANUAL USED");
			}else{
				lstTestKey.setStatusFullName("----");
			}
		}else{
			lstTestKey.setStatusFullName("----");
		}

		lstTestKey.setIsActive(Constants.U);
		lstTestKey.setIsActiveStatus(Constants.REMOVE);
		lstTestKey.setCreatedBy(sessionStateManage.getUserName());
		lstTestKey.setCreatedDate(new Date());    	

		addlstTestKeyValue.add(lstTestKey);

		if(addlstTestKeyValue.size() != 0){
			setBooRenderDataTable(true);
			lstTestKeyValue.add(lstTestKey);
		}else{
			setBooRenderDataTable(false);
		}

		// submit , view , clear open
		setBooDisableButton(false);
		//clear all records in Forms
		clearFormFields();

	}

	//edit from dataTable to Form
	public void edit(TestKeyValuesForDataTable editTestKeyValue){
		try{
			// submit , view , clear close
			setBooDisableButton(true);

			setTestKeyValueId(editTestKeyValue.getTestKeyValueId());
			setBankId(editTestKeyValue.getBankId());
			setBankCode(editTestKeyValue.getBankCode());
			setBankDescription(editTestKeyValue.getBankDescription());

			List<BankAccountDetails> lstBankAccountList = iTestKeyForParameterForBankService.toFetchAccountNumberBasedOnBank(getBankId());
			if(lstBankAccountList.size() != 0){
				for (BankAccountDetails bankAccountDetails : lstBankAccountList) {
					TestKeyValueAccNumDesc lstAccNumDesc = new TestKeyValueAccNumDesc();

					lstAccNumDesc.setAccountNumberId(bankAccountDetails.getBankAcctDetId());
					lstAccNumDesc.setAccountNumber(bankAccountDetails.getBankAcctNo());
					lstAccNumDesc.setAccountNumberDesc(lstBankAccountTypeDescMap.get(bankAccountDetails.getBankAccountType().getBankAccountTypeId()));

					lstBankAccount.add(lstAccNumDesc);
				}
			}

			setAccountNoId(editTestKeyValue.getAccountNoId());
			setAccountNo(editTestKeyValue.getAccountNo());
			setAccountDescription(editTestKeyValue.getAccountDescription());

			List<BankBranch> lstbankbranch = iTestKeyForParameterForBankService.toFetchBranchBasedOnBank(getBankId());
			if(lstbankbranch.size() != 0){
				bankbranchList.addAll(lstbankbranch);
			}

			setBankbranchId(editTestKeyValue.getBankbranchId());
			setBankbranchCode(editTestKeyValue.getBankbranchCode());
			setBankbranchDescription(editTestKeyValue.getBankbranchDescription());

			List<BankAccountDetails> lstCurrency = iTestKeyForParameterForBankService.toFetchCurrencyBasedOnAccountNumber(getAccountNoId());
			if(lstCurrency.size() != 0){
				bankCurrencylist.addAll(lstCurrency);
			}

			setCurrencyId(editTestKeyValue.getCurrencyId());
			setCurrencyCode(editTestKeyValue.getCurrencyCode());
			setCurrencyName(editTestKeyValue.getCurrencyName());
			setSendReceiveIndicator(editTestKeyValue.getSendReceiveIndicator());
			setCalculationOrderNo(editTestKeyValue.getCalculationOrderNo());
			fetchingRecordsTestKeyMaster();
			setPrimaryTestKeyCode(editTestKeyValue.getPrimaryTestKeyCode());
			setPrimaryTestKeyValue(editTestKeyValue.getPrimaryTestKeyValue());
			setSecondaryTestKeyCode(editTestKeyValue.getSecondaryTestKeyCode());
			setSecondaryTestKeyCodeValue(editTestKeyValue.getSecondaryTestKeyCodeValue());
			setKeyValue(editTestKeyValue.getKeyValue());
			setStatus(editTestKeyValue.getStatus());

			addlstTestKeyValue.remove(editTestKeyValue);
			lstTestKeyValue.remove(editTestKeyValue);

			if(lstTestKeyValue.size() != 0){
				setBooRenderDataTable(true);
			}else{
				setBooRenderDataTable(false);
			}
		}catch(NullPointerException ne){
			log.info("Method Name::edit"+ne.getMessage());
			setErrorMessage("Method Name::edit"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		} catch (Exception exception) {
			log.info("Method Name::edit"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}

	}

	// Negative Value checking
	public void negativeValueChecking(FacesContext context, UIComponent component,Object value) {
		BigDecimal negativeValue = (BigDecimal) value;

		if (negativeValue.intValue() <= 0) {
			FacesMessage msg = new FacesMessage("Please Enter Only Positive Values, Zero Not Allowed","Please Enter Only Positive Values, Zero Not Allowed");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
	}

	//Clear All fields in form
	public void clearAll(){

		setTestKeyValueId(null);
		setBankId(null);
		setBankCode(null);
		setBankDescription(null);
		setAccountNoId(null);
		setAccountNo(null);
		setAccountDescription(null);
		setBankbranchId(null);
		setBankbranchCode(null);
		setBankbranchDescription(null);
		setCurrencyId(null);
		setCurrencyCode(null);
		setCurrencyName(null);
		setSendReceiveIndicator(null);
		setCalculationOrderNo(null);
		setPrimaryTestKeyCode(null);
		setPrimaryTestKeyValue(null);
		setSecondaryTestKeyCode(null);
		setSecondaryTestKeyCodeValue(null);
		setKeyValue(null);
		setStatus(null);
		// form list
		lstBankAccount.clear();
		bankbranchList.clear();
		bankCurrencylist.clear();
		primaryParameterList.clear();

		//save list clear
		lstSaveTestKeyValue.clear();
		//dataTable list clear
		lstTestKeyValue.clear();
		//add dataTable list clear
		addlstTestKeyValue.clear();

	}

	//Clear only Form Fields
	public void clearFormFields(){

		setTestKeyValueId(null);
		setBankId(null);
		setBankCode(null);
		setBankDescription(null);
		setAccountNoId(null);
		setAccountNo(null);
		setAccountDescription(null);
		setBankbranchId(null);
		setBankbranchCode(null);
		setBankbranchDescription(null);
		setCurrencyId(null);
		setCurrencyCode(null);
		setCurrencyName(null);
		setSendReceiveIndicator(null);
		setCalculationOrderNo(null);
		setPrimaryTestKeyCode(null);
		setPrimaryTestKeyValue(null);
		setSecondaryTestKeyCode(null);
		setSecondaryTestKeyCodeValue(null);
		setKeyValue(null);
		setStatus(null);
		// form list
		lstBankAccount.clear();
		bankbranchList.clear();
		bankCurrencylist.clear();
		primaryParameterList.clear();

	}

	//Clear only Form Fields
	public void clearAllForBank(){

		setTestKeyValueId(null);
		setAccountNoId(null);
		setAccountNo(null);
		setAccountDescription(null);
		setBankbranchId(null);
		setBankbranchCode(null);
		setBankbranchDescription(null);
		setCurrencyId(null);
		setCurrencyCode(null);
		setCurrencyName(null);
		setSendReceiveIndicator(null);
		setCalculationOrderNo(null);
		setPrimaryTestKeyCode(null);
		setPrimaryTestKeyValue(null);
		setSecondaryTestKeyCode(null);
		setSecondaryTestKeyCodeValue(null);
		setKeyValue(null);
		setStatus(null);
		// form list
		lstBankAccount.clear();
		bankbranchList.clear();
		bankCurrencylist.clear();
		primaryParameterList.clear();

	}

	// Final save into TestKeyValues
	public void saveTestKeyValue(){
		try{
			if(lstTestKeyValue.size() != 0){

				for (TestKeyValuesForDataTable lsttestkey : lstTestKeyValue) {

					TestKeyValues testKeyValues = new TestKeyValues();

					if(lsttestkey.getTestKeyValueId() != null){
						testKeyValues.setTestKeyValuesId(lsttestkey.getTestKeyValueId());
					}

					CountryMaster applicationCountryMaster = new CountryMaster();
					applicationCountryMaster.setCountryId(sessionStateManage.getCountryId());
					testKeyValues.setFsApplicationCountryId(applicationCountryMaster);

					BankMaster bankMaster = new BankMaster();
					bankMaster.setBankId(lsttestkey.getBankId());
					testKeyValues.setExbankMasterId(bankMaster);

					testKeyValues.setBankCode(lsttestkey.getBankCode());
					testKeyValues.setBankAccountNumber(lsttestkey.getAccountNo());

					BankBranch bankBranch = new BankBranch();
					bankBranch.setBankBranchId(lsttestkey.getBankbranchId());
					testKeyValues.setExBankBranchId(bankBranch);

					testKeyValues.setBankBranchCode(lsttestkey.getBankbranchCode());
					testKeyValues.setSendReceiveIndicator(lsttestkey.getSendReceiveIndicator());
					testKeyValues.setCalculationOrderNo(lsttestkey.getCalculationOrderNo());
					testKeyValues.setPrimaryTestKeyCode(lsttestkey.getPrimaryTestKeyCode());
					testKeyValues.setPrimaryTestKeyValue(lsttestkey.getPrimaryTestKeyValue());
					if(lsttestkey.getSecondaryTestKeyCode()!=null){
					testKeyValues.setSecondaryTestKeyCode(lsttestkey.getSecondaryTestKeyCode().toString());
					}
					testKeyValues.setSecondaryTestKeyValue(lsttestkey.getSecondaryTestKeyCodeValue());
					testKeyValues.setKeyValue(lsttestkey.getKeyValue());
					testKeyValues.setBankAccountDesc(lsttestkey.getAccountDescription());
					testKeyValues.setCurrencyName(lsttestkey.getCurrencyName());
					testKeyValues.setStatus(lsttestkey.getStatus());

					testKeyValues.setIsActive(lsttestkey.getIsActive());

					if(lsttestkey.getTestKeyValueId() != null){
						testKeyValues.setModifiedBy(sessionStateManage.getUserName());
						testKeyValues.setModifiedDate(new Date());
						testKeyValues.setCreatedBy(lsttestkey.getCreatedBy());
						testKeyValues.setCreatedDate(lsttestkey.getCreatedDate());
						testKeyValues.setApprovedBy(lsttestkey.getApprovedBy());
						testKeyValues.setApprovedDate(lsttestkey.getApprovedDate());
					}else{
						testKeyValues.setCreatedBy(sessionStateManage.getUserName());
						testKeyValues.setCreatedDate(new Date());
					}

					lstSaveTestKeyValue.add(testKeyValues);

				}

				boolean savestatus = iTestKeyForParameterForBankService.saveTestKeyValues(lstSaveTestKeyValue);

				if(savestatus){
					//clear all records
					clearAll();
					//save list clear
					lstSaveTestKeyValue.clear();
					//dataTable list clear
					lstTestKeyValue.clear();
					//add dataTable list clear
					addlstTestKeyValue.clear();
					//data table and submit button off
					setBooRenderDataTable(false);
					//dialogue if success
					RequestContext.getCurrentInstance().execute("complete.show();");
				}else{
					RequestContext.getCurrentInstance().execute("errorwhilesave.show();");
				}

			}
		}catch(NullPointerException ne){
			log.info("Method Name::saveTestKeyValue"+ne.getMessage());
			setErrorMessage("Method Name::saveTestKeyValue"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		} catch (Exception exception) {
			exception.printStackTrace();
			log.info("Method Name::saveTestKeyValue"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}

	}

	//after save again redirecting same page
	public void clickOnOKSave(){
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../testKey/TestKeyValuesForBank.xhtml");   
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// exit from the form
	public void exit(){
		if (sessionStateManage.getRoleId().equalsIgnoreCase("1")) {
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void viewAll(){

		lstTestKeyValue.clear();
		try{
			List<TestKeyValues> lstTestKeyValuesDBRecords = iTestKeyForParameterForBankService.viewAllTestKeyValues();

			if(lstTestKeyValuesDBRecords.size() != 0){

				for (TestKeyValues testKeyValues : lstTestKeyValuesDBRecords) {

					TestKeyValuesForDataTable lstTestKey = new TestKeyValuesForDataTable();

					lstTestKey.setTestKeyValueId(testKeyValues.getTestKeyValuesId());
					lstTestKey.setBankId(testKeyValues.getExbankMasterId().getBankId());
					lstTestKey.setBankCode(testKeyValues.getBankCode());
					lstTestKey.setBankDescription(bankMasterFullNameListMap.get(testKeyValues.getExbankMasterId().getBankId()));

					List<BankAccountDetails> bankAccId = iTestKeyForParameterForBankService.tofetchAccountIdAccountTypeId(testKeyValues.getBankAccountNumber(), testKeyValues.getExbankMasterId().getBankId());
					if(bankAccId.size() != 0){
						BankAccountDetails bankAccNumDet = bankAccId.get(0);

						lstTestKey.setAccountNoId(bankAccNumDet.getBankAcctDetId());
						lstTestKey.setAccountNo(testKeyValues.getBankAccountNumber());
						lstTestKey.setAccountDescription(lstBankAccountTypeDescMap.get(bankAccNumDet.getBankAccountType().getBankAccountTypeId()));
					}

					lstTestKey.setBankbranchId(testKeyValues.getExBankBranchId().getBankBranchId());
					lstTestKey.setBankbranchCode(testKeyValues.getBankBranchCode());
					lstTestKey.setBankbranchDescription(iTestKeyForParameterForBankService.toFetchBankBranch(testKeyValues.getExBankBranchId().getBankBranchId()));

					List<CurrencyMaster> currencyId = iTestKeyForParameterForBankService.tofetchCurrencyId(testKeyValues.getCurrencyName());
					if (currencyId.size() != 0) {
						CurrencyMaster curMas = currencyId.get(0);
						lstTestKey.setCurrencyId(curMas.getCurrencyId());
						lstTestKey.setCurrencyCode(curMas.getCurrencyCode());
						lstTestKey.setCurrencyName(testKeyValues.getCurrencyName());
					}

					lstTestKey.setSendReceiveIndicator(testKeyValues.getSendReceiveIndicator());
					if(testKeyValues.getSendReceiveIndicator()!=null){
					if(testKeyValues.getSendReceiveIndicator().equalsIgnoreCase("S")){
						lstTestKey.setSendReceiveFullName("SEND");
					}else{
						lstTestKey.setSendReceiveFullName("RECEIVE");
					}
					}

					lstTestKey.setCalculationOrderNo(testKeyValues.getCalculationOrderNo());
					lstTestKey.setPrimaryTestKeyCode(testKeyValues.getPrimaryTestKeyCode());
					lstTestKey.setPrimaryTestKeyValue(testKeyValues.getPrimaryTestKeyValue());
					if(testKeyValues.getSecondaryTestKeyCode()!=null){
					lstTestKey.setSecondaryTestKeyCode(new BigDecimal(testKeyValues.getSecondaryTestKeyCode()));
					}
					lstTestKey.setSecondaryTestKeyCodeValue(testKeyValues.getSecondaryTestKeyValue());
					lstTestKey.setKeyValue(testKeyValues.getKeyValue());
					lstTestKey.setStatus(testKeyValues.getStatus());
					if(testKeyValues.getStatus() != null){
						if(testKeyValues.getStatus().equalsIgnoreCase("U")){
							lstTestKey.setStatusFullName("USED");
						}else if(testKeyValues.getStatus().equalsIgnoreCase("N")){
							lstTestKey.setStatusFullName("NEW");
						}else if(testKeyValues.getStatus().equalsIgnoreCase("M")){
							lstTestKey.setStatusFullName("MANUAL USED");
						}else{
							lstTestKey.setStatusFullName("----");
						}
					}else{
						lstTestKey.setStatusFullName("----");
					}

					lstTestKey.setCreatedBy(testKeyValues.getCreatedBy());
					lstTestKey.setCreatedDate(testKeyValues.getCreatedDate());
					lstTestKey.setModifiedBy(testKeyValues.getModifiedBy());
					lstTestKey.setModifiedDate(testKeyValues.getModifiedDate());
					lstTestKey.setApprovedBy(testKeyValues.getApprovedBy());
					lstTestKey.setApprovedDate(testKeyValues.getApprovedDate());
					lstTestKey.setIsActive(testKeyValues.getIsActive());

					if(testKeyValues.getIsActive().equalsIgnoreCase(Constants.Yes)){
						lstTestKey.setIsActiveStatus(Constants.DEACTIVATE); 
					}else if (testKeyValues.getIsActive().equalsIgnoreCase(Constants.D)) {
						lstTestKey.setIsActiveStatus(Constants.ACTIVATE);
					}else if (testKeyValues.getIsActive().equalsIgnoreCase(Constants.U) && testKeyValues.getModifiedBy()==null && testKeyValues.getModifiedDate()==null && testKeyValues.getApprovedBy()==null && testKeyValues.getApprovedDate()==null && testKeyValues.getRemarks()==null) {
						lstTestKey.setIsActiveStatus(Constants.DELETE);
					}else{
						lstTestKey.setIsActiveStatus(Constants.PENDING_FOR_APPROVE);      
					}

					lstTestKeyValue.add(lstTestKey);

				}

				if(lstTestKeyValue.size() != 0){
					setBooRenderDataTable(true);
					lstTestKeyValue.addAll(addlstTestKeyValue);
				}else{
					setBooRenderDataTable(false);
				}

			}
		}catch(NullPointerException ne){
			log.info("Method Name::viewAll"+ne.getMessage());
			setErrorMessage("Method Name::viewAll"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		} catch (Exception exception) {
			log.info("Method Name::viewAll"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}

	}

	//populate record if appcountry , bank , account , branch and send/receive ind matches
	public void populateDataRecords(){

		if(getBankId() != null && getAccountNo() != null && getBankbranchCode() != null && getSendReceiveIndicator() != null){

			lstTestKeyValue.clear();
			try{
				fetchingRecordsTestKeyMaster();

				List<TestKeyValues> lstpopData = iTestKeyForParameterForBankService.fetchtheRecordFromTestKeyValue(sessionStateManage.getCountryId(), getBankId(), getAccountNo(), getBankbranchCode(), getSendReceiveIndicator());

				if(lstpopData.size() != 0){

					for (TestKeyValues testKeyValues : lstpopData) {

						TestKeyValuesForDataTable lstTestKey = new TestKeyValuesForDataTable();

						lstTestKey.setTestKeyValueId(testKeyValues.getTestKeyValuesId());
						lstTestKey.setBankId(testKeyValues.getExbankMasterId().getBankId());
						lstTestKey.setBankCode(testKeyValues.getBankCode());
						lstTestKey.setBankDescription(bankMasterFullNameListMap.get(testKeyValues.getExbankMasterId().getBankId()));

						List<BankAccountDetails> bankAccId = iTestKeyForParameterForBankService.tofetchAccountIdAccountTypeId(testKeyValues.getBankAccountNumber(), testKeyValues.getExbankMasterId().getBankId());
						if(bankAccId.size() != 0){
							BankAccountDetails bankAccNumDet = bankAccId.get(0);

							lstTestKey.setAccountNoId(bankAccNumDet.getBankAcctDetId());
							lstTestKey.setAccountNo(testKeyValues.getBankAccountNumber());
							lstTestKey.setAccountDescription(lstBankAccountTypeDescMap.get(bankAccNumDet.getBankAccountType().getBankAccountTypeId()));
						}

						lstTestKey.setBankbranchId(testKeyValues.getExBankBranchId().getBankBranchId());
						lstTestKey.setBankbranchCode(testKeyValues.getBankBranchCode());
						lstTestKey.setBankbranchDescription(iTestKeyForParameterForBankService.toFetchBankBranch(testKeyValues.getExBankBranchId().getBankBranchId()));

						List<CurrencyMaster> currencyId = iTestKeyForParameterForBankService.tofetchCurrencyId(testKeyValues.getCurrencyName());
						if (currencyId.size() != 0) {
							CurrencyMaster curMas = currencyId.get(0);
							lstTestKey.setCurrencyId(curMas.getCurrencyId());
							lstTestKey.setCurrencyCode(curMas.getCurrencyCode());
							lstTestKey.setCurrencyName(testKeyValues.getCurrencyName());
						}

						lstTestKey.setSendReceiveIndicator(testKeyValues.getSendReceiveIndicator());
						if(testKeyValues.getSendReceiveIndicator()!=null){
						if(testKeyValues.getSendReceiveIndicator().equalsIgnoreCase("S")){
							lstTestKey.setSendReceiveFullName("SEND");
						}else{
							lstTestKey.setSendReceiveFullName("RECEIVE");
						}
						}

						lstTestKey.setCalculationOrderNo(testKeyValues.getCalculationOrderNo());
						lstTestKey.setPrimaryTestKeyCode(testKeyValues.getPrimaryTestKeyCode());
						lstTestKey.setPrimaryTestKeyValue(testKeyValues.getPrimaryTestKeyValue());
						lstTestKey.setSecondaryTestKeyCode(new BigDecimal(testKeyValues.getSecondaryTestKeyCode()));
						lstTestKey.setSecondaryTestKeyCodeValue(testKeyValues.getSecondaryTestKeyValue());
						lstTestKey.setKeyValue(testKeyValues.getKeyValue());
						lstTestKey.setStatus(testKeyValues.getStatus());
						if(testKeyValues.getStatus() != null){
							if(testKeyValues.getStatus().equalsIgnoreCase("U")){
								lstTestKey.setStatusFullName("USED");
							}else if(testKeyValues.getStatus().equalsIgnoreCase("N")){
								lstTestKey.setStatusFullName("NEW");
							}else if(testKeyValues.getStatus().equalsIgnoreCase("M")){
								lstTestKey.setStatusFullName("MANUAL USED");
							}else{
								lstTestKey.setStatusFullName("----");
							}
						}else{
							lstTestKey.setStatusFullName("----");
						}

						lstTestKey.setCreatedBy(testKeyValues.getCreatedBy());
						lstTestKey.setCreatedDate(testKeyValues.getCreatedDate());
						lstTestKey.setModifiedBy(testKeyValues.getModifiedBy());
						lstTestKey.setModifiedDate(testKeyValues.getModifiedDate());
						lstTestKey.setApprovedBy(testKeyValues.getApprovedBy());
						lstTestKey.setApprovedDate(testKeyValues.getApprovedDate());
						lstTestKey.setIsActive(testKeyValues.getIsActive());

						if(testKeyValues.getIsActive().equalsIgnoreCase(Constants.Yes)){
							lstTestKey.setIsActiveStatus(Constants.DEACTIVATE); 
						}else if (testKeyValues.getIsActive().equalsIgnoreCase(Constants.D)) {
							lstTestKey.setIsActiveStatus(Constants.ACTIVATE);
						}else if (testKeyValues.getIsActive().equalsIgnoreCase(Constants.U) && testKeyValues.getModifiedBy()==null && testKeyValues.getModifiedDate()==null && testKeyValues.getApprovedBy()==null && testKeyValues.getApprovedDate()==null && testKeyValues.getRemarks()==null) {
							lstTestKey.setIsActiveStatus(Constants.DELETE);
						}else{
							lstTestKey.setIsActiveStatus(Constants.PENDING_FOR_APPROVE);      
						}

						lstTestKeyValue.add(lstTestKey);

					}

					if(lstTestKeyValue.size() != 0){
						setBooRenderDataTable(true);
						lstTestKeyValue.addAll(addlstTestKeyValue);
					}else{
						setBooRenderDataTable(false);
					}

				}else{
					if(addlstTestKeyValue.size() != 0){
						setBooRenderDataTable(true);
						lstTestKeyValue.addAll(addlstTestKeyValue);
					}else{
						setBooRenderDataTable(false);
					}
				}
			}catch(NullPointerException ne){
				log.info("Method Name::populateDataRecords"+ne.getMessage());
				setErrorMessage("Method Name::populateDataRecords"); 
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return; 
			} catch (Exception exception) {
				log.info("Method Name::populateDataRecords"+exception.getMessage());
				setErrorMessage(exception.getMessage());
				RequestContext.getCurrentInstance().execute("exception.show();");
				return;
			}
		}else{
			primaryParameterList.clear();
			setSendReceiveIndicator(null);
			if(addlstTestKeyValue.size() != 0){
				setBooRenderDataTable(true);
				lstTestKeyValue.addAll(addlstTestKeyValue);
			}else{
				setBooRenderDataTable(false);
			}
			RequestContext.getCurrentInstance().execute("ordernotMatch.show();");
		}
	}

	//activate , De-Activate , Delete , Pending for Approval Condtion changes
	public void checkStatus(TestKeyValuesForDataTable testkeyvalue){
		if(testkeyvalue.getIsActiveStatus().equalsIgnoreCase(Constants.PENDING_FOR_APPROVE)){
			RequestContext.getCurrentInstance().execute("pending.show();");
			return;     
		}else if(testkeyvalue.getIsActiveStatus().equalsIgnoreCase(Constants.REMOVE)){
			addlstTestKeyValue.remove(testkeyvalue);
			lstTestKeyValue.remove(testkeyvalue);
		}else if(testkeyvalue.getIsActiveStatus().equalsIgnoreCase(Constants.DEACTIVATE)){
			testkeyvalue.setRemarksCheck(true);
			setApprovedBy(testkeyvalue.getApprovedBy());
			setApprovedDate(testkeyvalue.getApprovedDate());
			RequestContext.getCurrentInstance().execute("remarks.show();");
		}else if(testkeyvalue.getIsActiveStatus().equalsIgnoreCase(Constants.ACTIVATE)){
			testkeyvalue.setActiveRecordCheck(true);
			RequestContext.getCurrentInstance().execute("activateRecord.show();");
			return;      
		}
		else if (testkeyvalue.getIsActiveStatus().equalsIgnoreCase(Constants.DELETE) && testkeyvalue.getModifiedBy()==null && testkeyvalue.getModifiedDate()==null && testkeyvalue.getApprovedBy()==null && testkeyvalue.getApprovedDate()==null && testkeyvalue.getRemarks()==null) {
			testkeyvalue.setPermentDeleteCheck(true);
			RequestContext.getCurrentInstance().execute("permanentDelete.show();");
			return;			      
		}
		if(lstTestKeyValue.size()==0){
			setBooRenderDataTable(false); 
		}
	}

	// click ok to enter remarks and modify is active to D and making approval null
	public void clickOkRemarks(){
		if(getRemarks() != null && !getRemarks().equals("")){
			for (TestKeyValuesForDataTable testkeyvalues : lstTestKeyValue) {
				if(testkeyvalues.getRemarksCheck() != null){
					if(testkeyvalues.getRemarksCheck().equals(true)) {
						testkeyvalues.setRemarks(getRemarks());
						updateTestKeyMaster(testkeyvalues);
						clearFormFields();
						viewAll();
						setRemarks(null);	   
					}
				}
			}

		}else{
			RequestContext.getCurrentInstance().execute("remarksEmpty.show();");    
		}
	}

	// cancel to redirect to same page
	public void cancelRemarks() {
		setRemarks(null);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../testKey/TestKeyValuesForBank.xhtml"); 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// deactivate record in data base making "U" its confirming
	public void activateRecord(){
		if(lstTestKeyValue.size()>0){
			for (TestKeyValuesForDataTable testkeyvalues : lstTestKeyValue) {
				if(testkeyvalues.getActiveRecordCheck()!=null){
					if(testkeyvalues.getActiveRecordCheck().equals(true)){
						conformToDeActivteTestKeyMaster(testkeyvalues);    
					}
				}
			}
		}
	}

	// delete record permenantely from data base its confirming
	public void CompleteAssignedConfirmPermanentDelete(){
		if(lstTestKeyValue.size()>0){
			for (TestKeyValuesForDataTable testkeyvalues : lstTestKeyValue) {
				if(testkeyvalues.getPermentDeleteCheck()!=null){
					if(testkeyvalues.getPermentDeleteCheck().equals(true)){
						deleteRecordTestKeyMaster(testkeyvalues);
						lstTestKeyValue.remove(testkeyvalues);
					}
				}
			}
		}   
	}

	// deactivate record in data base making "D"
	public void updateTestKeyMaster(TestKeyValuesForDataTable testkeyvalues){
		try {
			iTestKeyForParameterForBankService.activatedTestKeyValues(testkeyvalues.getTestKeyValueId(),sessionStateManage.getUserName(),testkeyvalues.getRemarks());
			/*} catch (Exception e) {
    		e.printStackTrace();
    	}*/
		}catch(NullPointerException ne){
			log.info("Method Name::updateTestKeyMaster"+ne.getMessage());
			setErrorMessage("Method Name::updateTestKeyMaster"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		} catch (Exception exception) {
			log.info("Method Name::updateTestKeyMaster"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}
	}

	// deactivate record in data base making "U"
	public void conformToDeActivteTestKeyMaster(TestKeyValuesForDataTable testkeyvalues){
		try {
			iTestKeyForParameterForBankService.deActivateRecordTestKeyValues(testkeyvalues.getTestKeyValueId(),sessionStateManage.getUserName());
			viewAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// delete record permenantely from data base
	public void deleteRecordTestKeyMaster(TestKeyValuesForDataTable testkeyvalues){
		try {
			iTestKeyForParameterForBankService.deleteRecordTestKeyValues(testkeyvalues.getTestKeyValueId());
			/*} catch (Exception e) {
    		e.printStackTrace();
    	}*/
		}catch(NullPointerException ne){
			log.info("Method Name::deleteRecordTestKeyMaster"+ne.getMessage());
			setErrorMessage("Method Name::deleteRecordTestKeyMaster"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		} catch (Exception exception) {
			log.info("Method Name::deleteRecordTestKeyMaster"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}
	}

	// fetch primary parameter from  test key master
	public void fetchingRecordsTestKeyMaster(){

		primaryParameterList.clear();

		List<TestKeyMaster> lstpopDataTestkeyMaster = iTestKeyForParameterForBankService.fetchRecordFromTestKeyMasterForPrimaryKey(sessionStateManage.getCountryId(), getBankId(), getAccountNo(), getBankbranchCode(), getSendReceiveIndicator());

		if(lstpopDataTestkeyMaster.size() != 0){
			primaryParameterList.addAll(lstpopDataTestkeyMaster);
		}

	}

	// Test key values Approval Page
	public void testKeyMasterApprovalPageNavigation(){
		// clearing all data from form
		clearAll();
		try {
			//for loading to populate CorespondingBank
			popUpCorespondingBank();
			//for loading to populate Bank Account type
			popBankAccountType();
			//populate records for approval only "U"
			populateTestKeyValuesForApproval();
			// add panel
			setBooAddPanel(false);
			//submit panel
			setBooRenderDataTable(false);
			// for making read only while approval 
			setBooRenderApproval(true);
			setBooApprovalPanel(true);

			//try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "TestKeyValuesApproval.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../testKey/TestKeyValuesApproval.xhtml");      
			/*} catch (Exception e) {
    		e.printStackTrace();
    	}*/
		}catch(NullPointerException ne){
			log.info("Method Name::testKeyMasterApprovalPageNavigation"+ne.getMessage());
			setErrorMessage("Method Name::testKeyMasterApprovalPageNavigation"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		} catch (Exception exception) {
			log.info("Method Name::testKeyMasterApprovalPageNavigation"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}
	}

	//populate records for approval only "U"
	public void populateTestKeyValuesForApproval(){

		lstTestKeyValueforApproval.clear();
		try{
			List<TestKeyValues> lstTestKeyValuesDBRecords = iTestKeyForParameterForBankService.viewTestKeyValuesForApproval();

			if(lstTestKeyValuesDBRecords.size() != 0){

				for (TestKeyValues testKeyValues : lstTestKeyValuesDBRecords) {

					TestKeyValuesForDataTable lstTestKey = new TestKeyValuesForDataTable();

					lstTestKey.setTestKeyValueId(testKeyValues.getTestKeyValuesId());
					lstTestKey.setBankId(testKeyValues.getExbankMasterId().getBankId());
					lstTestKey.setBankCode(testKeyValues.getBankCode());
					lstTestKey.setBankDescription(bankMasterFullNameListMap.get(testKeyValues.getExbankMasterId().getBankId()));

					List<BankAccountDetails> bankAccId = iTestKeyForParameterForBankService.tofetchAccountIdAccountTypeId(testKeyValues.getBankAccountNumber(), testKeyValues.getExbankMasterId().getBankId());
					if(bankAccId.size() != 0){
						BankAccountDetails bankAccNumDet = bankAccId.get(0);

						lstTestKey.setAccountNoId(bankAccNumDet.getBankAcctDetId());
						lstTestKey.setAccountNo(testKeyValues.getBankAccountNumber());
						lstTestKey.setAccountDescription(lstBankAccountTypeDescMap.get(bankAccNumDet.getBankAccountType().getBankAccountTypeId()));
					}

					lstTestKey.setBankbranchId(testKeyValues.getExBankBranchId().getBankBranchId());
					lstTestKey.setBankbranchCode(testKeyValues.getBankBranchCode());
					lstTestKey.setBankbranchDescription(iTestKeyForParameterForBankService.toFetchBankBranch(testKeyValues.getExBankBranchId().getBankBranchId()));

					List<CurrencyMaster> currencyId = iTestKeyForParameterForBankService.tofetchCurrencyId(testKeyValues.getCurrencyName());
					if (currencyId.size() != 0) {
						CurrencyMaster curMas = currencyId.get(0);
						lstTestKey.setCurrencyId(curMas.getCurrencyId());
						lstTestKey.setCurrencyCode(curMas.getCurrencyCode());
						lstTestKey.setCurrencyName(testKeyValues.getCurrencyName());
					}

					lstTestKey.setSendReceiveIndicator(testKeyValues.getSendReceiveIndicator());
					if(testKeyValues.getSendReceiveIndicator().equalsIgnoreCase("S")){
						lstTestKey.setSendReceiveFullName("SEND");
					}else{
						lstTestKey.setSendReceiveFullName("RECEIVE");
					}

					lstTestKey.setCalculationOrderNo(testKeyValues.getCalculationOrderNo());            	
					lstTestKey.setPrimaryTestKeyCode(testKeyValues.getPrimaryTestKeyCode());
					lstTestKey.setPrimaryTestKeyValue(testKeyValues.getPrimaryTestKeyValue());
					lstTestKey.setSecondaryTestKeyCode(new BigDecimal(testKeyValues.getSecondaryTestKeyCode()));
					lstTestKey.setSecondaryTestKeyCodeValue(testKeyValues.getSecondaryTestKeyValue());
					lstTestKey.setKeyValue(testKeyValues.getKeyValue());
					lstTestKey.setStatus(testKeyValues.getStatus());
					if(testKeyValues.getStatus() != null){
						if(testKeyValues.getStatus().equalsIgnoreCase("U")){
							lstTestKey.setStatusFullName("USED");
						}else if(testKeyValues.getStatus().equalsIgnoreCase("N")){
							lstTestKey.setStatusFullName("NEW");
						}else if(testKeyValues.getStatus().equalsIgnoreCase("M")){
							lstTestKey.setStatusFullName("MANUAL USED");
						}else{
							lstTestKey.setStatusFullName("----");
						}
					}else{
						lstTestKey.setStatusFullName("----");
					}

					lstTestKey.setCreatedBy(testKeyValues.getCreatedBy());
					lstTestKey.setCreatedDate(testKeyValues.getCreatedDate());
					lstTestKey.setModifiedBy(testKeyValues.getModifiedBy());
					lstTestKey.setModifiedDate(testKeyValues.getModifiedDate());
					lstTestKey.setApprovedBy(testKeyValues.getApprovedBy());
					lstTestKey.setApprovedDate(testKeyValues.getApprovedDate());
					lstTestKey.setIsActive(testKeyValues.getIsActive());

					if(testKeyValues.getIsActive().equalsIgnoreCase(Constants.Yes)){
						lstTestKey.setIsActiveStatus(Constants.DEACTIVATE); 
					}else if (testKeyValues.getIsActive().equalsIgnoreCase(Constants.D)) {
						lstTestKey.setIsActiveStatus(Constants.ACTIVATE);
					}else if (testKeyValues.getIsActive().equalsIgnoreCase(Constants.U) && testKeyValues.getModifiedBy()==null && testKeyValues.getModifiedDate()==null && testKeyValues.getApprovedBy()==null && testKeyValues.getApprovedDate()==null && testKeyValues.getRemarks()==null) {
						lstTestKey.setIsActiveStatus(Constants.DELETE);
					}else{
						lstTestKey.setIsActiveStatus(Constants.PENDING_FOR_APPROVE);      
					}

					lstTestKeyValueforApproval.add(lstTestKey);

				}

			}
		}catch(NullPointerException ne){
			log.info("Method Name::populateTestKeyValuesForApproval"+ne.getMessage());
			setErrorMessage("Method Name::populateTestKeyValuesForApproval"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		} catch (Exception exception) {
			log.info("Method Name::populateTestKeyValuesForApproval"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}

	}

	// approval page redirecting to be done
	public void approvelCheckForTestKeyValuesUser(TestKeyValuesForDataTable testkeyvaluesApproval){
		if(!(testkeyvaluesApproval.getModifiedBy()==null?  testkeyvaluesApproval.getCreatedBy():testkeyvaluesApproval.getModifiedBy()).equalsIgnoreCase(sessionStateManage.getUserName()))
		{
			try{
				setTestKeyValueId(testkeyvaluesApproval.getTestKeyValueId());
				setBankId(testkeyvaluesApproval.getBankId());
				setBankCode(testkeyvaluesApproval.getBankCode());
				setBankDescription(testkeyvaluesApproval.getBankDescription());

				List<BankAccountDetails> lstBankAccountList = iTestKeyForParameterForBankService.toFetchAccountNumberBasedOnBank(getBankId());
				if(lstBankAccountList.size() != 0){
					for (BankAccountDetails bankAccountDetails : lstBankAccountList) {
						TestKeyValueAccNumDesc lstAccNumDesc = new TestKeyValueAccNumDesc();

						lstAccNumDesc.setAccountNumberId(bankAccountDetails.getBankAcctDetId());
						lstAccNumDesc.setAccountNumber(bankAccountDetails.getBankAcctNo());
						lstAccNumDesc.setAccountNumberDesc(lstBankAccountTypeDescMap.get(bankAccountDetails.getBankAccountType().getBankAccountTypeId()));

						lstBankAccount.add(lstAccNumDesc);
					}
				}

				setAccountNoId(testkeyvaluesApproval.getAccountNoId());
				setAccountNo(testkeyvaluesApproval.getAccountNo());
				setAccountDescription(testkeyvaluesApproval.getAccountDescription());

				List<BankBranch> lstbankbranch = iTestKeyForParameterForBankService.toFetchBranchBasedOnBank(getBankId());
				if(lstbankbranch.size() != 0){
					bankbranchList.addAll(lstbankbranch);
				}

				setBankbranchId(testkeyvaluesApproval.getBankbranchId());
				setBankbranchCode(testkeyvaluesApproval.getBankbranchCode());
				setBankbranchDescription(testkeyvaluesApproval.getBankbranchDescription());

				List<BankAccountDetails> lstCurrency = iTestKeyForParameterForBankService.toFetchCurrencyBasedOnAccountNumber(getAccountNoId());
				if(lstCurrency.size() != 0){
					bankCurrencylist.addAll(lstCurrency);
				}

				setCurrencyId(testkeyvaluesApproval.getCurrencyId());
				setCurrencyCode(testkeyvaluesApproval.getCurrencyCode());
				setCurrencyName(testkeyvaluesApproval.getCurrencyName());
				setSendReceiveIndicator(testkeyvaluesApproval.getSendReceiveIndicator());
				setCalculationOrderNo(testkeyvaluesApproval.getCalculationOrderNo());

				//fetching records from Test Key Master based on combination
				fetchingRecordsTestKeyMaster();

				setPrimaryTestKeyCode(testkeyvaluesApproval.getPrimaryTestKeyCode());
				setPrimaryTestKeyValue(testkeyvaluesApproval.getPrimaryTestKeyValue());
				setSecondaryTestKeyCode(testkeyvaluesApproval.getSecondaryTestKeyCode());
				setSecondaryTestKeyCodeValue(testkeyvaluesApproval.getSecondaryTestKeyCodeValue());
				setKeyValue(testkeyvaluesApproval.getKeyValue());
				setStatus(testkeyvaluesApproval.getStatus());

				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("../testKey/TestKeyValuesForBank.xhtml"); 	
				} catch (Exception e) {
					e.printStackTrace();
				}  
			}catch(NullPointerException ne){
				log.info("Method Name::approvelCheckForTestKeyValuesUser"+ne.getMessage());
				setErrorMessage("Method Name::approvelCheckForTestKeyValuesUser"); 
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return; 
			} catch (Exception exception) {
				log.info("Method Name::approvelCheckForTestKeyValuesUser"+exception.getMessage());
				setErrorMessage(exception.getMessage());
				RequestContext.getCurrentInstance().execute("exception.show();");
				return;
			}

		}else{
			RequestContext.getCurrentInstance().execute("notApproved.show();");
		}
	}

	// to make Approval Submit action
	public void testKeyValuesApproveRecord(){
		try{
			String approvalMsg = iTestKeyForParameterForBankService.checkTestKeyValuesApproveMultiUser(getTestKeyValueId(),sessionStateManage.getUserName());
			if(approvalMsg.equalsIgnoreCase("Success")){
				RequestContext.getCurrentInstance().execute("approve.show();");
			}else{
				RequestContext.getCurrentInstance().execute("alreadyapprov.show();");
				return; 
			}
		}catch(NullPointerException ne){
			log.info("Method Name::testKeyValuesApproveRecord"+ne.getMessage());
			setErrorMessage("Method Name::testKeyValuesApproveRecord"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		} catch (Exception exception) {
			log.info("Method Name::testKeyValuesApproveRecord"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}
	}

	// to make Approval Cancel and redirect to Approval Screen
	public void testKeyValuesCancel(){
		testKeyMasterApprovalPageNavigation();
	}



}
