package com.amg.exchange.remittance.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/*******************************************************************************************************************

File		: ServiceApplicabilityRule.java

Project	: AlmullaExchange

Package	: com.amg.exchange.remittance.model

Created	:	
				Date	: 26-Feb-2015
				By		: Nazish Ehsan Hashmi
				Revision:

 Last Change:
				Date	: 26-Feb-2015 
				By		: Nazish Ehsan Hashmi
				Revision:

Description: TODO 

********************************************************************************************************************/
@Entity
@Table(name="EX_SERVICE_APPLIC_PARAM_DATA")

public class ServiceApplicParamData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal serviceApplicParamDataId;
	private String fieldName;
	private String fieldDesc;
	private String isActive;
	
	public ServiceApplicParamData() {
			}

	public ServiceApplicParamData(BigDecimal serviceApplicParamDataId,
			String fieldName, String fieldDesc, String isActive) {
		super();
		this.serviceApplicParamDataId = serviceApplicParamDataId;
		this.fieldName = fieldName;
		this.fieldDesc = fieldDesc;
		this.isActive = isActive;
	}

	
    @Id
	@GeneratedValue(generator="ex_service_applparam_data_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_service_applparam_data_seq",sequenceName="EX_SERVICE_APPLPARAM_DATA_SEQ",allocationSize=1)
	@Column(name="SERVICE_APPLIC_PARAM_DATA", unique=true, nullable=false, precision=22, scale=0)

	public BigDecimal getServiceApplicParamDataId() {
		return serviceApplicParamDataId;
	}

	public void setServiceApplicParamDataId(BigDecimal serviceApplicParamDataId) {
		this.serviceApplicParamDataId = serviceApplicParamDataId;
	}

	@Column(name="FIELD_NAME")
	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	@Column(name="FIELD_DESC")
	public String getFieldDesc() {
		return fieldDesc;
	}

	public void setFieldDesc(String fieldDesc) {
		this.fieldDesc = fieldDesc;
	}

	@Column(name="ISACTIVE")
	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
	

}
