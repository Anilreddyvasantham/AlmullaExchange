package com.amg.exchange.remittance.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class KIOSKDenomination implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String currencyCode;
	
	private BigDecimal Denomination;
	
	private BigDecimal numberOfNotes;
	
	private BigDecimal totalNumberOfNotes;
	

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public BigDecimal getDenomination() {
		return Denomination;
	}

	public void setDenomination(BigDecimal denomination) {
		Denomination = denomination;
	}

	public BigDecimal getNumberOfNotes() {
		return numberOfNotes;
	}

	public void setNumberOfNotes(BigDecimal numberOfNotes) {
		this.numberOfNotes = numberOfNotes;
	}

	public BigDecimal getTotalNumberOfNotes() {
		return totalNumberOfNotes;
	}

	public void setTotalNumberOfNotes(BigDecimal totalNumberOfNotes) {
		this.totalNumberOfNotes = totalNumberOfNotes;
	}
	
	

}
