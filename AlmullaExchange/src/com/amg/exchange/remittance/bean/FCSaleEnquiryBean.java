package com.amg.exchange.remittance.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.service.IPurposeOfTransctionService;
import com.amg.exchange.common.service.ISourceOfIncomeService;
import com.amg.exchange.foreigncurrency.bean.ForeignCurrencySaleDatatable;
import com.amg.exchange.foreigncurrency.model.CurrencyAdjustView;
import com.amg.exchange.foreigncurrency.model.ForeignCurrencyAdjustApp;
import com.amg.exchange.foreigncurrency.model.PurposeOfTransaction;
import com.amg.exchange.foreigncurrency.model.ReceiptPaymentApp;
import com.amg.exchange.foreigncurrency.model.ReceiptPaymentView;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.foreigncurrency.service.IForeignCurrencyPurchaseService;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.treasury.service.IDealTrackerInfoService;
import com.amg.exchange.treasury.service.ISpecialCustomerDealRequestService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;
@Component("fcsaleEnquiryBean")
@Scope("session")
public class FCSaleEnquiryBean<T> implements Serializable {

	 
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(FCSaleEnquiryBean.class);
	
	private BigDecimal documentFinancialYear;
	private BigDecimal documentNo;
	private BigDecimal customerId;
	private BigDecimal customerReference;
	private String customerName;
	private String location;
	private  String telephoneNo;
	private String sourceofIncome;
	private String purpose;
	private String foreignCurrency;
	private String localCurrency;
	private String remarks;
	private String purchaseCurrency;
	private String saleCurrency;
	private String collectionMode;
	private BigDecimal paidAmount;
	private BigDecimal netAmount;
	private BigDecimal refundAmount;
	private BigDecimal collectionAmount;
	private String branchName;
	private BigDecimal foreignTrnxAmount;
	private BigDecimal localTrnxAmount;
	private BigDecimal localNetAmount;
	private BigDecimal averageRate;
	private Date accountmmyy;
	
	private String transactionType;
	private int notesQuantity;
	private BigDecimal exchangeRate;
	private BigDecimal denaminationAmount;
	private String progNumber;
	private String documentStatus;
	private BigDecimal notesQty;
	private String progno;
	private int adjustmentAmount;
	private Boolean renderPanel=false;
	private String errorMsg;
	private Boolean booError=false;
	private Boolean renderForRemittanceBranchWiseEnquiry=false;
	private Boolean renderBackButtonForRemittanceBranchWiseEnquiry=false;
	private Boolean renderBackButtonForRemittance=false;
	private String createdBy;
	private Boolean renderBackButtonForReceiptEnquiry=false;
	
	
	@Autowired
	IDealTrackerInfoService iDealTrackerInfoService;
	
	
	public Boolean getRenderBackButtonForRemittanceBranchWiseEnquiry() {
		return renderBackButtonForRemittanceBranchWiseEnquiry;
	}
	public void setRenderBackButtonForRemittanceBranchWiseEnquiry(
			Boolean renderBackButtonForRemittanceBranchWiseEnquiry) {
		this.renderBackButtonForRemittanceBranchWiseEnquiry = renderBackButtonForRemittanceBranchWiseEnquiry;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public int getAdjustmentAmount() {
		return adjustmentAmount;
	}
	public void setAdjustmentAmount(int adjustmentAmount) {
		this.adjustmentAmount = adjustmentAmount;
	}
	public int getNotesQuantity() {
		return notesQuantity;
	}
	public void setNotesQuantity(int notesQuantity) {
		this.notesQuantity = notesQuantity;
	}
	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	public BigDecimal getDenaminationAmount() {
		return denaminationAmount;
	}
	public void setDenaminationAmount(BigDecimal denaminationAmount) {
		this.denaminationAmount = denaminationAmount;
	}
	public String getProgNumber() {
		return progNumber;
	}
	public void setProgNumber(String progNumber) {
		this.progNumber = progNumber;
	}
	public String getDocumentStatus() {
		return documentStatus;
	}
	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}

	  public Boolean getRenderPanel() {
		    return renderPanel;
	  }

	  public void setRenderPanel(Boolean renderPanel) {
		    this.renderPanel = renderPanel;
	  }


	public Boolean getRenderForRemittanceBranchWiseEnquiry() {
		return renderForRemittanceBranchWiseEnquiry;
	}
	public void setRenderForRemittanceBranchWiseEnquiry(
			Boolean renderForRemittanceBranchWiseEnquiry) {
		this.renderForRemittanceBranchWiseEnquiry = renderForRemittanceBranchWiseEnquiry;
	}


	private List<UserFinancialYear> userFinancialYearList= new ArrayList<UserFinancialYear>();
	private List<ForeignCurrencySaleDatatable> fcsaleList=new ArrayList<ForeignCurrencySaleDatatable>();
	private SessionStateManage session = new SessionStateManage();
	
	@Autowired
	IForeignCurrencyPurchaseService<T> iforeigncurrencyservice;
	@Autowired
	ISpecialCustomerDealRequestService<T> specialCustomerDealRequestService;
	@Autowired
	IPurposeOfTransctionService purposeOfTransctionService;
	@Autowired
	ISourceOfIncomeService isourceOfIncomeService;
	
	
	public List<UserFinancialYear> getUserFinancialYearList() {
		userFinancialYearList=iforeigncurrencyservice.getAllDocumentYear();
		return userFinancialYearList;
	}
	public void setUserFinancialYearList(
			List<UserFinancialYear> userFinancialYearList) {
		this.userFinancialYearList = userFinancialYearList;
	}
	public BigDecimal getDocumentFinancialYear() {
		return documentFinancialYear;
	}
	public void setDocumentFinancialYear(BigDecimal documentFinancialYear) {
		this.documentFinancialYear = documentFinancialYear;
	}
	public BigDecimal getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getTelephoneNo() {
		return telephoneNo;
	}
	public void setTelephoneNo(String telephoneNo) {
		this.telephoneNo = telephoneNo;
	}
	public String getSourceofIncome() {
		return sourceofIncome;
	}
	public void setSourceofIncome(String sourceofIncome) {
		this.sourceofIncome = sourceofIncome;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getForeignCurrency() {
		return foreignCurrency;
	}
	public void setForeignCurrency(String foreignCurrency) {
		this.foreignCurrency = foreignCurrency;
	}
	public String getLocalCurrency() {
		return localCurrency;
	}
	public void setLocalCurrency(String localCurrency) {
		this.localCurrency = localCurrency;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getPurchaseCurrency() {
		return purchaseCurrency;
	}
	public void setPurchaseCurrency(String purchaseCurrency) {
		this.purchaseCurrency = purchaseCurrency;
	}
	public String getSaleCurrency() {
		return saleCurrency;
	}
	public void setSaleCurrency(String saleCurrency) {
		this.saleCurrency = saleCurrency;
	}
	public String getCollectionMode() {
		return collectionMode;
	}
	public void setCollectionMode(String collectionMode) {
		this.collectionMode = collectionMode;
	}
	public BigDecimal getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}
	public BigDecimal getNetAmount() {
		return netAmount;
	}
	public void setNetAmount(BigDecimal netAmount) {
		this.netAmount = netAmount;
	}
	public BigDecimal getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}
	public BigDecimal getCollectionAmount() {
		return collectionAmount;
	}
	public void setCollectionAmount(BigDecimal collectionAmount) {
		this.collectionAmount = collectionAmount;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public BigDecimal getForeignTrnxAmount() {
		return foreignTrnxAmount;
	}
	public void setForeignTrnxAmount(BigDecimal foreignTrnxAmount) {
		this.foreignTrnxAmount = foreignTrnxAmount;
	}
	public BigDecimal getLocalTrnxAmount() {
		return localTrnxAmount;
	}
	public void setLocalTrnxAmount(BigDecimal localTrnxAmount) {
		this.localTrnxAmount = localTrnxAmount;
	}
	public BigDecimal getLocalNetAmount() {
		return localNetAmount;
	}
	public void setLocalNetAmount(BigDecimal localNetAmount) {
		this.localNetAmount = localNetAmount;
	}
	public BigDecimal getAverageRate() {
		return averageRate;
	}
	public void setAverageRate(BigDecimal averageRate) {
		this.averageRate = averageRate;
	}
	public Date getAccountmmyy() {
		return accountmmyy;
	}
	public void setAccountmmyy(Date accountmmyy) {
		this.accountmmyy = accountmmyy;
	}
		
	
	public List<ForeignCurrencySaleDatatable> getFcsaleList() {
		return fcsaleList;
	}
	public void setFcsaleList(List<ForeignCurrencySaleDatatable> fcsaleList) {
		this.fcsaleList = fcsaleList;
	}
	public BigDecimal getCustomerReference() {
		return customerReference;
	}
	public void setCustomerReference(BigDecimal customerReference) {
		this.customerReference = customerReference;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public BigDecimal getNotesQty() {
		return notesQty;
	}
	public void setNotesQty(BigDecimal notesQty) {
		this.notesQty = notesQty;
	}
	public String getProgno() {
		return progno;
	}
	public void setProgno(String progno) {
		this.progno = progno;
	}
	//click on enquiry button this method called
	public void fcsaleEnquiry(){
	try{
	fcsaleList.clear();
	setRenderPanel(false);
	log.info("=========================enquiry method called=============");
		if(getDocumentNo()!=null&&getDocumentFinancialYear()!=null){
			log.info("documentNO==="+getDocumentNo());
			log.info("documentYEAR==="+getDocumentFinancialYear());
			  
			//List<ReceiptPaymentApp>  receiptDetailList=iforeigncurrencyservice.getReceipetDataApp( getDocumentFinancialYear(), getDocumentNo());
			//List<ForeignCurrencyAdjustApp>	currencyAdjust=iforeigncurrencyservice.getCurrencyAdjustRecords( getDocumentFinancialYear(), getDocumentNo());
			List<ReceiptPaymentView>  receiptDetailList=	iforeigncurrencyservice.getReceiptPaymentView(getDocumentNo(),  getDocumentFinancialYear(),  session.getCompanyId(), new BigDecimal( Constants.DOCUMENT_CODE_FOR_FCSALE));
			List<CurrencyAdjustView>	currencyAdjust=	iforeigncurrencyservice.getCurrencyAdjustViewRecords(getDocumentNo(),  getDocumentFinancialYear(), session.getCompanyId(), new BigDecimal( Constants.DOCUMENT_CODE_FOR_FCSALE));
			log.info(" CurrencyAdjust Size==="+currencyAdjust.size());
			log.info("Receipt Size==="+receiptDetailList.size());				
				if(receiptDetailList.size()>0&&currencyAdjust.size()>0){
				setRenderPanel(true);
				log.info(" CurrencyAdjust Size==="+currencyAdjust.size());
				log.info("Receipt Size==="+receiptDetailList.size());
				ForeignCurrencySaleDatatable fcsaleObj=null;
				for(CurrencyAdjustView currencyAdjObj:currencyAdjust){
					
						
						fcsaleObj=new ForeignCurrencySaleDatatable();
						
						fcsaleObj.setAccountMMYY(currencyAdjObj.getAccountmmyyyy() );
						//if(currencyAdjObj.get!=null){
						
						//}
						fcsaleObj.setAdjustmentAmount(  currencyAdjObj.getAdjustmentAmount() );
						fcsaleObj.setDenaminationAmount( currencyAdjObj.getDenaminationAmount());
						fcsaleObj.setExchangeRate( currencyAdjObj.getExchangeRate());
						 if(currencyAdjObj.getNotesQuantity()!=null){
						fcsaleObj.setNotesQty( currencyAdjObj.getNotesQuantity().intValue());
						 }
						if(currencyAdjObj.getTransactionType().equalsIgnoreCase("C")){
						fcsaleObj.setTransactionType(Constants.CASH);
						}
						else if(currencyAdjObj.getTransactionType().equalsIgnoreCase( "S")){
							fcsaleObj.setTransactionType(Constants.SALE );
 						}else{
 							fcsaleObj.setTransactionType(Constants.PURCHASE );
 						}
					 
						
						fcsaleObj.setProgNo( currencyAdjObj.getProgNumber());
						fcsaleObj.setAccountMMYY( currencyAdjObj.getAccountmmyyyy());
					
						fcsaleObj.setDocumentDate(currencyAdjObj.getDocumentDate() );
						fcsaleObj.setDocumentLineNo(  currencyAdjObj.getDocumentLineNumber());
						
						fcsaleList.add(fcsaleObj );
					}
										 
						for(ReceiptPaymentView receiptPaymentObj: receiptDetailList) {
							setCustomerId(receiptPaymentObj.getFsCustomer() );
							setCustomerReference(iDealTrackerInfoService.getCustomerRefBasedOnCustomerId(receiptPaymentObj.getFsCustomer() ));
							List<Customer> customer=iforeigncurrencyservice.getCustomerAllDetails(receiptPaymentObj.getFsCustomer());
							Customer customerObj=customer.get(0);
							setTelephoneNo(customerObj.getMobile());
							setCustomerName( receiptPaymentObj.getCustomerName() );
							//setLocation(session.getLocation());
							String location = iforeigncurrencyservice.getBranchName(receiptPaymentObj.getCountryBranch());
							if(location!=null){
								setLocation(location);
							}
							fcsaleObj.setRemarks(receiptPaymentObj.getRemarks());
							fcsaleObj.setSourceOfIncome(isourceOfIncomeService.getSourceOfIncome(   receiptPaymentObj.getSourceOfIncome() ));
							String saleCurrency = specialCustomerDealRequestService.getCurrencyForUpdate(receiptPaymentObj.getForeignFsCountryMaster());
							if(saleCurrency != null){
								setSaleCurrency(saleCurrency);
								setForeignCurrency(saleCurrency);
							}
							String purchaseCurrency = specialCustomerDealRequestService.getCurrencyForUpdate(receiptPaymentObj.getLocalFsCountryMaster());
							if(purchaseCurrency != null){
								setPurchaseCurrency(purchaseCurrency);
								setLocalCurrency(purchaseCurrency);
							}
							setRemarks(receiptPaymentObj.getRemarks());
							setLocalNetAmount( receiptPaymentObj.getLocalNetAmount());
							setLocalTrnxAmount( receiptPaymentObj.getLocalTrnxAmount());
							setForeignTrnxAmount(receiptPaymentObj.getForignTrnxAmount());
							setAverageRate(receiptPaymentObj.getTransactionActualRate() );
							/*setSourceofIncome(receiptPaymentObj.getSourceOfIncome().getSourceFullDesc());*/
							setCreatedBy(receiptPaymentObj.getCreatedBy());
							
							List<PurposeOfTransaction> PurposeName = purposeOfTransctionService.toPurposeNamebyId(receiptPaymentObj.getPurposeOfTransaction());
							if(PurposeName.size() != 0){
								setPurpose(PurposeName.get(0).getPurposeFullDesc());
								fcsaleObj.setPurpose(PurposeName.get(0).getPurposeFullDesc());
							}
							if(receiptPaymentObj.getSourceofIncomeId()!=null){
								setSourceofIncome( isourceOfIncomeService.getSourceOfIncome( receiptPaymentObj.getSourceofIncomeId()));
							}
							
							setAccountmmyy(receiptPaymentObj.getDocumentDate());
							if(receiptPaymentObj.getTransactionType().equalsIgnoreCase("S")){
							setTransactionType(Constants.SALE);
					
							}else if(receiptPaymentObj.getTransactionType().equalsIgnoreCase("P")){
								setTransactionType(Constants.PURCHASE);
							}
						
						
						
											 
					}
					
					
					
					
					
				}
			else{
			clearAll();
			setRenderPanel(false);
			RequestContext.getCurrentInstance().execute("nodatafound.show();");
			}
		
	}
	}catch(NullPointerException  e){ 
		
		  System.out.println("nullpointer");
		 	setBooError(true);
			setErrorMsg("Method:fcsaleEnquiry ");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
			return;
		}
	catch (Exception e) {
		e.printStackTrace();
		 	System.out.println("error occured"+e.getMessage());
		 	setBooError(true);
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
			return;
		}	 
	
}
	
public void clearAll(){
		 
		setBranchName(null);
		setCustomerId(null);
		setDocumentNo(null);
		setLocalCurrency(null);
		setForeignCurrency(null);
		setForeignTrnxAmount(null);
		setLocalTrnxAmount(null);
		setLocation(null);
		setLocalNetAmount(null);
		setPurpose(null);
		setSourceofIncome(null);
		setNetAmount(null);
		setPaidAmount(null);
		setRefundAmount(null);
		setRemarks(null);
		setTelephoneNo(null);
		setPurchaseCurrency(null);
		setSaleCurrency(null);
		setCustomerName( null);
		setCustomerReference( null);
		 
		setAverageRate(null);
		setAccountmmyy(null);
		setTransactionType(null);
		fcsaleList.clear();
		setRenderPanel(false);
		setBooError(false);
		setErrorMsg( null);
		setCreatedBy(null);
	}

public void exit() throws IOException {
	  setRenderPanel(false);
	if (session.getRoleId().equalsIgnoreCase("1")) {
		FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
	} else {
		FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
	}
}
@Autowired
LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
public void fcsaleEnquiryPageNavigation(){
	clearAll();
	setDocumentFinancialYear(null);
	setRenderPanel(false);
	setRenderBackButtonForRemittance( false);
    setRenderForRemittanceBranchWiseEnquiry(true);
    setRenderBackButtonForRemittanceBranchWiseEnquiry(false);
	try {
		loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "foreigncurrencysaleenquiry.xhtml");
		FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/foreigncurrencysaleenquiry.xhtml");
	} catch (Exception e) {
		log.error("Problem Occured in relationsPageNavigation() method");
	}
}
public void fcsaleEnquiryPageNavigationredirect(){
	clearAll();
	setDocumentFinancialYear(null);
	setRenderPanel(true);
	setRenderBackButtonForRemittance( true);
    setRenderForRemittanceBranchWiseEnquiry(true);
    setRenderBackButtonForRemittanceBranchWiseEnquiry(false);
	try {
		FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/foreigncurrencysaleenquiry.xhtml");
	} catch (Exception e) {
		log.error("Problem Occured in relationsPageNavigation() method");
	}
}
public Boolean getBooError() {
	return booError;
}
public void setBooError(Boolean booError) {
	this.booError = booError;
}



	
	
public void goBackToRemittanceBranchWiseEnquiryScreen(){
		try {
		clearAll();
		setDocumentFinancialYear(null);
	    setDocumentNo(null);
	    setRenderPanel(false);
	    setRenderForRemittanceBranchWiseEnquiry(true);
	    setRenderBackButtonForRemittanceBranchWiseEnquiry(false);
		FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/remittanceBranchWiseEnquiry.xhtml");
		}catch(Exception exception){
			log.error("Exception "+exception);
		    }
	}
public Boolean getRenderBackButtonForRemittance() {
	return renderBackButtonForRemittance;
}
public void setRenderBackButtonForRemittance(
		Boolean renderBackButtonForRemittance) {
	this.renderBackButtonForRemittance = renderBackButtonForRemittance;
}
public String getCreatedBy() {
	return createdBy;
}
public void setCreatedBy(String createdBy) {
	this.createdBy = createdBy;
}
public Boolean getRenderBackButtonForReceiptEnquiry() {
	return renderBackButtonForReceiptEnquiry;
}
public void setRenderBackButtonForReceiptEnquiry(Boolean renderBackButtonForReceiptEnquiry) {
	this.renderBackButtonForReceiptEnquiry = renderBackButtonForReceiptEnquiry;
}
	
	
	

}
