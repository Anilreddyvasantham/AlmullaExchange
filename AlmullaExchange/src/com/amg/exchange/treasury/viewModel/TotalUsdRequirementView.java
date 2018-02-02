package com.amg.exchange.treasury.viewModel;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/*
 * This Model is created for  Treasury – Sales Projections CR Point -9 
 */



@Entity
@Table(name="V_TOT_INTRREQ")
public class TotalUsdRequirementView {
	
	
	TotalUsdRequirementView(){}
	
	@Id
	@Column(name="TOTUSDID")
	private BigDecimal totalUsdRequirementId;
	
	@Column(name="CURRENT_BAL")
	private BigDecimal currentBalance;
	
	@Column(name="SALE_PROJ")
	private String sale_Projection;
	
	@Column(name="CURRENCY_REQUIRED")
	private String currencyRequirement;
	
	@Column(name="TOTAL")
	private String total;
	
	@Column(name="APPLICATION_COUNTRY_ID")
	private BigDecimal applicationCountryId;

	
	
	
	public TotalUsdRequirementView(BigDecimal totalUsdRequirementId , BigDecimal currentBalance,
			String sale_Projection, String currencyRequirement,
			String total, BigDecimal applicationCountryId) {
		this.totalUsdRequirementId = totalUsdRequirementId;
		this.currentBalance = currentBalance;
		this.sale_Projection = sale_Projection;
		this.currencyRequirement = currencyRequirement;
		this.total = total;
		this.applicationCountryId = applicationCountryId;

	}
	
	public BigDecimal getTotalUsdRequirementId() {
		return totalUsdRequirementId;
	}

	public void setTotalUsdRequirementId(BigDecimal totalUsdRequirementId) {
		this.totalUsdRequirementId = totalUsdRequirementId;
	}

	public BigDecimal getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(BigDecimal currentBalance) {
		this.currentBalance = currentBalance;
	}

	public String getSale_Projection() {
		return sale_Projection;
	}

	public void setSale_Projection(String sale_Projection) {
		this.sale_Projection = sale_Projection;
	}

	public String getCurrencyRequirement() {
		return currencyRequirement;
	}

	public void setCurrencyRequirement(String currencyRequirement) {
		this.currencyRequirement = currencyRequirement;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}

	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}
	
	
	// added 
	
	
	

}
