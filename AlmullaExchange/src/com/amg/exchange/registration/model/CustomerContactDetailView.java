package com.amg.exchange.registration.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/*
 * Author Rahamathali Shaik
*/
@Entity
@Table(name= "VW_EX_CUSTOMER_CONTACT_INFO")
public class CustomerContactDetailView  implements Serializable{

	
	private BigDecimal customerId;
	private BigDecimal contactDetailId;
	private BigDecimal contactTypeId;
	private String alterEmailId;
	private BigDecimal contactStateId;
	private BigDecimal contactDistrictId;
	private BigDecimal contactCityId;
	private String contactArea;
	private String contactBlockNo;
	private String contactStreet;
	private String contactFlat;
	private String contactTelephone;
	private String contactMobileNo;
	private String contactApprovedBy;
	private String contactBuildingNo;
	private String componentDescription;
	private String cityName;
	private String stateName;
	private String distcritName;
	private String countryName;
	
	
	
	
	@Column(name = "COUNTRYNAME")
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	@Column(name = "CUSTOMERID")
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	@Id
	@Column(name = "CONTACTDETAILID")
	public BigDecimal getContactDetailId() {
		return contactDetailId;
	}
	public void setContactDetailId(BigDecimal contactDetailId) {
		this.contactDetailId = contactDetailId;
	}
	@Column(name = "CONTACTTYPEID")
	public BigDecimal getContactTypeId() {
		return contactTypeId;
	}
	public void setContactTypeId(BigDecimal contactTypeId) {
		this.contactTypeId = contactTypeId;
	}
	@Column(name = "ALTEREMAILID")
	public String getAlterEmailId() {
		return alterEmailId;
	}
	public void setAlterEmailId(String alterEmailId) {
		this.alterEmailId = alterEmailId;
	}
	@Column(name = "CON_STATEID")
	public BigDecimal getContactStateId() {
		return contactStateId;
	}
	public void setContactStateId(BigDecimal contactStateId) {
		this.contactStateId = contactStateId;
	}
	@Column(name = "CON_DISTRICTID")
	public BigDecimal getContactDistrictId() {
		return contactDistrictId;
	}
	
	public void setContactDistrictId(BigDecimal contactDistrictId) {
		this.contactDistrictId = contactDistrictId;
	}
	@Column(name = "CON_CITYID")
	public BigDecimal getContactCityId() {
		return contactCityId;
	}
	public void setContactCityId(BigDecimal contactCityId) {
		this.contactCityId = contactCityId;
	}
	@Column(name = "CON_AREA")
	public String getContactArea() {
		return contactArea;
	}
	public void setContactArea(String contactArea) {
		this.contactArea = contactArea;
	}
	@Column(name = "CON_BLOCKNO")
	public String getContactBlockNo() {
		return contactBlockNo;
	}
	public void setContactBlockNo(String contactBlockNo) {
		this.contactBlockNo = contactBlockNo;
	}
	@Column(name = "CON_STREET")
	public String getContactStreet() {
		return contactStreet;
	}
	public void setContactStreet(String contactStreet) {
		this.contactStreet = contactStreet;
	}
	@Column(name = "CON_FLAT")
	public String getContactFlat() {
		return contactFlat;
	}
	public void setContactFlat(String contactFlat) {
		this.contactFlat = contactFlat;
	}
	@Column(name = "CON_TELEPHONE")
	public String getContactTelephone() {
		return contactTelephone;
	}
	public void setContactTelephone(String contactTelephone) {
		this.contactTelephone = contactTelephone;
	}
	@Column(name = "CON_MOBILENO")
	public String getContactMobileNo() {
		return contactMobileNo;
	}
	public void setContactMobileNo(String contactMobileNo) {
		this.contactMobileNo = contactMobileNo;
	}
	@Column(name = "CON_APPROVEDBY")
	public String getContactApprovedBy() {
		return contactApprovedBy;
	}
	public void setContactApprovedBy(String contactApprovedBy) {
		this.contactApprovedBy = contactApprovedBy;
	}
	@Column(name = "CON_BUILDINGNO")
	public String getContactBuildingNo() {
		return contactBuildingNo;
	}
	public void setContactBuildingNo(String contactBuildingNo) {
		this.contactBuildingNo = contactBuildingNo;
	}
	@Column(name = "COMPONENTDESC")
	public String getComponentDescription() {
		return componentDescription;
	}
	public void setComponentDescription(String componentDescription) {
		this.componentDescription = componentDescription;
	}
	@Column(name = "CITYNAME")
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	@Column(name = "STATENAME")
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	@Column(name = "DISTRICTNAME")
	public String getDistcritName() {
		return distcritName;
	}
	public void setDistcritName(String distcritName) {
		this.distcritName = distcritName;
	}
	
	
	
	
	
}
