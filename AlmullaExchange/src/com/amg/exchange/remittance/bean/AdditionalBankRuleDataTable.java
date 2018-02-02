package com.amg.exchange.remittance.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class AdditionalBankRuleDataTable {

	

	private BigDecimal AdditionalBankRuleId;
	private String fieldName;
	private String flexField;
	private BigDecimal orderNo;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private BigDecimal countryId;
	private String countryName;
	private String isActive;

	private String amiecCode;
	private String amiecDescription;
	private String additionalData;
	private String additionalDescription;
	private BigDecimal bankId;
	private String bankName;
	
	private String bankCode;
	private String bankDescription;
	private BigDecimal AdditionalBankRulePk;
	private BigDecimal pkamaieBankMapping;
	private BigDecimal pkEditfun;
	private Boolean remarkCheck=false;
	private Boolean actRecordCheck=false;
	private Boolean permanetDeleteCheck =false;
	
	//fields for Addition Bank Rule With Map
	private String dynamicLabelForActivateDeactivateForBankMap;
	private BigDecimal AdditionalBankRuleMapPk;
	private String approveBy;
	private Date approveDate;
	private String dataActive;
	private Boolean activateRecordCheck=false;
    private String remarks;
    private String remarksForBankMap;
    private Boolean remarkCheckForBankMap;
    private Boolean permanetDeleteCheckForBankMap=false;
    private Boolean editClikedBankMapRule=false;
	
	//fields for Addition Bank Rule with Almulla Code
	private String dynamicLabelForActivateDeactivateForAlmullaCode;
	private String isActiveForAlmullaCode;
	private String createdByForAlmullaCode;
	private Date createdDateForAlmullaCode;
	private BigDecimal additionalBankRulaAlmullaCodePK;
	private String approveByForAlmullaCode;
	private Date approveDateForAlmullaCode;
	private String modifiedByForAlmullaCode;
	private Date modifiedDateForAlmullaCode;
	private BigDecimal additionalBankFieldId;
	private String remarksForAlmullaCode;
	private Boolean remarkCheckForAlmullaCode;
	private Boolean activateRecordCheckAlmullaCode=false;
	private Boolean permanetDeleteCheckForAlmullaCode=false;
	private Boolean editClikedAlmullaCode=false;
	
	//fields for Addition Bank Rule
	private Boolean disableSubmitButtonForBankRule;
	private String dynamicLabelForActivateDeactivateForBankRule;
	private String isActiveForBankRule;
	private String createdByForBankRule;
	private Date createdDateForBankRule;
	private BigDecimal additionalBankRulePK;
	private String approveByForBankRule;
	private Date approveDateForBankRule;
	private String modifiedByForBankRule;
	private Date modifiedDateForBankRule;
	private BigDecimal additionalBankFieldIdForBankRule;
	private String remarksForBankRule;
	private Boolean remarkCheckForBankRule=false;
	private Boolean activateRecordCheckBankRule=false;
	private Boolean permanetDeleteCheckForBankRule=false;
	private Boolean editClikedBankRule=false;
	
    private Boolean deleteButton=false;
    
    //Added by kani for isActive status in word Begin
  	private String activeStatusInWord;
  	
  	private Boolean editClicked=false;
  	
  	
  	public BigDecimal getPkEditfun() {
		return pkEditfun;
	}

	public void setPkEditfun(BigDecimal pkEditfun) {
		this.pkEditfun = pkEditfun;
	}

	public Boolean getEditClicked() {
		return editClicked;
	}

	public Boolean getRemarkCheck() {
		return remarkCheck;
	}

	public void setRemarkCheck(Boolean remarkCheck) {
		this.remarkCheck = remarkCheck;
	}

	

	public Boolean getActRecordCheck() {
		return actRecordCheck;
	}

	public void setActRecordCheck(Boolean actRecordCheck) {
		this.actRecordCheck = actRecordCheck;
	}

	public Boolean getPermanetDeleteCheck() {
		return permanetDeleteCheck;
	}

	public void setPermanetDeleteCheck(Boolean permanetDeleteCheck) {
		this.permanetDeleteCheck = permanetDeleteCheck;
	}

	public void setEditClicked(Boolean editClicked) {
		this.editClicked = editClicked;
	}

	public Boolean getPermanetDeleteCheckForBankRule() {
		return permanetDeleteCheckForBankRule;
	}

	public void setPermanetDeleteCheckForBankRule(
			Boolean permanetDeleteCheckForBankRule) {
		this.permanetDeleteCheckForBankRule = permanetDeleteCheckForBankRule;
	}

	public Boolean getEditClikedBankRule() {
		return editClikedBankRule;
	}

	public void setEditClikedBankRule(Boolean editClikedBankRule) {
		this.editClikedBankRule = editClikedBankRule;
	}

	public Boolean getActivateRecordCheckBankRule() {
		return activateRecordCheckBankRule;
	}

	public void setActivateRecordCheckBankRule(Boolean activateRecordCheckBankRule) {
		this.activateRecordCheckBankRule = activateRecordCheckBankRule;
	}

	public String getActiveStatusInWord() {
  		return activeStatusInWord;
  	}

  	public void setActiveStatusInWord(String activeStatusInWord) {
  		this.activeStatusInWord = activeStatusInWord;
  	}

	// Added by kani End
	
  	
	

	public Boolean getDeleteButton() {
		return deleteButton;
	}



	public Boolean getEditClikedAlmullaCode() {
		return editClikedAlmullaCode;
	}

	public void setEditClikedAlmullaCode(Boolean editClikedAlmullaCode) {
		this.editClikedAlmullaCode = editClikedAlmullaCode;
	}

	public Boolean getPermanetDeleteCheckForAlmullaCode() {
		return permanetDeleteCheckForAlmullaCode;
	}

	public void setPermanetDeleteCheckForAlmullaCode(
			Boolean permanetDeleteCheckForAlmullaCode) {
		this.permanetDeleteCheckForAlmullaCode = permanetDeleteCheckForAlmullaCode;
	}

	public Boolean getActivateRecordCheckAlmullaCode() {
		return activateRecordCheckAlmullaCode;
	}

	public void setActivateRecordCheckAlmullaCode(
			Boolean activateRecordCheckAlmullaCode) {
		this.activateRecordCheckAlmullaCode = activateRecordCheckAlmullaCode;
	}

	public Boolean getEditClikedBankMapRule() {
		return editClikedBankMapRule;
	}

	public void setEditClikedBankMapRule(Boolean editClikedBankMapRule) {
		this.editClikedBankMapRule = editClikedBankMapRule;
	}

	public Boolean getPermanetDeleteCheckForBankMap() {
		return permanetDeleteCheckForBankMap;
	}

	public void setPermanetDeleteCheckForBankMap(
			Boolean permanetDeleteCheckForBankMap) {
		this.permanetDeleteCheckForBankMap = permanetDeleteCheckForBankMap;
	}

	public Boolean getActivateRecordCheck() {
		return activateRecordCheck;
	}

	public void setActivateRecordCheck(Boolean activateRecordCheck) {
		this.activateRecordCheck = activateRecordCheck;
	}

	public void setDeleteButton(Boolean deleteButton) {
		this.deleteButton = deleteButton;
	}

	public BigDecimal getAdditionalBankFieldIdForBankRule() {
		return additionalBankFieldIdForBankRule;
	}

	public void setAdditionalBankFieldIdForBankRule(
			BigDecimal additionalBankFieldIdForBankRule) {
		this.additionalBankFieldIdForBankRule = additionalBankFieldIdForBankRule;
	}

	public BigDecimal getAdditionalBankFieldId() {
		return additionalBankFieldId;
	}

	public void setAdditionalBankFieldId(BigDecimal additionalBankFieldId) {
		this.additionalBankFieldId = additionalBankFieldId;
	}

	public String getModifiedByForAlmullaCode() {
		return modifiedByForAlmullaCode;
	}

	public void setModifiedByForAlmullaCode(String modifiedByForAlmullaCode) {
		this.modifiedByForAlmullaCode = modifiedByForAlmullaCode;
	}

	public Date getModifiedDateForAlmullaCode() {
		return modifiedDateForAlmullaCode;
	}

	public void setModifiedDateForAlmullaCode(Date modifiedDateForAlmullaCode) {
		this.modifiedDateForAlmullaCode = modifiedDateForAlmullaCode;
	}

	public String getModifiedByForBankRule() {
		return modifiedByForBankRule;
	}

	public void setModifiedByForBankRule(String modifiedByForBankRule) {
		this.modifiedByForBankRule = modifiedByForBankRule;
	}

	public Date getModifiedDateForBankRule() {
		return modifiedDateForBankRule;
	}

	public void setModifiedDateForBankRule(Date modifiedDateForBankRule) {
		this.modifiedDateForBankRule = modifiedDateForBankRule;
	}

	public String getApproveBy() {
		return approveBy;
	}

	public void setApproveBy(String approveBy) {
		this.approveBy = approveBy;
	}

	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	public String getApproveByForAlmullaCode() {
		return approveByForAlmullaCode;
	}

	public void setApproveByForAlmullaCode(String approveByForAlmullaCode) {
		this.approveByForAlmullaCode = approveByForAlmullaCode;
	}

	public Date getApproveDateForAlmullaCode() {
		return approveDateForAlmullaCode;
	}

	public void setApproveDateForAlmullaCode(Date approveDateForAlmullaCode) {
		this.approveDateForAlmullaCode = approveDateForAlmullaCode;
	}

	public String getApproveByForBankRule() {
		return approveByForBankRule;
	}

	public void setApproveByForBankRule(String approveByForBankRule) {
		this.approveByForBankRule = approveByForBankRule;
	}

	public Date getApproveDateForBankRule() {
		return approveDateForBankRule;
	}

	public void setApproveDateForBankRule(Date approveDateForBankRule) {
		this.approveDateForBankRule = approveDateForBankRule;
	}

	public Boolean getDisableSubmitButtonForBankRule() {
		return disableSubmitButtonForBankRule;
	}

	public void setDisableSubmitButtonForBankRule(
			Boolean disableSubmitButtonForBankRule) {
		this.disableSubmitButtonForBankRule = disableSubmitButtonForBankRule;
	}

	public String getDynamicLabelForActivateDeactivateForBankRule() {
		return dynamicLabelForActivateDeactivateForBankRule;
	}

	public void setDynamicLabelForActivateDeactivateForBankRule(
			String dynamicLabelForActivateDeactivateForBankRule) {
		this.dynamicLabelForActivateDeactivateForBankRule = dynamicLabelForActivateDeactivateForBankRule;
	}

	public String getIsActiveForBankRule() {
		return isActiveForBankRule;
	}

	public void setIsActiveForBankRule(String isActiveForBankRule) {
		this.isActiveForBankRule = isActiveForBankRule;
	}


	public String getCreatedByForBankRule() {
		return createdByForBankRule;
	}

	public void setCreatedByForBankRule(String createdByForBankRule) {
		this.createdByForBankRule = createdByForBankRule;
	}

	public Date getCreatedDateForBankRule() {
		return createdDateForBankRule;
	}

	public void setCreatedDateForBankRule(Date createdDateForBankRule) {
		this.createdDateForBankRule = createdDateForBankRule;
	}

	public BigDecimal getAdditionalBankRulePK() {
		return additionalBankRulePK;
	}

	public void setAdditionalBankRulePK(BigDecimal additionalBankRulePK) {
		this.additionalBankRulePK = additionalBankRulePK;
	}

	public String getDynamicLabelForActivateDeactivateForAlmullaCode() {
		return dynamicLabelForActivateDeactivateForAlmullaCode;
	}

	public void setDynamicLabelForActivateDeactivateForAlmullaCode(
			String dynamicLabelForActivateDeactivateForAlmullaCode) {
		this.dynamicLabelForActivateDeactivateForAlmullaCode = dynamicLabelForActivateDeactivateForAlmullaCode;
	}



	public BigDecimal getAdditionalBankRulaAlmullaCodePK() {
		return additionalBankRulaAlmullaCodePK;
	}

	public void setAdditionalBankRulaAlmullaCodePK(
			BigDecimal additionalBankRulaAlmullaCodePK) {
		this.additionalBankRulaAlmullaCodePK = additionalBankRulaAlmullaCodePK;
	}


	public String getIsActiveForAlmullaCode() {
		return isActiveForAlmullaCode;
	}

	public void setIsActiveForAlmullaCode(String isActiveForAlmullaCode) {
		this.isActiveForAlmullaCode = isActiveForAlmullaCode;
	}


	public String getCreatedByForAlmullaCode() {
		return createdByForAlmullaCode;
	}

	public void setCreatedByForAlmullaCode(String createdByForAlmullaCode) {
		this.createdByForAlmullaCode = createdByForAlmullaCode;
	}

	public Date getCreatedDateForAlmullaCode() {
		return createdDateForAlmullaCode;
	}

	public void setCreatedDateForAlmullaCode(Date createdDateForAlmullaCode) {
		this.createdDateForAlmullaCode = createdDateForAlmullaCode;
	}

	public BigDecimal getAdditionalBankRuleMapPk() {
		return AdditionalBankRuleMapPk;
	}

	public void setAdditionalBankRuleMapPk(BigDecimal additionalBankRuleMapPk) {
		AdditionalBankRuleMapPk = additionalBankRuleMapPk;
	}

	
	
	public String getDynamicLabelForActivateDeactivateForBankMap() {
		return dynamicLabelForActivateDeactivateForBankMap;
	}

	public void setDynamicLabelForActivateDeactivateForBankMap(
			String dynamicLabelForActivateDeactivateForBankMap) {
		this.dynamicLabelForActivateDeactivateForBankMap = dynamicLabelForActivateDeactivateForBankMap;
	}


	public AdditionalBankRuleDataTable() {

	}
	
	private ArrayList<AdditionalBankRuleDataTable> additionalBankRuleDataTable = new ArrayList<AdditionalBankRuleDataTable>();

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFlexField() {
		return flexField;
	}

	public void setFlexField(String flexField) {
		this.flexField = flexField;
	}

	public BigDecimal getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(BigDecimal orderNo) {
		this.orderNo = orderNo;
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

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public BigDecimal getAdditionalBankRuleId() {
		return AdditionalBankRuleId;
	}

	public void setAdditionalBankRuleId(BigDecimal additionalBankRuleId) {
		AdditionalBankRuleId = additionalBankRuleId;
	}

	public String getAmiecCode() {
		return amiecCode;
	}

	public void setAmiecCode(String amiecCode) {
		this.amiecCode = amiecCode;
	}

	public String getAmiecDescription() {
		return amiecDescription;
	}

	public void setAmiecDescription(String amiecDescription) {
		this.amiecDescription = amiecDescription;
	}

	public ArrayList<AdditionalBankRuleDataTable> getAdditionalBankRuleDataTable() {
		return additionalBankRuleDataTable;
	}

	public void setAdditionalBankRuleDataTable(
			ArrayList<AdditionalBankRuleDataTable> additionalBankRuleDataTable) {
		this.additionalBankRuleDataTable = additionalBankRuleDataTable;
	}

	public BigDecimal getCountryId() {
		return countryId;
	}

	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}

	public BigDecimal getBankId() {
		return bankId;
	}

	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAdditionalData() {
		return additionalData;
	}

	public void setAdditionalData(String additionalData) {
		this.additionalData = additionalData;
	}

	public String getAdditionalDescription() {
		return additionalDescription;
	}

	public void setAdditionalDescription(String additionalDescription) {
		this.additionalDescription = additionalDescription;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankDescription() {
		return bankDescription;
	}

	public void setBankDescription(String bankDescription) {
		this.bankDescription = bankDescription;
	}

	public BigDecimal getAdditionalBankRulePk() {
		return AdditionalBankRulePk;
	}

	public void setAdditionalBankRulePk(BigDecimal additionalBankRulePk) {
		AdditionalBankRulePk = additionalBankRulePk;
	}
	
	public BigDecimal getPkamaieBankMapping() {
		return pkamaieBankMapping;
	}

	public void setPkamaieBankMapping(BigDecimal pkamaieBankMapping) {
		this.pkamaieBankMapping = pkamaieBankMapping;
	}

	public String getDataActive() {
		return dataActive;
	}

	public void setDataActive(String dataActive) {
		this.dataActive = dataActive;
	}


	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getRemarksForBankMap() {
		return remarksForBankMap;
	}

	public void setRemarksForBankMap(String remarksForBankMap) {
		this.remarksForBankMap = remarksForBankMap;
	}

	public Boolean getRemarkCheckForBankMap() {
		return remarkCheckForBankMap;
	}

	public void setRemarkCheckForBankMap(Boolean remarkCheckForBankMap) {
		this.remarkCheckForBankMap = remarkCheckForBankMap;
	}

	public String getRemarksForAlmullaCode() {
		return remarksForAlmullaCode;
	}

	public void setRemarksForAlmullaCode(String remarksForAlmullaCode) {
		this.remarksForAlmullaCode = remarksForAlmullaCode;
	}

	public Boolean getRemarkCheckForAlmullaCode() {
		return remarkCheckForAlmullaCode;
	}

	public void setRemarkCheckForAlmullaCode(Boolean remarkCheckForAlmullaCode) {
		this.remarkCheckForAlmullaCode = remarkCheckForAlmullaCode;
	}

	public String getRemarksForBankRule() {
		return remarksForBankRule;
	}

	public void setRemarksForBankRule(String remarksForBankRule) {
		this.remarksForBankRule = remarksForBankRule;
	}

	public Boolean getRemarkCheckForBankRule() {
		return remarkCheckForBankRule;
	}

	public void setRemarkCheckForBankRule(Boolean remarkCheckForBankRule) {
		this.remarkCheckForBankRule = remarkCheckForBankRule;
	}

	  private Boolean booclearfanel = false;

	  public Boolean getBooclearfanel() {
		    return booclearfanel;
	  }

	  public void setBooclearfanel(Boolean booclearfanel) {
		    this.booclearfanel = booclearfanel;
	  }
	  
	  private Boolean booclearPanelForBankRule=false;

	  public Boolean getBooclearPanelForBankRule() {
	  	  return booclearPanelForBankRule;
	  }

	  public void setBooclearPanelForBankRule(Boolean booclearPanelForBankRule) {
	  	  this.booclearPanelForBankRule = booclearPanelForBankRule;
	  }
	
	
}
