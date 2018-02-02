package com.amg.exchange.enquiry.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;


import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.enquiry.service.IBankMasterEnquiryService;
import com.amg.exchange.treasury.bean.BankCountryPopulationBean;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankAccountLength;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankContactDetails;
import com.amg.exchange.treasury.model.BankIndicatorDescription;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("bankMasterEnquiry")
@Scope("session")
public class BankMasterEnquiryBean<T> extends BankMaster  implements Serializable{

	private static final long serialVersionUID = 6117879247190857682L;

	private List<BankMasterEnquiryDataTable> bankMasterEnquiryList=new ArrayList<BankMasterEnquiryDataTable>();
	private List<BankMasterEnquiryDailogDataTable> bankMasterEnquiryAccontDailogList=new ArrayList<BankMasterEnquiryDailogDataTable>();
	
	
	private SessionStateManage session=new SessionStateManage();
	private Map<BigDecimal, String> bankDetailsMap=new HashMap<BigDecimal, String>();
	
	private BigDecimal bankCountryId;
	private BigDecimal bankIndicatorId;
	private List<BankCountryPopulationBean> bankCountryList=new ArrayList<BankCountryPopulationBean>();
	private List<BankIndicatorDescription> bankIndicatorList=new ArrayList<BankIndicatorDescription>();

	
	private Boolean renderDataTable =false;
	
	@Autowired
	IBankMasterEnquiryService bankMasterEnquiryService;
	@Autowired
	IGeneralService<?> generalService;
			
	

	public List<BankCountryPopulationBean> getBankCountryList() {
		return bankCountryList;
	}
	public void setBankCountryList(List<BankCountryPopulationBean> bankCountryList) {
		this.bankCountryList = bankCountryList;
	}
	public Boolean getRenderDataTable() {
		return renderDataTable;
	}
	public void setRenderDataTable(Boolean renderDataTable) {
		this.renderDataTable = renderDataTable;
	}
	public BigDecimal getBankCountryId() {
		return bankCountryId;
	}
	public void setBankCountryId(BigDecimal bankCountryId) {
		this.bankCountryId = bankCountryId;
	}
	public BigDecimal getBankIndicatorId() {
		return bankIndicatorId;
	}
	public void setBankIndicatorId(BigDecimal bankIndicatorId) {
		this.bankIndicatorId = bankIndicatorId;
	}

	public List<BankIndicatorDescription> getBankIndicatorList() {
		return bankIndicatorList;
	}
	public void setBankIndicatorList(
			List<BankIndicatorDescription> bankIndicatorList) {
		this.bankIndicatorList = bankIndicatorList;
	}

	
	
	public List<BankMasterEnquiryDailogDataTable> getBankMasterEnquiryAccontDailogList() {
		return bankMasterEnquiryAccontDailogList;
	}
	public void setBankMasterEnquiryAccontDailogList( List<BankMasterEnquiryDailogDataTable> bankMasterEnquiryAccontDailogList) {
		this.bankMasterEnquiryAccontDailogList = bankMasterEnquiryAccontDailogList;
	}
	public List<BankMasterEnquiryDataTable> getBankMasterEnquiryList() {
		return bankMasterEnquiryList;
	}
	public void setBankMasterEnquiryList(
			List<BankMasterEnquiryDataTable> bankMasterEnquiryList) {
		this.bankMasterEnquiryList = bankMasterEnquiryList;
	}


	public void populateAllBanks(){
		bankMasterEnquiryList.clear();
	 List<BankApplicability>	bankApplicabilityList = bankMasterEnquiryService.getBankMasterDetails(getBankCountryId(),getBankIndicatorId());
	 if(bankApplicabilityList.size()>0){
	 for(BankApplicability bankApplicability:bankApplicabilityList){
		 BankMasterEnquiryDataTable bankMasterEnquiry=new BankMasterEnquiryDataTable();
		 
		 bankMasterEnquiry.setBankId(bankApplicability.getBankMaster().getBankId());
		 bankMasterEnquiry.setBankCode(bankApplicability.getBankMaster().getBankCode());
		 bankMasterEnquiry.setBankName(bankApplicability.getBankMaster().getBankFullName());
		 bankMasterEnquiry.setBankLength(bankApplicability.getBankMaster().getBankCode()+" Account Length");
		 bankMasterEnquiry.setContactDetails(bankApplicability.getBankMaster().getBankCode()+" Contact Details");
		 bankMasterEnquiry.setAccountDetails(bankApplicability.getBankMaster().getBankCode()+" Account Details");
		 String bankIndicatorName = bankMasterEnquiryService.getBankIndicatorName(bankApplicability.getBankInd().getBankIndicatorId(), session.getLanguageId());
		 if(bankIndicatorName!=null){
		 bankMasterEnquiry.setBankIndicator(bankIndicatorName);
		 }else{
			 bankMasterEnquiry.setBankIndicator(null);
		 }
		 bankDetailsMap.put(bankApplicability.getBankMaster().getBankId(), bankApplicability.getBankMaster().getBankFullName());
			if(bankApplicability.getBankMaster().getRecordStatus()!=null){
				if(bankApplicability.getBankMaster().getRecordStatus().equalsIgnoreCase(Constants.Yes)){
					bankMasterEnquiry.setStatus("Approved");			
				 }else if(bankApplicability.getBankMaster().getRecordStatus().equalsIgnoreCase(Constants.D)){
					 bankMasterEnquiry.setStatus("Deleted");
				 }else{
					 bankMasterEnquiry.setStatus("Un-Approved");
				 }
				}
		 bankMasterEnquiryList.add(bankMasterEnquiry);
	 }
	 setRenderDataTable(true);
	 }else{
		 RequestContext.getCurrentInstance().execute("noRecords.show();");
		 setRenderDataTable(false);
	 }
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void navigateToBankMasterEnquiryPage(){
		bankDetailsMap.clear();
		setRenderDataTable(false);
		setBankCountryId(null);
		setBankIndicatorId(null);
		List<BankIndicatorDescription>	bankApplicabilityList = bankMasterEnquiryService.getAllBankIndicators(session.getLanguageId());
		if(bankApplicabilityList.size()>0){
			setBankIndicatorList(bankApplicabilityList);
		} 
		List<BankCountryPopulationBean> bankCountryList = generalService.getAllBankContry();
		if(bankCountryList.size()>0){
			setBankCountryList(bankCountryList);
		}
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "bankMasterEnquiry.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../enquiry/bankMasterEnquiry.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void bankAccountLength(BigDecimal bankId){
		bankMasterEnquiryAccontDailogList.clear();
          List<BankAccountLength>	bankAccountLengthList = bankMasterEnquiryService.getAllBankLengthRecords(bankId);
          if(bankAccountLengthList.size()>0){
		for(BankAccountLength bankLength:bankAccountLengthList){
			BankMasterEnquiryDailogDataTable bankEnquiry=new BankMasterEnquiryDailogDataTable();
			bankEnquiry.setBankAccountLength(bankLength.getAcLength());
			bankEnquiry.setBankName(bankDetailsMap.get(bankLength.getBankMaster().getBankId()));
			
			if(bankLength.getRecordStatus()!=null){
				if(bankLength.getRecordStatus().equalsIgnoreCase(Constants.Yes)){
					bankEnquiry.setStatus("Approved");			
				 }else if(bankLength.getRecordStatus().equalsIgnoreCase(Constants.D)){
					 bankEnquiry.setStatus("Deleted");
				 }else{
					 bankEnquiry.setStatus("Un-Approved");
				 }
				}
			bankMasterEnquiryAccontDailogList.add(bankEnquiry);
		}
          RequestContext.getCurrentInstance().execute("bankAccountLength.show();");
          }else{
        	  RequestContext.getCurrentInstance().execute("noRecords.show();");
          }
	}

	public void bankContactDetails(BigDecimal bankId){
		bankMasterEnquiryAccontDailogList.clear();
        List<BankContactDetails>	bankContactDetailsList = bankMasterEnquiryService.getAllBankContactDetailsRecords(bankId);
        if(bankContactDetailsList.size()>0){
		for(BankContactDetails bankLength:bankContactDetailsList){
			BankMasterEnquiryDailogDataTable bankEnquiry=new BankMasterEnquiryDailogDataTable();
			bankEnquiry.setBankName(bankDetailsMap.get(bankLength.getExBankMaster().getBankId()));
			bankEnquiry.setDepartment(bankLength.getContactDept());
			bankEnquiry.setContactPerson(bankLength.getContactPerson());
			bankEnquiry.setMobileNumber(bankLength.getMobile());
			bankEnquiry.setDesignation(bankLength.getContactDesg());
			bankEnquiry.setZoneId(bankLength.getExZone().getZoneId());
			String zoneName = bankMasterEnquiryService.getZoneName(bankLength.getExZone().getZoneId(), session.getLanguageId());
			if(zoneName!=null){
			bankEnquiry.setZone(zoneName);
			}else{
				bankEnquiry.setZone(null);
			}
			
			if(bankLength.getRecordStatus()!=null){
				if(bankLength.getRecordStatus().equalsIgnoreCase(Constants.Yes)){
					bankEnquiry.setStatus("Approved");			
				 }else if(bankLength.getRecordStatus().equalsIgnoreCase(Constants.D)){
					 bankEnquiry.setStatus("Deleted");
				 }else{
					 bankEnquiry.setStatus("Un-Approved");
				 }
				}
			bankMasterEnquiryAccontDailogList.add(bankEnquiry);
		}
        RequestContext.getCurrentInstance().execute("bankContactDetails.show();");
        }else{
        	RequestContext.getCurrentInstance().execute("noRecords.show();");
        }
	}

	public void bankAccountDetails(BigDecimal bankId){
		bankMasterEnquiryAccontDailogList.clear();
        List<BankAccountDetails>	bankAccountDetailsList = bankMasterEnquiryService.getAllBankAccountDetailsRecords(bankId);
        if(bankAccountDetailsList.size()>0){
		for(BankAccountDetails bankAccountDetails:bankAccountDetailsList){
			BankMasterEnquiryDailogDataTable bankEnquiry=new BankMasterEnquiryDailogDataTable();
			bankEnquiry.setBankName(bankDetailsMap.get(bankAccountDetails.getExBankMaster().getBankId()));
			bankEnquiry.setMinimumBalance(bankAccountDetails.getMinBal());
			String currencyName = generalService.getCurrencyName(bankAccountDetails.getFsCurrencyMaster().getCurrencyId());
			if(currencyName!=null){
			bankEnquiry.setCurrencyName(currencyName);
			}
			bankEnquiry.setBankAccountNumber(bankAccountDetails.getBankAcctNo());
			bankEnquiry.setFaAccountNumber(bankAccountDetails.getGlno());
			bankEnquiry.setFaFundAccountNumber(bankAccountDetails.getFundGlno());
			bankEnquiry.setAccountTypeId(bankAccountDetails.getBankAccountType().getBankAccountTypeId());
			String accountTypeName = bankMasterEnquiryService.getBankAccountTypeName(bankAccountDetails.getBankAccountType().getBankAccountTypeId(),session.getLanguageId());
			if(accountTypeName!=null){
			bankEnquiry.setAccountTypeName(accountTypeName);
			}
			bankEnquiry.setOverDraftLimit(bankAccountDetails.getOdlmt());
			if(bankAccountDetails.getRecordStatus()!=null){
				if(bankAccountDetails.getRecordStatus().equalsIgnoreCase(Constants.Yes)){
					bankEnquiry.setStatus("Approved");			
				 }else if(bankAccountDetails.getRecordStatus().equalsIgnoreCase(Constants.D)){
					 bankEnquiry.setStatus("Deleted");
				 }else{
					 bankEnquiry.setStatus("Un-Approved");
				 }
				}
			bankMasterEnquiryAccontDailogList.add(bankEnquiry);
		}
        RequestContext.getCurrentInstance().execute("bankAccountDetails.show();");
      }else{
    	  RequestContext.getCurrentInstance().execute("noRecords.show();");
        }
	}
	
	public void searchRecords(){
		populateAllBanks();
	}
	
	public void bankCountryChange(){
		setRenderDataTable(false);
		bankMasterEnquiryList.clear();
		setBankIndicatorId(null);
	}
	public void bankIndicatorChange(){
		setRenderDataTable(false);
		bankMasterEnquiryList.clear();
	}
	
}
