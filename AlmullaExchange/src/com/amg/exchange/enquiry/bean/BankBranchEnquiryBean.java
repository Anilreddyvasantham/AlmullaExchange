package com.amg.exchange.enquiry.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.enquiry.service.IBankBranchEnquiryService;
import com.amg.exchange.remittance.bean.AdditionalBankRuleBean;
import com.amg.exchange.treasury.bean.BankCountryPopulationBean;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

/*
 * Author RAHAMATHALI SHAIK
*/

@Component("bankBranchEnquiry")
@Scope("session")
public class BankBranchEnquiryBean<T> implements Serializable{
	private static final Logger log = Logger.getLogger(BankBranchEnquiryBean.class);
	
	private static final long serialVersionUID = -8064353283451969560L;
	private BigDecimal bankCountryId;
	private BigDecimal bankId;
	private Boolean renderDataTable=false;
	private String errorMessage=null;
	
	private SessionStateManage session=new SessionStateManage();
	private List<BankCountryPopulationBean> bankCountryList=new ArrayList<BankCountryPopulationBean>();
	private List<BankMaster> bankList=new ArrayList<BankMaster>();
	private List<BankBranchEnquiryDataTable> bankBranchEnquiryList = new ArrayList<BankBranchEnquiryDataTable>();
	
	@Autowired
	IGeneralService<?> generalService;
	@Autowired
	IBankBranchEnquiryService bankBranchEnquiryService;
	

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
	public BigDecimal getBankId() {
		return bankId;
	}
	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}
	public List<BankCountryPopulationBean> getBankCountryList() {
		return bankCountryList;
	}
	public void setBankCountryList(List<BankCountryPopulationBean> bankCountryList) {
		this.bankCountryList = bankCountryList;
	}
	public List<BankMaster> getBankList() {
		return bankList;
	}
	public void setBankList(List<BankMaster> bankList) {
		this.bankList = bankList;
	}
	public List<BankBranchEnquiryDataTable> getBankBranchEnquiryList() {
		return bankBranchEnquiryList;
	}
	public void setBankBranchEnquiryList(
			List<BankBranchEnquiryDataTable> bankBranchEnquiryList) {
		this.bankBranchEnquiryList = bankBranchEnquiryList;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public void bankCountryChange(){
		setBankId(null);
		setRenderDataTable(false);
		bankBranchEnquiryList.clear();
		bankList.clear();
		       //for Populating all Banks Based on Country
		try{
				List<BankMaster> bankList= generalService.getAllBankList(session.getLanguageId(), getBankCountryId());
				if(bankList.size()>0){
					setBankList(bankList);
				}
		} catch (Exception exception) {
			setErrorMessage(exception.getMessage());
			log.info("Method Name::bankCountryChange "+exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}
	}
	public void bankChange(){
		setRenderDataTable(false);
		bankBranchEnquiryList.clear();
	}
	
	public void onLoadRecordsPopulate(){
		//for Populating all Banks
		try{
		List<BankCountryPopulationBean> bankCountryList = generalService.getAllBankContry();
		if(bankCountryList.size()>0){
			setBankCountryList(bankCountryList);
		}
		} catch (Exception exception) {
			setErrorMessage(exception.getMessage());
			log.info("Method Name::onLoadRecordsPopulate "+exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}
	}
	
	public void searchRecords(){
		bankBranchEnquiryList.clear();
		try{
		List<BankBranch> bankBranchList = bankBranchEnquiryService.getBranchDetails(getBankCountryId(), getBankId());
		if(bankBranchList.size()>0){
		for(BankBranch bankBranch:bankBranchList){
			BankBranchEnquiryDataTable bankBranchEnquiry = new BankBranchEnquiryDataTable();
			bankBranchEnquiry.setBranchCode(bankBranch.getBranchCode());
			bankBranchEnquiry.setBranchName(bankBranch.getBranchFullName());
			bankBranchEnquiry.setContactPerson(bankBranch.getContactPerson());
			bankBranchEnquiry.setCityId(bankBranch.getFsCityMaster().getCityId());
			String cityName=generalService.getCityName(session.getLanguageId(), getBankCountryId(), bankBranch.getFsStateMaster().getStateId(), bankBranch.getFsDistrictMaster().getDistrictId(), bankBranch.getFsCityMaster().getCityId());
			if(cityName!=null){
			bankBranchEnquiry.setCityName(cityName);
			}
			bankBranchEnquiry.setCountryId(bankBranch.getFsCountryMaster().getCountryId());
			String countryName = generalService.getCountryName(bankBranch.getFsCountryMaster().getCountryId());
			if(countryName!=null){
			bankBranchEnquiry.setCountryName(countryName);
			}
			bankBranchEnquiry.setDistrctId(bankBranch.getFsDistrictMaster().getDistrictId());
			String districtName = generalService.getDistrictName(session.getLanguageId(), getBankCountryId(), bankBranch.getFsStateMaster().getStateId(), bankBranch.getFsDistrictMaster().getDistrictId());
			if(districtName!=null){
			bankBranchEnquiry.setDistictName(districtName);
			}
			bankBranchEnquiry.setStateId(bankBranch.getFsStateMaster().getStateId());
			String state = generalService.getStateName(session.getLanguageId(), getBankCountryId(), bankBranch.getFsStateMaster().getStateId());
			if(state!=null){
			bankBranchEnquiry.setStateName(state);
			}
			bankBranchEnquiry.setIfscCode(bankBranch.getBranchIfsc());
			bankBranchEnquiry.setZipCode(bankBranch.getZipCode());
			bankBranchEnquiry.setTelephoneNumnber(bankBranch.getMobile());
			
			if(bankBranch.getIsactive()!=null){
				if(bankBranch.getIsactive().equalsIgnoreCase(Constants.Yes)){
					bankBranchEnquiry.setStatus("Approved");			
				 }else if(bankBranch.getIsactive().equalsIgnoreCase(Constants.D)){
					 bankBranchEnquiry.setStatus("Deleted");
				 }else{
					 bankBranchEnquiry.setStatus("Un-Approved");
				 }
				}
			bankBranchEnquiryList.add(bankBranchEnquiry);
			}
			setRenderDataTable(true);
		}else{
			RequestContext.getCurrentInstance().execute("noRecords.show();");
			setRenderDataTable(false);
		}
		
		}catch(NullPointerException ne){
			  log.info("Method Name::searchRecords"+ne.getMessage());
			  setErrorMessage("Method Name::searchRecords"); 
			    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			    return; 
		} catch (Exception exception) {
			setErrorMessage(exception.getMessage());
			log.info("Method Name::searchRecords "+exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}
		
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void navigateToBankBranchEnquiryPage(){
		setBankCountryId(null);
		setBankId(null);
		setRenderDataTable(false);
		onLoadRecordsPopulate();
		
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "bankBranchEnquiry.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../enquiry/bankBranchEnquiry.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
