package com.amg.exchange.common.bean;

import java.math.BigDecimal;

public class CountryBranchMasterDataTable {
	  private BigDecimal applicationCountryId;
	  private String branchCode;
	  private BigDecimal branchId;
	  private String accountCode;
	  private String branchEnglishFullDesc;
	  private String brachEnglishShortDesc;
	  private String branchArabicFullDesc;
	  private String branchArabicShortDesc;
	  private String corporateId;
	  private String corporateName;
	  private Boolean renderEditButton=false;
	  private String dynamicLabelForActivateDeactivate;
	  
	  public BigDecimal getApplicationCountryId() {
	  	  return applicationCountryId;
	  }
	  public void setApplicationCountryId(BigDecimal applicationCountryId) {
	  	  this.applicationCountryId = applicationCountryId;
	  }
	  public String getBranchCode() {
	  	  return branchCode;
	  }
	  public void setBranchCode(String branchCode) {
	  	  this.branchCode = branchCode;
	  }
	  public BigDecimal getBranchId() {
	  	  return branchId;
	  }
	  public void setBranchId(BigDecimal branchId) {
	  	  this.branchId = branchId;
	  }
	  public String getAccountCode() {
	  	  return accountCode;
	  }
	  public void setAccountCode(String accountCode) {
	  	  this.accountCode = accountCode;
	  }
	  public String getBranchEnglishFullDesc() {
	  	  return branchEnglishFullDesc;
	  }
	  public void setBranchEnglishFullDesc(String branchEnglishFullDesc) {
	  	  this.branchEnglishFullDesc = branchEnglishFullDesc;
	  }
	  public String getBrachEnglishShortDesc() {
	  	  return brachEnglishShortDesc;
	  }
	  public void setBrachEnglishShortDesc(String brachEnglishShortDesc) {
	  	  this.brachEnglishShortDesc = brachEnglishShortDesc;
	  }
	  public String getBranchArabicFullDesc() {
	  	  return branchArabicFullDesc;
	  }
	  public void setBranchArabicFullDesc(String branchArabicFullDesc) {
	  	  this.branchArabicFullDesc = branchArabicFullDesc;
	  }
	  public String getBranchArabicShortDesc() {
	  	  return branchArabicShortDesc;
	  }
	  public void setBranchArabicShortDesc(String branchArabicShortDesc) {
	  	  this.branchArabicShortDesc = branchArabicShortDesc;
	  }
	  public String getCorporateId() {
	  	  return corporateId;
	  }
	  public void setCorporateId(String corporateId) {
	  	  this.corporateId = corporateId;
	  }
	  public String getCorporateName() {
	  	  return corporateName;
	  }
	  public void setCorporateName(String corporateName) {
	  	  this.corporateName = corporateName;
	  }
	  public Boolean getRenderEditButton() {
	  	  return renderEditButton;
	  }
	  public void setRenderEditButton(Boolean renderEditButton) {
	  	  this.renderEditButton = renderEditButton;
	  }
	  public String getDynamicLabelForActivateDeactivate() {
	  	  return dynamicLabelForActivateDeactivate;
	  }
	  public void setDynamicLabelForActivateDeactivate(String dynamicLabelForActivateDeactivate) {
	  	  this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	  }
	  
}
