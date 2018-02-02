package com.amg.exchange.remittance.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class ShoppingCartDataTableBean implements Serializable {

	private static final long serialVersionUID = 2315791709068216697L;
	private BigDecimal remittanceAppBeneficiaryId;        
	private BigDecimal companyId;        
	private BigDecimal documentFinanceYear;        
	private BigDecimal documentId;       
	private BigDecimal beneficiaryId;        
	private String beneficiaryName; 
	private String beneficiaryBank; 
	private String beneficiaryBranch; 
	private String beneficiaryAccountNo; 
	private String beneficiaryInterBankOne; 
	private String beneficiaryInterBankTwo; 
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
	private BigDecimal exchangeRateApplied;
	private BigDecimal customerId;
	private Boolean selectedrecord = false;
	private BigDecimal applicationDetailsId;
	private BigDecimal documentNo;
	private BigDecimal foreigncurrency;
	private BigDecimal localcurrency;
	private String foreignCurrencyDesc;
	private String spldeal;
	private String spldealStatus;
	private Boolean deleteStatus = true;
	private String applicationTypeDesc;
	private boolean booReportEligible;
	private BigDecimal loyaltsPointencahsed;
	private String loyaltsPointIndicator;
	private BigDecimal amtbCouponencahsed;
	private Boolean booRenderCheckBox;
	
	public BigDecimal getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}
	
	public BigDecimal getRemittanceAppBeneficiaryId() {
		return remittanceAppBeneficiaryId;
	}
	public void setRemittanceAppBeneficiaryId(BigDecimal remittanceAppBeneficiaryId) {
		this.remittanceAppBeneficiaryId = remittanceAppBeneficiaryId;
	}
	
	public BigDecimal getCompanyId() {
		return companyId;
	}
	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}
	
	public BigDecimal getDocumentFinanceYear() {
		return documentFinanceYear;
	}
	public void setDocumentFinanceYear(BigDecimal documentFinanceYear) {
		this.documentFinanceYear = documentFinanceYear;
	}
	
	public BigDecimal getDocumentId() {
		return documentId;
	}
	public void setDocumentId(BigDecimal documentId) {
		this.documentId = documentId;
	}
	
	public BigDecimal getBeneficiaryId() {
		return beneficiaryId;
	}
	public void setBeneficiaryId(BigDecimal beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
	}
	
	public String getBeneficiaryName() {
		return beneficiaryName;
	}
	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}
	
	public String getBeneficiaryBank() {
		return beneficiaryBank;
	}
	public void setBeneficiaryBank(String beneficiaryBank) {
		this.beneficiaryBank = beneficiaryBank;
	}
	
	public String getBeneficiaryBranch() {
		return beneficiaryBranch;
	}
	public void setBeneficiaryBranch(String beneficiaryBranch) {
		this.beneficiaryBranch = beneficiaryBranch;
	}
	
	public String getBeneficiaryAccountNo() {
		return beneficiaryAccountNo;
	}
	public void setBeneficiaryAccountNo(String beneficiaryAccountNo) {
		this.beneficiaryAccountNo = beneficiaryAccountNo;
	}
	
	public String getBeneficiaryInterBankOne() {
		return beneficiaryInterBankOne;
	}
	public void setBeneficiaryInterBankOne(String beneficiaryInterBankOne) {
		this.beneficiaryInterBankOne = beneficiaryInterBankOne;
	}
	
	public String getBeneficiaryInterBankTwo() {
		return beneficiaryInterBankTwo;
	}
	public void setBeneficiaryInterBankTwo(String beneficiaryInterBankTwo) {
		this.beneficiaryInterBankTwo = beneficiaryInterBankTwo;
	}
	
	public String getBeneficiarySwiftBankOne() {
		return beneficiarySwiftBankOne;
	}
	public void setBeneficiarySwiftBankOne(String beneficiarySwiftBankOne) {
		this.beneficiarySwiftBankOne = beneficiarySwiftBankOne;
	}
	
	public String getBeneficiarySwiftBankTwo() {
		return beneficiarySwiftBankTwo;
	}
	public void setBeneficiarySwiftBankTwo(String beneficiarySwiftBankTwo) {
		this.beneficiarySwiftBankTwo = beneficiarySwiftBankTwo;
	}
	
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
	public String getBeneficiaryFirstName() {
		return beneficiaryFirstName;
	}
	public void setBeneficiaryFirstName(String beneficiaryFirstName) {
		this.beneficiaryFirstName = beneficiaryFirstName;
	}
	
	public String getBeneficiarySecondName() {
		return beneficiarySecondName;
	}
	public void setBeneficiarySecondName(String beneficiarySecondName) {
		this.beneficiarySecondName = beneficiarySecondName;
	}
	
	public String getBeneficiaryThirdName() {
		return beneficiaryThirdName;
	}
	public void setBeneficiaryThirdName(String beneficiaryThirdName) {
		this.beneficiaryThirdName = beneficiaryThirdName;
	}
	
	public String getBeneficiaryFourthName() {
		return beneficiaryFourthName;
	}
	public void setBeneficiaryFourthName(String beneficiaryFourthName) {
		this.beneficiaryFourthName = beneficiaryFourthName;
	}
	
	public BigDecimal getRemittanceApplicationId() {
		return remittanceApplicationId;
	}
	public void setRemittanceApplicationId(BigDecimal remittanceApplicationId) {
		this.remittanceApplicationId = remittanceApplicationId;
	}
	
	public BigDecimal getForeignTranxAmount() {
		return foreignTranxAmount;
	}
	public void setForeignTranxAmount(BigDecimal foreignTranxAmount) {
		this.foreignTranxAmount = foreignTranxAmount;
	}
	
	public BigDecimal getLocalTranxAmount() {
		return localTranxAmount;
	}
	public void setLocalTranxAmount(BigDecimal localTranxAmount) {
		this.localTranxAmount = localTranxAmount;
	}
	
	public BigDecimal getLocalCommisionAmount() {
		return localCommisionAmount;
	}
	public void setLocalCommisionAmount(BigDecimal localCommisionAmount) {
		this.localCommisionAmount = localCommisionAmount;
	}
	
	public BigDecimal getLocalChargeAmount() {
		return localChargeAmount;
	}
	public void setLocalChargeAmount(BigDecimal localChargeAmount) {
		this.localChargeAmount = localChargeAmount;
	}
	
	public BigDecimal getLocalDeliveryAmount() {
		return localDeliveryAmount;
	}
	public void setLocalDeliveryAmount(BigDecimal localDeliveryAmount) {
		this.localDeliveryAmount = localDeliveryAmount;
	}
	
	public BigDecimal getLocalNextTranxAmount() {
		return localNextTranxAmount;
	}
	public void setLocalNextTranxAmount(BigDecimal localNextTranxAmount) {
		this.localNextTranxAmount = localNextTranxAmount;
	}
	
	public String getApplicationType() {
		return applicationType;
	}
	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}
	
	public BigDecimal getExchangeRateApplied() {
		return exchangeRateApplied;
	}
	public void setExchangeRateApplied(BigDecimal exchangeRateApplied) {
		this.exchangeRateApplied = exchangeRateApplied;
	}
	
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	
	public Boolean getSelectedrecord() {
		return selectedrecord;
	}
	public void setSelectedrecord(Boolean selectedrecord) {
		this.selectedrecord = selectedrecord;
	}
	
	public BigDecimal getApplicationDetailsId() {
		return applicationDetailsId;
	}
	public void setApplicationDetailsId(BigDecimal applicationDetailsId) {
		this.applicationDetailsId = applicationDetailsId;
	}
	
	public BigDecimal getForeigncurrency() {
		return foreigncurrency;
	}
	public void setForeigncurrency(BigDecimal foreigncurrency) {
		this.foreigncurrency = foreigncurrency;
	}
	
	public BigDecimal getLocalcurrency() {
		return localcurrency;
	}
	public void setLocalcurrency(BigDecimal localcurrency) {
		this.localcurrency = localcurrency;
	}
	
	public String getSpldeal() {
		return spldeal;
	}
	public void setSpldeal(String spldeal) {
		this.spldeal = spldeal;
	}
	
	public String getSpldealStatus() {
		return spldealStatus;
	}
	public void setSpldealStatus(String spldealStatus) {
		this.spldealStatus = spldealStatus;
	}
	
	public Boolean getDeleteStatus() {
		return deleteStatus;
	}
	public void setDeleteStatus(Boolean deleteStatus) {
		this.deleteStatus = deleteStatus;
	}
	
	public String getApplicationTypeDesc() {
		return applicationTypeDesc;
	}
	public void setApplicationTypeDesc(String applicationTypeDesc) {
		this.applicationTypeDesc = applicationTypeDesc;
	}
	
	public boolean isBooReportEligible() {
		return booReportEligible;
	}
	public void setBooReportEligible(boolean booReportEligible) {
		this.booReportEligible = booReportEligible;
	}
	
	public BigDecimal getLoyaltsPointencahsed() {
		return loyaltsPointencahsed;
	}
	public void setLoyaltsPointencahsed(BigDecimal loyaltsPointencahsed) {
		this.loyaltsPointencahsed = loyaltsPointencahsed;
	}
	
	public String getLoyaltsPointIndicator() {
		return loyaltsPointIndicator;
	}
	public void setLoyaltsPointIndicator(String loyaltsPointIndicator) {
		this.loyaltsPointIndicator = loyaltsPointIndicator;
	}
	
	public String getForeignCurrencyDesc() {
		return foreignCurrencyDesc;
	}
	public void setForeignCurrencyDesc(String foreignCurrencyDesc) {
		this.foreignCurrencyDesc = foreignCurrencyDesc;
	}
	
	public BigDecimal getAmtbCouponencahsed() {
		return amtbCouponencahsed;
	}
	public void setAmtbCouponencahsed(BigDecimal amtbCouponencahsed) {
		this.amtbCouponencahsed = amtbCouponencahsed;
	}
	
	public Boolean getBooRenderCheckBox() {
		return booRenderCheckBox;
	}
	public void setBooRenderCheckBox(Boolean booRenderCheckBox) {
		this.booRenderCheckBox = booRenderCheckBox;
	}
	
}
