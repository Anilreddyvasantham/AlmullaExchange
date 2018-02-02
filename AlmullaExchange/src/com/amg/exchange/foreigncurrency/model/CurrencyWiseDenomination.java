package com.amg.exchange.foreigncurrency.model;

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
import com.amg.exchange.treasury.model.CurrencyMaster;

@Entity
@Table(name = "EX_CURRENCY_DENOMINATION")
public class CurrencyWiseDenomination implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal denominationId;
	private CurrencyMaster exCurrencyMaster;
	private CountryMaster fsCountryMaster;
	private String denominationDesc;
	private BigDecimal denominationAmount;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;
	private String isActive;
	private BigDecimal denominationCode;
	
	public CurrencyWiseDenomination() {
		super();
	}

	
	public CurrencyWiseDenomination(BigDecimal denominationId,
			CurrencyMaster exCurrencyMaster, CountryMaster fsCountryMaster,
			String denominationDesc, BigDecimal denominationAmount,
			String createdBy, Date createdDate, String modifiedBy,
			Date modifiedDate, String approvedBy, Date approvedDate,
			String remarks, String isActive, BigDecimal denominationCode) {
		super();
		this.denominationId = denominationId;
		this.exCurrencyMaster = exCurrencyMaster;
		this.fsCountryMaster = fsCountryMaster;
		this.denominationDesc = denominationDesc;
		this.denominationAmount = denominationAmount;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.remarks = remarks;
		this.isActive = isActive;
		this.denominationCode = denominationCode;
	}



	@Id
	@GeneratedValue(generator="ex_currency_denomination_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_currency_denomination_seq" ,sequenceName="EX_CURRENCY_DENOMINATION_SEQ",allocationSize=1)
	@Column(name = "DENOMINATION_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getDenominationId() {
		return denominationId;
	}
	public void setDenominationId(BigDecimal denominationId) {
		this.denominationId = denominationId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CURRENCY_ID")
	public CurrencyMaster getExCurrencyMaster() {
		return exCurrencyMaster;
	}
	public void setExCurrencyMaster(CurrencyMaster exCurrencyMaster) {
		this.exCurrencyMaster = exCurrencyMaster;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_ID")
	public CountryMaster getFsCountryMaster() {
		return fsCountryMaster;
	}
	public void setFsCountryMaster(CountryMaster fsCountryMaster) {
		this.fsCountryMaster = fsCountryMaster;
	}
	
	@Column(name = "DENOMINATION_DESC")
	public String getDenominationDesc() {
		return denominationDesc;
	}
	public void setDenominationDesc(String denominationDesc) {
		this.denominationDesc = denominationDesc;
	}
	
	@Column(name = "DENOMINATION_AMOUNT")
	public BigDecimal getDenominationAmount() {
		return denominationAmount;
	}
	public void setDenominationAmount(BigDecimal denominationAmount) {
		this.denominationAmount = denominationAmount;
	}

	@Column(name = "CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	@Column(name = "CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	@Column(name = "MODIFIED_BY")
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	@Column(name = "MODIFIED_DATE")
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	@Column(name = "APPROVED_BY")
	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	
	@Column(name = "APPROVED_DATE")
	public Date getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
	
	@Column(name = "REMARKS")
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	@Column(name = "ISACTIVE")
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Column(name = "DENOMINATION_CODE")
	public BigDecimal getDenominationCode() {
		return denominationCode;
	}
	public void setDenominationCode(BigDecimal denominationCode) {
		this.denominationCode = denominationCode;
	}
	
}
