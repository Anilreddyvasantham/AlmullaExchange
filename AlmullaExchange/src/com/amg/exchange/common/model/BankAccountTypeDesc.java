package com.amg.exchange.common.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="EX_BANK_ACCOUNT_TYPE_DESC")
public class BankAccountTypeDesc {
	
	private BigDecimal bankAccountTypeDescId;
	private BankAccountType bankAccountTypeId;
	private LanguageType languageId;
	private String bankAccountTypeDesc;
	
	
	
	public BankAccountTypeDesc() {
	}
	
	public BankAccountTypeDesc(BigDecimal bankAccountTypeDescId,
			BankAccountType bankAccountTypeId, LanguageType languageId,
			String bankAccountTypeDesc) {
		this.bankAccountTypeDescId = bankAccountTypeDescId;
		this.bankAccountTypeId = bankAccountTypeId;
		this.languageId = languageId;
		this.bankAccountTypeDesc = bankAccountTypeDesc;
	}
	
	@Id
	@GeneratedValue(generator="ex_bank_account_type_desc_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_bank_account_type_desc_seq",sequenceName="EX_BANK_ACCOUNT_TYPE_DESC_SEQ",allocationSize=1)
	@Column(name ="BANK_ACCOUNT_TYPE_DESC_ID" , unique=true, nullable=false, precision=22, scale=0)
	public BigDecimal getBankAccountTypeDescId() {
		return bankAccountTypeDescId;
	}
	
	public void setBankAccountTypeDescId(BigDecimal bankAccountTypeDescId) {
		this.bankAccountTypeDescId = bankAccountTypeDescId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_ACCOUNT_TYPE_ID")
	public BankAccountType getBankAccountTypeId() {
		return bankAccountTypeId;
	}
	public void setBankAccountTypeId(BankAccountType bankAccountTypeId) {
		this.bankAccountTypeId = bankAccountTypeId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LANGUAGE_ID")
	public LanguageType getLanguageId() {
		return languageId;
	}
	public void setLanguageId(LanguageType languageId) {
		this.languageId = languageId;
	}
	/*@Column(name="BANK_ACCOUNT_TYPE_DESC")
	public String getBankAccountTypeDesc() {
		return bankAccountTypeDesc;
	}
	public void setBankAccountTypeDesc(String bankAccountTypeDesc) {
		bankAccountTypeDesc = bankAccountTypeDesc;
	}*/
	@Column(name="BANK_ACCOUNT_TYPE_DESC")
	public String getBankAccountTypeDesc() {
		return bankAccountTypeDesc;
	}

	public void setBankAccountTypeDesc(String bankAccountTypeDesc) {
		this.bankAccountTypeDesc = bankAccountTypeDesc;
	}
	
	
	
	
	
	

}
