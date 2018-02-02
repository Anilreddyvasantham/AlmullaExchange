package com.amg.exchange.cashtransfer.bean;

import java.math.BigDecimal;

import com.amg.exchange.foreigncurrency.model.CurrencyWiseDenomination;
import com.amg.exchange.treasury.model.CurrencyMaster;

public class StockCurrency {
	
	private BigDecimal stockId;
	private BigDecimal denominationId;
	private BigDecimal currencyId;
	
	
	public BigDecimal getStockId() {
		return stockId;
	}
	public void setStockId(BigDecimal stockId) {
		this.stockId = stockId;
	}
	public BigDecimal getDenominationId() {
		return denominationId;
	}
	public void setDenominationId(BigDecimal denominationId) {
		this.denominationId = denominationId;
	}
	public BigDecimal getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}
	

	

	
}
