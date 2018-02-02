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

import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.treasury.model.BankMaster;


@Entity
@Table(name="EX_DEAL_CUSTOMER_SUPPLIER")
public class DealCustomerSupplier implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	 private BigDecimal dealCustomerSupplierId;
     private Customer dealSupplierCustomer;
     private BankMaster bankMaster;
     private String bankAccountNumber;
     private String createdBy;
     private Date createdDate;
     private String modifiedBy;
     private Date modifiedDate;
     
     
     @Id
     @GeneratedValue(generator="ex_deal_customer_supplier_seq",strategy=GenerationType.SEQUENCE)
     @SequenceGenerator(name="ex_deal_customer_supplier_seq", sequenceName="EX_DEAL_CUSTOMER_SUPPLIER_SEQ",allocationSize=1)
     @Column(name="DEAL_CUSTOMER_SUPPLIER_ID", unique = true, nullable = false, precision = 22, scale = 0)
     
	public BigDecimal getDealCustomerSupplier() {
		return dealCustomerSupplierId;
	}
	public void setDealCustomerSupplier(BigDecimal dealCustomerSupplier) {
		this.dealCustomerSupplierId = dealCustomerSupplier;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMER_REF_ID")
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
	
	@Column(name="BANK_ACCOUNT_NUMBER")
	public String getBankAccountNumber() {
		return bankAccountNumber;
	}
	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
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
     
}
