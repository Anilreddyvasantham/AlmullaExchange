package com.amg.exchange.remittance.bean;

import java.math.BigDecimal;
import java.util.Date;

public class ServiceGroupBeanDataTable {

	private BigDecimal serviceGroupId;
	private BigDecimal appCountryId;
	private String serviceGroupCode;
	private String isActive;
	private Date createdDate;
	private String createdBy;
	private Date modifiedDate;
	private String modifiedBy;
	private Date approvedDate;
	private String approvedBy;
	private String remarks;
	private String serviceGroupLocalDesc;
	private String serviceGroupEnglishDesc;
	private BigDecimal serviceGroupEngDescId;
	private BigDecimal serviceGroupLocalDescId;
	private BigDecimal languageTypeId;
	private String dynamicLabelForActivateDeactivate;

	private Boolean booCheckUpdate = false;
	private Boolean booCheckDelete = false;

	private Boolean activateRecordCheck = false;

	private Boolean remarksCheck = false;


	public BigDecimal getServiceGroupId() {
		return serviceGroupId;
	}

	public void setServiceGroupId(BigDecimal serviceGroupId) {
		this.serviceGroupId = serviceGroupId;
	}

	public BigDecimal getAppCountryId() {
		return appCountryId;
	}

	public void setAppCountryId(BigDecimal appCountryId) {
		this.appCountryId = appCountryId;
	}

	public String getServiceGroupCode() {
		return serviceGroupCode;
	}

	public void setServiceGroupCode(String serviceGroupCode) {
		this.serviceGroupCode = serviceGroupCode;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getServiceGroupLocalDesc() {
		return serviceGroupLocalDesc;
	}

	public void setServiceGroupLocalDesc(String serviceGroupLocalDesc) {
		this.serviceGroupLocalDesc = serviceGroupLocalDesc;
	}

	public String getServiceGroupEnglishDesc() {
		return serviceGroupEnglishDesc;
	}

	public void setServiceGroupEnglishDesc(String serviceGroupEnglishDesc) {
		this.serviceGroupEnglishDesc = serviceGroupEnglishDesc;
	}

	public BigDecimal getServiceGroupEngDescId() {
		return serviceGroupEngDescId;
	}

	public void setServiceGroupEngDescId(BigDecimal serviceGroupEngDescId) {
		this.serviceGroupEngDescId = serviceGroupEngDescId;
	}

	public BigDecimal getServiceGroupLocalDescId() {
		return serviceGroupLocalDescId;
	}

	public void setServiceGroupLocalDescId(BigDecimal serviceGroupLocalDescId) {
		this.serviceGroupLocalDescId = serviceGroupLocalDescId;
	}

	public BigDecimal getLanguageTypeId() {
		return languageTypeId;
	}

	public void setLanguageTypeId(BigDecimal languageTypeId) {
		this.languageTypeId = languageTypeId;
	}

	public Boolean getBooCheckUpdate() {
		return booCheckUpdate;
	}

	public void setBooCheckUpdate(Boolean booCheckUpdate) {
		this.booCheckUpdate = booCheckUpdate;
	}

	public Boolean getBooCheckDelete() {
		return booCheckDelete;
	}

	public void setBooCheckDelete(Boolean booCheckDelete) {
		this.booCheckDelete = booCheckDelete;
	}

	public Boolean getActivateRecordCheck() {
		return activateRecordCheck;
	}

	public void setActivateRecordCheck(Boolean activateRecordCheck) {
		this.activateRecordCheck = activateRecordCheck;
	}

	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}

	public void setDynamicLabelForActivateDeactivate(String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}

	public Boolean getRemarksCheck() {
		return remarksCheck;
	}

	public void setRemarksCheck(Boolean remarksCheck) {
		this.remarksCheck = remarksCheck;
	}
	
	
	private BigDecimal englishLanguageId;
	private BigDecimal arabicLanguageId;

	  public BigDecimal getEnglishLanguageId() {
		    return englishLanguageId;
	  }

	  public void setEnglishLanguageId(BigDecimal englishLanguageId) {
		    this.englishLanguageId = englishLanguageId;
	  }

	  public BigDecimal getArabicLanguageId() {
		    return arabicLanguageId;
	  }

	  public void setArabicLanguageId(BigDecimal arabicLanguageId) {
		    this.arabicLanguageId = arabicLanguageId;
	  }

	  private Boolean permentDeleteCheck = false;
	  private Boolean ifEditClicked=false;

	  public Boolean getPermentDeleteCheck() {
		    return permentDeleteCheck;
	  }

	  public void setPermentDeleteCheck(Boolean permentDeleteCheck) {
		    this.permentDeleteCheck = permentDeleteCheck;
	  }

	  public Boolean getIfEditClicked() {
		    return ifEditClicked;
	  }

	  public void setIfEditClicked(Boolean ifEditClicked) {
		    this.ifEditClicked = ifEditClicked;
	  }
	
	  
	
	

}
