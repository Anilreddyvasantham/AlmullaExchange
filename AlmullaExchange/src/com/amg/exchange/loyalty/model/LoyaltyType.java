package com.amg.exchange.loyalty.model;

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
@Table(name = "EX_LTY_TYPE_MASTER")
public class LoyaltyType implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private BigDecimal loyalityTypeId;
	private String loyalityTypeCode;
	private String loyalityType;
	private String corporateCode;
	private String corporatePoints;
	private String employeePoints;
	private BigDecimal levelNo;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String isActive;
	private String remarks;
	private CountryMaster applicationCountryId;

	public LoyaltyType() {
	}

	public LoyaltyType(BigDecimal loyalityTypeId, String loyalityTypeCode,  String corporateCode, String loyalityType,String corporatePoints, String employeePoints, BigDecimal levelNo, String createdBy, Date createdDate, String modifiedBy, Date modifiedDate, String approvedBy, Date approvedDate,
			String isActive, String remarks ,CountryMaster applicationCountryId) {//String loyalityType,
		super();
		this.loyalityTypeId = loyalityTypeId;
		this.loyalityTypeCode = loyalityTypeCode;
		this.loyalityType = loyalityType;
		this.corporateCode = corporateCode;
		this.corporatePoints = corporatePoints;
		this.employeePoints = employeePoints;
		this.levelNo = levelNo;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.isActive = isActive;
		this.remarks = remarks;
		this.applicationCountryId = applicationCountryId;
	}

	@Id
	@GeneratedValue(generator = "ex_lty_type_master_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ex_lty_type_master_seq", sequenceName = "EX_LTY_TYPE_MASTER_SEQ", allocationSize = 1)
	@Column(name = "LTY_TYPE_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getLoyalityTypeId() {
		return loyalityTypeId;
	}

	public void setLoyalityTypeId(BigDecimal loyalityTypeId) {
		this.loyalityTypeId = loyalityTypeId;
	}

	@Column(name = "LTY_TYPE_CODE")
	public String getLoyalityTypeCode() {
		return loyalityTypeCode;
	}

	public void setLoyalityTypeCode(String loyalityTypeCode) {
		this.loyalityTypeCode = loyalityTypeCode;
	}

	@Column(name = "LOYALTY_TYPE")
	public String getLoyalityType() {
		return loyalityType;
	}

	public void setLoyalityType(String loyalityType) {
		this.loyalityType = loyalityType;
	}

	@Column(name = "CORPORATE_CODE")
	public String getCorporateCode() {
		return corporateCode;
	}

	public void setCorporateCode(String corporateCode) {
		this.corporateCode = corporateCode;
	}

	@Column(name = "CORPORATE_POINTS")
	public String getCorporatePoints() {
		return corporatePoints;
	}

	public void setCorporatePoints(String corporatePoints) {
		this.corporatePoints = corporatePoints;
	}

	@Column(name = "EMPLOYEE_POINTS")
	public String getEmployeePoints() {
		return employeePoints;
	}

	public void setEmployeePoints(String employeePoints) {
		this.employeePoints = employeePoints;
	}

	@Column(name = "LEVEL_NUMBER")
	public BigDecimal getLevelNo() {
		return levelNo;
	}

	public void setLevelNo(BigDecimal levelNo) {
		this.levelNo = levelNo;
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

	@Column(name = "ISACTIVE")
	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
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
