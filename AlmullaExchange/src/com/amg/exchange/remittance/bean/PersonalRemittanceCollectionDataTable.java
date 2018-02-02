package com.amg.exchange.remittance.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PersonalRemittanceCollectionDataTable implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String colpaymentmodetypeDT;
	private BigDecimal colpaymentmodeIDtypeDT;
	private String colbankNameDT;
	private BigDecimal colBankIdDT;
	private String colBankCodeDT;
	private BigDecimal colCardNumberDT;
	private String colNameofCardDT;
	private String colAuthorizedByDT;
	private BigDecimal colAmountDT;
	private String colApprovalNo;
	private String colpaymentmodeCode;
	
	//for cheque panel
	private Date colchequeDate;
	private String colchequeRefNo;
	private Boolean booDisbale;
	//for Knet panel
	private String knetReceiptDT;
	private String knetTransIdDT;
	private String knetReceiptDateDT;
	private String kneRceiptTimeDT;
	
	private Boolean editDisable;
	private String maskCardNumberDT;
	private String colCardNum;

	public PersonalRemittanceCollectionDataTable() {
		super();
	}

	public PersonalRemittanceCollectionDataTable(String colpaymentmodetypeDT,
			BigDecimal colpaymentmodeIDtypeDT, String colbankNameDT,
			BigDecimal colBankIdDT, String colBankCodeDT,
			BigDecimal colCardNumberDT, String colNameofCardDT,
			String colAuthorizedByDT, BigDecimal colAmountDT,
			String colApprovalNo, String colpaymentmodeCode,
			Date colchequeDate, String colchequeRefNo, Boolean booDisbale,String maskCardNumberDT,String colCardNum) {
		super();
		this.colpaymentmodetypeDT = colpaymentmodetypeDT;
		this.colpaymentmodeIDtypeDT = colpaymentmodeIDtypeDT;
		this.colbankNameDT = colbankNameDT;
		this.colBankIdDT = colBankIdDT;
		this.colBankCodeDT = colBankCodeDT;
		this.colCardNumberDT = colCardNumberDT;
		this.colNameofCardDT = colNameofCardDT;
		this.colAuthorizedByDT = colAuthorizedByDT;
		this.colAmountDT = colAmountDT;
		this.colApprovalNo = colApprovalNo;
		this.colpaymentmodeCode = colpaymentmodeCode;
		this.colchequeDate = colchequeDate;
		this.colchequeRefNo = colchequeRefNo;
		this.booDisbale =booDisbale;
		this.maskCardNumberDT=maskCardNumberDT;
		this.colCardNum=colCardNum;
	}

	public String getColpaymentmodetypeDT() {
		return colpaymentmodetypeDT;
	}
	public void setColpaymentmodetypeDT(String colpaymentmodetypeDT) {
		this.colpaymentmodetypeDT = colpaymentmodetypeDT;
	}

	public BigDecimal getColpaymentmodeIDtypeDT() {
		return colpaymentmodeIDtypeDT;
	}
	public void setColpaymentmodeIDtypeDT(BigDecimal colpaymentmodeIDtypeDT) {
		this.colpaymentmodeIDtypeDT = colpaymentmodeIDtypeDT;
	}

	public String getColbankNameDT() {
		return colbankNameDT;
	}
	public void setColbankNameDT(String colbankNameDT) {
		this.colbankNameDT = colbankNameDT;
	}

	public BigDecimal getColBankIdDT() {
		return colBankIdDT;
	}
	public void setColBankIdDT(BigDecimal colBankIdDT) {
		this.colBankIdDT = colBankIdDT;
	}

	public BigDecimal getColCardNumberDT() {
		return colCardNumberDT;
	}
	public void setColCardNumberDT(BigDecimal colCardNumberDT) {
		this.colCardNumberDT = colCardNumberDT;
	}

	public String getColNameofCardDT() {
		return colNameofCardDT;
	}
	public void setColNameofCardDT(String colNameofCardDT) {
		this.colNameofCardDT = colNameofCardDT;
	}

	public String getColAuthorizedByDT() {
		return colAuthorizedByDT;
	}
	public void setColAuthorizedByDT(String colAuthorizedByDT) {
		this.colAuthorizedByDT = colAuthorizedByDT;
	}

	public BigDecimal getColAmountDT() {
		return colAmountDT;
	}
	public void setColAmountDT(BigDecimal colAmountDT) {
		this.colAmountDT = colAmountDT;
	}

	public String getColApprovalNo() {
		return colApprovalNo;
	}
	public void setColApprovalNo(String colApprovalNo) {
		this.colApprovalNo = colApprovalNo;
	}

	public String getColBankCodeDT() {
		return colBankCodeDT;
	}
	public void setColBankCodeDT(String colBankCodeDT) {
		this.colBankCodeDT = colBankCodeDT;
	}

	public String getColpaymentmodeCode() {
		return colpaymentmodeCode;
	}
	public void setColpaymentmodeCode(String colpaymentmodeCode) {
		this.colpaymentmodeCode = colpaymentmodeCode;
	}

	public Date getColchequeDate() {
		return colchequeDate;
	}
	public void setColchequeDate(Date colchequeDate) {
		this.colchequeDate = colchequeDate;
	}

	public String getColchequeRefNo() {
		return colchequeRefNo;
	}
	public void setColchequeRefNo(String colchequeRefNo) {
		this.colchequeRefNo = colchequeRefNo;
	}
	
	
	public Boolean getBooDisbale() {
		return booDisbale;
	}

	public void setBooDisbale(Boolean booDisbale) {
		this.booDisbale = booDisbale;
	}

	public String getKnetReceiptDT() {
		return knetReceiptDT;
	}

	public void setKnetReceiptDT(String knetReceiptDT) {
		this.knetReceiptDT = knetReceiptDT;
	}

	public String getKnetTransIdDT()
		{
			return knetTransIdDT;
		}

	public void setKnetTransIdDT(String knetTransIdDT)
		{
			this.knetTransIdDT = knetTransIdDT;
		}

	public String getKnetReceiptDateDT()
		{
			return knetReceiptDateDT;
		}

	public void setKnetReceiptDateDT(String knetReceiptDateDT)
		{
			this.knetReceiptDateDT = knetReceiptDateDT;
		}

	public String getKneRceiptTimeDT()
		{
			return kneRceiptTimeDT;
		}

	public void setKneRceiptTimeDT(String kneRceiptTimeDT)
		{
			this.kneRceiptTimeDT = kneRceiptTimeDT;
		}

	public Boolean getEditDisable() {
		return editDisable;
	}

	public void setEditDisable(Boolean editDisable) {
		this.editDisable = editDisable;
	}

	public String getMaskCardNumberDT() {
		return maskCardNumberDT;
	}

	public void setMaskCardNumberDT(String maskCardNumberDT) {
		this.maskCardNumberDT = maskCardNumberDT;
	}

	public String getColCardNum() {
		return colCardNum;
	}

	public void setColCardNum(String colCardNum) {
		this.colCardNum = colCardNum;
	}

	
	/*public String getChequeBankAcountNumber() {
		return chequeBankAcountNumber;
	}
	public void setChequeBankAcountNumber(String chequeBankAcountNumber) {
		this.chequeBankAcountNumber = chequeBankAcountNumber;
	}*/

	/*public String getChequeBank() {
		return chequeBank;
	}
	public void setChequeBank(String chequeBank) {
		this.chequeBank = chequeBank;
	}*/
	
}
