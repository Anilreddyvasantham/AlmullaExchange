package com.amg.exchange.remittance.bean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.amg.exchange.beneficiary.model.BankBranchView;
import com.amg.exchange.treasury.model.BankAccountDetails;

public class RoutingSetUpDetailsDialogCheckBox {

	private BigDecimal routingSetupHeaderId;
	private BigDecimal routingSetupDetailsId;
	private BigDecimal bankBranchId;
	private BigDecimal bankId;
	private BigDecimal countryId;
	private BigDecimal bankbranchCode;
	private BigDecimal agentId;
	private String branchFullName;
	private Boolean selectRecord=false;
	private String branchApplicability;
	private String routingStatus;
	private List<PopulateDataWithCode> lstRoutingSetupBankDetails;
	private List<BankBranchView> lstBankBranchView;
	private List<BankBranchView> lstAgentBankBranchView;
	private String agentBankCode;
	private String agentBankFullName;
	private BigDecimal agentBankIndicatorId;
	private BigDecimal mappingRoutingBranch;
	private BigDecimal routingBranchId;
	private BigDecimal agentBranchId;
	private String isActive;
	private String dynamicLabelActivateDeActivate;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String dataTableStatus;
	private Boolean remarksCheck=false;
	private Boolean permentDeleteCheck=false;
	private Boolean activeRecordCheck=false;
	private Boolean booSlectCheck=false;
	private String routingbranchStatus;
	private String bankName;
	private String currencyName;
	private String countryName;
	private BigDecimal currencyId;
	private BigDecimal routingServciceId;
	private String routingServiceName;
	private BigDecimal routingcountryId;
	private String routingcountryName;
	private BigDecimal routingbankId;
	private String routingbankName;
	private BigDecimal routingDeliveryId;
	private String routingDeliveryMode;
	private BigDecimal routingRemittanceId;
	private String routingRemittanceMode;
	private BigDecimal applicationCountryId;
	private String routingcurrencyName;

	public String getAgentBankCode() {
		return agentBankCode;
	}
	public String getAgentBankFullName() {
		return agentBankFullName;
	}

	public BigDecimal getAgentBankIndicatorId() {
		return agentBankIndicatorId;
	}
	public void setAgentBankCode(String agentBankCode) {
		this.agentBankCode = agentBankCode;
	}

	public void setAgentBankFullName(String agentBankFullName) {
		this.agentBankFullName = agentBankFullName;
	}
	public void setAgentBankIndicatorId(BigDecimal agentBankIndicatorId) {
		this.agentBankIndicatorId = agentBankIndicatorId;
	}

	public BigDecimal getBankBranchId() {
		return bankBranchId;
	}
	public void setBankBranchId(BigDecimal bankBranchId) {
		this.bankBranchId = bankBranchId;
	}

	public BigDecimal getBankId() {
		return bankId;
	}
	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}

	public BigDecimal getCountryId() {
		return countryId;
	}
	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}

	public BigDecimal getBankbranchCode() {
		return bankbranchCode;
	}
	public void setBankbranchCode(BigDecimal bankbranchCode) {
		this.bankbranchCode = bankbranchCode;
	}

	public String getBranchFullName() {
		return branchFullName;
	}
	public void setBranchFullName(String branchFullName) {
		this.branchFullName = branchFullName;
	}

	public Boolean getSelectRecord() {
		return selectRecord;
	}
	public void setSelectRecord(Boolean selectRecord) {
		this.selectRecord = selectRecord;
	}

	public BigDecimal getRoutingSetupDetailsId() {
		return routingSetupDetailsId;
	}
	public void setRoutingSetupDetailsId(BigDecimal routingSetupDetailsId) {
		this.routingSetupDetailsId = routingSetupDetailsId;
	}

	public BigDecimal getAgentId() {
		return agentId;
	}
	public void setAgentId(BigDecimal agentId) {
		this.agentId = agentId;
	}

	public String getBranchApplicability() {
		return branchApplicability;
	}
	public void setBranchApplicability(String branchApplicability) {
		this.branchApplicability = branchApplicability;
	}

	public BigDecimal getRoutingSetupHeaderId() {
		return routingSetupHeaderId;
	}
	public void setRoutingSetupHeaderId(BigDecimal routingSetupHeaderId) {
		this.routingSetupHeaderId = routingSetupHeaderId;
	}

	public String getRoutingStatus() {
		return routingStatus;
	}
	public void setRoutingStatus(String routingStatus) {
		this.routingStatus = routingStatus;
	}

	public List<PopulateDataWithCode> getLstRoutingSetupBankDetails() {
		return lstRoutingSetupBankDetails;
	}
	public void setLstRoutingSetupBankDetails(List<PopulateDataWithCode> lstRoutingSetupBankDetails) {
		this.lstRoutingSetupBankDetails = lstRoutingSetupBankDetails;
	}

	public List<BankBranchView> getLstBankBranchView() {
		return lstBankBranchView;
	}
	public void setLstBankBranchView(List<BankBranchView> lstBankBranchView) {
		this.lstBankBranchView = lstBankBranchView;
	}

	public BigDecimal getMappingRoutingBranch() {
		return mappingRoutingBranch;
	}
	public void setMappingRoutingBranch(BigDecimal mappingRoutingBranch) {
		this.mappingRoutingBranch = mappingRoutingBranch;
	}

	public List<BankBranchView> getLstAgentBankBranchView() {
		return lstAgentBankBranchView;
	}
	public void setLstAgentBankBranchView(List<BankBranchView> lstAgentBankBranchView) {
		this.lstAgentBankBranchView = lstAgentBankBranchView;
	}

	public BigDecimal getRoutingBranchId() {
		return routingBranchId;
	}
	public void setRoutingBranchId(BigDecimal routingBranchId) {
		this.routingBranchId = routingBranchId;
	}

	public BigDecimal getAgentBranchId() {
		return agentBranchId;
	}
	public void setAgentBranchId(BigDecimal agentBranchId) {
		this.agentBranchId = agentBranchId;
	}

	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getDynamicLabelActivateDeActivate() {
		return dynamicLabelActivateDeActivate;
	}
	public void setDynamicLabelActivateDeActivate(String dynamicLabelActivateDeActivate) {
		this.dynamicLabelActivateDeActivate = dynamicLabelActivateDeActivate;
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

	public String getDataTableStatus() {
		return dataTableStatus;
	}
	public void setDataTableStatus(String dataTableStatus) {
		this.dataTableStatus = dataTableStatus;
	}

	public Boolean getRemarksCheck() {
		return remarksCheck;
	}
	public void setRemarksCheck(Boolean remarksCheck) {
		this.remarksCheck = remarksCheck;
	}

	public Boolean getPermentDeleteCheck() {
		return permentDeleteCheck;
	}
	public void setPermentDeleteCheck(Boolean permentDeleteCheck) {
		this.permentDeleteCheck = permentDeleteCheck;
	}

	public Boolean getActiveRecordCheck() {
		return activeRecordCheck;
	}
	public void setActiveRecordCheck(Boolean activeRecordCheck) {
		this.activeRecordCheck = activeRecordCheck;
	}

	public Boolean getBooSlectCheck() {
		return booSlectCheck;
	}
	public void setBooSlectCheck(Boolean booSlectCheck) {
		this.booSlectCheck = booSlectCheck;
	}

	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public BigDecimal getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	public BigDecimal getRoutingServciceId() {
		return routingServciceId;
	}
	public void setRoutingServciceId(BigDecimal routingServciceId) {
		this.routingServciceId = routingServciceId;
	}

	public String getRoutingServiceName() {
		return routingServiceName;
	}
	public void setRoutingServiceName(String routingServiceName) {
		this.routingServiceName = routingServiceName;
	}

	public BigDecimal getRoutingcountryId() {
		return routingcountryId;
	}
	public void setRoutingcountryId(BigDecimal routingcountryId) {
		this.routingcountryId = routingcountryId;
	}

	public String getRoutingcountryName() {
		return routingcountryName;
	}
	public void setRoutingcountryName(String routingcountryName) {
		this.routingcountryName = routingcountryName;
	}

	public BigDecimal getRoutingbankId() {
		return routingbankId;
	}
	public void setRoutingbankId(BigDecimal routingbankId) {
		this.routingbankId = routingbankId;
	}

	public String getRoutingbankName() {
		return routingbankName;
	}
	public void setRoutingbankName(String routingbankName) {
		this.routingbankName = routingbankName;
	}

	public BigDecimal getRoutingDeliveryId() {
		return routingDeliveryId;
	}
	public void setRoutingDeliveryId(BigDecimal routingDeliveryId) {
		this.routingDeliveryId = routingDeliveryId;
	}

	public String getRoutingDeliveryMode() {
		return routingDeliveryMode;
	}
	public void setRoutingDeliveryMode(String routingDeliveryMode) {
		this.routingDeliveryMode = routingDeliveryMode;
	}

	public BigDecimal getRoutingRemittanceId() {
		return routingRemittanceId;
	}
	public void setRoutingRemittanceId(BigDecimal routingRemittanceId) {
		this.routingRemittanceId = routingRemittanceId;
	}

	public String getRoutingRemittanceMode() {
		return routingRemittanceMode;
	}
	public void setRoutingRemittanceMode(String routingRemittanceMode) {
		this.routingRemittanceMode = routingRemittanceMode;
	}

	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}
	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}

	public String getRoutingcurrencyName() {
		return routingcurrencyName;
	}
	public void setRoutingcurrencyName(String routingcurrencyName) {
		this.routingcurrencyName = routingcurrencyName;
	}

	public String getRoutingbranchStatus() {
		return routingbranchStatus;
	}
	public void setRoutingbranchStatus(String routingbranchStatus) {
		this.routingbranchStatus = routingbranchStatus;
	}

}
