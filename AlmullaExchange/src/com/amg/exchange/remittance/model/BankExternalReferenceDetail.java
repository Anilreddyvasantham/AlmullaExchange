/**
 * 
 */
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

import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.BankMaster;

/**
 * @author Subramaniam
 *
 */

@Entity
@Table(name="EX_BANK_EXTERNAL_REF_DETAIL")
public class BankExternalReferenceDetail implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private BigDecimal bankExtRefDetailSeqId;
	private CountryMaster applicationCountry;
	private BankMaster bank;
	private String bankCode;
	private BankMaster beneficaryBank;
	//private BigDecimal beneficaryBankCode;
	private String beneficaryBankCode;
	private BankBranch beneficaryBranch;
	private BigDecimal beneficaryBranchCode;
	private String bankBranchExternalId;	
	private String isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private BankExternalReferenceHead bankExternalReferenceHead;
	private CountryMaster countryId;
	private String remarks;
	private String indic1;
	private String indic2 ;
	
	//Added by Rabil on 06/02/2016
	private String flexField1;
	private String flexField2;
	private String flexField3;

	/**
	 * 
	 */
	public BankExternalReferenceDetail() {
		// TODO Auto-generated constructor stub
	}
	
		
	



	public BankExternalReferenceDetail(BigDecimal bankExtRefDetailSeqId, CountryMaster applicationCountry, BankMaster bank, String bankCode, BankMaster beneficaryBank, String beneficaryBankCode,
			BankBranch beneficaryBranch, BigDecimal beneficaryBranchCode, String bankBranchExternalId, String isActive, String createdBy, Date createdDate, String modifiedBy, Date modifiedDate,
			String approvedBy, Date approvedDate, BankExternalReferenceHead bankExternalReferenceHead, CountryMaster countryId) {
		super();
		this.bankExtRefDetailSeqId = bankExtRefDetailSeqId;
		this.applicationCountry = applicationCountry;
		this.bank = bank;
		this.bankCode = bankCode;
		this.beneficaryBank = beneficaryBank;
		this.beneficaryBankCode = beneficaryBankCode;
		this.beneficaryBranch = beneficaryBranch;
		this.beneficaryBranchCode = beneficaryBranchCode;
		this.bankBranchExternalId = bankBranchExternalId;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.bankExternalReferenceHead = bankExternalReferenceHead;
		this.countryId = countryId;
	}






	@Id
	@GeneratedValue(generator="ex_bank_external_ref_detail_seq", strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_bank_external_ref_detail_seq",sequenceName="EX_BANK_EXT_REF_DETAIL_SEQ",allocationSize=1)
	@Column(name = "BANK_EXT_REF_DETAIL_SEQ_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getBankExtRefDetailSeqId() {
		return bankExtRefDetailSeqId;
	}
	public void setBankExtRefDetailSeqId(BigDecimal bankExtRefDetailSeqId) {
		this.bankExtRefDetailSeqId = bankExtRefDetailSeqId;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "APPLICATION_COUNTRY_ID")	
	public CountryMaster getApplicationCountry() {
		return applicationCountry;
	}
	public void setApplicationCountry(CountryMaster applicationCountry) {
		this.applicationCountry = applicationCountry;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_ID")
	public BankMaster getBank() {
		return bank;
	}
	public void setBank(BankMaster bank) {
		this.bank = bank;
	}
	@Column(name="BANK_CODE")
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BENEFICARY_BANK_ID")
	public BankMaster getBeneficaryBank() {
		return beneficaryBank;
	}
	
	public void setBeneficaryBank(BankMaster beneficaryBank) {
		this.beneficaryBank = beneficaryBank;
	}
	@Column(name="BENEFICARY_BANK_CODE")
	public String getBeneficaryBankCode() {
		return beneficaryBankCode;
	}	
	public void setBeneficaryBankCode(String beneficaryBankCode) {
		this.beneficaryBankCode = beneficaryBankCode;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BENEFICARY_BRANCH_ID")
	public BankBranch getBeneficaryBranch() {
		return beneficaryBranch;
	}
	public void setBeneficaryBranch(BankBranch beneficaryBranch) {
		this.beneficaryBranch = beneficaryBranch;
	}
	
	@Column(name="BENEFICARY_BRANCH_CODE")
	public BigDecimal getBeneficaryBranchCode() {
		return beneficaryBranchCode;
	}
	public void setBeneficaryBranchCode(BigDecimal beneficaryBranchCode) {
		this.beneficaryBranchCode = beneficaryBranchCode;
	}
	
	@Column(name="BRANCH_EXTERNAL_ID")
	public String getBankBranchExternalId() {
		return bankBranchExternalId;
	}
	public void setBankBranchExternalId(String bankBranchExternalId) {
		this.bankBranchExternalId = bankBranchExternalId;
	}
	@Column(name="ISACTIVE")
	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
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


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_EXT_REF_HEAD_SEQ_ID")
	public BankExternalReferenceHead getBankExternalReferenceHead() {
		return bankExternalReferenceHead;
	}


	public void setBankExternalReferenceHead(
			BankExternalReferenceHead bankExternalReferenceHead) {
		this.bankExternalReferenceHead = bankExternalReferenceHead;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_ID")	
	public CountryMaster getCountryId() {
		return countryId;
	}


	public void setCountryId(CountryMaster countryId) {
		this.countryId = countryId;
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





	@Column(name="O_INDIC1")
	public String getIndic1() {
		return indic1;
	}






	public void setIndic1(String indic1) {
		this.indic1 = indic1;
	}





	@Column(name="O_INDIC2")
	public String getIndic2() {
		return indic2;
	}






	public void setIndic2(String indic2) {
		this.indic2 = indic2;
	}
	
	@Column(name="FLEX_FIELD1")
	public String getFlexField1() {
		return flexField1;
	}



	
	public void setFlexField1(String flexField1) {
		this.flexField1 = flexField1;
	}



	@Column(name="FLEX_FIELD2")
	public String getFlexField2() {
		return flexField2;
	}




	public void setFlexField2(String flexField2) {
		this.flexField2 = flexField2;
	}



	@Column(name="FLEX_FIELD3")
	public String getFlexField3() {
		return flexField3;
	}




	public void setFlexField3(String flexField3) {
		this.flexField3 = flexField3;
	}
	
	
}