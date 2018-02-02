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
@Table(name = "EX_LTY_MAIL_OPTIONS")
public class LoyaltyMailOptions implements Serializable 
{
	private static final long serialVersionUID = 1L;
	private BigDecimal loyaltyMailOptionsId;
	private String smtpHost;
	private String toAddress1;
	private String toAddress2;
	private String fromAddress;
	private String mailFlag;
	private String isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;
	private CountryMaster applicationCountryId;
	
	public LoyaltyMailOptions() 
	{
		
	}
	public LoyaltyMailOptions(BigDecimal loyaltyMailOptionsId, String smtpHost,
			String toAddress1, String toAddress2, String fromAddress,
			String mailFlag, String isActive, String createdBy,
			Date createdDate, String modifiedBy, Date modifiedDate,
			String approvedBy, Date approvedDate, String remarks,
			CountryMaster applicationCountryId) {
		super();
		this.loyaltyMailOptionsId = loyaltyMailOptionsId;
		this.smtpHost = smtpHost;
		this.toAddress1 = toAddress1;
		this.toAddress2 = toAddress2;
		this.fromAddress = fromAddress;
		this.mailFlag = mailFlag;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.remarks = remarks;
		this.applicationCountryId = applicationCountryId;
	}
	
	@Id
	@GeneratedValue(generator="ex_lty_mail_options_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_lty_mail_options_seq",sequenceName="EX_LTY_MAIL_OPTIONS_SEQ",allocationSize=1)
	@Column(name = "LTY_MAIL_OPTIONS_ID")
	public BigDecimal getloyaltyMailOptionsId() {
		return loyaltyMailOptionsId;
	}
	public void setloyaltyMailOptionsId(BigDecimal loyaltyMailOptionsId) {
		this.loyaltyMailOptionsId = loyaltyMailOptionsId;
	}
	@Column(name = "SMTP_HOST")
	public String getSmtpHost() {
		return smtpHost;
	}
	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}
	@Column(name = "TO_ADDRESS1")
	public String getToAddress1() {
		return toAddress1;
	}
	public void setToAddress1(String toAddress1) {
		this.toAddress1 = toAddress1;
	}
	@Column(name = "TO_ADDRESS2")
	public String getToAddress2() {
		return toAddress2;
	}
	public void setToAddress2(String toAddress2) {
		this.toAddress2 = toAddress2;
	}
	@Column(name = "FROM_ADDRESS")
	public String getFromAddress() {
		return fromAddress;
	}
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}
	@Column(name = "MAIL_FLAG")
	public String getMailFlag() {
		return mailFlag;
	}
	public void setMailFlag(String mailFlag) {
		this.mailFlag = mailFlag;
	}
	@Column(name = "ISACTIVE")
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
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
