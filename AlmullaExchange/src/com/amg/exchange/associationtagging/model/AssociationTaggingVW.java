package com.amg.exchange.associationtagging.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VW_ASSOCIATION_TAGGING")
public class AssociationTaggingVW implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private BigDecimal customerId;
	private BigDecimal cusRefNum;
	private String civilId;
	private String title;
	private String name;
	private String nameLocal;
	private String mobile;
	private String email;
	private BigDecimal nationality;
	//private String employerName;
	
	private BigDecimal assoCode;
	
		
	
	/*
	private String postOffNum;
	private String pinNum;
	private String postalArea1;
	private String postalArea2;
	private String locArea1;
	private String locArea2;
	private String block2;
	private String residence;
	private String fax;
	private String hpin;
	private String motherMaidenname;
	private String emplType2;
	private String profession1;
	private String profession2;
	private String position1;
	private String position2;*/
	
	private BigDecimal homeCountryId;
	private BigDecimal homeStateId;
	private BigDecimal homeCityId;
	private BigDecimal homeDistrictId;
	
	
	private BigDecimal localCountryId;
	private BigDecimal localStateId;
	private BigDecimal localCityId;
	private BigDecimal localDistrictId;
	
	//private BigDecimal occupationId;
	
	
	
	public AssociationTaggingVW(){
		
	}	
	
	
	

	//Getters and Setters
	
	@Id
	@Column(name = "CUSTOMER_ID")
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	
	@Column(name = "CUS_REF_NUM")
	public BigDecimal getCusRefNum() {
		return cusRefNum;
	}
	public void setCusRefNum(BigDecimal cusRefNum) {
		this.cusRefNum = cusRefNum;
	}
	
	@Column(name = "CIVIL_ID")
	public String getCivilId() {
		return civilId;
	}
	public void setCivilId(String civilId) {
		this.civilId = civilId;
	}
	


	@Column(name = "TITLE")
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name = "NAME")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
	@Column(name = "MOBILE")
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Column(name = "EMAIL")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(name = "NATIONALITY")
	public BigDecimal getNationality() {
		return nationality;
	}
	public void setNationality(BigDecimal nationality) {
		this.nationality = nationality;
	}
	
	
	
/*	@Column(name = "EMPLOYER_NAME")
	public String getEmployerName() {
		return employerName;
	}
	public void setEmployerName(String employerName) {
		this.employerName = employerName;
	}	*/
	
	

	@Column(name = "ASSOCOD")
	public BigDecimal getAssoCode() {
		return assoCode;
	}
	public void setAssoCode(BigDecimal assoCode) {
		this.assoCode = assoCode;
	}
	
	@Column(name = "NAME_LOCAL")
	public String getNameLocal() {
		return nameLocal;
	}
	public void setNameLocal(String nameLocal) {
		this.nameLocal = nameLocal;
	}



	@Column(name="HOME_COUNTRY_ID")
	public BigDecimal getHomeCountryId() {
		return homeCountryId;
	}




	public void setHomeCountryId(BigDecimal homeCountryId) {
		this.homeCountryId = homeCountryId;
	}


	
	@Column(name="HOME_STATE_ID")
	public BigDecimal getHomeStateId() {
		return homeStateId;
	}




	public void setHomeStateId(BigDecimal homeStateId) {
		this.homeStateId = homeStateId;
	}



	@Column(name="HOME_CITY_ID")
	public BigDecimal getHomeCityId() {
		return homeCityId;
	}




	public void setHomeCityId(BigDecimal homeCityId) {
		this.homeCityId = homeCityId;
	}



	@Column(name="HOME_DISTRICT_ID")
	public BigDecimal getHomeDistrictId() {
		return homeDistrictId;
	}




	public void setHomeDistrictId(BigDecimal homeDistrictId) {
		this.homeDistrictId = homeDistrictId;
	}



	@Column(name="LOCAL_COUNTRY_ID")
	public BigDecimal getLocalCountryId() {
		return localCountryId;
	}




	public void setLocalCountryId(BigDecimal localCountryId) {
		this.localCountryId = localCountryId;
	}



	@Column(name="LOCAL_STATE_ID")
	public BigDecimal getLocalStateId() {
		return localStateId;
	}




	public void setLocalStateId(BigDecimal localStateId) {
		this.localStateId = localStateId;
	}



	@Column(name="LOCAL_CITY_ID")
	public BigDecimal getLocalCityId() {
		return localCityId;
	}




	public void setLocalCityId(BigDecimal localCityId) {
		this.localCityId = localCityId;
	}



	@Column(name="LOCAL_DISTRICT_ID")
	public BigDecimal getLocalDistrictId() {
		return localDistrictId;
	}




	public void setLocalDistrictId(BigDecimal localDistrictId) {
		this.localDistrictId = localDistrictId;
	}


/*
	@Column(name="OCCUPATION_ID")
	public BigDecimal getOccupationId() {
		return occupationId;
	}




	public void setOccupationId(BigDecimal occupationId) {
		this.occupationId = occupationId;
	}	
*/
}
