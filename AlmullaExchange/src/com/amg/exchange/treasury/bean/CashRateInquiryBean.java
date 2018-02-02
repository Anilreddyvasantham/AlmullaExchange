package com.amg.exchange.treasury.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.service.ICountryBranchService;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.foreigncurrency.service.ForeignLocalCurrencyDenominationService;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.remittance.model.CashRate;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.service.ICashRateService;
import com.amg.exchange.treasury.service.IGLTransactionForADocument;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("cashRateInquiryBean")
@Scope("session")
public class CashRateInquiryBean<T> implements Serializable {


	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(CashRateInquiryBean.class);
	SessionStateManage session=new SessionStateManage();

	private BigDecimal applicationCountryId;
	private BigDecimal baseCurrencyId;
	private BigDecimal alternativeCurrencyId;
	private BigDecimal baseCurrencyCode;
	private BigDecimal alternativeCurrencyCode;
	private BigDecimal countryBranchId;
	//private BigDecimal saleMinRate;
	private BigDecimal saleMaxRate;
	private BigDecimal buyMinRate;
	private BigDecimal buyMaxRate;
	private BigDecimal locCode;
	private Date uploadDate;
	private String isActive;
	private String errorMessage;

	@Autowired
	ICashRateService iCashRateService;
	@Autowired
	IGeneralService<T> igeneralService;
	@Autowired
	IGLTransactionForADocument iglTransactionService;
	@Autowired
	ICountryBranchService iCountryBranchService;
	@Autowired
	ForeignLocalCurrencyDenominationService<T> foreignLocalCurrencyDenominationService;

	Map<BigDecimal, String> countryBranchList = new HashMap<BigDecimal, String>();
	List<CountryBranch> countryBranch = new ArrayList<CountryBranch>();
	private List<CashRateDataTableBean> inquiryDTList=new ArrayList<CashRateDataTableBean>();
	private List<CashRateDataTableBean> tempInquiryDTList=new ArrayList<CashRateDataTableBean>();

	
	public List<CashRateDataTableBean> getTempInquiryDTList() {
		return tempInquiryDTList;
	}
	public void setTempInquiryDTList(List<CashRateDataTableBean> tempInquiryDTList) {
		this.tempInquiryDTList = tempInquiryDTList;
	}
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}
	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}
	public BigDecimal getBaseCurrencyId() {
		return baseCurrencyId;
	}
	public void setBaseCurrencyId(BigDecimal baseCurrencyId) {
		this.baseCurrencyId = baseCurrencyId;
	}
	public BigDecimal getAlternativeCurrencyId() {
		return alternativeCurrencyId;
	}
	public void setAlternativeCurrencyId(BigDecimal alternativeCurrencyId) {
		this.alternativeCurrencyId = alternativeCurrencyId;
	}
	public BigDecimal getBaseCurrencyCode() {
		return baseCurrencyCode;
	}
	public void setBaseCurrencyCode(BigDecimal baseCurrencyCode) {
		this.baseCurrencyCode = baseCurrencyCode;
	}
	public BigDecimal getAlternativeCurrencyCode() {
		return alternativeCurrencyCode;
	}
	public void setAlternativeCurrencyCode(BigDecimal alternativeCurrencyCode) {
		this.alternativeCurrencyCode = alternativeCurrencyCode;
	}
	public BigDecimal getCountryBranchId() {
		return countryBranchId;
	}
	public void setCountryBranchId(BigDecimal countryBranchId) {
		this.countryBranchId = countryBranchId;
	}
	public BigDecimal getSaleMaxRate() {
		return saleMaxRate;
	}
	public void setSaleMaxRate(BigDecimal saleMaxRate) {
		this.saleMaxRate = saleMaxRate;
	}
	public BigDecimal getBuyMinRate() {
		return buyMinRate;
	}
	public void setBuyMinRate(BigDecimal buyMinRate) {
		this.buyMinRate = buyMinRate;
	}
	public BigDecimal getBuyMaxRate() {
		return buyMaxRate;
	}
	public void setBuyMaxRate(BigDecimal buyMaxRate) {
		this.buyMaxRate = buyMaxRate;
	}
	public BigDecimal getLocCode() {
		return locCode;
	}
	public void setLocCode(BigDecimal locCode) {
		this.locCode = locCode;
	}
	public Date getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public List<CashRateDataTableBean> getInquiryDTList() {
		return inquiryDTList;
	}
	public void setInquiryDTList(List<CashRateDataTableBean> inquiryDTList) {
		this.inquiryDTList = inquiryDTList;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public List<CountryBranch> getCountryBranch() {
		return countryBranch;
	}

	public void setCountryBranch(List<CountryBranch> countryBranch) {
		this.countryBranch = countryBranch;
	}

	public void cashRateInquiry(){
		try{
			inquiryDTList.clear();
			log.info( "Inquiry Called======================");
			if(getCountryBranchId()!=null){
				List<CashRate> inquiryList=iCashRateService.getCashRateRecordsFromDB(getCountryBranchId());
				for(CashRate cashRateObj:inquiryList){
					CashRateDataTableBean cashRateDTObj=new CashRateDataTableBean();

					cashRateDTObj.setAltenateCurrencyCode(cashRateObj.getAlternateCurrencyCode());
					cashRateDTObj.setAltenativeCurrencyDescrption(iglTransactionService.getQuoteName(cashRateObj.getAlternateCurrencyId().getCurrencyId()));
					cashRateDTObj.setBaseCurrencyCode(cashRateObj.getBaseCurrencyCode());
					cashRateDTObj.setBaseCurrencyDescription(iglTransactionService.getQuoteName(cashRateObj.getBaseCurrencyId().getCurrencyId()) );
					cashRateDTObj.setMinBuyRate(cashRateObj.getBuyRateMin());
					cashRateDTObj.setMaxBuyRate(cashRateObj.getBuyRateMax());
					cashRateDTObj.setMinSellRate(cashRateObj.getSaleMinRate());
					cashRateDTObj.setMaxSellRate(cashRateObj.getSaleMaxRate());
					cashRateDTObj.setBranchDescriprion(cashRateObj.getCountryBranchId().getBranchName());
					cashRateDTObj.setLocCode(cashRateObj.getLocCode());
					cashRateDTObj.setCountryBranchId(cashRateObj.getCountryBranchId().getCountryBranchId());
					cashRateDTObj.setPrvBuyMaxRate(cashRateObj.getPrvBuyMaxRate());
					cashRateDTObj.setPrvBuyMinRate(cashRateObj.getPrvBuyMinRate());
					cashRateDTObj.setPrvSellMaxRate(cashRateObj.getPrvSellMaxRate());
					cashRateDTObj.setPrvSellMinRate(cashRateObj.getPrvSellMinRate());

					inquiryDTList.add(cashRateDTObj);

				}
			}
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void pageNavigateToCashRateInquiry(){
		if(inquiryDTList != null || !inquiryDTList.isEmpty() ){
			inquiryDTList.clear();
		}
		setCountryBranchId(null);
		fetchLocationDetails();

		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "cashrateinquiry.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/cashrateinquiry.xhtml");
		} catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}

	}

	public void exit()  {
		if (session.getRoleId().equalsIgnoreCase("1")) {
			try {
				FacesContext.getCurrentInstance().getExternalContext()
				.redirect("../registration/employeehome.xhtml");
			} catch(Exception exception){
				log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
				setErrorMessage(exception.getMessage()); 
				RequestContext.getCurrentInstance().execute("error.show();");
				return;       
			}
		} else {
			try {
				FacesContext.getCurrentInstance().getExternalContext()
				.redirect("../registration/branchhome.xhtml");
			}catch(Exception exception){
				log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
				setErrorMessage(exception.getMessage()); 
				RequestContext.getCurrentInstance().execute("error.show();");
				return;       
			}
		}

	}

	public void clearAll(){
		if(inquiryDTList != null || !inquiryDTList.isEmpty() ){
			inquiryDTList.clear();
		}
		setCountryBranchId(null);
	}

	public void fetchLocationDetails(){
		try{
			countryBranch = igeneralService.getBranchDetails(session.getCountryId());

			for (CountryBranch countryBranch1 : countryBranch) {
				countryBranchList.put(countryBranch1.getCountryBranchId(),countryBranch1.getBranchName());
			}
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
		}
	}
	
	public void cashRateInquiryForHeaderPage(){
		try{
			inquiryDTList.clear();
			tempInquiryDTList.clear();
			setCurrencyCheck(null);
			setCurrencyQuote(null);
			log.info( "Inquiry Called======================");
			if(session.getBranchId()!=null){
				// session country id decimal
				int decimalvalue = foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(session.getCurrencyId()));
				// session country Quote name for header
				String currencyquote = igeneralService.getCurrencyQuote(new BigDecimal(session.getCurrencyId()));
				if(currencyquote != null){
					setCurrencyQuote(currencyquote);
				}
				// All currency from DB
				Map<String, String> mapCurrencyMaster = new HashMap<String, String>();
				mapCurrencyMaster.clear();
				List<CurrencyMaster> lstCurrencyDesc = igeneralService.getCurrencyList();
				if(lstCurrencyDesc != null && !lstCurrencyDesc.isEmpty()){
					for (CurrencyMaster currencyMaster : lstCurrencyDesc) {
						mapCurrencyMaster.put(currencyMaster.getCurrencyCode(), currencyMaster.getCurrencyName());
					}
				}
								
				List<CashRate> inquiryList=iCashRateService.getCashRateRecordsFromDB(new BigDecimal(session.getBranchId()));
				for(CashRate cashRateObj:inquiryList){
					CashRateDataTableBean cashRateDTObj=new CashRateDataTableBean();

					cashRateDTObj.setAltenateCurrencyCode(cashRateObj.getAlternateCurrencyCode());
					cashRateDTObj.setAltenativeCurrencyDescrption(mapCurrencyMaster.get(cashRateObj.getAlternateCurrencyCode()));
					//cashRateDTObj.setBaseCurrencyCode(cashRateObj.getBaseCurrencyCode());
					//cashRateDTObj.setBaseCurrencyDescription(iglTransactionService.getQuoteName(cashRateObj.getBaseCurrencyId().getCurrencyId()) );
					cashRateDTObj.setMaxBuyRate(cashRateObj.getBuyRateMax());
					cashRateDTObj.setMinBuyRate((new BigDecimal(1)).divide(cashRateObj.getBuyRateMax(),decimalvalue,BigDecimal.ROUND_HALF_UP)); // not min amount it is actual amount in kwd
					cashRateDTObj.setMaxSellRate(cashRateObj.getSaleMaxRate());
					cashRateDTObj.setMinSellRate((new BigDecimal(1)).divide(cashRateObj.getSaleMaxRate(),decimalvalue,BigDecimal.ROUND_HALF_UP)); // not min amount it is actual amount in kwd
					//cashRateDTObj.setBranchDescriprion(cashRateObj.getCountryBranchId().getBranchName());
					//cashRateDTObj.setLocCode(cashRateObj.getLocCode());
					//cashRateDTObj.setCountryBranchId(cashRateObj.getCountryBranchId().getCountryBranchId());
					//cashRateDTObj.setPrvBuyMaxRate(cashRateObj.getPrvBuyMaxRate());
					//cashRateDTObj.setPrvBuyMinRate(cashRateObj.getPrvBuyMinRate());
					//cashRateDTObj.setPrvSellMaxRate(cashRateObj.getPrvSellMaxRate());
					//cashRateDTObj.setPrvSellMinRate(cashRateObj.getPrvSellMinRate());

					inquiryDTList.add(cashRateDTObj);
					tempInquiryDTList.add(cashRateDTObj);

				}
			}
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}
	
	private String currencyCheck;
	private String currencyQuote;
		
	public String getCurrencyCheck() {
		return currencyCheck;
	}
	public void setCurrencyCheck(String currencyCheck) {
		this.currencyCheck = currencyCheck;
	}
	
	public String getCurrencyQuote() {
		return currencyQuote;
	}
	public void setCurrencyQuote(String currencyQuote) {
		this.currencyQuote = currencyQuote;
	}
	
	
	public void checkCurrency(){
		if(getCurrencyCheck() != null && !getCurrencyCheck().equalsIgnoreCase("")){
			List<CashRateDataTableBean> matchedCurrencylst =new ArrayList<CashRateDataTableBean>();
			for (CashRateDataTableBean matchCurrency : tempInquiryDTList) {
				if(matchCurrency.getAltenateCurrencyCode().concat(" ").concat(matchCurrency.getAltenativeCurrencyDescrption()).contains(getCurrencyCheck().toUpperCase())){
					matchedCurrencylst.add(matchCurrency);
				}
			}
			inquiryDTList.clear();
			if(matchedCurrencylst.size() != 0){
				inquiryDTList.addAll(matchedCurrencylst);
			}
		}else{
			inquiryDTList.clear();
			inquiryDTList.addAll(tempInquiryDTList);
		}
	}

}
