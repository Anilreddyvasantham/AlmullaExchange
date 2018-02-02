package com.amg.exchange.treasury.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BankTransferInfo {
	
	
	private String documentDate;
	private String documentNo;
	private String branchName;
	
	//to bank Properties
	private String toBankDeatails;
	private String toBankExchangeAndAmountDetails;
	private String toFaFundAccountNo;
	private String toTotalAmount;
	private String totalAmountInWords;

	//from bank Properties
	private String fromBankDeatails;
	private String fromBankExchangeAndAmountDetails;
	private String fromFaFundAccountNo;
	private String fromTotalAmount;
	
	private List<BankTransferInfo> toTransactionList;
	
	private List<BankTransferInfo> toFaxTransactionList;
	private String contactMsg;
	private String faxNo;
	private String standardInstructionDel;
	
	private Boolean waterMarkCheck =false;
	private String waterMarkLogoPath;
	
	
	
	
	//for fax Report 
	private String bankDetails;
	private String debitAccountDetails;
	
	
	
	private String companyPhone;
	private String companyFax;
	private String companyRegNo;
	private String companyTelex;
	private String companyEmail;
	private String companyShareCaptal;
	private String companyAddress;
	private String companyName;
	private String fromBankAddress;
	private BigDecimal lineNumber;
	private String toBankAmount;
	private String toBankAmountInWords;
	private String toBankAddress;
	private String companyHeaderName;
	
	
	
	public String getWaterMarkLogoPath() {
		return waterMarkLogoPath;
	}
	public void setWaterMarkLogoPath(String waterMarkLogoPath) {
		this.waterMarkLogoPath = waterMarkLogoPath;
	}
	public Boolean getWaterMarkCheck() {
		return waterMarkCheck;
	}
	public void setWaterMarkCheck(Boolean waterMarkCheck) {
		this.waterMarkCheck = waterMarkCheck;
	}
	public String getStandardInstructionDel() {
		return standardInstructionDel;
	}
	public void setStandardInstructionDel(String standardInstructionDel) {
		this.standardInstructionDel = standardInstructionDel;
	}
	public List<BankTransferInfo> getToFaxTransactionList() {
		return toFaxTransactionList;
	}
	public String getContactMsg() {
		return contactMsg;
	}
	public String getFaxNo() {
		return faxNo;
	}
	public void setToFaxTransactionList(List<BankTransferInfo> toFaxTransactionList) {
		this.toFaxTransactionList = toFaxTransactionList;
	}
	public void setContactMsg(String contactMsg) {
		this.contactMsg = contactMsg;
	}
	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
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
	public String getCompanyName() {
		return companyName;
	}
	public String getFromBankAddress() {
		return fromBankAddress;
	}
	public BigDecimal getLineNumber() {
		return lineNumber;
	}
	public String getToBankAmount() {
		return toBankAmount;
	}
	public String getToBankAmountInWords() {
		return toBankAmountInWords;
	}
	public String getToBankAddress() {
		return toBankAddress;
	}
	public String getCompanyHeaderName() {
		return companyHeaderName;
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
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public void setFromBankAddress(String fromBankAddress) {
		this.fromBankAddress = fromBankAddress;
	}
	public void setLineNumber(BigDecimal lineNumber) {
		this.lineNumber = lineNumber;
	}
	public void setToBankAmount(String toBankAmount) {
		this.toBankAmount = toBankAmount;
	}
	public void setToBankAmountInWords(String toBankAmountInWords) {
		this.toBankAmountInWords = toBankAmountInWords;
	}
	public void setToBankAddress(String toBankAddress) {
		this.toBankAddress = toBankAddress;
	}
	public void setCompanyHeaderName(String companyHeaderName) {
		this.companyHeaderName = companyHeaderName;
	}
	public String getBankDetails() {
		return bankDetails;
	}
	public String getDebitAccountDetails() {
		return debitAccountDetails;
	}
	public void setBankDetails(String bankDetails) {
		this.bankDetails = bankDetails;
	}
	public void setDebitAccountDetails(String debitAccountDetails) {
		this.debitAccountDetails = debitAccountDetails;
	}
	public String getBranchName() {
		return branchName;
	}
	public String getToBankDeatails() {
		return toBankDeatails;
	}
	public String getToBankExchangeAndAmountDetails() {
		return toBankExchangeAndAmountDetails;
	}
	public String getToFaFundAccountNo() {
		return toFaFundAccountNo;
	}
	public String getToTotalAmount() {
		return toTotalAmount;
	}
	public String getTotalAmountInWords() {
		return totalAmountInWords;
	}
	public String getFromBankDeatails() {
		return fromBankDeatails;
	}
	public String getFromBankExchangeAndAmountDetails() {
		return fromBankExchangeAndAmountDetails;
	}
	public String getFromFaFundAccountNo() {
		return fromFaFundAccountNo;
	}
	public String getFromTotalAmount() {
		return fromTotalAmount;
	}
	public List<BankTransferInfo> getToTransactionList() {
		return toTransactionList;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public void setToBankDeatails(String toBankDeatails) {
		this.toBankDeatails = toBankDeatails;
	}
	public void setToBankExchangeAndAmountDetails(
			String toBankExchangeAndAmountDetails) {
		this.toBankExchangeAndAmountDetails = toBankExchangeAndAmountDetails;
	}
	public void setToFaFundAccountNo(String toFaFundAccountNo) {
		this.toFaFundAccountNo = toFaFundAccountNo;
	}
	public void setToTotalAmount(String toTotalAmount) {
		this.toTotalAmount = toTotalAmount;
	}
	public void setTotalAmountInWords(String totalAmountInWords) {
		this.totalAmountInWords = totalAmountInWords;
	}
	public void setFromBankDeatails(String fromBankDeatails) {
		this.fromBankDeatails = fromBankDeatails;
	}
	public void setFromBankExchangeAndAmountDetails(
			String fromBankExchangeAndAmountDetails) {
		this.fromBankExchangeAndAmountDetails = fromBankExchangeAndAmountDetails;
	}
	public void setFromFaFundAccountNo(String fromFaFundAccountNo) {
		this.fromFaFundAccountNo = fromFaFundAccountNo;
	}
	public void setFromTotalAmount(String fromTotalAmount) {
		this.fromTotalAmount = fromTotalAmount;
	}
	public void setToTransactionList(List<BankTransferInfo> toTransactionList) {
		this.toTransactionList = toTransactionList;
	}
	
	
	
	
	
	
	
////////////////////////////////////////////////////////////////////////////// BANK MASTER REPORT  ////////////////////////////////////////////////////////////////	
	private BigDecimal treasuryDealHeaderId;
	private BigDecimal applicationCountryId;
	private BigDecimal companyid;
	private BigDecimal documentFinanceYear;
	private BigDecimal documentId;

	private String dealNo;
	
	private String dealWith;
	private String bankAddress;
	private BigDecimal TotalPurchaseFcAmount;
	private Date valuedate;
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
	
	private String transferedFrom;
	
	private String wordAmount;
	
	private String bankCode;
	private BigDecimal bankAccountNumber;

	
	
	private BigDecimal btAmount;
	private BigDecimal btRate;
	
	
	private List<BankTransferInfo> saleList=new ArrayList<BankTransferInfo>();
	
	private String currencynameSD;
	
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
	public String getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}
	public String getDealNo() {
		return dealNo;
	}
	public void setDealNo(String dealNo) {
		this.dealNo = dealNo;
	}
	public String getDocumentDate() {
		return documentDate;
	}
	public void setDocumentDate(String documentDate) {
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
	public Date getValuedate() {
		return valuedate;
	}
	public void setValuedate(Date valuedate) {
		this.valuedate = valuedate;
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
	public List<BankTransferInfo> getSaleList() {
		return saleList;
	}
	public void setSaleList(List<BankTransferInfo> saleList) {
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
	public String getTransferedFrom() {
		return transferedFrom;
	}
	public void setTransferedFrom(String transferedFrom) {
		this.transferedFrom = transferedFrom;
	}
	public String getWordAmount() {
		return wordAmount;
	}
	public void setWordAmount(String wordAmount) {
		this.wordAmount = wordAmount;
	}
	public BigDecimal getBtAmount() {
		return btAmount;
	}
	public void setBtAmount(BigDecimal btAmount) {
		this.btAmount = btAmount;
	}
	public BigDecimal getBtRate() {
		return btRate;
	}
	public void setBtRate(BigDecimal btRate) {
		this.btRate = btRate;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public BigDecimal getBankAccountNumber() {
		return bankAccountNumber;
	}
	public void setBankAccountNumber(BigDecimal bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}
	
	
	
}
