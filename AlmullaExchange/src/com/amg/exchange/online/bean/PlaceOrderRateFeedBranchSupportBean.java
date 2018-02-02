package com.amg.exchange.online.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.xml.datatype.DatatypeConfigurationException;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.foreigncurrency.model.SourceOfIncomeDescription;
import com.amg.exchange.foreigncurrency.service.IForeignCurrencyPurchaseService;
import com.amg.exchange.online.model.RatePlaceOrder;
import com.amg.exchange.online.model.RatePlaceOrderAddlData;
import com.amg.exchange.online.service.IBranchStaffGSMRateService;
import com.amg.exchange.online.service.IGSMPlaceOrderRateFeedService;
import com.amg.exchange.online.service.IPlaceAnOrderCreationService;
import com.amg.exchange.online.service.IPlaceOrederBranchSupportService;
import com.amg.exchange.remittance.bean.AddAdditionalBankData;
import com.amg.exchange.remittance.bean.AddDynamicLebel;
import com.amg.exchange.remittance.bean.PopulateData;
import com.amg.exchange.remittance.model.AdditionalBankDetailsView;
import com.amg.exchange.remittance.model.AdditionalBankRuleMap;
import com.amg.exchange.remittance.model.AdditionalDataDisplayView;
import com.amg.exchange.remittance.model.BeneficaryAccount;
import com.amg.exchange.remittance.model.PaymentModeDesc;
import com.amg.exchange.remittance.service.IPaymentService;
import com.amg.exchange.remittance.service.IPersonalRemittanceService;
import com.amg.exchange.remittance.service.IServiceGroupMasterService;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.treasury.model.ServiceGroupMasterDesc;
import com.amg.exchange.treasury.model.ServiceMaster;
import com.amg.exchange.treasury.service.DeliveryModeService;
import com.amg.exchange.treasury.service.IBankBranchDetailsService;
import com.amg.exchange.treasury.service.IRemittanceModeService;
import com.amg.exchange.treasury.service.ServiceCodeMasterService;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;
@Component("placeOrderRateFeedBranchSupportBean")
@Scope("session")
public class PlaceOrderRateFeedBranchSupportBean<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log=Logger.getLogger(PlaceOrderRateFeedBranchSupportBean.class);

	// page level Variables
	private BigDecimal placeOrderPk;
	private BigDecimal countryBranchId;
	private String countryBranchName;
	private String mobileNumber;
	private BigDecimal placeOrderDocNumber;
	private BigDecimal placeOrderDocYear;
	private BigDecimal exchangeRate;
	private BigDecimal bankId;
	private String bankName;
	//private BigDecimal currencyId;
	private String currencyName;
	private String customerRefAndName;
	private BigDecimal amount;
	private Date visitTime;
	private BigDecimal sourceId;
	private String paymentmodeId;
	private Boolean booRenderTimePanel=false;
	// common Variables
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String isActive;
	private String errorMessage;
	private BigDecimal customerId;
	private String exceptionMessage;
	private BigDecimal routingCountry;
	private BigDecimal foriegnCurrency;

	private boolean additionalCheck = true;
	private BigDecimal routingBank;
	private BigDecimal minLenght;
	private BigDecimal documentFinanceYear;
	private BigDecimal documentNo;
	private Date minDate;

	private Date maxDate;



	private BigDecimal beneficiaryBranchId;
	private String serviceGroupCode;
	private Boolean booSingleService=false;
	private Boolean booMultipleService=false;
	private BigDecimal accountSeqId;
	private BigDecimal dataserviceid;
	private String databenificaryservice;
	private String routingCountryName;
	private Boolean booSingleRoutingCountry=false;
	private Boolean booMultipleRoutingCountry=false;
	private String routingBankName;
	private Boolean booMultipleRoutingBank=false;
	private Boolean booSingleRoutingBank=false;
	private String routingBranchName;
	private Boolean booMultipleRoutingBranch=false;
	private Boolean booSingleRoutingBranch=false;
	private BigDecimal routingBranch;
	private Boolean booRenderRemitPanel=false;
	//Routing Remittance Variables
	private Boolean booMultipleRemit=false;
	private Boolean booSingleRemit=false;
	private String remittanceName;
	private BigDecimal remitMode;
	//Routing Delivery Variables
	private Boolean booRenderDeliveryModeDDPanel=false;
	private Boolean booRenderDeliveryModeInputPanel=false;
	private String deliveryModeInput;
	private BigDecimal deliveryMode;

	private PlaceOrderRateFeedBranchSupportDataTable acceptPlaceOrderDataTable;

	private Boolean routingDetailsShow;

	private List<PopulateData> lstofRemittance;
	private List<PopulateData> lstofDelivery;
	private List<PopulateData> routingBankBranchlst;
	private List<PopulateData> routingbankvalues;
	private List<PopulateData> routingCountrylst;


	private String beneficiaryName;
	private BigDecimal beneficiaryCountryId;
	private BigDecimal beneficiaryBankId;
	private BigDecimal serviceGroupId;
	private BigDecimal customerRefNo;
	private BigDecimal accountSequenceId;
	private String procedureError;
	private Boolean booRenderAdditionals=false;
	private Boolean showBeneNameList;
	private List<PopulateData>  beneficiaryList;
	private String accountNumber;
	private List<PopulateData>  lstAccountNumber;
	private BigDecimal beneAccSeqId;
	private Boolean booRenderBnkAccNoList;
	private Boolean booRenderBnkAccNo;

	// Services

	@Autowired
	IPlaceOrederBranchSupportService placeOrederBranchSupportService;

	@Autowired
	IGeneralService<T> generalService;

	@Autowired
	IGSMPlaceOrderRateFeedService gSMPlaceOrderRateFeedService;

	@Autowired
	IForeignCurrencyPurchaseService<T> foreignCurrencyPurchaseService;

	@Autowired
	IPaymentService ipaymentService;

	@Autowired
	private ApplicationContext appContext;

	@Autowired
	IPersonalRemittanceService iPersonalRemittanceService;

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
	IBranchStaffGSMRateService branchStaffGSMRateService;


	SessionStateManage session= new SessionStateManage();
	private List<PlaceOrderRateFeedBranchSupportDataTable> lstBranchSupportDataTables=new ArrayList<PlaceOrderRateFeedBranchSupportDataTable>();
	private List<SourceOfIncomeDescription> lstSourceofIncome=new ArrayList<SourceOfIncomeDescription>();
	private List<PaymentModeDesc> lstFetchAllPayMode=new ArrayList<PaymentModeDesc>();
	private List<AddDynamicLebel> listDynamicLebel = new ArrayList<AddDynamicLebel>();
	private List<AddAdditionalBankData> listAdditionalBankDataTable = new ArrayList<AddAdditionalBankData>();




	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public List<PopulateData> getLstAccountNumber() {
		return lstAccountNumber;
	}
	public void setLstAccountNumber(List<PopulateData> lstAccountNumber) {
		this.lstAccountNumber = lstAccountNumber;
	}
	public BigDecimal getBeneAccSeqId() {
		return beneAccSeqId;
	}
	public void setBeneAccSeqId(BigDecimal beneAccSeqId) {
		this.beneAccSeqId = beneAccSeqId;
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
	public List<PopulateData> getBeneficiaryList() {
		return beneficiaryList;
	}
	public void setBeneficiaryList(List<PopulateData> beneficiaryList) {
		this.beneficiaryList = beneficiaryList;
	}
	public Boolean getBooRenderAdditionals() {
		return booRenderAdditionals;
	}
	public void setBooRenderAdditionals(Boolean booRenderAdditionals) {
		this.booRenderAdditionals = booRenderAdditionals;
	}
	public Boolean getShowBeneNameList() {
		return showBeneNameList;
	}
	public void setShowBeneNameList(Boolean showBeneNameList) {
		this.showBeneNameList = showBeneNameList;
	}
	public BigDecimal getAccountSequenceId() {
		return accountSequenceId;
	}
	public void setAccountSequenceId(BigDecimal accountSequenceId) {
		this.accountSequenceId = accountSequenceId;
	}
	public String getProcedureError() {
		return procedureError;
	}
	public void setProcedureError(String procedureError) {
		this.procedureError = procedureError;
	}
	public String getBeneficiaryName() {
		return beneficiaryName;
	}
	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}
	public BigDecimal getBeneficiaryCountryId() {
		return beneficiaryCountryId;
	}
	public void setBeneficiaryCountryId(BigDecimal beneficiaryCountryId) {
		this.beneficiaryCountryId = beneficiaryCountryId;
	}
	public BigDecimal getBeneficiaryBankId() {
		return beneficiaryBankId;
	}
	public void setBeneficiaryBankId(BigDecimal beneficiaryBankId) {
		this.beneficiaryBankId = beneficiaryBankId;
	}
	public BigDecimal getServiceGroupId() {
		return serviceGroupId;
	}
	public void setServiceGroupId(BigDecimal serviceGroupId) {
		this.serviceGroupId = serviceGroupId;
	}
	public BigDecimal getCustomerRefNo() {
		return customerRefNo;
	}
	public void setCustomerRefNo(BigDecimal customerRefNo) {
		this.customerRefNo = customerRefNo;
	}
	public BigDecimal getBeneficiaryBranchId() {
		return beneficiaryBranchId;
	}
	public void setBeneficiaryBranchId(BigDecimal beneficiaryBranchId) {
		this.beneficiaryBranchId = beneficiaryBranchId;
	}
	public String getServiceGroupCode() {
		return serviceGroupCode;
	}
	public void setServiceGroupCode(String serviceGroupCode) {
		this.serviceGroupCode = serviceGroupCode;
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
	public BigDecimal getAccountSeqId() {
		return accountSeqId;
	}
	public void setAccountSeqId(BigDecimal accountSeqId) {
		this.accountSeqId = accountSeqId;
	}
	public BigDecimal getDataserviceid() {
		return dataserviceid;
	}
	public void setDataserviceid(BigDecimal dataserviceid) {
		this.dataserviceid = dataserviceid;
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
	public BigDecimal getRoutingBranch() {
		return routingBranch;
	}
	public void setRoutingBranch(BigDecimal routingBranch) {
		this.routingBranch = routingBranch;
	}
	public Boolean getBooRenderRemitPanel() {
		return booRenderRemitPanel;
	}
	public void setBooRenderRemitPanel(Boolean booRenderRemitPanel) {
		this.booRenderRemitPanel = booRenderRemitPanel;
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
	public Boolean getBooRenderDeliveryModeDDPanel() {
		return booRenderDeliveryModeDDPanel;
	}
	public void setBooRenderDeliveryModeDDPanel(Boolean booRenderDeliveryModeDDPanel) {
		this.booRenderDeliveryModeDDPanel = booRenderDeliveryModeDDPanel;
	}
	public Boolean getBooRenderDeliveryModeInputPanel() {
		return booRenderDeliveryModeInputPanel;
	}
	public void setBooRenderDeliveryModeInputPanel(
			Boolean booRenderDeliveryModeInputPanel) {
		this.booRenderDeliveryModeInputPanel = booRenderDeliveryModeInputPanel;
	}
	public String getDeliveryModeInput() {
		return deliveryModeInput;
	}
	public void setDeliveryModeInput(String deliveryModeInput) {
		this.deliveryModeInput = deliveryModeInput;
	}
	public PlaceOrderRateFeedBranchSupportDataTable getAcceptPlaceOrderDataTable() {
		return acceptPlaceOrderDataTable;
	}
	public void setAcceptPlaceOrderDataTable(
			PlaceOrderRateFeedBranchSupportDataTable acceptPlaceOrderDataTable) {
		this.acceptPlaceOrderDataTable = acceptPlaceOrderDataTable;
	}
	public Boolean getRoutingDetailsShow() {
		return routingDetailsShow;
	}
	public void setRoutingDetailsShow(Boolean routingDetailsShow) {
		this.routingDetailsShow = routingDetailsShow;
	}
	public List<PopulateData> getLstofRemittance() {
		return lstofRemittance;
	}
	public void setLstofRemittance(List<PopulateData> lstofRemittance) {
		this.lstofRemittance = lstofRemittance;
	}
	public List<PopulateData> getLstofDelivery() {
		return lstofDelivery;
	}
	public void setLstofDelivery(List<PopulateData> lstofDelivery) {
		this.lstofDelivery = lstofDelivery;
	}
	public List<PopulateData> getRoutingBankBranchlst() {
		return routingBankBranchlst;
	}
	public void setRoutingBankBranchlst(List<PopulateData> routingBankBranchlst) {
		this.routingBankBranchlst = routingBankBranchlst;
	}
	public List<PopulateData> getRoutingbankvalues() {
		return routingbankvalues;
	}
	public void setRoutingbankvalues(List<PopulateData> routingbankvalues) {
		this.routingbankvalues = routingbankvalues;
	}
	public List<PopulateData> getRoutingCountrylst() {
		return routingCountrylst;
	}
	public void setRoutingCountrylst(List<PopulateData> routingCountrylst) {
		this.routingCountrylst = routingCountrylst;
	}
	public ApplicationContext getAppContext() {
		return appContext;
	}
	public void setAppContext(ApplicationContext appContext) {
		this.appContext = appContext;
	}
	public Date getMinDate() {
		return minDate;
	}
	public void setMinDate(Date minDate) {
		this.minDate = minDate;
	}
	public Date getMaxDate() {
		return maxDate;
	}
	public void setMaxDate(Date maxDate) {
		this.maxDate = maxDate;
	}
	public BigDecimal getDocumentFinanceYear() {
		return documentFinanceYear;
	}
	public void setDocumentFinanceYear(BigDecimal documentFinanceYear) {
		this.documentFinanceYear = documentFinanceYear;
	}
	public BigDecimal getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}
	public BigDecimal getMinLenght() {
		return minLenght;
	}
	public void setMinLenght(BigDecimal minLenght) {
		this.minLenght = minLenght;
	}
	public BigDecimal getRoutingBank() {
		return routingBank;
	}
	public void setRoutingBank(BigDecimal routingBank) {
		this.routingBank = routingBank;
	}
	public List<AddAdditionalBankData> getListAdditionalBankDataTable() {
		return listAdditionalBankDataTable;
	}
	public void setListAdditionalBankDataTable(
			List<AddAdditionalBankData> listAdditionalBankDataTable) {
		this.listAdditionalBankDataTable = listAdditionalBankDataTable;
	}
	public boolean isAdditionalCheck() {
		return additionalCheck;
	}
	public void setAdditionalCheck(boolean additionalCheck) {
		this.additionalCheck = additionalCheck;
	}
	public BigDecimal getDeliveryMode() {
		return deliveryMode;
	}
	public void setDeliveryMode(BigDecimal deliveryMode) {
		this.deliveryMode = deliveryMode;
	}
	public BigDecimal getRemitMode() {
		return remitMode;
	}
	public void setRemitMode(BigDecimal remitMode) {
		this.remitMode = remitMode;
	}
	public BigDecimal getForiegnCurrency() {
		return foriegnCurrency;
	}
	public void setForiegnCurrency(BigDecimal foriegnCurrency) {
		this.foriegnCurrency = foriegnCurrency;
	}
	public BigDecimal getRoutingCountry() {
		return routingCountry;
	}
	public void setRoutingCountry(BigDecimal routingCountry) {
		this.routingCountry = routingCountry;
	}
	public BigDecimal getPlaceOrderPk() {
		return placeOrderPk;
	}
	public void setPlaceOrderPk(BigDecimal placeOrderPk) {
		this.placeOrderPk = placeOrderPk;
	}
	public BigDecimal getCountryBranchId() {
		return countryBranchId;
	}
	public void setCountryBranchId(BigDecimal countryBranchId) {
		this.countryBranchId = countryBranchId;
	}
	public String getCountryBranchName() {
		return countryBranchName;
	}
	public void setCountryBranchName(String countryBranchName) {
		this.countryBranchName = countryBranchName;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public BigDecimal getPlaceOrderDocNumber() {
		return placeOrderDocNumber;
	}
	public void setPlaceOrderDocNumber(BigDecimal placeOrderDocNumber) {
		this.placeOrderDocNumber = placeOrderDocNumber;
	}
	public BigDecimal getPlaceOrderDocYear() {
		return placeOrderDocYear;
	}
	public void setPlaceOrderDocYear(BigDecimal placeOrderDocYear) {
		this.placeOrderDocYear = placeOrderDocYear;
	}
	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
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
	/*public BigDecimal getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}*/
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
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
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getCustomerRefAndName() {
		return customerRefAndName;
	}
	public void setCustomerRefAndName(String customerRefAndName) {
		this.customerRefAndName = customerRefAndName;
	}
	public List<PlaceOrderRateFeedBranchSupportDataTable> getLstBranchSupportDataTables() {
		return lstBranchSupportDataTables;
	}
	public void setLstBranchSupportDataTables(
			List<PlaceOrderRateFeedBranchSupportDataTable> lstBranchSupportDataTables) {
		this.lstBranchSupportDataTables = lstBranchSupportDataTables;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Date getVisitTime() {
		return visitTime;
	}
	public void setVisitTime(Date visitTime) {
		this.visitTime = visitTime;
	}
	public Boolean getBooRenderTimePanel() {
		return booRenderTimePanel;
	}
	public void setBooRenderTimePanel(Boolean booRenderTimePanel) {
		this.booRenderTimePanel = booRenderTimePanel;
	}
	public BigDecimal getSourceId() {
		return sourceId;
	}
	public void setSourceId(BigDecimal sourceId) {
		this.sourceId = sourceId;
	}
	public List<SourceOfIncomeDescription> getLstSourceofIncome() {
		return lstSourceofIncome;
	}
	public void setLstSourceofIncome(
			List<SourceOfIncomeDescription> lstSourceofIncome) {
		this.lstSourceofIncome = lstSourceofIncome;
	}
	public String getPaymentmodeId() {
		return paymentmodeId;
	}
	public void setPaymentmodeId(String paymentmodeId) {
		this.paymentmodeId = paymentmodeId;
	}
	public List<PaymentModeDesc> getLstFetchAllPayMode() {
		return lstFetchAllPayMode;
	}
	public void setLstFetchAllPayMode(List<PaymentModeDesc> lstFetchAllPayMode) {
		this.lstFetchAllPayMode = lstFetchAllPayMode;
	}


	public BigDecimal getCustomerId() {
		return customerId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}
	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}


	public List<AddDynamicLebel> getListDynamicLebel() {
		return listDynamicLebel;
	}
	public void setListDynamicLebel(List<AddDynamicLebel> listDynamicLebel) {
		this.listDynamicLebel = listDynamicLebel;
	}
	//PageNavigation
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void pageNavigationPlaceOrderBranch(){
		lstBranchSupportDataTables.clear();
		clearAllFields();
		clearRoutingSetup();
		setBooRenderTimePanel(false);
		toFetchAllRecords();
		setMinMaxDate();
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "placeOrderRateFeedBranchSupport.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../onlinespecialrate/placeOrderRateFeedBranchSupport.xhtml");
		} catch (Exception exception) {
			setErrorMessage(exception.getMessage());
			log.error("pageNavigationPlaceOrderBranch  :"+ exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}
	}
	private void setMinMaxDate()
	{
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 3);
		setMinDate(new Date());
		setMaxDate(cal.getTime());

	}


	//Fetch All Records From Db
	public void toFetchAllRecords(){
		lstBranchSupportDataTables.clear();
		setCustomerId(null);
		
		List<RatePlaceOrder> lstRatePlaceOrders=placeOrederBranchSupportService.toFetchAllRecordsFromDb(new BigDecimal(session.getBranchId()));
		if(lstRatePlaceOrders.size()>0){
			for (RatePlaceOrder ratePlaceOrder : lstRatePlaceOrders) {
				String custerName=null;
				BigDecimal custrRef=null;
				String brachName=null;
				String mobNum=null;
				String benefiBankName=null;
				PlaceOrderRateFeedBranchSupportDataTable placeBranchSupportDt=new PlaceOrderRateFeedBranchSupportDataTable();
				placeBranchSupportDt.setPlaceOrderPk(ratePlaceOrder.getRatePlaceOrderId());
				placeBranchSupportDt.setCustomerId(ratePlaceOrder.getCustomerId());
				setCustomerId(ratePlaceOrder.getCustomerId());
				//custerName=generalService.getCustomerNameBasedOnCustomerId(ratePlaceOrder.getCustomerId());
				custerName=generalService.getCustomerNameCustomerId(ratePlaceOrder.getCustomerId());
				placeBranchSupportDt.setCustomerName(custerName);
				//custrRef=gSMPlaceOrderRateFeedService.toFetchCustomerRef(ratePlaceOrder.getCustomerId());
				if(ratePlaceOrder.getCustomerreference() != null){
					placeBranchSupportDt.setCustomerRefAndName(ratePlaceOrder.getCustomerreference().toString()+ "-" +custerName);
				}else{
					placeBranchSupportDt.setCustomerRefAndName(custerName);	
				}


				placeBranchSupportDt.setCountryBranchId(ratePlaceOrder.getCountryBranchId());
				brachName=gSMPlaceOrderRateFeedService.toFetchBranchName(ratePlaceOrder.getCountryBranchId());
				placeBranchSupportDt.setCountryBranchName(brachName);
				mobNum=placeOrederBranchSupportService.toFetchMobileNumBasedOnCustomerId(ratePlaceOrder.getCustomerId());
				placeBranchSupportDt.setMobileNumber(mobNum);
				placeBranchSupportDt.setPlaceOrderDocNumber(ratePlaceOrder.getDocumentNumber());
				placeBranchSupportDt.setPlaceOrderDocYear(ratePlaceOrder.getDocumentFinanceYear());
				placeBranchSupportDt.setExchangeRate(ratePlaceOrder.getRateOffered());
				placeBranchSupportDt.setBankId(ratePlaceOrder.getBeneficiaryBankId());
				benefiBankName=generalService.getBankName(ratePlaceOrder.getBeneficiaryBankId());
				placeBranchSupportDt.setBankName(benefiBankName);
				placeBranchSupportDt.setCurrencyId(ratePlaceOrder.getDestinationCurrenyId());
				placeBranchSupportDt.setCurrencyName(generalService.getCurrencyName(ratePlaceOrder.getDestinationCurrenyId()));
				placeBranchSupportDt.setAmount(ratePlaceOrder.getTransactionAmount());
				placeBranchSupportDt.setRoutingCountry(ratePlaceOrder.getRoutingCountryId());
				placeBranchSupportDt.setCurrencyId(ratePlaceOrder.getDestinationCurrenyId());
				placeBranchSupportDt.setRemitMode(ratePlaceOrder.getRemittanceModeId());
				placeBranchSupportDt.setDeliveryMode(ratePlaceOrder.getDeliveryModeId());
				placeBranchSupportDt.setRoutingBank(ratePlaceOrder.getRoutingBankId());
				placeBranchSupportDt.setDocumentFinanceyear(ratePlaceOrder.getDocumentFinanceYear());
				placeBranchSupportDt.setDocumentNo(ratePlaceOrder.getDocumentNumber());

				placeBranchSupportDt.setBeneficiaryCountryId(ratePlaceOrder.getBeneficiaryCountryId());
				placeBranchSupportDt.setBeneficiaryAccountNo(ratePlaceOrder.getBeneficiaryAccountNo());
				placeBranchSupportDt.setCustomerEmail(ratePlaceOrder.getCustomerEmail());
				placeBranchSupportDt.setRemittType(ratePlaceOrder.getRemitType());
				placeBranchSupportDt.setBeneficiaryBankId(ratePlaceOrder.getBeneficiaryBankId());
				placeBranchSupportDt.setCustomerRefNo(ratePlaceOrder.getCustomerreference());
				
				List<PopulateData> lstofCurrency = new ArrayList<PopulateData>();
				lstofCurrency= iPlaceOnOrderCreationService.getBasedOnCountyCurrency(ratePlaceOrder.getBeneficiaryCountryId());
				if(lstofCurrency!=null && lstofCurrency.size()>0)
				{
					placeBranchSupportDt.setBeneCurrencyId(lstofCurrency.get(0).getPopulateId());
				}
				
				if(ratePlaceOrder.getBeneficiaryMasterId()!=null)
				{
					placeBranchSupportDt.setBeneficiaryMasterId(ratePlaceOrder.getBeneficiaryMasterId().getBeneficaryMasterSeqId());
					String beneName=gSMPlaceOrderRateFeedService.toFetchBeneficiaryName(ratePlaceOrder.getBeneficiaryMasterId().getBeneficaryMasterSeqId());
					placeBranchSupportDt.setBeneficiaryName(beneName);
				}


				lstBranchSupportDataTables.add(placeBranchSupportDt);
			}
		}
	}
	//on click customer ref tofetch customer Pk
	public void toFetchData(PlaceOrderRateFeedBranchSupportDataTable dataTable) throws AMGException{
		setBooRenderTimePanel(true);
		clearRouting();
		setAccountNumber(null);
		setAccountSequenceId(null);
		setLstAccountNumber(null);
		setBeneAccSeqId(null);
		
		setPlaceOrderPk(dataTable.getPlaceOrderPk());
		setRoutingCountry(dataTable.getRoutingCountry());
		setForiegnCurrency(dataTable.getBeneCurrencyId());
		setRemitMode(dataTable.getRemitMode());
		setDeliveryMode(dataTable.getDeliveryMode());
		setRoutingBank(dataTable.getRoutingBank());
		setDocumentFinanceYear(dataTable.getDocumentFinanceyear());
		setDocumentNo(dataTable.getDocumentNo());
		setCustomerRefAndName(dataTable.getCustomerRefAndName());

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
				setShowBeneNameList(false);
				setRoutingDetailsShow(false);
				setBooRenderTimePanel(false);
				RequestContext.getCurrentInstance().execute("beneNameNotExist.show();");
				return;
			}
		}else
		{
			
			setBeneficiaryCountryId(dataTable.getBeneficiaryCountryId());
			setBeneficiaryBankId(dataTable.getBeneficiaryBankId());
			setServiceGroupId(dataTable.getRemittType());
			setCustomerRefNo(dataTable.getCustomerRefNo());
			
			setBeneficiaryName(dataTable.getBeneficiaryName());
			setShowBeneNameList(false);
			setRoutingDetailsShow(true);
			routingsetupDetails(dataTable);
			addtionalMethods();
		}


		//routingsetupDetails(dataTable);
	}
	private void clearRouting()
	{
		setRoutingCountry(null);
		setForiegnCurrency(null);
		setRemitMode(null);
		setDeliveryMode(null);
		setRoutingBank(null);
		setDocumentFinanceYear(null);
		setDocumentNo(null);
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
	//save
	public void save() throws DatatypeConfigurationException{
		RatePlaceOrder ratePlaceOrder = new RatePlaceOrder();
		ratePlaceOrder.setRatePlaceOrderId(getPlaceOrderPk());
		ratePlaceOrder.setAppointmentTime(getVisitTime());
		ratePlaceOrder.setSourceOfincomeId(getSourceId());
		ratePlaceOrder.setCollectionMode(getPaymentmodeId());
		//placeOrederBranchSupportService.saveAppointmentTime(getPlaceOrderPk(),getVisitTime(), getSourceId(),getPaymentmodeId(), session.getUserName());
		HashMap<String,Object> saveMap= new HashMap<String,Object>();
		saveMap.put("PLACE_ORDER_PK", getPlaceOrderPk());
		saveMap.put("CUSTOMER_VISIT_TIME", getVisitTime());
		saveMap.put("SOURCE_OF_INCOME", getSourceId());
		saveMap.put("PAYMENT_MODE", getPaymentmodeId());
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
				createRemittanceApplicationForPlaceOrder();
			}else{
				RequestContext.getCurrentInstance().execute("alreadyapprov.show();");
				return;
			}
		} catch (Exception exception) {
			setErrorMessage(exception.getMessage());
			log.error("Save :"+ exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}
		//RequestContext.getCurrentInstance().execute("complete.show();");


	}
	//clear all fields
	public void clearAllFields(){

		setPlaceOrderDocYear(null);
		setPlaceOrderDocNumber(null);
		setMobileNumber(null);
		//setCurrencyId(null);
		setCurrencyName(null);
		setCustomerRefAndName(null);
		setCountryBranchId(null);
		setCountryBranchName(null);
		setBankId(null);
		setBankName(null);
		setAmount(null);
		setVisitTime(null);
		setSourceId(null);
		setPaymentmodeId(null);

	}
	//click on Ok save
	public void clickOnOKSave(){
		lstBranchSupportDataTables.clear();
		clearAllFields();
		setBooRenderTimePanel(false);
		toFetchAllRecords();
	}

	public void createRemittanceApplicationForPlaceOrder()
	{
		try{
			HashMap<String,String> inputValues = new HashMap<String,String>();
			inputValues.put("P_COMP_ID", session.getCompanyId().toPlainString());
			inputValues.put("PLACE_ORDER_PK", getPlaceOrderPk().toPlainString());

			HashMap<String,String> outPutValues=placeOrederBranchSupportService.createRemitAppProcedure(inputValues);
			String errMsg=outPutValues.get("P_ERROR_MESG");
			if(errMsg!=null && errMsg.equalsIgnoreCase(""))
			{
				clearAllFields();
				setBooRenderTimePanel(false);
				setPlaceOrderPk(null);
				PlaceOrderShoppingCartBean placeOrderShoppingCartBean = (PlaceOrderShoppingCartBean) appContext.getBean("placeOrderShoppingCartBean");

				placeOrderShoppingCartBean.pageNavigation(getCustomerId());

			}else
			{
				setErrorMessage(errMsg);
				log.error("createRemittanceApplicationForPlaceOrder  :"+ errMsg);
				RequestContext.getCurrentInstance().execute("error.show();");
			}
			//setPlaceOrderPk(null);

			//PlaceOrderShoppingCartBean placeOrderShoppingCartBean = (PlaceOrderShoppingCartBean) appContext.getBean("placeOrderShoppingCartBean");

			//placeOrderShoppingCartBean.pageNavigation(getCustomerId());


		}catch(Exception exception)
		{
			setErrorMessage(exception.getMessage());
			log.error("createRemittanceApplicationForPlaceOrder  :"+ exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}
	}


	public void dynamicLevel() throws AMGException {
		listDynamicLebel.clear();
		setExceptionMessage("");
		List<AdditionalDataDisplayView> serviceAppRuleList = iPersonalRemittanceService.getAdditionalDataFromServiceApplicability(session.getCountryId(),
				getRoutingCountry(), getForiegnCurrency(), getRemitMode(),getDeliveryMode());
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

							List<AdditionalBankDetailsView> listAdditionaView = iPersonalRemittanceService.getAmiecDetails(getForiegnCurrency(),getRoutingBank(), getRemitMode(),
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
			ratePlaceOrder.setRatePlaceOrderId(getPlaceOrderPk());
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
			ratePlaceOrderAddlData.setDocumentNo(getDocumentNo());

			ratePlaceOrderAddlData.setCreatedBy(session.getUserName());
			ratePlaceOrderAddlData.setCreatedDate(new Date());


			// iPersonalRemittanceService.saveAdditionalInsData(additionalInsData);

			lstAddInstrData.add(ratePlaceOrderAddlData);
		}
		return lstAddInstrData;
	}

	public void exit(){

		try {
			lstBranchSupportDataTables.clear();
			clearAllFields();
			setBooRenderTimePanel(false);
			//setBooRenderExitButtonOnline(false);
			//setBooRenderExitButtonBranch(false);
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		}catch(Exception e){
			//setErrorMsg( e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}  
	}

	public void onDateSelect(SelectEvent event) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

		try {
			System.out.println("event.getObject().toString()  : "+event.getObject().toString());
			setVisitTime(format.parse(event.getObject().toString()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void clickOnSave(){
		lstBranchSupportDataTables.clear();
		clearAllFields();
		setBooRenderTimePanel(false);
		toFetchAllRecords();
		setMinMaxDate();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../onlinespecialrate/placeOrderRateFeedBranchSupport.xhtml");
		} catch (Exception exception) {
			setErrorMessage(exception.getMessage());
			log.error("pageNavigationPlaceOrderBranch  :"+ exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}
	}



	public void routingsetupDetails(PlaceOrderRateFeedBranchSupportDataTable dataTable)
	{

		try {
			HashMap<String, BigDecimal> rtnMap=iPlaceOnOrderCreationService.getBeneMasterIdCurrencyId(getBeneficiaryName(),dataTable.getBeneficiaryCountryId(),dataTable.getBeneficiaryBankId(),dataTable.getRemittType(),dataTable.getCustomerRefNo(),dataTable.getBeneficiaryAccountNo());

			BigDecimal beneficaryMasterSeqId =  rtnMap.get("BeneficaryMasterSeqId");

			BigDecimal beneBranchId=gSMPlaceOrderRateFeedService.getBeneBranchId(dataTable.getBeneficiaryBankId(),beneficaryMasterSeqId,getBeneficiaryName());
			setBeneficiaryBranchId(beneBranchId);


			HashMap<String, String> inputRoutingBankSetUpdetails = new HashMap<String, String>();

			inputRoutingBankSetUpdetails.put("P_APPLICATION_COUNTRY_ID", session.getCountryId().toPlainString());
			inputRoutingBankSetUpdetails.put("P_BENE_COUNTRY_ID", dataTable.getBeneficiaryCountryId().toPlainString()); // beneficiary bank Country Id
			inputRoutingBankSetUpdetails.put("P_BENE_BANK_ID", dataTable.getBeneficiaryBankId().toPlainString());
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


	}


	//procedure internal calling methods start
	public void remittancelistByBankIdForCash(PlaceOrderRateFeedBranchSupportDataTable dataTable){

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
				setRoutingDetailsShow(false);
				RequestContext.getCurrentInstance().execute("remittanceNoData.show();");
				return;
			} else if (lstRemitView.size() == 1) {
				setBooSingleRemit(true);
				setBooMultipleRemit(false);
				setRemittanceName(lstRemitView.get(0).getPopulateName());
				setRemitMode(lstRemitView.get(0).getPopulateId());
				deliverylistByRemitIdForCash(dataTable);
			} else {
				setRemittanceName(null);
				setRemitMode(null);
				setBooSingleRemit(false);
				setBooMultipleRemit(true);
				setLstofRemittance(lstRemitView);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deliverylistByRemitIdForCash(PlaceOrderRateFeedBranchSupportDataTable dataTable){

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
				setRoutingDetailsShow(false);
				RequestContext.getCurrentInstance().execute("DeliveryNoData.show();");
				return;
			} else if (lstDeliveryView.size() == 1) {
				setBooRenderDeliveryModeInputPanel(true);
				setBooRenderDeliveryModeDDPanel(false);
				setDeliveryModeInput(lstDeliveryView.get(0).getPopulateName());
				setDeliveryMode(lstDeliveryView.get(0).getPopulateId());
				//bankBranchByBankView();
			} else {
				setDeliveryModeInput(null);
				setDeliveryMode(null);
				setBooRenderDeliveryModeInputPanel(false);
				setBooRenderDeliveryModeDDPanel(true);
				setLstofDelivery(lstDeliveryView);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void bankBranchByBankView(PlaceOrderRateFeedBranchSupportDataTable dataTable){

		if (getRoutingBank() != null) {
			List<PopulateData> lstRoutingBankBranch = iPersonalRemittanceService.getRoutingBranchList(session.getCountryId(),
					dataTable.getBeneficiaryBankId(), getBeneficiaryBranchId(),dataTable.getBeneficiaryCountryId(),dataTable.getBeneCurrencyId(), getDataserviceid(),
					getRoutingCountry(), getRoutingBank(),getRemitMode(), getDeliveryMode());

			if (lstRoutingBankBranch.size() == 0) {
				setBooSingleRoutingBranch(true);
				setBooMultipleRoutingBranch(false);
				setRoutingBranchName(null);
				setRoutingBranch(null);
			} else if (lstRoutingBankBranch.size() == 1) {
				setBooSingleRoutingBranch(true);
				setBooMultipleRoutingBranch(false);
				setRoutingBranch(lstRoutingBankBranch.get(0).getPopulateId());
				setRoutingBranchName(lstRoutingBankBranch.get(0).getPopulateName());
			} else {
				setRoutingBranchName(null);
				setRoutingBranch(null);
				setBooSingleRoutingBranch(false);
				setBooMultipleRoutingBranch(true);
				setRoutingBankBranchlst(lstRoutingBankBranch);
			}

		}
	}

	public void deliverylistByRemitId(PlaceOrderRateFeedBranchSupportDataTable dataTable){

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
			setRoutingDetailsShow(false);
			RequestContext.getCurrentInstance().execute("DeliveryNoData.show();");
			return;
		} else if (lstDeliveryView.size() == 1) {
			setBooRenderDeliveryModeInputPanel(true);
			setBooRenderDeliveryModeDDPanel(false);
			setDeliveryModeInput(lstDeliveryView.get(0).getPopulateName());
			setDeliveryMode(lstDeliveryView.get(0).getPopulateId());
			bankBranchByBankView(dataTable);
		} else {
			setDeliveryModeInput(null);
			setDeliveryMode(null);
			setBooRenderDeliveryModeInputPanel(false);
			setBooRenderDeliveryModeDDPanel(true);
			setLstofDelivery(lstDeliveryView);
		}
	}

	public void remittancelistByBankId(PlaceOrderRateFeedBranchSupportDataTable dataTable){

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
			setRoutingDetailsShow(false);
			RequestContext.getCurrentInstance().execute("remittanceNoData.show();");
			return;
		} else if (lstRemitView.size() == 1) {
			setBooSingleRemit(true);
			setBooMultipleRemit(false);
			setRemittanceName(lstRemitView.get(0).getPopulateName());
			setRemitMode(lstRemitView.get(0).getPopulateId());
			deliverylistByRemitId(dataTable);
		} else {
			setRemittanceName(null);
			setRemitMode(null);
			setBooSingleRemit(false);
			setBooMultipleRemit(true);
			setLstofRemittance(lstRemitView);
			//setRemitMode(lstRemitView.get(0).getPopulateId());
			//deliverylistByRemitId();
		}
	}

	public void bankDetailsByCountry(PlaceOrderRateFeedBranchSupportDataTable dataTable){



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

	public void getServiceListDetails(PlaceOrderRateFeedBranchSupportDataTable dataTable){
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
	}

	//based on service selection render fields
	public void countryNameByServiceId(PlaceOrderRateFeedBranchSupportDataTable dataTable){

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
		setBooSingleService(false);
		setBooMultipleService(false);
		setAccountSeqId(null);
		setDataserviceid(null);
		setDatabenificaryservice(null);
		setRoutingCountryName(null);
		setBooSingleRoutingCountry(false);
		setBooMultipleRoutingCountry(false);
		setRoutingBankName(null);;
		setBooMultipleRoutingBank(false);
		setBooSingleRoutingBank(false);
		setRoutingBranchName(null);
		setBooMultipleRoutingBranch(false);
		setBooSingleRoutingBranch(false);
		setRoutingBranch(null);
		setBooRenderRemitPanel(false);
		//Routing Remittance Variables
		setBooMultipleRemit(false);
		setBooSingleRemit(false);
		setRemittanceName(null);;
		setRemitMode(null);;
		//Routing Delivery Variables
		setBooRenderDeliveryModeDDPanel(false);
		setBooRenderDeliveryModeInputPanel(false);
		setDeliveryModeInput(null);;
		setDeliveryMode(null);;

		setLstofRemittance(null);
		setLstofDelivery(null);
		setRoutingBankBranchlst(null);
		setRoutingbankvalues(null);
		setRoutingCountrylst(null);
		setRoutingDetailsShow(false);
		//setAcceptPlaceOrderDataTable(null);
	}

	public boolean populateBeneficiary(PlaceOrderRateFeedBranchSupportDataTable dataTable){

		boolean beneNameCheck=false;
		try{
			setBeneficiaryName(null);
			List<PopulateData> lstBeneName=iPlaceOnOrderCreationService.getBeneficiarNameList(dataTable.getCustomerId(), dataTable.getBeneficiaryCountryId(), dataTable.getRemittType(),dataTable.getCustomerRefNo());

			if(lstBeneName!=null && lstBeneName.size()>0)
			{
				setBeneficiaryList(lstBeneName);
				beneNameCheck=true;
			}else
			{
				beneNameCheck=false;
			}



		}catch(Exception e){
			setErrorMessage( e.getMessage());
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
			clearRoutingSetup();
			if(getBeneficiaryName()!=null){

				//setCurrencyQuoteName(null);

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

					/*HashMap<String, BigDecimal> rtnMap=iPlaceOnOrderCreationService.getBeneMasterIdCurrencyId(getBeneficiaryName(),getBeneficiaryCountryId(),getBeneficiaryBankId(),getServiceGroupId(),getCustomerRefNo());

					BigDecimal beneficaryMasterSeqId =  rtnMap.get("BeneficaryMasterSeqId");*/

					/*	String currencyQuoteName =iPlaceOnOrderCreationService.toFetchCurrencyQtyName(beneficaryMasterSeqId,getAccountSequenceId(),getCustomerRef());
						if(currencyQuoteName!=null)
						{
							setCurrencyQuoteName("["+currencyQuoteName+"]");
						}*/



				}else if(accountNumberList!=null && accountNumberList.size()>1){
					setBooRenderBnkAccNoList(true);
					setBooRenderBnkAccNo(false);
					setLstAccountNumber(accountNumberList);
					/*log.info( "===========bank account number======"+accountNumberList.get( 0).getBankAccountNumber());
							setAccountNumber(accountNumberList.get( 0).getBankAccountNumber() );
							setAccountSequenceId(accountNumberList.get(0).getBeneficiaryAccountSeqId() );
							setCurrencyId(accountNumberList.get(0).getCurrencyId());*/
				}


				setRoutingDetailsShow(true);
				routingsetupDetails(getAcceptPlaceOrderDataTable());
				addtionalMethods();

			}/*else
			{
				clearRoutingSetup();
			}*/

		}catch(Exception e){
			setErrorMessage( e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}

	}
	
	private void addtionalMethods() throws AMGException
	{
		getSourceofIncomeDetails();
		toFetchPaymentDetails();
		dynamicLevel();
		matchData();
	}
}
