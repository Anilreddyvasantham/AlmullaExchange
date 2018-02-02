package com.amg.exchange.treasury.bean;

import java.math.BigDecimal;
import java.util.Date;

public class DeliveryModeDatatable {

	  private String deliveryMode;
	  private String englishDeliveryModeDesc;
	  private String localDeliveryModeDesc;
	  private BigDecimal deliveryPk;
	  private BigDecimal deliveryEnglishPk;
	  private BigDecimal deliveryLocalPk;
	  private String deliveryCode;
	  private String createdBy;
	  private Date createDate;
	  private String modifiedBy;
	  private Date modifiedDate;
	  private String isActive;
	  private BigDecimal engLanguageId;
	  private BigDecimal araLanguageId;
	  private Boolean permanetDeleteCheck = false;
	  private Boolean activateRecordCheck = false;
	  private boolean booEditButton;
	  private Boolean ifEditClicked = false;

	  public String getDeliveryMode() {
		    return deliveryMode;
	  }

	  public void setDeliveryMode(String deliveryMode) {
		    this.deliveryMode = deliveryMode;
	  }

	  public String getEnglishDeliveryModeDesc() {
		    return englishDeliveryModeDesc;
	  }

	  public void setEnglishDeliveryModeDesc(String englishDeliveryModeDesc) {
		    this.englishDeliveryModeDesc = englishDeliveryModeDesc;
	  }

	  public String getLocalDeliveryModeDesc() {
		    return localDeliveryModeDesc;
	  }

	  public void setLocalDeliveryModeDesc(String localDeliveryModeDesc) {
		    this.localDeliveryModeDesc = localDeliveryModeDesc;
	  }

	  public BigDecimal getDeliveryPk() {
		    return deliveryPk;
	  }

	  public BigDecimal getDeliveryEnglishPk() {
		    return deliveryEnglishPk;
	  }

	  public BigDecimal getDeliveryLocalPk() {
		    return deliveryLocalPk;
	  }

	  public void setDeliveryPk(BigDecimal deliveryPk) {
		    this.deliveryPk = deliveryPk;
	  }

	  public void setDeliveryEnglishPk(BigDecimal deliveryEnglishPk) {
		    this.deliveryEnglishPk = deliveryEnglishPk;
	  }

	  public void setDeliveryLocalPk(BigDecimal deliveryLocalPk) {
		    this.deliveryLocalPk = deliveryLocalPk;
	  }

	  public String getDeliveryCode() {
		    return deliveryCode;
	  }

	  public void setDeliveryCode(String deliveryCode) {
		    this.deliveryCode = deliveryCode;
	  }

	  public String getCreatedBy() {
		    return createdBy;
	  }

	  public void setCreatedBy(String createdBy) {
		    this.createdBy = createdBy;
	  }

	  public Date getCreateDate() {
		    return createDate;
	  }

	  public void setCreateDate(Date createDate) {
		    this.createDate = createDate;
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

	  public String getIsActive() {
		    return isActive;
	  }

	  public void setIsActive(String isActive) {
		    this.isActive = isActive;
	  }

	  private String dynamicLabelForActivateDeactivate;

	  public String getDynamicLabelForActivateDeactivate() {
		    return dynamicLabelForActivateDeactivate;
	  }

	  public void setDynamicLabelForActivateDeactivate(String dynamicLabelForActivateDeactivate) {
		    this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	  }

	  private Boolean renderEditButton = false;

	  public Boolean getRenderEditButton() {
		    return renderEditButton;
	  }

	  public void setRenderEditButton(Boolean renderEditButton) {
		    this.renderEditButton = renderEditButton;
	  }

	  private Boolean remarksCheck;
	  private String remarks;

	  public String getRemarks() {
		    return remarks;
	  }

	  public void setRemarks(String remarks) {
		    this.remarks = remarks;
	  }

	  public Boolean getRemarksCheck() {
		    return remarksCheck;
	  }

	  public void setRemarksCheck(Boolean remarksCheck) {
		    this.remarksCheck = remarksCheck;
	  }

	  private String activateBy;
	  private Date activateDate;

	  public String getActivateBy() {
		    return activateBy;
	  }

	  public void setActivateBy(String activateBy) {
		    this.activateBy = activateBy;
	  }

	  public Date getActivateDate() {
		    return activateDate;
	  }

	  public void setActivateDate(Date activateDate) {
		    this.activateDate = activateDate;
	  }

	  private String approvedBy;
	  private Date approvedDate;

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

	  public BigDecimal getEngLanguageId() {
		    return engLanguageId;
	  }

	  public void setEngLanguageId(BigDecimal engLanguageId) {
		    this.engLanguageId = engLanguageId;
	  }

	  public BigDecimal getAraLanguageId() {
		    return araLanguageId;
	  }

	  public void setAraLanguageId(BigDecimal araLanguageId) {
		    this.araLanguageId = araLanguageId;
	  }

	  public Boolean getPermanetDeleteCheck() {
		    return permanetDeleteCheck;
	  }

	  public void setPermanetDeleteCheck(Boolean permanetDeleteCheck) {
		    this.permanetDeleteCheck = permanetDeleteCheck;
	  }

	  public Boolean getActivateRecordCheck() {
		    return activateRecordCheck;
	  }

	  public void setActivateRecordCheck(Boolean activateRecordCheck) {
		    this.activateRecordCheck = activateRecordCheck;
	  }

	  public boolean isBooEditButton() {
		    return booEditButton;
	  }

	  public void setBooEditButton(boolean booEditButton) {
		    this.booEditButton = booEditButton;
	  }

	  public Boolean getIfEditClicked() {
		    return ifEditClicked;
	  }

	  public void setIfEditClicked(Boolean ifEditClicked) {
		    this.ifEditClicked = ifEditClicked;
	  }

}
