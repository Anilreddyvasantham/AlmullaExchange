package com.amg.exchange.remittance.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Clob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="VW_APPLICATION_DETAILS")
public class ShoppingCartDetails implements Serializable {
	
	
	private static final long serialVersionUID = 2315791709068216697L;
	
	private BigDecimal remittanceAppBeneficiaryId; 
	private BigDecimal applicationId;
	private BigDecimal companyId;        
	private BigDecimal documentFinanceYear;        
	private BigDecimal documentId;       
	private BigDecimal beneficiaryId;        
	private String beneficiaryName; 
	private String beneficiaryBank; 
	private String beneficiaryBranch; 
	private String beneficiaryAccountNo; 
	private String beneficiarySwiftAddrOne; 
	private String beneficiarySwiftAddrTwo; 
	private String beneficiarySwiftBankOne;  
	private String beneficiarySwiftBankTwo;  
	private String isActive;   
	private String beneficiaryFirstName;  
	private String beneficiarySecondName;  
	private String beneficiaryThirdName;  
	private String beneficiaryFourthName;  
	private BigDecimal remittanceApplicationId;        
	private BigDecimal foreignTranxAmount;  
	private BigDecimal localTranxAmount;        
	private BigDecimal localCommisionAmount;        
	private BigDecimal localChargeAmount;        
	private BigDecimal localDeliveryAmount;        
	private BigDecimal localNextTranxAmount;        
	private String applicationType;  
	private BigDecimal customerId;
	private BigDecimal exchangeRateApplied;// EXCHANGE_RATE_APPLIED
	private BigDecimal documentNo;
	private BigDecimal foreignCurrency;
	private BigDecimal localCurrency;
	private String spldeal;
	private String applicationTypeDesc;
	private String customerSignature;
	private Clob customerSignatureClob;
	private String sourceofincome;
	private String BeneCityName;
	private String BeneStateName;
	private String BeneDistrictName;
	private String instruction;
	private String sourceOfIncomeDesc;
	private String remittanceDescription;
	private String deliveryDescription;
	private BigDecimal loyaltsPointencahsed;
	private String loyaltsPointIndicator;
	private String foreignCurrencyDesc;
	private BigDecimal amtbCouponEncashed;
	
	public ShoppingCartDetails() {
		
	}


	@Column(name="REMITTANCE_APP_BENIFICIARY_ID")
	public BigDecimal getRemittanceAppBeneficiaryId() {
		return remittanceAppBeneficiaryId;
	}
	public void setRemittanceAppBeneficiaryId(BigDecimal remittanceAppBeneficiaryId) {
		this.remittanceAppBeneficiaryId = remittanceAppBeneficiaryId;
	}

	@Column(name="COMPANY_ID")
	public BigDecimal getCompanyId() {
		return companyId;
	}
	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}

	@Column(name="DOCUMENT_FINANCE_YEAR")
	public BigDecimal getDocumentFinanceYear() {
		return documentFinanceYear;
	}
	public void setDocumentFinanceYear(BigDecimal documentFinanceYear) {
		this.documentFinanceYear = documentFinanceYear;
	}

	@Column(name="DOCUMENT_ID")
	public BigDecimal getDocumentId() {
		return documentId;
	}
	public void setDocumentId(BigDecimal documentId) {
		this.documentId = documentId;
	}

	@Column(name="BENEFICIARY_ID")
	public BigDecimal getBeneficiaryId() {
		return beneficiaryId;
	}
	public void setBeneficiaryId(BigDecimal beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
	}

	@Column(name="BENEFICIARY_NAME")
	public String getBeneficiaryName() {
		return beneficiaryName;
	}
	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	@Column(name="BENEFICIARY_BANK")
	public String getBeneficiaryBank() {
		return beneficiaryBank;
	}
	public void setBeneficiaryBank(String beneficiaryBank) {
		this.beneficiaryBank = beneficiaryBank;
	}

	@Column(name="BENEFICIARY_BRANCH")
	public String getBeneficiaryBranch() {
		return beneficiaryBranch;
	}
	public void setBeneficiaryBranch(String beneficiaryBranch) {
		this.beneficiaryBranch = beneficiaryBranch;
	}

	@Column(name="BENEFICIARY_ACCOUNT_NO")
	public String getBeneficiaryAccountNo() {
		return beneficiaryAccountNo;
	}
	public void setBeneficiaryAccountNo(String beneficiaryAccountNo) {
		this.beneficiaryAccountNo = beneficiaryAccountNo;
	}

	@Column(name="BENEFICIARY_SWIFT_ADDR1")
	public String getBeneficiarySwiftAddrOne() {
		return beneficiarySwiftAddrOne;
	}
	public void setBeneficiarySwiftAddrOne(String beneficiarySwiftAddrOne) {
		this.beneficiarySwiftAddrOne = beneficiarySwiftAddrOne;
	}

	@Column(name="BENEFICIARY_SWIFT_ADDR2")
	public String getBeneficiarySwiftAddrTwo() {
		return beneficiarySwiftAddrTwo;
	}
	public void setBeneficiarySwiftAddrTwo(String beneficiarySwiftAddrTwo) {
		this.beneficiarySwiftAddrTwo = beneficiarySwiftAddrTwo;
	}

	@Column(name="BENEFICIARY_SWIFT_BANK1")
	public String getBeneficiarySwiftBankOne() {
		return beneficiarySwiftBankOne;
	}
	public void setBeneficiarySwiftBankOne(String beneficiarySwiftBankOne) {
		this.beneficiarySwiftBankOne = beneficiarySwiftBankOne;
	}

	@Column(name="BENEFICIARY_SWIFT_BANK2")
	public String getBeneficiarySwiftBankTwo() {
		return beneficiarySwiftBankTwo;
	}
	public void setBeneficiarySwiftBankTwo(String beneficiarySwiftBankTwo) {
		this.beneficiarySwiftBankTwo = beneficiarySwiftBankTwo;
	}

	@Column(name="ISACTIVE")
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Column(name="BENEFICIARY_FIRST_NAME")
	public String getBeneficiaryFirstName() {
		return beneficiaryFirstName;
	}
	public void setBeneficiaryFirstName(String beneficiaryFirstName) {
		this.beneficiaryFirstName = beneficiaryFirstName;
	}

	@Column(name="BENEFICIARY_SECOND_NAME")
	public String getBeneficiarySecondName() {
		return beneficiarySecondName;
	}
	public void setBeneficiarySecondName(String beneficiarySecondName) {
		this.beneficiarySecondName = beneficiarySecondName;
	}

	@Column(name="BENEFICIARY_THIRD_NAME")
	public String getBeneficiaryThirdName() {
		return beneficiaryThirdName;
	}
	public void setBeneficiaryThirdName(String beneficiaryThirdName) {
		this.beneficiaryThirdName = beneficiaryThirdName;
	}

	@Column(name="BENEFICIARY_FOURTH_NAME")
	public String getBeneficiaryFourthName() {
		return beneficiaryFourthName;
	}
	public void setBeneficiaryFourthName(String beneficiaryFourthName) {
		this.beneficiaryFourthName = beneficiaryFourthName;
	}

	@Column(name="REMITTANCE_APPLICATION_ID")
	public BigDecimal getRemittanceApplicationId() {
		return remittanceApplicationId;
	}
	public void setRemittanceApplicationId(BigDecimal remittanceApplicationId) {
		this.remittanceApplicationId = remittanceApplicationId;
	}

	@Column(name="FOREIGN_TRANX_AMOUNT")
	public BigDecimal getForeignTranxAmount() {
		return foreignTranxAmount;
	}
	public void setForeignTranxAmount(BigDecimal foreignTranxAmount) {
		this.foreignTranxAmount = foreignTranxAmount;
	}

	@Column(name="LOCAL_TRANX_AMOUNT")
	public BigDecimal getLocalTranxAmount() {
		return localTranxAmount;
	}
	public void setLocalTranxAmount(BigDecimal localTranxAmount) {
		this.localTranxAmount = localTranxAmount;
	}

	@Column(name="LOCAL_COMMISION_AMOUNT")
	public BigDecimal getLocalCommisionAmount() {
		return localCommisionAmount;
	}
	public void setLocalCommisionAmount(BigDecimal localCommisionAmount) {
		this.localCommisionAmount = localCommisionAmount;
	}

	@Column(name="LOCAL_CHARGE_AMOUNT")
	public BigDecimal getLocalChargeAmount() {
		return localChargeAmount;
	}
	public void setLocalChargeAmount(BigDecimal localChargeAmount) {
		this.localChargeAmount = localChargeAmount;
	}

	@Column(name="LOCAL_DELIVERY_AMOUNT")
	public BigDecimal getLocalDeliveryAmount() {
		return localDeliveryAmount;
	}
	public void setLocalDeliveryAmount(BigDecimal localDeliveryAmount) {
		this.localDeliveryAmount = localDeliveryAmount;
	}

	@Column(name="LOCAL_NET_TRANX_AMOUNT")
	public BigDecimal getLocalNextTranxAmount() {
		return localNextTranxAmount;
	}
	public void setLocalNextTranxAmount(BigDecimal localNextTranxAmount) {
		this.localNextTranxAmount = localNextTranxAmount;
	}

	@Column(name="APPLICATION_TYPE")
	public String getApplicationType() {
		return applicationType;
	}
	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}
	
	@Column(name="CUSTOMER_ID")
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	@Column(name="EXCHANGE_RATE_APPLIED")
	public BigDecimal getExchangeRateApplied() {
		return exchangeRateApplied;
	}
	public void setExchangeRateApplied(BigDecimal exchangeRateApplied) {
		this.exchangeRateApplied = exchangeRateApplied;
	}

	@Id
	@Column(name="APPLICATION_DETAILS_ID")
	public BigDecimal getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(BigDecimal applicationId) {
		this.applicationId = applicationId;
	}
	
	@Column(name="DOCUMENT_NO")
	public BigDecimal getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}
	
	@Column(name="FOREIGN_CURRENCY_ID")
	public BigDecimal getForeignCurrency() {
		return foreignCurrency;
	}
	public void setForeignCurrency(BigDecimal foreignCurrency) {
		this.foreignCurrency = foreignCurrency;
	}
	
	@Column(name="LOCAL_CURRENCY_ID")
	public BigDecimal getLocalCurrency() {
		return localCurrency;
	}
	public void setLocalCurrency(BigDecimal localCurrency) {
		this.localCurrency = localCurrency;
	}
	
	@Column(name="SPECIAL_DEAL")
	public String getSpldeal() {
		return spldeal;
	}
	public void setSpldeal(String spldeal) {
		this.spldeal = spldeal;
	}
	
	@Column(name="APPLICATION_TYPE_DESC")
	public String getApplicationTypeDesc() {
		return applicationTypeDesc;
	}
	public void setApplicationTypeDesc(String applicationTypeDesc) {
		this.applicationTypeDesc = applicationTypeDesc;
	}

	@Column(name="SIGNATURE_SPECIMEN")
	public String getCustomerSignature() {
		return customerSignature;
	}
	public void setCustomerSignature(String customerSignature) {
		this.customerSignature = customerSignature;
	}

	@Column(name="SOURCE_OF_INCOME_ID")
	public String getSourceofincome() {
		return sourceofincome;
	}
	public void setSourceofincome(String sourceofincome) {
		this.sourceofincome = sourceofincome;
	}

	@Column(name="CITY_NAME")
	public String getBeneCityName() {
		return BeneCityName;
	}
	public void setBeneCityName(String beneCityName) {
		BeneCityName = beneCityName;
	}

	@Column(name="STATE_NAME")
	public String getBeneStateName() {
		return BeneStateName;
	}
	public void setBeneStateName(String beneStateName) {
		BeneStateName = beneStateName;
	}

	@Column(name="DISTRICT_NAME")
	public String getBeneDistrictName() {
		return BeneDistrictName;
	}
	public void setBeneDistrictName(String beneDistrictName) {
		BeneDistrictName = beneDistrictName;
	}

	@Column(name="INSTRUCTION")
	public String getInstruction() {
		return instruction;
	}
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	@Column(name="SIGNATURE_SPECIMEN_CLOB")
	public Clob getCustomerSignatureClob() {
		return customerSignatureClob;
	}
	public void setCustomerSignatureClob(Clob customerSignatureClob) {
		this.customerSignatureClob = customerSignatureClob;
	}

	@Column(name="SOURCE_OF_INCOME_DESC")
	public String getSourceOfIncomeDesc() {
		return sourceOfIncomeDesc;
	}
	public void setSourceOfIncomeDesc(String sourceOfIncomeDesc) {
		this.sourceOfIncomeDesc = sourceOfIncomeDesc;
	}

	@Column(name="REMITTANCE_DESCRIPTION")
	public String getRemittanceDescription() {
		return remittanceDescription;
	}
	public void setRemittanceDescription(String remittanceDescription) {
		this.remittanceDescription = remittanceDescription;
	}

	@Column(name="DELIVERY_DESCRIPTION")
	public String getDeliveryDescription() {
		return deliveryDescription;
	}
	public void setDeliveryDescription(String deliveryDescription) {
		this.deliveryDescription = deliveryDescription;
	}
	
	@Column(name="LOYALTY_POINTS_ENCASHED")
	public BigDecimal getLoyaltsPointencahsed() {
		return loyaltsPointencahsed;
	}
	public void setLoyaltsPointencahsed(BigDecimal loyaltsPointencahsed) {
		this.loyaltsPointencahsed = loyaltsPointencahsed;
	}
	
	@Column(name="LOYALTY_POINTS_IND")
	public String getLoyaltsPointIndicator() {
		return loyaltsPointIndicator;
	}
	public void setLoyaltsPointIndicator(String loyaltsPointIndicator) {
		this.loyaltsPointIndicator = loyaltsPointIndicator;
	}

	@Column(name="FOREIGN_CURRENCY_DESC")
	public String getForeignCurrencyDesc() {
		return foreignCurrencyDesc;
	}
	public void setForeignCurrencyDesc(String foreignCurrencyDesc) {
		this.foreignCurrencyDesc = foreignCurrencyDesc;
	}


	@Column(name="AMTB_COUPON_ENCASHED")
	public BigDecimal getAmtbCouponEncashed() {
		return amtbCouponEncashed;
	}


	public void setAmtbCouponEncashed(BigDecimal amtbCouponEncashed) {
		this.amtbCouponEncashed = amtbCouponEncashed;
	}


	
	

}
