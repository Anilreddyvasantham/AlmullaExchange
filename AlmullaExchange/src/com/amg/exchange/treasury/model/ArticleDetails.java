package com.amg.exchange.treasury.model;


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

import com.amg.exchange.registration.model.Customer;

/**
 * ArticleDetails Created by Chennai ODC
 */
@Cacheable  
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "FS_ARTICLE_DETAILS")
public class ArticleDetails implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal articleDetailId;
	private ArticleMaster fsArticleMaster;
	//private String articleDesc;
	private BigDecimal weekly;
	private BigDecimal monthly;
	private BigDecimal yearly;
	private String articleDetailCode;
	private String createdBy;
	

	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;
	private String isActive;
	
	private List<Customer> fsCustomers = new ArrayList<Customer>();

	public ArticleDetails() {
	}

	
	
	public ArticleDetails(BigDecimal articleDetailId,
			ArticleMaster fsArticleMaster, 
			BigDecimal weekly, BigDecimal monthly, BigDecimal yearly,
			String articleDetailCode, String createdBy, Date createdDate,
			String modifiedBy, Date modifiedDate, String approvedBy,
			Date approvedDate, String remarks, String isActive,
			List<Customer> fsCustomers) {
		super();
		this.articleDetailId = articleDetailId;
		this.fsArticleMaster = fsArticleMaster;
		this.weekly = weekly;
		this.monthly = monthly;
		this.yearly = yearly;
		this.articleDetailCode = articleDetailCode;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.remarks = remarks;
		this.isActive = isActive;
		this.fsCustomers = fsCustomers;
	}

	@Id
	@GeneratedValue(generator="fs_article_details_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="fs_article_details_seq",sequenceName="FS_ARTICLE_DETAILS_SEQ",allocationSize=1)
	@Column(name = "ARTICLE_DETAIL_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getArticleDetailId() {
		return this.articleDetailId;
	}

	public void setArticleDetailId(BigDecimal articleDetailId) {
		this.articleDetailId = articleDetailId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ARTICLE_ID")
	public ArticleMaster getFsArticleMaster() {
		return this.fsArticleMaster;
	}

	public void setFsArticleMaster(ArticleMaster fsArticleMaster) {
		this.fsArticleMaster = fsArticleMaster;
	}

	/*@Column(name = "ARTICLE_DESC", length = 50)
	public String getArticleDesc() {
		return this.articleDesc;
	}

	public void setArticleDesc(String articleDesc) {
		this.articleDesc = articleDesc;
	}*/

	@Column(name = "WEEKLY", precision = 22, scale = 0)
	public BigDecimal getWeekly() {
		return this.weekly;
	}

	public void setWeekly(BigDecimal weekly) {
		this.weekly = weekly;
	}

	@Column(name = "MONTHLY", precision = 22, scale = 0)
	public BigDecimal getMonthly() {
		return this.monthly;
	}

	public void setMonthly(BigDecimal monthly) {
		this.monthly = monthly;
	}

	@Column(name = "YEARLY", precision = 22, scale = 0)
	public BigDecimal getYearly() {
		return this.yearly;
	}

	public void setYearly(BigDecimal yearly) {
		this.yearly = yearly;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsArticleDetails")
	public List<Customer> getFsCustomers() {
		return fsCustomers;
	}

	public void setFsCustomers(List<Customer> fsCustomers) {
		this.fsCustomers = fsCustomers;
	}
	
	@Column(name = "ARTICLE_DETAIL_CODE")
	public String getArticleDetailCode() {
		return articleDetailCode;
	}

	public void setArticleDetailCode(String articleDetailCode) {
		this.articleDetailCode = articleDetailCode;
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

	
}
