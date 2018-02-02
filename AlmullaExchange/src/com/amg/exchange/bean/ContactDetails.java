package com.amg.exchange.bean;

import java.math.BigDecimal;
import java.util.Date;

public class ContactDetails {
	
	private BigDecimal contactType = null;
	private String strContactType = null;
	
	private BigDecimal country = null;
	private String strCountry = null;
	
	private BigDecimal state = null;
	private String strState = null;
	
	private BigDecimal district = null;
	private String strDistrict = null;
	
	private BigDecimal city = null;
	private String strCity = null;
	
	private String localArea = null;
	private String street = null;
	private String telephone = null;
	private String flat = null;
	private String block = null;
	private String buildingNo=null;
	
	private BigDecimal pkContactDetails = null;
	
	private String createdBy = null;
	private Date creationDate = null;
	 private String mobileContact;
	 private String telephoneCode;
	
	
	public ContactDetails() {
		
	}

    public ContactDetails(BigDecimal contactType, String strContactType,
			BigDecimal country, String strCountry, BigDecimal state,
			String strState, BigDecimal district, String strDistrict,
			BigDecimal city, String strCity, String localArea, String street,
			String telephone, String flat, String block, String buildingNo,
			BigDecimal pkContactDetails, String createdBy, Date creationDate,
			String mobileContact, String telephoneCode) {
		super();
		this.contactType = contactType;
		this.strContactType = strContactType;
		this.country = country;
		this.strCountry = strCountry;
		this.state = state;
		this.strState = strState;
		this.district = district;
		this.strDistrict = strDistrict;
		this.city = city;
		this.strCity = strCity;
		this.localArea = localArea;
		this.street = street;
		this.telephone = telephone;
		this.flat = flat;
		this.block = block;
		this.buildingNo = buildingNo;
		this.pkContactDetails = pkContactDetails;
		this.createdBy = createdBy;
		this.creationDate = creationDate;
		this.mobileContact = mobileContact;
		this.telephoneCode = telephoneCode;
	}

   public BigDecimal getContactType() {
		return contactType;
	}
	public void setContactType(BigDecimal contactType) {
		this.contactType = contactType;
	}

	public String getStrContactType() {
		return strContactType;
	}
	public void setStrContactType(String strContactType) {
		this.strContactType = strContactType;
	}

	public BigDecimal getCountry() {
		return country;
	}
	public void setCountry(BigDecimal country) {
		this.country = country;
	}

	public String getStrCountry() {
		return strCountry;
	}
	public void setStrCountry(String strCountry) {
		this.strCountry = strCountry;
	}

	public BigDecimal getState() {
		return state;
	}
	public void setState(BigDecimal state) {
		this.state = state;
	}

	public String getStrState() {
		return strState;
	}
	public void setStrState(String strState) {
		this.strState = strState;
	}

	public BigDecimal getDistrict() {
		return district;
	}
	public void setDistrict(BigDecimal district) {
		this.district = district;
	}

	public String getStrDistrict() {
		return strDistrict;
	}
	public void setStrDistrict(String strDistrict) {
		this.strDistrict = strDistrict;
	}

	public BigDecimal getCity() {
		return city;
	}
	public void setCity(BigDecimal city) {
		this.city = city;
	}

	public String getStrCity() {
		return strCity;
	}
	public void setStrCity(String strCity) {
		this.strCity = strCity;
	}

	public String getLocalArea() {
		return localArea;
	}
	public void setLocalArea(String localArea) {
		this.localArea = localArea;
	}

	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}

	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getFlat() {
		return flat;
	}
	public void setFlat(String flat) {
		this.flat = flat;
	}

	public String getBlock() {
		return block;
	}
	public void setBlock(String block) {
		this.block = block;
	}

	public BigDecimal getPkContactDetails() {
		return pkContactDetails;
	}
	public void setPkContactDetails(BigDecimal pkContactDetails) {
		this.pkContactDetails = pkContactDetails;
	}
	
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getBuildingNo() {
		return buildingNo;
	}

	public void setBuildingNo(String buildingNo) {
		this.buildingNo = buildingNo;
	}

	public String getMobileContact() {
		return mobileContact;
	}

	public void setMobileContact(String mobileContact) {
		this.mobileContact = mobileContact;
	}

	public String getTelephoneCode() {
		return telephoneCode;
	}

	public void setTelephoneCode(String telephoneCode) {
		this.telephoneCode = telephoneCode;
	}
	
	
}
