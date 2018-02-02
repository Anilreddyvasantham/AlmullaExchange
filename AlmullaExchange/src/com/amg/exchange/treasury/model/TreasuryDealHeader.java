package com.amg.exchange.treasury.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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
import javax.persistence.TableGenerator;

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.LanguageType;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;

/*******************************************************************************************************************

File		: DealHeader.java

Project	: AlmullaExchange

Package	: com.amg.exchange.registration.model

Created	:	
				Date	: 13-Nov-2014 
				By		: Nazish Ehsan Hashmi
				Revision:

Last Change:
				Date	: 14-Nov-2014 
				By		: Nazish Ehsan Hashmi
				Revision:

Description:
********************************************************************************************************************/

	@Entity
	@Table(name = "EX_TREASURY_DEAL_HEADER")
	public class TreasuryDealHeader implements java.io.Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private BigDecimal treasuryDealHeaderId;
		private CountryMaster fsCountryMaster;
		private CompanyMaster fsCompanyMaster;
		private BigDecimal userFinanceYear;
		private Document exDocument;
		private BigDecimal treasuryDocumentNumber;
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
		private Date valueDate;
		private String attention;
		private BigDecimal balanceAmount;
		private String isActive;
		private BigDecimal eftAmount;
		private BigDecimal ttAmount;
		private BigDecimal cashAmount;
		private BigDecimal eftToAmount;
		

		private BigDecimal ttToAmount;
		private BigDecimal cashToAmount;

		
		private BigDecimal countryBranchId;
		private String reutersIndicator;
		
		private List<TreasuryDealDetail> exDealDetail = new ArrayList<TreasuryDealDetail>();
		
		public TreasuryDealHeader() {
			
		}
		
		
	public TreasuryDealHeader(BigDecimal treasuryDealHeaderId) {
		this.treasuryDealHeaderId = treasuryDealHeaderId;
			
		}


	public TreasuryDealHeader(BigDecimal treasuryDealHeaderId, CountryMaster fsCountryMaster,
			CompanyMaster fsCompanyMaster,BigDecimal userFinanceYear,
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
			String approvedBy, Date approvedDate,
			List<TreasuryDealDetail> exDealDetail,
			BigDecimal treasuryDocumentNumber,
			Date valueDate,String attention,
			BigDecimal balanceAmount,String isActive,BigDecimal countryBranchId,String reutersIndicator) {
		super();
		this.treasuryDealHeaderId = treasuryDealHeaderId;
		this.fsCountryMaster = fsCountryMaster;
		this.fsCompanyMaster = fsCompanyMaster;
		this.userFinanceYear = userFinanceYear;
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
		this.exDealDetail=exDealDetail;
		this.treasuryDocumentNumber =treasuryDocumentNumber;
		this.valueDate  =valueDate;
		this.attention =attention;
		this.balanceAmount= balanceAmount;
		this.isActive=isActive;
		this.countryBranchId=countryBranchId;
		this.reutersIndicator=reutersIndicator;
	} 
	
	@Id
	@GeneratedValue(generator="ex_treasury_deal_header_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_treasury_deal_header_seq",sequenceName="EX_TREASURY_DEAL_HEADER_SEQ",allocationSize=1)
	@Column(name = "TREASURY_DEAL_HEADER_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getTreasuryDealHeaderId() {
		return treasuryDealHeaderId;
	}


	public void setTreasuryDealHeaderId(BigDecimal treasuryDealHeaderId) {
		this.treasuryDealHeaderId = treasuryDealHeaderId;
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

	@Column(name = "DOCUMENT_FINANCE_YEAR")
	public BigDecimal getUserFinanceYear() {
		return userFinanceYear;
	}


	public void setUserFinanceYear(BigDecimal userFinanceYear) {
		this.userFinanceYear = userFinanceYear;
	}


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DOCUMENT_ID")
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "treasuryDealHeader")
	public List<TreasuryDealDetail> getExDealDetail() {
		return exDealDetail;
	}


	public void setExDealDetail(List<TreasuryDealDetail> exDealDetail) {
		this.exDealDetail = exDealDetail;
	}

	
	@Column(name = "DOCUMENT_NUMBER", length = 15)
	public BigDecimal getTreasuryDocumentNumber() {
		return treasuryDocumentNumber;
	}


	public void setTreasuryDocumentNumber(BigDecimal treasuryDocumentNumber) {
		this.treasuryDocumentNumber = treasuryDocumentNumber;
	}

	@Column(name = "VALUE_DATE")
	public Date getValueDate() {
		return valueDate;
	}


	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}

	@Column(name = "ATTENTION" ,length = 15)
	public String getAttention() {
		return attention;
	}


	public void setAttention(String attention) {
		this.attention = attention;
	}
	
	@Column(name = "BALANCE_AMOUNT" )
	public BigDecimal getBalanceAmount() {
		return balanceAmount;
	}


	public void setBalanceAmount(BigDecimal balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	@Column(name="ISACTIVE")
	public String getIsActive() {
		return isActive;
	}


	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Column(name="EFT_AMOUNT")
	public BigDecimal getEftAmount() {
		return eftAmount;
	}


	public void setEftAmount(BigDecimal eftAmount) {
		this.eftAmount = eftAmount;
	}

	@Column(name="TT_AMOUNT")
	public BigDecimal getTtAmount() {
		return ttAmount;
	}


	public void setTtAmount(BigDecimal ttAmount) {
		this.ttAmount = ttAmount;
	}

	@Column(name="CASH_AMOUNT")
	public BigDecimal getCashAmount() {
		return cashAmount;
	}


	public void setCashAmount(BigDecimal cashAmount) {
		this.cashAmount = cashAmount;
	}

	@Column(name="COUNTRY_BRANCH_ID")
	public BigDecimal getCountryBranchId() {
		return countryBranchId;
	}


	public void setCountryBranchId(BigDecimal countryBranchId) {
		this.countryBranchId = countryBranchId;
	}

	@Column(name="REUTERS_INDICATOR")
	public String getReutersIndicator() {
		return reutersIndicator;
	}


	public void setReutersIndicator(String reutersIndicator) {
		this.reutersIndicator = reutersIndicator;
	}
	@Column(name="EFT_TO_AMOUNT")
	public BigDecimal getEftToAmount() {
		return eftToAmount;
	}


	public void setEftToAmount(BigDecimal eftToAmount) {
		this.eftToAmount = eftToAmount;
	}

	@Column(name="TT_TO_AMOUNT")
	public BigDecimal getTtToAmount() {
		return ttToAmount;
	}


	public void setTtToAmount(BigDecimal ttToAmount) {
		this.ttToAmount = ttToAmount;
	}

	@Column(name="CASH_TO_AMOUNT")
	public BigDecimal getCashToAmount() {
		return cashToAmount;
	}


	public void setCashToAmount(BigDecimal cashToAmount) {
		this.cashToAmount = cashToAmount;
	}

	
	
		
}

