package com.amg.exchange.loyalty.model;

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
@Table(name = "EX_LTY_TYPE_MASTER_DESC")
public class LoyaltyTypeDesc implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private BigDecimal loyalityTypeDescId;
	private LoyaltyType loyalityTypeId;
	private String shortDescription;
	private String fullDescription;
	private LanguageType languageId;

	public LoyaltyTypeDesc() {
	}

	public LoyaltyTypeDesc(BigDecimal loyalityTypeDescId, LoyaltyType loyalityTypeId, String shortDescription, String fullDescription, LanguageType languageId) {
		super();
		this.loyalityTypeDescId = loyalityTypeDescId;
		this.loyalityTypeId = loyalityTypeId;
		this.shortDescription = shortDescription;
		this.fullDescription = fullDescription;
		this.languageId = languageId;
	}

	@Id
	@GeneratedValue(generator = "ex_lty_type_master_desc_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ex_lty_type_master_desc_seq", sequenceName = "EX_LTY_TYPE_MASTER_DESC_SEQ", allocationSize = 1)
	@Column(name = "LTY_TYPE_DESC_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getLoyalityTypeDescId() {
		return loyalityTypeDescId;
	}

	public void setLoyalityTypeDescId(BigDecimal loyalityTypeDescId) {
		this.loyalityTypeDescId = loyalityTypeDescId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LTY_TYPE_ID")
	public LoyaltyType getLoyalityTypeId() {
		return loyalityTypeId;
	}

	public void setLoyalityTypeId(LoyaltyType loyalityTypeId) {
		this.loyalityTypeId = loyalityTypeId;
	}

	@Column(name = "SHORT_DESC")
	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	@Column(name = "FULL_DESC")
	public String getFullDescription() {
		return fullDescription;
	}

	public void setFullDescription(String fullDescription) {
		this.fullDescription = fullDescription;
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
