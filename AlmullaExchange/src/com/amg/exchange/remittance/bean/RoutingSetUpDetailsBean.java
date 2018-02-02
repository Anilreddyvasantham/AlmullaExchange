package com.amg.exchange.remittance.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.beneficiary.model.BankBranchView;
import com.amg.exchange.beneficiary.service.IBeneficaryCreation;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.remittance.model.RoutingAgent;
import com.amg.exchange.remittance.model.RoutingDetails;
import com.amg.exchange.remittance.model.RoutingHeader;
import com.amg.exchange.remittance.service.IBankServiceRuleMasterService;
import com.amg.exchange.remittance.service.IRoutingSetUpDetailsService;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.BankIndicator;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.BeneCountryService;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.DeliveryMode;
import com.amg.exchange.treasury.model.DeliveryModeDesc;
import com.amg.exchange.treasury.model.RemittanceModeDescription;
import com.amg.exchange.treasury.model.RemittanceModeMaster;
import com.amg.exchange.treasury.model.ServiceMaster;
import com.amg.exchange.treasury.model.ServiceMasterDesc;
import com.amg.exchange.treasury.service.IBankApplicabilityService;
import com.amg.exchange.treasury.service.IBankIndicatorService;
import com.amg.exchange.treasury.service.IFXDetailInformationService;
import com.amg.exchange.treasury.service.IPipsMasterService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("routingSetUpDetailsBean")
@Scope("session")
public class RoutingSetUpDetailsBean<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	Logger log= Logger.getLogger(RoutingSetUpDetailsBean.class);

	// Variables Declaration

	Boolean selectAll = true;


	private BigDecimal routingSetUpHeaderId = null;
	private BigDecimal routingSetUpDetailsId = null;
	private BigDecimal countryId = null;
	private BigDecimal routingCountryId = null;
	private BigDecimal routingbankId = null;
	private BigDecimal routingcurrencyId = null;
	private BigDecimal routingServiceId = null;
	private BigDecimal routingRemittanceId = null;
	private BigDecimal routingDeliveryId = null;
	private String branchApplicability = null;
	private String routingStatus;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private boolean statusDelete=false;
	private BigDecimal countryIdForDailog;
	private BigDecimal routingcurrencyIdForDailog;
	private BigDecimal routingServiceIdForDailog;

	private Boolean booRenderDataTable = false;
	//private Boolean booRenderSelect = true;
	private Boolean booRenderAdd = false;
	boolean delete;
	boolean selectedcheck = true;
	//	private int selectcount = 0;
	private int i = 0;
	private BigDecimal routingagent = null;
	private Boolean booRenderAgentCol = false;
	private Boolean disableEditButton = false;

	private String serviceName;
	private String deliveryName;
	private String remittanceName;
	private Boolean booRenderApprovalDataTable=false;
	private Boolean booRenderDataTableApprovalExit=false;
	private Boolean booRenderview=false;
	private String dynamicLabelForActivateDeactivate;

	private boolean booNotAgentPanel;
	private boolean booAgentPanel;
	private boolean booRenderCash;
	private List<BankBranchView> lstTempBankBranchView;
	private boolean booAddmorefield;

	//SessionStateManage Declaration
	private SessionStateManage sessionManage = new SessionStateManage();
	private List<CountryMasterDesc> bankCountryList;

	//List Declaration
	private List<RoutingSetUpDetailsDataTable> routingSetUpDetailsTable = new CopyOnWriteArrayList<RoutingSetUpDetailsDataTable>();
	private List<RoutingSetUpDetailsDataTable> routingSetUpNewDetailsTable = new CopyOnWriteArrayList<RoutingSetUpDetailsDataTable>();
	private List<RoutingSetUpDetailsDialogCheckBox> bankbranchlist=new CopyOnWriteArrayList<RoutingSetUpDetailsDialogCheckBox>();
	private List<RoutingSetUpDetailsDialogCheckBox> dialogselectbankbranch=new ArrayList<RoutingSetUpDetailsDialogCheckBox>();
	private List<RoutingSetUpDetailsDialogCheckBox> addallvalues=new ArrayList<RoutingSetUpDetailsDialogCheckBox>();
	private List<RoutingSetUpDetailsDialogCheckBox> popupinDataTable=new ArrayList<RoutingSetUpDetailsDialogCheckBox>();
	private List<RoutingHeader> routingheaderData = new ArrayList<RoutingHeader>();
	private List<RoutingDetails> routingDetailsList = new ArrayList<RoutingDetails>();
	private List<RoutingDetails> allroutingDetails = new ArrayList<RoutingDetails>();
	private List<RemittanceModeDescription> servicedatafromdb = new ArrayList<RemittanceModeDescription>();
	private List<DeliveryModeDesc> deliverydataBasedonService = new ArrayList<DeliveryModeDesc>();
	private List<RoutingSetupBankDetails> bankAppList = new ArrayList<RoutingSetupBankDetails>();
	private List<BankApplicability> agentdetailsList = new ArrayList<BankApplicability>();
	private List<ServiceMasterDesc> lstofservice;
	private List<RoutingSetUpDetailsDialogCheckBox> routingAgentList=new CopyOnWriteArrayList<RoutingSetUpDetailsDialogCheckBox>();
	private List<RoutingSetUpDetailsDialogCheckBox> NewRoutingAgentList=new CopyOnWriteArrayList<RoutingSetUpDetailsDialogCheckBox>();

	//Added by Rabil on 15/12/2015.

	List<RoutingAgentViewDataTable> routingAgentDataTableList = new ArrayList<RoutingAgentViewDataTable>();


	private List<RoutingSetUpDetailsDataTable> routingSetUpDetailsTableForCash = null;
	//Get Set Methods for List 
	private List<BeneCountryService> currencyListFromDB;




	public List<RoutingSetUpDetailsDataTable> getRoutingSetUpDetailsTableForCash() {
		return routingSetUpDetailsTableForCash;
	}

	public void setRoutingSetUpDetailsTableForCash(
			List<RoutingSetUpDetailsDataTable> routingSetUpDetailsTableForCash) {
		this.routingSetUpDetailsTableForCash = routingSetUpDetailsTableForCash;
	}

	public boolean isBooAddmorefield() {
		return booAddmorefield;
	}

	public void setBooAddmorefield(boolean booAddmorefield) {
		this.booAddmorefield = booAddmorefield;
	}

	public List<BankBranchView> getLstTempBankBranchView() {
		return lstTempBankBranchView;
	}

	public void setLstTempBankBranchView(List<BankBranchView> lstTempBankBranchView) {
		this.lstTempBankBranchView = lstTempBankBranchView;
	}

	public boolean isBooRenderCash() {
		return booRenderCash;
	}

	public void setBooRenderCash(boolean booRenderCash) {
		this.booRenderCash = booRenderCash;
	}

	public boolean isBooNotAgentPanel() {
		return booNotAgentPanel;
	}

	public void setBooNotAgentPanel(boolean booNotAgentPanel) {
		this.booNotAgentPanel = booNotAgentPanel;
	}

	public boolean isBooAgentPanel() {
		return booAgentPanel;
	}

	public void setBooAgentPanel(boolean booAgentPanel) {
		this.booAgentPanel = booAgentPanel;
	}

	public List<ServiceMasterDesc> getLstofservice() {
		return lstofservice;
	}

	public BigDecimal getRoutingcurrencyIdForDailog() {
		return routingcurrencyIdForDailog;
	}



	public List<RoutingSetUpDetailsDialogCheckBox> getRoutingAgentList() {
		return routingAgentList;
	}

	public void setRoutingAgentList(
			List<RoutingSetUpDetailsDialogCheckBox> routingAgentList) {
		this.routingAgentList = routingAgentList;
	}

	public Boolean getDisableEditButton() {
		return disableEditButton;
	}

	public void setDisableEditButton(Boolean disableEditButton) {
		this.disableEditButton = disableEditButton;
	}

	public BigDecimal getRoutingServiceIdForDailog() {
		return routingServiceIdForDailog;
	}

	public List<RoutingSetUpDetailsDataTable> getRoutingSetUpNewDetailsTable() {
		return routingSetUpNewDetailsTable;
	}

	public void setRoutingcurrencyIdForDailog(BigDecimal routingcurrencyIdForDailog) {
		this.routingcurrencyIdForDailog = routingcurrencyIdForDailog;
	}

	public void setRoutingServiceIdForDailog(BigDecimal routingServiceIdForDailog) {
		this.routingServiceIdForDailog = routingServiceIdForDailog;
	}

	public void setRoutingSetUpNewDetailsTable(
			List<RoutingSetUpDetailsDataTable> routingSetUpNewDetailsTable) {
		this.routingSetUpNewDetailsTable = routingSetUpNewDetailsTable;
	}

	public void setLstofservice(List<ServiceMasterDesc> lstofservice) {
		this.lstofservice = lstofservice;
	}

	public List<RoutingSetUpDetailsDataTable> getRoutingSetUpDetailsTable() {
		return routingSetUpDetailsTable;
	}

	public void setRoutingSetUpDetailsTable(
			List<RoutingSetUpDetailsDataTable> routingSetUpDetailsTable) {
		this.routingSetUpDetailsTable = routingSetUpDetailsTable;
	}

	public List<RoutingSetUpDetailsDialogCheckBox> getBankbranchlist() {
		return bankbranchlist;
	}

	public void setBankbranchlist(
			List<RoutingSetUpDetailsDialogCheckBox> bankbranchlist) {
		this.bankbranchlist = bankbranchlist;
	}

	public List<RoutingSetUpDetailsDialogCheckBox> getDialogselectbankbranch() {
		return dialogselectbankbranch;
	}

	public void setDialogselectbankbranch(
			List<RoutingSetUpDetailsDialogCheckBox> dialogselectbankbranch) {
		this.dialogselectbankbranch = dialogselectbankbranch;
	}

	public List<RoutingHeader> getRoutingheaderData() {
		return routingheaderData;
	}

	public void setRoutingheaderData(List<RoutingHeader> routingheaderData) {
		this.routingheaderData = routingheaderData;
	}

	public List<RoutingDetails> getRoutingDetailsList() {
		return routingDetailsList;
	}

	public void setRoutingDetailsList(List<RoutingDetails> routingDetailsList) {
		this.routingDetailsList = routingDetailsList;
	}

	public List<RoutingSetUpDetailsDialogCheckBox> getPopupinDataTable() {
		return popupinDataTable;
	}

	public void setPopupinDataTable(
			List<RoutingSetUpDetailsDialogCheckBox> popupinDataTable) {
		this.popupinDataTable = popupinDataTable;
	}

	public List<RemittanceModeDescription> getServicedatafromdb() {
		return servicedatafromdb;
	}

	public void setServicedatafromdb(List<RemittanceModeDescription> servicedatafromdb) {
		this.servicedatafromdb = servicedatafromdb;
	}

	public List<DeliveryModeDesc> getDeliverydataBasedonService() {
		return deliverydataBasedonService;
	}

	public void setDeliverydataBasedonService(
			List<DeliveryModeDesc> deliverydataBasedonService) {
		this.deliverydataBasedonService = deliverydataBasedonService;
	}

	public List<RoutingSetUpDetailsDialogCheckBox> getAddallvalues() {
		return addallvalues;
	}

	public void setAddallvalues(List<RoutingSetUpDetailsDialogCheckBox> addallvalues) {
		this.addallvalues = addallvalues;
	}

	public List<RoutingSetupBankDetails> getBankAppList() {
		return bankAppList;
	}

	public void setBankAppList(List<RoutingSetupBankDetails> bankAppList) {
		this.bankAppList = bankAppList;
	}

	public List<BankApplicability> getAgentdetailsList() {
		return agentdetailsList;
	}

	public void setAgentdetailsList(List<BankApplicability> agentdetailsList) {
		this.agentdetailsList = agentdetailsList;
	}

	public Boolean getBooRenderAgentCol() {
		return booRenderAgentCol;
	}

	public void setBooRenderAgentCol(Boolean booRenderAgentCol) {
		this.booRenderAgentCol = booRenderAgentCol;
	}

	public List<RoutingDetails> getAllroutingDetails() {
		return allroutingDetails;
	}

	public void setAllroutingDetails(List<RoutingDetails> allroutingDetails) {
		this.allroutingDetails = allroutingDetails;
	}




	public Boolean getBooRenderApprovalDataTable() {
		return booRenderApprovalDataTable;
	}

	public void setBooRenderApprovalDataTable(Boolean booRenderApprovalDataTable) {
		this.booRenderApprovalDataTable = booRenderApprovalDataTable;
	}

	public Boolean getBooRenderDataTableApprovalExit() {
		return booRenderDataTableApprovalExit;
	}

	public void setBooRenderDataTableApprovalExit(
			Boolean booRenderDataTableApprovalExit) {
		this.booRenderDataTableApprovalExit = booRenderDataTableApprovalExit;
	}

	public Boolean getBooRenderview() {
		return booRenderview;
	}

	public void setBooRenderview(Boolean booRenderview) {
		this.booRenderview = booRenderview;
	}


	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}

	public void setDynamicLabelForActivateDeactivate(String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}




	//Services Called
	@Autowired
	IGeneralService<T> generalService;

	@Autowired
	IRoutingSetUpDetailsService<T> iroutingSetUpDetailsService;

	@Autowired
	IFXDetailInformationService<T> fXDetailInformationService;

	@Autowired
	IPipsMasterService pipsMasterService;

	@Autowired
	IBankIndicatorService bankIndicatorService;

	@Autowired
	IBeneficaryCreation iBeneficaryCreation;

	@Autowired
	IBankApplicabilityService<T> bankApplicabilityService;

	@Autowired
	IBankServiceRuleMasterService ibankServiceRuleMasterService;

	//Get Set Methods for All Variables
	public Boolean getBooRenderDataTable() {
		return booRenderDataTable;
	}

	public void setBooRenderDataTable(Boolean booRenderDataTable) {
		this.booRenderDataTable = booRenderDataTable;
	}

	public BigDecimal getRoutingSetUpHeaderId() {
		return routingSetUpHeaderId;
	}
	public void setRoutingSetUpHeaderId(BigDecimal routingSetUpHeaderId) {
		this.routingSetUpHeaderId = routingSetUpHeaderId;
	}
	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}

	public BigDecimal getCountryId() {
		return countryId;
	}

	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}

	public BigDecimal getRoutingCountryId() {
		return routingCountryId;
	}

	public void setRoutingCountryId(BigDecimal routingCountryId) {
		this.routingCountryId = routingCountryId;
	}

	public BigDecimal getRoutingbankId() {
		return routingbankId;
	}

	public void setRoutingbankId(BigDecimal routingbankId) {
		this.routingbankId = routingbankId;
	}

	public BigDecimal getRoutingcurrencyId() {
		return routingcurrencyId;
	}

	public void setRoutingcurrencyId(BigDecimal routingcurrencyId) {
		this.routingcurrencyId = routingcurrencyId;
	}

	public BigDecimal getRoutingServiceId() {
		return routingServiceId;
	}

	public void setRoutingServiceId(BigDecimal routingServiceId) {
		this.routingServiceId = routingServiceId;
	}

	public BigDecimal getRoutingRemittanceId() {
		return routingRemittanceId;
	}

	public void setRoutingRemittanceId(BigDecimal routingRemittanceId) {
		this.routingRemittanceId = routingRemittanceId;
	}

	public BigDecimal getRoutingDeliveryId() {
		return routingDeliveryId;
	}

	public void setRoutingDeliveryId(BigDecimal routingDeliveryId) {
		this.routingDeliveryId = routingDeliveryId;
	}

	public String getBranchApplicability() {
		return branchApplicability;
	}

	public void setBranchApplicability(String branchApplicability) {
		this.branchApplicability = branchApplicability;
	}	

	public String getRoutingStatus() {
		return routingStatus;
	}

	public void setRoutingStatus(String routingStatus) {
		this.routingStatus = routingStatus;
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

	public BigDecimal getRoutingSetUpDetailsId() {
		return routingSetUpDetailsId;
	}

	public void setRoutingSetUpDetailsId(BigDecimal routingSetUpDetailsId) {
		this.routingSetUpDetailsId = routingSetUpDetailsId;
	}

	public boolean isSelectedcheck() {
		return selectedcheck;
	}

	public void setSelectedcheck(boolean selectedcheck) {
		this.selectedcheck = selectedcheck;
	}

	/*	public Boolean getBooRenderSelect() {
		return booRenderSelect;
	}

	public void setBooRenderSelect(Boolean booRenderSelect) {
		this.booRenderSelect = booRenderSelect;
	}*/

	public Boolean getBooRenderAdd() {
		return booRenderAdd;
	}

	public void setBooRenderAdd(Boolean booRenderAdd) {
		this.booRenderAdd = booRenderAdd;
	}	

	/*	public int getSelectcount() {
		return selectcount;
	}

	public void setSelectcount(int selectcount) {
		this.selectcount = selectcount;
	}*/

	public BigDecimal getRoutingagent() {
		return routingagent;
	}

	public void setRoutingagent(BigDecimal routingagent) {
		this.routingagent = routingagent;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getDeliveryName() {
		return deliveryName;
	}

	public void setDeliveryName(String deliveryName) {
		this.deliveryName = deliveryName;
	}

	public String getRemittanceName() {
		return remittanceName;
	}

	public void setRemittanceName(String remittanceName) {
		this.remittanceName = remittanceName;
	}

	public boolean isStatusDelete() {
		return statusDelete;
	}

	public void setStatusDelete(boolean statusDelete) {
		this.statusDelete = statusDelete;
	}

	//to get All Bank Country
	public List<CountryMasterDesc> getBankCountryList() {
		return  bankCountryList; 
	}

	public void setBankCountryList(List<CountryMasterDesc> bankCountryList) {
		this.bankCountryList = bankCountryList;
	}

	//to get All Banks from BankApplicability acc to Corresponding and Service Provider Indicator
	public void getBankAccordingToBankIndicator() {
		try{
			BigDecimal pkCorresBankInd = null;
			BigDecimal pkServProBankInd = null;

			List<BankIndicator> corrBankIndlist = bankIndicatorService.getRecordFromDB(Constants.BANK_INDICATOR_CORR_BANK);

			if(corrBankIndlist.size() != 0){
				pkCorresBankInd = corrBankIndlist.get(0).getBankIndicatorId();
			}

			List<BankIndicator> serviceBankIndlist = bankIndicatorService.getRecordFromDB(Constants.BANK_INDICATOR_SERVICEPRO_BANK);

			if(serviceBankIndlist.size() != 0){
				pkServProBankInd = serviceBankIndlist.get(0).getBankIndicatorId();
			}

			if(getRoutingCountryId()!=null && pkCorresBankInd != null && pkServProBankInd!=null){
				bankAppList = iroutingSetUpDetailsService.getBankListbyIndicators(getRoutingCountryId(),pkCorresBankInd,pkServProBankInd);
			}else{
				System.out.println("Please Check ID");
			}
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}

	}

	/*//to get agent indicator names from agent master
	public List<AgentMaster> getAgentDetailsBasedonServiceProvider() {
		return iroutingSetUpDetailsService.getAgentdetails(sessionManage.getLanguageId());
	}*/

	//to get All Currency From CurrencyMaster

	public void listAllCurrencyBasedonCountry(BigDecimal currencyId){
		try{
			List<BeneCountryService> curencyList = pipsMasterService.getCurrencyMaster(currencyId);
			setCurrencyListFromDB(curencyList);
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}
	//to get All Service Code From ServiceMaster
	public List<BeneCountryService> getCurrencyListFromDB() {
		return currencyListFromDB;
	}

	public void setCurrencyListFromDB(List<BeneCountryService> currencyListFromDB) {
		this.currencyListFromDB = currencyListFromDB;
	}

	public void getServiceCodeFromDB() {
		try{
			List<ServiceMasterDesc> lstofservice = iroutingSetUpDetailsService.getServiceMaster(sessionManage.getLanguageId());

			setLstofservice(lstofservice);
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
		}
	}

	/*//to get All Remittance Mode From RemittanceModeMaster
	public List<RemittanceModeMaster> getRemittanceModeFromDB() {
		return iroutingSetUpDetailsService.getRemittanceModeMaster();
	}*/

	/*//to get All Delivery Mode From DeliveryMode
	public List<DeliveryMode> getDeliveryModeFromDB() {
		return iroutingSetUpDetailsService.getDeliveryMode();
	}*/

	//Checking Fields in Data Base and Data Table while Adding
	public void getAllDetailsToList() {
		try{
			setBooAddmorefield(false);
			if(getServiceName().equalsIgnoreCase(Constants.CASHNAME))
			{
				setBooAgentPanel(true);
				setBooNotAgentPanel(false);
				setRoutingSetUpDetailsTableForCash(null);

				showAgentDeatils(getRoutingbankId());

				if(routingAgentList.size()==0)
				{

					setBooRenderDataTable(Boolean.TRUE);
					setBooAddmorefield(true);

					List<RoutingSetUpDetailsDataTable> routingSetUpDetailsTableForCash = new ArrayList<RoutingSetUpDetailsDataTable>();
					RoutingSetUpDetailsDataTable routingSetUpDetailsDataTable = new RoutingSetUpDetailsDataTable();	
					routingSetUpDetailsDataTable.setCountryId(getCountryId());
					routingSetUpDetailsDataTable.setRoutingcurrencyId(getRoutingcurrencyId());
					routingSetUpDetailsDataTable.setRoutingServiceId(getRoutingServiceId());
					routingSetUpDetailsDataTable.setRoutingDeliveryId(getRoutingDeliveryId());
					routingSetUpDetailsDataTable.setRoutingRemittanceId(getRoutingRemittanceId());
					routingSetUpDetailsDataTable.setRoutingcountryId(getRoutingCountryId());
					routingSetUpDetailsDataTable.setRoutingbankId(getRoutingbankId());

					routingSetUpDetailsTableForCash.add(routingSetUpDetailsDataTable);

					setRoutingSetUpDetailsTableForCash(routingSetUpDetailsTableForCash);

					List<PopulateDataWithCode> lstAgentsMasterBanks = new ArrayList<PopulateDataWithCode>();
					List<PopulateDataWithCode> lstofAllCorrAgentsBeneServicePBanks = iroutingSetUpDetailsService.FetchAllBankListbyBankCountry(getRoutingCountryId());
					if(lstofAllCorrAgentsBeneServicePBanks != null && !lstofAllCorrAgentsBeneServicePBanks.isEmpty()){
						lstAgentsMasterBanks.addAll(lstofAllCorrAgentsBeneServicePBanks);
					}

					List<BankBranchView> lstBankBranchView= iBeneficaryCreation.getBranchListfromViewByCountryBank(getRoutingCountryId(),getRoutingbankId());
					RoutingSetUpDetailsDialogCheckBox routingAgentObj = new RoutingSetUpDetailsDialogCheckBox();
					routingAgentObj.setLstRoutingSetupBankDetails(lstAgentsMasterBanks);
					routingAgentObj.setLstBankBranchView(lstBankBranchView);
					routingAgentObj.setIsActive(Constants.U);
					routingAgentObj.setDynamicLabelActivateDeActivate(Constants.REMOVE);
					routingAgentObj.setDataTableStatus(Constants.REMOVE);
					routingAgentList.add(routingAgentObj);

				}
			}else
			{
				setBooNotAgentPanel(true);
				setBooAgentPanel(false);
				setBooAddmorefield(false);

				List<RoutingHeader> routingHeaderList =iroutingSetUpDetailsService.getAllRecordsIfExist(getCountryId(), getRoutingcurrencyId(), getRoutingServiceId(), getRoutingRemittanceId(), getRoutingDeliveryId(), getRoutingCountryId(), getRoutingbankId());
				if(routingHeaderList.size()>0&&isEditClick()){
					RequestContext.getCurrentInstance().execute("recordExist.show();");
					return;
				}else{
					if(getRoutingSetUpDetailsTable().size()!=0){
						for (RoutingSetUpDetailsDataTable routingheaddata : routingSetUpDetailsTable) {
							i = 0 ;
							if((routingheaddata.getRoutingcountryId().compareTo(getRoutingCountryId())!=0)){
								i=1;
							}else{
								if((routingheaddata.getRoutingbankId().compareTo(getRoutingbankId())!=0)){
									i=1;
								}else{					

									if(routingheaddata.getBranchApplicability().equals(Constants.SPECIFIC)){
										RequestContext.getCurrentInstance().execute("detailsexists.show();");
										break;
									}else{
										RequestContext.getCurrentInstance().execute("detailsexists.show();");
										break;
									}
								}
							}
						}
						if(i==1){
							if(addallvalues.size()!=0){
								//dialogselectbankbranch.clear();
								for(RoutingSetUpDetailsDialogCheckBox routcheck :addallvalues){
									if(routcheck.getSelectRecord()){
										//duplicate check need to be done
										boolean dupBranchcheck = false;
										for (RoutingSetUpDetailsDialogCheckBox dupCheck : dialogselectbankbranch) {
											if(dupCheck.getBankbranchCode().compareTo(routcheck.getBankbranchCode())==0){
												// no need to add
												dupBranchcheck = true;
												break;
											}else{
												dupBranchcheck = false;
											}
										}
										
										if(!dupBranchcheck){
											dialogselectbankbranch.add(routcheck);
										}
									}
								}
							}
							if(bankbranchlist.size()!=0){
								//dialogselectbankbranch.clear();
								for(RoutingSetUpDetailsDialogCheckBox routcheck :bankbranchlist){
									if(routcheck.getSelectRecord()){
										//duplicate check need to be done
										boolean dupBranchcheck = false;
										for (RoutingSetUpDetailsDialogCheckBox dupCheck : dialogselectbankbranch) {
											if(dupCheck.getBankId().compareTo(routcheck.getBankId())==0){
												if(dupCheck.getBankbranchCode().compareTo(routcheck.getBankbranchCode())==0){
													// no need to add
													dupBranchcheck = true;
													break;
												}else{
													dupBranchcheck = false;
												}
											}else{
												dupBranchcheck = false;
											}
										}
										
										if(!dupBranchcheck){
											dialogselectbankbranch.add(routcheck);
										}
									}
								}
							}

							if((addallvalues != null && addallvalues.size() != 0) || (bankbranchlist != null && bankbranchlist.size() != 0)){
								addtoListAllFields();
							}else{
								RequestContext.getCurrentInstance().execute("nobranch.show();");
							}

						}

					}else {
						if(addallvalues.size()!=0){
							//dialogselectbankbranch.clear();
							for(RoutingSetUpDetailsDialogCheckBox routcheck :addallvalues){
								if(routcheck.getSelectRecord()){
									//duplicate check need to be done
									boolean dupBranchcheck = false;
									for (RoutingSetUpDetailsDialogCheckBox dupCheck : dialogselectbankbranch) {
										if(dupCheck.getBankbranchCode().compareTo(routcheck.getBankbranchCode())==0){
											// no need to add
											dupBranchcheck = true;
											break;
										}else{
											dupBranchcheck = false;
										}
									}
									
									if(!dupBranchcheck){
										dialogselectbankbranch.add(routcheck);
									}
								}
							}
						}
						if(bankbranchlist.size()!=0){
							//dialogselectbankbranch.clear();
							for(RoutingSetUpDetailsDialogCheckBox routcheck :bankbranchlist){
								if(routcheck.getSelectRecord()){
									//duplicate check need to be done
									boolean dupBranchcheck = false;
									for (RoutingSetUpDetailsDialogCheckBox dupCheck : dialogselectbankbranch) {
										if(dupCheck.getBankbranchCode().compareTo(routcheck.getBankbranchCode())==0){
											// no need to add
											dupBranchcheck = true;
											break;
										}else{
											dupBranchcheck = false;
										}
									}
									
									if(!dupBranchcheck){
										dialogselectbankbranch.add(routcheck);
									}
								}
							}
						}

						if((addallvalues != null && addallvalues.size() != 0) || (bankbranchlist != null && bankbranchlist.size() != 0)){
							addtoListAllFields();
						}else{
							RequestContext.getCurrentInstance().execute("nobranch.show();");
						}
					}
				}

				if(routingSetUpDetailsTable.size()>0 || routingSetUpNewDetailsTable.size()>0)
				{
					setBooRenderDataTable(Boolean.TRUE);
				}else
				{
					setBooRenderDataTable(Boolean.FALSE);
				}
			}
		}catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::getAllDetailsToList"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}

	}

	//Adding values to List for Data Table - RoutingSetUpDetailsTable
	public void addtoListAllFields(){
		try{
			if(dialogselectbankbranch.size()!=0){
				setBooRenderDataTable(true);

				RoutingSetUpDetailsDataTable routingSetUpDetails= new RoutingSetUpDetailsDataTable();

				routingSetUpDetails.setRoutingSetUpHeaderId(getPkRoutingHeaderId());
				routingSetUpDetails.setCountryId(getCountryId());
				routingSetUpDetails.setCountryName(generalService.getCountryName(sessionManage.getLanguageId(), getCountryId()));
				routingSetUpDetails.setRoutingcurrencyId(getRoutingcurrencyId());
				routingSetUpDetails.setRoutingcurrencyName(generalService.getCurrencyName(getRoutingcurrencyId()));
				routingSetUpDetails.setRoutingServiceId(getRoutingServiceId());

				List<ServiceMasterDesc> serviceList =	iroutingSetUpDetailsService.getServiceCode(sessionManage.getLanguageId(),getRoutingServiceId());
				if(serviceList!=null && serviceList.size()>0){
					routingSetUpDetails.setRoutingServiceCode(serviceList.get(0).getLocalServiceDescription());
				}
				//	routingSetUpDetails.setRoutingServiceCode(getServiceName());


				if(getRoutingDeliveryId()!=null){
					routingSetUpDetails.setRoutingDeliveryId(getRoutingDeliveryId());
					//	routingSetUpDetails.setRoutingDeliveryMode(getDeliveryName());
					List<DeliveryModeDesc> deleveryList = iroutingSetUpDetailsService.getDeliveryModeDetails(sessionManage.getLanguageId(),getRoutingDeliveryId());
					if(deleveryList!=null && deleveryList.size()>0){
						routingSetUpDetails.setRoutingDeliveryMode(deleveryList.get(0).getEnglishDeliveryName());
					}
				}


				if(getRoutingRemittanceId()!=null){
					routingSetUpDetails.setRoutingRemittanceId(getRoutingRemittanceId());
					//	routingSetUpDetails.setRoutingRemittanceMode(getRemittanceName());
					List<RemittanceModeDescription> remittanceList = iroutingSetUpDetailsService.getRemittanceMode(sessionManage.getLanguageId(),getRoutingRemittanceId());
					if(remittanceList!=null && remittanceList.size()>0){
						routingSetUpDetails.setRoutingRemittanceMode(remittanceList.get(0).getRemittanceDescription());
					}
				}
				routingSetUpDetails.setRoutingcountryId(getRoutingCountryId());
				routingSetUpDetails.setRoutingcountryName(generalService.getCountryName(sessionManage.getLanguageId(), getRoutingCountryId()));
				routingSetUpDetails.setRoutingbankId(getRoutingbankId());
				routingSetUpDetails.setRoutingbankName(generalService.getBankName(getRoutingbankId()).toString());
				routingSetUpDetails.setAgentId(getRoutingagent());
				routingSetUpDetails.setBranchApplicability(getBranchApplicability());
				routingSetUpDetails.setRoutingStatus(getRoutingStatus());
				routingSetUpDetails.setCreatedBy(getCreatedBy());
				routingSetUpDetails.setCreatedDate(getCreatedDate());

				if(getPkRoutingHeaderId()!=null){
					if(!editClick && getRoutingStatus().equals(Constants.ACTIVE)){
						routingSetUpDetails.setModifiedBy(getModifiedBy());
						routingSetUpDetails.setModifiedDate(getModifiedDate());
						routingSetUpDetails.setApprovedBy(getApprovedBy());
						routingSetUpDetails.setApprovedDate(getApprovedDate());
						routingSetUpDetails.setRemarks(getRemarks());
						routingSetUpDetails.setIsActive(getRoutingStatus());
						routingSetUpDetails.setDynamicLabelForActivateDeactivate(getDynamicLabelForActivateDeactivate());
						routingSetUpDetails.setIsActiveStatus(getIsActiveStatus());


					}else{
						routingSetUpDetails.setModifiedBy(sessionManage.getUserName());
						routingSetUpDetails.setModifiedDate(new Date());
						routingSetUpDetails.setApprovedBy(null);
						routingSetUpDetails.setApprovedDate(null);
						routingSetUpDetails.setRemarks(null);
						routingSetUpDetails.setIsActive(Constants.U);
						routingSetUpDetails.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
						routingSetUpDetails.setIsActiveStatus(getIsActiveStatus());

					}

				}else{
					routingSetUpDetails.setCreatedBy(sessionManage.getUserName());
					routingSetUpDetails.setCreatedDate(new Date());
					routingSetUpDetails.setDynamicLabelForActivateDeactivate(Constants.REMOVE);
					routingSetUpDetails.setIsActiveStatus(Constants.UNAPPROVED);
				}

				routingSetUpDetailsTable.add(routingSetUpDetails);
				routingSetUpNewDetailsTable.add(routingSetUpDetails);
				setDisableEditButton(false);
				bankbranchlist.clear();
				addallvalues.clear();
				//	setPkRoutingHeaderId(null);
				//	setSelectcount(0);
				if(getServiceName()!=null && getServiceName().equalsIgnoreCase(Constants.EFTNAME)){
					clearnonsearchableExceptCountry();
				}else{
					clearnonsearchable();
				}

			}else{
				RequestContext.getCurrentInstance().execute("nobranch.show();");
			}
		}catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::addtoListAllFields"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}




	}


	//saving all data to the DataBase
	public void saveAllDataToDataBase() {
		try{

			if(getServiceName()!=null && getServiceName().equalsIgnoreCase(Constants.CASHNAME))
			{
				boolean saveAllowCheck=false;
				if(routingAgentList.size()>0)
				{
					for(RoutingSetUpDetailsDialogCheckBox routingSetUpDetailsDialogCheckBox :routingAgentList)
					{
						if(routingSetUpDetailsDialogCheckBox.getRoutingBranchId()==null || routingSetUpDetailsDialogCheckBox.getBankId() == null
								|| routingSetUpDetailsDialogCheckBox.getBranchApplicability() == null)
						{
							saveAllowCheck=false;
							break;
						}else
						{
							saveAllowCheck=true;
						}
					}
				}

				if(!saveAllowCheck)
				{
					RequestContext.getCurrentInstance().execute("saveAllowCheck.show();");
					return;
				}

				List<RoutingSetUpDetailsDataTable> lstRoutingSetUpDetailsDataTable = getRoutingSetUpDetailsTableForCash();

				for(RoutingSetUpDetailsDataTable routingSetUpDetailsDataTable:lstRoutingSetUpDetailsDataTable)
				{
					RoutingHeader routingHeader = saveRoutingHeader(routingSetUpDetailsDataTable);
					saveRoutingDetail(routingHeader);
				}

				RequestContext.getCurrentInstance().execute("complete.show();");
				routingSetUpDetailsTable.clear();
				routingSetUpNewDetailsTable.clear();
				dialogselectbankbranch.clear();
				routingAgentList.clear();
				setBooRenderview(false);
				setBooRenderApprovalDataTable(false);
				setBooRenderDataTableApprovalExit(false);
				setPkRoutingHeaderId(null);
				setDisableEditButton(false);
				setBooAgentPanel(false);
				setBooNotAgentPanel(false);
				setRoutingSetUpDetailsTableForCash(null);
				setBooAddmorefield(false);

			}else
			{
				for(RoutingSetUpDetailsDataTable routingSetup : routingSetUpDetailsTable)
				{

					RoutingHeader routingHeader = new RoutingHeader();

					routingHeader.setRoutingHeaderId(routingSetup.getRoutingSetUpHeaderId());


					//save Application Country
					CountryMaster applicationCountry = new CountryMaster();
					applicationCountry.setCountryId(sessionManage.getCountryId());
					routingHeader.setExApplicationCountry(applicationCountry);

					//save Country
					CountryMaster countryMaster = new CountryMaster();
					countryMaster.setCountryId(routingSetup.getCountryId());
					routingHeader.setExCountryId(countryMaster);

					//save Currency
					CurrencyMaster currencyMaster = new CurrencyMaster();
					currencyMaster.setCurrencyId(routingSetup.getRoutingcurrencyId());
					routingHeader.setExCurrenyId(currencyMaster);

					//save Service Code
					ServiceMaster serviceMaster = new ServiceMaster();
					serviceMaster.setServiceId(routingSetup.getRoutingServiceId());
					routingHeader.setExServiceId(serviceMaster);

					DeliveryMode deliveryMode = new DeliveryMode();

					if(routingSetup.getRoutingDeliveryId()!=null){
						//save Delivery Code
						deliveryMode.setDeliveryModeId(routingSetup.getRoutingDeliveryId());
						routingHeader.setExDeliveryModeId(deliveryMode);
					}

					RemittanceModeMaster remittanceModeMaster = new RemittanceModeMaster();

					if(routingSetup.getRoutingRemittanceId()!=null){
						//save Remittance Mode
						remittanceModeMaster.setRemittanceModeId(routingSetup.getRoutingRemittanceId());
						routingHeader.setExRemittanceModeId(remittanceModeMaster);
					}

					//save Routing Country
					CountryMaster routingcountryMaster = new CountryMaster();
					routingcountryMaster.setCountryId(routingSetup.getRoutingcountryId());
					routingHeader.setExRoutingCountryId(routingcountryMaster);

					//save Bank 
					BankMaster bankMaster = new BankMaster();
					bankMaster.setBankId(routingSetup.getRoutingbankId());
					routingHeader.setExRoutingBankId(bankMaster);
					if(routingSetup.getBranchApplicability()!=null && routingSetup.getBranchApplicability().equalsIgnoreCase(Constants.SPECIFIC)){
						routingHeader.setBranchApplicability(Constants.S);
					}else{
						routingHeader.setBranchApplicability(Constants.ALL);
					}

					/*if(routingSetup.getRoutingStatus() != null && routingSetup.getRoutingStatus().equals(Constants.ACTIVE)){
						routingHeader.setIsActive(Constants.Yes);
						routingHeader.setApprovedBy(sessionManage.getUserName());
						routingHeader.setApprovedDate(new Date());
					}else if(routingSetup.getRoutingStatus() != null && routingSetup.getRoutingStatus().equals(Constants.UNAPPROVED)){
						routingHeader.setIsActive(Constants.U);
						routingHeader.setApprovedBy(null);
						routingHeader.setApprovedDate(null);
					}else if(routingSetup.getRoutingStatus() != null && routingSetup.getRoutingStatus().equals(Constants.PENDING_FOR_APPROVE)){
						routingHeader.setIsActive(Constants.U);
						routingHeader.setApprovedBy(null);
						routingHeader.setApprovedDate(null);
					}else{
						routingHeader.setIsActive(Constants.D);
						routingHeader.setApprovedBy(null);
						routingHeader.setApprovedDate(null);
					}*/

					if(routingSetup.getDynamicLabelForActivateDeactivate() != null && routingSetup.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)){
						routingHeader.setIsActive(Constants.Yes);
						routingHeader.setApprovedBy(routingSetup.getApprovedBy());
						routingHeader.setApprovedDate(routingSetup.getApprovedDate());
					}else if(routingSetup.getDynamicLabelForActivateDeactivate() != null && routingSetup.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.UNAPPROVED)){
						routingHeader.setIsActive(Constants.U);
						routingHeader.setApprovedBy(null);
						routingHeader.setApprovedDate(null);
					}else if(routingSetup.getDynamicLabelForActivateDeactivate() != null && routingSetup.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.PENDING_FOR_APPROVE)){
						routingHeader.setIsActive(Constants.U);
						routingHeader.setApprovedBy(null);
						routingHeader.setApprovedDate(null);
					}else if(routingSetup.getDynamicLabelForActivateDeactivate() != null && routingSetup.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.REMOVE)){
						routingHeader.setIsActive(Constants.U);
						routingHeader.setApprovedBy(null);
						routingHeader.setApprovedDate(null);
					}else{
						routingHeader.setIsActive(Constants.D);
						routingHeader.setApprovedBy(null);
						routingHeader.setApprovedDate(null);
					}

					routingHeader.setCreatedBy(routingSetup.getCreatedBy());
					routingHeader.setCreatedDate(routingSetup.getCreatedDate());
					routingHeader.setModifiedBy(routingSetup.getModifiedBy());
					routingHeader.setModifiedDate(routingSetup.getModifiedDate());
					
					//System IP Address.
					InetAddress ipAddress = InetAddress.getLocalHost();
					String ipAddr = ipAddress.getHostAddress().trim();

					//Adding to Header Table
					iroutingSetUpDetailsService.saveRoutingHeader(routingHeader);

					for(RoutingSetUpDetailsDialogCheckBox routingObj: routingAgentList){
						if(routingObj.getSelectRecord().equals(true)){
							RoutingAgent agent =new RoutingAgent();
							agent.setAgentId(routingObj.getAgentId());
							//agent.setAgentBranchId(routingObj.get);
							//agent.setRoutingAgentId(routingAgentId);
							//agent.setRoutingHeaderId(routingHeaderId);
							//agent.setAgentBranchApplicability(agentBranchApplicability);
							agent.setIsActive(Constants.Yes);
							agent.setCreatedBy(sessionManage.getUserName());
							agent.setCreatedDate(new Date());

							iroutingSetUpDetailsService.saveRoutingAgent(agent);
						}
					}

					//Loop Repeated by selection of Branches in Specific
					for (RoutingSetUpDetailsDialogCheckBox dialogselect : dialogselectbankbranch) {
						if(dialogselect.getBranchApplicability() !=null){

							if(dialogselect.getBranchApplicability().equalsIgnoreCase(Constants.SPECIFIC))
							{
								if(routingSetup.getRoutingcountryId().compareTo(dialogselect.getCountryId())==0 && routingSetup.getRoutingbankId().compareTo(dialogselect.getBankId())==0){


									RoutingDetails routingDetails = new RoutingDetails();
									if(dialogselect.getRoutingSetupDetailsId()==null){
										List<RoutingDetails> existedList=	iroutingSetUpDetailsService.toFetchAllApprovalRecordsFromDetails(routingSetup.getRoutingcountryId() , routingSetup.getRoutingcurrencyId(), routingSetup.getRoutingServiceId(), routingSetup.getRoutingRemittanceId(), routingSetup.getRoutingDeliveryId(), dialogselect.getBankBranchId(),  dialogselect.getBankId(), routingSetup.getRoutingSetUpHeaderId());
										if(existedList.size()>0){
											routingDetails.setRoutingDetailsId(existedList.get(0).getRoutingDetailsId());
										}else{
											routingDetails.setRoutingDetailsId(dialogselect.getRoutingSetupDetailsId());
										}
									}else{
										routingDetails.setRoutingDetailsId(dialogselect.getRoutingSetupDetailsId());
									}
									routingDetails.setExRountingHeaderId(routingHeader);
									routingDetails.setExApplicationCountry(applicationCountry);
									routingDetails.setExCountryId(countryMaster);
									routingDetails.setExCurrenyId(currencyMaster);
									routingDetails.setExServiceId(serviceMaster);

									if(routingSetup.getRoutingRemittanceId()!=null){
										routingDetails.setExRemittanceModeId(remittanceModeMaster);
									}

									if(routingSetup.getRoutingDeliveryId()!=null){
										routingDetails.setExDeliveryModeId(deliveryMode);
									}

									routingDetails.setExRoutingCountryId(routingcountryMaster);
									routingDetails.setExRoutingBankId(bankMaster);

									//save Bank Branch Details
									BankBranch bankBranch = new BankBranch();
									bankBranch.setBankBranchId(dialogselect.getBankBranchId());
									routingDetails.setExBankBranchId(bankBranch);

									if (dialogselect.getAgentId() != null) {
										BankIndicator bankIndicator = new BankIndicator();
										bankIndicator.setBankIndicatorId(dialogselect.getAgentId());
										routingDetails.setExagentInd(bankIndicator);
									} else if (routingSetup.getAgentId() != null) {
										BankIndicator bankIndicator = new BankIndicator();
										bankIndicator.setBankIndicatorId(routingSetup.getAgentId());
										routingDetails.setExagentInd(bankIndicator);
									} else { 
										routingDetails.setExagentInd(null);
									}

									//	routingDetails.setBranchApplicability(routingSetup.getBranchApplicability());

									if(routingSetup.getBranchApplicability()!=null && routingSetup.getBranchApplicability().equalsIgnoreCase(Constants.SPECIFIC)){
										routingDetails.setBranchApplicability(Constants.S);
									}else{
										routingDetails.setBranchApplicability(Constants.ALL);
									}

									/*if(routingSetup.getRoutingStatus() != null && routingSetup.getRoutingStatus().equals(Constants.ACTIVE)){
										routingDetails.setIsActive(Constants.Yes);
										routingDetails.setApprovedBy(sessionManage.getUserName());
										routingDetails.setApprovedDate(new Date());
									}else{
										routingDetails.setIsActive(Constants.U);
										routingDetails.setApprovedBy(null);
										routingDetails.setApprovedDate(null);
									}*/

									if(routingSetup.getDynamicLabelForActivateDeactivate() != null && routingSetup.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)){
										routingDetails.setIsActive(Constants.Yes);
										routingDetails.setApprovedBy(routingHeader.getApprovedBy());
										routingDetails.setApprovedDate(routingHeader.getApprovedDate());
									}else if(routingSetup.getDynamicLabelForActivateDeactivate() != null && routingSetup.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.UNAPPROVED)){
										routingDetails.setIsActive(Constants.U);
										routingDetails.setApprovedBy(null);
										routingDetails.setApprovedDate(null);
									}else if(routingSetup.getDynamicLabelForActivateDeactivate() != null && routingSetup.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.PENDING_FOR_APPROVE)){
										routingDetails.setIsActive(Constants.U);
										routingDetails.setApprovedBy(null);
										routingDetails.setApprovedDate(null);
									}else if(routingSetup.getDynamicLabelForActivateDeactivate() != null && routingSetup.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.REMOVE)){
										routingDetails.setIsActive(Constants.U);
										routingDetails.setApprovedBy(null);
										routingDetails.setApprovedDate(null);
									}else{
										routingDetails.setIsActive(Constants.D);
										routingDetails.setApprovedBy(null);
										routingDetails.setApprovedDate(null);
									}

									routingDetails.setCreatedBy(routingSetup.getCreatedBy());
									routingDetails.setCreatedDate(routingSetup.getCreatedDate());
									routingDetails.setModifiedBy(routingSetup.getModifiedBy());
									routingDetails.setModifiedDate(routingSetup.getModifiedDate());
									/*routingDetails.setApprovedBy(routingDetails.getApprovedBy());
									routingDetails.setApprovedDate(routingDetails.getApprovedDate());*/
									iroutingSetUpDetailsService.saveRoutingDetails(routingDetails);

								}
							}
						}
					}

				}
				RequestContext.getCurrentInstance().execute("complete.show();");
				routingSetUpDetailsTable.clear();
				routingSetUpNewDetailsTable.clear();
				dialogselectbankbranch.clear();
				setBooRenderview(false);
				setBooRenderApprovalDataTable(false);
				setBooRenderDataTableApprovalExit(false);
				setPkRoutingHeaderId(null);
				setDisableEditButton(false);

			}
		}catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::saveAllDataToDataBase"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}

	}

	public void resetFilters(String clearDataTable) {
		DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent(clearDataTable);
		if (dataTable != null) {
			dataTable.reset();
		}
	}


	//On Select Branch Applicability Checking in DB
	public void specificCheckinginDataBase(){
		try{
			setSerchBranchName(null);
			bankbranchlist.clear();
			addallvalues.clear();
			setSelectAll(false);

			if(getRoutingCountryId()!=null && getRoutingbankId()!=null){
				List<BankBranch> bankbranchspecific=new ArrayList<BankBranch>();
				bankbranchspecific = generalService.getBankBranchList(getRoutingCountryId(), getRoutingbankId());

				if(getBranchApplicability().equals(Constants.SPECIFIC)){
					if(bankbranchspecific.size()!=0){
						for(BankBranch bankbranch : bankbranchspecific){

							RoutingSetUpDetailsDialogCheckBox routingcheckbox = new RoutingSetUpDetailsDialogCheckBox();

							routingcheckbox.setBankBranchId(bankbranch.getBankBranchId());
							routingcheckbox.setBankId(bankbranch.getExBankMaster().getBankId());
							routingcheckbox.setCountryId(bankbranch.getFsCountryMaster().getCountryId());
							routingcheckbox.setBankbranchCode(bankbranch.getBranchCode());
							routingcheckbox.setBranchFullName(bankbranch.getBranchFullName());
							routingcheckbox.setBranchApplicability(getBranchApplicability());

							if(dialogselectbankbranch.size()!=0){
								for (RoutingSetUpDetailsDialogCheckBox routingDetails : dialogselectbankbranch) {

									if((bankbranch.getFsCountryMaster().getCountryId().compareTo(routingDetails.getCountryId())==0)){

										if((bankbranch.getExBankMaster().getBankId().compareTo(routingDetails.getBankId())==0)){
											if(bankbranch.getBankBranchId().compareTo(routingDetails.getBankBranchId())==0){
												if(routingDetails.getSelectRecord().equals(Boolean.TRUE)){
													setRoutingSetUpHeaderId(routingDetails.getRoutingSetupHeaderId());
													routingcheckbox.setRoutingSetupHeaderId(routingDetails.getRoutingSetupHeaderId());
													routingcheckbox.setRoutingSetupDetailsId(routingDetails.getRoutingSetupDetailsId());
													routingcheckbox.setRoutingStatus(Constants.ACTIVE);
													routingcheckbox.setSelectRecord(true);
													break;
												}else{
													routingcheckbox.setRoutingSetupHeaderId(routingDetails.getRoutingSetupHeaderId());
													routingcheckbox.setRoutingSetupDetailsId(routingDetails.getRoutingSetupDetailsId());
													routingcheckbox.setRoutingStatus(Constants.IN_ACTIVE);
													routingcheckbox.setSelectRecord(false);
												}
											}else{
												routingcheckbox.setSelectRecord(false);
											}
										}else{
											routingcheckbox.setSelectRecord(false);
										}
									}else{
										routingcheckbox.setSelectRecord(true);
									}
								}

								bankbranchlist.add(routingcheckbox);

							}else{
								routingcheckbox.setSelectRecord(true);
								bankbranchlist.add(routingcheckbox);
							}

						}
						/*if(!editClick){
							RequestContext.getCurrentInstance().execute("details.show();");
						}*/
						RequestContext.getCurrentInstance().execute("details.show();");

					}else{
						RequestContext.getCurrentInstance().execute("nobranch.show();");
					}
				}else if(getBranchApplicability().equals(Constants.ALL)){
					if(bankbranchspecific.size()!=0){
						for(BankBranch bankbranch : bankbranchspecific){
							RoutingSetUpDetailsDialogCheckBox routingcheckbox = new RoutingSetUpDetailsDialogCheckBox();
							routingcheckbox.setBankBranchId(bankbranch.getBankBranchId());
							routingcheckbox.setBankId(bankbranch.getExBankMaster().getBankId());
							routingcheckbox.setCountryId(bankbranch.getFsCountryMaster().getCountryId());
							routingcheckbox.setBankbranchCode(bankbranch.getBranchCode());
							routingcheckbox.setBranchFullName(bankbranch.getBranchFullName());
							routingcheckbox.setSelectRecord(isSelectedcheck());
							routingcheckbox.setBranchApplicability(getBranchApplicability());
							routingcheckbox.setRoutingStatus(Constants.ACTIVE);

							addallvalues.add(routingcheckbox);
						}
					}else{
						RequestContext.getCurrentInstance().execute("nobranch.show();");
					}
				}else{
					System.out.println("!!!!!!!!!!bankbranchspecific not Selected!!!!!!!!!!!!!!!");
				}
			}else{
				setBranchApplicability(null);
				RequestContext.getCurrentInstance().execute("selectrecords.show();");
			}
		}catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::specificCheckinginDataBase"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}

	}

	// pop dataTable when clicked on Branch Applicabilty in Data Table
	public void detailsBranchData(RoutingSetUpDetailsDataTable event){
		try{
			popupinDataTable.clear();		

			if(event.getBranchApplicability().equalsIgnoreCase(Constants.SPECIFIC)){

				if(event.getRoutingSetUpHeaderId() != null){
					for (RoutingSetUpDetailsDialogCheckBox routingheaddata : dialogselectbankbranch) {
						if(routingheaddata.getRoutingSetupHeaderId() != null && (event.getRoutingSetUpHeaderId().compareTo(routingheaddata.getRoutingSetupHeaderId())==0) && (event.getRoutingcountryId().compareTo(routingheaddata.getCountryId())==0) && (event.getRoutingbankId().compareTo(routingheaddata.getBankId())==0)){
							RoutingSetUpDetailsDialogCheckBox routingcheckbox = new RoutingSetUpDetailsDialogCheckBox();

							routingcheckbox.setRoutingSetupDetailsId(routingheaddata.getRoutingSetupDetailsId());
							routingcheckbox.setRoutingSetupHeaderId(routingheaddata.getRoutingSetupHeaderId());
							routingcheckbox.setBankBranchId(routingheaddata.getBankBranchId());
							routingcheckbox.setBankId(routingheaddata.getBankId());
							routingcheckbox.setCountryId(routingheaddata.getCountryId());
							routingcheckbox.setBankbranchCode(routingheaddata.getBankbranchCode());
							routingcheckbox.setBranchFullName(routingheaddata.getBranchFullName());
							routingcheckbox.setAgentId(routingheaddata.getAgentId());
							routingcheckbox.setBranchApplicability(routingheaddata.getBranchApplicability());
							if(routingheaddata.getRoutingStatus()!=null){
								routingcheckbox.setRoutingStatus(routingheaddata.getRoutingStatus());
							}else{
								routingcheckbox.setRoutingStatus(Constants.New_Record);
							}

							popupinDataTable.add(routingcheckbox);
						}
					}
				}else {
					for (RoutingSetUpDetailsDialogCheckBox routingheaddata : dialogselectbankbranch) {
						if((event.getRoutingcountryId().compareTo(routingheaddata.getCountryId())==0) && (event.getRoutingbankId().compareTo(routingheaddata.getBankId())==0)){
							RoutingSetUpDetailsDialogCheckBox routingcheckbox = new RoutingSetUpDetailsDialogCheckBox();

							routingcheckbox.setRoutingSetupDetailsId(routingheaddata.getRoutingSetupDetailsId());
							routingcheckbox.setRoutingSetupHeaderId(routingheaddata.getRoutingSetupHeaderId());
							routingcheckbox.setBankBranchId(routingheaddata.getBankBranchId());
							routingcheckbox.setBankId(routingheaddata.getBankId());
							routingcheckbox.setCountryId(routingheaddata.getCountryId());
							routingcheckbox.setBankbranchCode(routingheaddata.getBankbranchCode());
							routingcheckbox.setBranchFullName(routingheaddata.getBranchFullName());
							routingcheckbox.setAgentId(routingheaddata.getAgentId());
							routingcheckbox.setBranchApplicability(routingheaddata.getBranchApplicability());
							if(routingheaddata.getRoutingStatus()!=null){
								routingcheckbox.setRoutingStatus(routingheaddata.getRoutingStatus());
							}else{
								routingcheckbox.setRoutingStatus(Constants.New_Record);
							}

							popupinDataTable.add(routingcheckbox);
						}
					}
				}

			}else{
				List<BankBranch> bankbranchspecific=new ArrayList<BankBranch>();
				bankbranchspecific = generalService.getBankBranchList(event.getRoutingcountryId(), event.getRoutingbankId());
				if(bankbranchspecific.size()!=0){
					for(BankBranch bankbranch : bankbranchspecific){
						RoutingSetUpDetailsDialogCheckBox routingcheckbox = new RoutingSetUpDetailsDialogCheckBox();

						routingcheckbox.setBankBranchId(bankbranch.getBankBranchId());
						routingcheckbox.setBankId(bankbranch.getExBankMaster().getBankId());
						routingcheckbox.setCountryId(bankbranch.getFsCountryMaster().getCountryId());
						routingcheckbox.setBankbranchCode(bankbranch.getBranchCode());
						routingcheckbox.setBranchFullName(bankbranch.getBranchFullName());
						routingcheckbox.setSelectRecord(isSelectedcheck());
						routingcheckbox.setBranchApplicability(getBranchApplicability());
						routingcheckbox.setRoutingStatus(event.getRoutingStatus());

						popupinDataTable.add(routingcheckbox);
					}
				}else{
					RequestContext.getCurrentInstance().execute("nobranch.show();");
				}
			}

			if(!statusDelete){
				RequestContext.getCurrentInstance().execute("datatabledetails.show();");
			}
		}catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::detailsBranchData"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}


	}
	/**
	 * author :Rabil
	 * Purposr :Select and Deselect all at a time	
	 */

	public void selecatAndDeselectAll(){
		try{
			System.out.println("selectAll :"+selectAll+"\t bankbranchlist :"+bankbranchlist.size());

			//private List<RoutingSetUpDetailsDialogCheckBox> bankbranchlist=new CopyOnWriteArrayList<RoutingSetUpDetailsDialogCheckBox>();

			List<RoutingSetUpDetailsDialogCheckBox> bankbranchlistTemp=new CopyOnWriteArrayList<RoutingSetUpDetailsDialogCheckBox>();

			if(selectAll){
				for(RoutingSetUpDetailsDialogCheckBox rouSetUpnew : bankbranchlist){
					rouSetUpnew.setSelectRecord(true);
					//bankbranchlist.remove(rouSetUpnew);
					bankbranchlistTemp.add(rouSetUpnew);
				}
			}else{
				for(RoutingSetUpDetailsDialogCheckBox rouSetUpnew : bankbranchlist){
					rouSetUpnew.setSelectRecord(false);
					//bankbranchlist.remove(rouSetUpnew);
					bankbranchlistTemp.add(rouSetUpnew);
				}
			}

			bankbranchlist.clear();

			bankbranchlist.addAll(bankbranchlistTemp);

			System.out.println("selectAll :"+selectAll+"\t bankbranchlist :"+bankbranchlist.size());
		}catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::selecatAndDeselectAll"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}

	}

	/**
	 * :Agent List for select All and deselect	
	 */
	public void selecatAndDeselectAllForAgent(){
		try{
			if(selectAll){
				resetFilters("routingsetupform:checkDataTable");
				for(RoutingSetUpDetailsDialogCheckBox rouSetUpnew : routingAgentList){
					rouSetUpnew.setSelectRecord(true);
					routingAgentList.remove(rouSetUpnew);
					routingAgentList.add(rouSetUpnew);
				}
			}else{
				for(RoutingSetUpDetailsDialogCheckBox rouSetUpnew : routingAgentList){
					rouSetUpnew.setSelectRecord(false);
					routingAgentList.remove(rouSetUpnew);
					routingAgentList.add(rouSetUpnew);
				}
			}
		}catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::selecatAndDeselectAllForAgent"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}

	public void addingAgentstoDataTable(RoutingAgentViewDataTable agentObj){
		System.out.println("RoutingAgentViewDataTable :"+agentObj.getAgentId()+"\t agentObj :"+agentObj.getBankFullName()+"\t code :"+agentObj.getAgentId());
	}




	public void addingBranchestoDataTableForAgent(RoutingSetUpDetailsDialogCheckBox branchcode){
		branchcode.setSelectRecord(false);
	}

	//adding selected branches to list
	public void addingBranchestoDataTable(RoutingSetUpDetailsDialogCheckBox branchcode){
		try{
			List<RoutingSetUpDetailsDialogCheckBox> bankbranchlistTemp=new CopyOnWriteArrayList<RoutingSetUpDetailsDialogCheckBox>();
			if(branchcode.getSelectRecord()){

				for(RoutingSetUpDetailsDialogCheckBox rouSetUpnew : bankbranchlist){
					if(rouSetUpnew.getBankBranchId().compareTo(branchcode.getBankBranchId())==0){
						rouSetUpnew.setSelectRecord(true);
						bankbranchlistTemp.add(rouSetUpnew);
						break;
					}else
					{
						bankbranchlistTemp.add(rouSetUpnew);
					}
				}

				bankbranchlist.clear();

				bankbranchlist.addAll(bankbranchlistTemp);

			}else{

				for(RoutingSetUpDetailsDialogCheckBox rouSetUpnew : bankbranchlist){
					if(rouSetUpnew.getBankBranchId().compareTo(branchcode.getBankBranchId())==0){
						rouSetUpnew.setSelectRecord(false);
						if(branchcode.getRoutingSetupDetailsId()!=null){
							iroutingSetUpDetailsService.updateRecord(branchcode.getRoutingSetupDetailsId() );
						}
						bankbranchlistTemp.add(rouSetUpnew);
						break;
					}else
					{
						bankbranchlistTemp.add(rouSetUpnew);
					}
				}

				bankbranchlist.clear();

				bankbranchlist.addAll(bankbranchlistTemp);

			}
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}

	}

	public void openSearchDailog(){

		if(getRoutingcurrencyId()==null ||getCountryId()==null ||  getRoutingServiceId()==null)
		{
			RequestContext.getCurrentInstance().execute("pleaseSelectCountryandService.show();");
		}else
		{
			displayView();
		}
	}

	//CR - Blocked due to combination of search made difference [Chiru]

	//searching All five Fields in Data Base
	public void searchingRecordsinDataBase(){
		try{
			getServiceCodeFromDB();

			if(getCountryId()!=null && getRoutingcurrencyId()!=null && getRoutingServiceId()!=null && getRoutingRemittanceId()!=null && getRoutingDeliveryId()!=null){
				// to set the Remittance Name in Data Table Based on the Remittance Id
				for (RemittanceModeDescription remitanceMode : servicedatafromdb) {
					if(getRoutingRemittanceId().compareTo(remitanceMode.getRemittanceModeMaster().getRemittanceModeId())==0){
						setRemittanceName(remitanceMode.getRemittanceDescription());
					}
				}
				// to set the Delivery Name in Data Table Based on the Delivery Id
				for (DeliveryModeDesc deliverydesc : deliverydataBasedonService) {
					if(getRoutingDeliveryId().compareTo(deliverydesc.getDeliveryMode().getDeliveryModeId())==0){
						setDeliveryName(deliverydesc.getEnglishDeliveryName());
					}
				}
				renderFields();

			}else if(getCountryId()!=null && getRoutingcurrencyId()!=null && getRoutingServiceId()!=null && getRoutingRemittanceId()!=null ){
				for (RemittanceModeDescription remitanceMode : servicedatafromdb) {
					if(getRoutingRemittanceId().compareTo(remitanceMode.getRemittanceModeMaster().getRemittanceModeId())==0){
						setRemittanceName(remitanceMode.getRemittanceDescription());
					}
				}
				renderFields();

			}else if(getCountryId()!=null && getRoutingcurrencyId()!=null && getRoutingServiceId()!=null && getRoutingDeliveryId()!=null){

				if(getRoutingRemittanceId()==null)
				{
					RequestContext.getCurrentInstance().execute("noremittance.show();");
					setRoutingDeliveryId(null);
					return;
				}
				// to set the Delivery Name in Data Table Based on the Delivery Id
				for (DeliveryModeDesc deliverydesc : deliverydataBasedonService) {
					if(getRoutingDeliveryId().compareTo(deliverydesc.getDeliveryMode().getDeliveryModeId())==0){
						setDeliveryName(deliverydesc.getEnglishDeliveryName());
					}
				}
				renderFields();
			}else if(getCountryId()!=null && getRoutingcurrencyId()!=null && getRoutingServiceId()!=null){
				renderFields();
			}

			if(getCountryId()!=null){
				listAllCurrencyBasedonCountry(getCountryId());
			}
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}

	}



	public void serviceNameSet(){

	}

	public void renderFields(){
		setBooRenderAdd(true);
		/*routingSetUpDetailsTable.clear();
		dialogselectbankbranch.clear();*/
		routingDetailsList.clear();
		bankbranchlist.clear();
		popupinDataTable.clear();
		routingheaderData.clear();

		if(getServiceName()!=null && getServiceName().equalsIgnoreCase(Constants.EFTNAME) || getServiceName().equalsIgnoreCase(Constants.TTNAME)){
			clearnonsearchableExceptCountry();
		}else{
			clearnonsearchable();
			setBooRenderview(false);
			setBooRenderApprovalDataTable(false);
			setBooRenderDataTableApprovalExit(false);
		}

		if(routingSetUpDetailsTable!=null && routingSetUpDetailsTable.size()>0){
			setBooRenderDataTable(true);
		}else{
			setBooRenderDataTable(false);
		}
	}

	private boolean disableRemmitance;
	private boolean disableDelivery;

	public boolean isDisableRemmitance() {
		return disableRemmitance;
	}

	public void setDisableRemmitance(boolean disableRemmitance) {
		this.disableRemmitance = disableRemmitance;
	}

	public boolean isDisableDelivery() {
		return disableDelivery;
	}

	public void setDisableDelivery(boolean disableDelivery) {
		this.disableDelivery = disableDelivery;
	}

	/*	//searching All 5 Fields in Data Base or 3 Fields According to CASH , DD or EFT
	public void searchingRecordsinDataBase(){

		if(getCountryId()!=null && getRoutingcurrencyId()!=null && getRoutingServiceId()!=null){

			if(!isDisableDelivery() && !isDisableRemmitance()){

				setBooRenderDataTable(false);
				routingSetUpDetailsTable.clear();
				dialogselectbankbranch.clear();
				routingDetailsList.clear();
				allroutingDetails.clear();
				bankbranchlist.clear();
				bankAppList.clear();
				popupinDataTable.clear();
				routingheaderData.clear();
				if(getServiceName().equalsIgnoreCase(Constants.EFTNAME)){
					clearnonsearchableExceptCountry();
				}else{
					clearnonsearchable();
				}

				if(getRoutingRemittanceId()!=null && getRoutingDeliveryId()!=null){

					setBooRenderAdd(true);

					// to set the Remittance Name in Data Table Based on the Remittance Id

					for (RemittanceModeDescription remitanceMode : servicedatafromdb) {
						if(getRoutingRemittanceId().compareTo(remitanceMode.getRemittanceModeMaster().getRemittanceModeId())==0){
							setRemittanceName(remitanceMode.getRemittanceDescription());
						}
					}

					// to set the Delivery Name in Data Table Based on the Delivery Id

					for (DeliveryModeDesc deliverydesc : deliverydataBasedonService) {
						if(getRoutingDeliveryId().compareTo(deliverydesc.getDeliveryMode().getDeliveryModeId())==0){
							setDeliveryName(deliverydesc.getEnglishDeliveryName());
						}
					}

					//Checking five fields in DB
					List<RoutingHeader> routingheaderList = iroutingSetUpDetailsService.getAllRecordsFromDB(getCountryId(),getRoutingcurrencyId(),getRoutingServiceId(),getRoutingRemittanceId(),getRoutingDeliveryId());

					if(routingheaderList.size()!=0){
						setBooRenderDataTable(true);
						setDisableDelivery(false);
						setDisableRemmitance(false);
						setRoutingheaderData(routingheaderList);

						for(RoutingHeader routingHeaderListValue : routingheaderData){

							RoutingSetUpDetailsDataTable routingdetails = new RoutingSetUpDetailsDataTable();

							routingdetails.setRoutingSetUpHeaderId(routingHeaderListValue.getRoutingHeaderId());
							routingdetails.setApplicationCountryId(routingHeaderListValue.getExApplicationCountry().getCountryId());
							routingdetails.setCountryId(routingHeaderListValue.getExCountryId().getCountryId());
							routingdetails.setCountryName(generalService.getCountryName(sessionManage.getLanguageId(), routingHeaderListValue.getExCountryId().getCountryId()));
							routingdetails.setRoutingcurrencyId(routingHeaderListValue.getExCurrenyId().getCurrencyId());
							routingdetails.setRoutingcurrencyName(generalService.getCurrencyName(routingHeaderListValue.getExCurrenyId().getCurrencyId()));
							routingdetails.setRoutingServiceId(routingHeaderListValue.getExServiceId().getServiceId());
							routingdetails.setRoutingServiceCode(iroutingSetUpDetailsService.getServiceCode(sessionManage.getLanguageId(),routingHeaderListValue.getExServiceId().getServiceId()).get(0).getLocalServiceDescription());
							routingdetails.setRoutingDeliveryId(routingHeaderListValue.getExDeliveryModeId().getDeliveryModeId());
							routingdetails.setRoutingDeliveryMode(iroutingSetUpDetailsService.getDeliveryModeDetails(sessionManage.getLanguageId(),routingHeaderListValue.getExDeliveryModeId().getDeliveryModeId()).get(0).getEnglishDeliveryName());
							routingdetails.setRoutingRemittanceId(routingHeaderListValue.getExRemittanceModeId().getRemittanceModeId());
							routingdetails.setRoutingRemittanceMode(iroutingSetUpDetailsService.getRemittanceMode(sessionManage.getLanguageId(),routingHeaderListValue.getExRemittanceModeId().getRemittanceModeId()).get(0).getRemittanceDescription());
							routingdetails.setRoutingcountryId(routingHeaderListValue.getExRoutingCountryId().getCountryId());
							routingdetails.setRoutingcountryName(generalService.getCountryName(sessionManage.getLanguageId(), routingHeaderListValue.getExRoutingCountryId().getCountryId()));
							routingdetails.setRoutingbankId(routingHeaderListValue.getExRoutingBankId().getBankId());
							routingdetails.setRoutingbankName(generalService.getBankName(routingHeaderListValue.getExRoutingBankId().getBankId()).toString());
							routingdetails.setBranchApplicability(routingHeaderListValue.getBranchApplicability());

							if(routingHeaderListValue.getIsActive().compareTo(Constants.Yes)==0){
								routingdetails.setRoutingStatus(Constants.ACTIVE);
							}else{
								routingdetails.setRoutingStatus(Constants.IN_ACTIVE);
							}				

							routingdetails.setCreatedBy(routingHeaderListValue.getCreatedBy());
							routingdetails.setCreatedDate(routingHeaderListValue.getCreatedDate());

							routingSetUpDetailsTable.add(routingdetails);

							if (getRoutingSetUpDetailsTable().size() != 0) {
								// Checking five fields in DB
								routingDetailsList = iroutingSetUpDetailsService.getAllRecordsOfRoutingDetails(routingHeaderListValue.getRoutingHeaderId());

								if(routingDetailsList.size()!=0){
									allroutingDetails.addAll(routingDetailsList);
									for (RoutingDetails routingDetails : routingDetailsList) {
										RoutingSetUpDetailsDialogCheckBox routingcheckbox = new RoutingSetUpDetailsDialogCheckBox();

										routingcheckbox.setRoutingSetupHeaderId(routingDetails.getExRountingHeaderId().getRoutingHeaderId());
										routingcheckbox.setRoutingSetupDetailsId(routingDetails.getRoutingDetailsId());
										routingcheckbox.setBankBranchId(routingDetails.getExBankBranchId().getBankBranchId());
										routingcheckbox.setBankId(routingDetails.getExRoutingBankId().getBankId());
										routingcheckbox.setCountryId(routingDetails.getExRoutingCountryId().getCountryId());
										routingcheckbox.setBankbranchCode(routingDetails.getExBankBranchId().getBranchCode());
										routingcheckbox.setBranchFullName(routingDetails.getExBankBranchId().getBranchFullName());
										routingcheckbox.setAgentId(routingDetails.getExagentInd()==null ? null : routingDetails.getExagentInd().getBankIndicatorId());
										routingcheckbox.setBranchApplicability(routingDetails.getBranchApplicability());
										if(routingDetails.getIsActive().compareTo(Constants.Yes)==0){
											routingcheckbox.setRoutingStatus(Constants.ACTIVE);
											routingcheckbox.setSelectRecord(Constants.True);
										}else{
											routingcheckbox.setRoutingStatus(Constants.IN_ACTIVE);
											routingcheckbox.setSelectRecord(Constants.False);
										}
										dialogselectbankbranch.add(routingcheckbox);
									}

								}

							}

						}		

					}else{
						setBooRenderDataTable(false);
					}
			}


			}else{

				setBooRenderAdd(true);
				routingSetUpDetailsTable.clear();
				dialogselectbankbranch.clear();
				routingDetailsList.clear();
				bankbranchlist.clear();
				allroutingDetails.clear();
				popupinDataTable.clear();
				routingheaderData.clear();
				bankAppList.clear();
				if(getServiceName().equalsIgnoreCase(Constants.EFTNAME)){
					clearnonsearchableExceptCountry();
				}else{
					clearnonsearchable();
				}

				//Checking five fields in DB
				List<RoutingHeader> routinghdlist= iroutingSetUpDetailsService.getAllRecordsFromDBRH(getCountryId(),getRoutingcurrencyId(),getRoutingServiceId());

				if(routinghdlist.size()!=0){
					setBooRenderDataTable(true);
					setDisableDelivery(true);
					setDisableRemmitance(true);
					setRoutingheaderData(routinghdlist);

					for(RoutingHeader routingHeaderListValue : routingheaderData){

						RoutingSetUpDetailsDataTable routingdetails = new RoutingSetUpDetailsDataTable();

						routingdetails.setRoutingSetUpHeaderId(routingHeaderListValue.getRoutingHeaderId());
						routingdetails.setApplicationCountryId(routingHeaderListValue.getExApplicationCountry().getCountryId());
						routingdetails.setCountryId(routingHeaderListValue.getExCountryId().getCountryId());
						routingdetails.setCountryName(generalService.getCountryName(sessionManage.getLanguageId(), routingHeaderListValue.getExCountryId().getCountryId()));
						routingdetails.setRoutingcurrencyId(routingHeaderListValue.getExCurrenyId().getCurrencyId());
						routingdetails.setRoutingcurrencyName(generalService.getCurrencyName(routingHeaderListValue.getExCurrenyId().getCurrencyId()));
						routingdetails.setRoutingServiceId(routingHeaderListValue.getExServiceId().getServiceId());
						routingdetails.setRoutingServiceCode(iroutingSetUpDetailsService.getServiceCode(sessionManage.getLanguageId(),routingHeaderListValue.getExServiceId().getServiceId()).get(0).getLocalServiceDescription());
						routingdetails.setRoutingDeliveryId(null);
						routingdetails.setRoutingDeliveryMode(null);
						routingdetails.setRoutingRemittanceId(null);
						routingdetails.setRoutingRemittanceMode(null);
						routingdetails.setRoutingcountryId(routingHeaderListValue.getExRoutingCountryId().getCountryId());
						routingdetails.setRoutingcountryName(generalService.getCountryName(sessionManage.getLanguageId(), routingHeaderListValue.getExRoutingCountryId().getCountryId()));
						routingdetails.setRoutingbankId(routingHeaderListValue.getExRoutingBankId().getBankId());
						routingdetails.setRoutingbankName(generalService.getBankName(routingHeaderListValue.getExRoutingBankId().getBankId()).toString());
						routingdetails.setBranchApplicability(routingHeaderListValue.getBranchApplicability());

						if(routingHeaderListValue.getIsActive().compareTo(Constants.Yes)==0){
							routingdetails.setRoutingStatus(Constants.ACTIVE);
						}else{
							routingdetails.setRoutingStatus(Constants.IN_ACTIVE);
						}				

						routingdetails.setCreatedBy(routingHeaderListValue.getCreatedBy());
						routingdetails.setCreatedDate(routingHeaderListValue.getCreatedDate());

						routingSetUpDetailsTable.add(routingdetails);

						if (getRoutingSetUpDetailsTable().size() != 0) {
							// Checking five fields in DB
							routingDetailsList = iroutingSetUpDetailsService.getAllRecordsOfRoutingDetails(routingHeaderListValue.getRoutingHeaderId());

							if(routingDetailsList.size()!=0){
								allroutingDetails.addAll(routingDetailsList);
								for (RoutingDetails routingDetails : routingDetailsList) {
									RoutingSetUpDetailsDialogCheckBox routingcheckbox = new RoutingSetUpDetailsDialogCheckBox();

									routingcheckbox.setRoutingSetupHeaderId(routingDetails.getExRountingHeaderId().getRoutingHeaderId());
									routingcheckbox.setRoutingSetupDetailsId(routingDetails.getRoutingDetailsId());
									routingcheckbox.setBankBranchId(routingDetails.getExBankBranchId().getBankBranchId());
									routingcheckbox.setBankId(routingDetails.getExRoutingBankId().getBankId());
									routingcheckbox.setCountryId(routingDetails.getExRoutingCountryId().getCountryId());
									routingcheckbox.setBankbranchCode(routingDetails.getExBankBranchId().getBranchCode());
									routingcheckbox.setBranchFullName(routingDetails.getExBankBranchId().getBranchFullName());
									routingcheckbox.setAgentId(routingDetails.getExagentInd()==null ? null : routingDetails.getExagentInd().getBankIndicatorId());
									routingcheckbox.setBranchApplicability(routingDetails.getBranchApplicability());
									if(routingDetails.getIsActive().compareTo(Constants.Yes)==0){
										routingcheckbox.setRoutingStatus(Constants.ACTIVE);
										routingcheckbox.setSelectRecord(Constants.True);
									}else{
										routingcheckbox.setRoutingStatus(Constants.IN_ACTIVE);
										routingcheckbox.setSelectRecord(Constants.False);
									}
									dialogselectbankbranch.add(routingcheckbox);
								}

							}

						}

					}		

				}else{
					setBooRenderDataTable(false);
				}

			}

		}

	}*/

	//to display Agent based on Bank Selected only Service Provider
	public void displayAgentBasedonServiceProvider(){
		try{
			routingAgentList.clear();
			List<BankIndicator> serviceBankIndlist = bankIndicatorService.getRecordFromDB(Constants.BANK_INDICATOR_SERVICEPRO_BANK);

			List<BigDecimal> seriveProviderBankList = new ArrayList<BigDecimal>();

			for(BankIndicator bankInd:serviceBankIndlist){
				seriveProviderBankList.add(bankInd.getBankIndicatorId());
			}

			BigDecimal serviceBankInd = null;

			if(getServiceName().equalsIgnoreCase(Constants.CASHNAME)){
				for(RoutingSetupBankDetails lstRouting : bankAppList){
					if(getRoutingbankId().compareTo(lstRouting.getBankId())==0){
						serviceBankInd = lstRouting.getBankIndicator();
						break;
					}
				}
				if(serviceBankInd != null){
					for(BigDecimal serviceBankId : seriveProviderBankList){
						if(serviceBankInd.compareTo(serviceBankId)==0){
							showAgentDeatils(serviceBankId);
							RequestContext.getCurrentInstance().execute("agentPanel.show();");
							break;
						}
					}
				}

			}
		}catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::displayAgentBasedonServiceProvider"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}

	public void showAgentDeatils(BigDecimal serviceBankId)throws Exception{
		routingAgentList.clear();
		setLstTempBankBranchView(null);
		setBooAddmorefield(false);
		addRecordsToDataTableFromDB();
	}


	//clearing the fields
	public void clear(){
		setCountryId(null);
		setRoutingCountryId(null);
		setRoutingbankId(null);
		setRoutingcurrencyId(null);
		setRoutingDeliveryId(null);
		setRoutingRemittanceId(null);
		setRoutingServiceId(null);
		setRoutingStatus(null);
		setBranchApplicability(null);
		setRoutingagent(null);
		setServiceName(null);
		setRemittanceName(null);
		setPkRoutingHeaderId(null);
		setDeliveryName(null);
		setDisableDelivery(false);
		setDisableRemmitance(false);
		setDisableEditButton(false);
		setCountryIdForDailog(null);
		setRoutingcurrencyIdForDailog(null);
		setRoutingServiceIdForDailog(null);
		setSelectAll(true);
		setBooAgentPanel(false);
		setBooNotAgentPanel(false);
		setBooRenderCash(false);
		setBooAddmorefield(false);
		setBooRenderDataTable(false);
		setRoutingSetUpDetailsTableForCash(null);
		bankAppList.clear();
	}

	public void clearFields(){
		setCountryId(null);
		setRoutingCountryId(null);
		setRoutingbankId(null);
		setRoutingcurrencyId(null);
		setRoutingDeliveryId(null);
		setRoutingRemittanceId(null);
		setRoutingServiceId(null);
		setRoutingStatus(null);
		setBranchApplicability(null);
		setRoutingagent(null);
		setServiceName(null);
		setRemittanceName(null);
		setPkRoutingHeaderId(null);
		setDeliveryName(null);
		setDisableDelivery(false);
		setDisableRemmitance(false);
		setDisableEditButton(false);
		setCountryIdForDailog(null);
		setRoutingcurrencyIdForDailog(null);
		setRoutingServiceIdForDailog(null);
		setBooAgentPanel(false);
		setBooNotAgentPanel(false);
		setBooRenderCash(false);
		setBooAddmorefield(false);
		setBooRenderDataTable(false);
		setRoutingSetUpDetailsTableForCash(null);
		bankAppList.clear();
		setSerchBranchName(null);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/RoutingSetup.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//clearing only non searchable
	public void clearnonsearchable(){
		setRoutingCountryId(null);
		setRoutingbankId(null);
		setRoutingStatus(null);
		setBranchApplicability(null);
		setRoutingagent(null);
		bankAppList.clear();
	}

	//clearing only non searchable and Routing Country Setted for EFT
	public void clearnonsearchableExceptCountry(){
		setRoutingbankId(null);
		setRoutingStatus(null);
		setBranchApplicability(null);
		setRoutingagent(null);
	}

	public void populateCountryList(){
		List<CountryMasterDesc>  countryList = generalService.getCountryList(sessionManage.getLanguageId());
		setBankCountryList(countryList);
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	//method called from menubar - navigation
	public void routingSetupNavigation() {
		setSerchBranchName(null);
		clear();

		if(routingSetUpDetailsTable.size()!=0){
			routingSetUpDetailsTable.clear();
		}

		populateCountryList();
		routingSetUpNewDetailsTable.clear();
		//	setBooRenderSelect(true);
		setBooRenderAdd(false);
		setBooRenderDataTable(false);
		setBooRenderview(false);
		setBooRenderApprovalDataTable(false);
		setBooRenderDataTableApprovalExit(false);

		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionManage.getCountryId(), sessionManage.getUserType(), sessionManage.getUserName(), "RoutingSetup.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/RoutingSetup.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//exit to Home Page
	public void exit() {
		clear();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//remove details from DataTable 
	public void removeStandardInstrn(RoutingSetUpDetailsDataTable bean) {

		if(bean.getRoutingSetUpHeaderId()!=null){

			if(bean.getBranchApplicability().equalsIgnoreCase(Constants.SPECIFIC)){

				if(bean.getRoutingStatus().equalsIgnoreCase(Constants.ACTIVE)){
					setStatusDelete(true);
					detailsBranchData(bean);
					setStatusDelete(false);
					for (RoutingSetUpDetailsDialogCheckBox deleteRD : popupinDataTable) {
						if(deleteRD.getRoutingSetupDetailsId()!=null){
							iroutingSetUpDetailsService.deleteRoutingDetails(deleteRD.getRoutingSetupDetailsId(),sessionManage.getUserName());
						}else{
							popupinDataTable.remove(deleteRD);
						}
					}

					for(int i=0; i<routingheaderData.size();i++){

						if(bean.getRoutingSetUpHeaderId().compareTo(routingheaderData.get(i).getRoutingHeaderId())==0){
							RoutingHeader routingdetails = routingheaderData.get(i);
							routingheaderData.remove(i);
							routingdetails.setIsActive(Constants.No);
							iroutingSetUpDetailsService.saveRoutingHeader(routingdetails);
						}
					}

					RequestContext.getCurrentInstance().execute("deleted.show();");
				}else{
					RequestContext.getCurrentInstance().execute("errormsg.show();");
				}

			}else{
				for(int i=0; i<routingheaderData.size();i++){

					if(bean.getRoutingSetUpHeaderId().compareTo(routingheaderData.get(i).getRoutingHeaderId())==0){
						RoutingHeader routingdetails = routingheaderData.get(i);
						routingheaderData.remove(i);
						routingdetails.setIsActive(Constants.No);
						iroutingSetUpDetailsService.saveRoutingHeader(routingdetails);
					}
				}

				RequestContext.getCurrentInstance().execute("deleted.show();");
			}
			routingSetUpDetailsTable.remove(bean);
		}else{
			routingSetUpDetailsTable.remove(bean);
			routingSetUpNewDetailsTable.remove(bean);
			RequestContext.getCurrentInstance().execute("deleted.show();");
		}
		//again search happen
		searchingRecordsinDataBase();
	}

	//branch refreshing according to country and bank selection
	public void clearbranchApplicabilty(){
		setBranchApplicability(null);
		setRoutingStatus(null);
	}

	//on select of service code
	public void servicecodedeclaration(){
		servicedatafromdb.clear();
		deliverydataBasedonService.clear();
		setRoutingRemittanceId(null);
		setRoutingDeliveryId(null);
		setDeliveryName(null);
		setRemittanceName(null);

		setBooAgentPanel(false);
		setBooNotAgentPanel(false);

		routingSetUpDetailsTable.clear();
		routingSetUpNewDetailsTable.clear();
		routingAgentList.clear();

		setBooRenderDataTable(false);
		setBooRenderCash(false);

		List<RemittanceModeDescription> remittancedata = new ArrayList<RemittanceModeDescription>();
		List<DeliveryModeDesc> deliverydata = new ArrayList<DeliveryModeDesc>();

		//setting service name in the datatable on select of service from form
		for (ServiceMasterDesc serviceMasterDesc : lstofservice) {
			if(getRoutingServiceId().compareTo(serviceMasterDesc.getServiceMaster().getServiceId())==0){
				setServiceName(serviceMasterDesc.getLocalServiceDescription());
			}

		}

		if(getServiceName().equalsIgnoreCase(Constants.CASHNAME))
		{
			setBooRenderCash(false);
			setBooAgentPanel(true);
			setBooNotAgentPanel(false);
		}else
		{
			setBooRenderCash(true);
			setBooAgentPanel(false);
			setBooNotAgentPanel(true);
		}

		// to get remittance details based on service
		remittancedata =  iroutingSetUpDetailsService.getRemittanceModeMaster(sessionManage.getLanguageId(), getRoutingServiceId());

		//remittancedata = ibankServiceRuleMasterService.getRemittance(sessionManage.getCountryId(),getCountryId(),sessionManage.getLanguageId());

		if(remittancedata.size()!=0){
			setServicedatafromdb(remittancedata);
		}else{
			RequestContext.getCurrentInstance().execute("noremittance.show();");
		}

		// to get the delivery details based on service
		deliverydata = iroutingSetUpDetailsService.getDeliveryMode(sessionManage.getLanguageId(), getRoutingServiceId());

		//List<DeliveryModeDesc> deliveryList = ibankServiceRuleMasterService.getDeliverFromRemittance(sessionManage.getCountryId(),getCountryId(),getRoutingRemittanceId(),sessionManage.getLanguageId());


		if(deliverydata.size()!=0){
			setDeliverydataBasedonService(deliverydata);
		}else{
			RequestContext.getCurrentInstance().execute("nodelivery.show();");
		}

		if(getServiceName()!=null && getServiceName().equalsIgnoreCase(Constants.CASHNAME) || getServiceName().equalsIgnoreCase(Constants.DDNAME)){
			setDisableDelivery(false);
			setDisableRemmitance(false);
			clearnonsearchable();

		}else if(getServiceName()!=null && getServiceName().equalsIgnoreCase(Constants.EFTNAME) || getServiceName().equalsIgnoreCase(Constants.TTNAME)) {

			setDisableDelivery(true);
			setDisableRemmitance(true);
			setRoutingCountryId(getCountryId());

		}else{
			setDisableDelivery(true);
			setDisableRemmitance(true);
			clearnonsearchable();
		}

		//combination Search of 5 Components
		searchingRecordsinDataBase();
		getBankAccordingToBankIndicator();

	}

	private boolean editClick=false;

	public boolean isEditClick() {
		return editClick;
	}

	public void setEditClick(boolean editClick) {
		this.editClick = editClick;
	}

	private BigDecimal pkRoutingHeaderId;

	public BigDecimal getPkRoutingHeaderId() {
		return pkRoutingHeaderId;
	}

	public void setPkRoutingHeaderId(BigDecimal pkRoutingHeaderId) {
		this.pkRoutingHeaderId = pkRoutingHeaderId;
	}


	//edit selected record in dataTable

	public void editRecord(RoutingSetUpDetailsDataTable recordedit){

		setDisableEditButton(true);
		//setEditClick(true);

		if(recordedit.getRoutingSetUpHeaderId() !=null){
			recordedit.setIsCheck(true);
		}

		setPkRoutingHeaderId(recordedit.getRoutingSetUpHeaderId());
		setCreatedBy(recordedit.getCreatedBy());
		setCreatedDate(recordedit.getCreatedDate());
		setModifiedBy(recordedit.getModifiedBy());
		setModifiedDate(recordedit.getModifiedDate());
		setApprovedBy(recordedit.getApprovedBy());
		setApprovedDate(recordedit.getApprovedDate());
		setRoutingStatus(recordedit.getRoutingStatus());
		populateCountryList();
		setDynamicLabelForActivateDeactivate(recordedit.getDynamicLabelForActivateDeactivate());
		if(getServiceName()!=null && getServiceName().equalsIgnoreCase(Constants.EFTNAME)){
			setRoutingServiceId(recordedit.getRoutingServiceId());
			setCountryId(recordedit.getCountryId());
			setRoutingcurrencyId(recordedit.getRoutingcurrencyId());
			setRoutingCountryId(recordedit.getRoutingcountryId());
			setRoutingbankId(recordedit.getRoutingbankId());
			setBranchApplicability(recordedit.getBranchApplicability());
			setRoutingStatus(recordedit.getRoutingStatus());
			displayAgentBasedonServiceProvider();
			setRoutingagent(recordedit.getAgentId());
		}else{
			setRoutingServiceId(recordedit.getRoutingServiceId());
			setCountryId(recordedit.getCountryId());
			setRoutingcurrencyId(recordedit.getRoutingcurrencyId());
			setRoutingCountryId(recordedit.getRoutingcountryId());
			setRoutingbankId(recordedit.getRoutingbankId());
			setBranchApplicability(recordedit.getBranchApplicability());
			setRoutingStatus(recordedit.getRoutingStatus());
			displayAgentBasedonServiceProvider();
			setRoutingagent(recordedit.getAgentId());
		}
		//set to branches
		specificCheckinginDataBase();
		getBankAccordingToBankIndicator();

		if(recordedit.getRoutingSetUpHeaderId()!=null){
			for(int i=0;i<routingSetUpDetailsTable.size();i++){
				RoutingSetUpDetailsDataTable editrecord = routingSetUpDetailsTable.get(i);
				if(editrecord.getRoutingSetUpHeaderId().compareTo(recordedit.getRoutingSetUpHeaderId())==0){
					routingSetUpDetailsTable.remove(i);

				}
			}
		}else{
			routingSetUpDetailsTable.remove(recordedit);
			routingSetUpNewDetailsTable.remove(recordedit);
		}

		setEditClick(false);

		if(routingSetUpDetailsTable.size()<=0){
			setBooRenderDataTable(false);
		}


		if(recordedit.getRoutingRemittanceId()!=null){
			setServicedatafromdb(iroutingSetUpDetailsService.getRemittanceModeMaster(sessionManage.getLanguageId(), recordedit.getRoutingServiceId()));
			setRoutingRemittanceId(recordedit.getRoutingRemittanceId());
		}
		if(recordedit.getRoutingDeliveryId()!=null){
			setDeliverydataBasedonService(iroutingSetUpDetailsService.getDeliveryMode(sessionManage.getLanguageId(), recordedit.getRoutingServiceId()));
			setRoutingDeliveryId(recordedit.getRoutingDeliveryId());
		}
		setBooRenderAdd(true);


		if(getServiceName()!=null && getServiceName().equalsIgnoreCase(Constants.CASHNAME)  || getServiceName()!=null && getServiceName().equalsIgnoreCase(Constants.DDNAME) ){
			setDisableDelivery(false);
			setDisableRemmitance(false);
		}else{
			setDisableDelivery(true);
			setDisableRemmitance(true);
		}
		
		/*if(getBranchApplicability() != null && getBranchApplicability().equalsIgnoreCase(Constants.SPECIFIC)){
			specificCheckinginDataBase();
		}*/

	}

	//After saving popup the details
	public void afterFinalSave(){
		clear();
		setSerchBranchName(null);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/RoutingSetup.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}

		setBooRenderDataTable(false);

	}

	//Approval Started 17/06/2015 @ koti
	public void routingSetupApprovalNavigation(){
		setBooRenderview(true);
		setBooRenderApprovalDataTable(false);
		setBooRenderDataTable(false);
		setBooRenderDataTableApprovalExit(false);
		setBooRenderAdd(false);
		setBooRenderDataTable(false);
		setCountryId(null);
		setRoutingCountryId(null);
		setRoutingcurrencyId(null);
		setRoutingDeliveryId(null);
		setRoutingRemittanceId(null);
		setRoutingServiceId(null);
		setServiceName(null);
		setRemittanceName(null);
		setDeliveryName(null);
		routingSetUpDetailsTable.clear();
		routingAgentList.clear();
		populateCountryList();
		getServiceCodeFromDB();
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionManage.getCountryId(), sessionManage.getUserType(), sessionManage.getUserName(), "RoutingSetupApprovel.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/RoutingSetupApprovel.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//add Records Data Table For Approval
	public void addRecordsToDataTableToApproval(){
		
		dialogselectbankbranch.clear();

		if(getServiceName()!=null && getServiceName().equalsIgnoreCase(Constants.CASHNAME))
		{
			setBooAgentPanel(true);
			setBooNotAgentPanel(false);
			setBooRenderApprovalDataTable(false);
			routingAgentList.clear();
			List<RoutingDetails> lstRoutingDetails=iroutingSetUpDetailsService.toFetchAllApprovalRecordsFromDetails(getCountryId(),getRoutingcurrencyId(),getRoutingServiceId(),getRoutingRemittanceId(),getRoutingDeliveryId());
			if(lstRoutingDetails.size() !=0){
				for (RoutingDetails routingDetails : lstRoutingDetails) {
					RoutingSetUpDetailsDialogCheckBox routingcheckbox=new RoutingSetUpDetailsDialogCheckBox();  
					routingcheckbox.setRoutingSetupDetailsId(routingDetails.getRoutingDetailsId());
					routingcheckbox.setRoutingSetupHeaderId(routingDetails.getExRountingHeaderId().getRoutingHeaderId());
					routingcheckbox.setBankBranchId(routingDetails.getExBankBranchId().getBankBranchId());
					routingcheckbox.setBankId(routingDetails.getExRoutingBankId().getBankId());
					routingcheckbox.setBankName(generalService.getBankName(routingDetails.getExRoutingBankId().getBankId()).toString());
					routingcheckbox.setCountryId(routingDetails.getExRoutingCountryId().getCountryId());
					routingcheckbox.setCountryName(generalService.getCountryName(sessionManage.getLanguageId(), routingDetails.getExCountryId().getCountryId()));
					routingcheckbox.setCurrencyId(routingDetails.getExCurrenyId().getCurrencyId());
					routingcheckbox.setCurrencyName(routingDetails.getExCurrenyId().getCurrencyName());
					routingcheckbox.setBankbranchCode(routingDetails.getExBankBranchId().getBranchCode());
					routingcheckbox.setBranchFullName(routingDetails.getExBankBranchId().getBranchFullName());
					routingcheckbox.setRoutingServciceId(routingDetails.getExServiceId().getServiceId());
					routingcheckbox.setRoutingServiceName(iroutingSetUpDetailsService.getServiceCode(sessionManage.getLanguageId(),routingDetails.getExServiceId().getServiceId()).get(0).getLocalServiceDescription());
					try {
						if (routingDetails.getExDeliveryModeId().getDeliveryModeId() != null) {
							routingcheckbox.setRoutingDeliveryId(routingDetails.getExDeliveryModeId().getDeliveryModeId());
							routingcheckbox.setRoutingDeliveryMode(iroutingSetUpDetailsService.getDeliveryModeDetails(sessionManage.getLanguageId(), routingDetails.getExDeliveryModeId().getDeliveryModeId()).get(0).getEnglishDeliveryName());
						}
						if (routingDetails.getExRemittanceModeId().getRemittanceModeId() != null) {
							routingcheckbox.setRoutingRemittanceId(routingDetails.getExRemittanceModeId().getRemittanceModeId());
							routingcheckbox.setRoutingRemittanceMode(iroutingSetUpDetailsService.getRemittanceMode(sessionManage.getLanguageId(), routingDetails.getExRemittanceModeId().getRemittanceModeId()).get(0).getRemittanceDescription());
						}
					} catch (Exception ne) {
						log.error(ne.getMessage());
					}

					routingcheckbox.setRoutingcountryId(routingDetails.getExRoutingCountryId().getCountryId());
					routingcheckbox.setRoutingcountryName(generalService.getCountryName(sessionManage.getLanguageId(), routingDetails.getExRoutingCountryId().getCountryId()));
					routingcheckbox.setRoutingbankId(routingDetails.getExRoutingBankId().getBankId());
					routingcheckbox.setRoutingbankName(generalService.getBankName(routingDetails.getExRoutingBankId().getBankId()).toString());
					routingcheckbox.setCreatedBy(routingDetails.getCreatedBy());
					routingcheckbox.setCreatedDate(routingDetails.getCreatedDate());
					routingcheckbox.setModifiedBy(routingDetails.getModifiedBy());
					routingcheckbox.setModifiedDate(routingDetails.getModifiedDate());
					routingcheckbox.setIsActive(routingDetails.getIsActive());
					//routingcheckbox.setAgentBranchId(routingDetails.getAgentBankId());

					//routingcheckbox.setAgentId(routingDetails.getExagentInd()==null ? null : routingDetails.getExagentInd().getBankIndicatorId());
					if(routingDetails.getIsActive().compareTo(Constants.Yes)==0){
						routingcheckbox.setSelectRecord(Boolean.TRUE);
						routingcheckbox.setRoutingStatus(Constants.ACTIVE);
					}else{
						routingcheckbox.setSelectRecord(Boolean.FALSE);
						routingcheckbox.setRoutingStatus(Constants.IN_ACTIVE);
					}
					List<RoutingHeader> lstRoutingHeaders=iroutingSetUpDetailsService.toFetchHeadervalues(routingDetails.getExRountingHeaderId().getRoutingHeaderId());
					if(lstRoutingHeaders.size() !=0){
						for(RoutingHeader routingHeaderListValue : routingheaderData){
							RoutingSetUpDetailsDialogCheckBox routingdetails=new RoutingSetUpDetailsDialogCheckBox();
							routingdetails.setRoutingSetupHeaderId(routingHeaderListValue.getRoutingHeaderId());
							routingdetails.setApplicationCountryId(routingHeaderListValue.getExApplicationCountry().getCountryId());
							routingdetails.setCountryId(routingHeaderListValue.getExCountryId().getCountryId());
							routingdetails.setCountryName(generalService.getCountryName(sessionManage.getLanguageId(), routingHeaderListValue.getExCountryId().getCountryId()));
							routingdetails.setCurrencyId(routingHeaderListValue.getExCurrenyId().getCurrencyId());
							routingdetails.setRoutingcurrencyName(generalService.getCurrencyName(routingHeaderListValue.getExCurrenyId().getCurrencyId()));
							routingdetails.setRoutingServciceId(routingHeaderListValue.getExServiceId().getServiceId());
							routingdetails.setRoutingServiceName(iroutingSetUpDetailsService.getServiceCode(sessionManage.getLanguageId(),routingHeaderListValue.getExServiceId().getServiceId()).get(0).getLocalServiceDescription());
							try{
								if(routingHeaderListValue.getExDeliveryModeId().getDeliveryModeId()!=null){
									routingdetails.setRoutingDeliveryId(routingHeaderListValue.getExDeliveryModeId().getDeliveryModeId());
									routingdetails.setRoutingDeliveryMode(iroutingSetUpDetailsService.getDeliveryModeDetails(sessionManage.getLanguageId(),routingHeaderListValue.getExDeliveryModeId().getDeliveryModeId()).get(0).getEnglishDeliveryName());
								}
								if(routingHeaderListValue.getExRemittanceModeId().getRemittanceModeId()!=null){
									routingdetails.setRoutingRemittanceId(routingHeaderListValue.getExRemittanceModeId().getRemittanceModeId());
									routingdetails.setRoutingRemittanceMode(iroutingSetUpDetailsService.getRemittanceMode(sessionManage.getLanguageId(),routingHeaderListValue.getExRemittanceModeId().getRemittanceModeId()).get(0).getRemittanceDescription());
								}
							}catch(Exception ne){
								log.error(ne.getMessage());
							}

							routingdetails.setRoutingcountryId(routingHeaderListValue.getExRoutingCountryId().getCountryId());
							routingdetails.setRoutingcountryName(generalService.getCountryName(sessionManage.getLanguageId(), routingHeaderListValue.getExRoutingCountryId().getCountryId()));
							routingdetails.setRoutingbankId(routingHeaderListValue.getExRoutingBankId().getBankId());
							routingdetails.setRoutingbankName(generalService.getBankName(routingHeaderListValue.getExRoutingBankId().getBankId()).toString());


							if(routingHeaderListValue.getBranchApplicability()!=null && routingHeaderListValue.getBranchApplicability().equalsIgnoreCase(Constants.S)){
								routingdetails.setBranchApplicability(Constants.SPECIFIC);
							}else{
								routingdetails.setBranchApplicability(routingHeaderListValue.getBranchApplicability());
							}

							if(routingHeaderListValue.getIsActive().compareTo(Constants.Yes)==0){
								routingdetails.setRoutingStatus(Constants.ACTIVE);
							}else{
								routingdetails.setRoutingStatus(Constants.IN_ACTIVE);
							}				

							routingdetails.setModifiedBy(routingHeaderListValue.getModifiedBy());
							routingdetails.setCreatedBy(routingHeaderListValue.getCreatedBy());
							routingdetails.setCreatedDate(routingHeaderListValue.getCreatedDate());  
						}
					}
					routingAgentList.add(routingcheckbox);	  
				} 
			}else{
				RequestContext.getCurrentInstance().execute("noRecord.show();");
				setBooRenderDataTableApprovalExit(false);
				setBooRenderApprovalDataTable(false);
				setBooRenderDataTable(false);
				setBooRenderAdd(false);
				setBooRenderDataTable(false);
				return;	  
			}
		}else{
			routingSetUpDetailsTable.clear();
			List<RoutingHeader> routingheaderLT = iroutingSetUpDetailsService.getAllRecordsToFetchForApproval(getCountryId(),getRoutingcurrencyId(),getRoutingServiceId(),getRoutingRemittanceId(),getRoutingDeliveryId());

			if(routingheaderLT.size() > 0){
				setBooRenderApprovalDataTable(true);
				setBooRenderDataTableApprovalExit(true);
				setRoutingheaderData(routingheaderLT);

				for(RoutingHeader routingHeaderListValue : routingheaderData){
					//	if(!(routingHeaderListValue.getModifiedBy() == null ?  routingHeaderListValue.getCreatedBy() : routingHeaderListValue.getModifiedBy()).equalsIgnoreCase(sessionManage.getUserName())){
					RoutingSetUpDetailsDataTable routingdetails = new RoutingSetUpDetailsDataTable();

					//setPkRoutingHeaderId(routingHeaderListValue.getRoutingHeaderId());
					routingdetails.setRoutingSetUpHeaderId(routingHeaderListValue.getRoutingHeaderId());
					routingdetails.setApplicationCountryId(routingHeaderListValue.getExApplicationCountry().getCountryId());
					routingdetails.setCountryId(routingHeaderListValue.getExCountryId().getCountryId());
					routingdetails.setCountryName(generalService.getCountryName(sessionManage.getLanguageId(), routingHeaderListValue.getExCountryId().getCountryId()));
					routingdetails.setRoutingcurrencyId(routingHeaderListValue.getExCurrenyId().getCurrencyId());
					routingdetails.setRoutingcurrencyName(generalService.getCurrencyName(routingHeaderListValue.getExCurrenyId().getCurrencyId()));
					routingdetails.setRoutingServiceId(routingHeaderListValue.getExServiceId().getServiceId());
					routingdetails.setRoutingServiceCode(iroutingSetUpDetailsService.getServiceCode(sessionManage.getLanguageId(),routingHeaderListValue.getExServiceId().getServiceId()).get(0).getLocalServiceDescription());
					try{
						if(routingHeaderListValue.getExDeliveryModeId()!=null){
							routingdetails.setRoutingDeliveryId(routingHeaderListValue.getExDeliveryModeId().getDeliveryModeId());
							routingdetails.setRoutingDeliveryMode(iroutingSetUpDetailsService.getDeliveryModeDetails(sessionManage.getLanguageId(),routingHeaderListValue.getExDeliveryModeId().getDeliveryModeId()).get(0).getEnglishDeliveryName());
						}
						if(routingHeaderListValue.getExRemittanceModeId()!=null){
							routingdetails.setRoutingRemittanceId(routingHeaderListValue.getExRemittanceModeId().getRemittanceModeId());
							routingdetails.setRoutingRemittanceMode(iroutingSetUpDetailsService.getRemittanceMode(sessionManage.getLanguageId(),routingHeaderListValue.getExRemittanceModeId().getRemittanceModeId()).get(0).getRemittanceDescription());
						}
					}catch(Exception ne){
						log.error(ne.getMessage());
					}

					routingdetails.setRoutingcountryId(routingHeaderListValue.getExRoutingCountryId().getCountryId());
					routingdetails.setRoutingcountryName(generalService.getCountryName(sessionManage.getLanguageId(), routingHeaderListValue.getExRoutingCountryId().getCountryId()));
					routingdetails.setRoutingbankId(routingHeaderListValue.getExRoutingBankId().getBankId());
					routingdetails.setRoutingbankName(generalService.getBankName(routingHeaderListValue.getExRoutingBankId().getBankId()).toString());


					if(routingHeaderListValue.getBranchApplicability()!=null && routingHeaderListValue.getBranchApplicability().equalsIgnoreCase(Constants.S)){
						routingdetails.setBranchApplicability(Constants.SPECIFIC);
					}else{
						routingdetails.setBranchApplicability(routingHeaderListValue.getBranchApplicability());
					}

					if(routingHeaderListValue.getIsActive().compareTo(Constants.Yes)==0){
						routingdetails.setRoutingStatus(Constants.ACTIVE);
					}else{
						routingdetails.setRoutingStatus(Constants.IN_ACTIVE);
					}				

					routingdetails.setModifiedBy(routingHeaderListValue.getModifiedBy());
					routingdetails.setCreatedBy(routingHeaderListValue.getCreatedBy());
					routingdetails.setCreatedDate(routingHeaderListValue.getCreatedDate());

					routingSetUpDetailsTable.add(routingdetails);


					if (getRoutingSetUpDetailsTable().size() != 0) {
						// Checking five fields in DB
						routingDetailsList = iroutingSetUpDetailsService.getAllRecordsOfRoutingDetails(routingHeaderListValue.getRoutingHeaderId());

						if(routingDetailsList.size()!=0){
							for (RoutingDetails routingDetails : routingDetailsList) {
								RoutingSetUpDetailsDialogCheckBox routingcheckbox = new RoutingSetUpDetailsDialogCheckBox();
								routingcheckbox.setRoutingSetupDetailsId(routingDetails.getRoutingDetailsId());
								routingcheckbox.setRoutingSetupHeaderId(routingDetails.getExRountingHeaderId().getRoutingHeaderId());
								routingcheckbox.setBankBranchId(routingDetails.getExBankBranchId().getBankBranchId());
								routingcheckbox.setBankId(routingDetails.getExRoutingBankId().getBankId());
								routingcheckbox.setCountryId(routingDetails.getExRoutingCountryId().getCountryId());
								routingcheckbox.setBankbranchCode(routingDetails.getExBankBranchId().getBranchCode());
								routingcheckbox.setBranchFullName(routingDetails.getExBankBranchId().getBranchFullName());
								//routingcheckbox.setAgentId(routingDetails.getExagentInd()==null ? null : routingDetails.getExagentInd().getBankIndicatorId());
								if(routingDetails.getIsActive().compareTo(Constants.Yes)==0){
									routingcheckbox.setSelectRecord(Boolean.TRUE);
									routingcheckbox.setRoutingStatus(Constants.ACTIVE);
								}else{
									routingcheckbox.setSelectRecord(Boolean.FALSE);
									routingcheckbox.setRoutingStatus(Constants.IN_ACTIVE);
								}
								dialogselectbankbranch.add(routingcheckbox);
							}

						}
					}	
				}

			}else{
				RequestContext.getCurrentInstance().execute("noRecord.show();");
				setBooRenderDataTableApprovalExit(false);
				setBooRenderApprovalDataTable(false);
				setBooRenderDataTable(false);
				setBooRenderAdd(false);
				setBooRenderDataTable(false);
				return;
			}
		}



		/*else{
			  RequestContext.getCurrentInstance().execute("noRecord.show();");
				setBooRenderDataTableApprovalExit(false);
				setBooRenderApprovalDataTable(false);
				setBooRenderDataTable(false);
				setBooRenderAdd(false);
				setBooRenderDataTable(false);
				return;
		}*/}

	//click on OK Button
	public void clickOnOKSaveApprove(){
		//addRecordsToDataTableToApproval();

		if(routingSetUpDetailsTable!=null && routingSetUpDetailsTable.size()>0){
			setBooRenderDataTableApprovalExit(true);
			setBooRenderApprovalDataTable(true);
		}else{
			setBooRenderDataTableApprovalExit(false);
			setBooRenderApprovalDataTable(false);
		}
		setBooRenderAdd(false);
		setBooRenderDataTable(false);
		setCountryId(null);
		setRoutingCountryId(null);
		setRoutingbankId(null);
		setRoutingcurrencyId(null);
		setRoutingDeliveryId(null);
		setRoutingRemittanceId(null);
		setRoutingServiceId(null);
		setRoutingStatus(null);
		setBranchApplicability(null);
		setRoutingagent(null);
		setServiceName(null);
		setRemittanceName(null);
		setDeliveryName(null);
		setDisableDelivery(false);
		setDisableRemmitance(false);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/RoutingSetupApprovel.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void displayView(){

		resetFilters("form1:agentDataTable1");

		setRoutingSetUpDetailsTableForCash(null);
		getServiceCodeFromDB();;

		for (ServiceMasterDesc serviceMasterDesc : lstofservice) {
			if(getRoutingServiceId().compareTo(serviceMasterDesc.getServiceMaster().getServiceId())==0){
				setServiceName(serviceMasterDesc.getLocalServiceDescription());
			}

		}

		if(getServiceName()!=null && getServiceName().equalsIgnoreCase(Constants.CASHNAME))
		{
			setBooAgentPanel(true);
			setBooNotAgentPanel(false);
			routingAgentList.clear();

			if(getRoutingCountryId()== null &&  getRoutingbankId()==null)
			{
				RequestContext.getCurrentInstance().execute("selectRouting.show();");
				return;
			}

			if(getCountryId()!=null && getRoutingcurrencyId()!=null && getRoutingServiceId()!=null && getRoutingbankId()!=null && getRoutingCountryId()!=null){
				//Checking five fields in DB
				List<RoutingHeader> routingheaderList = iroutingSetUpDetailsService.getAllRecordsIfExist(getCountryId(), getRoutingcurrencyId(), getRoutingServiceId(), getRoutingRemittanceId(), getRoutingDeliveryId(), getRoutingCountryId(), getRoutingbankId());

				List<RoutingSetUpDetailsDataTable> routingSetUpDetailsTableForCash = new ArrayList<RoutingSetUpDetailsDataTable>();

				for(RoutingHeader routingHeader :routingheaderList)
				{


					RoutingSetUpDetailsDataTable	routingSetUpDetailsDataTable = new RoutingSetUpDetailsDataTable();
					routingSetUpDetailsDataTable.setRoutingSetUpHeaderId(routingHeader.getRoutingHeaderId());
					routingSetUpDetailsDataTable.setCountryId(routingHeader.getExCountryId().getCountryId());
					routingSetUpDetailsDataTable.setRoutingcurrencyId(routingHeader.getExCurrenyId().getCurrencyId());
					routingSetUpDetailsDataTable.setRoutingServiceId(routingHeader.getExServiceId().getServiceId());
					if(routingHeader.getExDeliveryModeId()!=null)
					{
						routingSetUpDetailsDataTable.setRoutingDeliveryId(routingHeader.getExDeliveryModeId().getDeliveryModeId());
					}

					if(routingHeader.getExRemittanceModeId()!=null)
					{
						routingSetUpDetailsDataTable.setRoutingRemittanceId(routingHeader.getExRemittanceModeId().getRemittanceModeId());
					}

					routingSetUpDetailsDataTable.setRoutingcountryId(routingHeader.getExRoutingCountryId().getCountryId());
					routingSetUpDetailsDataTable.setRoutingbankId(routingHeader.getExRoutingBankId().getBankId());
					routingSetUpDetailsDataTable.setCreatedBy(routingHeader.getCreatedBy());
					routingSetUpDetailsDataTable.setCreatedDate(routingHeader.getCreatedDate());

					routingSetUpDetailsTableForCash.add(routingSetUpDetailsDataTable);

					List<RoutingDetails> lstRoutingDetails = iroutingSetUpDetailsService.getAllRecordsOfRoutingDetails(routingHeader.getRoutingHeaderId());

					//List<BankBranchView> lstBankBranchView= iBeneficaryCreation.getBranchListfromView(routingHeader.getExRoutingBankId().getBankId()) ;
					List<BankBranchView> lstBankBranchView= iBeneficaryCreation.getBranchListfromViewByCountryBank(routingHeader.getExCountryId().getCountryId(),routingHeader.getExRoutingBankId().getBankId());

					List<PopulateDataWithCode> lstAgentsMasterBanks = new ArrayList<PopulateDataWithCode>(); 
					List<PopulateDataWithCode> lstofAllCorrAgentsBeneServicePBanks = iroutingSetUpDetailsService.FetchAllBankListbyBankCountry(routingHeader.getExRoutingCountryId().getCountryId());
					if(lstofAllCorrAgentsBeneServicePBanks != null && !lstofAllCorrAgentsBeneServicePBanks.isEmpty()){
						lstAgentsMasterBanks.addAll(lstofAllCorrAgentsBeneServicePBanks);
					}

					if(lstRoutingDetails.size()>0)
					{
						List<BigDecimal> duplicateAgentBankId = new ArrayList<BigDecimal>();
						List<BankBranchView> lstAgentBankBranchView = new ArrayList<BankBranchView>();

						List<BigDecimal> lstBankBranchDeletedRec = new ArrayList<BigDecimal>();
						for(RoutingDetails routingDetails :lstRoutingDetails)
						{
							if(routingDetails.getExBankBranchId() != null && !routingDetails.getExBankBranchId().getIsactive().equalsIgnoreCase(Constants.Yes)){
								lstBankBranchDeletedRec.add(routingDetails.getExBankBranchId().getBankBranchId());
							}
						}

						if(lstBankBranchDeletedRec.size() != 0){
							List<BankBranch> lstBranchDeletedRec =  iroutingSetUpDetailsService.FetchAllBankBranchesTaggedDelete(lstBankBranchDeletedRec);
							if(lstBranchDeletedRec.size() != 0){

								for (BankBranch bankBranch : lstBranchDeletedRec) {
									BankBranchView addingDRec = new BankBranchView();
									addingDRec.setAddress1(bankBranch.getAddress1());
									addingDRec.setAddress2(bankBranch.getAddress2());
									addingDRec.setBankBranchId(bankBranch.getBankBranchId());
									addingDRec.setBankId(bankBranch.getExBankMaster().getBankId());
									addingDRec.setBranchCode(bankBranch.getBranchCode());
									addingDRec.setBranchFullName(bankBranch.getBranchFullName());
									if(bankBranch.getFsCityMaster() != null){
										addingDRec.setCityId(bankBranch.getFsCityMaster().getCityId());
										//addingDRec.setCityName(bankBranch.getFsCityMaster().getCityCode());
									}
									if(bankBranch.getFsDistrictMaster() != null){
										addingDRec.setDistrictId(bankBranch.getFsDistrictMaster().getDistrictId());
										//addingDRec.setDistrictName(bankBranch.getFsDistrictMaster().getDistrictCode());
									}
									if(bankBranch.getFsStateMaster() != null){
										addingDRec.setStateId(bankBranch.getFsStateMaster().getStateId());
										//addingDRec.setStateName(bankBranch.getFsStateMaster().getStateCode());
									}
									addingDRec.setIfscCode(bankBranch.getBranchIfsc());
									addingDRec.setSwift(bankBranch.getSwiftBic());
									addingDRec.setZipcode(bankBranch.getZipCode());
									lstBankBranchView.add(addingDRec);
								}
							}
						}


						for(RoutingDetails routingDetails :lstRoutingDetails)
						{
							RoutingSetUpDetailsDialogCheckBox routingAgentObj = new RoutingSetUpDetailsDialogCheckBox();

							routingAgentObj.setRoutingSetupHeaderId(routingHeader.getRoutingHeaderId());
							routingAgentObj.setLstRoutingSetupBankDetails(lstAgentsMasterBanks);
							routingAgentObj.setLstBankBranchView(lstBankBranchView);

							routingAgentObj.setBankId(routingDetails.getAgentBankId());

							if(!duplicateAgentBankId.contains(routingDetails.getAgentBankId())){
								duplicateAgentBankId.add(routingDetails.getAgentBankId());
								//lstAgentBankBranchView = iBeneficaryCreation.getBranchListfromView(routingDetails.getAgentBankId()) ;
								lstAgentBankBranchView = iBeneficaryCreation.getBranchListfromViewByCountryBank(routingDetails.getExRoutingCountryId().getCountryId(),routingDetails.getAgentBankId());
							}
							routingAgentObj.setAgentBranchId(routingDetails.getAgentBranchID());
							routingAgentObj.setLstAgentBankBranchView(lstAgentBankBranchView);
							if(routingDetails.getExBankBranchId() != null && routingDetails.getExBankBranchId().getIsactive().equalsIgnoreCase(Constants.Yes)){
								routingAgentObj.setRoutingBranchId(routingDetails.getExBankBranchId().getBankBranchId());
								routingAgentObj.setRoutingbranchStatus(Constants.ACTIVE);
							}else{
								routingAgentObj.setRoutingBranchId(routingDetails.getExBankBranchId().getBankBranchId());
								routingAgentObj.setRoutingbranchStatus(Constants.IN_ACTIVE);
							}
							routingAgentObj.setRoutingSetupDetailsId(routingDetails.getRoutingDetailsId());
							routingAgentObj.setMappingRoutingBranch(routingDetails.getAgentBranchEmosMapCode());
							routingAgentObj.setCreatedBy(routingDetails.getCreatedBy());
							routingAgentObj.setCreatedDate(routingDetails.getCreatedDate());
							routingAgentObj.setModifiedBy(routingAgentObj.getModifiedBy());
							routingAgentObj.setModifiedDate(routingDetails.getModifiedDate());
							routingAgentObj.setApprovedBy(routingDetails.getApprovedBy());
							routingAgentObj.setApprovedDate(routingDetails.getApprovedDate());
							if(routingDetails.getIsActive().equalsIgnoreCase(Constants.Yes)){
								routingAgentObj.setDynamicLabelActivateDeActivate(Constants.DEACTIVATE);
								routingAgentObj.setDataTableStatus(Constants.APPROVED);
							}else if (routingDetails.getIsActive().equalsIgnoreCase(Constants.D)) {
								routingAgentObj.setDynamicLabelActivateDeActivate(Constants.ACTIVATE);
								routingAgentObj.setDataTableStatus(Constants.UNAPPROVED);
							}else if (routingDetails.getIsActive().equalsIgnoreCase(Constants.U)) {
								routingAgentObj.setDynamicLabelActivateDeActivate(Constants.PENDING_FOR_APPROVE);
								routingAgentObj.setDataTableStatus(Constants.UNAPPROVED);
							}
							if(routingDetails.getBranchApplicability() != null){

								if(routingDetails.getBranchApplicability().equalsIgnoreCase(Constants.S)){
									routingAgentObj.setBranchApplicability(Constants.SPECIFIC);
								}else{
									routingAgentObj.setBranchApplicability(Constants.ALL);
								}
							}
							routingAgentList.add(routingAgentObj);
						}

					}

				}
				setRoutingSetUpDetailsTableForCash(routingSetUpDetailsTableForCash);
				if(routingAgentList.size()>0)
				{
					setBooRenderDataTable(true);
				}else
				{
					setBooRenderDataTable(false);
				}

			}

		}else
		{

			setBooNotAgentPanel(true);
			setBooAgentPanel(false);
			setBooAddmorefield(false);

			routingSetUpDetailsTable.clear();
			dialogselectbankbranch.clear();

			if(getCountryId()!=null && getRoutingcurrencyId()!=null && getRoutingServiceId()!=null){
				//Checking five fields in DB
				List<RoutingHeader> routingheaderList = iroutingSetUpDetailsService.getAllRecordsFromDB(getCountryId(),getRoutingcurrencyId(),getRoutingServiceId());

				if(routingheaderList.size()!=0){
					setBooRenderDataTable(true);
					setRoutingheaderData(routingheaderList);

					for(RoutingHeader routingHeaderListValue : routingheaderData){

						RoutingSetUpDetailsDataTable routingdetails = new RoutingSetUpDetailsDataTable();

						//setPkRoutingHeaderId(routingHeaderListValue.getRoutingHeaderId());
						routingdetails.setRoutingSetUpHeaderId(routingHeaderListValue.getRoutingHeaderId());
						routingdetails.setApplicationCountryId(routingHeaderListValue.getExApplicationCountry().getCountryId());
						routingdetails.setCountryId(routingHeaderListValue.getExCountryId().getCountryId());
						routingdetails.setCountryName(generalService.getCountryName(sessionManage.getLanguageId(), routingHeaderListValue.getExCountryId().getCountryId()));
						routingdetails.setRoutingcurrencyId(routingHeaderListValue.getExCurrenyId().getCurrencyId());
						routingdetails.setRoutingcurrencyName(generalService.getCurrencyName(routingHeaderListValue.getExCurrenyId().getCurrencyId()));
						routingdetails.setRoutingServiceId(routingHeaderListValue.getExServiceId().getServiceId());
						routingdetails.setRoutingServiceCode(iroutingSetUpDetailsService.getServiceCode(sessionManage.getLanguageId(),routingHeaderListValue.getExServiceId().getServiceId()).get(0).getLocalServiceDescription());
						try{
							if(routingHeaderListValue.getExDeliveryModeId()!=null){
								routingdetails.setRoutingDeliveryId(routingHeaderListValue.getExDeliveryModeId().getDeliveryModeId());
								routingdetails.setRoutingDeliveryMode(iroutingSetUpDetailsService.getDeliveryModeDetails(sessionManage.getLanguageId(),routingHeaderListValue.getExDeliveryModeId().getDeliveryModeId()).get(0).getEnglishDeliveryName());
							}
							if(routingHeaderListValue.getExRemittanceModeId()!=null){	
								routingdetails.setRoutingRemittanceId(routingHeaderListValue.getExRemittanceModeId().getRemittanceModeId());
								routingdetails.setRoutingRemittanceMode(iroutingSetUpDetailsService.getRemittanceMode(sessionManage.getLanguageId(),routingHeaderListValue.getExRemittanceModeId().getRemittanceModeId()).get(0).getRemittanceDescription());
							}
						}catch(Exception ne){
							log.error(ne.toString());
						}
						routingdetails.setRoutingcountryId(routingHeaderListValue.getExRoutingCountryId().getCountryId());
						routingdetails.setRoutingcountryName(generalService.getCountryName(sessionManage.getLanguageId(), routingHeaderListValue.getExRoutingCountryId().getCountryId()));
						routingdetails.setRoutingbankId(routingHeaderListValue.getExRoutingBankId().getBankId());
						routingdetails.setRoutingbankName(generalService.getBankName(routingHeaderListValue.getExRoutingBankId().getBankId()).toString());
						if(routingHeaderListValue.getBranchApplicability()!=null  && routingHeaderListValue.getBranchApplicability().equalsIgnoreCase(Constants.S)){
							routingdetails.setBranchApplicability(Constants.SPECIFIC);
						}else{
							routingdetails.setBranchApplicability(routingHeaderListValue.getBranchApplicability());
						}

						if(routingHeaderListValue.getIsActive().equalsIgnoreCase(Constants.Yes)){
							routingdetails.setRoutingStatus(Constants.ACTIVE);
						}else{
							routingdetails.setRoutingStatus(Constants.IN_ACTIVE);
						}				

						routingdetails.setCreatedBy(routingHeaderListValue.getCreatedBy());
						routingdetails.setCreatedDate(routingHeaderListValue.getCreatedDate());
						routingdetails.setModifiedBy(routingHeaderListValue.getModifiedBy());
						routingdetails.setModifiedDate(routingHeaderListValue.getModifiedDate());
						routingdetails.setApprovedBy(routingHeaderListValue.getApprovedBy());
						routingdetails.setApprovedDate(routingHeaderListValue.getApprovedDate());

						boolean routingStatus = false;
						if (routingHeaderListValue.getRoutingHeaderId() != null) {
							// Checking five fields in DB
							routingDetailsList = iroutingSetUpDetailsService.getAllRecordsOfRoutingDetails(routingHeaderListValue.getRoutingHeaderId());

							if(routingDetailsList.size()!=0){
								for (RoutingDetails routingDetails : routingDetailsList) {
									RoutingSetUpDetailsDialogCheckBox routingcheckbox = new RoutingSetUpDetailsDialogCheckBox();

									routingcheckbox.setRoutingSetupDetailsId(routingDetails.getRoutingDetailsId());
									routingcheckbox.setRoutingSetupHeaderId(routingDetails.getExRountingHeaderId().getRoutingHeaderId());
									routingcheckbox.setBankBranchId(routingDetails.getExBankBranchId().getBankBranchId());
									routingcheckbox.setBankId(routingDetails.getExRoutingBankId().getBankId());
									routingcheckbox.setCountryId(routingDetails.getExRoutingCountryId().getCountryId());
									routingcheckbox.setBankbranchCode(routingDetails.getExBankBranchId().getBranchCode());
									routingcheckbox.setBranchFullName(routingDetails.getExBankBranchId().getBranchFullName());
									routingcheckbox.setAgentId(routingDetails.getExagentInd()==null ? null : routingDetails.getExagentInd().getBankIndicatorId());
									if(routingDetails.getIsActive().equalsIgnoreCase(Constants.Yes)){
										routingcheckbox.setSelectRecord(Boolean.TRUE);
										routingcheckbox.setRoutingStatus(Constants.ACTIVE);
										if(!routingStatus){
											routingStatus = true;
										}
									}else if(routingDetails.getIsActive().equalsIgnoreCase(Constants.U)){
										if(!routingStatus){
											routingStatus = true;
										}
										routingcheckbox.setSelectRecord(Boolean.FALSE);
										routingcheckbox.setRoutingStatus(Constants.IN_ACTIVE);
									}else{
										routingStatus = false;
										routingcheckbox.setSelectRecord(Boolean.FALSE);
										routingcheckbox.setRoutingStatus(Constants.IN_ACTIVE);
									}
									dialogselectbankbranch.add(routingcheckbox);
								}

							}

						}else{
							// new Record
							routingStatus = true;
						}

						if(routingStatus){
							routingdetails.setIsActive(routingHeaderListValue.getIsActive());
							if (routingdetails.getIsActive().equalsIgnoreCase(Constants.Yes)) {
								routingdetails.setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
								routingdetails.setIsActiveStatus(Constants.APPROVED);
							} else if (routingdetails.getIsActive().equalsIgnoreCase(Constants.D)) {
								routingdetails.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
								routingdetails.setIsActiveStatus(Constants.DEACTIVATE);
							} else if (routingdetails.getIsActive().equalsIgnoreCase(Constants.U) && routingdetails.getModifiedBy() == null && routingdetails.getModifiedDate() == null && routingdetails.getApprovedBy() == null
									&& routingdetails.getApprovedDate() == null && routingdetails.getRemarks() == null) {
								routingdetails.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
								routingdetails.setIsActiveStatus(Constants.UNAPPROVED);
							} else {
								routingdetails.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
								routingdetails.setIsActiveStatus(Constants.UNAPPROVED);
							}
						}else{
							routingdetails.setIsActive(Constants.D);
							routingdetails.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
							routingdetails.setIsActiveStatus(Constants.DEACTIVATE);
						}

						routingSetUpDetailsTable.add(routingdetails);

					}		


				}else{
					RequestContext.getCurrentInstance().execute("nodataFound.show();");
				}


				if(routingSetUpNewDetailsTable!=null && routingSetUpNewDetailsTable.size()>0){
					routingSetUpDetailsTable.addAll(routingSetUpNewDetailsTable);
				}

				if( routingSetUpDetailsTable!=null && routingSetUpDetailsTable.size()>0){
					setBooRenderDataTable(true);
				}

			}else{
				RequestContext.getCurrentInstance().execute("threeMandatory.show();");
			}

		}




	}


	public void checkStatusType(RoutingSetUpDetailsDataTable routingSetUpDetailsObj) {

		if(routingSetUpDetailsObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)){
			routingSetUpDetailsObj.setRemarksCheck(true);
			setApprovedDate(routingSetUpDetailsObj.getApprovedDate());
			setApprovedBy(routingSetUpDetailsObj.getApprovedBy());
			RequestContext.getCurrentInstance().execute("remarks.show();");
			return;
		}else if(routingSetUpDetailsObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.PENDING_FOR_APPROVE)){
			routingSetUpDetailsObj.setPermentDeleteCheck(true);
			RequestContext.getCurrentInstance().execute("permanentDelete.show();");
			return;
		}else if(routingSetUpDetailsObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.ACTIVATE)){
			routingSetUpDetailsObj.setActiveRecordCheck(true);
			RequestContext.getCurrentInstance().execute("activateRecord.show();");
			return;
		}else if(routingSetUpDetailsObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.REMOVE)){
			routingSetUpDetailsTable.remove(routingSetUpDetailsObj);
			routingSetUpNewDetailsTable.remove(routingSetUpDetailsObj);
		}

		if(routingSetUpDetailsTable.size()>0){
			setBooRenderDataTable(true);
		}else{
			setBooRenderDataTable(false);
		}
	}

	public void clickOkRemarks() {
		/*  if (getRemarks() != null && !getRemarks().equals("")) {*/
		for (RoutingSetUpDetailsDataTable routingSetUpDetailsDataTable : routingSetUpDetailsTable) {
			if (routingSetUpDetailsDataTable.getRemarksCheck() != null) {
				if (routingSetUpDetailsDataTable.getRemarksCheck().equals(true)) {
					// routingSetUpDetailsDataTable.setRemarks(getRemarks());
					updateRoutingSetupHeaderAndDetails(routingSetUpDetailsDataTable);
					displayView();
					setRemarks(null);
				}
			}
		}

		/* } else {
			      RequestContext.getCurrentInstance().execute("remarksEmpty.show();");
		    }*/
	}
	
	public void updateRoutingSetupHeaderAndDetails(RoutingSetUpDetailsDataTable dataTable) {
		List<RoutingDetails> routingDetailsList=iroutingSetUpDetailsService.getAllReCordsFormHeader(dataTable.getRoutingSetUpHeaderId());
		if(routingDetailsList.size()>0){
			for (RoutingDetails routingDetails : routingDetailsList) {
				iroutingSetUpDetailsService.upDateActiveRecordDeActive(routingDetails.getRoutingDetailsId(),sessionManage.getUserName());
			}
		}
		iroutingSetUpDetailsService.upDateActiveRecordtoDeActive(dataTable.getRoutingSetUpHeaderId(), sessionManage.getUserName());
	}
	
	// click on Active link
	public void activateRecord() {
		if (routingSetUpDetailsTable.size() > 0) {
			for (RoutingSetUpDetailsDataTable routingSetUpDetailsObj : routingSetUpDetailsTable) {
				if (routingSetUpDetailsObj.getActiveRecordCheck() != null) {
					if (routingSetUpDetailsObj.getActiveRecordCheck().equals(true)) {
						conformActiveRecordToPendingForApproval(routingSetUpDetailsObj);
					}
				}
			}
		}
	}

	public void conformActiveRecordToPendingForApproval(RoutingSetUpDetailsDataTable dataTable) {
		List<RoutingDetails> routingDetailsList=iroutingSetUpDetailsService.getAllReCordsFormHeader(dataTable.getRoutingSetUpHeaderId());
		if(routingDetailsList.size()>0){
			for (RoutingDetails routingDetails : routingDetailsList) {
				iroutingSetUpDetailsService.DeActiveRecordToPendingForApprovalOfRoutingDetails(routingDetails.getRoutingDetailsId(),sessionManage.getUserName());
			}
		}
		iroutingSetUpDetailsService.DeActiveRecordToPendingApprovalOfRoutingHeader(dataTable.getRoutingSetUpHeaderId(), sessionManage.getUserName());
		displayView();
	} 
	public void cancelRemarks() {
		setRemarks(null);
		/*setBooApproval(false); 
		     setBooRead(false);
		 */
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/RoutingSetup.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void changeCountry(){
		setRoutingcurrencyIdForDailog(null);
		setRoutingServiceIdForDailog(null);
		getServiceCodeFromDB();
		listAllCurrencyBasedonCountry(getCountryIdForDailog());
	}

	public void changeCountryForApprove(){
		setRoutingcurrencyId(null);
		setRoutingServiceId(null);
		listAllCurrencyBasedonCountry(getCountryId());
	}

	//click on Approval Button
	/*public void gotoApproval(){
		  for (RoutingSetUpDetailsDataTable dataTable : routingSetUpDetailsTable) {
			    RoutingHeader routingHeader=new RoutingHeader();
			    routingHeader.setRoutingHeaderId(dataTable.getRoutingSetUpHeaderId());
			    List<RoutingDetails> routingDetailsList=iroutingSetUpDetailsService.getAllReCordsFormHeader(dataTable.getRoutingSetUpHeaderId());
			    if(routingDetailsList.size()>0){
				      iroutingSetUpDetailsService.getAllDealDetilasList(routingDetailsList.get(0).getRoutingDetailsId(),sessionManage.getUserName());
			    }
			    iroutingSetUpDetailsService.approveRecord(dataTable.getRoutingSetUpHeaderId(),sessionManage.getUserName());

		  	}
		   RequestContext.getCurrentInstance().execute("approve.show();");
		   return;
	}*/


	public void approveRecord(RoutingSetUpDetailsDataTable dataTable){
		if(!(dataTable.getModifiedBy() == null ?  dataTable.getCreatedBy() : dataTable.getModifiedBy()).equalsIgnoreCase(sessionManage.getUserName())){
			List<RoutingDetails> routingDetailsList=iroutingSetUpDetailsService.getAllReCordsFormHeader(dataTable.getRoutingSetUpHeaderId());
			if(routingDetailsList.size()>0){
				for (RoutingDetails routingDetails : routingDetailsList) {
					iroutingSetUpDetailsService.getAllDealDetilasList(routingDetails.getRoutingDetailsId(),sessionManage.getUserName());
				}

			}
			iroutingSetUpDetailsService.approveRecord(dataTable.getRoutingSetUpHeaderId(),sessionManage.getUserName());
			RequestContext.getCurrentInstance().execute("approve.show();");
			routingSetUpDetailsTable.remove(dataTable);
		}else{
			RequestContext.getCurrentInstance().execute("sameUser.show();");
			return;
		}
	}

	public BigDecimal getCountryIdForDailog() {
		return countryIdForDailog;
	}

	public void setCountryIdForDailog(BigDecimal countryIdForDailog) {
		this.countryIdForDailog = countryIdForDailog;
	}




	private String approvedBy;
	private Date approvedDate;
	private String remarks;
	private String isActiveStatus;
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getIsActiveStatus() {
		return isActiveStatus;
	}

	public void setIsActiveStatus(String isActiveStatus) {
		this.isActiveStatus = isActiveStatus;
	}

	public void exitSearchDailog(){
		setCountryIdForDailog(null);
		setRoutingcurrencyIdForDailog(null);
		setRoutingServiceIdForDailog(null);
	}

	public Boolean getSelectAll() {
		return selectAll;
	}

	public void setSelectAll(Boolean selectAll) {
		this.selectAll = selectAll;
	}

	public List<RoutingAgentViewDataTable> getRoutingAgentDataTableList() {
		return routingAgentDataTableList;
	}

	public void setRoutingAgentDataTableList(List<RoutingAgentViewDataTable> routingAgentDataTableList) {
		this.routingAgentDataTableList = routingAgentDataTableList;
	}

	public void loadAgentLocation(RoutingSetUpDetailsDialogCheckBox routingSetUpDetailsDialogCheckBox)
	{

		if(routingSetUpDetailsDialogCheckBox.getRoutingBranchId()!=null)
		{
			if(routingSetUpDetailsDialogCheckBox.getBranchApplicability() != null){
				routingSetUpDetailsDialogCheckBox.setBranchApplicability(null);
			}
			//added save
			if(routingSetUpDetailsDialogCheckBox.getDynamicLabelActivateDeActivate() != null && !routingSetUpDetailsDialogCheckBox.getDynamicLabelActivateDeActivate().equalsIgnoreCase(Constants.REMOVE)){
				routingSetUpDetailsDialogCheckBox.setIsActive(Constants.U);
				routingSetUpDetailsDialogCheckBox.setDynamicLabelActivateDeActivate(Constants.PENDING_FOR_APPROVE);
				routingSetUpDetailsDialogCheckBox.setDataTableStatus(Constants.UNAPPROVED);
			}
		}else
		{
			routingSetUpDetailsDialogCheckBox.setBankId(null);
			RequestContext.getCurrentInstance().execute("noBankBranchselection.show();");
		}

	}

	public void addEmptyRow()
	{

		for(RoutingSetUpDetailsDialogCheckBox routingAgent :routingAgentList)
		{
			if(routingAgent.getRoutingBranchId()==null || routingAgent.getBankId()==null || routingAgent.getBranchApplicability()==null)
			{
				RequestContext.getCurrentInstance().execute("someOfDetails.show();");
				return;
			}

		}

		List<BankBranchView> lstBankBranchView=null;
		if(getLstTempBankBranchView()!=null)
		{
			lstBankBranchView=getLstTempBankBranchView();
		}else
		{
			//lstBankBranchView= iBeneficaryCreation.getBranchListfromView(getRoutingbankId()) ;
			lstBankBranchView= iBeneficaryCreation.getBranchListfromViewByCountryBank(getRoutingCountryId(),getRoutingbankId());
		}

		List<PopulateDataWithCode> lstAgentsMasterBanks = new ArrayList<PopulateDataWithCode>();
		List<PopulateDataWithCode> lstofAllCorrAgentsBeneServicePBanks = iroutingSetUpDetailsService.FetchAllBankListbyBankCountry(getRoutingCountryId());
		if(lstofAllCorrAgentsBeneServicePBanks != null && !lstofAllCorrAgentsBeneServicePBanks.isEmpty()){
			lstAgentsMasterBanks.addAll(lstofAllCorrAgentsBeneServicePBanks);
		}

		RoutingSetUpDetailsDialogCheckBox routingAgentObj = new RoutingSetUpDetailsDialogCheckBox();
		routingAgentObj.setLstRoutingSetupBankDetails(lstAgentsMasterBanks);
		routingAgentObj.setLstBankBranchView(lstBankBranchView);
		routingAgentObj.setIsActive(Constants.U);
		routingAgentObj.setDynamicLabelActivateDeActivate(Constants.REMOVE);
		routingAgentObj.setDataTableStatus(Constants.REMOVE);
		routingAgentList.add(routingAgentObj);

	}

	public void bankBranchCkeck(RoutingSetUpDetailsDialogCheckBox routingAgentObj)
	{
		BigDecimal routingBranchId= routingAgentObj.getRoutingBranchId();

		if(routingBranchId!=null &&  routingAgentList!=null && routingAgentList.size()>0)
		{
			List<RoutingSetUpDetailsDialogCheckBox> tempRoutingAgentList=new CopyOnWriteArrayList<RoutingSetUpDetailsDialogCheckBox>();
			tempRoutingAgentList.addAll(routingAgentList);
			if(tempRoutingAgentList.size()==1)
			{
				List<RoutingDetails> routingDetailsList = iroutingSetUpDetailsService.toFetchAllBranches(getCountryId(), getRoutingcurrencyId(), getRoutingServiceId(), getRoutingRemittanceId(), getRoutingDeliveryId(), getRoutingCountryId(), getRoutingbankId());
				if(routingDetailsList.size()>0){
					for (RoutingDetails routingDetails : routingDetailsList) {
						if(routingDetails.getExBankBranchId().getBankBranchId().equals(tempRoutingAgentList.get(0).getRoutingBranchId())){
							routingAgentObj.setRoutingBranchId(null);
							RequestContext.getCurrentInstance().execute("recordExist.show();");
							return;
						}
					}
				}
			}else
			{
				tempRoutingAgentList.remove(routingAgentObj);
				for(RoutingSetUpDetailsDialogCheckBox routingAgent :tempRoutingAgentList)
				{
					if(routingAgent.getRoutingBranchId()!=null && routingAgent.getRoutingBranchId().compareTo(routingBranchId)==0)
					{
						routingAgentObj.setRoutingBranchId(null);
						RequestContext.getCurrentInstance().execute("alreadyslected.show();");
						return;
					}
				}

				List<RoutingDetails> routingDetailsList = iroutingSetUpDetailsService.toFetchAllBranches(getCountryId(), getRoutingcurrencyId(), getRoutingServiceId(), getRoutingRemittanceId(), getRoutingDeliveryId(), getRoutingCountryId(), getRoutingbankId());
				if(routingDetailsList.size()>0){
					for (RoutingDetails routingDetails : routingDetailsList) {
						if(routingDetails.getExBankBranchId().getBankBranchId().equals(routingBranchId)){
							routingAgentObj.setRoutingBranchId(null);
							RequestContext.getCurrentInstance().execute("recordExist.show();");
							return;
						}
					}
				}
			}
			//added save
			if(routingAgentObj.getDynamicLabelActivateDeActivate() != null && !routingAgentObj.getDynamicLabelActivateDeActivate().equalsIgnoreCase(Constants.REMOVE)){
				routingAgentObj.setIsActive(Constants.U);
				routingAgentObj.setDynamicLabelActivateDeActivate(Constants.PENDING_FOR_APPROVE);
				routingAgentObj.setDataTableStatus(Constants.UNAPPROVED);
			}
		}
	}

	private RoutingHeader saveRoutingHeader(RoutingSetUpDetailsDataTable routingSetUpDetailsDataTable)
	{
		RoutingHeader routingHeader = new RoutingHeader();

		routingHeader.setRoutingHeaderId(routingSetUpDetailsDataTable.getRoutingSetUpHeaderId());

		//save Application Country
		CountryMaster applicationCountry = new CountryMaster();
		applicationCountry.setCountryId(sessionManage.getCountryId());
		routingHeader.setExApplicationCountry(applicationCountry);

		//save Country
		CountryMaster countryMaster = new CountryMaster();
		countryMaster.setCountryId(routingSetUpDetailsDataTable.getCountryId());
		routingHeader.setExCountryId(countryMaster);

		//save Currency
		CurrencyMaster currencyMaster = new CurrencyMaster();
		currencyMaster.setCurrencyId(routingSetUpDetailsDataTable.getRoutingcurrencyId());
		routingHeader.setExCurrenyId(currencyMaster);

		//save Service Code
		ServiceMaster serviceMaster = new ServiceMaster();
		serviceMaster.setServiceId(routingSetUpDetailsDataTable.getRoutingServiceId());
		routingHeader.setExServiceId(serviceMaster);

		DeliveryMode deliveryMode = new DeliveryMode();

		if(routingSetUpDetailsDataTable.getRoutingDeliveryId()!=null){
			//save Delivery Code
			deliveryMode.setDeliveryModeId(routingSetUpDetailsDataTable.getRoutingDeliveryId());
			routingHeader.setExDeliveryModeId(deliveryMode);
		}

		RemittanceModeMaster remittanceModeMaster = new RemittanceModeMaster();

		if(routingSetUpDetailsDataTable.getRoutingRemittanceId()!=null){
			//save Remittance Mode
			remittanceModeMaster.setRemittanceModeId(routingSetUpDetailsDataTable.getRoutingRemittanceId());
			routingHeader.setExRemittanceModeId(remittanceModeMaster);
		}

		//save Routing Country
		CountryMaster routingcountryMaster = new CountryMaster();
		routingcountryMaster.setCountryId(routingSetUpDetailsDataTable.getRoutingcountryId());
		routingHeader.setExRoutingCountryId(routingcountryMaster);

		//save Bank 
		BankMaster bankMaster = new BankMaster();
		bankMaster.setBankId(routingSetUpDetailsDataTable.getRoutingbankId());
		routingHeader.setExRoutingBankId(bankMaster);

		routingHeader.setBranchApplicability(Constants.S);
		if(routingSetUpDetailsDataTable.getCreatedBy()!=null)
		{
			routingHeader.setCreatedBy(routingSetUpDetailsDataTable.getCreatedBy());
			routingHeader.setCreatedDate(routingSetUpDetailsDataTable.getCreatedDate());

			routingHeader.setModifiedBy(sessionManage.getUserName());
			routingHeader.setModifiedDate(new Date());

			routingHeader.setIsActive(Constants.U);
		}else
		{
			routingHeader.setCreatedBy(sessionManage.getUserName());
			routingHeader.setCreatedDate(new Date());
			routingHeader.setIsActive(Constants.U);
		}


		iroutingSetUpDetailsService.saveRoutingHeader(routingHeader);

		return routingHeader;
	}

	private void saveRoutingDetail(RoutingHeader routingHeader)
	{

		for(RoutingSetUpDetailsDialogCheckBox routingObj: routingAgentList){

			if((routingObj.getDynamicLabelActivateDeActivate() != null) && (routingObj.getDynamicLabelActivateDeActivate().equalsIgnoreCase(Constants.REMOVE) || routingObj.getDynamicLabelActivateDeActivate().equalsIgnoreCase(Constants.PENDING_FOR_APPROVE)) ){

				RoutingDetails routingDetails = new RoutingDetails();

				routingDetails.setRoutingDetailsId(routingObj.getRoutingSetupDetailsId());

				routingDetails.setExRountingHeaderId(routingHeader);
				routingDetails.setExApplicationCountry(routingHeader.getExApplicationCountry());
				routingDetails.setExCountryId(routingHeader.getExCountryId());
				routingDetails.setExCurrenyId(routingHeader.getExCurrenyId());
				routingDetails.setExServiceId(routingHeader.getExServiceId());

				if(routingHeader.getExRemittanceModeId()!=null){
					routingDetails.setExRemittanceModeId(routingHeader.getExRemittanceModeId());
				}

				if(routingHeader.getExDeliveryModeId()!=null){
					routingDetails.setExDeliveryModeId(routingHeader.getExDeliveryModeId());
				}

				routingDetails.setExRoutingCountryId(routingHeader.getExRoutingCountryId());
				routingDetails.setExRoutingBankId(routingHeader.getExRoutingBankId());

				//save Bank Branch Details
				BankBranch bankBranch = new BankBranch();
				bankBranch.setBankBranchId(routingObj.getRoutingBranchId());
				routingDetails.setExBankBranchId(bankBranch);

				//routingDetails.setBranchApplicability(Constants.S);

				if(routingObj.getBranchApplicability() != null && routingObj.getBranchApplicability().equalsIgnoreCase(Constants.SPECIFIC)){
					routingDetails.setBranchApplicability(Constants.S);
				}else{
					routingDetails.setBranchApplicability(Constants.ALL);
				}


				if(routingHeader.getCreatedBy()!=null)
				{
					routingDetails.setIsActive(Constants.U);
					routingDetails.setCreatedBy(routingHeader.getCreatedBy());
					routingDetails.setCreatedDate(routingHeader.getCreatedDate());
					routingDetails.setModifiedBy(sessionManage.getUserName());
					routingDetails.setModifiedDate(new Date());

				}else
				{
					routingDetails.setIsActive(Constants.U);
					routingDetails.setCreatedBy(sessionManage.getUserName());
					routingDetails.setCreatedDate(new Date());
				}


				routingDetails.setAgentBranchID(routingObj.getAgentBranchId());
				routingDetails.setAgentBranchEmosMapCode(routingObj.getMappingRoutingBranch());
				routingDetails.setAgentBankId(routingObj.getBankId());

				iroutingSetUpDetailsService.saveRoutingDetails(routingDetails);
			}

		}

	}

	private void addRecordsToDataTableFromDB()
	{

		try{

			//List<BankBranchView> lstBankBranchView= iBeneficaryCreation.getBranchListfromView(getRoutingbankId()) ;
			List<BankBranchView> lstBankBranchView = iBeneficaryCreation.getBranchListfromViewByCountryBank(getRoutingCountryId(),getRoutingbankId());

			if(lstBankBranchView.size()>0)
			{
				setLstTempBankBranchView(lstBankBranchView);
			}else
			{
				RequestContext.getCurrentInstance().execute("branchesNotExist.show();");
				return;
			}

			List<RoutingHeader> routingheaderList =iroutingSetUpDetailsService.getAllRecordsIfExist(getCountryId(), getRoutingcurrencyId(), getRoutingServiceId(), getRoutingRemittanceId(), getRoutingDeliveryId(), getRoutingCountryId(), getRoutingbankId());


			List<RoutingSetUpDetailsDataTable> routingSetUpDetailsTableForCash = new ArrayList<RoutingSetUpDetailsDataTable>();


			for(RoutingHeader routingHeader :routingheaderList)
			{

				RoutingSetUpDetailsDataTable routingSetUpDetailsDataTable = new RoutingSetUpDetailsDataTable();

				routingSetUpDetailsDataTable.setRoutingSetUpHeaderId(routingHeader.getRoutingHeaderId());
				routingSetUpDetailsDataTable.setCountryId(routingHeader.getExCountryId().getCountryId());
				routingSetUpDetailsDataTable.setRoutingcurrencyId(routingHeader.getExCurrenyId().getCurrencyId());
				routingSetUpDetailsDataTable.setRoutingServiceId(routingHeader.getExServiceId().getServiceId());
				if(routingHeader.getExDeliveryModeId()!=null)
				{
					routingSetUpDetailsDataTable.setRoutingDeliveryId(routingHeader.getExDeliveryModeId().getDeliveryModeId());
				}

				if(routingHeader.getExRemittanceModeId()!=null)
				{
					routingSetUpDetailsDataTable.setRoutingRemittanceId(routingHeader.getExRemittanceModeId().getRemittanceModeId());
				}

				routingSetUpDetailsDataTable.setRoutingcountryId(routingHeader.getExRoutingCountryId().getCountryId());
				routingSetUpDetailsDataTable.setRoutingbankId(routingHeader.getExRoutingBankId().getBankId());
				routingSetUpDetailsDataTable.setCreatedBy(routingHeader.getCreatedBy());
				routingSetUpDetailsDataTable.setCreatedDate(routingHeader.getCreatedDate());

				routingSetUpDetailsTableForCash.add(routingSetUpDetailsDataTable);

				List<RoutingDetails> lstRoutingDetails = iroutingSetUpDetailsService.getAllRecordsOfRoutingDetails(routingHeader.getRoutingHeaderId());
				List<PopulateDataWithCode> lstAgentsMasterBanks = new ArrayList<PopulateDataWithCode>();
				List<PopulateDataWithCode> lstofAllCorrAgentsBeneServicePBanks = iroutingSetUpDetailsService.FetchAllBankListbyBankCountry(routingHeader.getExRoutingCountryId().getCountryId());
				if(lstofAllCorrAgentsBeneServicePBanks != null && !lstofAllCorrAgentsBeneServicePBanks.isEmpty()){
					lstAgentsMasterBanks.addAll(lstofAllCorrAgentsBeneServicePBanks);
				}

				List<BigDecimal> lstBankBranchDeletedRec = new ArrayList<BigDecimal>();
				for(RoutingDetails routingDetails :lstRoutingDetails)
				{
					if(routingDetails.getExBankBranchId() != null && !routingDetails.getExBankBranchId().getIsactive().equalsIgnoreCase(Constants.Yes)){
						lstBankBranchDeletedRec.add(routingDetails.getExBankBranchId().getBankBranchId());
					}
				}

				if(lstBankBranchDeletedRec.size() != 0){
					List<BankBranch> lstBranchDeletedRec =  iroutingSetUpDetailsService.FetchAllBankBranchesTaggedDelete(lstBankBranchDeletedRec);
					if(lstBranchDeletedRec.size() != 0){

						for (BankBranch bankBranch : lstBranchDeletedRec) {
							BankBranchView addingDRec = new BankBranchView();
							addingDRec.setAddress1(bankBranch.getAddress1());
							addingDRec.setAddress2(bankBranch.getAddress2());
							addingDRec.setBankBranchId(bankBranch.getBankBranchId());
							addingDRec.setBankId(bankBranch.getExBankMaster().getBankId());
							addingDRec.setBranchCode(bankBranch.getBranchCode());
							addingDRec.setBranchFullName(bankBranch.getBranchFullName());
							if(bankBranch.getFsCityMaster() != null){
								addingDRec.setCityId(bankBranch.getFsCityMaster().getCityId());
								//addingDRec.setCityName(bankBranch.getFsCityMaster().getCityCode());
							}
							if(bankBranch.getFsDistrictMaster() != null){
								addingDRec.setDistrictId(bankBranch.getFsDistrictMaster().getDistrictId());
								//addingDRec.setDistrictName(bankBranch.getFsDistrictMaster().getDistrictCode());
							}
							if(bankBranch.getFsStateMaster() != null){
								addingDRec.setStateId(bankBranch.getFsStateMaster().getStateId());
								//addingDRec.setStateName(bankBranch.getFsStateMaster().getStateCode());
							}
							addingDRec.setIfscCode(bankBranch.getBranchIfsc());
							addingDRec.setSwift(bankBranch.getSwiftBic());
							addingDRec.setZipcode(bankBranch.getZipCode());
							lstBankBranchView.add(addingDRec);
						}
					}
				}

				if(lstRoutingDetails.size()>0)
				{

					List<BigDecimal> duplicateAgentBankId = new ArrayList<BigDecimal>();
					List<BankBranchView> lstAgentBankBranchView = new ArrayList<BankBranchView>();

					for(RoutingDetails routingDetails :lstRoutingDetails)
					{


						RoutingSetUpDetailsDialogCheckBox routingAgentObj = new RoutingSetUpDetailsDialogCheckBox();


						routingAgentObj.setRoutingSetupHeaderId(routingHeader.getRoutingHeaderId());
						routingAgentObj.setLstRoutingSetupBankDetails(lstAgentsMasterBanks);
						routingAgentObj.setLstBankBranchView(lstBankBranchView);

						routingAgentObj.setBankId(routingDetails.getAgentBankId());


						if(!duplicateAgentBankId.contains(routingDetails.getAgentBankId())){
							duplicateAgentBankId.add(routingDetails.getAgentBankId());
							//lstAgentBankBranchView = iBeneficaryCreation.getBranchListfromView(routingDetails.getAgentBankId()) ;
							lstAgentBankBranchView = iBeneficaryCreation.getBranchListfromViewByCountryBank(routingDetails.getExCountryId().getCountryId(),routingDetails.getAgentBankId());
						}

						routingAgentObj.setAgentBranchId(routingDetails.getAgentBranchID());
						routingAgentObj.setLstAgentBankBranchView(lstAgentBankBranchView);

						if(routingDetails.getExBankBranchId() != null && routingDetails.getExBankBranchId().getIsactive().equalsIgnoreCase(Constants.Yes)){
							routingAgentObj.setRoutingBranchId(routingDetails.getExBankBranchId().getBankBranchId());
							routingAgentObj.setRoutingbranchStatus(Constants.ACTIVE);
						}else{
							routingAgentObj.setRoutingBranchId(routingDetails.getExBankBranchId().getBankBranchId());
							routingAgentObj.setRoutingbranchStatus(Constants.IN_ACTIVE);
						}

						routingAgentObj.setRoutingSetupDetailsId(routingDetails.getRoutingDetailsId());
						routingAgentObj.setMappingRoutingBranch(routingDetails.getAgentBranchEmosMapCode());
						routingAgentObj.setCreatedBy(routingDetails.getCreatedBy());
						routingAgentObj.setCreatedDate(routingDetails.getCreatedDate());
						routingAgentObj.setModifiedBy(routingAgentObj.getModifiedBy());
						routingAgentObj.setModifiedDate(routingDetails.getModifiedDate());
						routingAgentObj.setApprovedBy(routingDetails.getApprovedBy());
						routingAgentObj.setApprovedDate(routingDetails.getApprovedDate());

						if(routingDetails.getBranchApplicability() != null){

							if(routingDetails.getBranchApplicability().equalsIgnoreCase(Constants.S)){
								routingAgentObj.setBranchApplicability(Constants.SPECIFIC);
							}else{
								routingAgentObj.setBranchApplicability(Constants.ALL);
							}

						}

						if(routingDetails.getIsActive().equalsIgnoreCase(Constants.Yes)){
							routingAgentObj.setDynamicLabelActivateDeActivate(Constants.DEACTIVATE);
							routingAgentObj.setDataTableStatus(Constants.APPROVED);
						}else if (routingDetails.getIsActive().equalsIgnoreCase(Constants.D)) {
							routingAgentObj.setDynamicLabelActivateDeActivate(Constants.ACTIVATE);
							routingAgentObj.setDataTableStatus(Constants.UNAPPROVED);
						}else if (routingDetails.getIsActive().equalsIgnoreCase(Constants.U)) {
							routingAgentObj.setDynamicLabelActivateDeActivate(Constants.PENDING_FOR_APPROVE);
							routingAgentObj.setDataTableStatus(Constants.UNAPPROVED);
						}

						routingAgentList.add(routingAgentObj);
					}

				}




			}
			setRoutingSetUpDetailsTableForCash(routingSetUpDetailsTableForCash);
			if(routingAgentList.size()>0)
			{
				setBooRenderDataTable(true);
				setBooAddmorefield(true);

			}else
			{
				setBooRenderDataTable(false);
				setBooAddmorefield(false);
			}
		}catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::addRecordsToDataTableFromDB"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}


	}

	private String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	//Agent Panel Activate DeActivate Delete Pending For Approval
	public void checkStatusType(RoutingSetUpDetailsDialogCheckBox routingSetUpDetailsObj) {

		if(routingSetUpDetailsObj.getDynamicLabelActivateDeActivate().equalsIgnoreCase(Constants.DEACTIVATE)){
			routingSetUpDetailsObj.setRemarksCheck(true);
			setApprovedDate(routingSetUpDetailsObj.getApprovedDate());
			setApprovedBy(routingSetUpDetailsObj.getApprovedBy());
			RequestContext.getCurrentInstance().execute("remarksAgent.show();");
			return;
		}else if(routingSetUpDetailsObj.getDynamicLabelActivateDeActivate().equalsIgnoreCase(Constants.PENDING_FOR_APPROVE)){
			routingSetUpDetailsObj.setPermentDeleteCheck(true);
			RequestContext.getCurrentInstance().execute("permanentDeleteAgent.show();");
			return;
		}else if(routingSetUpDetailsObj.getDynamicLabelActivateDeActivate().equalsIgnoreCase(Constants.ACTIVATE)){
			routingSetUpDetailsObj.setActiveRecordCheck(true);
			RequestContext.getCurrentInstance().execute("activateRecordAgent.show();");
			return;
		}else if(routingSetUpDetailsObj.getDynamicLabelActivateDeActivate().equalsIgnoreCase(Constants.REMOVE)){
			routingAgentList.remove(routingSetUpDetailsObj);
			//routingAgentList.remove(routingSetUpDetailsObj);
		}

		if(routingAgentList.size()>0){
			setBooRenderDataTable(true);
		}else{
			setBooRenderDataTable(false);
		}
	}
	public void clickOkRemarksAgent()throws Exception {
		// if (getRemarks() != null && !getRemarks().equals("")) {
		for (RoutingSetUpDetailsDialogCheckBox routingSetUpDetailsDataTable : routingAgentList) {
			if (routingSetUpDetailsDataTable.getRemarksCheck() != null) {
				if (routingSetUpDetailsDataTable.getRemarksCheck().equals(true)) {
					// routingSetUpDetailsDataTable.setRemarks(getRemarks());
					updateRoutingSetupHeaderAndDetailsAgent(routingSetUpDetailsDataTable);
					displayView();
					setRemarks(null);
				}
			}
		}

		/* } else {
			      RequestContext.getCurrentInstance().execute("remarksEmpty.show();");
		    }*/
	}
	public void updateRoutingSetupHeaderAndDetailsAgent(RoutingSetUpDetailsDialogCheckBox dataTable) {
		try{
			//List<RoutingDetails> routingDetailsList=iroutingSetUpDetailsService.getAllReCordsFormHeader(dataTable.getRoutingSetupHeaderId());
			List<RoutingDetails> routingDetailsList=iroutingSetUpDetailsService.getRoutDtsBasedOnHeaderIdBranchId(dataTable.getRoutingSetupHeaderId(),dataTable.getRoutingBranchId());
			if(routingDetailsList.size()>0){
				iroutingSetUpDetailsService.upDateActiveRecordDeActive(routingDetailsList.get(0).getRoutingDetailsId(),sessionManage.getUserName());
			}
			//iroutingSetUpDetailsService.upDateActiveRecordtoDeActive(dataTable.getRoutingSetupHeaderId(), sessionManage.getUserName());
		}catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("MethodName::updateRoutingSetupHeaderAndDetailsAgent");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}

	}
	// click on Active link
	public void activateRecordAgent() throws Exception{
		if (routingAgentList.size() > 0) {
			for (RoutingSetUpDetailsDialogCheckBox routingSetUpDetailsObj : routingAgentList) {
				if (routingSetUpDetailsObj.getActiveRecordCheck() != null) {
					if (routingSetUpDetailsObj.getActiveRecordCheck().equals(true)) {
						conformActiveRecordToPendingForApprovalAgent(routingSetUpDetailsObj);
					}
				}
			}
		}
	}

	public void conformActiveRecordToPendingForApprovalAgent(RoutingSetUpDetailsDialogCheckBox dataTable) {
		try{
			//List<RoutingDetails> routingDetailsList=iroutingSetUpDetailsService.getAllReCordsFormHeader(dataTable.getRoutingSetupHeaderId());
			List<RoutingDetails> routingDetailsList=iroutingSetUpDetailsService.getRoutDtsBasedOnHeaderIdBranchId(dataTable.getRoutingSetupHeaderId(),dataTable.getRoutingBranchId());
			if(routingDetailsList.size()>0){
				iroutingSetUpDetailsService.DeActiveRecordToPendingForApprovalOfRoutingDetails(routingDetailsList.get(0).getRoutingDetailsId(),sessionManage.getUserName());
			}
			//iroutingSetUpDetailsService.DeActiveRecordToPendingApprovalOfRoutingHeader(dataTable.getRoutingSetupHeaderId(), sessionManage.getUserName());
			displayView();
		}catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("MethodName::conformActiveRecordToPendingForApprovalAgent");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	} 
	public void cancelRemarksAgent() {
		setRemarks(null);
		// setBooApproval(false); 
		//  setBooRead(false);

		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/RoutingSetup.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<RoutingSetUpDetailsDialogCheckBox> getNewRoutingAgentList() {
		return NewRoutingAgentList;
	}

	public void setNewRoutingAgentList(
			List<RoutingSetUpDetailsDialogCheckBox> newRoutingAgentList) {
		NewRoutingAgentList = newRoutingAgentList;
	}

	public void changeStatus(RoutingSetUpDetailsDialogCheckBox routingAgentObj){
		routingAgentObj.setIsActive(Constants.U);
		routingAgentObj.setDynamicLabelActivateDeActivate(Constants.PENDING_FOR_APPROVE);
		routingAgentObj.setDataTableStatus(Constants.UNAPPROVED); 
	}

	private String serchBranchName;




	public String getSerchBranchName() {
		return serchBranchName;
	}

	public void setSerchBranchName(String serchBranchName) {
		this.serchBranchName = serchBranchName;
	}

	public void searchRecord()
	{
		try{

			if(getSerchBranchName()!=null && !getSerchBranchName().equalsIgnoreCase(""))
			{
				//resetFilters("routingsetupform:checkDataTable");
				//dialogselectbankbranch.clear();
				bankbranchlist.clear();
				addallvalues.clear();
				setSelectAll(true);
				if(getRoutingCountryId()!=null && getRoutingbankId()!=null){
					List<BankBranch> bankbranchspecific=new ArrayList<BankBranch>();
					bankbranchspecific = iroutingSetUpDetailsService.getSearchBankBranchList(getRoutingCountryId(), getRoutingbankId(),getSerchBranchName());

					if(getBranchApplicability().equals(Constants.SPECIFIC)){
						if(bankbranchspecific.size()!=0){
							for(BankBranch bankbranch : bankbranchspecific){

								RoutingSetUpDetailsDialogCheckBox routingcheckbox = new RoutingSetUpDetailsDialogCheckBox();

								routingcheckbox.setBankBranchId(bankbranch.getBankBranchId());
								routingcheckbox.setBankId(bankbranch.getExBankMaster().getBankId());
								routingcheckbox.setCountryId(bankbranch.getFsCountryMaster().getCountryId());
								routingcheckbox.setBankbranchCode(bankbranch.getBranchCode());
								routingcheckbox.setBranchFullName(bankbranch.getBranchFullName());
								routingcheckbox.setBranchApplicability(getBranchApplicability());

								if(dialogselectbankbranch.size()!=0){
									for (RoutingSetUpDetailsDialogCheckBox routingDetails : dialogselectbankbranch) {

										if((bankbranch.getFsCountryMaster().getCountryId().compareTo(routingDetails.getCountryId())==0)){

											if((bankbranch.getExBankMaster().getBankId().compareTo(routingDetails.getBankId())==0)){
												if(bankbranch.getBankBranchId().compareTo(routingDetails.getBankBranchId())==0){
													if(routingDetails.getSelectRecord().equals(Boolean.TRUE)){
														setRoutingSetUpHeaderId(routingDetails.getRoutingSetupHeaderId());
														routingcheckbox.setRoutingSetupHeaderId(routingDetails.getRoutingSetupHeaderId());
														routingcheckbox.setRoutingSetupDetailsId(routingDetails.getRoutingSetupDetailsId());
														routingcheckbox.setRoutingStatus(Constants.ACTIVE);
														routingcheckbox.setSelectRecord(true);
														break;
													}else{
														routingcheckbox.setRoutingSetupHeaderId(routingDetails.getRoutingSetupHeaderId());
														routingcheckbox.setRoutingSetupDetailsId(routingDetails.getRoutingSetupDetailsId());
														routingcheckbox.setRoutingStatus(Constants.IN_ACTIVE);
														routingcheckbox.setSelectRecord(false);
													}
												}else{
													routingcheckbox.setSelectRecord(false);
												}
											}else{
												routingcheckbox.setSelectRecord(true);
											}
										}else{
											routingcheckbox.setSelectRecord(true);
										}
									}

									//dialogselectbankbranch.add(routingcheckbox);

									/*if(editClick){
							dialogselectbankbranch.add(routingcheckbox);
						}else{
							bankbranchlist.add(routingcheckbox);
						}*/
									bankbranchlist.add(routingcheckbox);

								}else{
									//dialogselectbankbranch.add(routingcheckbox);
									routingcheckbox.setSelectRecord(true);
									/*if(editClick){
							dialogselectbankbranch.add(routingcheckbox);
						}else{
							bankbranchlist.add(routingcheckbox);
						}*/
									bankbranchlist.add(routingcheckbox);
								}

							}
							/*if(!editClick){
								RequestContext.getCurrentInstance().execute("details.show();");
							}*/

						}else{
							RequestContext.getCurrentInstance().execute("nobranch.show();");
						}
					}else if(getBranchApplicability().equals(Constants.ALL)){
						if(bankbranchspecific.size()!=0){
							for(BankBranch bankbranch : bankbranchspecific){
								RoutingSetUpDetailsDialogCheckBox routingcheckbox = new RoutingSetUpDetailsDialogCheckBox();
								routingcheckbox.setBankBranchId(bankbranch.getBankBranchId());
								routingcheckbox.setBankId(bankbranch.getExBankMaster().getBankId());
								routingcheckbox.setCountryId(bankbranch.getFsCountryMaster().getCountryId());
								routingcheckbox.setBankbranchCode(bankbranch.getBranchCode());
								routingcheckbox.setBranchFullName(bankbranch.getBranchFullName());
								routingcheckbox.setSelectRecord(isSelectedcheck());
								routingcheckbox.setBranchApplicability(getBranchApplicability());
								routingcheckbox.setRoutingStatus(Constants.ACTIVE);

								addallvalues.add(routingcheckbox);
							}
						}else{
							RequestContext.getCurrentInstance().execute("nobranch.show();");
						}
					}else{
						System.out.println("!!!!!!!!!!bankbranchspecific not Selected!!!!!!!!!!!!!!!");
					}
				}else{
					setBranchApplicability(null);
					RequestContext.getCurrentInstance().execute("selectrecords.show();");
				}
			}else
			{
				specificCheckinginDataBase();
			}

		}catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::specificCheckinginDataBase"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}

	}

	public void approveRecordAgent(RoutingSetUpDetailsDialogCheckBox dataTable){
		try{
			if(!(dataTable.getModifiedBy() == null ?  dataTable.getCreatedBy() : dataTable.getModifiedBy()).equalsIgnoreCase(sessionManage.getUserName())){
				List<RoutingDetails> routingDetailsList=iroutingSetUpDetailsService.getAllReCordsFormHeaderAndDetais(dataTable.getRoutingSetupHeaderId());
				if(routingDetailsList.size()>0){
					iroutingSetUpDetailsService.getAllDealDetilasList(routingDetailsList.get(0).getRoutingDetailsId(),sessionManage.getUserName());

				}
				iroutingSetUpDetailsService.approveRecord(dataTable.getRoutingSetupHeaderId(),sessionManage.getUserName());
				RequestContext.getCurrentInstance().execute("approve.show();");
				routingAgentList.remove(dataTable);
			}else{
				RequestContext.getCurrentInstance().execute("sameUser.show();");
				return;
			}
		}catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::approveRecordAgent"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}

	// cash product bank Applicability ALL/SPECIFIC to specify - blocked senario fail
	/*public void checkBankApplicabilityDataTable(RoutingSetUpDetailsDialogCheckBox routingSetUpDetailsDialogCheckBox)
		{

			if(routingSetUpDetailsDialogCheckBox.getRoutingBranchId()!=null)
			{
				int i = 0;
				if(routingSetUpDetailsDialogCheckBox.getBranchApplicability() != null){
					if(routingAgentList != null && routingAgentList.size() > 1){
						for (RoutingSetUpDetailsDialogCheckBox lstRoutingAgent : routingAgentList) {
							if(routingSetUpDetailsDialogCheckBox.getBankId() != null && lstRoutingAgent.getBankId() != null 
									&& lstRoutingAgent.getBankId().compareTo(routingSetUpDetailsDialogCheckBox.getBankId())==0){
								if(lstRoutingAgent.getBankId().compareTo(getRoutingbankId())!=0){
									if(routingSetUpDetailsDialogCheckBox.getBranchApplicability().equalsIgnoreCase(Constants.ALL)){
										int j = 0;
										for(RoutingSetUpDetailsDialogCheckBox lstRoutingAgentBank : routingAgentList){
											if(lstRoutingAgentBank.getBranchApplicability() != null 
													&& lstRoutingAgentBank.getBranchApplicability().equalsIgnoreCase(Constants.ALL)){
												j ++;
											}
										}
										if(j <= 1){
											i = 0;
											System.out.println("new agent or bank - add");
											break;
										}else{
											// error message Already All Available
											i = 1;
											System.out.println("error message Already All Available");
											break;
										}
									}else{
										// for Agents should not allow "Specific". Always "ALL" for Agents.
										i = 1;
										System.out.println("for Agents should not allow Specific. Always ALL for Agents");
										break;
									}
								}else{
									if(lstRoutingAgent.getBranchApplicability().equalsIgnoreCase(routingSetUpDetailsDialogCheckBox.getBranchApplicability())){
										if(routingSetUpDetailsDialogCheckBox.getBranchApplicability().equalsIgnoreCase(Constants.ALL)){
											int j = 0;
											for(RoutingSetUpDetailsDialogCheckBox lstRoutingAgentBank : routingAgentList){
												if(lstRoutingAgentBank.getBranchApplicability() != null 
														&& lstRoutingAgentBank.getBranchApplicability().equalsIgnoreCase(Constants.ALL)){
													j ++;
												}
											}
											if(j <= 1){
												i = 0;
												System.out.println("new agent or bank - add");
												break;
											}else{
												// error message Already All Available
												i = 1;
												System.out.println("error message Already All Available");
												break;
											}
										}else{
											// specific every time allow - add
											i = 0;
											System.out.println("specific every time allow - add");
										}
									}else{
										// error message Already another applicability created
										i = 1;
										System.out.println("error message Already another applicability created");
										break;
									}
								}
							}else{
								// new agent or bank - add
								int j = 0;
								for(RoutingSetUpDetailsDialogCheckBox lstRoutingAgentBank : routingAgentList){
									if(lstRoutingAgentBank.getBankId() != null && lstRoutingAgentBank.getBankId().compareTo(getRoutingbankId())!=0){
										j ++;
									}
								}
								if(j <= 1){
									i = 0;
									System.out.println("new agent or bank - add");
									break;
								}else{
									i = 0;
									System.out.println("new agent or bank - add");
								}
							}
						}
					}else{

						if(routingSetUpDetailsDialogCheckBox.getBankId().compareTo(getRoutingbankId())!=0){
							if(routingSetUpDetailsDialogCheckBox.getBranchApplicability() != null 
									&& routingSetUpDetailsDialogCheckBox.getBranchApplicability().equalsIgnoreCase(Constants.ALL)){
								// new to dataTable - add
								i = 0;
								System.out.println("new to dataTable - add");
							}else{
								// for Agents should not allow "Specific". Always "ALL" for Agents.
								i = 1;
								System.out.println("for Agents should not allow Specific. Always ALL for Agents");
							}
						}else{
							// new to dataTable - add
							i = 0;
							System.out.println("new to dataTable - add");
						}
					}
				}

				if(i==0){

					if(routingSetUpDetailsDialogCheckBox.getDynamicLabelActivateDeActivate() != null && !routingSetUpDetailsDialogCheckBox.getDynamicLabelActivateDeActivate().equalsIgnoreCase(Constants.REMOVE)){
						routingSetUpDetailsDialogCheckBox.setIsActive(Constants.U);
						routingSetUpDetailsDialogCheckBox.setDynamicLabelActivateDeActivate(Constants.PENDING_FOR_APPROVE);
						routingSetUpDetailsDialogCheckBox.setDataTableStatus(Constants.UNAPPROVED);
					}
				}else{
					routingSetUpDetailsDialogCheckBox.setBranchApplicability(null);
					System.out.println("error message record exist in below combination ");
				}
			}else
			{
				routingSetUpDetailsDialogCheckBox.setBankId(null);
				RequestContext.getCurrentInstance().execute("noBankBranchselection.show();");
			}

		}*/


	// cash product bank Applicability ALL/SPECIFIC to specify - blocked senario fail
	public void checkBankApplicabilityDataTable(RoutingSetUpDetailsDialogCheckBox routingSetUpDetailsDialogCheckBox){
		if(routingSetUpDetailsDialogCheckBox.getRoutingBranchId()!=null)
		{
			int i = 0;
			if(routingSetUpDetailsDialogCheckBox.getBranchApplicability() != null){
				if(routingAgentList != null && routingAgentList.size() > 1){
					for (RoutingSetUpDetailsDialogCheckBox lstRoutingAgent : routingAgentList) {
						if(routingSetUpDetailsDialogCheckBox.getBankId() != null && lstRoutingAgent.getBankId() != null 
								&& lstRoutingAgent.getBankId().compareTo(routingSetUpDetailsDialogCheckBox.getBankId())==0){
							if(lstRoutingAgent.getBankId().compareTo(getRoutingbankId())!=0){
								if(routingSetUpDetailsDialogCheckBox.getBranchApplicability().equalsIgnoreCase(Constants.ALL)){
									int j = 0;
									for(RoutingSetUpDetailsDialogCheckBox lstRoutingAgentBank : routingAgentList){
										if(lstRoutingAgentBank.getBranchApplicability() != null 
												&& lstRoutingAgentBank.getBranchApplicability().equalsIgnoreCase(Constants.ALL)){
											j ++;
										}
									}
									if(j <= 1){
										i = 0;
										System.out.println("new agent or bank - add");
										break;
									}else{
										// error message Already All Available
										i = 1;
										System.out.println("error message Already All Available");
										break;
									}
								}else{
									// for Agents should not allow "Specific". Always "ALL" for Agents.
									i = 1;
									System.out.println("for Agents should not allow Specific. Always ALL for Agents");
									break;
								}
							}else{
								if(lstRoutingAgent.getBranchApplicability().equalsIgnoreCase(routingSetUpDetailsDialogCheckBox.getBranchApplicability())){
									if(routingSetUpDetailsDialogCheckBox.getBranchApplicability().equalsIgnoreCase(Constants.ALL)){
										int j = 0;
										for(RoutingSetUpDetailsDialogCheckBox lstRoutingAgentBank : routingAgentList){
											if(lstRoutingAgentBank.getBranchApplicability() != null 
													&& lstRoutingAgentBank.getBranchApplicability().equalsIgnoreCase(Constants.ALL)){
												j ++;
											}
										}
										if(j <= 1){
											i = 0;
											System.out.println("new agent or bank - add");
											break;
										}else{
											// error message Already All Available
											i = 1;
											System.out.println("error message Already All Available");
											break;
										}
									}else{
										// specific every time allow - add
										i = 0;
										System.out.println("specific every time allow - add");
									}
								}else{
									// error message Already another applicability created
									i = 1;
									System.out.println("error message Already another applicability created");
									break;
								}
							}
						}else{
							// new agent or bank - add
							int j = 0;
							for(RoutingSetUpDetailsDialogCheckBox lstRoutingAgentBank : routingAgentList){
								if(lstRoutingAgentBank.getBankId() != null && lstRoutingAgentBank.getBankId().compareTo(routingSetUpDetailsDialogCheckBox.getBankId())==0){
									j ++;
								}
							}
							if(j <= 1){
								i = 0;
								System.out.println("new agent or bank - add");
								break;
							}else{
								i = 0;
								System.out.println("new agent or bank - add");
							}
						}
					}
				}else{
					i = 0;
					System.out.println("new to dataTable - add");
				}
			}

			if(i==0){
				if(routingSetUpDetailsDialogCheckBox.getDynamicLabelActivateDeActivate() != null && !routingSetUpDetailsDialogCheckBox.getDynamicLabelActivateDeActivate().equalsIgnoreCase(Constants.REMOVE)){
					routingSetUpDetailsDialogCheckBox.setIsActive(Constants.U);
					routingSetUpDetailsDialogCheckBox.setDynamicLabelActivateDeActivate(Constants.PENDING_FOR_APPROVE);
					routingSetUpDetailsDialogCheckBox.setDataTableStatus(Constants.UNAPPROVED);
				}
			}else{
				routingSetUpDetailsDialogCheckBox.setBranchApplicability(null);
				System.out.println("error message record exist in below combination ");
				setErrorMessage("Record Exist in Below Combination"); 
				RequestContext.getCurrentInstance().execute("error.show();");
			}

		}else
		{
			routingSetUpDetailsDialogCheckBox.setBankId(null);
			routingSetUpDetailsDialogCheckBox.setBranchApplicability(null);
			RequestContext.getCurrentInstance().execute("noBankBranchselection.show();");
		}
	}
}
