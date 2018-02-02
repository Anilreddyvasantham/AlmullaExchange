package com.amg.exchange.remittance.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.common.model.LanguageType;

public class ServiceApplicabilityRuleCopyDataTable implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal countryId;
	private BigDecimal currencyId;
	private BigDecimal remittenceModeid;
	private BigDecimal delivaryId;
	private BigDecimal applicationCountryId;
	
	private String countryName;
	private String currencyName;
	private String remittenceMode;
	private String delivary;
	
	
	public BigDecimal getCountryId() {
		return countryId;
	}
	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}
	public BigDecimal getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}
	public BigDecimal getRemittenceModeid() {
		return remittenceModeid;
	}
	public void setRemittenceModeid(BigDecimal remittenceModeid) {
		this.remittenceModeid = remittenceModeid;
	}
	public BigDecimal getDelivaryId() {
		return delivaryId;
	}
	public void setDelivaryId(BigDecimal delivaryId) {
		this.delivaryId = delivaryId;
	}
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}
	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public String getRemittenceMode() {
		return remittenceMode;
	}
	public void setRemittenceMode(String remittenceMode) {
		this.remittenceMode = remittenceMode;
	}
	public String getDelivary() {
		return delivary;
	}
	public void setDelivary(String delivary) {
		this.delivary = delivary;
	}
	@Override
	public String toString() {
		return "ServiceApplicabilityRuleCopyDataTable [countryId=" + countryId + ", currencyId=" + currencyId + ", remittenceModeid=" + remittenceModeid + ", delivaryId=" + delivaryId + ", applicationCountryId=" + applicationCountryId + ", countryName=" + countryName + ", currencyName="
				+ currencyName + ", remittenceMode=" + remittenceMode + ", delivary=" + delivary + "]";
	}
	
	 private BigDecimal serviceApplanguageId;
	  private String landuageName;

	  

	  public BigDecimal getServiceApplanguageId() {
	  	  return serviceApplanguageId;
	  }
	  public void setServiceApplanguageId(BigDecimal serviceApplanguageId) {
	  	  this.serviceApplanguageId = serviceApplanguageId;
	  }
	  public String getLanduageName() {
		    return landuageName;
	  }

	  public void setLanduageName(String landuageName) {
		    this.landuageName = landuageName;
	  }
	
	  private List<LanguageType> lstLanguageTypes;

	  public List<LanguageType> getLstLanguageTypes() {
	  	  return lstLanguageTypes;
	  }
	  public void setLstLanguageTypes(List<LanguageType> lstLanguageTypes) {
	  	  this.lstLanguageTypes = lstLanguageTypes;
	  }
	
	
	
}
