package com.amg.exchange.wuh2h.settlement.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class WUH2HTransactionInfo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String branchName;
	private BigDecimal sendTxnCount;
	private BigDecimal sendTxnAmount;
	private BigDecimal receiveTxnCount;
	private BigDecimal receiveTxnAmount;
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public BigDecimal getSendTxnCount() {
		return sendTxnCount;
	}
	public void setSendTxnCount(BigDecimal sendTxnCount) {
		this.sendTxnCount = sendTxnCount;
	}
	public BigDecimal getSendTxnAmount() {
		return sendTxnAmount;
	}
	public void setSendTxnAmount(BigDecimal sendTxnAmount) {
		this.sendTxnAmount = sendTxnAmount;
	}
	public BigDecimal getReceiveTxnCount() {
		return receiveTxnCount;
	}
	public void setReceiveTxnCount(BigDecimal receiveTxnCount) {
		this.receiveTxnCount = receiveTxnCount;
	}
	public BigDecimal getReceiveTxnAmount() {
		return receiveTxnAmount;
	}
	public void setReceiveTxnAmount(BigDecimal receiveTxnAmount) {
		this.receiveTxnAmount = receiveTxnAmount;
	}
	
	
}
