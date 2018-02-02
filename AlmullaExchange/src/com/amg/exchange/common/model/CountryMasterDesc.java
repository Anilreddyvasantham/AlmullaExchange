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

		 File		: CountryMasterDesc.java
 
		 Project	: AlmullaExchange

		 Package	: com.amg.exchange.model
 
		 Created	:	
 						Date	: 29-May-2014 5:00:43 pm
		 				By		: Justin Vincent
 						Revision:
 
 		 Last Change:
 						Date	: 29-May-2014 5:00:43 pm
 						By		: Justin Vincent
		 				Revision:

		 Description: TODO 

********************************************************************************************************************/
@Cacheable  
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "FS_COUNTRY_MASTER_DESC" )
public class CountryMasterDesc implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private BigDecimal countryMasterId;
	private LanguageType languageType;
	private CountryMaster fsCountryMaster;
	private String countryName;
	private String nationality;

	public CountryMasterDesc() {
	}

	public CountryMasterDesc(BigDecimal countryMasterId) {
		this.countryMasterId = countryMasterId;
	}

	public CountryMasterDesc(BigDecimal countryMasterId,
			LanguageType languageType, CountryMaster fsCountryMaster,
			String countryName, String nationality) {
		this.countryMasterId = countryMasterId;
		this.languageType = languageType;
		this.fsCountryMaster = fsCountryMaster;
		this.countryName = countryName;
		this.nationality = nationality;
	}

	/*@Id
	@TableGenerator(name = "countrymasterid", table = "fs_countrymasteridpk", pkColumnName = "countrymasteridkey", pkColumnValue = "countrymasteridvalue", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "countrymasterid")*/
	
	@Id
	@GeneratedValue(generator="fs_country_master_desc_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="fs_country_master_desc_seq",sequenceName="FS_COUNTRY_MASTER_DESC_SEQ",allocationSize=1)
	@Column(name = "COUNTRY_DESC_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getCountryMasterId() {
		return this.countryMasterId;
	}

	public void setCountryMasterId(BigDecimal countryMasterId) {
		this.countryMasterId = countryMasterId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LANGUAGE_ID")
	public LanguageType getFsLanguageType() {
		return this.languageType;
	}

	public void setFsLanguageType(LanguageType languageType) {
		this.languageType = languageType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_ID")
	public CountryMaster getFsCountryMaster() {
		return this.fsCountryMaster;
	}

	public void setFsCountryMaster(CountryMaster fsCountryMaster) {
		this.fsCountryMaster = fsCountryMaster;
	}

	@Column(name = "COUNTRY_NAME", length = 45)
	public String getCountryName() {
		return this.countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	@Column(name = "NATIONALITY", length = 50)
	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

}
