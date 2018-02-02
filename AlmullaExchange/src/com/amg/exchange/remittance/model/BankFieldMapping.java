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

import com.amg.exchange.treasury.model.BankMaster;

/**
 * @author Nazish Ehsan Hashmi
 * 
 */
@Entity
@Table(name = "EX_BANK_FIELD_MAPPING")
public class BankFieldMapping implements Serializable {

	  /**
	 * 
	 */
	  private static final long serialVersionUID = 1L;

	  private BigDecimal bankFieldMappingId;
	  private BigDecimal applicationCountry;
	  private String tableName;
	  private String fieldName;
	  private String fieldValue;
	  private BankMaster bankId;
	  private String bankValue;
	  private String bankValueDesc;
	  private String createdBy;
	  private Date createdDate;
	  private String modifiedBy;
	  private Date modifiedDate;
	  private String isActive;
	  private Date approvedDate;
	  private String approvedBy;
	  private String remarks;
	  private AdditionalBankRuleMap exbankAdditinalFields;

	  public BankFieldMapping() {
		
	  }

	  public BankFieldMapping(BigDecimal bankFieldMappingId) {
		 
		    this.bankFieldMappingId = bankFieldMappingId;
	  }

	  public BankFieldMapping(BigDecimal bankFieldMappingId, BigDecimal applicationCountry, String tableName, String fieldName, String fieldValue, BankMaster bankId, String bankValue, String bankValueDesc, String createdBy, Date createdDate, String modifiedBy, Date modifiedDate,
			      String isActive, Date approvedDate, String approvedBy, String remarks, AdditionalBankRuleMap exbankAdditinalFields) {
		    super();
		    this.bankFieldMappingId = bankFieldMappingId;
		    this.applicationCountry = applicationCountry;
		    this.tableName = tableName;
		    this.fieldName = fieldName;
		    this.fieldValue = fieldValue;
		    this.bankId = bankId;
		    this.bankValue = bankValue;
		    this.bankValueDesc = bankValueDesc;
		    this.createdBy = createdBy;
		    this.createdDate = createdDate;
		    this.modifiedBy = modifiedBy;
		    this.modifiedDate = modifiedDate;
		    this.isActive = isActive;
		    this.approvedDate = approvedDate;
		    this.approvedBy = approvedBy;
		    this.remarks = remarks;
		    this.exbankAdditinalFields = exbankAdditinalFields;
	  }

	  @Id
	  @GeneratedValue(generator = "ex_bank_field_mapping_seq", strategy = GenerationType.SEQUENCE)
	  @SequenceGenerator(name = "ex_bank_field_mapping_seq", sequenceName = "EX_BANK_FIELD_MAPPING_SEQ", allocationSize = 1)
	  @Column(name = "BANK_FIELD_MAPPING_ID", unique = true, nullable = false, precision = 22, scale = 0)
	  public BigDecimal getBankFieldMappingId() {
		    return bankFieldMappingId;
	  }

	  public void setBankFieldMappingId(BigDecimal bankFieldMappingId) {
		    this.bankFieldMappingId = bankFieldMappingId;
	  }

	  @Column(name = "APPLICATION_COUNTRY")
	  public BigDecimal getApplicationCountry() {
		    return applicationCountry;
	  }

	  public void setApplicationCountry(BigDecimal applicationCountry) {
		    this.applicationCountry = applicationCountry;
	  }

	  @Column(name = "TABLE_NAME")
	  public String getTableName() {
		    return tableName;
	  }

	  public void setTableName(String tableName) {
		    this.tableName = tableName;
	  }

	  @Column(name = "FIELD_NAME")
	  public String getFieldName() {
		    return fieldName;
	  }

	  public void setFieldName(String fieldName) {
		    this.fieldName = fieldName;
	  }

	  @Column(name = "FIELD_VALUE")
	  public String getFieldValue() {
		    return fieldValue;
	  }

	  public void setFieldValue(String fieldValue) {
		    this.fieldValue = fieldValue;
	  }

	  @ManyToOne(fetch = FetchType.LAZY)
	  @JoinColumn(name = "BANK_ID")
	  public BankMaster getBankId() {
		    return bankId;
	  }

	  public void setBankId(BankMaster bankId) {
		    this.bankId = bankId;
	  }

	  @Column(name = "BANK_VALUE")
	  public String getBankValue() {
		    return bankValue;
	  }

	  public void setBankValue(String bankValue) {
		    this.bankValue = bankValue;
	  }

	  @Column(name = "BANK_VALUE_DESCRIPTION")
	  public String getBankValueDesc() {
		    return bankValueDesc;
	  }

	  public void setBankValueDesc(String bankValueDesc) {
		    this.bankValueDesc = bankValueDesc;
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

	  @ManyToOne(fetch = FetchType.LAZY)
	  @JoinColumn(name = "ADDITIONAL_BANK_RULE_ID")
	  public AdditionalBankRuleMap getExbankAdditinalFields() {
		    return exbankAdditinalFields;
	  }

	  public void setExbankAdditinalFields(AdditionalBankRuleMap exbankAdditinalFields) {
		    this.exbankAdditinalFields = exbankAdditinalFields;
	  }

}
