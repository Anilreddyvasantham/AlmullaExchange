package com.amg.exchange.treasury.bean;

import java.math.BigDecimal;
import java.util.Date;

public class CashRateDataTableBean {

	private BigDecimal cashRatePk;
	private BigDecimal applicationCountryId;
	private BigDecimal countryBranchId;
	private BigDecimal altenateCurrencyId;
	private String altenateCurrencyCode;
	private BigDecimal baseCurrencyId;
	private String baseCurrencyCode;
	private BigDecimal localCurrencyCode;
	private String branchDescriprion;
	private String baseCurrencyDescription;
	private String altenativeCurrencyDescrption;
	private Date uploadDate;
	private Date approvedDate;
	private String approvedBy;
	private Date createdDate;
	private String createdBy;
	private String modifiedBy;
	private Date modifiedDate;
	private BigDecimal minSellRate;
	private BigDecimal maxSellRate;
	private BigDecimal minBuyRate;
	private BigDecimal maxBuyRate;
	private String isActive;
	private String locCode;
	private BigDecimal prvSellMinRate;
	private BigDecimal prvSellMaxRate;
	private BigDecimal prvBuyMinRate;
	private BigDecimal prvBuyMaxRate;

	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}

	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}

	public String getAltenateCurrencyCode() {
		return altenateCurrencyCode;
	}

	public void setAltenateCurrencyCode(String altenateCurrencyCode) {
		this.altenateCurrencyCode = altenateCurrencyCode;
	}

	public String getBaseCurrencyCode() {
		return baseCurrencyCode;
	}

	public void setBaseCurrencyCode(String baseCurrencyCode) {
		this.baseCurrencyCode = baseCurrencyCode;
	}

	public BigDecimal getLocalCurrencyCode() {
		return localCurrencyCode;
	}

	public void setLocalCurrencyCode(BigDecimal localCurrencyCode) {
		this.localCurrencyCode = localCurrencyCode;
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getBranchDescriprion() {
		return branchDescriprion;
	}

	public void setBranchDescriprion(String branchDescriprion) {
		this.branchDescriprion = branchDescriprion;
	}

	public String getBaseCurrencyDescription() {
		return baseCurrencyDescription;
	}

	public void setBaseCurrencyDescription(String baseCurrencyDescription) {
		this.baseCurrencyDescription = baseCurrencyDescription;
	}

	public String getAltenativeCurrencyDescrption() {
		return altenativeCurrencyDescrption;
	}

	public void setAltenativeCurrencyDescrption(String altenativeCurrencyDescrption) {
		this.altenativeCurrencyDescrption = altenativeCurrencyDescrption;
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

	public BigDecimal getMinSellRate() {
		return minSellRate;
	}

	public void setMinSellRate(BigDecimal minSellRate) {
		this.minSellRate = minSellRate;
	}

	public BigDecimal getMaxSellRate() {
		return maxSellRate;
	}

	public void setMaxSellRate(BigDecimal maxSellRate) {
		this.maxSellRate = maxSellRate;
	}

	public BigDecimal getMinBuyRate() {
		return minBuyRate;
	}

	public void setMinBuyRate(BigDecimal minBuyRate) {
		this.minBuyRate = minBuyRate;
	}

	public BigDecimal getMaxBuyRate() {
		return maxBuyRate;
	}

	public void setMaxBuyRate(BigDecimal maxBuyRate) {
		this.maxBuyRate = maxBuyRate;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public BigDecimal getCashRatePk() {
		return cashRatePk;
	}

	public void setCashRatePk(BigDecimal cashRatePk) {
		this.cashRatePk = cashRatePk;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public BigDecimal getAltenateCurrencyId() {
		return altenateCurrencyId;
	}

	public void setAltenateCurrencyId(BigDecimal altenateCurrencyId) {
		this.altenateCurrencyId = altenateCurrencyId;
	}

	public BigDecimal getBaseCurrencyId() {
		return baseCurrencyId;
	}

	public void setBaseCurrencyId(BigDecimal baseCurrencyId) {
		this.baseCurrencyId = baseCurrencyId;
	}

	public String getLocCode() {
		return locCode;
	}

	public void setLocCode(String locCode) {
		this.locCode = locCode;
	}

	public BigDecimal getCountryBranchId() {
		return countryBranchId;
	}

	public void setCountryBranchId(BigDecimal countryBranchId) {
		this.countryBranchId = countryBranchId;
	}

	public BigDecimal getPrvSellMinRate() {
		return prvSellMinRate;
	}

	public void setPrvSellMinRate(BigDecimal prvSellMinRate) {
		this.prvSellMinRate = prvSellMinRate;
	}

	public BigDecimal getPrvSellMaxRate() {
		return prvSellMaxRate;
	}

	public void setPrvSellMaxRate(BigDecimal prvSellMaxRate) {
		this.prvSellMaxRate = prvSellMaxRate;
	}

	public BigDecimal getPrvBuyMinRate() {
		return prvBuyMinRate;
	}

	public void setPrvBuyMinRate(BigDecimal prvBuyMinRate) {
		this.prvBuyMinRate = prvBuyMinRate;
	}

	public BigDecimal getPrvBuyMaxRate() {
		return prvBuyMaxRate;
	}

	public void setPrvBuyMaxRate(BigDecimal prvBuyMaxRate) {
		this.prvBuyMaxRate = prvBuyMaxRate;
	}

}
