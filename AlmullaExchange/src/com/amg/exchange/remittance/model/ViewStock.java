package com.amg.exchange.remittance.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "V_EX_STOCK")
public class ViewStock implements Serializable {

	  /**
	   * 
	   */
	  private static final long serialVersionUID = 1L;

	  private BigDecimal stockId;
	  private BigDecimal countryId;
	  private BigDecimal countryBranchId;
	  private String oracleUser;
	  private Date logDate;
	  private BigDecimal currencyId;
	  private BigDecimal denominationId;
	  private BigDecimal currentStock;
	  private BigDecimal denominationAmount;
	  private String denominationDEsc;
	  
	  

	  @Id
	  @Column(name = "STOCK_ID")
	  public BigDecimal getStockId() {
		    return stockId;
	  }

	  public void setStockId(BigDecimal stockId) {
		    this.stockId = stockId;
	  }

	  @Column(name = "COUNTRY_ID")
	  public BigDecimal getCountryId() {
		    return countryId;
	  }

	  public void setCountryId(BigDecimal countryId) {
		    this.countryId = countryId;
	  }

	  @Column(name = "COUNTRY_BRANCH_ID")
	  public BigDecimal getCountryBranchId() {
		    return countryBranchId;
	  }

	  public void setCountryBranchId(BigDecimal countryBranchId) {
		    this.countryBranchId = countryBranchId;
	  }

	  @Column(name = "ORACLE_USER")
	  public String getOracleUser() {
		    return oracleUser;
	  }

	  public void setOracleUser(String oracleUser) {
		    this.oracleUser = oracleUser;
	  }

	  @Column(name = "LOG_DATE")
	  public Date getLogDate() {
		    return logDate;
	  }

	  public void setLogDate(Date logDate) {
		    this.logDate = logDate;
	  }

	  @Column(name = "CURRENCY_ID")
	  public BigDecimal getCurrencyId() {
		    return currencyId;
	  }

	  public void setCurrencyId(BigDecimal currencyId) {
		    this.currencyId = currencyId;
	  }

	  @Column(name = "DENOMINATION_ID")
	  public BigDecimal getDenominationId() {
		    return denominationId;
	  }

	  public void setDenominationId(BigDecimal denominationId) {
		    this.denominationId = denominationId;
	  }

	  @Column(name = "CURRENT_STOCK")
	  public BigDecimal getCurrentStock() {
		    return currentStock;
	  }

	  public void setCurrentStock(BigDecimal currentStock) {
		    this.currentStock = currentStock;
	  }

	  @Column(name = "DENOMINATION_AMOUNT")
	public BigDecimal getDenominationAmount() {
		return denominationAmount;
	}

	public void setDenominationAmount(BigDecimal denominationAmount) {
		this.denominationAmount = denominationAmount;
	}

	@Column(name = "DENOMINATION_DESC")
	public String getDenominationDEsc() {
		return denominationDEsc;
	}

	public void setDenominationDEsc(String denominationDEsc) {
		this.denominationDEsc = denominationDEsc;
	}
	  
	  

}
