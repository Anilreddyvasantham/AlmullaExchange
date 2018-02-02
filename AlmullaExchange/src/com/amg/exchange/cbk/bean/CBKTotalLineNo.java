package com.amg.exchange.cbk.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class CBKTotalLineNo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal cbkLineNo;
	private BigDecimal cbkLineSeqId;
	private String lineDesc;
	
	public BigDecimal getCbkLineNo() {
		return cbkLineNo;
	}
	public void setCbkLineNo(BigDecimal cbkLineNo) {
		this.cbkLineNo = cbkLineNo;
	}
	public BigDecimal getCbkLineSeqId() {
		return cbkLineSeqId;
	}
	public void setCbkLineSeqId(BigDecimal cbkLineSeqId) {
		this.cbkLineSeqId = cbkLineSeqId;
	}
	public String getLineDesc() {
		return lineDesc;
	}
	public void setLineDesc(String lineDesc) {
		this.lineDesc = lineDesc;
	}
	
	

}
