package com.amg.exchange.remittance.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.bean.IncomeRangeDatatable;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.registration.model.IncomeRangeMaster;
import com.amg.exchange.registration.model.RoleMaster;
import com.amg.exchange.remittance.model.HighValueCurrencySetup;
import com.amg.exchange.remittance.service.IHighValueCurrencyService;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.util.CollectionUtil;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("highValueCurrency")
@Scope("session")
public class HighValueCurrencyBean<T> implements Serializable {

	private static final Logger LOGGER = Logger.getLogger(HighValueCurrencyBean.class);
	private static final long serialVersionUID = 1L;

	private BigDecimal highValueSetupId;
	private BigDecimal appCountryId;
	private String currencyCode;
	private BigDecimal currencyId;
	private BigDecimal highvalueAmount;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String isActive;
	private String remarks;
	private String currencyName;

	private Boolean booCheckUpdate = false;
	private Boolean booHighValueCurrency = false;
	private Boolean booHighValueCurrencyDataTable = false;
	
	List<CurrencyMaster> lstOfCurrency = new ArrayList<CurrencyMaster>();
	List<HighValueCurrencyDataTable> lstofHighValueCurrencyDataTable = new CopyOnWriteArrayList<HighValueCurrencyDataTable>();	
	List<HighValueCurrencySetup> lstofEnquiryCurrency= new ArrayList<HighValueCurrencySetup>();	

	private List<CurrencyMaster> lstcurrency = new ArrayList<CurrencyMaster>();
	
	

	@Autowired
	IGeneralService<T> generalService;
	
	@Autowired
	IHighValueCurrencyService<T> highValueCurrencyService;

	SessionStateManage sessionStateManage = new SessionStateManage();

	public HighValueCurrencyBean() {
		// TODO Auto-generated constructor stub
	}

	public BigDecimal getAppCountryId() {
		return appCountryId;
	}

	public void setAppCountryId(BigDecimal appCountryId) {
		this.appCountryId = appCountryId;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public BigDecimal getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	public BigDecimal getHighValueSetupId() {
		return highValueSetupId;
	}

	public void setHighValueSetupId(BigDecimal highValueSetupId) {
		this.highValueSetupId = highValueSetupId;
	}

	


	public List<CurrencyMaster> getLstcurrency() {
		return lstcurrency;
	}

	public void setLstcurrency(List<CurrencyMaster> lstcurrency) {
		this.lstcurrency = lstcurrency;
	}

	public BigDecimal getHighvalueAmount() {
		return highvalueAmount;
	}

	public void setHighvalueAmount(BigDecimal highvalueAmount) {
		this.highvalueAmount = highvalueAmount;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public IGeneralService<T> getGeneralService() {
		return generalService;
	}

	public void setGeneralService(IGeneralService<T> generalService) {
		this.generalService = generalService;
	}

	public Boolean getBooHighValueCurrency() {
		return booHighValueCurrency;
	}

	public void setBooHighValueCurrency(Boolean booHighValueCurrency) {
		this.booHighValueCurrency = booHighValueCurrency;
	}

	public Boolean getBooHighValueCurrencyDataTable() {
		return booHighValueCurrencyDataTable;
	}

	public void setBooHighValueCurrencyDataTable(Boolean booHighValueCurrencyDataTable) {
		this.booHighValueCurrencyDataTable = booHighValueCurrencyDataTable;
	}

	public Boolean getBooCheckUpdate() {
		return booCheckUpdate;
	}

	public void setBooCheckUpdate(Boolean booCheckUpdate) {
		this.booCheckUpdate = booCheckUpdate;
	}

	public List<CurrencyMaster> getLstOfCurrency() {
		return lstOfCurrency;
	}

	public void setLstOfCurrency(List<CurrencyMaster> lstOfCurrency) {
		this.lstOfCurrency = lstOfCurrency;
	}
	
	

	public List<HighValueCurrencySetup> getLstofEnquiryCurrency() {
		return lstofEnquiryCurrency;
	}

	public void setLstofEnquiryCurrency(List<HighValueCurrencySetup> lstofEnquiryCurrency) {
		this.lstofEnquiryCurrency = lstofEnquiryCurrency;
	}

	public List<HighValueCurrencyDataTable> getLstofHighValueCurrencyDataTable() {
		return lstofHighValueCurrencyDataTable;
	}

	public void setLstofHighValueCurrencyDataTable(List<HighValueCurrencyDataTable> lstofHighValueCurrencyDataTable) {
		this.lstofHighValueCurrencyDataTable = lstofHighValueCurrencyDataTable;
	}

	
	public IHighValueCurrencyService<T> getHighValueCurrencyService() {
		return highValueCurrencyService;
	}

	public void setHighValueCurrencyService(IHighValueCurrencyService<T> highValueCurrencyService) {
		this.highValueCurrencyService = highValueCurrencyService;
	}

	/**
	 * 
	 * @return currency List
	 */
	public List<CurrencyMaster> getCurrencyList() {
		try {
			lstcurrency = getGeneralService().getCurrencyList();
		} catch(Exception exception){
			  LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			  setErrorMessage(exception.getMessage());
		}
		return lstcurrency;
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void pageNavigation() {

		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "highvaluecurrency.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/highvaluecurrency.xhtml");
			setBooHighValueCurrency(true);
			setBooHighValueCurrencyDataTable(false);
			//addToDataTable();

		} catch(Exception exception){
		  LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		  setErrorMessage(exception.getMessage()); 
		  RequestContext.getCurrentInstance().execute("error.show();");
		  return;       
		  }

	}

	public void pageNavigationApprove() {

		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "highvaluecurrencyapproval.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/highvaluecurrencyapproval.xhtml");
			setBooHighValueCurrency(false);
			setBooHighValueCurrencyDataTable(true);
			addToApproveDataTable();

		} catch(Exception exception){
		  LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		  setErrorMessage(exception.getMessage()); 
		  RequestContext.getCurrentInstance().execute("error.show();");
		  return;       
		  }

	}

	public void approve(HighValueCurrencyDataTable highValueCurrencyDataTable) {

		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/highvaluecurrencyapproval.xhtml");
			setBooHighValueCurrency(true);
			setBooHighValueCurrencyDataTable(false);
			setHighValueSetupId(highValueCurrencyDataTable.getHighValueSetupId());
			setCurrencyCode(highValueCurrencyDataTable.getCurrencyCode());
			setHighvalueAmount(highValueCurrencyDataTable.getHighValueAmount());
			setCurrencyName(highValueCurrencyDataTable.getCurrencyName());

		} catch(Exception exception){
		  LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		  setErrorMessage(exception.getMessage()); 
		  RequestContext.getCurrentInstance().execute("error.show();");
		  return;       
		  }

	}
	
	

	public void pageNavigationEnquiry() {

		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "highvaluecurrencyenquiry.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/highvaluecurrencyenquiry.xhtml");
			//lstofHighValueCurrencyDataTable.clear();
			setBooHighValueCurrencyDataTable(true);
			addToEnquiryDataTable();
			

		} catch(Exception exception){
		  LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		  setErrorMessage(exception.getMessage()); 
		  RequestContext.getCurrentInstance().execute("error.show();");
		  return;       
		  }

	}

	public void exit() {
		LOGGER.info("Enter in exit method ");
		
		lstofHighValueCurrencyDataTable.clear();
		setCurrencyId(null);
		setHighvalueAmount(null);
		
		

		try {
			List<RoleMaster> rolList = generalService.getRoleList(new BigDecimal(sessionStateManage.getRoleId()));

			if (rolList.get(0).getRoleName().equalsIgnoreCase(Constants.USER_ROLE_MANAGER)) {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");

			} else {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");

			}

		} catch(Exception exception){
		  LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		  setErrorMessage(exception.getMessage()); 
		  RequestContext.getCurrentInstance().execute("error.show();");
		  return;       
		  }

		LOGGER.info("Exit from exit method ");
	}
	
	
	
	public void addToDataTable(){
		  try{
		lstofHighValueCurrencyDataTable.clear();
		lstOfCurrency = getHighValueCurrencyService().getCurrencyList(getCurrencyId());
		
		for(CurrencyMaster currencyMaster : lstOfCurrency){
			HighValueCurrencyDataTable highValueCurrencyDataTable = new HighValueCurrencyDataTable();
			
			highValueCurrencyDataTable.setAppCountryId(sessionStateManage.getCountryId());
			highValueCurrencyDataTable.setCurrencyId(currencyMaster.getCurrencyId());
			highValueCurrencyDataTable.setCurrencyCode(currencyMaster.getCurrencyCode());
			highValueCurrencyDataTable.setCurrencyName(currencyMaster.getCurrencyName());
			
			
			
			List<HighValueCurrencySetup> lstofHighValueCurrency = getHighValueCurrencyService().getAllCurrencyList(currencyMaster.getCurrencyId());
			
			if(lstofHighValueCurrency !=null){
				//for (HighValueCurrencySetup highValueCurrencySetup : lstofHighValueCurrency) {
				highValueCurrencyDataTable.setHighValueSetupId(lstofHighValueCurrency.get(0).getHighValueSetupId());
					highValueCurrencyDataTable.setHighValueAmount(lstofHighValueCurrency.get(0).getHighValueAmount());
			//	}
				
			}
			//highValueCurrencyDataTable.setHighValueAmount(highValueAmount);
			
			setBooHighValueCurrency(true);
			setBooHighValueCurrencyDataTable(true);
			
			lstofHighValueCurrencyDataTable.add(highValueCurrencyDataTable);
			
			
		}
		  }catch(Exception exception){
			    LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			    setErrorMessage(exception.getMessage()); 
			    RequestContext.getCurrentInstance().execute("error.show();");
			    return;       
			    }
	}
	
public void addToEnquiryDataTable(){
	  try{
	lstofHighValueCurrencyDataTable.clear();
		
	lstofEnquiryCurrency = getHighValueCurrencyService().getEnquiryList();
		
		for(HighValueCurrencySetup highValueCurrencySetup : lstofEnquiryCurrency){
			
			HighValueCurrencyDataTable highValueCurrencyDataTable = new HighValueCurrencyDataTable();
			
			highValueCurrencyDataTable.setCurrencyId(highValueCurrencySetup.getCurrencyId().getCurrencyId());
			
			highValueCurrencyDataTable.setCurrencyCode(highValueCurrencySetup.getCurrencyId().getCurrencyCode());
			highValueCurrencyDataTable.setCurrencyName(highValueCurrencySetup.getCurrencyId().getCurrencyName());
			
		
				highValueCurrencyDataTable.setHighValueAmount(highValueCurrencySetup.getHighValueAmount());
				
		
				lstofHighValueCurrencyDataTable.add(highValueCurrencyDataTable);
			
			
		}
	  	}catch(NullPointerException ne){
		    LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
		    setErrorMessage("MethodName::addToEnquiryDataTable");
		    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
		    return; 
		    }catch(Exception exception){
		    LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		    setErrorMessage(exception.getMessage()); 
		    RequestContext.getCurrentInstance().execute("error.show();");
		    return;       
		    }
		
	}

public void addToApproveDataTable(){
	  try{
	lstofHighValueCurrencyDataTable.clear();
		
	lstofEnquiryCurrency = getHighValueCurrencyService().getEnquiryList();
		
		for(HighValueCurrencySetup highValueCurrencySetup : lstofEnquiryCurrency){
			
			if(highValueCurrencySetup.getIsActive().equalsIgnoreCase(Constants.U)){
				
				HighValueCurrencyDataTable highValueCurrencyDataTable = new HighValueCurrencyDataTable();
				
				highValueCurrencyDataTable.setCurrencyId(highValueCurrencySetup.getCurrencyId().getCurrencyId());
				
				highValueCurrencyDataTable.setCurrencyCode(highValueCurrencySetup.getCurrencyId().getCurrencyCode());
				highValueCurrencyDataTable.setCurrencyName(highValueCurrencySetup.getCurrencyId().getCurrencyName());
				highValueCurrencyDataTable.setHighValueSetupId(highValueCurrencySetup.getHighValueSetupId());
				
			
					highValueCurrencyDataTable.setHighValueAmount(highValueCurrencySetup.getHighValueAmount());
					
			
					lstofHighValueCurrencyDataTable.add(highValueCurrencyDataTable);
				
			}
			
			
			
			
		}
	  	}catch(NullPointerException ne){
		    LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
		    setErrorMessage("MethodName::addToApproveDataTable");
		    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
		    return; 
		    }catch(Exception exception){
		    LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		    setErrorMessage(exception.getMessage()); 
		    RequestContext.getCurrentInstance().execute("error.show();");
		    return;       
		    }
		
	}
	
	public void save(){
		try{
		
		
			for(HighValueCurrencyDataTable highValueCurrencyDataTable : lstofHighValueCurrencyDataTable){
				if(highValueCurrencyDataTable.getHighValueAmount() !=null){
				//if(highValueCurrencyDataTable.getBooCheckUpdate()){
					//System.out.println("highValueCurrencyDataTable.getBooCheckUpdate()1 ======= "+highValueCurrencyDataTable.getBooCheckUpdate());
					
					HighValueCurrencySetup highValueCurrencySetup = new HighValueCurrencySetup();
					
					highValueCurrencySetup.setHighValueSetupId(highValueCurrencyDataTable.getHighValueSetupId());
					
					CurrencyMaster currencyMaster = new CurrencyMaster();
					currencyMaster.setCurrencyId(highValueCurrencyDataTable.getCurrencyId());			
					highValueCurrencySetup.setCurrencyId(currencyMaster);
					
					CountryMaster countryMaster = new CountryMaster();
					countryMaster.setCountryId(highValueCurrencyDataTable.getAppCountryId());
					highValueCurrencySetup.setAppCountryId(countryMaster);
					
					highValueCurrencySetup.setHighValueAmount(highValueCurrencyDataTable.getHighValueAmount());
					highValueCurrencySetup.setCreatedBy(sessionStateManage.getUserName());
					
					highValueCurrencySetup.setCreatedDate(new Date());
					
					highValueCurrencySetup.setIsActive(Constants.U);
					
					//try {
						getHighValueCurrencyService().save(highValueCurrencySetup);
						RequestContext.getCurrentInstance().execute("complete.show();");

						
					//} catch (Exception e) {
						// TODO: handle exception
					//}
				
			//}
				
				
				
				
			}else{
				RequestContext.getCurrentInstance().execute("dataNotFund.show();");
			}
		}
		}catch(NullPointerException ne){
			    LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			    setErrorMessage("MethodName::save");
			    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			    return; 
			    }catch(Exception exception){
			    LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			    setErrorMessage(exception.getMessage()); 
			    RequestContext.getCurrentInstance().execute("error.show();");
			    return;       
			    }
		
	}
	
	
	
	public void approveRecord(){		
		try{
		
		String approveMsg=getHighValueCurrencyService().approveReord(getHighValueSetupId(), sessionStateManage.getUserName());
		
		if(approveMsg.equalsIgnoreCase("Success")){
		RequestContext.getCurrentInstance().execute("approve.show();");
		
		}
	else{
		RequestContext.getCurrentInstance().execute("alreadyapprov.show();");
		}
		}catch(NullPointerException ne){
			    LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			    setErrorMessage("MethodName::approveRecord");
			    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			    return; 
			    }catch(Exception exception){
			    LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			    setErrorMessage(exception.getMessage()); 
			    RequestContext.getCurrentInstance().execute("error.show();");
			    return;       
			    }
	}
	
	public void clickOnOK() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/highvaluecurrencyapproval.xhtml");
			setBooHighValueCurrency(false);
			setBooHighValueCurrencyDataTable(true);
			addToApproveDataTable();
			setHighvalueAmount(null);
			setHighValueSetupId(null);
			setCurrencyCode(null);
			setCurrencyName(null);
			

		} catch(Exception exception){
			    LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			    setErrorMessage(exception.getMessage()); 
			    RequestContext.getCurrentInstance().execute("error.show();");
			    return;       
			    }
	}
	public void clickOK() {
		try {
			lstofHighValueCurrencyDataTable.clear();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/highvaluecurrency.xhtml");
			setBooHighValueCurrency(true);
			setBooHighValueCurrencyDataTable(false);
			//addToDataTable();
			setHighvalueAmount(null);
			setHighValueSetupId(null);
			setCurrencyCode(null);
			setCurrencyName(null);
			setCurrencyId(null);
			

		} catch(Exception exception){
			    LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			    setErrorMessage(exception.getMessage()); 
			    RequestContext.getCurrentInstance().execute("error.show();");
			    return;       
			    }
	}
	
	public void confirmDelete(HighValueCurrencyDataTable highValueCurrencyDataTable){
		
		highValueCurrencyDataTable.setBooCheckDelete(true);
		RequestContext.getCurrentInstance().execute("permanentDelete.show();");
		return;
	}
	
	public void confirmPermanentDelete()throws Exception{
		if(lstofHighValueCurrencyDataTable.size()>0){
			for (HighValueCurrencyDataTable  highValueCurrencyDataTable : lstofHighValueCurrencyDataTable) {
				if(highValueCurrencyDataTable.getBooCheckDelete()!=null){
				if(highValueCurrencyDataTable.getBooCheckDelete().equals(true)){
				delete(highValueCurrencyDataTable.getHighValueSetupId());
				lstofHighValueCurrencyDataTable.remove(highValueCurrencyDataTable);
				//RequestContext.getCurrentInstance().execute("delete.show();");

				//serviceCodeMasterDTList.remove(serviceModeDatatable);
				}
			}
		}
		}
	 }
	
	
	
	public void delete(BigDecimal highValueCurrencyId) {

		getHighValueCurrencyService().delete(highValueCurrencyId);
		RequestContext.getCurrentInstance().execute("delete.show();");

	}
	
	public void checkAmountisNumber(FacesContext context, UIComponent component, Object value) {
		try {
		BigDecimal val = new BigDecimal(value.toString());
		@SuppressWarnings("unused")
		boolean isNUmber = CollectionUtil.isIntegerValue(val);
		} catch (Exception e) {
		FacesMessage msg = new FacesMessage(" Amount should be number", " Amount should be number");
		msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		throw new ValidatorException(msg);
		}
		}
	
	  private String errorMessage;

	  public String getErrorMessage() {
		    return errorMessage;
	  }

	  public void setErrorMessage(String errorMessage) {
		    this.errorMessage = errorMessage;
	  }
	
	
	
}
