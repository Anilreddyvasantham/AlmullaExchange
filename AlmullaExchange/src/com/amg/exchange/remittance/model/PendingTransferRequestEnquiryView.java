package com.amg.exchange.remittance.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="VW_EX_TRANSFER_ENQUIRY")
public class PendingTransferRequestEnquiryView implements Serializable{

	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "IDNO")
	private BigDecimal idNo;
	
	/*@Column(name = "APPLICATION_COUNTRY_ID")
	private BigDecimal applicationCountryId;*/
	
/*	@Column(name = "COUNTRY_NAME")
	private String countryName;*/
	
	@Column(name = "COMPANY_ID")
	private BigDecimal companyId;
	
	@Column(name = "DOCUMENT_FINANCE_YEAR")
	private BigDecimal documentFinaceYear;
	
/*	@Column(name = "DOCUMENT_ID")
	private BigDecimal documentId;*/
	
	@Column(name = "DOCUMENT_NO")
	private BigDecimal documentNo;
	
	@Column(name = "COUNTRY_BRANCH_ID")
	private BigDecimal countryBranchId;
	
	@Column(name = "DOCUMENT_DATE")
	private Date documentDate;
	
	@Column(name = "SALE_CURRENCY_ID")
	private BigDecimal saleCurrencyId;
	
	@Column(name = "SALE_CURRENCY_CODE")
	private String saleCurrencyCode;
	
	@Column(name = "SALE_AMOUNT")
	private BigDecimal saleAmount;
	
	@Column(name = "PURCHASE_CURRENCY_ID")
	private BigDecimal purchageCurrencyId;
	
	@Column(name = "PURCHASE_CURRENCY_CODE")
	private String purchageCurrencyCode;
	
	@Column(name = "PURCHASE_AMOUNT")
	private BigDecimal purchageAmount;
	
	@Column(name = "CUSTOMER_ID")
	private BigDecimal customerId;
	
	@Column(name = "CUSTOMER_REF")
	private BigDecimal customerRef;
	
	@Column(name = "BENE_NAME")
	private String beneficiaryName;
	
	@Column(name = "BENEFICIARY_BANK")
	private String beneficiaryBank;
	
	@Column(name = "BENEFICIARY_BRANCH")
	private String beneficiaryBranch;
	
	@Column(name = "BENEFICIARY_ACCOUNT_NO")
	private String beneficiaryAccountNo;
	
	@Column(name = "BENEFICIARY_SWIFT_ADDR1")
	private String beneficiarySwiftAddr1;
	
	@Column(name = "BENEFICIARY_SWIFT_ADDR2")
	private String beneficiarySwiftAddr2;
	
	@Column(name = "BENE_STATE_NAME")
	private String beneficiaryStateName;
	
	@Column(name = "BENE_DISTRICT_NAME")
	private String beneficiaryDistrictName;
	
	@Column(name = "BENE_CITY_NAME")
	private String beneficiaryCityName;
	
	@Column(name = "REMITTANCE_MODE_ID")
	private BigDecimal remittanceModeId;
	
	@Column(name = "REMITTANCE_DESCRIPTION")
	private String remittanceDescription;
	
	@Column(name = "DELIVERY_MODE_ID")
	private BigDecimal deliveryModeId;
	
	@Column(name = "DELIVERY_DESCRIPTION")
	private String deliveryDescription;
	
	@Column(name = "BANK_ID")
	private BigDecimal bankId;
	
	@Column(name = "CORRESPONDING_BANK_NAME")
	private String correspondingBankName;
	
	@Column(name = "BANK_BRANCH_ID")
	private BigDecimal bankBranchId;
	
	@Column(name = "PAYABLE_AT")
	private String payableAt;
	
	@Column(name = "EXCHANGE_RATE_APPLIED")
	private BigDecimal exchangeRate;
	
	@Column(name = "LOCAL_COMMISION_AMOUNT")
	private BigDecimal localCommisionAmount;
	
	@Column(name = "LOCAL_CHARGE_AMOUNT")
	private BigDecimal localChargeAmount;
	
	@Column(name = "LOCAL_DELIVERY_AMOUNT")
	private BigDecimal localDeliveryAmount;
	
	@Column(name = "LOCAL_NET_TRANX_AMOUNT")
	private BigDecimal localNetTransactionAmount;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "MODIFIED_BY")
	private String modifiedBy;
	
	@Column(name = "MODIFIED_DATE")
	private Date modifiedDate;
	
	@Column(name = "ISACTIVE")
	private String isActive;
	
	@Column(name = "CUSTOMER_NAME")
	private String customerName;
	
	@Column(name = "CUSTOMER_TELEPHONE")
	private String customerTelephone;
   
	@Column(name = "APP_STATUS")
	private String appStatus;
	
	@Column(name = "PAYMENT_ID")
	private String paymentId;
	
	@Column(name = "RESULT_CODE")
	private String resultCode;
	
	@Column(name = "LOYALTY_POINTS_IND")
	private String loyaltyPoint;
	
	@Column(name = "LOYALTY_POINTS_ENCASHED")
	private BigDecimal loyaltyPointEncashed;
	
	@Column(name="ACCOUNT_MMYYYY")
	private Date accMonthYear;
	
	
	
	public BigDecimal getIdNo() {
		return idNo;
	}

	/*public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}

	public String getCountryName() {
		return countryName;
	}
*/
	public BigDecimal getCompanyId() {
		return companyId;
	}

	public BigDecimal getDocumentFinaceYear() {
		return documentFinaceYear;
	}

	/*public BigDecimal getDocumentId() {
		return documentId;
	}*/

	public BigDecimal getDocumentNo() {
		return documentNo;
	}

	public BigDecimal getCountryBranchId() {
		return countryBranchId;
	}

	public Date getDocumentDate() {
		return documentDate;
	}

	public BigDecimal getSaleCurrencyId() {
		return saleCurrencyId;
	}

	public String getSaleCurrencyCode() {
		return saleCurrencyCode;
	}

	public BigDecimal getSaleAmount() {
		return saleAmount;
	}

	public BigDecimal getPurchageCurrencyId() {
		return purchageCurrencyId;
	}

	public String getPurchageCurrencyCode() {
		return purchageCurrencyCode;
	}

	public BigDecimal getPurchageAmount() {
		return purchageAmount;
	}

	public BigDecimal getCustomerId() {
		return customerId;
	}

	public BigDecimal getCustomerRef() {
		return customerRef;
	}

	public String getBeneficiaryName() {
		return beneficiaryName;
	}

	public String getBeneficiaryBank() {
		return beneficiaryBank;
	}

	public String getBeneficiaryBranch() {
		return beneficiaryBranch;
	}

	public String getBeneficiaryAccountNo() {
		return beneficiaryAccountNo;
	}

	public String getBeneficiarySwiftAddr1() {
		return beneficiarySwiftAddr1;
	}

	public String getBeneficiarySwiftAddr2() {
		return beneficiarySwiftAddr2;
	}

	public String getBeneficiaryStateName() {
		return beneficiaryStateName;
	}

	public String getBeneficiaryDistrictName() {
		return beneficiaryDistrictName;
	}

	public String getBeneficiaryCityName() {
		return beneficiaryCityName;
	}

	public BigDecimal getRemittanceModeId() {
		return remittanceModeId;
	}

	public String getRemittanceDescription() {
		return remittanceDescription;
	}

	public BigDecimal getDeliveryModeId() {
		return deliveryModeId;
	}

	public String getDeliveryDescription() {
		return deliveryDescription;
	}

	public BigDecimal getBankId() {
		return bankId;
	}

	public String getCorrespondingBankName() {
		return correspondingBankName;
	}

	public BigDecimal getBankBranchId() {
		return bankBranchId;
	}

	public String getPayableAt() {
		return payableAt;
	}

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public BigDecimal getLocalCommisionAmount() {
		return localCommisionAmount;
	}

	public BigDecimal getLocalChargeAmount() {
		return localChargeAmount;
	}

	public BigDecimal getLocalDeliveryAmount() {
		return localDeliveryAmount;
	}

	public BigDecimal getLocalNetTransactionAmount() {
		return localNetTransactionAmount;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public String getIsActive() {
		return isActive;
	}

	public String getCustomerName() {
		return customerName;
	}

	public String getCustomerTelephone() {
		return customerTelephone;
	}

	public void setIdNo(BigDecimal idNo) {
		this.idNo = idNo;
	}

/*	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}*/

	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}

	public void setDocumentFinaceYear(BigDecimal documentFinaceYear) {
		this.documentFinaceYear = documentFinaceYear;
	}

	/*public void setDocumentId(BigDecimal documentId) {
		this.documentId = documentId;
	}*/

	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}

	public void setCountryBranchId(BigDecimal countryBranchId) {
		this.countryBranchId = countryBranchId;
	}

	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}

	public void setSaleCurrencyId(BigDecimal saleCurrencyId) {
		this.saleCurrencyId = saleCurrencyId;
	}

	public void setSaleCurrencyCode(String saleCurrencyCode) {
		this.saleCurrencyCode = saleCurrencyCode;
	}

	public void setSaleAmount(BigDecimal saleAmount) {
		this.saleAmount = saleAmount;
	}

	public void setPurchageCurrencyId(BigDecimal purchageCurrencyId) {
		this.purchageCurrencyId = purchageCurrencyId;
	}

	public void setPurchageCurrencyCode(String purchageCurrencyCode) {
		this.purchageCurrencyCode = purchageCurrencyCode;
	}

	public void setPurchageAmount(BigDecimal purchageAmount) {
		this.purchageAmount = purchageAmount;
	}

	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	public void setCustomerRef(BigDecimal customerRef) {
		this.customerRef = customerRef;
	}

	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	public void setBeneficiaryBank(String beneficiaryBank) {
		this.beneficiaryBank = beneficiaryBank;
	}

	public void setBeneficiaryBranch(String beneficiaryBranch) {
		this.beneficiaryBranch = beneficiaryBranch;
	}

	public void setBeneficiaryAccountNo(String beneficiaryAccountNo) {
		this.beneficiaryAccountNo = beneficiaryAccountNo;
	}

	public void setBeneficiarySwiftAddr1(String beneficiarySwiftAddr1) {
		this.beneficiarySwiftAddr1 = beneficiarySwiftAddr1;
	}

	public void setBeneficiarySwiftAddr2(String beneficiarySwiftAddr2) {
		this.beneficiarySwiftAddr2 = beneficiarySwiftAddr2;
	}

	public void setBeneficiaryStateName(String beneficiaryStateName) {
		this.beneficiaryStateName = beneficiaryStateName;
	}

	public void setBeneficiaryDistrictName(String beneficiaryDistrictName) {
		this.beneficiaryDistrictName = beneficiaryDistrictName;
	}

	public void setBeneficiaryCityName(String beneficiaryCityName) {
		this.beneficiaryCityName = beneficiaryCityName;
	}

	public void setRemittanceModeId(BigDecimal remittanceModeId) {
		this.remittanceModeId = remittanceModeId;
	}

	public void setRemittanceDescription(String remittanceDescription) {
		this.remittanceDescription = remittanceDescription;
	}

	public void setDeliveryModeId(BigDecimal deliveryModeId) {
		this.deliveryModeId = deliveryModeId;
	}

	public void setDeliveryDescription(String deliveryDescription) {
		this.deliveryDescription = deliveryDescription;
	}

	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}

	public void setCorrespondingBankName(String correspondingBankName) {
		this.correspondingBankName = correspondingBankName;
	}

	public void setBankBranchId(BigDecimal bankBranchId) {
		this.bankBranchId = bankBranchId;
	}

	public void setPayableAt(String payableAt) {
		this.payableAt = payableAt;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public void setLocalCommisionAmount(BigDecimal localCommisionAmount) {
		this.localCommisionAmount = localCommisionAmount;
	}

	public void setLocalChargeAmount(BigDecimal localChargeAmount) {
		this.localChargeAmount = localChargeAmount;
	}

	public void setLocalDeliveryAmount(BigDecimal localDeliveryAmount) {
		this.localDeliveryAmount = localDeliveryAmount;
	}

	public void setLocalNetTransactionAmount(BigDecimal localNetTransactionAmount) {
		this.localNetTransactionAmount = localNetTransactionAmount;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public void setCustomerTelephone(String customerTelephone) {
		this.customerTelephone = customerTelephone;
	}

	public String getAppStatus() {
		return appStatus;
	}

	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getLoyaltyPoint() {
		return loyaltyPoint;
	}

	public void setLoyaltyPoint(String loyaltyPoint) {
		this.loyaltyPoint = loyaltyPoint;
	}

	public BigDecimal getLoyaltyPointEncashed() {
		return loyaltyPointEncashed;
	}

	public void setLoyaltyPointEncashed(BigDecimal loyaltyPointEncashed) {
		this.loyaltyPointEncashed = loyaltyPointEncashed;
	}

	public Date getAccMonthYear() {
		return accMonthYear;
	}

	public void setAccMonthYear(Date accMonthYear) {
		this.accMonthYear = accMonthYear;
	}

	
}
