package com.amg.exchange.remittance.bean;

import java.math.BigDecimal;
import java.util.Date;

public class RelationsDatatable {

	private String relationsId = null;
	private String englishRelationsDesc = null;
	private String arabicRelationsDesc = null;
	private BigDecimal relationsPk;
	private BigDecimal relationEnglishPk;
	private BigDecimal relationArabicPk;

	private String createdBy = null;
	private Date createdDate = null;
	private String modifiedBy = null;
	private Date modifiedDate = null;
	private String isActive = null;
	private String approvedBy = null;
	private String remarks;
	private Boolean checkSave;
	private String dynamicLabelForActivateDeactivate;
	private Boolean renderEditButton = false;
	private Boolean remarkCheck;
	private Boolean booCheckDelete;
	private Boolean booCheckUpdate;
	private Boolean booDisableEdit;

	public Boolean getRenderEditButton() {
		return renderEditButton;
	}

	public void setRenderEditButton(Boolean renderEditButton) {
		this.renderEditButton = renderEditButton;
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

	private Date approvedDate = null;

	public BigDecimal getRelationEnglishPk() {
		return relationEnglishPk;
	}

	public BigDecimal getRelationArabicPk() {
		return relationArabicPk;
	}

	public void setRelationEnglishPk(BigDecimal relationEnglishPk) {
		this.relationEnglishPk = relationEnglishPk;
	}

	public void setRelationArabicPk(BigDecimal relationArabicPk) {
		this.relationArabicPk = relationArabicPk;
	}

	public BigDecimal getRelationsPk() {
		return relationsPk;
	}

	public void setRelationsPk(BigDecimal relationsPk) {
		this.relationsPk = relationsPk;
	}

	public String getRelationsId() {
		return relationsId;
	}

	public void setRelationsId(String relationsId) {
		this.relationsId = relationsId;
	}

	public String getEnglishRelationsDesc() {
		return englishRelationsDesc;
	}

	public void setEnglishRelationsDesc(String englishRelationsDesc) {
		this.englishRelationsDesc = englishRelationsDesc;
	}

	public String getArabicRelationsDesc() {
		return arabicRelationsDesc;
	}

	public void setArabicRelationsDesc(String arabicRelationsDesc) {
		this.arabicRelationsDesc = arabicRelationsDesc;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public Boolean getCheckSave() {
		return checkSave;
	}

	public void setCheckSave(Boolean checkSave) {
		this.checkSave = checkSave;
	}

	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}

	public void setDynamicLabelForActivateDeactivate(
			String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Boolean getRemarkCheck() {
		return remarkCheck;
	}

	public void setRemarkCheck(Boolean remarkCheck) {
		this.remarkCheck = remarkCheck;
	}

	public Boolean getBooCheckDelete() {
		return booCheckDelete;
	}

	public void setBooCheckDelete(Boolean booCheckDelete) {
		this.booCheckDelete = booCheckDelete;
	}

	public Boolean getBooCheckUpdate() {
		return booCheckUpdate;
	}

	public void setBooCheckUpdate(Boolean booCheckUpdate) {
		this.booCheckUpdate = booCheckUpdate;
	}

	public Boolean getBooDisableEdit() {
		return booDisableEdit;
	}

	public void setBooDisableEdit(Boolean booDisableEdit) {
		this.booDisableEdit = booDisableEdit;
	}

}
