package com.amg.exchange.bankBranchUpload.bean;

import java.math.BigDecimal;

public class BankBranchUploadExcelList { 
	
	private BigDecimal bankBranchId;
	private String bank;
	private String ifsc;
	private String micr;
	private String branchName;
	private String branchShortName;
	private String address;
	private String addr1;
	private String addr2;
	private String contact;
	private String city;
	private String district;
	private String state;
	private String remarks;
	private String columnInvalid;
	
	//Getters and Setters.
	
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getIfsc() {
		return ifsc;
	}
	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
	}
	public String getMicr() {
		return micr;
	}
	public void setMicr(String micr) {
		this.micr = micr;
	}	
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getAddr1() {
		return addr1;
	}
	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}
	public String getAddr2() {
		return addr2;
	}
	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public BigDecimal getBankBranchId() {
		return bankBranchId;
	}
	public void setBankBranchId(BigDecimal bankBranchId) {
		this.bankBranchId = bankBranchId;
	}
	public String getColumnInvalid() {
		return columnInvalid;
	}
	public void setColumnInvalid(String columnInvalid) {
		this.columnInvalid = columnInvalid;
	}
	public String getBranchShortName() {
		return branchShortName;
	}
	public void setBranchShortName(String branchShortName) {
		this.branchShortName = branchShortName;
	}
	
}
