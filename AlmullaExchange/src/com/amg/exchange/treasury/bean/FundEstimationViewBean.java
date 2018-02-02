package com.amg.exchange.treasury.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.treasury.model.FundEstimationDays;
import com.amg.exchange.treasury.service.FundEstimationDetailsBeanService;
import com.amg.exchange.treasury.service.IFundEstimationService;
import com.amg.exchange.treasury.viewModel.TreasuryFundestimationViewSummary;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("fundEstimationBeanView")
@Scope("session")
public class FundEstimationViewBean<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public static final Logger log = Logger.getLogger(FundEstimationViewBean.class);
	private String errorMessage;
	private Date projectionDate;
	private BigDecimal companyId;
	private String countryName;
	private BigDecimal bankCountry;
	private BigDecimal currencyId;
	private Boolean renderDataTable=false;
	private Boolean dateInputRender=false;
	private Boolean calenderRender =false;
	
	private BigDecimal cashAmount;
	public BigDecimal getCashAmount() {
		return cashAmount;
	}

	public void setCashAmount(BigDecimal cashAmount) {
		this.cashAmount = cashAmount;
	}

	public BigDecimal getSpotAmount() {
		return spotAmount;
	}

	public void setSpotAmount(BigDecimal spotAmount) {
		this.spotAmount = spotAmount;
	}

	public BigDecimal getTomAmount() {
		return tomAmount;
	}

	public void setTomAmount(BigDecimal tomAmount) {
		this.tomAmount = tomAmount;
	}

	private BigDecimal spotAmount;
	private BigDecimal tomAmount;
	
	private SessionStateManage session = new SessionStateManage();
	
	
	private List<BankCountryPopulationBean> bankCountryList;
	private List<CountryCurrencyPopulationBean> countryCurrencyList;
	private List<FundEstimationPopulationBean> fundEstimationList;
	
	private Date currentDate = new Date();
	
	@Autowired
	IFundEstimationService<T> fundEstimationService;
	
	@Autowired
	FundEstimationDetailsBeanService fundEstimationDetailsBeanService;
	
	
	
	
	
	public Boolean getDateInputRender() {
		return dateInputRender;
	}

	public Boolean getCalenderRender() {
		return calenderRender;
	}

	public void setDateInputRender(Boolean dateInputRender) {
		this.dateInputRender = dateInputRender;
	}

	public void setCalenderRender(Boolean calenderRender) {
		this.calenderRender = calenderRender;
	}

	public List<FundEstimationPopulationBean> getFundEstimationList() {
		return fundEstimationList;
	}

	public void setFundEstimationList(
			List<FundEstimationPopulationBean> fundEstimationList) {
		this.fundEstimationList = fundEstimationList;
	}

	public List<BankCountryPopulationBean> getBankCountryList() {
		return bankCountryList;
	}

	public void setBankCountryList(List<BankCountryPopulationBean> bankCountryList) {
		this.bankCountryList = bankCountryList;
	}

	public List<CountryCurrencyPopulationBean> getCountryCurrencyList() {
		return countryCurrencyList;
	}

	public void setCountryCurrencyList(
			List<CountryCurrencyPopulationBean> countryCurrencyList) {
		this.countryCurrencyList = countryCurrencyList;
	}


	public String getErrorMessage() {
		return errorMessage;
	}


	public Date getProjectionDate() {
		return projectionDate;
	}


	public BigDecimal getCompanyId() {
		return companyId;
	}

	public String getCountryName() {
		return countryName;
	}

	public BigDecimal getBankCountry() {
		return bankCountry;
	}

	public BigDecimal getCurrencyId() {
		return currencyId;
	}

	public Boolean getRenderDataTable() {
		return renderDataTable;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void setProjectionDate(Date projectionDate) {
		this.projectionDate = projectionDate;
	}


	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}


	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}


	public void setBankCountry(BigDecimal bankCountry) {
		this.bankCountry = bankCountry;
	}

	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	public void setRenderDataTable(Boolean renderDataTable) {
		this.renderDataTable = renderDataTable;
	}

	public void exit() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void dateChange(SelectEvent event){
		 Date date = (Date) event.getObject();
	    System.out.print(date.toString());
	    setProjectionDate(date);
	    setCurrencyId(null);
	    setBankCountry(null);
	}
	
	
	
	public void populateBankCountry(){
		List<BankCountryPopulationBean> lstBankCountry= fundEstimationService.getBankContryFromView(session.getCountryId());
		setBankCountryList(lstBankCountry);
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void navigateToFundEstimationView() {
		setBankCountry(null);
		setCurrencyId(null);
		setDateInputRender(true);
		setCalenderRender(false);
		setProjectionDate(new Date());
		setCountryName(fundEstimationService.getCountryNameDesc(session.getCountryId(), session.getLanguageId()));
		setCompanyId(session.getCompanyId());
		populateBankCountry();
		setFundEstimationList(null);
		setRenderDataTable(false);
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "fundEstimationView.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/fundEstimationView.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void switchDateField(){
		setDateInputRender(false);
		setCalenderRender(true);
	}
	
	public void clear(){
		setCountryCurrencyList(null);
		setFundEstimationList(null);
		setBankCountry(null);
		setCurrencyId(null);
		setRenderDataTable(false);
		
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/fundEstimationView.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void populateCurrency(){
		setCurrencyId(null);
		if(getBankCountry()!=null){
			List<CountryCurrencyPopulationBean> lstCountryCurrency = fundEstimationService.getBankCurrencyFromView(session.getCountryId(), getBankCountry());
			if (lstCountryCurrency.size() > 0) {
				setCountryCurrencyList(lstCountryCurrency);
			}
		}
	}
	
	public void searchFundEstimation() {
		
		try{
		setFundEstimationList(null);
		List<FundEstimationPopulationBean>	fundEstimationList = new ArrayList<FundEstimationPopulationBean>();
		//List<TreasuryFundestimationView> fundEstimationfromViewList = fundEstimationService.getDetaillsForFundEstimationView(session.getCountryId(), getBankCountry(), getCurrencyId());
		List<TreasuryFundestimationViewSummary> fundEstimationfromViewList = fundEstimationService.getDetaillsForFundEstimationViewSummary(session.getCountryId(), getBankCountry(), getCurrencyId(),getProjectionDate());
		if(fundEstimationfromViewList.size()>0){	
			NumberFormat usa = NumberFormat.getInstance(Locale.getDefault());
			for (TreasuryFundestimationViewSummary treasuryFundestimationView : fundEstimationfromViewList) {
				FundEstimationPopulationBean fundEstimationPopulationBean = new FundEstimationPopulationBean();
				fundEstimationPopulationBean.setApplicationCountryId(treasuryFundestimationView.getApplicationCountryId());
	
				fundEstimationPopulationBean.setSrNo(treasuryFundestimationView.getSrNo());
				fundEstimationPopulationBean.setApplicationCountryId(treasuryFundestimationView.getApplicationCountryId());
	
				fundEstimationPopulationBean.setAdditionalSalesProjAmnt(new BigDecimal(0).setScale(fundEstimationDetailsBeanService.getDecimalValue(treasuryFundestimationView.getCurrencyId()), RoundingMode.FLOOR));
				
				fundEstimationPopulationBean.setBankCountryId(treasuryFundestimationView.getBankCountryId());
				fundEstimationPopulationBean.setBankId(treasuryFundestimationView.getBankId());
				fundEstimationPopulationBean.setBankCountryId(treasuryFundestimationView.getBankCountryId());
				fundEstimationPopulationBean.setBankShortName(treasuryFundestimationView.getBankShortName());
				fundEstimationPopulationBean.setCurrencyId(treasuryFundestimationView.getCurrencyId());
				fundEstimationPopulationBean.setForeignCurrencyBalance(treasuryFundestimationView.getForeignCurrencyBalance());
				fundEstimationPopulationBean.setCashAmount(treasuryFundestimationView.getCashAmount() == null? BigDecimal.ZERO : treasuryFundestimationView.getCashAmount());
				fundEstimationPopulationBean.setSpotAmount(treasuryFundestimationView.getSpotAmount()== null? BigDecimal.ZERO : treasuryFundestimationView.getSpotAmount());
				fundEstimationPopulationBean.setTomAmount(treasuryFundestimationView.getTomAmount() == null? BigDecimal.ZERO : treasuryFundestimationView.getTomAmount());
				if(treasuryFundestimationView.getForeignCurrencyBalance()!=null)
				{
					fundEstimationPopulationBean.setForeignCurrencyBalanceForDisplay(usa.format(treasuryFundestimationView.getForeignCurrencyBalance()));
				}
				 if(treasuryFundestimationView.getSalesProjAmnt()!=null){			
				fundEstimationPopulationBean.setSalesProjAmnt(treasuryFundestimationView.getSalesProjAmnt().setScale(fundEstimationDetailsBeanService.getDecimalValue(treasuryFundestimationView.getCurrencyId()), RoundingMode.FLOOR));
				fundEstimationPopulationBean.setSalesProjAmntForDisplay(usa.format(treasuryFundestimationView.getSalesProjAmnt()));
				 }else{
					 throw new Exception("Sales Projection Amount is null:: salesProjAmnt" );
				 }
				fundEstimationPopulationBean.setServiceId(treasuryFundestimationView.getServiceId());
				fundEstimationPopulationBean.setServiceMasterDesc(treasuryFundestimationView.getServiceMasterDesc());
				
				BigDecimal totalAmt = fundEstimationPopulationBean.getCashAmount().add(fundEstimationPopulationBean.getTomAmount()).add(fundEstimationPopulationBean.getSpotAmount()).add(fundEstimationPopulationBean.getSalesProjAmnt());
				//BigDecimal usaTotalAmt=totalAmt.divide(getiKONRate(),RoundingMode.FLOOR);

				fundEstimationPopulationBean.setTotalProjectionAmount(totalAmt);
				//fundEstimationPopulationBean.setUsdTotalProjectionAmount(usaTotalAmt);
				//fundEstimationPopulationBean.setUsdTotalProjectionAmountForDisplay(usa.format(usaTotalAmt));
				fundEstimationPopulationBean.setTotalProjectionAmountForDisplay(usa.format(totalAmt));
				
				//fundEstimationPopulationBean.setTotalProjectionAmount(new BigDecimal(0).setScale(fundEstimationDetailsBeanService.getDecimalValue(treasuryFundestimationView.getCurrencyId()), RoundingMode.FLOOR));
				//fundEstimationPopulationBean.setTotalProjectionAmountForDisplay(usa.format(treasuryFundestimationView.getSalesProjAmnt()));
				
				if(treasuryFundestimationView.getUnfundedTrnx()!=null){
				fundEstimationPopulationBean.setUnfundedTrnx(treasuryFundestimationView.getUnfundedTrnx().setScale(fundEstimationDetailsBeanService.getDecimalValue(treasuryFundestimationView.getCurrencyId()), RoundingMode.FLOOR));
				fundEstimationPopulationBean.setUnfundedTrnxForDisplay(usa.format(treasuryFundestimationView.getUnfundedTrnx())); 
				}else{
					 throw new Exception(" Unfunded Transaction  is null:: unfundedTrnx" );
				}
				fundEstimationPopulationBean.setCompanyId(treasuryFundestimationView.getCompanyId());
				fundEstimationPopulationBean.setProjectionDate(treasuryFundestimationView.getProjectionDate());
				fundEstimationPopulationBean.setUsdTotalProjectionAmount(treasuryFundestimationView.getUsdTotalsalesProjAmnt());
				fundEstimationPopulationBean.setUsdTotalProjectionAmountForDisplay(usa.format(treasuryFundestimationView.getUsdTotalsalesProjAmnt()));
				
				
				
				
				//fundEstimationPopulationBean.setFundEstimationId(treasuryFundestimationView.getFundEstimationId());
				
			    /*if(treasuryFundestimationView.getBankId()!=null)
				{
					List<FundEstimationDaysDtable> localAddingDaysForCalculation =addViwRecords(treasuryFundestimationView.getBankId(),treasuryFundestimationView.getServiceId());
					fundEstimationPopulationBean.setAddingDaysForCalculation(localAddingDaysForCalculation);
				}*/
				
				fundEstimationList.add(fundEstimationPopulationBean);
			}
		}else {
			RequestContext.getCurrentInstance().execute("noRecords.show()");
		}
		
		if(fundEstimationList.size()>0){
			setFundEstimationList(fundEstimationList);
			setRenderDataTable(true);
		}else {
			setRenderDataTable(false);
		}
		}catch(NullPointerException ne){
			  log.info("Save::"+ne.getMessage());
			  setErrorMessage("Method Name::Search"); 
			    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			    return; 
		}catch (Exception e){
			log.info("Search :"+e.getMessage());
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("sqlexception.show();");
			return;
		}	
	}
	
	private List<FundEstimationDaysDtable>  addViwRecords(BigDecimal bankId,BigDecimal serviceId)
	{
		List<FundEstimationDaysDtable> localAddingDaysForCalculation = new ArrayList<FundEstimationDaysDtable>();
		try {

			HashMap<String, String> inputValues = new HashMap<String, String>();
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			
			inputValues.put("P_APPLICATION_COUNTRY_ID",  session.getCountryId().toPlainString());
			if(getBankCountry()!=null){
			inputValues.put("P_BANK_COUNTRY_ID", getBankCountry().toPlainString());
			}
			if(getCurrencyId()!=null){
			inputValues.put("P_CURRENCY_ID", getCurrencyId().toPlainString());
			}
			if(bankId!=null){
			inputValues.put("P_BANK_ID", bankId.toPlainString());
			}
			if(serviceId!=null){
			inputValues.put("P_SERVICE_ID", serviceId.toPlainString());
			}
			inputValues.put("P_COMPANY_ID", session.getCompanyId().toString());
			inputValues.put("P_PROJECTION_DATE",format.format(getProjectionDate()));
			
			List<FundEstimationDays> lstFundEstimationDays= fundEstimationDetailsBeanService.getFundEstimationDyas(inputValues);
		
			if(lstFundEstimationDays!=null && lstFundEstimationDays.size()>0)
			{
				for(FundEstimationDays fundEstimationDays :lstFundEstimationDays)
				{
					
					FundEstimationDaysDtable fundestmDaysdtable = new FundEstimationDaysDtable();

					fundestmDaysdtable.setProjectiondate(format.format(projectionDate));

					fundestmDaysdtable.setPreviousMonthDate1(format.format(fundEstimationDays.getPreviousMonthDate1()));
					fundestmDaysdtable.setPreviousMonthValue1(fundEstimationDays.getPreviousMonthValue1());		

					fundestmDaysdtable.setPreviousMonthDate2(format.format(fundEstimationDays.getPreviousMonthDate2()));
					fundestmDaysdtable.setPreviousMonthValue2(fundEstimationDays.getPreviousMonthValue2());

					fundestmDaysdtable.setPreviousMonthDate3(format.format(fundEstimationDays.getPreviousMonthDate3()));
					fundestmDaysdtable.setPreviousMonthValue3(fundEstimationDays.getPreviousMonthValue3());

					fundestmDaysdtable.setPreviousMonthWeekDate1(format.format(fundEstimationDays.getPreviousMonthWeekDate1()));
					fundestmDaysdtable.setPreviousMonthWeekValue1(fundEstimationDays.getPreviousMonthWeekValue1());

					fundestmDaysdtable.setPreviousMonthWeekDate2(format.format(fundEstimationDays.getPreviousMonthWeekDate2()));
					fundestmDaysdtable.setPreviousMonthWeekValue2(fundEstimationDays.getPreviousMonthWeekValue2());

					fundestmDaysdtable.setPreviousMonthWeekDate3(format.format(fundEstimationDays.getPreviousMonthWeekDate3()));
					fundestmDaysdtable.setPreviousMonthWeekValue3(fundEstimationDays.getPreviousMonthWeekValue3());
					fundestmDaysdtable.setAlreadyInsert(true);
					
					localAddingDaysForCalculation.add(fundestmDaysdtable);
				}
			}/*else {
				for (int i = 0; i < getNoOfDaysEstimation().intValue(); i++) {

					
					Date projectionDate=DateUtil.addDays(getProjectionDate(), i);

					inputValues.put("P_PROJECTION_DATE",format.format(projectionDate));

					HashMap<String, String> outPutValues =fundEstimationDetailsBeanService.callFundEstimation(inputValues);

					FundEstimationDaysDtable fundestmDaysdtable = new FundEstimationDaysDtable();

					fundestmDaysdtable.setProjectiondate(format.format(projectionDate));

					fundestmDaysdtable.setPreviousMonthDate1(outPutValues.get("P_PREVIOUS_MONTH_DT1"));
					fundestmDaysdtable.setPreviousMonthValue1(outPutValues.get("P_PREVIOUS_MONTH_1"));		

					fundestmDaysdtable.setPreviousMonthDate2(outPutValues.get("P_PREVIOUS_MONTH_DT2"));
					fundestmDaysdtable.setPreviousMonthValue2(outPutValues.get("P_PREVIOUS_MONTH_2"));

					fundestmDaysdtable.setPreviousMonthDate3(outPutValues.get("P_PREVIOUS_MONTH_DT3"));
					fundestmDaysdtable.setPreviousMonthValue3(outPutValues.get("P_PREVIOUS_MONTH_3"));

					fundestmDaysdtable.setPreviousMonthWeekDate1(outPutValues.get("P_PREVIOUS_WEEK_DT1"));
					fundestmDaysdtable.setPreviousMonthWeekValue1(outPutValues.get("P_PREVIOUS_WEEK_1"));

					fundestmDaysdtable.setPreviousMonthWeekDate2(outPutValues.get("P_PREVIOUS_WEEK_DT2"));
					fundestmDaysdtable.setPreviousMonthWeekValue2(outPutValues.get("P_PREVIOUS_WEEK_2"));

					fundestmDaysdtable.setPreviousMonthWeekDate3(outPutValues.get("P_PREVIOUS_WEEK_DT3"));
					fundestmDaysdtable.setPreviousMonthWeekValue3(outPutValues.get("P_PREVIOUS_WEEK_3"));
					fundestmDaysdtable.setAlreadyInsert(false);
					
					localAddingDaysForCalculation.add(fundestmDaysdtable);

				}
			}*/
			
		}catch(NullPointerException ne){
			  log.info("Save::"+ne.getMessage());
			  setErrorMessage("Method Name::Search"); 
			    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			    return null; 
		}catch (Exception e){
			log.info("Search :"+e.getMessage());
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("sqlexception.show();");
			return null;
		}	
			
		return localAddingDaysForCalculation;
	}

	public Date getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}
	
}
