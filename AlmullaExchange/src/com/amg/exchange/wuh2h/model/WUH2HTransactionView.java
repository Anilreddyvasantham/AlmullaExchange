package com.amg.exchange.wuh2h.model;

import java.io.Serializable;
import java.math.BigDecimal;


public class WUH2HTransactionView implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String branchName;
	private BigDecimal sendCount;
	private BigDecimal sendTotalTxnAmount;
	private BigDecimal receiveCount;
	private BigDecimal receiveTotalTxnAmount;
	private BigDecimal locCode;
	
	
	
	public WUH2HTransactionView() {
		super();
	}

	public WUH2HTransactionView(String branchName,BigDecimal sendCount,BigDecimal sendTotalTxnAmount) {
		super();
		this.branchName = branchName;
		this.sendCount = sendCount;
		this.sendTotalTxnAmount = sendTotalTxnAmount;
	}

	public BigDecimal getSendCount() {
		return sendCount;
	}
	public void setSendCount(BigDecimal sendCount) {
		this.sendCount = sendCount;
	}
	
	public BigDecimal getSendTotalTxnAmount() {
		return sendTotalTxnAmount;
	}
	public void setSendTotalTxnAmount(BigDecimal sendTotalTxnAmount) {
		this.sendTotalTxnAmount = sendTotalTxnAmount;
	}
	
	public BigDecimal getReceiveCount() {
		return receiveCount;
	}
	public void setReceiveCount(BigDecimal receiveCount) {
		this.receiveCount = receiveCount;
	}
	
	public BigDecimal getReceiveTotalTxnAmount() {
		return receiveTotalTxnAmount;
	}
	public void setReceiveTotalTxnAmount(BigDecimal receiveTotalTxnAmount) {
		this.receiveTotalTxnAmount = receiveTotalTxnAmount;
	}
	
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	
	public BigDecimal getLocCode() {
		return locCode;
	}
	public void setLocCode(BigDecimal locCode) {
		this.locCode = locCode;
	}
		
}
