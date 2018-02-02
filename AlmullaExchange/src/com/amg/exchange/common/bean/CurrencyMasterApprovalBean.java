package com.amg.exchange.common.bean;

import java.io.IOException;
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

import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.service.ICurrencyService;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.CurrencyOtherInformation;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;
@Component("currencyMasterApprovalBean")
@Scope("session")
public class CurrencyMasterApprovalBean<T> implements Serializable {

	private static Logger log = Logger.getLogger(CurrencyMasterApprovalBean.class);
	private static final long serialVersionUID = 1L;
	private BigDecimal currencyMasterPk;
	private BigDecimal currencyCountryId;
	
	private String currencyCode;
	private String currencyName;
	private String quoteName;
	//private Byte currencyDesc;
	private String countryName;
	
	private String decimalName;
	private String fimsCurrencyCode;
	private String isActive;

	private String arabicCurrencyName;
	private String arabicDecimalName;
	private String arabicQuoteName;
	private String swiftCurrency;
	
	private BigDecimal fundMinRate;
	private BigDecimal fundMaxRate;
	private String isoCurrencyCode;
	
	private BigDecimal cashMinRate;
	private BigDecimal cashMaxRate;
	
	private BigDecimal highValue;
	private BigDecimal placeOrderLimit;
	private BigDecimal averageRate;
	
	
	private String onlineInd;
	private String cbkPrintInd;
	private BigDecimal cbkSortInd;
	private BigDecimal decimalNumber;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;
	private String createdBy;
	private BigDecimal applicationCountryId;
	private String errMsg;
	private String allowFCPurchase;
	private String allowFCSale;
	
	private BigDecimal currencyOthInfoPk;
	private List<CountryMasterDesc> bankCountryList=new ArrayList<CountryMasterDesc>();
	private Map<BigDecimal, String> bankCountryMap=new HashMap<BigDecimal, String>();
	private SessionStateManage session=new SessionStateManage();
	private List<CurrencyDataTable> currencyList= new ArrayList<CurrencyDataTable>();
	
	
	@Autowired
	ICurrencyService icurencyService;
	
	@Autowired
	IGeneralService<T> igeneralService;
	
	public BigDecimal getCurrencyMasterPk() {
		return currencyMasterPk;
	}
	public void setCurrencyMasterPk(BigDecimal currencyMasterPk) {
		this.currencyMasterPk = currencyMasterPk;
	}
	public BigDecimal getCurrencyCountryId() {
		return currencyCountryId;
	}
	public void setCurrencyCountryId(BigDecimal currencyCountryId) {
		this.currencyCountryId = currencyCountryId;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public String getQuoteName() {
		return quoteName;
	}
	public void setQuoteName(String quoteName) {
		this.quoteName = quoteName;
	}
	 
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getDecimalName() {
		return decimalName;
	}
	public void setDecimalName(String decimalName) {
		this.decimalName = decimalName;
	}
	public String getFimsCurrencyCode() {
		return fimsCurrencyCode;
	}
	public void setFimsCurrencyCode(String fimsCurrencyCode) {
		this.fimsCurrencyCode = fimsCurrencyCode;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getArabicCurrencyName() {
		return arabicCurrencyName;
	}
	public void setArabicCurrencyName(String arabicCurrencyName) {
		this.arabicCurrencyName = arabicCurrencyName;
	}
	public String getArabicDecimalName() {
		return arabicDecimalName;
	}
	public void setArabicDecimalName(String arabicDecimalName) {
		this.arabicDecimalName = arabicDecimalName;
	}
	public String getArabicQuoteName() {
		return arabicQuoteName;
	}
	public void setArabicQuoteName(String arabicQuoteName) {
		this.arabicQuoteName = arabicQuoteName;
	}
	public String getSwiftCurrency() {
		return swiftCurrency;
	}
	public void setSwiftCurrency(String swiftCurrency) {
		this.swiftCurrency = swiftCurrency;
	}
	public BigDecimal getFundMinRate() {
		return fundMinRate;
	}
	public void setFundMinRate(BigDecimal fundMinRate) {
		this.fundMinRate = fundMinRate;
	}
	public BigDecimal getFundMaxRate() {
		return fundMaxRate;
	}
	public void setFundMaxRate(BigDecimal fundMaxRate) {
		this.fundMaxRate = fundMaxRate;
	}
	public String getIsoCurrencyCode() {
		return isoCurrencyCode;
	}
	public void setIsoCurrencyCode(String isoCurrencyCode) {
		this.isoCurrencyCode = isoCurrencyCode;
	}
	public BigDecimal getCashMinRate() {
		return cashMinRate;
	}
	public void setCashMinRate(BigDecimal cashMinRate) {
		this.cashMinRate = cashMinRate;
	}
	public BigDecimal getCashMaxRate() {
		return cashMaxRate;
	}
	public void setCashMaxRate(BigDecimal cashMaxRate) {
		this.cashMaxRate = cashMaxRate;
	}
	public String getOnlineInd() {
		return onlineInd;
	}
	public void setOnlineInd(String onlineInd) {
		this.onlineInd = onlineInd;
	}
	public String getCbkPrintInd() {
		return cbkPrintInd;
	}
	public void setCbkPrintInd(String cbkPrintInd) {
		this.cbkPrintInd = cbkPrintInd;
	}
	public BigDecimal getCbkSortInd() {
		return cbkSortInd;
	}
	public void setCbkSortInd(BigDecimal cbkSortInd) {
		this.cbkSortInd = cbkSortInd;
	}
	public BigDecimal getDecimalNumber() {
		return decimalNumber;
	}
	public void setDecimalNumber(BigDecimal decimalNumber) {
		this.decimalNumber = decimalNumber;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	public Date getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	 
	public Map<BigDecimal, String> getBankCountryMap() {
		return bankCountryMap;
	}
	public void setBankCountryMap(Map<BigDecimal, String> bankCountryMap) {
		this.bankCountryMap = bankCountryMap;
	}
	
	public String getAllowFCPurchase() {
		return allowFCPurchase;
	}
	public void setAllowFCPurchase(String allowFCPurchase) {
		this.allowFCPurchase = allowFCPurchase;
	}
	
	public String getAllowFCSale() {
		return allowFCSale;
	}
	public void setAllowFCSale(String allowFCSale) {
		this.allowFCSale = allowFCSale;
	}
	
	public List<CountryMasterDesc> getBankCountryList() {
		 bankCountryList=igeneralService.getCountryList(session.getLanguageId());
		 for(CountryMasterDesc bankMaster:bankCountryList){
			bankCountryMap.put(bankMaster.getFsCountryMaster().getCountryId(),bankMaster.getCountryName());
		}
		return bankCountryList;
	}
	public void setBankCountryList(List<CountryMasterDesc> bankCountryList) {
		this.bankCountryList = bankCountryList;
	}
	
	public void unapprovalList(CurrencyDataTable currencyDTable){
 
		if((currencyDTable.getModifiedBy()==null?currencyDTable.getCreatedBy():currencyDTable.getModifiedBy()).equalsIgnoreCase( session.getUserName())) {
			RequestContext.getCurrentInstance().execute("exist.show();");
		} else {
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../common/currencyfinalapprove.xhtml");
		} catch (IOException e) {
		 log.error( ":::::::::::::::::::::::::Problem Occured navigation:::::::::::::::::::::");
		}
			 
				setCurrencyMasterPk(currencyDTable.getCurrencyMasterPk());
				setCurrencyOthInfoPk(currencyDTable.getCurrencyOtherOnfoPk() );
				setCurrencyCode(currencyDTable.getCurrencyCode());
				setCurrencyName(  currencyDTable.getCurrencyName());
				setArabicCurrencyName( currencyDTable.getArabicCurrencyName());
				//setCurrencyDesc( currencyDTable.getCurrencyDesc());
				setCbkSortInd( currencyDTable.getCbkSortInd());
				setCbkPrintInd( currencyDTable.getCbkPrintInd());
				setCashMinRate( currencyDTable.getCashMinRate());
				setCashMaxRate( currencyDTable.getCashMaxRate());
				setCurrencyCountryId(currencyDTable.getCountryId());
				setCountryName( currencyDTable.getCountryName());
				setAverageRate(currencyDTable.getAverageRate() );
				setHighValue( currencyDTable.getHighValue());
				setPlaceOrderLimit(currencyDTable.getPlaceOrderLimit());
				
				setAllowFCPurchase(currencyDTable.getAllowFCPurchase());
				setAllowFCSale(currencyDTable.getAllowFCSale());
				
				setArabicQuoteName( currencyDTable.getArabicQuoteName());
				setQuoteName( currencyDTable.getQuoteName());
				setArabicDecimalName( currencyDTable.getArabicDecimalName());
				setDecimalNumber( currencyDTable.getDecimalNumber());
				setDecimalName( currencyDTable.getDecimalName());
				setOnlineInd( currencyDTable.getOnlineInd());
				setFimsCurrencyCode( currencyDTable.getFimsCurrencyCode());
				setFundMaxRate( currencyDTable.getFundMaxRate());
				setFundMinRate( currencyDTable.getFundMinRate());
				setSwiftCurrency(currencyDTable.getSwiftCurrency());
				setIsoCurrencyCode( currencyDTable.getIsoCurrencyCode());
				setCreatedBy(currencyDTable.getCreatedBy());
				setCreatedDate( currencyDTable.getCreatedDate());
				setApplicationCountryId(currencyDTable.getApplicationContryId() );
		}
					
					
					
				}
	
	
			public void approve(){
	           
				/*CurrencyMaster currencyMasterObj=new CurrencyMaster();
				
				currencyMasterObj.setCurrencyId(getCurrencyMasterPk());
				currencyMasterObj.setCurrencyCode( getCurrencyCode() );
				currencyMasterObj.setCurrencyName( getCurrencyName() );
				currencyMasterObj.setArabicCurrencyName( getArabicCurrencyName() );
				currencyMasterObj.setCurrencyDesc( getCurrencyDesc());

				
				currencyMasterObj.setCashMaxRate( getCashMaxRate());
				currencyMasterObj.setCashMinRate( getCashMinRate());
				currencyMasterObj.setFimsCurrencyCode( getFimsCurrencyCode() );
				
				currencyMasterObj.setFundMaxRate( getFundMaxRate());
				currencyMasterObj.setFundMinRate( getFundMinRate());
				currencyMasterObj.setIsoCurrencyCode( getIsoCurrencyCode());
				currencyMasterObj.setDecinalNumber( getDecimalNumber());
				currencyMasterObj.setDecimalName( getDecimalName());
				currencyMasterObj.setArabicDecimalName( getArabicDecimalName());
				
				currencyMasterObj.setCbkPrintInd(  getCbkPrintInd());
				currencyMasterObj.setCbkSortInd( getCbkSortInd());
				currencyMasterObj.setOnlineInd( getOnlineInd());
				currencyMasterObj.setQuoteName( getQuoteName());
				currencyMasterObj.setArabicQuoteName( getArabicQuoteName());
				currencyMasterObj.setSwiftCurrency( getSwiftCurrency() );
				currencyMasterObj.setCreatedBy( getCreatedBy());
				currencyMasterObj.setCreatedDate( getCreatedDate());
				currencyMasterObj.setApprovedBy(session.getUserName());
				currencyMasterObj.setApprovedDate( new Date());
						
				if( getCurrencyCountryId()!=null){
				CountryMaster countryMaster=new CountryMaster();
				countryMaster.setCountryId(getCurrencyCountryId());
				currencyMasterObj.setFsCountryMaster(countryMaster);
				}
				
				currencyMasterObj.setIsactive( Constants.Yes);*/
				try{
				String approveMsg=icurencyService.approveRecord(getCurrencyMasterPk(),session.getUserName(),getCurrencyOthInfoPk());
										 				
				if(approveMsg.equalsIgnoreCase("Success")){
					HashMap< String , String> inputValues=new HashMap<String, String>();
					inputValues.put("APP_COUNTRYID", getApplicationCountryId().toPlainString() );
					inputValues.put("CURRENCY_CODE",  getCurrencyCode());
					HashMap< String , String>  outputValues =	 icurencyService.callPopulateCurmasProcedure(inputValues);
					if(outputValues.get("P_ERROR_MESSAGE")!=null){
						setErrMsg(outputValues.get("P_ERROR_MESSAGE"));
						RequestContext.getCurrentInstance().execute("error.show();");
						return ; 
					}else{
					
					RequestContext.getCurrentInstance().execute("approv.show();");
					}
				}else{
					RequestContext.getCurrentInstance().execute("alreadyapprov.show();");
				}
				}catch(Exception e){
					setErrMsg("Procedure Exception" +e.getMessage());
					RequestContext.getCurrentInstance().execute("error.show();");
				}
			
			}
			List<CurrencyMaster>   appList=new ArrayList<CurrencyMaster>();
		
			public void approvalDTList(){
				getBankCountryList();
				appList.clear();
				currencyList.clear();
				List<CurrencyOtherInformation>   appList=icurencyService.getAllUnApprovedRecords();
				if(appList.size()>0){
				for(CurrencyOtherInformation currencyOthInfoObj:appList){
					
					CurrencyDataTable currencyDTObj=new CurrencyDataTable();
					
					currencyDTObj.setCurrencyMasterPk( currencyOthInfoObj.getExCurrencyMaster().getCurrencyId());
					currencyDTObj.setCurrencyOtherOnfoPk(currencyOthInfoObj.getCurrencyOtherInfoId() );
					currencyDTObj.setCurrencyCode( currencyOthInfoObj.getExCurrencyMaster().getCurrencyCode());
					currencyDTObj.setCurrencyName(currencyOthInfoObj.getExCurrencyMaster().getCurrencyName());
					//currencyDTObj.setCurrencyDesc( currencyOthInfoObj.getExCurrencyMaster().getCurrencyDesc());
					currencyDTObj.setArabicCurrencyName( currencyOthInfoObj.getExCurrencyMaster().getArabicCurrencyName());
					
					currencyDTObj.setArabicQuoteName(currencyOthInfoObj.getExCurrencyMaster().getArabicQuoteName());
					currencyDTObj.setQuoteName( currencyOthInfoObj.getExCurrencyMaster().getQuoteName());
				 
					currencyDTObj.setCbkPrintInd( currencyOthInfoObj.getCbkPrintIndicator());
					currencyDTObj.setCbkSortInd( currencyOthInfoObj.getCbkShortIndicator());
					currencyDTObj.setOnlineInd( currencyOthInfoObj.getOnlineInd());
					
					currencyDTObj.setCashMaxRate( currencyOthInfoObj.getCashMaxRate());
					currencyDTObj.setCashMinRate( currencyOthInfoObj.getCashMinRate());
					currencyDTObj.setFundMaxRate( currencyOthInfoObj.getFundMaxRate());
					currencyDTObj.setFundMinRate( currencyOthInfoObj.getFundMinRate());
					currencyDTObj.setAverageRate(currencyOthInfoObj.getAverageRate() );
					currencyDTObj.setHighValue( currencyOthInfoObj.getHighValue());
					currencyDTObj.setPlaceOrderLimit(currencyOthInfoObj.getPlaceOrderLimit());
					
					currencyDTObj.setCreatedBy(currencyOthInfoObj.getExCurrencyMaster().getCreatedBy() );
					currencyDTObj.setCreatedDate(currencyOthInfoObj.getExCurrencyMaster().getCreatedDate());
					currencyDTObj.setModifiedBy( currencyOthInfoObj.getExCurrencyMaster().getModifiedBy());
					currencyDTObj.setModifiedDate(currencyOthInfoObj.getExCurrencyMaster().getModifiedDate() );
					currencyDTObj.setApplicationContryId(currencyOthInfoObj.getFsCountryMaster().getCountryId() );
					
					currencyDTObj.setAllowFCPurchase(currencyOthInfoObj.getExCurrencyMaster().getAllowFCPurchase());
					currencyDTObj.setAllowFCSale(currencyOthInfoObj.getExCurrencyMaster().getAllowFCSale());
		 
					
					currencyDTObj.setFimsCurrencyCode( currencyOthInfoObj.getExCurrencyMaster().getFimsCurrencyCode());
					if(currencyOthInfoObj.getFsCountryMaster().getCountryId()!=null){
						currencyDTObj.setCountryName(bankCountryMap.get(currencyOthInfoObj.getFsCountryMaster().getCountryId()));
						currencyDTObj.setCountryId( currencyOthInfoObj.getFsCountryMaster().getCountryId());
					}
					currencyDTObj.setArabicDecimalName( currencyOthInfoObj.getExCurrencyMaster().getArabicDecimalName());
					currencyDTObj.setDecimalName( currencyOthInfoObj.getExCurrencyMaster().getDecimalName());
					currencyDTObj.setDecimalNumber(currencyOthInfoObj.getExCurrencyMaster().getDecinalNumber());
					currencyDTObj.setSwiftCurrency( currencyOthInfoObj.getExCurrencyMaster().getSwiftCurrency());
					currencyDTObj.setIsoCurrencyCode( currencyOthInfoObj.getExCurrencyMaster().getIsoCurrencyCode());
					currencyList.add(currencyDTObj);
				}
				
				}
			}
			public List<CurrencyDataTable> getCurrencyList() {
				return currencyList;
			}
			
			public void setCurrencyList(List<CurrencyDataTable> currencyList) {
				this.currencyList = currencyList;
			}
			@Autowired
			LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
			public void pageNavigationToApproval(){
				approvalDTList();
				try {
					loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "currencyApproval.xhtml");
					FacesContext.getCurrentInstance().getExternalContext().redirect("../common/currencyApproval.xhtml");
				} catch (Exception e) {
					 log.error( "::::::::::::::::::::::::::::::::Problem Occured Redirecting::::::::::::::::::::::::");
				}
 
				
			}
			public void exit() throws IOException{
				if (session.getRoleId().equalsIgnoreCase("1")) {
					FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
				} else if (session.getRoleId().equalsIgnoreCase("2")) {
					FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
				}else{
					FacesContext.getCurrentInstance() .getExternalContext().redirect("../common/saleshome.xhtml");
				}
			}
			public void clickOnOk() {
				approvalDTList();
				try {
					FacesContext.getCurrentInstance().getExternalContext()
							.redirect("../common/currencyApproval.xhtml");
				} catch (Exception e) {
					log.error( ":::::::::::::Problem oocured in clickOnOk()::::::::::"+e.getCause());
				}
			}
			public BigDecimal getHighValue() {
				return highValue;
			}
			public void setHighValue(BigDecimal highValue) {
				this.highValue = highValue;
			}
			public BigDecimal getAverageRate() {
				return averageRate;
			}
			public void setAverageRate(BigDecimal averageRate) {
				this.averageRate = averageRate;
			}
			public BigDecimal getCurrencyOthInfoPk() {
				return currencyOthInfoPk;
			}
			public void setCurrencyOthInfoPk(BigDecimal currencyOthInfoPk) {
				this.currencyOthInfoPk = currencyOthInfoPk;
			}
			public BigDecimal getApplicationCountryId() {
				return applicationCountryId;
			}
			public void setApplicationCountryId(BigDecimal applicationCountryId) {
				this.applicationCountryId = applicationCountryId;
			}
			public String getErrMsg() {
				return errMsg;
			}
			public void setErrMsg(String errMsg) {
				this.errMsg = errMsg;
			}
			public BigDecimal getPlaceOrderLimit() {
				return placeOrderLimit;
			}
			public void setPlaceOrderLimit(BigDecimal placeOrderLimit) {
				this.placeOrderLimit = placeOrderLimit;
			}


		 
	
	
	

}
