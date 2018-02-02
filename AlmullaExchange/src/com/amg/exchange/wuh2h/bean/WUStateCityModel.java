package com.amg.exchange.wuh2h.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="EX_WU_STATE_CITY")
public class WUStateCityModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal wuStateCityId;
	private BigDecimal wuCountryId;
	private String wuCountryISOCode;
	private String wuStateCode;
	private String wuStateDescription;
	private String wuCityName;
	private String createdBy;
	private Date createdDate;
	
	public WUStateCityModel(){
		
	}
	
	
	public WUStateCityModel(BigDecimal wuStateCityId, BigDecimal wuCountryId,
			String wuCountryISOCode, String wuStateCode,
			String wuStateDescription, String wuCityName, String createdBy,
			Date createdDate) {
		super();
		this.wuStateCityId = wuStateCityId;
		this.wuCountryId = wuCountryId;
		this.wuCountryISOCode = wuCountryISOCode;
		this.wuStateCode = wuStateCode;
		this.wuStateDescription = wuStateDescription;
		this.wuCityName = wuCityName;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
	}


	@Id
	@GeneratedValue(generator="ex_wu_state_city_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_wu_state_city_seq",sequenceName="EX_WU_STATE_CITY_SEQ",allocationSize=1)
	@Column(name = "EX_WU_STATE_CITY_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getWuStateCityId() {
		return wuStateCityId;
	}
	public void setWuStateCityId(BigDecimal wuStateCityId) {
		this.wuStateCityId = wuStateCityId;
	}
		
	@Column(name = "COUNTRY_ID")
	public BigDecimal getWuCountryId() {
		return wuCountryId;
	}
	public void setWuCountryId(BigDecimal wuCountryId) {
		this.wuCountryId = wuCountryId;
	}
	
	@Column(name = "WU_COUNTRY_ISO_CODE")	
	public String getWuCountryISOCode() {
		return wuCountryISOCode;
	}
	public void setWuCountryISOCode(String wuCountryISOCode) {
		this.wuCountryISOCode = wuCountryISOCode;
	}
	
	@Column(name = "WU_STATE_CODE")
	public String getWuStateCode() {
		return wuStateCode;
	}
	public void setWuStateCode(String wuStateCode) {
		this.wuStateCode = wuStateCode;
	}
	
	@Column(name = "WU_STATE_DESCRIPTION")
	public String getWuStateDescription() {
		return wuStateDescription;
	}
	public void setWuStateDescription(String wuStateDescription) {
		this.wuStateDescription = wuStateDescription;
	}
	
	@Column(name = "WU_CITY_NAME")
	public String getWuCityName() {
		return wuCityName;
	}
	public void setWuCityName(String wuCityName) {
		this.wuCityName = wuCityName;
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
		
}
