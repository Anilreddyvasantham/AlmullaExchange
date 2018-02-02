package com.amg.exchange.remittance.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class KIOSKDenominationDataTable implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int serial=0;
	private BigDecimal item=null;
	private String denominationDesc=null;
	private BigDecimal denominationNo=null;
	private BigDecimal stock=null;
	private BigDecimal noOfNotes=null;
	private BigDecimal cashAmount=null;
    private BigDecimal plustminus=null;
    private BigDecimal denominationId=null;
    
    
    
    
    
	public BigDecimal getDenominationId() {
		return denominationId;
	}
	public void setDenominationId(BigDecimal denominationId) {
		this.denominationId = denominationId;
	}
	public int getSerial() {
		return serial;
	}
	public void setSerial(int serial) {
		this.serial = serial;
	}
	public BigDecimal getItem() {
		return item;
	}
	public void setItem(BigDecimal item) {
		this.item = item;
	}
	public String getDenominationDesc() {
		return denominationDesc;
	}
	public void setDenominationDesc(String denominationDesc) {
		this.denominationDesc = denominationDesc;
	}
	public BigDecimal getDenominationNo() {
		return denominationNo;
	}
	public void setDenominationNo(BigDecimal denominationNo) {
		this.denominationNo = denominationNo;
	}
	public BigDecimal getStock() {
		return stock;
	}
	public void setStock(BigDecimal stock) {
		this.stock = stock;
	}
	public BigDecimal getNoOfNotes() {
		return noOfNotes;
	}
	public void setNoOfNotes(BigDecimal noOfNotes) {
		this.noOfNotes = noOfNotes;
	}
	public BigDecimal getCashAmount() {
		return cashAmount;
	}
	public void setCashAmount(BigDecimal cashAmount) {
		this.cashAmount = cashAmount;
	}
	public BigDecimal getPlustminus() {
		return plustminus;
	}
	public void setPlustminus(BigDecimal plustminus) {
		this.plustminus = plustminus;
	}

}
