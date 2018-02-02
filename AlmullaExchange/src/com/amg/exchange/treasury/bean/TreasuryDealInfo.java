package com.amg.exchange.treasury.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class TreasuryDealInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal treasuryDealHeaderId;
	private BigDecimal treasuryDealDetailsId;
	private BigDecimal applicationCountryId;
	private BigDecimal companyid;
	private BigDecimal documentFinanceYear;
	private BigDecimal documentId;
	private BigDecimal documentNo;
	private String dealNo;
	private Date documentDate;
	private String dealWith;
	private String bankAddress;
	private BigDecimal TotalPurchaseFcAmount;
	private Date valuedatePD;
	private Date valuedateSD;
	private BigDecimal purchaseExchangeRate;
	private BigDecimal saleAmount;
	private String dealConcludedWith;
	private String dealConcludedBy;
	private String dealDescription;
	private BigDecimal documentCode;
	private String faAccountNo;
	private BigDecimal currencyId;
	private String currencyCode;
	private String currencyname;
	private BigDecimal fcAmount;
	private BigDecimal localExchangeRate;
	private BigDecimal localAmount;
	private String lineType;
	private String logoPath;
	private String dealWithType;
	private String purchaseCurrencyCode;
	private String saleCurrencyCode;	
	private BigDecimal saleExchangeRate;
	private String soldAccountNo;
	private String currencynameSD;
	private String remarks;
	private String authorizedBy;
	private String dealer;
	private BigDecimal totalFaAmount;
	private BigDecimal totalSoldAmount;
	private BigDecimal totalPdAmount;
	private String sdBankCode;
	private String pdBankCode;
	private String pdAcNo;
	private String sdAcNo;
	private String faxNo;
	private String subReportPath;
	private List<TreasuryDealInfo> saleList=new ArrayList<TreasuryDealInfo>();
	private List<TreasuryDealInfo> treasuryDealInfoList=new CopyOnWriteArrayList<TreasuryDealInfo>();
	
	private String purchaseStandardInstruction;
	private String salesStandardInstruction;
	
	private String companyPhone;
	private String companyFax;
	private String companyRegNo;
	private String companyTelex;
	private String companyEmail;
	private String companyShareCaptal;
	private String companyAddress;
	private String dairy;
	private String dealReason;
	

	
	
	public String getDairy() {
		return dairy;
	}
	public String getDealReason() {
		return dealReason;
	}
	public void setDairy(String dairy) {
		this.dairy = dairy;
	}
	public void setDealReason(String dealReason) {
		this.dealReason = dealReason;
	}
	public String getCompanyPhone() {
		return companyPhone;
	}
	public String getCompanyFax() {
		return companyFax;
	}
	public String getCompanyRegNo() {
		return companyRegNo;
	}
	public String getCompanyTelex() {
		return companyTelex;
	}
	public String getCompanyEmail() {
		return companyEmail;
	}
	public String getCompanyShareCaptal() {
		return companyShareCaptal;
	}
	public String getCompanyAddress() {
		return companyAddress;
	}
	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}
	public void setCompanyFax(String companyFax) {
		this.companyFax = companyFax;
	}
	public void setCompanyRegNo(String companyRegNo) {
		this.companyRegNo = companyRegNo;
	}
	public void setCompanyTelex(String companyTelex) {
		this.companyTelex = companyTelex;
	}
	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}
	public void setCompanyShareCaptal(String companyShareCaptal) {
		this.companyShareCaptal = companyShareCaptal;
	}
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	public String getSubReportPath() {
		return subReportPath;
	}
	public void setSubReportPath(String subReportPath) {
		this.subReportPath = subReportPath;
	}
	public List<TreasuryDealInfo> getTreasuryDealInfoList() {
		return treasuryDealInfoList;
	}
	public void setTreasuryDealInfoList(List<TreasuryDealInfo> treasuryDealInfoList) {
		this.treasuryDealInfoList = treasuryDealInfoList;
	}
	public String getFaxNo() {
		return faxNo;
	}
	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}
	public String getSdBankCode() {
		return sdBankCode;
	}
	public void setSdBankCode(String sdBankCode) {
		this.sdBankCode = sdBankCode;
	}
	public String getPdBankCode() {
		return pdBankCode;
	}
	public void setPdBankCode(String pdBankCode) {
		this.pdBankCode = pdBankCode;
	}
	public String getPdAcNo() {
		return pdAcNo;
	}
	public void setPdAcNo(String pdAcNo) {
		this.pdAcNo = pdAcNo;
	}
	public String getSdAcNo() {
		return sdAcNo;
	}
	public void setSdAcNo(String sdAcNo) {
		this.sdAcNo = sdAcNo;
	}
	public BigDecimal getTotalFaAmount() {
		return totalFaAmount;
	}
	public void setTotalFaAmount(BigDecimal totalFaAmount) {
		this.totalFaAmount = totalFaAmount;
	}
	public BigDecimal getTotalSoldAmount() {
		return totalSoldAmount;
	}
	public void setTotalSoldAmount(BigDecimal totalSoldAmount) {
		this.totalSoldAmount = totalSoldAmount;
	}
	public BigDecimal getTotalPdAmount() {
		return totalPdAmount;
	}
	public void setTotalPdAmount(BigDecimal totalPdAmount) {
		this.totalPdAmount = totalPdAmount;
	}
	public String getDealer() {
		return dealer;
	}
	public String getAuthorizedBy() {
		return authorizedBy;
	}
	public void setAuthorizedBy(String authorizedBy) {
		this.authorizedBy = authorizedBy;
	}
	public void setDealer(String dealer) {
		this.dealer = dealer;
	}
	public BigDecimal getTreasuryDealHeaderId() {
		return treasuryDealHeaderId;
	}
	public void setTreasuryDealHeaderId(BigDecimal treasuryDealHeaderId) {
		this.treasuryDealHeaderId = treasuryDealHeaderId;
	}
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}
	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}
	public BigDecimal getCompanyid() {
		return companyid;
	}
	public void setCompanyid(BigDecimal companyid) {
		this.companyid = companyid;
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
	public BigDecimal getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}
	public String getDealNo() {
		return dealNo;
	}
	public void setDealNo(String dealNo) {
		this.dealNo = dealNo;
	}
	public Date getDocumentDate() {
		return documentDate;
	}
	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}
	public String getDealWith() {
		return dealWith;
	}
	public void setDealWith(String dealWith) {
		this.dealWith = dealWith;
	}
	public String getBankAddress() {
		return bankAddress;
	}
	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}
	public BigDecimal getTotalPurchaseFcAmount() {
		return TotalPurchaseFcAmount;
	}
	public void setTotalPurchaseFcAmount(BigDecimal totalPurchaseFcAmount) {
		TotalPurchaseFcAmount = totalPurchaseFcAmount;
	}
	
	public Date getValuedatePD() {
		return valuedatePD;
	}
	public void setValuedatePD(Date valuedatePD) {
		this.valuedatePD = valuedatePD;
	}
	public Date getValuedateSD() {
		return valuedateSD;
	}
	public void setValuedateSD(Date valuedateSD) {
		this.valuedateSD = valuedateSD;
	}
	public BigDecimal getPurchaseExchangeRate() {
		return purchaseExchangeRate;
	}
	public void setPurchaseExchangeRate(BigDecimal purchaseExchangeRate) {
		this.purchaseExchangeRate = purchaseExchangeRate;
	}
	public BigDecimal getSaleAmount() {
		return saleAmount;
	}
	public void setSaleAmount(BigDecimal saleAmount) {
		this.saleAmount = saleAmount;
	}
	public String getDealConcludedWith() {
		return dealConcludedWith;
	}
	public void setDealConcludedWith(String dealConcludedWith) {
		this.dealConcludedWith = dealConcludedWith;
	}
	public String getDealConcludedBy() {
		return dealConcludedBy;
	}
	public void setDealConcludedBy(String dealConcludedBy) {
		this.dealConcludedBy = dealConcludedBy;
	}
	public String getDealDescription() {
		return dealDescription;
	}
	public void setDealDescription(String dealDescription) {
		this.dealDescription = dealDescription;
	}
	public BigDecimal getDocumentCode() {
		return documentCode;
	}
	public void setDocumentCode(BigDecimal documentCode) {
		this.documentCode = documentCode;
	}
	public String getFaAccountNo() {
		return faAccountNo;
	}
	public void setFaAccountNo(String faAccountNo) {
		this.faAccountNo = faAccountNo;
	}
	public BigDecimal getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getCurrencyname() {
		return currencyname;
	}
	public void setCurrencyname(String currencyname) {
		this.currencyname = currencyname;
	}
	public BigDecimal getFcAmount() {
		return fcAmount;
	}
	public void setFcAmount(BigDecimal fcAmount) {
		this.fcAmount = fcAmount;
	}
	public BigDecimal getLocalExchangeRate() {
		return localExchangeRate;
	}
	public void setLocalExchangeRate(BigDecimal localExchangeRate) {
		this.localExchangeRate = localExchangeRate;
	}
	public BigDecimal getLocalAmount() {
		return localAmount;
	}
	public void setLocalAmount(BigDecimal localAmount) {
		this.localAmount = localAmount;
	}
	public String getLineType() {
		return lineType;
	}
	public void setLineType(String lineType) {
		this.lineType = lineType;
	}
	public String getLogoPath() {
		return logoPath;
	}
	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}
	public String getCurrencynameSD() {
		return currencynameSD;
	}
	public void setCurrencynameSD(String currencynameSD) {
		this.currencynameSD = currencynameSD;
	}
	public String getSoldAccountNo() {
		return soldAccountNo;
	}
	public void setSoldAccountNo(String soldAccountNo) {
		this.soldAccountNo = soldAccountNo;
	}
	public List<TreasuryDealInfo> getSaleList() {
		return saleList;
	}
	public void setSaleList(List<TreasuryDealInfo> saleList) {
		this.saleList = saleList;
	}
	public String getPurchaseCurrencyCode() {
		return purchaseCurrencyCode;
	}
	public void setPurchaseCurrencyCode(String purchaseCurrencyCode) {
		this.purchaseCurrencyCode = purchaseCurrencyCode;
	}
	public String getSaleCurrencyCode() {
		return saleCurrencyCode;
	}
	public void setSaleCurrencyCode(String saleCurrencyCode) {
		this.saleCurrencyCode = saleCurrencyCode;
	}
	public BigDecimal getSaleExchangeRate() {
		return saleExchangeRate;
	}
	public void setSaleExchangeRate(BigDecimal saleExchangeRate) {
		this.saleExchangeRate = saleExchangeRate;
	}
	public String getDealWithType() {
		return dealWithType;
	}
	public void setDealWithType(String dealWithType) {
		this.dealWithType = dealWithType;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public BigDecimal getTreasuryDealDetailsId() {
		return treasuryDealDetailsId;
	}
	public void setTreasuryDealDetailsId(BigDecimal treasuryDealDetailsId) {
		this.treasuryDealDetailsId = treasuryDealDetailsId;
	}
	
	//added koti 18/06/15 for Enquiry
	private String isActive;
	private String createBy;
	private Date createDate;
	private String view;
	private String companyName;
	private String documentDescription;
	private Date dealDate;
	private String recuterReference;
	private String contact;
	private String conculedBy;
	private String multiplicationDivision;
	private BigDecimal exchange;
	private BigDecimal standardInstruction;
	private BigDecimal bankBalance;
	private BigDecimal saleLocalAmount;
	private String standardInstructionDesc;
	
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public String getView() {
		return view;
	}
	public void setView(String view) {
		this.view = view;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getDocumentDescription() {
		return documentDescription;
	}
	public void setDocumentDescription(String documentDescription) {
		this.documentDescription = documentDescription;
	}
	public Date getDealDate() {
		return dealDate;
	}
	public void setDealDate(Date dealDate) {
		this.dealDate = dealDate;
	}
	public String getRecuterReference() {
		return recuterReference;
	}
	public void setRecuterReference(String recuterReference) {
		this.recuterReference = recuterReference;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getConculedBy() {
		return conculedBy;
	}
	public void setConculedBy(String conculedBy) {
		this.conculedBy = conculedBy;
	}
	public String getMultiplicationDivision() {
		return multiplicationDivision;
	}
	public void setMultiplicationDivision(String multiplicationDivision) {
		this.multiplicationDivision = multiplicationDivision;
	}
	public BigDecimal getExchange() {
		return exchange;
	}
	public void setExchange(BigDecimal exchange) {
		this.exchange = exchange;
	}
	public BigDecimal getStandardInstruction() {
		return standardInstruction;
	}
	public void setStandardInstruction(BigDecimal standardInstruction) {
		this.standardInstruction = standardInstruction;
	}
	public BigDecimal getBankBalance() {
		return bankBalance;
	}
	public void setBankBalance(BigDecimal bankBalance) {
		this.bankBalance = bankBalance;
	}
	public BigDecimal getSaleLocalAmount() {
		return saleLocalAmount;
	}
	public void setSaleLocalAmount(BigDecimal saleLocalAmount) {
		this.saleLocalAmount = saleLocalAmount;
	}
	public String getStandardInstructionDesc() {
		return standardInstructionDesc;
	}
	public void setStandardInstructionDesc(String standardInstructionDesc) {
		this.standardInstructionDesc = standardInstructionDesc;
	}
	
	private BigDecimal salelocalExchange;





	public BigDecimal getSalelocalExchange() {
		return salelocalExchange;
	}
	public void setSalelocalExchange(BigDecimal salelocalExchange) {
		this.salelocalExchange = salelocalExchange;
	}

private BigDecimal totalFcAmount;
private BigDecimal localSaleAmount;
private BigDecimal totalsaleAmount;









public BigDecimal getTotalsaleAmount() {
	return totalsaleAmount;
}

public void setTotalsaleAmount(BigDecimal totalsaleAmount) {
	this.totalsaleAmount = totalsaleAmount;
}

public BigDecimal getTotalFcAmount() {
	return totalFcAmount;
}
public void setTotalFcAmount(BigDecimal totalFcAmount) {
	this.totalFcAmount = totalFcAmount;
}
public BigDecimal getLocalSaleAmount() {
	return localSaleAmount;
}
public void setLocalSaleAmount(BigDecimal localSaleAmount) {
	this.localSaleAmount = localSaleAmount;
}
public String getPurchaseStandardInstruction() {
	return purchaseStandardInstruction;
}
public void setPurchaseStandardInstruction(String purchaseStandardInstruction) {
	this.purchaseStandardInstruction = purchaseStandardInstruction;
}
public String getSalesStandardInstruction() {
	return salesStandardInstruction;
}
public void setSalesStandardInstruction(String salesStandardInstruction) {
	this.salesStandardInstruction = salesStandardInstruction;
}









	

	
}
