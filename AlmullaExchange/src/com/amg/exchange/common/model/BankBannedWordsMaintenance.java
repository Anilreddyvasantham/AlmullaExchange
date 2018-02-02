package com.amg.exchange.common.model;

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

import com.amg.exchange.treasury.model.BankMaster;
@Entity
@Table(name="EX_BANNED_NAMES")
public class BankBannedWordsMaintenance implements Serializable {
 
	private static final long serialVersionUID = 1L;

	private BigDecimal bannedId;
	private CountryMaster  countryId;
	private String countryCode;
	private BankMaster bankId;
	private String bankCode;
	private String bannedName;
	private String selectionMode;
	private String createdBy;
	private String modifiedBy;
	private String isActive;
	private Date createdDate;
	private Date modifiedDate;
	
	public BankBannedWordsMaintenance(){
		
	}
		
	public BankBannedWordsMaintenance(BigDecimal bannedId,
			CountryMaster countryId, String countryCode, BankMaster bankId,
			String bankCode, String bannedName, String selectionMode,
			String createdBy, String modifiedBy, String isActive,
			Date createdDate, Date modifiedDate) {
		super();
		this.bannedId = bannedId;
		this.countryId = countryId;
		this.countryCode = countryCode;
		this.bankId = bankId;
		this.bankCode = bankCode;
		this.bannedName = bannedName;
		this.selectionMode = selectionMode;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.isActive = isActive;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
	}


	@Id
	@GeneratedValue(generator="ex_banned_names_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_banned_names_seq",sequenceName="EX_BANNED_NAMES_SEQ",allocationSize=1)
	@Column(name = "BANNED_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getBannedId() {
		return bannedId;
	}
	public void setBannedId(BigDecimal bannedId) {
		this.bannedId = bannedId;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_ID")
	public CountryMaster getCountryId() {
		return countryId;
	}
	public void setCountryId(CountryMaster countryId) {
		this.countryId = countryId;
	}
	@Column(name="COUNTRY_CODE")
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_ID")
	public BankMaster getBankId() {
		return bankId;
	}
	public void setBankId(BankMaster bankId) {
		this.bankId = bankId;
	}
	@Column(name="BANK_CODE")
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
@Column(name="BANNED_NAME")
	public String getBannedName() {
		return bannedName;
	}
	public void setBannedName(String bannedName) {
		this.bannedName = bannedName;
	}
	@Column(name="SELECTION_MODE")
	public String getSelectionMode() {
		return selectionMode;
	}
	public void setSelectionMode(String selectionMode) {
		this.selectionMode = selectionMode;
	}
	@Column(name="CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	@Column(name="MODIFIED_BY")
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	@Column(name="ISACTIVE")
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	@Column(name="CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	@Column(name="MODIFIED_DATE")
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
 
	
}
