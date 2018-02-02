package com.amg.exchange.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class CorporateRegistrationReport implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String crNumber;
	private String companyName;
	private String companyNameLocal;
	private Date companyRegDate;
	private String location;
	private String paidUpCapital;
	private String natureOfBusiness;
	private String primaryObjective;
	private String secondaryObjective;
	private String ofcState;
	private String ofcDistrict;
	private String ofcCity;
	private String ofcBlockNo;
	private String ofcStreetNo;
	private String ofcBuildNo;
	private String officeNo;
	private String ofcLocalArea;
	private BigDecimal ofcTeleNo;
	private String ofcMobileNo;
	private String artical;
	private String level;
	private String logoPath;
	private String subReport;
	private String turnOver;
	private Date auditStatementDate;
	private String auditGrossIncome;
	private String ofcTelephone;
	private String ofcEmail;
	private String currentDate;
	private String branchName;
	private String signature;
	private String customerRef;
	
	private List<CorporateCompanyIdentityDocument> companyIdDocumentList;
	private List<CorporatePartnerDetails> partnerDetailsList;
	
	
	
	
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public Date getAuditStatementDate() {
		return auditStatementDate;
	}
	public String getAuditGrossIncome() {
		return auditGrossIncome;
	}
	public String getOfcTelephone() {
		return ofcTelephone;
	}
	public String getOfcEmail() {
		return ofcEmail;
	}
	public String getCurrentDate() {
		return currentDate;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setAuditStatementDate(Date auditStatementDate) {
		this.auditStatementDate = auditStatementDate;
	}
	public void setAuditGrossIncome(String auditGrossIncome) {
		this.auditGrossIncome = auditGrossIncome;
	}
	public void setOfcTelephone(String ofcTelephone) {
		this.ofcTelephone = ofcTelephone;
	}
	public void setOfcEmail(String ofcEmail) {
		this.ofcEmail = ofcEmail;
	}
	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getTurnOver() {
		return turnOver;
	}
	public void setTurnOver(String turnOver) {
		this.turnOver = turnOver;
	}
	public String getCrNumber() {
		return crNumber;
	}
	public String getCompanyName() {
		return companyName;
	}
	public String getCompanyNameLocal() {
		return companyNameLocal;
	}
	public Date getCompanyRegDate() {
		return companyRegDate;
	}
	public String getLocation() {
		return location;
	}
	public String getPaidUpCapital() {
		return paidUpCapital;
	}

	public String getNatureOfBusiness() {
		return natureOfBusiness;
	}
	public String getPrimaryObjective() {
		return primaryObjective;
	}
	public String getSecondaryObjective() {
		return secondaryObjective;
	}
	public String getOfcState() {
		return ofcState;
	}
	public String getOfcDistrict() {
		return ofcDistrict;
	}
	public String getOfcCity() {
		return ofcCity;
	}

	public String getOfcLocalArea() {
		return ofcLocalArea;
	}
	public BigDecimal getOfcTeleNo() {
		return ofcTeleNo;
	}
	public String getOfcMobileNo() {
		return ofcMobileNo;
	}
	public String getArtical() {
		return artical;
	}
	public String getLevel() {
		return level;
	}
	public String getLogoPath() {
		return logoPath;
	}
	public String getSubReport() {
		return subReport;
	}
	public List<CorporateCompanyIdentityDocument> getCompanyIdDocumentList() {
		return companyIdDocumentList;
	}
	public List<CorporatePartnerDetails> getPartnerDetailsList() {
		return partnerDetailsList;
	}
	public void setCrNumber(String crNumber) {
		this.crNumber = crNumber;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public void setCompanyNameLocal(String companyNameLocal) {
		this.companyNameLocal = companyNameLocal;
	}
	public void setCompanyRegDate(Date companyRegDate) {
		this.companyRegDate = companyRegDate;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public void setPaidUpCapital(String paidUpCapital) {
		this.paidUpCapital = paidUpCapital;
	}

	public void setNatureOfBusiness(String natureOfBusiness) {
		this.natureOfBusiness = natureOfBusiness;
	}
	public void setPrimaryObjective(String primaryObjective) {
		this.primaryObjective = primaryObjective;
	}
	public void setSecondaryObjective(String secondaryObjective) {
		this.secondaryObjective = secondaryObjective;
	}
	public void setOfcState(String ofcState) {
		this.ofcState = ofcState;
	}
	public void setOfcDistrict(String ofcDistrict) {
		this.ofcDistrict = ofcDistrict;
	}
	public void setOfcCity(String ofcCity) {
		this.ofcCity = ofcCity;
	}

	
	public String getOfcBlockNo() {
		return ofcBlockNo;
	}
	public String getOfcStreetNo() {
		return ofcStreetNo;
	}
	public String getOfcBuildNo() {
		return ofcBuildNo;
	}
	public String getOfficeNo() {
		return officeNo;
	}
	public void setOfcBlockNo(String ofcBlockNo) {
		this.ofcBlockNo = ofcBlockNo;
	}
	public void setOfcStreetNo(String ofcStreetNo) {
		this.ofcStreetNo = ofcStreetNo;
	}
	public void setOfcBuildNo(String ofcBuildNo) {
		this.ofcBuildNo = ofcBuildNo;
	}
	public void setOfficeNo(String officeNo) {
		this.officeNo = officeNo;
	}
	public void setOfcLocalArea(String ofcLocalArea) {
		this.ofcLocalArea = ofcLocalArea;
	}
	public void setOfcTeleNo(BigDecimal ofcTeleNo) {
		this.ofcTeleNo = ofcTeleNo;
	}
	public void setOfcMobileNo(String ofcMobileNo) {
		this.ofcMobileNo = ofcMobileNo;
	}
	public void setArtical(String artical) {
		this.artical = artical;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}
	public void setSubReport(String subReport) {
		this.subReport = subReport;
	}
	public void setCompanyIdDocumentList(
			List<CorporateCompanyIdentityDocument> companyIdDocumentList) {
		this.companyIdDocumentList = companyIdDocumentList;
	}
	public void setPartnerDetailsList(
			List<CorporatePartnerDetails> partnerDetailsList) {
		this.partnerDetailsList = partnerDetailsList;
	}
	public String getCustomerRef() {
		return customerRef;
	}
	public void setCustomerRef(String customerRef) {
		this.customerRef = customerRef;
	}
	
	
	
}
