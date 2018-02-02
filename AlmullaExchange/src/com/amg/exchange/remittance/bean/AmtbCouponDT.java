package com.amg.exchange.remittance.bean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;



public class AmtbCouponDT {

	private BigDecimal couponNo;
	private String customerName;
	private BigDecimal amtbFinYear;
	private BigDecimal countryBranchId;
	
	private BigDecimal remitDocFyr;
	private BigDecimal remitDocNo;
	private Date documentDate;
	
	private Date fromDate;
	private Date toDate;
	
	private String location;
	private String userName;
	private String srNo;
	
	private String strfromDate;
	private String strToDate;
	private String couponNoStr;
	private String subReport;
	
	private String idNo;
	
	
	
	List<AmtbCouponDT> amtbCouponDTList;
	
	//List<AMTBReportBean> amtbCouponDTList;
	
	
	public BigDecimal getCouponNo() {
		return couponNo;
	}
	public void setCouponNo(BigDecimal couponNo) {
		this.couponNo = couponNo;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public BigDecimal getAmtbFinYear() {
		return amtbFinYear;
	}
	public void setAmtbFinYear(BigDecimal amtbFinYear) {
		this.amtbFinYear = amtbFinYear;
	}
	public BigDecimal getCountryBranchId() {
		return countryBranchId;
	}
	public void setCountryBranchId(BigDecimal countryBranchId) {
		this.countryBranchId = countryBranchId;
	}
	public BigDecimal getRemitDocFyr() {
		return remitDocFyr;
	}
	public void setRemitDocFyr(BigDecimal remitDocFyr) {
		this.remitDocFyr = remitDocFyr;
	}
	public BigDecimal getRemitDocNo() {
		return remitDocNo;
	}
	public void setRemitDocNo(BigDecimal remitDocNo) {
		this.remitDocNo = remitDocNo;
	}
	public Date getDocumentDate() {
		return documentDate;
	}
	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getSrNo() {
		return srNo;
	}
	public void setSrNo(String srNo) {
		this.srNo = srNo;
	}
	public String getStrfromDate() {
		return strfromDate;
	}
	public void setStrfromDate(String strfromDate) {
		this.strfromDate = strfromDate;
	}
	public String getStrToDate() {
		return strToDate;
	}
	public void setStrToDate(String strToDate) {
		this.strToDate = strToDate;
	}
	public String getCouponNoStr() {
		return couponNoStr;
	}
	public void setCouponNoStr(String couponNoStr) {
		this.couponNoStr = couponNoStr;
	}
	public String getSubReport() {
		return subReport;
	}
	public void setSubReport(String subReport) {
		this.subReport = subReport;
	}
	public List<AmtbCouponDT> getAmtbCouponDTList() {
		return amtbCouponDTList;
	}
	public void setAmtbCouponDTList(List<AmtbCouponDT> amtbCouponDTList) {
		this.amtbCouponDTList = amtbCouponDTList;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	
}
