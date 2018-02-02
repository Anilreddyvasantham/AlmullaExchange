/**
 * 
 */
package com.amg.exchange.stoppayment.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;



/**
 * @author Subramaniam
 *
 */
@Entity
@Table(name = "EX_REMIT_BENE")
public class RemittanceTranxBenificiary implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal remittanceTranxBeneId;
	private BigDecimal exDocument;
	private BigDecimal fsCompanyMaster;
	private BigDecimal exUserFinancialYear;
	private BigDecimal companyCode;
	private BigDecimal documentCode;
	private BigDecimal beneficiaryId;
	private String beneficiaryName;
	private String beneficiaryBank;
	private String beneficiaryBranch;
	private String beneficiaryAccountNo;
	// beneficiaryInterBank1 converted to beneficiarySwiftAddr1
	private String beneficiarySwiftAddr1;
	// beneficiaryInterBank2 converted to beneficiarySwiftAddr2
	private String beneficiarySwiftAddr2;
	private String beneficiarySwiftBank1;
	private String beneficiarySwiftBank2;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String isactive;
	private String beneficiaryFirstName;
	private String beneficiarySecondName;
	private String beneficiaryThirdName;
	private String beneficiaryFourthName;
	private BigDecimal remittanceTranxId;
	private BigDecimal beneficiaryBankId;
	private BigDecimal beneficiaryBankCountryId;
	private BigDecimal beneficiaryBankBranchId;
	private BigDecimal beneficiaryBranchStateId;
	private BigDecimal beneficiaryBranchDistrictId;
	private BigDecimal beneficiaryBranchCityId;
	private String beneficiaryBankSwift;
	private String beneficiaryFifthName;
	private BigDecimal beneficiaryAccountSeqId;
	private BigDecimal beneficiaryRelationShipSeqId;
	
	public RemittanceTranxBenificiary() {
		// TODO Auto-generated constructor stub
	}	

	public RemittanceTranxBenificiary(BigDecimal remittanceTranxBeneId,
			BigDecimal exDocument, BigDecimal fsCompanyMaster,
			BigDecimal exUserFinancialYear, BigDecimal companyCode,
			BigDecimal documentCode, BigDecimal beneficiaryId,
			String beneficiaryName, String beneficiaryBank,
			String beneficiaryBranch, String beneficiaryAccountNo,
			String beneficiarySwiftAddr1, String beneficiarySwiftAddr2,
			String beneficiarySwiftBank1, String beneficiarySwiftBank2,
			String createdBy, Date createdDate, String modifiedBy,
			Date modifiedDate, String isactive, String beneficiaryFirstName,
			String beneficiarySecondName, String beneficiaryThirdName,
			String beneficiaryFourthName, BigDecimal remittanceTranxId,
			BigDecimal beneficiaryBankId, BigDecimal beneficiaryBankCountryId,
			BigDecimal beneficiaryBankBranchId,
			BigDecimal beneficiaryBranchStateId,
			BigDecimal beneficiaryBranchDistrictId,
			BigDecimal beneficiaryBranchCityId, String beneficiaryBankSwift,
			String beneficiaryFifthName, BigDecimal beneficiaryAccountSeqId,
			BigDecimal beneficiaryRelationShipSeqId) {
		super();
		this.remittanceTranxBeneId = remittanceTranxBeneId;
		this.exDocument = exDocument;
		this.fsCompanyMaster = fsCompanyMaster;
		this.exUserFinancialYear = exUserFinancialYear;
		this.companyCode = companyCode;
		this.documentCode = documentCode;
		this.beneficiaryId = beneficiaryId;
		this.beneficiaryName = beneficiaryName;
		this.beneficiaryBank = beneficiaryBank;
		this.beneficiaryBranch = beneficiaryBranch;
		this.beneficiaryAccountNo = beneficiaryAccountNo;
		this.beneficiarySwiftAddr1 = beneficiarySwiftAddr1;
		this.beneficiarySwiftAddr2 = beneficiarySwiftAddr2;
		this.beneficiarySwiftBank1 = beneficiarySwiftBank1;
		this.beneficiarySwiftBank2 = beneficiarySwiftBank2;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.isactive = isactive;
		this.beneficiaryFirstName = beneficiaryFirstName;
		this.beneficiarySecondName = beneficiarySecondName;
		this.beneficiaryThirdName = beneficiaryThirdName;
		this.beneficiaryFourthName = beneficiaryFourthName;
		this.remittanceTranxId = remittanceTranxId;
		this.beneficiaryBankId = beneficiaryBankId;
		this.beneficiaryBankCountryId = beneficiaryBankCountryId;
		this.beneficiaryBankBranchId = beneficiaryBankBranchId;
		this.beneficiaryBranchStateId = beneficiaryBranchStateId;
		this.beneficiaryBranchDistrictId = beneficiaryBranchDistrictId;
		this.beneficiaryBranchCityId = beneficiaryBranchCityId;
		this.beneficiaryBankSwift = beneficiaryBankSwift;
		this.beneficiaryFifthName = beneficiaryFifthName;
		this.beneficiaryAccountSeqId = beneficiaryAccountSeqId;
		this.beneficiaryRelationShipSeqId = beneficiaryRelationShipSeqId;
	}

	@Id
	@GeneratedValue(generator="ex_remittance_trns_beni_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_remittance_trns_beni_seq",sequenceName="EX_REMITTANCE_TRANX_BENE_SEQ",allocationSize=1)
	@Column(name = "REMITTANCE_TRANX_BENE_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getRemittanceTranxBeneId() {
		return remittanceTranxBeneId;
	}
	public void setRemittanceTranxBeneId(BigDecimal remittanceTranxBeneId) {
		this.remittanceTranxBeneId = remittanceTranxBeneId;
	}

	@Column(name = "DOCUMENT_ID")
	public BigDecimal getExDocument() {
		return this.exDocument;
	}
	public void setExDocument(BigDecimal exDocument) {
		this.exDocument = exDocument;
	}

	@Column(name = "COMPANY_ID")
	public BigDecimal getFsCompanyMaster() {
		return this.fsCompanyMaster;
	}
	public void setFsCompanyMaster(BigDecimal fsCompanyMaster) {
		this.fsCompanyMaster = fsCompanyMaster;
	}

	@Column(name = "DOCUMENT_FINANCE_YEAR_ID")
	public BigDecimal getExUserFinancialYear() {
		return this.exUserFinancialYear;
	}
	public void setExUserFinancialYear(BigDecimal exUserFinancialYear) {
		this.exUserFinancialYear = exUserFinancialYear;
	}

	@Column(name = "COMPANY_CODE", precision = 22, scale = 0)
	public BigDecimal getCompanyCode() {
		return this.companyCode;
	}
	public void setCompanyCode(BigDecimal companyCode) {
		this.companyCode = companyCode;
	}

	@Column(name = "DOCUMENT_CODE", precision = 22, scale = 0)
	public BigDecimal getDocumentCode() {
		return this.documentCode;
	}
	public void setDocumentCode(BigDecimal documentCode) {
		this.documentCode = documentCode;
	}

	@Column(name = "BENEFICIARY_ID", precision = 22, scale = 0)
	public BigDecimal getBeneficiaryId() {
		return this.beneficiaryId;
	}
	public void setBeneficiaryId(BigDecimal beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
	}

	@Column(name = "BENEFICIARY_NAME", length = 200)
	public String getBeneficiaryName() {
		return this.beneficiaryName;
	}
	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	@Column(name = "BENEFICIARY_BANK", length = 200)
	public String getBeneficiaryBank() {
		return this.beneficiaryBank;
	}
	public void setBeneficiaryBank(String beneficiaryBank) {
		this.beneficiaryBank = beneficiaryBank;
	}

	@Column(name = "BENEFICIARY_BRANCH", length = 200)
	public String getBeneficiaryBranch() {
		return this.beneficiaryBranch;
	}
	public void setBeneficiaryBranch(String beneficiaryBranch) {
		this.beneficiaryBranch = beneficiaryBranch;
	}

	@Column(name = "BENEFICIARY_ACCOUNT_NO", length = 200)
	public String getBeneficiaryAccountNo() {
		return this.beneficiaryAccountNo;
	}
	public void setBeneficiaryAccountNo(String beneficiaryAccountNo) {
		this.beneficiaryAccountNo = beneficiaryAccountNo;
	}

	/*@Column(name = "BENEFICIARY_INTER_BANK1", length = 300)
	public String getBeneficiaryInterBank1() {
		return this.beneficiaryInterBank1;
	}

	public void setBeneficiaryInterBank1(String beneficiaryInterBank1) {
		this.beneficiaryInterBank1 = beneficiaryInterBank1;
	}

	@Column(name = "BENEFICIARY_INTER_BANK2", length = 300)
	public String getBeneficiaryInterBank2() {
		return this.beneficiaryInterBank2;
	}

	public void setBeneficiaryInterBank2(String beneficiaryInterBank2) {
		this.beneficiaryInterBank2 = beneficiaryInterBank2;
	}*/
	
	@Column(name = "BENEFICIARY_SWIFT_ADDR1")
	public String getBeneficiarySwiftAddr1() {
		return beneficiarySwiftAddr1;
	}
	public void setBeneficiarySwiftAddr1(String beneficiarySwiftAddr1) {
		this.beneficiarySwiftAddr1 = beneficiarySwiftAddr1;
	}

	@Column(name = "BENEFICIARY_SWIFT_ADDR2")
	public String getBeneficiarySwiftAddr2() {
		return beneficiarySwiftAddr2;
	}
	public void setBeneficiarySwiftAddr2(String beneficiarySwiftAddr2) {
		this.beneficiarySwiftAddr2 = beneficiarySwiftAddr2;
	}

	@Column(name = "BENEFICIARY_SWIFT_BANK1", length = 11)
	public String getBeneficiarySwiftBank1() {
		return this.beneficiarySwiftBank1;
	}
	public void setBeneficiarySwiftBank1(String beneficiarySwiftBank1) {
		this.beneficiarySwiftBank1 = beneficiarySwiftBank1;
	}

	@Column(name = "BENEFICIARY_SWIFT_BANK2", length = 11)
	public String getBeneficiarySwiftBank2() {
		return this.beneficiarySwiftBank2;
	}
	public void setBeneficiarySwiftBank2(String beneficiarySwiftBank2) {
		this.beneficiarySwiftBank2 = beneficiarySwiftBank2;
	}

	@Column(name = "CREATED_BY", length = 40)
	public String getCreatedBy() {
		return this.createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	
	@Column(name = "CREATED_DATE", length = 7)
	public Date getCreatedDate() {
		return this.createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "MODIFIED_BY", length = 40)
	public String getModifiedBy() {
		return this.modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	
	@Column(name = "MODIFIED_DATE", length = 7)
	public Date getModifiedDate() {
		return this.modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name = "ISACTIVE", length = 1)
	public String getIsactive() {
		return this.isactive;
	}
	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}

	@Column(name = "BENEFICIARY_FIRST_NAME", length = 20)
	public String getBeneficiaryFirstName() {
		return this.beneficiaryFirstName;
	}
	public void setBeneficiaryFirstName(String beneficiaryFirstName) {
		this.beneficiaryFirstName = beneficiaryFirstName;
	}

	@Column(name = "BENEFICIARY_SECOND_NAME", length = 20)
	public String getBeneficiarySecondName() {
		return this.beneficiarySecondName;
	}
	public void setBeneficiarySecondName(String beneficiarySecondName) {
		this.beneficiarySecondName = beneficiarySecondName;
	}

	@Column(name = "BENEFICIARY_THIRD_NAME", length = 20)
	public String getBeneficiaryThirdName() {
		return this.beneficiaryThirdName;
	}
	public void setBeneficiaryThirdName(String beneficiaryThirdName) {
		this.beneficiaryThirdName = beneficiaryThirdName;
	}

	@Column(name = "BENEFICIARY_FOURTH_NAME", length = 20)
	public String getBeneficiaryFourthName() {
		return this.beneficiaryFourthName;
	}
	public void setBeneficiaryFourthName(String beneficiaryFourthName) {
		this.beneficiaryFourthName = beneficiaryFourthName;
	}
	
	@Column(name = "REMITTANCE_TRANSACTION_ID")
	public BigDecimal getRemittanceTranxId() {
		return remittanceTranxId;
	}
	public void setRemittanceTranxId(BigDecimal remittanceTranxId) {
		this.remittanceTranxId = remittanceTranxId;
	}

	@Column(name = "BENEFICIARY_BANK_BRANCH_ID")
	public BigDecimal getBeneficiaryBankBranchId() {
		return beneficiaryBankBranchId;
	}
	public void setBeneficiaryBankBranchId(BigDecimal beneficiaryBankBranchId) {
		this.beneficiaryBankBranchId = beneficiaryBankBranchId;
	}
	
	@Column(name = "BENEFICIARY_BANK_ID")
	public BigDecimal getBeneficiaryBankId() {
		return beneficiaryBankId;
	}
	public void setBeneficiaryBankId(BigDecimal beneficiaryBankId) {
		this.beneficiaryBankId = beneficiaryBankId;
	}
	
	@Column(name = "BENEFICIARY_BANK_COUNTRY_ID")
	public BigDecimal getBeneficiaryBankCountryId() {
		return beneficiaryBankCountryId;
	}
	public void setBeneficiaryBankCountryId(BigDecimal beneficiaryBankCountryId) {
		this.beneficiaryBankCountryId = beneficiaryBankCountryId;
	}

	@Column(name = "BENEFICIARY_BANK_SWIFT")
	public String getBeneficiaryBankSwift() {
		return beneficiaryBankSwift;
	}
	public void setBeneficiaryBankSwift(String beneficiaryBankSwift) {
		this.beneficiaryBankSwift = beneficiaryBankSwift;
	}
	
	@Column(name = "BENEFICIARY_BRANCH_CITY_ID")
	public BigDecimal getBeneficiaryBranchCityId() {
		return beneficiaryBranchCityId;
	}
	public void setBeneficiaryBranchCityId(BigDecimal beneficiaryBranchCityId) {
		this.beneficiaryBranchCityId = beneficiaryBranchCityId;
	}
	
	@Column(name = "BENEFICIARY_BRANCH_DISTRICT_ID")
	public BigDecimal getBeneficiaryBranchDistrictId() {
		return beneficiaryBranchDistrictId;
	}
	public void setBeneficiaryBranchDistrictId(BigDecimal beneficiaryBranchDistrictId) {
		this.beneficiaryBranchDistrictId = beneficiaryBranchDistrictId;
	}
	
	@Column(name = "BENEFICIARY_BRANCH_STATE_ID")
	public BigDecimal getBeneficiaryBranchStateId() {
		return beneficiaryBranchStateId;
	}
	public void setBeneficiaryBranchStateId(BigDecimal beneficiaryBranchStateId) {
		this.beneficiaryBranchStateId = beneficiaryBranchStateId;
	}
	
	@Column(name = "BENEFICIARY_FIFTH_NAME")
	public String getBeneficiaryFifthName() {
		return beneficiaryFifthName;
	}
	public void setBeneficiaryFifthName(String beneficiaryFifthName) {
		this.beneficiaryFifthName = beneficiaryFifthName;
	}

	@Column(name="BENEFICARY_ACCOUNT_SEQ_ID")
	public BigDecimal getBeneficiaryAccountSeqId() {
		return beneficiaryAccountSeqId;
	}
	public void setBeneficiaryAccountSeqId(BigDecimal beneficiaryAccountSeqId) {
		this.beneficiaryAccountSeqId = beneficiaryAccountSeqId;
	}

	@Column(name="BENEFICARY_RELATIONSHIP_SEQ_ID")
	public BigDecimal getBeneficiaryRelationShipSeqId() {
		return beneficiaryRelationShipSeqId;
	}
	public void setBeneficiaryRelationShipSeqId(BigDecimal beneficiaryRelationShipSeqId) {
		this.beneficiaryRelationShipSeqId = beneficiaryRelationShipSeqId;
	}
	

}
