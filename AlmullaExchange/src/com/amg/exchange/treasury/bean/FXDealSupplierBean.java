package com.amg.exchange.treasury.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.LanguageType;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.foreigncurrency.service.ForeignLocalCurrencyDenominationService;
import com.amg.exchange.foreigncurrency.service.IForeignCurrencyPurchaseService;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.treasury.deal.supplier.model.ApplicationSetup;
import com.amg.exchange.treasury.deal.supplier.model.DayBookDetails;
import com.amg.exchange.treasury.deal.supplier.model.DayBookHeader;
import com.amg.exchange.treasury.model.AccountBalance;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.treasury.model.TreasuryCustomerDeal;
import com.amg.exchange.treasury.model.TreasuryDealDetail;
import com.amg.exchange.treasury.model.TreasuryDealHeader;
import com.amg.exchange.treasury.service.IBankTransferService;
import com.amg.exchange.treasury.service.IFXDealSupplierService;
import com.amg.exchange.treasury.service.IFXDetailInformationService;
import com.amg.exchange.treasury.service.IFundEstimationService;
import com.amg.exchange.treasury.service.IFxDealwithSupplierService;
import com.amg.exchange.treasury.service.ISpecialCustomerDealRequestService;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

/**
 * @author Sundaram C
 * 
 */

@Component("fXDealSupplierBean")
@Scope("session")
public class FXDealSupplierBean<T> implements Serializable {

	private static final long serialVersionUID = 1L;


	public static final Logger log = Logger.getLogger(FXDealSupplierBean.class);

	private BigDecimal companyId=null;
	private String companyName;
	private String documentDescription=null;
	private BigDecimal documentNo=null;
	private BigDecimal documentNoForDayBook=null;
	private BigDecimal financialYearId=null;
	private int finaceYear=0;
	private BigDecimal documentSerialforDayBook;
	private BigDecimal documentSerialforFxdeal;
	private BigDecimal docSerialIdNumberForSave;
	private String processIn=Constants.Yes;
	private String fxDlwithSupplierDealDate;
	private String fxDlwithSupplierDealWith;
	private String fxDlwithSupplierConttract;
	private String fxDlSupplierConcludedby;
	private String fxDlSupplierReutersRef;
	private String fxDlSupplierRemarks;
	private String pdBankName="";
	private BigDecimal pdBankId=null;
	private BigDecimal pdCurrencyId;
	private String fxDlSupplierAccNo;
	private String fxDlSupplierAccNoForSave;
	private BigDecimal sdBankId=null;
	private BigDecimal sdCurrencyId;
	private String fxDlSalesAccNo;
	//private String pdValueDate;
	private BigDecimal pdExchangeRate;
	private BigDecimal pdFCAmount;
	private BigDecimal pdAmount;
	private BigDecimal pdLocalExchangeRate;
	private BigDecimal pdLocalAmount;
	private String pdPayableAccountNumber;
	private BigDecimal pdMultipleDivision;
	private Date pdCalValueDate;
	private Date hdCalValueDate;
	private String pdCurrencyIdUpdate;
	private String pdAccNoForUpdate;
	private String pdSaveValueDate;
	private String sdSaveValueDate;

	private BigDecimal sdBankBalance;
	//private String sdValueDate;
	private BigDecimal sdSaleAmount;
	private BigDecimal sdAverageRate;
	private BigDecimal sdLocalAmount;
	private String sdBankName="";
	private Date sdCalValueDate;
	private String sdCurrencyName;

	private String errorMessage;

	//Render begins
	private Boolean booRenderCurrencyId=false;
	private Boolean booRenderCurrencyIdForUpdate=true;
	private Boolean booRenderPDAccNo=false;
	private Boolean booRenderPDAccNoForUpdate=true;
	private Boolean booRenderSDCurrency=false;
	private Boolean booRenderSDCurrencyForUpdate=true;


	private BigDecimal dealCustomerSupplierId ;
	private BigDecimal applicationSetupId;
	private BigDecimal daybookHeaderId;
	private BigDecimal dayBookDetailsId;

	private BigDecimal teasuryDealHeaderId = null;
	private BigDecimal treasuryInstructionId ;
	private BigDecimal teasuryDealDetailId = null; // P
	private BigDecimal treasurytandardInstructionTo;
	private BigDecimal treasuryStandardInstructionId;
	
	private Boolean successPanelRender = false;
	private Boolean mainPanelRender = false;

	private Date minDealDate = new Date();


	@Autowired
	IFXDetailInformationService<T> fXDetailInformationService;

	@Autowired
	IBankTransferService<T> bankTransferService;



	
	public Boolean getSuccessPanelRender() {
		return successPanelRender;
	}

	public Boolean getMainPanelRender() {
		return mainPanelRender;
	}

	public void setSuccessPanelRender(Boolean successPanelRender) {
		this.successPanelRender = successPanelRender;
	}

	public void setMainPanelRender(Boolean mainPanelRender) {
		this.mainPanelRender = mainPanelRender;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public BigDecimal getTeasuryDealHeaderId() {
		return teasuryDealHeaderId;
	}

	public void setTeasuryDealHeaderId(BigDecimal teasuryDealHeaderId) {
		this.teasuryDealHeaderId = teasuryDealHeaderId;
	}

	public BigDecimal getTreasuryInstructionId() {
		return treasuryInstructionId;
	}

	public void setTreasuryInstructionId(BigDecimal treasuryInstructionId) {
		this.treasuryInstructionId = treasuryInstructionId;
	}

	public BigDecimal getTeasuryDealDetailId() {
		return teasuryDealDetailId;
	}

	public void setTeasuryDealDetailId(BigDecimal teasuryDealDetailId) {
		this.teasuryDealDetailId = teasuryDealDetailId;
	}

	public BigDecimal getTreasurytandardInstructionTo() {
		return treasurytandardInstructionTo;
	}

	public void setTreasurytandardInstructionTo(
			BigDecimal treasurytandardInstructionTo) {
		this.treasurytandardInstructionTo = treasurytandardInstructionTo;
	}

	public BigDecimal getTreasuryStandardInstructionId() {
		return treasuryStandardInstructionId;
	}

	public void setTreasuryStandardInstructionId(
			BigDecimal treasuryStandardInstructionId) {
		this.treasuryStandardInstructionId = treasuryStandardInstructionId;
	}



	public BigDecimal getDealCustomerSupplierId() {
		return dealCustomerSupplierId;
	}

	public void setDealCustomerSupplierId(BigDecimal dealCustomerSupplierId) {
		this.dealCustomerSupplierId = dealCustomerSupplierId;
	}

	public BigDecimal getApplicationSetupId() {
		return applicationSetupId;
	}

	public void setApplicationSetupId(BigDecimal applicationSetupId) {
		this.applicationSetupId = applicationSetupId;
	}

	public BigDecimal getDaybookHeaderId() {
		return daybookHeaderId;
	}

	public void setDaybookHeaderId(BigDecimal daybookHeaderId) {
		this.daybookHeaderId = daybookHeaderId;
	}

	public BigDecimal getDayBookDetailsId() {
		return dayBookDetailsId;
	}

	public void setDayBookDetailsId(BigDecimal dayBookDetailsId) {
		this.dayBookDetailsId = dayBookDetailsId;
	}

	public BigDecimal getCompanyId() {
		return companyId;
	}

	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}


	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


	public BigDecimal getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}

	public void setDocumentDescription(String documentDescription) {
		this.documentDescription = documentDescription;
	}


	public BigDecimal getFinancialYearId() {
		return financialYearId;
	}

	public void setFinancialYearId(BigDecimal financialYearId) {
		this.financialYearId = financialYearId;
	}

	public BigDecimal getDocumentNoForDayBook() {
		return documentNoForDayBook;
	}

	public void setDocumentNoForDayBook(BigDecimal documentNoForDayBook) {
		this.documentNoForDayBook = documentNoForDayBook;
	}


	public Date getMinDealDate() {
		return minDealDate;
	}

	public void setMinDealDate(Date minDealDate) {
		this.minDealDate = minDealDate;
	}

	public int getFinaceYear() {
		/*try{
			List<UserFinancialYear> financialYearList = igeneralService.getDealYear(new Date());
			log.info("financialYearList :"+financialYearList.size());
			if(financialYearList!=null && financialYearList.size()>0)
			finaceYear = Integer.parseInt(financialYearList.get(0).getFinancialYear().toString());
			financialYearId=financialYearList.get(0).getFinancialYearID();
			setFinancialYearId(financialYearId);
			setFinaceYear(finaceYear);
		}catch(Exception e){
		e.printStackTrace();	
		}*/
		return finaceYear;
	}

	public void setFinaceYear(int finaceYear) {
		this.finaceYear = finaceYear;
	}


	public String getFxDlwithSupplierDealDate() {
		return returnFormattedDate(new Date());
	}

	public void setFxDlwithSupplierDealDate(String fxDlwithSupplierDealDate) {
		this.fxDlwithSupplierDealDate = fxDlwithSupplierDealDate;
	}

	public String getFxDlwithSupplierDealWith() {
		return fxDlwithSupplierDealWith;
	}

	public void setFxDlwithSupplierDealWith(String fxDlwithSupplierDealWith) {
		this.fxDlwithSupplierDealWith = fxDlwithSupplierDealWith;
	}

	public String getFxDlwithSupplierConttract() {
		return fxDlwithSupplierConttract;
	}

	public void setFxDlwithSupplierConttract(String fxDlwithSupplierConttract) {
		this.fxDlwithSupplierConttract = fxDlwithSupplierConttract;
	}

	public String getFxDlSupplierConcludedby() {
		return fxDlSupplierConcludedby;
	}

	public void setFxDlSupplierConcludedby(String fxDlSupplierConcludedby) {
		this.fxDlSupplierConcludedby = fxDlSupplierConcludedby;
	}

	public String getFxDlSupplierReutersRef() {
		return fxDlSupplierReutersRef;
	}

	public void setFxDlSupplierReutersRef(String fxDlSupplierReutersRef) {
		this.fxDlSupplierReutersRef = fxDlSupplierReutersRef;
	}

	public String getFxDlSupplierRemarks() {
		return fxDlSupplierRemarks;
	}

	public void setFxDlSupplierRemarks(String fxDlSupplierRemarks) {
		this.fxDlSupplierRemarks = fxDlSupplierRemarks;
	}


	public String getPdBankName() {
		return pdBankName;
	}

	public void setPdBankName(String pdBankName) {
		this.pdBankName = pdBankName;
	}

	public BigDecimal getDocumentSerialforDayBook() {
		return documentSerialforDayBook;
	}

	public void setDocumentSerialforDayBook(BigDecimal documentSerialforDayBook) {
		this.documentSerialforDayBook = documentSerialforDayBook;
	}

	public BigDecimal getDocumentSerialforFxdeal() {
		return documentSerialforFxdeal;
	}

	public void setDocumentSerialforFxdeal(BigDecimal documentSerialforFxdeal) {
		this.documentSerialforFxdeal = documentSerialforFxdeal;
	}

	public BigDecimal getDocSerialIdNumberForSave() {
		return docSerialIdNumberForSave;
	}

	public void setDocSerialIdNumberForSave(BigDecimal docSerialIdNumberForSave) {
		this.docSerialIdNumberForSave = docSerialIdNumberForSave;
	}


	public BigDecimal getPdCurrencyId() {
		return pdCurrencyId;
	}

	public void setPdCurrencyId(BigDecimal pdCurrencyId) {
		this.pdCurrencyId = pdCurrencyId;
	}


	public String getFxDlSupplierAccNo() {
		return fxDlSupplierAccNo;
	}

	public void setFxDlSupplierAccNo(String fxDlSupplierAccNo) {
		this.fxDlSupplierAccNo = fxDlSupplierAccNo;
	}


	public String getFxDlSupplierAccNoForSave() {
		return fxDlSupplierAccNoForSave;
	}

	public void setFxDlSupplierAccNoForSave(String fxDlSupplierAccNoForSave) {
		this.fxDlSupplierAccNoForSave = fxDlSupplierAccNoForSave;
	}

	public BigDecimal getSdBankId() {
		return sdBankId;
	}

	public void setSdBankId(BigDecimal sdBankId) {
		this.sdBankId = sdBankId;
	}

	public BigDecimal getSdCurrencyId() {
		return sdCurrencyId;
	}

	public void setSdCurrencyId(BigDecimal sdCurrencyId) {
		this.sdCurrencyId = sdCurrencyId;
	}


	public String getFxDlSalesAccNo() {
		return fxDlSalesAccNo;
	}

	public void setFxDlSalesAccNo(String fxDlSalesAccNo) {
		this.fxDlSalesAccNo = fxDlSalesAccNo;
	}



	public BigDecimal getPdExchangeRate() {
		return pdExchangeRate;
	}

	public void setPdExchangeRate(BigDecimal pdExchangeRate) {
		this.pdExchangeRate = pdExchangeRate;
	}

	public BigDecimal getPdFCAmount() {
		return pdFCAmount;
	}

	public void setPdFCAmount(BigDecimal pdFCAmount) {
		this.pdFCAmount = pdFCAmount;
	}

	public BigDecimal getPdAmount() {
		return pdAmount;
	}

	public void setPdAmount(BigDecimal pdAmount) {
		this.pdAmount = pdAmount;
	}

	public BigDecimal getPdLocalExchangeRate() {
		return pdLocalExchangeRate;
	}

	public void setPdLocalExchangeRate(BigDecimal pdLocalExchangeRate) {
		this.pdLocalExchangeRate = pdLocalExchangeRate;
	}

	public BigDecimal getPdLocalAmount() {
		return pdLocalAmount;
	}

	public void setPdLocalAmount(BigDecimal pdLocalAmount) {
		this.pdLocalAmount = pdLocalAmount;
	}


	public BigDecimal getSdBankBalance() {
		return sdBankBalance;
	}

	public void setSdBankBalance(BigDecimal sdBankBalance) {
		this.sdBankBalance = sdBankBalance;
	}



	public BigDecimal getSdSaleAmount() {
		return sdSaleAmount;
	}

	public void setSdSaleAmount(BigDecimal sdSaleAmount) {
		this.sdSaleAmount = sdSaleAmount;
	}

	public BigDecimal getSdAverageRate() {
		return sdAverageRate;
	}

	public void setSdAverageRate(BigDecimal sdAverageRate) {
		this.sdAverageRate = sdAverageRate;
	}

	public BigDecimal getSdLocalAmount() {
		return sdLocalAmount;
	}

	public void setSdLocalAmount(BigDecimal sdLocalAmount) {
		this.sdLocalAmount = sdLocalAmount;
	}


	public String getSdBankName() {
		return sdBankName;
	}

	public void setSdBankName(String sdBankName) {
		this.sdBankName = sdBankName;
	}


	public BigDecimal getPdMultipleDivision() {
		return pdMultipleDivision;
	}

	public void setPdMultipleDivision(BigDecimal pdMultipleDivision) {
		this.pdMultipleDivision = pdMultipleDivision;
	}


	public Date getPdCalValueDate() {
		return pdCalValueDate;
	}

	public void setPdCalValueDate(Date pdCalValueDate) {
		this.pdCalValueDate = pdCalValueDate;
	}


	public Date getHdCalValueDate() {
		return new Date();
	}

	public void setHdCalValueDate(Date hdCalValueDate) {
		this.hdCalValueDate = hdCalValueDate;
	}

	public Date getSdCalValueDate() {
		return sdCalValueDate;
	}

	public void setSdCalValueDate(Date sdCalValueDate) {
		this.sdCalValueDate = sdCalValueDate;
	}


	public Boolean getBooRenderCurrencyId() {
		return booRenderCurrencyId;
	}

	public void setBooRenderCurrencyId(Boolean booRenderCurrencyId) {
		this.booRenderCurrencyId = booRenderCurrencyId;
	}

	public Boolean getBooRenderCurrencyIdForUpdate() {
		return booRenderCurrencyIdForUpdate;
	}

	public void setBooRenderCurrencyIdForUpdate(Boolean booRenderCurrencyIdForUpdate) {
		this.booRenderCurrencyIdForUpdate = booRenderCurrencyIdForUpdate;
	}




	public String getPdCurrencyIdUpdate() {
		return pdCurrencyIdUpdate;
	}

	public void setPdCurrencyIdUpdate(String pdCurrencyIdUpdate) {
		this.pdCurrencyIdUpdate = pdCurrencyIdUpdate;
	}




	public Boolean getBooRenderPDAccNo() {
		return booRenderPDAccNo;
	}

	public void setBooRenderPDAccNo(Boolean booRenderPDAccNo) {
		this.booRenderPDAccNo = booRenderPDAccNo;
	}

	public Boolean getBooRenderPDAccNoForUpdate() {
		return booRenderPDAccNoForUpdate;
	}

	public void setBooRenderPDAccNoForUpdate(Boolean booRenderPDAccNoForUpdate) {
		this.booRenderPDAccNoForUpdate = booRenderPDAccNoForUpdate;
	}

	public Boolean getBooRenderSDCurrency() {
		return booRenderSDCurrency;
	}

	public void setBooRenderSDCurrency(Boolean booRenderSDCurrency) {
		this.booRenderSDCurrency = booRenderSDCurrency;
	}

	public Boolean getBooRenderSDCurrencyForUpdate() {
		return booRenderSDCurrencyForUpdate;
	}

	public void setBooRenderSDCurrencyForUpdate(Boolean booRenderSDCurrencyForUpdate) {
		this.booRenderSDCurrencyForUpdate = booRenderSDCurrencyForUpdate;
	}

	public String getPdAccNoForUpdate() {
		return pdAccNoForUpdate;
	}

	public void setPdAccNoForUpdate(String pdAccNoForUpdate) {
		this.pdAccNoForUpdate = pdAccNoForUpdate;
	}




	public String getSdCurrencyName() {
		return sdCurrencyName;
	}

	public void setSdCurrencyName(String sdCurrencyName) {
		this.sdCurrencyName = sdCurrencyName;
	}

	@Autowired
	ISpecialCustomerDealRequestService<T> specialCustomerDealRequestService;

	@Autowired
	ForeignLocalCurrencyDenominationService<T> foreignLocalCurrencyDenominationService;
	@Autowired
	IGeneralService<T> igeneralService;
	@Autowired
	IFundEstimationService<T> fundEstimationService;
	@Autowired
	IFxDealwithSupplierService<T> fxDealwithSupplierService;
	@Autowired
	IForeignCurrencyPurchaseService<T> foreignCurrencyPurchaseService;


	public ISpecialCustomerDealRequestService<T> getSpecialCustomerDealRequestService() {
		return specialCustomerDealRequestService;
	}

	public void setSpecialCustomerDealRequestService(
			ISpecialCustomerDealRequestService<T> specialCustomerDealRequestService) {
		this.specialCustomerDealRequestService = specialCustomerDealRequestService;
	}

	public IForeignCurrencyPurchaseService<T> getForeignCurrencyPurchaseService() {
		return foreignCurrencyPurchaseService;
	}

	public void setForeignCurrencyPurchaseService(
			IForeignCurrencyPurchaseService<T> foreignCurrencyPurchaseService) {
		this.foreignCurrencyPurchaseService = foreignCurrencyPurchaseService;
	}

	public ForeignLocalCurrencyDenominationService<T> getForeignLocalCurrencyDenominationService() {
		return foreignLocalCurrencyDenominationService;
	}

	public void setForeignLocalCurrencyDenominationService(
			ForeignLocalCurrencyDenominationService<T> foreignLocalCurrencyDenominationService) {
		this.foreignLocalCurrencyDenominationService = foreignLocalCurrencyDenominationService;
	}

	public IFundEstimationService<T> getFundEstimationService() {
		return fundEstimationService;
	}

	public void setFundEstimationService(
			IFundEstimationService<T> fundEstimationService) {
		this.fundEstimationService = fundEstimationService;
	}

	public IGeneralService<T> getIgeneralService() {
		return igeneralService;
	}

	public void setIgeneralService(IGeneralService<T> igeneralService) {
		this.igeneralService = igeneralService;
	}

	public IFxDealwithSupplierService<T> getFxDealwithSupplierService() {
		return fxDealwithSupplierService;
	}

	public void setFxDealwithSupplierService(
			IFxDealwithSupplierService<T> fxDealwithSupplierService) {
		this.fxDealwithSupplierService = fxDealwithSupplierService;
	}


	public String getPdPayableAccountNumber() {
		return pdPayableAccountNumber;
	}

	public void setPdPayableAccountNumber(String pdPayableAccountNumber) {
		this.pdPayableAccountNumber = pdPayableAccountNumber;
	}


	public String getPdSaveValueDate() {
		return pdSaveValueDate;
	}

	public void setPdSaveValueDate(String pdSaveValueDate) {
		this.pdSaveValueDate = pdSaveValueDate;
	}

	public String getSdSaveValueDate() {
		return sdSaveValueDate;
	}

	public void setSdSaveValueDate(String sdSaveValueDate) {
		this.sdSaveValueDate = sdSaveValueDate;
	}

	private List<BankMaster> pdBankLst=new ArrayList<BankMaster>();
	private List<BankMaster> sdBankLst=new ArrayList<BankMaster>();

	private List<BankAccountDetails> currencyListFromDB=new ArrayList<BankAccountDetails>();
	private List<BankAccountDetails> sdCurrencyListFromDB=new ArrayList<BankAccountDetails>();
	private List<BankAccountDetails> lstPdAccountNumber = new ArrayList<BankAccountDetails>();
	private List<BankAccountDetails> lstSdAccountNumber = new ArrayList<BankAccountDetails>();



	public BigDecimal getPdBankId() {
		return pdBankId;
	}

	public void setPdBankId(BigDecimal pdBankId) {
		this.pdBankId = pdBankId;
	}

	public List<BankApplicability> getSdBankLst() {
		  List<BankApplicability> lstBankApplicability=null;
		  try{
			    lstBankApplicability=fxDealwithSupplierService.getLocalBankFromOtherBank(sessionStateManage.getCountryId(),getPdBankId());

		for(BankApplicability bankApplicability:lstBankApplicability){


			if(bankApplicability.getBankMaster().getBankId().equals(getPdBankId())){
				setPdBankName(bankApplicability.getBankMaster().getBankFullName());
			}
		}
		    } catch (Exception exception) {
			      log.info("exception.getMessage():::::::::::::::::::::::::::::::" + exception.getMessage());
			      setErrorMessage(exception.getMessage());
			      RequestContext.getCurrentInstance().execute("error.show();");
			      return null;
		    }
		return lstBankApplicability;

	}


	public void setSdBankLst(List<BankMaster> sdBankLst) {
		this.sdBankLst = sdBankLst;
	}

	public List<BankAccountDetails> getCurrencyListFromDB() {
		return currencyListFromDB;
	}

	public void setCurrencyListFromDB(List<BankAccountDetails> currencyListFromDB) {
		this.currencyListFromDB = currencyListFromDB;
	}


	public List<BankAccountDetails> getSdCurrencyListFromDB() {
		return sdCurrencyListFromDB;
	}

	public void setSdCurrencyListFromDB(
			List<BankAccountDetails> sdCurrencyListFromDB) {
		this.sdCurrencyListFromDB = sdCurrencyListFromDB;
	}

	public List<BankApplicability> getPdBankLst() {
		  List<BankApplicability> lstBankApplicability=null;
		  try{
			    lstBankApplicability=igeneralService.getCorrespondingLocalFundingBanks(sessionStateManage.getCountryId());

		for(BankApplicability bankApplicability:lstBankApplicability){


			if(bankApplicability.getBankMaster().getBankId().equals(getPdBankId())){
				setPdBankName(bankApplicability.getBankMaster().getBankFullName());
			}
		}
		  }catch (Exception exception) {
			      log.info("exception.getMessage():::::::::::::::::::::::::::::::" + exception.getMessage());
			      setErrorMessage(exception.getMessage());
			      RequestContext.getCurrentInstance().execute("error.show();");
			      return null;
		    }
		return lstBankApplicability;


	}


	public void setPdBankLst(List<BankMaster> pdBankLst) {
		this.pdBankLst = pdBankLst;
	}



	public List<BankAccountDetails> getLstPdAccountNumber() {
		return lstPdAccountNumber;
	}

	public void setLstPdAccountNumber(List<BankAccountDetails> lstPdAccountNumber) {
		this.lstPdAccountNumber = lstPdAccountNumber;
	}


	public List<BankAccountDetails> getLstSdAccountNumber() {
		return lstSdAccountNumber;
	}

	public void setLstSdAccountNumber(List<BankAccountDetails> lstSdAccountNumber) {
		this.lstSdAccountNumber = lstSdAccountNumber;
	}


	private SessionStateManage sessionStateManage=new SessionStateManage();

	public String getCompanyListFromDB() {
		  List<CompanyMasterDesc> data=null;
		  try{
			    data=igeneralService.getCompanyList(sessionStateManage.getCompanyId(),new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"));
		setCompanyId(data.get(0).getFsCompanyMaster().getCompanyId());
		setCompanyName(data.get(0).getCompanyName());
		  }catch (Exception exception) {
			      log.info("exception.getMessage():::::::::::::::::::::::::::::::" + exception.getMessage());
			      setErrorMessage(exception.getMessage());
			      RequestContext.getCurrentInstance().execute("error.show();");
			      return null;
		    }
		return data.get(0).getCompanyName();
	}
	public String getDocumentDescription() {
		  List<Document> documentDesc=null;
		  try{
			    documentDesc=igeneralService.getDocument((Constants.DOCUMENT_CODE_FOR_FX_DEAL_WITH_BANK==null ? new BigDecimal(0): new BigDecimal(Constants.DOCUMENT_CODE_FOR_FX_DEAL_WITH_BANK)),new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"));
		for(Document des:documentDesc)
		{
			setDocumentNo(des.getDocumentID());
			setDocumentDescription(des.getDocumentDesc());
		}
		  }catch (Exception exception) {
			      log.info("exception.getMessage():::::::::::::::::::::::::::::::" + exception.getMessage());
			      setErrorMessage(exception.getMessage());
			      RequestContext.getCurrentInstance().execute("error.show();");
			      return null;
		    }
		return documentDescription;
	}
	public void pdBankFromOther()
	{
		  try{
		clearPDValuesForBankSelection();
		clearSDValues();
		List<BankAccountDetails> pbankcurrency = fundEstimationService.getCurrencyOfBank(getPdBankId());
		int pdCurrencySize=pbankcurrency.size();
		if(pdCurrencySize<=1)
		{
			for (BankAccountDetails element : pbankcurrency) {
				setPdCurrencyId(element.getFsCurrencyMaster().getCurrencyId());
				setPdCurrencyIdUpdate(element.getFsCurrencyMaster().getCurrencyName());
				setSdCurrencyName(element.getFsCurrencyMaster().getCurrencyName());
			}
			setBooRenderCurrencyId(false);
			setBooRenderCurrencyIdForUpdate(true);
			populateAccountNumberForPD();

		}else
		{
			setBooRenderCurrencyId(true);
			setBooRenderCurrencyIdForUpdate(false);
			setCurrencyListFromDB(pbankcurrency);
		}
		  }catch (Exception exception) {
			      log.info("exception.getMessage():::::::::::::::::::::::::::::::" + exception.getMessage());
			      setErrorMessage(exception.getMessage());
			      RequestContext.getCurrentInstance().execute("error.show();");
			      return;
		    }

	}
	public void populateAccountNumberForPD()
	{
		  try{
		List<BankAccountDetails> ptabledata = fundEstimationService.getAccountNumber(getPdBankId(), getPdCurrencyId());
		int accNoSize=ptabledata.size();
		if(accNoSize<=1)
		{
			for (BankAccountDetails element : ptabledata) {			
				setFxDlSupplierAccNo(element.getFundGlno());
				setPdAccNoForUpdate(element.getBankAcctNo().toString());
				setFxDlSupplierAccNoForSave(element.getGlno());
			}
			setBooRenderPDAccNo(false);
			setBooRenderPDAccNoForUpdate(true);

		}else
		{
			setLstPdAccountNumber(ptabledata);
			setBooRenderPDAccNo(true);
			setBooRenderPDAccNoForUpdate(false);
		}
		  }catch (Exception exception) {
			      log.info("exception.getMessage():::::::::::::::::::::::::::::::" + exception.getMessage());
			      setErrorMessage(exception.getMessage());
			      RequestContext.getCurrentInstance().execute("error.show();");
			      return;
		    }

	}
	public void  selecteAccountNameForPD()
	{
		  try{
		List<BankAccountDetails> ptabledata = fundEstimationService.getAccountNumber(getPdBankId(), getPdCurrencyId());

		for (BankAccountDetails element : ptabledata) {
			//if(getFxDlSupplierAccNo().equals(new BigDecimal(element.getFundGlno())))
			if(getFxDealSdAcccNo().equals(element.getFundGlno()))
			{
				setFxDlSupplierAccNo(element.getFundGlno());
				setPdAccNoForUpdate(element.getBankAcctNo().toString());
				setFxDlSupplierAccNoForSave(element.getGlno());
			}

		}
		  }catch (Exception exception) {
			      log.info("exception.getMessage():::::::::::::::::::::::::::::::" + exception.getMessage());
			      setErrorMessage(exception.getMessage());
			      RequestContext.getCurrentInstance().execute("error.show();");
			      return;
		    }

	}
	public void sdBankFromOther()
	{
		  try{
		clearSDValuesForCurrencySelection();
		List<BankAccountDetails> pbankcurrency = fundEstimationService.getCurrencyOfBank(getSdBankId());
		int sdLstCurrencySize=pbankcurrency.size();
		if(sdLstCurrencySize==1)
		{
			for (BankAccountDetails element : pbankcurrency) {
				setSdCurrencyId(element.getFsCurrencyMaster().getCurrencyId());
				setSdCurrencyName(element.getFsCurrencyMaster().getCurrencyName());
			}
			setBooRenderSDCurrency(false);
			setBooRenderSDCurrencyForUpdate(true);
			setSdCalValueDate(null);
			setBooRenderValueDate(true);
			setBooRenderValueDateForEdit(false);
			populateAccountNumberForSD();


		}else if (sdLstCurrencySize == 0) {
			clearSDValuesForCurrencySelection();
			RequestContext.getCurrentInstance().execute("sdNoCurrency.show();");
			return;
		}
		else
		{
			setBooRenderSDCurrency(true);
			setBooRenderSDCurrencyForUpdate(false);
			setSdCurrencyListFromDB(pbankcurrency);
		}

		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/FxDealWithSupplier.xhtml");
		} catch (IOException exception) {
			  log.info("exception.getMessage():::::::::::::::::::::::::::::::" + exception.getMessage());
			      setErrorMessage(exception.getMessage());
		}
		    } catch (Exception exception) {
			      log.info("exception.getMessage():::::::::::::::::::::::::::::::" + exception.getMessage());
			      setErrorMessage(exception.getMessage());
			      RequestContext.getCurrentInstance().execute("error.show();");
			      return;
		    }

	}
	public void populateAccountNumberForSD()
	{

		/*List<BankAccountDetails> sdAccountDetails = fundEstimationService.getAccountNumber(getSdBankId(), getSdCurrencyId());

		for (BankAccountDetails element : sdAccountDetails)
		{
			setFxDlSalesAccNo(element.getFundGlno());
			String accountBanlance=fundEstimationService.getCurrentBalance(element.getFundGlno());
			if(accountBanlance!=null)
			{
				setSdBankBalance(new BigDecimal(accountBanlance));
			}else
			{
				RequestContext.getCurrentInstance().execute("sdNoBankBalance.show();");
				return;
			}

		}*/

		  try{
		if(getPdCurrencyId().compareTo(getSdCurrencyId())!=0){
			List<BankAccountDetails> stabledata = fundEstimationService.getAccountNumber(getSdBankId(), getSdCurrencyId());

			int accNoSize=stabledata.size();

			if(accNoSize<=1)
			{
				for (BankAccountDetails element : stabledata) {			
					// setFxDlSalesAccNo(new BigDecimal(element.getFundGlno()).toString());
					// setSdAccNoForUpdate(element.getBankAcctNo().toString());

					setFxDealSdAcccNo(element.getFundGlno());
					setFxDlSalesAccNo(element.getBankAcctNo().toString());
					setFxDlSupplierAccNoForSale(element.getGlno());
					//setFxDlSupplierAccNoForSave(element.getGlno());
					String accountBanlance=fundEstimationService.getCurrentBalance(element.getFundGlno());
					if(accountBanlance != null){
						BigDecimal accBalance=new BigDecimal(accountBanlance);
						if(accBalance.signum() != -1)
						{
							setSdBankBalance(accBalance);
						}else
						{
							if(new BigDecimal(sessionStateManage.getCurrencyId()).compareTo(getSdCurrencyId())!=0){
								setSdBankBalance(null);
								RequestContext.getCurrentInstance().execute("sdNoBankBalance.show();");
								return;
							}
						}
					}else{
						if(new BigDecimal(sessionStateManage.getCurrencyId()).compareTo(getSdCurrencyId())!=0){
							setSdBankBalance(null);
							RequestContext.getCurrentInstance().execute("sdNoBankBalance.show();");
							return;
						}
					}
				}
				setBooRenderSDAccNo(false);
				setBooRenderSDAccNoForUpdate(true);
				getAverageRateFromAccountBalance();
			}else
			{
				setBooRenderSDAccNo(true);
				setBooRenderSDAccNoForUpdate(false);
				setLstSdAccountNumber(stabledata);
			}
			//getAverageRateFromAccountBalance();
		}else{
			setSdCurrencyId(null);
			RequestContext.getCurrentInstance().execute("currencyCheck.show();");
		}
		   } catch (Exception exception) {
			      log.info("exception.getMessage():::::::::::::::::::::::::::::::" + exception.getMessage());
			      setErrorMessage(exception.getMessage());
			      RequestContext.getCurrentInstance().execute("error.show();");
			      return;
		    }

	}

	private void getAverageRateFromAccountBalance()
	{
		  try{
		AccountBalance accountBalance=fxDealwithSupplierService.getAverageRate(getFxDealSdAcccNo());

		if(accountBalance.getAverageRate()==null)
		{
			RequestContext.getCurrentInstance().execute("sdNoAverageRate.show();");
			return;
		}else
		{
			setSdAverageRate(accountBalance.getAverageRate());
			calculateSDLocalAmount();
		}
		  } catch (Exception exception) {
			      log.info("exception.getMessage():::::::::::::::::::::::::::::::" + exception.getMessage());
			      setErrorMessage(exception.getMessage());
			      RequestContext.getCurrentInstance().execute("error.show();");
			      return;
		    }


	}

	public void  selecteAccountNameForSD()
	{
		  try{
		List<BankAccountDetails> ptabledata = fundEstimationService.getAccountNumber(getSdBankId(), getSdCurrencyId());
		setSdBankBalance(null);
		for (BankAccountDetails element : ptabledata) {
			if(getFxDealSdAcccNo().equalsIgnoreCase(element.getFundGlno()))
			{
				// setFxDealSdAcccNo(new BigDecimal(element.getFundGlno()).toString());
				setSdAccNoForUpdate(element.getBankAcctNo().toString());
				setFxDlSupplierAccNoForSale(element.getGlno());
				String accountBanlance=fundEstimationService.getCurrentBalance(element.getFundGlno());
				BigDecimal accBalance=new BigDecimal(accountBanlance);
				if(accountBanlance!=null ){
					if(accBalance.signum() != -1)
					{
						setSdBankBalance(accBalance);
					}else
					{
						if(new BigDecimal(sessionStateManage.getCurrencyId()).compareTo(getSdCurrencyId())!=0){
							setSdBankBalance(null);
							RequestContext.getCurrentInstance().execute("sdNoBankBalance.show();");
							return;
						}
					}
				}else{
					if(new BigDecimal(sessionStateManage.getCurrencyId()).compareTo(getSdCurrencyId())!=0){
						setSdBankBalance(null);
						RequestContext.getCurrentInstance().execute("sdNoBankBalance.show();");
						return;
					}  
				}
				getAverageRateFromAccountBalance();
			}
		}
		  } catch (Exception exception) {
			      log.info("exception.getMessage():::::::::::::::::::::::::::::::" + exception.getMessage());
			      setErrorMessage(exception.getMessage());
			      RequestContext.getCurrentInstance().execute("error.show();");
			      return;
		    }
	}

	private void calculateSDLocalAmount() throws Exception
	{
		BigDecimal saleSdAvgRate=getSdAverageRate();
		BigDecimal saleSdAmount=getSdSaleAmount();
		BigDecimal pdFCAmt=getPdFCAmount();

		if(saleSdAvgRate!=null && saleSdAmount!=null && pdFCAmt!=null)
		{
			BigDecimal sdLocalAmt=saleSdAmount.multiply(saleSdAvgRate);

			setSdLocalAmount(round(sdLocalAmt,foreignLocalCurrencyDenominationService.getDecimalPerCurrency(getPdCurrencyId())));

			setPdLocalAmount(round(sdLocalAmt,foreignLocalCurrencyDenominationService.getDecimalPerCurrency(getPdCurrencyId())));

			BigDecimal pdLocalExchangerate=sdLocalAmt.divide(pdFCAmt,Integer.parseInt(Constants.FX_DEAL_WITH_SUPPLIER_DEAL_DECIMAL_PLACES),BigDecimal.ROUND_HALF_UP);
			setPdLocalExchangeRate(pdLocalExchangerate);
		}
	}
	public void onDateSelect(SelectEvent event) {

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

		setPdSaveValueDate(format.format(event.getObject()));

	}
	public void onDateSelectforSD(SelectEvent event) {

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

		setSdSaveValueDate(format.format(event.getObject()));

	}

	public void onDateSelectForDealDate(SelectEvent event) {

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String dealDate=format.format(event.getObject());

		try {
			setHdCalValueDate(format.parse(dealDate));
			setMinDealDate(format.parse(dealDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void resetFCAmount()
	{
		  try{
		setPdAmount(null);
		BigDecimal multipleDiv= getPdMultipleDivision();
		BigDecimal exchangeRate=getPdExchangeRate();
		BigDecimal fcAmount=getPdFCAmount();
		if(getPdCurrencyId()==null)
		{
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("currency.show();"); 
		}
		if(multipleDiv!=null && exchangeRate!=null && fcAmount!=null && getPdCurrencyId()!=null)
		{
			int decimalvalue = foreignLocalCurrencyDenominationService.getDecimalPerCurrency(getPdCurrencyId());
			if(multipleDiv.compareTo(new BigDecimal(1))==0)
			{
				BigDecimal amount=fcAmount.multiply(exchangeRate);

				setPdAmount(round(amount,decimalvalue));
				setSdSaleAmount(round(amount,decimalvalue));
			}
			if(multipleDiv.compareTo(new BigDecimal(2))==0)
			{

				BigDecimal amount=fcAmount.divide(exchangeRate,decimalvalue,BigDecimal.ROUND_HALF_UP);
				setPdAmount(round(amount,decimalvalue));
				setSdSaleAmount(round(amount,decimalvalue));
			}

		}
		calculateSDLocalAmount();
		  } catch (Exception exception) {
			      log.info("exception.getMessage():::::::::::::::::::::::::::::::" + exception.getMessage());
			      setErrorMessage(exception.getMessage());
			      RequestContext.getCurrentInstance().execute("error.show();");
			      return;
		    }
	}
	
	private String returnFormattedDate(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return simpleDateFormat.format(date);
	}
	
	public void saveAll()
	{
		setSuccessPanelRender(false);
		setMainPanelRender(true);
		if(getDocumentSerialforFxdeal() == null || getDocumentSerialforFxdeal().compareTo(BigDecimal.ZERO)==0){
			RequestContext.getCurrentInstance().execute("fxdealSupplieridexist.show();");
			return;
		}

		if(getPdCurrencyId().compareTo(getSdCurrencyId())==0)
		{
			RequestContext.getCurrentInstance().execute("currencyCheck.show();");
			return;
		}

		if(getPdPayableAccountNumber()==null)
		{
			RequestContext.getCurrentInstance().execute("payableAccNo.show();");
			return;
		}

		if(getFxDlSalesAccNo() ==null && getFxDealSdAcccNo() == null)
		{
			RequestContext.getCurrentInstance().execute("NosalesAccNo.show();");
			return;
		}

		if(getSdBankBalance()==null)
		{
			if(new BigDecimal(sessionStateManage.getCurrencyId()).compareTo(getSdCurrencyId())!=0){
				RequestContext.getCurrentInstance().execute("sdNoBankBalance.show();");
				return;
			}
		}

		if(getSdAverageRate()==null)
		{
			RequestContext.getCurrentInstance().execute("sdNoAverageRate.show();");
			return;
		}

		BigDecimal saveDocumentSerialID = getDocumentSerialID(Constants.U);
		if(saveDocumentSerialID == null || saveDocumentSerialID.compareTo(BigDecimal.ZERO)==0 ){
			RequestContext.getCurrentInstance().execute("fxdealSupplieridexist.show();");
			return;
		}

		BigDecimal saveDocumentSerialIDForSupplier = getDocumentSerialIDForSupplier(Constants.U);
		if(saveDocumentSerialIDForSupplier == null || saveDocumentSerialIDForSupplier.compareTo(BigDecimal.ZERO)==0 ){
			RequestContext.getCurrentInstance().execute("daybookdealidexist.show();");
			return;
		}
		
		// saving process 
		HashMap<String, Object> saveAllMap=  new HashMap<String, Object>();

		getDocumentDescriptionForDayBook();
		BigDecimal lineNumber=BigDecimal.ZERO;

		TreasuryDealHeader treasuryDealHeader = saveTreasuryDealHeader(saveDocumentSerialID,saveDocumentSerialIDForSupplier);
		saveAllMap.put("TreasuryDealHeader", treasuryDealHeader);

		lineNumber=lineNumber.add(new BigDecimal(1));
		TreasuryDealDetail treasuryDealDetailForPD = saveTreasuryDealDetailForPD(treasuryDealHeader, Constants.PD, saveDocumentSerialID,lineNumber);
		saveAllMap.put("TreasuryDealDetailForPD", treasuryDealDetailForPD);

		lineNumber=lineNumber.add(new BigDecimal(1));
		TreasuryDealDetail treasuryDealDetailForPY = saveTreasuryDealDetailForPY(treasuryDealHeader, Constants.PY, saveDocumentSerialID,lineNumber);
		saveAllMap.put("TreasuryDealDetailForPY", treasuryDealDetailForPY);


		DayBookHeader dayBookHeader = saveDayBookHeader(treasuryDealHeader , saveDocumentSerialIDForSupplier , saveDocumentSerialID );
		saveAllMap.put("DayBookHeader", dayBookHeader);
		lineNumber=BigDecimal.ZERO;
		
		lineNumber=lineNumber.add(new BigDecimal(1));
		DayBookDetails dayBookSdDetailsForPY= saveDayBookDetailsForPY(dayBookHeader, Constants.PY, saveDocumentSerialIDForSupplier,lineNumber);
		saveAllMap.put("DayBookDetailsForPY", dayBookSdDetailsForPY);

		lineNumber=lineNumber.add(new BigDecimal(1));
		DayBookDetails dayBookSdDetailsForSD=saveDayBookDetailsForSD(dayBookHeader, Constants.SD, saveDocumentSerialIDForSupplier,lineNumber);
		saveAllMap.put("DayBookDetailsForSD", dayBookSdDetailsForSD);

		try {
			fxDealwithSupplierService.saveAllFxDealWithSupplier(saveAllMap);
			
			try{
				fXDetailInformationService.callUnApproveProcedure(sessionStateManage.getCountryId(), sessionStateManage.getCompanyId(), getDocumentNo(), new BigDecimal(getFinaceYear()), getDocSerialIdNumberForSave());
			}catch (AMGException e) {
				setErrorMessage(e.getMessage());
				RequestContext.getCurrentInstance().execute("procedureException.show();");
				return;
			} catch (Exception e1) {
				setErrorMessage(e1.getMessage());
				RequestContext.getCurrentInstance().execute("procedureException.show();");
				return;
			}
			
			setSuccessPanelRender(true);
			setMainPanelRender(false);
		//	RequestContext.getCurrentInstance().execute("complete.show();");
			
		}catch (AMGException e) {
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("sqlexception.show();");
			return;
		} catch (Exception e1) {
			setErrorMessage("Saving Error : "+e1.getMessage());
			RequestContext.getCurrentInstance().execute("sqlexception.show();");
			return;
		}

		/*try {
			fXDetailInformationService.callUnApproveProcedure(sessionStateManage.getCountryId(), sessionStateManage.getCompanyId(), getDocumentNo(), new BigDecimal(getFinaceYear()), getDocSerialIdNumberForSave());
		} catch (AMGException e) {
			setErrorMessage("EX_TREASURY_UAPPV_UGL" + " : " +e.getMessage());
			RequestContext.getCurrentInstance().execute("sqlexception.show();");
		}*/

	}

	private DayBookHeader saveDayBookHeader(TreasuryDealHeader treasuryDealHeader , BigDecimal saveDocumentSerialIDForSupplier , BigDecimal saveDocumentSerialID)
	{
		DayBookHeader dayBookHeader=null;
		try {

			//setDocSerialIdNumberForSave(saveDocumentSerialID);


			dayBookHeader = new DayBookHeader();

			dayBookHeader.setDaybookHeaderId(getDaybookHeaderId());
			// save Company Master
			CompanyMaster companyMaster = new CompanyMaster();
			companyMaster.setCompanyId(getCompanyId());
			dayBookHeader.setDayBookCompanyMaster(companyMaster);
			dayBookHeader.setCurrencyId(getSdCurrencyId());

			CountryMaster daybookCountry = new CountryMaster();
			daybookCountry.setCountryId(sessionStateManage.getCountryId());
			dayBookHeader.setDayBookCountryMaster(daybookCountry);

			Document document = new Document();
			document.setDocumentID(getDocumentNoForDayBook());
			dayBookHeader.setDoucDocumentId(document);

			dayBookHeader.setDocumentNumber(saveDocumentSerialIDForSupplier);

			dayBookHeader.setDocumentFinancialYear(new BigDecimal(getFinaceYear()));

			if(getBooCheckValueDate().equals(true)){
				dayBookHeader.setDocumentDate(getSdCalValueDate());
			}else{
				dayBookHeader.setDocumentDate(getSdCalValueDate());	  
			}
			dayBookHeader.setAcyymm(new SimpleDateFormat("dd/MM/yyyy").parse(getCurrentDateWithFormat()));


			dayBookHeader.setDealedWithCustomer(getFxDlwithSupplierDealWith());
			dayBookHeader.setContact(getFxDlwithSupplierConttract());


			dayBookHeader.setConcludedBy(getFxDlSupplierConcludedby());
			//dayBookHeader.setReuterReference(getFxDlSupplierReutersRef());
			dayBookHeader.setRemarks(getFxDlSupplierRemarks());


			if (getDaybookHeaderId() != null
					&& getDaybookHeaderId().intValue() != 0) {

				dayBookHeader.setModifiedBy(sessionStateManage.getUserName());
				dayBookHeader.setModifiedDate(new Date());
				dayBookHeader.setIsActive(Constants.U);
				dayBookHeader.setCreatedBy(getCreatedBy());
				dayBookHeader.setCreatedDate(getCreatedDate());
			} else {
				dayBookHeader.setCreatedBy(sessionStateManage.getUserName());
				dayBookHeader.setCreatedDate(new Date());
				dayBookHeader.setIsActive(Constants.U);
			}
			//CR 13-12-2014
			dayBookHeader.setDealedWithCustomer(getCustomerReference()==null ?  "":  getCustomerReference().toPlainString());
			dayBookHeader.setCurrencyId(getSdCurrencyId());
			dayBookHeader.setFaAccountNo(getFxDlSalesAccNo()==null ? "":getFxDlSalesAccNo());
			dayBookHeader.setFcAmount(getSdSaleAmount());
			dayBookHeader.setExchangeRate(getSdAverageRate());
			dayBookHeader.setLocalAmount(getSdLocalAmount());
			dayBookHeader.setBranchNumber(new BigDecimal(sessionStateManage.getBranchId()));

			//CR Hard Coded  
			dayBookHeader.setSubledgerInd("1");
			dayBookHeader.setOpenItemId("1");
			dayBookHeader.setOpenItemRef(sessionStateManage.getCompanyId()+""+new BigDecimal(Constants.DOCUMENT_CODE_FOR_FX_DEAL_WITH_BANK)+""+treasuryDealHeader.getUserFinanceYear()+""+treasuryDealHeader.getTreasuryDocumentNumber());

			//ref numbers storing
			dayBookHeader.setRefCompanyId(sessionStateManage.getCompanyId());
			//CR Changed to Set orginal ID 
			dayBookHeader.setRefDocumentId(treasuryDealHeader.getExDocument().getDocumentID());
			dayBookHeader.setRefFinYear(treasuryDealHeader.getUserFinanceYear());
			dayBookHeader.setRefNumber(treasuryDealHeader.getTreasuryDocumentNumber());


			//fxDealwithSupplierService.saveOrUpdateDayBook(dayBookHeader);
		    } catch (Exception e1) {
			      setErrorMessage("Saving Error : " + e1.getMessage());
		    }

		return dayBookHeader;
	}

	private DayBookDetails saveDayBookDetailsForSD(DayBookHeader dayBookHeader ,String lineType,BigDecimal saveDocumentSerialID,BigDecimal lineNumber)
	{
		DayBookDetails dayBookSdDetails =null;
		try {
			dayBookSdDetails = new DayBookDetails();
			dayBookSdDetails.setDayBookDetailsId(getDayBookDetailsId());
			dayBookSdDetails.setDayBookAccyymm(new SimpleDateFormat("dd/MM/yyyy").parse(getCurrentDateWithFormat()));
			dayBookSdDetails.setDayBookHeaderId(dayBookHeader);

			BankMaster bankMaster = new BankMaster();
			bankMaster.setBankId(getSdBankId());
			dayBookSdDetails.setDayBookDetailsBankMaster(bankMaster);


			BankAccountDetails bankAccountDetails = new BankAccountDetails();
			BigDecimal bankaccountDeatailsId=null;
			//BigDecimal bankaccountDeatailsId= bankTransferService.getBankAccountDeatilsPk(getFxDlSalesAccNo());
			if(getFxDealSdAcccNo() != null){
				bankaccountDeatailsId= bankTransferService.getBankAccountDeatilsPk(getFxDealSdAcccNo());
			}else{
				bankaccountDeatailsId= bankTransferService.getBankAccountDeatilsPk(getFxDealSdAcccNo());  
			}
			if(bankaccountDeatailsId!=null){
				bankAccountDetails.setBankAcctDetId(bankaccountDeatailsId);
			}
			dayBookSdDetails.setDayBookDetailsBankAccountDetails(bankAccountDetails);

			// save Company Master
			CompanyMaster dayBkDlsSdcompanyMaster = new CompanyMaster();
			dayBkDlsSdcompanyMaster.setCompanyId(getCompanyId());
			dayBookSdDetails.setDayBookCompanyMaster(dayBkDlsSdcompanyMaster);

			CountryMaster daybookDlsSdCountry = new CountryMaster();
			daybookDlsSdCountry.setCountryId(sessionStateManage.getCountryId());
			dayBookSdDetails.setDayBookCountryMaster(daybookDlsSdCountry);

			CurrencyMaster dayBookDlsSdCurrencyMaster= new CurrencyMaster();
			dayBookDlsSdCurrencyMaster.setCurrencyId(getSdCurrencyId());
			dayBookSdDetails.setDayBookCurrencyId(dayBookDlsSdCurrencyMaster);
			dayBookSdDetails.setValueDate(new SimpleDateFormat("dd/MM/yyyy").parse(getSdSaveValueDate()));


			Document dayBkDlsSddocument = new Document();
			dayBkDlsSddocument.setDocumentID(getDocumentNoForDayBook());
			dayBookSdDetails.setDayBookDocumentId(dayBkDlsSddocument);

			if(getBooCheckValueDate().equals(true)){
				dayBookSdDetails.setDayBookdocumentDate(getSdCalValueDate());
			}else{
				dayBookSdDetails.setDayBookdocumentDate(getSdCalValueDate());	  
			}
			dayBookSdDetails.setDayBookDocumentNumber(saveDocumentSerialID);

			dayBookSdDetails.setDayBookExchangeRate(getSdAverageRate());

			if (getFxDlSupplierAccNoForSale() != null) {
				dayBookSdDetails.setDayBookFaAccountNumber(getFxDlSupplierAccNoForSale() == null ? "" : getFxDlSupplierAccNoForSale());
			} else if (getFxDealSdAcccNo() != null) {
				dayBookSdDetails.setDayBookFaAccountNumber(getFxDealSdAcccNo() == null ? "" : getFxDealSdAcccNo());
			}
			dayBookSdDetails.setDayBookFcAmount(getSdSaleAmount());//

			dayBookSdDetails.setDayBookFinanceYear(new BigDecimal(getFinaceYear()));

			dayBookSdDetails.setDayBookLocalAmount(getSdLocalAmount());//

			dayBookSdDetails.setDayBookLineNumber(lineNumber);//
			dayBookSdDetails.setDayBookLineType(lineType);//

			if (getDayBookDetailsId() != null
					&& getDayBookDetailsId().intValue() != 0) {

				dayBookSdDetails.setModifiedBy(sessionStateManage.getUserName());
				dayBookSdDetails.setModifiedDate(new Date());
				dayBookSdDetails.setCreatedBy(getCreatedBy());
				dayBookSdDetails.setCreatedDate(getCreatedDate());
			} else {
				dayBookSdDetails.setCreatedBy(sessionStateManage.getUserName());
				dayBookSdDetails.setCreatedDate(new Date());

			}
			//fxDealwithSupplierService.saveOrUpdateDayBook(dayBookSdDetails);

		} catch (Exception e1) {
			      setErrorMessage("Saving Error : " + e1.getMessage());
		}
		return dayBookSdDetails;
	}

	private DayBookDetails saveDayBookDetailsForPY(DayBookHeader dayBookHeader ,String lineType,BigDecimal saveDocumentSerialID ,BigDecimal lineNumber)
	{
		DayBookDetails dayBookSdDetails =null;
		try {
			dayBookSdDetails = new DayBookDetails();
			dayBookSdDetails.setDayBookDetailsId(getDayBookDetailsId());
			dayBookSdDetails.setDayBookAccyymm(new SimpleDateFormat("dd/MM/yyyy").parse(getCurrentDateWithFormat()));
			dayBookSdDetails.setDayBookHeaderId(dayBookHeader);

			// save Company Master
			CompanyMaster dayBkDlsSdcompanyMaster = new CompanyMaster();
			dayBkDlsSdcompanyMaster.setCompanyId(getCompanyId());
			dayBookSdDetails.setDayBookCompanyMaster(dayBkDlsSdcompanyMaster);

			CountryMaster daybookDlsSdCountry = new CountryMaster();
			daybookDlsSdCountry.setCountryId(sessionStateManage.getCountryId());
			dayBookSdDetails.setDayBookCountryMaster(daybookDlsSdCountry);

			CurrencyMaster dayBookDlsSdCurrencyMaster= new CurrencyMaster();
			dayBookDlsSdCurrencyMaster.setCurrencyId(getSdCurrencyId());
			dayBookSdDetails.setDayBookCurrencyId(dayBookDlsSdCurrencyMaster);
			dayBookSdDetails.setValueDate(new SimpleDateFormat("dd/MM/yyyy").parse(getSdSaveValueDate()));


			Document dayBkDlsSddocument = new Document();
			dayBkDlsSddocument.setDocumentID(getDocumentNoForDayBook());
			dayBookSdDetails.setDayBookDocumentId(dayBkDlsSddocument);


			dayBookSdDetails.setDayBookdocumentDate(getHdCalValueDate());
			dayBookSdDetails.setDayBookDocumentNumber(saveDocumentSerialID);

			dayBookSdDetails.setDayBookExchangeRate(getSdAverageRate());

			dayBookSdDetails.setDayBookFaAccountNumber(getPdPayableAccountNumber());

			dayBookSdDetails.setDayBookFcAmount(getSdSaleAmount());//

			dayBookSdDetails.setDayBookFinanceYear(new BigDecimal(getFinaceYear()));

			dayBookSdDetails.setDayBookLocalAmount(getSdLocalAmount());//

			dayBookSdDetails.setDayBookLineNumber(lineNumber);//
			dayBookSdDetails.setDayBookLineType(lineType);//

			if (getDayBookDetailsId() != null
					&& getDayBookDetailsId().intValue() != 0) {

				dayBookSdDetails.setModifiedBy(sessionStateManage.getUserName());
				dayBookSdDetails.setModifiedDate(new Date());
				dayBookSdDetails.setCreatedBy(getCreatedBy());
				dayBookSdDetails.setCreatedDate(getCreatedDate());
			} else {
				dayBookSdDetails.setCreatedBy(sessionStateManage.getUserName());
				dayBookSdDetails.setCreatedDate(new Date());

			}
			//fxDealwithSupplierService.saveOrUpdateDayBook(dayBookSdDetails);

		} catch (Exception e1) {
			      setErrorMessage("Saving Error : " + e1.getMessage());
		}
		return dayBookSdDetails;
	}

	private TreasuryDealHeader saveTreasuryDealHeader(BigDecimal saveDocumentSerialID,BigDecimal saveDayBookDocNumber)
	{
		TreasuryDealHeader treasuryDealHeader=null;
		try {

			setDocSerialIdNumberForSave(saveDocumentSerialID);

			treasuryDealHeader = new TreasuryDealHeader();
			
			treasuryDealHeader.setTreasuryDealHeaderId(getTeasuryDealHeaderId());

			// Save Application Country
			CountryMaster applicationCountry = new CountryMaster();
			applicationCountry.setCountryId(sessionStateManage.getCountryId());
			treasuryDealHeader.setFsCountryMaster(applicationCountry);

			// save Company Master
			CompanyMaster companyMaster = new CompanyMaster();
			companyMaster.setCompanyId(getCompanyId());
			treasuryDealHeader.setFsCompanyMaster(companyMaster);

			// save Document
			Document document = new Document();
			document.setDocumentID(getDocumentNo());
			treasuryDealHeader.setExDocument(document);
			
			// save bank id
			//BankMaster bankMaster = new BankMaster();
			//bankMaster.setBankId(getPdBankId());
			//treasuryDealHeader.setExBankMaster(bankMaster);

			// save Document Financial year
			treasuryDealHeader.setUserFinanceYear(new BigDecimal(getFinaceYear()));
			treasuryDealHeader.setTreasuryDocumentNumber(saveDocumentSerialID);
			
			if(getBooCheckValueDate().equals(true)){
				treasuryDealHeader.setDocumentDate(getHdCalValueDateEdit());
			}else{
				treasuryDealHeader.setDocumentDate(getHdCalValueDate());    
			}
			treasuryDealHeader.setAccyymm(new SimpleDateFormat("dd/MM/yyyy").parse(getCurrentDateWithFormat()));
			treasuryDealHeader.setRemarks(getFxDlSupplierRemarks());

			treasuryDealHeader.setValueDate(getHdCalValueDate());

			// save Language Type
			LanguageType langType = new LanguageType();
			langType.setLanguageId(sessionStateManage.getLanguageId());
			treasuryDealHeader.setFsLanguageType(langType);

			treasuryDealHeader.setPaymentVoucherCompanyId(getCompanyId());
			treasuryDealHeader.setPaymentVoucherFinanceyYear(new BigDecimal(getFinaceYear()));
			treasuryDealHeader.setPaymentVoucherId(getDocumentNoForDayBook());
			treasuryDealHeader.setPaymentVoucherNumber(saveDayBookDocNumber);

			if (getTeasuryDealHeaderId() != null && getTeasuryDealHeaderId().intValue() != 0) {
				treasuryDealHeader.setModifiedBy(sessionStateManage.getUserName());
				treasuryDealHeader.setModifiedDate(new Date());
				treasuryDealHeader.setCreatedBy(getCreatedBy());
				treasuryDealHeader.setCreatedDate(getCreatedDate());
				treasuryDealHeader.setIsActive(Constants.U);
			} else {
				treasuryDealHeader.setCreatedBy(sessionStateManage.getUserName());
				treasuryDealHeader.setCreatedDate(new Date());
				treasuryDealHeader.setIsActive(Constants.U);
			}

			treasuryDealHeader.setContactName(getFxDlwithSupplierConttract());
			treasuryDealHeader.setConcludedBy(getFxDlSupplierConcludedby());
			//treasuryDealHeader.setReutersReference(getFxDlSupplierReutersRef());
			treasuryDealHeader.setMultiplicationDivision(getPdMultipleDivision().toPlainString());
			treasuryDealHeader.setPurchaseExchangeRate(getPdExchangeRate());
			treasuryDealHeader.setPurchaseLocalRate(getPdLocalExchangeRate());
			treasuryDealHeader.setTotalPurchaseLocalAmt(getPdLocalAmount());
			treasuryDealHeader.setTotalPurchaseFCAmt(getPdFCAmount());
			treasuryDealHeader.setDealWithCustomer(getCustomerReference());
			treasuryDealHeader.setSaleAmount(getPdAmount());
			treasuryDealHeader.setCountryBranchId(new BigDecimal(sessionStateManage.getBranchId()));
			treasuryDealHeader.setDealWithType(Constants.Fx_SupplierDealType);
			
			//fXDetailInformationService.saveHeader(treasuryDealHeader);
		} catch (Exception e1) {
			      setErrorMessage("Saving Error : " + e1.getMessage());
		}
		return treasuryDealHeader;
	}

	private TreasuryDealDetail saveTreasuryDealDetailForPD(TreasuryDealHeader treasuryDealHeader,String lineType,BigDecimal saveDocumentSerialID,BigDecimal lineNumber)
	{
		TreasuryDealDetail treasuryDealDetail=null;
		try {
			
			treasuryDealDetail = new TreasuryDealDetail();
			
			treasuryDealDetail.setTreasuryDealDetailId(getTeasuryDealDetailId());

			//save Company
			CompanyMaster companyMaster = new CompanyMaster();
			companyMaster.setCompanyId(getCompanyId());
			treasuryDealDetail.setTreasuryDealCompanyMaster(companyMaster);

			//save Country Master
			CountryMaster applicationCountry = new CountryMaster();
			applicationCountry.setCountryId(sessionStateManage.getCountryId());
			treasuryDealDetail.setTreasuryDealCountryMaster(applicationCountry);

			//save Document
			Document document = new Document();
			document.setDocumentID(getDocumentNo());
			treasuryDealDetail.setTreasuryDealDocument(document);

			treasuryDealDetail.setTreasuryDealHeader(treasuryDealHeader);
			
			//save Bank 
			BankMaster bankMaster = new BankMaster();
			bankMaster.setBankId(getPdBankId());
			treasuryDealDetail.setTreasuryDealBankMaster(bankMaster);
			
			//save Currency
			CurrencyMaster currencyMaster = new CurrencyMaster();
			currencyMaster.setCurrencyId(getPdCurrencyId());
			treasuryDealDetail.setTreasuryDealDetailCurrencyMaster(currencyMaster);
			
			//Save Account Number
			BankAccountDetails bankAccountDetails = new BankAccountDetails();
			System.out.println("getFxDlSupplierAccNo():::::::::::::::::::"+getFxDlSupplierAccNo());
			BigDecimal bankaccountDeatailsId= bankTransferService.getBankAccountDeatilsPk(getFxDlSupplierAccNo()); 
			if(bankaccountDeatailsId!=null){
				bankAccountDetails.setBankAcctDetId(bankaccountDeatailsId);
			}
			treasuryDealDetail.setTreasuryDealDetailBankAccountDetails(bankAccountDetails);

			BigDecimal pdLocalAmt=getPdFCAmount().multiply(getPdLocalExchangeRate());
			BigDecimal roundPdLocalAmt =round(pdLocalAmt,foreignLocalCurrencyDenominationService.getDecimalPerCurrency(getPdCurrencyId()));
			treasuryDealDetail.setLocalAmount(roundPdLocalAmt);

			//treasuryDealDetail.setLocalAmount(getPdFCAmount().multiply(getPdLocalExchangeRate()));

			treasuryDealDetail.setTreasuryDealUserFinanceYear(new BigDecimal(getFinaceYear()));
			treasuryDealDetail.setDocumentNumber(saveDocumentSerialID);
			treasuryDealDetail.setLineType(lineType);

			if(lineType.equalsIgnoreCase(Constants.PD))
			{
				treasuryDealDetail.setExchange(getPdExchangeRate());
				treasuryDealDetail.setSaleAmount(getPdAmount());
				treasuryDealDetail.setLocalExchangeRate(getPdLocalExchangeRate());
				treasuryDealDetail.setFcAmount(getPdFCAmount());
			}

			if(getTeasuryDealDetailId()!=null && getTeasuryDealDetailId().intValue()!=0){
				treasuryDealDetail.setModifiedBy(sessionStateManage.getUserName());
				treasuryDealDetail.setModifiedDate(new Date());
				treasuryDealDetail.setCreatedBy(getCreatedBy());
				treasuryDealDetail.setCreatedDate(getCreatedDate());
				treasuryDealDetail.setIsActive(Constants.U);

			}else{
				treasuryDealDetail.setCreatedBy(sessionStateManage.getUserName());
				treasuryDealDetail.setCreatedDate(new Date());
				treasuryDealDetail.setIsActive(Constants.U);
			}

			treasuryDealDetail.setValueDate(getPdCalValueDate());
			treasuryDealDetail.setLineNumber(lineNumber);

			treasuryDealDetail.setMultiplicationDivision(getPdMultipleDivision().toPlainString());

			if(getFxDlSupplierAccNoForSave() != null){
				treasuryDealDetail.setFaAccountNo(getFxDlSupplierAccNoForSave());
			}else{
				treasuryDealDetail.setFaAccountNo(getFxDealSdAcccNo());	  
			}
			treasuryDealDetail.setSubLedgerInd(Constants.Yes);
			treasuryDealDetail.setOpenItemId(Constants.Yes);
			treasuryDealDetail.setCustomerReference(getCustomerReference());

			//fXDetailInformationService.savePurchase(treasuryDealDetail);

		} catch (Exception e1) {
			      setErrorMessage("Saving Error : " + e1.getMessage());
		}
		return treasuryDealDetail;
	}


	private TreasuryDealDetail saveTreasuryDealDetailForPY(TreasuryDealHeader treasuryDealHeader,String lineType,BigDecimal saveDocumentSerialID,BigDecimal lineNumber)
	{
		TreasuryDealDetail treasuryDealDetail = null;
		try {
			
			treasuryDealDetail = new TreasuryDealDetail();

			treasuryDealDetail.setTreasuryDealDetailId(getTeasuryDealDetailId());
			
			CompanyMaster companyMaster = new CompanyMaster();
			companyMaster.setCompanyId(getCompanyId());
			treasuryDealDetail.setTreasuryDealCompanyMaster(companyMaster);

			CountryMaster applicationCountry = new CountryMaster();
			applicationCountry.setCountryId(sessionStateManage.getCountryId());
			treasuryDealDetail.setTreasuryDealCountryMaster(applicationCountry);

			Document document = new Document();
			document.setDocumentID(getDocumentNo());
			treasuryDealDetail.setTreasuryDealDocument(document);
			
			treasuryDealDetail.setTreasuryDealHeader(treasuryDealHeader);
			
			//save Bank 
			/*BankMaster bankMaster = new BankMaster();
			bankMaster.setBankId(getSdBankId());
			treasuryDealDetail.setTreasuryDealBankMaster(bankMaster);*/
			
			//save Currency
			CurrencyMaster currencyMaster = new CurrencyMaster();
			currencyMaster.setCurrencyId(getSdCurrencyId());
			treasuryDealDetail.setTreasuryDealDetailCurrencyMaster(currencyMaster);
			
			/*//Save Account Number
			BankAccountDetails bankAccountDetails = new BankAccountDetails();
			BigDecimal bankaccountDeatailsId= bankTransferService.getBankAccountDeatilsPk(getFxDlSupplierAccNo().toPlainString());
			if(bankaccountDeatailsId!=null){
			bankAccountDetails.setBankAcctDetId(bankaccountDeatailsId);
			}
			treasuryDealDetail.setTreasuryDealDetailBankAccountDetails(bankAccountDetails);*/
			
			BigDecimal sdLocalAmt=getPdFCAmount().multiply(getPdLocalExchangeRate());
			BigDecimal roundSdLocalAmt =round(sdLocalAmt,foreignLocalCurrencyDenominationService.getDecimalPerCurrency(getSdCurrencyId()));
			treasuryDealDetail.setLocalAmount(roundSdLocalAmt);

			treasuryDealDetail.setTreasuryDealUserFinanceYear(new BigDecimal(getFinaceYear()));

			treasuryDealDetail.setDocumentNumber(saveDocumentSerialID);
			treasuryDealDetail.setLineType(lineType);
			treasuryDealDetail.setExchange(getSdAverageRate());
			treasuryDealDetail.setAvgRate(getSdAverageRate());
			treasuryDealDetail.setSaleAmount(getSdSaleAmount());
			treasuryDealDetail.setLocalExchangeRate(getSdAverageRate());
			treasuryDealDetail.setFcAmount(getSdSaleAmount());

			/*if(lineType.equalsIgnoreCase(Constants.PD))
			{
				treasuryDealDetail.setExchange(getPdExchangeRate());
				treasuryDealDetail.setSaleAmount(getPdAmount());
				treasuryDealDetail.setLocalExchangeRate(getPdLocalExchangeRate());
				treasuryDealDetail.setFcAmount(getPdFCAmount());
			}else
			{
				treasuryDealDetail.setExchange(getPdLocalExchangeRate());
				treasuryDealDetail.setAvgRate(getSdAverageRate());
				treasuryDealDetail.setSaleAmount(getSdSaleAmount());
				treasuryDealDetail.setLocalExchangeRate(getSdAverageRate());
				treasuryDealDetail.setFcAmount(getSdSaleAmount());
			}*/


			if(getTeasuryDealDetailId()!=null && getTeasuryDealDetailId().intValue()!=0){
				treasuryDealDetail.setModifiedBy(sessionStateManage.getUserName());
				treasuryDealDetail.setModifiedDate(new Date());
				treasuryDealDetail.setCreatedBy(getCreatedBy());
				treasuryDealDetail.setCreatedDate(getCreatedDate());

			}else{
				treasuryDealDetail.setCreatedBy(sessionStateManage.getUserName());
				treasuryDealDetail.setCreatedDate(new Date());
			}

			treasuryDealDetail.setValueDate(getPdCalValueDate());
			treasuryDealDetail.setLineNumber(lineNumber);
			treasuryDealDetail.setIsActive(Constants.U);
			treasuryDealDetail.setMultiplicationDivision(getPdMultipleDivision().toPlainString());
			//treasuryDealDetail.setAvgRate(getSdAverageRate());

			treasuryDealDetail.setFaAccountNo(getPdPayableAccountNumber());
			treasuryDealDetail.setSubLedgerInd(Constants.Yes);
			treasuryDealDetail.setOpenItemId(Constants.Yes);
			treasuryDealDetail.setCustomerReference(getCustomerReference());

			//fXDetailInformationService.savePurchase(treasuryDealDetail);

		} catch (Exception e1) {
			      setErrorMessage("Saving Error : " + e1.getMessage());
		}
		return treasuryDealDetail;
	}

	public BigDecimal getDocumentSerialID(String processIn){

		/*String documentSerialID = igeneralService.getNextDocumentReferenceNumber(sessionStateManage.getCountryId().intValue() ,sessionStateManage.getCompanyId().intValue(), Integer.parseInt(Constants.DOCUMENT_CODE_FOR_FX_DEAL_WITH_BANK), finaceYear,processIn,sessionStateManage.getCountryBranchCode());
		return documentSerialID;*/
		String documentSerialID=null;
		try {
			HashMap<String, String> outPutValues= igeneralService.getNextDocumentReferenceNumbers(Integer.parseInt(sessionStateManage.getSessionValue("countryId")), Integer.parseInt(sessionStateManage.getSessionValue("companyId")), Integer.parseInt(Constants.DOCUMENT_CODE_FOR_FX_DEAL_WITH_BANK), finaceYear,
					processIn,sessionStateManage.getCountryBranchCode());


			//String proceErrorFlag=outPutValues.get("PROCE_ERORRFLAG");
			String proceErrorMsg=outPutValues.get("PROCE_ERORRMSG");
			if(proceErrorMsg!=null)
			{
				setErrmsg(proceErrorMsg);
				setBooDocVisble(true);
				RequestContext.getCurrentInstance().execute("proceErr.show();");
				return new BigDecimal(0);
			}else
			{
				documentSerialID=outPutValues.get("DOCNO");
				setDocumentSerialforFxdeal(new BigDecimal(documentSerialID));
			}
		} catch (NumberFormatException | AMGException e) {
			setErrmsg(e.getMessage());
			setBooDocVisble(true);
			RequestContext.getCurrentInstance().execute("proceErr.show();");
			if(documentSerialID != null){
				return new BigDecimal(documentSerialID);    
			}
		}
		if(documentSerialID != null){
			return new BigDecimal(documentSerialID);    
		}else{
			return new BigDecimal(0);
		}

	}

	public BigDecimal getDocumentSerialIDForSupplier(String processIn){

		String documentSerialID=null;
		try {
			HashMap<String, String> outPutValues= igeneralService.getNextDocumentReferenceNumbers(Integer.parseInt(sessionStateManage.getSessionValue("countryId")), Integer.parseInt(sessionStateManage.getSessionValue("companyId")), Integer.parseInt(Constants.FX_DEAL_WITH_DAY_BOOK), finaceYear,
					processIn,sessionStateManage.getCountryBranchCode());

			String proceErrorMsg=outPutValues.get("PROCE_ERORRMSG");
			if(proceErrorMsg!=null)
			{
				setErrmsg(proceErrorMsg);
				setBooDocVisble(true);
				RequestContext.getCurrentInstance().execute("proceErr.show();");
				return new BigDecimal(0);
			}else
			{
				documentSerialID=outPutValues.get("DOCNO");
			}
		} catch (NumberFormatException | AMGException e) {
			setErrmsg(e.getMessage());
			setBooDocVisble(true);
			RequestContext.getCurrentInstance().execute("proceErr.show();");
			if(documentSerialID != null){
				return new BigDecimal(documentSerialID);    
			}
		}
		if(documentSerialID != null){
			return new BigDecimal(documentSerialID);    
		}else{
			return new BigDecimal(0);
		}
	}

	// to get the accoMMYY value
	@Deprecated
	public static String getCurrentDateWithFormat() {
		Map<Integer, String> data = new HashMap<Integer, String>();
		for (int i = 0; i < 12; i++) {
			if (i < 9) {
				data.put(i, "0" + String.valueOf(i + 1));
			} else {
				data.put(i, String.valueOf(i + 1));
			}
		}

		//String year = String.valueOf(new Date().getYear()).substring(1, 3);
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		String year =String.valueOf(calendar.get(Calendar.YEAR));

		System.out.println(Calendar.getInstance().get(Calendar.MONTH));
		return "01/" + data.get(Calendar.getInstance().get(Calendar.MONTH)) + "/" + year;
	}		
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void clearCache()
	{

		setMainPanelRender(true);
		setSuccessPanelRender(false);
		//render
		setCustomerReference(null);
		setBooRenderCurrencyId(true);
		setBooRenderCurrencyIdForUpdate(false);
		setBooRenderPDAccNo(true);
		setBooRenderPDAccNoForUpdate(false);
		setBooRenderSDCurrency(true);
		setBooRenderSDCurrencyForUpdate(false);
		setBooRenderSDAccNo(true);
		setBooRenderPDAccNoForUpdate(false);
		//render
		clearHeaderValues();
		clearPDValues();
		clearSDValues();
		setPdPayableAccountNumber(null);
		if(currencyListFromDB!=null)
		{
			currencyListFromDB.clear();
		}
		if(sdCurrencyListFromDB!=null)
		{
			sdCurrencyListFromDB.clear();
		}
		getPayableAccountNumber();
		setMinDealDate(new Date());
		userFinanceYear();
		// new cr added
		setBoofxDealWithRef(true);
		setBoofxDealwithSupplierEditableRef(false);
		setBooRenderOffEditIcon(true);
		setBooSaveOrExit(true);
		setBooUpdateOrCancel(false);
		setBooReadOnlyForEdit(false);
		setBooReadOnlyForEdit(false);
		setTreasuryDealDetailPYPK(null);
		setDayBookDetailsForPYPK(null);
		setTeasuryDealHeaderId(null);
		setTeasuryDealDetailId(null);
		setDaybookHeaderId(null);
		setDayBookDetailsId(null);
		setHdCalValueDateEdit(null);
		setBooRenderValueDate(true);
		setBooRenderValueDateForEdit(false);
		setBooCheckValueDate(false);
		setBooRenderSDAccNo(false);
		setBooRenderSDAccNoForUpdate(true);
		setFirstName(null);
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "FxDealWithSupplier.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/FxDealWithSupplier.xhtml");
			getDocumentSerialID(processIn);
			getDocumentSerialIDForSupplier(processIn);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void userFinanceYear(){
		try{
			List<UserFinancialYear> financialYearList = igeneralService.getDealYear(new Date());
			log.info("financialYearList :"+financialYearList.size());
			if(financialYearList!=null && financialYearList.size()>0)
				finaceYear = Integer.parseInt(financialYearList.get(0).getFinancialYear().toString());
			financialYearId=financialYearList.get(0).getFinancialYearID();
			setFinancialYearId(financialYearId);
			setFinaceYear(finaceYear);
		}catch(Exception exception){
			  log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			  setErrorMessage(exception.getMessage()); 
			  RequestContext.getCurrentInstance().execute("error.show();");
			  return;       
			  }
	}
	public void clearHeaderValues()
	{
		setDocumentSerialforFxdeal(null);
		setDocumentSerialforDayBook(null);
		setFxDlwithSupplierDealWith(null);
		setFxDlwithSupplierConttract(null);
		setFxDlSupplierConcludedby(null);
		setFxDlSupplierReutersRef(null);
		setFxDlSupplierRemarks(null);
		setSundryDebtorReference(null);
		setFirstName(null);
	}
	public void clearPDValues()
	{
		setPdBankId(null);
		setPdCurrencyId(null);
		setPdCurrencyIdUpdate(null);
		setPdAccNoForUpdate(null);
		setFxDlSupplierAccNo(null);
		setPdSaveValueDate(null);
		setPdExchangeRate(null);
		setPdMultipleDivision(null);
		setPdFCAmount(null);
		setPdAmount(null);
		setPdLocalExchangeRate(null);
		setPdLocalAmount(null);
		setPdCalValueDate(null);
		lstPdAccountNumber.clear();
	}

	public void clearPDValuesForBankSelection()
	{
		setPdCurrencyId(null);
		setPdCurrencyIdUpdate(null);
		setPdAccNoForUpdate(null);
		setFxDlSupplierAccNo(null);
		setPdSaveValueDate(null);
		setPdExchangeRate(null);
		setPdMultipleDivision(null);
		setPdFCAmount(null);
		setPdAmount(null);
		setPdLocalExchangeRate(null);
		setPdLocalAmount(null);
		setPdCalValueDate(null);
		setSdCurrencyName(null);
		if(currencyListFromDB!=null)
		{
			currencyListFromDB.clear();
		}
	}

	public void clearSDValues()
	{
		setSdBankId(null);
		setSdCurrencyId(null);
		setFxDlSalesAccNo(null);
		setSdSaveValueDate(null);
		setSdBankBalance(null);
		setSdSaleAmount(null);
		setSdAverageRate(null);
		setSdLocalAmount(null);
		setSdCalValueDate(null);
		setSdCurrencyName(null);
		setFxDlSalesAccNo(null);
	}
	public void clearSDValuesForBankSelection()
	{
		setSdCurrencyId(null);
		setFxDlSalesAccNo(null);
		setSdSaveValueDate(null);
		setSdBankBalance(null);
		setSdAverageRate(null);
		setSdLocalAmount(null);
		setSdCalValueDate(null);
		if(sdCurrencyListFromDB!=null)
		{
			sdCurrencyListFromDB.clear();
		}
	}

	public void clearSDValuesForCurrencySelection()
	{
		
		setFxDlSalesAccNo(null);
		setSdBankBalance(null);
		setSdAverageRate(null);
		setSdLocalAmount(null);
		setSdCalValueDate(null);
		setSdCurrencyName(null);
		setSdCurrencyId(null);
		setSdCurrencyListFromDB(null);
		//setFxDealSdAcccNo(null);
		setFxDlSalesAccNo(null);
		setSdCalValueDate(null);
		lstSdAccountNumber.clear();
		if(sdCurrencyListFromDB!=null)
		{
			sdCurrencyListFromDB.clear();
		}


	}

	public void calCulateAmount()
	{
		  try{
		setPdAmount(null);
		BigDecimal multipleDiv= getPdMultipleDivision();
		BigDecimal exchangeRate=getPdExchangeRate();
		BigDecimal fcAmount=getPdFCAmount();
		if(multipleDiv==null)
		{
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("multi.show();"); 
		}
		if(getPdCurrencyId()==null)
		{
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("currency.show();"); 
		}
		if(multipleDiv!=null && exchangeRate!=null && fcAmount!=null && getPdCurrencyId()!=null)
		{
			int decimalvalue = foreignLocalCurrencyDenominationService.getDecimalPerCurrency(getPdCurrencyId());
			if(multipleDiv.compareTo(new BigDecimal(1))==0)
			{
				BigDecimal amount=fcAmount.multiply(exchangeRate);

				setPdAmount(round(amount,decimalvalue));
				setSdSaleAmount(round(amount,decimalvalue));
			}
			if(multipleDiv.compareTo(new BigDecimal(2))==0)
			{
				BigDecimal amount=fcAmount.divide(exchangeRate,decimalvalue,BigDecimal.ROUND_HALF_UP);
				setPdAmount(round(amount,decimalvalue));
				setSdSaleAmount(round(amount,decimalvalue));
			}

		}
		calculateSDLocalAmount();
		  }catch(Exception exception){
			    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			    setErrorMessage(exception.getMessage()); 
			    RequestContext.getCurrentInstance().execute("error.show();");
			    return;       
			    }
	}
	public void calCulateLocalAmt()
	{
		  try{
		setPdLocalAmount(null);
		BigDecimal multipleDiv= getPdMultipleDivision();

		BigDecimal localExchangeRate=getPdLocalExchangeRate();
		BigDecimal pdAmount=getPdAmount();


		if(multipleDiv!=null && localExchangeRate!=null && pdAmount!=null && getPdCurrencyId()!=null)
		{
			if(multipleDiv.compareTo(new BigDecimal(1))==0)
			{
				BigDecimal amount=pdAmount.multiply(localExchangeRate);

				setPdLocalAmount(round(amount,foreignLocalCurrencyDenominationService.getDecimalPerCurrency(getPdCurrencyId())));
			}
			if(multipleDiv.compareTo(new BigDecimal(2))==0)
			{
				BigDecimal amount=pdAmount.divide(localExchangeRate,BigDecimal.ROUND_HALF_UP);
				setPdLocalAmount(round(amount,foreignLocalCurrencyDenominationService.getDecimalPerCurrency(getPdCurrencyId())));
			}

		}
		  }catch(Exception exception){
			    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			    setErrorMessage(exception.getMessage()); 
			    RequestContext.getCurrentInstance().execute("error.show();");
			    return;       
			    }
	} 
	public void calculateSaleLocalAmt()
	{
		  try{
		BigDecimal saleSdAvgRate=getSdAverageRate();
		BigDecimal saleSdAmount=getSdSaleAmount();
		BigDecimal pdFCAmt=getPdFCAmount();
		if(saleSdAvgRate!=null && saleSdAmount!=null && pdFCAmt!=null)
		{
			BigDecimal sdLocalAmt=saleSdAmount.multiply(saleSdAvgRate);

			setSdLocalAmount(round(sdLocalAmt,foreignLocalCurrencyDenominationService.getDecimalPerCurrency(getPdCurrencyId())));

			BigDecimal pdLocalExchangerate=pdFCAmt.divide(saleSdAmount,BigDecimal.ROUND_HALF_UP);
			setPdLocalExchangeRate(round(pdLocalExchangerate,3));//foreignLocalCurrencyDenominationService.getDecimalPerCurrency(getPdCurrencyId())

			BigDecimal pdAmount=getPdAmount();
			BigDecimal pdLocalAmount=pdFCAmt.multiply(pdLocalExchangerate);
			setPdLocalAmount(round(pdLocalAmount,foreignLocalCurrencyDenominationService.getDecimalPerCurrency(getPdCurrencyId())));
		}
		  }catch(Exception exception){
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

	public void exit1() throws IOException {
		if(sessionStateManage.getRoleId().equalsIgnoreCase("1")){
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		}else{
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}
	}
	public void  cancelSelection()
	{
		clearCache();

	}
	private BigDecimal accBal;

	public BigDecimal getAccBal() {
		return accBal;
	}

	public void setAccBal(BigDecimal accBal) {
		this.accBal = accBal;
	}

	public void checkFCAmount(AjaxBehaviorEvent event) {
		  try{
		setPdAmount(null);
		BigDecimal multipleDiv= getPdMultipleDivision();
		BigDecimal exchangeRate=getPdExchangeRate();
		BigDecimal fcAmount=getPdFCAmount();
		if(getPdCurrencyId()==null)
		{
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("currency.show();"); 
		}
		if(multipleDiv!=null && exchangeRate!=null && fcAmount!=null && getPdCurrencyId()!=null)
		{
			int decimalvalue = foreignLocalCurrencyDenominationService.getDecimalPerCurrency(getPdCurrencyId());
			if(multipleDiv.compareTo(new BigDecimal(1))==0)
			{
				BigDecimal amount=fcAmount.multiply(exchangeRate);

				setPdAmount(round(amount,decimalvalue));
				setSdSaleAmount(round(amount,decimalvalue));
			}
			if(multipleDiv.compareTo(new BigDecimal(2))==0)
			{

				BigDecimal amount=fcAmount.divide(exchangeRate,decimalvalue,BigDecimal.ROUND_HALF_UP);
				setPdAmount(round(amount,decimalvalue));
				setSdSaleAmount(round(amount,decimalvalue));
			}

		}
		calculateSDLocalAmount();
		  }catch(Exception exception){
			    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			    setErrorMessage(exception.getMessage()); 
			    RequestContext.getCurrentInstance().execute("error.show();");
			    return;       
			    }

	}
	private Date effectiveMinDate = new Date();
	private Date effectiveMaxDate = new Date();;

	public Date getEffectiveMinDate() {
		return effectiveMinDate;
	}

	public void setEffectiveMinDate(Date effectiveMinDate) {
		this.effectiveMinDate = effectiveMinDate;
	}

	public Date getEffectiveMaxDate() {

		Date now = new Date();

		Calendar cal = Calendar.getInstance();

		cal.setTime(now);

		cal.add(Calendar.DAY_OF_YEAR, Integer.parseInt(Constants.FX_DEAL_WITH_SUPPLIER_DEAL_DATE_ALLOW));

		Date tomorrow = cal.getTime();
		effectiveMaxDate=tomorrow;
		return effectiveMaxDate;
	}

	public void setEffectiveMaxDate(Date effectiveMaxDate) {
		this.effectiveMaxDate = effectiveMaxDate;
	}

	public void completed()
	{
		clearCache();
	}

	//added Koti to get the auto complete List and populating the values 26/02/2015
	private BigDecimal CustomerReference;

	public BigDecimal getCustomerReference() {
		return CustomerReference;
	}

	public void setCustomerReference(BigDecimal customerReference) {
		CustomerReference = customerReference;
	}

	private List<Customer> clist = new ArrayList<Customer>();

	public List<Customer> getClist() {
		return clist;
	}

	public void setClist(List<Customer> clist) {
		this.clist = clist;
	}

	private String firstName;
	private String lastName;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	private String sundryDebtorReference;

	public String getSundryDebtorReference() {
		return sundryDebtorReference;
	}

	public void setSundryDebtorReference(String sundryDebtorReference) {
		this.sundryDebtorReference = sundryDebtorReference;
	}

	@Autowired
	IFXDealSupplierService<T> fxDealSupplierService;

	public List<String> autoComplteList(String query) {
		  try{
		List<Customer> custList = new ArrayList<Customer>();
		List<String> finalList = new ArrayList<String>();
		custList = (List<Customer>) fxDealSupplierService.getAllComponentOreder(query);
		for (Customer customerlist : custList) {
			finalList.add(customerlist.getCustomerReference().toPlainString());
		}
		HashSet<String> hs = new HashSet<String>();
		hs.addAll(finalList);
		finalList.clear();
		finalList.addAll(hs);
		if(custList.size()>0){
			return finalList;
		}else{
			return null;
		}
		  }catch(Exception exception){
			  log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			  setErrorMessage(exception.getMessage()); 
			  RequestContext.getCurrentInstance().execute("error.show();");
			  return null;       
			  }

	}

	@SuppressWarnings("unused")
	public void popup() {
		  try{
		//setPdPayableAccountNumber(getCustomerReference());
		clist.clear();

		clist = fxDealSupplierService .getAllCustomerList(getCustomerReference());
		if(clist.size()>0){
			Customer customer=	clist.get(0);
			String sundryDebtorReference=customer.getSundryDebtorReference();
			if(sundryDebtorReference!=null)
			{
				setSundryDebtorReference(customer.getSundryDebtorReference());
				String fxDlSupplierConttract =customer.getFirstName();
				setFirstName(fxDlSupplierConttract);
				//setFxDlwithSupplierConttract(fxDlSupplierConttract);
				//setFxDlwithSupplierConttract(customer.getFirstName());
			}else
			{
				RequestContext.getCurrentInstance().execute("succee.show();");
				setCustomerReference(null);
				setFirstName(null);
				clearHeaderValues();
				return;
			}

		}else{
			RequestContext.getCurrentInstance().execute("customerNotExist.show();");
			setCustomerReference(null);
			setFirstName(null);
			clearHeaderValues();

		} 
		  }catch(Exception exception){
			    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			    setErrorMessage(exception.getMessage()); 
			    RequestContext.getCurrentInstance().execute("error.show();");
			    return;       
			    }
	}


	public void getPayableAccountNumber()
	{
		  try{
		List<ApplicationSetup> lstApplicationSetup=fxDealwithSupplierService.getApplicationSetupDetalis(sessionStateManage.getCountryId(), sessionStateManage.getCompanyId());

		for(ApplicationSetup applicationSetup:lstApplicationSetup)
		{
			setPdPayableAccountNumber(applicationSetup.getFaAccountNumber());
		}
		  }catch(Exception exception){
			    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			    setErrorMessage(exception.getMessage()); 
			    RequestContext.getCurrentInstance().execute("error.show();");
			    return;       
			    }

	}
	public String getDocumentDescriptionForDayBook() {
		  try{
		List<Document> documentDesc=igeneralService.getDocument(new BigDecimal(Constants.FX_DEAL_WITH_DAY_BOOK) ,new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"));
		for(Document des:documentDesc)
		{
			setDocumentNoForDayBook(des.getDocumentID());
		}
		  }catch(Exception exception){
			    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			    setErrorMessage(exception.getMessage()); 
			    RequestContext.getCurrentInstance().execute("error.show();");
			    return null;       
			    }
		return documentDescription;
	}



	private BigDecimal paymentVoucherCompanyId;
	private BigDecimal paymentVoucherId;
	private BigDecimal paymentVoucherFinanceyYear;
	private BigDecimal paymentVoucherNumber;


	public BigDecimal getPaymentVoucherCompanyId() {
		return paymentVoucherCompanyId;
	}

	public void setPaymentVoucherCompanyId(BigDecimal paymentVoucherCompanyId) {
		this.paymentVoucherCompanyId = paymentVoucherCompanyId;
	}

	public BigDecimal getPaymentVoucherId() {
		return paymentVoucherId;
	}

	public void setPaymentVoucherId(BigDecimal paymentVoucherId) {
		this.paymentVoucherId = paymentVoucherId;
	}

	public BigDecimal getPaymentVoucherFinanceyYear() {
		return paymentVoucherFinanceyYear;
	}

	public void setPaymentVoucherFinanceyYear(BigDecimal paymentVoucherFinanceyYear) {
		this.paymentVoucherFinanceyYear = paymentVoucherFinanceyYear;
	}

	public BigDecimal getPaymentVoucherNumber() {
		return paymentVoucherNumber;
	}

	public void setPaymentVoucherNumber(BigDecimal paymentVoucherNumber) {
		this.paymentVoucherNumber = paymentVoucherNumber;
	}

	private String errmsg;
	private Boolean booDocVisble = false;

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public Boolean getBooDocVisble() {
		return booDocVisble;
	}

	public void setBooDocVisble(Boolean booDocVisble) {
		this.booDocVisble = booDocVisble;
	}

	//edit functionality added koti for new cr 16/12/2015

	private Boolean booRenderOffEditIcon=false;
	private Boolean boofxDealWithRef=false;
	private Boolean boofxDealwithSupplierEditableRef=false;
	private Boolean booSaveOrExit=false;
	private Boolean booUpdateOrCancel=false;
	private BigDecimal treasureDocumentNumber;
	private String createdBy;
	private Date createdDate;
	private BigDecimal refYear;
	private BigDecimal refNumber;
	private BigDecimal refDocId;
	private BigDecimal dayBookDetailsForPYPK;
	private BigDecimal treasuryDealDetailPYPK;
	private String pdCurrencyName = "";
	private Boolean booReadOnlyForEdit=false;
	private String fxDlSalesAccNoGl;
	private Boolean booRenderSDAccNo=false;
	private Boolean booRenderSDAccNoForUpdate=false;
	private String fxDealSdAcccNo;
	private String fxDlSupplierAccNoForSale;
	private String sdAccNoForUpdate;
	private Date hdCalValueDateEdit;
	private Boolean booRenderValueDate=false;
	private Boolean booRenderValueDateForEdit=false;
	private Boolean booCheckValueDate=false;


	@Autowired
	IGeneralService<T> generalService;



	//private List<DayBookHeader> lstofUnApproved = new ArrayList<DayBookHeader>();
	
	private List<BigDecimal> treasuryDealDetailSupplierPkId;
	private List<TreasuryDealHeader> lstofUnApproved = new ArrayList<TreasuryDealHeader>();

	public Boolean getBooRenderOffEditIcon() {
		return booRenderOffEditIcon;
	}

	public void setBooRenderOffEditIcon(Boolean booRenderOffEditIcon) {
		this.booRenderOffEditIcon = booRenderOffEditIcon;
	}

	public Boolean getBoofxDealWithRef() {
		return boofxDealWithRef;
	}

	public void setBoofxDealWithRef(Boolean boofxDealWithRef) {
		this.boofxDealWithRef = boofxDealWithRef;
	}

	public Boolean getBoofxDealwithSupplierEditableRef() {
		return boofxDealwithSupplierEditableRef;
	}

	public void setBoofxDealwithSupplierEditableRef(Boolean boofxDealwithSupplierEditableRef) {
		this.boofxDealwithSupplierEditableRef = boofxDealwithSupplierEditableRef;
	}
	public Boolean getBooSaveOrExit() {
		return booSaveOrExit;
	}

	public void setBooSaveOrExit(Boolean booSaveOrExit) {
		this.booSaveOrExit = booSaveOrExit;
	}

	public Boolean getBooUpdateOrCancel() {
		return booUpdateOrCancel;
	}

	public void setBooUpdateOrCancel(Boolean booUpdateOrCancel) {
		this.booUpdateOrCancel = booUpdateOrCancel;
	}

	/*public List<DayBookHeader> getLstofUnApproved() {
		return lstofUnApproved;
	}
	public void setLstofUnApproved(List<DayBookHeader> lstofUnApproved) {
		this.lstofUnApproved = lstofUnApproved;
	}*/
	
	
	
	public BigDecimal getTreasureDocumentNumber() {
		return treasureDocumentNumber;
	}

	public List<TreasuryDealHeader> getLstofUnApproved() {
		return lstofUnApproved;
	}

	public void setLstofUnApproved(List<TreasuryDealHeader> lstofUnApproved) {
		this.lstofUnApproved = lstofUnApproved;
	}

	public void setTreasureDocumentNumber(BigDecimal treasureDocumentNumber) {
		this.treasureDocumentNumber = treasureDocumentNumber;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public BigDecimal getRefYear() {
		return refYear;
	}

	public void setRefYear(BigDecimal refYear) {
		this.refYear = refYear;
	}

	public BigDecimal getRefNumber() {
		return refNumber;
	}

	public void setRefNumber(BigDecimal refNumber) {
		this.refNumber = refNumber;
	}
	public BigDecimal getRefDocId() {
		return refDocId;
	}

	public void setRefDocId(BigDecimal refDocId) {
		this.refDocId = refDocId;
	}

	public BigDecimal getDayBookDetailsForPYPK() {
		return dayBookDetailsForPYPK;
	}

	public void setDayBookDetailsForPYPK(BigDecimal dayBookDetailsForPYPK) {
		this.dayBookDetailsForPYPK = dayBookDetailsForPYPK;
	}
	public BigDecimal getTreasuryDealDetailPYPK() {
		return treasuryDealDetailPYPK;
	}

	public void setTreasuryDealDetailPYPK(BigDecimal treasuryDealDetailPYPK) {
		this.treasuryDealDetailPYPK = treasuryDealDetailPYPK;
	}
	public Boolean getBooReadOnlyForEdit() {
		return booReadOnlyForEdit;
	}

	public void setBooReadOnlyForEdit(Boolean booReadOnlyForEdit) {
		this.booReadOnlyForEdit = booReadOnlyForEdit;
	}
	public String getFxDlSalesAccNoGl() {
		return fxDlSalesAccNoGl;
	}

	public void setFxDlSalesAccNoGl(String fxDlSalesAccNoGl) {
		this.fxDlSalesAccNoGl = fxDlSalesAccNoGl;
	}
	public Boolean getBooRenderSDAccNo() {
		return booRenderSDAccNo;
	}

	public void setBooRenderSDAccNo(Boolean booRenderSDAccNo) {
		this.booRenderSDAccNo = booRenderSDAccNo;
	}

	public Boolean getBooRenderSDAccNoForUpdate() {
		return booRenderSDAccNoForUpdate;
	}

	public void setBooRenderSDAccNoForUpdate(Boolean booRenderSDAccNoForUpdate) {
		this.booRenderSDAccNoForUpdate = booRenderSDAccNoForUpdate;
	}

	public String getFxDealSdAcccNo() {
		return fxDealSdAcccNo;
	}

	public void setFxDealSdAcccNo(String fxDealSdAcccNo) {
		this.fxDealSdAcccNo = fxDealSdAcccNo;
	}
	public String getFxDlSupplierAccNoForSale() {
		return fxDlSupplierAccNoForSale;
	}

	public void setFxDlSupplierAccNoForSale(String fxDlSupplierAccNoForSale) {
		this.fxDlSupplierAccNoForSale = fxDlSupplierAccNoForSale;
	}

	public String getSdAccNoForUpdate() {
		return sdAccNoForUpdate;
	}

	public void setSdAccNoForUpdate(String sdAccNoForUpdate) {
		this.sdAccNoForUpdate = sdAccNoForUpdate;
	}
	public Date getHdCalValueDateEdit() {
		return hdCalValueDateEdit;
	}

	public void setHdCalValueDateEdit(Date hdCalValueDateEdit) {
		this.hdCalValueDateEdit = hdCalValueDateEdit;
	}

	public Boolean getBooRenderValueDate() {
		return booRenderValueDate;
	}

	public void setBooRenderValueDate(Boolean booRenderValueDate) {
		this.booRenderValueDate = booRenderValueDate;
	}

	public Boolean getBooRenderValueDateForEdit() {
		return booRenderValueDateForEdit;
	}

	public void setBooRenderValueDateForEdit(Boolean booRenderValueDateForEdit) {
		this.booRenderValueDateForEdit = booRenderValueDateForEdit;
	}
	public Boolean getBooCheckValueDate() {
		return booCheckValueDate;
	}

	public void setBooCheckValueDate(Boolean booCheckValueDate) {
		this.booCheckValueDate = booCheckValueDate;
	}

	public void editInputForUpdate(){
		  try{
		setBoofxDealWithRef(false);
		setBoofxDealwithSupplierEditableRef(true);
		setBooRenderOffEditIcon(false);
		setBooSaveOrExit(false);
		setBooUpdateOrCancel(true);
		setTeasuryDealHeaderId(null);
		setTreasuryDealDetailSupplierPkId(null);
		setTreasuryDealDetailPYPK(null);
		setDayBookDetailsId(null);
		setDayBookDetailsForPYPK(null);
		setDaybookHeaderId(null);
		setBooReadOnlyForEdit(true);
		setTreasureDocumentNumber(null);
		setCreatedBy(null);
		setCreatedDate(null);
		setRefYear(null);
		setRefNumber(null);
		setRefDocId(null);
		setCustomerReference(null);
		setFxDlwithSupplierConttract(null);
		setFxDlSalesAccNoGl(null);
		setHdCalValueDate(null);
		setHdCalValueDateEdit(null);
		setBooRenderValueDate(false);
		setBooRenderValueDateForEdit(true);
		setBooCheckValueDate(false);
		setFirstName(null);
		clearHeaderValues();
		lstofUnApproved.clear();
		List<DayBookHeader> lstofUnApprovedDlHeader = fxDealwithSupplierService.fetchDocumentNumberFromDayBookheader(Constants.U);
		List<TreasuryDealHeader> trasuryHeaderList=fXDetailInformationService.fetchDocumentNumberFromTreasDealheader(Constants.Fx_SupplierDealType ,Constants.U,new BigDecimal(getFinaceYear()));
		if(lstofUnApprovedDlHeader.size()!=0 && trasuryHeaderList.size() !=0){
			//setLstofUnApproved(lstofUnApprovedDlHeader);
			setLstofUnApproved(trasuryHeaderList);
		}
		  }catch(Exception exception){
			    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			    setErrorMessage(exception.getMessage()); 
			    RequestContext.getCurrentInstance().execute("error.show();");
			    return;       
			    }
	}

	public void populateAllFiledsValuesForEdit(){
		setBooCheckValueDate(false);
		List<TreasuryDealHeader> trasuryHeaderList = null;
		List<TreasuryDealDetail> treasuryDetailPDList =null;
		List<TreasuryDealDetail> treasuryDetailPYList =null;
		List<DayBookHeader> dayBookHeaderList = null;
		List<DayBookDetails> dayBookDetailsSDList = null;
		List<DayBookDetails> dayBookDetailsPYList = null;
		try{
			if(getDocumentSerialforFxdeal() != null && getDocumentSerialforFxdeal().compareTo(BigDecimal.ZERO)!=0){
				trasuryHeaderList=fXDetailInformationService.fecthTreasurydealHeaderRecordsForSupplierApprove(getDocumentSerialforFxdeal(),Constants.Fx_SupplierDealType ,Constants.U ,new BigDecimal(getFinaceYear()));
				setTreasureDocumentNumber(getDocumentSerialforFxdeal());
				
				if(trasuryHeaderList.size()!=0){
					List<DayBookHeader> lstofUnApprovedDlHeader = fxDealwithSupplierService.fetchDocumentNumberBasedOnAllDetails(trasuryHeaderList.get(0).getPaymentVoucherNumber());
				}
				if(trasuryHeaderList.size()>0){
					treasuryDetailPDList = fXDetailInformationService.fecthTreasuryDealDetailRecordsForPD(trasuryHeaderList.get(0).getTreasuryDealHeaderId());
					treasuryDetailPYList = fXDetailInformationService.fecthTreasuryDealDetailRecordsForPY(trasuryHeaderList.get(0).getTreasuryDealHeaderId());
					dayBookHeaderList= fXDetailInformationService.fecthDayBookHeaderForSupplierApprove(trasuryHeaderList.get(0).getPaymentVoucherNumber());
				}
				if(dayBookHeaderList.size()>0){
					dayBookDetailsSDList= fXDetailInformationService.fecthDayBookDetailsRecordsForSD(dayBookHeaderList.get(0).getDaybookHeaderId());
					dayBookDetailsPYList= fXDetailInformationService.fecthDayBookDetailsRecordsForPY(dayBookHeaderList.get(0).getDaybookHeaderId());
				}
				if(trasuryHeaderList.size()>0){
					setTeasuryDealHeaderId(trasuryHeaderList.get(0).getTreasuryDealHeaderId());
				}
				if(dayBookHeaderList.size()>0){
					setDaybookHeaderId(dayBookHeaderList.get(0).getDaybookHeaderId());
				}

				for(TreasuryDealHeader headerObj :trasuryHeaderList){
					String companyName=null;
					String documentDesc = null;
					setCompanyId(headerObj.getFsCompanyMaster().getCompanyId());
					if(headerObj.getFsCompanyMaster().getCompanyId()!=null){
						companyName= specialCustomerDealRequestService.getCompanyNameForUpdate(headerObj.getFsCompanyMaster().getCompanyId());
					}
					if(companyName!=null){	
						setCompanyName(companyName);
					}
					if(headerObj.getExDocument().getDocumentID()!=null){
						documentDesc = specialCustomerDealRequestService.getDocumentNameForUpdate(headerObj.getExDocument().getDocumentID());
					}
					if(documentDesc!=null){
						setDocumentDescription(documentDesc);
					}
					setFinaceYear(headerObj.getUserFinanceYear().intValue());
					setHdCalValueDateEdit(headerObj.getDocumentDate());
					setCustomerReference(headerObj.getDealWithCustomer());
					String customerName=fxDealSupplierService .getAllCustomerName(headerObj.getDealWithCustomer());
					setFirstName(customerName);
					setFxDlwithSupplierConttract(headerObj.getContactName());
					setFxDlSupplierConcludedby(headerObj.getConcludedBy());
					setFxDlSupplierReutersRef(headerObj.getReutersReference());
					setFxDlSupplierRemarks(headerObj.getRemarks());
					setTeasuryDealHeaderId(headerObj.getTreasuryDealHeaderId());
					setTreasureDocumentNumber(headerObj.getTreasuryDocumentNumber());
					setPaymentVoucherId(headerObj.getPaymentVoucherId());
					setPaymentVoucherNumber(headerObj.getPaymentVoucherNumber());
					setPaymentVoucherFinanceyYear(headerObj.getPaymentVoucherFinanceyYear());
					setPaymentVoucherCompanyId(headerObj.getPaymentVoucherCompanyId());
					setCreatedBy(headerObj.getCreatedBy());
					setCreatedDate(headerObj.getCreatedDate());
				}
				for(TreasuryDealDetail dealDetailObj:treasuryDetailPDList){
					treasuryDealDetailList.add(dealDetailObj.getTreasuryDealDetailId());
					if(dealDetailObj.getLineType().equalsIgnoreCase(Constants.PD)){
						setBooRenderCurrencyIdForUpdate(true);
						setBooRenderCurrencyId(false);
						setBooRenderPDAccNo(false);
						setBooRenderPDAccNoForUpdate(true);
						setPdBankId(dealDetailObj.getTreasuryDealBankMaster().getBankId());
						String bankName = generalService.getBankName(dealDetailObj.getTreasuryDealBankMaster().getBankId());
						setPdBankName(bankName);
						setPdCurrencyId(dealDetailObj.getTreasuryDealDetailCurrencyMaster().getCurrencyId());
						String currencyName = generalService.getCurrencyName(dealDetailObj.getTreasuryDealDetailCurrencyMaster().getCurrencyId());
						setPdCurrencyName(currencyName);
						setPdCurrencyIdUpdate(currencyName);
						setFxDlSupplierAccNo(dealDetailObj.getTreasuryDealDetailBankAccountDetails().getFundGlno());
						setPdAccNoForUpdate(dealDetailObj.getTreasuryDealDetailBankAccountDetails().getBankAcctNo());
						setPdCalValueDate(dealDetailObj.getValueDate());
						setPdFCAmount(dealDetailObj.getFcAmount());
						if(dealDetailObj.getMultiplicationDivision().equalsIgnoreCase("1")){
							setPdMultipleDivision(new BigDecimal(dealDetailObj.getMultiplicationDivision()));
						}else{
							setPdMultipleDivision(new BigDecimal(dealDetailObj.getMultiplicationDivision()));
						}
						setPdExchangeRate(dealDetailObj.getExchange());
						setPdAmount(dealDetailObj.getSaleAmount());
						setPdLocalExchangeRate(dealDetailObj.getLocalExchangeRate());
						setPdLocalAmount(dealDetailObj.getFcAmount());
						setTeasuryDealDetailId(dealDetailObj.getTreasuryDealDetailId());
						setPdCalValueDate(dealDetailObj.getValueDate());
					}
				}

				for(TreasuryDealDetail dealDetailObj:treasuryDetailPYList){
					treasuryDealDetailList.add(dealDetailObj.getTreasuryDealDetailId());
					setTreasuryDealDetailPYPK(dealDetailObj.getTreasuryDealDetailId());
				}
				if (dayBookHeaderList.size() != 0) {
					for (DayBookHeader dayBookHeader : dayBookHeaderList) {
						String companyName = null;
						String documentDesc = null;
						setCompanyId(dayBookHeader.getDayBookCompanyMaster().getCompanyId());
						if (dayBookHeader.getDayBookCompanyMaster().getCompanyId() != null) {
							companyName = specialCustomerDealRequestService.getCompanyNameForUpdate(dayBookHeader.getDayBookCompanyMaster().getCompanyId());
						}
						if (companyName != null) {
							setCompanyName(companyName);
						}
						if (dayBookHeader.getDoucDocumentId().getDocumentID() != null) {
							documentDesc = specialCustomerDealRequestService.getDocumentNameForUpdate(dayBookHeader.getDoucDocumentId().getDocumentID());
						}
						if (documentDesc != null) {
							setDocumentDescription(documentDesc);
						}

						setFinaceYear(dayBookHeader.getDocumentFinancialYear().intValue());
						setCustomerReference(new BigDecimal(dayBookHeader.getDealedWithCustomer()));
						setFxDlwithSupplierConttract(dayBookHeader.getContact());
						setFxDlSupplierConcludedby(dayBookHeader.getConcludedBy());
						setFxDlSupplierRemarks(dayBookHeader.getRemarks());
						setDaybookHeaderId(dayBookHeader.getDaybookHeaderId());
						setPaymentVoucherId(dayBookHeader.getDaybookHeaderId());
						setPaymentVoucherNumber(dayBookHeader.getDocumentNumber());
						setPaymentVoucherFinanceyYear(dayBookHeader.getDocumentFinancialYear());
						setPaymentVoucherCompanyId(dayBookHeader.getDayBookCompanyMaster().getCompanyId());
						setDocumentNoForDayBook(dayBookHeader.getDoucDocumentId().getDocumentID());
						setCreatedBy(dayBookHeader.getCreatedBy());
						setCreatedDate(dayBookHeader.getCreatedDate());
						setRefYear(dayBookHeader.getRefFinYear());
						setRefNumber(dayBookHeader.getRefNumber());
						setRefDocId(dayBookHeader.getRefDocumentId());
						setSdCalValueDate(dayBookHeader.getDocumentDate());
						setSdSaveValueDate(new SimpleDateFormat("dd/MM/yyyy").format(dayBookHeader.getDocumentDate()));
						setBooCheckValueDate(true);
					}
				}
				if(dayBookDetailsSDList != null){

					for(DayBookDetails detailsObj:dayBookDetailsSDList){
						setBooRenderSDCurrency(false);
						setBooRenderSDCurrencyForUpdate(true);
						setBooRenderSDAccNo(false);
						setBooRenderSDAccNoForUpdate(true);
						dealBookDetailList.add(detailsObj.getDayBookDetailsId());
						if(detailsObj.getDayBookLineType().equalsIgnoreCase(Constants.SD)){
							setSdBankId(detailsObj.getDayBookDetailsBankMaster().getBankId());
							String bankName = generalService.getBankName(detailsObj.getDayBookDetailsBankMaster().getBankId());
							setSdBankName(bankName);
							setSdCurrencyId(detailsObj.getDayBookCurrencyId().getCurrencyId());
							String currencyName = generalService.getCurrencyName(detailsObj.getDayBookCurrencyId().getCurrencyId());
							setSdCurrencyName(currencyName);
							String accountNo = detailsObj.getDayBookDetailsBankAccountDetails().getFundGlno();
							BigDecimal accountBanlance =new BigDecimal(fundEstimationService.getCurrentBalance(accountNo));
							setFxDealSdAcccNo(accountNo);
							setFxDlSalesAccNo(detailsObj.getDayBookDetailsBankAccountDetails().getBankAcctNo());
							setSdBankBalance(accountBanlance);
							setSdCalValueDate(detailsObj.getDayBookdocumentDate());
							setSdSaleAmount(detailsObj.getDayBookFcAmount());
							setSdAverageRate(detailsObj.getDayBookExchangeRate());
							setSdLocalAmount(detailsObj.getDayBookLocalAmount());
							setDayBookDetailsId(detailsObj.getDayBookDetailsId());
							setSdCalValueDate(detailsObj.getDayBookdocumentDate());
							setSdSaveValueDate(new SimpleDateFormat("dd/MM/yyyy").format(detailsObj.getDayBookdocumentDate()));
							setBooCheckValueDate(true);
						}

					}

					for(DayBookDetails detailsObj:dayBookDetailsPYList){
						dealBookDetailList.add(detailsObj.getDayBookDetailsId());
						setDayBookDetailsForPYPK(detailsObj.getDayBookDetailsId());
						setPdPayableAccountNumber(detailsObj.getDayBookFaAccountNumber());   
					}
				}
			}else{
				setBoofxDealWithRef(false);
				setBoofxDealwithSupplierEditableRef(true);
				setBooRenderOffEditIcon(false);
				setBooSaveOrExit(false);
				setBooUpdateOrCancel(true);
				setTeasuryDealHeaderId(null);
				setTreasuryDealDetailSupplierPkId(null);
				setTreasuryDealDetailPYPK(null);
				setDayBookDetailsId(null);
				setDayBookDetailsForPYPK(null);
				setDaybookHeaderId(null);
				setBooReadOnlyForEdit(true);
				setTreasureDocumentNumber(null);
				setCreatedBy(null);
				setCreatedDate(null);
				setRefYear(null);
				setRefNumber(null);
				setRefDocId(null);
				setCustomerReference(null);
				setFxDlwithSupplierConttract(null);
				setFxDlSalesAccNoGl(null);
				clearHeaderValues();
				clearPDValues();
				clearPDValuesForBankSelection();
				clearSDValues();
				clearSDValuesForBankSelection();
				clearSDValuesForCurrencySelection();
				setFirstName(null);
			}
		}catch(Exception exception){
			  log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			  setErrorMessage(exception.getMessage()); 
			  RequestContext.getCurrentInstance().execute("error.show();");
			  return;       
			  }



	}

	private List<BigDecimal> treasuryDealDetailList = new ArrayList<BigDecimal>();
	private List<BigDecimal> dealBookDetailList = new ArrayList<BigDecimal>();
	private List<TreasuryDealHeader> documentNumberList= new ArrayList<TreasuryDealHeader>();



	public List<BigDecimal> getTreasuryDealDetailList() {
		return treasuryDealDetailList;
	}

	public void setTreasuryDealDetailList(List<BigDecimal> treasuryDealDetailList) {
		this.treasuryDealDetailList = treasuryDealDetailList;
	}

	public List<BigDecimal> getDealBookDetailList() {
		return dealBookDetailList;
	}

	public void setDealBookDetailList(List<BigDecimal> dealBookDetailList) {
		this.dealBookDetailList = dealBookDetailList;
	}

	public List<TreasuryDealHeader> getDocumentNumberList() {
		return documentNumberList;
	}

	public void setDocumentNumberList(List<TreasuryDealHeader> documentNumberList) {
		this.documentNumberList = documentNumberList;
	}


	public String getPdCurrencyName() {
		return pdCurrencyName;
	}

	public void setPdCurrencyName(String pdCurrencyName) {
		this.pdCurrencyName = pdCurrencyName;
	}

	public List<BigDecimal> getTreasuryDealDetailSupplierPkId() {
		return treasuryDealDetailSupplierPkId;
	}

	public void setTreasuryDealDetailSupplierPkId(List<BigDecimal> treasuryDealDetailSupplierPkId) {
		this.treasuryDealDetailSupplierPkId = treasuryDealDetailSupplierPkId;
	}
	
	
	
	public void deleteDayBookandTreasure() {
		try {

			fxDealwithSupplierService.delete(getTeasuryDealHeaderId(),
					getTeasuryDealDetailId(), getTreasuryDealDetailPYPK(),
					getDaybookHeaderId(), getDayBookDetailsId(), getDayBookDetailsForPYPK(),
					sessionStateManage.getUserName());			
			RequestContext.getCurrentInstance().execute("deleteSuccess.show();");

		} catch (Exception e) {
			setErrorMessage("Deleting Error : " + e.getMessage());
			RequestContext.getCurrentInstance().execute("sqlexception.show();");
			return;
		}
	}
	
	

	public void upDateDayBookandTreasure(){

		if(getPdCurrencyId().compareTo(getSdCurrencyId())==0)
		{
			RequestContext.getCurrentInstance().execute("currencyCheck.show();");
			return;
		}

		if(getPdPayableAccountNumber()==null)
		{
			RequestContext.getCurrentInstance().execute("payableAccNo.show();");
			return;
		}

		if(getFxDlSalesAccNo() ==null && getFxDealSdAcccNo() == null)
		{
			RequestContext.getCurrentInstance().execute("NosalesAccNo.show();");
			return;
		}

		if(getSdBankBalance()==null)
		{
			if(new BigDecimal(sessionStateManage.getCurrencyId()).compareTo(getSdCurrencyId())!=0){
				RequestContext.getCurrentInstance().execute("sdNoBankBalance.show();");
				return;
			}
		}

		if(getSdAverageRate()==null)
		{
			RequestContext.getCurrentInstance().execute("sdNoAverageRate.show();");
			return;
		}

		HashMap<String, Object> saveAllMap=  new HashMap<String, Object>();


		BigDecimal lineNumber=BigDecimal.ZERO;

		TreasuryDealHeader treasuryDealHeader = saveTreasuryDealHeader(getTreasureDocumentNumber(),getDocumentSerialforFxdeal());

		saveAllMap.put("TreasuryDealHeader", treasuryDealHeader);

		lineNumber=lineNumber.add(new BigDecimal(1));
		TreasuryDealDetail treasuryDealDetailForPD = saveTreasuryDealDetailForPD(treasuryDealHeader, Constants.PD, getTreasureDocumentNumber(),lineNumber);
		saveAllMap.put("TreasuryDealDetailForPD", treasuryDealDetailForPD);

		lineNumber=lineNumber.add(new BigDecimal(1));
		TreasuryDealDetail treasuryDealDetailForPY = upDateTreasuryDealDetailForPY(treasuryDealHeader, Constants.PY, getTreasureDocumentNumber(),lineNumber);
		saveAllMap.put("TreasuryDealDetailForPY", treasuryDealDetailForPY);


		DayBookHeader dayBookHeader=  saveDayBookHeader(treasuryDealHeader , getDocumentSerialforFxdeal() , getTreasureDocumentNumber() );
		saveAllMap.put("DayBookHeader", dayBookHeader);

		lineNumber=BigDecimal.ZERO;
		lineNumber=lineNumber.add(new BigDecimal(1));
		DayBookDetails dayBookSdDetailsForPY= upDateDayBookDetailsForPY(dayBookHeader, Constants.PY, getDocumentSerialforFxdeal(),lineNumber);
		saveAllMap.put("DayBookDetailsForPY", dayBookSdDetailsForPY);

		lineNumber=lineNumber.add(new BigDecimal(1));
		DayBookDetails dayBookSdDetailsForSD=saveDayBookDetailsForSD(dayBookHeader, Constants.SD, getDocumentSerialforFxdeal(),lineNumber);
		saveAllMap.put("DayBookDetailsForSD", dayBookSdDetailsForSD);

		try {

			fxDealwithSupplierService.saveAllFxDealWithSupplier(saveAllMap);
			//fXDetailInformationService.callUnApproveProcedure(sessionStateManage.getCountryId(), sessionStateManage.getCompanyId(), getDocumentNo(), new BigDecimal(getFinaceYear()), getDocSerialIdNumberForSave());
			//RequestContext.getCurrentInstance().execute("updateDetais.show();");
			setSuccessPanelRender(true);
			setMainPanelRender(false);
		}catch (AMGException e) {
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("sqlexception.show();");
			return;
		} catch (Exception e) {
			setErrorMessage("Saving Error : "+e.getMessage());
			RequestContext.getCurrentInstance().execute("sqlexception.show();");
			return;
		}

		/*try {
				fXDetailInformationService.callUnApproveProcedure(sessionStateManage.getCountryId(), sessionStateManage.getCompanyId(), getDocumentNo(), new BigDecimal(getFinaceYear()), getDocSerialIdNumberForSave());
			} catch (AMGException e) {
				setErrorMessage("EX_TREASURY_UAPPV_UGL" + " : " +e.getMessage());
				RequestContext.getCurrentInstance().execute("sqlexception.show();");
			}*/
	}

	private DayBookDetails upDateDayBookDetailsForPY(DayBookHeader dayBookHeader ,String lineType,BigDecimal saveDocumentSerialID ,BigDecimal lineNumber)
	{
		DayBookDetails dayBookSdDetails =null;
		try {
			dayBookSdDetails = new DayBookDetails();
			dayBookSdDetails.setDayBookDetailsId(getDayBookDetailsForPYPK());
			dayBookSdDetails.setDayBookAccyymm(new SimpleDateFormat("dd/MM/yyyy").parse(getCurrentDateWithFormat()));
			dayBookSdDetails.setDayBookHeaderId(dayBookHeader);

			// update Company Master
			CompanyMaster dayBkDlsSdcompanyMaster = new CompanyMaster();
			dayBkDlsSdcompanyMaster.setCompanyId(getCompanyId());
			dayBookSdDetails.setDayBookCompanyMaster(dayBkDlsSdcompanyMaster);

			CountryMaster daybookDlsSdCountry = new CountryMaster();
			daybookDlsSdCountry.setCountryId(sessionStateManage.getCountryId());
			dayBookSdDetails.setDayBookCountryMaster(daybookDlsSdCountry);

			CurrencyMaster dayBookDlsSdCurrencyMaster= new CurrencyMaster();
			dayBookDlsSdCurrencyMaster.setCurrencyId(getSdCurrencyId());
			dayBookSdDetails.setDayBookCurrencyId(dayBookDlsSdCurrencyMaster);
			dayBookSdDetails.setValueDate(new SimpleDateFormat("dd/MM/yyyy").parse(getSdSaveValueDate()));


			Document dayBkDlsSddocument = new Document();
			dayBkDlsSddocument.setDocumentID(getDocumentNoForDayBook());
			dayBookSdDetails.setDayBookDocumentId(dayBkDlsSddocument);

			if (getBooCheckValueDate().equals(true)) {
				dayBookSdDetails.setDayBookdocumentDate(getHdCalValueDateEdit());
			} else {
				dayBookSdDetails.setDayBookdocumentDate(getHdCalValueDate());
			}
			dayBookSdDetails.setDayBookDocumentNumber(saveDocumentSerialID);

			dayBookSdDetails.setDayBookExchangeRate(getSdAverageRate());

			dayBookSdDetails.setDayBookFaAccountNumber(getPdPayableAccountNumber());

			dayBookSdDetails.setDayBookFcAmount(getSdSaleAmount());//

			dayBookSdDetails.setDayBookFinanceYear(new BigDecimal(getFinaceYear()));

			dayBookSdDetails.setDayBookLocalAmount(getSdLocalAmount());//

			dayBookSdDetails.setDayBookLineNumber(lineNumber);//
			dayBookSdDetails.setDayBookLineType(lineType);//

			if (getDayBookDetailsId() != null
					&& getDayBookDetailsId().intValue() != 0) {

				dayBookSdDetails.setModifiedBy(sessionStateManage.getUserName());
				dayBookSdDetails.setModifiedDate(new Date());
				dayBookSdDetails.setCreatedBy(getCreatedBy());
				dayBookSdDetails.setCreatedDate(getCreatedDate());
			} else {
				dayBookSdDetails.setCreatedBy(sessionStateManage.getUserName());
				dayBookSdDetails.setCreatedDate(new Date());

			}

		} catch (Exception e) {
			  setErrorMessage("Saving Error : "+e.getMessage());
		}
		return dayBookSdDetails;
	}
	private TreasuryDealDetail upDateTreasuryDealDetailForPY(TreasuryDealHeader treasuryDealHeader,String lineType,BigDecimal saveDocumentSerialID,BigDecimal lineNumber)
	{
		TreasuryDealDetail treasuryDealDetail = null;
		try {
			
			treasuryDealDetail = new TreasuryDealDetail();
			
			treasuryDealDetail.setTreasuryDealDetailId(getTreasuryDealDetailPYPK());
			
			CompanyMaster companyMaster = new CompanyMaster();
			companyMaster.setCompanyId(getCompanyId());
			treasuryDealDetail.setTreasuryDealCompanyMaster(companyMaster);

			CountryMaster applicationCountry = new CountryMaster();
			applicationCountry.setCountryId(sessionStateManage.getCountryId());
			treasuryDealDetail.setTreasuryDealCountryMaster(applicationCountry);

			Document document = new Document();
			document.setDocumentID(getDocumentNo());
			treasuryDealDetail.setTreasuryDealDocument(document);
			
			treasuryDealDetail.setTreasuryDealHeader(treasuryDealHeader);
			

			//save Currency
			CurrencyMaster currencyMaster = new CurrencyMaster();
			currencyMaster.setCurrencyId(getSdCurrencyId());
			treasuryDealDetail.setTreasuryDealDetailCurrencyMaster(currencyMaster);

			BigDecimal sdLocalAmt=getPdFCAmount().multiply(getPdLocalExchangeRate());
			BigDecimal roundSdLocalAmt =round(sdLocalAmt,foreignLocalCurrencyDenominationService.getDecimalPerCurrency(getSdCurrencyId()));
			treasuryDealDetail.setLocalAmount(roundSdLocalAmt);

			
			
			
			treasuryDealDetail.setTreasuryDealUserFinanceYear(new BigDecimal(getFinaceYear()));

			treasuryDealDetail.setDocumentNumber(saveDocumentSerialID);
			treasuryDealDetail.setLineType(lineType);
			treasuryDealDetail.setExchange(getSdAverageRate());
			treasuryDealDetail.setAvgRate(getSdAverageRate());
			treasuryDealDetail.setSaleAmount(getSdSaleAmount());
			treasuryDealDetail.setLocalExchangeRate(getSdAverageRate());
			treasuryDealDetail.setFcAmount(getSdSaleAmount());

			if(getTreasuryDealDetailPYPK()!=null && getTreasuryDealDetailPYPK().intValue()!=0){
				treasuryDealDetail.setModifiedBy(sessionStateManage.getUserName());
				treasuryDealDetail.setModifiedDate(new Date());
				treasuryDealDetail.setCreatedBy(getCreatedBy());
				treasuryDealDetail.setCreatedDate(getCreatedDate());
			}else{
				treasuryDealDetail.setCreatedBy(sessionStateManage.getUserName());
				treasuryDealDetail.setCreatedDate(new Date());
			}

			treasuryDealDetail.setValueDate(getPdCalValueDate());
			treasuryDealDetail.setLineNumber(lineNumber);
			treasuryDealDetail.setIsActive(Constants.U);
			treasuryDealDetail.setMultiplicationDivision(getPdMultipleDivision().toPlainString());
			treasuryDealDetail.setFaAccountNo(getPdPayableAccountNumber());
			treasuryDealDetail.setSubLedgerInd(Constants.Yes);
			treasuryDealDetail.setOpenItemId(Constants.Yes);
			treasuryDealDetail.setCustomerReference(getCustomerReference());

		} catch (Exception e) {
			  setErrorMessage("Saving Error : "+e.getMessage());
		}
		return treasuryDealDetail;
	}
	
	public void clearwhileEdit(){
		setBoofxDealWithRef(false);
		setBoofxDealwithSupplierEditableRef(true);
		setBooRenderOffEditIcon(false);
		setBooSaveOrExit(false);
		setBooUpdateOrCancel(true);
		setTeasuryDealHeaderId(null);
		setTreasuryDealDetailSupplierPkId(null);
		setTreasuryDealDetailPYPK(null);
		setDayBookDetailsId(null);
		setDayBookDetailsForPYPK(null);
		setDaybookHeaderId(null);
		setBooReadOnlyForEdit(true);
		setTreasureDocumentNumber(null);
		setCreatedBy(null);
		setCreatedDate(null);
		setRefYear(null);
		setRefNumber(null);
		setRefDocId(null);
		setCustomerReference(null);
		setFxDlwithSupplierConttract(null);
		setFxDlSalesAccNoGl(null);
		setHdCalValueDate(null);
		setHdCalValueDateEdit(null);
		setBooRenderValueDate(false);
		setBooRenderValueDateForEdit(true);
		setBooCheckValueDate(false);
		setFirstName(null);
		clearHeaderValues();
		lstofUnApproved.clear();
	}

	//////////////////////////////////////////////////   Report code   ///////////////////////////////////////////////////////////
	
	
	JasperPrint jasperPrint;
	private List<TreasuryDealInfo> treasuryDealInfoList;

	public void dealTicketReportInit() throws JRException {
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(treasuryDealInfoList);
		String reportPath = FacesContext.getCurrentInstance() .getExternalContext().getRealPath("reports/design/dealticket.jasper");
		jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap(), beanCollectionDataSource);
	}

	
	public void generateDealTicketReport(){
		try {
			fetchDataForDealTicketReport(getDocSerialIdNumberForSave());
			dealTicketReportInit();
			HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			httpServletResponse.addHeader("Content-disposition","attachment; filename=dealticket.pdf");

			ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint,servletOutputStream);
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	public void dealPaymentReportInit() throws JRException{
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(treasuryDealInfoList);
		String reportPath = FacesContext.getCurrentInstance() .getExternalContext().getRealPath("reports/design/dealpayment.jasper");
		jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap(), beanCollectionDataSource);
	}
	
	public void generateDealPaymentReport(){
		try {
			fetchDataForDealPaymentReport(getDocSerialIdNumberForSave());
			dealPaymentReportInit();
			HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			httpServletResponse.addHeader("Content-disposition","attachment; filename=dealpayment.pdf");

			ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint,servletOutputStream);
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	public void fetchDataForDealTicketReport(BigDecimal documentNumber) throws Exception {

		treasuryDealInfoList=new CopyOnWriteArrayList<TreasuryDealInfo>();
		List<TreasuryDealInfo> treasuryDealInfoList1=new CopyOnWriteArrayList<TreasuryDealInfo>();
		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath = ctx.getRealPath("//");
		//String logoPath = realPath + Constants.LOGO_PATH;
		
		String logoPath =null;
		if(sessionStateManage.getCountryName().equalsIgnoreCase(Constants.KUWAIT)){
			logoPath = realPath+Constants.LOGO_PATH;
		}else if(sessionStateManage.getCountryName().equalsIgnoreCase(Constants.OMAN)){
			logoPath = realPath+Constants.LOGO_PATH_OM;
		}else{
			logoPath =realPath+Constants.LOGO_PATH;
		}
		
		String subReportPath = realPath + Constants.SUB_REPORT_PATH;
		List<TreasuryCustomerDeal> treasuryDealInfo1 = new ArrayList<TreasuryCustomerDeal>();
		List<TreasuryCustomerDeal> saleList = new ArrayList<TreasuryCustomerDeal>();

		List<TreasuryCustomerDeal> dealTicketList = new ArrayList<TreasuryCustomerDeal>();
		
		List<TreasuryCustomerDeal> treasuryCustList = fXDetailInformationService.getTreasuryCustomerDealAndPaymentValues(sessionStateManage.getCompanyId(),new BigDecimal(Constants.DOCUMENT_CODE_FOR_FX_DEAL_WITH_BANK),new BigDecimal(getFinaceYear()), documentNumber);
	//	List<TreasuryCustomerDeal> treasuryCustList = fXDetailInformationService.getTreasuryCustomerDealAndPaymentValues(new BigDecimal(1),new BigDecimal(70), new BigDecimal(2015), new BigDecimal(599));
		
		for(TreasuryCustomerDeal treasuryCustDeal : treasuryCustList){
			if(treasuryCustDeal.getPaymentIndicator().trim().equalsIgnoreCase(Constants.No)){
				dealTicketList.add(treasuryCustDeal);
			}
		}

		for(TreasuryCustomerDeal treasuryCustDeal : dealTicketList){
			if(treasuryCustDeal.getLineType()!=null){
				if(treasuryCustDeal.getLineType().equalsIgnoreCase(Constants.PY)){
					treasuryDealInfo1.add(treasuryCustDeal);
				}else if(treasuryCustDeal.getLineType().equalsIgnoreCase(Constants.PD)){
					saleList.add(treasuryCustDeal);
				}
			}
		}
		
		TreasuryDealInfo	treasuryDealInfoPurchase = new TreasuryDealInfo();
		
		List<CompanyMasterDesc> companyDesc =	igeneralService.getCompanyList(sessionStateManage.getCompanyId(), sessionStateManage.getLanguageId());
	
		/*	String countryName =  igeneralService.getCountryName(sessionStateManage.getCountryId());
		//set Company Header Name
		if(countryName!=null && !countryName.equals("")){
		treasuryDealInfoPurchase.setCompanyHeaderName("ALMULLA EXCHANGE - "+countryName);
		}*/
		CompanyMasterDesc companyDesObj = companyDesc.get(0);
		
		//set company address
		if(companyDesObj!=null){
		StringBuffer companyAdd = new StringBuffer();
			if(companyDesObj.getCompanyName()!=null){
				companyAdd.append(companyDesObj.getCompanyName());
				companyAdd.append("\n");
			}
			if(companyDesObj.getFsCompanyMaster().getAddress1()!=null){
				companyAdd.append(companyDesObj.getFsCompanyMaster().getAddress1());
				companyAdd.append("\n");
			}
			if(companyDesObj.getFsCompanyMaster().getAddress2()!=null){
				companyAdd.append(companyDesObj.getFsCompanyMaster().getAddress2());
				companyAdd.append("\n");
			}
			if(companyDesObj.getFsCompanyMaster().getAddress3()!=null){
				companyAdd.append(companyDesObj.getFsCompanyMaster().getAddress3());
			}
			treasuryDealInfoPurchase.setCompanyAddress(companyAdd.toString());
		}
		
		//set company phone number
		treasuryDealInfoPurchase.setCompanyPhone(companyDesObj.getFsCompanyMaster().getTelephoneNo());
		
		//set company fax
		treasuryDealInfoPurchase.setCompanyFax(companyDesObj.getFsCompanyMaster().getFaxNo());
		
		//set company reg no
		treasuryDealInfoPurchase.setCompanyRegNo(companyDesObj.getFsCompanyMaster().getRegistrationNumber());
		
		//set company telex number
		treasuryDealInfoPurchase.setCompanyTelex(companyDesObj.getFsCompanyMaster().getTelexNo());
		
		//set company email 
		treasuryDealInfoPurchase.setCompanyEmail(companyDesObj.getFsCompanyMaster().getEmail());
		
		//set company share capital
		if(companyDesObj.getFsCompanyMaster().getCapitalAmount()!=null && companyDesObj.getFsCompanyMaster().getCurrencyId()!=null){
			 String currencyQuoteName = igeneralService.getCurrencyQuote(companyDesObj.getFsCompanyMaster().getCurrencyId());
			Integer d =  Integer.parseInt(companyDesObj.getFsCompanyMaster().getCapitalAmount());
			NumberFormat format = NumberFormat.getNumberInstance(new Locale("en", "in"));
			String capitalAmount = format.format(d).toString();
			
		treasuryDealInfoPurchase.setCompanyShareCaptal(currencyQuoteName+"   "+capitalAmount);
		}
		
		int i = 0;	
		for (TreasuryCustomerDeal viewTreasuryDeal : treasuryDealInfo1) {
			
			if(i==1){
				break;
			}
		
			
			List<Customer> clist = fxDealSupplierService .getAllCustomerList(getCustomerReference());
			String customerName=null;
			if(clist.size()>0){
				Customer customer=	clist.get(0);
				String sundryDebtorReference=customer.getSundryDebtorReference();
				
				if(sundryDebtorReference!=null)
				{
					setSundryDebtorReference(customer.getSundryDebtorReference());
					 customerName =customer.getFirstName();
					
				}
			}
			
			treasuryDealInfoPurchase.setDealWith(customerName);
			treasuryDealInfoPurchase.setBankAddress(viewTreasuryDeal.getBankAddress());
			treasuryDealInfoPurchase.setCurrencyname(viewTreasuryDeal.getCurrencyName());
			treasuryDealInfoPurchase.setDealConcludedBy(viewTreasuryDeal.getDealConcludedBy());
			treasuryDealInfoPurchase.setDealConcludedWith(viewTreasuryDeal.getDealConcludedWith());
			treasuryDealInfoPurchase.setDealDescription(viewTreasuryDeal.getDealDescription());
			treasuryDealInfoPurchase.setDocumentDate(viewTreasuryDeal.getDocumentDate());
			treasuryDealInfoPurchase.setDocumentFinanceYear(viewTreasuryDeal.getDocumentFinanceYear());
			treasuryDealInfoPurchase.setApplicationCountryId(viewTreasuryDeal.getApplicationCountryId());
			treasuryDealInfoPurchase.setDealWithType(viewTreasuryDeal.getDealWithType());
			treasuryDealInfoPurchase.setDealNo(viewTreasuryDeal.getDealNo());
			
			BigDecimal totalPurchaseFcAmount = BigDecimal.ZERO;
			if(viewTreasuryDeal.getTotalPurchaseFcAmount()!=null && viewTreasuryDeal.getCurrencyId()!=null){
				totalPurchaseFcAmount = round((viewTreasuryDeal.getTotalPurchaseFcAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(viewTreasuryDeal.getCurrencyId()));
			}
			
			treasuryDealInfoPurchase.setTotalPurchaseFcAmount(totalPurchaseFcAmount);
			treasuryDealInfoPurchase.setPurchaseExchangeRate(viewTreasuryDeal.getPurchaseExchangeRate());
			treasuryDealInfoPurchase.setFaAccountNo(viewTreasuryDeal.getFaAccountNumber());
			treasuryDealInfoPurchase.setFcAmount(viewTreasuryDeal.getFcAmount());
			treasuryDealInfoPurchase.setLocalExchangeRate(viewTreasuryDeal.getLocalExchangeRate());
			treasuryDealInfoPurchase.setLocalAmount(viewTreasuryDeal.getLocalAmount());
			treasuryDealInfoPurchase.setLineType(viewTreasuryDeal.getLineType());
			treasuryDealInfoPurchase.setValuedatePD(viewTreasuryDeal.getValueDate());
			treasuryDealInfoPurchase.setCurrencyCode(viewTreasuryDeal.getCurrencyCode());
			treasuryDealInfoPurchase.setLogoPath(logoPath);
			treasuryDealInfoPurchase.setRemarks(viewTreasuryDeal.getRemarks());
			
			List<Employee> empList = generalService.getEmployeeDetail(sessionStateManage.getEmployeeId());
			if(empList.size()>0){
				treasuryDealInfoPurchase.setAuthorizedBy(empList.get(0).getEmployeeName());
			}
			treasuryDealInfoPurchase.setDealer(viewTreasuryDeal.getDealConcludedBy());
			
			if(viewTreasuryDeal.getAccountNumber()!=null){
			treasuryDealInfoPurchase.setPdAcNo(viewTreasuryDeal.getAccountNumber());
			}else{
				treasuryDealInfoPurchase.setPdAcNo("");
			}
			if(viewTreasuryDeal.getBankCode()!=null){
				treasuryDealInfoPurchase.setPdBankCode(viewTreasuryDeal.getBankCode());
			}else{
				treasuryDealInfoPurchase.setPdBankCode("");
			}
			treasuryDealInfoPurchase.setFaxNo("1");
			BigDecimal denominationId = generalService.getDenomiationId(Constants.KUWAIT_QUOTE_NAME);
			BigDecimal totalFaAmount = BigDecimal.ZERO;
			
			if(viewTreasuryDeal.getTotalPurchaseFcAmount()!=null && viewTreasuryDeal.getLocalExchangeRate()!=null && denominationId!=null){
				totalFaAmount = round((viewTreasuryDeal.getTotalPurchaseFcAmount().multiply(viewTreasuryDeal.getLocalExchangeRate())),denominationId.intValue());
			}
			
			treasuryDealInfoPurchase.setTotalFaAmount(totalFaAmount);
			treasuryDealInfoPurchase.setTotalPdAmount(totalFaAmount);
			
		//	treasuryDealInfoPurchase.setPurchaseStandardInstruction(viewTreasuryDeal.getStandardInstruction());
			
			treasuryDealInfoPurchase.setPurchaseCurrencyCode(generalService.getCurrencyQuote(viewTreasuryDeal.getCurrencyId()));

			for (TreasuryCustomerDeal viewTreasuryDeal1 : saleList) {
				treasuryDealInfoPurchase.setCurrencynameSD(viewTreasuryDeal1.getCurrencyName());
				treasuryDealInfoPurchase.setSdBankCode(viewTreasuryDeal1.getBankCode());
				treasuryDealInfoPurchase.setSdAcNo(viewTreasuryDeal1.getAccountNumber());
				treasuryDealInfoPurchase.setSoldAccountNo(viewTreasuryDeal1.getFaAccountNumber());
			//	BigDecimal saleAmount=round((viewTreasuryDeal1.getSaleAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(viewTreasuryDeal1.getCurrencyId()));
				treasuryDealInfoPurchase.setSaleAmount(viewTreasuryDeal1.getSaleAmount());
				treasuryDealInfoPurchase.setSaleExchangeRate(viewTreasuryDeal1.getLocalExchangeRate());
				BigDecimal totalSoldAmount=round((viewTreasuryDeal1.getSaleAmount().multiply(viewTreasuryDeal1.getLocalExchangeRate())),denominationId.intValue());
				treasuryDealInfoPurchase.setTotalSoldAmount(totalSoldAmount);
				treasuryDealInfoPurchase.setSaleCurrencyCode(generalService.getCurrencyQuote(viewTreasuryDeal1.getCurrencyId()));
				treasuryDealInfoPurchase.setValuedateSD(viewTreasuryDeal1.getValueDate());
			//	treasuryDealInfoPurchase.setSalesStandardInstruction(viewTreasuryDeal1.getStandardInstruction());
			}
			treasuryDealInfoPurchase.setSubReportPath(subReportPath);
			treasuryDealInfoList1.add(treasuryDealInfoPurchase);
			treasuryDealInfoPurchase.setTreasuryDealInfoList(treasuryDealInfoList1);
			
			treasuryDealInfoList.add(treasuryDealInfoPurchase);
			i++;
		}

	}
	
	
	public void fetchDataForDealPaymentReport(BigDecimal documentNumber)throws Exception {

		treasuryDealInfoList=new CopyOnWriteArrayList<TreasuryDealInfo>();
		List<TreasuryDealInfo> treasuryDealInfoList1=new CopyOnWriteArrayList<TreasuryDealInfo>();
		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath = ctx.getRealPath("//");
		//String logoPath = realPath + Constants.LOGO_PATH;
		
		String logoPath =null;
		if(sessionStateManage.getCountryName().equalsIgnoreCase(Constants.KUWAIT)){
			logoPath = realPath+Constants.LOGO_PATH;
		}else if(sessionStateManage.getCountryName().equalsIgnoreCase(Constants.OMAN)){
			logoPath = realPath+Constants.LOGO_PATH_OM;
		}else{
			logoPath =realPath+Constants.LOGO_PATH;
		}
		
		String subReportPath = realPath + Constants.SUB_REPORT_PATH;
		List<TreasuryCustomerDeal> treasuryDealInfo1 = new ArrayList<TreasuryCustomerDeal>();
		List<TreasuryCustomerDeal> saleList = new ArrayList<TreasuryCustomerDeal>();

		List<TreasuryCustomerDeal> dealTicketList = new ArrayList<TreasuryCustomerDeal>();
		
		List<TreasuryCustomerDeal> treasuryCustList = fXDetailInformationService.getTreasuryCustomerDealAndPaymentValues(sessionStateManage.getCompanyId(),new BigDecimal(Constants.DOCUMENT_CODE_FOR_FX_DEAL_WITH_BANK),new BigDecimal(getFinaceYear()), documentNumber);
	//	List<TreasuryCustomerDeal> treasuryCustList = fXDetailInformationService.getTreasuryCustomerDealAndPaymentValues(new BigDecimal(1),new BigDecimal(70), new BigDecimal(2015), new BigDecimal(599));
		
		for(TreasuryCustomerDeal treasuryCustDeal : treasuryCustList){
			if(treasuryCustDeal.getPaymentIndicator().trim().equalsIgnoreCase(Constants.Yes)){
				dealTicketList.add(treasuryCustDeal);
			}
		}

		for(TreasuryCustomerDeal treasuryCustDeal : dealTicketList){
			if(treasuryCustDeal.getLineType()!=null){
				if(treasuryCustDeal.getLineType().equalsIgnoreCase(Constants.PY)){
					treasuryDealInfo1.add(treasuryCustDeal);
				}else if(treasuryCustDeal.getLineType().equalsIgnoreCase(Constants.SD)){
					saleList.add(treasuryCustDeal);
				}
			}
		}
		
		TreasuryDealInfo	treasuryDealInfoPurchase = new TreasuryDealInfo();
		
		List<CompanyMasterDesc> companyDesc =	igeneralService.getCompanyList(sessionStateManage.getCompanyId(), sessionStateManage.getLanguageId());
	
		/*	String countryName =  igeneralService.getCountryName(sessionStateManage.getCountryId());
		//set Company Header Name
		if(countryName!=null && !countryName.equals("")){
		treasuryDealInfoPurchase.setCompanyHeaderName("ALMULLA EXCHANGE - "+countryName);
		}*/
		CompanyMasterDesc companyDesObj = companyDesc.get(0);
		
		//set company address
		if(companyDesObj!=null){
		StringBuffer companyAdd = new StringBuffer();
			if(companyDesObj.getCompanyName()!=null){
				companyAdd.append(companyDesObj.getCompanyName());
				companyAdd.append("\n");
			}
			if(companyDesObj.getFsCompanyMaster().getAddress1()!=null){
				companyAdd.append(companyDesObj.getFsCompanyMaster().getAddress1());
				companyAdd.append("\n");
			}
			if(companyDesObj.getFsCompanyMaster().getAddress2()!=null){
				companyAdd.append(companyDesObj.getFsCompanyMaster().getAddress2());
				companyAdd.append("\n");
			}
			if(companyDesObj.getFsCompanyMaster().getAddress3()!=null){
				companyAdd.append(companyDesObj.getFsCompanyMaster().getAddress3());
			}
			treasuryDealInfoPurchase.setCompanyAddress(companyAdd.toString());
		}
		
		//set company phone number
		treasuryDealInfoPurchase.setCompanyPhone(companyDesObj.getFsCompanyMaster().getTelephoneNo());
		
		//set company fax
		treasuryDealInfoPurchase.setCompanyFax(companyDesObj.getFsCompanyMaster().getFaxNo());
		
		//set company reg no
		treasuryDealInfoPurchase.setCompanyRegNo(companyDesObj.getFsCompanyMaster().getRegistrationNumber());
		
		//set company telex number
		treasuryDealInfoPurchase.setCompanyTelex(companyDesObj.getFsCompanyMaster().getTelexNo());
		
		//set company email 
		treasuryDealInfoPurchase.setCompanyEmail(companyDesObj.getFsCompanyMaster().getEmail());
		
		//set company share capital
		if(companyDesObj.getFsCompanyMaster().getCapitalAmount()!=null && companyDesObj.getFsCompanyMaster().getCurrencyId()!=null){
			 String currencyQuoteName = igeneralService.getCurrencyQuote(companyDesObj.getFsCompanyMaster().getCurrencyId());
			Integer d =  Integer.parseInt(companyDesObj.getFsCompanyMaster().getCapitalAmount());
			NumberFormat format = NumberFormat.getNumberInstance(new Locale("en", "in"));
			String capitalAmount = format.format(d).toString();
			
		treasuryDealInfoPurchase.setCompanyShareCaptal(currencyQuoteName+"   "+capitalAmount);
		}
		
		int i = 0;	
		for (TreasuryCustomerDeal viewTreasuryDeal : treasuryDealInfo1) {
			
			if(i==1){
				break;
			}
		
			List<Customer> clist = fxDealSupplierService .getAllCustomerList(getCustomerReference());
			String customerName=null;
			if(clist.size()>0){
				Customer customer=	clist.get(0);
				String sundryDebtorReference=customer.getSundryDebtorReference();
				
				if(sundryDebtorReference!=null)
				{
					setSundryDebtorReference(customer.getSundryDebtorReference());
					 customerName =customer.getFirstName();
					
				}
			}
			
			treasuryDealInfoPurchase.setDealWith(customerName);
			treasuryDealInfoPurchase.setBankAddress(viewTreasuryDeal.getBankAddress());
			treasuryDealInfoPurchase.setCurrencyname(viewTreasuryDeal.getCurrencyName());
			treasuryDealInfoPurchase.setDealConcludedBy(viewTreasuryDeal.getDealConcludedBy());
			treasuryDealInfoPurchase.setDealConcludedWith(viewTreasuryDeal.getDealConcludedWith());
			treasuryDealInfoPurchase.setDealDescription(viewTreasuryDeal.getDealDescription());
			treasuryDealInfoPurchase.setDocumentDate(viewTreasuryDeal.getDocumentDate());
			treasuryDealInfoPurchase.setDocumentFinanceYear(viewTreasuryDeal.getDocumentFinanceYear());
			treasuryDealInfoPurchase.setApplicationCountryId(viewTreasuryDeal.getApplicationCountryId());
			treasuryDealInfoPurchase.setDealWithType(viewTreasuryDeal.getDealWithType());
			
			if(viewTreasuryDeal.getPaymentFinanceYear()!=null && viewTreasuryDeal.getPaymentDocumentNumber()!=null){
				treasuryDealInfoPurchase.setDealNo(viewTreasuryDeal.getPaymentFinanceYear()+" / "+viewTreasuryDeal.getPaymentDocumentNumber());
			}
			
			BigDecimal totalPurchaseFcAmount = BigDecimal.ZERO;
			if(viewTreasuryDeal.getTotalPurchaseFcAmount()!=null && viewTreasuryDeal.getCurrencyId()!=null){
				totalPurchaseFcAmount = round((viewTreasuryDeal.getTotalPurchaseFcAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(viewTreasuryDeal.getCurrencyId()));
			}
			
			treasuryDealInfoPurchase.setTotalPurchaseFcAmount(totalPurchaseFcAmount);
			treasuryDealInfoPurchase.setPurchaseExchangeRate(viewTreasuryDeal.getPurchaseExchangeRate());
			treasuryDealInfoPurchase.setFaAccountNo(viewTreasuryDeal.getFaAccountNumber());
			treasuryDealInfoPurchase.setFcAmount(viewTreasuryDeal.getFcAmount());
			treasuryDealInfoPurchase.setLocalExchangeRate(viewTreasuryDeal.getLocalExchangeRate());
			treasuryDealInfoPurchase.setLocalAmount(viewTreasuryDeal.getLocalAmount());
			treasuryDealInfoPurchase.setLineType(viewTreasuryDeal.getLineType());
			treasuryDealInfoPurchase.setValuedatePD(viewTreasuryDeal.getValueDate());
			treasuryDealInfoPurchase.setCurrencyCode(viewTreasuryDeal.getCurrencyCode());
			treasuryDealInfoPurchase.setLogoPath(logoPath);
			treasuryDealInfoPurchase.setRemarks(viewTreasuryDeal.getRemarks());
			
			List<Employee> empList = generalService.getEmployeeDetail(sessionStateManage.getEmployeeId());
			if(empList.size()>0){
				treasuryDealInfoPurchase.setAuthorizedBy(empList.get(0).getEmployeeName());
			}
			treasuryDealInfoPurchase.setDealer(viewTreasuryDeal.getDealConcludedBy());
			
			if(viewTreasuryDeal.getAccountNumber()!=null){
			treasuryDealInfoPurchase.setPdAcNo(viewTreasuryDeal.getAccountNumber());
			}else{
				treasuryDealInfoPurchase.setPdAcNo("");
			}
			if(viewTreasuryDeal.getBankCode()!=null){
				treasuryDealInfoPurchase.setPdBankCode(viewTreasuryDeal.getBankCode());
			}else{
				treasuryDealInfoPurchase.setPdBankCode("");
			}
			treasuryDealInfoPurchase.setFaxNo("1");
			BigDecimal denominationId = generalService.getDenomiationId(Constants.KUWAIT_QUOTE_NAME);
			BigDecimal totalFaAmount = BigDecimal.ZERO;
			
			if(viewTreasuryDeal.getTotalPurchaseFcAmount()!=null && viewTreasuryDeal.getLocalExchangeRate()!=null && denominationId!=null){
				totalFaAmount = round((viewTreasuryDeal.getTotalPurchaseFcAmount().multiply(viewTreasuryDeal.getLocalExchangeRate())),denominationId.intValue());
			}
			
			treasuryDealInfoPurchase.setTotalFaAmount(totalFaAmount);
			treasuryDealInfoPurchase.setTotalPdAmount(totalFaAmount);
			
		//	treasuryDealInfoPurchase.setPurchaseStandardInstruction(viewTreasuryDeal.getStandardInstruction());
			
			treasuryDealInfoPurchase.setPurchaseCurrencyCode(generalService.getCurrencyQuote(viewTreasuryDeal.getCurrencyId()));

			for (TreasuryCustomerDeal viewTreasuryDeal1 : saleList) {
				treasuryDealInfoPurchase.setCurrencynameSD(viewTreasuryDeal1.getCurrencyName());
				treasuryDealInfoPurchase.setSdBankCode(viewTreasuryDeal1.getBankCode());
				treasuryDealInfoPurchase.setSdAcNo(viewTreasuryDeal1.getAccountNumber());
				treasuryDealInfoPurchase.setSoldAccountNo(viewTreasuryDeal1.getFaAccountNumber());
			//	BigDecimal saleAmount=round((viewTreasuryDeal1.getSaleAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(viewTreasuryDeal1.getCurrencyId()));
				treasuryDealInfoPurchase.setSaleAmount(viewTreasuryDeal1.getSaleAmount());
				treasuryDealInfoPurchase.setSaleExchangeRate(viewTreasuryDeal1.getLocalExchangeRate());
				BigDecimal totalSoldAmount=round((viewTreasuryDeal1.getSaleAmount().multiply(viewTreasuryDeal1.getLocalExchangeRate())),denominationId.intValue());
				treasuryDealInfoPurchase.setTotalSoldAmount(totalSoldAmount);
				treasuryDealInfoPurchase.setSaleCurrencyCode(generalService.getCurrencyQuote(viewTreasuryDeal1.getCurrencyId()));
				treasuryDealInfoPurchase.setValuedateSD(viewTreasuryDeal1.getValueDate());
			//	treasuryDealInfoPurchase.setSalesStandardInstruction(viewTreasuryDeal1.getStandardInstruction());
			}
			treasuryDealInfoPurchase.setSubReportPath(subReportPath);
			treasuryDealInfoList1.add(treasuryDealInfoPurchase);
			treasuryDealInfoPurchase.setTreasuryDealInfoList(treasuryDealInfoList1);
			
			treasuryDealInfoList.add(treasuryDealInfoPurchase);
			i++;
		}

	}
	
	
	
}
