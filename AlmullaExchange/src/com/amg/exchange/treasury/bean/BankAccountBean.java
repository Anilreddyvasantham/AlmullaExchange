package com.amg.exchange.treasury.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.treasury.model.BankAccount;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.service.IBankAccountService;
import com.amg.exchange.treasury.service.IBankApprovalService;
import com.amg.exchange.treasury.service.IFundEstimationService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.SessionStateManage;

@Component("bankaccount")
@Scope("session")
public class BankAccountBean<T> implements Serializable {

	/**  
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BigDecimal bankAccountId = null; // PK
	private BigDecimal bankId = null;
	private BigDecimal branchId = null;
	private BigDecimal currencyId = null;
	private String debitAccount = null;
	private BigDecimal countryId = null;

	public BigDecimal getBankAccountId() {
		return bankAccountId;
	}

	public void setBankAccountId(BigDecimal bankAccountId) {
		this.bankAccountId = bankAccountId;
	}

	public BigDecimal getBankId() {
		return bankId;
	}

	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}

	public BigDecimal getBranchId() {
		return branchId;
	}

	public void setBranchId(BigDecimal branchId) {
		this.branchId = branchId;
	}

	public BigDecimal getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	public String getDebitAccount() {
		return debitAccount;
	}

	public void setDebitAccount(String debitAccount) {
		this.debitAccount = debitAccount;
	}

	public BigDecimal getCountryId() {
		return countryId;
	}

	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}

	@Autowired
	IGeneralService<T> generalService;

	@Autowired
	IBankAccountService<T> ibankAccountService;
	
	@Autowired
	IFundEstimationService<T> fundEstimationService;

	public IGeneralService<T> getGeneralService() {
		return generalService;
	}

	public void setGeneralService(IGeneralService<T> generalService) {
		this.generalService = generalService;
	}

	public IBankAccountService<T> getIbankAccountService() {
		return ibankAccountService;
	}

	public void setIbankAccountService(
			IBankAccountService<T> ibankAccountService) {
		this.ibankAccountService = ibankAccountService;
	}

	private List<BankBranch> branchList;

	public List<BankBranch> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<BankBranch> branchList) {
		this.branchList = branchList;
	}

	
	private List<BankMaster> bankMasterList;
	
	private List<BankBranch> bankBranchList;
	
	
	public List<BankMaster> getBankMasterList() {
		return bankMasterList;
	}

	public void setBankMasterList(List<BankMaster> bankMasterList) {
		this.bankMasterList = bankMasterList;
	}

	
	public List<BankBranch> getBankBranchList() {
		return bankBranchList;
	}

	public void setBankBranchList(List<BankBranch> bankBranchList) {
		this.bankBranchList = bankBranchList;
	}


	/**
	 * Responsible to populate Country
	 * 
	 * @return
	 */
	SessionStateManage sessionStateManage = new SessionStateManage();
	public List<BankCountryPopulationBean> getCountryNameList() {
		/*List<CountryMasterDesc> lstCountry = getGeneralService()
				.getCountryList(
						new BigDecimal(sessionStateManage
								.isExists("languageId") ? sessionStateManage
								.getSessionValue("languageId") : "" + 1));*/
		/*
		*//**
		 * Responsible to populate Bank Country from Bank Master
		 * 
		 * @return
		 *//*
		public List<BankCountryPopulationBean> getBankCountryList() {*/

			return generalService.getAllBankContry();
		/*}
		return lstCountry;*/
	}

	/**
	 * method is responsible to populate List of Bank from DB
	 * 
	 * @return
	 */

	public List<BankMaster> getBankListFromDB() {
	

		/*return getGeneralService()
				.getAllBankList(
						new BigDecimal(
								sessionStateManage.isExists("languageId") ? sessionStateManage
										.getSessionValue("languageId") : "1"),
						getCountryId());
						
*/
		
		return generalService.getBankAccordingToBankCountry(sessionStateManage.getCountryId(), getCountryId());
	}

	/**
	 * method is responsible to populate List of Bank
	 * 
	 * @return
	 */

	public void popBank(AjaxBehaviorEvent e) {
		bankMasterList = new ArrayList<BankMaster>();
		//SessionStateManage sessionStateManage = new SessionStateManage();
		
		bankMasterList.addAll(getGeneralService().getAllBankList(new BigDecimal(sessionStateManage.isExists("languageId")?sessionStateManage.getSessionValue("languageId"):"1"),getCountryId()));
	}
	
	
	public void popBankBranch(AjaxBehaviorEvent e) {
		System.out.println("..................");
		bankBranchList = new ArrayList<BankBranch>();
		bankBranchList.addAll(getGeneralService().getBankBranchList(getCountryId(),getBankId()));
		
	}
	
	
	

	/**
	 * Responsible to save Bank Account details
	 * 
	 * @return
	 */
	public void save() {

		BankAccount bankaccount = new BankAccount();

	
		CountryMaster countryMaster =  new CountryMaster();
		countryMaster.setCountryId(getCountryId());
		bankaccount.setFsCountryMaster(countryMaster);
		
		CompanyMaster companymaster = new CompanyMaster();
		companymaster.setCompanyId(new BigDecimal(1));
		bankaccount.setFsCompanyMaster(companymaster);

		BankMaster bankmaster = new BankMaster();
		bankmaster.setBankId(getBankId());
		bankaccount.setExBankMaster(bankmaster);

		/*BankBranch bankbranch = new BankBranch();
		bankbranch.setBankBranchId(getBranchId());
		bankaccount.setExBankBranch(bankbranch);*/

		bankaccount.setBankAccountId(getBankAccountId());
		bankaccount.setCurrencyId(getCurrencyId());
		bankaccount.setDebitAcct(getDebitAccount());
	

		if (getBankAccountId() != null && getBankAccountId().intValue() != 0) {

			bankaccount.setModifier(sessionStateManage.getUserName());
			bankaccount.setUpdateDate(new Date());
			bankaccount.setRecordStatus(Constants.U);

		} else {

			bankaccount.setCreator(sessionStateManage.getUserName());
			bankaccount.setCreateDate(new Date());
			bankaccount.setRecordStatus("A");
		}

		getIbankAccountService().saveBankAccount(bankaccount);

		RequestContext.getCurrentInstance().execute("complete.show();");

		//return "success";
	}

	/**
	 * for Clear the field
	 * 
	 */
	public void reset() {
		setCountryId(null);
		setBankId(null);
		setBranchId(null);
		setCurrencyId(null);
		setDebitAccount("");
	}
	
	public void cancel() {
		try{
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext(); 
			context.redirect("../login/login.xhtml");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	

	/**
	 * Pop currency List
	 * 
	 */
	
	private List<BankMaster> currencyList = new ArrayList<BankMaster>();
	
	public List<BankMaster> getCurrencyList() {
		return currencyList;
	}

	public void popCurrency(AjaxBehaviorEvent e){
		currencyList = new ArrayList<BankMaster>();
		currencyList.addAll(getIbankAccountService().getCurrencyOfBank(getCountryId(), getBankId()));
		
	}
	
	/**
	 * For Exit Button
	 */
	public void exit() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void clearCache() throws IOException {
		
		reset();
		setBooDuplicateAcc(false);
		FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/bankaccount.xhtml");
		getCurrencyList1();
	}
	
	private Boolean booDuplicateAcc=false;
	
	public Boolean getBooDuplicateAcc() {
		return booDuplicateAcc;
	}

	public void setBooDuplicateAcc(Boolean booDuplicateAcc) {
		this.booDuplicateAcc = booDuplicateAcc;
	}

	public void dulicateCheckAccountNo(){
		
		List<BankAccount> matchAccountNo= new ArrayList<BankAccount>();
		//matchAccountNo.addAll(getIbankAccountService().getDuplicateAccountNo(getBankId(), getBranchId(), getDebitAccount()));
		System.out.println("==============Size============="+matchAccountNo.size());
		if(matchAccountNo.size()>0){
			setBooDuplicateAcc(true);
			
		}else{
			save();
			setBooDuplicateAcc(false);
		}
	}
	
	List<CurrencyMaster> lstCurrencyMaster = new ArrayList<CurrencyMaster>();
	
	
	public List<CurrencyMaster> getLstCurrencyMaster() {
		return lstCurrencyMaster;
	}

	public void setLstCurrencyMaster(List<CurrencyMaster> lstCurrencyMaster) {
		this.lstCurrencyMaster = lstCurrencyMaster;
	}

	public void getCurrencyList1()
	{
		List<CurrencyMaster> lstDbCurrencyMaster=generalService.getCurrencyList();
		setLstCurrencyMaster(lstDbCurrencyMaster);
	}
	
	
	

}
