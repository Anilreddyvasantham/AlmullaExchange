package com.amg.exchange.treasury.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.foreigncurrency.service.ForeignLocalCurrencyDenominationService;
import com.amg.exchange.foreigncurrency.service.IForeignCurrencyPurchaseService;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.remittance.service.IStandingInstructionService;
import com.amg.exchange.treasury.deal.supplier.model.DayBookDetails;
import com.amg.exchange.treasury.deal.supplier.model.DayBookHeader;
import com.amg.exchange.treasury.model.AccountBalance;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.treasury.model.TreasuryCustomerDeal;
import com.amg.exchange.treasury.model.TreasuryDealDetail;
import com.amg.exchange.treasury.model.TreasuryDealHeader;
import com.amg.exchange.treasury.model.TreasuryDealHeaderDTO;
import com.amg.exchange.treasury.service.IBankMasterService;
import com.amg.exchange.treasury.service.IFXDealSupplierService;
import com.amg.exchange.treasury.service.IFXDetailInformationService;
import com.amg.exchange.treasury.service.ISpecialCustomerDealRequestService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;
@Component("fxdealBankEnquiryBean")
@Scope("session")
@SuppressWarnings("unused")
public class FxdealBankEnquiryBean<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER=Logger.getLogger(FxdealBankEnquiryBean.class);
	private BigDecimal documentId;
	private BigDecimal documentNumber;
	private String dealNo;
	private BigDecimal documentYear;
	private BigDecimal companyId;
	private String companyName;
	private String documentDescription;

	private String contact;
	private String conculedBy;
	private String recuterReference;
	private String remarks;
	private String bank;
	private BigDecimal accountNumber;
	private String multiplicationDivision;
	private BigDecimal currencyId;
	private String currencyName;
	private BigDecimal exchange;
	private BigDecimal standardInstruction;
	private Date valueDate;
	private BigDecimal fcAmount;
	private BigDecimal scAmount;
	private BigDecimal totalFcAmount;
	private BigDecimal totalSaleAmount;
	private BigDecimal localAmount;
	private BigDecimal editaleRefId=null;
	private BigDecimal teasuryDealHeaderId;
	private BigDecimal dealWithCustomer;
	private BigDecimal company;
	private String document;
	private BigDecimal documentNo;
	private String dealReference;
	private String dealYear;
	private Date dealDate;
	private String dealWith;
	private String concludedBy;
	private String reuterReference;
	private String remark;
	private String createdByHeader;
	private Date createdDateHeader;
	private String lstCompny;
	private BigDecimal dealwithBankId;
	private String paybleAccountNumber;


	private BigDecimal purchaseDealDetId = null; // PK
	private BigDecimal purchaseTreasuryInstructionId = null; // PK
	private BigDecimal purchaseBank;
	private BigDecimal purchaseCurrency;
	private String purchaseCurrencyName;
	private BigDecimal purchaseAccountNumber;
	private Date purchaseValueDate;
	private BigDecimal purchaseExchangeRate;
	private String purchaseMultipleDivision;
	private BigDecimal purchaseInstrunction;
	private String purchaseInstructionDesc;
	//Sale Details

	private BigDecimal saleDealDetId = null; // PK
	private BigDecimal saleTreasuryInstructionId = null; // PK
	private String saleBank;
	private BigDecimal saleCurrency;
	private String saleCurrencyName;
	private String saleAccountNumber;
	private BigDecimal saleAccountNoId;
	private BigDecimal saleBalance;
	private BigDecimal saleAccountBalanceId;
	private Date saleValueDate;
	private BigDecimal saleAmount;
	private BigDecimal saleAvgRate;
	private BigDecimal saleLocalAmount;
	private BigDecimal salePurchaseInstrunction;
	private String salePurchaseInstructionDesc;
	private BigDecimal fcSaleAmount;
	private BigDecimal saleBankId;

	private Boolean booSelectbankPAcc = true;
	private Boolean multiselectPAcc = false;
	private String singlacc;
	private Boolean oneAccNoSal = true;
	private Boolean mulAccNosal = false;
	private String saleAcctNo;
	private Date date3;
	private String purchaseDate;
	private String faAccountNumberForPurchase;
	private String faAccountNumberForSale;
	private BigDecimal fcLoacalAmount;
	private BigDecimal purchaseBankAccountId;
	private String createdByDetailPurchase;
	private Date createdDateDetailPurchase;

	private Date minDate;
	private String acyymm;
	private String dealTrackValueDate;
	private BigDecimal localExchangeRate;

	private BigDecimal dayBookHeaderId;
	private BigDecimal saleExchage;
	private BigDecimal saleCurrencyId;
	private Boolean booRead;
	private String saleBankBalance;
	private String errorMessage;

	private List<BankAccountDetails> accountNumberList = new ArrayList<BankAccountDetails>();
	private List<BankApplicability> lstAllBankApplicabity = new ArrayList<BankApplicability>();
	private List<TreasuryDealHeader> lstofUnApproved = new ArrayList<TreasuryDealHeader>();




	private List<TreasuryDealInfo> treasuryDealInfoList=new ArrayList<TreasuryDealInfo>();
	private List<TreasuryDealInfo> purchaseList=new ArrayList<TreasuryDealInfo>();
	private List<TreasuryDealInfo> SaleList=new ArrayList<TreasuryDealInfo>();
	private List<CompanyMasterDesc> lstCompany = new ArrayList<CompanyMasterDesc>();
	private List<Document> lstDocument=new ArrayList<Document>();
	private Map<BigDecimal, String> lstCompanyMap = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> lstDocumentMap = new HashMap<BigDecimal, String>();
	List<PurchaseReqSplPoolDataTable> lstofSplPoolDealReq = new ArrayList<PurchaseReqSplPoolDataTable>();
	SessionStateManage sessionManage = new SessionStateManage();
	private List<UserFinancialYear> userFinancialYearList= new ArrayList<UserFinancialYear>();

	@Autowired
	IForeignCurrencyPurchaseService<T> foreignCurrencyPurchaseService;

	@Autowired
	ISpecialCustomerDealRequestService<T> iSpecialCustomerDealRequestService;
	@Autowired
	IFXDetailInformationService<T> fxDetailInformationService;

	@Autowired
	IStandingInstructionService<T> standingInstructionService;

	@Autowired
	IGeneralService<T> generalService;

	@Autowired
	IBankMasterService<T> bankMasterInfoService;


	public List<UserFinancialYear> getUserFinancialYearList() {
		List<UserFinancialYear> userFinancialYearList=null;
		try{
			userFinancialYearList=foreignCurrencyPurchaseService.getAllDocumentYear();
		}catch(NullPointerException ne){
			LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("MethodName::saveDataTableRecods");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
		}catch(Exception exception){
			LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
		}
		return userFinancialYearList;
	}

	public void setUserFinancialYearList(
			List<UserFinancialYear> userFinancialYearList) {

		this.userFinancialYearList = userFinancialYearList;
	}


	public BigDecimal getDealWithCustomer() {
		return dealWithCustomer;
	}

	public void setDealWithCustomer(BigDecimal dealWithCustomer) {
		this.dealWithCustomer = dealWithCustomer;
	}



	public BigDecimal getPurchaseBank() {
		return purchaseBank;
	}

	public void setPurchaseBank(BigDecimal purchaseBank) {
		this.purchaseBank = purchaseBank;
	}

	public BigDecimal getDealwithBankId() {
		return dealwithBankId;
	}

	public void setDealwithBankId(BigDecimal dealwithBankId) {
		this.dealwithBankId = dealwithBankId;
	}


	public String getLstCompny() {
		List<CompanyMasterDesc> data=null;
		try{
			data=generalService.getCompanyList(sessionManage.getCompanyId(), sessionManage.getLanguageId());
			setCompany(data.get(0).getFsCompanyMaster().getCompanyId());
		}catch(NullPointerException ne){
			LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("MethodName::saveDataTableRecods");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
		}catch(Exception exception){
			LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
		}
		return data.get(0).getCompanyName();
	}

	public void setLstCompny(String lstCompny) {
		this.lstCompny = lstCompny;
	}

	public List<TreasuryDealInfo> getTreasuryDealInfoList() {
		return treasuryDealInfoList;
	}

	public void setTreasuryDealInfoList(List<TreasuryDealInfo> treasuryDealInfoList) {
		this.treasuryDealInfoList = treasuryDealInfoList;
	}

	public BigDecimal getDocumentId() {
		return documentId;
	}

	public void setDocumentId(BigDecimal documentId) {
		this.documentId = documentId;
	}

	public BigDecimal getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(BigDecimal documentNumber) {
		this.documentNumber = documentNumber;
	}


	public BigDecimal getDocumentYear() {
		return documentYear;
	}

	public void setDocumentYear(BigDecimal documentYear) {
		this.documentYear = documentYear;
	}

	public BigDecimal getCompanyId() {
		return companyId;
	}

	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
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


	public BigDecimal getDayBookHeaderId() {
		return dayBookHeaderId;
	}

	public void setDayBookHeaderId(BigDecimal dayBookHeaderId) {
		this.dayBookHeaderId = dayBookHeaderId;
	}


	public BigDecimal getSaleCurrencyId() {
		return saleCurrencyId;
	}

	public void setSaleCurrencyId(BigDecimal saleCurrencyId) {
		this.saleCurrencyId = saleCurrencyId;
	}

	public BigDecimal getSaleExchage() {
		return saleExchage;
	}

	public void setSaleExchage(BigDecimal saleExchage) {
		this.saleExchage = saleExchage;
	}

	public Boolean getBooRead() {
		return booRead;
	}

	public void setBooRead(Boolean booRead) {
		this.booRead = booRead;
	}

	public String getSaleBankBalance() {
		return saleBankBalance;
	}

	public void setSaleBankBalance(String saleBankBalance) {
		this.saleBankBalance = saleBankBalance;
	}

	public BigDecimal getCompany() {
		return company;
	}

	public void setCompany(BigDecimal company) {
		this.company = company;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public BigDecimal getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}

	public String getDealYear() {
		return dealYear;
	}

	public void setDealYear(String dealYear) {
		this.dealYear = dealYear;
	}

	public Date getDealDate() {
		return dealDate;
	}

	public void setDealDate(Date dealDate) {
		this.dealDate = dealDate;
	}

	public String getDealWith() {
		return dealWith;
	}

	public void setDealWith(String dealWith) {
		this.dealWith = dealWith;
	}

	public String getConcludedBy() {
		return concludedBy;
	}

	public void setConcludedBy(String concludedBy) {
		this.concludedBy = concludedBy;
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

	public String getRecuterReference() {
		return recuterReference;
	}

	public void setRecuterReference(String recuterReference) {
		this.recuterReference = recuterReference;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public List<TreasuryDealHeader> getLstofUnApproved() {
		return lstofUnApproved;
	}

	public void setLstofUnApproved(List<TreasuryDealHeader> lstofUnApproved) {
		this.lstofUnApproved = lstofUnApproved;
	}

	public List<BankMaster> getLstMaster() {
		return generalService.getAllBankCodeFromBankMaster(getBank());
	}

	public void setLstMaster(List<BankMaster> lstMaster) {
	}

	public List<BankApplicability> getLstAllBankApplicabity() {
		return lstAllBankApplicabity;
	}

	public void setLstAllBankApplicabity(
			List<BankApplicability> lstAllBankApplicabity) {
		this.lstAllBankApplicabity = lstAllBankApplicabity;
	}

	public Boolean getBooSelectbankPAcc() {
		return booSelectbankPAcc;
	}

	public void setBooSelectbankPAcc(Boolean booSelectbankPAcc) {
		this.booSelectbankPAcc = booSelectbankPAcc;
	}

	public Boolean getMultiselectPAcc() {
		return multiselectPAcc;
	}

	public void setMultiselectPAcc(Boolean multiselectPAcc) {
		this.multiselectPAcc = multiselectPAcc;
	}

	public String getSinglacc() {
		return singlacc;
	}

	public void setSinglacc(String singlacc) {
		this.singlacc = singlacc;
	}

	public Boolean getOneAccNoSal() {
		return oneAccNoSal;
	}

	public void setOneAccNoSal(Boolean oneAccNoSal) {
		this.oneAccNoSal = oneAccNoSal;
	}

	public Boolean getMulAccNosal() {
		return mulAccNosal;
	}

	public void setMulAccNosal(Boolean mulAccNosal) {
		this.mulAccNosal = mulAccNosal;
	}

	public String getSaleAcctNo() {
		return saleAcctNo;
	}

	public void setSaleAcctNo(String saleAcctNo) {
		this.saleAcctNo = saleAcctNo;
	}

	public Date getDate3() {
		return date3;
	}

	public void setDate3(Date date3) {
		this.date3 = date3;
	}

	public String getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public String getFaAccountNumberForPurchase() {
		return faAccountNumberForPurchase;
	}

	public void setFaAccountNumberForPurchase(String faAccountNumberForPurchase) {
		this.faAccountNumberForPurchase = faAccountNumberForPurchase;
	}

	public String getFaAccountNumberForSale() {
		return faAccountNumberForSale;
	}

	public void setFaAccountNumberForSale(String faAccountNumberForSale) {
		this.faAccountNumberForSale = faAccountNumberForSale;
	}

	public BigDecimal getFcLoacalAmount() {
		return fcLoacalAmount;
	}

	public void setFcLoacalAmount(BigDecimal fcLoacalAmount) {
		this.fcLoacalAmount = fcLoacalAmount;
	}

	public List<BankAccountDetails> getAccountNumberList() {
		return accountNumberList;
	}

	public void setAccountNumberList(List<BankAccountDetails> accountNumberList) {
		this.accountNumberList = accountNumberList;
	}

	public BigDecimal getPurchaseBankAccountId() {
		return purchaseBankAccountId;
	}

	public void setPurchaseBankAccountId(BigDecimal purchaseBankAccountId) {
		this.purchaseBankAccountId = purchaseBankAccountId;
	}

	public String getCreatedByDetailPurchase() {
		return createdByDetailPurchase;
	}

	public void setCreatedByDetailPurchase(String createdByDetailPurchase) {
		this.createdByDetailPurchase = createdByDetailPurchase;
	}

	public Date getCreatedDateDetailPurchase() {
		return createdDateDetailPurchase;
	}

	public void setCreatedDateDetailPurchase(Date createdDateDetailPurchase) {
		this.createdDateDetailPurchase = createdDateDetailPurchase;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Map<BigDecimal, String> getLstCompanyMap() {
		try{
			List<CompanyMasterDesc> data=generalService.getCompanyList(sessionManage.getCompanyId(), sessionManage.getLanguageId());
			for (CompanyMasterDesc companyMasterDesc : data) {
				lstCompanyMap.put(companyMasterDesc.getFsCompanyMaster().getCompanyId(), companyMasterDesc.getCompanyName());
			}
		}catch(NullPointerException ne){
			LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("MethodName::getLstCompanyMap");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
		}catch(Exception exception){
			LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
		}
		return lstCompanyMap;
	}

	public void setLstCompanyMap(Map<BigDecimal, String> lstCompanyMap) {
		this.lstCompanyMap = lstCompanyMap;
	}

	public List<CompanyMasterDesc> getLstCompany() {
		return lstCompany;
	}

	public void setLstCompany(List<CompanyMasterDesc> lstCompany) {
		this.lstCompany = lstCompany;
	}

	public List<Document> getLstDocument() {
		return lstDocument;
	}

	public void setLstDocument(List<Document> lstDocument) {
		this.lstDocument = lstDocument;
	}

	public Date getMinDate() {
		return new Date();
	}

	public void setMinDate(Date minDate) {
		this.minDate = minDate;
	}

	public String getDealTrackValueDate() {
		return dealTrackValueDate;
	}

	public void setDealTrackValueDate(String dealTrackValueDate) {
		this.dealTrackValueDate = dealTrackValueDate;
	}

	public String getAcyymm() {
		return acyymm;
	}

	public void setAcyymm(String acyymm) {
		this.acyymm = acyymm;
	}

	public Map<BigDecimal, String> getLstDocumentMap() {
		try{
			lstDocument=generalService.getDocument(new BigDecimal(Constants.DOCUMENT_CODE_FOR_FX_DEAL_WITH_BANK),new BigDecimal(sessionManage.isExists("languageId") ? sessionManage.getSessionValue("languageId") : "1"));
			for(Document lstdoc:lstDocument)
			{
				lstDocumentMap.put(lstdoc.getDocumentID(), lstdoc.getDocumentDesc());
			}
		}catch(NullPointerException ne){
			LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("MethodName::saveDataTableRecods");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
		}catch(Exception exception){
			LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
		}
		return lstDocumentMap;
	}

	public void setLstDocumentMap(Map<BigDecimal, String> lstDocumentMap) {
		this.lstDocumentMap = lstDocumentMap;
	}



	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public BigDecimal getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(BigDecimal accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getMultiplicationDivision() {
		return multiplicationDivision;
	}

	public void setMultiplicationDivision(String multiplicationDivision) {
		this.multiplicationDivision = multiplicationDivision;
	}

	public BigDecimal getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
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


	public BigDecimal getEditaleRefId() {
		return editaleRefId;
	}

	public void setEditaleRefId(BigDecimal editaleRefId) {
		this.editaleRefId = editaleRefId;
	}


	public BigDecimal getTeasuryDealHeaderId() {
		return teasuryDealHeaderId;
	}

	public void setTeasuryDealHeaderId(BigDecimal teasuryDealHeaderId) {
		this.teasuryDealHeaderId = teasuryDealHeaderId;
	}

	public String getReuterReference() {
		return reuterReference;
	}

	public void setReuterReference(String reuterReference) {
		this.reuterReference = reuterReference;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getFcSaleAmount() {
		return fcSaleAmount;
	}

	public void setFcSaleAmount(BigDecimal fcSaleAmount) {
		this.fcSaleAmount = fcSaleAmount;
	}


	public List<TreasuryDealInfo> getPurchaseList() {
		return purchaseList;
	}

	public void setPurchaseList(List<TreasuryDealInfo> purchaseList) {
		this.purchaseList = purchaseList;
	}

	public List<TreasuryDealInfo> getSaleList() {
		return SaleList;
	}

	public void setSaleList(List<TreasuryDealInfo> saleList) {
		SaleList = saleList;
	}


	public BigDecimal getLocalExchangeRate() {
		return localExchangeRate;
	}

	public void setLocalExchangeRate(BigDecimal localExchangeRate) {
		this.localExchangeRate = localExchangeRate;
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void fxDealEnquiryNavigation(){
		if(treasuryDealInfoList != null && !treasuryDealInfoList.isEmpty()){
			treasuryDealInfoList.clear();
		}
		setDealDate(null);
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionManage.getCountryId(), sessionManage.getUserType(), sessionManage.getUserName(), "FxDealBankEnquiry.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/FxDealBankEnquiry.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public Date getValueDate() {
		return valueDate;
	}

	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}



	public BigDecimal getFcAmount() {
		return fcAmount;
	}

	public void setFcAmount(BigDecimal fcAmount) {
		this.fcAmount = fcAmount;
	}

	public BigDecimal getScAmount() {
		return scAmount;
	}

	public void setScAmount(BigDecimal scAmount) {
		this.scAmount = scAmount;
	}


	public BigDecimal getTotalFcAmount() {
		return totalFcAmount;
	}

	public void setTotalFcAmount(BigDecimal totalFcAmount) {
		this.totalFcAmount = totalFcAmount;
	}

	public BigDecimal getTotalSaleAmount() {
		return totalSaleAmount;
	}

	public void setTotalSaleAmount(BigDecimal totalSaleAmount) {
		this.totalSaleAmount = totalSaleAmount;
	}


	public BigDecimal getLocalAmount() {
		return localAmount;
	}

	public void setLocalAmount(BigDecimal localAmount) {
		this.localAmount = localAmount;
	}

	public String getDealNo() {
		return dealNo;
	}

	public void setDealNo(String dealNo) {
		this.dealNo = dealNo;
	}

	public String getDealReference() {
		return dealReference;
	}

	public void setDealReference(String dealReference) {
		this.dealReference = dealReference;
	}

	public String getCreatedByHeader() {
		return createdByHeader;
	}

	public void setCreatedByHeader(String createdByHeader) {
		this.createdByHeader = createdByHeader;
	}

	public Date getCreatedDateHeader() {
		return createdDateHeader;
	}

	public void setCreatedDateHeader(Date createdDateHeader) {
		this.createdDateHeader = createdDateHeader;
	}


	public BigDecimal getSaleBankId() {
		return saleBankId;
	}

	public void setSaleBankId(BigDecimal saleBankId) {
		this.saleBankId = saleBankId;
	}

	public BigDecimal getPurchaseDealDetId() {
		return purchaseDealDetId;
	}

	public void setPurchaseDealDetId(BigDecimal purchaseDealDetId) {
		this.purchaseDealDetId = purchaseDealDetId;
	}

	public BigDecimal getPurchaseTreasuryInstructionId() {
		return purchaseTreasuryInstructionId;
	}

	public void setPurchaseTreasuryInstructionId(
			BigDecimal purchaseTreasuryInstructionId) {
		this.purchaseTreasuryInstructionId = purchaseTreasuryInstructionId;
	}




	public BigDecimal getPurchaseCurrency() {
		return purchaseCurrency;
	}

	public void setPurchaseCurrency(BigDecimal purchaseCurrency) {
		this.purchaseCurrency = purchaseCurrency;
	}

	public String getPurchaseCurrencyName() {
		return purchaseCurrencyName;
	}

	public void setPurchaseCurrencyName(String purchaseCurrencyName) {
		this.purchaseCurrencyName = purchaseCurrencyName;
	}

	public BigDecimal getPurchaseAccountNumber() {
		return purchaseAccountNumber;
	}

	public void setPurchaseAccountNumber(BigDecimal purchaseAccountNumber) {
		this.purchaseAccountNumber = purchaseAccountNumber;
	}

	public Date getPurchaseValueDate() {
		return purchaseValueDate;
	}

	public void setPurchaseValueDate(Date purchaseValueDate) {
		this.purchaseValueDate = purchaseValueDate;
	}

	public BigDecimal getPurchaseExchangeRate() {
		return purchaseExchangeRate;
	}

	public void setPurchaseExchangeRate(BigDecimal purchaseExchangeRate) {
		this.purchaseExchangeRate = purchaseExchangeRate;
	}

	public String getPurchaseMultipleDivision() {
		return purchaseMultipleDivision;
	}

	public void setPurchaseMultipleDivision(String purchaseMultipleDivision) {
		this.purchaseMultipleDivision = purchaseMultipleDivision;
	}

	public BigDecimal getPurchaseInstrunction() {
		return purchaseInstrunction;
	}

	public void setPurchaseInstrunction(BigDecimal purchaseInstrunction) {
		this.purchaseInstrunction = purchaseInstrunction;
	}

	public String getPurchaseInstructionDesc() {
		return purchaseInstructionDesc;
	}

	public void setPurchaseInstructionDesc(String purchaseInstructionDesc) {
		this.purchaseInstructionDesc = purchaseInstructionDesc;
	}


	public BigDecimal getSaleDealDetId() {
		return saleDealDetId;
	}

	public void setSaleDealDetId(BigDecimal saleDealDetId) {
		this.saleDealDetId = saleDealDetId;
	}

	public BigDecimal getSaleTreasuryInstructionId() {
		return saleTreasuryInstructionId;
	}

	public void setSaleTreasuryInstructionId(BigDecimal saleTreasuryInstructionId) {
		this.saleTreasuryInstructionId = saleTreasuryInstructionId;
	}


	public String getSaleBank() {
		return saleBank;
	}

	public void setSaleBank(String saleBank) {
		this.saleBank = saleBank;
	}

	public BigDecimal getSaleCurrency() {
		return saleCurrency;
	}

	public void setSaleCurrency(BigDecimal saleCurrency) {
		this.saleCurrency = saleCurrency;
	}

	public String getSaleCurrencyName() {
		return saleCurrencyName;
	}

	public void setSaleCurrencyName(String saleCurrencyName) {
		this.saleCurrencyName = saleCurrencyName;
	}

	public String getSaleAccountNumber() {
		return saleAccountNumber;
	}

	public void setSaleAccountNumber(String saleAccountNumber) {
		this.saleAccountNumber = saleAccountNumber;
	}

	public BigDecimal getSaleAccountNoId() {
		return saleAccountNoId;
	}

	public void setSaleAccountNoId(BigDecimal saleAccountNoId) {
		this.saleAccountNoId = saleAccountNoId;
	}

	public BigDecimal getSaleBalance() {
		return saleBalance;
	}

	public void setSaleBalance(BigDecimal saleBalance) {
		this.saleBalance = saleBalance;
	}

	public BigDecimal getSaleAccountBalanceId() {
		return saleAccountBalanceId;
	}

	public void setSaleAccountBalanceId(BigDecimal saleAccountBalanceId) {
		this.saleAccountBalanceId = saleAccountBalanceId;
	}

	public Date getSaleValueDate() {
		return saleValueDate;
	}

	public void setSaleValueDate(Date saleValueDate) {
		this.saleValueDate = saleValueDate;
	}

	public BigDecimal getSaleAmount() {
		return saleAmount;
	}

	public void setSaleAmount(BigDecimal saleAmount) {
		this.saleAmount = saleAmount;
	}

	public BigDecimal getSaleAvgRate() {
		return saleAvgRate;
	}

	public void setSaleAvgRate(BigDecimal saleAvgRate) {
		this.saleAvgRate = saleAvgRate;
	}

	public BigDecimal getSaleLocalAmount() {
		return saleLocalAmount;
	}

	public void setSaleLocalAmount(BigDecimal saleLocalAmount) {
		this.saleLocalAmount = saleLocalAmount;
	}

	public BigDecimal getSalePurchaseInstrunction() {
		return salePurchaseInstrunction;
	}

	public void setSalePurchaseInstrunction(BigDecimal salePurchaseInstrunction) {
		this.salePurchaseInstrunction = salePurchaseInstrunction;
	}

	public String getSalePurchaseInstructionDesc() {
		return salePurchaseInstructionDesc;
	}

	public void setSalePurchaseInstructionDesc(String salePurchaseInstructionDesc) {
		this.salePurchaseInstructionDesc = salePurchaseInstructionDesc;
	}


	public String getPaybleAccountNumber() {
		return paybleAccountNumber;
	}

	public void setPaybleAccountNumber(String paybleAccountNumber) {
		this.paybleAccountNumber = paybleAccountNumber;
	}

	public void fetchDataFromDb() throws ParseException{
		LOGGER.info("Entering into fetchDataFrom Db method");
		if(treasuryDealInfoList != null && !treasuryDealInfoList.isEmpty()){
			treasuryDealInfoList.clear();
		}
		//try {
		List<TreasuryDealHeaderDTO> treasuryDealHeaderList = fxDetailInformationService.getfxdealEnqSelectDate(new SimpleDateFormat("dd/MM/yy").parse(getAcyymm()));

		//List<TreasuryDealHeaderDTO> treasuryDealHeaderList=fxDetailInformationService.getAllDetailsFromDbHeader();
		if(treasuryDealHeaderList.size()>0){

			for (TreasuryDealHeaderDTO treasuryDealDetail : treasuryDealHeaderList) {
				TreasuryDealInfo treasuryDealObj=new TreasuryDealInfo();
				//FX DEAL WITH BANK
				treasuryDealObj.setDocumentId(treasuryDealDetail.getDocumentID());
				treasuryDealObj.setTreasuryDealHeaderId(treasuryDealDetail.getTreasuryDealHeaderId());
				treasuryDealObj.setDocumentFinanceYear(treasuryDealDetail.getDocumentFinanceYear());
				//treasuryDealObj.setDealDescription(treasuryDealDetail.getExDocument().getDocumentDesc());
				if(treasuryDealDetail.getTreasuryDocumentNumber()!=null){
					treasuryDealObj.setDealNo(treasuryDealDetail.getTreasuryDocumentNumber().toPlainString());
				}
				treasuryDealObj.setCompanyid(treasuryDealDetail.getCompanyId());
				treasuryDealObj.setRecuterReference(treasuryDealDetail.getReutersReference() );
				treasuryDealObj.setDealConcludedBy(treasuryDealDetail.getConcludedBy());
				treasuryDealObj.setDealConcludedWith(treasuryDealDetail.getContactName());

				if(treasuryDealDetail.getDealWithType()!=null  && treasuryDealDetail.getDealWithType().equalsIgnoreCase("B")){
					if(treasuryDealDetail.getDealwithBankId()!=null){
						treasuryDealObj.setDealWithType(generalService.getBankName(treasuryDealDetail.getDealwithBankId()));
					}
				}
				if(treasuryDealDetail.getDealWithType()!=null && treasuryDealDetail.getDealWithType().equalsIgnoreCase("S")){
					if(treasuryDealDetail.getDealWithCustomer()!=null){
						treasuryDealObj.setDealWithType(fxDetailInformationService.getCustomerName(new BigDecimal(treasuryDealDetail.getDealWithCustomer().toPlainString())));
					}
				}
				//treasuryDealObj.setDealWith(treasuryDealDetail.getDealWithType());
				//treasuryDealObj.setRecuterReference(treasuryDealDetail.getReutersReference());
				//treasuryDealObj.setRemarks(treasuryDealDetail.getRemarks());
				//treasuryDealObj.setDealWithType(treasuryDealDetail.getDealWithType());
				treasuryDealObj.setCreateBy(treasuryDealDetail.getCreatedBy());
				treasuryDealObj.setCreateDate(treasuryDealDetail.getCreatedDate());
				if(treasuryDealDetail.getIsActive().equalsIgnoreCase(Constants.Yes)){
					treasuryDealObj.setIsActive("Approved");
				}else if (treasuryDealDetail.getIsActive().equalsIgnoreCase(Constants.U)) {
					treasuryDealObj.setIsActive("Un_Approved");
				}else if (treasuryDealDetail.getIsActive().equalsIgnoreCase(Constants.D)) {
					treasuryDealObj.setIsActive("Deleted");
				}
				treasuryDealObj.setView("View");
				treasuryDealInfoList.add(treasuryDealObj);

			}
		}else{
			RequestContext.getCurrentInstance().execute("notApproved.show();");
			if(treasuryDealInfoList != null && !treasuryDealInfoList.isEmpty()){
				treasuryDealInfoList.clear();
			}
			return;
		}
		/*} catch (ParseException e) {
				e.printStackTrace();
		}*/

	}

	public void exit(){
		LOGGER.info("Entering into exit method");
		try{
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/FxDealBankEnquiry.xhtml");
		}catch(Exception e){
			e.printStackTrace();
		}
		LOGGER.info("Exit into exit method");
	}


	public void onSelectDateSelect(SelectEvent event) {
		LOGGER.info("Entering into onSelectDateSelect method");
		try{
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
			setAcyymm(format.format(event.getObject()));
			fetchDataFromDb();
		}catch(NullPointerException ne){
			LOGGER.info("Exit into onSelectDateSelect Db method"+ne.getMessage());
			setErrorMessage("Method Name::fetchDataFrom"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		} catch (Exception exception) {
			LOGGER.info("Exit into onSelectDateSelect Db method"+exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}

	}
	//FX_DEAL_WITH_Supplier Enquiry Started

	public void fxDealSupplierNavigationEnquiryNavigation(){
		LOGGER.info("Entering into fxDealSupplierNavigationEnquiryNavigation method");
		if(treasuryDealInfoList != null && !treasuryDealInfoList.isEmpty()){
			treasuryDealInfoList.clear();
		}
		setDealDate(null);
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionManage.getCountryId(), sessionManage.getUserType(), sessionManage.getUserName(), "FxDealWithSupplierForm.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/FxDealWithSupplierForm.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("Exit into fxDealSupplierNavigationEnquiryNavigation method");
	}

	public void onSelectDate(SelectEvent event)throws Exception{
		LOGGER.info("Entering into onSelectDate method");
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
		setAcyymm(format.format(event.getObject()));
		fetchDataFromSupplierRecords();
		LOGGER.info("Exit into onSelectDate method");
	}

	public void fetchDataFromSupplierRecords(){
		LOGGER.info("Entering into fetchDataFromSupplierRecords method");
		if(treasuryDealInfoList != null && !treasuryDealInfoList.isEmpty()){
			treasuryDealInfoList.clear();
		}
		try {
			List<TreasuryDealHeaderDTO> treasuryDealHeaderList = fxDetailInformationService.getfxdealSupplierEnqSelectDate(new SimpleDateFormat("dd/MM/yy").parse(getAcyymm()));
			if(treasuryDealHeaderList.size()>0){
				for (TreasuryDealHeaderDTO treasuryDealDetail : treasuryDealHeaderList) {
					TreasuryDealInfo treasuryDealObj=new TreasuryDealInfo();
					//FX DEAL WITH BANK
					treasuryDealObj.setDocumentId(treasuryDealDetail.getDocumentID());
					treasuryDealObj.setTreasuryDealHeaderId(treasuryDealDetail.getTreasuryDealHeaderId());
					treasuryDealObj.setDocumentFinanceYear(treasuryDealDetail.getDocumentFinanceYear());
					treasuryDealObj.setDealNo(treasuryDealDetail.getTreasuryDocumentNumber().toPlainString());
					treasuryDealObj.setCompanyid(treasuryDealDetail.getCompanyId());
					treasuryDealObj.setDealConcludedBy(treasuryDealDetail.getConcludedBy());
					treasuryDealObj.setDealConcludedWith(treasuryDealDetail.getContactName());
					if(treasuryDealDetail.getDealWithType().equalsIgnoreCase("B")){
						treasuryDealObj.setDealWithType(generalService.getBankName(treasuryDealDetail.getDealwithBankId()));
					}
					if(treasuryDealDetail.getDealWithType().equalsIgnoreCase("S")){
						treasuryDealObj.setDealWithType(fxDetailInformationService.getCustomerName(new BigDecimal(treasuryDealDetail.getDealWithCustomer().toPlainString())));
					}
					treasuryDealObj.setCreateBy(treasuryDealDetail.getCreatedBy());
					treasuryDealObj.setCreateDate(treasuryDealDetail.getCreatedDate());
					if(treasuryDealDetail.getIsActive().equalsIgnoreCase(Constants.Yes)){
						treasuryDealObj.setIsActive("Approved");
					}else if (treasuryDealDetail.getIsActive().equalsIgnoreCase(Constants.U)) {
						treasuryDealObj.setIsActive("Un_Approved");
					}else if (treasuryDealDetail.getIsActive().equalsIgnoreCase(Constants.D)) {
						treasuryDealObj.setIsActive("Deleted");
					}
					treasuryDealObj.setDealWith(treasuryDealDetail.getDealWithCustomer().toString());
					treasuryDealObj.setDocumentNo(treasuryDealDetail.getTreasuryDocumentNumber());
					treasuryDealObj.setView("View");
					treasuryDealInfoList.add(treasuryDealObj);
				}
			}else{
				RequestContext.getCurrentInstance().execute("notApproved.show();");
				if(treasuryDealInfoList != null && !treasuryDealInfoList.isEmpty()){
					treasuryDealInfoList.clear();
				}
				return;
			}
		} catch(NullPointerException ne){
			LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("MethodName::fetchDataFromSupplierRecords");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
		LOGGER.info("Exit into fetchDataFromSupplierRecords method");
	}

	public String getDocumentNumberDb() throws Exception{
		LOGGER.info("Entering into getDocumentNumberDb method");
		lstDocument=generalService.getDocument(new BigDecimal(Constants.DOCUMENT_CODE_FOR_FX_DEAL_WITH_BANK),new BigDecimal(sessionManage.isExists("languageId") ? sessionManage.getSessionValue("languageId") : "1"));
		for(Document lstdoc:lstDocument)
		{
			setDocumentNo(lstdoc.getDocumentID());
			setDocument(lstdoc.getDocumentDesc());
		}
		LOGGER.info("Exit into getDocumentNumberDb method");
		return document;
	}

	public void fetchingAllRecordsFromDBEnquireSupplier(TreasuryDealInfo fetchRecord){
		LOGGER.info("Entering into fetchingAllRecordsFromDBEnquireSupplier method");
		try {
			getDocumentNumberDb();

			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/FxDealWithSupplierEnquiryForm.xhtml");
			} catch (Exception e) {
				e.printStackTrace();
			}
			List<TreasuryDealHeader> treasuryDealDetailsList = fxDetailInformationService.getHeaderDetailsList(getDocumentNo(),new BigDecimal(fetchRecord.getDealNo()),"S");
			if(treasuryDealDetailsList.size()!=0){
				setBooRead(true);
				//First Panel Details
				BigDecimal trStandInsIdPurchase= BigDecimal.ZERO;
				BigDecimal trStandInsIdSale= BigDecimal.ZERO;
				BigDecimal commomnTrDetalId=BigDecimal.ZERO;
				setTeasuryDealHeaderId(treasuryDealDetailsList.get(0).getTreasuryDealHeaderId());
				setEditaleRefId(new BigDecimal(fetchRecord.getDealNo()));
				setDealReference(getEditaleRefId().toPlainString());
				//setDocSerialIdNumberForSave(getEditaleRefId());
				setCompany(treasuryDealDetailsList.get(0).getFsCompanyMaster().getCompanyId());
				setDocument(treasuryDealDetailsList.get(0).getExDocument().getDocumentDesc());
				setDocumentNo(treasuryDealDetailsList.get(0).getExDocument().getDocumentID());
				setDealYear(treasuryDealDetailsList.get(0).getUserFinanceYear().toPlainString());
				setDealDate(treasuryDealDetailsList.get(0).getDocumentDate());
				setDealWith(treasuryDealDetailsList.get(0).getDealWithCustomer().toPlainString());
				setContact(treasuryDealDetailsList.get(0).getContactName());
				setConcludedBy(treasuryDealDetailsList.get(0).getConcludedBy());
				setReuterReference(treasuryDealDetailsList.get(0).getReutersReference());
				setRemark(treasuryDealDetailsList.get(0).getRemarks());
				setCreatedByHeader(treasuryDealDetailsList.get(0).getCreatedBy());
				setCreatedDateHeader(treasuryDealDetailsList.get(0).getCreatedDate());

				List<TreasuryDealDetail> treasuryDealDetailList = fxDetailInformationService.getDetailsPurchase(getTeasuryDealHeaderId(), Constants.PD);
				if(treasuryDealDetailList.size()!=0){
					setPurchaseBank(treasuryDealDetailList.get(0).getTreasuryDealBankMaster().getBankId());
					setBank(generalService.getBankName(treasuryDealDetailList.get(0).getTreasuryDealBankMaster().getBankId()));
					setPurchaseCurrency(treasuryDealDetailList.get(0).getTreasuryDealDetailCurrencyMaster().getCurrencyId());
					setPurchaseCurrencyName(treasuryDealDetailList.get(0).getTreasuryDealDetailCurrencyMaster().getCurrencyName());

					populateAccountNumber();
					setSinglacc(treasuryDealDetailList.get(0).getTreasuryDealDetailBankAccountDetails().getBankAcctNo());
					setPurchaseAccountNumber(new BigDecimal(treasuryDealDetailList.get(0).getFaAccountNo()));
					setPurchaseDealDetId(treasuryDealDetailList.get(0).getTreasuryDealDetailId());
					commomnTrDetalId=treasuryDealDetailList.get(0).getTreasuryDealDetailId();
					setFaAccountNumberForPurchase(treasuryDealDetailList.get(0).getFaAccountNo());
					setExchange(treasuryDealDetailList.get(0).getExchange());
					setLocalAmount(treasuryDealDetailList.get(0).getLocalExchangeRate());
					setMultiplicationDivision(treasuryDealDetailList.get(0).getMultiplicationDivision());
					setFcAmount(treasuryDealDetailList.get(0).getFcAmount());
					setValueDate(treasuryDealDetailList.get(0).getValueDate());
					setLocalExchangeRate(getFcAmount().multiply(getExchange()));
				}

				List<DayBookHeader> dayBookHeaderList=fxDetailInformationService.getAllDetailsListFromDB(getEditaleRefId()); 
				if(dayBookHeaderList.size()>0){
					setDayBookHeaderId(dayBookHeaderList.get(0).getDaybookHeaderId());
				}
				List<DayBookDetails> dayBookDetailList=fxDetailInformationService.getAllDetailsDayBookDetails(getDayBookHeaderId(),Constants.SD);
				if(dayBookDetailList.size()>0){
					setSaleCurrencyId(dayBookDetailList.get(0).getDayBookCurrencyId().getCurrencyId());
					setSaleCurrencyName(generalService.getCurrencyName(dayBookDetailList.get(0).getDayBookCurrencyId().getCurrencyId()));
					setSaleExchage(dayBookDetailList.get(0).getDayBookExchangeRate());
					setSaleAccountNumber(dayBookDetailList.get(0).getDayBookFaAccountNumber());
					setFcSaleAmount(dayBookDetailList.get(0).getDayBookFcAmount());
					setSaleLocalAmount(dayBookDetailList.get(0).getDayBookLocalAmount());
					setSaleValueDate(dayBookDetailList.get(0).getValueDate());
					List<BankAccountDetails> bankAccountDetailList=fxDetailInformationService.getAccountNumberBasedOnBank(getSaleAccountNumber());
					if(bankAccountDetailList.size()>0){
						setSaleBankId(bankAccountDetailList.get(0).getExBankMaster().getBankId());
						setSaleBank(generalService.getBankName(bankAccountDetailList.get(0).getExBankMaster().getBankId()));
						String FundGlno=bankAccountDetailList.get(0).getFundGlno();
						if(FundGlno != null){
							List<AccountBalance> accountBalanceList=fxDetailInformationService.getBankBanceBasedOnAccNO(FundGlno);
							if(accountBalanceList.size()>0){
								setSaleBalance(accountBalanceList.get(0).getLocalBalance());
								setPaybleAccountNumber(accountBalanceList.get(0).getGeneralLeaderNo());
							}
						}
					}

				}

			}

			LOGGER.info("Exit into fetchingAllRecordsFromDBEnquireSupplier method");
		} catch(NullPointerException ne){
			LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("MethodName::fetchingAllRecordsFromDBEnquireSupplier");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}

	public void populateAccountNumber() {
		try{
			LOGGER.info("Entering into populateAccountNumber method");
			List<BankAccountDetails> ptabledata = generalService.getAccountNumber(getPurchaseBank(), getPurchaseCurrency());
			if(ptabledata.size()==0)
			{
				setBooSelectbankPAcc(true);
				setMultiselectPAcc(false);
				RequestContext.getCurrentInstance().execute("dataNotFund.show();");
			}else if(ptabledata.size()==1){
				for(BankAccountDetails bankAcount :ptabledata)
				{
					setPurchaseBankAccountId(new BigDecimal(bankAcount.getFundGlno()));
					setSinglacc(bankAcount.getBankAcctNo());
					setFaAccountNumberForPurchase(bankAcount.getFundGlno());
				}
				setPurchaseAccountNumber(getPurchaseBankAccountId());
				setBooSelectbankPAcc(true);
				setMultiselectPAcc(false);
				// populateAccountBalance();
			}else{
				setPurchaseAccountNumber(null);
				setBooSelectbankPAcc(false);
				setMultiselectPAcc(true);
				setAccountNumberList(ptabledata);
			}
			LOGGER.info("Exit into populateAccountNumber method");
		}catch(NullPointerException ne){
			LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("MethodName::fetchingAllRecordsFromDBEnquireSupplier");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}

	public void exitEnq(){
		LOGGER.info("Entering into exitEnq method");
		setDealDate(null);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/FxDealWithSupplierForm.xhtml");
		} catch(Exception exception){
			LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
		LOGGER.info("Exit into exitEnq method");
	}






	public void populateSearchValue() {
		HttpSession session = sessionManage.getSession();
		TreasuryDealHeader remittanceEnquiryBean = (TreasuryDealHeader) session.getAttribute("dealEnquiryObject");
		// clearAllFields();
		if (remittanceEnquiryBean != null) {
			setAcyymm(new SimpleDateFormat("dd/MM/yy").format(remittanceEnquiryBean.getAccyymm()));
			// setDocumentYear(remittanceEnquiryBean.getDocumentFinancialYear());
			try{
				fetchDataFromDb();
			}catch(NullPointerException ne){
				LOGGER.info("Exit into onSelectDateSelect Db method"+ne.getMessage());
				setErrorMessage("Method Name::fetchDataFrom"); 
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return; 
			} catch (Exception exception) {
				LOGGER.info("Exit into onSelectDateSelect Db method"+exception.getMessage());
				RequestContext.getCurrentInstance().execute("exception.show();");
				return;
			}
			session.removeAttribute("dealEnquiryObject");
		}
	}
	@Autowired
	IFXDealSupplierService<T> fxDealSupplierService;
	@Autowired
	ForeignLocalCurrencyDenominationService<T> foreignLocalCurrencyDenominationService;
	
	JasperPrint jasperPrint;
	private String sundryDebtorReference;

	public String getSundryDebtorReference() {
		return sundryDebtorReference;
	}

	public void setSundryDebtorReference(String sundryDebtorReference) {
		this.sundryDebtorReference = sundryDebtorReference;
	}
	
	public void generateDealPaymentReport(TreasuryDealInfo fetchRecord){
		try {
			List<TreasuryDealInfo> treasuryDealInfoList=fetchDataForDealPaymentReport(fetchRecord.getDocumentNo(),fetchRecord.getDocumentFinanceYear(),new BigDecimal(fetchRecord.getDealWith()));
			dealPaymentReportInit(treasuryDealInfoList);
			HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			httpServletResponse.addHeader("Content-disposition","attachment; filename=dealpayment.pdf");

			ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint,servletOutputStream);
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception e) {
			//log.error(e.getMessage());
		}
	}

	public List<TreasuryDealInfo> fetchDataForDealPaymentReport(BigDecimal documentNumber ,BigDecimal documentFinacialYear,BigDecimal customerReference)throws Exception {

		List<TreasuryDealInfo> treasuryDealInfoList=new CopyOnWriteArrayList<TreasuryDealInfo>();
		//treasuryDealInfoList=new CopyOnWriteArrayList<TreasuryDealInfo>();
		List<TreasuryDealInfo> treasuryDealInfoList1=new CopyOnWriteArrayList<TreasuryDealInfo>();
		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath = ctx.getRealPath("//");
		//String logoPath = realPath + Constants.LOGO_PATH;
		
		String logoPath =null;
		if(sessionManage.getCountryName().equalsIgnoreCase(Constants.KUWAIT)){
			logoPath = realPath+Constants.LOGO_PATH;
		}else if(sessionManage.getCountryName().equalsIgnoreCase(Constants.OMAN)){
			logoPath = realPath+Constants.LOGO_PATH_OM;
		}else{
			logoPath =realPath+Constants.LOGO_PATH;
		}
		
		String subReportPath = realPath + Constants.SUB_REPORT_PATH;
		List<TreasuryCustomerDeal> treasuryDealInfo1 = new ArrayList<TreasuryCustomerDeal>();
		List<TreasuryCustomerDeal> saleList = new ArrayList<TreasuryCustomerDeal>();

		List<TreasuryCustomerDeal> dealTicketList = new ArrayList<TreasuryCustomerDeal>();
		
		List<TreasuryCustomerDeal> treasuryCustList = fxDetailInformationService.getTreasuryCustomerDealAndPaymentValues(sessionManage.getCompanyId(),new BigDecimal(Constants.DOCUMENT_CODE_FOR_FX_DEAL_WITH_BANK),documentFinacialYear, documentNumber);
	//	List<TreasuryCustomerDeal> treasuryCustList = fXDetailInformationService.getTreasuryCustomerDealAndPaymentValues(new BigDecimal(1),new BigDecimal(70), new BigDecimal(2015), new BigDecimal(599));
		
		for(TreasuryCustomerDeal treasuryCustDeal : treasuryCustList){
			if(treasuryCustDeal.getPaymentIndicator().trim().equalsIgnoreCase(Constants.Yes)){
				dealTicketList.add(treasuryCustDeal);
			}
		}

		for(TreasuryCustomerDeal treasuryCustDeal : dealTicketList){
			if(treasuryCustDeal.getLineType()!=null){
				if(treasuryCustDeal.getLineType().equalsIgnoreCase(Constants.PY)){
					treasuryDealInfo1.add(treasuryCustDeal);
				}else if(treasuryCustDeal.getLineType().equalsIgnoreCase(Constants.SD)){
					saleList.add(treasuryCustDeal);
				}
			}
		}
		
		TreasuryDealInfo	treasuryDealInfoPurchase = new TreasuryDealInfo();
		
		List<CompanyMasterDesc> companyDesc =	generalService.getCompanyList(sessionManage.getCompanyId(), sessionManage.getLanguageId());
	
		/*	String countryName =  igeneralService.getCountryName(sessionStateManage.getCountryId());
		//set Company Header Name
		if(countryName!=null && !countryName.equals("")){
		treasuryDealInfoPurchase.setCompanyHeaderName("ALMULLA EXCHANGE - "+countryName);
		}*/
		CompanyMasterDesc companyDesObj = companyDesc.get(0);
		
		//set company address
		if(companyDesObj!=null){
		StringBuffer companyAdd = new StringBuffer();
			if(companyDesObj.getCompanyName()!=null){
				companyAdd.append(companyDesObj.getCompanyName());
				companyAdd.append("\n");
			}
			if(companyDesObj.getFsCompanyMaster().getAddress1()!=null){
				companyAdd.append(companyDesObj.getFsCompanyMaster().getAddress1());
				companyAdd.append("\n");
			}
			if(companyDesObj.getFsCompanyMaster().getAddress2()!=null){
				companyAdd.append(companyDesObj.getFsCompanyMaster().getAddress2());
				companyAdd.append("\n");
			}
			if(companyDesObj.getFsCompanyMaster().getAddress3()!=null){
				companyAdd.append(companyDesObj.getFsCompanyMaster().getAddress3());
			}
			treasuryDealInfoPurchase.setCompanyAddress(companyAdd.toString());
		}
		
		//set company phone number
		treasuryDealInfoPurchase.setCompanyPhone(companyDesObj.getFsCompanyMaster().getTelephoneNo());
		
		//set company fax
		treasuryDealInfoPurchase.setCompanyFax(companyDesObj.getFsCompanyMaster().getFaxNo());
		
		//set company reg no
		treasuryDealInfoPurchase.setCompanyRegNo(companyDesObj.getFsCompanyMaster().getRegistrationNumber());
		
		//set company telex number
		treasuryDealInfoPurchase.setCompanyTelex(companyDesObj.getFsCompanyMaster().getTelexNo());
		
		//set company email 
		treasuryDealInfoPurchase.setCompanyEmail(companyDesObj.getFsCompanyMaster().getEmail());
		
		//set company share capital
		if(companyDesObj.getFsCompanyMaster().getCapitalAmount()!=null && companyDesObj.getFsCompanyMaster().getCurrencyId()!=null){
			 String currencyQuoteName = generalService.getCurrencyQuote(companyDesObj.getFsCompanyMaster().getCurrencyId());
			Integer d =  Integer.parseInt(companyDesObj.getFsCompanyMaster().getCapitalAmount());
			NumberFormat format = NumberFormat.getNumberInstance(new Locale("en", "in"));
			String capitalAmount = format.format(d).toString();
			
		treasuryDealInfoPurchase.setCompanyShareCaptal(currencyQuoteName+"   "+capitalAmount);
		}
		
		int i = 0;	
		for (TreasuryCustomerDeal viewTreasuryDeal : treasuryDealInfo1) {
			
			if(i==1){
				break;
			}
		
			List<Customer> clist = fxDealSupplierService.getAllCustomerList(customerReference);
			String customerName=null;
			if(clist.size()>0){
				Customer customer=	clist.get(0);
				String sundryDebtorReference=customer.getSundryDebtorReference();
				
				if(sundryDebtorReference!=null)
				{
					setSundryDebtorReference(customer.getSundryDebtorReference());
					 customerName =customer.getFirstName();
					
				}
			}
			
			treasuryDealInfoPurchase.setDealWith(customerName);
			treasuryDealInfoPurchase.setBankAddress(viewTreasuryDeal.getBankAddress());
			treasuryDealInfoPurchase.setCurrencyname(viewTreasuryDeal.getCurrencyName());
			treasuryDealInfoPurchase.setDealConcludedBy(viewTreasuryDeal.getDealConcludedBy());
			treasuryDealInfoPurchase.setDealConcludedWith(viewTreasuryDeal.getDealConcludedWith());
			treasuryDealInfoPurchase.setDealDescription(viewTreasuryDeal.getDealDescription());
			treasuryDealInfoPurchase.setDocumentDate(viewTreasuryDeal.getDocumentDate());
			treasuryDealInfoPurchase.setDocumentFinanceYear(viewTreasuryDeal.getDocumentFinanceYear());
			treasuryDealInfoPurchase.setApplicationCountryId(viewTreasuryDeal.getApplicationCountryId());
			treasuryDealInfoPurchase.setDealWithType(viewTreasuryDeal.getDealWithType());
			
			if(viewTreasuryDeal.getPaymentFinanceYear()!=null && viewTreasuryDeal.getPaymentDocumentNumber()!=null){
				treasuryDealInfoPurchase.setDealNo(viewTreasuryDeal.getPaymentFinanceYear()+" / "+viewTreasuryDeal.getPaymentDocumentNumber());
			}
			
			BigDecimal totalPurchaseFcAmount = BigDecimal.ZERO;
			if(viewTreasuryDeal.getTotalPurchaseFcAmount()!=null && viewTreasuryDeal.getCurrencyId()!=null){
				totalPurchaseFcAmount = round((viewTreasuryDeal.getTotalPurchaseFcAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(viewTreasuryDeal.getCurrencyId()));
			}
			
			treasuryDealInfoPurchase.setTotalPurchaseFcAmount(totalPurchaseFcAmount);
			treasuryDealInfoPurchase.setPurchaseExchangeRate(viewTreasuryDeal.getPurchaseExchangeRate());
			treasuryDealInfoPurchase.setFaAccountNo(viewTreasuryDeal.getFaAccountNumber());
			treasuryDealInfoPurchase.setFcAmount(viewTreasuryDeal.getFcAmount());
			treasuryDealInfoPurchase.setLocalExchangeRate(viewTreasuryDeal.getLocalExchangeRate());
			treasuryDealInfoPurchase.setLocalAmount(viewTreasuryDeal.getLocalAmount());
			treasuryDealInfoPurchase.setLineType(viewTreasuryDeal.getLineType());
			treasuryDealInfoPurchase.setValuedatePD(viewTreasuryDeal.getValueDate());
			treasuryDealInfoPurchase.setCurrencyCode(viewTreasuryDeal.getCurrencyCode());
			treasuryDealInfoPurchase.setLogoPath(logoPath);
			treasuryDealInfoPurchase.setRemarks(viewTreasuryDeal.getRemarks());
			
			List<Employee> empList = generalService.getEmployeeDetail(sessionManage.getEmployeeId());
			if(empList.size()>0){
				treasuryDealInfoPurchase.setAuthorizedBy(empList.get(0).getEmployeeName());
			}
			treasuryDealInfoPurchase.setDealer(viewTreasuryDeal.getDealConcludedBy());
			
			if(viewTreasuryDeal.getAccountNumber()!=null){
			treasuryDealInfoPurchase.setPdAcNo(viewTreasuryDeal.getAccountNumber());
			}else{
				treasuryDealInfoPurchase.setPdAcNo("");
			}
			if(viewTreasuryDeal.getBankCode()!=null){
				treasuryDealInfoPurchase.setPdBankCode(viewTreasuryDeal.getBankCode());
			}else{
				treasuryDealInfoPurchase.setPdBankCode("");
			}
			treasuryDealInfoPurchase.setFaxNo("1");
			BigDecimal denominationId = generalService.getDenomiationId(Constants.KUWAIT_QUOTE_NAME);
			BigDecimal totalFaAmount = BigDecimal.ZERO;
			
			if(viewTreasuryDeal.getTotalPurchaseFcAmount()!=null && viewTreasuryDeal.getLocalExchangeRate()!=null && denominationId!=null){
				totalFaAmount = round((viewTreasuryDeal.getTotalPurchaseFcAmount().multiply(viewTreasuryDeal.getLocalExchangeRate())),denominationId.intValue());
			}
			
			treasuryDealInfoPurchase.setTotalFaAmount(totalFaAmount);
			treasuryDealInfoPurchase.setTotalPdAmount(totalFaAmount);
			
		//	treasuryDealInfoPurchase.setPurchaseStandardInstruction(viewTreasuryDeal.getStandardInstruction());
			
			treasuryDealInfoPurchase.setPurchaseCurrencyCode(generalService.getCurrencyQuote(viewTreasuryDeal.getCurrencyId()));

			for (TreasuryCustomerDeal viewTreasuryDeal1 : saleList) {
				treasuryDealInfoPurchase.setCurrencynameSD(viewTreasuryDeal1.getCurrencyName());
				treasuryDealInfoPurchase.setSdBankCode(viewTreasuryDeal1.getBankCode());
				treasuryDealInfoPurchase.setSdAcNo(viewTreasuryDeal1.getAccountNumber());
				treasuryDealInfoPurchase.setSoldAccountNo(viewTreasuryDeal1.getFaAccountNumber());
			//	BigDecimal saleAmount=round((viewTreasuryDeal1.getSaleAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(viewTreasuryDeal1.getCurrencyId()));
				treasuryDealInfoPurchase.setSaleAmount(viewTreasuryDeal1.getSaleAmount());
				treasuryDealInfoPurchase.setSaleExchangeRate(viewTreasuryDeal1.getLocalExchangeRate());
				BigDecimal totalSoldAmount=round((viewTreasuryDeal1.getSaleAmount().multiply(viewTreasuryDeal1.getLocalExchangeRate())),denominationId.intValue());
				treasuryDealInfoPurchase.setTotalSoldAmount(totalSoldAmount);
				treasuryDealInfoPurchase.setSaleCurrencyCode(generalService.getCurrencyQuote(viewTreasuryDeal1.getCurrencyId()));
				treasuryDealInfoPurchase.setValuedateSD(viewTreasuryDeal1.getValueDate());
			//	treasuryDealInfoPurchase.setSalesStandardInstruction(viewTreasuryDeal1.getStandardInstruction());
			}
			treasuryDealInfoPurchase.setSubReportPath(subReportPath);
			treasuryDealInfoList1.add(treasuryDealInfoPurchase);
			treasuryDealInfoPurchase.setTreasuryDealInfoList(treasuryDealInfoList1);
			
			treasuryDealInfoList.add(treasuryDealInfoPurchase);
			i++;
		}
		return treasuryDealInfoList;
	}
	public static BigDecimal round(BigDecimal bd, int places) {
		if (places < 0) throw new IllegalArgumentException();

		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd;
	}
	public void dealPaymentReportInit(List<TreasuryDealInfo> treasuryDealInfoList) throws JRException{
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(treasuryDealInfoList);
		String reportPath = FacesContext.getCurrentInstance() .getExternalContext().getRealPath("reports/design/dealpayment.jasper");
		jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap(), beanCollectionDataSource);
	}
	
	public void generateDealTicketReport(TreasuryDealInfo fetchRecord){
		try {
			List<TreasuryDealInfo> treasuryDealInfoList=fetchDataForDealTicketReport(fetchRecord.getDocumentNo(),fetchRecord.getDocumentFinanceYear(),new BigDecimal(fetchRecord.getDealWith()));
			dealTicketReportInit(treasuryDealInfoList);
			HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			httpServletResponse.addHeader("Content-disposition","attachment; filename=dealticket.pdf");

			ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint,servletOutputStream);
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception e) {
			//log.error(e.getMessage());
		}
	}
	
	
	public List<TreasuryDealInfo> fetchDataForDealTicketReport(BigDecimal documentNumber ,BigDecimal documentFinacialYear,BigDecimal customerReference) throws Exception {

		List<TreasuryDealInfo> treasuryDealInfoList=new CopyOnWriteArrayList<TreasuryDealInfo>();
		List<TreasuryDealInfo> treasuryDealInfoList1=new CopyOnWriteArrayList<TreasuryDealInfo>();
		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath = ctx.getRealPath("//");
		//String logoPath = realPath + Constants.LOGO_PATH;
		
		String logoPath =null;
		if(sessionManage.getCountryName().equalsIgnoreCase(Constants.KUWAIT)){
			logoPath = realPath+Constants.LOGO_PATH;
		}else if(sessionManage.getCountryName().equalsIgnoreCase(Constants.OMAN)){
			logoPath = realPath+Constants.LOGO_PATH_OM;
		}else{
			logoPath =realPath+Constants.LOGO_PATH;
		}
		
		String subReportPath = realPath + Constants.SUB_REPORT_PATH;
		List<TreasuryCustomerDeal> treasuryDealInfo1 = new ArrayList<TreasuryCustomerDeal>();
		List<TreasuryCustomerDeal> saleList = new ArrayList<TreasuryCustomerDeal>();

		List<TreasuryCustomerDeal> dealTicketList = new ArrayList<TreasuryCustomerDeal>();
		
		List<TreasuryCustomerDeal> treasuryCustList = fxDetailInformationService.getTreasuryCustomerDealAndPaymentValues(sessionManage.getCompanyId(),new BigDecimal(Constants.DOCUMENT_CODE_FOR_FX_DEAL_WITH_BANK),documentFinacialYear, documentNumber);
	//	List<TreasuryCustomerDeal> treasuryCustList = fXDetailInformationService.getTreasuryCustomerDealAndPaymentValues(new BigDecimal(1),new BigDecimal(70), new BigDecimal(2015), new BigDecimal(599));
		
		for(TreasuryCustomerDeal treasuryCustDeal : treasuryCustList){
			if(treasuryCustDeal.getPaymentIndicator().trim().equalsIgnoreCase(Constants.No)){
				dealTicketList.add(treasuryCustDeal);
			}
		}

		for(TreasuryCustomerDeal treasuryCustDeal : dealTicketList){
			if(treasuryCustDeal.getLineType()!=null){
				if(treasuryCustDeal.getLineType().equalsIgnoreCase(Constants.PY)){
					treasuryDealInfo1.add(treasuryCustDeal);
				}else if(treasuryCustDeal.getLineType().equalsIgnoreCase(Constants.PD)){
					saleList.add(treasuryCustDeal);
				}
			}
		}
		
		TreasuryDealInfo	treasuryDealInfoPurchase = new TreasuryDealInfo();
		
		List<CompanyMasterDesc> companyDesc =	generalService.getCompanyList(sessionManage.getCompanyId(), sessionManage.getLanguageId());
	
		/*	String countryName =  igeneralService.getCountryName(sessionStateManage.getCountryId());
		//set Company Header Name
		if(countryName!=null && !countryName.equals("")){
		treasuryDealInfoPurchase.setCompanyHeaderName("ALMULLA EXCHANGE - "+countryName);
		}*/
		CompanyMasterDesc companyDesObj = companyDesc.get(0);
		
		//set company address
		if(companyDesObj!=null){
		StringBuffer companyAdd = new StringBuffer();
			if(companyDesObj.getCompanyName()!=null){
				companyAdd.append(companyDesObj.getCompanyName());
				companyAdd.append("\n");
			}
			if(companyDesObj.getFsCompanyMaster().getAddress1()!=null){
				companyAdd.append(companyDesObj.getFsCompanyMaster().getAddress1());
				companyAdd.append("\n");
			}
			if(companyDesObj.getFsCompanyMaster().getAddress2()!=null){
				companyAdd.append(companyDesObj.getFsCompanyMaster().getAddress2());
				companyAdd.append("\n");
			}
			if(companyDesObj.getFsCompanyMaster().getAddress3()!=null){
				companyAdd.append(companyDesObj.getFsCompanyMaster().getAddress3());
			}
			treasuryDealInfoPurchase.setCompanyAddress(companyAdd.toString());
		}
		
		//set company phone number
		treasuryDealInfoPurchase.setCompanyPhone(companyDesObj.getFsCompanyMaster().getTelephoneNo());
		
		//set company fax
		treasuryDealInfoPurchase.setCompanyFax(companyDesObj.getFsCompanyMaster().getFaxNo());
		
		//set company reg no
		treasuryDealInfoPurchase.setCompanyRegNo(companyDesObj.getFsCompanyMaster().getRegistrationNumber());
		
		//set company telex number
		treasuryDealInfoPurchase.setCompanyTelex(companyDesObj.getFsCompanyMaster().getTelexNo());
		
		//set company email 
		treasuryDealInfoPurchase.setCompanyEmail(companyDesObj.getFsCompanyMaster().getEmail());
		
		//set company share capital
		if(companyDesObj.getFsCompanyMaster().getCapitalAmount()!=null && companyDesObj.getFsCompanyMaster().getCurrencyId()!=null){
			 String currencyQuoteName = generalService.getCurrencyQuote(companyDesObj.getFsCompanyMaster().getCurrencyId());
			Integer d =  Integer.parseInt(companyDesObj.getFsCompanyMaster().getCapitalAmount());
			NumberFormat format = NumberFormat.getNumberInstance(new Locale("en", "in"));
			String capitalAmount = format.format(d).toString();
			
		treasuryDealInfoPurchase.setCompanyShareCaptal(currencyQuoteName+"   "+capitalAmount);
		}
		
		int i = 0;	
		for (TreasuryCustomerDeal viewTreasuryDeal : treasuryDealInfo1) {
			
			if(i==1){
				break;
			}
		
			
			List<Customer> clist = fxDealSupplierService .getAllCustomerList(customerReference);
			String customerName=null;
			if(clist.size()>0){
				Customer customer=	clist.get(0);
				String sundryDebtorReference=customer.getSundryDebtorReference();
				
				if(sundryDebtorReference!=null)
				{
					setSundryDebtorReference(customer.getSundryDebtorReference());
					 customerName =customer.getFirstName();
					
				}
			}
			
			treasuryDealInfoPurchase.setDealWith(customerName);
			treasuryDealInfoPurchase.setBankAddress(viewTreasuryDeal.getBankAddress());
			treasuryDealInfoPurchase.setCurrencyname(viewTreasuryDeal.getCurrencyName());
			treasuryDealInfoPurchase.setDealConcludedBy(viewTreasuryDeal.getDealConcludedBy());
			treasuryDealInfoPurchase.setDealConcludedWith(viewTreasuryDeal.getDealConcludedWith());
			treasuryDealInfoPurchase.setDealDescription(viewTreasuryDeal.getDealDescription());
			treasuryDealInfoPurchase.setDocumentDate(viewTreasuryDeal.getDocumentDate());
			treasuryDealInfoPurchase.setDocumentFinanceYear(viewTreasuryDeal.getDocumentFinanceYear());
			treasuryDealInfoPurchase.setApplicationCountryId(viewTreasuryDeal.getApplicationCountryId());
			treasuryDealInfoPurchase.setDealWithType(viewTreasuryDeal.getDealWithType());
			treasuryDealInfoPurchase.setDealNo(viewTreasuryDeal.getDealNo());
			
			BigDecimal totalPurchaseFcAmount = BigDecimal.ZERO;
			if(viewTreasuryDeal.getTotalPurchaseFcAmount()!=null && viewTreasuryDeal.getCurrencyId()!=null){
				totalPurchaseFcAmount = round((viewTreasuryDeal.getTotalPurchaseFcAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(viewTreasuryDeal.getCurrencyId()));
			}
			
			treasuryDealInfoPurchase.setTotalPurchaseFcAmount(totalPurchaseFcAmount);
			treasuryDealInfoPurchase.setPurchaseExchangeRate(viewTreasuryDeal.getPurchaseExchangeRate());
			treasuryDealInfoPurchase.setFaAccountNo(viewTreasuryDeal.getFaAccountNumber());
			treasuryDealInfoPurchase.setFcAmount(viewTreasuryDeal.getFcAmount());
			treasuryDealInfoPurchase.setLocalExchangeRate(viewTreasuryDeal.getLocalExchangeRate());
			treasuryDealInfoPurchase.setLocalAmount(viewTreasuryDeal.getLocalAmount());
			treasuryDealInfoPurchase.setLineType(viewTreasuryDeal.getLineType());
			treasuryDealInfoPurchase.setValuedatePD(viewTreasuryDeal.getValueDate());
			treasuryDealInfoPurchase.setCurrencyCode(viewTreasuryDeal.getCurrencyCode());
			treasuryDealInfoPurchase.setLogoPath(logoPath);
			treasuryDealInfoPurchase.setRemarks(viewTreasuryDeal.getRemarks());
			
			List<Employee> empList = generalService.getEmployeeDetail(sessionManage.getEmployeeId());
			if(empList.size()>0){
				treasuryDealInfoPurchase.setAuthorizedBy(empList.get(0).getEmployeeName());
			}
			treasuryDealInfoPurchase.setDealer(viewTreasuryDeal.getDealConcludedBy());
			
			if(viewTreasuryDeal.getAccountNumber()!=null){
			treasuryDealInfoPurchase.setPdAcNo(viewTreasuryDeal.getAccountNumber());
			}else{
				treasuryDealInfoPurchase.setPdAcNo("");
			}
			if(viewTreasuryDeal.getBankCode()!=null){
				treasuryDealInfoPurchase.setPdBankCode(viewTreasuryDeal.getBankCode());
			}else{
				treasuryDealInfoPurchase.setPdBankCode("");
			}
			treasuryDealInfoPurchase.setFaxNo("1");
			BigDecimal denominationId = generalService.getDenomiationId(Constants.KUWAIT_QUOTE_NAME);
			BigDecimal totalFaAmount = BigDecimal.ZERO;
			
			if(viewTreasuryDeal.getTotalPurchaseFcAmount()!=null && viewTreasuryDeal.getLocalExchangeRate()!=null && denominationId!=null){
				totalFaAmount = round((viewTreasuryDeal.getTotalPurchaseFcAmount().multiply(viewTreasuryDeal.getLocalExchangeRate())),denominationId.intValue());
			}
			
			treasuryDealInfoPurchase.setTotalFaAmount(totalFaAmount);
			treasuryDealInfoPurchase.setTotalPdAmount(totalFaAmount);
			
		//	treasuryDealInfoPurchase.setPurchaseStandardInstruction(viewTreasuryDeal.getStandardInstruction());
			
			treasuryDealInfoPurchase.setPurchaseCurrencyCode(generalService.getCurrencyQuote(viewTreasuryDeal.getCurrencyId()));

			for (TreasuryCustomerDeal viewTreasuryDeal1 : saleList) {
				treasuryDealInfoPurchase.setCurrencynameSD(viewTreasuryDeal1.getCurrencyName());
				treasuryDealInfoPurchase.setSdBankCode(viewTreasuryDeal1.getBankCode());
				treasuryDealInfoPurchase.setSdAcNo(viewTreasuryDeal1.getAccountNumber());
				treasuryDealInfoPurchase.setSoldAccountNo(viewTreasuryDeal1.getFaAccountNumber());
			//	BigDecimal saleAmount=round((viewTreasuryDeal1.getSaleAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(viewTreasuryDeal1.getCurrencyId()));
				treasuryDealInfoPurchase.setSaleAmount(viewTreasuryDeal1.getSaleAmount());
				treasuryDealInfoPurchase.setSaleExchangeRate(viewTreasuryDeal1.getLocalExchangeRate());
				BigDecimal totalSoldAmount=round((viewTreasuryDeal1.getSaleAmount().multiply(viewTreasuryDeal1.getLocalExchangeRate())),denominationId.intValue());
				treasuryDealInfoPurchase.setTotalSoldAmount(totalSoldAmount);
				treasuryDealInfoPurchase.setSaleCurrencyCode(generalService.getCurrencyQuote(viewTreasuryDeal1.getCurrencyId()));
				treasuryDealInfoPurchase.setValuedateSD(viewTreasuryDeal1.getValueDate());
			//	treasuryDealInfoPurchase.setSalesStandardInstruction(viewTreasuryDeal1.getStandardInstruction());
			}
			treasuryDealInfoPurchase.setSubReportPath(subReportPath);
			treasuryDealInfoList1.add(treasuryDealInfoPurchase);
			treasuryDealInfoPurchase.setTreasuryDealInfoList(treasuryDealInfoList1);
			
			treasuryDealInfoList.add(treasuryDealInfoPurchase);
			i++;
		}

		return treasuryDealInfoList;
	}
	
	public void dealTicketReportInit(List<TreasuryDealInfo> treasuryDealInfoList) throws JRException {
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(treasuryDealInfoList);
		String reportPath = FacesContext.getCurrentInstance() .getExternalContext().getRealPath("reports/design/dealticket.jasper");
		jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap(), beanCollectionDataSource);
	}
}
