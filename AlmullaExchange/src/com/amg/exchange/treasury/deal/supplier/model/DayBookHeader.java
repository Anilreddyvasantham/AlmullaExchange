package com.amg.exchange.treasury.deal.supplier.model;

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
import com.amg.exchange.treasury.model.Document;

@Entity
@Table(name="EX_DAYBOOK_HEADER")
public class DayBookHeader implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private BigDecimal daybookHeaderId;
	private CountryMaster dayBookCountryMaster;
	private CompanyMaster dayBookCompanyMaster;
	private Document doucDocumentId;
	private BigDecimal documentFinancialYear;
	private BigDecimal documentNumber;
	private Date documentDate;
	private Date acyymm;
	private BigDecimal branchNumber;
	private String contact;
	private BigDecimal currencyId;
	private BigDecimal fcAmount;
	private BigDecimal exchangeRate;
	private BigDecimal localAmount;
	private String faAccountNo;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date  approvedDate;
	private String subledgerInd;
	private String openItemRef;
	private String openItemId;
	private BigDecimal refCompanyId;
	private BigDecimal refDocumentId;
	private BigDecimal refFinYear;
	private BigDecimal refNumber;
	
	private String remarks;
	private String reuterReference;
	private String concludedBy;
	private String dealedWithCustomer;
	private String isActive;
	private String reason;

    public DayBookHeader() {

	}


	public DayBookHeader(BigDecimal daybookHeaderId) {
		this.daybookHeaderId = daybookHeaderId;
	}

	public DayBookHeader(BigDecimal daybookHeaderId,
			CountryMaster dayBookCountryMaster,
			CompanyMaster dayBookCompanyMaster,
			BigDecimal documentFinancialYear,
			BigDecimal documentNumber,
			Date documentDate,
			Date acyymm,
			BigDecimal branchNumber,
			String contact,
			BigDecimal currencyId,
			BigDecimal fcAmount,
			BigDecimal exchangeRate,
			BigDecimal localAmount,
			String faAccountNo,
			String createdBy,
			Date createdDate,
			String modifiedBy,
			Date modifiedDate,
			String approvedBy,
			Date  approvedDate,
			String subledgerInd,
			String openItemRef,
			String openItemId,
			BigDecimal refCompanyId,
			BigDecimal refDocumentId,
			BigDecimal refFinYear,
			BigDecimal refNumber, Document doucDocumentId,
			String remarks,String reuterReference,String concludedBy,String dealedWithCustomer, String isActive, String reason) {
		

		this.daybookHeaderId=daybookHeaderId;
		this.dayBookCountryMaster=dayBookCountryMaster;
		this.dayBookCompanyMaster=dayBookCompanyMaster;
		this.documentFinancialYear=documentFinancialYear;
		this.documentNumber=documentNumber;
		this.documentDate=documentDate;
		this.acyymm=acyymm;
		this.branchNumber=branchNumber;
		this.contact=contact;
		this.currencyId=currencyId;
		this.fcAmount=fcAmount;
		this.exchangeRate=exchangeRate;
		this.localAmount=localAmount;
		this.faAccountNo=faAccountNo;
		this.createdBy=createdBy;
		this.createdDate=createdDate;
		this.modifiedBy=modifiedBy;
		this.modifiedDate=modifiedDate;
		this.approvedBy=approvedBy;
		this.approvedDate=approvedDate;
		this.subledgerInd=subledgerInd;
		this.openItemRef=openItemRef;
		this.openItemId=openItemId;
		this.refCompanyId=refCompanyId;
		this.refDocumentId=refDocumentId;
		this.refFinYear=refFinYear;
		this.refNumber=refNumber;
		this.doucDocumentId =doucDocumentId;
		this.remarks=remarks;
		this.reuterReference=reuterReference;
		this.concludedBy=concludedBy;
		this.dealedWithCustomer=dealedWithCustomer;
		this.isActive = isActive;
		this.reason = reason;
	}

	@Id
	@GeneratedValue(generator="ex_daybook_header_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_daybook_header_seq",sequenceName="EX_DAYBOOK_HEADER_SEQ",allocationSize=1)
	@Column(name = "DAYBOOK_HEADER_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getDaybookHeaderId() {
		return daybookHeaderId;
	}


	public void setDaybookHeaderId(BigDecimal daybookHeaderId) {
		this.daybookHeaderId = daybookHeaderId;
	}


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "APPLICATION_COUNTRY_ID")
	public CountryMaster getDayBookCountryMaster() {
		return dayBookCountryMaster;
	}


	public void setDayBookCountryMaster(CountryMaster dayBookCountryMaster) {
		this.dayBookCountryMaster = dayBookCountryMaster;
	}


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPANY_ID")
	public CompanyMaster getDayBookCompanyMaster() {
		return dayBookCompanyMaster;
	}


	public void setDayBookCompanyMaster(CompanyMaster dayBookCompanyMaster) {
		this.dayBookCompanyMaster = dayBookCompanyMaster;
	}

	
	@Column(name = "DOCUMENT_FINANCE_YEAR")
	public BigDecimal getDocumentFinancialYear() {
		return documentFinancialYear;
	}


	public void setDocumentFinancialYear(BigDecimal documentFinancialYear) {
		this.documentFinancialYear = documentFinancialYear;
	}

	@Column(name = "DOCUMENT_NUMBER")
	public BigDecimal getDocumentNumber() {
		return documentNumber;
	}


	public void setDocumentNumber(BigDecimal documentNumber) {
		this.documentNumber = documentNumber;
	}


	@Column(name = "DOCUMENT_DATE")
	public Date getDocumentDate() {
		return documentDate;
	}


	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}


	@Column(name = "ACYYMM")
	public Date getAcyymm() {
		return acyymm;
	}


	public void setAcyymm(Date acyymm) {
		this.acyymm = acyymm;
	}

	@Column(name = "BRANCH_NUMBER")
	public BigDecimal getBranchNumber() {
		return branchNumber;
	}


	public void setBranchNumber(BigDecimal branchNumber) {
		this.branchNumber = branchNumber;
	}


	@Column(name = "CONTACT")
	public String getContact() {
		return contact;
	}


	public void setContact(String contact) {
		this.contact = contact;
	}


	@Column(name = "CURRENCY_ID")
	public BigDecimal getCurrencyId() {
		return currencyId;
	}


	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	@Column(name = "FC_AMOUNT")
	public BigDecimal getFcAmount() {
		return fcAmount;
	}


	public void setFcAmount(BigDecimal fcAmount) {
		this.fcAmount = fcAmount;
	}

	@Column(name = "EXCHANGE_RATE")
	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}


	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	@Column(name = "LOCAL_AMOUNT")
	public BigDecimal getLocalAmount() {
		return localAmount;
	}


	public void setLocalAmount(BigDecimal localAmount) {
		this.localAmount = localAmount;
	}

	@Column(name = "FA_ACCOUNT_NUMBER")
	public String getFaAccountNo() {
		return faAccountNo;
	}


	public void setFaAccountNo(String faAccountNo) {
		this.faAccountNo = faAccountNo;
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

	@Column(name = "SUBLEDGER_IND")
	public String getSubledgerInd() {
		return subledgerInd;
	}


	public void setSubledgerInd(String subledgerInd) {
		this.subledgerInd = subledgerInd;
	}

	@Column(name = "OPEN_ITEM_REF")
	public String getOpenItemRef() {
		return openItemRef;
	}


	public void setOpenItemRef(String openItemRef) {
		this.openItemRef = openItemRef;
	}

	@Column(name = "OPEN_ITEM_ID")
	public String getOpenItemId() {
		return openItemId;
	}


	public void setOpenItemId(String openItemId) {
		this.openItemId = openItemId;
	}

	@Column(name = "REF_COMPANY_ID")
	public BigDecimal getRefCompanyId() {
		return refCompanyId;
	}


	public void setRefCompanyId(BigDecimal refCompanyId) {
		this.refCompanyId = refCompanyId;
	}

	@Column(name = "REF_DOCUMENT_ID")
	public BigDecimal getRefDocumentId() {
		return refDocumentId;
	}


	public void setRefDocumentId(BigDecimal refDocumentId) {
		this.refDocumentId = refDocumentId;
	}

	@Column(name = "REF_FIN_YEAR")
	public BigDecimal getRefFinYear() {
		return refFinYear;
	}


	public void setRefFinYear(BigDecimal refFinYear) {
		this.refFinYear = refFinYear;
	}

	@Column(name = "REF_NUMBER")
	public BigDecimal getRefNumber() {
		return refNumber;
	}


	public void setRefNumber(BigDecimal refNumber) {
		this.refNumber = refNumber;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DOCUMENT_ID")
	public Document getDoucDocumentId() {
		return doucDocumentId;
	}


	public void setDoucDocumentId(Document doucDocumentId) {
		this.doucDocumentId = doucDocumentId;
	}

	@Column(name = "REMARKS")
	public String getRemarks() {
		return remarks;
	}


	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name = "REUTERS_REFERENCE")
	public String getReuterReference() {
		return reuterReference;
	}


	public void setReuterReference(String reuterReference) {
		this.reuterReference = reuterReference;
	}

	@Column(name = "CONCLUDED_BY")
	public String getConcludedBy() {
		return concludedBy;
	}


	public void setConcludedBy(String concludedBy) {
		this.concludedBy = concludedBy;
	}

	@Column(name = "DEALED_WITH_CUSTOMER")
	public String getDealedWithCustomer() {
		return dealedWithCustomer;
	}


	public void setDealedWithCustomer(String dealedWithCustomer) {
		this.dealedWithCustomer = dealedWithCustomer;
	}
	
	@Column(name = "ISACTIVE")
	public String getIsActive() {
		return isActive;
	}


	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Column(name = "REASON")
	public String getReason() {
		return reason;
	}


	public void setReason(String reason) {
		this.reason = reason;
	}


}
