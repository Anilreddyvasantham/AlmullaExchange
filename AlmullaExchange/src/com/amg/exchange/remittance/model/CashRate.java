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
@Table(name = "EX_CASH_RATE")
public class CashRate implements Serializable {

	private static final long serialVersionUID = 1L;

	private BigDecimal cashRateId;
	private String alternateCurrencyCode;
	private CurrencyMaster alternateCurrencyId;
	private CountryMaster appCountryId;
	private String approvedBy;
	private Date approvedDate;
	private String authorisedBy;
	private Date authorisedDate;
	private String baseCurrencyCode;
	private CurrencyMaster baseCurrencyId;
	private BigDecimal buyRateMax;
	private BigDecimal buyRateMin;
	private CountryBranch countryBranchId;
	private String createdBy;
	private Date createdDate;
	private String isActive;
	private String locCode;
	private String modifiedBy;
	private Date modifiedDate;
	private BigDecimal saleMinRate;
	private BigDecimal saleMaxRate;
	private Date uploadDate;
	private BigDecimal prvSellMaxRate;
	private BigDecimal prvSellMinRate;
	private BigDecimal prvBuyMaxRate;
	private BigDecimal prvBuyMinRate;

	public CashRate() {
		super();
	}

	public CashRate(BigDecimal cashRateId, String alternateCurrencyCode,
			CurrencyMaster alternateCurrencyId, CountryMaster appCountryId,
			String approvedBy, Date approvedDate, String authorisedBy,
			Date authorisedDate, String baseCurrencyCode,
			CurrencyMaster baseCurrencyId, BigDecimal buyRateMax,
			BigDecimal buyRateMin, CountryBranch countryBranchId,
			String createdBy, Date createdDate, String isActive,
			String locCode, String modifiedBy, Date modifiedDate,
			BigDecimal saleMinRate, BigDecimal saleMaxRate, Date uploadDate,
			BigDecimal prvSellMaxRate, BigDecimal prvSellMinRate,
			BigDecimal prvBuyMaxRate, BigDecimal prvBuyMinRate) {
		super();
		this.cashRateId = cashRateId;
		this.alternateCurrencyCode = alternateCurrencyCode;
		this.alternateCurrencyId = alternateCurrencyId;
		this.appCountryId = appCountryId;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.authorisedBy = authorisedBy;
		this.authorisedDate = authorisedDate;
		this.baseCurrencyCode = baseCurrencyCode;
		this.baseCurrencyId = baseCurrencyId;
		this.buyRateMax = buyRateMax;
		this.buyRateMin = buyRateMin;
		this.countryBranchId = countryBranchId;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.isActive = isActive;
		this.locCode = locCode;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.saleMinRate = saleMinRate;
		this.saleMaxRate = saleMaxRate;
		this.uploadDate = uploadDate;
		this.prvSellMaxRate = prvSellMaxRate;
		this.prvSellMinRate = prvSellMinRate;
		this.prvBuyMaxRate = prvBuyMaxRate;
		this.prvBuyMinRate = prvBuyMinRate;
	}

	@Id
	@GeneratedValue(generator = "ex_cash_rate_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ex_cash_rate_seq", sequenceName = "EX_CASH_RATE_SEQ", allocationSize = 1)
	@Column(name = "CASH_RATE_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getCashRateId() {
		return cashRateId;
	}
	public void setCashRateId(BigDecimal cashRateId) {
		this.cashRateId = cashRateId;
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

	@Column(name = "AUTHORISED_BY")
	public String getAuthorisedBy() {
		return authorisedBy;
	}
	public void setAuthorisedBy(String authorisedBy) {
		this.authorisedBy = authorisedBy;
	}

	@Column(name = "AUTHORISED_DATE")
	public Date getAuthorisedDate() {
		return authorisedDate;
	}
	public void setAuthorisedDate(Date authorisedDate) {
		this.authorisedDate = authorisedDate;
	}

	@Column(name = "PRV_SELL_RATE_MAX")
	public BigDecimal getPrvSellMaxRate() {
		return prvSellMaxRate;
	}
	public void setPrvSellMaxRate(BigDecimal prvSellMaxRate) {
		this.prvSellMaxRate = prvSellMaxRate;
	}

	@Column(name = "PRV_SELL_RATE_MIN")
	public BigDecimal getPrvSellMinRate() {
		return prvSellMinRate;
	}
	public void setPrvSellMinRate(BigDecimal prvSellMinRate) {
		this.prvSellMinRate = prvSellMinRate;
	}

	@Column(name = "PRV_BUY_RATE_MAX")
	public BigDecimal getPrvBuyMaxRate() {
		return prvBuyMaxRate;
	}
	public void setPrvBuyMaxRate(BigDecimal prvBuyMaxRate) {
		this.prvBuyMaxRate = prvBuyMaxRate;
	}

	@Column(name = "PRV_BUY_RATE_MIN")
	public BigDecimal getPrvBuyMinRate() {
		return prvBuyMinRate;
	}
	public void setPrvBuyMinRate(BigDecimal prvBuyMinRate) {
		this.prvBuyMinRate = prvBuyMinRate;
	}
	
	
}
