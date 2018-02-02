package com.amg.exchange.remittance.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
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
@Table(name = "EX_AMIEC_AND_BANK_MAPPING")
public class AmiecAndBankMapping implements Serializable {


	private static final long serialVersionUID = 2315791709068216697L;

	private BigDecimal amiecAndBankMappingId;
	private String flexField;
	private CountryMaster countryId;
	private BankMaster bankId;
	private String bankCode;
	private String amiecCode;
	private String amiecDescription;
	private String bankDecription;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String isActive;
	private Date approvedDate;
	private String approvedBy;
	private String remarks;
	

	private Set<AdditionalBankRuleAmiec> additionalBankRule = new HashSet<AdditionalBankRuleAmiec>(0);
	private Set<AdditionalBankRuleAddData> additionalBankRuleData = new HashSet<AdditionalBankRuleAddData>(0);
	
	
	public AmiecAndBankMapping() {
	}

   
	public AmiecAndBankMapping(BigDecimal amiecAndBankMappingId) {
		this.amiecAndBankMappingId = amiecAndBankMappingId;
	}
	

	public AmiecAndBankMapping(BigDecimal amiecAndBankMappingId,
			String flexField, CountryMaster countryId, BankMaster bankId,
			String bankCode, String amiecCode, String amiecDescription,
			String bankDecription, String createdBy, Date createdDate,
			String modifiedBy, Date modifiedDate, String isActive,
			Date approvedDate, String approvedBy,
			Set<AdditionalBankRuleAmiec> additionalBankRule,
			Set<AdditionalBankRuleAddData> additionalBankRuleData,String remarks) {
		super();
		this.amiecAndBankMappingId = amiecAndBankMappingId;
		this.flexField = flexField;
		this.countryId = countryId;
		this.bankId = bankId;
		this.bankCode = bankCode;
		this.amiecCode = amiecCode;
		this.amiecDescription = amiecDescription;
		this.bankDecription = bankDecription;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.isActive = isActive;
		this.approvedDate = approvedDate;
		this.approvedBy = approvedBy;
		this.additionalBankRule = additionalBankRule;
		this.additionalBankRuleData = additionalBankRuleData;
		this.remarks=remarks;
	}


	@Id
	@GeneratedValue(generator = "ex_amiec_and_bank_mapping_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ex_amiec_and_bank_mapping_seq", sequenceName = "EX_AMIEC_AND_BANK_MAPPING_SEQ", allocationSize = 1)
	@Column(name = "AMIEC_AND_BANK_MAPPING_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getAmiecAndBankMappingId() {
		return amiecAndBankMappingId;
	}

	public void setAmiecAndBankMappingId(BigDecimal amiecAndBankMappingId) {
		this.amiecAndBankMappingId = amiecAndBankMappingId;
	}	

	@Column(name = "FLEX_FIELD")
	public String getFlexField() {
		return flexField;
	}

	public void setFlexField(String flexField) {
		this.flexField = flexField;
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

	
	@Column(name = "BANK_CODE")
	public String getBankCode() {
		return bankCode;
	}


	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	@Column(name = "BANK_DESCRIPTION")
	public String getBankDecription() {
		return bankDecription;
	}


	public void setBankDecription(String bankDecription) {
		this.bankDecription = bankDecription;
	}

	@Column(name = "AMIEC_CODE")
	public String getAmiecCode() {
		return amiecCode;
	}


	public void setAmiecCode(String amiecCode) {
		this.amiecCode = amiecCode;
	}

	@Column(name = "AMIEC_DESCRIPTION")
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
