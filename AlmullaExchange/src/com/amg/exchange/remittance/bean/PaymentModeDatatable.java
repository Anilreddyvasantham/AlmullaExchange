package com.amg.exchange.remittance.bean;

import java.math.BigDecimal;
import java.util.Date;

public class PaymentModeDatatable {
	
	private String modeOfPayment;
	private String modeOfPaymentDesc;
	private String modeOfPaymentInLocal;
	private String modeOfPaymentDescInLocal;
	private BigDecimal dtpymPk;
	private BigDecimal dtpymDescpken;
	private BigDecimal dtpymDescpkarb;
	private String createdBy;
	private Date createdDate;
	private String isActive;
	private Boolean renderEditButton=false;
	private String dynamicLabelForActivateDeactivate;
	private String remarks;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private Boolean booUpdateCheck;
	private BigDecimal engLanguageId;
	private BigDecimal araLanguageId;
	private Boolean permanetDeleteCheck=false;
	private Boolean activateRecordCheck=false;

	

	public String getModeOfPayment() {
		return modeOfPayment;
	}

	public void setModeOfPayment(String modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
	}

	public String getModeOfPaymentDesc() {
		return modeOfPaymentDesc;
	}

	public void setModeOfPaymentDesc(String modeOfPaymentDesc) {
		this.modeOfPaymentDesc = modeOfPaymentDesc;
	}

	public String getModeOfPaymentInLocal() {
		return modeOfPaymentInLocal;
	}

	public void setModeOfPaymentInLocal(String modeOfPaymentInLocal) {
		this.modeOfPaymentInLocal = modeOfPaymentInLocal;
	}

	public String getModeOfPaymentDescInLocal() {
		return modeOfPaymentDescInLocal;
	}

	public void setModeOfPaymentDescInLocal(String modeOfPaymentDescInLocal) {
		this.modeOfPaymentDescInLocal = modeOfPaymentDescInLocal;
	}

	public BigDecimal getDtpymPk() {
		return dtpymPk;
	}

	public void setDtpymPk(BigDecimal dtpymPk) {
		this.dtpymPk = dtpymPk;
	}

	public BigDecimal getDtpymDescpken() {
		return dtpymDescpken;
	}

	public void setDtpymDescpken(BigDecimal dtpymDescpken) {
		this.dtpymDescpken = dtpymDescpken;
	}

	public BigDecimal getDtpymDescpkarb() {
		return dtpymDescpkarb;
	}

	public void setDtpymDescpkarb(BigDecimal dtpymDescpkarb) {
		this.dtpymDescpkarb = dtpymDescpkarb;
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

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Boolean getRenderEditButton() {
		return renderEditButton;
	}
	public void setRenderEditButton(Boolean renderEditButton) {
		this.renderEditButton = renderEditButton;
	}
	

	public String getModifiedBy() {
		return modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}

	public void setDynamicLabelForActivateDeactivate(
			String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}

	public Boolean getBooUpdateCheck() {
		return booUpdateCheck;
	}

	public void setBooUpdateCheck(Boolean booUpdateCheck) {
		this.booUpdateCheck = booUpdateCheck;
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


	
	
}
