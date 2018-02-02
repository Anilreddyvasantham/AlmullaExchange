package com.amg.exchange.treasury.bean;

import java.io.IOException;
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

import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.HighValueDealRequestView;
import com.amg.exchange.treasury.service.ISpecialCustomerDealRequestService;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;


@Component("highVlaueDealRequestEnqBean")
@Scope("session")
public class HighValueDealRequestEnquiryBean<T>  implements Serializable{
 
	
	private static final long serialVersionUID = 1L;
	
	private BigDecimal exchangeCountryId;
	private String exchangeCountryName;
	private Date currentDate;
	private BigDecimal currencyId;
	private String currencyName;
	private String errorMsg;
	
	private SessionStateManage session =new SessionStateManage();
	private List<HighValueDealRequestEnquiryDataTable> highValDealReqEnqList;
	private List<CountryMasterDesc> businessCountryList;
	private List<CurrencyMaster> countryCurrencyList;
	private Boolean renderDataTable=false;
	
	@Autowired
	IGeneralService<T> generalService;
	@Autowired
	ISpecialCustomerDealRequestService<T> specialCustomerDealRequestService;
	
	


	public Boolean getRenderDataTable() {
		return renderDataTable;
	}
	public void setRenderDataTable(Boolean renderDataTable) {
		this.renderDataTable = renderDataTable;
	}
	public List<CurrencyMaster> getCountryCurrencyList() {
		return countryCurrencyList;
	}
	public void setCountryCurrencyList(List<CurrencyMaster> countryCurrencyList) {
		this.countryCurrencyList = countryCurrencyList;
	}
	public List<CountryMasterDesc> getBusinessCountryList() {
		return businessCountryList;
	}
	public void setBusinessCountryList(List<CountryMasterDesc> businessCountryList) {
		this.businessCountryList = businessCountryList;
	}
	
	public List<HighValueDealRequestEnquiryDataTable> getHighValDealReqEnqList() {
		return highValDealReqEnqList;
	}
	public void setHighValDealReqEnqList(
			List<HighValueDealRequestEnquiryDataTable> highValDealReqEnqList) {
		this.highValDealReqEnqList = highValDealReqEnqList;
	}
	public BigDecimal getExchangeCountryId() {
		return exchangeCountryId;
	}
	public String getExchangeCountryName() {
		return exchangeCountryName;
	}
	public Date getCurrentDate() {
		return currentDate;
	}
	public BigDecimal getCurrencyId() {
		return currencyId;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setExchangeCountryId(BigDecimal exchangeCountryId) {
		this.exchangeCountryId = exchangeCountryId;
	}
	public void setExchangeCountryName(String exchangeCountryName) {
		this.exchangeCountryName = exchangeCountryName;
	}
	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}
	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	
	
	
	
	
	public void populateApplicationCountryList(){
		try{
		List<CountryMasterDesc> countryList= 	generalService.getBusinessCountryList(session.getLanguageId());
		setBusinessCountryList(countryList);
		}catch(NullPointerException  e){ 
			 
				setErrorMsg("Method:populateApplicationCountryList ");
				RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
			}
		catch (Exception e) {
				setErrorMsg(e.getMessage());
				RequestContext.getCurrentInstance().execute("csp.show();");
			}
	}
	
	public void populateCountryCurrency(){
		try{
		List<CurrencyMaster> currecyList =generalService.getCountryCurrencyList(getExchangeCountryId());
		setCountryCurrencyList(currecyList);
		}catch(NullPointerException  e){ 
			 
				setErrorMsg("Method:populateCountryCurrency");
				RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
			}
		catch (Exception e) {
				setErrorMsg(e.getMessage());
				RequestContext.getCurrentInstance().execute("csp.show();");
			}
	}
	
	public void populateHighValueDetails(){
		try{
		setHighValDealReqEnqList(null);
		List<HighValueDealRequestEnquiryDataTable> highValDealReqEnqList1 = new ArrayList<HighValueDealRequestEnquiryDataTable>();
		
		List<HighValueDealRequestView> highValueDealReqList  =	specialCustomerDealRequestService.getHighValueDealRequestDetails(getExchangeCountryId(), getCurrencyId());
		if(highValueDealReqList.size()>0){
		for(HighValueDealRequestView highvalueReqObj:highValueDealReqList){
			
			HighValueDealRequestEnquiryDataTable highvalObj = new HighValueDealRequestEnquiryDataTable();
			
			highvalObj.setBeneficieryBankName(highvalueReqObj.getBankFullName());
			highvalObj.setCustomerName(highvalueReqObj.getCustomerName());
			highvalObj.setFcAmount(highvalueReqObj.getForeignCurrencyAmount());
			highvalObj.setRequestNumber(highvalueReqObj.getDocumentNumber());
			highvalObj.setValueDate(highvalueReqObj.getValidUpto());
			highvalObj.setSellRate(highvalueReqObj.getSellRate());
			highValDealReqEnqList1.add(highvalObj);
			
		}
		
		setHighValDealReqEnqList(highValDealReqEnqList1);
		
		if(highValDealReqEnqList1!=null && highValDealReqEnqList1.size()>0){
		setRenderDataTable(true);
		}else{
			setRenderDataTable(false);
		}
		}else{
			RequestContext.getCurrentInstance().execute("noRecords.show();");
		}
		}catch(NullPointerException  e){ 
			 
				setErrorMsg("Method:populateHighValueDetails ");
				RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
			}
		catch (Exception e) {
				setErrorMsg(e.getMessage());
				RequestContext.getCurrentInstance().execute("csp.show();");
			}
	}
	
	
	public void clear(){
		setExchangeCountryId(null);
		setExchangeCountryName(null);
		setCurrentDate(null);
		setCurrencyId(null);
		setCurrencyName(null);
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void navigateToHighValueDealRequestEnquiryPage(){
		clear();
		setHighValDealReqEnqList(null);
		setRenderDataTable(false);
		populateApplicationCountryList();
		setCurrentDate(new Date());
		
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "highValueDealRequestEnquiry.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/highValueDealRequestEnquiry.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public void clearAll(){
		setCurrencyId(null);
		setExchangeCountryId(null);
		highValDealReqEnqList.clear();
	}
	
	
}
