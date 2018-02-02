package com.amg.exchange.remittance.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="V_STATE")
public class ViewStatesForICASH implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/*COUNTRY  NOT NULL VARCHAR2(3)   
ZONE              VARCHAR2(4)   
STATE    NOT NULL VARCHAR2(4)   
FUNAME   NOT NULL VARCHAR2(60)  
ARFUNAME          VARCHAR2(120) 
AL2COM            VARCHAR2(11)  
CRTDAT   NOT NULL DATE          
UPDDAT            DATE          
CREATOR  NOT NULL VARCHAR2(15)  
MODIFIER          VARCHAR2(15)    */
	
	private String countryAlphaCode;
	private String zone;
	private String statecode;
	private String engStateName;
	private String arStateName;
	private String alcom;
	private Date createdDate;
	private String createdBy;
	private Date modifiedDate;
	private String modifiedBy;
	
	@Column(name="COUNTRY")
	public String getCountryAlphaCode() {
		return countryAlphaCode;
	}
	public void setCountryAlphaCode(String countryAlphaCode) {
		this.countryAlphaCode = countryAlphaCode;
	}
	
	@Column(name="ZONE")
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	
	@Id
	@Column(name="STATE")
	public String getStatecode() {
		return statecode;
	}
	public void setStatecode(String statecode) {
		this.statecode = statecode;
	}
	
	@Column(name="FUNAME")
	public String getEngStateName() {
		return engStateName;
	}
	public void setEngStateName(String engStateName) {
		this.engStateName = engStateName;
	}
	
	@Column(name="ARFUNAME")
	public String getArStateName() {
		return arStateName;
	}
	public void setArStateName(String arStateName) {
		this.arStateName = arStateName;
	}
	
	@Column(name="AL2COM")
	public String getAlcom() {
		return alcom;
	}
	public void setAlcom(String alcom) {
		this.alcom = alcom;
	}
	
	@Column(name="CRTDAT")
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	@Column(name="CREATOR")
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	@Column(name="UPDDAT")
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	@Column(name="MODIFIER")
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	
	
}


