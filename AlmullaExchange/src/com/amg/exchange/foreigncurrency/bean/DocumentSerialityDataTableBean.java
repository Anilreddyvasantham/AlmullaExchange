package com.amg.exchange.foreigncurrency.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DocumentSerialityDataTableBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private BigDecimal documentSerialityId;
	private BigDecimal docCountry;
	private String countryName;
	private BigDecimal docCompany;
	private String companyName;
	private BigDecimal exDocument;
	private BigDecimal documentCode;
	private BigDecimal docFinancialYear;
	private BigDecimal financialYear;
	private BigDecimal startNo;
	private BigDecimal endNo;
	private BigDecimal nextNo;
	private String isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;
	private Boolean remarksCheck = false;
	private Boolean activateRecordCheck = false;

	 private Boolean booCheckDelete = false;
	 

	

	private String dynamicLabelForActivateDeactivate;

	public DocumentSerialityDataTableBean() {
	}

	
	public DocumentSerialityDataTableBean(BigDecimal documentSerialityId, BigDecimal docCountry, String countryName, BigDecimal docCompany, String companyName, BigDecimal exDocument, BigDecimal documentCode, BigDecimal docFinancialYear, BigDecimal financialYear, BigDecimal startNo,
			BigDecimal endNo, BigDecimal nextNo, String isActive, String createdBy, Date createdDate, String modifiedBy, Date modifiedDate, String approvedBy, Date approvedDate, String remarks, Boolean remarksCheck, Boolean activateRecordCheck, Boolean booCheckDelete,
			String dynamicLabelForActivateDeactivate) {
		this.documentSerialityId = documentSerialityId;
		this.docCountry = docCountry;
		this.countryName = countryName;
		this.docCompany = docCompany;
		this.companyName = companyName;
		this.exDocument = exDocument;
		this.documentCode = documentCode;
		this.docFinancialYear = docFinancialYear;
		this.financialYear = financialYear;
		this.startNo = startNo;
		this.endNo = endNo;
		this.nextNo = nextNo;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.remarks = remarks;
		this.remarksCheck = remarksCheck;
		this.activateRecordCheck = activateRecordCheck;
		this.booCheckDelete = booCheckDelete;
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}


	public Boolean getRemarksCheck() {
		return remarksCheck;
	}

	public void setRemarksCheck(Boolean remarksCheck) {
		this.remarksCheck = remarksCheck;
	}


	public BigDecimal getDocumentSerialityId() {
		return documentSerialityId;
	}

	public void setDocumentSerialityId(BigDecimal documentSerialityId) {
		this.documentSerialityId = documentSerialityId;
	}

	public BigDecimal getDocCountry() {
		return docCountry;
	}

	public void setDocCountry(BigDecimal docCountry) {
		this.docCountry = docCountry;
	}

	public BigDecimal getDocCompany() {
		return docCompany;
	}

	public void setDocCompany(BigDecimal docCompany) {
		this.docCompany = docCompany;
	}

	public BigDecimal getExDocument() {
		return exDocument;
	}

	public void setExDocument(BigDecimal exDocument) {
		this.exDocument = exDocument;
	}

	public BigDecimal getDocFinancialYear() {
		return docFinancialYear;
	}

	public void setDocFinancialYear(BigDecimal docFinancialYear) {
		this.docFinancialYear = docFinancialYear;
	}

	public BigDecimal getStartNo() {
		return startNo;
	}

	public void setStartNo(BigDecimal startNo) {
		this.startNo = startNo;
	}

	public BigDecimal getEndNo() {
		return endNo;
	}

	public void setEndNo(BigDecimal endNo) {
		this.endNo = endNo;
	}

	public BigDecimal getNextNo() {
		return nextNo;
	}

	public void setNextNo(BigDecimal nextNo) {
		this.nextNo = nextNo;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}

	public void setDynamicLabelForActivateDeactivate(String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}

	public Boolean getActivateRecordCheck() {
		return activateRecordCheck;
	}

	public void setActivateRecordCheck(Boolean activateRecordCheck) {
		this.activateRecordCheck = activateRecordCheck;
	}

	public Boolean getBooCheckDelete() {
		return booCheckDelete;
	}

	public void setBooCheckDelete(Boolean booCheckDelete) {
		this.booCheckDelete = booCheckDelete;
	}


	public String getCountryName() {
		return countryName;
	}


	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}


	public String getCompanyName() {
		return companyName;
	}


	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


	public BigDecimal getDocumentCode() {
		return documentCode;
	}


	public void setDocumentCode(BigDecimal documentCode) {
		this.documentCode = documentCode;
	}


	public BigDecimal getFinancialYear() {
		return financialYear;
	}


	public void setFinancialYear(BigDecimal financialYear) {
		this.financialYear = financialYear;
	}
	
	

}
