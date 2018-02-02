/**
 * 
 */
package com.amg.exchange.currency.inquiry.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.currency.inquiry.service.ICountrywiseFundRequirementHighvalueEnquiryService;
import com.amg.exchange.stoppayment.model.RemittanceTransaction;
import com.amg.exchange.treasury.bean.BankCountryPopulationBean;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.CustomerSpecialDealRequest;
import com.amg.exchange.treasury.model.TreasuryDealHeader;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

/**
 * @author Subramaniam
 * 
 */
@Component("countrywiseFundRequirementHighvalueEnquiryBean")
@Scope("session")
public class CountrywiseFundRequirementHighvalueEnquiryBean<T> implements Serializable {

	/**
	 * 
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log=Logger.getLogger(CountrywiseFundRequirementHighvalueEnquiryBean.class);

	private BigDecimal applicationCountryId;
	private Date requestDate;
	private String createdDate;
	private String option;
	private BigDecimal bankCountryId;
	private Boolean booRenderDatatablePanel ;
	
	private BigDecimal remittanceYear ;
	private BigDecimal remittanceNo;
	private BigDecimal fcAmount;

	SessionStateManage sessionStateManage = new SessionStateManage();

	List<CountrywiseFundRequirementHighvalueEnquiryDataTable> lstofFundRequirement = new ArrayList<CountrywiseFundRequirementHighvalueEnquiryDataTable>();
	
	
	List<CountrywiseFundRequirementHighvalueEnquiryDataTable> lstofFundRequirementAfter = new ArrayList<CountrywiseFundRequirementHighvalueEnquiryDataTable>();
	//List<RemittanceTransactionDataTable> lstOfRemittanceDetails = new ArrayList<RemittanceTransaction>();
	private List<CountryMasterDesc> lstofbussinesscountry = new ArrayList<CountryMasterDesc>();
	List<CountryMaster> lstofBankCountry = new ArrayList<CountryMaster>();
	private List<BankCountryPopulationBean> bankCountryList = new ArrayList<BankCountryPopulationBean>();
	private List<BankMaster> bankList = new ArrayList<BankMaster>();
	private List<CustomerSpecialDealRequest> lstOfCustomerSpecialDealRequest = new ArrayList<CustomerSpecialDealRequest>();
	private List<RemittanceTransaction> lstOfRemittanceDetails = new ArrayList<RemittanceTransaction>();
	
	//private List<CountryMasterDesc> bankCountryList = new ArrayList<CountryMasterDesc>();
	
	

	@Autowired
	ICountrywiseFundRequirementHighvalueEnquiryService iCountrywiseFundRequirementHighvalueEnquiryService;

	@Autowired
	IGeneralService<T> generalService;
	
	
	

	public List<CountrywiseFundRequirementHighvalueEnquiryDataTable> getLstofFundRequirementAfter() {
		return lstofFundRequirementAfter;
	}

	public void setLstofFundRequirementAfter(List<CountrywiseFundRequirementHighvalueEnquiryDataTable> lstofFundRequirementAfter) {
		this.lstofFundRequirementAfter = lstofFundRequirementAfter;
	}

	public List<CountryMaster> getLstofBankCountry() {
		return lstofBankCountry;
	}

	public void setLstofBankCountry(List<CountryMaster> lstofBankCountry) {
		this.lstofBankCountry = lstofBankCountry;
	}

	public IGeneralService<T> getGeneralService() {
		return generalService;
	}

	public void setGeneralService(IGeneralService<T> generalService) {
		this.generalService = generalService;
	}

	public List<CountryMasterDesc> getLstofbussinesscountry() {
		return lstofbussinesscountry;
	}

	public void setLstofbussinesscountry(List<CountryMasterDesc> lstofbussinesscountry) {
		this.lstofbussinesscountry = lstofbussinesscountry;
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

	
	public BigDecimal getRemittanceYear() {
		return remittanceYear;
	}

	public void setRemittanceYear(BigDecimal remittanceYear) {
		this.remittanceYear = remittanceYear;
	}

	public BigDecimal getRemittanceNo() {
		return remittanceNo;
	}

	public void setRemittanceNo(BigDecimal remittanceNo) {
		this.remittanceNo = remittanceNo;
	}

	public BigDecimal getFcAmount() {
		return fcAmount;
	}

	public void setFcAmount(BigDecimal fcAmount) {
		this.fcAmount = fcAmount;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public List<RemittanceTransaction> getLstOfRemittanceDetails() {
		return lstOfRemittanceDetails;
	}

	public void setLstOfRemittanceDetails(List<RemittanceTransaction> lstOfRemittanceDetails) {
		this.lstOfRemittanceDetails = lstOfRemittanceDetails;
	}

	public List<CustomerSpecialDealRequest> getLstOfCustomerSpecialDealRequest() {
		return lstOfCustomerSpecialDealRequest;
	}

	public void setLstOfCustomerSpecialDealRequest(List<CustomerSpecialDealRequest> lstOfCustomerSpecialDealRequest) {
		this.lstOfCustomerSpecialDealRequest = lstOfCustomerSpecialDealRequest;
	}

	public Boolean getBooRenderDatatablePanel() {
		return booRenderDatatablePanel;
	}

	public void setBooRenderDatatablePanel(Boolean booRenderDatatablePanel) {
		this.booRenderDatatablePanel = booRenderDatatablePanel;
	}
	
	

/*
	public List<BankCountryPopulationBean> getAllBankList() {
		// for Populating all Banks
		List<BankCountryPopulationBean> bankCountryList = generalService.getAllBankContry();
		if (bankCountryList.size() > 0) {
			setBankCountryList(bankCountryList);
		}

		return bankCountryList;
	}
	*/
	

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public ICountrywiseFundRequirementHighvalueEnquiryService getiCountrywiseFundRequirementHighvalueEnquiryService() {
		return iCountrywiseFundRequirementHighvalueEnquiryService;
	}

	public void setiCountrywiseFundRequirementHighvalueEnquiryService(ICountrywiseFundRequirementHighvalueEnquiryService iCountrywiseFundRequirementHighvalueEnquiryService) {
		this.iCountrywiseFundRequirementHighvalueEnquiryService = iCountrywiseFundRequirementHighvalueEnquiryService;
	}

	public List<CountrywiseFundRequirementHighvalueEnquiryDataTable> getLstofFundRequirement() {
		return lstofFundRequirement;
	}

	public void setLstofFundRequirement(List<CountrywiseFundRequirementHighvalueEnquiryDataTable> lstofFundRequirement) {
		this.lstofFundRequirement = lstofFundRequirement;
	}

	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}

	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}
	
	

	

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public BigDecimal getBankCountryId() {
		return bankCountryId;
	}
	public void setBankCountryId(BigDecimal bankCountryId) {
		this.bankCountryId = bankCountryId;
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void navigateToCountrywiseFundRequirementHighvaluePage() {
		clear();
		setBooRenderDatatablePanel(false);
		//SessionStateManage sessionStateManage = new SessionStateManage();
		lstofbussinesscountry.clear();
		lstofFundRequirementAfter.clear();
		
		List<BankCountryPopulationBean> bankCountryList = generalService.getAllBankContry();
		if (bankCountryList.size() > 0) {
			setBankCountryList(bankCountryList);
		}
		setLstofbussinesscountry(getGeneralService().getBusinessCountryList(new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1")));
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "countrywisefundrequirementhighvalueenquiry.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../enquiry/countrywisefundrequirementhighvalueenquiry.xhtml");
		} catch(Exception exception){
		  log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		  setErrorMessage(exception.getMessage()); 
		  RequestContext.getCurrentInstance().execute("error.show();");
		  return;       
		  }
	}
	
	
	public void pageNavigation()
	{
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../enquiry/countrywisefundrequirementhighvalueenquiry.xhtml");
		}  catch(Exception exception){
		  log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		  setErrorMessage(exception.getMessage()); 
		  RequestContext.getCurrentInstance().execute("error.show();");
		  return;       
		  }
	}
	
	
	

	
	public void viewCountrywiseFundRequirement() {
		
		
		
		if(getApplicationCountryId()==null)
		{
			RequestContext.getCurrentInstance().execute("exchange.show();");
			return;
		}
		
		BankMaster bankMaster = null;
		CurrencyMaster currencyMaster = null;
		CountryMaster countryMaster = null;
		
		lstofFundRequirement.clear();
		
		//requestDate
		
		try{
			
			
			Calendar fromCalendar = Calendar.getInstance();
			fromCalendar.setTime(getRequestDate());
			fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
			fromCalendar.set(Calendar.MINUTE, 0);
			fromCalendar.set(Calendar.SECOND, 0);
			Date fromDate = fromCalendar.getTime();
			System.out.println("fromDate : "+fromDate);
			setRequestDate(fromDate);
			
			lstOfCustomerSpecialDealRequest = getiCountrywiseFundRequirementHighvalueEnquiryService().getCustomerDealRequest(getApplicationCountryId(),getRequestDate() , getBankCountryId(),getOption());
			
			if(lstOfCustomerSpecialDealRequest!=null){
				for(CustomerSpecialDealRequest customerSpecialDealRequest : lstOfCustomerSpecialDealRequest){
				CountrywiseFundRequirementHighvalueEnquiryDataTable countrywiseFundRequirementHighvalueEnquiryDataTable = new CountrywiseFundRequirementHighvalueEnquiryDataTable();
				
				bankMaster = getiCountrywiseFundRequirementHighvalueEnquiryService().getBankDetails(customerSpecialDealRequest.getCustomerSpeacialDealReqBankMaster().getBankId());
				if(bankMaster !=null){
					
					countrywiseFundRequirementHighvalueEnquiryDataTable.setBankName(bankMaster.getBankFullName());
					
				}
				
				currencyMaster = getiCountrywiseFundRequirementHighvalueEnquiryService().getCurrencyDetails(customerSpecialDealRequest.getCustomerSpeacialDealReqCurrencyMaster().getCurrencyId());
				if(currencyMaster !=null){
					
					countrywiseFundRequirementHighvalueEnquiryDataTable.setCurrencyName(currencyMaster.getCurrencyCode());
					
				}
				
				countryMaster = getiCountrywiseFundRequirementHighvalueEnquiryService().getCountryDetails(customerSpecialDealRequest.getCustomerSpeacialDealReqCountryMaster().getCountryId());
				if(countryMaster !=null){
					
					countrywiseFundRequirementHighvalueEnquiryDataTable.setCountryName(countryMaster.getCountryAlpha2Code());
					
				}
				
				countrywiseFundRequirementHighvalueEnquiryDataTable.setForeignCurrencyAmount(customerSpecialDealRequest.getForeignCurrencyAmount());
				countrywiseFundRequirementHighvalueEnquiryDataTable.setDealApplicationNumber(customerSpecialDealRequest.getDealApplicationNumber());
				countrywiseFundRequirementHighvalueEnquiryDataTable.setDealFinanceYear(customerSpecialDealRequest.getDealFinanceYear());
				countrywiseFundRequirementHighvalueEnquiryDataTable.setSellRate(customerSpecialDealRequest.getSellRate());
				countrywiseFundRequirementHighvalueEnquiryDataTable.setBuyRate(customerSpecialDealRequest.getBuyRate());
				countrywiseFundRequirementHighvalueEnquiryDataTable.setDealId(customerSpecialDealRequest.getDealId());
				countrywiseFundRequirementHighvalueEnquiryDataTable.setDocumentNumber(customerSpecialDealRequest.getDocumentNumber());
				countrywiseFundRequirementHighvalueEnquiryDataTable.setCustomerSpeacialDealReqCustomer(customerSpecialDealRequest.getCustomerSpeacialDealReqCustomer().getCustomerId());
				countrywiseFundRequirementHighvalueEnquiryDataTable.setFundingOption(customerSpecialDealRequest.getFundingOption());
				countrywiseFundRequirementHighvalueEnquiryDataTable.setCustomerSpeacialDealReqBankMaster(customerSpecialDealRequest.getCustomerSpeacialDealReqBankMaster().getBankId());
				countrywiseFundRequirementHighvalueEnquiryDataTable.setCustomerSpeacialDealReqCountryMaster(customerSpecialDealRequest.getCustomerSpeacialDealReqCountryMaster().getCountryId());
				countrywiseFundRequirementHighvalueEnquiryDataTable.setApplicationCountryCountryMaster(customerSpecialDealRequest.getApplicationCountryCountryMaster().getCountryId());
				
				
				lstofFundRequirement.add(countrywiseFundRequirementHighvalueEnquiryDataTable);
				setBooRenderDatatablePanel(true);

				}
			}else{
				setBooRenderDatatablePanel(false);
				RequestContext.getCurrentInstance().execute("notApproved.show();");
			}
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::viewCountrywiseFundRequirement");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch(Exception exception){
		  log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		  setErrorMessage(exception.getMessage()); 
		  RequestContext.getCurrentInstance().execute("error.show();");
		  return;       
		  }
		

	}
	
	public void clear(){
		setRequestDate(null);
		setApplicationCountryId(null);
		setBankCountryId(null);
		
		//setBooRenderDatatablePanel(FAL);
		
	}
	
	public String getDealDetails(CountrywiseFundRequirementHighvalueEnquiryDataTable countrywiseFundRequirementHighvalueEnquiryDataTable) {
		TreasuryDealHeader treasuryDealHeader = null;
		try{
		treasuryDealHeader = getiCountrywiseFundRequirementHighvalueEnquiryService().getTreasuryDealDetails(countrywiseFundRequirementHighvalueEnquiryDataTable.getDealApplicationNumber(), countrywiseFundRequirementHighvalueEnquiryDataTable.getDocumentFinancialYear());
		if (treasuryDealHeader != null) {
			HttpSession session = sessionStateManage.getSession();
			session.setAttribute("dealEnquiryObject", treasuryDealHeader);
			return "dealEnquiryPage";
		} else {
			RequestContext.getCurrentInstance().execute("dataNotFund.show();");
			return null;
		}
		} catch(Exception exception){
		  log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		  setErrorMessage(exception.getMessage()); 
		  RequestContext.getCurrentInstance().execute("error.show();");
		  return null;       
		  }
	}
	
	
	public void getRemittanceDetails(CountrywiseFundRequirementHighvalueEnquiryDataTable countrywiseFundRequirementHighvalueEnquiryDataTable){
		
	//	lstofFundRequirement.clear();
		try{
		lstofFundRequirementAfter.clear();
		BigDecimal appCountryId = countrywiseFundRequirementHighvalueEnquiryDataTable.getApplicationCountryCountryMaster();
		BigDecimal routingBankId = countrywiseFundRequirementHighvalueEnquiryDataTable.getCustomerSpeacialDealReqBankMaster();
		//String banKId = "2258";
		BigDecimal countryId = countrywiseFundRequirementHighvalueEnquiryDataTable.getCustomerSpeacialDealReqCountryMaster();
		BigDecimal customerId = countrywiseFundRequirementHighvalueEnquiryDataTable.getCustomerSpeacialDealReqCustomer();
		
		
		//lstOfRemittanceDetails = getiCountrywiseFundRequirementHighvalueEnquiryService().getRemittanceDetails(appCountryId, new BigDecimal(banKId), countryId, customerId);
		lstOfRemittanceDetails = getiCountrywiseFundRequirementHighvalueEnquiryService().getRemittanceDetails(appCountryId, routingBankId, countryId, customerId);
		
		if(lstOfRemittanceDetails != null){
			
			for(RemittanceTransaction remittanceTransaction : lstOfRemittanceDetails){
				
				CountrywiseFundRequirementHighvalueEnquiryDataTable countrywiseFundRequirementHighvalueEnquiryDataTable1 = new CountrywiseFundRequirementHighvalueEnquiryDataTable();
				countrywiseFundRequirementHighvalueEnquiryDataTable1.setRemittanceNo(remittanceTransaction.getDocumentNo());
				countrywiseFundRequirementHighvalueEnquiryDataTable1.setRemittanceYear(remittanceTransaction.getDocumentFinanceYr());
				countrywiseFundRequirementHighvalueEnquiryDataTable1.setFcAmount(remittanceTransaction.getForeignTranxAmount());
				
				lstofFundRequirementAfter.add(countrywiseFundRequirementHighvalueEnquiryDataTable1);
				RequestContext.getCurrentInstance().execute("remittanceDetail.show();");
				return;
				
			}
		}else{
			RequestContext.getCurrentInstance().execute("dataNotFund.show();");
			return;
		}
		} catch(Exception exception){
		  log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		  setErrorMessage(exception.getMessage()); 
		  RequestContext.getCurrentInstance().execute("error.show();");
		  return;       
		  }
		
		
	}
	
	public String populateRemittanceEnquiry(CountrywiseFundRequirementHighvalueEnquiryDataTable countrywiseFundRequirementHighvalueEnquiryDataTable)throws Exception{

	
		System.out.println(countrywiseFundRequirementHighvalueEnquiryDataTable);
		
		HttpSession session = sessionStateManage.getSession();
		session.setAttribute("remittanceEnquiryObject", countrywiseFundRequirementHighvalueEnquiryDataTable);

		return "remittanceEnquirPage";
	}
	

	public void clickOnExit() throws IOException {
		if (sessionStateManage.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
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
