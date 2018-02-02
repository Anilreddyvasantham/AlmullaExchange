package com.amg.exchange.common.bean;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;

public class CurrencyDataTable{

	private String currencyCode;
	private String currencyName;
	private String quoteName;
	private Byte currencyDesc;
	private String decimalName;
	private String fimsCurrencyCode;
	private String isActive;
	private String arabicCurrencyName;
	private String arabicDecimalName;
	private String arabicQuoteName;
	private String swiftCurrency;
	private BigDecimal currencyOtherOnfoPk;
	private BigDecimal fundMinRate;
	private BigDecimal fundMaxRate;
	private String isoCurrencyCode;
	private BigDecimal cashMinRate;
	private BigDecimal cashMaxRate;
	private String onlineInd;
	private String cbkPrintInd;
	private  BigDecimal cbkSortInd;
	private BigDecimal decimalNumber;
	private  BigDecimal countryId;
	private String countryName;
	
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private String approvedBy;
	private Date modifiedDate;
	private Date approvedDate;
	private String remarks;
	private BigDecimal currencyMasterPk;
	private Boolean renderEditButton;
	private String dynamicLabelForActivateDeactivate;
	private Boolean remarkCheck;
	private Boolean checkSave;
	private Boolean booCheckUpdate;
	private Boolean booCheckDelete;
	private BigDecimal highValue;
	private BigDecimal averageRate;
	private BigDecimal placeOrderLimit;
	private BigDecimal applicationContryId;
	private String allowFCPurchase;
	private String allowFCSale;
	
	public Boolean getRenderEditButton() {
		return renderEditButton;
	}
	public void setRenderEditButton(Boolean renderEditButton) {
		this.renderEditButton = renderEditButton;
	}
	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}
	public void setDynamicLabelForActivateDeactivate(
			String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
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
	public String getQuoteName() {
		return quoteName;
	}
	public void setQuoteName(String quoteName) {
		this.quoteName = quoteName;
	}
	public Byte getCurrencyDesc() {
		return currencyDesc;
	}
	public void setCurrencyDesc(Byte currencyDesc) {
		this.currencyDesc = currencyDesc;
	}
	public String getDecimalName() {
		return decimalName;
	}
	public void setDecimalName(String decimalName) {
		this.decimalName = decimalName;
	}
	public String getFimsCurrencyCode() {
		return fimsCurrencyCode;
	}
	public void setFimsCurrencyCode(String fimsCurrencyCode) {
		this.fimsCurrencyCode = fimsCurrencyCode;
	}
	 
	public String getArabicCurrencyName() {
		return arabicCurrencyName;
	}
	public void setArabicCurrencyName(String arabicCurrencyName) {
		this.arabicCurrencyName = arabicCurrencyName;
	}
	public String getArabicDecimalName() {
		return arabicDecimalName;
	}
	public void setArabicDecimalName(String arabicDecimalName) {
		this.arabicDecimalName = arabicDecimalName;
	}
	public String getArabicQuoteName() {
		return arabicQuoteName;
	}
	public void setArabicQuoteName(String arabicQuoteName) {
		this.arabicQuoteName = arabicQuoteName;
	}
	public String getSwiftCurrency() {
		return swiftCurrency;
	}
	public void setSwiftCurrency(String swiftCurrency) {
		this.swiftCurrency = swiftCurrency;
	}
	public BigDecimal getFundMinRate() {
		return fundMinRate;
	}
	public void setFundMinRate(BigDecimal fundMinRate) {
		this.fundMinRate = fundMinRate;
	}
	public BigDecimal getFundMaxRate() {
		return fundMaxRate;
	}
	public void setFundMaxRate(BigDecimal fundMaxRate) {
		this.fundMaxRate = fundMaxRate;
	}
	public String getIsoCurrencyCode() {
		return isoCurrencyCode;
	}
	public void setIsoCurrencyCode(String isoCurrencyCode) {
		this.isoCurrencyCode = isoCurrencyCode;
	}
	public BigDecimal getCashMinRate() {
		return cashMinRate;
	}
	public void setCashMinRate(BigDecimal cashMinRate) {
		this.cashMinRate = cashMinRate;
	}
	public BigDecimal getCashMaxRate() {
		return cashMaxRate;
	}
	public void setCashMaxRate(BigDecimal cashMaxRate) {
		this.cashMaxRate = cashMaxRate;
	}
	public String getOnlineInd() {
		return onlineInd;
	}
	public void setOnlineInd(String onlineInd) {
		this.onlineInd = onlineInd;
	}
	public String getCbkPrintInd() {
		return cbkPrintInd;
	}
	public void setCbkPrintInd(String cbkPrintInd) {
		this.cbkPrintInd = cbkPrintInd;
	}
	 
	 
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public BigDecimal getDecimalNumber() {
		return decimalNumber;
	}
	public void setDecimalNumber(BigDecimal decimalNumber) {
		this.decimalNumber = decimalNumber;
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
	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public Date getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public BigDecimal getCurrencyMasterPk() {
		return currencyMasterPk;
	}
	public void setCurrencyMasterPk(BigDecimal currencyMasterPk) {
		this.currencyMasterPk = currencyMasterPk;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public BigDecimal getCountryId() {
		return countryId;
	}
	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}
	public Boolean getRemarkCheck() {
		return remarkCheck;
	}
	public void setRemarkCheck(Boolean remarkCheck) {
		this.remarkCheck = remarkCheck;
	}
	public Boolean getCheckSave() {
		return checkSave;
	}
	public void setCheckSave(Boolean checkSave) {
		this.checkSave = checkSave;
	}
	public Boolean getBooCheckUpdate() {
		return booCheckUpdate;
	}
	public void setBooCheckUpdate(Boolean booCheckUpdate) {
		this.booCheckUpdate = booCheckUpdate;
	}
	public Boolean getBooCheckDelete() {
		return booCheckDelete;
	}
	public void setBooCheckDelete(Boolean booCheckDelete) {
		this.booCheckDelete = booCheckDelete;
	}
	public BigDecimal getCurrencyOtherOnfoPk() {
		return currencyOtherOnfoPk;
	}
	public void setCurrencyOtherOnfoPk(BigDecimal currencyOtherOnfoPk) {
		this.currencyOtherOnfoPk = currencyOtherOnfoPk;
	}
	public BigDecimal getCbkSortInd() {
		return cbkSortInd;
	}
	public void setCbkSortInd(BigDecimal cbkSortInd) {
		this.cbkSortInd = cbkSortInd;
	}
	public BigDecimal getHighValue() {
		return highValue;
	}
	public void setHighValue(BigDecimal highValue) {
		this.highValue = highValue;
	}
	public BigDecimal getAverageRate() {
		return averageRate;
	}
	public void setAverageRate(BigDecimal averageRate) {
		this.averageRate = averageRate;
	}
	public BigDecimal getApplicationContryId() {
		return applicationContryId;
	}
	public void setApplicationContryId(BigDecimal applicationContryId) {
		this.applicationContryId = applicationContryId;
	}
	
	public String getAllowFCPurchase() {
		return allowFCPurchase;
	}
	public void setAllowFCPurchase(String allowFCPurchase) {
		this.allowFCPurchase = allowFCPurchase;
	}

	public String getAllowFCSale() {
		return allowFCSale;
	}
	public void setAllowFCSale(String allowFCSale) {
		this.allowFCSale = allowFCSale;
	}
	public BigDecimal getPlaceOrderLimit() {
		return placeOrderLimit;
	}
	public void setPlaceOrderLimit(BigDecimal placeOrderLimit) {
		this.placeOrderLimit = placeOrderLimit;
	}
	
}