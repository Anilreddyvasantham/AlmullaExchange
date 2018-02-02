package com.amg.exchange.registration.model;

import java.io.Serializable;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/*
 * Author Rahamathali Shaik
*/

@Entity
@Table(name= "FS_CUSTOMER_CH_LOG")
public class CustomerChangeLog implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal customerChangeLogId;
	private BigDecimal applicationCountryId;
	private BigDecimal companyId;
	private BigDecimal customerId;
	private String fromTableName;
	private String fromColumnName;
	private String oldValue;
	private String newValue;
	private String modifiedBy;
	private String verificationToken;
	private Date createdDate;
	private String createdBy;
	private String logoPath;
	
	
	
	public CustomerChangeLog() {
	}

	public CustomerChangeLog(BigDecimal customerChangeLogId,
			BigDecimal applicationCountryId, BigDecimal companyId,
			BigDecimal customerId, String fromTableName, String fromColumnName,
			String oldValue, String newValue, String modifiedBy,
			String verificationToken, Date createdDate, String createdBy) {
		this.customerChangeLogId = customerChangeLogId;
		this.applicationCountryId = applicationCountryId;
		this.companyId = companyId;
		this.customerId = customerId;
		this.fromTableName = fromTableName;
		this.fromColumnName = fromColumnName;
		this.oldValue = oldValue;
		this.newValue = newValue;
		this.modifiedBy = modifiedBy;
		this.verificationToken = verificationToken;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
	}

	
	@Id
	@GeneratedValue(generator="fs_cust_ch_log_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="fs_cust_ch_log_seq" ,sequenceName="FS_CUST_CH_LOG_SEQ",allocationSize=1)
	@Column(name = "CUSTOMER_CH_LOG_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getCustomerChangeLogId() {
		return customerChangeLogId;
	}

	public void setCustomerChangeLogId(BigDecimal customerChangeLogId) {
		this.customerChangeLogId = customerChangeLogId;
	}

	@Column(name = "APP_COUNTRY_ID")
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}

	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}

	@Column(name = "COMPANY_ID")
	public BigDecimal getCompanyId() {
		return companyId;
	}

	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}

	@Column(name = "CUSTOMER_ID")
	public BigDecimal getCustomerId() {
		return customerId;
	}

	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	@Column(name = "FROM_TABLE_NAME")
	public String getFromTableName() {
		return fromTableName;
	}

	public void setFromTableName(String fromTableName) {
		this.fromTableName = fromTableName;
	}

	@Column(name = "FROM_COLUMN_NAME")
	public String getFromColumnName() {
		return fromColumnName;
	}

	public void setFromColumnName(String fromColumnName) {
		this.fromColumnName = fromColumnName;
	}

	@Column(name = "OLD_VALUE")
	public String getOldValue() {
		return oldValue;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	@Column(name = "NEW_VALUE")
	public String getNewValue() {
		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	@Column(name = "MODIFIED_BY")
	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "VERIFICATION_TOKEN")
	public String getVerificationToken() {
		return verificationToken;
	}

	public void setVerificationToken(String verificationToken) {
		this.verificationToken = verificationToken;
	}

	@Column(name = "CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	
	
	
	
	
}
