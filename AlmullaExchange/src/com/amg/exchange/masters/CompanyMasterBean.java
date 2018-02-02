package com.amg.exchange.masters;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.lang.*;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.SessionScope;

import antlr.StringUtils;

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.model.LanguageType;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.registration.service.ICompanyMasterservice;
import com.amg.exchange.remittance.model.ViewBeneServiceCurrency;
import com.amg.exchange.remittance.service.IPersonalRemittanceService;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.util.CommonUtil;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("companymaster")
@Scope("session")
public class CompanyMasterBean<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger
			.getLogger(CompanyMasterBean.class);
	private BigDecimal companyCode;
	private String companyName;
	private String englishcompanyDescription;
	private String localcompanyDescription;
	private BigDecimal companyId;
	private Date createdDate;
	private String createdBy;
	private Date modifiedDate;
	private String modifiedBy;
	private String isActive;
	private Date approvedDate;
	private String approvedBy;
	private BigDecimal applicationCountryId;
	private String applicationCountryName;
	private Boolean boocompanyCodeReadOnly;
	// desc Table
	private BigDecimal companyDescId;
	private BigDecimal companyEnglishDescId;
	private BigDecimal companyLocalDescId;
	private BigDecimal englishLanguageId;
	private BigDecimal localLanguageId;
	private SessionStateManage session = new SessionStateManage();
	private Boolean editFalg;
	private Boolean booDatatableEnabled;
	private Boolean booSubmitButton;
	private String remarks;
	private String dynamicLabelForActivateDeactivate;
	private boolean hideEdit;
	private boolean ispopulate;
	private Boolean permanetDeleteCheck = false;
	private String errorMessage;
	private Boolean booVisible;

	private String establishYear;
	private String engAddrLine1;
	private String engAddrLine2;
	private String engAddrLine3;
	private String arabicAddrLine1;
	private String arabicAddrLine2;
	private String arabicAddrLine3;
	private String telephoneNum;
	private String email;
	private String faxNum;
	private String telexNo;
	private String registrationNumber;
	private String licNumber;
	private BigDecimal financeYearBgn;
	private BigDecimal financeYearEnd;
	private String amount;
	private String shortName;
	private String arabicShortName;
	private String mnemonic;
	private BigDecimal comMasterCurrencyId;

	private List<CompanyMasterDataTable> companyMasterDataTableList = new ArrayList<CompanyMasterDataTable>();
	private List<CompanyMasterDataTable> companyDataTableApprovalList = new ArrayList<CompanyMasterDataTable>();
	private List<CompanyMasterDesc> viewList = new ArrayList<CompanyMasterDesc>();
	private List<CompanyMaster> viewMasterList = new ArrayList<CompanyMaster>();
	private List<CountryMasterDesc> bankCountryList = new ArrayList<CountryMasterDesc>();
	private List<CurrencyMaster> comMasterCurrencyList = new ArrayList<CurrencyMaster>();
	private Map<BigDecimal, String> bankCountryMap = new HashMap<BigDecimal, String>();
	CompanyMaster companyMaster = null;
	CompanyMasterDesc companyMasterDesc = null;
	@Autowired
	ICompanyMasterservice icompanyMasterservice;
	@Autowired
	IGeneralService<T> generalService;
	@Autowired
	IPersonalRemittanceService iPersonalRemittanceService;

	public BigDecimal getComMasterCurrencyId() {
		return comMasterCurrencyId;
	}

	public void setComMasterCurrencyId(BigDecimal comMasterCurrencyId) {
		this.comMasterCurrencyId = comMasterCurrencyId;
	}

	public List<CurrencyMaster> getComMasterCurrencyList() {
		return comMasterCurrencyList;
	}

	public void setComMasterCurrencyList(List<CurrencyMaster> comMasterCurrencyList) {
		this.comMasterCurrencyList = comMasterCurrencyList;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getArabicShortName() {
		return arabicShortName;
	}

	public void setArabicShortName(String arabicShortName) {
		this.arabicShortName = arabicShortName;
	}

	public String getMnemonic() {
		return mnemonic;
	}

	public void setMnemonic(String mnemonic) {
		this.mnemonic = mnemonic;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public String getLicNumber() {
		return licNumber;
	}

	public void setLicNumber(String licNumber) {
		this.licNumber = licNumber;
	}

	public BigDecimal getFinanceYearBgn() {
		return financeYearBgn;
	}

	public void setFinanceYearBgn(BigDecimal financeYearBgn) {
		this.financeYearBgn = financeYearBgn;
	}

	public BigDecimal getFinanceYearEnd() {
		return financeYearEnd;
	}

	public void setFinanceYearEnd(BigDecimal financeYearEnd) {
		this.financeYearEnd = financeYearEnd;
	}

	public String getTelephoneNum() {
		return telephoneNum;
	}

	public void setTelephoneNum(String telephoneNum) {
		this.telephoneNum = telephoneNum;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFaxNum() {
		return faxNum;
	}

	public void setFaxNum(String faxNum) {
		this.faxNum = faxNum;
	}

	public String getTelexNo() {
		return telexNo;
	}

	public void setTelexNo(String telexNo) {
		this.telexNo = telexNo;
	}

	public String getEstablishYear() {
		return establishYear;
	}

	public void setEstablishYear(String establishYear) {
		this.establishYear = establishYear;
	}

	public String getEngAddrLine1() {
		return engAddrLine1;
	}

	public void setEngAddrLine1(String engAddrLine1) {
		this.engAddrLine1 = engAddrLine1;
	}

	public String getEngAddrLine2() {
		return engAddrLine2;
	}

	public void setEngAddrLine2(String engAddrLine2) {
		this.engAddrLine2 = engAddrLine2;
	}

	public String getEngAddrLine3() {
		return engAddrLine3;
	}

	public void setEngAddrLine3(String engAddrLine3) {
		this.engAddrLine3 = engAddrLine3;
	}

	public String getArabicAddrLine1() {
		return arabicAddrLine1;
	}

	public void setArabicAddrLine1(String arabicAddrLine1) {
		this.arabicAddrLine1 = arabicAddrLine1;
	}

	public String getArabicAddrLine2() {
		return arabicAddrLine2;
	}

	public void setArabicAddrLine2(String arabicAddrLine2) {
		this.arabicAddrLine2 = arabicAddrLine2;
	}

	public String getArabicAddrLine3() {
		return arabicAddrLine3;
	}

	public void setArabicAddrLine3(String arabicAddrLine3) {
		this.arabicAddrLine3 = arabicAddrLine3;
	}

	public Boolean getBooVisible() {
		return booVisible;
	}

	public List<CompanyMasterDataTable> getCompanyDataTableApprovalList() {
		return companyDataTableApprovalList;
	}

	public void setCompanyDataTableApprovalList(
			List<CompanyMasterDataTable> companyDataTableApprovalList) {
		this.companyDataTableApprovalList = companyDataTableApprovalList;
	}

	public void setBooVisible(Boolean booVisible) {
		this.booVisible = booVisible;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Boolean getPermanetDeleteCheck() {
		return permanetDeleteCheck;
	}

	public void setPermanetDeleteCheck(Boolean permanetDeleteCheck) {
		this.permanetDeleteCheck = permanetDeleteCheck;
	}

	public List<CountryMasterDesc> getBankCountryList() {
		bankCountryList = generalService
				.getCountryList(session.getLanguageId());
		for (CountryMasterDesc bankMaster : bankCountryList) {
			bankCountryMap.put(bankMaster.getFsCountryMaster().getCountryId(),
					bankMaster.getCountryName());
		}
		return bankCountryList;
	}

	public String getApplicationCountryName() {
		return applicationCountryName;
	}

	public void setApplicationCountryName(String applicationCountryName) {
		this.applicationCountryName = applicationCountryName;
	}

	public void setBankCountryList(List<CountryMasterDesc> bankCountryList) {
		this.bankCountryList = bankCountryList;
	}

	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}

	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}

	public BigDecimal getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(BigDecimal companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getEnglishcompanyDescription() {
		return englishcompanyDescription;
	}

	public void setEnglishcompanyDescription(String englishcompanyDescription) {
		this.englishcompanyDescription = englishcompanyDescription;
	}

	public String getLocalcompanyDescription() {
		return localcompanyDescription;
	}

	public void setLocalcompanyDescription(String localcompanyDescription) {
		this.localcompanyDescription = localcompanyDescription;
	}

	public BigDecimal getCompanyId() {
		return companyId;
	}

	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createDate) {
		this.createdDate = createDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Boolean getBoocompanyCodeReadOnly() {
		return boocompanyCodeReadOnly;
	}

	public void setBoocompanyCodeReadOnly(Boolean boocompanyCodeReadOnly) {
		this.boocompanyCodeReadOnly = boocompanyCodeReadOnly;
	}

	public BigDecimal getCompanyDescId() {
		return companyDescId;
	}

	public void setCompanyDescId(BigDecimal companyDescId) {
		this.companyDescId = companyDescId;
	}

	public BigDecimal getCompanyEnglishDescId() {
		return companyEnglishDescId;
	}

	public void setCompanyEnglishDescId(BigDecimal companyEnglishDescId) {
		this.companyEnglishDescId = companyEnglishDescId;
	}

	public BigDecimal getCompanyLocalDescId() {
		return companyLocalDescId;
	}

	public void setCompanyLocalDescId(BigDecimal companyLocalDescId) {
		this.companyLocalDescId = companyLocalDescId;
	}

	public BigDecimal getEnglishLanguageId() {
		return englishLanguageId;
	}

	public void setEnglishLanguageId(BigDecimal englishLanguageId) {
		this.englishLanguageId = englishLanguageId;
	}

	public BigDecimal getLocalLanguageId() {
		return localLanguageId;
	}

	public void setLocalLanguageId(BigDecimal localLanguageId) {
		this.localLanguageId = localLanguageId;
	}

	public Boolean getEditFalg() {
		return editFalg;
	}

	public void setEditFalg(Boolean editFalg) {
		this.editFalg = editFalg;
	}

	public Boolean getBooDatatableEnabled() {
		return booDatatableEnabled;
	}

	public void setBooDatatableEnabled(Boolean booDatatableEnabled) {
		this.booDatatableEnabled = booDatatableEnabled;
	}

	public Boolean getBooSubmitButton() {
		return booSubmitButton;
	}

	public void setBooSubmitButton(Boolean booSubmitButton) {
		this.booSubmitButton = booSubmitButton;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}

	public void setDynamicLabelForActivateDeactivate(
			String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}

	public boolean isHideEdit() {
		return hideEdit;
	}

	public void setHideEdit(boolean hideEdit) {
		this.hideEdit = hideEdit;
	}

	public boolean isIspopulate() {
		return ispopulate;
	}

	public void setIspopulate(boolean ispopulate) {
		this.ispopulate = ispopulate;
	}

	public List<CompanyMasterDataTable> getCompanyMasterDataTableList() {
		return companyMasterDataTableList;
	}

	public void setCompanyMasterDataTableList(
			List<CompanyMasterDataTable> companyMasterDataTableList) {
		this.companyMasterDataTableList = companyMasterDataTableList;
	}

	public CompanyMaster getCompanyMaster() {
		return companyMaster;
	}

	public void setCompanyMaster(CompanyMaster companyMaster) {
		this.companyMaster = companyMaster;
	}

	public CompanyMasterDesc getCompanyMasterDesc() {
		return companyMasterDesc;
	}

	public void setCompanyMasterDesc(CompanyMasterDesc companyMasterDesc) {
		this.companyMasterDesc = companyMasterDesc;
	}

	public ICompanyMasterservice getIcompanyMasterservice() {
		return icompanyMasterservice;
	}

	public void setIcompanyMasterservice(
			ICompanyMasterservice icompanyMasterservice) {
		this.icompanyMasterservice = icompanyMasterservice;
	}

	public IPersonalRemittanceService getiPersonalRemittanceService() {
		return iPersonalRemittanceService;
	}

	public void setiPersonalRemittanceService(
			IPersonalRemittanceService iPersonalRemittanceService) {
		this.iPersonalRemittanceService = iPersonalRemittanceService;
	}

	public void add() {
		try {
			LOGGER.info("Entering into add method");
			boolean alreadyDT = false;
			setBooVisible(false);
			// Data table duplicatecheck
			if (companyMasterDataTableList.size() != 0) {
				for (CompanyMasterDataTable rticleMasterDataTable : companyMasterDataTableList) {
					// Data table duplicate check - company code check
					if (rticleMasterDataTable.getCompanyCode() != null
							&& rticleMasterDataTable.getCompanyCode().equals(
									getCompanyCode())) {
						RequestContext context = RequestContext
								.getCurrentInstance();
						context.execute("companyCodeExist.show();");
						alreadyDT = true;
						break;
					}
					// Data table duplicate check - English description code
					// check
					if (rticleMasterDataTable.getEnglishcompanyDescription() != null
							&& rticleMasterDataTable
									.getEnglishcompanyDescription()
									.equalsIgnoreCase(
											getEnglishcompanyDescription())) {
						RequestContext context = RequestContext
								.getCurrentInstance();
						context.execute("EnglishDescriptionExist.show();");
						alreadyDT = true;
						setEnglishcompanyDescription(null);
						setLocalcompanyDescription(null);
						break;
					}
					// Data table duplicate check - local description code check
					if (rticleMasterDataTable.getLocalcompanyDescription() != null
							&& rticleMasterDataTable
									.getLocalcompanyDescription()
									.equalsIgnoreCase(
											getLocalcompanyDescription())) {
						RequestContext context = RequestContext
								.getCurrentInstance();
						context.execute("LocalDescriptionExist.show();");
						setEnglishcompanyDescription(null);
						setLocalcompanyDescription(null);
						alreadyDT = true;
						break;
					}
				}
			}
			boolean alreadyDB = false;
			// Data table duplicatecheck
			if (!alreadyDT) {
				if (getCompanyId() == null) {
					CompanyMaster exist = icompanyMasterservice
							.viewByCode(getCompanyCode());
					if (exist != null && exist.getCompanyId() != null) {
						alreadyDB = true;
						RequestContext context = RequestContext
								.getCurrentInstance();
						context.execute("companyCodeExist.show();");
					} else {
						List<CompanyMasterDesc> local = icompanyMasterservice
								.checkDesciption(getLocalcompanyDescription());
						List<CompanyMasterDesc> english = icompanyMasterservice
								.checkDesciption(getEnglishcompanyDescription());
						if (local != null && local.size() > 0) {
							alreadyDB = true;
							RequestContext context = RequestContext
									.getCurrentInstance();
							context.execute("EnglishDescriptionExist.show();");
							setEnglishcompanyDescription(null);
							setLocalcompanyDescription(null);
						} else if (english != null && english.size() > 0) {
							alreadyDB = true;
							RequestContext context = RequestContext
									.getCurrentInstance();
							context.execute("LocalDescriptionExist.show();");
							setEnglishcompanyDescription(null);
							setLocalcompanyDescription(null);
						}
					}
				} else {
					alreadyDB = false;
				}
			}
			if (!alreadyDT && !alreadyDB) {
				CompanyMasterDataTable dataTable = new CompanyMasterDataTable();
				dataTable.setCompanyId(getCompanyId());
				/* dataTable.setCompanyId(session.getCompanyId()); */
				LOGGER.info("code" + getCompanyCode());
				LOGGER.info("English" + getEnglishcompanyDescription());
				dataTable
						.setEnglishcompanyDescription(getEnglishcompanyDescription());
				dataTable
						.setLocalcompanyDescription(getLocalcompanyDescription());
				dataTable.setCompanyName(getEnglishcompanyDescription());
				dataTable.setCompanyCode(getCompanyCode());
				dataTable
						.setDynamicLabelForActivateDeactivate(setreverActiveStatus(getIsActive()));
				if (dataTable.getDynamicLabelForActivateDeactivate().equals(
						Constants.DELETE)) {
					if (dataTable.getModifiedBy() == null
							&& dataTable.getModifiedDate() == null) {
					} else {
						dataTable.setDynamicLabelForActivateDeactivate("");
					}
				}
				dataTable.setApplicationCountryId(getApplicationCountryId());
				dataTable.setApplicationCountryName(bankCountryMap
						.get(getApplicationCountryId()));
				// In first Time
				if (!editFalg && !ispopulate) {
					dataTable.setEnglishLanguageId(new BigDecimal(
							Constants.ENGLISH_LANGUAGE_ID));
					dataTable.setLocalLanguageId((new BigDecimal(
							Constants.ARABIC_LANGUAGE_ID)));
					dataTable.setCreatedBy(session.getUserName());
					dataTable.setCreatedDate(new Timestamp(System
							.currentTimeMillis()));
					dataTable.setIsActive(Constants.U);
					dataTable
							.setDynamicLabelForActivateDeactivate(setreverActiveStatus(null));
				} else {
					dataTable.setEnglishLanguageId(new BigDecimal(
							Constants.ENGLISH_LANGUAGE_ID));
					dataTable.setLocalLanguageId(new BigDecimal(
							Constants.ARABIC_LANGUAGE_ID));
					dataTable.setCreatedBy(getCreatedBy());
					dataTable.setCreatedDate(getCreatedDate());
					dataTable.setIsActive(getIsActive());
					dataTable
							.setDynamicLabelForActivateDeactivate(setreverActiveStatus(getIsActive()));
					dataTable.setIsActive(Constants.U);
					dataTable.setModifiedBy(session.getUserName());
					dataTable.setModifiedDate(new Date());
					if (dataTable.getDynamicLabelForActivateDeactivate()
							.equals(Constants.DELETE)) {
						if (dataTable.getModifiedBy() == null
								&& dataTable.getModifiedDate() == null) {
						} else {
							dataTable.setDynamicLabelForActivateDeactivate("");
						}
					}
				}
				dataTable.setApprovedDate(getApprovedDate());
				dataTable.setApprovedBy(getApprovedBy());
				dataTable.setRemarks(getRemarks());
				// dataTable.setcompanyDescId(getcompanyDescId());
				dataTable.setCompanyEnglishDescId(getCompanyEnglishDescId());
				dataTable.setCompanyLocalDescId(getCompanyLocalDescId());
				LOGGER.info("Entered value is ");
				LOGGER.info(dataTable);
				companyMasterDataTableList.add(dataTable);
				setBooDatatableEnabled(true);
				setBooSubmitButton(false);
				// setBoocompanyCodeReadOnly(false);
				clearAll();
			}
			setHideEdit(false);
			LOGGER.info("Exit into add method");
		} catch (Exception e) {
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage(e.toString());
			setBooVisible(true);
			return;
		}
	}
	
	public boolean validations(){
		boolean rtnValue=true;
		if(getCompanyCode()==null){
			rtnValue=false;
			RequestContext.getCurrentInstance().execute("comCode.show();");
		}else if(getApplicationCountryId()==null){
			rtnValue=false;
			RequestContext.getCurrentInstance().execute("appCountryId.show();");
		}else if(getEnglishcompanyDescription()==null || getEnglishcompanyDescription().length()==0){
			rtnValue=false;
			RequestContext.getCurrentInstance().execute("engFullName.show();");
		}else if(getLocalcompanyDescription()==null || getLocalcompanyDescription().length()==0){
			rtnValue=false;
			RequestContext.getCurrentInstance().execute("arbFullName.show();");
		}else if(getMnemonic()==null || getMnemonic().length()==0){
			rtnValue=false;
			RequestContext.getCurrentInstance().execute("mnemonic.show();");
		}else if(getEstablishYear()==null || getEstablishYear().length()==0){
			rtnValue=false;
			RequestContext.getCurrentInstance().execute("establishYear.show();");
		}else if(getComMasterCurrencyId()==null){
			rtnValue=false;
			RequestContext.getCurrentInstance().execute("shareCapital.show();");
		}else if(getAmount()==null || getAmount().length()==0){
			rtnValue=false;
			RequestContext.getCurrentInstance().execute("amount.show();");
		}else if(getRegistrationNumber()==null || getRegistrationNumber().length()==0){
			rtnValue=false;
			RequestContext.getCurrentInstance().execute("regNum.show();");
		}else if(getLicNumber()==null || getLicNumber().length()==0){
			rtnValue=false;
			RequestContext.getCurrentInstance().execute("licNum.show();");
		}else if(getFinanceYearBgn()==null){
			rtnValue=false;
			RequestContext.getCurrentInstance().execute("finYrBgn.show();");
		}else if(getFinanceYearEnd()==null){
			rtnValue=false;
			RequestContext.getCurrentInstance().execute("financeYrEnd.show();");
		}
		return rtnValue;
	}

	public void saveCompanyValues() {
		try {
			boolean rtnValue=validations();
			if(!rtnValue)
			{
				return;
			}
			CompanyMaster saveCompanyMaster = new CompanyMaster();
			if (getCompanyId() != null) {
				saveCompanyMaster.setCompanyId(getCompanyId());
			}
			saveCompanyMaster.setCompanyCode(getCompanyCode());
			//saveCompanyMaster.setCompanyInitial(getMnemonic());
			saveCompanyMaster.setEstYear(getEstablishYear());
			saveCompanyMaster.setAddress1(getEngAddrLine1());
			saveCompanyMaster.setAddress2(getEngAddrLine2());
			saveCompanyMaster.setAddress3(getEngAddrLine3());
			saveCompanyMaster.setArabicAddress1(getArabicAddrLine1());
			saveCompanyMaster.setArabicAddress2(getArabicAddrLine2());
			saveCompanyMaster.setArabicAddress3(getArabicAddrLine3());
			saveCompanyMaster.setTelephoneNo(getTelephoneNum());
			saveCompanyMaster.setEmail(getEmail());
			saveCompanyMaster.setFaxNo(getFaxNum());
			saveCompanyMaster.setTelexNo(getTelexNo());
			saveCompanyMaster.setCurrencyId(getComMasterCurrencyId());
			saveCompanyMaster.setCapitalAmount(getAmount());
			saveCompanyMaster.setRegistrationNumber(getRegistrationNumber());
			saveCompanyMaster.setLicNumber(getLicNumber());
			saveCompanyMaster.setFinbgn(getFinanceYearBgn());
			saveCompanyMaster.setFinend(getFinanceYearEnd());			
			
			if(getCompanyId() == null){
				saveCompanyMaster.setCreatedBy(session.getUserName());
				saveCompanyMaster.setModifiedBy(session.getUserName());
				saveCompanyMaster.setCreatedDate(new Date());
				saveCompanyMaster.setModifiedDate(new Date());
				/*saveCompanyMaster.setApprovedBy(getApprovedBy());
				saveCompanyMaster.setApprovedDate(getApprovedDate());
				saveCompanyMaster.setRemarks(getRemarks());
				saveCompanyMaster.setIsActive(getIsActive());*/
			}else{
				saveCompanyMaster.setCreatedBy(getCreatedBy());
				saveCompanyMaster.setModifiedBy(session.getUserName());
				saveCompanyMaster.setCreatedDate(getCreatedDate());
				saveCompanyMaster.setModifiedDate(new Date());
				saveCompanyMaster.setApprovedBy(getApprovedBy());
				saveCompanyMaster.setApprovedDate(getApprovedDate());
				saveCompanyMaster.setRemarks(getRemarks());
				saveCompanyMaster.setIsActive(getIsActive());
			}

			CountryMaster countryMaster = new CountryMaster();
			countryMaster.setCountryId(getApplicationCountryId());
			saveCompanyMaster.setCountryMaster(countryMaster);

			List<CompanyMasterDesc> comDesc = new ArrayList<CompanyMasterDesc>();
			
			//English
			CompanyMasterDesc companyMasterDescEnglish = new CompanyMasterDesc();
			
			LanguageType ltype = new LanguageType();					
			if(getCompanyId() == null){
				setEnglishLanguageId(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID));
				ltype.setLanguageId(getEnglishLanguageId());
			}else{
				ltype.setLanguageId(getEnglishLanguageId());
			}
			companyMasterDescEnglish.setFsLanguageType(ltype);	
			companyMasterDescEnglish.setCompanyMasterId(getCompanyEnglishDescId());
			companyMasterDescEnglish.setCompanyName(getEnglishcompanyDescription());
			//companyMasterDescEnglish.setCompanyShortName(getShortName());
			
			comDesc.add(companyMasterDescEnglish);
			
				
			
			//Arabic
			CompanyMasterDesc companyMasterDescArabic = new CompanyMasterDesc();
			
			ltype = new LanguageType();			
			if(getCompanyId() == null){
				setLocalLanguageId(new BigDecimal(Constants.ARABIC_LANGUAGE_ID));
				ltype.setLanguageId(getLocalLanguageId());
			}else{
				ltype.setLanguageId(getLocalLanguageId());
			}
			companyMasterDescArabic.setFsLanguageType(ltype);
			companyMasterDescArabic.setCompanyMasterId(getCompanyLocalDescId());
			companyMasterDescArabic.setCompanyName(getLocalcompanyDescription());			
			//companyMasterDescArabic.setCompanyShortName(getArabicShortName());
			
			comDesc.add(companyMasterDescArabic);

			// Save method.
			icompanyMasterservice.saveCompanyValues(saveCompanyMaster, comDesc);
			clearAll();
			RequestContext.getCurrentInstance().execute("successMsg.show();");		

		} catch (Exception e) {
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage(e.toString());
		}
	}
	
	public void exit() {
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../registration/branchhome.xhtml");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void save() {
		try {
			LOGGER.info("Entering into save method");
			CompanyMaster savecompanyMaster = null;
			setBooVisible(false);
			CompanyMasterDesc companyMasterDesc = null;
			List<CompanyMasterDesc> desc = new ArrayList<CompanyMasterDesc>();
			for (CompanyMasterDataTable dataTable : companyMasterDataTableList) {
				savecompanyMaster = null;
				companyMasterDesc = null;
				desc.clear();
				LOGGER.info("Input values *************************************************");
				LOGGER.info(dataTable);
				LOGGER.info("Input values *************************************************");
				savecompanyMaster = new CompanyMaster();
				savecompanyMaster.setCompanyId(dataTable.getCompanyId());
				CountryMaster fsCountryMaster = new CountryMaster();
				fsCountryMaster.setCountryId(dataTable
						.getApplicationCountryId());
				savecompanyMaster.setCountryMaster(fsCountryMaster);
				savecompanyMaster.setCompanyId(dataTable.getCompanyId());
				savecompanyMaster.setCreatedBy(dataTable.getCreatedBy());
				savecompanyMaster.setModifiedBy(dataTable.getModifiedBy());
				savecompanyMaster.setCreatedDate(dataTable.getCreatedDate());
				savecompanyMaster.setModifiedDate(dataTable.getModifiedDate());
				savecompanyMaster.setCountryMaster(fsCountryMaster);
				savecompanyMaster.setApprovedBy(dataTable.getApprovedBy());
				savecompanyMaster.setApprovedDate(dataTable.getApprovedDate());
				savecompanyMaster.setRemarks(dataTable.getRemarks());
				savecompanyMaster.setIsActive(dataTable.getIsActive());
				savecompanyMaster.setCompanyCode(dataTable.getCompanyCode());

				try {
					/* icompanyMasterservice.save(savecompanyMaster); */
					for (int i = 0; i < 2; i++) {
						companyMasterDesc = null;
						companyMasterDesc = new CompanyMasterDesc();
						companyMasterDesc.setCompanyMasterId(dataTable
								.getCompanyDescId());
						companyMasterDesc.setFsCompanyMaster(savecompanyMaster);
						if (i == 0) {
							companyMasterDesc.setCompanyName(dataTable
									.getEnglishcompanyDescription());
							LanguageType ltype = new LanguageType();
							ltype.setLanguageId(dataTable
									.getEnglishLanguageId());
							companyMasterDesc.setFsLanguageType(ltype);
							companyMasterDesc.setCompanyMasterId(dataTable
									.getCompanyEnglishDescId());
						}
						if (i == 1) {
							LanguageType ltype = new LanguageType();
							ltype.setLanguageId(dataTable.getLocalLanguageId());
							companyMasterDesc.setFsLanguageType(ltype);
							companyMasterDesc.setCompanyName(dataTable
									.getLocalcompanyDescription());
							companyMasterDesc.setCompanyMasterId(dataTable
									.getCompanyLocalDescId());
						}
						desc.add(companyMasterDesc);
					}
					icompanyMasterservice.save(savecompanyMaster, desc);
					dataTable = null;
					RequestContext context = RequestContext
							.getCurrentInstance();
					context.execute("succsses.show();");
				} catch (Exception e) {
					RequestContext.getCurrentInstance()
							.execute("error.show();");
					setErrorMessage(e.toString());
					setBooVisible(true);
					return;
				}
			}
			LOGGER.info("Exit into save method");
		} catch (Exception e) {
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage(e.toString());
			setBooVisible(true);
			return;
		}
	}
	
	public void clear(){
		setApplicationCountryId(null);
		setLocalcompanyDescription(null);
		setEnglishcompanyDescription(null);
		setEnglishcompanyDescription(null);
		setLocalcompanyDescription(null);
		setEstablishYear(null);
		setEngAddrLine1(null);
		setEngAddrLine2(null);
		setEngAddrLine3(null);
		setArabicAddrLine1(null);
		setArabicAddrLine2(null);
		setArabicAddrLine3(null);
		setTelephoneNum(null);
		setEmail(null);
		setFaxNum(null);
		setTelexNo(null);
		setRegistrationNumber(null);
		setLicNumber(null);
		setFinanceYearBgn(null);
		setFinanceYearEnd(null);
		setAmount(null);
		setShortName(null);
		setArabicShortName(null);
		setMnemonic(null);
		setComMasterCurrencyId(null);
	}

	public void clearAll() {
		setApplicationCountryId(null);
		setCompanyId(null);
		setCreatedDate(null);
		setCreatedBy(null);
		setModifiedBy(null);
		setModifiedDate(null);
		setIsActive(null);
		setApprovedDate(null);
		setApprovedBy(null);
		setCompanyCode(null);
		setLocalcompanyDescription(null);
		setEnglishcompanyDescription(null);
		setRemarks(null);
		setEnglishcompanyDescription(null);
		setLocalcompanyDescription(null);
		setLocalLanguageId(null);
		setEnglishLanguageId(null);
		setBoocompanyCodeReadOnly(null);
		setCompanyEnglishDescId(null);
		setCompanyLocalDescId(null);
		ispopulate = false;

		setEstablishYear(null);
		setEngAddrLine1(null);
		setEngAddrLine2(null);
		setEngAddrLine3(null);
		setArabicAddrLine1(null);
		setArabicAddrLine2(null);
		setArabicAddrLine3(null);
		setTelephoneNum(null);
		setEmail(null);
		setFaxNum(null);
		setTelexNo(null);
		setRegistrationNumber(null);
		setLicNumber(null);
		setFinanceYearBgn(null);
		setFinanceYearEnd(null);
		setAmount(null);
		setShortName(null);
		setArabicShortName(null);
		setMnemonic(null);
		setComMasterCurrencyId(null);
	}

	public void checkStatus(CompanyMasterDataTable CompanyMasterDataTable) {
		try {
			setBooVisible(false);
			LOGGER.info("Entering into checkStatus method");
			if (CompanyMasterDataTable.getDynamicLabelForActivateDeactivate()
					.equalsIgnoreCase("DeActivate")) {
				CompanyMasterDataTable.setRemarksCheck(true);
				LOGGER.info("Approved By"
						+ CompanyMasterDataTable.getApprovedBy());
				LOGGER.info("Approved Date"
						+ CompanyMasterDataTable.getApprovedDate());
				setApprovedBy(CompanyMasterDataTable.getApprovedBy());
				setApprovedDate(CompanyMasterDataTable.getApprovedDate());
				RequestContext.getCurrentInstance().execute(
						"remarksMsg.show();");
			} else if (CompanyMasterDataTable
					.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(
							Constants.DELETE)
					&& CompanyMasterDataTable.getModifiedBy() == null
					&& CompanyMasterDataTable.getModifiedDate() == null
					&& CompanyMasterDataTable.getApprovedBy() == null
					&& CompanyMasterDataTable.getApprovedDate() == null
					&& CompanyMasterDataTable.getRemarks() == null) {
				CompanyMasterDataTable.setPermanetDeleteCheck(true);
				RequestContext.getCurrentInstance().execute(
						"permanentDelete.show();");
				return;
			} else {
				removefromDataTable(CompanyMasterDataTable);
			}
			LOGGER.info("Exit into checkStatus method");
		} catch (Exception e) {
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage(e.toString());
			setBooVisible(true);
			return;
		}
	}

	public void removefromDataTable(CompanyMasterDataTable dataTable) {
		LOGGER.info("Entering into removefromDataTable method");
		List<CompanyMasterDesc> desc = new ArrayList<CompanyMasterDesc>();
		if ((dataTable.getDynamicLabelForActivateDeactivate() != null
				&& (dataTable.getDynamicLabelForActivateDeactivate()
						.equalsIgnoreCase(Constants.DELETE)) || dataTable
				.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(
						Constants.REMOVE))) {
			if (dataTable.getCompanyId() != null) {
				CompanyMaster savecompanyMaster = new CompanyMaster();
				savecompanyMaster.setCompanyId(dataTable.getCompanyId());
				if (dataTable.getModifiedBy() == null
						&& dataTable.getModifiedDate() == null
						&& dataTable.getApprovedBy() == null
						&& dataTable.getApprovedDate() == null
						&& dataTable.getRemarks() == null) {
					companyMasterDataTableList.remove(dataTable);
					try {
						desc.clear();
						List<CompanyMasterDesc> list = icompanyMasterservice
								.viewById(savecompanyMaster.getCompanyId());
						for (CompanyMasterDesc companyMasterDesc : list) {
							LOGGER.info(companyMasterDesc.getCompanyMasterId());
							desc.add(companyMasterDesc);
						}
						icompanyMasterservice.delete(savecompanyMaster, desc);
						desc.clear();
						RequestContext.getCurrentInstance().execute(
								"deleteSuccess.show();");
					} catch (Exception e) {
						LOGGER.info("Exception occured while deleting the record "
								+ e);
						RequestContext.getCurrentInstance().execute(
								"deleteFailure.show();");
					}
				} else {
					CompanyMasterDesc companyMasterDesc = null;
					LOGGER.info("Input values *************************************************");
					LOGGER.info(dataTable);
					LOGGER.info("Input values *************************************************");
					CountryMaster fsCountryMaster = new CountryMaster();
					fsCountryMaster.setCountryId(dataTable
							.getApplicationCountryId());
					savecompanyMaster.setCountryMaster(fsCountryMaster);
					savecompanyMaster.setCreatedBy(dataTable.getCreatedBy());
					savecompanyMaster.setModifiedBy(session.getUserName());
					savecompanyMaster
							.setCreatedDate(dataTable.getCreatedDate());
					savecompanyMaster.setModifiedDate(new Date());
					savecompanyMaster.setApprovedBy(dataTable.getApprovedBy());
					savecompanyMaster.setApprovedDate(dataTable
							.getApprovedDate());
					savecompanyMaster.setRemarks(dataTable.getRemarks());
					if (dataTable.getIsActive().equalsIgnoreCase(Constants.U)) {
						savecompanyMaster.setIsActive(dataTable.getIsActive());
						savecompanyMaster.setRemarks(null);
						savecompanyMaster.setApprovedBy(null);
						savecompanyMaster.setApprovedDate(null);
					} else {
						savecompanyMaster.setIsActive(Constants.D);
					}
					savecompanyMaster
							.setCompanyCode(dataTable.getCompanyCode());
					try {
						/* icompanyMasterservice.save(companyMaster); */
						for (int i = 0; i < 2; i++) {
							companyMasterDesc = new CompanyMasterDesc();
							companyMasterDesc
									.setFsCompanyMaster(savecompanyMaster);
							if (i == 0) {
								companyMasterDesc.setCompanyName(dataTable
										.getEnglishcompanyDescription());
								companyMasterDesc.setCompanyMasterId(dataTable
										.getCompanyEnglishDescId());
								LanguageType ltype = new LanguageType();
								ltype.setLanguageId(dataTable
										.getEnglishLanguageId());
								companyMasterDesc.setFsLanguageType(ltype);
								;
							}
							if (i == 1) {
								LanguageType ltype = new LanguageType();
								ltype.setLanguageId(dataTable
										.getLocalLanguageId());
								companyMasterDesc.setFsLanguageType(ltype);
								companyMasterDesc.setCompanyName(dataTable
										.getLocalcompanyDescription());
								companyMasterDesc.setCompanyMasterId(dataTable
										.getCompanyLocalDescId());
							}
							desc.add(companyMasterDesc);
						}
						icompanyMasterservice.save(savecompanyMaster, desc);
						// companyMasterDataTableList.remove(dataTable);
					} catch (Exception e) {
						LOGGER.info("Exception occured while insert data in companyMasterBean"
								+ e);
						RequestContext context = RequestContext
								.getCurrentInstance();
						context.execute("failure.show();");
					}
					RequestContext context = RequestContext
							.getCurrentInstance();
					context.execute("alreadymodifed.show();");
				}
			} else {
				companyMasterDataTableList.remove(dataTable);
				if (companyMasterDataTableList.size() == 0) {
					setBooDatatableEnabled(false);
					clearAll();
				}
			}
		} else if ((dataTable.getDynamicLabelForActivateDeactivate() != null
				&& (dataTable.getDynamicLabelForActivateDeactivate()
						.equalsIgnoreCase(Constants.ACTIVATE)) || dataTable
				.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(
						Constants.DEACTIVATE))) {
			CompanyMaster savecompanyMaster = null;
			CompanyMasterDesc companyMasterDesc = null;
			LOGGER.info("Input values *************************************************");
			LOGGER.info(dataTable);
			LOGGER.info("Input values *************************************************");
			savecompanyMaster = new CompanyMaster();
			savecompanyMaster.setCompanyId(dataTable.getCompanyId());
			CountryMaster fsCountryMaster = new CountryMaster();
			fsCountryMaster.setCountryId(dataTable.getApplicationCountryId());
			savecompanyMaster.setCountryMaster(fsCountryMaster);
			savecompanyMaster.setCreatedBy(dataTable.getCreatedBy());
			savecompanyMaster.setModifiedBy(dataTable.getModifiedBy());
			savecompanyMaster.setCreatedDate(dataTable.getCreatedDate());
			savecompanyMaster.setModifiedDate(dataTable.getModifiedDate());
			savecompanyMaster.setApprovedBy(dataTable.getApprovedBy());
			savecompanyMaster.setApprovedDate(dataTable.getApprovedDate());
			savecompanyMaster.setRemarks(dataTable.getRemarks());
			savecompanyMaster.setIsActive(dataTable.getIsActive());
			savecompanyMaster.setCompanyCode(dataTable.getCompanyCode());
			if (dataTable.getDynamicLabelForActivateDeactivate()
					.equalsIgnoreCase(Constants.ACTIVATE)) {
				dataTable.setIsActive(Constants.U);
				savecompanyMaster.setIsActive(Constants.U);
				savecompanyMaster.setRemarks(null);
				savecompanyMaster.setApprovedBy(null);
				savecompanyMaster.setApprovedDate(null);
				companyMasterDataTableList.remove(dataTable);
			} else if (dataTable.getDynamicLabelForActivateDeactivate()
					.equalsIgnoreCase(Constants.DEACTIVATE)) {
				dataTable.setIsActive(Constants.D);
				savecompanyMaster.setIsActive(Constants.D);
				dataTable
						.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
			}
			desc.clear();
			try {
				for (int i = 0; i < 2; i++) {
					companyMasterDesc = new CompanyMasterDesc();
					companyMasterDesc.setFsCompanyMaster(savecompanyMaster);
					if (i == 0) {
						companyMasterDesc.setCompanyName(dataTable
								.getEnglishcompanyDescription());
						companyMasterDesc.setCompanyMasterId(dataTable
								.getCompanyEnglishDescId());
						LanguageType ltype = new LanguageType();
						ltype.setLanguageId(dataTable.getEnglishLanguageId());
						companyMasterDesc.setFsLanguageType(ltype);
						;
					}
					if (i == 1) {
						LanguageType ltype = new LanguageType();
						ltype.setLanguageId(dataTable.getLocalLanguageId());
						companyMasterDesc.setFsLanguageType(ltype);
						companyMasterDesc.setCompanyName(dataTable
								.getLocalcompanyDescription());
						companyMasterDesc.setCompanyMasterId(dataTable
								.getCompanyLocalDescId());
					}
					//
					desc.add(companyMasterDesc);
					companyMasterDesc = null;
				}
				icompanyMasterservice.save(savecompanyMaster, desc);
			} catch (Exception e) {
				LOGGER.info("Exception occured while insert data in companyMasterBean"
						+ e);
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("failure.show();");
			}
		}
	}

	public void clickOnExit() throws IOException {
		if (session.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../registration/employeehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../registration/branchhome.xhtml");
		}
	}

	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;

	public void pageNavigation() {
		editFalg = false;
		setBoocompanyCodeReadOnly(null);
		LOGGER.info("Entering into pageNavigation");
		clearAll();
		setBooDatatableEnabled(false);
		companyMasterDataTableList.clear();
		comMasterCurrencyList = generalService.getCurrencyList();
		if (session.getUserName() == null) {
		}
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(
					session.getCountryId(), session.getUserType(),
					session.getUserName(), "companymaster.xhtml");
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../customermaster/companymaster.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("Exit  into pageNavigation");
	}

	public String updatedCustomerType(String customerType) {
		String updatedCustomerType = null;
		if (customerType != null
				&& customerType.equalsIgnoreCase(Constants.INDIVIDUAL)) {
			updatedCustomerType = Constants.Individual;
		} else if (customerType != null
				&& customerType.equalsIgnoreCase(Constants.Corporate)) {
			updatedCustomerType = Constants.NonIndividual;
		}
		if (customerType != null
				&& customerType.equalsIgnoreCase(Constants.Individual)) {
			updatedCustomerType = Constants.INDIVIDUAL;
		} else if (customerType != null
				&& customerType.equalsIgnoreCase(Constants.NonIndividual)) {
			updatedCustomerType = Constants.Corporate;
		}
		return updatedCustomerType;
	}

	// TO POPULATE THE RELATION CODE BASED ON GIVEN ENTRY
	public List<String> autoCompleteData(String query) {
		if (query.length() > 0) {
			return icompanyMasterservice.getAllComponent(query);
		} else {
			return null;
		}
	}

	public void populatecompanyMaster() {
		try {
			ispopulate = true;
			setBooVisible(false);
			setCompanyId(null);
			LOGGER.info("Enter into populatecompanyMaster method : companyMasterBean ");
			LOGGER.info("company Code " + getCompanyCode());			
			if (getCompanyCode() != null) {
				companyMaster = icompanyMasterservice
						.viewByCode(getCompanyCode());
				if (companyMaster != null
						&& companyMaster.getCompanyId() != null) {
					// CompanyMasterDataTable dataTable = new
					// CompanyMasterDataTable();
					setCompanyId(companyMaster.getCompanyId());
					setApprovedBy(companyMaster.getApprovedBy());
					setApprovedDate(companyMaster.getApprovedDate());
					setModifiedBy(companyMaster.getModifiedBy());
					setModifiedDate(companyMaster.getModifiedDate());
					setRemarks(companyMaster.getRemarks());
					setIsActive(companyMaster.getIsActive());
					setCreatedDate(companyMaster.getCreatedDate());
					setCreatedBy(companyMaster.getCreatedBy());
					setApplicationCountryId(companyMaster.getCountryMaster()
							.getCountryId());
					setEstablishYear(companyMaster.getEstYear());
					setEngAddrLine1(companyMaster.getAddress1());
					setEngAddrLine2(companyMaster.getAddress2());
					setEngAddrLine3(companyMaster.getAddress3());
					setArabicAddrLine1(companyMaster.getArabicAddress1());
					setArabicAddrLine2(companyMaster.getArabicAddress2());
					setArabicAddrLine3(companyMaster.getArabicAddress3());
					setTelephoneNum(companyMaster.getTelephoneNo());
					setEmail(companyMaster.getEmail());
					setFaxNum(companyMaster.getFaxNo());
					setTelexNo(companyMaster.getTelexNo());
					setRegistrationNumber(companyMaster.getRegistrationNumber());
					setLicNumber(companyMaster.getLicNumber());
					setFinanceYearBgn(companyMaster.getFinbgn());
					setFinanceYearEnd(companyMaster.getFinend());
					setAmount(companyMaster.getCapitalAmount());
					//setMnemonic(companyMaster.getCompanyInitial());
					/*comMasterCurrencyList = iPersonalRemittanceService.getViewBeneCurrency(getApplicationCountryId());*/
					setComMasterCurrencyId(companyMaster.getCurrencyId());
					viewList = icompanyMasterservice.viewById(companyMaster
							.getCompanyId());
					LOGGER.info(viewList == null);
					if (viewList != null && viewList.size() != 0) {
						for (CompanyMasterDesc companyMasterDesc : viewList) {
							if (companyMasterDesc
									.getFsLanguageType()
									.getLanguageId()
									.equals(new BigDecimal(
											Constants.ENGLISH_LANGUAGE_ID))) {
								setEnglishLanguageId(companyMasterDesc
										.getFsLanguageType().getLanguageId());
								setEnglishcompanyDescription(companyMasterDesc
										.getCompanyName());
								setCompanyEnglishDescId(companyMasterDesc
										.getCompanyMasterId());
								//setShortName(companyMasterDesc.getCompanyShortName());
							} else if (companyMasterDesc
									.getFsLanguageType()
									.getLanguageId()
									.equals(new BigDecimal(
											Constants.ARABIC_LANGUAGE_ID))) {
								setLocalLanguageId(companyMasterDesc
										.getFsLanguageType().getLanguageId());
								setLocalcompanyDescription(companyMasterDesc
										.getCompanyName());
								setCompanyLocalDescId(companyMasterDesc
										.getCompanyMasterId());
								//setArabicShortName(companyMasterDesc.getCompanyShortName());
							}
						}
					} else {
						LOGGER.info("Child table data not available for this id "
								+ companyMaster.getCompanyId());
					}
					// LOGGER.info(dataTable);
					// companyMasterDataTableList.add(dataTable);
				}else{
					clear();
					RequestContext.getCurrentInstance().execute("noRecFound.show();");
				}
			}else{
				clearAll();
			}			
			LOGGER.info("Exit into populatecompanyMaster method : companyMasterBean ");
		} catch (Exception e) {
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage(e.toString());
			setBooVisible(true);
			return;
		}
	}	
	

	public void editRecord(CompanyMasterDataTable dataTable) {
		try {
			setEditFalg(true);
			setBooVisible(false);
			setHideEdit(true);
			setCompanyId(dataTable.getCompanyId());
			setApprovedBy(null);
			setApprovedDate(null);
			setCompanyCode(dataTable.getCompanyCode());
			setRemarks(null);
			if (dataTable.getCompanyId() == null) {
				setModifiedBy(null);
				setModifiedDate(null);
				editFalg = false;
			} else {
				setModifiedBy(session.getUserName());
				setModifiedDate(new Date());
				setBoocompanyCodeReadOnly(true);
			}
			setIsActive(Constants.U);
			setCreatedDate(dataTable.getCreatedDate());
			setCreatedBy(dataTable.getCreatedBy());
			setEnglishLanguageId(dataTable.getEnglishLanguageId());
			setEnglishcompanyDescription(dataTable
					.getEnglishcompanyDescription());
			setCompanyEnglishDescId(dataTable.getCompanyEnglishDescId());
			setCompanyLocalDescId(dataTable.getCompanyLocalDescId());
			setLocalLanguageId(dataTable.getLocalLanguageId());
			setLocalcompanyDescription(dataTable.getLocalcompanyDescription());
			setApplicationCountryId(dataTable.getApplicationCountryId());
			companyMasterDataTableList.remove(dataTable);
			setHideEdit(false);
		} catch (Exception e) {
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage(e.toString());
			setBooVisible(true);
			return;
		}
	}

	public void view() {
		try {
			LOGGER.info("Entering into view method");
			companyMasterDataTableList.clear();
			setBooVisible(false);
			LOGGER.info("Enter into view method");
			setBooSubmitButton(true);
			viewMasterList = icompanyMasterservice.viewMasterRecords();
			for (CompanyMaster tempObj : viewMasterList) {
				if (tempObj.getCompanyId() != null) {
					setBooDatatableEnabled(true);
					CompanyMasterDataTable dataTable = new CompanyMasterDataTable();
					dataTable.setCompanyId(tempObj.getCompanyId());
					dataTable.setApprovedBy(tempObj.getApprovedBy());
					dataTable.setApprovedDate(tempObj.getApprovedDate());
					dataTable.setCompanyCode(tempObj.getCompanyCode());
					dataTable.setModifiedBy(tempObj.getModifiedBy());
					dataTable.setModifiedDate(tempObj.getModifiedDate());
					dataTable.setRemarks(tempObj.getRemarks());
					dataTable.setIsActive(tempObj.getIsActive());
					dataTable
							.setDynamicLabelForActivateDeactivate(setreverActiveStatus(tempObj
									.getIsActive()));
					if (dataTable.getDynamicLabelForActivateDeactivate()
							.equals(Constants.DELETE)) {
						if (dataTable.getModifiedBy() == null
								&& dataTable.getModifiedDate() == null) {
						} else {
							dataTable
									.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
						}
					}
					dataTable.setCreatedDate(tempObj.getCreatedDate());
					dataTable.setCreatedBy(tempObj.getCreatedBy());
					dataTable.setApplicationCountryId(tempObj
							.getCountryMaster().getCountryId());
					dataTable.setApplicationCountryName(bankCountryMap
							.get(tempObj.getCountryMaster().getCountryId()));
					LOGGER.info("company Id " + tempObj.getCompanyId());
					viewList = icompanyMasterservice.viewById(tempObj
							.getCompanyId());
					LOGGER.info(viewList == null);
					if (viewList != null && viewList.size() != 0) {
						for (CompanyMasterDesc companyMasterDesc : viewList) {
							if (companyMasterDesc
									.getFsLanguageType()
									.getLanguageId()
									.equals(new BigDecimal(
											Constants.ENGLISH_LANGUAGE_ID))) {
								dataTable
										.setEnglishLanguageId(companyMasterDesc
												.getFsLanguageType()
												.getLanguageId());
								dataTable
										.setEnglishcompanyDescription(companyMasterDesc
												.getCompanyName());
								dataTable
										.setCompanyEnglishDescId(companyMasterDesc
												.getCompanyMasterId());
							} else if (companyMasterDesc
									.getFsLanguageType()
									.getLanguageId()
									.equals(new BigDecimal(
											Constants.ARABIC_LANGUAGE_ID))) {
								dataTable.setLocalLanguageId(companyMasterDesc
										.getFsLanguageType().getLanguageId());
								dataTable
										.setLocalcompanyDescription(companyMasterDesc
												.getCompanyName());
								dataTable
										.setCompanyLocalDescId(companyMasterDesc
												.getCompanyMasterId());
							}
						}
					}
					LOGGER.info(dataTable);
					companyMasterDataTableList.add(dataTable);
				}
			}
			LOGGER.info(viewList.size());
			setBooDatatableEnabled(true);
			setHideEdit(false);
			LOGGER.info("Exit into view method");
		} catch (Exception e) {
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage(e.toString());
			setBooVisible(true);
			return;
		}
	}

	private String setreverActiveStatus(String status) {
		LOGGER.info("Entering into setreverActiveStatus method");
		String strStatus = null;
		if (status == null) {
			strStatus = Constants.REMOVE;
		} else if (status.equalsIgnoreCase(Constants.U)) {
			strStatus = Constants.DELETE;
		} else if (status.equalsIgnoreCase(Constants.D)) {
			strStatus = Constants.ACTIVATE;
		} else if (status.equalsIgnoreCase(Constants.Yes)) {
			strStatus = Constants.DEACTIVATE;
		}
		LOGGER.info("strStatus " + strStatus);
		LOGGER.info("Exit into setreverActiveStatus method");
		return strStatus;
	}

	public void clickOK() throws IOException {
		LOGGER.info("Entering into clickOK method");
		LOGGER.info(getRemarks());
		if (getRemarks() != null && !getRemarks().equals("")) {
			for (CompanyMasterDataTable dataTable : companyMasterDataTableList) {
				if (dataTable.getRemarksCheck() != null) {
					if (dataTable.getRemarksCheck().equals(true)) {
						dataTable.setRemarks(getRemarks());
						removefromDataTable(dataTable);
						setRemarks(null);
					}
				}
			}
			try {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("../customermaster/companymaster.xhtml");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			RequestContext.getCurrentInstance().execute("remarksEmpty.show();");
		}
		LOGGER.info("Exit into clickOK method");
	}

	public void cancelRemarks() {
		LOGGER.info("Entering into cancelRemarks method");
		setRemarks(null);
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../customermaster/companymaster.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
		LOGGER.info("Exit into cancelRemarks method");
	}

	@Override
	public String toString() {
		return "CompanyMasterBean [companyCode=" + companyCode
				+ ", companyName=" + companyName
				+ ", englishcompanyDescription=" + englishcompanyDescription
				+ ", localcompanyDescription=" + localcompanyDescription
				+ ", companyId=" + companyId + ", createdDate=" + createdDate
				+ ", createdBy=" + createdBy + ", modifiedDate=" + modifiedDate
				+ ", modifiedBy=" + modifiedBy + ", isActive=" + isActive
				+ ", approvedDate=" + approvedDate + ", approvedBy="
				+ approvedBy + ", applicationCountryId=" + applicationCountryId
				+ ", applicationCountryName=" + applicationCountryName
				+ ", boocompanyCodeReadOnly=" + boocompanyCodeReadOnly
				+ ", companyDescId=" + companyDescId
				+ ", companyEnglishDescId=" + companyEnglishDescId
				+ ", companyLocalDescId=" + companyLocalDescId
				+ ", englishLanguageId=" + englishLanguageId
				+ ", localLanguageId=" + localLanguageId + ", session="
				+ session + ", editFalg=" + editFalg + ", booDatatableEnabled="
				+ booDatatableEnabled + ", booSubmitButton=" + booSubmitButton
				+ ", remarks=" + remarks
				+ ", dynamicLabelForActivateDeactivate="
				+ dynamicLabelForActivateDeactivate + ", hideEdit=" + hideEdit
				+ ", ispopulate=" + ispopulate + "]";
	}

	// ******************************************* approval starts here

	public void pageApprovalNavigation() {
		editFalg = false;
		System.out
				.println("Entering into serviceBenificiaryApprovalNavigation");
		setBooDatatableEnabled(false);
		companyDataTableApprovalList.clear();
		getBankCountryList();
		viewforApproval();
		if (session.getUserName() == null) {
		}
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(
					session.getCountryId(), session.getUserType(),
					session.getUserName(), "companymasterapproval.xhtml");
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../customermaster/companymasterapproval.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Exit  into serviceBenificiaryApprovalNavigation");
	}

	private void viewforApproval() {
		LOGGER.info("Entering into view method");
		companyDataTableApprovalList.clear();
		LOGGER.info("Enter into view method");
		setBooSubmitButton(true);
		List<CompanyMaster> viewMasterListforApproval = new ArrayList<CompanyMaster>();
		List<CompanyMasterDesc> viewList = new ArrayList<CompanyMasterDesc>();
		viewMasterListforApproval = icompanyMasterservice
				.viewMasterRecordsforApproval();
		for (CompanyMaster tempObj : viewMasterListforApproval) {
			if (tempObj.getCompanyId() != null) {
				setBooDatatableEnabled(true);
				CompanyMasterDataTable dataTable = new CompanyMasterDataTable();
				dataTable.setCompanyId(tempObj.getCompanyId());
				dataTable.setApprovedBy(tempObj.getApprovedBy());
				dataTable.setApprovedDate(tempObj.getApprovedDate());
				dataTable.setCompanyCode(tempObj.getCompanyCode());
				dataTable.setModifiedBy(tempObj.getModifiedBy());
				dataTable.setModifiedDate(tempObj.getModifiedDate());
				dataTable.setRemarks(tempObj.getRemarks());
				dataTable.setIsActive(tempObj.getIsActive());
				dataTable
						.setDynamicLabelForActivateDeactivate(setreverActiveStatus(tempObj
								.getIsActive()));
				if (dataTable.getDynamicLabelForActivateDeactivate().equals(
						Constants.DELETE)) {
					if (dataTable.getModifiedBy() == null
							&& dataTable.getModifiedDate() == null) {
					} else {
						dataTable.setDynamicLabelForActivateDeactivate("");
					}
				}
				dataTable.setCreatedDate(tempObj.getCreatedDate());
				dataTable.setCreatedBy(tempObj.getCreatedBy());
				dataTable.setApplicationCountryId(tempObj.getCountryMaster()
						.getCountryId());
				dataTable.setApplicationCountryName(bankCountryMap.get(tempObj
						.getCountryMaster().getCountryId()));
				LOGGER.info("company Id " + tempObj.getCompanyId());
				viewList = icompanyMasterservice.viewById(tempObj
						.getCompanyId());
				LOGGER.info(viewList == null);
				if (viewList != null && viewList.size() != 0) {
					for (CompanyMasterDesc companyMasterDesc : viewList) {
						if (companyMasterDesc
								.getFsLanguageType()
								.getLanguageId()
								.equals(new BigDecimal(
										Constants.ENGLISH_LANGUAGE_ID))) {
							dataTable.setEnglishLanguageId(companyMasterDesc
									.getFsLanguageType().getLanguageId());
							dataTable
									.setEnglishcompanyDescription(companyMasterDesc
											.getCompanyName());
							dataTable.setCompanyEnglishDescId(companyMasterDesc
									.getCompanyMasterId());
						} else if (companyMasterDesc
								.getFsLanguageType()
								.getLanguageId()
								.equals(new BigDecimal(
										Constants.ARABIC_LANGUAGE_ID))) {
							dataTable.setLocalLanguageId(companyMasterDesc
									.getFsLanguageType().getLanguageId());
							dataTable
									.setLocalcompanyDescription(companyMasterDesc
											.getCompanyName());
							dataTable.setCompanyLocalDescId(companyMasterDesc
									.getCompanyMasterId());
						}
					}
				}
				LOGGER.info(dataTable);
				companyDataTableApprovalList.add(dataTable);
			}
		}
		LOGGER.info(viewList.size());
		setBooDatatableEnabled(true);
		setHideEdit(false);
		LOGGER.info("Exit into view method");
	}

	public void gotoApproval(CompanyMasterDataTable dataTable) {
		setBooVisible(false);
		try {
			if (dataTable.getModifiedBy() == null) {
				if (CommonUtil.validateApprovedBy(session.getUserName(),
						dataTable.getCreatedBy())) {
					RequestContext.getCurrentInstance().execute(
							"notAuth.show();");
					return;
				}
			} else if (dataTable.getModifiedBy() != null) {
				if (CommonUtil.validateApprovedBy(session.getUserName(),
						dataTable.getModifiedBy())) {
					RequestContext.getCurrentInstance().execute(
							"notAuth.show();");
					return;
				}
			}
			setCompanyId(dataTable.getCompanyId());
			setCompanyCode(dataTable.getCompanyCode());
			setApplicationCountryId(dataTable.getApplicationCountryId());
			setApplicationCountryName(dataTable.getApplicationCountryName());
			setApprovedBy(dataTable.getApprovedBy());
			setApprovedDate(dataTable.getApprovedDate());
			setCompanyCode(dataTable.getCompanyCode());
			setModifiedBy(dataTable.getModifiedBy());
			setModifiedDate(dataTable.getModifiedDate());
			setRemarks(dataTable.getRemarks());
			setIsActive(dataTable.getIsActive());
			// dataTable.setDynamicLabelForActivateDeactivate(setreverActiveStatus(tempObj.getIsActive()));
			setCreatedDate(dataTable.getCreatedDate());
			setCreatedBy(dataTable.getCreatedBy());
			setEnglishcompanyDescription(dataTable
					.getEnglishcompanyDescription());
			setLocalcompanyDescription(dataTable.getLocalcompanyDescription());
			setEnglishLanguageId(dataTable.getEnglishLanguageId());
			try {
				FacesContext
						.getCurrentInstance()
						.getExternalContext()
						.redirect(
								"../customermaster/companymasterFinalApproval.xhtml");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage(e.toString());
			setBooVisible(true);
			return;
		}
	}

	public void gotoFinalApproval() {
		try {
			setBooVisible(false);
			try {
				LOGGER.info("getCompanyId" + getCompanyId());
				icompanyMasterservice.approveRecord(getCompanyId(),
						session.getUserName());
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("approve.show();");
			} catch (Exception e) {
				System.out
						.println("Exception occured while insert data in ArticleMasterBean"
								+ e);
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("failure.show();");
			}
		} catch (Exception e) {
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage(e.toString());
			setBooVisible(true);
			return;
		}
	}

	public void confirmPermanentDelete() {
		if (companyMasterDataTableList.size() > 0) {
			CompanyMasterDataTable companyMasterDataTable = null;
			List<CompanyMasterDesc> desc = new ArrayList<CompanyMasterDesc>();
			List<CompanyMasterDataTable> tempist = new CopyOnWriteArrayList<CompanyMasterDataTable>();
			// Collections.copy(tempist,countrymasterDataTableList);
			tempist.clear();
			for (CompanyMasterDataTable com : companyMasterDataTableList) {
				tempist.add(com);
			}
			Iterator<CompanyMasterDataTable> iter = tempist.iterator();
			while (iter.hasNext()) {
				companyMasterDataTable = iter.next();
				if (companyMasterDataTable.getPermanetDeleteCheck() != null
						&& companyMasterDataTable.getPermanetDeleteCheck()
								.equals(true)) {
					tempist.remove(companyMasterDataTable);
					CompanyMaster savecompanyMaster = new CompanyMaster();
					savecompanyMaster.setCompanyId(companyMasterDataTable
							.getCompanyId());
					try {
						desc.clear();
						List<CompanyMasterDesc> list = icompanyMasterservice
								.viewById(savecompanyMaster.getCompanyId());
						for (CompanyMasterDesc companyMasterDesc : list) {
							LOGGER.info(companyMasterDesc.getCompanyMasterId());
							desc.add(companyMasterDesc);
						}
						icompanyMasterservice.delete(savecompanyMaster, desc);
						desc.clear();
						RequestContext.getCurrentInstance().execute(
								"deleteSuccess.show();");
					} catch (Exception e) {
						LOGGER.info("Exception occured while deleting the record "
								+ e);
						RequestContext.getCurrentInstance().execute(
								"deleteFailure.show();");
					}
				}
			}
			companyMasterDataTableList.clear();
			for (CompanyMasterDataTable data : tempist) {
				companyMasterDataTableList.add(data);
			}
		}
	}
}
