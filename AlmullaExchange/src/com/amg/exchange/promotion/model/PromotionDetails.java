package com.amg.exchange.promotion.model;

import java.math.BigDecimal;
import java.sql.Clob;
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
@Table(name = "EX_PROMO_DETAIL")
public class PromotionDetails implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BigDecimal promotionDetailsId;
	private PromotionMaster promotionMaster;
	private PromotionPrize promotionPrize;
	private CountryMaster applicationCountry;
	private Date drawDate;
	private BigDecimal remittanceTrasactionId;
	private BigDecimal customerId;
	private BigDecimal prizeNo;
	private Date validUpto;
	private Date createdDate;
	private String createdBy;
	private Date modifiedDate;
	private String modifiedBy;
	private Clob signatureSpecimenClob;
	private Date declarationDate;
	
	public PromotionDetails()
	{

	}
	
	

	public PromotionDetails(BigDecimal promotionDetailsId,
			PromotionMaster promotionMaster, PromotionPrize promotionPrize,
			CountryMaster applicationCountry, Date drawDate,
			BigDecimal remittanceTrasactionId, BigDecimal customerId,
			BigDecimal prizeNo, Date validUpto, Date createdDate,
			String createdBy, Date modifiedDate, String modifiedBy,Clob signatureSpecimenClob,Date declarationDate) {
		super();
		this.promotionDetailsId = promotionDetailsId;
		this.promotionMaster = promotionMaster;
		this.promotionPrize = promotionPrize;
		this.applicationCountry = applicationCountry;
		this.drawDate = drawDate;
		this.remittanceTrasactionId = remittanceTrasactionId;
		this.customerId = customerId;
		this.prizeNo = prizeNo;
		this.validUpto = validUpto;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.modifiedDate = modifiedDate;
		this.modifiedBy = modifiedBy;
		this.signatureSpecimenClob=signatureSpecimenClob;
		this.declarationDate=declarationDate;
	}



	@Id
	@GeneratedValue(generator="ex_promo_detail_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_promo_detail_seq",sequenceName="EX_PROMO_DETAIL_SEQ",allocationSize=1)
	@Column(name = "EX_PROMO_DT_SEQ", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getPromotionDetailsId() {
		return promotionDetailsId;
	}

	public void setPromotionDetailsId(BigDecimal promotionDetailsId) {
		this.promotionDetailsId = promotionDetailsId;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EX_PROMO_HD_SEQ")
	public PromotionMaster getPromotionMaster() {
		return promotionMaster;
	}

	public void setPromotionMaster(PromotionMaster promotionMaster) {
		this.promotionMaster = promotionMaster;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EX_PROMO_PRIZE_SEQ")
	public PromotionPrize getPromotionPrize() {
		return promotionPrize;
	}

	public void setPromotionPrize(PromotionPrize promotionPrize) {
		this.promotionPrize = promotionPrize;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "APPLICATION_COUNTRY_ID")
	public CountryMaster getApplicationCountry() {
		return applicationCountry;
	}

	public void setApplicationCountry(CountryMaster applicationCountry) {
		this.applicationCountry = applicationCountry;
	}
	@Column(name = "DRAW_DATE")
	public Date getDrawDate() {
		return drawDate;
	}

	public void setDrawDate(Date drawDate) {
		this.drawDate = drawDate;
	}
	@Column(name = "REMITTANCE_TRANSACTION_ID")
	public BigDecimal getRemittanceTrasactionId() {
		return remittanceTrasactionId;
	}

	public void setRemittanceTrasactionId(BigDecimal remittanceTrasactionId) {
		this.remittanceTrasactionId = remittanceTrasactionId;
	}
	@Column(name = "CUSTOMER_ID")
	public BigDecimal getCustomerId() {
		return customerId;
	}

	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	@Column(name = "PRIZE_NO")
	public BigDecimal getPrizeNo() {
		return prizeNo;
	}

	public void setPrizeNo(BigDecimal prizeNo) {
		this.prizeNo = prizeNo;
	}
	@Column(name = "VALID_UPTO")
	public Date getValidUpto() {
		return validUpto;
	}

	public void setValidUpto(Date validUpto) {
		this.validUpto = validUpto;
	}


	@Column(name = "CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}



	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}


	@Column(name = "CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}



	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	@Column(name = "MODIFIED_DATE")
	public Date getModifiedDate() {
		return modifiedDate;
	}



	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}


	@Column(name = "MODIFIED_BY")
	public String getModifiedBy() {
		return modifiedBy;
	}



	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}


	@Column(name = "CUSTOMER_SIGNATURE")
	public Clob getSignatureSpecimenClob() {
		return signatureSpecimenClob;
	}



	public void setSignatureSpecimenClob(Clob signatureSpecimenClob) {
		this.signatureSpecimenClob = signatureSpecimenClob;
	}


	@Column(name = "DECLARATION_ON")
	public Date getDeclarationDate() {
		return declarationDate;
	}



	public void setDeclarationDate(Date declarationDate) {
		this.declarationDate = declarationDate;
	}
	
	
	
	
}
