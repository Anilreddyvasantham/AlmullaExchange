package com.amg.exchange.remittance.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

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
@Table(name = "EX_ADDITIONAL_EXCHANGE_DATA")
public class AdditionalBankRuleAmiec implements Serializable {

	private static final long serialVersionUID = 1L;

	public AdditionalBankRuleAmiec() {
		
	}

	private BigDecimal additionalBankRuleDetailId;
	private CountryMaster countryId;
	private String flexField;
	private String amiecCode;
	private String amiecDescription;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String isActive;
	private AdditionalBankRuleMap additionalBankFieldId;
	private Date approvedDate;
	private String approvedBy;
	
	private String remarks;
	
	
	
	
	public AdditionalBankRuleAmiec(BigDecimal additionalBankRuleDetailId,
			CountryMaster countryId,String flexField,String amiecCode,
			String amiecDescription, String createdBy, Date createdDate,String modifiedBy,
	 Date modifiedDate,	 String isActive, AdditionalBankRuleMap additionalBankFieldId,
	 Date approvedDate,	 String approvedBy
			) {
		super();
		this.additionalBankRuleDetailId = additionalBankRuleDetailId;
		this.countryId = countryId;
		this.flexField = flexField;
		this.amiecCode = amiecCode;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.isActive = isActive;
		this.approvedDate = approvedDate;
		this.approvedBy = approvedBy;
		this.additionalBankFieldId = additionalBankFieldId;
		
	}
		

	@Id
	@GeneratedValue(generator = "ex_addl_bank_rule_detail_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ex_addl_bank_rule_detail_seq", sequenceName = "EX_ADD_BANK_RULE_AMIEC_SEQ", allocationSize = 1)
	@Column(name = "ADDITIONAL_BANK_RULE_AMIEC_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getAdditionalBankRuleDetailId() {
		return additionalBankRuleDetailId;
	}

	public void setAdditionalBankRuleDetailId(
			BigDecimal additionalBankRuleDetailId) {
		this.additionalBankRuleDetailId = additionalBankRuleDetailId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_ID")
	public CountryMaster getCountryId() {
		return countryId;
	}

	public void setCountryId(CountryMaster countryId) {
		this.countryId = countryId;
	}

	@Column(name = "FLEX_FIELD")
	public String getFlexField() {
		return flexField;
	}

	public void setFlexField(String flexField) {
		this.flexField = flexField;
	}

	@Column(name = "AMIEC_CODE")
	public String getAmiecCode() {
		return amiecCode;
	}

	public void setAmiecCode(String amiecCode) {
		this.amiecCode = amiecCode;
	}

	@Column(name = "AMIEC_DESC")
	public String getAmiecDescription() {
		return amiecDescription;
	}

	public void setAmiecDescription(String amiecDescription) {
		this.amiecDescription = amiecDescription;
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

	@Column(name = "ISACTIVE")
	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ADDITIONAL_BANK_RULE_ID")
	public AdditionalBankRuleMap getAdditionalBankFieldId() {
		return additionalBankFieldId;
	}

	public void setAdditionalBankFieldId(AdditionalBankRuleMap additionalBankFieldId) {
		this.additionalBankFieldId = additionalBankFieldId;
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
	
	
	

}
