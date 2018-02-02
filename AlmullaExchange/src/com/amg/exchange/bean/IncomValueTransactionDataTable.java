package com.amg.exchange.bean;

import java.math.BigDecimal;
import java.util.Date;

public class IncomValueTransactionDataTable {

	private BigDecimal incomeValueTransactionId;
	private BigDecimal appCountryId;
	private BigDecimal valuePerTranx;
	private BigDecimal valuePerDay;
	private BigDecimal valuePerWeek;
	private BigDecimal valuePerMonth;
	private BigDecimal valuePerAnnum;
	private BigDecimal noofTranxPerDay;
	private BigDecimal noofTranxPerWeek;
	private BigDecimal noofTranxPerMonth;
	private BigDecimal noofTranxPerAnnum;
	private String isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;
	private BigDecimal incomeRangeFrom;
	private BigDecimal incomeRangeTo;
	
	private Boolean remarksCheck = false;
	private Boolean activateRecordCheck = false;

	 private Boolean booCheckDelete = false;

	private String dynamicLabelForActivateDeactivate;

	public IncomValueTransactionDataTable() {
		// TODO Auto-generated constructor stub
	}

	public BigDecimal getIncomeValueTransactionId() {
		return incomeValueTransactionId;
	}

	public void setIncomeValueTransactionId(BigDecimal incomeValueTransactionId) {
		this.incomeValueTransactionId = incomeValueTransactionId;
	}

	public BigDecimal getAppCountryId() {
		return appCountryId;
	}

	public void setAppCountryId(BigDecimal appCountryId) {
		this.appCountryId = appCountryId;
	}

	public BigDecimal getValuePerTranx() {
		return valuePerTranx;
	}

	public void setValuePerTranx(BigDecimal valuePerTranx) {
		this.valuePerTranx = valuePerTranx;
	}

	public BigDecimal getValuePerDay() {
		return valuePerDay;
	}

	public void setValuePerDay(BigDecimal valuePerDay) {
		this.valuePerDay = valuePerDay;
	}

	public BigDecimal getValuePerWeek() {
		return valuePerWeek;
	}

	public void setValuePerWeek(BigDecimal valuePerWeek) {
		this.valuePerWeek = valuePerWeek;
	}

	public BigDecimal getValuePerMonth() {
		return valuePerMonth;
	}

	public void setValuePerMonth(BigDecimal valuePerMonth) {
		this.valuePerMonth = valuePerMonth;
	}

	public BigDecimal getValuePerAnnum() {
		return valuePerAnnum;
	}

	public void setValuePerAnnum(BigDecimal valuePerAnnum) {
		this.valuePerAnnum = valuePerAnnum;
	}

	public BigDecimal getNoofTranxPerDay() {
		return noofTranxPerDay;
	}

	public void setNoofTranxPerDay(BigDecimal noofTranxPerDay) {
		this.noofTranxPerDay = noofTranxPerDay;
	}

	public BigDecimal getNoofTranxPerWeek() {
		return noofTranxPerWeek;
	}

	public void setNoofTranxPerWeek(BigDecimal noofTranxPerWeek) {
		this.noofTranxPerWeek = noofTranxPerWeek;
	}

	public BigDecimal getNoofTranxPerMonth() {
		return noofTranxPerMonth;
	}

	public void setNoofTranxPerMonth(BigDecimal noofTranxPerMonth) {
		this.noofTranxPerMonth = noofTranxPerMonth;
	}

	public BigDecimal getNoofTranxPerAnnum() {
		return noofTranxPerAnnum;
	}

	public void setNoofTranxPerAnnum(BigDecimal noofTranxPerAnnum) {
		this.noofTranxPerAnnum = noofTranxPerAnnum;
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

	public BigDecimal getIncomeRangeFrom() {
		return incomeRangeFrom;
	}

	public void setIncomeRangeFrom(BigDecimal incomeRangeFrom) {
		this.incomeRangeFrom = incomeRangeFrom;
	}

	public BigDecimal getIncomeRangeTo() {
		return incomeRangeTo;
	}

	public void setIncomeRangeTo(BigDecimal incomeRangeTo) {
		this.incomeRangeTo = incomeRangeTo;
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

	
}
