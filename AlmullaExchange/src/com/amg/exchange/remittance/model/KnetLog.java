package com.amg.exchange.remittance.model;

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


@Entity
@Table(name="EX_KNET_LOG")
public class KnetLog implements  Serializable {

	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(generator="EX_KNET_LOG_SEQ",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="EX_KNET_LOG_SEQ",sequenceName="EX_KNET_LOG_SEQ",allocationSize=1)
	@Column(name = "KNET_LOG_ID", unique = true, nullable = false, precision = 22, scale = 0)
	private BigDecimal knetLogId;
	
	@Column(name="APPLICATION_COUNTRY_ID")
	private  BigDecimal applicationCountryId;
	
	@Column(name="COMPANY_ID")
	private  BigDecimal companyId;
	
	@Column(name="CUSTOMER_ID")
	private  BigDecimal custmoerId;
	
	
	@Column(name="COLLAMT")
	private  BigDecimal knetAmount;
  
	@Column(name="AUTH_CODE")
	private String authCode;
	
	@Column(name="knet_message")
	private String knetMessage;
	
	@Column(name="receipt_data")
	private String receiptData;
	
	@Column(name="receipt")
	private String receipt;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	@Column(name="CREATED_DATE")
	private Date createdDate;
	
	@Column(name="ISACTIVE")
	private String isActive;

	public BigDecimal getKnetLogId() {
		return knetLogId;
	}

	public void setKnetLogId(BigDecimal knetLogId) {
		this.knetLogId = knetLogId;
	}

	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}

	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}

	public BigDecimal getCompanyId() {
		return companyId;
	}

	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}

	public BigDecimal getCustmoerId() {
		return custmoerId;
	}

	public void setCustmoerId(BigDecimal custmoerId) {
		this.custmoerId = custmoerId;
	}

	public BigDecimal getKnetAmount() {
		return knetAmount;
	}

	public void setKnetAmount(BigDecimal knetAmount) {
		this.knetAmount = knetAmount;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public String getKnetMessage() {
		return knetMessage;
	}

	public void setKnetMessage(String knetMessage) {
		this.knetMessage = knetMessage;
	}

	public String getReceiptData() {
		return receiptData;
	}

	public void setReceiptData(String receiptData) {
		this.receiptData = receiptData;
	}

	public String getReceipt() {
		return receipt;
	}

	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	  
	
	
	
	
}
