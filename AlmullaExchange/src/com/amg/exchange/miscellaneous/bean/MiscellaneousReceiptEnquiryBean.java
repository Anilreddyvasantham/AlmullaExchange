package com.amg.exchange.miscellaneous.bean;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.foreigncurrency.model.CollectDetail;
import com.amg.exchange.foreigncurrency.model.ViewReceiptPayment;
import com.amg.exchange.foreigncurrency.service.ForeignLocalCurrencyDenominationService;
import com.amg.exchange.foreigncurrency.service.IForeignCurrencyPurchaseService;
import com.amg.exchange.miscellaneous.model.Payment;
import com.amg.exchange.miscellaneous.service.IMiscellaneousReceiptPaymentService;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.remittance.bean.PersonalRemittanceCollectionDataTable;
import com.amg.exchange.remittance.model.CustomerBank;
import com.amg.exchange.remittance.model.DebitAutendicationView;
import com.amg.exchange.remittance.model.ViewBankDetails;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.GetRound;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("miscellaneousReceiptEnquiryBean")
@Scope("session")
public class MiscellaneousReceiptEnquiryBean implements Serializable {

	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger(MiscellaneousReceiptEnquiryBean.class);

	private BigDecimal documentCode;
	private BigDecimal documentYear;
	private BigDecimal documentNo;
	private Date documentDate;
	private BigDecimal transferRefYear;
	private BigDecimal transferNo;
	private Date transactionDate;
	private BigDecimal customerRef;
	private String customerName;
	private String telephone;
	private BigDecimal currencyId;
	private String currencyName;
	private BigDecimal commision;
	private BigDecimal charges;
	private BigDecimal deliveryCharge;
	private BigDecimal rateAdj;
	private BigDecimal otherAdj;
	private BigDecimal netAmount;
	private String remarks;
	private BigDecimal colchequebankId;
	private String colchequebankCode;
	private String colChequeRef;
	private Date colChequeDate;
	private String colChequeApprovalNo;
	private BigDecimal colCash;
	private Date effectiveMinDate = new Date();
	private String paymentCode;
	private BigDecimal totalDenominationCashEntered;
	private BigDecimal totalRefundCashEntered;
	private BigDecimal receiptPaymentPk;
	private BigDecimal customerId;
	private BigDecimal companyId;
	private BigDecimal totalKnetAmount;
	private BigDecimal totalCashAmount;
	private BigDecimal totalChequeAmount;
	private String status;
	private String emosId;

	private BigDecimal totalUamount = new BigDecimal(0);
	private String receiptType = "99";
	private String exceptionMessageForReport;

	private String colBankCode;
	private List<ViewBankDetails> bankMasterList = new ArrayList<ViewBankDetails>();
	private BigDecimal colCardNo;
	private BigDecimal colCardNoLength;
	private List<CustomerBank> lstDebitCard = new ArrayList<CustomerBank>();
	private String colNameofCard;
	private String colApprovalNo;
	private String colAuthorizedby;
	private List<DebitAutendicationView> empllist;
	private String colpassword;
	private String warningMessage;
	private BigDecimal documentYearId;
	private BigDecimal paymentModeId;
	private String branchName;

	private List<ViewReceiptPayment> receiptDocYearList;
	private List<ViewReceiptPayment> receiptDocNoList;
	private List<PersonalRemittanceCollectionDataTable> coldatatablevalues = new CopyOnWriteArrayList<PersonalRemittanceCollectionDataTable>();
	List<PersonalRemittanceCollectionDataTable> misDataTableList = new ArrayList<PersonalRemittanceCollectionDataTable>();
	private List<MiscelleneousReportBean> miscelleneousReportList;
	private JasperPrint jasperPrint;
	private String miscelleneousType;
	private String customerNameForReport;

	private Boolean renderChequePanel = false;
	private Boolean renderApproveButton = false;
	private Boolean renderNextButton = false;
	private Boolean renderDenominationDatatabe = false;
	private Boolean renderRefundDenominationDatatabe = false;
	private Boolean booRendercollectiondatatable = false;
	private Boolean renderPaymentModePanel = false;
	private Boolean renderDenominationApproveButton = false;
	private Boolean renderDenominationNextButton = false;
	private Boolean renderRefundDenominationApproveButton = false;
	private Boolean renderRefundDenominationNextButton = false;
	private Boolean directFromFirstPage = false;
	private Boolean booRenderMulDebit = false;
	private Boolean booRenderSingleDebit = true;
	private Boolean booRenderColDebitCard = false;
	private Boolean booAuthozed = false;
	private Boolean disableIfEdit = false;
	private Boolean renderKnetPanel = false;
	private Boolean booEdit = true;
	private Boolean booRenderBankTransfer = false;
	private Boolean miscePrint = false;
	private Boolean visableExceptionDailogForReceipt = false;
	private Boolean visibleDataTable = false;

	private String collectionMode;
	private String bankName;
	private BigDecimal chequeNo;
	private Date chequeDate;
	private BigDecimal approvalNo;
	private BigDecimal refundAmount;
	private String paymentMode;
	private BigDecimal totalCashEntered;
	private BigDecimal totalRefund;
	private String exceptionMessage;
	private String excheckCashLimitMessage;
	private BigDecimal errcolCashExistsLimit;
	private BigDecimal colamountKWD;
	private BigDecimal totalbalanceAmount;
	private BigDecimal toalUsedAmount;
	private String cyberPassword;

	private SessionStateManage sessionManage = new SessionStateManage();

	private BigDecimal colpaymentmodeId;
	private String colpaymentmodeName;
	private String colpaymentmodeCode;
	private BigDecimal populatedDebitCardNumber;
	private String knetIposReceipt;
	private String knetReceiptDate;
	private String knetReceiptTime;
	private String bankTransferApprovalNo;
	private String isActive;

	private HashMap<BigDecimal, String> paymentModeMap;
	private HashMap<String, Object> savingMap;

	@Autowired
	IMiscellaneousReceiptPaymentService<T> miscellaneousReceiptPaymentService;

	@Autowired
	IGeneralService<T> generalService;

	@Autowired
	ForeignLocalCurrencyDenominationService<T> foreignLocalCurrencyDenominationService;

	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;

	@Autowired
	IForeignCurrencyPurchaseService<T> iforeigncurrencyservice;

	/*
	 * Pagenavigation
	 */
	public void miscellanousreceiptEnquiryNavigation() {
		try {
			clear();
			clearAllMapObjects();
			setDocumentCode(null);
			setDocumentNo(null);
			setReceiptDocNoList(null);
			setReceiptDocYearList(null);
			setRenderPaymentModePanel(true);
			setRenderDenominationDatatabe(false);
			setRenderRefundDenominationDatatabe(false);
			setBooRendercollectiondatatable(false);
			setRenderChequePanel(false);
			setDirectFromFirstPage(false);
			setDisableIfEdit(false);
			setBankTransferApprovalNo(null);
			setBooRenderBankTransfer(false);
			bankMasterList.clear();
			setPaymentModeId(null);
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionManage.getCountryId(), sessionManage.getUserType(), sessionManage.getUserName(), "miscellaneousReceiptEnquiry.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../miscellaneous/miscellaneousReceiptEnquiry.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Clear the feilds.
	 */
	public void clear() {
		coldatatablevalues.clear();
		misDataTableList.clear();
		setCharges(null);
		setNetAmount(null);
		setOtherAdj(null);
		setCommision(null);
		setRateAdj(null);
		setCurrencyName(null);
		setCurrencyId(null);
		setCustomerRef(null);
		setCustomerName(null);
		setDeliveryCharge(null);
		setDocumentDate(null);
		setReceiptPaymentPk(null);
		setCustomerId(null);
		setRemarks(null);
		setTelephone(null);
		setTransactionDate(null);
		setPaymentModeId(null);
		setTransferRefYear(null);
		setTransferNo(null);
		setColCash(null);
		setTotalRefund(null);
		setTotalCashEntered(null);
		setTotalDenominationCashEntered(null);
		setTotalRefundCashEntered(null);
		setCompanyId(null);
		setStatus(null);
		setEmosId(null);
		setBranchName(null);
		setCustomerNameForReport(null);
		setVisibleDataTable(false);
		setMiscePrint(false);
	}

	public void clearAllMapObjects() {
		if (paymentModeMap != null) {
			paymentModeMap.clear();
		}
		if (savingMap != null) {
			savingMap.clear();
		}
	}

	public void changeDocumentNo() {
		if (getDocumentCode().equals(new BigDecimal(0))) {
			clearFeilds();
		} else {
			setReceiptDocYearList(null);
			setReceiptDocNoList(null);
			setPaymentModeId(null);
			setDocumentYear(null);
			setDocumentNo(null);
			clear();
			setRenderPaymentModePanel(true);
			setRenderDenominationDatatabe(false);
			setRenderRefundDenominationDatatabe(false);
			setBooRendercollectiondatatable(false);
			setRenderChequePanel(false);
			setDirectFromFirstPage(false);
			setBooRenderColDebitCard(false);
			setBooRenderBankTransfer(false);
			setDisableIfEdit(false);
			// List<ViewReceiptPayment> receiptDocList =
			// miscellaneousReceiptPaymentService.getAllDocumentYearFromView(getDocumentCode(),getDocumentYear());
			// setReceiptDocYearList(receiptDocList);
		}
	}

	public void fectchDocumentNumbers() {
		setReceiptDocNoList(null);
		setDocumentNo(null);
		setPaymentModeId(null);
		clear();
		/*
		 * List<ViewReceiptPayment> receiptDocList =
		 * miscellaneousReceiptPaymentService
		 * .getAllDocumentNumbersFromView(getDocumentCode(),getDocumentYear());
		 * if (receiptDocList.size() > 0) { setReceiptDocNoList(receiptDocList);
		 * }
		 */
	}

	public void fetchDocumentData() {
		try {
			if ((getDocumentCode() == null || getDocumentCode().compareTo(BigDecimal.ZERO) == 0) || (getDocumentYear() == null || getDocumentYear().compareTo(BigDecimal.ZERO) == 0)) {
				if (getDocumentCode() == null || getDocumentCode().compareTo(BigDecimal.ZERO) == 0) {
					RequestContext.getCurrentInstance().execute("recOrpayment.show();");
				} else if (getDocumentYear() == null || getDocumentYear().compareTo(BigDecimal.ZERO) == 0) {
					RequestContext.getCurrentInstance().execute("docYear.show();");
				}
			} else {
				clear();
				setRenderPaymentModePanel(true);
				setRenderDenominationDatatabe(false);
				setRenderRefundDenominationDatatabe(false);
				setBooRendercollectiondatatable(false);
				setRenderChequePanel(false);
				setDirectFromFirstPage(false);
				setDisableIfEdit(false);
				List<ViewReceiptPayment> paymentDetailsList = miscellaneousReceiptPaymentService.viewReceiptPaymentForUpdate(sessionManage.getCountryId(), sessionManage.getCompanyId(),
						getDocumentCode(), getDocumentYear(), getDocumentNo());

				if (paymentDetailsList != null && paymentDetailsList.size() > 0) {
					ViewReceiptPayment receiptObj = paymentDetailsList.get(0);
					if (receiptObj.getIsActive() != null
							&& (receiptObj.getIsActive().equalsIgnoreCase(Constants.Yes) || receiptObj.getIsActive().equalsIgnoreCase(Constants.U) || receiptObj.getIsActive().equalsIgnoreCase(
									Constants.D))) {

						setIsActive(receiptObj.getIsActive());
						setReceiptPaymentPk(receiptObj.getReceiptId());
						setCustomerRef(receiptObj.getCustomerReference());
						setCurrencyId(new BigDecimal(sessionManage.getCurrencyId()));
						setRemarks(receiptObj.getRemarks());
						setDocumentYear(receiptObj.getDocumentFinanceYear());
						if (receiptObj.getIsActive().equalsIgnoreCase(Constants.Yes)) {
							setDocumentDate(receiptObj.getModifiedDate());
						} else {
							setDocumentDate(receiptObj.getDocumentDate());
						}
						setTransferRefYear(receiptObj.getTransferFinanceYear());
						setTransactionDate(receiptObj.getAccountMMYYYY());
						setTransferNo(receiptObj.getTransferReference());
						setCustomerName(receiptObj.getCustomerName());
						if (getTransferNo() != null && getTransactionDate() != null) {
							setTransactionDate(receiptObj.getDocumentDate());
						}
						setRemarks(receiptObj.getRemarks());
						setCompanyId(receiptObj.getFsCompanyMaster());
						if (receiptObj.getIsActive() != null && receiptObj.getIsActive().equalsIgnoreCase(Constants.Yes)) {
							setStatus(Constants.APPROVED);
						} else if (receiptObj.getIsActive() != null && receiptObj.getIsActive().equalsIgnoreCase(Constants.U)) {
							setStatus(Constants.NOTAPPROVED);
						} else if (receiptObj.getIsActive() != null && receiptObj.getIsActive().equalsIgnoreCase(Constants.D)) {
							setStatus("Cancelled");
						}

						if (receiptObj.getIsActive().equalsIgnoreCase(Constants.Yes)) {
							setEmosId(receiptObj.getModifiedBy());
						} else {
							setEmosId(receiptObj.getCreatedBy());
						}
						/*
						 * List<CountryBranch> lstCountryBranch =
						 * generalService.
						 * getBranchDetailsForToLocation(receiptObj
						 * .getFsCountryMaster(),receiptObj.getCountryBranch());
						 * if(lstCountryBranch!=null &&
						 * lstCountryBranch.size()>0) { CountryBranch
						 * countryBranch =lstCountryBranch.get(0);
						 * setBranchName(countryBranch.getBranchId()+" - "
						 * +countryBranch.getBranchName()); }
						 */

						String location = iforeigncurrencyservice.getBranchName(receiptObj.getCountryBranch());
						if (location != null) {
							setBranchName(location);
						}

						if (receiptObj.getCustomerReference() != null) {
							List<Customer> customerList = miscellaneousReceiptPaymentService.getCustomerDetails(receiptObj.getCustomerReference());
							if (customerList != null && customerList.size() > 0) {
								Customer customer = customerList.get(0);
								setCustomerRef(customer.getCustomerReference());
								setTelephone(customer.getMobile());
								setCustomerId(customer.getCustomerId());

								StringBuffer customerName = new StringBuffer();
								if (customer.getCustomerReference() != null) {
									customerName.append(customer.getCustomerReference());
									customerName.append(" / ");
								}
								if (customer.getFirstName() != null) {
									customerName.append(customer.getFirstName());
								}
								if (customer.getMiddleName() != null) {
									customerName.append(" ");
									customerName.append(customer.getMiddleName());
								}
								if (customer.getLastName() != null) {
									customerName.append(" ");
									customerName.append(customer.getLastName());
								}

								if (customer.getMobile() != null) {
									customerName.append(", Tel :");
									customerName.append(customer.getMobile());
								}
								setCustomerNameForReport(customerName.toString());
							}
						}

						if (getCurrencyId() != null) {
							String currenyName = generalService.getCurrencyName(getCurrencyId());
							setCurrencyName(currenyName);
						}

						BigDecimal commision = BigDecimal.ZERO;
						BigDecimal charges = BigDecimal.ZERO;
						BigDecimal deliverCharge = BigDecimal.ZERO;
						BigDecimal rateAdjust = BigDecimal.ZERO;
						BigDecimal otherAdjust = BigDecimal.ZERO;
						BigDecimal netAmount = BigDecimal.ZERO;

						commision = receiptObj.getLocalCommisionAmoumnt() == null ? BigDecimal.ZERO : receiptObj.getLocalCommisionAmoumnt();
						charges = receiptObj.getLocalChargeAmount() == null ? BigDecimal.ZERO : receiptObj.getLocalChargeAmount();
						deliverCharge = receiptObj.getLocalDeliveryAmount() == null ? BigDecimal.ZERO : receiptObj.getLocalDeliveryAmount();
						rateAdjust = receiptObj.getLocalRateAmount() == null ? BigDecimal.ZERO : receiptObj.getLocalRateAmount();
						otherAdjust = receiptObj.getLocalOtherAdjAmount() == null ? BigDecimal.ZERO : receiptObj.getLocalOtherAdjAmount();
						netAmount = receiptObj.getLocalNetAmount() == null ? BigDecimal.ZERO : receiptObj.getLocalNetAmount();

						commision = GetRound.roundBigDecimal((commision), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(getCurrencyId()));
						charges = GetRound.roundBigDecimal((charges), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(getCurrencyId()));
						deliverCharge = GetRound.roundBigDecimal((deliverCharge), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(getCurrencyId()));
						rateAdjust = GetRound.roundBigDecimal((rateAdjust), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(getCurrencyId()));
						otherAdjust = GetRound.roundBigDecimal((otherAdjust), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(getCurrencyId()));
						netAmount = GetRound.roundBigDecimal((netAmount), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(getCurrencyId()));

						setCommision(commision);
						setCharges(charges);
						setDeliveryCharge(deliverCharge);
						setRateAdj(rateAdjust);
						setOtherAdj(otherAdjust);
						setNetAmount(netAmount);
						if (receiptObj.getIsActive().equalsIgnoreCase(Constants.Yes)) {
							setVisibleDataTable(true);
						} else {
							setVisibleDataTable(false);
						}
						if (getDocumentCode().equals(new BigDecimal(9))) {
							addPaymentModeRecord();
						} else {
							addReceiveModeRecord();
						}
						setMiscePrint(true);
					}
				} else {

					RequestContext.getCurrentInstance().execute("warningDailog1.show();");
					setDocumentNo(null);
					return;
				}
			}

		} catch (Exception e) {
			setWarningMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("warningDailog.show();");
		}

	}

	public void addPaymentModeRecord() {

		List<Payment> lstPaymentDetail = miscellaneousReceiptPaymentService.getPaymentDeatils(getDocumentNo(), getDocumentYear(), getDocumentCode(), getReceiptType(), sessionManage.getCompanyId());

		for (Payment paymentDetail : lstPaymentDetail) {
			PersonalRemittanceCollectionDataTable personalcollectionDT = new PersonalRemittanceCollectionDataTable();
			personalcollectionDT.setColpaymentmodeIDtypeDT(paymentDetail.getPaymentId());

			String paymentDesc = miscellaneousReceiptPaymentService.paymentModeDescription(paymentDetail.getPaymentmode(),
					new BigDecimal(sessionManage.isExists("languageId") ? sessionManage.getSessionValue("languageId") : "1"));
			String bankDesc = miscellaneousReceiptPaymentService.bankDescription(paymentDetail.getChequeBankCode());

			if (paymentDetail.getPaymentmode().equalsIgnoreCase(Constants.CashCode)) {
				personalcollectionDT.setColpaymentmodetypeDT(paymentDesc);
				personalcollectionDT.setColAmountDT(paymentDetail.getPaidAmount());
			}
			if (paymentDetail.getPaymentmode().equalsIgnoreCase(Constants.BankTransferCode)) {
				personalcollectionDT.setColpaymentmodetypeDT(paymentDesc);
				personalcollectionDT.setColbankNameDT(bankDesc);
				personalcollectionDT.setColAmountDT(paymentDetail.getPaidAmount());
				personalcollectionDT.setColApprovalNo(paymentDetail.getApprovalNo());
			}
			misDataTableList.add(personalcollectionDT);
			setColdatatablevalues(misDataTableList);
		}
	}

	public void addReceiveModeRecord() {

		List<CollectDetail> lstCollectionDetail = miscellaneousReceiptPaymentService.getBeanCorrespondingBankList(getDocumentNo(), getDocumentYear(), getDocumentCode(), sessionManage.getCompanyId());

		for (CollectDetail collectDetail : lstCollectionDetail) {
			PersonalRemittanceCollectionDataTable personalcollectionDT = new PersonalRemittanceCollectionDataTable();
			personalcollectionDT.setColpaymentmodeIDtypeDT(collectDetail.getPaymentModeId());

			String paymentDesc = miscellaneousReceiptPaymentService.paymentModeDescription(collectDetail.getCollectionMode(),
					new BigDecimal(sessionManage.isExists("languageId") ? sessionManage.getSessionValue("languageId") : "1"));
			String bankDesc = miscellaneousReceiptPaymentService.bankDescription(collectDetail.getChequeBankRef());

			if (collectDetail.getCollectionMode().equalsIgnoreCase(Constants.CashCode)) {
				personalcollectionDT.setColpaymentmodetypeDT(paymentDesc);
				personalcollectionDT.setColAmountDT(collectDetail.getCollAmt());
			}

			if (collectDetail.getCollectionMode().equalsIgnoreCase(Constants.BankTransferCode)) {
				personalcollectionDT.setColpaymentmodetypeDT(paymentDesc);
				personalcollectionDT.setColbankNameDT(bankDesc);
				personalcollectionDT.setColAmountDT(collectDetail.getCollAmt());
				personalcollectionDT.setColApprovalNo(collectDetail.getApprovalNo());
			}

			if (collectDetail.getCollectionMode().equalsIgnoreCase(Constants.ChequeCode)) {
				personalcollectionDT.setColpaymentmodetypeDT(paymentDesc);
				personalcollectionDT.setColbankNameDT(bankDesc);
				personalcollectionDT.setColchequeRefNo(collectDetail.getChequeRef());
				personalcollectionDT.setColchequeDate(collectDetail.getChequeDate());
				personalcollectionDT.setColAmountDT(collectDetail.getCollAmt());
				personalcollectionDT.setColApprovalNo(collectDetail.getApprovalNo());
			}

			if (collectDetail.getCollectionMode().equalsIgnoreCase(Constants.KNETCode)) {
				personalcollectionDT.setColpaymentmodetypeDT(paymentDesc);
				personalcollectionDT.setColbankNameDT(bankDesc);
				personalcollectionDT.setColNameofCardDT(collectDetail.getDbCardName());
				personalcollectionDT.setColAmountDT(collectDetail.getCollAmt());
				// personalcollectionDT.setColCardNumberDT(new
				// BigDecimal(collectDetail.getDebitCard()));
				personalcollectionDT.setColCardNum(collectDetail.getDebitCard());
				personalcollectionDT.setColApprovalNo(collectDetail.getApprovalNo());
			}
			misDataTableList.add(personalcollectionDT);
			setColdatatablevalues(misDataTableList);
		}
	}

	public void clearFeilds() {
		coldatatablevalues.clear();
		misDataTableList.clear();
		setDocumentCode(null);
		setDocumentNo(null);
		setReceiptDocYearList(null);
		setCharges(null);
		setNetAmount(null);
		setOtherAdj(null);
		setCommision(null);
		setRateAdj(null);
		setCurrencyName(null);
		setCurrencyId(null);
		setCustomerRef(null);
		setCustomerName(null);
		setDeliveryCharge(null);
		setDocumentDate(null);
		setReceiptPaymentPk(null);
		setCustomerId(null);
		setRemarks(null);
		setTelephone(null);
		setTransactionDate(null);
		setPaymentModeId(null);
		setTransferRefYear(null);
		setTransferNo(null);
		setColCash(null);
		setTotalRefund(null);
		setTotalCashEntered(null);
		setTotalDenominationCashEntered(null);
		setTotalRefundCashEntered(null);
		setCompanyId(null);
		setMiscePrint(false);
		setStatus(null);
		setEmosId(null);
		setBranchName(null);
		setCustomerNameForReport(null);
		setVisibleDataTable(false);
		setMiscePrint(false);
	}

	public void clickOnExit() throws IOException {
		setMiscePrint(false);
		setVisibleDataTable(false);
		if (sessionManage.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}
	}

	public void generateMiscelleneousEnquiryReport() throws IOException {
		setVisableExceptionDailogForReceipt(false);
		setExceptionMessageForReport(null);
		OutputStream outstream = null;
		try {
			fetchDataforReport();
			JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(miscelleneousReportList);
			ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
			// String realPath = ctx.getRealPath("//");
			// String reportPath = realPath
			// +"\\reports\\design\\miscpaymentrequest.jasper"; // only for
			// windows

			String realPath = ctx.getRealPath("//");
			String reportPath = realPath + "//reports//design//miscpaymentrequest.jasper";

			jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap(), beanCollectionDataSource);

			HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			httpServletResponse.setHeader("Cache-Control", "cache, must-revalidate");
			httpServletResponse.addHeader("Content-disposition", "attachment; filename=Miscelleneous" + getMiscelleneousType() + ".pdf");

			byte[] pdfasbytes = JasperExportManager.exportReportToPdf(jasperPrint);

			outstream = httpServletResponse.getOutputStream();
			httpServletResponse.setContentType("application/pdf");
			httpServletResponse.setContentLength(pdfasbytes.length);
			String strSettings = "inline;filename=\"Miscelleneous" + getMiscelleneousType() + ".pdf\"";
			httpServletResponse.setHeader("Content-Disposition", strSettings);
			outstream.write(pdfasbytes, 0, pdfasbytes.length);
			outstream.flush();
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception ecception) {
			setExceptionMessageForReport(null);
			setVisableExceptionDailogForReceipt(true);
			if (ecception.getMessage() != null) {
				setExceptionMessageForReport(ecception.getMessage());
			} else {
				setExceptionMessageForReport("Exception  :" + ecception);
			}
			RequestContext.getCurrentInstance().execute("exceptioninReceipt.show();");
		} finally {
			if (outstream != null) {
				outstream.close();
			}
		}
	}

	public void fetchDataforReport() {
		miscelleneousReportList = new ArrayList<MiscelleneousReportBean>();

		MiscelleneousReportBean misceObj = new MiscelleneousReportBean();

		List<CompanyMasterDesc> companyDesc = generalService.getCompanyList(sessionManage.getCompanyId(), sessionManage.getLanguageId());
		CompanyMasterDesc companyDesObj = companyDesc.get(0);

		StringBuffer companyAdds = new StringBuffer();
		if (companyDesObj.getFsCompanyMaster().getAddress1() != null) {
			companyAdds.append(companyDesObj.getFsCompanyMaster().getAddress1());
		}
		if (companyDesObj.getFsCompanyMaster().getAddress2() != null) {
			companyAdds.append(", ");
			companyAdds.append(companyDesObj.getFsCompanyMaster().getAddress2());
		}
		if (companyDesObj.getFsCompanyMaster().getAddress3() != null) {
			companyAdds.append(", ");
			companyAdds.append(companyDesObj.getFsCompanyMaster().getAddress3());
		}

		// set company share capital
		if (companyDesObj.getFsCompanyMaster().getCapitalAmount() != null && companyDesObj.getFsCompanyMaster().getCurrencyId() != null) {
			String currencyQuoteName = generalService.getCurrencyQuote(companyDesObj.getFsCompanyMaster().getCurrencyId());
			Integer d = Integer.parseInt(companyDesObj.getFsCompanyMaster().getCapitalAmount());
			NumberFormat format = NumberFormat.getNumberInstance(new Locale("en", "in"));
			String capitalAmount = format.format(d).toString();
			companyAdds.append(", Share Capital :");
			companyAdds.append(currencyQuoteName + " " + capitalAmount);
		}

		misceObj.setCompanyAddress(companyAdds.toString());
		misceObj.setCompanyName(companyDesObj.getCompanyName());
		if (getDocumentCode() != null) {
			if (getDocumentCode().equals("9")) {
				setMiscelleneousType("Payment");
			} else {
				setMiscelleneousType("Receive");
			}
		}
		misceObj.setMiscellinoiusReportType("Miscelleneous " + getMiscelleneousType());

		StringBuffer paymentno = new StringBuffer();
		if (getDocumentYear() != null) {
			paymentno.append(getDocumentYear());
		}
		if (getDocumentNo() != null) {
			paymentno.append(" / ");
			paymentno.append(getDocumentNo());
		}
		misceObj.setPaymentNo(paymentno.toString());

		StringBuffer transaNo = new StringBuffer();
		if (getTransferRefYear() != null) {
			transaNo.append(getTransferRefYear());
		}
		if (getTransferNo() != null) {
			transaNo.append(" / ");
			transaNo.append(getTransferNo());
		}
		misceObj.setRefundRefeNo(transaNo.toString());
		misceObj.setCustomerDetails(getCustomerNameForReport());
		misceObj.setRemarks(getRemarks());
		String currencyQuoteName = generalService.getCurrencyQuote(new BigDecimal(sessionManage.getCurrencyId()));
		misceObj.setCurCode(currencyQuoteName);

		BigDecimal commission = BigDecimal.ZERO;
		BigDecimal charges = BigDecimal.ZERO;
		BigDecimal delvCharges = BigDecimal.ZERO;
		BigDecimal rateAdjust = BigDecimal.ZERO;
		BigDecimal otherAdjust = BigDecimal.ZERO;
		BigDecimal netPayment = BigDecimal.ZERO;

		if (getCommision() != null) {
			commission = GetRound.roundBigDecimal((getCommision()), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId())));
		} else {
			commission = GetRound.roundBigDecimal((BigDecimal.ZERO), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId())));
		}
		if (getCharges() != null) {
			charges = GetRound.roundBigDecimal((getCharges()), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId())));
		} else {
			charges = GetRound.roundBigDecimal((BigDecimal.ZERO), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId())));
		}
		if (getDeliveryCharge() != null) {
			delvCharges = GetRound.roundBigDecimal((getDeliveryCharge()), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId())));
		} else {
			delvCharges = GetRound.roundBigDecimal((BigDecimal.ZERO), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId())));
		}
		if (getRateAdj() != null) {
			rateAdjust = GetRound.roundBigDecimal((getRateAdj()), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId())));
		} else {
			rateAdjust = GetRound.roundBigDecimal((BigDecimal.ZERO), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId())));
		}
		if (getOtherAdj() != null) {
			otherAdjust = GetRound.roundBigDecimal((getOtherAdj()), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId())));
		} else {
			otherAdjust = GetRound.roundBigDecimal((BigDecimal.ZERO), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId())));
		}
		if (getNetAmount() != null) {
			netPayment = GetRound.roundBigDecimal((getNetAmount()), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId())));
		} else {
			netPayment = GetRound.roundBigDecimal((BigDecimal.ZERO), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId())));
		}
		misceObj.setCommission("*********" + commission.toString());
		misceObj.setCharges("*********" + charges.toString());
		misceObj.setDelvCharges("*********" + delvCharges.toString());
		misceObj.setRateAdjust("*********" + rateAdjust.toString());
		misceObj.setOtherAdjust("*********" + otherAdjust.toString());
		misceObj.setNetPayment("*********" + netPayment.toString());

		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath = ctx.getRealPath("//");
		// String logoPath = realPath + Constants.LOGO_PATH;

		String logoPath = null;
		if (sessionManage.getCountryName().equalsIgnoreCase(Constants.KUWAIT)) {
			logoPath = realPath + Constants.LOGO_PATH;
		} else if (sessionManage.getCountryName().equalsIgnoreCase(Constants.OMAN)) {
			logoPath = realPath + Constants.LOGO_PATH_OM;
		} else {
			logoPath = realPath + Constants.LOGO_PATH;
		}

		misceObj.setLogoPath(logoPath);
		miscelleneousReportList.add(misceObj);

	}

	public void generateMiscelleneousEnquiryReportOffice() throws IOException {
		setVisableExceptionDailogForReceipt(false);
		setExceptionMessageForReport(null);
		OutputStream outstream = null;
		try {
			fetchDataforReportForOffice();
			JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(miscelleneousReportList);
			ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
			String realPath = ctx.getRealPath("//");
			// String reportPath = realPath
			// +"\\reports\\design\\miscpaymentrequestoffice.jasper";
			String reportPath = realPath + "//reports//design//miscpaymentrequestoffice.jasper";
			jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap(), beanCollectionDataSource);

			HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			httpServletResponse.setHeader("Cache-Control", "cache, must-revalidate");
			httpServletResponse.addHeader("Content-disposition", "attachment; filename=Miscelleneous" + getMiscelleneousType() + ".pdf");

			byte[] pdfasbytes = JasperExportManager.exportReportToPdf(jasperPrint);

			outstream = httpServletResponse.getOutputStream();
			httpServletResponse.setContentType("application/pdf");
			httpServletResponse.setContentLength(pdfasbytes.length);
			String strSettings = "inline;filename=\"Miscelleneous" + getMiscelleneousType() + ".pdf\"";
			httpServletResponse.setHeader("Content-Disposition", strSettings);
			outstream.write(pdfasbytes, 0, pdfasbytes.length);
			outstream.flush();
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception ecception) {
			setExceptionMessageForReport(null);
			setVisableExceptionDailogForReceipt(true);
			if (ecception.getMessage() != null) {
				setExceptionMessageForReport(ecception.getMessage());
			} else {
				setExceptionMessageForReport("Exception  :" + ecception);
			}
			RequestContext.getCurrentInstance().execute("exceptioninReceipt.show();");
		} finally {
			if (outstream != null) {
				outstream.close();
			}
		}
	}

	public void fetchDataforReportForOffice() {
		miscelleneousReportList = new ArrayList<MiscelleneousReportBean>();

		MiscelleneousReportBean misceObj = new MiscelleneousReportBean();

		List<CompanyMasterDesc> companyDesc = generalService.getCompanyList(sessionManage.getCompanyId(), sessionManage.getLanguageId());
		CompanyMasterDesc companyDesObj = companyDesc.get(0);

		StringBuffer companyAdds = new StringBuffer();
		if (companyDesObj.getFsCompanyMaster().getAddress1() != null) {
			companyAdds.append(companyDesObj.getFsCompanyMaster().getAddress1());
		}
		if (companyDesObj.getFsCompanyMaster().getAddress2() != null) {
			companyAdds.append(", ");
			companyAdds.append(companyDesObj.getFsCompanyMaster().getAddress2());
		}
		if (companyDesObj.getFsCompanyMaster().getAddress3() != null) {
			companyAdds.append(", ");
			companyAdds.append(companyDesObj.getFsCompanyMaster().getAddress3());
		}

		// set company share capital
		if (companyDesObj.getFsCompanyMaster().getCapitalAmount() != null && companyDesObj.getFsCompanyMaster().getCurrencyId() != null) {
			String currencyQuoteName = generalService.getCurrencyQuote(companyDesObj.getFsCompanyMaster().getCurrencyId());
			Integer d = Integer.parseInt(companyDesObj.getFsCompanyMaster().getCapitalAmount());
			NumberFormat format = NumberFormat.getNumberInstance(new Locale("en", "in"));
			String capitalAmount = format.format(d).toString();
			companyAdds.append(", Share Capital :");
			companyAdds.append(currencyQuoteName + " " + capitalAmount);
		}

		misceObj.setCompanyAddress(companyAdds.toString());
		misceObj.setCompanyName(companyDesObj.getCompanyName());
		if (getDocumentCode() != null) {
			if (getDocumentCode().equals("9")) {
				setMiscelleneousType("Payment");
			} else {
				setMiscelleneousType("Receive");
			}
		}
		misceObj.setMiscellinoiusReportType("Miscelleneous " + getMiscelleneousType());

		StringBuffer paymentno = new StringBuffer();
		if (getDocumentYear() != null) {
			paymentno.append(getDocumentYear());
		}
		if (getDocumentNo() != null) {
			paymentno.append(" / ");
			paymentno.append(getDocumentNo());
		}
		misceObj.setPaymentNo(paymentno.toString());

		StringBuffer transaNo = new StringBuffer();
		if (getTransferRefYear() != null) {
			transaNo.append(getTransferRefYear());
		}
		if (getTransferNo() != null) {
			transaNo.append(" / ");
			transaNo.append(getTransferNo());
		}
		misceObj.setRefundRefeNo(transaNo.toString());
		misceObj.setCustomerDetails(getCustomerNameForReport());
		misceObj.setRemarks(getRemarks());
		String currencyQuoteName = generalService.getCurrencyQuote(new BigDecimal(sessionManage.getCurrencyId()));
		misceObj.setCurCode(currencyQuoteName);

		BigDecimal commission = BigDecimal.ZERO;
		BigDecimal charges = BigDecimal.ZERO;
		BigDecimal delvCharges = BigDecimal.ZERO;
		BigDecimal rateAdjust = BigDecimal.ZERO;
		BigDecimal otherAdjust = BigDecimal.ZERO;
		BigDecimal netPayment = BigDecimal.ZERO;

		if (getCommision() != null) {
			commission = GetRound.roundBigDecimal((getCommision()), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId())));
		} else {
			commission = GetRound.roundBigDecimal((BigDecimal.ZERO), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId())));
		}
		if (getCharges() != null) {
			charges = GetRound.roundBigDecimal((getCharges()), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId())));
		} else {
			charges = GetRound.roundBigDecimal((BigDecimal.ZERO), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId())));
		}
		if (getDeliveryCharge() != null) {
			delvCharges = GetRound.roundBigDecimal((getDeliveryCharge()), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId())));
		} else {
			delvCharges = GetRound.roundBigDecimal((BigDecimal.ZERO), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId())));
		}
		if (getRateAdj() != null) {
			rateAdjust = GetRound.roundBigDecimal((getRateAdj()), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId())));
		} else {
			rateAdjust = GetRound.roundBigDecimal((BigDecimal.ZERO), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId())));
		}
		if (getOtherAdj() != null) {
			otherAdjust = GetRound.roundBigDecimal((getOtherAdj()), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId())));
		} else {
			otherAdjust = GetRound.roundBigDecimal((BigDecimal.ZERO), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId())));
		}
		if (getNetAmount() != null) {
			netPayment = GetRound.roundBigDecimal((getNetAmount()), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId())));
		} else {
			netPayment = GetRound.roundBigDecimal((BigDecimal.ZERO), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId())));
		}
		misceObj.setCommission("*********" + commission.toString());
		misceObj.setCharges("*********" + charges.toString());
		misceObj.setDelvCharges("*********" + delvCharges.toString());
		misceObj.setRateAdjust("*********" + rateAdjust.toString());
		misceObj.setOtherAdjust("*********" + otherAdjust.toString());
		misceObj.setNetPayment("*********" + netPayment.toString());

		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath = ctx.getRealPath("//");
		// String logoPath = realPath + Constants.LOGO_PATH;

		String logoPath = null;
		if (sessionManage.getCountryName().equalsIgnoreCase(Constants.KUWAIT)) {
			logoPath = realPath + Constants.LOGO_PATH;
		} else if (sessionManage.getCountryName().equalsIgnoreCase(Constants.OMAN)) {
			logoPath = realPath + Constants.LOGO_PATH_OM;
		} else {
			logoPath = realPath + Constants.LOGO_PATH;
		}

		misceObj.setLogoPath(logoPath);
		miscelleneousReportList.add(misceObj);

	}

	// Getters and setters.

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public BigDecimal getDocumentCode() {
		return documentCode;
	}

	public void setDocumentCode(BigDecimal documentCode) {
		this.documentCode = documentCode;
	}

	public BigDecimal getDocumentYear() {
		return documentYear;
	}

	public void setDocumentYear(BigDecimal documentYear) {
		this.documentYear = documentYear;
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

	public BigDecimal getTransferRefYear() {
		return transferRefYear;
	}

	public void setTransferRefYear(BigDecimal transferRefYear) {
		this.transferRefYear = transferRefYear;
	}

	public BigDecimal getTransferNo() {
		return transferNo;
	}

	public void setTransferNo(BigDecimal transferNo) {
		this.transferNo = transferNo;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
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

	public BigDecimal getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public BigDecimal getCommision() {
		return commision;
	}

	public void setCommision(BigDecimal commision) {
		this.commision = commision;
	}

	public BigDecimal getCharges() {
		return charges;
	}

	public void setCharges(BigDecimal charges) {
		this.charges = charges;
	}

	public BigDecimal getDeliveryCharge() {
		return deliveryCharge;
	}

	public void setDeliveryCharge(BigDecimal deliveryCharge) {
		this.deliveryCharge = deliveryCharge;
	}

	public BigDecimal getRateAdj() {
		return rateAdj;
	}

	public void setRateAdj(BigDecimal rateAdj) {
		this.rateAdj = rateAdj;
	}

	public BigDecimal getOtherAdj() {
		return otherAdj;
	}

	public void setOtherAdj(BigDecimal otherAdj) {
		this.otherAdj = otherAdj;
	}

	public BigDecimal getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(BigDecimal netAmount) {
		this.netAmount = netAmount;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public BigDecimal getColchequebankId() {
		return colchequebankId;
	}

	public void setColchequebankId(BigDecimal colchequebankId) {
		this.colchequebankId = colchequebankId;
	}

	public String getColchequebankCode() {
		return colchequebankCode;
	}

	public void setColchequebankCode(String colchequebankCode) {
		this.colchequebankCode = colchequebankCode;
	}

	public String getColChequeRef() {
		return colChequeRef;
	}

	public void setColChequeRef(String colChequeRef) {
		this.colChequeRef = colChequeRef;
	}

	public Date getColChequeDate() {
		return colChequeDate;
	}

	public void setColChequeDate(Date colChequeDate) {
		this.colChequeDate = colChequeDate;
	}

	public String getColChequeApprovalNo() {
		return colChequeApprovalNo;
	}

	public void setColChequeApprovalNo(String colChequeApprovalNo) {
		this.colChequeApprovalNo = colChequeApprovalNo;
	}

	public BigDecimal getColCash() {
		return colCash;
	}

	public void setColCash(BigDecimal colCash) {
		this.colCash = colCash;
	}

	public Date getEffectiveMinDate() {
		return effectiveMinDate;
	}

	public void setEffectiveMinDate(Date effectiveMinDate) {
		this.effectiveMinDate = effectiveMinDate;
	}

	public String getPaymentCode() {
		return paymentCode;
	}

	public void setPaymentCode(String paymentCode) {
		this.paymentCode = paymentCode;
	}

	public BigDecimal getTotalDenominationCashEntered() {
		return totalDenominationCashEntered;
	}

	public void setTotalDenominationCashEntered(BigDecimal totalDenominationCashEntered) {
		this.totalDenominationCashEntered = totalDenominationCashEntered;
	}

	public BigDecimal getTotalRefundCashEntered() {
		return totalRefundCashEntered;
	}

	public void setTotalRefundCashEntered(BigDecimal totalRefundCashEntered) {
		this.totalRefundCashEntered = totalRefundCashEntered;
	}

	public BigDecimal getReceiptPaymentPk() {
		return receiptPaymentPk;
	}

	public void setReceiptPaymentPk(BigDecimal receiptPaymentPk) {
		this.receiptPaymentPk = receiptPaymentPk;
	}

	public BigDecimal getCustomerId() {
		return customerId;
	}

	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	public BigDecimal getCompanyId() {
		return companyId;
	}

	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}

	public BigDecimal getTotalKnetAmount() {
		return totalKnetAmount;
	}

	public void setTotalKnetAmount(BigDecimal totalKnetAmount) {
		this.totalKnetAmount = totalKnetAmount;
	}

	public BigDecimal getTotalCashAmount() {
		return totalCashAmount;
	}

	public void setTotalCashAmount(BigDecimal totalCashAmount) {
		this.totalCashAmount = totalCashAmount;
	}

	public BigDecimal getTotalChequeAmount() {
		return totalChequeAmount;
	}

	public void setTotalChequeAmount(BigDecimal totalChequeAmount) {
		this.totalChequeAmount = totalChequeAmount;
	}

	public BigDecimal getTotalUamount() {
		return totalUamount;
	}

	public void setTotalUamount(BigDecimal totalUamount) {
		this.totalUamount = totalUamount;
	}

	public String getColBankCode() {
		return colBankCode;
	}

	public void setColBankCode(String colBankCode) {
		this.colBankCode = colBankCode;
	}

	public List<ViewBankDetails> getBankMasterList() {
		return bankMasterList;
	}

	public void setBankMasterList(List<ViewBankDetails> bankMasterList) {
		this.bankMasterList = bankMasterList;
	}

	public BigDecimal getColCardNo() {
		return colCardNo;
	}

	public void setColCardNo(BigDecimal colCardNo) {
		this.colCardNo = colCardNo;
	}

	public BigDecimal getColCardNoLength() {
		return colCardNoLength;
	}

	public void setColCardNoLength(BigDecimal colCardNoLength) {
		this.colCardNoLength = colCardNoLength;
	}

	public List<CustomerBank> getLstDebitCard() {
		return lstDebitCard;
	}

	public void setLstDebitCard(List<CustomerBank> lstDebitCard) {
		this.lstDebitCard = lstDebitCard;
	}

	public String getColNameofCard() {
		return colNameofCard;
	}

	public void setColNameofCard(String colNameofCard) {
		this.colNameofCard = colNameofCard;
	}

	public String getColApprovalNo() {
		return colApprovalNo;
	}

	public void setColApprovalNo(String colApprovalNo) {
		this.colApprovalNo = colApprovalNo;
	}

	public String getColAuthorizedby() {
		return colAuthorizedby;
	}

	public void setColAuthorizedby(String colAuthorizedby) {
		this.colAuthorizedby = colAuthorizedby;
	}

	public List<DebitAutendicationView> getEmpllist() {
		return empllist;
	}

	public void setEmpllist(List<DebitAutendicationView> empllist) {
		this.empllist = empllist;
	}

	public String getColpassword() {
		return colpassword;
	}

	public void setColpassword(String colpassword) {
		this.colpassword = colpassword;
	}

	public String getWarningMessage() {
		return warningMessage;
	}

	public void setWarningMessage(String warningMessage) {
		this.warningMessage = warningMessage;
	}

	public BigDecimal getDocumentYearId() {
		return documentYearId;
	}

	public void setDocumentYearId(BigDecimal documentYearId) {
		this.documentYearId = documentYearId;
	}

	public BigDecimal getPaymentModeId() {
		return paymentModeId;
	}

	public void setPaymentModeId(BigDecimal paymentModeId) {
		this.paymentModeId = paymentModeId;
	}

	public List<ViewReceiptPayment> getReceiptDocYearList() {
		return receiptDocYearList;
	}

	public void setReceiptDocYearList(List<ViewReceiptPayment> receiptDocYearList) {
		this.receiptDocYearList = receiptDocYearList;
	}

	public List<ViewReceiptPayment> getReceiptDocNoList() {
		return receiptDocNoList;
	}

	public void setReceiptDocNoList(List<ViewReceiptPayment> receiptDocNoList) {
		this.receiptDocNoList = receiptDocNoList;
	}

	public List<PersonalRemittanceCollectionDataTable> getColdatatablevalues() {
		return coldatatablevalues;
	}

	public void setColdatatablevalues(List<PersonalRemittanceCollectionDataTable> coldatatablevalues) {
		this.coldatatablevalues = coldatatablevalues;
	}

	public Boolean getRenderChequePanel() {
		return renderChequePanel;
	}

	public void setRenderChequePanel(Boolean renderChequePanel) {
		this.renderChequePanel = renderChequePanel;
	}

	public Boolean getRenderApproveButton() {
		return renderApproveButton;
	}

	public void setRenderApproveButton(Boolean renderApproveButton) {
		this.renderApproveButton = renderApproveButton;
	}

	public Boolean getRenderNextButton() {
		return renderNextButton;
	}

	public void setRenderNextButton(Boolean renderNextButton) {
		this.renderNextButton = renderNextButton;
	}

	public Boolean getRenderDenominationDatatabe() {
		return renderDenominationDatatabe;
	}

	public void setRenderDenominationDatatabe(Boolean renderDenominationDatatabe) {
		this.renderDenominationDatatabe = renderDenominationDatatabe;
	}

	public Boolean getRenderRefundDenominationDatatabe() {
		return renderRefundDenominationDatatabe;
	}

	public void setRenderRefundDenominationDatatabe(Boolean renderRefundDenominationDatatabe) {
		this.renderRefundDenominationDatatabe = renderRefundDenominationDatatabe;
	}

	public Boolean getBooRendercollectiondatatable() {
		return booRendercollectiondatatable;
	}

	public void setBooRendercollectiondatatable(Boolean booRendercollectiondatatable) {
		this.booRendercollectiondatatable = booRendercollectiondatatable;
	}

	public Boolean getRenderPaymentModePanel() {
		return renderPaymentModePanel;
	}

	public void setRenderPaymentModePanel(Boolean renderPaymentModePanel) {
		this.renderPaymentModePanel = renderPaymentModePanel;
	}

	public Boolean getRenderDenominationApproveButton() {
		return renderDenominationApproveButton;
	}

	public void setRenderDenominationApproveButton(Boolean renderDenominationApproveButton) {
		this.renderDenominationApproveButton = renderDenominationApproveButton;
	}

	public Boolean getRenderDenominationNextButton() {
		return renderDenominationNextButton;
	}

	public void setRenderDenominationNextButton(Boolean renderDenominationNextButton) {
		this.renderDenominationNextButton = renderDenominationNextButton;
	}

	public Boolean getRenderRefundDenominationApproveButton() {
		return renderRefundDenominationApproveButton;
	}

	public void setRenderRefundDenominationApproveButton(Boolean renderRefundDenominationApproveButton) {
		this.renderRefundDenominationApproveButton = renderRefundDenominationApproveButton;
	}

	public Boolean getRenderRefundDenominationNextButton() {
		return renderRefundDenominationNextButton;
	}

	public void setRenderRefundDenominationNextButton(Boolean renderRefundDenominationNextButton) {
		this.renderRefundDenominationNextButton = renderRefundDenominationNextButton;
	}

	public Boolean getDirectFromFirstPage() {
		return directFromFirstPage;
	}

	public void setDirectFromFirstPage(Boolean directFromFirstPage) {
		this.directFromFirstPage = directFromFirstPage;
	}

	public Boolean getBooRenderMulDebit() {
		return booRenderMulDebit;
	}

	public void setBooRenderMulDebit(Boolean booRenderMulDebit) {
		this.booRenderMulDebit = booRenderMulDebit;
	}

	public Boolean getBooRenderSingleDebit() {
		return booRenderSingleDebit;
	}

	public void setBooRenderSingleDebit(Boolean booRenderSingleDebit) {
		this.booRenderSingleDebit = booRenderSingleDebit;
	}

	public Boolean getBooRenderColDebitCard() {
		return booRenderColDebitCard;
	}

	public void setBooRenderColDebitCard(Boolean booRenderColDebitCard) {
		this.booRenderColDebitCard = booRenderColDebitCard;
	}

	public Boolean getBooAuthozed() {
		return booAuthozed;
	}

	public void setBooAuthozed(Boolean booAuthozed) {
		this.booAuthozed = booAuthozed;
	}

	public Boolean getDisableIfEdit() {
		return disableIfEdit;
	}

	public void setDisableIfEdit(Boolean disableIfEdit) {
		this.disableIfEdit = disableIfEdit;
	}

	public Boolean getRenderKnetPanel() {
		return renderKnetPanel;
	}

	public void setRenderKnetPanel(Boolean renderKnetPanel) {
		this.renderKnetPanel = renderKnetPanel;
	}

	public Boolean getBooEdit() {
		return booEdit;
	}

	public void setBooEdit(Boolean booEdit) {
		this.booEdit = booEdit;
	}

	public String getCollectionMode() {
		return collectionMode;
	}

	public void setCollectionMode(String collectionMode) {
		this.collectionMode = collectionMode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public BigDecimal getChequeNo() {
		return chequeNo;
	}

	public void setChequeNo(BigDecimal chequeNo) {
		this.chequeNo = chequeNo;
	}

	public Date getChequeDate() {
		return chequeDate;
	}

	public void setChequeDate(Date chequeDate) {
		this.chequeDate = chequeDate;
	}

	public BigDecimal getApprovalNo() {
		return approvalNo;
	}

	public void setApprovalNo(BigDecimal approvalNo) {
		this.approvalNo = approvalNo;
	}

	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public BigDecimal getTotalCashEntered() {
		return totalCashEntered;
	}

	public void setTotalCashEntered(BigDecimal totalCashEntered) {
		this.totalCashEntered = totalCashEntered;
	}

	public BigDecimal getTotalRefund() {
		return totalRefund;
	}

	public void setTotalRefund(BigDecimal totalRefund) {
		this.totalRefund = totalRefund;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public String getExcheckCashLimitMessage() {
		return excheckCashLimitMessage;
	}

	public void setExcheckCashLimitMessage(String excheckCashLimitMessage) {
		this.excheckCashLimitMessage = excheckCashLimitMessage;
	}

	public BigDecimal getErrcolCashExistsLimit() {
		return errcolCashExistsLimit;
	}

	public void setErrcolCashExistsLimit(BigDecimal errcolCashExistsLimit) {
		this.errcolCashExistsLimit = errcolCashExistsLimit;
	}

	public BigDecimal getColamountKWD() {
		return colamountKWD;
	}

	public void setColamountKWD(BigDecimal colamountKWD) {
		this.colamountKWD = colamountKWD;
	}

	public BigDecimal getTotalbalanceAmount() {
		return totalbalanceAmount;
	}

	public void setTotalbalanceAmount(BigDecimal totalbalanceAmount) {
		this.totalbalanceAmount = totalbalanceAmount;
	}

	public BigDecimal getToalUsedAmount() {
		return toalUsedAmount;
	}

	public void setToalUsedAmount(BigDecimal toalUsedAmount) {
		this.toalUsedAmount = toalUsedAmount;
	}

	public String getCyberPassword() {
		return cyberPassword;
	}

	public void setCyberPassword(String cyberPassword) {
		this.cyberPassword = cyberPassword;
	}

	public SessionStateManage getSessionManage() {
		return sessionManage;
	}

	public void setSessionManage(SessionStateManage sessionManage) {
		this.sessionManage = sessionManage;
	}

	public BigDecimal getColpaymentmodeId() {
		return colpaymentmodeId;
	}

	public void setColpaymentmodeId(BigDecimal colpaymentmodeId) {
		this.colpaymentmodeId = colpaymentmodeId;
	}

	public String getColpaymentmodeName() {
		return colpaymentmodeName;
	}

	public void setColpaymentmodeName(String colpaymentmodeName) {
		this.colpaymentmodeName = colpaymentmodeName;
	}

	public String getColpaymentmodeCode() {
		return colpaymentmodeCode;
	}

	public void setColpaymentmodeCode(String colpaymentmodeCode) {
		this.colpaymentmodeCode = colpaymentmodeCode;
	}

	public BigDecimal getPopulatedDebitCardNumber() {
		return populatedDebitCardNumber;
	}

	public void setPopulatedDebitCardNumber(BigDecimal populatedDebitCardNumber) {
		this.populatedDebitCardNumber = populatedDebitCardNumber;
	}

	public String getKnetIposReceipt() {
		return knetIposReceipt;
	}

	public void setKnetIposReceipt(String knetIposReceipt) {
		this.knetIposReceipt = knetIposReceipt;
	}

	public String getKnetReceiptDate() {
		return knetReceiptDate;
	}

	public void setKnetReceiptDate(String knetReceiptDate) {
		this.knetReceiptDate = knetReceiptDate;
	}

	public String getKnetReceiptTime() {
		return knetReceiptTime;
	}

	public void setKnetReceiptTime(String knetReceiptTime) {
		this.knetReceiptTime = knetReceiptTime;
	}

	public HashMap<BigDecimal, String> getPaymentModeMap() {
		return paymentModeMap;
	}

	public void setPaymentModeMap(HashMap<BigDecimal, String> paymentModeMap) {
		this.paymentModeMap = paymentModeMap;
	}

	public HashMap<String, Object> getSavingMap() {
		return savingMap;
	}

	public void setSavingMap(HashMap<String, Object> savingMap) {
		this.savingMap = savingMap;
	}

	public IMiscellaneousReceiptPaymentService<T> getMiscellaneousReceiptPaymentService() {
		return miscellaneousReceiptPaymentService;
	}

	public void setMiscellaneousReceiptPaymentService(IMiscellaneousReceiptPaymentService<T> miscellaneousReceiptPaymentService) {
		this.miscellaneousReceiptPaymentService = miscellaneousReceiptPaymentService;
	}

	public IGeneralService<T> getGeneralService() {
		return generalService;
	}

	public void setGeneralService(IGeneralService<T> generalService) {
		this.generalService = generalService;
	}

	public ForeignLocalCurrencyDenominationService<T> getForeignLocalCurrencyDenominationService() {
		return foreignLocalCurrencyDenominationService;
	}

	public void setForeignLocalCurrencyDenominationService(ForeignLocalCurrencyDenominationService<T> foreignLocalCurrencyDenominationService) {
		this.foreignLocalCurrencyDenominationService = foreignLocalCurrencyDenominationService;
	}

	public LoginLogoutHistoryUtil<T> getLoginLogoutHistoryUtil() {
		return loginLogoutHistoryUtil;
	}

	public void setLoginLogoutHistoryUtil(LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil) {
		this.loginLogoutHistoryUtil = loginLogoutHistoryUtil;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<PersonalRemittanceCollectionDataTable> getMisDataTableList() {
		return misDataTableList;
	}

	public void setMisDataTableList(List<PersonalRemittanceCollectionDataTable> misDataTableList) {
		this.misDataTableList = misDataTableList;
	}

	public boolean isBooRenderBankTransfer() {
		return booRenderBankTransfer;
	}

	public void setBooRenderBankTransfer(boolean booRenderBankTransfer) {
		this.booRenderBankTransfer = booRenderBankTransfer;
	}

	public String getBankTransferApprovalNo() {
		return bankTransferApprovalNo;
	}

	public void setBankTransferApprovalNo(String bankTransferApprovalNo) {
		this.bankTransferApprovalNo = bankTransferApprovalNo;
	}

	public String getReceiptType() {
		return receiptType;
	}

	public void setReceiptType(String receiptType) {
		this.receiptType = receiptType;
	}

	public Boolean getBooRenderBankTransfer() {
		return booRenderBankTransfer;
	}

	public void setBooRenderBankTransfer(Boolean booRenderBankTransfer) {
		this.booRenderBankTransfer = booRenderBankTransfer;
	}

	public Boolean getMiscePrint() {
		return miscePrint;
	}

	public void setMiscePrint(Boolean miscePrint) {
		this.miscePrint = miscePrint;
	}

	public Boolean getVisableExceptionDailogForReceipt() {
		return visableExceptionDailogForReceipt;
	}

	public void setVisableExceptionDailogForReceipt(Boolean visableExceptionDailogForReceipt) {
		this.visableExceptionDailogForReceipt = visableExceptionDailogForReceipt;
	}

	public String getExceptionMessageForReport() {
		return exceptionMessageForReport;
	}

	public void setExceptionMessageForReport(String exceptionMessageForReport) {
		this.exceptionMessageForReport = exceptionMessageForReport;
	}

	public List<MiscelleneousReportBean> getMiscelleneousReportList() {
		return miscelleneousReportList;
	}

	public void setMiscelleneousReportList(List<MiscelleneousReportBean> miscelleneousReportList) {
		this.miscelleneousReportList = miscelleneousReportList;
	}

	public String getMiscelleneousType() {
		return miscelleneousType;
	}

	public void setMiscelleneousType(String miscelleneousType) {
		this.miscelleneousType = miscelleneousType;
	}

	public JasperPrint getJasperPrint() {
		return jasperPrint;
	}

	public void setJasperPrint(JasperPrint jasperPrint) {
		this.jasperPrint = jasperPrint;
	}

	public String getCustomerNameForReport() {
		return customerNameForReport;
	}

	public void setCustomerNameForReport(String customerNameForReport) {
		this.customerNameForReport = customerNameForReport;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEmosId() {
		return emosId;
	}

	public void setEmosId(String emosId) {
		this.emosId = emosId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public Boolean getVisibleDataTable() {
		return visibleDataTable;
	}

	public void setVisibleDataTable(Boolean visibleDataTable) {
		this.visibleDataTable = visibleDataTable;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

}
