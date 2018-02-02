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

import org.apache.log4j.Logger;

import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.treasury.model.CurrencyMaster;

@Entity
@Table(name = "EX_HIGHVALUE_SETUP")
public class HighValueCurrencySetup implements Serializable {

	private static final long serialVersionUID = 1L;

	private BigDecimal highValueSetupId;
	private CountryMaster appCountryId;
	private CurrencyMaster currencyId;
	private BigDecimal highValueAmount;
	private String isActive;
	private Date createdDate;
	private String createdBy;
	private Date modifiedDate;
	private String modifiedBy;
	private Date approvedDate;
	private String approvedBy;
	private String remarks;

	public HighValueCurrencySetup() {
	}

	public HighValueCurrencySetup(BigDecimal highValueSetupId) {
		this.highValueSetupId = highValueSetupId;
	}

	public HighValueCurrencySetup(BigDecimal highValueSetupId, CountryMaster appCountryId, CurrencyMaster currencyId, BigDecimal highValueAmount, String isActive, Date createdDate, String createdBy, Date modifiedDate, String modifiedBy, Date approvedDate, String approvedBy, String remarks) {
		this.highValueSetupId = highValueSetupId;
		this.appCountryId = appCountryId;
		this.currencyId = currencyId;
		this.highValueAmount = highValueAmount;
		this.isActive = isActive;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.modifiedDate = modifiedDate;
		this.modifiedBy = modifiedBy;
		this.approvedDate = approvedDate;
		this.approvedBy = approvedBy;
		this.remarks = remarks;
	}

	@Id
	@GeneratedValue(generator = "ex_highvalue_setup_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ex_highvalue_setup_seq", sequenceName = "HIGHVALUE_SETUP_SEQ", allocationSize = 1)
	@Column(name = "HIGHVALUE_SETUP_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getHighValueSetupId() {
		return highValueSetupId;
	}

	public void setHighValueSetupId(BigDecimal highValueSetupId) {
		this.highValueSetupId = highValueSetupId;
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
	@JoinColumn(name = "CURRENCY_ID")
	public CurrencyMaster getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(CurrencyMaster currencyId) {
		this.currencyId = currencyId;
	}

	@Column(name = "HV_AMOUNT")
	public BigDecimal getHighValueAmount() {
		return highValueAmount;
	}

	public void setHighValueAmount(BigDecimal highValueAmount) {
		this.highValueAmount = highValueAmount;
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

	@Column(name = "REMARKS")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/*
	 * HIGHVALUE_SETUP_ID APPLICATION_COUNTRY_ID CURRENCY_ID HV_AMOUNT ISACTIVE
	 * CREATED_DATE CREATED_BY MODIFIED_DATE MODIFIED_BY APPROVED_DATE
	 * APPROVED_BY REMARKS
	 */

}
