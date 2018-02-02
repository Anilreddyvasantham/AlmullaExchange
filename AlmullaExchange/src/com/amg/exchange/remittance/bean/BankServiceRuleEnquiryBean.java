package com.amg.exchange.remittance.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.bean.RuleEngine;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.remittance.model.BankCharges;
import com.amg.exchange.remittance.model.BankServiceRule;
import com.amg.exchange.remittance.service.IBankServiceRuleMasterService;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.BeneCountryService;
import com.amg.exchange.treasury.model.DeliveryModeDesc;
import com.amg.exchange.treasury.model.RemittanceModeDescription;
import com.amg.exchange.treasury.service.IPipsMasterService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;
@Component("bankServiceRuleEnquiryBean")
@Scope("session")
public class BankServiceRuleEnquiryBean<T> implements Serializable {
 	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(BankServiceRuleEnquiryBean.class);
 	SessionStateManage session =new SessionStateManage();
 
 	private BigDecimal countryId;
 	private BigDecimal currencyId;
	private BigDecimal remittanceModeId;
	private BigDecimal deliveryId;
	private BigDecimal bankId;
	private  String errorMsg;
	 
	
	@Autowired
	IGeneralService< T>  igeneralService;
	@Autowired
	IPipsMasterService pipsMasterService;
	@Autowired
	IBankServiceRuleMasterService iBankServiceRuleMasterService;
	
	Map<BigDecimal, String> mapCountryList = new HashMap<BigDecimal, String>();
	Map<BigDecimal, String> mapCurrencyList = new HashMap<BigDecimal, String>();
	
	Map<BigDecimal, String> mapRemittanceList = new HashMap<BigDecimal, String>();
	Map<BigDecimal, String> mapDeliveryList = new HashMap<BigDecimal, String>();
	Map<BigDecimal,String> mapBank  = new HashMap<BigDecimal,String>();
	private List<CountryMasterDesc> countryList=new ArrayList<CountryMasterDesc>();
	private List<BankMaster> bankMasterList=new ArrayList<BankMaster>();
	

	List<RemittanceModeDescription> remittanceList= new ArrayList<RemittanceModeDescription>();
	List<DeliveryModeDesc> deliveryList= new ArrayList<DeliveryModeDesc>();
	
	List<BankServiceRuleMasterDataTable> bankServiceDTList=new ArrayList<BankServiceRuleMasterDataTable>();
	
	List<BankServiceRule> bankServiceRuleList=new ArrayList<BankServiceRule>();
	List<BankCharges> lstBankCharges=new ArrayList<BankCharges>();

	public Map<BigDecimal, String> getMapCurrencyList() {
		return mapCurrencyList;
	}
	public void setMapCurrencyList(Map<BigDecimal, String> mapCurrencyList) {
		this.mapCurrencyList = mapCurrencyList;
	}
	
	public List<BankMaster> getBankMasterList() {
		return bankMasterList;
	}
	public void setBankMasterList(List<BankMaster> bankMasterList) {
		this.bankMasterList = bankMasterList;
	}
	public List<BankServiceRuleMasterDataTable> getBankServiceDTList() {
		return bankServiceDTList;
	}
	public void setBankServiceDTList(
			List<BankServiceRuleMasterDataTable> bankServiceDTList) {
		this.bankServiceDTList = bankServiceDTList;
	}
	
	public List<RemittanceModeDescription> getRemittanceList() {
		return remittanceList;
	}
	public void setRemittanceList(List<RemittanceModeDescription> remittanceList) {
		this.remittanceList = remittanceList;
	}
	public List<DeliveryModeDesc> getDeliveryList() {
		return deliveryList;
	}
	public void setDeliveryList(List<DeliveryModeDesc> deliveryList) {
		this.deliveryList = deliveryList;
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
	public BigDecimal getRemittanceModeId() {
		return remittanceModeId;
	}
	public void setRemittanceModeId(BigDecimal remittanceModeId) {
		this.remittanceModeId = remittanceModeId;
	}
	public BigDecimal getDeliveryId() {
		return deliveryId;
	}
	public void setDeliveryId(BigDecimal deliveryId) {
		this.deliveryId = deliveryId;
	}
	public BigDecimal getBankId() {
		return bankId;
	}
	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}
public List<CountryMasterDesc> getCountryNameList() {
		
		countryList = new ArrayList<CountryMasterDesc>();
		countryList.addAll(igeneralService.getCountryList(session.getLanguageId()));

		for (CountryMasterDesc countryMasterDesc : countryList) {
			mapCountryList.put(countryMasterDesc.getFsCountryMaster().getCountryId(),
					countryMasterDesc.getCountryName());
		}
		return countryList;
	}
public void popCurrency()
{
	setBankId(null);
	 
}
	
	 
	public List<BeneCountryService> getCurrencyList() {
		List<BeneCountryService> lstBeneCountryService =new ArrayList<BeneCountryService>();
		try{
		lstBeneCountryService = pipsMasterService.getCurrencyMaster(getCountryId());

		for(BeneCountryService beneCountryService:lstBeneCountryService){

			mapCurrencyList.put(beneCountryService.getCurrencyId().getCurrencyId(),beneCountryService.getCurrencyId().getCurrencyName());

		}
		}catch(NullPointerException  e){ 
			 
				setErrorMsg("getCurrencyList :");
				RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
			}
		catch (Exception e) {
				setErrorMsg(e.getMessage());
				RequestContext.getCurrentInstance().execute("csp.show();");
			}

		return lstBeneCountryService;
	}
			public void popBank(AjaxBehaviorEvent e) {
				try{
				setBankId(null);
				setCurrencyId(null);
				bankMasterList = new ArrayList<BankMaster>();
				bankMasterList.addAll(igeneralService.getAllBankList(session.getLanguageId(), getCountryId()));
				for(BankMaster bankmaster:bankMasterList){
					mapBank.put(bankmaster.getBankId(), bankmaster.getBankFullName());

				}
				}catch(NullPointerException  e2){ 
	 
					setErrorMsg("getCurrencyList :");
					RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
				}
				catch (Exception e1) {
					setErrorMsg(e1.getMessage());
					RequestContext.getCurrentInstance().execute("csp.show();");
	}
				 
			}
			 
			public void bankServiceRuleEnquiry(){
			try{
				bankServiceDTList.clear(); 
				bankServiceRuleList =	iBankServiceRuleMasterService.getBankServiceRuleEnqryFrmDB( getCountryId(), getCurrencyId(), getBankId());
			/*	getCutomerTypeMap();*/
				System.out.println("********************************"+bankServiceRuleList.size());	
				if(bankServiceRuleList.size()>0){
					for(BankServiceRule bankServiceRule:bankServiceRuleList){
				
					lstBankCharges =iBankServiceRuleMasterService.getBankChargesEnqry(bankServiceRule.getBankServiceRuleId());
					System.out.println("******************************charges"+lstBankCharges.size());	
					if(lstBankCharges.size()>0){
					for(BankCharges bankCharge:lstBankCharges){
				 
					BankServiceRuleMasterDataTable bankServiceRuleDT=new BankServiceRuleMasterDataTable();
					bankServiceRuleDT.setChargeAmount( bankCharge.getChargeAmount());
		 			
				    BigDecimal chargesFor=bankCharge.getChargeFor().getComponentDataId();//getFsBizComponentDataDescs().get(0).getFsBizComponentData().getComponentDataId();
						 
					 bankServiceRuleDT.setBankChargesId(bankCharge.getBankChargeId());
				    	/*if(chargesFor.intValue()==Constants.INDIVIDUAL_COMPONENT_ID){
				    		bankServiceRuleDT.setChargeFordis("Individual");
				    		bankServiceRuleDT.setChargeFor(new BigDecimal(Constants.INDIVIDUAL_COMPONENT_ID));
				    	}else if(chargesFor.intValue()==Constants.CORPORATE_COMPONENT_ID){
				    		bankServiceRuleDT.setChargeFordis("Corporate");
				    		bankServiceRuleDT.setChargeFor(new BigDecimal(Constants.CORPORATE_COMPONENT_ID));
				    	}else{
				    		bankServiceRuleDT.setChargeFordis("Both");
				    		bankServiceRuleDT.setChargeFor(new BigDecimal(3));
				    	}*/
					 
					 if(chargesFor.intValue()== Integer.parseInt(Constants.BOTHFOR_BANKSERVICE_COMPONENT_ID)){
			    			bankServiceRuleDT.setChargeFordis(Constants.BOTH);
				    		bankServiceRuleDT.setChargeFor(new BigDecimal(Constants.BOTHFOR_BANKSERVICE_COMPONENT_ID));
					 }else{
						 
						 bankServiceRuleDT.setChargeFordis(customerTypeMap.get(bankCharge.getChargeFor().getComponentDataId()));
						 bankServiceRuleDT.setChargeFor(bankCharge.getChargeFor().getComponentDataId());
						 
					 }
			    		 
					
					if(bankCharge.getChargeType().equalsIgnoreCase("c")){
					bankServiceRuleDT.setChargeTypedis("Comission");
					}else if(bankCharge.getChargeType().equalsIgnoreCase("Oversease Charge")){
						bankServiceRuleDT.setChargeTypedis("Oversease Charge ");
					}else {
						bankServiceRuleDT.setChargeTypedis("Both ");
					}
					
					bankServiceRuleDT.setCostAmount( bankCharge.getCostAmount());
					 
			    	  if(bankCharge.getCostCurrencyCode()!=null){
			    		  System.out.println("==========="+bankCharge.getCostCurrencyCode());
					bankServiceRuleDT.setCostCurrencyCodeName(igeneralService.getCurrencyName( bankCharge.getCostCurrencyCode()));
					bankServiceRuleDT.setCurrencyCodeName(igeneralService.getCurrencyName(bankCharge.getCurrencyCode()));
					}
					bankServiceRuleDT.setToAmount(  bankCharge.getToAmount());
					bankServiceRuleDT.setFromAmount( bankCharge.getFromAmount());
			 
 
					bankServiceRuleDT.setCreatedBy( bankCharge.getCreatedBy());
					bankServiceRuleDT.setCreatedDate(bankCharge.getCreatedDate());
					if(bankCharge.getIsActive().equalsIgnoreCase(Constants.Yes)){
					bankServiceRuleDT.setIsActive("Activated");
					}
					else if(bankCharge.getIsActive().equalsIgnoreCase(Constants.D)){
						bankServiceRuleDT.setIsActive("Deleted");
					}
					else if(bankCharge.getIsActive().equalsIgnoreCase(Constants.U)){
						bankServiceRuleDT.setIsActive("Un-Approved");
					}
					bankServiceDTList.add(bankServiceRuleDT );
					
					 
					}
					}else{
						//RequestContext.getCurrentInstance().execute("bank.show();");
					}
				
			}
					}else{
						RequestContext.getCurrentInstance().execute("bank.show();");
						
					}
			}catch(NullPointerException  e){ 
				e.printStackTrace();
					setErrorMsg("bankServiceRuleEnquiry :");
					RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
				}
			catch (Exception e) {
				e.printStackTrace();
					setErrorMsg(e.getMessage());
					RequestContext.getCurrentInstance().execute("csp.show();");
				}
	
			}
			public void clear(){
				bankServiceDTList.clear();
				setBankId( null);
				setCountryId(null);
				setCurrencyId(null);
			}
		 
			public void exit() {
				try {
					if (session.getRoleId().equalsIgnoreCase("1")) {
						FacesContext.getCurrentInstance().getExternalContext()
								.redirect("../registration/employeehome.xhtml");
					} else {
						FacesContext.getCurrentInstance().getExternalContext()
								.redirect("../registration/branchhome.xhtml");
					}
				} catch (Exception e) {
					log.info("This is Navigation problem in SWIFT ENQUIRY" + e);
				}

			}
			@Autowired
			LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
			public void bankServiceRuleEnquryNavigation(){
				try {
					clear();
					loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "BankServiceRuleEnquiry.xhtml");
					FacesContext.getCurrentInstance().getExternalContext()
							.redirect("../remittance/BankServiceRuleEnquiry.xhtml");
				} catch (Exception e) {
					log.info("This is Navigation problem in Bank Service Rule ENQUIRY");
			}
	
			}
			
			 /*
		  	 * 
		  	 * Fetch ID Type
		  	 * 
		  	 * */
		      
		     @Autowired
		  	RuleEngine<T> ruleEngine;
		     @Autowired
		     IGeneralService<T> generalService;
		      
		     private Map<BigDecimal, String> customerTypeMap = new HashMap<BigDecimal, String>();
		     
		  	public Map<BigDecimal, String> getCutomerTypeMap() {
		  		try{
		  		customerTypeMap.putAll(ruleEngine.getComponentData("Customer Type"));
		  		
		  		customerTypeMap.remove(generalService.getComponentId(Constants.CUSTOMERTYPEFORPARTNER, session.getLanguageId()).getFsBizComponentData().getComponentDataId());
		  		customerTypeMap.remove(generalService.getComponentId(Constants.CUSTOMERTYPEFOROWNER, session.getLanguageId()).getFsBizComponentData().getComponentDataId());
		  		}catch(NullPointerException  e){ 
					 
						setErrorMsg("getCutomerTypeMap :");
						RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
					}
				catch (Exception e) {
						setErrorMsg(e.getMessage());
						RequestContext.getCurrentInstance().execute("csp.show();");
					}
		  		return customerTypeMap;
		  	}
			public String getErrorMsg() {
				return errorMsg;
			}
			public void setErrorMsg(String errorMsg) {
				this.errorMsg = errorMsg;
			}
}
