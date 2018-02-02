package com.amg.exchange.registration.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "FS_CUSTOMER_MOBILE_LOG" )
public class CustomerMobileLogModel implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	private BigDecimal customerMobileId;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private BigDecimal customerId;
	private BigDecimal customerReference;
	private String isActive;
	private String mobile;
	private BigDecimal otpNo;
	private BigDecimal otpRetry;
	private Date otpRetryDate;
	private Date otpVerifiedDate;
	private String otpVerifiedBy;
	private String otpVerifiedRemarks;
	
	public CustomerMobileLogModel() {
		super();
	}

	public CustomerMobileLogModel(BigDecimal customerMobileId,
			String createdBy, Date createdDate, String modifiedBy,
			Date modifiedDate, BigDecimal customerId,
			BigDecimal customerReference, String isActive, String mobile,
			BigDecimal otpNo, BigDecimal otpRetry, Date otpRetryDate,
			Date otpVerifiedDate, String otpVerifiedBy,
			String otpVerifiedRemarks) {
		super();
		this.customerMobileId = customerMobileId;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.customerId = customerId;
		this.customerReference = customerReference;
		this.isActive = isActive;
		this.mobile = mobile;
		this.otpNo = otpNo;
		this.otpRetry = otpRetry;
		this.otpRetryDate = otpRetryDate;
		this.otpVerifiedDate = otpVerifiedDate;
		this.otpVerifiedBy = otpVerifiedBy;
		this.otpVerifiedRemarks = otpVerifiedRemarks;
	}

	@Id
	@GeneratedValue(generator="fs_customer_mobile_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="fs_customer_mobile_seq" ,sequenceName="FS_CUSTOMER_MOBILE_SEQ",allocationSize=1)
	@Column(name = "CUSTOMER_MOBILE_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getCustomerMobileId() {
		return customerMobileId;
	}
	public void setCustomerMobileId(BigDecimal customerMobileId) {
		this.customerMobileId = customerMobileId;
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

	@Column(name = "CUSTOMER_ID")
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	@Column(name = "IS_ACTIVE")
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Column(name = "MOBILE")
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "OTP_NO")
	public BigDecimal getOtpNo() {
		return otpNo;
	}
	public void setOtpNo(BigDecimal otpNo) {
		this.otpNo = otpNo;
	}
	
	@Column(name = "OTP_RETRY")
	public BigDecimal getOtpRetry() {
		return otpRetry;
	}
	public void setOtpRetry(BigDecimal otpRetry) {
		this.otpRetry = otpRetry;
	}

	@Column(name = "OTP_RETRY_DATE")
	public Date getOtpRetryDate() {
		return otpRetryDate;
	}
	public void setOtpRetryDate(Date otpRetryDate) {
		this.otpRetryDate = otpRetryDate;
	}

	@Column(name = "OTP_VERIFIED_DT")
	public Date getOtpVerifiedDate() {
		return otpVerifiedDate;
	}
	public void setOtpVerifiedDate(Date otpVerifiedDate) {
		this.otpVerifiedDate = otpVerifiedDate;
	}

	@Column(name = "OTP_VERIFIED_BY")
	public String getOtpVerifiedBy() {
		return otpVerifiedBy;
	}
	public void setOtpVerifiedBy(String otpVerifiedBy) {
		this.otpVerifiedBy = otpVerifiedBy;
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
	
	@Column(name = "CUSTOMER_REFERENCE")
	public BigDecimal getCustomerReference() {
		return customerReference;
	}
	public void setCustomerReference(BigDecimal customerReference) {
		this.customerReference = customerReference;
	}

	@Column(name = "REMARKS")
	public String getOtpVerifiedRemarks() {
		return otpVerifiedRemarks;
	}
	public void setOtpVerifiedRemarks(String otpVerifiedRemarks) {
		this.otpVerifiedRemarks = otpVerifiedRemarks;
	}
	
	

}
