package com.amg.exchange.enquiry.bean;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.beneficiary.bean.BeneficaryTransactionBean;
import com.amg.exchange.beneficiary.model.BankBranchView;
import com.amg.exchange.cancelreissue.model.ViewRemittanceInquiryTransaction;
import com.amg.exchange.common.bean.RuleEngine;
import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CutomerDetailsView;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.foreigncurrency.model.CollectDetail;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.foreigncurrency.service.ForeignLocalCurrencyDenominationService;
import com.amg.exchange.foreigncurrency.service.IForeignCurrencyPurchaseService;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.registration.service.IBranchPageService;
import com.amg.exchange.remittance.bean.CustomerInquiryDataTable;
import com.amg.exchange.remittance.bean.PurposeOfRemittanceReportBean;
import com.amg.exchange.remittance.bean.RemittanceReceiptSubreport;
import com.amg.exchange.remittance.bean.RemittanceReportBean;
import com.amg.exchange.remittance.model.CollectionDetailView;
import com.amg.exchange.remittance.model.CollectionPaymentDetailsView;
import com.amg.exchange.remittance.model.PaymentModeDesc;
import com.amg.exchange.remittance.model.PurposeOfRemittanceView;
import com.amg.exchange.remittance.model.RemittanceApplication;
import com.amg.exchange.remittance.model.RemittanceApplicationView;
import com.amg.exchange.remittance.model.ViewBankDetails;
import com.amg.exchange.remittance.service.ICustomerBankService;
import com.amg.exchange.remittance.service.IPersonalRemittanceService;
import com.amg.exchange.remittance.service.IRelationsTypeService;
import com.amg.exchange.stoppayment.model.RemittanceComplaint;
import com.amg.exchange.stoppayment.service.IStopPaymentService;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.service.DeliveryModeService;
import com.amg.exchange.treasury.service.IBankBranchDetailsService;
import com.amg.exchange.treasury.service.IRemittanceModeService;
import com.amg.exchange.treasury.service.ISpecialCustomerDealRequestService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.GetRound;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("remittanceInquiryBean")
@Scope("session")
public class RemittanceInquiryBean<T> {

	private static final Logger LOGGER = Logger.getLogger(RemittanceInquiryBean.class);
	private String aliasFirstName;
	private String aliasSecondName;
	private String aliasThirdName;
	private String aliasFourthName;
	private BigDecimal docYear;
	private BigDecimal docNumber;
	private BigDecimal customerReference;
	private String firstName;
	private String middleName;
	private String lastName;
	private String gender;

	private String dob;
	private BigDecimal nationalityId;
	private String nationality;
	private String mobile;
	private String beneficiaryName;
	private String benenationality;
	private String benefirstName;
	private String beneSecondName;
	private String beneThirdName;
	private String beneFourthName;
	private String beneBankName;
	private String beneBankBranchName;
	private String beneficiaryAccountNumber;
	private String beneCountry;
	private String beneType;
	private String employmentType;

	private String branchName;
	private String correspondingCountryName;
	private String service;

	private BigDecimal transferNo;
	private BigDecimal applicationNo;
	private BigDecimal applicationyear;
	private Date documentDate;
	private String foreignCurrencyName;
	private String localCurrencyName;
	private String localChargeCurrencName;
	private String localDelivaryCurrencName;
	private String localNetCurrencName;
	private BigDecimal foreignCurrencyAmount;
	private BigDecimal localTransactionAmount;
	private BigDecimal localChargeAmount;
	private BigDecimal localDelivaryAmount;
	private BigDecimal localNetTransactionAmount;
	private String exchangeApplied;
	private String debitAccountNo;
	private String transactionStatus;
	private Date transactionlastUpdated;
	private String remittenceMode;
	private String delivaryMode;
	private String webServiceStatus;
	private String bankIndicatorName;

	private String mtcNo;
	private BigDecimal mtcyear;
	private Date remitcreateddate;
	private String countrybranchName;

	private String westionUnion;
	private String hvt;
	private String bli;
	private String dealyear;

	// Added by subramanian 
	private String collectionMode;
	private String localCommissionCurrencyName;
	private BigDecimal localCommissionAmount;
	private String paymentMode;
	private BigDecimal paymentId;
	private BigDecimal bankId;
	private String bankName;
	private Boolean renderForRemittanceBranchWiseEnquiry=false;

	private BigDecimal documentNo;
	private Boolean duplicateReportCheck=false;

	//NEW VARIABLES
	private String customerName;
	private String nickName;
	private String civilId;
	private String passportNo;
	private String crNo;
	private String gccNumber;
	private String mobileNo;
	private String ecNumber;
	private BigDecimal batchNo;
	private BigDecimal receiptNo;
	private BigDecimal  receipYear;
	private BigDecimal loyaltyPoints;
	private BigDecimal telephoneNo;
	//Routing Details

	private String corspBank;
	private String product;
	private String payableAt;
	private String routingAddress;
	private String routingValidity;
	private String accountPayee;

	//Beneficiary Bank Details
	private String bankCode;
	private String beneBank;
	private String beneBranchCode;
	private String beneBranchName;
	private String beneName;
	private String payTo;
	private String instruction;
	private String beneAddress;
	private String cashierName;
	private String helpDesk;
	private String zipCode;
	private  String beneTelePhone;
	private String agentCode;
	private String interMediary;
	private String beneBankDetails;
	private String swift;

	//Finance
	private BigDecimal saleAmount;
	private BigDecimal purchaseAmount;
	private BigDecimal exchangeRate;
	private BigDecimal comissionAmount;
	private BigDecimal chargesAmount;
	private BigDecimal deliveryAmount;
	private BigDecimal netAmount;
	private  String quoteName;
	
	// Hight Value Trnx  FCurrency  back  
	private Boolean renderForHIGHVALUETrnxForFC=false;
	

	private List<CollectDetail> collectList=new ArrayList<CollectDetail>();
	List<ReceiptEnquiryDataTable> paymentDataTableList=new ArrayList<ReceiptEnquiryDataTable>();

	//STATUS

	private String agentFileRef;
	private String bankFileRef;
	private String agentFileDate;
	private String  bankFileDate;
	private String bankAgent;
	private String bankRef;
	private String processDate;
	private String courierName;
	private String courierDate;
	private String trackingNo;
	private String deliveryDate;
	private String receivedByPrbStatus;
	private String remarks;
	private String bankRemarks;
	private String docStatus;
	private String taggedDate;
	private String taggedStatus;
	private String taggedBy;
	private  BigDecimal stopPaymentYear;
	private BigDecimal stopPaymentNo;
	private String stopPaymentDate;
	private BigDecimal canceledYear;
	private BigDecimal canceledNo;
	private String canceledDate;
	private String complaintStatus;
	private String actionDate;
	private String highValueTrnxAuth;
	private String highValueTrnxPending;
	private String highvalueAuthDate;
	private String creator;
	private Date createdDate;
	private String modifier;
	private  Date modifiedDate;
	private String debitAcDateForExchange;
	private  String paymntDateForCust;
	//Miscellaneous
	private BigDecimal totalAmount;

	//AML Auth
	private String sourceOfIncome;
	private String blackListIndicator;
	private String blackListCleared;
	private String blackListRemarks;
	private String blackListDate;
	private String blackListClearedBy;
	private String  awaitingAMLClearence;
	private String aceAMLStatus;
	private String highValueTrnxAuthForLocal;
	private String highValueTrnxPendingForLocal;
	private String highvalueAuthDateForLocal;

	//Signature
	private String signatureSpecimen = null; 

	//Deal Info
	private BigDecimal averageRate;
	private  Date dealDate;
	List<ReceiptEnquiryDataTable> listCust=new ArrayList<ReceiptEnquiryDataTable>();
	private List<ViewBankDetails> chequebankMasterList = new ArrayList<ViewBankDetails>();
	private Boolean renderPanel;
	private Boolean exitButtonRender=true;
	private Boolean backButtonRender=false;
	private BigDecimal collectionDocNo;
	private String errorMsg;
	private Boolean renderForReceiptEnquiry=false;
	

	// Hight Value Trnx back 
	private Boolean renderForHIGHVALUETrnx=false;
	@Autowired
	ApplicationContext appContext;



	public String getHighValueTrnxAuthForLocal() {
		return highValueTrnxAuthForLocal;
	}
	public void setHighValueTrnxAuthForLocal(String highValueTrnxAuthForLocal) {
		this.highValueTrnxAuthForLocal = highValueTrnxAuthForLocal;
	}

	public String getHighValueTrnxPendingForLocal() {
		return highValueTrnxPendingForLocal;
	}
	public void setHighValueTrnxPendingForLocal(String highValueTrnxPendingForLocal) {
		this.highValueTrnxPendingForLocal = highValueTrnxPendingForLocal;
	}

	public String getHighvalueAuthDateForLocal() {
		return highvalueAuthDateForLocal;
	}
	public void setHighvalueAuthDateForLocal(String highvalueAuthDateForLocal) {
		this.highvalueAuthDateForLocal = highvalueAuthDateForLocal;
	}

	public List<ViewBankDetails> getChequebankMasterList() {
		return chequebankMasterList;
	}
	public void setChequebankMasterList(List<ViewBankDetails> chequebankMasterList) {
		this.chequebankMasterList = chequebankMasterList;
	}

	public Boolean getRenderForRemittanceBranchWiseEnquiry() {
		return renderForRemittanceBranchWiseEnquiry;
	}
	public void setRenderForRemittanceBranchWiseEnquiry(Boolean renderForRemittanceBranchWiseEnquiry) {
		this.renderForRemittanceBranchWiseEnquiry = renderForRemittanceBranchWiseEnquiry;
	}

	public String getMtcNo() {
		return mtcNo;
	}
	public void setMtcNo(String mtcNo) {
		this.mtcNo = mtcNo;
	}

	public BigDecimal getMtcyear() {
		return mtcyear;
	}
	public void setMtcyear(BigDecimal mtcyear) {
		this.mtcyear = mtcyear;
	}

	public Date getRemitcreateddate() {
		return remitcreateddate;
	}
	public void setRemitcreateddate(Date remitcreateddate) {
		this.remitcreateddate = remitcreateddate;
	}

	public String getCountrybranchName() {
		return countrybranchName;
	}
	public void setCountrybranchName(String countrybranchName) {
		this.countrybranchName = countrybranchName;
	}

	public String getBankIndicatorName() {
		return bankIndicatorName;
	}
	public void setBankIndicatorName(String bankIndicatorName) {
		this.bankIndicatorName = bankIndicatorName;
	}

	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public List<ReceiptEnquiryDataTable> getListCust() {
		return listCust;
	}
	public void setListCust(List<ReceiptEnquiryDataTable> listCust) {
		this.listCust = listCust;
	}

	public BigDecimal getAverageRate() {
		return averageRate;
	}
	public void setAverageRate(BigDecimal averageRate) {
		this.averageRate = averageRate;
	}

	public Date getDealDate() {
		return dealDate;
	}
	public void setDealDate(Date dealDate) {
		this.dealDate = dealDate;
	}

	public String getSignatureSpecimen() {
		return signatureSpecimen;
	}
	public void setSignatureSpecimen(String signatureSpecimen) {
		this.signatureSpecimen = signatureSpecimen;
	}

	public String getSourceOfIncome() {
		return sourceOfIncome;
	}
	public void setSourceOfIncome(String sourceOfIncome) {
		this.sourceOfIncome = sourceOfIncome;
	}

	public String getBlackListIndicator() {
		return blackListIndicator;
	}
	public void setBlackListIndicator(String blackListIndicator) {
		this.blackListIndicator = blackListIndicator;
	}

	public String getBlackListCleared() {
		return blackListCleared;
	}
	public void setBlackListCleared(String blackListCleared) {
		this.blackListCleared = blackListCleared;
	}

	public String getBlackListRemarks() {
		return blackListRemarks;
	}
	public void setBlackListRemarks(String blackListRemarks) {
		this.blackListRemarks = blackListRemarks;
	}

	public String getBlackListDate() {
		return blackListDate;
	}
	public void setBlackListDate(String blackListDate) {
		this.blackListDate = blackListDate;
	}

	public String getBlackListClearedBy() {
		return blackListClearedBy;
	}
	public void setBlackListClearedBy(String blackListClearedBy) {
		this.blackListClearedBy = blackListClearedBy;
	}

	public String getAwaitingAMLClearence() {
		return awaitingAMLClearence;
	}
	public void setAwaitingAMLClearence(String awaitingAMLClearence) {
		this.awaitingAMLClearence = awaitingAMLClearence;
	}

	public String getAceAMLStatus() {
		return aceAMLStatus;
	}
	public void setAceAMLStatus(String aceAMLStatus) {
		this.aceAMLStatus = aceAMLStatus;
	}

	public BigDecimal getTelephoneNo() {
		return telephoneNo;
	}
	public void setTelephoneNo(BigDecimal telephoneNo) {
		this.telephoneNo = telephoneNo;
	}

	public String getQuoteName() {
		return quoteName;
	}
	public void setQuoteName( String quoteName) {
		this.quoteName = quoteName;
	}

	public String getHighvalueAuthDate() {
		return highvalueAuthDate;
	}
	public void setHighvalueAuthDate(String highvalueAuthDate) {
		this.highvalueAuthDate = highvalueAuthDate;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getAgentFileRef() {
		return agentFileRef;
	}
	public void setAgentFileRef(String agentFileRef) {
		this.agentFileRef = agentFileRef;
	}

	public String getBankFileRef() {
		return bankFileRef;
	}
	public void setBankFileRef(String bankFileRef) {
		this.bankFileRef = bankFileRef;
	}

	public String getAgentFileDate() {
		return agentFileDate;
	}
	public void setAgentFileDate(String agentFileDate) {
		this.agentFileDate = agentFileDate;
	}

	public String getBankFileDate() {
		return bankFileDate;
	}
	public void setBankFileDate(String bankFileDate) {
		this.bankFileDate = bankFileDate;
	}

	public String getBankAgent() {
		return bankAgent;
	}
	public void setBankAgent(String bankAgent) {
		this.bankAgent = bankAgent;
	}

	public String getBankRef() {
		return bankRef;
	}
	public void setBankRef(String bankRef) {
		this.bankRef = bankRef;
	}

	public String getProcessDate() {
		return processDate;
	}
	public void setProcessDate(String processDate) {
		this.processDate = processDate;
	}

	public String getCourierName() {
		return courierName;
	}
	public void setCourierName(String courierName) {
		this.courierName = courierName;
	}

	public String getCourierDate() {
		return courierDate;
	}
	public void setCourierDate(String courierDate) {
		this.courierDate = courierDate;
	}

	public String getTrackingNo() {
		return trackingNo;
	}
	public void setTrackingNo(String trackingNo) {
		this.trackingNo = trackingNo;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getReceivedByPrbStatus() {
		return receivedByPrbStatus;
	}
	public void setReceivedByPrbStatus(String receivedByPrbStatus) {
		this.receivedByPrbStatus = receivedByPrbStatus;
	}

	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getBankRemarks() {
		return bankRemarks;
	}
	public void setBankRemarks(String bankRemarks) {
		this.bankRemarks = bankRemarks;
	}

	public String getDocStatus() {
		return docStatus;
	}
	public void setDocStatus(String docStatus) {
		this.docStatus = docStatus;
	}

	public String getTaggedDate() {
		return taggedDate;
	}
	public void setTaggedDate(String taggedDate) {
		this.taggedDate = taggedDate;
	}

	public String getTaggedStatus() {
		return taggedStatus;
	}
	public void setTaggedStatus(String taggedStatus) {
		this.taggedStatus = taggedStatus;
	}

	public String getTaggedBy() {
		return taggedBy;
	}
	public void setTaggedBy(String taggedBy) {
		this.taggedBy = taggedBy;
	}
	public BigDecimal getStopPaymentYear() {
		return stopPaymentYear;
	}
	public void setStopPaymentYear(BigDecimal stopPaymentYear) {
		this.stopPaymentYear = stopPaymentYear;
	}
	public BigDecimal getStopPaymentNo() {
		return stopPaymentNo;
	}
	public void setStopPaymentNo(BigDecimal stopPaymentNo) {
		this.stopPaymentNo = stopPaymentNo;
	}
	public String getStopPaymentDate() {
		return stopPaymentDate;
	}
	public void setStopPaymentDate(String stopPaymentDate) {
		this.stopPaymentDate = stopPaymentDate;
	}
	public BigDecimal getCanceledYear() {
		return canceledYear;
	}
	public void setCanceledYear(BigDecimal canceledYear) {
		this.canceledYear = canceledYear;
	}
	public BigDecimal getCanceledNo() {
		return canceledNo;
	}
	public void setCanceledNo(BigDecimal canceledNo) {
		this.canceledNo = canceledNo;
	}
	public String getCanceledDate() {
		return canceledDate;
	}
	public void setCanceledDate(String canceledDate) {
		this.canceledDate = canceledDate;
	}
	public String getComplaintStatus() {
		return complaintStatus;
	}
	public void setComplaintStatus(String complaintStatus) {
		this.complaintStatus = complaintStatus;
	}
	public String getActionDate() {
		return actionDate;
	}
	public void setActionDate(String actionDate) {
		this.actionDate = actionDate;
	}
	public String getHighValueTrnxAuth() {
		return highValueTrnxAuth;
	}
	public void setHighValueTrnxAuth(String highValueTrnxAuth) {
		this.highValueTrnxAuth = highValueTrnxAuth;
	}
	public String getHighValueTrnxPending() {
		return highValueTrnxPending;
	}
	public void setHighValueTrnxPending(String highValueTrnxPending) {
		this.highValueTrnxPending = highValueTrnxPending;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getModifier() {
		return modifier;
	}
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getDebitAcDateForExchange() {
		return debitAcDateForExchange;
	}
	public void setDebitAcDateForExchange(String debitAcDateForExchange) {
		this.debitAcDateForExchange = debitAcDateForExchange;
	}
	public String getPaymntDateForCust() {
		return paymntDateForCust;
	}
	public void setPaymntDateForCust(String paymntDateForCust) {
		this.paymntDateForCust = paymntDateForCust;
	}
	public IStopPaymentService<T> getStopPaymentService() {
		return stopPaymentService;
	}
	public void setStopPaymentService(IStopPaymentService<T> stopPaymentService) {
		this.stopPaymentService = stopPaymentService;
	}
	public IGeneralService<T> getGeneralService() {
		return generalService;
	}
	public void setGeneralService(IGeneralService<T> generalService) {
		this.generalService = generalService;
	}
	public static Logger getLogger() {
		return LOGGER;
	}

	public List<ReceiptEnquiryDataTable> getPaymentDataTableList() {
		return paymentDataTableList;
	}
	public void setPaymentDataTableList(
			List<ReceiptEnquiryDataTable> paymentDataTableList) {
		this.paymentDataTableList = paymentDataTableList;
	}
	public List<CollectDetail> getCollectList() {
		return collectList;
	}
	public void setCollectList(List<CollectDetail> collectList) {
		this.collectList = collectList;
	}
	public BigDecimal getSaleAmount() {
		return saleAmount;
	}
	public void setSaleAmount(BigDecimal saleAmount) {
		this.saleAmount = saleAmount;
	}
	public BigDecimal getPurchaseAmount() {
		return purchaseAmount;
	}
	public void setPurchaseAmount(BigDecimal purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
	}
	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	public BigDecimal getComissionAmount() {
		return comissionAmount;
	}
	public void setComissionAmount(BigDecimal comissionAmount) {
		this.comissionAmount = comissionAmount;
	}
	public BigDecimal getChargesAmount() {
		return chargesAmount;
	}
	public void setChargesAmount(BigDecimal chargesAmount) {
		this.chargesAmount = chargesAmount;
	}
	public BigDecimal getDeliveryAmount() {
		return deliveryAmount;
	}
	public void setDeliveryAmount(BigDecimal deliveryAmount) {
		this.deliveryAmount = deliveryAmount;
	}
	public BigDecimal getNetAmount() {
		return netAmount;
	}
	public void setNetAmount(BigDecimal netAmount) {
		this.netAmount = netAmount;
	}

	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getBeneBank() {
		return beneBank;
	}
	public void setBeneBank(String beneBank) {
		this.beneBank = beneBank;
	}
	public String getBeneBranchCode() {
		return beneBranchCode;
	}
	public void setBeneBranchCode(String beneBranchCode) {
		this.beneBranchCode = beneBranchCode;
	}
	public String getBeneBranchName() {
		return beneBranchName;
	}
	public void setBeneBranchName(String beneBranchName) {
		this.beneBranchName = beneBranchName;
	}
	public String getBeneName() {
		return beneName;
	}
	public void setBeneName(String beneName) {
		this.beneName = beneName;
	}
	public String getPayTo() {
		return payTo;
	}
	public void setPayTo(String payTo) {
		this.payTo = payTo;
	}
	public String getInstruction() {
		return instruction;
	}
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
	public String getBeneAddress() {
		return beneAddress;
	}
	public void setBeneAddress(String beneAddress) {
		this.beneAddress = beneAddress;
	}
	public String getCashierName() {
		return cashierName;
	}
	public void setCashierName(String cashierName) {
		this.cashierName = cashierName;
	}
	public String getHelpDesk() {
		return helpDesk;
	}
	public void setHelpDesk(String helpDesk) {
		this.helpDesk = helpDesk;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public  String getBeneTelePhone() {
		return beneTelePhone;
	}
	public void setBeneTelePhone( String beneTelePhone) {
		this.beneTelePhone = beneTelePhone;
	}
	public String getAgentCode() {
		return agentCode;
	}
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}
	public String getInterMediary() {
		return interMediary;
	}
	public void setInterMediary(String interMediary) {
		this.interMediary = interMediary;
	}
	public String getBeneBankDetails() {
		return beneBankDetails;
	}
	public void setBeneBankDetails(String beneBankDetails) {
		this.beneBankDetails = beneBankDetails;
	}
	public String getSwift() {
		return swift;
	}
	public void setSwift(String swift) {
		this.swift = swift;
	}

	public String getCorspBank() {
		return corspBank;
	}
	public void setCorspBank(String corspBank) {
		this.corspBank = corspBank;
	}
	public String getPayableAt() {
		return payableAt;
	}
	public void setPayableAt(String payableAt) {
		this.payableAt = payableAt;
	}
	public String getRoutingAddress() {
		return routingAddress;
	}
	public void setRoutingAddress(String routingAddress) {
		this.routingAddress = routingAddress;
	}
	public String getRoutingValidity() {
		return routingValidity;
	}
	public void setRoutingValidity(String routingValidity) {
		this.routingValidity = routingValidity;
	}
	public String getAccountPayee() {
		return accountPayee;
	}
	public void setAccountPayee(String accountPayee) {
		this.accountPayee = accountPayee;
	}

	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getCivilId() {
		return civilId;
	}
	public void setCivilId(String civilId) {
		this.civilId = civilId;
	}
	public String getPassportNo() {
		return passportNo;
	}
	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}
	public String getCrNo() {
		return crNo;
	}
	public void setCrNo(String crNo) {
		this.crNo = crNo;
	}	
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getEcNumber() {
		return ecNumber;
	}
	public void setEcNumber(String ecNumber) {
		this.ecNumber = ecNumber;
	}
	public BigDecimal getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(BigDecimal batchNo) {
		this.batchNo = batchNo;
	}
	public BigDecimal getReceiptNo() {
		return receiptNo;
	}
	public void setReceiptNo(BigDecimal receiptNo) {
		this.receiptNo = receiptNo;
	}
	public BigDecimal getReceipYear() {
		return receipYear;
	}
	public void setReceipYear(BigDecimal receipYear) {
		this.receipYear = receipYear;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	private SessionStateManage session = new SessionStateManage();
	private List<UserFinancialYear> userFinancialYearList = new ArrayList<UserFinancialYear>();
	
	@Autowired
	IStopPaymentService<T> stopPaymentService;
	@Autowired
	IGeneralService<T> generalService;
	@Autowired
	IBranchPageService<T> iBranchPageService;
	@Autowired
	IRelationsTypeService relationsTypeService;
	@Autowired
	IRemittanceModeService remittanceModeService;
	@Autowired
	DeliveryModeService deliveryModeService;
	@Autowired
	IForeignCurrencyPurchaseService<T> iForeignCurrencyPurchaseService;
	@Autowired
	ICustomerBankService icustomerBankService;
	@Autowired
	IPersonalRemittanceService iPersonalRemittanceService;
	@Autowired
	ISpecialCustomerDealRequestService<T> specialCustomerDealRequestService;
	@Autowired
	ForeignLocalCurrencyDenominationService<T> foreignLocalCurrencyDenominationService;


	public Boolean getDuplicateReportCheck() {
		return duplicateReportCheck;
	}
	public void setDuplicateReportCheck(Boolean duplicateReportCheck) {
		this.duplicateReportCheck = duplicateReportCheck;
	}
	public List<UserFinancialYear> getUserFinancialYearList() {
		userFinancialYearList = iForeignCurrencyPurchaseService.getAllDocumentYear();
		return userFinancialYearList;
	}


	public BigDecimal getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}
	public void setUserFinancialYearList(List<UserFinancialYear> userFinancialYearList) {
		this.userFinancialYearList = userFinancialYearList;
	}

	public String getDealyear() {
		return dealyear;
	}

	public void setDealyear(String dealyear) {
		this.dealyear = dealyear;
	}

	public Date getDocumentDate() {
		return documentDate;
	}

	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getCorrespondingCountryName() {
		return correspondingCountryName;
	}

	public void setCorrespondingCountryName(String correspondingCountryName) {
		this.correspondingCountryName = correspondingCountryName;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public BigDecimal getTransferNo() {
		return transferNo;
	}

	public void setTransferNo(BigDecimal transferNo) {
		this.transferNo = transferNo;
	}

	public BigDecimal getApplicationNo() {
		return applicationNo;
	}
	public void setApplicationNo(BigDecimal applicationNo) {
		this.applicationNo = applicationNo;
	}

	public BigDecimal getApplicationyear() {
		return applicationyear;
	}
	public void setApplicationyear(BigDecimal applicationyear) {
		this.applicationyear = applicationyear;
	}

	public String getForeignCurrencyName() {
		return foreignCurrencyName;
	}
	public void setForeignCurrencyName(String foreignCurrencyName) {
		this.foreignCurrencyName = foreignCurrencyName;
	}

	public String getLocalCurrencyName() {
		return localCurrencyName;
	}
	public void setLocalCurrencyName(String localCurrencyName) {
		this.localCurrencyName = localCurrencyName;
	}

	public String getLocalChargeCurrencName() {
		return localChargeCurrencName;
	}

	public void setLocalChargeCurrencName(String localChargeCurrencName) {
		this.localChargeCurrencName = localChargeCurrencName;
	}

	public String getLocalDelivaryCurrencName() {
		return localDelivaryCurrencName;
	}

	public void setLocalDelivaryCurrencName(String localDelivaryCurrencName) {
		this.localDelivaryCurrencName = localDelivaryCurrencName;
	}

	public String getLocalNetCurrencName() {
		return localNetCurrencName;
	}

	public void setLocalNetCurrencName(String localNetCurrencName) {
		this.localNetCurrencName = localNetCurrencName;
	}

	public BigDecimal getForeignCurrencyAmount() {
		return foreignCurrencyAmount;
	}

	public void setForeignCurrencyAmount(BigDecimal foreignCurrencyAmount) {
		this.foreignCurrencyAmount = foreignCurrencyAmount;
	}

	public BigDecimal getLocalTransactionAmount() {
		return localTransactionAmount;
	}

	public void setLocalTransactionAmount(BigDecimal localTransactionAmount) {
		this.localTransactionAmount = localTransactionAmount;
	}

	public BigDecimal getLocalChargeAmount() {
		return localChargeAmount;
	}

	public void setLocalChargeAmount(BigDecimal localChargeAmount) {
		this.localChargeAmount = localChargeAmount;
	}

	public BigDecimal getLocalDelivaryAmount() {
		return localDelivaryAmount;
	}

	public void setLocalDelivaryAmount(BigDecimal localDelivaryAmount) {
		this.localDelivaryAmount = localDelivaryAmount;
	}

	public BigDecimal getLocalNetTransactionAmount() {
		return localNetTransactionAmount;
	}

	public void setLocalNetTransactionAmount(BigDecimal localNetTransactionAmount) {
		this.localNetTransactionAmount = localNetTransactionAmount;
	}

	public String getExchangeApplied() {
		return exchangeApplied;
	}

	public void setExchangeApplied(String exchangeApplied) {
		this.exchangeApplied = exchangeApplied;
	}

	public String getDebitAccountNo() {
		return debitAccountNo;
	}

	public void setDebitAccountNo(String debitAccountNo) {
		this.debitAccountNo = debitAccountNo;
	}

	public String getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public Date getTransactionlastUpdated() {
		return transactionlastUpdated;
	}

	public void setTransactionlastUpdated(Date transactionlastUpdated) {
		this.transactionlastUpdated = transactionlastUpdated;
	}

	public String getRemittenceMode() {
		return remittenceMode;
	}

	public void setRemittenceMode(String remittenceMode) {
		this.remittenceMode = remittenceMode;
	}

	public String getDelivaryMode() {
		return delivaryMode;
	}

	public void setDelivaryMode(String delivaryMode) {
		this.delivaryMode = delivaryMode;
	}

	public String getWebServiceStatus() {
		return webServiceStatus;
	}

	public void setWebServiceStatus(String webServiceStatus) {
		this.webServiceStatus = webServiceStatus;
	}

	public String getWestionUnion() {
		return westionUnion;
	}

	public void setWestionUnion(String westionUnion) {
		this.westionUnion = westionUnion;
	}

	public String getHvt() {
		return hvt;
	}

	public void setHvt(String hvt) {
		this.hvt = hvt;
	}

	public String getBli() {
		return bli;
	}

	public void setBli(String bli) {
		this.bli = bli;
	}

	public String getBenenationality() {
		return benenationality;
	}

	public void setBenenationality(String benenationality) {
		this.benenationality = benenationality;
	}

	public String getBeneCountry() {
		return beneCountry;
	}

	public void setBeneCountry(String beneCountry) {
		this.beneCountry = beneCountry;
	}

	public String getBeneType() {
		return beneType;
	}

	public void setBeneType(String beneType) {
		this.beneType = beneType;
	}

	public String getEmploymentType() {
		return employmentType;
	}

	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
	}

	public String getBeneficiaryName() {
		return beneficiaryName;
	}

	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	public String getBenefirstName() {
		return benefirstName;
	}

	public void setBenefirstName(String benefirstName) {
		this.benefirstName = benefirstName;
	}

	public String getBeneSecondName() {
		return beneSecondName;
	}

	public void setBeneSecondName(String beneSecondName) {
		this.beneSecondName = beneSecondName;
	}

	public String getBeneThirdName() {
		return beneThirdName;
	}

	public void setBeneThirdName(String beneThirdName) {
		this.beneThirdName = beneThirdName;
	}

	public String getBeneFourthName() {
		return beneFourthName;
	}

	public void setBeneFourthName(String beneFourthName) {
		this.beneFourthName = beneFourthName;
	}

	public String getBeneBankName() {
		return beneBankName;
	}

	public void setBeneBankName(String beneBankName) {
		this.beneBankName = beneBankName;
	}

	public String getBeneBankBranchName() {
		return beneBankBranchName;
	}

	public void setBeneBankBranchName(String beneBankBranchName) {
		this.beneBankBranchName = beneBankBranchName;
	}

	public String getBeneficiaryAccountNumber() {
		return beneficiaryAccountNumber;
	}

	public void setBeneficiaryAccountNumber(String beneficiaryAccountNumber) {
		this.beneficiaryAccountNumber = beneficiaryAccountNumber;
	}

	public BigDecimal getCustomerReference() {
		return customerReference;
	}

	public void setCustomerReference(BigDecimal customerReference) {
		this.customerReference = customerReference;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public BigDecimal getLoyaltyPoints() {
		return loyaltyPoints;
	}

	public void setLoyaltyPoints(BigDecimal loyaltyPoints) {
		this.loyaltyPoints = loyaltyPoints;
	}

	public BigDecimal getNationalityId() {
		return nationalityId;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public void setNationalityId(BigDecimal nationalityId) {
		this.nationalityId = nationalityId;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public BigDecimal getDocNumber() {
		return docNumber;
	}

	public void setDocNumber(BigDecimal docNumber) {
		this.docNumber = docNumber;
	}

	public Boolean getRenderPanel() {
		return renderPanel;
	}

	public void setRenderPanel(Boolean renderPanel) {
		this.renderPanel = renderPanel;
	}

	public BigDecimal getDocYear() {
		return docYear;
	}

	public void setDocYear(BigDecimal docYear) {
		this.docYear = docYear;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getAliasFirstName() {
		return aliasFirstName;
	}

	public void setAliasFirstName(String aliasFirstName) {
		this.aliasFirstName = aliasFirstName;
	}

	public String getAliasSecondName() {
		return aliasSecondName;
	}

	public void setAliasSecondName(String aliasSecondName) {
		this.aliasSecondName = aliasSecondName;
	}

	public String getAliasThirdName() {
		return aliasThirdName;
	}

	public void setAliasThirdName(String aliasThirdName) {
		this.aliasThirdName = aliasThirdName;
	}

	public String getAliasFourthName() {
		return aliasFourthName;
	}

	public void setAliasFourthName(String aliasFourthName) {
		this.aliasFourthName = aliasFourthName;
	}


	// Add By Subramanian 


	public BigDecimal getBankId() {
		return bankId;
	}

	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getPaymentMode() {
		return paymentMode;
	}



	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	public String getCollectionMode() {
		return collectionMode;
	}
	public void setCollectionMode(String collectionMode) {
		this.collectionMode = collectionMode;
	}

	public BigDecimal getLocalCommissionAmount() {
		return localCommissionAmount;
	}

	public void setLocalCommissionAmount(BigDecimal localCommissionAmount) {
		this.localCommissionAmount = localCommissionAmount;
	}


	public BigDecimal getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(BigDecimal paymentId) {
		this.paymentId = paymentId;
	}

	public String getLocalCommissionCurrencyName() {
		return localCommissionCurrencyName;
	}

	public void setLocalCommissionCurrencyName(String localCommissionCurrencyName) {
		this.localCommissionCurrencyName = localCommissionCurrencyName;
	}

	@Autowired
	RuleEngine<T> ruleEngine;
	//End By subramanian
	private Map<BigDecimal, String> idTypeMap = new HashMap<BigDecimal, String>();

	public void getFetchIdTypeList() {

		idTypeMap = ruleEngine.getComponentData("Identity Type");
	}

	public void clearAll(){

		setApplicationNo(null);
		setApplicationyear(null);
		setMtcNo(null);
		setMtcyear(null);
		setRemitcreateddate(null);
		setCountrybranchName(null);

		setCustomerName( null);
		setCivilId(null);
		setPassportNo(null);
		setMobileNo( null);
		setTelephoneNo( null);
		setCustomerReference( null);
		setLoyaltyPoints( null);
		setNickName( null);
		setCrNo(null);
		setGccNumber(null);
		setReceiptNo( null);
		setReceipYear(null);

		//Routing Details
		setCorspBank( null);
		setPayableAt( null);
		setProduct( null);
		setRoutingAddress(null);
		setCorrespondingCountryName(null);
		setCorresponingBankBranchId(null);
		setCorresponingCityName(null);
		setCorresponingDistName(null);
		setCorresponingDrBranchId(null);
		setCorresponingIfscCode(null);
		setCorresponingRoutingId(null);
		setCorresponingStateName(null);
		setCorresponingSwiftCode(null);
		//Bene Bank Details
		setBeneAddress( null);
		setBeneBankBranchName( null);
		setBeneBranchCode( null);
		setBeneBank( null);
		setBeneBranchName( null);
		setBankCode( null);
		setHelpDesk( null);
		setCashierName( null);
		setBeneTelePhone( null);
		setZipCode( null);
		setBeneName( null);
		setInstruction( null);
		setInterMediary( null);
		setBeneBankDetails( null);
		setPayTo( null);
		setAgentCode( null);
		setSwift(null);
		setBeneficiaryCountryName(null);
		setBeneficiaryCityName(null);
		setBeneficiaryDistName(null);
		setBeneficiaryDrBranchId(null);
		setBeneficiaryIfscCode(null);
		setBeneficiaryRoutingId(null);
		setBeneficiaryStateName(null);
		setBankIndicatorName(null);
		setBankAgent(null);
		setReceivedByPrbStatus(null);


		//Finance
		setSaleAmount( null);
		setPurchaseAmount( null);
		setExchangeRate(null);
		setQuoteName( null);
		setComissionAmount( null);
		setDeliveryAmount( null);
		setChargesAmount( null);
		setNetAmount( null);
		paymentDataTableList.clear();

		//STATUS

		setBankRef(null );
		setAgentFileRef( null);
		setAgentFileDate(null );
		setBankFileRef(null );
		setBankFileDate(null );
		setHighValueTrnxAuth(null );
		setHighValueTrnxPending(null);
		setHighvalueAuthDate(null);
		setHighValueTrnxAuthForLocal(null);
		setHighValueTrnxPendingForLocal(null);
		setHighvalueAuthDateForLocal(null);
		setCanceledNo( null);
		setCanceledYear( null);
		setCanceledDate(null );
		setStopPaymentNo(null );
		setStopPaymentDate( null);
		setStopPaymentYear(null);
		setComplaintStatus( null);
		setActionDate(null );
		setCreator(null );
		setCreatedDate( null);
		setModifiedDate(null );
		setModifier(null ); 
		//Miscellaneous
		setTotalAmount( null);
		// AML_Auth
		setSourceOfIncome( null);
		setBlackListCleared( null);
		setBlackListDate( null);
		setBlackListRemarks( null);
		setBlackListClearedBy( null);
		setAceAMLStatus( null);
		setAwaitingAMLClearence( null);
		setBlackListIndicator( null);
		//Signature
		setSignatureSpecimen( null);
		//DEAL Info
		setAverageRate( null);
		setDealDate( null);
		listCust.clear();

	}
	
	

	// high value trnx back action
		public void goBackToHighValueTrnxForFcBackButton(){
			try {
				setRenderForHIGHVALUETrnxForFC(false);
				clearAll();
				setDocNumber(null);
				setReceiptNo(null);
				setDocYear(null);
				FacesContext.getCurrentInstance().getExternalContext().redirect("../common/highValueAmlAuthFCurreny.xhtml");
			}catch(Exception exception){
				LOGGER.error("Exception "+exception);
			}
		}

	public void fetchData() {
		try{
			listCust.clear();
			paymentDataTableList.clear();
			clearAll();
			getFetchIdTypeList();
			LOGGER.info("Entering into fetchData method");
			ViewRemittanceInquiryTransaction viewRemittanceInfo=null;
			PurposeOfRemittanceView purposeOfRemittanceView=null;
			if(getDocNumber()!=null){

				//viewRemittanceInfo=stopPaymentService.getRemittanceTrnxDetailsFromView(getDocNumber(), getDocYear(), Constants.DOCUMENT_CODE_FOR_CANCEL_REISSUE, session.getCompanyId());
				viewRemittanceInfo=stopPaymentService.getRemittanceTrnxInqDetailsFromView(getDocNumber(), getDocYear(), Constants.DOCUMENT_CODE_FOR_CANCEL_REISSUE, session.getCompanyId());

				if (viewRemittanceInfo == null) {
					LOGGER.info("=========DATA IS NULL===========");
					RequestContext.getCurrentInstance().execute("datanotfound.show();");
					clearEnquiryBean();
				} else {
					LOGGER.info("Data Availble in Database");
					List<Customer> customerList = null;
					if (viewRemittanceInfo.getCustomerId() != null) {
						customerList = iBranchPageService.getCustomerInfo(viewRemittanceInfo.getCustomerId());
						if (customerList != null && !customerList.isEmpty()) {
							setCustomerReference(viewRemittanceInfo.getCustomerRefNo());
							//setCustomerName((customerList.get(0).getFirstName()==null?"":customerList.get(0).getFirstName())+""+(customerList.get(0).getMiddleName()==null?"":customerList.get(0).getMiddleName())+" "+(customerList.get(0).getLastName()==null?"":customerList.get(0).getLastName()) );
							if(customerList.get(0).getCompanyName() != null){
								setCustomerName(customerList.get(0).getCompanyName());
							}else{
								setCustomerName((customerList.get(0).getFirstName()==null?"":customerList.get(0).getFirstName())+""+(customerList.get(0).getMiddleName()==null?"":customerList.get(0).getMiddleName())+" "+(customerList.get(0).getLastName()==null?"":customerList.get(0).getLastName()) );
							}
							setCrNo(customerList.get(0).getCrNo());
							setMobileNo( customerList.get(0).getMobile());
							setTelephoneNo(customerList.get(0).getContactNumber());
							setLoyaltyPoints(viewRemittanceInfo.getLoyaltyPointEncashed()==null?BigDecimal.ZERO:viewRemittanceInfo.getLoyaltyPointEncashed());
							setReceipYear( viewRemittanceInfo.getCancellationDocFinanceYear());
							setReceiptNo(viewRemittanceInfo.getCancellationDocNo());
							setApplicationNo(viewRemittanceInfo.getApplicationDocumentNo());
							setApplicationyear(viewRemittanceInfo.getApplicationFinYear());
							if(viewRemittanceInfo.getWesternUnionMTCNo() != null){
								setMtcNo(viewRemittanceInfo.getWesternUnionMTCNo());
								setMtcyear(viewRemittanceInfo.getApplicationFinYear());
							}
							setRemitcreateddate(viewRemittanceInfo.getCreatedDate());
							setCountrybranchName(viewRemittanceInfo.getCountryBranchName());
							setNickName((customerList.get(0).getFirstName()==null?"":customerList.get(0).getFirstName())+""+(customerList.get(0).getMiddleName()==null?"":customerList.get(0).getMiddleName())+" "+(customerList.get(0).getLastName()==null?"":customerList.get(0).getLastName()));
							BigDecimal idtypeCivilId = generalService.getComponentId(Constants.CIVILID, session.getLanguageId()).getFsBizComponentData().getComponentDataId();
							BigDecimal passportId = generalService.getComponentId(Constants.PASSPORT, session.getLanguageId()).getFsBizComponentData().getComponentDataId();
							BigDecimal gcctId = generalService.getComponentId(Constants.GCC_NATIONAL_ID, session.getLanguageId()).getFsBizComponentData().getComponentDataId();
							if(viewRemittanceInfo.getIdentityTypeId() != null && viewRemittanceInfo.getIdentityTypeId().intValue() == idtypeCivilId.intValue()){
								setCivilId(viewRemittanceInfo.getIdentityInt());
							}else if(viewRemittanceInfo.getIdentityTypeId() != null && viewRemittanceInfo.getIdentityTypeId().equals(passportId)){
								setPassportNo(viewRemittanceInfo.getIdentityInt());
							}else if(viewRemittanceInfo.getIdentityTypeId() != null && viewRemittanceInfo.getIdentityTypeId().equals(gcctId)){
								setGccNumber(viewRemittanceInfo.getIdentityInt());
							}
						}

						//corresponding Bank Details
						setCorspBank(viewRemittanceInfo.getCorrespondingBankName() );
						if(viewRemittanceInfo.getRemittanceDesc()!=null&&viewRemittanceInfo.getDeliveryDesc()!=null){
							setProduct( viewRemittanceInfo.getRemittanceDesc()+"-"+viewRemittanceInfo.getDeliveryDesc());
						}
						//setPayableAt(viewRemittanceInfo.getCountryBranchName());
						setCorresponingBankBranchId(viewRemittanceInfo.getBankBranchId());
						if(viewRemittanceInfo.getBankBranchId() != null){
							String correspondingBankBranchName=bankBranchDetailsService.toFetchBranchName(viewRemittanceInfo.getBankBranchId());
							setPayableAt(correspondingBankBranchName);
							List<BankBranchView> lstBankBranchViews=bankBranchDetailsService.toFetchAllDetailsFromBankBranch(viewRemittanceInfo.getBankId(),viewRemittanceInfo.getBankBranchId());
							if(lstBankBranchViews.size()>0){
								BankBranchView bankBranchView=lstBankBranchViews.get(0);
								setCorrespondingCountryName(bankBranchView.getCountryName());
								setCorresponingStateName(bankBranchView.getStateName());
								setCorresponingDistName(bankBranchView.getDistrictName());
								setCorresponingCityName(bankBranchView.getCityName());
								setCorresponingIfscCode(bankBranchView.getIfscCode());
								setCorresponingDrBranchId(bankBranchView.getDebitBranchId());
								setCorresponingSwiftCode(bankBranchView.getSwift());
								setCorresponingRoutingId(bankBranchView.getRoutingId());
								setRoutingAddress(bankBranchView.getAddress1());
							}
						}
						BankMaster bankMasterObj1=stopPaymentService.getBankMasterDetails(viewRemittanceInfo.getBankId() );

						//setCorspBank( bankMasterObj1.getBankFullName());
						//setRoutingAddress(bankMasterObj1.getAddress1() );

						//Bene Bank Details
						setBeneBank( viewRemittanceInfo.getBenificiaryBank());
						setBeneBranchName(viewRemittanceInfo.getBenificiaryBranch());
						if(viewRemittanceInfo.getBeneficiaryBankId() != null && viewRemittanceInfo.getBeneficiaryBankBranchId() != null){
							List<BankBranchView> lstBankBranchViews=bankBranchDetailsService.toFetchAllDetailsFromBankBranch(viewRemittanceInfo.getBeneficiaryBankId(),viewRemittanceInfo.getBeneficiaryBankBranchId());
							if(lstBankBranchViews.size()>0){
								BankBranchView bankBranchView=lstBankBranchViews.get(0);
								setBeneficiaryCountryName(bankBranchView.getCountryName());
								setBeneficiaryStateName(bankBranchView.getStateName());
								setBeneficiaryDistName(bankBranchView.getDistrictName());
								setBeneficiaryCityName(bankBranchView.getCityName());
								setBeneficiaryIfscCode(bankBranchView.getIfscCode());
								setBeneficiaryDrBranchId(bankBranchView.getDebitBranchId());
								setBeneficiaryRoutingId(bankBranchView.getRoutingId());
								if(bankBranchView.getBranchCode() != null){
									setBeneBranchCode(bankBranchView.getBranchCode().toString());
								}
								setBeneBankDetails(bankBranchView.getAddress1()); 
								//setCorresponingSwiftCode(bankBranchView.getSwift());
								//setRoutingAddress(bankBranchView.getAddress1());
							}
						}
						setInstruction( viewRemittanceInfo.getInstruction());
						setSwift(viewRemittanceInfo.getBenificiarySwiftBank1() );
						setBeneName( viewRemittanceInfo.getBenificiaryName());
						setBeneTelePhone( viewRemittanceInfo.getBeneficiaryTelephone());
						setBeneAddress( viewRemittanceInfo.getBeneficiaryAddress());

						setInterMediary(viewRemittanceInfo.getBenificiaryInterBank1() );
						BankMaster bankMasterObj=stopPaymentService.getBankMasterDetails(viewRemittanceInfo.getBeneficiaryBankId());
						BankBranch bankBranchObj=stopPaymentService.getBankBranchDetails(viewRemittanceInfo.getBankBranchId() );
						if(bankMasterObj!=null){
							setBankCode(bankMasterObj.getBankCode() );
							setZipCode(bankMasterObj.getZipCode() );
						}

						setPayTo(viewRemittanceInfo.getPayTo() );
						setHelpDesk(viewRemittanceInfo.getApplcreatedBy());
						setCashierName(viewRemittanceInfo.getCreatedBy());
						//FINANCE
						setSaleAmount(viewRemittanceInfo.getForeignTrxAmount() );
						setPurchaseAmount( viewRemittanceInfo.getLocalAmount());
						setExchangeRate(viewRemittanceInfo.getExchangeRateApplied() );
						setChargesAmount( viewRemittanceInfo.getLocalChargeAmount());
						setComissionAmount( viewRemittanceInfo.getLocalCommisionAmount());
						setNetAmount(viewRemittanceInfo.getLocalNetTrxAmount());
						setDeliveryAmount( viewRemittanceInfo.getLocalDeliveryAmount());
						setQuoteName(viewRemittanceInfo.getCurrencyQuoteName() );
						if(viewRemittanceInfo.getDocumentId() != null){
							purposeOfRemittanceView=stopPaymentService.getPurposeOfRemittanceView(getDocNumber(), getDocYear(),viewRemittanceInfo.getDocumentId(), session.getCompanyId());
							if(purposeOfRemittanceView != null){
								setBankIndicatorName(purposeOfRemittanceView.getFlexiFieldValue());
							}
							purposeOfRemittanceView=stopPaymentService.getPurposeOfRemittanceView(getDocNumber(), getDocYear(),viewRemittanceInfo.getDocumentId(), session.getCompanyId());
							if(purposeOfRemittanceView != null){
								setBankIndicatorName(purposeOfRemittanceView.getFlexiFieldValue());
							}
						}
						LOGGER.info("============================="+viewRemittanceInfo.getCancellationDocNo());
						LOGGER.info("============================="+viewRemittanceInfo.getCancellationDocCode());
						if(viewRemittanceInfo.getCancellationDocCode() != null &&  viewRemittanceInfo.getCancellationDocFinanceYear() != null &&  viewRemittanceInfo.getCancellationDocNo() != null){
							collectList = stopPaymentService.getCollectionDetail(viewRemittanceInfo.getCancellationDocCode(), viewRemittanceInfo.getCancellationDocFinanceYear(), viewRemittanceInfo.getCancellationDocNo());
						}
						LOGGER.info("===========================TANSACTION STATUS"+viewRemittanceInfo.getTransactionStatus());


						if(collectList.size()>0){
							for(CollectDetail collectionDetails:collectList){
								ReceiptEnquiryDataTable receiptIquiry=new ReceiptEnquiryDataTable();
								if(collectionDetails.getCollectionMode().equalsIgnoreCase(Constants.CashCode)){
									receiptIquiry.setPaymentBy(Constants.CASH);
									receiptIquiry.setAmount(collectionDetails.getCollAmt());
								}else if(collectionDetails.getCollectionMode().equalsIgnoreCase(Constants.VOCHERCODE )){
									receiptIquiry.setPaymentBy( Constants.VOUCHER);
									receiptIquiry.setAmount(collectionDetails.getCollAmt());
									receiptIquiry.setVoucherNo(collectionDetails.getVoucherNo());
									receiptIquiry.setVoucherYear(collectionDetails.getVoucherYear());
								}else if(collectionDetails.getCollectionMode().equalsIgnoreCase(Constants.ChequeCode)){
									receiptIquiry.setPaymentBy( Constants.CHEQUENAME);
									receiptIquiry.setChequeNo(collectionDetails.getChequeRef());
									receiptIquiry.setAmount(collectionDetails.getCollAmt());
									receiptIquiry.setApproval(collectionDetails.getApprovalNo());
									List<ViewBankDetails> localBankListinCollection = icustomerBankService.customerBanks(collectionDetails.getFsCustomer().getCustomerId(), collectionDetails.getChequeBankRef());
									if (localBankListinCollection.size() > 0) {
										receiptIquiry.setCheckBank(localBankListinCollection.get(0).getBankFullName());
									} 
								}else if(collectionDetails.getCollectionMode().equalsIgnoreCase(Constants.KNETCode)){
									receiptIquiry.setPaymentBy( Constants.KNET);
									receiptIquiry.setChequeNo(collectionDetails.getDebitCard());
									receiptIquiry.setAmount(collectionDetails.getCollAmt());
									receiptIquiry.setApproval(collectionDetails.getApprovalNo());
									List<ViewBankDetails> localBankListinCollection = icustomerBankService.customerBanks(collectionDetails.getFsCustomer().getCustomerId(), collectionDetails.getChequeBankRef());
									if (localBankListinCollection.size() > 0) {
										receiptIquiry.setCheckBank(localBankListinCollection.get(0).getBankFullName());
									} 
								}else if(collectionDetails.getCollectionMode().equalsIgnoreCase(Constants.BankTransferCode)){
									receiptIquiry.setPaymentBy(Constants.BANKTRANSFER);
									List<ViewBankDetails> localBankListinCollection = icustomerBankService.customerBanks(collectionDetails.getFsCustomer().getCustomerId(), collectionDetails.getChequeBankRef());
									if (localBankListinCollection.size() > 0) {
										receiptIquiry.setCheckBank(localBankListinCollection.get(0).getBankFullName());
									} 
									receiptIquiry.setAmount(collectionDetails.getCollAmt());
									receiptIquiry.setApproval(collectionDetails.getApprovalNo());
								}else if(collectionDetails.getCollectionMode().equalsIgnoreCase(Constants.AMTBC)){
									PaymentModeDesc voucherDesc = iPersonalRemittanceService.getvoucherModeId(Constants.AMTBC, session.getLanguageId());
									if(voucherDesc!=null) {
										receiptIquiry.setPaymentBy(voucherDesc.getLocalPaymentName());
										receiptIquiry.setAmount(collectionDetails.getCollAmt());
									}
									
								}else{
									receiptIquiry.setPaymentBy(collectionDetails.getCollectionMode());
									receiptIquiry.setAmount(collectionDetails.getCollAmt());
								}

								if(collectionDetails.getChequeDate()!=null){
									SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
									String date=format.format(collectionDetails.getChequeDate());
									receiptIquiry.setChequeDate(date);
								}

								paymentDataTableList.add(receiptIquiry);
							}

						}

						//STATUS for FC		
						Map<String, Object> lstlocalHighValueAuth = iPersonalRemittanceService.fetchLocalAuthDetails(new BigDecimal("3"), getDocNumber(), getDocYear(), "FC HIGH VALUE");
						
						if(lstlocalHighValueAuth != null && lstlocalHighValueAuth.size() != 0){
							System.out.println(lstlocalHighValueAuth.toString());
							
							if(lstlocalHighValueAuth.get("STATUS") != null && lstlocalHighValueAuth.get("STATUS").toString().equalsIgnoreCase(Constants.No)){
								if(lstlocalHighValueAuth.get("AUTHORIZED_BY") != null){
									setHighValueTrnxPending(Constants.NO);
								}else{
									setHighValueTrnxPending("");
								}
							}else{
								setHighValueTrnxPending(Constants.YES);
							}
							
							if(lstlocalHighValueAuth.get("AUTHORIZED_BY") != null){
								setHighValueTrnxAuth((String)lstlocalHighValueAuth.get("AUTHORIZED_BY"));
								if(lstlocalHighValueAuth.get("CREATED_DATE") != null){
									//SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
									//setHighvalueAuthDate((Date)lstlocalHighVal ueAuth.get("CREATED_DATE"));
									//setHighvalueAuthDate(format.parse(lstlocalHighValueAuth.get("CREATED_DATE").toString()));
									setHighvalueAuthDate(lstlocalHighValueAuth.get("CREATED_DATE").toString());
								}
							}
						}	

						//Status for KD
						Map<String, Object> lstlocalHighValueAuthKd = iPersonalRemittanceService.fetchLocalAuthDetails(new BigDecimal("3"), getDocNumber(), getDocYear(), "LOCAL HIGH VALUE");
						
						if(lstlocalHighValueAuthKd != null && lstlocalHighValueAuthKd.size() != 0){
							System.out.println(lstlocalHighValueAuthKd.toString());
							
							if(lstlocalHighValueAuthKd.get("STATUS") != null && lstlocalHighValueAuthKd.get("STATUS").toString().equalsIgnoreCase(Constants.No)){
								if(lstlocalHighValueAuthKd.get("AUTHORIZED_BY") != null){
									setHighValueTrnxPendingForLocal(Constants.NO);
								}else{
									setHighValueTrnxPendingForLocal("");
								}
							}else{
								setHighValueTrnxPendingForLocal(Constants.YES);
							}
							
							if(lstlocalHighValueAuthKd.get("AUTHORIZED_BY") != null){
								setHighValueTrnxAuthForLocal((String)lstlocalHighValueAuthKd.get("AUTHORIZED_BY"));
								
								if(lstlocalHighValueAuthKd.get("CREATED_DATE") != null){
									//SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
									//setHighvalueAuthDateForLocal((Date)lstlocalHighValueAuthKd.get("CREATED_DATE"));
									//setHighvalueAuthDateForLocal(format.parse(lstlocalHighValueAuthKd.get("CREATED_DATE").toString()));
									setHighvalueAuthDateForLocal(lstlocalHighValueAuthKd.get("CREATED_DATE").toString());
								}
							}
						}


						LOGGER.info("==docYear==="+viewRemittanceInfo.getDocumentFinYear());
						LOGGER.info("==docNum==="+viewRemittanceInfo.getDocumentNo());
						LOGGER.info("==docCode==="+ viewRemittanceInfo.getDocumentId());
						LOGGER.info("== companyId==="+viewRemittanceInfo.getCompanyId());

						List<RemittanceComplaint> lisRemitComplaint = stopPaymentService.getRemitaComplaintDetails(viewRemittanceInfo.getDocumentFinYearId(), viewRemittanceInfo.getDocumentNo(), viewRemittanceInfo.getDocumentCode(),  viewRemittanceInfo.getCompanyId());
						if(lisRemitComplaint.size()>0){
							for (RemittanceComplaint remitComplaint : lisRemitComplaint) {

								if(remitComplaint.getStopDocumentNumber() != null){
									setStopPaymentNo(remitComplaint.getStopDocumentNumber());
									if(remitComplaint.getStopDocumentDate() !=null){
										SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
										setStopPaymentDate( format.format(remitComplaint.getStopDocumentDate() ));
									}
									if(remitComplaint.getStopDocumentFinanceYear()!=null){
										setStopPaymentYear(remitComplaint.getStopDocumentFinanceYear());
									}
									setReceivedByPrbStatus(remitComplaint.getProblemStatus());
								}

								if(remitComplaint.getCancelDocumentNumber() != null){
									setCanceledNo(remitComplaint.getCancelDocumentNumber());
									if(remitComplaint.getCancelDocumentFinanceYear() !=null){
										setCanceledYear(remitComplaint.getCancelDocumentFinanceYear());
									}
									if(remitComplaint.getCancelDocumentDate() !=null){
										SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
										setCanceledDate(format.format(remitComplaint.getCancelDocumentDate()));
									}
									setComplaintStatus(remitComplaint.getCancellationStatus());
								}

								setModifiedDate(remitComplaint.getModifiedDate());
								setModifier(remitComplaint.getModifiedBy());

							}
							
							if(getHighValueTrnxPending() != null && getHighValueTrnxPending().equalsIgnoreCase(Constants.YES)){
								setHighValueTrnxPending(Constants.NO);
							}
							
							if(getHighValueTrnxPendingForLocal() != null && getHighValueTrnxPendingForLocal().equalsIgnoreCase(Constants.YES)){
								setHighValueTrnxPendingForLocal(Constants.NO);
							}
						}
						
						
						/*setModifiedDate(viewRemittanceInfo.getModifiedDate());
						setModifier(viewRemittanceInfo.getModifiedBy());*/

						setBankAgent(viewRemittanceInfo.getBankCode());
						setCreator(viewRemittanceInfo.getCreatedBy());
						setCreatedDate(viewRemittanceInfo.getCreatedDate());

						Clob signature=stopPaymentService.getSignatureOfRemitter(viewRemittanceInfo.getDocumentNo(),viewRemittanceInfo.getDocumentFinYear(),viewRemittanceInfo.getDocumentId().toPlainString(),viewRemittanceInfo.getCompanyId());
						if(signature!=null){
							try {
								LOGGER.info("signature======"+signature );
								setSignatureSpecimen(signature
										.getSubString(1, (int) signature.length()));
							} catch (SQLException e) {

								e.printStackTrace();
							}
						}

						//AML_AUTH
						setSourceOfIncome(viewRemittanceInfo.getSourceOfIncomeDesc());
						setBlackListIndicator(viewRemittanceInfo.getBlackListIndicator() );
						setBlackListRemarks(viewRemittanceInfo.getBlackListRemarks() );
						setBlackListCleared(viewRemittanceInfo.getBlackListClear() );
						setBlackListClearedBy( viewRemittanceInfo.getBlackCreatedUser());
						setAceAMLStatus(viewRemittanceInfo.getAmlStatus());
						//setTransactionStatus(viewRemittanceInfo.getTransactionStatus());
						setWebServiceStatus(viewRemittanceInfo.getWebServiceStatus());
						setDelivaryMode(viewRemittanceInfo.getDeliveryDesc());
						if(viewRemittanceInfo.getBlackListDate()!=null){
							SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
							setBlackListDate(format.format(viewRemittanceInfo.getBlackListDate()) );
						}
						//Deal Info
						List<RemittanceApplication> remittAppList	 = stopPaymentService.getRemittanceApplication(viewRemittanceInfo.getDocumentFinYear(), viewRemittanceInfo.getDocumentNo(), viewRemittanceInfo.getDocumentCode(), viewRemittanceInfo.getCompanyId());
						if(remittAppList.size()>0){
							RemittanceApplication  remitApp=	remittAppList.get(0);
							setAverageRate( remitApp.getAverageRate());
							setDealDate(remitApp.getAverageRateDt());

							ReceiptEnquiryDataTable remitDT=new ReceiptEnquiryDataTable();
							//remitDT.setSpecialDealYear(remitApp.getSpldealdocumentyear() );
							//remitDT.setSpecialDealNo(remitApp.getSpldealdocumentnumber() );
							listCust.add( remitDT);
						}
					}
				}

				LOGGER.info("Exit into fetchData method");
				LOGGER.info("ABOVE ELSE PLEASE SELECT EXECUTED");
			}else{	
				LOGGER.info("PLEASE SELECT EXECUTED");
				RequestContext.getCurrentInstance().execute("pleaseselectdocno.show();");
			}	
		}catch(NullPointerException  e){ 

			setErrorMsg("fetchData :");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}

	}

	public void clearEnquiryBean() {
		setDocNumber(null);
		setDocYear(null);

	}

	public void clickOnExit() throws IOException {
		LOGGER.info( "exit called==================");
		if (session.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void remittancetEnquirypageNavigation() {

		clearEnquiryBean();
		clearAll();
		setDocYear(null);
		setDocNumber(null);
		LOGGER.info("Entering into stopPaymentCollectionpageNavigation method");
		setRenderPanel(true);
		setExitButtonRender(true);
		setBackButtonRender(false);
		setRenderForRemittanceBranchWiseEnquiry(false);
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "remittanceinquiry.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../enquiry/remittanceinquiry.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("Exit into stopPaymentCollectionpageNavigation method");
	}


	public void clearAllFieldsInquiry(){
		clearAll();
		setDocNumber(null);
		setDocYear( null);
	}


	public void populateValue()
	{
		try{
			LOGGER.info("Entering into populateValue method");
			if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("customerData") != null) {
				CustomerInquiryDataTable customerData = (CustomerInquiryDataTable)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("customerData");
				clearAllFields();
				setDocNumber(customerData.getTransactionNumber());
				setDocYear(customerData.getDocumentFinanceYear());
				setDocumentNo(customerData.getTransactionNumber());
				//		setDocNumber(new BigDecimal(99999900));

				fetchData();
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("customerData");
				LOGGER.info(customerData);
			}
		}catch(NullPointerException  e){ 

			setErrorMsg("populateValue :");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}

	}


	/**/


	public void clearAllFields() {
		//Customer Details
		setAliasFirstName(null);
		setAliasSecondName(null);
		setAliasThirdName(null);
		setAliasFourthName(null);
		setDocYear(null);
		setDocNumber(null);
		setCustomerReference(null);
		setFirstName(null);
		setMiddleName(null);
		setLastName(null);
		setGender(null);
		setLoyaltyPoints(null);
		setDob(null);
		setNationalityId(null);
		setNationality(null);
		setMobile(null);
		setBeneficiaryName(null);
		setBenenationality(null);
		setBenefirstName(null);
		setBeneSecondName(null);
		setBeneThirdName(null);
		setBeneFourthName(null);
		setBeneBankName(null);
		setBeneBankBranchName(null);
		setBeneficiaryAccountNumber(null);
		setBeneCountry(null);
		setBeneType(null);
		setEmploymentType(null);
		setBranchName(null);
		setCorrespondingCountryName(null);
		setService(null);
		setProduct(null);
		setTransferNo(null);
		setApplicationNo(null);
		setDocumentDate(null);
		setForeignCurrencyName(null);
		setLocalCurrencyName(null);
		setLocalChargeCurrencName(null);
		setLocalDelivaryCurrencName(null);
		setLocalNetCurrencName(null);
		setForeignCurrencyAmount(null);
		setLocalTransactionAmount(null);
		setLocalChargeAmount(null);
		setLocalDelivaryAmount(null);
		setLocalNetTransactionAmount(null);
		setExchangeApplied(null);
		setDebitAccountNo(null);
		setTransactionStatus(null);
		setTransactionlastUpdated(null);
		setRemittenceMode(null);
		setDelivaryMode(null);
		setWebServiceStatus(null);
		setWestionUnion(null);
		setHvt(null);
		setBli(null);
		setDealyear(null);
		setLocalCommissionAmount(null);
		setPaymentMode(null);
		setPaymentId(null);
		setLocalCommissionCurrencyName(null);
		setBankName(null);
		setBankId(null);

	}


	/////////////////////////////////////////////////// REPORT CODE  ///////////////////////////////////////////////
	//Jasper Report generation
	private JasperPrint jasperPrint;
	private List<RemittanceReceiptSubreport> remittanceReceiptSubreportList;
	private List<CollectionDetailView> collectionViewList = new CopyOnWriteArrayList<CollectionDetailView>();
	public void generatePersonalRemittanceReceiptReport() throws IOException{
		ServletOutputStream servletOutputStream=null;
		try {
			fetchRemittanceReceiptReportData(getReceiptNo(),getDocYear(),Constants.DOCUMENT_CODE_FOR_COLLECT_TRANSACTION );
			remittanceReceiptReportInit();
			
			String fileName = "RemittanceReceiptReport.pdf";
			printJasperDirectToPrinter(fileName);			
		} catch(NullPointerException  e){ 

			setErrorMsg("generatePersonalRemittanceReceiptReport :");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}finally{
			if(servletOutputStream!=null){
				servletOutputStream.close();
			}
		}

	}
	
	public void printJasperDirectToPrinter(String fileName){
		try{
			JRExporter exporter = null;
			ByteArrayOutputStream out = null;
			ByteArrayInputStream input = null;
			BufferedOutputStream output = null; 

			FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = facesContext.getExternalContext();
			HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

			facesContext = FacesContext.getCurrentInstance();
			externalContext = facesContext.getExternalContext();
			response = (HttpServletResponse) externalContext.getResponse();

			out = new ByteArrayOutputStream(); 

			exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRPdfExporterParameter.PDF_JAVASCRIPT, "this.print();");
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);

			exporter.exportReport();

			input = new ByteArrayInputStream(out.toByteArray());

			response.reset();
			response.setHeader("Content-Type", "application/pdf");
			response.setHeader("Content-Length", String.valueOf(out.toByteArray().length));
			response.setHeader("Content-Disposition", "inline; filename="+fileName);
			output = new BufferedOutputStream(response.getOutputStream(), org.apache.jasper.Constants.DEFAULT_BUFFER_SIZE);
			byte[] buffer = new byte[org.apache.jasper.Constants.DEFAULT_BUFFER_SIZE];
			int length;
			while ((length = input.read(buffer)) > 0) {
				output.write(buffer, 0, length);
			}
			output.flush();
			facesContext.responseComplete();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public void remittanceReceiptReportInit() throws JRException {
		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(remittanceReceiptSubreportList);
		//String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("reports/design/RemittanceReceiptNewReport.jasper");
		//String realPath = ctx.getRealPath("//");
		//String reportPath = realPath + "\\reports\\design\\RemittanceReceiptNewReport.jasper";
		String realPath = ctx.getRealPath("//");
		String reportPath = realPath +"//reports//design//RemittanceReceiptNewReport.jasper";
		jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap(),beanCollectionDataSource);
	}

	public List<RemittanceReportBean> calculateCollectionMode(RemittanceApplicationView viewCollectionObj){	
		List<RemittanceReportBean> collectionDetailList = new ArrayList<RemittanceReportBean>();
		List<CollectionPaymentDetailsView> collectionPaymentDetailList= iPersonalRemittanceService.getCollectionPaymentDetailForRemittanceReceipt(viewCollectionObj.getCompanyId(),viewCollectionObj.getCollectionDocumentNo(),viewCollectionObj.getCollectionDocFinanceYear(),viewCollectionObj.getCollectionDocCode());

		for(CollectionPaymentDetailsView viewObj: collectionPaymentDetailList){
			RemittanceReportBean obj = new RemittanceReportBean();

			obj.setCollectionMode(viewObj.getCollectionModeDesc());

			BigDecimal collectAmount=GetRound.roundBigDecimal((viewObj.getCollectAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(viewCollectionObj.getLocalTransactionCurrencyId()));
			obj.setCollectAmount(collectAmount);

			collectionDetailList.add(obj);
		}
		return collectionDetailList;
	}
	private void fetchRemittanceReceiptReportData(BigDecimal documentNumber, BigDecimal docYear,String docCode) throws Exception{

		collectionViewList.clear();
		remittanceReceiptSubreportList = new ArrayList<RemittanceReceiptSubreport>();

		List<RemittanceApplicationView> remittanceApplicationList = new ArrayList<RemittanceApplicationView>();

		List<RemittanceApplicationView> fcsaleList = new ArrayList<RemittanceApplicationView>();

		List<RemittanceReportBean> remittanceApplList = new ArrayList<RemittanceReportBean>();

		List<RemittanceReportBean> fcsaleAppList = new ArrayList<RemittanceReportBean>();

		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath = ctx.getRealPath("//");
		String subReportPath = realPath + Constants.SUB_REPORT_PATH;
		String waterMark = realPath+Constants.REPORT_WATERMARK_LOGO;

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String currentDate = dateFormat.format(new Date());
		int noOfTransactions = 0;

		String purposeOfRemittance = "";
		String currencyQuoteName = generalService.getCurrencyQuote(new BigDecimal(session.getCurrencyId()));


		List<Employee> employeeList = iPersonalRemittanceService.getEmployeeDetails(session.getUserName());
		System.out.println("=============="+documentNumber);
		System.out.println("=============="+docYear);
		System.out.println("=============="+docCode);
		List<RemittanceApplicationView> remittanceViewlist = iPersonalRemittanceService.getRecordsForRemittanceReceiptReport(documentNumber,docYear,docCode);

		if (remittanceViewlist.size() > 0) {

			for (RemittanceApplicationView remittanceAppview : remittanceViewlist) {

				if (remittanceAppview.getApplicationTypeDesc().equalsIgnoreCase("REMITTANCE")) {
					remittanceApplicationList.add(remittanceAppview);
					noOfTransactions= noOfTransactions+1;
				} else if (remittanceAppview.getApplicationTypeDesc().equalsIgnoreCase("FOREIGN CURRENCY SALE")) {
					fcsaleList.add(remittanceAppview);
					noOfTransactions= noOfTransactions+1;
				}
			}

			//remittance List
			for (RemittanceApplicationView view : remittanceApplicationList) {

				RemittanceReportBean obj = new RemittanceReportBean();

				if (view.getCustomerReference() != null && view.getFirstName() != null && view.getMiddleName() != null && view.getLastName()!=null) {
					obj.setFirstName(view.getCustomerReference().toString() + " / " + view.getFirstName() + " " + view.getMiddleName()+" "+view.getLastName());
				} else if (view.getCustomerReference() == null && view.getFirstName() != null && view.getMiddleName() != null && view.getLastName()!=null) {
					obj.setFirstName(view.getFirstName() + " "+ view.getMiddleName()+" "+view.getLastName());
				} else if (view.getCustomerReference() == null && view.getFirstName() == null && view.getMiddleName() != null && view.getLastName()!=null) {
					obj.setFirstName(view.getMiddleName()+" "+view.getLastName());
				} else if (view.getCustomerReference() != null && view.getFirstName() == null && view.getMiddleName() != null && view.getLastName()!=null) {
					obj.setFirstName(view.getCustomerReference().toString() + " / " + view.getMiddleName()+" "+view.getLastName());
				} else if (view.getCustomerReference() == null && view.getFirstName() != null && view.getMiddleName() == null && view.getLastName()!=null) {
					obj.setFirstName(view.getFirstName()+" "+view.getLastName());
				} else if (view.getCustomerReference() != null && view.getFirstName() == null && view.getMiddleName() == null && view.getLastName()==null) {
					obj.setFirstName(view.getCustomerReference().toString());
				} else if (view.getCustomerReference() != null && view.getFirstName() != null && view.getMiddleName() == null && view.getLastName()!=null) {
					obj.setFirstName(view.getCustomerReference().toString() + " " + view.getFirstName()+" "+view.getLastName());
				}
				if(view.getContactNumber()!=null && !view.getContactNumber().trim().equalsIgnoreCase("")){
					obj.setMobileNo(new BigDecimal(view.getContactNumber().trim()));
				}
				
				obj.setCivilId(view.getIdentityInt());
				Date sysdate = view.getIdentityExpiryDate();
				if(sysdate!=null){
					obj.setIdExpiryDate(new SimpleDateFormat("dd/MM/yyyy").format(sysdate));
				}
				//obj.setLocation(session.getLocation()); Commented by Rabil on 15/10/2017
				obj.setLocation(view.getCountryBranchName());

				if(view.getDocumentFinancialYear()!=null && view.getCollectionDocumentNo()!=null){
					obj.setReceiptNo(view.getDocumentFinancialYear()+" / "+view.getCollectionDocumentNo());
				}else if(view.getCollectionDocumentNo()!=null){
					obj.setReceiptNo(view.getCollectionDocumentNo().toString());
				}


				if(view.getDocumentFinancialYear()!=null && view.getDocumentNo()!=null){
					obj.setTransactionNo(view.getDocumentFinancialYear()+" / "+view.getDocumentNo());
				}else if(view.getDocumentNo()!=null){
					obj.setTransactionNo(view.getDocumentNo().toString());
				}

				//obj.setDate(currentDate); Commented by Rabil on 15/10/2017
				
				Date docDate = view.getDocumentDate();
				if(docDate != null){
					obj.setDate(new SimpleDateFormat("dd/MM/yyy").format(docDate));
				}

				
				
				
				obj.setBeneficiaryName(view.getBeneficiaryName());
				obj.setBenefeciaryBankName(view.getBeneficiaryBank());
				obj.setBenefeciaryBranchName(view.getBenefeciaryBranch());
				obj.setBenefeciaryAccountNumber(view.getBenefeciaryAccountNo());
				obj.setNoOfTransaction(new BigDecimal(noOfTransactions));
				obj.setPhoneNumber(view.getPhoneNumber());
				//obj.setUserName(session.getUserName());
				obj.setUserName(view.getCreatedBy());
				obj.setPinNo(view.getPinNo());



				HashMap<String, String> loyaltiPoints  =iPersonalRemittanceService.getloyalityPointsFromProcedure(view.getCustomerReference(), view.getDocumentDate());

				String prLtyStr1 =loyaltiPoints.get("P_LTY_STR1");
				String prLtyStr2 =loyaltiPoints.get("P_LTY_STR2");
				String prInsStr1 =loyaltiPoints.get("P_INS_STR1");
				String prInsStr2 =loyaltiPoints.get("P_INS_STR2");
				String prInsStrAr1 =loyaltiPoints.get("P_INS_STR_AR1");
				String prInsStrAr2 =loyaltiPoints.get("P_INS_STR_AR2");

				if(!prLtyStr1.trim().equals("") && !prLtyStr2.trim().equals("")){
					obj.setLoyalityPointExpiring(prLtyStr1+"  \n"+prLtyStr2);
				}else if(!prLtyStr1.trim().equals("")){
					obj.setLoyalityPointExpiring(prLtyStr1);
				}else if(!prLtyStr2.trim().equals("")){
					obj.setLoyalityPointExpiring(prLtyStr2);
				}

				
				List<CutomerDetailsView> customerListR = iPersonalRemittanceService.getCustomerDetails(view.getCustomerId());

				if (customerListR != null && customerListR.size() > 0) {
					CutomerDetailsView cust = customerListR.get(0);
					if(cust.getIdType() != null && cust.getIdType().compareTo(new BigDecimal(Constants.CORPORATE_ID_TYPE))==0){
						obj.setLoyalityPointExpiring("");
					}
				}
				
				

				if(!prInsStr1.trim().equals("") && !prInsStrAr1.trim().equals("")){
					obj.setInsurence1(prInsStr1+"  \n"+prInsStrAr1);
				}else if(!prInsStr1.trim().equals("")){
					obj.setInsurence1(prInsStr1);
				}else if(!prInsStrAr1.trim().equals("")){
					obj.setInsurence1(prInsStrAr1);
				}


				if(!prInsStr2.trim().equals("") && !prInsStrAr2.trim().equals("")){
					obj.setInsurence2(prInsStr2+"  \n"+prInsStrAr2);
				}else if(!prInsStr2.trim().equals("")){
					obj.setInsurence2(prInsStr2);
				}else if(!prInsStrAr2.trim().equals("")){
					obj.setInsurence2(prInsStrAr2);
				}

				if (view.getBeneCityName() != null && view.getBeneDistrictName() != null && view.getBeneStateName() != null) {
					obj.setAddress(view.getBeneCityName() + "," + view.getBeneDistrictName() + "," + view.getBeneStateName());
				} else if (view.getBeneCityName() == null && view.getBeneDistrictName() != null && view.getBeneStateName() != null) {
					obj.setAddress(view.getBeneDistrictName() + "," + view.getBeneStateName());
				} else if (view.getBeneCityName() == null && view.getBeneDistrictName() == null && view.getBeneStateName() != null) {
					obj.setAddress(view.getBeneStateName());
				} else if (view.getBeneCityName() != null && view.getBeneDistrictName() == null && view.getBeneStateName() != null) {
					obj.setAddress(view.getBeneCityName() + ", " + view.getBeneStateName());
				} else if (view.getBeneCityName() != null && view.getBeneDistrictName() == null && view.getBeneStateName() == null) {
					obj.setAddress(view.getBeneCityName());
				} else if (view.getBeneCityName() == null && view.getBeneDistrictName() != null && view.getBeneStateName() == null) {
					obj.setAddress(view.getBeneDistrictName());
				} else if (view.getBeneCityName() != null && view.getBeneDistrictName() != null && view.getBeneStateName() == null) {
					obj.setAddress(view.getBeneCityName() + ", " + view.getBeneDistrictName());
				}



				if (view.getRemittanceDescription() != null && view.getDeliveryDescription() != null) {
					obj.setPaymentChannel(view.getRemittanceDescription()+ " - " + view.getDeliveryDescription());
				} else if (view.getRemittanceDescription() == null && view.getDeliveryDescription() != null) {
					obj.setPaymentChannel(view.getDeliveryDescription());
				} else if (view.getRemittanceDescription() != null && view.getDeliveryDescription() == null) {
					obj.setPaymentChannel(view.getRemittanceDescription());
				}

				String currencyAndAmount=null;
				BigDecimal foreignTransationAmount=GetRound.roundBigDecimal((view.getForeignTransactionAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getForeignCurrencyId()));
				if(view.getCurrencyQuoteName()!=null && foreignTransationAmount!=null){
					currencyAndAmount = view.getCurrencyQuoteName()+"     ******"+foreignTransationAmount;
				}
				obj.setCurrencyAndAmount(currencyAndAmount);
				List<PurposeOfRemittanceView>  purposeOfRemittanceList =   iPersonalRemittanceService.getPurposeOfRemittance(view.getDocumentNo(),view.getDocumentFinancialYear());

				/*if(purposeOfRemittanceList!=null && purposeOfRemittanceList.size()>0){
					obj.setPerposeOfRemittance(purposeOfRemittanceList.get(0).getAmiecDescription());
				}*/
				/*for(PurposeOfRemittanceView purposeObj :purposeOfRemittanceList){
					if(purposeOfRemittance.equals("")){
						purposeOfRemittance  = purposeObj.getAmiecDescription();
					}else{
						purposeOfRemittance  = purposeOfRemittance+", "+purposeObj.getAmiecDescription();
					}
				}

				if(purposeOfRemittance != null && !purposeOfRemittance.equalsIgnoreCase("")){
					obj.setPerposeOfRemittance(purposeOfRemittance);
				}*/
				List<PurposeOfRemittanceReportBean> purposeOfRemitTrList1=new ArrayList<PurposeOfRemittanceReportBean>( );
				for(PurposeOfRemittanceView purposeObj :purposeOfRemittanceList){
					PurposeOfRemittanceReportBean beanObj=new PurposeOfRemittanceReportBean();
					beanObj.setPurposeOfTrField(purposeObj.getFlexfieldName());
					beanObj.setPurposeOfTrfieldArabic(null);
					beanObj.setPurposeOfTrValue(purposeObj.getFlexiFieldValue() );
					purposeOfRemitTrList1.add(beanObj);
				}

				if(purposeOfRemitTrList1.size()>0){
					obj.setPurposeOfRemitTrList(purposeOfRemitTrList1);
				}

				if(view.getCurrencyQuoteName()!=null && currencyQuoteName!=null && view.getExchangeRateApplied()!=null){
					obj.setExchangeRate(view.getCurrencyQuoteName()+" / "+currencyQuoteName+"     "+view.getExchangeRateApplied().toString());
				}

				if(view.getLocalTransactionAmount()!=null && view.getLocalTransactionCurrencyId()!=null){
					BigDecimal transationAmount=GetRound.roundBigDecimal((view.getLocalTransactionAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setLocalTransactionAmount(currencyQuoteName+"     ******"+transationAmount.toString());
				}

				if(view.getLocalCommissionAmount()!=null && view.getLocalTransactionCurrencyId()!=null){
					BigDecimal localCommitionAmount=GetRound.roundBigDecimal((view.getLocalCommissionAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setCommision(currencyQuoteName+"     ******"+localCommitionAmount.toString());
				}

				if(view.getLocalChargeAmount()!=null && view.getLocalTransactionCurrencyId()!=null){
					BigDecimal localChargeAmount=GetRound.roundBigDecimal((view.getLocalChargeAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setOtherCharges(currencyQuoteName+"     ******"+localChargeAmount.toString());
				}

				if(view.getLocalNetTransactionAmount()!=null && view.getLocalTransactionCurrencyId()!=null){
					BigDecimal netAmount=GetRound.roundBigDecimal((view.getLocalNetTransactionAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setTotalAmount(currencyQuoteName+"     ******"+netAmount.toString());
				}

				obj.setFutherInstructions(view.getInstructions());
				obj.setSourceOfIncome(view.getSourceOfIncomeDesc());
				obj.setIntermediataryBank(view.getBenefeciaryInterBank1());


				List<CollectionDetailView> collectionDetailList1= iPersonalRemittanceService.getCollectionDetailForRemittanceReceipt(view.getCompanyId(),view.getCollectionDocumentNo(),view.getCollectionDocFinanceYear(),view.getCollectionDocCode());

				CollectionDetailView collectionDetailView = collectionDetailList1.get(0);

				if(collectionDetailView.getNetAmount()!=null && view.getLocalTransactionCurrencyId()!=null){
					BigDecimal collectNetAmount=GetRound.roundBigDecimal((collectionDetailView.getNetAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setNetAmount(currencyQuoteName+"     ******"+collectNetAmount);
				}

				if(collectionDetailView.getPaidAmount()!=null && view.getLocalTransactionCurrencyId()!=null){
					BigDecimal collectPaidAmount=GetRound.roundBigDecimal((collectionDetailView.getPaidAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setPaidAmount(currencyQuoteName+"     ******"+collectPaidAmount);
				}

				if(collectionDetailView.getRefundedAmount()!=null && view.getLocalTransactionCurrencyId()!=null){
					BigDecimal collectRefundAmount=GetRound.roundBigDecimal((collectionDetailView.getRefundedAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setRefundedAmount(currencyQuoteName+"     ******"+collectRefundAmount);
				}

				obj.setSubReport(subReportPath);
				obj.setCollectionDetailList(calculateCollectionMode(view));
				
				//addedd new column
				BigDecimal lessLoyaltyEncash = BigDecimal.ZERO;
				BigDecimal amountPayable = BigDecimal.ZERO;
				List<CollectionPaymentDetailsView> collectionPmtDetailList= iPersonalRemittanceService.getCollectionPaymentDetailForRemittanceReceipt(view.getCompanyId(),view.getCollectionDocumentNo(),view.getCollectionDocFinanceYear(),view.getCollectionDocCode());
				for(CollectionPaymentDetailsView collPaymentDetailsView: collectionPmtDetailList){
					if(collPaymentDetailsView.getCollectionMode().equalsIgnoreCase(Constants.VOCHERCODE)){
						lessLoyaltyEncash = collPaymentDetailsView.getCollectAmount();
						amountPayable=amountPayable.add(collPaymentDetailsView.getCollectAmount());
					}else{
						amountPayable=amountPayable.add(collPaymentDetailsView.getCollectAmount());
					}
				}
				if(lessLoyaltyEncash.compareTo(BigDecimal.ZERO)==0){
					obj.setLessLoyaltyEncasement(null);					
				}else{
					BigDecimal loyaltyAmount=GetRound.roundBigDecimal((lessLoyaltyEncash),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setLessLoyaltyEncasement(currencyQuoteName+"     ******"+loyaltyAmount);
				}

				if(amountPayable!=null && currencyQuoteName!=null && view.getLocalTransactionCurrencyId()!=null){
					BigDecimal payable=GetRound.roundBigDecimal((amountPayable),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setAmountPayable(currencyQuoteName+"     ******"+payable);
				}

				//obj.setSignature(view.getCustomerSignature()); 
				// Rabil

				// Added by Rabil
				try {
					if (view.getCustomerSignatureClob() != null) {
						obj.setSignature(view.getCustomerSignatureClob().getSubString(1,(int) view.getCustomerSignatureClob().length()));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}

				if(employeeList!=null && employeeList.size()>0){
					try {
						Employee employee = employeeList.get(0);
						if (employee.getSignatureSpecimenClob() != null) {
							obj.setCashierSignature(employee.getSignatureSpecimenClob().getSubString(1,(int) employee.getSignatureSpecimenClob().length()));
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				
				//For Company information ADDED BY VISWA --START
				List<CompanyMaster> companyMaster =	iPersonalRemittanceService.getCompanyMaster(session.getCompanyId());
				StringBuffer engCompanyInfo = null;	
				StringBuffer arabicCompanyInfo=null;
				if(companyMaster.size()>0){
					engCompanyInfo = new StringBuffer();				
					if(companyMaster.get(0).getAddress1()!=null&&companyMaster.get(0).getAddress1().length()>0){
						engCompanyInfo=engCompanyInfo.append(companyMaster.get(0).getAddress1()+",");
					}
					if(companyMaster.get(0).getAddress2()!=null&&companyMaster.get(0).getAddress2().length()>0){
						engCompanyInfo=engCompanyInfo.append(companyMaster.get(0).getAddress2()+",");
					}
					if(companyMaster.get(0).getAddress3()!=null&&companyMaster.get(0).getAddress3().length()>0){
						engCompanyInfo=engCompanyInfo.append(companyMaster.get(0).getAddress3()+",");
					}
					if(companyMaster.get(0).getRegistrationNumber()!=null&&companyMaster.get(0).getRegistrationNumber().length()>0){
						engCompanyInfo=engCompanyInfo.append("C.R. "+companyMaster.get(0).getRegistrationNumber()+",");
					}
					if(companyMaster.get(0).getCapitalAmount()!=null&&companyMaster.get(0).getCapitalAmount().length()>0){
						engCompanyInfo=engCompanyInfo.append("Share Capital-"+companyMaster.get(0).getCapitalAmount());
					}				
					obj.setEngCompanyInfo(engCompanyInfo.toString());
					
					arabicCompanyInfo = new StringBuffer();
					
					if(companyMaster.get(0).getArabicAddress1()!=null&&companyMaster.get(0).getArabicAddress1().length()>0){
						arabicCompanyInfo=arabicCompanyInfo.append(companyMaster.get(0).getArabicAddress1());
					}
					if(companyMaster.get(0).getArabicAddress2()!=null&&companyMaster.get(0).getArabicAddress2().length()>0){
						arabicCompanyInfo=arabicCompanyInfo.append(companyMaster.get(0).getArabicAddress2()+",");
					}
					if(companyMaster.get(0).getArabicAddress3()!=null&&companyMaster.get(0).getArabicAddress3().length()>0){
						arabicCompanyInfo=arabicCompanyInfo.append(companyMaster.get(0).getArabicAddress3()+",");
					}
					if(companyMaster.get(0).getRegistrationNumber()!=null&&companyMaster.get(0).getRegistrationNumber().length()>0){
						arabicCompanyInfo=arabicCompanyInfo.append(Constants.CR + " " +companyMaster.get(0).getRegistrationNumber()+",");
					}					
					if(companyMaster.get(0).getCapitalAmount()!=null&&companyMaster.get(0).getCapitalAmount().length()>0){
						arabicCompanyInfo=arabicCompanyInfo.append(Constants.Share_Capital + " " +companyMaster.get(0).getCapitalAmount());
					}
					obj.setArabicCompanyInfo(arabicCompanyInfo.toString());
				}
				//For Company information ADDED BY VISWA --END
				
				if(obj.getFirstName() == null || obj.getFirstName().isEmpty()){
					List<CutomerDetailsView> customerList = iPersonalRemittanceService.getCustomerDetails(view.getCustomerId());

					if (!customerList.isEmpty()) {
						CutomerDetailsView cust = customerList.get(0);
						obj.setFirstName(cust.getCustomerName());
						if(cust.getContactNumber()!=null && !cust.getContactNumber().trim().equalsIgnoreCase("")){
							obj.setMobileNo(new BigDecimal(cust.getContactNumber()));
						}
						obj.setCivilId(cust.getIdNumber());
						Date sysdate1 = cust.getIdExp();
						if(sysdate1 != null){
							obj.setIdExpiryDate(new SimpleDateFormat("dd/MM/yyyy").format(sysdate1));
						}
						if(cust.getIdType()!= null && cust.getIdType().compareTo(new BigDecimal(Constants.CORPORATE_ID_TYPE))==0){
							obj.setLoyalityPointExpiring("");
						}
					}
				}


				remittanceApplList.add(obj);
			}

			// for foreign currency Sale report
			for (RemittanceApplicationView view : fcsaleList) {

				RemittanceReportBean obj = new RemittanceReportBean();
				
				//For Company information ADDED BY VISWA --START
				List<CompanyMaster> companyMaster =	iPersonalRemittanceService.getCompanyMaster(session.getCompanyId());
				StringBuffer engCompanyInfo = null;	
				StringBuffer arabicCompanyInfo=null;
				if(companyMaster.size()>0){
					engCompanyInfo = new StringBuffer();				
					if(companyMaster.get(0).getAddress1()!=null&&companyMaster.get(0).getAddress1().length()>0){
						engCompanyInfo=engCompanyInfo.append(companyMaster.get(0).getAddress1()+",");
					}
					if(companyMaster.get(0).getAddress2()!=null&&companyMaster.get(0).getAddress2().length()>0){
						engCompanyInfo=engCompanyInfo.append(companyMaster.get(0).getAddress2()+",");
					}
					if(companyMaster.get(0).getAddress3()!=null&&companyMaster.get(0).getAddress3().length()>0){
						engCompanyInfo=engCompanyInfo.append(companyMaster.get(0).getAddress3()+",");
					}
					if(companyMaster.get(0).getRegistrationNumber()!=null&&companyMaster.get(0).getRegistrationNumber().length()>0){
						engCompanyInfo=engCompanyInfo.append("C.R. "+companyMaster.get(0).getRegistrationNumber()+",");
					}
					if(companyMaster.get(0).getCapitalAmount()!=null&&companyMaster.get(0).getCapitalAmount().length()>0){
						engCompanyInfo=engCompanyInfo.append("Share Capital-"+companyMaster.get(0).getCapitalAmount());
					}				
					obj.setEngCompanyInfo(engCompanyInfo.toString());
					
					arabicCompanyInfo = new StringBuffer();
					
					if(companyMaster.get(0).getArabicAddress1()!=null&&companyMaster.get(0).getArabicAddress1().length()>0){
						arabicCompanyInfo=arabicCompanyInfo.append(companyMaster.get(0).getArabicAddress1());
					}
					if(companyMaster.get(0).getArabicAddress2()!=null&&companyMaster.get(0).getArabicAddress2().length()>0){
						arabicCompanyInfo=arabicCompanyInfo.append(companyMaster.get(0).getArabicAddress2()+",");
					}
					if(companyMaster.get(0).getArabicAddress3()!=null&&companyMaster.get(0).getArabicAddress3().length()>0){
						arabicCompanyInfo=arabicCompanyInfo.append(companyMaster.get(0).getArabicAddress3()+",");
					}
					if(companyMaster.get(0).getRegistrationNumber()!=null&&companyMaster.get(0).getRegistrationNumber().length()>0){
						arabicCompanyInfo=arabicCompanyInfo.append(Constants.CR + " "+companyMaster.get(0).getRegistrationNumber()+",");
					}						
					if(companyMaster.get(0).getCapitalAmount()!=null&&companyMaster.get(0).getCapitalAmount().length()>0){
						arabicCompanyInfo=arabicCompanyInfo.append(Constants.Share_Capital + " " +companyMaster.get(0).getCapitalAmount());
					}
					obj.setArabicCompanyInfo(arabicCompanyInfo.toString());
				}
				//For Company information ADDED BY VISWA --END

				if (view.getCustomerReference() != null && view.getFirstName() != null && view.getMiddleName() != null) {
					obj.setFirstName(view.getCustomerReference().toString() + "/" + view.getFirstName() + " " + view.getMiddleName());
				} else if (view.getCustomerReference() == null && view.getFirstName() != null && view.getMiddleName() != null) {
					obj.setFirstName(view.getFirstName() + " " + view.getMiddleName());
				} else if (view.getCustomerReference() == null && view.getFirstName() == null && view.getMiddleName() != null) {
					obj.setFirstName(view.getMiddleName());
				} else if (view.getCustomerReference() != null && view.getFirstName() == null && view.getMiddleName() != null) {
					obj.setFirstName(view.getCustomerReference().toString() + " / " + view.getMiddleName());
				} else if (view.getCustomerReference() == null && view.getFirstName() != null && view.getMiddleName() == null) {
					obj.setFirstName(view.getFirstName());
				} else if (view.getCustomerReference() != null && view.getFirstName() == null && view.getMiddleName() == null) {
					obj.setFirstName(view.getCustomerReference().toString());
				} else if (view.getCustomerReference() != null && view.getFirstName() != null && view.getMiddleName() == null) {
					obj.setFirstName(view.getCustomerReference().toString() + " " + view.getFirstName());
				}

				if(view.getContactNumber()!=null && !view.getContactNumber().trim().equalsIgnoreCase("")){
					obj.setMobileNo(new BigDecimal(view.getContactNumber().trim()));
				}
				obj.setCivilId(view.getIdentityInt());
				Date sysdate = view.getIdentityExpiryDate();
				if(sysdate!=null){
					obj.setIdExpiryDate(new SimpleDateFormat("dd/MM/yyyy").format(sysdate));
				}


				HashMap<String, String> loyaltiPoints  =iPersonalRemittanceService.getloyalityPointsFromProcedure(view.getCustomerReference(), view.getDocumentDate());

				String prLtyStr1 =loyaltiPoints.get("P_LTY_STR1");
				String prLtyStr2 =loyaltiPoints.get("P_LTY_STR2");
				String prInsStr1 =loyaltiPoints.get("P_INS_STR1");
				String prInsStr2 =loyaltiPoints.get("P_INS_STR2");
				String prInsStrAr1 =loyaltiPoints.get("P_INS_STR_AR1");
				String prInsStrAr2 =loyaltiPoints.get("P_INS_STR_AR2");

				if(!prLtyStr1.trim().equals("") && !prLtyStr2.trim().equals("")){
					obj.setLoyalityPointExpiring(prLtyStr1+"  \n"+prLtyStr2);
				}else if(!prLtyStr1.trim().equals("")){
					obj.setLoyalityPointExpiring(prLtyStr1);
				}else if(!prLtyStr2.trim().equals("")){
					obj.setLoyalityPointExpiring(prLtyStr2);
				}

				String insurence1 ="";

				if(!prInsStr1.trim().equals("") && !prInsStrAr1.trim().equals("")){
					insurence1 = prInsStr1+"  \n"+prInsStrAr1;
				}else if(!prInsStr1.trim().equals("")){
					insurence1 = prInsStr1;
				}else if(!prInsStrAr1.trim().equals("")){
					insurence1 = prInsStrAr1;
				}

				String insurence2 ="";

				if(!prInsStr2.trim().equals("") && !prInsStrAr2.trim().equals("")){
					insurence2 = prInsStr2+"  \n"+prInsStrAr2;
				}else if(!prInsStr2.trim().equals("")){
					insurence2 = prInsStr2;
				}else if(!prInsStrAr2.trim().equals("")){
					insurence2 = prInsStrAr2;
				}

				if(!insurence1.trim().equals("") && !insurence2.trim().equals("")){
					obj.setInsurence1(insurence1+" \n"+insurence2);
				}else if(!insurence1.trim().equals("")){
					obj.setInsurence1(insurence1);
				}else if(!insurence2.trim().equals("")){
					obj.setInsurence1(insurence2);
				}
				
				//addedd new column
				BigDecimal lessLoyaltyEncash = BigDecimal.ZERO;
				BigDecimal amountPayable = BigDecimal.ZERO;
				List<CollectionPaymentDetailsView> collectionPmtDetailList= iPersonalRemittanceService.getCollectionPaymentDetailForRemittanceReceipt(view.getCompanyId(),view.getCollectionDocumentNo(),view.getCollectionDocFinanceYear(),view.getCollectionDocCode());
				for(CollectionPaymentDetailsView collPaymentDetailsView: collectionPmtDetailList){
					if(collPaymentDetailsView.getCollectionMode().equalsIgnoreCase(Constants.VOCHERCODE)){
						lessLoyaltyEncash = collPaymentDetailsView.getCollectAmount();
						amountPayable=amountPayable.add(collPaymentDetailsView.getCollectAmount());
					}else{
						amountPayable=amountPayable.add(collPaymentDetailsView.getCollectAmount());
					}
				}
				if(lessLoyaltyEncash.compareTo(BigDecimal.ZERO)==0){
					obj.setLessLoyaltyEncasement(null);					
				}else{
					BigDecimal loyaltyAmount=GetRound.roundBigDecimal((lessLoyaltyEncash),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setLessLoyaltyEncasement(currencyQuoteName+"     ******"+loyaltyAmount);
				}

				if(amountPayable!=null && currencyQuoteName!=null && view.getLocalTransactionCurrencyId()!=null){
					BigDecimal payable=GetRound.roundBigDecimal((amountPayable),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setAmountPayable(currencyQuoteName+"     ******"+payable);
				}


				obj.setLocation(view.getCountryBranchName());
				obj.setPhoneNumber(view.getPhoneNumber());
				obj.setDate(currentDate);
				//obj.setUserName(session.getUserName());
				obj.setUserName(view.getCreatedBy());
				

				if(view.getDocumentFinancialYear()!=null && view.getCollectionDocumentNo()!=null){
					obj.setReceiptNo(view.getDocumentFinancialYear()+" / "+view.getCollectionDocumentNo());
				}else if(view.getCollectionDocumentNo()!=null){
					obj.setReceiptNo(view.getCollectionDocumentNo().toString());
				}
				String saleCurrency = specialCustomerDealRequestService.getCurrencyForUpdate(view.getForeignCurrencyId());


				obj.setCurrencyQuoteName(saleCurrency);

				String saleCurrencyCode = generalService.getCurrencyQuote(view.getForeignCurrencyId());

				if(view.getDocumentFinancialYear()!=null && view.getDocumentNo()!=null){
					obj.setTransactionNo(view.getDocumentFinancialYear()+" / "+view.getDocumentNo());
				}else if(view.getDocumentNo()!=null){
					obj.setTransactionNo(view.getDocumentNo().toString());
				}

				if (view.getForeignTransactionAmount() != null && saleCurrencyCode != null) {
					BigDecimal foreignTransationAmount=GetRound.roundBigDecimal((view.getForeignTransactionAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getForeignCurrencyId()));	
					obj.setSaleAmount( saleCurrencyCode+"     ******"+foreignTransationAmount.toString());
				} 



				if( view.getLocalTransactionCurrencyId()!=null && currencyQuoteName!=null){
					BigDecimal transationAmount=GetRound.roundBigDecimal((view.getLocalTransactionAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setPurchageAmount(currencyQuoteName+"     ******"+transationAmount.toString());
				}

				if(saleCurrencyCode!=null && currencyQuoteName!=null && view.getExchangeRateApplied()!=null){
					obj.setExchangeRate(saleCurrencyCode+" / "+currencyQuoteName+"     "+view.getExchangeRateApplied().toString());
				}

				if(view.getLocalTransactionAmount()!=null && view.getLocalTransactionCurrencyId()!=null && currencyQuoteName!=null){
					BigDecimal transationAmount=GetRound.roundBigDecimal((view.getLocalTransactionAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setLocalTransactionAmount(currencyQuoteName+"     ******"+transationAmount.toString());
				}

				if(view.getLocalCommissionAmount()!=null && view.getLocalTransactionCurrencyId()!=null && currencyQuoteName!=null){
					BigDecimal localCommitionAmount=GetRound.roundBigDecimal((view.getLocalCommissionAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setCommision(currencyQuoteName+"     ******"+localCommitionAmount.toString());
				}

				List<PurposeOfRemittanceView>  purposeOfRemittanceList =   iPersonalRemittanceService.getPurposeOfRemittance(view.getDocumentNo(),view.getDocumentFinancialYear());

				/*if(purposeOfRemittanceList!=null && purposeOfRemittanceList.size()>0){
					obj.setPerposeOfRemittance(purposeOfRemittanceList.get(0).getAmiecDescription());
				}*/
				obj.setSourceOfIncome(view.getSourceOfIncomeDesc());
				obj.setPerposeOfRemittance(view.getPurposeOfTransaction());


				List<CollectionDetailView> collectionDetailList1= iPersonalRemittanceService.getCollectionDetailForRemittanceReceipt(view.getCompanyId(),view.getCollectionDocumentNo(),view.getCollectionDocFinanceYear(),view.getCollectionDocCode());
				if(collectionDetailList1.size()>0){
					CollectionDetailView collectionDetailView = collectionDetailList1.get(0);
					if(collectionDetailView.getNetAmount()!=null && currencyQuoteName!=null && view.getLocalTransactionCurrencyId()!=null){
						BigDecimal collectNetAmount=GetRound.roundBigDecimal((collectionDetailView.getNetAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
						obj.setNetAmount(currencyQuoteName+"     ******"+collectNetAmount);
					}
					if(collectionDetailView.getPaidAmount()!=null && currencyQuoteName!=null && view.getLocalTransactionCurrencyId()!=null){
						BigDecimal collectPaidAmount=GetRound.roundBigDecimal((collectionDetailView.getPaidAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
						obj.setPaidAmount(currencyQuoteName+"     ******"+collectPaidAmount);
					}
					if(collectionDetailView.getRefundedAmount()!=null && currencyQuoteName!=null && view.getLocalTransactionCurrencyId()!=null){
						BigDecimal collectRefundAmount=GetRound.roundBigDecimal((collectionDetailView.getRefundedAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
						obj.setRefundedAmount(currencyQuoteName+"     ******"+collectRefundAmount);
					}
					obj.setCollectionDetailList(calculateCollectionMode(view));
				}

				obj.setSubReport(subReportPath);
				//obj.setUserName(session.getUserName());
				obj.setUserName(view.getCreatedBy());

				
				try {
					if (view.getCustomerSignatureClob() != null) {
						obj.setSignature(view.getCustomerSignatureClob().getSubString(1,(int) view.getCustomerSignatureClob().length()));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}

				if(employeeList!=null && employeeList.size()>0){
					try {
						Employee employee = employeeList.get(0);
						if (employee.getSignatureSpecimenClob() != null) {
							obj.setCashierSignature(employee.getSignatureSpecimenClob().getSubString(1,(int) employee.getSignatureSpecimenClob().length()));
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				
				if(obj.getFirstName() == null || obj.getFirstName().isEmpty()){
					List<CutomerDetailsView> customerList = iPersonalRemittanceService.getCustomerDetails(view.getCustomerId());

					if (!customerList.isEmpty()) {
						CutomerDetailsView cust = customerList.get(0);
						obj.setFirstName(cust.getCustomerName());
						if(cust.getContactNumber()!=null && !cust.getContactNumber().trim().equalsIgnoreCase("")){
							obj.setMobileNo(new BigDecimal(cust.getContactNumber()));
						}
						obj.setCivilId(cust.getIdNumber());
						Date sysdate1 = cust.getIdExp();
						if(sysdate1 != null){
							obj.setIdExpiryDate(new SimpleDateFormat("dd/MM/yyyy").format(sysdate1));
						}
					}
				}


				fcsaleAppList.add(obj);


			}

			//for Main Remittance Receipt report (Remittance Receipt and Fc Sale Application)
			RemittanceReceiptSubreport remittanceObj = new RemittanceReceiptSubreport();

			remittanceObj.setWaterMarkLogoPath(waterMark);
			remittanceObj.setWaterMarkCheck(true);
			remittanceObj.setFcsaleAppList(fcsaleAppList);
			remittanceObj.setRemittanceApplList(remittanceApplList);
			remittanceObj.setSubReport(subReportPath);

			if (fcsaleAppList!=null && fcsaleAppList.size()>0) {
				remittanceObj.setFcsaleApplicationCheck(true);
			} /*else {
remittanceObj.setFcsaleApplicationCheck(false);
}*/
			if(remittanceApplList!=null && remittanceApplList.size()>0){
				remittanceObj.setRemittanceReceiptCheck(true);
			}/*else{
remittanceObj.setRemittanceReceiptCheck(false);
}*/

			remittanceReceiptSubreportList.add(remittanceObj);

		} else {
			RequestContext.getCurrentInstance().execute("noDataForReport.show();");
			return;
		}


	}
	public Boolean getExitButtonRender() {
		return exitButtonRender;
	}
	public void setExitButtonRender(Boolean exitButtonRender) {
		this.exitButtonRender = exitButtonRender;
	}
	public Boolean getBackButtonRender() {
		return backButtonRender;
	}
	public void setBackButtonRender(Boolean backButtonRender) {
		this.backButtonRender = backButtonRender;
	}
	public BigDecimal getCollectionDocNo() {
		return collectionDocNo;
	}
	public void setCollectionDocNo(BigDecimal collectionDocNo) {
		this.collectionDocNo = collectionDocNo;
	}

	public Boolean getRenderForHIGHVALUETrnx() {
		return renderForHIGHVALUETrnx;
	}
	public void setRenderForHIGHVALUETrnx(Boolean renderForHIGHVALUETrnx) {
		this.renderForHIGHVALUETrnx = renderForHIGHVALUETrnx;
	}

	public Boolean getRenderForReceiptEnquiry() {
		return renderForReceiptEnquiry;
	}
	public void setRenderForReceiptEnquiry(Boolean renderForReceiptEnquiry) {
		this.renderForReceiptEnquiry = renderForReceiptEnquiry;
	}
	
	public void goBackToRemittanceBranchWiseEnquiryScreen(){
		try {
			clearAll();
			setDocNumber(null);
			setReceiptNo(null);
			setDocYear(null);
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/remittanceBranchWiseEnquiry.xhtml");
		}catch(Exception exception){
			LOGGER.error("Exception "+exception);
		}
	}

	// high value trnx back action
	public void goBackToHighValueTrnxBackButton(){
		try {
			setRenderForHIGHVALUETrnx(false);
			clearAll();
			setDocNumber(null);
			setReceiptNo(null);
			setDocYear(null);
			FacesContext.getCurrentInstance().getExternalContext().redirect("../common/highValuAmlAuth.xhtml");
		}catch(Exception exception){
			LOGGER.error("Exception "+exception);
		}
	}
	
	
	
	

	// reprint link for collection year and document number
	public void generateReceiptReprint() throws JRException, IOException, ParseException {
		OutputStream outstream=null;
		try {
			if (getReceiptNo() == null || getReceipYear() == null) {
				setErrorMsg("Receipt Document Year and Number is Empty");
				RequestContext.getCurrentInstance().execute("csp.show();");
				return;
			}
			int i = fetchRemittanceReceiptReportData(getReceiptNo(), getReceipYear());
			if (i == 0) {
				setErrorMsg("Data Not Found");
				RequestContext.getCurrentInstance().execute("csp.show();");
				return;
			} else {
				remittanceReceiptReprintReport();
				HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
				httpServletResponse.setHeader("Cache-Control", "cache, must-revalidate");
				httpServletResponse.addHeader("Content-disposition", "attachment; filename=RemittanceReceiptReport.pdf");
				/*ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
				JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
				FacesContext.getCurrentInstance().responseComplete();*/

				byte[] pdfasbytes = JasperExportManager.exportReportToPdf(jasperPrint);

				outstream = httpServletResponse.getOutputStream();
				httpServletResponse.setContentType("application/pdf");
				httpServletResponse.setContentLength(pdfasbytes.length);
				String strSettings = "inline;filename=\"RemittanceReceiptReport.pdf\"";
				httpServletResponse.setHeader("Content-Disposition", strSettings);
				outstream.write(pdfasbytes, 0, pdfasbytes.length);
				outstream.flush();
				FacesContext.getCurrentInstance().responseComplete();


			}
		} catch (Exception e) {
			System.out.println("exception" + e.getMessage());
		}finally{
			if(outstream!=null){
				outstream.close();
			}
		}
	}
	
	// to check null
	private String nullCheck(String custname) {
		return custname == null ? "" : custname;
	}

	private int fetchRemittanceReceiptReportData(BigDecimal documentNumber, BigDecimal documentYear) {
		LOGGER.info("Document Number=="+documentNumber);
		collectionViewList.clear();
		remittanceReceiptSubreportList = new ArrayList<RemittanceReceiptSubreport>();



		List<RemittanceApplicationView> remittanceApplicationList = new ArrayList<RemittanceApplicationView>();

		List<RemittanceApplicationView> fcsaleList = new ArrayList<RemittanceApplicationView>();

		List<RemittanceReportBean> remittanceApplList = new ArrayList<RemittanceReportBean>();

		List<RemittanceReportBean> fcsaleAppList = new ArrayList<RemittanceReportBean>();

		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath = ctx.getRealPath("//");
		String subReportPath = realPath + Constants.SUB_REPORT_PATH;
		String waterMark = realPath+Constants.REPORT_WATERMARK_LOGO;

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String currentDate = dateFormat.format(new Date());
		int noOfTransactions = 0;

		String currencyQuoteName = generalService.getCurrencyQuote(new BigDecimal(session.getCurrencyId()));


		List<Employee> employeeList = iPersonalRemittanceService.getEmployeeDetails(session.getUserName());

		List<RemittanceApplicationView> remittanceViewlist = iPersonalRemittanceService.getRecordsForRemittanceReceiptReport(documentNumber, documentYear, Constants.DOCUMENT_CODE_FOR_COLLECT_TRANSACTION);
		LOGGER.info("Remittance View List Size is======"+remittanceViewlist.size());
		if (remittanceViewlist.size() > 0) {

			for (RemittanceApplicationView remittanceAppview : remittanceViewlist) {

				if (remittanceAppview.getApplicationTypeDesc().equalsIgnoreCase("REMITTANCE")) {
					remittanceApplicationList.add(remittanceAppview);
					noOfTransactions= noOfTransactions+1;
				} else if (remittanceAppview.getApplicationTypeDesc().equalsIgnoreCase("FOREIGN CURRENCY SALE")) {
					fcsaleList.add(remittanceAppview);
					noOfTransactions= noOfTransactions+1;
				}
			}

			//remittance List
			for (RemittanceApplicationView view : remittanceApplicationList) {

				RemittanceReportBean obj = new RemittanceReportBean();

				if (view.getCustomerReference() != null && view.getFirstName() != null && view.getMiddleName() != null && view.getLastName()!=null) {
					obj.setFirstName(view.getCustomerReference().toString() + " / " + view.getFirstName() + " " + view.getMiddleName()+" "+view.getLastName());
				} else if (view.getCustomerReference() == null && view.getFirstName() != null && view.getMiddleName() != null && view.getLastName()!=null) {
					obj.setFirstName(view.getFirstName() + " "+ view.getMiddleName()+" "+view.getLastName());
				} else if (view.getCustomerReference() == null && view.getFirstName() == null && view.getMiddleName() != null && view.getLastName()!=null) {
					obj.setFirstName(view.getMiddleName()+" "+view.getLastName());
				} else if (view.getCustomerReference() != null && view.getFirstName() == null && view.getMiddleName() != null && view.getLastName()!=null) {
					obj.setFirstName(view.getCustomerReference().toString() + " / " + view.getMiddleName()+" "+view.getLastName());
				} else if (view.getCustomerReference() == null && view.getFirstName() != null && view.getMiddleName() == null && view.getLastName()!=null) {
					obj.setFirstName(view.getFirstName()+" "+view.getLastName());
				} else if (view.getCustomerReference() != null && view.getFirstName() == null && view.getMiddleName() == null && view.getLastName()==null) {
					obj.setFirstName(view.getCustomerReference().toString());
				} else if (view.getCustomerReference() != null && view.getFirstName() != null && view.getMiddleName() == null && view.getLastName()!=null) {
					obj.setFirstName(view.getCustomerReference().toString() + " / " + view.getFirstName()+" "+view.getLastName());
				} else if (view.getCustomerReference() != null) {
					obj.setFirstName(view.getCustomerReference().toString() + " / " + nullCheck(view.getFirstName()) + " " + nullCheck(view.getMiddleName()) +" "+ nullCheck(view.getLastName()));
				}
				
				if(view.getContactNumber()!=null && !view.getContactNumber().trim().equalsIgnoreCase("")){
					obj.setMobileNo(new BigDecimal(view.getContactNumber().trim()));
				}
				obj.setCivilId(view.getIdentityInt());
				Date sysdate = view.getIdentityExpiryDate();
				if(sysdate != null){
					obj.setIdExpiryDate(new SimpleDateFormat("dd/MM/yyy").format(sysdate));
				}

				//obj.setLocation(sessionStateManage.getLocation());
				obj.setLocation(view.getCountryBranchName());

				if(view.getDocumentFinancialYear()!=null && view.getCollectionDocumentNo()!=null){
					obj.setReceiptNo(view.getDocumentFinancialYear()+" / "+view.getCollectionDocumentNo());
				}else if(view.getCollectionDocumentNo()!=null){
					obj.setReceiptNo(view.getCollectionDocumentNo().toString());
				}

				if(view.getDocumentFinancialYear()!=null && view.getDocumentNo()!=null){
					obj.setTransactionNo(view.getDocumentFinancialYear()+" / "+view.getDocumentNo());
				}else if(view.getDocumentNo()!=null){
					obj.setTransactionNo(view.getDocumentNo().toString());
				}

				//obj.setDate(currentDate);
				Date docDate = view.getDocumentDate();
				if(docDate != null){
					obj.setDate(new SimpleDateFormat("dd/MM/yyy").format(docDate));
				}

				obj.setBeneficiaryName(view.getBeneficiaryName());
				obj.setBenefeciaryBankName(view.getBeneficiaryBank());
				obj.setBenefeciaryBranchName(view.getBenefeciaryBranch());
				obj.setBenefeciaryAccountNumber(view.getBenefeciaryAccountNo());
				obj.setNoOfTransaction(new BigDecimal(noOfTransactions));
				obj.setPhoneNumber(view.getPhoneNumber());
				//obj.setUserName(sessionStateManage.getUserName());
				obj.setUserName(view.getCreatedBy());
				obj.setPinNo(view.getPinNo() );

				HashMap<String, String> loyaltiPoints  =iPersonalRemittanceService.getloyalityPointsFromProcedure(view.getCustomerReference(), view.getDocumentDate());

				String prLtyStr1 =loyaltiPoints.get("P_LTY_STR1");
				String prLtyStr2 =loyaltiPoints.get("P_LTY_STR2");
				String prInsStr1 =loyaltiPoints.get("P_INS_STR1");
				String prInsStr2 =loyaltiPoints.get("P_INS_STR2");
				String prInsStrAr1 =loyaltiPoints.get("P_INS_STR_AR1");
				String prInsStrAr2 =loyaltiPoints.get("P_INS_STR_AR2");

				if(!prLtyStr1.trim().equals("") && !prLtyStr2.trim().equals("")){
					obj.setLoyalityPointExpiring(prLtyStr1+"  \n"+prLtyStr2);
				}else if(!prLtyStr1.trim().equals("")){
					obj.setLoyalityPointExpiring(prLtyStr1);
				}else if(!prLtyStr2.trim().equals("")){
					obj.setLoyalityPointExpiring(prLtyStr2);
				}

				if(!prInsStr1.trim().equals("") && !prInsStrAr1.trim().equals("")){
					obj.setInsurence1(prInsStr1+"  \n"+prInsStrAr1);
				}else if(!prInsStr1.trim().equals("")){
					obj.setInsurence1(prInsStr1);
				}else if(!prInsStrAr1.trim().equals("")){
					obj.setInsurence1(prInsStrAr1);
				}

				if(!prInsStr2.trim().equals("") && !prInsStrAr2.trim().equals("")){
					obj.setInsurence2(prInsStr2+"  \n"+prInsStrAr2);
				}else if(!prInsStr2.trim().equals("")){
					obj.setInsurence2(prInsStr2);
				}else if(!prInsStrAr2.trim().equals("")){
					obj.setInsurence2(prInsStrAr2);
				}

				if (view.getBeneCityName() != null && view.getBeneDistrictName() != null && view.getBeneStateName() != null) {
					obj.setAddress(view.getBeneCityName() + "," + view.getBeneDistrictName() + "," + view.getBeneStateName());
				} else if (view.getBeneCityName() == null && view.getBeneDistrictName() != null && view.getBeneStateName() != null) {
					obj.setAddress(view.getBeneDistrictName() + "," + view.getBeneStateName());
				} else if (view.getBeneCityName() == null && view.getBeneDistrictName() == null && view.getBeneStateName() != null) {
					obj.setAddress(view.getBeneStateName());
				} else if (view.getBeneCityName() != null && view.getBeneDistrictName() == null && view.getBeneStateName() != null) {
					obj.setAddress(view.getBeneCityName() + ", " + view.getBeneStateName());
				} else if (view.getBeneCityName() != null && view.getBeneDistrictName() == null && view.getBeneStateName() == null) {
					obj.setAddress(view.getBeneCityName());
				} else if (view.getBeneCityName() == null && view.getBeneDistrictName() != null && view.getBeneStateName() == null) {
					obj.setAddress(view.getBeneDistrictName());
				} else if (view.getBeneCityName() != null && view.getBeneDistrictName() != null && view.getBeneStateName() == null) {
					obj.setAddress(view.getBeneCityName() + ", " + view.getBeneDistrictName());
				}

				if (view.getRemittanceDescription() != null && view.getDeliveryDescription() != null) {
					obj.setPaymentChannel(view.getRemittanceDescription()+ " - " + view.getDeliveryDescription());
				} else if (view.getRemittanceDescription() == null && view.getDeliveryDescription() != null) {
					obj.setPaymentChannel(view.getDeliveryDescription());
				} else if (view.getRemittanceDescription() != null && view.getDeliveryDescription() == null) {
					obj.setPaymentChannel(view.getRemittanceDescription());
				}

				String currencyAndAmount=null;
				BigDecimal foreignTransationAmount=GetRound.roundBigDecimal((view.getForeignTransactionAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getForeignCurrencyId()));
				if(view.getCurrencyQuoteName()!=null && foreignTransationAmount!=null){
					currencyAndAmount = view.getCurrencyQuoteName()+"     ******"+foreignTransationAmount;
				}
				obj.setCurrencyAndAmount(currencyAndAmount);
				List<PurposeOfRemittanceView>  purposeOfRemittanceList =   iPersonalRemittanceService.getPurposeOfRemittance(view.getDocumentNo(),view.getDocumentFinancialYear());

				List<PurposeOfRemittanceReportBean> purposeOfRemitTrList1=new ArrayList<PurposeOfRemittanceReportBean>( );
				for(PurposeOfRemittanceView purposeObj :purposeOfRemittanceList){
					PurposeOfRemittanceReportBean beanObj=new PurposeOfRemittanceReportBean();
					beanObj.setPurposeOfTrField(purposeObj.getFlexfieldName());
					beanObj.setPurposeOfTrfieldArabic(null);
					beanObj.setPurposeOfTrValue(purposeObj.getFlexiFieldValue() );
					purposeOfRemitTrList1.add(beanObj);
				}

				if(purposeOfRemitTrList1.size()>0){
					obj.setPurposeOfRemitTrList(purposeOfRemitTrList1);
				}

				if(view.getCurrencyQuoteName()!=null && currencyQuoteName!=null && view.getExchangeRateApplied()!=null){
					obj.setExchangeRate(view.getCurrencyQuoteName()+" / "+currencyQuoteName+"     "+view.getExchangeRateApplied().toString());
				}

				if(view.getLocalTransactionAmount()!=null && view.getLocalTransactionCurrencyId()!=null){
					BigDecimal transationAmount=GetRound.roundBigDecimal((view.getLocalTransactionAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setLocalTransactionAmount(currencyQuoteName+"     ******"+transationAmount.toString());
				}

				if(view.getLocalCommissionAmount()!=null && view.getLocalTransactionCurrencyId()!=null){
					BigDecimal localCommitionAmount=GetRound.roundBigDecimal((view.getLocalCommissionAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setCommision(currencyQuoteName+"     ******"+localCommitionAmount.toString());
				}

				if(view.getLocalChargeAmount()!=null && view.getLocalTransactionCurrencyId()!=null){
					BigDecimal localChargeAmount=GetRound.roundBigDecimal((view.getLocalChargeAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setOtherCharges(currencyQuoteName+"     ******"+localChargeAmount.toString());
				}

				if(view.getLocalNetTransactionAmount()!=null && view.getLocalTransactionCurrencyId()!=null){
					BigDecimal netAmount=GetRound.roundBigDecimal((view.getLocalNetTransactionAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setTotalAmount(currencyQuoteName+"     ******"+netAmount.toString());
				}

				obj.setFutherInstructions(view.getInstructions());
				obj.setSourceOfIncome(view.getSourceOfIncomeDesc());
				obj.setIntermediataryBank(view.getBenefeciaryInterBank1());


				List<CollectionDetailView> collectionDetailList1= iPersonalRemittanceService.getCollectionDetailForRemittanceReceipt(view.getCompanyId(),view.getCollectionDocumentNo(),view.getCollectionDocFinanceYear(),view.getCollectionDocCode());

				CollectionDetailView collectionDetailView = collectionDetailList1.get(0);

				if(collectionDetailView.getNetAmount()!=null && view.getLocalTransactionCurrencyId()!=null){
					BigDecimal collectNetAmount=GetRound.roundBigDecimal((collectionDetailView.getNetAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setNetAmount(currencyQuoteName+"     ******"+collectNetAmount);
				}

				if(collectionDetailView.getPaidAmount()!=null && view.getLocalTransactionCurrencyId()!=null){
					BigDecimal collectPaidAmount=GetRound.roundBigDecimal((collectionDetailView.getPaidAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setPaidAmount(currencyQuoteName+"     ******"+collectPaidAmount);
				}

				if(collectionDetailView.getRefundedAmount()!=null && view.getLocalTransactionCurrencyId()!=null){
					BigDecimal collectRefundAmount=GetRound.roundBigDecimal((collectionDetailView.getRefundedAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setRefundedAmount(currencyQuoteName+"     ******"+collectRefundAmount);
				}

				obj.setSubReport(subReportPath);
				obj.setCollectionDetailList(calculateCollectionMode(view));

				//addedd new column
				BigDecimal lessLoyaltyEncash = BigDecimal.ZERO;
				BigDecimal amountPayable = BigDecimal.ZERO;
				List<CollectionPaymentDetailsView> collectionPmtDetailList= iPersonalRemittanceService.getCollectionPaymentDetailForRemittanceReceipt(view.getCompanyId(),view.getCollectionDocumentNo(),view.getCollectionDocFinanceYear(),view.getCollectionDocCode());
				for(CollectionPaymentDetailsView collPaymentDetailsView: collectionPmtDetailList){
					if(collPaymentDetailsView.getCollectionMode().equalsIgnoreCase(Constants.VOCHERCODE)){
						lessLoyaltyEncash = collPaymentDetailsView.getCollectAmount();
						amountPayable=amountPayable.add(collPaymentDetailsView.getCollectAmount());
					}else{
						amountPayable=amountPayable.add(collPaymentDetailsView.getCollectAmount());
					}
				}
				if(lessLoyaltyEncash.compareTo(BigDecimal.ZERO)==0){
					obj.setLessLoyaltyEncasement(null);					
				}else{
					BigDecimal loyaltyAmount=GetRound.roundBigDecimal((lessLoyaltyEncash),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setLessLoyaltyEncasement(currencyQuoteName+"     ******"+loyaltyAmount);
				}

				if(amountPayable!=null && currencyQuoteName!=null && view.getLocalTransactionCurrencyId()!=null){
					BigDecimal payable=GetRound.roundBigDecimal((amountPayable),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setAmountPayable(currencyQuoteName+"     ******"+payable);
				}


				//obj.setSignature(view.getCustomerSignature()); 
				// Rabil
				// Added by Rabil
				try {
					if (view.getCustomerSignatureClob() != null) {
						obj.setSignature(view.getCustomerSignatureClob().getSubString(1,(int) view.getCustomerSignatureClob().length()));
					}
				} catch (SQLException e) {
					LOGGER.info( "Exception Occured While Report2 "+e.getMessage());
					setErrorMsg("Exception Occured While Report "+e.getMessage());
				}

				if(employeeList!=null && employeeList.size()>0){
					try {
						Employee employee = employeeList.get(0);
						if (employee.getSignatureSpecimenClob() != null) {
							obj.setCashierSignature(employee.getSignatureSpecimenClob().getSubString(1,(int) employee.getSignatureSpecimenClob().length()));
						}
					} catch (SQLException e) {
						LOGGER.info( "Exception Occured While Report3 "+e.getMessage());
						setErrorMsg("Exception Occured While Report "+e.getMessage() );
					}
				}
				//For Company information ADDED BY VISWA --START
				List<CompanyMaster> companyMaster =	iPersonalRemittanceService.getCompanyMaster(session.getCompanyId());
				StringBuffer engCompanyInfo = null;	
				StringBuffer arabicCompanyInfo=null;
				if(companyMaster.size()>0){
					engCompanyInfo = new StringBuffer();				
					if(companyMaster.get(0).getAddress1()!=null&&companyMaster.get(0).getAddress1().length()>0){
						engCompanyInfo=engCompanyInfo.append(companyMaster.get(0).getAddress1()+",");
					}
					if(companyMaster.get(0).getAddress2()!=null&&companyMaster.get(0).getAddress2().length()>0){
						engCompanyInfo=engCompanyInfo.append(companyMaster.get(0).getAddress2()+",");
					}
					if(companyMaster.get(0).getAddress3()!=null&&companyMaster.get(0).getAddress3().length()>0){
						engCompanyInfo=engCompanyInfo.append(companyMaster.get(0).getAddress3()+",");
					}
					if(companyMaster.get(0).getRegistrationNumber()!=null&&companyMaster.get(0).getRegistrationNumber().length()>0){
						engCompanyInfo=engCompanyInfo.append("C.R. "+companyMaster.get(0).getRegistrationNumber()+",");
					}
					if(companyMaster.get(0).getCapitalAmount()!=null&&companyMaster.get(0).getCapitalAmount().length()>0){
						engCompanyInfo=engCompanyInfo.append("Share Capital-"+companyMaster.get(0).getCapitalAmount());
					}				
					obj.setEngCompanyInfo(engCompanyInfo.toString());

					arabicCompanyInfo = new StringBuffer();

					if(companyMaster.get(0).getArabicAddress1()!=null&&companyMaster.get(0).getArabicAddress1().length()>0){
						arabicCompanyInfo=arabicCompanyInfo.append(companyMaster.get(0).getArabicAddress1());
					}
					if(companyMaster.get(0).getArabicAddress2()!=null&&companyMaster.get(0).getArabicAddress2().length()>0){
						arabicCompanyInfo=arabicCompanyInfo.append(companyMaster.get(0).getArabicAddress2()+",");
					}
					if(companyMaster.get(0).getArabicAddress3()!=null&&companyMaster.get(0).getArabicAddress3().length()>0){
						arabicCompanyInfo=arabicCompanyInfo.append(companyMaster.get(0).getArabicAddress3()+",");
					}
					if(companyMaster.get(0).getRegistrationNumber()!=null&&companyMaster.get(0).getRegistrationNumber().length()>0){
						arabicCompanyInfo=arabicCompanyInfo.append(Constants.CR +companyMaster.get(0).getRegistrationNumber()+",");
					}						
					if(companyMaster.get(0).getCapitalAmount()!=null&&companyMaster.get(0).getCapitalAmount().length()>0){
						arabicCompanyInfo=arabicCompanyInfo.append(Constants.Share_Capital +companyMaster.get(0).getCapitalAmount());
					}
					obj.setArabicCompanyInfo(arabicCompanyInfo.toString());
				}
				//For Company information ADDED BY VISWA --END

				if(obj.getFirstName() == null || obj.getFirstName().isEmpty()){
					List<CutomerDetailsView> customerList = iPersonalRemittanceService.getCustomerDetails(view.getCustomerId());

					if (customerList.size() > 0) {
						CutomerDetailsView cust = customerList.get(0);
						obj.setFirstName(cust.getCustomerName());
						if(cust.getContactNumber()!=null && !cust.getContactNumber().trim().equalsIgnoreCase("")){
							obj.setMobileNo(new BigDecimal(cust.getContactNumber()));
						}
						obj.setCivilId(cust.getIdNumber());
						Date sysdate1 = cust.getIdExp();
						if(sysdate1 != null){
							obj.setIdExpiryDate(new SimpleDateFormat("dd/MM/yyyy").format(sysdate1));
						}	
							if(cust.getIdType()!= null && cust.getIdType().compareTo(new BigDecimal(Constants.CORPORATE_ID_TYPE))==0){
								obj.setLoyalityPointExpiring("");
							}
						
						
					}
				}

				remittanceApplList.add(obj);
			}

			// for foreign currency Sale report
			for (RemittanceApplicationView view : fcsaleList) {

				RemittanceReportBean obj = new RemittanceReportBean();

				//For Company information ADDED BY VISWA --START
				List<CompanyMaster> companyMaster =	iPersonalRemittanceService.getCompanyMaster(session.getCompanyId());
				StringBuffer engCompanyInfo = null;	
				StringBuffer arabicCompanyInfo=null;
				if(companyMaster.size()>0){
					engCompanyInfo = new StringBuffer();				
					if(companyMaster.get(0).getAddress1()!=null&&companyMaster.get(0).getAddress1().length()>0){
						engCompanyInfo=engCompanyInfo.append(companyMaster.get(0).getAddress1()+",");
					}
					if(companyMaster.get(0).getAddress2()!=null&&companyMaster.get(0).getAddress2().length()>0){
						engCompanyInfo=engCompanyInfo.append(companyMaster.get(0).getAddress2()+",");
					}
					if(companyMaster.get(0).getAddress3()!=null&&companyMaster.get(0).getAddress3().length()>0){
						engCompanyInfo=engCompanyInfo.append(companyMaster.get(0).getAddress3()+",");
					}
					if(companyMaster.get(0).getRegistrationNumber()!=null&&companyMaster.get(0).getRegistrationNumber().length()>0){
						engCompanyInfo=engCompanyInfo.append("C.R. "+companyMaster.get(0).getRegistrationNumber()+",");
					}
					if(companyMaster.get(0).getCapitalAmount()!=null&&companyMaster.get(0).getCapitalAmount().length()>0){
						engCompanyInfo=engCompanyInfo.append("Share Capital-"+companyMaster.get(0).getCapitalAmount());
					}				
					obj.setEngCompanyInfo(engCompanyInfo.toString());

					arabicCompanyInfo = new StringBuffer();

					if(companyMaster.get(0).getArabicAddress1()!=null&&companyMaster.get(0).getArabicAddress1().length()>0){
						arabicCompanyInfo=arabicCompanyInfo.append(companyMaster.get(0).getArabicAddress1());
					}
					if(companyMaster.get(0).getArabicAddress2()!=null&&companyMaster.get(0).getArabicAddress2().length()>0){
						arabicCompanyInfo=arabicCompanyInfo.append(companyMaster.get(0).getArabicAddress2()+",");
					}
					if(companyMaster.get(0).getArabicAddress3()!=null&&companyMaster.get(0).getArabicAddress3().length()>0){
						arabicCompanyInfo=arabicCompanyInfo.append(companyMaster.get(0).getArabicAddress3()+",");
					}
					if(companyMaster.get(0).getRegistrationNumber()!=null&&companyMaster.get(0).getRegistrationNumber().length()>0){
						arabicCompanyInfo=arabicCompanyInfo.append(Constants.CR +companyMaster.get(0).getRegistrationNumber()+",");
					}					
					if(companyMaster.get(0).getCapitalAmount()!=null&&companyMaster.get(0).getCapitalAmount().length()>0){
						arabicCompanyInfo=arabicCompanyInfo.append(Constants.Share_Capital +companyMaster.get(0).getCapitalAmount());
					}
					obj.setArabicCompanyInfo(arabicCompanyInfo.toString());
				}

				//For Company information ADDED BY VISWA --END
				if (view.getCustomerReference() != null && view.getFirstName() != null && view.getMiddleName() != null) {
					obj.setFirstName(view.getCustomerReference().toString() + "/" + view.getFirstName() + " " + view.getMiddleName());
				} else if (view.getCustomerReference() == null && view.getFirstName() != null && view.getMiddleName() != null) {
					obj.setFirstName(view.getFirstName() + " " + view.getMiddleName());
				} else if (view.getCustomerReference() == null && view.getFirstName() == null && view.getMiddleName() != null) {
					obj.setFirstName(view.getMiddleName());
				} else if (view.getCustomerReference() != null && view.getFirstName() == null && view.getMiddleName() != null) {
					obj.setFirstName(view.getCustomerReference().toString() + " / " + view.getMiddleName());
				} else if (view.getCustomerReference() == null && view.getFirstName() != null && view.getMiddleName() == null) {
					obj.setFirstName(view.getFirstName());
				} else if (view.getCustomerReference() != null && view.getFirstName() == null && view.getMiddleName() == null) {
					obj.setFirstName(view.getCustomerReference().toString());
				} else if (view.getCustomerReference() != null && view.getFirstName() != null && view.getMiddleName() == null) {
					obj.setFirstName(view.getCustomerReference().toString() + " " + view.getFirstName());
				}

				if(view.getContactNumber()!=null && !view.getContactNumber().trim().equalsIgnoreCase("")){
					obj.setMobileNo(new BigDecimal(view.getContactNumber().trim()));
				}
				obj.setCivilId(view.getIdentityInt());
				Date sysdate = view.getIdentityExpiryDate();
				if(sysdate != null){
					obj.setIdExpiryDate(new SimpleDateFormat("dd/MM/yyy").format(sysdate));
				}

				HashMap<String, String> loyaltiPoints  =iPersonalRemittanceService.getloyalityPointsFromProcedure(view.getCustomerReference(), view.getDocumentDate());

				String prLtyStr1 =loyaltiPoints.get("P_LTY_STR1");
				String prLtyStr2 =loyaltiPoints.get("P_LTY_STR2");
				String prInsStr1 =loyaltiPoints.get("P_INS_STR1");
				String prInsStr2 =loyaltiPoints.get("P_INS_STR2");
				String prInsStrAr1 =loyaltiPoints.get("P_INS_STR_AR1");
				String prInsStrAr2 =loyaltiPoints.get("P_INS_STR_AR2");

				if(!prLtyStr1.trim().equals("") && !prLtyStr2.trim().equals("")){
					obj.setLoyalityPointExpiring(prLtyStr1+"  \n"+prLtyStr2);
				}else if(!prLtyStr1.trim().equals("")){
					obj.setLoyalityPointExpiring(prLtyStr1);
				}else if(!prLtyStr2.trim().equals("")){
					obj.setLoyalityPointExpiring(prLtyStr2);
				}
				
				
				
				List<CutomerDetailsView> customerListD = iPersonalRemittanceService.getCustomerDetails(view.getCustomerId());

				if (customerListD  != null && customerListD.size() > 0) {
					CutomerDetailsView cust = customerListD.get(0);
					if(cust.getIdType()!= null && cust.getIdType().compareTo(new BigDecimal(Constants.CORPORATE_ID_TYPE))==0){
						obj.setLoyalityPointExpiring("");
					}
				
				}

				String insurence1 ="";

				if(!prInsStr1.trim().equals("") && !prInsStrAr1.trim().equals("")){
					insurence1 = prInsStr1+"  \n"+prInsStrAr1;
				}else if(!prInsStr1.trim().equals("")){
					insurence1 = prInsStr1;
				}else if(!prInsStrAr1.trim().equals("")){
					insurence1 = prInsStrAr1;
				}

				String insurence2 ="";

				if(!prInsStr2.trim().equals("") && !prInsStrAr2.trim().equals("")){
					insurence2 = prInsStr2+"  \n"+prInsStrAr2;
				}else if(!prInsStr2.trim().equals("")){
					insurence2 = prInsStr2;
				}else if(!prInsStrAr2.trim().equals("")){
					insurence2 = prInsStrAr2;
				}

				if(!insurence1.trim().equals("") && !insurence2.trim().equals("")){
					obj.setInsurence1(insurence1+" \n"+insurence2);
				}else if(!insurence1.trim().equals("")){
					obj.setInsurence1(insurence1);
				}else if(!insurence2.trim().equals("")){
					obj.setInsurence1(insurence2);
				}


				obj.setLocation(view.getCountryBranchName());
				obj.setPhoneNumber(view.getPhoneNumber());
				obj.setDate(currentDate);
				obj.setUserName(view.getCreatedBy());

				if(view.getDocumentFinancialYear()!=null && view.getCollectionDocumentNo()!=null){
					obj.setReceiptNo(view.getDocumentFinancialYear()+" / "+view.getCollectionDocumentNo());
				}else if(view.getCollectionDocumentNo()!=null){
					obj.setReceiptNo(view.getCollectionDocumentNo().toString());
				}
				String saleCurrency = specialCustomerDealRequestService.getCurrencyForUpdate(view.getForeignCurrencyId());


				obj.setCurrencyQuoteName(saleCurrency);

				String saleCurrencyCode = generalService.getCurrencyQuote(view.getForeignCurrencyId());

				if(view.getDocumentFinancialYear()!=null && view.getDocumentNo()!=null){
					obj.setTransactionNo(view.getDocumentFinancialYear()+" / "+view.getDocumentNo());
				}else if(view.getDocumentNo()!=null){
					obj.setTransactionNo(view.getDocumentNo().toString());
				}

				if (view.getForeignTransactionAmount() != null && saleCurrencyCode != null) {
					BigDecimal foreignTransationAmount=GetRound.roundBigDecimal((view.getForeignTransactionAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getForeignCurrencyId()));	
					obj.setSaleAmount( saleCurrencyCode+"     ******"+foreignTransationAmount.toString());
				} 

				if( view.getLocalTransactionCurrencyId()!=null && currencyQuoteName!=null){
					BigDecimal transationAmount=GetRound.roundBigDecimal((view.getLocalTransactionAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setPurchageAmount(currencyQuoteName+"     ******"+transationAmount.toString());
				}

				if(saleCurrencyCode!=null && currencyQuoteName!=null && view.getExchangeRateApplied()!=null){
					obj.setExchangeRate(saleCurrencyCode+" / "+currencyQuoteName+"     "+view.getExchangeRateApplied().toString());
				}

				if(view.getLocalTransactionAmount()!=null && view.getLocalTransactionCurrencyId()!=null && currencyQuoteName!=null){
					BigDecimal transationAmount=GetRound.roundBigDecimal((view.getLocalTransactionAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setLocalTransactionAmount(currencyQuoteName+"     ******"+transationAmount.toString());
				}

				if(view.getLocalCommissionAmount()!=null && view.getLocalTransactionCurrencyId()!=null && currencyQuoteName!=null){
					BigDecimal localCommitionAmount=GetRound.roundBigDecimal((view.getLocalCommissionAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setCommision(currencyQuoteName+"     ******"+localCommitionAmount.toString());
				}

				List<PurposeOfRemittanceView>  purposeOfRemittanceList =   iPersonalRemittanceService.getPurposeOfRemittance(view.getDocumentNo(),view.getDocumentFinancialYear());

				obj.setPerposeOfRemittance(view.getPurposeOfTransaction());
				obj.setSourceOfIncome(view.getSourceOfIncomeDesc());

				List<CollectionDetailView> collectionDetailList1= iPersonalRemittanceService.getCollectionDetailForRemittanceReceipt(view.getCompanyId(),view.getCollectionDocumentNo(),view.getCollectionDocFinanceYear(),view.getCollectionDocCode());
				if(collectionDetailList1.size()>0){
					CollectionDetailView collectionDetailView = collectionDetailList1.get(0);
					if(collectionDetailView.getNetAmount()!=null && currencyQuoteName!=null && view.getLocalTransactionCurrencyId()!=null){
						BigDecimal collectNetAmount=GetRound.roundBigDecimal((collectionDetailView.getNetAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
						obj.setNetAmount(currencyQuoteName+"     ******"+collectNetAmount);
					}
					if(collectionDetailView.getPaidAmount()!=null && currencyQuoteName!=null && view.getLocalTransactionCurrencyId()!=null){
						BigDecimal collectPaidAmount=GetRound.roundBigDecimal((collectionDetailView.getPaidAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
						obj.setPaidAmount(currencyQuoteName+"     ******"+collectPaidAmount);
					}
					if(collectionDetailView.getRefundedAmount()!=null && currencyQuoteName!=null && view.getLocalTransactionCurrencyId()!=null){
						BigDecimal collectRefundAmount=GetRound.roundBigDecimal((collectionDetailView.getRefundedAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
						obj.setRefundedAmount(currencyQuoteName+"     ******"+collectRefundAmount);
					}
					obj.setCollectionDetailList(calculateCollectionMode(view));
				}

				obj.setSubReport(subReportPath);
				//obj.setUserName(sessionStateManage.getUserName());
				obj.setUserName(view.getCreatedBy());

				//		obj.setSignature(view.getCustomerSignature());

				//addedd new column
				BigDecimal lessLoyaltyEncash = BigDecimal.ZERO;
				BigDecimal amountPayable = BigDecimal.ZERO;
				List<CollectionPaymentDetailsView> collectionPmtDetailList= iPersonalRemittanceService.getCollectionPaymentDetailForRemittanceReceipt(view.getCompanyId(),view.getCollectionDocumentNo(),view.getCollectionDocFinanceYear(),view.getCollectionDocCode());
				for(CollectionPaymentDetailsView collPaymentDetailsView: collectionPmtDetailList){
					if(collPaymentDetailsView.getCollectionMode().equalsIgnoreCase(Constants.VOCHERCODE)){
						lessLoyaltyEncash = collPaymentDetailsView.getCollectAmount();
						amountPayable=amountPayable.add(collPaymentDetailsView.getCollectAmount());
					}else{
						amountPayable=amountPayable.add(collPaymentDetailsView.getCollectAmount());
					}
				}
				if(lessLoyaltyEncash.compareTo(BigDecimal.ZERO)==0){
					obj.setLessLoyaltyEncasement(null);					
				}else{
					BigDecimal loyaltyAmount=GetRound.roundBigDecimal((lessLoyaltyEncash),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setLessLoyaltyEncasement(currencyQuoteName+"     ******"+loyaltyAmount);
				}

				if(amountPayable!=null && currencyQuoteName!=null && view.getLocalTransactionCurrencyId()!=null){
					BigDecimal payable=GetRound.roundBigDecimal((amountPayable),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setAmountPayable(currencyQuoteName+"     ******"+payable);
				}

				try {
					if (view.getCustomerSignatureClob() != null) {
						obj.setSignature(view.getCustomerSignatureClob().getSubString(1,(int) view.getCustomerSignatureClob().length()));
					}
				} catch (SQLException e) {
					LOGGER.info( "Exception Occured While Report4 "+e.getMessage());
					setErrorMsg("Exception Occured While Report "+e.getMessage() );
				}

				if(employeeList!=null && employeeList.size()>0){
					try {
						Employee employee = employeeList.get(0);
						if (employee.getSignatureSpecimenClob() != null) {
							obj.setCashierSignature(employee.getSignatureSpecimenClob().getSubString(1,(int) employee.getSignatureSpecimenClob().length()));
						}
					} catch (SQLException e) {
						LOGGER.info( "Exception Occured While Report 5"+e.getMessage());
						setErrorMsg("Exception Occured While Report "+e.getMessage());
					}
				}

				if(obj.getFirstName() == null || obj.getFirstName().isEmpty()){
					List<CutomerDetailsView> customerList = iPersonalRemittanceService.getCustomerDetails(view.getCustomerId());

					if (customerList.size() > 0) {
						CutomerDetailsView cust = customerList.get(0);
						obj.setFirstName(cust.getCustomerName());
						if(cust.getContactNumber()!=null && !cust.getContactNumber().trim().equalsIgnoreCase("")){
							obj.setMobileNo(new BigDecimal(cust.getContactNumber()));
						}
						obj.setCivilId(cust.getIdNumber());
						Date sysdate1 = cust.getIdExp();
						if(sysdate1 != null){
							obj.setIdExpiryDate(new SimpleDateFormat("dd/MM/yyyy").format(sysdate1));
						}
					}
				}

				fcsaleAppList.add(obj);
			}

			//for Main Remittance Receipt report (Remittance Receipt and Fc Sale Application)
			RemittanceReceiptSubreport remittanceObj = new RemittanceReceiptSubreport();

			remittanceObj.setWaterMarkLogoPath(waterMark);
			remittanceObj.setWaterMarkCheck(true);
			remittanceObj.setFcsaleAppList(fcsaleAppList);
			remittanceObj.setRemittanceApplList(remittanceApplList);
			remittanceObj.setSubReport(subReportPath);

			if (fcsaleAppList!=null && fcsaleAppList.size()>0) {
				remittanceObj.setFcsaleApplicationCheck(true);
			}

			if(remittanceApplList!=null && remittanceApplList.size()>0){
				remittanceObj.setRemittanceReceiptCheck(true);
			}

			remittanceReceiptSubreportList.add(remittanceObj);
			return 1;
		} else {
			RequestContext.getCurrentInstance().execute("noDataForReport.show();");
			return 0;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void remittanceReceiptReprintReport() throws JRException {
		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(remittanceReceiptSubreportList);
		//String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("reports/design/RemittanceReceiptNewReport.jasper");
		//String realPath = ctx.getRealPath("//");
		//String reportPath = realPath +"\\reports\\design\\RemittanceReceiptNewReport.jasper";
		String realPath = ctx.getRealPath("//");
		String reportPath = realPath +"//reports//design//RemittanceReceiptNewReport.jasper";
		jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap(), beanCollectionDataSource);
	}

	private BigDecimal corresponingBankBranchId;
	private String corresponingCountryName;
	private String corresponingStateName;
	private String corresponingDistName;
	private String corresponingCityName;
	private String corresponingIfscCode;
	private String corresponingDrBranchId;
	private String corresponingSwiftCode;
	private String corresponingRoutingId;

	private String beneficiaryCountryName;
	private String beneficiaryStateName;
	private String beneficiaryDistName;
	private String beneficiaryCityName;
	private String beneficiaryIfscCode;
	private String beneficiaryDrBranchId;
	private String beneficiaryRoutingId;

	@Autowired
	IBankBranchDetailsService<T> bankBranchDetailsService;

	public BigDecimal getCorresponingBankBranchId() {
		return corresponingBankBranchId;
	}
	public void setCorresponingBankBranchId(BigDecimal corresponingBankBranchId) {
		this.corresponingBankBranchId = corresponingBankBranchId;
	}
	public String getCorresponingCountryName() {
		return corresponingCountryName;
	}
	public void setCorresponingCountryName(String corresponingCountryName) {
		this.corresponingCountryName = corresponingCountryName;
	}
	public String getCorresponingStateName() {
		return corresponingStateName;
	}
	public void setCorresponingStateName(String corresponingStateName) {
		this.corresponingStateName = corresponingStateName;
	}
	public String getCorresponingDistName() {
		return corresponingDistName;
	}
	public void setCorresponingDistName(String corresponingDistName) {
		this.corresponingDistName = corresponingDistName;
	}
	public String getCorresponingCityName() {
		return corresponingCityName;
	}
	public void setCorresponingCityName(String corresponingCityName) {
		this.corresponingCityName = corresponingCityName;
	}
	public String getCorresponingIfscCode() {
		return corresponingIfscCode;
	}
	public void setCorresponingIfscCode(String corresponingIfscCode) {
		this.corresponingIfscCode = corresponingIfscCode;
	}
	public String getCorresponingDrBranchId() {
		return corresponingDrBranchId;
	}
	public void setCorresponingDrBranchId(String corresponingDrBranchId) {
		this.corresponingDrBranchId = corresponingDrBranchId;
	}
	public String getCorresponingSwiftCode() {
		return corresponingSwiftCode;
	}
	public void setCorresponingSwiftCode(String corresponingSwiftCode) {
		this.corresponingSwiftCode = corresponingSwiftCode;
	}
	public String getCorresponingRoutingId() {
		return corresponingRoutingId;
	}
	public void setCorresponingRoutingId(String corresponingRoutingId) {
		this.corresponingRoutingId = corresponingRoutingId;
	}
	public String getBeneficiaryCountryName() {
		return beneficiaryCountryName;
	}
	public void setBeneficiaryCountryName(String beneficiaryCountryName) {
		this.beneficiaryCountryName = beneficiaryCountryName;
	}
	public String getBeneficiaryStateName() {
		return beneficiaryStateName;
	}
	public void setBeneficiaryStateName(String beneficiaryStateName) {
		this.beneficiaryStateName = beneficiaryStateName;
	}
	public String getBeneficiaryDistName() {
		return beneficiaryDistName;
	}
	public void setBeneficiaryDistName(String beneficiaryDistName) {
		this.beneficiaryDistName = beneficiaryDistName;
	}
	public String getBeneficiaryCityName() {
		return beneficiaryCityName;
	}
	public void setBeneficiaryCityName(String beneficiaryCityName) {
		this.beneficiaryCityName = beneficiaryCityName;
	}
	public String getBeneficiaryIfscCode() {
		return beneficiaryIfscCode;
	}
	public void setBeneficiaryIfscCode(String beneficiaryIfscCode) {
		this.beneficiaryIfscCode = beneficiaryIfscCode;
	}
	public String getBeneficiaryDrBranchId() {
		return beneficiaryDrBranchId;
	}
	public void setBeneficiaryDrBranchId(String beneficiaryDrBranchId) {
		this.beneficiaryDrBranchId = beneficiaryDrBranchId;
	}
	public String getBeneficiaryRoutingId() {
		return beneficiaryRoutingId;
	}
	public void setBeneficiaryRoutingId(String beneficiaryRoutingId) {
		this.beneficiaryRoutingId = beneficiaryRoutingId;
	}
	public Boolean getRenderForHIGHVALUETrnxForFC() {
		return renderForHIGHVALUETrnxForFC;
	}
	public void setRenderForHIGHVALUETrnxForFC(Boolean renderForHIGHVALUETrnxForFC) {
		this.renderForHIGHVALUETrnxForFC = renderForHIGHVALUETrnxForFC;
	}
	public String getGccNumber() {
		return gccNumber;
	}
	public void setGccNumber(String gccNumber) {
		this.gccNumber = gccNumber;
	}
	
	// reprint in inquire
	public void reprintReport(){
		if(getReceiptNo() != null && getDocYear() != null){
			try{
				if(getBankCode() != null && getBankCode().equalsIgnoreCase(Constants.WU_BANK_CODE)){
					BeneficaryTransactionBean beneficaryTransactionBean = (BeneficaryTransactionBean) appContext.getBean("beneficaryTransactionBean");
					beneficaryTransactionBean.generateWUSendReport(getReceiptNo(),getReceipYear(),new BigDecimal(Constants.DOCUMENT_CODE_FOR_COLLECT_TRANSACTION), session.getCountryId(),session.getCompanyId());
				}else{
					generatePersonalRemittanceReceiptReport();
				}
			}catch(Exception e){
				
			}
		}
	}
	
	public void goBackToReceiptEnquiryBackButton(){
		try {
			clearAll();
			setDocNumber(null);
			setReceiptNo(null);
			setDocYear(null);
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/ReceiptEnquiry.xhtml");
		}catch(Exception exception){
			LOGGER.error("Exception "+exception);
		}

	}


}
