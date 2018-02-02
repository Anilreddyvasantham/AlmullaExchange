package com.amg.exchange.loyalty.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.loyalty.model.LoyaltyLogOptions;
import com.amg.exchange.loyalty.service.ILoyaltyCalculationLogOptionsService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;
@Component("calculationLogOptionsBean")
@Scope("session")
public class LoyaltyCalculationLogOptionsBean<T> implements Serializable {
 
	private static final long serialVersionUID = 1L;
	
	private BigDecimal loyaltyLogOptionsId;
	private String  realTime;
	private String simulation;
	private    Boolean writeCustomerFlag;
	private String logOptionType;
	private String errorMsg;
	
	private static final Logger log=Logger.getLogger(LoyaltyCalculationLogOptionsBean.class);
	private SessionStateManage session=new SessionStateManage();
	
	@Autowired
	ILoyaltyCalculationLogOptionsService iLoyaltyCalculationService;
	
	 
	public Boolean getWriteCustomerFlag() {
		return writeCustomerFlag;
	}
	public void setWriteCustomerFlag(Boolean writeCustomerFlag) {
		this.writeCustomerFlag = writeCustomerFlag;
	}
	public BigDecimal getLoyaltyLogOptionsId() {
		return loyaltyLogOptionsId;
	}
	public void setLoyaltyLogOptionsId(BigDecimal loyaltyLogOptionsId) {
		this.loyaltyLogOptionsId = loyaltyLogOptionsId;
	}
	public String getLogOptionType() {
		return logOptionType;
	}
	public void setLogOptionType(String logOptionType) {
		this.logOptionType = logOptionType;
	}
	public String getSimulation() {
		return simulation;
	}
	public void setSimulation(String simulation) {
		this.simulation = simulation;
	}
	public String getRealTime() {
		return realTime;
	}
	public void setRealTime(String realTime) {
		this.realTime = realTime;
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void pageNavigationCalculateLogOptions(){
		setLogOptionType( null);
		setSimulation(null);
		setRealTime( null);
		setWriteCustomerFlag(null);
		setLoyaltyLogOptionsId(null);
		List<LoyaltyLogOptions>  loyaltyCalLogOptions=iLoyaltyCalculationService.getAllRecords();
		if(loyaltyCalLogOptions.size()>0){
			for(LoyaltyLogOptions loyaltyLogOptions:loyaltyCalLogOptions){
			if(loyaltyLogOptions.getWriteCustomerFlag().equalsIgnoreCase( Constants.Yes)){
			setWriteCustomerFlag(true);
			}else if(loyaltyLogOptions.getWriteCustomerFlag().equalsIgnoreCase( Constants.No)){
				setWriteCustomerFlag(false);
			}
			if(loyaltyLogOptions.getLogOptionType().equalsIgnoreCase( Constants.R)){
				setRealTime(loyaltyLogOptions.getLogFlag() );

			}else if(loyaltyLogOptions.getLogOptionType().equalsIgnoreCase( Constants.S)){
			    setSimulation(loyaltyLogOptions.getLogFlag() );
			}
			}
		}
		
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "loyaltycalculationlogoptions.xhtml");
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../loyalty/loyaltycalculationlogoptions.xhtml");
		} catch (Exception e) {
			log.error("Error in Page Navigation to  Loyalty  Calculationlogoptions");
		}
		
	}
	
	
	public void clickOnOk(){
		try{
		List<LoyaltyLogOptions>  loyaltyCalLogOptions=iLoyaltyCalculationService.getAllRecords();
		if(loyaltyCalLogOptions.size()>0){
			for(LoyaltyLogOptions loyaltyLogOptions:loyaltyCalLogOptions){
				if(loyaltyLogOptions.getWriteCustomerFlag().equalsIgnoreCase( Constants.Yes)){
					setWriteCustomerFlag(true);
					}else if(loyaltyLogOptions.getWriteCustomerFlag().equalsIgnoreCase( Constants.No)){
						setWriteCustomerFlag(false);
					}
			if(loyaltyLogOptions.getLogOptionType().equalsIgnoreCase( Constants.R)){
				setRealTime(loyaltyLogOptions.getLogFlag() );

			}else if(loyaltyLogOptions.getLogOptionType().equalsIgnoreCase( Constants.S)){
			    setSimulation(loyaltyLogOptions.getLogFlag() );
			}
			}
			
		}
		}catch(NullPointerException  e){ 
			 
			setErrorMsg("clickOnOk :");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
	catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
		
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../loyalty/loyaltycalculationlogoptions.xhtml");
		} catch (Exception e) {
			log.error("Error in Page Navigation to  Loyalty  Calculationlogoptions");
		}
	}
	
public void saveRecords(){
try{
	List<LoyaltyLogOptions> iloyaltyCalcOptionList=iLoyaltyCalculationService.getAllRecords();	
	if(iloyaltyCalcOptionList.size()==0){
	for(int i=1;i<=2;i++){
	   if(i==1){
	   LoyaltyLogOptions loyaltyLogOptionsObj=new LoyaltyLogOptions();
		
	 loyaltyLogOptionsObj.setCreatedBy( session.getUserName());
	 loyaltyLogOptionsObj.setCreatedDate(new Date());
	
	  CountryMaster countryMasterObj=new CountryMaster();
	  countryMasterObj.setCountryId( session.getCountryId());
	  loyaltyLogOptionsObj.setApplicationCountryId(countryMasterObj );
		
	   loyaltyLogOptionsObj.setIsActive( Constants.Yes);
	   loyaltyLogOptionsObj.setLogOptionType(Constants.S);
	   loyaltyLogOptionsObj.setLogFlag( getSimulation());
	  
	   if( getWriteCustomerFlag()==true){
			loyaltyLogOptionsObj.setWriteCustomerFlag( Constants.Yes);
		}else{
			loyaltyLogOptionsObj.setWriteCustomerFlag( Constants.No);
		}

	   iLoyaltyCalculationService.saveRecords(loyaltyLogOptionsObj);
	}else if(i==2){
		 LoyaltyLogOptions loyaltyLogOptionsObj1=new LoyaltyLogOptions();
			
		    loyaltyLogOptionsObj1.setCreatedBy( session.getUserName());
		    loyaltyLogOptionsObj1.setCreatedDate(new Date());
			
			CountryMaster countryMasterObj=new CountryMaster();
			countryMasterObj.setCountryId( session.getCountryId());
			loyaltyLogOptionsObj1.setApplicationCountryId(countryMasterObj );
			loyaltyLogOptionsObj1.setIsActive( Constants.Yes);
		 
			loyaltyLogOptionsObj1.setLogFlag( getSimulation());
			loyaltyLogOptionsObj1.setLogOptionType( Constants.R);
			loyaltyLogOptionsObj1.setLogFlag(getRealTime());
			
			 if( getWriteCustomerFlag()==true){
				 loyaltyLogOptionsObj1.setWriteCustomerFlag( Constants.Yes);
				}else{
				loyaltyLogOptionsObj1.setWriteCustomerFlag( Constants.No);
				}

		       iLoyaltyCalculationService.saveRecords(loyaltyLogOptionsObj1);
	}
	}
RequestContext.getCurrentInstance().execute("complete.show();");
}else{
	updateRecord();
}
}catch(NullPointerException  e){ 
	 
		setErrorMsg("saveRecords :");
		RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
	}
catch (Exception e) {
		setErrorMsg(e.getMessage());
		RequestContext.getCurrentInstance().execute("csp.show();");
	}
	}
	
	
	public void updateRecord(){
		try{ 
		List<LoyaltyLogOptions> iloyaltyCalOptionList=iLoyaltyCalculationService.getAllRecords();
		 if(iloyaltyCalOptionList.size()>0){
		  for(LoyaltyLogOptions loyaltyCalcOptions:iloyaltyCalOptionList){
			  if(loyaltyCalcOptions.getLogOptionType().equalsIgnoreCase( Constants.S)){
				  LoyaltyLogOptions loyaltyLogOptionsobj=new LoyaltyLogOptions();
				  loyaltyLogOptionsobj.setLoyaltyLogOptionsId(loyaltyCalcOptions.getLoyaltyLogOptionsId());
				  loyaltyLogOptionsobj.setCreatedBy(loyaltyCalcOptions.getCreatedBy() );
				  loyaltyLogOptionsobj.setCreatedDate( loyaltyCalcOptions.getCreatedDate());
				  loyaltyLogOptionsobj.setModifiedBy(session.getUserName() );
				  loyaltyLogOptionsobj.setModifiedDate( new Date());
				  loyaltyLogOptionsobj.setIsActive(loyaltyCalcOptions.getIsActive() );
				  loyaltyLogOptionsobj.setLogOptionType(loyaltyCalcOptions.getLogOptionType() );
				  loyaltyLogOptionsobj.setWriteCustomerFlag(getWriteCustomerFlag().toString() );
				  loyaltyLogOptionsobj.setLogFlag(getSimulation());
				 CountryMaster countrtMasterObj=new CountryMaster();
				 countrtMasterObj.setCountryId( loyaltyCalcOptions.getApplicationCountryId().getCountryId());
				  loyaltyLogOptionsobj.setApplicationCountryId(countrtMasterObj );
				  if( getWriteCustomerFlag() ==true){
					  loyaltyLogOptionsobj.setWriteCustomerFlag( Constants.Yes);
						}else{
							loyaltyLogOptionsobj.setWriteCustomerFlag( Constants.No);
						}
				  iLoyaltyCalculationService.saveRecords( loyaltyLogOptionsobj);
			  }else if(loyaltyCalcOptions.getLogOptionType().equalsIgnoreCase( Constants.R)){
				  LoyaltyLogOptions loyaltyLogOptionsobj1=new LoyaltyLogOptions();
				  loyaltyLogOptionsobj1.setLoyaltyLogOptionsId(loyaltyCalcOptions.getLoyaltyLogOptionsId());
				  loyaltyLogOptionsobj1.setCreatedBy(loyaltyCalcOptions.getCreatedBy() );
				  loyaltyLogOptionsobj1.setCreatedDate( loyaltyCalcOptions.getCreatedDate());
				  loyaltyLogOptionsobj1.setModifiedBy(session.getUserName() );
				  loyaltyLogOptionsobj1.setModifiedDate( new Date());
				  loyaltyLogOptionsobj1.setIsActive(loyaltyCalcOptions.getIsActive() );
				  loyaltyLogOptionsobj1.setLogOptionType(loyaltyCalcOptions.getLogOptionType() );
				  CountryMaster countrtMasterObj1=new CountryMaster();
				  countrtMasterObj1.setCountryId( loyaltyCalcOptions.getApplicationCountryId().getCountryId());
				  loyaltyLogOptionsobj1.setApplicationCountryId(countrtMasterObj1 );
				  if( getWriteCustomerFlag()==true){
					  loyaltyLogOptionsobj1.setWriteCustomerFlag( Constants.Yes);
						}else{
							loyaltyLogOptionsobj1.setWriteCustomerFlag( Constants.No);
						}
				 
				  loyaltyLogOptionsobj1.setLogFlag(getRealTime());
				  iLoyaltyCalculationService.saveRecords(loyaltyLogOptionsobj1);
			  }
			 
				  
		  }
			 
			RequestContext.getCurrentInstance().execute("complete.show();");
		 }
		}catch(NullPointerException  e){ 
			 
				setErrorMsg("updateRecord :");
				RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
			}
		catch (Exception e) {
				setErrorMsg(e.getMessage());
				RequestContext.getCurrentInstance().execute("csp.show();");
			}
	}
	
	
	
	//exit button code
	public void exitButton(){
		try {
			if (session.getRoleId().equalsIgnoreCase("1")) {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("../registration/employeehome.xhtml");
			} else {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("../registration/branchhome.xhtml");
			}
		} catch (Exception e) {
			log.error("======Problem Ocuured in Exit Button=====");
		}
		
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	
	
	
}
