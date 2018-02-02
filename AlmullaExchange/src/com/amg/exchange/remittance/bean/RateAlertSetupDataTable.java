package com.amg.exchange.remittance.bean;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Mohan
 * 
 */
public class RateAlertSetupDataTable {
	private BigDecimal currency;
	private String howOften;
	private BigDecimal howOftenId;
	private BigDecimal exchnageCurrentRate;
	private BigDecimal exchnageCurrentRateId;
	private BigDecimal triggerRate;
	private Boolean emailCheck;
	private Boolean mobileCheck;
	private String currencyName;
	private Date effectoveFrom = null;
	private Date effectoveTo = null;
	private String frequency;

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public BigDecimal getExchnageCurrentRateId() {
		return exchnageCurrentRateId;
	}

	public void setExchnageCurrentRateId(BigDecimal exchnageCurrentRateId) {
		this.exchnageCurrentRateId = exchnageCurrentRateId;
	}

	public BigDecimal getHowOftenId() {
		return howOftenId;
	}

	public void setHowOftenId(BigDecimal howOftenId) {
		this.howOftenId = howOftenId;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public Date getEffectoveFrom() {
		return effectoveFrom;
	}

	public void setEffectoveFrom(Date effectoveFrom) {
		this.effectoveFrom = effectoveFrom;
	}

	public Date getEffectoveTo() {
		return effectoveTo;
	}

	public void setEffectoveTo(Date effectoveTo) {
		this.effectoveTo = effectoveTo;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public BigDecimal getCurrency() {
		return currency;
	}

	public void setCurrency(BigDecimal currency) {
		this.currency = currency;
	}

	public String getHowOften() {
		return howOften;
	}

	public void setHowOften(String howOften) {
		this.howOften = howOften;
	}

	public BigDecimal getExchnageCurrentRate() {
		return exchnageCurrentRate;
	}

	public void setExchnageCurrentRate(BigDecimal exchnageCurrentRate) {
		this.exchnageCurrentRate = exchnageCurrentRate;
	}

	public BigDecimal getTriggerRate() {
		return triggerRate;
	}

	public void setTriggerRate(BigDecimal triggerRate) {
		this.triggerRate = triggerRate;
	}

	public Boolean getEmailCheck() {
		return emailCheck;
	}

	public void setEmailCheck(Boolean emailCheck) {
		this.emailCheck = emailCheck;
	}

	public Boolean getMobileCheck() {
		return mobileCheck;
	}

	public void setMobileCheck(Boolean mobileCheck) {
		this.mobileCheck = mobileCheck;
	}

	@Override
	public String toString() {
		return "RateAlertSetupDataTable [currency=" + currency + ", howOften=" + howOften + ", howOftenId=" + howOftenId + ", exchnageCurrentRate=" + exchnageCurrentRate + ", exchnageCurrentRateId="
				+ exchnageCurrentRateId + ", triggerRate=" + triggerRate + ", emailCheck=" + emailCheck + ", mobileCheck=" + mobileCheck + ", currencyName=" + currencyName + ", effectoveFrom="
				+ effectoveFrom + ", effectoveTo=" + effectoveTo + ", frequncy=" + frequency + "]";
	}

}
