package com.amg.exchange.treasury.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class FundEstimationDaysDtable implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	//private BigDecimal fundEstimtaionDaysId;

	private String previousMonthDate1;
	private String previousMonthDate2;
	private String previousMonthDate3;
	private String previousMonthValue1;
	private String previousMonthValue2;
	private String previousMonthValue3;
	private String previousMonthWeekDate1;
	private String previousMonthWeekDate2;
	private String previousMonthWeekDate3;
	private String previousMonthWeekValue1;
	private String previousMonthWeekValue2;
	private String previousMonthWeekValue3;
	private String projectiondate;
	private boolean alreadyInsert;
	
	public FundEstimationDaysDtable(){
		super();
	}
	

	public FundEstimationDaysDtable(String previousMonthDate1, String previousMonthDate2,
			String previousMonthDate3, String previousMonthValue1,
			String previousMonthValue2, String previousMonthValue3,
			String previousMonthWeekDate1, String previousMonthWeekDate2,
			String previousMonthWeekDate3, String previousMonthWeekValue1,
			String previousMonthWeekValue2, String previousMonthWeekValue3,
			String projectiondate,boolean alreadyInsert) {
		super();
		//this.fundEstimtaionDaysId = fundEstimtaionDaysId;
		this.previousMonthDate1 = previousMonthDate1;
		this.previousMonthDate2 = previousMonthDate2;
		this.previousMonthDate3 = previousMonthDate3;
		this.previousMonthValue1 = previousMonthValue1;
		this.previousMonthValue2 = previousMonthValue2;
		this.previousMonthValue3 = previousMonthValue3;
		this.previousMonthWeekDate1 = previousMonthWeekDate1;
		this.previousMonthWeekDate2 = previousMonthWeekDate2;
		this.previousMonthWeekDate3 = previousMonthWeekDate3;
		this.previousMonthWeekValue1 = previousMonthWeekValue1;
		this.previousMonthWeekValue2 = previousMonthWeekValue2;
		this.previousMonthWeekValue3 = previousMonthWeekValue3;
		this.projectiondate = projectiondate;
		this.alreadyInsert=alreadyInsert;
	}

	public String getPreviousMonthValue1() {
		return previousMonthValue1;
	}

	public void setPreviousMonthValue1(String previousMonthValue1) {
		this.previousMonthValue1 = previousMonthValue1;
	}

	public String getPreviousMonthValue2() {
		return previousMonthValue2;
	}

	public void setPreviousMonthValue2(String previousMonthValue2) {
		this.previousMonthValue2 = previousMonthValue2;
	}

	public String getPreviousMonthValue3() {
		return previousMonthValue3;
	}

	public void setPreviousMonthValue3(String previousMonthValue3) {
		this.previousMonthValue3 = previousMonthValue3;
	}

	public String getPreviousMonthWeekValue1() {
		return previousMonthWeekValue1;
	}

	public void setPreviousMonthWeekValue1(String previousMonthWeekValue1) {
		this.previousMonthWeekValue1 = previousMonthWeekValue1;
	}

	public String getPreviousMonthWeekValue2() {
		return previousMonthWeekValue2;
	}

	public void setPreviousMonthWeekValue2(String previousMonthWeekValue2) {
		this.previousMonthWeekValue2 = previousMonthWeekValue2;
	}

	public String getPreviousMonthWeekValue3() {
		return previousMonthWeekValue3;
	}

	public void setPreviousMonthWeekValue3(String previousMonthWeekValue3) {
		this.previousMonthWeekValue3 = previousMonthWeekValue3;
	}
	
	

/*	public BigDecimal getFundEstimtaionDaysId() {
		return fundEstimtaionDaysId;
	}

	public void setFundEstimtaionDaysId(BigDecimal fundEstimtaionDaysId) {
		this.fundEstimtaionDaysId = fundEstimtaionDaysId;
	}*/

	public String getPreviousMonthDate1() {
		return previousMonthDate1;
	}

	public void setPreviousMonthDate1(String previousMonthDate1) {
		this.previousMonthDate1 = previousMonthDate1;
	}

	public String getPreviousMonthDate2() {
		return previousMonthDate2;
	}

	public void setPreviousMonthDate2(String previousMonthDate2) {
		this.previousMonthDate2 = previousMonthDate2;
	}

	public String getPreviousMonthDate3() {
		return previousMonthDate3;
	}

	public void setPreviousMonthDate3(String previousMonthDate3) {
		this.previousMonthDate3 = previousMonthDate3;
	}

	public String getPreviousMonthWeekDate1() {
		return previousMonthWeekDate1;
	}

	public void setPreviousMonthWeekDate1(String previousMonthWeekDate1) {
		this.previousMonthWeekDate1 = previousMonthWeekDate1;
	}

	public String getPreviousMonthWeekDate2() {
		return previousMonthWeekDate2;
	}

	public void setPreviousMonthWeekDate2(String previousMonthWeekDate2) {
		this.previousMonthWeekDate2 = previousMonthWeekDate2;
	}

	public String getPreviousMonthWeekDate3() {
		return previousMonthWeekDate3;
	}

	public void setPreviousMonthWeekDate3(String previousMonthWeekDate3) {
		this.previousMonthWeekDate3 = previousMonthWeekDate3;
	}

	public String getProjectiondate() {
		return projectiondate;
	}

	public void setProjectiondate(String projectiondate) {
		this.projectiondate = projectiondate;
	}


	public boolean isAlreadyInsert() {
		return alreadyInsert;
	}


	public void setAlreadyInsert(boolean alreadyInsert) {
		this.alreadyInsert = alreadyInsert;
	}
	
	
}
