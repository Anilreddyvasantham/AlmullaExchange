package com.amg.exchange.online.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.dto.BankMasterDTO;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.mail.ApllicationMailer1;
import com.amg.exchange.online.model.RatePlaceOrder;
import com.amg.exchange.online.service.IGSMPlaceOrderRateFeedService;
import com.amg.exchange.online.service.IPlaceAnOrderCreationService;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.registration.model.ViewArea;
import com.amg.exchange.registration.service.IRemmiterOnlineRegService;
import com.amg.exchange.remittance.bean.PopulateData;
import com.amg.exchange.remittance.model.BeneficaryMaster;
import com.amg.exchange.remittance.service.IPersonalRemittanceService;
import com.amg.exchange.remittance.service.IRateAlertSetupService;
import com.amg.exchange.remittance.service.IServiceGroupMasterService;
import com.amg.exchange.remittance.service.ISpecialRateRequestService;
import com.amg.exchange.stoppayment.service.IStopPaymentService;
import com.amg.exchange.treasury.deal.supplier.model.ApplicationSetup;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.CurrencyOtherInformation;
import com.amg.exchange.treasury.model.ServiceMasterDesc;
import com.amg.exchange.treasury.service.DeliveryModeService;
import com.amg.exchange.treasury.service.IBankBranchDetailsService;
import com.amg.exchange.treasury.service.IRemittanceModeService;
import com.amg.exchange.treasury.service.ServiceCodeMasterService;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
//import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;
import com.amg.exchange.util.WarningHandler;
@Component("gSMPlaceOrderBean")
@Scope("session")
public class GSMPlaceOrderBean<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger log=Logger.getLogger(GSMPlaceOrderBean.class);
	SessionStateManage session= new SessionStateManage();

	//Page Level Variables
	private BigDecimal rateOfferedPk;
	private String area;
	private BigDecimal countryBranchid;
	private BigDecimal currencyid;
	private BigDecimal customerReference;
	private BigDecimal beneficiaryBankid;
	private BigDecimal beneficiaryCountryId;
	private String fixRateid;
	private String beneficiaryName;
	private BigDecimal remittanceType;
	private BigDecimal rateOfferMinRate;
	private BigDecimal rateOfferMaxRate;
	private String customerEmailId;
	private BigDecimal serviceGroupId;

	//Boolean Variables
	private Boolean booRoutingDeatls;
	private Boolean boofixRateCurrency;
	private Boolean booRenderCurrency;
	private Boolean booRenderDataTable;
	private Boolean booRenderSaveOrExit;
	private Boolean booRenderFixRate;
	private String customerRefandName;
	private Boolean booRenderlstFiveTrnxDataTable;
	private Boolean booRenderSingleBank=false;
	private Boolean booRenderMultipleBank=false;
	private Boolean booRenderBeneBank;

	// Routing County Variables
	private BigDecimal routingCountry;
	private String routingCountryName;
	// Routing Bank Variables
	private BigDecimal routingBank;
	private String routingBankName;
	private BigDecimal rateOffered;
	private BigDecimal beneficiaryBranchId;

	//common Variables
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String isActive;
	private String errorMessage;
	private String procedureError;
	private List<ViewArea> lstViewArea;
	private String beneficiaryBankName;


	//List 
	private List<Employee> lstEmploye;
	private List<CountryBranch> lstCountryBranch;
	private List<CurrencyMaster> lstCurrencyMaster;
	private List<BankMasterDTO> lstBankMasterDTO;
	private List<GSMPlaceOrderDataTable> lstGsmPlaceOrderDt;
	private List<GSMPlaceOrderDataTable> gsmPlaceOrderDt;
	private List<PopulateData> lstRoutingCountry;
	private List<PopulateData> lstRoutingBank;
	private List<CountryMasterDesc> lstCountry;
	private List<PopulateData> lstBanks ;
	
	private BigDecimal serviceMasterId;
	private String serviceMasterCode;
	private String serviceMasterName;
	private boolean beneBankMandatory;
	private String serviceEFTorTTName;
	private List<ServiceMasterDesc> lstServiceMaster = new ArrayList<ServiceMasterDesc>();
	private List<PopulateData> lstRoutingChecksforSearch = new ArrayList<PopulateData>();
	

	//Service 
	@Autowired
	IGSMPlaceOrderRateFeedService gSMPlaceOrderRateFeedService;

	@Autowired
	IGeneralService<T> generalService;

	@Autowired 
	IServiceGroupMasterService iServiceGroupMasterService;

	@Autowired
	IPersonalRemittanceService iPersonalRemittanceService;

	@Autowired
	ServiceCodeMasterService serviceMasterService;

	@Autowired
	IBankBranchDetailsService<T> bankBranchDetailsService;

	@Autowired
	ISpecialRateRequestService specialRateService;

	@Autowired
	IRemittanceModeService iRemitModeMaster;

	@Autowired
	DeliveryModeService iDeliveryModeMaster;

	@Autowired
	IStopPaymentService<T> stopPaymentService;

	@Autowired
	IPlaceAnOrderCreationService  iPlaceOnOrderCreationService;
	
	@Autowired
	IRateAlertSetupService<T> iRateAlertSetupService;
	
	@Autowired
	IRemmiterOnlineRegService iRemmiterOnlineRegService;
	
	@Autowired
	ApllicationMailer1 apllicationMailer1;

	public Boolean getBooRenderBeneBank() {
		return booRenderBeneBank;
	}
	public void setBooRenderBeneBank(Boolean booRenderBeneBank) {
		this.booRenderBeneBank = booRenderBeneBank;
	}
	public String getBeneficiaryBankName() {
		return beneficiaryBankName;
	}
	public void setBeneficiaryBankName(String beneficiaryBankName) {
		this.beneficiaryBankName = beneficiaryBankName;
	}
	public List<PopulateData> getLstBanks() {
		return lstBanks;
	}
	public void setLstBanks(List<PopulateData> lstBanks) {
		this.lstBanks = lstBanks;
	}
	public Boolean getBooRenderSingleBank() {
		return booRenderSingleBank;
	}
	public void setBooRenderSingleBank(Boolean booRenderSingleBank) {
		this.booRenderSingleBank = booRenderSingleBank;
	}
	public Boolean getBooRenderMultipleBank() {
		return booRenderMultipleBank;
	}
	public void setBooRenderMultipleBank(Boolean booRenderMultipleBank) {
		this.booRenderMultipleBank = booRenderMultipleBank;
	}
	public BigDecimal getServiceGroupId() {
		return serviceGroupId;
	}
	public void setServiceGroupId(BigDecimal serviceGroupId) {
		this.serviceGroupId = serviceGroupId;
	}
	public List<CountryMasterDesc> getLstCountry() {
		return lstCountry;
	}
	public void setLstCountry(List<CountryMasterDesc> lstCountry) {
		this.lstCountry = lstCountry;
	}
	public List<ViewArea> getLstViewArea() {
		return lstViewArea;
	}
	public void setLstViewArea(List<ViewArea> lstViewArea) {
		this.lstViewArea = lstViewArea;
	}
	public BigDecimal getRateOfferedPk() {
		return rateOfferedPk;
	}
	public void setRateOfferedPk(BigDecimal rateOfferedPk) {
		this.rateOfferedPk = rateOfferedPk;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public BigDecimal getCountryBranchid() {
		return countryBranchid;
	}
	public void setCountryBranchid(BigDecimal countryBranchid) {
		this.countryBranchid = countryBranchid;
	}
	public BigDecimal getCurrencyid() {
		return currencyid;
	}
	public void setCurrencyid(BigDecimal currencyid) {
		this.currencyid = currencyid;
	}
	public BigDecimal getCustomerReference() {
		return customerReference;
	}
	public void setCustomerReference(BigDecimal customerReference) {
		this.customerReference = customerReference;
	}
	public BigDecimal getBeneficiaryBankid() {
		return beneficiaryBankid;
	}
	public void setBeneficiaryBankid(BigDecimal beneficiaryBankid) {
		this.beneficiaryBankid = beneficiaryBankid;
	}
	public String getFixRateid() {
		return fixRateid;
	}
	public void setFixRateid(String fixRateid) {
		this.fixRateid = fixRateid;
	}
	public Boolean getBooRoutingDeatls() {
		return booRoutingDeatls;
	}
	public void setBooRoutingDeatls(Boolean booRoutingDeatls) {
		this.booRoutingDeatls = booRoutingDeatls;
	}

	public BigDecimal getRoutingCountry() {
		return routingCountry;
	}
	public void setRoutingCountry(BigDecimal routingCountry) {
		this.routingCountry = routingCountry;
	}

	public String getRoutingCountryName() {
		return routingCountryName;
	}
	public void setRoutingCountryName(String routingCountryName) {
		this.routingCountryName = routingCountryName;
	}

	public BigDecimal getRoutingBank() {
		return routingBank;
	}
	public void setRoutingBank(BigDecimal routingBank) {
		this.routingBank = routingBank;
	}

	public String getRoutingBankName() {
		return routingBankName;
	}
	public void setRoutingBankName(String routingBankName) {
		this.routingBankName = routingBankName;
	}
	public BigDecimal getRateOffered() {
		return rateOffered;
	}
	public void setRateOffered(BigDecimal rateOffered) {
		this.rateOffered = rateOffered;
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
	public List<Employee> getLstEmploye() {
		return lstEmploye;
	}
	public void setLstEmploye(List<Employee> lstEmploye) {
		this.lstEmploye = lstEmploye;
	}
	public List<CountryBranch> getLstCountryBranch() {
		return lstCountryBranch;
	}
	public void setLstCountryBranch(List<CountryBranch> lstCountryBranch) {
		this.lstCountryBranch = lstCountryBranch;
	}
	public List<CurrencyMaster> getLstCurrencyMaster() {
		return lstCurrencyMaster;
	}
	public void setLstCurrencyMaster(List<CurrencyMaster> lstCurrencyMaster) {
		this.lstCurrencyMaster = lstCurrencyMaster;
	}
	public List<BankMasterDTO> getLstBankMasterDTO() {
		return lstBankMasterDTO;
	}
	public void setLstBankMasterDTO(List<BankMasterDTO> lstBankMasterDTO) {
		this.lstBankMasterDTO = lstBankMasterDTO;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public BigDecimal getBeneficiaryCountryId() {
		return beneficiaryCountryId;
	}
	public void setBeneficiaryCountryId(BigDecimal beneficiaryCountryId) {
		this.beneficiaryCountryId = beneficiaryCountryId;
	}
	public List<GSMPlaceOrderDataTable> getLstGsmPlaceOrderDt() {
		return lstGsmPlaceOrderDt;
	}
	public void setLstGsmPlaceOrderDt(
			List<GSMPlaceOrderDataTable> lstGsmPlaceOrderDt) {
		this.lstGsmPlaceOrderDt = lstGsmPlaceOrderDt;
	}
	public BigDecimal getBeneficiaryBranchId() {
		return beneficiaryBranchId;
	}
	public void setBeneficiaryBranchId(BigDecimal beneficiaryBranchId) {
		this.beneficiaryBranchId = beneficiaryBranchId;
	}
	public String getBeneficiaryName() {
		return beneficiaryName;
	}
	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}
	public BigDecimal getRemittanceType() {
		return remittanceType;
	}
	public void setRemittanceType(BigDecimal remittanceType) {
		this.remittanceType = remittanceType;
	}

	public List<PopulateData> getLstRoutingCountry() {
		return lstRoutingCountry;
	}
	public void setLstRoutingCountry(List<PopulateData> lstRoutingCountry) {
		this.lstRoutingCountry = lstRoutingCountry;
	}
	public List<PopulateData> getLstRoutingBank() {
		return lstRoutingBank;
	}
	public void setLstRoutingBank(List<PopulateData> lstRoutingBank) {
		this.lstRoutingBank = lstRoutingBank;
	}
	public BigDecimal getRateOfferMinRate() {
		return rateOfferMinRate;
	}
	public void setRateOfferMinRate(BigDecimal rateOfferMinRate) {
		this.rateOfferMinRate = rateOfferMinRate;
	}
	public BigDecimal getRateOfferMaxRate() {
		return rateOfferMaxRate;
	}
	public void setRateOfferMaxRate(BigDecimal rateOfferMaxRate) {
		this.rateOfferMaxRate = rateOfferMaxRate;
	}
	public Boolean getBoofixRateCurrency() {
		return boofixRateCurrency;
	}
	public void setBoofixRateCurrency(Boolean boofixRateCurrency) {
		this.boofixRateCurrency = boofixRateCurrency;
	}
	public Boolean getBooRenderCurrency() {
		return booRenderCurrency;
	}
	public void setBooRenderCurrency(Boolean booRenderCurrency) {
		this.booRenderCurrency = booRenderCurrency;
	}
	public Boolean getBooRenderDataTable() {
		return booRenderDataTable;
	}
	public void setBooRenderDataTable(Boolean booRenderDataTable) {
		this.booRenderDataTable = booRenderDataTable;
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
	public Boolean getBooRenderFixRate() {
		return booRenderFixRate;
	}
	public void setBooRenderFixRate(Boolean booRenderFixRate) {
		this.booRenderFixRate = booRenderFixRate;
	}
	public List<GSMPlaceOrderDataTable> getGsmPlaceOrderDt() {
		return gsmPlaceOrderDt;
	}
	public void setGsmPlaceOrderDt(List<GSMPlaceOrderDataTable> gsmPlaceOrderDt) {
		this.gsmPlaceOrderDt = gsmPlaceOrderDt;
	}
	public String getCustomerEmailId() {
		return customerEmailId;
	}
	public void setCustomerEmailId(String customerEmailId) {
		this.customerEmailId = customerEmailId;
	}
	public String getCustomerRefandName() {
		return customerRefandName;
	}
	public void setCustomerRefandName(String customerRefandName) {
		this.customerRefandName = customerRefandName;
	}
	public Boolean getBooRenderlstFiveTrnxDataTable() {
		return booRenderlstFiveTrnxDataTable;
	}
	public void setBooRenderlstFiveTrnxDataTable(
			Boolean booRenderlstFiveTrnxDataTable) {
		this.booRenderlstFiveTrnxDataTable = booRenderlstFiveTrnxDataTable;
	}
	
	public BigDecimal getServiceMasterId() {
		return serviceMasterId;
	}
	public void setServiceMasterId(BigDecimal serviceMasterId) {
		this.serviceMasterId = serviceMasterId;
	}
	
	public String getServiceMasterCode() {
		return serviceMasterCode;
	}
	public void setServiceMasterCode(String serviceMasterCode) {
		this.serviceMasterCode = serviceMasterCode;
	}
	
	public String getServiceMasterName() {
		return serviceMasterName;
	}
	public void setServiceMasterName(String serviceMasterName) {
		this.serviceMasterName = serviceMasterName;
	}
	
	public List<ServiceMasterDesc> getLstServiceMaster() {
		return lstServiceMaster;
	}
	public void setLstServiceMaster(List<ServiceMasterDesc> lstServiceMaster) {
		this.lstServiceMaster = lstServiceMaster;
	}
	
	public boolean isBeneBankMandatory() {
		return beneBankMandatory;
	}
	public void setBeneBankMandatory(boolean beneBankMandatory) {
		this.beneBankMandatory = beneBankMandatory;
	}
	
	public List<PopulateData> getLstRoutingChecksforSearch() {
		return lstRoutingChecksforSearch;
	}
	public void setLstRoutingChecksforSearch(List<PopulateData> lstRoutingChecksforSearch) {
		this.lstRoutingChecksforSearch = lstRoutingChecksforSearch;
	}
	
	public String getServiceEFTorTTName() {
		return serviceEFTorTTName;
	}
	public void setServiceEFTorTTName(String serviceEFTorTTName) {
		this.serviceEFTorTTName = serviceEFTorTTName;
	}
	
	/*@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;*/
	//PageNavigation
	public void pageNavigation(){
		clearAllFields();
		toFetchAllArea();
		toFetchCountryBranches();
		toFetchAllCurrency();
		toFetchAllBeneficiaryBank();
		setBooRenderCurrency(true);
		setBooRoutingDeatls(false);
		setBoofixRateCurrency(false);
		setBooRenderDataTable(false);
		setBooRenderSaveOrExit(false);
		setBooRenderFixRate(false);
		setBooRenderlstFiveTrnxDataTable(false);
		List<CountryMasterDesc>  lstCountryMasterDesc= generalService.getCountryList(session.getLanguageId());
		setLstCountry(lstCountryMasterDesc);
		try {
			//loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "GsmPlaceOrder.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../onlinespecialrate/GsmPlaceOrder.xhtml");
		} catch (Exception exception) {
			setErrorMessage(exception.getMessage());
			log.error("GSMPageNavigation  :"+ exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
		}
	}

	//clear All Fields
	public void clearAllFields(){
		setRateOfferedPk(null);
		setArea(null);
		setCountryBranchid(null);
		setCurrencyid(null);
		setCustomerReference(null);
		setBeneficiaryBankid(null);
		setFixRateid(null);
		setRoutingCountry(null);
		setRoutingCountryName(null);
		setRoutingBank(null);
		setRoutingBankName(null);
		setRateOffered(null);
		setCustomerRefandName(null);
		setBooRenderMultipleBank(true);
		setBooRenderBeneBank(true);
		setBeneficiaryBankid(null);
		setBeneficiaryBankName(null);
		setBeneficiaryCountryId(null);
		setServiceMasterId(null);
		setServiceMasterCode(null);
		setServiceMasterName(null);
		lstServiceMaster.clear();
		setLstRoutingChecksforSearch(null);
		
	}

	//toFetchAllArea
	public void toFetchAllArea(){
		setLstViewArea(null);
		//List<Employee> employeeList=gSMPlaceOrderRateFeedService.toFetchEmployeeArea();
		List<ViewArea> lstViewArea=gSMPlaceOrderRateFeedService.getAreaPlace();

		if(lstViewArea.size()>0){
			setLstViewArea(lstViewArea);
		}
	}
	//ToFetchCountry Branches
	public void toFetchCountryBranches(){
		//lstCountryBranch.clear();
		List<CountryBranch> countryBranchs=generalService.getBranchDetails(session.getCountryId());
		if(countryBranchs.size()>0){
			setLstCountryBranch(countryBranchs);
		}
	}
	//to Fetch All Currency
	public void toFetchAllCurrency(){
		//lstCurrencyMaster.clear();
		List<CurrencyMaster> currencyMasters=iRateAlertSetupService.getCurrencyList();
		if(currencyMasters.size()>0){
			setLstCurrencyMaster(currencyMasters);
		}
	}
	//toFetchAllBeneficiary Bank
	public void toFetchAllBeneficiaryBank(){
		//lstBankMasterDTO.clear();
		List<BankMasterDTO> bankMasterDTOs=generalService.getBeneBankListForApplicationCountry(session.getCountryId());
		if(bankMasterDTOs.size()>0){
			setLstBankMasterDTO(bankMasterDTOs);
		}
	}
	//toFetchRoutingDetails
	public void toFetchRoutingDetails(){
		if(getFixRateid().equalsIgnoreCase(Constants.Yes)){
			setBooRoutingDeatls(true);
			setBoofixRateCurrency(true);
			setBooRenderCurrency(false);
			setBooRenderDataTable(false);
			setBooRenderSaveOrExit(false);
			setCurrencyid(null);
			setLstRoutingCountry(null);
			//setLstRoutingBank(null);
			setRoutingCountry(null);
			setRoutingBank(null);
			setBooRenderlstFiveTrnxDataTable(false);
			RequestContext.getCurrentInstance().execute("selectCurrency.show();");
			return;
		}else{
			setBooRoutingDeatls(false);
			setBoofixRateCurrency(false);
			setBooRenderCurrency(true);
			setCurrencyid(null);
			setBooRenderDataTable(false);
			setBooRenderSaveOrExit(false);
			setLstRoutingCountry(null);
			setLstRoutingBank(null);
			setRoutingCountry(null);
			setRoutingBank(null);
			setRateOffered(null);
			setBooRenderlstFiveTrnxDataTable(false);
		}
	}
	//to fetch routing country
	public void toFtechRoutingCountry(){
		if(getFixRateid().equalsIgnoreCase(Constants.Yes)){
			setBooRenderFixRate(true);
			setLstRoutingCountry(null);
			setLstRoutingBank(null);
			setRoutingCountry(null);
			setRoutingBank(null);
			setBooRoutingDeatls(true);
			setBooRenderlstFiveTrnxDataTable(false);
			setLstGsmPlaceOrderDt(null);
			setBooRenderDataTable(false);
			setBooRenderSaveOrExit(false);
			setServiceMasterId(null);
			setServiceMasterCode(null);
			setServiceMasterName(null);
			lstServiceMaster.clear();
			setLstRoutingChecksforSearch(null);
			setBooRenderlstFiveTrnxDataTable(false);
			setCurrencyid(null);
			setCustomerReference(null);
			setBeneficiaryCountryId(null);
			setBeneficiaryBankid(null);
			setBeneficiaryBankName(null);
			setBeneBankMandatory(false);
			getServiceMasterList();
		}else{
			setBooRenderFixRate(false);
			setBooRoutingDeatls(false);
			setBoofixRateCurrency(false);
			setBooRenderCurrency(true);
			setBooRenderDataTable(false);
			setBooRenderSaveOrExit(false);
			setLstRoutingCountry(null);
			setLstRoutingBank(null);
			setRoutingCountry(null);
			setRoutingBank(null);
			setRateOffered(null);
			setServiceMasterId(null);
			setServiceMasterCode(null);
			setServiceMasterName(null);
			lstServiceMaster.clear();
			setLstRoutingChecksforSearch(null);
			setBooRenderlstFiveTrnxDataTable(false);
			setCurrencyid(null);
			setCustomerReference(null);
			setBeneficiaryCountryId(null);
			setBeneficiaryBankid(null);
			setBeneficiaryBankName(null);
			setBeneBankMandatory(false);
		}
	}

	//to fetch routing bank
	public void toFetchRoutingBank(){
		setLstRoutingBank(null);
		setLstRoutingChecksforSearch(null);
		setRoutingBank(null);
		setRateOffered(null);
		setBooRenderDataTable(false);
		setBooRenderSaveOrExit(false);
		setBooRenderlstFiveTrnxDataTable(false);
		setServiceEFTorTTName(null);
		
		List<PopulateData> routingBankList = new ArrayList<PopulateData>();
		List<PopulateData> chkEFTorTTLst = new ArrayList<PopulateData>();
		List<PopulateData> allEFTorTTLst = new ArrayList<PopulateData>();
		List<BigDecimal> duplicateChk = new ArrayList<BigDecimal>();
		
		if(getServiceMasterId() != null){
			
			
			routingBankList = gSMPlaceOrderRateFeedService.toFtechAllRoutingBanksBasedOncurrencyServiceId(getCurrencyid(),getRoutingCountry(),getServiceMasterId());
			
			if(routingBankList != null && routingBankList.size() != 0){
				if(lstServiceMaster != null && lstServiceMaster.size() != 0){
					BigDecimal serviceSelected = null;
					String serviceSelectedName = null;
					for (ServiceMasterDesc populateData : lstServiceMaster) {
						if(getServiceMasterId().compareTo(populateData.getServiceMaster().getServiceId()) == 0){
							serviceSelected = populateData.getServiceMaster().getServiceId();
							serviceSelectedName = populateData.getLocalServiceDescription();
							break;
						}
					}
					
					if(serviceSelected != null && serviceSelectedName != null){
						
						if(serviceSelectedName.equalsIgnoreCase(Constants.CASHNAME)){
							routingBankList = gSMPlaceOrderRateFeedService.toFtechAllRoutingBanksBasedOncurrencyServiceId(getCurrencyid(),getRoutingCountry(),serviceSelected);
							setLstRoutingChecksforSearch(routingBankList);
							setServiceEFTorTTName(serviceSelectedName);
						}else if(serviceSelectedName.equalsIgnoreCase(Constants.EFTNAME)){
							
							BigDecimal ttId = null;
							String ttName = null;
							for (ServiceMasterDesc populateData : lstServiceMaster) {
								if(populateData.getLocalServiceDescription().equalsIgnoreCase(Constants.TTNAME)){
									ttId = populateData.getServiceMaster().getServiceId();
									ttName = populateData.getLocalServiceDescription();
									break;
								}
							}
							
							if(ttId != null){
								List<PopulateData> lstchkEFTorTT = gSMPlaceOrderRateFeedService.toFtechAllRoutingBanksBasedOncurrencyServiceId(getCurrencyid(),getRoutingCountry(),ttId);
								if(lstchkEFTorTT != null && lstchkEFTorTT.size() != 0){
									
									allEFTorTTLst.addAll(lstchkEFTorTT);
									allEFTorTTLst.addAll(routingBankList);
									
									for (PopulateData populateData1 : allEFTorTTLst) { // SBI, SBT, ICICI, HDFC
										int i = 0;
										for (PopulateData populateData2 : routingBankList) {  // HDFC, SBI
											if(populateData2.getPopulateId().compareTo(populateData1.getPopulateId())!=0){
												i = 1;
												break;
											}else{
												i = 0;
											}
										}
										if(i == 1){
											chkEFTorTTLst.add(populateData1);
										}
									}
									

									if(chkEFTorTTLst != null && chkEFTorTTLst.size() != 0){
										List<PopulateData> popData = new ArrayList<PopulateData>();
										for (PopulateData populateData3 : chkEFTorTTLst) {
											if(!duplicateChk.contains(populateData3.getPopulateId())){
												duplicateChk.add(populateData3.getPopulateId());
												popData.add(populateData3);
											}
										}
										
										setLstRoutingChecksforSearch(popData);
										setServiceEFTorTTName(ttName);
									}
								}
							}
							
						}else if(serviceSelectedName.equalsIgnoreCase(Constants.TTNAME)){
							
							BigDecimal eftId = null;
							String eftName = null;
							for (ServiceMasterDesc populateData : lstServiceMaster) {
								if(populateData.getLocalServiceDescription().equalsIgnoreCase(Constants.EFTNAME)){
									eftId = populateData.getServiceMaster().getServiceId();
									eftName = populateData.getLocalServiceDescription();
									break;
								}
							}
							
							if(eftId != null){
								List<PopulateData> lstchkEFTorTT = gSMPlaceOrderRateFeedService.toFtechAllRoutingBanksBasedOncurrencyServiceId(getCurrencyid(),getRoutingCountry(),eftId);
								if(lstchkEFTorTT != null && lstchkEFTorTT.size() != 0){
									
									allEFTorTTLst.addAll(lstchkEFTorTT);
									allEFTorTTLst.addAll(routingBankList);
									
									for (PopulateData populateData1 : allEFTorTTLst) { // SBI, SBT, ICICI, HDFC
										int i = 0;
										for (PopulateData populateData2 : routingBankList) {  // HDFC, SBI
											if(populateData2.getPopulateId().compareTo(populateData1.getPopulateId())!=0){
												i = 1;
												break;
											}else{
												i = 0;
											}
										}
										if(i == 1){
											chkEFTorTTLst.add(populateData1);
										}
									}
									

									if(chkEFTorTTLst != null && chkEFTorTTLst.size() != 0){
										List<PopulateData> popData = new ArrayList<PopulateData>();
										for (PopulateData populateData3 : chkEFTorTTLst) {
											if(!duplicateChk.contains(populateData3.getPopulateId())){
												duplicateChk.add(populateData3.getPopulateId());
												popData.add(populateData3);
											}
										}
										
										setLstRoutingChecksforSearch(popData);
										setServiceEFTorTTName(eftName);
									}
								}
							}
							
						}else{
							// not available
						}
					}else{
						// not available
					}
				}
			}else{
				routingBankList = gSMPlaceOrderRateFeedService.toFtechAllRoutingBanksBasedOncurrency(getCurrencyid(),getRoutingCountry(),null);
			}
		}else{
			routingBankList = gSMPlaceOrderRateFeedService.toFtechAllRoutingBanksBasedOncurrency(getCurrencyid(),getRoutingCountry(),null);
		}
		
		if(routingBankList != null && routingBankList.size() != 0){
			
			if(routingBankList.size() == 1){
				setLstRoutingBank(routingBankList);
				
				PopulateData popCountry = routingBankList.get(0);
				setRoutingBank(popCountry.getPopulateId());
				
			}else{
				setLstRoutingBank(routingBankList);
			}
		}
	}

	// in data table
	public void toFetchRoutingBankByroutingCountry(GSMPlaceOrderDataTable gsmPlaceOrder){
		List<PopulateData> routingBankList=gSMPlaceOrderRateFeedService.toFtechAllRoutingBanksBasedOncurrency(gsmPlaceOrder.getCurrencyId(),gsmPlaceOrder.getRoutingCountry(),gsmPlaceOrder.getRemittanceType());
		if(routingBankList.size()>0){
			gsmPlaceOrder.setRoutingbankvalues(routingBankList);
			for(PopulateData populateData:routingBankList)
			{
				if(gsmPlaceOrder.getBeneficiaryBankId().compareTo(populateData.getPopulateId())==0)
				{
					gsmPlaceOrder.setRoutingBankId(gsmPlaceOrder.getBeneficiaryBankId());
					break;
				}
			}
			
			// actual Rate
			if(gsmPlaceOrder.getRoutingBankId() != null){
				BigDecimal acctualRate = fetchActualExchangeRate(gsmPlaceOrder);
				gsmPlaceOrder.setActualRate(acctualRate);
			}
		}else{
			gsmPlaceOrder.setRoutingbankvalues(routingBankList);
		}
	}

	//to Enble fix Rate Panel
	public void toEnbleFixRate(){
		if(getCurrencyid() != null && getBeneficiaryCountryId() != null){
			//setBooRenderFixRate(false);
			//setBooRoutingDeatls(true);
			//setBoofixRateCurrency(true);
			setBooRenderCurrency(true);
			setBooRenderDataTable(false);
			setBooRenderSaveOrExit(false);
			setLstRoutingBank(null);
			setRoutingCountry(null);
			setRoutingBank(null);
			//setBooRoutingDeatls(false);
			//setFixRateid(null);
			setRateOffered(null);
			setBooRenderlstFiveTrnxDataTable(false);

			setLstRoutingCountry(null);
			List<PopulateData> routingCountryList=gSMPlaceOrderRateFeedService.toFtechAllRoutingCountry(getCurrencyid(),session.getLanguageId(),null,getBeneficiaryCountryId());
			if(routingCountryList != null && routingCountryList.size() != 0){
				if(routingCountryList.size() == 1){
					setLstRoutingCountry(routingCountryList);
					
					PopulateData popCountry = routingCountryList.get(0);
					setRoutingCountry(popCountry.getPopulateId());
					
					if(getRoutingCountry() != null){
						toFetchRoutingBank();
					}
					
				}else{
					setLstRoutingCountry(routingCountryList);
				}
				
				
			}
		}else{
			//setBooRenderFixRate(true);
			//setBooRoutingDeatls(true);
			//setBoofixRateCurrency(true);
			setBooRenderCurrency(true);
			setBooRenderDataTable(false);
			setBooRenderSaveOrExit(false);
			setLstRoutingCountry(null);
			setLstRoutingBank(null);
			setRoutingCountry(null);
			setRoutingBank(null);
			setCurrencyid(null);
			//setBooRoutingDeatls(false);
			//setFixRateid(null);
			setRateOffered(null);
			setBooRenderlstFiveTrnxDataTable(false);
		}
	}

	//click on search Button
	public void search(){
		try{
			if (getFixRateid() != null && getFixRateid().equalsIgnoreCase(Constants.Yes)) {
				if(getRoutingCountry() != null && getRoutingBank() != null && getRateOffered() != null){
					boolean minMaxrate=checkMinMaxRate();
					if(!minMaxrate)
					{
						return;
					}
				}else{
					RequestContext.getCurrentInstance().execute("plsSelectMan.show();");
					return;
				}
			}else{
				setLstRoutingCountry(null);
				if(getCurrencyid() != null){
					List<PopulateData> routingCountryList=gSMPlaceOrderRateFeedService.toFtechAllRoutingCountry(getCurrencyid(),session.getLanguageId(),null,getBeneficiaryCountryId());
					if(routingCountryList.size()>0){
						setLstRoutingCountry(routingCountryList);
					}
				}
			}

			if(getCustomerReference() != null){
				toCheckValidCustomerRef();
			}

			List<RatePlaceOrder> lstPlaceOrders=gSMPlaceOrderRateFeedService.toFetchRoutingDetails(getArea(),getCountryBranchid(),getCurrencyid(),getCustomerReference(),getBeneficiaryBankid(),getFixRateid(),getBeneficiaryCountryId(),getLstRoutingChecksforSearch(),getServiceEFTorTTName());
			if(lstPlaceOrders.size()>0){
				List<GSMPlaceOrderDataTable> lstDataTables=new ArrayList<GSMPlaceOrderDataTable>();
				for (RatePlaceOrder ratePlaceOrder : lstPlaceOrders) {
					GSMPlaceOrderDataTable gsmPlaceOrderDtOBJ=new GSMPlaceOrderDataTable();
					String custerName=null;
					String ctryName=null;
					String beneBank=null;
					String routingBank=null;
					String routingCountry=null;
					
					/*Dispaly Data table Variables started*/
					//CustomerId and Name
					gsmPlaceOrderDtOBJ.setApplicationCountryId(ratePlaceOrder.getApplicationCountryId());
					gsmPlaceOrderDtOBJ.setCustomerId(ratePlaceOrder.getCustomerId());
					custerName=generalService.getCustomerNameCustomerId(ratePlaceOrder.getCustomerId());
					gsmPlaceOrderDtOBJ.setCustomerName(custerName);
					gsmPlaceOrderDtOBJ.setCustomerReference(ratePlaceOrder.getCustomerreference());
					//CountryId and Name
					gsmPlaceOrderDtOBJ.setBeneficiaryCountryId(ratePlaceOrder.getBeneficiaryCountryId());
					ctryName=generalService.getCountryName(session.getLanguageId(), ratePlaceOrder.getBeneficiaryCountryId());
					gsmPlaceOrderDtOBJ.setBeneficiaryCountryName(ctryName);
					//BranchId and Name
					gsmPlaceOrderDtOBJ.setBeneficiaryBranchId(ratePlaceOrder.getCountryBranchId());
					//beneficiary Name
					if(ratePlaceOrder.getBeneficiaryMasterId() != null){
						gsmPlaceOrderDtOBJ.setBeneficiaryId(ratePlaceOrder.getBeneficiaryMasterId().getBeneficaryMasterSeqId());
					}
					
					// routing country
					if(getRoutingCountry() != null){
						gsmPlaceOrderDtOBJ.setRoutingCountry(getRoutingCountry());
						routingCountry = generalService.getCountryName(getRoutingCountry());
						gsmPlaceOrderDtOBJ.setRoutingCountryName(routingCountry);
						
						/*if(getLstRoutingCountry() != null){
							gsmPlaceOrderDtOBJ.setRoutingCountryvalues(getLstRoutingCountry());
						}else{
							List<PopulateData> routingCountryList=gSMPlaceOrderRateFeedService.toFtechAllRoutingCountry(ratePlaceOrder.getDestinationCurrenyId(),session.getLanguageId(),ratePlaceOrder.getRemitType());
							gsmPlaceOrderDtOBJ.setRoutingCountryvalues(routingCountryList);
						}*/
						
						List<PopulateData> routingCountryList=gSMPlaceOrderRateFeedService.toFtechAllRoutingCountry(ratePlaceOrder.getDestinationCurrenyId(),session.getLanguageId(),ratePlaceOrder.getRemitType(),ratePlaceOrder.getBeneficiaryCountryId());
						gsmPlaceOrderDtOBJ.setRoutingCountryvalues(routingCountryList);
						
					}else {
						gsmPlaceOrderDtOBJ.setRoutingCountry(ratePlaceOrder.getRoutingCountryId());
						if(ratePlaceOrder.getRoutingCountryId() != null){
							routingCountry = generalService.getCountryName(ratePlaceOrder.getRoutingCountryId());
							gsmPlaceOrderDtOBJ.setRoutingCountryName(routingCountry);
						}
						List<PopulateData> routingCountryList=gSMPlaceOrderRateFeedService.toFtechAllRoutingCountry(ratePlaceOrder.getDestinationCurrenyId(),session.getLanguageId(),ratePlaceOrder.getRemitType(),ratePlaceOrder.getBeneficiaryCountryId());
						gsmPlaceOrderDtOBJ.setRoutingCountryvalues(routingCountryList);
					}
					
					//bank Name
					
					if(getRoutingBank() != null){
						gsmPlaceOrderDtOBJ.setRoutingBankId(getRoutingBank());
						routingBank = generalService.getBankName(getRoutingBank());
						gsmPlaceOrderDtOBJ.setRoutingBankName(routingBank);
						
						/*if(getLstRoutingBank() != null){
							gsmPlaceOrderDtOBJ.setRoutingbankvalues(getLstRoutingBank());
						}else{
							List<PopulateData> routingBankList = gSMPlaceOrderRateFeedService.toFtechAllRoutingBanksBasedOncurrency(ratePlaceOrder.getDestinationCurrenyId(),ratePlaceOrder.getRoutingCountryId(),ratePlaceOrder.getRemitType());
							gsmPlaceOrderDtOBJ.setRoutingbankvalues(routingBankList);
						}*/
						List<PopulateData> routingBankList = gSMPlaceOrderRateFeedService.toFtechAllRoutingBanksBasedOncurrency(ratePlaceOrder.getDestinationCurrenyId(),getRoutingCountry(),ratePlaceOrder.getRemitType());
						gsmPlaceOrderDtOBJ.setRoutingbankvalues(routingBankList);
						
					}else
					{
						gsmPlaceOrderDtOBJ.setRoutingBankId(ratePlaceOrder.getRoutingBankId());
						if(ratePlaceOrder.getRoutingBankId() != null){
							routingBank = generalService.getBankName(ratePlaceOrder.getRoutingBankId());
							gsmPlaceOrderDtOBJ.setRoutingBankName(routingBank);
						}
						List<PopulateData> routingBankList = gSMPlaceOrderRateFeedService.toFtechAllRoutingBanksBasedOncurrency(ratePlaceOrder.getDestinationCurrenyId(),ratePlaceOrder.getRoutingCountryId(),ratePlaceOrder.getRemitType());
						gsmPlaceOrderDtOBJ.setRoutingbankvalues(routingBankList);
					}

					//Remit Type
					gsmPlaceOrderDtOBJ.setRemittanceType(ratePlaceOrder.getRemitType());
					String remittanceType=gSMPlaceOrderRateFeedService.toFetchServiceGroupDesc(session.getLanguageId(),ratePlaceOrder.getRemitType());
					gsmPlaceOrderDtOBJ.setRemittanceName(remittanceType);
					//currency
					gsmPlaceOrderDtOBJ.setCurrencyId(ratePlaceOrder.getDestinationCurrenyId());
					gsmPlaceOrderDtOBJ.setCurrencyName(generalService.getCurrencyName(ratePlaceOrder.getDestinationCurrenyId()));
					//bene bank
					gsmPlaceOrderDtOBJ.setBeneficiaryBankId(ratePlaceOrder.getBeneficiaryBankId());
					if(ratePlaceOrder.getBeneficiaryBankId() != null){
						beneBank=generalService.getBankName(ratePlaceOrder.getBeneficiaryBankId());
						gsmPlaceOrderDtOBJ.setBeneficiaryBankName(beneBank);
					}
					/*Dispaly Data table Variables Endec*/
					//all  variables
					gsmPlaceOrderDtOBJ.setRateOfferedPk(ratePlaceOrder.getRatePlaceOrderId());
					gsmPlaceOrderDtOBJ.setRateOffered(getRateOffered());
					gsmPlaceOrderDtOBJ.setCreatedBy(ratePlaceOrder.getCreatedBy());
					gsmPlaceOrderDtOBJ.setCreatedDate(ratePlaceOrder.getCreatedDate());
					gsmPlaceOrderDtOBJ.setIsActive(ratePlaceOrder.getIsActive());
					gsmPlaceOrderDtOBJ.setModifiedBy(ratePlaceOrder.getModifiedBy());
					gsmPlaceOrderDtOBJ.setModifiedDate(ratePlaceOrder.getModifiedDate());
					gsmPlaceOrderDtOBJ.setApprovedBy(ratePlaceOrder.getApprovedBy());
					gsmPlaceOrderDtOBJ.setApprovedDate(ratePlaceOrder.getApprovedDate());
					gsmPlaceOrderDtOBJ.setTransctionConculed(ratePlaceOrder.getTransactionConcluded());
					gsmPlaceOrderDtOBJ.setAmount(ratePlaceOrder.getTransactionAmount());
					gsmPlaceOrderDtOBJ.setDocumentNumber(ratePlaceOrder.getDocumentNumber());
					gsmPlaceOrderDtOBJ.setDocumentFinanceYear(ratePlaceOrder.getDocumentFinanceYear());
					gsmPlaceOrderDtOBJ.setCustomerEmailId(ratePlaceOrder.getCustomerEmail());
					gsmPlaceOrderDtOBJ.setCountryBranchId(ratePlaceOrder.getCountryBranchId());
					
					List<CountryBranch> lstCountryBranch = generalService.getCountryBranchLocCode(ratePlaceOrder.getCountryBranchId());
					if(lstCountryBranch != null){
						CountryBranch countrybranch = lstCountryBranch.get(0);
						gsmPlaceOrderDtOBJ.setCountryBranchCode(countrybranch.getBranchId());
						gsmPlaceOrderDtOBJ.setCountryBranchName(countrybranch.getBranchName());
					}

					if(ratePlaceOrder.getCustomerIndicator() != null){
						gsmPlaceOrderDtOBJ.setSpecialOrCommonPoolIndicator(ratePlaceOrder.getCustomerIndicator());
					}/*else if(getFixRateid() != null && getFixRateid().equalsIgnoreCase(Constants.Yes)){
						gsmPlaceOrderDtOBJ.setSpecialOrCommonPoolIndicator(Constants.C);
					}else if (getFixRateid() != null && getFixRateid().equalsIgnoreCase(Constants.No)) {
						gsmPlaceOrderDtOBJ.setSpecialOrCommonPoolIndicator(null);
					}*/else{
						gsmPlaceOrderDtOBJ.setSpecialOrCommonPoolIndicator(Constants.C);
					}

					gsmPlaceOrderDtOBJ.setValueDate(ratePlaceOrder.getValueDate());
					if(ratePlaceOrder.getAccountSeqquenceId() != null){
						gsmPlaceOrderDtOBJ.setAccountSeqId(ratePlaceOrder.getAccountSeqquenceId().getBeneficaryAccountSeqId());
					}
					gsmPlaceOrderDtOBJ.setView("View");
					if(ratePlaceOrder.getNegotiateSts() != null && ratePlaceOrder.getNegotiateSts().equalsIgnoreCase(Constants.Yes)){
						gsmPlaceOrderDtOBJ.setBoonegotiate(false);
						gsmPlaceOrderDtOBJ.setRateOffered(ratePlaceOrder.getRateOffered());
					}else{
						gsmPlaceOrderDtOBJ.setBoonegotiate(true);
					}
					gsmPlaceOrderDtOBJ.setBooReadOnlyCheckBox(true);
					gsmPlaceOrderDtOBJ.setRequestCurrencyId(ratePlaceOrder.getRequestCurrencyId());

					if(ratePlaceOrder.getRequestCurrencyId() != null){
						String currencyName = generalService.getCurrencyName(ratePlaceOrder.getRequestCurrencyId());
						if(currencyName != null){
							gsmPlaceOrderDtOBJ.setRequestcurrencyName(currencyName);
						}
					}
					
					// actual Rate
					BigDecimal acctualRate = fetchActualExchangeRate(gsmPlaceOrderDtOBJ);
					gsmPlaceOrderDtOBJ.setActualRate(acctualRate);

					lstDataTables.add(gsmPlaceOrderDtOBJ);
					
					setLstGsmPlaceOrderDt(lstDataTables);

					setBooRenderDataTable(true);
					setBooRenderSaveOrExit(true);
					setBooRenderlstFiveTrnxDataTable(false);
				}
			}else{
				RequestContext.getCurrentInstance().execute("noRecords.show();");
				setBooRenderDataTable(false);
				setBooRenderSaveOrExit(false);
				setBooRenderlstFiveTrnxDataTable(false);
				clearAllFields();
				toClearDetails();
				return;
			}
		}catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("MethodName::toFetchAllRecords");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}

	/*//select checkBox
	public void SelectCheckBoxList(GSMPlaceOrderDataTable dataTableObj){
		try{
			if (dataTableObj.getSelectCheckBox()) {
				setRateOfferedPk(dataTableObj.getRateOfferedPk());
				List<PopulateData> lstofCurrency = new ArrayList<PopulateData>();

				lstofCurrency= iPlaceOnOrderCreationService.getBasedOnCountyCurrency(dataTableObj.getBeneficiaryCountryId());
				if(lstofCurrency!=null && lstofCurrency.size()>0)
				{
					List<PopulateData> routingBankList=gSMPlaceOrderRateFeedService.toFtechAllRoutingBanksBasedOncurrency(lstofCurrency.get(0).getPopulateId(),dataTableObj.getBeneficiaryCountryId());
					if(routingBankList.size()>0){
						dataTableObj.setBooReadOnlyCheckBox(false);
						dataTableObj.setRoutingbankvalues(routingBankList);
						setBooRenderlstFiveTrnxDataTable(false);

						if(getBooRoutingDeatls()){
							if(getRoutingBank() != null){
								dataTableObj.setRoutingBankId(getRoutingBank());
								dataTableObj.setRateOffered(getRateOffered());
								dataTableObj.setSpecialOrCommonPoolIndicator(Constants.C);
							}else{
								for(PopulateData populateData:routingBankList)
								{
									if(dataTableObj.getBeneficiaryBankId().compareTo(populateData.getPopulateId())==0)
									{
										dataTableObj.setRoutingBankId(dataTableObj.getBeneficiaryBankId());
									}				
								}
							}
						}else{
							for(PopulateData populateData:routingBankList)
							{
								if(dataTableObj.getBeneficiaryBankId().compareTo(populateData.getPopulateId())==0)
								{
									dataTableObj.setRoutingBankId(dataTableObj.getBeneficiaryBankId());
								}				
							}
						}


					}
				}

			}else
			{
				//dataTableObj.setRateOfferedPk(null);
				dataTableObj.setRoutingBankId(null);
				dataTableObj.setRoutingBankName(null);
				dataTableObj.setRoutingbankvalues(null);
				dataTableObj.setRateOffered(null);
				dataTableObj.setSpecialOrCommonPoolIndicator(null);
				dataTableObj.setBooReadOnlyCheckBox(true);
				setBooRenderlstFiveTrnxDataTable(false);
			}
		}catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("MethodName::toFetchAllRecords");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}*/

	//select checkBox
	public void SelectCheckBoxList(GSMPlaceOrderDataTable dataTableObj){
		try{
			
			if (dataTableObj.getSelectCheckBox()) {
				setRateOfferedPk(dataTableObj.getRateOfferedPk());

				if(getBooRoutingDeatls()){
					
					List<PopulateData> routingCountryList = gSMPlaceOrderRateFeedService.toFtechAllRoutingCountry(dataTableObj.getCurrencyId(),session.getLanguageId(),dataTableObj.getRemittanceType(),dataTableObj.getBeneficiaryCountryId());
					dataTableObj.setRoutingCountryvalues(routingCountryList);
					
					//dataTableObj.setRoutingCountryvalues(getLstRoutingCountry());
					dataTableObj.setRoutingCountry(getRoutingCountry());
					String routingCountryName = generalService.getCountryName(getRoutingCountry());
					dataTableObj.setRoutingCountryName(routingCountryName);

					dataTableObj.setRoutingBankId(getRoutingBank());
					dataTableObj.setRateOffered(getRateOffered());
					dataTableObj.setSpecialOrCommonPoolIndicator(Constants.C);
					
					// actual Rate
					BigDecimal acctualRate = fetchActualExchangeRate(dataTableObj);
					dataTableObj.setActualRate(acctualRate);
					
					List<PopulateData> routingBankList = gSMPlaceOrderRateFeedService.toFtechAllRoutingBanksBasedOncurrency(dataTableObj.getCurrencyId(),getRoutingCountry(),dataTableObj.getRemittanceType());
					dataTableObj.setRoutingbankvalues(routingBankList);
					//dataTableObj.setRoutingbankvalues(getLstRoutingBank());
					dataTableObj.setBooReadOnlyCheckBox(false);
					setBooRenderlstFiveTrnxDataTable(false);
				}else{
					
					List<PopulateData> routingCountryList = gSMPlaceOrderRateFeedService.toFtechAllRoutingCountry(dataTableObj.getCurrencyId(),session.getLanguageId(),dataTableObj.getRemittanceType(),dataTableObj.getBeneficiaryCountryId());
					
					if(dataTableObj.getRateOffered() != null){
						
						dataTableObj.setRoutingCountryvalues(routingCountryList);
						dataTableObj.setRoutingCountry(dataTableObj.getRoutingCountry());
						
						List<PopulateData> routingBankList = gSMPlaceOrderRateFeedService.toFtechAllRoutingBanksBasedOncurrency(dataTableObj.getCurrencyId(),dataTableObj.getRoutingCountry(),dataTableObj.getRemittanceType());
						if(routingBankList.size()>0){
							dataTableObj.setBooReadOnlyCheckBox(false);
							dataTableObj.setRoutingbankvalues(routingBankList);
							setBooRenderlstFiveTrnxDataTable(false);
							dataTableObj.setRoutingBankId(dataTableObj.getRoutingBankId());
							
						}
						
						// actual Rate
						BigDecimal acctualRate = fetchActualExchangeRate(dataTableObj);
						dataTableObj.setActualRate(acctualRate);

						dataTableObj.setRateOffered(dataTableObj.getRateOffered());
						dataTableObj.setSpecialOrCommonPoolIndicator(dataTableObj.getSpecialOrCommonPoolIndicator());
						
					}else{

						
						if(routingCountryList.size() == 1){
							dataTableObj.setRoutingCountryvalues(routingCountryList);
							PopulateData popRoutCountry = routingCountryList.get(0);
							dataTableObj.setRoutingCountry(popRoutCountry.getPopulateId());

							List<PopulateData> routingBankList = gSMPlaceOrderRateFeedService.toFtechAllRoutingBanksBasedOncurrency(dataTableObj.getCurrencyId(),dataTableObj.getRoutingCountry(),dataTableObj.getRemittanceType());
							if(routingBankList.size()>0){
								dataTableObj.setBooReadOnlyCheckBox(false);
								dataTableObj.setRoutingbankvalues(routingBankList);
								setBooRenderlstFiveTrnxDataTable(false);
								for(PopulateData populateData:routingBankList)
								{
									if(dataTableObj.getBeneficiaryBankId().compareTo(populateData.getPopulateId())==0)
									{
										dataTableObj.setRoutingBankId(dataTableObj.getBeneficiaryBankId());
										break;
									}				
								}
							}
							
							// actual Rate
							BigDecimal acctualRate = fetchActualExchangeRate(dataTableObj);
							dataTableObj.setActualRate(acctualRate);

							dataTableObj.setRateOffered(null);
							//dataTableObj.setSpecialOrCommonPoolIndicator(null);

						}else if(routingCountryList.size() > 1){
							dataTableObj.setRoutingCountryvalues(routingCountryList);
							dataTableObj.setRoutingCountry(null);
							dataTableObj.setBooReadOnlyCheckBox(false);
							dataTableObj.setRoutingbankvalues(null);
							setBooRenderlstFiveTrnxDataTable(false);
							dataTableObj.setRateOffered(null);
							//dataTableObj.setSpecialOrCommonPoolIndicator(null);
						}else{
							dataTableObj.setRoutingCountryvalues(routingCountryList);
							dataTableObj.setRoutingCountry(null);
							dataTableObj.setRoutingBankId(null);
							//dataTableObj.setRoutingBankName(null);
							dataTableObj.setRoutingbankvalues(null);
							dataTableObj.setRateOffered(null);
							//dataTableObj.setSpecialOrCommonPoolIndicator(null);
							dataTableObj.setBooReadOnlyCheckBox(true);
							setBooRenderlstFiveTrnxDataTable(false);
						}

					}
				}
			}else {
				//dataTableObj.setRateOfferedPk(null);
				//dataTableObj.setRoutingBankId(null);
				//dataTableObj.setRoutingBankName(null);
				//dataTableObj.setRoutingbankvalues(null);
				//dataTableObj.setRateOffered(null);
				//dataTableObj.setSpecialOrCommonPoolIndicator(null);
				dataTableObj.setBooReadOnlyCheckBox(true);
				//dataTableObj.setRoutingCountry(null);
				//dataTableObj.setRoutingCountryvalues(null);
				setBooRenderlstFiveTrnxDataTable(false);
			}
		}catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("MethodName::toFetchAllRecords");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}

	//to check min and max rate form
	public boolean checkMinMaxRate(){

		boolean minMaxrate=false;
		try{
			setRateOfferMaxRate(null);
			setRateOfferMaxRate(null);
			BigDecimal raterMinRate=null;
			BigDecimal rateMaxRate=null;

			List<CurrencyOtherInformation> lstCurrencyOtherInformation=specialRateService.getMinMaxRate(getCurrencyid());
			if(lstCurrencyOtherInformation!=null && lstCurrencyOtherInformation.size()>0)
			{
				CurrencyOtherInformation currencyOtherInformation=lstCurrencyOtherInformation.get(0);
				rateMaxRate= currencyOtherInformation.getFundMaxRate();
				raterMinRate=currencyOtherInformation.getFundMinRate();
				setRateOfferMinRate(raterMinRate);
				setRateOfferMaxRate(rateMaxRate);
				if(getRateOffered() != null && getRateOffered().compareTo(raterMinRate)>=0)
				{
					if(!(getRateOffered() != null && getRateOffered().compareTo(rateMaxRate)<=0))
					{
						minMaxrate=false;
						setRateOffered(null);
						RequestContext.getCurrentInstance().execute("minmaxcheck.show();");
						return minMaxrate;
					}
					minMaxrate=true;
				}else
				{
					minMaxrate=false;
					setRateOffered(null);
					RequestContext.getCurrentInstance().execute("minmaxcheck.show();");
					return minMaxrate;
				}
			}else{
				minMaxrate=false;
				setRateOffered(null);
				RequestContext.getCurrentInstance().execute("noSellRate.show();");
				return minMaxrate;
			}
		} catch(NullPointerException  e){
			minMaxrate=false;
			setErrorMessage("Method Name: checkMinMaxRate  "+e.getMessage());
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}catch(Exception e){
			minMaxrate=false;
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}
		return minMaxrate;

	}

	//to check min and max rate datatable
	public boolean checkMax(GSMPlaceOrderDataTable dataTable){
		boolean minMaxrate=false;
		
		try{
			
			if(dataTable.getSelectCheckBox() && dataTable.getRoutingBankId()==null){
				RequestContext.getCurrentInstance().execute("routingBank.show();");
				return minMaxrate;
			}
			
			if(!dataTable.getSelectCheckBox()){
				RequestContext.getCurrentInstance().execute("checkBox.show();");
				return minMaxrate;
			}
			
			// actual Rate
			BigDecimal acctualRate = fetchActualExchangeRate(dataTable);
			dataTable.setActualRate(acctualRate);
			
			/*if(dataTable.getActualRate() != null && dataTable.getActualRate().compareTo(BigDecimal.ZERO) != 0){
				// no need to stop
			}else{
				setErrorMessage("Exchange rate not available for this selected bank");
				RequestContext.getCurrentInstance().execute("error.show();");
				return minMaxrate;
			}*/
			
			if(dataTable.getRateOffered() != null){
				/*RequestContext.getCurrentInstance().execute("rateOffred.show();");
				return minMaxrate;*/

				setRateOfferMaxRate(null);
				setRateOfferMaxRate(null);
				BigDecimal raterMinRate=null;
				BigDecimal rateMaxRate=null;

				List<PopulateData> lstofCurrency = new ArrayList<PopulateData>();

				lstofCurrency= gSMPlaceOrderRateFeedService.getBasedOnCountyCurrency(dataTable.getBeneficiaryCountryId(),dataTable.getCurrencyId());
				if(lstofCurrency!=null && lstofCurrency.size()>0)
				{
					List<CurrencyOtherInformation> lstCurrencyOtherInformation=specialRateService.getMinMaxRate(lstofCurrency.get(0).getPopulateId());
					if(lstCurrencyOtherInformation!=null && lstCurrencyOtherInformation.size()>0)
					{
						CurrencyOtherInformation currencyOtherInformation=lstCurrencyOtherInformation.get(0);
						rateMaxRate= currencyOtherInformation.getFundMaxRate();
						raterMinRate=currencyOtherInformation.getFundMinRate();
						if(rateMaxRate==null || raterMinRate==null)
						{
							minMaxrate=false;
							setRateOffered(null);
							RequestContext.getCurrentInstance().execute("noSellRate.show();");
							return minMaxrate;
						}
						setRateOfferMinRate(raterMinRate);
						setRateOfferMaxRate(rateMaxRate);
						if(dataTable.getRateOffered() != null && dataTable.getRateOffered().compareTo(raterMinRate)>=0)
						{
							if(!(dataTable.getRateOffered() != null && dataTable.getRateOffered().compareTo(rateMaxRate)<=0))
							{
								minMaxrate=false;
								dataTable.setRateOffered(null);
								RequestContext.getCurrentInstance().execute("minmaxcheck.show();");
								return minMaxrate;
							}
							minMaxrate=true;
						}else
						{
							minMaxrate=false;
							dataTable.setRateOffered(null);
							RequestContext.getCurrentInstance().execute("minmaxcheck.show();");
							return minMaxrate;
						}
					}else{
						minMaxrate=false;
						setRateOffered(null);
						RequestContext.getCurrentInstance().execute("noSellRate.show();");
						return minMaxrate;
					}
				}else
				{
					minMaxrate=false;
					setRateOffered(null);
					RequestContext.getCurrentInstance().execute("noSellRate.show();");
					return minMaxrate;
				}
			}

		} catch(NullPointerException  e){
			minMaxrate=false;
			setErrorMessage("Method Name: updateRecords  "+e.getMessage());
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}catch(Exception e){
			minMaxrate=false;
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}
		return minMaxrate;
	}
	
	private String nullCheck(String custname) {
		return custname == null ? "" : custname;
	}

	public void saveAll(){
		try{
			String approveMsg=null;
			boolean pendingForApproval=false;

			List<GSMPlaceOrderDataTable> lstSaveGSM = new ArrayList<GSMPlaceOrderDataTable>();

			for (GSMPlaceOrderDataTable dataTableObj : lstGsmPlaceOrderDt) {
				if(dataTableObj.getSelectCheckBox()){
					if(dataTableObj.getRoutingBankId() != null && dataTableObj.getSpecialOrCommonPoolIndicator() != null && dataTableObj.getRateOffered() != null){
						pendingForApproval=gSMPlaceOrderRateFeedService.toCheckStatus(dataTableObj.getRateOfferedPk());
						if(pendingForApproval){
							RequestContext.getCurrentInstance().execute("alreadyapprov.show();");
							return;
						}

						if(!dataTableObj.getBoonegotiate())
						{
							boolean negotiateCheck= gSMPlaceOrderRateFeedService.toCheckRateOfferedForNegotiat(dataTableObj.getRateOfferedPk(), dataTableObj.getRateOffered());
							if(!negotiateCheck){
								RequestContext.getCurrentInstance().execute("negotiateCheck.show();");
								return;
							}
						}
					}
				}

			}

			lstSaveGSM.clear();
			for (GSMPlaceOrderDataTable dataTable : lstGsmPlaceOrderDt) {
				if(dataTable.getSelectCheckBox()){
					if(dataTable.getRoutingCountry() != null && dataTable.getRoutingBankId() != null && dataTable.getSpecialOrCommonPoolIndicator() != null && dataTable.getRateOffered() != null){
						//approveMsg=gSMPlaceOrderRateFeedService.saveOrUpDate(dataTable.getRateOfferedPk(),dataTable.getRoutingBankId(),dataTable.getSpecialOrCommonPoolIndicator(),dataTable.getRateOffered(),session.getUserName());
						lstSaveGSM.add(dataTable);
					}else{
						RequestContext.getCurrentInstance().execute("commonPollInd.show();");
						return;
					}
				}
			}
			
			if(lstSaveGSM != null && lstSaveGSM.size() >0){
				approveMsg = gSMPlaceOrderRateFeedService.saveOrUpDateRatePlaceOrder(lstSaveGSM);
				
				// tigger mail to customer if country branch id is 78 for online
				BigDecimal countryBranchId = null;
				List<CountryBranch> lstCountryBranch = iRemmiterOnlineRegService.getCountryBranch(new BigDecimal(Constants.BRANCH_CODE_ONLINE));
				if(lstCountryBranch != null && lstCountryBranch.size() != 0){
					CountryBranch countrybranch = lstCountryBranch.get(0);
					countryBranchId = countrybranch.getCountryBranchId();
				}
				
				for (GSMPlaceOrderDataTable gsmPlaceOrderDataTable : lstSaveGSM) {
					if(gsmPlaceOrderDataTable.getCountryBranchId() != null && countryBranchId != null && gsmPlaceOrderDataTable.getCountryBranchId().compareTo(countryBranchId)==0){
						if(gsmPlaceOrderDataTable.getCustomerEmailId() != null){
							String beneBankName=gsmPlaceOrderDataTable.getBeneficiaryBankName();
							String currencyName= gsmPlaceOrderDataTable.getRequestcurrencyName();
							//BigDecimal customerRef = gsmPlaceOrderDataTable.getCustomerReference();
							String custmerName = gsmPlaceOrderDataTable.getCustomerName();
							String custmerEmailId= gsmPlaceOrderDataTable.getCustomerEmailId();
							BigDecimal amount = gsmPlaceOrderDataTable.getAmount();
							BigDecimal exchangeRate = gsmPlaceOrderDataTable.getRateOffered();
							String beneficiaryName = null;
							List<BeneficaryMaster> lstBeneName = iPersonalRemittanceService.getBeneficaryMasterDetails(gsmPlaceOrderDataTable.getBeneficiaryId());
							if(lstBeneName != null && lstBeneName.size() != 0){
								BeneficaryMaster beneficiaryMaster = lstBeneName.get(0);
								beneficiaryName = nullCheck(beneficiaryMaster.getFirstName()).concat(" ")
										.concat(nullCheck(beneficiaryMaster.getSecondName())).concat(" ")
										.concat(nullCheck(beneficiaryMaster.getThirdName())).concat(" ")
										.concat(nullCheck(beneficiaryMaster.getFourthName())).concat(" ")
										.concat(nullCheck(beneficiaryMaster.getFifthName()));
							}

							HashMap<String, String> inputValues=new HashMap<String, String>();
							SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
							inputValues.put("EmailId", custmerEmailId);
							inputValues.put("RateOffered", "Rate Offered");
							inputValues.put("Name", custmerName);
							inputValues.put("beneName", beneficiaryName);
							inputValues.put("Bank", beneBankName);
							inputValues.put("Amount", amount +" - "+ currencyName);
							inputValues.put("exchangeRate", exchangeRate.toPlainString());
							inputValues.put("Created Date", format.format(new Date()).toString());
							List<ApplicationSetup> lstApplicationSetup = iPersonalRemittanceService.getEmailFromAppSetup(session.getCompanyId(), session.getCountryId());

							apllicationMailer1.sendMailToCustomerFromGSMConmfirmation(lstApplicationSetup, inputValues);
						}
					}
				}
				
				if(approveMsg != null && approveMsg.equalsIgnoreCase("Success")){
					RequestContext.getCurrentInstance().execute("complete.show();");
					lstGsmPlaceOrderDt.clear();
					return;
				}else{
					RequestContext.getCurrentInstance().execute("commonPollInd.show();");
					return;
				}
			}else{
				setErrorMessage(WarningHandler.showWarningMessage("lbl.pleaseSctCheckBox", session.getLanguageId()));
				RequestContext.getCurrentInstance().execute("error.show();");
			}

		}catch(NullPointerException  e){
			setErrorMessage("Method Name: saveAll  "+e.getMessage());
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}catch(Exception e){
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}
	}

	public void clickOnOKSave(){
		lstGsmPlaceOrderDt.clear();
		setBooRenderDataTable(false);
		setBooRenderSaveOrExit(false);
		setBoofixRateCurrency(false);
		setBooRenderCurrency(true);
		setBooRoutingDeatls(false);
		setBooRenderlstFiveTrnxDataTable(false);
		//clearAllFields();
		search();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../onlinespecialrate/GsmPlaceOrder.xhtml");
		} catch (Exception exception) {
			setErrorMessage(exception.getMessage());
			log.error("GSMPageNavigation  :"+ exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
		}
	}



	//view button
	public void fetchingAllRecordsFromDB(GSMPlaceOrderDataTable dataTable1){
		try{
			setGsmPlaceOrderDt(null);
			List<RatePlaceOrder> lstRatePlaceOrders=gSMPlaceOrderRateFeedService.fetchAllRecrds(dataTable1.getCustomerId(),getCountryBranchid());
			if(lstRatePlaceOrders.size()>0){
				List<GSMPlaceOrderDataTable> lstFiveTrnxRecords=new ArrayList<GSMPlaceOrderDataTable>();
				for (RatePlaceOrder ratePlaceOrder : lstRatePlaceOrders) {
					GSMPlaceOrderDataTable dataTable =new GSMPlaceOrderDataTable();
					//Customer Information
					dataTable.setViewCustomerRef(ratePlaceOrder.getCustomerreference());
					dataTable.setViewCustomerId(ratePlaceOrder.getCustomerId());
					String custoName=null;
					custoName=generalService.getCustomerNameCustomerId(ratePlaceOrder.getCustomerId());
					dataTable.setViewCustomerName(custoName);
					if(custoName !=null){
						setCustomerRefandName(ratePlaceOrder.getCustomerId().toString()+" - "+custoName);
					}
					//country BranchId
					dataTable.setViewCountryBranchId(ratePlaceOrder.getCountryBranchId());
					String cntryBranchName=null;
					cntryBranchName=stopPaymentService.toFetchCountryBranchName(session.getCountryId(),ratePlaceOrder.getCountryBranchId());
					dataTable.setViewCountryBranchName(cntryBranchName);
					//Currency
					dataTable.setViewCurrencyId(ratePlaceOrder.getDestinationCurrenyId());
					if(ratePlaceOrder.getDestinationCurrenyId() != null){
						dataTable.setViewCurrencyName(generalService.getCurrencyName(ratePlaceOrder.getDestinationCurrenyId()));
					}
					dataTable.setViewBeneCounteyId(ratePlaceOrder.getBeneficiaryCountryId());
					if(ratePlaceOrder.getBeneficiaryCountryId() != null){
						String benecountryName=null;
						benecountryName=generalService.getCountryName(session.getLanguageId(), ratePlaceOrder.getBeneficiaryCountryId());
						dataTable.setViewBeneCountryName(benecountryName);
					}
					dataTable.setViewRemitType(ratePlaceOrder.getRemitType());
					if(ratePlaceOrder.getRemitType() != null){
						String remitType=null;
						remitType=gSMPlaceOrderRateFeedService.toFetchServiceGroupDesc(session.getLanguageId(),ratePlaceOrder.getRemitType());
						dataTable.setViewRemitName(remitType);
					}
					if(ratePlaceOrder.getBeneficiaryMasterId() != null){
						dataTable.setViewBeneId(ratePlaceOrder.getBeneficiaryMasterId().getBeneficaryMasterSeqId());
						String BeneName=null;
						BeneName=gSMPlaceOrderRateFeedService.toFetchBeneficiaryName(ratePlaceOrder.getBeneficiaryMasterId().getBeneficaryMasterSeqId());
						dataTable.setViewBeneName(BeneName);
					}
					dataTable.setViewBeneBankId(ratePlaceOrder.getBeneficiaryBankId());
					if(ratePlaceOrder.getBeneficiaryBankId() != null){
						String beneBankName=null;
						beneBankName=generalService.getBankName(ratePlaceOrder.getBeneficiaryBankId());
						dataTable.setViewBeneBankName(beneBankName);
					}
					dataTable.setCreatedBy(ratePlaceOrder.getCreatedBy());
					dataTable.setRateOfferedLastTanx(ratePlaceOrder.getRateOffered());
					dataTable.setViewtrnxAmount(ratePlaceOrder.getTransactionAmount());
					dataTable.setViewCreatedDate(ratePlaceOrder.getCreatedDate());
					//dataTable.setCustomerEmailId(ratePlaceOrder.getCustomerEmail());
					if(ratePlaceOrder.getAccountSeqquenceId() != null){
						dataTable.setViewAccountSeqId(ratePlaceOrder.getAccountSeqquenceId().getBeneficaryAccountSeqId());
					}

					lstFiveTrnxRecords.add(dataTable);
				}

				setGsmPlaceOrderDt(lstFiveTrnxRecords);

			}
			setBooRenderlstFiveTrnxDataTable(true);
		}catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("MethodName::toFetchAllRecords");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}

	//Exit into Home
	public void exit(){
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

	//tp check valid customer Ref
	public void toCheckValidCustomerRef(){
		if(getCustomerReference() != null){
			List<Customer> lstCustomers=generalService.getCustomerDeatilsBasedOnRef(getCustomerReference());
			if(lstCustomers.size()>0){
				setCustomerEmailId(lstCustomers.get(0).getEmail());
			}else{
				RequestContext.getCurrentInstance().execute("customerRefNotExist.show();");
				return; 
			}
		}
	}
	public void toClearDetails(){
		setBooRenderFixRate(false);
		setBooRoutingDeatls(true);
		//setBoofixRateCurrency(true);
		setBooRenderCurrency(true);
		setBooRenderDataTable(false);
		setBooRenderSaveOrExit(false);
		setLstRoutingCountry(null);
		setLstRoutingBank(null);
		setRoutingCountry(null);
		setRoutingBank(null);
		setCurrencyid(null);
		setBooRoutingDeatls(false);
		setFixRateid(null);
		setBooRenderlstFiveTrnxDataTable(false);
		setBooRenderMultipleBank(true);
		setBooRenderBeneBank(true);
	}

	public void populateBanks()
	{

		setBeneficiaryBankid(null);
		setBeneficiaryBankName(null);
		List<PopulateData> serviceList=iPlaceOnOrderCreationService.getServiceGroupList(session.getLanguageId());
		if(serviceList!=null && serviceList.size()>0){
			setServiceGroupId(serviceList.get(0).getPopulateId());
		}
		List<PopulateData> lstBeneBanks=gSMPlaceOrderRateFeedService.getPopulateBeneficiaryBanks(session.getCountryId() , getBeneficiaryCountryId(), getServiceGroupId());

		if(lstBeneBanks!=null && lstBeneBanks.size()==0)
		{
			setBooRenderMultipleBank(true);
			setBooRenderBeneBank(true);
			RequestContext.getCurrentInstance().execute("bankNot.show();");
			return;
		}else if (lstBeneBanks!=null && lstBeneBanks.size()==1) {
			setBeneficiaryBankid(lstBeneBanks.get(0).getPopulateId());
			setBeneficiaryBankName(lstBeneBanks.get(0).getPopulateName());
			setBooRenderSingleBank(true);
			setBooRenderMultipleBank(false);
			toEnableBeneficiarfBank();
			fetchRoutingBankForBene();
		}else if (lstBeneBanks!=null && lstBeneBanks.size() > 1){
			setLstBanks(lstBeneBanks);
			setBooRenderSingleBank(false);
			setBooRenderMultipleBank(true);
			toEnableBeneficiarfBank();
		}
		
		// routing Country's
		toEnbleFixRate();

	}

	public void toEnableBeneficiarfBank()
	{
		if(getBeneficiaryCountryId()!=null)
		{
			setBooRenderBeneBank(false);
		}else
		{
			setBooRenderBeneBank(true);
		}
	}

	/* public void remitEquivalentCounterAmountForDisplay() throws AMGException{

				//setEquivalentRemitAmount(null);
				//setEquivalentCurrency(null);
				if(getAmountToRemit() != null){
					HashMap<String, String> hmoutPutValues = getRemitEquivalentAmtForSpecialRate();
					if(hmoutPutValues!=null){
						setEquivalentRemitAmount(new BigDecimal(hmoutPutValues.get("P_EQUIV_GROSS_AMOUNT")));
						if(hmoutPutValues.get("P_EQUIV_CURRENCY_ID")!=null && !hmoutPutValues.get("P_EQUIV_CURRENCY_ID").equals("0")){
							setEquivalentCurrency("["+generalService.getCurrencyQuote(new BigDecimal(hmoutPutValues.get("P_EQUIV_CURRENCY_ID")))+"]"); 
						}
					}
				}else{
					setEquivalentRemitAmount(null);
				}
			}*/

	private HashMap<String, String> getRemitEquivalentAmtForSpecialRate(BigDecimal reqCurrencuId, BigDecimal destinationCurrencyId,BigDecimal transAmount) throws AMGException
	{
		HashMap<String, String> inputValues = new HashMap<String, String>();

		inputValues.put("P_APPLICATION_COUNTRY_ID", session.getCountryId() == null ? "0" : session.getCountryId().toString());
		inputValues.put("P_ROUTING_COUNTRY_ID",  "0" );
		inputValues.put("P_BRANCH_ID", session.getBranchId());
		inputValues.put("P_COMPANY_ID", session.getCompanyId() == null ? "0" : session.getCompanyId().toString());
		inputValues.put("P_ROUTING_BANK_ID", getRoutingBank() == null ? "0" : getRoutingBank().toString());
		inputValues.put("P_SERVICE_MASTER_ID",  "0" );
		inputValues.put("P_DELIVERY_MODE_ID",  "0" );
		inputValues.put("P_REMITTANCE_MODE_ID", "0" );
		inputValues.put("P_FOREIGN_CURRENCY_ID", destinationCurrencyId == null ? "0" : destinationCurrencyId.toString());
		inputValues.put("P_SELECTED_CURRENCY_ID", reqCurrencuId == null ? "0" : reqCurrencuId.toString());
		inputValues.put("P_CUSTOMER_TYPE", "I");
		inputValues.put("P_SELECTED_CURRENCY_AMOUNT", transAmount == null ? "0": transAmount.toString());

		HashMap<String, String> outputValues = iPersonalRemittanceService.getRemitExchangeEquivalentAount(inputValues);

		return outputValues;
	}

	public void cancel()
	{
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		}catch(Exception e){
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}  
	}
	
	public void fetchBeneficiaryCountry(){
		
		setBeneficiaryCountryId(null);
		if(lstBanks != null && lstBanks.size() != 0){
			lstBanks.clear();
		}
		
		if(getCurrencyid() != null){
			List<CurrencyMaster> lstCurrencyCountry = generalService.getCurrency(getCurrencyid());
			if(lstCurrencyCountry != null && lstCurrencyCountry.size() != 0){
				CurrencyMaster currencyMaster = lstCurrencyCountry.get(0);
				if(currencyMaster.getFsCountryMaster() != null){
					setBeneficiaryCountryId(currencyMaster.getFsCountryMaster().getCountryId());
					if(getBeneficiaryCountryId() != null){
						populateBanks();
					}
				}
			}
		}
	}
	
	// actual Rate
	public BigDecimal fetchActualExchangeRate(GSMPlaceOrderDataTable dataTable) {
		
		BigDecimal serviceMasterId=null;
		HashMap<String, String> actualRateDt = null ;
		BigDecimal actualRate = null;
		
		try{
			if(dataTable.getRoutingCountry() != null && dataTable.getRoutingBankId() != null && dataTable.getCurrencyId() != null && dataTable.getCountryBranchId() != null){
				HashMap<String, Object> mapRateDt = new HashMap<String, Object>();
				mapRateDt.put("APPLICATION_COUNTRY_ID", session.getCountryId());
				mapRateDt.put("COUNTRY_ID", dataTable.getRoutingCountry());
				mapRateDt.put("CURRENCY_ID", dataTable.getCurrencyId());  // destination currency -- fc currency
				mapRateDt.put("COUNTRY_BRANCH_ID", dataTable.getCountryBranchId());
				mapRateDt.put("BANK_ID", dataTable.getRoutingBankId());

				if(dataTable.getRemittanceType() != null){
					if(dataTable.getRemittanceType().compareTo(BigDecimal.ONE) == 0){ // cash product Service Group
						// cash
						serviceMasterId = serviceMasterService.getServiceIdbyServiceName(Constants.CASH_PRODUCT);
					}else if(dataTable.getRemittanceType().compareTo(new BigDecimal(2)) == 0){ // Banking Channel product Service Group
						if(dataTable.getBeneficiaryBankId() != null && dataTable.getRoutingBankId() != null){
							if(dataTable.getBeneficiaryBankId().compareTo(dataTable.getRoutingBankId())==0){
								// eft
								serviceMasterId = serviceMasterService.getServiceIdbyServiceName(Constants.EFTNAME);
							}else{
								// tt
								serviceMasterId = serviceMasterService.getServiceIdbyServiceName(Constants.TTNAME);
							}
						}
					}
				}
				mapRateDt.put("SERVICE_MASTER_ID", serviceMasterId);
				mapRateDt.put("P_FOREIGN_CURRENCY_ID", dataTable.getCurrencyId());
				mapRateDt.put("P_SELECTED_CURRENCY_ID", dataTable.getRequestCurrencyId());
				
				
				// before getting rate checking the Routing Bank available or not
				Boolean chkRoutingSetupDt = iPersonalRemittanceService.chkRoutingSetup(mapRateDt);
				
				if(!chkRoutingSetupDt){
					dataTable.setRateOffered(null);
					setErrorMessage("Routing set up not available for this selected bank");
					RequestContext.getCurrentInstance().execute("error.show();");
					return actualRate;
				}

				List<CustomerIdProof> customerTypeString = iPersonalRemittanceService.getCustomerTypeByCustomer(dataTable.getCustomerId());
				if (customerTypeString != null && customerTypeString.size() != 0) {
					CustomerIdProof custType = customerTypeString.get(0);
					if(custType.getFsBizComponentDataByCustomerTypeId() != null){
						mapRateDt.put("P_CUSTOMER_TYPE", custType.getFsBizComponentDataByCustomerTypeId().getComponentCode());
					}
				}

				mapRateDt.put("P_SELECTED_CURRENCY_AMOUNT", dataTable.getAmount());

				actualRateDt = gSMPlaceOrderRateFeedService.fetchActualRate(mapRateDt);
				if(actualRateDt!=null){
					actualRate = new BigDecimal(actualRateDt.get("P_EXCHANGE_RATE"));
				}
			}
		}catch(Exception e){
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}
		
		return actualRate;
	}
	
	// To get Service Master list from Service Master Desc
	public void getServiceMasterList(){
		lstServiceMaster.clear();
		setServiceMasterCode(null);
		setServiceMasterId(null);
		setServiceMasterName(null);
		try{
			List<ServiceMasterDesc> serviceMaster = iRemitModeMaster.getServiceMastersActivateList(session.getLanguageId());

			if(serviceMaster.size()!=0){
				for(ServiceMasterDesc serviceMasterlst : serviceMaster){
					if(serviceMasterlst.getServiceMaster().getIsActive() != null && serviceMasterlst.getServiceMaster().getIsActive().equalsIgnoreCase(Constants.Yes)){
						if(serviceMasterlst.getLocalServiceDescription() != null && (serviceMasterlst.getLocalServiceDescription().equalsIgnoreCase(Constants.EFTNAME) 
								|| serviceMasterlst.getLocalServiceDescription().equalsIgnoreCase(Constants.TTNAME) || serviceMasterlst.getLocalServiceDescription().equalsIgnoreCase(Constants.CASHNAME))){
							lstServiceMaster.add(serviceMasterlst);
						}
					}
				}
				
				if(lstServiceMaster != null && lstServiceMaster.size() != 0){
					
					ServiceMasterDesc serviceMasterId = lstServiceMaster.get(0);
					
					setServiceMasterCode(serviceMasterId.getServiceMaster().getServiceCode());
					setServiceMasterId(serviceMasterId.getServiceMaster().getServiceId());
					setServiceMasterName(serviceMasterId.getLocalServiceDescription());
					
					chkBeneBankDt();
				}
			}
		} catch (Exception e) {
			log.info("lstServiceMaster time Error:  "+e);
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}
	}
	
	// bene bank mandatory or not
	public void chkBeneBankDt(){
		// not Mandatory Bene Bank
		setBeneBankMandatory(false);
		if(getFixRateid() != null && getFixRateid().equalsIgnoreCase(Constants.Yes) && getServiceMasterId() != null){
			String serviceName = null;
			if(lstServiceMaster != null && lstServiceMaster.size() != 0){
				for (ServiceMasterDesc service : lstServiceMaster) {
					if(service.getServiceMaster().getServiceId().compareTo(getServiceMasterId()) == 0){
						serviceName = service.getLocalServiceDescription();
						break;
					}
				}
				
				if(serviceName != null && serviceName.equalsIgnoreCase(Constants.EFTNAME)){
					// bene  bank is mandatory
					setBeneBankMandatory(true);
				}
			}
		}
		
		fetchBeneficiaryCountry();
	}
	
	// once Bene bank selected 
	public void fetchRoutingBankForBene(){
		setRoutingBank(null);
		setRoutingBankName(null);
		if(getRoutingCountry() != null && getLstRoutingBank() != null && getLstRoutingBank().size() != 0){
			for (PopulateData routingBank : getLstRoutingBank()) {
				if(getBeneficiaryBankid() != null && getBeneficiaryBankid().compareTo(routingBank.getPopulateId())==0){
					setRoutingBank(routingBank.getPopulateId());
					setRoutingBankName(routingBank.getPopulateName());
					break;
				}
			}
			
			/*if(getRoutingBank() != null){
				// EFT Product
			}else{
				// no routing available TT product
			}*/
		}
	}
}
