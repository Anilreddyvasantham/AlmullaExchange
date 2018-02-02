package com.amg.exchange.treasury.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.remittance.model.RoutingHeader;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankIndicator;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.RoutingSetupMaster;
import com.amg.exchange.treasury.service.IBankIndicatorService;
import com.amg.exchange.treasury.service.IFXDetailInformationService;
import com.amg.exchange.treasury.service.IRoutingSetUpService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.SessionStateManage;

@Component("routingSetupBean")
@Scope("session")
public class RoutingSetupBean<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	// Variables Declaration
	private BigDecimal bankCountry = null;
	private BigDecimal bankId = null;
	private BigDecimal currencyId = null;
	private BigDecimal routingSetUpId = null;
	
	private Boolean booRenderDataTable = false;
	boolean delete;
	
	//SessionStateManage Declaration
	private SessionStateManage sessionManage = new SessionStateManage();
	
	//List Declaration
	private List<RoutingSetUpDetailsTable> routingSetUpDetailsTable = new ArrayList<RoutingSetUpDetailsTable>();
	private List<RoutingHeader> bankActiveinRoutingSetUp=new ArrayList<RoutingHeader>();
	
	public List<RoutingSetUpDetailsTable> getRoutingSetUpDetailsTable() {
		return routingSetUpDetailsTable;
	}
	public void setRoutingSetUpDetailsTable(
			List<RoutingSetUpDetailsTable> routingSetUpDetailsTable) {
		this.routingSetUpDetailsTable = routingSetUpDetailsTable;
	}

	//Services Called
	@Autowired
	IGeneralService<T> generalService;
	
	@Autowired
	IRoutingSetUpService<T> iroutingSetUpService;
	
	@Autowired
	IFXDetailInformationService<T> fXDetailInformationService;
	
	@Autowired
	IBankIndicatorService bankIndicatorService;
	

	//Get Set Methods for All Variables
	public BigDecimal getBankCountry() {
		return bankCountry;
	}
	public void setBankCountry(BigDecimal bankCountry) {
		this.bankCountry = bankCountry;
	}
	
	public BigDecimal getBankId() {
		return bankId;
	}
	
	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}
	
	public BigDecimal getCurrencyId() {
		return currencyId;
	}
	
	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	public Boolean getBooRenderDataTable() {
		return booRenderDataTable;
	}
	
	public void setBooRenderDataTable(Boolean booRenderDataTable) {
		this.booRenderDataTable = booRenderDataTable;
	}
	
	public BigDecimal getRoutingSetUpId() {
		return routingSetUpId;
	}
	
	public void setRoutingSetUpId(BigDecimal routingSetUpId) {
		this.routingSetUpId = routingSetUpId;
	}
	
	public boolean isDelete() {
		return delete;
	}
	
	public void setDelete(boolean delete) {
		this.delete = delete;
	}
	
	//to get All Bank Country
	public List<BankCountryPopulationBean> getBankCountryList() {
		return generalService.getBankContry(sessionManage.getCountryId());
	}
	
	//to get All Banks from BankApplicability
	public List<BankApplicability> getBankAccordingToBankIndicator() {
		
		BigDecimal pkCorresBankInd = null;
		
		List<BankIndicator> corrBankIndlist = bankIndicatorService.getRecordFromDB(Constants.BANK_INDICATOR_CORR_BANK);
		
		if(corrBankIndlist.size() != 0){
			pkCorresBankInd = corrBankIndlist.get(0).getBankIndicatorId();
		}
		
		List<BankApplicability> lstcorresbankind = generalService.getBankListbyIndicator(getBankCountry(),pkCorresBankInd);
		
		return lstcorresbankind;
	}
	
	//to get All Currency From CurrencyMaster
	public List<CurrencyMaster> getCurrencyListFromDB() {
		return generalService.getCurrencyList();
	}
	
	//Checking Fields in Data Base and Data Table while Adding
	public void getAllDetailsToList() {
		
		//To Get Spl Pool Data Routing Setup Bank Must be IsActive "Y" else No records
		//this method blocked
		bankActiveinRoutingSetUp = fXDetailInformationService.checkBankisActiveinRoutingSetupMaster(getBankCountry(),getBankId(),getCurrencyId());
		
		if(bankActiveinRoutingSetUp.size()!=0){
			RequestContext.getCurrentInstance().execute("detailsexists.show();");
			clear();
		}else{
			if(routingSetUpDetailsTable.size()!=0){
			for (RoutingSetUpDetailsTable routingsetupvaluecheck : routingSetUpDetailsTable) {
				if(routingsetupvaluecheck.getBankCountryId().equals(getBankCountry()) && routingsetupvaluecheck.getBankId().equals(getBankId()) && routingsetupvaluecheck.getCurrencyId().equals(getCurrencyId())){
					setDelete(true);
					break;
				}else{
					setDelete(false);
				}
			}
			if(isDelete()){
				RequestContext.getCurrentInstance().execute("detailsexists.show();");
				clear();
			}else{
				addtoListAllFields();
			}
			}else{
				addtoListAllFields();
			}			
		}
	}
	
	//Adding values to List for Data Table - RoutingSetUpDetailsTable
	public void addtoListAllFields(){
		setBooRenderDataTable(true);		
		RoutingSetUpDetailsTable routingSetUpDetails= new RoutingSetUpDetailsTable();
		
		routingSetUpDetails.setRoutingSetupDetailsId(getRoutingSetUpId());
		routingSetUpDetails.setBankCountryId(getBankCountry());
		routingSetUpDetails.setBankCountryName(generalService.getCountryName(sessionManage.getLanguageId(), getBankCountry()));
		routingSetUpDetails.setBankId(getBankId());
		routingSetUpDetails.setBankName(generalService.getBankName(getBankId()).toString());
		routingSetUpDetails.setCurrencyId(getCurrencyId());
		routingSetUpDetails.setCurrencyName(generalService.getCurrencyName(getCurrencyId()));
		routingSetUpDetails.setCreatedBy(sessionManage.getUserName());
		routingSetUpDetails.setCreatedDate(new Date());
		
		routingSetUpDetailsTable.add(routingSetUpDetails);
		clear();
	}
	
	
	//saving all data to the DataBase
	public void saveAllDataToDataBase() {
		for(RoutingSetUpDetailsTable routingSetup : routingSetUpDetailsTable)
		{
			RoutingSetupMaster routingSetUpmaster = new RoutingSetupMaster();
			
			routingSetUpmaster.setRoutingSetupMasterid(routingSetup.getRoutingSetupDetailsId());
			
			//save Application Country
			CountryMaster applicationCountry = new CountryMaster();
			applicationCountry.setCountryId(sessionManage.getCountryId());
			routingSetUpmaster.setExApplicationCountry(applicationCountry);
			
			//save Bank Country
			CountryMaster bankCountryMaster = new CountryMaster();
			bankCountryMaster.setCountryId(routingSetup.getBankCountryId());
			routingSetUpmaster.setExCountryMaster(bankCountryMaster);
			
			//save Bank 
			BankMaster bankMaster = new BankMaster();
			bankMaster.setBankId(routingSetup.getBankId());
			routingSetUpmaster.setExBankMaster(bankMaster);
			
			//save Currency
			CurrencyMaster currencyMaster = new CurrencyMaster();
			currencyMaster.setCurrencyId(routingSetup.getCurrencyId());
			routingSetUpmaster.setExCurrenyMaster(currencyMaster);
			
			routingSetUpmaster.setIsActive(routingSetup.getIsActive());
			routingSetUpmaster.setCreatedBy(routingSetup.getCreatedBy());
			routingSetUpmaster.setCreatedDate(routingSetup.getCreatedDate());
			
			iroutingSetUpService.save(routingSetUpmaster);

		}
		RequestContext.getCurrentInstance().execute("complete.show();");
		if(routingSetUpDetailsTable.size()!=0){
			routingSetUpDetailsTable.clear();
		}
	}
	
	//clearing the fields
	public void clear(){
		setBankCountry(null);
		setBankId(null);
		setCurrencyId(null);
	}
	
	//method called from menubar - navigation
	public void routingSetupNavigation() {
		clear();
		setBooRenderDataTable(false);
		if(routingSetUpDetailsTable.size()!=0){
			routingSetUpDetailsTable.clear();
		}
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/RoutingSetup.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	//exit to Home Page
	public void exit() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//remove details from DataTable 
	public void removeStandardInstrn(RoutingSetUpDetailsTable bean) {
		routingSetUpDetailsTable.remove(bean);
		RequestContext.getCurrentInstance().execute("deleted.show();");
    }

}
