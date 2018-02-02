package com.amg.exchange.telemarketing.bean;

import java.io.IOException;
import java.io.Serializable;
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
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.telemarketing.model.TelemartCustomer;
import com.amg.exchange.telemarketing.service.TelemarketingService;
import com.amg.exchange.util.SessionStateManage;


@Component("telemarketingBranchTransferMbean")
@Scope("session")
public class TelemarketingBranchTransferMbean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal fromBranchId;
	private BigDecimal nationalityId;
	private String noOfRecords;
	private BigDecimal toBranchId;
	private String errorMessage;
	
	private Boolean searchSelected = false;
	
	private List<CountryBranch> countryBranchList = null;	
	private List<CountryBranch> transferBranchList = null;
	private List<CountryMasterDesc> nationalityList = null;	
	private List<TelemartCustomer> telemartCustomerFromBranchList =null;
	
	private SessionStateManage session = new SessionStateManage();
	
	
	@Autowired
	IGeneralService generalService;
	
	@Autowired
	TelemarketingService telemarketingService;
	
	
	
	public void pageNavigation() {
		try {
			clear();
			filterValues();	
			FacesContext.getCurrentInstance().getExternalContext().redirect("../telemarketing/teleMarketingBranchTransfer.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void searchRecords(){
		if(getFromBranchId()==null){
			RequestContext.getCurrentInstance().execute("selectSourceBranch.show();");	
			return;
		}
		List<TelemartCustomer> telemartCustomerList = telemarketingService.getTelemartCustomerList(getFromBranchId());
		if(telemartCustomerList!=null && telemartCustomerList.size() >0){
			int count = 0;
			count = count+telemartCustomerList.size();
			setNoOfRecords(String.valueOf(count));	
			setTelemartCustomerFromBranchList(telemartCustomerList);
		}else{
			setNoOfRecords(null);			
		}
		
		
		if(getNationalityId()!=null){
			List<TelemartCustomer> fromBranchList = getTelemartCustomerFromBranchList();
			List<TelemartCustomer> nationalityList = new ArrayList<TelemartCustomer>();
			for (TelemartCustomer customerList : fromBranchList)
			{
				List<Customer> customerDetails = telemarketingService.getCustomerList(customerList.getCustomerId());
				if(customerDetails!=null && customerDetails.size()>0)
				{
					Customer customer =customerDetails.get(0);
					if(customer.getFsCountryMasterByNationality()!=null && customer.getFsCountryMasterByNationality().getCountryId().compareTo(getNationalityId())==0)
					{
						nationalityList.add(customerList);
					}
				}				
			}
			
			setNoOfRecords(String.valueOf(nationalityList.size()));
		}
		
		setTransferBranchList(null);
		List<CountryBranch> transferBranch = getCountryBranchList();
		List<CountryBranch> list = new ArrayList<CountryBranch>();
		if(transferBranch!=null && transferBranch.size() > 0){
			for(CountryBranch countryBranch : transferBranch){
				if(countryBranch.getCountryBranchId().compareTo(getFromBranchId())!=0){
					list.add(countryBranch);
				}
			}
			setTransferBranchList(list);			
		}
		setSearchSelected(true);
	}	
	
	
	
	public void filterValues(){

		List<CountryBranch> branchList = generalService.getBranchDetails(session.getCountryId());
		if(branchList!=null && branchList.size() >0){
			setCountryBranchList(branchList);
		}

		List<CountryMasterDesc> nationalityListdetails = generalService.getNationalityList(session.getLanguageId());
		if(nationalityListdetails!=null && nationalityListdetails.size() >0){
			setNationalityList(nationalityListdetails);			

		}
	}
	
	
	public void update(){
		try{
			if(getFromBranchId()==null){
				RequestContext.getCurrentInstance().execute("selectSourceBranch.show();");		 
			}else if(getToBranchId()==null){
				RequestContext.getCurrentInstance().execute("selectTransferBranch.show();");
			}else if(getFromBranchId().compareTo(getToBranchId())==0){
				RequestContext.getCurrentInstance().execute("selectBranch.show();");
			}else if(getNoOfRecords().equalsIgnoreCase("0")){
				RequestContext.getCurrentInstance().execute("countZero.show();");
			}else{
				telemarketingService.branchTransferProcedure(getToBranchId(), getFromBranchId(),getNationalityId());
				RequestContext.getCurrentInstance().execute("success.show();");	
			}
		}catch (Exception e) {
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("errorPage.show();");		
		}
	}
	
	
	public void okSelected(){
		pageNavigation();
	}
	
	
	public void clear(){
		setNationalityId(null);
		setToBranchId(null);		
		setFromBranchId(null);
		setNoOfRecords(null);
		setTelemartCustomerFromBranchList(null);
		setSearchSelected(false);
		setTransferBranchList(null);
	}
	
	
	
	
	public void exit(){
		try {
			clear();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	
	//Getters and Setters.
	
	public List<CountryBranch> getCountryBranchList() {
		return countryBranchList;
	}

	public void setCountryBranchList(List<CountryBranch> countryBranchList) {
		this.countryBranchList = countryBranchList;
	}

	public SessionStateManage getSession() {
		return session;
	}

	public void setSession(SessionStateManage session) {
		this.session = session;
	}

	public IGeneralService getGeneralService() {
		return generalService;
	}

	public void setGeneralService(IGeneralService generalService) {
		this.generalService = generalService;
	}

	public List<CountryMasterDesc> getNationalityList() {
		return nationalityList;
	}

	public void setNationalityList(List<CountryMasterDesc> nationalityList) {
		this.nationalityList = nationalityList;
	}

	public TelemarketingService getTelemarketingService() {
		return telemarketingService;
	}

	public void setTelemarketingService(
			TelemarketingService telemarketingService) {
		this.telemarketingService = telemarketingService;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public BigDecimal getNationalityId() {
		return nationalityId;
	}

	public void setNationalityId(BigDecimal nationalityId) {
		this.nationalityId = nationalityId;
	}

	public BigDecimal getToBranchId() {
		return toBranchId;
	}

	public void setToBranchId(BigDecimal toBranchId) {
		this.toBranchId = toBranchId;
	}

	public BigDecimal getFromBranchId() {
		return fromBranchId;
	}

	public void setFromBranchId(BigDecimal fromBranchId) {
		this.fromBranchId = fromBranchId;
	}

	public String getNoOfRecords() {
		return noOfRecords;
	}

	public void setNoOfRecords(String noOfRecords) {
		this.noOfRecords = noOfRecords;
	}

	public List<TelemartCustomer> getTelemartCustomerFromBranchList() {
		return telemartCustomerFromBranchList;
	}

	public void setTelemartCustomerFromBranchList(
			List<TelemartCustomer> telemartCustomerFromBranchList) {
		this.telemartCustomerFromBranchList = telemartCustomerFromBranchList;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}


	public Boolean getSearchSelected() {
		return searchSelected;
	}


	public void setSearchSelected(Boolean searchSelected) {
		this.searchSelected = searchSelected;
	}


	public List<CountryBranch> getTransferBranchList() {
		return transferBranchList;
	}


	public void setTransferBranchList(List<CountryBranch> transferBranchList) {
		this.transferBranchList = transferBranchList;
	}

}
