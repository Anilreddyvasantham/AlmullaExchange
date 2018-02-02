package com.amg.exchange.treasury.viewModel;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="VW_EX_ACCOUNT_BALANCE")
public class ViewAccountBalance implements Serializable {

	 	private static final long serialVersionUID = 1L;
	 	
	 	private BigDecimal bankId;
	 	private BigDecimal currencyId;
		private BigDecimal countryId;
	 	private BigDecimal balanceId;
	 	private String bankName;
	 	private String accountNumber;
	 	private String glslNumber;
	 	private String bankFcBalance;
	 	private BigDecimal eftUnfundAmount;
	 	private BigDecimal ttUnfundAmount;
	 	private BigDecimal cashUnfundAmount;
	 	private BigDecimal eftCurrenctBalance;
	 	private BigDecimal ttCurrentBalance;
	 	private BigDecimal cashCurrentBalance;

		private BigDecimal companyId;
	 	private BigDecimal bankAccDetailId;
	 	private BigDecimal localCurrencyBalance;
	 	private BigDecimal averageRate;

		@Id
		 @Column(name="BALANCE_ID")
		public BigDecimal getBalanceId() {
			return balanceId;
		}
		public void setBalanceId(BigDecimal balanceId) {
			this.balanceId = balanceId;
		}
	 	
	 	 @Column(name="CURRENCY_ID")
		public BigDecimal getCurrencyId() {
			return currencyId;
		}
		public void setCurrencyId(BigDecimal currencyId) {
			this.currencyId = currencyId;
		}
		 @Column(name="COUNTRY_ID")
		public BigDecimal getCountryId() {
			return countryId;
		}
		public void setCountryId(BigDecimal countryId) {
			this.countryId = countryId;
		}
		
	 	
	 	@Column(name="BANK_ID")
	 	public BigDecimal getBankId() {
			return bankId;
		}
		public void setBankId(BigDecimal bankId) {
			this.bankId = bankId;
		}
		@Column(name="BANK_NAME")
		public String getBankName() {
			return bankName;
		}
		public void setBankName(String bankName) {
			this.bankName = bankName;
		}
		@Column(name="ACCOUNT_NUMBER")
		public String getAccountNumber() {
			return accountNumber;
		}
		public void setAccountNumber(String accountNumber) {
			this.accountNumber = accountNumber;
		}
		@Column(name="GL_SL_NUMBER")
		public String getGlslNumber() {
			return glslNumber;
		}
		public void setGlslNumber(String glslNumber) {
			this.glslNumber = glslNumber;
		}
		@Column(name="BANK_FC_BALANCE")
		public String getBankFcBalance() {
			return bankFcBalance;
		}
		public void setBankFcBalance(String bankFcBalance) {
			this.bankFcBalance = bankFcBalance;
		}
		@Column(name="EFT_UNFUND_AMOUNT")
		public BigDecimal getEftUnfundAmount() {
			return eftUnfundAmount;
		}
		public void setEftUnfundAmount(BigDecimal eftUnfundAmount) {
			this.eftUnfundAmount = eftUnfundAmount;
		}
		@Column(name="TT_UNFUND_AMOUNT")
		public BigDecimal getTtUnfundAmount() {
			return ttUnfundAmount;
		}
		public void setTtUnfundAmount(BigDecimal ttUnfundAmount) {
			this.ttUnfundAmount = ttUnfundAmount;
		}
		@Column(name="CASH_UNFUND_AMOUNT")
		public BigDecimal getCashUnfundAmount() {
			return cashUnfundAmount;
		}
		public void setCashUnfundAmount(BigDecimal cashUnfundAmount) {
			this.cashUnfundAmount = cashUnfundAmount;
		}
		@Column(name="EFT_CURRENT_BAL")
		public BigDecimal getEftCurrenctBalance() {
			return eftCurrenctBalance;
		}
		public void setEftCurrenctBalance(BigDecimal eftCurrenctBalance) {
			this.eftCurrenctBalance = eftCurrenctBalance;
		}
		@Column(name="TT_CURRENT_BAL")
		public BigDecimal getTtCurrentBalance() {
			return ttCurrentBalance;
		}
		public void setTtCurrentBalance(BigDecimal ttCurrentBalance) {
			this.ttCurrentBalance = ttCurrentBalance;
		}
		@Column(name="CASH_CURRENT_BAL")
		public BigDecimal getCashCurrentBalance() {
			return cashCurrentBalance;
		}
		public void setCashCurrentBalance(BigDecimal cashCurrentBalance) {
			this.cashCurrentBalance = cashCurrentBalance;
		}
		@Column(name="COMPANY_ID")
	 	public BigDecimal getCompanyId() {
			return companyId;
		}
		public void setCompanyId(BigDecimal companyId) {
			this.companyId = companyId;
		}
		@Column(name="BANK_ACCT_DET_ID")
		public BigDecimal getBankAccDetailId() {
			return bankAccDetailId;
		}
		public void setBankAccDetailId(BigDecimal bankAccDetailId) {
			this.bankAccDetailId = bankAccDetailId;
		}
		@Column(name="LOCAL_CURRENCY_BALANCE")
		public BigDecimal getLocalCurrencyBalance() {
			return localCurrencyBalance;
		}
		public void setLocalCurrencyBalance(BigDecimal localCurrencyBalance) {
			this.localCurrencyBalance = localCurrencyBalance;
		}
		@Column(name="AVERAGE_RATE")
		public BigDecimal getAverageRate() {
			return averageRate;
		}
		public void setAverageRate(BigDecimal averageRate) {
			this.averageRate = averageRate;
		}
	 	
	 	
	 	
	 	
	

}
