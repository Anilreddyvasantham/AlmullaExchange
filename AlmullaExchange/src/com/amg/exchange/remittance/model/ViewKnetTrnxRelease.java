package com.amg.exchange.remittance.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



@Entity
@Table(name = "VW_KNET_RELAESE")
public class ViewKnetTrnxRelease implements Serializable {

	 private static final long serialVersionUID = 1L;

	  @Id
	  @Column(name="CUSREF") 
	  private BigDecimal customerRefernce;
	 
	  @Column(name="CUSNAM")
	  private String customerName;
	  
	  @Column(name="TRANSACTION_DATE")
	  private String transactionDate;
	  
	
	  @Column(name="APPROVAL_NO")
	  private BigDecimal approvalNo;
	  
	  @Column(name="TRANSACTION_AMT")
	  private BigDecimal trnxAmount;
	  
	  @Id
	  @Column(name="CARD_NUMBER")
	  private String cardNumber;
	  
	  @Column(name="COLLECTION_DOCUMENT_CODE")
	  private BigDecimal collDocCode;
	  
	  @Column(name="COLLECTION_DOCUMENT_NO")
	  private BigDecimal collDocNo;
	  
	  @Column(name="COLLECTION_DOCUMENT_YEAR")
	  private BigDecimal collDocfyr;
	  
	  
	  
	  

	public BigDecimal getCustomerRefernce() {
		return customerRefernce;
	}

	public void setCustomerRefernce(BigDecimal customerRefernce) {
		this.customerRefernce = customerRefernce;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	

	public BigDecimal getApprovalNo() {
		return approvalNo;
	}

	public void setApprovalNo(BigDecimal approvalNo) {
		this.approvalNo = approvalNo;
	}

	public BigDecimal getTrnxAmount() {
		return trnxAmount;
	}

	public void setTrnxAmount(BigDecimal trnxAmount) {
		this.trnxAmount = trnxAmount;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public BigDecimal getCollDocCode() {
		return collDocCode;
	}

	public void setCollDocCode(BigDecimal collDocCode) {
		this.collDocCode = collDocCode;
	}

	public BigDecimal getCollDocNo() {
		return collDocNo;
	}

	public void setCollDocNo(BigDecimal collDocNo) {
		this.collDocNo = collDocNo;
	}

	public BigDecimal getCollDocfyr() {
		return collDocfyr;
	}

	public void setCollDocfyr(BigDecimal collDocfyr) {
		this.collDocfyr = collDocfyr;
	}
	  
	
}
