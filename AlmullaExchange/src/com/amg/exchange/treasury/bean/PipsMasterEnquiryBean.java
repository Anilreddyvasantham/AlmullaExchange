/**
 * 
 */
package com.amg.exchange.treasury.bean;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;


import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.remittance.model.ViewPIPSType;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.BeneCountryService;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.PipsMaster;
import com.amg.exchange.treasury.model.ServiceMasterDesc;
import com.amg.exchange.treasury.service.IPipsMasterService;
import com.amg.exchange.treasury.service.IRatesUpdateService;
import com.amg.exchange.treasury.service.ISpecialCustomerDealRequestService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

/**
 * @author Subramaniam
 *
 */
@Component("pipsMasterEnquiryBean")
@Scope("session")
public class PipsMasterEnquiryBean<T> {

	/**
	 * 
	 */
	
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
	private String errorMsg;



	private List<CountryMasterDesc> countryList = new ArrayList<CountryMasterDesc>();
	private List<CurrencyMaster> currencyList = new ArrayList<CurrencyMaster>();
	private List<BankMaster> bankList = new ArrayList<BankMaster>();
	private List<CountryBranch> countryBranchList = new ArrayList<CountryBranch>();
	private List<BeneCountryService> lstBeneCountryService = new ArrayList<BeneCountryService>();

	private List<ServiceMasterDesc> serviceMasters = new ArrayList<ServiceMasterDesc>();

	
	/*
	 * private List<ServiceIndicator> serviceList = new
	 * ArrayList<ServiceIndicator>();
	 */
	private List<PipsMasterDataTable> pipsAllList = new ArrayList<PipsMasterDataTable>();
	private SessionStateManage session = new SessionStateManage();
	
	@Autowired
	IGeneralService<?> generalService;

	@Autowired
	IRatesUpdateService<?> ratesUpdateService;

	@Autowired
	ISpecialCustomerDealRequestService<?> specialCustomerDealRequestService;

	@Autowired
	IPipsMasterService pipsMasterService;

	public PipsMasterEnquiryBean() {
		// TODO Auto-generated constructor stub
	}
	
	public BigDecimal getCountryId() {
		return countryId;
	}

	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}
	public List<BeneCountryService> getLstBeneCountryService() {
		return lstBeneCountryService;
	}

	public void setLstBeneCountryService(
			List<BeneCountryService> lstBeneCountryService) {
		this.lstBeneCountryService = lstBeneCountryService;
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

	public BigDecimal getPipsMasterPk() {
		return pipsMasterPk;
	}

	public void setPipsMasterPk(BigDecimal pipsMasterPk) {
		this.pipsMasterPk = pipsMasterPk;
	}

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

	public List<CountryMasterDesc> getCountryList() {
		List<CountryMasterDesc> countryList =new ArrayList<CountryMasterDesc>();
		try{
		countryList =	generalService.getCountryList(session.getLanguageId());
		}catch(Exception e){
			setErrorMsg("Error Occured :");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		return countryList ;
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

	public List<ServiceMasterDesc> getServiceMasters() {
		List<ServiceMasterDesc> serviceList=new ArrayList<ServiceMasterDesc>();
		try{
		serviceList=	ratesUpdateService.getServiceMastersList(session.getLanguageId());
		}catch(Exception e){
			setErrorMsg("Error Occured:");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		return serviceList;
	}

	public void setServiceMasters(List<ServiceMasterDesc> serviceMasters) {
		this.serviceMasters = serviceMasters;
	}
	public List<BankMaster> getBankList() {
 
		return generalService.getAllBankListFromBankMaster();
	}

	public void setBankList(List<BankMaster> bankList) {
		this.bankList = bankList;
	}

	public List<CountryBranch> getCountryBranchList() {
		List<CountryBranch> countryBranchList=new ArrayList<CountryBranch>();
		try{
			countryBranchList=ratesUpdateService.getCountryBranchList(session.getCountryId());
		}catch(Exception e){
			setErrorMsg("Error:");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		return countryBranchList;
	}

	public void setCountryBranchList(List<CountryBranch> countryBranchList) {
		this.countryBranchList = countryBranchList;
	}

	
	

	public List<PipsMasterDataTable> getPipsAllList() {
		return pipsAllList;
	}

	public void setPipsAllList(List<PipsMasterDataTable> pipsAllList) {
		this.pipsAllList = pipsAllList;
	}
	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public void clearAllFields() {
		// setCountryId(null);
		// setCurrencyId(null);
		setBankId(null);
		setServiceId(null);
		setBranchId(null);
		setFromAmount(null);
		setToAmount(null);
		setPipsTypeCode(null);
		//setPips(null);

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
		setLstBeneCountryService(null);
		setBankList(null);
		setCountryList(null);
		setCurrencyList(null);
		setServiceMasters(null);
		setCountryBranchList(null);
		setPipsTypeCode(null);
		pipsAllList.clear();
		//setPips(null);

	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void pipsMasterPageNavigation() {
		//clearAllFields();
		setCountryId(null);
		setCurrencyId(null);
		setBooRenderDataTable(false);
		setBooRenderSaveExit(true);
		setLstPipsIndView(null);
		toFetchPipsTypeDesc();
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "PipsMasterEnquiry.xhtml");
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../treasury/PipsMasterEnquiry.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public void checkPipsCombination() {

		//pipsMasterList.clear();
		setPipsMasterPk(null);
		setFromAmount(null);
		setToAmount(null);
		setPips(null);
		try{
		List<BeneCountryService> lstBeneCountryService = pipsMasterService.getCurrencyMaster(getCountryId());
		setLstBeneCountryService(lstBeneCountryService);
		}catch(NullPointerException  e){ 
			 
				setErrorMsg("checkPipsCombination :");
				RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
			}
	
		
	}
	 public void currencyClearFields() {
		 setBankId(null);
		 setServiceId(null);
		 setBranchId(null);

	 }
	public void populatePipsCombination()
	{
		try{
		pipsAllList.clear();
		setBooRenderDataTable(true);
		List<PipsMaster> pipsMasterAppList= pipsMasterService.pipsListForEnquiry(getCountryId(), getCurrencyId(), getBranchId(), getServiceId(), getBankId());
		System.out.println("**********************************************"+pipsMasterAppList.size());
		
		if(pipsMasterAppList.size()>0){
			for(PipsMaster pip:pipsMasterAppList){
			PipsMasterDataTable pipsapproveDT = new PipsMasterDataTable();
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
			pipsapproveDT.setIsActive(setRecordStatus(pip.getIsActive()) );
			
			pipsapproveDT.setPipsTypeCode(pip.getPipsTypeCode());
			String pipsFullName=pipsMasterService.toFetchFullNameTypeCode(pip.getPipsTypeCode());
			if(pipsFullName != null){
				pipsapproveDT.setPipsTypeFullName(pipsFullName);
			}

			pipsAllList.add(pipsapproveDT);
			}
			
		}
		}catch(NullPointerException  e){ 
			 
				setErrorMsg("populatePipsCombination :");
				RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
			}
		catch (Exception e) {
				setErrorMsg(e.getMessage());
				RequestContext.getCurrentInstance().execute("csp.show();");
			}
		
	}
	
	public void exit() throws IOException {
		
		try {
			clearAll();
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("../registration/branchhome.xhtml");
			
		} catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
		
	}

	private String setRecordStatus(String status) {

		String strStatus = null;

		if (status.equalsIgnoreCase(Constants.U)) {
			strStatus="Un_ Approve";
		}
		if (status.equalsIgnoreCase(Constants.D)) {

			strStatus = "Deleted";
		}
		if (status.equalsIgnoreCase(Constants.Yes)) {
			strStatus = "Activated";
		}
		return strStatus;

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
			setErrorMsg("Method Name::toFetchPipsTypeDesc"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			setErrorMsg(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}
}
