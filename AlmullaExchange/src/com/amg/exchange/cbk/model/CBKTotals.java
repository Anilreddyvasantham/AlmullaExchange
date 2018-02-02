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
@Table(name = "EX_CBK_TOTALS")
public class CBKTotals implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal cbkTotalsId;
	private String calcLogic;
	private CBKHeader cbkId;
	private CBKLines cbkLineId;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private BigDecimal totalLineSeqId;
	private BigDecimal applicationCountryId;
	private String isActive;

	public CBKTotals() {

	}	

	public CBKTotals(BigDecimal cbkTotalsId, String calcLogic, CBKHeader cbkId,
			CBKLines cbkLineId, String createdBy, Date createdDate,
			String modifiedBy, Date modifiedDate, BigDecimal totalLineSeqId,
			BigDecimal applicationCountryId, String isActive) {
		super();
		this.cbkTotalsId = cbkTotalsId;
		this.calcLogic = calcLogic;
		this.cbkId = cbkId;
		this.cbkLineId = cbkLineId;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.totalLineSeqId = totalLineSeqId;
		this.applicationCountryId = applicationCountryId;
		this.isActive = isActive;
	}



	@Id
	@GeneratedValue(generator = "ex_cbk_details_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ex_cbk_details_seq", sequenceName = "EX_CBK_TOTALS_SEQ", allocationSize = 1)
	@Column(name = "CBK_TOTAL_SEQ_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getCbkTotalsId() {
		return cbkTotalsId;
	}

	public void setCbkTotalsId(BigDecimal cbkTotalsId) {
		this.cbkTotalsId = cbkTotalsId;
	}

	@Column(name = "CALC_LOGIC")
	public String getCalcLogic() {
		return calcLogic;
	}

	public void setCalcLogic(String calcLogic) {
		this.calcLogic = calcLogic;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CBK_REPORT_SEQ_ID")
	public CBKHeader getCbkId() {
		return cbkId;
	}

	public void setCbkId(CBKHeader cbkId) {
		this.cbkId = cbkId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CBK_LINE_SEQ_ID")
	public CBKLines getCbkLineId() {
		return cbkLineId;
	}

	public void setCbkLineId(CBKLines cbkLineId) {
		this.cbkLineId = cbkLineId;
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

	@Column(name = "TOTAL_LINE_SEQ_ID")
	public BigDecimal getTotalLineSeqId() {
		return totalLineSeqId;
	}

	public void setTotalLineSeqId(BigDecimal totalLineSeqId) {
		this.totalLineSeqId = totalLineSeqId;
	}

	@Column(name = "IS_ACTIVE")
	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Column(name = "APPLICATION_COUNTRY_ID")
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}

	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}

	
}
