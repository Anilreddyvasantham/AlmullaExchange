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
@Table(name = "EX_ADDITIONAL_BANK_DATA")
public class AdditionalBankRuleAddData implements Serializable {

	private static final long serialVersionUID = 1L;



	private BigDecimal additionalBankRuleDataId;
	private CountryMaster countryId;
	private BankMaster bankId;
	private String flexField;
	private String additionalData;
	private String additionalDescription;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String isActive;
	private AdditionalBankRuleMap additionalBankFieldId;
	private Date approvedDate;
	private String approvedBy;
	
	private String remarks;
	
	
	
	public AdditionalBankRuleAddData() {
		
	}
	public AdditionalBankRuleAddData(BigDecimal additionalBankRuleDataId) {
		this.additionalBankRuleDataId = additionalBankRuleDataId;
		
	}



	public AdditionalBankRuleAddData(BigDecimal additionalBankRuleDataId,
			CountryMaster countryId, BankMaster bankId, String flexField,
			String additionalData, String additionalDescription,
			String createdBy, Date createdDate, String modifiedBy,
			Date modifiedDate, String isActive,
			AdditionalBankRuleMap additionalBankFieldId, Date approvedDate,
			String approvedBy) {
		super();
		this.additionalBankRuleDataId = additionalBankRuleDataId;
		this.countryId = countryId;
		this.bankId = bankId;
		this.flexField = flexField;
		this.additionalData = additionalData;
		this.additionalDescription = additionalDescription;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.isActive = isActive;
		this.additionalBankFieldId = additionalBankFieldId;
		this.approvedDate = approvedDate;
		this.approvedBy = approvedBy;
	}
	@Id
	@GeneratedValue(generator = "ex_add_bank_rule_data_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ex_add_bank_rule_data_seq", sequenceName = "EX_ADD_BANK_RULE_ADDDATA_SEQ", allocationSize = 1)
	@Column(name = "ADD_BANK_RULE_ADDDATA_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getAdditionalBankRuleDataId() {
		return additionalBankRuleDataId;
	}

	public void setAdditionalBankRuleDataId(
			BigDecimal additionalBankRuleDataId) {
		this.additionalBankRuleDataId = additionalBankRuleDataId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_ID")
	public CountryMaster getCountryId() {
		return countryId;
	}

	public void setCountryId(CountryMaster countryId) {
		this.countryId = countryId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_ID")
	public BankMaster getBankId() {
		return bankId;
	}

	public void setBankId(BankMaster bankId) {
		this.bankId = bankId;
	}
	
	@Column(name = "FLEX_FIELD")
	public String getFlexField() {
		return flexField;
	}

	public void setFlexField(String flexField) {
		this.flexField = flexField;
	}
	
	@Column(name = "BANK_CODE")
	public String getAdditionalData() {
		return additionalData;
	}

	public void setAdditionalData(String additionalData) {
		this.additionalData = additionalData;
	}
	
	@Column(name = "BANK_DESC")
	public String getAdditionalDescription() {
		return additionalDescription;
	}

	public void setAdditionalDescription(String additionalDescription) {
		this.additionalDescription = additionalDescription;
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
