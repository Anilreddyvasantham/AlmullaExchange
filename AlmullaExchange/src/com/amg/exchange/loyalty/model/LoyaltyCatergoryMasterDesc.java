package com.amg.exchange.loyalty.model;

import java.io.Serializable;
import java.math.BigDecimal;

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

import com.amg.exchange.common.model.LanguageType;

@Entity
@Table(name = "EX_LTY_CATEGORY_MASTER_DESC")
public class LoyaltyCatergoryMasterDesc implements Serializable {

	private static final long serialVersionUID = 1L;
	private BigDecimal loyaltyCategoryMasterdescId;
	private LoyaltyCatagoryMaster loyaltyCategoryId;
	private LanguageType languageId;
	private String shortDesc;
	private String fullDesc;

	public LoyaltyCatergoryMasterDesc() {
	}

	public LoyaltyCatergoryMasterDesc(BigDecimal loyaltyCategoryMasterdesc,
			LoyaltyCatagoryMaster loyaltyCategoryId, LanguageType languageId,
			String shortDesc, String fullDesc) {
		super();
		this.loyaltyCategoryMasterdescId = loyaltyCategoryMasterdesc;
		this.loyaltyCategoryId = loyaltyCategoryId;
		this.languageId = languageId;
		this.shortDesc = shortDesc;
		this.fullDesc = fullDesc;
	}

	@Id
	@GeneratedValue(generator = "ex_lty_cate_master_desc_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ex_lty_cate_master_desc_seq", sequenceName = "EX_LTY_CATE_MASTER_DESC_SEQ", allocationSize = 1)
	@Column(name = "LTY_CATEGORY_MASTER_DESC_ID")
	public BigDecimal getLoyaltyCategoryMasterdescId() {
		return loyaltyCategoryMasterdescId;
	}

	public void setLoyaltyCategoryMasterdescId(BigDecimal loyaltyCategoryMasterdescId) {
		this.loyaltyCategoryMasterdescId = loyaltyCategoryMasterdescId;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LTY_CATEGORY_ID")
	public LoyaltyCatagoryMaster getLoyaltyCategoryId() {
		return loyaltyCategoryId;
	}

	public void setLoyaltyCategoryId(LoyaltyCatagoryMaster loyaltyCategoryId) {
		this.loyaltyCategoryId = loyaltyCategoryId;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LANGUAGE_ID")
	public LanguageType getLanguageId() {
		return languageId;
	}

	public void setLanguageId(LanguageType languageId) {
		this.languageId = languageId;
	}

	@Column(name = "SHORT_DESC")
	public String getShortDesc() {
		return shortDesc;
	}

	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}

	@Column(name = "FULL_DESC")
	public String getFullDesc() {
		return fullDesc;
	}

	public void setFullDesc(String fullDesc) {
		this.fullDesc = fullDesc;
	}
	
}
