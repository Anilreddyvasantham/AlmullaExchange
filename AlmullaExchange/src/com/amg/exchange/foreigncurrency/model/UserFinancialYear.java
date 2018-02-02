package com.amg.exchange.foreigncurrency.model;

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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.amg.exchange.remittance.model.AdditionalInstructionData;
import com.amg.exchange.remittance.model.Remittance;
import com.amg.exchange.remittance.model.RemittanceAppBenificiary;
import com.amg.exchange.remittance.model.RemittanceApplication;
import com.amg.exchange.remittance.model.SpecialRateRequest;
import com.amg.exchange.treasury.model.CustomerSpecialDealRequest;

@Entity
@Table(name = "EX_USER_FINANCIAL_YEAR")
public class UserFinancialYear implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal financialYearID;
	private BigDecimal financialYear;
	private Date financialYearBegin;
	private Date financialYearEnd;
	private String fullDesc;
	public String shortDesc;
	private Date NewBeginYear;
	private String createdBy;
	private Date createdDate;
	public String modifiedBy;
	private Date modifiedDate;
	
	private List<CustomerSpecialDealRequest> documentFinancialYear = new ArrayList<CustomerSpecialDealRequest>();
	/*private Set<TreasuryDealHeader> exDealHeader = new HashSet<TreasuryDealHeader>(0);
	private Set<TreasuryDealDetail> exDealDetail = new HashSet<TreasuryDealDetail>(0);
	private Set<TreasuryStandardInstruction> exTreasuryStandardIns = new HashSet<TreasuryStandardInstruction>(0);*/
//	private List<TreasuryDealDetail> exTreasuryDealDetails = new ArrayList<TreasuryDealDetail>();
	 private List<SpecialRateRequest> specialRatefinacialList=new ArrayList<SpecialRateRequest>();
	 private Set<Remittance> exRemittance = new HashSet<Remittance>(0);
	
	 //private Set<RemiittanceApplication> exRemiittanceApplicationsForFileFinanceYr = new HashSet<RemiittanceApplication>(0);
	 //private Set<RemiittanceApplication> exRemiittanceApplicationsForDocumentFinanceYear = new HashSet<RemiittanceApplication>(0);
	 //private Set<RemiittanceApplication> exRemiittanceApplicationsForApplicationFinanceYear = new HashSet<RemiittanceApplication>(0);
	 private Set<RemittanceAppBenificiary> exRemittanceAppBenificiary = new HashSet<RemittanceAppBenificiary>(0);
	 
	 private Set<RemittanceApplication> exRemittanceApplicationsForDocumentFinanceYear = new HashSet<RemittanceApplication>(0);
   //private Set<RemittanceApplication> exRemittanceApplicationsForTransactionFinanceYear = new HashSet<RemittanceApplication>(0);
	 private Set<RemittanceApplication> exRemittanceAppBenificiaries = new HashSet<RemittanceApplication>(0);
	 private Set<AdditionalInstructionData> additionalInstData = new HashSet<AdditionalInstructionData>(0);
	
	
	
	public UserFinancialYear(){
		
	}
	/**
	 * @param financialYearID
	 * @param financialYear
	 * @param financialYearBegin
	 * @param financialYearEnd
	 * @param fullDesc
	 * @param shortDesc
	 * @param newBeginYear
	 * @param createdBy
	 * @param createdDate
	 * @param modifiedBy
	 * @param modifiedDate
	 */
	public UserFinancialYear(
			BigDecimal financialYearID,
			BigDecimal financialYear,
			Date financialYearBegin,
			Date financialYearEnd,
			String fullDesc,
			String shortDesc,
			Date newBeginYear,
			String createdBy,
			Date createdDate,
			String modifiedBy,
			Date modifiedDate,
			List<CustomerSpecialDealRequest> documentFinancialYear,
			List<SpecialRateRequest> specialRatefinacialList,
			Set<Remittance> exRemittance,
			//Set<RemiittanceApplication> exRemiittanceApplicationsForFileFinanceYr,
			//Set<RemiittanceApplication> exRemiittanceApplicationsForDocumentFinanceYear,
			//Set<RemiittanceApplication> exRemiittanceApplicationsForApplicationFinanceYear,
			Set<RemittanceAppBenificiary> exRemittanceAppBenificiary,
			Set<RemittanceApplication> exRemittanceApplicationsForDocumentFinanceYear,
			Set<RemittanceApplication> exRemittanceAppBenificiaries,
			Set<AdditionalInstructionData> additionalInstData) {
		super();
		this.financialYearID = financialYearID;
		this.financialYear = financialYear;
		this.financialYearBegin = financialYearBegin;
		this.financialYearEnd = financialYearEnd;
		this.fullDesc = fullDesc;
		this.shortDesc = shortDesc;
		NewBeginYear = newBeginYear;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.documentFinancialYear = documentFinancialYear;
		this.specialRatefinacialList = specialRatefinacialList;
		this.exRemittance = exRemittance;
		//this.exRemiittanceApplicationsForFileFinanceYr = exRemiittanceApplicationsForFileFinanceYr;
		//this.exRemiittanceApplicationsForDocumentFinanceYear = exRemiittanceApplicationsForDocumentFinanceYear;
		//this.exRemiittanceApplicationsForApplicationFinanceYear = exRemiittanceApplicationsForApplicationFinanceYear;
		this.exRemittanceAppBenificiary = exRemittanceAppBenificiary;
		this.exRemittanceApplicationsForDocumentFinanceYear = exRemittanceApplicationsForDocumentFinanceYear;
		this.exRemittanceAppBenificiaries = exRemittanceAppBenificiaries;
		this.additionalInstData = additionalInstData;
	}
	
	
	
	@Id
	/*@TableGenerator(name="financialyearid",table="ex_user_financial_yearpk",pkColumnName="financialyearKey",pkColumnValue="financialyearvalue",allocationSize=1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator="financialyearid")
	*/
	@GeneratedValue(generator="ex_user_financial_year_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_user_financial_year_seq" ,sequenceName="EX_USER_FINANCIAL_YEAR_SEQ",allocationSize=1)	
	@Column(name = "FINANCIAL_YEAR_ID",unique=true, nullable=false, precision=22, scale=0)
	public BigDecimal getFinancialYearID() {
		return financialYearID;
	}
	public void setFinancialYearID(BigDecimal financialYearID) {
		this.financialYearID = financialYearID;
	}
	
	@Column(name = "FINANCIAL_YEAR")
	public BigDecimal getFinancialYear() {
		return financialYear;
	}
	public void setFinancialYear(BigDecimal financialYear) {
		this.financialYear = financialYear;
	}
	
	@Column(name = "FINANCIAL_YEAR_BEGIN")
	public Date getFinancialYearBegin() {
		return financialYearBegin;
	}
	public void setFinancialYearBegin(Date financialYearBegin) {
		this.financialYearBegin = financialYearBegin;
	}
	
	@Column(name = "FINANCIAL_YEAR_END")
	public Date getFinancialYearEnd() {
		return financialYearEnd;
	}
	public void setFinancialYearEnd(Date financialYearEnd) {
		this.financialYearEnd = financialYearEnd;
	}
	
	@Column(name = "FULL_DESC")
	public String getFullDesc() {
		return fullDesc;
	}
	public void setFullDesc(String fullDesc) {
		this.fullDesc = fullDesc;
	}
	@Column(name = "SHORT_DESC")
	public String getShortDesc() {
		return shortDesc;
	}
	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}
	
	@Column(name = "NEW_BEGIN_YEAR")
	public Date getNewBeginYear() {
		return NewBeginYear;
	}
	public void setNewBeginYear(Date newBeginYear) {
		NewBeginYear = newBeginYear;
	}
	@Column(name = "CREATEDBY")
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	@Column(name = "CREATEDDATE")
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	@Column(name = "MODIFIEDBY")
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	@Column(name = "MODIFIEDDATE")
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "documentFinancialYear")
	public List<CustomerSpecialDealRequest> getDocumentFinancialYear() {
		return documentFinancialYear;
	}
	public void setDocumentFinancialYear(
			List<CustomerSpecialDealRequest> documentFinancialYear) {
		this.documentFinancialYear = documentFinancialYear;
	}
/*	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exUserFinanceYear")
	public Set<TreasuryDealHeader> getExDealHeader() {
		return exDealHeader;
	}
	public void setExDealHeader(Set<TreasuryDealHeader> exDealHeader) {
		this.exDealHeader = exDealHeader;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "treasuryDealUserFinanceYear")
	public Set<TreasuryDealDetail> getExDealDetail() {
		return exDealDetail;
	}
	public void setExDealDetail(Set<TreasuryDealDetail> exDealDetail) {
		this.exDealDetail = exDealDetail;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "treasDocumentFinancialYear")
	public Set<TreasuryStandardInstruction> getExTreasuryStandardIns() {
		return exTreasuryStandardIns;
	}
	public void setExTreasuryStandardIns(
			Set<TreasuryStandardInstruction> exTreasuryStandardIns) {
		this.exTreasuryStandardIns = exTreasuryStandardIns;
	}*/
	
	/*@OneToMany(fetch = FetchType.LAZY, mappedBy = "specialRequestFinanceYear")
	public List<TreasuryDealDetail> getExTreasuryDealDetails() {
		return exTreasuryDealDetails;
	}
	public void setExTreasuryDealDetails(
			List<TreasuryDealDetail> exTreasuryDealDetails) {
		this.exTreasuryDealDetails = exTreasuryDealDetails;
	}*/
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsFinanceYear")
	public List<SpecialRateRequest> getSpecialRatefinacialList() {
		return specialRatefinacialList;
	}
	public void setSpecialRatefinacialList(List<SpecialRateRequest> specialRatefinacialList) {
		this.specialRatefinacialList = specialRatefinacialList;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exUserFinancialYear")
	public Set<Remittance> getExRemittance() {
		return exRemittance;
	}
	public void setExRemittance(Set<Remittance> exRemittance) {
		this.exRemittance = exRemittance;
	}
	
	/*@OneToMany(fetch = FetchType.LAZY, mappedBy = "exUserFinancialYearByFileFinanceYr")
	public Set<RemiittanceApplication> getExRemiittanceApplicationsForFileFinanceYr() {
		return exRemiittanceApplicationsForFileFinanceYr;
	}
	public void setExRemiittanceApplicationsForFileFinanceYr(
			Set<RemiittanceApplication> exRemiittanceApplicationsForFileFinanceYr) {
		this.exRemiittanceApplicationsForFileFinanceYr = exRemiittanceApplicationsForFileFinanceYr;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exUserFinancialYearByDocumentFinanceYear")
	public Set<RemiittanceApplication> getExRemiittanceApplicationsForDocumentFinanceYear() {
		return exRemiittanceApplicationsForDocumentFinanceYear;
	}
	public void setExRemiittanceApplicationsForDocumentFinanceYear(
			Set<RemiittanceApplication> exRemiittanceApplicationsForDocumentFinanceYear) {
		this.exRemiittanceApplicationsForDocumentFinanceYear = exRemiittanceApplicationsForDocumentFinanceYear;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exUserFinancialYearByApplicationFinanceYear")
	public Set<RemiittanceApplication> getExRemiittanceApplicationsForApplicationFinanceYear() {
		return exRemiittanceApplicationsForApplicationFinanceYear;
	}
	public void setExRemiittanceApplicationsForApplicationFinanceYear(
			Set<RemiittanceApplication> exRemiittanceApplicationsForApplicationFinanceYear) {
		this.exRemiittanceApplicationsForApplicationFinanceYear = exRemiittanceApplicationsForApplicationFinanceYear;
	}*/
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exUserFinancialYear")
	public Set<RemittanceAppBenificiary> getExRemittanceAppBenificiary() {
		return exRemittanceAppBenificiary;
	}
	public void setExRemittanceAppBenificiary(
			Set<RemittanceAppBenificiary> exRemittanceAppBenificiary) {
		this.exRemittanceAppBenificiary = exRemittanceAppBenificiary;
	}
	
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exUserFinancialYearByDocumentFinanceYear")
	public Set<RemittanceApplication> getExRemittanceApplicationsForDocumentFinanceYear() {
		return this.exRemittanceApplicationsForDocumentFinanceYear;
	}

	public void setExRemittanceApplicationsForDocumentFinanceYear(
			Set<RemittanceApplication> exRemittanceApplicationsForDocumentFinanceYear) {
		this.exRemittanceApplicationsForDocumentFinanceYear = exRemittanceApplicationsForDocumentFinanceYear;
	}

	

	/*@OneToMany(fetch = FetchType.LAZY, mappedBy = "exUserFinancialYearByTransactionFinanceYear")
	public Set<RemittanceApplication> getExRemittanceApplicationsForTransactionFinanceYear() {
		return this.exRemittanceApplicationsForTransactionFinanceYear;
	}

	public void setExRemittanceApplicationsForTransactionFinanceYear(
			Set<RemittanceApplication> exRemittanceApplicationsForTransactionFinanceYear) {
		this.exRemittanceApplicationsForTransactionFinanceYear = exRemittanceApplicationsForTransactionFinanceYear;
	}*/
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exUserFinancialYear")
	public Set<AdditionalInstructionData> getAdditionalInstData() {
		return additionalInstData;
	}
	public void setAdditionalInstData(
			Set<AdditionalInstructionData> additionalInstData) {
		this.additionalInstData = additionalInstData;
	}

	 
}
