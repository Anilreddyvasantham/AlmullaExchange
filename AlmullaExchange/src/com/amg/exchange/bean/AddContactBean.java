package com.amg.exchange.bean;

import java.math.BigDecimal;

import javax.servlet.http.Part;

public class AddContactBean {

	private String contType;
	
	private String localArea;
	
	private String state;
	
	private String streetNo;
	
	private String blockNo;
	
	private String telephoneNo;
	
	private String flatNo;
	
	private String dist;
	
	private String city;
	
    private Part part;
    
	private String saveOrUpdate;
    private BigDecimal contactDetailIdKey;
    private String dataTableContactTypeValue;
    
    private String stateShow;
	private String districtShow;
    private String cityShow;
    private String contactType;
    
    
	public Part getPart() {
		return part;
	}

	public void setPart(Part part) {
		this.part = part;
	}

	public AddContactBean(String contType,  String localArea, String state,  String streetNo,String blockNo,String flatNo,
			String telephoneNo2, String dist, String city, String saveOrUpdate, BigDecimal contactDetailIdKey, String stateShow, String distShow, String cityShow, String contactType) {
		this.contType = contType;
		this.localArea = localArea;
		this.state = state;
		this.streetNo = streetNo;
		this.blockNo = blockNo;
		this.flatNo = flatNo;
		this.telephoneNo = telephoneNo2;
		this.dist = dist;
		this.city = city;
		this.stateShow = stateShow;
		this.districtShow = distShow;
		this.cityShow = cityShow;
		this.contactType = contactType;
		
		this.saveOrUpdate = saveOrUpdate;
        this.contactDetailIdKey = contactDetailIdKey;
	}

	/**
	 * @return the contType
	 */
	public String getContType() {
		return contType;
	}


	/**
	 * @param contType the contType to set
	 */
	public void setContType(String contType) {
		this.contType = contType;
	}


	/**
	 * @return the localArea
	 */
	public String getLocalArea() {
		return localArea;
	}


	/**
	 * @param localArea the localArea to set
	 */
	public void setLocalArea(String localArea) {
		this.localArea = localArea;
	}


	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}


	/**
	 * @return the streetNo
	 */
	public String getStreetNo() {
		return streetNo;
	}


	/**
	 * @param streetNo the streetNo to set
	 */
	public void setStreetNo(String streetNo) {
		this.streetNo = streetNo;
	}


	/**
	 * @return the blockNo
	 */
	public String getBlockNo() {
		return blockNo;
	}


	/**
	 * @param blockNo the blockNo to set
	 */
	public void setBlockNo(String blockNo) {
		this.blockNo = blockNo;
	}


	/**
	 * @return the telephoneNo
	 */
	public String getTelephoneNo() {
		return telephoneNo;
	}


	/**
	 * @param telephoneNo the telephoneNo to set
	 */
	public void setTelephoneNo(String telephoneNo) {
		this.telephoneNo = telephoneNo;
	}


	/**
	 * @return the flatNo
	 */
	public String getFlatNo() {
		return flatNo;
	}


	/**
	 * @param flatNo the flatNo to set
	 */
	public void setFlatNo(String flatNo) {
		this.flatNo = flatNo;
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
	
	public String getSaveOrUpdate() {
		return saveOrUpdate;
	}

	public void setSaveOrUpdate(String saveOrUpdate) {
		this.saveOrUpdate = saveOrUpdate;
	}

	public BigDecimal getContactDetailIdKey() {
		return contactDetailIdKey;
	}

	public void setContactDetailIdKey(BigDecimal contactDetailIdKey) {
		this.contactDetailIdKey = contactDetailIdKey;
	}

	public String getDataTableContactTypeValue() {
		return dataTableContactTypeValue;
	}

	public void setDataTableContactTypeValue(String dataTableContactTypeValue) {
		this.dataTableContactTypeValue = dataTableContactTypeValue;
	}
	
	public String getDistrictShow() {
		return districtShow;
	}

	public void setDistrictShow(String districtShow) {
		this.districtShow = districtShow;
	}

	public String getCityShow() {
		return cityShow;
	}

	public void setCityShow(String cityShow) {
		this.cityShow = cityShow;
	}

	public String getStateShow() {
		return stateShow;
	}

	public void setStateShow(String stateShow) {
		this.stateShow = stateShow;
	}

	public String getContactType() {
		return contactType;
	}

	public void setContactType(String contactType) {
		this.contactType = contactType;
	}


}
