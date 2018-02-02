package com.amg.exchange.treasury.model;

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

import com.amg.exchange.common.model.CountryMaster;

@Entity
@Table(name = "EX_CURRENCY_OTHINFO")
public class CurrencyOtherInformation implements Serializable {
	private static final long serialVersionUID = 1L;

	private BigDecimal currencyOtherInfoId;
	private CurrencyMaster exCurrencyMaster;
	private CountryMaster fsCountryMaster;
	private BigDecimal fundMinRate;
	private BigDecimal fundMaxRate;
	private BigDecimal cashMinRate;
	private BigDecimal cashMaxRate;
	private String cbkPrintIndicator;
	private String onlineInd;
	private BigDecimal cbkShortIndicator;
	private String isActive;
	private String createdby;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	
	//NEWLY ADDED
	private BigDecimal averageRate;
	private BigDecimal highValue;
	private BigDecimal placeOrderLimit;
	
	
	@Id
	@GeneratedValue(generator = "ex_currency_othinfo_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ex_currency_othinfo_seq", sequenceName = "EX_CURRNECY_OTHINFO_SEQ", allocationSize = 1)
	@Column(name = "CURRENCY_OTHINFO_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getCurrencyOtherInfoId() {
		return currencyOtherInfoId;
	}
	public void setCurrencyOtherInfoId(BigDecimal currencyOtherInfoId) {
		this.currencyOtherInfoId = currencyOtherInfoId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CURRENCY_ID")
	public CurrencyMaster getExCurrencyMaster() {
		return exCurrencyMaster;
	}
	public void setExCurrencyMaster(CurrencyMaster exCurrencyMaster) {
		this.exCurrencyMaster = exCurrencyMaster;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "APPLICATION_COUNTRY_ID")
	public CountryMaster getFsCountryMaster() {
		return fsCountryMaster;
	}
	public void setFsCountryMaster(CountryMaster fsCountryMaster) {
		this.fsCountryMaster = fsCountryMaster;
	}

	@Column(name = "FUND_MIN_RATE")
	public BigDecimal getFundMinRate() {
		return fundMinRate;
	}
	public void setFundMinRate(BigDecimal fundMinRate) {
		this.fundMinRate = fundMinRate;
	}

	@Column(name = "FUND_MAX_RATE")
	public BigDecimal getFundMaxRate() {
		return fundMaxRate;
	}
	public void setFundMaxRate(BigDecimal fundMaxRate) {
		this.fundMaxRate = fundMaxRate;
	}

	@Column(name = "CASH_MIN_RATE")
	public BigDecimal getCashMinRate() {
		return cashMinRate;
	}
	public void setCashMinRate(BigDecimal cashMinRate) {
		this.cashMinRate = cashMinRate;
	}

	@Column(name = "CASH_MAX_RATE")
	public BigDecimal getCashMaxRate() {
		return cashMaxRate;
	}
	public void setCashMaxRate(BigDecimal cashMaxRate) {
		this.cashMaxRate = cashMaxRate;
	}

	@Column(name = "CBK_PRINT_IND")
	public String getCbkPrintIndicator() {
		return cbkPrintIndicator;
	}
	public void setCbkPrintIndicator(String cbkPrintIndicator) {
		this.cbkPrintIndicator = cbkPrintIndicator;
	}

	@Column(name = "ONLINE_IND")
	public String getOnlineInd() {
		return onlineInd;
	}
	public void setOnlineInd(String onlineInd) {
		this.onlineInd = onlineInd;
	}

	@Column(name = "CBK_SORT_IND")
	public BigDecimal getCbkShortIndicator() {
		return cbkShortIndicator;
	}
	public void setCbkShortIndicator(BigDecimal cbkShortIndicator) {
		this.cbkShortIndicator = cbkShortIndicator;
	}

	@Column(name = "IS_ACTIVE")
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Column(name = "CREATED_BY")
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	@Column(name = "CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "MODIFIED_BY")
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "MODIFIED_DATE")
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	@Column(name = "AVERAGE_RATE")
	public BigDecimal getAverageRate() {
		return averageRate;
	}
	public void setAverageRate(BigDecimal averageRate) {
		this.averageRate = averageRate;
	}
	
	@Column(name = "HIGH_VALUE")
	public BigDecimal getHighValue() {
		return highValue;
	}
	public void setHighValue(BigDecimal highValue) {
		this.highValue = highValue;
	}
	
	@Column(name = "PLACE_ORDER_LIMIT")
	public BigDecimal getPlaceOrderLimit() {
		return placeOrderLimit;
	}
	public void setPlaceOrderLimit(BigDecimal placeOrderLimit) {
		this.placeOrderLimit = placeOrderLimit;
	}

	
}
