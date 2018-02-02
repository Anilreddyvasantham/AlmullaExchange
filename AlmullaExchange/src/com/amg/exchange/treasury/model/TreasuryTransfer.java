package com.amg.exchange.treasury.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "EX_TREASURY_TRANSFER")
public class TreasuryTransfer implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BigDecimal treasurytransferId;
	private BigDecimal applicationCountryId;
	private BigDecimal bankCountryId;
	private BigDecimal bankId;
	private BigDecimal currencyId;
	private BigDecimal bankAccDetId;
	private String faAccountNumber;
	private BigDecimal fromSeviceMasterId;
	private BigDecimal toSeviceMasterId;
	private BigDecimal foriegnTrnxAmount;
	private String createdBy;
	private Date createdDate;
	

	public TreasuryTransfer() {
		super();
	}
	
	public TreasuryTransfer(BigDecimal treasurytransferId,
			BigDecimal applicationCountryId, BigDecimal bankCountryId,
			BigDecimal bankId, BigDecimal currencyId, BigDecimal bankAccDetId,
			String faAccountNumber, BigDecimal fromSeviceMasterId,
			BigDecimal toSeviceMasterId, BigDecimal foriegnTrnxAmount,
			String createdBy, Date createdDate) {
		super();
		this.treasurytransferId = treasurytransferId;
		this.applicationCountryId = applicationCountryId;
		this.bankCountryId = bankCountryId;
		this.bankId = bankId;
		this.currencyId = currencyId;
		this.bankAccDetId = bankAccDetId;
		this.faAccountNumber = faAccountNumber;
		this.fromSeviceMasterId = fromSeviceMasterId;
		this.toSeviceMasterId = toSeviceMasterId;
		this.foriegnTrnxAmount = foriegnTrnxAmount;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
	}


	@Id
	@GeneratedValue(generator="ex_treasury_transfer_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_treasury_transfer_seq",sequenceName="EX_TREASURY_TRASFER_SEQ",allocationSize=1)
	@Column(name = "TREASURY_TRASFER_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getTreasurytransferId() {
		return treasurytransferId;
	}
	public void setTreasurytransferId(BigDecimal treasurytransferId) {
		this.treasurytransferId = treasurytransferId;
	}
	
	@Column(name = "APPLICATION_COUNTRY_ID")
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}
	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}
	
	@Column(name = "BANK_COUNTRY_ID")
	public BigDecimal getBankCountryId() {
		return bankCountryId;
	}
	public void setBankCountryId(BigDecimal bankCountryId) {
		this.bankCountryId = bankCountryId;
	}
	
	@Column(name = "BANK_ID")
	public BigDecimal getBankId() {
		return bankId;
	}
	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}
	
	@Column(name = "CURRENCY_ID")
	public BigDecimal getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}
	
	@Column(name = "BANK_ACCT_DET_ID")
	public BigDecimal getBankAccDetId() {
		return bankAccDetId;
	}
	public void setBankAccDetId(BigDecimal bankAccDetId) {
		this.bankAccDetId = bankAccDetId;
	}
	
	@Column(name = "FA_ACCOUNT_NUMBER")
	public String getFaAccountNumber() {
		return faAccountNumber;
	}
	public void setFaAccountNumber(String faAccountNumber) {
		this.faAccountNumber = faAccountNumber;
	}
	
	@Column(name = "FROM_SERVICE_MASTER_ID")
	public BigDecimal getFromSeviceMasterId() {
		return fromSeviceMasterId;
	}
	public void setFromSeviceMasterId(BigDecimal fromSeviceMasterId) {
		this.fromSeviceMasterId = fromSeviceMasterId;
	}
	
	@Column(name = "TO_SERVICE_MASTER_ID")
	public BigDecimal getToSeviceMasterId() {
		return toSeviceMasterId;
	}
	public void setToSeviceMasterId(BigDecimal toSeviceMasterId) {
		this.toSeviceMasterId = toSeviceMasterId;
	}
	
	@Column(name = "FOREIGN_TRANX_AMOUNT")
	public BigDecimal getForiegnTrnxAmount() {
		return foriegnTrnxAmount;
	}
	public void setForiegnTrnxAmount(BigDecimal foriegnTrnxAmount) {
		this.foriegnTrnxAmount = foriegnTrnxAmount;
	}
	
	@Column(name = "CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	@Column(name = "CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	

}
