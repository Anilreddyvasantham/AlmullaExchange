package com.amg.exchange.enquiry.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.complaint.service.IComplaintService;
import com.amg.exchange.enquiry.model.FundTransferInquiryModelView;
import com.amg.exchange.enquiry.service.IFundTransferInquiryService;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;
import com.sun.org.apache.xpath.internal.operations.Bool;

@Component("fundTransferInquiryBean")
@Scope("session")
public class FundTransferInquiryBean<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log=Logger.getLogger(FundTransferInquiryBean.class);
	// Create Session
	SessionStateManage sessionStateManage = new SessionStateManage();

	// Auto wire required Services
	@Autowired
	IGeneralService<T> generalService;

	@Autowired
	IComplaintService<T> iComplaintService;
	
	@Autowired
	IFundTransferInquiryService ifundTransferInquiryService;

	// Necessary lists for Form
	private List<UserFinancialYear> dealYearList = new ArrayList<UserFinancialYear>();
	private List<FundTransferInquiryBean> lstforDataTable = new ArrayList<FundTransferInquiryBean>();
	private List<CurrencyMaster> lstCurrencyMaster = new ArrayList<CurrencyMaster>();
	private List<FundTransferInquiryModelView> lstOfFundTransferInquiry = new ArrayList<FundTransferInquiryModelView>();
	private List<String> lstToCashier = new ArrayList<String>();
	private List<String> lstFromBranchName = new ArrayList<String>();
	private List<BigDecimal> lstDocumentNum = new ArrayList<BigDecimal>();
	private Map<BigDecimal, BigDecimal> mapFinancialYearIdAndYear = new HashMap<BigDecimal, BigDecimal>();

	public List<UserFinancialYear> getDealYearList() {
		return dealYearList;
	}
	public void setDealYearList(List<UserFinancialYear> dealYearList) {
		this.dealYearList = dealYearList;
	}

	public List<FundTransferInquiryBean> getLstforDataTable() {
		return lstforDataTable;
	}
	public void setLstforDataTable(List<FundTransferInquiryBean> lstforDataTable) {
		this.lstforDataTable = lstforDataTable;
	}
	
	public List<CurrencyMaster> getLstCurrencyMaster() {
		return lstCurrencyMaster;
	}
	public void setLstCurrencyMaster(List<CurrencyMaster> lstCurrencyMaster) {
		this.lstCurrencyMaster = lstCurrencyMaster;
	}

	public List<FundTransferInquiryModelView> getLstOfFundTransferInquiry() {
		return lstOfFundTransferInquiry;
	}
	public void setLstOfFundTransferInquiry(List<FundTransferInquiryModelView> lstOfFundTransferInquiry) {
		this.lstOfFundTransferInquiry = lstOfFundTransferInquiry;
	}

	public List<String> getLstToCashier() {
		return lstToCashier;
	}
	public void setLstToCashier(List<String> lstToCashier) {
		this.lstToCashier = lstToCashier;
	}
	
	public List<String> getLstFromBranchName() {
		return lstFromBranchName;
	}
	public void setLstFromBranchName(List<String> lstFromBranchName) {
		this.lstFromBranchName = lstFromBranchName;
	}

	public Map<BigDecimal, BigDecimal> getMapFinancialYearIdAndYear() {
		return mapFinancialYearIdAndYear;
	}
	public void setMapFinancialYearIdAndYear(Map<BigDecimal, BigDecimal> mapFinancialYearIdAndYear) {
		this.mapFinancialYearIdAndYear = mapFinancialYearIdAndYear;
	}

	public List<BigDecimal> getLstDocumentNum() {
		return lstDocumentNum;
	}
	public void setLstDocumentNum(List<BigDecimal> lstDocumentNum) {
		this.lstDocumentNum = lstDocumentNum;
	}





	// Form Fields
	private BigDecimal currencyId;
	private BigDecimal currencyCode;
	private String currencyName;
	private BigDecimal locationId;
	private String locationName;
	private BigDecimal documentCode;
	private String documentName;
	private BigDecimal financialYearId;
	private BigDecimal financialYear;
	private BigDecimal documentNo;
	private Date documentDate;
	private String cashTransfer;
	
	// extra variables
	private Date effectiveMinDate = new Date();
	private Boolean booRenderDataTablePrint = false;

	// Data Table Fields
	private BigDecimal lineNo;
	private BigDecimal denaminationNo;
	private String denominationDetails;
	private BigDecimal quantity;
	private BigDecimal cashAmount;

	public BigDecimal getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	public BigDecimal getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(BigDecimal currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public BigDecimal getLocationId() {
		return locationId;
	}
	public void setLocationId(BigDecimal locationId) {
		this.locationId = locationId;
	}

	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public BigDecimal getDocumentCode() {
		return documentCode;
	}
	public void setDocumentCode(BigDecimal documentCode) {
		this.documentCode = documentCode;
	}

	public String getDocumentName() {
		return documentName;
	}
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public BigDecimal getFinancialYearId() {
		return financialYearId;
	}
	public void setFinancialYearId(BigDecimal financialYearId) {
		this.financialYearId = financialYearId;
	}

	public BigDecimal getFinancialYear() {
		return financialYear;
	}
	public void setFinancialYear(BigDecimal financialYear) {
		this.financialYear = financialYear;
	}

	public BigDecimal getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}
	
	public Date getDocumentDate() {
		return documentDate;
	}
	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}

	public String getCashTransfer() {
		return cashTransfer;
	}
	public void setCashTransfer(String cashTransfer) {
		this.cashTransfer = cashTransfer;
	}

	public BigDecimal getLineNo() {
		return lineNo;
	}
	public void setLineNo(BigDecimal lineNo) {
		this.lineNo = lineNo;
	}

	public BigDecimal getDenaminationNo() {
		return denaminationNo;
	}
	public void setDenaminationNo(BigDecimal denaminationNo) {
		this.denaminationNo = denaminationNo;
	}

	public String getDenominationDetails() {
		return denominationDetails;
	}
	public void setDenominationDetails(String denominationDetails) {
		this.denominationDetails = denominationDetails;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getCashAmount() {
		return cashAmount;
	}
	public void setCashAmount(BigDecimal cashAmount) {
		this.cashAmount = cashAmount;
	}

	public Date getEffectiveMinDate() {
		return effectiveMinDate;
	}
	public void setEffectiveMinDate(Date effectiveMinDate) {
		this.effectiveMinDate = effectiveMinDate;
	}
	
	public Boolean getBooRenderDataTablePrint() {
		return booRenderDataTablePrint;
	}
	public void setBooRenderDataTablePrint(Boolean booRenderDataTablePrint) {
		this.booRenderDataTablePrint = booRenderDataTablePrint;
	}
	
	
	
	public FundTransferInquiryBean() {
		super();
	}
	
	// For Data Table
	public FundTransferInquiryBean(BigDecimal lineNo, String denominationDetails,BigDecimal denaminationNo,BigDecimal quantity, BigDecimal cashAmount) {
		super();
		this.lineNo = lineNo;
		this.denominationDetails = denominationDetails;
		this.denaminationNo = denaminationNo;
		this.quantity = quantity;
		this.cashAmount = cashAmount;
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	// page navigation
	public void fundTransferInquiryNavigation() {
		
		// clear All Fields and Lists
		clearAll();
		// fetch all Currency based on application country id
		fetchAllCurrencyBasedonAppCountryId();
		// to fetch financial year
		fetchFinancialYear();
		// Doc Date is current Date 
		complaintLogDateCurrentDate();
		// fetch Doc Code and Desc , To Cashier and From Branch Name
		fetchDataFromView();

		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "FundTransferInquiry.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../enquiry/FundTransferInquiry.xhtml");
		} catch(Exception exception){
			  log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			  setErrorMessage(exception.getMessage()); 
			  RequestContext.getCurrentInstance().execute("error.show();");
			  return;       
			  }
	}


	//Fetch Remittance year , Complaint Year , Complaint Reference along with Deal year Ids
	public void fetchFinancialYear() {

		dealYearList.clear();
		mapFinancialYearIdAndYear.clear();
		setFinancialYear(null);
		setFinancialYearId(null);

		try{

			List<UserFinancialYear> lstFinancialYear = iComplaintService.getAllDocumentYear();
			if(lstFinancialYear.size() != 0){
				dealYearList.addAll(lstFinancialYear);
				for (UserFinancialYear userFinancialYear : lstFinancialYear) {
					mapFinancialYearIdAndYear.put(userFinancialYear.getFinancialYearID(), userFinancialYear.getFinancialYear());
				}
				
			}

			List<UserFinancialYear> lstCurrentDealYear = generalService.getDealYear(new Date());
			if(lstCurrentDealYear.size() != 0){
				BigDecimal financialYear = lstCurrentDealYear.get(0).getFinancialYear();
				BigDecimal financialYearId = lstCurrentDealYear.get(0).getFinancialYearID();
				setFinancialYear(financialYear);
				setFinancialYearId(financialYearId);
			}else{
				setFinancialYear(null);
				setFinancialYearId(null);
			}

		}catch(Exception exception){
			  log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			  setErrorMessage(exception.getMessage()); 
			  RequestContext.getCurrentInstance().execute("error.show();");
			  return;       
			  }

	}
	
	// ajax call on date selection
	public void selectingDateToFetchRecords(){
		try{
		if(getFinancialYearId() != null){
			setFinancialYear(mapFinancialYearIdAndYear.get(getFinancialYearId()));
		}else{
			setFinancialYear(null);
		}
		}catch(Exception exception){
			  log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			  setErrorMessage(exception.getMessage()); 
			  RequestContext.getCurrentInstance().execute("error.show();");
			  return;       
			  }
		
	}
	
	// set Doc Date Current Date
	public void complaintLogDateCurrentDate() {

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String dealDate = format.format(new Date());

		try {
			setDocumentDate(format.parse(dealDate));

		} catch (ParseException e) {
			e.printStackTrace();
		}

	}
	
	//print button action
	public void printFundTransferInquiry(){
		
	}
	
	// Exit from this module
	public void exit() throws IOException {
		if (sessionStateManage.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}
	}
	
	// to fetch all currency based on session country id
	public void fetchAllCurrencyBasedonAppCountryId(){
		  try{
		lstCurrencyMaster.clear();
		List<CurrencyMaster> lstCurrency =  generalService.getCurrencyList();
		if(lstCurrency.size() != 0){
			lstCurrencyMaster.addAll(lstCurrency);
			setCurrencyId(new BigDecimal(sessionStateManage.getCurrencyId()));
		}
		  }catch(Exception exception){
				  log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
				  setErrorMessage(exception.getMessage()); 
				  RequestContext.getCurrentInstance().execute("error.show();");
				  return;       
				  }
	}
	
	// fetch Document Code , Document Description from view VW_EX_FUND_TRANSFER_INQUIRY
	public void fetchDataFromView(){
		try{
		lstToCashier.clear();
		lstFromBranchName.clear();
		lstDocumentNum.clear();
		List<String> dupCheckforToCashier = new ArrayList<String>();
		List<String> dupCheckforFromBranchName = new ArrayList<String>();
		List<BigDecimal> dupCheckforDocumentNo = new ArrayList<BigDecimal>();
		
		List<FundTransferInquiryModelView> lstfundtransferInq = ifundTransferInquiryService.getAllFundTransferInquiry();
		
		if(lstfundtransferInq.size() != 0){
			FundTransferInquiryModelView lstFundTrnInq = lstfundtransferInq.get(0);
			setDocumentCode(lstFundTrnInq.getDocumentCode());
			setDocumentName(lstFundTrnInq.getDocumentDesc());
		}else{
			setDocumentCode(null);
			setDocumentName(null);
		}
		
		if(lstfundtransferInq.size() != 0){

			for (FundTransferInquiryModelView fundTransferInquiryModelView : lstfundtransferInq) {
				if(fundTransferInquiryModelView.getToCashier() != null){
					if(!dupCheckforToCashier.contains(fundTransferInquiryModelView.getToCashier())) {
						dupCheckforToCashier.add(fundTransferInquiryModelView.getToCashier());
						lstToCashier.add(fundTransferInquiryModelView.getToCashier());
					}
				}
			}
			
			if(lstToCashier.size() == 1){
				FundTransferInquiryModelView lstCashierName = lstfundtransferInq.get(0);
				setCashTransfer(lstCashierName.getToCashier());
			}else{
				setCashTransfer(null);
			}

		}
		
		if(lstfundtransferInq.size() != 0){

			for (FundTransferInquiryModelView fundTransferInquiryModelView : lstfundtransferInq) {
				if(!dupCheckforFromBranchName.contains(fundTransferInquiryModelView.getFromBranchName())) {
					dupCheckforFromBranchName.add(fundTransferInquiryModelView.getFromBranchName());
					lstFromBranchName.add(fundTransferInquiryModelView.getFromBranchName());
				}
			}
			
			if(lstFromBranchName.size() == 1){
				FundTransferInquiryModelView lstFromBranchName = lstfundtransferInq.get(0);
				setLocationName(lstFromBranchName.getFromBranchName());
			}else{
				setLocationName(null);
			}

		}
		
		if(lstfundtransferInq.size() != 0){

			for (FundTransferInquiryModelView fundTransferInquiryModelView : lstfundtransferInq) {
				if(!dupCheckforDocumentNo.contains(fundTransferInquiryModelView.getDocumentNo())) {
					dupCheckforDocumentNo.add(fundTransferInquiryModelView.getDocumentNo());
					lstDocumentNum.add(fundTransferInquiryModelView.getDocumentNo());
				}
			}
			
			if(lstDocumentNum.size() == 1){
				FundTransferInquiryModelView lstDocNum = lstfundtransferInq.get(0);
				setDocumentNo(lstDocNum.getDocumentNo());
			}else{
				setDocumentNo(null);
			}

		}
		}catch(NullPointerException ne){
		  log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
		  setErrorMessage("MethodName::fetchDataFromView");
		  RequestContext.getCurrentInstance().execute("nullPointerId.show();");
		  return; 
		  }catch(Exception exception){
		  log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		  setErrorMessage(exception.getMessage()); 
		  RequestContext.getCurrentInstance().execute("error.show();");
		  return;       
		  }

	}
	
	// Search operation for fund transfer inquiry
	public void searchForFundTransferInquiry(){
		try{
		lstforDataTable.clear();
		
		if(getCurrencyId() != null && getLocationName() != null && getDocumentCode() != null &&
				getDocumentName() != null && getFinancialYearId() != null && getDocumentNo() != null &&
				getDocumentDate() != null && getCashTransfer() != null){
			
			List<FundTransferInquiryModelView> lstfundtransferInq = ifundTransferInquiryService.getFundTransferInquiryBasedonFormDetails(getCurrencyId(),getLocationName(),getDocumentCode(),getDocumentName(),getFinancialYear(),getDocumentNo(),getDocumentDate(),getCashTransfer());
			
			if(lstfundtransferInq.size() != 0){
				
				setBooRenderDataTablePrint(true);
				
				for (FundTransferInquiryModelView fundTransferInquiryModelView : lstfundtransferInq) {
					
					FundTransferInquiryBean lstDataTable = new FundTransferInquiryBean();
					
					lstDataTable.setLineNo(fundTransferInquiryModelView.getDocumentLNo());
					lstDataTable.setDenominationDetails(fundTransferInquiryModelView.getDenominationDesc());
					lstDataTable.setDenaminationNo(fundTransferInquiryModelView.getDenominationAmount());
					lstDataTable.setQuantity(fundTransferInquiryModelView.getNotesQty());
					lstDataTable.setCashAmount(fundTransferInquiryModelView.getCashAmount());
					
					lstforDataTable.add(lstDataTable);
					
				}
				
			}else{
				setBooRenderDataTablePrint(false);
				RequestContext.getCurrentInstance().execute("norecordsfound.show();");
			}
			
		}else{
			// any one not available in the form
			setBooRenderDataTablePrint(false);
		}
		}catch(NullPointerException ne){
			  log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			  setErrorMessage("MethodName::searchForFundTransferInquiry");
			  RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			  return; 
			  }catch(Exception exception){
			  log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			  setErrorMessage(exception.getMessage()); 
			  RequestContext.getCurrentInstance().execute("error.show();");
			  return;       
			  }
	}
	
	public void clearAll(){
		
		setCurrencyId(null);
		setCurrencyCode(null);
		setCurrencyName(null);
		setLocationId(null);
		setLocationName(null);
		setDocumentCode(null);
		setDocumentName(null);
		setFinancialYear(null);
		setFinancialYearId(null);
		setDocumentNo(null);
		setDocumentDate(null);
		setCashTransfer(null);
		
		setBooRenderDataTablePrint(false);
		
		lstforDataTable.clear();
		
	}


	  private String errorMessage;

	  public String getErrorMessage() {
		    return errorMessage;
	  }

	  public void setErrorMessage(String errorMessage) {
		    this.errorMessage = errorMessage;
	  }

}
