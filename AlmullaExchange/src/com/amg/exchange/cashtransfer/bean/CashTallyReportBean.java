package com.amg.exchange.cashtransfer.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class CashTallyReportBean {

	private BigDecimal lineNo;
	private String currencyName;
	private String branchName;
	private String cashierName;
	private BigDecimal physicalCash;
	private BigDecimal systemCash;
	private BigDecimal differenceCash;
	private String denominations;
	private String logoPath;
	private String subReport;
	
	private List<CashTallySubReportBean> cashTallyReportList=new ArrayList<CashTallySubReportBean>();
	
	private List<String> linoList;
	
	
	
	
	public List<String> getLinoList() {
		return linoList;
	}
	public void setLinoList(List<String> linoList) {
		this.linoList = linoList;
	}
	public String getLogoPath() {
		return logoPath;
	}
	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}
	public String getSubReport() {
		return subReport;
	}
	public void setSubReport(String subReport) {
		this.subReport = subReport;
	}
	public List<CashTallySubReportBean> getCashTallyReportList() {
		return cashTallyReportList;
	}
	public void setCashTallyReportList(
			List<CashTallySubReportBean> cashTallyReportList) {
		this.cashTallyReportList = cashTallyReportList;
	}
	
	public String getDenominations() {
		return denominations;
	}
	public void setDenominations(String denominations) {
		this.denominations = denominations;
	}

	public BigDecimal getLineNo() {
		return lineNo;
	}
	public void setLineNo(BigDecimal lineNo) {
		this.lineNo = lineNo;
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
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
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

	
	
	
}
