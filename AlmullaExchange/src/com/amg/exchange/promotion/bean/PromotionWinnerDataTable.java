package com.amg.exchange.promotion.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class PromotionWinnerDataTable implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String fromDate;
	private List<String>  lstFromDate;
	private String toDate;
	private List<String>  lstToDate;
	private String civilId;
	private String customerName;
	private String custMobileNo;
	private BigDecimal prizeNo;
	private List<BigDecimal> lstPrizeNo;
	private BigDecimal prizeAmount;
	private BigDecimal transactionYear;
	private BigDecimal transactionNo;
	private String validUpTo;
	List<BigDecimal> lstPrizeAmount;
	private BigDecimal promotionPrizeId;
	private BigDecimal promotionMasterId;
	private BigDecimal promotionDetailsId;
	private BigDecimal tempPrizeAmount;
	private String tempToDate;
	private Map<BigDecimal ,BigDecimal> mapPrizeAmount;
	private Map<String ,String> mapDateMatch;
	private Map<BigDecimal ,BigDecimal> mapPrizeIdPrizeNo;
	private BigDecimal customerId;
	private BigDecimal lineIndex;
	private BigDecimal remittancTransactionId;
	private Date createdDate;
	private String createdBy;
	private Date modifiedDate;
	private String modifiedBy;
	private String drawDate;
	private Date declarationDate;
	private BigDecimal promoUsedAmt;
	
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public List<String> getLstFromDate() {
		return lstFromDate;
	}
	public void setLstFromDate(List<String> lstFromDate) {
		this.lstFromDate = lstFromDate;
	}
	public List<String> getLstToDate() {
		return lstToDate;
	}
	public void setLstToDate(List<String> lstToDate) {
		this.lstToDate = lstToDate;
	}
	public String getCivilId() {
		return civilId;
	}
	public void setCivilId(String civilId) {
		this.civilId = civilId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustMobileNo() {
		return custMobileNo;
	}
	public void setCustMobileNo(String custMobileNo) {
		this.custMobileNo = custMobileNo;
	}
	public BigDecimal getPrizeNo() {
		return prizeNo;
	}
	public void setPrizeNo(BigDecimal prizeNo) {
		this.prizeNo = prizeNo;
	}
	public BigDecimal getPrizeAmount() {
		return prizeAmount;
	}
	public void setPrizeAmount(BigDecimal prizeAmount) {
		this.prizeAmount = prizeAmount;
	}
	public BigDecimal getTransactionYear() {
		return transactionYear;
	}
	public void setTransactionYear(BigDecimal transactionYear) {
		this.transactionYear = transactionYear;
	}
	public BigDecimal getTransactionNo() {
		return transactionNo;
	}
	public void setTransactionNo(BigDecimal transactionNo) {
		this.transactionNo = transactionNo;
	}
	public String getValidUpTo() {
		return validUpTo;
	}
	public void setValidUpTo(String validUpTo) {
		this.validUpTo = validUpTo;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public List<BigDecimal> getLstPrizeNo() {
		return lstPrizeNo;
	}
	public void setLstPrizeNo(List<BigDecimal> lstPrizeNo) {
		this.lstPrizeNo = lstPrizeNo;
	}
	public List<BigDecimal> getLstPrizeAmount() {
		return lstPrizeAmount;
	}
	public void setLstPrizeAmount(List<BigDecimal> lstPrizeAmount) {
		this.lstPrizeAmount = lstPrizeAmount;
	}
	public BigDecimal getPromotionPrizeId() {
		return promotionPrizeId;
	}
	public void setPromotionPrizeId(BigDecimal promotionPrizeId) {
		this.promotionPrizeId = promotionPrizeId;
	}
	public BigDecimal getPromotionMasterId() {
		return promotionMasterId;
	}
	public void setPromotionMasterId(BigDecimal promotionMasterId) {
		this.promotionMasterId = promotionMasterId;
	}
	public BigDecimal getTempPrizeAmount() {
		return tempPrizeAmount;
	}
	public void setTempPrizeAmount(BigDecimal tempPrizeAmount) {
		this.tempPrizeAmount = tempPrizeAmount;
	}
	public String getTempToDate() {
		return tempToDate;
	}
	public void setTempToDate(String tempToDate) {
		this.tempToDate = tempToDate;
	}
	public Map<BigDecimal, BigDecimal> getMapPrizeAmount() {
		return mapPrizeAmount;
	}
	public void setMapPrizeAmount(Map<BigDecimal, BigDecimal> mapPrizeAmount) {
		this.mapPrizeAmount = mapPrizeAmount;
	}
	public Map<String, String> getMapDateMatch() {
		return mapDateMatch;
	}
	public void setMapDateMatch(Map<String, String> mapDateMatch) {
		this.mapDateMatch = mapDateMatch;
	}
	public Map<BigDecimal, BigDecimal> getMapPrizeIdPrizeNo() {
		return mapPrizeIdPrizeNo;
	}
	public void setMapPrizeIdPrizeNo(Map<BigDecimal, BigDecimal> mapPrizeIdPrizeNo) {
		this.mapPrizeIdPrizeNo = mapPrizeIdPrizeNo;
	}
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	public BigDecimal getLineIndex() {
		return lineIndex;
	}
	public void setLineIndex(BigDecimal lineIndex) {
		this.lineIndex = lineIndex;
	}
	public BigDecimal getPromotionDetailsId() {
		return promotionDetailsId;
	}
	public void setPromotionDetailsId(BigDecimal promotionDetailsId) {
		this.promotionDetailsId = promotionDetailsId;
	}
	public BigDecimal getRemittancTransactionId() {
		return remittancTransactionId;
	}
	public void setRemittancTransactionId(BigDecimal remittancTransactionId) {
		this.remittancTransactionId = remittancTransactionId;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public String getDrawDate() {
		return drawDate;
	}
	public void setDrawDate(String drawDate) {
		this.drawDate = drawDate;
	}
	public Date getDeclarationDate() {
		return declarationDate;
	}
	public void setDeclarationDate(Date declarationDate) {
		this.declarationDate = declarationDate;
	}
	public BigDecimal getPromoUsedAmt() {
		return promoUsedAmt;
	}
	public void setPromoUsedAmt(BigDecimal promoUsedAmt) {
		this.promoUsedAmt = promoUsedAmt;
	}
	
	
	
}
