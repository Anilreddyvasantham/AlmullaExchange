package com.amg.exchange.stoppayment.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.aop.RefundRequestReport;
import com.amg.exchange.cancelreissue.model.RemittanceView;
import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.foreigncurrency.model.PurposeOfTransaction;
import com.amg.exchange.foreigncurrency.model.ReceiptPayment;
import com.amg.exchange.foreigncurrency.model.SourceOfIncome;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.foreigncurrency.service.ForeignLocalCurrencyDenominationService;
import com.amg.exchange.foreigncurrency.service.IForeignCurrencyPurchaseService;
import com.amg.exchange.miscellaneous.service.IMiscellaneousReceiptPaymentService;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerIdproofView;
import com.amg.exchange.registration.service.ICompanyMasterservice;
import com.amg.exchange.registration.service.ICustomerRegistrationBranchService;
import com.amg.exchange.registration.service.IEncrptionDescriptionService;
import com.amg.exchange.registration.service.ILoginService;
import com.amg.exchange.remittance.service.ICustomerBankService;
import com.amg.exchange.remittance.service.IPaymentService;
import com.amg.exchange.remittance.service.IPersonalRemittanceService;
import com.amg.exchange.stoppayment.model.RemittanceComplaint;
import com.amg.exchange.stoppayment.model.RemittanceTransaction;
import com.amg.exchange.stoppayment.model.RemittanceTranxBenificiary;
import com.amg.exchange.stoppayment.service.IStopPaymentCollectionService;
import com.amg.exchange.stoppayment.service.IStopPaymentService;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.service.IGLTransactionForADocument;
import com.amg.exchange.treasury.viewModel.CurrencyMasterView;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.GetRound;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;
import com.amg.exchange.util.iCypherSecurity;
import com.amg.exchange.util.impl.CypherSecurityImpl;

@Component(value = "RefundRequestCreation")
@Scope("session")
public class RefundRequestCreationBean<T> implements Serializable {
	Logger log = Logger.getLogger(RefundRequestCreationBean.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public BigDecimal applicationYear;
	public BigDecimal refundReferenceNo;
	public BigDecimal remittanceYear;
	public BigDecimal remittanceTransRefNo;
	private String transactionType;
	private String transStatus;
	private String branchName;
	private BigDecimal customerRefNo;
	private String customerName;
	private String telephoneNo;
	private Date transferDate;
	private int validUntill;
	private String accPayee;
	private String benificiary;
	private String transferAmountCurrency;
	private String transferAmount;
	private String payableBank;
	private String payableBranch;
	private String refundFor;
	private BigDecimal exchangeRate;
	private BigDecimal commission;
	private BigDecimal charges;
	private BigDecimal deliveryCharges;
	private BigDecimal rateAdjust;
	private BigDecimal otherAdjust;
	private BigDecimal netRefund;
	private String errorMessage;
	private BigDecimal customerCode;
	private BigDecimal customerId;
	private BigDecimal currencyId;
	public BigDecimal applicationYearId;
	private BigDecimal transactionId;
	private BigDecimal localCurrencyId;
	private BigDecimal calNetAmountPaid;
	private BigDecimal payNetPaidAmount;
	private BigDecimal foreignCurrencyId;
	private BigDecimal documentFinanceYearId;
	private BigDecimal remittanceDocumnetCode;
	private BigDecimal remittanceDocumnetId;
	private BigDecimal localCurrnecyCommisionId;
	private BigDecimal localCommisionAmount;
	private String signature;
	private Boolean successPanel = false;
	private String cancellationStatus = "U";

	SessionStateManage sessionStateManage = new SessionStateManage();

	@Autowired
	IMiscellaneousReceiptPaymentService<T> iMiscellaneousReceiptPaymentService;
	@Autowired
	ForeignLocalCurrencyDenominationService<T> foreignLocalCurrencyDenominationService;
	@Autowired
	IForeignCurrencyPurchaseService<T> foreignCurrencyPurchaseService;
	@Autowired
	IPersonalRemittanceService iPersonalRemittanceService;
	@Autowired
	IGeneralService<T> iGeneralService;
	@Autowired
	IStopPaymentService<T> iStopPaymentService;
	@Autowired
	ICustomerRegistrationBranchService<T> icustomerRegistrationService;
	@Autowired
	ILoginService<T> iloginService;
	iCypherSecurity cypherSecurity = new CypherSecurityImpl();
	@Autowired
	ICustomerBankService icustomerBankService;
	@Autowired
	IStopPaymentCollectionService iStopPaymentCollectionService;
	@Autowired
	IPaymentService ipaymentService;
	@Autowired
	IEncrptionDescriptionService<T> encryptionDescriptionService;
	@Autowired
	IGLTransactionForADocument iglTransactionForADocument;
	@Autowired
	ICompanyMasterservice iCompanyMasterservice;
	@Autowired
	IStopPaymentService<T> stopPaymentService;

	private List<RemittanceView> lstremittanceTransaction = new ArrayList<RemittanceView>();

	public List<RemittanceView> getLstremittanceTransaction() {
		return lstremittanceTransaction;
	}
	public void setLstremittanceTransaction(List<RemittanceView> lstremittanceTransaction) {
		this.lstremittanceTransaction = lstremittanceTransaction;
	}

	public BigDecimal getLocalCurrnecyCommisionId() {
		return localCurrnecyCommisionId;
	}
	public void setLocalCurrnecyCommisionId(BigDecimal localCurrnecyCommisionId) {
		this.localCurrnecyCommisionId = localCurrnecyCommisionId;
	}

	public BigDecimal getLocalCommisionAmount() {
		return localCommisionAmount;
	}
	public void setLocalCommisionAmount(BigDecimal localCommisionAmount) {
		this.localCommisionAmount = localCommisionAmount;
	}

	public BigDecimal getRemittanceDocumnetCode() {
		return remittanceDocumnetCode;
	}

	public void setRemittanceDocumnetCode(BigDecimal remittanceDocumnetCode) {
		this.remittanceDocumnetCode = remittanceDocumnetCode;
	}
	public BigDecimal getRemittanceDocumnetId() {
		return remittanceDocumnetId;
	}

	public void setRemittanceDocumnetId(BigDecimal remittanceDocumnetId) {
		this.remittanceDocumnetId = remittanceDocumnetId;
	}
	public BigDecimal getDocumentFinanceYearId() {
		return documentFinanceYearId;
	}

	public void setDocumentFinanceYearId(BigDecimal documentFinanceYearId) {
		this.documentFinanceYearId = documentFinanceYearId;
	}

	public BigDecimal getForeignCurrencyId() {
		return foreignCurrencyId;
	}

	public void setForeignCurrencyId(BigDecimal foreignCurrencyId) {
		this.foreignCurrencyId = foreignCurrencyId;
	}

	public BigDecimal getPayNetPaidAmount() {
		return payNetPaidAmount;
	}

	public void setPayNetPaidAmount(BigDecimal payNetPaidAmount) {
		this.payNetPaidAmount = payNetPaidAmount;
	}

	public BigDecimal getCalNetAmountPaid() {
		return calNetAmountPaid;
	}

	public void setCalNetAmountPaid(BigDecimal calNetAmountPaid) {
		this.calNetAmountPaid = calNetAmountPaid;
	}

	public BigDecimal getLocalCurrencyId() {
		return localCurrencyId;
	}

	public void setLocalCurrencyId(BigDecimal localCurrencyId) {
		this.localCurrencyId = localCurrencyId;
	}

	public BigDecimal getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(BigDecimal transactionId) {
		this.transactionId = transactionId;
	}

	public BigDecimal getApplicationYearId() {
		return applicationYearId;
	}

	public void setApplicationYearId(BigDecimal applicationYearId) {
		this.applicationYearId = applicationYearId;
	}

	public BigDecimal getApplicationYear() {
		return applicationYear;
	}

	public void setApplicationYear(BigDecimal applicationYear) {
		this.applicationYear = applicationYear;
	}

	public BigDecimal getRefundReferenceNo() {
		return refundReferenceNo;
	}

	public void setRefundReferenceNo(BigDecimal refundReferenceNo) {
		this.refundReferenceNo = refundReferenceNo;
	}

	public BigDecimal getRemittanceYear() {
		return remittanceYear;
	}

	public void setRemittanceYear(BigDecimal remittanceYear) {
		this.remittanceYear = remittanceYear;
	}

	public BigDecimal getRemittanceTransRefNo() {
		return remittanceTransRefNo;
	}

	public void setRemittanceTransRefNo(BigDecimal remittanceTransRefNo) {
		this.remittanceTransRefNo = remittanceTransRefNo;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getTransStatus() {
		return transStatus;
	}

	public void setTransStatus(String transStatus) {
		this.transStatus = transStatus;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public BigDecimal getCustomerRefNo() {
		return customerRefNo;
	}

	public void setCustomerRefNo(BigDecimal customerRefNo) {
		this.customerRefNo = customerRefNo;
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

	public int getValidUntill() {
		return validUntill;
	}

	public void setValidUntill(int validUntill) {
		this.validUntill = validUntill;
	}

	public String getAccPayee() {
		return accPayee;
	}

	public void setAccPayee(String accPayee) {
		this.accPayee = accPayee;
	}

	public String getBenificiary() {
		return benificiary;
	}

	public void setBenificiary(String benificiary) {
		this.benificiary = benificiary;
	}

	public String getTransferAmountCurrency() {
		return transferAmountCurrency;
	}

	public void setTransferAmountCurrency(String transferAmountCurrency) {
		this.transferAmountCurrency = transferAmountCurrency;
	}

	public String getTransferAmount() {
		return transferAmount;
	}

	public void setTransferAmount(String transferAmount) {
		this.transferAmount = transferAmount;
	}

	public String getPayableBank() {
		return payableBank;
	}

	public void setPayableBank(String payableBank) {
		this.payableBank = payableBank;
	}

	public String getPayableBranch() {
		return payableBranch;
	}

	public void setPayableBranch(String payableBranch) {
		this.payableBranch = payableBranch;
	}

	public String getRefundFor() {
		return refundFor;
	}

	public void setRefundFor(String refundFor) {
		this.refundFor = refundFor;
	}

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
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

	public BigDecimal getRateAdjust() {
		return rateAdjust;
	}

	public void setRateAdjust(BigDecimal rateAdjust) {
		this.rateAdjust = rateAdjust;
	}

	public BigDecimal getOtherAdjust() {
		return otherAdjust;
	}

	public void setOtherAdjust(BigDecimal otherAdjust) {
		this.otherAdjust = otherAdjust;
	}

	public BigDecimal getNetRefund() {
		return netRefund;
	}

	public void setNetRefund(BigDecimal netRefund) {
		this.netRefund = netRefund;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public BigDecimal getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(BigDecimal customerCode) {
		this.customerCode = customerCode;
	}

	public BigDecimal getCustomerId() {
		return customerId;
	}

	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	public BigDecimal getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	public void getRemittanceTransactionDetails() {
		try {
			clearFetch();
			log.info("=========== getRemittanceTransactionDetails() Start============ ");
			log.info("==================REFUND REQUEST CALLED=======================");
			log.info("================== CountryId= " + sessionStateManage.getCountryId());
			log.info("================== Fin Year=" + getRemittanceYear());
			log.info("==================documentNo= " + getRemittanceTransRefNo());
			log.info("================== comanyid=" + sessionStateManage.getCompanyId());
			log.info("==================Document Code=3 "+new BigDecimal(Constants.DOCUMENT_CODE_FOR_REMITTANCE_TRANSACTION));
			RemittanceView remittanceTransaction = iStopPaymentCollectionService.getRemittanceTransactionFromView(sessionStateManage.getCountryId(), getRemittanceYear().intValue(), getRemittanceTransRefNo(), sessionStateManage.getCompanyId(), new BigDecimal(Constants.DOCUMENT_CODE_FOR_REMITTANCE_TRANSACTION));
			if (remittanceTransaction != null) {
				if (sessionStateManage.getBranchId().equalsIgnoreCase(remittanceTransaction.getBranchId().toPlainString())) {
					System.out.println(remittanceTransaction.getDocumentNo() + "getTransferNo() =========== > " + getRemittanceTransRefNo());
					System.out.println("remittanceTransaction.getBankId() =========== > " + remittanceTransaction.getBankId());
					if (remittanceTransaction.getTransactionStatus() != null) {
						if (remittanceTransaction.getTransactionStatus().equalsIgnoreCase(Constants.TRANSACTION_STATUS_FOR_CASH_CODE)) {
							setErrorMessage("Refund over. Document year " + remittanceTransaction.getDocumentFinYear() + " Document No " + remittanceTransaction.getDocumentNo());
							RequestContext.getCurrentInstance().execute("csp.show();");
							return;
						} else if (remittanceTransaction.getTransactionStatus().equalsIgnoreCase(Constants.TRANSACTION_STATUS_FOR_STOP_PAYMENT_CODE)) {
							setErrorMessage("Stop payment processed. Document year " + remittanceTransaction.getDocumentFinYear() + " Document No " + remittanceTransaction.getDocumentNo());
							RequestContext.getCurrentInstance().execute("csp.show();");
							return;
						} else if (remittanceTransaction.getTransactionStatus().equalsIgnoreCase(Constants.TRANSACTION_STATUS_FOR_STOP_REFUND_OVER_CODE)) {
							setErrorMessage("Already paid. Document year " + remittanceTransaction.getDocumentFinYear() + " Document No " + remittanceTransaction.getDocumentNo());
							RequestContext.getCurrentInstance().execute("csp.show();");
							return;
						}
					} else {
						BigDecimal bankId = null;
						BigDecimal remittanceId = null;
						if (iStopPaymentCollectionService.getBanKId(Constants.TRANSACTION_STATUS_FOR_WESTERN_UNION) != null) {
							bankId = iStopPaymentCollectionService.getBanKId(Constants.TRANSACTION_STATUS_FOR_WESTERN_UNION);
						}
						if (iStopPaymentCollectionService.getRemittanceId(sessionStateManage.getLanguageId(), Constants.CASH_PRODUCT) != null) {
							remittanceId = iStopPaymentCollectionService.getRemittanceId(sessionStateManage.getLanguageId(), Constants.CASH_PRODUCT);
						}

						if((remittanceTransaction.getBankStaus()==null || (remittanceTransaction.getBankStaus()!=null && remittanceTransaction.getBankStaus().equals(""))) 
								&& remittanceTransaction.getTransferMode()!=null && remittanceTransaction.getTransferMode().equalsIgnoreCase("W"))
						{
							setErrorMessage("Cannot Refund Web Service Transaction. Document year " + remittanceTransaction.getDocumentFinYear() + " Document No " + remittanceTransaction.getDocumentNo());
							RequestContext.getCurrentInstance().execute("csp.show();");
							return;
						}

						if(remittanceTransaction.getCancelStatus()!=null && remittanceTransaction.getCancelStatus().equals("U"))
						{
							setErrorMessage("Refund document is pending. Document year " + remittanceTransaction.getDocumentFinYear() + " Document No " + remittanceTransaction.getDocumentNo());
							RequestContext.getCurrentInstance().execute("csp.show();");
							return;
						}



						if( remittanceTransaction.getTransferMode()!=null && remittanceTransaction.getTransferMode().equalsIgnoreCase(Constants.W))
						{

							if(remittanceTransaction.getWebServiceStatus()!=null && remittanceTransaction.getWebServiceStatus().equalsIgnoreCase(Constants.I) && remittanceTransaction.getStopDocumentNo()==null)
							{
								setErrorMessage("Refund Can not be Processed W/O Stop Payment for This Document. Document year " + remittanceTransaction.getDocumentFinYear() + " Document No " + remittanceTransaction.getDocumentNo());
								RequestContext.getCurrentInstance().execute("csp.show();");
								return;
							}

							if(remittanceTransaction.getWebServiceStatus()!=null && remittanceTransaction.getWebServiceStatus().equalsIgnoreCase(Constants.I) &&
									(remittanceTransaction.getBankStaus()==null || (remittanceTransaction.getBankStaus()!=null && remittanceTransaction.getBankStaus().equals(""))))
							{
								setErrorMessage("Bank Feedback for Stop Payment Request Not Updated for this Document . Document year " + remittanceTransaction.getDocumentFinYear() + " Document No " + remittanceTransaction.getDocumentNo());
								RequestContext.getCurrentInstance().execute("csp.show();");
								return;
							}

							if(remittanceTransaction.getWebServiceStatus()!=null && remittanceTransaction.getWebServiceStatus().equalsIgnoreCase(Constants.I) &&
									( (remittanceTransaction.getBankStaus()!=null && remittanceTransaction.getBankStaus().equalsIgnoreCase(Constants.Y))))
							{
								setErrorMessage("Refund Can not be Processed, Bank has confirmed the payment on Stop Payment Request. Document year " + remittanceTransaction.getDocumentFinYear() + " Document No " + remittanceTransaction.getDocumentNo());
								RequestContext.getCurrentInstance().execute("csp.show();");
								return;
							}


						}
						else
						{

							if(remittanceTransaction.getFileCreation() != null && remittanceTransaction.getFileCreation().equalsIgnoreCase(Constants.T) 
									&& remittanceTransaction.getStopDocumentNo()==null)
							{
								setErrorMessage("Refund Can not be Processed W/O Stop Payment for This Document. Document year " + remittanceTransaction.getDocumentFinYear() + " Document No " + remittanceTransaction.getDocumentNo());
								RequestContext.getCurrentInstance().execute("csp.show();");
								return;
							}

							if(remittanceTransaction.getFileCreation() != null && remittanceTransaction.getFileCreation().equalsIgnoreCase(Constants.T) 
									&& (remittanceTransaction.getBankStaus()==null || (remittanceTransaction.getBankStaus()!=null && remittanceTransaction.getBankStaus().equals(""))))
							{
								setErrorMessage("Refund Can not be Processed W/O Stop Payment for This Document. Document year " + remittanceTransaction.getDocumentFinYear() + " Document No " + remittanceTransaction.getDocumentNo());
								RequestContext.getCurrentInstance().execute("csp.show();");
								return;
							}


							if(remittanceTransaction.getFileCreation() != null && remittanceTransaction.getFileCreation().equalsIgnoreCase(Constants.T) 
									&& ((remittanceTransaction.getBankStaus()!=null && remittanceTransaction.getBankStaus().equalsIgnoreCase(Constants.Y))))
							{
								setErrorMessage("Refund Can not be Processed, Bank has confirmed the payment on Stop Payment Request. Document year " + remittanceTransaction.getDocumentFinYear() + " Document No " + remittanceTransaction.getDocumentNo());
								RequestContext.getCurrentInstance().execute("csp.show();");
								return;
							}


						}

						if (remittanceTransaction.getFileCreation() != null) {
							if (remittanceTransaction.getFileCreation().equalsIgnoreCase(Constants.Yes)) {
								setErrorMessage("File Creation not allowed to process. Document year " + remittanceTransaction.getDocumentFinYear() + " Document No " + remittanceTransaction.getDocumentNo());
								RequestContext.getCurrentInstance().execute("csp.show();");
							} else if (remittanceTransaction.getRemittanceModeId().equals(remittanceId)) {
								setErrorMessage("Cash Product not allowed to process. Document year " + remittanceTransaction.getDocumentFinYear() + " Document No " + remittanceTransaction.getDocumentNo());
								RequestContext.getCurrentInstance().execute("csp.show();");
							} else if (remittanceTransaction.getBankId().equals(bankId)) {
								setErrorMessage("Western Union Product not allowed to process. Document year " + remittanceTransaction.getDocumentFinYear() + " Document No " + remittanceTransaction.getDocumentNo());
								RequestContext.getCurrentInstance().execute("csp.show();");
							} else if (remittanceTransaction.getFileCreation().equalsIgnoreCase(Constants.No)) {
								boolean refundStatus = iStopPaymentCollectionService.checkRemittanceCancelationStatus(sessionStateManage.getCompanyId(), remittanceTransaction.getDocumentId(), remittanceTransaction.getDocumentCode(), getRemittanceTransRefNo(), getRemittanceYear());
								if (refundStatus) {
									setErrorMessage("Refund Request already created for this year " + remittanceTransaction.getDocumentFinYear() + "   and Document No  " + remittanceTransaction.getDocumentNo());
									setRemittanceYear(null);
									setRemittanceTransRefNo(null);
									RequestContext.getCurrentInstance().execute("csp.show();");
									return;
								}
								if (remittanceTransaction.getBranchName() != null) {
									setBranchName(remittanceTransaction.getBranchName());
								} else {
									setBranchName(null);
								}
								List<Customer> customer = iStopPaymentCollectionService.getCustomerInfo(remittanceTransaction.getCustomerId());
								setCustomerCode(remittanceTransaction.getCustomerRefNo());
								setCustomerId(remittanceTransaction.getCustomerId());
								for (Customer customer2 : customer) {
									//setCustomerName(customer2.getFirstName() + "" + customer2.getLastName());
									setCustomerName(customer2.getFirstName()==null? "":customer2.getFirstName()+ " "+customer2.getMiddleName()==null?"":customer2.getMiddleName()+" " + customer2.getLastName()==null?"":customer2.getLastName());
									setTelephoneNo(customer2.getMobile());
									setCustomerRefNo(customer2.getCustomerReference());
									try{
										setSignature(customer2.getSignatureSpecimenClob().getSubString(1,(int) customer2.getSignatureSpecimenClob().length()));
									}catch(Exception e){

									}
								}
								List<CurrencyMasterView> currencyList = iStopPaymentCollectionService.getCurrencyDetails(remittanceTransaction.getSaleCurrencyId());
								if (currencyList != null) {
									setTransferAmountCurrency(currencyList.get(0).getQuoteName());
									setCurrencyId(currencyList.get(0).getCurrencyId());
								} else {
									setTransferAmountCurrency(null);
								}
								setTransactionId(remittanceTransaction.getRemittanceTransactionId());
								setTransStatus(getTransactionStatus(remittanceTransaction.getTransactionStatus()));
								setTransferAmount(remittanceTransaction.getSaleAmount().toString());
								setTransferDate(remittanceTransaction.getDocumentDate());
								String product = iPersonalRemittanceService.getRemittanceServiceRuleName(remittanceTransaction.getBankCountryId(), remittanceTransaction.getSaleCurrencyId(), remittanceTransaction.getBankId(), remittanceTransaction.getRemittanceModeId(),
										remittanceTransaction.getDeliveryModeId());
								if (product != null) {
									setTransactionType(product);
								} else {
									setTransactionType(null);
								}
								List<RemittanceTranxBenificiary> trnxBenificiaries = iStopPaymentService.viewDetailsTranxBeneficiary(getTransactionId());
								for (RemittanceTranxBenificiary remittancetrnxBenificiary : trnxBenificiaries) {
									setBenificiary(remittancetrnxBenificiary.getBeneficiaryFirstName() + " " + remittancetrnxBenificiary.getBeneficiarySecondName() + " " + "A/C No:" + remittancetrnxBenificiary.getBeneficiaryAccountNo() + ", " + remittancetrnxBenificiary.getBeneficiaryBank() + " "
											+ remittancetrnxBenificiary.getBeneficiaryBranch());
									setPayableBranch(remittancetrnxBenificiary.getBeneficiaryBranch());
									setPayableBank(remittancetrnxBenificiary.getBeneficiaryBank());
								}
								setRefundFor(remittanceTransaction.getSaleAmount().toString());
								setExchangeRate(remittanceTransaction.getExchangeRateApplied());
								setCommission(GetRound.roundBigDecimal(remittanceTransaction.getLocalCommisionAmount(), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
								setCharges(GetRound.roundBigDecimal(remittanceTransaction.getLocalChargeAmount(), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
								// setDeliveryCharges(remittanceTransaction.getLocalDeliveryAmount());
								if (remittanceTransaction.getLocalDeliveryAmount() != null)
									setDeliveryCharges(GetRound.roundBigDecimal(remittanceTransaction.getLocalDeliveryAmount(), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
								setRateAdjust(null);
								setOtherAdjust(null);
								setLocalCurrencyId(remittanceTransaction.getPurchaseCurrencyId());
								setCalNetAmountPaid(GetRound.roundBigDecimal(remittanceTransaction.getLocalNetTrxAmount(), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));// .toPlainString();
								setPayNetPaidAmount(GetRound.roundBigDecimal(remittanceTransaction.getPurchaseAmount(), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
								setNetRefund(GetRound.roundBigDecimal(remittanceTransaction.getPurchaseAmount(), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
								setForeignCurrencyId(remittanceTransaction.getSaleCurrencyId());
								setAccPayee(null);
								setRemittanceDocumnetCode(remittanceTransaction.getDocumentCode());
								setRemittanceDocumnetId(remittanceTransaction.getDocumentId());
								setLocalCurrnecyCommisionId(remittanceTransaction.getLocalCurrnecyCommisionId());
								setLocalCommisionAmount(remittanceTransaction.getLocalCommisionAmount());
							}
						} else {
							boolean refundStatus = iStopPaymentCollectionService.checkRemittanceCancelationStatus(sessionStateManage.getCompanyId(), remittanceTransaction.getDocumentId(), remittanceTransaction.getDocumentCode(), getRemittanceTransRefNo(), getRemittanceYear());
							if (refundStatus) {
								setErrorMessage("Refund Request already created for this year " + remittanceTransaction.getDocumentFinYear() + "   and Document No  " + remittanceTransaction.getDocumentNo());
								setRemittanceYear(null);
								setRemittanceTransRefNo(null);
								RequestContext.getCurrentInstance().execute("csp.show();");
								return;
							}
							if (remittanceTransaction.getBranchName() != null) {
								setBranchName(remittanceTransaction.getBranchName());
							} else {
								setBranchName(null);
							}
							List<Customer> customer = iStopPaymentCollectionService.getCustomerInfo(remittanceTransaction.getCustomerId());
							setCustomerCode(remittanceTransaction.getCustomerRefNo());
							setCustomerId(remittanceTransaction.getCustomerId());
							for (Customer customer2 : customer) {
								//setCustomerName(customer2.getFirstName() + "" + customer2.getLastName());
								setCustomerName(customer2.getFirstName()==null? "":customer2.getFirstName()+ " "+customer2.getMiddleName()==null?"":customer2.getMiddleName()+" " + customer2.getLastName()==null?"":customer2.getLastName());
								setTelephoneNo(customer2.getMobile());
								setCustomerRefNo(customer2.getCustomerReference());
								try{
									setSignature(customer2.getSignatureSpecimenClob().getSubString(1,(int) customer2.getSignatureSpecimenClob().length()));
								}catch(Exception e){

								}
							}
							List<CurrencyMasterView> currencyList = iStopPaymentCollectionService.getCurrencyDetails(remittanceTransaction.getSaleCurrencyId());
							if (currencyList != null) {
								setTransferAmountCurrency(currencyList.get(0).getQuoteName());
								setCurrencyId(currencyList.get(0).getCurrencyId());
							} else {
								setTransferAmountCurrency(null);
							}
							setTransactionId(remittanceTransaction.getRemittanceTransactionId());
							setTransStatus(getTransactionStatus(remittanceTransaction.getTransactionStatus()));
							setTransferAmount(remittanceTransaction.getSaleAmount().toString());
							setTransferDate(remittanceTransaction.getDocumentDate());
							String product = iPersonalRemittanceService.getRemittanceServiceRuleName(remittanceTransaction.getBankCountryId(), remittanceTransaction.getSaleCurrencyId(), remittanceTransaction.getBankId(), remittanceTransaction.getRemittanceModeId(),
									remittanceTransaction.getDeliveryModeId());
							if (product != null) {
								setTransactionType(product);
							} else {
								setTransactionType(null);
							}
							List<RemittanceTranxBenificiary> trnxBenificiaries = iStopPaymentService.viewDetailsTranxBeneficiary(getTransactionId());
							for (RemittanceTranxBenificiary remittancetrnxBenificiary : trnxBenificiaries) {
								setBenificiary(remittancetrnxBenificiary.getBeneficiaryFirstName() + " " + remittancetrnxBenificiary.getBeneficiarySecondName() + " " + "A/C No:" + remittancetrnxBenificiary.getBeneficiaryAccountNo() + ", " + remittancetrnxBenificiary.getBeneficiaryBank() + " "
										+ remittancetrnxBenificiary.getBeneficiaryBranch());
								setPayableBranch(remittancetrnxBenificiary.getBeneficiaryBranch());
								setPayableBank(remittancetrnxBenificiary.getBeneficiaryBank());
								setAccPayee(remittancetrnxBenificiary.getBeneficiaryFirstName());
							}
							setRefundFor(remittanceTransaction.getSaleAmount().toString());
							setExchangeRate(remittanceTransaction.getExchangeRateApplied());
							setCommission(GetRound.roundBigDecimal(remittanceTransaction.getLocalCommisionAmount(), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
							setCharges(GetRound.roundBigDecimal(remittanceTransaction.getLocalChargeAmount(), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
							// setDeliveryCharges(remittanceTransaction.getLocalDeliveryAmount());
							if (remittanceTransaction.getLocalDeliveryAmount() != null)
								setDeliveryCharges(GetRound.roundBigDecimal(remittanceTransaction.getLocalDeliveryAmount(), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
							setRateAdjust(null);
							setOtherAdjust(null);
							setLocalCurrencyId(remittanceTransaction.getPurchaseCurrencyId());
							setCalNetAmountPaid(GetRound.roundBigDecimal(remittanceTransaction.getLocalNetTrxAmount(), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));// .toPlainString();
							setPayNetPaidAmount(GetRound.roundBigDecimal(remittanceTransaction.getPurchaseAmount(), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
							setNetRefund(GetRound.roundBigDecimal(remittanceTransaction.getPurchaseAmount(), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
							setForeignCurrencyId(remittanceTransaction.getSaleCurrencyId());
							// setAccPayee(getCurrentDateWithFormat());
							setRemittanceDocumnetCode(remittanceTransaction.getDocumentCode());
							setRemittanceDocumnetId(remittanceTransaction.getDocumentId());
							setLocalCurrnecyCommisionId(remittanceTransaction.getLocalCurrnecyCommisionId());
							setLocalCommisionAmount(remittanceTransaction.getLocalCommisionAmount());
						}
					}
				} else {
					RequestContext.getCurrentInstance().execute("diffnotallow.show();");
					return;
				}
			} else {
				setErrorMessage("Transaction Number does not exist " + getRemittanceTransRefNo());
				RequestContext.getCurrentInstance().execute("csp.show();");
			}
			log.info("=========== getRemittanceTransactionDetails() End============ ");
		} catch (Exception e) {
			setErrorMessage("Exception occurred " + e);
			setRemittanceTransRefNo(null);
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}

	public String getTransactionStatus(String status) {
		String returnStatus = null;
		if (null == status) {
			returnStatus = "Not Cleared";
		}
		return returnStatus;
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void navigateToRefundRequestPage() {
		setAccPayee(null);
		setSuccessPanel(false);
		setMainPanelRender(true);
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "RefundRequestCreation.xhtml");
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			context.redirect("../remittance/RefundRequestCreation.xhtml");
			clear();
			getApplicationYearFromdb();
			nextApplicationNo();
		} catch (Exception e) {
			log.info("Problem to redirect: " + e);
		}
	}

	private void clear() {
		//setApplicationYear(null);
		//setApplicationYearId(null);
		//setRefundReferenceNo(null);
		setRemittanceYear(null);
		setRemittanceTransRefNo(null);
		setBranchName(null);
		setCustomerCode(null);
		setCustomerId(null);
		setCustomerName(null);
		setTelephoneNo(null);
		setCustomerRefNo(null);
		setTransferAmountCurrency(null);
		setCurrencyId(null);
		setTransactionId(null);
		setTransactionType(null);
		setTransStatus(null);
		setTransferAmount(null);
		setTransferDate(null);
		setBenificiary(null);
		setAccPayee(null);
		setPayNetPaidAmount(null);
		setPayableBranch(null);
		setPayableBank(null);
		setRefundFor(null);
		setExchangeRate(null);
		setCommission(null);
		setCharges(null);
		setDeliveryCharges(null);
		setNetRefund(null);
		setRateAdjust(null);
		setOtherAdjust(null);
		setDocumentFinanceYearId(null);
		setRemittanceDocumnetCode(null);
		setRemittanceDocumnetId(null);
		setLocalCurrnecyCommisionId(null);
		setLocalCommisionAmount(null);
	}

	private void clearFetch() {
		setBranchName(null);
		setCustomerCode(null);
		setCustomerId(null);
		setCustomerName(null);
		setTelephoneNo(null);
		setCustomerRefNo(null);
		setTransferAmountCurrency(null);
		setCurrencyId(null);
		setTransactionId(null);
		setTransactionType(null);
		setTransStatus(null);
		setTransferAmount(null);
		setTransferDate(null);
		setBenificiary(null);
		setPayableBranch(null);
		setPayableBank(null);
		setRefundFor(null);
		setExchangeRate(null);
		setCommission(null);
		setCharges(null);
		setDeliveryCharges(null);
		setNetRefund(null);
		setRateAdjust(null);
		setOtherAdjust(null);
		setDocumentFinanceYearId(null);
		setRemittanceDocumnetCode(null);
		setRemittanceDocumnetId(null);
		setLocalCurrnecyCommisionId(null);
		setLocalCommisionAmount(null);

		if(lstremittanceTransaction != null || !lstremittanceTransaction.isEmpty()){
			lstremittanceTransaction.clear();
		}
	}

	public void nextApplicationNo() {
		String docNo = getDocumentSerialID(Constants.Yes);
		if (docNo.equalsIgnoreCase("0")) {
			setRefundReferenceNo(BigDecimal.ZERO);
		} else {
			setRefundReferenceNo(new BigDecimal(docNo));
		}
	}

	public String getDocumentSerialID(String processIn) {
		try {
			HashMap<String, String> outPutValues = iGeneralService.getNextDocumentRefNumber(sessionStateManage.getCountryId().intValue(), sessionStateManage.getCompanyId().intValue(), Integer.parseInt(Constants.DOCUMENT_CODE_FOR_REFUND_REQUEST), getApplicationYear().intValue(), processIn,
					sessionStateManage.getCountryBranchCode());
			log.info("====docno===" + outPutValues.get("DOCNO"));
			String proceErrorMsg = outPutValues.get("PROCE_ERORRMSG");
			if (proceErrorMsg != null) {
				// setBooProcedureDialog(true);
				// setErrorMsg(proceErrorMsg );
				RequestContext.getCurrentInstance().execute("proceErr.show();");
				return "0";
			} else {
				// setBooProcedureDialog(false);
				String documentSerialID = outPutValues.get("DOCNO");
				return documentSerialID;
			}
		} catch (NumberFormatException | AMGException e) {
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("proceErr.show();");
			return "0";
		}
	}

	public void getApplicationYearFromdb() {
		try {
			List<UserFinancialYear> applicationYearList = iGeneralService.getDealYear(new Date());
			if (applicationYearList.size() > 0) {
				setApplicationYear(applicationYearList.get(0).getFinancialYear());
				setApplicationYearId(applicationYearList.get(0).getFinancialYearID());
			}
		} catch (Exception e) {
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("proceErr.show();");
		}
	}

	public String save() {		
		
		List<ReceiptPayment> checkReceiptPayment = iStopPaymentCollectionService.checkRecieptPaymentExist(getRemittanceYear(), getRemittanceTransRefNo());
		if (checkReceiptPayment != null && checkReceiptPayment.size() != 0) {
			ReceiptPayment recPay = checkReceiptPayment.get(0);
			setErrorMessage("Already record existed for Transaction No : " + getRemittanceYear() + " / "+ getRemittanceTransRefNo() + ". Pending for Approval for : " + recPay.getDocumentFinanceYear() + " / " + recPay.getDocumentNo());
			RequestContext.getCurrentInstance().execute("proceErr.show();");
			return "";
		}
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
		Date acc_Month = null;
		try {
			acc_Month = DATE_FORMAT.parse(getCurrentDateWithFormat());
			log.info("Account Date :" + acc_Month);
		} catch (ParseException e) {
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("proceErr.show();");
			return "";
		}
		try {
			ReceiptPayment receiptPayment = new ReceiptPayment();
			CompanyMaster companyMaster = new CompanyMaster();
			companyMaster.setCompanyId(sessionStateManage.getCompanyId());
			receiptPayment.setFsCompanyMaster(companyMaster);
			
			// generate document Number
			String refunRefNo = getDocumentSerialID(Constants.U);
			
			setRefundReferenceNo(new BigDecimal(refunRefNo));
			
			receiptPayment.setDocumentNo(new BigDecimal(refunRefNo));
			// Country Save
			CountryMaster countryMaster = new CountryMaster();
			countryMaster.setCountryId(sessionStateManage.getCountryId());
			receiptPayment.setFsCountryMaster(countryMaster);
			// customer Save
			Customer customer = new Customer();
			customer.setCustomerId(getCustomerId());
			receiptPayment.setFsCustomer(customer);

			List<CompanyMasterDesc> companyCode = iGeneralService.getCompanyList(sessionStateManage.getCompanyId(), sessionStateManage.getLanguageId());
			if(companyCode != null && !companyCode.isEmpty() && companyCode.get(0).getFsCompanyMaster() != null){
				BigDecimal companyCodeValue = companyCode.get(0).getFsCompanyMaster().getCompanyCode();
				receiptPayment.setCompanyCode(companyCodeValue);
			}

			receiptPayment.setCustomerReference(getCustomerRefNo());
			receiptPayment.setDocumentFinanceYear(getApplicationYear());
			receiptPayment.setCustomerName(getCustomerName());
			CurrencyMaster localcurrencyMaster = new CurrencyMaster();
			localcurrencyMaster.setCurrencyId(getLocalCurrencyId());
			receiptPayment.setLocalFsCountryMaster(localcurrencyMaster);
			// receiptPayment.setColDocCode(new
			// BigDecimal(Constants.DOCUMENT_CODE_FOR_REFUND_REQUEST));
			// receiptPayment.setColDocFyr(new BigDecimal(getFinanceYear()));
			receiptPayment.setForignTrnxAmount(new BigDecimal(getTransferAmount()));
			//receiptPayment.setLocalTrnxAmount(getPayNetPaidAmount());
			receiptPayment.setTransactionActualRate(getExchangeRate());
			
			BigDecimal roundingValue = null;
			try {
				roundingValue = iStopPaymentCollectionService.roundingTotalNetAmountbyFunction(sessionStateManage.getCountryId(),new BigDecimal(getRefundFor()), "D");
			}
			catch(Exception e){
				e.printStackTrace();
				
			}
			
			//receiptPayment.setLocalNetAmount(getExchangeRate().multiply(roundingValue));
			//receiptPayment.setLocalTrnxAmount(getExchangeRate().multiply(roundingValue));
			receiptPayment.setLocalNetAmount(getNetRefund());
			receiptPayment.setLocalTrnxAmount(getNetRefund());
							
			//receiptPayment.setLocalNetAmount(getCalNetAmountPaid());
			//receiptPayment.setLocalNetAmount(getNetRefund());
			receiptPayment.setDocumentStatus(Constants.U);
			receiptPayment.setGeneralLegerDate(new Date());
			receiptPayment.setIsActive(Constants.Yes);
			receiptPayment.setAccountMMYYYY(acc_Month);
			// receiptPayment.setTransactionType(Constants.C);
			receiptPayment.setDocumentCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_REFUND_REQUEST));
			// receiptPayment.setDocumentNo(new BigDecimal(documentSerialId));
			receiptPayment.setDocumentFinanceYearId(getDocumentFinanceYearId());
			CountryBranch countryBranch = new CountryBranch();
			countryBranch.setCountryBranchId(new BigDecimal(sessionStateManage.getBranchId()));
			receiptPayment.setCountryBranch(countryBranch);
			receiptPayment.setDocumentDate(new Date());
			CurrencyMaster fcurrencyMaster = new CurrencyMaster();
			fcurrencyMaster.setCurrencyId(getForeignCurrencyId());
			receiptPayment.setForeignFsCountryMaster(fcurrencyMaster);
			// receiptPayment.setLocalTrnxAmount(getLocalCurrencyId());
			// purposeoftransaction
			PurposeOfTransaction purposeofTransaction = new PurposeOfTransaction();
			purposeofTransaction.setPurposeId(new BigDecimal(Constants.PURPUSE_ID_FOR_FC));
			receiptPayment.setPurposeOfTransaction(purposeofTransaction);
			SourceOfIncome sourceOfIncome = new SourceOfIncome();
			sourceOfIncome.setSourceId(new BigDecimal(Constants.SOURCE_ID_FOR_FC));
			receiptPayment.setSourceOfIncome(sourceOfIncome);
			// receiptPayment.setRemarks(getRemarks());
			receiptPayment.setCreatedDate(new Date());
			receiptPayment.setCreatedBy(sessionStateManage.getUserName());
			receiptPayment.setTransferReference(getRemittanceTransRefNo());
			receiptPayment.setTransferFinanceYear(getRemittanceYear());
			//receiptPayment.setLocalCommisionCurrencyId(getLocalCurrnecyCommisionId());
			//receiptPayment.setLocalCommisionAmoumnt(getLocalCommisionAmount());

			receiptPayment.setLocCode(sessionStateManage.getCountryBranchCode());
			BigDecimal documentPk=null;
			documentPk=iStopPaymentCollectionService.toFetchDocumentPk(new BigDecimal(Constants.DOCUMENT_CODE_FOR_REFUND_REQUEST));
			if(documentPk != null){
				receiptPayment.setDocumentId(documentPk);
			}

			iMiscellaneousReceiptPaymentService.saveOrUpdate(receiptPayment);
			saveRemitComplaint(new BigDecimal(refunRefNo));
			// Move to OLD System
			// List<Collect> collectionList =
			// iStopPaymentService.getCollectionListById(collect.getCollectionId());
			List<RemittanceTransaction> remitTxn = iStopPaymentService.getRemitTxnDetailsById(getTransactionId());
			// List<ViewRemittanceTransaction> remitTxn =
			// iStopPaymentService.getRemitTxnDetailsFromViewById(getTransactionId());
			if (remitTxn.size() > 0) {
				System.out.println("receiptPayment.getFsCountryMaster().getCountryId()" + receiptPayment.getFsCountryMaster().getCountryId());
				System.out.println("receiptPayment.getFsCompanyMaster().getCompanyId()" + receiptPayment.getFsCompanyMaster().getCompanyId());
				System.out.println("receiptPayment.getDocumentCode()" + receiptPayment.getDocumentCode());
				System.out.println("receiptPayment.getDocumentFinanceYear()" + receiptPayment.getDocumentFinanceYear());
				System.out.println("receiptPayment.getDocumentNo()" + receiptPayment.getDocumentNo());
				System.out.println("remitTxn.get(0).getCompanyId().getCompanyId()" + remitTxn.get(0).getCompanyId().getCompanyId());
				System.out.println("remitTxn.get(0).getDocumentId().getDocumentCode()" + remitTxn.get(0).getDocumentId().getDocumentCode());
				System.out.println("remitTxn.get(0).getDocumentFinanceYear()" + remitTxn.get(0).getDocumentFinanceYear());
				System.out.println("remitTxn.get(0).getDocumentNo()" + remitTxn.get(0).getDocumentNo());
				String errMsg = iStopPaymentService.moveToOldSystemRefundRequest(receiptPayment.getFsCountryMaster().getCountryId(), receiptPayment.getFsCompanyMaster().getCompanyId(), receiptPayment.getDocumentCode(), receiptPayment.getDocumentFinanceYear(), receiptPayment.getDocumentNo(),
						remitTxn.get(0).getCompanyId().getCompanyId(), remitTxn.get(0).getDocumentId().getDocumentCode(), remitTxn.get(0).getDocumentFinanceYear(), remitTxn.get(0).getDocumentNo());
			}
			// RequestContext.getCurrentInstance().execute("complete.show();");
			return "refundsuccessPage";
		} catch (Exception e) {
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("proceErr.show();");
			return "";
		}
	}

	private void saveRemitComplaint(BigDecimal recieptRefNo) {

		RemittanceComplaint remitComplaint = new RemittanceComplaint();
		
		List<UserFinancialYear> userFinList = iMiscellaneousReceiptPaymentService.getFinanacilYearId(getRemittanceYear());
		
		BigDecimal docFinYearId = null;
		if(userFinList.size()>0){
			docFinYearId = userFinList.get(0).getFinancialYearID();
		}

		List<RemittanceComplaint> checkReceiptPayment = iStopPaymentCollectionService.checkRemittanceComplaintExist(docFinYearId, getRemittanceTransRefNo());

		if(checkReceiptPayment != null && !checkReceiptPayment.isEmpty()){

			RemittanceComplaint remitComplaintId = checkReceiptPayment.get(0);

			iStopPaymentService.updateRemitComplaint(getApplicationYear(),recieptRefNo,remitComplaintId.getRemittanceComplaintId());

		}else{

			remitComplaint.setCompanyId(sessionStateManage.getCompanyId());

			List<CompanyMasterDesc> companyCode = iGeneralService.getCompanyList(sessionStateManage.getCompanyId(), sessionStateManage.getLanguageId());
			if(companyCode != null && !companyCode.isEmpty() && companyCode.get(0).getFsCompanyMaster() != null){
				BigDecimal companyCodeValue = companyCode.get(0).getFsCompanyMaster().getCompanyCode();
				remitComplaint.setCompanyCode(companyCodeValue);
			}

			remitComplaint.setApplicationCountryId(sessionStateManage.getCountryId());
			remitComplaint.setDocumentCode(getRemittanceDocumnetCode());
			remitComplaint.setDocumentId(getRemittanceDocumnetId());
			remitComplaint.setDocumentFinanceYear(getDocumentFinanceYearId());
			remitComplaint.setDocumentNo(getRemittanceTransRefNo());

			remitComplaint.setCancelDocumentDate(new Date());
			remitComplaint.setCancelDocumentCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_REFUND_REQUEST));
			remitComplaint.setCancelDocumentFinanceYear(getApplicationYear());
			remitComplaint.setCancelDocumentNumber(recieptRefNo);
			remitComplaint.setCancellationStatus(Constants.U);
			
			if(docFinYearId != null){
				remitComplaint.setDocumentFinanceYear(docFinYearId);
			}
			
			remitComplaint.setIsactive(Constants.Yes);
			remitComplaint.setCreatedBy(sessionStateManage.getUserName());
			remitComplaint.setCreatedDate(new Date());

			iStopPaymentService.saveOrUpdate((T) remitComplaint);
		}

	}

	public static String getCurrentDateWithFormat() {
		Map<Integer, String> data = new HashMap<Integer, String>();
		for (int i = 0; i < 12; i++) {
			if (i < 9) {
				data.put(i, "0" + String.valueOf(i + 1));
			} else {
				data.put(i, String.valueOf(i + 1));
			}
		}
		//String year = String.valueOf(new Date().getYear()).substring(1, 3);
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		String year =String.valueOf(calendar.get(Calendar.YEAR));

		System.out.println(Calendar.getInstance().get(Calendar.MONTH));
		return "01/" + data.get(Calendar.getInstance().get(Calendar.MONTH)) + "/" + year;
	}

	public int getFinanceYear() {
		int financeYear = 0;
		try {
			List<UserFinancialYear> financialYearList = foreignCurrencyPurchaseService.getUserFinancialYear(new Date());
			log.info("financialYearList :" + financialYearList.size());
			if (financialYearList != null && financialYearList.size() > 0)
				financeYear = Integer.parseInt(financialYearList.get(0).getFinancialYear().toString());
			setDocumentFinanceYearId(financialYearList.get(0).getFinancialYearID());
			// setFinanceYear(financeYear);
		} catch (Exception e) {
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("proceErr.show();");
		}
		return financeYear;
	}

	public void exit() {
		try {
			clear();
			//FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
			String roleNameDesc=iPersonalRemittanceService.toFetchRoleName(new BigDecimal(sessionStateManage.getRoleId()));
			String roleName = roleNameDesc.trim();
			if (roleName.equalsIgnoreCase(Constants.USER_ROLE_BRANCHSTAFF)) {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
			}else if (roleName.equalsIgnoreCase(Constants.USER_ROLE_BRANCH_MANAGER) || roleName.equalsIgnoreCase(Constants.USER_ROLE_MANAGER)) {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
			}else if (roleName.equalsIgnoreCase(Constants.USER_ROLE_CORPORATE)) {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/corporatehome.xhtml");
			}else{
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
			}
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::" + exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}
	// ############## Report Genaration By Subramanian ##############

	private JasperPrint jasperPrint;
	private List<RefundRequestReport> refundRequestList = new CopyOnWriteArrayList<RefundRequestReport>();

	public void init() throws JRException {
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(refundRequestList);
		String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("reports/design/RefundRequestOfficeCopy.jasper");
		jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap(), beanCollectionDataSource);
	}

	public void genarateRefundRequestOfficeCopyReports(ActionEvent actionEvent) throws JRException, IOException {
		ServletOutputStream servletOutputStream=null;
		try{
			fetchRefundRequestReportDetails();
			init();
			HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			httpServletResponse.addHeader("Content-disposition", "attachment; filename=RefundRequestOfficeCopy.pdf");
			servletOutputStream = httpServletResponse.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
			FacesContext.getCurrentInstance().responseComplete();
		}catch(Exception e){

			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
			return;
		}finally{
			if(servletOutputStream!=null){
				servletOutputStream.close();
			}
		}
	}

	public void init1() throws JRException {
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(refundRequestList);
		String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("reports/design/RefundRequestCustomerCopy.jasper");
		jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap(), beanCollectionDataSource);
	}

	public void genarateRefundRequestCustomerCopyReports(ActionEvent actionEvent) throws JRException, IOException {
		ServletOutputStream servletOutputStream=null;
		try{
			fetchRefundRequestReportDetails();
			init1();

			HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			httpServletResponse.addHeader("Content-disposition", "attachment; filename=RefundRequestCustomerCopyCopy.pdf");
			servletOutputStream = httpServletResponse.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
			FacesContext.getCurrentInstance().responseComplete();
		}catch(Exception e){
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
			return;
		}finally{
			if(servletOutputStream!=null){
				servletOutputStream.close();
			}
		}
	}

	public void fetchRefundRequestReportDetails() {
		refundRequestList.clear();
		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath = ctx.getRealPath("//");
		//String logoPath = realPath + Constants.LOGO_PATH;
		
		String logoPath =null;
		if(sessionStateManage.getCountryName().equalsIgnoreCase(Constants.KUWAIT)){
			logoPath = realPath+Constants.LOGO_PATH;
		}else if(sessionStateManage.getCountryName().equalsIgnoreCase(Constants.OMAN)){
			logoPath = realPath+Constants.LOGO_PATH_OM;
		}else{
			logoPath =realPath+Constants.LOGO_PATH;
		}
		
		RefundRequestReport refundRequestReport = new RefundRequestReport();
		NumberFormat usa = NumberFormat.getInstance(Locale.getDefault());
		//refundRequestReport.setCustomerName(getCustomerName());
		refundRequestReport.setCustomerRefNo(getCustomerRefNo()+" / "+getCustomerName());
		BigDecimal   amt=new BigDecimal(getRefundFor());

		if (getRefundFor() != null) {
			refundRequestReport.setRefundFor(round(amt,foreignLocalCurrencyDenominationService.getDecimalPerCurrency(getForeignCurrencyId())));
		}
		if(getTransferAmount()!=null){
			refundRequestReport.setRefund( getPayNetPaidAmount() );
		}
		refundRequestReport.setNetRefund(getNetRefund());
		refundRequestReport.setPaymentNo(getRefundReferenceNo());
		refundRequestReport.setPaymentYear(getApplicationYear());
		if (getRefundReferenceNo() != null && getApplicationYear() != null) {
			refundRequestReport.setPaymentNoAndYear(getApplicationYear().toString()+" / "+getRefundReferenceNo().toString());
		}
		SimpleDateFormat form1 = new SimpleDateFormat("dd-MM-yyyy");
		String finalDate1 = form1.format(getTransferDate());
		refundRequestReport.setPaymentDate(finalDate1);
		// refundRequestReport.setPaymentDate(getTransferDate());
		if (getExchangeRate() != null) {
			refundRequestReport.setExchangeRate(getExchangeRate().toString());
		}
		refundRequestReport.setDrawnBank(getPayableBank());
		refundRequestReport.setDrawnBankBranch(getPayableBranch());
		SimpleDateFormat form = new SimpleDateFormat("dd-MM-yyyy");
		String finalDate = form.format(getTransferDate());
		refundRequestReport.setRefDate(finalDate);
		refundRequestReport.setRefNo(getRemittanceTransRefNo());
		refundRequestReport.setRefYear(getRemittanceYear());
		if (getRemittanceTransRefNo() != null && getRemittanceYear() != null) {
			refundRequestReport.setRemitYearNumber(getRemittanceYear().toString()+" / "+getRemittanceTransRefNo().toString());
		}
		List<RemittanceTranxBenificiary> trnxBenificiaries = iStopPaymentService.viewDetailsTranxBeneficiary(getTransactionId());
		if(trnxBenificiaries.size()>0){
			for (RemittanceTranxBenificiary remittancetrnxBenificiary : trnxBenificiaries) {
				if(remittancetrnxBenificiary.getBeneficiaryAccountNo()!=null){
					refundRequestReport.setFavouringBankAccNo(remittancetrnxBenificiary.getBeneficiaryAccountNo());
				}
				refundRequestReport.setFavouringBankBranch(remittancetrnxBenificiary.getBeneficiaryBranch());
				refundRequestReport.setFavouringBankName(remittancetrnxBenificiary.getBeneficiaryBank());
				refundRequestReport.setFavouringBankLocation(remittancetrnxBenificiary.getBeneficiaryName());
			}
		}
		refundRequestReport.setNetRefundCurrencyName(iGeneralService.getCurrencyQuote(getLocalCurrencyId()));
		refundRequestReport.setRefundCurrencyName(iGeneralService.getCurrencyQuote(getLocalCurrencyId()));

		refundRequestReport.setExchangeRateCurrencyName(getTransferAmountCurrency()+"/"+iGeneralService.getCurrencyQuote(getLocalCurrencyId()));
		refundRequestReport.setRefundOf(getTransactionType() );
		String refundforCurrency=iGeneralService.getCurrencyName( getForeignCurrencyId());

		String[] bits = refundforCurrency.split(" ");
		String  refundForCur = bits[bits.length-1];
		refundRequestReport.setRefundForCurrencyName(refundForCur);

		List<CompanyMasterDesc> companyList=  iGeneralService.getCompanyList(sessionStateManage.getCompanyId(),sessionStateManage.getLanguageId());
		if(companyList.size()>0){
			refundRequestReport.setCompanyName( companyList.get( 0).getCompanyName());
			refundRequestReport.setAddress( companyList.get( 0).getFsCompanyMaster().getAddress1()+","+companyList.get( 0).getFsCompanyMaster().getAddress2()+","+companyList.get( 0).getFsCompanyMaster().getAddress3()+",C.R."+companyList.get( 0).getFsCompanyMaster().getRegistrationNumber()+",Share Capital:K.D."+usa.format(new BigDecimal(companyList.get( 0).getFsCompanyMaster().getCapitalAmount())));
		}


		List<CustomerIdproofView> custList=iGeneralService.getCustomerIdProofDetailsFromView(getCustomerId() );

		if(custList.size()>0){
			refundRequestReport.setIdNumber(custList.get(0).getIdProofInt() );	
			refundRequestReport.setIdType(custList.get(0).getIdProofTypeDesc() );
		}

		refundRequestReport.setSignature(getSignature());
		refundRequestReport.setLogoPath(logoPath);
		refundRequestList.add(refundRequestReport);
	}


	private Boolean mainPanelRender = true;

	public Boolean getSuccessPanel() {
		return successPanel;
	}

	public void setSuccessPanel(Boolean successPanel) {
		this.successPanel = successPanel;
	}

	public Boolean getMainPanelRender() {
		return mainPanelRender;
	}

	public void setMainPanelRender(Boolean mainPanelRender) {
		this.mainPanelRender = mainPanelRender;
	}

	// procedure call to show error and proceed or not
	/*public void getRemittanceTransactionDetailsCheckingProc() {
		try {

			clearFetch();

			log.info("=========== getRemittanceTransactionDetails() Start============ ");
			log.info("==================REFUND REQUEST CALLED=======================");
			log.info("================== CountryId= " + sessionStateManage.getCountryId());
			log.info("================== Fin Year= " + getRemittanceYear());
			log.info("==================documentNo= " + getRemittanceTransRefNo());
			log.info("================== comanyid= " + sessionStateManage.getCompanyId());
			log.info("==================Document Code= "+ new BigDecimal(Constants.DOCUMENT_CODE_FOR_REMITTANCE_TRANSACTION));

			if(getRemittanceYear() != null && getRemittanceTransRefNo() != null){
				
				RemittanceView remittanceTransaction = iStopPaymentCollectionService.getRemittanceTransactionFromView(sessionStateManage.getCountryId(), getRemittanceYear().intValue(), getRemittanceTransRefNo(), sessionStateManage.getCompanyId(), new BigDecimal(Constants.DOCUMENT_CODE_FOR_REMITTANCE_TRANSACTION));

				if (remittanceTransaction != null) {

					BigDecimal documentId = iStopPaymentCollectionService.toFetchDocumentPk(new BigDecimal(Constants.DOCUMENT_CODE_FOR_REMITTANCE_TRANSACTION));

					// procedure call
					HashMap<String, Object> checkingProcedureVALIDATEREFUND = new HashMap<String, Object>();

					checkingProcedureVALIDATEREFUND.put("P_REMIT_COMP_ID", sessionStateManage.getCompanyId());
					checkingProcedureVALIDATEREFUND.put("P_REMIT_DOC_ID", documentId);
					checkingProcedureVALIDATEREFUND.put("P_REMIT_DOCFYR", getRemittanceYear());
					checkingProcedureVALIDATEREFUND.put("P_REMIT_DOCNO", getRemittanceTransRefNo());
					checkingProcedureVALIDATEREFUND.put("P_LOGIN_BRANCH_ID", new BigDecimal(sessionStateManage.getBranchId()));

					Map<String, Object> lstfetchdetails = iStopPaymentCollectionService.toFetchRefundDetails(checkingProcedureVALIDATEREFUND);

					String comfirmInd = (String)lstfetchdetails.get("P_CONFIRM_IND");
					String errMsg = (String)lstfetchdetails.get("P_ERROR");

					if(comfirmInd != null){
						if(comfirmInd.equalsIgnoreCase(Constants.No)){
							// normal Dialog - stopping
							if(errMsg != null){
								setErrorMessage("EX_P_VALIDATE_REFUND : " +errMsg);
								RequestContext.getCurrentInstance().execute("csp.show();");
								return;
							}else{
								// continue
								fetchRefundRequestDetails(remittanceTransaction);
							}

						}else if(comfirmInd.equalsIgnoreCase(Constants.Yes)){
							// normal Dialog - still continue
							if(errMsg != null){
								lstremittanceTransaction.add(remittanceTransaction);
								setErrorMessage("EX_P_VALIDATE_REFUND : " +errMsg);
								RequestContext.getCurrentInstance().execute("cspyesno.show();");
								return;
							}else{
								// continue
								fetchRefundRequestDetails(remittanceTransaction);
							}
						}
					}

				} else {

					// company code
					BigDecimal companyCode = null;
					List<CompanyMasterDesc> lstcompanymaster = iCompanyMasterservice.viewById(sessionStateManage.getCompanyId());
					if(lstcompanymaster.size() != 0){
						CompanyMasterDesc companycode = lstcompanymaster.get(0);
						if(companycode.getFsCompanyMaster().getCompanyCode() != null){
							companyCode = companycode.getFsCompanyMaster().getCompanyCode();
						}
					}

					HashMap<String, Object> fetchTransferToRemits = stopPaymentService.fetchTransferForStopPayment(companyCode, new BigDecimal(Constants.DOCUMENT_CODE_FOR_REMITTANCE_TRANSACTION), getRemittanceYear(), getRemittanceTransRefNo(),Constants.RefundForm);

					if((String)fetchTransferToRemits.get("ERRMSG") != null && !((String) fetchTransferToRemits.get("ERRMSG")).equalsIgnoreCase("")){

						setRemittanceTransRefNo(null);
						setErrorMessage((String) fetchTransferToRemits.get("ERRMSG"));
						RequestContext.getCurrentInstance().execute("csp.show();");
						clearFetch();
						return;

					}else{

						if((RemittanceTxnOLDView)fetchTransferToRemits.get("TRANSFER") != null){

							RemittanceTxnOLDView remitOldTrnx = (RemittanceTxnOLDView) fetchTransferToRemits.get("TRANSFER");
							
							if(remitOldTrnx != null){

								BigDecimal documentId = iStopPaymentCollectionService.toFetchDocumentPk(new BigDecimal(Constants.DOCUMENT_CODE_FOR_REMITTANCE_TRANSACTION));

								// procedure call
								HashMap<String, Object> checkingProcedureVALIDATEREFUND = new HashMap<String, Object>();

								checkingProcedureVALIDATEREFUND.put("P_REMIT_COMP_ID", sessionStateManage.getCompanyId());
								checkingProcedureVALIDATEREFUND.put("P_REMIT_DOC_ID", documentId);
								checkingProcedureVALIDATEREFUND.put("P_REMIT_DOCFYR", getRemittanceYear());
								checkingProcedureVALIDATEREFUND.put("P_REMIT_DOCNO", getRemittanceTransRefNo());
								checkingProcedureVALIDATEREFUND.put("P_LOGIN_BRANCH_ID", new BigDecimal(sessionStateManage.getBranchId()));

								Map<String, Object> lstfetchdetails = iStopPaymentCollectionService.toFetchRefundDetails(checkingProcedureVALIDATEREFUND);

								String comfirmInd = (String)lstfetchdetails.get("P_CONFIRM_IND");
								String errMsg = (String)lstfetchdetails.get("P_ERROR");
								
								//errMsg = null;

								if(comfirmInd != null){
									if(comfirmInd.equalsIgnoreCase(Constants.No)){
										// normal Dialog - stopping
										if(errMsg != null){
											setErrorMessage("EX_P_VALIDATE_REFUND : " +errMsg);
											RequestContext.getCurrentInstance().execute("csp.show();");
											return;
										}else{
											// continue
											fetchRefundRequestDetailsFromOld(remitOldTrnx);
										}

									}else if(comfirmInd.equalsIgnoreCase(Constants.Yes)){
										// normal Dialog - still continue
										if(errMsg != null){
											lstremittanceTransaction.add(remittanceTransaction);
											setErrorMessage("EX_P_VALIDATE_REFUND : " +errMsg);
											RequestContext.getCurrentInstance().execute("cspyesno.show();");
											return;
										}else{
											// continue
											fetchRefundRequestDetailsFromOld(remitOldTrnx);
										}
									}
								}
							}else{
								setErrorMessage("Transaction Number does not exist " + getRemittanceTransRefNo());
								RequestContext.getCurrentInstance().execute("csp.show();");
							}
						}else{
							setErrorMessage("Transaction Number Not Transfer from OLD " + getRemittanceTransRefNo());
							RequestContext.getCurrentInstance().execute("csp.show();");
						}
					}
				}
				log.info("=========== getRemittanceTransactionDetails() End============ ");
			}

		} catch (Exception e) {
			setErrorMessage("Exception occurred " + e);
			setRemittanceTransRefNo(null);
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}*/
	
	public void getRemittanceTransactionDetailsCheckingProc() {
		try {

			clearFetch();

			log.info("=========== getRemittanceTransactionDetails() Start============ ");
			log.info("==================REFUND REQUEST CALLED=======================");
			log.info("================== CountryId= " + sessionStateManage.getCountryId());
			log.info("================== Fin Year= " + getRemittanceYear());
			log.info("==================documentNo= " + getRemittanceTransRefNo());
			log.info("================== comanyid= " + sessionStateManage.getCompanyId());
			log.info("==================Document Code= "+ new BigDecimal(Constants.DOCUMENT_CODE_FOR_REMITTANCE_TRANSACTION));

			if(getRemittanceYear() != null && getRemittanceTransRefNo() != null){
				
				RemittanceView remittanceTransaction = iStopPaymentCollectionService.getRemittanceTransactionFromView(sessionStateManage.getCountryId(), getRemittanceYear().intValue(), getRemittanceTransRefNo(), sessionStateManage.getCompanyId(), new BigDecimal(Constants.DOCUMENT_CODE_FOR_REMITTANCE_TRANSACTION));
				
				// if VW_REMITTANCE_TRANSACTION list not available old trnx to Migrate from Old Emos
				if (remittanceTransaction == null) {
					
					// company code
					BigDecimal companyCode = null;
					List<CompanyMasterDesc> lstcompanymaster = iCompanyMasterservice.viewById(sessionStateManage.getCompanyId());
					if(lstcompanymaster.size() != 0){
						CompanyMasterDesc companycode = lstcompanymaster.get(0);
						if(companycode.getFsCompanyMaster().getCompanyCode() != null){
							companyCode = companycode.getFsCompanyMaster().getCompanyCode();
						}
					}
					
					HashMap<String, Object> fetchTransferToRemits = stopPaymentService.fetchTransferForStopPayment(companyCode, new BigDecimal(Constants.DOCUMENT_CODE_FOR_REMITTANCE_TRANSACTION), getRemittanceYear(), getRemittanceTransRefNo(),Constants.RefundForm);

					if((String)fetchTransferToRemits.get("ERRMSG") != null && !((String) fetchTransferToRemits.get("ERRMSG")).equalsIgnoreCase("")){
						setRemittanceTransRefNo(null);
						setErrorMessage((String) fetchTransferToRemits.get("ERRMSG"));
						RequestContext.getCurrentInstance().execute("csp.show();");
						clear();
						return;
					}else{
						remittanceTransaction = iStopPaymentCollectionService.getRemittanceTransactionFromView(sessionStateManage.getCountryId(), getRemittanceYear().intValue(), getRemittanceTransRefNo(), sessionStateManage.getCompanyId(), new BigDecimal(Constants.DOCUMENT_CODE_FOR_REMITTANCE_TRANSACTION));
					}
				}

				if (remittanceTransaction != null) {

					/*if(remittanceTransaction.getCancelStatus()!=null && remittanceTransaction.getCancelStatus().equalsIgnoreCase(Constants.D))
					{
						setErrorMessage("This transaction number is already deactivated");
						RequestContext.getCurrentInstance().execute("csp.show();");
						return;
					}*/
					BigDecimal documentId = iStopPaymentCollectionService.toFetchDocumentPk(new BigDecimal(Constants.DOCUMENT_CODE_FOR_REMITTANCE_TRANSACTION));
					List<RemittanceComplaint> lstRemittanceComplaint = iStopPaymentCollectionService.getRefundRequestExist(sessionStateManage.getCompanyId(),documentId,getRemittanceTransRefNo(),remittanceTransaction.getDocumentFinYearId(),cancellationStatus);
					
					if(lstRemittanceComplaint!=null && lstRemittanceComplaint.size()>0){
						if(lstRemittanceComplaint.get(0).getCancellationStatus().equalsIgnoreCase(cancellationStatus)){
							Date date = lstRemittanceComplaint.get(0).getCancelDocumentDate();
							SimpleDateFormat form = new SimpleDateFormat("dd-MM-yyyy");
							String docDate = form.format(date);
							setErrorMessage("Refund Document cancel document date"+":"+" "+ docDate +" "+","+" "+"Doc year : "+getRemittanceYear()+" "+'/'+" "+"Doc num : "+getRemittanceTransRefNo()+" "+"Refund document dated Prepared but no Refund, Do you want to delete ?");
							RequestContext.getCurrentInstance().execute("remitComplaint.show();");
							return;
						}
					}
					

					// procedure call
					HashMap<String, Object> checkingProcedureVALIDATEREFUND = new HashMap<String, Object>();

					checkingProcedureVALIDATEREFUND.put("P_REMIT_COMP_ID", sessionStateManage.getCompanyId());
					checkingProcedureVALIDATEREFUND.put("P_REMIT_DOC_ID", documentId);
					checkingProcedureVALIDATEREFUND.put("P_REMIT_DOCFYR", getRemittanceYear());
					checkingProcedureVALIDATEREFUND.put("P_REMIT_DOCNO", getRemittanceTransRefNo());
					checkingProcedureVALIDATEREFUND.put("P_LOGIN_BRANCH_ID", new BigDecimal(sessionStateManage.getBranchId()));

					Map<String, Object> lstfetchdetails = iStopPaymentCollectionService.toFetchRefundDetails(checkingProcedureVALIDATEREFUND);

					String comfirmInd = (String)lstfetchdetails.get("P_CONFIRM_IND");
					String errMsg = (String)lstfetchdetails.get("P_ERROR");

					if(comfirmInd != null){
						if(comfirmInd.equalsIgnoreCase(Constants.No)){
							// normal Dialog - stopping
							if(errMsg != null){
								setErrorMessage("EX_P_VALIDATE_REFUND : " +errMsg);
								RequestContext.getCurrentInstance().execute("csp.show();");
								return;
							}else{
								// continue
								fetchRefundRequestDetails(remittanceTransaction);
							}

						}else if(comfirmInd.equalsIgnoreCase(Constants.Yes)){
							// normal Dialog - still continue
							if(errMsg != null){
								lstremittanceTransaction.add(remittanceTransaction);
								setErrorMessage("EX_P_VALIDATE_REFUND : " +errMsg);
								RequestContext.getCurrentInstance().execute("cspyesno.show();");
								return;
							}else{
								// continue
								fetchRefundRequestDetails(remittanceTransaction);
							}
						}
					}

				} else {
					setErrorMessage("Transaction Number does not exist " + getRemittanceTransRefNo());
					RequestContext.getCurrentInstance().execute("csp.show();");
				}
				log.info("=========== getRemittanceTransactionDetails() End============ ");
			}

		} catch (Exception e) {
			setErrorMessage("Exception occurred " + e);
			setRemittanceTransRefNo(null);
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}

	public void clickYesIn(){
		if(lstremittanceTransaction.size() != 0){
			fetchRefundRequestDetails(lstremittanceTransaction.get(0));
		}
	}

	public static BigDecimal round(BigDecimal bd, int places) {
		if (places < 0) throw new IllegalArgumentException();
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd;
	}

	public void fetchRefundRequestDetails(RemittanceView remittanceTransaction){

		boolean refundStatus = iStopPaymentCollectionService.checkRemittanceCancelationStatus(sessionStateManage.getCompanyId(), remittanceTransaction.getDocumentId(), remittanceTransaction.getDocumentCode(), getRemittanceTransRefNo(), getRemittanceYear());
		if (refundStatus) {
			setErrorMessage("Refund Request already created for this year " + remittanceTransaction.getDocumentFinYear() + "   and Document No  " + remittanceTransaction.getDocumentNo());
			setRemittanceYear(null);
			setRemittanceTransRefNo(null);
			RequestContext.getCurrentInstance().execute("csp.show();");
			//return;
		}else{
			if (remittanceTransaction.getBranchName() != null) {
				setBranchName(remittanceTransaction.getBranchName());
			} else {
				setBranchName(null);
			}
			List<Customer> customer = iStopPaymentCollectionService.getCustomerInfo(remittanceTransaction.getCustomerId());
			setCustomerCode(remittanceTransaction.getCustomerRefNo());
			setCustomerId(remittanceTransaction.getCustomerId());
			for (Customer customer2 : customer) {
				setCustomerName((customer2.getFirstName()==null? "" :customer2.getFirstName()) + " " + (customer2.getMiddleName()==null? "" :customer2.getMiddleName()) + " " + (customer2.getLastName()==null? "" :customer2.getLastName()));
				setTelephoneNo(customer2.getMobile());
				setCustomerRefNo(customer2.getCustomerReference());
				try{
					if(customer2.getSignatureSpecimenClob() != null){
						setSignature(customer2.getSignatureSpecimenClob().getSubString(1,(int) customer2.getSignatureSpecimenClob().length()));
					}
				}catch(Exception e){

				}
			}
			List<CurrencyMasterView> currencyList = iStopPaymentCollectionService.getCurrencyDetails(remittanceTransaction.getSaleCurrencyId());
			if (currencyList != null) {
				setTransferAmountCurrency(currencyList.get(0).getQuoteName());
				setCurrencyId(currencyList.get(0).getCurrencyId());
			} else {
				setTransferAmountCurrency(null);
			}
			setTransactionId(remittanceTransaction.getRemittanceTransactionId());
			setTransStatus(getTransactionStatus(remittanceTransaction.getTransactionStatus()));
			setTransferAmount(remittanceTransaction.getSaleAmount().toString());
			setTransferDate(remittanceTransaction.getDocumentDate());
			String product = iPersonalRemittanceService.getRemittanceServiceRuleName(remittanceTransaction.getBankCountryId(), remittanceTransaction.getSaleCurrencyId(), remittanceTransaction.getBankId(), remittanceTransaction.getRemittanceModeId(),
					remittanceTransaction.getDeliveryModeId());
			if (product != null) {
				setTransactionType(product);
			} else {
				setTransactionType(null);
			}
			List<RemittanceTranxBenificiary> trnxBenificiaries = iStopPaymentService.viewDetailsTranxBeneficiary(getTransactionId());
			for (RemittanceTranxBenificiary remittancetrnxBenificiary : trnxBenificiaries) {
				setBenificiary(remittancetrnxBenificiary.getBeneficiaryFirstName() + " " + remittancetrnxBenificiary.getBeneficiarySecondName() + " " + "A/C No:" + remittancetrnxBenificiary.getBeneficiaryAccountNo() + ", " + remittancetrnxBenificiary.getBeneficiaryBank() + " "
						+ remittancetrnxBenificiary.getBeneficiaryBranch());
				setPayableBranch(remittancetrnxBenificiary.getBeneficiaryBranch());
				setPayableBank(remittancetrnxBenificiary.getBeneficiaryBank());
			}
			setRefundFor(remittanceTransaction.getSaleAmount().toString());
			
			
			BigDecimal serviceMasterId = iStopPaymentService.getServiceMasterId(remittanceTransaction.getRemittanceModeId());
			BigDecimal buyRateMax = iStopPaymentService.getBuyRateMin(remittanceTransaction.getApplicationCountryId(),remittanceTransaction.getBankCountryId() , remittanceTransaction.getSaleCurrencyId(),remittanceTransaction.getBranchId() , remittanceTransaction.getBankId(), serviceMasterId);
			BigDecimal netRefundAmount = null;
			BigDecimal roundingValue = null;
			
			// Transaction Exchange is greater than the new Buy Max Rate, then do the following. 
			if(remittanceTransaction.getExchangeRateApplied().compareTo(buyRateMax)>0){
			
				if(buyRateMax!=null){
					setExchangeRate(buyRateMax);
				}
				
				netRefundAmount = remittanceTransaction.getSaleAmount().multiply(buyRateMax);
				
				
				try {
					roundingValue = iStopPaymentCollectionService.roundingTotalNetAmountbyFunction(sessionStateManage.getCountryId(),netRefundAmount, "D");
				}
				catch(Exception e){
					e.printStackTrace();
				 }
				setPayNetPaidAmount(GetRound.roundBigDecimal(roundingValue, foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
				setNetRefund(GetRound.roundBigDecimal(roundingValue, foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
			}else{
				setExchangeRate(remittanceTransaction.getExchangeRateApplied());
				setPayNetPaidAmount(GetRound.roundBigDecimal(remittanceTransaction.getPurchaseAmount(), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
				setNetRefund(       GetRound.roundBigDecimal(remittanceTransaction.getPurchaseAmount(), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
			}
			setCommission(GetRound.roundBigDecimal(remittanceTransaction.getLocalCommisionAmount(), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
			setCharges(GetRound.roundBigDecimal(remittanceTransaction.getLocalChargeAmount(), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
			// setDeliveryCharges(remittanceTransaction.getLocalDeliveryAmount());

			if (remittanceTransaction.getLocalDeliveryAmount() != null)
				setDeliveryCharges(GetRound.roundBigDecimal(remittanceTransaction.getLocalDeliveryAmount(), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
			
			setRateAdjust(null);
			setOtherAdjust(null);
			setLocalCurrencyId(remittanceTransaction.getPurchaseCurrencyId());
			setCalNetAmountPaid(GetRound.roundBigDecimal(remittanceTransaction.getLocalNetTrxAmount(), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));// .toPlainString();
			setForeignCurrencyId(remittanceTransaction.getSaleCurrencyId());
			setAccPayee(null);
			setRemittanceDocumnetCode(remittanceTransaction.getDocumentCode());
			setRemittanceDocumnetId(remittanceTransaction.getDocumentId());
			setLocalCurrnecyCommisionId(remittanceTransaction.getLocalCurrnecyCommisionId());
			setLocalCommisionAmount(remittanceTransaction.getLocalCommisionAmount());
		}
	}
	
	public void deleteSelected(){
		BigDecimal documentId = iStopPaymentCollectionService.toFetchDocumentPk(new BigDecimal(Constants.DOCUMENT_CODE_FOR_REMITTANCE_TRANSACTION));
		RemittanceView remittanceTransaction = iStopPaymentCollectionService.getRemittanceTransactionFromView(sessionStateManage.getCountryId(), getRemittanceYear().intValue(), getRemittanceTransRefNo(), sessionStateManage.getCompanyId(), new BigDecimal(Constants.DOCUMENT_CODE_FOR_REMITTANCE_TRANSACTION));
		iStopPaymentCollectionService.saveOrUpdate(sessionStateManage.getCompanyId(),documentId,getRemittanceTransRefNo(),remittanceTransaction.getDocumentFinYearId(),cancellationStatus,getRemittanceYear(),sessionStateManage.getUserName());
		setRemittanceYear(null);
		setRemittanceTransRefNo(null);
	}
	
	/*public void fetchRefundRequestDetailsFromOld(RemittanceTxnOLDView remittanceTransaction){

		boolean refundStatus = iStopPaymentCollectionService.checkRemittanceCancelationStatus(sessionStateManage.getCompanyId(), remittanceTransaction.getDocumentId(), remittanceTransaction.getDocumentCode(), getRemittanceTransRefNo(), getRemittanceYear());
		if (refundStatus) {
			setErrorMessage("Refund Request already created for this year " + remittanceTransaction.getDocumentFinanceYear() + "   and Document No  " + remittanceTransaction.getDocumentNumber());
			setRemittanceYear(null);
			setRemittanceTransRefNo(null);
			RequestContext.getCurrentInstance().execute("csp.show();");
			//return;
		}else{
			if (remittanceTransaction.getBeneficaryBranchName() != null) {
				setBranchName(remittanceTransaction.getBeneficaryBranchName());
			} else {
				setBranchName(null);
			}
			List<Customer> customer = iStopPaymentCollectionService.getCustomerInfo(remittanceTransaction.getCustomerId());
			setCustomerCode(remittanceTransaction.getCustomerReference());
			setCustomerId(remittanceTransaction.getCustomerId());
			for (Customer customer2 : customer) {
				setCustomerName((customer2.getFirstName()==null? "" :customer2.getFirstName()) + " " + (customer2.getMiddleName()==null? "" :customer2.getMiddleName()) + " " + (customer2.getLastName()==null? "" :customer2.getLastName()));
				setTelephoneNo(new BigDecimal(customer2.getMobile()));
				setCustomerRefNo(customer2.getCustomerReference());
				try{
					setSignature(customer2.getSignatureSpecimenClob().getSubString(1,(int) customer2.getSignatureSpecimenClob().length()));
				}catch(Exception e){

				}
			}
			
			List<CurrencyMasterView> currencyList = iStopPaymentCollectionService.getCurrencyDetails(remittanceTransaction.getForeignCurrencyId());
			if (currencyList != null) {
				setTransferAmountCurrency(currencyList.get(0).getQuoteName());
				setCurrencyId(currencyList.get(0).getCurrencyId());
			} else {
				setTransferAmountCurrency(null);
			}
			
			setTransactionId(remittanceTransaction.getRemittanceTransactionId());
			setTransStatus(getTransactionStatus(remittanceTransaction.getTransactionStatus()));
			
			//setTransferAmount(remittanceTransaction.getSaleAmount().toString());
			setTransferAmount(remittanceTransaction.getForeignTransactionAmount().toPlainString());
			
			setTransferDate(remittanceTransaction.getDocumentDate());
			
			String productName = iStopPaymentCollectionService.getProductName(remittanceTransaction.getBankId(), remittanceTransaction.getRemittanceModeId(), remittanceTransaction.getDeliveryModeId(), remittanceTransaction.getApplicationCountryId(), remittanceTransaction.getForeignCurrencyId());
			if (productName != null) {
				setTransactionType(productName);
			} else {
				setTransactionType(null);
			}
			
			setBenificiary(remittanceTransaction.getBeneficaryName() + " " + "A/C No:" + remittanceTransaction.getBeneficaryAccountNumber() + ", " + remittanceTransaction.getBeneficaryBankName() + " " + remittanceTransaction.getBeneficaryBranchName());
			setPayableBranch(remittanceTransaction.getBeneficaryBranchName());
			setPayableBank(remittanceTransaction.getBeneficaryBankName());
			
			//setRefundFor(remittanceTransaction.getSaleAmount().toString());
			setRefundFor(remittanceTransaction.getForeignTransactionAmount().toPlainString());
			
			setExchangeRate(remittanceTransaction.getExchangeRateApplied());
			setCommission(GetRound.roundBigDecimal(remittanceTransaction.getLocalCommisionAmount(), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
			setCharges(GetRound.roundBigDecimal(remittanceTransaction.getLocalChargeAmount(), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
			// setDeliveryCharges(remittanceTransaction.getLocalDeliveryAmount());
			if (remittanceTransaction.getLocalDeliveryAmount() != null)
				setDeliveryCharges(GetRound.roundBigDecimal(remittanceTransaction.getLocalDeliveryAmount(), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
			setRateAdjust(null);
			setOtherAdjust(null);
			
			setLocalCurrencyId(remittanceTransaction.getLocalTranxCurrencyId());
			setCalNetAmountPaid(GetRound.roundBigDecimal(remittanceTransaction.getLocalNetTranxAmount(), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));// .toPlainString();
			setPayNetPaidAmount(GetRound.roundBigDecimal(remittanceTransaction.getLocalTranxAmount(), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
			setNetRefund(GetRound.roundBigDecimal(remittanceTransaction.getLocalNetTranxAmount(), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
			setForeignCurrencyId(remittanceTransaction.getForeignCurrencyId());
			
			setAccPayee(null);
			setRemittanceDocumnetCode(remittanceTransaction.getDocumentCode());
			setRemittanceDocumnetId(remittanceTransaction.getDocumentId());
			//setLocalCurrnecyCommisionId(remittanceTransaction.getLocalCurrnecyCommisionId());
			setLocalCommisionAmount(remittanceTransaction.getLocalCommisionAmount());
		}
	}*/
	
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getCancellationStatus() {
		return cancellationStatus;
	}
	public void setCancellationStatus(String cancellationStatus) {
		this.cancellationStatus = cancellationStatus;
	}
	
}
