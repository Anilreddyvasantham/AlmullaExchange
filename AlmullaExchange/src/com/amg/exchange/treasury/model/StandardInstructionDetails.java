package com.amg.exchange.treasury.model;

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
import javax.persistence.TableGenerator;

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;

@Entity
@Table(name = "EX_STANDARD_INSTRN_DETAILS")
public class StandardInstructionDetails implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private BigDecimal standardInstrnDetailsId;
	private StandardInstruction exstandardInstructionId;
	private CountryMaster exStandardInstructionForAllicationCountry;
	private CompanyMaster fsCompanyMaster;
	private BankMaster exBankMaster;
	private CurrencyMaster exCurrenyMaster;
	private BankAccountDetails bankAccountDetailsId;
	private BigDecimal lineNumber;
	private String lineDescription;
	private String isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
//	private BigDecimal standardInsructionNumber;
	private String approveBy;
	private Date approveDate;
	private String intructionType;
	private String remarks;
	
	
	
	@Id
	@GeneratedValue(generator="ex_standard_instrn_details_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_standard_instrn_details_seq",sequenceName="EX_STANDARD_INSTRN_DETAILS_SEQ",allocationSize=1)
	@Column(name = "STANDARD_INSTRN_DETAILS_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getStandardInstrnDetailsId() {
		return standardInstrnDetailsId;
	}

	public void setStandardInstrnDetailsId(BigDecimal standardInstrnDetailsId) {
		this.standardInstrnDetailsId = standardInstrnDetailsId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STANDARD_INSTRUCTION_ID")
	public StandardInstruction getExstandardInstructionId() {
		return exstandardInstructionId;
	}

	public void setExstandardInstructionId(
			StandardInstruction exstandardInstructionId) {
		this.exstandardInstructionId = exstandardInstructionId;
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
	
	@Column(name = "LINE_NUMBER")
	public BigDecimal getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(BigDecimal lineNumber) {
		this.lineNumber = lineNumber;
	}
	

	@Column(name = "LINE_DESCRIPTION", length = 100)
	public String getLineDescription() {
		return lineDescription;
	}

	public void setLineDescription(String lineDescription) {
		this.lineDescription = lineDescription;
	}

	@Column(name = "IS_ACTIVE", length = 1)
	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
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
	
/*	@Column(name = "STANDARD_INSTRUCTION_NUMBER")
	public BigDecimal getStandardInsructionNumber() {
		return standardInsructionNumber;
	}

	public void setStandardInsructionNumber(BigDecimal standardInsructionNumber) {
		this.standardInsructionNumber = standardInsructionNumber;
	}*/

	
	@Column(name = "APPROVED_BY")
	public String getApproveBy() {
		return approveBy;
	}
	
	@Column(name = "LINE_TYPE")
	public String getIntructionType() {
		return intructionType;
	}

	public void setIntructionType(String intructionType) {
		this.intructionType = intructionType;
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

	
	@Column(name = "REMARKS")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	
}
