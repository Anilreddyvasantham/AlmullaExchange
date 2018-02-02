package com.amg.exchange.currency.inquiry.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VW_EX_STOCK_BRANCH")
public class BranchWiseCurrencyStock implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private BigDecimal idNo;
	private BigDecimal fcAmount;
	private BigDecimal currentStock;
	private BigDecimal denominationId;
	private BigDecimal currencyId;
	private BigDecimal closerQuantity;
	private String branchName;
	private BigDecimal countryBranchId;
	private BigDecimal denominationAmount;

	public BranchWiseCurrencyStock() {

	}

	/**
	 * @param idNo
	 * @param fcAmount
	 * @param currentStock
	 * @param denominationId
	 * @param currencyId
	 * @param closerQuantity
	 * @param branchName
	 */
	public BranchWiseCurrencyStock(BigDecimal idNo, BigDecimal fcAmount,
			BigDecimal currentStock, BigDecimal denominationId,
			BigDecimal currencyId, BigDecimal closerQuantity,
			String branchName, BigDecimal countryBranchId, BigDecimal denominationAmount) {
		super();
		this.idNo = idNo;
		this.fcAmount = fcAmount;
		this.currentStock = currentStock;
		this.denominationId = denominationId;
		this.currencyId = currencyId;
		this.closerQuantity = closerQuantity;
		this.branchName = branchName;
		this.countryBranchId = countryBranchId;
		this.denominationAmount = denominationAmount;
	}

	@Id
	@Column(name = "IDNO")
	public BigDecimal getIdNo() {
		return idNo;
	}

	@Column(name = "FC_AMOUNT")
	public BigDecimal getFcAmount() {
		return fcAmount;
	}

	@Column(name = "CURRENT_STOCK")
	public BigDecimal getCurrentStock() {
		return currentStock;
	}

	@Column(name = "DENOMINATION_ID")
	public BigDecimal getDenominationId() {
		return denominationId;
	}

	@Column(name = "COUNTRY_BRANCH_ID")
	public BigDecimal getCountryBranchId() {
		return countryBranchId;
	}

	@Column(name = "CURRENCY_ID")
	public BigDecimal getCurrencyId() {
		return currencyId;
	}

	@Column(name = "CLOSE_QTY")
	public BigDecimal getCloserQuantity() {
		return closerQuantity;
	}

	@Column(name = "BRANCH_NAME")
	public String getBranchName() {
		return branchName;
	}
	
	@Column(name = "DENOMINATION_AMOUNT")	
	public BigDecimal getDenominationAmount() {
		return denominationAmount;
	}

	public void setIdNo(BigDecimal idNo) {
		this.idNo = idNo;
	}

	public void setFcAmount(BigDecimal fcAmount) {
		this.fcAmount = fcAmount;
	}

	public void setCurrentStock(BigDecimal currentStock) {
		this.currentStock = currentStock;
	}

	public void setDenominationId(BigDecimal denominationId) {
		this.denominationId = denominationId;
	}

	public void setCountryBranchId(BigDecimal countryBranchId) {
		this.countryBranchId = countryBranchId;
	}

	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	public void setCloserQuantity(BigDecimal closerQuantity) {
		this.closerQuantity = closerQuantity;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	
	public void setDenominationAmount(BigDecimal denominationAmount) {
		this.denominationAmount = denominationAmount;
	}
	

	@Override
	public String toString() {
		return "BranchWiseCurrencyStock [idNo=" + idNo + ", fcAmount=" + fcAmount + ", currentStock=" + currentStock + ", denominationId=" + denominationId + ", currencyId=" + currencyId + ", closerQuantity=" + closerQuantity + ", branchName=" + branchName + ", countryBranchId=" + countryBranchId+ ", denominationAmount=" + denominationAmount
				+ "]";
	}

}
