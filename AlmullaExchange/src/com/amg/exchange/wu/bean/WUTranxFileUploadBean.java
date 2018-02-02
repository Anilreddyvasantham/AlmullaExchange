package com.amg.exchange.wu.bean;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import com.amg.exchange.beneficiary.bean.BeneficiaryCreationBean;
import com.amg.exchange.beneficiary.service.IBeneficaryCreation;
import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.common.service.ISourceOfIncomeService;
import com.amg.exchange.foreigncurrency.bean.ForeignLocalCurrencyDataTable;
import com.amg.exchange.foreigncurrency.model.CurrencyWiseDenomination;
import com.amg.exchange.foreigncurrency.model.ForeignCurrencyAdjustWU;
import com.amg.exchange.foreigncurrency.model.PurposeOfTransaction;
import com.amg.exchange.foreigncurrency.model.SourceOfIncomeDescription;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.foreigncurrency.service.ForeignLocalCurrencyDenominationService;
import com.amg.exchange.foreigncurrency.service.IForeignCurrencyPurchaseService;
import com.amg.exchange.registration.bean.CustomerPersonalInfoBean;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.registration.service.ICustomerRegistrationBranchService;
import com.amg.exchange.remittance.bean.BeneficiaryEditBean;
import com.amg.exchange.remittance.bean.PersonalRemittanceCollectionDataTable;
import com.amg.exchange.remittance.bean.PersonalRemmitanceBeneficaryDataTable;
import com.amg.exchange.remittance.bean.PopulateData;
import com.amg.exchange.remittance.model.AuthicationLimitCheckView;
import com.amg.exchange.remittance.model.BeneficaryAccount;
import com.amg.exchange.remittance.model.BeneficaryContact;
import com.amg.exchange.remittance.model.BeneficaryMaster;
import com.amg.exchange.remittance.model.BeneficaryRelationship;
import com.amg.exchange.remittance.model.BenificiaryListView;
import com.amg.exchange.remittance.model.Relations;
import com.amg.exchange.remittance.model.ViewBeneServiceCurrency;
import com.amg.exchange.remittance.model.ViewStock;
import com.amg.exchange.remittance.service.IPaymentService;
import com.amg.exchange.remittance.service.IPersonalRemittanceService;
import com.amg.exchange.remittance.service.IServiceGroupMasterService;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.ServiceGroupMaster;
import com.amg.exchange.treasury.model.ServiceGroupMasterDesc;
import com.amg.exchange.treasury.service.IBankBranchDetailsService;
import com.amg.exchange.treasury.service.IBankMasterService;
import com.amg.exchange.treasury.service.IBeneCountryServices;
import com.amg.exchange.treasury.service.ServiceCodeMasterService;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.DateUtil;
import com.amg.exchange.util.GetRound;
import com.amg.exchange.util.SessionStateManage;
import com.amg.exchange.util.WarningHandler;
import com.amg.exchange.wu.model.WesternUnionTransfer;
import com.amg.exchange.wu.service.IWesternUnionService;
@Component("wuTranxFileUploadBean")
@Scope("session")
public class WUTranxFileUploadBean<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger(WUTranxFileUploadBean.class);

	// panel 1 beneficiary and customer details
	private UploadedFile file;
	private String senderName;
	private String receiverName;
	private String destinationCountry;
	private String desctinationCurnecy;
	private String mtcNO;
	private BigDecimal netAmount;
	private BigDecimal mtcYear;
	private String sendOrReceive;
	private BigDecimal customerID;
	private BigDecimal idType;
	private String idNo;
	private BigDecimal customerrefno;
	private String customerName;
	private String idTypeName;
	private BigDecimal selectCard;
	private BigDecimal beneficiaryCountryId;
	private BigDecimal operatorID;
	private boolean isWUSend;
	private String errorMessage;
	private boolean booDenoEnable = false;
	private Boolean booRenderBeniListPanel;
	private BigDecimal companyId;
	private BigDecimal sendDocumentNo;
	private BigDecimal receiveDocumentNo;
	private BigDecimal sendDocumentCode;
	private BigDecimal sendCompanyCode;
	private BigDecimal sendDocumentFinanceYear;
	private BigDecimal receiveDocumentCode;
	private BigDecimal receiveCompanyCode;
	private BigDecimal receiveDocumentFinanceYear;
	private boolean booSourceEnable;
	private boolean booPurposeEnable;
	private String sourceOfIncome;
	private String purposeOfTransactions;
	private BigDecimal benifisCountryId;
	private BigDecimal benifisCurrencyId;
	private Boolean renderBeneCountry = false;
	private BigDecimal deleteBeneRelationId;
	private boolean booRenderBenificaryFirstPanel;

	// panel 2 payment details
	private boolean booRenderPaymentDetails;
	private String colCurrency;
	private BigDecimal calNetAmountPaid;
	private String colpaymentmodeName;
	private BigDecimal cashAmount = BigDecimal.ZERO;
	private BigDecimal toalUsedAmount = BigDecimal.ZERO;
	private BigDecimal colamountKWD = BigDecimal.ZERO;
	private BigDecimal denomtotalCashreceived;
	private boolean booRendercashdenomination = false;
	private boolean booRenderCurrencyAdjust = false;
	private BigDecimal collectedAmount;
	private BigDecimal tempCash;
	private BigDecimal totalrefundAmount = BigDecimal.ZERO;
	private String denominationchecking;
	private BigDecimal denomtotalCash;

	// panel 3 refund  details
	private boolean boorefundPaymentDetails;
	private BigDecimal refundAmount;
	private BigDecimal collectedRefundAmount;
	private BigDecimal payRefund;
	private String nextOrSaveButton;
	private String refundORcollect;
	private int txnType;
	private BigDecimal wucheckRecCustomer;
	private boolean booSaveDenomRender;
	private boolean booSaveAll;
	private BigDecimal payPaidAmount;
	private BigDecimal payNetPaidAmount;


	@Autowired
	ICustomerRegistrationBranchService<T> icustomerRegistrationService;
	@Autowired
	IGeneralService<T> generalService;
	@Autowired
	IPersonalRemittanceService iPersonalRemittanceService;
	@Autowired
	IWesternUnionService westernUnionService;
	@Autowired
	IBeneCountryServices<T> ibeneCountryServices;
	@Autowired
	ISourceOfIncomeService sourceofIncomeservice;
	@Autowired
	IBankMasterService bankMasterService;
	@Autowired
	IBankBranchDetailsService bankBranchDetailsService;
	@Autowired
	BeneficiaryEditBean beneficiaryEditBean;
	@Autowired
	IServiceGroupMasterService serviceGroupMasterService;
	@Autowired
	IBeneficaryCreation beneficaryCreation;
	@Autowired
	IForeignCurrencyPurchaseService<T> foreignCurrencyPurchaseService;
	@Autowired
	ApplicationContext context;
	@Autowired
	ServiceCodeMasterService serviceMasterService;
	@Autowired
	IPaymentService ipaymentService;
	@Autowired
	ForeignLocalCurrencyDenominationService<T> foreignLocalCurrencyDenominationService;

	SessionStateManage sessionStateManage = new SessionStateManage();
	private Map<BigDecimal, String> mapcomIdentityType = new HashMap<BigDecimal, String>();
	private List<WesternUnionBeneficaryDataTable> coustomerBeneficaryDTList= new ArrayList<WesternUnionBeneficaryDataTable>();
	private List<CustomerIdProof> customerDetailsList = new ArrayList<CustomerIdProof>();
	private List<WUTranxFileUploadDatatable> lstWUTranxFileUploadDatatable = new CopyOnWriteArrayList<WUTranxFileUploadDatatable>();
	private WesternUnionBeneficaryDataTable selectedValues;
	private List<WesternUnionBeneficaryDataTable> selectedWUBeneList = new ArrayList<WesternUnionBeneficaryDataTable>();
	private List<CountryMasterDesc> lstCountry = new ArrayList<CountryMasterDesc>();
	private List<SourceOfIncomeDescription> lstSourceOfIncomes = new ArrayList<SourceOfIncomeDescription>();
	private List<PurposeOfTransaction> lstPurposeOfTransactions = new ArrayList<PurposeOfTransaction>();
	private List<ViewBeneServiceCurrency> beneServiceCurrencyList = new ArrayList<ViewBeneServiceCurrency>();
	private WesternUnionBeneficaryDataTable selectWesternUnionBeneRec = new WesternUnionBeneficaryDataTable();
	private List<PopulateData> allBeneCountryList = new ArrayList<PopulateData>();
	private CopyOnWriteArrayList<PersonalRemittanceCollectionDataTable> coldatatablevalues = new CopyOnWriteArrayList<PersonalRemittanceCollectionDataTable>();
	private ArrayList<ForeignLocalCurrencyDataTable> lstData = new ArrayList<ForeignLocalCurrencyDataTable>();
	private ArrayList<ForeignLocalCurrencyDataTable> lstRefundData = new ArrayList<ForeignLocalCurrencyDataTable>();
	private ForeignLocalCurrencyDataTable dataTableClear = new ForeignLocalCurrencyDataTable();
	private WUTranxFileUploadDatatable lstSelectedWUTranxFileUpload = new WUTranxFileUploadDatatable();



	// getters and setters

	public BigDecimal getPayPaidAmount() {
		return payPaidAmount;
	}
	public void setPayPaidAmount(BigDecimal payPaidAmount) {
		this.payPaidAmount = payPaidAmount;
	}

	public BigDecimal getPayNetPaidAmount() {
		return payNetPaidAmount;
	}
	public void setPayNetPaidAmount(BigDecimal payNetPaidAmount) {
		this.payNetPaidAmount = payNetPaidAmount;
	}

	public boolean isBooSaveAll() {
		return booSaveAll;
	}
	public void setBooSaveAll(boolean booSaveAll) {
		this.booSaveAll = booSaveAll;
	}

	public Boolean getBooRenderBenificaryFirstPanel() {
		return booRenderBenificaryFirstPanel;
	}
	public void setBooRenderBenificaryFirstPanel(Boolean booRenderBenificaryFirstPanel) {
		this.booRenderBenificaryFirstPanel = booRenderBenificaryFirstPanel;
	}

	public boolean isBooSaveDenomRender() {
		return booSaveDenomRender;
	}
	public void setBooSaveDenomRender(boolean booSaveDenomRender) {
		this.booSaveDenomRender = booSaveDenomRender;
	}

	public BigDecimal getWucheckRecCustomer() {
		return wucheckRecCustomer;
	}
	public void setWucheckRecCustomer(BigDecimal wucheckRecCustomer) {
		this.wucheckRecCustomer = wucheckRecCustomer;
	}

	public int getTxnType() {
		return txnType;
	}
	public void setTxnType(int txnType) {
		this.txnType = txnType;
	}

	public String getNextOrSaveButton() {
		return nextOrSaveButton;
	}
	public void setNextOrSaveButton(String nextOrSaveButton) {
		this.nextOrSaveButton = nextOrSaveButton;
	}

	public String getRefundORcollect() {
		return refundORcollect;
	}
	public void setRefundORcollect(String refundORcollect) {
		this.refundORcollect = refundORcollect;
	}

	public BigDecimal getPayRefund() {
		return payRefund;
	}
	public void setPayRefund(BigDecimal payRefund) {
		this.payRefund = payRefund;
	}

	public boolean isBoorefundPaymentDetails() {
		return boorefundPaymentDetails;
	}
	public void setBoorefundPaymentDetails(boolean boorefundPaymentDetails) {
		this.boorefundPaymentDetails = boorefundPaymentDetails;
	}

	public BigDecimal getCollectedRefundAmount() {
		return collectedRefundAmount;
	}
	public void setCollectedRefundAmount(BigDecimal collectedRefundAmount) {
		this.collectedRefundAmount = collectedRefundAmount;
	}

	public BigDecimal getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	public ForeignLocalCurrencyDataTable getDataTableClear() {
		return dataTableClear;
	}
	public void setDataTableClear(ForeignLocalCurrencyDataTable dataTableClear) {
		this.dataTableClear = dataTableClear;
	}

	public BigDecimal getDenomtotalCash() {
		return denomtotalCash;
	}
	public void setDenomtotalCash(BigDecimal denomtotalCash) {
		this.denomtotalCash = denomtotalCash;
	}

	public ArrayList<ForeignLocalCurrencyDataTable> getLstData() {
		return lstData;
	}
	public void setLstData(ArrayList<ForeignLocalCurrencyDataTable> lstData) {
		this.lstData = lstData;
	}

	public ArrayList<ForeignLocalCurrencyDataTable> getLstRefundData() {
		return lstRefundData;
	}
	public void setLstRefundData(ArrayList<ForeignLocalCurrencyDataTable> lstRefundData) {
		this.lstRefundData = lstRefundData;
	}

	public String getDenominationchecking() {
		return denominationchecking;
	}
	public void setDenominationchecking(String denominationchecking) {
		this.denominationchecking = denominationchecking;
	}

	public BigDecimal getTotalrefundAmount() {
		return totalrefundAmount;
	}
	public void setTotalrefundAmount(BigDecimal totalrefundAmount) {
		this.totalrefundAmount = totalrefundAmount;
	}

	public boolean getBooRendercashdenomination() {
		return booRendercashdenomination;
	}
	public void setBooRendercashdenomination(boolean booRendercashdenomination) {
		this.booRendercashdenomination = booRendercashdenomination;
	}

	public boolean isBooRenderCurrencyAdjust() {
		return booRenderCurrencyAdjust;
	}
	public void setBooRenderCurrencyAdjust(boolean booRenderCurrencyAdjust) {
		this.booRenderCurrencyAdjust = booRenderCurrencyAdjust;
	}

	public BigDecimal getDenomtotalCashreceived() {
		return denomtotalCashreceived;
	}
	public void setDenomtotalCashreceived(BigDecimal denomtotalCashreceived) {
		this.denomtotalCashreceived = denomtotalCashreceived;
	}

	public BigDecimal getColamountKWD() {
		return colamountKWD;
	}
	public void setColamountKWD(BigDecimal colamountKWD) {
		this.colamountKWD = colamountKWD;
	}

	public CopyOnWriteArrayList<PersonalRemittanceCollectionDataTable> getColdatatablevalues() {
		return coldatatablevalues;
	}
	public void setColdatatablevalues(CopyOnWriteArrayList<PersonalRemittanceCollectionDataTable> coldatatablevalues) {
		this.coldatatablevalues = coldatatablevalues;
	}

	public boolean isBooRenderPaymentDetails() {
		return booRenderPaymentDetails;
	}
	public void setBooRenderPaymentDetails(boolean booRenderPaymentDetails) {
		this.booRenderPaymentDetails = booRenderPaymentDetails;
	}

	public String getColCurrency() {
		return colCurrency;
	}
	public void setColCurrency(String colCurrency) {
		this.colCurrency = colCurrency;
	}

	public BigDecimal getCalNetAmountPaid() {
		return calNetAmountPaid;
	}
	public void setCalNetAmountPaid(BigDecimal calNetAmountPaid) {
		this.calNetAmountPaid = calNetAmountPaid;
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

	public BigDecimal getDeleteBeneRelationId() {
		return deleteBeneRelationId;
	}
	public void setDeleteBeneRelationId(BigDecimal deleteBeneRelationId) {
		this.deleteBeneRelationId = deleteBeneRelationId;
	}

	public List<PopulateData> getAllBeneCountryList() {
		return allBeneCountryList;
	}
	public void setAllBeneCountryList(List<PopulateData> allBeneCountryList) {
		this.allBeneCountryList = allBeneCountryList;
	}

	public Boolean getRenderBeneCountry() {
		return renderBeneCountry;
	}
	public void setRenderBeneCountry(Boolean renderBeneCountry) {
		this.renderBeneCountry = renderBeneCountry;
	}

	public List<ViewBeneServiceCurrency> getBeneServiceCurrencyList() {
		return beneServiceCurrencyList;
	}
	public void setBeneServiceCurrencyList(List<ViewBeneServiceCurrency> beneServiceCurrencyList) {
		this.beneServiceCurrencyList = iPersonalRemittanceService.getViewBeneCurrency(getBenifisCountryId());
	}

	public BigDecimal getBenifisCountryId() {
		return benifisCountryId;
	}
	public void setBenifisCountryId(BigDecimal benifisCountryId) {
		this.benifisCountryId = benifisCountryId;
	}

	public BigDecimal getBenifisCurrencyId() {
		return benifisCurrencyId;
	}
	public void setBenifisCurrencyId(BigDecimal benifisCurrencyId) {
		this.benifisCurrencyId = benifisCurrencyId;
	}

	public WesternUnionBeneficaryDataTable getSelectWesternUnionBeneRec() {
		return selectWesternUnionBeneRec;
	}
	public void setSelectWesternUnionBeneRec(WesternUnionBeneficaryDataTable selectWesternUnionBeneRec) {
		this.selectWesternUnionBeneRec = selectWesternUnionBeneRec;
	}

	public List<CountryMasterDesc> getLstCountry() {
		return lstCountry;
	}
	public void setLstCountry(List<CountryMasterDesc> lstCountry) {
		this.lstCountry = lstCountry;
	}

	public BigDecimal getCustomerrefno() {
		return customerrefno;
	}
	public void setCustomerrefno(BigDecimal customerrefno) {
		this.customerrefno = customerrefno;
	}

	public List<SourceOfIncomeDescription> getLstSourceOfIncomes() {
		return lstSourceOfIncomes;
	}
	public void setLstSourceOfIncomes(List<SourceOfIncomeDescription> lstSourceOfIncomes) {
		this.lstSourceOfIncomes = lstSourceOfIncomes;
	}

	public List<PurposeOfTransaction> getLstPurposeOfTransactions() {
		return lstPurposeOfTransactions;
	}
	public void setLstPurposeOfTransactions(List<PurposeOfTransaction> lstPurposeOfTransactions) {
		this.lstPurposeOfTransactions = lstPurposeOfTransactions;
	}

	public boolean isBooSourceEnable() {
		return booSourceEnable;
	}
	public void setBooSourceEnable(boolean booSourceEnable) {
		this.booSourceEnable = booSourceEnable;
	}

	public boolean isBooPurposeEnable() {
		return booPurposeEnable;
	}
	public void setBooPurposeEnable(boolean booPurposeEnable) {
		this.booPurposeEnable = booPurposeEnable;
	}

	public String getSourceOfIncome() {
		return sourceOfIncome;
	}
	public void setSourceOfIncome(String sourceOfIncome) {
		this.sourceOfIncome = sourceOfIncome;
	}

	public String getPurposeOfTransactions() {
		return purposeOfTransactions;
	}
	public void setPurposeOfTransactions(String purposeOfTransactions) {
		this.purposeOfTransactions = purposeOfTransactions;
	}

	public BigDecimal getSendDocumentNo() {
		return sendDocumentNo;
	}
	public void setSendDocumentNo(BigDecimal sendDocumentNo) {
		this.sendDocumentNo = sendDocumentNo;
	}

	public BigDecimal getReceiveDocumentNo() {
		return receiveDocumentNo;
	}
	public void setReceiveDocumentNo(BigDecimal receiveDocumentNo) {
		this.receiveDocumentNo = receiveDocumentNo;
	}

	public BigDecimal getSendDocumentCode() {
		return sendDocumentCode;
	}
	public void setSendDocumentCode(BigDecimal sendDocumentCode) {
		this.sendDocumentCode = sendDocumentCode;
	}

	public BigDecimal getSendCompanyCode() {
		return sendCompanyCode;
	}
	public void setSendCompanyCode(BigDecimal sendCompanyCode) {
		this.sendCompanyCode = sendCompanyCode;
	}

	public BigDecimal getSendDocumentFinanceYear() {
		return sendDocumentFinanceYear;
	}
	public void setSendDocumentFinanceYear(BigDecimal sendDocumentFinanceYear) {
		this.sendDocumentFinanceYear = sendDocumentFinanceYear;
	}

	public BigDecimal getReceiveDocumentCode() {
		return receiveDocumentCode;
	}
	public void setReceiveDocumentCode(BigDecimal receiveDocumentCode) {
		this.receiveDocumentCode = receiveDocumentCode;
	}

	public BigDecimal getReceiveCompanyCode() {
		return receiveCompanyCode;
	}
	public void setReceiveCompanyCode(BigDecimal receiveCompanyCode) {
		this.receiveCompanyCode = receiveCompanyCode;
	}

	public BigDecimal getReceiveDocumentFinanceYear() {
		return receiveDocumentFinanceYear;
	}
	public void setReceiveDocumentFinanceYear(BigDecimal receiveDocumentFinanceYear) {
		this.receiveDocumentFinanceYear = receiveDocumentFinanceYear;
	}

	public BigDecimal getCompanyId() {
		return companyId;
	}
	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}

	public List<CustomerIdProof> getCustomerDetailsList() {
		return customerDetailsList;
	}
	public void setCustomerDetailsList(List<CustomerIdProof> customerDetailsList) {
		this.customerDetailsList = customerDetailsList;
	}

	public BigDecimal getOperatorID() {
		return operatorID;
	}
	public void setOperatorID(BigDecimal operatorID) {
		this.operatorID = operatorID;
	}

	public BigDecimal getBeneficiaryCountryId() {
		return beneficiaryCountryId;
	}
	public void setBeneficiaryCountryId(BigDecimal beneficiaryCountryId) {
		this.beneficiaryCountryId = beneficiaryCountryId;
	}

	public List<WesternUnionBeneficaryDataTable> getCoustomerBeneficaryDTList() {
		return coustomerBeneficaryDTList;
	}
	public void setCoustomerBeneficaryDTList(List<WesternUnionBeneficaryDataTable> coustomerBeneficaryDTList) {
		this.coustomerBeneficaryDTList = coustomerBeneficaryDTList;
	}

	public Boolean getBooRenderBeniListPanel() {
		return booRenderBeniListPanel;
	}
	public void setBooRenderBeniListPanel(Boolean booRenderBeniListPanel) {
		this.booRenderBeniListPanel = booRenderBeniListPanel;
	}

	public BigDecimal getSelectCard() {
		return selectCard;
	}
	public void setSelectCard(BigDecimal selectCard) {
		this.selectCard = selectCard;
	}

	public Map<BigDecimal, String> getMapcomIdentityType() {
		return mapcomIdentityType;
	}
	public void setMapcomIdentityType(Map<BigDecimal, String> mapcomIdentityType) {
		this.mapcomIdentityType = mapcomIdentityType;
	}

	public List<WUTranxFileUploadDatatable> getLstWUTranxFileUploadDatatable() {
		return lstWUTranxFileUploadDatatable;
	}
	public void setLstWUTranxFileUploadDatatable(List<WUTranxFileUploadDatatable> lstWUTranxFileUploadDatatable) {
		this.lstWUTranxFileUploadDatatable = lstWUTranxFileUploadDatatable;
	}

	public UploadedFile getFile() {
		return file;
	}
	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getDestinationCountry() {
		return destinationCountry;
	}
	public void setDestinationCountry(String destinationCountry) {
		this.destinationCountry = destinationCountry;
	}

	public String getDesctinationCurnecy() {
		return desctinationCurnecy;
	}
	public void setDesctinationCurnecy(String desctinationCurnecy) {
		this.desctinationCurnecy = desctinationCurnecy;
	}

	public String getMtcNO() {
		return mtcNO;
	}
	public void setMtcNO(String mtcNO) {
		this.mtcNO = mtcNO;
	}

	public BigDecimal getNetAmount() {
		return netAmount;
	}
	public void setNetAmount(BigDecimal netAmount) {
		this.netAmount = netAmount;
	}

	public BigDecimal getMtcYear() {
		return mtcYear;
	}
	public void setMtcYear(BigDecimal mtcYear) {
		this.mtcYear = mtcYear;
	}

	public BigDecimal getCollectedAmount() {
		return collectedAmount;
	}
	public void setCollectedAmount(BigDecimal collectedAmount) {
		this.collectedAmount = collectedAmount;
	}

	public String getSendOrReceive() {
		return sendOrReceive;
	}
	public void setSendOrReceive(String sendOrReceive) {
		this.sendOrReceive = sendOrReceive;
	}

	public BigDecimal getCustomerID() {
		return customerID;
	}
	public void setCustomerID(BigDecimal customerID) {
		this.customerID = customerID;
	}

	public BigDecimal getIdType() {
		return idType;
	}
	public void setIdType(BigDecimal idType) {
		this.idType = idType;
	}

	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getIdTypeName() {
		return idTypeName;
	}
	public void setIdTypeName(String idTypeName) {
		this.idTypeName = idTypeName;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public boolean getIsWUSend() {
		return isWUSend;
	}
	public void setIsWUSend(boolean isWUSend) {
		this.isWUSend = isWUSend;
	}

	public boolean isBooDenoEnable() {
		return booDenoEnable;
	}
	public void setBooDenoEnable(boolean booDenoEnable) {
		this.booDenoEnable = booDenoEnable;
	}

	public BigDecimal getToalUsedAmount() {
		return toalUsedAmount;
	}
	public void setToalUsedAmount(BigDecimal toalUsedAmount) {
		this.toalUsedAmount = toalUsedAmount;
	}

	public BigDecimal getTempCash() {
		return tempCash;
	}
	public void setTempCash(BigDecimal tempCash) {
		this.tempCash = tempCash;
	}

	public WUTranxFileUploadDatatable getLstSelectedWUTranxFileUpload() {
		return lstSelectedWUTranxFileUpload;
	}
	public void setLstSelectedWUTranxFileUpload(WUTranxFileUploadDatatable lstSelectedWUTranxFileUpload) {
		this.lstSelectedWUTranxFileUpload = lstSelectedWUTranxFileUpload;
	}
	
	// checking cashier option in Fs_Employee is Y or N
	public boolean checkEmployeeAllowedOrNot(){
		boolean empStatus = Boolean.FALSE;
		List<Employee> checkEmployee = generalService.getEmployeeDetail(sessionStateManage.getEmployeeId());
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

	// page navigation
	public void pageNavigation()
	{
		boolean cashierOptionStatus = checkEmployeeAllowedOrNot();
		if(cashierOptionStatus){
			try {
				clearDetails();
				fetchUserFinancialYear();
				setOperatorID(sessionStateManage.getWUUsername());
				FacesContext.getCurrentInstance().getExternalContext().redirect("../wu/wutranxfileupload.xhtml");
			} catch (IOException e) {
				setErrorMessage(e.getMessage());
				RequestContext.getCurrentInstance().execute("errorPage.show();");
			}
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "You are not authorized to collect amount. Please request IT for permission.", ""));
		}
	}

	// upload 
	public void upload() throws IOException {
		try{
			if(file != null) {
				String line = "";
				String cvsSplitBy = ",";

				InputStream is=file.getInputstream();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);

				int lineNoCount=0;
				List<String> mainList= new ArrayList<String>();
				List<WUTranxFileUploadDatatable> lstWUTranxFileUploadDatatable= new ArrayList<WUTranxFileUploadDatatable>();
				while ((line = br.readLine()) != null) {

					// use comma as separator
					String[] csvData = line.split(cvsSplitBy);
					if(lineNoCount>2)
					{

						System.out.println(line);
						if(csvData.length==0)
						{
							break;
						}
						if(lineNoCount==3)
						{
							ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(csvData));
						}else
						{
							WUTranxFileUploadDatatable wuTranxFileUploadDt =mappingValuesToListObject(csvData);
							if(wuTranxFileUploadDt!=null)
							{
								lstWUTranxFileUploadDatatable.add(wuTranxFileUploadDt);
							}
						}

					}
					lineNoCount++;

				}
				setLstWUTranxFileUploadDatatable(lstWUTranxFileUploadDatatable);
				System.out.println(mainList);
				FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
				FacesContext.getCurrentInstance().addMessage(null, message);
			}
		}catch(Exception e)
		{
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("errorPage.show();");
		}

	}

	// reading csv File
	public void readCSVfile(FileUploadEvent event)
	{
		//String csvFileDir = Constants.WU_FILE_UPLOAD_PATH;
		//String csvFileName=Constants.WU_FILE_NAME;
		//String csvFile=csvFileDir+"/"+csvFileName;
		
		if(getOperatorID() != null && getOperatorID().compareTo(BigDecimal.ZERO) != 0){
			file = event.getFile();
			file.getFileName();
			BufferedReader br = null;
			String line = "";
			String cvsSplitBy = ",";

			try {
				int lineNoCount=0;
				List<WUTranxFileUploadDatatable> lstWUTranxFileUploadDatatable= new ArrayList<WUTranxFileUploadDatatable>();
				//br = new BufferedReader(new FileReader(csvFile));
				InputStream is=file.getInputstream();
				InputStreamReader isr = new InputStreamReader(is);
				br = new BufferedReader(isr);
				while ((line = br.readLine()) != null) {

					// use comma as separator
					String[] csvData = line.split(cvsSplitBy);
					if(lineNoCount>2)
					{

						//System.out.println(line);
						if(csvData.length==0)
						{
							//break;
						}
						if(lineNoCount==3)
						{
							ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(csvData));
						}else
						{
							WUTranxFileUploadDatatable wuTranxFileUploadDt = mappingValuesToListObject(csvData);
							if(wuTranxFileUploadDt != null)
							{
								lstWUTranxFileUploadDatatable.add(wuTranxFileUploadDt);
							}
						}
					}
					lineNoCount++;
				}

				// checking any record available in ex_wu_moneyTransfer
				//List<WesternUnionTransfer> lstwuMoneyTransfer = westernUnionService.getWUTransactionDenomination(sessionStateManage.getCountryBranchCode().toPlainString(), sessionStateManage.getWUUsername());

				List<WUTranxFileUploadDatatable> lstWUTranx = new ArrayList<WUTranxFileUploadDatatable>();
				if(lstWUTranxFileUploadDatatable.size() != 0){
					for (WUTranxFileUploadDatatable wuTranxFileUploadDatatable : lstWUTranxFileUploadDatatable) {
						if(wuTranxFileUploadDatatable.getOpID() != null && !wuTranxFileUploadDatatable.getOpID().equalsIgnoreCase("") && !wuTranxFileUploadDatatable.getOpID().equalsIgnoreCase("Op. ID")){
							BigDecimal operationId = new BigDecimal(wuTranxFileUploadDatatable.getOpID());
							if(operationId != null && operationId.compareTo(sessionStateManage.getWUUsername())==0){
								lstWUTranx.add(wuTranxFileUploadDatatable);
							}
						}
					}

					if(lstWUTranx != null && lstWUTranx.size() != 0){

						// checking any record available in ex_wu_moneyTransfer
						List<WUTranxFileUploadDatatable> lstwuMoneyTransfer = westernUnionService.checkWuMoneyTransferMTCNO(lstWUTranx,getMtcYear());
						if(lstwuMoneyTransfer != null && lstwuMoneyTransfer.size() != 0){
							setLstWUTranxFileUploadDatatable(lstwuMoneyTransfer);
						}else{
							setLstWUTranxFileUploadDatatable(null);
							setErrorMessage("No Records Found");
							RequestContext.getCurrentInstance().execute("errorPage.show();");
						}
					}else{
						setLstWUTranxFileUploadDatatable(null);
						setErrorMessage("No Records Found");
						RequestContext.getCurrentInstance().execute("errorPage.show();");
					}


				}else{
					setErrorMessage("No Records Found");
					RequestContext.getCurrentInstance().execute("errorPage.show();");
				}

			} catch (FileNotFoundException e) {
				setErrorMessage(e.getMessage());
				RequestContext.getCurrentInstance().execute("errorPage.show();");
			} catch (IOException e) {
				setErrorMessage(e.getMessage());
				RequestContext.getCurrentInstance().execute("errorPage.show();");
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						setErrorMessage(e.getMessage());
						RequestContext.getCurrentInstance().execute("errorPage.show();");
					}
				}
			}
		}else{
			setErrorMessage("Operation Id Not Available. Please Contact Help Desk");
			RequestContext.getCurrentInstance().execute("errorPage.show();");
		}
		
	}

	// mapping to data table
	private WUTranxFileUploadDatatable mappingValuesToListObject(String[] csvData)
	{
		WUTranxFileUploadDatatable wuTranxFileUploadDt =null;
		try{

			if(csvData!=null && csvData.length>0)
			{
				int csvLength = csvData.length;
				
				for (int i = 0; i < csvLength; i++) {
					
					wuTranxFileUploadDt = new WUTranxFileUploadDatatable();

					if(csvLength > 0 && csvData[0] != null && !csvData[0].equalsIgnoreCase("")){
						wuTranxFileUploadDt.setOriginatingCountryCode(csvData[0].replace("\"", ""));
					}

					if(csvLength > 1 && csvData[1] != null && !csvData[1].equalsIgnoreCase("")){
						wuTranxFileUploadDt.setoOriginatingCurrencyCode(csvData[1].replace("\"", ""));
					}

					if(csvLength > 2 && csvData[2] != null && !csvData[2].equalsIgnoreCase("")){
						wuTranxFileUploadDt.setTerminalID(csvData[2].replace("\"", ""));
					}

					if(csvLength > 3 && csvData[3] != null && !csvData[3].equalsIgnoreCase("")){
						wuTranxFileUploadDt.setOpID(csvData[3].replace("\"", ""));
					}

					if(csvLength > 4 && csvData[4] != null && !csvData[4].equalsIgnoreCase("")){
						wuTranxFileUploadDt.setSupervopID(csvData[4].replace("\"", ""));
					}

					if(csvLength > 5 && csvData[5] != null && !csvData[5].equalsIgnoreCase("")){
						wuTranxFileUploadDt.setUserName(csvData[5].replace("\"", ""));
					}

					if(csvLength > 6 && csvData[6] != null && !csvData[6].equalsIgnoreCase("")){
						wuTranxFileUploadDt.setMtcNo(csvData[6].replace("\"", ""));
					}

					if(csvLength > 7 && csvData[7] != null && !csvData[7].equalsIgnoreCase("")){
						wuTranxFileUploadDt.setReceiverName(csvData[7].replace("\"", ""));
					}

					if(csvLength > 8 && csvData[8] != null && !csvData[8].equalsIgnoreCase("")){
						wuTranxFileUploadDt.setSenderName(csvData[8].replace("\"", ""));
					}

					if(csvLength > 9 && csvData[9] != null && !csvData[9].equalsIgnoreCase("")){
						wuTranxFileUploadDt.setDestinationCountryCode(csvData[9].replace("\"", ""));
					}

					if(csvLength > 10 && csvData[10] != null && !csvData[10].equalsIgnoreCase("")){
						wuTranxFileUploadDt.setDestinationCurrencyCode(csvData[10].replace("\"", ""));
					}

					if(csvLength > 11 && csvData[11] != null && !csvData[11].equalsIgnoreCase("")){
						wuTranxFileUploadDt.setType(csvData[11].replace("\"", ""));
					}

					if(csvLength > 12 && csvData[12] != null && !csvData[12].equalsIgnoreCase("")){
						wuTranxFileUploadDt.setCreationDate(csvData[12].replace("\"", ""));
					}

					if(csvLength > 13 && csvData[13] != null && !csvData[13].equalsIgnoreCase("")){
						wuTranxFileUploadDt.setPrincipalAmount(csvData[13].replace("\"", ""));
					}

					if(csvLength > 14 && csvData[14] != null && !csvData[14].equalsIgnoreCase("")){
						wuTranxFileUploadDt.setCharge(csvData[14].replace("\"", ""));
					}

					if(csvLength > 15 && csvData[15] != null && !csvData[15].equalsIgnoreCase("")){
						wuTranxFileUploadDt.setDeliveryCharge(csvData[15].replace("\"", ""));
					}

					if(csvLength > 16 && csvData[16] != null && !csvData[16].equalsIgnoreCase("")){
						wuTranxFileUploadDt.setMessageCharge(csvData[16].replace("\"", ""));
					}

					if(csvLength > 17 && csvData[17] != null && !csvData[17].equalsIgnoreCase("")){
						wuTranxFileUploadDt.setPromotionDiscount(csvData[17].replace("\"", ""));
					}

					if(csvLength > 18 && csvData[18] != null && !csvData[18].equalsIgnoreCase("")){
						wuTranxFileUploadDt.setCollectAmount(csvData[18].replace("\"", ""));
					}

					if(csvLength > 19 && csvData[19] != null && !csvData[19].equalsIgnoreCase("")){
						wuTranxFileUploadDt.setExchangeRate(csvData[19].replace("\"", ""));
					}

					if(csvLength > 20 && csvData[20] != null && !csvData[20].equalsIgnoreCase("")){
						wuTranxFileUploadDt.setExpectedPayoutAmount(csvData[20].replace("\"", ""));
					}

					if(csvLength > 21 && csvData[21] != null && !csvData[21].equalsIgnoreCase("")){
						wuTranxFileUploadDt.setTotalCharges(csvData[21].replace("\"", ""));
					}

					if(csvLength > 22 && csvData[22] != null && !csvData[22].equalsIgnoreCase("")){
						wuTranxFileUploadDt.setTotalTaxes(csvData[22].replace("\"", ""));
					}

					if(csvLength > 23 && csvData[23] != null && !csvData[23].equalsIgnoreCase("")){
						wuTranxFileUploadDt.setPaymentType(csvData[23].replace("\"", ""));
					}

					if(wuTranxFileUploadDt.getType() != null && wuTranxFileUploadDt.getType().equalsIgnoreCase("PAID")){
						wuTranxFileUploadDt.setInorOut(Constants.R);
					}else{
						wuTranxFileUploadDt.setInorOut(Constants.S);
					}
				}

			}
		}catch(Exception e)
		{
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("errorPage.show();");
		}

		return wuTranxFileUploadDt;
	}

	// second screen show details 
	public void showDetails(WUTranxFileUploadDatatable wuTranxFileUploadDt)
	{

		List<WUTranxFileUploadDatatable> lstWuTrnxDt = new ArrayList<WUTranxFileUploadDatatable>();
		lstWuTrnxDt.add(wuTranxFileUploadDt);
		// need to check once again weather already transaction done or not
		List<WUTranxFileUploadDatatable> lstwuMoneyTransfer = westernUnionService.checkWuMoneyTransferMTCNO(lstWuTrnxDt,getMtcYear());
		if(lstwuMoneyTransfer != null && lstwuMoneyTransfer.size() != 0){
			detailsClear();

			setLstSelectedWUTranxFileUpload(wuTranxFileUploadDt);

			setSenderName(wuTranxFileUploadDt.getSenderName());
			setReceiverName(wuTranxFileUploadDt.getReceiverName());
			setDestinationCountry(wuTranxFileUploadDt.getDestinationCountryCode());;
			setDesctinationCurnecy(wuTranxFileUploadDt.getDestinationCurrencyCode());
			setMtcNO(wuTranxFileUploadDt.getMtcNo());
			setSendOrReceive(wuTranxFileUploadDt.getInorOut());

			//setBeneficiaryCountryId(new BigDecimal(94));
			loadIdType() ;
			fetchPurposeOfTransactionsList();
			fetchSourceOfIncomeList();

			// showing beneficiarys and sender details
			setBooRenderBenificaryFirstPanel(true);
			setBooRenderPaymentDetails(false);
			setBooRenderCurrencyAdjust(false);
			setBooRendercashdenomination(false);
			setBoorefundPaymentDetails(false);
			setBooSaveAll(false);
			setBooSaveDenomRender(false);

			if(getSendOrReceive() != null && getSendOrReceive().equalsIgnoreCase(Constants.R)){
				setBooSourceEnable(false);
				setBooPurposeEnable(true);

				if(wuTranxFileUploadDt.getExpectedPayoutAmount() != null){
					setNetAmount(new BigDecimal(wuTranxFileUploadDt.getExpectedPayoutAmount()));
				}
			}else if(getSendOrReceive() != null && getSendOrReceive().equalsIgnoreCase(Constants.S)){
				setBooSourceEnable(true);
				setBooPurposeEnable(true);
				if(wuTranxFileUploadDt.getCollectAmount() != null){
					setNetAmount(new BigDecimal(wuTranxFileUploadDt.getCollectAmount()));
				}
			}else{
				// not Available
				setBooSourceEnable(true);
				setBooPurposeEnable(true);
				if(wuTranxFileUploadDt.getCollectAmount() != null){
					setNetAmount(new BigDecimal(wuTranxFileUploadDt.getCollectAmount()));
				}
			}

			if(getNetAmount() != null){

				BigDecimal authLimit = BigDecimal.ZERO;
				List<AuthicationLimitCheckView> lstAuthLimit = iPersonalRemittanceService.parameterDeTails_AUTH_View(Constants.WU_Percentage_authorization_Type);
				if(lstAuthLimit != null && lstAuthLimit.size() != 0){
					authLimit = lstAuthLimit.get(0).getAuthLimit();
				}

				double tempNetAmount = getNetAmount().doubleValue();
				double netAmountRound = tempNetAmount / authLimit.doubleValue();
				double netDecimalValue = Math.ceil(netAmountRound) * authLimit.doubleValue();

				BigDecimal netRountValue = new BigDecimal(netDecimalValue).setScale(3, BigDecimal.ROUND_HALF_UP);
				setNetAmount(netRountValue);
			}

			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../wu/wutranxfileuploaddetail.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			setErrorMessage("Transaction for this MTCNO Already Done");
			RequestContext.getCurrentInstance().execute("errorPage.show();");
			return;
		}

	}

	// next to payment details
	public void fetchPaymentDetails(){

		// source of Income and Purpose of Trnx
		if(isBooSourceEnable() && isBooPurposeEnable()){
			if(getSourceOfIncome() == null || getPurposeOfTransactions() == null){
				setErrorMessage("Source of Income and Purpose of Transaction is Mandatory");
				RequestContext.getCurrentInstance().execute("errorPage.show();");
				return;
			}
		}else if(isBooPurposeEnable()){
			if(getPurposeOfTransactions() == null){
				setErrorMessage("Purpose of Transaction is Mandatory");
				RequestContext.getCurrentInstance().execute("errorPage.show();");
				return;
			}
		}
		// Id No Check
		if(getIdNo() == null || getIdNo().equalsIgnoreCase("")){
			RequestContext.getCurrentInstance().execute("idNumbernotenter.show();");
			return;
		}

		// Id No Check
		if(getCustomerName() == null || getCustomerName().equalsIgnoreCase("")){
			setErrorMessage("Customer Encounter Problem. Please Contact Back Office");
			RequestContext.getCurrentInstance().execute("errorPage.show();");
			return;
		}

		// check customer and beneficiary is available or not
		if (customerDetailsList != null && customerDetailsList.size() != 0) {
			if(!selectedWUBeneList.isEmpty()){
				setBooRenderBenificaryFirstPanel(false);
				setBooRenderPaymentDetails(true);
				setBooRenderCurrencyAdjust(false);
				setBooRendercashdenomination(false);
				setBoorefundPaymentDetails(false);
				setBooSaveAll(false);
				setBooSaveDenomRender(false);

				String localCurrencyQuote = generalService.getCurrencyQuote(new BigDecimal(sessionStateManage.getCurrencyId()));
				if(localCurrencyQuote != null){
					setColCurrency(localCurrencyQuote);
				}
				setCalNetAmountPaid(getNetAmount());
				setColpaymentmodeName(Constants.CASH);
				setCashAmount(BigDecimal.ZERO);
			}else{
				setErrorMessage("Beneficiary is populated below Please select based on Country else Create New Beneficiary");
				RequestContext.getCurrentInstance().execute("errorPage.show();");
			}
		}else{
			RequestContext.getCurrentInstance().execute("idNotFound.show();");
			return;
		}
	}

	// search operation 
	public void searchClicked()
	{
		try {
			HttpSession session = sessionStateManage.getSession();
			session.setAttribute("request", "wuFileUpload");
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			context.redirect("../search/searchcustomer.xhtml");
		} catch (Exception e) {
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("errorPage.show();");
		}
	}

	// clear
	private void clearDetails()
	{
		setFile(null);
		setLstWUTranxFileUploadDatatable(null);
		setSenderName(null);
		setReceiverName(null);
		setDestinationCountry(null);
		setDesctinationCurnecy(null);
		setMtcNO(null);
		setNetAmount(null);
		setSendOrReceive(null);
		setCustomerID(null);
		setIdType(null);
		setIdNo(null);
		setCustomerName(null);
		setIdTypeName(null);
		setErrorMessage(null);
		setBooRenderBeniListPanel(false);
	}

	// clear customer details
	public void clearData(){
		setFile(null);
		setSenderName(null);
		setReceiverName(null);
		setDestinationCountry(null);
		setDesctinationCurnecy(null);
		setMtcNO(null);
		setNetAmount(null);
		setSendOrReceive(null);
		setCustomerID(null);
		setIdType(null);
		setIdNo(null);
		setCustomerName(null);
		setIdTypeName(null);
		setErrorMessage(null);
		setBooRenderBeniListPanel(false);
	}

	// setting default as civil id
	public void loadIdType() {
		mapcomIdentityType = icustomerRegistrationService.getAllComponentComboDataForCustomer(sessionStateManage.getLanguageId(), "I", "Identity Type");

		BigDecimal idtypeCivilIdnew = generalService.getComponentId(Constants.CIVILID, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId();
		if (idtypeCivilIdnew != null) {
			setSelectCard(idtypeCivilIdnew);
		}
	}

	// clear customer details
	private void detailsClear(){
		setSelectCard(null);
		setBooRenderBeniListPanel(false);
		coustomerBeneficaryDTList.clear();
		setCustomerName(null);
		setIdNo(null);
		setSourceOfIncome(null);
		setPurposeOfTransactions(null);
	}

	// populating beneficiary details
	public void populateCustomerDetailsFromBeneRelation() {

		resetFilters("form1:dataTable");
		if (coustomerBeneficaryDTList != null || !coustomerBeneficaryDTList.isEmpty()) {
			coustomerBeneficaryDTList.clear();
		}

		setBooRenderBeniListPanel(true);
		try {

			if(getCustomerID() != null && getBeneficiaryCountryId() != null){

				List<BenificiaryListView> isCoustomerExist = iPersonalRemittanceService.getBeneficiaryCountryList(getCustomerID(),getBeneficiaryCountryId());

				//WesternUnionBeneficaryDataTable personalRBDataTable = null;
				if (isCoustomerExist.size() > 0) {

					List<BenificiaryListView> duplicateCheck = new ArrayList<BenificiaryListView>();

					for (BenificiaryListView rel : isCoustomerExist) {

						if (duplicateCheck.isEmpty()) {
							duplicateCheck.add(rel);
							addBeneficiaryViewList(rel);
						} else {
							Boolean checkavailable = false;
							for (BenificiaryListView dupl : duplicateCheck) {
								if (dupl.getBeneficaryMasterSeqId().compareTo(rel.getBeneficaryMasterSeqId()) == 0) {
									if (dupl.getCurrencyId().compareTo(rel.getCurrencyId()) == 0) {
										checkavailable = true;
										break;
									} else {
										checkavailable = false;
									}
								} else {
									checkavailable = false;
								}
							}

							if (!checkavailable) {
								duplicateCheck.add(rel);
								addBeneficiaryViewList(rel);
							}

						}

					}
				}
			}

		} catch (Exception e) {
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("errorPage.show();");
		}

	}

	// adding new beneficiary
	public void addBeneficiaryViewList(BenificiaryListView rel) {

		//List<WesternUnionBeneficaryDataTable> coustomerBeneficaryDTList = new ArrayList<WesternUnionBeneficaryDataTable>();

		WesternUnionBeneficaryDataTable personalRBDataTable = new WesternUnionBeneficaryDataTable();

		personalRBDataTable.setApplicationCountryId(rel.getApplicationCountryId());
		personalRBDataTable.setBenificaryName(rel.getBenificaryName());
		personalRBDataTable.setArbenificaryName(rel.getArbenificaryName());
		personalRBDataTable.setBeneficaryMasterSeqId(rel.getBeneficaryMasterSeqId());
		personalRBDataTable.setBeneficiaryAccountSeqId(rel.getBeneficiaryAccountSeqId());
		personalRBDataTable.setBenificaryCountry(rel.getCountryId());
		personalRBDataTable.setBenificaryCountryName(rel.getCountryName());
		personalRBDataTable.setCountryId(rel.getBenificaryCountry());
		personalRBDataTable.setCountryName(rel.getBenificaryBankCountryName());
		personalRBDataTable.setBenificaryStatusId(rel.getBenificaryStatusId());
		personalRBDataTable.setBenificaryStatusName(rel.getBenificaryStatusName());
		personalRBDataTable.setCityName(rel.getCityName());
		personalRBDataTable.setCreatedBy(rel.getCreatedBy());
		personalRBDataTable.setCreatedDate(rel.getCreatedDate());
		personalRBDataTable.setCurrencyId(rel.getCurrencyId());
		personalRBDataTable.setCurrencyName(rel.getCurrencyName());
		personalRBDataTable.setCurrencyQuoteName(rel.getCurrencyQuoteName() == null ? "" : rel.getCurrencyQuoteName());
		personalRBDataTable.setCustomerId(rel.getCustomerId());
		personalRBDataTable.setDateOfBirth(rel.getDateOfBirth());
		personalRBDataTable.setDistrictName(rel.getDistrictName());
		personalRBDataTable.setFiftheName(rel.getFiftheName());
		personalRBDataTable.setFifthNameLocal(rel.getFifthNameLocal());
		personalRBDataTable.setFirstName(rel.getFirstName());
		personalRBDataTable.setFirstNameLocal(rel.getFirstNameLocal());
		personalRBDataTable.setFourthName(rel.getFourthName());
		personalRBDataTable.setFourthNameLocal(rel.getFourthNameLocal());
		personalRBDataTable.setIsActive(rel.getIsActive());
		personalRBDataTable.setLocation(rel.getNationalityName());
		personalRBDataTable.setModifiedBy(rel.getModifiedBy());
		personalRBDataTable.setModifiedDate(rel.getModifiedDate());
		personalRBDataTable.setNationality(rel.getNationality());
		personalRBDataTable.setNationalityName(rel.getNationalityName());
		personalRBDataTable.setNoOfRemittance(rel.getNoOfRemittance());
		personalRBDataTable.setOccupation(rel.getOccupation());
		personalRBDataTable.setRelationShipId(rel.getRelationShipId());
		personalRBDataTable.setRelationShipName(rel.getRelationShipName());
		personalRBDataTable.setBeneficiaryRelationShipSeqId(rel.getBeneficiaryRelationShipSeqId());
		personalRBDataTable.setRemarks(rel.getRemarks());
		personalRBDataTable.setSecondNameLocal(rel.getSecondNameLocal());
		personalRBDataTable.setSecondName(rel.getSecondName());
		personalRBDataTable.setStateName(rel.getStateName());
		personalRBDataTable.setThirdName(rel.getThirdName());
		personalRBDataTable.setThirdNameLocal(rel.getThirdNameLocal());
		personalRBDataTable.setYearOfBirth(rel.getYearOfBirth());
		personalRBDataTable.setCountryId(rel.getBenificaryCountry());
		personalRBDataTable.setCountryName(rel.getBenificaryBankCountryName());
		personalRBDataTable.setBankCode(rel.getBankCode());
		personalRBDataTable.setBankId(rel.getBankId());
		personalRBDataTable.setBankName(rel.getBankName());
		personalRBDataTable.setBankBranchName(rel.getBankBranchName());
		personalRBDataTable.setBranchCode(rel.getBranchCode());
		personalRBDataTable.setBranchId(rel.getBranchId());
		personalRBDataTable.setIdNo(rel.getIdNo());

		coustomerBeneficaryDTList.add(personalRBDataTable);

	}

	//CR Deal Year Changing from User Financial year Table 
	public void fetchUserFinancialYear(){
		try{
			List<UserFinancialYear> finYearList = generalService.getDealYear(new Date());
			if(finYearList!=null){
				setMtcYear(finYearList.get(0).getFinancialYear());
			}
		}catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage(ne.getMessage());
			RequestContext.getCurrentInstance().execute("errorPage.show();");
			return; 
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::" + exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("errorPage.show();");
			return;       
		}
	}

	// resetting data table
	public void resetFilters(String clearDataTable) {
		DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent(clearDataTable);
		if (dataTable != null) {
			dataTable.reset();
		}
	}

	// back to first screen
	public void backToUploadFile()
	{
		try {
			//clearDetails();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../wu/wutranxfileupload.xhtml");
		} catch (IOException e) {
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("errorPage.show();");
		}
	}

	// back to beneficiary details
	public void backToBeneficiaryDetails(){
		setBooRenderBenificaryFirstPanel(true);
		setBooRenderPaymentDetails(false);
		setBooRenderCurrencyAdjust(false);
		setBooRendercashdenomination(false);
		setBoorefundPaymentDetails(false);
		setBooSaveAll(false);
		setBooSaveDenomRender(false);		
		setBooSaveAll(false);
	}

	// redirect to customer
	public void redirectToCustomerFirstPage() throws DOMException, ParseException, ParserConfigurationException, SAXException, IOException{
		CustomerPersonalInfoBean customerPesonel=	(CustomerPersonalInfoBean)context.getBean("customerPersonalInfo");
		customerPesonel.resetValues();

		customerPesonel.setSelectType(Constants.MANUAL);
		customerPesonel.setBooManual(true);
		customerPesonel.setBooIdType(true);
		customerPesonel.setIsFromWesternUnion(true);

		if(getSelectCard() != null){
			customerPesonel.setSelectedIdType(getSelectCard().toString());
			customerPesonel.setIdType(getSelectCard().toString());
		}
		if(getIdNo() != null){
			customerPesonel.setIdNumber(getIdNo().trim());
		}

		customerPesonel.setMobileNoFetch(null);
		customerPesonel.checkingIdWithDBForProcessing();

		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/customerregistrationmain.xhtml");
		} catch (Exception e) {
			log.error("Error in Page Navigation to  CutomerRegistration");
		}

	}

	// to check null
	private String nullCheck(String custname) {
		return custname == null ? "" : custname;
	}

	// clear the customer details
	public void clearCustomerBeneDetails(){
		if (allBeneCountryList != null || !allBeneCountryList.isEmpty()) {
			allBeneCountryList.clear();
		}

		if (coustomerBeneficaryDTList != null || !coustomerBeneficaryDTList.isEmpty()) {
			coustomerBeneficaryDTList.clear();
		}

		if (selectedWUBeneList != null || !selectedWUBeneList.isEmpty()) {
			selectedWUBeneList.clear();
		}

		setCustomerID(null);
		setCustomerName(null);
		setCustomerrefno(null);

		setBooRenderBeniListPanel(false);
	}

	// checking P validate customer
	public boolean checkPValidateCustomer(){
		String userType ="BRANCH";
		boolean error = false;
		log.info("getCustomerNo() :"+getCustomerID()+"\t sessionStateManage.getCountryId():"+sessionStateManage.getCountryId()+"\t sessionStateManage.getUserName() :"+sessionStateManage.getUserName());
		HashMap<String,String> customerValiMessage = iPersonalRemittanceService.getValidateCustomerProcedure(sessionStateManage.getCountryId(), getCustomerID(),sessionStateManage.getUserName(),userType);
		log.info("customerValiMessage :"+customerValiMessage);
		log.info("INDICATOR===="+customerValiMessage.get("INDICATOR"));

		//customerValiMessage.put("ERROR_MESSAGE", null);
		if(customerValiMessage.get("ERROR_MESSAGE")!=null){
			error = false;
			setErrorMessage(customerValiMessage.get("ERROR_MESSAGE"));
			RequestContext.getCurrentInstance().execute("expiredCustomer.show();");
		}else if(customerValiMessage!=null && customerValiMessage.get("INDICATOR") !=null){
			setErrorMessage(customerValiMessage.get("ERROR_MESSAGE"));
			if(customerValiMessage.get("INDICATOR").equalsIgnoreCase(Constants.Yes)){
				error = false;
				RequestContext.getCurrentInstance().execute("customerregproceed.show();");
			}else{
				error = true;
			}
		}else{
			error = true;
		}

		return error;
	}

	// first method after go clicked to fetch all customer details
	public void goFromOldSmartCardpanel() {

		// clearing data table
		resetFilters("form1:dataTable");
		clearCustomerBeneDetails();

		//log.info("Entering into goFromOldSmartCardpanel method");
		if (getIdNo() != null && !getIdNo().equalsIgnoreCase("")) {

			if (getSelectCard() != null) {
				customerDetailsList = iPersonalRemittanceService.getCustomerDetailsActiveRec(getIdNo().toUpperCase(), getSelectCard());
				if (customerDetailsList.size() != 0) {
					CustomerIdProof customerDetails = customerDetailsList.get(0);
					setCustomerID(customerDetails.getFsCustomer().getCustomerId());
					Boolean popError = checkPValidateCustomer();
					if(popError){
						setCustomerName(nullCheck(customerDetails.getFsCustomer().getFirstName()) + " " + nullCheck(customerDetails.getFsCustomer().getMiddleName()) + " " + nullCheck(customerDetails.getFsCustomer().getLastName()));
						setCustomerrefno(customerDetails.getFsCustomer().getCustomerReference());
						//populateCustomerDetailsFromBeneRelation();
						populateBeneFiciaryCountry();
					}
				} else {
					// comparing with civil id
					BigDecimal identityTpeIds = generalService.getComponentId(Constants.CIVILID, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId();
					if (getSelectCard().compareTo(identityTpeIds) != 0) {
						customerDetailsList = iPersonalRemittanceService.getCustomerDetailsActiveRec(getIdNo(), identityTpeIds);
						if (customerDetailsList.size() != 0) {
							CustomerIdProof customerDetails = customerDetailsList.get(0);
							setCustomerID(customerDetails.getFsCustomer().getCustomerId());
							Boolean popError = checkPValidateCustomer();
							if(popError){
								setCustomerName(nullCheck(customerDetails.getFsCustomer().getFirstName()) + " " + nullCheck(customerDetails.getFsCustomer().getMiddleName()) + " " + nullCheck(customerDetails.getFsCustomer().getLastName()));
								setCustomerrefno(customerDetails.getFsCustomer().getCustomerReference());
								//populateCustomerDetailsFromBeneRelation();
								populateBeneFiciaryCountry();
							}
						} else {
							// comparing with civil id new
							BigDecimal idtypeCivilIdnew = generalService.getComponentId(Constants.CIVIL_ID_NEW, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId();
							customerDetailsList = iPersonalRemittanceService.getCustomerDetailsActiveRec(getIdNo(), idtypeCivilIdnew);
							if (customerDetailsList.size() != 0) {
								CustomerIdProof customerDetails = customerDetailsList.get(0);
								setCustomerID(customerDetails.getFsCustomer().getCustomerId());
								Boolean popError = checkPValidateCustomer();
								if(popError){
									setCustomerName(nullCheck(customerDetails.getFsCustomer().getFirstName()) + " " + nullCheck(customerDetails.getFsCustomer().getMiddleName()) + " " + nullCheck(customerDetails.getFsCustomer().getLastName()));
									setCustomerrefno(customerDetails.getFsCustomer().getCustomerReference());
									//populateCustomerDetailsFromBeneRelation();
									populateBeneFiciaryCountry();
								}
							} else {
								RequestContext.getCurrentInstance().execute("idNotFound.show();");
								return;
							}
						}
					} else {
						// comparing with civil id new
						BigDecimal idtypeCivilIdnew = generalService.getComponentId(Constants.CIVIL_ID_NEW, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId();
						customerDetailsList = iPersonalRemittanceService.getCustomerDetailsActiveRec(getIdNo(), idtypeCivilIdnew);
						if (customerDetailsList.size() != 0) {
							CustomerIdProof customerDetails = customerDetailsList.get(0);
							setCustomerID(customerDetails.getFsCustomer().getCustomerId());
							Boolean popError = checkPValidateCustomer();
							if(popError){
								setCustomerName(nullCheck(customerDetails.getFsCustomer().getFirstName()) + " " + nullCheck(customerDetails.getFsCustomer().getMiddleName()) + " " + nullCheck(customerDetails.getFsCustomer().getLastName()));
								setCustomerrefno(customerDetails.getFsCustomer().getCustomerReference());
								//populateCustomerDetailsFromBeneRelation();
								populateBeneFiciaryCountry();
							}
						} else {
							// failed all conditions
							RequestContext.getCurrentInstance().execute("idNotFound.show();");
							return;
						}
					}
				}
			}
		}
	}

	public void onRowSelect(SelectEvent event) {
		setIsWUSend(false);
		if (selectedWUBeneList != null || !selectedWUBeneList.isEmpty()) {
			selectedWUBeneList.clear();
		}

		selectedWUBeneList.add((WesternUnionBeneficaryDataTable) event.getObject());
		setIsWUSend(false);

		checkingBeneAccountDetails();

	}

	public void onRowUnselect(UnselectEvent event) {
		if (selectedWUBeneList != null || !selectedWUBeneList.isEmpty()) {
			selectedWUBeneList.clear();
		}
		this.selectedValues = null;
	}

	// checking weather beneficiary account created or not
	public void checkingBeneAccountDetails() {

		try {

			if (selectedWUBeneList != null && selectedWUBeneList.size() == 1) {
				this.setBooDenoEnable(true);

				System.out.println("selectedWUBeneList select object" + selectedWUBeneList.size());

				WesternUnionBeneficaryDataTable selectedRecord = selectedWUBeneList.get(0);

				List<BeneficaryAccount> lstBene = westernUnionService.getBeneficaryAccountDetails(selectedRecord.getBeneficaryMasterSeqId(),
						Constants.WU_BANK_CODE, selectedRecord.getCountryId(), selectedRecord.getCurrencyId());

				if (lstBene != null && !lstBene.isEmpty()) {

					if (lstBene != null && lstBene.size() == 1) {

						BeneficaryAccount beneAcc = lstBene.get(0);

						// update if deleted D to activate Y
						List<BeneficaryRelationship> lstBeneRelationShip = ibeneCountryServices.checkBenificaryRelationExist(beneAcc.getBeneficaryMaster().getBeneficaryMasterSeqId(), beneAcc.getBeneficaryAccountSeqId());
						if(lstBeneRelationShip.size() != 0){

							BeneficaryRelationship beneRel = lstBeneRelationShip.get(0);

							BigDecimal beneRelationShipId = beneRel.getBeneficaryRelationshipId();

							if(!beneRel.getIsActive().equalsIgnoreCase(Constants.Yes)){
								String status = Constants.Yes;
								iPersonalRemittanceService.deleteBeneAccountRecord(beneAcc.getBeneficaryAccountSeqId() ,beneRelationShipId,status);
							}
						}

						/*try {
							saveWUTransfer(selectedRecord, beneAcc);
						} catch (IOException e) {
							log.info("exception.getMessage():::::::::::::::::::::::::::::::" + e.getMessage());
							setErrorMessage(e.getMessage());
							RequestContext.getCurrentInstance().execute("errorPage.show();");
							return;
						} catch (InterruptedException e) {
							log.info("exception.getMessage():::::::::::::::::::::::::::::::" + e.getMessage());
							setErrorMessage(e.getMessage());
							RequestContext.getCurrentInstance().execute("errorPage.show();");
							return;
						} catch (AMGException e) {
							log.info("exception.getMessage():::::::::::::::::::::::::::::::" + e.getMessage());
							setErrorMessage(e.getMessage());
							RequestContext.getCurrentInstance().execute("errorPage.show();");
							return;
						}*/
					} else {
						setErrorMessage("Beneficiary Account Not Available");
						RequestContext.getCurrentInstance().execute("errorPage.show();");
						return;
					}
				} else {
					// BeneficaryAccount beneficiaryAccRecord =
					// saveBeneficiaryAccountDetails(selectedRecord);
					BeneficaryAccount beneficiaryAccRecord = saveBeneficiaryAccountDetails(selectedRecord, selectedRecord.getCountryId(),selectedRecord.getCurrencyId());
					if (beneficiaryAccRecord != null) {
						/*try {
							saveWUTransfer(selectedRecord, beneficiaryAccRecord);
						} catch (IOException e) {
							log.info("exception.getMessage():::::::::::::::::::::::::::::::" + e.getMessage());
							setErrorMessage(e.getMessage());
							RequestContext.getCurrentInstance().execute("errorPage.show();");
							return;
						} catch (InterruptedException e) {
							log.info("exception.getMessage():::::::::::::::::::::::::::::::" + e.getMessage());
							setErrorMessage(e.getMessage());
							RequestContext.getCurrentInstance().execute("errorPage.show();");
							return;
						} catch (AMGException e) {
							log.info("exception.getMessage():::::::::::::::::::::::::::::::" + e.getMessage());
							setErrorMessage(e.getMessage());
							RequestContext.getCurrentInstance().execute("errorPage.show();");
							return;
						}*/
					}else{
						setErrorMessage("Beneficiary Account Not Available");
						RequestContext.getCurrentInstance().execute("errorPage.show();");
						return;
					}
				}

			} else {
				setErrorMessage("Beneficiary not available");
				RequestContext.getCurrentInstance().execute("errorPage.show();");
				return;
			}
		} catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::" + ne.getMessage());
			setErrorMessage("Method Name::saveWUTransfer()");
			RequestContext.getCurrentInstance().execute("errorPage.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::" + exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("errorPage.show();");
			return;
		}

	}

	// Saving record for western union in Beneficiary Account
	public BeneficaryAccount saveBeneficiaryAccountDetails(WesternUnionBeneficaryDataTable selectedRecord, BigDecimal bankCountryId,
			BigDecimal bankCurrencyId) {

		BigDecimal dbankId = null;
		BigDecimal dbankBranchId = null;
		BeneficaryAccount beneficaryAccount = null;

		try {
			beneficaryAccount = new BeneficaryAccount();

			CountryMaster countryMaster = new CountryMaster();
			countryMaster.setCountryId(sessionStateManage.getCountryId()); // application
			// country
			beneficaryAccount.setBeneApplicationCountry(countryMaster);

			CountryMaster countryMaster1 = new CountryMaster();
			countryMaster1.setCountryId(bankCountryId); // beneficiary bank
			// country
			beneficaryAccount.setBeneficaryCountry(countryMaster1);

			List<BankMaster> bankMasterdetails = bankMasterService.getBankMasterInfo(Constants.WU_BANK_CODE);

			if (bankMasterdetails != null && bankMasterdetails.size() != 0) {

				BankMaster bankMasterId = bankMasterdetails.get(0);

				if (bankMasterId.getBankId() != null) {
					dbankId = bankMasterId.getBankId(); // setting bankID
					// western union
					BankMaster bankMaster = new BankMaster();
					bankMaster.setBankId(bankMasterId.getBankId()); // western union bank id
					beneficaryAccount.setBank(bankMaster);

					beneficaryAccount.setBankCode(Constants.WU_BANK_CODE); // western union bank code

					List<BankBranch> bankBranchdetails = bankBranchDetailsService.getBranchCodebyBank(bankMasterId.getBankId());

					if (bankBranchdetails != null && bankBranchdetails.size() != 0) {

						BankBranch bankBranchId = bankBranchdetails.get(0);

						if (bankBranchId.getBankBranchId() != null) {
							dbankBranchId = bankBranchId.getBankBranchId();
							BankBranch bankBranch = new BankBranch();
							bankBranch.setBankBranchId(bankBranchId.getBankBranchId()); // western union bank branch id
							beneficaryAccount.setBankBranch(bankBranch);
							beneficaryAccount.setBankBranchCode(bankBranchId.getBranchCode()); // western union bank branch code
						}
					}
				}
			}

			List<ServiceGroupMaster> lstServiceGroup = serviceGroupMasterService.toServiceGroupCodeAllValues(Constants.CashCode);
			if (lstServiceGroup.size() != 0) {
				ServiceGroupMaster serviceGroup = lstServiceGroup.get(0);

				if (serviceGroup.getServiceGroupId() != null) {
					ServiceGroupMaster serviceGroupMaster = new ServiceGroupMaster();
					serviceGroupMaster.setServiceGroupId(serviceGroup.getServiceGroupId()); // cash for western union
					beneficaryAccount.setServicegropupId(serviceGroupMaster);
				}
			}

			CurrencyMaster currencyMaster = new CurrencyMaster();
			currencyMaster.setCurrencyId(bankCurrencyId); // beneficiary country currency western union
			beneficaryAccount.setCurrencyId(currencyMaster);

			beneficaryAccount.setIsActive(Constants.Yes);
			beneficaryAccount.setCreatedBy(sessionStateManage.getUserName());
			beneficaryAccount.setCreatedDate(new Date());

			if (selectedRecord.getBeneficaryMasterSeqId() != null) {
				BeneficaryMaster beneficaryMaster = new BeneficaryMaster();
				beneficaryMaster.setBeneficaryMasterSeqId(selectedRecord.getBeneficaryMasterSeqId());
				beneficaryAccount.setBeneficaryMaster(beneficaryMaster);
			}

			iPersonalRemittanceService.saveBeneficiaryAccount(beneficaryAccount);

			// saving relation with master_id , relation_id and account_id
			BeneficaryRelationship beneficaryRelationship = saveBeneficiaryRelationshipDetails(selectedRecord, beneficaryAccount);

			if (selectedRecord.getBeneficaryMasterSeqId() != null && dbankId != null && dbankBranchId != null
					&& beneficaryAccount.getBeneficaryAccountSeqId() != null && beneficaryAccount.getCurrencyId().getCurrencyId() != null
					&& beneficaryRelationship.getCustomerId().getCustomerId() != null) {
				String errorMessage = beneficaryCreation.getBeneDetailProce(selectedRecord.getBeneficaryMasterSeqId(), dbankId, dbankBranchId,
						beneficaryAccount.getBeneficaryAccountSeqId(), beneficaryAccount.getCurrencyId().getCurrencyId(), beneficaryRelationship
						.getCustomerId().getCustomerId());
				if (errorMessage != null) {
					setErrorMessage("EX_POPULATE_BENE_DT " + errorMessage);
					RequestContext.getCurrentInstance().execute("errorPage.show();");
					return beneficaryAccount;
				}
			} else {
				setErrorMessage("EX_POPULATE_BENE_DT NOT EXECUTED");
				RequestContext.getCurrentInstance().execute("errorPage.show();");
				return beneficaryAccount;
			}

		} catch (Exception e) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::" + e.getMessage());
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("errorPage.show();");
			return beneficaryAccount;
		}

		return beneficaryAccount;
	}

	// saving record for western union in beneficiary relationship
	public BeneficaryRelationship saveBeneficiaryRelationshipDetails(WesternUnionBeneficaryDataTable selectedRecord,
			BeneficaryAccount beneficaryAccount) {

		BeneficaryRelationship beneficaryRelationship = new BeneficaryRelationship();

		try {

			CountryMaster countryMaster = new CountryMaster();
			countryMaster.setCountryId(sessionStateManage.getCountryId());
			beneficaryRelationship.setApplicationCountry(countryMaster);

			if (selectedRecord.getCustomerId() != null) {
				Customer customer = new Customer();
				customer.setCustomerId(selectedRecord.getCustomerId());
				beneficaryRelationship.setCustomerId(customer);
			}

			if (selectedRecord.getRelationShipId() != null) {
				Relations relations = new Relations();
				relations.setRelationsId(selectedRecord.getRelationShipId());
				beneficaryRelationship.setRelations(relations);
			}

			beneficaryRelationship.setIsActive(Constants.Yes);
			beneficaryRelationship.setCreatedBy(sessionStateManage.getUserName());
			beneficaryRelationship.setCreatedDate(new Date());
			beneficaryRelationship.setBeneficaryAccount(beneficaryAccount);

			if (selectedRecord.getBeneficaryMasterSeqId() != null) {
				BeneficaryMaster beneficaryMaster = new BeneficaryMaster();
				beneficaryMaster.setBeneficaryMasterSeqId(selectedRecord.getBeneficaryMasterSeqId());
				beneficaryRelationship.setBeneficaryMaster(beneficaryMaster);
			}

			iPersonalRemittanceService.saveBeneficiaryRelationship(beneficaryRelationship);

		} catch (Exception e) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::" + e.getMessage());
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("errorPage.show();");
		}

		return beneficaryRelationship;
	}

	// Save to Western Union money transfer ( Send prompt)
	public void saveWUTransfer(WesternUnionBeneficaryDataTable selectedRecord) throws IOException, InterruptedException,
	AMGException {

		try {

			WesternUnionTransfer westernUnionTransfer = new WesternUnionTransfer();

			Date accyymm = null;

			try {
				accyymm = DateUtil.getCurrentAccountYearMonth();
			} catch (ParseException e) {
				e.printStackTrace();
			}

			List<CompanyMasterDesc> companyList = generalService.getCompanyList(sessionStateManage.getCompanyId(), sessionStateManage.getLanguageId());
			if (companyList.size() > 0) {
				westernUnionTransfer.setCompanyCode(companyList.get(0).getFsCompanyMaster().getCompanyCode());
				setCompanyId(companyList.get(0).getFsCompanyMaster().getCompanyId());
			}

			westernUnionTransfer.setLocationCode(sessionStateManage.getCountryBranchCode().toString());
			westernUnionTransfer.setWuId(sessionStateManage.getWUUsername());

			// beneficiary Details
			westernUnionTransfer.setReceiverFirstName(nullCheck(selectedRecord.getFirstName()).concat(nullCheck(selectedRecord.getSecondName())));
			westernUnionTransfer.setReceiverLastName((nullCheck(selectedRecord.getThirdName()).concat(nullCheck(selectedRecord.getFourthName())).concat(nullCheck(selectedRecord.getFiftheName()))));


			if(customerDetailsList.size() != 0){
				CustomerIdProof customerData = customerDetailsList.get(0);
				westernUnionTransfer.setSenderFirstName(customerData.getFsCustomer().getFirstName());
				westernUnionTransfer.setSenderLastName(nullCheck(customerData.getFsCustomer().getMiddleName()).concat(" ").concat(nullCheck(customerData.getFsCustomer().getLastName())));
			}

			if(getLstSelectedWUTranxFileUpload() != null){

				if(getLstSelectedWUTranxFileUpload().getExpectedPayoutAmount() != null && !getLstSelectedWUTranxFileUpload().getExpectedPayoutAmount().equalsIgnoreCase("")){
					westernUnionTransfer.setDestinationAmount(new BigDecimal(getLstSelectedWUTranxFileUpload().getExpectedPayoutAmount()));
				}
				if(getLstSelectedWUTranxFileUpload().getCharge() != null && !getLstSelectedWUTranxFileUpload().getCharge().equalsIgnoreCase("")){
					westernUnionTransfer.setFees(new BigDecimal(getLstSelectedWUTranxFileUpload().getCharge()));
				}
				if(getLstSelectedWUTranxFileUpload().getExchangeRate() != null && !getLstSelectedWUTranxFileUpload().getExchangeRate().equalsIgnoreCase("")){
					westernUnionTransfer.setRate(new BigDecimal(getLstSelectedWUTranxFileUpload().getExchangeRate()));
				}
				
				if(getLstSelectedWUTranxFileUpload().getPrincipalAmount() != null && !getLstSelectedWUTranxFileUpload().getPrincipalAmount().equalsIgnoreCase("")){
					
					BigDecimal authLimit = BigDecimal.ZERO;
					List<AuthicationLimitCheckView> lstAuthLimit = iPersonalRemittanceService.parameterDeTails_AUTH_View(Constants.WU_Percentage_authorization_Type);
					if(lstAuthLimit != null && lstAuthLimit.size() != 0){
						authLimit = lstAuthLimit.get(0).getAuthLimit();
					}

					double tempNetAmount = new Double(getLstSelectedWUTranxFileUpload().getPrincipalAmount());
					double netAmountRound = tempNetAmount / authLimit.doubleValue();
					double netDecimalValue = Math.ceil(netAmountRound) * authLimit.doubleValue();

					BigDecimal netRountValue = new BigDecimal(netDecimalValue).setScale(3, BigDecimal.ROUND_HALF_UP);
				
					westernUnionTransfer.setSendAmount(netRountValue);
				}

				westernUnionTransfer.setMtcNo(getLstSelectedWUTranxFileUpload().getMtcNo());

				String mtcno = getLstSelectedWUTranxFileUpload().getMtcNo();
				String mtc1 = mtcno.substring(0, 3);
				String mtc2 = mtcno.substring(3, 6);
				String mtc3 = mtcno.substring(6);
				String wumtcno = mtc1 + "-" + mtc2 + "-" + mtc3;

				westernUnionTransfer.setWuMTCNo(wumtcno);

				westernUnionTransfer.setPaymentMode(getColpaymentmodeName());
				westernUnionTransfer.setProgNo("W_WU_TRN");

				westernUnionTransfer.setMtcYear(getMtcYear());
				westernUnionTransfer.setDocumentDate(new Date());

				westernUnionTransfer.setDestinationCountry(getLstSelectedWUTranxFileUpload().getDestinationCountryCode());
				//westernUnionTransfer.setDestinationPlace();
				westernUnionTransfer.setDestinationCurrency(getLstSelectedWUTranxFileUpload().getDestinationCurrencyCode());

				westernUnionTransfer.setSenderCountry(getLstSelectedWUTranxFileUpload().getOriginatingCountryCode());
				//String currencyName = generalService.getCurrencyName(new BigDecimal(sessionStateManage.getCurrencyId()));
				westernUnionTransfer.setSendCurrency(getLstSelectedWUTranxFileUpload().getoOriginatingCurrencyCode());

			}

			if (accyymm != null) {
				westernUnionTransfer.setAccountYearMonth(accyymm);
			}
			westernUnionTransfer.setDocumentCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_WU));
			westernUnionTransfer.setDocumentDate(new Date());

			westernUnionTransfer.setTerminalId(Constants.TERMINAL_ID);
			System.out.println("WUID=======>" + sessionStateManage.getWUUsername());

			westernUnionTransfer.setWuId(sessionStateManage.getWUUsername());
			westernUnionTransfer.setCreatedDate(new Date());
			westernUnionTransfer.setCreatedBy(sessionStateManage.getUserName());
			westernUnionTransfer.setJavaTransaction(Constants.JAVA_TRANSACTION_YES);
			westernUnionTransfer.setCustomerReference(getCustomerrefno());

			String tokenno = westernUnionService.getNextToken();
			System.out.println("Token No=========>" + tokenno);

			westernUnionTransfer.setDocumentFinanceYear(getMtcYear());

			westernUnionTransfer.setDocumentNo(new BigDecimal(tokenno));
			setSendDocumentNo(westernUnionTransfer.getDocumentNo());
			setSendCompanyCode(westernUnionTransfer.getCompanyCode());
			setSendDocumentFinanceYear(westernUnionTransfer.getDocumentFinanceYear());
			setSendDocumentCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_WU));

			westernUnionTransfer.setInorOut(Constants.WU_INOUT_SEND);

			// System.out.println("exist customer reference==="+customerDetails.getFsCustomer().getCustomerReference());

			westernUnionTransfer.setPurposeId(getPurposeOfTransactions());
			westernUnionTransfer.setSourceOfIncomeId(getSourceOfIncome());

			if (selectedRecord != null) {

				List<BeneficaryRelationship> beneRelList = westernUnionService.fetchBeneficiaryRelationShipForWU(selectedRecord.getBeneficaryMasterSeqId(), Constants.WU_BANK_CODE, getCustomerID());

				System.out.println("customer id" + beneRelList.get(0).getCustomerId().getCustomerId());

				if (beneRelList.size() > 0) {
					System.out.println("sequence" + beneRelList.get(0).getMapSequenceId());
					if (beneRelList.get(0).getMapSequenceId() != null) {
						westernUnionTransfer.setBeneficiarySequence(beneRelList.get(0).getMapSequenceId());

						// Save western Union Money Transfer
						westernUnionService.saveWesternUnionTransfer(westernUnionTransfer);

					} else {
						RequestContext.getCurrentInstance().execute("nomapid.show();");
						return;
					}
				} else {
					RequestContext.getCurrentInstance().execute("nomapid.show();");
					return;
				}
			}

		} catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::" + ne.getMessage());
			setErrorMessage("Method Name::saveWUTransfer()");
			RequestContext.getCurrentInstance().execute("errorPage.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::" + exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("errorPage.show();");
			return;
		}

	}

	// Save to Western Union money transfer ( Receive prompt)
	public void saveWUReceive(WesternUnionBeneficaryDataTable selectedRecord) throws IOException, InterruptedException, AMGException {

		try {
			setBooDenoEnable(true);

			WesternUnionTransfer westernUnionTransfer = new WesternUnionTransfer();
			List<UserFinancialYear> finYearList = generalService.getDealYear(new Date());
			Date accyymm = null;
			try {
				accyymm = DateUtil.getCurrentAccountYearMonth();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			List<CompanyMasterDesc> companyList = generalService
					.getCompanyList(sessionStateManage.getCompanyId(), sessionStateManage.getLanguageId());
			if (companyList.size() > 0) {
				westernUnionTransfer.setCompanyCode(companyList.get(0).getFsCompanyMaster().getCompanyCode());
				setCompanyId(companyList.get(0).getFsCompanyMaster().getCompanyId());
			}

			westernUnionTransfer.setLocationCode(sessionStateManage.getCountryBranchCode().toString());

			if (finYearList.size() > 0) {
				westernUnionTransfer.setDocumentFinanceYear(finYearList.get(0).getFinancialYear());
			}
			if (accyymm != null) {
				westernUnionTransfer.setAccountYearMonth(accyymm);
			}

			westernUnionTransfer.setDocumentCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_WU));
			westernUnionTransfer.setDocumentDate(new Date());

			// beneficiary Details
			westernUnionTransfer.setSenderFirstName(nullCheck(selectedRecord.getFirstName()).concat(nullCheck(selectedRecord.getSecondName())));
			westernUnionTransfer.setSenderLastName((nullCheck(selectedRecord.getThirdName()).concat(nullCheck(selectedRecord.getFourthName())).concat(nullCheck(selectedRecord.getFiftheName()))));


			if(customerDetailsList.size() != 0){
				CustomerIdProof customerData = customerDetailsList.get(0);
				westernUnionTransfer.setReceiverFirstName(customerData.getFsCustomer().getFirstName());
				westernUnionTransfer.setReceiverLastName(nullCheck(customerData.getFsCustomer().getMiddleName()).concat(" ").concat(nullCheck(customerData.getFsCustomer().getLastName())));

				//westernUnionTransfer.setSenderCountry(sessionStateManage.getCountryName());
				//String currencyName = generalService.getCurrencyName(new BigDecimal(sessionStateManage.getCurrencyId()));
				//westernUnionTransfer.setSendCurrency(currencyName);

			}

			if(getLstSelectedWUTranxFileUpload() != null){

				/*if(getLstSelectedWUTranxFileUpload().getExpectedPayoutAmount() != null && !getLstSelectedWUTranxFileUpload().getExpectedPayoutAmount().equalsIgnoreCase("")){
					westernUnionTransfer.setDestinationAmount(new BigDecimal(getLstSelectedWUTranxFileUpload().getExpectedPayoutAmount()));
				}*/
				
				if(getLstSelectedWUTranxFileUpload().getPrincipalAmount() != null && !getLstSelectedWUTranxFileUpload().getPrincipalAmount().equalsIgnoreCase("")){
					westernUnionTransfer.setDestinationAmount(new BigDecimal(getLstSelectedWUTranxFileUpload().getPrincipalAmount()));
				}
				/*if(getLstSelectedWUTranxFileUpload().getCharge() != null && !getLstSelectedWUTranxFileUpload().getCharge().equalsIgnoreCase("")){
					westernUnionTransfer.setFees(new BigDecimal(getLstSelectedWUTranxFileUpload().getCharge()));
				}*/
				if(getLstSelectedWUTranxFileUpload().getExchangeRate() != null && !getLstSelectedWUTranxFileUpload().getExchangeRate().equalsIgnoreCase("")){
					westernUnionTransfer.setRate(new BigDecimal(getLstSelectedWUTranxFileUpload().getExchangeRate()));
				}
				/*if(getLstSelectedWUTranxFileUpload().getPrincipalAmount() != null && !getLstSelectedWUTranxFileUpload().getPrincipalAmount().equalsIgnoreCase("")){
					
					BigDecimal authLimit = BigDecimal.ZERO;
					List<AuthicationLimitCheckView> lstAuthLimit = iPersonalRemittanceService.parameterDeTails_AUTH_View(Constants.WU_Percentage_authorization_Type);
					if(lstAuthLimit != null && lstAuthLimit.size() != 0){
						authLimit = lstAuthLimit.get(0).getAuthLimit();
					}

					double tempNetAmount = new Double(getLstSelectedWUTranxFileUpload().getPrincipalAmount());
					double netAmountRound = tempNetAmount / authLimit.doubleValue();
					double netDecimalValue = Math.ceil(netAmountRound) * authLimit.doubleValue();

					BigDecimal netRountValue = new BigDecimal(netDecimalValue).setScale(3, BigDecimal.ROUND_HALF_UP);
				
					westernUnionTransfer.setSendAmount(netRountValue);
					
				}*/
				
				if(getLstSelectedWUTranxFileUpload().getExpectedPayoutAmount() != null && !getLstSelectedWUTranxFileUpload().getExpectedPayoutAmount().equalsIgnoreCase("")){
					
					BigDecimal authLimit = BigDecimal.ZERO;
					List<AuthicationLimitCheckView> lstAuthLimit = iPersonalRemittanceService.parameterDeTails_AUTH_View(Constants.WU_Percentage_authorization_Type);
					if(lstAuthLimit != null && lstAuthLimit.size() != 0){
						authLimit = lstAuthLimit.get(0).getAuthLimit();
					}

					double tempNetAmount = new Double(getLstSelectedWUTranxFileUpload().getExpectedPayoutAmount());
					double netAmountRound = tempNetAmount / authLimit.doubleValue();
					double netDecimalValue = Math.ceil(netAmountRound) * authLimit.doubleValue();

					BigDecimal netRountValue = new BigDecimal(netDecimalValue).setScale(3, BigDecimal.ROUND_HALF_UP);
				
					westernUnionTransfer.setSendAmount(netRountValue);
					
				}

				westernUnionTransfer.setMtcNo(getLstSelectedWUTranxFileUpload().getMtcNo());

				String mtcno = getLstSelectedWUTranxFileUpload().getMtcNo();
				String mtc1 = mtcno.substring(0, 3);
				String mtc2 = mtcno.substring(3, 6);
				String mtc3 = mtcno.substring(6);
				String wumtcno = mtc1 + "-" + mtc2 + "-" + mtc3;

				westernUnionTransfer.setWuMTCNo(wumtcno);

				westernUnionTransfer.setPaymentMode(getColpaymentmodeName());
				westernUnionTransfer.setProgNo("W_WU_TRN");

				westernUnionTransfer.setDocumentDate(new Date());

				//westernUnionTransfer.setSenderCountry(getLstSelectedWUTranxFileUpload().getDestinationCountryCode());
				//westernUnionTransfer.setDestinationPlace();
				//westernUnionTransfer.setDestinationCountry(getLstSelectedWUTranxFileUpload().getOriginatingCountryCode());

				//String currencyName = generalService.getCurrencyName(new BigDecimal(sessionStateManage.getCurrencyId()));
				//westernUnionTransfer.setDestinationCurrency(currencyName);
				//westernUnionTransfer.setDestinationPlace();

				//westernUnionTransfer.setDestinationCountry(getLstSelectedWUTranxFileUpload().getDestinationCountryCode());
				//westernUnionTransfer.setDestinationCurrency(getLstSelectedWUTranxFileUpload().getDestinationCurrencyCode());

				//westernUnionTransfer.setSenderCountry(getLstSelectedWUTranxFileUpload().getOriginatingCountryCode());
				//westernUnionTransfer.setSendCurrency(getLstSelectedWUTranxFileUpload().getoOriginatingCurrencyCode());
				
				westernUnionTransfer.setDestinationCountry(getLstSelectedWUTranxFileUpload().getOriginatingCountryCode());
				westernUnionTransfer.setDestinationCurrency(getLstSelectedWUTranxFileUpload().getoOriginatingCurrencyCode());

				westernUnionTransfer.setSenderCountry(getLstSelectedWUTranxFileUpload().getDestinationCountryCode());
				westernUnionTransfer.setSendCurrency(getLstSelectedWUTranxFileUpload().getDestinationCurrencyCode());

			}

			westernUnionTransfer.setTerminalId(Constants.TERMINAL_ID);
			westernUnionTransfer.setWuId(sessionStateManage.getWUUsername());
			westernUnionTransfer.setJavaTransaction(Constants.JAVA_TRANSACTION_YES);
			westernUnionTransfer.setInorOut(Constants.WU_INOUT_RECEIVE);
			westernUnionTransfer.setMtcYear(getMtcYear());

			String mtcno = getMtcNO();
			String mtc1 = mtcno.substring(0, 3);
			String mtc2 = mtcno.substring(3, 6);
			String mtc3 = mtcno.substring(6);
			String wumtcno = mtc1 + "-" + mtc2 + "-" + mtc3;

			westernUnionTransfer.setWuMTCNo(wumtcno);
			westernUnionTransfer.setPurposeId(getPurposeOfTransactions());
			westernUnionTransfer.setSourceOfIncomeId(getSourceOfIncome());

			// checking Western union Receive condition
			HashMap<String, String> lstWuTransfer = new HashMap<String, String>();
			lstWuTransfer.put("Company_Code", westernUnionTransfer.getCompanyCode().toPlainString());
			lstWuTransfer.put("Documnent_Code", Constants.DOCUMENT_CODE_FOR_WU);
			lstWuTransfer.put("Documnent_Year", westernUnionTransfer.getDocumentFinanceYear().toPlainString());
			lstWuTransfer.put("MTC_Number", wumtcno);
			lstWuTransfer.put("INorOUT", Constants.WU_INOUT_RECEIVE);
			List<WesternUnionTransfer> lstWesternUnion = westernUnionService.fetchWesternUnionTransfer(lstWuTransfer);

			//Each time token is generated.
			westernUnionTransfer.setCreatedDate(new Date());
			westernUnionTransfer.setCreatedBy(sessionStateManage.getUserName());
			// Generate Token no
			String tokenno = westernUnionService.getNextToken();
			westernUnionTransfer.setDocumentNo(new BigDecimal(tokenno));
			westernUnionTransfer.setCustomerReference(getCustomerrefno());
			// Each time token generation code end

			setReceiveCompanyCode(westernUnionTransfer.getCompanyCode());
			setReceiveDocumentCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_WU));
			setReceiveDocumentFinanceYear(westernUnionTransfer.getDocumentFinanceYear());
			setReceiveDocumentNo(westernUnionTransfer.getDocumentNo());

			// Save western Union Money Transfer
			//westernUnionService.saveWesternUnionTransfer(westernUnionTransfer);

			if (selectedRecord != null) {

				List<BeneficaryRelationship> beneRelList = westernUnionService.fetchBeneficiaryRelationShipForWU(selectedRecord.getBeneficaryMasterSeqId(), Constants.WU_BANK_CODE, getCustomerID());

				System.out.println("customer id" + beneRelList.get(0).getCustomerId().getCustomerId());

				if (beneRelList.size() > 0) {
					System.out.println("sequence" + beneRelList.get(0).getMapSequenceId());
					if (beneRelList.get(0).getMapSequenceId() != null) {
						westernUnionTransfer.setBeneficiarySequence(beneRelList.get(0).getMapSequenceId());

						// Save western Union Money Transfer
						westernUnionService.saveWesternUnionTransfer(westernUnionTransfer);

					} else {
						RequestContext.getCurrentInstance().execute("nomapid.show();");
						return;
					}
				} else {
					RequestContext.getCurrentInstance().execute("nomapid.show();");
					return;
				}
			}

		} catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::" + ne.getMessage());
			setErrorMessage("Method Name::saveWUReceive()");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::" + exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	// Source Of Income List
	public void fetchSourceOfIncomeList() {
		lstSourceOfIncomes.clear();
		try {
			List<SourceOfIncomeDescription> sourceOfIncomesLst = foreignCurrencyPurchaseService.getSourceofIncome(sessionStateManage.getLanguageId());
			if(sourceOfIncomesLst != null && sourceOfIncomesLst.size() != 0){
				lstSourceOfIncomes.addAll(sourceOfIncomesLst);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Purpose of Transaction List
	public void fetchPurposeOfTransactionsList() {
		lstPurposeOfTransactions.clear();
		try {
			List<PurposeOfTransaction> purposeOfTransactionsLst = foreignCurrencyPurchaseService.getAllPurposeOfTransaction();
			if(purposeOfTransactionsLst != null && purposeOfTransactionsLst.size() != 0){
				lstPurposeOfTransactions.addAll(purposeOfTransactionsLst);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Call beneficiary creation from western union
	public void gotToNewBenificaryCreation() {

		try {

			if (customerDetailsList.size() != 0) {
				if(getSelectCard() != null && getIdNo() != null && getCustomerID() != null){
					BeneficiaryCreationBean objectBene = (BeneficiaryCreationBean) context.getBean("beneficiaryCreationBean");

					objectBene.setBooenableAgentPanel(false);
					objectBene.setBooEnableBankChannelPanel(false);

					objectBene.setIdNumber(getIdNo());
					objectBene.setSelectCard(getSelectCard());
					objectBene.setCustomerNo(getCustomerID());
					objectBene.setCustomerrefno(getCustomerrefno());
					objectBene.setCustomerFullName(getCustomerName());
					objectBene.setBooBenefiPersonalRemit(true);

					objectBene.renderWesternUnionUploadNavigation();

					FacesContext.getCurrentInstance().getExternalContext().redirect("../beneficary/beneficiaryCreation.xhtml");
				}else{
					RequestContext.getCurrentInstance().execute("idNumbernotenter.show();");
					return;
				}
			} else {
				RequestContext.getCurrentInstance().execute("idNotFound.show();");
				return;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void differentCurrencyDetails(WesternUnionBeneficaryDataTable beneAccountCurrencyDetails) {
		System.out.println(beneAccountCurrencyDetails);
		setSelectWesternUnionBeneRec(beneAccountCurrencyDetails);
		setBenifisCountryId(null);
		setBenifisCurrencyId(null);
		if (beneServiceCurrencyList != null || !beneServiceCurrencyList.isEmpty()) {
			beneServiceCurrencyList.clear();
		}
		lstCountry = generalService.getCountryList(sessionStateManage.getLanguageId()); // beneficiary bank country list
		RequestContext.getCurrentInstance().execute("beneCurrencyPanel.show();");
	}

	public void benServiceCurrencyListDetails(AjaxBehaviorEvent event) {
		setBenifisCurrencyId(null);
		if (getBenifisCountryId() != null) {
			beneServiceCurrencyList = iPersonalRemittanceService.getViewBeneCurrency(getBenifisCountryId()); // beneficiary bank country currency list
		}
	}

	public void differentCurrencyData() {
		try {
			BigDecimal dbankId = null;
			BigDecimal dbankBranchId = null;
			String dbankCode = null;
			BigDecimal dbankBranchCode = null;

			WesternUnionBeneficaryDataTable westerUnionSelected = getSelectWesternUnionBeneRec();
			// checking bank_country , bank_id , bank_branch_id , currency_id
			// with master_seq_id
			if (getBenifisCountryId() != null && getBenifisCurrencyId() != null && getSelectWesternUnionBeneRec() != null) {

				List<BankMaster> bankMasterdetails = bankMasterService.getBankMasterInfo(Constants.WU_BANK_CODE);

				if (bankMasterdetails != null && bankMasterdetails.size() != 0) {

					BankMaster bankMasterId = bankMasterdetails.get(0);

					if (bankMasterId.getBankId() != null) {
						dbankId = bankMasterId.getBankId(); // setting bankID
						dbankCode = Constants.WU_BANK_CODE; // western union
						// bank code

						List<BankBranch> bankBranchdetails = bankBranchDetailsService.getBranchCodebyBank(bankMasterId.getBankId());

						if (bankBranchdetails != null && bankBranchdetails.size() != 0) {

							BankBranch bankBranchId = bankBranchdetails.get(0);

							if (bankBranchId.getBankBranchId() != null) {
								dbankBranchId = bankBranchId.getBankBranchId();
								dbankBranchCode = bankBranchId.getBranchCode(); // western union bank branch code
							}
						}
					}
				}

				if (getBenifisCountryId() != null && dbankId != null && dbankBranchId != null && getBenifisCurrencyId() != null
						&& westerUnionSelected.getBeneficaryMasterSeqId() != null) {
					boolean checkWesternUnionCountryCurrency = westernUnionService.checkBeneficaryAccountDetailsForWUnion(getBenifisCountryId(),
							dbankId, dbankBranchId, getBenifisCurrencyId(), westerUnionSelected.getBeneficaryMasterSeqId());
					if (checkWesternUnionCountryCurrency) {
						// record already Available
						setErrorMessage("BanK Country and Currency already Available for Beneficary");
						RequestContext.getCurrentInstance().execute("errorPage.show();");
						return;
					} else {
						// insert to database
						saveBeneficiaryAccountDetails(getSelectWesternUnionBeneRec(), getBenifisCountryId(), getBenifisCurrencyId());
						populateBeneFiciaryCountry();
						setBeneficiaryCountryId(getBenifisCountryId());
						// populateCustomerDetailsFromBeneRelation();
						setBenifisCountryId(null);
						setBenifisCurrencyId(null);
						if (getSelectWesternUnionBeneRec() != null) {
							setSelectWesternUnionBeneRec(null);
						}
					}
				} else {
					setErrorMessage("BanK Country and Currency and Selected Beneficiary are Null");
					RequestContext.getCurrentInstance().execute("errorPage.show();");
					return;
				}

			} else {
				// details from Dialogue box not correctly updated.
			}
		} catch (Exception e) {
			setBenifisCountryId(null);
			setBenifisCurrencyId(null);
			if (getSelectWesternUnionBeneRec() != null) {
				setSelectWesternUnionBeneRec(null);
			}
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("errorPage.show();");
			return;
		}

	}

	public void populateBeneFiciaryCountry() {
		try {
			if (allBeneCountryList != null || !allBeneCountryList.isEmpty()) {
				allBeneCountryList.clear();
			}

			if (coustomerBeneficaryDTList != null || !coustomerBeneficaryDTList.isEmpty()) {
				coustomerBeneficaryDTList.clear();
			}

			if (selectedWUBeneList != null || !selectedWUBeneList.isEmpty()) {
				selectedWUBeneList.clear();
			}

			List<PopulateData> countryList = iPersonalRemittanceService.getBeneficaryList(getCustomerID());

			if (countryList != null && countryList.size() > 1) {
				allBeneCountryList.addAll(countryList);
				populateCustomerDetailsFromBeneRelation();
			} else if (countryList != null && countryList.size() == 1) {
				allBeneCountryList.addAll(countryList);
				PopulateData populateData = countryList.get(0);
				setBeneficiaryCountryId(populateData.getPopulateId());
				populateCustomerDetailsFromBeneRelation();
			}else{
				populateCustomerDetailsFromBeneRelation();
			}

			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../wu/wutranxfileuploaddetail.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("errorPage.show();");
		}

	}

	public void editWUBenificary(WesternUnionBeneficaryDataTable datatabledetails) {

		try{

			log.info("Exit into edit method ");

			// checking beneficiary Account for that master and currency and western union code
			List<BeneficaryAccount> beneAccDt = beneficaryCreation.getBeneAccountDetails(datatabledetails.getBeneficaryMasterSeqId(), datatabledetails.getCurrencyId(), Constants.WU_BANK_CODE);

			// only western union records to deleted not the banking channel or cash product
			if(beneAccDt != null && beneAccDt.size() != 0){
				BigDecimal beneAccountSeqId = beneAccDt.get(0).getBeneficaryAccountSeqId();

				// update if deleted D to activate Y
				List<BeneficaryRelationship> lstBeneRelationShip = ibeneCountryServices.checkBenificaryRelationExist(datatabledetails.getBeneficaryMasterSeqId(), beneAccountSeqId);
				if(lstBeneRelationShip.size() != 0){

					BeneficaryRelationship beneRel = lstBeneRelationShip.get(0);

					BigDecimal beneRelationShipId = beneRel.getBeneficaryRelationshipId();

					if(!beneRel.getIsActive().equalsIgnoreCase(Constants.Yes)){
						String status = Constants.Yes;
						iPersonalRemittanceService.deleteBeneAccountRecord(beneAccountSeqId ,beneRelationShipId,status);
					}
				}

				PersonalRemmitanceBeneficaryDataTable pdatatabledetails = populateCustomerBeneDetails(datatabledetails,beneAccountSeqId);
				if(pdatatabledetails != null){
					fetchAllRecordForNonTrnx(pdatatabledetails);
				}

			}else {
				setErrorMessage("Beneficiary Doesn't Belong to WESTERN UNION");
				RequestContext.getCurrentInstance().execute("errorPage.show();");
			}

		}catch (Exception e) {
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("errorPage.show();");
		}

	}

	// full edit
	public void fetchAllRecordForNonTrnx(PersonalRemmitanceBeneficaryDataTable datatabledetails){
		try {
			BeneficiaryCreationBean objectBene = (BeneficiaryCreationBean)context.getBean("beneficiaryCreationBean");
			//objectBene.setCoustomerBeneficaryDTList(coustomerBeneficaryDTList);
			objectBene.setBooRenderBeneficaryCreation(false);
			objectBene.setBooRenderWesterUnion(false);
			objectBene.setBooRenderWesterUnionUpload(true);
			objectBene.setDataTableBeneObj(datatabledetails);
			objectBene.renderBeneficiaryFullEditNavigation();

			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			context.redirect("../beneficary/firsttimebeneficaryedit.xhtml");
		} catch (IOException e) {
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("errorPage.show();");
		}
	}

	// Second Method to Populate Records which is Approved all condition
	public PersonalRemmitanceBeneficaryDataTable populateCustomerBeneDetails(WesternUnionBeneficaryDataTable datatabledetails,BigDecimal beneAccountSeqId) {
		resetFilters("form1:dataTable");

		log.info("Entering into populateCustomerBeneDetails method ");

		PersonalRemmitanceBeneficaryDataTable personalRBDataTable = new PersonalRemmitanceBeneficaryDataTable();

		try{

			log.info( "=====================getCustomerNo()"+getCustomerID()+" : getBeneficiaryCountryId() : "+getBeneficiaryCountryId());
			List<BenificiaryListView> isCoustomerExist = iPersonalRemittanceService.getBeneficiaryDtFromView(datatabledetails.getCustomerId(),datatabledetails.getCountryId(),datatabledetails.getBeneficaryMasterSeqId(), datatabledetails.getCurrencyId(), Constants.WU_BANK_CODE,beneAccountSeqId);
			log.info( "====================="+isCoustomerExist.size());

			if (isCoustomerExist.size() == 1) {

				BenificiaryListView rel =  isCoustomerExist.get(0);

				personalRBDataTable = new PersonalRemmitanceBeneficaryDataTable();

				personalRBDataTable.setAccountStatus(rel.getAccountStatus());
				personalRBDataTable.setAge(rel.getAge());
				personalRBDataTable.setApplicationCountryId(rel.getApplicationCountryId());
				personalRBDataTable.setArbenificaryName(rel.getArbenificaryName());
				personalRBDataTable.setBankAccountNumber(rel.getBankAccountNumber());
				personalRBDataTable.setBankBranchName(rel.getBankBranchName());
				personalRBDataTable.setBankCode(rel.getBankCode());
				personalRBDataTable.setBankId(rel.getBankId());
				personalRBDataTable.setBankName(rel.getBankName());
				personalRBDataTable.setBeneficaryMasterSeqId(rel.getBeneficaryMasterSeqId());
				personalRBDataTable.setBeneficiaryAccountSeqId(rel.getBeneficiaryAccountSeqId());
				personalRBDataTable.setBeneficiaryRelationShipSeqId(rel.getBeneficiaryRelationShipSeqId());
				personalRBDataTable.setBenificaryCountry(rel.getCountryId()); // bene country Id
				personalRBDataTable.setBenificaryCountryName(rel.getCountryName()); // bene country Name
				personalRBDataTable.setBenificaryName(rel.getBenificaryName());
				personalRBDataTable.setBenificaryStatusId(rel.getBenificaryStatusId());
				personalRBDataTable.setBenificaryStatusName(rel.getBenificaryStatusName());
				personalRBDataTable.setBooDisabled(rel.getBankAccountNumber()!=null ? true: false);
				personalRBDataTable.setBranchCode(rel.getBranchCode());
				personalRBDataTable.setBranchId(rel.getBranchId());
				personalRBDataTable.setCityName(rel.getCityName());
				personalRBDataTable.setCountryId(rel.getBenificaryCountry()); // bank country Id
				personalRBDataTable.setCountryName(rel.getBenificaryBankCountryName()); // bank country Name
				personalRBDataTable.setCreatedBy(rel.getCreatedBy());
				personalRBDataTable.setCreatedDate(rel.getCreatedDate());
				personalRBDataTable.setCurrencyId(rel.getCurrencyId());
				personalRBDataTable.setCurrencyName(rel.getCurrencyName());
				personalRBDataTable.setCurrencyQuoteName(rel.getCurrencyQuoteName()==null?"":rel.getCurrencyQuoteName());
				personalRBDataTable.setCustomerId(rel.getCustomerId());
				personalRBDataTable.setDateOfBirth(rel.getDateOfBirth());
				personalRBDataTable.setDistrictName(rel.getDistrictName());
				personalRBDataTable.setFiftheName(rel.getFiftheName());
				personalRBDataTable.setFifthNameLocal(rel.getFifthNameLocal());
				personalRBDataTable.setFirstName(rel.getFirstName());
				personalRBDataTable.setFirstNameLocal(rel.getFirstNameLocal());
				personalRBDataTable.setFourthName(rel.getFourthName());
				personalRBDataTable.setFourthNameLocal(rel.getFourthNameLocal());
				personalRBDataTable.setIsActive(rel.getIsActive());
				personalRBDataTable.setLocation(rel.getNationalityName());
				personalRBDataTable.setModifiedBy(rel.getModifiedBy());
				personalRBDataTable.setModifiedDate(rel.getModifiedDate());
				personalRBDataTable.setNationality(rel.getNationality());
				personalRBDataTable.setNationalityName(rel.getNationalityName());
				personalRBDataTable.setNoOfRemittance(rel.getNoOfRemittance());
				personalRBDataTable.setOccupation(rel.getOccupation());
				personalRBDataTable.setRelationShipId(rel.getRelationShipId());
				personalRBDataTable.setRelationShipName(rel.getRelationShipName());
				personalRBDataTable.setRemarks(rel.getRemarks());
				personalRBDataTable.setSecondNameLocal(rel.getSecondNameLocal());
				personalRBDataTable.setSecondName(rel.getSecondName());
				personalRBDataTable.setServiceGroupCode(rel.getServiceGroupCode());
				personalRBDataTable.setServiceGroupId(rel.getServiceGroupId());
				personalRBDataTable.setServiceProvider(rel.getServiceProvider());
				personalRBDataTable.setStateName(rel.getStateName());
				personalRBDataTable.setMapSequenceId(rel.getMapSequenceId());
				//Bene Details Added @koti 10/08/2016
				StringBuffer strAdd=new StringBuffer();
				if(rel.getStateName() != null){
					strAdd.append(rel.getStateName());
				}
				if(rel.getDistrictName() != null){
					strAdd.append(","+rel.getDistrictName());
				}
				if (rel.getCityName() != null) {
					strAdd.append(","+rel.getCityName());
				}
				if(strAdd != null){
					personalRBDataTable.setBeneAddressDetails(strAdd.toString());
				}

				personalRBDataTable.setBeneHouseNo(rel.getBuildingNo());
				personalRBDataTable.setBeneFlatNo(rel.getFlatNo());
				personalRBDataTable.setBeneStreetNo(rel.getStreetNo());

				//For swift  beneficiary 
				personalRBDataTable.setSwiftBic(rel.getSwiftBic());
				if(rel.getLastEmosRemittance()==null){
					personalRBDataTable.setTransactinStatus(Constants.NO);
				}else{
					personalRBDataTable.setTransactinStatus(Constants.YES);
				}

				List<ServiceGroupMasterDesc> lstServiceGroupMasterDesc = serviceMasterService.LocalServiceGroupDescription(new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"),rel.getServiceGroupId());
				if (lstServiceGroupMasterDesc.size() > 0) {
					personalRBDataTable.setServiceGroupName(lstServiceGroupMasterDesc.get(0).getServiceGroupDesc());
				}

				List<BeneficaryContact> telePhone = beneficaryCreation.getTelephoneDetails(rel.getBeneficaryMasterSeqId());

				System.out.println("telePhone :"+telePhone.toString());
				if (telePhone != null && telePhone.size() != 0) {
					String telNumber = null;
					if(telePhone.size() == 1){
						if(telePhone.get(0).getTelephoneNumber()!=null){
							telNumber = telePhone.get(0).getTelephoneNumber();
						}else if(telePhone.get(0).getMobileNumber() !=null){
							telNumber = telePhone.get(0).getMobileNumber().toPlainString();
						}else{
							telNumber = null;
						}
						personalRBDataTable.setTelphoneNum(telNumber);
						personalRBDataTable.setTelphoneCode(telePhone.get(0).getCountryTelCode());
						personalRBDataTable.setBeneficiaryContactSeqId(telePhone.get(0).getBeneficaryTelephoneSeqId());
					}else{
						BeneficaryContact beneficaryContact = telePhone.get(0);
						if(beneficaryContact.getTelephoneNumber()!=null){
							telNumber = beneficaryContact.getTelephoneNumber();
						}else if(beneficaryContact.getMobileNumber()!=null){
							telNumber = beneficaryContact.getMobileNumber().toPlainString();
						}else{
							telNumber = null;
						}
						personalRBDataTable.setTelphoneNum(telNumber);
						personalRBDataTable.setTelphoneCode(beneficaryContact.getCountryTelCode());
						personalRBDataTable.setBeneficiaryContactSeqId(beneficaryContact.getBeneficaryTelephoneSeqId());
					}
				}

				personalRBDataTable.setThirdName(rel.getThirdName());
				personalRBDataTable.setThirdNameLocal(rel.getThirdNameLocal());
				personalRBDataTable.setYearOfBirth(rel.getYearOfBirth());

				personalRBDataTable.setStateId(rel.getStateId());
				personalRBDataTable.setDistrictId(rel.getDistrictId());
				personalRBDataTable.setCityId(rel.getCityId());
				personalRBDataTable.setBankAccountTypeId(rel.getBankAccountTypeId());


			} else if(isCoustomerExist.size() > 1){
				// more than one record
				setErrorMessage("More Records Available for One Beneficiary");
				RequestContext.getCurrentInstance().execute("errorPage.show();");
			}else{
				// no records
				setErrorMessage("No Records Available for One Beneficiary");
				RequestContext.getCurrentInstance().execute("errorPage.show();");
			}

		}catch(Exception e){
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("errorPage.show();");
		}

		log.info("Exit into populateCustomerDetailsFromBeneRelation method ");
		return personalRBDataTable;
	}

	public void deleteBeneRelationIsActive(WesternUnionBeneficaryDataTable deletebene){

		setDeleteBeneRelationId(null);

		List<BeneficaryAccount> beneAccDt = beneficaryCreation.getBeneAccountDetails(deletebene.getBeneficaryMasterSeqId(), deletebene.getCurrencyId(), Constants.WU_BANK_CODE);

		// only western union records to deleted not the banking channel or cash product
		if(beneAccDt != null && beneAccDt.size() != 0){
			BigDecimal beneAccountSeqId = beneAccDt.get(0).getBeneficaryAccountSeqId();

			List<BeneficaryRelationship> lstBeneRelationShip = ibeneCountryServices.checkBenificaryRelationExist(deletebene.getBeneficaryMasterSeqId(), beneAccountSeqId);
			if(lstBeneRelationShip.size() == 1){

				setDeleteBeneRelationId(lstBeneRelationShip.get(0).getBeneficaryRelationshipId());
				setErrorMessage("Do you want to delete Western Union Beneficiary Account?");
				RequestContext.getCurrentInstance().execute("deleteBeneAcc.show();");
			}

			setDeleteBeneRelationId(lstBeneRelationShip.get(0).getBeneficaryRelationshipId());

		}else {
			setErrorMessage("Beneficiary Doesn't Belong to WESTERN UNION");
			RequestContext.getCurrentInstance().execute("errorPage.show();");
		}

	}

	public void deleteBeneficiaryAccount(){
		if(getDeleteBeneRelationId() != null){
			BigDecimal deletebeneRel = getDeleteBeneRelationId();
			if(deletebeneRel != null){
				String status = Constants.D;
				//iPersonalRemittanceService.deleteBeneAccountRecord(deletebene.getBeneficiaryAccountSeqId() ,deletebene.getBeneficiaryRelationShipSeqId(),status);
				beneficaryCreation.deleteBeneRelationRecord(deletebeneRel,status);
				if(getBeneficiaryCountryId()!=null){
					//populateCustomerDetailsFromBeneRelation();
					populateBeneFiciaryCountry();
				}
			}
		}
	}

	public void exitWU() {
		try {
			String roleNameDesc=iPersonalRemittanceService.toFetchRoleName(new BigDecimal(sessionStateManage.getRoleId()));
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// redirect to list of wu file upload
	public void fetchWuFileList(){

		try{
			if(lstWUTranxFileUploadDatatable != null && lstWUTranxFileUploadDatatable.size() != 0){
				if(getLstSelectedWUTranxFileUpload() != null){
					for (WUTranxFileUploadDatatable lstAllWu : lstWUTranxFileUploadDatatable) {
						if(lstAllWu.getMtcNo() != null && lstAllWu.getMtcNo().equalsIgnoreCase(getLstSelectedWUTranxFileUpload().getMtcNo())){
							if(lstAllWu.getInorOut() != null && lstAllWu.getInorOut().equalsIgnoreCase(getLstSelectedWUTranxFileUpload().getInorOut())){
								lstWUTranxFileUploadDatatable.remove(lstAllWu);
								break;
							}
						}
					}
				}else{
					setLstWUTranxFileUploadDatatable(null);
				}
			}else{
				setLstWUTranxFileUploadDatatable(null);
			}

			clearData();
			fetchUserFinancialYear();
			setOperatorID(sessionStateManage.getWUUsername());
			FacesContext.getCurrentInstance().getExternalContext().redirect("../wu/wutranxfileupload.xhtml");
		}catch(Exception e){

		}


	}


	// payment details

	// calculation of cash while entering
	public void checkcashcollection() {
		setToalUsedAmount(getCashAmount());
	}

	// Render denomination data
	public void renderingDenominationDT() {
		//if (coldatatablevalues.size() != 0) {
		BigDecimal paymentModeCashId = ipaymentService.fetchPaymodeMasterId(Constants.CASHNAME,
				new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"));
		if (paymentModeCashId != null) {
			setDenomtotalCashreceived(getCashAmount());
			setBooRendercashdenomination(true);
			setCollectedAmount(GetRound.roundBigDecimal(BigDecimal.ZERO,
					foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
		}
		//BigDecimal totalCardCash = BigDecimal.ZERO;
		//BigDecimal totalCreditCardCash = BigDecimal.ZERO;
		/*for (PersonalRemittanceCollectionDataTable collectionlst : coldatatablevalues) {
				if (collectionlst.getColpaymentmodeCode().equalsIgnoreCase(Constants.KNETCode)) {
					totalCardCash = totalCardCash.add(collectionlst.getColAmountDT());
				} else if (collectionlst.getColpaymentmodeCode().equalsIgnoreCase(Constants.ChequeCode)) {
					totalCreditCardCash = totalCreditCardCash.add(collectionlst.getColAmountDT());
				}
			}
			setKnetValue(GetRound.roundBigDecimal(totalCardCash,
					foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
			setChequeValue(GetRound.roundBigDecimal(totalCreditCardCash,
					foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));*/
		//}
	}

	// Render payment details
	public void nextpaneltoPaymentDetails() {

		System.out.println("getToalUsedAmount()" + getToalUsedAmount());
		System.out.println("getCalNetAmountPaid()" + getCalNetAmountPaid());

		if(getToalUsedAmount() != null && getCalNetAmountPaid() != null && getCashAmount() != null && getCashAmount().compareTo(BigDecimal.ZERO) != 0){
			if (getToalUsedAmount().compareTo(getCalNetAmountPaid()) >= 0) {
				// continue
			} else {
				setErrorMessage(WarningHandler.showWarningMessage("lbl.netamountnotmatchingtototalamount", sessionStateManage.getLanguageId()));
				RequestContext.getCurrentInstance().execute("errorPage.show();");
				return;
			}
		}else {
			setErrorMessage("Amount Not Avaiable");
			RequestContext.getCurrentInstance().execute("errorPage.show();");
			return;
		}

		setBooRenderBenificaryFirstPanel(false);
		setBooRenderCurrencyAdjust(false);
		setBooRendercashdenomination(false);
		setBoorefundPaymentDetails(false);
		setBooSaveAll(false);
		setBooSaveDenomRender(false);	

		lstData.clear();
		lstRefundData.clear();
		if(getSendOrReceive() != null && getSendOrReceive().equalsIgnoreCase(Constants.R)){
			if(getCalNetAmountPaid() != null && getCashAmount() != null){
				/*if(getCalNetAmountPaid().compareTo(getCashAmount())==0){
					refundDenominationData();
					setBooRendercashdenomination(false);
					setBoorefundPaymentDetails(true);
					setRefundORcollect("Amount to Collect");
					setNextOrSaveButton(Constants.Save);
				}else{
					setErrorMessage("Net Amount and Cash to be paid Must be equal");
					RequestContext.getCurrentInstance().execute("errorPage.show();");
					return;
				}*/

				refundDenominationData();
				setBooRendercashdenomination(false);
				setBoorefundPaymentDetails(true);
				setRefundORcollect("Amount to Collect");
				//setNextOrSaveButton(Constants.Save);
			}
		}else if(getSendOrReceive() != null && getSendOrReceive().equalsIgnoreCase(Constants.S)){
			denaMinLstData();
			setBooRendercashdenomination(true);
			setBoorefundPaymentDetails(false);
			setRefundORcollect("Amount to Refund");
		}else{
			setRefundORcollect("Amount to Refund");
			setErrorMessage("Unable to Identify Send or Receive");
			RequestContext.getCurrentInstance().execute("errorPage.show();");
			return;
		}

		setBooRenderPaymentDetails(false);
		setBooRenderCurrencyAdjust(true);		
		setBooSaveAll(true);
		setTotalrefundAmount(getToalUsedAmount().subtract(getCalNetAmountPaid()));
		//setRefundORcollect("Amount to Refund");
		if (getTotalrefundAmount().compareTo(BigDecimal.ZERO) == 0) {
			setNextOrSaveButton(Constants.Save);
		} else {
			setNextOrSaveButton(Constants.Next);
		}

	}

	public void backFromFinal() {
		setCashAmount(BigDecimal.ZERO);
		setBooRenderBenificaryFirstPanel(false);
		setBooRenderCurrencyAdjust(false);
		setBooRendercashdenomination(false);
		setBoorefundPaymentDetails(false);
		setBooRenderPaymentDetails(true);
		setBooSaveDenomRender(false);
		setBooSaveAll(false);
		setBooDenoEnable(false);
	}

	// Denomination List Data
	private void denaMinLstData() {
		try {
			lstData.clear();
			ArrayList<ForeignLocalCurrencyDataTable> localLstData = new ArrayList<ForeignLocalCurrencyDataTable>();
			/*
			 * Checking that it's first time or not, first time list size will
			 * be 0
			 */
			// double sAmount = 0;
			localLstData.clear();
			if (localLstData.size() == 0) {

				List<CurrencyWiseDenomination> currencyWiseDenolst = iPersonalRemittanceService.getCurrencyDenominations(new BigDecimal(
						sessionStateManage.getCurrencyId()), sessionStateManage.getCountryId());

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
			setDenomtotalCash(GetRound.roundBigDecimal(new BigDecimal(totalSum),
					foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
			// setCollectedAmount(getDenomtotalCash());
			setPayNetPaidAmount(getCalNetAmountPaid());
			setPayPaidAmount(GetRound.roundBigDecimal(getToalUsedAmount(),
					foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
			setPayRefund(getPayPaidAmount().subtract(getPayNetPaidAmount()));
			if (getPayRefund().compareTo(BigDecimal.ZERO) == 0) {
				setNextOrSaveButton(Constants.Save);
				// setSavetimeReportEmailCheck(true);
			} else {
				setNextOrSaveButton(Constants.Next);
				// setSavetimeReportEmailCheck(false);
			}
			setLstData(localLstData); // Adding denomication here
			if (localLstData.size() != 0) {
				setDenominationchecking(Constants.DenominationAvailable);
			} else {
				setDenominationchecking(Constants.DenominationNotAvailable);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occured During Denomination " + e.getMessage());

		}
	}

	// Refund denomination data
	private void refundDenominationData() {
		lstRefundData.clear();
		ArrayList<ForeignLocalCurrencyDataTable> localLstData = new ArrayList<ForeignLocalCurrencyDataTable>();
		/* Checking that it's first time or not, first time list size will be 0 */
		// double sAmount = 0;
		localLstData.clear();
		if (localLstData.size() == 0) {

			List<ViewStock> stockList = iPersonalRemittanceService.toCheckStockForView(sessionStateManage.getCountryId(),
					sessionStateManage.getUserName(), sessionStateManage.getBranchId(), sessionStateManage.getCompanyId(),
					sessionStateManage.getCurrencyId());

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

		if(getSendOrReceive() != null && getSendOrReceive().equalsIgnoreCase(Constants.R)){
			setCollectedRefundAmount(BigDecimal.ZERO);
			setDenomtotalCash(GetRound.roundBigDecimal(new BigDecimal(totalSum),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
			System.out.println("getTxnType()==2 getDenomtotalCash :"+getDenomtotalCash());
			setPayNetPaidAmount(getCalNetAmountPaid());
			System.out.println("getTxnType()==2 getPayNetPaidAmount :"+getPayNetPaidAmount());
			setPayPaidAmount(GetRound.roundBigDecimal(getToalUsedAmount(),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
			System.out.println("getPayPaidAmount() :"+getPayPaidAmount()+"\t getPayNetPaidAmount() :"+getPayNetPaidAmount());
			setPayRefund(getPayNetPaidAmount());//getPayPaidAmount().subtract(getPayNetPaidAmount()));
			System.out.println("getTxnType()==2 setPayRefund :"+getPayRefund());
			setLstRefundData(localLstData);
			setRefundAmount(getCashAmount());
		}else if(getSendOrReceive() != null && getSendOrReceive().equalsIgnoreCase(Constants.S)){
			setRefundAmount(getPayRefund());
			setCollectedRefundAmount(new BigDecimal(0));
			setLstRefundData(localLstData);
		}else{
			setErrorMessage("Unable to Identify Send or Receive");
			RequestContext.getCurrentInstance().execute("errorPage.show();");
			return;
		}

		if (localLstData.size() != 0) {
			setDenominationchecking(Constants.DenominationAvailable);
		} else {
			setDenominationchecking(Constants.DenominationNotAvailable);
		}

	}

	// Collection cell edit
	public void onCellEdit(ForeignLocalCurrencyDataTable foreignLocalCurrencyDataTable) {

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
				foreignLocalCurrencyDataTable.setPrice(GetRound.roundBigDecimal(totalcashAmount,
						foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId())))
						.toPlainString());
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
			setDenomtotalCash(GetRound.roundBigDecimal(totalSum,
					foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
			setCollectedAmount(getDenomtotalCash());
			RequestContext.getCurrentInstance().execute("cleardenominationexceed.show();");
			setDataTableClear(foreignLocalCurrencyDataTable);
			foreignLocalCurrencyDataTable.setQty("");
			return;

		} else {
			try {
				setDenomtotalCash(GetRound.roundBigDecimal(totalSum,
						foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
			} catch (Exception e) {
				System.out.println("Exceptionnnnnnnnnnnnn---------------------->33333333" + e);
			}

			setCollectedAmount(getDenomtotalCash());
		}

	}


	// Refund cell edit
	public void onRefundCellEdit(ForeignLocalCurrencyDataTable foreignLocalCurrencyDataTable) {

		int quantity = 0; 

		if (foreignLocalCurrencyDataTable.getQty() == "" && foreignLocalCurrencyDataTable.getQty() != null) {
			quantity = 0;
		} else {
			try {
				quantity = Integer.parseInt(foreignLocalCurrencyDataTable.getQty());

			} catch (Exception e) {
				System.out.println("Exception occured" + e.getMessage());
				quantity = 0;

			}
		}


		if (foreignLocalCurrencyDataTable.getStock() >= quantity && quantity != 0) {

			BigDecimal totalcashAmount = foreignLocalCurrencyDataTable.getItem().multiply(
					new BigDecimal(foreignLocalCurrencyDataTable.getQty() == "" ? "0" : foreignLocalCurrencyDataTable.getQty()));
			if (foreignLocalCurrencyDataTable.getQty().equals("")) {
				foreignLocalCurrencyDataTable.setQty("");
				// added by rabil for clear if blank
				foreignLocalCurrencyDataTable.setPrice("");
			}
			if (totalcashAmount != null && totalcashAmount.doubleValue() != 0) {
				foreignLocalCurrencyDataTable.setPrice(GetRound.roundBigDecimal(totalcashAmount,
						foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId())))
						.toPlainString());
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
				setCollectedRefundAmount(GetRound.roundBigDecimal(totalSum,foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
				foreignLocalCurrencyDataTable.setQty("");
				foreignLocalCurrencyDataTable.setPrice("");
				RequestContext.getCurrentInstance().execute("clearrefunddenominationexceed.show();");
				return;

			} else {
				setCollectedRefundAmount(GetRound.roundBigDecimal(totalSum,foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
			}
		} else {
			RequestContext.getCurrentInstance().execute("stockNotAvailable.show();");
			foreignLocalCurrencyDataTable.setQty("");
			foreignLocalCurrencyDataTable.setPrice("");
		}
	}

	// Save all denomination
	public void saveAll() {

		try{
			if (getBooRendercashdenomination()) {
				if (lstData.size() != 0) {

					if(lstRefundData.size() != 0){
						// need to collect from customer
						if(getCashAmount().compareTo(getDenomtotalCash())==0){
							saveRemittance();
						}else {
							RequestContext.getCurrentInstance().execute("netamountgreater.show();");
						}
					}else{
						if (getCashAmount().compareTo(getDenomtotalCash()) == 0) {
							if (getPayRefund().compareTo(BigDecimal.ZERO) > 0) {
								nextpaneltoRefundDenomination();
								setNextOrSaveButton(Constants.Save);
							} else {
								saveRemittance();
							}
						} else {
							RequestContext.getCurrentInstance().execute("netamountgreater.show();");
						}
					}
				} else {
					RequestContext.getCurrentInstance().execute("denominationerror.show();");
				}
			} else if (isBoorefundPaymentDetails()) {
				if (lstRefundData.size() != 0) {

					if (lstData.size() != 0) {
						// need to pay to customer
						if(getRefundAmount().compareTo(getCollectedRefundAmount())==0){
							saveRemittance();
						}else{
							RequestContext.getCurrentInstance().execute("refundamounterror.show();");
						}
					}else{
						if (getTotalrefundAmount().compareTo(BigDecimal.ZERO) == 0) {
							saveRemittance();
						} else {
							// need to pay to customer
							//RequestContext.getCurrentInstance().execute("refundamounterror.show();");
							if(getRefundAmount().compareTo(getCollectedRefundAmount())==0){
								denaMinLstData();
								setBooRendercashdenomination(true);
								setBoorefundPaymentDetails(false);
								setCashAmount(getPayRefund());
								setNextOrSaveButton(Constants.Save);
							}else{
								RequestContext.getCurrentInstance().execute("refundamounterror.show();");
							}
						}
					}
				} else {
					RequestContext.getCurrentInstance().execute("denominationerror.show();");
				}
			} else {
				if (getPayRefund().compareTo(BigDecimal.ZERO) > 0) {
					nextpaneltoRefundDenomination();
					setNextOrSaveButton(Constants.Save);
				} else {
					saveRemittance();
				}
			}
		}catch(Exception e){
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("errorPage.show();");
		}
	}

	// to render panel 8 - Refund Denomination details
	public void nextpaneltoRefundDenomination() {
		// first panel
		setBooRenderBenificaryFirstPanel(false);
		setBooRenderCurrencyAdjust(true);
		setBooRendercashdenomination(false);
		setBoorefundPaymentDetails(true);
		setBooRenderPaymentDetails(false);
		setBooSaveDenomRender(false);
		refundDenominationData();
	}

	// Save Denomination
	public void saveRemittance() {

		String errorMessage = null;

		if(getSendOrReceive() != null && getSendOrReceive().equalsIgnoreCase(Constants.R)){

			try {
				WesternUnionBeneficaryDataTable selectRec = selectedWUBeneList.get(0);
				saveWUReceive(selectRec);
			} catch (IOException e) {
				log.info("exception.getMessage():::::::::::::::::::::::::::::::" + e.getMessage());
				setErrorMessage(e.getMessage());
				RequestContext.getCurrentInstance().execute("errorPage.show();");
				return;
			} catch (InterruptedException e) {
				log.info("exception.getMessage():::::::::::::::::::::::::::::::" + e.getMessage());
				setErrorMessage(e.getMessage());
				RequestContext.getCurrentInstance().execute("errorPage.show();");
				return;
			} catch (AMGException e) {
				log.info("exception.getMessage():::::::::::::::::::::::::::::::" + e.getMessage());
				setErrorMessage(e.getMessage());
				RequestContext.getCurrentInstance().execute("errorPage.show();");
				return;
			}

			// if any records available in Temp Currency Adjust- Hard Delete
			westernUnionService.deleteFromExTempCurrencyAdjust(getReceiveCompanyCode(), getReceiveDocumentCode(), getReceiveDocumentFinanceYear(), getReceiveDocumentNo(), getMtcNO());

			Boolean saveRec = saveForeignCurrencyAdjust();

			if(saveRec != null){
				if(saveRec){
					errorMessage = westernUnionService.updateWUTransfer(getReceiveCompanyCode(), getReceiveDocumentCode(), getReceiveDocumentFinanceYear(),getReceiveDocumentNo());
					if (errorMessage == null) {
						finalRender();
					} else {
						setErrorMessage(errorMessage);
						RequestContext.getCurrentInstance().execute("errorPage.show();");
						return;
					}
				}else{
					setErrorMessage("Amount Not Matching");
					RequestContext.getCurrentInstance().execute("errorPage.show();");
					return;
				}
			}else{
				setErrorMessage("Amount Not Avaiable");
				RequestContext.getCurrentInstance().execute("errorPage.show();");
				return;
			}

		}else if(getSendOrReceive() != null && getSendOrReceive().equalsIgnoreCase(Constants.S)){


			try {
				WesternUnionBeneficaryDataTable selectRec = selectedWUBeneList.get(0);
				saveWUTransfer(selectRec);
			} catch (IOException e) {
				log.info("exception.getMessage():::::::::::::::::::::::::::::::" + e.getMessage());
				setErrorMessage(e.getMessage());
				RequestContext.getCurrentInstance().execute("errorPage.show();");
				return;
			} catch (InterruptedException e) {
				log.info("exception.getMessage():::::::::::::::::::::::::::::::" + e.getMessage());
				setErrorMessage(e.getMessage());
				RequestContext.getCurrentInstance().execute("errorPage.show();");
				return;
			} catch (AMGException e) {
				log.info("exception.getMessage():::::::::::::::::::::::::::::::" + e.getMessage());
				setErrorMessage(e.getMessage());
				RequestContext.getCurrentInstance().execute("errorPage.show();");
				return;
			}

			// if any records available in Temp Currency Adjust- Hard Delete
			westernUnionService.deleteFromExTempCurrencyAdjust(getSendCompanyCode(), getSendDocumentCode(), getSendDocumentFinanceYear(), getSendDocumentNo(), getMtcNO());

			Boolean saveRec = saveForeignCurrencyAdjust();

			if(saveRec != null){
				if(saveRec){
					errorMessage = westernUnionService.updateWUTransfer(getSendCompanyCode(), getSendDocumentCode(), getSendDocumentFinanceYear(),getSendDocumentNo());
					if (errorMessage == null) {
						finalRender();
					} else {
						setErrorMessage(errorMessage);
						RequestContext.getCurrentInstance().execute("errorPage.show();");
						return;
					}
				}else{
					setErrorMessage("Amount Not Matching");
					RequestContext.getCurrentInstance().execute("errorPage.show();");
					return;
				}
			}else{
				setErrorMessage("Amount Not Avaiable");
				RequestContext.getCurrentInstance().execute("errorPage.show();");
				return;
			}

		}else{
			// not S or R
		}
	}

	public void finalRender() {
		setBooRenderBenificaryFirstPanel(false);
		setBooRenderCurrencyAdjust(false);
		setBooRendercashdenomination(false);
		setBooRenderBeniListPanel(false);
		setBoorefundPaymentDetails(false);
		setBooRenderPaymentDetails(false);
		setBooSaveDenomRender(true);
		setBooSaveAll(false);
	}

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
		// String year = String.valueOf(new Date().getYear()).substring(1, 3);

		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		String year = String.valueOf(calendar.get(Calendar.YEAR));

		System.out.println(Calendar.getInstance().get(Calendar.MONTH));
		return "01/" + data.get(Calendar.getInstance().get(Calendar.MONTH)) + "/" + year;
	}

	// Save Currency denomination to currency adjust
	public Boolean saveForeignCurrencyAdjust() {

		BigDecimal totalCollect = BigDecimal.ZERO;
		BigDecimal totalRefund = BigDecimal.ZERO;
		Boolean saveTempCurrencyAdjust = false;

		List<ForeignCurrencyAdjustWU> foreignCurrencyAdjustlst = new ArrayList<ForeignCurrencyAdjustWU>();

		try {

			SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
			Date acc_Month = null;
			acc_Month = DATE_FORMAT.parse(getCurrentDateWithFormat());

			List<UserFinancialYear> finYearList = generalService.getDealYear(new Date());

			int i = 0;

			for (ForeignLocalCurrencyDataTable foreignLocalCurrencyDataTable : lstData) {

				if (!foreignLocalCurrencyDataTable.getQty().equals("") && foreignLocalCurrencyDataTable.getQty() != null && !foreignLocalCurrencyDataTable.getQty().equals("0")) {

					ForeignCurrencyAdjustWU foreignCurrencyAdjust = new ForeignCurrencyAdjustWU();

					// Country save
					CountryMaster countryMaster = new CountryMaster();
					countryMaster.setCountryId(new BigDecimal(sessionStateManage.getSessionValue("countryId")));
					foreignCurrencyAdjust.setFsCountryMaster(countryMaster);

					// customer Save
					if (getWucheckRecCustomer() != null) {
						Customer customer = new Customer();
						customer.setCustomerId(getWucheckRecCustomer());
						foreignCurrencyAdjust.setFsCustomer(customer);
					} else {
						Customer customer = new Customer();
						customer.setCustomerId(getCustomerID());
						foreignCurrencyAdjust.setFsCustomer(customer);
					}

					foreignCurrencyAdjust.setDocumentCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_WU));
					// send document number
					if(getSendOrReceive() != null && getSendOrReceive().equalsIgnoreCase(Constants.S)){
						foreignCurrencyAdjust.setDocumentNo(getSendDocumentNo());
					} else if(getSendOrReceive() != null && getSendOrReceive().equalsIgnoreCase(Constants.R)){
						foreignCurrencyAdjust.setDocumentNo(getReceiveDocumentNo());
					}

					foreignCurrencyAdjust.setDocumentLineNumber(new BigDecimal(++i));
					foreignCurrencyAdjust.setDocumentDate(new Date());
					foreignCurrencyAdjust.setAccountmmyyyy(acc_Month);

					// country branch Id
					CountryBranch countryBranch = new CountryBranch();
					countryBranch.setCountryBranchId(new BigDecimal(sessionStateManage.getBranchId()));
					foreignCurrencyAdjust.setCountryBranch(countryBranch);

					// currency Id
					CurrencyMaster currencyMaster = new CurrencyMaster();
					currencyMaster.setCurrencyId(new BigDecimal(sessionStateManage.getCurrencyId()));
					foreignCurrencyAdjust.setFsCurrencyMaster(currencyMaster);

					foreignCurrencyAdjust.setAdjustmentAmount(new BigDecimal(foreignLocalCurrencyDataTable.getPrice()));
					foreignCurrencyAdjust.setNotesQuantity(new BigDecimal(foreignLocalCurrencyDataTable.getQty()));
					// calculate amount
					totalCollect = totalCollect.add(foreignCurrencyAdjust.getAdjustmentAmount());

					// currency denomination Id
					CurrencyWiseDenomination denominationMaster = new CurrencyWiseDenomination();
					denominationMaster.setDenominationId(foreignLocalCurrencyDataTable.getDenominationId());
					foreignCurrencyAdjust.setFsDenominationId(denominationMaster);

					// western union exchange rate not available
					//foreignCurrencyAdjust.setExchangeRate(BigDecimal.ONE);

					foreignCurrencyAdjust.setDenaminationAmount(foreignLocalCurrencyDataTable.getDenominationAmount());

					foreignCurrencyAdjust.setProgNumber(Constants.FC_SALE_REMIT);
					//foreignCurrencyAdjust.setProgNumber("WESTERN UNION");

					foreignCurrencyAdjust.setDocumentStatus(Constants.Yes);
					foreignCurrencyAdjust.setTransactionType(Constants.C);
					foreignCurrencyAdjust.setCreatedDate(new Date());
					foreignCurrencyAdjust.setCreatedBy(sessionStateManage.getUserName());

					foreignCurrencyAdjust.setMtcNo(getMtcNO());

					if(finYearList != null && !finYearList.isEmpty()){
						foreignCurrencyAdjust.setDocumentFinanceYear(finYearList.get(0).getFinancialYear());
					}

					//westernUnionService.saveForeignCurrencyAdjust(foreignCurrencyAdjust);
					foreignCurrencyAdjustlst.add(foreignCurrencyAdjust);

				} else {
					log.info("Number of notes is 0");
				}
			}


			int j = 0;

			for (ForeignLocalCurrencyDataTable foreignLocalCurrencyDataTable : lstRefundData) {

				if (!foreignLocalCurrencyDataTable.getQty().equals("") && foreignLocalCurrencyDataTable.getQty() != null && !foreignLocalCurrencyDataTable.getQty().equals("0")) {

					ForeignCurrencyAdjustWU foreignCurrencyAdjust = new ForeignCurrencyAdjustWU();

					// Country save
					CountryMaster countryMaster = new CountryMaster();
					countryMaster.setCountryId(new BigDecimal(sessionStateManage.getSessionValue("countryId")));
					foreignCurrencyAdjust.setFsCountryMaster(countryMaster);

					// customer Save
					if (getWucheckRecCustomer() != null) {
						Customer customer = new Customer();
						customer.setCustomerId(getWucheckRecCustomer());
						foreignCurrencyAdjust.setFsCustomer(customer);
					} else {
						Customer customer = new Customer();
						customer.setCustomerId(getCustomerID());
						foreignCurrencyAdjust.setFsCustomer(customer);
					}

					foreignCurrencyAdjust.setDocumentCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_WU));
					if(getSendOrReceive() != null && getSendOrReceive().equalsIgnoreCase(Constants.S)){
						foreignCurrencyAdjust.setDocumentNo(getSendDocumentNo());
					} else if(getSendOrReceive() != null && getSendOrReceive().equalsIgnoreCase(Constants.R)){
						foreignCurrencyAdjust.setDocumentNo(getReceiveDocumentNo());
					}
					foreignCurrencyAdjust.setDocumentLineNumber(new BigDecimal(++j));
					foreignCurrencyAdjust.setDocumentDate(new Date());
					foreignCurrencyAdjust.setAccountmmyyyy(acc_Month);

					// country branch Id
					CountryBranch countryBranch = new CountryBranch();
					countryBranch.setCountryBranchId(new BigDecimal(sessionStateManage.getBranchId()));
					foreignCurrencyAdjust.setCountryBranch(countryBranch);

					// currency Id
					CurrencyMaster currencyMaster = new CurrencyMaster();
					currencyMaster.setCurrencyId(new BigDecimal(sessionStateManage.getCurrencyId()));
					foreignCurrencyAdjust.setFsCurrencyMaster(currencyMaster);

					foreignCurrencyAdjust.setAdjustmentAmount(new BigDecimal(foreignLocalCurrencyDataTable.getPrice()));
					foreignCurrencyAdjust.setNotesQuantity(new BigDecimal(foreignLocalCurrencyDataTable.getQty()));
					// calculate amount
					totalRefund = totalRefund.add(foreignCurrencyAdjust.getAdjustmentAmount());

					// currency denomination Id
					CurrencyWiseDenomination denominationMaster = new CurrencyWiseDenomination();
					denominationMaster.setDenominationId(foreignLocalCurrencyDataTable.getDenominationId());
					foreignCurrencyAdjust.setFsDenominationId(denominationMaster);

					// western union exchange rate not available
					//foreignCurrencyAdjust.setExchangeRate(BigDecimal.ONE);

					foreignCurrencyAdjust.setDenaminationAmount(foreignLocalCurrencyDataTable.getDenominationAmount());

					foreignCurrencyAdjust.setProgNumber(Constants.FC_SALE_REMIT);
					//foreignCurrencyAdjust.setProgNumber("WESTERN UNION");

					foreignCurrencyAdjust.setDocumentStatus(Constants.Yes);
					foreignCurrencyAdjust.setTransactionType(Constants.F);

					foreignCurrencyAdjust.setCreatedDate(new Date());
					foreignCurrencyAdjust.setCreatedBy(sessionStateManage.getUserName());
					foreignCurrencyAdjust.setMtcNo(getMtcNO());

					if(finYearList != null && !finYearList.isEmpty()){
						foreignCurrencyAdjust.setDocumentFinanceYear(finYearList.get(0).getFinancialYear());
					}

					//westernUnionService.saveForeignCurrencyAdjust(foreignCurrencyAdjust);
					foreignCurrencyAdjustlst.add(foreignCurrencyAdjust);

				} else {
					log.info("Number of notes is 0");
				}
			}

			if(totalCollect.compareTo(BigDecimal.ZERO) != 0 || totalRefund.compareTo(BigDecimal.ZERO) != 0){
				BigDecimal originalAmount = BigDecimal.ZERO;
				if(totalCollect.compareTo(totalRefund)>=0){
					originalAmount = totalCollect.subtract(totalRefund);
				}else{
					originalAmount = totalRefund.subtract(totalCollect);
				}

				if(getCalNetAmountPaid() != null && getCalNetAmountPaid().compareTo(BigDecimal.ZERO) != 0){
					if(originalAmount.compareTo(BigDecimal.ZERO) != 0 && originalAmount.compareTo(getCalNetAmountPaid())==0){
						// save rec
						saveTempCurrencyAdjust = true;
						westernUnionService.saveForeignCurrencyAdjust(foreignCurrencyAdjustlst);
					}else{
						saveTempCurrencyAdjust = false;
					}
				}else{
					saveTempCurrencyAdjust = null;
				}
			}

		} catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::" + ne.getMessage());
			setErrorMessage("Method Name::saveForeignCurrencyAdjust");
			RequestContext.getCurrentInstance().execute("errorPage.show();");
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::" + exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("errorPage.show();");
		}

		return saveTempCurrencyAdjust;
	}

}
