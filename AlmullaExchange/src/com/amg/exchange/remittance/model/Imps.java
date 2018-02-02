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
import com.amg.exchange.treasury.model.BankMaster;
@Entity
@Table(name="EX_IMPS_MASTER")
public class Imps implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private BigDecimal impsId;
	private  CountryMaster applicationCountryId;
	private String correspodentBankCode;
	private  String beneBankCode;
	private BankMaster correspondingBankId;
	private BankMaster beneBankId;
	private String createdBy;
	private String modifiedBy;
	private String approvedBy;
	private Date createdDate;
	private Date modifiedDate;
	private Date approvedDate;
	private String isActive;
	private String remarks;
	
	public Imps(BigDecimal impsId, String correspodentBankCode,
			String beneBankCode, BankMaster correspondingBankId,
			BankMaster beneBankId, String createdBy, String modifiedBy,
			String approvedBy, Date createdDate, Date modifiedDate,
			Date approvedDate, String isActive) {
		super();
		this.impsId = impsId;
		this.correspodentBankCode = correspodentBankCode;
		this.beneBankCode = beneBankCode;
		this.correspondingBankId = correspondingBankId;
		this.beneBankId = beneBankId;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.approvedBy = approvedBy;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.approvedDate = approvedDate;
		this.isActive = isActive;
	}


	public Imps(){
		
	}
	
	@Id
	@GeneratedValue(generator="ex_imps_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_imps_seq",sequenceName="EX_IMPS_SEQ",allocationSize=1)
	@Column(name = "IMPS_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getImpsId() {
		return impsId;
	}
	public void setImpsId(BigDecimal impsId) {
		this.impsId = impsId;
	}
	@Column(name="CORRESPONDING_BANK_CODE")
	public String getCorrespodentBankCode() {
		return correspodentBankCode;
	}
	public void setCorrespodentBankCode(String correspodentBankCode) {
		this.correspodentBankCode = correspodentBankCode;
	}
	@Column(name="BENEFICARY_BANK_CODE")
	public String getBeneBankCode() {
		return beneBankCode;
	}
	public void setBeneBankCode(String beneBankCode) {
		this.beneBankCode = beneBankCode;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CORRESPONDING_BANK_ID")
	public BankMaster getCorrespondingBankId() {
		return correspondingBankId;
	}
	public void setCorrespondingBankId(BankMaster correspondingBankId) {
		this.correspondingBankId = correspondingBankId;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BENEFICARY_BANK_ID")
	public BankMaster getBeneBankId() {
		return beneBankId;
	}
	public void setBeneBankId(BankMaster beneBankId) {
		this.beneBankId = beneBankId;
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
	@Column(name="APPROVED_BY")
	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
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
	@Column(name="APPROVED_DATE")
	public Date getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
	@Column(name="ISACTIVE")
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}


	public String getRemarks() {
		return remarks;
	}


	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "APPLICATION_COUNTRY_ID")
	public CountryMaster getApplicationCountryId() {
		return applicationCountryId;
	}


	public void setApplicationCountryId(CountryMaster applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}


	 
	
	
	
	
	
	 
	
	
}
