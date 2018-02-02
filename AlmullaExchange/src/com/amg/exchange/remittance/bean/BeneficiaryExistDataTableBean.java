package com.amg.exchange.remittance.bean;

import java.math.BigDecimal;

public class BeneficiaryExistDataTableBean {
	
	
	private String beneficiaryName;
	private String telephoneNumber;
	private BigDecimal beneficiaryAccountNumber;
	private String beneficiaryBank;
	private String beneficiaryBankBranch;
	private String beneficiaryCountry;
	

	

	

	public BeneficiaryExistDataTableBean() {
	}


	public BeneficiaryExistDataTableBean(String beneficiaryName,
			String telephoneNumber, BigDecimal beneficiaryAccountNumber,
			String beneficiaryBank, String beneficiaryBankBranch,
			String beneficiaryCountry) {
		this.beneficiaryName = beneficiaryName;
		this.telephoneNumber = telephoneNumber;
		this.beneficiaryAccountNumber = beneficiaryAccountNumber;
		this.beneficiaryBank = beneficiaryBank;
		this.beneficiaryBankBranch = beneficiaryBankBranch;
		this.beneficiaryCountry = beneficiaryCountry;
	}


	public String getBeneficiaryName() {
		return beneficiaryName;
	}


	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}


	public String getTelephoneNumber() {
		return telephoneNumber;
	}


	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}


	public BigDecimal getBeneficiaryAccountNumber() {
		return beneficiaryAccountNumber;
	}


	public void setBeneficiaryAccountNumber(BigDecimal beneficiaryAccountNumber) {
		this.beneficiaryAccountNumber = beneficiaryAccountNumber;
	}


	public String getBeneficiaryBank() {
		return beneficiaryBank;
	}


	public void setBeneficiaryBank(String beneficiaryBank) {
		this.beneficiaryBank = beneficiaryBank;
	}


	public String getBeneficiaryBankBranch() {
		return beneficiaryBankBranch;
	}


	public void setBeneficiaryBankBranch(String beneficiaryBankBranch) {
		this.beneficiaryBankBranch = beneficiaryBankBranch;
	}


	public String getBeneficiaryCountry() {
		return beneficiaryCountry;
	}


	public void setBeneficiaryCountry(String beneficiaryCountry) {
		this.beneficiaryCountry = beneficiaryCountry;
	}
	
	
	

}
