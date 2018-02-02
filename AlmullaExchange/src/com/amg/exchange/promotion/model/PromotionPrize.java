package com.amg.exchange.promotion.model;

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
import com.amg.exchange.treasury.model.Document;

@Entity
@Table(name = "EX_PROMO_PRIZE")
public class PromotionPrize implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BigDecimal promotionPrizeId;
	private PromotionMaster promotionMaster;
	private CountryMaster applicationCountry;
	private Document documentMaster;
	private Date fromDate;
	private Date toDate;
	private BigDecimal prizeNo;
	private BigDecimal prizeAmount;
	
	public PromotionPrize()
	{

	}
	
	public PromotionPrize(BigDecimal promotionPrizeId,
			PromotionMaster promotionMaster, CountryMaster applicationCountry,
			Document documentMaster, Date fromDate, Date toDate,
			BigDecimal prizeNo, BigDecimal prizeAmount) {
		super();
		this.promotionPrizeId = promotionPrizeId;
		this.promotionMaster = promotionMaster;
		this.applicationCountry = applicationCountry;
		this.documentMaster = documentMaster;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.prizeNo = prizeNo;
		this.prizeAmount = prizeAmount;
	}

	@Id
	@GeneratedValue(generator="ex_promo_prize_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_promo_prize_seq",sequenceName="EX_PROMO_PRIZE_SEQ",allocationSize=1)
	@Column(name = "EX_PROMO_PRIZE_SEQ", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getPromotionPrizeId() {
		return promotionPrizeId;
	}

	public void setPromotionPrizeId(BigDecimal promotionPrizeId) {
		this.promotionPrizeId = promotionPrizeId;
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
	@JoinColumn(name = "APPLICATION_COUNTRY_ID")
	public CountryMaster getApplicationCountry() {
		return applicationCountry;
	}

	public void setApplicationCountry(CountryMaster applicationCountry) {
		this.applicationCountry = applicationCountry;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DOCUMENT_ID")
	public Document getDocumentMaster() {
		return documentMaster;
	}

	public void setDocumentMaster(Document documentMaster) {
		this.documentMaster = documentMaster;
	}
	@Column(name = "FROM_DATE")
	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	@Column(name = "TO_DATE")
	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	@Column(name = "PRIZE_NO")
	public BigDecimal getPrizeNo() {
		return prizeNo;
	}

	public void setPrizeNo(BigDecimal prizeNo) {
		this.prizeNo = prizeNo;
	}
	@Column(name = "PRIZE_AMOUNT")
	public BigDecimal getPrizeAmount() {
		return prizeAmount;
	}

	public void setPrizeAmount(BigDecimal prizeAmount) {
		this.prizeAmount = prizeAmount;
	}
	
	
	
}
