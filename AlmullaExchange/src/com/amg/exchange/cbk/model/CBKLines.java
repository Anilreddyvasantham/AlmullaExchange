package com.amg.exchange.cbk.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "EX_CBK_LINES")
public class CBKLines implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal cbkLineId;
	private BigDecimal lineNo;
	private String lineDescription;
	private String lineType;
	private BigDecimal totalLevel;
	private String printOption;
	private BigDecimal printOrder;
	private String cbkReference;
	private CBKHeader cbkId;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private BigDecimal applicationCountryId;
	private String isActive;

	public CBKLines() {

	}

	public CBKLines(BigDecimal cbkLineId, BigDecimal lineNo,
			String lineDescription, String lineType, BigDecimal totalLevel,
			String printOption, BigDecimal printOrder, String cbkReference,
			CBKHeader cbkId, String createdBy, Date createdDate,
			String modifiedBy, Date modifiedDate) {
		super();
		this.cbkLineId = cbkLineId;
		this.lineNo = lineNo;
		this.lineDescription = lineDescription;
		this.lineType = lineType;
		this.totalLevel = totalLevel;
		this.printOption = printOption;
		this.printOrder = printOrder;
		this.cbkReference = cbkReference;
		this.cbkId = cbkId;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}

	@Id
	@GeneratedValue(generator = "ex_cbk_lines_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ex_cbk_lines_seq", sequenceName = "EX_CBK_LINES_SEQ", allocationSize = 1)
	@Column(name = "CBK_LINE_SEQ_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getCbkLineId() {
		return cbkLineId;
	}

	public void setCbkLineId(BigDecimal cbkLineId) {
		this.cbkLineId = cbkLineId;
	}

	@Column(name = "LINE_NO")
	public BigDecimal getLineNo() {
		return lineNo;
	}

	public void setLineNo(BigDecimal lineNo) {
		this.lineNo = lineNo;
	}

	@Column(name = "LINE_DESCRIPTION")
	public String getLineDescription() {
		return lineDescription;
	}

	public void setLineDescription(String lineDescription) {
		this.lineDescription = lineDescription;
	}

	@Column(name = "LINE_TYPE")
	public String getLineType() {
		return lineType;
	}

	public void setLineType(String lineType) {
		this.lineType = lineType;
	}

	@Column(name = "TOTAL_LEVEL")
	public BigDecimal getTotalLevel() {
		return totalLevel;
	}

	public void setTotalLevel(BigDecimal totalLevel) {
		this.totalLevel = totalLevel;
	}

	@Column(name = "PRINT_OPTION")
	public String getPrintOption() {
		return printOption;
	}

	public void setPrintOption(String printOption) {
		this.printOption = printOption;
	}

	@Column(name = "PRINT_ORDER")
	public BigDecimal getPrintOrder() {
		return printOrder;
	}

	public void setPrintOrder(BigDecimal printOrder) {
		this.printOrder = printOrder;
	}

	@Column(name = "CBK_REFERENCE")
	public String getCbkReference() {
		return cbkReference;
	}

	public void setCbkReference(String cbkReference) {
		this.cbkReference = cbkReference;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CBK_REPORT_SEQ_ID")
	public CBKHeader getCbkId() {
		return cbkId;
	}

	public void setCbkId(CBKHeader cbkId) {
		this.cbkId = cbkId;
	}

	@Column(name = "CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "MODIFIED_BY")
	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "MODIFIED_DATE")
	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name = "APPLICATION_COUNTRY_ID")
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}

	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}

	@Column(name = "IS_ACTIVE")
	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

}
