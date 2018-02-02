package com.amg.exchange.registration.model;

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

import com.amg.exchange.common.model.CountryMaster;

@Entity
@Table(name = "FS_INCOME_RANGE_VALUE_MASTER")
public class IncomeRangeValue implements Serializable {
	
	private static final long serialVersionUID = 1L;


	private BigDecimal incomeValueTransactionId;
	private CountryMaster appCountryId;
	private BigDecimal valuePerTranx;
	private BigDecimal valuePerDay;
	private BigDecimal valuePerWeek;
	private BigDecimal valuePerMonth;
	private BigDecimal valuePerAnnum;
	private BigDecimal noofTranxPerDay;
	private BigDecimal noofTranxPerWeek;
	private BigDecimal noofTranxPerMonth;
	private BigDecimal noofTranxPerAnnum;
	private String isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;
	private BigDecimal incomeRangeFrom;
	private BigDecimal incomeRangeTo;


	public IncomeRangeValue() {
		// TODO Auto-generated constructor stub
	}
	
	@Id
	@GeneratedValue(generator="fs_income_range_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="fs_income_range_seq",sequenceName="FS_INCOME_RANGE_VALUE_SEQ",allocationSize=1)
	@Column(name = "INCOME_RANGE_VALUE_MASTER_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getIncomeValueTransactionId() {
		return incomeValueTransactionId;
	}


	public void setIncomeValueTransactionId(BigDecimal incomeValueTransactionId) {
		this.incomeValueTransactionId = incomeValueTransactionId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "APPLICATION_COUNTRY_ID")
	public CountryMaster getAppCountryId() {
		return appCountryId;
	}


	public void setAppCountryId(CountryMaster appCountryId) {
		this.appCountryId = appCountryId;
	}

	@Column(name="VALUE_PER_TRANX")
	public BigDecimal getValuePerTranx() {
		return valuePerTranx;
	}


	public void setValuePerTranx(BigDecimal valuePerTranx) {
		this.valuePerTranx = valuePerTranx;
	}

	@Column(name="VALUE_PER_DAY")
	public BigDecimal getValuePerDay() {
		return valuePerDay;
	}


	public void setValuePerDay(BigDecimal valuePerDay) {
		this.valuePerDay = valuePerDay;
	}

	@Column(name="VALUE_PER_WEEK")
	public BigDecimal getValuePerWeek() {
		return valuePerWeek;
	}


	public void setValuePerWeek(BigDecimal valuePerWeek) {
		this.valuePerWeek = valuePerWeek;
	}

	@Column(name="VALUE_PER_MONTH")
	public BigDecimal getValuePerMonth() {
		return valuePerMonth;
	}


	public void setValuePerMonth(BigDecimal valuePerMonth) {
		this.valuePerMonth = valuePerMonth;
	}

	@Column(name="VALUE_PER_ANNUM")
	public BigDecimal getValuePerAnnum() {
		return valuePerAnnum;
	}


	public void setValuePerAnnum(BigDecimal valuePerAnnum) {
		this.valuePerAnnum = valuePerAnnum;
	}

	@Column(name="NUMBER_OF_TRANX_PER_DAY")
	public BigDecimal getNoofTranxPerDay() {
		return noofTranxPerDay;
	}


	public void setNoofTranxPerDay(BigDecimal noofTranxPerDay) {
		this.noofTranxPerDay = noofTranxPerDay;
	}

	@Column(name="NUMBER_OF_TRANX_PER_WEEK")
	public BigDecimal getNoofTranxPerWeek() {
		return noofTranxPerWeek;
	}


	public void setNoofTranxPerWeek(BigDecimal noofTranxPerWeek) {
		this.noofTranxPerWeek = noofTranxPerWeek;
	}

	@Column(name="NUMBER_OF_TRANX_PER_MONTH")
	public BigDecimal getNoofTranxPerMonth() {
		return noofTranxPerMonth;
	}


	public void setNoofTranxPerMonth(BigDecimal noofTranxPerMonth) {
		this.noofTranxPerMonth = noofTranxPerMonth;
	}

	@Column(name="NUMBER_OF_TRANX_PER_ANNUM")
	public BigDecimal getNoofTranxPerAnnum() {
		return noofTranxPerAnnum;
	}


	public void setNoofTranxPerAnnum(BigDecimal noofTranxPerAnnum) {
		this.noofTranxPerAnnum = noofTranxPerAnnum;
	}

	@Column(name="ISACTIVE")
	public String getIsActive() {
		return isActive;
	}


	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Column(name="CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name="MODIFIED_BY")
	public String getModifiedBy() {
		return modifiedBy;
	}


	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name="MODIFIED_DATE")
	public Date getModifiedDate() {
		return modifiedDate;
	}


	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name="APPROVED_BY")
	public String getApprovedBy() {
		return approvedBy;
	}


	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	@Column(name="APPROVED_DATE")
	public Date getApprovedDate() {
		return approvedDate;
	}


	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	@Column(name="REMARKS")
	public String getRemarks() {
		return remarks;
	}


	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name="INCOME_RANGE_FROM")
	public BigDecimal getIncomeRangeFrom() {
		return incomeRangeFrom;
	}


	public void setIncomeRangeFrom(BigDecimal incomeRangeFrom) {
		this.incomeRangeFrom = incomeRangeFrom;
	}

	@Column(name="INCOME_RANGE_TO")
	public BigDecimal getIncomeRangeTo() {
		return incomeRangeTo;
	}


	public void setIncomeRangeTo(BigDecimal incomeRangeTo) {
		this.incomeRangeTo = incomeRangeTo;
	}
	

	

}
