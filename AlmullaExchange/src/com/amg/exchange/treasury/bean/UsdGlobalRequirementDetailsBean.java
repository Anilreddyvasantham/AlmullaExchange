package com.amg.exchange.treasury.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.foreigncurrency.service.ForeignLocalCurrencyDenominationService;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.FundEstimationDetails;
import com.amg.exchange.treasury.service.FundEstimationDetailsBeanService;
import com.amg.exchange.treasury.service.IFundEstimationService;
import com.amg.exchange.treasury.service.IUsdGlobalRequirementDetailsService;
import com.amg.exchange.util.SessionStateManage;
import com.sun.xml.rpc.processor.model.Request;

@Component("usdGlobalRequirementDetails")
@Scope("session")
public class UsdGlobalRequirementDetailsBean<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String projectiondate;
	private String applicationCountry;
	private BigDecimal applicationCountryId;
	private BigDecimal bankCountryId;
	private BigDecimal bankId;
	private BigDecimal currencyId;
	private List<UsdGlobalRequirementDetails> usdGlobalRequirementDetails =new ArrayList<UsdGlobalRequirementDetails>();
	private List<BankMaster> lstBank = new ArrayList<BankMaster>();
	private List<BankAccountDetails> currencyOfBank = new ArrayList<BankAccountDetails>();
	private List<BankCountryPopulationBean>bankCountryList = new ArrayList<BankCountryPopulationBean>(); 
	private SessionStateManage session = new SessionStateManage();
	private BigDecimal accountNumber = null;
	
	@Autowired
	IUsdGlobalRequirementDetailsService iUsdGlobalRequirementDetailsService;
	
	
	
	@Autowired
	IFundEstimationService<T> fundEstimationService;
	
	@Autowired
	FundEstimationDetailsBeanService fundEstimationDetailsBeanService; 
	
	@Autowired
	ForeignLocalCurrencyDenominationService<T> iForeignLocalCurrencyDenominationService;
	

	//Added by kani begin
	@Autowired
	IGeneralService<T> iGeneralService;
	
	//Added by kani end
	
	public String getProjectiondate() {
		return projectiondate;
	}
	public void setProjectiondate(String projectiondate) {
		this.projectiondate = projectiondate;
	}
	
	public String getApplicationCountry() {
		return applicationCountry;
	}
	public void setApplicationCountry(String applicationCountry) {
		this.applicationCountry = applicationCountry;
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
	
	public List<UsdGlobalRequirementDetails> getUsdGlobalRequirementDetails() {
		return usdGlobalRequirementDetails;
	}
	public void setUsdGlobalRequirementDetails(List<UsdGlobalRequirementDetails> usdGlobalRequirementDetails) {
		this.usdGlobalRequirementDetails = usdGlobalRequirementDetails;
	}
	
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}
	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}
	
	public BigDecimal getBankCountryId() {
		return bankCountryId;
	}
	public void setBankCountryId(BigDecimal bankCountryId) {
		this.bankCountryId = bankCountryId;
	}
	
	public List<BankCountryPopulationBean> getBankCountryList() {
		return  iUsdGlobalRequirementDetailsService.getBankContry();
	}

	public List<BankMaster> getLstBank() {
		return iUsdGlobalRequirementDetailsService.getBankAccordingToBankCountry(session.getCountryId(), getBankCountryId());
	}
	public void setLstBank(List<BankMaster> lstBank) {
		this.lstBank = lstBank;
	}
	
	public List<BankAccountDetails> getCurrencyOfBank() {
		return iUsdGlobalRequirementDetailsService.getCurrencyOfBank(getBankId());
	}
	public void setCurrencyOfBank(List<BankAccountDetails> currencyOfBank) {
		this.currencyOfBank = currencyOfBank;
	}
	
	public void clearFromBankCountryChange() {
		setLstBank(null);
		setBankId(null);
		setCurrencyOfBank(null);
		setCurrencyId(null);
		setLstAccountNumber(null);
		setAccountNumber(null);
	}
	
	public void clearFromBankChange() {
		setCurrencyOfBank(null);
		setCurrencyId(null);
		setLstAccountNumber(null);
		setAccountNumber(null);
		
	}

	public void search() {
		usdGlobalRequirementDetails.clear();
		List<FundEstimationDetails> data = iUsdGlobalRequirementDetailsService.search(getBankId(), getCurrencyId(), getBankCountryId(), getAccountNumber());
		
		if(data.size()>0){
		if(getCurrencyId()!=null && getCurrencyId().intValue()>0) {
			for (FundEstimationDetails fundEstimationDetails : data) {
				UsdGlobalRequirementDetails usdGlobalRequi=new UsdGlobalRequirementDetails();
				usdGlobalRequi.setSalesProjectionValue(fundEstimationDetails.getSalesForeignCurrencyProjection());
				usdGlobalRequi.setValueDatedTransaction(fundEstimationDetails.getFundEstimtaionId().getValueDatedTransaction());
				BigDecimal bankBal =  fundEstimationDetails.getFundEstimtaionId().getPreviousDaysCurrentBalance();
				usdGlobalRequi.setBankBalance(bankBal);
				usdGlobalRequi.setIkonRate(fundEstimationDetails.getIkonRate());
				BigDecimal amount = fundEstimationDetails.getUsdValue().add(fundEstimationDetails.getFundEstimtaionId().getuSdValueDatedTransaction());
				BigDecimal intermefound = fundEstimationDetails.getFundEstimtaionId().getUsdCurrentBalanace().subtract(amount);
				
				usdGlobalRequi.setUsdEquivalantAmount(intermefound);
				usdGlobalRequi.setBankCountry(iUsdGlobalRequirementDetailsService.getBankName(fundEstimationDetails.getBankCountryId()));
				/*usdGlobalRequi.setBankCode(fundEstimationDetails.getExBankMaster().getBankCode());
				usdGlobalRequi.setBank(fundEstimationDetails.getExBankMaster().getBankFullName());
				usdGlobalRequi.setAccountNumber(fundEstimationDetails.getExFundEstimationDetails().getBankAcctNo());*/
				
				usdGlobalRequi.setBankCode("TEST");//fundEstimationDetails.getExBankMaster().getBankCode());
				usdGlobalRequi.setBank("TEST");//fundEstimationDetails.getExBankMaster().getBankFullName());
				usdGlobalRequi.setAccountNumber("TEST");//fundEstimationDetails.getExFundEstimationDetails().getBankAcctNo());
				
				//usdGlobalRequi.setCurrency(fundEstimationDetails.getExCurrenyMaster().getCurrencyName());
				
				usdGlobalRequirementDetails.add(usdGlobalRequi);
			}
		} else {
			for (FundEstimationDetails fundEstimationDetails : data) {
				
				UsdGlobalRequirementDetails usdGlobalRequi=new UsdGlobalRequirementDetails();
				usdGlobalRequi.setSalesProjectionValue(fundEstimationDetails.getSalesForeignCurrencyProjection());
				usdGlobalRequi.setValueDatedTransaction(fundEstimationDetails.getFundEstimtaionId().getValueDatedTransaction());
				BigDecimal bankBal = fundEstimationDetails.getFundEstimtaionId().getPreviousDaysCurrentBalance();
				usdGlobalRequi.setBankBalance(bankBal);
				usdGlobalRequi.setIkonRate(fundEstimationDetails.getIkonRate());
				BigDecimal amount = fundEstimationDetails.getUsdValue().add(fundEstimationDetails.getFundEstimtaionId().getuSdValueDatedTransaction());
				BigDecimal intermefound = fundEstimationDetails.getFundEstimtaionId().getUsdCurrentBalanace().subtract(amount);
				usdGlobalRequi.setUsdEquivalantAmount(intermefound);
				usdGlobalRequi.setBankCountry(iUsdGlobalRequirementDetailsService.getBankName(fundEstimationDetails.getBankCountryId()));
				usdGlobalRequi.setBankCode("TEST");//fundEstimationDetails.getExBankMaster().getBankCode());
				usdGlobalRequi.setBank("TEST");//fundEstimationDetails.getExBankMaster().getBankFullName());
				usdGlobalRequi.setCurrency("CURR TEST");//fundEstimationDetails.getCurrencyId());
				usdGlobalRequi.setAccountNumber("TEST");//fundEstimationDetails.getExFundEstimationDetails().getBankAcctNo());
				usdGlobalRequirementDetails.add(usdGlobalRequi);
				
			}
		}
		}else{
			setCurrencyId(null);
			setAccountNumber(null);
			RequestContext.getCurrentInstance().execute("noDataFound.show();");
		}
	}
	
	public void populate() {
		setApplicationCountryId(session.getCountryId());
		//setApplicationCountry(iUsdGlobalRequirementDetailsService.getCountryName(getApplicationCountryId()));
		
		setApplicationCountry(iGeneralService.getCountryName(session.getLanguageId(),getApplicationCountryId()));
				
				
				//public String getCountryName(BigDecimal languageId,BigDecimal countryId);
				
		//public String getCountryName(BigDecimal languageId,BigDecimal countryId);
		
		setProjectiondate(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
		setBankCountryId(null);
		setBankId(null);
		setCurrencyId(null);
		usdGlobalRequirementDetails.clear();
		
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/usdglobalrequirementdetails.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
		//return "globalUsdRequirement";
	}
	
	public double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}
	
	/**
	 * For Exit Button
	 */
	public void exit() {
		try{
			//added by kani begin
			setLstAccountNumber(null);
			//added by kani end
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public BigDecimal getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(BigDecimal accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	List<BankAccountDetails> lstAccountNumber = new ArrayList<BankAccountDetails>();
	
	public List<BankAccountDetails> getLstAccountNumber() {
		return lstAccountNumber;
	}

	public void setLstAccountNumber(List<BankAccountDetails> lstAccountNumber) {
		this.lstAccountNumber = lstAccountNumber;
	}

	public void populateAccountNumber() {
		setLstAccountNumber(fundEstimationService.getAccountNumber(getBankId(), getCurrencyId()));
	}
}
