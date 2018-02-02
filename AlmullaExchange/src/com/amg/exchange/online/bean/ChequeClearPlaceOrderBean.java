package com.amg.exchange.online.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.foreigncurrency.service.IForeignCurrencyPurchaseService;
import com.amg.exchange.online.service.IBranchStaffGSMRateService;
import com.amg.exchange.online.service.IChequeClearPlaceOrderService;
import com.amg.exchange.online.service.IGSMPlaceOrderRateFeedService;
import com.amg.exchange.online.service.IPlaceAnOrderCreationService;
import com.amg.exchange.online.service.IPlaceOrderPendingTransctionService;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.service.ICustomerRegistrationBranchService;
import com.amg.exchange.remittance.bean.PopulateData;
import com.amg.exchange.remittance.model.RemittanceApplication;
import com.amg.exchange.remittance.service.ICustomerBankService;
import com.amg.exchange.remittance.service.IPaymentService;
import com.amg.exchange.remittance.service.IPersonalRemittanceService;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;
@Component("chequeClearPlaceOrderBean")
@Scope("session")
public class ChequeClearPlaceOrderBean<T> implements Serializable{


	private static final long serialVersionUID = 1L;
	private static final Logger log=Logger.getLogger(ChequeClearPlaceOrderBean.class);
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
	private BigDecimal cardBankId;
	private BigDecimal cardNumber;
	private String nameOfTheCard;
	private BigDecimal approvalNumberCard;
	
	//cheque Details For DB
		private String chequeIndicator;
		private BigDecimal chequeBankCode;
		private String chequeReference;
		private Date chequeDate;
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
	private Boolean booRenderDataTable=false;
	private String procedureError;
	
	SessionStateManage session= new SessionStateManage();
	//Search Customer Variables
	private BigDecimal customerId;
	private String customerName;
	private ChequeClearPlaceOrderDataTable chequeClearPlaceOrderDataTable;
	
	
	private List<ChequeClearPlaceOrderDataTable> lstChequeClearPlaceOrderDataTable=new ArrayList<ChequeClearPlaceOrderDataTable>();
	private List<BigDecimal> lstPkUpdates=new ArrayList<BigDecimal>();
	/*private List<SourceOfIncomeDescription> lstSourceofIncome=new ArrayList<SourceOfIncomeDescription>();
	private List<PaymentModeDesc> lstFetchAllPayMode=new ArrayList<PaymentModeDesc>();
	private List<AddDynamicLebel> listDynamicLebel = new ArrayList<AddDynamicLebel>();
	private List<AddAdditionalBankData> listAdditionalBankDataTable = new ArrayList<AddAdditionalBankData>();
	private List<ViewBankDetails> bankMasterList = new CopyOnWriteArrayList<ViewBankDetails>();
	private List<ViewBankDetails> chequebankMasterList = new ArrayList<ViewBankDetails>();
	private List<ViewBankDetails> localbankList = new ArrayList<ViewBankDetails>();*/
	
	//service Declarations
	@Autowired
	IBranchStaffGSMRateService branchStaffGSMRateService;
	
	@Autowired
	ICustomerRegistrationBranchService<T> icustomerRegistrationService;
	
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
	IChequeClearPlaceOrderService chequeClearPlaceOrderService;
	
	@Autowired
	private ApplicationContext appContext;
	
	@Autowired
	IPlaceAnOrderCreationService  iPlaceOnOrderCreationService;
	
	
	
	public ChequeClearPlaceOrderDataTable getChequeClearPlaceOrderDataTable() {
		return chequeClearPlaceOrderDataTable;
	}

	public void setChequeClearPlaceOrderDataTable(
			ChequeClearPlaceOrderDataTable chequeClearPlaceOrderDataTable) {
		this.chequeClearPlaceOrderDataTable = chequeClearPlaceOrderDataTable;
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

	public List<ChequeClearPlaceOrderDataTable> getLstChequeClearPlaceOrderDataTable() {
		return lstChequeClearPlaceOrderDataTable;
	}

	public void setLstChequeClearPlaceOrderDataTable(
			List<ChequeClearPlaceOrderDataTable> lstChequeClearPlaceOrderDataTable) {
		this.lstChequeClearPlaceOrderDataTable = lstChequeClearPlaceOrderDataTable;
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
	
	public BigDecimal getCardBankId() {
		return cardBankId;
	}

	public void setCardBankId(BigDecimal cardBankId) {
		this.cardBankId = cardBankId;
	}
	
	
	public String getColBankCode() {
		return colBankCode;
	}

	public void setColBankCode(String colBankCode) {
		this.colBankCode = colBankCode;
	}
	
	public Boolean getBooRenderDataTable() {
		return booRenderDataTable;
	}

	public void setBooRenderDataTable(Boolean booRenderDataTable) {
		this.booRenderDataTable = booRenderDataTable;
	}
	
	public String getChequeIndicator() {
		return chequeIndicator;
	}

	public void setChequeIndicator(String chequeIndicator) {
		this.chequeIndicator = chequeIndicator;
	}

	public BigDecimal getChequeBankCode() {
		return chequeBankCode;
	}

	public void setChequeBankCode(BigDecimal chequeBankCode) {
		this.chequeBankCode = chequeBankCode;
	}

	public String getChequeReference() {
		return chequeReference;
	}

	public void setChequeReference(String chequeReference) {
		this.chequeReference = chequeReference;
	}

	public Date getChequeDate() {
		return chequeDate;
	}

	public void setChequeDate(Date chequeDate) {
		this.chequeDate = chequeDate;
	}
	
	public List<BigDecimal> getLstPkUpdates() {
		return lstPkUpdates;
	}

	public void setLstPkUpdates(List<BigDecimal> lstPkUpdates) {
		this.lstPkUpdates = lstPkUpdates;
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	//Page Navigation
	public void pageNavigationchequeClearPlaceOrder(){
		lstChequeClearPlaceOrderDataTable.clear();
		clearAllFields();
		setBooRenderDataTable(false);
		setBooRenderSaveOrExit(false);
		tofetchAllRecords();
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "chequeClearPlaceOrder.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../onlinespecialrate/chequeClearPlaceOrder.xhtml");
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
		//cheque clear
		setCheckRefNo(null);
		setChequeBankCode(null);
		setChequeIndicator(null);
		setChequeDate(null);
		setChequeClearPlaceOrderDataTable(null);
	}
	//view
	public void toFetchRecordsCheque(){
		lstChequeClearPlaceOrderDataTable.clear();
		setBooRenderDataTable(false);
		setBooRenderSaveOrExit(false);
		 try{
			  List<Customer> listDetails = icustomerRegistrationService.getIntroducerCustId(getCustomerRefNo());
				if(listDetails!=null && listDetails.size()>0){
					setCustomerId(listDetails.get(0).getCustomerId());
					tofetchAllRecords();
				}else{
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("noRecords.show();");
					setCustomerId(null);
					setCustomerRefNo(null);
					setBooRenderDataTable(false);
					setBooRenderSaveOrExit(false);
				}
			    }catch(Exception exception){
			    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			    setErrorMessage(exception.getMessage()); 
			    RequestContext.getCurrentInstance().execute("error.show();");
			    return;       
			    }
	}
	//on form load to fetch All records
	public void tofetchAllRecords(){
		lstChequeClearPlaceOrderDataTable.clear();
		lstPkUpdates.clear();
		List<RatePlaceOrderDTO> lstRatePlaceOrder=chequeClearPlaceOrderService.toFetchAllRecordsFromDb(getCustomerId());
		if(lstRatePlaceOrder.size()>0){
			for (RatePlaceOrderDTO ratePlaceOrder : lstRatePlaceOrder) {
				ChequeClearPlaceOrderDataTable chequeClearPlaceOrderDT=new ChequeClearPlaceOrderDataTable();
				chequeClearPlaceOrderDT.setRateOfferedPk(ratePlaceOrder.getRateOfferedPk());
				chequeClearPlaceOrderDT.setCustomerId(ratePlaceOrder.getCustomerId());
				String custmerName=null;
				BigDecimal custrRef=null;
				String BeneName=null;
				String beneBankName=null;
				String curQtyName=null;
				//to Fetch Customer reference and Name
				//custrRef=gSMPlaceOrderRateFeedService.toFetchCustomerRef(ratePlaceOrder.getCustomerId());
				custmerName=generalService.getCustomerNameCustomerId(ratePlaceOrder.getCustomerId());
				chequeClearPlaceOrderDT.setCustomerName(custmerName);
				chequeClearPlaceOrderDT.setCustomerRefNo(ratePlaceOrder.getCustomerReference());
				if(ratePlaceOrder.getCustomerReference() != null){
					chequeClearPlaceOrderDT.setCustomerRefAndName(ratePlaceOrder.getCustomerReference().toString()+ "-" +custmerName);
				}else{
				chequeClearPlaceOrderDT.setCustomerRefAndName(custmerName);
				}
				//chequeClearPlaceOrderDT.setDocumentNumber(ratePlaceOrder.getDocumentNumber());
				//to bene name
				/*chequeClearPlaceOrderDT.setBeneficiaryMasterId(ratePlaceOrder.getBeneficiaryMasterId().getBeneficaryMasterSeqId());
				BeneName=gSMPlaceOrderRateFeedService.toFetchBeneficiaryName(ratePlaceOrder.getBeneficiaryMasterId().getBeneficaryMasterSeqId());
				chequeClearPlaceOrderDT.setBeneficiaryName(BeneName);*/
				//bene bank name
				/*chequeClearPlaceOrderDT.setBeneficiaryBankId(ratePlaceOrder.getBeneficiaryBankId());
				beneBankName=generalService.getBankName(ratePlaceOrder.getBeneficiaryBankId());
				chequeClearPlaceOrderDT.setBeneficiaryBankName(beneBankName);*/
				chequeClearPlaceOrderDT.setBeneficiaryBankId(ratePlaceOrder.getChequeBankCode());
				if(ratePlaceOrder.getChequeBankCode() != null){
				beneBankName=chequeClearPlaceOrderService.getBankNamebasedonBankCode(ratePlaceOrder.getChequeBankCode().toString());
				}
				chequeClearPlaceOrderDT.setBeneficiaryBankName(beneBankName);
				chequeClearPlaceOrderDT.setRateOffered(ratePlaceOrder.getRateOfferedPk());
				chequeClearPlaceOrderDT.setTransctionAmount(ratePlaceOrder.getTranctionAmount());
				//chequeClearPlaceOrderDT.setCurrencyId(ratePlaceOrder.getCurrencyId());
				//currency name qty code
				//ratePlaceOrder.getBeneficiaryMasterId().getBeneficaryMasterSeqId(),ratePlaceOrder.getAccountSeqquenceId().getBeneficaryAccountSeqId()
				/*curQtyName=gSMPlaceOrderRateFeedService.toFetchCurrencyQtyName(ratePlaceOrder.getCurrencyId());
				chequeClearPlaceOrderDT.setCurrencyQtyName("["+curQtyName+"]");
				chequeClearPlaceOrderDT.setAmountAndQtyName(ratePlaceOrder.getTranctionAmount()+ "-" +curQtyName);*/
				//remittance and service , delivery
				/*chequeClearPlaceOrderDT.setRemitServiceId(ratePlaceOrder.getServiceMasterId());
				chequeClearPlaceOrderDT.setRemitRemittanceId(ratePlaceOrder.getRemittanceModeId());
				chequeClearPlaceOrderDT.setRemitDeliveryId(ratePlaceOrder.getDeliveryModeId());
				chequeClearPlaceOrderDT.setCreatedBy(ratePlaceOrder.getCreatedBy());
				chequeClearPlaceOrderDT.setCreatedDate(ratePlaceOrder.getCreatedDate());
				chequeClearPlaceOrderDT.setModifiedBy(ratePlaceOrder.getModifiedBy());
				chequeClearPlaceOrderDT.setModifiedDate(ratePlaceOrder.getModifiedDate());
				chequeClearPlaceOrderDT.setApprovedBy(ratePlaceOrder.getApprovedBy());
				chequeClearPlaceOrderDT.setApprovedDate(ratePlaceOrder.getApprovedDate());
				chequeClearPlaceOrderDT.setIsActive(ratePlaceOrder.getIsActive());
				chequeClearPlaceOrderDT.setRoutingCountry(ratePlaceOrder.getRoutingCountryId());
				chequeClearPlaceOrderDT.setRoutingBank(ratePlaceOrder.getRoutingBankId());
				chequeClearPlaceOrderDT.setRemitDocumentNumber(ratePlaceOrder.getApplDocumentNumber());
				chequeClearPlaceOrderDT.setRemitDocumentFinanceYear(ratePlaceOrder.getApplDocumentFinanceYear());
				chequeClearPlaceOrderDT.setPaymentCode(ratePlaceOrder.getCollectionMode())*/;
				
				List<RemittanceApplication> lstRemittanceApplication= placeOrderPendingTransctionService.getTransactionDetails(ratePlaceOrder.getRateOfferedPk());

				if(lstRemittanceApplication!=null && lstRemittanceApplication.size()>0)
				{
					RemittanceApplication remittanceApplication=lstRemittanceApplication.get(0);
					
					BigDecimal localRemitAmount= remittanceApplication.getLocalNetTranxAmount();
					BigDecimal localCurrencyId= remittanceApplication.getExCurrencyMasterByLocalNetCurrencyId().getCurrencyId();
					curQtyName=gSMPlaceOrderRateFeedService.toFetchCurrencyQtyName(localCurrencyId);
					chequeClearPlaceOrderDT.setRemitLocalAmount(localRemitAmount);
					chequeClearPlaceOrderDT.setRemitLocalAmountAndQtyName(localRemitAmount+ "-" +"["+curQtyName+"]");
					
					
					BigDecimal equivalentForeignTranxAmount= remittanceApplication.getForeignTranxAmount();
					BigDecimal equivalentCurrencyId=remittanceApplication.getExCurrencyMasterByForeignCurrencyId().getCurrencyId();
					curQtyName=gSMPlaceOrderRateFeedService.toFetchCurrencyQtyName(equivalentCurrencyId);
					chequeClearPlaceOrderDT.setFreignTranxAmount(equivalentForeignTranxAmount);
					chequeClearPlaceOrderDT.setAmountAndQtyName(equivalentForeignTranxAmount+ "-" +"["+curQtyName+"]");
					chequeClearPlaceOrderDT.setLocalCommissionAmount(remittanceApplication.getLocalCommisionAmount());
				}
				
				chequeClearPlaceOrderDT.setChequeBankCode(ratePlaceOrder.getChequeBankCode());
				chequeClearPlaceOrderDT.setChequeReference(ratePlaceOrder.getChequeReference());
				chequeClearPlaceOrderDT.setChequeDate(ratePlaceOrder.getChequeDate());
				chequeClearPlaceOrderDT.setEmailId(ratePlaceOrder.getEmailId());
				lstChequeClearPlaceOrderDataTable.add(chequeClearPlaceOrderDT);
				setCustomerId(chequeClearPlaceOrderDT.getCustomerId());
				setBooRenderDataTable(true);
				setBooRenderSaveOrExit(true);
			}
		}else{
			RequestContext.getCurrentInstance().execute("noRecords.show();");
			setCustomerRefNo(null);
			setCustomerId(null);
			return;
		}
	}
	
	//save Functionality starts
		public void save(){
			
			/*for (BigDecimal bigDecimal : lstPkUpdates) {
				chequeClearPlaceOrderService.saveOrUpdatePlaceOrder(lstPkUpdates,session.getUserName());
			}*/
			HashMap<String, Object> inputValue= new HashMap<String, Object>();
			HashMap<String, String> outPutValues1= new HashMap<String, String>();
			inputValue.put("Customer_Id", getCustomerId().toString());
			try {
				ChequeClearPlaceOrderDataTable dataTable=getChequeClearPlaceOrderDataTable();
				inputValue.put("CUSTOMER_ID", getCustomerId());
				inputValue.put("CHEQUE_BANK_CODE", dataTable.getChequeBankCode());
				inputValue.put("CHEQUE_REFERENCE", dataTable.getChequeReference());
				inputValue.put("CHEQUE_DATE", dataTable.getChequeDate());
				inputValue.put("RATE_OFFERED_PK", dataTable.getRateOfferedPk());
				
				/*BigDecimal customerId= (BigDecimal) inputValues.get("CUSTOMER_ID");
		BigDecimal chequeBankCode= (BigDecimal)inputValues.get("CHEQUE_BANK_CODE");
		String chequeRefNo= (String)inputValues.get("CHEQUE_REFERENCE");
		Date chequeDate=(Date) inputValues.get("CHEQUE_DATE");*/
		
				String updateValue=chequeClearPlaceOrderService.upadteCheckClearence(inputValue);
				if(updateValue!=null && updateValue.equalsIgnoreCase("Success"))
				{
					outPutValues1=chequeClearPlaceOrderService.placeOrderRemitTranxProcedure(inputValue);
					
					String errMsg=outPutValues1.get("P_ERROR_MESG");
					String pCollDocFyr=outPutValues1.get("P_COLL_DOCFYR");
					String pCollDocNo=outPutValues1.get("P_COLL_DOCNO");
					if(errMsg==null || errMsg.equalsIgnoreCase(""))
					{
						
						setErrorMessage(pCollDocNo);
						RequestContext.getCurrentInstance().execute("success.show();");
						
						PlaceOrderPendingTransctionBean placeOrderPendingTransctionBean = (PlaceOrderPendingTransctionBean) appContext.getBean("placeOrderPendingTransctionBean");
						
						boolean rtnCheck=placeOrderPendingTransctionBean.generatePersonalRemittanceReceiptReport(new BigDecimal(pCollDocNo), new BigDecimal(pCollDocFyr),dataTable.getEmailId());
						if(rtnCheck)
						{
							
							return;
						}
						
					}else
					{
						setErrorMessage("Procedure Error : "+errMsg);
						RequestContext.getCurrentInstance().execute("error.show();");
						return;
					}
				}else
				{
					RequestContext.getCurrentInstance().execute("multiUser.show();");
					return;
				}
				
				/*
				System.out.println("getCustomerId():::::::::::::::"+getCustomerId());
				lstChequeClearPlaceOrderDataTable.clear();
				setCustomerId(null);
				RequestContext.getCurrentInstance().execute("complete.show();");
				return;*/
			} catch(NullPointerException ne){
				log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				setErrorMessage("save::toFetchAllRecords");
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return; 
				}catch(Exception exception){
				log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
				setErrorMessage(exception.getMessage()); 
				RequestContext.getCurrentInstance().execute("error.show();");
				return;       
				}
		}
		
		public void clickOnOKSave(){
			lstChequeClearPlaceOrderDataTable.clear();
			setBooRenderDataTable(false);
			setBooRenderSaveOrExit(false);
			pageNavigationchequeClearPlaceOrder();
		}
	//check box selection event
	/*public void SelectCheckBoxList(ChequeClearPlaceOrderDataTable dataTableObj) {
		if (dataTableObj.isSelectCheckBox()) {
			setRateOfferedPk(dataTableObj.getRateOfferedPk());
			lstPkUpdates.add(getRateOfferedPk());
		} else if (!dataTableObj.isSelectCheckBox()) {
				setRateOfferedPk(null);
		}
		}*/
	
	
	
	//on click event
	public void toLoadSourecOfincome(ChequeClearPlaceOrderDataTable dataTable) throws AMGException{
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
		//BigDecimal PaymentId=placeOrderPendingTransctionService.toFetchPaymentId(dataTable.getPaymentCode());
		//setPaymentmodeId(PaymentId);
		/*toFetchPaymentDetails();
		getLocalBankListforIndicator();
		if(dataTable.getPaymentCode().equalsIgnoreCase("B")){
			setBooChequePanel(true);
			setBooCardPanel(false);
			setPaymentmodeId(PaymentId);
			
		}else{
			setBooChequePanel(false);
			setBooCardPanel(true);
			setPaymentmodeId(PaymentId);
		}*/
		
		//String PaymentName=placeOrderPendingTransctionService.toFetchPaymentName(PaymentId,session.getLanguageId());
	}
	
	
		//Payment Details
		/*public void toFetchPaymentDetails(){
			lstFetchAllPayMode.clear();
			List<PaymentModeDesc> list=ipaymentService.fetchPaymodeDesc(new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId") : "1"),Constants.Yes);
			if(list.size() !=0){
				lstFetchAllPayMode.addAll(list);
			}

		}*/
		
		// to get the local bank list or customer bank list
		/*public void getLocalBankListforIndicator() {
			List<BigDecimal> duplicateCheck = new ArrayList<BigDecimal>();
			List<BigDecimal> duplicateCheck1 = new ArrayList<BigDecimal>();
			List<ViewBankDetails> lstofBank = new ArrayList<ViewBankDetails>();
			List<ViewBankDetails> lstofBank1 = new ArrayList<ViewBankDetails>();
			bankMasterList.clear();
			chequebankMasterList.clear();

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

		}*/
		//ajax event changes changes 
		/*public void changeBanksForPayment(){
			getLocalBankListforIndicator();
		}*/
		
		
		
		public void exit(){
			lstChequeClearPlaceOrderDataTable.clear();
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

		public void toLoadSelectValue(SelectEvent event)
		{
			setChequeClearPlaceOrderDataTable(null);
			ChequeClearPlaceOrderDataTable dataTable=(ChequeClearPlaceOrderDataTable) event.getObject();
			setChequeClearPlaceOrderDataTable(dataTable);
			
			
		}
}
