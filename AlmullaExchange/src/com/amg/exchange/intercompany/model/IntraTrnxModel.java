package com.amg.exchange.intercompany.model;

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

/**
 * @author Chiranjeevi
 * 
 */
@Entity
@Table(name = "EX_INTRA_TRNX")
public class IntraTrnxModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private BigDecimal intraTransactionId;
	private BigDecimal applicationCountryId;
	private BigDecimal beneficiaryId;
	private BigDecimal companyId;
	private BigDecimal customerId;
	private BigDecimal deliveryModeId;
	private BigDecimal documentFinanceYear;
	private BigDecimal documentId;
	private BigDecimal documentNo;
	private BigDecimal paidCountryBranchId;
	private String pinNumber;
	private String recvCityName;
	private String recvCountryName;
	private String recvCountryTelCode;
	private String recvDistrictName;
	private String recvFirstName;
	private String recvFirstNameLocal;
	private String recvSecondName;
	private String recvSecondNameLocal;
	private String recvThirdName;
	private String recvThirdNameLocal;
	private String recvFourthName;
	private String recvFourthNameLocal;
	private String recvFifthName;
	private String recvFifthNameLocal;
	private BigDecimal recvMobileNumber;
	private String recvStateName;
	private String recvTelephoneNumber;
	private BigDecimal remittanceModeId;
	private String foreignCurrencyName;
	private BigDecimal sendcompanyCode;
	private BigDecimal foreignTrnxAmount;
	private String sendCountryName;
	private Date sendDocumentDate;
	private BigDecimal sendDocumentFinanceYear;
	private BigDecimal sendDocumentCode;
	private BigDecimal sendDocumentNo;
	private String sendFirstName;
	private String sendFirstNameLocal;
	private String sendSecondName;
	private String sendSecondNameLocal;
	private String sendThirdName;
	private String sendThirdNameLocal;
	private String beneBankCode;
	private String beneBankName;
	private String beneBankBranchName;
	private String beneBankBranchCode;
	private String beneBankAccountNumber;
	private String localBankCode;
	private String transactionStatus;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	
	
	public IntraTrnxModel() {
		super();
	}


	@Id
	@GeneratedValue(generator = "ex_intra_trnx_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ex_intra_trnx_seq", sequenceName = "EX_INTRA_TRNX_SEQ", allocationSize = 1)
	@Column(name = "INTRA_TRANSACTION_ID", nullable = false)
	public BigDecimal getIntraTransactionId() {
		return intraTransactionId;
	}
	public void setIntraTransactionId(BigDecimal intraTransactionId) {
		this.intraTransactionId = intraTransactionId;
	}

	@Column(name = "APPLICATION_COUNTRY_ID")
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}
	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}

	@Column(name = "BENEFICIARY_ID")
	public BigDecimal getBeneficiaryId() {
		return beneficiaryId;
	}
	public void setBeneficiaryId(BigDecimal beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
	}

	@Column(name = "COMPANY_ID")
	public BigDecimal getCompanyId() {
		return companyId;
	}
	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}

	@Column(name = "CUSTOMER_ID")
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	
	@Column(name = "DELIVERY_MODE_ID")
	public BigDecimal getDeliveryModeId() {
		return deliveryModeId;
	}
	public void setDeliveryModeId(BigDecimal deliveryModeId) {
		this.deliveryModeId = deliveryModeId;
	}

	@Column(name = "DOCUMENT_FINANCE_YEAR")
	public BigDecimal getDocumentFinanceYear() {
		return documentFinanceYear;
	}
	public void setDocumentFinanceYear(BigDecimal documentFinanceYear) {
		this.documentFinanceYear = documentFinanceYear;
	}

	@Column(name = "DOCUMENT_ID")
	public BigDecimal getDocumentId() {
		return documentId;
	}
	public void setDocumentId(BigDecimal documentId) {
		this.documentId = documentId;
	}

	@Column(name = "DOCUMENT_NO")
	public BigDecimal getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}

	@Column(name = "PAID_COUNTRY_BRANCH_ID")
	public BigDecimal getPaidCountryBranchId() {
		return paidCountryBranchId;
	}
	public void setPaidCountryBranchId(BigDecimal paidCountryBranchId) {
		this.paidCountryBranchId = paidCountryBranchId;
	}

	@Column(name = "PIN_NUMBER")
	public String getPinNumber() {
		return pinNumber;
	}
	public void setPinNumber(String pinNumber) {
		this.pinNumber = pinNumber;
	}

	@Column(name = "RECV_CITY_NAME")
	public String getRecvCityName() {
		return recvCityName;
	}
	public void setRecvCityName(String recvCityName) {
		this.recvCityName = recvCityName;
	}

	@Column(name = "RECV_COUNTRY_NAME")
	public String getRecvCountryName() {
		return recvCountryName;
	}
	public void setRecvCountryName(String recvCountryName) {
		this.recvCountryName = recvCountryName;
	}

	@Column(name = "RECV_COUNTRY_TEL_CODE")
	public String getRecvCountryTelCode() {
		return recvCountryTelCode;
	}
	public void setRecvCountryTelCode(String recvCountryTelCode) {
		this.recvCountryTelCode = recvCountryTelCode;
	}

	@Column(name = "RECV_DISTRICT_NAME")
	public String getRecvDistrictName() {
		return recvDistrictName;
	}
	public void setRecvDistrictName(String recvDistrictName) {
		this.recvDistrictName = recvDistrictName;
	}

	@Column(name = "RECV_FIRST_NAME")
	public String getRecvFirstName() {
		return recvFirstName;
	}
	public void setRecvFirstName(String recvFirstName) {
		this.recvFirstName = recvFirstName;
	}

	@Column(name = "RECV_FIRST_NAME_LOCAL")
	public String getRecvFirstNameLocal() {
		return recvFirstNameLocal;
	}
	public void setRecvFirstNameLocal(String recvFirstNameLocal) {
		this.recvFirstNameLocal = recvFirstNameLocal;
	}

	@Column(name = "RECV_SECOND_NAME")
	public String getRecvSecondName() {
		return recvSecondName;
	}
	public void setRecvSecondName(String recvSecondName) {
		this.recvSecondName = recvSecondName;
	}

	@Column(name = "RECV_SECOND_NAME_LOCAL")
	public String getRecvSecondNameLocal() {
		return recvSecondNameLocal;
	}
	public void setRecvSecondNameLocal(String recvSecondNameLocal) {
		this.recvSecondNameLocal = recvSecondNameLocal;
	}

	@Column(name = "RECV_THIRD_NAME")
	public String getRecvThirdName() {
		return recvThirdName;
	}
	public void setRecvThirdName(String recvThirdName) {
		this.recvThirdName = recvThirdName;
	}

	@Column(name = "RECV_THIRD_NAME_LOCAL")
	public String getRecvThirdNameLocal() {
		return recvThirdNameLocal;
	}
	public void setRecvThirdNameLocal(String recvThirdNameLocal) {
		this.recvThirdNameLocal = recvThirdNameLocal;
	}

	@Column(name = "RECV_FOURTH_NAME")
	public String getRecvFourthName() {
		return recvFourthName;
	}
	public void setRecvFourthName(String recvFourthName) {
		this.recvFourthName = recvFourthName;
	}

	@Column(name = "RECV_FOURTH_NAME_LOCAL")
	public String getRecvFourthNameLocal() {
		return recvFourthNameLocal;
	}
	public void setRecvFourthNameLocal(String recvFourthNameLocal) {
		this.recvFourthNameLocal = recvFourthNameLocal;
	}

	@Column(name = "RECV_FIFTH_NAME")
	public String getRecvFifthName() {
		return recvFifthName;
	}
	public void setRecvFifthName(String recvFifthName) {
		this.recvFifthName = recvFifthName;
	}

	@Column(name = "RECV_FIFTH_NAME_LOCAL")
	public String getRecvFifthNameLocal() {
		return recvFifthNameLocal;
	}
	public void setRecvFifthNameLocal(String recvFifthNameLocal) {
		this.recvFifthNameLocal = recvFifthNameLocal;
	}

	@Column(name = "RECV_MOBILE_NUMBER")
	public BigDecimal getRecvMobileNumber() {
		return recvMobileNumber;
	}
	public void setRecvMobileNumber(BigDecimal recvMobileNumber) {
		this.recvMobileNumber = recvMobileNumber;
	}

	@Column(name = "RECV_STATE_NAME")
	public String getRecvStateName() {
		return recvStateName;
	}
	public void setRecvStateName(String recvStateName) {
		this.recvStateName = recvStateName;
	}

	@Column(name = "RECV_TELEPHONE_NUMBER")
	public String getRecvTelephoneNumber() {
		return recvTelephoneNumber;
	}
	public void setRecvTelephoneNumber(String recvTelephoneNumber) {
		this.recvTelephoneNumber = recvTelephoneNumber;
	}

	@Column(name = "REMITTANCE_MODE_ID")
	public BigDecimal getRemittanceModeId() {
		return remittanceModeId;
	}
	public void setRemittanceModeId(BigDecimal remittanceModeId) {
		this.remittanceModeId = remittanceModeId;
	}

	@Column(name = "FOREIGN_CURRENCY_NAME")
	public String getForeignCurrencyName() {
		return foreignCurrencyName;
	}
	public void setForeignCurrencyName(String foreignCurrencyName) {
		this.foreignCurrencyName = foreignCurrencyName;
	}

	@Column(name = "SEND_COMPANY_CODE")
	public BigDecimal getSendcompanyCode() {
		return sendcompanyCode;
	}
	public void setSendcompanyCode(BigDecimal sendcompanyCode) {
		this.sendcompanyCode = sendcompanyCode;
	}

	@Column(name = "FOREIGN_TRANX_AMOUNT")
	public BigDecimal getForeignTrnxAmount() {
		return foreignTrnxAmount;
	}
	public void setForeignTrnxAmount(BigDecimal foreignTrnxAmount) {
		this.foreignTrnxAmount = foreignTrnxAmount;
	}
	
	@Column(name = "SEND_COUNTRY_NAME")
	public String getSendCountryName() {
		return sendCountryName;
	}
	public void setSendCountryName(String sendCountryName) {
		this.sendCountryName = sendCountryName;
	}

	@Column(name = "SEND_DOCUMENT_DATE")
	public Date getSendDocumentDate() {
		return sendDocumentDate;
	}
	public void setSendDocumentDate(Date sendDocumentDate) {
		this.sendDocumentDate = sendDocumentDate;
	}

	@Column(name = "SEND_DOCUMENT_FINANCE_YEAR")
	public BigDecimal getSendDocumentFinanceYear() {
		return sendDocumentFinanceYear;
	}
	public void setSendDocumentFinanceYear(BigDecimal sendDocumentFinanceYear) {
		this.sendDocumentFinanceYear = sendDocumentFinanceYear;
	}

	@Column(name = "SEND_DOCUMENT_CODE")
	public BigDecimal getSendDocumentCode() {
		return sendDocumentCode;
	}
	public void setSendDocumentCode(BigDecimal sendDocumentCode) {
		this.sendDocumentCode = sendDocumentCode;
	}
	
	@Column(name = "SEND_DOCUMENT_NO")
	public BigDecimal getSendDocumentNo() {
		return sendDocumentNo;
	}
	public void setSendDocumentNo(BigDecimal sendDocumentNo) {
		this.sendDocumentNo = sendDocumentNo;
	}

	@Column(name = "SEND_FIRST_NAME")
	public String getSendFirstName() {
		return sendFirstName;
	}
	public void setSendFirstName(String sendFirstName) {
		this.sendFirstName = sendFirstName;
	}

	@Column(name = "SEND_FIRST_NAME_LOCAL")
	public String getSendFirstNameLocal() {
		return sendFirstNameLocal;
	}
	public void setSendFirstNameLocal(String sendFirstNameLocal) {
		this.sendFirstNameLocal = sendFirstNameLocal;
	}

	@Column(name = "SEND_SECOND_NAME")
	public String getSendSecondName() {
		return sendSecondName;
	}
	public void setSendSecondName(String sendSecondName) {
		this.sendSecondName = sendSecondName;
	}

	@Column(name = "SEND_SECOND_NAME_LOCAL")
	public String getSendSecondNameLocal() {
		return sendSecondNameLocal;
	}
	public void setSendSecondNameLocal(String sendSecondNameLocal) {
		this.sendSecondNameLocal = sendSecondNameLocal;
	}

	@Column(name = "SEND_THIRD_NAME")
	public String getSendThirdName() {
		return sendThirdName;
	}
	public void setSendThirdName(String sendThirdName) {
		this.sendThirdName = sendThirdName;
	}

	@Column(name = "SEND_THIRD_NAME_LOCAL")
	public String getSendThirdNameLocal() {
		return sendThirdNameLocal;
	}
	public void setSendThirdNameLocal(String sendThirdNameLocal) {
		this.sendThirdNameLocal = sendThirdNameLocal;
	}
	
	@Column(name = "BENE_BANK_CODE")
	public String getBeneBankCode() {
		return beneBankCode;
	}
	public void setBeneBankCode(String beneBankCode) {
		this.beneBankCode = beneBankCode;
	}

	@Column(name = "BENE_BANK_NAME")
	public String getBeneBankName() {
		return beneBankName;
	}
	public void setBeneBankName(String beneBankName) {
		this.beneBankName = beneBankName;
	}
	
	@Column(name = "BENE_BANK_BRANCH_NAME")
	public String getBeneBankBranchName() {
		return beneBankBranchName;
	}
	public void setBeneBankBranchName(String beneBankBranchName) {
		this.beneBankBranchName = beneBankBranchName;
	}

	@Column(name = "BENE_BANK_BRANCH_CODE")
	public String getBeneBankBranchCode() {
		return beneBankBranchCode;
	}
	public void setBeneBankBranchCode(String beneBankBranchCode) {
		this.beneBankBranchCode = beneBankBranchCode;
	}

	@Column(name = "BENE_BANK_ACCOUNT_NUMBER")
	public String getBeneBankAccountNumber() {
		return beneBankAccountNumber;
	}
	public void setBeneBankAccountNumber(String beneBankAccountNumber) {
		this.beneBankAccountNumber = beneBankAccountNumber;
	}

	@Column(name = "TRANSACTION_STATUS")
	public String getTransactionStatus() {
		return transactionStatus;
	}
	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	@Column(name = "LOCAL_BANK_CODE")
	public String getLocalBankCode() {
		return localBankCode;
	}
	public void setLocalBankCode(String localBankCode) {
		this.localBankCode = localBankCode;
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

	
}
