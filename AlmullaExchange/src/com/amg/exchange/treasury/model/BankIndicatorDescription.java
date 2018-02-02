package com.amg.exchange.treasury.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.amg.exchange.common.model.LanguageType;
@Entity
@Table(name="EX_BANK_INDICATOR_DESC")
public class BankIndicatorDescription implements Serializable {
 	private static final long serialVersionUID = 1L;
 	
 	private BigDecimal bankIndicatorDescId;
 	private LanguageType languageType;
 	private String bankIndicatorDescription;
 	private BankIndicator bankIndicator;
 	
 	 private Set<BankApplicability> exBankApplicability = new HashSet<BankApplicability>(0);
 	
 	public BankIndicatorDescription(){
 		
 	}
 	public BankIndicatorDescription(BigDecimal bankIndicatorDescId){
 		
 	}
 	
 	public BankIndicatorDescription(BigDecimal bankIndicatorDescId,
			LanguageType languageType, String bankIndicatorDescription,
			BankIndicator bankIndicator,Set<BankApplicability> exBankApplicability) {
		super();
		this.bankIndicatorDescId = bankIndicatorDescId;
		this.languageType = languageType;
		this.bankIndicatorDescription = bankIndicatorDescription;
		this.bankIndicator = bankIndicator;
		this.setExBankApplicability(exBankApplicability);
	}
 	@Id
 	@GeneratedValue(generator="ex_bank_indicator_desc",strategy=GenerationType.SEQUENCE)
 	@SequenceGenerator(name = "ex_bank_indicator_desc",sequenceName="EX_BANK_INDICATOR_DESC_SEQ",allocationSize=1)
 	@Column(name="BANK_INDICATOR_DESC_ID",unique=true,nullable=false,precision=22,scale=0)
	public BigDecimal getBankIndicatorDescId() {
		return bankIndicatorDescId;
	}
	public void setBankIndicatorDescId(BigDecimal bankIndicatorDescId) {
		this.bankIndicatorDescId = bankIndicatorDescId;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LANGUAGE_ID")
	public LanguageType getLanguageType() {
		return languageType;
	}
	public void setLanguageType(LanguageType languageType) {
		this.languageType = languageType;
	}
	@Column(name ="BANK_INDICATOR_DESC" )
	public String getBankIndicatorDescription() {
		return bankIndicatorDescription;
	}
	public void setBankIndicatorDescription(String bankIndicatorDescription) {
		this.bankIndicatorDescription = bankIndicatorDescription;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_INDICATOR_ID")
	public BankIndicator getBankIndicator() {
		return bankIndicator;
	}
	public void setBankIndicator(BankIndicator bankIndicator) {
		this.bankIndicator = bankIndicator;
	}
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bankInd")
	public Set<BankApplicability> getExBankApplicability() {
		return exBankApplicability;
	}
	public void setExBankApplicability(Set<BankApplicability> exBankApplicability) {
		this.exBankApplicability = exBankApplicability;
	}
	
 	
	
	
	

}
