package com.amg.exchange.complaint.bean;

import java.math.BigDecimal;
import java.util.Date;

public class ComplaintMatrixDataTable {

	private BigDecimal countryId;
	private BigDecimal bankId;
	private BigDecimal serviceId;
	private BigDecimal complaintTypeId;
	private String takenBy;
	private String actionTakenBy;
	private BigDecimal action;
	private BigDecimal communicationMethod;
	private BigDecimal complaintDestinationId;
	
	private BigDecimal complaintTakenById;
	private String complaintTypeCode;
	private String complaintTakenByCode;
	private String complaintActionCode;
	private String complaintDestinationCode;
	private String communicationMethodCode;
	
	private String bankName;
	private String countryName;
	private String serviceName;
	
	private Boolean booRenderDataTable=false;
	private Boolean booRenderSaveExit=false;
	
	private BigDecimal applicationCountryId;
	
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	private BigDecimal complaintActionId;
	
	public BigDecimal getComplaintTakenById() {
		return complaintTakenById;
	}
	public void setComplaintTakenById(BigDecimal complaintTakenById) {
		this.complaintTakenById = complaintTakenById;
	}
	public String getComplaintTypeCode() {
		return complaintTypeCode;
	}
	public void setComplaintTypeCode(String complaintTypeCode) {
		this.complaintTypeCode = complaintTypeCode;
	}
	public String getComplaintTakenByCode() {
		return complaintTakenByCode;
	}
	public void setComplaintTakenByCode(String complaintTakenByCode) {
		this.complaintTakenByCode = complaintTakenByCode;
	}
	public String getComplaintActionCode() {
		return complaintActionCode;
	}
	public void setComplaintActionCode(String complaintActionCode) {
		this.complaintActionCode = complaintActionCode;
	}
	public String getComplaintDestinationCode() {
		return complaintDestinationCode;
	}
	public void setComplaintDestinationCode(String complaintDestinationCode) {
		this.complaintDestinationCode = complaintDestinationCode;
	}
	public String getCommunicationMethodCode() {
		return communicationMethodCode;
	}
	public void setCommunicationMethodCode(String communicationMethodCode) {
		this.communicationMethodCode = communicationMethodCode;
	}
	public BigDecimal getComplaintDestinationId() {
		return complaintDestinationId;
	}
	public void setComplaintDestinationId(BigDecimal complaintDestinationId) {
		this.complaintDestinationId = complaintDestinationId;
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
	public BigDecimal getServiceId() {
		return serviceId;
	}
	public void setServiceId(BigDecimal serviceId) {
		this.serviceId = serviceId;
	}
	public BigDecimal getComplaintTypeId() {
		return complaintTypeId;
	}
	public void setComplaintTypeId(BigDecimal complaintTypeId) {
		this.complaintTypeId = complaintTypeId;
	}
	public String getTakenBy() {
		return takenBy;
	}
	public void setTakenBy(String takenBy) {
		this.takenBy = takenBy;
	}
	public String getActionTakenBy() {
		return actionTakenBy;
	}
	public void setActionTakenBy(String actionTakenBy) {
		this.actionTakenBy = actionTakenBy;
	}
	public BigDecimal getAction() {
		return action;
	}
	public void setAction(BigDecimal action) {
		this.action = action;
	}
	public BigDecimal getCommunicationMethod() {
		return communicationMethod;
	}
	public void setCommunicationMethod(BigDecimal communicationMethod) {
		this.communicationMethod = communicationMethod;
	}
	
	public BigDecimal getComplaintActionId() {
		return complaintActionId;
	}
	
	public void setComplaintActionId(BigDecimal complaintActionId) {
		this.complaintActionId = complaintActionId;
	}

	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
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
	
	/*Started koti Varible Declaration 05/08/15*/
	private BigDecimal appCountryId;
	private BigDecimal complaintMatrixPk;
	
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String isActive;
	private String remarks;
	
	private String dynamicLabelForActivateDeactivate;
	 private Boolean booRenderClear=false;
	 private Boolean ifEditClicked=false;
	 private Boolean renderEditButton=false;
	 private Boolean booEditButton=false;	
	 private Boolean booSubmitPanel=false;
	 private Boolean permanetDeleteCheck=false;
	  private Boolean remarksCheck=false;
	  private Boolean activateRecordCheck=false;
	  
        public Boolean getActivateRecordCheck() {
	  	  return activateRecordCheck;
	  }
	  public void setActivateRecordCheck(Boolean activateRecordCheck) {
	  	  this.activateRecordCheck = activateRecordCheck;
	  }
	  public Boolean getPermanetDeleteCheck() {
	  	  return permanetDeleteCheck;
	  }
	  public void setPermanetDeleteCheck(Boolean permanetDeleteCheck) {
	  	  this.permanetDeleteCheck = permanetDeleteCheck;
	  }
	  public Boolean getRemarksCheck() {
	  	  return remarksCheck;
	  }
	  public void setRemarksCheck(Boolean remarksCheck) {
	  	  this.remarksCheck = remarksCheck;
	  }
	  public Boolean getBooSubmitPanel() {
			  return booSubmitPanel;
		}
		public void setBooSubmitPanel(Boolean booSubmitPanel) {
			  this.booSubmitPanel = booSubmitPanel;
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
		public Boolean getIfEditClicked() {
		  return ifEditClicked;
		}
		public void setIfEditClicked(Boolean ifEditClicked) {
		  this.ifEditClicked = ifEditClicked;
		}
	public String getDynamicLabelForActivateDeactivate() {
	  return dynamicLabelForActivateDeactivate;
	}
	public void setDynamicLabelForActivateDeactivate(String dynamicLabelForActivateDeactivate) {
	  this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}
	public Boolean getBooRenderClear() {
	  return booRenderClear;
	}
	public void setBooRenderClear(Boolean booRenderClear) {
	  this.booRenderClear = booRenderClear;
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
	public BigDecimal getAppCountryId() {
	  return appCountryId;
		}
	public void setAppCountryId(BigDecimal appCountryId) {
	  this.appCountryId = appCountryId;
	}
	public BigDecimal getComplaintMatrixPk() {
	  return complaintMatrixPk;
	}
	public void setComplaintMatrixPk(BigDecimal complaintMatrixPk) {
	  this.complaintMatrixPk = complaintMatrixPk;
	}
        public BigDecimal getApplicationCountryId() {
        	  return applicationCountryId;
        }
        public void setApplicationCountryId(BigDecimal applicationCountryId) {
        	  this.applicationCountryId = applicationCountryId;
        }
	
	
	
	
	/*Ended koti Varible Declaration 05/08/15*/

}
