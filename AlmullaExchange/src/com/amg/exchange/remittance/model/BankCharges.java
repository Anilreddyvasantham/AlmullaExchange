package com.amg.exchange.remittance.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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

import com.amg.exchange.common.model.BizComponentData;

/*******************************************************************************************************************

File		: BankCharges.java

Project	: AlmullaExchange

Package	: com.amg.exchange.remittance.model

Created	:	
				Date	: 5-JAN-2015 
				By		: Nazish Ehsan Hashmi
				Revision:

 Last Change:
				Date	: 5-JAN-2015  
				By		: Nazish Ehsan Hashmi
				Revision:

Description: TODO 

********************************************************************************************************************/
@Entity
@Table(name="EX_BANK_CHARGES")
public class BankCharges implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal bankChargeId;
	private BankServiceRule bankServiceRuleId;
	private String chargeType;
	private BigDecimal fromAmount;
	private BigDecimal toAmount;
	private BigDecimal chargeAmount;
	private String isActive;
	private Date createdDate;
	private String createdBy;
	private Date modifiedDate;
	private String modifiedBy;
	private BizComponentData chargeFor;
	private BigDecimal currencyCode;
	private BigDecimal costCurrencyCode;
	private BigDecimal costAmount;

	private Date approvedDate;
	private String approvedBy;
	private String remarks;
	
public BankCharges() {
		
	}
	
	

	public BankCharges(BigDecimal bankChargeId, BankServiceRule bankServiceRuleId,
		String chargeType, BigDecimal fromAmount, BigDecimal toAmount,
		BigDecimal chargeAmount, String isActive, Date createdDate,
		String createdBy, Date modifiedDate, String modifiedBy,
		BizComponentData chargeFor, BigDecimal currencyCode,
		BigDecimal costCurrencyCode, BigDecimal costAmount,Date approvedDate,String approvedBy,String remarks) {
	super();
	this.bankChargeId = bankChargeId;
	this.bankServiceRuleId = bankServiceRuleId;
	this.chargeType = chargeType;
	this.fromAmount = fromAmount;
	this.toAmount = toAmount;
	this.chargeAmount = chargeAmount;
	this.isActive = isActive;
	this.createdDate = createdDate;
	this.createdBy = createdBy;
	this.modifiedDate = modifiedDate;
	this.modifiedBy = modifiedBy;
	this.chargeFor = chargeFor;
	this.currencyCode = currencyCode;
	this.costCurrencyCode = costCurrencyCode;
	this.costAmount = costAmount;
	this.approvedBy=approvedBy;
	this.approvedDate=approvedDate;
	this.remarks=remarks;
}



	@Id
	@GeneratedValue(generator="ex_bank_charges_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_bank_charges_seq",sequenceName="EX_BANK_CHARGES_SEQ",allocationSize=1)
	@Column(name="BANK_SERVICE_CHARGES_ID", unique=true, nullable=false, precision=22, scale=0)
	public BigDecimal getBankChargeId() {
		return bankChargeId;
	}

	public void setBankChargeId(BigDecimal bankChargeId) {
		this.bankChargeId = bankChargeId;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="BANK_SERVICE_RULE_ID")
	public BankServiceRule getBankServiceRuleId() {
		return bankServiceRuleId;
	}

	public void setBankServiceRuleId(BankServiceRule bankServiceRuleId) {
		this.bankServiceRuleId = bankServiceRuleId;
	}

	@Column(name="CHARGES_TYPE")
	public String getChargeType() {
		return chargeType;
	}

	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	@Column(name="FROM_AMOUNT")
	public BigDecimal getFromAmount() {
		return fromAmount;
	}

	public void setFromAmount(BigDecimal fromAmount) {
		this.fromAmount = fromAmount;
	}
	
	@Column(name="TO_AMOUNT")
	public BigDecimal getToAmount() {
		return toAmount;
	}

	public void setToAmount(BigDecimal toAmount) {
		this.toAmount = toAmount;
	}

	@Column(name="CHARGE_AMOUNT")
	public BigDecimal getChargeAmount() {
		return chargeAmount;
	}

	public void setChargeAmount(BigDecimal chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	@Column(name="ISACTIVE")
	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Column(name="CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name="CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="MODIFIED_DATE")
	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name="MODIFIED_BY")
	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CHARGES_FOR")
	public BizComponentData getChargeFor() {
		return chargeFor;
	}

	public void setChargeFor(BizComponentData chargeFor) {
		this.chargeFor = chargeFor;
	}


	@Column(name="CURRENCY_ID")
	public BigDecimal getCurrencyCode() {
		return currencyCode;
	}



	public void setCurrencyCode(BigDecimal currencyCode) {
		this.currencyCode = currencyCode;
	}


	@Column(name="COST_CURRENCY_ID")
	public BigDecimal getCostCurrencyCode() {
		return costCurrencyCode;
	}



	public void setCostCurrencyCode(BigDecimal costCurrencyCode) {
		this.costCurrencyCode = costCurrencyCode;
	}


	@Column(name="COST_AMOUNT")
	public BigDecimal getCostAmount() {
		return costAmount;
	}


	
	public void setCostAmount(BigDecimal costAmount) {
		this.costAmount = costAmount;
	}
	
	
	@Column(name="APPROVED_DATE")
	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
	@Column(name="APPROVED_BY")
	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	@Column(name="REMARKS")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
	

}
