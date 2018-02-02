/*package com.amg.exchange.treasury.model;

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
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.LanguageType;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;

*//*******************************************************************************************************************

File		: DealHeader.java

Project	: AlmullaExchange

Package	: com.amg.exchange.registration.model

Created	:	
				Date	: 13-Nov-2014 
				By		: Nazish Ehsan Hashmi
				Revision:

Last Change:
				Date	: 13-Nov-2014 
				By		: Nazish Ehsan Hashmi
				Revision:

Description:
********************************************************************************************************************//*

	@Entity
	@Table(name = "EX_DEAL_HEADER")
	public class DealHeader implements java.io.Serializable {

		*//**
		 * 
		 *//*
		private static final long serialVersionUID = 1L;
		private BigDecimal dealHeaderId;
		private CountryMaster fsCountryMaster;
		private CompanyMaster fsCompanyMaster;
		private UserFinancialYear exUserFinanceYear;
		private Document exDocument;
		private BankMaster exBankMaster;
		private LanguageType fsLanguageType;
		
		private Date documentDate;
		private Date accyymm;
		private String contactName;
		private String concludedBy;
		private BigDecimal dealWithCustomer;
		private String dealWithType;
		private String reutersReference;
		private String remarks;
		private String multiplicationDivision;
		private BigDecimal purchaseExchangeRate;
		private BigDecimal totalPurchaseFCAmt;
		private BigDecimal purchaseLocalRate;
		private BigDecimal totalPurchaseLocalAmt;
		private BigDecimal saleAmount;
		private BigDecimal fileNo;
		private Date fileDate;
		private BigDecimal cancelCompanyId;
		private BigDecimal cancelDealId;
		private BigDecimal cancelFinYear;
		private BigDecimal cancelNumber;
		private BigDecimal paymentVoucherCompanyId;
		private BigDecimal paymentVoucherId;
		private BigDecimal paymentVoucherFinanceyYear;
		private BigDecimal paymentVoucherNumber;
		private String createdBy;
		private Date createdDate;
		private String modifiedBy;
		private Date modifiedDate;
		private String approvedBy;
		private Date approvedDate;
		
		public DealHeader() {
			
		}
		
		
	public DealHeader(BigDecimal dealHeaderId) {
		this.dealHeaderId = dealHeaderId;
			
		}


	public DealHeader(BigDecimal dealHeaderId, CountryMaster fsCountryMaster,
			CompanyMaster fsCompanyMaster, UserFinancialYear exUserFinanceYear,
			Document exDocument, BankMaster exBankMaster, LanguageType fsLanguageType,
			Date documentDate, Date accyymm, String contactName,
			String concludedBy, BigDecimal dealWithCustomer,
			String dealWithType, String reutersReference, String remarks,
			String multiplicationDivision, BigDecimal purchaseExchangeRate,
			BigDecimal totalPurchaseFCAmt, BigDecimal purchaseLocalRate,
			BigDecimal totalPurchaseLocalAmt, BigDecimal saleAmount,
			BigDecimal fileNo, Date fileDate, BigDecimal cancelCompanyId,
			BigDecimal cancelDealId, BigDecimal cancelFinYear,
			BigDecimal cancelNumber, BigDecimal paymentVoucherCompanyId,
			BigDecimal paymentVoucherId, BigDecimal paymentVoucherFinanceyYear,
			BigDecimal paymentVoucherNumber, String createdBy,
			Date createdDate, String modifiedBy, Date modifiedDate,
			String approvedBy, Date approvedDate) {
		super();
		this.dealHeaderId = dealHeaderId;
		this.fsCountryMaster = fsCountryMaster;
		this.fsCompanyMaster = fsCompanyMaster;
		this.exUserFinanceYear = exUserFinanceYear;
		this.exDocument = exDocument;
		this.exBankMaster = exBankMaster;
		this.fsLanguageType = fsLanguageType;
		this.documentDate = documentDate;
		this.accyymm = accyymm;
		this.contactName = contactName;
		this.concludedBy = concludedBy;
		this.dealWithCustomer = dealWithCustomer;
		this.dealWithType = dealWithType;
		this.reutersReference = reutersReference;
		this.remarks = remarks;
		this.multiplicationDivision = multiplicationDivision;
		this.purchaseExchangeRate = purchaseExchangeRate;
		this.totalPurchaseFCAmt = totalPurchaseFCAmt;
		this.purchaseLocalRate = purchaseLocalRate;
		this.totalPurchaseLocalAmt = totalPurchaseLocalAmt;
		this.saleAmount = saleAmount;
		this.fileNo = fileNo;
		this.fileDate = fileDate;
		this.cancelCompanyId = cancelCompanyId;
		this.cancelDealId = cancelDealId;
		this.cancelFinYear = cancelFinYear;
		this.cancelNumber = cancelNumber;
		this.paymentVoucherCompanyId = paymentVoucherCompanyId;
		this.paymentVoucherId = paymentVoucherId;
		this.paymentVoucherFinanceyYear = paymentVoucherFinanceyYear;
		this.paymentVoucherNumber = paymentVoucherNumber;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
	}

	@Id
	@TableGenerator(name = "dealheaderid", table = "ex_dealheaderidpk", pkColumnName = "dealheaderidkey", pkColumnValue = "dealheaderidvalue", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "dealheaderid")
	@Column(name = "DEAL_HEADER_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getDealHeaderId() {
		return dealHeaderId;
	}


	public void setDealHeaderId(BigDecimal dealHeaderId) {
		this.dealHeaderId = dealHeaderId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "APPLICATION_COUNTRY_ID")
	public CountryMaster getFsCountryMaster() {
		return fsCountryMaster;
	}
	
	public void setFsCountryMaster(CountryMaster fsCountryMaster) {
		this.fsCountryMaster = fsCountryMaster;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPANY_ID")
	public CompanyMaster getFsCompanyMaster() {
		return fsCompanyMaster;
	}


	public void setFsCompanyMaster(CompanyMaster fsCompanyMaster) {
		this.fsCompanyMaster = fsCompanyMaster;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DOCUMENT_FINANCE_YEAR")
	public UserFinancialYear getExUserFinanceYear() {
		return exUserFinanceYear;
	}


	public void setExUserFinanceYear(UserFinancialYear exUserFinanceYear) {
		this.exUserFinanceYear = exUserFinanceYear;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DOCUMENT_NUMBER")
	public Document getExDocument() {
		return exDocument;
	}


	public void setExDocument(Document exDocument) {
		this.exDocument = exDocument;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DEAL_WITH_BANK_ID")
	public BankMaster getExBankMaster() {
		return exBankMaster;
	}


	public void setExBankMaster(BankMaster exBankMaster) {
		this.exBankMaster = exBankMaster;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LANGUAGE_ID")
	public LanguageType getFsLanguageType() {
		return fsLanguageType;
	}


	public void setFsLanguageType(LanguageType fsLanguageType) {
		this.fsLanguageType = fsLanguageType;
	}

	@Column(name = "DOCUMENT_DATE")
	public Date getDocumentDate() {
		return documentDate;
	}


	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}

	@Column(name = "ACYYMM")
	public Date getAccyymm() {
		return accyymm;
	}


	public void setAccyymm(Date accyymm) {
		this.accyymm = accyymm;
	}

	@Column(name = "CONTACT_NAME", length = 20)
	public String getContactName() {
		return contactName;
	}


	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	@Column(name = "CONCLUDED_BY", length = 20)
	public String getConcludedBy() {
		return concludedBy;
	}


	public void setConcludedBy(String concludedBy) {
		this.concludedBy = concludedBy;
	}

	@Column(name = "DEAL_WITH_CUSTOMER", precision = 10, scale = 6)
	public BigDecimal getDealWithCustomer() {
		return dealWithCustomer;
	}


	public void setDealWithCustomer(BigDecimal dealWithCustomer) {
		this.dealWithCustomer = dealWithCustomer;
	}

	@Column(name = "DEAL_WITH_TYPE", length = 1)
	public String getDealWithType() {
		return dealWithType;
	}


	public void setDealWithType(String dealWithType) {
		this.dealWithType = dealWithType;
	}

	@Column(name = "REUTERS_REFERECE", length = 1)
	public String getReutersReference() {
		return reutersReference;
	}


	public void setReutersReference(String reutersReference) {
		this.reutersReference = reutersReference;
	}

	@Column(name = "REMARKS", length = 100)
	public String getRemarks() {
		return remarks;
	}


	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name = "MULTIPLICATION_DIVISION", length = 1)
	public String getMultiplicationDivision() {
		return multiplicationDivision;
	}


	public void setMultiplicationDivision(String multiplicationDivision) {
		this.multiplicationDivision = multiplicationDivision;
	}

	@Column(name = "PURCHASE_EXCHANGE_RATE", precision = 10, scale = 6)
	public BigDecimal getPurchaseExchangeRate() {
		return purchaseExchangeRate;
	}


	public void setPurchaseExchangeRate(BigDecimal purchaseExchangeRate) {
		this.purchaseExchangeRate = purchaseExchangeRate;
	}

	@Column(name = "TOTAL_PURCHASE_FC_AMOUNT", precision = 10, scale = 6)
	public BigDecimal getTotalPurchaseFCAmt() {
		return totalPurchaseFCAmt;
	}

	public void setTotalPurchaseFCAmt(BigDecimal totalPurchaseFCAmt) {
		this.totalPurchaseFCAmt = totalPurchaseFCAmt;
	}

	@Column(name = "PURCHASE_LOCAL_RATE", precision = 10, scale = 6)
	public BigDecimal getPurchaseLocalRate() {
		return purchaseLocalRate;
	}


	public void setPurchaseLocalRate(BigDecimal purchaseLocalRate) {
		this.purchaseLocalRate = purchaseLocalRate;
	}

	@Column(name = "TOTAL_PURCHASE_LOCAL_AMOUNT", precision = 10, scale = 6)
	public BigDecimal getTotalPurchaseLocalAmt() {
		return totalPurchaseLocalAmt;
	}


	public void setTotalPurchaseLocalAmt(BigDecimal totalPurchaseLocalAmt) {
		this.totalPurchaseLocalAmt = totalPurchaseLocalAmt;
	}

	@Column(name = "SALE_AMOUNT", precision = 10, scale = 6)
	public BigDecimal getSaleAmount() {
		return saleAmount;
	}


	public void setSaleAmount(BigDecimal saleAmount) {
		this.saleAmount = saleAmount;
	}

	@Column(name = "FILE_NO", precision = 10, scale = 6)
	public BigDecimal getFileNo() {
		return fileNo;
	}


	public void setFileNo(BigDecimal fileNo) {
		this.fileNo = fileNo;
	}

	@Column(name = "FILE_DATE")
	public Date getFileDate() {
		return fileDate;
	}


	public void setFileDate(Date fileDate) {
		this.fileDate = fileDate;
	}

	@Column(name = "CANCEL_COMPANY_ID", precision = 10, scale = 6)
	public BigDecimal getCancelCompanyId() {
		return cancelCompanyId;
	}


	public void setCancelCompanyId(BigDecimal cancelCompanyId) {
		this.cancelCompanyId = cancelCompanyId;
	}

	@Column(name = "CANCEL_DEAL_ID", precision = 10, scale = 6)
	public BigDecimal getCancelDealId() {
		return cancelDealId;
	}


	public void setCancelDealId(BigDecimal cancelDealId) {
		this.cancelDealId = cancelDealId;
	}

	@Column(name = "CANCEL_FINANCE_YEAR", precision = 10, scale = 6)
	public BigDecimal getCancelFinYear() {
		return cancelFinYear;
	}


	public void setCancelFinYear(BigDecimal cancelFinYear) {
		this.cancelFinYear = cancelFinYear;
	}

	@Column(name = "CANCEL_NUMBER", precision = 10, scale = 6)
	public BigDecimal getCancelNumber() {
		return cancelNumber;
	}


	public void setCancelNumber(BigDecimal cancelNumber) {
		this.cancelNumber = cancelNumber;
	}

	@Column(name = "PAYMENT_VOUCHER_COMPANY_ID", precision = 10, scale = 6)
	public BigDecimal getPaymentVoucherCompanyId() {
		return paymentVoucherCompanyId;
	}


	public void setPaymentVoucherCompanyId(BigDecimal paymentVoucherCompanyId) {
		this.paymentVoucherCompanyId = paymentVoucherCompanyId;
	}

	@Column(name = "PAYMENT_VOUCHER_ID", precision = 10, scale = 6)
	public BigDecimal getPaymentVoucherId() {
		return paymentVoucherId;
	}


	public void setPaymentVoucherId(BigDecimal paymentVoucherId) {
		this.paymentVoucherId = paymentVoucherId;
	}

	@Column(name = "PAYMENT_VOUCHER_FINANCE_YEAR", precision = 10, scale = 6)
	public BigDecimal getPaymentVoucherFinanceyYear() {
		return paymentVoucherFinanceyYear;
	}


	public void setPaymentVoucherFinanceyYear(BigDecimal paymentVoucherFinanceyYear) {
		this.paymentVoucherFinanceyYear = paymentVoucherFinanceyYear;
	}

	@Column(name = "PAYMENT_VOUCHER_NUMBER", precision = 10, scale = 6)
	public BigDecimal getPaymentVoucherNumber() {
		return paymentVoucherNumber;
	}


	public void setPaymentVoucherNumber(BigDecimal paymentVoucherNumber) {
		this.paymentVoucherNumber = paymentVoucherNumber;
	}

	@Column(name = "CREATED_BY", length = 15)
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

	@Column(name = "MODIFIED_BY", length = 15)
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

	@Column(name = "APPROVED_BY", length = 15)
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
	
	
		
}

*/