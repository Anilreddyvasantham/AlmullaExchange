package com.amg.exchange.treasury.bean;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.foreigncurrency.service.ForeignLocalCurrencyDenominationService;
import com.amg.exchange.treasury.model.AccountBalance;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.service.IFxDealwithSupplierService;
import com.amg.exchange.treasury.service.ILocalBankBalanceService;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

/**
 * @author Mohan last modified on 27-02-2015
 * 
 * @param <T>
 */
@Component("localBankBalanceBean")
@Scope("session")
public class LocalBankBalanceBean<T> {
	
	private static final Logger LOGGER = Logger.getLogger(LocalBankBalanceBean.class);

	private List<LocalBankBalanceDatatable> lstDataLocalBankBalance = new ArrayList<LocalBankBalanceDatatable>();

	/** NAG CODE START 30/01/2015 **/
	public List<LocalBankBalanceDatatable> getLstDataLocalBankBalance() {
		return lstDataLocalBankBalance;
	}

	public void setLstDataLocalBankBalance(List<LocalBankBalanceDatatable> lstDataLocalBankBalance) {
		this.lstDataLocalBankBalance = lstDataLocalBankBalance;
	}

	/** NAG END 30/01/2015 **/
	@Autowired
	ILocalBankBalanceService iLocalBankBalanceService;

	@Autowired
	ForeignLocalCurrencyDenominationService<T> foreignLocalCurrencyDenominationService;

	@Autowired
	IGeneralService<T> iGeneralService;

	@Autowired
	IFxDealwithSupplierService<T> iservice;

	SessionStateManage session = new SessionStateManage();

	public void lstDataLocalBankBalanceDetails() {
		LOGGER.info("Entering into lstDataLocalBankBalanceDetails method");
		LocalBankBalanceDatatable localBankBalanceDatatable = null;
		try {
			lstDataLocalBankBalance.clear();
			List<BankApplicability> list = iGeneralService.getLocalBankList(session.getCountryId());
			BigDecimal currencyId = iservice.getLocalCurrencyId(session.getCountryId());
			LOGGER.info("Country Id" + session.getCountryId());
			for (BankApplicability bankapp : list) {
				localBankBalanceDatatable = new LocalBankBalanceDatatable();
				localBankBalanceDatatable.setUsdValue(BigDecimal.ZERO);
				localBankBalanceDatatable.setBankName(bankapp.getBankMaster().getBankFullName());
				localBankBalanceDatatable.setBankCode(bankapp.getBankMaster().getBankCode());
				
				List<BankAccountDetails> bankAccountDeatList = iservice.getAccountDetailslist(bankapp.getBankMaster().getBankId());
				BigDecimal usd =BigDecimal.ZERO;
				BigDecimal localBalance = BigDecimal.ZERO;
				if(bankAccountDeatList.size()>0){
					BigDecimal ikonRate = iservice.getIkonRateBasedOnCurrency(currencyId);
				for(BankAccountDetails bankAcrDel:bankAccountDeatList){
					List<AccountBalance> lbalance = iservice.getBankBalance(bankAcrDel.getFundGlno());
					for (AccountBalance localbalance : lbalance) {
						localBalance = localBalance.add(localbalance.getLocalBalance());
					}
				}
				if(ikonRate!=null){
				usd = ikonRate.multiply(localBalance);
				}
				localBankBalanceDatatable.setLocalCurrency(localBalance);
				localBankBalanceDatatable.setUsdValue(round(usd, foreignLocalCurrencyDenominationService.getDecimalPerCurrency(currencyId)));
				}
				lstDataLocalBankBalance.add(localBankBalanceDatatable);
			}
		} catch(NullPointerException ne){
			  LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			  setErrorMessage("MethodName::saveDataTableRecods");
			  RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			  return; 
			  }catch(Exception exception){
			  LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			  setErrorMessage(exception.getMessage()); 
			  RequestContext.getCurrentInstance().execute("error.show();");
			  return;       
			  }
		LOGGER.info("Exit into lstDataLocalBankBalanceDetails method");
	}

	public static BigDecimal round(BigDecimal bd, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		LOGGER.info("!!BigDecimal bd!!! " + bd);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd;
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	/** NAG CODE START 30/01/2015 **/
	public void populateValues() {
		lstDataLocalBankBalanceDetails();

		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "localbankbalance.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/localbankbalance.xhtml");
		} catch(Exception exception){
			  LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			  setErrorMessage(exception.getMessage()); 
			  RequestContext.getCurrentInstance().execute("error.show();");
			  return;       
			  }
	}

	/** NAG CODE END 30/01/2015 **/

	public void exit() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
	}

	
	  private String errorMessage;

	  public String getErrorMessage() {
		    return errorMessage;
	  }

	  public void setErrorMessage(String errorMessage) {
		    this.errorMessage = errorMessage;
	  }
	
	
}
