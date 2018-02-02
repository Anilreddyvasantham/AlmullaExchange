package com.amg.exchange.remittance.bean;

import java.math.BigDecimal;

public class PersonalRemittanceTeleExistDTBean {

	private String bankAccountNumber;
	private String bankFullName;
	private String branchFullName;
	private BigDecimal bankBranchCode;
	private BigDecimal beneficaryAccountSeqId;
	private boolean selectRecord;
	private String beneficaryName;
	
	public PersonalRemittanceTeleExistDTBean()
	{
		
	}
	public PersonalRemittanceTeleExistDTBean(String bankAccountNumber,
			String bankFullName, String branchFullName,
			BigDecimal bankBranchCode, BigDecimal beneficaryAccountSeqId,
			boolean selectRecord,String beneficaryName) {
		super();
		this.bankAccountNumber = bankAccountNumber;
		this.bankFullName = bankFullName;
		this.branchFullName = branchFullName;
		this.bankBranchCode = bankBranchCode;
		this.beneficaryAccountSeqId = beneficaryAccountSeqId;
		this.selectRecord=selectRecord;
		this.beneficaryName=beneficaryName;
	}


	public String getBankAccountNumber() {
		return bankAccountNumber;
	}


	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}


	public String getBankFullName() {
		return bankFullName;
	}


	public void setBankFullName(String bankFullName) {
		this.bankFullName = bankFullName;
	}


	public String getBranchFullName() {
		return branchFullName;
	}


	public void setBranchFullName(String branchFullName) {
		this.branchFullName = branchFullName;
	}


	public BigDecimal getBankBranchCode() {
		return bankBranchCode;
	}


	public void setBankBranchCode(BigDecimal bankBranchCode) {
		this.bankBranchCode = bankBranchCode;
	}


	public BigDecimal getBeneficaryAccountSeqId() {
		return beneficaryAccountSeqId;
	}


	public void setBeneficaryAccountSeqId(BigDecimal beneficaryAccountSeqId) {
		this.beneficaryAccountSeqId = beneficaryAccountSeqId;
	}
	public boolean isSelectRecord() {
		return selectRecord;
	}
	public void setSelectRecord(boolean selectRecord) {
		this.selectRecord = selectRecord;
	}
	public String getBeneficaryName() {
		return beneficaryName;
	}
	public void setBeneficaryName(String beneficaryName) {
		this.beneficaryName = beneficaryName;
	}
	
	
	
	
}
