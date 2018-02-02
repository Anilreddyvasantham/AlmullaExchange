package com.amg.exchange.cashtransfer.bean;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CurrencyStockReportBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String logoPath;
	private String branchName;
	private String cashierName;
	private List<CurrencyStockDetailBean> currencyStockDetailBeanList = new CopyOnWriteArrayList<CurrencyStockDetailBean>();
	private List<CurrencyStockHeaderBean> currencyStockHeaderBeanList = new CopyOnWriteArrayList<CurrencyStockHeaderBean>();
	private List<CurrencyStockReportBean> currencyStockReportBeanList = new CopyOnWriteArrayList<CurrencyStockReportBean>();

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getCashierName() {
		return cashierName;
	}

	public void setCashierName(String cashierName) {
		this.cashierName = cashierName;
	}

	public List<CurrencyStockReportBean> getCurrencyStockReportBeanList() {
		return currencyStockReportBeanList;
	}

	public void setCurrencyStockReportBeanList(
			List<CurrencyStockReportBean> currencyStockReportBeanList) {
		this.currencyStockReportBeanList = currencyStockReportBeanList;
	}

	public List<CurrencyStockDetailBean> getCurrencyStockDetailBeanList() {
		return currencyStockDetailBeanList;
	}

	public void setCurrencyStockDetailBeanList(
			List<CurrencyStockDetailBean> currencyStockDetailBeanList) {
		this.currencyStockDetailBeanList = currencyStockDetailBeanList;
	}

	public List<CurrencyStockHeaderBean> getCurrencyStockHeaderBeanList() {
		return currencyStockHeaderBeanList;
	}

	public void setCurrencyStockHeaderBeanList(
			List<CurrencyStockHeaderBean> currencyStockHeaderBeanList) {
		this.currencyStockHeaderBeanList = currencyStockHeaderBeanList;
	}

}
