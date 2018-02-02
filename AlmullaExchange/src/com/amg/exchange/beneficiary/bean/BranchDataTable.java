package com.amg.exchange.beneficiary.bean;

import java.math.BigDecimal;

public class BranchDataTable  implements java.io.Serializable
{ 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String branchIFSC;
	String stateName;
	String districtName;
	String cityName;
	String bankFullName;
	BigDecimal stateId;
	BigDecimal distictId;
	BigDecimal cityId;
	String swiftCode;
	private BigDecimal bankBranchId;
	private BigDecimal bankBranchCode;
	

	public BigDecimal getBankBranchId() {
		return bankBranchId;
	}
	public void setBankBranchId(BigDecimal bankBranchId) {
		this.bankBranchId = bankBranchId;
	}

	public String getBranchIFSC() {
		return branchIFSC;
	}
	public void setBranchIFSC(String branchIFSC) {
		this.branchIFSC = branchIFSC;
	}

	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getBankFullName() {
		return bankFullName;
	}
	public void setBankFullName(String bankFullName) {
		this.bankFullName = bankFullName;
	}
	
	public BigDecimal getStateId() {
		return stateId;
	}
	public void setStateId(BigDecimal stateId) {
		this.stateId = stateId;
	}

	public BigDecimal getDistictId() {
		return distictId;
	}
	public void setDistictId(BigDecimal distictId) {
		this.distictId = distictId;
	}

	public BigDecimal getCityId() {
		return cityId;
	}
	public void setCityId(BigDecimal cityId) {
		this.cityId = cityId;
	}
	
	public String getSwiftCode() {
		return swiftCode;
	}
	public void setSwiftCode(String swiftCode) {
		this.swiftCode = swiftCode;
	}

	public BigDecimal getBankBranchCode() {
		return bankBranchCode;
	}
	public void setBankBranchCode(BigDecimal bankBranchCode) {
		this.bankBranchCode = bankBranchCode;
	}
	
	@Override
	public String toString() {
		return "BranchDataTable [branchIFSC=" + branchIFSC + ", stateName=" + stateName + ", districtName=" + districtName + ", cityName=" + cityName + ", bankFullName=" + bankFullName + ", stateId=" + stateId + ", distictId=" + distictId + ", cityId=" + cityId + ", bankBranchId=" + bankBranchId
				+ ", bankBranchCode=" + bankBranchCode + "]";
	}
	
	
}