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
import com.amg.exchange.loyalty.model.LoyaltyMailOptions;
import com.amg.exchange.loyalty.service.ILoyaltyAutoEmailConfigurationService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;
@Component("loyaltyAutoEmailConfigurationsBean")
@Scope("session")
public class LoyaltyAutoEmailConfigurationsBean<T> implements Serializable {
 
	private static final long serialVersionUID = 1L;
	private static final Logger log=Logger.getLogger(LoyaltyAutoEmailConfigurationsBean.class);
	private SessionStateManage session=new SessionStateManage();
	
	private String smtpHost;
	private String fromAddess;
	private String toAddressOne;
	private String toAddressTwo;
	private Boolean mailFlag;
	private BigDecimal loyaltyMailOptionId;
	private String errorMsg;
	
	@Autowired
	ILoyaltyAutoEmailConfigurationService iLoyaltyAutoEmailConfigService;
	
	public String getSmtpHost() {
		return smtpHost;
	}
	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}
	public String getFromAddess() {
		return fromAddess;
	}
	public void setFromAddess(String fromAddess) {
		this.fromAddess = fromAddess;
	}
	public String getToAddressOne() {
		return toAddressOne;
	}
	public void setToAddressOne(String toAddressOne) {
		this.toAddressOne = toAddressOne;
	}
	public String getToAddressTwo() {
		return toAddressTwo;
	}
	public void setToAddressTwo(String toAddressTwo) {
		this.toAddressTwo = toAddressTwo;
	}
	public Boolean getMailFlag() {
		return mailFlag;
	}
	public void setMailFlag(Boolean mailFlag) {
		this.mailFlag = mailFlag;
	}
	public BigDecimal getLoyaltyMailOptionId() {
		return loyaltyMailOptionId;
	}
	public void setLoyaltyMailOptionId(BigDecimal loyaltyMailOptionId) {
		this.loyaltyMailOptionId = loyaltyMailOptionId;
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void pageNavigationToAutoEmailConfig(){
		setFromAddess(null);
		setMailFlag( null);
		setToAddressOne( null);
		setToAddressTwo( null);
		setSmtpHost(null);
		
		List<LoyaltyMailOptions>  iloyaltyAutoEmailConfigurations=	iLoyaltyAutoEmailConfigService.getAllRecords();	
		if(iloyaltyAutoEmailConfigurations.size()>0){
			
			setFromAddess(iloyaltyAutoEmailConfigurations.get(0).getFromAddress());
			setToAddressOne(iloyaltyAutoEmailConfigurations.get(0).getToAddress1());
			setToAddressTwo(iloyaltyAutoEmailConfigurations.get(0).getToAddress2() );
			setSmtpHost(iloyaltyAutoEmailConfigurations.get(0).getSmtpHost() );
			if(iloyaltyAutoEmailConfigurations.get(0).getMailFlag()!=null){
			if(iloyaltyAutoEmailConfigurations.get(0).getMailFlag().equalsIgnoreCase( Constants.Yes)){
			   setMailFlag(true);
					}else if(iloyaltyAutoEmailConfigurations.get(0).getMailFlag().equalsIgnoreCase( Constants.No)){
				setMailFlag(false);
			}
			}
		}
			try {
				loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "loyaltyautoemailconfigurations.xhtml");
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../loyalty/loyaltyautoemailconfigurations.xhtml");
		} catch (Exception e) {
			log.error("Error in Page Navigation to  Loyalty  Auto Email Configurations");
		}
		
	}
		
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
			log.error("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
		}
	}
	
	public void saveRecord(){
		try{
	List<LoyaltyMailOptions>  iloyaltyAutoEmailConfigurations=	iLoyaltyAutoEmailConfigService.getAllRecords();
		if(iloyaltyAutoEmailConfigurations.size()==0){
					
		LoyaltyMailOptions loyaltyMailOptionObj=new LoyaltyMailOptions();
				
		loyaltyMailOptionObj.setSmtpHost( getSmtpHost());
		loyaltyMailOptionObj.setFromAddress( getFromAddess());
		loyaltyMailOptionObj.setToAddress1( getToAddressOne());
		loyaltyMailOptionObj.setToAddress2( getToAddressTwo());
		loyaltyMailOptionObj.setCreatedBy(session.getUserName());
		loyaltyMailOptionObj.setCreatedDate( new Date());
		loyaltyMailOptionObj.setIsActive( Constants.Yes);
		
		if(getMailFlag()){
			loyaltyMailOptionObj.setMailFlag( Constants.Yes);
		}else{
			loyaltyMailOptionObj.setMailFlag(Constants.No ) ;
		}

		CountryMaster countryMasterObj=new CountryMaster();
		countryMasterObj.setCountryId(session.getCountryId() );
		loyaltyMailOptionObj.setApplicationCountryId(countryMasterObj );
		
		iLoyaltyAutoEmailConfigService.saveRecords(loyaltyMailOptionObj );
		RequestContext.getCurrentInstance().execute("complete.show();");
		}else{
			updateRecord();
			
		}
		}catch(NullPointerException  e){ 
			setErrorMsg("saveRecord :");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
	catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}
	public void updateRecord(){
	try{
	List<LoyaltyMailOptions>  iloyaltyAutoEmailConfigurations=	iLoyaltyAutoEmailConfigService.getAllRecords();
 
		LoyaltyMailOptions loyaltyMailOptionObj=new LoyaltyMailOptions();
	
		loyaltyMailOptionObj.setloyaltyMailOptionsId(iloyaltyAutoEmailConfigurations.get(0).getloyaltyMailOptionsId() );
		loyaltyMailOptionObj.setSmtpHost( getSmtpHost());
		loyaltyMailOptionObj.setFromAddress(getFromAddess() );
		loyaltyMailOptionObj.setToAddress1( getToAddressOne());
		loyaltyMailOptionObj.setToAddress2(getToAddressTwo());
		loyaltyMailOptionObj.setCreatedBy(iloyaltyAutoEmailConfigurations.get( 0).getCreatedBy());
		loyaltyMailOptionObj.setCreatedDate( iloyaltyAutoEmailConfigurations.get( 0).getCreatedDate());
		loyaltyMailOptionObj.setModifiedBy(session.getUserName());
		loyaltyMailOptionObj.setModifiedDate(new Date() );
		loyaltyMailOptionObj.setIsActive(Constants.Yes );
		
		if(getMailFlag()){
			loyaltyMailOptionObj.setMailFlag( Constants.Yes);
		}else{
			loyaltyMailOptionObj.setMailFlag(Constants.No ) ;
		}
	
		CountryMaster countryMasterObj=new CountryMaster();
		countryMasterObj.setCountryId( iloyaltyAutoEmailConfigurations.get( 0).getApplicationCountryId().getCountryId());
		loyaltyMailOptionObj.setApplicationCountryId(countryMasterObj );
		
		iLoyaltyAutoEmailConfigService.saveRecords(loyaltyMailOptionObj );
		RequestContext.getCurrentInstance().execute("complete.show();");
	}catch(NullPointerException  e){ 
		 
		setErrorMsg("updateRecord :");
		RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
	}
catch (Exception e) {
		setErrorMsg(e.getMessage());
		RequestContext.getCurrentInstance().execute("csp.show();");
	}
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	
	
	
}
