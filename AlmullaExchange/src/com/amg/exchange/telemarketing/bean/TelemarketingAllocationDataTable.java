package com.amg.exchange.telemarketing.bean;

import java.math.BigDecimal;

public class TelemarketingAllocationDataTable {
	
	private String displayEmployeeName;
	private BigDecimal cusCount;
	
	
	
	//Getters and setters.
	
	public String getDisplayEmployeeName() {
		return displayEmployeeName;
	}
	public void setDisplayEmployeeName(String displayEmployeeName) {
		this.displayEmployeeName = displayEmployeeName;
	}
	public BigDecimal getCusCount() {
		return cusCount;
	}
	public void setCusCount(BigDecimal cusCount) {
		this.cusCount = cusCount;
	}
	

}
