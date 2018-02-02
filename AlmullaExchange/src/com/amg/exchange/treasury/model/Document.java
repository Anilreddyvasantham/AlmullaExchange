package com.amg.exchange.treasury.model;

import java.io.Serializable;
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

import com.amg.exchange.common.model.LanguageType;
import com.amg.exchange.remittance.model.AdditionalInstructionData;
import com.amg.exchange.remittance.model.Remittance;
import com.amg.exchange.remittance.model.RemittanceAppBenificiary;
import com.amg.exchange.remittance.model.RemittanceApplication;
import com.amg.exchange.remittance.model.SpecialRateRequest;
import com.amg.exchange.treasury.deal.supplier.model.DayBookDetails;
import com.amg.exchange.treasury.deal.supplier.model.DayBookHeader;

@Entity
@Table(name="EX_DOCUMENT")
public class Document implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private BigDecimal documentID;
	private BigDecimal documentCode;
	private String documentDesc;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;
	private String isActive;
	
	
	
	
	private LanguageType fsLanguageType;
	
	private List<CustomerSpecialDealRequest> customerSpeacialDealReqDocument = new ArrayList<CustomerSpecialDealRequest>();
	private Set<TreasuryDealHeader> exDealHeader = new HashSet<TreasuryDealHeader>(0);
	private Set<TreasuryDealDetail> exDealDetail = new HashSet<TreasuryDealDetail>(0);
	private Set<TreasuryStandardInstruction> exTreasuryStandardIns = new HashSet<TreasuryStandardInstruction>(0);
	private Set<DayBookHeader> exDayBook = new HashSet<DayBookHeader>(0);
	private Set<DayBookDetails> dayBookDetailsList = new HashSet<DayBookDetails>(0);
	private List<SpecialRateRequest> specialRateRequest=new ArrayList<SpecialRateRequest>();
	private Set<Remittance> exRemittance = new HashSet<Remittance>(0);
	private Set<RemittanceApplication> exRemittanceApplication = new HashSet<RemittanceApplication>(0);
	 private Set<RemittanceAppBenificiary> exRemittanceAppBenificiary = new HashSet<RemittanceAppBenificiary>(0);
	 private Set<AdditionalInstructionData> additionalInstData = new HashSet<AdditionalInstructionData>(0);
	
	/*private List<TreasuryDealDetail> exTreasuryDealDetails = new ArrayList<TreasuryDealDetail>();*/
	
	
	public Document() {
	}

	public Document(BigDecimal documentID, BigDecimal documentCode,
			String documentDesc, String createdBy, Date createdDate,
			List<CustomerSpecialDealRequest> customerSpeacialDealReqDocument,LanguageType fsLanguageType,
			Set<TreasuryDealHeader> exDealHeader,
			Set<TreasuryDealDetail> exDealDetail,
			Set<TreasuryStandardInstruction> exTreasuryStandardIns,
			Set<DayBookHeader> exDayBook,
			Set<DayBookDetails> dayBookDetailsList ,List<SpecialRateRequest> specialRateRequest,
			Set<Remittance> exRemittance,
			Set<RemittanceApplication> exRemittanceApplication,
			Set<RemittanceAppBenificiary> exRemittanceAppBenificiary,
			Set<AdditionalInstructionData> additionalInstData,String modifiedBy,Date modifiedDate,String approvedBy ,Date approvedDate,String remarks,String isActive) {
		this.documentID = documentID;
		this.documentCode = documentCode;
		this.documentDesc = documentDesc;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy=modifiedBy;
		this.modifiedDate=modifiedDate;
		this.approvedBy=approvedBy;
		this.approvedDate=approvedDate;
		this.isActive=isActive;
		this.remarks=remarks;
		this.customerSpeacialDealReqDocument=customerSpeacialDealReqDocument;
		this.fsLanguageType = fsLanguageType;
		this.exDealHeader = exDealHeader;
		this.exDealDetail=exDealDetail;
		this.exTreasuryStandardIns = exTreasuryStandardIns;
		this.exDayBook=exDayBook;
		this.dayBookDetailsList =dayBookDetailsList;
		this.specialRateRequest=specialRateRequest;
		this.exRemittance = exRemittance;
		this.exRemittanceApplication = exRemittanceApplication;
		this.exRemittanceAppBenificiary = exRemittanceAppBenificiary;
		this.additionalInstData = additionalInstData;
	}
	
	/*@Id
	@TableGenerator(name="documentid",table="ex_documentidpk",pkColumnName="documentidkey",pkColumnValue="documentidvalue",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.TABLE,generator="documentid")
	@Column(name ="DOCUMENT_ID" , unique=true, nullable=false, precision=22, scale=0)*/
	
	//dded by kani begin
	
	@Id
	@GeneratedValue(generator="ex_document_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_document_seq",sequenceName="EX_DOCUMENT_SEQ",allocationSize=1)
	@Column(name = "DOCUMENT_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getDocumentID() {
		return documentID;
	}

	public void setDocumentID(BigDecimal documentID) {
		this.documentID = documentID;
	}
	@Column(name="DOCUMENT_CODE")
	public BigDecimal getDocumentCode() {
		return documentCode;
	}

	public void setDocumentCode(BigDecimal documentCode) {
		this.documentCode = documentCode;
	}
	@Column(name="DOCUMENT_DESC")
	public String getDocumentDesc() {
		return documentDesc;
	}

	public void setDocumentDesc(String documentDesc) {
		this.documentDesc = documentDesc;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customerSpeacialDealReqDocument")
	public List<CustomerSpecialDealRequest> getCustomerSpeacialDealReqDocument() {
		return customerSpeacialDealReqDocument;
	}

	public void setCustomerSpeacialDealReqDocument(
			List<CustomerSpecialDealRequest> customerSpeacialDealReqDocument) {
		this.customerSpeacialDealReqDocument = customerSpeacialDealReqDocument;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LANGUAGE_ID")
	public LanguageType getFsLanguageType() {
		return fsLanguageType;
	}

	public void setFsLanguageType(LanguageType fsLanguageType) {
		this.fsLanguageType = fsLanguageType;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exDocument")
	public Set<TreasuryDealHeader> getExDealHeader() {
		return exDealHeader;
	}

	public void setExDealHeader(Set<TreasuryDealHeader> exDealHeader) {
		this.exDealHeader = exDealHeader;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "treasuryDealDocument")
	public Set<TreasuryDealDetail> getExDealDetail() {
		return exDealDetail;
	}

	public void setExDealDetail(Set<TreasuryDealDetail> exDealDetail) {
		this.exDealDetail = exDealDetail;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "treasurydocDocument")
	public Set<TreasuryStandardInstruction> getExTreasuryStandardIns() {
		return exTreasuryStandardIns;
	}

	public void setExTreasuryStandardIns(
			Set<TreasuryStandardInstruction> exTreasuryStandardIns) {
		this.exTreasuryStandardIns = exTreasuryStandardIns;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "doucDocumentId")
	public Set<DayBookHeader> getExDayBook() {
		return exDayBook;
	}

	public void setExDayBook(Set<DayBookHeader> exDayBook) {
		this.exDayBook = exDayBook;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "dayBookDocumentId")
	public Set<DayBookDetails> getDayBookDetailsList() {
		return dayBookDetailsList;
	}

	public void setDayBookDetailsList(Set<DayBookDetails> dayBookDetailsList) {
		this.dayBookDetailsList = dayBookDetailsList;
	}

	/*@OneToMany(fetch = FetchType.LAZY, mappedBy = "specialRequestDocumentCode")
	public List<TreasuryDealDetail> getExTreasuryDealDetails() {
		return exTreasuryDealDetails;
	}

	public void setExTreasuryDealDetails(
			List<TreasuryDealDetail> exTreasuryDealDetails) {
		this.exTreasuryDealDetails = exTreasuryDealDetails;
	}
*/
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsDocument")
	public List<SpecialRateRequest> getSpecialRateRequest() {
		return specialRateRequest;
	}

	public void setSpecialRateRequest(List<SpecialRateRequest> specialRateRequest) {
		this.specialRateRequest = specialRateRequest;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exDocument")
	public Set<Remittance> getExRemittance() {
		return exRemittance;
	}

	public void setExRemittance(Set<Remittance> exRemittance) {
		this.exRemittance = exRemittance;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exDocument")
	public Set<RemittanceApplication> getExRemittanceApplication() {
		return exRemittanceApplication;
	}

	public void setExRemittanceApplication(
			Set<RemittanceApplication> exRemittanceApplication) {
		this.exRemittanceApplication = exRemittanceApplication;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exDocument")
	public Set<RemittanceAppBenificiary> getExRemittanceAppBenificiary() {
		return exRemittanceAppBenificiary;
	}

	public void setExRemittanceAppBenificiary(
			Set<RemittanceAppBenificiary> exRemittanceAppBenificiary) {
		this.exRemittanceAppBenificiary = exRemittanceAppBenificiary;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exDocument")
	public Set<AdditionalInstructionData> getAdditionalInstData() {
		return additionalInstData;
	}

	public void setAdditionalInstData(
			Set<AdditionalInstructionData> additionalInstData) {
		this.additionalInstData = additionalInstData;
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
	@Column(name="APPROVED_BY")
	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	@Column(name="APPROVED_DATE")
	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
	@Column(name="REMARKS")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	@Column(name="ISACTIVE")
	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	
}