package com.amg.exchange.enquiry.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;


import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.enquiry.model.CashDepositInquiryModelView;
import com.amg.exchange.enquiry.model.FundTransferInquiryModelView;
import com.amg.exchange.enquiry.service.IFundTransferInquiryService;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("cashDepositInquiryBean")
@Scope("session")
public class CashDepositInquiryBean<T>  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Create Session
	SessionStateManage sessionStateManage = new SessionStateManage();
	
	// Auto wire required Services
	@Autowired
	IGeneralService<T> generalService;
	
	@Autowired
	IFundTransferInquiryService ifundTransferInquiryService;
	
	// Necessary lists for Form
	private List<String> lstToCashier = new ArrayList<String>();
	private List<CurrencyMaster> lstCurrencyMaster = new ArrayList<CurrencyMaster>();
	private List<CashDepositInquiryBean> lstforDataTable = new ArrayList<CashDepositInquiryBean>();
	private List<String> lstFromBranchName = new ArrayList<String>();
	
	public List<String> getLstToCashier() {
		return lstToCashier;
	}
	public void setLstToCashier(List<String> lstToCashier) {
		this.lstToCashier = lstToCashier;
	}	
	
	public List<CurrencyMaster> getLstCurrencyMaster() {
		return lstCurrencyMaster;
	}
	public void setLstCurrencyMaster(List<CurrencyMaster> lstCurrencyMaster) {
		this.lstCurrencyMaster = lstCurrencyMaster;
	}

	public List<CashDepositInquiryBean> getLstforDataTable() {
		return lstforDataTable;
	}
	public void setLstforDataTable(List<CashDepositInquiryBean> lstforDataTable) {
		this.lstforDataTable = lstforDataTable;
	}

	public List<String> getLstFromBranchName() {
		return lstFromBranchName;
	}
	public void setLstFromBranchName(List<String> lstFromBranchName) {
		this.lstFromBranchName = lstFromBranchName;
	}




	// Form Fields
	private String cashTransfer;
	private BigDecimal currencyId;
	private BigDecimal currencyCode;
	private String currencyName;
	private Date fromDocumentDate;
	private Date toDocumentDate;
	private BigDecimal locationId;
	private String locationName;
	
	// for Data Table
	private BigDecimal branchId;
	private String branchName;
	private BigDecimal documentNo;
	private BigDecimal documentCode;
	private BigDecimal documentFinancialYr;
	private String currencyDesc;
	private BigDecimal amountDeposited;
	
	// extra variables
	private Date effectiveMinDate = new Date();
	private Boolean booRenderDataTablePrint = false;


	public String getCashTransfer() {
		return cashTransfer;
	}
	public void setCashTransfer(String cashTransfer) {
		this.cashTransfer = cashTransfer;
	}
	
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
	
	public Date getFromDocumentDate() {
		return fromDocumentDate;
	}
	public void setFromDocumentDate(Date fromDocumentDate) {
		this.fromDocumentDate = fromDocumentDate;
	}
	
	public Date getToDocumentDate() {
		return toDocumentDate;
	}
	public void setToDocumentDate(Date toDocumentDate) {
		this.toDocumentDate = toDocumentDate;
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
	
	public BigDecimal getBranchId() {
		return branchId;
	}
	public void setBranchId(BigDecimal branchId) {
		this.branchId = branchId;
	}
	
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	
	public BigDecimal getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}
	
	public BigDecimal getDocumentCode() {
		return documentCode;
	}
	public void setDocumentCode(BigDecimal documentCode) {
		this.documentCode = documentCode;
	}
		
	public BigDecimal getDocumentFinancialYr() {
		return documentFinancialYr;
	}
	public void setDocumentFinancialYr(BigDecimal documentFinancialYr) {
		this.documentFinancialYr = documentFinancialYr;
	}
	
	public String getCurrencyDesc() {
		return currencyDesc;
	}
	public void setCurrencyDesc(String currencyDesc) {
		this.currencyDesc = currencyDesc;
	}
	
	public BigDecimal getAmountDeposited() {
		return amountDeposited;
	}
	public void setAmountDeposited(BigDecimal amountDeposited) {
		this.amountDeposited = amountDeposited;
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
	

	
	public CashDepositInquiryBean() {
		super();
	}
	
	
	public CashDepositInquiryBean(BigDecimal branchId, String branchName,
			BigDecimal documentNo, BigDecimal documentCode,
			BigDecimal documentFinancialYr, String currencyDesc, BigDecimal amountDeposited) {
		super();
		this.branchId = branchId;
		this.branchName = branchName;
		this.documentNo = documentNo;
		this.documentCode = documentCode;
		this.documentFinancialYr = documentFinancialYr;
		this.currencyDesc = currencyDesc;
		this.amountDeposited = amountDeposited;
	}
	
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	// page navigation
	public void cashDepositInquiryNavigation() {

		// clear All Fields and Lists
		clearAll();
		// fetch all Currency based on application country id
		fetchAllCurrencyBasedonAppCountryId();
		// Doc Date is current Date 
		complaintLogDateCurrentDate();
		// fetch Doc Code and Desc , To Cashier and From Branch Name
		fetchDataFromView();

		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "CashDepositInquiry.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../enquiry/CashDepositInquiry.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void clearAll(){

		setCurrencyId(null);
		setCurrencyCode(null);
		setCurrencyName(null);
		setLocationId(null);
		setLocationName(null);
		setDocumentCode(null);
		setDocumentFinancialYr(null);
		setBranchId(null);
		setBranchName(null);
		setDocumentNo(null);
		setFromDocumentDate(null);
		setToDocumentDate(null);
		setCashTransfer(null);

		setBooRenderDataTablePrint(false);

		lstforDataTable.clear();

	}
	
	// to fetch all currency based on session country id
	public void fetchAllCurrencyBasedonAppCountryId(){
		lstCurrencyMaster.clear();
		List<CurrencyMaster> lstCurrency =  generalService.getCurrencyList();
		if(lstCurrency.size() != 0){
			lstCurrencyMaster.addAll(lstCurrency);
			setCurrencyId(new BigDecimal(sessionStateManage.getCurrencyId()));
		}
	}
	
	// set Doc Date Current Date
	public void complaintLogDateCurrentDate() {

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String dealDate = format.format(new Date());

		try {
			setToDocumentDate(format.parse(dealDate));
			setFromDocumentDate(format.parse(dealDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}
	
	// fetch Document Code , Document Description from view VW_EX_FUND_TRANSFER_INQUIRY
	public void fetchDataFromView(){

		lstToCashier.clear();
		lstFromBranchName.clear();
		List<String> dupCheckforToCashier = new ArrayList<String>();
		List<String> dupCheckforFromBranchName = new ArrayList<String>();

		List<CashDepositInquiryModelView> lstCashDepositInq = ifundTransferInquiryService.getAllCashDepositInquiry();

		if(lstCashDepositInq.size() != 0){

			for (CashDepositInquiryModelView cashDepositInquiryModelView : lstCashDepositInq) {
				if(cashDepositInquiryModelView.getToCashier() != null){
					if(!dupCheckforToCashier.contains(cashDepositInquiryModelView.getToCashier())) {
						dupCheckforToCashier.add(cashDepositInquiryModelView.getToCashier());
						lstToCashier.add(cashDepositInquiryModelView.getToCashier());
					}
				}
			}

			if(lstToCashier.size() == 1){
				CashDepositInquiryModelView lstCashierName = lstCashDepositInq.get(0);
				setCashTransfer(lstCashierName.getToCashier());
			}else{
				setCashTransfer(null);
			}

		}

		if(lstCashDepositInq.size() != 0){

			for (CashDepositInquiryModelView cashDepositInquiryModelView : lstCashDepositInq) {
				if(!dupCheckforFromBranchName.contains(cashDepositInquiryModelView.getFromBranchName())) {
					dupCheckforFromBranchName.add(cashDepositInquiryModelView.getFromBranchName());
					lstFromBranchName.add(cashDepositInquiryModelView.getFromBranchName());
				}
			}

			if(lstFromBranchName.size() == 1){
				CashDepositInquiryModelView lstFromBranchName = lstCashDepositInq.get(0);
				setLocationName(lstFromBranchName.getFromBranchName());
			}else{
				setLocationName(null);
			}

		}

	}
	
	// Search operation for Cash Deposit inquiry
	public void searchForCashDepositInquiry(){
		
		lstforDataTable.clear();
		
		if(getCashTransfer() != null && getCurrencyId() != null && getFromDocumentDate() != null && getToDocumentDate() != null && getLocationName() != null ){
			
			List<CashDepositInquiryModelView> lstCashDepositInq = ifundTransferInquiryService.getCashDepositInquiryBasedonFormDetails(getCashTransfer(),getCurrencyId(),getFromDocumentDate(),getToDocumentDate(),getLocationName());
			
			if (lstCashDepositInq.size() != 0) {
				
				setBooRenderDataTablePrint(true);
				
				for (CashDepositInquiryModelView cashDepositInquiryModelView : lstCashDepositInq) {
					
					CashDepositInquiryBean lstCashDepositDt = new CashDepositInquiryBean();
					
					lstCashDepositDt.setBranchName(cashDepositInquiryModelView.getFromBranchName());
					lstCashDepositDt.setDocumentNo(cashDepositInquiryModelView.getDocumentNo());
					lstCashDepositDt.setDocumentCode(cashDepositInquiryModelView.getDocumentCode());
					lstCashDepositDt.setDocumentFinancialYr(cashDepositInquiryModelView.getDocumentFinancialYear());
					lstCashDepositDt.setCurrencyDesc(cashDepositInquiryModelView.getCurrencyName());
					lstCashDepositDt.setAmountDeposited(cashDepositInquiryModelView.getTotalValues());
					
					lstforDataTable.add(lstCashDepositDt);
				}
				
			}else{
				setBooRenderDataTablePrint(false);
				RequestContext.getCurrentInstance().execute("norecordsfound.show();");
			}
		
		}else{
			// any one not available in the form
			setBooRenderDataTablePrint(false);
		}

	}
	
	
	// Exit from this module
	public void exit() throws IOException {
		if (sessionStateManage.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}
	}
	
	//print button action
	public void printCashDepositInquiry(){

	}
	

}
