package com.amg.exchange.cashtransfer.bean;

import java.math.BigDecimal;

public class CashTallySubReportBean {

	private String denominations;
	private BigDecimal physicalNotes;
	private BigDecimal physicalAmount;
	private BigDecimal systemNotes;
	private BigDecimal systemAmount;
	private BigDecimal difference;
	private BigDecimal totalSystemAmount;
	private BigDecimal totalPhysicalAmount;
	private BigDecimal totaldifference;
	
	
	
	
	
	public BigDecimal getTotalSystemAmount() {
		return totalSystemAmount;
	}
	public void setTotalSystemAmount(BigDecimal totalSystemAmount) {
		this.totalSystemAmount = totalSystemAmount;
	}
	public BigDecimal getTotalPhysicalAmount() {
		return totalPhysicalAmount;
	}
	public void setTotalPhysicalAmount(BigDecimal totalPhysicalAmount) {
		this.totalPhysicalAmount = totalPhysicalAmount;
	}
	public BigDecimal getTotaldifference() {
		return totaldifference;
	}
	public void setTotaldifference(BigDecimal totaldifference) {
		this.totaldifference = totaldifference;
	}
	public String getDenominations() {
		return denominations;
	}
	public void setDenominations(String denominations) {
		this.denominations = denominations;
	}
	public BigDecimal getPhysicalNotes() {
		return physicalNotes;
	}
	public void setPhysicalNotes(BigDecimal physicalNotes) {
		this.physicalNotes = physicalNotes;
	}
	public BigDecimal getPhysicalAmount() {
		return physicalAmount;
	}
	public void setPhysicalAmount(BigDecimal physicalAmount) {
		this.physicalAmount = physicalAmount;
	}
	public BigDecimal getSystemNotes() {
		return systemNotes;
	}
	public void setSystemNotes(BigDecimal systemNotes) {
		this.systemNotes = systemNotes;
	}
	public BigDecimal getSystemAmount() {
		return systemAmount;
	}
	public void setSystemAmount(BigDecimal systemAmount) {
		this.systemAmount = systemAmount;
	}
	public BigDecimal getDifference() {
		return difference;
	}
	public void setDifference(BigDecimal difference) {
		this.difference = difference;
	}
	
	
	
	
}
