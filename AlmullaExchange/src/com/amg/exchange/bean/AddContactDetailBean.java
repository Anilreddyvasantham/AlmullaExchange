package com.amg.exchange.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.amg.exchange.common.model.CityMasterDesc;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.model.DistrictMasterDesc;
import com.amg.exchange.common.model.StateMasterDesc;



public class AddContactDetailBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String contType;
	
	private String area;
	
	private String country;
	
	private String street;
	
	private String block;
	
	private String tel;
	
	private String flat;
	
    private String state;
    
    private String dist;
    
    private BigDecimal contactTypeId;
    
    private BigDecimal countryId;
    
    private BigDecimal stateId;
    
    private BigDecimal districtId;
    
    private BigDecimal cityId;
    
	private String city;
	
	private boolean modified;
	
	private boolean objStatus;
	
	private BigDecimal contactDetailsId;
	
	private String createdByContact;
    private Date createdDateContact;
    private String buildingNo;
    private String mobileContact;
    
    private String telephoneCode;
	
	private List<CountryMasterDesc> lstCountryList = new ArrayList<CountryMasterDesc>();
	private List<StateMasterDesc> lstStateList = new ArrayList<StateMasterDesc>();
	private List<DistrictMasterDesc> lstDistrictList = new ArrayList<DistrictMasterDesc>();
	private List<CityMasterDesc> lstCityList = new ArrayList<CityMasterDesc>();
	
	
	public AddContactDetailBean() {
	}

	public AddContactDetailBean(String contType, String area, String country, String street,String block, String tel, String flat,String state, String dist, String city, boolean modified, boolean objStatus, BigDecimal contTypeId,
			BigDecimal countryId,BigDecimal stateId,
			BigDecimal distId, BigDecimal cityId, BigDecimal contactDetailsId,String buildingNo) {
		this.contType = contType;
		this.area = area;
		this.country = country;
		this.street = street;
		this.block = block;
		this.tel = tel;
		this.flat = flat;
		this.state = state;
		this.city = city;
		this.dist = dist;
		this.modified = modified;
		this.objStatus = objStatus;
		this.contactTypeId = contTypeId;
		this.countryId = countryId;
		this.stateId = stateId;
		this.districtId = distId;
		this.cityId = cityId;
		this.contactDetailsId = contactDetailsId;
		this.buildingNo = buildingNo;
	}

	public String getCreatedByContact() {
		return createdByContact;
	}

	public void setCreatedByContact(String createdByContact) {
		this.createdByContact = createdByContact;
	}

	public Date getCreatedDateContact() {
		return createdDateContact;
	}

	public void setCreatedDateContact(Date createdDateContact) {
		this.createdDateContact = createdDateContact;
	}

	public BigDecimal getContactTypeId() {
		return contactTypeId;
	}

	public void setContactTypeId(BigDecimal contactTypeId) {
		this.contactTypeId = contactTypeId;
	}

	public BigDecimal getCountryId() {
		return countryId;
	}

	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}

	public BigDecimal getStateId() {
		return stateId;
	}

	public void setStateId(BigDecimal stateId) {
		this.stateId = stateId;
	}

	public BigDecimal getDistrictId() {
		return districtId;
	}

	public void setDistrictId(BigDecimal districtId) {
		this.districtId = districtId;
	}

	public BigDecimal getCityId() {
		return cityId;
	}

	public void setCityId(BigDecimal cityId) {
		this.cityId = cityId;
	}
	

	public boolean isModified() {
		return modified;
	}

	public void setModified(boolean modified) {
		this.modified = modified;
	}

	public boolean isObjstatus() {
		return objStatus;
	}

	public void setObjstatus(boolean objstatus) {
		this.objStatus = objstatus;
	}
	
	public String getDist() {
		return dist;
	}

	public void setDist(String dist) {
		this.dist = dist;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}


	public String getContType() {
		return contType;
	}

	public void setContType(String contType) {
		this.contType = contType;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getFlat() {
		return flat;
	}

	public void setFlat(String flat) {
		this.flat = flat;
	}
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public BigDecimal getContactDetailsId() {
		return contactDetailsId;
	}

	public void setContactDetailsId(BigDecimal contactDetailsId) {
		this.contactDetailsId = contactDetailsId;
	}

	public List<CountryMasterDesc> getLstCountryList() {
		return lstCountryList;
	}

	public void setLstCountryList(List<CountryMasterDesc> lstCountryList) {
		this.lstCountryList = lstCountryList;
	}

	public List<StateMasterDesc> getLstStateList() {
		return lstStateList;
	}

	public void setLstStateList(List<StateMasterDesc> lstStateList) {
		this.lstStateList = lstStateList;
	}

	public List<DistrictMasterDesc> getLstDistrictList() {
		return lstDistrictList;
	}

	public void setLstDistrictList(List<DistrictMasterDesc> lstDistrictList) {
		this.lstDistrictList = lstDistrictList;
	}

	public List<CityMasterDesc> getLstCityList() {
		return lstCityList;
	}

	public void setLstCityList(List<CityMasterDesc> lstCityList) {
		this.lstCityList = lstCityList;
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
