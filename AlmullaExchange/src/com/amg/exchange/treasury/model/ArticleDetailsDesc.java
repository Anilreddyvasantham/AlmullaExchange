package com.amg.exchange.treasury.model;

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
@Table(name = "FS_ARTICLE_DETAILS_DESC")
public class ArticleDetailsDesc implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private BigDecimal articleDetailsDescId;
	private ArticleDetails articleDetails;
	private String articleDetailDesc;
	private LanguageType languageId;

	public ArticleDetailsDesc() {
	}

	public ArticleDetailsDesc(BigDecimal articleDetailsDescId,
			ArticleDetails articleDetails, String articleDetailDesc,
			LanguageType languageId) {
		super();
		this.articleDetailsDescId = articleDetailsDescId;
		this.articleDetails = articleDetails;
		this.articleDetailDesc = articleDetailDesc;
		this.languageId = languageId;
	}

	@Id
	@GeneratedValue(generator = "fs_article_details_desc_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "fs_article_details_desc_seq", sequenceName = "FS_ARTICLE_DETAILS_DESC_SEQ", allocationSize = 1)
	@Column(name = "ARTICLE_DETAILS_DESC_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getArticleDetailsDescId() {
		return articleDetailsDescId;
	}

	public void setArticleDetailsDescId(BigDecimal articleDetailsDescId) {
		this.articleDetailsDescId = articleDetailsDescId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ARTICLE_DETAILS_ID")
	public ArticleDetails getArticleDetails() {
		return articleDetails;
	}

	public void setArticleDetails(ArticleDetails articleDetails) {
		this.articleDetails = articleDetails;
	}

	@Column(name = "ARTICLE_DETAIL_DESC")
	public String getArticleDetailDesc() {
		return articleDetailDesc;
	}

	public void setArticleDetailDesc(String articleDetailDesc) {
		this.articleDetailDesc = articleDetailDesc;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LANGUAGE_ID")
	public LanguageType getLanguageId() {
		return languageId;
	}

	public void setLanguageId(LanguageType languageId) {
		this.languageId = languageId;
	}

}
