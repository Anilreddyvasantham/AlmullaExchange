package com.amg.exchange.foreigncurrency.model;

import java.io.Serializable;
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
import javax.persistence.TableGenerator;

import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.CurrencyMaster;

@Entity
@Table(name = "EX_STOCK")
public class Stock  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3270158197901319250L;
	private BigDecimal stockId;
	//private BankBranch bankBranchId; // need to check with table
	private CountryBranch countryBranch;
	private CurrencyMaster currencyId;
	private CurrencyWiseDenomination denominationId;
	private String oracleUser;
	private Date logDate;
	private Date accountmmyyyy;
	private int openQuantity;
	private int purchaseQuantity;
	private int saleQuantity;
	private int transactionQuantity;
	private int receivedQuantity;
	private int closerQuantity;
	private String createdBy;
	private Date creationDate;
	private String modifiedBy;
	private Date modifiedDate;

	
	
	public Stock(){
	}
	public Stock( BigDecimal stockId, //BankBranch bankBranchId, 
						CurrencyMaster currencyId,CurrencyWiseDenomination denominationId,
						String oracleUser,Date logDate,Date accountmmyyyy, 
						int purchaseQuantity, int saleQuantity, int transactionQuantity,
						int receivedQuantity,	 int closerQuantity,String createdBy, Date creationDate, String modifiedBy,
						Date modifiedDate){
		this.stockId = stockId;
		//this.bankBranchId = bankBranchId;
		this.currencyId =currencyId;
		this.denominationId = denominationId;
		this.oracleUser = oracleUser;
		this.logDate= logDate;
		this.accountmmyyyy = accountmmyyyy;
		this.purchaseQuantity= purchaseQuantity;
		this.saleQuantity= saleQuantity;
		this.transactionQuantity =-transactionQuantity;
		this.receivedQuantity= receivedQuantity;
		this.closerQuantity =closerQuantity;
		this.createdBy = createdBy;
		this.creationDate = creationDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		
	}
	
	@Id
	@GeneratedValue(generator="ex_stock_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_stock_seq",sequenceName="EX_STOCK_SEQ",allocationSize=1)
	@Column(name = "STOCK_ID", unique = true, nullable = false, precision = 22, scale = 0)

	public BigDecimal getStockId() {
		return stockId;
	}
	public void setStockId(BigDecimal stockId) {
		this.stockId = stockId;
	}
	/*@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_BRANCH_ID")
	public BankBranch getBankBranchId() {
		return bankBranchId;
	}
	public void setBankBranchId(BankBranch bankBranchId) {
		this.bankBranchId = bankBranchId;
	}*/
	@Column(name = "ORACLE_USER")
	public String getOracleUser() {
		return oracleUser;
	}
	public void setOracleUser(String oracleUser) {
		this.oracleUser = oracleUser;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CURRENCY_ID")
	public CurrencyMaster getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(CurrencyMaster currencyId) {
		this.currencyId = currencyId;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DENOMINATION_ID")
	public CurrencyWiseDenomination getDenominationId() {
		return denominationId;
	}
	public void setDenominationId(CurrencyWiseDenomination denominationId) {
		this.denominationId = denominationId;
	}
	@Column(name = "LOG_DATE")
	public Date getLogDate() {
		return logDate;
	}
	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}
	@Column(name = "ACCOUNT_MMYYYY")
	public Date getAccountmmyyyy() {
		return accountmmyyyy;
	}
	public void setAccountmmyyyy(Date accountmmyyyy) {
		this.accountmmyyyy = accountmmyyyy;
	}
	@Column(name = "OPEN_QTY")
	public int getOpenQuantity() {
		return openQuantity;
	}
	public void setOpenQuantity(int openQuantity) {
		this.openQuantity = openQuantity;
	}
	@Column(name = "PURCHASE_QTY")
	public int getPurchaseQuantity() {
		return purchaseQuantity;
	}
	public void setPurchaseQuantity(int purchaseQuantity) {
		this.purchaseQuantity = purchaseQuantity;
	}
	@Column(name = "SALE_QTY")
	public int getSaleQuantity() {
		return saleQuantity;
	}
	public void setSaleQuantity(int saleQuantity) {
		this.saleQuantity = saleQuantity;
	}
	@Column(name = "TRANF_QTY")
	public int getTransactionQuantity() {
		return transactionQuantity;
	}
	public void setTransactionQuantity(int transactionQuantity) {
		this.transactionQuantity = transactionQuantity;
	}
	@Column(name = "RECEIVED_QTY")
	public int getReceivedQuantity() {
		return receivedQuantity;
	}
	public void setReceivedQuantity(int receivedQuantity) {
		this.receivedQuantity = receivedQuantity;
	}
	@Column(name = "CLOSE_QTY")
	public int getCloserQuantity() {
		return closerQuantity;
	}
	public void setCloserQuantity(int closerQuantity) {
		this.closerQuantity = closerQuantity;
	}
	@Column(name = "CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "CREATED_DATE")
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "MODIFIED_BY")
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "MODIFIED_DATE")
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_BRANCH_ID")
	public CountryBranch getCountryBranch() {
		return countryBranch;
	}
	public void setCountryBranch(CountryBranch countryBranch) {
		this.countryBranch = countryBranch;
	}
		

}
