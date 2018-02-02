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
@Table(name = "EX_LTY_LOG_OPTIONS")
public class LoyaltyLogOptions implements Serializable {

	private static final long serialVersionUID = 1L;
	private BigDecimal loyaltyLogOptionsId;
	private String logFlag;
	private String writeCustomerFlag;
	private String logOptionType;
	private String isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;
	private CountryMaster applicationCountryId;
	
	public LoyaltyLogOptions() {
		
	}

	public LoyaltyLogOptions(BigDecimal loyaltyLogOptionsId, String logFlag,
			String writeCustomerFlag, String isActive, String createdBy,
			Date createdDate, String modifiedBy, Date modifiedDate,
			String approvedBy, Date approvedDate, String remarks,
			CountryMaster applicationCountryId) {
		super();
		this.loyaltyLogOptionsId = loyaltyLogOptionsId;
		this.logFlag = logFlag;
		this.writeCustomerFlag = writeCustomerFlag;
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
	@GeneratedValue(generator="ex_lty_log_options_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_lty_log_options_seq",sequenceName="EX_LTY_LOG_OPTIONS_SEQ",allocationSize=1)
	@Column(name = "LTY_LOG_OPTIONS_ID")
	public BigDecimal getLoyaltyLogOptionsId() {
		return loyaltyLogOptionsId;
	}

	public void setLoyaltyLogOptionsId(BigDecimal loyaltyLogOptionsId) {
		this.loyaltyLogOptionsId = loyaltyLogOptionsId;
	}
	@Column(name = "LOG_FLAG")
	public String getLogFlag() {
		return logFlag;
	}

	public void setLogFlag(String logFlag) {
		this.logFlag = logFlag;
	}
	@Column(name = "WRITE_CUSTOMER_FLAG")
	public String getWriteCustomerFlag() {
		return writeCustomerFlag;
	}

	public void setWriteCustomerFlag(String writeCustomerFlag) {
		this.writeCustomerFlag = writeCustomerFlag;
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
	@Column(name = "LOG_OPTION_TYPE")
	public String getLogOptionType() {
		return logOptionType;
	}

	public void setLogOptionType(String logOptionType) {
		this.logOptionType = logOptionType;
	}
}
