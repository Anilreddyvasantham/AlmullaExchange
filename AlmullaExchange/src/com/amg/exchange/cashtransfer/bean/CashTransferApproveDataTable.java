package com.amg.exchange.cashtransfer.bean;

import java.math.BigDecimal;
import java.util.Date;

import com.sun.xml.rpc.processor.modeler.j2ee.xml.string;

public class CashTransferApproveDataTable {

	private BigDecimal cashHeaderPk;
	private BigDecimal fromLocationId;
	private String fromLocation;
	private String fromCashier;
	private BigDecimal transferDocumentCode;
	private BigDecimal transferDocumentYear;
	private BigDecimal transferDocumentNo;
	private Date transferDocumentDate;
	private String createdUserName;
	

	public String getCreatedUserName() {
		return createdUserName;
	}
	public void setCreatedUserName(String createdUserName) {
		this.createdUserName = createdUserName;
	}
	
	public BigDecimal getCashHeaderPk() {
		return cashHeaderPk;
	}
	public void setCashHeaderPk(BigDecimal cashHeaderPk) {
		this.cashHeaderPk = cashHeaderPk;
	}
	
	public String getFromCashier() {
		return fromCashier;
	}
	public void setFromCashier(String fromCashier) {
		this.fromCashier = fromCashier;
	}
	
	public BigDecimal getFromLocationId() {
		return fromLocationId;
	}
	public void setFromLocationId(BigDecimal fromLocationId) {
		this.fromLocationId = fromLocationId;
	}

	public String getFromLocation() {
		return fromLocation;
	}
	public void setFromLocation(String fromLocation) {
		this.fromLocation = fromLocation;
	}

	public BigDecimal getTransferDocumentYear() {
		return transferDocumentYear;
	}
	public void setTransferDocumentYear(BigDecimal transferDocumentYear) {
		this.transferDocumentYear = transferDocumentYear;
	}
	
	public BigDecimal getTransferDocumentNo() {
		return transferDocumentNo;
	}
	public void setTransferDocumentNo(BigDecimal transferDocumentNo) {
		this.transferDocumentNo = transferDocumentNo;
	}
	
	public Date getTransferDocumentDate() {
		return transferDocumentDate;
	}
	public void setTransferDocumentDate(Date transferDocumentDate) {
		this.transferDocumentDate = transferDocumentDate;
	}
	
	public BigDecimal getTransferDocumentCode() {
		return transferDocumentCode;
	}
	public void setTransferDocumentCode(BigDecimal transferDocumentCode) {
		this.transferDocumentCode = transferDocumentCode;
	}
	
}
