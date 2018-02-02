package com.amg.exchange.cbk.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CBKInfoDataTable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BigDecimal lineNo;
	private BigDecimal lineIndex;
	private String lineDescription;
	private String lineType;
	private BigDecimal totalLevel;
	private String printStatus;
	private BigDecimal printOrder;
	private String cbkCode;
	private BigDecimal zoomIn;
	private Boolean  deleteStatus;
	private BigDecimal cbkLineSeqId;
	private String cbkLineCreatedBy;
	private Date cbkLineCreatedDate;
	private BigDecimal cbkHdrSeqId;
	private BigDecimal cbkDetalisSeqId;
	private String cbkLineModifiedBy;
	private Date cbkLineModifiedDate;

	public BigDecimal getLineNo() {
		return lineNo;
	}

	public void setLineNo(BigDecimal lineNo) {
		this.lineNo = lineNo;
	}

	public String getLineDescription() {
		return lineDescription;
	}

	public void setLineDescription(String lineDescription) {
		this.lineDescription = lineDescription;
	}

	public String getLineType() {
		return lineType;
	}

	public void setLineType(String lineType) {
		this.lineType = lineType;
	}

	public BigDecimal getTotalLevel() {
		return totalLevel;
	}

	public void setTotalLevel(BigDecimal totalLevel) {
		this.totalLevel = totalLevel;
	}

	public String getPrintStatus() {
		return printStatus;
	}

	public void setPrintStatus(String printStatus) {
		this.printStatus = printStatus;
	}

	public BigDecimal getPrintOrder() {
		return printOrder;
	}

	public void setPrintOrder(BigDecimal printOrder) {
		this.printOrder = printOrder;
	}

	public String getCbkCode() {
		return cbkCode;
	}

	public void setCbkCode(String cbkCode) {
		this.cbkCode = cbkCode;
	}

	public BigDecimal getZoomIn() {
		return zoomIn;
	}

	public void setZoomIn(BigDecimal zoomIn) {
		this.zoomIn = zoomIn;
	}

	public Boolean getDeleteStatus() {
		return deleteStatus;
	}

	public void setDeleteStatus(Boolean deleteStatus) {
		this.deleteStatus = deleteStatus;
	}

	public BigDecimal getLineIndex() {
		return lineIndex;
	}

	public void setLineIndex(BigDecimal lineIndex) {
		this.lineIndex = lineIndex;
	}

	public BigDecimal getCbkLineSeqId() {
		return cbkLineSeqId;
	}

	public void setCbkLineSeqId(BigDecimal cbkLineSeqId) {
		this.cbkLineSeqId = cbkLineSeqId;
	}

	public String getCbkLineCreatedBy() {
		return cbkLineCreatedBy;
	}

	public void setCbkLineCreatedBy(String cbkLineCreatedBy) {
		this.cbkLineCreatedBy = cbkLineCreatedBy;
	}

	public Date getCbkLineCreatedDate() {
		return cbkLineCreatedDate;
	}

	public void setCbkLineCreatedDate(Date cbkLineCreatedDate) {
		this.cbkLineCreatedDate = cbkLineCreatedDate;
	}

	public BigDecimal getCbkHdrSeqId() {
		return cbkHdrSeqId;
	}

	public void setCbkHdrSeqId(BigDecimal cbkHdrSeqId) {
		this.cbkHdrSeqId = cbkHdrSeqId;
	}

	public String getCbkLineModifiedBy() {
		return cbkLineModifiedBy;
	}

	public void setCbkLineModifiedBy(String cbkLineModifiedBy) {
		this.cbkLineModifiedBy = cbkLineModifiedBy;
	}

	public Date getCbkLineModifiedDate() {
		return cbkLineModifiedDate;
	}

	public void setCbkLineModifiedDate(Date cbkLineModifiedDate) {
		this.cbkLineModifiedDate = cbkLineModifiedDate;
	}

	public BigDecimal getCbkDetalisSeqId() {
		return cbkDetalisSeqId;
	}

	public void setCbkDetalisSeqId(BigDecimal cbkDetalisSeqId) {
		this.cbkDetalisSeqId = cbkDetalisSeqId;
	}

	

}
