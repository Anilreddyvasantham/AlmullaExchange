package com.amg.exchange.online.bean;

import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
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

import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.foreigncurrency.model.SourceOfIncomeDescription;
import com.amg.exchange.foreigncurrency.service.ForeignLocalCurrencyDenominationService;
import com.amg.exchange.foreigncurrency.service.IForeignCurrencyPurchaseService;
import com.amg.exchange.mail.ApllicationMailer1;
import com.amg.exchange.online.model.RatePlaceOrder;
import com.amg.exchange.online.service.IBranchStaffGSMRateService;
import com.amg.exchange.online.service.IGSMPlaceOrderRateFeedService;
import com.amg.exchange.online.service.IPlaceAnOrderCreationService;
import com.amg.exchange.online.service.IPlaceOrderPendingTransctionService;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.registration.service.IEncrptionDescriptionService;
import com.amg.exchange.remittance.bean.AddAdditionalBankData;
import com.amg.exchange.remittance.bean.AddDynamicLebel;
import com.amg.exchange.remittance.bean.PersonalRemittanceCollectionDataTable;
import com.amg.exchange.remittance.bean.PopulateData;
import com.amg.exchange.remittance.bean.PurposeOfRemittanceReportBean;
import com.amg.exchange.remittance.bean.RemittanceReceiptSubreport;
import com.amg.exchange.remittance.bean.RemittanceReportBean;
import com.amg.exchange.remittance.model.CollectionDetailView;
import com.amg.exchange.remittance.model.CollectionPaymentDetailsView;
import com.amg.exchange.remittance.model.CustomerBank;
import com.amg.exchange.remittance.model.DebitAutendicationView;
import com.amg.exchange.remittance.model.PaymentMode;
import com.amg.exchange.remittance.model.PaymentModeDesc;
import com.amg.exchange.remittance.model.PurposeOfRemittanceView;
import com.amg.exchange.remittance.model.RemittanceApplication;
import com.amg.exchange.remittance.model.RemittanceApplicationView;
import com.amg.exchange.remittance.model.ViewBankDetails;
import com.amg.exchange.remittance.service.ICustomerBankService;
import com.amg.exchange.remittance.service.IPaymentService;
import com.amg.exchange.remittance.service.IPersonalRemittanceService;
import com.amg.exchange.treasury.deal.supplier.model.ApplicationSetup;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.service.IGLTransactionForADocument;
import com.amg.exchange.treasury.service.ISpecialCustomerDealRequestService;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.GetRound;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;
@Component("placeOrderPendingTransctionBean")
@Scope("session")
public class PlaceOrderPendingTransctionBean<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger log=Logger.getLogger(PlaceOrderPendingTransctionBean.class);
	//Page level Variables
	private BigDecimal rateOfferedPk;
	private BigDecimal customerRefNo;
	private BigDecimal documentNumber;
	private BigDecimal beneficiaryMasterId;
	private String beneficiaryName;
	private BigDecimal beneficiaryBankId;
	private String beneficiaryBankName;
	private BigDecimal rateOffered;
	private BigDecimal currencyId;
	private String currencyName;
	private BigDecimal transctionAmount;
	private String customerRefAndName;
	private BigDecimal remitServiceId;
	private BigDecimal remitRemittanceId;
	private BigDecimal remitDeliveryId;
	private String amountAndQtyName;
	private BigDecimal beneficiaryAccountSeqId;
	private String currencyQtyName;
	private BigDecimal routingCountry;
	private BigDecimal routingBank;
	private BigDecimal minLenght;
	private BigDecimal maxLenght;
	private BigDecimal remitDocumentNumber;
	private BigDecimal remitDocumentFinanceYear;
	private BigDecimal totalAmount;
	private List<BigDecimal> lstRatePlaceOrderPk;
	
	//render Variables
	private BigDecimal sourceId;
	private BigDecimal paymentmodeId;
	private String paymentCode;
	private String exceptionMessage;
	private boolean additionalCheck = true;
	//cheque and Card common Details
	private BigDecimal remitamount;
	private String colBankCode;
	
	//cheque  Details
	private BigDecimal remitBankId;
	private String checkRefNo;
	private Date remitApproveCheckDate;
	private BigDecimal approvalNumber;
	private Boolean booChequePanel=false;
	
	
	//card Details
	private Boolean booCardPanel=false;
	private String cardBankId;
	private BigDecimal cardNumber;
	private String nameOfTheCard;
	private BigDecimal approvalNumberCard;
	//common Variables
	private String errorMessage;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String isActive;
	private Boolean booRenderAdditionals=false;
	private Boolean booRenderSaveOrExit=false;
	private String procedureError;
	private Boolean paymentAmountPanel;
	
	
	SessionStateManage session= new SessionStateManage();
	//Search Customer Variables
	private BigDecimal customerId;
	private String customerName;
	
	private JasperPrint jasperPrint;
	private String customerNameForReport;
	private String exceptionMessageForReport;
	private String knetRecieptNo;
	private BigDecimal equivalentRemitAmount;
	private String equivalentCurrency;
	
	private List<PlaceOrderPendingTransctionDataTable> lstPlaceOrderPendingTransction=new ArrayList<PlaceOrderPendingTransctionDataTable>();
	private List<SourceOfIncomeDescription> lstSourceofIncome=new ArrayList<SourceOfIncomeDescription>();
	private List<PaymentModeDesc> lstFetchAllPayMode=new ArrayList<PaymentModeDesc>();
	private List<AddDynamicLebel> listDynamicLebel = new ArrayList<AddDynamicLebel>();
	private List<AddAdditionalBankData> listAdditionalBankDataTable = new ArrayList<AddAdditionalBankData>();
	private List<ViewBankDetails> bankMasterList = new CopyOnWriteArrayList<ViewBankDetails>();
	private List<ViewBankDetails> chequebankMasterList = new ArrayList<ViewBankDetails>();
	private List<ViewBankDetails> localbankList = new ArrayList<ViewBankDetails>();
	
	private List<CollectionDetailView> collectionViewList = new CopyOnWriteArrayList<CollectionDetailView>();
	private List<RemittanceReceiptSubreport> remittanceReceiptSubreportList;
	
	//service Declarations
	@Autowired
	IBranchStaffGSMRateService branchStaffGSMRateService;
	
	@Autowired
	IGSMPlaceOrderRateFeedService gSMPlaceOrderRateFeedService;
	
	@Autowired
	IGeneralService<T> generalService;
	
	@Autowired
	IForeignCurrencyPurchaseService<T> foreignCurrencyPurchaseService;
	
	@Autowired
	IPaymentService ipaymentService;
	
	@Autowired
	IPersonalRemittanceService iPersonalRemittanceService;
	
	@Autowired
	IPlaceOrderPendingTransctionService placeOrderPendingTransctionService;
	
	@Autowired
	ICustomerBankService icustomerBankService;
	
	@Autowired
	ForeignLocalCurrencyDenominationService<T> foreignLocalCurrencyDenominationService;
	@Autowired
	ISpecialCustomerDealRequestService<T> specialCustomerDealRequestService;
	
	@Autowired
	ApllicationMailer1 mailService;
	
	@Autowired
	IPlaceAnOrderCreationService  iPlaceOnOrderCreationService;
	
	public String getEquivalentCurrency() {
		return equivalentCurrency;
	}

	public void setEquivalentCurrency(String equivalentCurrency) {
		this.equivalentCurrency = equivalentCurrency;
	}

	public BigDecimal getEquivalentRemitAmount() {
		return equivalentRemitAmount;
	}

	public void setEquivalentRemitAmount(BigDecimal equivalentRemitAmount) {
		this.equivalentRemitAmount = equivalentRemitAmount;
	}

	public String getKnetRecieptNo() {
		return knetRecieptNo;
	}

	public void setKnetRecieptNo(String knetRecieptNo) {
		this.knetRecieptNo = knetRecieptNo;
	}

	public String getExceptionMessageForReport() {
		return exceptionMessageForReport;
	}

	public void setExceptionMessageForReport(String exceptionMessageForReport) {
		this.exceptionMessageForReport = exceptionMessageForReport;
	}

	public Boolean getPaymentAmountPanel() {
		return paymentAmountPanel;
	}

	public void setPaymentAmountPanel(Boolean paymentAmountPanel) {
		this.paymentAmountPanel = paymentAmountPanel;
	}

	public List<RemittanceReceiptSubreport> getRemittanceReceiptSubreportList() {
		return remittanceReceiptSubreportList;
	}

	public void setRemittanceReceiptSubreportList(
			List<RemittanceReceiptSubreport> remittanceReceiptSubreportList) {
		this.remittanceReceiptSubreportList = remittanceReceiptSubreportList;
	}

	public List<CollectionDetailView> getCollectionViewList() {
		return collectionViewList;
	}

	public void setCollectionViewList(List<CollectionDetailView> collectionViewList) {
		this.collectionViewList = collectionViewList;
	}

	public String getCustomerNameForReport() {
		return customerNameForReport;
	}

	public void setCustomerNameForReport(String customerNameForReport) {
		this.customerNameForReport = customerNameForReport;
	}

	public List<BigDecimal> getLstRatePlaceOrderPk() {
		return lstRatePlaceOrderPk;
	}

	public void setLstRatePlaceOrderPk(List<BigDecimal> lstRatePlaceOrderPk) {
		this.lstRatePlaceOrderPk = lstRatePlaceOrderPk;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getCustomerRefNo() {
		return customerRefNo;
	}

	public void setCustomerRefNo(BigDecimal customerRefNo) {
		this.customerRefNo = customerRefNo;
	}

	public BigDecimal getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(BigDecimal documentNumber) {
		this.documentNumber = documentNumber;
	}

	public String getBeneficiaryName() {
		return beneficiaryName;
	}

	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	public BigDecimal getBeneficiaryBankId() {
		return beneficiaryBankId;
	}

	public void setBeneficiaryBankId(BigDecimal beneficiaryBankId) {
		this.beneficiaryBankId = beneficiaryBankId;
	}

	public String getBeneficiaryBankName() {
		return beneficiaryBankName;
	}

	public void setBeneficiaryBankName(String beneficiaryBankName) {
		this.beneficiaryBankName = beneficiaryBankName;
	}

	public BigDecimal getRateOffered() {
		return rateOffered;
	}

	public void setRateOffered(BigDecimal rateOffered) {
		this.rateOffered = rateOffered;
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

	public BigDecimal getTransctionAmount() {
		return transctionAmount;
	}

	public void setTransctionAmount(BigDecimal transctionAmount) {
		this.transctionAmount = transctionAmount;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
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

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public Boolean getBooRenderAdditionals() {
		return booRenderAdditionals;
	}

	public void setBooRenderAdditionals(Boolean booRenderAdditionals) {
		this.booRenderAdditionals = booRenderAdditionals;
	}

	public Boolean getBooRenderSaveOrExit() {
		return booRenderSaveOrExit;
	}

	public void setBooRenderSaveOrExit(Boolean booRenderSaveOrExit) {
		this.booRenderSaveOrExit = booRenderSaveOrExit;
	}

	public String getProcedureError() {
		return procedureError;
	}

	public void setProcedureError(String procedureError) {
		this.procedureError = procedureError;
	}

	public BigDecimal getCustomerId() {
		return customerId;
	}

	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public List<PlaceOrderPendingTransctionDataTable> getLstPlaceOrderPendingTransction() {
		return lstPlaceOrderPendingTransction;
	}

	public void setLstPlaceOrderPendingTransction(
			List<PlaceOrderPendingTransctionDataTable> lstPlaceOrderPendingTransction) {
		this.lstPlaceOrderPendingTransction = lstPlaceOrderPendingTransction;
	}

	public String getCustomerRefAndName() {
		return customerRefAndName;
	}
	public void setCustomerRefAndName(String customerRefAndName) {
		this.customerRefAndName = customerRefAndName;
	}
	
	public BigDecimal getRemitServiceId() {
		return remitServiceId;
	}

	public void setRemitServiceId(BigDecimal remitServiceId) {
		this.remitServiceId = remitServiceId;
	}

	public BigDecimal getRemitRemittanceId() {
		return remitRemittanceId;
	}

	public void setRemitRemittanceId(BigDecimal remitRemittanceId) {
		this.remitRemittanceId = remitRemittanceId;
	}

	public BigDecimal getRemitDeliveryId() {
		return remitDeliveryId;
	}

	public void setRemitDeliveryId(BigDecimal remitDeliveryId) {
		this.remitDeliveryId = remitDeliveryId;
	}
	public BigDecimal getRateOfferedPk() {
		return rateOfferedPk;
	}
	public void setRateOfferedPk(BigDecimal rateOfferedPk) {
		this.rateOfferedPk = rateOfferedPk;
	}
	public String getAmountAndQtyName() {
		return amountAndQtyName;
	}
	public void setAmountAndQtyName(String amountAndQtyName) {
		this.amountAndQtyName = amountAndQtyName;
	}
	public BigDecimal getBeneficiaryMasterId() {
		return beneficiaryMasterId;
	}

	public void setBeneficiaryMasterId(BigDecimal beneficiaryMasterId) {
		this.beneficiaryMasterId = beneficiaryMasterId;
	}
	public BigDecimal getBeneficiaryAccountSeqId() {
		return beneficiaryAccountSeqId;
	}

	public void setBeneficiaryAccountSeqId(BigDecimal beneficiaryAccountSeqId) {
		this.beneficiaryAccountSeqId = beneficiaryAccountSeqId;
	}

	public String getCurrencyQtyName() {
		return currencyQtyName;
	}

	public void setCurrencyQtyName(String currencyQtyName) {
		this.currencyQtyName = currencyQtyName;
	}
	public List<SourceOfIncomeDescription> getLstSourceofIncome() {
		return lstSourceofIncome;
	}

	public void setLstSourceofIncome(List<SourceOfIncomeDescription> lstSourceofIncome) {
		this.lstSourceofIncome = lstSourceofIncome;
	}

	public List<PaymentModeDesc> getLstFetchAllPayMode() {
		return lstFetchAllPayMode;
	}

	public void setLstFetchAllPayMode(List<PaymentModeDesc> lstFetchAllPayMode) {
		this.lstFetchAllPayMode = lstFetchAllPayMode;
	}
	

	public BigDecimal getSourceId() {
		return sourceId;
	}

	public void setSourceId(BigDecimal sourceId) {
		this.sourceId = sourceId;
	}

	public BigDecimal getPaymentmodeId() {
		return paymentmodeId;
	}

	public void setPaymentmodeId(BigDecimal paymentmodeId) {
		this.paymentmodeId = paymentmodeId;
	}
	public List<AddDynamicLebel> getListDynamicLebel() {
		return listDynamicLebel;
	}

	public void setListDynamicLebel(List<AddDynamicLebel> listDynamicLebel) {
		this.listDynamicLebel = listDynamicLebel;
	}

	public List<AddAdditionalBankData> getListAdditionalBankDataTable() {
		return listAdditionalBankDataTable;
	}

	public void setListAdditionalBankDataTable(
			List<AddAdditionalBankData> listAdditionalBankDataTable) {
		this.listAdditionalBankDataTable = listAdditionalBankDataTable;
	}
	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public boolean isAdditionalCheck() {
		return additionalCheck;
	}

	public void setAdditionalCheck(boolean additionalCheck) {
		this.additionalCheck = additionalCheck;
	}
	public BigDecimal getRoutingCountry() {
		return routingCountry;
	}

	public void setRoutingCountry(BigDecimal routingCountry) {
		this.routingCountry = routingCountry;
	}

	public BigDecimal getRoutingBank() {
		return routingBank;
	}

	public void setRoutingBank(BigDecimal routingBank) {
		this.routingBank = routingBank;
	}
	
	public BigDecimal getMinLenght() {
		return minLenght;
	}

	public void setMinLenght(BigDecimal minLenght) {
		this.minLenght = minLenght;
	}

	public BigDecimal getMaxLenght() {
		return maxLenght;
	}

	public void setMaxLenght(BigDecimal maxLenght) {
		this.maxLenght = maxLenght;
	}
	public BigDecimal getRemitDocumentNumber() {
		return remitDocumentNumber;
	}

	public void setRemitDocumentNumber(BigDecimal remitDocumentNumber) {
		this.remitDocumentNumber = remitDocumentNumber;
	}

	public BigDecimal getRemitDocumentFinanceYear() {
		return remitDocumentFinanceYear;
	}

	public void setRemitDocumentFinanceYear(BigDecimal remitDocumentFinanceYear) {
		this.remitDocumentFinanceYear = remitDocumentFinanceYear;
	}
	public String getPaymentCode() {
		return paymentCode;
	}

	public void setPaymentCode(String paymentCode) {
		this.paymentCode = paymentCode;
	}
	
	
	public BigDecimal getRemitamount() {
		return remitamount;
	}

	public void setRemitamount(BigDecimal remitamount) {
		this.remitamount = remitamount;
	}

	public BigDecimal getRemitBankId() {
		return remitBankId;
	}

	public void setRemitBankId(BigDecimal remitBankId) {
		this.remitBankId = remitBankId;
	}

	public String getCheckRefNo() {
		return checkRefNo;
	}

	public void setCheckRefNo(String checkRefNo) {
		this.checkRefNo = checkRefNo;
	}

	public Date getRemitApproveCheckDate() {
		return remitApproveCheckDate;
	}

	public void setRemitApproveCheckDate(Date remitApproveCheckDate) {
		this.remitApproveCheckDate = remitApproveCheckDate;
	}

	public BigDecimal getApprovalNumber() {
		return approvalNumber;
	}

	public void setApprovalNumber(BigDecimal approvalNumber) {
		this.approvalNumber = approvalNumber;
	}

	public Boolean getBooChequePanel() {
		return booChequePanel;
	}

	public void setBooChequePanel(Boolean booChequePanel) {
		this.booChequePanel = booChequePanel;
	}

	public Boolean getBooCardPanel() {
		return booCardPanel;
	}

	public void setBooCardPanel(Boolean booCardPanel) {
		this.booCardPanel = booCardPanel;
	}

	public BigDecimal getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(BigDecimal cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getNameOfTheCard() {
		return nameOfTheCard;
	}

	public void setNameOfTheCard(String nameOfTheCard) {
		this.nameOfTheCard = nameOfTheCard;
	}

	public BigDecimal getApprovalNumberCard() {
		return approvalNumberCard;
	}

	public void setApprovalNumberCard(BigDecimal approvalNumberCard) {
		this.approvalNumberCard = approvalNumberCard;
	}
	
	public String getCardBankId() {
		return cardBankId;
	}

	public void setCardBankId(String cardBankId) {
		this.cardBankId = cardBankId;
	}
	
	public List<ViewBankDetails> getBankMasterList() {
		return bankMasterList;
	}

	public void setBankMasterList(List<ViewBankDetails> bankMasterList) {
		this.bankMasterList = bankMasterList;
	}

	public List<ViewBankDetails> getChequebankMasterList() {
		return chequebankMasterList;
	}

	public void setChequebankMasterList(List<ViewBankDetails> chequebankMasterList) {
		this.chequebankMasterList = chequebankMasterList;
	}
	
	public String getColBankCode() {
		return colBankCode;
	}

	public void setColBankCode(String colBankCode) {
		this.colBankCode = colBankCode;
	}

	public List<ViewBankDetails> getLocalbankList() {
		return localbankList;
	}

	public void setLocalbankList(List<ViewBankDetails> localbankList) {
		this.localbankList = localbankList;
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	//Page Navigation
	public void pageNavigationPendingPlaceOrder(){
		lstPlaceOrderPendingTransction.clear();
		clearAllFields();
		tofetchAllRecords();
		setBooRenderAdditionals(false);
		setBooRenderSaveOrExit(false);
		setBooChequePanel(false);
		setBooCardPanel(false);
		toFetchPaymentDetails();
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "PlaceOrderPendingTransction.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../onlinespecialrate/PlaceOrderPendingTransction.xhtml");
		} catch (Exception exception) {
			  setErrorMessage(exception.getMessage());
				log.error("pageNavigationPendingPlaceOrder  :"+ exception.getMessage());
				RequestContext.getCurrentInstance().execute("exception.show();");
		}
	}

	//clear Page level Variables
	public void clearAllFields(){
		//clear Page level Variables
		setRateOfferedPk(null);
		setCustomerRefNo(null);
		setDocumentNumber(null);
		setBeneficiaryName(null);
		setBeneficiaryBankId(null);
		setBeneficiaryBankName(null);
		setRateOffered(null);
		setCurrencyId(null);
		setCurrencyName(null);
		setTransctionAmount(null);
		setCustomerRefAndName(null);
		setRemitServiceId(null);
		setRemitRemittanceId(null);
		setRemitDeliveryId(null);
		setCustomerId(null);
		setCustomerName(null);
		setRateOfferedPk(null);
		setAmountAndQtyName(null);
		setBeneficiaryMasterId(null);
		setBeneficiaryAccountSeqId(null);
		setCurrencyQtyName(null);
		setRoutingCountry(null);
		setRoutingBank(null);
		setPaymentCode(null);
		//common Variables
		setErrorMessage(null);;
		setCreatedBy(null);
		setCreatedDate(null);
		setModifiedBy(null);
		setModifiedDate(null);
		setApprovedBy(null);
		setApprovedDate(null);
		setIsActive(null);
		
		//cheque and Card common Details
		setRemitamount(null);;
		setRemitBankId(null);;
		setCheckRefNo(null);;
		//cheque  Details
		setRemitApproveCheckDate(null);;
		setApprovalNumber(null);;
		
		//card Details
		setCardNumber(null);;
		setNameOfTheCard(null);
		setApprovalNumberCard(null);
		setCardBankId(null);
		setLstRatePlaceOrderPk(null);
		setBooRenderSaveOrExit(false);
		setTotalAmount(null);
		setPaymentAmountPanel(false);
	}
	
	//on form load to fetch All records
	public void tofetchAllRecords(){
		setPaymentAmountPanel(false);
		lstPlaceOrderPendingTransction.clear();
		List<RatePlaceOrder> lstRatePlaceOrder=placeOrderPendingTransctionService.toFetchAllRecordsFromDb(getCustomerId());
		if(lstRatePlaceOrder.size()>0){
			for (RatePlaceOrder ratePlaceOrder : lstRatePlaceOrder) {
				PlaceOrderPendingTransctionDataTable placeOrderPendingDT=new PlaceOrderPendingTransctionDataTable();
				placeOrderPendingDT.setRateOfferedPk(ratePlaceOrder.getRatePlaceOrderId());
				placeOrderPendingDT.setCustomerId(ratePlaceOrder.getCustomerId());
				String custmerName=null;
				BigDecimal custrRef=null;
				String BeneName=null;
				String beneBankName=null;
				String curQtyName=null;
				//to Fetch Customer reference and Name
				custrRef=gSMPlaceOrderRateFeedService.toFetchCustomerRef(ratePlaceOrder.getCustomerId());
				custmerName=generalService.getCustomerNameCustomerId(ratePlaceOrder.getCustomerId());
				placeOrderPendingDT.setCustomerName(custmerName);
				placeOrderPendingDT.setCustomerRefNo(custrRef);
				placeOrderPendingDT.setCustomerRefAndName(custrRef+ "-" +custmerName);
				placeOrderPendingDT.setDocumentNumber(ratePlaceOrder.getDocumentNumber());
				//to bene name
				placeOrderPendingDT.setBeneficiaryMasterId(ratePlaceOrder.getBeneficiaryMasterId().getBeneficaryMasterSeqId());
				BeneName=gSMPlaceOrderRateFeedService.toFetchBeneficiaryName(ratePlaceOrder.getBeneficiaryMasterId().getBeneficaryMasterSeqId());
				placeOrderPendingDT.setBeneficiaryName(BeneName);
				//bene bank name
				placeOrderPendingDT.setBeneficiaryBankId(ratePlaceOrder.getBeneficiaryBankId());
				beneBankName=generalService.getBankName(ratePlaceOrder.getBeneficiaryBankId());
				placeOrderPendingDT.setBeneficiaryBankName(beneBankName);
				placeOrderPendingDT.setRateOffered(ratePlaceOrder.getRateOffered());
				placeOrderPendingDT.setTransctionAmount(ratePlaceOrder.getTransactionAmount());
				placeOrderPendingDT.setCurrencyId(ratePlaceOrder.getDestinationCurrenyId());
				//currency name qty code
				//ratePlaceOrder.getBeneficiaryMasterId().getBeneficaryMasterSeqId(),ratePlaceOrder.getAccountSeqquenceId().getBeneficaryAccountSeqId()
				/*curQtyName=gSMPlaceOrderRateFeedService.toFetchCurrencyQtyName(ratePlaceOrder.getCurrencyId());
				placeOrderPendingDT.setCurrencyQtyName("["+curQtyName+"]");
				placeOrderPendingDT.setAmountAndQtyName(ratePlaceOrder.getTransactionAmount()+ "-" +curQtyName);*/
				//remittance and service , delivery
				placeOrderPendingDT.setRemitServiceId(ratePlaceOrder.getServiceMasterId());
				placeOrderPendingDT.setRemitRemittanceId(ratePlaceOrder.getRemittanceModeId());
				placeOrderPendingDT.setRemitDeliveryId(ratePlaceOrder.getDeliveryModeId());
				placeOrderPendingDT.setCreatedBy(ratePlaceOrder.getCreatedBy());
				placeOrderPendingDT.setCreatedDate(ratePlaceOrder.getCreatedDate());
				placeOrderPendingDT.setModifiedBy(ratePlaceOrder.getModifiedBy());
				placeOrderPendingDT.setModifiedDate(ratePlaceOrder.getModifiedDate());
				placeOrderPendingDT.setApprovedBy(ratePlaceOrder.getApprovedBy());
				placeOrderPendingDT.setApprovedDate(ratePlaceOrder.getApprovedDate());
				placeOrderPendingDT.setIsActive(ratePlaceOrder.getIsActive());
				placeOrderPendingDT.setRoutingCountry(ratePlaceOrder.getRoutingCountryId());
				placeOrderPendingDT.setRoutingBank(ratePlaceOrder.getRoutingBankId());
				placeOrderPendingDT.setRemitDocumentNumber(ratePlaceOrder.getApplDocumentNumber());
				placeOrderPendingDT.setRemitDocumentFinanceYear(ratePlaceOrder.getApplDocumentFinanceYear());
				placeOrderPendingDT.setPaymentCode(ratePlaceOrder.getCollectionMode());
				placeOrderPendingDT.setBeneficiaryCountryId(ratePlaceOrder.getBeneficiaryCountryId());

				List<RemittanceApplication> lstRemittanceApplication= placeOrderPendingTransctionService.getTransactionDetails(ratePlaceOrder.getRatePlaceOrderId());

				if(lstRemittanceApplication!=null && lstRemittanceApplication.size()>0)
				{
					BigDecimal equivalentRemitAmount= lstRemittanceApplication.get(0).getLocalNetTranxAmount();
					BigDecimal equivalentCurrencyId=lstRemittanceApplication.get(0).getExCurrencyMasterByForeignCurrencyId().getCurrencyId();
					BigDecimal equivalentForeignTranxAmount= lstRemittanceApplication.get(0).getForeignTranxAmount();

					List<PopulateData> lstofCurrency = new ArrayList<PopulateData>();

					lstofCurrency= iPlaceOnOrderCreationService.getBasedOnCountyCurrency(ratePlaceOrder.getBeneficiaryCountryId());
					if(lstofCurrency!=null && lstofCurrency.size()>0)
					{
						if(lstofCurrency.get(0).getPopulateId().compareTo(ratePlaceOrder.getDestinationCurrenyId())==0)
						{
							curQtyName=gSMPlaceOrderRateFeedService.toFetchCurrencyQtyName(ratePlaceOrder.getDestinationCurrenyId());
							placeOrderPendingDT.setCurrencyQtyName("["+curQtyName+"]");
							placeOrderPendingDT.setAmountAndQtyName(ratePlaceOrder.getTransactionAmount()+ "-" +"["+curQtyName+"]");

							placeOrderPendingDT.setRemitLocalAmount(equivalentRemitAmount);


						}else
						{
							curQtyName=gSMPlaceOrderRateFeedService.toFetchCurrencyQtyName(equivalentCurrencyId);
							placeOrderPendingDT.setAmountAndQtyName(equivalentForeignTranxAmount+ "-" +"["+curQtyName+"]");
							placeOrderPendingDT.setRemitLocalAmount(equivalentRemitAmount);
						}
					}
				}
				lstPlaceOrderPendingTransction.add(placeOrderPendingDT);
			}
			if(lstPlaceOrderPendingTransction.size()>0)
			{
				setPaymentAmountPanel(true);
			}
		}
	}
	
	//on click event
	public void toLoadSourecOfincome(PlaceOrderPendingTransctionDataTable dataTable) throws AMGException{
		setRateOfferedPk(dataTable.getRateOfferedPk());
		setCustomerRefAndName(dataTable.getCustomerRefAndName());
		setDocumentNumber(dataTable.getDocumentNumber());
		setCurrencyId(dataTable.getCurrencyId());
		setRemitServiceId(dataTable.getRemitServiceId());
		setRemitRemittanceId(dataTable.getRemitRemittanceId());
		setRemitDeliveryId(dataTable.getRemitDeliveryId());
		setRoutingCountry(dataTable.getRoutingCountry());
		setRoutingBank(dataTable.getRoutingBank());
		setRemitDocumentNumber(dataTable.getRemitDocumentNumber());
		setRemitDocumentFinanceYear(dataTable.getRemitDocumentFinanceYear());
		setBooRenderAdditionals(true);
		setBooRenderSaveOrExit(true);
		setPaymentCode(dataTable.getPaymentCode());
		BigDecimal PaymentId=placeOrderPendingTransctionService.toFetchPaymentId(dataTable.getPaymentCode());
		setPaymentmodeId(PaymentId);
		toFetchPaymentDetails();
		getLocalBankListforIndicator();
		if(dataTable.getPaymentCode().equalsIgnoreCase("B")){
			setBooChequePanel(true);
			setBooCardPanel(false);
			setPaymentmodeId(PaymentId);
			
		}else{
			setBooChequePanel(false);
			setBooCardPanel(true);
			setPaymentmodeId(PaymentId);
		}
		
		//String PaymentName=placeOrderPendingTransctionService.toFetchPaymentName(PaymentId,session.getLanguageId());
	}
	
	
		//Payment Details
		public void toFetchPaymentDetails(){
			lstFetchAllPayMode.clear();
			List<PaymentModeDesc> list=gSMPlaceOrderRateFeedService.fetchPaymodeDesc(new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId") : "1"),Constants.Yes);
			if(list.size() !=0){
				lstFetchAllPayMode.addAll(list);
			}

		}
		
		// to get the local bank list or customer bank list
		public void getLocalBankListforIndicator() {
			List<BigDecimal> duplicateCheck = new ArrayList<BigDecimal>();
			List<BigDecimal> duplicateCheck1 = new ArrayList<BigDecimal>();
			List<ViewBankDetails> lstofBank = new ArrayList<ViewBankDetails>();
			List<ViewBankDetails> lstofBank1 = new ArrayList<ViewBankDetails>();
			bankMasterList.clear();
			chequebankMasterList.clear();
			setRemitBankId(null);
			setCardBankId(null);
			clearKnetDetails();
			localbankList = generalService.getLocalBankListFromView(session.getCountryId());

			// cheque banks purpose 
			if(localbankList.size() != 0){
				chequebankMasterList.addAll(localbankList);
			}

			
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
		//ajax event changes changes 
		public void changeBanksForPayment(){
			getLocalBankListforIndicator();
			setPaymentmodeId(null);
			if(getPaymentCode()!=null && getPaymentCode().equalsIgnoreCase("B"))
			{
				
				setBooChequePanel(true);
				setBooCardPanel(false);
				setRemitamount(null);
				BigDecimal PaymentId=placeOrderPendingTransctionService.toFetchPaymentId(getPaymentCode());
				setPaymentmodeId(PaymentId);
				//clearKnetDetails();
			}else
			{
				setBooChequePanel(false);
				setBooCardPanel(true);
				setRemitamount(null);
				BigDecimal PaymentId=placeOrderPendingTransctionService.toFetchPaymentId(getPaymentCode());
				setPaymentmodeId(PaymentId);
			}
			setBooRenderSaveOrExit(true);
		}
		
		//save Functionality starts
		public void save(){
			boolean verifyCheck=verifyNewUserDetails();
			if(!verifyCheck)
			{
				return;
			}
			BigDecimal totalAmount= getTotalAmount();
			BigDecimal remitAmount= getRemitamount();
			if(totalAmount!=null && remitAmount!=null)
			{
				if(remitAmount.compareTo(BigDecimal.ZERO)==0 || totalAmount.compareTo(BigDecimal.ZERO)==0)
				{
					RequestContext.getCurrentInstance().execute("selectOne.show();");
					return;
				}
				
				if(totalAmount.compareTo(remitAmount)!=0)
				{
					RequestContext.getCurrentInstance().execute("amountNotMatch.show();");
					return;
				}
			}else
			{
				RequestContext.getCurrentInstance().execute("selectOne.show();");
				return;
			}
			HashMap<String, Object> inputValues = new HashMap<String, Object>();
			
			inputValues.put("P_COMP_ID",session.getCompanyId());
			inputValues.put("PLACE_ORDER_PK",getLstRatePlaceOrderPk());
			inputValues.put("CHEQUE_BANK_CODE",getRemitBankId());
			inputValues.put("CHEQUE_DATE",getRemitApproveCheckDate());
			inputValues.put("CHEQUE_REFERENCE",getCheckRefNo());
			inputValues.put("COLLECTION_MODE",getPaymentCode());
			inputValues.put("APPROVAL_NO",getApprovalNumber());
			
			
			inputValues.put("DB_CARD_NAME",getNameOfTheCard());
			inputValues.put("DEBIT_CARD",getCardNumber());
			inputValues.put("KNET_RECEIPT",getKnetRecieptNo());
			inputValues.put("KNET_RECEIPT_DATE_TIME",new Date().toString());
			inputValues.put("KNET_APPROVAL_NO",getApprovalNumberCard());
			
			
			HashMap<String, Object> outPutValues=placeOrderPendingTransctionService.updatePlaceOrderPaymentDetails(inputValues);
			RatePlaceOrder ratePlaceOrder=(RatePlaceOrder) outPutValues.get("RATE_PLACE_ORDER");
			if(getPaymentCode().equalsIgnoreCase("B"))
			{
				RequestContext.getCurrentInstance().execute("chequeClear.show();");
				return;
			}else
			{
				try {
					
					inputValues.put("PLACE_ORDER_PK_TR",getLstRatePlaceOrderPk().get(0));
					HashMap<String, String> outPutValues1=placeOrderPendingTransctionService.placeOrderRemitTranxProcedure(inputValues);
					String errMsg=outPutValues1.get("P_ERROR_MESG");
					String pCollDocFyr=outPutValues1.get("P_COLL_DOCFYR");
					String pCollDocNo=outPutValues1.get("P_COLL_DOCNO");
					if(errMsg==null || errMsg.equalsIgnoreCase(""))
					{
						setErrorMessage(pCollDocNo);
						RequestContext.getCurrentInstance().execute("success.show();");
						clearKnetDetails();
						boolean rtnCheck=generatePersonalRemittanceReceiptReport(new BigDecimal(pCollDocNo), new BigDecimal(pCollDocFyr),ratePlaceOrder.getCustomerEmail());
						if(rtnCheck)
						{
							//setErrorMessage(pCollDocNo);
							//RequestContext.getCurrentInstance().execute("success.show();");
							//clearKnetDetails();
						}
						
						
					}else
					{
						setErrorMessage(errMsg);
						RequestContext.getCurrentInstance().execute("error.show();");
						return;
					}
					
				} catch (Exception e) {
					setErrorMessage(e.getMessage());
					RequestContext.getCurrentInstance().execute("error.show();");
					return;
				}
			}
			
			
		}
		
		public void exit(){
			lstPlaceOrderPendingTransction.clear();
			   try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
				} catch (NullPointerException ne) {
					setErrorMessage("Method Name::exit");
					RequestContext.getCurrentInstance().execute("nullPointerId.show();");
					return;
				} catch (Exception exception) {
					setErrorMessage(exception.getMessage());
					RequestContext.getCurrentInstance().execute("error.show();");
					return;
				}  
		}
		
		public void getSelectedRecordstoRemittance(PlaceOrderPendingTransctionDataTable dataTable) throws AMGException
		{
			setPaymentAmountPanel(true);
			if(dataTable.getSelectedrecord())
			{
				BigDecimal transAmount= dataTable.getRemitLocalAmount();
				if(transAmount!=null)
				{
					BigDecimal ltotalAmount= getTotalAmount();
					if(ltotalAmount==null)
					{
						ltotalAmount=BigDecimal.ZERO;
					}
					BigDecimal showTotAmount=transAmount.add(ltotalAmount);
					setTotalAmount(showTotAmount);
					
				}
				List<BigDecimal> lstRatePlaceOrder=getLstRatePlaceOrderPk();
				if(lstRatePlaceOrder==null)
				{
					lstRatePlaceOrder= new ArrayList<BigDecimal>();
				}
				
				lstRatePlaceOrder.add(dataTable.getRateOfferedPk());
				setLstRatePlaceOrderPk(lstRatePlaceOrder);
				setCustomerId(dataTable.getCustomerId());
				System.out.println("Check"+lstRatePlaceOrder);
			}else
			{
				BigDecimal transAmount= dataTable.getRemitLocalAmount();
				if(transAmount!=null)
				{
					BigDecimal ltotalAmount= getTotalAmount();
					if(ltotalAmount==null)
					{
						ltotalAmount=BigDecimal.ZERO;
					}
					BigDecimal showTotAmount=ltotalAmount.subtract(transAmount);
					setTotalAmount(showTotAmount);
					remitEquivalentCounterAmountForDisplay(showTotAmount,dataTable);
				}
				
				List<BigDecimal> lstRatePlaceOrder=getLstRatePlaceOrderPk();
				if(lstRatePlaceOrder==null)
				{
					lstRatePlaceOrder= new ArrayList<BigDecimal>();
				}
				
				lstRatePlaceOrder.remove(dataTable.getRateOfferedPk());
				setLstRatePlaceOrderPk(lstRatePlaceOrder);
				setCustomerId(null);
				System.out.println("Check"+lstRatePlaceOrder);
			}
			if(getTotalAmount()!=null && getTotalAmount().compareTo(BigDecimal.ZERO)==0)
			{
				setPaymentCode(null);
				setLstRatePlaceOrderPk(null);
				setRemitBankId(null);
				setCheckRefNo(null);
				setPaymentCode(null);
				setApprovalNumber(null);
				setNameOfTheCard(null);
				setCardNumber(null);
				setApprovalNumberCard(null);
				setRemitamount(null);
				setRemitApproveCheckDate(null);
				setBooChequePanel(false);
				setBooCardPanel(false);
				setBooRenderSaveOrExit(false);
			}
			
		}
		
		
		public boolean generatePersonalRemittanceReceiptReport(BigDecimal documentNo,BigDecimal docFinaceYear,String emailId) throws Exception{
			//setVisableExceptionDailogForReceipt(false);
			//setExceptionMessageForReport(null);
			boolean rtnCheck=false;
			try {
				fetchRemittanceReceiptReportData(documentNo,docFinaceYear,Constants.DOCUMENT_CODE_FOR_COLLECT_TRANSACTION);

				remittanceReceiptReportInit();
				byte[] pdfasbytes = JasperExportManager.exportReportToPdf(jasperPrint);

				if(emailId!=null)
				{
					//email sending to customer
					sendEmail(pdfasbytes,emailId);
				}else
				{
					HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
					httpServletResponse.setHeader("Cache-Control","cache, must-revalidate");
					httpServletResponse.addHeader("Content-disposition","attachment; filename=RemittanceReceiptReport.pdf");
					/*JasperExportManager.exportReportToPdfStream(jasperPrint,servletOutputStream);
					FacesContext.getCurrentInstance().responseComplete();*/


					OutputStream outstream = httpServletResponse.getOutputStream();
					httpServletResponse.setContentType("application/pdf");
					httpServletResponse.setContentLength(pdfasbytes.length);
					String strSettings = "inline;filename=\"RemittanceReceiptReport.pdf\"";
					httpServletResponse.setHeader("Content-Disposition", strSettings);
					outstream.write(pdfasbytes, 0, pdfasbytes.length);
					outstream.flush();
					FacesContext.getCurrentInstance().responseComplete();
				}

				rtnCheck=true;
			} catch (Exception e) {
				/*setExceptionMessageForReport(null);
				rtnCheck=false;
				//setVisableExceptionDailogForReceipt(true);
				if(e.getMessage()!=null){
					setExceptionMessageForReport(e.getMessage());	
				}else{
					setExceptionMessageForReport("Exception  :"+e);
				}
				RequestContext.getCurrentInstance().execute("exceptioninReceipt.show();");*/
				rtnCheck=false;
				throw new Exception(e.getMessage());
				/*if(i>0){	
					try {
						Thread.sleep(40000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
				i=i+1;*/
			}
			return rtnCheck;
		}

		private void fetchRemittanceReceiptReportData(BigDecimal documentNumber,BigDecimal finaceYear, String documentCode) throws Exception{

			collectionViewList.clear();
			remittanceReceiptSubreportList = new CopyOnWriteArrayList<RemittanceReceiptSubreport>();



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

			List<RemittanceApplicationView> remittanceViewlist = iPersonalRemittanceService.getRecordsForRemittanceReceiptReport(documentNumber,finaceYear,documentCode);

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

					List<PurposeOfRemittanceView>  purposeOfRemittanceList =   iPersonalRemittanceService.getPurposeOfRemittance(view.getDocumentNo(),view.getDocumentFinancialYear());

					/*if(purposeOfRemittanceList!=null && purposeOfRemittanceList.size()>0){
						obj.setPerposeOfRemittance(purposeOfRemittanceList.get(0).getAmiecDescription());
					}*/
					/*String purposeOfRemittance = "";
					for(PurposeOfRemittanceView purposeObj :purposeOfRemittanceList){
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
					// setting customer reference	
					StringBuffer customerReff = new StringBuffer();

					if(view.getCustomerReference() != null){
						customerReff.append(view.getCustomerReference());
						customerReff.append(" / ");
					}

					StringBuffer customerName = new StringBuffer();

					if(view.getFirstName() != null){
						customerName.append(" ");
						customerName.append(view.getFirstName());
						customerReff.append(" ");
						customerReff.append(view.getFirstName());
					}
					if(view.getMiddleName() != null){
						customerName.append(" ");
						customerName.append(view.getMiddleName());
						customerReff.append(" ");
						customerReff.append(view.getMiddleName());
					}
					if(view.getLastName()!=null){
						customerName.append(" ");
						customerName.append(view.getLastName());
						customerReff.append(" ");
						customerReff.append(view.getLastName());
					}

					obj.setFirstName(customerReff.toString());
					setCustomerNameForReport(customerName.toString());

					if(view.getContactNumber()!=null){
						obj.setMobileNo(new BigDecimal(view.getContactNumber().trim()));
					}

					obj.setCivilId(view.getIdentityInt());

					Date sysdate = view.getIdentityExpiryDate();

					if(sysdate!=null){
						obj.setIdExpiryDate(new SimpleDateFormat("dd/MM/yyy").format(sysdate));
					}

					obj.setLocation(session.getLocation());

					//setting receipt Number
					StringBuffer receiptNo = new StringBuffer();

					if(view.getDocumentFinancialYear()!=null){
						receiptNo.append(view.getDocumentFinancialYear());
						receiptNo.append(" / ");
					}
					if(view.getCollectionDocumentNo()!=null){
						receiptNo.append(view.getCollectionDocumentNo());
					}
					obj.setReceiptNo(receiptNo.toString());


					// setting transaction Number
					StringBuffer transactionNo = new StringBuffer();
					if(view.getDocumentFinancialYear()!=null){
						transactionNo.append(view.getDocumentFinancialYear());
						transactionNo.append(" / ");
					}
					if(view.getDocumentNo()!=null){
						transactionNo.append(view.getDocumentNo());
					}
					obj.setTransactionNo(transactionNo.toString());

					obj.setDate(currentDate);
					obj.setBeneficiaryName(view.getBeneficiaryName());
					obj.setBenefeciaryBankName(view.getBeneficiaryBank());
					obj.setBenefeciaryBranchName(view.getBenefeciaryBranch());
					obj.setBenefeciaryAccountNumber(view.getBenefeciaryAccountNo());
					obj.setNoOfTransaction(new BigDecimal(noOfTransactions));
					obj.setPhoneNumber(view.getPhoneNumber());
					obj.setUserName(session.getUserName());
					obj.setPinNo(view.getPinNo() );



					HashMap<String, String> loyaltiPoints  =iPersonalRemittanceService.getloyalityPointsFromProcedure(view.getCustomerReference(), view.getDocumentDate());

					String prLtyStr1 =loyaltiPoints.get("P_LTY_STR1");
					String prLtyStr2 =loyaltiPoints.get("P_LTY_STR2");
					String prInsStr1 =loyaltiPoints.get("P_INS_STR1");
					String prInsStr2 =loyaltiPoints.get("P_INS_STR2");
					String prInsStrAr1 =loyaltiPoints.get("P_INS_STR_AR1");
					String prInsStrAr2 =loyaltiPoints.get("P_INS_STR_AR2");


					StringBuffer loyaltyPoint = new StringBuffer();
					if(!prLtyStr1.trim().equals("")){
						loyaltyPoint.append(prLtyStr1);
					}
					if(!prLtyStr2.trim().equals("")){
						loyaltyPoint.append("\n");
						loyaltyPoint.append(prLtyStr2);
					}
					obj.setLoyalityPointExpiring(loyaltyPoint.toString());

					StringBuffer insurence1 = new StringBuffer();
					if(!prInsStr1.trim().equals("")){
						insurence1.append(prInsStr1);
					}
					if(!prInsStrAr1.trim().equals("")){
						insurence1.append("\n");
						insurence1.append(prInsStrAr1);
					}
					obj.setInsurence1(insurence1.toString());

					StringBuffer insurence2 = new StringBuffer();
					if(!prInsStr2.trim().equals("")){
						insurence2.append(prInsStr2);
					}
					if(!prInsStrAr2.trim().equals("")){
						insurence2.append(prInsStrAr2);
					}
					obj.setInsurence2(insurence2.toString());


					// setting beneficiary Address
					StringBuffer  address = new StringBuffer();
					if(view.getBeneStateName() != null){
						address.append(view.getBeneStateName());
						address.append(",  ");
					}
					if(view.getBeneCityName() != null){
						address.append(view.getBeneCityName());
						address.append(",  ");
					}
					if(view.getBeneDistrictName() != null){
						address.append(view.getBeneDistrictName());
					}
					obj.setAddress(address.toString());

					//setting payment channel 
					StringBuffer paymentChannel = new StringBuffer();
					if(view.getRemittanceDescription() != null){
						paymentChannel.append(view.getRemittanceDescription());
						paymentChannel.append(" - ");
					}
					if(view.getDeliveryDescription() != null){
						paymentChannel.append(view.getDeliveryDescription());
					}
					obj.setPaymentChannel(paymentChannel.toString());

					String currencyAndAmount=null;
					BigDecimal foreignTransationAmount=GetRound.roundBigDecimal((view.getForeignTransactionAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getForeignCurrencyId()));
					if(view.getCurrencyQuoteName()!=null && foreignTransationAmount!=null){
						currencyAndAmount = view.getCurrencyQuoteName()+"     ******"+foreignTransationAmount;
					}
					obj.setCurrencyAndAmount(currencyAndAmount);

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


					remittanceApplList.add(obj);
				}

				// for foreign currency Sale report
				for (RemittanceApplicationView view : fcsaleList) {

					RemittanceReportBean obj = new RemittanceReportBean();

					// setting customer reference	
					StringBuffer customerReff = new StringBuffer();

					if(view.getCustomerReference() != null){
						customerReff.append(view.getCustomerReference());
						customerReff.append(" / ");
					}

					StringBuffer customerName = new StringBuffer();

					if(view.getFirstName() != null){
						customerName.append(" ");
						customerName.append(view.getFirstName());
						customerReff.append(" ");
						customerReff.append(view.getFirstName());
					}
					if(view.getMiddleName() != null){
						customerName.append(" ");
						customerName.append(view.getMiddleName());
						customerReff.append(" ");
						customerReff.append(view.getMiddleName());
					}
					if(view.getLastName()!=null){
						customerName.append(" ");
						customerName.append(view.getLastName());
						customerReff.append(" ");
						customerReff.append(view.getLastName());
					}

					obj.setFirstName(customerReff.toString());
					setCustomerNameForReport(customerName.toString());

					if(view.getContactNumber()!=null){
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


					StringBuffer loyaltyPoint = new StringBuffer();

					if(!prLtyStr1.trim().equals("")){
						loyaltyPoint.append(prLtyStr1);
					}
					if(!prLtyStr2.trim().equals("")){
						loyaltyPoint.append("\n");
						loyaltyPoint.append(prLtyStr2);
					}
					obj.setLoyalityPointExpiring(loyaltyPoint.toString());

					StringBuffer insurence = new StringBuffer();

					if(!prInsStr1.trim().equals("")){
						insurence.append(prInsStr1);
					}
					if(prInsStrAr1.trim().equals("")){
						insurence.append("\n");
						insurence.append(prInsStrAr1);
					}

					if(!prInsStr2.trim().equals("")){
						insurence.append("\n");
						insurence.append(prInsStr2);
					}
					if(!prInsStrAr2.trim().equals("")){
						insurence.append("\n");
						insurence.append(prInsStrAr2);
					}
					obj.setInsurence1(insurence.toString());


					obj.setLocation(view.getCountryBranchName());
					obj.setPhoneNumber(view.getPhoneNumber());
					obj.setDate(currentDate);
					obj.setUserName(session.getUserName());

					//setting receipt Number
					StringBuffer receiptNo = new StringBuffer();
					if(view.getDocumentFinancialYear()!=null){
						receiptNo.append(view.getDocumentFinancialYear());
						receiptNo.append(" / ");
					}
					if(view.getCollectionDocumentNo()!=null){
						receiptNo.append(view.getCollectionDocumentNo());
					}
					obj.setReceiptNo(receiptNo.toString());

					// setting transaction Number
					StringBuffer transactionNo = new StringBuffer();
					if(view.getDocumentFinancialYear()!=null){
						transactionNo.append(view.getDocumentFinancialYear());
						transactionNo.append(" / ");
					}
					if(view.getDocumentNo()!=null){
						transactionNo.append(view.getDocumentNo());
					}
					obj.setTransactionNo(transactionNo.toString());

					if(view.getForeignCurrencyId()!=null){
						String saleCurrency = specialCustomerDealRequestService.getCurrencyForUpdate(view.getForeignCurrencyId());
						obj.setCurrencyQuoteName(saleCurrency);
					}
					String saleCurrencyCode =null;

					if(view.getForeignCurrencyId()!=null){
						saleCurrencyCode = generalService.getCurrencyQuote(view.getForeignCurrencyId());
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

					/*List<PurposeOfRemittanceView>  purposeOfRemittanceList =   iPersonalRemittanceService.getPurposeOfRemittance(view.getDocumentNo(),view.getDocumentFinancialYear());

					if(purposeOfRemittanceList!=null && purposeOfRemittanceList.size()>0){
						obj.setPerposeOfRemittance(purposeOfRemittanceList.get(0).getAmiecDescription());
					}*/
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
					obj.setUserName(session.getUserName());

					//	obj.setSignature(view.getCustomerSignature());

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


					fcsaleAppList.add(obj);


				}

				//for Main Remittance Receipt report (Remittance Receipt and Fc Sale Application)
				RemittanceReceiptSubreport remittanceObj = new RemittanceReceiptSubreport();

				remittanceObj.setWaterMarkLogoPath(waterMark);
				remittanceObj.setWaterMarkCheck(false);
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

		public void remittanceReceiptReportInit() throws JRException {

			JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(remittanceReceiptSubreportList);
			ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
			String realPath = ctx.getRealPath("//");
			String reportPath = realPath +"\\reports\\design\\RemittanceReceiptNewReport.jasper";
			//		String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("reports/design/RemittanceReceiptNewReport.jasper");
			jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap(),beanCollectionDataSource);


		}
		
		public void sendEmail(byte[] pdfasbytes,String emailId) throws Exception{

			String from = session.getEmail();
			String subject = "Remittance Receipt Report From Al-Mulla Exchange";
			try {
				List<ApplicationSetup> lstApplicationSetup=iPersonalRemittanceService.getEmailFromAppSetup(session.getCompanyId(), session.getCountryId());

				if(emailId!=null && !emailId.equals("")){
					mailService.sendMailToCustomerWithAttachment(lstApplicationSetup, emailId, subject, pdfasbytes,getCustomerNameForReport());
				}
			} catch (AMGException e) {
				 throw new Exception();
			}

		}
		
		public List<RemittanceReportBean> calculateCollectionMode(RemittanceApplicationView viewCollectionObj){	
			List<RemittanceReportBean> collectionDetailList = new ArrayList<RemittanceReportBean>();
			List<CollectionPaymentDetailsView> collectionPaymentDetailList= iPersonalRemittanceService.getCollectionPaymentDetailForRemittanceReceipt(viewCollectionObj.getCompanyId(),viewCollectionObj.getCollectionDocumentNo(),viewCollectionObj.getCollectionDocFinanceYear(),viewCollectionObj.getCollectionDocCode());

			int size = collectionPaymentDetailList.size();
			for(CollectionPaymentDetailsView viewObj: collectionPaymentDetailList){
				RemittanceReportBean obj = new RemittanceReportBean();
				if(viewObj.getCollectionMode()!=null && viewObj.getCollectionMode().equalsIgnoreCase("K")){
					obj.setCollectionMode(viewObj.getCollectionModeDesc());
					obj.setApprovalNo(viewObj.getApprovalNo());
					obj.setTransactionId(viewObj.getTransactionId());
					obj.setKnetreceiptDateTime(viewObj.getKnetReceiptDatenTime());
					obj.setKnetBooleanCheck(true);
					if(viewObj.getCollectAmount()!=null && viewCollectionObj.getLocalTransactionCurrencyId()!=null){
						BigDecimal collectAmount=GetRound.roundBigDecimal((viewObj.getCollectAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(viewCollectionObj.getLocalTransactionCurrencyId()));
						obj.setCollectAmount(collectAmount);
					}
				}else{
					obj.setCollectionMode(viewObj.getCollectionModeDesc());
					obj.setKnetBooleanCheck(false);
					if(viewObj.getCollectAmount()!=null && viewCollectionObj.getLocalTransactionCurrencyId()!=null){
						BigDecimal collectAmount=GetRound.roundBigDecimal((viewObj.getCollectAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(viewCollectionObj.getLocalTransactionCurrencyId()));
						obj.setCollectAmount(collectAmount);
					}
				}
				if(size>1){
					obj.setDrawLine(true);
				}else{
					obj.setDrawLine(false);
				}
				collectionDetailList.add(obj);
				size = size-1;
			}
			return collectionDetailList;
		}

		//populate Customer details
		public void populateCustomerDetails() {

			BigDecimal cardlength = BigDecimal.ZERO;

			localbankListForBankCode.clear();

			lstDebitCard.clear();
			setColAuthorizedby(null);
			setCardNumber(null);
			setColCardNoLength(null);
			setPopulatedDebitCardNumber(null);
			setNameOfTheCard(null);
			setApprovalNumberCard(null);
			if (getCardBankId() != null) {
				localbankListForBankCode = icustomerBankService.getCustomerLocalBankListFromView(session.getCountryId(), getCardBankId());

				List<CustomerBank> localBankListinCollection = icustomerBankService.fetchcustomerBanksDetails(getCustomerId(), getCardBankId());

				if(localbankListForBankCode.size() != 0){
					ViewBankDetails bankLength = localbankListForBankCode.get(0);
					cardlength = bankLength.getDebitCardLength();
					System.out.println("cardlength :" + cardlength);
				}

				if (localBankListinCollection.size() != 0) {
					if (cardlength.compareTo(BigDecimal.ZERO) != 0) {
						setColCardNoLength(cardlength);
						if (localBankListinCollection.size() == 1) {
							for (CustomerBank customerBank : localBankListinCollection) {
								if (customerBank.getBankCode().equals(getCardBankId())) {
									setPopulatedDebitCardNumber(new BigDecimal(encryptionDescriptionService.getDECrypted(customerBank.getDebitCardName(),customerBank.getDebitCard())));
									setCardNumber(new BigDecimal(encryptionDescriptionService.getDECrypted(customerBank.getDebitCardName(),customerBank.getDebitCard())));
									setNameOfTheCard(customerBank.getDebitCardName());
									if (customerBank.getAuthorizedBy() != null) {
										//List<Employee> localEmpllist = generalService.getEmployeelist(sessionStateManage.getCountryId(),new BigDecimal(sessionStateManage.getBranchId()),new BigDecimal(sessionStateManage.getRoleId()));

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
									lstofDebitCard.setDebitCard(encryptionDescriptionService.getDECrypted(lstDebitcrd.getDebitCardName(),lstDebitcrd.getDebitCard()));
									/**Added by Rabil on 13/01/2016 **/	
									lstofDebitCard.setDebitCardName(lstDebitcrd.getDebitCardName());
									/**End by Rabil on 13/01/2016 **/	
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
							setCardNumber(null);
							setPopulatedDebitCardNumber(null);
							setNameOfTheCard(null);
							setColpassword(null);
							setApprovalNumberCard(null);
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
				setCardNumber(null);
				setColCardNoLength(null);
				setPopulatedDebitCardNumber(null);
				setNameOfTheCard(null);
				setColpassword(null);
				setApprovalNumberCard(null);
				setBooAuthozed(false);
				setBooRenderSingleDebit(true);
				setBooRenderMulDebit(false);
			}
		}
		
		public void populateCustKnetCardDetails() {
			for (CustomerBank customerBank : lstDebitCard) {
				if (customerBank.getDebitCard().equalsIgnoreCase(
						getCardNumber().toPlainString())) {
					if (customerBank.getAuthorizedBy() != null) {
						/*List<Employee> localEmpllist = generalService.getEmployeelist(sessionStateManage.getCountryId(),new BigDecimal(sessionStateManage.getBranchId()),new BigDecimal(sessionStateManage.getRoleId()));*/

						List<DebitAutendicationView> localEmpllist = iPersonalRemittanceService.getdebitAutendicationList();
						setEmpllist(localEmpllist);
						setColAuthorizedby(customerBank.getAuthorizedBy());
						setColpassword(null);
						setBooAuthozed(true);
						break;
					} else {
						setNameOfTheCard(customerBank.getDebitCardName());
						setColAuthorizedby(null);
						setColpassword(null);
						setBooAuthozed(false);
					}
				}
			}
		}
		
		public void editDebitCardtoEnter() {
			setBooRenderSingleDebit(true);
			setBooRenderMulDebit(false);
			setNameOfTheCard(null);
			setPopulatedDebitCardNumber(null);
		}
		
		// checking customer name and debit card name of card
		public void firstNameCheck() {
			if (getNameOfTheCard().equalsIgnoreCase(getCustomerFullName())) {
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
		
		//Knet Variables
		private List<CustomerBank> lstDebitCard=new ArrayList<CustomerBank>();
		private List<DebitAutendicationView> empllist=new ArrayList<DebitAutendicationView>();
		private List<ViewBankDetails> localbankListForBankCode=new ArrayList<ViewBankDetails>();
		private Boolean booRenderSingleDebit=false;
		private Boolean booRenderMulDebit=false;
		private BigDecimal colCardNoLength;
		private Boolean booAuthozed=false;
		private String colAuthorizedby;
		private String colpassword;
		private String customerFullName;
		private BigDecimal populatedDebitCardNumber;
		private Boolean booColApprovalNo=false;

		public List<CustomerBank> getLstDebitCard() {
			return lstDebitCard;
		}

		public void setLstDebitCard(List<CustomerBank> lstDebitCard) {
			this.lstDebitCard = lstDebitCard;
		}

		public List<DebitAutendicationView> getEmpllist() {
			return empllist;
		}

		public void setEmpllist(List<DebitAutendicationView> empllist) {
			this.empllist = empllist;
		}

		public Boolean getBooRenderSingleDebit() {
			return booRenderSingleDebit;
		}

		public void setBooRenderSingleDebit(Boolean booRenderSingleDebit) {
			this.booRenderSingleDebit = booRenderSingleDebit;
		}

		public Boolean getBooRenderMulDebit() {
			return booRenderMulDebit;
		}

		public void setBooRenderMulDebit(Boolean booRenderMulDebit) {
			this.booRenderMulDebit = booRenderMulDebit;
		}

		public BigDecimal getColCardNoLength() {
			return colCardNoLength;
		}

		public void setColCardNoLength(BigDecimal colCardNoLength) {
			this.colCardNoLength = colCardNoLength;
		}

		public Boolean getBooAuthozed() {
			return booAuthozed;
		}

		public void setBooAuthozed(Boolean booAuthozed) {
			this.booAuthozed = booAuthozed;
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

		public String getCustomerFullName() {
			return customerFullName;
		}

		public void setCustomerFullName(String customerFullName) {
			this.customerFullName = customerFullName;
		}

		public BigDecimal getPopulatedDebitCardNumber() {
			return populatedDebitCardNumber;
		}

		public void setPopulatedDebitCardNumber(BigDecimal populatedDebitCardNumber) {
			this.populatedDebitCardNumber = populatedDebitCardNumber;
		}

		public List<ViewBankDetails> getLocalbankListForBankCode() {
			return localbankListForBankCode;
		}

		public void setLocalbankListForBankCode(
				List<ViewBankDetails> localbankListForBankCode) {
			this.localbankListForBankCode = localbankListForBankCode;
		}
		
		
		public Boolean getBooColApprovalNo() {
			return booColApprovalNo;
		}

		public void setBooColApprovalNo(Boolean booColApprovalNo) {
			this.booColApprovalNo = booColApprovalNo;
		}

		@Autowired
		IEncrptionDescriptionService<T> encryptionDescriptionService;
		
		public void clearKnetDetails(){
			lstDebitCard.clear();
			empllist.clear();
			localbankListForBankCode.clear();
			setBooRenderSingleDebit(true);
			setBooRenderMulDebit(false);
			setColCardNoLength(null);
			setBooAuthozed(false);
			setColAuthorizedby(null);
			setColpassword(null);
			setCustomerFullName(null);
			setPopulatedDebitCardNumber(null);
			setBooColApprovalNo(false);
			setCardNumber(null);
			setNameOfTheCard(null);
			setApprovalNumberCard(null);
			setKnetRecieptNo(null);
		}
		
		
		/** populate new bankList for Customer **/
		public void addNewBenificiary() {
			setColBankCode(null);
			setCardNumber(null);
			setPopulatedDebitCardNumber(null);
			setApprovalNumberCard(null);
			setRemitamount(null);
			setColAuthorizedby(null);
			setColpassword(null);
			setApprovalNumber(null);
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
		//add new Knet Card Details
		boolean checkKnetAmount ;
		private BigDecimal errcolCashExistsLimit;
		private BigDecimal colamountKWD = new BigDecimal(0);
		private BigDecimal calNetAmountPaid;
		private String excheckCashLimitMessage = null;
		private String cyberPassword;
		
		@Autowired
		IGLTransactionForADocument iglTransactionForADocument;
		
		public BigDecimal getErrcolCashExistsLimit() {
			return errcolCashExistsLimit;
		}

		public void setErrcolCashExistsLimit(BigDecimal errcolCashExistsLimit) {
			this.errcolCashExistsLimit = errcolCashExistsLimit;
		}
		

		public BigDecimal getColamountKWD() {
			return colamountKWD;
		}

		public void setColamountKWD(BigDecimal colamountKWD) {
			this.colamountKWD = colamountKWD;
		}
		

		public BigDecimal getCalNetAmountPaid() {
			return calNetAmountPaid;
		}

		public void setCalNetAmountPaid(BigDecimal calNetAmountPaid) {
			this.calNetAmountPaid = calNetAmountPaid;
		}
		

		public String getExcheckCashLimitMessage() {
			return excheckCashLimitMessage;
		}

		public void setExcheckCashLimitMessage(String excheckCashLimitMessage) {
			this.excheckCashLimitMessage = excheckCashLimitMessage;
		}
		

		public String getCyberPassword() {
			return cyberPassword;
		}

		public void setCyberPassword(String cyberPassword) {
			this.cyberPassword = cyberPassword;
		}

		public void checkcashcollection(){
			checkKnetAmount = false;
			checkingKnetExtraAmount();
		}
		
		// Knet 5% calculation
		public void checkingKnetExtraAmount(){
			BigDecimal totalAmount = BigDecimal.ZERO;
			setErrcolCashExistsLimit(null);
			if(getPaymentCode() != null){
				if(getPaymentCode().equalsIgnoreCase(Constants.KNETCode)){
					BigDecimal colKnetAmount = BigDecimal.ZERO;
					
					colKnetAmount = colKnetAmount.add(getRemitamount());
					BigDecimal percentage = new BigDecimal(5).divide(new BigDecimal(100));
					BigDecimal percentageAmount = percentage.multiply(getRemitamount());
					totalAmount = GetRound.roundBigDecimal(percentageAmount.add(getRemitamount()), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(session.getCurrencyId())));

					if (colKnetAmount.compareTo(totalAmount) <= 0) {
						setColamountKWD(getRemitamount());
					} else {
						setRemitamount(null);
						setErrcolCashExistsLimit(totalAmount);
						RequestContext.getCurrentInstance().execute("amountgreater.show();");
						checkKnetAmount=true;
					}
				}else if(getPaymentCode().equalsIgnoreCase(Constants.ChequeCode)){
					setColamountKWD(getRemitamount());
				}else{
					System.out.println("Other Payment Mode Selected");
				}
			}else{
				setColamountKWD(getRemitamount());
			}
		}
		public boolean verifyNewUserDetails(){

			boolean rtnCheck=false;
			checkcashcollection();

			if(!checkKnetAmount){


				String errorMessage;
				try {
					errorMessage = iPersonalRemittanceService.getExCheckCashLimitProcedure(session.getCountryId(),getCustomerId(), getPaymentmodeId(),getColamountKWD());
					System.out.println("errorMessage :" + errorMessage);
					if (errorMessage != null && !errorMessage.equals("")) {
						setExcheckCashLimitMessage(errorMessage);
						rtnCheck=false;
						RequestContext.getCurrentInstance().execute("exCheckCashLimit.show();");
						return rtnCheck;
					} else {
						setExcheckCashLimitMessage(null);

						if(getPaymentCode() != null){
							List<PaymentMode> paymentModedetails = ipaymentService.getPaymentCheck(getPaymentCode());

							if (paymentModedetails.size() != 0) {

								 if(getPaymentCode().equalsIgnoreCase(Constants.KNETCode)){
									if (getColAuthorizedby() != null) {
										List<DebitAutendicationView> lstEmpLogin = new ArrayList<DebitAutendicationView>();
										String userNames = getColAuthorizedby();
										
										lstEmpLogin = 	iPersonalRemittanceService.getdebitAutendicationListByUserId(getColAuthorizedby(),getColpassword());				

										if (lstEmpLogin.size() != 0) {
											checkingPaymentCardinDB();
										} else {
											setColpassword(null);
											rtnCheck=false;
											RequestContext.getCurrentInstance().execute("passwordcheck.show();");
											return rtnCheck;
										}
									} else {
										checkingPaymentCardinDB();
									}

								}else if(getPaymentCode().equalsIgnoreCase(Constants.ChequeCode)){

									/*Boolean checkdata = checkingChequeDuplicateCheck();

									if(checkdata){
										//setBooRendercollectiondatatable(true);
										//clearingDetailAfterAdding();
									}else{
										RequestContext.getCurrentInstance().execute("chequerefexists.show();");
									}*/
								}else{
									System.out.println("Other Payment Mode");
								}

							}
						}else{
							setBooChequePanel(false);
							setBooCardPanel(false);
						}
					}
					rtnCheck=true;
				} catch (AMGException e) {
					setExceptionMessage(e.getMessage());
					rtnCheck=false;
					RequestContext.getCurrentInstance().execute("sqlexception.show();");
					return rtnCheck;
				}
			}
			return rtnCheck;
		}
		
		public void checkingPaymentCardinDB(){
			if (lstDebitCard.size() != 0) {
				int i = 0;
				for (CustomerBank lstDebit : lstDebitCard) {
					if (lstDebit.getDebitCard().equalsIgnoreCase(getCardNumber().toString())) {
						i = 1;
						break;
					} else {
						i = 0;
					}
				}
				if (i == 0) {
					saveCustomerDetailsToCustomerBank();
				} /*else {
					clearKnetDetails();
				}*/
			} else {
				if (getPopulatedDebitCardNumber() != null) {
					if (getPopulatedDebitCardNumber().compareTo(getCardNumber()) == 0) {
						//clearKnetDetails();
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
		
		public void saveCustomerDetailsToCustomerBank(){
			CustomerBank customerBank = new CustomerBank();

			customerBank.setCollectionMode(Constants.COLLECTIONMODE);

			if(localbankListForBankCode.size() != 0){
				BankMaster bankMaster = new BankMaster();
				bankMaster.setBankId(localbankListForBankCode.get(0).getChequeBankId()); 
				customerBank.setFsBankMaster(bankMaster);
			}

			customerBank.setBankCode(getCardBankId()); // this is fixed//generalService.getBankCode(getColBankid()));

			Customer customer = new Customer();
			customer.setCustomerId(getCustomerId());
			customerBank.setFsCustomer(customer);
			customerBank.setAuthorizedBy(getColAuthorizedby());
			customerBank.setAuthorizedDate(new Date());
			customerBank.setDebitCard(encryptionDescriptionService.getENCrypted(getNameOfTheCard(), getCardNumber().toString()));
			customerBank.setDebitCardName(getNameOfTheCard());
			customerBank.setPassword(getCyberPassword());
			customerBank.setIsActive(Constants.Yes);
			customerBank.setCreatedBy(session.getUserName());
			customerBank.setCreatedDate(new Date());
			customerBank.setCustomerReference(iglTransactionForADocument.getCustomeReference(getCustomerId()));
			// customerBank.setModifiedBy(null);
			// customerBank.setModifiedDate(null);
			icustomerBankService.save(customerBank);
			//calculatingNetAmountDT();
			//clearKnetDetails();
		}
		
		// checking cheque data with datatable
		/*public Boolean checkingChequeDuplicateCheck(){
			Boolean checkCheque = false;
				checkCheque = true;	
			return checkCheque;
		}*/
		
		
		
		
		/*public void calculatingNetAmountDT(){
			int i = 0;
			BigDecimal paymentModeCashId = ipaymentService.fetchPaymodeMasterId(Constants.CASHNAME,new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId") : "1"));
			if (paymentModeCashId != null) {
				boolean flag = true;
				//setBooRendercollectiondatatable(true);
				//setBoRenderTotalAmountCalPanel(true);
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
				}t

				if (coldatatablevalues.size() > 0 && flag == true) {
					for (PersonalRemittanceCollectionDataTable collectionlst : coldatatablevalues) {
						// totalUamount=totalUamount+collectionlst.getColAmountDT().intValue();
						totalUamount = totalUamount.add(collectionlst.getColAmountDT());
					}
					setToalUsedAmount(totalUamount);
					if(getToalUsedAmount().compareTo(getCalNetAmountPaid())>0){
						setTotalbalanceAmount(BigDecimal.ZERO);
						setTotalrefundAmount(getToalUsedAmount().subtract(getCalNetAmountPaid()));t
					}else if(getToalUsedAmount().compareTo(getCalNetAmountPaid())<0){
						setTotalbalanceAmount(getCalNetAmountPaid().subtract(getToalUsedAmount()));
						setTotalrefundAmount(BigDecimal.ZERO);
					}else{
						setTotalbalanceAmount(BigDecimal.ZERO);
						setTotalrefundAmount(BigDecimal.ZERO);
					}
				}

				clearingDetailAfterAdding();
			}
		}*/
		
		public HashMap<String , String> remitEquivalentCounterAmountForDisplay(BigDecimal remitAmount,PlaceOrderPendingTransctionDataTable dataTable) throws AMGException
		{
			HashMap<String , String> rtnMap= new HashMap<String , String>();

			setEquivalentRemitAmount(null);
			setEquivalentCurrency(null);
			if(remitAmount != null){
				HashMap<String, String> hmoutPutValues = getRemitEquivalentAmtForSpecialRate(dataTable,remitAmount);
				if(hmoutPutValues!=null){
					setEquivalentRemitAmount(new BigDecimal(hmoutPutValues.get("P_EQUIV_GROSS_AMOUNT")));
					if(hmoutPutValues.get("P_EQUIV_CURRENCY_ID")!=null && !hmoutPutValues.get("P_EQUIV_CURRENCY_ID").equals("0")){
						setEquivalentCurrency("["+generalService.getCurrencyQuote(new BigDecimal(hmoutPutValues.get("P_EQUIV_CURRENCY_ID")))+"]"); 
					}
				}
			}else{
				setEquivalentRemitAmount(null);
			}
			
			rtnMap.put("EquivalentRemitAmount", getEquivalentRemitAmount()== null ? "0": getEquivalentRemitAmount().toPlainString());
			rtnMap.put("EquivalentCurrency", getEquivalentCurrency()== null ? "0": getEquivalentCurrency());
			
			return rtnMap;
		}


		private HashMap<String, String> getRemitEquivalentAmtForSpecialRate(PlaceOrderPendingTransctionDataTable dataTable,BigDecimal remitAmount) throws AMGException
		{
			HashMap<String, String> inputValues = new HashMap<String, String>();

			inputValues.put("P_APPLICATION_COUNTRY_ID", session.getCountryId() == null ? "0" : session.getCountryId().toString());
			inputValues.put("P_ROUTING_COUNTRY_ID", dataTable.getRoutingCountry() == null ? "0" : dataTable.getRoutingCountry().toString());
			inputValues.put("P_BRANCH_ID", session.getBranchId());
			inputValues.put("P_COMPANY_ID", session.getCompanyId() == null ? "0" : session.getCompanyId().toString());
			inputValues.put("P_ROUTING_BANK_ID", dataTable.getRoutingBank() == null ? "0" :  dataTable.getRoutingBank().toString());
			inputValues.put("P_SERVICE_MASTER_ID", dataTable.getRemitServiceId() == null ? "0" : dataTable.getRemitServiceId().toString());
			inputValues.put("P_DELIVERY_MODE_ID", dataTable.getRemitDeliveryId()==null ? "0" : dataTable.getRemitDeliveryId().toString());
			inputValues.put("P_REMITTANCE_MODE_ID", dataTable.getRemitRemittanceId()==null ? "0" : dataTable.getRemitRemittanceId().toString());
			
			List<PopulateData> lstofCurrency = new ArrayList<PopulateData>();
			
			lstofCurrency= iPlaceOnOrderCreationService.getBasedOnCountyCurrency(dataTable.getBeneficiaryCountryId());
			if(lstofCurrency!=null && lstofCurrency.size()>0)
			{
				inputValues.put("P_FOREIGN_CURRENCY_ID", lstofCurrency.get(0).getPopulateId() == null ? "0" : lstofCurrency.get(0).getPopulateId().toString());
				
			/*	if(lstofCurrency.get(0).getPopulateId().compareTo(dataTable.getCurrencyId())==0)
				{
					inputValues.put("P_SELECTED_CURRENCY_ID", session.getCurrencyId() == null ? "0" : session.getCurrencyId());
				}else
				{
					inputValues.put("P_SELECTED_CURRENCY_ID", dataTable.getCurrencyId() == null ? "0" : dataTable.getCurrencyId().toString());
				}*/
				inputValues.put("P_SELECTED_CURRENCY_ID", dataTable.getCurrencyId() == null ? "0" : dataTable.getCurrencyId().toString());
				
			}
			
			
			//inputValues.put("P_CUSTOMER_ID", getCustomerNo() == null ? "0" : getCustomerNo().toString());
			inputValues.put("P_CUSTOMER_TYPE", Constants.Individual);
			//inputValues.put("P_LOYALTY_POINTS_IND", getAvailLoyaltyPoints());

			// special Deal Ind Changed to special Deal Rate
			//inputValues.put("P_SPECIAL_DEAL_RATE",getSpecialDealRate() == null ? "0" : getSpecialDealRate().toString());
			//inputValues.put("P_OVERSEAS_CHRG_IND", getChargesOverseas());
			inputValues.put("P_SELECTED_CURRENCY_AMOUNT", remitAmount == null ? "0": remitAmount.toString());
			//spot Rate Ind Changed to spot Exchange Rate
			/*inputValues.put("P_SPOT_RATE", getSpotExchangeRate() == null ? "0": getSpotExchangeRate().toString());
			inputValues.put("P_CASH_ROUND_IND", getCashRounding());
			inputValues.put("P_ROUTING_BANK_BRANCH_ID", getRoutingBranch() == null ? "0" : getRoutingBranch().toString());
			inputValues.put("P_BENE_ID", getMasterId() == null ? "0" : getMasterId().toString());
			inputValues.put("P_BENE_COUNTRY_ID", getDatabenificarycountry() == null ? "0" : getDatabenificarycountry().toString());
			inputValues.put("P_BENE_BANK_ID", getBeneficaryBankId() == null ? "0" : getBeneficaryBankId().toString());
			inputValues.put("P_BENE_BANK_BRANCH_ID",getBeneficaryBankBranchId() == null ? "0" : getBeneficaryBankBranchId().toString());
			inputValues.put("P_BENE_ACCOUNT_NO",getDataAccountnum());*/

			HashMap<String, String> outputValues = iPersonalRemittanceService.getRemitExchangeEquivalentAount(inputValues);

			return outputValues;
		}
		
}
