package com.amg.exchange.wu.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.amg.exchange.registration.model.CountryBranch;

@Entity
@Table(name = "EX_WU_MONEYTRANSFER")
public class WesternUnionTransfer implements Serializable {
	private static final long serialVersionUID = 1L;

	private BigDecimal westernUnionId;
	private BigDecimal companyCode;
	private BigDecimal documentCode;
	private BigDecimal documentFinanceYear;
	private BigDecimal documentNo;
	private BigDecimal customerReference;
	private BigDecimal beneficiarySequence;
	private Date documentDate;
	private String terminalId;
	private BigDecimal wuId;
	private Date createdDate;
	private String createdBy;
	private String locationCode;
	private Date accountYearMonth;
	private BigDecimal mtcYear;
	private String inorOut;
	private String columnMode;
	private String receiverFirstName;
	private String receiverLastName;
	private String senderTitle;
	private String senderFirstName;
	private String senderLastName;
	private String senderLocalArea;
	private String senderPhone;
	private String senderCity;
	private String senderPost;
	private String senderToWn;
	private String senderCountry;
	private BigDecimal sendAmount;
	private BigDecimal destinationAmount;
	private String sendCurrency;
	private String destinationCurrency;
	private BigDecimal fees;
	private BigDecimal rate;
	private String destinationCountry;
	private String paymentMode;
	private BigDecimal equivalentAmount;
	private String destinationPlace;
	private Date updatedDate;
	private String modifiedBy;
	private String progNo;
	private String denominationUpdated;
	private String wuMTCNo;
	private String mtcNo;
	private BigDecimal exDocumentCode;
	private BigDecimal exDocumentFinanceYear;
	private BigDecimal exDocumentNo;
	private String collError;
	private String sysColl;
	private BigDecimal deliveryCharge;
	private BigDecimal serviceCharge;
	private BigDecimal taxCharge;
	private BigDecimal refundAmount;
	private String javaTransaction;
	private String purposeId;
	private String sourceOfIncomeId;

	public WesternUnionTransfer() {
	}

	public WesternUnionTransfer(BigDecimal westernUnionId,
			BigDecimal companyCode, BigDecimal documentCode,
			BigDecimal documentFinanceYear, BigDecimal documentNo,
			BigDecimal customerReference, BigDecimal beneficiarySequence,
			Date documentDate, String terminalId, BigDecimal wuId,
			Date createdDate, String createdBy, String locationCode,
			Date accountYearMonth, BigDecimal mtcYear, String inorOut,
			String columnMode, String receiverFirstName,
			String receiverLastName, String senderTitle,
			String senderFirstName, String senderLastName,
			String senderLocalArea, String senderPhone, String senderCity,
			String senderPost, String senderToWn, String senderCountry,
			BigDecimal sendAmount, BigDecimal destinationAmount,
			String sendCurrency, String destinationCurrency, BigDecimal fees,
			BigDecimal rate, String destinationCountry, String paymentMode,
			BigDecimal equivalentAmount, String destinationPlace,
			Date updatedDate, String modifiedBy, String progNo,
			String denominationUpdated, String wuMTCNo, String mtcNo,
			BigDecimal exDocumentCode, BigDecimal exDocumentFinanceYear,
			BigDecimal exDocumentNo, String collError, String sysColl,
			BigDecimal deliveryCharge, BigDecimal serviceCharge,
			BigDecimal taxCharge, BigDecimal refundAmount,
			String javaTransaction, String purposeId,
			String sourceOfIncomeId) {
		super();
		this.westernUnionId = westernUnionId;
		this.companyCode = companyCode;
		this.documentCode = documentCode;
		this.documentFinanceYear = documentFinanceYear;
		this.documentNo = documentNo;
		this.customerReference = customerReference;
		this.beneficiarySequence = beneficiarySequence;
		this.documentDate = documentDate;
		this.terminalId = terminalId;
		this.wuId = wuId;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.locationCode = locationCode;
		this.accountYearMonth = accountYearMonth;
		this.mtcYear = mtcYear;
		this.inorOut = inorOut;
		this.columnMode = columnMode;
		this.receiverFirstName = receiverFirstName;
		this.receiverLastName = receiverLastName;
		this.senderTitle = senderTitle;
		this.senderFirstName = senderFirstName;
		this.senderLastName = senderLastName;
		this.senderLocalArea = senderLocalArea;
		this.senderPhone = senderPhone;
		this.senderCity = senderCity;
		this.senderPost = senderPost;
		this.senderToWn = senderToWn;
		this.senderCountry = senderCountry;
		this.sendAmount = sendAmount;
		this.destinationAmount = destinationAmount;
		this.sendCurrency = sendCurrency;
		this.destinationCurrency = destinationCurrency;
		this.fees = fees;
		this.rate = rate;
		this.destinationCountry = destinationCountry;
		this.paymentMode = paymentMode;
		this.equivalentAmount = equivalentAmount;
		this.destinationPlace = destinationPlace;
		this.updatedDate = updatedDate;
		this.modifiedBy = modifiedBy;
		this.progNo = progNo;
		this.denominationUpdated = denominationUpdated;
		this.wuMTCNo = wuMTCNo;
		this.mtcNo = mtcNo;
		this.exDocumentCode = exDocumentCode;
		this.exDocumentFinanceYear = exDocumentFinanceYear;
		this.exDocumentNo = exDocumentNo;
		this.collError = collError;
		this.sysColl = sysColl;
		this.deliveryCharge = deliveryCharge;
		this.serviceCharge = serviceCharge;
		this.taxCharge = taxCharge;
		this.refundAmount = refundAmount;
		this.javaTransaction = javaTransaction;
		this.purposeId = purposeId;
		this.sourceOfIncomeId = sourceOfIncomeId;
	}



	@Id
	@GeneratedValue(generator = "ex_wu_moneytransfer_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ex_wu_moneytransfer_seq", sequenceName = "EX_WU_MONEYTRANSFER_SEQ", allocationSize = 1)
	@Column(name = "WU_MONEY_TRANSFER_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getWesternUnionId() {
		return westernUnionId;
	}

	public void setWesternUnionId(BigDecimal westernUnionId) {
		this.westernUnionId = westernUnionId;
	}

	@Column(name = "COMCOD")
	public BigDecimal getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(BigDecimal companyCode) {
		this.companyCode = companyCode;
	}

	@Column(name = "DOCCOD")
	public BigDecimal getDocumentCode() {
		return documentCode;
	}

	public void setDocumentCode(BigDecimal documentCode) {
		this.documentCode = documentCode;
	}

	@Column(name = "DOCFYR")
	public BigDecimal getDocumentFinanceYear() {
		return documentFinanceYear;
	}

	public void setDocumentFinanceYear(BigDecimal documentFinanceYear) {
		this.documentFinanceYear = documentFinanceYear;
	}

	@Column(name = "DOCNO")
	public BigDecimal getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}

	@Column(name = "CUSREF")
	public BigDecimal getCustomerReference() {
		return customerReference;
	}

	public void setCustomerReference(BigDecimal customerReference) {
		this.customerReference = customerReference;
	}

	@Column(name = "BENE_SEQ")
	public BigDecimal getBeneficiarySequence() {
		return beneficiarySequence;
	}

	public void setBeneficiarySequence(BigDecimal beneficiarySequence) {
		this.beneficiarySequence = beneficiarySequence;
	}

	@Column(name = "DOCDAT")
	public Date getDocumentDate() {
		return documentDate;
	}

	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}

	@Column(name = "TERMINALID")
	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	@Column(name = "WUID")
	public BigDecimal getWuId() {
		return wuId;
	}

	public void setWuId(BigDecimal wuId) {
		this.wuId = wuId;
	}

	@Column(name = "CRTDAT")
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "CREATOR")
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "LOCCOD")
	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	@Column(name = "ACYYMM")
	public Date getAccountYearMonth() {
		return accountYearMonth;
	}

	public void setAccountYearMonth(Date accountYearMonth) {
		this.accountYearMonth = accountYearMonth;
	}

	@Column(name = "MTCFYR")
	public BigDecimal getMtcYear() {
		return mtcYear;
	}

	public void setMtcYear(BigDecimal mtcYear) {
		this.mtcYear = mtcYear;
	}

	@Column(name = "INOROUT")
	public String getInorOut() {
		return inorOut;
	}

	public void setInorOut(String inorOut) {
		this.inorOut = inorOut;
	}

	@Column(name = "COLLMOD")
	public String getColumnMode() {
		return columnMode;
	}

	public void setColumnMode(String columnMode) {
		this.columnMode = columnMode;
	}

	@Column(name = "RECIVERFIRSTNAME")
	public String getReceiverFirstName() {
		return receiverFirstName;
	}

	public void setReceiverFirstName(String receiverFirstName) {
		this.receiverFirstName = receiverFirstName;
	}

	@Column(name = "RECIVERLASTNAME")
	public String getReceiverLastName() {
		return receiverLastName;
	}

	public void setReceiverLastName(String receiverLastName) {
		this.receiverLastName = receiverLastName;
	}

	@Column(name = "SENDERTITLE")
	public String getSenderTitle() {
		return senderTitle;
	}

	public void setSenderTitle(String senderTitle) {
		this.senderTitle = senderTitle;
	}

	@Column(name = "SENDERFIRSTNAME")
	public String getSenderFirstName() {
		return senderFirstName;
	}

	public void setSenderFirstName(String senderFirstName) {
		this.senderFirstName = senderFirstName;
	}

	@Column(name = "SENDERLASTNAME")
	public String getSenderLastName() {
		return senderLastName;
	}

	public void setSenderLastName(String senderLastName) {
		this.senderLastName = senderLastName;
	}

	@Column(name = "SENDERLOCALAREA")
	public String getSenderLocalArea() {
		return senderLocalArea;
	}

	public void setSenderLocalArea(String senderLocalArea) {
		this.senderLocalArea = senderLocalArea;
	}

	@Column(name = "SENDERPHONE")
	public String getSenderPhone() {
		return senderPhone;
	}

	public void setSenderPhone(String senderPhone) {
		this.senderPhone = senderPhone;
	}

	@Column(name = "SENDERCITY")
	public String getSenderCity() {
		return senderCity;
	}

	public void setSenderCity(String senderCity) {
		this.senderCity = senderCity;
	}

	@Column(name = "SENDERPOST")
	public String getSenderPost() {
		return senderPost;
	}

	public void setSenderPost(String senderPost) {
		this.senderPost = senderPost;
	}

	@Column(name = "SENDERTOWN")
	public String getSenderToWn() {
		return senderToWn;
	}

	public void setSenderToWn(String senderToWn) {
		this.senderToWn = senderToWn;
	}

	@Column(name = "SENDERCOUNTRY")
	public String getSenderCountry() {
		return senderCountry;
	}

	public void setSenderCountry(String senderCountry) {
		this.senderCountry = senderCountry;
	}

	@Column(name = "SENDAMT")
	public BigDecimal getSendAmount() {
		return sendAmount;
	}

	public void setSendAmount(BigDecimal sendAmount) {
		this.sendAmount = sendAmount;
	}

	@Column(name = "DESTAMT")
	public BigDecimal getDestinationAmount() {
		return destinationAmount;
	}

	public void setDestinationAmount(BigDecimal destinationAmount) {
		this.destinationAmount = destinationAmount;
	}

	@Column(name = "SENDCUR")
	public String getSendCurrency() {
		return sendCurrency;
	}

	public void setSendCurrency(String sendCurrency) {
		this.sendCurrency = sendCurrency;
	}

	@Column(name = "DESTCUR")
	public String getDestinationCurrency() {
		return destinationCurrency;
	}

	public void setDestinationCurrency(String destinationCurrency) {
		this.destinationCurrency = destinationCurrency;
	}

	@Column(name = "FEES")
	public BigDecimal getFees() {
		return fees;
	}

	public void setFees(BigDecimal fees) {
		this.fees = fees;
	}

	@Column(name = "RATE")
	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	@Column(name = "DESTCOUNTRY")
	public String getDestinationCountry() {
		return destinationCountry;
	}

	public void setDestinationCountry(String destinationCountry) {
		this.destinationCountry = destinationCountry;
	}

	@Column(name = "PAYMODE")
	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	@Column(name = "EQVAMT")
	public BigDecimal getEquivalentAmount() {
		return equivalentAmount;
	}

	public void setEquivalentAmount(BigDecimal equivalentAmount) {
		this.equivalentAmount = equivalentAmount;
	}

	@Column(name = "DESTPLACE")
	public String getDestinationPlace() {
		return destinationPlace;
	}

	public void setDestinationPlace(String destinationPlace) {
		this.destinationPlace = destinationPlace;
	}

	@Column(name = "UPDDAT")
	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	@Column(name = "MODIFIER")
	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "PROGNO")
	public String getProgNo() {
		return progNo;
	}

	public void setProgNo(String progNo) {
		this.progNo = progNo;
	}

	@Column(name = "DENO_UPD")
	public String getDenominationUpdated() {
		return denominationUpdated;
	}

	public void setDenominationUpdated(String denominationUpdated) {
		this.denominationUpdated = denominationUpdated;
	}

	@Column(name = "WU_MTCNO")
	public String getWuMTCNo() {
		return wuMTCNo;
	}

	public void setWuMTCNo(String wuMTCNo) {
		this.wuMTCNo = wuMTCNo;
	}

	@Column(name = "MTCNO")
	public String getMtcNo() {
		return mtcNo;
	}

	public void setMtcNo(String mtcNo) {
		this.mtcNo = mtcNo;
	}

	@Column(name = "EX_DOCCOD")
	public BigDecimal getExDocumentCode() {
		return exDocumentCode;
	}

	public void setExDocumentCode(BigDecimal exDocumentCode) {
		this.exDocumentCode = exDocumentCode;
	}

	@Column(name = "EX_DOCFYR")
	public BigDecimal getExDocumentFinanceYear() {
		return exDocumentFinanceYear;
	}

	public void setExDocumentFinanceYear(BigDecimal exDocumentFinanceYear) {
		this.exDocumentFinanceYear = exDocumentFinanceYear;
	}

	@Column(name = "EX_DOCNO")
	public BigDecimal getExDocumentNo() {
		return exDocumentNo;
	}

	public void setExDocumentNo(BigDecimal exDocumentNo) {
		this.exDocumentNo = exDocumentNo;
	}

	@Column(name = "COLL_ERR")
	public String getCollError() {
		return collError;
	}

	public void setCollError(String collError) {
		this.collError = collError;
	}

	@Column(name = "SYS_COLL")
	public String getSysColl() {
		return sysColl;
	}

	public void setSysColl(String sysColl) {
		this.sysColl = sysColl;
	}

	@Column(name = "DELVCHG")
	public BigDecimal getDeliveryCharge() {
		return deliveryCharge;
	}

	public void setDeliveryCharge(BigDecimal deliveryCharge) {
		this.deliveryCharge = deliveryCharge;
	}

	@Column(name = "SERVCHG")
	public BigDecimal getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(BigDecimal serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	@Column(name = "TAXCHG")
	public BigDecimal getTaxCharge() {
		return taxCharge;
	}

	public void setTaxCharge(BigDecimal taxCharge) {
		this.taxCharge = taxCharge;
	}

	@Column(name = "REFAMT")
	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	@Column(name = "JAVA_TRNX")
	public String getJavaTransaction() {
		return javaTransaction;
	}

	public void setJavaTransaction(String javaTransaction) {
		this.javaTransaction = javaTransaction;
	}

	@Column(name = "PURP_ID")
	public String getPurposeId() {
		return purposeId;
	}
	public void setPurposeId(String purposeId) {
		this.purposeId = purposeId;
		
	}

	@Column(name = "SRCI_ID")
	public String getSourceOfIncomeId() {
		return sourceOfIncomeId;
	}
	public void setSourceOfIncomeId(String sourceOfIncomeId) {
		this.sourceOfIncomeId = sourceOfIncomeId;
	}

	
	

	
	
	
	
}
