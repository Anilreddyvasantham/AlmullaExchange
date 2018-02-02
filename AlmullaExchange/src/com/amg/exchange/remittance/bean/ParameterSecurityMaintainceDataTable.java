package com.amg.exchange.remittance.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ParameterSecurityMaintainceDataTable implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal parameterId;
	private String parameterRecordId;
	private String parameterDesc;
	private BigDecimal countryBranchid;
	private String countryBranchName;
	private BigDecimal parameterGrantPk;
	private BigDecimal applicationCountryId;
	private String countryName;
	private BigDecimal userId;
	private String userName;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String remarks;
	private String isActive;
	private Boolean booRenderDataTable=false;
	private Boolean booRenderSaveExit=false;
	private boolean selectCheckBox=false;
	private String recordId;





	public BigDecimal getParameterId() {
		return parameterId;
	}
	public void setParameterId(BigDecimal parameterId) {
		this.parameterId = parameterId;
	}
	public String getParameterDesc() {
		return parameterDesc;
	}
	public void setParameterDesc(String parameterDesc) {
		this.parameterDesc = parameterDesc;
	}

	public BigDecimal getCountryBranchid() {
		return countryBranchid;
	}
	public void setCountryBranchid(BigDecimal countryBranchid) {
		this.countryBranchid = countryBranchid;
	}
	public String getCountryBranchName() {
		return countryBranchName;
	}
	public void setCountryBranchName(String countryBranchName) {
		this.countryBranchName = countryBranchName;
	}
	public Boolean getBooRenderDataTable() {
		return booRenderDataTable;
	}
	public void setBooRenderDataTable(Boolean booRenderDataTable) {
		this.booRenderDataTable = booRenderDataTable;
	}
	public Boolean getBooRenderSaveExit() {
		return booRenderSaveExit;
	}
	public void setBooRenderSaveExit(Boolean booRenderSaveExit) {
		this.booRenderSaveExit = booRenderSaveExit;
	}
	public BigDecimal getParameterGrantPk() {
		return parameterGrantPk;
	}
	public void setParameterGrantPk(BigDecimal parameterGrantPk) {
		this.parameterGrantPk = parameterGrantPk;
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
	public BigDecimal getUserId() {
		return userId;
	}
	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public boolean isSelectCheckBox() {
		return selectCheckBox;
	}
	public void setSelectCheckBox(boolean selectCheckBox) {
		this.selectCheckBox = selectCheckBox;
	}
	public String getRecordId() {
		return recordId;
	}
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	public String getParameterRecordId() {
		return parameterRecordId;
	}
	public void setParameterRecordId(String parameterRecordId) {
		this.parameterRecordId = parameterRecordId;
	}

}
