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

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;

@Cacheable  
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "FS_ARTICLE_MASTER")
public class ArticleMaster implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal articleId;
	private CompanyMaster fsCompanyMaster;
	private CountryMaster fsCountryMaster;
	//private String articleName;
	private String creator;
	private String modifier;
	private Date createDate;
	private Date updateDate;
	private String customerType;
	private List<ArticleDetails> fsArticleDetailses = new ArrayList<ArticleDetails>();
	private String isActive;
	private String remarks;
	private String articleCode;
	private String approvedBy;
	private Date approvedDate;
	

	public ArticleMaster() {
	}

	public ArticleMaster(BigDecimal articleId) {
		this.articleId = articleId;
	}

	

	public ArticleMaster(BigDecimal articleId, CompanyMaster fsCompanyMaster,
			CountryMaster fsCountryMaster, String creator,
			String modifier, Date createDate, Date updateDate,
			String customerType, List<ArticleDetails> fsArticleDetailses) {
		this.articleId = articleId;
		this.fsCompanyMaster = fsCompanyMaster;
		this.fsCountryMaster = fsCountryMaster;
		this.creator = creator;
		this.modifier = modifier;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.customerType = customerType;
		this.fsArticleDetailses = fsArticleDetailses;
	}
	
	
	
	public ArticleMaster(BigDecimal articleId, CompanyMaster fsCompanyMaster, CountryMaster fsCountryMaster, String articleName, String creator, String modifier, Date createDate, Date updateDate, String customerType, List<ArticleDetails> fsArticleDetailses, String isActive, String remarks,
			String articleCode, String approvedBy, Date approvedDate) {
		super();
		this.articleId = articleId;
		this.fsCompanyMaster = fsCompanyMaster;
		this.fsCountryMaster = fsCountryMaster;
		this.creator = creator;
		this.modifier = modifier;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.customerType = customerType;
		this.fsArticleDetailses = fsArticleDetailses;
		this.isActive = isActive;
		this.remarks = remarks;
		this.articleCode = articleCode;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
	}

	@Id
	@GeneratedValue(generator="fs_articale_master_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="fs_articale_master_seq",sequenceName="FS_ARTICLE_MASTER_SEQ",allocationSize=1)
	@Column(name = "ARTICLE_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getArticleId() {
		return this.articleId;
	}

	public void setArticleId(BigDecimal articleId) {
		this.articleId = articleId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPANY_ID")
	public CompanyMaster getFsCompanyMaster() {
		return this.fsCompanyMaster;
	}

	public void setFsCompanyMaster(CompanyMaster fsCompanyMaster) {
		this.fsCompanyMaster = fsCompanyMaster;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_ID")
	public CountryMaster getFsCountryMaster() {
		return this.fsCountryMaster;
	}

	public void setFsCountryMaster(CountryMaster fsCountryMaster) {
		this.fsCountryMaster = fsCountryMaster;
	}
/*
	@Column(name = "ARTICLE_NAME", length = 50)
	public String getArticleName() {
		return this.articleName;
	}

	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}*/

	@Column(name = "CREATED_BY", length = 50)
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name = "MODIFIED_BY", length = 50)
	public String getModifier() {
		return this.modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	@Column(name = "CREATED_DATE")
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "MODIFIED_DATE")
	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsArticleMaster")
	public List<ArticleDetails> getFsArticleDetailses() {
		return this.fsArticleDetailses;
	}

	public void setFsArticleDetailses(List<ArticleDetails> fsArticleDetailses) {
		this.fsArticleDetailses = fsArticleDetailses;
	}

	@Column(name="CUSTOMER_TYPE", length = 1)
	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	@Column(name="ISACTIVE", length = 1)
	public String getIsActive() {
		return isActive;
	}

	
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Column(name="REMARKS")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name="ARTICLE_CODE")
	public String getArticleCode() {
		return articleCode;
	}

	public void setArticleCode(String articleCode) {
		this.articleCode = articleCode;
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
	
	
	

}
