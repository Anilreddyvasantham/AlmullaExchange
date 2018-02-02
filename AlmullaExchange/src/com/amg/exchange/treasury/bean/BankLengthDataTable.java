package com.amg.exchange.treasury.bean;

import java.math.BigDecimal;

public class BankLengthDataTable {
	
	private BigDecimal bankId;
	private BigDecimal bankLengthId;
	private BigDecimal bankLength;
	private String statusRecord;
	private String dynamicLabelForActivateDeactivate;
	private Boolean remarksCheck=false;
	private Boolean activeRecordCheck=false;
	private Boolean permentDeleteCheck=false;
	private String remarks;
	private String recordStatus;
	private Boolean booEditButton=false;
	private Boolean renderEditButton=false;
	
	public BigDecimal getBankId() {
		return bankId;
	}
	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}
	public BigDecimal getBankLengthId() {
		return bankLengthId;
	}
	public void setBankLengthId(BigDecimal bankLengthId) {
		this.bankLengthId = bankLengthId;
	}
	public BigDecimal getBankLength() {
		return bankLength;
	}
	public void setBankLength(BigDecimal bankLength) {
		this.bankLength = bankLength;
	}
	public String getStatusRecord() {
		return statusRecord;
	}
	public void setStatusRecord(String statusRecord) {
		this.statusRecord = statusRecord;
	}
	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}
	public void setDynamicLabelForActivateDeactivate(
			String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}
	public Boolean getRemarksCheck() {
		return remarksCheck;
	}
	public void setRemarksCheck(Boolean remarksCheck) {
		this.remarksCheck = remarksCheck;
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
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getRecordStatus() {
		return recordStatus;
	}
	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}
	public Boolean getBooEditButton() {
		return booEditButton;
	}
	public void setBooEditButton(Boolean booEditButton) {
		this.booEditButton = booEditButton;
	}
	public Boolean getRenderEditButton() {
		return renderEditButton;
	}
	public void setRenderEditButton(Boolean renderEditButton) {
		this.renderEditButton = renderEditButton;
	}
	
	


	
}
