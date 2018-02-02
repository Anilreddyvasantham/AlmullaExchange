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
@Table(name="EX_ZONE_DESC")
public class ZoneMasterDesc implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal zoneDescId;
	private Zone zone;
	private LanguageType languageType;
	private String zoneDescription;
	
	
	public ZoneMasterDesc() {
		super();
	}


	public ZoneMasterDesc(BigDecimal zoneDescId) {
		this.zoneDescId = zoneDescId;
	}


	public ZoneMasterDesc(BigDecimal zoneDescId, Zone zone, LanguageType languageType, String zoneDescription) {
		super();
		this.zoneDescId = zoneDescId;
		this.zone = zone;
		this.languageType = languageType;
		this.zoneDescription = zoneDescription;
	}

	@Id
	@GeneratedValue(generator="ex_zone_desc",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ex_zone_desc",sequenceName="EX_ZONE_DESC_SEQ",allocationSize=1)
	@Column(name="ZONE_DESC_ID",unique=true,nullable=false,precision=22,scale=0)
	public BigDecimal getZoneDescId() {
		return zoneDescId;
	}


	public void setZoneDescId(BigDecimal zoneDescId) {
		this.zoneDescId = zoneDescId;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ZONE_ID")
		public Zone getZone() {
		return zone;
	}


	public void setZone(Zone zone) {
		this.zone = zone;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LANGUAGE_ID")
	public LanguageType getLanguageType() {
		return languageType;
	}


	public void setLanguageType(LanguageType languageType) {
		this.languageType = languageType;
	}

	@Column(name="ZONE_DESC")
	public String getZoneDescription() {
		return zoneDescription;
	}


	public void setZoneDescription(String zoneDescription) {
		this.zoneDescription = zoneDescription;
	}

}
