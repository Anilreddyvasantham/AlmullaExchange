package com.amg.exchange.registration.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/*******************************************************************************************************************

File		: ViewExDmsApplMap.java

Project	: AlmullaExchange

Package	: com.amg.exchange.registration.model

Created	:	
				Date	: 11-Nov-2015
				By		: Nazish Ehsan Hashmi
				Revision:

 Last Change:
				Date	: 11-Nov-2015
				By		: Nazish Ehsan Hashmi
				Revision:

********************************************************************************************************************/
@Entity
@Table(name="V_EX_DMS_APPL_MAP")
public class ViewExDmsApplMap {
	
	private BigDecimal customerId;
	private BigDecimal documentFinYear;
	private Date idExpiryDate;
	private String identityInt;
	private BigDecimal identityTypeId;
	
	
	@Id
	@Column(name="CUSTOMER_ID")
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	
	@Column(name="DOC_FIN_YR")
	public BigDecimal getDocumentFinYear() {
		return documentFinYear;
	}
	public void setDocumentFinYear(BigDecimal documentFinYear) {
		this.documentFinYear = documentFinYear;
	}
	
	@Column(name="IDENTITY_EXPIRY_DATE")
	public Date getIdExpiryDate() {
		return idExpiryDate;
	}
	public void setIdExpiryDate(Date idExpiryDate) {
		this.idExpiryDate = idExpiryDate;
	}
	
	
	@Column(name="IDENTITY_INT")
	public String getIdentityInt() {
		return identityInt;
	}
	public void setIdentityInt(String identityInt) {
		this.identityInt = identityInt;
	}
	
	@Column(name="IDENTITY_TYPE_ID")
	public BigDecimal getIdentityTypeId() {
		return identityTypeId;
	}
	
	public void setIdentityTypeId(BigDecimal identityTypeId) {
		this.identityTypeId = identityTypeId;
	}
	

}
