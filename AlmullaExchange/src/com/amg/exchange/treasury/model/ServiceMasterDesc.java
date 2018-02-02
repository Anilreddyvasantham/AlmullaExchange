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
@Table(name="EX_SERVICE_MASTER_DESC")
public class ServiceMasterDesc implements Serializable {
	private static final long serialVersionUID = 1L;
private ServiceMaster serviceMaster;
private LanguageType languageType;
private BigDecimal serviceMasterDecsId;
private String localServiceDescription;


public ServiceMasterDesc() {
	super();
}

public ServiceMasterDesc(BigDecimal serviceMasterDecsId){
	this.serviceMasterDecsId=serviceMasterDecsId;
}
public ServiceMasterDesc(ServiceMaster serviceMaster, LanguageType languageType,
		String localServiceDescription,BigDecimal serviceMasterDecsId) {
	super();
	this.serviceMaster = serviceMaster;
	this.setLanguageType(languageType);
	this.localServiceDescription = localServiceDescription;
	this.serviceMasterDecsId=serviceMasterDecsId;
	
}

@Id
@GeneratedValue(generator="ex_service_master_desc",strategy=GenerationType.SEQUENCE)
@SequenceGenerator(name = "ex_service_master_desc",sequenceName="EX_SERVICE_MASTER_DESC_SEQ",allocationSize=1)
@Column(name="SERVICE_MASTER_DESC_ID",unique=true,nullable=false,precision=22,scale=0)
public BigDecimal getServiceMasterDecsId() {
	return serviceMasterDecsId;
}
public void setServiceMasterDecsId(BigDecimal serviceMasterDecsId) {
	this.serviceMasterDecsId = serviceMasterDecsId;
}

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "SERVICE_ID")

public ServiceMaster getServiceMaster() {
	return serviceMaster;
}


public void setServiceMaster(ServiceMaster serviceMaster) {
	this.serviceMaster = serviceMaster;
}

@Column(name="SERVICE_DESCRIPTION",length=40)
public String getLocalServiceDescription() {
	return localServiceDescription;
}

public void setLocalServiceDescription(String localServiceDescription) {
	this.localServiceDescription = localServiceDescription;
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
