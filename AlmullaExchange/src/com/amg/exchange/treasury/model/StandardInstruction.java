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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;

@Entity
@Table(name = "EX_STANDARD_INSTRUCTION")
public class StandardInstruction implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;

	private BigDecimal standardInstructionId;
	private CountryMaster exStandardInstructionForAllicationCountry;
	private CompanyMaster fsCompanyMaster;
	private BankMaster exBankMaster;
	private CurrencyMaster exCurrenyMaster;
	private BankAccountDetails bankAccountDetailsId;
//	private BigDecimal standardInsructionNumber;
	private String isActive;
	private String approveBy;
	private Date approveDate;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String instructionDescription;
	private String intructionType;
	
	/*private Set<StandardInstructionDetails> exStandardInstructionDetails = new HashSet<StandardInstructionDetails>(0);*/
	/*private Set<TreasuryDealDetail> treasuryDealDetail = new HashSet<TreasuryDealDetail>(0);*/

	
	@Id
	@GeneratedValue(generator="ex_standard_instruction_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_standard_instruction_seq",sequenceName="EX_STANDARD_INSTRUCTION_SEQ",allocationSize=1)
	@Column(name = "STANDARD_INSTRUCTION_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getStandardInstructionId() {
		return standardInstructionId;
	}

	public void setStandardInstructionId(BigDecimal standardInstructionId) {
		this.standardInstructionId = standardInstructionId;
	}

	@ManyToOne
	@JoinColumn(name = "APPLICATION_COUNTRY_ID")
	public CountryMaster getExStandardInstructionForAllicationCountry() {
		return exStandardInstructionForAllicationCountry;
	}

	public void setExStandardInstructionForAllicationCountry(
			CountryMaster exStandardInstructionForAllicationCountry) {
		this.exStandardInstructionForAllicationCountry = exStandardInstructionForAllicationCountry;
	}

	@ManyToOne
	@JoinColumn(name = "COMPANY_ID")
	public CompanyMaster getFsCompanyMaster() {
		return fsCompanyMaster;
	}

	public void setFsCompanyMaster(CompanyMaster fsCompanyMaster) {
		this.fsCompanyMaster = fsCompanyMaster;
	}

	@ManyToOne
	@JoinColumn(name = "BANK_ID")
	public BankMaster getExBankMaster() {
		return exBankMaster;
	}

	public void setExBankMaster(BankMaster exBankMaster) {
		this.exBankMaster = exBankMaster;
	}

	@ManyToOne
	@JoinColumn(name = "CURRENCY_ID")
	public CurrencyMaster getExCurrenyMaster() {
		return exCurrenyMaster;
	}

	public void setExCurrenyMaster(CurrencyMaster exCurrenyMaster) {
		this.exCurrenyMaster = exCurrenyMaster;
	}

	
	
	
	@ManyToOne
	@JoinColumn(name = "BANK_ACCT_DET_ID")
	public BankAccountDetails getBankAccountDetailsId() {
		return bankAccountDetailsId;
	}

	public void setBankAccountDetailsId(BankAccountDetails bankAccountDetailsId) {
		this.bankAccountDetailsId = bankAccountDetailsId;
	}

/*	@Column(name = "STANDARD_INSTRUCTION_NUMBER")
	public BigDecimal getStandardInsructionNumber() {
		return standardInsructionNumber;
	}

	public void setStandardInsructionNumber(BigDecimal standardInsructionNumber) {
		this.standardInsructionNumber = standardInsructionNumber;
	}*/

	@Column(name = "IS_ACTIVE", length = 1)
	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Column(name = "APPROVED_BY", length = 15)
	public String getApproveBy() {
		return approveBy;
	}

	public void setApproveBy(String approveBy) {
		this.approveBy = approveBy;
	}

	@Column(name = "APPROVED_DATE")
	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	@Column(name = "LINE_TYPE")
	public String getIntructionType() {
		return intructionType;
	}

	public void setIntructionType(String intructionType) {
		this.intructionType = intructionType;
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

	@Column(name = "INSTRUCTION_DESCRIPTION", length = 15)
	public String getInstructionDescription() {
		return instructionDescription;
	}

	public void setInstructionDescription(String instructionDescription) {
		this.instructionDescription = instructionDescription;
	}
	
	/*@OneToMany(fetch = FetchType.LAZY, mappedBy = "exstandardInstructionId")
	public Set<StandardInstructionDetails> getExStandardInstructionDetails() {
		return exStandardInstructionDetails;
	}

	public void setExStandardInstructionDetails(Set<StandardInstructionDetails> exStandardInstructionDetails) {
		this.exStandardInstructionDetails = exStandardInstructionDetails;
	}*/

/*	@OneToMany(fetch = FetchType.LAZY, mappedBy = "standardInstructionId")
	public Set<TreasuryDealDetail> getTreasuryDealDetail() {
		return treasuryDealDetail;
	}

	public void setTreasuryDealDetail(Set<TreasuryDealDetail> treasuryDealDetail) {
		this.treasuryDealDetail = treasuryDealDetail;
	}*/

}
