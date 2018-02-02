package com.amg.exchange.remittance.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VW_AMTB_COUPON")
public class ViewAmtbCoupon {

	private String idNo;
	private BigDecimal couponNo;
	private String couponDesc;
	private Date validity;
	private BigDecimal couponAmount;
	private BigDecimal amtbFinYear;

	
	@Column(name = "ID_NO")
	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	@Id
	@Column(name = "COUPON_NO")
	public BigDecimal getCouponNo() {
		return couponNo;
	}

	public void setCouponNo(BigDecimal couponNo) {
		this.couponNo = couponNo;
	}

	@Column(name = "VALIDITY")
	public Date getValidity() {
		return validity;
	}

	public void setValidity(Date validity) {
		this.validity = validity;
	}

	@Column(name = "COUPON_AMOUNT")
	public BigDecimal getCouponAmount() {
		return couponAmount;
	}

	public void setCouponAmount(BigDecimal couponAmount) {
		this.couponAmount = couponAmount;
	}

	@Column(name="COUPON_DESC")
	public String getCouponDesc() {
		return couponDesc;
	}

	public void setCouponDesc(String couponDesc) {
		this.couponDesc = couponDesc;
	}

	@Column(name="DOC_FYR")
	public BigDecimal getAmtbFinYear() {
		return amtbFinYear;
	}

	public void setAmtbFinYear(BigDecimal amtbFinYear) {
		this.amtbFinYear = amtbFinYear;
	}

}
