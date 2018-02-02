package com.amg.exchange.remittance.bean;

import java.math.BigDecimal;
import java.util.Date;

public class RoutingSetUpDetailsDataTable {

	private BigDecimal routingSetUpHeaderId;
	private BigDecimal routingSetUpDetailsId;
	private BigDecimal applicationCountryId;
	private String countryName;
	private BigDecimal countryId;
	private String routingcountryName;
	private BigDecimal routingcountryId;
	private String routingbankName;
	private BigDecimal routingbankId;
	private String routingcurrencyName;
	private BigDecimal routingcurrencyId;
	private String routingServiceCode;
	private BigDecimal routingServiceId;
	private String routingRemittanceMode;
	private BigDecimal routingRemittanceId;
	private String routingDeliveryMode;
	private BigDecimal routingDeliveryId;
	private String branchApplicability;
	private String routingStatus;
	private String createdBy;
	private Date createdDate;
	private BigDecimal bankbranchId;
	private BigDecimal agentId;
	private Boolean isCheck=false;
	private Boolean booRenderApprovalDataTable=false;
	private Boolean booRenderDataTableApprovalExit=false;
	private String dynamicLabelForActivateDeactivate;
	private String routingbranchStatus;
	private String modifiedBy;
	private String approvedBy;
	private Date approvedDate;
	private Date modifiedDate;
	private String isActive;
	private String remarks;
	private Boolean renderEditButton = false;
	private Boolean booEditButton = false;
	private Boolean ifEditClicked = false;
	private Boolean remarksCheck = false;
	private Boolean activeRecordCheck = false;
	private Boolean permentDeleteCheck = false;
	private String isActiveStatus;


	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}
	public void setDynamicLabelForActivateDeactivate(String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public BigDecimal getRoutingSetUpHeaderId() {
		return routingSetUpHeaderId;
	}
	public void setRoutingSetUpHeaderId(BigDecimal routingSetUpHeaderId) {
		this.routingSetUpHeaderId = routingSetUpHeaderId;
	}

	public String getRoutingbankName() {
		return routingbankName;
	}
	public void setRoutingbankName(String routingbankName) {
		this.routingbankName = routingbankName;
	}

	public BigDecimal getRoutingbankId() {
		return routingbankId;
	}
	public void setRoutingbankId(BigDecimal routingbankId) {
		this.routingbankId = routingbankId;
	}

	public String getRoutingcurrencyName() {
		return routingcurrencyName;
	}
	public void setRoutingcurrencyName(String routingcurrencyName) {
		this.routingcurrencyName = routingcurrencyName;
	}

	public BigDecimal getRoutingcurrencyId() {
		return routingcurrencyId;
	}
	public void setRoutingcurrencyId(BigDecimal routingcurrencyId) {
		this.routingcurrencyId = routingcurrencyId;
	}

	public String getRoutingServiceCode() {
		return routingServiceCode;
	}
	public void setRoutingServiceCode(String routingServiceCode) {
		this.routingServiceCode = routingServiceCode;
	}

	public BigDecimal getRoutingServiceId() {
		return routingServiceId;
	}
	public void setRoutingServiceId(BigDecimal routingServiceId) {
		this.routingServiceId = routingServiceId;
	}

	public String getRoutingRemittanceMode() {
		return routingRemittanceMode;
	}
	public void setRoutingRemittanceMode(String routingRemittanceMode) {
		this.routingRemittanceMode = routingRemittanceMode;
	}

	public BigDecimal getRoutingRemittanceId() {
		return routingRemittanceId;
	}
	public void setRoutingRemittanceId(BigDecimal routingRemittanceId) {
		this.routingRemittanceId = routingRemittanceId;
	}

	public String getRoutingDeliveryMode() {
		return routingDeliveryMode;
	}
	public void setRoutingDeliveryMode(String routingDeliveryMode) {
		this.routingDeliveryMode = routingDeliveryMode;
	}

	public BigDecimal getRoutingDeliveryId() {
		return routingDeliveryId;
	}
	public void setRoutingDeliveryId(BigDecimal routingDeliveryId) {
		this.routingDeliveryId = routingDeliveryId;
	}

	public String getBranchApplicability() {
		return branchApplicability;
	}
	public void setBranchApplicability(String branchApplicability) {
		this.branchApplicability = branchApplicability;
	}

	public String getRoutingStatus() {
		return routingStatus;
	}
	public void setRoutingStatus(String routingStatus) {
		this.routingStatus = routingStatus;
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

	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public BigDecimal getCountryId() {
		return countryId;
	}
	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}

	public String getRoutingcountryName() {
		return routingcountryName;
	}
	public void setRoutingcountryName(String routingcountryName) {
		this.routingcountryName = routingcountryName;
	}

	public BigDecimal getRoutingcountryId() {
		return routingcountryId;
	}
	public void setRoutingcountryId(BigDecimal routingcountryId) {
		this.routingcountryId = routingcountryId;
	}

	public BigDecimal getBankbranchId() {
		return bankbranchId;
	}
	public void setBankbranchId(BigDecimal bankbranchId) {
		this.bankbranchId = bankbranchId;
	}

	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}
	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}

	public BigDecimal getAgentId() {
		return agentId;
	}
	public void setAgentId(BigDecimal agentId) {
		this.agentId = agentId;
	}

	public Boolean getIsCheck() {
		return isCheck;
	}
	public void setIsCheck(Boolean isCheck) {
		this.isCheck = isCheck;
	}

	public Boolean getBooRenderApprovalDataTable() {
		return booRenderApprovalDataTable;
	}
	public void setBooRenderApprovalDataTable(Boolean booRenderApprovalDataTable) {
		this.booRenderApprovalDataTable = booRenderApprovalDataTable;
	}

	public Boolean getBooRenderDataTableApprovalExit() {
		return booRenderDataTableApprovalExit;
	}
	public void setBooRenderDataTableApprovalExit(Boolean booRenderDataTableApprovalExit) {
		this.booRenderDataTableApprovalExit = booRenderDataTableApprovalExit;
	}

	public BigDecimal getRoutingSetUpDetailsId() {
		return routingSetUpDetailsId;
	}
	public void setRoutingSetUpDetailsId(BigDecimal routingSetUpDetailsId) {
		this.routingSetUpDetailsId = routingSetUpDetailsId;
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

	public Boolean getBooEditButton() {
		return booEditButton;
	}
	public void setBooEditButton(Boolean booEditButton) {
		this.booEditButton = booEditButton;
	}

	public Boolean getIfEditClicked() {
		return ifEditClicked;
	}
	public void setIfEditClicked(Boolean ifEditClicked) {
		this.ifEditClicked = ifEditClicked;
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

	public String getIsActiveStatus() {
		return isActiveStatus;
	}
	public void setIsActiveStatus(String isActiveStatus) {
		this.isActiveStatus = isActiveStatus;
	}
	
	public String getRoutingbranchStatus() {
		return routingbranchStatus;
	}
	public void setRoutingbranchStatus(String routingbranchStatus) {
		this.routingbranchStatus = routingbranchStatus;
	}

	
}
