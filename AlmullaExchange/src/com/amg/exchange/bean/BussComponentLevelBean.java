package com.amg.exchange.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class BussComponentLevelBean implements Serializable {
	
	private static final long serialVersionUID = 8861160792406145517L;
	private BigDecimal applicationId;
	private String applicationName;
	private BigDecimal companyId;
	private String companyName;
	private BigDecimal countryId;
	private String countryName;
	private BigDecimal pageId;
	private String pageName;
	private BigDecimal componentConfId;
	
	
	
	public BussComponentLevelBean() {
	}
	
	public BussComponentLevelBean(BigDecimal applicationId,
			String applicationName, BigDecimal companyId, String companyName,
			BigDecimal countryId, String countryName, BigDecimal pageId,
			String pageName, BigDecimal componentConfId) {
		this.applicationId = applicationId;
		this.applicationName = applicationName;
		this.companyId = companyId;
		this.companyName = companyName;
		this.countryId = countryId;
		this.countryName = countryName;
		this.pageId = pageId;
		this.pageName = pageName;
		this.componentConfId = componentConfId;
	}

	public BigDecimal getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(BigDecimal applicationId) {
		this.applicationId = applicationId;
	}
	public String getApplicationName() {
		return applicationName;
	}
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	public BigDecimal getCompanyId() {
		return companyId;
	}
	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public BigDecimal getCountryId() {
		return countryId;
	}
	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public BigDecimal getPageId() {
		return pageId;
	}
	public void setPageId(BigDecimal pageId) {
		this.pageId = pageId;
	}
	public String getPageName() {
		return pageName;
	}
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}
	public BigDecimal getComponentConfId() {
		return componentConfId;
	}
	public void setComponentConfId(BigDecimal componentConfId) {
		this.componentConfId = componentConfId;
	}

	@Override
	public String toString() {
		return "BussComponentLevelBean [applicationId=" + applicationId
				+ ", applicationName=" + applicationName + ", companyId="
				+ companyId + ", companyName=" + companyName + ", countryId="
				+ countryId + ", countryName=" + countryName + ", pageId="
				+ pageId + ", pageName=" + pageName + ", componentConfId="
				+ componentConfId + "]";
	}
	
}
