package com.amg.exchange.promotion.bean;

import java.math.BigDecimal;
import java.util.Date;

public class PromotionMasterDataTable {
	
	private Date weekFrDt;
	private Date weekToDt;
	private BigDecimal prizesNo;
	private BigDecimal amount;
	private BigDecimal promoPrizeSeq;
	private BigDecimal promoMasterSeq;
	
	
	//Getters and setters.
	
	public Date getWeekFrDt() {
		return weekFrDt;
	}
	public void setWeekFrDt(Date weekFrDt) {
		this.weekFrDt = weekFrDt;
	}
	public Date getWeekToDt() {
		return weekToDt;
	}
	public void setWeekToDt(Date weekToDt) {
		this.weekToDt = weekToDt;
	}
	public BigDecimal getPrizesNo() {
		return prizesNo;
	}
	public void setPrizesNo(BigDecimal prizesNo) {
		this.prizesNo = prizesNo;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getPromoPrizeSeq() {
		return promoPrizeSeq;
	}
	public void setPromoPrizeSeq(BigDecimal promoPrizeSeq) {
		this.promoPrizeSeq = promoPrizeSeq;
	}
	public BigDecimal getPromoMasterSeq() {
		return promoMasterSeq;
	}
	public void setPromoMasterSeq(BigDecimal promoMasterSeq) {
		this.promoMasterSeq = promoMasterSeq;
	}
		

}
