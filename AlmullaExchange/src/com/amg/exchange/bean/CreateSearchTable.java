package com.amg.exchange.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.util.Date;


public class CreateSearchTable implements Serializable {
	
	/**
	 *  
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal idType;
	private String idTypeName;
	private String idNumber;
	private String firstName;
	private String lastName;
	private String middleName;
	private String nationality;
	private String dob;
	private String mob;
	private String email;
	private BigDecimal nationalityId;
	private BigDecimal customerTyId;
	private BigDecimal customerRef;
	private String companyName;
	private String shortName;
	private String sundryDebtorRef;
	private BigDecimal customerPk;
	private String createdBy;
	private String createdDate;
	private Blob blob;
	private String customerName;
	private boolean disableLink;
	private String cust_id;
	
	//speacial Customer
	private int cusTypeIdForSpeclcus;
	private String dynamicLabelActivateDeactivate;
	private String AMLStatus;
	private String currentStatus;
	private String idExpiryDate;
	private String isActive;
	private String idStatus;
	private BigDecimal custIdProofPk;
	private String deactvatedBy;
	private Date deactivatedDate;
	private String remarks;
	private String modifiedDate;
	private String modifiedBy;
	
	
	
	public String getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getIdExpiryDate() {
		return idExpiryDate;
	}
	public void setIdExpiryDate(String idExpiryDate) {
		this.idExpiryDate = idExpiryDate;
	}

	public Blob getBlob() {
		return blob;
	}
	public void setBlob(Blob blob) {
		this.blob = blob;
	}

	public int getCusTypeIdForSpeclcus() {
		return cusTypeIdForSpeclcus;
	}
	public void setCusTypeIdForSpeclcus(int cusTypeIdForSpeclcus) {
		this.cusTypeIdForSpeclcus = cusTypeIdForSpeclcus;
	}

	public BigDecimal getCustomerPk() {
		return customerPk;
	}
	public void setCustomerPk(BigDecimal customerPk) {
		this.customerPk = customerPk;
	}

	public String getSundryDebtorRef() {
		return sundryDebtorRef;
	}
	public void setSundryDebtorRef(String sundryDebtorRef) {
		this.sundryDebtorRef = sundryDebtorRef;
	}

	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public BigDecimal getCustomerRef() {
		return customerRef;
	}
	public void setCustomerRef(BigDecimal customerRef) {
		this.customerRef = customerRef;
	}

	public String getIdTypeName() {
		return idTypeName;
	}
	public void setIdTypeName(String idTypeName) {
		this.idTypeName = idTypeName;
	}

	public BigDecimal getCustomerTyId() {
		return customerTyId;
	}
	public void setCustomerTyId(BigDecimal customerTyId) {
		this.customerTyId = customerTyId;
	}

	public BigDecimal getNationalityId() {
		return nationalityId;
	}
	public void setNationalityId(BigDecimal nationalityId) {
		this.nationalityId = nationalityId;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public BigDecimal getIdType() {
		return idType;
	}
	public void setIdType(BigDecimal idType) {
		this.idType = idType;
	}

	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getMob() {
		return mob;
	}
	public void setMob(String mob) {
		this.mob = mob;
	}

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getCust_id() {
		return cust_id;
	}
	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}

	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getDynamicLabelActivateDeactivate() {
		return dynamicLabelActivateDeactivate;
	}
	public void setDynamicLabelActivateDeactivate(String dynamicLabelActivateDeactivate) {
		this.dynamicLabelActivateDeactivate = dynamicLabelActivateDeactivate;
	}

	public String getCurrentStatus() {
		return currentStatus;
	}
	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}

	public String getAMLStatus() {
		return AMLStatus;
	}
	public void setAMLStatus(String aMLStatus) {
		AMLStatus = aMLStatus;
	}

	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getIdStatus() {
		return idStatus;
	}
	public void setIdStatus(String idStatus) {
		this.idStatus = idStatus;
	}

	public BigDecimal getCustIdProofPk() {
		return custIdProofPk;
	}
	public void setCustIdProofPk(BigDecimal custIdProofPk) {
		this.custIdProofPk = custIdProofPk;
	}

	public Date getDeactivatedDate() {
		return deactivatedDate;
	}
	public void setDeactivatedDate(Date deactivatedDate) {
		this.deactivatedDate = deactivatedDate;
	}

	public String getDeactvatedBy() {
		return deactvatedBy;
	}
	public void setDeactvatedBy(String deactvatedBy) {
		this.deactvatedBy = deactvatedBy;
	}

	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public boolean isDisableLink() {
		return disableLink;
	}
	public void setDisableLink(boolean disableLink) {
		this.disableLink = disableLink;
	}
	
}
