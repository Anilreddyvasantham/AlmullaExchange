package com.amg.exchange.enquiry.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.enquiry.model.ViewFundSummaryDetails;
import com.amg.exchange.enquiry.service.IReceiptEnquiryService;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.TreasuryDealHeader;
import com.amg.exchange.treasury.service.IFundEstimationService;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;
@Component("consiladateCountryWiseFundReqBean")
@Scope("session")
public class ConsiladateCountryWiseFundReqBean<T> implements Serializable {

	  /**
	   * 
	   */
	  private static final long serialVersionUID = 1L;
	  private static final Logger log=Logger.getLogger(ConsiladateCountryWiseFundReqBean.class);
	  
	  //form variables
	  private Date projectionDate;
	  private BigDecimal exchangeCountry;
	  private BigDecimal bankCountry;
	  private BigDecimal bankId;
	  private BigDecimal currencyId;
	  private BigDecimal fundReqEFT;
	  private BigDecimal fundReqTT;
	  private BigDecimal fundReqCash;
	  private BigDecimal dealsDoneEFT;
	  private BigDecimal dealsDoneTT;
	  private BigDecimal dealsDoneCash;
	  private BigDecimal diffEFT;
	  private BigDecimal diffTT;
	  private BigDecimal diffCash;
	  private String errorMessage;
	  private String exchangeCountryName;
	  private String bankCountryName;
	  private String bankName;
	  private String currencyName;
	  private Date minDate;
	  private Boolean booBank=false;
	  private Boolean booTT=false;
	  private String ttBankName;
	  private BigDecimal fcAmount;
	  private BigDecimal totalFcAmount;
	  private String totalAmountDispaly;
	  
	  
	  private Boolean booRenderDataTable=false;
	  private List<ConsiladateCountryWiseFundReqDataTable> lstConsiladateCountryWiseFundReqDT=new ArrayList<ConsiladateCountryWiseFundReqDataTable>();
	  private List<ConsiladateCountryWiseFundReqDataTable> ttBankList=new ArrayList<ConsiladateCountryWiseFundReqDataTable>();
	  private List<CountryMasterDesc> lstCountryMasterDesc= new ArrayList<CountryMasterDesc>();
	  private List<CountryMasterDesc> lstBankCountry= new ArrayList<CountryMasterDesc>();
	  private List<BankMaster> lstBankMaster= new ArrayList<BankMaster>();
	  private List<BankAccountDetails> lstCurrencyMaster= new ArrayList<BankAccountDetails>();
	  private SessionStateManage sessionStateManage=new SessionStateManage();
	  
	  @Autowired
	  IReceiptEnquiryService receiptEnquiryService;
	  
	  @Autowired
	  IGeneralService<T> generalService;
	  
	  @Autowired
	  IFundEstimationService<T> fundEstimationService;
	  
	  public Date getProjectionDate() {
	  	  return projectionDate;
	  }
	  public void setProjectionDate(Date projectionDate) {
	  	  this.projectionDate = projectionDate;
	  }
	  public BigDecimal getExchangeCountry() {
	  	  return exchangeCountry;
	  }
	  public void setExchangeCountry(BigDecimal exchangeCountry) {
	  	  this.exchangeCountry = exchangeCountry;
	  }
	  public BigDecimal getBankCountry() {
	  	  return bankCountry;
	  }
	  public void setBankCountry(BigDecimal bankCountry) {
	  	  this.bankCountry = bankCountry;
	  }
	  public BigDecimal getBankId() {
	  	  return bankId;
	  }
	  public void setBankId(BigDecimal bankId) {
	  	  this.bankId = bankId;
	  }
	  public BigDecimal getCurrencyId() {
	  	  return currencyId;
	  }
	  public void setCurrencyId(BigDecimal currencyId) {
	  	  this.currencyId = currencyId;
	  }
	  public BigDecimal getFundReqEFT() {
	  	  return fundReqEFT;
	  }
	  public void setFundReqEFT(BigDecimal fundReqEFT) {
	  	  this.fundReqEFT = fundReqEFT;
	  }
	  public BigDecimal getFundReqTT() {
	  	  return fundReqTT;
	  }
	  public void setFundReqTT(BigDecimal fundReqTT) {
	  	  this.fundReqTT = fundReqTT;
	  }
	  public BigDecimal getFundReqCash() {
	  	  return fundReqCash;
	  }
	  public void setFundReqCash(BigDecimal fundReqCash) {
	  	  this.fundReqCash = fundReqCash;
	  }
	  public BigDecimal getDealsDoneEFT() {
	  	  return dealsDoneEFT;
	  }
	  public void setDealsDoneEFT(BigDecimal dealsDoneEFT) {
	  	  this.dealsDoneEFT = dealsDoneEFT;
	  }
	  public BigDecimal getDealsDoneTT() {
	  	  return dealsDoneTT;
	  }
	  public void setDealsDoneTT(BigDecimal dealsDoneTT) {
	  	  this.dealsDoneTT = dealsDoneTT;
	  }
	  public BigDecimal getDealsDoneCash() {
	  	  return dealsDoneCash;
	  }
	  public void setDealsDoneCash(BigDecimal dealsDoneCash) {
	  	  this.dealsDoneCash = dealsDoneCash;
	  }
	  public BigDecimal getDiffEFT() {
	  	  return diffEFT;
	  }
	  public void setDiffEFT(BigDecimal diffEFT) {
	  	  this.diffEFT = diffEFT;
	  }
	  public BigDecimal getDiffTT() {
	  	  return diffTT;
	  }
	  public void setDiffTT(BigDecimal diffTT) {
	  	  this.diffTT = diffTT;
	  }
	  public BigDecimal getDiffCash() {
	  	  return diffCash;
	  }
	  public void setDiffCash(BigDecimal diffCash) {
	  	  this.diffCash = diffCash;
	  }
	  public String getErrorMessage() {
	  	  return errorMessage;
	  }
	  public void setErrorMessage(String errorMessage) {
	  	  this.errorMessage = errorMessage;
	  }
	  
	  public Boolean getBooRenderDataTable() {
	  	  return booRenderDataTable;
	  }
	  public void setBooRenderDataTable(Boolean booRenderDataTable) {
	  	  this.booRenderDataTable = booRenderDataTable;
	  }
	  public List<ConsiladateCountryWiseFundReqDataTable> getLstConsiladateCountryWiseFundReqDT() {
	  	  return lstConsiladateCountryWiseFundReqDT;
	  }
	  public void setLstConsiladateCountryWiseFundReqDT(List<ConsiladateCountryWiseFundReqDataTable> lstConsiladateCountryWiseFundReqDT) {
	  	  this.lstConsiladateCountryWiseFundReqDT = lstConsiladateCountryWiseFundReqDT;
	  }
	  
	  public String getExchangeCountryName() {
	  	  return exchangeCountryName;
	  }
	  public void setExchangeCountryName(String exchangeCountryName) {
	  	  this.exchangeCountryName = exchangeCountryName;
	  }
	  public String getBankCountryName() {
	  	  return bankCountryName;
	  }
	  public void setBankCountryName(String bankCountryName) {
	  	  this.bankCountryName = bankCountryName;
	  }
	  public String getBankName() {
	  	  return bankName;
	  }
	  public void setBankName(String bankName) {
	  	  this.bankName = bankName;
	  }
	  public String getCurrencyName() {
	  	  return currencyName;
	  }
	  public void setCurrencyName(String currencyName) {
	  	  this.currencyName = currencyName;
	  }
	  public Date getMinDate() {
	  	  return new Date();
	  }
	  public void setMinDate(Date minDate) {
	  	  this.minDate = minDate;
	  }
	  public List<CountryMasterDesc> getLstCountryMasterDesc() {
	  	  return lstCountryMasterDesc;
	  }
	  public void setLstCountryMasterDesc(List<CountryMasterDesc> lstCountryMasterDesc) {
	  	  this.lstCountryMasterDesc = lstCountryMasterDesc;
	  }
	  
	  public List<CountryMasterDesc> getLstBankCountry() {
	  	  return lstBankCountry;
	  }
	  public void setLstBankCountry(List<CountryMasterDesc> lstBankCountry) {
	  	  this.lstBankCountry = lstBankCountry;
	  }
	  public List<BankMaster> getLstBankMaster() {
	  	  return lstBankMaster;
	  }
	  public void setLstBankMaster(List<BankMaster> lstBankMaster) {
	  	  this.lstBankMaster = lstBankMaster;
	  }
	  public List<BankAccountDetails> getLstCurrencyMaster() {
	  	  return lstCurrencyMaster;
	  }
	  public void setLstCurrencyMaster(List<BankAccountDetails> lstCurrencyMaster) {
	  	  this.lstCurrencyMaster = lstCurrencyMaster;
	  }
	  public Boolean getBooBank() {
	  	  return booBank;
	  }
	  public void setBooBank(Boolean booBank) {
	  	  this.booBank = booBank;
	  }
	  public Boolean getBooTT() {
	  	  return booTT;
	  }
	  public void setBooTT(Boolean booTT) {
	  	  this.booTT = booTT;
	  }
	  public String getTtBankName() {
	  	  return ttBankName;
	  }
	  public void setTtBankName(String ttBankName) {
	  	  this.ttBankName = ttBankName;
	  }
	  public BigDecimal getFcAmount() {
	  	  return fcAmount;
	  }
	  public void setFcAmount(BigDecimal fcAmount) {
	  	  this.fcAmount = fcAmount;
	  }
	  public List<ConsiladateCountryWiseFundReqDataTable> getTtBankList() {
	  	  return ttBankList;
	  }
	  public void setTtBankList(List<ConsiladateCountryWiseFundReqDataTable> ttBankList) {
	  	  this.ttBankList = ttBankList;
	  }
	  public BigDecimal getTotalFcAmount() {
	  	  return totalFcAmount;
	  }
	  public void setTotalFcAmount(BigDecimal totalFcAmount) {
	  	  this.totalFcAmount = totalFcAmount;
	  }
	  public String getTotalAmountDispaly() {
	  	  return totalAmountDispaly;
	  }
	  public void setTotalAmountDispaly(String totalAmountDispaly) {
	  	  this.totalAmountDispaly = totalAmountDispaly;
	  }
	  @Autowired
	  LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	  public void consiladateCountryWiseFundNavigation(){
		    lstConsiladateCountryWiseFundReqDT.clear();
		    lstCountryMasterDesc.clear();
		    ttBankList.clear();
		    setBooRenderDataTable(false);
		    setBooTT(false);
		    setBooBank(false);
		    clearAll();
		    toFetchExchangeCountry();
		    try {
		    	loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "ConsiladateCountryWiseFundReq.xhtml");
				FacesContext.getCurrentInstance().getExternalContext().redirect("../common/ConsiladateCountryWiseFundReq.xhtml");
			} catch (Exception exception) {
			  log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
        		      setErrorMessage(exception.getMessage());
        		      RequestContext.getCurrentInstance().execute("error.show();");
        			return;
			}
	  }
	  
	  public void toFetchExchangeCountry(){
		    try{
		    List<CountryMasterDesc> lstCountryMaster=generalService.getBusinessCountryList(sessionStateManage.getLanguageId());
		    if(lstCountryMaster.size() !=0){
			      lstCountryMasterDesc.addAll(lstCountryMaster);
		    }
		    }catch(Exception exception){
		      log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		      setErrorMessage(exception.getMessage());
		      RequestContext.getCurrentInstance().execute("error.show();");
			return;      
		    }
	  }
	  public void toFetchBankCountry(){
		    try{
		    lstBankCountry.clear();
		    lstBankMaster.clear();
		      lstCurrencyMaster.clear();
		      setBankCountry(null);
		      setBankId(null);
		      setCurrencyId(null);
		    List<CountryMasterDesc> lstBankApplicability=receiptEnquiryService.getBankCounty(getExchangeCountry(),sessionStateManage.getLanguageId());
		    if(lstBankApplicability.size() !=0){
			      lstBankCountry.addAll(lstBankApplicability);
		    }
		    }catch(Exception exception){
			      log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			      setErrorMessage(exception.getMessage());
			      RequestContext.getCurrentInstance().execute("error.show();");
				return;      
			    }
	  }
	  public void toFetchBank(){
		    try{
		    lstBankMaster.clear();
		    lstCurrencyMaster.clear();
		    setBankId(null);
		    setCurrencyId(null);
		    List<BankMaster> lstBank=generalService.getBankList(getBankCountry());
		    if(lstBank.size() !=0){
			      lstBankMaster.addAll(lstBank);
		    }
		    }catch(Exception exception){
			      log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			      setErrorMessage(exception.getMessage());
			      RequestContext.getCurrentInstance().execute("error.show();");
				return;      
			    }
	  }
	 
	  public void toFetchCurrency(){
		    try{
		    lstCurrencyMaster.clear();
		    setCurrencyId(null);
		    List<BankAccountDetails> currencyMasterList=fundEstimationService.getCurrencyOfBank(getBankId());
		    if(currencyMasterList.size() !=0){
			      lstCurrencyMaster.addAll(currencyMasterList);
		    }
		    }catch(Exception exception){
			      log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			      setErrorMessage(exception.getMessage());
			      RequestContext.getCurrentInstance().execute("error.show();");
				return;      
			    }
	  }
	  
	  
	  public void clearAll(){
		    setProjectionDate(null);
		    setExchangeCountry(null);
		    setBankCountry(null);
		    setBankId(null);
		    setCurrencyId(null);
		    lstConsiladateCountryWiseFundReqDT.clear();
		    ttBankList.clear();
		    setBooRenderDataTable(false);
		    setBooTT(false);
		    setBooBank(false);
	  }
	  
	  public void exit() {
		    lstConsiladateCountryWiseFundReqDT.clear();
		    ttBankList.clear();
		    setBooRenderDataTable(false); 
		    setBooTT(false);
		    setBooBank(false);
		    try {
			      FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		    }catch(Exception exception){
			      log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			      setErrorMessage(exception.getMessage());
			      RequestContext.getCurrentInstance().execute("error.show();");
				return;      
	            }
	  }

	  public void consiladateFundEnquiry(){
		    
	    lstConsiladateCountryWiseFundReqDT.clear();
	    ttBankList.clear();
	    setBooRenderDataTable(false);
	    setBooTT(false);
	    setBooBank(false);
	    if(getExchangeCountry() != null ){
		  try {
	           List<ViewFundSummaryDetails> lstViewFundSummaryDetails=receiptEnquiryService.toFetchAllDetilsFromView(getExchangeCountry(),getBankCountry(),getBankId(),getCurrencyId(),getProjectionDate());
		     if(lstViewFundSummaryDetails.size() !=0){
			    for (ViewFundSummaryDetails viewFundSummaryDetails : lstViewFundSummaryDetails) {
				      NumberFormat usa = NumberFormat.getInstance(Locale.getDefault());
			         ConsiladateCountryWiseFundReqDataTable dataTable= new ConsiladateCountryWiseFundReqDataTable();
			         dataTable.setBankCountry(viewFundSummaryDetails.getBankCountryId());
			         dataTable.setBankCountryName(viewFundSummaryDetails.getBankCountryName());
			         if(viewFundSummaryDetails.getBankShortName().equalsIgnoreCase("TT")){
			         dataTable.setBankName(viewFundSummaryDetails.getBankShortName());
			         dataTable.setBankId(viewFundSummaryDetails.getBankId());
			         dataTable.setBooTT(true);
			         dataTable.setBooBank(false);
			         }else{
			         dataTable.setBankName(viewFundSummaryDetails.getBankShortName());
			         dataTable.setBankId(viewFundSummaryDetails.getBankId());
			         dataTable.setBooTT(false);
			         dataTable.setBooBank(true);
			         }
			         dataTable.setCurrencyName(viewFundSummaryDetails.getCurrencyName());
			         dataTable.setFundReqEFT(viewFundSummaryDetails.getEftFundAmount());
			         dataTable.setDealsDoneEFT(viewFundSummaryDetails.getEftDealAmount());
			         dataTable.setFundReqTT(viewFundSummaryDetails.getTtFundAmount());
			         dataTable.setDealsDoneTT(viewFundSummaryDetails.getTtDealAmount());
			         dataTable.setFundReqCash(viewFundSummaryDetails.getCashFundAmount());
			         dataTable.setDealsDoneCash(viewFundSummaryDetails.getCashDealAmount());
			         if(viewFundSummaryDetails.getEftFundAmount() != null){
			         dataTable.setFundReqEFTDisplay(usa.format(viewFundSummaryDetails.getEftFundAmount()));
			         }
			         if(viewFundSummaryDetails.getEftDealAmount() != null){
			         dataTable.setDealsDoneEFTDisplay(usa.format(viewFundSummaryDetails.getEftDealAmount()));
			         }
			         if(viewFundSummaryDetails.getTtFundAmount() != null){
			         dataTable.setFundReqTTDisplay(usa.format(viewFundSummaryDetails.getTtFundAmount()));
			         }
			         if(viewFundSummaryDetails.getTtDealAmount() != null){
			         dataTable.setDealsDoneTTDisplay(usa.format(viewFundSummaryDetails.getTtDealAmount()));
			         }
			         if(viewFundSummaryDetails.getCashFundAmount() != null){
			         dataTable.setFundReqCashDisplay(usa.format(viewFundSummaryDetails.getCashFundAmount()));
			         }
			         if(viewFundSummaryDetails.getCashDealAmount() != null){
			         dataTable.setDealsDoneCashDisplay(usa.format(viewFundSummaryDetails.getCashDealAmount()));
			         }
			         if(viewFundSummaryDetails.getEftFundAmount() != null && viewFundSummaryDetails.getEftDealAmount() != null){
			        	   if(viewFundSummaryDetails.getEftFundAmount().compareTo(new BigDecimal(0)) !=0 && viewFundSummaryDetails.getEftDealAmount().compareTo(new BigDecimal(0))!=0){
			        	   dataTable.setDiffEFT(viewFundSummaryDetails.getEftFundAmount().subtract(viewFundSummaryDetails.getEftDealAmount())); 
			        	   dataTable.setDiffEFTDisplay(usa.format(viewFundSummaryDetails.getEftFundAmount().subtract(viewFundSummaryDetails.getEftDealAmount())));
			         }
			         }
			         if(viewFundSummaryDetails.getTtFundAmount() != null && viewFundSummaryDetails.getTtDealAmount() != null){
			        	   if(viewFundSummaryDetails.getTtFundAmount().compareTo(new BigDecimal(0)) !=0 && viewFundSummaryDetails.getTtDealAmount().compareTo(new BigDecimal(0))!=0){
			        	   dataTable.setDiffTT(viewFundSummaryDetails.getTtFundAmount().subtract(viewFundSummaryDetails.getTtDealAmount())); 
			        	   dataTable.setDiffTTDisplay(usa.format(viewFundSummaryDetails.getTtFundAmount().subtract(viewFundSummaryDetails.getTtDealAmount())));
			         }
			         }
			         if(viewFundSummaryDetails.getCashFundAmount() != null && viewFundSummaryDetails.getCashDealAmount() != null){
			        	   if(viewFundSummaryDetails.getCashFundAmount().compareTo(new BigDecimal(0)) !=0 && viewFundSummaryDetails.getCashDealAmount().compareTo(new BigDecimal(0))!=0){
			        	   dataTable.setDiffCash(viewFundSummaryDetails.getCashFundAmount().subtract(viewFundSummaryDetails.getCashDealAmount()));  
			        	   dataTable.setDiffCashDisplay(usa.format(viewFundSummaryDetails.getCashFundAmount().subtract(viewFundSummaryDetails.getCashDealAmount())));
			         }
			         }
			         lstConsiladateCountryWiseFundReqDT.add(dataTable);
			         setBooRenderDataTable(true);
			    }   
		     }else{
			       RequestContext.getCurrentInstance().execute("noRecord.show();");
			       setBooRenderDataTable(false);
				return;       
		     }
	    }catch(NullPointerException ne){
		      log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
		      setErrorMessage("MethodName::consiladateFundEnquiry");
		      RequestContext.getCurrentInstance().execute("nullPointerId.show();");
		      return; 
		      }
		  catch (Exception exception) {
		      log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		      setErrorMessage(exception.getMessage());
		      RequestContext.getCurrentInstance().execute("error.show();");
			return;
	    }    
	    }
	  }
	  
	  
	  public void toFetchBankTreasureDealHeader(ConsiladateCountryWiseFundReqDataTable dataTable){
		    ttBankList.clear();
		    if(dataTable.getBankId() != null ){
		try{
		   List<TreasuryDealHeader> lstTreasuryDealHeader=receiptEnquiryService.toFetchAllBankFromTT(dataTable.getBankCountry());
		   if(lstTreasuryDealHeader.size() !=0){
			     NumberFormat usa = NumberFormat.getInstance(Locale.getDefault());
			     BigDecimal totalAmount=new BigDecimal(0);
			     BigDecimal fcAmountFromTreDealDetail=null;
		     for (TreasuryDealHeader treasuryDealHeader : lstTreasuryDealHeader) {
		      ConsiladateCountryWiseFundReqDataTable conDataTable=new ConsiladateCountryWiseFundReqDataTable();
		      conDataTable.setTtBankName(treasuryDealHeader.getExBankMaster().getBankFullName());
		      conDataTable.setTrasureDealHeaderId(treasuryDealHeader.getTreasuryDealHeaderId());
		      fcAmountFromTreDealDetail=receiptEnquiryService.toFetchFcAmount(treasuryDealHeader.getTreasuryDealHeaderId(),"PD");
		      if(fcAmountFromTreDealDetail != null){
		      conDataTable.setFcAmount(fcAmountFromTreDealDetail);
		      conDataTable.setFcAmountDisplay(usa.format(fcAmountFromTreDealDetail));
		      totalAmount=totalAmount.add(fcAmountFromTreDealDetail);
		      }
		      conDataTable.setTotalFcAmount(totalAmount);
		      ttBankList.add(conDataTable);
		     }
		     RequestContext.getCurrentInstance().execute("ttPanel.show();");
		     setTotalFcAmount(totalAmount);
		     if(totalAmount != null){
		     setTotalAmountDispaly(usa.format(totalAmount));
		     }
		     log.info("TotalFcAmount for All Banks ::::::::::::::::::::::"+totalAmount);
			return;
		   }else{
			     RequestContext.getCurrentInstance().execute("ttAmount.show();");
				return;      
		   }
		   }catch (Exception exception) {
		     log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		      setErrorMessage(exception.getMessage());
		      RequestContext.getCurrentInstance().execute("error.show();");
			return;
		   }
		   
		  }
	  }
}
