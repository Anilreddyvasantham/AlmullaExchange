package com.amg.exchange.treasury.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.remittance.model.ViewPIPSType;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.BeneCountryService;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.PipsMaster;
import com.amg.exchange.treasury.model.ServiceMaster;
import com.amg.exchange.treasury.model.ServiceMasterDesc;
import com.amg.exchange.treasury.service.IPipsMasterService;
import com.amg.exchange.treasury.service.IRatesUpdateService;
import com.amg.exchange.treasury.service.ISpecialCustomerDealRequestService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;
import com.sun.org.apache.xerces.internal.utils.Objects;

/*
 * Author Rahamathali Shaik
 */
@Component("pipsMasterBean")
@Scope("session")
public class PipsMasterBean<T> implements Serializable{

	
	/**
	   * 
	   */
	  private static final long serialVersionUID = 1L;
	  private static final Logger log=Logger.getLogger(PipsMasterBean.class);
	private BigDecimal countryId = null;
	private BigDecimal currencyId = null;
	private BigDecimal bankId = null;
	private BigDecimal serviceId = null;
	private BigDecimal branchId = null;
	private BigDecimal fromAmount = null;
	private BigDecimal toAmount = null;
	private BigDecimal pips = null;
	private BigDecimal pipsMasterPk = null;
	private Boolean booRenderSaveExit = false;
	private Boolean booRenderDataTable = false;

	private List<CountryMasterDesc> countryList = new ArrayList<CountryMasterDesc>();
	private List<CurrencyMaster> currencyList = new ArrayList<CurrencyMaster>();
	private List<BankMaster> bankList = new ArrayList<BankMaster>();
	private List<CountryBranch> countryBranchList = new ArrayList<CountryBranch>();
	/*
	 * private List<ServiceIndicator> serviceList = new
	 * ArrayList<ServiceIndicator>();
	 */
	private List<PipsMasterDataTable> pipsMasterDataTableList = new ArrayList<PipsMasterDataTable>();
	private SessionStateManage session = new SessionStateManage();
	private List<ServiceMasterDesc> serviceMasters = new ArrayList<ServiceMasterDesc>();

	@Autowired
	IGeneralService<?> generalService;

	@Autowired
	IRatesUpdateService<?> ratesUpdateService;

	@Autowired
	ISpecialCustomerDealRequestService<?> specialCustomerDealRequestService;

	@Autowired
	IPipsMasterService pipsMasterService;

	public Boolean getBooRenderSaveExit() {
		return booRenderSaveExit;
	}

	public void setBooRenderSaveExit(Boolean booRenderSaveExit) {
		this.booRenderSaveExit = booRenderSaveExit;
	}

	public Boolean getBooRenderDataTable() {
		return booRenderDataTable;
	}

	public void setBooRenderDataTable(Boolean booRenderDataTable) {
		this.booRenderDataTable = booRenderDataTable;
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

	public BigDecimal getBankId() {
		return bankId;
	}

	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}

	public BigDecimal getServiceId() {
		return serviceId;
	}

	public void setServiceId(BigDecimal serviceId) {
		this.serviceId = serviceId;
	}

	public BigDecimal getBranchId() {
		return branchId;
	}

	public void setBranchId(BigDecimal branchId) {
		this.branchId = branchId;
	}

	public BigDecimal getFromAmount() {
		return fromAmount;
	}

	public void setFromAmount(BigDecimal fromAmount) {
		this.fromAmount = fromAmount;
	}

	public BigDecimal getToAmount() {
		return toAmount;
	}

	public void setToAmount(BigDecimal toAmount) {
		this.toAmount = toAmount;
	}

	public BigDecimal getPips() {
		return pips;
	}

	public void setPips(BigDecimal pips) {
		this.pips = pips;
	}

	/*
	 * public List<ServiceIndicator> getServiceList() { return
	 * ratesUpdateService.getServiceIndicatorList(); }
	 * 
	 * public void setServiceList(List<ServiceIndicator> serviceList) {
	 * this.serviceList = serviceList; }
	 */

	public List<CountryMasterDesc> getCountryList() {
		return generalService.getCountryList(session.getLanguageId());
	}

	public void setCountryList(List<CountryMasterDesc> countryList) {
		this.countryList = countryList;
	}

	public List<CurrencyMaster> getCurrencyList() {
		return generalService.getCurrencyList();
	}

	public void setCurrencyList(List<CurrencyMaster> currencyList) {
		this.currencyList = currencyList;
	}

	public List<BankMaster> getBankList() {
		return generalService.getAllBankListFromBankMaster();
	}

	public void setBankList(List<BankMaster> bankList) {
		this.bankList = bankList;
	}

	public List<CountryBranch> getCountryBranchList() {
		return ratesUpdateService.getCountryBranchList(session.getCountryId());
	}

	public void setCountryBranchList(List<CountryBranch> countryBranchList) {
		this.countryBranchList = countryBranchList;
	}

	public List<PipsMasterDataTable> getPipsMasterDataTableList() {
		return pipsMasterDataTableList;
	}

	public void setPipsMasterDataTableList(
			List<PipsMasterDataTable> pipsMasterDataTableList) {
		this.pipsMasterDataTableList = pipsMasterDataTableList;
	}

	public void clearAllFields() {
		// setCountryId(null);
		// setCurrencyId(null);
		setBankId(null);
		setServiceId(null);
		setBranchId(null);
		setFromAmount(null);
		setToAmount(null);
		setPips(null);
		setPipsTypeCode(null);

	}

	public void clearAll() {
		setPipsMasterPk(null);
		setCountryId(null);
		setCurrencyId(null);
		setBankId(null);
		setServiceId(null);
		setBranchId(null);
		setFromAmount(null);
		setToAmount(null);
		setPips(null);
		setPipsTypeCode(null);

	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void pipsMasterPageNavigation() {
		clearAllFields();
		setCountryId(null);
		setCurrencyId(null);
		setBooRenderDataTable(false);
		setBooRenderSaveExit(false);
		setApprov(false);
		setBooAdd(true);
		
		setBooBankInput(false);
		setBooBankList(true);
		
		setBooContryInput( false);
		setBooContryList( true);
		
		setBooBranchInput(false);
		setBooBranchList( true);
		
		setBooCurrencyInput(false);
		setBooCurrencyList(true);
		
		setBooServiceInput(false);
		setBooServiceList(true);
		setLstPipsIndView(null);
		setBooApp(false);
		toFetchPipsTypeDesc();
		pipsMasterDataTableList.clear();
		pipListDt.clear();
		
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "PipsMaster.xhtml");
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../treasury/PipsMaster.xhtml");
		} catch(Exception exception){
        	    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
        	    setErrorMessage(exception.getMessage()); 
        	    RequestContext.getCurrentInstance().execute("error.show();");
        	    return;       
        	  }

	}

	public void addRecordsToDataTable() {
		
		/*List<PipsMaster> lstPipsMaster = pipsMasterService.pipsListFrmDB(getCountryId(),getCurrencyId(), getBranchId(), getServiceId(), getBankId());
		
		for(PipsMaster pipsMaster :lstPipsMaster)
		{
			
		}*//*
		if (pipListDt.size() == 0) {
			PipsMasterDataTable pipsMasterDTObj = new PipsMasterDataTable();
			pipsMasterDTObj.setPipsMasterPk(getPipsMasterPk());
			pipsMasterDTObj.setCountryId(getCountryId());
			pipsMasterDTObj.setCountryName(generalService.getCountryName(session.getLanguageId(), getCountryId()));
			pipsMasterDTObj.setCurrencyId(getCurrencyId());
			pipsMasterDTObj.setCurrencyName(specialCustomerDealRequestService.getCurrencyForUpdate(getCurrencyId()));
			
			
			pipsMasterDTObj.setBankId(getBankId());
			pipsMasterDTObj.setBankName(specialCustomerDealRequestService.getBankNameForUpdate(getBankId()));
			pipsMasterDTObj.setServiceId(getServiceId());
			pipsMasterDTObj.setServiceName(ratesUpdateService.getServiceName(getServiceId()));

			pipsMasterDTObj.setBranchId(getBranchId());
			pipsMasterDTObj.setBranchName(ratesUpdateService.getBranchName(getBranchId()));
			pipsMasterDTObj.setFromAmount(getFromAmount());
			pipsMasterDTObj.setToAmount(getToAmount());
			pipsMasterDTObj.setPips(getPips());

			pipsMasterDataTableList.add(pipsMasterDTObj);
		}
		if (pipsMasterDataTableList.size() > 0) {

			if (pipListDt.size() > 0) {
				for (PipsMasterDataTable pipsDt1 : pipsMasterDataTableList) {
					for (PipsMasterDataTable pipsDt2 : pipListDt) {
						if (pipsDt1.getCountryId().toString()
								.equals(pipsDt2.getCountryId().toString())
								&& pipsDt1
										.getCurrencyId()
										.toString()
										.equals(pipsDt2.getCurrencyId()
												.toString())
								&& pipsDt1
										.getBranchId()
										.toString()
										.equals(pipsDt2.getBranchId()
												.toString())
								&& pipsDt1
										.getServiceId()
										.toString()
										.equals(pipsDt2.getServiceId()
												.toString())) {
							pipListDt.remove(pipsDt2);
						}

					}

				}

			}
			pipsMasterDataTableList.addAll(pipListDt);
		} else {
			pipsMasterDataTableList.addAll(pipListDt);

		}*/
		//clearAllFields();
		//clearAll();
		try{
		PipsMasterDataTable pipsMasterDTObj = new PipsMasterDataTable();
		pipsMasterDTObj.setPipsMasterPk(getPipsMasterPk());
		pipsMasterDTObj.setCountryId(getCountryId());
		pipsMasterDTObj.setCountryName(generalService.getCountryName(session.getLanguageId(), getCountryId()));
		pipsMasterDTObj.setCurrencyId(getCurrencyId());
		pipsMasterDTObj.setCurrencyName(specialCustomerDealRequestService.getCurrencyForUpdate(getCurrencyId()));
		pipsMasterDTObj.setPipsTypeCode(getPipsTypeCode());
		String pipsFullName=pipsMasterService.toFetchFullNameTypeCode(getPipsTypeCode());
		if(pipsFullName != null){
			pipsMasterDTObj.setPipsTypeFullName(pipsFullName);
		}
		
		pipsMasterDTObj.setBankId(getBankId());
		pipsMasterDTObj.setBankName(specialCustomerDealRequestService.getBankNameForUpdate(getBankId()));
		if(getServiceId()!=null)
		{
			pipsMasterDTObj.setServiceId(getServiceId());
			pipsMasterDTObj.setServiceName(ratesUpdateService.getServiceName(new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId") : "1"),getServiceId()));

		}
		if(getBranchId()!=null)
		{
			pipsMasterDTObj.setBranchId(getBranchId());
			pipsMasterDTObj.setBranchName(ratesUpdateService.getBranchName(getBranchId()));
			
		}
		pipsMasterDataTableList.add(pipsMasterDTObj);
		setBooRenderDataTable(true);
		setBooRenderSaveExit(true);
		}catch(NullPointerException ne){
		  log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
		    setErrorMessage("Method Name::addRecordsToDataTable"); 
		    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
		    return; 
        	  }catch(Exception exception){
        	    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
        	    setErrorMessage(exception.getMessage()); 
        	    RequestContext.getCurrentInstance().execute("error.show();");
        	    return;       
        	  }

	}

	public void editRecord(PipsMasterDataTable pipsMasterDTObj) {
          try{
        	setPipsMasterPk(pipsMasterDTObj.getPipsMasterPk());
        	setCountryId(pipsMasterDTObj.getCountryId());
        	setCurrencyId(pipsMasterDTObj.getCurrencyId());
        	setBankId(pipsMasterDTObj.getBankId());
        	setServiceId(pipsMasterDTObj.getServiceId());
        	setBranchId(pipsMasterDTObj.getBranchId());
        	setFromAmount(pipsMasterDTObj.getFromAmount());
        	setToAmount(pipsMasterDTObj.getToAmount());
        	setPips(pipsMasterDTObj.getPips());
        	pipsMasterDataTableList.remove(pipsMasterDTObj);
          }catch(Exception exception){
	    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
	    setErrorMessage(exception.getMessage()); 
	    RequestContext.getCurrentInstance().execute("error.show();");
	    return;       
          }
	}

	public void removeRecord(PipsMasterDataTable pipsMasterDTObj)throws Exception {

		if (pipsMasterDTObj.getPipsMasterPk() == null) {

			pipsMasterDataTableList.remove(pipsMasterDTObj);

		} else {
			deleteRecord(pipsMasterDTObj);
			pipsMasterDataTableList.remove(pipsMasterDTObj);

		}
		/*
		 * pipsMasterDataTableList.remove(pipsMasterDTObj); if
		 * (pipsMasterDataTableList.size() == 0) { setBooRenderDataTable(false);
		 * setBooRenderSaveExit(false); }
		 */
	}

	public void saveDataTableRecods() {

		if (pipsMasterDataTableList.isEmpty()) {
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("dataNotFund.show();");
		} else {
		  try{
			String localErrorMessage=null;
			boolean mandatoryCheck=true;
			for (PipsMasterDataTable pipsMasterDTObj : pipsMasterDataTableList)
			{
				if(pipsMasterDTObj.getFromAmount()==null || pipsMasterDTObj.getToAmount()==null || pipsMasterDTObj.getPips()==null)
				{
					localErrorMessage="Country Name : "+(pipsMasterDTObj.getCountryName()==null ? "-" :pipsMasterDTObj.getCountryName())+" / CurrencyName : " +
							(pipsMasterDTObj.getCurrencyName()== null ? "----" : pipsMasterDTObj.getCurrencyName())+" / Bank Name : " +
									(pipsMasterDTObj.getBankName() == null ? "----" :pipsMasterDTObj.getBankName())+" / Service Name : " +
									(pipsMasterDTObj.getServiceName()== null ? "----" :pipsMasterDTObj.getServiceName())+" / Branch Name : " +
									(pipsMasterDTObj.getBranchName() == null ? "----" :pipsMasterDTObj.getBranchName())+" / Pips Type Code : " +
									(pipsMasterDTObj.getPipsTypeCode() == null ? "----" :pipsMasterDTObj.getPipsTypeCode());
					/*localErrorMessage=pipsMasterDTObj.getCountryName()==null ? "-" :pipsMasterDTObj.getCountryName();
					localErrorMessage=localErrorMessage+(pipsMasterDTObj.getCurrencyName()== null ? "-" : pipsMasterDTObj.getCurrencyName());
					localErrorMessage=localErrorMessage+(pipsMasterDTObj.getBankName() == null ? "-" :pipsMasterDTObj.getBankName());
					localErrorMessage=localErrorMessage+(pipsMasterDTObj.getServiceName()== null ? "-" :pipsMasterDTObj.getServiceName());
					localErrorMessage=localErrorMessage+(pipsMasterDTObj.getBranchName() == null ? "-" :pipsMasterDTObj.getBranchName());*/
					
					setErrorMessage(localErrorMessage);
					mandatoryCheck=false;
					break;
				}
			}
			
			if(mandatoryCheck)
			{
				for (PipsMasterDataTable pipsMasterDTObj : pipsMasterDataTableList) {

					
					PipsMaster pipsMasterObj = new PipsMaster();
					pipsMasterObj.setPipsMasterId(pipsMasterDTObj.getPipsMasterPk());
					
					if (pipsMasterDTObj.getPipsMasterPk() != null) {
						pipsMasterObj.setModifiedBy(session.getUserName());
						pipsMasterObj.setModifiedDate(new Date());

					}
					CountryMaster countryMaster = new CountryMaster();
					countryMaster.setCountryId(pipsMasterDTObj.getCountryId());
					pipsMasterObj.setCountryMaster(countryMaster);

					CurrencyMaster currencyMaster = new CurrencyMaster();
					currencyMaster.setCurrencyId(pipsMasterDTObj.getCurrencyId());
					pipsMasterObj.setCurrencyMaster(currencyMaster);

					BankMaster bankMaster = new BankMaster();
					bankMaster.setBankId(pipsMasterDTObj.getBankId());
					pipsMasterObj.setBankMaster(bankMaster);
					
					if(pipsMasterDTObj.getBranchId()!=null)
					{
						CountryBranch countryBranch = new CountryBranch();
						countryBranch.setCountryBranchId(pipsMasterDTObj.getBranchId());
						pipsMasterObj.setCountryBranch(countryBranch);
					}
					
					/*
					 * ServiceIndicator serviceIndicator = new ServiceIndicator();
					 * serviceIndicator
					 * .setServiceIndicatorId(pipsMasterDTObj.getServiceId());
					 * pipsMasterObj.setServiceIndicator(serviceIndicator);
					 */
					if(pipsMasterDTObj.getServiceId()!=null)
					{
						ServiceMaster serviceMaster = new ServiceMaster();
						serviceMaster.setServiceId(pipsMasterDTObj.getServiceId());
						pipsMasterObj.setserviceMaster(serviceMaster);
					}
					

					pipsMasterObj.setFromAmount(pipsMasterDTObj.getFromAmount());
					pipsMasterObj.setToAmount(pipsMasterDTObj.getToAmount());
					pipsMasterObj.setPipsNo(pipsMasterDTObj.getPips());
					pipsMasterObj.setCreatedBy(session.getUserName());
					pipsMasterObj.setCreatedDate(new Date());
					pipsMasterObj.setIsActive(Constants.U);
					pipsMasterObj.setPipsTypeCode(pipsMasterDTObj.getPipsTypeCode());
					pipsMasterService.saveRecord(pipsMasterObj);
				}
				
				RequestContext.getCurrentInstance().execute("complete.show();");
			}else
			{
				RequestContext.getCurrentInstance().execute("mandatoryFail.show();");
			}
        	} catch(NullPointerException ne){
		  log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
		    setErrorMessage("Method Name::saveDataTableRecods"); 
		    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
		    return; 
             }catch(Exception exception){
            log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
            setErrorMessage(exception.getMessage()); 
            RequestContext.getCurrentInstance().execute("error.show();");
            return;       
          }
	}
	}

	public void deleteRecord(PipsMasterDataTable pipsMasterObj) {
	      try{
		PipsMaster pipsMasters = new PipsMaster();
		pipsMasters.setPipsMasterId(pipsMasterObj.getPipsMasterPk());

		pipsMasters.setModifiedBy(session.getUserName());
		pipsMasters.setModifiedDate(new Date());

		CountryMaster countryMaster = new CountryMaster();
		countryMaster.setCountryId(pipsMasterObj.getCountryId());
		pipsMasters.setCountryMaster(countryMaster);

		CurrencyMaster currencyMaster = new CurrencyMaster();
		currencyMaster.setCurrencyId(pipsMasterObj.getCurrencyId());
		pipsMasters.setCurrencyMaster(currencyMaster);

		CountryBranch countryBranch = new CountryBranch();
		countryBranch.setCountryBranchId(pipsMasterObj.getBranchId());
		pipsMasters.setCountryBranch(countryBranch);

		BankMaster bankMaster = new BankMaster();
		bankMaster.setBankId(pipsMasterObj.getBankId());
		pipsMasters.setBankMaster(bankMaster);

		ServiceMaster serviceMaster = new ServiceMaster();
		serviceMaster.setServiceId(pipsMasterObj.getServiceId());
		pipsMasters.setserviceMaster(serviceMaster);

		pipsMasters.setFromAmount(pipsMasterObj.getFromAmount());
		pipsMasters.setToAmount(pipsMasterObj.getToAmount());
		pipsMasters.setPipsNo(pipsMasterObj.getPips());
		pipsMasters.setCreatedBy(session.getUserName());
		pipsMasters.setCreatedDate(new Date());
		pipsMasters.setIsActive(Constants.D);
		pipsMasterService.saveRecord(pipsMasters);
	      } catch(NullPointerException ne){
		  log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
		    setErrorMessage("Method Name::deleteRecord"); 
		    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
		    return; 
	      }catch(Exception exception){
		log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		setErrorMessage(exception.getMessage()); 
		RequestContext.getCurrentInstance().execute("error.show();");
		return;       
	      }

	}

	public void clickOnOKSave() {
		pipsMasterDataTableList.clear();
		setBooRenderDataTable(false);
		setBooRenderSaveExit(false);
		try {
			saveClearAllFields();
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../treasury/PipsMaster.xhtml");
		}catch(NullPointerException ne){
		  log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
		    setErrorMessage("Method Name::clickOnOKSave"
		    		    + ""); 
		    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
		    return; 
	      }catch(Exception exception){
		log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		setErrorMessage(exception.getMessage()); 
		RequestContext.getCurrentInstance().execute("error.show();");
		return;       
	      }
	}

	public void exit() throws IOException {
		if (session.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../registration/employeehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../registration/branchhome.xhtml");
		}
	}

	public List<ServiceMasterDesc> getServiceMasters() {
		  List<ServiceMasterDesc> serviceMasters=null;
		  try{
			    serviceMasters=ratesUpdateService
				.getServiceMastersList(session.getLanguageId());
		  }catch(Exception exception){
		    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		    setErrorMessage(exception.getMessage()); 
		  }
		  return serviceMasters;
	}

	public void setServiceMasters(List<ServiceMasterDesc> serviceMasters) {
		this.serviceMasters = serviceMasters;
	}

	List<PipsMaster> pipsList = new ArrayList<>();

	public List<PipsMaster> getPipsList() {
		return pipsList;
	}

	public void setPipsList(List<PipsMaster> pipsList) {
		this.pipsList = pipsList;
	}

	public BigDecimal getPipsMasterPk() {
		return pipsMasterPk;
	}

	public void setPipsMasterPk(BigDecimal pipsMasterPk) {
		this.pipsMasterPk = pipsMasterPk;
	}

	public void getAllDetailsToList() {

		/*if (pipsMasterDataTableList.size() != 0) {
			for (PipsMasterDataTable pipsDT : pipsMasterDataTableList) {
				if (pipsDT.getServiceId().equals(getServiceId())
						&& (pipsDT.getBankId().equals(getBankId())
								&& pipsDT.getCountryId().equals(getCountryId()) && (pipsDT
								.getBranchId().equals(getBranchId()) && (pipsDT
								.getServiceId().equals(getServiceId()) && (pipsDT
								.getCurrencyId().equals(getCurrencyId())))))) {
					setBankId(null);
					;
					setCountryId(null);
					setBranchId(null);
					setCurrencyId(null);
					setServiceId(null);
					setFromAmount(null);
					setToAmount(null);
					setPips(null);

					RequestContext.getCurrentInstance().execute(
							"duplicate.show();");

				}

			}
		}*/
		/*if (getBankId() != null && getBranchId() != null
				&& getCurrencyId() != null & getCountryId() != null
				&& getServiceId() != null) {
			addRecordsToDataTable();
		}*/
		addRecordsToDataTable();
	}

	public void exitDialog() {
		if (pipsMasterDataTableList.size() > 0) {
			setBooRenderDataTable(true);
			setBooRenderSaveExit(true);
			setBankId(null);
			setCountryId(null);
			setBranchId(null);
			setCurrencyId(null);
			setServiceId(null);
			setFromAmount(null);
			setToAmount(null);
			setPips(null);
			setPipsTypeCode(null);
		} else {
			setBooRenderDataTable(false);
			setBooRenderSaveExit(false);
			setCountryId(null);
			setBankId(null);
			setBranchId(null);
			setCurrencyId(null);
			setServiceId(null);
			setFromAmount(null);
			setToAmount(null);
			setPips(null);
			setPipsTypeCode(null);
		}
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../treasury/PipsMaster.xhtml");
		} catch(Exception exception){
		    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		    setErrorMessage(exception.getMessage()); 
		    RequestContext.getCurrentInstance().execute("error.show();");
		    return;       
		  }
	}

	public void updateRecord() {

		if (pipsMasterDataTableList.size() > 0) {
			setBooRenderDataTable(true);
			setBooRenderSaveExit(true);

		} else {
			setBooRenderDataTable(false);
			setBooRenderSaveExit(false);
		}

		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../treasury/PipsMaster.xhtml");
		} catch(Exception exception){
		    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		    setErrorMessage(exception.getMessage()); 
		    RequestContext.getCurrentInstance().execute("error.show();");
		    return;       
		}

	}

	List<PipsMaster> pipsMasterList = new CopyOnWriteArrayList<>();

	public void checkPipsCombination() {
	    try{
		pipsMasterList.clear();
		setPipsMasterPk(null);
		setFromAmount(null);
		setToAmount(null);
		setPips(null);

//		pipsList = pipsMasterService.pipsListFrmDB(getCountryId(),
//				getCurrencyId(), getBranchId(), getServiceId(), getBankId());
//
//		if (pipsList.size() > 0) {
//			// RequestContext.getCurrentInstance().execute("duplicate1.show();");
//
//			setPipsMasterPk(pipsList.get(0).getPipsMasterId());
//			setFromAmount(pipsList.get(0).getFromAmount());
//			setPips(pipsList.get(0).getPipsNo());
//			setToAmount(pipsList.get(0).getToAmount());
//			// RequestContext.getCurrentInstance().execute("duplicate.show();");
//
//		}
//
//		if (pipsMasterDataTableList.size() > 0) {
//			// setPipsMasterPk(pipsList.get(0).getPipsMasterId());
//			for (PipsMasterDataTable pipsDT : pipsMasterDataTableList) {
//
//				if (pipsDT.getServiceId().equals(getServiceId())
//						&& (pipsDT.getBankId().equals(getBankId())
//								&& pipsDT.getCountryId().equals(getCountryId()) && (pipsDT
//								.getBranchId().equals(getBranchId()) && (pipsDT
//								.getServiceId().equals(getServiceId()) && (pipsDT
//								.getCurrencyId().equals(getCurrencyId())))))) {
//
//					setBooRenderDataTable(true);
//					/*
//					 * setBankId(null); setBranchId(null); setCountryId(null);
//					 * setCurrencyId(null); setServiceId(null);
//					 * setFromAmount(null); setToAmount(null); setPips(null);
//					 */
//					// RequestContext.getCurrentInstance().execute(
//					// "duplicate1.show();");
//
//				}
//
//			}
//
//		}

		List<BeneCountryService> lstBeneCountryService = pipsMasterService.getCurrencyMaster(getCountryId());
		setLstBeneCountryService(lstBeneCountryService);
	    }catch(NullPointerException ne){
		  log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
		    setErrorMessage("Method Name::saveDataTableRecods"); 
		    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
		    return; 
	  }catch(Exception exception){
	    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
	    setErrorMessage(exception.getMessage()); 
	    RequestContext.getCurrentInstance().execute("error.show();");
	    return;       
	  }
		
	}

	public void populatePipsCombination()
	{
	try{	  
		List<PipsMaster> lstPipsMaster=pipsMasterService.populatePipsListFrmDB(getCountryId(),getCurrencyId(),getBankId());
		pipsMasterDataTableList.clear();
		currencyClearFields();
		if(lstPipsMaster.size()>0)
		{
			
			for(PipsMaster pipsMaster :lstPipsMaster)
			{
				PipsMasterDataTable pipsMasterDTObj = new PipsMasterDataTable();
				pipsMasterDTObj.setPipsMasterPk(pipsMaster.getPipsMasterId());
				pipsMasterDTObj.setCountryId(pipsMaster.getCountryMaster().getCountryId());
				pipsMasterDTObj.setCountryName(generalService.getCountryName(session.getLanguageId(), pipsMaster.getCountryMaster().getCountryId()));
				pipsMasterDTObj.setCurrencyId(pipsMaster.getCurrencyMaster().getCurrencyId());
				pipsMasterDTObj.setCurrencyName(specialCustomerDealRequestService.getCurrencyForUpdate(pipsMaster.getCurrencyMaster().getCurrencyId()));
				
				
				pipsMasterDTObj.setBankId(pipsMaster.getBankMaster().getBankId());
				pipsMasterDTObj.setBankName(specialCustomerDealRequestService.getBankNameForUpdate(pipsMaster.getBankMaster().getBankId()));
				
				pipsMasterDTObj.setPipsTypeCode(pipsMaster.getPipsTypeCode());
				String pipsFullName=pipsMasterService.toFetchFullNameTypeCode(pipsMaster.getPipsTypeCode());
				if(pipsFullName != null){
					pipsMasterDTObj.setPipsTypeFullName(pipsFullName);
				}
				if(pipsMaster.getserviceMaster()!=null)
				{
					pipsMasterDTObj.setServiceId(pipsMaster.getserviceMaster().getServiceId());
					pipsMasterDTObj.setServiceName(ratesUpdateService.getServiceName(new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId") : "1"),pipsMaster.getserviceMaster().getServiceId()));
				}
				

				
				if(pipsMaster.getCountryBranch()!=null)
				{
					pipsMasterDTObj.setBranchId(pipsMaster.getCountryBranch().getCountryBranchId());
					pipsMasterDTObj.setBranchName(ratesUpdateService.getBranchName(pipsMaster.getCountryBranch().getCountryBranchId()));
				}
				
				pipsMasterDTObj.setFromAmount(pipsMaster.getFromAmount());
				pipsMasterDTObj.setToAmount(pipsMaster.getToAmount());
				pipsMasterDTObj.setPips(pipsMaster.getPipsNo());

				pipsMasterDataTableList.add(pipsMasterDTObj);
				
			}
			setBooRenderSaveExit(true);
			setBooRenderDataTable(true);
		}else
		{
			setBooRenderDataTable(false);
			setBooRenderSaveExit(false);
		}
        	}catch(NullPointerException ne){
        		  log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
        		    setErrorMessage("Method Name::saveDataTableRecods"); 
        		    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
        		    return; 
                }catch(Exception exception){
                  log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
                  setErrorMessage(exception.getMessage()); 
                  RequestContext.getCurrentInstance().execute("error.show();");
                  return;       
                }
	}
	List<PipsMasterDataTable> pipListDt = new CopyOnWriteArrayList<PipsMasterDataTable>();

	public void view() {
	    try{
		List<PipsMaster> pipList = pipsMasterService.getAllPipsList();
		System.out.println("+++++++++++++++++++++++++++++++++++"
				+ pipList.size());

		if (pipList.size() > 0) {
	 
			for (PipsMaster pips : pipList) {
				PipsMasterDataTable pipsDT = new PipsMasterDataTable();
				pipsDT.setPipsMasterPk(pips.getPipsMasterId());

				pipsDT.setBankId(pips.getBankMaster().getBankId());
				pipsDT.setBankName((specialCustomerDealRequestService
						.getBankNameForUpdate(pips.getBankMaster().getBankId())));

				pipsDT.setCurrencyId(pips.getCurrencyMaster().getCurrencyId());
				pipsDT.setCurrencyName(specialCustomerDealRequestService
						.getCurrencyForUpdate(pips.getCurrencyMaster()
								.getCurrencyId()));

				pipsDT.setBranchId(pips.getCountryBranch().getCountryBranchId());
				pipsDT.setBranchName(ratesUpdateService.getBranchName(pips
						.getCountryBranch().getCountryBranchId()));

				pipsDT.setCountryId(pips.getCountryMaster().getCountryId());
				pipsDT.setCountryName(generalService.getCountryName(session.getLanguageId(), pips.getCountryMaster().getCountryId()));

				pipsDT.setServiceId(pips.getserviceMaster().getServiceId());
				pipsDT.setServiceName(ratesUpdateService.getServiceName(new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId") : "1"),pips.getserviceMaster().getServiceId()));

				pipsDT.setPips(pips.getPipsNo());
				pipsDT.setFromAmount(pips.getFromAmount());
				pipsDT.setToAmount(pips.getToAmount());

				pipListDt.add(pipsDT);
			}

		}
		if (pipListDt.size() > 0) {
			addRecordsToDataTable();
			pipListDt.clear();
		} else {

			RequestContext.getCurrentInstance().execute("exist.show();");
		}
	    }catch(NullPointerException ne){
			  log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			    setErrorMessage("Method Name::saveDataTableRecods"); 
			    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			    return; 
	  }catch(Exception exception){
	    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
	    setErrorMessage(exception.getMessage()); 
	    RequestContext.getCurrentInstance().execute("error.show();");
	    return;       
	  }
	}
//APROVAL RELATED CODE
	List<PipsMasterDataTable> pipsApproveList = new ArrayList<PipsMasterDataTable>();
	private String createdBy=null;
	private String modifiedBy=null;
	private Date modifiedDate;
	private Date createdDate;
	private String isActive=null;
	private boolean booAdd=true;
	private boolean approv=false;
	private boolean booContryList=true;
	private boolean booContryInput=false;
	private boolean booApp=false;
	
	private boolean booCurrencyList=true;
	private boolean booCurrencyInput=false;
	
	private boolean booBankList=true;
	private boolean booBankInput=false;
	
	private boolean booBranchList=true;
	private boolean booBranchInput=false;
	
	private boolean booServiceList=true;
	private boolean booServiceInput=false;
	
	private String bankName=null;
	private String branchName=null;
	private String currencyName=null;
	private String serviceName=null;
	private String countryName=null;

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public ISpecialCustomerDealRequestService<?> getSpecialCustomerDealRequestService() {
		return specialCustomerDealRequestService;
	}

	public void setSpecialCustomerDealRequestService(
			ISpecialCustomerDealRequestService<?> specialCustomerDealRequestService) {
		this.specialCustomerDealRequestService = specialCustomerDealRequestService;
	}

	public boolean isBooContryList() {
		return booContryList;
	}

	public void setBooContryList(boolean booContryList) {
		this.booContryList = booContryList;
	}

	public boolean isBooContryInput() {
		return booContryInput;
	}

	public void setBooContryInput(boolean booContryInput) {
		this.booContryInput = booContryInput;
	}

	public boolean isBooCurrencyList() {
		return booCurrencyList;
	}

	public void setBooCurrencyList(boolean booCurrencyList) {
		this.booCurrencyList = booCurrencyList;
	}

	public boolean isBooCurrencyInput() {
		return booCurrencyInput;
	}

	public void setBooCurrencyInput(boolean booCurrencyInput) {
		this.booCurrencyInput = booCurrencyInput;
	}

	public boolean isBooBankList() {
		return booBankList;
	}

	public void setBooBankList(boolean booBankList) {
		this.booBankList = booBankList;
	}

	public boolean isBooBankInput() {
		return booBankInput;
	}

	public void setBooBankInput(boolean booBankInput) {
		this.booBankInput = booBankInput;
	}

	public boolean isBooBranchList() {
		return booBranchList;
	}

	public void setBooBranchList(boolean booBranchList) {
		this.booBranchList = booBranchList;
	}

	public boolean isBooBranchInput() {
		return booBranchInput;
	}

	public void setBooBranchInput(boolean booBranchInput) {
		this.booBranchInput = booBranchInput;
	}

	public boolean isBooServiceList() {
		return booServiceList;
	}

	public void setBooServiceList(boolean booServiceList) {
		this.booServiceList = booServiceList;
	}

	public boolean isBooServiceInput() {
		return booServiceInput;
	}

	public void setBooServiceInput(boolean booServiceInput) {
		this.booServiceInput = booServiceInput;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public List<PipsMasterDataTable> getPipsApproveList() {
		
		return pipsApproveList;
	}

	public void setPipsApproveList(List<PipsMasterDataTable> pipsApproveList) {
		this.pipsApproveList = pipsApproveList;
	}

	public void pipsApprove() {
		  try{
		pipsApproveList.clear();
		List<PipsMaster> pipsMasterAppList= pipsMasterService.getAllPipsApprove();
		System.out.println("**********************************************"+pipsMasterAppList.size());
		
		if(pipsMasterAppList.size()>0){
			for(PipsMaster pip:pipsMasterAppList){
			PipsMasterDataTable pipsapproveDT = new PipsMasterDataTable();
			try{
			pipsapproveDT.setPipsMasterPk(pip.getPipsMasterId());

			pipsapproveDT.setBankId(pip.getBankMaster().getBankId());
			pipsapproveDT.setBankName((specialCustomerDealRequestService
					.getBankNameForUpdate(pip.getBankMaster().getBankId())));

			pipsapproveDT.setCurrencyId(pip.getCurrencyMaster().getCurrencyId());
			pipsapproveDT.setCurrencyName(specialCustomerDealRequestService
					.getCurrencyForUpdate(pip.getCurrencyMaster()
							.getCurrencyId()));

			if(pip.getCountryBranch()!=null)
			{
				pipsapproveDT.setBranchId(pip.getCountryBranch().getCountryBranchId());
				pipsapproveDT.setBranchName(ratesUpdateService.getBranchName(pip
						.getCountryBranch().getCountryBranchId()));
			}
			

			pipsapproveDT.setCountryId(pip.getCountryMaster().getCountryId());
			pipsapproveDT.setCountryName(generalService.getCountryName(session.getLanguageId(), pip.getCountryMaster().getCountryId()));

			if(pip.getserviceMaster()!=null)
			{
				pipsapproveDT.setServiceId(pip.getserviceMaster().getServiceId());
				pipsapproveDT.setServiceName(ratesUpdateService.getServiceName(new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId") : "1"),pip.getserviceMaster().getServiceId()));
			}
			

			pipsapproveDT.setPips(pip.getPipsNo());
			pipsapproveDT.setFromAmount(pip.getFromAmount());
			pipsapproveDT.setToAmount(pip.getToAmount());
			pipsapproveDT.setCreatedBy( pip.getCreatedBy());
			pipsapproveDT.setCreatedDate(pip.getCreatedDate() );
			pipsapproveDT.setModifiedBy(pip.getModifiedBy() );
			pipsapproveDT.setModifiedDate(pip.getModifiedDate() );
			pipsapproveDT.setIsActive(pip.getIsActive() );
			pipsapproveDT.setPipsTypeCode(pip.getPipsTypeCode());
			String pipsFullName=pipsMasterService.toFetchFullNameTypeCode(pip.getPipsTypeCode());
			if(pipsFullName != null){
				pipsapproveDT.setPipsTypeFullName(pipsFullName);
			}
			}catch(Exception e){
				e.printStackTrace();
			}
			pipsApproveList.add(pipsapproveDT);
			}
			
		}
		  }catch(NullPointerException ne){
				  log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				    setErrorMessage("Method Name::saveDataTableRecods"); 
				    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				    return; 
		  }catch(Exception exception){
		    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		    setErrorMessage(exception.getMessage()); 
		    RequestContext.getCurrentInstance().execute("error.show();");
		    return;       
		  }

	}
	public void gotoServiceApproval(PipsMasterDataTable datatable) {
		if(!(datatable.getModifiedBy()==null?datatable.getCreatedBy():datatable.getModifiedBy()).equalsIgnoreCase( session.getUserName())){
		setPipsMasterPk(datatable.getPipsMasterPk() );
		
		setBankId(datatable.getBankId());
		setBankName(datatable.getBankName());
		
		setCurrencyId(datatable.getCurrencyId());
		setCurrencyName( datatable.getCurrencyName());
		
		setBranchId(datatable.getBranchId());
		setBranchName( datatable.getBranchName());
		
		setServiceId(datatable.getServiceId());
		setServiceName(datatable.getServiceName() );
		
		setFromAmount(datatable.getFromAmount());
		setToAmount(datatable.getToAmount());
		setPips(datatable.getPips());
		
		setPipsTypeCode(datatable.getPipsTypeFullName());
		setCountryId(datatable.getCountryId());
		setCountryName(datatable.getCountryName());
		
		setCreatedBy( datatable.getCreatedBy());
		setCreatedDate(datatable.getCreatedDate() );
		setModifiedBy( datatable.getModifiedBy());
		setModifiedDate( datatable.getModifiedDate());
		setIsActive( datatable.getIsActive());
		
		setBooAdd(false);
		setBooRenderDataTable(false);
		setBooRenderSaveExit(false);
		setApprov(true);
		
		setBooBankInput(true);
		setBooBankList(false);
		
		setBooContryInput( true);
		setBooContryList( false);
		
		setBooBranchInput(true);
		setBooBranchList( false);
		
		setBooCurrencyInput(true);
		setBooCurrencyList(false);
		
		setBooServiceInput(true);
		setBooServiceList(false);
		
		setBooApp( true);
		
		
		
		
		 
		 
		try {
			
			FacesContext.getCurrentInstance().getExternalContext()
				.redirect("../treasury/PipsMaster.xhtml");
		 
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		}else{
			RequestContext.getCurrentInstance().execute("notApproved.show();");
		}
		
	}


	public void populateValues()throws Exception {
		pipsApproveList.clear();
 
		
		try {
			pipsApprove();
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "PipsMasterApprove.xhtml");
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../remittance/PipsMasterApprove.xhtml");
			pipsApprove();
		} catch (IOException e) {
			 
			e.printStackTrace();
		}
	}
	  
	public void approveSelectedPips(){
		try{
		//if(!CommonUtil.validateApprovedBy(session.getUserName(),getCreatedBy())){
			/*PipsMaster pipsMasters = new PipsMaster();
			pipsMasters.setPipsMasterId(getPipsMasterPk());

			pipsMasters.setModifiedBy(getModifiedBy());
			pipsMasters.setModifiedDate(getModifiedDate());

			CountryMaster countryMaster = new CountryMaster();
			countryMaster.setCountryId(getCountryId());
			pipsMasters.setCountryMaster(countryMaster);

			CurrencyMaster currencyMaster = new CurrencyMaster();
			currencyMaster.setCurrencyId(getCurrencyId());
			pipsMasters.setCurrencyMaster(currencyMaster);

			if(getBranchId()!=null)
			{
				CountryBranch countryBranch = new CountryBranch();
				countryBranch.setCountryBranchId(getBranchId());
				pipsMasters.setCountryBranch(countryBranch);
			}
			

			BankMaster bankMaster = new BankMaster();
			bankMaster.setBankId(getBankId());
			pipsMasters.setBankMaster(bankMaster);

			if(getServiceId()!=null)
			{
				ServiceMaster serviceMaster = new ServiceMaster();
				serviceMaster.setServiceId(getServiceId());
				pipsMasters.setserviceMaster(serviceMaster);
			}
			

			pipsMasters.setFromAmount(getFromAmount());
			pipsMasters.setToAmount(getToAmount());
			pipsMasters.setPipsNo(getPips());
			pipsMasters.setCreatedBy(getCreatedBy());
			pipsMasters.setCreatedDate(getCreatedDate());
			pipsMasters.setIsActive(Constants.Yes);
			pipsMasters.setApprovedBy(session.getUserName());
			pipsMasters.setApprovedDate(new Date() );
			pipsMasters.setModifiedBy(getModifiedBy());
			setModifiedDate(getModifiedDate());
			pipsMasterService.saveRecord(pipsMasters);*/
			String approveMsg=pipsMasterService.approveRecord(getPipsMasterPk(), session.getUserName());
			if(approveMsg.equalsIgnoreCase("Success")){
			RequestContext.getCurrentInstance().execute("approve.show();");
			}else{
				RequestContext.getCurrentInstance().execute("alreadyapprov.show();");
			}
		}catch(NullPointerException ne){
			  log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			    setErrorMessage("Method Name::saveDataTableRecods"); 
			    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			    return; 
		}catch(Exception exception){
			  log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			  setErrorMessage(exception.getMessage()); 
			  RequestContext.getCurrentInstance().execute("error.show();");
			  return;       
		}
		/*}else{
			RequestContext.getCurrentInstance().execute("notApproved.show();");
		}*/
		
	}
	 
public void clickOnOkButton(){
	try {
		pipsApprove();
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("../remittance/PipsMasterApprove.xhtml");
	} catch (IOException e) {
		e.printStackTrace();
	}
}

	public void clickOnOKSaveApprove()throws Exception{
		setBankId(null);
		setBranchId(null);
		setServiceId(null);
		setCountryId( null);
		setCurrencyId(null);
		setFromAmount( null);
		setToAmount( null);
		setPips( null);
		setPipsMasterPk(null);
		setCreatedBy(null);
		setCreatedDate( null);
		setModifiedBy( null);
		setModifiedDate( null);
		
		 
		try {
			pipsApprove();
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../remittance/PipsMasterApprove.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public boolean isBooAdd() {
		return booAdd;
	}

	public void setBooAdd(boolean booAdd) {
		this.booAdd = booAdd;
	}

	public boolean isApprov() {
		return approv;
	}

	public void setApprov(boolean approv) {
		this.approv = approv;
	}

	public boolean isBooApp() {
		return booApp;
	}

	public void setBooApp(boolean booApp) {
		this.booApp = booApp;
	}

	private List<BeneCountryService> lstBeneCountryService = new ArrayList<BeneCountryService>();

	public List<BeneCountryService> getLstBeneCountryService() {
		return lstBeneCountryService;
	}

	public void setLstBeneCountryService(
			List<BeneCountryService> lstBeneCountryService) {
		this.lstBeneCountryService = lstBeneCountryService;
	}
	
	 public void onCellEdit(CellEditEvent event) {
	        Object oldValue = event.getOldValue();
	        Object newValue = event.getNewValue();
	         
	        if(newValue != null && !newValue.equals(oldValue)) {
	            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
	            FacesContext.getCurrentInstance().addMessage(null, msg);
	        }
	    }
	 
	 
	 public void checkFrom(PipsMasterDataTable pipsMasterFromObj )
	 {
		// pipsMasterToObj.setFromAmount(null);
		 //pipsMasterFromObj.setToAmount(null);
		 /*
		 

		 boolean checkFromRecord= false;
			
		 for (PipsMasterDataTable pipsMasterDTObj : pipsMasterDataTableList) {
			 
			 if(pipsMasterFromObj.getCountryId().compareTo(pipsMasterDTObj.getCountryId())==0)
			 {
				 if(pipsMasterFromObj.getCurrencyId().compareTo(pipsMasterDTObj.getCurrencyId())==0)
				 {
					 if(pipsMasterFromObj.getBankId().compareTo(pipsMasterDTObj.getBankId())==0)
					 {
						 if(pipsMasterFromObj.getServiceId().compareTo(pipsMasterDTObj.getServiceId())==0)
						 {
							 if(pipsMasterFromObj.getBranchId().compareTo(pipsMasterDTObj.getBranchId())==0)
							 {
								 boolean isRangeCheck=isInRange(pipsMasterFromObj.getFromAmount(),pipsMasterFromObj.getToAmount(), pipsMasterDTObj.getFromAmount(), pipsMasterDTObj.getToAmount());
								 if(isRangeCheck)
								 {
									 checkFromRecord= true;
								 }else
								 {
									 checkFromRecord= false;
									// higestToAnt=pipsMasterDTObj.getToAmount();
									 break;
								 }
								 
							 }else
							 {
								 checkFromRecord= true;
							 }
						 }else
						 {
							 checkFromRecord= true;
						 }
					 }else
					 {
						 checkFromRecord= true;
					 }
				 }
					 
			 }
			 
		 }
		 
	 
		 if(checkFromRecord)
		 {
			 nextHigestToAnt=higestToAnt.add(new BigDecimal(1));
			 if(nextHigestToAnt.compareTo(pipsMasterFromObj.getToAmount())==0)
			 {
				 System.out.println("ADDDD");
			 }
			 
		 }
		 
	 */}
	 
	 List<BigDecimal> lstToAmountCheck = new ArrayList<BigDecimal>();
	 
	 String errorMessage;
	 
	 public List<BigDecimal> getLstToAmountCheck() {
		return lstToAmountCheck;
	}

	public void setLstToAmountCheck(List<BigDecimal> lstToAmountCheck) {
		this.lstToAmountCheck = lstToAmountCheck;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void checkTo(PipsMasterDataTable pipsMasterToObj )
	 {
		try{ 
		 List<BigDecimal> lstToAmount = new ArrayList<BigDecimal>();
		 List<BigDecimal> lstFromAmount = new ArrayList<BigDecimal>();
		 
		 BigDecimal previouseToAnt= new BigDecimal(0);
		 BigDecimal higestToAnt= new BigDecimal(0);
		 BigDecimal nextHigestToAnt= new BigDecimal(0);
		
		 boolean checkToRecord= false;
		 List<PipsMasterDataTable> tempPipsMasterDataTableList = new ArrayList<PipsMasterDataTable>();
		 tempPipsMasterDataTableList.addAll(pipsMasterDataTableList);
		 
		 if(tempPipsMasterDataTableList.size()==1)
		 {
			 
		 }else
		 {
			 tempPipsMasterDataTableList.remove(pipsMasterToObj);
			 
			 for (PipsMasterDataTable pipsMasterDTObj : tempPipsMasterDataTableList) {
				 
				 if(pipsMasterToObj.getCountryId().compareTo(pipsMasterDTObj.getCountryId())==0)
				 {
					 if(pipsMasterToObj.getCurrencyId().compareTo(pipsMasterDTObj.getCurrencyId())==0)
					 {
						 if(pipsMasterToObj.getBankId().compareTo(pipsMasterDTObj.getBankId())==0)
						 {
							 if(Objects.equals(pipsMasterToObj.getServiceId(), pipsMasterDTObj.getServiceId()))//pipsMasterToObj.getServiceId().compareTo(pipsMasterDTObj.getServiceId())==0
							 {
								 if(Objects.equals(pipsMasterToObj.getBranchId(), pipsMasterDTObj.getBranchId()))//pipsMasterToObj.getBranchId().compareTo(pipsMasterDTObj.getBranchId())==0
								 {
									 
									 if(pipsMasterDTObj.getFromAmount()==null || pipsMasterDTObj.getToAmount()==null)
									 {
										 checkToRecord=true;
										 continue;
									 }
									
									/* if(pipsMasterDTObj.getFromAmount().compareTo(pipsMasterToObj.getFromAmount())==0 && pipsMasterDTObj.getToAmount().compareTo(pipsMasterToObj.getToAmount())==0)
									 {
										 checkToRecord=true;
										 continue;
									 }*/
									 
									 if(pipsMasterToObj.getPipsTypeCode().equalsIgnoreCase(pipsMasterDTObj.getPipsTypeCode()))
									 {
										 lstToAmount.add(pipsMasterDTObj.getToAmount());
										 lstFromAmount.add(pipsMasterDTObj.getFromAmount());
										 boolean isRangeCheck=isInRange(pipsMasterToObj.getFromAmount(),pipsMasterToObj.getToAmount(), pipsMasterDTObj.getFromAmount(), pipsMasterDTObj.getToAmount());
										 if(isRangeCheck)
										 {
											 if(previouseToAnt.compareTo(BigDecimal.ZERO)==0)
											 {
												 previouseToAnt=pipsMasterDTObj.getToAmount();
												 higestToAnt=previouseToAnt;
											 }else
											 {
												 int i=previouseToAnt.compareTo(pipsMasterDTObj.getToAmount());
												 
												 if(i==0)
												 {
													 higestToAnt=previouseToAnt;
												 }else if(i<0)
												 {
													 higestToAnt=pipsMasterDTObj.getToAmount();
												 }else if(i==1)
												 {
													 higestToAnt=previouseToAnt;
												 }
											 }
											 
											 
											 checkToRecord=true;
										 }else
										 {
											 checkToRecord= false;
											 higestToAnt=pipsMasterDTObj.getToAmount();
											 break;
										 }
									 }else
									 {
										 checkToRecord= true;
									 }
									
									 
								 }else
								 {
									 checkToRecord= true;
								 }
							 }else
							 {
								 checkToRecord= true;
							 }
						 }else
						 {
							 checkToRecord= true;
						 }
					 }
						 
				 }
				 
			 }
			 
			 if(checkToRecord)
			 {
				 if(higestToAnt.compareTo(BigDecimal.ZERO)==0)
				 {
					 
				 }else
				 {
					 BigDecimal maxToAmt = Collections.max(lstToAmount);
					 nextHigestToAnt=maxToAmt.add(new BigDecimal(1));
					 if(nextHigestToAnt.compareTo(pipsMasterToObj.getFromAmount())==0)
					 {
						 System.out.println("ADDDD");
					 }else
					 {
						 BigDecimal minFromAmt = Collections.min(lstFromAmount);
						 BigDecimal minAllowFrom=minFromAmt.subtract(new BigDecimal(1));
						 if(minAllowFrom.compareTo(pipsMasterToObj.getToAmount())==0)
						 {
							 System.out.println("ADDDD");
						 }else
						 {
							 RequestContext.getCurrentInstance().execute("bankcharges.show();");
							 pipsMasterToObj.setFromAmount(null);
							 pipsMasterToObj.setToAmount(null);
						 }
						
					 }
				 }
				 
				 
			 }else
			 {
				 RequestContext.getCurrentInstance().execute("bankcharges.show();");
				 pipsMasterToObj.setFromAmount(null);
				 pipsMasterToObj.setToAmount(null);
			 }
		 }
		 }catch(NullPointerException ne){
				  log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				    setErrorMessage("Method Name::saveDataTableRecods"); 
				    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				    return; 
		  }catch(Exception exception){
		    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		    setErrorMessage(exception.getMessage()); 
		    RequestContext.getCurrentInstance().execute("error.show();");
		    return;       
		  }
		 
		 
		/* for (PipsMasterDataTable pipsMasterDTObj : pipsMasterDataTableList) {
			 
			 if(pipsMasterToObj.getToAmount()!=null)
			 {
				 
			 }
		 }*/
		 //setLstToAmountCheck(lstToAmount);
		 
		
	 }
	 
	 private  boolean isInRange(BigDecimal frmoAmt, BigDecimal toAmt, BigDecimal tabFromAmt, BigDecimal tabToAmt) {
		 boolean returnValue=false;
		    try {
		    	
		    	if(frmoAmt.compareTo(toAmt)<0)
		    	{
		    		if(frmoAmt.compareTo(tabFromAmt)<0){
			    		if(toAmt.compareTo(tabFromAmt)<0)
			    		{
			    			returnValue=true;
			    		}else
			    		{
			    			returnValue=false;
			    		}
			    	}else if(frmoAmt.compareTo(tabToAmt)>0){
			    		if(toAmt.compareTo(tabToAmt)>0)
			    		{
			    			returnValue=true;
			    		}else
			    		{
			    			returnValue=false;
			    		}
			    	}
			    	
		    	}else
		    	{
		    		returnValue=false;
		    	}
		    	
		     
		    } catch (NumberFormatException e) {
		    	returnValue=false;
		    }
		    return returnValue;
		  }
	 
	 public void saveClearAllFields() {
		 setCountryId(null);
		 setCurrencyId(null);
		 setBankId(null);
		 setServiceId(null);
		 setBranchId(null);
		 setPipsTypeCode(null);

	 }
	 public void currencyClearFields() {
		 setBankId(null);
		 setServiceId(null);
		 setBranchId(null);

	 }
	 
	 private String pipsTypeCode;
	 private List<ViewPIPSType> lstPipsIndView;

	public String getPipsTypeCode() {
		return pipsTypeCode;
	}

	public void setPipsTypeCode(String pipsTypeCode) {
		this.pipsTypeCode = pipsTypeCode;
	}
	
	public List<ViewPIPSType> getLstPipsIndView() {
		return lstPipsIndView;
	}

	public void setLstPipsIndView(List<ViewPIPSType> lstPipsIndView) {
		this.lstPipsIndView = lstPipsIndView;
	}

	public void toFetchPipsTypeDesc(){
		try{

			List<ViewPIPSType> lstViewPips=pipsMasterService.toFetchAllPipsIndParamView();
			if(lstViewPips.size()>0){
				setLstPipsIndView(lstViewPips);
			}
		} catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::toFetchPipsTypeDesc"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}
	 
}
