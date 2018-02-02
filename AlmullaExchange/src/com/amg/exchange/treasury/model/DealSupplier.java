package com.amg.exchange.treasury.model;

// default package
// Generated Jul 10, 2014 5:34:26  Created by Chennai ODC

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
import javax.persistence.TableGenerator;
import javax.persistence.UniqueConstraint;

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;

/**
 * ExDealSupplier Created by Chennai ODC
 */
@Entity
@Table(name = "EX_DEAL_SUPPLIER", uniqueConstraints = @UniqueConstraint(columnNames = {
		"COUNTRY_ID", "COMPANY_ID", "DOCUMENT_FIN_YEAR", "DOCUMENT_CODE" }))
public class DealSupplier implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2315791709068216697L;
	private BigDecimal dealSupplierId;
	private CurrencyMaster exCurrencyMaster;
	private BankMaster exBankMaster;
	private CompanyMaster fsCompanyMaster;
	private Deal exDeal;
	private SupplierMaster exSupplierMaster;
	private CountryMaster fsCountryMaster;
	private Short documentFinYear;
	private Short documentCode;
	private String isactive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;

	public DealSupplier() {
	}

	public DealSupplier(BigDecimal dealSupplierId) {
		this.dealSupplierId = dealSupplierId;
	}

	public DealSupplier(BigDecimal dealSupplierId,
			CurrencyMaster exCurrencyMaster, BankMaster exBankMaster,
			CompanyMaster fsCompanyMaster, Deal exDeal,
			SupplierMaster exSupplierMaster, CountryMaster fsCountryMaster,
			Short documentFinYear, Short documentCode, String isactive,
			String createdBy, Date createdDate, String modifiedBy,
			Date modifiedDate) {
		this.dealSupplierId = dealSupplierId;
		this.exCurrencyMaster = exCurrencyMaster;
		this.exBankMaster = exBankMaster;
		this.fsCompanyMaster = fsCompanyMaster;
		this.exDeal = exDeal;
		this.exSupplierMaster = exSupplierMaster;
		this.fsCountryMaster = fsCountryMaster;
		this.documentFinYear = documentFinYear;
		this.documentCode = documentCode;
		this.isactive = isactive;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}
		
	@Id
	@GeneratedValue(generator="ex_deal_supplier_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_deal_supplier_seq",sequenceName="EX_DEAL_SUPPLIER_SEQ",allocationSize=1)
	@Column(name = "DEAL_SUPPLIER_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getDealSupplierId() {
		return dealSupplierId;
	}
	
	public void setDealSupplierId(BigDecimal dealSupplierId) {
		this.dealSupplierId = dealSupplierId;
	}
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CURRENCY_ID")
	public CurrencyMaster getExCurrencyMaster() {
		return this.exCurrencyMaster;
	}


	public void setExCurrencyMaster(CurrencyMaster exCurrencyMaster) {
		this.exCurrencyMaster = exCurrencyMaster;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_ID")
	public BankMaster getExBankMaster() {
		return this.exBankMaster;
	}

	public void setExBankMaster(BankMaster exBankMaster) {
		this.exBankMaster = exBankMaster;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPANY_ID")
	public CompanyMaster getFsCompanyMaster() {
		return this.fsCompanyMaster;
	}

	public void setFsCompanyMaster(CompanyMaster fsCompanyMaster) {
		this.fsCompanyMaster = fsCompanyMaster;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DEAL_ID")
	public Deal getExDeal() {
		return this.exDeal;
	}

	public void setExDeal(Deal exDeal) {
		this.exDeal = exDeal;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SUPPLIER_MASTER_ID")
	public SupplierMaster getExSupplierMaster() {
		return this.exSupplierMaster;
	}

	public void setExSupplierMaster(SupplierMaster exSupplierMaster) {
		this.exSupplierMaster = exSupplierMaster;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_ID")
	public CountryMaster getFsCountryMaster() {
		return this.fsCountryMaster;
	}

	public void setFsCountryMaster(CountryMaster fsCountryMaster) {
		this.fsCountryMaster = fsCountryMaster;
	}

	@Column(name = "DOCUMENT_FIN_YEAR", precision = 4, scale = 0)
	public Short getDocumentFinYear() {
		return this.documentFinYear;
	}

	public void setDocumentFinYear(Short documentFinYear) {
		this.documentFinYear = documentFinYear;
	}

	@Column(name = "DOCUMENT_CODE", precision = 4, scale = 0)
	public Short getDocumentCode() {
		return this.documentCode;
	}

	public void setDocumentCode(Short documentCode) {
		this.documentCode = documentCode;
	}

	@Column(name = "ISACTIVE", length = 1)
	public String getIsactive() {
		return this.isactive;
	}

	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}

	@Column(name = "CREATED_BY", length = 15)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "CREATED_DATE")
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "MODIFIED_BY", length = 15)
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "MODIFIED_DATE")
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

}
