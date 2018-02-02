package com.amg.exchange.treasury.model;



import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Cacheable;
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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.amg.exchange.common.model.LanguageType;

@Cacheable  
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "FS_ARTICLE_MASTER_DESC")
public class ArticleMasterDesc implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal articleDescId;
	private ArticleMaster articleMaster;
	private LanguageType languageType;
	private String articleeDescription;

	public ArticleMasterDesc() {
		super();
	}

	public ArticleMasterDesc(BigDecimal articleDescId) {
		super();
		this.articleDescId = articleDescId;
	}

	public ArticleMasterDesc(BigDecimal articleDescId, ArticleMaster articleMaster, LanguageType languageType, String articleeDescription) {
		super();
		this.articleDescId = articleDescId;
		this.articleMaster = articleMaster;
		this.languageType = languageType;
		this.articleeDescription = articleeDescription;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LANGUAGE_ID")
	public LanguageType getLanguageType() {
		return languageType;
	}

	public void setLanguageType(LanguageType languageType) {
		this.languageType = languageType;
	}

	@Id
	@GeneratedValue(generator = "fs_articale_masterdesc_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "fs_articale_masterdesc_seq", sequenceName = "FS_ARTICLE_MASTER_DESC_SEQ", allocationSize = 1)
	@Column(name = "ARTICLE_DESC_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getArticleDescId() {
		return articleDescId;
	}

	public void setArticleDescId(BigDecimal articleDescId) {
		this.articleDescId = articleDescId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ARTICLE_ID")
	public ArticleMaster getArticleMaster() {
		return articleMaster;
	}

	public void setArticleMaster(ArticleMaster articleMaster) {
		this.articleMaster = articleMaster;
	}

	@Column(name = "ARTICLE_DESC")
	public String getArticleeDescription() {
		return articleeDescription;
	}

	public void setArticleeDescription(String articleeDescription) {
		this.articleeDescription = articleeDescription;
	}
}

