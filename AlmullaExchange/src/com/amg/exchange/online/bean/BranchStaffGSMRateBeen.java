package com.amg.exchange.online.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.beneficiary.model.BankBranchView;
import com.amg.exchange.beneficiary.service.IBeneficaryCreation;
import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.service.IDocumentSerialityService;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.foreigncurrency.model.SourceOfIncomeDescription;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.foreigncurrency.service.ForeignLocalCurrencyDenominationService;
import com.amg.exchange.foreigncurrency.service.IForeignCurrencyPurchaseService;
import com.amg.exchange.online.model.RatePlaceOrder;
import com.amg.exchange.online.model.RatePlaceOrderAddlData;
import com.amg.exchange.online.service.IBranchStaffGSMRateService;
import com.amg.exchange.online.service.IGSMPlaceOrderRateFeedService;
import com.amg.exchange.online.service.IPlaceAnOrderCreationService;
import com.amg.exchange.online.service.IPlaceOrederBranchSupportService;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerIdproofView;
import com.amg.exchange.registration.service.ICustomerRegistrationBranchService;
import com.amg.exchange.remittance.bean.AddAdditionalBankData;
import com.amg.exchange.remittance.bean.AddDynamicLebel;
import com.amg.exchange.remittance.bean.PersonalRemittanceBean;
import com.amg.exchange.remittance.bean.PopulateData;
import com.amg.exchange.remittance.model.AccountTypeFromView;
import com.amg.exchange.remittance.model.AdditionalBankDetailsView;
import com.amg.exchange.remittance.model.AdditionalBankRuleMap;
import com.amg.exchange.remittance.model.AdditionalDataDisplayView;
import com.amg.exchange.remittance.model.BeneficaryAccount;
import com.amg.exchange.remittance.model.BeneficaryContact;
import com.amg.exchange.remittance.model.BenificiaryListView;
import com.amg.exchange.remittance.model.PaymentModeDesc;
import com.amg.exchange.remittance.model.ViewAmtbCoupon;
import com.amg.exchange.remittance.service.IPaymentService;
import com.amg.exchange.remittance.service.IPersonalRemittanceService;
import com.amg.exchange.remittance.service.IServiceGroupMasterService;
import com.amg.exchange.treasury.deal.supplier.model.ApplicationSetup;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.treasury.model.ServiceGroupMasterDesc;
import com.amg.exchange.treasury.model.ServiceMaster;
import com.amg.exchange.treasury.model.ServiceMasterDesc;
import com.amg.exchange.treasury.service.DeliveryModeService;
import com.amg.exchange.treasury.service.IBankBranchDetailsService;
import com.amg.exchange.treasury.service.IRemittanceModeService;
import com.amg.exchange.treasury.service.ServiceCodeMasterService;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.GetRound;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;
@Component("branchStaffGSMRateBean")
@Scope("session")
public class BranchStaffGSMRateBeen<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log=Logger.getLogger(BranchStaffGSMRateBeen.class);
	//Page level Variables
	private BigDecimal rateOfferedPk;
	private BigDecimal customerRefNo;
	private BigDecimal documentNumber;
	private BigDecimal documentFinanceYear;
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
	private Date visitDate;
	private String idNumber;
	private BigDecimal selectCard;

	//render Variables
	private BigDecimal sourceId;
	private String paymentmodeId;
	private String exceptionMessage;
	private boolean additionalCheck = true;
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

	SessionStateManage session= new SessionStateManage();
	//Search Customer Variables
	private BigDecimal customerId;
	private String customerName;
	private Boolean booDocVisble;
	private String errmsg;

	private List<BranchStaffGSMRateDataTable> lstBranchStaffGSMRateDataTables=new ArrayList<BranchStaffGSMRateDataTable>();
	private List<SourceOfIncomeDescription> lstSourceofIncome=new ArrayList<SourceOfIncomeDescription>();
	private List<PaymentModeDesc> lstFetchAllPayMode=new ArrayList<PaymentModeDesc>();
	private List<AddDynamicLebel> listDynamicLebel = new ArrayList<AddDynamicLebel>();
	private List<AddAdditionalBankData> listAdditionalBankDataTable = new ArrayList<AddAdditionalBankData>();
	List<PopulateData> serviceList = new ArrayList<PopulateData>();

	private List<PopulateData>  beneficiaryList;
	private Boolean showBeneNameList;
	private Boolean booRenderBnkAccNoList;
	private Boolean booRenderBnkAccNo;
	private BigDecimal beneAccSeqId;
	private List<PopulateData>  lstAccountNumber;
	private String accountNumber;
	private BigDecimal beneficiaryCountryId;
	private BigDecimal serviceGroupId;
	private BigDecimal accountSequenceId;


	private BigDecimal beneficiaryBranchId;
	private String serviceGroupCode;
	private Boolean booSingleService=true;
	private Boolean booMultipleService=false;
	private BigDecimal accountSeqId;
	private BigDecimal dataserviceid;
	private String databenificaryservice;
	private String routingCountryName;
	private Boolean booSingleRoutingCountry=true;
	private Boolean booMultipleRoutingCountry=false;
	private String routingBankName;
	private Boolean booMultipleRoutingBank=false;
	private Boolean booSingleRoutingBank=true;
	private String routingBranchName;
	private Boolean booMultipleRoutingBranch=false;
	private Boolean booSingleRoutingBranch=true;
	private BigDecimal routingBranch;
	private Boolean booRenderRemitPanel=false;
	//Routing Remittance Variables
	private Boolean booMultipleRemit=false;
	private Boolean booSingleRemit=true;
	private String remittanceName;
	private BigDecimal remitMode;
	//Routing Delivery Variables
	private Boolean booRenderDeliveryModeDDPanel=false;
	private Boolean booRenderDeliveryModeInputPanel=true;
	private String deliveryModeInput;
	private BigDecimal deliveryMode;

	private BranchStaffGSMRateDataTable acceptPlaceOrderDataTable;

	private Boolean routingDetailsShow;

	private List<PopulateData> lstofRemittance;
	private List<PopulateData> lstofDelivery;
	private List<PopulateData> routingBankBranchlst;
	private List<PopulateData> routingbankvalues;
	private List<PopulateData> routingCountrylst;
	private BigDecimal cashRoutingBranch;
	private String cashRoutingBranchName;

	private Boolean booRenderBranchStaffRemittance=false;

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
	IPlaceOrederBranchSupportService placeOrederBranchSupportService;
	@Autowired
	ICustomerRegistrationBranchService<T> iCustomerRegistrationBranchService;
	@Autowired
	private ApplicationContext appContext;
	@Autowired
	IPlaceAnOrderCreationService  iPlaceOnOrderCreationService;
	@Autowired 
	IServiceGroupMasterService iServiceGroupMasterService;
	@Autowired
	ServiceCodeMasterService serviceMasterService;
	@Autowired
	IBankBranchDetailsService<T> bankBranchDetailsService;
	@Autowired
	IRemittanceModeService iRemitModeMaster;
	@Autowired
	DeliveryModeService iDeliveryModeMaster;
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	@Autowired
	IBeneficaryCreation beneficaryCreation;
	@Autowired
	IRemittanceModeService iremittanceModeService;
	@Autowired
	DeliveryModeService deliveryModeService;
	@Autowired
	ForeignLocalCurrencyDenominationService<T> foreignLocalCurrencyDenominationService;
	@Autowired
	IDocumentSerialityService<T> documentSerialityService;


	public Boolean getBooRenderBranchStaffRemittance() {
		return booRenderBranchStaffRemittance;
	}
	public void setBooRenderBranchStaffRemittance(Boolean booRenderBranchStaffRemittance) {
		this.booRenderBranchStaffRemittance = booRenderBranchStaffRemittance;
	}

	public Boolean getRoutingDetailsShow() {
		return routingDetailsShow;
	}
	public void setRoutingDetailsShow(Boolean routingDetailsShow) {
		this.routingDetailsShow = routingDetailsShow;
	}

	public BranchStaffGSMRateDataTable getAcceptPlaceOrderDataTable() {
		return acceptPlaceOrderDataTable;
	}
	public void setAcceptPlaceOrderDataTable(BranchStaffGSMRateDataTable acceptPlaceOrderDataTable) {
		this.acceptPlaceOrderDataTable = acceptPlaceOrderDataTable;
	}

	public List<PopulateData> getRoutingCountrylst() {
		return routingCountrylst;
	}
	public void setRoutingCountrylst(List<PopulateData> routingCountrylst) {
		this.routingCountrylst = routingCountrylst;
	}

	public List<PopulateData> getRoutingbankvalues() {
		return routingbankvalues;
	}
	public void setRoutingbankvalues(List<PopulateData> routingbankvalues) {
		this.routingbankvalues = routingbankvalues;
	}

	public List<PopulateData> getRoutingBankBranchlst() {
		return routingBankBranchlst;
	}
	public void setRoutingBankBranchlst(List<PopulateData> routingBankBranchlst) {
		this.routingBankBranchlst = routingBankBranchlst;
	}

	public List<PopulateData> getLstofDelivery() {
		return lstofDelivery;
	}
	public void setLstofDelivery(List<PopulateData> lstofDelivery) {
		this.lstofDelivery = lstofDelivery;
	}

	public List<PopulateData> getLstofRemittance() {
		return lstofRemittance;
	}
	public void setLstofRemittance(List<PopulateData> lstofRemittance) {
		this.lstofRemittance = lstofRemittance;
	}

	public Boolean getBooMultipleRemit() {
		return booMultipleRemit;
	}
	public void setBooMultipleRemit(Boolean booMultipleRemit) {
		this.booMultipleRemit = booMultipleRemit;
	}

	public Boolean getBooSingleRemit() {
		return booSingleRemit;
	}
	public void setBooSingleRemit(Boolean booSingleRemit) {
		this.booSingleRemit = booSingleRemit;
	}

	public String getRemittanceName() {
		return remittanceName;
	}
	public void setRemittanceName(String remittanceName) {
		this.remittanceName = remittanceName;
	}

	public BigDecimal getRemitMode() {
		return remitMode;
	}
	public void setRemitMode(BigDecimal remitMode) {
		this.remitMode = remitMode;
	}

	public Boolean getBooRenderDeliveryModeDDPanel() {
		return booRenderDeliveryModeDDPanel;
	}
	public void setBooRenderDeliveryModeDDPanel(Boolean booRenderDeliveryModeDDPanel) {
		this.booRenderDeliveryModeDDPanel = booRenderDeliveryModeDDPanel;
	}

	public Boolean getBooRenderDeliveryModeInputPanel() {
		return booRenderDeliveryModeInputPanel;
	}
	public void setBooRenderDeliveryModeInputPanel(Boolean booRenderDeliveryModeInputPanel) {
		this.booRenderDeliveryModeInputPanel = booRenderDeliveryModeInputPanel;
	}

	public String getDeliveryModeInput() {
		return deliveryModeInput;
	}
	public void setDeliveryModeInput(String deliveryModeInput) {
		this.deliveryModeInput = deliveryModeInput;
	}

	public BigDecimal getDeliveryMode() {
		return deliveryMode;
	}
	public void setDeliveryMode(BigDecimal deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	public Boolean getBooRenderRemitPanel() {
		return booRenderRemitPanel;
	}
	public void setBooRenderRemitPanel(Boolean booRenderRemitPanel) {
		this.booRenderRemitPanel = booRenderRemitPanel;
	}

	public BigDecimal getRoutingBranch() {
		return routingBranch;
	}
	public void setRoutingBranch(BigDecimal routingBranch) {
		this.routingBranch = routingBranch;
	}

	public BigDecimal getCashRoutingBranch() {
		return cashRoutingBranch;
	}
	public void setCashRoutingBranch(BigDecimal cashRoutingBranch) {
		this.cashRoutingBranch = cashRoutingBranch;
	}

	public String getCashRoutingBranchName() {
		return cashRoutingBranchName;
	}
	public void setCashRoutingBranchName(String cashRoutingBranchName) {
		this.cashRoutingBranchName = cashRoutingBranchName;
	}

	public String getDatabenificaryservice() {
		return databenificaryservice;
	}
	public void setDatabenificaryservice(String databenificaryservice) {
		this.databenificaryservice = databenificaryservice;
	}

	public String getRoutingCountryName() {
		return routingCountryName;
	}
	public void setRoutingCountryName(String routingCountryName) {
		this.routingCountryName = routingCountryName;
	}

	public Boolean getBooSingleRoutingCountry() {
		return booSingleRoutingCountry;
	}
	public void setBooSingleRoutingCountry(Boolean booSingleRoutingCountry) {
		this.booSingleRoutingCountry = booSingleRoutingCountry;
	}

	public Boolean getBooMultipleRoutingCountry() {
		return booMultipleRoutingCountry;
	}
	public void setBooMultipleRoutingCountry(Boolean booMultipleRoutingCountry) {
		this.booMultipleRoutingCountry = booMultipleRoutingCountry;
	}

	public String getRoutingBankName() {
		return routingBankName;
	}
	public void setRoutingBankName(String routingBankName) {
		this.routingBankName = routingBankName;
	}

	public Boolean getBooMultipleRoutingBank() {
		return booMultipleRoutingBank;
	}
	public void setBooMultipleRoutingBank(Boolean booMultipleRoutingBank) {
		this.booMultipleRoutingBank = booMultipleRoutingBank;
	}

	public Boolean getBooSingleRoutingBank() {
		return booSingleRoutingBank;
	}
	public void setBooSingleRoutingBank(Boolean booSingleRoutingBank) {
		this.booSingleRoutingBank = booSingleRoutingBank;
	}

	public String getRoutingBranchName() {
		return routingBranchName;
	}
	public void setRoutingBranchName(String routingBranchName) {
		this.routingBranchName = routingBranchName;
	}

	public Boolean getBooMultipleRoutingBranch() {
		return booMultipleRoutingBranch;
	}
	public void setBooMultipleRoutingBranch(Boolean booMultipleRoutingBranch) {
		this.booMultipleRoutingBranch = booMultipleRoutingBranch;
	}

	public Boolean getBooSingleRoutingBranch() {
		return booSingleRoutingBranch;
	}
	public void setBooSingleRoutingBranch(Boolean booSingleRoutingBranch) {
		this.booSingleRoutingBranch = booSingleRoutingBranch;
	}

	public BigDecimal getDataserviceid() {
		return dataserviceid;
	}
	public void setDataserviceid(BigDecimal dataserviceid) {
		this.dataserviceid = dataserviceid;
	}

	public BigDecimal getAccountSeqId() {
		return accountSeqId;
	}
	public void setAccountSeqId(BigDecimal accountSeqId) {
		this.accountSeqId = accountSeqId;
	}

	public Boolean getBooSingleService() {
		return booSingleService;
	}
	public void setBooSingleService(Boolean booSingleService) {
		this.booSingleService = booSingleService;
	}

	public Boolean getBooMultipleService() {
		return booMultipleService;
	}
	public void setBooMultipleService(Boolean booMultipleService) {
		this.booMultipleService = booMultipleService;
	}

	public String getServiceGroupCode() {
		return serviceGroupCode;
	}
	public void setServiceGroupCode(String serviceGroupCode) {
		this.serviceGroupCode = serviceGroupCode;
	}

	public BigDecimal getBeneficiaryBranchId() {
		return beneficiaryBranchId;
	}
	public void setBeneficiaryBranchId(BigDecimal beneficiaryBranchId) {
		this.beneficiaryBranchId = beneficiaryBranchId;
	}

	public BigDecimal getAccountSequenceId() {
		return accountSequenceId;
	}
	public void setAccountSequenceId(BigDecimal accountSequenceId) {
		this.accountSequenceId = accountSequenceId;
	}

	public Boolean getBooRenderBnkAccNoList() {
		return booRenderBnkAccNoList;
	}
	public void setBooRenderBnkAccNoList(Boolean booRenderBnkAccNoList) {
		this.booRenderBnkAccNoList = booRenderBnkAccNoList;
	}

	public Boolean getBooRenderBnkAccNo() {
		return booRenderBnkAccNo;
	}
	public void setBooRenderBnkAccNo(Boolean booRenderBnkAccNo) {
		this.booRenderBnkAccNo = booRenderBnkAccNo;
	}

	public BigDecimal getBeneAccSeqId() {
		return beneAccSeqId;
	}
	public void setBeneAccSeqId(BigDecimal beneAccSeqId) {
		this.beneAccSeqId = beneAccSeqId;
	}

	public List<PopulateData> getLstAccountNumber() {
		return lstAccountNumber;
	}
	public void setLstAccountNumber(List<PopulateData> lstAccountNumber) {
		this.lstAccountNumber = lstAccountNumber;
	}

	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public BigDecimal getBeneficiaryCountryId() {
		return beneficiaryCountryId;
	}
	public void setBeneficiaryCountryId(BigDecimal beneficiaryCountryId) {
		this.beneficiaryCountryId = beneficiaryCountryId;
	}

	public BigDecimal getServiceGroupId() {
		return serviceGroupId;
	}
	public void setServiceGroupId(BigDecimal serviceGroupId) {
		this.serviceGroupId = serviceGroupId;
	}

	public Boolean getShowBeneNameList() {
		return showBeneNameList;
	}
	public void setShowBeneNameList(Boolean showBeneNameList) {
		this.showBeneNameList = showBeneNameList;
	}

	public List<PopulateData> getBeneficiaryList() {
		return beneficiaryList;
	}
	public void setBeneficiaryList(List<PopulateData> beneficiaryList) {
		this.beneficiaryList = beneficiaryList;
	}

	public Boolean getBooDocVisble() {
		return booDocVisble;
	}
	public void setBooDocVisble(Boolean booDocVisble) {
		this.booDocVisble = booDocVisble;
	}

	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public BigDecimal getSelectCard() {
		return selectCard;
	}
	public void setSelectCard(BigDecimal selectCard) {
		this.selectCard = selectCard;
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

	public List<BranchStaffGSMRateDataTable> getLstBranchStaffGSMRateDataTables() {
		return lstBranchStaffGSMRateDataTables;
	}
	public void setLstBranchStaffGSMRateDataTables(List<BranchStaffGSMRateDataTable> lstBranchStaffGSMRateDataTables) {
		this.lstBranchStaffGSMRateDataTables = lstBranchStaffGSMRateDataTables;
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

	public String getPaymentmodeId() {
		return paymentmodeId;
	}
	public void setPaymentmodeId(String paymentmodeId) {
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
	public void setListAdditionalBankDataTable(List<AddAdditionalBankData> listAdditionalBankDataTable) {
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

	public BigDecimal getDocumentFinanceYear() {
		return documentFinanceYear;
	}
	public void setDocumentFinanceYear(BigDecimal documentFinanceYear) {
		this.documentFinanceYear = documentFinanceYear;
	}

	public List<PopulateData> getServiceList() {
		return serviceList;
	}
	public void setServiceList(List<PopulateData> serviceList) {
		this.serviceList = serviceList;
	}

	public Date getVisitDate() {
		return visitDate;
	}
	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}

	//Page Navigation
	public void pageNavigationBranchStaff(){
		setBooRenderBranchStaffRemittance(false);
		lstBranchStaffGSMRateDataTables.clear();
		clearAllFields();
		clearRoutingSetup();
		tofetchAllRecords();
		setBooRenderAdditionals(false);
		setBooRenderSaveOrExit(false);
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "BranchStaffGsmRateRemitt.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../onlinespecialrate/BranchStaffGsmRateRemitt.xhtml");
			//FacesContext.getCurrentInstance().getExternalContext().redirect("../onlinespecialrate/BranchStaffGsmRate.xhtml");
		} catch (Exception exception) {
			setErrorMessage(exception.getMessage());
			log.error("groupSalesManagerApprovalPageNavigation  :"+ exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}
	}

	//Page Navigation
	public void pageNavigationFromPersonalRemitt(BigDecimal customerId ,String idNumber ,BigDecimal selectCard){
		setBooRenderBranchStaffRemittance(true);
		lstBranchStaffGSMRateDataTables.clear();
		clearAllFields();
		setIdNumber(idNumber);
		setSelectCard(selectCard);
		setCustomerId(customerId);
		tofetchAllRecordsBasedOnCustomerId();
		setBooRenderAdditionals(false);
		setBooRenderSaveOrExit(false);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../onlinespecialrate/BranchStaffGsmRateRemitt.xhtml");
			//FacesContext.getCurrentInstance().getExternalContext().redirect("../onlinespecialrate/BranchStaffGsmRate.xhtml");
		} catch (Exception exception) {
			setErrorMessage(exception.getMessage());
			log.error("groupSalesManagerApprovalPageNavigation  :"+ exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
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
		setAmountAndQtyName(null);
		setBeneficiaryMasterId(null);
		setBeneficiaryAccountSeqId(null);
		setCurrencyQtyName(null);
		setRoutingCountry(null);
		setRoutingBank(null);
		//common Variables
		setErrorMessage(null);;
		setCreatedBy(null);
		setCreatedDate(null);
		setModifiedBy(null);
		setModifiedDate(null);
		setApprovedBy(null);
		setApprovedDate(null);
		setIsActive(null);
		setIdNumber(null);
		setSelectCard(null);
		setShowBeneNameList(false);
		setErrorMessage(null);
	}

	//on form load to fetch All records
	public void tofetchAllRecords(){
		lstBranchStaffGSMRateDataTables.clear();
		List<RatePlaceOrder> lstRatePlaceOrder=branchStaffGSMRateService.toFetchAllRecordsFromDb(new BigDecimal(session.getBranchId()));
		if(lstRatePlaceOrder.size()>0){
			for (RatePlaceOrder ratePlaceOrder : lstRatePlaceOrder) {
				BranchStaffGSMRateDataTable branchStaffGSMRateDT=new BranchStaffGSMRateDataTable();
				branchStaffGSMRateDT.setRateOfferedPk(ratePlaceOrder.getRatePlaceOrderId());
				branchStaffGSMRateDT.setCustomerId(ratePlaceOrder.getCustomerId());
				String custmerName=null;
				BigDecimal custrRef=null;
				String BeneName=null;
				String beneBankName=null;
				String curQtyName=null;
				//to Fetch Customer reference and Name
				//custrRef=gSMPlaceOrderRateFeedService.toFetchCustomerRef(ratePlaceOrder.getCustomerId());
				custmerName=generalService.getCustomerNameCustomerId(ratePlaceOrder.getCustomerId());
				branchStaffGSMRateDT.setCustomerName(custmerName);
				branchStaffGSMRateDT.setCustomerRefNo(ratePlaceOrder.getCustomerreference());
				if(ratePlaceOrder.getCustomerreference() != null){
					branchStaffGSMRateDT.setCustomerRefAndName(ratePlaceOrder.getCustomerreference().toString()+ "-" +custmerName);
				}else{
					branchStaffGSMRateDT.setCustomerRefAndName(custmerName);	
				}
				branchStaffGSMRateDT.setDocumentNumber(ratePlaceOrder.getDocumentNumber());
				branchStaffGSMRateDT.setDocumentFinanceYear(ratePlaceOrder.getDocumentFinanceYear());
				//to bene name
				if(ratePlaceOrder.getBeneficiaryMasterId()!=null)
				{
					branchStaffGSMRateDT.setBeneficiaryMasterId(ratePlaceOrder.getBeneficiaryMasterId().getBeneficaryMasterSeqId());
					BeneName=gSMPlaceOrderRateFeedService.toFetchBeneficiaryName(ratePlaceOrder.getBeneficiaryMasterId().getBeneficaryMasterSeqId());
					branchStaffGSMRateDT.setBeneficiaryName(BeneName);
				}

				branchStaffGSMRateDT.setBeneCurrencyId(ratePlaceOrder.getDestinationCurrenyId()); // bene currency

				String currencyName = generalService.getCurrencyName(ratePlaceOrder.getDestinationCurrenyId());
				branchStaffGSMRateDT.setCurrencyName(currencyName);

				/*List<PopulateData> lstofCurrency = new ArrayList<PopulateData>();
				lstofCurrency= iPlaceOnOrderCreationService.getBasedOnCountyCurrency(ratePlaceOrder.getBeneficiaryCountryId());
				if(lstofCurrency!=null && lstofCurrency.size()>0)
				{
					branchStaffGSMRateDT.setBeneCurrencyId(lstofCurrency.get(0).getPopulateId());
				}*/

				if(ratePlaceOrder.getAccountSeqquenceId()!=null)
				{
					branchStaffGSMRateDT.setBeneficiaryAccountSeqId(ratePlaceOrder.getAccountSeqquenceId().getBeneficaryAccountSeqId());
				}
				//bene bank name
				branchStaffGSMRateDT.setBeneficiaryBankId(ratePlaceOrder.getBeneficiaryBankId());
				beneBankName=generalService.getBankName(ratePlaceOrder.getBeneficiaryBankId());
				branchStaffGSMRateDT.setBeneficiaryBankName(beneBankName);
				branchStaffGSMRateDT.setRateOffered(ratePlaceOrder.getRateOffered());
				branchStaffGSMRateDT.setTransctionAmount(ratePlaceOrder.getTransactionAmount());
				branchStaffGSMRateDT.setCurrencyId(ratePlaceOrder.getRequestCurrencyId()); // requested currency - selected currency
				//currency name qty code
				//ratePlaceOrder.getBeneficiaryMasterId().getBeneficaryMasterSeqId(),ratePlaceOrder.getAccountSeqquenceId().getBeneficaryAccountSeqId()
				//curQtyName=gSMPlaceOrderRateFeedService.toFetchCurrencyQtyName(ratePlaceOrder.getDestinationCurrenyId());
				curQtyName=gSMPlaceOrderRateFeedService.toFetchCurrencyQtyName(ratePlaceOrder.getRequestCurrencyId());
				branchStaffGSMRateDT.setCurrencyQtyName("["+curQtyName+"]");
				branchStaffGSMRateDT.setAmountAndQtyName(ratePlaceOrder.getTransactionAmount()+ "-" +curQtyName);
				//remittance and service , delivery
				branchStaffGSMRateDT.setRemitServiceId(ratePlaceOrder.getServiceMasterId());
				branchStaffGSMRateDT.setRemitRemittanceId(ratePlaceOrder.getRemittanceModeId());
				branchStaffGSMRateDT.setRemitDeliveryId(ratePlaceOrder.getDeliveryModeId());
				branchStaffGSMRateDT.setCreatedBy(ratePlaceOrder.getCreatedBy());
				branchStaffGSMRateDT.setCreatedDate(ratePlaceOrder.getCreatedDate());
				branchStaffGSMRateDT.setModifiedBy(ratePlaceOrder.getModifiedBy());
				branchStaffGSMRateDT.setModifiedDate(ratePlaceOrder.getModifiedDate());
				branchStaffGSMRateDT.setApprovedBy(ratePlaceOrder.getApprovedBy());
				branchStaffGSMRateDT.setApprovedDate(ratePlaceOrder.getApprovedDate());
				branchStaffGSMRateDT.setIsActive(ratePlaceOrder.getIsActive());
				branchStaffGSMRateDT.setRoutingCountry(ratePlaceOrder.getRoutingCountryId());
				branchStaffGSMRateDT.setRoutingBank(ratePlaceOrder.getRoutingBankId());
				branchStaffGSMRateDT.setBeneficiaryCountryId(ratePlaceOrder.getBeneficiaryCountryId());
				branchStaffGSMRateDT.setBeneficiaryAccountNo(ratePlaceOrder.getBeneficiaryAccountNo());
				branchStaffGSMRateDT.setCustomerEmail(ratePlaceOrder.getCustomerEmail());
				branchStaffGSMRateDT.setRemittType(ratePlaceOrder.getRemitType()); // service group id
				lstBranchStaffGSMRateDataTables.add(branchStaffGSMRateDT);
			}
		}
	}

	//click on Accept button
	public void toLoadSourecOfincome(BranchStaffGSMRateDataTable dataTable) throws AMGException{

		boolean status = false;
		clearRoutingSetup();
		setAcceptPlaceOrderDataTable(null);
		setAccountNumber(null);
		setAccountSequenceId(null);
		setLstAccountNumber(null);
		setSourceId(null);

		setRateOfferedPk(null);
		setCustomerRefAndName(null);
		setDocumentNumber(null);
		setDocumentFinanceYear(null);
		setCurrencyId(null);
		setCashRoutingBranch(null);
		setCashRoutingBranchName(null);

		setBooRenderAdditionals(false);
		setBooRenderSaveOrExit(false);

		String acceptSts=branchStaffGSMRateService.checkPlaceOrderStatusForAccept(dataTable.getRateOfferedPk());
		if(acceptSts!=null && acceptSts.equalsIgnoreCase(Constants.No))
		{
			RequestContext.getCurrentInstance().execute("alreadyCreate.show();");
			return;
		}
		if(acceptSts!=null && acceptSts.equalsIgnoreCase(Constants.D))
		{
			RequestContext.getCurrentInstance().execute("alreadyReject.show();");
			return;
		}
		setShowBeneNameList(false);
		setRoutingDetailsShow(false);

		setRateOfferedPk(dataTable.getRateOfferedPk());
		setCustomerRefAndName(dataTable.getCustomerRefAndName());
		setDocumentNumber(dataTable.getDocumentNumber());
		setDocumentFinanceYear(dataTable.getDocumentFinanceYear());
		setCurrencyId(dataTable.getBeneCurrencyId());

		if(dataTable.getBeneficiaryMasterId()==null)
		{
			boolean beneNameCheck=populateBeneficiary(dataTable);
			if(beneNameCheck)
			{
				setShowBeneNameList(true);
				setRoutingDetailsShow(false);

				setBeneficiaryCountryId(dataTable.getBeneficiaryCountryId());
				setBeneficiaryBankId(dataTable.getBeneficiaryBankId());
				setServiceGroupId(dataTable.getRemittType());
				setCustomerRefNo(dataTable.getCustomerRefNo());

				setAcceptPlaceOrderDataTable(dataTable);
				//populateAccountNumber();
			}else
			{
				RequestContext.getCurrentInstance().execute("beneNameNotExist.show();");
				return;
			}
		}else {
			setAcceptPlaceOrderDataTable(dataTable);
			setBeneficiaryName(dataTable.getBeneficiaryName());
			setBeneficiaryCountryId(dataTable.getBeneficiaryCountryId());
			setBeneficiaryBankId(dataTable.getBeneficiaryBankId());
			setServiceGroupId(dataTable.getRemittType());
			setCustomerRefNo(dataTable.getCustomerRefNo());
			setAccountSequenceId(dataTable.getBeneficiaryAccountSeqId());
			setAccountNumber(dataTable.getBeneficiaryAccountNo());
			setShowBeneNameList(false);
			setRoutingDetailsShow(false);
			status = routingsetupDetails(dataTable);
			//addtionalMethods();
		}

		setCustomerId(dataTable.getCustomerId());

		if(status){
			setBooRenderAdditionals(true);
			setBooRenderSaveOrExit(true);
			setRoutingDetailsShow(true);
		}else{
			setBooRenderAdditionals(false);
			setBooRenderSaveOrExit(false);
			setRoutingDetailsShow(false);
			setErrorMessage("Routing SetUp Not Available");
			RequestContext.getCurrentInstance().execute("error.show();");
		}
	}

	public boolean routingsetupDetails(BranchStaffGSMRateDataTable dataTable){

		boolean status = false;

		try {

			setCashRoutingBranch(null);
			setCashRoutingBranchName(null);

			//HashMap<String, BigDecimal> rtnMap = iPlaceOnOrderCreationService.getBeneMasterIdCurrencyId(getBeneficiaryName(),getBeneficiaryCountryId(),getBeneficiaryBankId(),getServiceGroupId(),getCustomerRefNo(),dataTable.getBeneficiaryAccountNo());

			List<BenificiaryListView> listBene = iPlaceOnOrderCreationService.getBeneficiaryDetailsForAccept(dataTable);

			BigDecimal beneficaryMasterSeqId =  null;
			BigDecimal currencyId =  null;
			BigDecimal beneCashBranchId =  null;
			String beneCashBranchName =  null;
			String serviceGroupDesc = null;
			// bene Master seq id
			if(listBene != null && listBene.size() == 1){
				BenificiaryListView lstBene = listBene.get(0);
				beneficaryMasterSeqId = lstBene.getBeneficaryMasterSeqId();
				currencyId = lstBene.getCurrencyId();

				// service group description Cash or Banking Channel
				serviceGroupDesc = gSMPlaceOrderRateFeedService.toFetchServiceGroupDesc(session.getLanguageId(),dataTable.getRemittType());

			}else{
				setErrmsg("Multiple Records");
				RequestContext.getCurrentInstance().execute("error.show();");
				return status;
			}

			//bene Branch Id
			BigDecimal beneBranchId=gSMPlaceOrderRateFeedService.getBeneBranchId(getBeneficiaryBankId(),beneficaryMasterSeqId,getBeneficiaryName());
			setBeneficiaryBranchId(beneBranchId);

			HashMap<String, String> inputRoutingBankSetUpdetails = new HashMap<String, String>();

			inputRoutingBankSetUpdetails.put("P_APPLICATION_COUNTRY_ID", session.getCountryId().toPlainString());
			inputRoutingBankSetUpdetails.put("P_BENE_COUNTRY_ID", getBeneficiaryCountryId().toPlainString()); // beneficiary bank Country Id
			inputRoutingBankSetUpdetails.put("P_BENE_BANK_ID", getBeneficiaryBankId().toPlainString());
			inputRoutingBankSetUpdetails.put("P_BENE_BANK_BRANCH_ID",getBeneficiaryBranchId().toPlainString());

			// service group - Banking Channel for place order
			List<ServiceGroupMasterDesc> lstServiceCode = iServiceGroupMasterService.getServiceGroupDescList(dataTable.getRemittType());
			if (lstServiceCode != null) {
				ServiceGroupMasterDesc ServiceCode = lstServiceCode.get(0);
				inputRoutingBankSetUpdetails.put("P_SERVICE_GROUP_CODE", ServiceCode.getServiceGroupMasterId().getServiceGroupCode());
				setServiceGroupCode(ServiceCode.getServiceGroupMasterId().getServiceGroupCode());
			}

			if(dataTable.getBeneCurrencyId() != null){
				inputRoutingBankSetUpdetails.put("P_CURRENCY_ID",  dataTable.getBeneCurrencyId().toPlainString());
			}else{
				if(currencyId != null){
					inputRoutingBankSetUpdetails.put("P_CURRENCY_ID",  currencyId.toPlainString());
				}
			}

			inputRoutingBankSetUpdetails.put("P_USER_TYPE","BRANCH");
			setBooSingleService(true);

			setAccountSeqId(getAccountSequenceId());

			if(serviceGroupDesc.equalsIgnoreCase(Constants.CASHNAME)){

				List<BeneficaryAccount> lstBeneficaryAccount = iPersonalRemittanceService.getCashProductDetails(dataTable.getBeneficiaryAccountSeqId());

				if(lstBeneficaryAccount != null && lstBeneficaryAccount.size() != 0){

					BeneficaryAccount beneficaryAccount =lstBeneficaryAccount.get(0);
					// setting service Master Id
					List<ServiceMaster> lstServiceMaster = serviceMasterService.toFetchServiceRecordsServiceMaster(beneficaryAccount.getServicegropupId().getServiceGroupId());
					if(lstServiceMaster.size() != 0){
						ServiceMaster serviceMasterId = lstServiceMaster.get(0);
						setDataserviceid(serviceMasterId.getServiceId());
						setDatabenificaryservice(serviceMasterService.LocalServiceDescriptionFromDB(new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId"): "1"), getDataserviceid()).get(0).getLocalServiceDescription());
						setBooSingleService(true);
						setBooMultipleService(false);
						System.out.println("Service Desc :"+getDatabenificaryservice()+"\t setDatabenificaryservice :"+getDatabenificaryservice());
					}

					setRoutingCountry(beneficaryAccount.getBank().getFsCountryMaster().getCountryId()); 
					dataTable.setRoutingCountry(getRoutingCountry());
					String lstRcountry = generalService.getCountryName(new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId") : "1"),getRoutingCountry());
					if (lstRcountry != null) {
						setRoutingCountryName(lstRcountry);
						setBooMultipleRoutingCountry(false);
						setBooSingleRoutingCountry(true);
					}

					if(beneficaryAccount.getServiceProvider() != null){
						setRoutingBank(beneficaryAccount.getServiceProvider().getBankId()); 
						dataTable.setRoutingBank(getRoutingBank());
						String lstRBank = generalService.getBankName(getRoutingBank());
						if (lstRBank != null) {
							setRoutingBankName(lstRBank);
							setBooMultipleRoutingBank(false);
							setBooSingleRoutingBank(true);
						}
					}

					if(beneficaryAccount.getServiceProviderBranchId() != null){
						setRoutingBranch(beneficaryAccount.getServiceProviderBranchId()); // routing Bank Branch Id
						beneCashBranchId = beneficaryAccount.getServiceProviderBranchId();// routing Bank Branch Id
						List<BankBranch> lstRbranch = bankBranchDetailsService.getBankBranchByBranchID(beneCashBranchId);
						if (lstRbranch != null && lstRbranch.size() != 0) {
							BankBranch routingBranchName = lstRbranch.get(0);
							beneCashBranchName = routingBranchName.getBranchFullName();
						}
					}
					
					// to fetch remittance
					//need to check with routing setup Specific - S or ALL
					Map<String,Object> mapRoutingSetup = checkRoutingSetupBranchApplicability(dataTable);
					if(mapRoutingSetup != null && mapRoutingSetup.size() != 0){
						if(mapRoutingSetup.get("BranchApplicability") != null){
							String BranchAppl = (String)mapRoutingSetup.get("BranchApplicability");
							if(BranchAppl.equalsIgnoreCase(Constants.ALL)){
								if(mapRoutingSetup.get("RoutingBankBranchId") != null){
									beneCashBranchId =(BigDecimal)mapRoutingSetup.get("RoutingBankBranchId"); // routing Bank Branch Id
									setRoutingBranch(beneCashBranchId); // routing Bank Branch Id
									List<BankBranch> lstRbranch = bankBranchDetailsService.getBankBranchByBranchID(beneCashBranchId);
									if (lstRbranch != null && lstRbranch.size() != 0) {
										BankBranch routingBranchName = lstRbranch.get(0);
										beneCashBranchName = routingBranchName.getBranchFullName();
									}
								}
							}
						}
					}

					setCashRoutingBranch(beneCashBranchId);
					setCashRoutingBranchName(beneCashBranchName);

					dataTable.setRoutingBankBranch(beneCashBranchId);

					// to fetch remittance
					//status = remittancelistByBankId(dataTable);
					
					status = remittancelistByBankIdForCash(dataTable);

				}else
				{
					// if record not available
				}
			}else{
				
				HashMap<String, String> outputRoutingBankSetUpdetails = iPersonalRemittanceService.getRoutingBankSetupDetails(inputRoutingBankSetUpdetails);

				System.out.println("P_ERROR_MESSAGE"+outputRoutingBankSetUpdetails.get("P_ERROR_MESSAGE"));
				if (outputRoutingBankSetUpdetails.get("P_ERROR_MESSAGE") != null) {
					/*setProcedureError("EX_GET_ROUTING_SET_UP" + " : " +outputRoutingBankSetUpdetails.get("P_ERROR_MESSAGE"));
					RequestContext.getCurrentInstance().execute("procedureErr.show();");*/
					
					setErrorMessage("EX_GET_ROUTING_SET_UP" + " : " +outputRoutingBankSetUpdetails.get("P_ERROR_MESSAGE"));
					RequestContext.getCurrentInstance().execute("error.show();");
					
					setBooRenderRemitPanel(false);
					setRoutingDetailsShow(false);
					status = false;
				} else {

					setBooRenderRemitPanel(true);
					if (!outputRoutingBankSetUpdetails.get("P_SERVICE_MASTER_ID").equalsIgnoreCase("0")) {
						setDataserviceid(new BigDecimal(outputRoutingBankSetUpdetails.get("P_SERVICE_MASTER_ID")));
						setDatabenificaryservice(serviceMasterService.LocalServiceDescriptionFromDB(new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId"): "1"), getDataserviceid()).get(0).getLocalServiceDescription());
						setBooSingleService(true);
						setBooMultipleService(false);
						System.out.println("Service Desc :"+getDatabenificaryservice()+"\t setDatabenificaryservice :"+getDatabenificaryservice());

						setRoutingCountry(dataTable.getRoutingCountry());
						dataTable.setRoutingCountry(dataTable.getRoutingCountry());
						String lstRcountry = generalService.getCountryName(new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId") : "1"),getRoutingCountry());
						if (lstRcountry != null) {
							setRoutingCountryName(lstRcountry);
							setBooMultipleRoutingCountry(false);
							setBooSingleRoutingCountry(true);
						}

						setRoutingBank(dataTable.getRoutingBank());
						dataTable.setRoutingBank(dataTable.getRoutingBank());
						String lstRBank = generalService.getBankName(getRoutingBank());
						if (lstRBank != null) {
							setRoutingBankName(lstRBank);
							setBooMultipleRoutingBank(false);
							setBooSingleRoutingBank(true);
						}

						status = remittancelistByBankId(dataTable);

					}else{ 
						//setDataserviceid(new BigDecimal(outputRoutingBankSetUpdetails.get("P_SERVICE_MASTER_ID")));

						status = getServiceListDetails(dataTable);
					}

				}
			}

		}catch(Exception e){
			setErrmsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}

		return status;
	}
	
	// checking routing setup
	public HashMap<String,Object> checkRoutingSetupBranchApplicability(BranchStaffGSMRateDataTable dataTable){

		HashMap<String,Object> mapRoutingSetUpDt = new HashMap<String, Object>();

		HashMap<String,Object> hashMapDt = new HashMap<String, Object>();
		hashMapDt.put("APPLICATION_COUNTRY_ID", session.getCountryId());
		hashMapDt.put("CURRENCY_ID", dataTable.getBeneCurrencyId());
		hashMapDt.put("SERVICE_MASTER_ID", getDataserviceid());
		hashMapDt.put("ROUTING_COUNTRY_ID", getRoutingCountry());
		hashMapDt.put("ROUTING_BANK_ID", getRoutingBank());
		hashMapDt.put("BENEFICIARY_COUNTRY_ID", dataTable.getBeneficiaryCountryId());
		
		try {
			HashMap<String,Object> lstRoutingSetUp = iPersonalRemittanceService.getRoutingSetupForCashProduct(hashMapDt);

			if (lstRoutingSetUp != null) {
				mapRoutingSetUpDt = lstRoutingSetUp;
			} 

		} catch (Exception e) {
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}

		return mapRoutingSetUpDt;
	}

	/*public void routingsetupDetails(BranchStaffGSMRateDataTable dataTable)
	{

		try {
			HashMap<String, BigDecimal> rtnMap=iPlaceOnOrderCreationService.getBeneMasterIdCurrencyId(getBeneficiaryName(),getBeneficiaryCountryId(),getBeneficiaryBankId(),getServiceGroupId(),getCustomerRefNo());

			BigDecimal beneficaryMasterSeqId =  rtnMap.get("BeneficaryMasterSeqId");

			BigDecimal beneBranchId=gSMPlaceOrderRateFeedService.getBeneBranchId(getBeneficiaryBankId(),beneficaryMasterSeqId,getBeneficiaryName());
			setBeneficiaryBranchId(beneBranchId);


			HashMap<String, String> inputRoutingBankSetUpdetails = new HashMap<String, String>();

			inputRoutingBankSetUpdetails.put("P_APPLICATION_COUNTRY_ID", session.getCountryId().toPlainString());
			inputRoutingBankSetUpdetails.put("P_BENE_COUNTRY_ID", getBeneficiaryCountryId().toPlainString()); // beneficiary bank Country Id
			inputRoutingBankSetUpdetails.put("P_BENE_BANK_ID", getBeneficiaryBankId().toPlainString());
			inputRoutingBankSetUpdetails.put("P_BENE_BANK_BRANCH_ID",getBeneficiaryBranchId().toPlainString());

			List<ServiceGroupMasterDesc> lstServiceCode = iServiceGroupMasterService.getServiceGroupDescList(dataTable.getRemittType());
			if (lstServiceCode != null) {
				ServiceGroupMasterDesc ServiceCode = lstServiceCode.get(0);
				inputRoutingBankSetUpdetails.put("P_SERVICE_GROUP_CODE", ServiceCode.getServiceGroupMasterId().getServiceGroupCode());
				setServiceGroupCode(ServiceCode.getServiceGroupMasterId().getServiceGroupCode());
			}
			inputRoutingBankSetUpdetails.put("P_CURRENCY_ID", dataTable.getBeneCurrencyId().toPlainString());

			if( (session.getBranchId()!=null || session.getCustomerType().equals("E"))){ // && sessionStateManage.getRoleId().equals("1")
				inputRoutingBankSetUpdetails.put("P_USER_TYPE","BRANCH");
				setBooSingleService(true);
			}else if(session.getBranchId()!=null  &&  session.getUserType().equalsIgnoreCase("K")){
				inputRoutingBankSetUpdetails.put("P_USER_TYPE","KIOSK");
				setBooMultipleService(false);
			}else{
				setBooMultipleService(false);
			}


			setAccountSeqId(getAccountSequenceId());

			String remittanceType=gSMPlaceOrderRateFeedService.toFetchServiceGroupDesc(session.getLanguageId(),dataTable.getRemittType());



			if (remittanceType.equalsIgnoreCase(Constants.CASHNAME))
			{
				List<BeneficaryAccount> lstBeneficaryAccount= iPersonalRemittanceService.getCashProductDetails(getAccountSeqId());

				if(lstBeneficaryAccount.size()>0)
				{

					BeneficaryAccount beneficaryAccount =lstBeneficaryAccount.get(0);
					// setting service Master Id
					List<ServiceMaster> lstServiceMaster = serviceMasterService.toFetchServiceRecordsServiceMaster(beneficaryAccount.getServicegropupId().getServiceGroupId());
					if(lstServiceMaster.size() != 0){
						ServiceMaster serviceMasterId = lstServiceMaster.get(0);
						setDataserviceid(serviceMasterId.getServiceId());
						setDatabenificaryservice(serviceMasterService.LocalServiceDescriptionFromDB(new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId"): "1"), getDataserviceid()).get(0).getLocalServiceDescription());
						setBooSingleService(true);
						setBooMultipleService(false);
						System.out.println("Service Desc :"+getDatabenificaryservice()+"\t setDatabenificaryservice :"+getDatabenificaryservice());
					}

					setRoutingCountry(beneficaryAccount.getBank().getFsCountryMaster().getCountryId()); 
					String lstRcountry = generalService.getCountryName(new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId") : "1"),getRoutingCountry());
					if (lstRcountry != null) {
						setRoutingCountryName(lstRcountry);
						setBooMultipleRoutingCountry(false);
						setBooSingleRoutingCountry(true);
					}

					if(beneficaryAccount.getServiceProvider() != null){
						setRoutingBank(beneficaryAccount.getServiceProvider().getBankId()); 
						String lstRBank = generalService.getBankName(getRoutingBank());
						if (lstRBank != null) {
							setRoutingBankName(lstRBank);
							setBooMultipleRoutingBank(false);
							setBooSingleRoutingBank(true);
						}
					}

					if(beneficaryAccount.getBankBranch() != null){
						setRoutingBranch(beneficaryAccount.getServiceProviderBranchId()); // routing Bank Branch Id
						List<BankBranch> lstRbranch = bankBranchDetailsService.getBankBranchByBranchID(getRoutingBranch());
						if (lstRbranch != null && lstRbranch.size() != 0) {
							BankBranch routingBranchName = lstRbranch.get(0);
							setRoutingBranchName(routingBranchName.getBranchFullName());
							setBooMultipleRoutingBranch(false);
							setBooSingleRoutingBranch(true);
						}
					}


					// to fetch remittance
					//remittancelistByBankIdForCash(beneficaryAccount.getServicegropupId().getServiceGroupId());
					remittancelistByBankIdForCash(dataTable);
					setBooRenderRemitPanel(true);
					// to fetch remittance
					//remittancelistByBankId();
				}
			}else {

				HashMap<String, String> outputRoutingBankSetUpdetails = iPersonalRemittanceService.getRoutingBankSetupDetails(inputRoutingBankSetUpdetails);

				System.out.println("P_ERROR_MESSAGE"+outputRoutingBankSetUpdetails.get("P_ERROR_MESSAGE"));
				if (outputRoutingBankSetUpdetails.get("P_ERROR_MESSAGE") != null) {
					setProcedureError("EX_GET_ROUTING_SET_UP" + " : " +outputRoutingBankSetUpdetails.get("P_ERROR_MESSAGE"));
					RequestContext.getCurrentInstance().execute("procedureErr.show();");
					setBooRenderRemitPanel(false);
					setRoutingDetailsShow(false);
					return;
				} else {

					setBooRenderRemitPanel(true);
					if (!outputRoutingBankSetUpdetails.get("P_SERVICE_MASTER_ID").equalsIgnoreCase("0")) {
						setDataserviceid(new BigDecimal(outputRoutingBankSetUpdetails.get("P_SERVICE_MASTER_ID")));
						setDatabenificaryservice(serviceMasterService.LocalServiceDescriptionFromDB(new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId"): "1"), getDataserviceid()).get(0).getLocalServiceDescription());
						setBooSingleService(true);
						setBooMultipleService(false);
						System.out.println("Service Desc :"+getDatabenificaryservice()+"\t setDatabenificaryservice :"+getDatabenificaryservice());

						if (!outputRoutingBankSetUpdetails.get("P_ROUTING_COUNTRY_ID").equalsIgnoreCase("0")) {

							setRoutingCountry(new BigDecimal(outputRoutingBankSetUpdetails.get("P_ROUTING_COUNTRY_ID"))); 
							String lstRcountry = generalService.getCountryName(new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId") : "1"),getRoutingCountry());
							if (lstRcountry != null) {
								setRoutingCountryName(lstRcountry);
								setBooMultipleRoutingCountry(false);
								setBooSingleRoutingCountry(true);
							}

							if (!outputRoutingBankSetUpdetails.get("P_ROUTING_BANK_ID").equalsIgnoreCase("0")) {

								setRoutingBank(new BigDecimal(outputRoutingBankSetUpdetails.get("P_ROUTING_BANK_ID"))); 
								String lstRBank = generalService.getBankName(getRoutingBank());
								if (lstRBank != null) {
									setRoutingBankName(lstRBank);
									setBooMultipleRoutingBank(false);
									setBooSingleRoutingBank(true);
								}

								if (!outputRoutingBankSetUpdetails.get("P_REMITTANCE_MODE_ID").equalsIgnoreCase("0")) {

									// spl pool based on routing country , routing bank
									//specialRequestFcAmountCalculation();
									setRemitMode(new BigDecimal(outputRoutingBankSetUpdetails.get("P_REMITTANCE_MODE_ID")));
									String remitName = iRemitModeMaster.getRemittanceDesc(getRemitMode(),new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId"): "1"));
									if (remitName != null) {
										setRemittanceName(remitName);
										setBooMultipleRemit(false);
										setBooSingleRemit(true);
									}

									if (!outputRoutingBankSetUpdetails.get("P_DELIVERY_MODE_ID").equalsIgnoreCase("0")) {
										setDeliveryMode(new BigDecimal(outputRoutingBankSetUpdetails.get("P_DELIVERY_MODE_ID")));
										String deliveryName = iDeliveryModeMaster.getDeliveryDesc(
												getDeliveryMode(),new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId"): "1"));
										if (deliveryName != null) {
											setDeliveryModeInput(deliveryName);
											setBooRenderDeliveryModeDDPanel(false);
											setBooRenderDeliveryModeInputPanel(true);
										}

										if (!outputRoutingBankSetUpdetails.get("P_ROUTING_BANK_BRANCH_ID").equalsIgnoreCase("0")) {
											// set All values in form , No Need of view

											setRoutingBranch(new BigDecimal(outputRoutingBankSetUpdetails.get("P_ROUTING_BANK_BRANCH_ID"))); // routing Bank Branch Id
											List<BankBranch> lstRbranch = bankBranchDetailsService.getBankBranchByBranchID(getRoutingBranch());
											if (lstRbranch != null && lstRbranch.size() != 0) {
												BankBranch routingBranchName = lstRbranch.get(0);
												setRoutingBranchName(routingBranchName.getBranchFullName());
												setBooMultipleRoutingBranch(false);
												setBooSingleRoutingBranch(true);
											}
										} else {
											// to fetch Bank Branch
											bankBranchByBankView(dataTable);
										}

									} else {
										// to fetch Delivery
										deliverylistByRemitId(dataTable);
									}

								} else {
									// to fetch remittance
									remittancelistByBankId(dataTable);
								}

							} else {

								// setting routing bank Id from view
								bankDetailsByCountry(dataTable);

							}

						} else {
							// fetch routing country from view
							List<PopulateData> lstRoutingCountry = iPersonalRemittanceService.getRoutingCountryList(session.getCountryId(),dataTable.getBeneficiaryBankId(),getBeneficiaryBranchId(),dataTable.getBeneficiaryCountryId(),
									dataTable.getBeneCurrencyId(),getDataserviceid(),new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId"): "1"));

							if (lstRoutingCountry.size() == 0) {
								setBooSingleRoutingCountry(true);
								setBooMultipleRoutingCountry(false);
								setRoutingCountryName(null);
								setRoutingCountry(null);
								RequestContext.getCurrentInstance().execute("routingCountryNoData.show();");
								setRoutingDetailsShow(false);
								return;
							} else if (lstRoutingCountry.size() == 1) {
								setBooSingleRoutingCountry(true);
								setBooMultipleRoutingCountry(false);
								setRoutingCountry(lstRoutingCountry.get(0).getPopulateId());
								setRoutingCountryName(lstRoutingCountry.get(0).getPopulateName());
								// setting routing bank Id from view
								bankDetailsByCountry(dataTable);
							} else {
								setRoutingCountryName(null);
								setBooSingleRoutingCountry(false);
								setBooMultipleRoutingCountry(true);
								setRoutingCountrylst(lstRoutingCountry);
							}

						}

					}else{ 
						setDataserviceid(new BigDecimal(outputRoutingBankSetUpdetails.get("P_SERVICE_MASTER_ID")));
						getServiceListDetails(dataTable);
					}

				}
			}



		} catch (AMGException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}*/

	//click on Reject button
	public void toRejectSourecOfincome(BranchStaffGSMRateDataTable dataTable) throws AMGException{

		clearRoutingSetup();
		String rejectSts=branchStaffGSMRateService.checkPlaceOrderStatusForReject(dataTable.getRateOfferedPk());
		if(rejectSts!=null && rejectSts.equalsIgnoreCase(Constants.No))
		{
			RequestContext.getCurrentInstance().execute("alreadyCreate.show();");
			return;
		}
		if(rejectSts!=null && rejectSts.equalsIgnoreCase(Constants.D))
		{
			RequestContext.getCurrentInstance().execute("alreadyReject.show();");
			return;
		}

		branchStaffGSMRateService.toRejectRateForCustomer(dataTable.getRateOfferedPk(),session.getUserName());
		setBooRenderAdditionals(false);
		setBooRenderSaveOrExit(false);
		lstBranchStaffGSMRateDataTables.clear();
		if(getBooRenderBranchStaffRemittance()){
			tofetchAllRecordsBasedOnCustomerId();
		}else{
			tofetchAllRecords();
		}

	}

	//click on Reject button
	public void toRejectSourecOfincome1(BranchStaffGSMRateDataTable dataTable) throws AMGException{

		clearRoutingSetup();
		String rejectSts=branchStaffGSMRateService.checkPlaceOrderStatusForReject(dataTable.getRateOfferedPk());
		if(rejectSts!=null && rejectSts.equalsIgnoreCase(Constants.No))
		{
			RequestContext.getCurrentInstance().execute("alreadyCreate.show();");
			return;
		}
		if(rejectSts!=null && rejectSts.equalsIgnoreCase(Constants.D))
		{
			RequestContext.getCurrentInstance().execute("alreadyReject.show();");
			return;
		}

		branchStaffGSMRateService.toRejectRateForCustomer(dataTable.getRateOfferedPk(),session.getUserName());
		setBooRenderAdditionals(false);
		setBooRenderSaveOrExit(false);
		lstBranchStaffGSMRateDataTables.clear();
		
		if(getBooRenderBranchStaffRemittance()){
			tofetchAllRecordsBasedOnCustomerId();
		}else{
			tofetchAllRecords();
		}

	}


	//Source income details
	public void getSourceofIncomeDetails() {
		lstSourceofIncome.clear();
		List<SourceOfIncomeDescription> lstSource = foreignCurrencyPurchaseService.getSourceofIncome(session.getLanguageId());
		if (lstSource.size() != 0) {
			lstSourceofIncome.addAll(lstSource);
		}
	}
	//Payment Details
	public void toFetchPaymentDetails(){
		lstFetchAllPayMode.clear();
		List<PaymentModeDesc> list=gSMPlaceOrderRateFeedService.fetchPaymodeDesc(new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId") : "1"),Constants.Yes);
		if(list.size() !=0){
			lstFetchAllPayMode.addAll(list);   
		}

	}
	//Additional Bank Details Starts
	public void dynamicLevel() throws AMGException {
		listDynamicLebel.clear();
		setExceptionMessage("");
		List<AdditionalDataDisplayView> serviceAppRuleList = iPersonalRemittanceService.getAdditionalDataFromServiceApplicability(session.getCountryId(),
				getRoutingCountry(), getCurrencyId(), getRemitMode(),getDeliveryMode());
		if (serviceAppRuleList.size() > 0) {
			for (AdditionalDataDisplayView serviceRule : serviceAppRuleList) {
				AddDynamicLebel addDynamic = new AddDynamicLebel();
				addDynamic.setLebelId(serviceRule.getServiceApplicabilityRuleId());
				addDynamic.setFieldLength(serviceRule.getFieldLength());
				if(serviceRule.getFieldDescription()!=null){
					addDynamic.setLebelDesc(serviceRule.getFieldDescription());
				}

				addDynamic.setFlexiField(serviceRule.getFlexField());
				addDynamic.setValidation(serviceRule.getValidationsReq());
				addDynamic.setNavicable(serviceRule.getIsRendered());
				addDynamic.setMinLenght(serviceRule.getMinLength());
				addDynamic.setMaxLenght(serviceRule.getMaxLength());

				if(serviceRule.getIsRequired()!=null && serviceRule.getIsRequired().equalsIgnoreCase(Constants.Yes)){
					addDynamic.setMandatory("*");
					addDynamic.setRequired(true);
				}

				listDynamicLebel.add(addDynamic);
			}

			setAdditionalCheck(true);

		}else{
			setAdditionalCheck(false);
		}
	}

	public void matchData() {
		setExceptionMessage(null);
		listAdditionalBankDataTable.clear();
		try {
			for (AddDynamicLebel dyamicLabel : listDynamicLebel) {
				AddAdditionalBankData adddata = new AddAdditionalBankData();
				if(dyamicLabel.getValidation()!=null && dyamicLabel.getValidation().equalsIgnoreCase(Constants.Yes)){
					List<AdditionalBankRuleMap> listAdditinalBankfield = iPersonalRemittanceService.getDynamicLevelMatch(getRoutingCountry(),dyamicLabel.getFlexiField());
					if (listAdditinalBankfield.size() > 0) {
						for (AdditionalBankRuleMap listAdd : listAdditinalBankfield) {

							List<AdditionalBankDetailsView> listAdditionaView = iPersonalRemittanceService.getAmiecDetails(getCurrencyId(),getRoutingBank(), getRemitMode(),
									getDeliveryMode(),getRoutingCountry(),listAdd.getFlexField());

							if(listAdditionaView.size()>0){

								//setting dynamic functionality
								adddata.setMandatory(dyamicLabel.getMandatory());
								if (dyamicLabel.getMinLenght() != null) {
									adddata.setMinLenght(dyamicLabel.getMinLenght().intValue());
								}else{
									adddata.setMinLenght(0);
								}
								if(dyamicLabel.getMaxLenght()!=null){
									adddata.setMaxLenght(dyamicLabel.getMaxLenght());
								}else{
									adddata.setMaxLenght(new BigDecimal(50));	
								}
								adddata.setFieldLength(dyamicLabel.getFieldLength());
								adddata.setRequired(dyamicLabel.getRequired());

								adddata.setAdditionalBankRuleFiledId(listAdd.getAdditionalBankRuleId());
								adddata.setFlexiField(listAdd.getFlexField());
								if(listAdd.getFieldName() != null){
									adddata.setAdditionalDesc(listAdd.getFieldName());
								}else{
									setExceptionMessage((getExceptionMessage().equalsIgnoreCase("") ? "" : ",")  + dyamicLabel.getFlexiField());
								}
								adddata.setRenderInputText(false);
								adddata.setRenderSelect(true);
								adddata.setRenderOneSelect(false);
								adddata.setListadditionAmiecData(listAdditionaView);

							}
						}

						if(getExceptionMessage() != null && !getExceptionMessage().equalsIgnoreCase("")){
							setAdditionalCheck(false);
							setExceptionMessage(getExceptionMessage());
							RequestContext.getCurrentInstance().execute("dataexception.show();");
						}else{
							setAdditionalCheck(true);
							setExceptionMessage(null);
						}
					}
				}else{
					/*if (dyamicLabel.getValidation()!=null && dyamicLabel.getValidation().equalsIgnoreCase(Constants.No)) {*/
					adddata.setMandatory(dyamicLabel.getMandatory());
					if (dyamicLabel.getMinLenght() != null) {
						adddata.setMinLenght(dyamicLabel.getMinLenght().intValue());
					}else{
						adddata.setMinLenght(0);
					}
					if(dyamicLabel.getMaxLenght()!=null){
						adddata.setMaxLenght(dyamicLabel.getMaxLenght());
					}else{
						adddata.setMaxLenght(new BigDecimal(50));	
					}

					adddata.setFieldLength(dyamicLabel.getFieldLength());
					adddata.setRequired(dyamicLabel.getRequired());
					adddata.setRenderInputText(true);
					adddata.setRenderSelect(false);
					adddata.setRenderOneSelect(false);
					adddata.setFlexiField(dyamicLabel.getFlexiField());
					if(dyamicLabel.getLebelDesc()!=null){
						adddata.setAdditionalDesc(dyamicLabel.getLebelDesc());
					}else{
						List<AdditionalBankRuleMap> listAdditinalBankfield = iPersonalRemittanceService.getDynamicLevelMatch(getRoutingCountry(),dyamicLabel.getFlexiField());
						if(listAdditinalBankfield.size()>0){
							if(listAdditinalBankfield.get(0).getFieldName() != null){
								adddata.setAdditionalDesc(listAdditinalBankfield.get(0).getFieldName());
							}else{
								setExceptionMessage((getExceptionMessage().equalsIgnoreCase("") ? "" : ",")  + dyamicLabel.getFlexiField());
							}
						}else{
							setExceptionMessage((getExceptionMessage().equalsIgnoreCase("") ? "" : ",")  + dyamicLabel.getFlexiField());
						}
					}

				}
				listAdditionalBankDataTable.add(adddata);
			}

			if(getExceptionMessage() != null && !getExceptionMessage().equalsIgnoreCase("")){
				setAdditionalCheck(false);
				setExceptionMessage(getExceptionMessage());
				RequestContext.getCurrentInstance().execute("dataexception.show();");
			}else{
				setAdditionalCheck(true);
				setExceptionMessage(null);
			}

		} catch (Exception e) {
			log.info(e);
		}
	}
	//Additional Bank Details Starts

	//save Functionality starts
	public void save(){

		if( getDataserviceid()== null || getRoutingCountry() == null || getRemitMode() == null || getDeliveryMode()==null || getRoutingBranch()== null)
		{
			RequestContext.getCurrentInstance().execute("manDatoryCheck.show();");
			return;
		}

		RatePlaceOrder ratePlaceOrder = new RatePlaceOrder();
		ratePlaceOrder.setRatePlaceOrderId(getRateOfferedPk());
		ratePlaceOrder.setAppointmentTime(getVisitDate());
		ratePlaceOrder.setSourceOfincomeId(getSourceId());
		ratePlaceOrder.setCollectionMode(getPaymentmodeId());
		HashMap<String,Object> saveMap= new HashMap<String,Object>();
		saveMap.put("PLACE_ORDER_PK", getRateOfferedPk());
		//saveMap.put("CUSTOMER_VISIT_TIME", getVisitTime());
		saveMap.put("SOURCE_OF_INCOME", getSourceId());
		//saveMap.put("PAYMENT_MODE", getPaymentmodeId());
		saveMap.put("USER_NAME", session.getUserName());
		List<RatePlaceOrderAddlData>  lstRatePlaceOrderAddlData= saveAdditionalInstnData();

		saveMap.put("PLACE_ORDER_ADDL_DATA", lstRatePlaceOrderAddlData);

		try {

			//String approveMsg = gSMPlaceOrderRateFeedService.approveAllRecords(getRateOfferedIdPk(),getRateOffered(),getCustIndicator(),getDataserviceid(),getRoutingCountry(),getRoutingBank(),getRemitMode(),getDeliveryMode(),getRoutingBranch(),session.getUserName(),getCustomerUniqueNumber());
			HashMap<String, BigDecimal> rtnMap=iPlaceOnOrderCreationService.getBeneMasterIdCurrencyId(getBeneficiaryName(),getBeneficiaryCountryId(),getBeneficiaryBankId(),getServiceGroupId(),getCustomerRefNo(),getAccountNumber());

			BigDecimal beneficaryMasterSeqId =  rtnMap.get("BeneficaryMasterSeqId");

			saveMap.put("DATA_SERVICE_ID", getDataserviceid());
			saveMap.put("ROUTING_COUNTRY_ID", getRoutingCountry());
			saveMap.put("REMIT_MODE", getRemitMode());
			saveMap.put("DELIVERY_MODE", getDeliveryMode());
			saveMap.put("ROUTING_BRANCH", getRoutingBranch());
			saveMap.put("BENEFICIARY_MASTER_ID",beneficaryMasterSeqId);
			saveMap.put("BENEFICIARY_ACC_SEQ_ID", getAccountSequenceId());
			saveMap.put("BENEFICIARY_ACC_NO", getAccountNumber());
			saveMap.put("ROUTING_BANK_ID", getRoutingBank());

			String approvalMsg=null;
			approvalMsg=branchStaffGSMRateService.saveOrUpdatePlaceOrderAddlData(saveMap);
			if(approvalMsg.equalsIgnoreCase("Success")){
				clearFields();
				//setBooRenderTimePanel(false);
				createRemittanceApplicationForPlaceOrder();
			}else{
				RequestContext.getCurrentInstance().execute("alreadyapprov.show();");
				return;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//RequestContext.getCurrentInstance().execute("complete.show();");

	}

	public void clearFields(){

		setDocumentFinanceYear(null);
		setDocumentNumber(null);
		setCurrencyId(null);
		setCurrencyName(null);
		setCustomerRefAndName(null);
		//setCountryBranchId(null);
		//setCountryBranchName(null);
		setBeneficiaryBankId(null);
		setBeneficiaryBankName(null);
		setTransctionAmount(null);
		setVisitDate(null);
		setSourceId(null);
		setPaymentmodeId(null);
		setErrorMessage(null);

	}

	// to check null
	private String nullCheck(String custname) {
		return custname == null ? "" : custname;
	}

	public  void createRemittanceApplicationForPlaceOrder() {
		try{
			HashMap<String,String> inputValues = new HashMap<String,String>();
			inputValues.put("P_COMP_ID", session.getCompanyId().toPlainString());
			inputValues.put("PLACE_ORDER_PK", getRateOfferedPk().toPlainString());

			HashMap<String,String> outPutValues=placeOrederBranchSupportService.createRemitAppProcedure(inputValues);

			String errMsg=outPutValues.get("P_ERROR_MESG");
			if(errMsg!=null && errMsg.equalsIgnoreCase(""))
			{
				setRateOfferedPk(null);

				PersonalRemittanceBean personalRemittanceBean = (PersonalRemittanceBean) appContext.getBean("personalRemittanceBean");
				List<Customer> custList= iCustomerRegistrationBranchService.getCustomerInfo(getCustomerId());
				if(custList.size()>0){
					personalRemittanceBean.setCustomerFullName(nullCheck(custList.get(0).getFirstName()) + " " + nullCheck(custList.get(0).getShortName()) + " " + nullCheck(custList.get(0).getLastName()));
					personalRemittanceBean.setCustomerLocalFullName(nullCheck(custList.get(0).getFirstNameLocal()) + " " + nullCheck(custList.get(0).getMiddleNameLocal()) + " " + nullCheck(custList.get(0).getLastNameLocal()));
				}


				personalRemittanceBean.setCustomerNo(getCustomerId());
				personalRemittanceBean.checkShoppingCartRecords();

				//personalRemittanceBean.getShoppingCartDetails(getCustomerId());

				FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/PersonalRemittance.xhtml");

			}else
			{
				setErrorMessage("Procedure Error -- EX_P_PO_APPLICATION: "+errMsg);
				log.error("clickOnOKSave  :"+ errMsg);
				RequestContext.getCurrentInstance().execute("error.show();");
			}

		}catch(Exception e)
		{
			setErrorMessage( e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}

	}

	public List<RatePlaceOrderAddlData> saveAdditionalInstnData()
	{
		List<RatePlaceOrderAddlData> lstAddInstrData = new ArrayList<RatePlaceOrderAddlData>();
		Document document = new Document();
		document.setDocumentID(generalService.getDocument(new BigDecimal(Constants.DOCUMENT_CODE_FOR_PLACEORDER),
				new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId") : "1")).get(0).getDocumentID());
		// company Id
		CompanyMaster companymaster = new CompanyMaster();
		companymaster.setCompanyId(session.getCompanyId());
		// Application Country
		CountryMaster countrymaster = new CountryMaster();
		countrymaster.setCountryId(session.getCountryId());

		for (AddAdditionalBankData dynamicList : listAdditionalBankDataTable) {

			RatePlaceOrderAddlData ratePlaceOrderAddlData = new RatePlaceOrderAddlData();
			/*AdditionalBankRuleMap additionalBank = new AdditionalBankRuleMap();
				if(dynamicList.getAdditionalBankRuleFiledId()!=null){
					additionalBank.setAdditionalBankRuleId(dynamicList.getAdditionalBankRuleFiledId());
					ratePlaceOrderAddlData.setAdditionalBankFieldsId(additionalBank);
				}*/

			// System.out.println("dynamicList.getFlexiField() :"+dynamicList.getFlexiField()+"\t dynamicList.getAmicCode() :"+dynamicList.getAmicCode()); 
			RatePlaceOrder ratePlaceOrder = new RatePlaceOrder();
			ratePlaceOrder.setRatePlaceOrderId(getRateOfferedPk());
			ratePlaceOrderAddlData.setRatePlaceOrder(ratePlaceOrder);

			ratePlaceOrderAddlData.setAdditionalBankRuleId(dynamicList.getAdditionalBankRuleFiledId());


			ratePlaceOrderAddlData.setFlexFiled(dynamicList.getFlexiField());
			if(dynamicList.getAdditionalBankRuleFiledId()!=null){
				String amiecdec = dynamicList.getVariableName();
				String amicCode=null;
				String amicDesc=null;
				if(amiecdec!=null)
				{

					String [] amiecdecValues =amiecdec.split("-");
					if(amiecdecValues.length>0)
					{
						amicCode=amiecdecValues[0];		

					}
					if(amiecdecValues.length>1)
					{
						amicDesc=amiecdecValues[1];		

					}

				}

				ratePlaceOrderAddlData.setAmiecCode(amicCode);
				ratePlaceOrderAddlData.setFlexFiledValue(amicDesc);
			}else{
				ratePlaceOrderAddlData.setAmiecCode(Constants.AMIEC_CODE);
				ratePlaceOrderAddlData.setFlexFiledValue(dynamicList.getVariableName());
			}

			ratePlaceOrderAddlData.setDocumentId(generalService.getDocument(new BigDecimal(Constants.DOCUMENT_CODE_FOR_PLACEORDER),
					new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId") : "1")).get(0).getDocumentID());
			ratePlaceOrderAddlData.setCompanyId(session.getCompanyId());
			ratePlaceOrderAddlData.setApplicationCountryId(session.getCountryId());
			ratePlaceOrderAddlData.setDocumentCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_PLACEORDER));
			ratePlaceOrderAddlData.setDocumentFinanceYear(getDocumentFinanceYear());
			ratePlaceOrderAddlData.setDocumentNo(getDocumentNumber());

			ratePlaceOrderAddlData.setCreatedBy(session.getUserName());
			ratePlaceOrderAddlData.setCreatedDate(new Date());


			// iPersonalRemittanceService.saveAdditionalInsData(additionalInsData);

			lstAddInstrData.add(ratePlaceOrderAddlData);
		}
		return lstAddInstrData;
	}



	public void exit(){
		lstBranchStaffGSMRateDataTables.clear();
		try {
			setErrorMessage(null);
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

	public void tofetchCustomerPlaceOrderRecords(BigDecimal customerId){
		lstBranchStaffGSMRateDataTables.clear();
		List<RatePlaceOrder> lstRatePlaceOrder=branchStaffGSMRateService.tofetchCustomerPlaceOrderRecords(customerId,new BigDecimal(session.getBranchId()));
		if(lstRatePlaceOrder.size()>0){
			for (RatePlaceOrder ratePlaceOrder : lstRatePlaceOrder) {
				BranchStaffGSMRateDataTable branchStaffGSMRateDT=new BranchStaffGSMRateDataTable();
				branchStaffGSMRateDT.setRateOfferedPk(ratePlaceOrder.getRatePlaceOrderId());
				branchStaffGSMRateDT.setCustomerId(ratePlaceOrder.getCustomerId());
				String custmerName=null;
				BigDecimal custrRef=null;
				String BeneName=null;
				String beneBankName=null;
				String curQtyName=null;
				//to Fetch Customer reference and Name
				//custrRef=gSMPlaceOrderRateFeedService.toFetchCustomerRef(ratePlaceOrder.getCustomerId());
				custmerName=generalService.getCustomerNameCustomerId(ratePlaceOrder.getCustomerId());
				branchStaffGSMRateDT.setCustomerName(custmerName);
				branchStaffGSMRateDT.setCustomerRefNo(ratePlaceOrder.getCustomerreference());
				if(ratePlaceOrder.getCustomerreference() != null){
					branchStaffGSMRateDT.setCustomerRefAndName(ratePlaceOrder.getCustomerreference().toString()+ "-" +custmerName);
				}else{
					branchStaffGSMRateDT.setCustomerRefAndName(custmerName);	
				}
				branchStaffGSMRateDT.setDocumentNumber(ratePlaceOrder.getDocumentNumber());
				branchStaffGSMRateDT.setDocumentFinanceYear(ratePlaceOrder.getDocumentFinanceYear());
				//to bene name
				branchStaffGSMRateDT.setBeneficiaryMasterId(ratePlaceOrder.getBeneficiaryMasterId().getBeneficaryMasterSeqId());
				BeneName=gSMPlaceOrderRateFeedService.toFetchBeneficiaryName(ratePlaceOrder.getBeneficiaryMasterId().getBeneficaryMasterSeqId());
				branchStaffGSMRateDT.setBeneficiaryName(BeneName);

				branchStaffGSMRateDT.setBeneCurrencyId(ratePlaceOrder.getDestinationCurrenyId());
				String currencyName = generalService.getCurrencyName(ratePlaceOrder.getDestinationCurrenyId());
				branchStaffGSMRateDT.setCurrencyName(currencyName);

				/*List<PopulateData> lstofCurrency = new ArrayList<PopulateData>();
				lstofCurrency= iPlaceOnOrderCreationService.getBasedOnCountyCurrency(ratePlaceOrder.getBeneficiaryCountryId());
				if(lstofCurrency!=null && lstofCurrency.size()>0)
				{
					branchStaffGSMRateDT.setBeneCurrencyId(lstofCurrency.get(0).getPopulateId());
				}*/

				//bene bank name
				branchStaffGSMRateDT.setBeneficiaryBankId(ratePlaceOrder.getBeneficiaryBankId());
				beneBankName=generalService.getBankName(ratePlaceOrder.getBeneficiaryBankId());
				branchStaffGSMRateDT.setBeneficiaryBankName(beneBankName);
				branchStaffGSMRateDT.setRateOffered(ratePlaceOrder.getRateOffered());
				branchStaffGSMRateDT.setTransctionAmount(ratePlaceOrder.getTransactionAmount());
				branchStaffGSMRateDT.setCurrencyId(ratePlaceOrder.getRequestCurrencyId());
				//currency name qty code
				//ratePlaceOrder.getBeneficiaryMasterId().getBeneficaryMasterSeqId(),ratePlaceOrder.getAccountSeqquenceId().getBeneficaryAccountSeqId()
				//curQtyName=gSMPlaceOrderRateFeedService.toFetchCurrencyQtyName(ratePlaceOrder.getDestinationCurrenyId());
				curQtyName=gSMPlaceOrderRateFeedService.toFetchCurrencyQtyName(ratePlaceOrder.getRequestCurrencyId());
				branchStaffGSMRateDT.setCurrencyQtyName("["+curQtyName+"]");
				branchStaffGSMRateDT.setAmountAndQtyName(ratePlaceOrder.getTransactionAmount()+ "-" +curQtyName);
				//remittance and service , delivery
				branchStaffGSMRateDT.setRemitServiceId(ratePlaceOrder.getServiceMasterId());
				branchStaffGSMRateDT.setRemitRemittanceId(ratePlaceOrder.getRemittanceModeId());
				branchStaffGSMRateDT.setRemitDeliveryId(ratePlaceOrder.getDeliveryModeId());
				branchStaffGSMRateDT.setCreatedBy(ratePlaceOrder.getCreatedBy());
				branchStaffGSMRateDT.setCreatedDate(ratePlaceOrder.getCreatedDate());
				branchStaffGSMRateDT.setModifiedBy(ratePlaceOrder.getModifiedBy());
				branchStaffGSMRateDT.setModifiedDate(ratePlaceOrder.getModifiedDate());
				branchStaffGSMRateDT.setApprovedBy(ratePlaceOrder.getApprovedBy());
				branchStaffGSMRateDT.setApprovedDate(ratePlaceOrder.getApprovedDate());
				branchStaffGSMRateDT.setIsActive(ratePlaceOrder.getIsActive());
				branchStaffGSMRateDT.setRoutingCountry(ratePlaceOrder.getRoutingCountryId());
				branchStaffGSMRateDT.setRoutingBank(ratePlaceOrder.getRoutingBankId());
				lstBranchStaffGSMRateDataTables.add(branchStaffGSMRateDT);
			}
		}
	}

	public void callNormalRemiitancePage()
	{
		try{
			PersonalRemittanceBean personalRemittanceBean = (PersonalRemittanceBean) appContext.getBean("personalRemittanceBean");

			personalRemittanceBean.setCustomerNo(getCustomerId());
			personalRemittanceBean.setIdNumber(getIdNumber());
			personalRemittanceBean.setSelectCard(getSelectCard());
			//personalRemittanceBean.setShowPlaceOrderScreen(Boolean.FALSE);
			personalRemittanceBean.goFromOldSmartCardpanel();

			//personalRemittanceBean.getShoppingCartDetails(getCustomerId());

			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/PersonalRemittance.xhtml");

		}catch(Exception e)
		{

		}
	}

	public void rejectRecord(BranchStaffGSMRateDataTable dataTable)
	{
		if(dataTable.getRateOfferedPk()!=null)
		{
			gSMPlaceOrderRateFeedService.rejectPlaceorder(dataTable.getRateOfferedPk());
			tofetchAllRecords();
		}

	}

	public void clickOnOKSave(){
		lstBranchStaffGSMRateDataTables.clear();
		clearAllFieldsForSave();
		tofetchAllRecords();
		setBooRenderAdditionals(false);
		setBooRenderSaveOrExit(false);
		setRateOfferedPk(null);
		setSourceId(null);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../onlinespecialrate/BranchStaffGsmRate.xhtml");
		} catch (Exception exception) {
			setErrorMessage(exception.getMessage());
			log.error("groupSalesManagerApprovalPageNavigation  :"+ exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}
	}

	public void clickOnOKSave1(){
		lstBranchStaffGSMRateDataTables.clear();
		clearAllFieldsForSave();
		tofetchAllRecordsBasedOnCustomerId();
		setBooRenderAdditionals(false);
		setBooRenderSaveOrExit(false);
		setRateOfferedPk(null);
		setSourceId(null);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../onlinespecialrate/BranchStaffGsmRateRemitt.xhtml");
		} catch (Exception exception) {
			setErrorMessage(exception.getMessage());
			log.error("groupSalesManagerApprovalPageNavigation  :"+ exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}
	}

	public void negotiatePlaceOrder(BranchStaffGSMRateDataTable dataTable)
	{
		clearRoutingSetup();
		//BigDecimal docRefNumber=getDocumentSerialIdNumber(Constants.U);
		/*if(docRefNumber.compareTo(BigDecimal.ZERO)==0)
		{
			//RequestContext.getCurrentInstance().execute("docZero.show();");
			return;
		}*/

		String rejectSts=branchStaffGSMRateService.checkPlaceOrderStatusForNegotiate(dataTable.getRateOfferedPk());
		if(rejectSts!=null && rejectSts.equalsIgnoreCase(Constants.No))
		{
			RequestContext.getCurrentInstance().execute("alreadyNegotiate.show();");
			return;
		}
		if(rejectSts!=null && rejectSts.equalsIgnoreCase(Constants.D))
		{
			RequestContext.getCurrentInstance().execute("alreadyNegotiate.show();");
			return;
		}

		branchStaffGSMRateService.createPlaceOrderForNegotiate(dataTable.getRateOfferedPk(), session.getUserName(), dataTable.getDocumentNumber());
		setBooRenderAdditionals(false);
		setBooRenderSaveOrExit(false);
		if(getBooRenderBranchStaffRemittance()){
			tofetchAllRecordsBasedOnCustomerId();
		}else{
			tofetchAllRecords();
		}
	}

	public void negotiatePlaceOrder1(BranchStaffGSMRateDataTable dataTable)
	{
		clearRoutingSetup();
		//BigDecimal docRefNumber=getDocumentSerialIdNumber(Constants.U);
		/*if(docRefNumber.compareTo(BigDecimal.ZERO)==0)
		{
			//RequestContext.getCurrentInstance().execute("docZero.show();");
			return;
		}*/

		String rejectSts=branchStaffGSMRateService.checkPlaceOrderStatusForNegotiate(dataTable.getRateOfferedPk());
		if(rejectSts!=null && rejectSts.equalsIgnoreCase(Constants.No))
		{
			RequestContext.getCurrentInstance().execute("alreadyNegotiate.show();");
			return;
		}
		if(rejectSts!=null && rejectSts.equalsIgnoreCase(Constants.D))
		{
			RequestContext.getCurrentInstance().execute("alreadyNegotiate.show();");
			return;
		}

		branchStaffGSMRateService.createPlaceOrderForNegotiate(dataTable.getRateOfferedPk(), session.getUserName(), dataTable.getDocumentNumber());
		setBooRenderAdditionals(false);
		setBooRenderSaveOrExit(false);
		
		if(getBooRenderBranchStaffRemittance()){
			tofetchAllRecordsBasedOnCustomerId();
		}else{
			tofetchAllRecords();
		}
		
	}



	public BigDecimal getDocumentSerialIdNumber(String processIn) {

		String documentSerialId="0";
		try {
			HashMap<String, String> outPutValues= generalService.getNextDocumentRefNumber(session.getCountryId().intValue() , session.getCompanyId().intValue(), Integer.parseInt(Constants.DOCUMENT_CODE_FOR_PLACEORDER) , getFinaceYear().intValue(),processIn,session.getCountryBranchCode());

			//String proceErrorFlag=outPutValues.get("PROCE_ERORRFLAG");
			String proceErrorMsg=outPutValues.get("PROCE_ERORRMSG");
			if(proceErrorMsg!=null)
			{
				setBooDocVisble(Boolean.TRUE);
				setErrmsg(proceErrorMsg);
				RequestContext.getCurrentInstance().execute("proceErr.show();");
				return BigDecimal.ZERO;
			}else if(outPutValues.get("DOCNO") !=null && new BigDecimal(outPutValues.get("DOCNO")).compareTo(BigDecimal.ZERO)==0){
				setBooDocVisble(Boolean.TRUE);
				RequestContext.getCurrentInstance().execute("docZero.show();");
				return BigDecimal.ZERO;

			}else
			{
				setBooDocVisble(Boolean.FALSE);
				documentSerialId=outPutValues.get("DOCNO");
			}
		} catch (NumberFormatException | AMGException e) {

			setBooDocVisble(Boolean.TRUE);
			setErrmsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("proceErr.show();");
			return BigDecimal.ZERO;
		}


		return new BigDecimal(documentSerialId);
	}

	public BigDecimal getFinaceYear() {
		BigDecimal finaceYear=BigDecimal.ZERO;
		try{
			List<UserFinancialYear> financialYearList =generalService .getDealYear(new Date());
			log.info("financialYearList :"+financialYearList.size());
			if(financialYearList!=null && financialYearList.size()>0){
				if(financialYearList.get(0).getFinancialYear()!=null){
					finaceYear = financialYearList.get(0).getFinancialYear();
				}

			}
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrmsg(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return BigDecimal.ZERO;       
		}
		return finaceYear;
	}


	public boolean populateBeneficiary(BranchStaffGSMRateDataTable dataTable){

		boolean beneNameCheck=false;
		try{
			setBeneficiaryName(null);
			List<PopulateData> lstBeneName=branchStaffGSMRateService.getBeneficiarNameList(dataTable.getCustomerId(), dataTable.getBeneficiaryCountryId(), dataTable.getRemittType(),dataTable.getCustomerRefNo(),dataTable.getBeneficiaryBankId());

			if(lstBeneName!=null && lstBeneName.size()>0)
			{
				setBeneficiaryList(lstBeneName);
				beneNameCheck=true;
			}else
			{
				beneNameCheck=false;
			}

		}catch(Exception e){
			setErrmsg( e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}

		return beneNameCheck;
	}

	public void populateAccountNumber()
	{

		try{
			setAccountNumber(null);
			setAccountSequenceId(null);
			setLstAccountNumber(null);
			setBeneAccSeqId(null);
			if(getBeneficiaryName()!=null){

				List<PopulateData> accountNumberList= iPlaceOnOrderCreationService.getBeneAccountNumber(getBeneficiaryName(),getBeneficiaryCountryId(),getBeneficiaryBankId(),getServiceGroupId(),getCustomerRefNo());
				if(accountNumberList!=null && accountNumberList.size()==0  )
				{
					setBeneficiaryName(null);
					setRoutingDetailsShow(false);
					RequestContext.getCurrentInstance().execute("accountNo.show();");
					return;
				}
				log.info( "============bank accountnumList======="+accountNumberList.size());
				HashMap<BigDecimal,String> beneAccNoMap = new HashMap<BigDecimal,String> ();
				for(PopulateData populateData :accountNumberList)
				{
					beneAccNoMap.put(populateData.getPopulateId(), populateData.getPopulateCode());
				}
				//setBeneAccNoMap(beneAccNoMap);
				if(accountNumberList!=null && accountNumberList.size()==1)
				{
					setBooRenderBnkAccNoList(false);
					setBooRenderBnkAccNo(true);
					setAccountNumber(accountNumberList.get( 0).getPopulateCode());
					setAccountSequenceId(accountNumberList.get(0).getPopulateId());

					HashMap<String, BigDecimal> rtnMap=iPlaceOnOrderCreationService.getBeneMasterIdCurrencyId(getBeneficiaryName(),getBeneficiaryCountryId(),getBeneficiaryBankId(),getServiceGroupId(),getCustomerRefNo(),getAccountNumber());

					BigDecimal beneficaryMasterSeqId =  rtnMap.get("BeneficaryMasterSeqId");

				}else if(accountNumberList!=null && accountNumberList.size()>1){
					setBooRenderBnkAccNoList(true);
					setBooRenderBnkAccNo(false);
					setLstAccountNumber(accountNumberList);
				}

				setRoutingDetailsShow(true);
				routingsetupDetails(getAcceptPlaceOrderDataTable());
				addtionalMethods();

			}else
			{
				clearRoutingSetup();
			}

		}catch(Exception e){
			setErrmsg( e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}

	}

	//procedure internal calling methods start
	public boolean remittancelistByBankIdForCash(BranchStaffGSMRateDataTable dataTable){

		boolean status = false;

		dataTable.setRemitServiceId(getDataserviceid());

		// spl pool based on routing country , routing bank
		List<PopulateData> lstRemitView;
		try {
			lstRemitView = iPersonalRemittanceService.getRemittanceListByCountryBankForCashProduct(session.getCountryId(),dataTable.getBeneficiaryBankId(),getBeneficiaryBranchId(),
					dataTable.getBeneficiaryCountryId(),dataTable.getBeneCurrencyId(),getDataserviceid(),getRoutingCountry(),getRoutingBank(),getRoutingBranch(),
					new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId") : "1"));
			if (lstRemitView.size() == 0) {
				setBooSingleRemit(true);
				setBooMultipleRemit(false);
				setRemittanceName(null);
				setRemitMode(null);
				//setRoutingDetailsShow(false);
				//RequestContext.getCurrentInstance().execute("remittanceNoData.show();");
				status = false;
			} else if (lstRemitView.size() == 1) {
				setBooSingleRemit(true);
				setBooMultipleRemit(false);
				setRemittanceName(lstRemitView.get(0).getPopulateName());
				setRemitMode(lstRemitView.get(0).getPopulateId());
				status = deliverylistByRemitIdForCash(dataTable);
			} else {
				status = true;
				setRemittanceName(null);
				setRemitMode(null);
				setBooSingleRemit(false);
				setBooMultipleRemit(true);

				setRemittanceName(lstRemitView.get(0).getPopulateName());
				setRemitMode(lstRemitView.get(0).getPopulateId());
				status = deliverylistByRemitIdForCash(dataTable);

				setLstofRemittance(lstRemitView);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return status;
	}

	public boolean deliverylistByRemitIdForCash(BranchStaffGSMRateDataTable dataTable){

		boolean status = false;

		dataTable.setRemitRemittanceId(getRemitMode());

		List<PopulateData> lstDeliveryView;
		try {
			lstDeliveryView = iPersonalRemittanceService
					.getDeliveryListByCountryBankRemitForCashProduct(session.getCountryId(),dataTable.getBeneficiaryBankId(),getBeneficiaryBranchId(),
							dataTable.getBeneficiaryCountryId(),dataTable.getBeneCurrencyId(),getDataserviceid(),getRoutingCountry(),getRoutingBank(),getRoutingBranch(),getRemitMode(),
							new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId") : "1"));

			if (lstDeliveryView.size() == 0) {
				setBooRenderDeliveryModeInputPanel(true);
				setBooRenderDeliveryModeDDPanel(false);
				setDeliveryModeInput(null);
				setDeliveryMode(null);
				//setRoutingDetailsShow(false);
				//RequestContext.getCurrentInstance().execute("DeliveryNoData.show();");
				status = false;
			} else if (lstDeliveryView.size() == 1) {
				setBooRenderDeliveryModeInputPanel(true);
				setBooRenderDeliveryModeDDPanel(false);
				setDeliveryModeInput(lstDeliveryView.get(0).getPopulateName());
				setDeliveryMode(lstDeliveryView.get(0).getPopulateId());
				status = bankBranchByBankView(dataTable);
			} else {
				status = true;
				setDeliveryModeInput(null);
				setDeliveryMode(null);
				setBooRenderDeliveryModeInputPanel(false);
				setBooRenderDeliveryModeDDPanel(true);

				setDeliveryModeInput(lstDeliveryView.get(0).getPopulateName());
				setDeliveryMode(lstDeliveryView.get(0).getPopulateId());
				status = bankBranchByBankView(dataTable);

				setLstofDelivery(lstDeliveryView);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return status;
	}

	public boolean bankBranchByBankView(BranchStaffGSMRateDataTable dataTable){

		boolean status = false;

		dataTable.setRemitDeliveryId(getDeliveryMode());

		if(getCashRoutingBranch() != null && getCashRoutingBranchName() != null){
			setBooSingleRoutingBranch(true);
			setBooMultipleRoutingBranch(false);
			setRoutingBranch(getCashRoutingBranch());
			setRoutingBranchName(getCashRoutingBranchName());
			fetchBankVariables(dataTable);
			status = true;
		}else{
			if (getRoutingBank() != null) {
				List<PopulateData> lstRoutingBankBranch = iPersonalRemittanceService.getRoutingBranchList(session.getCountryId(),
						dataTable.getBeneficiaryBankId(), getBeneficiaryBranchId(),dataTable.getBeneficiaryCountryId(),dataTable.getBeneCurrencyId(), getDataserviceid(),
						getRoutingCountry(), getRoutingBank(),getRemitMode(), getDeliveryMode());

				if (lstRoutingBankBranch.size() == 0) {
					setBooSingleRoutingBranch(true);
					setBooMultipleRoutingBranch(false);
					setRoutingBranchName(null);
					setRoutingBranch(null);
					status = false;
				} else if (lstRoutingBankBranch.size() == 1) {
					setBooSingleRoutingBranch(true);
					setBooMultipleRoutingBranch(false);
					setRoutingBranch(lstRoutingBankBranch.get(0).getPopulateId());
					setRoutingBranchName(lstRoutingBankBranch.get(0).getPopulateName());
					fetchBankVariables(dataTable);
					status = true;
				} else {
					setRoutingBranchName(null);
					setRoutingBranch(null);
					setBooSingleRoutingBranch(false);
					setBooMultipleRoutingBranch(true);
					setRoutingBankBranchlst(lstRoutingBankBranch);
					
					setRoutingBranch(lstRoutingBankBranch.get(0).getPopulateId());
					setRoutingBranchName(lstRoutingBankBranch.get(0).getPopulateName());
					fetchBankVariables(dataTable);
					
					status = true;
				}
			}
		}

		return status;
	}

	// fetch dynamic and match data
	public void fetchBankVariables(BranchStaffGSMRateDataTable dataTable){
		try {
			dataTable.setRoutingBankBranch(getRoutingBranch());
			//dynamicLevel();
			//matchData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean deliverylistByRemitId(BranchStaffGSMRateDataTable dataTable){
		
		boolean status = false;

		dataTable.setRemitRemittanceId(getRemitMode());

		List<PopulateData> lstDeliveryView = iPersonalRemittanceService.getDeliveryListByCountryBankRemit(session.getCountryId(),dataTable.getBeneficiaryBankId(),getBeneficiaryBranchId(),
				//getDatabenificarycountry(),
				dataTable.getBeneficiaryCountryId(),
				dataTable.getBeneCurrencyId(),getDataserviceid(),getRoutingCountry(),getRoutingBank(),getRemitMode(),
				new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId") : "1"));

		if (lstDeliveryView.size() == 0) {
			setBooRenderDeliveryModeInputPanel(true);
			setBooRenderDeliveryModeDDPanel(false);
			setDeliveryModeInput(null);
			setDeliveryMode(null);
			//setRoutingDetailsShow(false);
			//RequestContext.getCurrentInstance().execute("DeliveryNoData.show();");
			status = false;
		} else if (lstDeliveryView.size() == 1) {
			setBooRenderDeliveryModeInputPanel(true);
			setBooRenderDeliveryModeDDPanel(false);
			setDeliveryModeInput(lstDeliveryView.get(0).getPopulateName());
			setDeliveryMode(lstDeliveryView.get(0).getPopulateId());
			status = bankBranchByBankView(dataTable);
		} else {
			status = true;
			setDeliveryModeInput(null);
			setDeliveryMode(null);
			setBooRenderDeliveryModeInputPanel(false);
			setBooRenderDeliveryModeDDPanel(true);
			
			setDeliveryModeInput(lstDeliveryView.get(0).getPopulateName());
			setDeliveryMode(lstDeliveryView.get(0).getPopulateId());
			status = bankBranchByBankView(dataTable);
			
			setLstofDelivery(lstDeliveryView);
		}

		return status;
	}

	public void deliverylistByRemitId(){
		bankBranchByBankView(getAcceptPlaceOrderDataTable());
	}

	public void remittancelistByBankId(){
		deliverylistByRemitId(getAcceptPlaceOrderDataTable());
	}

	public boolean remittancelistByBankId(BranchStaffGSMRateDataTable dataTable){
		
		boolean status = false;

		dataTable.setRemitServiceId(getDataserviceid());

		// spl pool based on routing country , routing bank
		List<PopulateData> lstRemitView = iPersonalRemittanceService
				.getRemittanceListByCountryBank(session.getCountryId(),dataTable.getBeneficiaryBankId(),getBeneficiaryBranchId(),
						//getDatabenificarycountry(),
						dataTable.getBeneficiaryCountryId(),
						dataTable.getBeneCurrencyId(),getDataserviceid(),getRoutingCountry(),getRoutingBank(),
						new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId") : "1"));

		if (lstRemitView.size() == 0) {
			setBooSingleRemit(true);
			setBooMultipleRemit(false);
			setRemittanceName(null);
			setRemitMode(null);
			//setRoutingDetailsShow(false);
			//RequestContext.getCurrentInstance().execute("remittanceNoData.show();");
			status = false;
		} else if (lstRemitView.size() == 1) {
			setBooSingleRemit(true);
			setBooMultipleRemit(false);
			setRemittanceName(lstRemitView.get(0).getPopulateName());
			setRemitMode(lstRemitView.get(0).getPopulateId());
			status = deliverylistByRemitId(dataTable);
		} else {
			status = true;
			setRemittanceName(null);
			setRemitMode(null);
			setBooSingleRemit(false);
			setBooMultipleRemit(true);
			
			setRemittanceName(lstRemitView.get(0).getPopulateName());
			setRemitMode(lstRemitView.get(0).getPopulateId());
			status = deliverylistByRemitId(dataTable);
			
			setLstofRemittance(lstRemitView);
		}

		return status;
	}

	public void bankDetailsByCountry(BranchStaffGSMRateDataTable dataTable){

		System.out.println("bankDetailsByCountry getDataserviceid bankDetailsByCountry :"+getDataserviceid());
		if (getRoutingCountry() != null) {
			System.out.println("Service name : " + getDatabenificaryservice());
			Boolean ttCheckSameNotAllow = false;

			if (getDatabenificaryservice().equalsIgnoreCase(Constants.TTNAME)) {
				ttCheckSameNotAllow = true;
			} else {
				ttCheckSameNotAllow = false;
			}

			List<PopulateData> lstRoutingBank = iPersonalRemittanceService.getRoutingBankList(session.getCountryId(),
					dataTable.getBeneficiaryBankId(), getBeneficiaryBranchId(),
					//getDatabenificarycountry(),
					dataTable.getBeneficiaryCountryId(),
					dataTable.getBeneCurrencyId(), getDataserviceid(),
					getRoutingCountry(), ttCheckSameNotAllow);
			if (lstRoutingBank.size() == 0) {
				setBooSingleRoutingBank(true);
				setBooMultipleRoutingBank(false);
				setRoutingBankName(null);
				setRoutingBank(null);
				setRoutingDetailsShow(false);
				RequestContext.getCurrentInstance().execute("routingBankNoData.show();");
				return;
			} else if (lstRoutingBank.size() == 1) {
				setBooSingleRoutingBank(true);
				setBooMultipleRoutingBank(false);
				setRoutingBank(lstRoutingBank.get(0).getPopulateId());
				setRoutingBankName(lstRoutingBank.get(0).getPopulateName());
				remittancelistByBankId(dataTable);
			} else {
				setRoutingBankName(null);
				setRoutingBank(null);
				setBooSingleRoutingBank(false);
				setBooMultipleRoutingBank(true);
				setRoutingbankvalues(lstRoutingBank);
			}

		}

	}

	/*public void getServiceListDetails(BranchStaffGSMRateDataTable dataTable){
		List<PopulateData> serviceList = iPersonalRemittanceService.getServiceList(session.getCountryId(),dataTable.getBeneficiaryBankId(), getBeneficiaryBranchId(),dataTable.getBeneficiaryCountryId(),dataTable.getBeneCurrencyId(),getServiceGroupCode());
		//this.setServiceList(serviceList);

		if(serviceList!=null  && serviceList.size()>0){
			//setBooMultipleService(true);
			//setBooSingleService(false);
			//setRemittanceId(serviceList.get(0).getPopulateId());
			setDataserviceid(serviceList.get(0).getPopulateId());
			countryNameByServiceId(dataTable);
			//deliverylistByRemitId();
		}else{
			setRoutingDetailsShow(false);
			RequestContext.getCurrentInstance().execute("serviceNoData.show();");
			return;
		}
	}*/

	public boolean getServiceListDetails(BranchStaffGSMRateDataTable dataTable){

		boolean status = false;

		List<PopulateData> serviceList = iPersonalRemittanceService.getServiceListForPO(session.getCountryId(),dataTable.getBeneficiaryBankId(), getBeneficiaryBranchId(),dataTable.getBeneficiaryCountryId(),dataTable.getBeneCurrencyId(),getServiceGroupCode(),dataTable.getRoutingCountry(),dataTable.getRoutingBank());

		if(serviceList!=null  && serviceList.size()>0){

			setRoutingCountry(dataTable.getRoutingCountry());
			String lstRcountry = generalService.getCountryName(new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId") : "1"),getRoutingCountry());
			if (lstRcountry != null) {
				setRoutingCountryName(lstRcountry);
				setBooMultipleRoutingCountry(false);
				setBooSingleRoutingCountry(true);
			}

			setRoutingBank(dataTable.getRoutingBank());
			String lstRBank = generalService.getBankName(getRoutingBank());
			if (lstRBank != null) {
				setRoutingBankName(lstRBank);
				setBooMultipleRoutingBank(false);
				setBooSingleRoutingBank(true);
			}

			setBooSingleService(true);
			setBooMultipleService(false);
			List<BigDecimal> duplService = new ArrayList<BigDecimal>();
			for (PopulateData populateData : serviceList) {
				if(!duplService.contains(populateData.getPopulateId())){
					duplService.add(populateData.getPopulateId());
					setDataserviceid(populateData.getPopulateId());
					setDatabenificaryservice(populateData.getPopulateName());

					status = remittancelistByBankId(dataTable);
					if(status){
						break;
					}
				}
			}

		}else{
			setRoutingDetailsShow(false);
			RequestContext.getCurrentInstance().execute("serviceNoData.show();");
		}

		return status;
	}

	//based on service selection render fields
	public void countryNameByServiceId(BranchStaffGSMRateDataTable dataTable){

		if(dataserviceid !=null){
			setDatabenificaryservice(serviceMasterService.LocalServiceDescriptionFromDB(new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId"): "1"), getDataserviceid()).get(0).getLocalServiceDescription());

			// fetch routing country from view
			List<PopulateData> lstRoutingCountry = iPersonalRemittanceService.getRoutingCountryList(session.getCountryId(),dataTable.getBeneficiaryBankId(),getBeneficiaryBranchId(),
					//getDatabenificarycountry(),
					dataTable.getBeneficiaryCountryId(),
					dataTable.getBeneCurrencyId(),getDataserviceid(),new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId"): "1"));

			if (lstRoutingCountry.size() == 0) {
				setBooSingleRoutingCountry(true);
				setBooMultipleRoutingCountry(false);
				setRoutingCountryName(null);
				setRoutingCountry(null);
				setRoutingDetailsShow(false);
				RequestContext.getCurrentInstance().execute("routingCountryNoData.show();");
				return;
			} else if (lstRoutingCountry.size() == 1) {
				setBooSingleRoutingCountry(true);
				setBooMultipleRoutingCountry(false);
				setRoutingCountry(lstRoutingCountry.get(0).getPopulateId());
				setRoutingCountryName(lstRoutingCountry.get(0).getPopulateName());
				// setting routing bank Id from view
				bankDetailsByCountry(dataTable);
			} else {
				setRoutingCountryName(null);
				setRoutingCountry(null);
				setBooSingleRoutingCountry(false);
				setBooMultipleRoutingCountry(true);
				setRoutingCountrylst(lstRoutingCountry);
			}
		}

	}

	public void clearRoutingSetup()
	{
		setBeneficiaryBranchId(null);
		setServiceGroupCode(null);;
		setBooSingleService(true);
		setBooMultipleService(false);
		setAccountSeqId(null);
		setDataserviceid(null);
		setDatabenificaryservice(null);
		setRoutingCountryName(null);
		setBooSingleRoutingCountry(true);
		setBooMultipleRoutingCountry(false);
		setRoutingBankName(null);;
		setBooMultipleRoutingBank(false);
		setBooSingleRoutingBank(true);
		setRoutingBranchName(null);
		setBooMultipleRoutingBranch(false);
		setBooSingleRoutingBranch(true);
		setRoutingBranch(null);
		setBooRenderRemitPanel(false);
		//Routing Remittance Variables
		setBooMultipleRemit(false);
		setBooSingleRemit(false);
		setRemittanceName(null);;
		setRemitMode(null);;
		//Routing Delivery Variables
		setBooRenderDeliveryModeDDPanel(false);
		setBooRenderDeliveryModeInputPanel(true);
		setDeliveryModeInput(null);;
		setDeliveryMode(null);;
		setLstofRemittance(null);
		setLstofDelivery(null);
		setRoutingBankBranchlst(null);
		setRoutingbankvalues(null);
		setRoutingCountrylst(null);
		setRoutingDetailsShow(false);
		//setAcceptPlaceOrderDataTable(null);
		setErrorMessage(null);
	}

	//to fetch all records based on customer id
	public void tofetchAllRecordsBasedOnCustomerId(){
		lstBranchStaffGSMRateDataTables.clear();
		List<RatePlaceOrder> lstRatePlaceOrder=branchStaffGSMRateService.toFetchAllBranchStaffGsmBasedOnCustId(new BigDecimal(session.getBranchId()),getCustomerId());
		if(lstRatePlaceOrder.size()>0){
			for (RatePlaceOrder ratePlaceOrder : lstRatePlaceOrder) {
				BranchStaffGSMRateDataTable branchStaffGSMRateDT=new BranchStaffGSMRateDataTable();
				branchStaffGSMRateDT.setRateOfferedPk(ratePlaceOrder.getRatePlaceOrderId());
				branchStaffGSMRateDT.setCustomerId(ratePlaceOrder.getCustomerId());
				String custmerName=null;
				BigDecimal custrRef=null;
				String BeneName=null;
				String beneBankName=null;
				String curQtyName=null;
				//to Fetch Customer reference and Name
				//custrRef=gSMPlaceOrderRateFeedService.toFetchCustomerRef(ratePlaceOrder.getCustomerId());
				custmerName=generalService.getCustomerNameCustomerId(ratePlaceOrder.getCustomerId());
				branchStaffGSMRateDT.setCustomerName(custmerName);
				branchStaffGSMRateDT.setCustomerRefNo(ratePlaceOrder.getCustomerreference());
				if(ratePlaceOrder.getCustomerreference() != null){
					branchStaffGSMRateDT.setCustomerRefAndName(ratePlaceOrder.getCustomerreference().toString()+ "-" +custmerName);
				}else{
					branchStaffGSMRateDT.setCustomerRefAndName(custmerName);	
				}
				branchStaffGSMRateDT.setDocumentNumber(ratePlaceOrder.getDocumentNumber());
				branchStaffGSMRateDT.setDocumentFinanceYear(ratePlaceOrder.getDocumentFinanceYear());
				//to bene name
				if(ratePlaceOrder.getBeneficiaryMasterId()!=null)
				{
					branchStaffGSMRateDT.setBeneficiaryMasterId(ratePlaceOrder.getBeneficiaryMasterId().getBeneficaryMasterSeqId());
					BeneName=gSMPlaceOrderRateFeedService.toFetchBeneficiaryName(ratePlaceOrder.getBeneficiaryMasterId().getBeneficaryMasterSeqId());
					branchStaffGSMRateDT.setBeneficiaryName(BeneName);
				}

				if(ratePlaceOrder.getAccountSeqquenceId()!=null)
				{
					branchStaffGSMRateDT.setBeneficiaryAccountSeqId(ratePlaceOrder.getAccountSeqquenceId().getBeneficaryAccountSeqId());
				}

				branchStaffGSMRateDT.setBeneCurrencyId(ratePlaceOrder.getDestinationCurrenyId());
				String currencyName = generalService.getCurrencyName(ratePlaceOrder.getDestinationCurrenyId());
				branchStaffGSMRateDT.setCurrencyName(currencyName);

				/*List<PopulateData> lstofCurrency = new ArrayList<PopulateData>();
				lstofCurrency= iPlaceOnOrderCreationService.getBasedOnCountyCurrency(ratePlaceOrder.getBeneficiaryCountryId());
				if(lstofCurrency!=null && lstofCurrency.size()>0)
				{
					branchStaffGSMRateDT.setBeneCurrencyId(lstofCurrency.get(0).getPopulateId());
				}*/

				//bene bank name
				branchStaffGSMRateDT.setBeneficiaryBankId(ratePlaceOrder.getBeneficiaryBankId());
				beneBankName=generalService.getBankName(ratePlaceOrder.getBeneficiaryBankId());
				branchStaffGSMRateDT.setBeneficiaryBankName(beneBankName);
				branchStaffGSMRateDT.setRateOffered(ratePlaceOrder.getRateOffered());
				branchStaffGSMRateDT.setTransctionAmount(ratePlaceOrder.getTransactionAmount());
				branchStaffGSMRateDT.setCurrencyId(ratePlaceOrder.getRequestCurrencyId());
				//currency name qty code
				//ratePlaceOrder.getBeneficiaryMasterId().getBeneficaryMasterSeqId(),ratePlaceOrder.getAccountSeqquenceId().getBeneficaryAccountSeqId()
				//curQtyName=gSMPlaceOrderRateFeedService.toFetchCurrencyQtyName(ratePlaceOrder.getDestinationCurrenyId());
				curQtyName=gSMPlaceOrderRateFeedService.toFetchCurrencyQtyName(ratePlaceOrder.getRequestCurrencyId());
				branchStaffGSMRateDT.setCurrencyQtyName("["+curQtyName+"]");
				branchStaffGSMRateDT.setAmountAndQtyName(ratePlaceOrder.getTransactionAmount()+ "-" +curQtyName);
				//remittance and service , delivery
				branchStaffGSMRateDT.setRemitServiceId(ratePlaceOrder.getServiceMasterId());
				branchStaffGSMRateDT.setRemitRemittanceId(ratePlaceOrder.getRemittanceModeId());
				branchStaffGSMRateDT.setRemitDeliveryId(ratePlaceOrder.getDeliveryModeId());
				branchStaffGSMRateDT.setCreatedBy(ratePlaceOrder.getCreatedBy());
				branchStaffGSMRateDT.setCreatedDate(ratePlaceOrder.getCreatedDate());
				branchStaffGSMRateDT.setModifiedBy(ratePlaceOrder.getModifiedBy());
				branchStaffGSMRateDT.setModifiedDate(ratePlaceOrder.getModifiedDate());
				branchStaffGSMRateDT.setApprovedBy(ratePlaceOrder.getApprovedBy());
				branchStaffGSMRateDT.setApprovedDate(ratePlaceOrder.getApprovedDate());
				branchStaffGSMRateDT.setIsActive(ratePlaceOrder.getIsActive());
				branchStaffGSMRateDT.setRoutingCountry(ratePlaceOrder.getRoutingCountryId());
				branchStaffGSMRateDT.setRoutingBank(ratePlaceOrder.getRoutingBankId());
				branchStaffGSMRateDT.setBeneficiaryCountryId(ratePlaceOrder.getBeneficiaryCountryId());
				branchStaffGSMRateDT.setBeneficiaryAccountNo(ratePlaceOrder.getBeneficiaryAccountNo());
				branchStaffGSMRateDT.setCustomerEmail(ratePlaceOrder.getCustomerEmail());
				branchStaffGSMRateDT.setRemittType(ratePlaceOrder.getRemitType());
				lstBranchStaffGSMRateDataTables.add(branchStaffGSMRateDT);
			}
		}else{
			PersonalRemittanceBean<T> personremit = (PersonalRemittanceBean) appContext.getBean("personalRemittanceBean");
			personremit.setIdNumber(getIdNumber());
			personremit.setSelectCard(getSelectCard());
			try {
				try {
					personremit.goFromOldSmartCardpanel();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/PersonalRemittance.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}


		}
	}


	public void clearAllFieldsForSave(){
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
		//setCustomerId(null);
		setCustomerName(null);
		setAmountAndQtyName(null);
		setBeneficiaryMasterId(null);
		setBeneficiaryAccountSeqId(null);
		setCurrencyQtyName(null);
		setRoutingCountry(null);
		setRoutingBank(null);
		//common Variables
		setErrorMessage(null);;
		setCreatedBy(null);
		setCreatedDate(null);
		setModifiedBy(null);
		setModifiedDate(null);
		setApprovedBy(null);
		setApprovedDate(null);
		setIsActive(null);
		setIdNumber(null);
		setSelectCard(null);
		setShowBeneNameList(false);
		setErrorMessage(null);
	}

	private void addtionalMethods() throws AMGException
	{
		getSourceofIncomeDetails();
		toFetchPaymentDetails();
		dynamicLevel();
		matchData();
	}

	//click on Accept button
	public void acceptButtonToRemittance() throws AMGException{
		
		setErrorMessage(null);
		String errorMsg = null;
		PersonalRemittanceBean objectPersonalRemittance = null;

		try{

			BranchStaffGSMRateDataTable dataTable = getAcceptPlaceOrderDataTable();

			objectPersonalRemittance = (PersonalRemittanceBean)appContext.getBean("personalRemittanceBean");
			
			objectPersonalRemittance.setDatabenificarybankname(dataTable.getBeneficiaryBankName());
			if(dataTable.getBeneficiaryBankId() != null){
				String bankMasterCode = generalService.getBankCode(dataTable.getBeneficiaryBankId());
				objectPersonalRemittance.setDatabenificarybankalphacode(bankMasterCode);
			}

			objectPersonalRemittance.setDataserviceid(dataTable.getRemitServiceId());
			
			if(dataTable.getRemitServiceId() != null){
				List<ServiceMasterDesc> lstServiceMaster = serviceMasterService.LocalServiceDescriptionFromDB(new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId"): "1"), dataTable.getRemitServiceId());
				if(lstServiceMaster != null && lstServiceMaster.size() != 0){
					ServiceMasterDesc serviceCode = lstServiceMaster.get(0);
					objectPersonalRemittance.setDatabenificaryservice(serviceCode.getLocalServiceDescription());
					objectPersonalRemittance.setDatabenificaryservicecode(serviceCode.getServiceMaster().getServiceCode());
				}
			}
			objectPersonalRemittance.setBooSingleService(true);
			objectPersonalRemittance.setBooMultipleService(false);

			objectPersonalRemittance.setRoutingCountry(dataTable.getRoutingCountry());
			
			if(dataTable.getRoutingCountry() != null){
				List<CountryMasterDesc> lstRcountry = documentSerialityService.getCountryListAppCountry(new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId") : "1"),dataTable.getRoutingCountry());
				if (lstRcountry != null) {
					CountryMasterDesc routingCountry = lstRcountry.get(0);
					objectPersonalRemittance.setRoutingCountryName(routingCountry.getCountryName());
					objectPersonalRemittance.setDataroutingcountrycode(routingCountry.getFsCountryMaster().getCountryCode());
					objectPersonalRemittance.setDataroutingcountryalphacode(routingCountry.getFsCountryMaster().getCountryAlpha2Code());
				}
			}
			objectPersonalRemittance.setBooSingleRoutingCountry(true);
			objectPersonalRemittance.setBooMultipleRoutingCountry(false);

			objectPersonalRemittance.setRoutingBank(dataTable.getRoutingBank());
			
			List<BankMaster> lstRBank = generalService.getBankDetailsList(session.getLanguageId(),dataTable.getRoutingCountry(),dataTable.getRoutingBank());
			if (lstRBank != null && !lstRBank.isEmpty()) {
				BankMaster bankRec = lstRBank.get(0);
				objectPersonalRemittance.setRoutingBankName(bankRec.getBankFullName());
				objectPersonalRemittance.setDataroutingbankalphacode(bankRec.getBankCode());
			}
			objectPersonalRemittance.setBooSingleRoutingBank(true);
			objectPersonalRemittance.setBooMultipleRoutingBank(false);

			objectPersonalRemittance.setRemitMode(dataTable.getRemitRemittanceId());
			String lstRemitName = iremittanceModeService.getRemittanceDesc(dataTable.getRemitRemittanceId());
			if (lstRemitName != null) {
				objectPersonalRemittance.setRemittanceName(lstRemitName);
			}
			objectPersonalRemittance.setBooSingleRemit(true);
			objectPersonalRemittance.setBooMultipleRemit(false);

			objectPersonalRemittance.setDeliveryMode(dataTable.getRemitDeliveryId());
			String lstDeliveryName = deliveryModeService.getDeliveryDesc(dataTable.getRemitDeliveryId(), new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId") : "1"));
			if (lstDeliveryName != null) {
				objectPersonalRemittance.setDeliveryModeInput(lstDeliveryName);
			}
			objectPersonalRemittance.setBooRenderDeliveryModeInputPanel(true);
			objectPersonalRemittance.setBooRenderDeliveryModeDDPanel(false);

			objectPersonalRemittance.setRoutingBranch(dataTable.getRoutingBankBranch());
			List<BankBranch> lstRbranch = bankBranchDetailsService.getBankBranchByBranchID(dataTable.getRoutingBankBranch());
			if (lstRbranch != null && lstRbranch.size() != 0) {
				BankBranch routingBranchName = lstRbranch.get(0);
				objectPersonalRemittance.setRoutingBranchName(routingBranchName.getBranchFullName());
			}
			objectPersonalRemittance.setBooSingleRoutingBranch(true);
			objectPersonalRemittance.setBooMultipleRoutingBranch(false);

			List<PopulateData> currency = new ArrayList<PopulateData>();

			PopulateData populatedata = new PopulateData(dataTable.getBeneCurrencyId(), dataTable.getCurrencyName());
			PopulateData populatedata1 = null;
			String localCurrencyName = generalService.getCurrencyName(new BigDecimal(session.getCurrencyId()));
			if(localCurrencyName != null){
				populatedata1 = new PopulateData(new BigDecimal(session.getCurrencyId()),localCurrencyName);
			}

			currency.add(populatedata);
			if(populatedata1 != null){
				currency.add(populatedata1);
			}

			objectPersonalRemittance.setLstofCurrency(currency);

			objectPersonalRemittance.setForiegnCurrency(dataTable.getBeneCurrencyId()); // bene currency
			objectPersonalRemittance.setDatabenificarycurrency(dataTable.getBeneCurrencyId());
			objectPersonalRemittance.setDatabenificarycurrencyname(dataTable.getCurrencyName());
			objectPersonalRemittance.setCurrency(dataTable.getCurrencyId());  // selected currency 
			objectPersonalRemittance.setCustomerNo(dataTable.getCustomerId());

			List<CustomerIdproofView> customerDetails = generalService.getCustomerIdProofDetailsFromView(dataTable.getCustomerId());
			if(customerDetails != null && customerDetails.size() != 0){

				CustomerIdproofView customerDetailsDt = customerDetails.get(0);

				String customerTypeString = iPersonalRemittanceService.getCustomerType(customerDetailsDt.getIdproofCustomerTypeId());
				if (customerTypeString != null) {
					objectPersonalRemittance.setCustomerType(customerTypeString);
				}
			}

			objectPersonalRemittance.setAvailLoyaltyPoints(Constants.No);
			objectPersonalRemittance.setChargesOverseas(Constants.No);
			objectPersonalRemittance.setAmountToRemit(dataTable.getTransctionAmount());
			// Spl rate
			objectPersonalRemittance.setSpecialDealRate(null);
			// spot rate
			objectPersonalRemittance.setSpotExchangeRate(dataTable.getRateOffered());
			objectPersonalRemittance.setSpotRate(Constants.Yes);
			objectPersonalRemittance.setFinaceYear(dataTable.getDocumentFinanceYear());
			objectPersonalRemittance.setApprovalNo(dataTable.getDocumentNumber());
			objectPersonalRemittance.setApprovalYear(dataTable.getDocumentFinanceYear());
			objectPersonalRemittance.setBooRenderOverseaCharges(true);
			
			objectPersonalRemittance.setCashRounding(Constants.U);

			objectPersonalRemittance.setSpecialApprovalRadio(Integer.parseInt("2"));
			objectPersonalRemittance.setSpotRateRender(true);
			objectPersonalRemittance.setDisableSpotRatePanel(true);
			objectPersonalRemittance.setNextRender(true);

			objectPersonalRemittance.setLoyaltyPoints(iPersonalRemittanceService.getLoyaltyPointFromFunction(session.getCountryId(),getCustomerRefNo()));

			objectPersonalRemittance.setMasterId(dataTable.getBeneficiaryMasterId());
			objectPersonalRemittance.setBeneficiaryAccountSeqId(dataTable.getBeneficiaryAccountSeqId());
			
			if(dataTable.getBeneficiaryCountryId() != null){
				objectPersonalRemittance.setDataBankbenificarycountry(dataTable.getBeneficiaryCountryId());
				List<CountryMasterDesc> lstRcountry = documentSerialityService.getCountryListAppCountry(new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId") : "1"),dataTable.getBeneficiaryCountryId());
				if (lstRcountry != null) {
					CountryMasterDesc routingCountry = lstRcountry.get(0);
					objectPersonalRemittance.setDataBankbenificarycountryname(routingCountry.getCountryName());
					objectPersonalRemittance.setDatabenificarycountrycode(routingCountry.getFsCountryMaster().getCountryCode());
				}
			}
			
			objectPersonalRemittance.setBeneficaryBankId(dataTable.getBeneficiaryBankId());

			List<BenificiaryListView> lstBene = iPlaceOnOrderCreationService.getBeneficiaryDetailsForAccept(dataTable);

			if(lstBene != null && lstBene.size() == 1){

				BenificiaryListView lstBeneDt = lstBene.get(0);
				
				String errorStatus = createPlaceOrderForBene(lstBeneDt);
				if(errorStatus != null && !errorStatus.equalsIgnoreCase("")){
					errorMsg = getErrmsg();
					setErrorMessage(errorMsg);
					RequestContext.getCurrentInstance().execute("error.show();");
				    return;
				}
				
				objectPersonalRemittance.setBeneficiaryRelationShipSeqId(lstBeneDt.getBeneficiaryRelationShipSeqId());

				objectPersonalRemittance.setBeneficaryBankBranchId(lstBeneDt.getBranchId());
				objectPersonalRemittance.setDatabenificarybranchname(lstBeneDt.getBankBranchName());

				//fetch customer reference				
				objectPersonalRemittance.setCustomerrefno(dataTable.getCustomerRefNo());
				objectPersonalRemittance.setCustomerFullName(dataTable.getCustomerName());
				
				if(lstBeneDt.getSwiftBic() != null){
					objectPersonalRemittance.setSwiftBic(lstBeneDt.getSwiftBic());
				}else{
					log.info("datatabledetails.getBankId() :"+lstBeneDt.getBankId()+"\t Branch Id :"+lstBeneDt.getBranchId());
					List<BankBranchView> bankBranchViewList = bankBranchDetailsService.toFetchAllDetailsFromBankBranch(lstBeneDt.getBankId(),lstBeneDt.getBranchId());
					if(bankBranchViewList!=null && bankBranchViewList.size()==1){
						log.info("bankBranchViewList.get(0).getSwift() "+bankBranchViewList.get(0).getSwift());
						objectPersonalRemittance.setSwiftBic(bankBranchViewList.get(0).getSwift());
					}
				}

				// fetch customer local name
				//List<Customer> lstcustomer = generalService.getCustomerDeatilsBasedOnRef(dataTable.getCustomerRefNo());
				//if()
				//objectPersonalRemittance.setCustomerLocalFullName(dataTable.getCustomerName());

				StringBuffer strAdd=new StringBuffer();
				if(lstBeneDt.getStateName() != null){
					strAdd.append(lstBeneDt.getStateName());
				}
				if(lstBeneDt.getDistrictName() != null){
					strAdd.append(","+lstBeneDt.getDistrictName());
				}
				if (lstBeneDt.getCityName() != null) {
					strAdd.append(","+lstBeneDt.getCityName());
				}
				if(strAdd != null){
					objectPersonalRemittance.setBeneAddressDetails(strAdd.toString());
				}

				objectPersonalRemittance.setBeneHouseNo(lstBeneDt.getBuildingNo());
				objectPersonalRemittance.setBeneFlatNo(lstBeneDt.getFlatNo());
				objectPersonalRemittance.setBeneStreetNo(lstBeneDt.getStreetNo());

				List<BeneficaryContact> telePhone = beneficaryCreation.getTelephoneDetails(lstBeneDt.getBeneficaryMasterSeqId());

				System.out.println("telePhone :"+telePhone.toString());
				if (telePhone != null && telePhone.size() != 0) {
					String telNumber = null;
					if(telePhone.size() == 1){
						if(telePhone.get(0).getTelephoneNumber()!=null){
							telNumber = telePhone.get(0).getTelephoneNumber().trim();
						}else if(telePhone.get(0).getMobileNumber() !=null){
							telNumber = telePhone.get(0).getMobileNumber().toPlainString();
						}else{
							telNumber = null;
						}
						objectPersonalRemittance.setBenificaryTelephone(telNumber);
						objectPersonalRemittance.setDataTempBeneTelNum(telNumber);
					}else{
						BeneficaryContact beneficaryContact = telePhone.get(0);
						if(beneficaryContact.getTelephoneNumber()!=null){
							telNumber = beneficaryContact.getTelephoneNumber().trim();
						}else if(beneficaryContact.getMobileNumber()!=null){
							telNumber = beneficaryContact.getMobileNumber().toPlainString();
						}else{
							telNumber = null;
						}
						objectPersonalRemittance.setBenificaryTelephone(telNumber);
						objectPersonalRemittance.setDataTempBeneTelNum(telNumber);
					}
				}
			}

			objectPersonalRemittance.setDataAccountnum(dataTable.getBeneficiaryAccountNo());
			
			objectPersonalRemittance.setBenificiaryryNameRemittance(dataTable.getBeneficiaryName());

			//AMTB Coupon Value For that customer IF in Application setup EX_APPLICATION_SETUP --AMTBC_PROMOTION is 'Y' .
			List<ApplicationSetup> lstApplicationSetup=iPersonalRemittanceService.getEmailFromAppSetup(session.getCompanyId(), session.getCountryId());
			if(lstApplicationSetup!=null && !lstApplicationSetup.isEmpty()){
				if(lstApplicationSetup.size()==1 && lstApplicationSetup.get(0).getAmtbcPromotion()!=null && lstApplicationSetup.get(0).getAmtbcPromotion().equalsIgnoreCase("Y")){
					List<ViewAmtbCoupon> lstAmtbFromView = null;
					if(getIdNumber() != null){
						lstAmtbFromView =iPersonalRemittanceService.getAmtbCouponFromView(getIdNumber());
					}else{
						List<CustomerIdproofView> identityNum = generalService.getCustomerIdProofDetailsFromView(dataTable.getCustomerId());
						if(identityNum != null && identityNum.size() != 0){
							String idno = identityNum.get(0).getIdProofInt();
							lstAmtbFromView = iPersonalRemittanceService.getAmtbCouponFromView(idno);
						}
					}
					if(objectPersonalRemittance.lstVwAmtbCoupon !=null && !objectPersonalRemittance.lstVwAmtbCoupon.isEmpty()){
						objectPersonalRemittance.lstVwAmtbCoupon.clear();
					}
					
					if(lstAmtbFromView!=null && lstAmtbFromView.size()>0){
						objectPersonalRemittance.setLstVwAmtbCoupon(lstAmtbFromView);
						objectPersonalRemittance.setAmtbCouponRender(true);
					}else{
						objectPersonalRemittance.setLstVwAmtbCoupon(null);
						objectPersonalRemittance.setAmtbCouponRender(false);
					}
				}
			}

			// customer telephone number fetching
			HashMap<String, String> pValidatecustTelInPut = new HashMap<String, String>();

			pValidatecustTelInPut.put("APPLICATION_COUNTRY_ID", session.getCountryId().toPlainString());
			pValidatecustTelInPut.put("CUSTOMER_ID", getCustomerId().toPlainString());

			try{
				HashMap<String, String> pValidatecustTelOutPut = iPersonalRemittanceService.pValidateCustomerTelephoneDetails(pValidatecustTelInPut);

				if (pValidatecustTelOutPut.get("P_ERROR_MESSAGE") != null) {
					setErrorMessage(pValidatecustTelOutPut.get("P_ERROR_MESSAGE"));
					RequestContext.getCurrentInstance().execute("error.show();");
					return;
				} else {

					if(pValidatecustTelOutPut.get("P_CONTACT_ID") != null){
						objectPersonalRemittance.setDataCustomerContactId(new BigDecimal(pValidatecustTelOutPut.get("P_CONTACT_ID")));
					}else{
						objectPersonalRemittance.setDataCustomerContactId(null);
					}

					if(pValidatecustTelOutPut.get("P_TELEPHONE_NUMBER") != null && !pValidatecustTelOutPut.get("P_TELEPHONE_NUMBER").trim().equalsIgnoreCase("")){
						objectPersonalRemittance.setDataCustomerTelephoneNumber(pValidatecustTelOutPut.get("P_TELEPHONE_NUMBER"));
						objectPersonalRemittance.setDataTempCustomerMobile(pValidatecustTelOutPut.get("P_TELEPHONE_NUMBER"));
						objectPersonalRemittance.setBooRenderCustTelDisable(true);
					}else{
						objectPersonalRemittance.setDataCustomerTelephoneNumber(null);
						objectPersonalRemittance.setDataTempCustomerMobile(null);
						objectPersonalRemittance.setBooRenderCustTelDisable(false);
						objectPersonalRemittance.setBooRenderCustTelMandatory(true);
					}
				}

			}catch(AMGException e){
				setErrorMessage(e.getMessage());
				RequestContext.getCurrentInstance().execute("error.show();");
			}catch(Exception e){
				setErrorMessage(e.getMessage());
				RequestContext.getCurrentInstance().execute("error.show();");
			}

			objectPersonalRemittance.setRatePlaceOrderPk(getRateOfferedPk());
			
			if(getBooRenderBranchStaffRemittance()){
				objectPersonalRemittance.setBooRenderPlaceOrderByRemit(true);
				objectPersonalRemittance.setBooRenderPlaceOrder(false);
			}else{
				objectPersonalRemittance.setBooRenderPlaceOrderByRemit(false);
				objectPersonalRemittance.setBooRenderPlaceOrder(true);
			}
			
			// not selected currency
			BigDecimal equivalentCurrency = null;
			
			if(currency != null && currency.size() != 0){
				for (PopulateData popCurrency : currency) {
					if(popCurrency.getPopulateId().compareTo(dataTable.getCurrencyId()) != 0){
						equivalentCurrency = popCurrency.getPopulateId();
						break;
					}
				}
				
				int decimalvalue = 0;
				if(equivalentCurrency != null){
					objectPersonalRemittance.setEquivalentCurrency("["+generalService.getCurrencyQuote(equivalentCurrency)+"]");
					decimalvalue = foreignLocalCurrencyDenominationService.getDecimalPerCurrency(equivalentCurrency);
				}
				
				if(dataTable.getCurrencyId() != null && dataTable.getCurrencyId().compareTo(new BigDecimal(session.getCurrencyId()))==0){
					objectPersonalRemittance.setEquivalentRemitAmount(dataTable.getTransctionAmount().divide(dataTable.getRateOffered(),decimalvalue,BigDecimal.ROUND_HALF_UP));
				}else{
					objectPersonalRemittance.setEquivalentRemitAmount(GetRound.roundBigDecimal(dataTable.getTransctionAmount().multiply(dataTable.getRateOffered()),decimalvalue));
				}
				
			}
			
			errorMsg = objectPersonalRemittance.callPersonalRemitFromPlaceOrder();
			
			if(errorMsg != null){
				setErrorMessage(errorMsg);
				RequestContext.getCurrentInstance().execute("error.show();");
			}else{
				FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/PersonalRemittance.xhtml");
			}
			
			
		}catch(Exception e){
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}

	}
	
	// place order redirect to create
	public String createPlaceOrderForBene(BenificiaryListView datatabledetails){
		
		//boolean status = false;
		String error = null;
		
		log.info("Enter into createPlaceOrderForBene method ");
		// check beneficiary account type, state , district id setted or not
		try{
			HashMap <String,String> inputValues = new HashMap<String,String>();
			inputValues.put("P_BENE_MASTER_SEQ", datatabledetails.getBeneficaryMasterSeqId()==null ? "0" : datatabledetails.getBeneficaryMasterSeqId().toPlainString());
			inputValues.put("P_BENE_ACCOUNT_SEQ", datatabledetails.getBeneficiaryAccountSeqId()==null ? "0" : datatabledetails.getBeneficiaryAccountSeqId().toPlainString());
			inputValues.put("P_CUSTOMER_ID", datatabledetails.getCustomerId()==null ? "0" : datatabledetails.getCustomerId().toPlainString());

			String beneEditStatus=iPersonalRemittanceService.getBeneficiaryStatusForEdit(inputValues);

			if(beneEditStatus!=null && beneEditStatus.equalsIgnoreCase("E5"))
			{
				// err msg acc type , state , district and city
				setErrmsg("Please edit beneficiary and fill the blank details and save the record and then continue Place Order");
				error = getErrmsg();
				//RequestContext.getCurrentInstance().execute("proceErr.show();");
			}else {
				if(!datatabledetails.getBankCode().equalsIgnoreCase(Constants.WU_BANK_CODE)){
					if(datatabledetails.getMapSequenceId() != null){
						
						// bank account type check
						boolean chkAccTypeValue = chkBeneficiaryAccountType(datatabledetails);
						if(!chkAccTypeValue){
							log.info("exception.getMessage():::::::::::chkBeneficiaryAccountType::::::::::::::::::::"+datatabledetails.getBenificaryCountry());
							log.info("exception.getMessage():::::::::::chkBeneficiaryAccountType::::::::::::::::::::"+datatabledetails.getBankAccountTypeId());
							log.info("exception.getMessage():::::::::::chkBeneficiaryAccountType::::::::::::::::::::"+datatabledetails.getCustomerId());
							//RequestContext.getCurrentInstance().execute("proceErr.show();");
							setErrmsg("Account Type Mismatch, Please edit and save");
							error = getErrmsg();
							return error;
						}
						
						String errMsg = checkBeneficiaryDetailsWithProc(datatabledetails);

						if(errMsg != null && !errMsg.equalsIgnoreCase("")){
							setErrmsg(" EX_CHK_BENEFICIARY : "+ errMsg + " Please Contact Branch Manager for Modification of Beneficiary Details");
							//RequestContext.getCurrentInstance().execute("proceErr.show();");
							error = getErrmsg();
							return error;
						}else{
							
							String proceValiMessage =null;

							proceValiMessage = iPersonalRemittanceService.getValidateBeneficiaryProcedure(session.getCountryId(),datatabledetails.getCustomerId(),session.getUserName(),datatabledetails.getBeneficaryMasterSeqId(),datatabledetails.getBeneficiaryAccountSeqId());

							if(proceValiMessage!=null && proceValiMessage.length()>0){
								setErrmsg(" EX_P_VALIDATE_BENEFICIARY : "+proceValiMessage);
								error = getErrmsg();
								//RequestContext.getCurrentInstance().execute("proceErr.show();");
								return error;
							}

							HashMap<String ,String> bannedBankProcedureOut = iPersonalRemittanceService.getBannedBankCheckProcedure(session.getCountryId(),datatabledetails.getBankId(),datatabledetails.getBeneficaryMasterSeqId());

							if(bannedBankProcedureOut.get("P_ERROR_MESSAGE")!=null && !bannedBankProcedureOut.get("P_ERROR_MESSAGE").equals("")){
								System.out.println("P_error_message :"+bannedBankProcedureOut.get("P_ERROR_MESSAGE"));
								setErrmsg("EX_P_BANNED_BANK_CHECK "+bannedBankProcedureOut.get("P_ERROR_MESSAGE"));
								error = getErrmsg();
								//RequestContext.getCurrentInstance().execute("proceErr.show();");
								return error;
							}else if(bannedBankProcedureOut.get("P_ALERT_MESSAGE")!=null && !bannedBankProcedureOut.get("P_ALERT_MESSAGE").equals("")){
								System.out.println("P_ALERT_MESSAGE :"+bannedBankProcedureOut.get("P_ALERT_MESSAGE"));
								setErrmsg("EX_P_BANNED_BANK_CHECK "+bannedBankProcedureOut.get("P_ALERT_MESSAGE"));
								error = null;
								RequestContext.getCurrentInstance().execute("proceErr.show();");
							}else{
								// no error in EX_P_BANNED_BANK_CHECK
							}
						}
					}else{
						setErrmsg("Please edit beneficiary and fill the blank details and save the record and then continue Place Order");
						error = getErrmsg();
						//RequestContext.getCurrentInstance().execute("proceErr.show();");
					}
				}else{
					setErrmsg("Western Union Place Order cannot be created");
					error = getErrmsg();
					//RequestContext.getCurrentInstance().execute("proceErr.show();");
				}
			}
		}catch(Exception e){
			setErrmsg(e.getMessage());
			error = getErrmsg();
			//RequestContext.getCurrentInstance().execute("proceErr.show();");
		}
		log.info("Exit into createPlaceOrderForBene method ");
		
		return error;
	}
	
	// check beneficiary account type
	public boolean chkBeneficiaryAccountType(BenificiaryListView datatabledetails){

		boolean chkAccType = false;
		if(datatabledetails.getBankAccountNumber() != null && datatabledetails.getBenificaryCountry() != null && datatabledetails.getBankAccountTypeId() != null){
			int valueAvail = 0;
			List<AccountTypeFromView> lstAccType = beneficaryCreation.getAccountTypeFromView(datatabledetails.getBenificaryCountry());
			if(lstAccType != null && lstAccType.size() != 0){
				for (AccountTypeFromView accountTypeFromView : lstAccType) {
					if(accountTypeFromView.getAdditionalAmiecId() != null && accountTypeFromView.getAdditionalAmiecId().compareTo(datatabledetails.getBankAccountTypeId())==0){
						valueAvail = 1;
						break;
					}
				}

				if(valueAvail == 1){
					chkAccType = true;
				}else{
					chkAccType = false;
				}
			}else{
				chkAccType = false;
			}
		}else{
			chkAccType = true;
		}

		return chkAccType;
	}
	
	// call check beneficiary for validate the beneficiary details EX_CHK_BENEFICIARY
	public String checkBeneficiaryDetailsWithProc(BenificiaryListView datatabledetails){

		String errMsg = null;

		try{

			HashMap<String, Object> checkBeneDetails = new HashMap<String, Object>();

			checkBeneDetails.put("BeneBankCountryId", datatabledetails.getBenificaryCountry()); // bene bank country id
			checkBeneDetails.put("BeneServiceGroupId", datatabledetails.getServiceGroupId());
			checkBeneDetails.put("BeneBankId", datatabledetails.getBankId());
			checkBeneDetails.put("BeneBankBranchId", datatabledetails.getBranchId());
			checkBeneDetails.put("BeneCurrencyId", datatabledetails.getCurrencyId());
			checkBeneDetails.put("BeneAccountNumber", datatabledetails.getBankAccountNumber());
			checkBeneDetails.put("BeneBankServiceProvider", datatabledetails.getServiceProvider());
			checkBeneDetails.put("BeneFirstName", datatabledetails.getFirstName());
			checkBeneDetails.put("BeneSecondName", datatabledetails.getSecondName());
			checkBeneDetails.put("BeneThirdName", datatabledetails.getThirdName());
			checkBeneDetails.put("BeneFourthName", datatabledetails.getFourthName());
			checkBeneDetails.put("BeneFifthName", datatabledetails.getFiftheName());
			checkBeneDetails.put("BeneArabicFirstName", datatabledetails.getFirstNameLocal());
			checkBeneDetails.put("BeneArabicSecondName", datatabledetails.getSecondNameLocal());
			checkBeneDetails.put("BeneArabicThirdName", datatabledetails.getThirdNameLocal());
			checkBeneDetails.put("BeneArabicFourthName", datatabledetails.getFourthNameLocal());

			List<BeneficaryContact> telePhone = beneficaryCreation.getTelephoneDetails(datatabledetails.getBeneficaryMasterSeqId());

			if (telePhone != null && telePhone.size() != 0) {
				BeneficaryContact beneficaryContact = telePhone.get(0);
				if(beneficaryContact.getTelephoneNumber() != null){
					String telNumber = beneficaryContact.getTelephoneNumber().trim();
					checkBeneDetails.put("BeneTelephone", telNumber);
				}
				if(beneficaryContact.getMobileNumber() != null){
					BigDecimal mobileNumber = beneficaryContact.getMobileNumber();
					checkBeneDetails.put("BeneMobileNumber", mobileNumber);
				}
			}

			checkBeneDetails.put("BeneCountryId", datatabledetails.getCountryId()); // master beneficiary country
			checkBeneDetails.put("BeneStateId", datatabledetails.getStateId());
			checkBeneDetails.put("BeneDistrictId", datatabledetails.getDistrictId());
			checkBeneDetails.put("BeneCityId", datatabledetails.getCityId());

			errMsg = beneficaryCreation.checkBeneDetailsValidation(checkBeneDetails);

		}catch(Exception e){
			errMsg = e.getMessage();
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}
		
		return errMsg;
	}

}
