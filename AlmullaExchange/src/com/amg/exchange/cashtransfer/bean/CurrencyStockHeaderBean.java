package com.amg.exchange.cashtransfer.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CurrencyStockHeaderBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private BigDecimal currencyId;
	private BigDecimal physicalCash;
	private BigDecimal systemCash;
	private BigDecimal differenceCash;
	private String currencyName;
	private List<CurrencyStockHeaderBean> currencyStockHeaderList = new CopyOnWriteArrayList<CurrencyStockHeaderBean>();

	public CurrencyStockHeaderBean() {
		super();
	}

	public BigDecimal getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	public BigDecimal getPhysicalCash() {
		return physicalCash;
	}

	public void setPhysicalCash(BigDecimal physicalCash) {
		this.physicalCash = physicalCash;
	}

	public BigDecimal getSystemCash() {
		return systemCash;
	}

	public void setSystemCash(BigDecimal systemCash) {
		this.systemCash = systemCash;
	}

	public BigDecimal getDifferenceCash() {
		return differenceCash;
	}

	public void setDifferenceCash(BigDecimal differenceCash) {
		this.differenceCash = differenceCash;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public List<CurrencyStockHeaderBean> getCurrencyStockHeaderList() {
		return currencyStockHeaderList;
	}

	public void setCurrencyStockHeaderList(
			List<CurrencyStockHeaderBean> currencyStockHeaderList) {
		this.currencyStockHeaderList = currencyStockHeaderList;
	}

	
}
