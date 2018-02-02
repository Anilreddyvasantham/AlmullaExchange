package com.amg.exchange.treasury.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.model.LanguageType;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.treasury.model.AccountBalance;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.BeneCountryService;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.treasury.model.TreasuryDealDetail;
import com.amg.exchange.treasury.model.TreasuryDealHeader;
import com.amg.exchange.treasury.model.TreasuryTransfer;
import com.amg.exchange.treasury.service.IBankServiceBalanceAdjustmentService;
import com.amg.exchange.treasury.service.IPipsMasterService;
import com.amg.exchange.treasury.viewModel.ViewAccountBalance;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.SessionStateManage;
@Component("bankServiceBalanceAdjustmentBean")
@Scope("session")
public class BankServiceBalanceAdjustmentBean<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger log=Logger.getLogger(BankServiceBalanceAdjustmentBean.class);
	private SessionStateManage session=new SessionStateManage();

	private BigDecimal bankId;
	private BigDecimal currencyId;
	private BigDecimal countryId;
	private BigDecimal currentBalance;
	private BigDecimal transferBalalnce;
	private BigDecimal fromTransfer;
	private BigDecimal toTransfer;
	private BigDecimal documentYear;
	private BigDecimal documentYearId;
	private String errmsg;
	private boolean booProcedureDialog;
	private String processIn=Constants.Yes;
	private BigDecimal documentIdForTreasury;


	private Boolean renderSavePanel=false;
	private Boolean renderDatatable=false;
	private Boolean renderTransferPanel=false;
	private BankServiceBalanceAdjustDatatable bankServiceBalanceAdjust;
	private BankServiceBalanceAdjustDatatable bankServiceBalanceAdjForSave;
	private AccountBalance AccountBalanceObj;



	@Autowired
	IBankServiceBalanceAdjustmentService iBankSrvcBalAdjmntService;

	@Autowired
	IGeneralService<T> generalService;

	@Autowired
	IPipsMasterService iPipsMasterService;


	private List<CountryMasterDesc> lstAllCountry = new ArrayList<CountryMasterDesc>();
	private List<BeneCountryService> lstAllCurrencyBasedonCountry = new ArrayList<BeneCountryService>();
	private Map<BigDecimal , String> lstCurrencyCodeId = new HashMap<BigDecimal , String>();
	private List<BankMaster> lstBank = new ArrayList<BankMaster>();
	private List<BankServiceBalanceAdjustDatatable> selectedList=new ArrayList<BankServiceBalanceAdjustDatatable>(); 
	private List<BankServiceBalanceAdjustDatatable> datatableList=new ArrayList<BankServiceBalanceAdjustDatatable>();
	private List<Document> lstDocument=new ArrayList<Document>();


	public List<Document> getLstDocument() {
		return lstDocument;
	}
	public void setLstDocument(List<Document> lstDocument) {
		this.lstDocument = lstDocument;
	}
	public List<BeneCountryService> getLstAllCurrencyBasedonCountry() {
		return lstAllCurrencyBasedonCountry;
	}
	public void setLstAllCurrencyBasedonCountry(
			List<BeneCountryService> lstAllCurrencyBasedonCountry) {
		this.lstAllCurrencyBasedonCountry = lstAllCurrencyBasedonCountry;
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
	public BigDecimal getCountryId() {
		return countryId;
	}
	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}
	public List<CountryMasterDesc> getLstAllCountry() {
		return lstAllCountry;
	}
	public void setLstAllCountry(List<CountryMasterDesc> lstAllCountry) {
		this.lstAllCountry = lstAllCountry;
	}
	public List<BankMaster> getLstBank() {
		return lstBank;
	}
	public void setLstBank(List<BankMaster> lstBank) {
		this.lstBank = lstBank;
	}

	public Boolean getRenderSavePanel() {
		return renderSavePanel;
	}
	public void setRenderSavePanel(Boolean renderSavePanel) {
		this.renderSavePanel = renderSavePanel;
	}
	public Boolean getRenderDatatable() {
		return renderDatatable;
	}
	public void setRenderDatatable(Boolean renderDatatable) {
		this.renderDatatable = renderDatatable;
	}
	public Boolean getRenderTransferPanel() {
		return renderTransferPanel;
	}
	public void setRenderTransferPanel(Boolean renderTransferPanel) {
		this.renderTransferPanel = renderTransferPanel;
	}

	public BigDecimal getCurrentBalance() {
		return currentBalance;
	}
	public void setCurrentBalance(BigDecimal currentBalance) {
		this.currentBalance = currentBalance;
	}
	public BigDecimal getTransferBalalnce() {
		return transferBalalnce;
	}
	public void setTransferBalalnce(BigDecimal transferBalalnce) {
		this.transferBalalnce = transferBalalnce;
	}
	public BigDecimal getFromTransfer() {
		return fromTransfer;
	}
	public void setFromTransfer(BigDecimal fromTransfer) {
		this.fromTransfer = fromTransfer;
	}
	public BigDecimal getToTransfer() {
		return toTransfer;
	}
	public void setToTransfer(BigDecimal toTransfer) {
		this.toTransfer = toTransfer;
	}	
	public List<BankServiceBalanceAdjustDatatable> getDatatableList() {
		return datatableList;
	}
	public void setDatatableList(
			List<BankServiceBalanceAdjustDatatable> datatableList) {
		this.datatableList = datatableList;
	}
	public BankServiceBalanceAdjustDatatable getBankServiceBalanceAdjust() {
		return bankServiceBalanceAdjust;
	}
	public void setBankServiceBalanceAdjust(
			BankServiceBalanceAdjustDatatable bankServiceBalanceAdjust) {
		this.bankServiceBalanceAdjust = bankServiceBalanceAdjust;
	}
	public BankServiceBalanceAdjustDatatable getBankServiceBalanceAdjForSave() {
		return bankServiceBalanceAdjForSave;
	}
	public void setBankServiceBalanceAdjForSave(BankServiceBalanceAdjustDatatable bankServiceBalanceAdjForSave) {
		this.bankServiceBalanceAdjForSave = bankServiceBalanceAdjForSave;
	}
	public BigDecimal getDocumentYear() {
		return documentYear;
	}
	public void setDocumentYear(BigDecimal documentYear) {
		this.documentYear = documentYear;
	}
	public BigDecimal getDocumentYearId() {
		return documentYearId;
	}
	public void setDocumentYearId(BigDecimal documentYearId) {
		this.documentYearId = documentYearId;
	}
	public AccountBalance getAccountBalanceObj() {
		return AccountBalanceObj;
	}
	public void setAccountBalanceObj(AccountBalance accountBalanceObj) {
		AccountBalanceObj = accountBalanceObj;
	}
	public boolean isBooProcedureDialog() {
		return booProcedureDialog;
	}
	public void setBooProcedureDialog(boolean booProcedureDialog) {
		this.booProcedureDialog = booProcedureDialog;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	public BigDecimal getDocumentIdForTreasury() {
		return documentIdForTreasury;
	}
	public void setDocumentIdForTreasury(BigDecimal documentIdForTreasury) {
		this.documentIdForTreasury = documentIdForTreasury;
	}


	public void fetchAllCountryList(){
		lstAllCountry.clear();
		List<CountryMasterDesc> lstCountry = generalService.getCountryList(new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId") : "" + 1));
		if (lstCountry.size() != 0) {
			lstAllCountry.addAll(lstCountry);
		}
	}

	public void fetchCurrency(){
		fetchCurrencyBasedOnCountry();
		popbanklist() ;
	}

	public void fetchCurrencyBasedOnCountry(){
		lstAllCurrencyBasedonCountry.clear();
		List<BeneCountryService> lstCurrency = iPipsMasterService.getCurrencyMaster(getCountryId());
		if(lstCurrency.size() != 0){
			lstAllCurrencyBasedonCountry.addAll(lstCurrency);
			for (BeneCountryService beneCountryService : lstAllCurrencyBasedonCountry) {
				lstCurrencyCodeId.put(beneCountryService.getCurrencyId().getCurrencyId(), beneCountryService.getCurrencyId().getQuoteName());
			}
		}
	}

	public void popbanklist() {
		lstBank.clear();
		log.info("Country Id:::::::::::::::"+getCountryId());
		setLstBank(generalService.getBankList(getCountryId()));
	}


	public void pageNavigationToBankServiceBalanceAdjustment(){
		try {
			clearAll();
			fetchAllCountryList();
			getFinanceYearFromdb() ;
			getDocumentIDForSave();
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("../treasury/bankservicebalanceadjustment.xhtml");
		} catch (Exception e) {
			log.error("Error in Page Navigation to  Bank Service Balance Adjustment:::::::::::"+e.getMessage());
		}
	}


	public void redirectToSameScreen(){
		try {
			fetchAllCountryList();
			getFinanceYearFromdb() ;
			getDocumentIDForSave();
			setAccountBalanceObj(null);
			setTransferBalalnce(null);
			setCurrentBalance( null);
			setFromTransfer(null);
			setToTransfer( null);
			setBankServiceBalanceAdjForSave(null);
			setBankServiceBalanceAdjust( null);
			searchButton();
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("../treasury/bankservicebalanceadjustment.xhtml");
		} catch (Exception e) {
			log.error("Error in Page Navigation to  Bank Service Balance Adjustment");
		}
	}

	public void getFinanceYearFromdb() {
		try{
			List<UserFinancialYear> applicationYearList = generalService.getDealYear(new Date());
			if(applicationYearList.size()>0){
				setDocumentYear(applicationYearList.get(0).getFinancialYear());
				setDocumentYearId(applicationYearList.get(0).getFinancialYearID());
			}
		}catch(Exception e){
			e.printStackTrace();	
		}
	}

	public  String getDocumentSerialIdNumberFromDB(String processIn) {
		String documentSerialID = generalService.getNextDocumentReferenceNumber(session.getCountryId().intValue() , session.getCompanyId().intValue(),  73 , getDocumentYear().intValue(),processIn,session.getCountryBranchCode());
		return documentSerialID;

	}

	public int getDocumentSerialId() {
		return Integer.parseInt(getDocumentSerialIdNumberFromDB(processIn));
	}

	public  void getDocumentIDForSave() {
		lstDocument=generalService.getDocument(new BigDecimal(73),new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId") : "1"));
		for(Document lstdoc:lstDocument)
		{
			setDocumentIdForTreasury(lstdoc.getDocumentID());
		}

	}



	public void searchButton(){
		log.info( ":::::::::::::::Search Button() Method Called ::::::::::::::::");
		datatableList.clear();
		selectedList.clear(); 

		List<ViewAccountBalance> bankBalList = iBankSrvcBalAdjmntService.getAllRecordsFrmAccountBalance( getCountryId(), getCurrencyId(), getBankId());
		if(bankBalList.size()>0){
			for(ViewAccountBalance vwAccBal:bankBalList){
				setRenderDatatable(true);
				setRenderTransferPanel(false);
				BankServiceBalanceAdjustDatatable datatable=new BankServiceBalanceAdjustDatatable();

				datatable.setBankName( vwAccBal.getBankName());
				datatable.setBalanceId(vwAccBal.getBalanceId().toString() );
				datatable.setAccountNumber(vwAccBal.getAccountNumber() );
				datatable.setBankFcBalance(vwAccBal.getBankFcBalance() );
				datatable.setCashUnfundAmount(vwAccBal.getCashUnfundAmount());
				datatable.setEftUnfundAmount( vwAccBal.getEftUnfundAmount());
				datatable.setTtUnfundAmount( vwAccBal.getTtUnfundAmount());
				datatable.setEftCurrenctBalance(vwAccBal.getEftCurrenctBalance());
				datatable.setTtCurrentBalance(vwAccBal.getTtCurrentBalance());
				datatable.setCashCurrentBalance( vwAccBal.getCashCurrentBalance());
				datatable.setGlslNumber(vwAccBal.getGlslNumber() );
				//datatable.setDynamicLabelForActivateDeactivate("ADJUST");
				datatable.setCurrencyId(vwAccBal.getCurrencyId() );
				datatable.setCountryId(vwAccBal.getCountryId() );
				datatable.setComanyId(vwAccBal.getCompanyId() );
				datatable.setLocalCurrencyBalance( vwAccBal.getLocalCurrencyBalance());
				datatable.setBankDetailId(vwAccBal.getBankAccDetailId() );
				datatable.setAverageRate(vwAccBal.getAverageRate() );
				datatable.setBankId(vwAccBal.getBankId());
				
				datatableList.add(datatable );
			}

		}else{
			setRenderDatatable(false);
			setRenderTransferPanel( false);
			RequestContext.getCurrentInstance().execute("norecordfound.show();");

		}

	}

	public void updateAccountBalance(){
		selectedList.clear();
		try{
			if(datatableList.size()>0){
				for(BankServiceBalanceAdjustDatatable bankServiceBalAdj:datatableList){
					if(bankServiceBalAdj.getSelectCheck()){
						setBankServiceBalanceAdjForSave(bankServiceBalAdj );
						AccountBalance accBal = new AccountBalance();

						//accBal.setAccountId(iBankSrvcBalAdjmntService.getAccountBalanceIdBasedOnGlno(bankServiceBalAdj.getGlslNumber()));
						log.info( "AccountId::::::::::::::::"+iBankSrvcBalAdjmntService.getAccountBalanceIdBasedOnGlno(bankServiceBalAdj.getGlslNumber()));
						if(getFromTransfer().intValue()==1){
							accBal.setEftCurrentBalance( bankServiceBalAdj.getEftCurrenctBalance().subtract(getTransferBalalnce()) );
							if(getToTransfer().intValue()==2){
								accBal.setTtCurrentBalance( bankServiceBalAdj.getTtCurrentBalance().add( getTransferBalalnce()));
								accBal.setCashCurrencyBalance( bankServiceBalAdj.getCashCurrentBalance());
							}else{
								accBal.setCashCurrencyBalance( bankServiceBalAdj.getTtCurrentBalance().add(getTransferBalalnce()));
								accBal.setTtCurrentBalance( bankServiceBalAdj.getTtCurrentBalance());
							}
						}
						else if(getFromTransfer().intValue()==2){
							accBal.setTtCurrentBalance( bankServiceBalAdj.getTtCurrentBalance().subtract(getTransferBalalnce()) );
							if(getToTransfer().intValue()==1){
								accBal.setEftCurrentBalance( bankServiceBalAdj.getEftCurrenctBalance().add( getTransferBalalnce()));
								accBal.setCashCurrencyBalance( bankServiceBalAdj.getCashCurrentBalance());
							}else{
								accBal.setCashCurrencyBalance( bankServiceBalAdj.getCashCurrentBalance().add(getTransferBalalnce()));
								accBal.setEftCurrentBalance(bankServiceBalAdj.getEftCurrenctBalance());
							}

						}else if(getFromTransfer().intValue()==3){
							accBal.setCashCurrencyBalance(bankServiceBalAdj.getCashCurrentBalance().subtract(getTransferBalalnce()) );
							if(getToTransfer().intValue()==1){
								accBal.setEftCurrentBalance( bankServiceBalAdj.getEftCurrenctBalance().add( getTransferBalalnce()));
								accBal.setTtCurrentBalance( bankServiceBalAdj.getTtCurrentBalance());
							}else{
								accBal.setTtCurrentBalance( bankServiceBalAdj.getTtCurrentBalance().add( getTransferBalalnce()));
								accBal.setEftCurrentBalance(bankServiceBalAdj.getEftCurrenctBalance());
							}

						}

						setAccountBalanceObj(accBal );
						iBankSrvcBalAdjmntService.update( iBankSrvcBalAdjmntService.getAccountBalanceIdBasedOnGlno(bankServiceBalAdj.getGlslNumber()),accBal.getEftCurrentBalance(),accBal.getTtCurrentBalance(),accBal.getCashCurrencyBalance());

					}
				}

			}else{

			}
		}catch(Exception e){
			log.error("Error Occured While Updation AccountBalalnce:::::::::::::"+e.getMessage());

		}

	}
	public void saveTreasuryHeader(){
		log.info("Entered into TreasuryHeader :::::::::::::::::::::::::::::::::");
		try{
			TreasuryDealHeader tresuryDealHeader =new TreasuryDealHeader();

			CountryMaster country=new CountryMaster();
			country.setCountryId(getBankServiceBalanceAdjust().getCountryId());
			tresuryDealHeader.setFsCountryMaster(country);

			CompanyMaster company=new CompanyMaster();
			company.setCompanyId( getBankServiceBalanceAdjust().getComanyId());
			tresuryDealHeader.setFsCompanyMaster(company);

			String documentSerialId=getDocumentSerialIdNumberFromDB(Constants.U);
			log.info( "documentno==========="+documentSerialId);
			log.info( "documentYear==========="+getDocumentYear());
			tresuryDealHeader.setUserFinanceYear(getDocumentYear());
			tresuryDealHeader.setTreasuryDocumentNumber( new BigDecimal(documentSerialId));
			tresuryDealHeader.setCreatedBy(session.getUserName());
			tresuryDealHeader.setCreatedDate(new Date());
			tresuryDealHeader.setIsActive( Constants.Yes);

			LanguageType language=new LanguageType();
			language.setLanguageId( session.getLanguageId());
			tresuryDealHeader.setFsLanguageType(language);

			Document document=new Document();
			log.info( "documentid========"+getDocumentIdForTreasury());
			document.setDocumentID(getDocumentIdForTreasury());
			tresuryDealHeader.setExDocument(document );


			tresuryDealHeader.setEftAmount(getBankServiceBalanceAdjust().getEftCurrenctBalance());
			tresuryDealHeader.setTtAmount(getBankServiceBalanceAdjust().getTtCurrentBalance() );
			tresuryDealHeader.setCashAmount( getBankServiceBalanceAdjust().getCashCurrentBalance());

			tresuryDealHeader.setEftToAmount(getAccountBalanceObj().getEftCurrentBalance());
			tresuryDealHeader.setTtToAmount(getAccountBalanceObj().getTtCurrentBalance() );
			tresuryDealHeader.setCashToAmount( getAccountBalanceObj().getCashCurrencyBalance()); 


			iBankSrvcBalAdjmntService.saveTreasuryHeader(tresuryDealHeader);

			saveTreasuryDetails(tresuryDealHeader,documentSerialId);
		}catch(Exception e){
			log.error("Error While saving TreasuryHeader:::::::::::::"+e.getMessage());
		}

	}

	public void saveTreasuryDetails(TreasuryDealHeader treasuryHeader,String Docno){
		log.info("Entered into TreasuryDetails:::::::::::::::::::::::::::::::::::::::::: ");
		try{
			TreasuryDealDetail treasuryDealDetails=new TreasuryDealDetail();

			CountryMaster countryMaster=new CountryMaster();
			countryMaster.setCountryId( getBankServiceBalanceAdjust().getCountryId());
			treasuryDealDetails.setTreasuryDealCountryMaster(countryMaster );

			CompanyMaster company=new CompanyMaster();
			company.setCompanyId(getBankServiceBalanceAdjust().getComanyId());
			treasuryDealDetails.setTreasuryDealCompanyMaster(company);

			treasuryDealDetails.setTreasuryDealUserFinanceYear( getDocumentYear());
			treasuryDealDetails.setDocumentNumber(new BigDecimal(Docno) );

			if(getBankServiceBalanceAdjust().getBankId()!=null){
				BankMaster bankMaster=new BankMaster();
				bankMaster.setBankId(getBankServiceBalanceAdjust().getBankId());
				treasuryDealDetails.setTreasuryDealBankMaster(bankMaster );
			}
			CurrencyMaster currency=new CurrencyMaster() ;
			currency.setCurrencyId(getBankServiceBalanceAdjust().getCurrencyId() );
			treasuryDealDetails.setTreasuryDealDetailCurrencyMaster(currency );
			if(getBankServiceBalanceAdjust().getBankDetailId()!=null){
				BankAccountDetails bankAccountDetails=new BankAccountDetails();
				bankAccountDetails.setBankAcctDetId(getBankServiceBalanceAdjust().getBankDetailId());
				treasuryDealDetails.setTreasuryDealDetailBankAccountDetails(bankAccountDetails );
			}
			treasuryDealDetails.setCreatedBy( session.getUserName());
			treasuryDealDetails.setCreatedDate( new Date());
			treasuryDealDetails.setIsActive( Constants.Yes);

			treasuryDealDetails.setTreasuryDealHeader(treasuryHeader );

			AccountBalance accountBal=new AccountBalance();
			accountBal.setAccountId(iBankSrvcBalAdjmntService.getAccountBalanceIdBasedOnGlno(getBankServiceBalanceAdjust().getGlslNumber() ) );
			treasuryDealDetails.setAccountBalance(accountBal );

			Document document=new Document();
			document.setDocumentID(getDocumentIdForTreasury());
			treasuryDealDetails.setTreasuryDealDocument(document);
			treasuryDealDetails.setAvgRate(getBankServiceBalanceAdjust().getAverageRate());

			iBankSrvcBalAdjmntService.saveTreasuryDetails(treasuryDealDetails);	

		}catch(Exception e){
			log.info( "Error Occured While Saving Treasury Deatils:::::::::::::::::"+e.getMessage());

		}

	}

	public void saveAll(){
		if(getTransferBalalnce()!=BigDecimal.ZERO){
			try{
				//updateAccountBalance();
				//saveTreasuryHeader();
				
				// inserting into log table EX_Treasury_Transfer
				saveTreasuryTransfer();
				
				setBankServiceBalanceAdjForSave(null);
				setAccountBalanceObj( null);

				RequestContext.getCurrentInstance().execute("complete.show();");
			}catch(Exception e){
				log.error("Error While Saving::::::::::"+e.getMessage());
			}
		}else{
			RequestContext.getCurrentInstance().execute("transferbalancecouldnozero.show();");
		}
	}


	public void clearAll(){
		log.info( "Clear Method called::::::::::::::::::::::::::::::::::::::::::::");
		lstBank.clear();
		lstAllCurrencyBasedonCountry.clear();
		setCountryId(null);
		setBankId(null);
		setCurrencyId(null);
		setTransferBalalnce(null);
		setCurrentBalance(null);
		setToTransfer(null);
		setFromTransfer(null);
		datatableList.clear();
		setRenderDatatable(false);
		setRenderTransferPanel(false);
		selectedList.clear();

	}



	public void fromType(){
		log.info( ":::::::::::::::: From Method called ::::::::::::::::");
		if(getToTransfer()!=null){
			log.info("::::::::::::: it will came to if  condition ::::::::::::::");
			setToTransfer(null);
			setTransferBalalnce(null);

		}else{
			log.info("::::::::::::: it will came to  else  condition ::::::::::::");
			if(getFromTransfer().intValue()==1){
				setCurrentBalance( getBankServiceBalanceAdjust().getEftCurrenctBalance());

			}else if(getFromTransfer().intValue()==2){
				setCurrentBalance( getBankServiceBalanceAdjust().getTtCurrentBalance());
			}else if(getFromTransfer().intValue()==3){
				setCurrentBalance( getBankServiceBalanceAdjust().getCashCurrentBalance());
			}
		}

	}


	public void selectBankBalance(BankServiceBalanceAdjustDatatable bankObj){
		
		if(bankObj.getBankId() != null){
			setTransferBalalnce(null);
			setCurrentBalance(null);
			setToTransfer(null);
			setFromTransfer(null);
			if(selectedList!=null && selectedList.size()>0&& datatableList.size()>0){
				for(BankServiceBalanceAdjustDatatable bankServ:datatableList){
					if(selectedList.contains(bankServ)){
						bankServ.setSelectCheck(false);
					}
				}
				selectedList.clear();
			}
			bankObj.setSelectCheck(true);
			selectedList.add(bankObj);
			setRenderTransferPanel( true);
			setBankServiceBalanceAdjust(bankObj);
		}else{
			bankObj.setSelectCheck(false);
			setErrmsg("BANK IS NOT AVAILABLE");
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}
		
	}


	public void toType(){
		log.info(" :::::::::::::::::::: To Method called  :::::::::::::::::");		
		if(getFromTransfer()!=null){
			if(getToTransfer().toString().equalsIgnoreCase(getFromTransfer().toString())){
				setToTransfer(null);
				RequestContext.getCurrentInstance().execute("different.show();");
			}else{

			}

		}
		else{

		}

	}

	public void checkBalance(){
		if(getCurrentBalance()!=null){
			if(getCurrentBalance()!=null||getCurrentBalance().intValue()==0){

				if(getCurrentBalance().compareTo(getTransferBalalnce())==1){

				}else if(getCurrentBalance().compareTo(getTransferBalalnce())>0){

				}else if(getCurrentBalance().compareTo(getTransferBalalnce())<0){
					setTransferBalalnce(null);
					RequestContext.getCurrentInstance().execute("amountexceed.show();");
				}
			}
		}


	}

	public void exit() {
		try {
			if (session.getRoleId().equalsIgnoreCase("1")) {
				FacesContext.getCurrentInstance().getExternalContext()
				.redirect("../registration/employeehome.xhtml");
			} else {
				FacesContext.getCurrentInstance().getExternalContext()
				.redirect("../registration/branchhome.xhtml");
			}
		} catch (Exception e) {
			log.error(":::::::::::::::::::Problem Ocuured in Exit Button::::::::::::::");
		}

	}
	
	public void saveTreasuryTransfer(){
		

		log.info("Entered into saveTreasuryTransfer:::::::::::::::::::::::::::::::::::::::::: ");
		try{
			
			if(datatableList.size()!=0 ){
				
				for (BankServiceBalanceAdjustDatatable selectedBankServiceBal : datatableList) {
					
					if(selectedBankServiceBal.getSelectCheck()){
						
						TreasuryTransfer treasuryTransfer = new TreasuryTransfer();
						
						//treasuryTransfer.setTreasurytransferId();
						
						treasuryTransfer.setApplicationCountryId(session.getCountryId());
						treasuryTransfer.setBankAccDetId(selectedBankServiceBal.getBankDetailId());
						treasuryTransfer.setBankCountryId(selectedBankServiceBal.getCountryId());
						treasuryTransfer.setBankId(selectedBankServiceBal.getBankId());
						treasuryTransfer.setCreatedBy(session.getUserName());
						treasuryTransfer.setCreatedDate(new Date());
						treasuryTransfer.setCurrencyId(selectedBankServiceBal.getCurrencyId());
						treasuryTransfer.setFaAccountNumber(selectedBankServiceBal.getGlslNumber());
						treasuryTransfer.setForiegnTrnxAmount(getTransferBalalnce());
						treasuryTransfer.setFromSeviceMasterId(getFromTransfer());
						treasuryTransfer.setToSeviceMasterId(getToTransfer());
						
						iBankSrvcBalAdjmntService.saveTreasuryTransfer(treasuryTransfer);	
						
					}
					
				}
				
			}
			
		}catch(AMGException e){
			log.info( "Error Occured While saveTreasuryTransfer:::::::::::::::::"+e.getMessage());
			setErrmsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}catch(Exception e){
			log.info( "Error Occured While saveTreasuryTransfer :::::::::::::::::"+e.getMessage());
			setErrmsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}

	
		
	}

















}
