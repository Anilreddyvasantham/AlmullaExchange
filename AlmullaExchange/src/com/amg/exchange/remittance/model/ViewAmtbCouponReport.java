package com.amg.exchange.remittance.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VW_EX_AMTB_COUPON_REPORT")
public class ViewAmtbCouponReport {

	
	private BigDecimal couponNo;
	private String customerName;
	private BigDecimal amtbFinYear;
	private BigDecimal countryBranchId;
	
	private BigDecimal remitDocFyr;
	private BigDecimal remitDocNo;
	private Date documentDate;
	private String idNo;
	
	
	

	
	

	@Id
	@Column(name = "COUPON_NO")
	public BigDecimal getCouponNo() {
		return couponNo;
	}

	public void setCouponNo(BigDecimal couponNo) {
		this.couponNo = couponNo;
	}

	
	@Column(name="DOCUMENT_FINANCE_YEAR")
	public BigDecimal getAmtbFinYear() {
		return amtbFinYear;
	}

	public void setAmtbFinYear(BigDecimal amtbFinYear) {
		this.amtbFinYear = amtbFinYear;
	}

	@Column(name="COUNTRY_BRANCH_ID")
	public BigDecimal getCountryBranchId() {
		return countryBranchId;
	}

	public void setCountryBranchId(BigDecimal countryBranchId) {
		this.countryBranchId = countryBranchId;
	}

	@Column(name="CUSTOMER_NAME")
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Column(name="REMIT_DOCUMENT_FINANCE_YEAR")
	public BigDecimal getRemitDocFyr() {
		return remitDocFyr;
	}

	public void setRemitDocFyr(BigDecimal remitDocFyr) {
		this.remitDocFyr = remitDocFyr;
	}

	@Column(name="REMIT_DOCUMENT_NO")
	public BigDecimal getRemitDocNo() {
		return remitDocNo;
	}

	public void setRemitDocNo(BigDecimal remitDocNo) {
		this.remitDocNo = remitDocNo;
	}

	@Column(name="DOCUMENT_DATE")
	public Date getDocumentDate() {
		return documentDate;
	}

	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}

	@Column(name="ID_NO")
	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

}
