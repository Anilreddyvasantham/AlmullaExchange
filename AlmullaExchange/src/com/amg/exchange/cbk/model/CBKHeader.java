package com.amg.exchange.cbk.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "EX_CBK_HD")
public class CBKHeader implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal cbkId;
	private BigDecimal applicationCountryId;
	private String reportNo;
	private String reportName;
	private String frequencyId;
	private String isactive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;

	public CBKHeader() {

	}
	
	

	public CBKHeader(BigDecimal cbkId,BigDecimal applicationCountryId, String reportNo, String reportName,
			String frequencyId, String isactive, String createdBy,
			Date createdDate, String modifiedBy, Date modifiedDate) {
		super();
		this.cbkId = cbkId;
		this.applicationCountryId = applicationCountryId;
		this.reportNo = reportNo;
		this.reportName = reportName;
		this.frequencyId = frequencyId;
		this.isactive = isactive;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}



	@Id
	@GeneratedValue(generator = "ex_cbk_hd_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ex_cbk_hd_seq", sequenceName = "EX_CBK_HD_SEQ", allocationSize = 1)
	@Column(name = "CBK_REPORT_SEQ_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getCbkId() {
		return cbkId;
	}

	public void setCbkId(BigDecimal cbkId) {
		this.cbkId = cbkId;
	}
	
	@Column(name = "REPORT_NO")
	public String getReportNo() {
		return reportNo;
	}	
	
	public void setReportNo(String reportNo) {
		this.reportNo = reportNo;
	}

	@Column(name = "APPLICATION_COUNTRY_ID")
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}

	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}	
	
	@Column(name = "REPORT_NAME")
	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	
	@Column(name = "FREQUENCY_ID")
	public String getFrequencyId() {
		return frequencyId;
	}

	public void setFrequencyId(String frequencyId) {
		this.frequencyId = frequencyId;
	}
	
	@Column(name = "IS_ACTIVE")
	public String getIsactive() {
		return isactive;
	}

	public void setIsactive(String isactive) {
		this.isactive = isactive;
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

}
