package com.amg.exchange.treasury.model;

import java.io.Serializable;
import java.math.BigDecimal;

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

import com.amg.exchange.common.model.LanguageType;

@Entity
@Table(name="EX_SERVICE_GROUP_DESC")
public class ServiceGroupMasterDesc implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private ServiceGroupMaster serviceGroupMasterId;
	private BigDecimal serviceGroupMasterDescId;
	private String serviceGroupDesc;
	private LanguageType languageType;
	
	
	
	public ServiceGroupMasterDesc() {
	}
	
	public ServiceGroupMasterDesc(ServiceGroupMaster serviceGroupMasterId, BigDecimal serviceGroupMasterDescId, String serviceGroupDesc, LanguageType languageType) {
		this.serviceGroupMasterId = serviceGroupMasterId;
		this.serviceGroupMasterDescId = serviceGroupMasterDescId;
		this.serviceGroupDesc = serviceGroupDesc;
		this.languageType = languageType;
	}
	
	@Id
	@GeneratedValue(generator="ex_service_group_desc_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_service_group_desc_seq",sequenceName="EX_SERVICE_GROUP_DESC_SEQ",allocationSize=1)
	@Column(name ="SERVICE_GROUP_DESC_ID" , unique=true, nullable=false, precision=22, scale=0)
	public BigDecimal getServiceGroupMasterDescId() {
		return serviceGroupMasterDescId;
	}
	public void setServiceGroupMasterDescId(BigDecimal serviceGroupMasterDescId) {
		this.serviceGroupMasterDescId = serviceGroupMasterDescId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SERVICE_GROUP_ID")
	public ServiceGroupMaster getServiceGroupMasterId() {
		return serviceGroupMasterId;
	}
	public void setServiceGroupMasterId(ServiceGroupMaster serviceGroupMasterId) {
		this.serviceGroupMasterId = serviceGroupMasterId;
	}
	
	@Column(name = "SERVICE_GROUP_DESC")
	public String getServiceGroupDesc() {
		return serviceGroupDesc;
	}
	public void setServiceGroupDesc(String serviceGroupDesc) {
		this.serviceGroupDesc = serviceGroupDesc;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LANGUAGE_ID")
	public LanguageType getLanguageType() {
		return languageType;
	}
	public void setLanguageType(LanguageType languageType) {
		this.languageType = languageType;
	}
	
	
	
	


}
