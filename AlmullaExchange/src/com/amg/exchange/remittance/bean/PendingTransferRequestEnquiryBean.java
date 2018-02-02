package com.amg.exchange.remittance.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.remittance.model.PendingTransferRequestEnquiryView;
import com.amg.exchange.remittance.service.IPersonalRemittanceService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("pendingTransferReqEnqBean")
@Scope("session")
public class PendingTransferRequestEnquiryBean<T> implements Serializable{

	private static final long serialVersionUID = 1L;
	private static final Logger log=Logger.getLogger(PendingTransferRequestEnquiryBean.class);
	private List<PendingTransferReqEnqDataTable> pendingTransApplicationList;
	private List<CountryBranch> branchList;
	private BigDecimal branchId;

	// Pending Transfer Request Inquiry  Detail screen properties 
	private String applicationNumber;
	private Date applicationDate;
	private String customerReference;
	private String telephone;
	private String beneficiaryName;
	private String futherInstruction;
	private String product;
	private String correspondingBank;
	private String payableAt;
	private BigDecimal saleAmount;
	private String status;
	private String transactionNo;
	private BigDecimal exchangeRate;
	private String transferDate;
	private String validUpto;
	private BigDecimal purchageAmount;
	private String transactionStatus;
	private String date;
	private BigDecimal commision;
	private String courier;
	private BigDecimal charges;
	private String deliveryDate;
	private String deliveryAmount;
	private String paymentDate;
	private BigDecimal netAmount;
	private String debitDate;
	private String address;
	private String createdBy;
	private String modifiedBy;
	private String branchName;
	private Date createdDate;
	private Date modifiedDate;
	private Date maxDate = new Date();
	private Date toDate;
	private Date fromDate = new Date();
	private boolean booRenderLocation = false;
	private String styleColor = null;
	
	 //Added by Rabil on 06 Dec 2016
	  private String appStatus;
	  private String paymnetId;
	  private String resultCode;
	  private String loyaltyInd;
	  private BigDecimal loyaltEncash;

	private SessionStateManage session = new SessionStateManage();

	@Autowired
	IGeneralService generalService;
	@Autowired
	IPersonalRemittanceService iPersonalRemittanceService;


	public boolean isBooRenderLocation() {
		return booRenderLocation;
	}
	public void setBooRenderLocation(boolean booRenderLocation) {
		this.booRenderLocation = booRenderLocation;
	}
	
	public Date getMaxDate() {
		return maxDate;
	}
	public Date getToDate() {
		return toDate;
	}

	public Date getFromDate() {
		return fromDate;
	}
	public void setMaxDate(Date maxDate) {
		this.maxDate = maxDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public String getApplicationNumber() {
		return applicationNumber;
	}
	public void setApplicationNumber(String applicationNumber) {
		this.applicationNumber = applicationNumber;
	}

	public Date getApplicationDate() {
		return applicationDate;
	}
	
	public String getCustomerReference() {
		return customerReference;
	}

	public String getTelephone() {
		return telephone;
	}

	public String getBeneficiaryName() {
		return beneficiaryName;
	}

	public String getFutherInstruction() {
		return futherInstruction;
	}

	public String getProduct() {
		return product;
	}

	public String getCorrespondingBank() {
		return correspondingBank;
	}

	public String getPayableAt() {
		return payableAt;
	}

	public BigDecimal getSaleAmount() {
		return saleAmount;
	}

	public String getStatus() {
		return status;
	}

	public String getTransactionNo() {
		return transactionNo;
	}

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public String getTransferDate() {
		return transferDate;
	}

	public String getValidUpto() {
		return validUpto;
	}

	public BigDecimal getPurchageAmount() {
		return purchageAmount;
	}

	public String getTransactionStatus() {
		return transactionStatus;
	}

	public String getDate() {
		return date;
	}

	public BigDecimal getCommision() {
		return commision;
	}

	public String getCourier() {
		return courier;
	}

	public BigDecimal getCharges() {
		return charges;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public String getDeliveryAmount() {
		return deliveryAmount;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public BigDecimal getNetAmount() {
		return netAmount;
	}

	public String getDebitDate() {
		return debitDate;
	}

	public String getAddress() {
		return address;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public String getBranchName() {
		return branchName;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}


	public void setApplicationDate(Date applicationDate) {
		this.applicationDate = applicationDate;
	}

	public void setCustomerReference(String customerReference) {
		this.customerReference = customerReference;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	public void setFutherInstruction(String futherInstruction) {
		this.futherInstruction = futherInstruction;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public void setCorrespondingBank(String correspondingBank) {
		this.correspondingBank = correspondingBank;
	}

	public void setPayableAt(String payableAt) {
		this.payableAt = payableAt;
	}

	public void setSaleAmount(BigDecimal saleAmount) {
		this.saleAmount = saleAmount;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public void setTransferDate(String transferDate) {
		this.transferDate = transferDate;
	}

	public void setValidUpto(String validUpto) {
		this.validUpto = validUpto;
	}

	public void setPurchageAmount(BigDecimal purchageAmount) {
		this.purchageAmount = purchageAmount;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setCommision(BigDecimal commision) {
		this.commision = commision;
	}

	public void setCourier(String courier) {
		this.courier = courier;
	}

	public void setCharges(BigDecimal charges) {
		this.charges = charges;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public void setDeliveryAmount(String deliveryAmount) {
		this.deliveryAmount = deliveryAmount;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public void setNetAmount(BigDecimal netAmount) {
		this.netAmount = netAmount;
	}

	public void setDebitDate(String debitDate) {
		this.debitDate = debitDate;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public List<PendingTransferReqEnqDataTable> getPendingTransApplicationList() {
		return pendingTransApplicationList;
	}

	public void setPendingTransApplicationList(List<PendingTransferReqEnqDataTable> pendingTransApplicationList) {
		this.pendingTransApplicationList = pendingTransApplicationList;
	}

	public BigDecimal getBranchId() {
		return branchId;
	}

	public void setBranchId(BigDecimal branchId) {
		this.branchId = branchId;
	}

	public List<CountryBranch> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<CountryBranch> branchList) {
		this.branchList = branchList;
	}

	public void onFromDateSelect(SelectEvent event) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	}
	
	// to check null
	private String nullCheck(String beneDt) {
		return beneDt == null ? "" : beneDt;
	}


	public void displayAllPendingTransaction(){
		try{
			setPendingTransApplicationList(null);
			BigDecimal custRef = null;
			System.out.println("getC :"+getCustomerReference()+"\t customerReference :"+customerReference);
			if(getCustomerReference()!= null && !getCustomerReference().equals("")){
				custRef = new BigDecimal(getCustomerReference());
			}
			List<PendingTransferRequestEnquiryView> pendingAppList = iPersonalRemittanceService.getAllRecordsFromApplicationDetailView(getBranchId(),getFromDate(),getToDate(),custRef);
			if(pendingAppList.size()>0){
				NumberFormat usa = NumberFormat.getInstance(Locale.getDefault());
				List<PendingTransferReqEnqDataTable> pendingTransApplicationList = new ArrayList<PendingTransferReqEnqDataTable>();
				for(PendingTransferRequestEnquiryView shopCart :pendingAppList){

					PendingTransferReqEnqDataTable pendTraReqEnq = new PendingTransferReqEnqDataTable();

					String applicationNumber = null;
					if(shopCart.getDocumentFinaceYear()!=null && shopCart.getDocumentNo()!=null){
						applicationNumber =shopCart.getDocumentFinaceYear().toString()+" / "+shopCart.getDocumentNo().toString();
					}else if(shopCart.getDocumentNo()!=null){
						applicationNumber = shopCart.getDocumentNo().toString();
					}
					pendTraReqEnq.setApplicationNumber(applicationNumber);

					pendTraReqEnq.setCustomerRef(shopCart.getCustomerRef());
					pendTraReqEnq.setCurrencyName(shopCart.getSaleCurrencyCode());
					pendTraReqEnq.setForeignCurrencyAmount(shopCart.getSaleAmount());
					if (shopCart.getSaleAmount() != null) {
						pendTraReqEnq.setFcCurrencyAmountDisplay(usa.format(shopCart.getSaleAmount()));
					}
					pendTraReqEnq.setExchangeRate(shopCart.getExchangeRate());
					pendTraReqEnq.setLocalAmount(shopCart.getPurchageAmount());
					//	pendTraReqEnq.setCountryName(shopCart);
					pendTraReqEnq.setBankName(shopCart.getBeneficiaryBank());
					pendTraReqEnq.setRemittanceMode(shopCart.getRemittanceDescription());
					pendTraReqEnq.setDeliveryMode(shopCart.getDeliveryDescription());

					if(shopCart.getCustomerName()!=null && shopCart.getCustomerRef()!=null){
						pendTraReqEnq.setCustomerReference(shopCart.getCustomerRef().toString()+" - "+shopCart.getCustomerName());
					}else if(shopCart.getCustomerName()!=null){
						pendTraReqEnq.setCustomerReference(shopCart.getCustomerName());	
					}else if(shopCart.getCustomerRef()!=null){
						pendTraReqEnq.setCustomerReference(shopCart.getCustomerRef().toString());
					}


					pendTraReqEnq.setApplicationDate(shopCart.getDocumentDate());
					pendTraReqEnq.setTelephone(shopCart.getCustomerTelephone());
					pendTraReqEnq.setBeneficiaryName(nullCheck(shopCart.getBeneficiaryName()).concat(" ").concat(nullCheck(shopCart.getBeneficiaryBank())).concat(" ").concat(nullCheck(shopCart.getBeneficiaryBranch())).concat(" ").concat(nullCheck(shopCart.getBeneficiaryAccountNo())));

					if(shopCart.getDeliveryDescription()!=null && shopCart.getRemittanceDescription()!=null){
						pendTraReqEnq.setProduct(shopCart.getDeliveryDescription()+"- "+shopCart.getRemittanceDescription());
					}else if(shopCart.getDeliveryDescription()!=null){
						pendTraReqEnq.setProduct(shopCart.getDeliveryDescription());
					}else if(shopCart.getRemittanceDescription()!=null){
						pendTraReqEnq.setProduct(shopCart.getRemittanceDescription());
					}

					pendTraReqEnq.setCorrespondingBank(shopCart.getCorrespondingBankName());
					pendTraReqEnq.setPayableAt(shopCart.getPayableAt());
					pendTraReqEnq.setSaleAmount(shopCart.getSaleAmount());
					//	pendTraReqEnq.setStatus(shopCart.getIsActive());
					//	pendTraReqEnq.setTransactionNo(shopCart.getTransactionNo());
					//	pendTraReqEnq.setTransferDate(shopCart.getTransferDate());
					//	pendTraReqEnq.setValidUpto(shopCart.getValidUpto());
					pendTraReqEnq.setPurchageAmount(shopCart.getPurchageAmount());
					//	pendTraReqEnq.setTransactionStatus(shopCart.getTransactionStatus());
					//	pendTraReqEnq.setDate(shopCart.getDate());
					pendTraReqEnq.setCommision(shopCart.getLocalCommisionAmount());
					//	pendTraReqEnq.setCourier(shopCart.getCourier());
					pendTraReqEnq.setCharges(shopCart.getLocalChargeAmount());
					//	pendTraReqEnq.setDeliveryDate(shopCart.getDeliveryDate());
					//		pendTraReqEnq.setDeliveryAmount(shopCart.getD);
					//		pendTraReqEnq.setPaymentDate(transReqEnqDataTable.getPaymentDate());
					pendTraReqEnq.setNetAmount(shopCart.getLocalNetTransactionAmount());
					//		pendTraReqEnq.setDebitDate(shopCart.getDebitDate());

					if(shopCart.getBeneficiaryCityName()!=null && shopCart.getBeneficiaryStateName()!=null && shopCart.getBeneficiaryDistrictName()!=null){
						pendTraReqEnq.setAddress(shopCart.getBeneficiaryCityName()+ ", "+shopCart.getBeneficiaryStateName()+",  "+shopCart.getBeneficiaryDistrictName());
					}else if(shopCart.getBeneficiaryCityName()!=null && shopCart.getBeneficiaryStateName()!=null){
						pendTraReqEnq.setAddress(shopCart.getBeneficiaryCityName()+ ", "+shopCart.getBeneficiaryStateName());
					}else if(shopCart.getBeneficiaryStateName()!=null && shopCart.getBeneficiaryDistrictName()!=null){
						pendTraReqEnq.setAddress(shopCart.getBeneficiaryStateName()+",  "+shopCart.getBeneficiaryDistrictName());
					}else if(shopCart.getBeneficiaryCityName()!=null && shopCart.getBeneficiaryDistrictName()!=null){
						pendTraReqEnq.setAddress(shopCart.getBeneficiaryCityName()+ ", "+shopCart.getBeneficiaryDistrictName());
					}else if(shopCart.getBeneficiaryCityName()!=null){
						pendTraReqEnq.setAddress(shopCart.getBeneficiaryCityName());
					}else if(shopCart.getBeneficiaryStateName()!=null){
						pendTraReqEnq.setAddress(shopCart.getBeneficiaryStateName());
					}else if(shopCart.getBeneficiaryDistrictName()!=null){
						pendTraReqEnq.setAddress(shopCart.getBeneficiaryDistrictName());
					}


					pendTraReqEnq.setCreatedBy(shopCart.getCreatedBy());
					pendTraReqEnq.setModifiedBy(shopCart.getModifiedBy());
					pendTraReqEnq.setBranchName(shopCart.getBeneficiaryBranch());
					pendTraReqEnq.setCreatedDate(shopCart.getCreatedDate());		
					pendTraReqEnq.setModifiedDate(shopCart.getModifiedDate());
					//Added by Rabil on 05 Dec 2016
					pendTraReqEnq.setAppStatus(shopCart.getAppStatus());
					if(shopCart.getAppStatus()!= null && shopCart.getAppStatus().equalsIgnoreCase("DELETED")){
						pendTraReqEnq.setStyleColor("red");
					}else if(shopCart.getAppStatus()!= null && shopCart.getAppStatus().equalsIgnoreCase("Acknowledged")){
						pendTraReqEnq.setStyleColor("green");
					}else if(shopCart.getAppStatus()!= null && shopCart.getAppStatus().equalsIgnoreCase("Not Acknowledged")){
						pendTraReqEnq.setStyleColor("brown");
					}else{
						pendTraReqEnq.setStyleColor("black");
					}
					
					pendTraReqEnq.setPaymnetId(shopCart.getPaymentId());
					pendTraReqEnq.setResultCode(shopCart.getResultCode()==null?"":shopCart.getResultCode());
					pendTraReqEnq.setLoyaltyInd(shopCart.getLoyaltyPoint()==null?"":shopCart.getLoyaltyPoint());
					pendTraReqEnq.setLoyaltEncash(shopCart.getLoyaltyPointEncashed()==null?new BigDecimal("0"):shopCart.getLoyaltyPointEncashed());

					pendingTransApplicationList.add(pendTraReqEnq);
				}
				setPendingTransApplicationList(pendingTransApplicationList);
			}else{
				RequestContext.getCurrentInstance().execute("norecord.show();");
				return;
			}
		}catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("MethodName::displayAllPendingTransaction");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}


	}

	public void populateBranch() {
		setBranchList(null);
		setBranchId(null);
		List<CountryBranch> listSearchBranch  = generalService.getBranchDetails(session.getCountryId());
		if(listSearchBranch.size()>0){
			setBranchList(listSearchBranch);
		}
	}

	public void clear(){
		setToDate(null);
		setFromDate(null);
		setBranchId(null);
		setCustomerReference(null);
		setPendingTransApplicationList(null);
		
		
	}
	public void clearValues(){
		setApplicationNumber(null);
		setApplicationDate(null);
		setCustomerReference(null);
		setTelephone(null);
		setBeneficiaryName(null);
		setFutherInstruction(null);
		setProduct(null);
		setCorrespondingBank(null);
		setPayableAt(null);
		setSaleAmount(null);
		setStatus(null);
		setTransactionNo(null);
		setExchangeRate(null);
		setTransferDate(null);
		setValidUpto(null);
		setPurchageAmount(null);
		setTransactionStatus(null);
		setDate(null);
		setCommision(null);
		setCourier(null);
		setCharges(null);
		setDeliveryDate(null);
		setDeliveryAmount(null);
		setPaymentDate(null);
		setNetAmount(null);
		setDebitDate(null);
		setAddress(null);
		setCreatedBy(null);
		setModifiedBy(null);
		setCreatedDate(null);		
		setModifiedDate(null);
		setBranchName(null);
	}

	public void showAllTransactionValues(PendingTransferReqEnqDataTable transReqEnqDataTable){
		setApplicationNumber(transReqEnqDataTable.getApplicationNumber());
		setApplicationDate(transReqEnqDataTable.getApplicationDate());
		setCustomerReference(transReqEnqDataTable.getCustomerReference());
		setTelephone(transReqEnqDataTable.getTelephone());
		setBeneficiaryName(transReqEnqDataTable.getBeneficiaryName());
		setFutherInstruction(transReqEnqDataTable.getFutherInstruction());
		setProduct(transReqEnqDataTable.getProduct());
		setCorrespondingBank(transReqEnqDataTable.getCorrespondingBank());
		setPayableAt(transReqEnqDataTable.getPayableAt());
		setSaleAmount(transReqEnqDataTable.getSaleAmount());
		setStatus(transReqEnqDataTable.getStatus());
		setTransactionNo(transReqEnqDataTable.getTransactionNo());
		setExchangeRate(transReqEnqDataTable.getExchangeRate());
		setTransferDate(transReqEnqDataTable.getTransferDate());
		setValidUpto(transReqEnqDataTable.getValidUpto());
		setPurchageAmount(transReqEnqDataTable.getPurchageAmount());
		setTransactionStatus(transReqEnqDataTable.getTransactionStatus());
		setDate(transReqEnqDataTable.getDate());
		setCommision(transReqEnqDataTable.getCommision());
		setCourier(transReqEnqDataTable.getCourier());
		setCharges(transReqEnqDataTable.getCharges());
		setDeliveryDate(transReqEnqDataTable.getDeliveryDate());
		setDeliveryAmount(transReqEnqDataTable.getDeliveryAmount());
		setPaymentDate(transReqEnqDataTable.getPaymentDate());
		setNetAmount(transReqEnqDataTable.getNetAmount());
		setDebitDate(transReqEnqDataTable.getDebitDate());
		setAddress(transReqEnqDataTable.getAddress());
		setCreatedBy(transReqEnqDataTable.getCreatedBy());
		setModifiedBy(transReqEnqDataTable.getModifiedBy());
		setBranchName(transReqEnqDataTable.getBranchName());
		setCreatedDate(transReqEnqDataTable.getCreatedDate());		
		setModifiedDate(transReqEnqDataTable.getModifiedDate());
		//Added by Rabil on 06  dec 2016
		setAppStatus(transReqEnqDataTable.getAppStatus());
		setPaymnetId(transReqEnqDataTable.getPaymnetId());
		setResultCode(transReqEnqDataTable.getResultCode());
		setLoyaltyInd(transReqEnqDataTable.getLoyaltyInd());
		setLoyaltEncash(transReqEnqDataTable.getLoyaltEncash());
		setStyleColor(transReqEnqDataTable.getStyleColor());
	}

	public void gotoTransferReqEnqDetailScreen(PendingTransferReqEnqDataTable transReqEnqDataTable){
		clearValues();
		showAllTransactionValues(transReqEnqDataTable);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/pendingTransferReqEnqDetails.xhtml");
		} catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}
	public void returnTopendingTransferEnqDetailScreen(){
		clearValues();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/pendingTransferRequestsEnquiry.xhtml");
		} catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void navigatingtoPendingTransferReqEnq(){
		populateBranch();
		setFromDate(null);
		setToDate(null);
		setPendingTransApplicationList(null);
		setBranchId(new BigDecimal(session.getBranchId()));
		// branch manager restricted
		String roleNameDesc=iPersonalRemittanceService.toFetchRoleName(new BigDecimal(session.getRoleId()));
		String roleName = roleNameDesc.trim();
		if (roleName.equalsIgnoreCase(Constants.USER_ROLE_BRANCH_MANAGER)) {
			setBooRenderLocation(true);
		} else {
			setBooRenderLocation(false);
		}
		
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "pendingTransferRequestsEnquiry.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/pendingTransferRequestsEnquiry.xhtml");
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}

	public void exitFromPendingTransferReqEnqScreen(){
		try {
			if (session.getRoleId().equalsIgnoreCase("1")) {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
			} else {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
			}
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}

	private String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getAppStatus() {
		return appStatus;
	}
	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus;
	}
	public String getPaymnetId() {
		return paymnetId;
	}
	public void setPaymnetId(String paymnetId) {
		this.paymnetId = paymnetId;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getLoyaltyInd() {
		return loyaltyInd;
	}
	public void setLoyaltyInd(String loyaltyInd) {
		this.loyaltyInd = loyaltyInd;
	}
	public BigDecimal getLoyaltEncash() {
		return loyaltEncash;
	}
	public void setLoyaltEncash(BigDecimal loyaltEncash) {
		this.loyaltEncash = loyaltEncash;
	}
	public String getStyleColor() {
		return styleColor;
	}
	public void setStyleColor(String styleColor) {
		this.styleColor = styleColor;
	}

}
