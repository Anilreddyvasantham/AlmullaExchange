package com.amg.exchange.foreigncurrency.bean;

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

import com.amg.exchange.common.service.ISourceOfIncomeService;
import com.amg.exchange.foreigncurrency.model.CollectDetail;
import com.amg.exchange.foreigncurrency.model.ReceiptPayment;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.foreigncurrency.service.IForeignCurrencyPurchaseService;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("foreignCurrencyPurchaseBeanEnquiry")
@Scope("session")
public class ForeignCurrencyPurchaseBeanEnquiry<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER=Logger.getLogger(ForeignCurrencyPurchaseBeanEnquiry.class);

	private BigDecimal documentFinanceYear;
	private BigDecimal documentNo;
	private BigDecimal customerReferenceNumber;
	private String customerName;
	private BigDecimal telephoneNumber;
	private String location;
	private BigDecimal customerId;
	//Collection Mode
	private String purchaseCurrency;
	private String saleCurrency;
	private BigDecimal paidAmount;
	private BigDecimal netAmount;
	private BigDecimal refundAmount;
	private String collectionMode;
	private BigDecimal collectionAmount;
	private String branchName;
	//Receipt Payment
	private String sourceName;
	private String purposeName;
	private String transactionType;
	private String currencyName;
	private BigDecimal fcTransactionAmount;
	private String localCurrencyName;
	private BigDecimal localTransactionAmount;
	private String remarks;
	private BigDecimal localNetAmount;
	private Date date;
	private BigDecimal avgRate;
	private Boolean booRenderRemittance=true;
	private Boolean renderBackButtonForRemittanceBranchWiseEnquiry=false;

	private String createdBy;
	private Date createdDate;

	private List<UserFinancialYear> userFinancialYearList= new ArrayList<UserFinancialYear>();
	private List<Customer> customerList=new ArrayList<Customer>();

	SessionStateManage sessionstate = new SessionStateManage();

	@Autowired
	IForeignCurrencyPurchaseService<T> foreignCurrencyPurchaseService;

	@Autowired
	ISourceOfIncomeService isourceOfIncomeService;


	public BigDecimal getDocumentFinanceYear() {
		return documentFinanceYear;
	}
	public void setDocumentFinanceYear(BigDecimal documentFinanceYear) {
		this.documentFinanceYear = documentFinanceYear;
	}

	public BigDecimal getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}

	public BigDecimal getCustomerReferenceNumber() {
		return customerReferenceNumber;
	}
	public void setCustomerReferenceNumber(BigDecimal customerReferenceNumber) {
		this.customerReferenceNumber = customerReferenceNumber;
	}

	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public BigDecimal getTelephoneNumber() {
		return telephoneNumber;
	}
	public void setTelephoneNumber(BigDecimal telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}

	public List<UserFinancialYear> getUserFinancialYearList() {
		userFinancialYearList=foreignCurrencyPurchaseService.getAllDocumentYear();
		return userFinancialYearList;
	}
	public void setUserFinancialYearList(
			List<UserFinancialYear> userFinancialYearList) {
		this.userFinancialYearList = userFinancialYearList;
	}

	public List<Customer> getCustomerList() {
		return customerList;
	}
	public void setCustomerList(List<Customer> customerList) {
		this.customerList = customerList;
	}

	public BigDecimal getCustomerId() {
		return customerId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public BigDecimal getFcTransactionAmount() {
		return fcTransactionAmount;
	}
	public void setFcTransactionAmount(BigDecimal fcTransactionAmount) {
		this.fcTransactionAmount = fcTransactionAmount;
	}

	public String getLocalCurrencyName() {
		return localCurrencyName;
	}
	public void setLocalCurrencyName(String localCurrencyName) {
		this.localCurrencyName = localCurrencyName;
	}

	public BigDecimal getLocalTransactionAmount() {
		return localTransactionAmount;
	}
	public void setLocalTransactionAmount(BigDecimal localTransactionAmount) {
		this.localTransactionAmount = localTransactionAmount;
	}

	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public BigDecimal getLocalNetAmount() {
		return localNetAmount;
	}
	public void setLocalNetAmount(BigDecimal localNetAmount) {
		this.localNetAmount = localNetAmount;
	}

	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	public BigDecimal getAvgRate() {
		return avgRate;
	}
	public void setAvgRate(BigDecimal avgRate) {
		this.avgRate = avgRate;
	}

	public String getSourceName() {
		return sourceName;
	}
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getPurposeName() {
		return purposeName;
	}
	public void setPurposeName(String purposeName) {
		this.purposeName = purposeName;
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

	public String getCollectionMode() {
		return collectionMode;
	}
	public void setCollectionMode(String collectionMode) {
		this.collectionMode = collectionMode;
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

	// to check null
	private String nullCheck(String custname) {
		return custname == null ? "" : custname;
	}

	public void enquiry(){
		LOGGER.info("Entering into enquiry method");
		try{
			List<CollectDetail> CollectList=foreignCurrencyPurchaseService.getCustomerEnquiry(getDocumentFinanceYear(),getDocumentNo());
			List<ReceiptPayment> receiptPaymentList=foreignCurrencyPurchaseService.getReceipetData(getDocumentFinanceYear(),getDocumentNo());
			if(CollectList.size()>0 && receiptPaymentList.size()>0){
				for (CollectDetail collect : CollectList) {
					//Customer
					setCustomerReferenceNumber(collect.getFsCustomer().getCustomerReference());

					//setCustomerName(collect.getFsCustomer().getFirstName()+" "+collect.getFsCustomer().getLastName());
					setCustomerName(nullCheck(collect.getFsCustomer().getFirstName()) + " " + nullCheck(collect.getFsCustomer().getMiddleName()) + " " + nullCheck(collect.getFsCustomer().getLastName()));

					if(collect.getFsCustomer().getMobile() != null && !collect.getFsCustomer().getMobile().trim().equalsIgnoreCase("")){
						setTelephoneNumber(new BigDecimal(collect.getFsCustomer().getMobile()));	
					}

					//Collection Details
					setPaidAmount(collect.getCashCollectionId().getPaidAmount());
					setNetAmount(collect.getCashCollectionId().getNetAmount());
					setRefundAmount(collect.getCashCollectionId().getRefoundAmount());
					//setCollectionMode(collect.getCollectionMode());
					if(collect.getCollectionMode().equalsIgnoreCase("C")){
						setCollectionMode("CASH");
					}else if (collect.getCollectionMode().equalsIgnoreCase("K")) {
						setCollectionMode("KNET");
					}
					setCollectionAmount(collect.getCollAmt());
					setBranchName(collect.getExBankBranch().getBranchName());
					setPurchaseCurrency(collect.getExCurrencyMaster().getCurrencyName());
					
					setCreatedBy(collect.getCreatedBy());
					setCreatedDate(collect.getCreatedDate());
					
					for (ReceiptPayment receiptPayment : receiptPaymentList) {
						//ReceiptPayment
						setSaleCurrency(receiptPayment.getLocalFsCountryMaster().getCurrencyName());
						setSourceName(isourceOfIncomeService.getSourceOfIncome(receiptPayment.getSourceofIncomeId()) ); 
						setPurposeName(receiptPayment.getPurposeOfTransaction().getPurposeFullDesc());
						//setTransactionType(null);
						if(receiptPayment.getTransactionType().equalsIgnoreCase("P")){
							setTransactionType("PURCHASE");
						}
						setFcTransactionAmount(receiptPayment.getForignTrnxAmount());
						setLocalTransactionAmount(receiptPayment.getLocalTrnxAmount());
						setLocalNetAmount(receiptPayment.getLocalNetAmount());
						setDate(receiptPayment.getDocumentDate());
						setRemarks(receiptPayment.getRemarks());
						setAvgRate(receiptPayment.getTransactionActualRate());
						setCurrencyName(receiptPayment.getForeignFsCountryMaster().getCurrencyName());
						setLocalCurrencyName(receiptPayment.getLocalFsCountryMaster().getCurrencyName());
						setLocation(receiptPayment.getCountryBranch().getBranchId()+"-"+receiptPayment.getCountryBranch().getBranchName());
					}
				}
			}else{
				RequestContext.getCurrentInstance().execute("noRecord.show();");
				clear();
				return;
			}
			LOGGER.info("Exit into enquiry method");
		}catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::enquiry");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch(Exception exception){
			//log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}

	public void clearAllFields(){
		LOGGER.info("Entering into clearAllFields method");
		setDocumentFinanceYear(null);
		setDocumentNo(null);
		setCustomerReferenceNumber(null);
		setCustomerName(null);
		setTelephoneNumber(null);
		setLocation(null);
		setPurchaseCurrency(null);
		setSaleCurrency(null);
		setPaidAmount(null);
		setNetAmount(null);
		setRefundAmount(null);
		setCollectionMode(null);
		setCollectionAmount(null);
		setBranchName(null);
		setSourceName(null);
		setPurposeName(null);
		setTransactionType(null);
		setFcTransactionAmount(null);
		setLocalTransactionAmount(null);
		setCurrencyName(null);
		setLocalCurrencyName(null);
		setLocalNetAmount(null);
		setRemarks(null);
		setAvgRate(null);
		setDate(null);
		setCreatedBy(null);
		setCreatedDate(null);
		LOGGER.info("Exit into clearAllFields method");
	}

	public void clear(){
		LOGGER.info("Entering into clear method");
		setDocumentNo(null);
		setCustomerReferenceNumber(null);
		setCustomerName(null);
		setTelephoneNumber(null);
		setLocation(null);
		setPurchaseCurrency(null);
		setSaleCurrency(null);
		setPaidAmount(null);
		setNetAmount(null);
		setRefundAmount(null);
		setCollectionMode(null);
		setCollectionAmount(null);
		setBranchName(null);
		setSourceName(null);
		setPurposeName(null);
		setTransactionType(null);
		setFcTransactionAmount(null);
		setLocalTransactionAmount(null);
		setCurrencyName(null);
		setLocalCurrencyName(null);
		setLocalNetAmount(null);
		setRemarks(null);
		setAvgRate(null);
		setDate(null);
		setCreatedBy(null);
		setCreatedDate(null);
		LOGGER.info("Exit into clear method");
	}
	
	public void exit() {
		LOGGER.info("Entering into exit method");
		try {
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("../registration/employeehome.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("Exit into exit method");
	}
	
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	
	public void foreignCurrencyEnquiryNavigation() {
		setBooRenderRemittance(true);
		setRenderBackButtonForRemittanceBranchWiseEnquiry( false);
		LOGGER.info("Entering into foreignCurrencyEnquiryNavigation method");
		clearAllFields();
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionstate.getCountryId(), sessionstate.getUserType(), sessionstate.getUserName(), "ForeigncurrencyPurchaseEnquiry.xhtml");
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("../treasury/ForeigncurrencyPurchaseEnquiry.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("Exit into foreignCurrencyEnquiryNavigation method");
	}
	
	public void foreignCurrencyEnquiryNavigationReirect() {
		setBooRenderRemittance( false);
		setRenderBackButtonForRemittanceBranchWiseEnquiry( true);
		LOGGER.info("Entering into foreignCurrencyEnquiryNavigation method");
		clearAllFields();
		try {
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("../treasury/ForeigncurrencyPurchaseEnquiry.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("Exit into foreignCurrencyEnquiryNavigation method");
	}


	private String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public Boolean getBooRenderRemittance() {
		return booRenderRemittance;
	}
	public void setBooRenderRemittance(Boolean booRenderRemittance) {
		this.booRenderRemittance = booRenderRemittance;
	}
	
	public Boolean getRenderBackButtonForRemittanceBranchWiseEnquiry() {
		return renderBackButtonForRemittanceBranchWiseEnquiry;
	}
	public void setRenderBackButtonForRemittanceBranchWiseEnquiry(
			Boolean renderBackButtonForRemittanceBranchWiseEnquiry) {
		this.renderBackButtonForRemittanceBranchWiseEnquiry = renderBackButtonForRemittanceBranchWiseEnquiry;
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
	
}
