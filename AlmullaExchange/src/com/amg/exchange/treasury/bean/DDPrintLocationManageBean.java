package com.amg.exchange.treasury.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CityMaster;
import com.amg.exchange.common.model.CityMasterDesc;
import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.model.DistrictMaster;
import com.amg.exchange.common.model.DistrictMasterDesc;
import com.amg.exchange.common.model.StateMaster;
import com.amg.exchange.common.model.StateMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.treasury.model.AgentMaster;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.BankDdPrintLoc;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.service.IBankDDPrintLocationService;
import com.amg.exchange.util.SessionStateManage;
@Component("dDPrintLocationManagedBean")
@Scope("session")
public class DDPrintLocationManageBean<T> implements Serializable {

	/**  
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//TODO
	private BigDecimal ddPrintLocId = null;
	private BigDecimal countryId = null;
	private String dDAgent;
	private String dDPrintLocation;
	private BigDecimal bankId;
	private BigDecimal bankBranchId;
	private BigDecimal stateId = null;
	private BigDecimal districtId = null;
	private BigDecimal cityId = null;
	private List<AddBankDDPrintLocBean> bankDdPrintLocationList = new ArrayList<AddBankDDPrintLocBean>();
	private List<AddBankDDPrintLocBean> removeBankDdPrintLocationList = new ArrayList<AddBankDDPrintLocBean>();
	Map<BigDecimal,String> mapCountryList = new HashMap<BigDecimal, String>();
	Map<BigDecimal,String> mapAgentList = new HashMap<BigDecimal, String>();
	private boolean renderDDDataTable = false;
	private List<CountryMasterDesc>  countryList;
    private List<StateMasterDesc> lstState;
    private List<BankMaster> bankMasterList;
	private List<BankBranch> lstBankBranch;
	private List<CityMasterDesc>lstCity;
	private List<DistrictMasterDesc>lstDistrict;
	private List<AgentMaster> agentMasterList;
	private Boolean renderDdprintLocation = false;
	
	private BankBranch bankBranch = null;
	private BankDdPrintLoc bankDdPrintLoc = null;
	private CountryMaster countryMaster  = null;
	private CompanyMaster companyMaster  = null;
	private StateMaster stateMaster = null;
	private DistrictMaster districtMaster = null;
	private CityMaster cityMaster = null;
	private SessionStateManage sessionStateManage = null;
	private Boolean renderBankBranch = false;
	private Boolean bankVisibility = false;
	//TODO
	@Autowired
	IGeneralService<T> generalService;
	@Autowired
	IBankDDPrintLocationService<T> bankDDPrintLocationService;
	
	
	public Boolean getRenderBankBranch() {
		return renderBankBranch;
	}
	public void setRenderBankBranch(Boolean renderBankBranch) {
		this.renderBankBranch = renderBankBranch;
	}
	public Boolean getBankVisibility() {
		return bankVisibility;
	}
	public void setBankVisibility(Boolean bankVisibility) {
		this.bankVisibility = bankVisibility;
	}
	public Boolean getRenderDdprintLocation() {
		return renderDdprintLocation;
	}
	public void setRenderDdprintLocation(Boolean renderDdprintLocation) {
		this.renderDdprintLocation = renderDdprintLocation;
	}
	public IBankDDPrintLocationService<T> getBankDDPrintLocationService() {
		return bankDDPrintLocationService;
	}
	public void setBankDDPrintLocationService(
			IBankDDPrintLocationService<T> bankDDPrintLocationService) {
		this.bankDDPrintLocationService = bankDDPrintLocationService;
	}
	public BigDecimal getDdPrintLocId() {
		return ddPrintLocId;
	}
	public void setDdPrintLocId(BigDecimal ddPrintLocId) {
		this.ddPrintLocId = ddPrintLocId;
	}
	public BigDecimal getDistrictId() {
		return districtId;
	}
	public void setDistrictId(BigDecimal districtId) {
		this.districtId = districtId;
	}
	public BigDecimal getCityId() {
		return cityId;
	}
	public void setCityId(BigDecimal cityId) {
		this.cityId = cityId;
	}
	public BigDecimal getStateId() {
		return stateId;
	}
	public void setStateId(BigDecimal stateId) {
		this.stateId = stateId;
	}
	public BigDecimal getBankBranchId() {
		return bankBranchId;
	}
	public void setBankBranchId(BigDecimal bankBranchId) {
		this.bankBranchId = bankBranchId;
	}
	public BigDecimal getBankId() {
		return bankId;
	}
	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}
	public List<AddBankDDPrintLocBean> getRemoveBankDdPrintLocationList() {
		return removeBankDdPrintLocationList;
	}
	public void setRemoveBankDdPrintLocationList(
			List<AddBankDDPrintLocBean> removeBankDdPrintLocationList) {
		this.removeBankDdPrintLocationList = removeBankDdPrintLocationList;
	}
	public boolean isRenderDDDataTable() {
		return renderDDDataTable;
	}
	public void setRenderDDDataTable(boolean renderDDDataTable) {
		this.renderDDDataTable = renderDDDataTable;
	}
	public List<AddBankDDPrintLocBean> getBankDdPrintLocationList() {
		return bankDdPrintLocationList;
	}
	public void setBankDdPrintLocationList(
			List<AddBankDDPrintLocBean> bankDdPrintLocationList) {
		this.bankDdPrintLocationList = bankDdPrintLocationList;
	}
	public IGeneralService<T> getGeneralService() {
		return generalService;
	}
	public void setGeneralService(IGeneralService<T> generalService) {
		this.generalService = generalService;
	}
	public BigDecimal getCountryId() {
		return countryId;
	}
	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}
	
	public String getdDAgent() {
		return dDAgent;
	}
	public void setdDAgent(String dDAgent) {
		this.dDAgent = dDAgent;
	}
	public String getdDPrintLocation() {
		return dDPrintLocation;
	}
	public void setdDPrintLocation(String dDPrintLocation) {
		this.dDPrintLocation = dDPrintLocation;
	}
	
	
	
	public Map<BigDecimal, String> getMapAgentList() {
		return mapAgentList;
	}
	public void setMapAgentList(Map<BigDecimal, String> mapAgentList) {
		this.mapAgentList = mapAgentList;
	}
	public List<AgentMaster> getAgentMasterList() {
		try{
		agentMasterList = getBankDDPrintLocationService().getAgentMasterList();
		System.out.println("agentMasterList   :"+agentMasterList.size());
		for(AgentMaster agent:agentMasterList){
			System.out.println(agent.getAgentId() +" ::: "+agent.getAgentName() );
			mapAgentList.put(agent.getAgentId(), agent.getAgentName());
				}
		System.out.println("mapAgentList   :"+agentMasterList.size());
	}catch(Exception e){
		e.printStackTrace();
	}
		return agentMasterList;
	}
	public void setAgentMasterList(List<AgentMaster> agentMasterList) {
		this.agentMasterList = agentMasterList;
	}
	/**
	 * method is responsible to populate List of countries from DB 
	 * @return
	 */
	public List<CountryMasterDesc> getCountryListFromDB() {
		 sessionStateManage = new SessionStateManage(); 
		countryList = new ArrayList<CountryMasterDesc>();
		countryList.addAll( getGeneralService().getCountryList(new BigDecimal(sessionStateManage.isExists("languageId")?sessionStateManage.getSessionValue("languageId"):"1")));
		for(CountryMasterDesc countryMasterDesc:countryList) {
			mapCountryList.put(countryMasterDesc.getFsCountryMaster().getCountryId(), countryMasterDesc.getCountryName());
		}
 		return countryList;
	}
	/*
	*
	*method to get state from db and add all the state code and name will be mapped to hashMap
	*/
	public void popState(AjaxBehaviorEvent e) {
		lstState = new ArrayList<StateMasterDesc>();
		lstState.addAll(getGeneralService().getStateList(new BigDecimal(sessionStateManage.isExists("languageId")?sessionStateManage.getSessionValue("languageId"):"1"),getCountryId()));
		popBank(e);
	}
	public List<StateMasterDesc> getStateListFromDB() {
		return lstState;
	}
	/*
	*
	*method to get District from db and add all the state code and name will be mapped to hashMap
	*/
	public void popDistrict(AjaxBehaviorEvent e) {
		lstDistrict = new ArrayList<DistrictMasterDesc>();
		lstDistrict.addAll(getGeneralService().getDistrictList(new BigDecimal(sessionStateManage.isExists("languageId")?sessionStateManage.getSessionValue("languageId"):"1"),getCountryId(),getStateId()));
		
	}
	public List<DistrictMasterDesc> getDistrictListFromDB() {
		return lstDistrict;
		
	}
	/*
	*
	*method to get city from db and add all the state code and name will be mapped to hashMap
	*/
	public void popCity(AjaxBehaviorEvent e) {
		lstCity = new ArrayList<CityMasterDesc>();
		lstCity.addAll(getGeneralService().getCityList(new BigDecimal(sessionStateManage.isExists("languageId")?sessionStateManage.getSessionValue("languageId"):"1"),getCountryId(),getStateId(),getDistrictId()));
	}
	public List<CityMasterDesc> getCityListFromDB() {
		return lstCity;
		
	}
	/**
	 *  method is responsible to populate List of banks from DB 
	 * @return
	 */
	public void popBank(AjaxBehaviorEvent e) {
		System.out.println("inside pop bank");
		bankMasterList = new ArrayList<BankMaster>();
		bankMasterList.addAll(getGeneralService().getAllBankList(new BigDecimal(sessionStateManage.isExists("languageId")?sessionStateManage.getSessionValue("languageId"):"1"),getCountryId()));
		
	}
	
	public List<BankMaster> getBankListFromDB() {
		return bankMasterList;
	}
	public void popBankBranch(AjaxBehaviorEvent e) {
		System.out.println("pop bankbranch");
		lstBankBranch = new ArrayList<BankBranch>();
		lstBankBranch.addAll(getGeneralService().getBankBranchList(getCountryId(),getBankId()));
		
	}
	public List<BankBranch> getBankBranchListFromDB() {
		return lstBankBranch;
	}
	/**
	 * method to responsible add add data in List
	 */
	 public void addBankDDLocation() {
		 System.out.println("the value of country"+this.countryId);
		 System.out.println("the map value is"+mapCountryList.get(this.countryId));
		 System.out.println("dDAgent  :"+ dDAgent +"the map value is"+mapAgentList.get(new BigDecimal(this.dDAgent)));
		 try {
		 AddBankDDPrintLocBean  addBankDDPrintLocBean = new  AddBankDDPrintLocBean(mapCountryList.get(this.countryId),this.dDAgent,this.dDPrintLocation,this.countryId,this.stateId,this.districtId,this.cityId,this.bankBranchId,mapAgentList.get(new BigDecimal(this.dDAgent)));
		 bankDdPrintLocationList.add(addBankDDPrintLocBean);
		 }catch(NullPointerException npexp) {
			 System.out.println("null pointer exception is occured");
			 npexp.printStackTrace();
		 }catch (Exception e) {
			e.printStackTrace();
		}
		 
		 setRenderDDDataTable(true);
		 setBankBranchId(null);
		 setBankId(null);
		 setCountryId(null);
		 setStateId(null);
		 setDistrictId(null);
		 setCityId(null);
		 setdDAgent("");
		 setdDPrintLocation("");
		 setRenderDdprintLocation(false);
	 }
	 /**
		 * 
		 * method is responsible to remove data from dataTable
		 * 
		 */
	 public String saveBankDdPrintLocation() {
		 System.out.println("inside save method");
		   bankDdPrintLoc = new BankDdPrintLoc();
		   bankBranch = new BankBranch();
		   countryMaster = new CountryMaster();
		   companyMaster = new CompanyMaster();
		   stateMaster = new StateMaster();
		   districtMaster = new DistrictMaster();
		   cityMaster = new CityMaster();
		   if(bankDdPrintLocationList.size() == 0) {
			   setRenderDdprintLocation(true);
			 return "";  
		   }else {
			   setRenderDdprintLocation(false);
		 for(AddBankDDPrintLocBean addBankDDPrintLocBean:bankDdPrintLocationList) {
			 System.out.println("the dd print location id is"+getDdPrintLocId());
			 if(getBankVisibility()) {
				 bankBranch.setBankBranchId(addBankDDPrintLocBean.getBankBranchId());
				 bankDdPrintLoc.setExBankBranch(bankBranch);
			 }
			
			 countryMaster.setCountryId(addBankDDPrintLocBean.getCountryId());
			 companyMaster.setCompanyId(new BigDecimal(12));
			 stateMaster.setStateId(addBankDDPrintLocBean.getStateId());
			 districtMaster.setDistrictId(addBankDDPrintLocBean.getDistrictId());
			 cityMaster.setCityId(addBankDDPrintLocBean.getCityId());
			 bankDdPrintLoc.setFsCountryMaster(countryMaster);
			 bankDdPrintLoc.setFsStateMaster(stateMaster);
			 bankDdPrintLoc.setFsDistrictMaster(districtMaster);
			 bankDdPrintLoc.setFsCityMaster(cityMaster);
			 bankDdPrintLoc.setDdAgent(addBankDDPrintLocBean.getdDAgent());
			 bankDdPrintLoc.setDdPrintLocation(addBankDDPrintLocBean.getdDPrintLocation());
			 bankDdPrintLoc.setCreator("Hakeem");
			 bankDdPrintLoc.setCreateDate(new Date());
			 bankDdPrintLoc.setModifier("hakeem");
			 bankDdPrintLoc.setUpdateDate(new Date());
			 bankDdPrintLoc.setRecordStatus("1");
			 bankDdPrintLoc.setDdPrintLocId(getDdPrintLocId());
			 if(getDdPrintLocId() == null) {
			     getBankDDPrintLocationService().saveOrUpdateBankDdPrintLoc(bankDdPrintLoc);
			 }
		 }
		return "success";
		   }
	 }
	 /**
		 * 
		 * method is responsible to remove data from dataTable
		 * 
		 */
		public void removeDdPrintLocFromList(AddBankDDPrintLocBean  addBankDDPrintLocBean) {
            System.out.println("inside remove");
			bankDdPrintLocationList.remove( addBankDDPrintLocBean);
			removeBankDdPrintLocationList.add( addBankDDPrintLocBean);
		}
	/**
	 * 
	 * 
	 * method is responsible to clear the Bank Applicability Details from the screen
	 */
	public void clearBankDdPrintLocation() {
		 System.out.println("inside method cleare");
		 setBankBranchId(null);
		 setBankId(null);
		 setCountryId(null);
		 setStateId(null);
		 setDistrictId(null);
		 setCityId(null);
		  //System.out.println(getCountryId());
		 setCountryId(null);
	   	 setdDAgent("");
	   	 //System.out.println(getCountryId());
		 setdDPrintLocation("");
		 setRenderDdprintLocation(false);
		 System.out.println("Value : "+getdDAgent()); 
	}
	
	
	/**
	 * method is responsible to cancel the Bank Master
	 * @return
	 */
	public void cancelBankDdPrintLocation() {
		System.out.println("inside cancel    ");
		try{
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext(); 
			context.redirect("../login/login.xhtml");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * method to show or hide Bank Branch
	 */
	 public void displayBankBranch() {
		 if(getBankVisibility()) {
			 setRenderBankBranch(true);
		 }else{
			setRenderBankBranch(false); 
		 }
		 
	 }
}
