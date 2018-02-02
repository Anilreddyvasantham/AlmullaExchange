package com.amg.exchange.treasury.deal.supplier.model;

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
import javax.persistence.UniqueConstraint;

import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.CurrencyMaster;


@Entity
@Table(name="EX_TREASURY_CUSTOMER_SUPPLIER", uniqueConstraints = @UniqueConstraint(columnNames = {"BANK_ACCOUNT_NUMBER"}))
public class TreasuryCustomerSupplier implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	 private BigDecimal dealCustomerSupplierId;
     private Customer dealSupplierCustomer;
     private BankMaster bankMaster;
     private CurrencyMaster dealSupplierCurrency;
     private BigDecimal bankAccountNo;//change1
     private String customerName;
     private String createdBy;
     private Date createdDate;
     private String modifiedBy;
     private Date modifiedDate;
     private String isActive;
     
    
     
     

	public TreasuryCustomerSupplier() {
	}
	
	public TreasuryCustomerSupplier(BigDecimal dealCustomerSupplierId) {
		this.dealCustomerSupplierId = dealCustomerSupplierId;
	}

	
	
	

	public TreasuryCustomerSupplier(BigDecimal dealCustomerSupplierId,
			Customer dealSupplierCustomer, BankMaster bankMaster,
			CurrencyMaster dealSupplierCurrency, BigDecimal bankAccountNo,
			String customerName, String createdBy, Date createdDate,
			String modifiedBy, Date modifiedDate, String isActive) {
		super();
		this.dealCustomerSupplierId = dealCustomerSupplierId;
		this.dealSupplierCustomer = dealSupplierCustomer;
		this.bankMaster = bankMaster;
		this.dealSupplierCurrency = dealSupplierCurrency;
		this.bankAccountNo = bankAccountNo;
		this.customerName = customerName;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.isActive = isActive;
	}

	@Id
     @GeneratedValue(generator="ex_deal_customer_supplier_seq",strategy=GenerationType.SEQUENCE)
     @SequenceGenerator(name="ex_deal_customer_supplier_seq", sequenceName="EX_DEAL_CUSTOMER_SUPPLIER_SEQ",allocationSize=1)
     @Column(name="TREASURY_CUSTOMER_SUPPLIER_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getDealCustomerSupplierId() {
		return dealCustomerSupplierId;
	}

	public void setDealCustomerSupplierId(BigDecimal dealCustomerSupplierId) {
		this.dealCustomerSupplierId = dealCustomerSupplierId;
	} 
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMER_ID")
	public Customer getDealSupplierCustomer() {
		return dealSupplierCustomer;
	}
	public void setDealSupplierCustomer(Customer dealSupplierCustomer) {
		this.dealSupplierCustomer = dealSupplierCustomer;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_ID")
	public BankMaster getBankMaster() {
		return bankMaster;
	}
	public void setBankMaster(BankMaster bankMaster) {
		this.bankMaster = bankMaster;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CURRENCY_ID")
	public CurrencyMaster getDealSupplierCurrency() {
		return dealSupplierCurrency;
	}

	public void setDealSupplierCurrency(CurrencyMaster dealSupplierCurrency) {
		this.dealSupplierCurrency = dealSupplierCurrency;
	}

	@Column(name="BANK_ACCOUNT_NUMBER")
	public BigDecimal getBankAccountNo() {
		return bankAccountNo;
	}
	public void setBankAccountNo(BigDecimal bankAccountNo) {
		this.bankAccountNo = bankAccountNo;
	}
	
	@Column(name="CUSTOMER_NAME")
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	
	@Column(name="CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	@Column(name="CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	@Column(name="MODIFIED_BY")
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	@Column(name="MODIFIED_DATE")
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}


	@Column(name="ISACTIVE")
	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
	
}
