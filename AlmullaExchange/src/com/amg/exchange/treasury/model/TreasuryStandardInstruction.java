package com.amg.exchange.treasury.model;

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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.LanguageType;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;



/*******************************************************************************************************************

File & Description		: TreasuryStandardInstruction.java & Is model for both Sale / Purchase instruction date into DB.

Created	:	
				Date	: 19-Nov-2014 
				By		: Muruganand

 ********************************************************************************************************************/
@Entity
@Table(name = "EX_TREASURY_STANDARD_INSTRUCT")
public class TreasuryStandardInstruction implements java.io.Serializable {

	private static final long serialVersionUID = 8982196144100043131L;
	
	private BigDecimal treasuryStandardInstructionId;
	private CountryMaster treasuryCountryMaster;
	private CompanyMaster treasurycomCompanyMaster;
	private Document treasurydocDocument;
	private BigDecimal treasDocumentFinancialYear;
	private LanguageType treasuryLanguageType;
	private BigDecimal ducumentNumber;
	private String lineType;
	private BigDecimal standardInstructionNumber;
	private BigDecimal messageLineNumber;
	private String messageDescription;
	private String IsActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;

	private TreasuryDealDetail treasuryDealDetail;
	
	//private Set<TreasuryDealDetail> treasuryStandardInstruction = new HashSet<TreasuryDealDetail>(0);
	
	public TreasuryStandardInstruction() {
		super();
	}
	public TreasuryStandardInstruction(
			BigDecimal treasuryStandardInstructionId,
			CountryMaster treasuryCountryMaster,
			CompanyMaster treasurycomCompanyMaster,
			Document treasurydocDocument,
			BigDecimal treasDocumentFinancialYear,
			LanguageType treasuryLanguageType, BigDecimal ducumentNumber,
			String lineType, BigDecimal standardInstructionNumber,
			BigDecimal messageLineNumber, String messageDescription,
			String isActive, String createdBy, Date createdDate,
			String modifiedBy, Date modifiedDate,
			TreasuryDealDetail treasuryDealDetail) {
		super();
		this.treasuryStandardInstructionId = treasuryStandardInstructionId;
		this.treasuryCountryMaster = treasuryCountryMaster;
		this.treasurycomCompanyMaster = treasurycomCompanyMaster;
		this.treasurydocDocument = treasurydocDocument;
		this.treasDocumentFinancialYear = treasDocumentFinancialYear;
		this.treasuryLanguageType = treasuryLanguageType;
		this.ducumentNumber = ducumentNumber;
		this.lineType = lineType;
		this.standardInstructionNumber = standardInstructionNumber;
		this.messageLineNumber = messageLineNumber;
		this.messageDescription = messageDescription;
		IsActive = isActive;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate; 
		this.treasuryDealDetail = treasuryDealDetail ;
	}
		
	@Id
	@GeneratedValue(generator="ex_trasry_standa_instruct_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_trasry_standa_instruct_seq",sequenceName="EX_TRASRY_STANDA_INSTRUCT_SEQ",allocationSize=1)
	@Column(name = "TREASURY_STANDARD_INSTRUCT_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getTreasuryStandardInstructionId() {
		return treasuryStandardInstructionId;
	}
	public void setTreasuryStandardInstructionId(
			BigDecimal treasuryStandardInstructionId) {
		this.treasuryStandardInstructionId = treasuryStandardInstructionId;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "APPLICATION_COUNTRY_ID")
	public CountryMaster getTreasuryCountryMaster() {
		return treasuryCountryMaster;
	}
	public void setTreasuryCountryMaster(CountryMaster treasuryCountryMaster) {
		this.treasuryCountryMaster = treasuryCountryMaster;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPANY_ID")
	public CompanyMaster getTreasurycomCompanyMaster() {
		return treasurycomCompanyMaster;
	}
	public void setTreasurycomCompanyMaster(CompanyMaster treasurycomCompanyMaster) {
		this.treasurycomCompanyMaster = treasurycomCompanyMaster;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DOCUMENT_ID")
	public Document getTreasurydocDocument() {
		return treasurydocDocument;
	}
	public void setTreasurydocDocument(Document treasurydocDocument) {
		this.treasurydocDocument = treasurydocDocument;
	}

	@Column(name = "DOCUMENT_FINANCE_YEAR")
	public BigDecimal getTreasDocumentFinancialYear() {
		return treasDocumentFinancialYear;
	}
	public void setTreasDocumentFinancialYear(
			BigDecimal treasDocumentFinancialYear) {
		this.treasDocumentFinancialYear = treasDocumentFinancialYear;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LANGUAGE_ID")
	public LanguageType getTreasuryLanguageType() {
		return treasuryLanguageType;
	}
	public void setTreasuryLanguageType(LanguageType treasuryLanguageType) {
		this.treasuryLanguageType = treasuryLanguageType;
	}
	@Column(name = "DOCUMENT_NUMBER")
	public BigDecimal getDucumentNumber() {
		return ducumentNumber;
	}
	public void setDucumentNumber(BigDecimal ducumentNumber) {
		this.ducumentNumber = ducumentNumber;
	}
	@Column(name = "LINE_TYPE")
	public String getLineType() {
		return lineType;
	}
	public void setLineType(String lineType) {
		this.lineType = lineType;
	}
	@Column(name = "STANDARD_INSTRUCTION_NO")
	public BigDecimal getStandardInstructionNumber() {
		return standardInstructionNumber;
	}
	public void setStandardInstructionNumber(BigDecimal standardInstructionNumber) {
		this.standardInstructionNumber = standardInstructionNumber;
	}
	@Column(name = "MESSAGE_LINE_NO")
	public BigDecimal getMessageLineNumber() {
		return messageLineNumber;
	}
	public void setMessageLineNumber(BigDecimal messageLineNumber) {
		this.messageLineNumber = messageLineNumber;
	}
	@Column(name = "MESSAGE_DESCRIPTION")
	public String getMessageDescription() {
		return messageDescription;
	}
	public void setMessageDescription(String messageDescription) {
		this.messageDescription = messageDescription;
	}
	@Column(name = "ISACTIVE")
	public String getIsActive() {
		return IsActive;
	}
	public void setIsActive(String isActive) {
		IsActive = isActive;
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
	@JoinColumn(name = "TREASURY_DEAL_DETAIL_ID", nullable = false)
	public TreasuryDealDetail getTreasuryDealDetail() {
		return treasuryDealDetail;
	}
	public void setTreasuryDealDetail(TreasuryDealDetail treasuryDealDetail) {
		this.treasuryDealDetail = treasuryDealDetail;
	}
	

	/*@OneToMany(fetch = FetchType.LAZY, mappedBy = "treasuryStandardInstruction")
	public TreasuryDealDetail getTreasuryStandardInstruction() {
		return treasuryDealDetail;
	}
	public void setTreasuryStandardInstruction(
			Set<TreasuryDealDetail> treasuryStandardInstruction) {
		this.treasuryDealDetail = treasuryDealDetail;
	}*/
	
}
