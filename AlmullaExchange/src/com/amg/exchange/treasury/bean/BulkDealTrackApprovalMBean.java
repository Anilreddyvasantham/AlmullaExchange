package com.amg.exchange.treasury.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.poi.ss.formula.functions.T;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BeneCountryService;
import com.amg.exchange.treasury.model.ViewCorrespondingBankCountry;
import com.amg.exchange.treasury.model.VwExBulkFxDeal;
import com.amg.exchange.treasury.service.IDealTrackerInfoService;
import com.amg.exchange.treasury.service.IFundEstimationService;
import com.amg.exchange.treasury.service.IPipsMasterService;
import com.amg.exchange.util.SessionStateManage;

@Component("bulkDealTrackApprovalMBean")
@Scope("session")
public class BulkDealTrackApprovalMBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private List<ViewCorrespondingBankCountry> lstAllCountry;	
	private List<CountryCurrencyPopulationBean> lstAllCountryCurrencyPopulationBean;
	private List<BankApplicability> lstAllbankBasedOnCntry;
	private List<BulkDealApprovalDataTable> lstBulkDealApprovalDataTable;
	private List<BulkDealApprovalDataTable> selectedBulkDealApprovalDataTable;
	private BigDecimal countryId;
	private BigDecimal currencyId;
	private BigDecimal bankId;
	private Boolean tableDisplay = false;
	private String warningMessage;


	private Map<BigDecimal , String> lstCurrencyCodeId = new HashMap<BigDecimal , String>();
	private Map<BigDecimal , String> lstBankCodeId = new HashMap<BigDecimal , String>();
	// Create Session
	SessionStateManage sessionStateManage = new SessionStateManage();




	public BigDecimal getCountryId() {
		return countryId;
	}

	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}

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

	public Map<BigDecimal, String> getLstCurrencyCodeId() {
		return lstCurrencyCodeId;
	}

	public void setLstCurrencyCodeId(Map<BigDecimal, String> lstCurrencyCodeId) {
		this.lstCurrencyCodeId = lstCurrencyCodeId;
	}

	public List<ViewCorrespondingBankCountry> getLstAllCountry() {
		return lstAllCountry;
	}

	public void setLstAllCountry(List<ViewCorrespondingBankCountry> lstAllCountry) {
		this.lstAllCountry = lstAllCountry;
	}

	public List<CountryCurrencyPopulationBean> getLstAllCountryCurrencyPopulationBean() {
		return lstAllCountryCurrencyPopulationBean;
	}

	public void setLstAllCountryCurrencyPopulationBean(
			List<CountryCurrencyPopulationBean> lstAllCountryCurrencyPopulationBean) {
		this.lstAllCountryCurrencyPopulationBean = lstAllCountryCurrencyPopulationBean;
	}

	public List<BankApplicability> getLstAllbankBasedOnCntry() {
		return lstAllbankBasedOnCntry;
	}

	public void setLstAllbankBasedOnCntry(
			List<BankApplicability> lstAllbankBasedOnCntry) {
		this.lstAllbankBasedOnCntry = lstAllbankBasedOnCntry;
	}
	
	
	public List<BulkDealApprovalDataTable> getLstBulkDealApprovalDataTable() {
		return lstBulkDealApprovalDataTable;
	}

	public void setLstBulkDealApprovalDataTable(
			List<BulkDealApprovalDataTable> lstBulkDealApprovalDataTable) {
		this.lstBulkDealApprovalDataTable = lstBulkDealApprovalDataTable;
	}
	

	public List<BulkDealApprovalDataTable> getSelectedBulkDealApprovalDataTable() {
		return selectedBulkDealApprovalDataTable;
	}

	public void setSelectedBulkDealApprovalDataTable(
			List<BulkDealApprovalDataTable> selectedBulkDealApprovalDataTable) {
		this.selectedBulkDealApprovalDataTable = selectedBulkDealApprovalDataTable;
	}

	public Boolean getTableDisplay() {
		return tableDisplay;
	}

	public void setTableDisplay(Boolean tableDisplay) {
		this.tableDisplay = tableDisplay;
	}

	


	public String getWarningMessage() {
		return warningMessage;
	}

	public void setWarningMessage(String warningMessage) {
		this.warningMessage = warningMessage;
	}




	@Autowired
	IDealTrackerInfoService<T> idealTrackerInfoService;
	@Autowired
	IPipsMasterService iPipsMasterService;
	@Autowired
	IFundEstimationService<T> fundEstimationService;
	@Autowired
	IGeneralService<T> generalService;

	public void pageNavigation() {
		try {
			setTableDisplay(false);
			clearAll();
			fetchAllCountryList();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/bulkDealTrackApproval.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// fetching all country
	public void fetchAllCountryList(){
		List<ViewCorrespondingBankCountry> lstViewCorrespondingBankCountry=idealTrackerInfoService.getCorrespondingbankCountryList();
		setLstAllCountry(lstViewCorrespondingBankCountry);
	}


	// fetch both Currency and Banks Based on Country Id
	public void fetchCurrencyAndBanksBasedCountryId(){
		
		try{
			setCurrencyId(null);
			setLstAllCountryCurrencyPopulationBean(null);
			//fetchCurrencyBasedOnCountry();
			fetchBankBasedOnCountry();
		}catch(Exception e)
		{
			setWarningMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("warningDailogId.show();");
		}
		
	}

	// fetch currency based on country
	public void fetchCurrencyBasedOnCountry(){

		lstCurrencyCodeId.clear();
		List<BeneCountryService> lstCurrency = iPipsMasterService.getCurrencyMaster(getCountryId());
		if(lstCurrency.size() != 0){

			for (BeneCountryService beneCountryService : lstCurrency) {
				lstCurrencyCodeId.put(beneCountryService.getCurrencyId().getCurrencyId(), beneCountryService.getCurrencyId().getQuoteName());
			}
		}
		setLstAllCountryCurrencyPopulationBean(null);
		List<CountryCurrencyPopulationBean> lstCountryCurrency = fundEstimationService.getBankCurrencyFromView(sessionStateManage.getCountryId(), getCountryId());

		setLstAllCountryCurrencyPopulationBean(lstCountryCurrency);

	}
	// fetch Bank based on country
	public void fetchBankBasedOnCountry(){
		List<BankApplicability> lstbank=generalService.getCoresBankList(getCountryId());

		if(lstbank.size() != 0){
			setLstAllbankBasedOnCntry(lstbank);
			for (BankApplicability bankMaster : lstAllbankBasedOnCntry) {
				lstBankCodeId.put(bankMaster.getBankMaster().getBankId(), bankMaster.getBankMaster().getReutersBankName());
			}
		}
	}

	public void clearAll()
	{
		setTableDisplay(false);
		setCountryId(null);
		setCurrencyId(null);
		setBankId(null);
		setLstAllbankBasedOnCntry(null);
		setLstAllCountryCurrencyPopulationBean(null);
		setLstBulkDealApprovalDataTable(null);
	}
	public void searchrecords()
	{
		try{
			setSelectedBulkDealApprovalDataTable(null);
			List<BulkDealApprovalDataTable> lstBulkDealApprovalDataTable= new ArrayList<BulkDealApprovalDataTable>();
			
			/*List<TreasuryDealDetail> lstTreasuryDealDetail=idealTrackerInfoService.getBulkFXDealUnApprovalRecords(sessionStateManage.getCompanyId(), sessionStateManage.getCountryId(), getCurrencyId(), getBankId());
			if(lstTreasuryDealDetail!=null && lstTreasuryDealDetail.size()>0)
			{
				setTableDisplay(true);
				for(TreasuryDealDetail treasuryDealDetail :lstTreasuryDealDetail)
				{
					BulkDealApprovalDataTable bulkDealApprovalDataTable=new BulkDealApprovalDataTable();
					TreasuryDealHeader treasuryDealHeader=treasuryDealDetail.getTreasuryDealHeader();
					bulkDealApprovalDataTable.setTreasuryHeaderId(treasuryDealHeader.getTreasuryDealHeaderId());
					bulkDealApprovalDataTable.setDealNo(treasuryDealHeader.getTreasuryDocumentNumber());
					bulkDealApprovalDataTable.setDealYear(treasuryDealHeader.getUserFinanceYear());
					bulkDealApprovalDataTable.setExchangeRate(treasuryDealHeader.getPurchaseExchangeRate());
					bulkDealApprovalDataTable.setPurchaseAmount(treasuryDealHeader.getTotalPurchaseFCAmt());
					bulkDealApprovalDataTable.setSaleAmount(treasuryDealHeader.getSaleAmount());
					//bulkDealApprovalDataTable.setSaleBank(saleBank);
					bulkDealApprovalDataTable.setDealBankId(treasuryDealDetail.getTreasuryDealBankMaster().getBankId());
					bulkDealApprovalDataTable.setDealBankName(treasuryDealDetail.getTreasuryDealBankMaster().getBankFullName());
					bulkDealApprovalDataTable.setDealDate(treasuryDealHeader.getValueDate());
					bulkDealApprovalDataTable.setCreatedBy(treasuryDealHeader.getCreatedBy());
					bulkDealApprovalDataTable.setCreatedDate(treasuryDealHeader.getCreatedDate());
					bulkDealApprovalDataTable.setModifiedBy(treasuryDealHeader.getModifiedBy());
					bulkDealApprovalDataTable.setModifiedDate(treasuryDealHeader.getModifiedDate());
					if(treasuryDealHeader.getReutersIndicator()== null)
					{
						bulkDealApprovalDataTable.setAutoManual("Manual");
					}else
					{
						bulkDealApprovalDataTable.setAutoManual("Reuters");
					}
					lstBulkDealApprovalDataTable.add(bulkDealApprovalDataTable);
					
				}
				setLstBulkDealApprovalDataTable(lstBulkDealApprovalDataTable);
			}else
			{
				setTableDisplay(false);
				setLstBulkDealApprovalDataTable(null);
				RequestContext.getCurrentInstance().execute("noRecord.show();");
			}*/
			
			List<VwExBulkFxDeal> lstVwExBulkFxDeal=idealTrackerInfoService.getViewBulckFxDDealApproval(sessionStateManage.getCompanyId(), sessionStateManage.getCountryId(), getCountryId(), getBankId());
			if(lstVwExBulkFxDeal!=null && lstVwExBulkFxDeal.size()>0)
			{
				setTableDisplay(true);
				for(VwExBulkFxDeal vwExBulkFxDeal :lstVwExBulkFxDeal)
				{

					BulkDealApprovalDataTable bulkDealApprovalDataTable=new BulkDealApprovalDataTable();
					bulkDealApprovalDataTable.setTreasuryHeaderId(vwExBulkFxDeal.getTreasuryDealHeaderId());
					bulkDealApprovalDataTable.setDealNo(vwExBulkFxDeal.getDocumentNumber());
					bulkDealApprovalDataTable.setDealYear(vwExBulkFxDeal.getDocFinYear());
					bulkDealApprovalDataTable.setExchangeRate(vwExBulkFxDeal.getPurchaseExchangeRate());
					bulkDealApprovalDataTable.setPurchaseAmount(vwExBulkFxDeal.getTotalPurchaseFcAmount());
					bulkDealApprovalDataTable.setSaleAmount(vwExBulkFxDeal.getSaleAmount());
					//bulkDealApprovalDataTable.setSaleBank(saleBank);
					bulkDealApprovalDataTable.setDealBankId(vwExBulkFxDeal.getDealWithBankId());
					bulkDealApprovalDataTable.setDealBankName(vwExBulkFxDeal.getBankFullName());
					bulkDealApprovalDataTable.setDealDate(vwExBulkFxDeal.getValueDate());
					bulkDealApprovalDataTable.setCreatedBy(vwExBulkFxDeal.getCreatedBy());
					bulkDealApprovalDataTable.setCreatedDate(vwExBulkFxDeal.getCreatedDate());
					bulkDealApprovalDataTable.setModifiedBy(vwExBulkFxDeal.getModifiedBy());
					bulkDealApprovalDataTable.setModifiedDate(vwExBulkFxDeal.getModifiedDate());
					bulkDealApprovalDataTable.setSelectCheck(false);
					
					bulkDealApprovalDataTable.setPdbankFullName(vwExBulkFxDeal.getPdbankFullName());
					bulkDealApprovalDataTable.setPdCurrencyName(vwExBulkFxDeal.getPdCurrencyName());
					bulkDealApprovalDataTable.setSdCurrencyName(vwExBulkFxDeal.getSdCurrencyName());
					
					if(vwExBulkFxDeal.getReutersIndicator()== null)
					{
						bulkDealApprovalDataTable.setAutoManual("Manual");
					}else
					{
						bulkDealApprovalDataTable.setAutoManual("Reuters");
					}
					lstBulkDealApprovalDataTable.add(bulkDealApprovalDataTable);
				}
				setLstBulkDealApprovalDataTable(lstBulkDealApprovalDataTable);
			}else
			{
				setTableDisplay(false);
				setLstBulkDealApprovalDataTable(null);
				RequestContext.getCurrentInstance().execute("noRecord.show();");
			}
		}catch(Exception e)
		{
			setWarningMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("warningDailogId.show();");
		}
		
	}
	
	public void dealRecordSelectBox(BulkDealApprovalDataTable bulkDealApprovalDataTable)
	{
		try{
			if(bulkDealApprovalDataTable!=null && bulkDealApprovalDataTable.getSelectCheck())
			{
				bulkDealApprovalDataTable.setSelectCheck(true);
				List<BulkDealApprovalDataTable> aselectedBulkDealApprovalDataTable= new ArrayList<BulkDealApprovalDataTable>();
				List<BulkDealApprovalDataTable> lstelectedBulkDealApprovalDataTable= getSelectedBulkDealApprovalDataTable();
				aselectedBulkDealApprovalDataTable.add(bulkDealApprovalDataTable);
				if(lstelectedBulkDealApprovalDataTable!=null && lstelectedBulkDealApprovalDataTable.size()>0)
				{
					aselectedBulkDealApprovalDataTable.addAll(lstelectedBulkDealApprovalDataTable);
				}
				setSelectedBulkDealApprovalDataTable(aselectedBulkDealApprovalDataTable);
				
				/*boolean checkStatus=getSelectRecordFrom(bulkDealApprovalDataTable);
				if(checkStatus)
				{
					bulkDealApprovalDataTable.setSelectCheck(true);
					List<BulkDealApprovalDataTable> aselectedBulkDealApprovalDataTable= new ArrayList<BulkDealApprovalDataTable>();
					List<BulkDealApprovalDataTable> lstelectedBulkDealApprovalDataTable= getSelectedBulkDealApprovalDataTable();
					aselectedBulkDealApprovalDataTable.add(bulkDealApprovalDataTable);
					if(lstelectedBulkDealApprovalDataTable!=null && lstelectedBulkDealApprovalDataTable.size()>0)
					{
						aselectedBulkDealApprovalDataTable.addAll(lstelectedBulkDealApprovalDataTable);
					}
					setSelectedBulkDealApprovalDataTable(aselectedBulkDealApprovalDataTable);
					
				}else
				{
					bulkDealApprovalDataTable.setSelectCheck(false);
					RequestContext.getCurrentInstance().execute("notApproved.show();");
				}*/
			}else
			{
				List<BulkDealApprovalDataTable> lstelectedBulkDealApprovalDataTable= getSelectedBulkDealApprovalDataTable();
				if(lstelectedBulkDealApprovalDataTable!=null)
				{
					lstelectedBulkDealApprovalDataTable.remove(bulkDealApprovalDataTable);
					setSelectedBulkDealApprovalDataTable(lstelectedBulkDealApprovalDataTable);
				}
				
			}
		}catch(Exception e)
		{
			setWarningMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("warningDailogId.show();");
		}
		
		
	}
	
	public boolean getSelectRecordFrom(BulkDealApprovalDataTable bulkDealApprovalDataTable)
	{
		boolean checkStatus = false; 
		
		if(!(bulkDealApprovalDataTable.getModifiedBy()==null?bulkDealApprovalDataTable.getCreatedBy():bulkDealApprovalDataTable.getModifiedBy()).equalsIgnoreCase( sessionStateManage.getUserName()))
		{
			
			checkStatus=true;
		}else
		{
			checkStatus=false;
		}
		
		return checkStatus;
	}
	public void approveRecords()
	{
		try{
			List<BulkDealApprovalDataTable> mainLstBulkDealApprovalDataTable=getLstBulkDealApprovalDataTable();
			if(mainLstBulkDealApprovalDataTable!=null && mainLstBulkDealApprovalDataTable.size()>0)
			{
				
				List<BulkDealApprovalDataTable> lstelectedBulkDealApprovalDataTable= getSelectedBulkDealApprovalDataTable();
				if(lstelectedBulkDealApprovalDataTable!=null && lstelectedBulkDealApprovalDataTable.size()>0)
				{
					
					boolean samUserChk=true;
					for(BulkDealApprovalDataTable bulkDealApprovalDataTable:lstelectedBulkDealApprovalDataTable)
					{
						boolean checkStatus=getSelectRecordFrom(bulkDealApprovalDataTable);
						if(!checkStatus)
						{
							samUserChk=checkStatus;
							break;
						}
						
					}
					if(!samUserChk)
					{
						RequestContext.getCurrentInstance().execute("notApproved.show();");
						return;
					}
					
					List<BigDecimal> lstTreasuryHrdId= new ArrayList<BigDecimal>();
					for(BulkDealApprovalDataTable bulkDealApprovalDataTable:lstelectedBulkDealApprovalDataTable)
					{
						lstTreasuryHrdId.add(bulkDealApprovalDataTable.getTreasuryHeaderId());
						
					}
					String rtnMessageJax=  "failure";
					String rtnMessageEmos= "failure";
					
					rtnMessageJax=idealTrackerInfoService.bulkDealApprove(lstTreasuryHrdId, sessionStateManage.getUserName());
					rtnMessageEmos=idealTrackerInfoService.bulkDealApproveWithProcedure(sessionStateManage.getCompanyId(),sessionStateManage.getCountryId(),  lstTreasuryHrdId, sessionStateManage.getUserName());
					
					if(rtnMessageJax.equalsIgnoreCase("Sucess") && rtnMessageEmos.equalsIgnoreCase("Sucess") )
					{
						RequestContext.getCurrentInstance().execute("succesApprove.show();");
					}else
					{
						RequestContext.getCurrentInstance().execute("alreadyApprove.show();");
					}
					
				}else
				{
					RequestContext.getCurrentInstance().execute("slectOneRecord.show();");
				}
			}else
			{
				RequestContext.getCurrentInstance().execute("noRecordApproval.show();");
			}
		}catch(Exception e)
		{
			setWarningMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("warningDailogId.show();");
		}
		
		
		
	}
	
	public void okSelected(){
		pageNavigation();
	}
	
	
	public void deleteRecords()
	{
		try{
			List<BulkDealApprovalDataTable> mainLstBulkDealApprovalDataTable=getLstBulkDealApprovalDataTable();
			if(mainLstBulkDealApprovalDataTable!=null && mainLstBulkDealApprovalDataTable.size()>0)
			{
				List<BulkDealApprovalDataTable> lstelectedBulkDealApprovalDataTable= getSelectedBulkDealApprovalDataTable();
				if(lstelectedBulkDealApprovalDataTable!=null && lstelectedBulkDealApprovalDataTable.size()>0)
				{
					List<BigDecimal> lstTreasuryHrdId= new ArrayList<BigDecimal>();
					for(BulkDealApprovalDataTable bulkDealApprovalDataTable:lstelectedBulkDealApprovalDataTable)
					{
						lstTreasuryHrdId.add(bulkDealApprovalDataTable.getTreasuryHeaderId());
						
					}
					String rtnMessage=idealTrackerInfoService.bulkDealDelete(lstTreasuryHrdId, sessionStateManage.getUserName());
					if(rtnMessage.equalsIgnoreCase("Sucess"))
					{
						RequestContext.getCurrentInstance().execute("recordsDelete.show();");
					}else
					{
						RequestContext.getCurrentInstance().execute("alreadyApprove.show();");
					}
					
				}else
				{
					RequestContext.getCurrentInstance().execute("slectOneRecord.show();");
				}
			}else
			{
				RequestContext.getCurrentInstance().execute("noRecordApproval.show();");
			}
		}catch(Exception e)
		{
			setWarningMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("warningDailogId.show();");
		}
		
		
	}
	
	public void exit() throws IOException {
		setTableDisplay(false);
		if (sessionStateManage.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("../registration/employeehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("../registration/branchhome.xhtml");
		}
	}
}
