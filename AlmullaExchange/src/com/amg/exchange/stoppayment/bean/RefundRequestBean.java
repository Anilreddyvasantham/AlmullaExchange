/**
 * 
 */
package com.amg.exchange.stoppayment.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.aop.RefundRequestReport;
import com.amg.exchange.cancelreissue.model.RemittanceView;
import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.service.IDocumentSerialityService;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.foreigncurrency.bean.ForeignLocalCurrencyDataTable;
import com.amg.exchange.foreigncurrency.model.Collect;
import com.amg.exchange.foreigncurrency.model.CollectDetail;
import com.amg.exchange.foreigncurrency.model.CurrencyWiseDenomination;
import com.amg.exchange.foreigncurrency.model.ForeignCurrencyAdjust;
import com.amg.exchange.foreigncurrency.model.PurposeOfTransaction;
import com.amg.exchange.foreigncurrency.model.ReceiptPayment;
import com.amg.exchange.foreigncurrency.model.SourceOfIncome;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.foreigncurrency.service.ForeignLocalCurrencyDenominationService;
import com.amg.exchange.foreigncurrency.service.IForeignCurrencyPurchaseService;
import com.amg.exchange.miscellaneous.model.Payment;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.service.ICompanyMasterservice;
import com.amg.exchange.registration.service.ICustomerRegistrationBranchService;
import com.amg.exchange.registration.service.IEncrptionDescriptionService;
import com.amg.exchange.registration.service.ILoginService;
import com.amg.exchange.remittance.bean.PersonalRemittanceCollectionDataTable;
import com.amg.exchange.remittance.model.CustomerBank;
import com.amg.exchange.remittance.model.DebitAutendicationView;
import com.amg.exchange.remittance.model.PaymentMode;
import com.amg.exchange.remittance.model.PaymentModeDesc;
import com.amg.exchange.remittance.model.ViewBankDetails;
import com.amg.exchange.remittance.model.ViewStock;
import com.amg.exchange.remittance.service.ICustomerBankService;
import com.amg.exchange.remittance.service.IPaymentService;
import com.amg.exchange.remittance.service.IPersonalRemittanceService;
import com.amg.exchange.stoppayment.model.RemittanceTransaction;
import com.amg.exchange.stoppayment.model.RemittanceTranxBenificiary;
import com.amg.exchange.stoppayment.service.IStopPaymentCollectionService;
import com.amg.exchange.stoppayment.service.IStopPaymentService;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.treasury.service.IGLTransactionForADocument;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.GetRound;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;
import com.amg.exchange.util.iCypherSecurity;
import com.amg.exchange.util.impl.CypherSecurityImpl;

/**
 * @author Subramaniam
 * 
 */

@Component(value = "requestRefund")
@Scope("session")
public class RefundRequestBean<T> implements Serializable {

	/**
	 * 
	 */

	Logger log = Logger.getLogger(RefundRequestBean.class);

	private static final long serialVersionUID = 1L;

	public BigDecimal remittanceYear = null;
	public BigDecimal transferNo = null;
	public String applicationYear = null;
	public BigDecimal applicationYearId = null;
	public BigDecimal applicationRef = null;
	public String systemDate = null;
	private BigDecimal companyId;
	private String companyName;
	private int financeYear;
	private Integer paymentFinanceYear;
	private String productName;
	private String productStatus;
	private BigDecimal receiptNo;
	private String processIn = Constants.Yes;
	private boolean firstTime = false;
	private BigDecimal documentSerialId;
	private BigDecimal remittanceAppBenificiaryId;
	private String benificiary;
	private String transferAmountCurrency;
	private BigDecimal customerId;
	private BigDecimal countryId;
	private BigDecimal currencyId;
	private BigDecimal transactionId;
	private BigDecimal branchCode;
	private String branchName;
	private BigDecimal customerCode;
	private String customerName;
	private BigDecimal customerRefNo;
	private String telephoneNo;
	private Date transferDate;
	private int validUntill;
	private String accPayee;
	private String beneficiaryName;
	private BigDecimal beneficiaryAccNo;
	private BigDecimal beneficiaryBankId;
	private BigDecimal beneficiaryBankBranchId;
	private BigDecimal beneficiaryBankCountryId;
	private String transferAmount;
	private BigDecimal payableAt;
	private String payableBranch;
	private String payableBank;
	private String payableBranchCode;
	private String refundFor;
	private BigDecimal exchangeRate;
	private String currencyCode;
	private String referenceAmount;
	private BigDecimal bankBranchId;
	private BigDecimal deliveryCharges;
	private BigDecimal rateAdjust;
	private BigDecimal otherAdjust;
	private BigDecimal netRefund;
	private BigDecimal localCurrencyId;
	private BigDecimal foreignCurrencyId;
	private BigDecimal localCommissionCurrencyId;
	private BigDecimal commission;
	private BigDecimal localChargeCurrencyId;
	private BigDecimal charges;
	private BigDecimal localTrnxCurrencyId;
	private BigDecimal localDeliveryCurrencyId;
	private BigDecimal localNetCurrencyId;
	private BigDecimal paymentNo;
	private Date paymentDate = new Date();
	private String remarks;
	private BigDecimal colpaymentmodeId;
	private BigDecimal colCash;
	private BigDecimal cashAmount = new BigDecimal(0);
	private String colpaymentmodeName;
	private BigDecimal denomdifference;
	private BigDecimal calNetAmountPaid;
	private String paymentDetailsremark;
	private BigDecimal payPaidAmount;
	// private BigDecimal payNetPaidAmount;
	private String errorMessage;
	private Boolean booRenderCollection = false;
	private Boolean booRendercollectiondatatable = false;
	private BigDecimal colfcsaleNo;
	private BigDecimal colBankid;
	private String colCurrency;
	private BigDecimal colCardNo;
	private String colNameofCard;
	private String colAuthorizedby;
	private String colpassword;
	private String cyberPassword;
	private Boolean booRenderPaymentDetails = false;
	private Boolean booRenderPaymentModeDetails = false;
	private Boolean booRenderTransferModeDetails = false;
	private Boolean booRenderButton = false;
	private Boolean booAuthozed = false;
	private Boolean booRenderColDebitCard = false;
	private Boolean paymentDeatailsPanel = false;
	private Boolean boorefundPaymentDetails = false;
	private Boolean booRenderFirstPanel = false;
	private BigDecimal toalUsedAmount = new BigDecimal(0);
	private BigDecimal totalUamount = new BigDecimal(0);
	private BigDecimal subtractedAmount = new BigDecimal(0);
	private Boolean boRenderTotalAmountCalPanel = false;
	private BigDecimal denomtotalCashreceived;
	private String exceptionMessage;
	private Boolean booRendercashdenomination = false;
	private Boolean successPanel = false;

	private String colBankTransferBankCode;

	SessionStateManage sessionStateManage = new SessionStateManage();

	private List<UserFinancialYear> financialYearList = new ArrayList<>();
	// private List<Employee> empllist = new ArrayList<Employee>();

	@Autowired
	IForeignCurrencyPurchaseService<T> foreignCurrencyPurchaseService;

	@Autowired
	IPersonalRemittanceService iPersonalRemittanceService;

	@Autowired
	IGeneralService<T> iGeneralService;

	@Autowired
	IStopPaymentService<T> iStopPaymentService;

	@Autowired
	ICustomerRegistrationBranchService<T> icustomerRegistrationService;

	@Autowired
	ILoginService<T> iloginService;

	iCypherSecurity cypherSecurity = new CypherSecurityImpl();

	@Autowired
	ICustomerBankService icustomerBankService;

	@Autowired
	IStopPaymentCollectionService iStopPaymentCollectionService;

	@Autowired
	IPaymentService ipaymentService;

	@Autowired
	IEncrptionDescriptionService<T> encryptionDescriptionService;

	@Autowired
	IGLTransactionForADocument iglTransactionForADocument;

	@Autowired
	IDocumentSerialityService documentSerialityService;

	@Autowired
	ICompanyMasterservice iCompanyMasterservice;

	@Autowired
	IStopPaymentService<T> stopPaymentService;

	public IGeneralService<T> getiGeneralService() {
		return iGeneralService;
	}

	public void setiGeneralService(IGeneralService<T> iGeneralService) {
		this.iGeneralService = iGeneralService;
	}

	public IForeignCurrencyPurchaseService<T> getForeignCurrencyPurchaseService() {
		return foreignCurrencyPurchaseService;
	}

	public void setForeignCurrencyPurchaseService(
			IForeignCurrencyPurchaseService<T> foreignCurrencyPurchaseService) {
		this.foreignCurrencyPurchaseService = foreignCurrencyPurchaseService;
	}

	public IPersonalRemittanceService getiPersonalRemittanceService() {
		return iPersonalRemittanceService;
	}

	public void setiPersonalRemittanceService(
			IPersonalRemittanceService iPersonalRemittanceService) {
		this.iPersonalRemittanceService = iPersonalRemittanceService;
	}

	public IStopPaymentService<T> getiStopPaymentService() {
		return iStopPaymentService;
	}

	public void setiStopPaymentService(
			IStopPaymentService<T> iStopPaymentService) {
		this.iStopPaymentService = iStopPaymentService;
	}

	public Boolean getBooRenderPaymentDetails() {
		return booRenderPaymentDetails;
	}

	public void setBooRenderPaymentDetails(Boolean booRenderPaymentDetails) {
		this.booRenderPaymentDetails = booRenderPaymentDetails;
	}

	public Boolean getBooRenderPaymentModeDetails() {
		return booRenderPaymentModeDetails;
	}

	public void setBooRenderPaymentModeDetails(
			Boolean booRenderPaymentModeDetails) {
		this.booRenderPaymentModeDetails = booRenderPaymentModeDetails;
	}

	public Boolean getBooRenderTransferModeDetails() {
		return booRenderTransferModeDetails;
	}

	public void setBooRenderTransferModeDetails(
			Boolean booRenderTransferModeDetails) {
		this.booRenderTransferModeDetails = booRenderTransferModeDetails;
	}

	public Boolean getBooRenderButton() {
		return booRenderButton;
	}

	public void setBooRenderButton(Boolean booRenderButton) {
		this.booRenderButton = booRenderButton;
	}

	public Boolean getBooRenderCollection() {
		return booRenderCollection;
	}

	public void setBooRenderCollection(Boolean booRenderCollection) {
		this.booRenderCollection = booRenderCollection;
	}

	public Boolean getBooRendercollectiondatatable() {
		return booRendercollectiondatatable;
	}

	public void setBooRendercollectiondatatable(
			Boolean booRendercollectiondatatable) {
		this.booRendercollectiondatatable = booRendercollectiondatatable;
	}

	public Boolean getPaymentDeatailsPanel() {
		return paymentDeatailsPanel;
	}

	public void setPaymentDeatailsPanel(Boolean paymentDeatailsPanel) {
		this.paymentDeatailsPanel = paymentDeatailsPanel;
	}

	public Boolean getSuccessPanel() {
		return successPanel;
	}

	public void setSuccessPanel(Boolean successPanel) {
		this.successPanel = successPanel;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public BigDecimal getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(BigDecimal receiptNo) {
		this.receiptNo = receiptNo;
	}

	public BigDecimal getBankBranchId() {
		return bankBranchId;
	}

	public void setBankBranchId(BigDecimal bankBranchId) {
		this.bankBranchId = bankBranchId;
	}

	public BigDecimal getColfcsaleNo() {
		return colfcsaleNo;
	}

	public void setColfcsaleNo(BigDecimal colfcsaleNo) {
		this.colfcsaleNo = colfcsaleNo;
	}

	public BigDecimal getColBankid() {
		return colBankid;
	}

	public void setColBankid(BigDecimal colBankid) {
		this.colBankid = colBankid;
	}

	public String getColCurrency() {
		return colCurrency;
	}

	public void setColCurrency(String colCurrency) {
		this.colCurrency = colCurrency;
	}

	public BigDecimal getColCardNo() {
		return colCardNo;
	}

	public void setColCardNo(BigDecimal colCardNo) {
		this.colCardNo = colCardNo;
	}

	public String getColNameofCard() {
		return colNameofCard;
	}

	public void setColNameofCard(String colNameofCard) {
		this.colNameofCard = colNameofCard;
	}

	public String getColAuthorizedby() {
		return colAuthorizedby;
	}

	public void setColAuthorizedby(String colAuthorizedby) {
		this.colAuthorizedby = colAuthorizedby;
	}

	public String getColpassword() {
		return colpassword;
	}

	public void setColpassword(String colpassword) {
		this.colpassword = colpassword;
	}

	public String getCyberPassword() {
		return cyberPassword;
	}

	public void setCyberPassword(String cyberPassword) {
		this.cyberPassword = cyberPassword;
	}

	public Boolean getBooAuthozed() {
		return booAuthozed;
	}

	public void setBooAuthozed(Boolean booAuthozed) {
		this.booAuthozed = booAuthozed;
	}

	public Boolean getBooRenderColDebitCard() {
		return booRenderColDebitCard;
	}

	public void setBooRenderColDebitCard(Boolean booRenderColDebitCard) {
		this.booRenderColDebitCard = booRenderColDebitCard;
	}

	public CopyOnWriteArrayList<PersonalRemittanceCollectionDataTable> getColdatatablevalues() {
		return coldatatablevalues;
	}

	public void setColdatatablevalues(
			CopyOnWriteArrayList<PersonalRemittanceCollectionDataTable> coldatatablevalues) {
		this.coldatatablevalues = coldatatablevalues;
	}

	// denomination adding
	private ArrayList<ForeignLocalCurrencyDataTable> lstData = new ArrayList<ForeignLocalCurrencyDataTable>();

	public ArrayList<ForeignLocalCurrencyDataTable> getLstData() {
		return lstData;
	}

	public void setLstData(ArrayList<ForeignLocalCurrencyDataTable> lstData) {
		this.lstData = lstData;
	}

	@Autowired
	ForeignLocalCurrencyDenominationService<T> foreignLocalCurrencyDenominationService;

	/**
	 * 
	 */
	public RefundRequestBean() {
		// TODO Auto-generated constructor stub
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void setFinanceYear(int financeYear) {
		this.financeYear = financeYear;
	}

	private String varToKeepSerial = null;

	public String getVarToKeepSerial() {
		return varToKeepSerial;
	}

	public void setVarToKeepSerial(String varToKeepSerial) {
		this.varToKeepSerial = varToKeepSerial;
	}

	public BigDecimal getTransferNo() {
		return transferNo;
	}

	public void setTransferNo(BigDecimal transferNo) {
		this.transferNo = transferNo;
	}

	/*
	 * public BigDecimal getProductCode() { return productCode; }
	 * 
	 * public void setProductCode(BigDecimal productCode) { this.productCode =
	 * productCode; }
	 */

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}

	public BigDecimal getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(BigDecimal branchCode) {
		this.branchCode = branchCode;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public BigDecimal getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(BigDecimal customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public BigDecimal getCustomerRefNo() {
		return customerRefNo;
	}

	public void setCustomerRefNo(BigDecimal customerRefNo) {
		this.customerRefNo = customerRefNo;
	}

	public String getTelephoneNo() {
		return telephoneNo;
	}

	public void setTelephoneNo(String telephoneNo) {
		this.telephoneNo = telephoneNo;
	}

	public Date getTransferDate() {
		return transferDate;
	}

	public void setTransferDate(Date transferDate) {
		this.transferDate = transferDate;
	}

	public int getValidUntill() {
		return validUntill;
	}

	public void setValidUntill(int validUntill) {
		this.validUntill = validUntill;
	}

	public String getAccPayee() {
		return accPayee;
	}

	public void setAccPayee(String accPayee) {
		this.accPayee = accPayee;
	}

	public String getBeneficiaryName() {
		return beneficiaryName;
	}

	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	public BigDecimal getBeneficiaryAccNo() {
		return beneficiaryAccNo;
	}

	public void setBeneficiaryAccNo(BigDecimal beneficiaryAccNo) {
		this.beneficiaryAccNo = beneficiaryAccNo;
	}

	public BigDecimal getBeneficiaryBankId() {
		return beneficiaryBankId;
	}

	public void setBeneficiaryBankId(BigDecimal beneficiaryBankId) {
		this.beneficiaryBankId = beneficiaryBankId;
	}

	public BigDecimal getBeneficiaryBankBranchId() {
		return beneficiaryBankBranchId;
	}

	public void setBeneficiaryBankBranchId(BigDecimal beneficiaryBankBranchId) {
		this.beneficiaryBankBranchId = beneficiaryBankBranchId;
	}

	public BigDecimal getBeneficiaryBankCountryId() {
		return beneficiaryBankCountryId;
	}

	public void setBeneficiaryBankCountryId(BigDecimal beneficiaryBankCountryId) {
		this.beneficiaryBankCountryId = beneficiaryBankCountryId;
	}

	public String getTransferAmount() {
		return transferAmount;
	}

	public void setTransferAmount(String transferAmount) {
		this.transferAmount = transferAmount;
	}

	public BigDecimal getPayableAt() {
		return payableAt;
	}

	public void setPayableAt(BigDecimal payableAt) {
		this.payableAt = payableAt;
	}

	public String getPayableBranch() {
		return payableBranch;
	}

	public void setPayableBranch(String payableBranch) {
		this.payableBranch = payableBranch;
	}

	public String getPayableBank() {
		return payableBank;
	}

	public void setPayableBank(String payableBank) {
		this.payableBank = payableBank;
	}

	public String getPayableBranchCode() {
		return payableBranchCode;
	}

	public void setPayableBranchCode(String payableBranchCode) {
		this.payableBranchCode = payableBranchCode;
	}

	public String getRefundFor() {
		return refundFor;
	}

	public void setRefundFor(String refundFor) {
		this.refundFor = refundFor;
	}

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getReferenceAmount() {
		return referenceAmount;
	}

	public void setReferenceAmount(String referenceAmount) {
		this.referenceAmount = referenceAmount;
	}

	public BigDecimal getCommission() {
		return commission;
	}

	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}

	public BigDecimal getCharges() {
		return charges;
	}

	public void setCharges(BigDecimal charges) {
		this.charges = charges;
	}

	public BigDecimal getRemittanceYear() {
		return remittanceYear;
	}

	public void setRemittanceYear(BigDecimal remittanceYear) {
		this.remittanceYear = remittanceYear;
	}

	public String getApplicationYear() {
		return applicationYear;
	}

	public void setApplicationYear(String applicationYear) {
		this.applicationYear = applicationYear;
	}

	public BigDecimal getApplicationRef() {
		return applicationRef;
	}

	public void setApplicationRef(BigDecimal applicationRef) {
		this.applicationRef = applicationRef;
	}

	public BigDecimal getDeliveryCharges() {
		return deliveryCharges;
	}

	public void setDeliveryCharges(BigDecimal deliveryCharges) {
		this.deliveryCharges = deliveryCharges;
	}

	public BigDecimal getRateAdjust() {
		return rateAdjust;
	}

	public void setRateAdjust(BigDecimal rateAdjust) {
		this.rateAdjust = rateAdjust;
	}

	public BigDecimal getOtherAdjust() {
		return otherAdjust;
	}

	public void setOtherAdjust(BigDecimal otherAdjust) {
		this.otherAdjust = otherAdjust;
	}

	public BigDecimal getNetRefund() {
		return netRefund;
	}

	public void setNetRefund(BigDecimal netRefund) {
		this.netRefund = netRefund;
	}

	public BigDecimal getPaymentNo() {
		return paymentNo;
	}

	public void setPaymentNo(BigDecimal paymentNo) {
		this.paymentNo = paymentNo;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public BigDecimal getRemittanceAppBenificiaryId() {
		return remittanceAppBenificiaryId;
	}

	public void setRemittanceAppBenificiaryId(
			BigDecimal remittanceAppBenificiaryId) {
		this.remittanceAppBenificiaryId = remittanceAppBenificiaryId;
	}

	public String getBenificiary() {
		return benificiary;
	}

	public void setBenificiary(String benificiary) {
		this.benificiary = benificiary;
	}

	public String getTransferAmountCurrency() {
		return transferAmountCurrency;
	}

	public void setTransferAmountCurrency(String transferAmountCurrency) {
		this.transferAmountCurrency = transferAmountCurrency;
	}

	public BigDecimal getCustomerId() {
		return customerId;
	}

	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	public BigDecimal getCountryId() {
		return countryId;
	}

	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}

	public BigDecimal getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	public BigDecimal getTransactionId() {
		return transactionId;
	}

	public BigDecimal getLocalCurrencyId() {
		return localCurrencyId;
	}

	public void setLocalCurrencyId(BigDecimal localCurrencyId) {
		this.localCurrencyId = localCurrencyId;
	}

	public BigDecimal getForeignCurrencyId() {
		return foreignCurrencyId;
	}

	public void setForeignCurrencyId(BigDecimal foreignCurrencyId) {
		this.foreignCurrencyId = foreignCurrencyId;
	}

	public BigDecimal getLocalCommissionCurrencyId() {
		return localCommissionCurrencyId;
	}

	public void setLocalCommissionCurrencyId(
			BigDecimal localCommissionCurrencyId) {
		this.localCommissionCurrencyId = localCommissionCurrencyId;
	}

	public BigDecimal getLocalChargeCurrencyId() {
		return localChargeCurrencyId;
	}

	public void setLocalChargeCurrencyId(BigDecimal localChargeCurrencyId) {
		this.localChargeCurrencyId = localChargeCurrencyId;
	}

	public BigDecimal getLocalTrnxCurrencyId() {
		return localTrnxCurrencyId;
	}

	public void setLocalTrnxCurrencyId(BigDecimal localTrnxCurrencyId) {
		this.localTrnxCurrencyId = localTrnxCurrencyId;
	}

	public BigDecimal getLocalDeliveryCurrencyId() {
		return localDeliveryCurrencyId;
	}

	public void setLocalDeliveryCurrencyId(BigDecimal localDeliveryCurrencyId) {
		this.localDeliveryCurrencyId = localDeliveryCurrencyId;
	}

	public BigDecimal getLocalNetCurrencyId() {
		return localNetCurrencyId;
	}

	public void setLocalNetCurrencyId(BigDecimal localNetCurrencyId) {
		this.localNetCurrencyId = localNetCurrencyId;
	}

	public void setTransactionId(BigDecimal transactionId) {
		this.transactionId = transactionId;
	}

	public Boolean getBooRendercashdenomination() {
		return booRendercashdenomination;
	}

	public void setBooRendercashdenomination(Boolean booRendercashdenomination) {
		this.booRendercashdenomination = booRendercashdenomination;
	}

	public BigDecimal getDenomtotalCashreceived() {
		return denomtotalCashreceived;
	}

	public void setDenomtotalCashreceived(BigDecimal denomtotalCashreceived) {
		this.denomtotalCashreceived = denomtotalCashreceived;
	}

	public BigDecimal getToalUsedAmount() {
		return toalUsedAmount;
	}

	public void setToalUsedAmount(BigDecimal toalUsedAmount) {
		this.toalUsedAmount = toalUsedAmount;
	}

	public BigDecimal getTotalUamount() {
		return totalUamount;
	}

	public void setTotalUamount(BigDecimal totalUamount) {
		this.totalUamount = totalUamount;
	}

	public BigDecimal getSubtractedAmount() {
		return subtractedAmount;
	}

	public void setSubtractedAmount(BigDecimal subtractedAmount) {
		this.subtractedAmount = subtractedAmount;
	}

	public Boolean getBoRenderTotalAmountCalPanel() {
		return boRenderTotalAmountCalPanel;
	}

	public void setBoRenderTotalAmountCalPanel(
			Boolean boRenderTotalAmountCalPanel) {
		this.boRenderTotalAmountCalPanel = boRenderTotalAmountCalPanel;
	}

	public BigDecimal getDocumentSerialId() {
		return documentSerialId;
	}

	public void setDocumentSerialId(BigDecimal documentSerialId) {
		this.documentSerialId = documentSerialId;
	}

	public int getFinanceYear() {
		try {
			financialYearList = getForeignCurrencyPurchaseService()
					.getUserFinancialYear(new Date());
			log.info("financialYearList :" + financialYearList.size());
			if (financialYearList != null)
				financeYear = Integer.parseInt(financialYearList.get(0)
						.getFinancialYear().toString());
			setFinanceYear(financeYear);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return financeYear;
	}

	public BigDecimal getApplicationYearId() {
		return applicationYearId;
	}

	public void setApplicationYearId(BigDecimal applicationYearId) {
		this.applicationYearId = applicationYearId;
	}

	public void getApplicationYearFromdb() {

		try {
			List<UserFinancialYear> applicationYearList = iGeneralService.getDealYear(new Date());
			if (applicationYearList.size() > 0) {
				setDocFinYear(applicationYearList.get(0).getFinancialYear());
				setApplicationYear(applicationYearList.get(0).getFinancialYear().toString());
				setApplicationYearId(applicationYearList.get(0).getFinancialYearID());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * public void nextApplicationNo() { String docNo =
	 * getDocumentSerialID(processIn); if (docNo.equalsIgnoreCase("0")) {
	 * setApplicationRef(BigDecimal.ZERO); } else { setApplicationRef(new
	 * BigDecimal(docNo)); }
	 * 
	 * }
	 */

	public String getDocumentSerialID(String processIn) {

		try {
			HashMap<String, String> outPutValues = iGeneralService
					.getNextDocumentRefNumber(
							sessionStateManage.getCountryId().intValue(),
							sessionStateManage.getCompanyId().intValue(),
							Integer.parseInt(Constants.DOCUMENT_CODE_FOR_REFUND_REQUEST),
							new BigDecimal(getApplicationYear()).intValue(),
							processIn, sessionStateManage
									.getCountryBranchCode());
			log.info("====docno===" + outPutValues.get("DOCNO"));
			String proceErrorMsg = outPutValues.get("PROCE_ERORRMSG");
			if (proceErrorMsg != null) {
				// setBooProcedureDialog(true);
				// setErrorMsg(proceErrorMsg );
				RequestContext.getCurrentInstance().execute("proceErr.show();");
				return "0";

			} else {
				// setBooProcedureDialog(false);
				String documentSerialID = outPutValues.get("DOCNO");
				return documentSerialID;

			}

		} catch (NumberFormatException | AMGException e) {
			// setErrorMsg(e.getMessage() );
			RequestContext.getCurrentInstance().execute("proceErr.show();");
			return "0";
		}

		// String documentSerialID =
		// generalService.getNextDocumentReferenceNumber(sessionManage.getCountryId().intValue()
		// , sessionManage.getCompanyId().intValue(),
		// Integer.parseInt(Constants.DOCUMENT_CODE_FOR_CANCELLATION_REISSUE) ,
		// new
		// BigDecimal(getApplicationYear()).intValue(),processIn,sessionManage.getCountryBranchCode());
		// return documentSerialID;
	}

	/*
	 * public String getDocumentSerialId() { if (getVarToKeepSerial() != null) {
	 * return getVarToKeepSerial(); } else {
	 * setVarToKeepSerial(getDocumentSerialID(Constants.U)); return
	 * getVarToKeepSerial(); } }
	 */

	/**
	 * Document Seriality
	 */
	/*
	 * public String getDocumentSerialID(String processIn) {
	 * log.info("=========== getDocumentSerialID() Start============ ");
	 * log.info("process in :" + processIn); documentSerialId =
	 * iGeneralService.getNextDocumentReferenceNumber
	 * (Integer.parseInt(sessionStateManage.getSessionValue("countryId")),
	 * Integer.parseInt(sessionStateManage.getSessionValue("companyId")),
	 * Integer.parseInt(Constants.DOCUMENT_CODE_FOR_REFUND_REQUEST),
	 * financeYear, Constants.U, sessionStateManage.getCountryBranchCode());
	 * log.info("=========== getDocumentSerialID() End============ "); return
	 * documentSerialId; }
	 */

	public Integer getPaymentFinanceYear() {
		log.info("=========== getPaymentFinanceYear() Start============ ");
		try {
			financialYearList = getForeignCurrencyPurchaseService()
					.getUserFinancialYear(new Date());
			log.info("financialYearList :" + financialYearList.size());
			if (financialYearList != null)
				financeYear = Integer.parseInt(financialYearList.get(0)
						.getFinancialYear().toString());
			setPaymentFinanceYear(financeYear);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("=========== getPaymentFinanceYear() End============ ");
		return paymentFinanceYear;
	}

	public void setPaymentFinanceYear(Integer paymentFinanceYear) {
		this.paymentFinanceYear = paymentFinanceYear;
	}

	public void clear() {

		/*
		 * try {
		 * 
		 * FacesContext.getCurrentInstance().getExternalContext()
		 * .redirect("../remittance/refundrequest.xhtml");
		 * 
		 * } catch (Exception e) { // TODO: handle exception }
		 */

		setRemittanceYear(null);

		setCustomerRefNo(null);

		setTransferNo(null);
		// setProductCode(null);
		setProductName(null);
		setProductStatus(null);
		// setApplicationNo(null);
		setBranchCode(null);
		setBranchName(null);
		setCustomerCode(null);
		setCustomerName(null);
		setTelephoneNo(null);
		setTransferDate(null);
		// setValidUntill(null);
		setAccPayee(null);
		setBeneficiaryName(null);
		setBeneficiaryAccNo(null);
		setBeneficiaryBankId(null);
		setBeneficiaryBankBranchId(null);
		setBeneficiaryBankCountryId(null);
		setTransferAmount(null);
		setPayableAt(null);
		setPayableBranch(null);
		setPayableBank(null);
		setPayableBranchCode(null);
		setRefundFor(null);
		setExchangeRate(null);
		setCurrencyCode(null);
		setReferenceAmount(null);
		setCommission(null);
		setCharges(null);
		setDeliveryCharges(null);
		setRateAdjust(null);
		setOtherAdjust(null);
		setColCash(null);
		setNetRefund(null);
		setPaymentNo(null);
		setPaymentDate(null);
		setRemarks(null);
		setBenificiary(null);
		setBooRenderPaymentDetails(false);
		setTransferAmountCurrency(null);
		setBooRenderPaymentModeDetails(false);
		setBooRenderFirstPanel(true);
		setBooRenderButton(false);
		coldatatablevalues.clear();
		setTotalUamount(null);
		setDocumentNum(null);
		setReceiptDocNoList(null);
		setReceiptPaymentPk(null);
		fectchDocumentNumbers();
		toFetchAllDocFinanceYear();

	}

	public IStopPaymentCollectionService getiStopPaymentCollectionService() {
		return iStopPaymentCollectionService;
	}

	public void setiStopPaymentCollectionService(
			IStopPaymentCollectionService iStopPaymentCollectionService) {
		this.iStopPaymentCollectionService = iStopPaymentCollectionService;
	}

	public ICustomerRegistrationBranchService<T> getIcustomerRegistrationService() {
		return icustomerRegistrationService;
	}

	public void setIcustomerRegistrationService(
			ICustomerRegistrationBranchService<T> icustomerRegistrationService) {
		this.icustomerRegistrationService = icustomerRegistrationService;
	}

	private boolean editFlag = false;

	public boolean isEditFlag() {
		return editFlag;
	}

	public void setEditFlag(boolean editFlag) {
		this.editFlag = editFlag;
	}

	public String getSystemDate() {
		return systemDate;
	}

	public void setSystemDate(String systemDate) {
		this.systemDate = systemDate;
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

	/*
	 * public void getRemittanceTransactionDetails() { try {
	 * 
	 * log.info("=========== getRemittanceTransactionDetails() Start============ "
	 * );
	 * log.info("==================REFUND REQUEST CALLED======================="
	 * ); log.info("================== CountryId= " +
	 * sessionStateManage.getCountryId());
	 * log.info("================== Fin Year=" + getRemittanceYear());
	 * log.info("==================documentNo= " + getTransferNo());
	 * log.info("================== comanyid=" +
	 * sessionStateManage.getCompanyId());
	 * log.info("==================Document Code="
	 * +Constants.DOCUMENT_CODE_FOR_REMITTANCE_TRANSACTION);
	 * 
	 * if(getRemittanceYear() != null && getTransferNo() != null){
	 * RemittanceView remittanceTransaction =
	 * getiStopPaymentCollectionService().
	 * getRemittanceTransactionFromView(sessionStateManage.getCountryId(),
	 * getRemittanceYear().intValue(), getTransferNo(),
	 * sessionStateManage.getCompanyId(), new
	 * BigDecimal(Constants.DOCUMENT_CODE_FOR_REMITTANCE_TRANSACTION));
	 * 
	 * if (remittanceTransaction != null) {
	 * 
	 * // String branchName = //
	 * iPersonalRemittanceService.getBankBranchName(remittanceTransaction
	 * .getBranchId().getBranchId());
	 * fetchRemittanceView(remittanceTransaction);
	 * 
	 * } else {
	 * 
	 * // company code BigDecimal companyCode = null; List<CompanyMasterDesc>
	 * lstcompanymaster =
	 * iCompanyMasterservice.viewById(sessionStateManage.getCompanyId());
	 * if(lstcompanymaster.size() != 0){ CompanyMasterDesc companycode =
	 * lstcompanymaster.get(0);
	 * if(companycode.getFsCompanyMaster().getCompanyCode() != null){
	 * companyCode = companycode.getFsCompanyMaster().getCompanyCode(); } }
	 * 
	 * HashMap<String, Object> fetchTransferToRemits =
	 * stopPaymentService.fetchTransferForStopPayment(companyCode, new
	 * BigDecimal(Constants.DOCUMENT_CODE_FOR_REMITTANCE_TRANSACTION),
	 * getRemittanceYear(), getTransferNo(), Constants.RefundForm);
	 * 
	 * if((String)fetchTransferToRemits.get("ERRMSG") != null && !((String)
	 * fetchTransferToRemits.get("ERRMSG")).equalsIgnoreCase("")){
	 * 
	 * setTransferNo(null); setErrorMessage((String)
	 * fetchTransferToRemits.get("ERRMSG"));
	 * RequestContext.getCurrentInstance().execute("csp.show();"); clear();
	 * return;
	 * 
	 * }else{ if((RemittanceTxnOLDView)fetchTransferToRemits.get("TRANSFER") !=
	 * null){
	 * 
	 * RemittanceTxnOLDView remitOldTrnx = (RemittanceTxnOLDView)
	 * fetchTransferToRemits.get("TRANSFER");
	 * 
	 * if(remitOldTrnx != null){ fetchRemittanceTrnxOldView(remitOldTrnx);
	 * }else{ setErrorMessage("Application number does not exist " +
	 * getTransferNo()); setTransferNo(null);
	 * RequestContext.getCurrentInstance().execute("csp.show();"); } }else{
	 * setErrorMessage("Transaction Number Not Transfer from OLD " +
	 * getTransferNo()); setTransferNo(null);
	 * RequestContext.getCurrentInstance().execute("csp.show();"); } } }
	 * log.info
	 * ("=========== getRemittanceTransactionDetails() End============ ");
	 * }else{ setErrorMessage("Transaction Year and Number not Available for " +
	 * getDocumentNum());
	 * RequestContext.getCurrentInstance().execute("csp.show();"); } } catch
	 * (Exception e) { setErrorMessage("Exception occurred " + e);
	 * setTransferNo(null);
	 * RequestContext.getCurrentInstance().execute("csp.show();");
	 * 
	 * } }
	 */

	public void getRemittanceTransactionDetails() {
		try {

			log.info("=========== getRemittanceTransactionDetails() Start============ ");
			log.info("==================REFUND REQUEST CALLED=======================");
			log.info("================== CountryId= "
					+ sessionStateManage.getCountryId());
			log.info("================== Fin Year=" + getRemittanceYear());
			log.info("==================documentNo= " + getTransferNo());
			log.info("================== comanyid="
					+ sessionStateManage.getCompanyId());
			log.info("==================Document Code="
					+ Constants.DOCUMENT_CODE_FOR_REMITTANCE_TRANSACTION);

			if (getRemittanceYear() != null && getTransferNo() != null) {

				RemittanceView remittanceTransaction = getiStopPaymentCollectionService()
						.getRemittanceTransactionFromView(
								sessionStateManage.getCountryId(),
								getRemittanceYear().intValue(),
								getTransferNo(),
								sessionStateManage.getCompanyId(),
								new BigDecimal(
										Constants.DOCUMENT_CODE_FOR_REMITTANCE_TRANSACTION));

				if (remittanceTransaction != null) {

					fetchRemittanceView(remittanceTransaction);

				} else {

					// company code
					BigDecimal companyCode = null;
					List<CompanyMasterDesc> lstcompanymaster = iCompanyMasterservice
							.viewById(sessionStateManage.getCompanyId());
					if (lstcompanymaster.size() != 0) {
						CompanyMasterDesc companycode = lstcompanymaster.get(0);
						if (companycode.getFsCompanyMaster().getCompanyCode() != null) {
							companyCode = companycode.getFsCompanyMaster()
									.getCompanyCode();
						}
					}

					HashMap<String, Object> fetchTransferToRemits = stopPaymentService
							.fetchTransferForStopPayment(
									companyCode,
									new BigDecimal(
											Constants.DOCUMENT_CODE_FOR_REMITTANCE_TRANSACTION),
									getRemittanceYear(), getTransferNo(),
									Constants.RefundForm);

					if ((String) fetchTransferToRemits.get("ERRMSG") != null
							&& !((String) fetchTransferToRemits.get("ERRMSG"))
									.equalsIgnoreCase("")) {

						setTransferNo(null);
						setErrorMessage((String) fetchTransferToRemits
								.get("ERRMSG"));
						RequestContext.getCurrentInstance().execute(
								"csp.show();");
						clear();
						return;

					} else {
						remittanceTransaction = getiStopPaymentCollectionService()
								.getRemittanceTransactionFromView(
										sessionStateManage.getCountryId(),
										getRemittanceYear().intValue(),
										getTransferNo(),
										sessionStateManage.getCompanyId(),
										new BigDecimal(
												Constants.DOCUMENT_CODE_FOR_REMITTANCE_TRANSACTION));

						if (remittanceTransaction != null) {
							fetchRemittanceView(remittanceTransaction);
						} else {
							setErrorMessage("Transaction Number Not Transfer from OLD "
									+ getTransferNo());
							setTransferNo(null);
							RequestContext.getCurrentInstance().execute(
									"csp.show();");
						}
					}
				}
				log.info("=========== getRemittanceTransactionDetails() End============ ");
			} else {
				setErrorMessage("Transaction Year and Number not Available for "
						+ getDocumentNum());
				RequestContext.getCurrentInstance().execute("csp.show();");
			}
		} catch (Exception e) {
			setErrorMessage("Exception occurred " + e);
			setTransferNo(null);
			RequestContext.getCurrentInstance().execute("csp.show();");

		}
	}

	// fetch all details from RemittanceView
	public void fetchRemittanceView(RemittanceView remittanceTransaction) {
		if (remittanceTransaction.getBranchName() != null) {
			setBranchName(remittanceTransaction.getBranchName());
		} else {
			setBranchName(null);
		}
		List<Customer> customer = getiStopPaymentCollectionService()
				.getCustomerInfo(remittanceTransaction.getCustomerId());
		setCustomerCode(remittanceTransaction.getCustomerRefNo());
		setCustomerId(remittanceTransaction.getCustomerId());
		for (Customer customer2 : customer) {
			setCustomerName((customer2.getFirstName() == null ? "" : customer2
					.getFirstName())
					+ " "
					+ (customer2.getMiddleName() == null ? "" : customer2
							.getMiddleName())
					+ " "
					+ (customer2.getLastName() == null ? "" : customer2
							.getLastName()));
			setTelephoneNo(customer2.getMobile());
			setCustomerRefNo(customer2.getCustomerReference());
		}
		List<CurrencyMaster> currencyList = iPersonalRemittanceService
				.getCurrencyMasterList(sessionStateManage.getCountryId(),
						remittanceTransaction.getSaleCurrencyId());
		if (currencyList != null) {
			setTransferAmountCurrency(currencyList.get(0).getQuoteName());
			setCurrencyId(currencyList.get(0).getCurrencyId());
		} else {
			setTransferAmountCurrency(null);
		}
		setProductName(productName);
		setProductStatus(getTransactionStatus(remittanceTransaction
				.getTransactionStatus()));
		setTransferAmount(remittanceTransaction.getLocalNetTrxAmount()
				.toString());
		setTransferDate(remittanceTransaction.getDocumentDate());
		setPaymentDate(new Date());
		setReceiptNo(new BigDecimal(getDocumentSerialID(Constants.Yes)));
		setForeignCurrencyId(remittanceTransaction.getSaleCurrencyId());
		setLocalChargeCurrencyId(remittanceTransaction
				.getLocalChargeCurrnecyId());
		setTransferAmount(remittanceTransaction.getPurchaseAmount().toString());
		// JAF COMMENTED
		//setExchangeRate(remittanceTransaction.getExchangeRateApplied());
		setBankBranchId(remittanceTransaction.getBankBranchId());
		setLocalCurrencyId(remittanceTransaction.getPurchaseCurrencyId());
		setLocalCommissionCurrencyId(remittanceTransaction
				.getLocalChargeCurrnecyId());
		setForeignCurrencyId(remittanceTransaction.getSaleCurrencyId());
		setLocalChargeCurrencyId(remittanceTransaction
				.getLocalChargeCurrnecyId());
		setLocalTrnxCurrencyId(remittanceTransaction.getSaleCurrencyId());
		setLocalDeliveryCurrencyId(remittanceTransaction
				.getLocalDeliveryCurrnecyId());
		setTransferAmount(remittanceTransaction.getSaleAmount().toString());
		// setRefundFor(remittanceTransaction.getSaleAmount().toString());
		
		// JAF COMMENTED
		/*BigDecimal refundfor = GetRound.roundBigDecimal(remittanceTransaction
				.getPurchaseAmount(), foreignLocalCurrencyDenominationService
				.getDecimalPerCurrency(new BigDecimal(sessionStateManage
						.getCurrencyId())));
		setRefundFor(refundfor.toPlainString());*/
		
		// setTransferDate(remittanceTransaction.getCreatedDate());
		setTransactionId(remittanceTransaction.getRemittanceTransactionId());
		// setProductStatus(getTransactionStatus(remittanceTransaction.getTransactionStatus()));
		// setExchangeRate(remittanceTransaction.getExchangeRateApplied());
		// setCommission(remittanceTransaction.getLocalCommisionAmount());
		setCommission(GetRound.roundBigDecimal(remittanceTransaction
				.getLocalCommisionAmount(),
				foreignLocalCurrencyDenominationService
						.getDecimalPerCurrency(new BigDecimal(
								sessionStateManage.getCurrencyId()))));
		// setCharges(remittanceTransaction.getLocalChargeAmount());
		setCharges(GetRound.roundBigDecimal(remittanceTransaction
				.getLocalChargeAmount(),
				foreignLocalCurrencyDenominationService
						.getDecimalPerCurrency(new BigDecimal(
								sessionStateManage.getCurrencyId()))));
		// setDeliveryCharges(remittanceTransaction.getLocalDeliveryAmount());
		if (remittanceTransaction.getLocalDeliveryAmount() != null)
			setDeliveryCharges(GetRound.roundBigDecimal(remittanceTransaction
					.getLocalDeliveryAmount(),
					foreignLocalCurrencyDenominationService
							.getDecimalPerCurrency(new BigDecimal(
									sessionStateManage.getCurrencyId()))));
		// setNetRefund(remittanceTransaction.getLocalNetTrxAmount());
		// setNetRefund(GetRound.roundBigDecimal(remittanceTransaction.getLocalNetTrxAmount(),
		// foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new
		// BigDecimal(sessionStateManage.getCurrencyId()))));
		// JAF COMMENTED
		/*setNetRefund(GetRound.roundBigDecimal(remittanceTransaction
				.getPurchaseAmount(), foreignLocalCurrencyDenominationService
				.getDecimalPerCurrency(new BigDecimal(sessionStateManage
						.getCurrencyId()))));*/
		// setCalNetAmountPaid(GetRound.roundBigDecimal(remittanceTransaction.getLocalNetTrxAmount(),
		// foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new
		// BigDecimal(sessionStateManage.getCurrencyId()))));//
		// .toPlainString();
		// JAF COMMENTED
		/*setCalNetAmountPaid(GetRound.roundBigDecimal(remittanceTransaction
				.getPurchaseAmount(), foreignLocalCurrencyDenominationService
				.getDecimalPerCurrency(new BigDecimal(sessionStateManage
						.getCurrencyId()))));// .toPlainString();
		setColCash(getCalNetAmountPaid());*/
		String product = getiPersonalRemittanceService()
				.getRemittanceServiceRuleName(
						remittanceTransaction.getBankCountryId(),
						remittanceTransaction.getSaleCurrencyId(),
						remittanceTransaction.getBankId(),
						remittanceTransaction.getRemittanceModeId(),
						remittanceTransaction.getDeliveryModeId());
		if (product != null) {
			setProductName(product);
		} else {
			setProductName(null);
		}
		List<RemittanceTranxBenificiary> trnxBenificiaries = getiStopPaymentService()
				.viewDetailsTranxBeneficiary(getTransactionId());
		for (RemittanceTranxBenificiary remittancetrnxBenificiary : trnxBenificiaries) {
			setBenificiary(remittancetrnxBenificiary.getBeneficiaryFirstName()
					+ " "
					+ remittancetrnxBenificiary.getBeneficiarySecondName()
					+ " " + "A/C No:"
					+ remittancetrnxBenificiary.getBeneficiaryAccountNo()
					+ ", " + remittancetrnxBenificiary.getBeneficiaryBank()
					+ " " + remittancetrnxBenificiary.getBeneficiaryBranch());
			setPayableBranch(remittancetrnxBenificiary.getBeneficiaryBranch());
			setPayableBank(remittancetrnxBenificiary.getBeneficiaryBank());
		}
		setPaymentDate(remittanceTransaction.getDocumentDate());
		setBooRenderPaymentDetails(false);
		setBooRenderButton(false);
		setBooRenderPaymentModeDetails(false);
		setBooRenderTransferModeDetails(true);
		// denaMinLstData();
		refundDenominationData();
	}

	// fetch all details from RemittanceView
	/*
	 * public void fetchRemittanceTrnxOldView(RemittanceTxnOLDView
	 * remittanceTransaction){ if
	 * (remittanceTransaction.getBeneficaryBranchName() != null) {
	 * setBranchName(remittanceTransaction.getBeneficaryBranchName()); } else {
	 * setBranchName(null); } List<Customer> customer =
	 * getiStopPaymentCollectionService
	 * ().getCustomerInfo(remittanceTransaction.getCustomerId());
	 * setCustomerCode(remittanceTransaction.getCustomerReference());
	 * setCustomerId(remittanceTransaction.getCustomerId()); for (Customer
	 * customer2 : customer) { setCustomerName((customer2.getFirstName()==null?
	 * "" :customer2.getFirstName()) + " " + (customer2.getMiddleName()==null?
	 * "" :customer2.getMiddleName()) + " " + (customer2.getLastName()==null? ""
	 * :customer2.getLastName())); setTelephoneNo(new
	 * BigDecimal(customer2.getMobile()));
	 * setCustomerRefNo(customer2.getCustomerReference()); }
	 * List<CurrencyMaster> currencyList =
	 * iPersonalRemittanceService.getCurrencyMasterList
	 * (sessionStateManage.getCountryId(),
	 * remittanceTransaction.getForeignCurrencyId()); if (currencyList != null)
	 * { setTransferAmountCurrency(currencyList.get(0).getQuoteName());
	 * setCurrencyId(currencyList.get(0).getCurrencyId()); } else {
	 * setTransferAmountCurrency(null); } setProductName(productName);
	 * setProductStatus
	 * (getTransactionStatus(remittanceTransaction.getTransactionStatus()));
	 * setTransferAmount
	 * (remittanceTransaction.getLocalNetTranxAmount().toPlainString());
	 * setTransferDate(remittanceTransaction.getDocumentDate());
	 * setPaymentDate(new Date()); setReceiptNo(new
	 * BigDecimal(getDocumentSerialID(Constants.Yes)));
	 * setForeignCurrencyId(remittanceTransaction.getForeignCurrencyId());
	 * setLocalChargeCurrencyId
	 * (remittanceTransaction.getLocalTranxCurrencyId());
	 * setTransferAmount(remittanceTransaction
	 * .getLocalTranxAmount().toString());
	 * setExchangeRate(remittanceTransaction.getExchangeRateApplied());
	 * setBankBranchId(remittanceTransaction.getBankBranchId());
	 * setLocalCurrencyId(remittanceTransaction.getLocalTranxCurrencyId());
	 * setLocalCommissionCurrencyId
	 * (remittanceTransaction.getLocalTranxCurrencyId());
	 * setForeignCurrencyId(remittanceTransaction.getForeignCurrencyId());
	 * setLocalChargeCurrencyId
	 * (remittanceTransaction.getLocalTranxCurrencyId());
	 * setLocalTrnxCurrencyId(remittanceTransaction.getForeignCurrencyId());
	 * setLocalDeliveryCurrencyId
	 * (remittanceTransaction.getLocalTranxCurrencyId());
	 * setTransferAmount(remittanceTransaction
	 * .getForeignTransactionAmount().toString());
	 * //setRefundFor(remittanceTransaction.getSaleAmount().toString());
	 * BigDecimal refundfor =
	 * GetRound.roundBigDecimal(remittanceTransaction.getLocalTranxAmount(),
	 * foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new
	 * BigDecimal(sessionStateManage.getCurrencyId())));
	 * setRefundFor(refundfor.toPlainString()); //
	 * setTransferDate(remittanceTransaction.getCreatedDate());
	 * setTransactionId(remittanceTransaction.getRemittanceTransactionId()); //
	 * setProductStatus
	 * (getTransactionStatus(remittanceTransaction.getTransactionStatus())); //
	 * setExchangeRate(remittanceTransaction.getExchangeRateApplied()); //
	 * setCommission(remittanceTransaction.getLocalCommisionAmount());
	 * setCommission
	 * (GetRound.roundBigDecimal(remittanceTransaction.getLocalCommisionAmount
	 * (), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new
	 * BigDecimal(sessionStateManage.getCurrencyId())))); //
	 * setCharges(remittanceTransaction.getLocalChargeAmount());
	 * setCharges(GetRound
	 * .roundBigDecimal(remittanceTransaction.getLocalChargeAmount(),
	 * foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new
	 * BigDecimal(sessionStateManage.getCurrencyId())))); //
	 * setDeliveryCharges(remittanceTransaction.getLocalDeliveryAmount()); if
	 * (remittanceTransaction.getLocalDeliveryAmount() != null)
	 * setDeliveryCharges
	 * (GetRound.roundBigDecimal(remittanceTransaction.getLocalDeliveryAmount(),
	 * foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new
	 * BigDecimal(sessionStateManage.getCurrencyId())))); //
	 * setNetRefund(remittanceTransaction.getLocalNetTrxAmount());
	 * //setNetRefund
	 * (GetRound.roundBigDecimal(remittanceTransaction.getLocalNetTrxAmount(),
	 * foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new
	 * BigDecimal(sessionStateManage.getCurrencyId()))));
	 * setNetRefund(GetRound.roundBigDecimal
	 * (remittanceTransaction.getLocalTranxAmount(),
	 * foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new
	 * BigDecimal(sessionStateManage.getCurrencyId()))));
	 * //setCalNetAmountPaid(GetRound
	 * .roundBigDecimal(remittanceTransaction.getLocalNetTrxAmount(),
	 * foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new
	 * BigDecimal(sessionStateManage.getCurrencyId()))));// .toPlainString();
	 * setCalNetAmountPaid
	 * (GetRound.roundBigDecimal(remittanceTransaction.getLocalTranxAmount(),
	 * foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new
	 * BigDecimal(sessionStateManage.getCurrencyId()))));// .toPlainString();
	 * setColCash(getCalNetAmountPaid()); String product =
	 * getiPersonalRemittanceService
	 * ().getRemittanceServiceRuleName(remittanceTransaction.getBankCountryId(),
	 * remittanceTransaction.getSaleCurrencyId(),
	 * remittanceTransaction.getBankId(),
	 * remittanceTransaction.getRemittanceModeId(),
	 * remittanceTransaction.getDeliveryModeId()); if (product != null) {
	 * setProductName(product); } else { setProductName(null); } String
	 * productName =
	 * iStopPaymentCollectionService.getProductName(remittanceTransaction
	 * .getBankId(), remittanceTransaction.getRemittanceModeId(),
	 * remittanceTransaction.getDeliveryModeId(),
	 * remittanceTransaction.getApplicationCountryId(),
	 * remittanceTransaction.getForeignCurrencyId()); if (productName != null) {
	 * setProductName(productName); } else { setProductName(null); }
	 * 
	 * List<RemittanceTranxBenificiary> trnxBenificiaries =
	 * getiStopPaymentService().viewDetailsTranxBeneficiary(getTransactionId());
	 * for (RemittanceTranxBenificiary remittancetrnxBenificiary :
	 * trnxBenificiaries) {
	 * setBenificiary(remittancetrnxBenificiary.getBeneficiaryFirstName() + " "
	 * + remittancetrnxBenificiary.getBeneficiarySecondName() + " " + "A/C No:"
	 * + remittancetrnxBenificiary.getBeneficiaryAccountNo() + ", " +
	 * remittancetrnxBenificiary.getBeneficiaryBank() + " " +
	 * remittancetrnxBenificiary.getBeneficiaryBranch());
	 * setPayableBranch(remittancetrnxBenificiary.getBeneficiaryBranch());
	 * setPayableBank(remittancetrnxBenificiary.getBeneficiaryBank()); }
	 * 
	 * setBenificiary(remittanceTransaction.getBeneficaryName() + " " +
	 * "A/C No:" + remittanceTransaction.getBeneficaryAccountNumber() + ", " +
	 * remittanceTransaction.getBeneficaryBankName() + " " +
	 * remittanceTransaction.getBeneficaryBranchName());
	 * setPayableBranch(remittanceTransaction.getBeneficaryBranchName());
	 * setPayableBank(remittanceTransaction.getBeneficaryBankName());
	 * 
	 * setPaymentDate(remittanceTransaction.getDocumentDate());
	 * setBooRenderPaymentDetails(false); setBooRenderButton(false);
	 * setBooRenderPaymentModeDetails(false);
	 * setBooRenderTransferModeDetails(true); // denaMinLstData();
	 * refundDenominationData(); }
	 */

	private String dateFormat = "dd/MM/yyyy";
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void navigateToRefundRequestPage() {

		clear();
		setCompanyName(null);
		setCompanyId(null);
		setSystemDate(null);
		setApplicationYear(null);
		setCompanyName(getiStopPaymentCollectionService().getCompanyName(
				sessionStateManage.getCompanyId(),
				sessionStateManage.getLanguageId()));
		setSystemDate(new SimpleDateFormat(dateFormat).format(new Date()));
		getApplicationYearFromdb();
		// nextApplicationNo();
		hideAllPanels();
		toFetchAllDocFinanceYear();
		fectchDocumentNumbers();
		setBooRenderFirstPanel(true);

		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "refundrequest.xhtml");
			ExternalContext context = FacesContext.getCurrentInstance()
					.getExternalContext();
			context.redirect("../remittance/refundrequest.xhtml");
		} catch (Exception e) {
			log.info("Problem to redirect: " + e);
		}

		/*
		 * try {
		 * 
		 * FacesContext.getCurrentInstance().getExternalContext().redirect(
		 * "../remittance/refundrequest.xhtml"); clear();
		 * 
		 * setBooRenderCollection(false);
		 * 
		 * setCompanyName(cancelReissueSevice.getCompanyName(sessionManage.
		 * getCompanyId(), sessionManage.getLanguageId())); setSystemDate(new
		 * SimpleDateFormat(dateFormat).format(new Date()));
		 * getApplicationYearFromdb();
		 * 
		 * nextApplicationNo();
		 * 
		 * } catch (Exception e) { // TODO: handle exception }
		 */

	}

	public String getTransactionStatus(String status) {

		String returnStatus = null;
		if (null == status) {
			returnStatus = "Not Cleared";
		}

		return returnStatus;
	}

	private Date currentTime = null;

	public void getSysTimeZone() {

		Date objList = getiGeneralService().getSysDateTimestamp(
				sessionStateManage.getCountryId());

		if (objList != null) {
			currentTime = objList;
		} else {

			// currentTime.getTime();
			currentTime = null;
		}

		// Date dat = new Date(currentTime.getTime());

		System.out.println("Current Date: " + currentTime);
		SimpleDateFormat ft = new SimpleDateFormat("yyyy.MMMMM.dd hh:mm aaa");

		System.out.println("Current Date: " + ft.format(currentTime));

	}

	public void clickOk() {

		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../remittance/refundrequest.xhtml");
			setColpaymentmodeId(null);
			// setBooRenderPaymentModeDetails(false);
			setBooRenderTransferModeDetails(false);
			setPaymentDeatailsPanel(false);
			setBooRenderPaymentDetails(true);
			setBooRenderCollection(true);
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public void Ok() {

		try {

			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../remittance/refundrequest.xhtml");
			clear();
			setBooRenderPaymentDetails(false);
			setBooRenderPaymentModeDetails(false);
			setBooRenderFirstPanel(true);
			// setSuccessPanel(true);
			setBooRenderButton(false);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void clickOnOk() {

		try {

			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../remittance/refundrequest.xhtml");

			setCustomerRefNo(null);
			setTransferNo(null);
			// setProductCode(null);
			setProductName(null);
			setProductStatus(null);
			// setApplicationNo(null);
			setBranchCode(null);
			setBranchName(null);
			setCustomerCode(null);
			setCustomerName(null);
			setTelephoneNo(null);
			setTransferDate(null);
			// setValidUntill(null);
			setAccPayee(null);
			setBeneficiaryName(null);
			setBeneficiaryAccNo(null);
			setBeneficiaryBankId(null);
			setBeneficiaryBankBranchId(null);
			setBeneficiaryBankCountryId(null);
			setTransferAmount(null);
			setPayableAt(null);
			setPayableBranch(null);
			setPayableBank(null);
			setPayableBranchCode(null);
			setRefundFor(null);
			setExchangeRate(null);
			setCurrencyCode(null);
			setReferenceAmount(null);
			setCommission(null);
			setCharges(null);
			setDeliveryCharges(null);
			setRateAdjust(null);
			setOtherAdjust(null);
			setColCash(null);
			setNetRefund(null);
			setPaymentNo(null);
			setPaymentDate(null);
			setRemarks(null);
			setBenificiary(null);
			setCalNetAmountPaid(null);
			setColpaymentmodeId(null);
			setBooRendercollectiondatatable(false);
			setBooRenderColDebitCard(false);
			setBooRenderCollection(false);
			setPaymentDeatailsPanel(false);
			// setBooRenderPaymentDetails(true);
			setBooRendercashdenomination(false);
			setBoRenderTotalAmountCalPanel(false);
			setBooRenderPaymentDetails(false);
			setBooRenderPaymentModeDetails(false);
			// setBooRenderTransferModeDetails(true);
			// setSuccessPanel(false);
			setBooRenderButton(false);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	private void denaMinLstData() {
		lstData.clear();
		ArrayList<ForeignLocalCurrencyDataTable> localLstData = new ArrayList<ForeignLocalCurrencyDataTable>();
		localLstData.clear();
		if (localLstData.size() == 0) {
			List<CurrencyWiseDenomination> currencyWiseDenolst = iPersonalRemittanceService
					.getCurrencyDenominations(
							new BigDecimal(sessionStateManage.getCurrencyId()),
							sessionStateManage.getCountryId());
			int serial = 1;
			for (CurrencyWiseDenomination currencyDenObj : currencyWiseDenolst) {
				ForeignLocalCurrencyDataTable forLocalCurrencyDtObj = new ForeignLocalCurrencyDataTable();
				forLocalCurrencyDtObj.setSerial(serial);
				forLocalCurrencyDtObj.setItem(currencyDenObj
						.getDenominationAmount());
				forLocalCurrencyDtObj.setQty("");
				forLocalCurrencyDtObj.setPrice("");
				forLocalCurrencyDtObj.setDenominationId(currencyDenObj
						.getDenominationId());

				forLocalCurrencyDtObj.setCurrencyId(currencyDenObj
						.getExCurrencyMaster().getCurrencyId());
				forLocalCurrencyDtObj.setDenominationDesc(currencyDenObj
						.getDenominationDesc());
				forLocalCurrencyDtObj.setDenominationAmount(currencyDenObj
						.getDenominationAmount());
				localLstData.add(forLocalCurrencyDtObj);
				serial++;
			}
		}
		/* Responsible to keep sum of total amount of cash entered */
		int totalSum = 0;
		/* Responsible to calculate sum of entered cash amount */
		for (ForeignLocalCurrencyDataTable element : localLstData) {
			if (element.getPrice().length() != 0) {
				totalSum = totalSum + Integer.parseInt(element.getPrice());
			}
		}
		System.out.println(totalSum);
		setDenomtotalCash(GetRound.roundBigDecimal(new BigDecimal(totalSum),
				foreignLocalCurrencyDenominationService
						.getDecimalPerCurrency(new BigDecimal(
								sessionStateManage.getCurrencyId()))));
		setCashAmount(getPayRefund());

		/*
		 * setPayNetPaidAmount(getCalNetAmountPaid());
		 * setPayRefund(getPayRefund()); setPayPaidAmount(getPayPaidAmount());
		 * if (getPayRefund().compareTo(BigDecimal.ZERO) == 0) {
		 * setNextOrSaveButton(Constants.Save); } else {
		 * setNextOrSaveButton(Constants.Next); }
		 */

		setLstData(localLstData); // Adding denomication here
		if (localLstData.size() != 0) {
			setDenominationchecking(Constants.DenominationAvailable);
		} else {
			setDenominationchecking(Constants.DenominationNotAvailable);
		}
	}

	private BigDecimal denomtotalCash;
	private BigDecimal payRefund;

	public BigDecimal getDenomtotalCash() {
		return denomtotalCash;
	}

	public void setDenomtotalCash(BigDecimal denomtotalCash) {
		this.denomtotalCash = denomtotalCash;
	}

	public BigDecimal getPayRefund() {
		return payRefund;
	}

	public void setPayRefund(BigDecimal payRefund) {
		this.payRefund = payRefund;
	}

	ForeignLocalCurrencyDataTable dataTableClear = new ForeignLocalCurrencyDataTable();

	public ForeignLocalCurrencyDataTable getDataTableClear() {
		return dataTableClear;
	}

	public void setDataTableClear(ForeignLocalCurrencyDataTable dataTableClear) {
		this.dataTableClear = dataTableClear;
	}

	public void clearDataTableClearDenomination() {
		if (getDataTableClear() != null) {
			ForeignLocalCurrencyDataTable foreignLocalCurrencyDataTable = getDataTableClear();

			System.out.println("foreignLocalCurrencyDataTable"
					+ foreignLocalCurrencyDataTable);
			try {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("../remittance/refundrequest.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void onCellEdit(
			ForeignLocalCurrencyDataTable foreignLocalCurrencyDataTable) {
		BigDecimal qty = null;

		if (foreignLocalCurrencyDataTable.getQty() == ""
				&& foreignLocalCurrencyDataTable.getQty() != null) {
			qty = new BigDecimal(0);
			System.out
					.println("qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq ZEROOOOOOOOOOOOOOOO");
		} else {
			System.out.println("foreignLocalCurrencyDataTable.getQty()"
					+ foreignLocalCurrencyDataTable.getQty());

			try {
				qty = new BigDecimal(foreignLocalCurrencyDataTable.getQty());
			} catch (Exception e) {

				System.out.println("Exception occcured " + e);
				System.out.println("Exception occcured " + e);
				System.out.println("Exception occcured " + e);
				qty = new BigDecimal(0);

			}

			System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		}
		BigDecimal totalcashAmount = null;
		try {
			totalcashAmount = foreignLocalCurrencyDataTable.getItem().multiply(
					qty);
		} catch (Exception e) {
			System.out
					.println("Exceptionnnnnnnnnnnnn---------------------->QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ"
							+ e);
		}

		System.out.println("@@@@@@@@@@@@@@@@@"
				+ foreignLocalCurrencyDataTable.getQty() == null);
		System.out.println("#################"
				+ foreignLocalCurrencyDataTable.getQty().equals(""));

		if (foreignLocalCurrencyDataTable.getQty().equals("")) {
			foreignLocalCurrencyDataTable.setQty("");
			// added by rabil for clear if blank
			foreignLocalCurrencyDataTable.setPrice("");
		}
		if (totalcashAmount != null && totalcashAmount.doubleValue() != 0.0) {

			try {
				foreignLocalCurrencyDataTable.setPrice(GetRound
						.roundBigDecimal(
								totalcashAmount,
								foreignLocalCurrencyDenominationService
										.getDecimalPerCurrency(new BigDecimal(
												sessionStateManage
														.getCurrencyId())))
						.toPlainString());
			} catch (Exception e) {
				System.out
						.println("Exceptionnnnnnnnnnnnn---------------------->11111"
								+ e);
			}

		} else {
			foreignLocalCurrencyDataTable.setPrice("");
		}
		BigDecimal totalSum = BigDecimal.ZERO;
		/* Responsible to calculate sum of entered cash amount */
		for (ForeignLocalCurrencyDataTable element : lstData) {
			if (element.getPrice().length() != 0) {
				totalSum = totalSum.add(new BigDecimal(element.getPrice()));
			}
		}
		// BigDecimal totalDenoCash = getDenomtotalCash();
		if (getCashAmount().compareTo(totalSum) < 0) {
			totalSum = BigDecimal.ZERO;
			foreignLocalCurrencyDataTable.setQty("");
			foreignLocalCurrencyDataTable.setPrice("");
			for (ForeignLocalCurrencyDataTable element : lstData) {
				if (element.getPrice().length() != 0) {
					totalSum = totalSum.add(new BigDecimal(element.getPrice()));
				}
			}

			setDenomtotalCash(GetRound.roundBigDecimal(totalSum,
					foreignLocalCurrencyDenominationService
							.getDecimalPerCurrency(new BigDecimal(
									sessionStateManage.getCurrencyId()))));
			// setPayRefund(getPayPaidAmount().subtract(getCashAmount()));
			// setPayPaidAmount(getPayPaidAmount());
			// setCashAmount(totalcashAmount);
			// setPayNetPaidAmount(getDenomtotalCashreceived());
			RequestContext.getCurrentInstance().execute(
					"cleardenominationexceed.show();");
			setDataTableClear(foreignLocalCurrencyDataTable);
			foreignLocalCurrencyDataTable.setQty("");
			return;

		} else {
			try {
				setDenomtotalCash(GetRound.roundBigDecimal(totalSum,
						foreignLocalCurrencyDenominationService
								.getDecimalPerCurrency(new BigDecimal(
										sessionStateManage.getCurrencyId()))));
			} catch (Exception e) {
				System.out
						.println("Exceptionnnnnnnnnnnnn---------------------->33333333"
								+ e);
			}

			// setPayPaidAmount(getPayPaidAmount());
			// setPayRefund(getCashAmount().subtract(getCashAmount()));
			// setColCash(getColCash());
		}

	}

	public void updadateRecords() {

		BigDecimal paymentReciptPk = getiStopPaymentCollectionService()
				.getReceiptPaymentTablePk(getCustomerId(), getTransferNo());
		if (null != paymentReciptPk) {
			getiStopPaymentCollectionService().updateReceiptPaymentTableData(
					paymentReciptPk);
		}

		BigDecimal remittanceCompliantPk = getiStopPaymentCollectionService()
				.getRemittanceCompliantPk(getTransferNo());
		if (null != remittanceCompliantPk) {
			getiStopPaymentCollectionService()
					.updateRemittanceCompliantTableData(paymentReciptPk);

		}

	}

	/**
	 * Foreign Currency Adjust Save
	 */
	/*
	 * @SuppressWarnings("unchecked") public void
	 * saveForeignCurrencyAdjust(HashMap<String, Object> returnResult) {
	 * log.info
	 * ("================ saveForeignCurrencyAdjust() Start =================="
	 * ); Collect collect = (Collect) returnResult.get("Collect");
	 * SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
	 * 
	 * Date acc_Month = null;
	 * 
	 * try { acc_Month = DATE_FORMAT.parse(getCurrentDateWithFormat()); } catch
	 * (ParseException e) { e.printStackTrace(); }
	 * 
	 * int i = 0; for (ForeignLocalCurrencyDataTable
	 * foreignLocalCurrencyDataTable : lstData) {
	 * System.out.println("foreignLocalCurrencyDataTable.getQty() ^^^^^^^^^^^^^^^^^"
	 * + foreignLocalCurrencyDataTable.getQty()); if
	 * (foreignLocalCurrencyDataTable.getQty() != null &&
	 * !foreignLocalCurrencyDataTable.getQty().equals("0")) {
	 * 
	 * ForeignCurrencyAdjust foreignCurrencyAdjust = new
	 * ForeignCurrencyAdjust(); // Company save CompanyMaster companyMaster =
	 * new CompanyMaster(); companyMaster.setCompanyId(new
	 * BigDecimal(sessionStateManage.getSessionValue("companyId")));
	 * foreignCurrencyAdjust.setFsCompanyMaster(companyMaster);
	 * 
	 * // Country Save CountryMaster countryMaster = new CountryMaster();
	 * countryMaster.setCountryId(new
	 * BigDecimal(sessionStateManage.getSessionValue("countryId")));
	 * foreignCurrencyAdjust.setFsCountryMaster(countryMaster);
	 * 
	 * // customer Save Customer customer = new Customer();
	 * customer.setCustomerId(getCustomerId());
	 * foreignCurrencyAdjust.setFsCustomer(customer);
	 * 
	 * foreignCurrencyAdjust.setDocumentDate(new Date()); //
	 * System.out.println(""+collect.getExCurrencyMaster().getCurrencyId()); //
	 * currency Id CurrencyMaster currencyMaster = new CurrencyMaster();
	 * currencyMaster.setCurrencyId(new
	 * BigDecimal(sessionStateManage.getCurrencyId()));
	 * foreignCurrencyAdjust.setFsCurrencyMaster(currencyMaster);
	 * 
	 * // foreignCurrencyAdjust.setNotesQuantity(new //
	 * BigDecimal(foreignLocalCurrencyDataTable.getQty()));
	 * foreignCurrencyAdjust.setAdjustmentAmount(new
	 * BigDecimal(foreignLocalCurrencyDataTable.getPrice()));
	 * 
	 * CurrencyWiseDenomination denominationMaster = new
	 * CurrencyWiseDenomination();
	 * denominationMaster.setDenominationId(foreignLocalCurrencyDataTable
	 * .getDenominationId());
	 * foreignCurrencyAdjust.setFsDenominationId(denominationMaster);
	 * foreignCurrencyAdjust.setDocumentCode(new
	 * BigDecimal(Constants.DOCUMENT_CODE_FOR_REFUND_REQUEST));
	 * 
	 * foreignCurrencyAdjust.setExchangeRate((BigDecimal)
	 * returnResult.get("ExchangeRate"));
	 * 
	 * foreignCurrencyAdjust.setDenaminationAmount(new
	 * BigDecimal(foreignLocalCurrencyDataTable.getPrice()));
	 * 
	 * foreignCurrencyAdjust.setDocumentFinanceYear(new
	 * BigDecimal(getFinanceYear()));
	 * foreignCurrencyAdjust.setOracleUser(sessionStateManage.getUserName()); //
	 * Tanumoy try { //
	 * foreignCurrencyAdjust.setDocumentCode(Constants.DOCUMENT_CODE_FOR_REMMITANCE
	 * ); // //wait foreignCurrencyAdjust.setDocumentLineNumber(new
	 * BigDecimal(++i)); foreignCurrencyAdjust.setAccountmmyyyy(acc_Month);
	 * 
	 * CountryBranch countryBranch = new CountryBranch();
	 * countryBranch.setCountryBranchId(new
	 * BigDecimal(sessionStateManage.getBranchId()));
	 * foreignCurrencyAdjust.setCountryBranch(countryBranch);
	 * 
	 * // foreignCurrencyAdjust.setDocumentStatus(Constants.YES); } catch
	 * (Exception e) { e.printStackTrace(); }
	 *//********************************************************************************************/
	/*
	 * 
	 * foreignCurrencyAdjust.setCreatedDate(new Date());
	 * foreignCurrencyAdjust.setCreatedBy(sessionStateManage.getUserName());
	 * 
	 * if (collect != null) {
	 * foreignCurrencyAdjust.setDocumentNo(collect.getDocumentNo());
	 * foreignCurrencyAdjust.setCollect(collect); }
	 * 
	 * iPersonalRemittanceService.saveForeignCurrencyAdjust(foreignCurrencyAdjust
	 * );
	 * 
	 * // updadateRecords(); //
	 * RequestContext.getCurrentInstance().execute("complete.show();");
	 * 
	 * Ok(); } else { log.info("Number of notes is 0"); //
	 * RequestContext.getCurrentInstance().execute("denaminationZero.show();");
	 * 
	 * } } log.info(
	 * "================ saveForeignCurrencyAdjust() End =================="); }
	 */

	// to get the accoMMYY value
	public static String getCurrentDateWithFormat() {
		Map<Integer, String> data = new HashMap<Integer, String>();
		for (int i = 0; i < 12; i++) {
			if (i < 9) {
				data.put(i, "0" + String.valueOf(i + 1));
			} else {
				data.put(i, String.valueOf(i + 1));
			}
		}
		// String year = String.valueOf(new Date().getYear()).substring(1, 3);
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		String year = String.valueOf(calendar.get(Calendar.YEAR));

		System.out.println(Calendar.getInstance().get(Calendar.MONTH));

		return "01/" + data.get(Calendar.getInstance().get(Calendar.MONTH))
				+ "/" + year;
	}

	// private BigDecimal payRefund;

	private String paymentMode;

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public BigDecimal getColpaymentmodeId() {
		return colpaymentmodeId;
	}

	public void setColpaymentmodeId(BigDecimal colpaymentmodeId) {
		this.colpaymentmodeId = colpaymentmodeId;
	}

	public BigDecimal getColCash() {
		return colCash;
	}

	public void setColCash(BigDecimal colCash) {
		this.colCash = colCash;
	}

	public String getColpaymentmodeName() {
		return colpaymentmodeName;
	}

	public void setColpaymentmodeName(String colpaymentmodeName) {
		this.colpaymentmodeName = colpaymentmodeName;
	}

	public BigDecimal getCashAmount() {
		return cashAmount;
	}

	public void setCashAmount(BigDecimal cashAmount) {
		this.cashAmount = cashAmount;
	}

	public BigDecimal getDenomdifference() {
		return denomdifference;
	}

	public void setDenomdifference(BigDecimal denomdifference) {
		this.denomdifference = denomdifference;
	}

	public String getPaymentDetailsremark() {
		return paymentDetailsremark;
	}

	public void setPaymentDetailsremark(String paymentDetailsremark) {
		this.paymentDetailsremark = paymentDetailsremark;
	}

	public BigDecimal getPayPaidAmount() {
		return payPaidAmount;
	}

	public void setPayPaidAmount(BigDecimal payPaidAmount) {
		this.payPaidAmount = payPaidAmount;
	}

	/*
	 * public BigDecimal getPayNetPaidAmount() { return payNetPaidAmount; }
	 * public void setPayNetPaidAmount(BigDecimal payNetPaidAmount) {
	 * this.payNetPaidAmount = payNetPaidAmount; }
	 */

	public BigDecimal getCalNetAmountPaid() {
		return calNetAmountPaid;
	}

	public void setCalNetAmountPaid(BigDecimal calNetAmountPaid) {
		this.calNetAmountPaid = calNetAmountPaid;
	}

	List<PaymentModeDesc> lstFetchAllPayMode = new ArrayList<PaymentModeDesc>();

	public List<PaymentModeDesc> getLstFetchPayMode() {
		return lstFetchAllPayMode;
	}

	public List<PaymentModeDesc> getLstFetchAllPayMode() {
		return lstFetchAllPayMode;
	}

	public void setLstFetchAllPayMode(List<PaymentModeDesc> lstFetchAllPayMode) {
		this.lstFetchAllPayMode = lstFetchAllPayMode;
	}

	public void fetchAllPaymentDetails() {
		lstFetchAllPayMode.clear();
		List<PaymentModeDesc> paymentModeList = ipaymentService
				.fetchPaymodeDesc(
						new BigDecimal(sessionStateManage
								.isExists("languageId") ? sessionStateManage
								.getSessionValue("languageId") : "1"),
						Constants.Yes);
		for (PaymentModeDesc paymentObj : paymentModeList) {
			if (paymentObj.getPaymentMode().getPaymentCode()
					.equalsIgnoreCase(Constants.CashCode)
					|| paymentObj.getPaymentMode().getPaymentCode()
							.equalsIgnoreCase(Constants.BankTransferCode)) {
				lstFetchAllPayMode.add(paymentObj);
			}
		}
	}

	// change by payment mode - cash and debit card
	public void changeofPaymentMode() {
		List<PaymentModeDesc> lstofPayment = ipaymentService
				.getPaymentDescLangList(new BigDecimal(sessionStateManage
						.isExists("languageId") ? sessionStateManage
						.getSessionValue("languageId") : "1"));
		// Boolean checkCash = false;
		String paymentModedesc = null;
		String paymentModeCode = null;
		if (lstofPayment.size() != 0) {
			for (PaymentModeDesc paymentModeDesc : lstofPayment) {
				if ((getColpaymentmodeId() == null ? BigDecimal.ZERO
						: getColpaymentmodeId()).compareTo(paymentModeDesc
						.getPaymentMode().getPaymentModeId()) == 0) {
					paymentModedesc = paymentModeDesc.getLocalPaymentName();
					paymentModeCode = paymentModeDesc.getPaymentMode()
							.getPaymentCode();
					setColpaymentmodeName(paymentModedesc);
					setColpaymentmodeCode(paymentModeCode);
					break;
				} else {
					// paymentModedesc = null;
					setColpaymentmodeName(null);
					setColpaymentmodeCode(null);
				}
			}
			if (getColpaymentmodeCode() != null) {
				List<PaymentMode> paymentModedetails = ipaymentService
						.getPaymentCheck(getColpaymentmodeCode());
				if (paymentModedetails.size() != 0) {
					// payment mode bank variables
					setColBankCode(null);
					setColCardNo(null);
					setPopulatedDebitCardNumber(null);
					// setColCash(null);
					setColAuthorizedby(null);
					setColpassword(null);
					setColNameofCard(null);
					setColApprovalNo(null);
					// payment mode Cheque variables
					setColchequebankCode(null);
					setColChequeRef(null);
					setColChequeDate(null);
					setColChequeApprovalNo(null);
					// payment mode bank transfer
					setBankTransferApprovalNo(null);
					// bank Transfer Bank Code
					setColBankTransferBankCode(null);

					if (getColpaymentmodeCode().equalsIgnoreCase(
							Constants.CashCode)) {
						setBooRenderColDebitCard(false);
						setBooRenderColCheque(false);
						setBooRenderBankTransfer(false);
						setPaymentMode(Constants.CASHNAME);
					} else if (getColpaymentmodeCode().equalsIgnoreCase(
							Constants.KNETCode)) {
						setBooRenderColCheque(false);
						setBooRenderColDebitCard(true);
						setBooRenderBankTransfer(false);
					} else if (getColpaymentmodeCode().equalsIgnoreCase(
							Constants.ChequeCode)) {
						setBooRenderColDebitCard(false);
						setBooRenderColCheque(true);
						setBooRenderBankTransfer(false);
					} else if (getColpaymentmodeCode().equalsIgnoreCase(
							Constants.BankTransferCode)) {
						setBooRenderColDebitCard(false);
						setBooRenderColCheque(false);
						setBooRenderBankTransfer(true);
						getLocalBankListforIndicator();
					} else {
						setColpaymentmodeId(null);
						setBooRenderColDebitCard(false);
						setBooRenderColCheque(false);
						setBooRenderBankTransfer(false);
						System.out.println("Payment Mode Newly added");
						RequestContext.getCurrentInstance().execute(
								"checkPaymentModeService.show();");
						return;
					}
				}
			} else {
				setBooRenderColDebitCard(false);
				setBooRenderColCheque(false);
				setBooRenderBankTransfer(false);
			}

		}

	}

	public void back() {
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../remittance/refundrequest.xhtml");

		} catch (Exception e) {
			// TODO: handle exception
		}
		setBooRenderPaymentDetails(false);
		setBooRenderPaymentModeDetails(false);

		setBooRenderTransferModeDetails(true);
		setBooRenderButton(false);
		setBooRenderCollection(false);

	}

	public void goBack() {
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../remittance/refundrequest.xhtml");

		} catch (Exception e) {
			// TODO: handle exception
		}
		setBooRenderPaymentDetails(false);
		setBooRenderPaymentModeDetails(false);

		setBooRenderTransferModeDetails(true);
		setBooRenderButton(false);
		setBooRenderCollection(false);

	}

	public void exit() {
		try {
			clear();
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../registration/branchhome.xhtml");

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	//private BigDecimal colamountKWD = new BigDecimal(0);
	CopyOnWriteArrayList<PersonalRemittanceCollectionDataTable> coldatatablevalues = new CopyOnWriteArrayList<PersonalRemittanceCollectionDataTable>();

	/*public BigDecimal getColamountKWD() {
		return colamountKWD;
	}

	public void setColamountKWD(BigDecimal colamountKWD) {
		this.colamountKWD = colamountKWD;
	}*/

	// calculation of cash while entering
	/*
	 * public void checkcashcollection() {
	 * 
	 * if (coldatatablevalues.size() != 0) { colamountKWD = new BigDecimal(0);
	 * for (PersonalRemittanceCollectionDataTable collectionlst :
	 * coldatatablevalues) { colamountKWD =
	 * colamountKWD.add(collectionlst.getColAmountDT()); // START VISWA
	 * DINOMINATION CODE 2015/01/29 if
	 * (collectionlst.getColpaymentmodetypeDT().equals(Constants.CASHNAME)) {
	 * //setCashAmount(collectionlst.getColAmountDT());
	 * setColamountKWD(colamountKWD); } // END VISWA DINOMINATION CODE
	 * 2015/01/29 //setColamountKWD(colamountKWD); } //if
	 * (getColamountKWD().compareTo(getCalNetAmountPaid()) < 0 ||
	 * getColamountKWD().compareTo(getCalNetAmountPaid()) == 0) { if
	 * (getColamountKWD().compareTo(getCalNetAmountPaid()) < 0 ) { colamountKWD
	 * = getColamountKWD().add(getColCash());
	 * 
	 * if (getColamountKWD().compareTo(getCalNetAmountPaid()) < 0 ||
	 * getColamountKWD().compareTo(getCalNetAmountPaid()) == 0) {
	 * setColamountKWD(colamountKWD); } else { setColCash(null);
	 * setColpaymentmodeId(null);
	 * RequestContext.getCurrentInstance().execute("amountgreater.show();"); }
	 * 
	 * } else { setColCash(null);
	 * RequestContext.getCurrentInstance().execute("amountgreater.show();"); }
	 * 
	 * } else { if (getColCash().compareTo(getCalNetAmountPaid()) < 0 ||
	 * getColCash().compareTo(getCalNetAmountPaid()) == 0) {
	 * setColamountKWD(getColCash());
	 * System.out.println("#######Direct##########" + getColCash()); } else {
	 * setColCash(null);
	 * RequestContext.getCurrentInstance().execute("amountgreater.show();"); } }
	 * }
	 */

	// to remove details from data table after adding
	public void deletePaymentModeRecords(
			PersonalRemittanceCollectionDataTable collectionDt) {
		if (coldatatablevalues.size() > 0) {
			// subtractedAmount =getToalUsedAmount().intValue() -
			// collectionDt.getColAmountDT().intValue();
			subtractedAmount = getToalUsedAmount().subtract(
					collectionDt.getColAmountDT());
			setToalUsedAmount(subtractedAmount);
		} else {
			setToalUsedAmount(null);
		}

		coldatatablevalues.remove(collectionDt);

		if (coldatatablevalues.size() != 0) {
			setBooRendercollectiondatatable(true);
			setBoRenderTotalAmountCalPanel(true);
		} else {
			setBooRendercollectiondatatable(false);
			setBoRenderTotalAmountCalPanel(false);
			clearingDetailAfterAdding();
		}

	}

	// after adding data to datatable and then reseting
	public void clearingDetailAfterAdding() {
		setColpaymentmodeId(null);
		setColpaymentmodeName(null);
		setColBankid(null);
		setColCardNo(null);
		setPopulatedDebitCardNumber(null);
		// setColCash(null);
		setColAuthorizedby(null);
		setColpassword(null);
		setColNameofCard(null);
		setBooAuthozed(false);

		changeofPaymentMode();

	}

	public void editRecord(
			PersonalRemittanceCollectionDataTable personalRemitObj) {

		BigDecimal paymentModeCashId = ipaymentService
				.fetchPaymodeMasterId(
						Constants.CASHNAME,
						new BigDecimal(sessionStateManage
								.isExists("languageId") ? sessionStateManage
								.getSessionValue("languageId") : "1"));

		if (paymentModeCashId != null) {
			if (coldatatablevalues.size() > 0) {
				if (personalRemitObj.getColAmountDT() != null) {
					subtractedAmount = getToalUsedAmount().subtract(
							personalRemitObj.getColAmountDT());
					setToalUsedAmount(subtractedAmount);
				}
			} else {
				setToalUsedAmount(null);
			}

			if (personalRemitObj.getColpaymentmodeIDtypeDT().equals(
					paymentModeCashId)) {
				setBooRenderColDebitCard(false);
				setBooRenderColCheque(false);
				setBooRenderBankTransfer(false);
				setBooRendercollectiondatatable(false);
				setColpaymentmodeId(personalRemitObj
						.getColpaymentmodeIDtypeDT());
				setColpaymentmodeName(personalRemitObj
						.getColpaymentmodetypeDT());
				setColpaymentmodeCode(personalRemitObj.getColpaymentmodeCode());
				setColCash(personalRemitObj.getColAmountDT());
				coldatatablevalues.remove(personalRemitObj);
			} else {
				setBooRenderColDebitCard(false);
				setBooRenderColCheque(false);
				setBooRenderBankTransfer(true);
				setBooRendercollectiondatatable(false);
				setColpaymentmodeId(personalRemitObj
						.getColpaymentmodeIDtypeDT());
				setColpaymentmodeName(personalRemitObj
						.getColpaymentmodetypeDT());
				setColpaymentmodeCode(personalRemitObj.getColpaymentmodeCode());
				// setColBankid(personalRemitObj.getColBankIdDT());
				// setColCardNo(personalRemitObj.getColCardNumberDT());
				// setColNameofCard(personalRemitObj.getColNameofCardDT());
				setColCash(personalRemitObj.getColAmountDT());

				setBankTransferApprovalNo(personalRemitObj.getColApprovalNo());

				coldatatablevalues.remove(personalRemitObj);
			}
		}
	}

	private int i = 0;

	private BigDecimal tempCash;

	public BigDecimal getTempCash() {
		return tempCash;
	}

	public void setTempCash(BigDecimal tempCash) {
		this.tempCash = tempCash;
	}

	// to add values to data table in collection
	public void calculatingNetAmountDT() {
		int i = 0;
		BigDecimal paymentModeCashId = ipaymentService
				.fetchPaymodeMasterId(
						Constants.CASHNAME,
						new BigDecimal(sessionStateManage
								.isExists("languageId") ? sessionStateManage
								.getSessionValue("languageId") : "1"));
		if (paymentModeCashId != null) {
			boolean flag = true;
			setBooRendercollectiondatatable(true);
			setBoRenderTotalAmountCalPanel(true);
			if (coldatatablevalues.size() != 0) {
				for (PersonalRemittanceCollectionDataTable collectionlst : coldatatablevalues) {
					i = 0;
					if (collectionlst.getColpaymentmodeIDtypeDT().compareTo(
							(getColpaymentmodeId() == null ? new BigDecimal(0)
									: getColpaymentmodeId())) == 0) {
						if (collectionlst.getColpaymentmodeIDtypeDT()
								.compareTo(paymentModeCashId) == 0) {
							clearingDetailAfterAdding();
							/*
							 * RequestContext.getCurrentInstance().execute(
							 * "cashexists.show();");
							 */
							flag = false;
							break;
						} else {
							if (collectionlst.getColBankCodeDT().compareTo(
									getColBankCode()) == 0) {
								if (collectionlst.getColCardNumberDT()
										.compareTo(getColCardNo()) == 0) {
									clearingDetailAfterAdding();
									RequestContext.getCurrentInstance()
											.execute("bankexists.show();");
									flag = false;
									break;
								} else {
									i = 1;
								}
							} else {
								i = 1;
							}
						}
					} else {
						i = 1;
					}
				}
				if (i == 1) {
					addPaymentModerecord();
				}
			} else {
				setToalUsedAmount(null);
				setTotalbalanceAmount(null);
				setTotalrefundAmount(null);
				addPaymentModerecord();
			}
			if (coldatatablevalues.size() > 0 && flag == true) {
				for (PersonalRemittanceCollectionDataTable collectionlst : coldatatablevalues) {
					// totalUamount=totalUamount+collectionlst.getColAmountDT().intValue();
					totalUamount = totalUamount.add(collectionlst
							.getColAmountDT());
				}
				setToalUsedAmount(totalUamount);
				if (getToalUsedAmount().compareTo(getCalNetAmountPaid()) > 0) {
					setTotalbalanceAmount(BigDecimal.ZERO);
					setTotalrefundAmount(getToalUsedAmount().subtract(
							getCalNetAmountPaid()));
				} else if (getToalUsedAmount().compareTo(getCalNetAmountPaid()) < 0) {
					setTotalbalanceAmount(getCalNetAmountPaid().subtract(
							getToalUsedAmount()));
					setTotalrefundAmount(BigDecimal.ZERO);
				} else {
					setTotalbalanceAmount(BigDecimal.ZERO);
					setTotalrefundAmount(BigDecimal.ZERO);
				}
			}
			clearingDetailAfterAdding();
		}
	}

	// adding to datatable list in payment mode
	public void addPaymentModerecord() {

		BigDecimal paymentModeCashId = ipaymentService
				.fetchPaymodeMasterId(
						Constants.CASHNAME,
						new BigDecimal(sessionStateManage
								.isExists("languageId") ? sessionStateManage
								.getSessionValue("languageId") : "1"));
		if (paymentModeCashId != null) {
			totalUamount = new BigDecimal(0);
			PersonalRemittanceCollectionDataTable personalcollectionDT = new PersonalRemittanceCollectionDataTable();
			personalcollectionDT
					.setColpaymentmodeIDtypeDT(getColpaymentmodeId());
			personalcollectionDT
					.setColpaymentmodetypeDT(getColpaymentmodeName());
			personalcollectionDT.setColpaymentmodeCode(getColpaymentmodeCode());
			if (getColBankCode() != null || getColchequebankCode() != null
					|| getColBankTransferBankCode() != null) {
				if (localbankListForBankCode.size() != 0) {
					ViewBankDetails lstDetaiks = localbankListForBankCode
							.get(0);
					personalcollectionDT.setColBankIdDT(lstDetaiks
							.getChequeBankId());
					personalcollectionDT.setColbankNameDT(lstDetaiks
							.getBankFullName());

					if (getColChequeRef() != null) {
						personalcollectionDT
								.setColBankCodeDT(getColchequebankCode());
						personalcollectionDT
								.setColchequeRefNo(getColChequeRef());
						personalcollectionDT
								.setColchequeDate(getColChequeDate());
						personalcollectionDT
								.setColApprovalNo(getColChequeApprovalNo());
					} else if (getColBankTransferBankCode() != null) {
						personalcollectionDT
								.setColBankCodeDT(getColBankTransferBankCode());
					} else {
						personalcollectionDT.setColBankCodeDT(getColBankCode());
						personalcollectionDT.setColCardNumberDT(getColCardNo());
						personalcollectionDT
								.setColNameofCardDT(getColNameofCard());
						personalcollectionDT
								.setColAuthorizedByDT(getColAuthorizedby());
						personalcollectionDT
								.setColApprovalNo(getColApprovalNo());
					}
				}
			}

			if (isBooRenderBankTransfer()) {
				personalcollectionDT
						.setColApprovalNo(getBankTransferApprovalNo());
			}

			if (getColCash() != null) {
				personalcollectionDT.setColAmountDT(GetRound.roundBigDecimal(
						getColCash(), foreignLocalCurrencyDenominationService
								.getDecimalPerCurrency(new BigDecimal(
										sessionStateManage.getCurrencyId()))));
			}
			if (getColpaymentmodeId() != null
					&& getColpaymentmodeId().compareTo(paymentModeCashId) == 0) {
				setTempCash(GetRound.roundBigDecimal(getColCash(),
						foreignLocalCurrencyDenominationService
								.getDecimalPerCurrency(new BigDecimal(
										sessionStateManage.getCurrencyId()))));
			}
			coldatatablevalues.add(personalcollectionDT);
		}
		setBooRenderSingleDebit(true);
		setBooRenderMulDebit(false);
		lstDebitCard.clear();
		setColCardNo(null);

	}

	private BigDecimal colremittanceNo;
	private String colpaymentmodeCode;
	private String colApprovalNo;
	private BigDecimal colCardNoLength;
	private String colBankCode;

	private boolean booRenderSingleDebit = true;
	private boolean booRenderMulDebit = false;
	private boolean booShowCashRoundingPanel = false;
	private boolean booRenderModifiedRoundData = false;

	// private boolean booAuthozed = false;
	private BigDecimal totalbalanceAmount = new BigDecimal(0);
	private BigDecimal totalrefundAmount = new BigDecimal(0);

	// private boolean boorefundPaymentDetails = false;
	// private boolean paymentDeatailsPanel = false;

	public BigDecimal getColremittanceNo() {
		return colremittanceNo;
	}

	public BigDecimal getTotalbalanceAmount() {
		return totalbalanceAmount;
	}

	public void setTotalbalanceAmount(BigDecimal totalbalanceAmount) {
		this.totalbalanceAmount = totalbalanceAmount;
	}

	public BigDecimal getTotalrefundAmount() {
		return totalrefundAmount;
	}

	public void setTotalrefundAmount(BigDecimal totalrefundAmount) {
		this.totalrefundAmount = totalrefundAmount;
	}

	public void setColremittanceNo(BigDecimal colremittanceNo) {
		this.colremittanceNo = colremittanceNo;
	}

	public String getColpaymentmodeCode() {
		return colpaymentmodeCode;
	}

	public void setColpaymentmodeCode(String colpaymentmodeCode) {
		this.colpaymentmodeCode = colpaymentmodeCode;
	}

	public String getColApprovalNo() {
		return colApprovalNo;
	}

	public void setColApprovalNo(String colApprovalNo) {
		this.colApprovalNo = colApprovalNo;
	}

	public BigDecimal getColCardNoLength() {
		return colCardNoLength;
	}

	public void setColCardNoLength(BigDecimal colCardNoLength) {
		this.colCardNoLength = colCardNoLength;
	}

	public String getColBankCode() {
		return colBankCode;
	}

	public void setColBankCode(String colBankCode) {
		this.colBankCode = colBankCode;
	}

	public boolean isBooRenderSingleDebit() {
		return booRenderSingleDebit;
	}

	public void setBooRenderSingleDebit(boolean booRenderSingleDebit) {
		this.booRenderSingleDebit = booRenderSingleDebit;
	}

	public boolean isBooRenderMulDebit() {
		return booRenderMulDebit;
	}

	public void setBooRenderMulDebit(boolean booRenderMulDebit) {
		this.booRenderMulDebit = booRenderMulDebit;
	}

	public boolean isBooShowCashRoundingPanel() {
		return booShowCashRoundingPanel;
	}

	public void setBooShowCashRoundingPanel(boolean booShowCashRoundingPanel) {
		this.booShowCashRoundingPanel = booShowCashRoundingPanel;
	}

	public boolean isBooRenderModifiedRoundData() {
		return booRenderModifiedRoundData;
	}

	public void setBooRenderModifiedRoundData(boolean booRenderModifiedRoundData) {
		this.booRenderModifiedRoundData = booRenderModifiedRoundData;
	}

	private String excheckCashLimitMessage = null;

	public String getExcheckCashLimitMessage() {
		return excheckCashLimitMessage;
	}

	public void setExcheckCashLimitMessage(String excheckCashLimitMessage) {
		this.excheckCashLimitMessage = excheckCashLimitMessage;
	}

	private List<ViewBankDetails> localbankListForBankCode = new ArrayList<ViewBankDetails>();
	private List<CustomerBank> lstDebitCard = new ArrayList<CustomerBank>();

	public List<ViewBankDetails> getLocalbankListForBankCode() {
		return localbankListForBankCode;
	}

	public void setLocalbankListForBankCode(
			List<ViewBankDetails> localbankListForBankCode) {
		this.localbankListForBankCode = localbankListForBankCode;
	}

	public List<CustomerBank> getLstDebitCard() {
		return lstDebitCard;
	}

	public void setLstDebitCard(List<CustomerBank> lstDebitCard) {
		this.lstDebitCard = lstDebitCard;
	}

	public void verifyPassword() {
		String errorMessage;

		try {
			if (getColCash() != null) {
				if (coldatatablevalues != null
						&& coldatatablevalues.size() != 0) {
					setColpaymentmodeId(null);
					setColApprovalNo(null);
					setBooRenderColDebitCard(false);
					setBooRenderColCheque(false);
					setBooRenderBankTransfer(false);
					setErrorMessage("Cash or Bank Transfer either one allowed");
					RequestContext.getCurrentInstance().execute("csp.show();");
					return;
				} else {
					errorMessage = iPersonalRemittanceService
							.getExCheckRefundCashLimitProcedure(
									sessionStateManage.getCountryId(),
									getCustomerId(), getColpaymentmodeId(),
									getColCash());
					System.out.println("errorMessage :" + errorMessage);
					if (errorMessage != null && !errorMessage.equals("")) {
						setExcheckCashLimitMessage(errorMessage);
						RequestContext.getCurrentInstance().execute(
								"exCheckCashLimit.show();");
					} else {
						setExcheckCashLimitMessage(null);
						if (getColpaymentmodeCode() != null) {
							List<PaymentMode> paymentModedetails = ipaymentService
									.getPaymentCheck(getColpaymentmodeCode());
							if (paymentModedetails.size() != 0) {
								if (getColpaymentmodeCode().equalsIgnoreCase(
										Constants.CashCode)) {
									calculatingNetAmountDT();
								} else if (getColpaymentmodeCode()
										.equalsIgnoreCase(Constants.KNETCode)) {
									setColpaymentmodeId(null);
									setBooRenderColDebitCard(false);
									setBooRenderColCheque(false);
									System.out
											.println("Payment Mode Newly added");
									RequestContext
											.getCurrentInstance()
											.execute(
													"checkPaymentModeService.show();");
								} else if (getColpaymentmodeCode()
										.equalsIgnoreCase(Constants.ChequeCode)) {
									Boolean checkdata = checkingChequeDuplicateCheck();
									if (checkdata) {
										localbankListForBankCode = icustomerBankService
												.getCustomerLocalBankListFromView(
														sessionStateManage
																.getCountryId(),
														getColchequebankCode());
										addPaymentModerecord();
										if (coldatatablevalues.size() > 0) {
											for (PersonalRemittanceCollectionDataTable collectionlst : coldatatablevalues) {
												totalUamount = totalUamount
														.add(collectionlst
																.getColAmountDT());
											}
											setToalUsedAmount(totalUamount);
											if (getToalUsedAmount().compareTo(
													getCalNetAmountPaid()) > 0) {
												setTotalbalanceAmount(BigDecimal.ZERO);
												setTotalrefundAmount(getToalUsedAmount()
														.subtract(
																getCalNetAmountPaid()));
											} else if (getToalUsedAmount()
													.compareTo(
															getCalNetAmountPaid()) < 0) {
												setTotalbalanceAmount(getCalNetAmountPaid()
														.subtract(
																getToalUsedAmount()));
												setTotalrefundAmount(BigDecimal.ZERO);
											} else {
												setTotalbalanceAmount(BigDecimal.ZERO);
												setTotalrefundAmount(BigDecimal.ZERO);
											}
										}
										setBooRendercollectiondatatable(true);
										clearingDetailAfterAdding();
									} else {
										RequestContext
												.getCurrentInstance()
												.execute(
														"chequerefexists.show();");
									}
								} else if (getColpaymentmodeCode()
										.equalsIgnoreCase(
												Constants.BankTransferCode)) {
									if (getBankTransferApprovalNo() != null) {
										System.out.println("BANK TRANSFER");
										localbankListForBankCode = icustomerBankService.getCustomerLocalBankListFromView(sessionStateManage.getCountryId(), getColBankTransferBankCode());
										
										addPaymentModerecord();
										if (coldatatablevalues.size() > 0) {
											for (PersonalRemittanceCollectionDataTable collectionlst : coldatatablevalues) {
												totalUamount = totalUamount
														.add(collectionlst
																.getColAmountDT());
											}
											setToalUsedAmount(totalUamount);
											if (getToalUsedAmount().compareTo(
													getCalNetAmountPaid()) > 0) {
												setTotalbalanceAmount(BigDecimal.ZERO);
												setTotalrefundAmount(getToalUsedAmount()
														.subtract(
																getCalNetAmountPaid()));
											} else if (getToalUsedAmount()
													.compareTo(
															getCalNetAmountPaid()) < 0) {
												setTotalbalanceAmount(getCalNetAmountPaid()
														.subtract(
																getToalUsedAmount()));
												setTotalrefundAmount(BigDecimal.ZERO);
											} else {
												setTotalbalanceAmount(BigDecimal.ZERO);
												setTotalrefundAmount(BigDecimal.ZERO);
											}
										}
										setBooRendercollectiondatatable(true);
										clearingDetailAfterAdding();
									} else {
										setErrorMessage("Please Enter Approval Number");
										RequestContext.getCurrentInstance()
												.execute("csp.show();");
										return;
									}

								} else {
									System.out.println("Other Payment Mode");
								}
							}
						} else {
							setBooRenderColDebitCard(false);
							setBooRenderColCheque(false);
							setBooRendercollectiondatatable(true);
							setTotalUamount(null);
							calculatingNetAmountDT();
						}
					}
				}

			} else {
				setErrorMessage("Please Enter Amount");
				RequestContext.getCurrentInstance().execute("csp.show();");
				return;
			}
		} catch (Exception e) {
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("sqlexception.show();");
			return;
		}
	}

	private BigDecimal populatedDebitCardNumber;

	public BigDecimal getPopulatedDebitCardNumber() {
		return populatedDebitCardNumber;
	}

	public void setPopulatedDebitCardNumber(BigDecimal populatedDebitCardNumber) {
		this.populatedDebitCardNumber = populatedDebitCardNumber;
	}

	public void checkingPaymentCardinDB() {

		if (getPopulatedDebitCardNumber() != null) {
			if (getPopulatedDebitCardNumber().compareTo(getColCardNo()) == 0) {
				calculatingNetAmountDT();
			} else {
				RequestContext.getCurrentInstance().execute(
						"addDebitCard.show();");
			}
		} else {
			RequestContext.getCurrentInstance().execute("addDebitCard.show();");
		}

	}

	// to render panel 7 - payment details denomination
	public void nextpaneltoPaymentDetails() {

		log.info("Entering into nextpaneltoPaymentDetails method");
		// if (getCalNetAmountPaid() != null && getColamountKWD() != null &&
		// getCalNetAmountPaid().compareTo(getColamountKWD()) <= 0) {
		if (getCalNetAmountPaid().compareTo(getCalNetAmountPaid()) == 0) {
			// setBooRenderPaymentDetails(true);
			setBooRenderCollection(false);
			// setCashAmount(getTempCash());
			// to render the denomination in payment details panel -7
			renderingDenominationDT();
			// denaMinLstData();
			// if payment mode cash only refund required
			if (getBoorefundPaymentDetails()) {
				refundDenominationData();
				setColCash(getToalUsedAmount());
				// setBoorefundPaymentDetails(true);
			} else {
				// setBoorefundPaymentDetails(false);
				setColCash(getToalUsedAmount());
				setPayPaidAmount(getToalUsedAmount());
				setPayRefund(BigDecimal.ZERO);
			}

			setBooRenderPaymentDetails(true);
			setBooRendercashdenomination(false);
			setNextOrSaveButton(Constants.Save);
		} else {
			RequestContext.getCurrentInstance().execute("amountmatch.show();");
		}
		log.info("Exit into nextpaneltoPaymentDetails method");

	}

	// to render denomination based on datatable added

	public void renderingDenominationDT() {
		if (coldatatablevalues.size() != 0) {
			BigDecimal paymentModeCashId = ipaymentService
					.fetchPaymodeMasterId(
							Constants.CASHNAME,
							new BigDecimal(
									sessionStateManage.isExists("languageId") ? sessionStateManage
											.getSessionValue("languageId")
											: "1"));

			if (paymentModeCashId != null) {
				for (PersonalRemittanceCollectionDataTable collectionlst : coldatatablevalues) {
					if (collectionlst.getColpaymentmodeIDtypeDT().compareTo(
							paymentModeCashId) == 0) {
						setDenomtotalCashreceived(getColCash());
						// setBooRendercashdenomination(true);
						setBoorefundPaymentDetails(true);
						break;
					} else {
						setDenomtotalCashreceived(getColCash());
						// setBooRendercashdenomination(false);
						setBoorefundPaymentDetails(false);
					}
				}
			}
		}
	}

	public void backFromDenominationPanel() {

		hideAllPanels();
		setBooRenderCollection(true);
		setBoRenderTotalAmountCalPanel(true);
		setBooRendercollectiondatatable(true);

	}

	public void nextpaneltoCollection() {

		hideAllPanels();

		setBooRenderCollection(true);
		setBooRendercollectiondatatable(false);
		setBoRenderTotalAmountCalPanel(false);
		// setColCash(null);
		setTotalUamount(null);

	}

	public void hideAllPanels() {
		setBooRenderTransferModeDetails(false);
		setBooRenderPaymentModeDetails(false);
		setBooRenderButton(false);
		setBooRendercollectiondatatable(false);
		setBooRenderColDebitCard(false);
		// setBooRenderPaymentDetails(true);
		// setBooRenderPaymentModeDetails(true);
		setBooRenderCollection(false);
		setPaymentDeatailsPanel(false);
		setBooRendercashdenomination(false);
		setBoRenderTotalAmountCalPanel(false);
		setBooRendercollectiondatatable(false);
		// setSuccessPanel(false);
		// setBooRendercollectiondatatable(false);

	}

	List<CustomerBank> localBankListinCollection = new ArrayList<CustomerBank>();

	public void setLocalBankListinCollection(
			List<CustomerBank> localBankListinCollection) {
		this.localBankListinCollection = localBankListinCollection;
	}

	public void populateCustomerDetails() {
		localBankListinCollection.clear();
		setColAuthorizedby(null);
		setPopulatedDebitCardNumber(null);
		setColCardNo(null);
		setColNameofCard(null);
		// setColpassword(null);

		if (getColBankid() != null) {

			// if (getColBankCode() != null) {

			localBankListinCollection = icustomerBankService.customerBanks(
					getCustomerId(), getColBankid());

			// localbankListForBankCode =
			// icustomerBankService.getCustomerLocalBankListFromView(sessionStateManage.getCountryId(),
			// getColBankCode());

			for (CustomerBank customerBank : localBankListinCollection) {
				if (customerBank.getFsBankMaster().getBankId()
						.equals(getColBankid())) {
					setPopulatedDebitCardNumber(new BigDecimal(
							encryptionDescriptionService.getDECrypted(
									customerBank.getDebitCardName(),
									customerBank.getDebitCard())));
					setColCardNo(new BigDecimal(
							encryptionDescriptionService.getDECrypted(
									customerBank.getDebitCardName(),
									customerBank.getDebitCard())));
					setColNameofCard(customerBank.getDebitCardName());
					if (customerBank.getAuthorizedBy() != null) {
						List<DebitAutendicationView> localEmpllist = iPersonalRemittanceService
								.getdebitAutendicationList();
						setEmpllist(localEmpllist);
						setColAuthorizedby(customerBank.getAuthorizedBy());
						setColpassword(null);
						setBooAuthozed(true);
						break;
					} else {
						setColAuthorizedby(null);
						setColpassword(null);
						setBooAuthozed(false);
					}
				} else {
					setColAuthorizedby(null);
					setColpassword(null);
					setBooAuthozed(false);
				}
			}
		} else {
			setColAuthorizedby(null);
			setColCardNo(null);
			setPopulatedDebitCardNumber(null);
			setColNameofCard(null);
			setColpassword(null);
			setBooAuthozed(false);
		}

	}

	public boolean saveForeignCurrencyAdjustValidation() {
		log.info("Entering into saveForeignCurrencyAdjust method");
		boolean notesEmpty = false;
		log.info("******************size of the currency" + lstData.size());
		for (ForeignLocalCurrencyDataTable foreignLocalCurrencyDataTable : lstData) {
			if (foreignLocalCurrencyDataTable.getQty() != null
					&& !foreignLocalCurrencyDataTable.getQty().equals("0")) {
				notesEmpty = true;
				break;
			}
		}
		log.info("Exit into saveForeignCurrencyAdjust method");
		return notesEmpty;
	}

	private String nextOrSaveButton;

	public boolean getBoorefundPaymentDetails() {
		return boorefundPaymentDetails;
	}

	public void setBoorefundPaymentDetails(Boolean boorefundPaymentDetails) {
		this.boorefundPaymentDetails = boorefundPaymentDetails;
	}

	public String getNextOrSaveButton() {
		return nextOrSaveButton;
	}

	public void setNextOrSaveButton(String nextOrSaveButton) {
		this.nextOrSaveButton = nextOrSaveButton;
	}

	public void nextpaneltoRefundDenomination() {
		// first panel
		// second panel
		// third panel
		// fourth panel
		// fifth panel
		// sixth panel
		// seventh panel
		// eight panel
		setBooRenderCollection(false);
		setBooRenderColDebitCard(false);
		setBooRendercollectiondatatable(false);
		// ninth panel
		setBooRenderPaymentDetails(true);
		setBooRendercashdenomination(false);
		setBoorefundPaymentDetails(true);
		setPaymentDeatailsPanel(true);
		// final report panel
		setPaymentDetailsremark(null);

	}

	private ArrayList<ForeignLocalCurrencyDataTable> lstRefundData = new ArrayList<ForeignLocalCurrencyDataTable>();
	private BigDecimal collectedRefundAmount;

	public BigDecimal getCollectedRefundAmount() {
		return collectedRefundAmount;
	}

	public void setCollectedRefundAmount(BigDecimal collectedRefundAmount) {
		this.collectedRefundAmount = collectedRefundAmount;
	}

	public ArrayList<ForeignLocalCurrencyDataTable> getLstRefundData() {
		return lstRefundData;
	}

	public void setLstRefundData(
			ArrayList<ForeignLocalCurrencyDataTable> lstRefundData) {
		this.lstRefundData = lstRefundData;
	}

	private void refundDenominationData() {
		lstRefundData.clear();
		ArrayList<ForeignLocalCurrencyDataTable> localLstData = new ArrayList<ForeignLocalCurrencyDataTable>();
		/*
		 * Checking that it's first time or not, first time list size will be 0
		 */
		// double sAmount = 0;
		localLstData.clear();
		if (localLstData.size() == 0) {
			List<ViewStock> stockList = iPersonalRemittanceService
					.toCheckStockForView(sessionStateManage.getCountryId(),
							sessionStateManage.getUserName(),
							sessionStateManage.getBranchId(),
							sessionStateManage.getCompanyId(),
							sessionStateManage.getCurrencyId());
			int serial = 1;
			for (ViewStock viewStockObj : stockList) {
				ForeignLocalCurrencyDataTable forLocalCurrencyDtObj = new ForeignLocalCurrencyDataTable();
				forLocalCurrencyDtObj.setSerial(serial);
				forLocalCurrencyDtObj.setItem(viewStockObj
						.getDenominationAmount());
				forLocalCurrencyDtObj.setQty("");
				forLocalCurrencyDtObj.setPrice("");
				if (viewStockObj.getCurrentStock() != null) {
					forLocalCurrencyDtObj.setStock(viewStockObj
							.getCurrentStock().intValue());
				} else {
					forLocalCurrencyDtObj.setStock(0);
				}
				forLocalCurrencyDtObj.setDenominationId(viewStockObj
						.getDenominationId());
				forLocalCurrencyDtObj.setCurrencyId(viewStockObj
						.getCurrencyId());
				forLocalCurrencyDtObj.setDenominationDesc(viewStockObj
						.getDenominationDEsc());
				forLocalCurrencyDtObj.setDenominationAmount(viewStockObj
						.getDenominationAmount());
				localLstData.add(forLocalCurrencyDtObj);
				serial++;
			}
		}
		/* Responsible to keep sum of total amount of cash entered */
		int totalSum = 0;
		/* Responsible to calculate sum of entered cash amount */
		for (ForeignLocalCurrencyDataTable element : localLstData) {
			if (element.getPrice().length() != 0) {
				totalSum = totalSum + Integer.parseInt(element.getPrice());
			}
		}
		System.out.println(totalSum);
		setCollectedRefundAmount(BigDecimal.ZERO);
		setLstRefundData(localLstData);
		setTotalUamount(toalUsedAmount);

		// setPayNetPaidAmount(getCalNetAmountPaid());
		setPayRefund(BigDecimal.ZERO);
		setPayPaidAmount(BigDecimal.ZERO);
		setRefundAmount(getPayRefund());
		if (getPayRefund().compareTo(BigDecimal.ZERO) == 0) {
			setNextOrSaveButton(Constants.Save);
		} else {
			setNextOrSaveButton(Constants.Next);
		}

		if (localLstData.size() != 0) {
			setDenominationchecking(Constants.DenominationAvailable);
		} else {
			setDenominationchecking(Constants.DenominationNotAvailable);
		}
	}

	private String denominationchecking;

	private BigDecimal refundAmount;

	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getDenominationchecking() {
		return denominationchecking;
	}

	public void setDenominationchecking(String denominationchecking) {
		this.denominationchecking = denominationchecking;
	}

	public void onRefundCellEdit(
			ForeignLocalCurrencyDataTable foreignLocalCurrencyDataTable) {
		int quantity = 0;
		if (foreignLocalCurrencyDataTable.getQty() == ""
				&& foreignLocalCurrencyDataTable.getQty() != null) {
			quantity = 0;
		} else {
			try {
				quantity = Integer.parseInt(foreignLocalCurrencyDataTable
						.getQty());
			} catch (Exception e) {
				System.out.println("Exception occured" + e);
				quantity = 0;
			}
		}
		if (foreignLocalCurrencyDataTable.getStock() >= quantity
				&& quantity != 0) {
			BigDecimal totalcashAmount = foreignLocalCurrencyDataTable
					.getItem().multiply(
							new BigDecimal(foreignLocalCurrencyDataTable
									.getQty() == "" ? "0"
									: foreignLocalCurrencyDataTable.getQty()));
			if (foreignLocalCurrencyDataTable.getQty().equals("")) {
				foreignLocalCurrencyDataTable.setQty("");
				// added by rabil for clear if blank
				foreignLocalCurrencyDataTable.setPrice("");
			}
			if (totalcashAmount != null && totalcashAmount.doubleValue() != 0) {
				foreignLocalCurrencyDataTable.setPrice(GetRound
						.roundBigDecimal(
								totalcashAmount,
								foreignLocalCurrencyDenominationService
										.getDecimalPerCurrency(new BigDecimal(
												sessionStateManage
														.getCurrencyId())))
						.toPlainString());
			} else {
				foreignLocalCurrencyDataTable.setPrice("");
			}

		} else {
			foreignLocalCurrencyDataTable.setQty("");
			foreignLocalCurrencyDataTable.setPrice("");
		}

		BigDecimal totalSum = BigDecimal.ZERO;
		/* Responsible to calculate sum of entered cash amount */
		for (ForeignLocalCurrencyDataTable element : lstRefundData) {
			if (element.getPrice().length() != 0) {
				totalSum = totalSum.add(new BigDecimal(element.getPrice()));
			}
		}
		setTotalUamount(getTotalUamount());
		if (getTotalUamount() != null) {
			setPayRefund(totalSum.subtract(getTotalUamount()));
			setPayPaidAmount(totalSum);
			setColCash(getTotalUamount());
			setCollectedRefundAmount(totalSum);
			if (getPayRefund().compareTo(BigDecimal.ZERO) <= 0) {
				setNextOrSaveButton(Constants.Save);
			} else {
				setNextOrSaveButton(Constants.Next);
			}
		}
	}

	public void saveCheck() {

		if (getBooRendercashdenomination()) {
			if (lstData.size() != 0) {
				if (getCashAmount() != null && getDenomtotalCash() != null
						&& getCashAmount().compareTo(getDenomtotalCash()) == 0) {
					saveAllForUpdate();
				} else {
					RequestContext.getCurrentInstance().execute(
							"netamountgreater.show();");
				}
			} else {
				RequestContext.getCurrentInstance().execute(
						"denominationerror.show();");
			}
		} else if (getBoorefundPaymentDetails()) {

			try {
				if (lstRefundData.size() != 0) {
					if (getTotalUamount() != null
							&& getCollectedRefundAmount() != null
							&& getTotalUamount().compareTo(
									getCollectedRefundAmount()) <= 0) {
						if (getPayRefund() != null
								&& getPayRefund().compareTo(BigDecimal.ZERO) > 0) {
							nextpaneltoRefundDenomination();
							denaMinLstData();
							setPaymentDeatailsPanel(true);
							setBoorefundPaymentDetails(false);
							// setBooRenderPaymentDetails(true);
							setBooRendercashdenomination(true);
							setNextOrSaveButton(Constants.Save);
						} else {
							saveAllForUpdate();
						}
					} else {
						RequestContext.getCurrentInstance().execute(
								"refundamounterror.show();");
					}
				} else {
					RequestContext.getCurrentInstance().execute(
							"denominationerror.show();");
				}
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		} else {
			if (getPayRefund().compareTo(BigDecimal.ZERO) > 0) {
				nextpaneltoRefundDenomination();
				setNextOrSaveButton(Constants.Save);
			} else {
				saveAllForUpdate();
			}
		}
	}

	public void saveAll() {

		log.info("Entering into saveAll method");
		boolean noteEmpty = false;
		log.info("****getPayRefund****" + getPayRefund());
		// card and cash both payment save
		if (coldatatablevalues.size() == 2) {
			// getDocumentSerialID("U");
			log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@ CASH and Card both  @@@@@@@@@@@@@@@@@@@@@@@@@@@");
			// log.info("CASH and Card both are choosed here");
			noteEmpty = saveForeignCurrencyAdjustValidation();
			if (noteEmpty) {
				if (getPayRefund().signum() == -1) {
					RequestContext.getCurrentInstance().execute("totalcashCheck.show();");
				} else {
					HashMap<String, Object> documentSerial = new HashMap<String, Object>();
					documentSerial.put("countryId", sessionStateManage.getCountryId().intValue());
					documentSerial.put("companyId", sessionStateManage.getCompanyId().intValue());
					documentSerial.put("documentCode", new BigDecimal(Constants.DOCUMENT_CODE_FOR_REFUND_REQUEST).intValue());
					documentSerial.put("dealYear", getFinanceYear());
					documentSerial.put("processIn", Constants.U);
					documentSerial.put("remittanceTransactionId", getTransactionId());
					documentSerial.put("status", Constants.P);
					documentSerial.put("userName", sessionStateManage.getUserName());
					documentSerial.put("transferNo", getTransferNo());
					documentSerial.put("receiptNo", getReceiptNo());
					documentSerial.put("receiptPK", getReceiptPaymentPk());
					HashMap<String, Object> mapAllDetailForSave = new HashMap<String, Object>();
					mapAllDetailForSave.put("DocuemntSeriality", documentSerial);
					mapAllDetailForSave.put("PaymentReceipt", concurrentsavepaymentReceipt());
					mapAllDetailForSave.put("ExchangeRate", getExchangeRate());
					mapAllDetailForSave.put("LocalTrnsAmount", getTransferAmount());
					Collect collect = concurrentSaveCollect();

					mapAllDetailForSave.put("Collection", collect);
					mapAllDetailForSave.put("CollectionDetails", concurrentsaveCollectionDetail(collect));
					mapAllDetailForSave.put("ForeignCurrencyAdjust", concurrentsaveForeignCurrencyAdjust(getExchangeRate().toPlainString()));
					mapAllDetailForSave.put("BranchCode", sessionStateManage.getCountryBranchCode());
					try {
						iStopPaymentCollectionService.saveAlltheDeatailsforCash(mapAllDetailForSave);
						RequestContext.getCurrentInstance().execute("save.show();");
					} catch (Exception e) {
						RequestContext.getCurrentInstance().execute("csp.show();");
						setErrorMessage(e.getMessage());
					}
					log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@ CASH and Card both  completed@@@@@@@@@@@@@@@@@@@@@@@@@@@");
				}
			} else {
				RequestContext.getCurrentInstance().execute("notesZero.show();");
			}
		} else {
			if (getColpaymentmodeId() != null && getColpaymentmodeName().equals(Constants.CASHNAME)) {
				// cash payment mode
				log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@ cash payment mode @@@@@@@@@@@@@@@@@@@@@@@@@@@");
				noteEmpty = saveForeignCurrencyAdjustValidation();
				if (noteEmpty) {
					if (getPayRefund().signum() == -1) {
						RequestContext.getCurrentInstance().execute(
								"totalcashCheck.show();");
					} else {
						HashMap<String, Object> documentSerial = new HashMap<String, Object>();
						documentSerial.put("countryId", sessionStateManage.getCountryId().intValue());
						documentSerial.put("companyId", sessionStateManage.getCompanyId().intValue());
						documentSerial.put("documentCode", new BigDecimal(Constants.DOCUMENT_CODE_FOR_REFUND_REQUEST).intValue());
						documentSerial.put("processIn", Constants.U);
						documentSerial.put("remittanceTransactionId",getTransactionId());
						documentSerial.put("status", Constants.P);
						documentSerial.put("userName",sessionStateManage.getUserName());
						documentSerial.put("transferNo", getTransferNo());
						documentSerial.put("receiptNo", getReceiptNo());
						documentSerial.put("receiptPK", getReceiptPaymentPk());
						HashMap<String, Object> mapAllDetailForSave = new HashMap<String, Object>();
						mapAllDetailForSave.put("DocuemntSeriality", documentSerial);
						mapAllDetailForSave.put("PaymentReceipt", concurrentsavepaymentReceipt());
						mapAllDetailForSave.put("ExchangeRate", getExchangeRate());
						mapAllDetailForSave.put("LocalTrnsAmount", getTransferAmount());
						Collect collect = concurrentSaveCollect();
						mapAllDetailForSave.put("Collection", collect);
						mapAllDetailForSave.put("CollectionDetails", concurrentsaveCollectionDetail(collect));
						mapAllDetailForSave.put("ForeignCurrencyAdjust", concurrentsaveForeignCurrencyAdjust(getExchangeRate().toPlainString()));
						mapAllDetailForSave.put("BranchCode", sessionStateManage.getCountryBranchCode());
						try {
							iStopPaymentCollectionService.saveAlltheDeatailsforCash(mapAllDetailForSave);
							RequestContext.getCurrentInstance().execute("save.show();");
						} catch (Exception e) {
							RequestContext.getCurrentInstance().execute("csp.show();");
							setErrorMessage(e.getMessage());
						}
						log.info("Exit into saveAll method");
						log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@ cash payment mode completed@@@@@@@@@@@@@@@@@@@@@@@@@@@");
					}
				} else {
					RequestContext.getCurrentInstance().execute("notesZero.show();");
				}
			} else {
				// card payment mode
				log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@  card payment mode @@@@@@@@@@@@@@@@@@@@@@@@@@@");
				HashMap<String, Object> mapAllDetailForSave = new HashMap<String, Object>();
				HashMap<String, Object> documentSerial = new HashMap<String, Object>();
				documentSerial.put("countryId", sessionStateManage.getCountryId().intValue());
				documentSerial.put("companyId", sessionStateManage.getCompanyId().intValue());
				documentSerial.put("documentCode", new BigDecimal(Constants.DOCUMENT_CODE_FOR_REFUND_REQUEST).intValue());
				documentSerial.put("dealYear", getFinanceYear());
				documentSerial.put("processIn", Constants.U);
				documentSerial.put("remittanceTransactionId",getTransactionId());
				documentSerial.put("status", Constants.P);
				documentSerial.put("userName", sessionStateManage.getUserName());
				documentSerial.put("transferNo", getTransferNo());
				documentSerial.put("receiptNo", getReceiptNo());
				documentSerial.put("receiptPK", getReceiptPaymentPk());
				// getDocumentSerialID("U");
				// savepaymentReceipt();
				mapAllDetailForSave.put("DocuemntSeriality", documentSerial);
				mapAllDetailForSave.put("PaymentReceipt",concurrentsavepaymentReceipt());
				Collect collect = concurrentSaveCollect();
				mapAllDetailForSave.put("Collection", collect);
				mapAllDetailForSave.put("CollectionDetails", concurrentsaveCollectionDetail(collect));
				mapAllDetailForSave.put("BranchCode", sessionStateManage.getCountryBranchCode());
				try {
					iStopPaymentCollectionService.saveAlltheDeatailsforCard(mapAllDetailForSave);
					RequestContext.getCurrentInstance().execute("save.show();");
				} catch (Exception e) {
					// RequestContext.getCurrentInstance().execute("csp.show();");
					setErrorMessage(e.getMessage());
				}
				log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@  card payment mode completed @@@@@@@@@@@@@@@@@@@@@@@@@@@");
			}
		}
		firstTime = false;
		log.info("Exit into saveAll method");

	}

	public List<ForeignCurrencyAdjust> concurrentsaveForeignCurrencyAdjust(
			String exchangeRate) {
		log.info("Entering into saveForeignCurrencyAdjust method");
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
		// String date = "01/09/14" ;Collect collectid , BigDecimal exchangerate
		// , BigDecimal localnettrastamount
		List<ForeignCurrencyAdjust> foreignCurrencyAdjustlst = new ArrayList<ForeignCurrencyAdjust>();
		Date acc_Month = null;
		try {
			acc_Month = DATE_FORMAT.parse(getCurrentDateWithFormat());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		BigDecimal companyCodeValue = null;
		List<CompanyMasterDesc> companyCode = iGeneralService.getCompanyList(
				sessionStateManage.getCompanyId(),
				sessionStateManage.getLanguageId());
		if (companyCode != null && !companyCode.isEmpty()
				&& companyCode.get(0).getFsCompanyMaster() != null) {
			companyCodeValue = companyCode.get(0).getFsCompanyMaster()
					.getCompanyCode();
		}
		BigDecimal documentPk = null;
		documentPk = iStopPaymentCollectionService
				.toFetchDocumentPk(new BigDecimal(
						Constants.DOCUMENT_CODE_FOR_REFUND_REQUEST));

		int i = 0;
		log.info("******************size of the currency" + lstData.size());
		if (lstData != null && lstData.size() > 0) {
			for (ForeignLocalCurrencyDataTable foreignLocalCurrencyDataTable : lstData) {
				System.out.println(foreignLocalCurrencyDataTable);
				if (foreignLocalCurrencyDataTable.getQty() != null
						&& !foreignLocalCurrencyDataTable.getQty().equals("")
						&& !foreignLocalCurrencyDataTable.getQty().equals("0")) {
					ForeignCurrencyAdjust foreignCurrencyAdjust = new ForeignCurrencyAdjust();
					// Company save
					CompanyMaster companyMaster = new CompanyMaster();
					companyMaster.setCompanyId(sessionStateManage
							.getCompanyId());
					foreignCurrencyAdjust.setFsCompanyMaster(companyMaster);
					// Country Save
					CountryMaster countryMaster = new CountryMaster();
					countryMaster.setCountryId(sessionStateManage
							.getCountryId());
					foreignCurrencyAdjust.setFsCountryMaster(countryMaster);
					// customer Save
					Customer customer = new Customer();
					customer.setCustomerId(getCustomerId());
					foreignCurrencyAdjust.setFsCustomer(customer);
					foreignCurrencyAdjust.setDocumentDate(new Date());
					// currency Id
					CurrencyMaster currencyMaster = new CurrencyMaster();
					currencyMaster.setCurrencyId(new BigDecimal(
							sessionStateManage.getCurrencyId()));
					foreignCurrencyAdjust.setFsCurrencyMaster(currencyMaster);
					foreignCurrencyAdjust.setNotesQuantity(new BigDecimal(
							foreignLocalCurrencyDataTable.getQty()));
					foreignCurrencyAdjust.setAdjustmentAmount(new BigDecimal(
							foreignLocalCurrencyDataTable.getPrice()));
					CurrencyWiseDenomination denominationMaster = new CurrencyWiseDenomination();
					denominationMaster
							.setDenominationId(foreignLocalCurrencyDataTable
									.getDenominationId());
					foreignCurrencyAdjust
							.setFsDenominationId(denominationMaster);
					foreignCurrencyAdjust.setExchangeRate(new BigDecimal(
							exchangeRate));
					foreignCurrencyAdjust.setDenaminationAmount(new BigDecimal(
							foreignLocalCurrencyDataTable.getPrice()));
					// foreignCurrencyAdjust.setDocumentFinanceYear(new
					// BigDecimal(getFinaceYear()));
					foreignCurrencyAdjust.setOracleUser(sessionStateManage
							.getUserName());
					foreignCurrencyAdjust.setDocumentCode(new BigDecimal(
							Constants.DOCUMENT_CODE_FOR_REFUND_REQUEST)); // wait
					foreignCurrencyAdjust.setDocumentLineNumber(new BigDecimal(
							++i));
					foreignCurrencyAdjust.setAccountmmyyyy(acc_Month);
					CountryBranch countryBranch = new CountryBranch();
					countryBranch.setCountryBranchId(new BigDecimal(
							sessionStateManage.getBranchId()));
					foreignCurrencyAdjust.setCountryBranch(countryBranch);
					// foreignCurrencyAdjust.setProgNumber(Constants.STOPR_PAYMENT_PROG);
					foreignCurrencyAdjust.setDocumentStatus(Constants.P);
					foreignCurrencyAdjust.setTransactionType(Constants.C);
					foreignCurrencyAdjust.setCreatedDate(new Date());
					foreignCurrencyAdjust.setCreatedBy(sessionStateManage
							.getUserName());
					foreignCurrencyAdjust.setDocumentNo(getDocumentNum());
					foreignCurrencyAdjust
							.setDocumentFinanceYear(getDocFinYear());

					if (documentPk != null) {
						foreignCurrencyAdjust.setDocumentId(documentPk);
					}

					if (companyCodeValue != null) {
						foreignCurrencyAdjust.setCompanyCode(companyCodeValue);
					}

					foreignCurrencyAdjustlst.add(foreignCurrencyAdjust);
				} else {
					log.info("Number of notes is 0");
				}
			}
		}

		if (lstRefundData != null && lstRefundData.size() != 0) {
			log.info("******************size of the currency"
					+ lstRefundData.size());
			for (ForeignLocalCurrencyDataTable foreignLocalCurrencyDataTable : lstRefundData) {
				System.out.println(foreignLocalCurrencyDataTable);
				if (foreignLocalCurrencyDataTable.getQty() != null
						&& !foreignLocalCurrencyDataTable.getQty().equals("")
						&& !foreignLocalCurrencyDataTable.getQty().equals("0")) {
					ForeignCurrencyAdjust foreignCurrencyAdjust = new ForeignCurrencyAdjust();
					// Company save
					CompanyMaster companyMaster = new CompanyMaster();
					companyMaster.setCompanyId(sessionStateManage
							.getCompanyId());
					foreignCurrencyAdjust.setFsCompanyMaster(companyMaster);
					// Country Save
					CountryMaster countryMaster = new CountryMaster();
					countryMaster.setCountryId(sessionStateManage
							.getCountryId());
					foreignCurrencyAdjust.setFsCountryMaster(countryMaster);
					// customer Save
					Customer customer = new Customer();
					customer.setCustomerId(getCustomerId());
					foreignCurrencyAdjust.setFsCustomer(customer);
					foreignCurrencyAdjust.setDocumentDate(new Date());
					// currency Id
					CurrencyMaster currencyMaster = new CurrencyMaster();
					currencyMaster.setCurrencyId(new BigDecimal(
							sessionStateManage.getCurrencyId()));
					foreignCurrencyAdjust.setFsCurrencyMaster(currencyMaster);
					foreignCurrencyAdjust.setNotesQuantity(new BigDecimal(
							foreignLocalCurrencyDataTable.getQty()));
					foreignCurrencyAdjust.setAdjustmentAmount(new BigDecimal(
							foreignLocalCurrencyDataTable.getPrice()));
					CurrencyWiseDenomination denominationMaster = new CurrencyWiseDenomination();
					denominationMaster
							.setDenominationId(foreignLocalCurrencyDataTable
									.getDenominationId());
					foreignCurrencyAdjust
							.setFsDenominationId(denominationMaster);
					foreignCurrencyAdjust.setExchangeRate(new BigDecimal(
							exchangeRate));
					foreignCurrencyAdjust.setDenaminationAmount(new BigDecimal(
							foreignLocalCurrencyDataTable.getPrice()));
					// foreignCurrencyAdjust.setDocumentFinanceYear(new
					// BigDecimal(getFinaceYear()));
					foreignCurrencyAdjust.setOracleUser(sessionStateManage
							.getUserName());
					foreignCurrencyAdjust.setDocumentCode(new BigDecimal(
							Constants.DOCUMENT_CODE_FOR_REFUND_REQUEST)); // wait
					foreignCurrencyAdjust.setDocumentLineNumber(new BigDecimal(
							++i));
					foreignCurrencyAdjust.setAccountmmyyyy(acc_Month);
					CountryBranch countryBranch = new CountryBranch();
					countryBranch.setCountryBranchId(new BigDecimal(
							sessionStateManage.getBranchId()));
					foreignCurrencyAdjust.setCountryBranch(countryBranch);
					// foreignCurrencyAdjust.setProgNumber(Constants.STOPR_PAYMENT_PROG);
					foreignCurrencyAdjust.setDocumentStatus(Constants.P);
					foreignCurrencyAdjust.setTransactionType(Constants.F);
					foreignCurrencyAdjust.setCreatedDate(new Date());
					foreignCurrencyAdjust.setCreatedBy(sessionStateManage
							.getUserName());
					foreignCurrencyAdjust.setDocumentNo(getDocumentNum());
					foreignCurrencyAdjust
							.setDocumentFinanceYear(getDocFinYear());
					if (companyCodeValue != null) {
						foreignCurrencyAdjust.setCompanyCode(companyCodeValue);
					}

					if (documentPk != null) {
						foreignCurrencyAdjust.setDocumentId(documentPk);
					}
					foreignCurrencyAdjustlst.add(foreignCurrencyAdjust);
				} else {
					log.info("Number of notes is 0");
				}
			}
		}
		return foreignCurrencyAdjustlst;
	}

	private ReceiptPayment receiptPayment;

	public ReceiptPayment concurrentsavepaymentReceipt() {
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
		Date acc_Month = null;
		try {
			acc_Month = DATE_FORMAT.parse(getCurrentDateWithFormat());
			log.info("Account Date :" + acc_Month);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		try {
			receiptPayment = new ReceiptPayment();
			receiptPayment.setReceiptId(getReceiptPaymentPk());
			CompanyMaster companyMaster = new CompanyMaster();
			companyMaster.setCompanyId(sessionStateManage.getCompanyId());
			receiptPayment.setFsCompanyMaster(companyMaster);
			// Country Save
			CountryMaster countryMaster = new CountryMaster();
			countryMaster.setCountryId(sessionStateManage.getCountryId());
			receiptPayment.setFsCountryMaster(countryMaster);
			// customer Save
			Customer customer = new Customer();
			customer.setCustomerId(getCustomerId());
			receiptPayment.setFsCustomer(customer);

			receiptPayment.setDocumentFinanceYear(new BigDecimal(
					getFinanceYear()));
			receiptPayment.setCustomerName(getCustomerName());
			CurrencyMaster localcurrencyMaster = new CurrencyMaster();
			localcurrencyMaster.setCurrencyId(getLocalCurrencyId());
			receiptPayment.setLocalFsCountryMaster(localcurrencyMaster);
			receiptPayment.setColDocCode(new BigDecimal(
					Constants.DOCUMENT_CODE_FOR_REFUND_REQUEST));
			receiptPayment.setColDocFyr(new BigDecimal(getFinanceYear()));
			receiptPayment.setForignTrnxAmount(new BigDecimal(
					getTransferAmount()));
			// receiptPayment.setLocalTrnxAmount(getPayNetPaidAmount()); - not
			// set
			receiptPayment.setTransactionActualRate(getExchangeRate());
			// receiptPayment.setLocalNetAmount(getPayNetPaidAmount());- not set
			receiptPayment.setDocumentStatus("P");
			receiptPayment.setGeneralLegerDate(new Date());
			receiptPayment.setIsActive(Constants.Yes);
			receiptPayment.setAccountMMYYYY(acc_Month);
			receiptPayment.setTransactionType(Constants.C);
			receiptPayment.setDocumentCode(new BigDecimal(
					Constants.DOCUMENT_CODE_FOR_REFUND_REQUEST));
			// receiptPayment.setDocumentNo(new BigDecimal(documentSerialId));

			CountryBranch countryBranch = new CountryBranch();
			countryBranch.setCountryBranchId(new BigDecimal(sessionStateManage
					.getBranchId()));
			receiptPayment.setCountryBranch(countryBranch);
			receiptPayment.setDocumentDate(new Date());
			CurrencyMaster fcurrencyMaster = new CurrencyMaster();
			fcurrencyMaster.setCurrencyId(getForeignCurrencyId());
			receiptPayment.setForeignFsCountryMaster(fcurrencyMaster);
			receiptPayment.setLocalTrnxAmount(getLocalCurrencyId());
			// purposeoftransaction
			PurposeOfTransaction purposeofTransaction = new PurposeOfTransaction();
			purposeofTransaction.setPurposeId(new BigDecimal(
					Constants.PURPUSE_ID_FOR_FC));
			receiptPayment.setPurposeOfTransaction(purposeofTransaction);
			SourceOfIncome sourceOfIncome = new SourceOfIncome();
			sourceOfIncome.setSourceId(new BigDecimal(
					Constants.SOURCE_ID_FOR_FC));
			receiptPayment.setSourceOfIncome(sourceOfIncome);
			receiptPayment.setRemarks(getRemarks());
			receiptPayment.setCreatedDate(getCreatedDate());
			receiptPayment.setCreatedBy(getCreatedBy());
			receiptPayment.setModifiedBy(sessionStateManage.getUserName());
			receiptPayment.setModifiedDate(new Date());
			// getForeignCurrencyPurchaseService().saveReceiptPayment(receiptPayment);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return receiptPayment;
	}

	public Collect concurrentSaveCollect() {
		log.info("Entering into saveCollect method");

		Collect collect = new Collect();
		// company Id
		CompanyMaster companymaster = new CompanyMaster();
		companymaster.setCompanyId(sessionStateManage.getCompanyId());
		collect.setFsCompanyMaster(companymaster);

		collect.setApplicationCountryId(sessionStateManage.getCountryId());

		CountryBranch cb = new CountryBranch();
		cb.setCountryBranchId(new BigDecimal(sessionStateManage.getBranchId()));
		collect.setExBankBranch(cb);

		// customer Id
		Customer customer = new Customer();
		customer.setCustomerId(getCustomerId());
		collect.setFsCustomer(customer);

		// document Id
		collect.setDocumentNo(getDocumentNum());
		collect.setDocumentCode(new BigDecimal(23));
		BigDecimal DocumentPk = null;
		DocumentPk = iStopPaymentCollectionService
				.toFetchDocumentPk(new BigDecimal(
						Constants.DOCUMENT_CODE_FOR_REFUND_REQUEST));
		if (DocumentPk != null) {
			collect.setDocumentId(DocumentPk);
		}

		// Document Financial Year
		collect.setDocumentFinanceYear(new BigDecimal(getFinanceYear()));
		collect.setCollectDate(new Date());

		// Foreign Currency id
		CurrencyMaster forcurrencymaster = new CurrencyMaster();
		forcurrencymaster.setCurrencyId(getLocalCurrencyId());
		collect.setExCurrencyMaster(forcurrencymaster);

		collect.setPaidAmount(getPayPaidAmount());
		collect.setRefoundAmount(getPayRefund());
		collect.setNetAmount(getPayPaidAmount().subtract(getPayRefund()));

		collect.setIsActive(Constants.Yes);

		try {
			collect.setAccountMMYYYY(new SimpleDateFormat("dd/MM/yyyy")
					.parse(getCurrentDateWithFormat()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		collect.setGeneralLegerDate(new Date());
		collect.setCreatedBy(sessionStateManage.getUserName());
		collect.setCreatedDate(new Date());

		log.info("Exit into saveCollect method");

		return collect;
	}

	public ArrayList<CollectDetail> concurrentsaveCollectionDetail(
			Collect collect) {
		int i = 1;
		ArrayList<CollectDetail> collectionDetailsList = new ArrayList<CollectDetail>();

		for (PersonalRemittanceCollectionDataTable lstofPayment : coldatatablevalues) {

			CollectDetail collectDetails = new CollectDetail();

			Collect collection = new Collect();
			collection.setCollectionId(collect.getCollectionId());
			collectDetails.setCashCollectionId(collection);

			// customer Id
			Customer customer = new Customer();
			customer.setCustomerId(collect.getFsCustomer().getCustomerId());
			collectDetails.setFsCustomer(customer);

			// Application Country
			CountryMaster appcountrymaster = new CountryMaster();
			appcountrymaster.setCountryId(sessionStateManage.getCountryId());
			collectDetails.setFsCountryMaster(appcountrymaster);

			// company Id
			CompanyMaster companymaster = new CompanyMaster();
			companymaster.setCompanyId(collect.getFsCompanyMaster()
					.getCompanyId());
			collectDetails.setFsCompanyMaster(companymaster);

			// document Id
			collectDetails.setDocumentNo(collect.getDocumentNo());
			collectDetails.setDocumentCode(collect.getDocumentCode());

			// Document Financial Year
			collectDetails.setDocumentFinanceYear(collect
					.getDocumentFinanceYear());
			BigDecimal documentPk = null;
			documentPk = iStopPaymentCollectionService
					.toFetchDocumentPk(new BigDecimal(
							Constants.DOCUMENT_CODE_FOR_REFUND_REQUEST));
			if (documentPk != null) {
				collectDetails.setDocumentId(documentPk);
			}

			// Routing Bank Branch
			CountryBranch bankbranch = new CountryBranch();
			bankbranch.setCountryBranchId(new BigDecimal(sessionStateManage
					.getBranchId()));
			collectDetails.setExBankBranch(bankbranch);

			collectDetails.setDocumentDate(new Date());
			collectDetails.setDocumentLineNo(new BigDecimal(i++));

			// Foriegn Currency id //It should be loccal currency Id
			CurrencyMaster forcurrencymaster = new CurrencyMaster();
			forcurrencymaster.setCurrencyId(collect.getExCurrencyMaster()
					.getCurrencyId());
			collectDetails.setExCurrencyMaster(forcurrencymaster);

			collectDetails.setCollectionMode(lstofPayment
					.getColpaymentmodeCode());
			if (lstofPayment.getColBankCodeDT() != null) {
				collectDetails
						.setChequeBankRef(lstofPayment.getColBankCodeDT());
			}
			collectDetails.setCollAmt(lstofPayment.getColAmountDT());
			if (lstofPayment.getColApprovalNo() != null) {
				collectDetails.setApprovalNo(lstofPayment.getColApprovalNo());
			}

			collectDetails.setIsActive(Constants.Yes);

			try {
				collectDetails.setAcyymm(new SimpleDateFormat("dd/MM/yyyy")
						.parse(getCurrentDateWithFormat()));
			} catch (ParseException e) {
				e.printStackTrace();
			}

			collectDetails.setCreatedBy(sessionStateManage.getUserName());
			collectDetails.setCreatedDate(new Date());
			collectDetails.setPaymentModeId(lstofPayment
					.getColpaymentmodeIDtypeDT());

			collectionDetailsList.add(collectDetails);

		}
		return collectionDetailsList;
	}

	// ############## Report Genaration By Subramanian ##############

	private JasperPrint jasperPrint;
	private List<RefundRequestReport> refundRequestList = new CopyOnWriteArrayList<RefundRequestReport>();

	public void init() throws JRException {
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(
				refundRequestList);
		String reportPath = FacesContext.getCurrentInstance()
				.getExternalContext()
				.getRealPath("reports/design/RefundRequestOfficeCopy.jasper");
		jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap(),
				beanCollectionDataSource);
	}

	public void genarateRefundRequestOfficeCopyReports(ActionEvent actionEvent)
			throws JRException, IOException {
		ServletOutputStream servletOutputStream = null;

		try {
			fetchRefundRequestReportDetails();
			init();
			HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext
					.getCurrentInstance().getExternalContext().getResponse();
			httpServletResponse.addHeader("Content-disposition",
					"attachment; filename=RefundRequestOfficeCopy.pdf");
			servletOutputStream = httpServletResponse.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint,
					servletOutputStream);
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception e) {

		} finally {
			if (servletOutputStream != null) {
				servletOutputStream.close();
			}
		}
	}

	public void init1() throws JRException {
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(
				refundRequestList);
		String reportPath = FacesContext.getCurrentInstance()
				.getExternalContext()
				.getRealPath("reports/design/RefundRequestCustomerCopy.jasper");
		jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap(),
				beanCollectionDataSource);
	}

	public void genarateRefundRequestCustomerCopyReports(ActionEvent actionEvent)
			throws JRException, IOException {
		ServletOutputStream servletOutputStream = null;
		try {
			init1();
			HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext
					.getCurrentInstance().getExternalContext().getResponse();
			httpServletResponse.addHeader("Content-disposition",
					"attachment; filename=RefundRequestCustomerCopyCopy.pdf");
			servletOutputStream = httpServletResponse.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint,
					servletOutputStream);
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception e) {

		} finally {
			if (servletOutputStream != null) {
				servletOutputStream.close();
			}
		}
	}

	public void fetchRefundRequestReportDetails() {
		refundRequestList.clear();

		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance()
				.getExternalContext().getContext();
		String realPath = ctx.getRealPath("//");
		// String logoPath = realPath + Constants.LOGO_PATH;

		String logoPath = null;
		if (sessionStateManage.getCountryName().equalsIgnoreCase(
				Constants.KUWAIT)) {
			logoPath = realPath + Constants.LOGO_PATH;
		} else if (sessionStateManage.getCountryName().equalsIgnoreCase(
				Constants.OMAN)) {
			logoPath = realPath + Constants.LOGO_PATH_OM;
		} else {
			logoPath = realPath + Constants.LOGO_PATH;
		}

		RefundRequestReport refundRequestReport = new RefundRequestReport();

		refundRequestReport.setCustomerName(getCustomerName());
		if (getCustomerRefNo() != null) {
			refundRequestReport.setCustomerRefNo(getCustomerRefNo()
					.toPlainString());
		}
		if (getRefundFor() != null) {
			refundRequestReport.setRefundFor(new BigDecimal(getRefundFor()));
		}

		refundRequestReport.setNetRefund(getNetRefund());
		refundRequestReport.setPaymentNo(getTransferNo());

		refundRequestReport.setPaymentYear(new BigDecimal(getFinanceYear()));
		if (getTransferDate() != null) {
			SimpleDateFormat form1 = new SimpleDateFormat("dd-MM-yyyy");
			String finalDate1 = form1.format(getTransferDate());
			refundRequestReport.setPaymentDate(finalDate1);

		}
		// refundRequestReport.setPaymentDate(getTransferDate());
		if (getExchangeRate() != null) {
			refundRequestReport.setExchangeRate(getExchangeRate().toString());
		}
		refundRequestReport.setDrawnBank(getPayableBank());
		refundRequestReport.setDrawnBankBranch(getPayableBranch());
		if (getPaymentDate() != null) {
			SimpleDateFormat form = new SimpleDateFormat("dd-MM-yyyy");
			String finalDate = form.format(getPaymentDate());
			refundRequestReport.setRefDate(finalDate);
		}

		refundRequestReport.setRefNo(getApplicationRef());
		if (getApplicationYear() != null) {
			refundRequestReport
					.setRefYear(new BigDecimal(getApplicationYear()));
		}
		List<RemittanceTranxBenificiary> trnxBenificiaries = getiStopPaymentService()
				.viewDetailsTranxBeneficiary(getTransactionId());

		for (RemittanceTranxBenificiary remittancetrnxBenificiary : trnxBenificiaries) {
			refundRequestReport.setFavouringBankAccNo(remittancetrnxBenificiary
					.getBeneficiaryAccountNo());
			refundRequestReport
					.setFavouringBankBranch(remittancetrnxBenificiary
							.getBeneficiaryBranch());
			refundRequestReport.setFavouringBankName(remittancetrnxBenificiary
					.getBeneficiaryBank());
			refundRequestReport
					.setFavouringBankLocation(remittancetrnxBenificiary
							.getBeneficiaryFirstName());
		}
		refundRequestReport
				.setNetRefundCurrencyName(getTransferAmountCurrency());
		refundRequestReport.setRefundCurrencyName(getTransferAmountCurrency());
		refundRequestReport
				.setExchangeRateCurrencyName(getTransferAmountCurrency());

		refundRequestReport.setLogoPath(logoPath);

		refundRequestList.add(refundRequestReport);

	}

	private boolean booRenderColCheque = false;
	private String colchequebankCode;
	private String colChequeRef;
	private Date colChequeDate;
	private Date effectiveMinDate = new Date();
	private String colChequeApprovalNo;
	private String bankTransferApprovalNo;
	private boolean booRenderBankTransfer = false;

	public String getBankTransferApprovalNo() {
		return bankTransferApprovalNo;
	}

	public void setBankTransferApprovalNo(String bankTransferApprovalNo) {
		this.bankTransferApprovalNo = bankTransferApprovalNo;
	}

	public boolean isBooRenderBankTransfer() {
		return booRenderBankTransfer;
	}

	public void setBooRenderBankTransfer(boolean booRenderBankTransfer) {
		this.booRenderBankTransfer = booRenderBankTransfer;
	}

	public boolean isBooRenderColCheque() {
		return booRenderColCheque;
	}

	public void setBooRenderColCheque(boolean booRenderColCheque) {
		this.booRenderColCheque = booRenderColCheque;
	}

	public String getColchequebankCode() {
		return colchequebankCode;
	}

	public void setColchequebankCode(String colchequebankCode) {
		this.colchequebankCode = colchequebankCode;
	}

	public String getColChequeRef() {
		return colChequeRef;
	}

	public void setColChequeRef(String colChequeRef) {
		this.colChequeRef = colChequeRef;
	}

	public Date getColChequeDate() {
		return colChequeDate;
	}

	public void setColChequeDate(Date colChequeDate) {
		this.colChequeDate = colChequeDate;
	}

	public Date getEffectiveMinDate() {
		return effectiveMinDate;
	}

	public void setEffectiveMinDate(Date effectiveMinDate) {
		this.effectiveMinDate = effectiveMinDate;
	}

	public String getColChequeApprovalNo() {
		return colChequeApprovalNo;
	}

	public void setColChequeApprovalNo(String colChequeApprovalNo) {
		this.colChequeApprovalNo = colChequeApprovalNo;
	}

	// checking cheque data with datatable
	public Boolean checkingChequeDuplicateCheck() {
		Boolean checkCheque = false;
		int i = 0;
		if (getColchequebankCode() != null && coldatatablevalues.size() != 0) {
			for (PersonalRemittanceCollectionDataTable lstpaymentdata : coldatatablevalues) {
				i = 0;
				if (lstpaymentdata.getColBankCodeDT() != null) {
					if (lstpaymentdata.getColBankCodeDT().equalsIgnoreCase(
							getColchequebankCode())) {
						if (lstpaymentdata.getColchequeRefNo()
								.equalsIgnoreCase(getColChequeRef())) {
							i = 1;
							break;
						} else {
							i = 0;
						}
					} else {
						i = 0;
					}
				} else {
					i = 0;
				}
			}
			if (i == 1) {
				checkCheque = false;
			} else {
				checkCheque = true;
			}
		} else {
			checkCheque = true;
		}
		return checkCheque;
	}

	private Boolean booRenderDenaminationDetails;

	private int count;

	public Boolean getBooRenderDenaminationDetails() {
		return booRenderDenaminationDetails;
	}

	public void setBooRenderDenaminationDetails(
			Boolean booRenderDenaminationDetails) {
		this.booRenderDenaminationDetails = booRenderDenaminationDetails;
	}

	public Boolean getBooRenderFirstPanel() {
		return booRenderFirstPanel;
	}

	public void setBooRenderFirstPanel(Boolean booRenderFirstPanel) {
		this.booRenderFirstPanel = booRenderFirstPanel;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void view() {

		if (getDocumentNum() != null) {
			if (getRemittanceYear() != null && getTransferNo() != null) {
				setCount(0);
				count++;
				log.info("Entering into view Method");
				log.info("Trsansfer No " + getTransferNo());
				log.info("exit into view Method");
				setBooRenderFirstPanel(false);
				setBooRenderPaymentDetails(false);
				setBooRenderDenaminationDetails(false);
				setBooRenderCollection(true);
				getLocalBankListforIndicator();
				log.info("getTransferAmount" + getTransferAmount());
				// this value only redirect next page
				// setCalNetAmountPaid(getTransferAmount());
				if (coldatatablevalues.size() != 0) {
					setBooRendercollectiondatatable(true);
					setBoRenderTotalAmountCalPanel(true);
				}
				// LOGGER.info("setCalNetAmountPaid" + getCalNetAmountPaid());
				log.info("Exit into view Method");

				// clear fields
				setPaymentDetailsremark(null);
				setPayPaidAmount(null);
				setPayRefund(null);
				coldatatablevalues.clear();
				setBooRendercollectiondatatable(false);
				fetchAllPaymentDetails();
			} else {
				setErrorMessage("Transaction Document Number and Year not Available");
				RequestContext.getCurrentInstance().execute("csp.show();");
				return;
			}
		} else {
			setErrorMessage("Please Enter Document Number");
			RequestContext.getCurrentInstance().execute("csp.show();");
			return;
		}

	}

	private List<ViewBankDetails> chequebankMasterList = new ArrayList<ViewBankDetails>();
	private List<ViewBankDetails> localbankList = new ArrayList<ViewBankDetails>();

	private List<ViewBankDetails> bankMasterList = new CopyOnWriteArrayList<ViewBankDetails>();

	public List<ViewBankDetails> getBankMasterList() {
		return bankMasterList;
	}

	public void setBankMasterList(List<ViewBankDetails> bankMasterList) {
		this.bankMasterList = bankMasterList;
	}

	public List<ViewBankDetails> getLocalbankList() {
		return localbankList;
	}

	public void setLocalbankList(List<ViewBankDetails> localbankList) {
		this.localbankList = localbankList;
	}

	public List<ViewBankDetails> getChequebankMasterList() {
		return chequebankMasterList;
	}

	public void setChequebankMasterList(
			List<ViewBankDetails> chequebankMasterList) {
		this.chequebankMasterList = chequebankMasterList;
	}

	// to get the local bank list or customer bank list
	public void getLocalBankListforIndicator() {
		List<BigDecimal> duplicateCheck = new ArrayList<BigDecimal>();
		List<BigDecimal> duplicateCheck1 = new ArrayList<BigDecimal>();
		List<ViewBankDetails> lstofBank = new ArrayList<ViewBankDetails>();
		List<ViewBankDetails> lstofBank1 = new ArrayList<ViewBankDetails>();
		bankMasterList.clear();
		chequebankMasterList.clear();
		localbankList = iGeneralService
				.getLocalBankListFromView(sessionStateManage.getCountryId());
		// cheque banks purpose
		if (localbankList.size() != 0) {
			chequebankMasterList.addAll(localbankList);
		}
		// lstoflocalbank =
		// generalService.getLocalBankList(sessionmanage.getCountryId());
		List<ViewBankDetails> localBankListinCollection = icustomerBankService
				.customerBanks(getCustomerCode(), getColBankCode());
		if (localBankListinCollection.size() > 0) {
			bankMasterList.addAll(localBankListinCollection);
		} else {
			bankMasterList.addAll(localbankList);
		}
		if (bankMasterList.size() != 0) {
			for (ViewBankDetails lstBank : bankMasterList) {
				if (!duplicateCheck.contains(lstBank.getChequeBankId())) {
					duplicateCheck.add(lstBank.getChequeBankId());
					lstofBank.add(lstBank);
				}
			}
		}
		bankMasterList.clear();
		bankMasterList.addAll(lstofBank);
		if (chequebankMasterList.size() != 0) {
			for (ViewBankDetails lstBank : chequebankMasterList) {
				if (!duplicateCheck1.contains(lstBank.getChequeBankId())) {
					duplicateCheck1.add(lstBank.getChequeBankId());
					lstofBank1.add(lstBank);
				}
			}
		}
		chequebankMasterList.clear();
		chequebankMasterList.addAll(lstofBank1);
	}

	public void clickOnExit() throws IOException {
		/*
		 * if (sessionStateManage.getRoleId().equalsIgnoreCase("1")) {
		 * FacesContext.getCurrentInstance().getExternalContext()
		 * .redirect("../registration/employeehome.xhtml"); } else {
		 * FacesContext.getCurrentInstance().getExternalContext()
		 * .redirect("../registration/branchhome.xhtml"); }
		 */
		String roleNameDesc = iPersonalRemittanceService
				.toFetchRoleName(new BigDecimal(sessionStateManage.getRoleId()));
		String roleName = roleNameDesc.trim();
		if (roleName.equalsIgnoreCase(Constants.USER_ROLE_BRANCHSTAFF)) {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../registration/branchhome.xhtml");
		} else if (roleName
				.equalsIgnoreCase(Constants.USER_ROLE_BRANCH_MANAGER)
				|| roleName.equalsIgnoreCase(Constants.USER_ROLE_MANAGER)) {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../registration/branchhome.xhtml");
		} else if (roleName.equalsIgnoreCase(Constants.USER_ROLE_CORPORATE)) {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../registration/corporatehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../registration/branchhome.xhtml");
		}
	}

	public void gotoFirstPanel() {
		hideAllPanels();
		setBooRenderFirstPanel(true);
	}

	// checking customer name and debit card name of card
	public void firstNameCheck() {
		if (getColNameofCard().equalsIgnoreCase(getCustomerFullName())) {
			setColAuthorizedby(null);
			setColpassword(null);
			setBooAuthozed(false);
		} else {
			/*
			 * List<Employee> localEmpllist = generalService.getEmployeelist(
			 * sessionStateManage.getCountryId(), new
			 * BigDecimal(sessionStateManage.getBranchId()), new
			 * BigDecimal(sessionStateManage.getRoleId()));
			 * setEmpllist(localEmpllist);
			 */
			List<DebitAutendicationView> localEmpllist = iPersonalRemittanceService
					.getdebitAutendicationList();
			setEmpllist(localEmpllist);
			setColAuthorizedby(null);
			setColpassword(null);
			setBooAuthozed(true);
			// populate alert msg if customer name not match
			setExceptionMessage(Constants.NameCheckAlertMsg);
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}
	}

	private List<DebitAutendicationView> empllist = new ArrayList<DebitAutendicationView>();
	private String customerFullName;

	public String getCustomerFullName() {
		return customerFullName;
	}

	public void setCustomerFullName(String customerFullName) {
		this.customerFullName = customerFullName;
	}

	public List<DebitAutendicationView> getEmpllist() {
		return empllist;
	}

	public void setEmpllist(List<DebitAutendicationView> empllist) {
		this.empllist = empllist;
	}

	public void gotoBack() {
		log.info("Entering into gotoBack method");

		if (getBooRendercashdenomination()) {
			nextpaneltoPaymentDetails();
		} else {
			setBooRenderPaymentDetails(false);
			setBooRenderDenaminationDetails(false);
			setBooRenderCollection(true);
			// setColCash(null);
			// setBooRenderPaymentDetails(true);
			setBooRendercollectiondatatable(true);
			setBoRenderTotalAmountCalPanel(true);
			/* setCalNetAmountPaid(null); */
		}
		log.info("Exit into gotoBack method");
	}

	// added for new cr changes
	private BigDecimal docFinYear;
	private BigDecimal documentNum;
	private BigDecimal receiptPaymentPk;
	private String createdBy;
	private Date createdDate;
	private BigDecimal docIDPK;
	private List<ReceiptPayment> remittanceDocYearList = new ArrayList<ReceiptPayment>();
	private List<ReceiptPayment> receiptDocNoList = new ArrayList<ReceiptPayment>();

	public BigDecimal getDocFinYear() {
		return docFinYear;
	}

	public void setDocFinYear(BigDecimal docFinYear) {
		this.docFinYear = docFinYear;
	}

	public BigDecimal getDocumentNum() {
		return documentNum;
	}

	public void setDocumentNum(BigDecimal documentNum) {
		this.documentNum = documentNum;
	}

	public List<ReceiptPayment> getRemittanceDocYearList() {
		return remittanceDocYearList;
	}

	public void setRemittanceDocYearList(
			List<ReceiptPayment> remittanceDocYearList) {
		this.remittanceDocYearList = remittanceDocYearList;
	}

	public List<ReceiptPayment> getReceiptDocNoList() {
		return receiptDocNoList;
	}

	public void setReceiptDocNoList(List<ReceiptPayment> receiptDocNoList) {
		this.receiptDocNoList = receiptDocNoList;
	}

	public BigDecimal getReceiptPaymentPk() {
		return receiptPaymentPk;
	}

	public void setReceiptPaymentPk(BigDecimal receiptPaymentPk) {
		this.receiptPaymentPk = receiptPaymentPk;
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

	public BigDecimal getDocIDPK() {
		return docIDPK;
	}

	public void setDocIDPK(BigDecimal docIDPK) {
		this.docIDPK = docIDPK;
	}

	public void toFetchAllDocFinanceYear() {
		// setRemittanceDocYearList(null);
		//setDocFinYear(null);
		BigDecimal currentFinYear = null;
		currentFinYear = iStopPaymentService.toFetchFinancialYear();
		if (currentFinYear != null) {
			//setDocFinYear(currentFinYear);
		}
	}

	public void fectchDocumentNumbers() {
		setReceiptDocNoList(null);
		List<ReceiptPayment> lstRemittanceDocNum = iStopPaymentService
				.toFetchAllDocumentNo(new BigDecimal(23), getDocFinYear());
		if (lstRemittanceDocNum.size() > 0) {
			setReceiptDocNoList(lstRemittanceDocNum);
		}
	}

	// tofetchAllRecords from DB
	public void getRemittanceTransDetails() {
		try {
			if (getDocFinYear() != null && getDocumentNum() != null) {
				List<ReceiptPayment> liReceiptPayment = iStopPaymentService
						.toFetchAllRecordBasedOnDocYearAndDocNum(
								sessionStateManage.getCompanyId(),
								new BigDecimal(
										Constants.DOCUMENT_CODE_FOR_REFUND_REQUEST),
								getDocFinYear(), getDocumentNum());
				if (liReceiptPayment.size() > 0) {
					for (ReceiptPayment receiptPayment : liReceiptPayment) {
						if ((receiptPayment.getModifiedBy() == null ? receiptPayment
								.getCreatedBy() : receiptPayment
								.getModifiedBy())
								.equalsIgnoreCase(sessionStateManage
										.getUserName())) {
							RequestContext.getCurrentInstance().execute(
									"notApproved.show();");
							clear();
							return;
						} else {
							if (receiptPayment.getDocumentStatus() != null
									&& receiptPayment.getDocumentStatus()
											.equalsIgnoreCase(Constants.P)) {
								setErrorMessage("Refund Paid");
								RequestContext.getCurrentInstance().execute(
										"csp.show();");
							} else {
								setRemittanceYear(receiptPayment
										.getTransferFinanceYear());
								setTransferNo(receiptPayment
										.getTransferReference());
								setReceiptPaymentPk(receiptPayment
										.getReceiptId());
								setCreatedBy(receiptPayment.getCreatedBy());
								setCreatedDate(receiptPayment.getCreatedDate());
								setApplicationRef(getTransferNo());
								
								//Added for fixation of Issue
								BigDecimal refundfor = GetRound.roundBigDecimal(receiptPayment.getLocalNetAmount(), foreignLocalCurrencyDenominationService
										.getDecimalPerCurrency(new BigDecimal(sessionStateManage
												.getCurrencyId())));
								setRefundFor(refundfor.toPlainString());
								setExchangeRate(receiptPayment.getTransactionActualRate());
								
								setNetRefund(GetRound.roundBigDecimal(receiptPayment.getLocalNetAmount(), foreignLocalCurrencyDenominationService
										.getDecimalPerCurrency(new BigDecimal(sessionStateManage
												.getCurrencyId()))));
								
								setCalNetAmountPaid(GetRound.roundBigDecimal(receiptPayment.getLocalNetAmount(), foreignLocalCurrencyDenominationService
										.getDecimalPerCurrency(new BigDecimal(sessionStateManage
												.getCurrencyId()))));// .toPlainString();
								setColCash(getCalNetAmountPaid());
								
								getRemittanceTransactionDetails();
							}
						}
					}
				} else {
					RequestContext.getCurrentInstance().execute(
							"noRecord.show();");
					setRemittanceYear(null);
					setTransferNo(null);
					setReceiptPaymentPk(null);
					setDocumentNum(null);
					clear();
					return;
				}
			}
		} catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"
					+ ne.getMessage());
			setErrorMessage("MethodName::getRemittanceTransDetails");
			RequestContext.getCurrentInstance().execute("csp.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
			return;
		}
	}

	/*
	 * public void saveAllForUpdate(){ log.info("Entering into saveAll method");
	 * boolean noteEmpty = false; log.info("****getPayRefund****" +
	 * getPayRefund()); // card and cash both payment save if
	 * (coldatatablevalues.size() == 2) { // getDocumentSerialID("U"); //
	 * log.info("CASH and Card both are choosed here"); noteEmpty =
	 * saveForeignCurrencyAdjustValidation(); if (noteEmpty) { if
	 * (getPayRefund().signum() == -1) {
	 * RequestContext.getCurrentInstance().execute("totalcashCheck.show();"); }
	 * else { BigDecimal remitTranx=null; HashMap<String, Object> inputValues =
	 * new HashMap<String, Object>(); inputValues.put("receiptPK",
	 * getReceiptPaymentPk()==null?"":getReceiptPaymentPk().toPlainString());
	 * inputValues.put("user", sessionStateManage.getUserName());
	 * remitTranx=iStopPaymentCollectionService
	 * .toFetchRemitTrnxPk(sessionStateManage
	 * .getCompanyId(),sessionStateManage.getCountryId(), new
	 * BigDecimal(3),getDocumentNum(),getDocFinYear()); if(remitTranx != null){
	 * inputValues.put("remitTrnxId", remitTranx.toPlainString()); } Collect
	 * collect = concurrentSaveCollect(); inputValues.put("Collection",
	 * collect);
	 * inputValues.put("CollectionDetails",concurrentsaveCollectionDetail
	 * (collect));
	 * inputValues.put("ForeignCurrencyAdjust",concurrentsaveForeignCurrencyAdjust
	 * (getExchangeRate().toPlainString())); try {
	 * iStopPaymentCollectionService.saveOrUpDateAllValues(inputValues);
	 * 
	 * // Move to OLD System List<Collect> collectionList =
	 * iStopPaymentService.getCollectionListById(collect.getCollectionId());
	 * List<RemittanceTransaction> remitTxn =
	 * iStopPaymentService.getRemitTxnDetailsById(getTransactionId());
	 * if(collectionList.size()>0 && remitTxn.size()>0){
	 * iStopPaymentService.moveToOldSystemRefundPay
	 * (collectionList.get(0).getApplicationCountryId(),
	 * collectionList.get(0).getCompanyCode(),
	 * collectionList.get(0).getDocumentCode(),
	 * collectionList.get(0).getDocumentFinanceYear(),
	 * collectionList.get(0).getDocumentNo(),
	 * remitTxn.get(0).getCompanyId().getCompanyId(),
	 * remitTxn.get(0).getDocumentId().getDocumentID(),
	 * remitTxn.get(0).getDocumentFinanceYear(),
	 * remitTxn.get(0).getDocumentNo()); }
	 * RequestContext.getCurrentInstance().execute("save.show();"); } catch
	 * (Exception e) {
	 * RequestContext.getCurrentInstance().execute("csp.show();");
	 * setErrorMessage(e.getMessage()); e.printStackTrace(); }
	 * 
	 * } } else {
	 * RequestContext.getCurrentInstance().execute("notesZero.show();"); } }
	 * else { if (getColpaymentmodeId() != null &&
	 * getColpaymentmodeName().equals(Constants.CASHNAME)) { // cash payment
	 * mode
	 * 
	 * noteEmpty = saveForeignCurrencyAdjustValidation(); if (noteEmpty) { if
	 * (getPayRefund().signum() == -1) {
	 * RequestContext.getCurrentInstance().execute("totalcashCheck.show();"); }
	 * else { BigDecimal remitTranx=null; HashMap<String, Object> inputValues =
	 * new HashMap<String, Object>(); inputValues.put("receiptPK",
	 * getReceiptPaymentPk()==null?"":getReceiptPaymentPk().toPlainString());
	 * inputValues.put("user", sessionStateManage.getUserName());
	 * remitTranx=iStopPaymentCollectionService
	 * .toFetchRemitTrnxPk(sessionStateManage
	 * .getCompanyId(),sessionStateManage.getCountryId(), new
	 * BigDecimal(3),getDocumentNum(),getDocFinYear()); if(remitTranx != null){
	 * inputValues.put("remitTrnxId", remitTranx.toPlainString()); } Collect
	 * collect = concurrentSaveCollect(); inputValues.put("Collection",
	 * collect);
	 * inputValues.put("CollectionDetails",concurrentsaveCollectionDetail
	 * (collect));
	 * inputValues.put("ForeignCurrencyAdjust",concurrentsaveForeignCurrencyAdjust
	 * (getExchangeRate().toPlainString())); try {
	 * iStopPaymentCollectionService.saveOrUpDateAllValues(inputValues);
	 * 
	 * // Move to OLD System List<Collect> collectionList =
	 * iStopPaymentService.getCollectionListById(collect.getCollectionId());
	 * List<RemittanceTransaction> remitTxn =
	 * iStopPaymentService.getRemitTxnDetailsById(getTransactionId());
	 * if(collectionList.size()>0 && remitTxn.size()>0){
	 * iStopPaymentService.moveToOldSystemRefundPay
	 * (collectionList.get(0).getApplicationCountryId(),
	 * collectionList.get(0).getCompanyCode(),
	 * collectionList.get(0).getDocumentCode(),
	 * collectionList.get(0).getDocumentFinanceYear(),
	 * collectionList.get(0).getDocumentNo(),
	 * remitTxn.get(0).getCompanyId().getCompanyId(),
	 * remitTxn.get(0).getDocumentId().getDocumentID(),
	 * remitTxn.get(0).getDocumentFinanceYear(),
	 * remitTxn.get(0).getDocumentNo()); }
	 * 
	 * RequestContext.getCurrentInstance().execute("save.show();"); } catch
	 * (Exception e) {
	 * RequestContext.getCurrentInstance().execute("csp.show();");
	 * setErrorMessage(e.getMessage()); e.printStackTrace(); }
	 * log.info("Exit into saveAll method");
	 * 
	 * } } else {
	 * RequestContext.getCurrentInstance().execute("notesZero.show();"); } }
	 * else { // card payment mode
	 * 
	 * BigDecimal remitTranx=null; HashMap<String, Object> inputValues = new
	 * HashMap<String, Object>(); inputValues.put("receiptPK",
	 * getReceiptPaymentPk()==null?"":getReceiptPaymentPk().toPlainString());
	 * inputValues.put("user", sessionStateManage.getUserName());
	 * remitTranx=iStopPaymentCollectionService
	 * .toFetchRemitTrnxPk(sessionStateManage
	 * .getCompanyId(),sessionStateManage.getCountryId(), new
	 * BigDecimal(Constants.DOCUMENT_CODE_FOR_REFUND_REQUEST)
	 * ,getDocumentNum(),getDocFinYear()); if(remitTranx != null){
	 * inputValues.put("remitTrnxId", remitTranx.toPlainString()); } Collect
	 * collect = concurrentSaveCollect(); inputValues.put("Collection",
	 * collect);
	 * inputValues.put("CollectionDetails",concurrentsaveCollectionDetail
	 * (collect));
	 * inputValues.put("ForeignCurrencyAdjust",concurrentsaveForeignCurrencyAdjust
	 * (getExchangeRate().toPlainString())); try {
	 * iStopPaymentCollectionService.saveOrUpDateAllValues(inputValues);
	 * 
	 * // Move to OLD System List<Collect> collectionList =
	 * iStopPaymentService.getCollectionListById(collect.getCollectionId());
	 * List<RemittanceTransaction> remitTxn =
	 * iStopPaymentService.getRemitTxnDetailsById(getTransactionId());
	 * if(collectionList.size()>0 && remitTxn.size()>0){
	 * iStopPaymentService.moveToOldSystemRefundPay
	 * (collectionList.get(0).getApplicationCountryId(),
	 * collectionList.get(0).getCompanyCode(),
	 * collectionList.get(0).getDocumentCode(),
	 * collectionList.get(0).getDocumentFinanceYear(),
	 * collectionList.get(0).getDocumentNo(),
	 * remitTxn.get(0).getCompanyId().getCompanyId(),
	 * remitTxn.get(0).getDocumentId().getDocumentID(),
	 * remitTxn.get(0).getDocumentFinanceYear(),
	 * remitTxn.get(0).getDocumentNo()); }
	 * 
	 * RequestContext.getCurrentInstance().execute("save.show();"); } catch
	 * (Exception e) { setErrorMessage(e.getMessage());
	 * RequestContext.getCurrentInstance().execute("csp.show();"); }
	 * 
	 * } } firstTime = false; log.info("Exit into saveAll method"); }
	 */

	public void saveAllForUpdate() {
		log.info("Entering into saveAll method");
		boolean noteEmpty = false;
		log.info("****getPayRefund****" + getPayRefund());
		try {
			//getDocumentSerialID("U");
			BigDecimal remitTranx = null;
			HashMap<String, Object> inputValues = new HashMap<String, Object>();

			remitTranx = iStopPaymentCollectionService.toFetchRemitTrnxPk(
					sessionStateManage.getCompanyId(), sessionStateManage
							.getCountryId(), new BigDecimal(
							Constants.DOCUMENT_CODE_FOR_REFUND_REQUEST),
					getDocumentNum(), getDocFinYear());

			if (remitTranx != null) {

				inputValues.put("receiptPK", getReceiptPaymentPk() == null ? ""	: getReceiptPaymentPk().toPlainString());
				inputValues.put("user", sessionStateManage.getUserName());
				inputValues.put("remitTrnxId", remitTranx.toPlainString());
				List<ForeignCurrencyAdjust> lstCurrencyAdjust = concurrentsaveForeignCurrencyAdjust(getExchangeRate().toPlainString());
				inputValues.put("ForeignCurrencyAdjust", lstCurrencyAdjust);
				List<Payment> lstpayment = savePaymentRecord();
				inputValues.put("Payment", lstpayment);

				iStopPaymentCollectionService.saveOrUpDateAllValues(inputValues);

				List<RemittanceTransaction> lstRemitTrnx = iPersonalRemittanceService.fetchRemittanceTransactionDetails(sessionStateManage.getCountryId(),
								sessionStateManage.getCompanyId(),getRemittanceYear(), getTransferNo());
				if (lstRemitTrnx != null && !lstRemitTrnx.isEmpty()) {
					RemittanceTransaction remitTrnx = lstRemitTrnx.get(0);

					List<Document> lstdocCode = documentSerialityService.getDocumentCodeByDocId(remitTrnx.getDocumentId().getDocumentID());
					Document docCode = lstdocCode.get(0);
					// Move to OLD System
					String errmsg = iStopPaymentService.moveToOldSystemRefundPay(sessionStateManage.getCountryId(),sessionStateManage.getCompanyId(),
									new BigDecimal(Constants.DOCUMENT_CODE_FOR_REFUND_REQUEST),	getDocFinYear(), getDocumentNum(),remitTrnx.getCompanyId().getCompanyId(),
									docCode.getDocumentCode(),getRemittanceYear(), getTransferNo());
					if (errmsg != null) {
						setErrorMessage(errmsg);
						RequestContext.getCurrentInstance().execute("csp.show();");
					} else {
						RequestContext.getCurrentInstance().execute("save.show();");
					}
				}
			} else {
				setErrorMessage("Record not available in Receipt Complaint");
				RequestContext.getCurrentInstance().execute("csp.show();");
			}
		} catch (Exception e) {
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}

	public List<Payment> savePaymentRecord() {

		List<Payment> lstPayment = new ArrayList<Payment>();
		BigDecimal documentPk = null;
		documentPk = iStopPaymentCollectionService
				.toFetchDocumentPk(new BigDecimal(
						Constants.DOCUMENT_CODE_FOR_REFUND_REQUEST));

		// if refund available need to store in Ex_Payment
		if (coldatatablevalues != null && !coldatatablevalues.isEmpty()) {

			try {
				SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
						"dd/MM/yyyy");
				String date = getCurrentDateWithFormat();
				int i = 0;
				Date acc_Month = null;
				acc_Month = DATE_FORMAT.parse(date);

				Payment payment = new Payment();

				CountryMaster countryMaster = new CountryMaster();
				countryMaster.setCountryId(sessionStateManage.getCountryId());
				payment.setCountryId(countryMaster);

				payment.setCompanyId(sessionStateManage.getCompanyId());

				if (getCustomerId() != null) {
					payment.setCustomerId(getCustomerId());
				}

				payment.setDocCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_REFUND_REQUEST));

				if (getDocFinYear() != null) {
					payment.setDocYear(getDocFinYear());
				}

				payment.setAcyymm(acc_Month);

				payment.setCountryBranchId(new BigDecimal(sessionStateManage.getBranchId()));

				// payment.setReceiptType(Constants.RECEIPT_TYPE); // 70 - fc
				// Purchase
				payment.setPaymentDate(new Date());
				payment.setPaymentmode(coldatatablevalues.get(0).getColpaymentmodeCode());// not only collection mode
													// Cash - C , Bank Transfer	// - T , Bank Cheque - B
				payment.setCurrencyId(new BigDecimal(sessionStateManage.getCurrencyId())); // stock currency id always
											// application currency id
				payment.setPaidAmount(getColCash());
				payment.setApprovalNo(coldatatablevalues.get(0).getColApprovalNo());
				
				payment.setChequeBankCode(coldatatablevalues.get(0).getColBankCodeDT());

				payment.setDocNumber(getDocumentNum());

				payment.setIsActive(Constants.Yes);

				payment.setCreatedBy(sessionStateManage.getUserName());
				payment.setCreatedDate(new Date());

				payment.setLocCod(sessionStateManage.getCountryBranchCode());

				List<CompanyMasterDesc> companyCode = iGeneralService
						.getCompanyList(sessionStateManage.getCompanyId(),
								sessionStateManage.getLanguageId());
				if (companyCode != null && !companyCode.isEmpty()
						&& companyCode.get(0).getFsCompanyMaster() != null) {
					BigDecimal companyCodeValue = companyCode.get(0)
							.getFsCompanyMaster().getCompanyCode();
					payment.setCompanyCode(companyCodeValue);
				}

				if (documentPk != null) {
					payment.setDocumentId(documentPk);
				}

				lstPayment.add(payment);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return lstPayment;
	}

	public String getColBankTransferBankCode() {
		return colBankTransferBankCode;
	}

	public void setColBankTransferBankCode(String colBankTransferBankCode) {
		this.colBankTransferBankCode = colBankTransferBankCode;
	}

}
