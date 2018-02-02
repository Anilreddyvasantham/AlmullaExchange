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
@Table(name = "EX_PRODUCT_GROUP")
public class ProductGroup implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal productGroupId;
	private BigDecimal productGroupSerial;
	private String productgroupdesc;
	private BigDecimal bankId;
	private String bankCode;
	private BigDecimal bankLimit;
	private BigDecimal currencyId;
	private String currencyCode;
	private String debitAccountNo;
	private String printMode;
	private BigDecimal transactionLimit;
	private String isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	
	public ProductGroup() {
		super();
	}

	public ProductGroup(BigDecimal productGroupId,
			BigDecimal productGroupSerial, String productgroupdesc,
			BigDecimal bankId, String bankCode, BigDecimal bankLimit,
			BigDecimal currencyId, String currencyCode, String debitAccountNo,
			String printMode, BigDecimal transactionLimit, String isActive,
			String createdBy, Date createdDate, String modifiedBy,
			Date modifiedDate) {
		super();
		this.productGroupId = productGroupId;
		this.productGroupSerial = productGroupSerial;
		this.productgroupdesc = productgroupdesc;
		this.bankId = bankId;
		this.bankCode = bankCode;
		this.bankLimit = bankLimit;
		this.currencyId = currencyId;
		this.currencyCode = currencyCode;
		this.debitAccountNo = debitAccountNo;
		this.printMode = printMode;
		this.transactionLimit = transactionLimit;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}
	
	@Id
	@GeneratedValue(generator="ex_product_group_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_product_group_seq",sequenceName="EX_PRODUCT_GROUP_SEQ",allocationSize=1)
	@Column(name = "PRODUCT_GRP_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getProductGroupId() {
		return productGroupId;
	}
	public void setProductGroupId(BigDecimal productGroupId) {
		this.productGroupId = productGroupId;
	}

	@Column(name = "PRODUCT_GRP")
	public BigDecimal getProductGroupSerial() {
		return productGroupSerial;
	}
	public void setProductGroupSerial(BigDecimal productGroupSerial) {
		this.productGroupSerial = productGroupSerial;
	}

	@Column(name = "PRODUCT_GRDES")
	public String getProductgroupdesc() {
		return productgroupdesc;
	}
	public void setProductgroupdesc(String productgroupdesc) {
		this.productgroupdesc = productgroupdesc;
	}

	@Column(name = "BANK_ID")
	public BigDecimal getBankId() {
		return bankId;
	}
	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}

	@Column(name = "BANK_CODE")
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	@Column(name = "BANK_LIMIT")
	public BigDecimal getBankLimit() {
		return bankLimit;
	}
	public void setBankLimit(BigDecimal bankLimit) {
		this.bankLimit = bankLimit;
	}

	@Column(name = "CURRENCY_ID")
	public BigDecimal getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	@Column(name = "CURRENCY_CODE")
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	@Column(name = "DEBIT_ACCOUNT")
	public String getDebitAccountNo() {
		return debitAccountNo;
	}
	public void setDebitAccountNo(String debitAccountNo) {
		this.debitAccountNo = debitAccountNo;
	}

	@Column(name = "PRINTMODE")
	public String getPrintMode() {
		return printMode;
	}
	public void setPrintMode(String printMode) {
		this.printMode = printMode;
	}

	@Column(name = "TRANSACTION_LIMIT")
	public BigDecimal getTransactionLimit() {
		return transactionLimit;
	}
	public void setTransactionLimit(BigDecimal transactionLimit) {
		this.transactionLimit = transactionLimit;
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
	

}
