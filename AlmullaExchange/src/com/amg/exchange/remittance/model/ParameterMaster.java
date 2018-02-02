package com.amg.exchange.remittance.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.amg.exchange.common.model.CountryMaster;

@Entity
@Table(name = "EX_PARAMETER_MASTER")
public class ParameterMaster implements Serializable {
	
	private static final long serialVersionUID = 2315791709068216697L;
	
	private BigDecimal parameterMasterId;
	private String recordId;
	private String fullDesc;
	private String shortDesc;
	private BigDecimal codeLen;
	private String codeType;
	private String isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	
	private String localFullDesc;
	private String localShortDesc;
	
	private CountryMaster applicationCountry;
	
	
	public ParameterMaster() {
		super();
	}

	public ParameterMaster(BigDecimal parameterMasterId, String recordId, String fullDesc, String shortDesc, BigDecimal codeLen, String codeType, String isActive, String createdBy, Date createdDate,
			String modifiedBy, Date modifiedDate, String approvedBy, Date approvedDate, String remarks,CountryMaster applicationCountry) {
		super();
		this.parameterMasterId = parameterMasterId;
		this.recordId = recordId;
		this.fullDesc = fullDesc;
		this.shortDesc = shortDesc;
		this.codeLen = codeLen;
		this.codeType = codeType;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.applicationCountry = applicationCountry;
		/*this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.remarks = remarks;*/
	}

	@Id
	@GeneratedValue(generator = "ex_parameter_master_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ex_parameter_master_seq", sequenceName = "EX_PARAMETER_MASTER_SEQ", allocationSize = 1)
	@Column(name = "PARAM_MASTER_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getParameterMasterId() {
		return parameterMasterId;
	}
	
	public void setParameterMasterId(BigDecimal parameterMasterId) {
		this.parameterMasterId = parameterMasterId;
	}
	
	@Column(name = "RECORD_ID")
	public String getRecordId() {
		return recordId;
	}
	
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	
	@Column(name = "FULL_DESC")
	public String getFullDesc() {
		return fullDesc;
	}
	
	public void setFullDesc(String fullDesc) {
		this.fullDesc = fullDesc;
	}
	
	@Column(name = "SHORT_DESC")
	public String getShortDesc() {
		return shortDesc;
	}
	
	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}
	
	@Column(name = "CODE_LEN")
	public BigDecimal getCodeLen() {
		return codeLen;
	}
	
	public void setCodeLen(BigDecimal codeLen) {
		this.codeLen = codeLen;
	}
	
	@Column(name = "CODE_TYPE")
	public String getCodeType() {
		return codeType;
	}
	
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}
	
	@Column(name = "ISACTIVE")
	public String getIsActive() {
		return isActive;
	}
	
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
	@Column(name = "CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}
	
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	@Column(name = "CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}
	
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	@Column(name = "MODIFIED_BY")
	public String getModifiedBy() {
		return modifiedBy;
	}
	
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	@Column(name = "MODIFIED_DATE")
	public Date getModifiedDate() {
		return modifiedDate;
	}
	
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name = "LOCAL_FULL_DESC")
	public String getLocalFullDesc() {
		return localFullDesc;
	}

	public void setLocalFullDesc(String localFullDesc) {
		this.localFullDesc = localFullDesc;
	}

	@Column(name = "LOCAL_SHORT_DESC")
	public String getLocalShortDesc() {
		return localShortDesc;
	}

	public void setLocalShortDesc(String localShortDesc) {
		this.localShortDesc = localShortDesc;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "APPLICATION_COUNTRY_ID")	
	public CountryMaster getApplicationCountry() {
		return applicationCountry;
	}
	public void setApplicationCountry(CountryMaster applicationCountry) {
		this.applicationCountry = applicationCountry;
	}
}
