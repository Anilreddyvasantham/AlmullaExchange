package com.amg.exchange.stoppayment.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.foreigncurrency.model.ReceiptPayment;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.service.ICustomerRegistrationBranchService;
import com.amg.exchange.remittance.model.RemittanceApplication;
import com.amg.exchange.stoppayment.model.RemittanceComplaint;
import com.amg.exchange.stoppayment.model.RemittanceTransaction;
import com.amg.exchange.stoppayment.model.RemittanceTranxBenificiary;
import com.amg.exchange.stoppayment.service.IStopPaymentCollectionService;
import com.amg.exchange.stoppayment.service.IStopPaymentService;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.SessionStateManage;


@Component(value = "stopPayment")
@Scope("session")
public class StopPaymentBean<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(StopPaymentBean.class);

	public BigDecimal transferNo = null;
	public BigDecimal transferYear = null;
	public BigDecimal transferYearId = null;
	public BigDecimal docNo = null;
	public String date = null;
	public BigDecimal customerRef = null;
	private String customerName = null;
	private String telephone = null;
	public BigDecimal productNo = null;
	private String productName = null;
	private String accountPayee = null;
	private String benificiary = null;
	private String payableBranch = null;
	private String payableBank = null;
	private BigDecimal payableBranchId = null;
	private BigDecimal payableBankId = null;
	private double saleAmount;
	private String remarks = null;
	/*    private double exchangeRate;
    private double paidAmount;
    private double purchaseAmount;
    private double netPaidAmount;
    private double commission;
    private double refundAmount;
    private double chargesForBank;
    private double chargesForDelv;*/
	private BigDecimal exchangeRate;

	private BigDecimal paidAmount;
	private BigDecimal purchaseAmount;
	private BigDecimal netPaidAmount;
	private BigDecimal commission;
	private BigDecimal refundAmount;
	private BigDecimal chargesForBank;
	private BigDecimal chargesForDelv;
	private BigDecimal remittaceComplaintId; //REMITTANCE_COMPLAINT_ID

	private String validUntill = null;
	private String valueDate = null;
	private BigDecimal netAmount;
	private BigDecimal remittanceApplicationId = null;
	private String processIn = Constants.U;
	public String documents = null;
	private BigDecimal documentSerialityNo;
	private BigDecimal remittanceTrxId = null;
	private BigDecimal docId;



	public BigDecimal getRemittaceComplaintId() {
		return remittaceComplaintId;
	}
	public void setRemittaceComplaintId(BigDecimal remittaceComplaintId) {
		this.remittaceComplaintId = remittaceComplaintId;
	}
	public BigDecimal getDocId() {
		return docId;
	}
	public void setDocId(BigDecimal docId) {
		this.docId = docId;
	}
	public BigDecimal getTransferNo() {
		return transferNo;
	}
	public void setTransferNo(BigDecimal transferNo) {
		this.transferNo = transferNo;
	}
	
	public BigDecimal getTransferYearId() {
		return transferYearId;
	}
	public void setTransferYearId(BigDecimal transferYearId) {
		this.transferYearId = transferYearId;
	}
	
	public BigDecimal getTransferYear() {

		try{
			transferYearList = generalService.getDealYear(new Date());
			if(transferYearList!=null){
				System.out.println(transferYearList.get(0).getFinancialYear().toString());
				System.out.println(transferYearList.get(0).getFinancialYear().toString());
				System.out.println(transferYearList.get(0).getFinancialYear().toString());
				setTransferYear(transferYearList.get(0).getFinancialYear());
				setTransferYearId(transferYearList.get(0).getFinancialYearID());
			}
		}catch(Exception e){
			e.printStackTrace();	
		}
		return transferYear;
	}
	public void setTransferYear(BigDecimal transferYear) {
		this.transferYear = transferYear;
	}
	public BigDecimal getDocNo() {
		return docNo;
	}
	public void setDocNo(BigDecimal docNo) {
		this.docNo = docNo;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public BigDecimal getCustomerRef() {
		return customerRef;
	}
	public void setCustomerRef(BigDecimal customerRef) {
		this.customerRef = customerRef;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public BigDecimal getProductNo() {
		return productNo;
	}
	public void setProductNo(BigDecimal productNo) {
		this.productNo = productNo;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getAccountPayee() {
		return accountPayee;
	}
	public void setAccountPayee(String accountPayee) {
		this.accountPayee = accountPayee;
	}
	public String getBenificiary() {
		return benificiary;
	}
	public void setBenificiary(String benificiary) {
		this.benificiary = benificiary;
	}
	public String getPayableBranch() {
		return payableBranch;
	}
	public void setPayableBranch(String payableBranch) {
		this.payableBranch = payableBranch;
	}
	public String getPayableBank() {
		return payableBank;
	}
	public void setPayableBank(String payableBank) {
		this.payableBank = payableBank;
	}
	public BigDecimal getPayableBranchId() {
		return payableBranchId;
	}
	public void setPayableBranchId(BigDecimal payableBranchId) {
		this.payableBranchId = payableBranchId;
	}
	public BigDecimal getPayableBankId() {
		return payableBankId;
	}
	public void setPayableBankId(BigDecimal payableBankId) {
		this.payableBankId = payableBankId;
	}
	public double getSaleAmount() {
		return saleAmount;
	}
	public void setSaleAmount(double saleAmount) {
		this.saleAmount = saleAmount;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	public BigDecimal getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}
	public BigDecimal getPurchaseAmount() {
		return purchaseAmount;
	}
	public void setPurchaseAmount(BigDecimal purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
	}
	public BigDecimal getNetPaidAmount() {
		return netPaidAmount;
	}
	public void setNetPaidAmount(BigDecimal netPaidAmount) {
		this.netPaidAmount = netPaidAmount;
	}
	public BigDecimal getCommission() {
		return commission;
	}
	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}
	public BigDecimal getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}
	public BigDecimal getChargesForBank() {
		return chargesForBank;
	}
	public void setChargesForBank(BigDecimal chargesForBank) {
		this.chargesForBank = chargesForBank;
	}
	public BigDecimal getChargesForDelv() {
		return chargesForDelv;
	}
	public void setChargesForDelv(BigDecimal chargesForDelv) {
		this.chargesForDelv = chargesForDelv;
	}


	public String getValidUntill() {
		return validUntill;
	}
	public void setValidUntill(String validUntill) {
		this.validUntill = validUntill;
	}
	public String getValueDate() {
		return valueDate;
	}
	public void setValueDate(String valueDate) {
		this.valueDate = valueDate;
	}

	public BigDecimal getNetAmount() {
		return netAmount;
	}
	public void setNetAmount(BigDecimal netAmount) {
		this.netAmount = netAmount;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	public BigDecimal getRemittanceApplicationId() {
		return remittanceApplicationId;
	}
	public void setRemittanceApplicationId(BigDecimal remittanceApplicationId) {
		this.remittanceApplicationId = remittanceApplicationId;
	}



	public void setDocuments(String documents) {
		this.documents = documents;
	}


	public BigDecimal getDocumentSerialityNo() {
		return documentSerialityNo;
	}
	public void setDocumentSerialityNo(BigDecimal documentSerialityNo) {
		this.documentSerialityNo = documentSerialityNo;
	}



	public BigDecimal getRemittanceTrxId() {
		return remittanceTrxId;
	}
	public void setRemittanceTrxId(BigDecimal remittanceTrxId) {
		this.remittanceTrxId = remittanceTrxId;
	}



	@Autowired
	IStopPaymentService<T> stopPaymentService;

	IStopPaymentCollectionService stopPaymentCollectionService;

	@Autowired
	IGeneralService<T> generalService;

	SessionStateManage sessionManage = new SessionStateManage();
	private List<RemittanceApplication> remiittaceAppList = null;
	List<ReceiptPayment> receiptPaymentList = null;
	List<RemittanceTransaction> remiittaceTrxList = null;
	private List<Document> lstDocument = new ArrayList<Document>();
	private List<UserFinancialYear> transferYearList= new ArrayList<UserFinancialYear>();

	public String getDocuments() {

		System.out.println("Constants.DOCUMENT_CODE_FOR_STOP_PAYMENT"+Constants.DOCUMENT_CODE_FOR_STOPPAYMENT);
		System.out.println("sessionManage.getLanguageId()"+sessionManage.getLanguageId());
		lstDocument = generalService.getDocument(Constants.DOCUMENT_CODE_FOR_STOPPAYMENT,sessionManage.getLanguageId());
		for (Document lstdoc : lstDocument) {
			setDocumentSerialityNo(lstdoc.getDocumentID());
			System.out.println(lstdoc.getDocumentID());

		}
		return documents;
	}

	public String getDocumentSerialID(String processIn) {

		System.out.println("sessionManage.getCountryId().intValue()"+sessionManage.getCountryId().intValue());
		System.out.println("sessionManage.getCompanyId().intValue()"+sessionManage.getCompanyId().intValue());
		System.out.println("getDocumentSerialityNo"+getDocumentSerialityNo());
		System.out.println("transferYear"+transferYear);
		System.out.println("processIn"+processIn);
		String documentSerialID = generalService.getNextDocumentReferenceNumber(sessionManage.getCountryId().intValue(), sessionManage.getCompanyId().intValue(),getDocumentSerialityNo().intValue(), transferYear.intValue(),processIn,sessionManage.getCountryBranchCode());

		System.out.println(documentSerialID);
		System.out.println(documentSerialID);
		System.out.println(documentSerialID);


		setDocNo(new BigDecimal(documentSerialID));
		return documentSerialID;
	}


	public void clear(){

		setDocNo(null);
		setDate(null);
		setCustomerRef(null);
		setCustomerName(null);
		setTelephone(null);
		setProductNo(null);
		setProductName(null);
		setAccountPayee(null);
		setBenificiary(null);
		setPayableBank(null);
		setPayableBankId(null);
		setPayableBranch(null);
		setPayableBranchId(null);
		setSaleAmount(0.0);
		setRemarks(null);
		setCommission(null);
		setNetAmount(null);
		setNetPaidAmount(null);
		setChargesForBank(null);
		setChargesForDelv(null);
		setExchangeRate(null);
		setTransferNo(null);


	}

	RemittanceTransaction showDetailsBean= null;

	@Autowired
	ICustomerRegistrationBranchService<T> icustomerRegistrationService;


	public void viewDetails() {
		// clear();
		Customer customer = null;



		System.out.println("getTransferNo" + getTransferNo());
		System.out.println("sessionManage.getCountryId()" + sessionManage.getCountryId());

		// remiittaceAppList =
		// stopPaymentService.viewDetails(getTransferNo(),sessionManage.getCountryId());
		System.out.println(remiittaceAppList == null);
		showDetailsBean = stopPaymentService.viewTransactiondetailsbyDocumentNo(getTransferNo(), sessionManage.getCountryId(),getTransferYear(),sessionManage.getCompanyId());

		System.out.println(showDetailsBean == null);

		if (null != showDetailsBean) {
			// setDate(remiittaceAppList.get(0).get);
			System.out.println("showDetailsBean.getCustomerId()" + showDetailsBean.getCustomerId());
			List<Customer> custList = icustomerRegistrationService.getCustomerInfo(showDetailsBean.getCustomerId().getCustomerId());
			if (null != custList && !custList.isEmpty()) {
				customer = icustomerRegistrationService.getCustomerInfo(showDetailsBean.getCustomerId().getCustomerId()).get(0);
			}

			if (null != customer) {
				setCustomerRef(customer.getCustomerId());
				setCustomerName(customer.getFirstName() + " " + customer.getLastName());
				setTelephone(customer.getMobile());
			}
			setDate(new SimpleDateFormat("dd/MM/yyyy").format(showDetailsBean.getCreatedDate()));
			setCommission(showDetailsBean.getLocalCommisionAmount());
			setExchangeRate(showDetailsBean.getExchangeRateApplied());
			setChargesForBank(showDetailsBean.getLocalChargeAmount());
			setChargesForDelv(showDetailsBean.getLocalDeliveryAmount());
			List<RemittanceTranxBenificiary> benificiaryList = stopPaymentService.viewDetailsTranxBeneficiary(showDetailsBean.getRemittanceTransactionId());
			if (benificiaryList.size() > 0) {

				setBenificiary(benificiaryList.get(0).getBeneficiaryFirstName() + " " + benificiaryList.get(0).getBeneficiarySecondName() + " " + "A/C No:"
						+ benificiaryList.get(0).getBeneficiaryAccountNo() + ", " + benificiaryList.get(0).getBeneficiaryBank() + " " + benificiaryList.get(0).getBeneficiaryBranch());
				setPayableBranch(benificiaryList.get(0).getBeneficiaryBranch());
				setPayableBank(benificiaryList.get(0).getBeneficiaryBank());
			}

			receiptPaymentList = stopPaymentService.viewDetailsPayment(getTransferNo());
			if (receiptPaymentList.size() > 0) {
				setNetAmount(receiptPaymentList.get(0).getLocalNetAmount());
			}
			setRemittanceTrxId(showDetailsBean.getRemittanceTransactionId());

			RemittanceComplaint exist= stopPaymentService.viewRemittanceComplaintbyDocumentNo(getTransferNo(),getTransferYearId());
			if (exist != null) {
				setRemittaceComplaintId(exist.getRemittanceComplaintId());
				setRemarks(exist.getProblemStatus());
				setDocNo(exist.getDocumentId());
			}
			else
			{
				//document seriality done here
				getDocuments();
				getDocumentSerialID(processIn);
			}


		} else {
			RequestContext.getCurrentInstance().execute("detailsnotexists.show();");
		}
	}

	@SuppressWarnings("unchecked")
	public void print(){

		RemittanceComplaint exist= stopPaymentService.viewRemittanceComplaintbyDocumentNo(getTransferNo(),getTransferYearId());
		System.out.println("Entering into print method");
		RemittanceComplaint remitComplaint = new RemittanceComplaint();
		if (exist != null) {
			remitComplaint.setRemittanceComplaintId(exist.getRemittanceComplaintId());
		}

		remitComplaint.setRemittanceComplaintId(getRemittaceComplaintId());
		remitComplaint.setStopDocumentDate(new Date());
		remitComplaint.setCompanyId(sessionManage.getCompanyId());
		// remitComplaint.setCompanyCode(sessionManage.getcom);
		remitComplaint.setApplicationCountryId(sessionManage.getCountryId());
		remitComplaint.setStopDocumentCode(Constants.DOCUMENT_CODE_FOR_STOPPAYMENT);
		remitComplaint.setDocumentNo(getTransferNo());
		remitComplaint.setDocumentId(getDocNo());
		remitComplaint.setDocumentFinanceYear(getTransferYear());
		remitComplaint.setProblemStatus(getRemarks());
		remitComplaint.setCreatedBy(sessionManage.getUserName());
		remitComplaint.setCreatedDate(new Date());
		
		remitComplaint.setRemittanceTransactionId(getRemittanceTrxId());
		
		stopPaymentService.saveOrUpdate((T) remitComplaint);
		RequestContext.getCurrentInstance().execute("save.show();");
		/*	RemittanceTransaction remiTrx = new RemittanceTransaction();*/
		//	remiTrx.setRemittanceTransactionId(getRemittanceTrxId());
		//	stopPaymentService.saveOrUpdate(getRemittanceTrxId(), "S", sessionManage.getUserName());

		/*	else
		{
			RequestContext.getCurrentInstance().execute("detailsexists.show();");
		}*/

		System.out.println("Exit into print method");
	}

	public void PageNavigation(){
		clear();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../stoppayment/stoppaymentrequest.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clickOnExit() throws IOException {
		if (sessionManage.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}
	}

	public void clickOnPrint(){

		//print is pending here
		clear();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../stoppayment/stoppaymentrequest.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
