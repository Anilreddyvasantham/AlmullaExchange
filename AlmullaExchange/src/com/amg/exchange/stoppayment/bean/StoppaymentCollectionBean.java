package com.amg.exchange.stoppayment.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

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

import com.amg.exchange.common.model.CityMasterDesc;
import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.common.model.CountryMaster;
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
import com.amg.exchange.miscellaneous.service.IMiscellaneousReceiptPaymentService;
import com.amg.exchange.registration.model.ContactDetail;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.registration.service.ICityMasterService;
import com.amg.exchange.registration.service.ICompanyMasterservice;
import com.amg.exchange.registration.service.ICustomerRegistrationBranchService;
import com.amg.exchange.registration.service.IEncrptionDescriptionService;
import com.amg.exchange.registration.service.ILoginService;
import com.amg.exchange.remittance.bean.PersonalRemittanceCollectionDataTable;
import com.amg.exchange.remittance.model.CustomerBank;
import com.amg.exchange.remittance.model.DebitAutendicationView;
import com.amg.exchange.remittance.model.PaymentMode;
import com.amg.exchange.remittance.model.PaymentModeDesc;
import com.amg.exchange.remittance.model.RemittanceTxnView;
import com.amg.exchange.remittance.model.ViewBankDetails;
import com.amg.exchange.remittance.model.ViewStock;
import com.amg.exchange.remittance.service.ICustomerBankService;
import com.amg.exchange.remittance.service.IPaymentService;
import com.amg.exchange.remittance.service.IPersonalRemittanceService;
import com.amg.exchange.stoppayment.model.RemittanceTransaction;
import com.amg.exchange.stoppayment.model.RemittanceTranxBenificiary;
import com.amg.exchange.stoppayment.service.IStopPaymentCollectionService;
import com.amg.exchange.stoppayment.service.IStopPaymentService;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.treasury.service.IGLTransactionForADocument;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.GetRound;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;
import com.amg.exchange.util.WarningHandler;
import com.amg.exchange.util.iCypherSecurity;
import com.amg.exchange.util.impl.CypherSecurityImpl;

/**
 * @author Mohan
 * 
 */
@Component("stoppaymentCollectionBean")
@Scope("session")
public class StoppaymentCollectionBean<T> implements Serializable {
	/**
	 * 
	 */
	private static final Logger LOGGER = Logger.getLogger(StoppaymentCollectionBean.class);
	private static final long serialVersionUID = 1L;
	private BigDecimal remittanceTransactionId;
	//private int financialYear;
	private BigDecimal transferNo;
	private BigDecimal transferYear;
	private BigDecimal productCode;
	private String ProductName;
	private String productStatus;
	private BigDecimal applicationNo;
	private BigDecimal applicationFinancialYear;
	private BigDecimal branchCode;
	private String branchName;
	private BigDecimal customerCode;
	private BigDecimal customerReference;
	private String customerName;
	private String customerFirstName;
	private String telephoneNo;
	private Date transferDate;
	private BigDecimal validUntill;
	private String accPayee;
	private String beneficiaryName;
	private BigDecimal beneficiaryAccNo;
	private BigDecimal beneficiaryBankId;
	private BigDecimal beneficiaryBankBranchId;
	private BigDecimal beneficiaryBankCountryId;
	private BigDecimal transferAmount;
	private BigDecimal payableAt;
	// private BigDecimal payableBranch;
	private BigDecimal payableBankId;
	private String payableBranchCode;
	private BigDecimal saleAmount;
	private BigDecimal exchangeRate;
	private BigDecimal charges;
	private BigDecimal deliveryCharges;
	private BigDecimal netAmount;
	private BigDecimal purchaseAmount;
	private BigDecimal commission;
	private String userDealYear;
	private String dealYear;
	private String dealDate;
	private BigDecimal dealYearId;
	private BigDecimal userDealYearId;
	private Date receiptDate;
	private BigDecimal receiptNo;
	private Date paymentDate;
	private String remarks;
	private Boolean renderRefEditable = false;
	private Boolean renderRef = true;
	private BigDecimal spCharges;
	private BigDecimal paidAmount;
	private BigDecimal refundAmount;
	private String beneficiary;
	private String payableBranch;
	private String errorMessage;
	// for denamination details
	private Boolean booRenderFirstPanel;
	private Boolean booRenderPaymentDetails;
	private Boolean booRenderDenaminationDetails;
	private Boolean booRenderCollection;
	private String documentSerialId;
	// last panel Payment details
	private BigDecimal denomtotalCashreceived;
	private BigDecimal denomdifference;
	private BigDecimal denomtotalCash;
	private String paymentDetailsremark;
	private BigDecimal payPaidAmount;
	private BigDecimal payNetPaidAmount;
	private BigDecimal payRefund;
	private BigDecimal cashAmount;
	private String varToKeepSerial = null;
	private Boolean booRendercashdenomination = false;
	private BigDecimal colamountKWD = new BigDecimal(0);
	private BigDecimal calNetAmountPaid;
	private BigDecimal calGrossAmount;
	private BigDecimal calNetAmountSend;
	private BigDecimal colCash;
	private BigDecimal errcolCashExistsLimit;
	private String colCurrency;
	// private BigDecimal colremittanceNo;
	private BigDecimal colfcsaleNo;
	private BigDecimal colpaymentmodeId;
	private String colpaymentmodeName;
	private BigDecimal colBankid;
	private BigDecimal colCardNo;
	private String colNameofCard;
	private String colAuthorizedby;
	private String colpassword;
	private String cyberPassword;
	private String paymentMode;
	private Boolean booRenderColDebitCard = false;
	private Boolean booRendercollectiondatatable = false;
	private BigDecimal toalUsedAmount = new BigDecimal(0);
	private BigDecimal totalUamount = new BigDecimal(0);
	private BigDecimal subtractedAmount = new BigDecimal(0);
	private Boolean boRenderTotalAmountCalPanel = false;
	private List<UserFinancialYear> financialYearList = new ArrayList<>();
	private BigDecimal forgienCurrencyId;
	private BigDecimal localCurrencyId;
	private BigDecimal localTransactionAmount;
	private BigDecimal forgienTransactionAmount;
	private int finaceYearId;
	private int finaceYear;
	private ReceiptPayment receiptPayment;
	private BigDecimal tempCash;
	private BigDecimal bankBranchId;
	private boolean firstTime = false;
	private JasperPrint jasperPrint;
	private Boolean successPanel;
	private String beneBankName;
	private String beneBranchName;
	private BigDecimal customerId;
	private String customerNameForReport;
	private String bankForDisplay;
	private BigDecimal transNumForReport;

	private List<StopPaymentCollectionReport> StopPaymentCollectionReport = new CopyOnWriteArrayList<StopPaymentCollectionReport>();
	private List<StopPaymentCollectionReport> StopPaymentCollectionrequest = new CopyOnWriteArrayList<StopPaymentCollectionReport>();
	private ArrayList<ForeignLocalCurrencyDataTable> lstData = new ArrayList<ForeignLocalCurrencyDataTable>();
	private List<ViewBankDetails> bankMasterList = new CopyOnWriteArrayList<ViewBankDetails>();
	private List<UserFinancialYear> DealYearList = new ArrayList<UserFinancialYear>();
	private SessionStateManage session = new SessionStateManage();
	CopyOnWriteArrayList<PersonalRemittanceCollectionDataTable> coldatatablevalues = new CopyOnWriteArrayList<PersonalRemittanceCollectionDataTable>();
	private List<BankApplicability> lstoflocalbank = new ArrayList<BankApplicability>();
	private int count;
	private String exceptionMessage;
	RemittanceTxnView beanTransaction = null;


	private  Boolean booProcedureDialog=false;
	private Boolean focus = true;
	private String errMsg;

	private boolean booRenderColBankTransfer = false;
	private String colBankTransferBankCode;

	

	@Autowired
	IMiscellaneousReceiptPaymentService<T> iMiscellaneousReceiptPaymentService;
	@Autowired
	IForeignCurrencyPurchaseService<T> foreignCurrencyPurchaseService;
	@Autowired
	IStopPaymentCollectionService stopPaymentCollectionService;
	@Autowired
	ICustomerRegistrationBranchService<T> icustomerRegistrationService;
	@Autowired
	ForeignLocalCurrencyDenominationService<T> foreignLocalCurrencyDenominationService;
	@Autowired
	IStopPaymentService<T> stopPaymentService;
	@Autowired
	ICustomerBankService icustomerBankService;
	@Autowired
	IPersonalRemittanceService iPersonalRemittanceService;
	@Autowired
	IForeignCurrencyPurchaseService<T> foreignCurrencyPurchase;
	@Autowired
	IGeneralService<T> generalService;
	@Autowired
	IPaymentService ipaymentService;
	@Autowired
	ICustomerRegistrationBranchService<T> customerService;
	@Autowired
	IEncrptionDescriptionService<T> encryptionDescriptionService;
	@Autowired
	IGLTransactionForADocument iglTransactionForADocument;
	@Autowired
	ICityMasterService<T> cityService;
	Customer customer;





	public BigDecimal getPurchaseAmount() {
		return purchaseAmount;
	}

	public void setPurchaseAmount(BigDecimal purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
	}

	public BigDecimal getDeliveryCharges() {
		return deliveryCharges;
	}

	public void setDeliveryCharges(BigDecimal deliveryCharges) {
		this.deliveryCharges = deliveryCharges;
	}

	public BigDecimal getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(BigDecimal netAmount) {
		this.netAmount = netAmount;
	}

	public BigDecimal getCharges() {
		return charges;
	}

	public void setCharges(BigDecimal charges) {
		this.charges = charges;
	}

	public BigDecimal getCommission() {
		return commission;
	}

	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}

	public BigDecimal getCustomerReference() {
		return customerReference;
	}

	public void setCustomerReference(BigDecimal customerReference) {
		this.customerReference = customerReference;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public BigDecimal getErrcolCashExistsLimit() {
		return errcolCashExistsLimit;
	}

	public void setErrcolCashExistsLimit(BigDecimal errcolCashExistsLimit) {
		this.errcolCashExistsLimit = errcolCashExistsLimit;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Boolean getSuccessPanel() {
		return successPanel;
	}

	public void setSuccessPanel(Boolean successPanel) {
		this.successPanel = successPanel;
	}

	public BigDecimal getBankBranchId() {
		return bankBranchId;
	}

	public void setBankBranchId(BigDecimal bankBranchId) {
		this.bankBranchId = bankBranchId;
	}

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public BigDecimal getLocalTransactionAmount() {
		return localTransactionAmount;
	}

	public void setLocalTransactionAmount(BigDecimal localTransactionAmount) {
		this.localTransactionAmount = localTransactionAmount;
	}

	public BigDecimal getForgienTransactionAmount() {
		return forgienTransactionAmount;
	}

	public void setForgienTransactionAmount(BigDecimal forgienTransactionAmount) {
		this.forgienTransactionAmount = forgienTransactionAmount;
	}

	public BigDecimal getLocalCurrencyId() {
		return localCurrencyId;
	}

	public void setLocalCurrencyId(BigDecimal localCurrencyId) {
		this.localCurrencyId = localCurrencyId;
	}

	public BigDecimal getForgienCurrencyId() {
		return forgienCurrencyId;
	}

	public void setForgienCurrencyId(BigDecimal forgienCurrencyId) {
		this.forgienCurrencyId = forgienCurrencyId;
	}

	public BigDecimal getRemittanceTransactionId() {
		return remittanceTransactionId;
	}

	public void setRemittanceTransactionId(BigDecimal remittanceTransactionId) {
		this.remittanceTransactionId = remittanceTransactionId;
	}

	public List<ViewBankDetails> getBankMasterList() {
		return bankMasterList;
	}

	public void setBankMasterList(List<ViewBankDetails> bankMasterList) {
		this.bankMasterList = bankMasterList;
	}

	public Boolean getBooRenderDenaminationDetails() {
		return booRenderDenaminationDetails;
	}

	public void setBooRenderDenaminationDetails(Boolean booRenderDenaminationDetails) {
		this.booRenderDenaminationDetails = booRenderDenaminationDetails;
	}

	public String getCustomerFirstName() {
		return customerFirstName;
	}

	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Boolean getBooRendercashdenomination() {
		return booRendercashdenomination;
	}

	public void setBooRendercashdenomination(Boolean booRendercashdenomination) {
		this.booRendercashdenomination = booRendercashdenomination;
	}

	public Boolean getBooRenderCollection() {
		return booRenderCollection;
	}

	public void setBooRenderCollection(Boolean booRenderCollection) {
		this.booRenderCollection = booRenderCollection;
	}

	public BigDecimal getApplicationNo() {
		return applicationNo;
	}

	public void setApplicationNo(BigDecimal applicationNo) {
		this.applicationNo = applicationNo;
	}

	public ArrayList<ForeignLocalCurrencyDataTable> getLstData() {
		return lstData;
	}

	public void setLstData(ArrayList<ForeignLocalCurrencyDataTable> lstData) {
		this.lstData = lstData;
	}

	public Boolean getBooRenderFirstPanel() {
		return booRenderFirstPanel;
	}

	public void setBooRenderFirstPanel(Boolean booRenderFirstPanel) {
		this.booRenderFirstPanel = booRenderFirstPanel;
	}

	public Boolean getBooRenderPaymentDetails() {
		return booRenderPaymentDetails;
	}

	public BigDecimal getTempCash() {
		return tempCash;
	}

	public void setTempCash(BigDecimal tempCash) {
		this.tempCash = tempCash;
	}

	public void setBooRenderPaymentDetails(Boolean booRenderPaymentDetails) {
		this.booRenderPaymentDetails = booRenderPaymentDetails;
	}

	public String getBeneficiary() {
		return beneficiary;
	}

	public void setBeneficiary(String beneficiary) {
		this.beneficiary = beneficiary;
	}

	public IForeignCurrencyPurchaseService<T> getForeignCurrencyPurchaseService() {
		return foreignCurrencyPurchaseService;
	}

	public void setForeignCurrencyPurchaseService(IForeignCurrencyPurchaseService<T> foreignCurrencyPurchaseService) {
		this.foreignCurrencyPurchaseService = foreignCurrencyPurchaseService;
	}

	public String getVarToKeepSerial() {
		return varToKeepSerial;
	}

	public void setVarToKeepSerial(String varToKeepSerial) {
		this.varToKeepSerial = varToKeepSerial;
	}

	public void setDocumentSerialId(String documentSerialId) {
		this.documentSerialId = documentSerialId;
	}

	public Date getReceiptDate() {
		return receiptDate;
	}

	public void setReceiptDate(Date receiptDate) {
		this.receiptDate = receiptDate;
	}

	public BigDecimal getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(BigDecimal receiptNo) {
		this.receiptNo = receiptNo;
	}

	public BigDecimal getSpCharges() {
		return spCharges;
	}

	public void setSpCharges(BigDecimal spCharges) {
		this.spCharges = spCharges;
	}

	public BigDecimal getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}

	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	public BigDecimal getUserDealYearId() {
		return userDealYearId;
	}

	public void setUserDealYearId(BigDecimal userDealYearId) {
		this.userDealYearId = userDealYearId;
	}

	public BigDecimal getDealYearId() {
		return dealYearId;
	}

	public void setDealYearId(BigDecimal dealYearId) {
		this.dealYearId = dealYearId;
	}

	public String getDealDate() {
		return dealDate;
	}

	public void setDealDate(String dealDate) {
		this.dealDate = dealDate;
	}

	public String getUserDealYear() {
		return userDealYear;
	}

	public void setUserDealYear(String userDealYear) {
		this.userDealYear = userDealYear;
	}

	public Boolean getRenderRefEditable() {
		return renderRefEditable;
	}

	public void setRenderRefEditable(Boolean renderRefEditable) {
		this.renderRefEditable = renderRefEditable;
	}

	public Boolean getRenderRef() {
		return renderRef;
	}

	public void setRenderRef(Boolean renderRef) {
		this.renderRef = renderRef;
	}

	/*public int getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(int financialYear) {
		this.financialYear = financialYear;
	}*/
	/*
	 * public Long getTransferNo() { return transferNo; }
	 * 
	 * public void setTransferNo(Long transferNo) { this.transferNo =
	 * transferNo; }
	 */

	public BigDecimal getProductCode() {
		return productCode;
	}

	public BigDecimal getTransferNo() {
		return transferNo;
	}

	public void setTransferNo(BigDecimal transferNo) {
		this.transferNo = transferNo;
	}

	public BigDecimal getTransferYear() {
		return transferYear;
	}

	public void setTransferYear(BigDecimal transferYear) {
		this.transferYear = transferYear;
	}

	public void setProductCode(BigDecimal productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return ProductName;
	}

	public void setProductName(String productName) {
		ProductName = productName;
	}

	public String getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}

	public BigDecimal getApplicationFinancialYear() {
		return applicationFinancialYear;
	}

	public void setApplicationFinancialYear(BigDecimal applicationFinancialYear) {
		this.applicationFinancialYear = applicationFinancialYear;
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

	public String getAccPayee() {
		return accPayee;
	}

	public void setAccPayee(String accPayee) {
		this.accPayee = accPayee;
	}

	public BigDecimal getCashAmount() {
		return cashAmount;
	}

	public void setCashAmount(BigDecimal cashAmount) {
		this.cashAmount = cashAmount;
	}

	public BigDecimal getPayNetPaidAmount() {
		return payNetPaidAmount;
	}

	public void setPayNetPaidAmount(BigDecimal payNetPaidAmount) {
		this.payNetPaidAmount = payNetPaidAmount;
	}

	public BigDecimal getDenomtotalCashreceived() {
		return denomtotalCashreceived;
	}

	public void setDenomtotalCashreceived(BigDecimal denomtotalCashreceived) {
		this.denomtotalCashreceived = denomtotalCashreceived;
	}

	public BigDecimal getDenomdifference() {
		return denomdifference;
	}

	public void setDenomdifference(BigDecimal denomdifference) {
		this.denomdifference = denomdifference;
	}

	public BigDecimal getDenomtotalCash() {
		return denomtotalCash;
	}

	public void setDenomtotalCash(BigDecimal denomtotalCash) {
		this.denomtotalCash = denomtotalCash;
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

	public BigDecimal getPayRefund() {
		return payRefund;
	}

	public void setPayRefund(BigDecimal payRefund) {
		this.payRefund = payRefund;
	}

	public CopyOnWriteArrayList<PersonalRemittanceCollectionDataTable> getColdatatablevalues() {
		return coldatatablevalues;
	}

	public void setColdatatablevalues(CopyOnWriteArrayList<PersonalRemittanceCollectionDataTable> coldatatablevalues) {
		this.coldatatablevalues = coldatatablevalues;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
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

	public void setBoRenderTotalAmountCalPanel(Boolean boRenderTotalAmountCalPanel) {
		this.boRenderTotalAmountCalPanel = boRenderTotalAmountCalPanel;
	}

	public Boolean getBooRenderColDebitCard() {
		return booRenderColDebitCard;
	}

	public void setBooRenderColDebitCard(Boolean booRenderColDebitCard) {
		this.booRenderColDebitCard = booRenderColDebitCard;
	}

	public Boolean getBooRendercollectiondatatable() {
		return booRendercollectiondatatable;
	}

	public void setBooRendercollectiondatatable(Boolean booRendercollectiondatatable) {
		this.booRendercollectiondatatable = booRendercollectiondatatable;
	}

	public String getColCurrency() {
		return colCurrency;
	}

	public void setColCurrency(String colCurrency) {
		this.colCurrency = colCurrency;
	}

	public BigDecimal getColfcsaleNo() {
		return colfcsaleNo;
	}

	public void setColfcsaleNo(BigDecimal colfcsaleNo) {
		this.colfcsaleNo = colfcsaleNo;
	}

	public BigDecimal getColpaymentmodeId() {
		return colpaymentmodeId;
	}

	public void setColpaymentmodeId(BigDecimal colpaymentmodeId) {
		this.colpaymentmodeId = colpaymentmodeId;
	}

	public String getColpaymentmodeName() {
		return colpaymentmodeName;
	}

	public void setColpaymentmodeName(String colpaymentmodeName) {
		this.colpaymentmodeName = colpaymentmodeName;
	}

	public BigDecimal getColBankid() {
		return colBankid;
	}

	public void setColBankid(BigDecimal colBankid) {
		this.colBankid = colBankid;
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

	public BigDecimal getColCash() {
		return colCash;
	}

	public void setColCash(BigDecimal colCash) {
		this.colCash = colCash;
	}

	public BigDecimal getCalNetAmountPaid() {
		return calNetAmountPaid;
	}

	public void setCalNetAmountPaid(BigDecimal calNetAmountPaid) {
		this.calNetAmountPaid = calNetAmountPaid;
	}

	public BigDecimal getCalGrossAmount() {
		return calGrossAmount;
	}

	public void setCalGrossAmount(BigDecimal calGrossAmount) {
		this.calGrossAmount = calGrossAmount;
	}

	public BigDecimal getCalNetAmountSend() {
		return calNetAmountSend;
	}

	public void setCalNetAmountSend(BigDecimal calNetAmountSend) {
		this.calNetAmountSend = calNetAmountSend;
	}

	public BigDecimal getColamountKWD() {
		return colamountKWD;
	}

	public void setColamountKWD(BigDecimal colamountKWD) {
		this.colamountKWD = colamountKWD;
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

	public BigDecimal getTransferAmount() {
		return transferAmount;
	}

	public void setTransferAmount(BigDecimal transferAmount) {
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

	public BigDecimal getPayableBankId() {
		return payableBankId;
	}

	public void setPayableBankId(BigDecimal payableBankId) {
		this.payableBankId = payableBankId;
	}

	public String getPayableBranchCode() {
		return payableBranchCode;
	}

	public void setPayableBranchCode(String payableBranchCode) {
		this.payableBranchCode = payableBranchCode;
	}

	public BigDecimal getSaleAmount() {
		return saleAmount;
	}

	public void setSaleAmount(BigDecimal saleAmount) {
		this.saleAmount = saleAmount;
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

	public BigDecimal getValidUntill() {
		return validUntill;
	}

	public void setValidUntill(BigDecimal validUntill) {
		this.validUntill = validUntill;
	}

	/**
	 * Document Seriality
	 */
	public String getDocumentSerialID(String processIn) {
		LOGGER.info("Start getDocumentSerisalID ....");
		LOGGER.info("countryId" + Integer.parseInt(session.getSessionValue("countryId")));
		LOGGER.info("Company Id " + Integer.parseInt(session.getSessionValue("companyId")));
		//LOGGER.info("financialYear" + financialYear);
		LOGGER.info("dealYear" + getDealYear());
		LOGGER.info("processIn" + processIn);

		try{
			HashMap<String, String> outPutValues = generalService.getNextDocumentRefNumber(Integer.parseInt(session.getSessionValue("countryId")), Integer.parseInt(session.getSessionValue("companyId")), Constants.DOCUMENT_CODE_FOR_STOPPAYMENT.intValue(), Integer.parseInt(getDealYear()), processIn,session.getCountryBranchCode());

			String proceErrorMsg=outPutValues.get("PROCE_ERORRMSG");
			if(proceErrorMsg!=null)
			{
				setBooProcedureDialog(true);
				setErrMsg(proceErrorMsg);
				RequestContext.getCurrentInstance().execute("proceErr.show();");
				return "0";

			}else{
				setBooProcedureDialog(false);
				String documentSerialID=outPutValues.get("DOCNO");
				return documentSerialID;

			}



		}catch(NumberFormatException | AMGException e){
			setErrMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("proceErr.show();");
			return "0";
		}



		//documentSerialId = generalService.getNextDocumentReferenceNumber(Integer.parseInt(session.getSessionValue("countryId")), Integer.parseInt(session.getSessionValue("companyId")), Constants.DOCUMENT_CODE_FOR_STOPPAYMENT.intValue(), Integer.parseInt(dealYear), processIn,
		//	session.getCountryBranchCode());
		//LOGGER.info("end getDocumentSerialID  :" + documentSerialId);
		//return documentSerialId;
	}

	/**
	 * stopPaymentCollectionpageNavigation method calling from fx-deal
	 */
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void stopPaymentCollectionpageNavigation() {
		LOGGER.info("Entering into stopPaymentCollectionpageNavigation method");
		fetchComplaintfinanceYear();
		hideAllPanels();
		clear();
		setBooRenderFirstPanel(true);
		setBooRenderDenaminationDetails(false);
		setBooRenderPaymentDetails(false);
		setBooRenderCollection(false);
		setBooRendercollectiondatatable(false);
		// boRenderTotalAmountCalPanel
		setBoRenderTotalAmountCalPanel(false);
		setSuccessPanel(false);
		setErrorMessage(null);
		setBooProcedureDialog( false);
		//setCustomerNameForReport( null);
		lstData.clear();
		coldatatablevalues.clear();
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "StopPaymentrequestwithCollection.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../stoppayment/StopPaymentrequestwithCollection.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("Exit into stopPaymentCollectionpageNavigation method");
	}

	// For Hiding all panels except firstPanel
	public void hideAllPanels() {
		setBooRenderCollection(false);
		setBooRendercollectiondatatable(false);
		setBoRenderTotalAmountCalPanel(false);
		setBooAuthozed(false);
		setBooRenderColDebitCard(false);
		setBooRenderCollection(false);
		setBooRenderPaymentDetails(false);
		setBooRenderDenaminationDetails(false);
		setSuccessPanel(false);
	}

	public void clear() {
		firstTime = false;
		setTransferNo(null);
		setTransferYear(null);
		setProductCode(null);
		setProductName(null);
		setProductStatus(null);
		setApplicationNo(null);
		setApplicationFinancialYear(null);
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
		setPayableBankId(null);
		setPayableBranchCode(null);
		setSaleAmount(null);
		setBeneficiary(null);
		setPaidAmount(null);
		setReceiptDate(null);
		setPaymentDate(null);
		setCalNetAmountPaid(null);
		setRemarks(null);
		setColCash(null);
		setColpaymentmodeId(null);
		setSpCharges(null);
		setPaymentDetailsremark(null);
		clearingDetailAfterAdding();
		setPaymentMode(null);
		setBoorefundPaymentDetails(false);
		setCustomerReference(null);
		setSaleAmount(null);
		setExchangeRate(null);
		setCommission(null);
		setCharges(null);
		setDeliveryCharges(null);
		setPurchaseAmount(null);
		setNetAmount(null);

		coldatatablevalues.clear();
		setToalUsedAmount(null);
		setTotalbalanceAmount(null);
		setTotalrefundAmount(null);

		setColBankCode(null);
		setColCardNo(null);
		setPopulatedDebitCardNumber(null);
		setColNameofCard(null);
		setColAuthorizedby(null);
		setColpassword(null);
		setBooRendercollectiondatatable(false);
	}

	public void getDealYearDetails() {
		LOGGER.info("Entering into getDealYear method");
		try {

			DealYearList = generalService.getDealYear(new Date());
			if (DealYearList != null) {
				if (getUserDealYear() == null) {
					dealYear = DealYearList.get(0).getFinancialYear().toString();
					dealYearId = DealYearList.get(0).getFinancialYearID();
					setDealYearId(dealYearId);
					setDealYear(dealYear);
				} else {
					setDealYear(getUserDealYear());
					setDealYearId(getUserDealYearId());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("Exit into getDealYear method");
	}

	public void updatespCharges() {
		setCalNetAmountPaid(getSpCharges());
		setFocus(false);
	}

	public void view() {

		if(getTransferNo() != null){
			if(getApplicationFinancialYear() != null && getApplicationNo() != null){
				setCount(0);
				count++;
				LOGGER.info("Entering into view Method");
				LOGGER.info("Trsansfer No " + getTransferNo());
				LOGGER.info("exit into view Method");
				setCalNetAmountPaid(null);
				setColCash(null);
				setColpaymentmodeId(null);
				setSpCharges(null);
				coldatatablevalues.clear();
				setToalUsedAmount(null);
				setTotalbalanceAmount(null);
				setTotalrefundAmount(null);
				setColBankCode(null);
				setColCardNo(null);
				setPopulatedDebitCardNumber(null);
				setColNameofCard(null);
				setColAuthorizedby(null);
				setColpassword(null);
				setBooRendercollectiondatatable(false);


				setBooRenderFirstPanel(false);
				setBooRenderPaymentDetails(false);
				setBooRenderDenaminationDetails(false);
				setBooRenderCollection(true);
				fetchPaymentMode();
				getLocalBankListforIndicator();
				LOGGER.info("getTransferAmount" + getTransferAmount());
				// this value only redirect next page
				// setCalNetAmountPaid(getTransferAmount());
				if (coldatatablevalues.size() != 0) {
					setBooRendercollectiondatatable(true);
					setBoRenderTotalAmountCalPanel(true);
				}
				// LOGGER.info("setCalNetAmountPaid" + getCalNetAmountPaid());
				LOGGER.info("Exit into view Method");
			}else{
				setErrorMessage("Application Document Number and Year not Available");
				RequestContext.getCurrentInstance().execute("csp.show();");
				return;
			}
		}else{
			setErrorMessage("Please Enter Transaction Document Number");
			RequestContext.getCurrentInstance().execute("csp.show();");
			return;
		}
	}

	// to check null
	private String nullCheck(String custname) {
		return custname == null ? "" : custname;
	}

	private String customerFullName;

	public String getCustomerFullName() {
		return customerFullName;
	}

	public void setCustomerFullName(String customerFullName) {
		this.customerFullName = customerFullName;
	}

	@Autowired
	ICompanyMasterservice iCompanyMasterservice;

	/*public void fetchDetails() {
		try {
			LOGGER.info("Entering into fetchDetails Method");
			LOGGER.info("Transfer No " + getTransferNo());
			LOGGER.info("Application no " + getApplicationNo());
			LOGGER.info(dealYear);
			if (getTransferNo() == null || getTransferNo().equals("")) {
				// please enter trnx number
			} else {
				// fetching trnx details
				beanTransaction = stopPaymentService.getRemittanceTransactionDetailsfromView(getTransferNo(), session.getCountryId(), new BigDecimal(dealYear), session.getCompanyId());

				// need to check with V_TRANSFER if stop status is S.
				// company code
				BigDecimal companyCode = null;
				List<CompanyMasterDesc> lstcompanymaster = iCompanyMasterservice.viewById(session.getCompanyId());
				if(lstcompanymaster.size() != 0){
					CompanyMasterDesc companycode = lstcompanymaster.get(0);
					if(companycode.getFsCompanyMaster().getCompanyCode() != null){
						companyCode = companycode.getFsCompanyMaster().getCompanyCode();

						Boolean stopPaymentStatus = stopPaymentService.checkTransferForStopPayment(companyCode, new BigDecimal(Constants.DOCUMENT_CODE_FOR_REMITTANCE_TRANSACTION), new BigDecimal(dealYear), getTransferNo());

						if(stopPaymentStatus){
							setTransferNo(null);
							RequestContext.getCurrentInstance().execute("stopPaymentDone.show();");
							return;
						}
					}
				}

				// validations
				if (beanTransaction != null) {
					LOGGER.info("Transaction type is " + beanTransaction.getTranscationStatus());
					if (beanTransaction.getTranscationStatus() != null && !(beanTransaction.getTranscationStatus().trim().equals(""))) {
						if (beanTransaction.getTranscationStatus().equalsIgnoreCase(Constants.TRANSACTION_STATUS_FOR_CASH_CODE)) {
							LOGGER.info("refundover message");
							setErrorMessage("Refund over. Document year " + beanTransaction.getDocumentFinancialYear() + " Document No " + beanTransaction.getDocumentNo());
							RequestContext.getCurrentInstance().execute("csp.show();");
							clear();
							return;
						} else if (beanTransaction.getTranscationStatus().equalsIgnoreCase(Constants.TRANSACTION_STATUS_FOR_STOP_PAYMENT_CODE)) {
							setErrorMessage("Stop payment processed. Document year " + beanTransaction.getDocumentFinancialYear() + " Document No " + beanTransaction.getDocumentNo());
							RequestContext.getCurrentInstance().execute("csp.show();");
							clear();
							return;
						} else if (beanTransaction.getTranscationStatus().equalsIgnoreCase(Constants.TRANSACTION_STATUS_FOR_STOP_REFUND_OVER_CODE)) {
							setErrorMessage("Already paid. Document year " + beanTransaction.getDocumentFinancialYear() + " Document No " + beanTransaction.getDocumentNo());
							RequestContext.getCurrentInstance().execute("csp.show();");
							clear();
							return;
						}else {
							fetchDataFromRemitView();
						}
					} else {
						fetchDataFromRemitView();
					}
					LOGGER.info("exit into fetchDetails Method");
				} else {

					HashMap<String, Object> fetchTransferToRemits = stopPaymentService.fetchTransferForStopPayment(companyCode, new BigDecimal(Constants.DOCUMENT_CODE_FOR_REMITTANCE_TRANSACTION), new BigDecimal(dealYear), getTransferNo(),Constants.StopPaymentForm);

					if((String)fetchTransferToRemits.get("ERRMSG") != null && !((String) fetchTransferToRemits.get("ERRMSG")).equalsIgnoreCase("")){

						setTransferNo(null);
						setErrorMessage((String) fetchTransferToRemits.get("ERRMSG"));
						RequestContext.getCurrentInstance().execute("csp.show();");
						clear();
						return;

					}else{

						if((RemittanceTxnOLDView)fetchTransferToRemits.get("TRANSFER") != null){

							RemittanceTxnOLDView remitOldTrnx = (RemittanceTxnOLDView) fetchTransferToRemits.get("TRANSFER");
							if(remitOldTrnx != null){
								fetchDataFromOLDTransferRemitView(remitOldTrnx);
							}else{
								setTransferNo(null);
								RequestContext.getCurrentInstance().execute("datanotfound.show();");
								clear();
							}
						}else{
							setTransferNo(null);
							RequestContext.getCurrentInstance().execute("datanotfound.show();");
							clear();
						}
					}
				}
			}
		} catch (Exception e) {
			setErrorMessage("Exception occurred "+ e);
			RequestContext.getCurrentInstance().execute("csp.show();");
			return;
		}
	}*/
	
	public void fetchDetails() {
		try {
			LOGGER.info("Entering into fetchDetails Method");
			LOGGER.info("Transfer No " + getTransferNo());
			LOGGER.info("Application no " + getApplicationNo());
			LOGGER.info(dealYear);
			if (getTransferYear() != null && getTransferNo() != null && getTransferYear().compareTo(BigDecimal.ZERO) != 0 && getTransferNo().compareTo(BigDecimal.ZERO) != 0) {


				beanTransaction = stopPaymentService.getRemittanceTransactionDetailsfromView(getTransferNo(), session.getCountryId(), getTransferYear(), session.getCompanyId(),new BigDecimal(Constants.DOCUMENT_CODE_FOR_REMITTANCE_TRANSACTION));
				
				// if VW_REMITTANCE_TRANSACTION list not available old trnx to Migrate from Old Emos
				if (beanTransaction == null) {
					
					// company code
					BigDecimal companyCode = null;
					List<CompanyMasterDesc> lstcompanymaster = iCompanyMasterservice.viewById(session.getCompanyId());
					if(lstcompanymaster.size() != 0){
						CompanyMasterDesc companycode = lstcompanymaster.get(0);
						if(companycode.getFsCompanyMaster().getCompanyCode() != null){
							companyCode = companycode.getFsCompanyMaster().getCompanyCode();
						}
					}
					
					HashMap<String, Object> fetchTransferToRemits = stopPaymentService.fetchTransferForStopPayment(companyCode, new BigDecimal(Constants.DOCUMENT_CODE_FOR_REMITTANCE_TRANSACTION), getTransferYear(), getTransferNo(),Constants.StopPaymentForm);

					if((String)fetchTransferToRemits.get("ERRMSG") != null && !((String) fetchTransferToRemits.get("ERRMSG")).equalsIgnoreCase("")){
						setTransferNo(null);
						setErrorMessage((String) fetchTransferToRemits.get("ERRMSG"));
						RequestContext.getCurrentInstance().execute("csp.show();");
						clear();
						return;
					}else{
						beanTransaction = stopPaymentService.getRemittanceTransactionDetailsfromView(getTransferNo(), session.getCountryId(), getTransferYear(), session.getCompanyId(),new BigDecimal(Constants.DOCUMENT_CODE_FOR_REMITTANCE_TRANSACTION));
					}
				}
			 
				// validations
				if (beanTransaction != null) {
					LOGGER.info("Transaction type is " + beanTransaction.getTranscationStatus());
					if (beanTransaction.getTranscationStatus() != null && !(beanTransaction.getTranscationStatus().trim().equals(""))) {
						if (beanTransaction.getTranscationStatus().equalsIgnoreCase(Constants.TRANSACTION_STATUS_FOR_CASH_CODE)) {
							LOGGER.info("refundover message");
							setErrorMessage("Refund over. Document year " + beanTransaction.getDocumentFinancialYear() + " Document No " + beanTransaction.getDocumentNo());
							RequestContext.getCurrentInstance().execute("csp.show();");
							clear();
							return;
						} else if (beanTransaction.getTranscationStatus().equalsIgnoreCase(Constants.TRANSACTION_STATUS_FOR_STOP_PAYMENT_CODE)) {
							setErrorMessage("Stop payment processed. Document year " + beanTransaction.getDocumentFinancialYear() + " Document No " + beanTransaction.getDocumentNo());
							RequestContext.getCurrentInstance().execute("csp.show();");
							clear();
							return;
						} else if (beanTransaction.getTranscationStatus().equalsIgnoreCase(Constants.TRANSACTION_STATUS_FOR_STOP_REFUND_OVER_CODE)) {
							setErrorMessage("Already paid. Document year " + beanTransaction.getDocumentFinancialYear() + " Document No " + beanTransaction.getDocumentNo());
							RequestContext.getCurrentInstance().execute("csp.show();");
							clear();
							return;
						}else {
							fetchDataFromRemitView();
						}
					} else {
						fetchDataFromRemitView();
					}
					LOGGER.info("exit into fetchDetails Method");
				} else {
					setDealYear( null);
					RequestContext.getCurrentInstance().execute("datanotfound.show();");
					clear();
				}
			}
		} catch (Exception e) {
			setErrorMessage("Exception occurred "+ e);
			RequestContext.getCurrentInstance().execute("csp.show();");
			return;
		}
	}

	// fetch details
	public void fetchDataFromRemitView(){
		BigDecimal cashRemittenceId = stopPaymentCollectionService.getRemittanceId(session.getLanguageId(), Constants.CASH_PRODUCT);
		BigDecimal wuBankCode = stopPaymentService.getWUbankId(Constants.TRANSACTION_STATUS_FOR_WESTERN_UNION);
		LOGGER.info("*********WU code " + wuBankCode);
		if (wuBankCode != null && wuBankCode.equals(beanTransaction.getBankId())) {
			RequestContext.getCurrentInstance().execute("wu.show();");
			clear();
		} else if (beanTransaction.getRemittanceModeId() != null && cashRemittenceId != null && beanTransaction.getRemittanceModeId().equals(cashRemittenceId)) {
			setErrorMessage("Cash Product not allowed to process. Document year " + beanTransaction.getDocumentFinancialYear() + " Document No " + beanTransaction.getDocumentNo());
			RequestContext.getCurrentInstance().execute("csp.show();");
			clear();
		} else {
			LOGGER.info("Docment No " + beanTransaction.getDocumentNo());
			LOGGER.info("" + beanTransaction.getDebitAccountNo());
			LOGGER.info(beanTransaction.getCustomerId());
			customer = icustomerRegistrationService.getCustomerInfo(beanTransaction.getCustomerId()).get(0);
			setCustomerCode(beanTransaction.getCustomerReference());
			setCustomerId( beanTransaction.getCustomerId());
			if (null != customer) {
				setCustomerName(nullCheck(customer.getFirstName()) + " " + nullCheck(customer.getMiddleName()) + " " + nullCheck(customer.getLastName()));
				setCustomerFirstName(customer.getFirstName());
				setTelephoneNo(customer.getMobile());
				setCustomerFullName(nullCheck(customer.getFirstName()) + " " + nullCheck(customer.getMiddleName()) + " " + nullCheck(customer.getLastName()));
			}
			setCustomerNameForReport(getCustomerFullName());
			setTransNumForReport(getTransferNo());
			String productName = stopPaymentCollectionService.getProductName(beanTransaction.getBankId(), beanTransaction.getRemittanceModeId(), beanTransaction.getDeliveryModeId(), beanTransaction.getApplicationCountryId(), beanTransaction.getForeignCurrencyId());
			LOGGER.info("productName" + productName);
			setRemittanceTransactionId(beanTransaction.getRemittanceTransactionId());
			List<RemittanceTranxBenificiary> benificiaryList = stopPaymentService.viewDetailsTranxBeneficiary(beanTransaction.getRemittanceTransactionId());
			if (benificiaryList.size() > 0) {
				// setBeneficiary(beneficiary);
				setBeneficiary(benificiaryList.get(0).getBeneficiaryName()  + " " + "A/C No:" + benificiaryList.get(0).getBeneficiaryAccountNo() + ", " + benificiaryList.get(0).getBeneficiaryBank() + " "
						+ benificiaryList.get(0).getBeneficiaryBranch());
				setPayableBranch(benificiaryList.get(0).getBeneficiaryBranch());
				//setPayableBank(benificiaryList.get(0).getBeneficiaryBank());
				setBankForDisplay(benificiaryList.get(0).getBeneficiaryBank());
				setBeneBankName(benificiaryList.get(0).getBeneficiaryBank());
				setBeneBranchName(benificiaryList.get(0).getBeneficiaryBranch());
			}
			setApplicationFinancialYear(beanTransaction.getApplicationFinancialYear());
			setApplicationNo(beanTransaction.getApplicationDocumentNo());
			setCustomerReference(beanTransaction.getCustomerReference());
			setProductName(productName);
			//setProductStatus(getTransactionStatus(beanTransaction.getTranscationStatus()));
			setTransferAmount(beanTransaction.getForeignTransactionAmount());
			setReceiptDate(beanTransaction.getDocumentDate());
			setPaymentDate(new Date());
			getDealYearDetails();
			String docNo = getDocumentSerialID(Constants.Yes);
			if( docNo.equalsIgnoreCase("0")){
				// if docNo is zero
			}else{
				setReceiptNo(new BigDecimal(docNo));
			}
			setForgienCurrencyId(beanTransaction.getForeignCurrencyId());
			setLocalCurrencyId(beanTransaction.getLocalTransactionCurrencyId());
			setLocalTransactionAmount(beanTransaction.getLocalTransactionAmount());
			setForgienTransactionAmount(beanTransaction.getForeignTransactionAmount());
			setExchangeRate(beanTransaction.getExchangeRateApplied());
			setBankBranchId(beanTransaction.getBankBranchId());
			setBranchName(beanTransaction.getCountryBranchName());

			setSaleAmount(beanTransaction.getForeignTransactionAmount());
			setCommission(beanTransaction.getLocalCommissionAmount());
			setCharges(beanTransaction.getLocalChargeAmount());
			setDeliveryCharges(beanTransaction.getLocalDeliveryAmount());
			setPurchaseAmount(beanTransaction.getLocalTransactionAmount());
			setNetAmount(beanTransaction.getLocalNetTransactionAmount());

		}

	}

	// fetch details
	/*public void fetchDataFromOLDTransferRemitView(RemittanceTxnOLDView remitOldTrnx){
		BigDecimal cashRemittenceId = stopPaymentCollectionService.getRemittanceId(session.getLanguageId(), Constants.CASH_PRODUCT);
		BigDecimal wuBankCode = stopPaymentService.getWUbankId(Constants.TRANSACTION_STATUS_FOR_WESTERN_UNION);
		LOGGER.info("*********WU code " + wuBankCode);
		if (wuBankCode != null && wuBankCode.equals(remitOldTrnx.getBankId())) {
			// setErrorMessage("Transaction processed by Westion
			// Union.");
			RequestContext.getCurrentInstance().execute("wu.show();");
			clear();
		} else if (remitOldTrnx.getRemittanceModeId() != null && cashRemittenceId != null && remitOldTrnx.getRemittanceModeId().equals(cashRemittenceId)) {
			setErrorMessage("Cash Product not allowed to process. Document year " + remitOldTrnx.getDocumentFinanceYear() + " Document No " + remitOldTrnx.getDocumentNumber());
			RequestContext.getCurrentInstance().execute("csp.show();");
			clear();
		} else {
			LOGGER.info("Docment No " + remitOldTrnx.getDocumentNumber());
			LOGGER.info("" + remitOldTrnx.getBeneficaryAccountNumber());
			LOGGER.info(remitOldTrnx.getCustomerId());
			customer = icustomerRegistrationService.getCustomerInfo(remitOldTrnx.getCustomerId()).get(0);
			setCustomerCode(remitOldTrnx.getCustomerReference());
			setCustomerId( remitOldTrnx.getCustomerId());
			if (null != customer) {
				setCustomerName(nullCheck(customer.getFirstName()) + " " + nullCheck(customer.getMiddleName()) + " " + nullCheck(customer.getLastName()));
				setCustomerFirstName(customer.getFirstName());
				setTelephoneNo(customer.getMobile());
				setCustomerFullName(nullCheck(customer.getFirstName()) + " " + nullCheck(customer.getMiddleName()) + " " + nullCheck(customer.getLastName()));
			}
			setCustomerNameForReport(getCustomerFullName());
			setTransNumForReport(getTransferNo());
			String productName = stopPaymentCollectionService.getProductName(remitOldTrnx.getBankId(), remitOldTrnx.getRemittanceModeId(), remitOldTrnx.getDeliveryModeId(), remitOldTrnx.getApplicationCountryId(), remitOldTrnx.getForeignCurrencyId());
			LOGGER.info("productName" + productName);
			setRemittanceTransactionId(remitOldTrnx.getRemittanceTransactionId());
			
			setBeneficiary(remitOldTrnx.getBeneficaryName()  + " " + "A/C No:" + remitOldTrnx.getBeneficaryAccountNumber() + ", " + remitOldTrnx.getBeneficaryBankName() + " "
					+ remitOldTrnx.getBeneficaryBranchName());
			setPayableBranch(remitOldTrnx.getBeneficaryBranchName());
			setBankForDisplay(remitOldTrnx.getBeneficaryBankName());
			setBeneBankName(remitOldTrnx.getBeneficaryBankName());
			setBeneBranchName(remitOldTrnx.getBeneficaryBranchName());
			
			setApplicationNo(remitOldTrnx.getApplicationdocumentNo());
			setCustomerReference(remitOldTrnx.getCustomerReference());
			setProductName(productName);
			//setProductStatus(getTransactionStatus(beanTransaction.getTranscationStatus()));
			setTransferAmount(remitOldTrnx.getForeignTransactionAmount());
			setReceiptDate(remitOldTrnx.getDocumentDate());
			setPaymentDate(new Date());
			String docNo=getDocumentSerialID(Constants.Yes);
			if( docNo.equalsIgnoreCase("0")){

			}else{
				setReceiptNo(new BigDecimal(docNo));
			}
			setForgienCurrencyId(remitOldTrnx.getForeignCurrencyId());
			setLocalCurrencyId(remitOldTrnx.getLocalTranxCurrencyId());
			setLocalTransactionAmount(remitOldTrnx.getLocalTranxAmount());
			setForgienTransactionAmount(remitOldTrnx.getForeignTransactionAmount());
			setExchangeRate(remitOldTrnx.getExchangeRateApplied());
			setBankBranchId(remitOldTrnx.getBankBranchId());
			setBranchName(remitOldTrnx.getCountryBranchName());

			setSaleAmount(remitOldTrnx.getForeignTransactionAmount());
			setCommission(remitOldTrnx.getLocalCommisionAmount());
			setCharges(remitOldTrnx.getLocalChargeAmount());
			setDeliveryCharges(remitOldTrnx.getLocalDeliveryAmount());
			setPurchaseAmount(remitOldTrnx.getLocalTranxAmount());
			setNetAmount(remitOldTrnx.getLocalNetTranxAmount());

		}

	}*/

	public void clickOnExit() throws IOException {
		/*if (session.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}*/
		setFocus(true);
		String roleNameDesc=iPersonalRemittanceService.toFetchRoleName(new BigDecimal(session.getRoleId()));
		String roleName = roleNameDesc.trim();
		if (roleName.equalsIgnoreCase(Constants.USER_ROLE_BRANCHSTAFF)) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}else if (roleName.equalsIgnoreCase(Constants.USER_ROLE_BRANCH_MANAGER) || roleName.equalsIgnoreCase(Constants.USER_ROLE_MANAGER)) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}else if (roleName.equalsIgnoreCase(Constants.USER_ROLE_CORPORATE)) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/corporatehome.xhtml");
		}else{
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}
	}

	public String getTransactionStatus(String status) {
		String returnStatus = null;
		if (null == status) {
			returnStatus = "Not Cleared";
		}
		return returnStatus;
	}


	private void denaMinLstData() {
		lstData.clear();
		ArrayList<ForeignLocalCurrencyDataTable> localLstData = new ArrayList<ForeignLocalCurrencyDataTable>();
		localLstData.clear();
		if (localLstData.size() == 0) {
			List<CurrencyWiseDenomination> currencyWiseDenolst = iPersonalRemittanceService.getCurrencyDenominations(new BigDecimal(session.getCurrencyId()), session.getCountryId());
			int serial = 1;
			for (CurrencyWiseDenomination currencyDenObj : currencyWiseDenolst) {
				ForeignLocalCurrencyDataTable forLocalCurrencyDtObj = new ForeignLocalCurrencyDataTable();
				forLocalCurrencyDtObj.setSerial(serial);
				forLocalCurrencyDtObj.setItem(currencyDenObj.getDenominationAmount());
				forLocalCurrencyDtObj.setQty("");
				forLocalCurrencyDtObj.setPrice("");
				forLocalCurrencyDtObj.setDenominationId(currencyDenObj.getDenominationId());
				forLocalCurrencyDtObj.setCurrencyId(currencyDenObj.getExCurrencyMaster().getCurrencyId());
				forLocalCurrencyDtObj.setDenominationDesc(currencyDenObj.getDenominationDesc());
				forLocalCurrencyDtObj.setDenominationAmount(currencyDenObj.getDenominationAmount());
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
		setDenomtotalCash(GetRound.roundBigDecimal(new BigDecimal(totalSum), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(session.getCurrencyId()))));
		// setCollectedAmount(getDenomtotalCash());
		setPayNetPaidAmount(getCalNetAmountPaid());
		setPayPaidAmount(GetRound.roundBigDecimal(getToalUsedAmount(), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(session.getCurrencyId()))));
		setPayRefund(getPayPaidAmount().subtract(getPayNetPaidAmount()));
		if (getPayRefund().compareTo(BigDecimal.ZERO) == 0) {
			setNextOrSaveButton(Constants.Save);
		} else {
			setNextOrSaveButton(Constants.Next);
		}
		setLstData(localLstData); // Adding denomication here
		if (localLstData.size() != 0) {
			setDenominationchecking(Constants.DenominationAvailable);
		} else {
			setDenominationchecking(Constants.DenominationNotAvailable);
		}
	}

	public void onCellEdit(ForeignLocalCurrencyDataTable foreignLocalCurrencyDataTable) {/*
	 * LOGGER
	 * .
	 * info(
	 * "Entering into onCellEdit method"
	 * );
	 * BigDecimal
	 * totalcashAmount
	 * =
	 * foreignLocalCurrencyDataTable
	 * .
	 * getItem
	 * (
	 * )
	 * .
	 * multiply
	 * (
	 * new
	 * BigDecimal
	 * (
	 * foreignLocalCurrencyDataTable
	 * .
	 * getQty
	 * (
	 * )
	 * )
	 * )
	 * ;
	 * LOGGER
	 * .
	 * info
	 * (
	 * totalcashAmount
	 * ==
	 * null
	 * )
	 * ;
	 * LOGGER
	 * .
	 * info
	 * (
	 * totalcashAmount
	 * )
	 * ;
	 * foreignLocalCurrencyDataTable
	 * .
	 * setPrice
	 * (
	 * totalcashAmount
	 * .
	 * toPlainString
	 * (
	 * )
	 * )
	 * ;
	 * double
	 * totalSum
	 * =
	 * 0;
	 * Responsible
	 * to
	 * calculate
	 * sum
	 * of
	 * entered
	 * cash
	 * amount
	 * for
	 * (ForeignLocalCurrencyDataTable
	 * element
	 * :
	 * lstData)
	 * {
	 * if
	 * (element
	 * .
	 * getPrice
	 * (
	 * )
	 * .
	 * length
	 * (
	 * )
	 * !=
	 * 0)
	 * {
	 * totalSum
	 * =
	 * totalSum
	 * +
	 * Double
	 * .
	 * parseDouble
	 * (
	 * element
	 * .
	 * getPrice
	 * (
	 * )
	 * )
	 * ;
	 * }
	 * }
	 * LOGGER
	 * .
	 * info
	 * (
	 * "totalSum"
	 * +
	 * totalSum
	 * )
	 * ;
	 * LOGGER
	 * .
	 * info
	 * (
	 * "getCashAmount"
	 * +
	 * getCashAmount
	 * (
	 * )
	 * )
	 * ;
	 * LOGGER
	 * .
	 * info
	 * (
	 * "getDenomtotalCash"
	 * +
	 * getDenomtotalCash
	 * (
	 * )
	 * )
	 * ;
	 * LOGGER
	 * .
	 * info
	 * (
	 * "getDenomtotalCashreceived"
	 * +
	 * getDenomtotalCashreceived
	 * (
	 * )
	 * )
	 * ;
	 * setDenomtotalCash
	 * (
	 * new
	 * BigDecimal
	 * (
	 * totalSum
	 * )
	 * .
	 * setScale
	 * (
	 * 3
	 * ,
	 * RoundingMode
	 * .
	 * HALF_UP
	 * )
	 * )
	 * ;
	 * setPayRefund
	 * (
	 * getDenomtotalCash
	 * (
	 * )
	 * .
	 * subtract
	 * (
	 * getCashAmount
	 * (
	 * )
	 * )
	 * )
	 * ;
	 * setPayPaidAmount
	 * (
	 * getDenomtotalCash
	 * (
	 * )
	 * )
	 * ;
	 * setPayNetPaidAmount
	 * (
	 * getDenomtotalCashreceived
	 * (
	 * )
	 * )
	 * ;
	 * LOGGER
	 * .
	 * info(
	 * "Exit into onCellEdit method"
	 * );
	 */
		BigDecimal qty = null;
		if (foreignLocalCurrencyDataTable.getQty() == "" && foreignLocalCurrencyDataTable.getQty() != null) {
			qty = new BigDecimal(0);
			System.out.println("qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq ZEROOOOOOOOOOOOOOOO");
		} else {
			System.out.println("foreignLocalCurrencyDataTable.getQty()" + foreignLocalCurrencyDataTable.getQty());
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
			totalcashAmount = foreignLocalCurrencyDataTable.getItem().multiply(qty);
		} catch (Exception e) {
			System.out.println("Exceptionnnnnnnnnnnnn---------------------->QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ" + e);
		}
		System.out.println("@@@@@@@@@@@@@@@@@" + foreignLocalCurrencyDataTable.getQty() == null);
		System.out.println("#################" + foreignLocalCurrencyDataTable.getQty().equals(""));
		if (foreignLocalCurrencyDataTable.getQty().equals("")) {
			foreignLocalCurrencyDataTable.setQty("");
			// added by rabil for clear if blank
			foreignLocalCurrencyDataTable.setPrice("");
		}
		if (totalcashAmount != null && totalcashAmount.doubleValue() != 0.0) {
			try {
				foreignLocalCurrencyDataTable.setPrice(GetRound.roundBigDecimal(totalcashAmount, foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(session.getCurrencyId()))).toPlainString());
			} catch (Exception e) {
				System.out.println("Exceptionnnnnnnnnnnnn---------------------->11111" + e);
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
		BigDecimal totalDenoCash = getDenomtotalCash();
		if (getCashAmount().compareTo(totalSum) < 0) {
			totalSum = BigDecimal.ZERO;
			foreignLocalCurrencyDataTable.setQty("");
			foreignLocalCurrencyDataTable.setPrice("");
			for (ForeignLocalCurrencyDataTable element : lstData) {
				if (element.getPrice().length() != 0) {
					totalSum = totalSum.add(new BigDecimal(element.getPrice()));
				}
			}
			setDenomtotalCash(GetRound.roundBigDecimal(totalSum, foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(session.getCurrencyId()))));
			setCollectedAmount(getDenomtotalCash());
			RequestContext.getCurrentInstance().execute("cleardenominationexceed.show();");
			setDataTableClear(foreignLocalCurrencyDataTable);
			foreignLocalCurrencyDataTable.setQty("");
			return;
		} else {
			try {
				setDenomtotalCash(GetRound.roundBigDecimal(totalSum, foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(session.getCurrencyId()))));
			} catch (Exception e) {
				System.out.println("Exceptionnnnnnnnnnnnn---------------------->33333333" + e);
			}
			setCollectedAmount(getDenomtotalCash());
		}
	}

	public void clearDataTableClearDenomination() {
		if (getDataTableClear() != null) {
			ForeignLocalCurrencyDataTable foreignLocalCurrencyDataTable = getDataTableClear();
			System.out.println("foreignLocalCurrencyDataTable" + foreignLocalCurrencyDataTable);
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../stoppayment/StopPaymentrequestwithCollection.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	ForeignLocalCurrencyDataTable dataTableClear = new ForeignLocalCurrencyDataTable();

	public ForeignLocalCurrencyDataTable getDataTableClear() {
		return dataTableClear;
	}

	public void setDataTableClear(ForeignLocalCurrencyDataTable dataTableClear) {
		this.dataTableClear = dataTableClear;
	}

	private ArrayList<ForeignLocalCurrencyDataTable> lstRefundData = new ArrayList<ForeignLocalCurrencyDataTable>();

	public ArrayList<ForeignLocalCurrencyDataTable> getLstRefundData() {
		return lstRefundData;
	}

	public void setLstRefundData(ArrayList<ForeignLocalCurrencyDataTable> lstRefundData) {
		this.lstRefundData = lstRefundData;
	}

	public void onRefundCellEdit(ForeignLocalCurrencyDataTable foreignLocalCurrencyDataTable) {
		int quantity = 0;
		if ((foreignLocalCurrencyDataTable.getQty() == "") || (foreignLocalCurrencyDataTable.getQty() != null && foreignLocalCurrencyDataTable.getQty().equalsIgnoreCase("0"))) {
			quantity = 0;
			BigDecimal totalSum = BigDecimal.ZERO;
			foreignLocalCurrencyDataTable.setQty("");
			foreignLocalCurrencyDataTable.setPrice("");
			for (ForeignLocalCurrencyDataTable element : lstRefundData) {
				if (element.getPrice().length() != 0) {
					totalSum = totalSum.add(new BigDecimal(element.getPrice()));
				}
			}
			setCollectedRefundAmount(GetRound.roundBigDecimal(totalSum, foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(session.getCurrencyId()))));
		} else {
			try {
				quantity = Integer.parseInt(foreignLocalCurrencyDataTable.getQty());
				if (foreignLocalCurrencyDataTable.getStock() >= quantity && quantity != 0) {
					BigDecimal totalcashAmount = foreignLocalCurrencyDataTable.getItem().multiply(new BigDecimal(foreignLocalCurrencyDataTable.getQty() == "" ? "0" : foreignLocalCurrencyDataTable.getQty()));
					if (foreignLocalCurrencyDataTable.getQty().equals("")) {
						foreignLocalCurrencyDataTable.setQty("");
						// added by rabil for clear if blank
						foreignLocalCurrencyDataTable.setPrice("");
					}
					if (totalcashAmount != null && totalcashAmount.doubleValue() != 0) {
						foreignLocalCurrencyDataTable.setPrice(GetRound.roundBigDecimal(totalcashAmount, foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(session.getCurrencyId()))).toPlainString());
					} else {
						foreignLocalCurrencyDataTable.setPrice("");
					}
					BigDecimal totalSum = BigDecimal.ZERO;
					/* Responsible to calculate sum of entered cash amount */
					for (ForeignLocalCurrencyDataTable element : lstRefundData) {
						if (element.getPrice().length() != 0) {
							totalSum = totalSum.add(new BigDecimal(element.getPrice()));
						}
					}
					BigDecimal totalDenoCash = getDenomtotalCash();
					if (getRefundAmount().compareTo(totalSum) < 0) {
						totalSum = BigDecimal.ZERO;
						foreignLocalCurrencyDataTable.setQty("");
						foreignLocalCurrencyDataTable.setPrice("");
						for (ForeignLocalCurrencyDataTable element : lstRefundData) {
							if (element.getPrice().length() != 0) {
								totalSum = totalSum.add(new BigDecimal(element.getPrice()));
							}
						}
						setCollectedRefundAmount(GetRound.roundBigDecimal(totalSum, foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(session.getCurrencyId()))));
						RequestContext.getCurrentInstance().execute("clearrefunddenominationexceed.show();");
						return;
					} else {
						setCollectedRefundAmount(GetRound.roundBigDecimal(totalSum, foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(session.getCurrencyId()))));
					}
				} else {
					RequestContext.getCurrentInstance().execute("stockNotAvailable.show();");
					BigDecimal totalSum = BigDecimal.ZERO;
					foreignLocalCurrencyDataTable.setQty("");
					foreignLocalCurrencyDataTable.setPrice("");
					for (ForeignLocalCurrencyDataTable element : lstRefundData) {
						if (element.getPrice().length() != 0) {
							totalSum = totalSum.add(new BigDecimal(element.getPrice()));
						}
					}
					setCollectedRefundAmount(GetRound.roundBigDecimal(totalSum, foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(session.getCurrencyId()))));
				}
			} catch (Exception e) {
				System.out.println("Exception occured" + e);
				quantity = 0;
			}
		}
	}

	private BigDecimal collectedRefundAmount;

	public BigDecimal getCollectedRefundAmount() {
		return collectedRefundAmount;
	}

	public void setCollectedRefundAmount(BigDecimal collectedRefundAmount) {
		this.collectedRefundAmount = collectedRefundAmount;
	}

	// change by payment mode - cash and debit card
	public void changeofPaymentMode() {
		List<PaymentModeDesc> lstofPayment = ipaymentService.getPaymentDescLangList(new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId") : "1"));
		// Boolean checkCash = false;
		String paymentModedesc = null;
		String paymentModeCode = null;
		if (lstofPayment.size() != 0) {
			for (PaymentModeDesc paymentModeDesc : lstofPayment) {
				if ((getColpaymentmodeId() == null ? BigDecimal.ZERO : getColpaymentmodeId()).compareTo(paymentModeDesc.getPaymentMode().getPaymentModeId()) == 0) {
					paymentModedesc = paymentModeDesc.getLocalPaymentName();
					paymentModeCode = paymentModeDesc.getPaymentMode().getPaymentCode();
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
				List<PaymentMode> paymentModedetails = ipaymentService.getPaymentCheck(getColpaymentmodeCode());
				if (paymentModedetails.size() != 0) {
					// payment mode bank variables
					setColBankCode(null);
					setColCardNo(null);
					setPopulatedDebitCardNumber(null);
					setColCash(null);
					setColAuthorizedby(null);
					setColpassword(null);
					setColNameofCard(null);
					setColApprovalNo(null);
					// payment mode Cheque variables
					setColchequebankCode(null);
					setColChequeRef(null);
					setColChequeDate(null);
					setColChequeApprovalNo(null);
					
					// bank Transfer Bank Code
					setColBankTransferBankCode(null);
					
					if (getColpaymentmodeCode().equalsIgnoreCase(Constants.CashCode)) {
						setBooRenderColDebitCard(false);
						setBooRenderColCheque(false);
						setPaymentMode(Constants.CASHNAME);
						setBooRenderColBankTransfer(false);
					} else if (getColpaymentmodeCode().equalsIgnoreCase(Constants.KNETCode)) {
						setBooRenderColCheque(false);
						setBooRenderColDebitCard(true);
						setPaymentMode(Constants.KNET);
						setBooRenderColBankTransfer(false);
					} else if (getColpaymentmodeCode().equalsIgnoreCase(Constants.ChequeCode)) {
						setBooRenderColDebitCard(false);
						setBooRenderColCheque(true);
						setBooRenderColBankTransfer(false);
					} else if (getColpaymentmodeCode().equalsIgnoreCase(Constants.BankTransferCode)) {
						/**Added by Rabil on 13/12/2016 */
						setBooRenderColBankTransfer(true);
						setBooRenderColDebitCard(false);
						setBooRenderColCheque(false);
						
						
						/**commented by Rabil on 13/12/2016 */
						//setColpaymentmodeId(null);
						//RequestContext.getCurrentInstance().execute("checkPaymentModeService.show();");
						//return;
						
						
					} else {
						setColpaymentmodeId(null);
						setBooRenderColDebitCard(false);
						setBooRenderColCheque(false);
						System.out.println("Payment Mode Newly added");
						RequestContext.getCurrentInstance().execute("checkPaymentModeService.show();");
						return;
					}
				}
			} else {
				setBooRenderColDebitCard(false);
				setBooRenderColCheque(false);
				setBooRenderColBankTransfer(false);
			}
		}
	}

	// calculation of cash while entering
	public void checkcashcollection() {
		if(getSpCharges() != null && !(getSpCharges().compareTo(BigDecimal.ZERO)==0)){
			if (coldatatablevalues.size() != 0) {
				colamountKWD = new BigDecimal(0);
				for (PersonalRemittanceCollectionDataTable collectionlst : coldatatablevalues) {
					colamountKWD = colamountKWD.add(collectionlst.getColAmountDT());
					setColamountKWD(colamountKWD);
				}
				checkingKnetExtraAmount();
			} else {
				checkingKnetExtraAmount();
			}
		}else{
			setColCash(null);
			setErrorMessage("Please Enter the Stop Payment Charges");
			RequestContext.getCurrentInstance().execute("csp.show();");
			return;
		}
	}

	private BigDecimal colremittanceNo;
	private String colpaymentmodeCode;
	private String colApprovalNo;
	private BigDecimal colCardNoLength;
	private String colBankCode;
	/** Added  by Rabil */
	private boolean booColApprovalNo=false;
	private String knetTranId;
	private String knetIposReceipt;
	private String knetReceiptDate;
	private String knetReceiptTime;
	private boolean booRenderSingleDebit = true;
	private boolean booRenderMulDebit = false;
	private boolean booShowCashRoundingPanel = false;
	private boolean booRenderModifiedRoundData = false;




	public String getKnetTranId() {
		return knetTranId;
	}

	public void setKnetTranId(String knetTranId) {
		this.knetTranId = knetTranId;
	}

	public boolean isBooColApprovalNo() {
		return booColApprovalNo;
	}

	public void setBooColApprovalNo(boolean booColApprovalNo) {
		this.booColApprovalNo = booColApprovalNo;
	}

	public String getKnetIposReceipt() {
		return knetIposReceipt;
	}

	public void setKnetIposReceipt(String knetIposReceipt) {
		this.knetIposReceipt = knetIposReceipt;
	}

	public String getKnetReceiptDate() {
		return knetReceiptDate;
	}

	public void setKnetReceiptDate(String knetReceiptDate) {
		this.knetReceiptDate = knetReceiptDate;
	}

	public String getKnetReceiptTime() {
		return knetReceiptTime;
	}

	public void setKnetReceiptTime(String knetReceiptTime) {
		this.knetReceiptTime = knetReceiptTime;
	}

	public boolean isBooRenderModifiedRoundData() {
		return booRenderModifiedRoundData;
	}

	public void setBooRenderModifiedRoundData(boolean booRenderModifiedRoundData) {
		this.booRenderModifiedRoundData = booRenderModifiedRoundData;
	}

	public boolean isBooShowCashRoundingPanel() {
		return booShowCashRoundingPanel;
	}

	public void setBooShowCashRoundingPanel(boolean booShowCashRoundingPanel) {
		this.booShowCashRoundingPanel = booShowCashRoundingPanel;
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

	public BigDecimal getColremittanceNo() {
		return colremittanceNo;
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

	// Knet 5% calculation
	public void checkingKnetExtraAmount() {
		BigDecimal totalAmount = BigDecimal.ZERO;
		setErrcolCashExistsLimit(null);
		if (getColpaymentmodeCode() != null) {
			if (getColpaymentmodeCode().equalsIgnoreCase(Constants.CashCode)) {
				setColamountKWD(getColCash());
			} else if (getColpaymentmodeCode().equalsIgnoreCase(Constants.KNETCode)) {
				BigDecimal colKnetAmount = BigDecimal.ZERO;
				for (PersonalRemittanceCollectionDataTable collectionlst : coldatatablevalues) {
					if (collectionlst.getColpaymentmodeCode().equalsIgnoreCase(Constants.KNETCode)) {
						colKnetAmount = colKnetAmount.add(collectionlst.getColAmountDT());
					}
				}
				colKnetAmount = colKnetAmount.add(getColCash());
				BigDecimal percentage = new BigDecimal(5).divide(new BigDecimal(100));
				BigDecimal percentageAmount = percentage.multiply(getCalNetAmountPaid());
				totalAmount = GetRound.roundBigDecimal(percentageAmount.add(getCalNetAmountPaid()), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(session.getCurrencyId())));
				if (colKnetAmount.compareTo(totalAmount) <= 0) {
					setColamountKWD(getColCash());
				} else {
					setColCash(null);
					setErrcolCashExistsLimit(totalAmount);
					RequestContext.getCurrentInstance().execute("amountgreater.show();");
				}
			} else if (getColpaymentmodeCode().equalsIgnoreCase(Constants.ChequeCode)) {
				setColamountKWD(getColCash());
			} else if (getColpaymentmodeCode().equalsIgnoreCase(Constants.BankTransferCode)) {
				//setColamountKWD(getColCash());
				if(coldatatablevalues != null && coldatatablevalues.size() != 0){
					for (PersonalRemittanceCollectionDataTable collectionlst : coldatatablevalues) {
						colamountKWD = colamountKWD.add(collectionlst.getColAmountDT());
						setColamountKWD(colamountKWD);
					}
				}else{
					setColamountKWD(getColCash());
				}
				
			} else {
				System.out.println("Other Payment Mode Selected");
			}
		} else {
			setColamountKWD(getColCash());
		}
		/*
		 * String paymentDesc =
		 * ipaymentService.paymentModeDescription(getColpaymentmodeId(),new
		 * BigDecimal(sessionStateManage.isExists("languageId") ?
		 * sessionStateManage.getSessionValue("languageId") : "1")); if
		 * (paymentDesc != null) { if
		 * (paymentDesc.equalsIgnoreCase(Constants.CASHNAME)) {
		 * setColamountKWD(getColCash()); } else { BigDecimal percentage = new
		 * BigDecimal(5).divide(new BigDecimal(100)); BigDecimal
		 * percentageAmount = percentage.multiply(getCalNetAmountPaid());
		 * BigDecimal totalAmount = percentageAmount.add(getCalNetAmountPaid());
		 * if (getColCash().compareTo(totalAmount) <= 0) {
		 * setColamountKWD(getColCash()); } else { setColCash(null);
		 * RequestContext.getCurrentInstance().execute("amountgreater.show();");
		 * } } }
		 */
	}

	@Autowired
	ILoginService<T> iloginService;
	iCypherSecurity cypherSecurity = new CypherSecurityImpl();
	// verifying the password with authoried person
	/*
	 * public void verifyPassword() {
	 * 
	 * if(getSpCharges()==null ) {
	 * RequestContext.getCurrentInstance().execute("csp.show();");
	 * setErrorMessage("Please enter Stop Payment charges"); return; }
	 * 
	 * 
	 * BigDecimal paymentModeCashId =
	 * ipaymentService.fetchPaymodeMasterId(Constants.CASHNAME,new
	 * BigDecimal(session.isExists("languageId") ?
	 * session.getSessionValue("languageId") : "1"));
	 * 
	 * if(paymentModeCashId != null ){
	 * if(getColpaymentmodeId().compareTo(paymentModeCashId)==0){
	 * calculatingNetAmountDT(); }else{
	 * 
	 * if(getColAuthorizedby()!=null){ List<DebitAutendicationView> lstEmpLogin
	 * = new ArrayList<DebitAutendicationView>();
	 * 
	 * String userNames = getColAuthorizedby(); String authorname = new
	 * StringBuffer(userNames).reverse().toString().toUpperCase();
	 * 
	 * lstEmpLogin =
	 * iPersonalRemittanceService.getdebitAutendicationListByUserId(
	 * getColAuthorizedby(),getColpassword());
	 * 
	 * if(lstEmpLogin.size()!=0){
	 * 
	 * iCypherSecurity cypherSecurity2 = new CypherSecurityImpl(); String
	 * secretKey = getColpassword().toUpperCase(); StringBuffer secretKeys = new
	 * StringBuffer(secretKey).reverse();
	 * 
	 * String cyperpassword= cypherSecurity2.encodePassword(getColpassword(),
	 * secretKeys.toString()); setColpassword(getColpassword());
	 * setCyberPassword(cyperpassword);
	 * 
	 * checkingPaymentCardinDB();
	 * 
	 * }else{ setColpassword(null);
	 * RequestContext.getCurrentInstance().execute("passwordcheck.show();"); }
	 * }else{ checkingPaymentCardinDB(); }
	 * 
	 * } } }
	 */
	private String excheckCashLimitMessage = null;

	public String getExcheckCashLimitMessage() {
		return excheckCashLimitMessage;
	}

	public void setExcheckCashLimitMessage(String excheckCashLimitMessage) {
		this.excheckCashLimitMessage = excheckCashLimitMessage;
	}

	// verifying the password with authoried person
	public void verifyPassword() {
		String errorMessage;
		try {
			errorMessage = iPersonalRemittanceService.getExCheckCashLimitProcedure(session.getCountryId(), getCustomerId(), getColpaymentmodeId(), getColamountKWD());
			System.out.println("errorMessage :" + errorMessage);
			if (errorMessage != null && !errorMessage.equals("")) {
				setExcheckCashLimitMessage(errorMessage);
				RequestContext.getCurrentInstance().execute("exCheckCashLimit.show();");
			} else {
				setExcheckCashLimitMessage(null);
				if (getColpaymentmodeCode() != null) {
					List<PaymentMode> paymentModedetails = ipaymentService.getPaymentCheck(getColpaymentmodeCode());
					if (paymentModedetails.size() != 0) {
						if (getColpaymentmodeCode().equalsIgnoreCase(Constants.CashCode)) {
							calculatingNetAmountDT();
						} else if (getColpaymentmodeCode().equalsIgnoreCase(Constants.KNETCode)) {
							if (getColAuthorizedby() != null) {
								List<DebitAutendicationView> lstEmpLogin = new ArrayList<DebitAutendicationView>();
								String userNames = getColAuthorizedby();
								/*
								 * String authorname = new
								 * StringBuffer(userNames)
								 * .reverse().toString().toUpperCase();
								 * lstEmpLogin.addAll(loginService.
								 * getLoginInfoForEmployees(sessionStateManage.
								 * getCountryId(),
								 * getColAuthorizedby(),cypherSecurity.
								 * encodePassword(getColpassword(),
								 * authorname)));
								 */
								lstEmpLogin = iPersonalRemittanceService.getdebitAutendicationListByUserId(getColAuthorizedby(), getColpassword());
								if (lstEmpLogin.size() != 0) {
									/*
									 * String secretKey =
									 * getColpassword().toUpperCase();
									 * StringBuffer secretKeys = new
									 * StringBuffer(secretKey).reverse(); String
									 * cyperpassword =
									 * cypherSecurity2.encodePassword(
									 * getColpassword(),secretKeys.toString());
									 */
									// setColpassword(getColpassword());
									// setCyberPassword(cyperpassword);
									checkingPaymentCardinDB();
								} else {
									setColpassword(null);
									RequestContext.getCurrentInstance().execute("passwordcheck.show();");
								}
							} else {
								checkingPaymentCardinDB();
							}
						} else if (getColpaymentmodeCode().equalsIgnoreCase(Constants.ChequeCode)) {
							Boolean checkdata = checkingChequeDuplicateCheck();
							if (checkdata) {
								localbankListForBankCode = icustomerBankService.getCustomerLocalBankListFromView(session.getCountryId(), getColchequebankCode());
								addPaymentModerecord();
								if (coldatatablevalues.size() > 0) {
									for (PersonalRemittanceCollectionDataTable collectionlst : coldatatablevalues) {
										totalUamount = totalUamount.add(collectionlst.getColAmountDT());
									}
									setToalUsedAmount(totalUamount);
									if (getToalUsedAmount().compareTo(getCalNetAmountPaid()) > 0) {
										setTotalbalanceAmount(BigDecimal.ZERO);
										setTotalrefundAmount(getToalUsedAmount().subtract(getCalNetAmountPaid()));
									} else if (getToalUsedAmount().compareTo(getCalNetAmountPaid()) < 0) {
										setTotalbalanceAmount(getCalNetAmountPaid().subtract(getToalUsedAmount()));
										setTotalrefundAmount(BigDecimal.ZERO);
									} else {
										setTotalbalanceAmount(BigDecimal.ZERO);
										setTotalrefundAmount(BigDecimal.ZERO);
									}
								}
								setBooRendercollectiondatatable(true);
								clearingDetailAfterAdding();
							} else {
								RequestContext.getCurrentInstance().execute("chequerefexists.show();");
							}
						} else if (getColpaymentmodeCode().equalsIgnoreCase(Constants.BankTransferCode)) {
								//Boolean checkBT = checkingBankTransferDuplicateCheck();
								// blocking duplicate condition for bank Transfer based on user Requirement 
								Boolean checkBT = Boolean.TRUE;
								if(checkBT){
									localbankListForBankCode = icustomerBankService.getCustomerLocalBankListFromView(session.getCountryId(), getColBankTransferBankCode());
									addPaymentModerecord();
									if (coldatatablevalues.size() > 0) {
										for (PersonalRemittanceCollectionDataTable collectionlst : coldatatablevalues) {
											totalUamount = totalUamount.add(collectionlst.getColAmountDT());
										}
										setToalUsedAmount(totalUamount);
										if(getToalUsedAmount().compareTo(getCalNetAmountPaid())>0){
											setTotalbalanceAmount(BigDecimal.ZERO);
											setTotalrefundAmount(getToalUsedAmount().subtract(getCalNetAmountPaid()));
										}else if(getToalUsedAmount().compareTo(getCalNetAmountPaid())<0){
											setTotalbalanceAmount(getCalNetAmountPaid().subtract(getToalUsedAmount()));
											setTotalrefundAmount(BigDecimal.ZERO);
										}else{
											setTotalbalanceAmount(BigDecimal.ZERO);
											setTotalrefundAmount(BigDecimal.ZERO);
										}

									}
									setBooRendercollectiondatatable(true);
									clearingDetailAfterAdding();
								}else{
									
									setExceptionMessage("Bank Transfer Payment Mode Already Exists in DataTable");
									LOGGER.info("Bank Transfer Payment Mode Already Exists in DataTable");
									RequestContext.getCurrentInstance().execute("warningmsg.show();");
									return;
								}	
						} else {
							System.out.println("Other Payment Mode");
						}
					}
				} else {
					setBooRenderColDebitCard(false);
					setBooRenderColCheque(false);
					setBooRenderColBankTransfer(false);
				}
			}
		} catch (AMGException e) {
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("sqlexception.show();");
		}
	}

	// checking cheque data with datatable
	public Boolean checkingChequeDuplicateCheck() {
		Boolean checkCheque = false;
		int i = 0;
		if (getColchequebankCode() != null && coldatatablevalues.size() != 0) {
			for (PersonalRemittanceCollectionDataTable lstpaymentdata : coldatatablevalues) {
				i = 0;
				if (lstpaymentdata.getColBankCodeDT() != null) {
					if (lstpaymentdata.getColBankCodeDT().equalsIgnoreCase(getColchequebankCode())) {
						if (lstpaymentdata.getColchequeRefNo().equalsIgnoreCase(getColChequeRef())) {
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

	/*
	 * public void checkingPaymentCardinDB() { LOGGER.info(
	 * "Entering into checkingPaymentCardinDB method");
	 * 
	 * if(getPopulatedDebitCardNumber() != null){
	 * if(getPopulatedDebitCardNumber().compareTo(getColCardNo())==0){
	 * calculatingNetAmountDT(); }else{
	 * RequestContext.getCurrentInstance().execute("addDebitCard.show();"); }
	 * }else{
	 * RequestContext.getCurrentInstance().execute("addDebitCard.show();"); }
	 * 
	 * LOGGER.info("Exit into checkingPaymentCardinDB method"); }
	 */
	public void checkingPaymentCardinDB() {
		if (lstDebitCard.size() != 0) {
			int i = 0;
			for (CustomerBank lstDebit : lstDebitCard) {
				if (lstDebit.getDebitCard().equalsIgnoreCase(getColCardNo().toString())) {
					i = 1;
					break;
				} else {
					i = 0;
				}
			}
			if (i == 0) {
				// RequestContext.getCurrentInstance().execute("addDebitCard.show();");
				saveCustomerDetailsToCustomerBank();
			} else {
				calculatingNetAmountDT();
			}
		} else {
			if (getPopulatedDebitCardNumber() != null) {
				if (getPopulatedDebitCardNumber().compareTo(getColCardNo()) == 0) {
					calculatingNetAmountDT();
				} else {
					// RequestContext.getCurrentInstance().execute("addDebitCard.show();");
					saveCustomerDetailsToCustomerBank();
				}
			} else {
				// RequestContext.getCurrentInstance().execute("addDebitCard.show();");
				saveCustomerDetailsToCustomerBank();
			}
		}
	}

	int i;

	/** TO STORE THE NEW BENIFICARY CUSTOMER DETAILS TO CUSTOMER BANK **/
	public void saveCustomerDetailsToCustomerBank() {
		CustomerBank customerBank = new CustomerBank();
		customerBank.setCollectionMode(Constants.COLLECTIONMODE);
		if (localbankListForBankCode.size() != 0) {
			BankMaster bankMaster = new BankMaster();
			bankMaster.setBankId(localbankListForBankCode.get(0).getChequeBankId());
			customerBank.setFsBankMaster(bankMaster);
		}
		customerBank.setBankCode(getColBankCode()); // this is
		// fixed//generalService.getBankCode(getColBankid()));
		Customer customer = new Customer();
		customer.setCustomerId(getCustomerId());
		customerBank.setFsCustomer(customer);
		customerBank.setAuthorizedBy(getColAuthorizedby());
		customerBank.setAuthorizedDate(new Date());
		customerBank.setDebitCard(encryptionDescriptionService.getENCrypted(getColNameofCard(), getColCardNo().toString()));
		customerBank.setDebitCardName(getColNameofCard());
		customerBank.setPassword(getCyberPassword());
		customerBank.setIsActive(Constants.Yes);
		customerBank.setCreatedBy(session.getUserName());
		customerBank.setCreatedDate(new Date());
		customerBank.setCustomerReference(iglTransactionForADocument.getCustomeReference(getCustomerId()));
		// customerBank.setModifiedBy(null);
		// customerBank.setModifiedDate(null);
		icustomerBankService.save(customerBank);
		calculatingNetAmountDT();
		// RequestContext.getCurrentInstance().execute("locbankid.show();");
	}

	// to add values to data table in collection
	public void calculatingNetAmountDT() {
		int i = 0;
		BigDecimal paymentModeCashId = ipaymentService.fetchPaymodeMasterId(Constants.CASHNAME, new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId") : "1"));
		if (paymentModeCashId != null) {
			boolean flag = true;
			setBooRendercollectiondatatable(true);
			setBoRenderTotalAmountCalPanel(true);
			if (coldatatablevalues.size() != 0) {
				for (PersonalRemittanceCollectionDataTable collectionlst : coldatatablevalues) {
					i = 0;
					if (collectionlst.getColpaymentmodeIDtypeDT().compareTo((getColpaymentmodeId() == null ? new BigDecimal(0) : getColpaymentmodeId())) == 0) {
						if (collectionlst.getColpaymentmodeIDtypeDT().compareTo(paymentModeCashId) == 0) {
							clearingDetailAfterAdding();
							RequestContext.getCurrentInstance().execute("cashexists.show();");
							flag = false;
							break;
						} else {
							if (collectionlst.getColBankCodeDT().compareTo(getColBankCode()) == 0) {
								if (collectionlst.getColCardNumberDT().compareTo(getColCardNo()) == 0) {
									if(collectionlst.getColApprovalNo().compareTo(getColApprovalNo()) == 0){
										clearingDetailAfterAdding();
										RequestContext.getCurrentInstance().execute("bankexists.show();");
										flag = false;
										break;
									}else {
										i = 1;
									}

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
					totalUamount = totalUamount.add(collectionlst.getColAmountDT());
				}
				setToalUsedAmount(totalUamount);
				if (getToalUsedAmount().compareTo(getCalNetAmountPaid()) > 0) {
					setTotalbalanceAmount(BigDecimal.ZERO);
					setTotalrefundAmount(getToalUsedAmount().subtract(getCalNetAmountPaid()));
				} else if (getToalUsedAmount().compareTo(getCalNetAmountPaid()) < 0) {
					setTotalbalanceAmount(getCalNetAmountPaid().subtract(getToalUsedAmount()));
					setTotalrefundAmount(BigDecimal.ZERO);
				} else {
					setTotalbalanceAmount(BigDecimal.ZERO);
					setTotalrefundAmount(BigDecimal.ZERO);
				}
			}
			clearingDetailAfterAdding();
		}
	}
	
	public void editDebitCardtoEnter() {
		setBooRenderSingleDebit(true);
		setBooRenderMulDebit(false);
		setColCardNo(null);
		setPopulatedDebitCardNumber(null);
	}
	
	public void checkingCardNumberLength() {
		if (getColCardNo() != null && getColCardNoLength() != null) {
			if ((getColCardNo().toString()).length() != getColCardNoLength().intValue()) {
				setColCardNo(null);
				RequestContext.getCurrentInstance().execute("misMatchBankCardLength.show();");
			}
		}
	}
	
	/** populate new bankList for Customer **/
	public void addNewBenificiary() {
		setColBankTransferBankCode(null);
		setColBankCode(null);
		setColCardNo(null);
		setPopulatedDebitCardNumber(null);
		setColNameofCard(null);
		setColCash(null);
		setColAuthorizedby(null);
		setColpassword(null);
		setColApprovalNo(null);
		setBooAuthozed(false);
		bankMasterList.clear();
		localbankList.clear();// From View V_EX_CBNK
		lstDebitCard.clear();

		// to fetch All Banks from View
		//getLocalBankListforIndicatorFromView();
		localbankList = generalService.getLocalBankListFromView(session.getCountryId());

		List<BigDecimal> duplicateCheck = new ArrayList<BigDecimal>();
		List<ViewBankDetails> lstofBank = new ArrayList<ViewBankDetails>();
		if (localbankList.size() != 0) {
			for (ViewBankDetails lstBank : localbankList) {
				if (!duplicateCheck.contains(lstBank.getChequeBankId())) {
					duplicateCheck.add(lstBank.getChequeBankId());
					lstofBank.add(lstBank);
				}
			}
		}
		setBankMasterList(lstofBank);
		setBooRenderSingleDebit(true);
		setBooRenderMulDebit(false);
	}

	private boolean booAuthozed = false;
	private BigDecimal totalbalanceAmount = new BigDecimal(0);
	private BigDecimal totalrefundAmount = new BigDecimal(0);
	private boolean boorefundPaymentDetails = false;
	private boolean paymentDeatailsPanel = false;

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

	public boolean isBoorefundPaymentDetails() {
		return boorefundPaymentDetails;
	}

	public void setBoorefundPaymentDetails(boolean boorefundPaymentDetails) {
		this.boorefundPaymentDetails = boorefundPaymentDetails;
	}

	public boolean isPaymentDeatailsPanel() {
		return paymentDeatailsPanel;
	}

	public void setPaymentDeatailsPanel(boolean paymentDeatailsPanel) {
		this.paymentDeatailsPanel = paymentDeatailsPanel;
	}

	public boolean isBooAuthozed() {
		return booAuthozed;
	}

	public void setBooAuthozed(boolean booAuthozed) {
		this.booAuthozed = booAuthozed;
	}

	// after adding data to datatable and then reseting
	public void clearingDetailAfterAdding() {
		LOGGER.info("Entering into clearingDetailAfterAdding method");
		setColpaymentmodeId(null);
		setColpaymentmodeName(null);
		setColpaymentmodeCode(null);
		setColBankCode(null);
		setColCardNo(null);
		setPopulatedDebitCardNumber(null);
		setColCash(null);
		setColAuthorizedby(null);
		setColpassword(null);
		setColNameofCard(null);
		setBooAuthozed(false);
		setColApprovalNo(null);
		setBooRenderSingleDebit(true);
		setBooRenderMulDebit(false);
		// clear cheque details
		// payment mode Cheque variables
		setColchequebankCode(null);
		setColChequeRef(null);
		setColChequeDate(null);
		setColChequeApprovalNo(null);
		lstDebitCard.clear();
		changeofPaymentMode();
		LOGGER.info("Exit into clearingDetailAfterAdding method");
	}

	/*
	 * // adding to datatable list in payment mode public void
	 * addPaymentModerecord() {
	 * 
	 * 
	 * BigDecimal paymentModeCashId =
	 * ipaymentService.fetchPaymodeMasterId(Constants.CASHNAME,new
	 * BigDecimal(session.isExists("languageId") ?
	 * session.getSessionValue("languageId") : "1"));
	 * 
	 * if(paymentModeCashId != null){ totalUamount = new BigDecimal(0);
	 * PersonalRemittanceCollectionDataTable personalcollectionDT = new
	 * PersonalRemittanceCollectionDataTable();
	 * 
	 * personalcollectionDT.setColpaymentmodeIDtypeDT(getColpaymentmodeId());
	 * personalcollectionDT.setColpaymentmodetypeDT(getColpaymentmodeName()); if
	 * (getColBankid() != null) {
	 * personalcollectionDT.setColBankIdDT(getColBankid());
	 * personalcollectionDT.setColbankNameDT(generalService.getBankName(
	 * getColBankid()));
	 * personalcollectionDT.setColCardNumberDT(getColCardNo());
	 * personalcollectionDT.setColNameofCardDT(getColNameofCard());
	 * personalcollectionDT.setColAuthorizedByDT(getColAuthorizedby()); }
	 * 
	 * personalcollectionDT.setColAmountDT(getColCash());
	 * if(getColpaymentmodeId().compareTo(paymentModeCashId)==0){
	 * setTempCash(getColCash()); }
	 * coldatatablevalues.add(personalcollectionDT); firstTime = false; }
	 * 
	 * }
	 */
	private boolean booRenderColCheque = false;
	private String colchequebankCode;
	private String colChequeRef;
	private Date colChequeDate;
	private Date effectiveMinDate = new Date();
	private String colChequeApprovalNo;

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

	// adding to datatable list in payment mode
	public void addPaymentModerecord() {
		BigDecimal paymentModeCashId = ipaymentService.fetchPaymodeMasterId(Constants.CASHNAME, new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId") : "1"));
		if (paymentModeCashId != null) {
			totalUamount = new BigDecimal(0);
			PersonalRemittanceCollectionDataTable personalcollectionDT = new PersonalRemittanceCollectionDataTable();
			personalcollectionDT.setColpaymentmodeIDtypeDT(getColpaymentmodeId());
			personalcollectionDT.setColpaymentmodetypeDT(getColpaymentmodeName());
			personalcollectionDT.setColpaymentmodeCode(getColpaymentmodeCode());
			if (getColBankCode() != null || getColchequebankCode() != null  || getColBankTransferBankCode() != null) {
				if (localbankListForBankCode.size() != 0) {
					ViewBankDetails lstDetaiks = localbankListForBankCode.get(0);
					personalcollectionDT.setColBankIdDT(lstDetaiks.getChequeBankId());
					personalcollectionDT.setColbankNameDT(lstDetaiks.getBankFullName());
					if (getColChequeRef() != null) {
						personalcollectionDT.setColBankCodeDT(getColchequebankCode());
						personalcollectionDT.setColchequeRefNo(getColChequeRef());
						personalcollectionDT.setColchequeDate(getColChequeDate());
						personalcollectionDT.setColApprovalNo(getColChequeApprovalNo());
					} else if(getColBankCode() != null){
						personalcollectionDT.setColBankCodeDT(getColBankCode());
						personalcollectionDT.setColCardNumberDT(getColCardNo());
						personalcollectionDT.setColNameofCardDT(getColNameofCard());
						personalcollectionDT.setColAuthorizedByDT(getColAuthorizedby());
						personalcollectionDT.setColApprovalNo(getColApprovalNo());
						personalcollectionDT.setKnetReceiptDT(getKnetIposReceipt());
						personalcollectionDT.setKnetTransIdDT(getKnetTranId());
						personalcollectionDT.setKneRceiptTimeDT(getKnetReceiptDate());
						personalcollectionDT.setBooDisbale(false);
					}else if(getColBankTransferBankCode() != null){
						personalcollectionDT.setBooDisbale(false);
						personalcollectionDT.setColBankCodeDT(getColBankTransferBankCode());
					}
				}
			}
			personalcollectionDT.setColAmountDT(GetRound.roundBigDecimal(getColCash(), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(session.getCurrencyId()))));
			if (getColpaymentmodeId().compareTo(paymentModeCashId) == 0) {
				setTempCash(GetRound.roundBigDecimal(getColCash(), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(session.getCurrencyId()))));
			}
			coldatatablevalues.add(personalcollectionDT);
		}
		setBooRenderSingleDebit(true);
		setBooRenderMulDebit(false);
		lstDebitCard.clear();
		setColCardNo(null);
	}

	// Next button -- Payment Mode
	public void nextpaneltoPaymentDetails() {
		LOGGER.info("Entering into nextpaneltoPaymentDetails method");
		if (getSpCharges() != null && getColamountKWD() != null && getSpCharges().compareTo(getColamountKWD()) <= 0) {
			setBooRenderPaymentDetails(true);
			setBooRenderCollection(false);
			setCashAmount(getTempCash());
			// to render the denomination in payment details panel -7
			renderingDenominationDT();
			denaMinLstData();
			// if Payment mode is cash then will enable the denomiation table
			/*
			 * LOGGER.info(getPaymentMode()); LOGGER.info(getPaymentMode() ==
			 * null); if (getPaymentMode()!= null &&
			 * getPaymentMode().equals(Constants.CASHNAME)) {
			 * setBooRenderDenaminationDetails(true); } // card and cash both
			 * payement should be selected here if (coldatatablevalues.size() ==
			 * 2) { setBooRenderDenaminationDetails(true); }
			 */
			setBooRenderPaymentDetails(true);
		} else {
			RequestContext.getCurrentInstance().execute("amountmatch.show();");
		}
		LOGGER.info("Exit into nextpaneltoPaymentDetails method");
	}
	/*
	 * // to render denomination based on datatable added public void
	 * renderingDenominationDT() { if (coldatatablevalues.size() != 0) {
	 * BigDecimal paymentModeCashId =
	 * ipaymentService.fetchPaymodeMasterId(Constants.CASHNAME,new
	 * BigDecimal(session.isExists("languageId") ?
	 * session.getSessionValue("languageId") : "1"));
	 * 
	 * if(paymentModeCashId != null){ for (PersonalRemittanceCollectionDataTable
	 * collectionlst : coldatatablevalues) { if
	 * (collectionlst.getColpaymentmodeIDtypeDT().compareTo(paymentModeCashId)
	 * == 0) { setDenomtotalCashreceived(getColamountKWD());
	 * setBooRendercashdenomination(true); break; } else {
	 * setDenomtotalCashreceived(getColamountKWD());
	 * setBooRendercashdenomination(false); } } } } }
	 */

	private BigDecimal collectedAmount;
	private BigDecimal knetValue;
	private BigDecimal chequeValue;

	public BigDecimal getKnetValue() {
		return knetValue;
	}

	public void setKnetValue(BigDecimal knetValue) {
		this.knetValue = knetValue;
	}

	public BigDecimal getChequeValue() {
		return chequeValue;
	}

	public void setChequeValue(BigDecimal chequeValue) {
		this.chequeValue = chequeValue;
	}

	public BigDecimal getCollectedAmount() {
		return collectedAmount;
	}

	public void setCollectedAmount(BigDecimal collectedAmount) {
		this.collectedAmount = collectedAmount;
	}

	// to render denomination based on datatable added
	public void renderingDenominationDT() {
		if (coldatatablevalues.size() != 0) {
			BigDecimal paymentModeCashId = ipaymentService.fetchPaymodeMasterId(Constants.CASHNAME, new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId") : "1"));
			if (paymentModeCashId != null) {
				for (PersonalRemittanceCollectionDataTable collectionlst : coldatatablevalues) {
					if (collectionlst.getColpaymentmodeIDtypeDT().compareTo(paymentModeCashId) == 0) {
						setDenomtotalCashreceived(getColamountKWD());
						setBooRendercashdenomination(true);
						setCollectedAmount(GetRound.roundBigDecimal(BigDecimal.ZERO, foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(session.getCurrencyId()))));
						break;
					} else {
						setCollectedAmount(getToalUsedAmount());
						setDenomtotalCashreceived(getColamountKWD());
						setBooRendercashdenomination(false);
					}
				}
			}
			BigDecimal totalCardCash = BigDecimal.ZERO;
			BigDecimal totalCreditCardCash = BigDecimal.ZERO;
			for (PersonalRemittanceCollectionDataTable collectionlst : coldatatablevalues) {
				if (collectionlst.getColpaymentmodeCode().equalsIgnoreCase(Constants.KNETCode)) {
					totalCardCash = totalCardCash.add(collectionlst.getColAmountDT());
				} else if (collectionlst.getColpaymentmodeCode().equalsIgnoreCase(Constants.ChequeCode)) {
					totalCreditCardCash = totalCreditCardCash.add(collectionlst.getColAmountDT());
				}
			}
			setKnetValue(GetRound.roundBigDecimal(totalCardCash, foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(session.getCurrencyId()))));
			setChequeValue(GetRound.roundBigDecimal(totalCreditCardCash, foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(session.getCurrencyId()))));
		}
	}

	public void editRecord(PersonalRemittanceCollectionDataTable personalRemitObj) {
		setFocus(true);
		if (coldatatablevalues.size() > 0) {
			subtractedAmount = getToalUsedAmount().subtract(personalRemitObj.getColAmountDT());
			setToalUsedAmount(subtractedAmount);
			if (getToalUsedAmount().compareTo(getCalNetAmountPaid()) > 0) {
				setTotalbalanceAmount(BigDecimal.ZERO);
				setTotalrefundAmount(getToalUsedAmount().subtract(getCalNetAmountPaid()));
			} else if (getToalUsedAmount().compareTo(getCalNetAmountPaid()) < 0) {
				setTotalbalanceAmount(getCalNetAmountPaid().subtract(getToalUsedAmount()));
				setTotalrefundAmount(BigDecimal.ZERO);
			} else {
				setTotalbalanceAmount(BigDecimal.ZERO);
				setTotalrefundAmount(BigDecimal.ZERO);
			}
		} else {
			setToalUsedAmount(null);
			setTotalbalanceAmount(null);
			setTotalrefundAmount(null);
		}
		if (personalRemitObj.getColpaymentmodeCode().equalsIgnoreCase(Constants.CashCode)) {
			setBooRenderColDebitCard(false);
			setBooRenderColCheque(false);
			setColpaymentmodeId(personalRemitObj.getColpaymentmodeIDtypeDT());
			setColpaymentmodeName(personalRemitObj.getColpaymentmodetypeDT());
			setColpaymentmodeCode(personalRemitObj.getColpaymentmodeCode());
			setColCash(personalRemitObj.getColAmountDT());
			coldatatablevalues.remove(personalRemitObj);
		} else if (personalRemitObj.getColpaymentmodeCode().equalsIgnoreCase(Constants.KNETCode)) {
			setBooRenderColDebitCard(true);
			setBooRenderColCheque(false);
			setColpaymentmodeId(personalRemitObj.getColpaymentmodeIDtypeDT());
			setColpaymentmodeName(personalRemitObj.getColpaymentmodetypeDT());
			setColpaymentmodeCode(personalRemitObj.getColpaymentmodeCode());
			setColBankCode(personalRemitObj.getColBankCodeDT());
			setPopulatedDebitCardNumber(personalRemitObj.getColCardNumberDT());
			setColCardNo(personalRemitObj.getColCardNumberDT());
			setColNameofCard(personalRemitObj.getColNameofCardDT());
			setColCash(personalRemitObj.getColAmountDT());
			setColApprovalNo(personalRemitObj.getColApprovalNo());
			if (personalRemitObj.getColAuthorizedByDT() != null) {
				setColAuthorizedby(personalRemitObj.getColAuthorizedByDT());
				setColpassword(null);
				setBooAuthozed(true);
			} else {
				setBooAuthozed(false);
			}
			coldatatablevalues.remove(personalRemitObj);
			checkcashcollection();
		} else if (personalRemitObj.getColpaymentmodeCode().equalsIgnoreCase(Constants.ChequeCode)) {
			setBooRenderColDebitCard(false);
			setBooRenderColCheque(true);
			setColpaymentmodeId(personalRemitObj.getColpaymentmodeIDtypeDT());
			setColpaymentmodeName(personalRemitObj.getColpaymentmodetypeDT());
			setColpaymentmodeCode(personalRemitObj.getColpaymentmodeCode());
			setColchequebankCode(personalRemitObj.getColBankCodeDT());
			setColChequeRef(personalRemitObj.getColchequeRefNo());
			setColChequeDate(personalRemitObj.getColchequeDate());
			setColCash(personalRemitObj.getColAmountDT());
			setColChequeApprovalNo(personalRemitObj.getColApprovalNo());
			coldatatablevalues.remove(personalRemitObj);
		} else {
			System.out.println("Other Payment mode");
		}
		if (coldatatablevalues.size() != 0) {
			setBooRendercollectiondatatable(true);
		} else {
			setBooRendercollectiondatatable(false);
		}
	}

	// to remove details from data table after adding
	public void deletePaymentModeRecords(PersonalRemittanceCollectionDataTable collectionDt) {
		setFocus(true);
		if (coldatatablevalues.size() > 0) {
			subtractedAmount = getToalUsedAmount().subtract(collectionDt.getColAmountDT());
			setToalUsedAmount(subtractedAmount);
			if (getToalUsedAmount().compareTo(getCalNetAmountPaid()) > 0) {
				setTotalbalanceAmount(BigDecimal.ZERO);
				setTotalrefundAmount(getToalUsedAmount().subtract(getCalNetAmountPaid()));
			} else if (getToalUsedAmount().compareTo(getCalNetAmountPaid()) < 0) {
				setTotalbalanceAmount(getCalNetAmountPaid().subtract(getToalUsedAmount()));
				setTotalrefundAmount(BigDecimal.ZERO);
			} else {
				setTotalbalanceAmount(BigDecimal.ZERO);
				setTotalrefundAmount(BigDecimal.ZERO);
			}
		} else {
			setToalUsedAmount(null);
			setTotalbalanceAmount(null);
			setTotalrefundAmount(null);
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

	public void saveAll() {
		LOGGER.info("Entering into saveAll method");
		boolean noteEmpty = false;
		LOGGER.info("****getPayRefund****" + getPayRefund());
		// card and cash both payment save
		if (coldatatablevalues.size() == 2) {
			// getDocumentSerialID("U");
			LOGGER.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@ CASH and Card both  @@@@@@@@@@@@@@@@@@@@@@@@@@@");
			// LOGGER.info("CASH and Card both are choosed here");
			noteEmpty = saveForeignCurrencyAdjustValidation();
			if (noteEmpty) {
				if (getPayRefund().signum() == -1) {
					RequestContext.getCurrentInstance().execute("totalcashCheck.show();");
				} else {

					BigDecimal documentId=BigDecimal.ZERO;
					BigDecimal companyCode=BigDecimal.ZERO;
					List<Document> listDocument = generalService.getDocument(Constants.DOCUMENT_CODE_FOR_STOPPAYMENT, session.getLanguageId());
					if(listDocument!=null && listDocument.size()>0){
						documentId=listDocument.get(0).getDocumentID();
					}

					List<CompanyMasterDesc> data=generalService.getCompanyList(session.getCompanyId(),new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId") : "1"));
					if(data!=null && data.size()>0)
					{
						companyCode=data.get(0).getFsCompanyMaster().getCompanyCode();
					}

					setTransactionNoForDisplay(getReceiptNo());
					HashMap<String, Object> documentSerial = new HashMap<String, Object>();
					documentSerial.put("countryId", session.getCountryId().intValue());
					documentSerial.put("companyId", session.getCompanyId().intValue());
					documentSerial.put("documentCode", Constants.DOCUMENT_CODE_FOR_STOPPAYMENT.intValue());
					documentSerial.put("dealYear", Integer.parseInt(dealYear));
					documentSerial.put("processIn", Constants.U);
					documentSerial.put("remittanceTransactionId", getRemittanceTransactionId());
					documentSerial.put("status", Constants.S);
					documentSerial.put("userName", session.getUserName());
					documentSerial.put("transferNo", getTransferNo());
					
					List<UserFinancialYear> userFinList = iMiscellaneousReceiptPaymentService.getFinanacilYearId(getTransferYear());
					
					BigDecimal docFinYearId = null;
					if(userFinList.size()>0){
						docFinYearId = userFinList.get(0).getFinancialYearID();
					}
					
					documentSerial.put("transferYearId", docFinYearId);
					documentSerial.put("receiptNo", getReceiptNo());
					HashMap<String, Object> mapAllDetailForSave = new HashMap<String, Object>();
					mapAllDetailForSave.put("DocuemntSeriality", documentSerial);
					mapAllDetailForSave.put("PaymentReceipt", concurrentsavepaymentReceipt(documentId,companyCode));
					mapAllDetailForSave.put("ExchangeRate", getExchangeRate());
					mapAllDetailForSave.put("LocalTrnsAmount", getTransferAmount());



					Collect collect = concurrentSaveCollect(documentId,companyCode);
					mapAllDetailForSave.put("Collection", collect);
					mapAllDetailForSave.put("CollectionDetails", concurrentsaveCollectionDetail(collect,documentId,companyCode));
					mapAllDetailForSave.put("ForeignCurrencyAdjust", concurrentsaveForeignCurrencyAdjust(getExchangeRate(),documentId,companyCode));
					mapAllDetailForSave.put("BranchCode", session.getCountryBranchCode());
					try {
						stopPaymentService.saveAlltheDeatailsforCash(mapAllDetailForSave);

						// Move to OLD System
						List<Collect> collectionList = stopPaymentService.getCollectionListById(collect.getCollectionId());
						List<RemittanceTransaction> remitTxn = stopPaymentService.getRemitTxnDetailsById(getRemittanceTransactionId());							
						if(collectionList.size()>0 && remitTxn.size()>0){
							stopPaymentService.moveToOldSystem(session.getCountryId(), 
									session.getCompanyId(), 
									Constants.DOCUMENT_CODE_FOR_STOPPAYMENT,
									collectionList.get(0).getDocumentFinanceYear(), 
									collectionList.get(0).getDocumentNo(), 
									remitTxn.get(0).getCompanyId().getCompanyId(), 
									remitTxn.get(0).getDocumentId().getDocumentID(), 
									remitTxn.get(0).getDocumentFinanceYear(), 
									remitTxn.get(0).getDocumentNo());
						}
						setReceiptNo(collectionList.get(0).getDocumentNo());
						RequestContext.getCurrentInstance().execute("save.show();");
					} catch (Exception e) {
						RequestContext.getCurrentInstance().execute("csp.show();");
						setErrorMessage(e.getMessage());
						return;
					}
					LOGGER.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@ CASH and Card both  completed@@@@@@@@@@@@@@@@@@@@@@@@@@@");
				}
			} else {
				RequestContext.getCurrentInstance().execute("notesZero.show();");
				return;
			}
		} else {
			if (getPaymentMode() != null && getPaymentMode().equals(Constants.CASHNAME)) {
				// cash payment mode
				LOGGER.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@ cash payment mode @@@@@@@@@@@@@@@@@@@@@@@@@@@");
				noteEmpty = saveForeignCurrencyAdjustValidation();
				if (noteEmpty) {
					if (getPayRefund().signum() == -1) {
						RequestContext.getCurrentInstance().execute("totalcashCheck.show();");
					} else {

						BigDecimal documentId=BigDecimal.ZERO;
						BigDecimal companyCode=BigDecimal.ZERO;
						List<Document> listDocument = generalService.getDocument(Constants.DOCUMENT_CODE_FOR_STOPPAYMENT, session.getLanguageId());
						if(listDocument!=null && listDocument.size()>0){
							documentId=listDocument.get(0).getDocumentID();
						}

						List<CompanyMasterDesc> data=generalService.getCompanyList(session.getCompanyId(),new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId") : "1"));
						if(data!=null && data.size()>0)
						{
							companyCode=data.get(0).getFsCompanyMaster().getCompanyCode();
						}

						HashMap<String, Object> documentSerial = new HashMap<String, Object>();
						documentSerial.put("countryId", session.getCountryId().intValue());
						documentSerial.put("companyId", session.getCompanyId().intValue());
						documentSerial.put("documentCode", Constants.DOCUMENT_CODE_FOR_STOPPAYMENT.intValue());
						documentSerial.put("dealYear", Integer.parseInt(dealYear));
						documentSerial.put("processIn", Constants.U);
						documentSerial.put("remittanceTransactionId", getRemittanceTransactionId());
						documentSerial.put("status", Constants.S);
						documentSerial.put("userName", session.getUserName());
						documentSerial.put("transferNo", getTransferNo());
						List<UserFinancialYear> userFinList = iMiscellaneousReceiptPaymentService.getFinanacilYearId(getTransferYear());
						
						BigDecimal docFinYearId = null;
						if(userFinList.size()>0){
							docFinYearId = userFinList.get(0).getFinancialYearID();
						}
						
						documentSerial.put("transferYearId", docFinYearId);
						documentSerial.put("receiptNo", getReceiptNo());
						HashMap<String, Object> mapAllDetailForSave = new HashMap<String, Object>();
						mapAllDetailForSave.put("DocuemntSeriality", documentSerial);
						mapAllDetailForSave.put("PaymentReceipt", concurrentsavepaymentReceipt(documentId,companyCode));
						mapAllDetailForSave.put("ExchangeRate", getExchangeRate());
						mapAllDetailForSave.put("LocalTrnsAmount", getTransferAmount());
						Collect collect = concurrentSaveCollect(documentId,companyCode);
						mapAllDetailForSave.put("Collection", collect);
						mapAllDetailForSave.put("CollectionDetails", concurrentsaveCollectionDetail(collect,documentId,companyCode));
						mapAllDetailForSave.put("ForeignCurrencyAdjust", concurrentsaveForeignCurrencyAdjust(getExchangeRate(),documentId,companyCode));
						mapAllDetailForSave.put("BranchCode", session.getCountryBranchCode());
						try {
							stopPaymentService.saveAlltheDeatailsforCash(mapAllDetailForSave);

							// Move to OLD System
							List<Collect> collectionList = stopPaymentService.getCollectionListById(collect.getCollectionId());
							List<RemittanceTransaction> remitTxn = stopPaymentService.getRemitTxnDetailsById(getRemittanceTransactionId());							
							if(collectionList.size()>0 && remitTxn.size()>0){
								stopPaymentService.moveToOldSystem(session.getCountryId(), 
										session.getCompanyId(), 
										Constants.DOCUMENT_CODE_FOR_STOPPAYMENT,
										collectionList.get(0).getDocumentFinanceYear(), 
										collectionList.get(0).getDocumentNo(), 
										remitTxn.get(0).getCompanyId().getCompanyId(), 
										remitTxn.get(0).getDocumentId().getDocumentID(), 
										remitTxn.get(0).getDocumentFinanceYear(), 
										remitTxn.get(0).getDocumentNo());
							}				
							setReceiptNo(collectionList.get(0).getDocumentNo());
							RequestContext.getCurrentInstance().execute("save.show();");
						} catch (Exception e) {
							RequestContext.getCurrentInstance().execute("csp.show();");
							setErrorMessage(e.getMessage());
							return;
						}
						LOGGER.info("Exit into saveAll method");
						LOGGER.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@ cash payment mode completed@@@@@@@@@@@@@@@@@@@@@@@@@@@");
					}
				} else {
					RequestContext.getCurrentInstance().execute("notesZero.show();");
				}
			} else {

				BigDecimal documentId=BigDecimal.ZERO;
				BigDecimal companyCode=BigDecimal.ZERO;
				List<Document> listDocument = generalService.getDocument(Constants.DOCUMENT_CODE_FOR_STOPPAYMENT, session.getLanguageId());
				if(listDocument!=null && listDocument.size()>0){
					documentId=listDocument.get(0).getDocumentID();
				}

				List<CompanyMasterDesc> data=generalService.getCompanyList(session.getCompanyId(),new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId") : "1"));
				if(data!=null && data.size()>0)
				{
					companyCode=data.get(0).getFsCompanyMaster().getCompanyCode();
				}

				// card payment mode
				LOGGER.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@  card payment mode @@@@@@@@@@@@@@@@@@@@@@@@@@@");
				HashMap<String, Object> mapAllDetailForSave = new HashMap<String, Object>();
				HashMap<String, Object> documentSerial = new HashMap<String, Object>();
				documentSerial.put("countryId", session.getCountryId().intValue());
				documentSerial.put("companyId", session.getCompanyId().intValue());
				documentSerial.put("documentCode", Constants.DOCUMENT_CODE_FOR_STOPPAYMENT.intValue());
				documentSerial.put("dealYear", Integer.parseInt(dealYear));
				documentSerial.put("processIn", Constants.U);
				documentSerial.put("remittanceTransactionId", getRemittanceTransactionId());
				documentSerial.put("status", Constants.S);
				documentSerial.put("userName", session.getUserName());
				documentSerial.put("transferNo", getTransferNo());
				List<UserFinancialYear> userFinList = iMiscellaneousReceiptPaymentService.getFinanacilYearId(getTransferYear());
				
				BigDecimal docFinYearId = null;
				if(userFinList.size()>0){
					docFinYearId = userFinList.get(0).getFinancialYearID();
				}
				
				documentSerial.put("transferYearId", docFinYearId);
				documentSerial.put("receiptNo", getReceiptNo());
				// getDocumentSerialID("U");
				// savepaymentReceipt();
				mapAllDetailForSave.put("DocuemntSeriality", documentSerial);
				mapAllDetailForSave.put("PaymentReceipt", concurrentsavepaymentReceipt(documentId,companyCode));
				Collect collect = concurrentSaveCollect(documentId,companyCode);
				mapAllDetailForSave.put("Collection", collect);
				mapAllDetailForSave.put("CollectionDetails", concurrentsaveCollectionDetail(collect,documentId,companyCode));
				mapAllDetailForSave.put("BranchCode", session.getCountryBranchCode());
				try {
					stopPaymentService.saveAlltheDeatailsforCard(mapAllDetailForSave);

					// Move to OLD System
					List<Collect> collectionList = stopPaymentService.getCollectionListById(collect.getCollectionId());
					List<RemittanceTransaction> remitTxn = stopPaymentService.getRemitTxnDetailsFromViewById(getRemittanceTransactionId());							
					if(collectionList.size()>0 && remitTxn.size()>0){
						
						BigDecimal viewCompanyId = remitTxn.get(0).getCompanyId().getCompanyId();
						BigDecimal viewDocumentId = remitTxn.get(0).getDocumentId().getDocumentID();
						BigDecimal viewDocumentFinanceYear = remitTxn.get(0).getDocumentFinanceYear();
						BigDecimal viewDocumentNo = remitTxn.get(0).getDocumentNo();
						
						stopPaymentService.moveToOldSystem(session.getCountryId(), 
								session.getCompanyId(), 
								Constants.DOCUMENT_CODE_FOR_STOPPAYMENT,
								collectionList.get(0).getDocumentFinanceYear(), 
								collectionList.get(0).getDocumentNo(), 
								viewCompanyId, 
								viewDocumentId, 
								viewDocumentFinanceYear, 
								viewDocumentNo);
					}
					setReceiptNo(collectionList.get(0).getDocumentNo());
					RequestContext.getCurrentInstance().execute("save.show();");
				} catch (Exception e) {
					RequestContext.getCurrentInstance().execute("csp.show();");
					setErrorMessage(e.getMessage());
					return;
				}
				LOGGER.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@  card payment mode completed @@@@@@@@@@@@@@@@@@@@@@@@@@@");
			}
		}
		firstTime = false;
		LOGGER.info("Exit into saveAll method");
	}

	public boolean saveForeignCurrencyAdjustValidation() {
		LOGGER.info("Entering into saveForeignCurrencyAdjust method");
		// Collect collect = (Collect) returnResult.get("Collect");
		boolean notesEmpty = false;
		LOGGER.info("******************size of the currency" + lstData.size());
		for (ForeignLocalCurrencyDataTable foreignLocalCurrencyDataTable : lstData) {
			if (foreignLocalCurrencyDataTable.getQty() != null && !foreignLocalCurrencyDataTable.getQty().equals("0")) {
				notesEmpty = true;
				break;
			}
		}
		LOGGER.info("Exit into saveForeignCurrencyAdjust method");
		return notesEmpty;
	}

	// to get the accoMMYY value
	public static String getCurrentDateWithFormat() {
		LOGGER.info("Entering into getCurrentDateWithFormat method");
		Map<Integer, String> data = new HashMap<Integer, String>();
		for (int i = 0; i < 12; i++) {
			if (i < 9) {
				data.put(i, "0" + String.valueOf(i + 1));
			} else {
				data.put(i, String.valueOf(i + 1));
			}
		}
		/*
		 * String year = String.valueOf(new Date().getYear()).substring(1, 3);
		 */
		String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR)).substring(2, 4);
		LOGGER.info(Calendar.getInstance().get(Calendar.MONTH));
		LOGGER.info("Exit into getCurrentDateWithFormat method");
		return "01/" + data.get(Calendar.getInstance().get(Calendar.MONTH)) + "/" + year;
	}

	public void setFinaceYearId(int finaceYearId) {
		this.finaceYearId = finaceYearId;
	}

	public void setFinaceYear(int finaceYear) {
		this.finaceYear = finaceYear;
	}

	public int getFinaceYearId() {
		LOGGER.info("Entering into getFinaceYearId method");
		try {
			financialYearList = foreignCurrencyPurchase.getUserFinancialYear(new Date());
			LOGGER.info("financialYearList :" + financialYearList.size());
			if (financialYearList != null)
				finaceYearId = Integer.parseInt(financialYearList.get(0).getFinancialYearID().toString());
			setFinaceYearId(finaceYearId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("Exit into getFinaceYearId method");
		return finaceYearId;
	}

	public int getFinaceYear() {
		LOGGER.info("Entering into getFinaceYear method");
		try {
			financialYearList = foreignCurrencyPurchase.getUserFinancialYear(new Date());
			LOGGER.info("financialYearList :" + financialYearList.size());
			if (financialYearList != null)
				finaceYear = Integer.parseInt(financialYearList.get(0).getFinancialYear().toString());
			setFinaceYear(finaceYear);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("Exit into getFinaceYear method");
		return finaceYear;
	}

	public void gotoBack() {

		LOGGER.info("Entering into gotoBack method");
		if(getNextOrSaveButton()!=null && getNextOrSaveButton().equals(Constants.Save)  && getBoorefundPaymentDetails()==true)

		{
			//setBooRenderPaymentDetails(true);
			/*setCashAmount(getTempCash());*/
			// to render the denomination in payment details panel -7
			//	renderingDenominationDT();
			//	denaMinLstData();
			setBooRenderPaymentDetails(true);
			setBooRenderCollection(false);
			setBooRendercashdenomination(true);
			setBoorefundPaymentDetails( false);
			setNextOrSaveButton(Constants.Next);


		}
		else {

			setBooRenderPaymentDetails(false);
			setBooRenderDenaminationDetails(false);
			setBooRenderCollection(true);
			// setBooRenderPaymentDetails(true);
			setBooRendercollectiondatatable(true);
			setBoRenderTotalAmountCalPanel(true);
			/* setCalNetAmountPaid(null); */


			setBoorefundPaymentDetails( false);
			setPaymentDeatailsPanel( false);

		}



		LOGGER.info("Exit into gotoBack method");
	}

	public List<BankApplicability> getLstoflocalbank() {
		return lstoflocalbank;
	}

	public void setLstoflocalbank(List<BankApplicability> lstoflocalbank) {
		this.lstoflocalbank = lstoflocalbank;
	}

	List<CustomerBank> localBankListinCollection = new ArrayList<CustomerBank>();
	private List<ViewBankDetails> chequebankMasterList = new ArrayList<ViewBankDetails>();
	private List<ViewBankDetails> localbankList = new ArrayList<ViewBankDetails>();

	public List<ViewBankDetails> getLocalbankList() {
		return localbankList;
	}

	public void setLocalbankList(List<ViewBankDetails> localbankList) {
		this.localbankList = localbankList;
	}

	public List<ViewBankDetails> getChequebankMasterList() {
		return chequebankMasterList;
	}

	public void setChequebankMasterList(List<ViewBankDetails> chequebankMasterList) {
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
		localbankList = generalService.getLocalBankListFromView(session.getCountryId());
		// cheque banks purpose
		if (localbankList.size() != 0) {
			chequebankMasterList.addAll(localbankList);
		}
		// lstoflocalbank =
		// generalService.getLocalBankList(sessionmanage.getCountryId());
		List<ViewBankDetails> localBankListinCollection = icustomerBankService.customerBanks(getCustomerId(), getColBankCode());
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

	// checking customer name and debit card name of card
	public void firstNameCheck() {
		if (getColNameofCard().equalsIgnoreCase(getCustomerFullName())) {
			setColAuthorizedby(null);
			setColpassword(null);
			setBooAuthozed(false);
		} else {
			List<DebitAutendicationView> localEmpllist = iPersonalRemittanceService.getdebitAutendicationList();
			setEmpllist(localEmpllist);
			setColAuthorizedby(null);
			setColpassword(null);
			setBooAuthozed(true);
			// populate alert msg if customer name not match
			setExceptionMessage(Constants.NameCheckAlertMsg);
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}
	}

	private BigDecimal populatedDebitCardNumber;

	public BigDecimal getPopulatedDebitCardNumber() {
		return populatedDebitCardNumber;
	}

	public void setPopulatedDebitCardNumber(BigDecimal populatedDebitCardNumber) {
		this.populatedDebitCardNumber = populatedDebitCardNumber;
	}

	private List<ViewBankDetails> localbankListForBankCode = new ArrayList<ViewBankDetails>();
	private List<CustomerBank> lstDebitCard = new ArrayList<CustomerBank>();
	private List<DebitAutendicationView> empllist = new ArrayList<DebitAutendicationView>();

	public List<DebitAutendicationView> getEmpllist() {
		return empllist;
	}

	public void setEmpllist(List<DebitAutendicationView> empllist) {
		this.empllist = empllist;
	}

	public List<CustomerBank> getLstDebitCard() {
		return lstDebitCard;
	}

	public void setLstDebitCard(List<CustomerBank> lstDebitCard) {
		this.lstDebitCard = lstDebitCard;
	}

	public List<ViewBankDetails> getLocalbankListForBankCode() {
		return localbankListForBankCode;
	}

	public void setLocalbankListForBankCode(List<ViewBankDetails> localbankListForBankCode) {
		this.localbankListForBankCode = localbankListForBankCode;
	}

	public void populateCustomerDetails() {
		BigDecimal cardlength = BigDecimal.ZERO;
		localbankListForBankCode.clear();
		lstDebitCard.clear();
		setColAuthorizedby(null);
		setColCardNo(null);
		setColCardNoLength(null);
		setPopulatedDebitCardNumber(null);
		setColNameofCard(null);
		setColApprovalNo(null);
		if (getColBankCode() != null) {
			
			boolean OthersSts = checkKNETBanks();
			if(OthersSts){
				setColBankCode(null);
				setExceptionMessage(WarningHandler.showWarningMessage("lbl.bnkOthers", session.getLanguageId()));
				RequestContext.getCurrentInstance().execute("alertmsg.show();");
				return;
			}
			
			localbankListForBankCode = icustomerBankService.getCustomerLocalBankListFromView(session.getCountryId(), getColBankCode());
			List<CustomerBank> localBankListinCollection = icustomerBankService.fetchcustomerBanksDetails(getCustomerId(), getColBankCode());
			if (localbankListForBankCode.size() != 0) {
				ViewBankDetails bankLength = localbankListForBankCode.get(0);
				cardlength = bankLength.getDebitCardLength();
				System.out.println("cardlength :" + cardlength);
			}
			if (localBankListinCollection.size() != 0) {
				if (cardlength.compareTo(BigDecimal.ZERO) != 0) {
					setColCardNoLength(cardlength);
					if (localBankListinCollection.size() == 1) {
						for (CustomerBank customerBank : localBankListinCollection) {
							if (customerBank.getBankCode().equals(getColBankCode())) {
								setPopulatedDebitCardNumber(new BigDecimal(encryptionDescriptionService.getDECrypted(customerBank.getDebitCardName(), customerBank.getDebitCard())));
								setColCardNo(new BigDecimal(encryptionDescriptionService.getDECrypted(customerBank.getDebitCardName(), customerBank.getDebitCard())));
								setColNameofCard(customerBank.getDebitCardName());
								if (customerBank.getAuthorizedBy() != null) {
									List<DebitAutendicationView> localEmpllist = iPersonalRemittanceService.getdebitAutendicationList();
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
						setBooRenderSingleDebit(true);
						setBooRenderMulDebit(false);
						lstDebitCard.clear();
					} else {
						setBooRenderSingleDebit(false);
						setBooRenderMulDebit(true);
						if (localBankListinCollection.size() != 0) {
							for (CustomerBank lstDebitcrd : localBankListinCollection) {
								CustomerBank lstofDebitCard = new CustomerBank();
								lstofDebitCard.setDebitCard(encryptionDescriptionService.getDECrypted(lstDebitcrd.getDebitCardName(), lstDebitcrd.getDebitCard()));
								lstofDebitCard.setDebitCardName(lstDebitcrd.getDebitCardName());
								lstDebitCard.add(lstofDebitCard);
							}
						}
					}
				} else {
					RequestContext.getCurrentInstance().execute("bankDebitCardLenErr.show();");
				}
			} else {
				System.out.println("cardlength :" + cardlength);
				if (cardlength != null) {
					if (cardlength.compareTo(BigDecimal.ZERO) != 0) {
						setColCardNoLength(cardlength);
						lstDebitCard.clear();
						setColAuthorizedby(null);
						setColCardNo(null);
						setPopulatedDebitCardNumber(null);
						setColNameofCard(null);
						setColpassword(null);
						setColApprovalNo(null);
						setBooAuthozed(false);
						setBooRenderSingleDebit(true);
						setBooRenderMulDebit(false);
					} else {
						RequestContext.getCurrentInstance().execute("bankDebitCardLenErr.show();");
					}
				}
			}
		} else {
			lstDebitCard.clear();
			setColAuthorizedby(null);
			setColCardNo(null);
			setColCardNoLength(null);
			setPopulatedDebitCardNumber(null);
			setColNameofCard(null);
			setColpassword(null);
			setColApprovalNo(null);
			setBooAuthozed(false);
			setBooRenderSingleDebit(true);
			setBooRenderMulDebit(false);
		}
	}

	public void gotoFirstPanel() {
		hideAllPanels();
		setBooRenderFirstPanel(true);
		setFocus(true);
	}

	public ReceiptPayment concurrentsavepaymentReceipt(BigDecimal documentId,BigDecimal companyCode) {
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
		Date acc_Month = null;
		try {
			acc_Month = DATE_FORMAT.parse(getCurrentDateWithFormat());
			LOGGER.info("Account Date :" + acc_Month);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		try {
			receiptPayment = new ReceiptPayment();
			CompanyMaster companyMaster = new CompanyMaster();
			companyMaster.setCompanyId(session.getCompanyId());
			receiptPayment.setFsCompanyMaster(companyMaster);
			// Country Save
			CountryMaster countryMaster = new CountryMaster();
			countryMaster.setCountryId(session.getCountryId());
			receiptPayment.setFsCountryMaster(countryMaster);
			// customer Save
			Customer customer = new Customer();
			customer.setCustomerId(getCustomerId());
			receiptPayment.setFsCustomer(customer);
			receiptPayment.setDocumentFinanceYear(new BigDecimal(getDealYear()));
			receiptPayment.setCustomerName(getCustomerName());
			receiptPayment.setDocumentStatus(Constants.P);
			receiptPayment.setReceiptType("92");
			CurrencyMaster localcurrencyMaster = new CurrencyMaster();
			localcurrencyMaster.setCurrencyId(getLocalCurrencyId());
			receiptPayment.setLocalFsCountryMaster(localcurrencyMaster);
			receiptPayment.setColDocCode(Constants.DOCUMENT_CODE_FOR_STOPPAYMENT);
			receiptPayment.setColDocFyr(new BigDecimal(getDealYear()));



			receiptPayment.setLocalNetAmount(getPayNetPaidAmount());
			receiptPayment.setGeneralLegerDate(new Date());
			receiptPayment.setIsActive(Constants.Yes);
			receiptPayment.setAccountMMYYYY(acc_Month);
			receiptPayment.setTransactionType(null);
			receiptPayment.setDocumentCode(Constants.DOCUMENT_CODE_FOR_STOPPAYMENT);
			if(documentSerialId != null){
				receiptPayment.setDocumentNo(new BigDecimal(documentSerialId));
			}

			receiptPayment.setCustomerReference(getCustomerReference());


			receiptPayment.setTransferReference(getTransferNo());
			receiptPayment.setTransferFinanceYear(getTransferYear());
			
			
			CountryBranch countryBranch = new CountryBranch();
			countryBranch.setCountryBranchId(new BigDecimal(session.getBranchId()));
			receiptPayment.setCountryBranch(countryBranch);
			receiptPayment.setDocumentDate(new Date());


			// purposeoftransaction
			PurposeOfTransaction purposeofTransaction = new PurposeOfTransaction();
			purposeofTransaction.setPurposeId(new BigDecimal(Constants.PURPUSE_ID_FOR_FC));
			receiptPayment.setPurposeOfTransaction(purposeofTransaction);
			SourceOfIncome sourceOfIncome = new SourceOfIncome();
			sourceOfIncome.setSourceId(new BigDecimal(Constants.SOURCE_ID_FOR_FC));
			receiptPayment.setSourceOfIncome(sourceOfIncome);
			receiptPayment.setRemarks(getRemarks());
			receiptPayment.setCreatedDate(new Date());
			receiptPayment.setCreatedBy(session.getUserName());
			receiptPayment.setDocumentId(documentId);
			receiptPayment.setCompanyCode(companyCode);
			receiptPayment.setLocCode(session.getCountryBranchCode());
			receiptPayment.setDocumentFinanceYearId(getDealYearId());
			receiptPayment.setCollectionDocumentId(documentId);
			// getForeignCurrencyPurchaseService().save((T) receiptPayment);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return receiptPayment;
	}

	public Collect concurrentSaveCollect(BigDecimal documentId,BigDecimal companyCode) {
		LOGGER.info("Entering into saveCollect method");
		List<Collect> collectIdDetails = new ArrayList<Collect>();
		Collect collect = new Collect();
		// company Id
		CompanyMaster companymaster = new CompanyMaster();
		companymaster.setCompanyId(session.getCompanyId());
		collect.setFsCompanyMaster(companymaster);
		collect.setApplicationCountryId(session.getCountryId());
		CountryBranch cb = new CountryBranch();
		cb.setCountryBranchId(new BigDecimal(session.getBranchId()));
		collect.setExBankBranch(cb);
		// customer Id
		Customer customer = new Customer();
		customer.setCustomerId(getCustomerId());
		collect.setFsCustomer(customer);
		// document Id
		collect.setDocumentNo(getTransferNo());
		collect.setDocumentCode(Constants.DOCUMENT_CODE_FOR_STOPPAYMENT);
		// Document Financial Year
		collect.setDocumentFinanceYear(new BigDecimal(getDealYear()));
		collect.setCollectDate(new Date());
		// Foriegn Currency id
		if(getForgienCurrencyId()!=null){
			CurrencyMaster forcurrencymaster = new CurrencyMaster();
			forcurrencymaster.setCurrencyId(getLocalCurrencyId());
			collect.setExCurrencyMaster(forcurrencymaster);
		}
		collect.setPaidAmount(getPayPaidAmount());
		collect.setRefoundAmount(getPayRefund());
		collect.setNetAmount(getPayNetPaidAmount());
		collect.setIsActive(Constants.Yes);
		try {
			collect.setAccountMMYYYY(new SimpleDateFormat("dd/MM/yyyy").parse(getCurrentDateWithFormat()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		collect.setGeneralLegerDate(new Date());
		collect.setCreatedBy(session.getUserName());
		collect.setCreatedDate(new Date());
		collect.setCompanyCode(companyCode);
		collect.setDocumentId(documentId);
		collect.setLocCode(session.getCountryBranchCode());
		// iPersonalRemittanceService.saveCollectTableData(collect);
		collectIdDetails.add(collect);
		LOGGER.info("Exit into saveCollect method");
		return collect;
	}

	public ArrayList<CollectDetail> concurrentsaveCollectionDetail(Collect collect,BigDecimal documentId,BigDecimal companyCode) {
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
			// Application CountrycompanyCode
			CountryMaster appcountrymaster = new CountryMaster();
			appcountrymaster.setCountryId(session.getCountryId());
			collectDetails.setFsCountryMaster(appcountrymaster);
			// company Id
			CompanyMaster companymaster = new CompanyMaster();
			companymaster.setCompanyId(collect.getFsCompanyMaster().getCompanyId());
			collectDetails.setFsCompanyMaster(companymaster);
			// document Id
			collectDetails.setDocumentNo(collect.getDocumentNo());
			collectDetails.setDocumentCode(collect.getDocumentCode());
			// Document Financial Year
			collectDetails.setDocumentFinanceYear(collect.getDocumentFinanceYear());
			// Routing Bank Branch
			CountryBranch bankbranch = new CountryBranch();
			bankbranch.setCountryBranchId(new BigDecimal(session.getBranchId()));
			collectDetails.setExBankBranch(bankbranch);
			collectDetails.setDocumentDate(new Date());
			collectDetails.setDocumentLineNo(new BigDecimal(i++));
			// Foriegn Currency id //It should be loccal currency Id
			CurrencyMaster forcurrencymaster = new CurrencyMaster();
			forcurrencymaster.setCurrencyId(collect.getExCurrencyMaster().getCurrencyId());
			collectDetails.setExCurrencyMaster(forcurrencymaster);
			collectDetails.setCollectionMode(lstofPayment.getColpaymentmodeCode());
			if (lstofPayment.getColBankCodeDT() != null) {
				collectDetails.setChequeBankRef(lstofPayment.getColBankCodeDT());
			}
			collectDetails.setCollAmt(lstofPayment.getColAmountDT());
			if (lstofPayment.getColpaymentmodeCode().equalsIgnoreCase(Constants.KNETCode)) {
				collectDetails.setDebitCard(lstofPayment.getColCardNumberDT().toPlainString());
				collectDetails.setDbCardName(lstofPayment.getColNameofCardDT());
				collectDetails.setApprovalNo(lstofPayment.getColApprovalNo());
				if (lstofPayment.getColAuthorizedByDT() != null) {
					collectDetails.setAuthby(lstofPayment.getColAuthorizedByDT());
					collectDetails.setAuthdate(new Date());
				}
			} else if (lstofPayment.getColpaymentmodeCode().equalsIgnoreCase(Constants.ChequeCode)) {
				collectDetails.setChequeRef(lstofPayment.getColchequeRefNo());
				collectDetails.setChequeDate(lstofPayment.getColchequeDate());
				collectDetails.setApprovalNo(lstofPayment.getColApprovalNo());
			}
			collectDetails.setIsActive(Constants.Yes);
			try {
				collectDetails.setAcyymm(new SimpleDateFormat("dd/MM/yyyy").parse(getCurrentDateWithFormat()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			collectDetails.setCreatedBy(session.getUserName());
			collectDetails.setCreatedDate(new Date());
			collectDetails.setPaymentModeId(lstofPayment.getColpaymentmodeIDtypeDT());

			collectDetails.setCompanyCode(companyCode);
			collectDetails.setDocumentId(documentId);
			collectDetails.setLocCode(collect.getLocCode());

			collectionDetailsList.add(collectDetails);
		}
		return collectionDetailsList;
	}

	public List<ForeignCurrencyAdjust> concurrentsaveForeignCurrencyAdjust(BigDecimal exchangeRate,BigDecimal documentId,BigDecimal companyCode) {
		LOGGER.info("Entering into saveForeignCurrencyAdjust method");
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
		// String date = "01/09/14" ;Collect collectid , BigDecimal exchangerate
		// , BigDecimal localnettrastamount

		List<ForeignCurrencyAdjust> list = new ArrayList<ForeignCurrencyAdjust>();


		Date acc_Month = null;
		try {
			acc_Month = DATE_FORMAT.parse(getCurrentDateWithFormat());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		int i = 0;
		LOGGER.info("******************size of the currency" + lstData.size());
		for (ForeignLocalCurrencyDataTable foreignLocalCurrencyDataTable : lstData) {

			ForeignCurrencyAdjust foreignCurrencyAdjust = new ForeignCurrencyAdjust();
			System.out.println(foreignLocalCurrencyDataTable);
			if (foreignLocalCurrencyDataTable.getQty() != null && !foreignLocalCurrencyDataTable.getQty().equals("") && !foreignLocalCurrencyDataTable.getQty().equals("0")) {
				// Company save
				CompanyMaster companyMaster = new CompanyMaster();
				companyMaster.setCompanyId(session.getCompanyId());
				foreignCurrencyAdjust.setFsCompanyMaster(companyMaster);
				// Country Save
				CountryMaster countryMaster = new CountryMaster();
				countryMaster.setCountryId(session.getCountryId());
				foreignCurrencyAdjust.setFsCountryMaster(countryMaster);
				// customer Save
				Customer customer = new Customer();
				customer.setCustomerId(getCustomerId());
				foreignCurrencyAdjust.setFsCustomer(customer);
				foreignCurrencyAdjust.setDocumentDate(new Date());
				// currency Id
				CurrencyMaster currencyMaster = new CurrencyMaster();
				currencyMaster.setCurrencyId(new BigDecimal(session.getCurrencyId()));
				foreignCurrencyAdjust.setFsCurrencyMaster(currencyMaster);
				foreignCurrencyAdjust.setNotesQuantity(new BigDecimal(foreignLocalCurrencyDataTable.getQty()));
				foreignCurrencyAdjust.setAdjustmentAmount(new BigDecimal(foreignLocalCurrencyDataTable.getPrice()));
				CurrencyWiseDenomination denominationMaster = new CurrencyWiseDenomination();
				denominationMaster.setDenominationId(foreignLocalCurrencyDataTable.getDenominationId());
				foreignCurrencyAdjust.setFsDenominationId(denominationMaster);
				foreignCurrencyAdjust.setExchangeRate(exchangeRate);
				foreignCurrencyAdjust.setDenaminationAmount(new BigDecimal(foreignLocalCurrencyDataTable.getPrice()));
				foreignCurrencyAdjust.setDocumentFinanceYear(new BigDecimal(getFinaceYear()));
				foreignCurrencyAdjust.setOracleUser(session.getUserName());
				foreignCurrencyAdjust.setDocumentCode(Constants.DOCUMENT_CODE_FOR_STOPPAYMENT); 
				foreignCurrencyAdjust.setDocumentLineNumber(new BigDecimal(++i));
				foreignCurrencyAdjust.setAccountmmyyyy(acc_Month);
				CountryBranch countryBranch = new CountryBranch();
				countryBranch.setCountryBranchId(new BigDecimal(session.getBranchId()));
				foreignCurrencyAdjust.setCountryBranch(countryBranch);
				foreignCurrencyAdjust.setProgNumber(Constants.STOPR_PAYMENT_PROG);
				foreignCurrencyAdjust.setDocumentStatus(Constants.P);
				foreignCurrencyAdjust.setTransactionType(Constants.C);
				foreignCurrencyAdjust.setCreatedDate(new Date());
				foreignCurrencyAdjust.setCreatedBy(session.getUserName());
				foreignCurrencyAdjust.setDocumentId(documentId);
				foreignCurrencyAdjust.setCompanyCode(companyCode);
				list.add(foreignCurrencyAdjust);
			} else {
				LOGGER.info("Number of notes is 0");
			}
		}


		int j = 0;
		for (ForeignLocalCurrencyDataTable foreignLocalCurrencyDataTable : lstRefundData) {

			if (!foreignLocalCurrencyDataTable.getQty().equals("") && foreignLocalCurrencyDataTable.getQty() != null && !foreignLocalCurrencyDataTable.getQty().equals("0")) {
				ForeignCurrencyAdjust foreignCurrencyAdjust = new ForeignCurrencyAdjust();
				CompanyMaster companyMaster = new CompanyMaster();
				companyMaster.setCompanyId(session.getCompanyId());
				foreignCurrencyAdjust.setFsCompanyMaster(companyMaster);

				CountryMaster countryMaster = new CountryMaster();
				countryMaster.setCountryId(new BigDecimal(session.getSessionValue("countryId")));
				foreignCurrencyAdjust.setFsCountryMaster(countryMaster);
				Customer customer = new Customer();
				customer.setCustomerId(getCustomerId());
				foreignCurrencyAdjust.setFsCustomer(customer);
				foreignCurrencyAdjust.setDocumentDate(new Date());
				CurrencyMaster currencyMaster = new CurrencyMaster();
				currencyMaster.setCurrencyId(new BigDecimal(session.getCurrencyId()));
				foreignCurrencyAdjust.setFsCurrencyMaster(currencyMaster);
				foreignCurrencyAdjust.setDocumentFinanceYear(new BigDecimal(getFinaceYear()));
				foreignCurrencyAdjust.setOracleUser(session.getUserName());
				foreignCurrencyAdjust.setNotesQuantity(new BigDecimal(foreignLocalCurrencyDataTable.getQty()));
				foreignCurrencyAdjust.setAdjustmentAmount(new BigDecimal(foreignLocalCurrencyDataTable.getPrice()));
				CurrencyWiseDenomination denominationMaster = new CurrencyWiseDenomination();
				denominationMaster.setDenominationId(foreignLocalCurrencyDataTable.getDenominationId());
				foreignCurrencyAdjust.setFsDenominationId(denominationMaster);
				foreignCurrencyAdjust.setExchangeRate(exchangeRate);
				foreignCurrencyAdjust.setDenaminationAmount(foreignLocalCurrencyDataTable.getDenominationAmount());
				try {
					foreignCurrencyAdjust.setDocumentCode(Constants.DOCUMENT_CODE_FOR_STOPPAYMENT);
					foreignCurrencyAdjust.setDocumentLineNumber(new BigDecimal(++j));
					foreignCurrencyAdjust.setAccountmmyyyy(acc_Month);
					CountryBranch countryBranch = new CountryBranch();
					countryBranch.setCountryBranchId(new BigDecimal(session.getBranchId()));
					foreignCurrencyAdjust.setCountryBranch(countryBranch);
					foreignCurrencyAdjust.setProgNumber(Constants.STOPR_PAYMENT_PROG);
					foreignCurrencyAdjust.setDocumentStatus(Constants.P);
					foreignCurrencyAdjust.setTransactionType(Constants.F);
				} catch (Exception e) {
					e.printStackTrace();
				}
				foreignCurrencyAdjust.setCreatedDate(new Date());
				foreignCurrencyAdjust.setCreatedBy(session.getUserName());
				foreignCurrencyAdjust.setDocumentId(documentId);
				foreignCurrencyAdjust.setCompanyCode(companyCode);
				/*if (collect != null) {
					foreignCurrencyAdjust.setTempCollection(collect);
				}*/
				list.add(foreignCurrencyAdjust);
			} else {

			}
		}

		return list;
	}

	// fetching Payment Mode Details
	
	// fetch payment Mode
	public void fetchPaymentMode(){
		List<PaymentModeDesc> tempLstPayment = new ArrayList<PaymentModeDesc>();
		tempLstPayment.clear();
		lstFetchAllPayMode.clear();
		
		List<PaymentModeDesc> lstPayment = ipaymentService.fetchPaymodeDescForStopPayment( new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId") : "1"), Constants.Yes);
		if(lstPayment != null && lstPayment.size() != 0){
			
			for (PaymentModeDesc paymentModeDesc : lstPayment) {
				if(paymentModeDesc.getPaymentMode().getPaymentCode() != null && (paymentModeDesc.getPaymentMode().getPaymentCode().equalsIgnoreCase(Constants.CashCode) || 
						paymentModeDesc.getPaymentMode().getPaymentCode().equalsIgnoreCase(Constants.KNETCode) || paymentModeDesc.getPaymentMode().getPaymentCode().equalsIgnoreCase(Constants.BankTransferCode))){
					tempLstPayment.add(paymentModeDesc);
				}
			}
			
			if(tempLstPayment != null){
				lstFetchAllPayMode.addAll(tempLstPayment);
			}
		}
	}

	List<PaymentModeDesc> lstFetchAllPayMode = new ArrayList<PaymentModeDesc>();

	public List<PaymentModeDesc> getLstFetchAllPayMode() {
		return lstFetchAllPayMode;
	}
	public void setLstFetchAllPayMode(List<PaymentModeDesc> lstFetchAllPayMode) {
		this.lstFetchAllPayMode = lstFetchAllPayMode;
	}

	public void initforCollection() throws JRException {
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(StopPaymentCollectionReport);
		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		//String realPath = ctx.getRealPath("//");
		//String reportPath = realPath + "\\reports\\design\\StopPaymentCollectionReport.jasper";
		// String reportPath= FacesContext.getCurrentInstance().getExternalContext().getRealPath("reports/design/StopPaymentCollectionReport.jasper");
		String realPath = ctx.getRealPath("//");
		String reportPath = realPath +"//reports//design//StopPaymentCollectionReport.jasper";

		jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap(), beanCollectionDataSource);
	}

	public void genarateStopPaymentCollectionReport(ActionEvent actionEvent) throws JRException, IOException {
		ServletOutputStream servletOutputStream=null;
		try{
			fetchStopaymentDetails();
			initforCollection();
			HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			httpServletResponse.addHeader("Content-disposition", "attachment; filename=StopPaymentCollectionReport.pdf");
			servletOutputStream = httpServletResponse.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
			FacesContext.getCurrentInstance().responseComplete();
		}catch(Exception e){
			e.getStackTrace();
		}finally{
			if(servletOutputStream!=null){
				servletOutputStream.close();
			}

		}
	}

	private void fetchRequestDetails() {
		StopPaymentCollectionrequest.clear();
		StopPaymentCollectionReport report = new StopPaymentCollectionReport();
		if (null != customer) {
			LOGGER.info("Customer Name" + getCustomerName());
			LOGGER.info("Tele" + getTelephoneNo());
			report.setCustomerName(getCustomerName());
			if(getCustomerReference()!=null){
				report.setCustRef( getCustomerReference().toString());
			}
			report.setTelePhoneNumber(getTelephoneNo());
			List<CustomerIdProof> civilIdList = customerService.getCivilID(customer.getCustomerReference());
			if (civilIdList != null && !civilIdList.isEmpty()) {
				LOGGER.info("Civil ID " + civilIdList.get(0).getIdentityInt());
				report.setCivilId(civilIdList.get(0).getIdentityInt());
			}
			List<ContactDetail> contactdetails = customerService.getCustomerContactDetails(customer.getCustomerId());
			String contactDetails = null;
			if (contactdetails != null && !contactdetails.isEmpty()) {
				LOGGER.info("City master is null " + contactdetails.get(0).getFsCityMaster() == null);
				BigDecimal cityid = null;
				String cityName = "";
				String distName = "";
				String stateName = "";
				String countryName = "";
				if (contactdetails.get(0).getFsCityMaster() != null) {
					cityid = contactdetails.get(0).getFsCityMaster().getCityId();
					List<CityMasterDesc> cityList = cityService.viewDescRecord(cityid);
					for (CityMasterDesc cityMasterDesc : cityList) {
						if (cityMasterDesc.getFsLanguageType().getLanguageId().equals(Constants.ONE)) {
							cityName = cityMasterDesc.getCityName();
						}
					}
				}
				if (contactdetails.get(0).getFsDistrictMaster() != null) {
					try {
						distName = nullCheck(generalService.getDistrictName(session.getLanguageId(), contactdetails.get(0).getFsCountryMaster().getCountryId(), contactdetails.get(0).getFsStateMaster().getStateId(), contactdetails.get(0).getFsDistrictMaster().getDistrictId()));
						stateName = nullCheck(generalService.getStateName(session.getLanguageId(), contactdetails.get(0).getFsCountryMaster().getCountryId(), contactdetails.get(0).getFsStateMaster().getStateId()));
						countryName = nullCheck(generalService.getCountryName(session.getLanguageId(), contactdetails.get(0).getFsCountryMaster().getCountryId()));
					} catch (Exception e) {
						LOGGER.info("District details not found " + contactDetails);
					}
				}
				contactDetails = nullCheck(contactdetails.get(0).getFlat()) + " " + nullCheck(contactdetails.get(0).getBlock()) + " " + nullCheck(contactdetails.get(0).getBuildingNo()) + " " + contactdetails.get(0).getStreet() + cityName + " " + distName + " " + stateName + " " + countryName;
				LOGGER.info("contactDetails " + contactDetails);
			}
			report.setContactDetails(contactdetails == null ? "" : contactDetails);
			LOGGER.info(getBeneficiary());
			report.setBeneDetails(getBeneficiary());
			LOGGER.info("getTransferAmount" + getTransferAmount());
			BigDecimal  stopPaymentAmount=GetRound.roundBigDecimal((getTransferAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency( getLocalCurrencyId()));
			report.setTransferAmount( stopPaymentAmount );
			//	DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
			// Date startDate;
			//try {
			//startDate = (Date)formatter.parse(getReceiptDate());
			report.setTranxDate(getReceiptDate());
			//	} catch (ParseException e) {
			// TODO Auto-generated catch block
			//	e.printStackTrace();
			//}
			report.setTransactionNo(getTransferYear()+" / "+getTransferNo().toString());
			//report.setTransactionNo(getTransactionNoForDisplay()==null?"":getTransactionNoForDisplay().toPlainString() );
			//report.setFinYear(getDealYear());
			report.setBeneBank(getBeneBankName()+" , "+getBeneBranchName()  );
			report.setQuoteName(generalService.getCurrencyQuote(getForgienCurrencyId())+"**************"  );
			ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
			String realPath = ctx.getRealPath("//");
			//String logoPath = realPath + Constants.LOGO_PATH;
			
			List<CompanyMasterDesc> companyList=  generalService.getCompanyList(session.getCompanyId(),session.getLanguageId());
			if(companyList.size()>0){
				report.setCompanyName(companyList.get(0).getCompanyName());
			}
			
			report.setLetterBody(Constants.StopPaymentLetterBody1 + " " + report.getCompanyName() + " " + Constants.StopPaymentLetterBody2 + " " + report.getCompanyName() + " " + Constants.StopPaymentLetterBody3);
			
			String logoPath =null;
			if(session.getCountryName().equalsIgnoreCase(Constants.KUWAIT)){
				logoPath = realPath+Constants.LOGO_PATH;
			}else if(session.getCountryName().equalsIgnoreCase(Constants.OMAN)){
				logoPath = realPath+Constants.LOGO_PATH_OM;
			}else{
				logoPath =realPath+Constants.LOGO_PATH;
			}
			
			LOGGER.info("***************************88");
			LOGGER.info(logoPath);
			LOGGER.info("***************************88");
			report.setLogoPath(logoPath);
			StopPaymentCollectionrequest.add(report);
		}
	}

	private void initforRequest() throws JRException, IOException {
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(StopPaymentCollectionrequest);
		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		//String realPath = ctx.getRealPath("//");
		//String reportPath = realPath + "\\reports\\design\\StopPaymentRequest.jasper";
		String realPath = ctx.getRealPath("//");
		String reportPath = realPath +"//reports//design//StopPaymentRequest.jasper";
		jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap(), beanCollectionDataSource);
	}

	public void genarateStopPaymentrequestReport(ActionEvent actionEvent) throws JRException, IOException {

		ServletOutputStream servletOutputStream=null;
		try{
			fetchRequestDetails();
			initforRequest();
			HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			httpServletResponse.addHeader("Content-disposition", "attachment; filename=StopPaymentRequestReport.pdf");
			servletOutputStream = httpServletResponse.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
			FacesContext.getCurrentInstance().responseComplete();
		}catch(Exception e){
			e.getStackTrace();
		}finally{
			if(servletOutputStream!=null){
				servletOutputStream.close();
			}
		}
	}

	private void fetchStopaymentDetails() {
		StopPaymentCollectionReport.clear();
		StopPaymentCollectionReport report = new StopPaymentCollectionReport();
		if (null != customer) {
			report.setCustomerRefNo(customer.getCustomerReference());
			LOGGER.info("customer.getCustomerReference()" + customer.getCustomerReference());
			LOGGER.info("Customer Name" + getCustomerName());
			LOGGER.info("Tele" + getTelephoneNo());
			report.setCustomerName(getCustomerName());
			report.setTelePhoneNumber(getTelephoneNo());

			report.setCustInfo(customer.getCustomerReference()+"/"+getCustomerNameForReport());
			LOGGER.info("customer.getCustomerReference()" + customer.getCustomerReference());
			LOGGER.info("Customer Name" + getCustomerName());
			LOGGER.info("Tele" + getTelephoneNo());
			report.setTelPhone("(Tel)"+getTelephoneNo());
			report.setCustomerName(getCustomerName());
			report.setProduct(getProductName());
			report.setLocCur(generalService.getCurrencyQuote(getLocalCurrencyId())+" ******");
			report.setTelePhoneNumber(getTelephoneNo());

			NumberFormat usa = NumberFormat.getInstance(Locale.getDefault());
			List<CompanyMasterDesc> companyList=  generalService.getCompanyList(session.getCompanyId(),session.getLanguageId());
			if(companyList.size()>0){
				report.setCompanyName( companyList.get( 0).getCompanyName());
				report.setAddress( companyList.get( 0).getFsCompanyMaster().getAddress1()+","+companyList.get( 0).getFsCompanyMaster().getAddress2()+","+companyList.get( 0).getFsCompanyMaster().getAddress3()+",C.R."+companyList.get( 0).getFsCompanyMaster().getRegistrationNumber()+",Share Capital:K.D."+usa.format(new BigDecimal(companyList.get( 0).getFsCompanyMaster().getCapitalAmount())));
			}


			if(customer.getCustomerReference()!=null){
				report.setCustRef(customer.getCustomerReference().toString());
			}
			String civilId = customerService.getCivilID(customer.getCustomerReference()).toString();
			LOGGER.info("civilId " + civilId);
			report.setCivilId(civilId);
			LOGGER.info("getCustomerId  " + customer.getCustomerId());
			List<ContactDetail> contactdetails = customerService.getCustomerContactDetails(customer.getCustomerId());
			String contactDetails = null;
			if (contactdetails != null && !contactdetails.isEmpty()) {
				/*
				 * BigDecimal cityid =
				 * contactdetails.get(0).getFsCityMaster().getCityId();
				 * LOGGER.info("City Id" + cityid);
				 */
				contactDetails = contactdetails.get(0).getFlat() + " " + contactdetails.get(0).getBlock() + " " + contactdetails.get(0).getBuildingNo() + " "
						+ contactdetails.get(0).getStreet() /* + cityName + */ + " ";
				LOGGER.info("contactDetails " + contactDetails);
			}
			BigDecimal spCharges = getSpCharges();
			report.setContactDetails(contactdetails == null ? "" : contactDetails);
			report.setBeneDetails(getBeneficiary());
			report.setFcAmount(getTransferAmount());
			report.setTransferAmount(getTransferAmount() );
			report.setBeneDetails(getBeneficiary());
			report.setReceiptNoForCollect(getDealYear()+" / "+getReceiptNo());
			report.setEftNoandYear( "NO:"+""+getTransferYear()+"/"+getTransNumForReport());
			if(getReceiptDate()!=null){
				DateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
				String receiptDateString = df1.format(getReceiptDate());
				report.setReceiptDate(receiptDateString );
			}
			//report.setPaymentDate(getReceiptDate());
			if(getPaymentDate()!=null){
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				String testDateString = df.format(getPaymentDate());
				report.setRefDateReport(testDateString );
			}
			report.setRefDate(getPaymentDate());
			report.setFavouringBankAccNo(beanTransaction.getBenefeciaryAccountNo());
			report.setFavouringBankBranch(beanTransaction.getBenefeciaryBranch());
			report.setFavouringBankLocation( beanTransaction.getBenefeciaryFirstName()+","+"A/CNO:"+""+beanTransaction.getBenefeciaryAccountNo());
			
			report.setDrawnBank(getBankForDisplay() );
			report.setDrawnBankBranch(getPayableBranch());
			report.setDrawnBankLocation("");
			report.setFavouringBankName( beanTransaction.getBeneficiaryBank());
			if(getLocalCurrencyId()!=null&&spCharges!=null){
				report.setSpcharges(GetRound.roundBigDecimal(spCharges, foreignLocalCurrencyDenominationService.getDecimalPerCurrency(getLocalCurrencyId())));
			}

			report.setPaymentBy(getPaymentMode());
			report.setAmountPaid(getPayPaidAmount());
			report.setAmountRefund(getPayRefund());
			if(getLocalCurrencyId()!=null&&getPayNetPaidAmount()!=null){
				report.setAmountNet(GetRound.roundBigDecimal(getPayNetPaidAmount(), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(getLocalCurrencyId())));
			}
			ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
			String realPath = ctx.getRealPath("//");
			//String logoPath = realPath + Constants.LOGO_PATH;
			
			String logoPath =null;
			if(session.getCountryName().equalsIgnoreCase(Constants.KUWAIT)){
				logoPath = realPath+Constants.LOGO_PATH;
			}else if(session.getCountryName().equalsIgnoreCase(Constants.OMAN)){
				logoPath = realPath+Constants.LOGO_PATH_OM;
			}else{
				logoPath =realPath+Constants.LOGO_PATH;
			}
			
			
			
			LOGGER.info("***************************88");
			LOGGER.info(logoPath);
			LOGGER.info("***************************88");
			/*List<RemittanceTranxBenificiary> trnxBenificiaries = getiStopPaymentService()
					.viewDetailsTranxBeneficiary(getTransactionId());

		for (RemittanceTranxBenificiary remittancetrnxBenificiary : trnxBenificiaries) {

				refundRequestReport.setFavouringBankAccNo(new BigDecimal(
						remittancetrnxBenificiary.getBeneficiaryAccountNo()));
				refundRequestReport
				.setFavouringBankBranch(remittancetrnxBenificiary
						.getBeneficiaryBranch());
				refundRequestReport.setFavouringBankName(remittancetrnxBenificiary
						.getBeneficiaryBank());
				refundRequestReport
				.setFavouringBankLocation(remittancetrnxBenificiary
						.getBeneficiaryFirstName());

			}*/


			report.setLogoPath(logoPath);
		}
		StopPaymentCollectionReport.add(report);
		//clear();
	}

	public void stopPaymentsuccesseNavigation() {
		LOGGER.info("Entering into stopPaymentsuccesseNavigation method");


		hideAllPanels();
		// clear();
		setTransactionNoForDisplay(getReceiptNo());
		setBooRenderFirstPanel(false);
		setBooRenderDenaminationDetails(false);
		setBooRenderPaymentDetails(false);
		setBooRenderCollection(false);
		setBooRendercollectiondatatable(false);
		// boRenderTotalAmountCalPanel
		setBoRenderTotalAmountCalPanel(false);
		setErrorMessage(null);
		setSuccessPanel(true);
		LOGGER.info("Exit into stopPaymentsuccesseNavigation method");
	}

	public void saveCheck() {
		if (getBooRendercashdenomination()) {
			if (lstData.size() != 0) {
				if (getCashAmount().compareTo(getDenomtotalCash()) == 0) {
					if (getPayRefund().compareTo(BigDecimal.ZERO) > 0) {
						nextpaneltoRefundDenomination();
						setNextOrSaveButton(Constants.Save);
					} else {
						saveAll();
					}
				} else {
					RequestContext.getCurrentInstance().execute("netamountgreater.show();");
				}
			} else {
				RequestContext.getCurrentInstance().execute("denominationerror.show();");
			}
		} else if (getBoorefundPaymentDetails()) {
			if (lstRefundData.size() != 0) {
				if (getCollectedRefundAmount().compareTo(getPayRefund()) == 0) {
					saveAll();
				} else {
					RequestContext.getCurrentInstance().execute("refundamounterror.show();");
				}
			} else {
				RequestContext.getCurrentInstance().execute("denominationerror.show();");
			}
		} else {
			if (getPayRefund().compareTo(BigDecimal.ZERO) > 0) {
				nextpaneltoRefundDenomination();
				setNextOrSaveButton(Constants.Save);
			} else {
				saveAll();
			}
		}
	}

	private String nextOrSaveButton;

	public boolean getBoorefundPaymentDetails() {
		return boorefundPaymentDetails;
	}

	public String getNextOrSaveButton() {
		return nextOrSaveButton;
	}

	public void setNextOrSaveButton(String nextOrSaveButton) {
		this.nextOrSaveButton = nextOrSaveButton;
	}

	// to render panel 8 - Refund Denomination details
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
		refundDenominationData();
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
			List<ViewStock> stockList = iPersonalRemittanceService.toCheckStockForView(session.getCountryId(), session.getUserName(), session.getBranchId(), session.getCompanyId(), session.getCurrencyId());
			int serial = 1;
			for (ViewStock viewStockObj : stockList) {
				ForeignLocalCurrencyDataTable forLocalCurrencyDtObj = new ForeignLocalCurrencyDataTable();
				forLocalCurrencyDtObj.setSerial(serial);
				forLocalCurrencyDtObj.setItem(viewStockObj.getDenominationAmount());
				forLocalCurrencyDtObj.setQty("");
				forLocalCurrencyDtObj.setPrice("");
				if (viewStockObj.getCurrentStock() != null) {
					forLocalCurrencyDtObj.setStock(viewStockObj.getCurrentStock().intValue());
				} else {
					forLocalCurrencyDtObj.setStock(0);
				}
				forLocalCurrencyDtObj.setDenominationId(viewStockObj.getDenominationId());
				forLocalCurrencyDtObj.setCurrencyId(viewStockObj.getCurrencyId());
				forLocalCurrencyDtObj.setDenominationDesc(viewStockObj.getDenominationDEsc());
				forLocalCurrencyDtObj.setDenominationAmount(viewStockObj.getDenominationAmount());
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
		setRefundAmount(getPayRefund());
		setCollectedRefundAmount(new BigDecimal(0));
		setLstRefundData(localLstData);
		if (localLstData.size() != 0) {
			setDenominationchecking(Constants.DenominationAvailable);
		} else {
			setDenominationchecking(Constants.DenominationNotAvailable);
		}
	}

	private String denominationchecking;
	private  BigDecimal transactionNoForDisplay;

	public String getDenominationchecking() {
		return denominationchecking;
	}

	public void setDenominationchecking(String denominationchecking) {
		this.denominationchecking = denominationchecking;
	}



	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public Boolean getBooProcedureDialog() {
		return booProcedureDialog;
	}

	public void setBooProcedureDialog(Boolean booProcedureDialog) {
		this.booProcedureDialog = booProcedureDialog;
	}

	public BigDecimal getTransactionNoForDisplay() {
		return transactionNoForDisplay;
	}

	public void setTransactionNoForDisplay(BigDecimal transactionNoForDisplay) {
		this.transactionNoForDisplay = transactionNoForDisplay;
	}

	public String getBeneBranchName() {
		return beneBranchName;
	}

	public void setBeneBranchName(String beneBranchName) {
		this.beneBranchName = beneBranchName;
	}

	public String getBeneBankName() {
		return beneBankName;
	}

	public void setBeneBankName(String beneBankName) {
		this.beneBankName = beneBankName;
	}

	private List<UserFinancialYear> trnxYearList = new ArrayList<UserFinancialYear>();


	public List<UserFinancialYear> getTrnxYearList() {
		return trnxYearList;
	}

	public void setTrnxYearList(List<UserFinancialYear> trnxYearList) {
		this.trnxYearList = trnxYearList;
	}

	public void fetchComplaintfinanceYear() {

		trnxYearList.clear();
		setDealYear(null);
		try {

			List<UserFinancialYear> lstFinancialYear = stopPaymentService.getTransferYearList();
			if(lstFinancialYear.size() != 0){
				trnxYearList.addAll(lstFinancialYear);
				System.out.println("year============"+getDealYear());
			}



		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::fetchComplaintfinanceYear");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}

	}

	public BigDecimal getCustomerId() {
		return customerId;
	}

	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	public String getCustomerNameForReport() {
		return customerNameForReport;
	}

	public void setCustomerNameForReport(String customerNameForReport) {
		this.customerNameForReport = customerNameForReport;
	}

	public String getDealYear() {
		return dealYear;
	}

	public void setDealYear(String dealYear) {
		this.dealYear = dealYear;
	}

	public void populateCustKnetCardDetails() {
		for (CustomerBank customerBank : lstDebitCard) {
			if (customerBank.getDebitCard().equalsIgnoreCase(getColCardNo().toPlainString())) {
				if (customerBank.getAuthorizedBy() != null) {
					List<DebitAutendicationView> localEmpllist = iPersonalRemittanceService.getdebitAutendicationList();
					setEmpllist(localEmpllist);
					setColAuthorizedby(customerBank.getAuthorizedBy());
					setColpassword(null);
					setBooAuthozed(true);
					break;
				} else {
					setColNameofCard(customerBank.getDebitCardName());
					setColAuthorizedby(null);
					setColpassword(null);
					setBooAuthozed(false);
				}
			}
		}
	}


	public String getBankForDisplay() {
		return bankForDisplay;
	}

	public void setBankForDisplay(String bankForDisplay) {
		this.bankForDisplay = bankForDisplay;
	}

	public BigDecimal getTransNumForReport() {
		return transNumForReport;
	}

	public void setTransNumForReport(BigDecimal transNumForReport) {
		this.transNumForReport = transNumForReport;
	}

	public boolean isBooRenderColBankTransfer() {
		return booRenderColBankTransfer;
	}

	public void setBooRenderColBankTransfer(boolean booRenderColBankTransfer) {
		this.booRenderColBankTransfer = booRenderColBankTransfer;
	}

	public String getColBankTransferBankCode() {
		return colBankTransferBankCode;
	}

	public void setColBankTransferBankCode(String colBankTransferBankCode) {
		this.colBankTransferBankCode = colBankTransferBankCode;
	}

	public Boolean getFocus() {
		return focus;
	}

	public void setFocus(Boolean focus) {
		this.focus = focus;
	}
	
	// check for KNET  - OTHERS 10 not allowed
	public boolean checkKNETBanks(){
		boolean checkStatus = false;
		if(getColpaymentmodeCode() != null && getColpaymentmodeCode().equalsIgnoreCase(Constants.KNETCode)){
			if(getColBankCode() != null && getColBankCode().equalsIgnoreCase("10")){
				checkStatus = true;
			}
		}
		return checkStatus;
	}
	
}
