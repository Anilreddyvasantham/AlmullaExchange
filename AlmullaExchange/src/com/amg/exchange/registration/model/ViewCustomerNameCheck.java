package com.amg.exchange.registration.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/*******************************************************************************************************************

File		: ViewCustomerNameCheck.java

Project	: AlmullaExchange

Package	: com.amg.exchange.registration.model

Created	:	
				Date	: 10-Oct-2015
				By		: Nazish Ehsan Hashmi
				Revision:

 Last Change:
				Date	: 10-Oct-2015
				By		: Nazish Ehsan Hashmi
				Revision:

********************************************************************************************************************/
@Entity
@Table(name="VW_CUSTOMER_NAME_CHECK")
public class ViewCustomerNameCheck {

	private BigDecimal srNo;
	private String recordId;
	private String paramCodeDef;
	private String fullDesc;
	private String shortDesc;
	private BigDecimal countryId;
	private BigDecimal nameLenght;
	
	@Id
	@Column(name="SRNO")
	public BigDecimal getSrNo() {
		return srNo;
	}
	public void setSrNo(BigDecimal srNo) {
		this.srNo = srNo;
	}
	
	@Column(name="RECORD_ID")
	public String getRecordId() {
		return recordId;
	}
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	
	@Column(name="PARAM_CODE_DEF")
	public String getParamCodeDef() {
		return paramCodeDef;
	}
	public void setParamCodeDef(String paramCodeDef) {
		this.paramCodeDef = paramCodeDef;
	}
	@Column(name="FULL_DESC")
	public String getFullDesc() {
		return fullDesc;
	}
	public void setFullDesc(String fullDesc) {
		this.fullDesc = fullDesc;
	}
	
	@Column(name="SHORT_DESC")
	public String getShortDesc() {
		return shortDesc;
	}
	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}
	
	@Column(name="COUNTRY_ID")
	public BigDecimal getCountryId() {
		return countryId;
	}
	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}
	
	@Column(name="NAME_LENGTH")
	public BigDecimal getNameLenght() {
		return nameLenght;
	}
	public void setNameLenght(BigDecimal nameLenght) {
		this.nameLenght = nameLenght;
	}
	
	
}
