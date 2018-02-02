package com.amg.exchange.remittance.model;

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

/*******************************************************************************************************************
 * File : SwiftMaster.java
 * 
 * Project : AlmullaExchange
 * 
 * Package : com.amg.exchange.remittance.model
 * 
 * Created : Date : 10-Mar-2015 10:00:47 am By : Nagarjuna Atla Revision:
 * 
 * Last Change: Date : By :
 * 
 * Description: TODO
 ********************************************************************************************************************/
@Entity
@Table(name = "EX_SWIFT_MASTER")
public class SwiftMaster implements Serializable {

	private static final long serialVersionUID = -7913857411388570542L;

	private BigDecimal swiftId;
	private String swiftCountryCode;
	private BigDecimal swiftBankId;
	private String swiftLocation;
	private String swiftBranchCode;
	private String bankCode;
	private String bankName;

	private String cityName;
	private String region;
	
	private String swiftBIC;
	private String chipsUID;
	private String acronymId;
	private String fedwireId;
	private String abaId;

	private String firstAddress;
	

	private String secondAddress;
	private String thirdAddress;
	private String isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;
  public SwiftMaster(){
	  
  }
	public SwiftMaster(BigDecimal swiftId, String swiftCountryCode,
			BigDecimal swiftBankId, String swiftLocation,
			String swiftBranchCode, String bankCode, String bankName,
			String cityName, String region, String swiftBIC,
			String chipsUID, String acronymId, String fedwireId, String abaId,
			String firstAddress, String secondAddress, String thirdAddress, String isActive,
			String createdBy, Date createdDate, String modifiedBy,
			Date modifiedDate,String approvedBy,Date approvedDate,String remarks) {
		super();
		this.swiftId = swiftId;
		this. swiftCountryCode=swiftCountryCode;
		this.swiftBankId = swiftBankId;
		this.swiftLocation = swiftLocation;
		this.swiftBranchCode=swiftBranchCode;
		this. bankCode=bankCode;
		this.bankName = bankName;
		this.cityName = cityName;
		this.region = region;
		this.swiftBIC = swiftBIC;
		this.chipsUID = chipsUID;
		this.acronymId = acronymId;
		this.fedwireId = fedwireId;
		this.abaId = abaId;
		this.firstAddress = firstAddress;
		this.secondAddress = secondAddress;
		this.thirdAddress = thirdAddress;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.approvedBy=approvedBy; 
		this.approvedDate=approvedDate;
		this.remarks=remarks;
	      
	}

	@Id
	@GeneratedValue(generator = "ex_swift_master_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ex_swift_master_seq", sequenceName = "EX_SWIFT_MASTER_SEQ", allocationSize = 1)
	@Column(name = "SWIFT_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getSwiftId() {
		return swiftId;
	}

	public void setSwiftId(BigDecimal swiftId) {
		this.swiftId = swiftId;
	}

	 
	@Column(name = "BANK_ID")
	public BigDecimal getSwiftBankId() {
		return swiftBankId;
	}

	public void setSwiftBankId(BigDecimal swiftBankId) {
		this.swiftBankId = swiftBankId;
	}

	@Column(name = "SWIFT_LOCATION")
	public String getSwiftLocation() {
		return swiftLocation;
	}

	public void setSwiftLocation(String swiftLocation) {
		this.swiftLocation = swiftLocation;
	}

 

	@Column(name = "BANK_NAME")
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Column(name = "CITY_NAME")
	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	@Column(name = "REGION")
	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	@Column(name = "SWIFT_BIC")
	public String getSwiftBIC() {
		return swiftBIC;
	}

	public void setSwiftBIC(String swiftBIC) {
		this.swiftBIC = swiftBIC;
	}

	@Column(name = "CHIPS_UID")
	public String getChipsUID() {
		return chipsUID;
	}

	public void setChipsUID(String chipsUID) {
		this.chipsUID = chipsUID;
	}

	@Column(name = "ACRONYM_ID")
	public String getAcronymId() {
		return acronymId;
	}

	public void setAcronymId(String acronymId) {
		this.acronymId = acronymId;
	}

	@Column(name = "FEDWIRE_ID")
	public String getFedwireId() {
		return fedwireId;
	}

	public void setFedwireId(String fedwireId) {
		this.fedwireId = fedwireId;
	}

	@Column(name = "ABA_ID")
	public String getAbaId() {
		return abaId;
	}

	public void setAbaId(String abaId) {
		this.abaId = abaId;
	}

	 
	@Column(name = "ADDRESS1")
	public String getFirstAddress() {
		return firstAddress;
	}
	public void setFirstAddress(String firstAddress) {
		this.firstAddress = firstAddress;
	}
	@Column(name = "ADDRESS2")
	public String getSecondAddress() {
		return secondAddress;
	}
	public void setSecondAddress(String secondAddress) {
		this.secondAddress = secondAddress;
	}
	@Column(name = "ADDRESS3")
	public String getThirdAddress() {
		return thirdAddress;
	}
	public void setThirdAddress(String thirdAddress) {
		this.thirdAddress = thirdAddress;
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

	@Column(name = "ISACTIVE")
	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	 
	@Column(name = "APPROVED_BY")
	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	@Column(name = "APPROVED_DATE")
	public Date getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
	@Column(name = "REMARKS")
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	@Column(name = "SWIFT_COUNTRY")
	public String getSwiftCountryCode() {
		return swiftCountryCode;
	}
	public void setSwiftCountryCode(String swiftCountryCode) {
		this.swiftCountryCode = swiftCountryCode;
	}
	@Column(name = "SWIFT_BRANCH")
	public String getSwiftBranchCode() {
		return swiftBranchCode;
	}
	public void setSwiftBranchCode(String swiftBranchCode) {
		this.swiftBranchCode = swiftBranchCode;
	}
	@Column(name = "SWIFT_BANK")
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	@Override
	public String toString() {
		return "SwiftMaster [swiftId=" + swiftId + ", swiftCountryCode="
				+ swiftCountryCode + ", swiftBankId=" + swiftBankId
				+ ", swiftLocation=" + swiftLocation + ", swiftBranchCode="
				+ swiftBranchCode + ", bankCode=" + bankCode + ", bankName="
				+ bankName + ", cityName=" + cityName + ", region=" + region
				+ ", swiftBIC=" + swiftBIC + ", chipsUID=" + chipsUID
				+ ", acronymId=" + acronymId + ", fedwireId=" + fedwireId
				+ ", abaId=" + abaId + ", firstAddress=" + firstAddress
				+ ", secondAddress=" + secondAddress + ", thirdAddress="
				+ thirdAddress + ", isActive=" + isActive + ", createdBy="
				+ createdBy + ", createdDate=" + createdDate + ", modifiedBy="
				+ modifiedBy + ", modifiedDate=" + modifiedDate
				+ ", approvedBy=" + approvedBy + ", approvedDate="
				+ approvedDate + ", remarks=" + remarks + "]";
	}
	
	
	

}
