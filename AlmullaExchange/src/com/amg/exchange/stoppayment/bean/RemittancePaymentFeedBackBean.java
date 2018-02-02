package com.amg.exchange.stoppayment.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.cancelreissue.model.RemittanceTrnxViewStopMiscModel;
import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.foreigncurrency.service.ForeignLocalCurrencyDenominationService;
import com.amg.exchange.miscellaneous.service.IMiscellaneousReceiptPaymentService;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.service.ICompanyMasterservice;
import com.amg.exchange.registration.service.ICustomerRegistrationBranchService;
import com.amg.exchange.stoppayment.model.RemittanceComplaint;
import com.amg.exchange.stoppayment.model.RemittanceTranxBenificiary;
import com.amg.exchange.stoppayment.service.IStopPaymentCollectionService;
import com.amg.exchange.stoppayment.service.IStopPaymentService;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.GetRound;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

/**
 * @author Mohan
 * 
 */
@Component("remittancepaymentFeedBackBean")
@Scope("session")
public class RemittancePaymentFeedBackBean<T> implements Serializable {
	/**
	 * 
	 */
	private static final Logger LOGGER = Logger.getLogger(RemittancePaymentFeedBackBean.class);

	private static final long serialVersionUID = 1L;
	private BigDecimal remittanceTransactionId;
	private int financialYear;

	// column
	private BigDecimal dealYear;
	private BigDecimal transferYear;
	private BigDecimal transferNo;
	private Date receiptDate;
	private String ProductName;
	private BigDecimal applicationNo;// Remittance No
	private String applicationFinancialYear;// Remittance Year
	private String payableBranch;
	private BigDecimal customerReference;
	private String customerName;
	private String beneficiary;
	private String errorMessage;
	private String accPayee;
	private String correspondentBankName;
	private String correspondentBankBranchName;
	private String localCurrencyCode;
	private String localCurrencyAmount;
	private String foreignCurrencyCode;
	private String foreignCurrencyAmount;
	private BigDecimal exchangeRate;
	private BigDecimal commission;
	private BigDecimal charges;
	private BigDecimal deliveryCharges;
	private BigDecimal payNetPaidAmount;
	private String stopPaymentStatus;
	private BigDecimal companycode;
	private BigDecimal documentCode;
	private List<UserFinancialYear> trnxYearList = new ArrayList<UserFinancialYear>();
	private List<UserFinancialYear> DealYearList = new ArrayList<UserFinancialYear>();
	private BigDecimal productCode;
	private String productStatus;
	private BigDecimal customerRef;
	private BigDecimal branchCode;
	private String branchName;
	private BigDecimal customerCode;
	private String customerFirstName;
	private String telephoneNo;
	private Date transferDate;
	private String beneficiaryName;
	private BigDecimal beneficiaryAccNo;
	private BigDecimal beneficiaryBankId;
	private BigDecimal beneficiaryBankBranchId;
	private BigDecimal beneficiaryBankCountryId;
	private BigDecimal transferAmount;
	private BigDecimal payableAt;
	// private BigDecimal payableBranch;
	private BigDecimal payableBankId;
	private String payableBranchCode;
	private String refundFor;
	private BigDecimal userDealYear;
	private String dealDate;
	private BigDecimal dealYearId;
	private BigDecimal userDealYearId;
	private BigDecimal receiptNo;
	private Date paymentDate;
	private String remarks;
	private Boolean renderRefEditable = false;
	private Boolean renderRef = true;
	private BigDecimal spCharges;
	private BigDecimal paidAmount;
	private BigDecimal refundAmount;
	// for denamination details
	private Boolean booRenderFirstPanel;
	private BigDecimal forgienCurrencyId;
	private BigDecimal localCurrencyId;
	private BigDecimal localTransactionAmount;
	private BigDecimal forgienTransactionAmount;
	private BigDecimal tempCash;
	private BigDecimal bankBranchId;
	private String beneBankName;
	private String beneBranchName;
	Date paymentDateforCusto;
	Date paymentDateExch;
	Date returnDate;
	private String bankRefer;

	private Boolean booPaymentDateforCusto = false;
	private Boolean booPaymentDateExch = false;
	private Boolean booReturnDate = false;
	private Boolean booSaveBtn = false;

	private SessionStateManage session = new SessionStateManage();

	private String exceptionMessage;

	@Autowired
	ForeignLocalCurrencyDenominationService<T> foreignLocalCurrencyDenominationService;
	@Autowired
	IStopPaymentCollectionService stopPaymentCollectionService;
	@Autowired
	ICustomerRegistrationBranchService<T> icustomerRegistrationService;
	@Autowired
	IStopPaymentService<T> stopPaymentService;
	@Autowired
	ICompanyMasterservice iCompanyMasterservice;
	@Autowired
	IGeneralService<T> generalService;
	@Autowired
	IMiscellaneousReceiptPaymentService<T> iMiscellaneousReceiptPaymentService;

	Customer customer;

	public List<UserFinancialYear> getDealYearList() {
		return DealYearList;
	}

	public void setDealYearList(List<UserFinancialYear> dealYearList) {
		DealYearList = dealYearList;
	}

	public String getCorrespondentBankName() {
		return correspondentBankName;
	}

	public BigDecimal getCompanycode() {
		return companycode;
	}

	public void setCompanycode(BigDecimal companycode) {
		this.companycode = companycode;
	}

	public BigDecimal getDocumentCode() {
		return documentCode;
	}

	public void setDocumentCode(BigDecimal documentCode) {
		this.documentCode = documentCode;
	}

	public void setCorrespondentBankName(String correspondentBankName) {
		this.correspondentBankName = correspondentBankName;
	}

	public String getCorrespondentBankBranchName() {
		return correspondentBankBranchName;
	}

	public void setCorrespondentBankBranchName(String correspondentBankBranchName) {
		this.correspondentBankBranchName = correspondentBankBranchName;
	}

	public String getLocalCurrencyCode() {
		return localCurrencyCode;
	}

	public void setLocalCurrencyCode(String localCurrencyCode) {
		this.localCurrencyCode = localCurrencyCode;
	}

	public String getLocalCurrencyAmount() {
		return localCurrencyAmount;
	}

	public void setLocalCurrencyAmount(String localCurrencyAmount) {
		this.localCurrencyAmount = localCurrencyAmount;
	}

	public String getForeignCurrencyCode() {
		return foreignCurrencyCode;
	}

	public void setForeignCurrencyCode(String foreignCurrencyCode) {
		this.foreignCurrencyCode = foreignCurrencyCode;
	}

	public String getForeignCurrencyAmount() {
		return foreignCurrencyAmount;
	}

	public void setForeignCurrencyAmount(String foreignCurrencyAmount) {
		this.foreignCurrencyAmount = foreignCurrencyAmount;
	}

	public BigDecimal getCommission() {
		return commission;
	}

	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}

	public BigDecimal getCharges() {
		return charges;
	}

	public void setCharges(BigDecimal charges) {
		this.charges = charges;
	}

	public BigDecimal getDeliveryCharges() {
		return deliveryCharges;
	}

	public void setDeliveryCharges(BigDecimal deliveryCharges) {
		this.deliveryCharges = deliveryCharges;
	}

	public BigDecimal getCustomerReference() {
		return customerReference;
	}

	public void setCustomerReference(BigDecimal customerReference) {
		this.customerReference = customerReference;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public String getStopPaymentStatus() {
		return stopPaymentStatus;
	}

	public void setStopPaymentStatus(String stopPaymentStatus) {
		this.stopPaymentStatus = stopPaymentStatus;
	}

	public BigDecimal getCustomerRef() {
		return customerRef;
	}

	public void setCustomerRef(BigDecimal customerRef) {
		this.customerRef = customerRef;
	}

	public BigDecimal getBankBranchId() {
		return bankBranchId;
	}

	public void setBankBranchId(BigDecimal bankBranchId) {
		this.bankBranchId = bankBranchId;
	}

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public BigDecimal getLocalTransactionAmount() {
		return localTransactionAmount;
	}

	public void setLocalTransactionAmount(BigDecimal localTransactionAmount) {
		this.localTransactionAmount = localTransactionAmount;
	}

	public BigDecimal getForgienTransactionAmount() {
		return forgienTransactionAmount;
	}

	public void setForgienTransactionAmount(BigDecimal forgienTransactionAmount) {
		this.forgienTransactionAmount = forgienTransactionAmount;
	}

	public BigDecimal getLocalCurrencyId() {
		return localCurrencyId;
	}

	public void setLocalCurrencyId(BigDecimal localCurrencyId) {
		this.localCurrencyId = localCurrencyId;
	}

	public BigDecimal getForgienCurrencyId() {
		return forgienCurrencyId;
	}

	public void setForgienCurrencyId(BigDecimal forgienCurrencyId) {
		this.forgienCurrencyId = forgienCurrencyId;
	}

	public BigDecimal getRemittanceTransactionId() {
		return remittanceTransactionId;
	}

	public void setRemittanceTransactionId(BigDecimal remittanceTransactionId) {
		this.remittanceTransactionId = remittanceTransactionId;
	}

	public String getCustomerFirstName() {
		return customerFirstName;
	}

	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public BigDecimal getApplicationNo() {
		return applicationNo;
	}

	public void setApplicationNo(BigDecimal applicationNo) {
		this.applicationNo = applicationNo;
	}

	public Boolean getBooRenderFirstPanel() {
		return booRenderFirstPanel;
	}

	public void setBooRenderFirstPanel(Boolean booRenderFirstPanel) {
		this.booRenderFirstPanel = booRenderFirstPanel;
	}

	public BigDecimal getTempCash() {
		return tempCash;
	}

	public void setTempCash(BigDecimal tempCash) {
		this.tempCash = tempCash;
	}

	public String getBeneficiary() {
		return beneficiary;
	}

	public void setBeneficiary(String beneficiary) {
		this.beneficiary = beneficiary;
	}

	public Date getReceiptDate() {
		return receiptDate;
	}

	public void setReceiptDate(Date receiptDate) {
		this.receiptDate = receiptDate;
	}

	public BigDecimal getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(BigDecimal receiptNo) {
		this.receiptNo = receiptNo;
	}

	public BigDecimal getSpCharges() {
		return spCharges;
	}

	public void setSpCharges(BigDecimal spCharges) {
		this.spCharges = spCharges;
	}

	public BigDecimal getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}

	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	public BigDecimal getUserDealYearId() {
		return userDealYearId;
	}

	public void setUserDealYearId(BigDecimal userDealYearId) {
		this.userDealYearId = userDealYearId;
	}

	public BigDecimal getDealYearId() {
		return dealYearId;
	}

	public void setDealYearId(BigDecimal dealYearId) {
		this.dealYearId = dealYearId;
	}

	public String getDealDate() {
		return dealDate;
	}

	public void setDealDate(String dealDate) {
		this.dealDate = dealDate;
	}

	public BigDecimal getUserDealYear() {
		return userDealYear;
	}

	public void setUserDealYear(BigDecimal userDealYear) {
		this.userDealYear = userDealYear;
	}

	public Boolean getRenderRefEditable() {
		return renderRefEditable;
	}

	public void setRenderRefEditable(Boolean renderRefEditable) {
		this.renderRefEditable = renderRefEditable;
	}

	public Boolean getRenderRef() {
		return renderRef;
	}

	public void setRenderRef(Boolean renderRef) {
		this.renderRef = renderRef;
	}

	public int getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(int financialYear) {
		this.financialYear = financialYear;
	}

	/*
	 * public Long getTransferNo() { return transferNo; }
	 * 
	 * public void setTransferNo(Long transferNo) { this.transferNo =
	 * transferNo; }
	 */

	public BigDecimal getProductCode() {
		return productCode;
	}

	public BigDecimal getTransferNo() {
		return transferNo;
	}

	public void setTransferNo(BigDecimal transferNo) {
		this.transferNo = transferNo;
	}

	public void setProductCode(BigDecimal productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return ProductName;
	}

	public void setProductName(String productName) {
		ProductName = productName;
	}

	public String getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}

	public String getApplicationFinancialYear() {
		return applicationFinancialYear;
	}

	public void setApplicationFinancialYear(String applicationFinancialYear) {
		this.applicationFinancialYear = applicationFinancialYear;
	}

	public BigDecimal getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(BigDecimal branchCode) {
		this.branchCode = branchCode;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public BigDecimal getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(BigDecimal customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getTelephoneNo() {
		return telephoneNo;
	}

	public void setTelephoneNo(String telephoneNo) {
		this.telephoneNo = telephoneNo;
	}

	public Date getTransferDate() {
		return transferDate;
	}

	public void setTransferDate(Date transferDate) {
		this.transferDate = transferDate;
	}

	public String getAccPayee() {
		return accPayee;
	}

	public void setAccPayee(String accPayee) {
		this.accPayee = accPayee;
	}

	public BigDecimal getPayNetPaidAmount() {
		return payNetPaidAmount;
	}

	public void setPayNetPaidAmount(BigDecimal payNetPaidAmount) {
		this.payNetPaidAmount = payNetPaidAmount;
	}

	public String getBeneficiaryName() {
		return beneficiaryName;
	}

	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	public BigDecimal getBeneficiaryAccNo() {
		return beneficiaryAccNo;
	}

	public void setBeneficiaryAccNo(BigDecimal beneficiaryAccNo) {
		this.beneficiaryAccNo = beneficiaryAccNo;
	}

	public BigDecimal getBeneficiaryBankId() {
		return beneficiaryBankId;
	}

	public void setBeneficiaryBankId(BigDecimal beneficiaryBankId) {
		this.beneficiaryBankId = beneficiaryBankId;
	}

	public BigDecimal getBeneficiaryBankBranchId() {
		return beneficiaryBankBranchId;
	}

	public void setBeneficiaryBankBranchId(BigDecimal beneficiaryBankBranchId) {
		this.beneficiaryBankBranchId = beneficiaryBankBranchId;
	}

	public BigDecimal getBeneficiaryBankCountryId() {
		return beneficiaryBankCountryId;
	}

	public void setBeneficiaryBankCountryId(BigDecimal beneficiaryBankCountryId) {
		this.beneficiaryBankCountryId = beneficiaryBankCountryId;
	}

	public BigDecimal getTransferAmount() {
		return transferAmount;
	}

	public void setTransferAmount(BigDecimal transferAmount) {
		this.transferAmount = transferAmount;
	}

	public BigDecimal getPayableAt() {
		return payableAt;
	}

	public void setPayableAt(BigDecimal payableAt) {
		this.payableAt = payableAt;
	}

	public String getPayableBranch() {
		return payableBranch;
	}

	public void setPayableBranch(String payableBranch) {
		this.payableBranch = payableBranch;
	}

	public BigDecimal getPayableBankId() {
		return payableBankId;
	}

	public void setPayableBankId(BigDecimal payableBankId) {
		this.payableBankId = payableBankId;
	}

	public String getPayableBranchCode() {
		return payableBranchCode;
	}

	public void setPayableBranchCode(String payableBranchCode) {
		this.payableBranchCode = payableBranchCode;
	}

	public String getRefundFor() {
		return refundFor;
	}

	public void setRefundFor(String refundFor) {
		this.refundFor = refundFor;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * stopPaymentCollectionpageNavigation method calling from fx-deal
	 */
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;

	public void stopPaymentFeedbackPageNavigation() {
		LOGGER.info("Entering into Remittance Feedback method");
		fetchComplaintfinanceYear();
		clear();
		setBooRenderFirstPanel(true);
		setErrorMessage(null);
		fetchDocYear();
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(),
					"RemittancePaymentFeedbackUpdate.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/RemittancePaymentFeedbackUpdate.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("Exit into stopPaymentCollectionpageNavigation method");

	}

	public void clear() {
		setTransferNo(null);
		setProductCode(null);
		setProductName(null);
		setProductStatus(null);
		setApplicationNo(null);
		setApplicationFinancialYear(null);
		setBranchCode(null);
		setBranchName(null);
		setCustomerCode(null);
		setCustomerName(null);
		setTelephoneNo(null);
		setTransferDate(null);
		setAccPayee(null);
		setBeneficiaryName(null);
		setBeneficiaryAccNo(null);
		setBeneficiaryBankId(null);
		setBeneficiaryBankBranchId(null);
		setBeneficiaryBankCountryId(null);
		setTransferAmount(null);
		setReceiptDate(null);
		setDocumentCode(null);
		setCompanycode(null);

		setCustomerReference(null);
		setPayableBranch(null);
		setBeneficiary(null);
		setForgienTransactionAmount(null);
		setLocalTransactionAmount(null);
		setCorrespondentBankBranchName(null);
		setCorrespondentBankName(null);
		setExchangeRate(null);
		setForeignCurrencyAmount(null);
		setForeignCurrencyCode(null);
		setLocalCurrencyCode(null);
		setLocalCurrencyAmount(null);
		setStopPaymentStatus(null);
		setCharges(null);
		setDeliveryCharges(null);
		setCommission(null);
		setPayNetPaidAmount(null);

		setBankRefer(null);
		setPaymentDateforCusto(null);
		setPaymentDateExch(null);
		setReturnDate(null);
		setRemarks(null);
		setBooSaveBtn(null);
		setStopPaymentStatus(null);

	}

	public BigDecimal getDealYear() {
		return dealYear;
	}

	public void setDealYear(BigDecimal dealYear) {
		this.dealYear = dealYear;
	}

	// RemittanceTxnView beanTransaction = null;
	// ViewRemiitanceInfo beanTransaction = null;

	// to check null
	private String nullCheck(String custname) {
		return custname == null ? "" : custname;
	}

	private String customerFullName;

	public String getCustomerFullName() {
		return customerFullName;
	}

	public void setCustomerFullName(String customerFullName) {
		this.customerFullName = customerFullName;
	}

	public void fetchDocYear() {
		LOGGER.info("Entering into getDealYear method");
		try {

			DealYearList = generalService.getDealYear(new Date());
			if (DealYearList != null) {
				if (getUserDealYear() == null) {
					dealYear = DealYearList.get(0).getFinancialYear();
					dealYearId = DealYearList.get(0).getFinancialYearID();
					setDealYearId(dealYearId);
					setDealYear(dealYear);
				} else {
					setDealYear(getUserDealYear());
					setDealYearId(getUserDealYearId());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("Exit into getDealYear method");
	}

	
	public void onSelectRemitStatus() {
		System.out.println("GET Payment " + getStopPaymentStatus());

		/*
		 * try{
		 * 
		 * HashMap<String, Object> inputValues = new HashMap<String, Object>();
		 * HashMap<String, String> outputValues = new HashMap<String, String>();
		 * 
		 * if(getStopPaymentStatus()!= null){
		 * 
		 * inputValues.put("P_REMIT_COMP_ID",session.getCompanyId());
		 * inputValues.put("P_REMIT_DOC_ID",getDocumentCode());
		 * inputValues.put("P_REMIT_DOCFYR",getFinYear());
		 * inputValues.put("P_REMIT_DOCNO",getTransferNo());
		 * 
		 * outputValues =
		 * stopPaymentService.validateRemitFeedBackProcedure(inputValues);
		 * System.out.println("msg :"+outputValues.get("P_ERROR"));
		 * if(outputValues.get("P_ERROR")!=null){ setBooSaveBtn(true);
		 * setErrorMessage(outputValues.get("P_ERROR"));
		 * RequestContext.getCurrentInstance().execute("csp.show();"); return; }
		 * }
		 */

		if (getStopPaymentStatus() != null && getStopPaymentStatus().equalsIgnoreCase("P")) {
			setReturnDate(null);
			setBooReturnDate(true);
			setBooPaymentDateExch(false);
			setBooPaymentDateforCusto(false);
		} else if (getStopPaymentStatus() != null && getStopPaymentStatus().equalsIgnoreCase("R")) {
			setPaymentDateforCusto(null);
			setPaymentDateExch(null);
			setBooPaymentDateExch(true);
			setBooPaymentDateforCusto(true);
			setBooReturnDate(false);
		}
		/*
		 * }catch (AMGException e) { setErrorMessage(e.getMessage());
		 * RequestContext.getCurrentInstance().execute("csp.show();"); return; }
		 */
	}

	public void fetchDetails() {
		try {
			LOGGER.info("Entering into fetchDetails Method");
			LOGGER.info("Transfer No " + getTransferNo());
			LOGGER.info("Application no " + getTransferYear());
			LOGGER.info(dealYear);
			setRemarks(null);
			setStopPaymentStatus(null);
			setPaymentDateforCusto(null);
			setReturnDate(null);
			setBankRefer(null);

			if (getTransferNo() == null || getTransferNo().equals("")) {
			} else {

				Document document = null;
				BigDecimal documentId = null;
				document = stopPaymentService.getDocumentId(new BigDecimal(Constants.DOCUMENT_CODE_FOR_REMITTANCE_TRANSACTION));

				if (document != null) {
					documentId = document.getDocumentID();
				} else {
					documentId = null;
				}

				HashMap<String, Object> inputValues = new HashMap<String, Object>();
				HashMap<String, String> outputValues = new HashMap<String, String>();

				inputValues.put("P_REMIT_COMP_ID", session.getCompanyId());
				inputValues.put("P_REMIT_DOC_ID", documentId);
				inputValues.put("P_REMIT_DOCFYR", getTransferYear()==null?getFinYear():getTransferYear());
				inputValues.put("P_REMIT_DOCNO", getTransferNo());

				outputValues = stopPaymentService.validateRemitFeedBackProcedure(inputValues);
				System.out.println("msg :" + outputValues.get("P_ERROR"));
				if (outputValues.get("P_ERROR") != null) {
					setBooSaveBtn(true);
					setErrorMessage(outputValues.get("P_ERROR") + "\t " + getFinYear() + " Document No " + getTransferNo());
					RequestContext.getCurrentInstance().execute("csp.show();");
					clear();
					return;
				}

				// ViewRemiitanceInfo beanTransaction =
				// stopPaymentService.getStopPaymentTrnxDetailsFromView(getTransferNo(),getDealYear(),session.getCompanyId(),documentId);
				RemittanceTrnxViewStopMiscModel beanTransaction = stopPaymentService.getRemitTrnxFromView(getTransferNo(), getTransferYear(),
						session.getCompanyId(), documentId);

				// if VW_REMITTANCE_TRANSACTION list not available old trnx to
				// Migrate from Old Emos
				if (beanTransaction == null) {

					// company code
					BigDecimal companyCode = null;
					List<CompanyMasterDesc> lstcompanymaster = iCompanyMasterservice.viewById(session.getCompanyId());
					if (lstcompanymaster.size() != 0) {
						CompanyMasterDesc companycode = lstcompanymaster.get(0);
						if (companycode.getFsCompanyMaster().getCompanyCode() != null) {
							companyCode = companycode.getFsCompanyMaster().getCompanyCode();
						}
					}

					HashMap<String, Object> fetchTransferToRemits = stopPaymentService.fetchTransferForStopPayment(companyCode, new BigDecimal(
							Constants.DOCUMENT_CODE_FOR_REMITTANCE_TRANSACTION), getTransferYear(), getTransferNo(), Constants.StopPaymentForm);

					if ((String) fetchTransferToRemits.get("ERRMSG") != null && !((String) fetchTransferToRemits.get("ERRMSG")).equalsIgnoreCase("")) {
						setTransferNo(null);
						setErrorMessage((String) fetchTransferToRemits.get("ERRMSG"));
						RequestContext.getCurrentInstance().execute("csp.show();");
						clear();
						return;
					} else {
						beanTransaction = stopPaymentService.getRemitTrnxFromView(getTransferNo(), getTransferYear(), session.getCompanyId(),
								documentId);
					}
				}

				if (beanTransaction != null) {

					List<UserFinancialYear> userFinList = iMiscellaneousReceiptPaymentService.getFinanacilYearId(getTransferYear());

					BigDecimal docFinYearId = null;
					if (userFinList.size() > 0) {
						docFinYearId = userFinList.get(0).getFinancialYearID();
					}
					if (beanTransaction.getTransactionStatus() != null) {
						LOGGER.info("Transaction type is " + beanTransaction.getTransactionStatus());
						if (beanTransaction.getTransactionStatus().equalsIgnoreCase(Constants.P)) {
							LOGGER.info("refundover message");
							setErrorMessage("This Remittanc has been paid !" + beanTransaction.getDocumentFinYear() + " Document No "
									+ beanTransaction.getDocumentNo());
							fetchStopPaymentRec(beanTransaction);
							RequestContext.getCurrentInstance().execute("csp.show();");
							// clear();
							return;
						} else {
							if (beanTransaction.getProblemStatus() != null) {
								if ((beanTransaction.getProblemStatus().equalsIgnoreCase(Constants.R))) {
									setErrorMessage("This Remittanc has been returned. Document year " + beanTransaction.getDocumentFinYear()
											+ " Document No " + beanTransaction.getDocumentNo());
									RequestContext.getCurrentInstance().execute("csp.show();");
									// clear();
									return;
								} else {
									if (beanTransaction.getBankStatus() != null) {
										setErrorMessage("Remittance feedback payment has been updated!, can not be changed. Document year "
												+ beanTransaction.getDocumentFinYear() + " Document No " + beanTransaction.getDocumentNo());
										RequestContext.getCurrentInstance().execute("csp.show();");
										// clear();
										return;
									} else {
										fetchStopPaymentRec(beanTransaction);
									}
								}
							} else {

								if (beanTransaction.getBankStatus() != null) {
									setErrorMessage("Remittance feedback payment has been updated!, Can not be Changed. Document year "
											+ beanTransaction.getDocumentFinYear() + " Document No " + beanTransaction.getDocumentNo());
									RequestContext.getCurrentInstance().execute("csp.show();");
									// clear();
									return;
								} else {
									fetchStopPaymentRec(beanTransaction);
								}

							}

						}
					} else {
						if (beanTransaction.getProblemStatus() != null) {
							if (beanTransaction.getProblemStatus().equalsIgnoreCase(Constants.S)) {
								setErrorMessage("Remittance feedback update Request Not Processed. Document year "
										+ beanTransaction.getDocumentFinYear() + " Document No " + beanTransaction.getDocumentNo());
								RequestContext.getCurrentInstance().execute("csp.show();");
								clear();
								return;
							} else {
								if (beanTransaction.getBankStatus() != null) {
									setErrorMessage("Remittance feedback update has been updated!, Can not be Changed. Document year "
											+ beanTransaction.getDocumentFinYear() + " Document No " + beanTransaction.getDocumentNo());
									RequestContext.getCurrentInstance().execute("csp.show();");
									clear();
									return;
								} else {
									fetchStopPaymentRec(beanTransaction);
								}
							}
						} else {

							if (beanTransaction.getBankStatus() != null) {
								setErrorMessage("Remittance feedback update has been updated!, Can not be Changed. Document year "
										+ beanTransaction.getDocumentFinYear() + " Document No " + beanTransaction.getDocumentNo());
								RequestContext.getCurrentInstance().execute("csp.show();");
								clear();
								return;
							} else {
								fetchStopPaymentRec(beanTransaction);
							}

						}

					}
				}

				LOGGER.info("exit into fetchDetails Method");
			}

		} catch (Exception e) {
			setErrorMessage("Exception occurred " + e);
			RequestContext.getCurrentInstance().execute("csp.show();");
			return;
		}
	}

	public void fetchStopPaymentRec(RemittanceTrnxViewStopMiscModel beanTransaction) {

		LOGGER.info("Docment No " + beanTransaction.getDocumentNo());
		// LOGGER.info("" +
		// beanTransaction.getDebitAccountNo());
		LOGGER.info(beanTransaction.getCustomerId());
		customer = icustomerRegistrationService.getCustomerInfo(beanTransaction.getCustomerId()).get(0);
		setCustomerCode(beanTransaction.getCustomerId());
		if (null != customer) {
			setCustomerName(nullCheck(customer.getFirstName()) + " " + nullCheck(customer.getMiddleName()) + " " + nullCheck(customer.getLastName()));
			setCustomerFirstName(customer.getFirstName());
			setTelephoneNo(customer.getMobile());
			setCustomerFullName(nullCheck(customer.getFirstName()) + " " + nullCheck(customer.getMiddleName()) + " "
					+ nullCheck(customer.getLastName()));
			setCustomerRef(customer.getCustomerReference());
		}

		String productName = stopPaymentCollectionService.getProductName(beanTransaction.getBankId(), beanTransaction.getRemittanceModeId(),
				beanTransaction.getDeliveryModeId(), beanTransaction.getApplicationCountryId(), beanTransaction.getForeignCurrencyId());
		LOGGER.info("productName" + productName);
		setRemittanceTransactionId(beanTransaction.getRemittanceTransactionId());
		List<RemittanceTranxBenificiary> benificiaryList = stopPaymentService.viewDetailsTranxBeneficiary(beanTransaction
				.getRemittanceTransactionId());
		if (benificiaryList.size() > 0) {
			// setBeneficiary(beneficiary);
			setBeneficiary(benificiaryList.get(0).getBeneficiaryName() + " " + "A/C No:" + benificiaryList.get(0).getBeneficiaryAccountNo() + ", "
					+ benificiaryList.get(0).getBeneficiaryBank() + " " + benificiaryList.get(0).getBeneficiaryBranch());
			setPayableBranch(benificiaryList.get(0).getBeneficiaryBranch());
			// setPayableBank(benificiaryList.get(0).getBeneficiaryBank());
		}
		setBeneBankName(benificiaryList.get(0).getBeneficiaryBank());
		setBeneBranchName(benificiaryList.get(0).getBeneficiaryBranch());
		setApplicationNo(beanTransaction.getApplicationDocumentNo());
		if (beanTransaction.getApplicationFinYear() != null)
			setApplicationFinancialYear(beanTransaction.getApplicationFinYear().toPlainString());
		setCustomerReference(beanTransaction.getCustomerRefNo());
		setProductName(productName);
		// setProductStatus(getTransactionStatus(beanTransaction.getTranscationStatus()));
		// if(beanTransaction.getInstructions())
		setAccPayee(beanTransaction.getInstruction());
		setTransferAmount(beanTransaction.getLocalNetTrxAmount());
		setReceiptDate(beanTransaction.getDocumentDate());
		setPaymentDate(new Date());
		setDocumentCode(beanTransaction.getDocumentCode());

		BankBranch bankBranch = stopPaymentService.getBankBranchDetails(beanTransaction.getBankBranchId());

		if (bankBranch != null) {
			setCorrespondentBankBranchName(bankBranch.getBranchFullName());
			setCorrespondentBankName(bankBranch.getExBankMaster().getBankFullName());
		}

		setForgienCurrencyId(beanTransaction.getForeignCurrencyId());
		// setLocalCurrencyId(beanTransaction.getLocalTransactionCurrencyId());
		setLocalCurrencyCode(beanTransaction.getLocalCurrencyCode());
		setForeignCurrencyCode(beanTransaction.getForeignCurrencyCode());
		setLocalTransactionAmount(beanTransaction.getLocalAmount());
		setForgienTransactionAmount(beanTransaction.getForeignTrxAmount());
		setExchangeRate(beanTransaction.getExchangeRateApplied());
		setPayNetPaidAmount(GetRound.roundBigDecimal(beanTransaction.getLocalNetTrxAmount(),
				foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(session.getCurrencyId()))));

		setBankBranchId(beanTransaction.getBankBranchId());
		setExchangeRate(beanTransaction.getExchangeRateApplied());
		setCommission(GetRound.roundBigDecimal(beanTransaction.getLocalCommisionAmount(),
				foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(session.getCurrencyId()))));
		setCharges(GetRound.roundBigDecimal(beanTransaction.getLocalChargeAmount(),
				foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(session.getCurrencyId()))));
		// setDeliveryCharges(remittanceTransaction.getLocalDeliveryAmount());
		if (beanTransaction.getLocalDeliveryAmount() != null) {
			setDeliveryCharges(GetRound.roundBigDecimal(beanTransaction.getLocalDeliveryAmount(),
					foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(session.getCurrencyId()))));
		}

		setStopPaymentStatus(beanTransaction.getTransactionStatus());

		if (beanTransaction.getTransactionStatus() != null && beanTransaction.getTransactionStatus().equalsIgnoreCase("P")) {
			setBooPaymentDateforCusto(false);
			setBooReturnDate(true);
		} else if (beanTransaction.getTransactionStatus() != null && beanTransaction.getTransactionStatus().equalsIgnoreCase("R")) {
			setBooPaymentDateforCusto(true);
			setBooReturnDate(false);
		}

	}

	public void clickOnExit() throws IOException {
		if (session.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}
	}

	public String getTransactionStatus(String status) {
		String returnStatus = null;
		if (null == status) {
			returnStatus = "Not Cleared";
		}
		return returnStatus;
	}

	public void onFromDateSelect(SelectEvent event) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	}

	public void update() throws AMGException {

		System.out.println("Bank Rfer :" + getBankRefer() + "\n Pay Date Customer :" + getPaymentDateforCusto() + "\n Exch Date :"
				+ getPaymentDateExch() + "\t Remarks :" + getRemarks());
		HashMap<String, Object> inputValues = new HashMap<String, Object>();

		try {

			if (getTransferNo() != null) {

				List<UserFinancialYear> userFinList = iMiscellaneousReceiptPaymentService.getFinanacilYearId(getTransferYear());

				BigDecimal docFinYearId = null;
				if (userFinList.size() > 0) {
					docFinYearId = userFinList.get(0).getFinancialYear();
				}

				String startDateStr = null;
				String returnDateStr = null;
				String debitDateStr = null;

				SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
				if (getPaymentDateforCusto() != null) {
					startDateStr = dateformat.format(getPaymentDateforCusto());
				}
				if (getReturnDate() != null) {
					returnDateStr = dateformat.format(getReturnDate());
				}

				if (getPaymentDateExch() != null) {
					debitDateStr = dateformat.format(getPaymentDateExch());
				}

				inputValues.put("P_APPL_CNTY_ID", session.getCountryId());
				inputValues.put("P_COMPANY_ID", session.getCompanyId());
				inputValues.put("P_DOCUMENT_CODE", getDocumentCode());
				inputValues.put("P_DOC_FINYR", getTransferYear()==null?docFinYearId:getTransferYear());
				inputValues.put("P_DOCUMENT_NO", getTransferNo());
				inputValues.put("P_REMIT_STAUS", getStopPaymentStatus());
				inputValues.put("P_BANK_REF", getBankRefer());
				inputValues.put("P_PAID_DATE", startDateStr);
				inputValues.put("P_DEBIT_DATE", debitDateStr);
				inputValues.put("P_RETURN_DATE", returnDateStr);
				inputValues.put("P_REMARKS", getRemarks());
				inputValues.put("USER_NAME", session.getUserName());

				String msg = stopPaymentService.updateRemitFeedBackProcedure(inputValues);
				System.out.println("msg :" + msg);
				if (msg != null) {
					setErrorMessage(msg);
					RequestContext.getCurrentInstance().execute("csp.show();");
					return;
				} else {
					RequestContext.getCurrentInstance().execute("save.show();");
				}
			} else {
				RequestContext.getCurrentInstance().execute("statusEmptyDlg.show();");
			}

		} catch (AMGException e) {
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
			return;
		}

		/*
		 * if(getTransferNo()!= null ){ if(getStopPaymentStatus() != null){
		 * 
		 * List<UserFinancialYear> userFinList =
		 * iMiscellaneousReceiptPaymentService
		 * .getFinanacilYearId(getTransferYear());
		 * 
		 * BigDecimal docFinYearId = null; if(userFinList.size()>0){
		 * docFinYearId = userFinList.get(0).getFinancialYearID(); }
		 * 
		 * RemittanceComplaint remittanceComplaint =
		 * stopPaymentService.viewRemittanceComplaintbyDocumentNo
		 * (getTransferNo(),docFinYearId);
		 * 
		 * if (remittanceComplaint != null) {
		 * 
		 * CompanyMaster companyMaster = null; companyMaster =
		 * stopPaymentService.getCompanyCode(session.getCompanyId());
		 * if(companyMaster != null){
		 * setCompanycode(companyMaster.getCompanyCode()); }
		 * 
		 * stopPaymentService.updateRemittanceComplaint(getStopPaymentStatus(),
		 * remittanceComplaint.getRemittanceComplaintId(),
		 * session.getUserName());
		 * 
		 * try { String msg =
		 * stopPaymentService.moveToOldSystemStopPay(getCompanycode
		 * (),getDocumentCode(), getTransferYear(), getTransferNo());
		 * 
		 * if(msg !=null){ setErrorMessage(msg);
		 * RequestContext.getCurrentInstance().execute("csp.show();"); return; }
		 * 
		 * } catch (Exception e) { setErrorMessage(e.getMessage());
		 * RequestContext.getCurrentInstance().execute("csp.show();"); return; }
		 * 
		 * RequestContext.getCurrentInstance().execute("save.show();");
		 * 
		 * } }else{
		 * RequestContext.getCurrentInstance().execute("statusEmptyDlg.show();"
		 * ); }
		 * 
		 * }else{
		 * RequestContext.getCurrentInstance().execute("emptyDlg.show();"); }
		 */

	}

	public BigDecimal getFinYear() {

		List<UserFinancialYear> userFinList = iMiscellaneousReceiptPaymentService.getFinanacilYearId(getTransferYear());

		BigDecimal docFinYearId = null;
		if (userFinList.size() > 0) {
			docFinYearId = userFinList.get(0).getFinancialYear();
		}

		return docFinYearId;

	}

	public String getBeneBranchName() {
		return beneBranchName;
	}

	public void setBeneBranchName(String beneBranchName) {
		this.beneBranchName = beneBranchName;
	}

	public String getBeneBankName() {
		return beneBankName;
	}

	public void setBeneBankName(String beneBankName) {
		this.beneBankName = beneBankName;
	}

	public List<UserFinancialYear> getTrnxYearList() {
		return trnxYearList;
	}

	public void setTrnxYearList(List<UserFinancialYear> trnxYearList) {
		this.trnxYearList = trnxYearList;
	}

	public BigDecimal getTransferYear() {
		return transferYear;
	}

	public void setTransferYear(BigDecimal transferYear) {
		this.transferYear = transferYear;
	}

	public void fetchComplaintfinanceYear() {

		trnxYearList.clear();

		try {

			List<UserFinancialYear> lstFinancialYear = stopPaymentService.getTransferYearList();
			if (lstFinancialYear.size() != 0) {
				trnxYearList.addAll(lstFinancialYear);
			}

		} catch (NullPointerException ne) {
			setErrorMessage("Method Name::fetchComplaintfinanceYear");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}

	}

	public Date getPaymentDateforCusto() {
		return paymentDateforCusto;
	}

	public void setPaymentDateforCusto(Date paymentDateforCusto) {
		this.paymentDateforCusto = paymentDateforCusto;
	}

	public Date getPaymentDateExch() {
		return paymentDateExch;
	}

	public void setPaymentDateExch(Date paymentDateExch) {
		this.paymentDateExch = paymentDateExch;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public String getBankRefer() {
		return bankRefer;
	}

	public void setBankRefer(String bankRefer) {
		this.bankRefer = bankRefer;
	}

	public Boolean getBooPaymentDateforCusto() {
		return booPaymentDateforCusto;
	}

	public void setBooPaymentDateforCusto(Boolean booPaymentDateforCusto) {
		this.booPaymentDateforCusto = booPaymentDateforCusto;
	}

	public Boolean getBooPaymentDateExch() {
		return booPaymentDateExch;
	}

	public void setBooPaymentDateExch(Boolean booPaymentDateExch) {
		this.booPaymentDateExch = booPaymentDateExch;
	}

	public Boolean getBooReturnDate() {
		return booReturnDate;
	}

	public void setBooReturnDate(Boolean booReturnDate) {
		this.booReturnDate = booReturnDate;
	}

	public Boolean getBooSaveBtn() {
		return booSaveBtn;
	}

	public void setBooSaveBtn(Boolean booSaveBtn) {
		this.booSaveBtn = booSaveBtn;
	}
}
