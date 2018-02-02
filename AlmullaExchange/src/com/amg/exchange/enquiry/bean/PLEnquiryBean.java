package com.amg.exchange.enquiry.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.enquiry.model.PlEnquiryView;
import com.amg.exchange.enquiry.service.IPlEnquiryService;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("plenqiuryBean")
@Scope("session")
public class PLEnquiryBean<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Logger log = Logger.getLogger(PLEnquiryBean.class);
    private SessionStateManage session =new SessionStateManage();
    
    private BigDecimal currencyId = null;
    private BigDecimal bankId = null;
    
    private Boolean booRenderDataTable = false;
    private String errorMsg;
    private String totalAmt;
    
    List<PLEnquiryDataTableBean> plenquiryDatatableList = new ArrayList<PLEnquiryDataTableBean>();
    private List<CurrencyMaster> currencyList = new ArrayList<CurrencyMaster>();
    private List<BankMaster> bankList = new ArrayList<BankMaster>();
    Map<BigDecimal, String> mapcurrencyList = new HashMap<BigDecimal, String>();
    Map<BigDecimal, String> mapBankList = new HashMap<BigDecimal, String>();
    
    
    
    
    
	public BigDecimal getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}
	public BigDecimal getBankId() {
		return bankId;
	}
	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}
	
	public Boolean getBooRenderDataTable() {
		return booRenderDataTable;
	}
	public void setBooRenderDataTable(Boolean booRenderDataTable) {
		this.booRenderDataTable = booRenderDataTable;
	}
	
	public List<PLEnquiryDataTableBean> getPlenquiryDatatableList() {
		return plenquiryDatatableList;
	}
	public void setPlenquiryDatatableList(
			List<PLEnquiryDataTableBean> plenquiryDatatableList) {
		this.plenquiryDatatableList = plenquiryDatatableList;
	}
	public List<CurrencyMaster> getCurrencyList() {
		return currencyList;
	}
	public void setCurrencyList(List<CurrencyMaster> currencyList) {
		this.currencyList = currencyList;
	}
	public List<BankMaster> getBankList() {
		return bankList;
	}
	public void setBankList(List<BankMaster> bankList) {
		this.bankList = bankList;
	}
    
	
	@Autowired
	IGeneralService<T> generalService;
	@Autowired
	IPlEnquiryService<T> plEnquiry;
	
	
	
	/**
	 * This method is responsible to populate State depending upon country selection
	 */
	private void populateCurrency() {
	
		List<CurrencyMaster> lstCountryCurrencyList = generalService.getCurrencyList();
		
		for (CurrencyMaster currency : lstCountryCurrencyList) {
			mapcurrencyList.put(currency.getCurrencyId(),currency.getCurrencyName());
		}
				
		setCurrencyList(lstCountryCurrencyList);
	}
	
	 public void populateBank() {
		setBankId( null);
		bankList.clear();
		List<BankMaster>    bankList=plEnquiry.getBanksList(getCurrencyId() );
		
		//List<BankMaster> lstCBankMaster = generalService.getAllBankListFromBankMaster();
		
		for (BankMaster bank : bankList) {
			mapBankList.put(bank.getBankId(),bank.getBankFullName());
		}
				
		setBankList(bankList);
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void navigatePlEnquiry(){
		
		try{
			clear();
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "plenquiry.xhtml");
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		context.redirect("../enquiry/plenquiry.xhtml");
		populateCurrency();
	
	} catch (Exception e) {
		log.info("Problem to redirect: " + e);
	}
	
	}
	
	public void clear(){
		setCurrencyId(null);
		setBankId(null);
		setBooRenderDataTable(false);
		plenquiryDatatableList.clear();
		setTotalAmt(null);
		bankList.clear();
		
	}

	public void exit() throws IOException{
		clear();

    FacesContext.getCurrentInstance().getExternalContext().redirect(session.getMenuPage());
		
	}
	
	public void view(){
		try{
		plenquiryDatatableList.clear();
		List<PlEnquiryView> viewPlEnqList = plEnquiry.viewRecord(getCurrencyId(), getBankId());
		if(viewPlEnqList.size()>0){
			BigDecimal totalBankPositionAmt=BigDecimal.ZERO;
			/*BigDecimal bankPosition = BigDecimal.ZERO;
			BigDecimal currencyLimit = BigDecimal.ZERO;*/
			NumberFormat usa = NumberFormat.getInstance(Locale.getDefault());
			for(PlEnquiryView viewEnquiry:viewPlEnqList){
				
			/*bankPosition = BigDecimal.ZERO;
		    currencyLimit = BigDecimal.ZERO;*/
			PLEnquiryDataTableBean plEnqDataTable = new PLEnquiryDataTableBean();
			plEnqDataTable.setBankId(viewEnquiry.getBankId());
			//plEnqDataTable.setBankName(mapBankList.get(viewEnquiry.getBankId()));
			//plEnqDataTable.setCurrencyId(viewEnquiry.getCurrencyId());
			//plEnqDataTable.setCurrencyName(mapcurrencyList.get(viewEnquiry.getCurrencyId()));
			plEnqDataTable.setBankCode(viewEnquiry.getBankCode());
			plEnqDataTable.setGlNo(viewEnquiry.getGlSno());
	
			if(viewEnquiry.getForeignCurrencyBal()!=null){
			plEnqDataTable.setBankPosition(usa.format(viewEnquiry.getForeignCurrencyBal()));
			totalBankPositionAmt=totalBankPositionAmt.add(viewEnquiry.getForeignCurrencyBal());
			}

			if(viewEnquiry.getMinimumBalance()!=null){
			plEnqDataTable.setCurrencyLimit(usa.format(viewEnquiry.getMinimumBalance()));
			}
			/*if(viewEnquiry.getForeignCurrencyBal()!=null){
			bankPosition = viewEnquiry.getForeignCurrencyBal();
			}
			if(viewEnquiry.getMinimumBalance()!=null){
				currencyLimit = viewEnquiry.getMinimumBalance();
			}
			
			BigDecimal exposure = bankPosition.add(currencyLimit);
			if(exposure.intValue()!=0){
			plEnqDataTable.setTotalExposure(exposure);
			}*/

			if(viewEnquiry.getLocalCurrencyBal()!=null){
			plEnqDataTable.setAmountLocal(usa.format(viewEnquiry.getLocalCurrencyBal()));
			}
			
			if( viewEnquiry.getHvTrx()!=null){
			plEnqDataTable.setHvTrx(  usa.format(viewEnquiry.getHvTrx()));
			}
			if(viewEnquiry.getEftBalance()!=null){
			plEnqDataTable.setEftBalance(usa.format(viewEnquiry.getEftBalance()));
			}
			if(viewEnquiry.getTtBalance()!=null){
			plEnqDataTable.setTtBalance(usa.format(viewEnquiry.getTtBalance()));
			}
			if(viewEnquiry.getProjectionBalance()!=null){
			plEnqDataTable.setFundBalance(usa.format(viewEnquiry.getProjectionBalance()));
			}
			
			plenquiryDatatableList.add(plEnqDataTable);
			
		
			}
		 
			setTotalAmt(usa.format(totalBankPositionAmt) );
			 
			 
			
			setBooRenderDataTable(true);
		}else{
			setBooRenderDataTable(false);
			 RequestContext.getCurrentInstance().execute("norecord.show();");
		}
		}catch(NullPointerException  e){ 
			setErrorMsg("medicalInsuranceInquiry :");
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
	public String getTotalAmt() {
		return totalAmt;
	}
	public void setTotalAmt(String totalAmt) {
		this.totalAmt = totalAmt;
	}

}
