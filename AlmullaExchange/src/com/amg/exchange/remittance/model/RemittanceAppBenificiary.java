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
import com.amg.exchange.treasury.model.Document;

/*******************************************************************************************************************

File		: RemittanceAppBenificiary.java

Project	: AlmullaExchange

Package	: com.amg.exchange.remittance.model

Created	:	
				Date	: 23-Jan-2015
				By		: Nazish Ehsan Hashmi
				Revision:

 Last Change:
				Date	:  23-Jan-2015
				By		: Nazish Ehsan Hashmi
				Revision:

Description: TODO 

********************************************************************************************************************/
@Entity
@Table(name = "EX_APPL_BENE")
public class RemittanceAppBenificiary implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal remittanceAppBenificiaryId;
	private Document exDocument;
	private CompanyMaster fsCompanyMaster;
	private UserFinancialYear exUserFinancialYear;
	private BigDecimal companyCode;
	private BigDecimal documentCode;
	private BigDecimal beneficiaryId;
	private String beneficiaryName;
	private String beneficiaryBank;
	private String beneficiaryBranch;
	private String beneficiaryAccountNo;
	private String beneficiarySwiftAddr1;
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
	private RemittanceApplication exRemittanceAppfromBenfi;
	private BigDecimal beneficiaryBankCountryId;
	private BigDecimal beneficiaryBankId;
	private BigDecimal beneficiaryBankBranchId;
	private BigDecimal beneficiaryBranchStateId;
	private BigDecimal beneficiaryBranchDistrictId;
	private BigDecimal beneficiaryBranchCityId;
	private String beneficiaryBankSwift;
	private String beneficiaryFifthName;
	private BigDecimal beneficiarySwiftBank1Id;
	private BigDecimal beneficiarySwiftBank2Id;
	private BigDecimal beneficiaryAccountSeqId;
	private BigDecimal beneficiaryRelationShipSeqId;
	private BigDecimal documentNo;
	private String beneficiaryTelephoneNumber;
	
	public RemittanceAppBenificiary() {
	}

	public RemittanceAppBenificiary(BigDecimal remittanceAppBenificiaryId) {
		this.remittanceAppBenificiaryId = remittanceAppBenificiaryId;
	}

	public RemittanceAppBenificiary(BigDecimal remittanceAppBenificiaryId,
			Document exDocument, CompanyMaster fsCompanyMaster,
			UserFinancialYear exUserFinancialYear, BigDecimal companyCode,
			BigDecimal documentCode, BigDecimal beneficiaryId,
			String beneficiaryName, String beneficiaryBank,
			String beneficiaryBranch, String beneficiaryAccountNo,
			String beneficiarySwiftAddr1, String beneficiarySwiftAddr2,
			String beneficiarySwiftBank1, String beneficiarySwiftBank2,
			String createdBy, Date createdDate, String modifiedBy,
			Date modifiedDate, String isactive, String beneficiaryFirstName,
			String beneficiarySecondName, String beneficiaryThirdName,
			String beneficiaryFourthName,
			RemittanceApplication exRemittanceAppfromBenfi,
			BigDecimal beneficiaryBankCountryId, BigDecimal beneficiaryBankId,
			BigDecimal beneficiaryBankBranchId,
			BigDecimal beneficiaryBranchStateId,
			BigDecimal beneficiaryBranchDistrictId,
			BigDecimal beneficiaryBranchCityId, String beneficiaryBankSwift,
			String beneficiaryFifthName, BigDecimal beneficiarySwiftBank1Id,
			BigDecimal beneficiarySwiftBank2Id,
			BigDecimal beneficiaryAccountSeqId,
			BigDecimal beneficiaryRelationShipSeqId, BigDecimal documentNo,
			String beneficiaryTelephoneNumber) {
		super();
		this.remittanceAppBenificiaryId = remittanceAppBenificiaryId;
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
		this.exRemittanceAppfromBenfi = exRemittanceAppfromBenfi;
		this.beneficiaryBankCountryId = beneficiaryBankCountryId;
		this.beneficiaryBankId = beneficiaryBankId;
		this.beneficiaryBankBranchId = beneficiaryBankBranchId;
		this.beneficiaryBranchStateId = beneficiaryBranchStateId;
		this.beneficiaryBranchDistrictId = beneficiaryBranchDistrictId;
		this.beneficiaryBranchCityId = beneficiaryBranchCityId;
		this.beneficiaryBankSwift = beneficiaryBankSwift;
		this.beneficiaryFifthName = beneficiaryFifthName;
		this.beneficiarySwiftBank1Id = beneficiarySwiftBank1Id;
		this.beneficiarySwiftBank2Id = beneficiarySwiftBank2Id;
		this.beneficiaryAccountSeqId = beneficiaryAccountSeqId;
		this.beneficiaryRelationShipSeqId = beneficiaryRelationShipSeqId;
		this.documentNo = documentNo;
		this.beneficiaryTelephoneNumber = beneficiaryTelephoneNumber;
	}

	@Id
	@GeneratedValue(generator="ex_remittance_app_beni_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_remittance_app_beni_seq",sequenceName="EX_REMITTANCE_APP_BENI_SEQ",allocationSize=1)
	@Column(name = "REMITTANCE_APP_BENIFICIARY_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getRemittanceAppBenificiaryId() {
		return this.remittanceAppBenificiaryId;
	}
	public void setRemittanceAppBenificiaryId(BigDecimal remittanceAppBenificiaryId) {
		this.remittanceAppBenificiaryId = remittanceAppBenificiaryId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DOCUMENT_ID")
	public Document getExDocument() {
		return this.exDocument;
	}
	public void setExDocument(Document exDocument) {
		this.exDocument = exDocument;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPANY_ID")
	public CompanyMaster getFsCompanyMaster() {
		return this.fsCompanyMaster;
	}
	public void setFsCompanyMaster(CompanyMaster fsCompanyMaster) {
		this.fsCompanyMaster = fsCompanyMaster;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DOCUMENT_FINANCE_YEAR_ID")
	public UserFinancialYear getExUserFinancialYear() {
		return this.exUserFinancialYear;
	}
	public void setExUserFinancialYear(UserFinancialYear exUserFinancialYear) {
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

/*	@Column(name = "BENEFICIARY_INTER_BANK1", length = 300)
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REMITTANCE_APPLICATION_ID")
	public RemittanceApplication getExRemittanceAppfromBenfi() {
		return exRemittanceAppfromBenfi;
	}
	public void setExRemittanceAppfromBenfi(RemittanceApplication exRemittanceAppfromBenfi) {
		this.exRemittanceAppfromBenfi = exRemittanceAppfromBenfi;
	}
	
	@Column(name = "BENEFICIARY_BANK_COUNTRY_ID")
	public BigDecimal getBeneficiaryBankCountryId() {
		return beneficiaryBankCountryId;
	}
	public void setBeneficiaryBankCountryId(BigDecimal beneficiaryBankCountryId) {
		this.beneficiaryBankCountryId = beneficiaryBankCountryId;
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
	
	@Column(name = "BENEFICIARY_BRANCH_STATE_ID")
	public BigDecimal getBeneficiaryBranchStateId() {
		return beneficiaryBranchStateId;
	}
	public void setBeneficiaryBranchStateId(BigDecimal beneficiaryBranchStateId) {
		this.beneficiaryBranchStateId = beneficiaryBranchStateId;
	}

	@Column(name = "BENEFICIARY_BRANCH_DISTRICT_ID")
	public BigDecimal getBeneficiaryBranchDistrictId() {
		return beneficiaryBranchDistrictId;
	}
	public void setBeneficiaryBranchDistrictId(BigDecimal beneficiaryBranchDistrictId) {
		this.beneficiaryBranchDistrictId = beneficiaryBranchDistrictId;
	}

	@Column(name = "BENEFICIARY_BRANCH_CITY_ID")
	public BigDecimal getBeneficiaryBranchCityId() {
		return beneficiaryBranchCityId;
	}
	public void setBeneficiaryBranchCityId(BigDecimal beneficiaryBranchCityId) {
		this.beneficiaryBranchCityId = beneficiaryBranchCityId;
	}

	@Column(name = "BENEFICIARY_BANK_SWIFT")
	public String getBeneficiaryBankSwift() {
		return beneficiaryBankSwift;
	}
	public void setBeneficiaryBankSwift(String beneficiaryBankSwift) {
		this.beneficiaryBankSwift = beneficiaryBankSwift;
	}

	@Column(name = "BENEFICIARY_FIFTH_NAME")
	public String getBeneficiaryFifthName() {
		return beneficiaryFifthName;
	}
	public void setBeneficiaryFifthName(String beneficiaryFifthName) {
		this.beneficiaryFifthName = beneficiaryFifthName;
	}

	@Column(name="BENEFICIARY_SWIFT_BANK1_ID")
	public BigDecimal getBeneficiarySwiftBank1Id() {
		return beneficiarySwiftBank1Id;
	}
	public void setBeneficiarySwiftBank1Id(BigDecimal beneficiarySwiftBank1Id) {
		this.beneficiarySwiftBank1Id = beneficiarySwiftBank1Id;
	}

	@Column(name="BENEFICIARY_SWIFT_BANK2_ID")
	public BigDecimal getBeneficiarySwiftBank2Id() {
		return beneficiarySwiftBank2Id;
	}
	public void setBeneficiarySwiftBank2Id(BigDecimal beneficiarySwiftBank2Id) {
		this.beneficiarySwiftBank2Id = beneficiarySwiftBank2Id;
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
	
	@Column(name="DOCUMENT_NO")
	public BigDecimal getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}

	@Column(name="BENEFICIARY_TELEPHONE_NUMBER")
	public String getBeneficiaryTelephoneNumber() {
		return beneficiaryTelephoneNumber;
	}
	public void setBeneficiaryTelephoneNumber(String beneficiaryTelephoneNumber) {
		this.beneficiaryTelephoneNumber = beneficiaryTelephoneNumber;
	}
	
}
