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

		 File		: CityMasterDesc.java
 
		 Project	: AlmullaExchange

		 Package	: com.amg.exchange.model
 
		 Created	:	
 						Date	: 29-May-2014 4:43:33 pm
		 				By		: Justin Vincent
 						Revision:
 
 		 Last Change:
 						Date	: 29-May-2014 4:43:33 pm
 						By		: Justin Vincent
		 				Revision:

		 Description: TODO 

********************************************************************************************************************/
@Cacheable  
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "FS_CITY_MASTER_DESC" )
public class CityMasterDesc implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal cityMasterId;
	private LanguageType fsLanguageType;
	private CityMaster fsCityMaster;
	private String cityName;

	public CityMasterDesc() {
	}

	public CityMasterDesc(BigDecimal cityMasterId) {
		this.cityMasterId = cityMasterId;
	}

	public CityMasterDesc(BigDecimal cityMasterId,
			LanguageType fsLanguageType, CityMaster fsCityMaster,
			String cityName) {
		this.cityMasterId = cityMasterId;
		this.fsLanguageType = fsLanguageType;
		this.fsCityMaster = fsCityMaster;
		this.cityName = cityName;
	}

	@Id
	@GeneratedValue(generator="fs_city_master_desc_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="fs_city_master_desc_seq",sequenceName="FS_CITY_MASTER_DESC_SEQ",allocationSize=1)
	@Column(name = "CITY_MASTER_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getCityMasterId() {
		return this.cityMasterId;
	}

	public void setCityMasterId(BigDecimal cityMasterId) {
		this.cityMasterId = cityMasterId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LANGUAGE_ID")
	public LanguageType getFsLanguageType() {
		return this.fsLanguageType;
	}

	public void setFsLanguageType(LanguageType fsLanguageType) {
		this.fsLanguageType = fsLanguageType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CITY_ID")
	public CityMaster getFsCityMaster() {
		return this.fsCityMaster;
	}

	public void setFsCityMaster(CityMaster fsCityMaster) {
		this.fsCityMaster = fsCityMaster;
	}

	@Column(name = "CITY_NAME", length = 45)
	public String getCityName() {
		return this.cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

}
