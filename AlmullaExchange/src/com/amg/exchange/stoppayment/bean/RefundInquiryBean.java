package com.amg.exchange.stoppayment.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

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
import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.foreigncurrency.service.ForeignLocalCurrencyDenominationService;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerIdproofView;
import com.amg.exchange.remittance.model.PaymentModeDesc;
import com.amg.exchange.remittance.service.IPaymentService;
import com.amg.exchange.remittance.service.IPersonalRemittanceService;
import com.amg.exchange.stoppayment.model.RefundInquiryView;
import com.amg.exchange.stoppayment.model.RemittanceTranxBenificiary;
import com.amg.exchange.stoppayment.service.IStopPaymentCollectionService;
import com.amg.exchange.stoppayment.service.IStopPaymentService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.GetRound;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component(value = "requestInquiryBean")
@Scope("session")
public class RefundInquiryBean<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger(RefundInquiryBean.class);
	SessionStateManage sessionStateManage = new SessionStateManage();

	@Autowired
	IStopPaymentService<T> iStopPaymentService;
	@Autowired
	ForeignLocalCurrencyDenominationService<T> foreignLocalCurrencyDenominationService;
	@Autowired
	IStopPaymentCollectionService iStopPaymentCollectionService;
	@Autowired
	IGeneralService<T> iGeneralService;
	@Autowired
	IPersonalRemittanceService iPersonalRemittanceService;
	@Autowired
	IPaymentService paymentService;

	// input variables
	private BigDecimal companyId;
	private BigDecimal applicationCountryId;
	private BigDecimal documentCode;
	private BigDecimal documentYear;
	private BigDecimal documentNo;
	private BigDecimal customerId;
	private Date documentDate;
	private String foreignCurrencyCode;
	private BigDecimal foreignCurrencyId;
	private BigDecimal foreignTrnxAmount;
	private BigDecimal trnxActualRate;
	private String localCurrencyCode;
	private BigDecimal localCurrencyId;
	private BigDecimal localTrnxAmount;
	private BigDecimal localNetAmount;
	private BigDecimal locationCode;
	private String paymentMode;

	private String transactionType;
	private BigDecimal remittanceYear;
	private BigDecimal transferNo;
	private BigDecimal remittanceTrnxId;
	private String status;
	private String branchName;
	private BigDecimal customerRefNo;
	private String customerName;
	private String telephoneNo;
	private Date transferDate;
	//private BigDecimal validUntill;
	private String payableBranch;
	private String payableBank;
	private String benificiary;
	private String transferAmountCurrency;
	private BigDecimal transferAmount;
	private BigDecimal paymentYear;
	private BigDecimal paymentNo;
	private Date date;
	private String payBy;
	private String payBank;
	private Date chequedate;
	private String approvalNo;
	private String remark;
	private BigDecimal refundFor;
	private BigDecimal charges;
	private BigDecimal deliveryCharges;
	private BigDecimal rateAdjust;
	private BigDecimal otherAdjust;
	private BigDecimal netRefund;
	private BigDecimal commission;
	private String exchangeRateCurrency;
	private String signature;

	// extra variable
	private String errMsg;
	private JasperPrint jasperPrint;
	private boolean disableReport = true;

	// list
	private List<RefundRequestReport> refundRequestList = new CopyOnWriteArrayList<RefundRequestReport>();
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	// navigation page
	public void navigateToRefundInquiryPage() {
		clear();
		toFetchAllDocFinanceYear();
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "refundInquiry.xhtml");
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			context.redirect("../remittance/refundInquiry.xhtml");
		} catch (Exception e) {
			log.info("Problem to redirect: " + e);
		}
	}

	// clear 
	public void clear(){
		setApplicationCountryId(null);
		setCompanyId(null);
		setCustomerId(null);
		setStatus(null);
		setDocumentCode(null);
		setDocumentDate(null);
		setDocumentNo(null);
		setForeignCurrencyId(null);
		setForeignCurrencyCode(null);
		setForeignTrnxAmount(null);
		setLocalCurrencyId(null);
		setLocalCurrencyCode(null);
		setLocalNetAmount(null);
		setLocalTrnxAmount(null);
		setLocationCode(null);
		setPaymentMode(null);
		setTrnxActualRate(null);
		setBranchName(null);
		setCustomerName(null);
		setTelephoneNo(null);
		setCustomerRefNo(null);
		setTransferDate(null);
		setPayableBranch(null);
		setPayableBank(null);
		setBenificiary(null);
		setTransferAmountCurrency(null);
		setTransferAmount(null);
		setPaymentYear(null);
		setPaymentNo(null);
		setRemark(null);
		setDate(null);
		setRefundFor(null);
		setExchangeRateCurrency(null);
		setNetRefund(null);
		setCommission(null);
		setCharges(null);
		setDeliveryCharges(null);
		setRateAdjust(null);
		setOtherAdjust(null);
		setPayBy(null);
		setPayBank(null);
		setChequedate(null);
		setApprovalNo(null);
		setRemittanceYear(null);
		setTransferNo(null);
		setRemittanceTrnxId(null);
		setDisableReport(true);
	}

	// fetch records from view VW_EX_REFUND
	public void fetchRecFromViewRefund(){

		if(getDocumentYear() != null && getDocumentNo() != null){
			List<RefundInquiryView> lstRefund = iStopPaymentService.fetchRefundApprovedRec(sessionStateManage.getCompanyId(), new BigDecimal(Constants.DOCUMENT_CODE_FOR_REFUND_REQUEST), getDocumentYear(), getDocumentNo());
			if(lstRefund != null && lstRefund.size() != 0){

				RefundInquiryView refundview = lstRefund.get(0);

				setCustomerId(refundview.getCustomerId());
				List<Customer> customer = iStopPaymentCollectionService.getCustomerInfo(refundview.getCustomerId());
				for (Customer customer2 : customer) {
					setCustomerName((customer2.getFirstName()==null? "" :customer2.getFirstName()) + " " + (customer2.getMiddleName()==null? "" :customer2.getMiddleName()) + " " + (customer2.getLastName()==null? "" :customer2.getLastName()));
					setTelephoneNo(customer2.getMobile());
					setCustomerRefNo(customer2.getCustomerReference());
					try{
						setSignature(customer2.getSignatureSpecimenClob().getSubString(1,(int) customer2.getSignatureSpecimenClob().length()));
					}catch(Exception e){

					}
				}
				setBranchName(refundview.getLocationDesc());
				setStatus(refundview.getDocumentStatus());

				setRemittanceYear(refundview.getTransactionYear());
				setTransferNo(refundview.getTransactionNo());
				setTransferDate(refundview.getTransactionDate());
				setDocumentDate(refundview.getDocumentDate());
				setPayableBranch(refundview.getBranchDesc());
				setPayableBank(refundview.getBankDesc());
				List<RemittanceTranxBenificiary> trnxBenificiaries = iStopPaymentService.viewDetailsTranxBeneficiary(refundview.getTransactionId());
				for (RemittanceTranxBenificiary remittancetrnxBenificiary : trnxBenificiaries) {
					setBenificiary(nullCheck(remittancetrnxBenificiary.getBeneficiaryFirstName()) + nullCheck(remittancetrnxBenificiary.getBeneficiarySecondName()) + nullCheck(remittancetrnxBenificiary.getBeneficiaryThirdName())
							+ nullCheck(remittancetrnxBenificiary.getBeneficiaryFourthName()) + nullCheck(remittancetrnxBenificiary.getBeneficiaryFifthName())
							+ "A/C No:" + nullCheck(remittancetrnxBenificiary.getBeneficiaryAccountNo()) + ", " + nullCheck(remittancetrnxBenificiary.getBeneficiaryBank())
							+ nullCheck(remittancetrnxBenificiary.getBeneficiaryBranch()));
				}
				setTransferAmountCurrency(refundview.getForeignCurrencyCode());
				setTransferAmount(refundview.getForeignTrnxAmount());
				setRemittanceTrnxId(refundview.getTransactionId());
				setPaymentYear(refundview.getDocumentYear());
				setPaymentNo(refundview.getDocumentNo());
				setDate(refundview.getDocumentDate());
				
				RemittanceView remittanceTransaction = iStopPaymentCollectionService.getRemittanceTransactionFromView(sessionStateManage.getCountryId(), getRemittanceYear().intValue(), getTransferNo(), sessionStateManage.getCompanyId(), new BigDecimal(Constants.DOCUMENT_CODE_FOR_REMITTANCE_TRANSACTION));
				if(remittanceTransaction != null){
					String product = iPersonalRemittanceService.getRemittanceServiceRuleName(remittanceTransaction.getBankCountryId(), remittanceTransaction.getSaleCurrencyId(), remittanceTransaction.getBankId(), remittanceTransaction.getRemittanceModeId(),
							remittanceTransaction.getDeliveryModeId());
					if (product != null) {
						setTransactionType(product);
					} else {
						setTransactionType(null);
					}
				}

				setApplicationCountryId(refundview.getApplicationCountryId());
				setCompanyId(refundview.getCompanyId());
				setCustomerId(refundview.getCustomerId());
				setDocumentCode(refundview.getDocumentCode());
				setDocumentNo(refundview.getDocumentNo());
				setDocumentYear(refundview.getDocumentYear());
				setForeignCurrencyCode(refundview.getForeignCurrencyCode());
				setForeignCurrencyId(refundview.getForeignCurrencyId());
				setForeignTrnxAmount(refundview.getForeignTrnxAmount());
				setLocalNetAmount(refundview.getLocalNetAmount());

				setLocationCode(refundview.getLocationCode());
				setPaymentMode(refundview.getPaymentMode());
				if(refundview.getPaymentModeDesc() != null){
					setPayBy(refundview.getPaymentModeDesc());
				}else{
					if(refundview.getPaymentMode() != null){
						List<PaymentModeDesc> lstpayment = paymentService.searchRecordByCode(refundview.getPaymentMode());
						if(lstpayment != null && lstpayment.size() != 0){
							PaymentModeDesc paydesc = lstpayment.get(0);
							setPayBy(paydesc.getLocalPaymentName());
						}
					}
				}
				
				if(refundview.getChequeBankCode() != null){
					setPayBank(refundview.getChequeBankCode().toPlainString());
				}
				setChequedate(refundview.getChequeDate());
				setApprovalNo(refundview.getApprovalNo());
				setRemark(refundview.getRemarks());
				

				setRefundFor(refundview.getForeignTrnxAmount());
				setExchangeRateCurrency(refundview.getForeignCurrencyCode() + " / " +refundview.getLocalCurrencyCode());
				setTrnxActualRate(refundview.getTrnxActualRate());
				setLocalCurrencyCode(refundview.getLocalCurrencyCode());
				setLocalCurrencyId(refundview.getLocalCurrencyId());
				setLocalTrnxAmount(refundview.getLocalTrnxAmount());
				setNetRefund(refundview.getLocalNetAmount());
				//setCommission();
				//setCharges();
				//setDeliveryCharges();
				//setRateAdjust();
				//setOtherAdjust();
				
				setDisableReport(false);

			}else{
				setErrMsg("Refund Number does not Exist");
				setDocumentNo(null);
				setDisableReport(true);
				RequestContext.getCurrentInstance().execute("csp.show();");
			}
		}
	}

	// to check null
	private String nullCheck(String value) {
		return value == null ? "" : value.trim().concat(" ");
	}

	// fetch financial year
	public void toFetchAllDocFinanceYear() {
		try {
			List<UserFinancialYear> applicationYearList = iGeneralService.getDealYear(new Date());
			if (applicationYearList.size() > 0) {
				setDocumentYear(applicationYearList.get(0).getFinancialYear());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// exit from page
	public void clickOnExit() throws IOException {
		if (sessionStateManage.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("../registration/employeehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("../registration/branchhome.xhtml");
		}
	}

	// generate report for office
	public void genarateRefundRequestOfficeCopyReports(ActionEvent actionEvent) throws IOException {
		ServletOutputStream servletOutputStream=null;
		try{
			if(getDocumentNo() != null && getDocumentYear() != null){
				fetchRefundRequestReportDetails();
				init();
				HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
				httpServletResponse.addHeader("Content-disposition", "attachment; filename=RefundRequestOfficeCopy.pdf");
				servletOutputStream = httpServletResponse.getOutputStream();
				JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
				FacesContext.getCurrentInstance().responseComplete();
			}else{
				setErrMsg("Please Enter Document Number");
				RequestContext.getCurrentInstance().execute("csp.show();");
			}
		}catch(Exception e){
			setErrMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}finally{
			if(servletOutputStream!=null){
				servletOutputStream.close();
			}
		}
	}

	public void init() {
		try{
			JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(refundRequestList);
			String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("reports/design/RefundRequestOfficeCopy.jasper");
			jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap(), beanCollectionDataSource);
		}catch(Exception e){
			setErrMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}

	public void fetchRefundRequestReportDetails() {
		try{
			refundRequestList.clear();
			ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
			String realPath = ctx.getRealPath("//");
			//String logoPath = realPath + Constants.LOGO_PATH;
			
			RefundRequestReport refundRequestReport = new RefundRequestReport();
			NumberFormat usa = NumberFormat.getInstance(Locale.getDefault());
			refundRequestReport.setCustomerRefNo(getCustomerRefNo()+" / "+getCustomerName());
			if (getRefundFor() != null && getForeignCurrencyId() != null) {
				refundRequestReport.setRefundFor(GetRound.roundBigDecimal(getRefundFor(),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(getForeignCurrencyId())));
			}
			if(getLocalTrnxAmount() != null && getLocalCurrencyId() != null){
				refundRequestReport.setRefund(GetRound.roundBigDecimal(getLocalTrnxAmount(),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(getLocalCurrencyId())));
			}
			if(getNetRefund() != null && getLocalCurrencyId() != null){
				refundRequestReport.setNetRefund(GetRound.roundBigDecimal(getNetRefund(),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(getLocalCurrencyId())));
			}
			refundRequestReport.setPaymentNo(getDocumentNo());
			refundRequestReport.setPaymentYear(getDocumentYear());
			if (getDocumentNo() != null && getDocumentYear() != null) {
				refundRequestReport.setPaymentNoAndYear(getDocumentYear().toString()+" / "+getDocumentNo().toString());
			}
			if(getTransferDate() != null){
				SimpleDateFormat form1 = new SimpleDateFormat("dd-MM-yyyy");
				String finalDate1 = form1.format(getTransferDate());
				refundRequestReport.setPaymentDate(finalDate1);
				SimpleDateFormat form = new SimpleDateFormat("dd-MM-yyyy");
				String finalDate = form.format(getTransferDate());
				refundRequestReport.setRefDate(finalDate);
			}
			if (getTrnxActualRate() != null) {
				refundRequestReport.setExchangeRate(getTrnxActualRate().toString());
			}
			refundRequestReport.setDrawnBank(getPayableBank());
			refundRequestReport.setDrawnBankBranch(getPayableBranch());
			refundRequestReport.setRefNo(getTransferNo());
			refundRequestReport.setRefYear(getRemittanceYear());
			if (getTransferNo() != null && getRemittanceYear() != null) {
				refundRequestReport.setRemitYearNumber(getRemittanceYear().toString()+" / "+getTransferNo().toString());
			}
			List<RemittanceTranxBenificiary> trnxBenificiaries = iStopPaymentService.viewDetailsTranxBeneficiary(getRemittanceTrnxId());
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
			refundRequestReport.setNetRefundCurrencyName(getLocalCurrencyCode());
			refundRequestReport.setRefundCurrencyName(getLocalCurrencyCode());
			refundRequestReport.setExchangeRateCurrencyName(getTransferAmountCurrency()+" / "+getLocalCurrencyCode());
			refundRequestReport.setRefundForCurrencyName(getForeignCurrencyCode());
			refundRequestReport.setRefundOf(getTransactionType());

			List<CompanyMasterDesc> companyList=  iGeneralService.getCompanyList(sessionStateManage.getCompanyId(),sessionStateManage.getLanguageId());
			if(companyList.size()>0){
				String amt = "";
				refundRequestReport.setCompanyName(companyList.get(0).getCompanyName());
				if(companyList.get(0).getFsCompanyMaster().getCapitalAmount() != null){
					amt = usa.format(new BigDecimal(companyList.get(0).getFsCompanyMaster().getCapitalAmount()));
				}
				
				refundRequestReport.setAddress(nullCheck(companyList.get(0).getFsCompanyMaster().getAddress1())+","+nullCheck(companyList.get( 0).getFsCompanyMaster().getAddress2())+","+nullCheck(companyList.get( 0).getFsCompanyMaster().getAddress3())+",C.R."+nullCheck(companyList.get(0).getFsCompanyMaster().getRegistrationNumber())+",Share Capital:K.D."+amt);
			}
			List<CustomerIdproofView> custList=iGeneralService.getCustomerIdProofDetailsFromView(getCustomerId() );
			
			if(custList.size()>0){
				refundRequestReport.setIdNumber(custList.get(0).getIdProofInt() );	
				refundRequestReport.setIdType(custList.get(0).getIdProofTypeDesc() );
			}
			
			refundRequestReport.setSignature(getSignature());
			
			String logoPath =null;
			if(sessionStateManage.getCountryName().equalsIgnoreCase(Constants.KUWAIT)){
				logoPath = realPath+Constants.LOGO_PATH;
			}else if(sessionStateManage.getCountryName().equalsIgnoreCase(Constants.OMAN)){
				logoPath = realPath+Constants.LOGO_PATH_OM;
			}else{
				logoPath =realPath+Constants.LOGO_PATH;
			}
			refundRequestReport.setLogoPath(logoPath);
			refundRequestList.add(refundRequestReport);
		}catch(Exception e){
			setErrMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}

	// generate report for customer 
	public void genarateRefundRequestCustomerCopyReports(ActionEvent actionEvent) throws IOException {
		ServletOutputStream servletOutputStream=null;
		try{
			if(getDocumentNo() != null && getDocumentYear() != null){
				fetchRefundRequestReportDetails();
				init1();
				HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
				httpServletResponse.addHeader("Content-disposition", "attachment; filename=RefundRequestCustomerCopyCopy.pdf");
				servletOutputStream = httpServletResponse.getOutputStream();
				JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
				FacesContext.getCurrentInstance().responseComplete();
			}else{
				setErrMsg("Please Enter Document Number");
				RequestContext.getCurrentInstance().execute("csp.show();");
			}
		}catch(Exception e){
			setErrMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}finally{
			if(servletOutputStream!=null){
				servletOutputStream.close();
			}
		}
	}
	
	public void init1() {
		try{
			JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(refundRequestList);
			String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("reports/design/RefundRequestCustomerCopy.jasper");
			jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap(), beanCollectionDataSource);
		}catch(Exception e){
			setErrMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}

	// getters and setters
	public BigDecimal getCompanyId() {
		return companyId;
	}
	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}

	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}
	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
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

	public BigDecimal getCustomerId() {
		return customerId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	public Date getDocumentDate() {
		return documentDate;
	}
	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}

	public String getForeignCurrencyCode() {
		return foreignCurrencyCode;
	}
	public void setForeignCurrencyCode(String foreignCurrencyCode) {
		this.foreignCurrencyCode = foreignCurrencyCode;
	}

	public BigDecimal getForeignTrnxAmount() {
		return foreignTrnxAmount;
	}
	public void setForeignTrnxAmount(BigDecimal foreignTrnxAmount) {
		this.foreignTrnxAmount = foreignTrnxAmount;
	}

	public BigDecimal getTrnxActualRate() {
		return trnxActualRate;
	}
	public void setTrnxActualRate(BigDecimal trnxActualRate) {
		this.trnxActualRate = trnxActualRate;
	}

	public String getLocalCurrencyCode() {
		return localCurrencyCode;
	}
	public void setLocalCurrencyCode(String localCurrencyCode) {
		this.localCurrencyCode = localCurrencyCode;
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

	public BigDecimal getLocationCode() {
		return locationCode;
	}
	public void setLocationCode(BigDecimal locationCode) {
		this.locationCode = locationCode;
	}

	public String getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public BigDecimal getForeignCurrencyId() {
		return foreignCurrencyId;
	}
	public void setForeignCurrencyId(BigDecimal foreignCurrencyId) {
		this.foreignCurrencyId = foreignCurrencyId;
	}

	public BigDecimal getLocalCurrencyId() {
		return localCurrencyId;
	}
	public void setLocalCurrencyId(BigDecimal localCurrencyId) {
		this.localCurrencyId = localCurrencyId;
	}

	// extra variables
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public BigDecimal getRemittanceYear() {
		return remittanceYear;
	}
	public void setRemittanceYear(BigDecimal remittanceYear) {
		this.remittanceYear = remittanceYear;
	}

	public BigDecimal getTransferNo() {
		return transferNo;
	}
	public void setTransferNo(BigDecimal transferNo) {
		this.transferNo = transferNo;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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

	/*public BigDecimal getValidUntill() {
		return validUntill;
	}
	public void setValidUntill(BigDecimal validUntill) {
		this.validUntill = validUntill;
	}*/

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

	public BigDecimal getTransferAmount() {
		return transferAmount;
	}
	public void setTransferAmount(BigDecimal transferAmount) {
		this.transferAmount = transferAmount;
	}

	public BigDecimal getPaymentYear() {
		return paymentYear;
	}
	public void setPaymentYear(BigDecimal paymentYear) {
		this.paymentYear = paymentYear;
	}

	public BigDecimal getPaymentNo() {
		return paymentNo;
	}
	public void setPaymentNo(BigDecimal paymentNo) {
		this.paymentNo = paymentNo;
	}

	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	public String getPayBy() {
		return payBy;
	}
	public void setPayBy(String payBy) {
		this.payBy = payBy;
	}

	public String getPayBank() {
		return payBank;
	}
	public void setPayBank(String payBank) {
		this.payBank = payBank;
	}

	public Date getChequedate() {
		return chequedate;
	}
	public void setChequedate(Date chequedate) {
		this.chequedate = chequedate;
	}

	public String getApprovalNo() {
		return approvalNo;
	}
	public void setApprovalNo(String approvalNo) {
		this.approvalNo = approvalNo;
	}

	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getRefundFor() {
		return refundFor;
	}
	public void setRefundFor(BigDecimal refundFor) {
		this.refundFor = refundFor;
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

	public BigDecimal getCommission() {
		return commission;
	}
	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}

	public String getExchangeRateCurrency() {
		return exchangeRateCurrency;
	}
	public void setExchangeRateCurrency(String exchangeRateCurrency) {
		this.exchangeRateCurrency = exchangeRateCurrency;
	}

	public BigDecimal getRemittanceTrnxId() {
		return remittanceTrnxId;
	}
	public void setRemittanceTrnxId(BigDecimal remittanceTrnxId) {
		this.remittanceTrnxId = remittanceTrnxId;
	}
	
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}

	public boolean isDisableReport() {
		return disableReport;
	}
	public void setDisableReport(boolean disableReport) {
		this.disableReport = disableReport;
	}

	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	
	
	
}
