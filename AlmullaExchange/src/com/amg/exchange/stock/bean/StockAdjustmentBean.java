package com.amg.exchange.stock.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import com.amg.exchange.common.dto.CurrencyMasterDTO;
import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.foreigncurrency.model.CurrencyWiseDenomination;
import com.amg.exchange.foreigncurrency.model.ForeignCurrencyAdjust;
import com.amg.exchange.foreigncurrency.model.Stock;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.foreigncurrency.service.ForeignLocalCurrencyDenominationService;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.remittance.bean.KIOSKDenominationDataTable;
import com.amg.exchange.stock.service.IStockAdjustmentService;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.service.ISpecialCustomerDealRequestService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("stockAdjustmentBean")
@Scope("session")
public class StockAdjustmentBean<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final Logger log = Logger.getLogger(StockAdjustmentBean.class);
	
	
	private BigDecimal countryBranchId=null;
	private String staffName=null;
	private BigDecimal currencyId=null;
	private BigDecimal stockBalance=null;
	private BigDecimal totalNoofNotes=null;
	private BigDecimal totalNewBalance=null;
	
	private List<CountryBranch> countryBranchLstForLocation = null;
	private List<Employee> staffList =null;
	private List<CurrencyMasterDTO> currencyList =null;
	private ArrayList<KIOSKDenominationDataTable> lstDataForStockAdjust = null;
	private List<UserFinancialYear> financialYearList=null;
	private Map<BigDecimal, BigDecimal> denominationMap =null;
	
	private List<ForeignCurrencyAdjust> foreignCurrencyAdjustList;
	
	//SessionStateManage Declaration
	private SessionStateManage sessionManage = new SessionStateManage();
	
	@Autowired
	IGeneralService<T> igeneralService;
	
	
	
	public IGeneralService<T> getIgeneralService() {
		return igeneralService;
	}

	public void setIgeneralService(IGeneralService<T> igeneralService) {
		this.igeneralService = igeneralService;
	}

	public BigDecimal getCountryBranchId() {
		return countryBranchId;
	}

	public void setCountryBranchId(BigDecimal countryBranchId) {
		this.countryBranchId = countryBranchId;
	}

	public String getStaffName() {
		return staffName;
	}

	
	public BigDecimal getTotalNoofNotes() {
		return totalNoofNotes;
	}

	public BigDecimal getTotalNewBalance() {
		return totalNewBalance;
	}

	public void setTotalNoofNotes(BigDecimal totalNoofNotes) {
		this.totalNoofNotes = totalNoofNotes;
	}

	public void setTotalNewBalance(BigDecimal totalNewBalance) {
		this.totalNewBalance = totalNewBalance;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public BigDecimal getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	public BigDecimal getStockBalance() {
		return stockBalance;
	}

	public void setStockBalance(BigDecimal stockBalance) {
		this.stockBalance = stockBalance;
	}

	public List<CountryBranch> getCountryBranchLstForLocation() {
		return countryBranchLstForLocation;
	}

	public void setCountryBranchLstForLocation(
			List<CountryBranch> countryBranchLstForLocation) {
		this.countryBranchLstForLocation = countryBranchLstForLocation;
	}

	public List<Employee> getStaffList() {
		return staffList;
	}

	public void setStaffList(List<Employee> staffList) {
		this.staffList = staffList;
	}

	public List<CurrencyMasterDTO> getCurrencyList() {
		return currencyList;
	}

	public void setCurrencyList(List<CurrencyMasterDTO> currencyList) {
		this.currencyList = currencyList;
	}

	public ArrayList<KIOSKDenominationDataTable> getLstDataForStockAdjust() {
		return lstDataForStockAdjust;
	}

	public void setLstDataForStockAdjust(
			ArrayList<KIOSKDenominationDataTable> lstDataForStockAdjust) {
		this.lstDataForStockAdjust = lstDataForStockAdjust;
	}

	SessionStateManage sessionStateManage = new SessionStateManage();
	
	private String errorMessage;
	private Boolean booVisible;
	
	
	public Boolean getBooVisible() {
		return booVisible;
	}


	public void setBooVisible(Boolean booVisible) {
		this.booVisible = booVisible;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
	@Autowired
	IGeneralService<T> iGeneralService;
	
	@Autowired
	IStockAdjustmentService<T> iStockAdjustmentService;
	
	@Autowired
	ForeignLocalCurrencyDenominationService<T> foreignLocalCurrencyDenominationService;
	
	@Autowired
	 ISpecialCustomerDealRequestService<T> specialCustomerDealRequestService;
	
	public void getStaffListFromLocation()
	{
		try {
			setBooVisible(false);
			setStaffList(null);
			setStaffName(null);
			List<Employee> lstEmployee=iStockAdjustmentService.getAllStaffList(getCountryBranchId());
			setStaffList(lstEmployee);
			clearSelected();
			setCurrencyList(null);
		} catch (Exception e) {
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage(e.toString());
			setBooVisible(true);
			return;
		}
	}
	
	public void getStaffCurrency()
	{
		try {
			setBooVisible(false);
		//	setStaffList(null);
			setCurrencyList(null);
			setCurrencyId(null);
			List<CurrencyMasterDTO> lstCurrencyMasterDTO=iStockAdjustmentService.getCurrencyFromStock(getCountryBranchId(), getStaffName());
			setCurrencyList(lstCurrencyMasterDTO);
			clearSelected();
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::getStaffCurrency");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		  setErrorMessage(exception.getMessage()); 
		  RequestContext.getCurrentInstance().execute("error.show();");
		  return;       
		  }
	}
	
	public void getDenominationList()
	{
		try {
			setBooVisible(false);
			BigDecimal balanceAmt= iStockAdjustmentService.getBalance(getCountryBranchId(), getStaffName(),getCurrencyId());
			setStockBalance(balanceAmt);
			displayDenomination();
			getDenominationMap();
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::getDenominationList");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		  setErrorMessage(exception.getMessage()); 
		  RequestContext.getCurrentInstance().execute("error.show();");
		  return;       
		  }
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void stockAdjustmentNavigation()
	{
		clear();
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "stockadjustment.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../stock/stockadjustment.xhtml");
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::stockAdjustmentNavigation");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		  setErrorMessage(exception.getMessage()); 
		  RequestContext.getCurrentInstance().execute("error.show();");
		  return;       
		  }
	
		
	}
	public void clearSelected(){
		setLstDataForStockAdjust(null);
		setTotalNewBalance(null);
		setTotalNoofNotes(null);
		setStockBalance(null);
	}
	
	private void clear()
	{
		
		setCountryBranchLstForLocation(null);
		setStaffList(null);
		setCurrencyList(null);
		setCountryBranchId(null);
		setStaffName(null);
		setCurrencyId(null);
		clearSelected();
		try{
		List<CountryBranch> lstCountryBranch=iGeneralService.getBranchDetails(sessionStateManage.getCountryId());
		setCountryBranchLstForLocation(lstCountryBranch);
		}catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::clear");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		  setErrorMessage(exception.getMessage()); 
		  RequestContext.getCurrentInstance().execute("error.show();");
		  return;       
		  }
		
	}
	
	private void displayDenomination()
	{
		try {
			setBooVisible(false);
			int currenyDecimal=foreignLocalCurrencyDenominationService.getDecimalPerCurrency(getCurrencyId());
			
			ArrayList<KIOSKDenominationDataTable> localLstData =new  ArrayList<KIOSKDenominationDataTable>();
			
			List<Stock> dataFromDb = foreignLocalCurrencyDenominationService.getLocalCurrencyDenominationFromStock(sessionStateManage.getCountryId(),getStaffName(),getCountryBranchId().toPlainString(),sessionStateManage.getCompanyId(),getCurrencyId().toPlainString());

			int i = 0;
			
			for (Stock element : dataFromDb) {
				int stock = element.getOpenQuantity() + element.getPurchaseQuantity()	+ element.getReceivedQuantity() - (element.getSaleQuantity() + element.getTransactionQuantity());
				
				KIOSKDenominationDataTable kIOSKDenominationDataTable= new KIOSKDenominationDataTable();
				
				kIOSKDenominationDataTable.setStock(new BigDecimal(stock));
				
				kIOSKDenominationDataTable.setDenominationDesc(element.getDenominationId().getDenominationDesc());
				
				kIOSKDenominationDataTable.setDenominationNo(round(element.getDenominationId().getDenominationAmount(),currenyDecimal));
				
				kIOSKDenominationDataTable.setSerial(++i);
			
				kIOSKDenominationDataTable.setDenominationId(element.getDenominationId().getDenominationId());
				
				localLstData.add(kIOSKDenominationDataTable);
				

				}
			setLstDataForStockAdjust(localLstData);
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::displayDenomination");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		  setErrorMessage(exception.getMessage()); 
		  RequestContext.getCurrentInstance().execute("error.show();");
		  return;       
		  }
		
	}
	
	public static BigDecimal round(BigDecimal bd, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd;
	}
	public void makeEditable(KIOSKDenominationDataTable kioskDtObj) {
		try {
			setBooVisible(false);
			BigDecimal newBalance = null;
			BigDecimal noOfNotes = null;
			if(kioskDtObj.getNoOfNotes()!=null){
			if((kioskDtObj.getNoOfNotes()==null?0:kioskDtObj.getNoOfNotes().intValue())<=(kioskDtObj.getStock()==null?0:kioskDtObj.getStock().intValue()) || (kioskDtObj.getPlustminus()==null?0:kioskDtObj.getPlustminus().intValue())==1){
				if(kioskDtObj.getPlustminus().equals(BigDecimal.ONE)) {
					kioskDtObj.setCashAmount(null);
					newBalance = (kioskDtObj.getStock()==null ? BigDecimal.ZERO :kioskDtObj.getStock().add(kioskDtObj.getNoOfNotes())).multiply(kioskDtObj.getDenominationNo()==null ? BigDecimal.ZERO :kioskDtObj.getDenominationNo());
					kioskDtObj.setCashAmount(newBalance);
				}else if(kioskDtObj.getPlustminus().equals(new BigDecimal(2))) {
					kioskDtObj.setCashAmount(null);
					newBalance = (kioskDtObj.getStock()==null ? BigDecimal.ZERO :kioskDtObj.getStock().subtract(kioskDtObj.getNoOfNotes())).multiply(kioskDtObj.getDenominationNo()==null ? BigDecimal.ZERO :kioskDtObj.getDenominationNo());
					kioskDtObj.setCashAmount(newBalance);
				}else{
					kioskDtObj.setCashAmount(null);
				}
			}else{
				kioskDtObj.setNoOfNotes(null);
				kioskDtObj.setCashAmount(null);
				RequestContext.getCurrentInstance().execute("stockcheck.show();");
				
			}
			
			setTotalNoofNotes(null);
			setTotalNewBalance(null);
			newBalance =null;
			noOfNotes = null;
			for(KIOSKDenominationDataTable kioskDtObj1:lstDataForStockAdjust){	
				if(kioskDtObj1.getNoOfNotes()!=null && ! kioskDtObj1.getNoOfNotes().equals(BigDecimal.ZERO)){
					noOfNotes = (noOfNotes==null?BigDecimal.ZERO:noOfNotes).add(kioskDtObj1.getNoOfNotes());
				}
				if(kioskDtObj1.getCashAmount()!=null && ! kioskDtObj1.getCashAmount().equals(BigDecimal.ZERO)){
					newBalance = (newBalance==null?BigDecimal.ZERO:newBalance).add(kioskDtObj1.getCashAmount());
				}
			}
			setTotalNoofNotes(noOfNotes);
			setTotalNewBalance(newBalance);
			
			}
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::makeEditable");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		  setErrorMessage(exception.getMessage()); 
		  RequestContext.getCurrentInstance().execute("error.show();");
		  return;       
		  }
	}
	
	public void onCellEdit(KIOSKDenominationDataTable kioskDtObj){
		try {
			setBooVisible(false);
			BigDecimal newBalance = null;
			BigDecimal noOfNotes = null;
			if(kioskDtObj.getNoOfNotes()!=null){
			if((kioskDtObj.getNoOfNotes()==null?0:kioskDtObj.getNoOfNotes().intValue())<=(kioskDtObj.getStock()==null?0:kioskDtObj.getStock().intValue()) || (kioskDtObj.getPlustminus()==null?0:kioskDtObj.getPlustminus().intValue())==1){
				if((kioskDtObj.getPlustminus()==null?0:kioskDtObj.getPlustminus().intValue())==1 && (kioskDtObj.getNoOfNotes()==null ? BigDecimal.ZERO :kioskDtObj.getNoOfNotes()).compareTo(BigDecimal.ZERO)>=0){
					kioskDtObj.setCashAmount(null);
					newBalance = (kioskDtObj.getStock()==null ? BigDecimal.ZERO :kioskDtObj.getStock().add(kioskDtObj.getNoOfNotes())).multiply(kioskDtObj.getDenominationNo()==null ? BigDecimal.ZERO :kioskDtObj.getDenominationNo());
					kioskDtObj.setCashAmount(newBalance);
				}else if((kioskDtObj.getPlustminus()==null?0:kioskDtObj.getPlustminus().intValue())==2  && (kioskDtObj.getNoOfNotes()==null ? BigDecimal.ZERO :kioskDtObj.getNoOfNotes()).compareTo(BigDecimal.ZERO)>=0){
					kioskDtObj.setCashAmount(null);
					newBalance = (kioskDtObj.getStock()==null ? BigDecimal.ZERO :kioskDtObj.getStock().subtract(kioskDtObj.getNoOfNotes())).multiply(kioskDtObj.getDenominationNo()==null ? BigDecimal.ZERO :kioskDtObj.getDenominationNo());
					kioskDtObj.setCashAmount(newBalance);
				}else if((kioskDtObj.getPlustminus()==null?0:kioskDtObj.getPlustminus().intValue())==0){
						kioskDtObj.setPlustminus(null);
						kioskDtObj.setCashAmount(null);
						RequestContext.getCurrentInstance().execute("selectPlusMinus.show();");
						return;
				}
			}else{
					kioskDtObj.setNoOfNotes(null);
					kioskDtObj.setCashAmount(null);
					RequestContext.getCurrentInstance().execute("stockcheck.show();");
					
			}
			}else{
				kioskDtObj.setNoOfNotes(null);
				kioskDtObj.setCashAmount(null);
			}
			setTotalNoofNotes(null);
			setTotalNewBalance(null);
			newBalance =null;
			noOfNotes = null;
			for(KIOSKDenominationDataTable kioskDtObj1:lstDataForStockAdjust){	
				if(kioskDtObj1.getNoOfNotes()!=null && ! kioskDtObj1.getNoOfNotes().equals(BigDecimal.ZERO)){
					noOfNotes= (noOfNotes==null?BigDecimal.ZERO:noOfNotes).add(kioskDtObj1.getNoOfNotes());
				}
				if(kioskDtObj1.getCashAmount()!=null && ! kioskDtObj1.getCashAmount().equals(BigDecimal.ZERO)){
					newBalance= (newBalance==null?BigDecimal.ZERO:newBalance).add(kioskDtObj1.getCashAmount());
				}
			}
			setTotalNoofNotes(noOfNotes);
			setTotalNewBalance(newBalance);
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::onCellEdit");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		  setErrorMessage(exception.getMessage()); 
		  RequestContext.getCurrentInstance().execute("error.show();");
		  return;       
		  }
		
	}
	
	public void exit(){
		if (sessionManage.getRoleId().equalsIgnoreCase("1")) {
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
			} catch (NullPointerException ne) {
				// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				setErrorMessage("Method Name::exit");
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return;
			} catch(Exception exception){
				log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			  setErrorMessage(exception.getMessage()); 
			  RequestContext.getCurrentInstance().execute("error.show();");
			  return;       
			  }
		} else {
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
			} catch (NullPointerException ne) {
				// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				setErrorMessage("Method Name::exit");
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return;
			} catch(Exception exception){
				log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			  setErrorMessage(exception.getMessage()); 
			  RequestContext.getCurrentInstance().execute("error.show();");
			  return;       
			  }
		}
	}
	
	public void clearAll(){
		clear();
	
		if(denominationMap!=null){
			denominationMap.clear();
		}
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../stock/stockadjustment.xhtml");
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::clearAll");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		  setErrorMessage(exception.getMessage()); 
		  RequestContext.getCurrentInstance().execute("error.show();");
		  return;       
		  }
	}
	
	
	public String getDocumentSerialID(String processIn){

		try{
		String documentSerialID = igeneralService.getNextDocumentReferenceNumber(sessionStateManage.getCountryId().intValue() ,sessionStateManage.getCompanyId().intValue(), Integer.parseInt(Constants.DOCUMENT_CODE_FOR_STOCK_ADJUST), getFinaceYear(),processIn,sessionStateManage.getCountryBranchCode());
		return documentSerialID;
		}	catch (NullPointerException ne) {
			ne.printStackTrace();
					// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
					setErrorMessage("Method Name::getDocumentSerialID");
					RequestContext.getCurrentInstance().execute("nullPointerId.show();");
					return null;
				} catch(Exception exception){
					log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
				  setErrorMessage(exception.getMessage()); 
				  RequestContext.getCurrentInstance().execute("error.show();");
				  return null;       
				  }
	}

	
	
	//for getting document seriality Number
		/*	public String getDocumentSerialID() {
				// TODO
				try{
				String documentSerialID = specialCustomerDealRequestService.getNextDocumentSerialNumber(Integer.parseInt(sessionManage.getSessionValue("countryId")), 
						Integer.parseInt(sessionManage.getSessionValue("companyId")),Integer.parseInt(Constants.DOCUMENT_CODE_FOR_STOCK_ADJUST) , getFinaceYear(), Constants.Yes);
				return documentSerialID;
				}catch (NullPointerException ne) {
					// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
					setErrorMessage("Method Name::getDocumentSerialID");
					RequestContext.getCurrentInstance().execute("nullPointerId.show();");
					return null;
				} catch(Exception exception){
					log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
				  setErrorMessage(exception.getMessage()); 
				  RequestContext.getCurrentInstance().execute("error.show();");
				  return null;       
				  }
			}*/
			
			//for getting financial year
			public int getFinaceYear() {
				int finaceYear=0;
				financialYearList = new ArrayList<UserFinancialYear>();
				try {

					financialYearList = specialCustomerDealRequestService.getUserFinancialYear(new Date());
					log.info("financialYearList :" + financialYearList.size());
					if (financialYearList != null)
						finaceYear = Integer.parseInt(financialYearList.get(0).getFinancialYear().toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				System.out.println(finaceYear);
				return finaceYear;
			}
	
			
			public void getDenominationMap(){
				denominationMap=new HashMap<BigDecimal, BigDecimal>();
				try{
				List<CurrencyWiseDenomination> denominationList=iStockAdjustmentService.currencyWiseDenominations(getCurrencyId());
				for(CurrencyWiseDenomination denomination:denominationList){
					denominationMap.put(denomination.getDenominationId(), denomination.getDenominationAmount());
				}
				}catch (NullPointerException ne) {
					// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
					setErrorMessage("Method Name::getDenominationMap");
					RequestContext.getCurrentInstance().execute("nullPointerId.show();");
					return;
				} catch(Exception exception){
					log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
				  setErrorMessage(exception.getMessage()); 
				  RequestContext.getCurrentInstance().execute("error.show();");
				  return;       
				  }
			}
			
			
		public static String getCurrentDateWithFormat() {
			Map<Integer, String> data = new HashMap<Integer, String>();
			for (int i = 0; i < 12; i++) {
				if (i < 9) {
					data.put(i, "0" + String.valueOf(i + 1));
				} else {
					data.put(i, String.valueOf(i + 1));
				}
			}
			String year = String.valueOf(new Date().getYear()).substring(1, 3);
			return "01/" + data.get(Calendar.getInstance().get(Calendar.MONTH)) + "/" + year;
		}
		
	public void save() {
		try {
			setBooVisible(false);
			BigDecimal balance = null;
			foreignCurrencyAdjustList = new ArrayList<ForeignCurrencyAdjust>();
			BigDecimal lineNo = BigDecimal.ZERO;
			SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
			Date acc_Month = null;
			try {
				acc_Month = DATE_FORMAT.parse(getCurrentDateWithFormat());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			for (KIOSKDenominationDataTable kioskDtObj : lstDataForStockAdjust) {
				if (kioskDtObj.getCashAmount() != null && kioskDtObj.getCashAmount().intValue() > 0) {
					ForeignCurrencyAdjust foreignCurrencyAdjust = new ForeignCurrencyAdjust();
					lineNo = lineNo.add(BigDecimal.ONE);
					CompanyMaster companyMaster = new CompanyMaster();
					companyMaster.setCompanyId(new BigDecimal(sessionManage.getSessionValue("companyId")));
					foreignCurrencyAdjust.setFsCompanyMaster(companyMaster);
					CountryMaster countryMaster = new CountryMaster();
					countryMaster.setCountryId(new BigDecimal(sessionManage.getSessionValue("countryId")));
					foreignCurrencyAdjust.setFsCountryMaster(countryMaster);
					foreignCurrencyAdjust.setDocumentCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_STOCK_ADJUST));
					foreignCurrencyAdjust.setDocumentFinanceYear(new BigDecimal(getFinaceYear()));
					String document = getDocumentSerialID(Constants.U);
					foreignCurrencyAdjust.setDocumentNo(new BigDecimal(document));
					foreignCurrencyAdjust.setDocumentDate(new Date());
					CountryBranch countryBranch = new CountryBranch();
					countryBranch.setCountryBranchId(getCountryBranchId());
					foreignCurrencyAdjust.setCountryBranch(countryBranch);
					CurrencyMaster currencyMaster = new CurrencyMaster();
					currencyMaster.setCurrencyId(getCurrencyId());
					foreignCurrencyAdjust.setFsCurrencyMaster(currencyMaster);
					balance = denominationMap.get(kioskDtObj.getDenominationId()).multiply(kioskDtObj.getNoOfNotes());
					foreignCurrencyAdjust.setAdjustmentAmount(balance);
					foreignCurrencyAdjust.setNotesQuantity(kioskDtObj.getNoOfNotes());
					CurrencyWiseDenomination denominationMaster = new CurrencyWiseDenomination();
					denominationMaster.setDenominationId(kioskDtObj.getDenominationId());
					foreignCurrencyAdjust.setFsDenominationId(denominationMaster);
					foreignCurrencyAdjust.setProgNumber(Constants.STOCK_ADJUST);
					if ((kioskDtObj.getPlustminus() == null ? 0 : kioskDtObj.getPlustminus().intValue()) == 1) {
						foreignCurrencyAdjust.setTransactionType(Constants.P);
					} else if ((kioskDtObj.getPlustminus() == null ? 0 : kioskDtObj.getPlustminus().intValue()) == 2) {
						foreignCurrencyAdjust.setTransactionType(Constants.R);
					}
					foreignCurrencyAdjust.setDocumentStatus(Constants.Yes);
					foreignCurrencyAdjust.setOracleUser(getStaffName());
					//foreignCurrencyAdjust.setStockUpdated(Constants.Yes);
					foreignCurrencyAdjust.setDocumentLineNumber(lineNo);
					foreignCurrencyAdjust.setAccountmmyyyy(acc_Month);
					foreignCurrencyAdjust.setDenaminationAmount(denominationMap.get(kioskDtObj.getDenominationId()));
					foreignCurrencyAdjust.setCreatedDate(new Date());
					foreignCurrencyAdjust.setCreatedBy(sessionManage.getSessionValue("userName"));
					foreignCurrencyAdjustList.add(foreignCurrencyAdjust);
				}
			}
			if (foreignCurrencyAdjustList.size() > 0) {
				try {
					iStockAdjustmentService.saveForeignCurrencyAdjustRecords(foreignCurrencyAdjustList);
					RequestContext.getCurrentInstance().execute("success.show();");
					denominationMap.clear();
				} catch (NullPointerException ne) {
					// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
					setErrorMessage("Method Name::save");
					RequestContext.getCurrentInstance().execute("nullPointerId.show();");
					return;
				} catch(Exception exception){
					log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
				  setErrorMessage(exception.getMessage()); 
				  RequestContext.getCurrentInstance().execute("error.show();");
				  return;       
				  }
			}
			if (balance == null) {
				RequestContext.getCurrentInstance().execute("selectAmount.show();");
				return;
			}
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::save");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		  setErrorMessage(exception.getMessage()); 
		  RequestContext.getCurrentInstance().execute("error.show();");
		  return;       
		  }
	}
	
}
