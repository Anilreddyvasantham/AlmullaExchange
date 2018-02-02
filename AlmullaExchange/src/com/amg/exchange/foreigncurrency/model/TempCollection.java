package com.amg.exchange.foreigncurrency.model;

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

import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.treasury.model.CurrencyMaster;

@Entity
@Table(name = "EX_TEMP_COLLECTION")
public class TempCollection implements Serializable {
	private static final long serialVersionUID = 1L;

	public TempCollection() {
	}

	private BigDecimal documentCode;
	private BigDecimal applicationCountryId;
	private Customer fsCustomer;
	private BigDecimal collectionId;
	private Date collectDate;
	private CurrencyMaster exCurrencyMaster;
	private BigDecimal paidAmount;
	private BigDecimal refoundAmount;
	private BigDecimal netAmount;
	private Date accountMMYYYY;
	private CountryBranch countryBranch;
	private String receiptType;
	private String createdBy;
	private Date createdDate;
	
	private String cashDeclarationIndicator;
	private String totalAmountDeclarationIndicator;

	@Id
	@GeneratedValue(generator = "ex_collection_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ex_collection_seq", sequenceName = "EX_TEMP_COLLECTION_SEQ", allocationSize = 1)
	@Column(name = "DOCUMENT_SEQ_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getCollectionId() {
		return collectionId;
	}

	public void setCollectionId(BigDecimal collectionId) {
		this.collectionId = collectionId;
	}

	@Column(name = "APPLICATION_COUNTRY_ID")
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}

	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMER_ID")
	public Customer getFsCustomer() {
		return fsCustomer;
	}

	public void setFsCustomer(Customer fsCustomer) {
		this.fsCustomer = fsCustomer;
	}

	@Column(name = "DOCUMENT_CODE")
	public BigDecimal getDocumentCode() {
		return documentCode;
	}

	public void setDocumentCode(BigDecimal documentCode) {
		this.documentCode = documentCode;
	}

	@Column(name = "COLLECT_DATE")
	public Date getCollectDate() {
		return collectDate;
	}

	public void setCollectDate(Date collectDate) {
		this.collectDate = collectDate;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CURRENCY_ID")
	public CurrencyMaster getExCurrencyMaster() {
		return exCurrencyMaster;
	}

	public void setExCurrencyMaster(CurrencyMaster exCurrencyMaster) {
		this.exCurrencyMaster = exCurrencyMaster;
	}

	@Column(name = "PAID_AMOUNT")
	public BigDecimal getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}

	@Column(name = "REFUNDED_AMOUNT")
	public BigDecimal getRefoundAmount() {
		return refoundAmount;
	}

	public void setRefoundAmount(BigDecimal refoundAmount) {
		this.refoundAmount = refoundAmount;
	}

	@Column(name = "NET_AMOUNT")
	public BigDecimal getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(BigDecimal netAmount) {
		this.netAmount = netAmount;
	}


	@Column(name = "ACCOUNT_MMYYYY")
	public Date getAccountMMYYYY() {
		return accountMMYYYY;
	}

	public void setAccountMMYYYY(Date accountMMYYYY) {
		this.accountMMYYYY = accountMMYYYY;
	}

	@Column(name = "CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "CREATION_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_BRANCH_ID")
	public CountryBranch getCountryBranch() {
		return countryBranch;
	}

	public void setCountryBranch(CountryBranch countryBranch) {
		this.countryBranch = countryBranch;
	}

	@Column(name = "RECEIPT_TYPE")
	public String getReceiptType() {
		return receiptType;
	}

	public void setReceiptType(String receiptType) {
		this.receiptType = receiptType;
	}
	
	
	
	@Column(name = "DECL_PRINT_2KCASH")
	public String getCashDeclarationIndicator() {
		return cashDeclarationIndicator;
	}

	public void setCashDeclarationIndicator(String cashDeclarationIndicator) {
		this.cashDeclarationIndicator = cashDeclarationIndicator;
	}
	
	@Column(name = "DECL_PRINT_TOTALNET")
	public String getTotalAmountDeclarationIndicator() {
		return totalAmountDeclarationIndicator;
	}

	public void setTotalAmountDeclarationIndicator(
			String totalAmountDeclarationIndicator) {
		this.totalAmountDeclarationIndicator = totalAmountDeclarationIndicator;
	}
}
