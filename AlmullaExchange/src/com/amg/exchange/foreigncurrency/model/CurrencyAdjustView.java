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

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.treasury.model.CurrencyMaster;
@Entity
@Table(name="VW_EX_CURRENCY_ADJUST")
public class CurrencyAdjustView implements Serializable {

 
	private static final long serialVersionUID = 1L;
 
 
	private BigDecimal foreignCurrencyAdjustId;
	private BigDecimal fsCustomer;
	private BigDecimal fsCountryMaster;
	private BigDecimal fsCompanyMaster;
	private BigDecimal countryBranch;// Nedd to check with tables
	private BigDecimal fsCurrencyMaster;
	private BigDecimal fsDenominationId; // need to check
	private String oracleUser;
	private BigDecimal documentCode;
	private BigDecimal documentFinanceYear;
	private BigDecimal documentNo;
	private BigDecimal documentLineNumber;
	private Date documentDate;
	private Date accountmmyyyy;
	private BigDecimal adjustmentAmount;
	private BigDecimal notesQuantity;
	private BigDecimal exchangeRate;
	private BigDecimal denaminationAmount;
	private String progNumber;
	private String documentStatus;
	private String stockUpdated;
	private String transactionType;
	private Date approvalDate;
	private String approvalBy;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private BigDecimal collect;
	private BigDecimal documentId;


	/**
	 * @param foreignCurrencyAdjustId
	 * @param fsCustomer
	 * @param fsCountryMaster
	 * @param fsCompanyMaster
	 * @param countryBranch
	 * @param fsCurrencyMaster
	 * @param fsDenominationId
	 * @param oracleUser
	 * @param documentCode
	 * @param documentFinanceYear
	 * @param documentNo
	 * @param documentLineNumber
	 * @param documentDate
	 * @param accountmmyyyy
	 * @param adjustmentAmount
	 * @param notesQuantity
	 * @param exchangeRate
	 * @param denaminationAmount
	 * @param progNumber
	 * @param documentStatus
	 * @param stockUpdated
	 * @param transactionType
	 * @param approvalDate
	 * @param approvalBy
	 * @param createdBy
	 * @param createdDate
	 * @param modifiedBy
	 * @param modifiedDate
	 * @param collect
	 */
	
	public CurrencyAdjustView(BigDecimal foreignCurrencyAdjustId,
			BigDecimal fsCustomer, BigDecimal fsCountryMaster,
			BigDecimal fsCompanyMaster, BigDecimal countryBranch,
			BigDecimal fsCurrencyMaster,
			BigDecimal fsDenominationId, String oracleUser,
			BigDecimal documentCode, BigDecimal documentFinanceYear, BigDecimal documentNo,
			BigDecimal documentLineNumber, Date documentDate, Date accountmmyyyy,
			BigDecimal adjustmentAmount, BigDecimal notesQuantity, BigDecimal exchangeRate,
			BigDecimal denaminationAmount, String progNumber,
			String documentStatus, String stockUpdated, String transactionType,
			Date approvalDate, String approvalBy, String createdBy,
			Date createdDate, String modifiedBy, Date modifiedDate,
			BigDecimal collect) {

		this.foreignCurrencyAdjustId = foreignCurrencyAdjustId;
		this.fsCustomer = fsCustomer;
		this.fsCountryMaster = fsCountryMaster;
		this.fsCompanyMaster = fsCompanyMaster;
		this.countryBranch = countryBranch;
		this.fsCurrencyMaster = fsCurrencyMaster;
		this.fsDenominationId = fsDenominationId;
		this.oracleUser = oracleUser;
		this.documentCode = documentCode;
		this.documentFinanceYear = documentFinanceYear;
		this.documentNo = documentNo;
		this.documentLineNumber = documentLineNumber;
		this.documentDate = documentDate;
		this.accountmmyyyy = accountmmyyyy;
		this.adjustmentAmount = adjustmentAmount;
		this.notesQuantity = notesQuantity;
		this.exchangeRate = exchangeRate;
		this.denaminationAmount = denaminationAmount;
		this.progNumber = progNumber;
		this.documentStatus = documentStatus;
		this.stockUpdated = stockUpdated;
		this.transactionType = transactionType;
		this.approvalDate = approvalDate;
		this.approvalBy = approvalBy;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.collect = collect;
	}

	public CurrencyAdjustView() {

	}

 
	 
	@Id
	@Column(name="FOREIGN_CURRENCY_ADJUST_ID")
 	public BigDecimal getForeignCurrencyAdjustId() {
		return foreignCurrencyAdjustId;
	}
	public void setForeignCurrencyAdjustId(BigDecimal foreignCurrencyAdjustId) {
		this.foreignCurrencyAdjustId = foreignCurrencyAdjustId;
	}

 
	@Column(name = "CUSTOMER_ID")
	public BigDecimal getFsCustomer() {
		return fsCustomer;
	}
	public void setFsCustomer(BigDecimal fsCustomer) {
		this.fsCustomer = fsCustomer;
	}
 
	@Column(name = "COUNTRY_ID")
	public BigDecimal getFsCountryMaster() {
		return fsCountryMaster;
	}
	public void setFsCountryMaster(BigDecimal fsCountryMaster) {
		this.fsCountryMaster = fsCountryMaster;
	}

 
	@Column(name = "COMPANY_ID")
	public BigDecimal getFsCompanyMaster() {
		return fsCompanyMaster;
	}
	public void setFsCompanyMaster(BigDecimal fsCompanyMaster) {
		this.fsCompanyMaster = fsCompanyMaster;
	}

 
 
	@Column(name = "CURRENCY_ID")
	public BigDecimal getFsCurrencyMaster() {
		return fsCurrencyMaster;
	}
	public void setFsCurrencyMaster(BigDecimal fsCurrencyMaster) {
		this.fsCurrencyMaster = fsCurrencyMaster;
	}

 
	@Column(name = "DENOMINATION_ID")
	public BigDecimal getFsDenominationId() {
		return fsDenominationId;
	}
	public void setFsDenominationId(BigDecimal fsDenominationId) {
		this.fsDenominationId = fsDenominationId;
	}

	@Column(name = "ORACLE_USER")
	public String getOracleUser() {
		return oracleUser;
	}
	public void setOracleUser(String oracleUser) {
		this.oracleUser = oracleUser;
	}

	@Column(name = "DOCUMENT_CODE")
	public BigDecimal getDocumentCode() {
		return documentCode;
	}
	public void setDocumentCode(BigDecimal documentCode) {
		this.documentCode = documentCode;
	}

	@Column(name = "DOCUMENT_FINANCE_YEAR")
	public BigDecimal getDocumentFinanceYear() {
		return documentFinanceYear;
	}
	public void setDocumentFinanceYear(BigDecimal documentFinanceYear) {
		this.documentFinanceYear = documentFinanceYear;
	}

	@Column(name = "DOCUMENT_NO")
	public BigDecimal getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}

	@Column(name = "DOCUMENT_LINE_NO")
	public BigDecimal getDocumentLineNumber() {
		return documentLineNumber;
	}
	public void setDocumentLineNumber(BigDecimal documentLineNumber) {
		this.documentLineNumber = documentLineNumber;
	}

	@Column(name = "DOCUMENT_DATE")
	public Date getDocumentDate() {
		return documentDate;
	}
	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}

	@Column(name = "ACCOUNT_MMYYYY")
	public Date getAccountmmyyyy() {
		return accountmmyyyy;
	}
	public void setAccountmmyyyy(Date accountmmyyyy) {
		this.accountmmyyyy = accountmmyyyy;
	}

	@Column(name = "ADJUSTMENT_AMOUNT")
	public BigDecimal getAdjustmentAmount() {
		return adjustmentAmount;
	}
	public void setAdjustmentAmount(BigDecimal adjustmentAmount) {
		this.adjustmentAmount = adjustmentAmount;
	}

	@Column(name = "NOTES_QTY")
	public BigDecimal getNotesQuantity() {
		return notesQuantity;
	}
	public void setNotesQuantity(BigDecimal notesQuantity) {
		this.notesQuantity = notesQuantity;
	}

	@Column(name = "EXCHANGE_RATE")
	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	@Column(name = "DENOMINATION_AMOUNT")
	public BigDecimal getDenaminationAmount() {
		return denaminationAmount;
	}
	public void setDenaminationAmount(BigDecimal denaminationAmount) {
		this.denaminationAmount = denaminationAmount;
	}


	@Column(name = "PROGNO")
	public String getProgNumber() {
		return progNumber;
	}
	public void setProgNumber(String progNumber) {
		this.progNumber = progNumber;
	}

	@Column(name = "DOCUMENT_STATUS")
	public String getDocumentStatus() {
		return documentStatus;
	}
	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}

	@Column(name = "STOCK_UPDATED")
	public String getStockUpdated() {
		return stockUpdated;
	}
	public void setStockUpdated(String stockUpdated) {
		this.stockUpdated = stockUpdated;
	}

	@Column(name = "TRANSACTION_TYPE")
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	@Column(name = "APPROVAL_DATE")
	public Date getApprovalDate() {
		return approvalDate;
	}
	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	@Column(name = "APPROVAL_BY")
	public String getApprovalBy() {
		return approvalBy;
	}
	public void setApprovalBy(String approvalBy) {
		this.approvalBy = approvalBy;
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

 
	@Column(name="CASH_COLLECTION_ID")
	public BigDecimal getCollect() {
		return collect;
	}
	public void setCollect(BigDecimal collect) {
		this.collect = collect;
	}

 
	@Column(name="COUNTRY_BRANCH_ID")
	public BigDecimal getCountryBranch() {
		return countryBranch;
	}
	public void setCountryBranch(BigDecimal countryBranch) {
		this.countryBranch = countryBranch;
	}
   
	@Column(name="DOCUMENT_ID")
	public BigDecimal getDocumentId() {
		return documentId;
	}

	public void setDocumentId(BigDecimal documentId) {
		this.documentId = documentId;
	}


}
