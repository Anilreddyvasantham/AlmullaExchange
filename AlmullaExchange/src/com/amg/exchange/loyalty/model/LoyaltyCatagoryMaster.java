package com.amg.exchange.loyalty.model;

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

@Entity
@Table(name = "EX_LTY_CATEGORY_MASTER")
public class LoyaltyCatagoryMaster implements Serializable
{
	
	private static final long serialVersionUID = 1L;
	private BigDecimal loyaltyCatagoryId;
	private String categoryCode;
	
	private String categoryType;
	private String fieldName;
	private String validOption;
	private String createdBy;
	private Date createdDate;
	private String isactive;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;
	private CountryMaster applicationCountryId;
	
	public LoyaltyCatagoryMaster() {
		
	}

	public LoyaltyCatagoryMaster(BigDecimal loyaltyCatagoryId, String categoryCode,
		          String categoryType, String fieldName,
			String validOption, String createdBy, Date createdDate,
			String isactive, String modifiedBy, Date modifiedDate,
			String approvedBy, Date approvedDate, String remarks ,CountryMaster applicationCountryId) {
		super();
		this.loyaltyCatagoryId = loyaltyCatagoryId;
		this.categoryCode = categoryCode;
		this.categoryType = categoryType;
		this.fieldName = fieldName;
		this.validOption = validOption;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.isactive = isactive;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.remarks = remarks;
		this.applicationCountryId = applicationCountryId;
	}

	@Id
	@GeneratedValue(generator="ex_lty_category_master_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_lty_category_master_seq",sequenceName="EX_LTY_CATEGORY_MASTER_SEQ",allocationSize=1)
	@Column(name = "LTY_CATEGORY_ID")
	public BigDecimal getLoyaltyCatagoryId() {
		return loyaltyCatagoryId;
	}

	public void setLoyaltyCatagoryId(BigDecimal loyaltyCatagoryId) {
		this.loyaltyCatagoryId = loyaltyCatagoryId;
	}

	@Column(name = "CATEGORY_CODE")
	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	

	@Column(name = "CATEGORY_TYPE")
	public String getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}
	@Column(name = "FIELD_NAME")
	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	@Column(name = "VALID_OPTION")
	public String getValidOption() {
		return validOption;
	}

	public void setValidOption(String validOption) {
		this.validOption = validOption;
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
	@Column(name = "ISACTIVE")
	public String getIsactive() {
		return isactive;
	}

	public void setIsactive(String isactive) {
		this.isactive = isactive;
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
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "APPLICATION_COUNTRY_ID")
	public CountryMaster getApplicationCountryId() {
		return applicationCountryId;
	}

	public void setApplicationCountryId(CountryMaster applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}
	
	
}
