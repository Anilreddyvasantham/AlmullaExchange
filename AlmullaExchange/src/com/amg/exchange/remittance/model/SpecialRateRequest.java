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
import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.Document;

/*******************************************************************************************************************
 * File : SpecialRateRequest.java
 * 
 * Project : AlmullaExchange
 * 
 * Package : com.amg.exchange.remittance.model
 * 
 * Created : Date : 30-Dec-2014 10:00:47 am By : Nagarjuna Atla Revision:
 * 
 * Last Change: Date : 13-Jan-2015 10:38:47 am By : Nazish Ehsan Hashmi Revision:
 * 
 * Description: TODO
 ********************************************************************************************************************/

@Entity
@Table(name = "EX_SPOT_SPECIAL_RATE")
public class SpecialRateRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private BigDecimal specialRateRequestId;
	private Customer fsCustomer;
	private BankMaster fsBankMaster;
	private CurrencyMaster fsCurrencyMaster;
	private BigDecimal fcAmount;
	private BigDecimal sellRate;
	private String isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private CompanyMaster companyMaster;
	private Document fsDocument;
	private UserFinancialYear fsFinanceYear;
	private BigDecimal documentNo;
	private BigDecimal applicationCountryId;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;
	private BigDecimal beneficiaryId;
	private BigDecimal beneficiaryBankId;
	private BigDecimal beneficiaryBankBranchId;
	private BigDecimal applicationCompanyId;
	private BigDecimal applicationDocumentId;
	private BigDecimal applicationFinanceYear;
	private BigDecimal applicationDocumentNo;
	private BigDecimal branchId;
	private BigDecimal countryBranchId;

	public SpecialRateRequest() {

	}

	public SpecialRateRequest(BigDecimal specialRateRequestId,
			Customer fsCustomer, BankMaster fsBankMaster,
			CurrencyMaster fsCurrencyMaster, BigDecimal fcAmount,
			BigDecimal sellRate, String isActive, String createdBy,
			Date createdDate, String modifiedBy, Date modifiedDate,
			CompanyMaster companyMaster, Document fsDocument,
			UserFinancialYear fsFinanceYear, BigDecimal documentNo,
			BigDecimal applicationCountryId, String approvedBy,
			Date approvedDate, String remarks, BigDecimal beneficiaryId,
			BigDecimal beneficiaryBankId, BigDecimal beneficiaryBankBranchId,
			BigDecimal applicationCompanyId, BigDecimal applicationDocumentId,
			BigDecimal applicationFinanceYear, BigDecimal applicationDocumentNo,BigDecimal branchId,BigDecimal countryBranchId) {
		super();
		this.specialRateRequestId = specialRateRequestId;
		this.fsCustomer = fsCustomer;
		this.fsBankMaster = fsBankMaster;
		this.fsCurrencyMaster = fsCurrencyMaster;
		this.fcAmount = fcAmount;
		this.sellRate = sellRate;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.companyMaster = companyMaster;
		this.fsDocument = fsDocument;
		this.fsFinanceYear = fsFinanceYear;
		this.documentNo = documentNo;
		this.applicationCountryId = applicationCountryId;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.remarks = remarks;
		this.beneficiaryId = beneficiaryId;
		this.beneficiaryBankId = beneficiaryBankId;
		this.beneficiaryBankBranchId = beneficiaryBankBranchId;
		this.applicationCompanyId = applicationCompanyId;
		this.applicationDocumentId = applicationDocumentId;
		this.applicationFinanceYear = applicationFinanceYear;
		this.applicationDocumentNo = applicationDocumentNo;
		this.branchId=branchId;
		this.countryBranchId=countryBranchId;
	}

	@Id
	@GeneratedValue(generator = "ex_special_rate_req_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ex_special_rate_req_seq", sequenceName = "EX_SPECIAL_RATE_REQ_SEQ", allocationSize = 1)
	@Column(name = "SPECIAL_RATE_REQ_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getSpecialRateRequestId() {
		return specialRateRequestId;
	}
	public void setSpecialRateRequestId(BigDecimal specialRateRequestId) {
		this.specialRateRequestId = specialRateRequestId;
	}

	@Column(name = "DOCUMENT_NO")
	public BigDecimal getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}

	@Column(name = "APPLICATION_COUNTRY_ID")
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}
	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}

	@Column(name = "FC_AMOUNT")
	public BigDecimal getFcAmount() {
		return fcAmount;
	}
	public void setFcAmount(BigDecimal fcAmount) {
		this.fcAmount = fcAmount;
	}

	@Column(name = "SELL_RATE")
	public BigDecimal getSellRate() {
		return sellRate;
	}
	public void setSellRate(BigDecimal sellRate) {
		this.sellRate = sellRate;
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
	@JoinColumn(name = "BENEFICARY_BANK_ID")
	public BankMaster getFsBankMaster() {
		return fsBankMaster;
	}
	public void setFsBankMaster(BankMaster fsBankMaster) {
		this.fsBankMaster = fsBankMaster;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CURRENCY_ID")
	public CurrencyMaster getFsCurrencyMaster() {
		return fsCurrencyMaster;
	}
	public void setFsCurrencyMaster(CurrencyMaster fsCurrencyMaster) {
		this.fsCurrencyMaster = fsCurrencyMaster;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DOCUMENT_FINANCE_YEAR")
	public UserFinancialYear getFsFinanceYear() {
		return fsFinanceYear;
	}
	public void setFsFinanceYear(UserFinancialYear fsFinanceYear) {
		this.fsFinanceYear = fsFinanceYear;
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
	@JoinColumn(name = "DOCUMENT_CODE ")
	public Document getFsDocument() {
		return fsDocument;
	}
	public void setFsDocument(Document fsDocument) {
		this.fsDocument = fsDocument;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPANY_ID")
	public CompanyMaster getCompanyMaster() {
		return companyMaster;
	}
	public void setCompanyMaster(CompanyMaster companyMaster) {
		this.companyMaster = companyMaster;
	}

	@Column(name = "APPROVED_BY")
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

	@Column(name = "REMARKS")
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name = "BENEFICIARY_ID")
	public BigDecimal getBeneficiaryId() {
		return beneficiaryId;
	}
	public void setBeneficiaryId(BigDecimal beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
	}

	@Column(name = "BENEFICIARY_BANK_ID")
	public BigDecimal getBeneficiaryBankId() {
		return beneficiaryBankId;
	}
	public void setBeneficiaryBankId(BigDecimal beneficiaryBankId) {
		this.beneficiaryBankId = beneficiaryBankId;
	}

	@Column(name = "BENEFICIARY_BANK_BRANCH_ID")
	public BigDecimal getBeneficiaryBankBranchId() {
		return beneficiaryBankBranchId;
	}
	public void setBeneficiaryBankBranchId(BigDecimal beneficiaryBankBranchId) {
		this.beneficiaryBankBranchId = beneficiaryBankBranchId;
	}

	@Column(name = "APPLICATION_COMPANY_ID")
	public BigDecimal getApplicationCompanyId() {
		return applicationCompanyId;
	}
	public void setApplicationCompanyId(BigDecimal applicationCompanyId) {
		this.applicationCompanyId = applicationCompanyId;
	}

	@Column(name = "APPLICATION_DOCUMENT_ID")
	public BigDecimal getApplicationDocumentId() {
		return applicationDocumentId;
	}
	public void setApplicationDocumentId(BigDecimal applicationDocumentId) {
		this.applicationDocumentId = applicationDocumentId;
	}

	@Column(name = "APPLICATION_FINANCE_YEAR")
	public BigDecimal getApplicationFinanceYear() {
		return applicationFinanceYear;
	}
	public void setApplicationFinanceYear(BigDecimal applicationFinanceYear) {
		this.applicationFinanceYear = applicationFinanceYear;
	}

	@Column(name = "APPLICATION_DOCUMENT_NO")
	public BigDecimal getApplicationDocumentNo() {
		return applicationDocumentNo;
	}
	public void setApplicationDocumentNo(BigDecimal applicationDocumentNo) {
		this.applicationDocumentNo = applicationDocumentNo;
	}

	@Column(name = "BRANCH_ID")
	public BigDecimal getBranchId() {
		return branchId;
	}

	public void setBranchId(BigDecimal branchId) {
		this.branchId = branchId;
	}
	@Column(name = "COUNTRY_BRANCH_ID")
	public BigDecimal getCountryBranchId() {
		return countryBranchId;
	}

	public void setCountryBranchId(BigDecimal countryBranchId) {
		this.countryBranchId = countryBranchId;
	}
	

	
}