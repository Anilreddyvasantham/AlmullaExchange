/*******************************************************************************************************************

File	: CustomerBank.java

Project	: AlmullaExchange

Package	: com.amg.exchange.treasury.model

Created	:	
				Date	: 27-Jan-2015 
				By		: Nagarjuna.Atla
				Revision:

Last Change:
				Date	: 
				By		: 
				Revision:

Description:
 
**********************************************************************************************************************/
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

import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.treasury.model.BankMaster;

@Entity
@Table(name = "EX_CUSTOMER_BANK")
public class CustomerBank implements Serializable{
	private static final long serialVersionUID = 2315791709068216697L;
	
	private BigDecimal 	customerBankId;
	private Customer fsCustomer;
	private BankMaster  fsBankMaster;
	private String bankCode;
	private String collectionMode;
	private String debitCard;
	private String debitCardName;
	private String authorizedBy;
	private Date authorizedDate;
	private String isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private BigDecimal customerReference;
	private String password;
	private String stsRemarks;
	private String bankPrefix;
	private String bankSuffix;
	
	
	public CustomerBank() {
		super();
	}
	
	public CustomerBank(BigDecimal customerBankId, Customer fsCustomer,
			BankMaster fsBankMaster, String bankCode, String collectionMode,
			String debitCard, String debitCardName, String authorizedBy,
			Date authorizedDate, String isActive, String createdBy,
			Date createdDate, String modifiedBy, Date modifiedDate,
			BigDecimal customerReference, String password, String stsRemarks,
			String bankPrefix, String bankSuffix) {
		super();
		this.customerBankId = customerBankId;
		this.fsCustomer = fsCustomer;
		this.fsBankMaster = fsBankMaster;
		this.bankCode = bankCode;
		this.collectionMode = collectionMode;
		this.debitCard = debitCard;
		this.debitCardName = debitCardName;
		this.authorizedBy = authorizedBy;
		this.authorizedDate = authorizedDate;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.customerReference = customerReference;
		this.password = password;
		this.stsRemarks = stsRemarks;
		this.bankPrefix = bankPrefix;
		this.bankSuffix = bankSuffix;
	}

	@Id
	@GeneratedValue(generator = "ex_customer_bank_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ex_customer_bank_seq", sequenceName = "EX_CUSTOMER_BANK_SEQ", allocationSize = 1)
	@Column(name = "CUSTOMER_BANK_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getCustomerBankId() {
		return customerBankId;
	}
	public void setCustomerBankId(BigDecimal customerBankId) {
		this.customerBankId = customerBankId;
	}
	
	@Column(name = "CUSTOMER_REFERENCE")
	public BigDecimal getCustomerReference() {
		return customerReference;
	}
	public void setCustomerReference(BigDecimal customerReference) {
		this.customerReference = customerReference;
	}
	 
	@Column(name = "BANK_CODE")
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
	@Column(name = "COLLECTION_MODE")
	public String getCollectionMode() {
		return collectionMode;
	}
	public void setCollectionMode(String collectionMode) {
		this.collectionMode = collectionMode;
	}
	
	@Column(name = "DEBIT_CARD")
	public String getDebitCard() {
		return debitCard;
	}
	public void setDebitCard(String debitCard) {
		this.debitCard = debitCard;
	}
	
	@Column(name = "DEBIT_CARD_NAME")
	public String getDebitCardName() {
		return debitCardName;
	}
	public void setDebitCardName(String debitCardName) {
		this.debitCardName = debitCardName;
	}
	
	@Column(name = "AUTHORIZED_BY")
	public String getAuthorizedBy() {
		return authorizedBy;
	}
	public void setAuthorizedBy(String authorizedBy) {
		this.authorizedBy = authorizedBy;
	}
	
	@Column(name = "AUTHORIZED_DATE")
	public Date getAuthorizedDate() {
		return authorizedDate;
	}
	public void setAuthorizedDate(Date authorizedDate) {
		this.authorizedDate = authorizedDate;
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMER_ID")
	public Customer getFsCustomer() {
		return fsCustomer;
	}
	public void setFsCustomer(Customer fsCustomer) {
		this.fsCustomer = fsCustomer;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_ID")
	public BankMaster getFsBankMaster() {
		return fsBankMaster;
	}
	public void setFsBankMaster(BankMaster fsBankMaster) {
		this.fsBankMaster = fsBankMaster;
	}
	
	@Column(name = "PASSWORD")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(name = "REMARKS")
	public String getStsRemarks() {
		return stsRemarks;
	}
	public void setStsRemarks(String stsRemarks) {
		this.stsRemarks = stsRemarks;
	}

	@Column(name = "BANK_PREFIX")
	public String getBankPrefix() {
		return bankPrefix;
	}
	public void setBankPrefix(String bankPrefix) {
		this.bankPrefix = bankPrefix;
	}

	@Column(name = "BANK_SUFFIX")
	public String getBankSuffix() {
		return bankSuffix;
	}
	public void setBankSuffix(String bankSuffix) {
		this.bankSuffix = bankSuffix;
	}
	
}
