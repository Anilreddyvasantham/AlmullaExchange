package com.amg.exchange.cbk.model;

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

@Entity
@Table(name = "EX_CBK_DETAILS")
public class CBKDetails implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal cbkDetailsId;
	private BigDecimal companyCode;
	private String activityCode;
	private BigDecimal activityCenter;
	private String accountClass;
	private String accountCategory;
	private CBKHeader cbkId;
	private CBKLines cbkLineId;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private BigDecimal applicationCountryId;
	private String isActive;

	public CBKDetails() {

	}	

	public CBKDetails(BigDecimal cbkDetailsId, BigDecimal companyCode,
			String activityCode, BigDecimal activityCenter,
			String accountClass, String accountCategory, CBKHeader cbkId,
			CBKLines cbkLineId, String createdBy, Date createdDate,
			String modifiedBy, Date modifiedDate,
			BigDecimal applicationCountryId, String isActive) {
		super();
		this.cbkDetailsId = cbkDetailsId;
		this.companyCode = companyCode;
		this.activityCode = activityCode;
		this.activityCenter = activityCenter;
		this.accountClass = accountClass;
		this.accountCategory = accountCategory;
		this.cbkId = cbkId;
		this.cbkLineId = cbkLineId;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.applicationCountryId = applicationCountryId;
		this.isActive = isActive;
	}



	@Id
	@GeneratedValue(generator = "ex_cbk_details_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ex_cbk_details_seq", sequenceName = "EX_CBK_DETAILS_SEQ", allocationSize = 1)
	@Column(name = "CBK_DETAILS_SEQ_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getCbkDetailsId() {
		return cbkDetailsId;
	}

	public void setCbkDetailsId(BigDecimal cbkDetailsId) {
		this.cbkDetailsId = cbkDetailsId;
	}

	@Column(name = "COMPANY_CODE")
	public BigDecimal getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(BigDecimal companyCode) {
		this.companyCode = companyCode;
	}

	@Column(name = "ACTIVITY_CODE")
	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

	@Column(name = "ACTIVITY_CENTER")
	public BigDecimal getActivityCenter() {
		return activityCenter;
	}

	public void setActivityCenter(BigDecimal activityCenter) {
		this.activityCenter = activityCenter;
	}

/*	@Column(name = "ACCOUNT_CLASS")
	public String getActivityClass() {
		return activityClass;
	}

	public void setActivityClass(String activityClass) {
		this.activityClass = activityClass;
	}*/
	@Column(name = "ACCOUNT_CLASS")
	public String getAccountClass() {
		return accountClass;
	}

	public void setAccountClass(String accountClass) {
		this.accountClass = accountClass;
	}
	

	@Column(name = "ACCOUNT_CATEGORY")
	public String getAccountCategory() {
		return accountCategory;
	}


	public void setAccountCategory(String accountCategory) {
		this.accountCategory = accountCategory;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CBK_REPORT_SEQ_ID")
	public CBKHeader getCbkId() {
		return cbkId;
	}

	public void setCbkId(CBKHeader cbkId) {
		this.cbkId = cbkId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CBK_LINE_SEQ_ID")
	public CBKLines getCbkLineId() {
		return cbkLineId;
	}

	public void setCbkLineId(CBKLines cbkLineId) {
		this.cbkLineId = cbkLineId;
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

	@Column(name = "APPLICATION_COUNTRY_ID")
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}

	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}

	@Column(name = "IS_ACTIVE")
	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

}
