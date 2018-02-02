package com.amg.exchange.remittance.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;
import org.joda.time.Weeks;
import org.joda.time.Years;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.TokenGeneration;
import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.foreigncurrency.service.ForeignLocalCurrencyDenominationService;
import com.amg.exchange.mail.ApplicationMailer;
import com.amg.exchange.model.RateAlertFrequency;
import com.amg.exchange.model.StandingInstruction;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.service.ICustomerRegistrationBranchService;
import com.amg.exchange.registration.service.IRemmiterOnlineRegService;
import com.amg.exchange.remittance.model.AdditionalBankRuleAddData;
import com.amg.exchange.remittance.model.AdditionalBankRuleMap;
import com.amg.exchange.remittance.model.BeneficaryAccount;
import com.amg.exchange.remittance.model.BeneficaryMaster;
import com.amg.exchange.remittance.model.BeneficaryRelationship;
import com.amg.exchange.remittance.service.IPersonalRemittanceService;
import com.amg.exchange.remittance.service.IStandingInstructionService;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.treasury.model.ServiceGroupMasterDesc;
import com.amg.exchange.treasury.service.ISupplierService;
import com.amg.exchange.treasury.service.ServiceCodeMasterService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.GetRound;
import com.amg.exchange.util.SessionStateManage;

@Component("standingInstructionBean")
@Scope("session")
public class StandingInstructionBean<T> implements Serializable {

	Logger log = Logger.getLogger(StandingInstructionBean.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	SessionStateManage sessionmanage = new SessionStateManage();

	// autowire for services
	@Autowired
	IPersonalRemittanceService iPersonalRemittanceService;
	
	@Autowired
	ICustomerRegistrationBranchService<T> customerRegistrationBranchService;

	@Autowired
	ISupplierService iSupplierService;

	@Autowired
	IGeneralService<T> generalService;

	@Autowired
	IStandingInstructionService<T> standingInstructionService;
	
	@Autowired
	ForeignLocalCurrencyDenominationService<T> foreignLocalCurrencyDenominationService;
	
	@Autowired
	IRemmiterOnlineRegService<T> remOnlineReg;
	public IRemmiterOnlineRegService<T> getRemOnlineReg() {
		return remOnlineReg;
	}
	
	public void setRemOnlineReg(IRemmiterOnlineRegService<T> remOnlineReg) {
		this.remOnlineReg = remOnlineReg;
	}
	
	
	private TokenGeneration tokenGeneration = new TokenGeneration();
	
	@Autowired
	public TokenGeneration getTokenGeneration() {
		return tokenGeneration;
	}
	public void setTokenGeneration(TokenGeneration tokenGeneration) {
		this.tokenGeneration = tokenGeneration;
	}
	
	@Autowired
	ApplicationMailer mailService;
	public ApplicationMailer getMailService() {
		return mailService;
	}
	public void setMailService(ApplicationMailer mailService) {
		this.mailService = mailService;
	}

	// List to get Details in PersonalRemmitanceBeneficary
	private List<PersonalRemmitanceBeneficaryDataTable> customerBeneficaryDTList = new ArrayList<PersonalRemmitanceBeneficaryDataTable>();
	private List<Customer> customerDetailsList = new ArrayList<Customer>();
	private List<BeneficaryAccount> beneficaryAccountList = new ArrayList<BeneficaryAccount>();
	private List<CurrencyMaster> currencyMasterList = new ArrayList<CurrencyMaster>();
	private List<RateAlertFrequency> lstFrequencyDetails = new ArrayList<RateAlertFrequency>();
	private List<Document> lstDocument = new ArrayList<Document>();
	private List<StandingInstruction> lstStandingInstrn = new ArrayList<StandingInstruction>();
	private List<StandingInstructionList> lstStndingInstrnDetails = new ArrayList<StandingInstructionList>();
	

	public List<CurrencyMaster> getCurrencyMasterList() {
		return currencyMasterList;
	}

	public void setCurrencyMasterList(List<CurrencyMaster> currencyMasterList) {
		this.currencyMasterList = currencyMasterList;
	}

	public List<PersonalRemmitanceBeneficaryDataTable> getCustomerBeneficaryDTList() {
		return customerBeneficaryDTList;
	}

	public void setCustomerBeneficaryDTList(
			List<PersonalRemmitanceBeneficaryDataTable> customerBeneficaryDTList) {
		this.customerBeneficaryDTList = customerBeneficaryDTList;
	}

	public List<Customer> getCustomerDetailsList() {
		return customerDetailsList;
	}

	public void setCustomerDetailsList(List<Customer> customerDetailsList) {
		this.customerDetailsList = customerDetailsList;
	}

	public List<BeneficaryAccount> getBeneficaryAccountList() {
		return beneficaryAccountList;
	}

	public void setBeneficaryAccountList(
			List<BeneficaryAccount> beneficaryAccountList) {
		this.beneficaryAccountList = beneficaryAccountList;
	}

	public List<RateAlertFrequency> getLstFrequencyDetails() {
		return lstFrequencyDetails;
	}

	public void setLstFrequencyDetails(
			List<RateAlertFrequency> lstFrequencyDetails) {
		this.lstFrequencyDetails = lstFrequencyDetails;
	}

	public List<Document> getLstDocument() {
		return lstDocument;
	}

	public void setLstDocument(List<Document> lstDocument) {
		this.lstDocument = lstDocument;
	}

	public List<StandingInstruction> getLstStandingInstrn() {
		return lstStandingInstrn;
	}

	public void setLstStandingInstrn(List<StandingInstruction> lstStandingInstrn) {
		this.lstStandingInstrn = lstStandingInstrn;
	}


	public List<StandingInstructionList> getLstStndingInstrnDetails() {
		return lstStndingInstrnDetails;
	}

	public void setLstStndingInstrnDetails(
			List<StandingInstructionList> lstStndingInstrnDetails) {
		this.lstStndingInstrnDetails = lstStndingInstrnDetails;
	}


	// variables declaration - All Beneficiary Selected Details (First Panel)
	private String customerBeneName;
	private String customerAccNum;
	private BigDecimal customerCurrencyId;
	private String customerCurrencyName;
	private BigDecimal customerCountryId;
	private String customerCountryName;
	private BigDecimal customerBankId;
	private String customerBankName;
	private BigDecimal customerBranchId;
	private String customerBranchName;
	private String customerSInstrnCreatedFor;

	// Variables Declaration - Based on Login Customer (First Panel)
	private String customerName;
	private BigDecimal customerNo;
	private BigDecimal customerRefNo;
	private String custfirstName;
	private String custsecondName;
	private String custthirdName;
	private BigDecimal custNationality;
	private String custNationalityName;
	private String customerEmailId;
	private BigDecimal selCurrencyCode;
	private BigDecimal sendingAmount;
	private BigDecimal customerBeneMasterId;
	private BigDecimal customerBeneAccId;
	private BigDecimal customerServiceId;
	private BigDecimal customerDeliveryId;
	private BigDecimal customerRemitId;

	// rendering Panels
	private Boolean booRenderAllBeneDetails;
	private Boolean booRenderBeneDtAdditionalDetails;
	private Boolean booRenderBeneDtStandInstn;
	private Boolean booRenderTokenVerification;
	private Boolean booRenderSuccessPage;
	private Boolean booRenderStandingInstrnByDate;

	// Variable Declaration - For Standing Instruction Creation (Third Panel)
	private Date sIStartdate;
	private BigDecimal sIRepeatNOOFTimes;
	private BigDecimal sIFrequencyId;
	private Boolean emailCheckbox = false;
	private BigDecimal documentNo;
	private String documentdesc;
	private BigDecimal documentId;
	private BigDecimal companyId;
	 

	// variable Declaration - For Token Verification (Forth Panel)
	private String tokenEntered;
	private BigDecimal pkCustomerId;
	private String tokenKey = null;
	private BigDecimal standingInstrnId;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public BigDecimal getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(BigDecimal customerNo) {
		this.customerNo = customerNo;
	}

	public BigDecimal getCustomerRefNo() {
		return customerRefNo;
	}

	public void setCustomerRefNo(BigDecimal customerRefNo) {
		this.customerRefNo = customerRefNo;
	}

	public String getCustfirstName() {
		return custfirstName;
	}

	public void setCustfirstName(String custfirstName) {
		this.custfirstName = custfirstName;
	}

	public String getCustsecondName() {
		return custsecondName;
	}

	public void setCustsecondName(String custsecondName) {
		this.custsecondName = custsecondName;
	}

	public String getCustthirdName() {
		return custthirdName;
	}

	public void setCustthirdName(String custthirdName) {
		this.custthirdName = custthirdName;
	}

	public BigDecimal getCustNationality() {
		return custNationality;
	}

	public void setCustNationality(BigDecimal custNationality) {
		this.custNationality = custNationality;
	}

	public String getCustNationalityName() {
		return custNationalityName;
	}

	public void setCustNationalityName(String custNationalityName) {
		this.custNationalityName = custNationalityName;
	}

	public String getCustomerBeneName() {
		return customerBeneName;
	}

	public void setCustomerBeneName(String customerBeneName) {
		this.customerBeneName = customerBeneName;
	}

	public BigDecimal getSelCurrencyCode() {
		return selCurrencyCode;
	}

	public void setSelCurrencyCode(BigDecimal selCurrencyCode) {
		this.selCurrencyCode = selCurrencyCode;
	}

	public BigDecimal getSendingAmount() {
		return sendingAmount;
	}

	public void setSendingAmount(BigDecimal sendingAmount) {
		this.sendingAmount = sendingAmount;
	}

	public Boolean getBooRenderAllBeneDetails() {
		return booRenderAllBeneDetails;
	}

	public void setBooRenderAllBeneDetails(Boolean booRenderAllBeneDetails) {
		this.booRenderAllBeneDetails = booRenderAllBeneDetails;
	}

	public Boolean getBooRenderBeneDtAdditionalDetails() {
		return booRenderBeneDtAdditionalDetails;
	}

	public void setBooRenderBeneDtAdditionalDetails(
			Boolean booRenderBeneDtAdditionalDetails) {
		this.booRenderBeneDtAdditionalDetails = booRenderBeneDtAdditionalDetails;
	}

	public String getCustomerAccNum() {
		return customerAccNum;
	}

	public void setCustomerAccNum(String customerAccNum) {
		this.customerAccNum = customerAccNum;
	}

	public BigDecimal getCustomerCurrencyId() {
		return customerCurrencyId;
	}

	public void setCustomerCurrencyId(BigDecimal customerCurrencyId) {
		this.customerCurrencyId = customerCurrencyId;
	}

	public String getCustomerCurrencyName() {
		return customerCurrencyName;
	}

	public void setCustomerCurrencyName(String customerCurrencyName) {
		this.customerCurrencyName = customerCurrencyName;
	}

	public BigDecimal getCustomerCountryId() {
		return customerCountryId;
	}

	public void setCustomerCountryId(BigDecimal customerCountryId) {
		this.customerCountryId = customerCountryId;
	}

	public String getCustomerCountryName() {
		return customerCountryName;
	}

	public void setCustomerCountryName(String customerCountryName) {
		this.customerCountryName = customerCountryName;
	}

	public BigDecimal getCustomerBankId() {
		return customerBankId;
	}

	public void setCustomerBankId(BigDecimal customerBankId) {
		this.customerBankId = customerBankId;
	}

	public String getCustomerBankName() {
		return customerBankName;
	}

	public void setCustomerBankName(String customerBankName) {
		this.customerBankName = customerBankName;
	}

	public BigDecimal getCustomerBranchId() {
		return customerBranchId;
	}

	public void setCustomerBranchId(BigDecimal customerBranchId) {
		this.customerBranchId = customerBranchId;
	}

	public String getCustomerBranchName() {
		return customerBranchName;
	}

	public void setCustomerBranchName(String customerBranchName) {
		this.customerBranchName = customerBranchName;
	}

	public String getCustomerSInstrnCreatedFor() {
		return customerSInstrnCreatedFor;
	}

	public void setCustomerSInstrnCreatedFor(String customerSInstrnCreatedFor) {
		this.customerSInstrnCreatedFor = customerSInstrnCreatedFor;
	}

	public Boolean getBooRenderBeneDtStandInstn() {
		return booRenderBeneDtStandInstn;
	}

	public void setBooRenderBeneDtStandInstn(Boolean booRenderBeneDtStandInstn) {
		this.booRenderBeneDtStandInstn = booRenderBeneDtStandInstn;
	}

	public Date getsIStartdate() {
		return sIStartdate;
	}

	public void setsIStartdate(Date sIStartdate) {
		this.sIStartdate = sIStartdate;
	}

	public BigDecimal getsIRepeatNOOFTimes() {
		return sIRepeatNOOFTimes;
	}

	public void setsIRepeatNOOFTimes(BigDecimal sIRepeatNOOFTimes) {
		this.sIRepeatNOOFTimes = sIRepeatNOOFTimes;
	}

	public BigDecimal getsIFrequencyId() {
		return sIFrequencyId;
	}

	public void setsIFrequencyId(BigDecimal sIFrequencyId) {
		this.sIFrequencyId = sIFrequencyId;
	}

	public Boolean getBooRenderTokenVerification() {
		return booRenderTokenVerification;
	}

	public void setBooRenderTokenVerification(Boolean booRenderTokenVerification) {
		this.booRenderTokenVerification = booRenderTokenVerification;
	}

	public String getTokenEntered() {
		return tokenEntered;
	}

	public void setTokenEntered(String tokenEntered) {
		this.tokenEntered = tokenEntered;
	}

	public Boolean getBooRenderSuccessPage() {
		return booRenderSuccessPage;
	}

	public void setBooRenderSuccessPage(Boolean booRenderSuccessPage) {
		this.booRenderSuccessPage = booRenderSuccessPage;
	}

	public Boolean getBooRenderStandingInstrnByDate() {
		return booRenderStandingInstrnByDate;
	}

	public void setBooRenderStandingInstrnByDate(
			Boolean booRenderStandingInstrnByDate) {
		this.booRenderStandingInstrnByDate = booRenderStandingInstrnByDate;
	}

	public Boolean getEmailCheckbox() {
		return emailCheckbox;
	}

	public void setEmailCheckbox(Boolean emailCheckbox) {
		this.emailCheckbox = emailCheckbox;
	}

	public String getTokenKey() {
		return tokenKey;
	}
	
	public void setTokenKey(String tokenKey) {
		this.tokenKey = tokenKey;
	}
	
	public BigDecimal getCustomerBeneMasterId() {
		return customerBeneMasterId;
	}

	public void setCustomerBeneMasterId(BigDecimal customerBeneMasterId) {
		this.customerBeneMasterId = customerBeneMasterId;
	}

	public BigDecimal getCustomerBeneAccId() {
		return customerBeneAccId;
	}

	public void setCustomerBeneAccId(BigDecimal customerBeneAccId) {
		this.customerBeneAccId = customerBeneAccId;
	}

	public BigDecimal getCustomerDeliveryId() {
		return customerDeliveryId;
	}

	public void setCustomerDeliveryId(BigDecimal customerDeliveryId) {
		this.customerDeliveryId = customerDeliveryId;
	}

	public BigDecimal getCustomerRemitId() {
		return customerRemitId;
	}

	public void setCustomerRemitId(BigDecimal customerRemitId) {
		this.customerRemitId = customerRemitId;
	}

	public BigDecimal getCustomerServiceId() {
		return customerServiceId;
	}

	public void setCustomerServiceId(BigDecimal customerServiceId) {
		this.customerServiceId = customerServiceId;
	}

	public BigDecimal getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}

	public String getDocumentdesc() {
		return documentdesc;
	}

	public void setDocumentdesc(String documentdesc) {
		this.documentdesc = documentdesc;
	}

	public BigDecimal getDocumentId() {
		return documentId;
	}

	public void setDocumentId(BigDecimal documentId) {
		this.documentId = documentId;
	}

	public BigDecimal getCompanyId() {
		return companyId;
	}

	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}

	public String getCustomerEmailId() {
		return customerEmailId;
	}

	public void setCustomerEmailId(String customerEmailId) {
		this.customerEmailId = customerEmailId;
	}
	
	public BigDecimal getPkCustomerId() {
		return pkCustomerId;
	}
	public void setPkCustomerId(BigDecimal pkCustomerId) {
		this.pkCustomerId = pkCustomerId;
	}

	public BigDecimal getStandingInstrnId() {
		return standingInstrnId;
	}

	public void setStandingInstrnId(BigDecimal standingInstrnId) {
		this.standingInstrnId = standingInstrnId;
	}

	// to get customer Details from CustomerIdProof
	public void getCustomerDetails() {
		setCustomerName(customerDetailsList.get(0).getFirstName());
		setCustomerNo(customerDetailsList.get(0).getCustomerId());
		setCustomerRefNo(customerDetailsList.get(0).getCustomerReference());
		setCustfirstName(customerDetailsList.get(0).getFirstName());
		setCustsecondName(customerDetailsList.get(0).getMiddleName());
		setCustthirdName(customerDetailsList.get(0).getLastName());
		setCustNationality(customerDetailsList.get(0).getFsCountryMasterByNationality().getCountryId());
		setCustNationalityName(iSupplierService.getNationalityName(customerDetailsList.get(0).getFsCountryMasterByNationality().getCountryId()));
		setCustomerEmailId(customerDetailsList.get(0).getEmail());

	}
	
	@Autowired
	ServiceCodeMasterService serviceMasterService;

	// to get Beneficiary Details from BeneficaryAccount
	public void beneficiaryDetailFromDB() {
		customerBeneficaryDTList.clear();
		//customerDetailsList = iPersonalRemittanceService.getCustomerDetails("502874732018", new BigDecimal(Constants.CIVILID));
		customerDetailsList = customerRegistrationBranchService.getCustomerInfoFetch(sessionmanage.getCustomerId());
		if (customerDetailsList.size() > 0) {
			getCustomerDetails();
			List<BeneficaryRelationship> isCoustomerExist = iPersonalRemittanceService.isCoustomerExistInDB(getCustomerNo());
			if (isCoustomerExist.size() > 0) {
				for (BeneficaryRelationship beneficaryRelationship : isCoustomerExist) {
					beneficaryAccountList = iPersonalRemittanceService
							.getCustomerBeneficaryDetails(beneficaryRelationship
									.getBeneficaryMaster()
									.getBeneficaryMasterSeqId());
					if (beneficaryAccountList.size() != 0) {
						for (BeneficaryAccount beneficaryAccount : beneficaryAccountList) {

							PersonalRemmitanceBeneficaryDataTable personalRBDataTable = new PersonalRemmitanceBeneficaryDataTable();

							personalRBDataTable.setBeneficaryMasterSeqId(beneficaryAccount.getBeneficaryMaster().getBeneficaryMasterSeqId());
							personalRBDataTable.setBeneficiaryAccountSeqId(beneficaryAccount.getBeneficaryAccountSeqId());
							personalRBDataTable.setBenificaryCountry(beneficaryAccount.getBeneficaryCountry().getCountryId());
							personalRBDataTable.setBenificaryCountryName(beneficaryAccount.getBeneficaryCountry().getFsCountryMasterDescs().get(0).getCountryName());
							personalRBDataTable.setBankAccountNumber(beneficaryAccount.getBankAccountNumber());
							personalRBDataTable.setBenificaryName(beneficaryAccount.getBeneficaryMaster().getFirstName());
							personalRBDataTable.setBranchId(beneficaryAccount.getBankBranch().getBankBranchId());
							personalRBDataTable.setBankName(beneficaryAccount.getBank().getBankFullName());
							personalRBDataTable.setBankId(beneficaryAccount.getBank().getBankId());
							personalRBDataTable.setBankBranchName(beneficaryAccount.getBankBranch().getBranchFullName());
							personalRBDataTable.setLocation(beneficaryAccount.getBeneficaryMaster().getNationality());
							personalRBDataTable.setServiceGroupCode(beneficaryAccount.getServicegropupId().getServiceGroupCode());
							personalRBDataTable.setServiceGroupId(beneficaryAccount.getServicegropupId().getServiceGroupId());
							personalRBDataTable.setCurrencyId(beneficaryAccount.getCurrencyId().getCurrencyId());
							personalRBDataTable.setCurrencyName(beneficaryAccount.getCurrencyId().getCurrencyName());
							personalRBDataTable.setLocation(beneficaryAccount.getBankBranch().getLocation());
							
							
							List<ServiceGroupMasterDesc>  lstServiceGroupMasterDesc = serviceMasterService.LocalServiceGroupDescription(new BigDecimal(sessionmanage.isExists("languageId") ? sessionmanage.getSessionValue("languageId") : "1"),beneficaryAccount.getServicegropupId().getServiceGroupId());
							
							if(lstServiceGroupMasterDesc.size()>0) {
								personalRBDataTable.setServiceGroupName(lstServiceGroupMasterDesc.get(0).getServiceGroupDesc());
							}
							
							/*personalRBDataTable.setServiceName(serviceMasterService.LocalServiceDescriptionFromDB(
									new BigDecimal(sessionmanage.isExists("languageId") ? sessionmanage.getSessionValue("languageId") : "1"),beneficaryAccount.getServicegropupId().getServiceGroupId())));*/
							personalRBDataTable.setBenificaryCountryName(generalService.getCountryName(sessionmanage.getLanguageId(),beneficaryAccount.getBeneficaryCountry().getCountryId()));
							// personalRBDataTable.setTelphoneNum(beneficaryAccount.);

							customerBeneficaryDTList.add(personalRBDataTable);
						}

					}
				}
			}
		}
	}

	// Navigation to Standing Instruction
	public void standingInstrnNavigation() {
		// clear all panels
		mainClear();
		// rendering first panel
		allBeneficiaryDetails();
		// getting all Beneficiary details of Customer
		beneficiaryDetailFromDB();
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../remittance/StandingInstruction.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// selected beneficiary to pop up
	public void selectedBeneficiaryDetails(
			PersonalRemmitanceBeneficaryDataTable personalRemitBeneDT) {
		setCustomerBeneName(personalRemitBeneDT.getBenificaryName());
		setCustomerAccNum(personalRemitBeneDT.getBankAccountNumber());
		setCustomerCurrencyId(personalRemitBeneDT.getCurrencyId());
		setCustomerCurrencyName(personalRemitBeneDT.getCurrencyName());
		setCustomerCountryId(personalRemitBeneDT.getBenificaryCountry());
		setCustomerCountryName(personalRemitBeneDT.getBenificaryCountryName());
		setCustomerBankId(personalRemitBeneDT.getBankId());
		setCustomerBankName(personalRemitBeneDT.getBankName());
		setCustomerBranchId(personalRemitBeneDT.getBranchId());
		setCustomerBranchName(personalRemitBeneDT.getBankBranchName());
		setCustomerBeneAccId(personalRemitBeneDT.getBeneficiaryAccountSeqId());
		setCustomerBeneMasterId(personalRemitBeneDT.getBeneficaryMasterSeqId());
		setCustomerServiceId(personalRemitBeneDT.getServiceGroupId());
		
		currencyMasterList = iPersonalRemittanceService.getCurrencyMasterList(
				sessionmanage.getCountryId(),
				personalRemitBeneDT.getCurrencyId());
	}

	// clearing the first Panel
	public void clearAllBeneficiaryDetails() {
		setCustomerBeneName(null);
		setSelCurrencyCode(null);
		setSendingAmount(null);
		if (currencyMasterList.size() != 0) {
			currencyMasterList.clear();
		}
	}

	// First panel to render - All Beneficiary details
	public void allBeneficiaryDetails() {
		setBooRenderAllBeneDetails(true);
		setBooRenderBeneDtAdditionalDetails(false);
		setBooRenderBeneDtStandInstn(false);
		setBooRenderTokenVerification(false);
		setBooRenderSuccessPage(false);
	}

	// Second panel to render - Beneficiary details and Additional Bank Details
	public void beneficiaryAdditionalBankDetails() {
		setBooRenderAllBeneDetails(false);
		setBooRenderBeneDtAdditionalDetails(true);
		setBooRenderBeneDtStandInstn(false);
		setBooRenderTokenVerification(false);
		setBooRenderSuccessPage(false);
	}

	// Third panel to render - Beneficiary details and Standing Instruction
	// Details
	public void beneficiaryStandingInstrnDetails() {
		setBooRenderAllBeneDetails(false);
		setBooRenderBeneDtAdditionalDetails(false);
		setBooRenderBeneDtStandInstn(true);
		setBooRenderTokenVerification(false);
		setBooRenderSuccessPage(false);
	}

	// Forth panel to render - Token Verification which sent to email
	public void beneficiaryTokenVerification() {
		setBooRenderAllBeneDetails(false);
		setBooRenderBeneDtAdditionalDetails(false);
		setBooRenderBeneDtStandInstn(false);
		setBooRenderTokenVerification(true);
		setBooRenderSuccessPage(false);
	}

	// Forth panel to render - Token Verification which sent to email
	public void beneficiarySuccessPage() {
		setBooRenderAllBeneDetails(false);
		setBooRenderBeneDtAdditionalDetails(false);
		setBooRenderBeneDtStandInstn(false);
		setBooRenderTokenVerification(false);
		setBooRenderSuccessPage(true);
	}

	// setting selected Beneficiary Details in Second Panel
	public void selectedBeneficiaryDetails() {
		// render Second panel
		beneficiaryAdditionalBankDetails();

		// setting standing Instruction concate currency and amount

		for (CurrencyMaster currencycode : currencyMasterList) {
			if (getSelCurrencyCode().compareTo(currencycode.getCurrencyId()) == 0) {
				String standInstrnfor = currencycode.getQuoteName().concat(GetRound.roundBigDecimal(getSendingAmount(),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(getSelCurrencyCode())).toPlainString());
				setCustomerSInstrnCreatedFor(standInstrnfor);
			}
		}
	}

	// clear all Standing Instructions
	public void mainClear() {
		clearAllBeneficiaryDetails();
		clearStandingInstrn();
	}

	private List<AddAdditionalBankData> listAdditionalBankDataTable = new ArrayList<AddAdditionalBankData>();
	private List<AddAdditionalBankData> listAdditionalBankDataTableForSize = new ArrayList<AddAdditionalBankData>();
	private List<AddDynamicLebel> listDynamicLebel = new ArrayList<AddDynamicLebel>();

	public List<AddDynamicLebel> getListDynamicLebel() {
		return listDynamicLebel;
	}

	public void setListDynamicLebel(List<AddDynamicLebel> listDynamicLebel) {
		this.listDynamicLebel = listDynamicLebel;
	}

	public List<AddAdditionalBankData> getListAdditionalBankDataTableForSize() {
		return listAdditionalBankDataTableForSize;
	}

	public void setListAdditionalBankDataTableForSize(
			List<AddAdditionalBankData> listAdditionalBankDataTableForSize) {
		this.listAdditionalBankDataTableForSize = listAdditionalBankDataTableForSize;
	}

	public List<AddAdditionalBankData> getListAdditionalBankDataTable() {
		return listAdditionalBankDataTable;
	}

	public void setListAdditionalBankDataTable(
			List<AddAdditionalBankData> listAdditionalBankDataTable) {
		this.listAdditionalBankDataTable = listAdditionalBankDataTable;
	}

	public void dynamicLevel() {
		/*
		 * listDynamicLebel.clear(); List<ServiceApplicabilityRule>
		 * serviceAppRuleList =
		 * iPersonalRemittanceService.getDynamicLevel(sessionmanage
		 * .getCountryId(), getCurrency(),getRemitMode(), getDeliveryMode());
		 * for (ServiceApplicabilityRule serviceRule : serviceAppRuleList) {
		 * AddDynamicLebel addDynamic = new AddDynamicLebel();
		 * 
		 * 
		 * addDynamic.setLebelId(serviceRule.getServiceApplicabilityRuleId());
		 * 
		 * addDynamic.setFieldLength(serviceRule.getFieldLength());
		 * 
		 * 
		 * if(serviceRule.getMandatory().equalsIgnoreCase("Y")){
		 * addDynamic.setLebelDesc(serviceRule.getFieldDesc());
		 * addDynamic.setFlexiField(serviceRule.getFieldName());
		 * addDynamic.setValidation(serviceRule.getValidate());
		 * if(serviceRule.getValidate().equalsIgnoreCase("Y")){
		 * addDynamic.setMinLenght(serviceRule.getMinLenght());
		 * addDynamic.setMaxLenght(serviceRule.getMaxLenght());
		 * 
		 * addDynamic.setMandatory("*"); addDynamic.setRequired(true); } }
		 * 
		 * listDynamicLebel.add(addDynamic); }
		 */
	}

	public void matchData() {
		listAdditionalBankDataTable.clear();
		// additionalBankRuleData.clear();
		try {/*
			for (AddDynamicLebel dyamicLabel : listDynamicLebel) {
				List<AdditionalBankRuleMap> listAdditinalBankfield = iPersonalRemittanceService.getDynamicLevelMatch(getRoutingCountry(),dyamicLabel.getFlexiField());
				if(listAdditinalBankfield.size()>0){
				for (AdditionalBankRuleMap listAdd : listAdditinalBankfield) {
					AddAdditionalBankData adddata = new AddAdditionalBankData();
					adddata.setAdditionalBankRuleFiledId(listAdd.getAdditionalBankRuleId());
					adddata.setFlexiField(listAdd.getFlexField());
					adddata.setAdditionalDesc(listAdd.getFieldName());
					List<AdditionalBankDetailsView> listAdditionaView = iPersonalRemittanceService.fetchAdditionalDetails(listAdd.getAdditionalBankRuleId());
					if(listAdditionaView.size()>0){
						adddata.setAmicCode(listAdditionaView.get(0).getAmiecCode());
						adddata.setAmicDesc(listAdditionaView.get(0).getAmiecDescription());
					}
					List<AdditionalBankRuleAddData> additionalBankRuleData = new ArrayList<AdditionalBankRuleAddData>();
					additionalBankRuleData.addAll(iPersonalRemittanceService.getAdditionalBankData(getBeneficaryBankId(), listAdd.getAdditionalBankRuleId()));
					for (AdditionalBankRuleAddData additionalBankData : additionalBankRuleData) {
						if (additionalBankRuleData.size() > 0 && listAdd.getAdditionalBankRuleId().intValue() == additionalBankData.getAdditionalBankFieldId().getAdditionalBankRuleId().intValue()) {
							adddata.setRenderInputText(false);
							adddata.setRenderSelect(true);
							adddata.setRenderOneSelect(false);
							adddata.setAdditionalBankRuleData(additionalBankRuleData);
							
						}
					}
					if (dyamicLabel.getValidation().equalsIgnoreCase(Constants.Yes)) {
						adddata.setMandatory(dyamicLabel.getMandatory());
						if(dyamicLabel.getMinLenght() !=null){
						adddata.setMinLenght(dyamicLabel.getMinLenght().intValue());
						}
						adddata.setMaxLenght(dyamicLabel.getMaxLenght());
						adddata.setFieldLength(dyamicLabel.getFieldLength());
						adddata.setRequired(dyamicLabel.getRequired());
					}
					listAdditionalBankDataTable.add(adddata);
				}
			}
			}
		*/} catch (Exception e) {
			log.info(e);
		}
	}

	private int minLenght;
	private BigDecimal additionalbankFieldId;

	public int getMinLenght() {

		// listAdditionalBankDataTable.clear();
		for (AddAdditionalBankData listAdditionals : listAdditionalBankDataTable) {

			setMinLenght(listAdditionals.getMinLenght());
			break;
		}

		return minLenght;
	}

	public void setMinLenght(int minLenght) {
		this.minLenght = minLenght;
	}

	public BigDecimal getAdditionalbankFieldId() {
		return additionalbankFieldId;
	}

	public void setAdditionalbankFieldId(BigDecimal additionalbankFieldId) {
		this.additionalbankFieldId = additionalbankFieldId;
	}

	// Frequency min Date - Starting From Today
	private Date effectiveMinDate = new Date();

	public Date getEffectiveMinDate() {
		return effectiveMinDate;
	}

	public void setEffectiveMinDate(Date effectiveMinDate) {
		this.effectiveMinDate = effectiveMinDate;
	}

	// third Panel Frequency fetching and rendering Panel
	public void frequencyDetails() {
		// rendering third panel
		beneficiaryStandingInstrnDetails();

		// fetching frequency details from DB
		List<RateAlertFrequency> lstFrqDetails = generalService
				.frequencyDetailsLst(new BigDecimal(sessionmanage
						.isExists("languageId") ? sessionmanage
						.getSessionValue("languageId") : "1"));

		if (lstFrqDetails.size() != 0) {
			setLstFrequencyDetails(lstFrqDetails);
			try {
				SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
				Calendar cal = Calendar.getInstance();
				setsIStartdate(myFormat
						.parse(new SimpleDateFormat("dd/MM/YYYY").format(cal
								.getTime())));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void onDateSelect(SelectEvent event) throws ParseException {
	       
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        
        setsIStartdate(format.parse(format.format(event.getObject())));
       
    }

	// calculating the Repeat No of Times For Default 3 Years
	public void calculationRepeatTimes() {

		int days = 0, weeks = 0, months = 0, years = 0;

		SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, 3);

		// Calendar cal1 = Calendar.getInstance();

		try {
			// Date date1 = myFormat.parse(new
			// SimpleDateFormat("dd/MM/YYYY").format(cal1.getTime()));
			Date date1 = getsIStartdate();
			Date date2 = myFormat.parse(new SimpleDateFormat("dd/MM/YYYY")
					.format(cal.getTime()));
			days = Days.daysBetween(new DateTime(date1), new DateTime(date2))
					.getDays();
			weeks = Weeks
					.weeksBetween(new DateTime(date1), new DateTime(date2))
					.getWeeks();
			months = Months.monthsBetween(new DateTime(date1),
					new DateTime(date2)).getMonths();
			years = Years
					.yearsBetween(new DateTime(date1), new DateTime(date2))
					.getYears();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (getsIFrequencyId().compareTo(new BigDecimal(Constants.Daily)) == 0) {
			setsIRepeatNOOFTimes(new BigDecimal(days));
		} else if (getsIFrequencyId().compareTo(new BigDecimal(Constants.Weekly)) == 0) {
			setsIRepeatNOOFTimes(new BigDecimal(weeks));
		} else if (getsIFrequencyId().compareTo(new BigDecimal(Constants.Monthly)) == 0) {
			setsIRepeatNOOFTimes(new BigDecimal(months));
		} else if (getsIFrequencyId().compareTo(new BigDecimal(Constants.Quaterly)) == 0) {
			setsIRepeatNOOFTimes(GetRound.roundBigDecimal(
					new BigDecimal(months).divide(new BigDecimal(4), 4,
							BigDecimal.ROUND_HALF_UP), 0));
		} else if (getsIFrequencyId().compareTo(new BigDecimal(Constants.HalfYearly)) == 0) {
			setsIRepeatNOOFTimes(GetRound.roundBigDecimal(
					new BigDecimal(months).divide(new BigDecimal(6), 6,
							BigDecimal.ROUND_HALF_UP), 0));
		} else if (getsIFrequencyId().compareTo(new BigDecimal(Constants.Annually)) == 0) {
			setsIRepeatNOOFTimes(new BigDecimal(years));
		} else {
			setsIRepeatNOOFTimes(null);
		}

	}

	// token verification with DB
	public void verifyTokenEntered() throws ParseException {
		if (getTokenEntered() != null && !getTokenEntered().equals("")) {
			if(getTokenEntered().equals(getTokenKey())){
				saveStandingInstruction();
				addingRecordstoList();
				beneficiarySuccessPage();
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
				if (getsIStartdate().compareTo(myFormat.parse(new SimpleDateFormat("dd/MM/YYYY").format(cal.getTime()))) == 0) {
					setBooRenderStandingInstrnByDate(true);
				}else{
					setBooRenderStandingInstrnByDate(false);
				}
			}else{
				RequestContext.getCurrentInstance().execute("PF('tokenverify').show();");
			}
		}
	}
	
	public void addingRecordstoList(){
		
		lstStandingInstrn = standingInstructionService.standingInstrnDetailsByID(getStandingInstrnId());
		
		for(StandingInstruction lstStrnInstrn : lstStandingInstrn){
			
			StandingInstructionList stndingInstrn = new StandingInstructionList();
			
			stndingInstrn.setBeneficiaryAccount(lstStrnInstrn.getDebitAccountNo());
			stndingInstrn.setBeneficiaryAccountDtId(lstStrnInstrn.getExbeneficaryAccountSeqId().getBeneficaryAccountSeqId());
			stndingInstrn.setBeneficiaryBankBranchId(lstStrnInstrn.getExBankBranchId().getBankBranchId());
			stndingInstrn.setBeneficiaryBankBranchName(lstStrnInstrn.getExBankBranchId().getBranchFullName());
			stndingInstrn.setBeneficiaryBankId(lstStrnInstrn.getExBankMaster().getBankId());
			stndingInstrn.setBeneficiaryBankName(lstStrnInstrn.getExBankMaster().getBankFullName());
			stndingInstrn.setBeneficiaryCityName(lstStrnInstrn.getExbeneficaryMasterSeqId().getCityName());
			stndingInstrn.setBeneficiaryCurrencyId(lstStrnInstrn.getExCurrencyMasterByForeignCurrencyId().getCurrencyId());
			stndingInstrn.setBeneficiaryCurrencyName(lstStrnInstrn.getExCurrencyMasterByForeignCurrencyId().getCurrencyName());
			stndingInstrn.setBeneficiaryCurrenyShort(lstStrnInstrn.getExCurrencyMasterByForeignCurrencyId().getQuoteName());
			stndingInstrn.setBeneficiaryDueDate(new SimpleDateFormat("dd/MM/yyyy").format(lstStrnInstrn.getEffectiveFromDate()));
			stndingInstrn.setBeneficiaryMasterId(lstStrnInstrn.getExbeneficaryMasterSeqId().getBeneficaryMasterSeqId());
			stndingInstrn.setBeneficiaryStatus(lstStrnInstrn.getIsactive());
			stndingInstrn.setStandingInstnID(lstStrnInstrn.getStandingInstructionSeqId());
			stndingInstrn.setBeneficiaryUserName(lstStrnInstrn.getExbeneficaryMasterSeqId().getFirstName());
			stndingInstrn.setBeneficiaryCustomerNo(lstStrnInstrn.getFsCustomer().getCustomerId());
			stndingInstrn.setStandingAmount(lstStrnInstrn.getAmount());
			
			lstStndingInstrnDetails.add(stndingInstrn);
		}
		
	}

	// save records to standing instruction table
	public void saveStandingInstruction() {

		if (getEmailCheckbox()) {

			StandingInstruction standingInstn = new StandingInstruction();
			
			standingInstn.setStandingInstructionSeqId(getStandingInstrnId());

			standingInstn.setAlertEmail(Constants.Yes);
			standingInstn.setAlertSms(Constants.No);
			standingInstn.setApplicationStatus(Constants.Yes);
			// standingInstn.setCreatedBy();
			standingInstn.setCreatedDate(new Date());
			standingInstn.setCustomerRefNo(getCustomerRefNo());
			standingInstn.setDebitAccountNo(new BigDecimal(getCustomerAccNum())); // to change in table to varchar
			
			lstDocument=generalService.getDocument(new BigDecimal(Constants.STANDING_INSTRUCTION),new BigDecimal(sessionmanage.isExists("languageId") ? sessionmanage.getSessionValue("languageId") : "1"));
			if(lstDocument.size()!=0){
				for(Document lstdoc:lstDocument)
				{
					setDocumentNo(lstdoc.getDocumentCode());
					setDocumentId(lstdoc.getDocumentID());
					setDocumentdesc(lstdoc.getDocumentDesc());
				}
			}
			
			
			List<CompanyMasterDesc> data=generalService.getCompanyList(sessionmanage.getCompanyId(), sessionmanage.getLanguageId());
			if(data.size()!=0){
				setCompanyId(data.get(0).getFsCompanyMaster().getCompanyId());
			}
			
			
			// standingInstn.setDeliverymode();
			 standingInstn.setDocumentNo(getDocumentNo());
			// standingInstn.setEffectiveduration();
			standingInstn.setEffectiveFromDate(getsIStartdate());
			// standingInstn.setExApplicationDocumentNo();

			BankBranch bankbranchid = new BankBranch();
			bankbranchid.setBankBranchId(getCustomerBranchId());
			standingInstn.setExBankBranchId(bankbranchid);

			BankMaster bankmasterid = new BankMaster();
			bankmasterid.setBankId(getCustomerBankId());
			standingInstn.setExBankMaster(bankmasterid);

			BeneficaryAccount beneAccId = new BeneficaryAccount();
			beneAccId.setBeneficaryAccountSeqId(getCustomerBeneAccId());
			standingInstn.setExbeneficaryAccountSeqId(beneAccId);

			BeneficaryMaster beneMasterId = new BeneficaryMaster();
			beneMasterId.setBeneficaryMasterSeqId(getCustomerBeneMasterId());
			standingInstn.setExbeneficaryMasterSeqId(beneMasterId);

			// from session - customer means want to change
			CountryBranch countrybranchid = new CountryBranch();
			countrybranchid.setCountryBranchId(new BigDecimal(sessionmanage.getBranchId()));
			standingInstn.setExCountryBranchId(countrybranchid);

			CurrencyMaster currencyforeignId = new CurrencyMaster();
			currencyforeignId.setCurrencyId(getCustomerCurrencyId());
			standingInstn
					.setExCurrencyMasterByForeignCurrencyId(currencyforeignId);

			CurrencyMaster currencytransId = new CurrencyMaster();
			currencytransId.setCurrencyId(new BigDecimal(sessionmanage.getCurrencyId()));
			standingInstn
					.setExCurrencyMasterByLocalTranxCurrencyId(currencytransId);

			// standingInstn.setExDeliveryMode();
			
			Document documentid = new Document();
			documentid.setDocumentID(getDocumentId());
			standingInstn.setExDocumentId(documentid);
			
			// standingInstn.setExRemittanceMode();
			// standingInstn.setExTransactionDocumentNo();
			
			// to set User Financial Year
			UserFinancialYear userFinancialYear = new UserFinancialYear();
			userFinancialYear.setFinancialYearID(generalService.getDealYear(new Date()).get(0).getFinancialYearID());
		    standingInstn.setExUserFinancialYearByApplicationFinanceYear(userFinancialYear);
		    
		    standingInstn.setExUserFinancialYearByDocumentFinanceYear(generalService.getDealYear(new Date()).get(0).getFinancialYear());
			standingInstn.setExUserFinancialYearByTransactionFinanceYear(generalService.getDealYear(new Date()).get(0).getFinancialYear());
			
			RateAlertFrequency ratefreq = new RateAlertFrequency();
			ratefreq.setOnlineRateAlertId(getsIFrequencyId());
			standingInstn.setFrequency(ratefreq);

			CountryMaster countrymaster = new CountryMaster();
			countrymaster.setCountryId(sessionmanage.getCountryId());
			standingInstn.setFsApplicationCountryMasterId(countrymaster);

			CompanyMaster companyMasterid = new CompanyMaster();
			companyMasterid.setCompanyId(getCompanyId());
			standingInstn.setFsCompanyMasterId(companyMasterid);

			CountryMaster bankcountry = new CountryMaster();
			bankcountry.setCountryId(getCustomerCountryId());
			standingInstn.setFsCountryMasterByBankCountryId(bankcountry);

			CountryMaster correspondingcountry = new CountryMaster();
			correspondingcountry.setCountryId(getCustomerCountryId());
			standingInstn.setFsCountryMasterByCorespondingCountryId(correspondingcountry);

			Customer customerid = new Customer();
			customerid.setCustomerId(getCustomerNo());
			standingInstn.setFsCustomer(customerid);
			
			if(getStandingInstrnId()!=null){
				standingInstn.setIsactive(Constants.Yes);
			}else{
				standingInstn.setIsactive(Constants.U);
				/**Responsible to create token randomly*/
				createTokenId();
			}
			standingInstn.setTokenKey(getTokenKey());
			standingInstn.setAmount(getSendingAmount());
			
			// standingInstn.setModifiedBy();
			// standingInstn.setModifiedDate(new Date());
			// standingInstn.setRemittancemode();
			standingInstn.setRepeatNoofTimes(getsIRepeatNOOFTimes());
			// standingInstn.setStandingInstructionDate();

			standingInstructionService.saveStandingInstruction(standingInstn);
			
			if(getStandingInstrnId() == null){
				
				setStandingInstrnId(standingInstn.getStandingInstructionSeqId());
				
				/**After save first time we have to save the customer ID primary key field, by which we will send mail to that customer*/
				setPkCustomerId(standingInstn.getFsCustomer().getCustomerId());
				
				/**First Time will go the mail, with the generated Token*/
				getMailService().sendTokenMail(getCustomerEmailId(), "Successfully Registered", getPkCustomerId().toPlainString(), getTokenKey());
				
				beneficiaryTokenVerification();
			}/*else{
				lstStandingInstrn.add(standingInstn);
			}*/
			
		} else {
			RequestContext.getCurrentInstance().execute("PF('emailcheckverify').show();");
		}

	}
	
    /***
     * Responsible to send mail, with Customer ID with the Token Number 
     */
    public void createTokenId() {
    	String strToken = null;
		try{
			strToken = getTokenGeneration().getRandomIdentifier(8);
		} catch(Exception e){
			log.info("Problem in Token Generation"+ e);
		}
		
		boolean tokenConfirm = true;
		while(tokenConfirm){
			try{
				if(getRemOnlineReg().CheckTokenAvailable(strToken).size() > 0) {
					tokenConfirm = true;
					strToken = getTokenGeneration().getRandomIdentifier(8);
				}else{
					tokenConfirm = false;
				}
			}catch(Exception e){
				tokenConfirm = false;
			}
		}
		setTokenKey(strToken);
		
    }
    
    public void clearStandingInstrn(){
    	setsIFrequencyId(null);
    	setStandingInstrnId(null);
    	setsIRepeatNOOFTimes(null);
    	setEmailCheckbox(false);
    	setsIStartdate(null);
    	setTokenEntered(null);
    	setBooRenderStandingInstrnByDate(false);
    	if(lstStandingInstrn.size()!=0){
    		lstStandingInstrn.clear();
    	}
    }

}
