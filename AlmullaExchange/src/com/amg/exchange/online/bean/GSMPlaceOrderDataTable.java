package com.amg.exchange.online.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.amg.exchange.remittance.bean.PopulateData;

public class GSMPlaceOrderDataTable implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal rateOfferedPk;
	private BigDecimal customerId;
	private String customerName;
	private String currencyName;
	private BigDecimal currencyId;
	private BigDecimal amount;
	private BigDecimal beneficiaryBankId;
	private String beneficiaryBankName;
	private BigDecimal routingBankId;
	private BigDecimal actualRate;
	private BigDecimal rateOffered;
	private Date valueDate;
	private String specialOrCommonPoolIndicator;
	private BigDecimal beneficiaryCountryId;
	private BigDecimal remittanceType;
	private BigDecimal applicationCountryId;
	private BigDecimal beneficiaryBranchId;
	private String serviceGroupCode;
	private BigDecimal accountSeqId;
	private String remittanceName;
	private BigDecimal dataserviceid;
	private BigDecimal routingCountry;
	private String routingCountryName;
	private String routingBankName;
	private String customerEmailId;
	private BigDecimal countryBranchId;
	private BigDecimal countryBranchCode;
	private String countryBranchName;
	private BigDecimal rateOfferMinRate;
	private BigDecimal rateOfferMaxRate;
	private Boolean selectCheckBox=false;
	private String beneficiaryCountryName;
	private BigDecimal beneficiaryId;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String isActive;
	private String transctionConculed;
	private BigDecimal documentNumber;
	private BigDecimal documentFinanceYear;
	private String view;
	private BigDecimal customerReference;
	private String beneficiaryName;
	private String customerRefandName;
	private BigDecimal rateOfferedLastTanx;
	private BigDecimal viewtrnxAmount;
	private BigDecimal viewCurrencyId;
	private String viewCurrencyName;
	private Date viewCreatedDate;
	private BigDecimal viewBeneBankId;
	private String viewBeneBankName;
	private BigDecimal viewCountryBranchId;
	private String viewCountryBranchName;
	private BigDecimal viewBeneCounteyId;
	private String viewBeneCountryName;
	private BigDecimal viewBeneId;
	private String viewBeneName;
	private BigDecimal viewRemitType;
	private String viewRemitName;
	private String negotiate;
	private BigDecimal viewCustomerId;
	private String viewCustomerName;
	private Boolean booReadOnlyCheckBox=false;
	private BigDecimal requestCurrencyId;
	private String requestcurrencyName;
	private Boolean boonegotiate=false;
	private BigDecimal viewCustomerRef;
	private BigDecimal viewAccountSeqId;
	private List<PopulateData> routingbankvalues;
	private List<PopulateData> routingCountryvalues;
	
	public BigDecimal getRateOfferedPk() {
		return rateOfferedPk;
	}
	public void setRateOfferedPk(BigDecimal rateOfferedPk) {
		this.rateOfferedPk = rateOfferedPk;
	}
	
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	
	public BigDecimal getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public BigDecimal getBeneficiaryBankId() {
		return beneficiaryBankId;
	}
	public void setBeneficiaryBankId(BigDecimal beneficiaryBankId) {
		this.beneficiaryBankId = beneficiaryBankId;
	}
	
	public String getBeneficiaryBankName() {
		return beneficiaryBankName;
	}
	public void setBeneficiaryBankName(String beneficiaryBankName) {
		this.beneficiaryBankName = beneficiaryBankName;
	}
	
	public BigDecimal getRoutingBankId() {
		return routingBankId;
	}
	public void setRoutingBankId(BigDecimal routingBankId) {
		this.routingBankId = routingBankId;
	}
	
	public String getRoutingCountryName() {
		return routingCountryName;
	}
	public void setRoutingCountryName(String routingCountryName) {
		this.routingCountryName = routingCountryName;
	}
	
	public BigDecimal getRateOffered() {
		return rateOffered;
	}
	public void setRateOffered(BigDecimal rateOffered) {
		this.rateOffered = rateOffered;
	}
	
	public Date getValueDate() {
		return valueDate;
	}
	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}
	
	public String getSpecialOrCommonPoolIndicator() {
		return specialOrCommonPoolIndicator;
	}
	public void setSpecialOrCommonPoolIndicator(String specialOrCommonPoolIndicator) {
		this.specialOrCommonPoolIndicator = specialOrCommonPoolIndicator;
	}
	
	public BigDecimal getBeneficiaryCountryId() {
		return beneficiaryCountryId;
	}
	public void setBeneficiaryCountryId(BigDecimal beneficiaryCountryId) {
		this.beneficiaryCountryId = beneficiaryCountryId;
	}
	
	public BigDecimal getRemittanceType() {
		return remittanceType;
	}
	public void setRemittanceType(BigDecimal remittanceType) {
		this.remittanceType = remittanceType;
	}
	
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}
	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}
	
	public BigDecimal getBeneficiaryBranchId() {
		return beneficiaryBranchId;
	}
	public void setBeneficiaryBranchId(BigDecimal beneficiaryBranchId) {
		this.beneficiaryBranchId = beneficiaryBranchId;
	}
	
	public String getServiceGroupCode() {
		return serviceGroupCode;
	}
	public void setServiceGroupCode(String serviceGroupCode) {
		this.serviceGroupCode = serviceGroupCode;
	}
	
	public BigDecimal getAccountSeqId() {
		return accountSeqId;
	}
	public void setAccountSeqId(BigDecimal accountSeqId) {
		this.accountSeqId = accountSeqId;
	}
	
	public String getRemittanceName() {
		return remittanceName;
	}
	public void setRemittanceName(String remittanceName) {
		this.remittanceName = remittanceName;
	}
	
	public BigDecimal getDataserviceid() {
		return dataserviceid;
	}
	public void setDataserviceid(BigDecimal dataserviceid) {
		this.dataserviceid = dataserviceid;
	}
	
	public BigDecimal getRoutingCountry() {
		return routingCountry;
	}
	public void setRoutingCountry(BigDecimal routingCountry) {
		this.routingCountry = routingCountry;
	}
	
	public BigDecimal getRateOfferMinRate() {
		return rateOfferMinRate;
	}
	public void setRateOfferMinRate(BigDecimal rateOfferMinRate) {
		this.rateOfferMinRate = rateOfferMinRate;
	}
	
	public BigDecimal getRateOfferMaxRate() {
		return rateOfferMaxRate;
	}
	public void setRateOfferMaxRate(BigDecimal rateOfferMaxRate) {
		this.rateOfferMaxRate = rateOfferMaxRate;
	}
	
	public Boolean getSelectCheckBox() {
		return selectCheckBox;
	}
	public void setSelectCheckBox(Boolean selectCheckBox) {
		this.selectCheckBox = selectCheckBox;
	}
	
	public String getBeneficiaryCountryName() {
		return beneficiaryCountryName;
	}
	public void setBeneficiaryCountryName(String beneficiaryCountryName) {
		this.beneficiaryCountryName = beneficiaryCountryName;
	}
	
	public BigDecimal getBeneficiaryId() {
		return beneficiaryId;
	}
	public void setBeneficiaryId(BigDecimal beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
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
	
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
	public String getTransctionConculed() {
		return transctionConculed;
	}
	public void setTransctionConculed(String transctionConculed) {
		this.transctionConculed = transctionConculed;
	}
	
	public BigDecimal getDocumentNumber() {
		return documentNumber;
	}
	public void setDocumentNumber(BigDecimal documentNumber) {
		this.documentNumber = documentNumber;
	}
	
	public BigDecimal getDocumentFinanceYear() {
		return documentFinanceYear;
	}
	public void setDocumentFinanceYear(BigDecimal documentFinanceYear) {
		this.documentFinanceYear = documentFinanceYear;
	}
	
	public String getView() {
		return view;
	}
	public void setView(String view) {
		this.view = view;
	}
	
	public BigDecimal getCustomerReference() {
		return customerReference;
	}
	public void setCustomerReference(BigDecimal customerReference) {
		this.customerReference = customerReference;
	}
	
	public String getRoutingBankName() {
		return routingBankName;
	}
	public void setRoutingBankName(String routingBankName) {
		this.routingBankName = routingBankName;
	}
	
	public List<PopulateData> getRoutingbankvalues() {
		return routingbankvalues;
	}
	public void setRoutingbankvalues(List<PopulateData> routingbankvalues) {
		this.routingbankvalues = routingbankvalues;
	}
	
	public String getCustomerEmailId() {
		return customerEmailId;
	}
	public void setCustomerEmailId(String customerEmailId) {
		this.customerEmailId = customerEmailId;
	}
	
	public BigDecimal getCountryBranchId() {
		return countryBranchId;
	}
	public void setCountryBranchId(BigDecimal countryBranchId) {
		this.countryBranchId = countryBranchId;
	}
	
	public String getCountryBranchName() {
		return countryBranchName;
	}
	public void setCountryBranchName(String countryBranchName) {
		this.countryBranchName = countryBranchName;
	}
	
	public String getBeneficiaryName() {
		return beneficiaryName;
	}
	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}
	
	public String getCustomerRefandName() {
		return customerRefandName;
	}
	public void setCustomerRefandName(String customerRefandName) {
		this.customerRefandName = customerRefandName;
	}
	
	public BigDecimal getRateOfferedLastTanx() {
		return rateOfferedLastTanx;
	}
	public void setRateOfferedLastTanx(BigDecimal rateOfferedLastTanx) {
		this.rateOfferedLastTanx = rateOfferedLastTanx;
	}
	
	public String getNegotiate() {
		return negotiate;
	}
	public void setNegotiate(String negotiate) {
		this.negotiate = negotiate;
	}
	
	public Boolean getBoonegotiate() {
		return boonegotiate;
	}
	public void setBoonegotiate(Boolean boonegotiate) {
		this.boonegotiate = boonegotiate;
	}
	
	public BigDecimal getViewtrnxAmount() {
		return viewtrnxAmount;
	}
	public void setViewtrnxAmount(BigDecimal viewtrnxAmount) {
		this.viewtrnxAmount = viewtrnxAmount;
	}
	
	public BigDecimal getViewCurrencyId() {
		return viewCurrencyId;
	}
	public void setViewCurrencyId(BigDecimal viewCurrencyId) {
		this.viewCurrencyId = viewCurrencyId;
	}
	
	public String getViewCurrencyName() {
		return viewCurrencyName;
	}
	public void setViewCurrencyName(String viewCurrencyName) {
		this.viewCurrencyName = viewCurrencyName;
	}
	
	public Date getViewCreatedDate() {
		return viewCreatedDate;
	}
	public void setViewCreatedDate(Date viewCreatedDate) {
		this.viewCreatedDate = viewCreatedDate;
	}
	
	public BigDecimal getViewBeneBankId() {
		return viewBeneBankId;
	}
	public void setViewBeneBankId(BigDecimal viewBeneBankId) {
		this.viewBeneBankId = viewBeneBankId;
	}
	
	public String getViewBeneBankName() {
		return viewBeneBankName;
	}
	public void setViewBeneBankName(String viewBeneBankName) {
		this.viewBeneBankName = viewBeneBankName;
	}
	
	public BigDecimal getViewCountryBranchId() {
		return viewCountryBranchId;
	}
	public void setViewCountryBranchId(BigDecimal viewCountryBranchId) {
		this.viewCountryBranchId = viewCountryBranchId;
	}
	
	public String getViewCountryBranchName() {
		return viewCountryBranchName;
	}
	public void setViewCountryBranchName(String viewCountryBranchName) {
		this.viewCountryBranchName = viewCountryBranchName;
	}
	
	public BigDecimal getViewBeneCounteyId() {
		return viewBeneCounteyId;
	}
	public void setViewBeneCounteyId(BigDecimal viewBeneCounteyId) {
		this.viewBeneCounteyId = viewBeneCounteyId;
	}
	
	public String getViewBeneCountryName() {
		return viewBeneCountryName;
	}
	public void setViewBeneCountryName(String viewBeneCountryName) {
		this.viewBeneCountryName = viewBeneCountryName;
	}
	
	public BigDecimal getViewBeneId() {
		return viewBeneId;
	}
	public void setViewBeneId(BigDecimal viewBeneId) {
		this.viewBeneId = viewBeneId;
	}
	
	public String getViewBeneName() {
		return viewBeneName;
	}
	public void setViewBeneName(String viewBeneName) {
		this.viewBeneName = viewBeneName;
	}
	
	public BigDecimal getViewRemitType() {
		return viewRemitType;
	}
	public void setViewRemitType(BigDecimal viewRemitType) {
		this.viewRemitType = viewRemitType;
	}
	
	public String getViewRemitName() {
		return viewRemitName;
	}
	public void setViewRemitName(String viewRemitName) {
		this.viewRemitName = viewRemitName;
	}
	
	public BigDecimal getViewCustomerId() {
		return viewCustomerId;
	}
	public void setViewCustomerId(BigDecimal viewCustomerId) {
		this.viewCustomerId = viewCustomerId;
	}
	
	public String getViewCustomerName() {
		return viewCustomerName;
	}
	public void setViewCustomerName(String viewCustomerName) {
		this.viewCustomerName = viewCustomerName;
	}

	public BigDecimal getViewCustomerRef() {
		return viewCustomerRef;
	}
	public void setViewCustomerRef(BigDecimal viewCustomerRef) {
		this.viewCustomerRef = viewCustomerRef;
	}

	public BigDecimal getViewAccountSeqId() {
		return viewAccountSeqId;
	}
	public void setViewAccountSeqId(BigDecimal viewAccountSeqId) {
		this.viewAccountSeqId = viewAccountSeqId;
	}
	
	public Boolean getBooReadOnlyCheckBox() {
		return booReadOnlyCheckBox;
	}
	public void setBooReadOnlyCheckBox(Boolean booReadOnlyCheckBox) {
		this.booReadOnlyCheckBox = booReadOnlyCheckBox;
	}
	
	public BigDecimal getRequestCurrencyId() {
		return requestCurrencyId;
	}
	public void setRequestCurrencyId(BigDecimal requestCurrencyId) {
		this.requestCurrencyId = requestCurrencyId;
	}
	
	public String getRequestcurrencyName() {
		return requestcurrencyName;
	}
	public void setRequestcurrencyName(String requestcurrencyName) {
		this.requestcurrencyName = requestcurrencyName;
	}
	
	public List<PopulateData> getRoutingCountryvalues() {
		return routingCountryvalues;
	}
	public void setRoutingCountryvalues(List<PopulateData> routingCountryvalues) {
		this.routingCountryvalues = routingCountryvalues;
	}
	
	public BigDecimal getActualRate() {
		return actualRate;
	}
	public void setActualRate(BigDecimal actualRate) {
		this.actualRate = actualRate;
	}
	
	public BigDecimal getCountryBranchCode() {
		return countryBranchCode;
	}
	public void setCountryBranchCode(BigDecimal countryBranchCode) {
		this.countryBranchCode = countryBranchCode;
	}
	
}
