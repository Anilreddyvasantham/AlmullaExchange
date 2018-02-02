package com.amg.exchange.common.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.sql.rowset.serial.SerialException;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.model.FimsCurmas;
import com.amg.exchange.common.service.ICurrencyService;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.CurrencyOtherInformation;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;
@Component("currencyMasterBean")
@Scope("session")
public class CurrencyMasterBean<T> implements Serializable {

	private static Logger log = Logger.getLogger(CurrencyMasterBean.class);
	private static final long serialVersionUID = 1L;
	private BigDecimal currencyMasterPk;
	private BigDecimal currencyOtherInfoPk;
	private BigDecimal currencyCountryId;

	private String currencyCode;
	private String currencyName;
	private String quoteName;
	private Byte currencyDesc;
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
	private Boolean renderEditButton=false;
	private String dynamicLabelForActivateDeactivate;
	private String selectType;
	private Boolean booHideClear=false;
	private Boolean booHideSubmit=false;
	private Boolean booHideEdit=false;
	private Boolean booRenderDatatable=false;
	private Boolean booRenderSubmit=false;
	private Boolean checkSave;
	private Boolean booCheckUpdate;
	private String  errorMessage;
	private Boolean booSeach=false;
	private BigDecimal averageRate;
	private BigDecimal highValue;
	private BigDecimal applicationCountryId;
	private String allowFCPurchase;
	private String allowFCSale;
	private BigDecimal placeOrderLimit;

	private SessionStateManage session=new SessionStateManage();
	private List<CurrencyDataTable> listDT= new CopyOnWriteArrayList<CurrencyDataTable>();
	private List<CurrencyDataTable> viewList=new CopyOnWriteArrayList<CurrencyDataTable>();
	private List<CountryMasterDesc> bankCountryList=new ArrayList<CountryMasterDesc>();
	private Map<BigDecimal, String> bankCountryMap=new HashMap<BigDecimal, String>();

	private List<CurrencyDataTable>  listCurrency;


	@Autowired
	ICurrencyService icurencyService;

	@Autowired
	IGeneralService<T> igeneralService;

	public List<CurrencyDataTable> getListDT() {
		return listDT;
	}
	public void setListDT(List<CurrencyDataTable> listDT) {
		this.listDT = listDT;
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
	public Boolean getRenderEditButton() {
		return renderEditButton;
	}
	public void setRenderEditButton(Boolean renderEditButton) {
		this.renderEditButton = renderEditButton;
	}
	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}
	public void setDynamicLabelForActivateDeactivate(
			String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
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
	public Byte getCurrencyDesc() {
		return currencyDesc;
	}
	public void setCurrencyDesc(Byte currencyDesc) {
		this.currencyDesc = currencyDesc;
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
	public BigDecimal getCurrencyMasterPk() {
		return currencyMasterPk;
	}
	public void setCurrencyMasterPk(BigDecimal currencyMasterPk) {
		this.currencyMasterPk = currencyMasterPk;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public  String getSelectType() {
		return selectType;
	}
	public void setSelectType(String selectType) {
		this.selectType = selectType;
	}
	public Boolean getBooHideClear() {
		return booHideClear;
	}
	public void setBooHideClear(Boolean booHideClear) {
		this.booHideClear = booHideClear;
	}
	public Boolean getBooHideSubmit() {
		return booHideSubmit;
	}
	public void setBooHideSubmit(Boolean booHideSubmit) {
		this.booHideSubmit = booHideSubmit;
	}
	public Boolean getBooHideEdit() {
		return booHideEdit;
	}
	public void setBooHideEdit(Boolean booHideEdit) {
		this.booHideEdit = booHideEdit;
	}
	public Boolean getBooRenderDatatable() {
		return booRenderDatatable;
	}
	public void setBooRenderDatatable(Boolean booRenderDatatable) {
		this.booRenderDatatable = booRenderDatatable;
	}
	public Boolean getBooRenderSubmit() {
		return booRenderSubmit;
	}
	public void setBooRenderSubmit(Boolean booRenderSubmit) {
		this.booRenderSubmit = booRenderSubmit;
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
	
	
	public void comparingAmount(FacesContext context,UIComponent component, Object value) {
		BigDecimal fundmaxRate = (BigDecimal)value;
		if (getFundMinRate() == null || fundmaxRate == null) {
			FacesMessage msg = new FacesMessage("Please Enter Fund Min Rate  and Then Fund Max Rate","Please Enter Fund Min Rate  and Then Fund Max Rate");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		} else if (getFundMinRate().compareTo(fundmaxRate) > 0||getFundMinRate().compareTo(fundmaxRate)==0) {

			setFundMaxRate(null);
			FacesMessage msg = new FacesMessage("Please Enter Greater Than Fund Min Rate","Please Enter Greater Than Fund Min Rate");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
	}


	public void comparingCashRate(FacesContext context,UIComponent component, Object value) {
		BigDecimal cashmaxAmount = (BigDecimal)value;
		if (getCashMinRate() == null || cashmaxAmount == null) {
			FacesMessage msg = new FacesMessage("Please Enter Cash Min Rate  and Then Fund Max Rate","Please Enter Cash Min Rate  and Then Fund Max Rate");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		} else if (getCashMinRate().compareTo(cashmaxAmount)> 0||getCashMinRate().compareTo(cashmaxAmount)==0) {

			setFundMaxRate(null);
			FacesMessage msg = new FacesMessage("Please Enter Greater Than Cash Min Rate","Please Enter Greater Than Cash Min Rate");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
	}	



	public void addRecordsToDatatable(){
		log.info( "========================= save() CALLED========================");
		setBooRenderSubmit(true);
		try{
			if (viewList.size() == 0) {
				CurrencyDataTable currencyDT=new CurrencyDataTable();

				currencyDT.setCurrencyMasterPk( getCurrencyMasterPk());
				currencyDT.setCurrencyOtherOnfoPk(getCurrencyOtherInfoPk());
				
				String[] s = getCurrencyCode().split("-");
				if(s[0] != null){
					setCurrencyCode(s[0].trim().replaceAll(" +", " "));
				}
				currencyDT.setCurrencyCode( getCurrencyCode());
				currencyDT.setCurrencyName(getCurrencyName());
				currencyDT.setCurrencyDesc( getCurrencyDesc());
				currencyDT.setArabicCurrencyName( getArabicCurrencyName());

				currencyDT.setArabicQuoteName( getArabicQuoteName());
				currencyDT.setQuoteName( getQuoteName());

				currencyDT.setCbkPrintInd( getCbkPrintInd());
				currencyDT.setCbkSortInd( getCbkSortInd());
				currencyDT.setOnlineInd( getOnlineInd());

				currencyDT.setCashMaxRate( getCashMaxRate());
				currencyDT.setCashMinRate( getCashMinRate());
				currencyDT.setFundMaxRate( getFundMaxRate());
				currencyDT.setFundMinRate( getFundMinRate());
				//new columns
				currencyDT.setAverageRate( getAverageRate());
				currencyDT. setHighValue( getHighValue());
				currencyDT.setPlaceOrderLimit(getPlaceOrderLimit());
				currencyDT.setApplicationContryId( getApplicationCountryId());

				currencyDT.setFimsCurrencyCode( getFimsCurrencyCode());

				currencyDT.setCountryId( getCurrencyCountryId());
				currencyDT.setCountryName(bankCountryMap.get(getCurrencyCountryId()));

				currencyDT.setArabicDecimalName( getArabicDecimalName());
				currencyDT.setDecimalName( getDecimalName());
				currencyDT.setDecimalNumber( getDecimalNumber());
				currencyDT.setSwiftCurrency( getSwiftCurrency());
				currencyDT.setIsoCurrencyCode( getIsoCurrencyCode());
				if(getCurrencyMasterPk()!=null){
					currencyDT.setCreatedBy(getCreatedBy() );
					currencyDT.setCreatedDate(getCreatedDate() );
					currencyDT.setApprovedBy( getApprovedBy());
					currencyDT.setApprovedDate( getApprovedDate());
					currencyDT.setDynamicLabelForActivateDeactivate( getDynamicLabelForActivateDeactivate());
					currencyDT.setRenderEditButton( getRenderEditButton());
					currencyDT.setModifiedBy( getModifiedBy());
					currencyDT.setModifiedDate( getModifiedDate());
					currencyDT.setCheckSave( getCheckSave());
					currencyDT.setBooCheckUpdate( getBooCheckUpdate());
					currencyDT.setIsActive(getIsActive());
				}
				else{
					currencyDT.setDynamicLabelForActivateDeactivate( Constants.REMOVE);
					currencyDT.setRenderEditButton(true);
					currencyDT.setIsActive( Constants.U);
					currencyDT.setCreatedBy( session.getUserName());
					currencyDT.setCreatedDate(new Date());
					currencyDT.setCheckSave(true);
					currencyDT.setBooCheckUpdate(true);

				}
				
				currencyDT.setAllowFCPurchase(getAllowFCPurchase());
				currencyDT.setAllowFCSale(getAllowFCSale());
				
				listDT.add(currencyDT);
			}

			if (listDT.size() > 0) {
				if (viewList.size() > 0) {
					for ( CurrencyDataTable currencyDTObj : listDT) {
						for (CurrencyDataTable currencyViewDTObj : viewList) {
							if (currencyDTObj.getCurrencyCode().equals(
									currencyViewDTObj.getCurrencyCode())) {
								viewList.remove(currencyViewDTObj);
							}
						}
					}
				}
				listDT.addAll(viewList);
			} else {
				listDT.addAll(viewList);

			}
		}	catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::addRecordsToDatatable");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}


		clearAll();
		setBooHideClear( false);
		setBooHideEdit( false);
		setBooHideSubmit( false);
		setBooRenderDatatable( true);


	}

	public void duplicateCheck() {

		try{
			if (listDT.size() != 0) {
				for ( CurrencyDataTable currencyDt : listDT) {
					if (currencyDt.getCurrencyCode().equals(getCurrencyCode())) {
						clearAll();
						RequestContext.getCurrentInstance().execute("succ.show();");
					} 
				}
			}
			if (getCurrencyCode()!= null) {
				addRecordsToDatatable();
				viewList.clear();

			}
		}catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::duplicateCheck");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	public void populateValues(){
		log.info( "=====================populateValues================");
		clearAllfields();
		setDynamicLabelForActivateDeactivate(null);
		if(getCurrencyCode()!=null){
			try{
				String[] s = getCurrencyCode().split("-");
				if(s[0] != null){
					setCurrencyCode(s[0].trim().replaceAll(" +", " "));
				}
				
				List<CurrencyOtherInformation>   populatecurrencylist= icurencyService.getRecordFromDB(getCurrencyCode());

				if (populatecurrencylist.size() != 0) {
					setCurrencyMasterPk( populatecurrencylist.get(0).getExCurrencyMaster().getCurrencyId());
					setCurrencyOtherInfoPk( populatecurrencylist.get(0).getCurrencyOtherInfoId() );
					setCurrencyCode(populatecurrencylist.get(0).getExCurrencyMaster().getCurrencyCode());
					setCurrencyName(populatecurrencylist.get(0).getExCurrencyMaster().getCurrencyName());
					setArabicCurrencyName(populatecurrencylist.get(0).getExCurrencyMaster().getArabicCurrencyName());
					//setCurrencyDesc( populatecurrencylist.get(0).getExCurrencyMaster().getCurrencyDesc());
					setCbkSortInd(  populatecurrencylist.get( 0).getCbkShortIndicator());
					setCbkPrintInd(  populatecurrencylist.get( 0).getCbkPrintIndicator());
					setCashMinRate( populatecurrencylist.get( 0).getCashMinRate());
					setCashMaxRate(  populatecurrencylist.get( 0).getCashMaxRate()); 
					//NEW COLUMNS
					setAverageRate( populatecurrencylist.get( 0).getAverageRate());
					setHighValue( populatecurrencylist.get( 0).getHighValue());
					setPlaceOrderLimit(populatecurrencylist.get(0).getPlaceOrderLimit());					

					if(populatecurrencylist.get( 0).getExCurrencyMaster().getFsCountryMaster()!=null){
						setCurrencyCountryId( populatecurrencylist.get( 0).getExCurrencyMaster().getFsCountryMaster().getCountryId());
						setCountryName(bankCountryMap.get( populatecurrencylist.get( 0).getExCurrencyMaster().getFsCountryMaster().getCountryId()) );
					}
					//setCheckSave( currencyDT.getCheckSave());
					//setCountryName(bankCountryMap.get(getCurrencyCountryId()));
					setArabicQuoteName( populatecurrencylist.get(0).getExCurrencyMaster().getArabicQuoteName());
					setQuoteName(  populatecurrencylist.get(0).getExCurrencyMaster().getQuoteName());
					setArabicDecimalName( populatecurrencylist.get(0).getExCurrencyMaster().getArabicDecimalName());
					setDecimalNumber( populatecurrencylist.get(0).getExCurrencyMaster().getDecinalNumber());
					setDecimalName( populatecurrencylist.get(0).getExCurrencyMaster().getDecimalName());
					setOnlineInd( populatecurrencylist.get( 0).getOnlineInd());
					setFimsCurrencyCode(populatecurrencylist.get(0).getExCurrencyMaster().getFimsCurrencyCode());
					setFundMaxRate(  populatecurrencylist.get( 0).getFundMaxRate());
					setFundMinRate(  populatecurrencylist.get( 0).getFundMinRate());
					setSwiftCurrency( populatecurrencylist.get(0).getExCurrencyMaster().getSwiftCurrency());
					setIsoCurrencyCode(  populatecurrencylist.get(0).getExCurrencyMaster().getIsoCurrencyCode());
					setCreatedBy (populatecurrencylist.get(0).getExCurrencyMaster().getCreatedBy());
					setCreatedDate(populatecurrencylist.get(0).getExCurrencyMaster().getCreatedDate());
					setAllowFCPurchase(populatecurrencylist.get(0).getExCurrencyMaster().getAllowFCPurchase());
					setAllowFCSale(populatecurrencylist.get(0).getExCurrencyMaster().getAllowFCSale());
					
					setIsActive( Constants.U);
					setBooCheckUpdate(true);
					setApplicationCountryId( populatecurrencylist.get( 0).getFsCountryMaster().getCountryId());
					if (populatecurrencylist.get(0).getExCurrencyMaster().getIsactive().equalsIgnoreCase(Constants.Yes)) {
						setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
						setRenderEditButton( true);
						setCheckSave(false);
					} else if (populatecurrencylist.get(0).getExCurrencyMaster().getIsactive().equalsIgnoreCase(Constants.D)) {
						setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
						setCheckSave(false);
					}	else{
						setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
						setRenderEditButton( true);
						setCheckSave(false);
					}
				}else {
					clearAll1();
					//if(populatecurrencylist.get(0).getExCurrencyMaster().getIsactive().equalsIgnoreCase(Constants.U)){
					setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
					setRenderEditButton( true);
					setCheckSave(false);
				}
			}catch (NullPointerException ne) {
				// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				setErrorMessage("Method Name::populateValues");
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return;
			} catch (Exception exception) {
				// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
				setErrorMessage(exception.getMessage());
				RequestContext.getCurrentInstance().execute("error.show();");
				return;
			}
		}else{
			clearAll2();
		}
	} 
	public List<String> autoCompleteData(String query) {
		if (query.length() > 0) {
			try {
				return  icurencyService.getCurrencyCodeFromDB(query);
			} catch (NullPointerException ne) {
				// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				setErrorMessage("Method Name::autoCompleteData");
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return null;
			} catch (Exception exception) {
				// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
				setErrorMessage(exception.getMessage());
				RequestContext.getCurrentInstance().execute("error.show();");
				return null;
			}		

		} else {
			return null;
		}
	}
	
	public List<String> autoCompleteCurrencyCode(String query) {
		List<String> results = null;
		try {
			if (query.length() > 0) {
				List<CurrencyMaster> currencyList = icurencyService
						.getCurrencyList();
				results = new ArrayList<String>();
				if (currencyList != null && currencyList.size() > 0) {
					for (CurrencyMaster currency : currencyList) {
						results.add(currency.getCurrencyCode()
								+ " - "
								+ currency.getCurrencyName()
								+ " - "
								+ icurencyService.getCountryName(currency.getFsCountryMaster().getCountryId(),session.getLanguageId()));
					}
				}
			}
		} catch (NullPointerException e) {
			setErrorMessage("Method Name: autoCompleteBankCode");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
		} catch (Exception e) {
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute(
					"error.show();");
		}
		return results;
	}





	public void editDatatableRecord(CurrencyDataTable currencyDT){
		try{
			setCurrencyMasterPk( currencyDT.getCurrencyMasterPk());
			setCurrencyOtherInfoPk(currencyDT.getCurrencyOtherOnfoPk());
			setCurrencyCode(currencyDT.getCurrencyCode());
			setCurrencyName(  currencyDT.getCurrencyName());
			setArabicCurrencyName( currencyDT.getArabicCurrencyName());
			setCurrencyDesc( currencyDT.getCurrencyDesc());
			setCbkSortInd( currencyDT.getCbkSortInd());
			setCbkPrintInd( currencyDT.getCbkPrintInd());
			setCashMinRate( currencyDT.getCashMinRate());
			setCashMaxRate( currencyDT.getCashMaxRate());
			setCurrencyCountryId(currencyDT.getCountryId());
			setCountryName(currencyDT.getCountryName() );
			setCheckSave( currencyDT.getCheckSave());
			setAverageRate(currencyDT.getAverageRate() );
			setHighValue( currencyDT.getHighValue());
			//	setCountryName(bankCountryMap.get(getCurrencyCountryId()));
			setArabicQuoteName( currencyDT.getArabicQuoteName());
			setQuoteName( currencyDT.getQuoteName());
			setArabicDecimalName( currencyDT.getArabicDecimalName());
			setDecimalNumber( currencyDT.getDecimalNumber());
			setDecimalName( currencyDT.getDecimalName());
			setOnlineInd( currencyDT.getOnlineInd());
			setFimsCurrencyCode( currencyDT.getFimsCurrencyCode());
			setFundMaxRate( currencyDT.getFundMaxRate());
			setFundMinRate( currencyDT.getFundMinRate());
			setSwiftCurrency(currencyDT.getSwiftCurrency());
			setIsoCurrencyCode( currencyDT.getIsoCurrencyCode());
			setCreatedBy(currencyDT.getCreatedBy());
			setCreatedDate( currencyDT.getCreatedDate());
			
			setAllowFCPurchase(currencyDT.getAllowFCPurchase());
			setAllowFCSale(currencyDT.getAllowFCSale());
			setPlaceOrderLimit(currencyDT.getPlaceOrderLimit());
			setBooCheckUpdate(true);
			setApplicationCountryId( currencyDT.getApplicationContryId());
			if(currencyDT.getCurrencyMasterPk()!=null){
				setModifiedBy( session.getUserName());	
				setModifiedDate( new Date());
			}
			setIsActive(Constants.U );
			setRenderEditButton( currencyDT.getRenderEditButton());
			setDynamicLabelForActivateDeactivate( Constants.PENDING_FOR_APPROVE);


			listDT.remove(currencyDT);

			setBooHideClear(true);
			setBooHideSubmit( true);
			setBooHideEdit( true);
			setBooRenderDatatable(true);
		}catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::editDatatableRecord");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}

	}

	public void saveDataTableRecords(){
		if( listDT.isEmpty()){
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("dataNotFund.show();");
		}else{
			try{
				for(CurrencyDataTable currencyDTObj:listDT){

					CurrencyMaster currencyMasterObj=new CurrencyMaster();


					currencyMasterObj.setCurrencyId(currencyDTObj.getCurrencyMasterPk());

					if (currencyDTObj.getCurrencyMasterPk() != null) {
						currencyMasterObj.setCreatedBy(currencyDTObj.getCreatedBy());
						currencyMasterObj.setCreatedDate(currencyDTObj.getCreatedDate());
						currencyMasterObj.setModifiedBy(session.getUserName());
						currencyMasterObj.setModifiedDate(new Date());
					} else {
						currencyMasterObj.setCreatedBy(session.getUserName());
						currencyMasterObj.setCreatedDate(new Date());

					}
					currencyMasterObj.setIsactive(currencyDTObj.getIsActive() );

					currencyMasterObj.setCurrencyCode(currencyDTObj.getCurrencyCode() );
					currencyMasterObj.setCurrencyName(currencyDTObj.getCurrencyName() );
					currencyMasterObj.setArabicCurrencyName(currencyDTObj.getArabicCurrencyName() );
					//currencyMasterObj.setCurrencyDesc(currencyDTObj.getCurrencyDesc());
					currencyMasterObj.setFimsCurrencyCode(currencyDTObj.getFimsCurrencyCode() );
					currencyMasterObj.setIsoCurrencyCode(currencyDTObj.getIsoCurrencyCode());
					currencyMasterObj.setDecinalNumber(currencyDTObj.getDecimalNumber());
					currencyMasterObj.setDecimalName(currencyDTObj.getDecimalName());
					currencyMasterObj.setArabicDecimalName(currencyDTObj.getArabicDecimalName());
					currencyMasterObj.setQuoteName(currencyDTObj.getQuoteName());
					currencyMasterObj.setArabicQuoteName(currencyDTObj.getArabicQuoteName());
					currencyMasterObj.setSwiftCurrency(currencyDTObj.getSwiftCurrency() );
					
					currencyMasterObj.setAllowFCPurchase(currencyDTObj.getAllowFCPurchase());
					currencyMasterObj.setAllowFCSale(currencyDTObj.getAllowFCSale());

					if( currencyDTObj.getCountryName()!=null){
						CountryMaster countryMaster=new CountryMaster();
						countryMaster.setCountryId(currencyDTObj.getCountryId());
						currencyMasterObj.setFsCountryMaster(countryMaster);
					}

					CurrencyOtherInformation currencyOthInfo=new CurrencyOtherInformation();
					currencyOthInfo.setCurrencyOtherInfoId(currencyDTObj.getCurrencyOtherOnfoPk());
					currencyOthInfo.setCashMaxRate(currencyDTObj.getCashMaxRate());
					currencyOthInfo.setCashMinRate(currencyDTObj.getCashMinRate());
					currencyOthInfo.setFundMinRate(currencyDTObj.getFundMinRate());
					currencyOthInfo.setFundMaxRate(currencyDTObj.getFundMaxRate() );
					currencyOthInfo.setAverageRate(currencyDTObj.getAverageRate() );
					currencyOthInfo.setHighValue( currencyDTObj.getHighValue());
					if(currencyDTObj.getPlaceOrderLimit()!=null){
						currencyOthInfo.setPlaceOrderLimit(currencyDTObj.getPlaceOrderLimit());
					} else{
						currencyOthInfo.setPlaceOrderLimit(new BigDecimal(0));
					}
					
					currencyOthInfo.setOnlineInd(currencyDTObj.getOnlineInd());
					currencyOthInfo.setCbkShortIndicator(currencyDTObj.getCbkSortInd());
					currencyOthInfo.setCbkPrintIndicator(currencyDTObj.getCbkPrintInd());
					currencyOthInfo.setExCurrencyMaster(currencyMasterObj);

					if (currencyDTObj.getCurrencyMasterPk() != null) {
						CountryMaster countryMaster=new CountryMaster();
						countryMaster.setCountryId(currencyDTObj.getApplicationContryId());
						currencyOthInfo.setFsCountryMaster(countryMaster);
					}else{
						CountryMaster countryMaster=new CountryMaster();
						countryMaster.setCountryId(session.getCountryId());
						currencyOthInfo.setFsCountryMaster(countryMaster);
					}
					currencyOthInfo.setCreatedby(session.getUserName() );
					currencyOthInfo.setCreatedDate(new Date());




					if(currencyDTObj.getBooCheckUpdate()){

						icurencyService.saveOrUpdate(currencyMasterObj);
						icurencyService.saveOrUpdate(currencyOthInfo);

					}



				} 
				setBooSeach(false);
				RequestContext.getCurrentInstance().execute("complete.show();");
			}catch (NullPointerException ne) {
				// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				setErrorMessage("Method Name::saveDataTableRecords");
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return;
			} catch (Exception exception) {
				// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
				setErrorMessage(exception.getMessage());
				RequestContext.getCurrentInstance().execute("error.show();");
				return;
			}
		}
		listDT.clear();
	}

	public void clickOnOKSave() {
		listDT.clear();
		viewList.clear();
		setBooRenderDatatable( false);
		setBooHideClear(false);
		setBooRenderSubmit(false);
		clearAll();
		try {
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("../common/currencydetailsmannual.xhtml");
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::saveDataTableRecords");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}
	List<CurrencyOtherInformation>  allRecordList=new CopyOnWriteArrayList<CurrencyOtherInformation>();

	public void viewAllRecordsFromDB( ){
		allRecordList.clear();
		viewList.clear();
		if( getSearchValue()!=null){
			listDT.clear();
		}
		setBooRenderSubmit(true);

		try{
			allRecordList =icurencyService.getAllRecords( getSearchValue());
			log.info( "VIEW CALLED=================="+getSearchValue()+""+allRecordList.size());
			if(allRecordList.size()>0){
				setBooSeach( true);
				for(CurrencyOtherInformation currencyOthInfoObj: allRecordList){
					CurrencyDataTable CurrencyDTObj=new CurrencyDataTable();

					CurrencyDTObj.setCurrencyMasterPk( currencyOthInfoObj.getExCurrencyMaster().getCurrencyId());
					CurrencyDTObj.setCurrencyOtherOnfoPk(currencyOthInfoObj.getCurrencyOtherInfoId() );
					CurrencyDTObj.setCurrencyCode(currencyOthInfoObj.getExCurrencyMaster().getCurrencyCode() );
					CurrencyDTObj.setCurrencyName(currencyOthInfoObj.getExCurrencyMaster().getCurrencyName());
					//CurrencyDTObj.setCurrencyDesc(currencyOthInfoObj.getExCurrencyMaster().getCurrencyDesc() );
					CurrencyDTObj.setArabicCurrencyName( currencyOthInfoObj.getExCurrencyMaster().getArabicCurrencyName());
					CurrencyDTObj.setArabicQuoteName(currencyOthInfoObj.getExCurrencyMaster().getArabicQuoteName());
					CurrencyDTObj.setQuoteName( currencyOthInfoObj.getExCurrencyMaster().getQuoteName());

					CurrencyDTObj.setCbkPrintInd( currencyOthInfoObj.getCbkPrintIndicator());
					CurrencyDTObj.setCbkSortInd( currencyOthInfoObj.getCbkShortIndicator());
					CurrencyDTObj.setOnlineInd( currencyOthInfoObj.getOnlineInd());

					CurrencyDTObj.setCashMaxRate( currencyOthInfoObj.getCashMaxRate());
					CurrencyDTObj.setCashMinRate( currencyOthInfoObj.getCashMinRate());
					CurrencyDTObj.setFundMaxRate( currencyOthInfoObj.getFundMaxRate());
					CurrencyDTObj.setFundMinRate( currencyOthInfoObj.getFundMinRate());

					CurrencyDTObj.setHighValue(currencyOthInfoObj.getHighValue() );
					CurrencyDTObj.setAverageRate( currencyOthInfoObj.getAverageRate());
					CurrencyDTObj.setPlaceOrderLimit(currencyOthInfoObj.getPlaceOrderLimit());					

					CurrencyDTObj.setCreatedBy(currencyOthInfoObj.getExCurrencyMaster().getCreatedBy() );
					CurrencyDTObj.setCreatedDate(currencyOthInfoObj.getExCurrencyMaster().getCreatedDate());
					CurrencyDTObj.setModifiedBy( currencyOthInfoObj.getExCurrencyMaster().getModifiedBy());
					CurrencyDTObj.setModifiedDate(currencyOthInfoObj.getExCurrencyMaster().getModifiedDate() );
					CurrencyDTObj.setApprovedBy( currencyOthInfoObj.getExCurrencyMaster().getApprovedBy());
					CurrencyDTObj.setApprovedDate(currencyOthInfoObj.getExCurrencyMaster().getApprovedDate());
					CurrencyDTObj.setIsActive( currencyOthInfoObj.getExCurrencyMaster().getIsactive());
					CurrencyDTObj.setAllowFCPurchase(currencyOthInfoObj.getExCurrencyMaster().getAllowFCPurchase());
					CurrencyDTObj.setAllowFCSale(currencyOthInfoObj.getExCurrencyMaster().getAllowFCSale());

					CurrencyDTObj.setFimsCurrencyCode( currencyOthInfoObj.getExCurrencyMaster().getFimsCurrencyCode());
					if(currencyOthInfoObj.getExCurrencyMaster().getFsCountryMaster()!=null){
						CurrencyDTObj.setCountryName(bankCountryMap.get(currencyOthInfoObj.getExCurrencyMaster().getFsCountryMaster().getCountryId()));
						CurrencyDTObj.setCountryId( currencyOthInfoObj.getExCurrencyMaster().getFsCountryMaster().getCountryId());
					}
					CurrencyDTObj.setArabicDecimalName( currencyOthInfoObj.getExCurrencyMaster().getArabicDecimalName());
					CurrencyDTObj.setDecimalName( currencyOthInfoObj.getExCurrencyMaster().getDecimalName());
					CurrencyDTObj.setDecimalNumber(currencyOthInfoObj.getExCurrencyMaster().getDecinalNumber());
					CurrencyDTObj.setSwiftCurrency( currencyOthInfoObj.getExCurrencyMaster().getSwiftCurrency());
					CurrencyDTObj.setIsoCurrencyCode( currencyOthInfoObj.getExCurrencyMaster().getIsoCurrencyCode());
					CurrencyDTObj.setBooCheckUpdate(false);
					CurrencyDTObj.setApplicationContryId(currencyOthInfoObj.getFsCountryMaster().getCountryId() );
					if(currencyOthInfoObj.getExCurrencyMaster().getIsactive().equalsIgnoreCase( Constants.D)){
						CurrencyDTObj.setDynamicLabelForActivateDeactivate( Constants.ACTIVATE);
						CurrencyDTObj.setRenderEditButton(true );
						CurrencyDTObj.setRemarks( currencyOthInfoObj.getExCurrencyMaster().getRemarks());
						CurrencyDTObj.setCheckSave( false);
						CurrencyDTObj.setBooCheckDelete(false);
					}else if(currencyOthInfoObj.getExCurrencyMaster().getIsactive().equalsIgnoreCase( Constants.Yes)){
						CurrencyDTObj.setDynamicLabelForActivateDeactivate( Constants.DEACTIVATE);
						CurrencyDTObj.setRenderEditButton(true );
						CurrencyDTObj.setCheckSave( false);
						CurrencyDTObj.setBooCheckDelete(false);

					}else if(currencyOthInfoObj.getExCurrencyMaster().getIsactive().equalsIgnoreCase( Constants.U)){
						if(currencyOthInfoObj.getExCurrencyMaster().getModifiedBy()==null&&currencyOthInfoObj.getExCurrencyMaster().getModifiedDate()==null&&currencyOthInfoObj.getExCurrencyMaster().getRemarks()==null&&currencyOthInfoObj.getExCurrencyMaster().getApprovedBy()==null&&currencyOthInfoObj.getExCurrencyMaster().getApprovedDate()==null){
							CurrencyDTObj.setDynamicLabelForActivateDeactivate(Constants.DELETE);
							CurrencyDTObj.setRenderEditButton(true );
							CurrencyDTObj.setCheckSave( false);
							CurrencyDTObj.setBooCheckDelete(false);
						}else{
							CurrencyDTObj.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
							CurrencyDTObj.setRenderEditButton(true );
							CurrencyDTObj.setCheckSave( false);
							CurrencyDTObj.setBooCheckDelete(false);
						}
					} 

					viewList.add( CurrencyDTObj);
				}

			}

			if (viewList.size() > 0) {
				setBooRenderDatatable(true);
				addRecordsToDatatable();
				viewList.clear();
			} else {
				setSearchValue(null);
				setBooRenderSubmit(false);
				RequestContext.getCurrentInstance().execute("exist.show();");
			}
		}catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::viewAllRecordsFromDB");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}



	}

	public void checkStatusType(CurrencyDataTable currencyDataTable)
			throws IOException {

		if (currencyDataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)) {
			currencyDataTable.setRemarkCheck(true);
			setApprovedBy(currencyDataTable.getApprovedBy() );
			setApprovedDate(currencyDataTable.getApprovedDate() );
			RequestContext.getCurrentInstance().execute("remarks.show();");
			currencyDataTable.setBooCheckDelete(false);

		} else if (currencyDataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.ACTIVATE)) {
			removeRecord(currencyDataTable);
			currencyDataTable.setBooCheckDelete(false);
		}
		else if (currencyDataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DELETE)&&currencyDataTable.getRemarks()==null&&currencyDataTable.getApprovedBy()==null&&currencyDataTable.getApprovedDate()==null&&currencyDataTable.getModifiedBy()==null&&currencyDataTable.getModifiedDate()==null) {

			currencyDataTable.setBooCheckDelete(true);
			RequestContext.getCurrentInstance().execute("permanentDelete.show();");

		}else if (currencyDataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.REMOVE)) {
			removeRecord(currencyDataTable);
			currencyDataTable.setBooCheckDelete(false);
		}else {

			RequestContext.getCurrentInstance().execute("couldnot.show();");
		}

	}



	public void removeRecord(CurrencyDataTable currencyDTObj) {

		try{
			if (currencyDTObj.getCurrencyMasterPk()== null) {

				if (currencyDTObj.getCheckSave().equals(true)) {

					listDT.remove(currencyDTObj);
					if (listDT.size() <= 0) {
						setBooRenderSubmit(false);	 
						setBooRenderDatatable(false);
					}
				}

			} else {
				deActivateRecord(currencyDTObj);
				listDT.clear();
				viewAllRecordsFromDB( );
				listDT.addAll(viewList);
			}
		}catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::removeRecord");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}


	}
	public void remarkSelectedRecord() throws IOException {
		log.info("::::::::::::::::::remarkSelectedRecord() method called:::::::::::::::");
		try{
			for ( CurrencyDataTable currencyDataObj : listDT) {
				if (currencyDataObj.getRemarkCheck() != null) {
					if (currencyDataObj.getRemarkCheck().equals(true)) {
						currencyDataObj.setRemarks(getRemarks());
						removeRecord(currencyDataObj);
						setRemarks(null);
					}
				}
			}
		}catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::remarkSelectedRecord");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}

	}
	public void cancelRemarks(){
		setRemarks(null);

		try {
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("../common/currencydetailsmannual.xhtml");
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::remarkSelectedRecord");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}

	}
	public void deActivateRecord(CurrencyDataTable currencyDTObj){
		log.info( ":::::::::::::::::::::::::::::::deActivateRecord()::::::::::::::::::::::::::::");

		try{
			CurrencyMaster currencyMasterObj=new CurrencyMaster();
			/*
	currencyMasterObj.setCurrencyId(currencyDTObj.getCurrencyMasterPk() );
	currencyMasterObj.setCurrencyCode(currencyDTObj.getCurrencyCode() );
	currencyMasterObj.setCurrencyName(currencyDTObj.getCurrencyName() );
	currencyMasterObj.setArabicCurrencyName(currencyDTObj.getArabicCurrencyName() );
	currencyMasterObj.setCurrencyDesc(currencyDTObj.getCurrencyDesc());


	//currencyMasterObj.setCashMaxRate(currencyDTObj.getCashMaxRate());
	//currencyMasterObj.setCashMinRate(currencyDTObj.getCashMinRate());
	currencyMasterObj.setFimsCurrencyCode(currencyDTObj.getFimsCurrencyCode() );

	//currencyMasterObj.setFundMaxRate(currencyDTObj.getFundMaxRate());
	//currencyMasterObj.setFundMinRate(currencyDTObj.getFundMinRate());
	currencyMasterObj.setIsoCurrencyCode(currencyDTObj.getIsoCurrencyCode());
	currencyMasterObj.setDecinalNumber(currencyDTObj.getDecimalNumber());
	currencyMasterObj.setDecimalName(currencyDTObj.getDecimalName());
	currencyMasterObj.setArabicDecimalName(currencyDTObj.getArabicDecimalName());

	//currencyMasterObj.setCbkPrintInd( currencyDTObj.getCbkPrintInd());
	//currencyMasterObj.setCbkSortInd(currencyDTObj.getCbkSortInd());
	//currencyMasterObj.setOnlineInd(currencyDTObj.getOnlineInd());
	currencyMasterObj.setQuoteName(currencyDTObj.getQuoteName());
	currencyMasterObj.setArabicQuoteName(currencyDTObj.getArabicQuoteName());
	currencyMasterObj.setSwiftCurrency(currencyDTObj.getSwiftCurrency() );

	currencyMasterObj.setModifiedBy(session.getUserName());
	currencyMasterObj.setModifiedDate(new Date());
	currencyMasterObj.setCreatedBy(currencyDTObj.getCreatedBy());
	currencyMasterObj.setCreatedDate(currencyDTObj.getCreatedDate());


	CountryMaster countryMaster=new CountryMaster();
	countryMaster.setCountryId(currencyDTObj.getCountryId() );
	currencyMasterObj.setFsCountryMaster(countryMaster);*/

			if(currencyDTObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase( Constants.DEACTIVATE)){
				if(getRemarks()!=null){
					currencyMasterObj.setIsactive( Constants.D);
					//currencyMasterObj.setRemarks(currencyDTObj.getRemarks());
					log.info( "======remarks====="+getRemarks());
					icurencyService.saveDeactiveRec(currencyDTObj.getCurrencyMasterPk(), getRemarks(), session.getUserName(),currencyDTObj.getCurrencyOtherOnfoPk());
					RequestContext.getCurrentInstance().execute("deact.show();");
				}else{
					RequestContext.getCurrentInstance().execute("remarksmandatory.show();");
				}
			} else if(currencyDTObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.ACTIVATE)){
				String remarks=null;
				currencyMasterObj.setIsactive( Constants.U);
				icurencyService.saveDeactiveRec(currencyDTObj.getCurrencyMasterPk(), remarks, session.getUserName(),currencyDTObj.getCurrencyOtherOnfoPk());
				RequestContext.getCurrentInstance().execute("act.show();");
			}


		}
		catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::deActivateRecord");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}

		//viewAllRecordsFromDB();
	}


	public void removeRecordFromDB(CurrencyDataTable currencyDTObj){
		log.info( ":::::::::::::::::::::::::::hard delete record:::::::::::::::::::::::::::::::::::::::::::::::::");
		try{

			icurencyService.delteRecordFromOtherInfo(currencyDTObj.getCurrencyOtherOnfoPk());
			icurencyService.deleteRecordFromDB(currencyDTObj.getCurrencyMasterPk());
			listDT.remove( currencyDTObj);
		}catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::removeRecordFromDB");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}

	}


	public void confirmPermanentDelete(){
		try{
			if(listDT.size()>0){
				for (CurrencyDataTable  currencyDatatableObj : listDT) {
					if(currencyDatatableObj.getBooCheckDelete()){
						removeRecordFromDB(currencyDatatableObj);
						listDT.clear();
						viewAllRecordsFromDB( );
						listDT.addAll(viewList);

					}

				}
			}
		}catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::confirmPermanentDelete");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}



	public void clickOnGo() {
		clearAll();
		listDT.clear();
		setBooHideClear( false);
		setBooHideEdit(false);
		setBooHideSubmit( false);
		setBooRenderDatatable(false);
		setBooRenderSubmit(false);
		setBooSeach( false);
		if (getSelectType().equalsIgnoreCase("1")) {
			try {
				FacesContext.getCurrentInstance().getExternalContext()
				.redirect("../common/currencydetailsmannual.xhtml");
			} catch (NullPointerException ne) {
				// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				setErrorMessage("Method Name::clickOnGo");
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return;
			} catch (Exception exception) {
				// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
				setErrorMessage(exception.getMessage());
				RequestContext.getCurrentInstance().execute("error.show();");
				return;
			}
		} else {
			currencyListDT.clear();
			setRenderFileuploadDatatable(false);
			setRenderFileuploadSubmit(false);
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../common/currencydetailsfileupload.xhtml");
			} catch (NullPointerException ne) {
				// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				setErrorMessage("Method Name::clickOnGo");
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return;
			} catch (Exception exception) {
				// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
				setErrorMessage(exception.getMessage());
				RequestContext.getCurrentInstance().execute("error.show();");
				return;
			}
		}

	}

	public void exit() throws IOException {

		if (session.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} else if (session.getRoleId().equalsIgnoreCase("2")) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}else{
			FacesContext.getCurrentInstance() .getExternalContext().redirect("../common/saleshome.xhtml");
		}
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void pageNavigationToCurrencyMaster(){
		setSelectType( null);
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "commoncurrency.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../common/commoncurrency.xhtml");

		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::pageNavigationToCurrencyMaster");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}
	public void clearAll(){

		setApplicationCountryId( null);
		setSearchValue(null);
		setCurrencyMasterPk( null);
		setCurrencyCountryId(null);

		setCurrencyCode(null);
		setCurrencyName( null);
		setArabicCurrencyName( null);
		setCurrencyDesc( null);
		setCbkSortInd( null);
		setCbkPrintInd( null);
		setCashMinRate( null);
		setCashMaxRate( null);

		setCountryName( null);

		setArabicQuoteName( null);
		setQuoteName( null);
		setArabicDecimalName( null);
		setDecimalNumber( null);
		setDecimalName( null);
		setOnlineInd( null);
		setFimsCurrencyCode( null);
		setFundMaxRate( null);
		setFundMinRate( null);
		setSwiftCurrency(null);
		setIsoCurrencyCode( null);
		setAverageRate( null);
		setHighValue( null);
		
		setAllowFCPurchase(null);
		setAllowFCSale(null);
		setPlaceOrderLimit(null);


	}

	public void clearAll1(){

		setSearchValue(null);
		setCurrencyMasterPk( null);
		setCurrencyCountryId(null);
		setApplicationCountryId( null);


		setCurrencyName( null);
		setArabicCurrencyName( null);
		setCurrencyDesc( null);
		setCbkSortInd( null);
		setCbkPrintInd( null);
		setCashMinRate( null);
		setCashMaxRate( null);

		setCountryName( null);

		setArabicQuoteName( null);
		setQuoteName( null);
		setArabicDecimalName( null);
		setDecimalNumber( null);
		setDecimalName( null);
		setOnlineInd( null);
		setFimsCurrencyCode( null);
		setFundMaxRate( null);
		setFundMinRate( null);
		setSwiftCurrency(null);
		setIsoCurrencyCode( null);
		setAverageRate( null);
		setHighValue( null);
		setAllowFCPurchase(null);
		setAllowFCSale(null);


	}
	public void clearAll2(){

		setSearchValue(null);
		setCurrencyMasterPk( null);
		setCurrencyCountryId(null);
		setApplicationCountryId( null);
		setCurrencyCode( null);

		setCurrencyName( null);
		setArabicCurrencyName( null);
		setCurrencyDesc( null);
		setCbkSortInd( null);
		setCbkPrintInd( null);
		setCashMinRate( null);
		setCashMaxRate( null);

		setCountryName( null);

		setArabicQuoteName( null);
		setQuoteName( null);
		setArabicDecimalName( null);
		setDecimalNumber( null);
		setDecimalName( null);
		setOnlineInd( null);
		setFimsCurrencyCode( null);
		setFundMaxRate( null);
		setFundMinRate( null);
		setSwiftCurrency(null);
		setIsoCurrencyCode( null);
		setAverageRate( null);
		setHighValue( null);
		setAllowFCPurchase(null);
		setAllowFCSale(null);


	}
	public void clearAllfields(){


		setCurrencyMasterPk( null);
		setCurrencyOtherInfoPk(null);
		setCurrencyCountryId(null);

		setApplicationCountryId( null);
		setCurrencyName( null);
		setArabicCurrencyName( null);
		setCurrencyDesc( null);
		setCbkSortInd( null);
		setCbkPrintInd(null);
		setCashMinRate( null);
		setCashMaxRate( null);

		setCountryName(null);

		setArabicQuoteName(null);
		setQuoteName(null);
		setArabicDecimalName(null);
		setDecimalNumber(null);
		setDecimalName(null);
		setOnlineInd(null);
		setFimsCurrencyCode(null);
		setFundMaxRate(null);
		setFundMinRate(null);
		setSwiftCurrency(null);
		setIsoCurrencyCode( null);
		setAverageRate( null);
		setHighValue( null);
		setAllowFCPurchase(null);
		setAllowFCSale(null);

	}

	public BigDecimal getAverageRate() {
		return averageRate;
	}
	public void setAverageRate(BigDecimal averageRate) {
		this.averageRate = averageRate;
	}
	public BigDecimal getHighValue() {
		return highValue;
	}
	public void setHighValue(BigDecimal highValue) {
		this.highValue = highValue;
	}


	/* ******Excel File Read Using Apache POI API and Stored into DataBase Related Coding Started Here***************/
	private UploadedFile uploadedFile;
	private Boolean renderFileuploadDatatable=false;
	private Boolean renderFileuploadSubmit=false;


	private List<CurrencyDataTable> currencyListDT=new  CopyOnWriteArrayList<CurrencyDataTable>();
	public Boolean getBooCheckUpdate() {
		return booCheckUpdate;
	}
	public void setBooCheckUpdate(Boolean booCheckUpdate) {
		this.booCheckUpdate = booCheckUpdate;
	}

	public List<CurrencyDataTable> getCurrencyListDT() {
		return currencyListDT;
	}
	public void setCurrencyListDT(List<CurrencyDataTable> currencyListDT) {
		this.currencyListDT = currencyListDT;
	}
	public Boolean getRenderFileuploadDatatable() {
		return renderFileuploadDatatable;
	}
	public void setRenderFileuploadDatatable(Boolean renderFileuploadDatatable) {
		this.renderFileuploadDatatable = renderFileuploadDatatable;
	}
	public Boolean getCheckSave() {
		return checkSave;
	}
	public void setCheckSave(Boolean checkSave) {
		this.checkSave = checkSave;
	}
	public Boolean getRenderFileuploadSubmit() {
		return renderFileuploadSubmit;
	}
	public void setRenderFileuploadSubmit(Boolean renderFileuploadSubmit) {
		this.renderFileuploadSubmit = renderFileuploadSubmit;
	}
	public Boolean getCheckFile() {
		return checkFile;
	}
	public void setCheckFile(Boolean checkFile) {
		this.checkFile = checkFile;
	}
	private Boolean  checkFile=false;
	public void exportExcel(FileUploadEvent event) throws IOException, SerialException, SQLException {
		uploadedFile = event.getFile();
		uploadedFile.getFileName();
		setCheckFile( true);
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("uploadfile.show();");
	}

	public void uploadtoDatatable() {
		if( getCheckFile()){

			setRenderFileuploadDatatable(true);
			setRenderFileuploadSubmit(true);
			currencyListDT.clear();
			try {

				Workbook workbook = null;

				workbook = new HSSFWorkbook(uploadedFile.getInputstream());


				//Get the number of sheets in the xlsx file
				int numberOfSheets = workbook.getNumberOfSheets();

				//loop through each of the sheets
				for(int i=0; i < numberOfSheets; i++){

					//Get the nth sheet from the workbook
					Sheet sheet = workbook.getSheetAt(i);

					//every sheet has rows, iterate over them
					Iterator<Row> rowIterator = sheet.iterator();
					int rowNumber =1;
					while (rowIterator.hasNext()) 
					{


						Row row = rowIterator.next();
						if(rowNumber==1)
						{
							rowNumber++;
							continue;
						}



						//Every row has columns, get the column iterator and iterate over them
						Iterator<Cell> cellIterator = row.cellIterator();
						//  CityMasterDataTable cityMasterData = new CityMasterDataTable();
						CurrencyDataTable currencyDT=new CurrencyDataTable();

						while (cellIterator.hasNext()) 
						{
							//Get the Cell object
							Cell cell = cellIterator.next();

							//check the cell type and process accordingly
							switch(cell.getCellType()){
							case Cell.CELL_TYPE_STRING:

								if(cell.getColumnIndex()==0){
									String	currencyCode = cell.getStringCellValue().trim();
									log.info("Currency Code ="+currencyCode);
									currencyDT.setCurrencyCode(currencyCode);
								}

								if(cell.getColumnIndex()==1){
									String currencyName = cell.getStringCellValue().trim();
									log.info("Currency Name="+currencyName);
									currencyDT.setCurrencyName( currencyName);
								}
								if(cell.getColumnIndex()==2){
									String quoteName = cell.getStringCellValue().trim();
									log.info(" Quote Name="+quoteName);
									currencyDT.setQuoteName(quoteName );
								}
								if(cell.getColumnIndex()==4){
									String decimalName = cell.getStringCellValue().trim();
									log.info("  Decimal Name="+decimalName);
									currencyDT.setDecimalName(decimalName );
								}
								if(cell.getColumnIndex()==5){
									String  fimsCurrencyCode = cell.getStringCellValue().trim();
									log.info("  fimsCurrencyCode="+fimsCurrencyCode);
									currencyDT.setFimsCurrencyCode(fimsCurrencyCode );
								}
								if(cell.getColumnIndex()==6){
									String arabicCurrencyName = cell.getStringCellValue().trim();
									log.info("  arabicCurrencyName="+arabicCurrencyName);
									currencyDT.setArabicCurrencyName(arabicCurrencyName );
								}

								if(cell.getColumnIndex()==7){
									String arabicDecimalName = cell.getStringCellValue().trim();
									log.info("  arabicDecimalName="+arabicDecimalName);
									currencyDT.setArabicDecimalName(arabicDecimalName);
								}
								if(cell.getColumnIndex()==8){
									String  arabicQuoteName = cell.getStringCellValue().trim();
									log.info("  arabicQuoteName="+arabicQuoteName);
									currencyDT.setArabicQuoteName(arabicQuoteName );
								}
								if(cell.getColumnIndex()==9){
									String   swiftCurrency = cell.getStringCellValue().trim();
									log.info("  swiftCurrency="+swiftCurrency);
									currencyDT.setSwiftCurrency( swiftCurrency );
								}
								if(cell.getColumnIndex()==10){
									String  isoCurrencyCode = cell.getStringCellValue().trim();
									log.info("  isoCurrencyCode="+isoCurrencyCode);
									currencyDT.setIsoCurrencyCode(isoCurrencyCode );
								}
								if(cell.getColumnIndex()==19){
									String   onlineInd= cell.getStringCellValue().trim();
									log.info(" onlineInd="+onlineInd);
									currencyDT.setOnlineInd(onlineInd );
								}
								if(cell.getColumnIndex()==17){
									String    cbkPrintInd= cell.getStringCellValue().trim();
									log.info("cbkPrintInd="+cbkPrintInd);
									currencyDT.setCbkPrintInd(cbkPrintInd );
								} 


								break;
							case Cell.CELL_TYPE_NUMERIC:

								/*if(cell.getColumnIndex()==0){
                        		cell.getNumericCellValue();
                             	Double d= cell.getNumericCellValue();
                             	currencyDT.setCurrencyMasterPk(new BigDecimal(d.intValue()) );
                        		log.info("Currency ID ="+d.intValue());
                                            }*/
								if(cell.getColumnIndex()==3){
									Double d = cell.getNumericCellValue();
									Byte currencyDesc = d.byteValue(); 
									log.info("currencyDesc ="+currencyDesc);
									currencyDT.setCurrencyDesc(currencyDesc );

								}
								if(cell.getColumnIndex()==13){
									Double d = cell.getNumericCellValue();
									log.info(" Fund Min Rate ="+d.intValue());
									currencyDT.setFundMinRate( new BigDecimal(d));
								}
								if(cell.getColumnIndex()==14){
									Double d = cell.getNumericCellValue();
									log.info(" Fund Max Rate ="+d.intValue());
									currencyDT.setFundMaxRate( new BigDecimal(d));
								} 
								if(cell.getColumnIndex()==15){
									Double d = cell.getNumericCellValue();
									log.info(" Cash Min Rate ="+d.intValue());
									currencyDT.setCashMinRate( new BigDecimal(d));
								}
								if(cell.getColumnIndex()==16){
									Double d = cell.getNumericCellValue();
									log.info(" Cash Max Rate ="+d.intValue());
									currencyDT.setCashMaxRate(new BigDecimal(d));
								}
								if(cell.getColumnIndex()==18){
									Double d = cell.getNumericCellValue();
									log.info(" CBK SHORT IND ="+d.intValue());
									currencyDT.setCbkSortInd(new BigDecimal(d));
								} 
								if(cell.getColumnIndex()==11){
									Double d = cell.getNumericCellValue();
									log.info("Country id ="+d.intValue());
									currencyDT.setCountryId((new BigDecimal(d.intValue())));
									currencyDT.setCountryId(new BigDecimal(d.intValue()));

								}
								if(cell.getColumnIndex()==12){
									Double d = cell.getNumericCellValue();
									log.info(" Decimal Number ="+d.intValue());
									currencyDT.setDecimalNumber(new BigDecimal(d));
								}

								break;

							case Cell.CELL_TYPE_BLANK:

							}


						}
						//end of cell iterator
						List <CurrencyMaster>     currencyrecordList  =icurencyService.getRecordToCheckDuplicate(currencyDT.getCurrencyCode());
						if(currencyrecordList.size()>0){
							currencyDT.setDynamicLabelForActivateDeactivate( Constants.DUPLICATE);

						}else{
							currencyDT.setDynamicLabelForActivateDeactivate(Constants.New_Record);
						}
						currencyListDT.add(currencyDT );
						setBooRenderDatatable(false);

					} //end of rows iterator

				} //end of sheets for loop


			}  catch (NullPointerException ne) {
				// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				setErrorMessage("Method Name::uploadtoDatatable");
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return;
			} catch (Exception exception) {
				// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
				setErrorMessage(exception.getMessage());
				RequestContext.getCurrentInstance().execute("error.show();");
				return;
			}

		}   else{
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("upload.show();");
		}
	}
	public void saveFileUploadDatatableRecords(){
		clearAll();
		if( currencyListDT.isEmpty()){
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("dataNotFund.show();");
		}else{
			try{
				for(CurrencyDataTable currencyDTObj:currencyListDT){
					if(currencyDTObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase( "New_Record")){

						CurrencyMaster currencyMasterObj=new CurrencyMaster();


						currencyMasterObj.setCurrencyCode(currencyDTObj.getCurrencyCode() );
						currencyMasterObj.setCurrencyName(currencyDTObj.getCurrencyName() );
						currencyMasterObj.setArabicCurrencyName(currencyDTObj.getArabicCurrencyName() );
						//currencyMasterObj.setCurrencyDesc(currencyDTObj.getCurrencyDesc());



						currencyMasterObj.setFimsCurrencyCode(currencyDTObj.getFimsCurrencyCode() );
						currencyMasterObj.setIsoCurrencyCode(currencyDTObj.getIsoCurrencyCode());
						currencyMasterObj.setDecinalNumber(currencyDTObj.getDecimalNumber());
						currencyMasterObj.setDecimalName(currencyDTObj.getDecimalName());
						currencyMasterObj.setArabicDecimalName(currencyDTObj.getArabicDecimalName());
						currencyMasterObj.setQuoteName(currencyDTObj.getQuoteName());
						currencyMasterObj.setArabicQuoteName(currencyDTObj.getArabicQuoteName());
						currencyMasterObj.setSwiftCurrency(currencyDTObj.getSwiftCurrency() );

						CountryMaster countryMaster=new CountryMaster();
						countryMaster.setCountryId(currencyDTObj.getCountryId());
						currencyMasterObj.setFsCountryMaster(countryMaster);
						currencyMasterObj.setCreatedBy(session.getUserName());
						currencyMasterObj.setCreatedDate(new Date());
						currencyMasterObj.setIsactive(Constants.U);
						currencyMasterObj.setAllowFCPurchase(currencyDTObj.getAllowFCPurchase());
						currencyMasterObj.setAllowFCSale(currencyDTObj.getAllowFCSale());

						icurencyService.saveOrUpdate(currencyMasterObj);

						CurrencyOtherInformation currencyOthInfo = new CurrencyOtherInformation();

						currencyOthInfo.setCashMaxRate(currencyDTObj.getCashMaxRate());
						currencyOthInfo.setCashMinRate(currencyDTObj.getCashMinRate());
						currencyOthInfo.setFundMinRate(currencyDTObj.getFundMinRate());
						currencyOthInfo.setFundMaxRate(currencyDTObj.getFundMaxRate());
						currencyOthInfo.setCbkPrintIndicator(currencyDTObj.getCbkPrintInd() );
						currencyOthInfo.setCbkShortIndicator(currencyDTObj.getCbkSortInd());
						currencyOthInfo.setOnlineInd(currencyDTObj.getOnlineInd());
						currencyOthInfo.setCreatedby(session.getUserName());
						currencyOthInfo.setCreatedDate(new Date());
						currencyOthInfo.setExCurrencyMaster(currencyMasterObj);
						currencyOthInfo.setFsCountryMaster(countryMaster);
						


						icurencyService.saveOrUpdate(currencyOthInfo);	

					}

				} 
				RequestContext.getCurrentInstance().execute("complete.show();");
			}catch (NullPointerException ne) {
				// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				setErrorMessage("Method Name::saveFileUploadDatatableRecords");
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return;
			} catch (Exception exception) {
				// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
				setErrorMessage(exception.getMessage());
				RequestContext.getCurrentInstance().execute("error.show();");
				return;
			}
		}
		setRenderFileuploadSubmit(true);
		currencyListDT.clear();
	}

	public void clickOnOKFileSave() {
		setRenderFileuploadDatatable(false);
		setRenderFileuploadSubmit( false);
		currencyListDT.clear();
		setRenderFileuploadDatatable(false);

		try {
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("../common/currencydetailsfileupload.xhtml");
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::clickOnOKFileSave");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}



	}
	public BigDecimal getCurrencyOtherInfoPk() {
		return currencyOtherInfoPk;
	}
	public void setCurrencyOtherInfoPk(BigDecimal currencyOtherInfoPk) {
		this.currencyOtherInfoPk = currencyOtherInfoPk;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public List<CurrencyDataTable> getListCurrency() {
		return listCurrency;
	}
	public void setListCurrency(List<CurrencyDataTable> listCurrency) {
		this.listCurrency = listCurrency;
	}

	private String searchValue;

	public void searchMethod( ){
		viewAllRecordsFromDB();

	}
	public String getSearchValue() {
		return searchValue;
	}
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}
	public Boolean getBooSeach() {
		return booSeach;
	}
	public void setBooSeach(Boolean booSeach) {
		this.booSeach = booSeach;
	}

	public void fimsCurrencyCode(){
		try{
			if(getFimsCurrencyCode()!=null){
				List<FimsCurmas>    listFims=icurencyService.getFimsCurrencyCode( getFimsCurrencyCode());
				if(listFims.size()==0){
					setFimsCurrencyCode(null);
					RequestContext.getCurrentInstance().execute("fimscode.show();");	
				} 
			}
			/*List<CurrencyMaster> currencyMaster =  icurencyService.getRecordToCheckDuplicate( getCurrencyCode());
	if(currencyMaster.size()>0){
	 for(CurrencyMaster currency:currencyMaster){
		 if(currency.getFimsCurrencyCode()!=null){
		 if(currency.getFimsCurrencyCode()!=null &&currency.getFimsCurrencyCode().equals(getFimsCurrencyCode())){
		 }else{
			 setFimsCurrencyCode(null);
			 RequestContext.getCurrentInstance().execute("fimscode.show();");	

		 }
		 }

	 }

	}*/

			//}


		}catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::fimsCurrencyCode");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}
	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}
	public BigDecimal getPlaceOrderLimit() {
		return placeOrderLimit;
	}
	public void setPlaceOrderLimit(BigDecimal placeOrderLimit) {
		this.placeOrderLimit = placeOrderLimit;
	}
	

}










