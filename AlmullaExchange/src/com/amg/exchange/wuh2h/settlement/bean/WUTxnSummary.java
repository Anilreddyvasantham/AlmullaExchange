package com.amg.exchange.wuh2h.settlement.bean;

import java.util.Date;
import java.util.List;

public class WUTxnSummary {
	
	private Date transactionDate;
	private List<WUTransaction> summaryList;
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public List<WUTransaction> getSummaryList() {
		return summaryList;
	}
	public void setSummaryList(List<WUTransaction> summaryList) {
		this.summaryList = summaryList;
	}	
	

}
