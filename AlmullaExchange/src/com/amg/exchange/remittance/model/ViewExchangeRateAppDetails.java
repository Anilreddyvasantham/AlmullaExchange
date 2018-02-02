package com.amg.exchange.remittance.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "V_EX_RATE_MASTER_APRDET")
public class ViewExchangeRateAppDetails {
private static final long serialVersionUID = 1L;
	
	
	private BigDecimal bankId;
	private BigDecimal buyRateMax;
	private BigDecimal buyRateMin;
	private BigDecimal corporateRate;
	private BigDecimal countryBranchId;
	private BigDecimal countryId;
	private String countryName;
	private BigDecimal currencyId;
	private BigDecimal sellRateMin;
	private BigDecimal sellRateMax;
	private BigDecimal serviceId;
	private BigDecimal amount;
	private BigDecimal srNo;
	

	@Column(name = "BANK_ID")
	public BigDecimal getBankId() {
		return bankId;
	}
	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}

	@Column(name = "BUY_RATE_MAX")
	public BigDecimal getBuyRateMax() {
		return buyRateMax;
	}
	public void setBuyRateMax(BigDecimal buyRateMax) {
		this.buyRateMax = buyRateMax;
	}

	@Column(name = "BUY_RATE_MIN")
	public BigDecimal getBuyRateMin() {
		return buyRateMin;
	}
	public void setBuyRateMin(BigDecimal buyRateMin) {
		this.buyRateMin = buyRateMin;
	}

	@Column(name = "CORPORATE_RATE")
	public BigDecimal getCorporateRate() {
		return corporateRate;
	}
	public void setCorporateRate(BigDecimal corporateRate) {
		this.corporateRate = corporateRate;
	}

	@Column(name = "COUNTRY_BRANCH_ID")
	public BigDecimal getCountryBranchId() {
		return countryBranchId;
	}
	public void setCountryBranchId(BigDecimal countryBranchId) {
		this.countryBranchId = countryBranchId;
	}

	@Column(name = "COUNTRY_ID")
	public BigDecimal getCountryId() {
		return countryId;
	}
	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}

	
	

	@Column(name = "CURRENCY_ID")
	public BigDecimal getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	
	@Column(name = "SELL_RATE_MIN")
	public BigDecimal getSellRateMin() {
		return sellRateMin;
	}
	public void setSellRateMin(BigDecimal sellRateMin) {
		this.sellRateMin = sellRateMin;
	}

	@Column(name = "SELL_RATE_MAX")
	public BigDecimal getSellRateMax() {
		return sellRateMax;
	}
	public void setSellRateMax(BigDecimal sellRateMax) {
		this.sellRateMax = sellRateMax;
	}

	@Column(name = "SERVICE_INDICATOR_ID")
	public BigDecimal getServiceId() {
		return serviceId;
	}
	public void setServiceId(BigDecimal serviceId) {
		this.serviceId = serviceId;
	}
	@Column(name="KD_FC_RATE")
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	@Id
	@Column(name ="RATE_ROW" , unique=true, nullable=false, precision=22, scale=0)
	public BigDecimal getSrNo() {
		return srNo;
	}
	public void setSrNo(BigDecimal srNo) {
		this.srNo = srNo;
	}
	@Column(name="COUNTRY_NAME")
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	
}
