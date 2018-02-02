package com.amg.exchange.cbk.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class LineTotalDataTable implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private BigDecimal totalLineNo;
	private String lineDescription;
	private String computation;
	
	private BigDecimal cbkLineSeqId;
	private BigDecimal cbkHdrSeqId;
	private BigDecimal cbkTotalsSeqId;
	private String cbkTotalsCreatedBy;
	private Date cbkTotalsCreatedDate;
	
	
	private String cbkTotalsModifiedBy;
	private Date cbkTotalsModifiedDate;
	
	private List<CBKTotalLineNo> lstLineNo;
	private BigDecimal selectedLineNo;
	private BigDecimal lineIndex;
	
	public BigDecimal getTotalLineNo() {
		return totalLineNo;
	}
	public void setTotalLineNo(BigDecimal totalLineNo) {
		this.totalLineNo = totalLineNo;
	}
	public String getLineDescription() {
		return lineDescription;
	}
	public void setLineDescription(String lineDescription) {
		this.lineDescription = lineDescription;
	}
	public String getComputation() {
		return computation;
	}
	public void setComputation(String computation) {
		this.computation = computation;
	}
	public BigDecimal getCbkLineSeqId() {
		return cbkLineSeqId;
	}
	public void setCbkLineSeqId(BigDecimal cbkLineSeqId) {
		this.cbkLineSeqId = cbkLineSeqId;
	}
	public BigDecimal getCbkHdrSeqId() {
		return cbkHdrSeqId;
	}
	public void setCbkHdrSeqId(BigDecimal cbkHdrSeqId) {
		this.cbkHdrSeqId = cbkHdrSeqId;
	}
	public BigDecimal getCbkTotalsSeqId() {
		return cbkTotalsSeqId;
	}
	public void setCbkTotalsSeqId(BigDecimal cbkTotalsSeqId) {
		this.cbkTotalsSeqId = cbkTotalsSeqId;
	}
	public String getCbkTotalsCreatedBy() {
		return cbkTotalsCreatedBy;
	}
	public void setCbkTotalsCreatedBy(String cbkTotalsCreatedBy) {
		this.cbkTotalsCreatedBy = cbkTotalsCreatedBy;
	}
	public Date getCbkTotalsCreatedDate() {
		return cbkTotalsCreatedDate;
	}
	public void setCbkTotalsCreatedDate(Date cbkTotalsCreatedDate) {
		this.cbkTotalsCreatedDate = cbkTotalsCreatedDate;
	}
	public String getCbkTotalsModifiedBy() {
		return cbkTotalsModifiedBy;
	}
	public void setCbkTotalsModifiedBy(String cbkTotalsModifiedBy) {
		this.cbkTotalsModifiedBy = cbkTotalsModifiedBy;
	}
	public Date getCbkTotalsModifiedDate() {
		return cbkTotalsModifiedDate;
	}
	public void setCbkTotalsModifiedDate(Date cbkTotalsModifiedDate) {
		this.cbkTotalsModifiedDate = cbkTotalsModifiedDate;
	}
	public List<CBKTotalLineNo> getLstLineNo() {
		return lstLineNo;
	}
	public void setLstLineNo(List<CBKTotalLineNo> lstLineNo) {
		this.lstLineNo = lstLineNo;
	}
	public BigDecimal getSelectedLineNo() {
		return selectedLineNo;
	}
	public void setSelectedLineNo(BigDecimal selectedLineNo) {
		this.selectedLineNo = selectedLineNo;
	}
	public BigDecimal getLineIndex() {
		return lineIndex;
	}
	public void setLineIndex(BigDecimal lineIndex) {
		this.lineIndex = lineIndex;
	}
	
	
}
