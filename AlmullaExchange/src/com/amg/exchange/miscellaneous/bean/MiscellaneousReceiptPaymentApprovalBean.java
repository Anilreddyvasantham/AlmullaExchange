package com.amg.exchange.miscellaneous.bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import KNET_WinEPTS_API.Receipt;
import KNET_WinEPTS_API.WinEPTS_Wrapper;

import com.amg.exchange.cancelreissue.service.ICancelReissueService;
import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.foreigncurrency.bean.ForeignLocalCurrencyDataTable;
import com.amg.exchange.foreigncurrency.model.Collect;
import com.amg.exchange.foreigncurrency.model.CollectDetail;
import com.amg.exchange.foreigncurrency.model.CurrencyWiseDenomination;
import com.amg.exchange.foreigncurrency.model.ForeignCurrencyAdjust;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.foreigncurrency.model.ViewReceiptPayment;
import com.amg.exchange.foreigncurrency.service.ForeignLocalCurrencyDenominationService;
import com.amg.exchange.foreigncurrency.service.IForeignCurrencyPurchaseService;
import com.amg.exchange.miscellaneous.model.Payment;
import com.amg.exchange.miscellaneous.model.PaymentDetail;
import com.amg.exchange.miscellaneous.service.IMiscellaneousReceiptPaymentService;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.registration.service.IEncrptionDescriptionService;
import com.amg.exchange.remittance.bean.PersonalRemittanceCollectionDataTable;
import com.amg.exchange.remittance.model.CustomerBank;
import com.amg.exchange.remittance.model.DebitAutendicationView;
import com.amg.exchange.remittance.model.KnetLog;
import com.amg.exchange.remittance.model.PaymentMode;
import com.amg.exchange.remittance.model.PaymentModeDesc;
import com.amg.exchange.remittance.model.ViewBankDetails;
import com.amg.exchange.remittance.model.ViewStock;
import com.amg.exchange.remittance.service.ICustomerBankService;
import com.amg.exchange.remittance.service.IPaymentService;
import com.amg.exchange.remittance.service.IPersonalRemittanceService;
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

/*
 * Author RAHAMATHALI SHAIK
 */
@Component("misReceiptPaymentApproval")
@Scope("session")
public class MiscellaneousReceiptPaymentApprovalBean<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger(MiscellaneousReceiptPaymentApprovalBean.class);

	private BigDecimal documentCode;
	private BigDecimal documentYear;
	private BigDecimal documentNo;
	private Date documentDate;
	private BigDecimal transferRefYear;
	private BigDecimal transferNo;
	private Date transactionDate;
	private BigDecimal customerRef;
	private String customerName;
	private String telephone;
	private BigDecimal currencyId;
	private String currencyName;
	private BigDecimal commision;
	private BigDecimal charges;
	private BigDecimal deliveryCharge;
	private BigDecimal rateAdj;
	private BigDecimal otherAdj;
	private BigDecimal netAmount;
	private String remarks;
	private BigDecimal colchequebankId;
	private String colchequebankCode;
	private String colChequeRef;
	private Date colChequeDate;
	private String colChequeApprovalNo;
	private BigDecimal colCash;
	private Date effectiveMinDate = new Date();
	private String paymentCode;
	private BigDecimal totalDenominationCashEntered;
	private BigDecimal totalRefundCashEntered;
	private BigDecimal receiptPaymentPk;
	private BigDecimal customerId;
	private BigDecimal companyId;
	private BigDecimal totalKnetAmount;
	private BigDecimal totalCashAmount;
	private BigDecimal totalChequeAmount;
	private BigDecimal totalUamount = new BigDecimal(0);

	// for K-NET panel
	private String colBankCode;
	private List<ViewBankDetails> bankMasterList = new ArrayList<ViewBankDetails>();
	private BigDecimal colCardNo;
	private BigDecimal colCardNoLength;
	private List<CustomerBank> lstDebitCard = new ArrayList<CustomerBank>();
	private String colNameofCard;
	private String colApprovalNo;
	private String colAuthorizedby;
	private List<DebitAutendicationView> empllist;
	private String colpassword;
	private String warningMessage;
	private BigDecimal documentYearId;
	private BigDecimal paymentModeId;
	private List<ViewBankDetails> localbankList = new ArrayList<ViewBankDetails>();

	public List<ViewBankDetails> getLocalbankList() {
		return localbankList;
	}

	public void setLocalbankList(List<ViewBankDetails> localbankList) {
		this.localbankList = localbankList;
	}

	private Boolean renderChequePanel = false;
	private Boolean renderApproveButton = false;
	private Boolean renderNextButton = false;
	private Boolean renderDenominationDatatabe = false;
	private Boolean renderRefundDenominationDatatabe = false;
	private Boolean booRendercollectiondatatable = false;
	private Boolean renderPaymentModePanel = false;
	private Boolean renderDenominationApproveButton = false;
	private Boolean renderDenominationNextButton = false;
	private Boolean renderRefundDenominationApproveButton = false;
	private Boolean renderRefundDenominationNextButton = false;
	private Boolean directFromFirstPage = false;
	private Boolean booRenderMulDebit = false;
	private Boolean booRenderSingleDebit = true;
	private Boolean booRenderColDebitCard = false;
	private Boolean booAuthozed = false;
	private Boolean disableIfEdit = false;

	private Boolean renderKnetPanel = false;

	// for check panel
	private String collectionMode;
	private String bankName;
	private BigDecimal chequeNo;
	private Date chequeDate;
	private BigDecimal approvalNo;
	private BigDecimal refundAmount;
	private String paymentMode;
	private BigDecimal totalCashEntered;
	private BigDecimal totalRefund;
	private String exceptionMessage;
	private String excheckCashLimitMessage;
	private BigDecimal errcolCashExistsLimit;
	private BigDecimal colamountKWD;
	private BigDecimal totalbalanceAmount;
	private BigDecimal toalUsedAmount;
	private String cyberPassword;
	private Boolean booEdit = true;

	public BigDecimal getColamountKWD() {
		return colamountKWD;
	}

	public void setColamountKWD(BigDecimal colamountKWD) {
		this.colamountKWD = colamountKWD;
	}

	@Autowired
	ICustomerBankService icustomerBankService;
	@Autowired
	IEncrptionDescriptionService<T> encryptionDescriptionService;

	@Autowired
	IGLTransactionForADocument iglTransactionForADocument;

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

	public String getColpaymentmodeCode() {
		return colpaymentmodeCode;
	}

	public void setColpaymentmodeCode(String colpaymentmodeCode) {
		this.colpaymentmodeCode = colpaymentmodeCode;
	}

	public BigDecimal getPopulatedDebitCardNumber() {
		return populatedDebitCardNumber;
	}

	public void setPopulatedDebitCardNumber(BigDecimal populatedDebitCardNumber) {
		this.populatedDebitCardNumber = populatedDebitCardNumber;
	}

	private BigDecimal colpaymentmodeId;
	private String colpaymentmodeName;
	private String colpaymentmodeCode;
	private BigDecimal populatedDebitCardNumber;

	String knetTranId = null;

	public String getKnetTranId() {
		return knetTranId;
	}

	public void setKnetTranId(String knetTranId) {
		this.knetTranId = knetTranId;
	}

	private String knetIposReceipt;
	private String knetReceiptDate;
	private String knetReceiptTime;

	private List<ViewReceiptPayment> receiptDocYearList;
	private List<ViewReceiptPayment> receiptDocNoList;
	private List<PaymentModeDesc> paymentModeList;
	private ArrayList<ForeignLocalCurrencyDataTable> lstData;
	private ArrayList<ForeignLocalCurrencyDataTable> lstRefundData;
	private List<ViewBankDetails> chequebankMasterList = new ArrayList<ViewBankDetails>();
	// private List<PersonalRemittanceCollectionDataTable> coldatatablevalues;
	private List<ViewBankDetails> localbankListForBankCode = new ArrayList<ViewBankDetails>();

	public List<ViewBankDetails> getLocalbankListForBankCode() {
		return localbankListForBankCode;
	}

	public void setLocalbankListForBankCode(List<ViewBankDetails> localbankListForBankCode) {
		this.localbankListForBankCode = localbankListForBankCode;
	}

	private List<PersonalRemittanceCollectionDataTable> coldatatablevalues = new CopyOnWriteArrayList<PersonalRemittanceCollectionDataTable>();

	public List<PersonalRemittanceCollectionDataTable> getColdatatablevalues() {
		return coldatatablevalues;
	}

	public void setColdatatablevalues(List<PersonalRemittanceCollectionDataTable> coldatatablevalues) {
		this.coldatatablevalues = coldatatablevalues;
	}

	private SessionStateManage sessionManage = new SessionStateManage();
	private HashMap<BigDecimal, String> paymentModeMap;
	// private HashMap<BigDecimal, String> localBankMap;
	private HashMap<String, Object> savingMap;

	@Autowired
	ICancelReissueService<T> cancelReissueSevice;
	@Autowired
	IMiscellaneousReceiptPaymentService<T> miscellaneousReceiptPaymentService;
	@Autowired
	IGeneralService<T> generalService;
	@Autowired
	IPaymentService ipaymentService;
	@Autowired
	IPersonalRemittanceService iPersonalRemittanceService;
	@Autowired
	ForeignLocalCurrencyDenominationService<T> foreignLocalCurrencyDenominationService;
	@Autowired
	IForeignCurrencyPurchaseService<T> foreignCurrencyPurchaseService;

	public Boolean getDisableIfEdit() {
		return disableIfEdit;
	}

	public void setDisableIfEdit(Boolean disableIfEdit) {
		this.disableIfEdit = disableIfEdit;
	}

	public BigDecimal getTotalKnetAmount() {
		return totalKnetAmount;
	}

	public void setTotalKnetAmount(BigDecimal totalKnetAmount) {
		this.totalKnetAmount = totalKnetAmount;
	}

	public BigDecimal getTotalCashAmount() {
		return totalCashAmount;
	}

	public void setTotalCashAmount(BigDecimal totalCashAmount) {
		this.totalCashAmount = totalCashAmount;
	}

	public BigDecimal getTotalChequeAmount() {
		return totalChequeAmount;
	}

	public void setTotalChequeAmount(BigDecimal totalChequeAmount) {
		this.totalChequeAmount = totalChequeAmount;
	}

	public String getColBankCode() {
		return colBankCode;
	}

	public void setColBankCode(String colBankCode) {
		this.colBankCode = colBankCode;
	}

	public List<ViewBankDetails> getBankMasterList() {
		return bankMasterList;
	}

	public BigDecimal getColCardNo() {
		return colCardNo;
	}

	public BigDecimal getColCardNoLength() {
		return colCardNoLength;
	}

	public List<CustomerBank> getLstDebitCard() {
		return lstDebitCard;
	}

	public String getColNameofCard() {
		return colNameofCard;
	}

	public String getColApprovalNo() {
		return colApprovalNo;
	}

	public String getColAuthorizedby() {
		return colAuthorizedby;
	}

	public List<DebitAutendicationView> getEmpllist() {
		return empllist;
	}

	public String getColpassword() {
		return colpassword;
	}

	public Boolean getBooRenderMulDebit() {
		return booRenderMulDebit;
	}

	public Boolean getBooRenderSingleDebit() {
		return booRenderSingleDebit;
	}

	public Boolean getBooRenderColDebitCard() {
		return booRenderColDebitCard;
	}

	public Boolean getBooAuthozed() {
		return booAuthozed;
	}

	public void setBankMasterList(List<ViewBankDetails> bankMasterList) {
		this.bankMasterList = bankMasterList;
	}

	public void setColCardNo(BigDecimal colCardNo) {
		this.colCardNo = colCardNo;
	}

	public void setColCardNoLength(BigDecimal colCardNoLength) {
		this.colCardNoLength = colCardNoLength;
	}

	public void setLstDebitCard(List<CustomerBank> lstDebitCard) {
		this.lstDebitCard = lstDebitCard;
	}

	public void setColNameofCard(String colNameofCard) {
		this.colNameofCard = colNameofCard;
	}

	public void setColApprovalNo(String colApprovalNo) {
		this.colApprovalNo = colApprovalNo;
	}

	public void setColAuthorizedby(String colAuthorizedby) {
		this.colAuthorizedby = colAuthorizedby;
	}

	public void setEmpllist(List<DebitAutendicationView> empllist) {
		this.empllist = empllist;
	}

	public void setColpassword(String colpassword) {
		this.colpassword = colpassword;
	}

	public void setBooRenderMulDebit(Boolean booRenderMulDebit) {
		this.booRenderMulDebit = booRenderMulDebit;
	}

	public void setBooRenderSingleDebit(Boolean booRenderSingleDebit) {
		this.booRenderSingleDebit = booRenderSingleDebit;
	}

	public void setBooRenderColDebitCard(Boolean booRenderColDebitCard) {
		this.booRenderColDebitCard = booRenderColDebitCard;
	}

	public void setBooAuthozed(Boolean booAuthozed) {
		this.booAuthozed = booAuthozed;
	}

	public BigDecimal getCompanyId() {
		return companyId;
	}

	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}

	public BigDecimal getCustomerId() {
		return customerId;
	}

	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	public BigDecimal getReceiptPaymentPk() {
		return receiptPaymentPk;
	}

	public void setReceiptPaymentPk(BigDecimal receiptPaymentPk) {
		this.receiptPaymentPk = receiptPaymentPk;
	}

	public Boolean getDirectFromFirstPage() {
		return directFromFirstPage;
	}

	public void setDirectFromFirstPage(Boolean directFromFirstPage) {
		this.directFromFirstPage = directFromFirstPage;
	}

	public Boolean getRenderRefundDenominationApproveButton() {
		return renderRefundDenominationApproveButton;
	}

	public void setRenderRefundDenominationApproveButton(Boolean renderRefundDenominationApproveButton) {
		this.renderRefundDenominationApproveButton = renderRefundDenominationApproveButton;
	}

	public Boolean getRenderRefundDenominationNextButton() {
		return renderRefundDenominationNextButton;
	}

	public void setRenderRefundDenominationNextButton(Boolean renderRefundDenominationNextButton) {
		this.renderRefundDenominationNextButton = renderRefundDenominationNextButton;
	}

	public BigDecimal getTotalDenominationCashEntered() {
		return totalDenominationCashEntered;
	}

	public BigDecimal getTotalRefundCashEntered() {
		return totalRefundCashEntered;
	}

	public void setTotalDenominationCashEntered(BigDecimal totalDenominationCashEntered) {
		this.totalDenominationCashEntered = totalDenominationCashEntered;
	}

	public void setTotalRefundCashEntered(BigDecimal totalRefundCashEntered) {
		this.totalRefundCashEntered = totalRefundCashEntered;
	}

	public Boolean getRenderDenominationApproveButton() {
		return renderDenominationApproveButton;
	}

	public Boolean getRenderDenominationNextButton() {
		return renderDenominationNextButton;
	}

	public void setRenderDenominationApproveButton(Boolean renderDenominationApproveButton) {
		this.renderDenominationApproveButton = renderDenominationApproveButton;
	}

	public void setRenderDenominationNextButton(Boolean renderDenominationNextButton) {
		this.renderDenominationNextButton = renderDenominationNextButton;
	}

	public List<ViewReceiptPayment> getReceiptDocYearList() {
		return receiptDocYearList;
	}

	public void setReceiptDocYearList(List<ViewReceiptPayment> receiptDocYearList) {
		this.receiptDocYearList = receiptDocYearList;
	}

	/*
	 * public List<ReceiptPayment> getReceiptDocNoList() { return
	 * receiptDocNoList; }
	 */

	/*
	 * public void setReceiptDocNoList(List<ReceiptPayment> receiptDocNoList) {
	 * this.receiptDocNoList = receiptDocNoList; }
	 */

	public List<ViewReceiptPayment> getReceiptDocNoList() {
		return receiptDocNoList;
	}

	public void setReceiptDocNoList(List<ViewReceiptPayment> receiptDocNoList) {
		this.receiptDocNoList = receiptDocNoList;
	}

	public BigDecimal getTotalCashEntered() {
		return totalCashEntered;
	}

	public BigDecimal getTotalRefund() {
		return totalRefund;
	}

	public String getPaymentCode() {
		return paymentCode;
	}

	public void setPaymentCode(String paymentCode) {
		this.paymentCode = paymentCode;
	}

	public String getColChequeRef() {
		return colChequeRef;
	}

	public Date getColChequeDate() {
		return colChequeDate;
	}

	public String getColChequeApprovalNo() {
		return colChequeApprovalNo;
	}

	public Boolean getRenderPaymentModePanel() {
		return renderPaymentModePanel;
	}

	public void setRenderPaymentModePanel(Boolean renderPaymentModePanel) {
		this.renderPaymentModePanel = renderPaymentModePanel;
	}

	public BigDecimal getColchequebankId() {
		return colchequebankId;
	}

	public void setColchequebankId(BigDecimal colchequebankId) {
		this.colchequebankId = colchequebankId;
	}

	public String getColchequebankCode() {
		return colchequebankCode;
	}

	public void setColchequebankCode(String colchequebankCode) {
		this.colchequebankCode = colchequebankCode;
	}

	public void setColChequeRef(String colChequeRef) {
		this.colChequeRef = colChequeRef;
	}

	public void setColChequeDate(Date colChequeDate) {
		this.colChequeDate = colChequeDate;
	}

	public void setColChequeApprovalNo(String colChequeApprovalNo) {
		this.colChequeApprovalNo = colChequeApprovalNo;
	}

	public BigDecimal getColCash() {
		return colCash;
	}

	public void setColCash(BigDecimal colCash) {
		this.colCash = colCash;
	}

	/*
	 * public List<PersonalRemittanceCollectionDataTable>
	 * getColdatatablevalues() { return coldatatablevalues; }
	 */

	/*
	 * public void setColdatatablevalues(
	 * List<PersonalRemittanceCollectionDataTable> coldatatablevalues) {
	 * this.coldatatablevalues = coldatatablevalues; }
	 */

	public List<ViewBankDetails> getChequebankMasterList() {
		return chequebankMasterList;
	}

	public void setChequebankMasterList(List<ViewBankDetails> chequebankMasterList) {
		this.chequebankMasterList = chequebankMasterList;
	}

	public ArrayList<ForeignLocalCurrencyDataTable> getLstRefundData() {
		return lstRefundData;
	}

	public Boolean getBooRendercollectiondatatable() {
		return booRendercollectiondatatable;
	}

	public void setBooRendercollectiondatatable(Boolean booRendercollectiondatatable) {
		this.booRendercollectiondatatable = booRendercollectiondatatable;
	}

	public void setLstRefundData(ArrayList<ForeignLocalCurrencyDataTable> lstRefundData) {
		this.lstRefundData = lstRefundData;
	}

	public Boolean getRenderDenominationDatatabe() {
		return renderDenominationDatatabe;
	}

	public Boolean getRenderRefundDenominationDatatabe() {
		return renderRefundDenominationDatatabe;
	}

	public void setRenderDenominationDatatabe(Boolean renderDenominationDatatabe) {
		this.renderDenominationDatatabe = renderDenominationDatatabe;
	}

	public void setRenderRefundDenominationDatatabe(Boolean renderRefundDenominationDatatabe) {
		this.renderRefundDenominationDatatabe = renderRefundDenominationDatatabe;
	}

	public void setTotalCashEntered(BigDecimal totalCashEntered) {
		this.totalCashEntered = totalCashEntered;
	}

	public void setTotalRefund(BigDecimal totalRefund) {
		this.totalRefund = totalRefund;
	}

	public Date getEffectiveMinDate() {
		return effectiveMinDate;
	}

	public void setEffectiveMinDate(Date effectiveMinDate) {
		this.effectiveMinDate = effectiveMinDate;
	}

	public Boolean getRenderApproveButton() {
		return renderApproveButton;
	}

	public Boolean getRenderNextButton() {
		return renderNextButton;
	}

	public void setRenderApproveButton(Boolean renderApproveButton) {
		this.renderApproveButton = renderApproveButton;
	}

	public void setRenderNextButton(Boolean renderNextButton) {
		this.renderNextButton = renderNextButton;
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

	public String getBankName() {
		return bankName;
	}

	public BigDecimal getChequeNo() {
		return chequeNo;
	}

	public Date getChequeDate() {
		return chequeDate;
	}

	public void setChequeDate(Date chequeDate) {
		this.chequeDate = chequeDate;
	}

	public BigDecimal getApprovalNo() {
		return approvalNo;
	}

	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setCollectionMode(String collectionMode) {
		this.collectionMode = collectionMode;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public void setChequeNo(BigDecimal chequeNo) {
		this.chequeNo = chequeNo;
	}

	public void setApprovalNo(BigDecimal approvalNo) {
		this.approvalNo = approvalNo;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getWarningMessage() {
		return warningMessage;
	}

	public void setWarningMessage(String warningMessage) {
		this.warningMessage = warningMessage;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public Boolean getRenderChequePanel() {
		return renderChequePanel;
	}

	public void setRenderChequePanel(Boolean renderChequePanel) {
		this.renderChequePanel = renderChequePanel;
	}

	public BigDecimal getPaymentModeId() {
		return paymentModeId;
	}

	public List<PaymentModeDesc> getPaymentModeList() {
		return paymentModeList;
	}

	public void setPaymentModeId(BigDecimal paymentModeId) {
		this.paymentModeId = paymentModeId;
	}

	public void setPaymentModeList(List<PaymentModeDesc> paymentModeList) {
		this.paymentModeList = paymentModeList;
	}

	public BigDecimal getDocumentYear() {
		return documentYear;
	}

	public BigDecimal getDocumentYearId() {
		return documentYearId;
	}

	public BigDecimal getDocumentNo() {
		return documentNo;
	}

	public Date getDocumentDate() {
		return documentDate;
	}

	public BigDecimal getTransferRefYear() {
		return transferRefYear;
	}

	public BigDecimal getTransferNo() {
		return transferNo;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public BigDecimal getCustomerRef() {
		return customerRef;
	}

	public String getCustomerName() {
		return customerName;
	}

	public String getTelephone() {
		return telephone;
	}

	public BigDecimal getCurrencyId() {
		return currencyId;
	}

	public BigDecimal getCommision() {
		return commision;
	}

	public BigDecimal getCharges() {
		return charges;
	}

	public BigDecimal getDeliveryCharge() {
		return deliveryCharge;
	}

	public BigDecimal getRateAdj() {
		return rateAdj;
	}

	public BigDecimal getOtherAdj() {
		return otherAdj;
	}

	public BigDecimal getNetAmount() {
		return netAmount;
	}

	public String getRemarks() {
		return remarks;
	}

	public BigDecimal getDocumentCode() {
		return documentCode;
	}

	public void setDocumentCode(BigDecimal documentCode) {
		this.documentCode = documentCode;
	}

	public void setDocumentYear(BigDecimal documentYear) {
		this.documentYear = documentYear;
	}

	public void setDocumentYearId(BigDecimal documentYearId) {
		this.documentYearId = documentYearId;
	}

	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}

	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}

	public void setTransferRefYear(BigDecimal transferRefYear) {
		this.transferRefYear = transferRefYear;
	}

	public void setTransferNo(BigDecimal transferNo) {
		this.transferNo = transferNo;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public void setCustomerRef(BigDecimal customerRef) {
		this.customerRef = customerRef;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	public void setCommision(BigDecimal commision) {
		this.commision = commision;
	}

	public void setCharges(BigDecimal charges) {
		this.charges = charges;
	}

	public void setDeliveryCharge(BigDecimal deliveryCharge) {
		this.deliveryCharge = deliveryCharge;
	}

	public void setRateAdj(BigDecimal rateAdj) {
		this.rateAdj = rateAdj;
	}

	public void setOtherAdj(BigDecimal otherAdj) {
		this.otherAdj = otherAdj;
	}

	public void setNetAmount(BigDecimal netAmount) {
		this.netAmount = netAmount;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public ArrayList<ForeignLocalCurrencyDataTable> getLstData() {
		return lstData;
	}

	public void setLstData(ArrayList<ForeignLocalCurrencyDataTable> lstData) {
		this.lstData = lstData;
	}

	public void clear() {
		coldatatablevalues.clear();
		misDataTableList.clear();
		setLstData(null);
		setCharges(null);
		setNetAmount(null);
		setOtherAdj(null);
		setCommision(null);
		setRateAdj(null);
		setCurrencyName(null);
		setCurrencyId(null);
		setCustomerRef(null);
		setCustomerName(null);
		setDeliveryCharge(null);
		setDocumentDate(null);
		setReceiptPaymentPk(null);
		setCustomerId(null);
		setRemarks(null);
		setTelephone(null);
		setTransactionDate(null);
		setPaymentModeId(null);
		setTransferRefYear(null);
		setTransferNo(null);
		setColCash(null);
		setTotalRefund(null);
		setTotalCashEntered(null);
		setTotalDenominationCashEntered(null);
		setTotalRefundCashEntered(null);
		setCompanyId(null);
		// setColdatatablevalues(null);
	}

	public void getFinanceYearFromdb() {
		try {
			List<UserFinancialYear> applicationYearList = generalService.getDealYear(new Date());
			if (applicationYearList.size() > 0) {
				setDocumentYear(applicationYearList.get(0).getFinancialYear());
				setDocumentYearId(applicationYearList.get(0).getFinancialYearID());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void totalRefundAmountCal() {
		BigDecimal totalEnteredAmount = BigDecimal.ZERO;
		BigDecimal totalRefundAmount = BigDecimal.ZERO;
		if (getColdatatablevalues() != null && getColdatatablevalues().size() > 0) {
			List<PersonalRemittanceCollectionDataTable> misDataTableList1 = getColdatatablevalues();
			for (PersonalRemittanceCollectionDataTable dataTableObj : misDataTableList1) {
				totalEnteredAmount = totalEnteredAmount.add(dataTableObj.getColAmountDT() == null ? BigDecimal.ZERO : dataTableObj.getColAmountDT());
			}
		}
		setTotalCashEntered(totalEnteredAmount);
		if (getNetAmount() != null) {
			// Here totalEnteredAmount > getNetAmount()
			if (totalEnteredAmount.compareTo(getNetAmount()) == 0) {
				totalRefundAmount = totalEnteredAmount.subtract(getNetAmount());
			} else if (totalEnteredAmount.compareTo(getNetAmount()) == 1) {
				totalRefundAmount = totalEnteredAmount.subtract(getNetAmount());
			}/*
			 * else if(totalEnteredAmount.compareTo(getNetAmount())==-1){
			 * totalRefundAmount=BigDecimal.ZERO; }
			 */

		}
		setTotalRefund(totalRefundAmount);
	}

	public void buttonRenderCheck() {
		Boolean cashCheck = false;
		Boolean chequeCheck = false;
		if (getColdatatablevalues() != null && getColdatatablevalues().size() > 0) {
			List<PersonalRemittanceCollectionDataTable> misDataTableList1 = getColdatatablevalues();
			for (PersonalRemittanceCollectionDataTable dataDataObj : misDataTableList1) {
				if (dataDataObj.getColpaymentmodeCode().equalsIgnoreCase(Constants.CashCode)) {
					cashCheck = true;
				} else if (dataDataObj.getColpaymentmodeCode().equalsIgnoreCase(Constants.BankTransferCode)) {
					chequeCheck = true;
				} else if (dataDataObj.getColpaymentmodeCode().equalsIgnoreCase(Constants.ChequeCode)) {
					chequeCheck = true;
				} else if (dataDataObj.getColpaymentmodeCode().equalsIgnoreCase(Constants.KNETCode)) {
					chequeCheck = true;
				} else if (dataDataObj.getColpaymentmodeCode().equalsIgnoreCase(Constants.VOCHERCODE)) {
					chequeCheck = true;
				}
			}

			if (cashCheck) {
				setRenderNextButton(true);
				setRenderApproveButton(false);
			} else if (chequeCheck) {
				if (getTotalRefund() != null) {
					// Here getTotalRefund() >0
					if (getTotalRefund().compareTo(BigDecimal.ZERO) == 1) {
						setRenderNextButton(true);
						setRenderApproveButton(false);
					} else {
						setRenderNextButton(false);
						setRenderApproveButton(true);
					}
				}
			}

		}
	}

	List<PersonalRemittanceCollectionDataTable> misDataTableList = new ArrayList<PersonalRemittanceCollectionDataTable>();

	// adding to datatable list in payment mode
	public void addPaymentModerecord() {
		setDisableIfEdit(false);

		if (getColdatatablevalues() != null && getColdatatablevalues().size() != 0 && getDocumentCode().compareTo(new BigDecimal(Constants.DOCUMENT_CODE_FOR_PAYMENT)) == 0) {
			setColCash(null);
			setBankTransferApprovalNo(null);
			setBooRenderBankTransfer(false);
			setPaymentModeId(null);
			setWarningMessage("Cash or Bank Transfer either one allowed");
			RequestContext.getCurrentInstance().execute("warningDailog.show();");
			return;
		} else {

			PersonalRemittanceCollectionDataTable personalcollectionDT = new PersonalRemittanceCollectionDataTable();

			personalcollectionDT.setColpaymentmodeIDtypeDT(getPaymentModeId());
			personalcollectionDT.setColpaymentmodetypeDT(getPaymentMode());
			personalcollectionDT.setColpaymentmodeCode(getPaymentCode());
			if (getPaymentCode().equalsIgnoreCase(Constants.CashCode) || getPaymentCode().equalsIgnoreCase(Constants.VOCHERCODE) && getPaymentCode().equalsIgnoreCase(Constants.BankTransferCode)) {
				personalcollectionDT.setEditDisable(false);
			}

			if (getPaymentCode().equalsIgnoreCase(Constants.ChequeCode)) {
				personalcollectionDT.setColchequeRefNo(getColChequeRef());
				personalcollectionDT.setColchequeDate(getColChequeDate());
				personalcollectionDT.setColApprovalNo(getColChequeApprovalNo());
				personalcollectionDT.setColBankIdDT(getColchequebankId());
				List<ViewBankDetails> listBank = generalService.getLocalBankListFromView(sessionManage.getCountryId(), getColchequebankId());
				if (listBank.size() > 0) {
					personalcollectionDT.setColBankCodeDT(listBank.get(0).getChequeBankCode());
					personalcollectionDT.setColbankNameDT(listBank.get(0).getBankFullName());
				}
				/*
				 * if(getColchequebankId()!=null){
				 * personalcollectionDT.setColbankNameDT
				 * (localBankMap.get(getColchequebankId())); }
				 */
				personalcollectionDT.setEditDisable(false);
			} else if (getPaymentCode().equalsIgnoreCase(Constants.KNETCode)) {

				// List<ViewBankDetails>
				// listBank=generalService.getLocalBankListFromView(sessionManage.getCountryId(),getColchequebankId());
				if (localbankListForBankCode.size() > 0) {
					personalcollectionDT.setColbankNameDT(localbankListForBankCode.get(0).getBankFullName());
				}
				personalcollectionDT.setColBankCodeDT(getColBankCode());
				personalcollectionDT.setColCardNumberDT(getColCardNo());
				personalcollectionDT.setColNameofCardDT(getColNameofCard());
				personalcollectionDT.setColAuthorizedByDT(getColAuthorizedby());
				personalcollectionDT.setColApprovalNo(getColApprovalNo());
				personalcollectionDT.setKnetReceiptDT(getKnetIposReceipt());
				personalcollectionDT.setKnetTransIdDT(getKnetTranId());
				personalcollectionDT.setKneRceiptTimeDT(getKnetReceiptDate());
				personalcollectionDT.setBooDisbale(true);
				personalcollectionDT.setEditDisable(null);

			}

			if (isBooRenderBankTransfer()) {
				personalcollectionDT.setColApprovalNo(getBankTransferApprovalNo());
				personalcollectionDT
						.setColAmountDT(GetRound.roundBigDecimal(getColCash(), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId()))));
				personalcollectionDT.setColBankIdDT(getColchequebankId());
				List<ViewBankDetails> listBank = generalService.getLocalBankListFromView(sessionManage.getCountryId(), getColchequebankId());
				if (listBank.size() > 0) {
					personalcollectionDT.setColBankCodeDT(listBank.get(0).getChequeBankCode());
					personalcollectionDT.setColbankNameDT(listBank.get(0).getBankFullName());
				}
				/*
				 * if(getColchequebankId()!=null){
				 * personalcollectionDT.setColbankNameDT
				 * (localBankMap.get(getColchequebankId())); }
				 */
			}

			if (getColCash() != null) {
				personalcollectionDT
						.setColAmountDT(GetRound.roundBigDecimal(getColCash(), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId()))));
			}
			misDataTableList.add(personalcollectionDT);

			setColdatatablevalues(misDataTableList);

			clearChequeValues();
			setBooRendercollectiondatatable(true);

			if (getColdatatablevalues() != null && getColdatatablevalues().size() > 0) {
				totalRefundAmountCal();
				buttonRenderCheck();
			}
		}
	}

	public void editDebitCardtoEnter() {
		setBooRenderSingleDebit(true);
		setBooRenderMulDebit(false);
		setColCardNo(null);
		setPopulatedDebitCardNumber(null);
	}

	/*
	 * public void addChequeRecords(){ setDisableIfEdit(false);
	 * if(getPaymentCode().equalsIgnoreCase(Constants.BankTransferCode)){
	 * List<PersonalRemittanceCollectionDataTable> misDataTableList =new
	 * ArrayList<PersonalRemittanceCollectionDataTable>();
	 * PersonalRemittanceCollectionDataTable collectionDtObj = new
	 * PersonalRemittanceCollectionDataTable();
	 * collectionDtObj.setColpaymentmodeCode(Constants.CHEQUENAME);
	 * collectionDtObj.setColpaymentmodetypeDT(Constants.ChequeCode);
	 * collectionDtObj.setColpaymentmodeIDtypeDT(getPaymentModeId());
	 * collectionDtObj.setColBankIdDT(getColchequebankId());
	 * if(getColchequebankId()!=null){
	 * collectionDtObj.setColbankNameDT(localBankMap.get(getColchequebankId()));
	 * }
	 * 
	 * collectionDtObj.setColchequeRefNo(getColChequeRef());
	 * collectionDtObj.setColchequeDate(getColChequeDate());
	 * collectionDtObj.setColApprovalNo(getColChequeApprovalNo());
	 * collectionDtObj.setColAmountDT(getColCash());
	 * 
	 * misDataTableList.add(collectionDtObj);
	 * 
	 * if(getColdatatablevalues()!=null && getColdatatablevalues()!=null){
	 * List<PersonalRemittanceCollectionDataTable> misDataTableList1 =
	 * getColdatatablevalues(); misDataTableList1.addAll(misDataTableList);
	 * setColdatatablevalues(misDataTableList1); }else{
	 * setColdatatablevalues(misDataTableList); }
	 * 
	 * clearChequeValues(); setBooRendercollectiondatatable(true); }else
	 * if(getPaymentCode().equals(Constants.CashCode)){
	 * List<PersonalRemittanceCollectionDataTable> misDataTableList =new
	 * ArrayList<PersonalRemittanceCollectionDataTable>();
	 * PersonalRemittanceCollectionDataTable collectionDtObj = new
	 * PersonalRemittanceCollectionDataTable();
	 * collectionDtObj.setColpaymentmodeIDtypeDT(getPaymentModeId());
	 * collectionDtObj.setColpaymentmodeCode(Constants.CASHNAME);
	 * collectionDtObj.setColpaymentmodetypeDT(Constants.CashCode);
	 * collectionDtObj.setColAmountDT(getColCash());
	 * misDataTableList.add(collectionDtObj);
	 * 
	 * if(getColdatatablevalues()!=null && getColdatatablevalues().size()>0){
	 * List<PersonalRemittanceCollectionDataTable> misDataTableList1 =
	 * getColdatatablevalues(); misDataTableList1.addAll(misDataTableList);
	 * setColdatatablevalues(misDataTableList1); }else{
	 * setColdatatablevalues(misDataTableList); } clearCashValues();
	 * setBooRendercollectiondatatable(true); }
	 * 
	 * if(getColdatatablevalues()!=null && getColdatatablevalues().size()>0){
	 * totalRefundAmountCal(); buttonRenderCheck(); } }
	 */

	/*
	 * public void changeofPaymentMode(){ try{ //BigDecimal cashPaymentModeId =
	 * ipaymentService.fetchPaymodeMasterId(Constants.CASHNAME,
	 * sessionManage.getLanguageId()); //BigDecimal chequePaymentModeId =
	 * ipaymentService.fetchPaymodeMasterId(Constants.CHEQUENAME,
	 * sessionManage.getLanguageId()); //BigDecimal knetPaymentModeId =
	 * ipaymentService.fetchPaymodeMasterId(Constants.KNET,
	 * sessionManage.getLanguageId()); setColCash(null);
	 * 
	 * if(getPaymentModeId().equals(cashPaymentModeId)){
	 * if(getColdatatablevalues()!=null && getColdatatablevalues().size()>0){
	 * for(PersonalRemittanceCollectionDataTable datatableObj :
	 * getColdatatablevalues()){
	 * if(datatableObj.getColpaymentmodeIDtypeDT().equals(cashPaymentModeId)){
	 * setPaymentModeId(null);
	 * setWarningMessage(WarningHandler.showWarningMessage
	 * ("lbl.thisrecordalreadyaddedtodatatable",
	 * sessionManage.getLanguageId()));
	 * RequestContext.getCurrentInstance().execute("warningDailog.show();");
	 * return; } } } }
	 * 
	 * if(getDocumentNo()!=null && getDocumentYear()!=null){
	 * setPaymentCode(null); if(!getPaymentModeId().equals(BigDecimal.ZERO)){
	 * if(getPaymentModeId().equals(cashPaymentModeId)){
	 * setPaymentCode(Constants.CASHNAME); setRenderChequePanel(false); }else
	 * if(getPaymentModeId().equals(chequePaymentModeId)){
	 * setPaymentCode(Constants.CHEQUENAME); setRenderChequePanel(true);
	 * displayLocalBanks(); }else
	 * if(getPaymentModeId().equals(knetPaymentModeId)){
	 * setPaymentCode(Constants.KNET); setRenderChequePanel(false); }else{
	 * setRenderChequePanel(false); setPaymentModeId(null);
	 * setWarningMessage(WarningHandler.showWarningMessage("lbl.serviceNotImpl",
	 * sessionManage.getLanguageId()));
	 * RequestContext.getCurrentInstance().execute("warningDailog.show();"); } }
	 * 
	 * }else{ setPaymentModeId(null);
	 * setWarningMessage(WarningHandler.showWarningMessage
	 * ("lbl.documentYearandNumber", sessionManage.getLanguageId()));
	 * RequestContext.getCurrentInstance().execute("warningDailog.show();"); }
	 * }catch(Exception e){ setWarningMessage(e.getMessage());
	 * RequestContext.getCurrentInstance().execute("warningDailog.show();"); }
	 * 
	 * }
	 */

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

	// change by payment mode - cash and debit card
	public void changeofPaymentMode() {
		List<PaymentModeDesc> lstofPayment = ipaymentService.getPaymentDescLangList(new BigDecimal(sessionManage.isExists("languageId") ? sessionManage.getSessionValue("languageId") : "1"));
		// Boolean checkCash = false;
		String paymentModedesc = null;
		String paymentModeCode = null;
		setBooColApprovalNo(false);

		if (lstofPayment.size() != 0) {
			for (PaymentModeDesc paymentModeDesc : lstofPayment) {
				if ((getPaymentModeId() == null ? BigDecimal.ZERO : getPaymentModeId()).compareTo(paymentModeDesc.getPaymentMode().getPaymentModeId()) == 0) {
					paymentModedesc = paymentModeDesc.getLocalPaymentName();
					paymentModeCode = paymentModeDesc.getPaymentMode().getPaymentCode();
					setPaymentMode(paymentModedesc);
					setPaymentCode(paymentModeCode);
					break;
				} else {
					// paymentModedesc = null;
					setPaymentMode(null);
					setPaymentCode(null);
				}
			}
			if (getPaymentCode() != null) {
				List<PaymentMode> paymentModedetails = ipaymentService.getPaymentCheck(getPaymentCode());
				if (paymentModedetails.size() != 0) {
					// payment mode bank variables
					setColBankCode(null);
					setColCardNo(null);
					// setPopulatedDebitCardNumber(null);
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
					// payment mode bank transfer
					setBankTransferApprovalNo(null);

					if (getPaymentCode().equalsIgnoreCase(Constants.CashCode)) {
						setBooRenderColDebitCard(false);
						setRenderChequePanel(false);
						setBooRenderBankTransfer(false);
						setPaymentMode(Constants.CASHNAME);
						bankMasterList.clear();
					} else if (getPaymentCode().equalsIgnoreCase(Constants.KNETCode)) {
						setRenderChequePanel(false);
						setBooRenderColDebitCard(true);
						setBooRenderBankTransfer(false);
						setPaymentMode(Constants.KNET);
						displayLocalBanks();
					} else if (getPaymentCode().equalsIgnoreCase(Constants.ChequeCode)) {
						setBooRenderColDebitCard(false);
						setRenderChequePanel(true);
						setBooRenderBankTransfer(false);
						setPaymentMode(Constants.CHEQUENAME);
						displayLocalBanks();
					} else if (getPaymentCode().equalsIgnoreCase(Constants.BankTransferCode)) {
						setBooRenderColDebitCard(false);
						setRenderChequePanel(false);
						setBooRenderBankTransfer(true);
						setRenderKnetPanel(false);
						setPaymentMode(Constants.BANKTRANSFER);
						displayLocalBanks();
					} else if (getPaymentCode().equalsIgnoreCase(Constants.VOCHERCODE)) {
						setBooRenderColDebitCard(false);
						setRenderChequePanel(false);
						setBooRenderBankTransfer(false);
						setPaymentMode(Constants.VOUCHER);
						bankMasterList.clear();
					} else {

						setPaymentModeId(null);
						setBooRenderColDebitCard(false);
						setRenderChequePanel(false);
						setBooRenderBankTransfer(false);

						System.out.println("Payment Mode Newly added");
						RequestContext.getCurrentInstance().execute("checkPaymentModeService.show();");
						return;
					}
				}
			} else {
				setBooRenderColDebitCard(false);
				setRenderChequePanel(false);
				setBooRenderBankTransfer(false);
			}

		}

	}

	public void clearChequeValues() {
		setColchequebankId(null);
		setColchequebankCode(null);
		setColChequeRef(null);
		setColChequeDate(null);
		setColChequeApprovalNo(null);
		setColCash(null);
		setBankTransferApprovalNo(null);
		setBooRenderBankTransfer(false);
		setPaymentModeId(null);
	}

	public void clearCashValues() {
		setColCash(null);
		setPaymentModeId(null);
	}

	public void displayRefundStock(List<ViewStock> stockList) {

		ArrayList<ForeignLocalCurrencyDataTable> localLstData = new ArrayList<ForeignLocalCurrencyDataTable>();
		setLstRefundData(null);
		setTotalRefundCashEntered(null);

		if (stockList.size() > 0) {
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
		setLstRefundData(localLstData);
	}

	public void displayCurrencyDenomination() {
		// ArrayList<ForeignLocalCurrencyDataTable> lstData = new
		// ArrayList<ForeignLocalCurrencyDataTable>();
		setLstData(null);
		setTotalDenominationCashEntered(null);
		ArrayList<ForeignLocalCurrencyDataTable> localLstData = new ArrayList<ForeignLocalCurrencyDataTable>();
		localLstData.clear();
		if (localLstData.size() == 0) {
			List<CurrencyWiseDenomination> currencyWiseDenolst = iPersonalRemittanceService.getCurrencyDenominations(new BigDecimal(sessionManage.getCurrencyId()), sessionManage.getCountryId());
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

		setLstData(localLstData);
	}

	public void fetchDocumentData() {
		try {
			clear();
			setRenderPaymentModePanel(true);
			setRenderDenominationDatatabe(false);
			setRenderRefundDenominationDatatabe(false);
			setBooRendercollectiondatatable(false);
			setRenderChequePanel(false);
			setDirectFromFirstPage(false);
			setDisableIfEdit(false);
			// callPaymentMode();
			List<ViewReceiptPayment> paymentDetailsList = miscellaneousReceiptPaymentService.getReceiptPaymentForApproval(sessionManage.getCountryId(), sessionManage.getCompanyId(),
					getDocumentCode(), getDocumentYear(), getDocumentNo());

			if (paymentDetailsList != null && paymentDetailsList.size() > 0) {
				ViewReceiptPayment receiptObj = paymentDetailsList.get(0);
				if (receiptObj.getIsActive().equalsIgnoreCase(Constants.Yes)) {
					RequestContext.getCurrentInstance().execute("alreadyApprove.show();");
					clear();
					setRenderPaymentModePanel(true);
					setRenderDenominationDatatabe(false);
					setRenderRefundDenominationDatatabe(false);
					setBooRendercollectiondatatable(false);
					setRenderChequePanel(false);
					setDirectFromFirstPage(false);
					setDisableIfEdit(false);
					setDocumentNo(null);
					// callPaymentMode();
					return;
				} else {
					if ((receiptObj.getModifiedBy() == null ? receiptObj.getCreatedBy() : receiptObj.getModifiedBy()).equalsIgnoreCase(sessionManage.getUserName())) {
						RequestContext.getCurrentInstance().execute("username.show();");
						return;
					} else {

						setReceiptPaymentPk(receiptObj.getReceiptId());
						setCustomerRef(receiptObj.getCustomerReference());
						setCurrencyId(new BigDecimal(sessionManage.getCurrencyId()));
						setRemarks(receiptObj.getRemarks());
						setDocumentYear(receiptObj.getDocumentFinanceYear());
						setDocumentDate(receiptObj.getDocumentDate());
						setTransferRefYear(receiptObj.getTransferFinanceYear());
						setTransferNo(receiptObj.getTransferReference());
						setCustomerName(receiptObj.getCustomerName());
						if (getTransferNo() != null && getTransactionDate() != null) {
							setTransactionDate(receiptObj.getDocumentDate());
						}
						setRemarks(receiptObj.getRemarks());
						setCompanyId(receiptObj.getFsCompanyMaster());

						if (receiptObj.getCustomerReference() != null) {
							List<Customer> customerList = miscellaneousReceiptPaymentService.getCustomerDetails(receiptObj.getCustomerReference());
							if (customerList != null && customerList.size() > 0) {
								Customer customer = customerList.get(0);
								setCustomerRef(customer.getCustomerReference());
								setTelephone(customer.getMobile());
								setCustomerId(customer.getCustomerId());
							}
						}

						if (getCurrencyId() != null) {
							String currenyName = generalService.getCurrencyName(getCurrencyId());
							setCurrencyName(currenyName);
						}

						BigDecimal commision = BigDecimal.ZERO;
						BigDecimal charges = BigDecimal.ZERO;
						BigDecimal deliverCharge = BigDecimal.ZERO;
						BigDecimal rateAdjust = BigDecimal.ZERO;
						BigDecimal otherAdjust = BigDecimal.ZERO;
						BigDecimal netAmount = BigDecimal.ZERO;

						commision = receiptObj.getLocalCommisionAmoumnt() == null ? BigDecimal.ZERO : receiptObj.getLocalCommisionAmoumnt();
						charges = receiptObj.getLocalChargeAmount() == null ? BigDecimal.ZERO : receiptObj.getLocalChargeAmount();
						deliverCharge = receiptObj.getLocalDeliveryAmount() == null ? BigDecimal.ZERO : receiptObj.getLocalDeliveryAmount();
						rateAdjust = receiptObj.getLocalRateAmount() == null ? BigDecimal.ZERO : receiptObj.getLocalRateAmount();
						otherAdjust = receiptObj.getLocalOtherAdjAmount() == null ? BigDecimal.ZERO : receiptObj.getLocalOtherAdjAmount();
						netAmount = receiptObj.getLocalNetAmount() == null ? BigDecimal.ZERO : receiptObj.getLocalNetAmount();

						commision = GetRound.roundBigDecimal((commision), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(getCurrencyId()));
						charges = GetRound.roundBigDecimal((charges), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(getCurrencyId()));
						deliverCharge = GetRound.roundBigDecimal((deliverCharge), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(getCurrencyId()));
						rateAdjust = GetRound.roundBigDecimal((rateAdjust), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(getCurrencyId()));
						otherAdjust = GetRound.roundBigDecimal((otherAdjust), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(getCurrencyId()));
						netAmount = GetRound.roundBigDecimal((netAmount), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(getCurrencyId()));

						setCommision(commision);
						setCharges(charges);
						setDeliveryCharge(deliverCharge);
						setRateAdj(rateAdjust);
						setOtherAdj(otherAdjust);
						setNetAmount(netAmount);
					}
				}

			} else {
				// setWarningMessage(WarningHandler.showWarningMessage("lbl.noRecordFound",
				// sessionManage.getLanguageId()));
				RequestContext.getCurrentInstance().execute("warningDailog1.show();");
				setDocumentNo(null);
				return;
			}

		} catch (Exception e) {
			setWarningMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("warningDailog.show();");
		}

	}

	public void onRefundCellEdit(ForeignLocalCurrencyDataTable foreignLocalCurrencyDataTable) {
		try {

			int quantity = 0;
			if (foreignLocalCurrencyDataTable.getQty() == "" && foreignLocalCurrencyDataTable.getQty() != null) {
				quantity = 0;
			} else {
				try {
					quantity = Integer.parseInt(foreignLocalCurrencyDataTable.getQty());
				} catch (Exception e) {
					System.out.println("Exception occured" + e);
					quantity = 0;
				}
			}

			if (quantity <= foreignLocalCurrencyDataTable.getStock()) {
				if (foreignLocalCurrencyDataTable.getStock() >= quantity && quantity != 0) {
					BigDecimal totalcashAmount = foreignLocalCurrencyDataTable.getItem().multiply(
							new BigDecimal(foreignLocalCurrencyDataTable.getQty() == "" ? "0" : foreignLocalCurrencyDataTable.getQty()));
					if (foreignLocalCurrencyDataTable.getQty().equals("")) {
						foreignLocalCurrencyDataTable.setQty("");
						foreignLocalCurrencyDataTable.setPrice("");
					}
					// Here totalcashAmount !=0
					if (totalcashAmount != null && totalcashAmount.compareTo(BigDecimal.ZERO) == 1) {
						foreignLocalCurrencyDataTable.setPrice(GetRound.roundBigDecimal(totalcashAmount,
								foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId()))).toPlainString());
					} else {
						foreignLocalCurrencyDataTable.setPrice("");
					}
				} else {
					foreignLocalCurrencyDataTable.setQty("");
					foreignLocalCurrencyDataTable.setPrice("");
				}
			} else {
				foreignLocalCurrencyDataTable.setQty("");
				foreignLocalCurrencyDataTable.setPrice("");
				setWarningMessage(WarningHandler.showWarningMessage("lbl.enternoofnoteswithincurrentstock", sessionManage.getLanguageId()));
				RequestContext.getCurrentInstance().execute("warningDailog.show();");
			}

			BigDecimal totalSum = BigDecimal.ZERO;
			for (ForeignLocalCurrencyDataTable element : lstRefundData) {
				if (element.getPrice().length() != 0) {
					totalSum = totalSum.add(new BigDecimal(element.getPrice()));
				}
			}

			if (getDocumentCode().compareTo(new BigDecimal(Constants.DOCUMENT_CODE_FOR_PAYMENT)) == 0) {
				setTotalRefundCashEntered(totalSum);
				if (getNetAmount() != null) {
					if (getNetAmount().compareTo(getTotalRefundCashEntered()) >= 0) {
						setTotalRefund(getNetAmount().subtract(getTotalRefundCashEntered()));
						setRenderRefundDenominationApproveButton(true);
						setRenderRefundDenominationNextButton(false);
					} else {
						setTotalRefund(getTotalRefundCashEntered().subtract(getNetAmount()));
						setRenderRefundDenominationApproveButton(false);
						setRenderRefundDenominationNextButton(true);
					}
				}

				/*
				 * if(getTotalRefund() != null &&
				 * getTotalRefund().compareTo(BigDecimal.ZERO)==0){
				 * setRenderRefundDenominationApproveButton(true);
				 * setRenderRefundDenominationNextButton(false); }else{
				 * setRenderRefundDenominationApproveButton(false);
				 * setRenderRefundDenominationNextButton(true); }
				 */

			} else if (getDocumentCode().compareTo(new BigDecimal(Constants.DOCUMENT_CODE_FOR_RECEIVE)) == 0) {
				if (totalSum != null && totalSum.compareTo(BigDecimal.ZERO) != 0) {
					if (totalSum.compareTo(getTotalRefund()) <= 0) {
						// allow to enter
					} else {
						foreignLocalCurrencyDataTable.setPrice("");
						foreignLocalCurrencyDataTable.setQty("");
					}
				} else {
					foreignLocalCurrencyDataTable.setPrice("");
					foreignLocalCurrencyDataTable.setQty("");
				}
				BigDecimal totalCash = BigDecimal.ZERO;
				for (ForeignLocalCurrencyDataTable element : lstRefundData) {
					if (element.getPrice().length() != 0) {
						totalCash = totalCash.add(new BigDecimal(element.getPrice()));
					}
				}
				if (totalCash != null) {
					setTotalRefundCashEntered(totalCash);
				}

			} else {
				// both not
			}

		} catch (Exception e) {
			setWarningMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("warningDailog.show();");
		}
	}

	public void editRecord(ForeignLocalCurrencyDataTable denominationBean) {

		try {

			String quantity = (denominationBean.getQty() == "" ? "0" : denominationBean.getQty());
			if (denominationBean.getItem() != null) {
				if (!quantity.trim().equals("")) {
					System.out.println("*********************" + quantity);
					BigDecimal purchageAmount = denominationBean.getItem().multiply(new BigDecimal(quantity));
					// Here purchageAmount!=0
					if (purchageAmount != null && purchageAmount.compareTo(BigDecimal.ZERO) == 1) {
						denominationBean.setPrice(GetRound
								.roundBigDecimal(purchageAmount, foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId()))).toPlainString());
					} else {
						denominationBean.setPrice("");
					}
				} else {
					denominationBean.setPrice("");
				}
			}
			BigDecimal totalCashEntered = BigDecimal.ZERO;
			for (ForeignLocalCurrencyDataTable foreignCurrencyObj : getLstData()) {
				if (foreignCurrencyObj.getPrice() != null && foreignCurrencyObj.getPrice().length() != 0) {
					totalCashEntered = totalCashEntered.add(new BigDecimal(foreignCurrencyObj.getPrice()));
				}
			}

			if (getDocumentCode().compareTo(new BigDecimal(Constants.DOCUMENT_CODE_FOR_PAYMENT)) == 0) {
				if (totalCashEntered != null && totalCashEntered.compareTo(BigDecimal.ZERO) != 0) {
					if (totalCashEntered.compareTo(getTotalRefund()) <= 0) {
						// allow to enter
					} else {
						denominationBean.setPrice("");
						denominationBean.setQty("");
					}
				} else {
					denominationBean.setPrice("");
					denominationBean.setQty("");
				}

				BigDecimal totalCash = BigDecimal.ZERO;
				for (ForeignLocalCurrencyDataTable foreignCurrencyObj : getLstData()) {
					if (foreignCurrencyObj.getPrice() != null && foreignCurrencyObj.getPrice().length() != 0) {
						totalCash = totalCash.add(new BigDecimal(foreignCurrencyObj.getPrice()));
					}
				}
				if (totalCash != null) {
					setTotalDenominationCashEntered(totalCash);
				}

			} else if (getDocumentCode().compareTo(new BigDecimal(Constants.DOCUMENT_CODE_FOR_RECEIVE)) == 0) {
				if (totalCashEntered != null && totalCashEntered.compareTo(BigDecimal.ZERO) != 0) {
					if (totalCashEntered.compareTo(getTotalCashAmount()) <= 0) {
						// allow to enter
					} else {
						denominationBean.setPrice("");
						denominationBean.setQty("");
					}
				} else {
					denominationBean.setPrice("");
					denominationBean.setQty("");
				}

				BigDecimal totalCash = BigDecimal.ZERO;
				for (ForeignLocalCurrencyDataTable foreignCurrencyObj : getLstData()) {
					if (foreignCurrencyObj.getPrice() != null && foreignCurrencyObj.getPrice().length() != 0) {
						totalCash = totalCash.add(new BigDecimal(foreignCurrencyObj.getPrice()));
					}
				}
				if (totalCash != null) {
					setTotalDenominationCashEntered(totalCash);
				}
			} else {
				// both not
			}

		} catch (Exception e) {
			setWarningMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("warningDailog.show();");
		}
	}

	public void nextToRefundDenomination() {
		try {
			if (getDocumentCode().compareTo(new BigDecimal(Constants.DOCUMENT_CODE_FOR_PAYMENT)) == 0) {
				if (getNetAmount() != null && getTotalCashEntered() != null && getNetAmount().compareTo(getTotalCashEntered()) == 0) {
					List<ViewStock> stockList = iPersonalRemittanceService.toCheckStockForView(sessionManage.getCountryId(), sessionManage.getUserName(), sessionManage.getBranchId(),
							sessionManage.getCompanyId(), sessionManage.getCurrencyId());
					if (stockList != null && stockList.size() > 0) {
						displayRefundStock(stockList);
						setRenderDenominationDatatabe(false);
						setRenderRefundDenominationDatatabe(true);
						setRenderPaymentModePanel(false);
						setTotalRefundCashEntered(null);
						setRenderRefundDenominationApproveButton(true);
						setRenderRefundDenominationNextButton(false);
					} else {
						setWarningMessage(WarningHandler.showWarningMessage("lbl.refundStockNotFound", sessionManage.getLanguageId()));
						RequestContext.getCurrentInstance().execute("warningDailog.show();");
					}
				} else {
					setWarningMessage(WarningHandler.showWarningMessage("lbl.totalCashAmountAndCashReceived", sessionManage.getLanguageId()));
					RequestContext.getCurrentInstance().execute("warningDailog.show();");
				}
			} else if (getDocumentCode().compareTo(new BigDecimal(Constants.DOCUMENT_CODE_FOR_RECEIVE)) == 0) {

				if (getTotalDenominationCashEntered() != null && getTotalCashAmount() != null) {
					if (getTotalDenominationCashEntered().compareTo(getTotalCashAmount()) == 0) {
						if (getTotalRefund() != null && getTotalRefund().compareTo(BigDecimal.ZERO) != 0) {
							List<ViewStock> stockList = iPersonalRemittanceService.toCheckStockForView(sessionManage.getCountryId(), sessionManage.getUserName(), sessionManage.getBranchId(),
									sessionManage.getCompanyId(), sessionManage.getCurrencyId());
							if (stockList != null && stockList.size() > 0) {
								displayRefundStock(stockList);
								setRenderDenominationDatatabe(false);
								setRenderRefundDenominationDatatabe(true);
								setRenderPaymentModePanel(false);
								setTotalRefundCashEntered(null);
								setRenderRefundDenominationApproveButton(true);
								setRenderRefundDenominationNextButton(false);
							} else {
								setWarningMessage(WarningHandler.showWarningMessage("lbl.refundStockNotFound", sessionManage.getLanguageId()));
								RequestContext.getCurrentInstance().execute("warningDailog.show();");
							}
						}
					} else {
						setWarningMessage("Total Cash Amount and Total Cash Received should be Tally");
						RequestContext.getCurrentInstance().execute("warningDailog.show();");
					}
				} else {
					setWarningMessage("Please Enter the Denomination");
					RequestContext.getCurrentInstance().execute("warningDailog.show();");
				}
			}
		} catch (Exception e) {
			setWarningMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("warningDailog.show();");
		}
	}

	public void backFromRefundScreen() {
		setRenderRefundDenominationDatatabe(false);
		setRenderDenominationDatatabe(true);
		setRenderApproveButton(false);
		setRenderNextButton(true);

	}

	public void displayLocalBanks() {
		List<BigDecimal> duplicateCheck = new ArrayList<BigDecimal>();
		List<BigDecimal> duplicateCheck1 = new ArrayList<BigDecimal>();
		List<ViewBankDetails> lstofBank = new ArrayList<ViewBankDetails>();
		List<ViewBankDetails> lstofBank1 = new ArrayList<ViewBankDetails>();
		bankMasterList.clear();
		chequebankMasterList.clear();

		localbankList = generalService.getLocalBankListFromView(sessionManage.getCountryId());

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

	public void changeDocumentNo() {
		setReceiptDocYearList(null);
		setReceiptDocNoList(null);
		setPaymentModeId(null);
		setDocumentYear(null);
		setDocumentNo(null);
		clear();
		setRenderPaymentModePanel(true);
		setRenderDenominationDatatabe(false);
		setRenderRefundDenominationDatatabe(false);
		setBooRendercollectiondatatable(false);
		setRenderChequePanel(false);
		setDirectFromFirstPage(false);
		setBooRenderColDebitCard(false);
		setBooRenderBankTransfer(false);
		setDisableIfEdit(false);
		// List<ViewReceiptPayment> receiptDocList =
		// miscellaneousReceiptPaymentService.getAllDocumentYearFromView(getDocumentCode(),getDocumentYear());
		// setReceiptDocYearList(receiptDocList);
		// payment mode list
		callPaymentMode();
	}

	public void fectchDocumentNumbers() {
		setReceiptDocNoList(null);
		setDocumentNo(null);
		setPaymentModeId(null);
		clear();
		// callPaymentMode();
		setRenderPaymentModePanel(true);
		setRenderDenominationDatatabe(false);
		setRenderRefundDenominationDatatabe(false);
		setBooRendercollectiondatatable(false);
		setRenderChequePanel(false);
		setDirectFromFirstPage(false);
		setDisableIfEdit(false);
		/*
		 * List<ViewReceiptPayment> receiptDocList =
		 * miscellaneousReceiptPaymentService
		 * .getAllDocumentNumbersFromView(getDocumentCode(),getDocumentYear());
		 * if(receiptDocList.size()>0){ setReceiptDocNoList(receiptDocList); }
		 */
	}

	public void clearAllMapObjects() {
		/*
		 * if(localBankMap!=null){ localBankMap.clear(); }
		 */
		if (paymentModeMap != null) {
			paymentModeMap.clear();
		}
		if (savingMap != null) {
			savingMap.clear();
		}
	}

	public void addNewBenificiary() {
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
		// getLocalBankListforIndicatorFromView();
		localbankList = generalService.getLocalBankListFromView(sessionManage.getCountryId());

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

	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	
	// checking cashier option in Fs_Employee is Y or N
	public boolean checkEmployeeAllowedOrNot(){
		boolean empStatus = Boolean.FALSE;
		List<Employee> checkEmployee = generalService.getEmployeeDetail(sessionManage.getEmployeeId());
		if(checkEmployee != null && checkEmployee.size() != 0){
			Employee empstatus = checkEmployee.get(0);
			if(empstatus.getCashierOpt() != null){
				if(empstatus.getCashierOpt().equalsIgnoreCase(Constants.Y)){
					empStatus = Boolean.TRUE;
				}else{
					empStatus = Boolean.FALSE;
				}
			}else{
				empStatus = Boolean.FALSE;
			}
		}

		return empStatus;
	}

	public void miscellanousreceiptApprovalNavigation() {
		try {
			boolean cashierOptionStatus = checkEmployeeAllowedOrNot();
			if(cashierOptionStatus){
				clear();
				clearAllMapObjects();
				setDocumentCode(null);
				setDocumentNo(null);
				setReceiptDocNoList(null);
				setReceiptDocYearList(null);
				setLstRefundData(null);
				setRenderPaymentModePanel(true);
				setRenderDenominationDatatabe(false);
				setRenderRefundDenominationDatatabe(false);
				setBooRendercollectiondatatable(false);
				setRenderChequePanel(false);
				setDirectFromFirstPage(false);
				setDisableIfEdit(false);
				setBankTransferApprovalNo(null);
				setBooRenderBankTransfer(false);
				bankMasterList.clear();
				setPaymentModeId(null);
				loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionManage.getCountryId(), sessionManage.getUserType(), sessionManage.getUserName(), "miscellaneousPaymentRequestApproval.xhtml");
				FacesContext.getCurrentInstance().getExternalContext().redirect("../miscellaneous/miscellaneousPaymentRequestApproval.xhtml");
			}else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "You are not authorized to collect amount. Please request IT for permission.", ""));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void nextToDenominationPanel() {
		try {
			// Here getTotalCashEntered() >=getNetAmount()
			if (getTotalCashEntered() != null && getNetAmount() != null) {
				if (getTotalCashEntered().compareTo(getNetAmount()) == 1 || getTotalCashEntered().compareTo(getNetAmount()) == 0) {
					Boolean cashCheck = false;
					Boolean chequeCheck = false;
					setTotalDenominationCashEntered(null);
					if (getColdatatablevalues() != null && getColdatatablevalues().size() > 0) {
						BigDecimal cashAmount = BigDecimal.ZERO;
						BigDecimal chequeAmount = BigDecimal.ZERO;
						BigDecimal knetAmount = BigDecimal.ZERO;
						BigDecimal bankTransferAmount = BigDecimal.ZERO;

						List<PersonalRemittanceCollectionDataTable> misDataTableList1 = getColdatatablevalues();
						for (PersonalRemittanceCollectionDataTable dataDataObj : misDataTableList1) {
							if (dataDataObj.getColpaymentmodeCode().equalsIgnoreCase(Constants.CashCode)) {
								cashCheck = true;
								cashAmount = dataDataObj.getColAmountDT();
							} else if (dataDataObj.getColpaymentmodeCode().equalsIgnoreCase(Constants.ChequeCode)) {
								chequeCheck = true;
								chequeAmount = dataDataObj.getColAmountDT();
							} else if (dataDataObj.getColpaymentmodeCode().equalsIgnoreCase(Constants.KNETCode)) {
								knetAmount = dataDataObj.getColAmountDT();
							} else if ((dataDataObj.getColpaymentmodeCode().equalsIgnoreCase(Constants.BankTransferCode))) {
								bankTransferAmount = dataDataObj.getColAmountDT();
							} else if ((dataDataObj.getColpaymentmodeCode().equalsIgnoreCase(Constants.VOCHERCODE))) {
								// not available
							}
						}

						setTotalKnetAmount(knetAmount);
						setTotalCashAmount(cashAmount);
						setTotalChequeAmount(chequeAmount);

						if (cashCheck) {

							if (getDocumentCode().compareTo(new BigDecimal(Constants.DOCUMENT_CODE_FOR_PAYMENT)) == 0) {
								if (getTotalRefund() != null) {
									if (getTotalRefund().compareTo(BigDecimal.ZERO) == 1) {
										setRenderDenominationApproveButton(false);
										setRenderDenominationNextButton(true);
									} else {
										setRenderDenominationApproveButton(true);
										setRenderDenominationNextButton(false);
									}
								}
								nextToRefundDenomination();
								setDirectFromFirstPage(true);
							} else if (getDocumentCode().compareTo(new BigDecimal(Constants.DOCUMENT_CODE_FOR_RECEIVE)) == 0) {
								if (getTotalRefund() != null) {
									if (getTotalRefund().compareTo(BigDecimal.ZERO) == 1) {
										setRenderDenominationApproveButton(false);
										setRenderDenominationNextButton(true);
									} else {
										setRenderDenominationApproveButton(true);
										setRenderDenominationNextButton(false);
									}
								}
								setRenderPaymentModePanel(false);
								setRenderDenominationDatatabe(true);
								setRenderRefundDenominationDatatabe(false);
								setDirectFromFirstPage(true);
								displayCurrencyDenomination();
							}

						} else if (chequeCheck) {
							if (getTotalRefund() != null) {
								// Here getTotalRefund()>0
								if (getTotalRefund().compareTo(BigDecimal.ZERO) == 1) {
									List<ViewStock> stockList = iPersonalRemittanceService.toCheckStockForView(sessionManage.getCountryId(), sessionManage.getUserName(), sessionManage.getBranchId(),
											sessionManage.getCompanyId(), sessionManage.getCurrencyId());
									if (stockList != null && stockList.size() > 0) {
										displayRefundStock(stockList);
										setRenderPaymentModePanel(false);
										setRenderDenominationDatatabe(false);
										setRenderRefundDenominationDatatabe(true);
										setDirectFromFirstPage(true);
										setRenderRefundDenominationApproveButton(true);
										setRenderRefundDenominationNextButton(false);
									} else {
										setWarningMessage(WarningHandler.showWarningMessage("lbl.refundStockNotFound", sessionManage.getLanguageId()));
										RequestContext.getCurrentInstance().execute("warningDailog.show();");
									}
								}
							}
						}
					}
				} else {
					setWarningMessage(WarningHandler.showWarningMessage("lbl.totalAmountandNetAmount", sessionManage.getLanguageId()));
					RequestContext.getCurrentInstance().execute("warningDailog.show();");
				}
			}

		} catch (Exception e) {
			if (e.getMessage() != null) {
				setWarningMessage(e.getMessage());
				RequestContext.getCurrentInstance().execute("warningDailog.show();");
			} else {
				setWarningMessage("Exception :" + e.toString());
				RequestContext.getCurrentInstance().execute("warningDailog.show();");
			}
		}

	}

	public void callPaymentMode() {
		paymentModeMap = new HashMap<BigDecimal, String>();
		List<PaymentModeDesc> paymentModeTempList = new ArrayList<PaymentModeDesc>();
		List<PaymentModeDesc> paymentModeList = ipaymentService.fetchPaymodeDesc(sessionManage.getLanguageId(), Constants.Yes);
		for (PaymentModeDesc paymentObj : paymentModeList) {
			if (getDocumentCode().equals(new BigDecimal(Constants.DOCUMENT_CODE_FOR_PAYMENT))) {
				if (paymentObj.getPaymentMode().getPaymentCode().equalsIgnoreCase(Constants.CashCode) || paymentObj.getPaymentMode().getPaymentCode().equalsIgnoreCase(Constants.BankTransferCode)) {
					// setPaymentModeId(
					// paymentObj.getPaymentMode().getPaymentModeId());
					paymentModeMap.put(paymentObj.getPaymentMode().getPaymentModeId(), paymentObj.getLocalPaymentName());
					paymentModeTempList.add(paymentObj);
				}
			} else if (!paymentObj.getPaymentMode().getPaymentCode().equalsIgnoreCase(Constants.VOCHERCODE)) {
				paymentModeMap.put(paymentObj.getPaymentMode().getPaymentModeId(), paymentObj.getLocalPaymentName());
				paymentModeTempList.add(paymentObj);
			}
		}
		if (paymentModeTempList.size() > 0) {
			setPaymentModeList(paymentModeTempList);
		}
	}

	public void exitFromMiscellanouse() {
		clear();
		bankMasterList.clear();
		setRenderChequePanel(false);
		try {
			/*
			 * if (sessionManage.getRoleId().equalsIgnoreCase("1")) {
			 * FacesContext.getCurrentInstance().getExternalContext().redirect(
			 * "../registration/employeehome.xhtml"); } else {
			 * FacesContext.getCurrentInstance
			 * ().getExternalContext().redirect("../registration/branchhome.xhtml"
			 * ); }
			 */
			String roleNameDesc = iPersonalRemittanceService.toFetchRoleName(new BigDecimal(sessionManage.getRoleId()));
			String roleName = roleNameDesc.trim();
			if (roleName.equalsIgnoreCase(Constants.USER_ROLE_BRANCHSTAFF)) {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
			} else if (roleName.equalsIgnoreCase(Constants.USER_ROLE_BRANCH_MANAGER) || roleName.equalsIgnoreCase(Constants.USER_ROLE_MANAGER)) {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
			} else if (roleName.equalsIgnoreCase(Constants.USER_ROLE_CORPORATE)) {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/corporatehome.xhtml");
			} else {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public void editDataTableRecord(PersonalRemittanceCollectionDataTable personalRemitObj) {

		setDisableIfEdit(true);
		if (personalRemitObj.getColpaymentmodeCode().equalsIgnoreCase(Constants.ChequeCode)) {
			clearChequeValues();
			setPaymentCode(null);
			setColchequebankId(personalRemitObj.getColBankIdDT());
			setColchequebankCode(personalRemitObj.getColBankCodeDT());
			setColChequeRef(personalRemitObj.getColchequeRefNo());
			setColChequeDate(personalRemitObj.getColchequeDate());
			setColChequeApprovalNo(personalRemitObj.getColApprovalNo());
			setColCash(personalRemitObj.getColAmountDT());
			setPaymentMode(personalRemitObj.getColpaymentmodetypeDT());
			setPaymentCode(personalRemitObj.getColpaymentmodeCode());
			setPaymentModeId(personalRemitObj.getColpaymentmodeIDtypeDT());
			List<PersonalRemittanceCollectionDataTable> colDatatableList = getColdatatablevalues();
			colDatatableList.remove(personalRemitObj);

			if (colDatatableList.size() > 0) {
				setColdatatablevalues(colDatatableList);
				setBooRendercollectiondatatable(true);
				setRenderChequePanel(true);
			} else {
				coldatatablevalues.clear();
				setBooRendercollectiondatatable(false);
				setRenderChequePanel(true);
			}
			totalRefundAmountCalForDataTable(personalRemitObj);
		} else if (personalRemitObj.getColpaymentmodeCode().equalsIgnoreCase(Constants.CashCode)) {
			setRenderChequePanel(false);
			setBooRenderBankTransfer(false);
			setBooRenderColDebitCard(false);
			clearCashValues();
			setPaymentCode(null);
			setColCash(personalRemitObj.getColAmountDT());
			setPaymentCode(personalRemitObj.getColpaymentmodeCode());
			setPaymentModeId(personalRemitObj.getColpaymentmodeIDtypeDT());
			setPaymentMode(personalRemitObj.getColpaymentmodetypeDT());
			List<PersonalRemittanceCollectionDataTable> colDatatableList = getColdatatablevalues();
			colDatatableList.remove(personalRemitObj);

			if (colDatatableList.size() > 0) {
				setColdatatablevalues(colDatatableList);
				setBooRendercollectiondatatable(true);
			} else {
				setColdatatablevalues(colDatatableList);
				setBooRendercollectiondatatable(true);
			}
			totalRefundAmountCalForDataTable(personalRemitObj);
		} else if (personalRemitObj.getColpaymentmodeCode().equalsIgnoreCase(Constants.KNETCode)) {
			setBooRenderColDebitCard(true);
			setRenderChequePanel(false);
			setBooRenderBankTransfer(false);
			List<PersonalRemittanceCollectionDataTable> colDatatableList = getColdatatablevalues();
			setColpaymentmodeId(personalRemitObj.getColpaymentmodeIDtypeDT());
			setColpaymentmodeName(personalRemitObj.getColpaymentmodetypeDT());
			setColpaymentmodeCode(personalRemitObj.getColpaymentmodeCode());
			setColBankCode(personalRemitObj.getColBankCodeDT());
			setPopulatedDebitCardNumber(personalRemitObj.getColCardNumberDT());
			setPaymentMode(personalRemitObj.getColpaymentmodetypeDT());
			setPaymentModeId(personalRemitObj.getColpaymentmodeIDtypeDT());
			setPaymentCode(personalRemitObj.getColpaymentmodeCode());

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

			colDatatableList.remove(personalRemitObj);
			totalRefundAmountCalForDataTable(personalRemitObj);

		} else if (personalRemitObj.getColpaymentmodeCode().equalsIgnoreCase(Constants.VOCHERCODE)) {
			clearChequeValues();
			setPaymentCode(null);
			setBooRenderBankTransfer(false);
			setBooRenderColDebitCard(false);
			setRenderChequePanel(false);
			List<PersonalRemittanceCollectionDataTable> colDatatableList = getColdatatablevalues();
			setPaymentModeId(personalRemitObj.getColpaymentmodeIDtypeDT());
			setColCash(personalRemitObj.getColAmountDT());
			setPaymentCode(personalRemitObj.getColpaymentmodeCode());
			colDatatableList.remove(personalRemitObj);
			totalRefundAmountCalForDataTable(personalRemitObj);
			setPaymentMode(personalRemitObj.getColpaymentmodetypeDT());
		} else if (personalRemitObj.getColpaymentmodeCode().equalsIgnoreCase(Constants.BankTransferCode)) {
			setBooRenderBankTransfer(true);
			setBooRenderColDebitCard(false);
			setRenderChequePanel(false);
			List<PersonalRemittanceCollectionDataTable> colDatatableList = getColdatatablevalues();
			setPaymentModeId(personalRemitObj.getColpaymentmodeIDtypeDT());
			setColCash(personalRemitObj.getColAmountDT());
			setBankTransferApprovalNo(personalRemitObj.getColApprovalNo());
			setPaymentCode(personalRemitObj.getColpaymentmodeCode());
			colDatatableList.remove(personalRemitObj);
			totalRefundAmountCalForDataTable(personalRemitObj);
			setPaymentMode(personalRemitObj.getColpaymentmodetypeDT());
			setColchequebankId(personalRemitObj.getColBankIdDT());
			setColchequebankCode(personalRemitObj.getColBankCodeDT());
		}

	}

	public void refundValidationForPayment() {
		if (getDocumentCode() != null && getNetAmount() != null && getNetAmount() != BigDecimal.ZERO && getColCash() != null) {
			if (getDocumentCode().compareTo(new BigDecimal(Constants.DOCUMENT_CODE_FOR_PAYMENT)) == 0) {
				if (!(getNetAmount().compareTo(getColCash()) == 0)) {
					setColCash(null);
					setWarningMessage("Total Net Amount Must be equals to Total Entered Amount ");
					RequestContext.getCurrentInstance().execute("warningDailog.show();");
				}
			} else if (getDocumentCode().compareTo(new BigDecimal(Constants.DOCUMENT_CODE_FOR_RECEIVE)) == 0) {
				BigDecimal paymentModeCashId = ipaymentService.fetchPaymodeMasterId(Constants.CASHNAME,
						new BigDecimal(sessionManage.isExists("languageId") ? sessionManage.getSessionValue("languageId") : "1"));
				if (getPaymentModeId().compareTo(paymentModeCashId) != 0) {
					BigDecimal totalAmount = BigDecimal.ZERO;
					setErrcolCashExistsLimit(null);
					if (getPaymentCode() != null && getPaymentCode().equalsIgnoreCase(Constants.KNETCode)) {
						BigDecimal colKnetAmount = BigDecimal.ZERO;
						for (PersonalRemittanceCollectionDataTable collectionlst : coldatatablevalues) {
							if (collectionlst.getColpaymentmodeCode().equalsIgnoreCase(Constants.KNETCode)) {
								colKnetAmount = colKnetAmount.add(collectionlst.getColAmountDT());
							}
						}
						colKnetAmount = colKnetAmount.add(getColCash());
						BigDecimal percentage = new BigDecimal(5).divide(new BigDecimal(100));
						BigDecimal percentageAmount = percentage.multiply(getNetAmount());
						totalAmount = GetRound.roundBigDecimal(percentageAmount.add(getNetAmount()),
								foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId())));

						if (colKnetAmount.compareTo(totalAmount) <= 0) {
							setColamountKWD(getColCash());
						} else {
							setColCash(null);
							setErrcolCashExistsLimit(totalAmount);
							RequestContext.getCurrentInstance().execute("amountgreater.show();");
							checkKnetAmount = true;
						}
					} else if (!(getNetAmount().compareTo(getColCash()) >= 0)) {
						setColCash(null);
						setWarningMessage("Total Net Amount Must be equals to Total Entered Amount");
						RequestContext.getCurrentInstance().execute("warningDailog.show();");
					}
				}
			} else {
				// not both
			}
		}

	}

	public void totalRefundAmountCalForDataTable(PersonalRemittanceCollectionDataTable personalRemitObj) {

		BigDecimal totalEnteredAmount = BigDecimal.ZERO;
		BigDecimal totalRefundAmount = BigDecimal.ZERO;
		// Here getTotalCashEntered()>0
		if (getTotalCashEntered() != null && personalRemitObj.getColAmountDT() != null && getTotalCashEntered().compareTo(BigDecimal.ZERO) == 1) {
			totalEnteredAmount = getTotalCashEntered().subtract(personalRemitObj.getColAmountDT());
			setTotalCashEntered(totalEnteredAmount);
		}
		// Here getTotalRefund()>0
		if (getTotalRefund() != null && personalRemitObj.getColAmountDT() != null && getTotalRefund().compareTo(BigDecimal.ZERO) == 1) {
			totalRefundAmount = getTotalRefund().subtract(personalRemitObj.getColAmountDT());
			// Here getTotalRefund()>=personalRemitObj.getColAmountDT()
			if (getTotalRefund().compareTo(personalRemitObj.getColAmountDT()) == 0 || getTotalRefund().compareTo(personalRemitObj.getColAmountDT()) == 1) {
				setTotalRefund(totalRefundAmount);
			} else {
				setTotalRefund(BigDecimal.ZERO);
			}

		}
	}

	// to remove details from data table after adding
	public void deletePaymentModeRecord(PersonalRemittanceCollectionDataTable collectionDt) {
		if (collectionDt.getColpaymentmodeCode().equalsIgnoreCase(Constants.CashCode)) {
			setRenderNextButton(false);
			setRenderApproveButton(true);
		}
		List<PersonalRemittanceCollectionDataTable> colDatatableList = getColdatatablevalues();
		colDatatableList.remove(collectionDt);

		if (colDatatableList.size() > 0) {
			setColdatatablevalues(colDatatableList);
		} else {
			coldatatablevalues.clear();
		}
		totalRefundAmountCalForDataTable(collectionDt);

		if (getColdatatablevalues() != null && getColdatatablevalues().size() > 0) {
			setBooRendercollectiondatatable(true);
		} else {
			setBooRendercollectiondatatable(false);
		}

		// if knet impleted need to add one more condition here

		if (colDatatableList != null && colDatatableList.size() == 1) {
			if (colDatatableList.get(0).getColpaymentmodeCode().equalsIgnoreCase(Constants.ChequeCode)) {
				if (getTotalRefund() != null) {
					// Here getTotalRefund()>0
					if (getTotalRefund().compareTo(BigDecimal.ZERO) == 1) {
						setRenderNextButton(true);
						setRenderApproveButton(false);
					} else {
						setRenderNextButton(false);
						setRenderApproveButton(true);
					}
				}
			}
		}

	}

	public Payment savePayment(Date acc_Month) {

		Payment payment = new Payment();

		CountryMaster countryMaster = new CountryMaster();
		countryMaster.setCountryId(sessionManage.getCountryId());
		payment.setCountryId(countryMaster);

		payment.setCompanyId(getCompanyId());
		payment.setDocCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_PAYMENT));
		payment.setDocNumber(getDocumentNo());
		payment.setCountryBranchId(new BigDecimal(sessionManage.getBranchId()));
		payment.setReceiptType("99");
		// payment.setPaymentmode();
		// payment.setChequeReference(chequeReference);
		// payment.setChequekdate(chequekdate);
		// payment.setApprovalNo(approvalNo);
		payment.setCurrencyId(getCurrencyId());
		// lstofPayment
		// payment.setPaidAmount(getTotalCashEntered());
		// payment.setIsCashDeposit(isCashDeposit);
		// payment.setCdepDoccod(cdepDoccod);
		// payment.setCdepDocfyr(cdepDocfyr);
		// payment.setCdepDocno(cdepDocno);
		// payment.setCdepDate(cdepDate);
		payment.setIsActive(Constants.Yes);
		payment.setAcyymm(acc_Month);
		payment.setCreatedBy(sessionManage.getUserName());
		payment.setCreatedDate(new Date());
		payment.setModifiedBy(null);
		payment.setModifiedDate(null);
		payment.setDocYear(getDocumentYear());
		payment.setCustomerId(getCustomerId());

		BigDecimal documentId = BigDecimal.ZERO;
		BigDecimal companyCode = BigDecimal.ZERO;
		BigDecimal locCode = BigDecimal.ZERO;

		List<Document> listDocument = generalService.getDocument(Constants.DOCUMENT_CODE_FOR_STOPPAYMENT, sessionManage.getLanguageId());
		if (listDocument != null && listDocument.size() > 0) {
			documentId = listDocument.get(0).getDocumentID();
		}

		List<CompanyMasterDesc> data = generalService.getCompanyList(sessionManage.getCompanyId(), new BigDecimal(sessionManage.isExists("languageId") ? sessionManage.getSessionValue("languageId")
				: "1"));
		if (data != null && data.size() > 0) {
			companyCode = data.get(0).getFsCompanyMaster().getCompanyCode();
		}

		payment.setCompanyCode(companyCode);
		payment.setDocumentId(documentId);

		List<CountryBranch> listCode = generalService.getCountryBranchLocCode(new BigDecimal(sessionManage.getBranchId()));
		if (listCode != null && listCode.size() > 0) {
			locCode = listCode.get(0).getBranchId();
		}
		payment.setLocCod(locCode);
		payment.setCountryBranchId(new BigDecimal(sessionManage.getBranchId()));
		payment.setPaymentDate(new Date());
		for (PersonalRemittanceCollectionDataTable lstofPayment : getColdatatablevalues()) {
			payment.setPaidAmount(lstofPayment.getColAmountDT());
			if (lstofPayment.getColpaymentmodeCode().equalsIgnoreCase(Constants.CashCode)) {
				payment.setPaymentmode(Constants.CashCode);
			} else if (lstofPayment.getColpaymentmodeCode().equalsIgnoreCase(Constants.BankTransferCode)) {
				payment.setPaymentmode(Constants.BankTransferCode);
				if (lstofPayment.getColBankCodeDT() != null) {
					payment.setChequeBankCode(lstofPayment.getColBankCodeDT());
				}
				payment.setApprovalNo(lstofPayment.getColApprovalNo());
			}

			savingMap.put("PAYMENT", payment);
		}
		return payment;
	}

	public void savePaymentDetail(Payment payment, Date acc_Month) {

		List<PaymentDetail> paymentDetaillist = new ArrayList<PaymentDetail>();
		int i = 0;
		for (PersonalRemittanceCollectionDataTable lstofPayment : getColdatatablevalues()) {

			PaymentDetail paymentDetail = new PaymentDetail();

			paymentDetail.setPaymentId(payment);

			CountryMaster countryMaster = new CountryMaster();
			countryMaster.setCountryId(sessionManage.getCountryId());
			paymentDetail.setCountryId(countryMaster);

			Customer customer = new Customer();
			customer.setCustomerId(payment.getCustomerId());
			paymentDetail.setFsCustomer(customer);

			CompanyMaster companymaster = new CompanyMaster();
			companymaster.setCompanyId(payment.getCompanyId());
			paymentDetail.setCompanyId(companymaster);

			paymentDetail.setDocumentCode(payment.getDocCode());
			paymentDetail.setDocNumber(payment.getDocNumber());
			paymentDetail.setDocumentLineNo(new BigDecimal(++i));

			CountryBranch bankbranch = new CountryBranch();
			bankbranch.setCountryBranchId(new BigDecimal(sessionManage.getBranchId()));
			paymentDetail.setBranchId(bankbranch);

			CurrencyMaster forcurrencymaster = new CurrencyMaster();
			forcurrencymaster.setCurrencyId(payment.getCurrencyId());
			paymentDetail.setCurrencyId(forcurrencymaster);

			if (lstofPayment.getColBankCodeDT() != null) {
				paymentDetail.setChequeBankRef(new BigDecimal(lstofPayment.getColBankCodeDT()));
			}
			paymentDetail.setPaymentAmt(lstofPayment.getColAmountDT());

			if (lstofPayment.getColpaymentmodetypeDT().equalsIgnoreCase(Constants.KNETCode)) {
				paymentDetail.setDebitCard(lstofPayment.getColCardNumberDT().toPlainString());
				paymentDetail.setDbCardName(lstofPayment.getColNameofCardDT());
				paymentDetail.setApprovalNo(lstofPayment.getColApprovalNo());
				if (lstofPayment.getColAuthorizedByDT() != null) {
					paymentDetail.setAuthby(lstofPayment.getColAuthorizedByDT());
					paymentDetail.setAuthdat(new Date());
				}
			} else if (lstofPayment.getColpaymentmodetypeDT().equalsIgnoreCase(Constants.ChequeCode)) {
				paymentDetail.setChequeReference(lstofPayment.getColchequeRefNo());
				paymentDetail.setChequekdate(lstofPayment.getColchequeDate());
				paymentDetail.setApprovalNo(lstofPayment.getColApprovalNo());
			}

			paymentDetail.setIsActive(Constants.Yes);
			try {
				paymentDetail.setAcyymm(new SimpleDateFormat("dd/MM/yyyy").parse(getCurrentDateWithFormat()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			paymentDetail.setCreatedBy(sessionManage.getUserName());
			paymentDetail.setCreatedDate(new Date());
			paymentDetail.setModifiedBy(null);
			paymentDetail.setModifiedDate(null);
			paymentDetail.setDocumentFinaceyear(getDocumentYear());
			paymentDetail.setPaymentDate(new Date());

			PaymentMode paymentMode = new PaymentMode();
			paymentMode.setPaymentModeId(lstofPayment.getColpaymentmodeIDtypeDT());
			paymentDetail.setPaymentModeId(paymentMode);

			List<PaymentMode> paymentModeList = miscellaneousReceiptPaymentService.fetchPaymentmode(lstofPayment.getColpaymentmodeIDtypeDT());

			if (paymentModeList.size() > 0) {
				paymentDetail.setPaymentmode(paymentModeList.get(0).getPaymentCode());
			}

			paymentDetaillist.add(paymentDetail);

		}
		savingMap.put("PAYMENTDETAILLIST", paymentDetaillist);
	}

	public void saveDenominationPaid(Date acc_Month, BigDecimal documentCode, String programName) throws Exception {
		int i = 0;
		List<ForeignCurrencyAdjust> foreignCurrencyAdjustList = new ArrayList<ForeignCurrencyAdjust>();
		for (ForeignLocalCurrencyDataTable foreignLocalCurrencyDataTable : getLstData()) {

			if (!foreignLocalCurrencyDataTable.getQty().equals("") && foreignLocalCurrencyDataTable.getQty() != null && !foreignLocalCurrencyDataTable.getQty().equals("0")) {
				ForeignCurrencyAdjust foreignCurrencyAdjust = new ForeignCurrencyAdjust();
				// Company save
				CompanyMaster companyMaster = new CompanyMaster();
				companyMaster.setCompanyId(new BigDecimal(sessionManage.getSessionValue("companyId")));
				foreignCurrencyAdjust.setFsCompanyMaster(companyMaster);
				// Country Save
				CountryMaster countryMaster = new CountryMaster();
				countryMaster.setCountryId(new BigDecimal(sessionManage.getSessionValue("countryId")));
				foreignCurrencyAdjust.setFsCountryMaster(countryMaster);
				// customer Save
				Customer customer = new Customer();
				customer.setCustomerId(getCustomerId());
				foreignCurrencyAdjust.setFsCustomer(customer);
				foreignCurrencyAdjust.setDocumentDate(new Date());
				// currency Id
				CurrencyMaster currencyMaster = new CurrencyMaster();
				currencyMaster.setCurrencyId(new BigDecimal(sessionManage.getCurrencyId()));
				foreignCurrencyAdjust.setFsCurrencyMaster(currencyMaster);
				if (foreignLocalCurrencyDataTable.getQty() != null) {
					foreignCurrencyAdjust.setNotesQuantity(new BigDecimal(foreignLocalCurrencyDataTable.getQty()));
				}
				if (foreignLocalCurrencyDataTable.getPrice() != null) {
					foreignCurrencyAdjust.setAdjustmentAmount(new BigDecimal(foreignLocalCurrencyDataTable.getPrice()));
				}

				CurrencyWiseDenomination denominationMaster = new CurrencyWiseDenomination();

				denominationMaster.setDenominationId(foreignLocalCurrencyDataTable.getDenominationId());

				foreignCurrencyAdjust.setFsDenominationId(denominationMaster);
				// foreignCurrencyAdjust.setExchangeRate();
				foreignCurrencyAdjust.setDenaminationAmount(foreignLocalCurrencyDataTable.getDenominationAmount());
				foreignCurrencyAdjust.setDocumentNo(getDocumentNo());
				foreignCurrencyAdjust.setDocumentFinanceYear(getFinaceYear());
				foreignCurrencyAdjust.setOracleUser(sessionManage.getUserName());

				try {
					foreignCurrencyAdjust.setDocumentCode(documentCode);
					foreignCurrencyAdjust.setDocumentLineNumber(new BigDecimal(++i));
					foreignCurrencyAdjust.setAccountmmyyyy(acc_Month);
					CountryBranch countryBranch = new CountryBranch();
					countryBranch.setCountryBranchId(new BigDecimal(sessionManage.getBranchId()));
					foreignCurrencyAdjust.setCountryBranch(countryBranch);

					foreignCurrencyAdjust.setProgNumber(programName);
					foreignCurrencyAdjust.setDocumentStatus(Constants.P);

				} catch (Exception e) {
					e.printStackTrace();
				}
				foreignCurrencyAdjust.setCreatedDate(new Date());
				foreignCurrencyAdjust.setCreatedBy(sessionManage.getUserName());
				Collect collect = (Collect) savingMap.get("COLLECT");
				if (collect != null) {
					foreignCurrencyAdjust.setDocumentNo(collect.getDocumentNo());
					foreignCurrencyAdjust.setCollect(collect);
				} else {
					foreignCurrencyAdjust.setDocumentNo(getDocumentNo());
				}
				List<Document> document = null;

				if (getDocumentCode().compareTo(new BigDecimal(Constants.DOCUMENT_CODE_FOR_PAYMENT)) == 0) {
					document = generalService.getDocument(new BigDecimal(Constants.DOCUMENT_CODE_FOR_PAYMENT), sessionManage.getLanguageId());
					if (document != null) {
						foreignCurrencyAdjust.setDocumentId(document.get(0).getDocumentID());
					}
				} else if (getDocumentCode().compareTo(new BigDecimal(Constants.DOCUMENT_CODE_FOR_RECEIVE)) == 0) {
					document = generalService.getDocument(new BigDecimal(Constants.DOCUMENT_CODE_FOR_RECEIVE), sessionManage.getLanguageId());
					if (document != null) {
						foreignCurrencyAdjust.setDocumentId(document.get(0).getDocumentID());
					}
				}

				foreignCurrencyAdjust.setTransactionType(Constants.C);

				BigDecimal companyCode = null;
				companyCode = miscellaneousReceiptPaymentService.toFetchCompanyCode(sessionManage.getCompanyId());
				foreignCurrencyAdjust.setCompanyCode(companyCode);
				foreignCurrencyAdjustList.add(foreignCurrencyAdjust);
			} else {
				log.info("Number of notes is 0");
			}
		}
		savingMap.put("FOREIGNCURRENCYADJUSTDENOMINATION", foreignCurrencyAdjustList);
	}

	public void saveDenominationRefund(Date acc_Month, BigDecimal documentCode, String programName) throws Exception {

		List<ForeignCurrencyAdjust> foreignCurrencyAdjustList = new ArrayList<ForeignCurrencyAdjust>();
		int j = 0;
		for (ForeignLocalCurrencyDataTable foreignLocalCurrencyDataTable : getLstRefundData()) {
			if (!foreignLocalCurrencyDataTable.getQty().equals("") && foreignLocalCurrencyDataTable.getQty() != null && !foreignLocalCurrencyDataTable.getQty().equals("0")) {
				ForeignCurrencyAdjust foreignCurrencyAdjust = new ForeignCurrencyAdjust();
				// Company save
				CompanyMaster companyMaster = new CompanyMaster();
				companyMaster.setCompanyId(new BigDecimal(sessionManage.getSessionValue("companyId")));
				foreignCurrencyAdjust.setFsCompanyMaster(companyMaster);
				// Country Save
				CountryMaster countryMaster = new CountryMaster();
				countryMaster.setCountryId(new BigDecimal(sessionManage.getSessionValue("countryId")));
				foreignCurrencyAdjust.setFsCountryMaster(countryMaster);
				// customer Save
				Customer customer = new Customer();
				customer.setCustomerId(getCustomerId());
				foreignCurrencyAdjust.setFsCustomer(customer);
				foreignCurrencyAdjust.setDocumentDate(new Date());
				// currency Id
				CurrencyMaster currencyMaster = new CurrencyMaster();
				currencyMaster.setCurrencyId(new BigDecimal(sessionManage.getCurrencyId()));
				foreignCurrencyAdjust.setFsCurrencyMaster(currencyMaster);
				if (foreignLocalCurrencyDataTable.getQty() != null) {
					foreignCurrencyAdjust.setNotesQuantity(new BigDecimal(foreignLocalCurrencyDataTable.getQty()));
				}
				if (foreignLocalCurrencyDataTable.getPrice() != null) {
					foreignCurrencyAdjust.setAdjustmentAmount(new BigDecimal(foreignLocalCurrencyDataTable.getPrice()));
				}

				CurrencyWiseDenomination denominationMaster = new CurrencyWiseDenomination();
				denominationMaster.setDenominationId(foreignLocalCurrencyDataTable.getDenominationId());
				foreignCurrencyAdjust.setFsDenominationId(denominationMaster);
				// foreignCurrencyAdjust.setExchangeRate((BigDecimal)
				// returnResult.get("ExchangeRate"));
				foreignCurrencyAdjust.setDenaminationAmount(foreignLocalCurrencyDataTable.getDenominationAmount());
				foreignCurrencyAdjust.setDocumentFinanceYear(getFinaceYear());
				foreignCurrencyAdjust.setOracleUser(sessionManage.getUserName());

				try {
					foreignCurrencyAdjust.setDocumentCode(documentCode);
					foreignCurrencyAdjust.setDocumentNo(getDocumentNo());
					foreignCurrencyAdjust.setDocumentLineNumber(new BigDecimal(++j));
					foreignCurrencyAdjust.setAccountmmyyyy(acc_Month);
					CountryBranch countryBranch = new CountryBranch();
					countryBranch.setCountryBranchId(new BigDecimal(sessionManage.getBranchId()));
					foreignCurrencyAdjust.setCountryBranch(countryBranch);
					foreignCurrencyAdjust.setProgNumber(programName);
					foreignCurrencyAdjust.setDocumentStatus(Constants.P);

				} catch (Exception e) {
					e.printStackTrace();
				}

				foreignCurrencyAdjust.setCreatedDate(new Date());
				foreignCurrencyAdjust.setCreatedBy(sessionManage.getUserName());

				List<Document> document = null;

				if (getDocumentCode().compareTo(new BigDecimal(Constants.DOCUMENT_CODE_FOR_PAYMENT)) == 0) {
					document = generalService.getDocument(new BigDecimal(Constants.DOCUMENT_CODE_FOR_PAYMENT), sessionManage.getLanguageId());
					if (document != null) {
						foreignCurrencyAdjust.setDocumentId(document.get(0).getDocumentID());
					}
				} else if (getDocumentCode().compareTo(new BigDecimal(Constants.DOCUMENT_CODE_FOR_RECEIVE)) == 0) {
					document = generalService.getDocument(new BigDecimal(Constants.DOCUMENT_CODE_FOR_RECEIVE), sessionManage.getLanguageId());
					if (document != null) {
						foreignCurrencyAdjust.setDocumentId(document.get(0).getDocumentID());
					}
				}

				foreignCurrencyAdjust.setTransactionType(Constants.F);

				BigDecimal companyCode = null;
				companyCode = miscellaneousReceiptPaymentService.toFetchCompanyCode(sessionManage.getCompanyId());
				foreignCurrencyAdjust.setCompanyCode(companyCode);
				Collect collect = (Collect) savingMap.get("COLLECT");
				if (collect != null) {
					foreignCurrencyAdjust.setDocumentNo(collect.getDocumentNo());
					foreignCurrencyAdjust.setCollect(collect);
				} else {
					foreignCurrencyAdjust.setDocumentNo(getDocumentNo());
				}
				foreignCurrencyAdjustList.add(foreignCurrencyAdjust);
			} else {
				log.info("Number of notes is 0");
			}
		}
		savingMap.put("FOREIGNCURRENCYADJUSTREFUND", foreignCurrencyAdjustList);
	}

	// to get the accoMMYY value
	@Deprecated
	public static String getCurrentDateWithFormat() {
		Map<Integer, String> data = new HashMap<Integer, String>();
		for (int i = 0; i < 12; i++) {
			if (i < 9) {
				data.put(i, "0" + String.valueOf(i + 1));
			} else {
				data.put(i, String.valueOf(i + 1));
			}
		}
		String year = String.valueOf(new Date().getYear()).substring(1, 3);
		System.out.println(Calendar.getInstance().get(Calendar.MONTH));
		return "01/" + data.get(Calendar.getInstance().get(Calendar.MONTH)) + "/" + year;
	}

	public BigDecimal getFinaceYear() {
		BigDecimal finaceYear = null;
		try {
			List<UserFinancialYear> financialYearList = foreignCurrencyPurchaseService.getUserFinancialYear(new Date());
			log.info("financialYearList :" + financialYearList.size());
			if (financialYearList != null) {
				finaceYear = financialYearList.get(0).getFinancialYear();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return finaceYear;
	}

	public Collect saveCollect() throws Exception {
		Collect collect = new Collect();
		// company Id
		CompanyMaster companymaster = new CompanyMaster();
		companymaster.setCompanyId(getCompanyId());
		collect.setFsCompanyMaster(companymaster);

		collect.setApplicationCountryId(sessionManage.getCountryId());
		// customer Id
		Customer customer = new Customer();
		customer.setCustomerId(getCustomerId());
		collect.setFsCustomer(customer);
		// document Id
		collect.setDocumentNo(getDocumentNo());
		collect.setDocumentCode(new BigDecimal(2));
		// Document Financial Year
		collect.setDocumentFinanceYear(getDocumentYear());
		collect.setCollectDate(new Date());
		// Foriegn Currency id
		CurrencyMaster forcurrencymaster = new CurrencyMaster();
		forcurrencymaster.setCurrencyId(new BigDecimal(sessionManage.getCurrencyId()));
		collect.setExCurrencyMaster(forcurrencymaster);

		collect.setPaidAmount(getTotalCashEntered());
		collect.setRefoundAmount(getTotalRefund());
		collect.setNetAmount(getNetAmount());
		collect.setIsActive(Constants.Yes);
		try {
			collect.setAccountMMYYYY(new SimpleDateFormat("dd/MM/yyyy").parse(getCurrentDateWithFormat()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		CountryBranch bankbranch = new CountryBranch();
		bankbranch.setCountryBranchId(new BigDecimal(sessionManage.getBranchId()));
		collect.setExBankBranch(bankbranch);
		collect.setReceiptType("98");
		collect.setGeneralLegerDate(new Date());
		collect.setCreatedBy(sessionManage.getUserName());
		collect.setCreatedDate(new Date());

		BigDecimal locCode = BigDecimal.ZERO;
		List<CountryBranch> listCode = generalService.getCountryBranchLocCode(new BigDecimal(sessionManage.getBranchId()));
		if (listCode != null && listCode.size() > 0) {
			locCode = listCode.get(0).getBranchId();
		}
		collect.setLocCode(locCode);

		List<CompanyMaster> companyMaster = null;
		companyMaster = miscellaneousReceiptPaymentService.getCompanyCode(sessionManage.getCompanyId());
		if (companyMaster.size() > 0) {
			collect.setCompanyCode(companyMaster.get(0).getCompanyCode());

		}

		List<Document> document = null;

		if (getDocumentCode().equals(Constants.DOCUMENT_CODE_FOR_RECEIVE)) {

			document = miscellaneousReceiptPaymentService.getDocumentId(new BigDecimal(Constants.DOCUMENT_CODE_FOR_RECEIVE));

		} else {
			document = miscellaneousReceiptPaymentService.getDocumentId(new BigDecimal(Constants.DOCUMENT_CODE_FOR_PAYMENT));

		}
		collect.setDocumentId(document.get(0).getDocumentID());

		savingMap.put("COLLECT", collect);
		return collect;
	}

	public void saveCollectDetail(Collect collect) throws Exception {
		int i = 1;
		List<CollectDetail> collectDetaillist = new ArrayList<CollectDetail>();
		for (PersonalRemittanceCollectionDataTable lstofPayment : getColdatatablevalues()) {
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
			appcountrymaster.setCountryId(sessionManage.getCountryId());
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
			bankbranch.setCountryBranchId(new BigDecimal(sessionManage.getBranchId()));
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
				// collectDetails.setChequeBankRef(lstofPayment.getColBankCodeDT()
				// );
				if (lstofPayment.getColAuthorizedByDT() != null) {
					collectDetails.setAuthby(lstofPayment.getColAuthorizedByDT());
					collectDetails.setAuthdate(new Date());
				}
			} else if (lstofPayment.getColpaymentmodeCode().equalsIgnoreCase(Constants.ChequeCode)) {
				collectDetails.setChequeRef(lstofPayment.getColchequeRefNo());
				collectDetails.setChequeDate(lstofPayment.getColchequeDate());
				collectDetails.setApprovalNo(lstofPayment.getColApprovalNo());
				// collectDetails.setChequeBankRef(lstofPayment.getColBankCodeDT());
			} else if (lstofPayment.getColpaymentmodeCode().equalsIgnoreCase(Constants.BankTransferCode)) {
				collectDetails.setApprovalNo(lstofPayment.getColApprovalNo());
			}

			collectDetails.setIsActive(Constants.Yes);
			try {
				collectDetails.setAcyymm(new SimpleDateFormat("dd/MM/yyyy").parse(getCurrentDateWithFormat()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			collectDetails.setCreatedBy(sessionManage.getUserName());
			collectDetails.setCreatedDate(new Date());

			BigDecimal locCode = BigDecimal.ZERO;
			List<CountryBranch> listCode = generalService.getCountryBranchLocCode(new BigDecimal(sessionManage.getBranchId()));
			if (listCode != null && listCode.size() > 0) {
				locCode = listCode.get(0).getBranchId();
			}

			collectDetails.setLocCode(locCode);

			List<CompanyMaster> companyMaster = null;
			companyMaster = miscellaneousReceiptPaymentService.getCompanyCode(sessionManage.getCompanyId());
			if (companyMaster.size() > 0) {
				collectDetails.setCompanyCode(companyMaster.get(0).getCompanyCode());

			}
			List<Document> document = null;

			if (getDocumentCode().equals(Constants.DOCUMENT_CODE_FOR_RECEIVE)) {

				document = miscellaneousReceiptPaymentService.getDocumentId(new BigDecimal(Constants.DOCUMENT_CODE_FOR_RECEIVE));

			} else {
				document = miscellaneousReceiptPaymentService.getDocumentId(new BigDecimal(Constants.DOCUMENT_CODE_FOR_PAYMENT));

			}
			collectDetails.setDocumentId(document.get(0).getDocumentID());

			collectDetails.setPaymentModeId(lstofPayment.getColpaymentmodeIDtypeDT());

			collectDetaillist.add(collectDetails);
		}
		savingMap.put("COLLECTDETAILLIST", collectDetaillist);
	}

	public void approveReceiveRecord(BigDecimal documentCode, String programName) throws Exception {
		if (savingMap != null) {
			savingMap.clear();
		}
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
		// String date = "01/09/14" ;Collect collectid , BigDecimal exchangerate
		// , BigDecimal localnettrastamount
		Date acc_Month = null;
		try {
			acc_Month = DATE_FORMAT.parse(getCurrentDateWithFormat());

			savingMap.put("RECEIPTPAYMENTPK", getReceiptPaymentPk());
			savingMap.put("DOCUMENTCODE", documentCode);
			savingMap.put("USERNAME", sessionManage.getUserName());

			Collect collect = saveCollect();
			saveCollectDetail(collect);

			if (getLstRefundData() != null && getLstRefundData().size() > 0) {
				saveDenominationRefund(acc_Month, documentCode, programName);
			}
			if (getLstData() != null && getLstData().size() > 0) {
				saveDenominationPaid(acc_Month, documentCode, programName);
			}

		} catch (Exception e) {
			e.printStackTrace();
			setWarningMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("warningDailog.show();");

		}

	}

	public void approvePaymentRecord(BigDecimal documentCode, String programName) throws Exception {
		if (savingMap != null) {
			savingMap.clear();
		}

		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
		// String date = "01/09/14" ;Collect collectid , BigDecimal exchangerate
		// , BigDecimal localnettrastamount
		Date acc_Month = null;
		try {
			acc_Month = DATE_FORMAT.parse(getCurrentDateWithFormat());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		savingMap.put("RECEIPTPAYMENTPK", getReceiptPaymentPk());
		savingMap.put("DOCUMENTCODE", documentCode);
		savingMap.put("USERNAME", sessionManage.getUserName());

		Payment payment = savePayment(acc_Month);
		// savePaymentDetail(payment,acc_Month);

		if (getLstRefundData() != null && getLstRefundData().size() > 0) {
			saveDenominationRefund(acc_Month, documentCode, programName);
		}
		if (getLstData() != null && getLstData().size() > 0) {
			saveDenominationPaid(acc_Month, documentCode, programName);
		}
	}

	// knet code
	Receipt receipt = null;

	private boolean BooColApprovalNo;

	public Boolean getRenderKnetPanel() {
		return renderKnetPanel;
	}

	public void setRenderKnetPanel(Boolean renderKnetPanel) {
		this.renderKnetPanel = renderKnetPanel;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public String getKnetReceiptTime() {
		return knetReceiptTime;
	}

	public void setKnetReceiptTime(String knetReceiptTime) {
		this.knetReceiptTime = knetReceiptTime;
	}

	public String getKnetReceiptDate() {
		return knetReceiptDate;
	}

	public void setKnetReceiptDate(String knetReceiptDate) {
		this.knetReceiptDate = knetReceiptDate;
	}

	public String getKnetIposReceipt() {
		return knetIposReceipt;
	}

	public void setKnetIposReceipt(String knetIposReceipt) {
		this.knetIposReceipt = knetIposReceipt;
	}

	public void approvalNoFromKnetIpos() throws UnsupportedEncodingException, IOException {

		try {
			log.info("******approvalNoFromKnetIpos START *****\n" + getColCash());
			System.out.println("getColCash amount  method " + getColCash() + "\t getColBankCode() :" + getColBankCode() + "\t getColCardNo() :" + getColCardNo());
			int trnxAmount = 0;
			if (getColCash() != null) {
				trnxAmount = (int) (getColCash().intValue() * 1000);
			} else {
				setExceptionMessage("Please Enter Amount");
				RequestContext.getCurrentInstance().execute("alertmsg.show();");
				return;
			}

			if (getColBankCode() != null) {
				log.info("Get Card No :" + getColBankCode());
			} else {
				setExceptionMessage("Please Select Bank");
				RequestContext.getCurrentInstance().execute("alertmsg.show();");
				return;
			}

			if (getColCardNo() != null) {
				log.info("getColCardNo :" + getColCardNo());
			} else {
				setExceptionMessage("Please Enter Card Number");
				RequestContext.getCurrentInstance().execute("alertmsg.show();");
				return;
			}

			if (getColNameofCard() != null) {
				log.info("getColNameofCard :" + getColNameofCard());
			} else {
				setExceptionMessage("Please Enter Name of Card");
				RequestContext.getCurrentInstance().execute("alertmsg.show();");
				return;
			}

			String workingDir = System.getProperty("user.dir");
			log.info("workingDir :" + workingDir);
			System.out.println("web  workingDir approvalNoFromKnetIpos  application Current working directory : " + workingDir);

			String absoluteWebPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");

			log.info("absoluteWebPath :" + absoluteWebPath);

			if (receipt != null) {
				receipt.clearAll();
			}

			System.out.println("web absoluteWebPath approvalNoFromKnetIpos  application Current working directory : " + absoluteWebPath);

			WinEPTS_Wrapper api = new WinEPTS_Wrapper();
			String cashhier = sessionManage.getUserName() + "-" + getCustomerId();
			log.info("*********Trnx net amount :" + trnxAmount + "\t Cashier or user Name: " + cashhier + "\t Document No." + getTransferNo());

			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			String ipAddress = request.getHeader("X-FORWARDED-FOR");
			if (ipAddress == null) {
				ipAddress = request.getRemoteAddr();
			}

			log.info("ipAddress --" + ipAddress);

			/**
			 * commented by Rabil on 01/05/2016 api.setIPAdress(ipAddress);
			 * 
			 * 
			 * api.Payment(trnxAmount, cashhier,
			 * getApplicationDocNum().toString()); receipt = api.getReceipt();
			 */
			// log.info("*********Web receipt receiptData:" +
			// receipt.receiptData);

			// New code aded by Rabil on 1/05/2016
			Hashtable<String, String> ht = new Hashtable<String, String>();
			StringBuffer sb = new StringBuffer();
			StringBuffer urlBuffer = new StringBuffer();
			String appender = "?";
			String ampersand = "&";
			String equals = "=";
			String colon = ":";
			String rootContext = "/IposWeb/ipos/ipos.jsp"; // KwtSmartCard/smartcard
			String env = "test";
			// HttpServletRequest request = (HttpServletRequest)
			// FacesContext.getCurrentInstance().getExternalContext().getRequest();
			String host = request.getHeader("X-FORWARDED-FOR");
			if (host == null) {
				host = request.getRemoteAddr();
			}
			String prdPort = "8085";

			if (env.equalsIgnoreCase("test")) {
				urlBuffer.append("http://").append(host).append(colon).append(prdPort).append(rootContext).append(appender);
			} else if (env.equalsIgnoreCase("live")) {
				urlBuffer.append("https://").append(host);
				if (prdPort != null && prdPort.length() > 0) {
					urlBuffer.append(colon).append(prdPort);
				}
				urlBuffer.append(rootContext).append(appender);
			}
			urlBuffer.append("amount").append(equals).append(trnxAmount).append(ampersand).append("cashier").append(equals).append(cashhier).append(ampersand).append("docno").append(equals)
					.append(getTransferNo().toString()).append(ampersand).append("action").append(equals).append("P");
			log.info("urlBuffer :" + urlBuffer.toString());
			try {
				URL knetRequest = new URL(urlBuffer.toString());
				HttpURLConnection testyc = null;
				HttpsURLConnection prdyc = null;
				BufferedReader in = null;
				if (env.equalsIgnoreCase("test")) {
					testyc = (HttpURLConnection) knetRequest.openConnection();
					in = new BufferedReader(new InputStreamReader(testyc.getInputStream()));
				} else if (env.equalsIgnoreCase("live")) {
					prdyc = (HttpsURLConnection) knetRequest.openConnection();
					in = new BufferedReader(new InputStreamReader(prdyc.getInputStream()));
				}
				String inputLine;
				// Hashtable<String, String> ht = new Hashtable<String,
				// String>();
				String receiptDataKey = "Key-Receipt Data";
				while ((inputLine = in.readLine()) != null) {
					System.out.println(inputLine);
					if (inputLine != null) {
						String[] str = inputLine.split(":");
						if (str != null && str.length > 1) {
							ht.put(str[0].trim(), str[1].trim());
						} else {
							if (inputLine != null && !inputLine.trim().equalsIgnoreCase("Receipt Data  :")) {
								sb.append("").append(inputLine);
							}
						}
					}

					// sb.append(inputLine + "##");
				}
				System.out.println("sb :" + sb);
				ht.put(receiptDataKey, sb.toString());
				in.close();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("ht :" + ht.toString());
			/*
			 * System.out.println("str REceipt:"+sb.toString()); String[] str =
			 * sb.toString().split("#"); System.out.println("str REceipt:"+str);
			 * 
			 * if(sb!=null && sb.length()>0){ String[] strSp =
			 * sb.toString().split("##"); if(strSp!=null && strSp.length>0){
			 * for(int i=0;i<strSp.length;i++){
			 * System.out.println("strSp["+i+"] "+strSp[i]); if( strSp[i]!=null
			 * && !strSp[i].equals("")){
			 * if(strSp[i].trim().equalsIgnoreCase("Receipt Data")){
			 * ht.put("Receipt Data", strSp[i].replace("##", "")); }else{
			 * String[] strCol = strSp[i].split(":");
			 * System.out.println("Key  :"+strCol[0]+"\t Value :"+strCol[1]);
			 * ht.put(strCol[0], strCol[1]); } } } } }
			 */

			// New Code ended here

			// if (receipt != null) {
			if (ht != null) {
				System.out.println("IF approvalNoFromKnetIpos  method Receipt Data  :\n" + ht.get("Key-Receipt Data"));
				log.info("Receive  from KNET-IPOS:" + ht.toString());
				log.info("KNET-IPO auth code :" + ht.get("Auth. Code"));
				log.info("KNET-IPO error description  :" + ht.get("MsgCode") + "\t  :" + ht.get("Error Desc."));
				if (ht.get("Auth. Code") != null && ht.get("Auth. Code").equals("")) {
					setColApprovalNo(ht.get("Auth. Code"));// receipt.authorizationCode);
				}
				// log.info("******approvalNoFromKnetIpos :\n" +
				// receipt.receiptData);

				// Approved :176 error 255

				// if (receipt.errorDescription.equalsIgnoreCase("APPROVED") ||
				// (receipt.msgCode == 176)) {
				if (ht.get("Error Desc.").trim() != null && (ht.get("Error Desc.").trim().equalsIgnoreCase("APPROVED") || ht.get("MsgCode").equals("176"))) {
					KnetLog knetLog = new KnetLog();
					setBooColApprovalNo(true);
					setKnetIposReceipt(ht.get("Key-Receipt Data").trim());// receipt.receiptData.toString());
					setColApprovalNo(ht.get("Auth. Code").trim());// receipt.authorizationCode);
					setKnetTranId(ht.get("Trans. ID").trim());// (receipt.transID);

					String dateTime = null;
					if (ht.get("Receipt Date") != null && ht.get("Receipt Time") != null) {
						dateTime = ht.get("Receipt Date").substring(0, 2) + "/" + ht.get("Receipt Time").substring(2, 4) + "/" + ht.get("Receipt Date").substring(4, 8) + " "
								+ ht.get("Receipt Time").substring(0, 2) + ":" + ht.get("Receipt Date").substring(2, 4);

					}
					log.info("Knet ReceiptDate and Time: " + dateTime);
					System.out.println("Knet ReceiptDate and Time: " + dateTime);
					setKnetReceiptDate(dateTime);
					setKnetReceiptTime(ht.get("Receipt Time"));// (receipt.receiptTime);

					knetLog.setApplicationCountryId(sessionManage.getCountryId());
					knetLog.setCompanyId(sessionManage.getCompanyId());
					knetLog.setCustmoerId(getCustomerId());
					knetLog.setAuthCode(ht.get("Auth. Code"));// receipt.authorizationCode);
					knetLog.setCreatedBy(sessionManage.getUserName());
					knetLog.setCreatedDate(new Date());
					knetLog.setIsActive("Y");
					// knetLog.setKnetAmount(new
					// BigDecimal(receipt.amount).divide(new BigDecimal(1000)));
					knetLog.setKnetAmount(new BigDecimal(ht.get("Amount")).divide(new BigDecimal(1000)));
					knetLog.setKnetMessage("TRANSACTION APPROVED");
					knetLog.setReceipt(ht.get("Key-Receipt Data"));// receipt.toString());
					knetLog.setReceiptData(ht.toString());// get("Key-Receipt Data"));//receipt.receiptData);
					iPersonalRemittanceService.saveKnetLogDetails(knetLog);

				} else if (ht.get("Error Desc.") != null && (ht.get("Error Desc.").equalsIgnoreCase("ISSUER NOT AVAILABLE") || ht.get("MsgCode").equals("255"))) {

					String dateTime = null;

					if (ht.get("Receipt Date") != null && ht.get("Receipt Time") != null) {
						dateTime = ht.get("Receipt Date").substring(0, 2) + "/" + ht.get("Receipt Time").substring(2, 4) + "/" + ht.get("Receipt Date").substring(4, 8) + " "
								+ ht.get("Receipt Time").substring(0, 2) + ":" + ht.get("Receipt Date").substring(2, 4);

					}
					log.info("Knet ReceiptDate and Time: " + dateTime);

					KnetLog knetLog = new KnetLog();
					knetLog.setApplicationCountryId(sessionManage.getCountryId());
					knetLog.setCompanyId(sessionManage.getCompanyId());
					knetLog.setCustmoerId(getCustomerId());
					knetLog.setAuthCode(ht.get("Auth. Code"));// receipt.authorizationCode);
					knetLog.setCreatedBy(sessionManage.getUserName());
					knetLog.setCreatedDate(new Date());
					knetLog.setIsActive("N");
					knetLog.setKnetAmount(new BigDecimal(ht.get("Amount")).divide(new BigDecimal(1000)));
					knetLog.setKnetMessage(ht.get("Error Desc."));// receipt.errorDescription);
					knetLog.setReceipt(ht.get("Key-Receipt Data"));// receipt.toString());
					// knetLog.setReceiptData(ht.get("Key-Receipt Data"));//receipt.receiptData);
					knetLog.setReceiptData(ht.toString());

					iPersonalRemittanceService.saveKnetLogDetails(knetLog);

					setBooColApprovalNo(false);
					setKnetIposReceipt(null);
					// setExceptionMessage(receipt.errorDescription +
					// " PLEASE REMOVE CARD AND TRY AGAIN");
					setExceptionMessage(ht.get("Error Desc.") + " PLEASE REMOVE CARD AND TRY AGAIN");
					RequestContext.getCurrentInstance().execute("alertmsg.show();");
					return;
				}
			} else {

				KnetLog knetLog = new KnetLog();
				knetLog.setApplicationCountryId(sessionManage.getCountryId());
				knetLog.setCompanyId(sessionManage.getCompanyId());
				knetLog.setCustmoerId(getCustomerId());
				knetLog.setAuthCode("C");
				knetLog.setCreatedBy(sessionManage.getUserName());
				knetLog.setCreatedDate(new Date());
				knetLog.setIsActive("C");
				knetLog.setKnetAmount(getColCash());
				knetLog.setKnetMessage("CANNOT CONNECT TO KNET SERVER . ");
				knetLog.setReceipt("CANNOT CONNECT TO KNET SERVER. ");
				knetLog.setReceiptData("CANNOT CONNECT TO KNET SERVER. ");

				iPersonalRemittanceService.saveKnetLogDetails(knetLog);

				setBooColApprovalNo(false);
				setExceptionMessage("CANNOT CONNECT TO KNET SERVER. ");
				RequestContext.getCurrentInstance().execute("alertmsg.show();");
				return;
			}

		} catch (Exception e) {
			setBooColApprovalNo(false);
			e.printStackTrace();
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
			return;
		}

		System.out.println("++++++++++++++++++PaymentInitiation END ++++++++++++++++++++++\n");
		log.info("******approvalNoFromKnetIpos END *****\n");
	}

	public boolean isBooColApprovalNo() {
		return BooColApprovalNo;
	}

	public void setBooColApprovalNo(boolean booColApprovalNo) {
		BooColApprovalNo = booColApprovalNo;
	}

	public void checkingCardNumberLength() {
		if (getColCardNo() != null && getColCardNoLength() != null) {
			if ((getColCardNo().toString()).length() != getColCardNoLength().intValue()) {
				setColCardNo(null);
				RequestContext.getCurrentInstance().execute("misMatchBankCardLength.show();");
			}
		}
	}

	public void populateCustKnetCardDetails() {
		for (CustomerBank customerBank : lstDebitCard) {
			if (customerBank.getDebitCard().equalsIgnoreCase(getColCardNo().toPlainString())) {
				if (customerBank.getAuthorizedBy() != null) {
					/*
					 * List<Employee> localEmpllist =
					 * generalService.getEmployeelist
					 * (sessionStateManage.getCountryId(),new
					 * BigDecimal(sessionStateManage.getBranchId()),new
					 * BigDecimal(sessionStateManage.getRoleId()));
					 */

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
			if (OthersSts) {
				setColBankCode(null);
				setExceptionMessage(WarningHandler.showWarningMessage("lbl.bnkOthers", sessionManage.getLanguageId()));
				RequestContext.getCurrentInstance().execute("alertmsg.show();");
				return;
			}

			localbankListForBankCode = icustomerBankService.getCustomerLocalBankListFromView(sessionManage.getCountryId(), getColBankCode());

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
									// List<Employee> localEmpllist =
									// generalService.getEmployeelist(sessionStateManage.getCountryId(),new
									// BigDecimal(sessionStateManage.getBranchId()),new
									// BigDecimal(sessionStateManage.getRoleId()));

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
								/** Added by Rabil on 13/01/2016 **/
								lstofDebitCard.setDebitCardName(lstDebitcrd.getDebitCardName());
								/** End by Rabil on 13/01/2016 **/
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

	public void firstNameCheck() {
		if (getColNameofCard().equalsIgnoreCase(getCustomerName())) {
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

	public void verifyPassword() {
		totalUamount = BigDecimal.ZERO;
		checkcashcollection();

		if (!checkKnetAmount) {

			String errorMessage;
			try {
				errorMessage = iPersonalRemittanceService.getExCheckCashLimitProcedure(sessionManage.getCountryId(), getCustomerId(), getPaymentModeId(), getColamountKWD());
				System.out.println("errorMessage :" + errorMessage);
				if (errorMessage != null && !errorMessage.equals("")) {
					setExcheckCashLimitMessage(errorMessage);
					RequestContext.getCurrentInstance().execute("exCheckCashLimit.show();");
				} else {
					setExcheckCashLimitMessage(null);

					if (getPaymentCode() != null) {
						List<PaymentMode> paymentModedetails = ipaymentService.getPaymentCheck(getPaymentCode());

						if (paymentModedetails.size() != 0) {

							if (getPaymentCode().equalsIgnoreCase(Constants.CashCode)) {
								calculatingNetAmountDT();
							} else if (getPaymentCode().equalsIgnoreCase(Constants.KNETCode)) {
								if (getColAuthorizedby() != null) {
									List<DebitAutendicationView> lstEmpLogin = new ArrayList<DebitAutendicationView>();
									String userNames = getColAuthorizedby();
									lstEmpLogin = iPersonalRemittanceService.getdebitAutendicationListByUserId(getColAuthorizedby(), getColpassword());

									if (lstEmpLogin.size() != 0) {
										checkingPaymentCardinDB();
									} else {
										setColpassword(null);
										RequestContext.getCurrentInstance().execute("passwordcheck.show();");
									}
								} else {
									checkingPaymentCardinDB();
								}

							} else if (getPaymentCode().equalsIgnoreCase(Constants.ChequeCode)) {

								Boolean checkdata = checkingChequeDuplicateCheck();

								if (checkdata) {
									localbankListForBankCode = icustomerBankService.getCustomerLocalBankListFromView(sessionManage.getCountryId(), getColChequeRef());
									addPaymentModerecord();
									if (coldatatablevalues.size() > 0) {
										for (PersonalRemittanceCollectionDataTable collectionlst : coldatatablevalues) {
											totalUamount = totalUamount.add(collectionlst.getColAmountDT());
										}
										setToalUsedAmount(totalUamount);
										if (getToalUsedAmount().compareTo(getNetAmount()) > 0) {
											setTotalbalanceAmount(BigDecimal.ZERO);
											setTotalRefund(getToalUsedAmount().subtract(getNetAmount()));
										} else if (getToalUsedAmount().compareTo(getNetAmount()) < 0) {
											setTotalbalanceAmount(getNetAmount().subtract(getToalUsedAmount()));
											setTotalRefund(BigDecimal.ZERO);
										} else {
											setTotalbalanceAmount(BigDecimal.ZERO);
											setTotalRefund(BigDecimal.ZERO);
										}
									}
									setBooRendercollectiondatatable(true);
									clearingDetailAfterAdding();
								} else {
									RequestContext.getCurrentInstance().execute("chequerefexists.show();");
								}
							} else if (getPaymentCode().equalsIgnoreCase(Constants.BankTransferCode)) {
								localbankListForBankCode = icustomerBankService.getCustomerLocalBankListFromView(sessionManage.getCountryId(), getColChequeRef());
								addPaymentModerecord();
								if (coldatatablevalues.size() > 0) {
									for (PersonalRemittanceCollectionDataTable collectionlst : coldatatablevalues) {
										totalUamount = totalUamount.add(collectionlst.getColAmountDT());
									}
									setToalUsedAmount(totalUamount);
									if (getToalUsedAmount().compareTo(getNetAmount()) > 0) {
										setTotalbalanceAmount(BigDecimal.ZERO);
										setTotalRefund(getToalUsedAmount().subtract(getNetAmount()));
									} else if (getToalUsedAmount().compareTo(getNetAmount()) < 0) {
										setTotalbalanceAmount(getNetAmount().subtract(getToalUsedAmount()));
										setTotalRefund(BigDecimal.ZERO);
									} else {
										setTotalbalanceAmount(BigDecimal.ZERO);
										setTotalRefund(BigDecimal.ZERO);
									}
								}
								// setBooRendercollectiondatatable(true);
								clearingDetailAfterAdding();
							} else {
								System.out.println("Other Payment Mode");
							}

						}
					} else {
						setBooRenderColDebitCard(false);
						setRenderChequePanel(false);
					}

				}
			} catch (AMGException e) {
				setExceptionMessage(e.getMessage());
				RequestContext.getCurrentInstance().execute("exceptionMessage.show();");
			}
		}
	}

	// checking cheque data with datatable
	public Boolean checkingChequeDuplicateCheck() {
		Boolean checkCheque = false;
		int i = 0;
		if (getColChequeRef() != null && coldatatablevalues.size() != 0) {
			for (PersonalRemittanceCollectionDataTable lstpaymentdata : coldatatablevalues) {
				i = 0;
				if (lstpaymentdata.getColBankCodeDT() != null && lstpaymentdata.getColBankCodeDT().equalsIgnoreCase(getColchequebankCode())) {
					if (lstpaymentdata.getColchequeRefNo() != null && lstpaymentdata.getColchequeRefNo().equalsIgnoreCase(getColChequeRef())) {
						i = 1;
						break;
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

	boolean checkKnetAmount;

	// calculation of cash while entering
	public void checkcashcollection() {
		checkKnetAmount = false;
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
				BigDecimal percentageAmount = percentage.multiply(getNetAmount());
				totalAmount = GetRound.roundBigDecimal(percentageAmount.add(getNetAmount()),
						foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId())));

				if (colKnetAmount.compareTo(totalAmount) <= 0) {
					setColamountKWD(getColCash());
				} else {
					setColCash(null);
					setErrcolCashExistsLimit(totalAmount);
					RequestContext.getCurrentInstance().execute("amountgreater.show();");
					checkKnetAmount = true;
				}
			} else if (getColpaymentmodeCode().equalsIgnoreCase(Constants.ChequeCode)) {
				setColamountKWD(getColCash());
			} else if (getColpaymentmodeCode().equalsIgnoreCase(Constants.BankTransferCode)) {
				setColamountKWD(getColCash());
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
		customerBank.setCreatedBy(sessionManage.getUserName());
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
		totalUamount = BigDecimal.ZERO;
		BigDecimal paymentModeCashId = ipaymentService.fetchPaymodeMasterId(Constants.CASHNAME,
				new BigDecimal(sessionManage.isExists("languageId") ? sessionManage.getSessionValue("languageId") : "1"));
		if (paymentModeCashId != null) {
			boolean flag = true;
			setBooRendercollectiondatatable(true);

			if (coldatatablevalues.size() != 0) {
				for (PersonalRemittanceCollectionDataTable collectionlst : coldatatablevalues) {
					i = 0;
					if (collectionlst.getColpaymentmodeIDtypeDT().compareTo((getPaymentModeId() == null ? new BigDecimal(0) : getPaymentModeId())) == 0) {
						if (collectionlst.getColpaymentmodeIDtypeDT().compareTo(paymentModeCashId) == 0) {
							clearingDetailAfterAdding();
							RequestContext.getCurrentInstance().execute("cashexists.show();");
							flag = false;
							break;
						} else {
							if (collectionlst.getColBankCodeDT().compareTo(getColBankCode()) == 0) {
								if (collectionlst.getColCardNumberDT().compareTo(getColCardNo()) == 0) {
									if (collectionlst.getColApprovalNo().compareTo(getColApprovalNo()) == 0) {
										clearingDetailAfterAdding();
										RequestContext.getCurrentInstance().execute("bankexists.show();");
										flag = false;
										break;
									} else {
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
				setTotalRefund(null);
				addPaymentModerecord();
			}

			if (coldatatablevalues.size() > 0 && flag == true) {
				for (PersonalRemittanceCollectionDataTable collectionlst : coldatatablevalues) {
					// totalUamount=totalUamount+collectionlst.getColAmountDT().intValue();
					totalUamount = totalUamount.add(collectionlst.getColAmountDT());
				}
				setToalUsedAmount(totalUamount);
				if (getToalUsedAmount().compareTo(getNetAmount()) > 0) {
					setTotalbalanceAmount(BigDecimal.ZERO);
					setTotalRefund(getToalUsedAmount().subtract(getNetAmount()));
				} else if (getToalUsedAmount().compareTo(getNetAmount()) < 0) {
					setTotalbalanceAmount(getNetAmount().subtract(getToalUsedAmount()));
					setTotalRefund(BigDecimal.ZERO);
				} else {
					setTotalbalanceAmount(BigDecimal.ZERO);
					setTotalRefund(BigDecimal.ZERO);
				}
			}

			clearingDetailAfterAdding();
		}
	}

	public void clearingDetailAfterAdding() {
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
		setColChequeRef(null);
		setColChequeDate(null);
		setColChequeApprovalNo(null);
		lstDebitCard.clear();
		changeofPaymentMode();
	}

	public String getExcheckCashLimitMessage() {
		return excheckCashLimitMessage;
	}

	public void setExcheckCashLimitMessage(String excheckCashLimitMessage) {
		this.excheckCashLimitMessage = excheckCashLimitMessage;
	}

	public BigDecimal getErrcolCashExistsLimit() {
		return errcolCashExistsLimit;
	}

	public void setErrcolCashExistsLimit(BigDecimal errcolCashExistsLimit) {
		this.errcolCashExistsLimit = errcolCashExistsLimit;
	}

	public BigDecimal getTotalbalanceAmount() {
		return totalbalanceAmount;
	}

	public void setTotalbalanceAmount(BigDecimal totalbalanceAmount) {
		this.totalbalanceAmount = totalbalanceAmount;
	}

	public BigDecimal getToalUsedAmount() {
		return toalUsedAmount;
	}

	public void setToalUsedAmount(BigDecimal toalUsedAmount) {
		this.toalUsedAmount = toalUsedAmount;
	}

	public String getCyberPassword() {
		return cyberPassword;
	}

	public void setCyberPassword(String cyberPassword) {
		this.cyberPassword = cyberPassword;
	}

	public Boolean getBooEdit() {
		return booEdit;
	}

	public void setBooEdit(Boolean booEdit) {
		this.booEdit = booEdit;
	}

	// approve button checking condition in denomination page
	public void saveApproveButtonDenominationPage() {

		try {
			if (getDocumentCode().equals(new BigDecimal(Constants.DOCUMENT_CODE_FOR_RECEIVE))) {
				if (lstData != null && lstData.size() != 0) {

					if (getTotalDenominationCashEntered().compareTo(getTotalCashAmount()) == 0) {
						if (getTotalDenominationCashEntered().compareTo(getTotalCashAmount()) == 0) {
							saveApprovedRecord();
						} else {
							setWarningMessage("Total Cash and Total Cash Received should be Tally");
							RequestContext.getCurrentInstance().execute("warningDailog.show();");
						}
					} else {
						setWarningMessage("Please Enter the Denomination");
						RequestContext.getCurrentInstance().execute("warningDailog.show();");
					}
				}

			} else if (getDocumentCode().equals(new BigDecimal(Constants.DOCUMENT_CODE_FOR_PAYMENT))) {
				if (lstData != null && lstData.size() != 0) {

					if (getTotalDenominationCashEntered() != null && getTotalRefund() != null) {
						if (getTotalDenominationCashEntered().compareTo(getTotalRefund()) == 0) {
							saveApprovedRecord();
						} else {
							setWarningMessage("Refund Amount and Total Cash Received should be Tally");
							RequestContext.getCurrentInstance().execute("warningDailog.show();");
						}
					} else {
						setWarningMessage("Please Enter the Denomination");
						RequestContext.getCurrentInstance().execute("warningDailog.show();");
					}
				}
			} else {
				// not both
			}
		} catch (Exception e) {
			setWarningMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("warningDailog.show();");
		}

	}

	// approve button checking condition in denomination page
	public void saveApproveButtonReFundDenominationPage() {

		try {
			if (getDocumentCode().equals(new BigDecimal(Constants.DOCUMENT_CODE_FOR_RECEIVE))) {
				if (lstRefundData != null && lstRefundData.size() != 0) {

					if (getTotalRefundCashEntered() != null && getTotalRefund() != null) {
						if (getTotalRefundCashEntered().compareTo(getTotalRefund()) == 0) {
							saveApprovedRecord();
						} else {
							setWarningMessage("Refund Amount and Total Cash Received should be Tally");
							RequestContext.getCurrentInstance().execute("saveApproveCheck.show();");
						}
					} else {
						setWarningMessage("Please Enter the Denomination");
						RequestContext.getCurrentInstance().execute("saveApproveCheck.show();");
					}
				}
			} else if (getDocumentCode().equals(new BigDecimal(Constants.DOCUMENT_CODE_FOR_PAYMENT))) {
				if (lstRefundData != null && lstRefundData.size() != 0) {

					if (getTotalRefundCashEntered() != null && getNetAmount() != null) {
						if (getTotalRefundCashEntered().compareTo(getNetAmount()) == 0) {
							saveApprovedRecord();
						} else {
							setWarningMessage("Refund Amount and Total Refund Cash should be Tally");
							RequestContext.getCurrentInstance().execute("saveApproveCheck.show();");
						}
					} else {
						setWarningMessage("Please Enter the Denomination");
						RequestContext.getCurrentInstance().execute("saveApproveCheck.show();");
					}
				}
			} else {
				// not both
			}
		} catch (Exception e) {
			setWarningMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("warningDailog.show();");
		}
	}

	// approve save in refund and denomination
	public void saveApprovedRecord() {
		savingMap = new HashMap<String, Object>();
		try {
			if (getDocumentCode().equals(new BigDecimal(Constants.DOCUMENT_CODE_FOR_RECEIVE))) {
				approveReceiveRecord(getDocumentCode(), "RECEIVE");
			} else if (getDocumentCode().equals(new BigDecimal(Constants.DOCUMENT_CODE_FOR_PAYMENT))) {
				approvePaymentRecord(getDocumentCode(), "PAYMENT");
			} else {
				// not both
			}

			if (savingMap != null) {

				miscellaneousReceiptPaymentService.saveRecords(savingMap);

				BigDecimal applicationCountryId = null;
				BigDecimal companyId = null;
				BigDecimal documentCode = null;
				BigDecimal documentFinanceYr = null;
				BigDecimal documentNo = null;
				BigDecimal oldRemitDocFinyr = getDocumentYear();
				BigDecimal oldRemitDocNo = getDocumentNo();

				if (getDocumentCode().equals(new BigDecimal(2))) {
					Collect collect = (Collect) savingMap.get("COLLECT");
					List<Collect> collectionList = miscellaneousReceiptPaymentService.getCollectionListById(collect.getCollectionId());
					if (collectionList.size() > 0) {
						applicationCountryId = collectionList.get(0).getApplicationCountryId();
						companyId = collectionList.get(0).getFsCompanyMaster().getCompanyId();
						documentCode = collectionList.get(0).getDocumentCode();
						documentFinanceYr = collectionList.get(0).getDocumentFinanceYear();
						documentNo = collectionList.get(0).getDocumentNo();
					}

					HashMap<String, String> inputValues = new HashMap<String, String>();
					inputValues.put("App_Country_Id", applicationCountryId.toString());
					inputValues.put("Company_Id", companyId.toString());
					inputValues.put("Doc_Code", documentCode.toString());
					inputValues.put("Doc_Finan_year", documentFinanceYr.toString());
					inputValues.put("Doc_No", documentNo.toString());
					if (getTransferNo() != null) {
						inputValues.put("Old_Remit_Doc_No", getTransferNo().toString());
						inputValues.put("Old_Remit_Fin_Year", getTransferRefYear().toString());
					} else {
						inputValues.put("Old_Remit_Doc_No", "");
						inputValues.put("Old_Remit_Fin_Year", "");
					}
					// miscellaneousReceiptPaymentService.moveToApproveRecordFromCollectionOldEmosSystem(applicationCountryId,
					// companyId, documentCode, documentFinanceYr, documentNo,
					// oldRemitDocFinyr, oldRemitDocNo);
					HashMap<String, String> outPutValues = miscellaneousReceiptPaymentService.moveToApproveRecordFromCollectionOldEmosSystem(inputValues);
					String errMsg = outPutValues.get("P_ERROR_MESSAGE");
					if (errMsg != null && !(errMsg.equalsIgnoreCase(""))) {
						setWarningMessage("Procedure Error" + errMsg);
						log.error("clickOnOKSave  :" + errMsg);
						RequestContext.getCurrentInstance().execute("warningDailog.show();");
						// miscellanousreceiptApprovalNavigation();
						return;
					}
				} else {
					Payment payment = (Payment) savingMap.get("PAYMENT");
					List<Payment> paymentList = miscellaneousReceiptPaymentService.getPaymentListById(payment.getPaymentId(), new BigDecimal(Constants.DOCUMENT_CODE_FOR_PAYMENT));
					if (paymentList.size() > 0) {
						applicationCountryId = paymentList.get(0).getCountryId().getCountryId();
						companyId = paymentList.get(0).getCompanyId();
						documentCode = paymentList.get(0).getDocCode();
						documentFinanceYr = paymentList.get(0).getDocYear();
						documentNo = paymentList.get(0).getDocNumber();
					}
					HashMap<String, String> inputValues = new HashMap<String, String>();
					inputValues.put("App_Country_Id", applicationCountryId.toString());
					inputValues.put("Company_Id", companyId.toString());
					inputValues.put("Doc_Code", documentCode.toString());
					inputValues.put("Doc_Finan_year", documentFinanceYr.toString());
					inputValues.put("Doc_No", documentNo.toString());
					if (getTransferNo() != null && getTransferRefYear() != null) {
						inputValues.put("Old_Remit_Doc_No", getTransferNo().toPlainString());
						inputValues.put("Old_Remit_Fin_Year", getTransferRefYear().toPlainString());
					} else {
						inputValues.put("Old_Remit_Doc_No", "");
						inputValues.put("Old_Remit_Fin_Year", "");
					}
					// miscellaneousReceiptPaymentService.moveToApproveRecordFromPaymentOldEmosSystem(applicationCountryId,
					// companyId, documentCode, documentFinanceYr, documentNo,
					// oldRemitDocFinyr, oldRemitDocNo);
					HashMap<String, String> outPutValues = miscellaneousReceiptPaymentService.moveToApproveRecordFromPaymentOldEmosSystem(inputValues);
					String errMsg = outPutValues.get("P_ERROR_MESSAGE");
					if (errMsg != null && !(errMsg.equalsIgnoreCase(""))) {
						setWarningMessage("Data Save Successfully but Procedure EX_P_POPULATE_MISC_COLL_PAY Error :" + errMsg + "(" + inputValues + ")");
						log.error("clickOnOKSave  :" + errMsg);
						RequestContext.getCurrentInstance().execute("warningDailog.show();");
						// miscellanousreceiptApprovalNavigation();
						return;
					}

				}

				RequestContext.getCurrentInstance().execute("success.show();");
				return;
			}

		} catch (Exception e) {
			setWarningMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("warningDailog.show();");
		}
	}

	/*
	 * public void approveRecordFromDenomination()throws AMGException{ savingMap
	 * = new HashMap<String, Object>(); try{
	 * if(getTotalDenominationCashEntered()!=null &&
	 * getTotalCashAmount()!=null){ // Here getTotalDenominationCashEntered() ==
	 * getTotalCashAmount()
	 * if(getTotalDenominationCashEntered().compareTo(getTotalCashAmount())==0){
	 * if(getDocumentCode().equals(new
	 * BigDecimal(Constants.DOCUMENT_CODE_FOR_RECEIVE))){
	 * approveReceiveRecord(getDocumentCode(),"RECEIVE"); }else
	 * if(getDocumentCode().equals(new
	 * BigDecimal(Constants.DOCUMENT_CODE_FOR_PAYMENT))){
	 * approvePaymentRecord(getDocumentCode(),"PAYMENT"); }else{ // not both }
	 * 
	 * if(savingMap!=null){
	 * 
	 * miscellaneousReceiptPaymentService.saveRecords(savingMap);
	 * 
	 * Collect collect =(Collect)savingMap.get("COLLECT"); Payment payment
	 * =(Payment)savingMap.get("PAYMENT");
	 * 
	 * BigDecimal applicationCountryId = null; BigDecimal companyId= null;
	 * BigDecimal documentCode= null; BigDecimal documentFinanceYr= null;
	 * BigDecimal documentNo= null; BigDecimal oldRemitDocFinyr=
	 * getDocumentYear(); BigDecimal oldRemitDocNo= getDocumentNo();
	 * 
	 * 
	 * if(getDocumentCode().equals(new BigDecimal(2))){ List<Collect>
	 * collectionList =
	 * miscellaneousReceiptPaymentService.getCollectionListById(
	 * collect.getCollectionId()); if (collectionList.size() > 0) {
	 * 
	 * applicationCountryId= collectionList.get(0).getApplicationCountryId();
	 * companyId = collectionList.get(0).getFsCompanyMaster().getCompanyId();
	 * documentCode = collectionList.get(0).getDocumentCode(); documentFinanceYr
	 * = collectionList.get(0).getDocumentFinanceYear(); documentNo =
	 * collectionList.get(0).getDocumentNo(); }
	 * 
	 * HashMap<String, String> inputValues=new HashMap<String, String>();
	 * inputValues.put("App_Country_Id", applicationCountryId.toString());
	 * inputValues.put("Company_Id", companyId.toString());
	 * inputValues.put("Doc_Code", documentCode.toString());
	 * inputValues.put("Doc_Finan_year", documentFinanceYr.toString());
	 * inputValues.put("Doc_No", documentNo.toString());
	 * if(getTransferNo()!=null){ inputValues.put("Old_Remit_Doc_No",
	 * getTransferNo().toString()); inputValues.put("Old_Remit_Fin_Year",
	 * oldRemitDocFinyr.toString()); } else{ inputValues.put("Old_Remit_Doc_No",
	 * ""); inputValues.put("Old_Remit_Fin_Year", ""); }
	 * //miscellaneousReceiptPaymentService
	 * .moveToApproveRecordFromCollectionOldEmosSystem(applicationCountryId,
	 * companyId, documentCode, documentFinanceYr, documentNo, oldRemitDocFinyr,
	 * oldRemitDocNo); HashMap<String,String>
	 * outPutValues=miscellaneousReceiptPaymentService
	 * .moveToApproveRecordFromCollectionOldEmosSystem(inputValues); String
	 * errMsg=outPutValues.get("P_ERROR_MESSAGE"); if(errMsg!=null &&
	 * !(errMsg.equalsIgnoreCase(""))) {
	 * setWarningMessage("Procedure Error"+errMsg);
	 * log.error("clickOnOKSave  :"+ errMsg);
	 * RequestContext.getCurrentInstance().execute("warningDailog.show();");
	 * miscellanousreceiptApprovalNavigation(); return; } }else{ List<Payment>
	 * paymentList =
	 * miscellaneousReceiptPaymentService.getPaymentListById(payment
	 * .getPaymentId()); if (paymentList.size() > 0) {
	 * 
	 * applicationCountryId= paymentList.get(0).getCountryId().getCountryId();
	 * companyId = paymentList.get(0).getCompanyId(); documentCode =
	 * paymentList.get(0).getDocCode(); documentFinanceYr =
	 * paymentList.get(0).getDocYear(); documentNo =
	 * paymentList.get(0).getDocNumber(); } HashMap<String, String>
	 * inputValues=new HashMap<String, String>();
	 * inputValues.put("App_Country_Id", applicationCountryId.toString());
	 * inputValues.put("Company_Id", companyId.toString());
	 * inputValues.put("Doc_Code", documentCode.toString());
	 * inputValues.put("Doc_Finan_year", documentFinanceYr.toString());
	 * inputValues.put("Doc_No", documentNo.toString());
	 * if(getTransferNo()!=null){
	 * inputValues.put("Old_Remit_Doc_No",getTransferNo().toString());
	 * inputValues.put("Old_Remit_Fin_Year", oldRemitDocFinyr.toString()); }
	 * else{ inputValues.put("Old_Remit_Doc_No","");
	 * inputValues.put("Old_Remit_Fin_Year", ""); }
	 * //miscellaneousReceiptPaymentService
	 * .moveToApproveRecordFromPaymentOldEmosSystem(applicationCountryId,
	 * companyId, documentCode, documentFinanceYr, documentNo, oldRemitDocFinyr,
	 * oldRemitDocNo); HashMap<String,String>
	 * outPutValues=miscellaneousReceiptPaymentService
	 * .moveToApproveRecordFromPaymentOldEmosSystem(inputValues); String
	 * errMsg=outPutValues.get("P_ERROR_MESSAGE"); if(errMsg!=null &&
	 * !(errMsg.equalsIgnoreCase(""))) {
	 * setWarningMessage("Procedure Error :"+errMsg);
	 * log.error("clickOnOKSave  :"+ errMsg);
	 * 
	 * RequestContext.getCurrentInstance().execute("warningDailog.show();");
	 * miscellanousreceiptApprovalNavigation(); return; }
	 * 
	 * }
	 * 
	 * RequestContext.getCurrentInstance().execute("success.show();"); return; }
	 * }else{ setWarningMessage(WarningHandler.showWarningMessage(
	 * "lbl.totalCashAndReceivedShouldmatch", sessionManage.getLanguageId()));
	 * RequestContext.getCurrentInstance().execute("warningDailog.show();"); }
	 * }else{ setWarningMessage(WarningHandler.showWarningMessage(
	 * "lbl.totalCashAndReceivedShouldmatch", sessionManage.getLanguageId()));
	 * RequestContext.getCurrentInstance().execute("warningDailog.show();"); }
	 * }catch(Exception e){
	 * 
	 * setWarningMessage(e.getMessage());
	 * RequestContext.getCurrentInstance().execute("warningDailog.show();"); } }
	 */

	public void approveRecordFromPaymentMode() {
		savingMap = new HashMap<String, Object>();
		try {
			if (getTotalCashEntered() != null && getNetAmount() != null) {

				if (getTotalCashEntered().compareTo(getNetAmount()) == 0) {
					if (getDocumentCode().equals(new BigDecimal(Constants.DOCUMENT_CODE_FOR_RECEIVE))) {
						approveReceiveRecord(getDocumentCode(), "RECEIVE");
					} else if (getDocumentCode().equals(new BigDecimal(Constants.DOCUMENT_CODE_FOR_PAYMENT))) {
						approvePaymentRecord(getDocumentCode(), "PAYMENT");
					} else {
						// not both
					}
					if (savingMap != null) {
						miscellaneousReceiptPaymentService.saveRecords(savingMap);

						Collect collect = (Collect) savingMap.get("COLLECT");
						Payment payment = (Payment) savingMap.get("PAYMENT");

						BigDecimal applicationCountryId = null;
						BigDecimal companyId = null;
						BigDecimal documentCode = null;
						BigDecimal documentFinanceYr = null;
						BigDecimal documentNo = null;
						BigDecimal oldRemitDocFinyr = getDocumentYear();
						BigDecimal oldRemitDocNo = getDocumentNo();

						if (getDocumentCode().equals(new BigDecimal(2))) {
							List<Collect> collectionList = miscellaneousReceiptPaymentService.getCollectionListById(collect.getCollectionId());
							if (collectionList.size() > 0) {

								applicationCountryId = collectionList.get(0).getApplicationCountryId();
								companyId = collectionList.get(0).getFsCompanyMaster().getCompanyId();
								documentCode = collectionList.get(0).getDocumentCode();
								documentFinanceYr = collectionList.get(0).getDocumentFinanceYear();
								documentNo = collectionList.get(0).getDocumentNo();
							}

							HashMap<String, String> inputValues = new HashMap<String, String>();
							inputValues.put("App_Country_Id", applicationCountryId.toString());
							inputValues.put("Company_Id", companyId.toString());
							inputValues.put("Doc_Code", documentCode.toString());
							inputValues.put("Doc_Finan_year", documentFinanceYr.toString());
							inputValues.put("Doc_No", documentNo.toString());
							if (getTransferNo() != null) {
								inputValues.put("Old_Remit_Doc_No", getTransferNo().toString());
								inputValues.put("Old_Remit_Fin_Year", oldRemitDocFinyr.toString());
							} else {
								inputValues.put("Old_Remit_Doc_No", "");
								inputValues.put("Old_Remit_Fin_Year", "");
							}
							// miscellaneousReceiptPaymentService.moveToApproveRecordFromCollectionOldEmosSystem(applicationCountryId,
							// companyId, documentCode, documentFinanceYr,
							// documentNo, oldRemitDocFinyr, oldRemitDocNo);
							HashMap<String, String> outPutValues = miscellaneousReceiptPaymentService.moveToApproveRecordFromCollectionOldEmosSystem(inputValues);
							String errMsg = outPutValues.get("P_ERROR_MESSAGE");
							if (errMsg != null && !(errMsg.equalsIgnoreCase(""))) {
								setWarningMessage(errMsg);
								log.error("clickOnOKSave  :" + errMsg);
								RequestContext.getCurrentInstance().execute("warningDailog.show();");
								return;
							}
						} else {
							List<Payment> paymentList = miscellaneousReceiptPaymentService.getPaymentListById(payment.getPaymentId(), new BigDecimal(Constants.DOCUMENT_CODE_FOR_PAYMENT));
							if (paymentList.size() > 0) {

								applicationCountryId = paymentList.get(0).getCountryId().getCountryId();
								companyId = paymentList.get(0).getCompanyId();
								documentCode = paymentList.get(0).getDocCode();
								documentFinanceYr = paymentList.get(0).getDocYear();
								documentNo = paymentList.get(0).getDocNumber();
							}
							HashMap<String, String> inputValues = new HashMap<String, String>();
							inputValues.put("App_Country_Id", applicationCountryId.toString());
							inputValues.put("Company_Id", companyId.toString());
							inputValues.put("Doc_Code", documentCode.toString());
							inputValues.put("Doc_Finan_year", documentFinanceYr.toString());
							inputValues.put("Doc_No", documentNo.toString());
							if (getTransferNo() != null) {
								inputValues.put("Old_Remit_Doc_No", getTransferNo().toString());
								inputValues.put("Old_Remit_Fin_Year", oldRemitDocFinyr.toString());
							} else {
								inputValues.put("Old_Remit_Doc_No", "");
								inputValues.put("Old_Remit_Fin_Year", "");
							}
							HashMap<String, String> outPutValues = miscellaneousReceiptPaymentService.moveToApproveRecordFromPaymentOldEmosSystem(inputValues);
							String errMsg = outPutValues.get("P_ERROR_MESSAGE");
							if (errMsg != null && !(errMsg.equalsIgnoreCase(""))) {
								setWarningMessage(errMsg);
								log.error("clickOnOKSave  :" + errMsg);
								RequestContext.getCurrentInstance().execute("warningDailog.show();");
								return;
							}

						}

						RequestContext.getCurrentInstance().execute("success.show();");
						return;
					}
				} else {
					setWarningMessage(WarningHandler.showWarningMessage("lbl.totalAmountandNetAmount", sessionManage.getLanguageId()));
					RequestContext.getCurrentInstance().execute("warningDailog.show();");
				}
			} else {
				setWarningMessage(WarningHandler.showWarningMessage("lbl.totalAmountandNetAmount", sessionManage.getLanguageId()));
				RequestContext.getCurrentInstance().execute("warningDailog.show();");
			}
		} catch (Exception e) {
			setWarningMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("warningDailog.show();");
		}
	}

	/*
	 * public void approveRecordFromRefundDenomination(){ savingMap = new
	 * HashMap<String, Object>(); try{ if(getTotalRefundCashEntered()!=null &&
	 * getTotalRefund()!=null){ // Here getTotalRefundCashEntered() ==
	 * getTotalRefund()
	 * if(getTotalRefundCashEntered().compareTo(getTotalRefund())==0){
	 * if(getDocumentCode().equals(new
	 * BigDecimal(Constants.DOCUMENT_CODE_FOR_RECEIVE))){
	 * approveReceiveRecord(getDocumentCode(),"RECEIVE"); }else
	 * if(getDocumentCode().equals(new
	 * BigDecimal(Constants.DOCUMENT_CODE_FOR_PAYMENT))){
	 * approvePaymentRecord(getDocumentCode(),"PAYMENT"); }else{ // not Both }
	 * if(savingMap!=null){
	 * miscellaneousReceiptPaymentService.saveRecords(savingMap);
	 * 
	 * Collect collect =(Collect)savingMap.get("COLLECT"); Payment payment
	 * =(Payment)savingMap.get("PAYMENT");
	 * 
	 * BigDecimal applicationCountryId = null; BigDecimal companyId= null;
	 * BigDecimal documentCode= null; BigDecimal documentFinanceYr= null;
	 * BigDecimal documentNo= null; BigDecimal oldRemitDocFinyr=
	 * getDocumentYear(); BigDecimal oldRemitDocNo= getDocumentNo();
	 * 
	 * 
	 * if(getDocumentCode().equals(new BigDecimal(2))){ List<Collect>
	 * collectionList =
	 * miscellaneousReceiptPaymentService.getCollectionListById(
	 * collect.getCollectionId()); if (collectionList.size() > 0) {
	 * 
	 * applicationCountryId= collectionList.get(0).getApplicationCountryId();
	 * companyId = collectionList.get(0).getFsCompanyMaster().getCompanyId();
	 * documentCode = collectionList.get(0).getDocumentCode(); documentFinanceYr
	 * = collectionList.get(0).getDocumentFinanceYear(); documentNo =
	 * collectionList.get(0).getDocumentNo(); }
	 * 
	 * HashMap<String, String> inputValues=new HashMap<String, String>();
	 * inputValues.put("App_Country_Id", applicationCountryId.toString());
	 * inputValues.put("Company_Id", companyId.toString());
	 * inputValues.put("Doc_Code", documentCode.toString());
	 * inputValues.put("Doc_Finan_year", documentFinanceYr.toString());
	 * inputValues.put("Doc_No", documentNo.toString());
	 * if(getTransferNo()!=null){ inputValues.put("Old_Remit_Doc_No",
	 * getTransferNo().toString()); inputValues.put("Old_Remit_Fin_Year",
	 * oldRemitDocFinyr.toString()); }else{
	 * inputValues.put("Old_Remit_Doc_No","");
	 * inputValues.put("Old_Remit_Fin_Year", ""); }
	 * //miscellaneousReceiptPaymentService
	 * .moveToApproveRecordFromCollectionOldEmosSystem(applicationCountryId,
	 * companyId, documentCode, documentFinanceYr, documentNo, oldRemitDocFinyr,
	 * oldRemitDocNo); HashMap<String,String>
	 * outPutValues=miscellaneousReceiptPaymentService
	 * .moveToApproveRecordFromCollectionOldEmosSystem(inputValues); String
	 * errMsg=outPutValues.get("P_ERROR_MESSAGE"); if(errMsg!=null &&
	 * !(errMsg.equalsIgnoreCase(""))) { setWarningMessage(errMsg);
	 * log.error("clickOnOKSave  :"+ errMsg);
	 * RequestContext.getCurrentInstance().execute("warningDailog.show();");
	 * return; } }else{ List<Payment> paymentList =
	 * miscellaneousReceiptPaymentService
	 * .getPaymentListById(payment.getPaymentId()); if (paymentList.size() > 0)
	 * {
	 * 
	 * applicationCountryId= paymentList.get(0).getCountryId().getCountryId();
	 * companyId = paymentList.get(0).getCompanyId(); documentCode =
	 * paymentList.get(0).getDocCode(); documentFinanceYr =
	 * paymentList.get(0).getDocYear(); documentNo =
	 * paymentList.get(0).getDocNumber(); } HashMap<String, String>
	 * inputValues=new HashMap<String, String>();
	 * inputValues.put("App_Country_Id", applicationCountryId.toString());
	 * inputValues.put("Company_Id", companyId.toString());
	 * inputValues.put("Doc_Code", documentCode.toString());
	 * inputValues.put("Doc_Finan_year", documentFinanceYr.toString());
	 * inputValues.put("Doc_No", documentNo.toString());
	 * if(getTransferNo()!=null){
	 * inputValues.put("Old_Remit_Doc_No",getTransferNo().toString());
	 * inputValues.put("Old_Remit_Fin_Year", oldRemitDocFinyr.toString());
	 * }else{ inputValues.put("Old_Remit_Doc_No","");
	 * inputValues.put("Old_Remit_Fin_Year", ""); } HashMap<String,String>
	 * outPutValues=miscellaneousReceiptPaymentService.
	 * moveToApproveRecordFromPaymentOldEmosSystem(inputValues); String
	 * errMsg=outPutValues.get("P_ERROR_MESSAGE"); if(errMsg!=null &&
	 * !(errMsg.equalsIgnoreCase(""))) { setWarningMessage(errMsg);
	 * log.error("clickOnOKSave  :"+ errMsg);
	 * RequestContext.getCurrentInstance().execute("warningDailog.show();");
	 * return; }
	 * 
	 * }
	 * 
	 * RequestContext.getCurrentInstance().execute("success.show();"); return; }
	 * }else{ setWarningMessage(WarningHandler.showWarningMessage(
	 * "lbl.totalCashAndRefundShouldmatch", sessionManage.getLanguageId()));
	 * RequestContext.getCurrentInstance().execute("warningDailog.show();"); }
	 * }else{ setWarningMessage(WarningHandler.showWarningMessage(
	 * "lbl.totalCashAndRefundShouldmatch", sessionManage.getLanguageId()));
	 * RequestContext.getCurrentInstance().execute("warningDailog.show();"); }
	 * }catch(Exception e){ setWarningMessage(e.getMessage());
	 * RequestContext.getCurrentInstance().execute("warningDailog.show();"); } }
	 */

	// next to denomination page
	public void nextPageToDenomination() {
		if (getTotalRefund() != null) {
			if (getTotalRefund().compareTo(BigDecimal.ZERO) == 1) {
				setRenderDenominationApproveButton(false);
				setRenderDenominationNextButton(true);
			} else {
				setRenderDenominationApproveButton(true);
				setRenderDenominationNextButton(false);
			}
		}
		setRenderPaymentModePanel(false);
		setRenderDenominationDatatabe(true);
		setRenderRefundDenominationDatatabe(false);
		setDirectFromFirstPage(true);
		displayCurrencyDenomination();
		setRenderDenominationApproveButton(true);
		setRenderDenominationNextButton(false);
	}

	public void backFromDenominationPanel() {

		if (getDocumentCode().equals(new BigDecimal(Constants.DOCUMENT_CODE_FOR_RECEIVE))) {
			if (lstData != null && lstData.size() != 0) {
				if (lstRefundData != null && lstRefundData.size() != 0) {
					setRenderRefundDenominationDatatabe(true);
					setRenderDenominationDatatabe(false);
					setRenderPaymentModePanel(false);
					/*
					 * if(getNetAmount() != null && getTotalRefundCashEntered()
					 * != null){
					 * if(getNetAmount().compareTo(getTotalRefundCashEntered
					 * ())>=0){ setTotalRefund(getNetAmount().subtract(
					 * getTotalRefundCashEntered())); }else{
					 * setTotalRefund(getTotalRefundCashEntered
					 * ().subtract(getNetAmount())); } }
					 */
				} else {
					setRenderRefundDenominationDatatabe(false);
					setRenderDenominationDatatabe(false);
					setRenderPaymentModePanel(true);
					setDirectFromFirstPage(false);

					if (getNetAmount() != null && getTotalCashEntered() != null) {
						if (getNetAmount().compareTo(getTotalCashEntered()) >= 0) {
							setTotalRefund(getNetAmount().subtract(getTotalCashEntered()));
						} else {
							setTotalRefund(getTotalCashEntered().subtract(getNetAmount()));
						}
					}
				}
			}
		} else if (getDocumentCode().equals(new BigDecimal(Constants.DOCUMENT_CODE_FOR_PAYMENT))) {
			if (lstData != null && lstData.size() != 0) {
				setRenderRefundDenominationDatatabe(true);
				setRenderDenominationDatatabe(false);
				setRenderPaymentModePanel(false);
				if (getNetAmount() != null && getTotalRefundCashEntered() != null) {
					if (getNetAmount().compareTo(getTotalRefundCashEntered()) >= 0) {
						setTotalRefund(getNetAmount().subtract(getTotalRefundCashEntered()));
					} else {
						setTotalRefund(getTotalRefundCashEntered().subtract(getNetAmount()));
					}
				}
			} else {
				setRenderRefundDenominationDatatabe(false);
				setRenderDenominationDatatabe(false);
				setRenderPaymentModePanel(true);
				setDirectFromFirstPage(false);

				if (getNetAmount() != null && getTotalCashEntered() != null) {
					if (getNetAmount().compareTo(getTotalCashEntered()) >= 0) {
						setTotalRefund(getNetAmount().subtract(getTotalCashEntered()));
					} else {
						setTotalRefund(getTotalCashEntered().subtract(getNetAmount()));
					}
				}
			}
		} else {
			// not both
		}
	}

	public void backFromRefundDenominationPanel() {

		if (getDocumentCode().equals(new BigDecimal(Constants.DOCUMENT_CODE_FOR_RECEIVE))) {
			if (lstRefundData != null && lstRefundData.size() != 0) {
				if (lstData != null && lstData.size() != 0) {
					setRenderRefundDenominationDatatabe(false);
					setRenderDenominationDatatabe(true);
					setRenderPaymentModePanel(false);
					/*
					 * if(getNetAmount() != null && getTotalRefundCashEntered()
					 * != null){
					 * if(getNetAmount().compareTo(getTotalRefundCashEntered
					 * ())>=0){ setTotalRefund(getNetAmount().subtract(
					 * getTotalRefundCashEntered())); }else{
					 * setTotalRefund(getTotalRefundCashEntered
					 * ().subtract(getNetAmount())); } }
					 */
					lstRefundData.clear();
				} else {
					setRenderRefundDenominationDatatabe(false);
					setRenderDenominationDatatabe(false);
					setRenderPaymentModePanel(true);
					setDirectFromFirstPage(false);
					if (getNetAmount() != null && getTotalCashEntered() != null) {
						if (getNetAmount().compareTo(getTotalCashEntered()) >= 0) {
							setTotalRefund(getNetAmount().subtract(getTotalCashEntered()));
						} else {
							setTotalRefund(getTotalCashEntered().subtract(getNetAmount()));
						}
					}
				}
			}
		} else if (getDocumentCode().equals(new BigDecimal(Constants.DOCUMENT_CODE_FOR_PAYMENT))) {
			if (lstRefundData != null && lstRefundData.size() != 0) {
				setRenderRefundDenominationDatatabe(false);
				setRenderDenominationDatatabe(false);
				setRenderPaymentModePanel(true);
				setDirectFromFirstPage(false);

				if (getNetAmount() != null && getTotalCashEntered() != null) {
					if (getNetAmount().compareTo(getTotalCashEntered()) >= 0) {
						setTotalRefund(getNetAmount().subtract(getTotalCashEntered()));
					} else {
						setTotalRefund(getTotalCashEntered().subtract(getNetAmount()));
					}
				}
			}
		} else {
			// not both
		}

		/*
		 * if(getDirectFromFirstPage()){
		 * setRenderRefundDenominationDatatabe(false);
		 * setRenderDenominationDatatabe(false);
		 * setRenderPaymentModePanel(true); setDirectFromFirstPage(false);
		 * 
		 * if(getNetAmount() != null && getTotalCashEntered() != null){
		 * if(getNetAmount().compareTo(getTotalCashEntered())>=0){
		 * setTotalRefund(getNetAmount().subtract(getTotalCashEntered()));
		 * }else{
		 * setTotalRefund(getTotalCashEntered().subtract(getNetAmount())); } }
		 * 
		 * }else{ setRenderRefundDenominationDatatabe(false);
		 * setRenderDenominationDatatabe(true);
		 * setRenderPaymentModePanel(false); }
		 */

	}

	// check for KNET - OTHERS 10 not allowed
	public boolean checkKNETBanks() {
		boolean checkStatus = false;
		if (getPaymentCode() != null && getPaymentCode().equalsIgnoreCase(Constants.KNETCode)) {
			if (getColBankCode() != null && getColBankCode().equalsIgnoreCase("10")) {
				checkStatus = true;
			}
		}
		return checkStatus;
	}

}
