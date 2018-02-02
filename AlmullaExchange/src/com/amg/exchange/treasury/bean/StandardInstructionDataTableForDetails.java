package com.amg.exchange.treasury.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.amg.exchange.treasury.model.BankAccountDetails;

public class StandardInstructionDataTableForDetails implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private BigDecimal standardInstrnDetailsId;
	private BigDecimal exstandardInstructionId;
	private BigDecimal exStandardInstructionForAllicationCountry;
	private BigDecimal fsCompanyMaster;
	private BigDecimal exBankMaster;
	private BigDecimal exCurrenyMaster;
	private BigDecimal lineNumber;
	private String lineDescription;
	private String isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private BigDecimal standardInsructionNumber;
	private Boolean statusModify;
	private String intructionType;
	private String approveBy;
	private Date approveDate;
	private BigDecimal bankAccountDetailsId;
//	private Boolean renderDeleteButton=true;
	private Boolean deleteCheckForSave = false;
	private String dynamicLabelForActivateDeactivate;
	
	 private Boolean activateRecordCheck = false;
	 private Boolean booCheckDelete = false;
	 private Boolean remarkCheck=false;
	 
	private String remarks;
	private String activateBy;
	private Date activateDate;
	private Boolean readOnlyDescription=false;
	private Boolean editRender = false;
	private Boolean instructionModiFiCheck=false;
	
	
	public Boolean getInstructionModiFiCheck() {
		return instructionModiFiCheck;
	}

	public void setInstructionModiFiCheck(Boolean instructionModiFiCheck) {
		this.instructionModiFiCheck = instructionModiFiCheck;
	}

	public Boolean getEditRender() {
		return editRender;
	}

	public void setEditRender(Boolean editRender) {
		this.editRender = editRender;
	}

	public String getRemarks() {
			return remarks;
		}

		public String getActivateBy() {
			return activateBy;
		}

		public Date getActivateDate() {
			return activateDate;
		}

		public void setRemarks(String remarks) {
			this.remarks = remarks;
		}

		public void setActivateBy(String activateBy) {
			this.activateBy = activateBy;
		}

		public void setActivateDate(Date activateDate) {
			this.activateDate = activateDate;
		}

	public Boolean getActivateRecordCheck() {
		return activateRecordCheck;
	}

	public Boolean getBooCheckDelete() {
		return booCheckDelete;
	}

	public Boolean getRemarkCheck() {
		return remarkCheck;
	}

	public void setActivateRecordCheck(Boolean activateRecordCheck) {
		this.activateRecordCheck = activateRecordCheck;
	}

	public void setBooCheckDelete(Boolean booCheckDelete) {
		this.booCheckDelete = booCheckDelete;
	}

	public void setRemarkCheck(Boolean remarkCheck) {
		this.remarkCheck = remarkCheck;
	}

	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}

	public void setDynamicLabelForActivateDeactivate(
			String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}

	public Boolean getDeleteCheckForSave() {
		return deleteCheckForSave;
	}

	public void setDeleteCheckForSave(Boolean deleteCheckForSave) {
		this.deleteCheckForSave = deleteCheckForSave;
	}

	/*public Boolean getRenderDeleteButton() {
		return renderDeleteButton;
	}

	public void setRenderDeleteButton(Boolean renderDeleteButton) {
		this.renderDeleteButton = renderDeleteButton;
	}*/

	
	
	public BigDecimal getBankAccountDetailsId() {
		return bankAccountDetailsId;
	}

	public void setBankAccountDetailsId(BigDecimal bankAccountDetailsId) {
		this.bankAccountDetailsId = bankAccountDetailsId;
	}

	public String getApproveBy() {
		return approveBy;
	}

	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveBy(String approveBy) {
		this.approveBy = approveBy;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	public String getIntructionType() {
		return intructionType;
	}

	public void setIntructionType(String intructionType) {
		this.intructionType = intructionType;
	}

	public BigDecimal getStandardInstrnDetailsId() {
		return standardInstrnDetailsId;
	}
	
	public void setStandardInstrnDetailsId(BigDecimal standardInstrnDetailsId) {
		this.standardInstrnDetailsId = standardInstrnDetailsId;
	}
	
	public BigDecimal getExstandardInstructionId() {
		return exstandardInstructionId;
	}
	
	public void setExstandardInstructionId(BigDecimal exstandardInstructionId) {
		this.exstandardInstructionId = exstandardInstructionId;
	}
	
	public BigDecimal getExStandardInstructionForAllicationCountry() {
		return exStandardInstructionForAllicationCountry;
	}
	
	public void setExStandardInstructionForAllicationCountry(BigDecimal exStandardInstructionForAllicationCountry) {
		this.exStandardInstructionForAllicationCountry = exStandardInstructionForAllicationCountry;
	}
	
	public BigDecimal getFsCompanyMaster() {
		return fsCompanyMaster;
	}
	
	public void setFsCompanyMaster(BigDecimal fsCompanyMaster) {
		this.fsCompanyMaster = fsCompanyMaster;
	}
	
	public BigDecimal getExBankMaster() {
		return exBankMaster;
	}
	
	public void setExBankMaster(BigDecimal exBankMaster) {
		this.exBankMaster = exBankMaster;
	}
	
	public BigDecimal getExCurrenyMaster() {
		return exCurrenyMaster;
	}
	
	public void setExCurrenyMaster(BigDecimal exCurrenyMaster) {
		this.exCurrenyMaster = exCurrenyMaster;
	}
	
	public BigDecimal getLineNumber() {
		return lineNumber;
	}
	
	public void setLineNumber(BigDecimal lineNumber) {
		this.lineNumber = lineNumber;
	}
	
	public String getLineDescription() {
		return lineDescription;
	}
	
	public void setLineDescription(String lineDescription) {
		this.lineDescription = lineDescription;
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
	
	public BigDecimal getStandardInsructionNumber() {
		return standardInsructionNumber;
	}
	
	public void setStandardInsructionNumber(BigDecimal standardInsructionNumber) {
		this.standardInsructionNumber = standardInsructionNumber;
	}
	
	public Boolean getStatusModify() {
		return statusModify;
	}
	
	public void setStatusModify(Boolean statusModify) {
		this.statusModify = statusModify;
	}

	public Boolean getReadOnlyDescription() {
		return readOnlyDescription;
	}

	public void setReadOnlyDescription(Boolean readOnlyDescription) {
		this.readOnlyDescription = readOnlyDescription;
	}
	
}
