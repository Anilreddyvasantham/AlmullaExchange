package com.amg.exchange.treasury.model;

// default package
// Generated Jul 10, 2014 5:34:26  Created by Chennai ODC

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.registration.model.Customer;

/**
 * ExSpecialDeal Created by Chennai ODC
 */
@Entity
@Table(name = "EX_SPECIAL_DEAL")
public class SpecialDeal implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -767752069292146121L;
	private BigDecimal specialDealId;
	private CurrencyMaster exCurrencyMaster;
	private BankMaster exBankMaster;
	private CompanyMaster fsCompanyMaster;
	private Customer fsCustomer;
	private CountryMaster fsCountryMaster;
	private BigDecimal splDocId;
	private Short splYear;
	private BigDecimal splRef;
	private Date splDealReqDate;
	private Date splExpiryDate;
	private BigDecimal amount;
	private BigDecimal exchangeRate;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private Set<DealPurchase> exDealPurchases = new HashSet<DealPurchase>(0);
	private Set<SpecialDealUtiliesed> exSpecialDealUtilieseds = new HashSet<SpecialDealUtiliesed>(
			0);

	public SpecialDeal() {
	}

	public SpecialDeal(BigDecimal specialDealId) {
		this.specialDealId = specialDealId;
	}

	public SpecialDeal(BigDecimal specialDealId,
			CurrencyMaster exCurrencyMaster, BankMaster exBankMaster,
			CompanyMaster fsCompanyMaster, Customer fsCustomer,
			CountryMaster fsCountryMaster, BigDecimal splDocId, Short splYear,
			BigDecimal splRef, Date splDealReqDate, Date splExpiryDate,
			BigDecimal amount, BigDecimal exchangeRate, String createdBy,
			Date createdDate, String modifiedBy, Date modifiedDate,
			Set<DealPurchase> exDealPurchases,
			Set<SpecialDealUtiliesed> exSpecialDealUtilieseds) {
		this.specialDealId = specialDealId;
		this.exCurrencyMaster = exCurrencyMaster;
		this.exBankMaster = exBankMaster;
		this.fsCompanyMaster = fsCompanyMaster;
		this.fsCustomer = fsCustomer;
		this.fsCountryMaster = fsCountryMaster;
		this.splDocId = splDocId;
		this.splYear = splYear;
		this.splRef = splRef;
		this.splDealReqDate = splDealReqDate;
		this.splExpiryDate = splExpiryDate;
		this.amount = amount;
		this.exchangeRate = exchangeRate;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.exDealPurchases = exDealPurchases;
		this.exSpecialDealUtilieseds = exSpecialDealUtilieseds;
	}

	/*@Id
	@TableGenerator(name="specialdealid",table="ex_specialdealidpk",pkColumnName="specialdealidkey",pkColumnValue="specialdealidvalue",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.TABLE,generator="specialdealid")
	@Column(name = "SPECIAL_DEAL_ID", unique = true, nullable = false, precision = 22, scale = 0)
	*/
	
	//Added by kani begin
	@Id
	@GeneratedValue(generator="ex_special_deal_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_special_deal_seq",sequenceName="EX_SPECIAL_DEAL_SEQ",allocationSize=1)
	@Column(name = "SPECIAL_DEAL_ID", unique = true, nullable = false, precision = 22, scale = 0)
	
	//Added by kani end
	
	public BigDecimal getSpecialDealId() {
		return this.specialDealId;
	}

	public void setSpecialDealId(BigDecimal specialDealId) {
		this.specialDealId = specialDealId;
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
	@JoinColumn(name = "CUSTOMER_ID")
	public Customer getFsCustomer() {
		return this.fsCustomer;
	}

	public void setFsCustomer(Customer fsCustomer) {
		this.fsCustomer = fsCustomer;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_ID")
	public CountryMaster getFsCountryMaster() {
		return this.fsCountryMaster;
	}

	public void setFsCountryMaster(CountryMaster fsCountryMaster) {
		this.fsCountryMaster = fsCountryMaster;
	}

	@Column(name = "SPL_DOC_ID", precision = 22, scale = 0)
	public BigDecimal getSplDocId() {
		return this.splDocId;
	}

	public void setSplDocId(BigDecimal splDocId) {
		this.splDocId = splDocId;
	}

	@Column(name = "SPL_YEAR", precision = 4, scale = 0)
	public Short getSplYear() {
		return this.splYear;
	}

	public void setSplYear(Short splYear) {
		this.splYear = splYear;
	}

	@Column(name = "SPL_REF", precision = 22, scale = 0)
	public BigDecimal getSplRef() {
		return this.splRef;
	}

	public void setSplRef(BigDecimal splRef) {
		this.splRef = splRef;
	}

	@Column(name = "SPL_DEAL_REQ_DATE")
	public Date getSplDealReqDate() {
		return this.splDealReqDate;
	}

	public void setSplDealReqDate(Date splDealReqDate) {
		this.splDealReqDate = splDealReqDate;
	}

	@Column(name = "SPL_EXPIRY_DATE")
	public Date getSplExpiryDate() {
		return this.splExpiryDate;
	}

	public void setSplExpiryDate(Date splExpiryDate) {
		this.splExpiryDate = splExpiryDate;
	}

	@Column(name = "AMOUNT", precision = 22, scale = 3)
	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Column(name = "EXCHANGE_RATE", precision = 10, scale = 6)
	public BigDecimal getExchangeRate() {
		return this.exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exSpecialDeal")
	public Set<DealPurchase> getExDealPurchases() {
		return this.exDealPurchases;
	}

	public void setExDealPurchases(Set<DealPurchase> exDealPurchases) {
		this.exDealPurchases = exDealPurchases;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exSpecialDeal")
	public Set<SpecialDealUtiliesed> getExSpecialDealUtilieseds() {
		return this.exSpecialDealUtilieseds;
	}

	public void setExSpecialDealUtilieseds(
			Set<SpecialDealUtiliesed> exSpecialDealUtilieseds) {
		this.exSpecialDealUtilieseds = exSpecialDealUtilieseds;
	}

}
