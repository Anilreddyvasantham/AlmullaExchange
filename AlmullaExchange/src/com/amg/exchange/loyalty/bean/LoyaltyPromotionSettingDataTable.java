package com.amg.exchange.loyalty.bean;

import java.math.BigDecimal;
import java.util.Date;

public class LoyaltyPromotionSettingDataTable {

	  private BigDecimal loyaltyPromotionId;
	  private BigDecimal appCountryId;

	  private BigDecimal loyaltyParameterId;
	  private Date startDate;
	  private Date endDate;
	  private String templateCode;
	  private String description;
	  private String templateDescription;
	  private BigDecimal branchId;
	  private String releaseFlag;
	  private String branchName;
	  private String isActive;
	  private String createdBy;
	  private Date createdDate;
	  private String modifiedBy;
	  private Date modifiedDate;
	  private String approvedBy;
	  private Date approvedDate;

	  private String remarks = "";
	  private String dynamicLabelForActivateDeactivate;
	  private Boolean remarksCheck = false;
	  private Boolean activateRecordCheck = false;

	  private Boolean booCheckDelete = false;
	  private Boolean ifEditClicked = false;

	  private Boolean renderEditButton = false;
	  private Boolean booEditButton = false;
	  private Boolean activeRecordCheck = false;
	  private Boolean permentDeleteCheck = false;

	  public LoyaltyPromotionSettingDataTable() {
		    // TODO Auto-generated constructor stub
	  }

	  public BigDecimal getLoyaltyPromotionId() {
		    return loyaltyPromotionId;
	  }

	  public void setLoyaltyPromotionId(BigDecimal loyaltyPromotionId) {
		    this.loyaltyPromotionId = loyaltyPromotionId;
	  }

	  public BigDecimal getAppCountryId() {
		    return appCountryId;
	  }

	  public void setAppCountryId(BigDecimal appCountryId) {
		    this.appCountryId = appCountryId;
	  }

	  public BigDecimal getLoyaltyParameterId() {
		    return loyaltyParameterId;
	  }

	  public void setLoyaltyParameterId(BigDecimal loyaltyParameterId) {
		    this.loyaltyParameterId = loyaltyParameterId;
	  }

	  public Date getStartDate() {
		    return startDate;
	  }

	  public void setStartDate(Date startDate) {
		    this.startDate = startDate;
	  }

	  public Date getEndDate() {
		    return endDate;
	  }

	  public void setEndDate(Date endDate) {
		    this.endDate = endDate;
	  }

	  public String getTemplateCode() {
		    return templateCode;
	  }

	  public void setTemplateCode(String templateCode) {
		    this.templateCode = templateCode;
	  }

	  public String getTemplateDescription() {
		    return templateDescription;
	  }

	  public void setTemplateDescription(String templateDescription) {
		    this.templateDescription = templateDescription;
	  }

	  public BigDecimal getBranchId() {
		    return branchId;
	  }

	  public void setBranchId(BigDecimal branchId) {
		    this.branchId = branchId;
	  }

	  public String getIsActive() {
		    return isActive;
	  }

	  public void setIsActive(String isActive) {
		    this.isActive = isActive;
	  }

	  public String getCreatedBy() {
		    return createdBy;
	  }

	  public void setCreatedBy(String createdBy) {
		    this.createdBy = createdBy;
	  }

	  public Date getCreatedDate() {
		    return createdDate;
	  }

	  public void setCreatedDate(Date createdDate) {
		    this.createdDate = createdDate;
	  }

	  public String getModifiedBy() {
		    return modifiedBy;
	  }

	  public void setModifiedBy(String modifiedBy) {
		    this.modifiedBy = modifiedBy;
	  }

	  public Date getModifiedDate() {
		    return modifiedDate;
	  }

	  public void setModifiedDate(Date modifiedDate) {
		    this.modifiedDate = modifiedDate;
	  }

	  public String getApprovedBy() {
		    return approvedBy;
	  }

	  public void setApprovedBy(String approvedBy) {
		    this.approvedBy = approvedBy;
	  }

	  public Date getApprovedDate() {
		    return approvedDate;
	  }

	  public void setApprovedDate(Date approvedDate) {
		    this.approvedDate = approvedDate;
	  }

	  public String getRemarks() {
		    return remarks;
	  }

	  public void setRemarks(String remarks) {
		    this.remarks = remarks;
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

	  public Boolean getActivateRecordCheck() {
		    return activateRecordCheck;
	  }

	  public void setActivateRecordCheck(Boolean activateRecordCheck) {
		    this.activateRecordCheck = activateRecordCheck;
	  }

	  public Boolean getBooCheckDelete() {
		    return booCheckDelete;
	  }

	  public void setBooCheckDelete(Boolean booCheckDelete) {
		    this.booCheckDelete = booCheckDelete;
	  }

	  public String getReleaseFlag() {
		    return releaseFlag;
	  }

	  public void setReleaseFlag(String releaseFlag) {
		    this.releaseFlag = releaseFlag;
	  }

	  public Boolean getIfEditClicked() {
		    return ifEditClicked;
	  }

	  public void setIfEditClicked(Boolean ifEditClicked) {
		    this.ifEditClicked = ifEditClicked;
	  }

	  public Boolean getRenderEditButton() {
		    return renderEditButton;
	  }

	  public void setRenderEditButton(Boolean renderEditButton) {
		    this.renderEditButton = renderEditButton;
	  }

	  public Boolean getBooEditButton() {
		    return booEditButton;
	  }

	  public void setBooEditButton(Boolean booEditButton) {
		    this.booEditButton = booEditButton;
	  }

	  public Boolean getActiveRecordCheck() {
		    return activeRecordCheck;
	  }

	  public void setActiveRecordCheck(Boolean activeRecordCheck) {
		    this.activeRecordCheck = activeRecordCheck;
	  }

	  public Boolean getPermentDeleteCheck() {
		    return permentDeleteCheck;
	  }

	  public void setPermentDeleteCheck(Boolean permentDeleteCheck) {
		    this.permentDeleteCheck = permentDeleteCheck;
	  }

	  public String getDescription() {
		    return description;
	  }

	  public void setDescription(String description) {
		    this.description = description;
	  }

	  public String getBranchName() {
		    return branchName;
	  }

	  public void setBranchName(String branchName) {
		    this.branchName = branchName;
	  }

}
