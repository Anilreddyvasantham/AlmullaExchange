package com.amg.exchange.registration.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.treasury.model.ArticleDetails;

/**
 * ArticleDetails Created by Chennai ODC
 */
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "FS_INCOME_RANGE_MASTER")
public class IncomeRangeMaster implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal incomeRangeId;
	private ArticleDetails articleDetail;
	private CountryMaster fsCountryMaster;
	private String monthlyIncome;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;
	private String isActive;
	private BigDecimal incomeRangeFrom;
	private BigDecimal incomeRangeTo;

	private List<Customer> fsCustomers = new ArrayList<Customer>();

	public IncomeRangeMaster() {
	}

	public IncomeRangeMaster(BigDecimal incomeRangeId) {
		this.incomeRangeId = incomeRangeId;
	}

	public IncomeRangeMaster(BigDecimal incomeRangeId, ArticleDetails articleDetail, CountryMaster fsCountryMaster, String monthlyIncome, String createdBy, Date createdDate, String modifiedBy, Date modifiedDate, String approvedBy, Date approvedDate, String remarks, String isActive,
			BigDecimal incomeRangeFrom, BigDecimal incomeRangeTo, List<Customer> fsCustomers) {
		this.incomeRangeId = incomeRangeId;
		this.articleDetail = articleDetail;
		this.fsCountryMaster = fsCountryMaster;
		this.monthlyIncome = monthlyIncome;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.remarks = remarks;
		this.isActive = isActive;
		this.incomeRangeFrom = incomeRangeFrom;
		this.incomeRangeTo = incomeRangeTo;
		this.fsCustomers = fsCustomers;
	}

	@Id
	@GeneratedValue(generator = "fs_income_range_master_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "fs_income_range_master_seq", sequenceName = "FS_INCOME_RANGE_MASTER_SEQ", allocationSize = 1)
	@Column(name = "INCOME_RANGE_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getIncomeRangeId() {
		return incomeRangeId;
	}

	public void setIncomeRangeId(BigDecimal incomeRangeId) {
		this.incomeRangeId = incomeRangeId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ARTICLE_DETAIL_ID")
	public ArticleDetails getArticleDetail() {
		return articleDetail;
	}

	public void setArticleDetail(ArticleDetails articleDetail) {
		this.articleDetail = articleDetail;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "APPLICATION_COUNTRY_ID")
	public CountryMaster getFsCountryMaster() {
		return fsCountryMaster;
	}

	public void setFsCountryMaster(CountryMaster fsCountryMaster) {
		this.fsCountryMaster = fsCountryMaster;
	}

	@Column(name = "MONTHLY_INCOME", length = 200)
	public String getMonthlyIncome() {
		return monthlyIncome;
	}

	public void setMonthlyIncome(String monthlyIncome) {
		this.monthlyIncome = monthlyIncome;
	}

	@Column(name = "CREATED_BY", length = 200)
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

	@Column(name = "APPROVED_BY")
	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	@Column(name = "APPROVED_DATE")
	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	@Column(name = "REMARKS")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name = "ISACTIVE")
	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsIncomeRangeMaster")
	public List<Customer> getFsCustomers() {
		return fsCustomers;
	}

	public void setFsCustomers(List<Customer> fsCustomers) {
		this.fsCustomers = fsCustomers;
	}
	@Column(name = "INCOME_FROM")
	public BigDecimal getIncomeRangeFrom() {
		return incomeRangeFrom;
	}

	public void setIncomeRangeFrom(BigDecimal incomeRangeFrom) {
		this.incomeRangeFrom = incomeRangeFrom;
	}
	@Column(name = "INCOME_TO")
	public BigDecimal getIncomeRangeTo() {
		return incomeRangeTo;
	}

	public void setIncomeRangeTo(BigDecimal incomeRangeTo) {
		this.incomeRangeTo = incomeRangeTo;
	}

	
}
