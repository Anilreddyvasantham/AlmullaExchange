package com.amg.exchange.remittance.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.sun.org.apache.xpath.internal.operations.Bool;

public class StandingInstructionList implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal standingInstnID;
	private BigDecimal beneficiaryMasterId;
	private BigDecimal beneficiaryAccountDtId;
	private BigDecimal beneficiaryBankId;
	private String beneficiaryBankName;
	private BigDecimal beneficiaryBankBranchId;
	private String beneficiaryBankBranchName;
	private String beneficiaryUserName;
	private BigDecimal beneficiaryCustomerNo;
	private String beneficiaryCityName;
	private BigDecimal beneficiaryAccount;
	private BigDecimal beneficiaryCurrencyId;
	private String beneficiaryCurrenyShort;
	private String beneficiaryCurrencyName;
	private String beneficiaryDueDate;
	private String beneficiaryStatus;
	private BigDecimal standingAmount;
	private Boolean selectedRecord;
	
	public BigDecimal getStandingInstnID() {
		return standingInstnID;
	}
	public void setStandingInstnID(BigDecimal standingInstnID) {
		this.standingInstnID = standingInstnID;
	}
	public BigDecimal getBeneficiaryMasterId() {
		return beneficiaryMasterId;
	}
	public void setBeneficiaryMasterId(BigDecimal beneficiaryMasterId) {
		this.beneficiaryMasterId = beneficiaryMasterId;
	}
	public BigDecimal getBeneficiaryAccountDtId() {
		return beneficiaryAccountDtId;
	}
	public void setBeneficiaryAccountDtId(BigDecimal beneficiaryAccountDtId) {
		this.beneficiaryAccountDtId = beneficiaryAccountDtId;
	}
	public BigDecimal getBeneficiaryBankId() {
		return beneficiaryBankId;
	}
	public void setBeneficiaryBankId(BigDecimal beneficiaryBankId) {
		this.beneficiaryBankId = beneficiaryBankId;
	}
	public String getBeneficiaryBankName() {
		return beneficiaryBankName;
	}
	public void setBeneficiaryBankName(String beneficiaryBankName) {
		this.beneficiaryBankName = beneficiaryBankName;
	}
	public BigDecimal getBeneficiaryBankBranchId() {
		return beneficiaryBankBranchId;
	}
	public void setBeneficiaryBankBranchId(BigDecimal beneficiaryBankBranchId) {
		this.beneficiaryBankBranchId = beneficiaryBankBranchId;
	}
	public String getBeneficiaryBankBranchName() {
		return beneficiaryBankBranchName;
	}
	public void setBeneficiaryBankBranchName(String beneficiaryBankBranchName) {
		this.beneficiaryBankBranchName = beneficiaryBankBranchName;
	}
	public String getBeneficiaryUserName() {
		return beneficiaryUserName;
	}
	public void setBeneficiaryUserName(String beneficiaryUserName) {
		this.beneficiaryUserName = beneficiaryUserName;
	}
	public String getBeneficiaryCityName() {
		return beneficiaryCityName;
	}
	public void setBeneficiaryCityName(String beneficiaryCityName) {
		this.beneficiaryCityName = beneficiaryCityName;
	}
	public BigDecimal getBeneficiaryAccount() {
		return beneficiaryAccount;
	}
	public void setBeneficiaryAccount(BigDecimal beneficiaryAccount) {
		this.beneficiaryAccount = beneficiaryAccount;
	}
	public BigDecimal getBeneficiaryCurrencyId() {
		return beneficiaryCurrencyId;
	}
	public void setBeneficiaryCurrencyId(BigDecimal beneficiaryCurrencyId) {
		this.beneficiaryCurrencyId = beneficiaryCurrencyId;
	}
	public String getBeneficiaryCurrenyShort() {
		return beneficiaryCurrenyShort;
	}
	public void setBeneficiaryCurrenyShort(String beneficiaryCurrenyShort) {
		this.beneficiaryCurrenyShort = beneficiaryCurrenyShort;
	}
	public String getBeneficiaryCurrencyName() {
		return beneficiaryCurrencyName;
	}
	public void setBeneficiaryCurrencyName(String beneficiaryCurrencyName) {
		this.beneficiaryCurrencyName = beneficiaryCurrencyName;
	}
	public String getBeneficiaryDueDate() {
		return beneficiaryDueDate;
	}
	public void setBeneficiaryDueDate(String beneficiaryDueDate) {
		this.beneficiaryDueDate = beneficiaryDueDate;
	}
	public String getBeneficiaryStatus() {
		return beneficiaryStatus;
	}
	public void setBeneficiaryStatus(String beneficiaryStatus) {
		this.beneficiaryStatus = beneficiaryStatus;
	}
	public BigDecimal getBeneficiaryCustomerNo() {
		return beneficiaryCustomerNo;
	}
	public void setBeneficiaryCustomerNo(BigDecimal beneficiaryCustomerNo) {
		this.beneficiaryCustomerNo = beneficiaryCustomerNo;
	}
	public BigDecimal getStandingAmount() {
		return standingAmount;
	}
	public void setStandingAmount(BigDecimal standingAmount) {
		this.standingAmount = standingAmount;
	}
	public Boolean getSelectedRecord() {
		return selectedRecord;
	}
	public void setSelectedRecord(Boolean selectedRecord) {
		this.selectedRecord = selectedRecord;
	}
	
}
