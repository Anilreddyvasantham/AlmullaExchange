package com.amg.exchange.enquiry.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.enquiry.model.BankAccountDetailsView;
import com.amg.exchange.enquiry.service.IAverageRateBankORCurrencyWiseEnquiryService;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.CustomerSpecialDealRequest;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

/*
* Author RAHAMATHALI SHAIK
*/

/*
 * Here @Component ,@Scope annotation belongs to Spring Framework
 * @Component used to define your bean as managed bean .It will work as a controller for the application
 * @Scope annotation used for maintain your bean in Hole application. Different session scopes (request,view,session,application)
*/
@Component("averageRateBankORCurrencyWiseEnquiry")
@Scope("session")
public class AverageRateBankORCurrencyWiseEnquiryBean<T> implements Serializable{

	/*
	 * serialVersionUID is used to ensure that during De-serialization the same class (that was used during serialize process) is loaded.
	*/
	private static final long serialVersionUID = 1L;
	
	/*
	 * Configuring Logger class for displaying error messages at production time in a log file.
	 */
	private static final Logger log= Logger.getLogger(AverageRateBankORCurrencyWiseEnquiryBean.class);
	
	SessionStateManage session=new SessionStateManage();
	
	/*	
 	* Defining all properties for AverageRateBankORCurrencyWiseEnquiry Screen
 	*/	
	private BigDecimal bankId;
	private BigDecimal currencyId;
	private String bankName;
	private String currencyName;
	private BigDecimal totalAmount;
	private BigDecimal countryId;
	
	
	/*	
 	* For rendering Panels
 	*/	
	private Boolean renderFirstPanel;
	private Boolean renderSecondPanel;

	
	private List<BankMaster> bankMasterList = new ArrayList<BankMaster>();
	private List<CurrencyMaster> currencyMasterList = new ArrayList<CurrencyMaster>();
	private List<AverageRateBankORCurrencyWiseEnquiryDataTable>  enquiryDataTableList = new ArrayList<AverageRateBankORCurrencyWiseEnquiryDataTable>();
	private List<AverageRateBankORCurrencyWiseEnquiryDataTable>  enquiryDataTableListForSpCus = new ArrayList<AverageRateBankORCurrencyWiseEnquiryDataTable>();
	
	
	private Map<BigDecimal, String> bankMap = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> currencyMap = new HashMap<BigDecimal, String>();
	private List<CountryMasterDesc> allCountryList = new ArrayList<CountryMasterDesc>();
	
	
	/*	
 	* Here @Autowired annotation makes us to access all methods and properties of that service
 	*/	
	@Autowired
	IAverageRateBankORCurrencyWiseEnquiryService averageRateBankORCurrencyWiseEnquiryService;
	
	@Autowired
	IGeneralService<T> igeneralService;
	
	
	/*	
 	* All getters and setter methods for that properties  
 	* Here Getter method always called whenever page get loaded .Whatever data you seted in bean using setter method that value auto populated
 	* at  loading time
 	*/	
	public List<BankMaster> getBankMasterList() {
		return bankMasterList;
	}
	public void setBankMasterList(List<BankMaster> bankMasterList) {
		this.bankMasterList = bankMasterList;
	}
	public List<CurrencyMaster> getCurrencyMasterList() {
		return currencyMasterList;
	}
	public void setCurrencyMasterList(List<CurrencyMaster> currencyMasterList) {
		this.currencyMasterList = currencyMasterList;
	}
	public List<AverageRateBankORCurrencyWiseEnquiryDataTable> getEnquiryDataTableList() {
		return enquiryDataTableList;
	}
	public void setEnquiryDataTableList(
			List<AverageRateBankORCurrencyWiseEnquiryDataTable> enquiryDataTableList) {
		this.enquiryDataTableList = enquiryDataTableList;
	}
	
	
	
	public List<AverageRateBankORCurrencyWiseEnquiryDataTable> getEnquiryDataTableListForSpCus() {
		return enquiryDataTableListForSpCus;
	}
	public void setEnquiryDataTableListForSpCus(
			List<AverageRateBankORCurrencyWiseEnquiryDataTable> enquiryDataTableListForSpCus) {
		this.enquiryDataTableListForSpCus = enquiryDataTableListForSpCus;
	}
	public Boolean getRenderFirstPanel() {
		return renderFirstPanel;
	}
	public void setRenderFirstPanel(Boolean renderFirstPanel) {
		this.renderFirstPanel = renderFirstPanel;
	}
	public Boolean getRenderSecondPanel() {
		return renderSecondPanel;
	}
	public void setRenderSecondPanel(Boolean renderSecondPanel) {
		this.renderSecondPanel = renderSecondPanel;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
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
	public void clearAll(){
		setCountryId( null);
		setCurrencyId( null);
		setBankId( null);
		enquiryDataTableList.clear();
		getbankListFromBankAccountDetails();
		getCurrencyListFromBankAccountDetails();
	}




	public void enquiry(){
		
		try{
		enquiryDataTableList.clear();
		if(getBankId()!=null || getCurrencyId()!=null || getCountryId()!=null)
		{
			List<BankAccountDetailsView> bankAccountDetailViewList = averageRateBankORCurrencyWiseEnquiryService.getBankAccountDeatailsView(getBankId(), getCurrencyId(),getCountryId());
			if(bankAccountDetailViewList.size()>0){
			for(BankAccountDetailsView bankAccDObj:bankAccountDetailViewList){
				  NumberFormat usa = NumberFormat.getInstance(Locale.getDefault());
				AverageRateBankORCurrencyWiseEnquiryDataTable dataTableObject=new AverageRateBankORCurrencyWiseEnquiryDataTable();
				dataTableObject.setBankName(bankAccDObj.getBankName());
				dataTableObject.setCurrencyName(bankAccDObj.getCurrencyName());
				dataTableObject.setBankId(bankAccDObj.getBankId());
				dataTableObject.setBankCode(bankAccDObj.getBankCode());
				dataTableObject.setCurrencyId(bankAccDObj.getCurrencyId());
				dataTableObject.setForeignCurrencyBalForCommonPool(bankAccDObj.getForeignCurrencyBalance());
				if(bankAccDObj.getForeignCurrencyBalance()!=null){
				dataTableObject.setForeignCurrencyBalForCommonPoolDisplay(usa.format(new BigDecimal(bankAccDObj.getForeignCurrencyBalance())));
				}
				dataTableObject.setLocalBalanceForCommonPool(bankAccDObj.getLocalCurrencyBalance());
				if( bankAccDObj.getLocalCurrencyBalance()!=null){
				dataTableObject.setLocalBalanceForCommonPoolDisplay(usa.format(bankAccDObj.getLocalCurrencyBalance()));
				}
				dataTableObject.setAvgRate(bankAccDObj.getAverageRate());
				dataTableObject.setForeignCurrencyBalForProvision(bankAccDObj.getFcProvBalance());
				dataTableObject.setLocalBalanceForProvision(bankAccDObj.getLcProvBalance());
				dataTableObject.setSpecialDealFcAmount(bankAccDObj.getSpeacialDealFcAmount());
				dataTableObject.setTotalForeigncurrencyBalance(bankAccDObj.getTotalForeignCurrencyAmount());
				if(bankAccDObj.getTotalForeignCurrencyAmount()!=null){
				dataTableObject.setTotalForeigncurrencyBalanceDisplay(usa.format(bankAccDObj.getTotalForeignCurrencyAmount()));
				}
				dataTableObject.setCountryName(igeneralService. getCountryName(session.getLanguageId(),bankAccDObj.getCountryId()));
				enquiryDataTableList.add(dataTableObject);
			}
			}else{
				RequestContext.getCurrentInstance().execute("noDataFound.show();");
				return;
			}
			//setCountryId( null);
			//setBankId(null);
			//setCurrencyId(null);
		}else
		{
			RequestContext.getCurrentInstance().execute("optionSelect.show();");
			return;
		}
		}catch(NullPointerException ne){
		  log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
		  setErrorMessage("MethodName::enquiry");
		  RequestContext.getCurrentInstance().execute("nullPointerId.show();");
		  return; 
		  }catch(Exception exception){
		  log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		  setErrorMessage(exception.getMessage()); 
		  RequestContext.getCurrentInstance().execute("error.show();");
		  return;       
		  }
		
	}
	
	public void showSpecialDealForeignCurrencyDetails(AverageRateBankORCurrencyWiseEnquiryDataTable dtObj){
		  try{
		enquiryDataTableListForSpCus.clear();
		setRenderFirstPanel(false);
		setRenderSecondPanel(true);
		setBankName(bankMap.get(dtObj.getBankId()));
		setCurrencyName(currencyMap.get(dtObj.getCurrencyId()));
		
		List<CustomerSpecialDealRequest> getSpecialCustoemrList = averageRateBankORCurrencyWiseEnquiryService.getCustomerRecords(dtObj.getBankId(), dtObj.getCurrencyId());
		for(CustomerSpecialDealRequest specCusObj:getSpecialCustoemrList){
			  NumberFormat usa = NumberFormat.getInstance(Locale.getDefault());
			AverageRateBankORCurrencyWiseEnquiryDataTable dataTableObj =new AverageRateBankORCurrencyWiseEnquiryDataTable();
			dataTableObj.setCustomerName(specCusObj.getCustomerSpeacialDealReqCustomer().getFirstName());
			dataTableObj.setCustomerReference(specCusObj.getCustomerSpeacialDealReqCustomer().getCustomerReference());
			dataTableObj.setForeignCurrencyBalance(specCusObj.getForeignCurrencyAmount());
			if(specCusObj.getForeignCurrencyAmount()!=null){
			dataTableObj.setForeignCurrencyBalanceDisplay(usa.format(specCusObj.getForeignCurrencyAmount()));
			}
			enquiryDataTableListForSpCus.add(dataTableObj);
		}
		BigDecimal amount=BigDecimal.ZERO;
		for(AverageRateBankORCurrencyWiseEnquiryDataTable obj :enquiryDataTableListForSpCus){
			amount = amount.add(obj.getForeignCurrencyBalance()==null ? BigDecimal.ZERO : obj.getForeignCurrencyBalance());
		}
		setTotalAmount(amount);
		  }catch(NullPointerException ne){
		  log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
		  setErrorMessage("MethodName::enquiry");
		  RequestContext.getCurrentInstance().execute("nullPointerId.show();");
		  return; 
		  }catch(Exception exception){
		  log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		  setErrorMessage(exception.getMessage()); 
		  RequestContext.getCurrentInstance().execute("error.show();");
		  return;       
		  }
	}
	
	public void cancel(){
		setRenderFirstPanel(true);
		setRenderSecondPanel(false);
		setBankName(null);
		setCurrencyName(null);
		setTotalAmount(null);
		enquiryDataTableListForSpCus.clear();
		 
	}
	
	
	/*
	 * Display all Banks from EX_BANK_ACCOUNT_DETAILS Table 
	 * Here Removing Duplicate Banks from the list and maintain all Banks in a Map(bankMap) 
	*/
	public void getbankListFromBankAccountDetails(){
	 
		  try{
		currencyMasterList.clear();
		setCurrencyId(null);
		getCurrencyListFromBankAccountDetails();
		List<BankMaster> bankMasterList = new ArrayList<BankMaster>();
		bankMap.clear();
		List<BigDecimal> duplicateCheck = new ArrayList<BigDecimal>();
		List<BankAccountDetails> bankaccountDetailList=averageRateBankORCurrencyWiseEnquiryService.getallBanksFromBankAccountDetailsList(getCountryId(),getBankId());
		if(bankaccountDetailList!=null){
		for(BankAccountDetails bankAccDetail:bankaccountDetailList){
			if(!duplicateCheck.contains(bankAccDetail.getExBankMaster().getBankId())){
			bankMasterList.add(bankAccDetail.getExBankMaster());
			duplicateCheck.add(bankAccDetail.getExBankMaster().getBankId());
			bankMap.put(bankAccDetail.getExBankMaster().getBankId(), bankAccDetail.getExBankMaster().getBankFullName());
			}
		}

		setBankMasterList(bankMasterList);
		}
		  }catch(Exception exception){
		    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		    setErrorMessage(exception.getMessage()); 
		    RequestContext.getCurrentInstance().execute("error.show();");
		    return;       
		    }
	}

	/*
	 * Display all Currencies from EX_BANK_ACCOUNT_DETAILS Table 
	 * Here Removing Duplicate Currencies from the list and maintain all Currencies in a Map(currencyMap)
	*/
	public void getCurrencyListFromBankAccountDetails(){
		currencyMasterList.clear();
		setCurrencyId(null);
		try{
		List<CurrencyMaster> bankCurrencyList = new ArrayList<CurrencyMaster>();
		currencyMap.clear();
		List<BigDecimal> duplicateCheck = new ArrayList<BigDecimal>();
		List<BankAccountDetails> bankaccountDetailList=averageRateBankORCurrencyWiseEnquiryService.getallBanksFromBankAccountDetailsList(getCountryId(),getBankId());
		if(bankaccountDetailList!=null){
		for(BankAccountDetails bankAccDetail:bankaccountDetailList){
			if(!duplicateCheck.contains(bankAccDetail.getFsCurrencyMaster().getCurrencyId())){
			bankCurrencyList.add(bankAccDetail.getFsCurrencyMaster());
			duplicateCheck.add(bankAccDetail.getFsCurrencyMaster().getCurrencyId());
			currencyMap.put(bankAccDetail.getFsCurrencyMaster().getCurrencyId(), bankAccDetail.getFsCurrencyMaster().getCurrencyName());
			}
		}
		setCurrencyMasterList(bankCurrencyList);
		}
		}catch(Exception exception){
		  log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		  setErrorMessage(exception.getMessage()); 
		  RequestContext.getCurrentInstance().execute("error.show();");
		  return;       
		  }
	}

	/*
	 * It Navigate to AverageRateBankOrCurrencyWiseEnquiry Page
	 * Here Auto Populated Bank List and Currency List
	 * and set to default behavior of the screen
	*/
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void navigateToAverageRateBankOrCurrencyWiseEnquiryPage() throws Exception{
		setBankId(null);
		setCurrencyId(null);
		setRenderFirstPanel(true);
		setRenderSecondPanel(false);
		getbankListFromBankAccountDetails();
		getCurrencyListFromBankAccountDetails();
		allCountryList.clear();
		setCountryId( null);
		populateCountryList();
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "AverageRateBankORCurrencyWiseEnquiry.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../enquiry/AverageRateBankORCurrencyWiseEnquiry.xhtml");
		} catch(Exception exception){
		  log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		  setErrorMessage(exception.getMessage()); 
		  RequestContext.getCurrentInstance().execute("error.show();");
		  return;       
		  }
	}
	
	
	
	
	
	
	public void populateCountryList(){
		List<CountryMasterDesc> allCountryList1= igeneralService.getCountryList(session.getLanguageId());
		setAllCountryList(allCountryList1);
		 
	}
		
	  private String foreignCurrencyBalForCommonPoolDisplay;
	  private String localBalanceForCommonPoolDisplay;
	  private String totalForeigncurrencyBalanceDisplay;
	  private String foreignCurrencyBalanceDisplay;


	  public String getForeignCurrencyBalForCommonPoolDisplay() {
	  	  return foreignCurrencyBalForCommonPoolDisplay;
	  }
	  public void setForeignCurrencyBalForCommonPoolDisplay(String foreignCurrencyBalForCommonPoolDisplay) {
	  	  this.foreignCurrencyBalForCommonPoolDisplay = foreignCurrencyBalForCommonPoolDisplay;
	  }
	  public String getLocalBalanceForCommonPoolDisplay() {
	  	  return localBalanceForCommonPoolDisplay;
	  }
	  public void setLocalBalanceForCommonPoolDisplay(String localBalanceForCommonPoolDisplay) {
	  	  this.localBalanceForCommonPoolDisplay = localBalanceForCommonPoolDisplay;
	  }
	  public String getTotalForeigncurrencyBalanceDisplay() {
	  	  return totalForeigncurrencyBalanceDisplay;
	  }
	  public void setTotalForeigncurrencyBalanceDisplay(String totalForeigncurrencyBalanceDisplay) {
	  	  this.totalForeigncurrencyBalanceDisplay = totalForeigncurrencyBalanceDisplay;
	  }
	  public String getForeignCurrencyBalanceDisplay() {
	  	  return foreignCurrencyBalanceDisplay;
	  }
	  public void setForeignCurrencyBalanceDisplay(String foreignCurrencyBalanceDisplay) {
	  	  this.foreignCurrencyBalanceDisplay = foreignCurrencyBalanceDisplay;
	  }
	  
	  
	  
	  private String errorMessage;

	  public String getErrorMessage() {
		    return errorMessage;
	  }

	  public void setErrorMessage(String errorMessage) {
		    this.errorMessage = errorMessage;
	  }
	public List<CountryMasterDesc> getAllCountryList() {
		return allCountryList;
	}
	public void setAllCountryList(List<CountryMasterDesc> allCountryList) {
		this.allCountryList = allCountryList;
	}
	public BigDecimal getCountryId() {
		return countryId;
	}
	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}
	
	
}
