package com.amg.exchange.common.model;

import java.math.BigDecimal;

import javax.persistence.Cacheable;
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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/*******************************************************************************************************************

		 File		: DistrictMasterDesc.java
 
		 Project	: AlmullaExchange

		 Package	: com.amg.exchange.model
 
		 Created	:	
 						Date	: 29-May-2014 5:13:12 pm
		 				By		: Justin Vincent
 						Revision:
 
 		 Last Change:
 						Date	: 29-May-2014 5:13:12 pm
 						By		: Justin Vincent
		 				Revision:

		 Description: TODO 

********************************************************************************************************************/
@Cacheable  
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "FS_DISTRICT_MASTER_DESC" )
public class DistrictMasterDesc implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private BigDecimal districtDescId;
	private DistrictMaster fsDistrictMaster;
	private LanguageType fsLanguageType;
	private String district;

	public DistrictMasterDesc() {
	}

	public DistrictMasterDesc(BigDecimal districtDescId) {
		this.districtDescId = districtDescId;
	}

	public DistrictMasterDesc(BigDecimal districtDescId,
			DistrictMaster fsDistrictMaster, LanguageType fsLanguageType,
			String district) {
		this.districtDescId = districtDescId;
		this.fsDistrictMaster = fsDistrictMaster;
		this.fsLanguageType = fsLanguageType;
		this.district = district;
	}

	/*@Id
	@TableGenerator(name="districtdescid",table="fs_districtdescidpk",pkColumnName="districtdescidkey",pkColumnValue="districtdescidvalue",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.TABLE,generator="districtdescid")*/
	
	@Id
	@GeneratedValue(generator="fs_district_master_desc_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="fs_district_master_desc_seq",sequenceName="FS_DISTRICT_MASTER_DESC_SEQ",allocationSize=1)
	@Column(name = "DISTRICT_DESC_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getDistrictDescId() {
		return this.districtDescId;
	}

	public void setDistrictDescId(BigDecimal districtDescId) {
		this.districtDescId = districtDescId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DISTRICT_ID")
	public DistrictMaster getFsDistrictMaster() {
		return this.fsDistrictMaster;
	}

	public void setFsDistrictMaster(DistrictMaster fsDistrictMaster) {
		this.fsDistrictMaster = fsDistrictMaster;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LANGUAGE_ID")
	public LanguageType getFsLanguageType() {
		return this.fsLanguageType;
	}

	public void setFsLanguageType(LanguageType fsLanguageType) {
		this.fsLanguageType = fsLanguageType;
	}

	@Column(name = "DISTRICT", length = 100)
	public String getDistrict() {
		return this.district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

}
