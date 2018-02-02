package com.amg.exchange.treasury.bean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class BankTransferBeanDataTable {

	private int lineNo;
	
	public int getLineNo() {
		return lineNo;
	}

	public void setLineNo(int lineNo) {
		this.lineNo = lineNo;
	}

	private String bankName;
	private BigDecimal bankId;
	
	private String accountNo;
	private BigDecimal accountDtlId;
	
	private BigDecimal fcAmount;
	private String fcAmt; 
	
	private String exChangeRate;
	
	private BigDecimal localAmount;
	private String localAmt;
	
	private String bankTrToInstrunction;
	private String bankTrFromInstrunction;
	private String instDescription;
	
	private BigDecimal currencyId;
	private String lineType;
	
	private BigDecimal tresuryDtPK=null;
	private BigDecimal tresurySdPK=null;
	
	private String createdBy=null;
	private Date createdDate=null;
	
	private String bankTrToInstrunctionName;
	
	private boolean selectedrecord;
	
	private String glslNo; 
	
	private BigDecimal accountDetId;
	private BigDecimal standardInsId;
	private BigDecimal localExchangeRate;
	private List<BigDecimal> treasuryStdBrToPkId;
	
	
	public BigDecimal getAccountDetId() {
		return accountDetId;
	}

	public BigDecimal getStandardInsId() {
		return standardInsId;
	}

	public void setAccountDetId(BigDecimal accountDetId) {
		this.accountDetId = accountDetId;
	}

	public void setStandardInsId(BigDecimal standardInsId) {
		this.standardInsId = standardInsId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public BigDecimal getTresuryDtPK() {
		return tresuryDtPK;
	}

	public void setTresuryDtPK(BigDecimal tresuryDtPK) {
		this.tresuryDtPK = tresuryDtPK;
	}

	public BigDecimal getTresurySdPK() {
		return tresurySdPK;
	}

	
	public String getBankTrFromInstrunction() {
		return bankTrFromInstrunction;
	}

	public void setBankTrFromInstrunction(String bankTrFromInstrunction) {
		this.bankTrFromInstrunction = bankTrFromInstrunction;
	}

	public void setTresurySdPK(BigDecimal tresurySdPK) {
		this.tresurySdPK = tresurySdPK;
	}

	

	public BankTransferBeanDataTable() {
		
	}

	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public BigDecimal getBankId() {
		return bankId;
	}
	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public BigDecimal getAccountDtlId() {
		return accountDtlId;
	}
	public void setAccountDtlId(BigDecimal accountDtlId) {
		this.accountDtlId = accountDtlId;
	}
	public BigDecimal getFcAmount() {
		return fcAmount;
	}
	public void setFcAmount(BigDecimal fcAmount) {
		this.fcAmount = fcAmount;
	}
	public String getFcAmt() {
		return fcAmt;
	}
	public void setFcAmt(String fcAmt) {
		this.fcAmt = fcAmt;
	}
	public String getExChangeRate() {
		return exChangeRate;
	}
	public void setExChangeRate(String exChangeRate) {
		this.exChangeRate = exChangeRate;
	}
	public BigDecimal getLocalAmount() {
		return localAmount;
	}
	public void setLocalAmount(BigDecimal localAmount) {
		this.localAmount = localAmount;
	}
	public String getLocalAmt() {
		return localAmt;
	}
	public void setLocalAmt(String localAmt) {
		this.localAmt = localAmt;
	}
	
	public String getBankTrToInstrunction() {
		return bankTrToInstrunction;
	}

	public void setBankTrToInstrunction(String bankTrToInstrunction) {
		this.bankTrToInstrunction = bankTrToInstrunction;
	}

	public String getInstDescription() {
		return instDescription;
	}
	public void setInstDescription(String instDescription) {
		this.instDescription = instDescription;
	}

	public BigDecimal getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	public String getLineType() {
		return lineType;
	}

	public void setLineType(String lineType) {
		this.lineType = lineType;
	}

	public String getBankTrToInstrunctionName() {
		return bankTrToInstrunctionName;
	}

	public void setBankTrToInstrunctionName(String bankTrToInstrunctionName) {
		this.bankTrToInstrunctionName = bankTrToInstrunctionName;
	}

	public boolean getSelectedrecord() {
		return selectedrecord;
	}

	public void setSelectedrecord(boolean selectedrecord) {
		this.selectedrecord = selectedrecord;
	}

	public String getGlslNo() {
		return glslNo;
	}

	public void setGlslNo(String glslNo) {
		this.glslNo = glslNo;
	}

	public BigDecimal getLocalExchangeRate() {
		return localExchangeRate;
	}

	public void setLocalExchangeRate(BigDecimal localExchangeRate) {
		this.localExchangeRate = localExchangeRate;
	}

	public List<BigDecimal> getTreasuryStdBrToPkId() {
		return treasuryStdBrToPkId;
	}

	public void setTreasuryStdBrToPkId(List<BigDecimal> treasuryStdBrToPkId) {
		this.treasuryStdBrToPkId = treasuryStdBrToPkId;
	}
	
	
	
}
