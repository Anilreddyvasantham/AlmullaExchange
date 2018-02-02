package com.amg.exchange.remittance.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.treasury.model.CurrencyMaster;

@Entity
@Table(name = "EX_UNAPP_CASHRATE")
public class UnApprovalCashRate implements Serializable {

	private static final long serialVersionUID = 1L;

	private BigDecimal unAppCashRateId;
	private CountryMaster appCountryId;
	private CurrencyMaster baseCurrencyId;
	private String baseCurrencyCode;
	private CurrencyMaster alternateCurrencyId;
	private String alternateCurrencyCode;
	private CountryBranch countryBranchId;
	private BigDecimal saleMinRate;
	private BigDecimal saleMaxRate;
	private BigDecimal buyRateMin;
	private BigDecimal buyRateMax;
	private String locCode;
	private Date uploadDate;
	private String isActive;
	private Date createdDate;
	private String createdBy;
	private Date modifiedDate;
	private String modifiedBy;
	private Date approvedDate;
	private String approvedBy;

	public UnApprovalCashRate() {
	}

	public UnApprovalCashRate(BigDecimal unAppCashRateId, CountryMaster appCountryId, CurrencyMaster baseCurrencyId, String baseCurrencyCode, CurrencyMaster alternateCurrencyId, String alternateCurrencyCode, CountryBranch countryBranchId, BigDecimal saleMinRate, BigDecimal saleMaxRate,
			BigDecimal buyRateMin, BigDecimal buyRateMax, String locCode, Date uploadDate, String isActive, Date createdDate, String createdBy, Date modifiedDate, String modifiedBy, Date approvedDate, String approvedBy) {
		this.unAppCashRateId = unAppCashRateId;
		this.appCountryId = appCountryId;
		this.baseCurrencyId = baseCurrencyId;
		this.baseCurrencyCode = baseCurrencyCode;
		this.alternateCurrencyId = alternateCurrencyId;
		this.alternateCurrencyCode = alternateCurrencyCode;
		this.countryBranchId = countryBranchId;
		this.saleMinRate = saleMinRate;
		this.saleMaxRate = saleMaxRate;
		this.buyRateMin = buyRateMin;
		this.buyRateMax = buyRateMax;
		this.locCode = locCode;
		this.uploadDate = uploadDate;
		this.isActive = isActive;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.modifiedDate = modifiedDate;
		this.modifiedBy = modifiedBy;
		this.approvedDate = approvedDate;
		this.approvedBy = approvedBy;
	}

	@Id
	@GeneratedValue(generator = "ex_unapp_cashrate_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ex_unapp_cashrate_seq", sequenceName = "EX_UNAPP_CASHRATE_SEQ", allocationSize = 1)
	@Column(name = "UNAPP_CASHRATE_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getUnAppCashRateId() {
		return unAppCashRateId;
	}

	public void setUnAppCashRateId(BigDecimal unAppCashRateId) {
		this.unAppCashRateId = unAppCashRateId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "APPLICATION_COUNTRY_ID")
	public CountryMaster getAppCountryId() {
		return appCountryId;
	}

	public void setAppCountryId(CountryMaster appCountryId) {
		this.appCountryId = appCountryId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BASE_CURRENCY_ID")
	public CurrencyMaster getBaseCurrencyId() {
		return baseCurrencyId;
	}

	public void setBaseCurrencyId(CurrencyMaster baseCurrencyId) {
		this.baseCurrencyId = baseCurrencyId;
	}

	@Column(name = "BASE_CURRENCY_CODE")
	public String getBaseCurrencyCode() {
		return baseCurrencyCode;
	}

	public void setBaseCurrencyCode(String baseCurrencyCode) {
		this.baseCurrencyCode = baseCurrencyCode;
	}

	@Column(name = "ALTERNATIVE_CURRENCY_CODE")
	public String getAlternateCurrencyCode() {
		return alternateCurrencyCode;
	}

	public void setAlternateCurrencyCode(String alternateCurrencyCode) {
		this.alternateCurrencyCode = alternateCurrencyCode;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ALTERNATIVE_CURRENCY_ID")
	public CurrencyMaster getAlternateCurrencyId() {
		return alternateCurrencyId;
	}

	public void setAlternateCurrencyId(CurrencyMaster alternateCurrencyId) {
		this.alternateCurrencyId = alternateCurrencyId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_BRANCH_ID")
	public CountryBranch getCountryBranchId() {
		return countryBranchId;
	}

	public void setCountryBranchId(CountryBranch countryBranchId) {
		this.countryBranchId = countryBranchId;
	}

	@Column(name = "SAL_MIN_RATE")
	public BigDecimal getSaleMinRate() {
		return saleMinRate;
	}

	public void setSaleMinRate(BigDecimal saleMinRate) {
		this.saleMinRate = saleMinRate;
	}

	@Column(name = "SAL_MAX_RATE")
	public BigDecimal getSaleMaxRate() {
		return saleMaxRate;
	}

	public void setSaleMaxRate(BigDecimal saleMaxRate) {
		this.saleMaxRate = saleMaxRate;
	}

	@Column(name = "BUY_MIN_RATE")
	public BigDecimal getBuyRateMin() {
		return buyRateMin;
	}

	public void setBuyRateMin(BigDecimal buyRateMin) {
		this.buyRateMin = buyRateMin;
	}

	@Column(name = "BUY_MAX_RATE")
	public BigDecimal getBuyRateMax() {
		return buyRateMax;
	}

	public void setBuyRateMax(BigDecimal buyRateMax) {
		this.buyRateMax = buyRateMax;
	}

	@Column(name = "LOC_CODE")
	public String getLocCode() {
		return locCode;
	}

	public void setLocCode(String locCode) {
		this.locCode = locCode;
	}

	@Column(name = "UPLOAD_DATE")
	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	@Column(name = "ISACTIVE")
	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Column(name = "CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "MODIFIED_DATE")
	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name = "MODIFIED_BY")
	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "APPROVED_DATE")
	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	@Column(name = "APPROVED_BY")
	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

}
