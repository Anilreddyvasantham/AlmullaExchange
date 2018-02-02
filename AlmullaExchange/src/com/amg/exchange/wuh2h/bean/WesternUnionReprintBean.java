package com.amg.exchange.wuh2h.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CutomerDetailsView;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.foreigncurrency.service.ForeignLocalCurrencyDenominationService;
import com.amg.exchange.foreigncurrency.service.IForeignCurrencyPurchaseService;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.registration.service.ICustomerRegistrationBranchService;
import com.amg.exchange.remittance.model.CollectionDetailView;
import com.amg.exchange.remittance.model.CollectionPaymentDetailsView;
import com.amg.exchange.remittance.service.IPersonalRemittanceService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.GetRound;
import com.amg.exchange.util.SessionStateManage;
import com.amg.exchange.wuh2h.model.ViewWUSendTransaction;
import com.amg.exchange.wuh2h.model.WURemittanceApplicationView;
import com.amg.exchange.wuh2h.model.WUReportTermsAndConditions;
import com.amg.exchange.wuh2h.service.IWUH2HService;

@SuppressWarnings({ "unused" })
@Component("wuh2hReprint")
@Scope("session")
public class WesternUnionReprintBean<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	SessionStateManage sessionStateManage = new SessionStateManage();
	Logger log = Logger.getLogger(WesternUnionReprintBean.class);

	/*
	 * Autowire Configuration
	 */
	
	@Autowired
	IWUH2HService iwuh2hService;
	
	@Autowired
	ICustomerRegistrationBranchService<T> icustomerRegistrationService;
	
	@Autowired
	IGeneralService<T> generalService;
	
	@Autowired
	ForeignLocalCurrencyDenominationService<T> foreignLocalCurrencyDenominationService;
	
	@Autowired
	IPersonalRemittanceService iPersonalRemittanceService;

	
	public WesternUnionReprintBean() {
	}

	/*
	 * Western Union Page Navigation
	 */
	public void pageNavigation() {
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../wuh2h/wuh2htransactionreprint.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void pageNavigationBySendTerminal() {
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../wuh2h/wuh2hsendtransactionreprintbyterminal.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void pageNavigationByReceiveTerminal() {
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../wuh2h/wuh2hreceivetransactionreprintbyterminal.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	List<WUH2HSendTxnDataTable> sendTransactionList = new ArrayList<WUH2HSendTxnDataTable>();
	List<WUH2HReceiveTxnDataTable> receiveTransactionList = new ArrayList<WUH2HReceiveTxnDataTable>();

	public List<WUH2HSendTxnDataTable> getSendTransactionList() {
		return sendTransactionList;
	}

	public void setSendTransactionList(
			List<WUH2HSendTxnDataTable> sendTransactionList) {
		this.sendTransactionList = sendTransactionList;
	}	
	
	public List<WUH2HReceiveTxnDataTable> getReceiveTransactionList() {
		return receiveTransactionList;
	}

	public void setReceiveTransactionList(
			List<WUH2HReceiveTxnDataTable> receiveTransactionList) {
		this.receiveTransactionList = receiveTransactionList;
	}

	public void populateSendTxn(){
		
		if(sendTransactionList!=null || !sendTransactionList.isEmpty()){
			sendTransactionList.clear();
		}
		
		HttpSession session = sessionStateManage.getSession();
		BigDecimal  referenecno = (BigDecimal)session.getAttribute("customerRefNo");
		List<ViewWUSendTransaction> customerInquiryList = new ArrayList<ViewWUSendTransaction>();
		try {
			customerInquiryList = iwuh2hService.getWUTxnInquiryList(referenecno,sessionStateManage.getCompanyId(),sessionStateManage.getCountryId(), new BigDecimal(Constants.DOCUMENT_CODE_FOR_COLLECT_TRANSACTION),getFinaceYear());
			
			WUH2HSendTxnDataTable dataTable =null;

			for (ViewWUSendTransaction viewObject : customerInquiryList) {
				dataTable = new WUH2HSendTxnDataTable();				
				dataTable.setSenderFirstName(viewObject.getBeneficaryName());
				dataTable.setCollectionDocumentNo(viewObject.getCollectionDocumentNo());
				dataTable.setDocumentFinanceYear(viewObject.getCollectionDocumentFinYear());
				dataTable.setCollectionDocumentCode(viewObject.getCollectionDocumentCode());
				dataTable.setTransactionNumber(viewObject.getDocumentNumber());
				
				dataTable.setTransactionType(viewObject.getTransactionTypeDesc());
				dataTable.setDocumentDate(viewObject.getDocumentDate());
				dataTable.setForeignTxnAmount(viewObject.getForeignTransactionAmount());
				dataTable.setForeignCurrencyCode(viewObject.getCurrencyQuoteName());
				dataTable.setMtcn(viewObject.getMtcn());
				dataTable.setApplicationCountryId(viewObject.getApplicationCountryId());
				dataTable.setCompanyId(viewObject.getCompanyId());
				
				
				sendTransactionList.add(dataTable);
			}
		} catch (Exception ex) {
			log.info("Problem in VIEW :" + ex);
			//setErrmsg(ex.getMessage());
			//equestContext.getCurrentInstance().execute("procedureErr.show();");
		}
	}	
	
	public void populateReceiveTxn(){
		if(receiveTransactionList!=null || !receiveTransactionList.isEmpty()){
			receiveTransactionList.clear();
		}
		
		HttpSession session = sessionStateManage.getSession();
		BigDecimal  referenecno = (BigDecimal)session.getAttribute("customerRefNo");
		List<ReceiveReceiptView> customerInquiryReceiveList = new ArrayList<ReceiveReceiptView>();
		try {
			
			customerInquiryReceiveList = iwuh2hService.getWUReceiveTxnInquiryList(referenecno,sessionStateManage.getCompanyId(),sessionStateManage.getCountryId(), new BigDecimal(Constants.DOCUMENT_CODE_FOR_PAYMENT),getFinaceYear());
			
			WUH2HReceiveTxnDataTable dataTable =null;

			for (ReceiveReceiptView viewObject : customerInquiryReceiveList) {
				dataTable = new WUH2HReceiveTxnDataTable();
				
				dataTable.setReceiverFirstName(viewObject.getCustomerName());
				dataTable.setSenderFirstName(viewObject.getWuSenderFirstName()+" "+viewObject.getWuSenderLastName());
				dataTable.setCollectionDocumentNo(viewObject.getDocumentNo());
				dataTable.setDocumentFinanceYear(viewObject.getDocumentFinanceYear());
				dataTable.setCollectionDocumentCode(viewObject.getDocumentCode());
				dataTable.setTransactionNumber(viewObject.getDocumentNo());
				
				dataTable.setTransactionType(viewObject.getTransactionType());
				dataTable.setDocumentDate(viewObject.getDocumentDate());
				dataTable.setLocalTxnAmount(viewObject.getLocalTransactionAmount());
				dataTable.setLocalCurrencyCode("KWD");
				dataTable.setMtcn(viewObject.getMtcno());
				dataTable.setApplicationCountryId(viewObject.getApplicationCountryId());
				dataTable.setCompanyId(viewObject.getCompanyId());
				
				receiveTransactionList.add(dataTable);
			}
		} catch (Exception ex) {
			log.info("Problem in VIEW :" + ex);
			//setErrmsg(ex.getMessage());
			//equestContext.getCurrentInstance().execute("procedureErr.show();");
		}
	
	}
	
	public void populateSendTxnByTerminal(){
		if(sendTransactionList!=null || !sendTransactionList.isEmpty()){
			sendTransactionList.clear();
		}
		List<ViewWUSendTransaction> customerInquiryList = new ArrayList<ViewWUSendTransaction>();
		try {
			
			String terminalAddress = fetchTerminalAddress();
			customerInquiryList = iwuh2hService.getWUSendTxnInquiryListByTerminal(sessionStateManage.getCompanyId(),sessionStateManage.getCountryId(), new BigDecimal(Constants.DOCUMENT_CODE_FOR_COLLECT_TRANSACTION),getFinaceYear(),terminalAddress);
			
			WUH2HSendTxnDataTable dataTable =null;

			for (ViewWUSendTransaction viewObject : customerInquiryList) {
				dataTable = new WUH2HSendTxnDataTable();				
				dataTable.setSenderFirstName(viewObject.getBeneficaryName());
				dataTable.setCollectionDocumentNo(viewObject.getCollectionDocumentNo());
				dataTable.setDocumentFinanceYear(viewObject.getCollectionDocumentFinYear());
				dataTable.setCollectionDocumentCode(viewObject.getCollectionDocumentCode());
				dataTable.setTransactionNumber(viewObject.getDocumentNumber());
				
				dataTable.setTransactionType(viewObject.getTransactionTypeDesc());
				dataTable.setDocumentDate(viewObject.getDocumentDate());
				dataTable.setForeignTxnAmount(viewObject.getForeignTransactionAmount());
				dataTable.setForeignCurrencyCode(viewObject.getCurrencyQuoteName());
				dataTable.setMtcn(viewObject.getMtcn());
				dataTable.setApplicationCountryId(viewObject.getApplicationCountryId());
				dataTable.setCompanyId(viewObject.getCompanyId());
				
				sendTransactionList.add(dataTable);
			}
		} catch (Exception ex) {
			log.info("Problem in VIEW :" + ex);
			//setErrmsg(ex.getMessage());
			//equestContext.getCurrentInstance().execute("procedureErr.show();");
		}
	}	
	
	public void populateReceiveTxnByTerminal(){
		if(receiveTransactionList!=null || !receiveTransactionList.isEmpty()){
			receiveTransactionList.clear();
		}
			List<ReceiveReceiptView> customerInquiryReceiveList = new ArrayList<ReceiveReceiptView>();
		try {
			String terminalAddress = fetchTerminalAddress();
			customerInquiryReceiveList = iwuh2hService.getWUReceiveTxnInquiryListByTerminal(sessionStateManage.getCompanyId(),sessionStateManage.getCountryId(), new BigDecimal(Constants.DOCUMENT_CODE_FOR_PAYMENT),getFinaceYear(),terminalAddress);
			
			WUH2HReceiveTxnDataTable dataTable =null;

			for (ReceiveReceiptView viewObject : customerInquiryReceiveList) {
				dataTable = new WUH2HReceiveTxnDataTable();
				
				dataTable.setReceiverFirstName(viewObject.getCustomerName());
				dataTable.setSenderFirstName(viewObject.getWuSenderFirstName()+" "+viewObject.getWuSenderLastName());
				dataTable.setCollectionDocumentNo(viewObject.getDocumentNo());
				dataTable.setDocumentFinanceYear(viewObject.getDocumentFinanceYear());
				dataTable.setCollectionDocumentCode(viewObject.getDocumentCode());
				dataTable.setTransactionNumber(viewObject.getDocumentNo());
				
				dataTable.setTransactionType(viewObject.getTransactionType());
				dataTable.setDocumentDate(viewObject.getDocumentDate());
				dataTable.setLocalTxnAmount(viewObject.getLocalTransactionAmount());
				dataTable.setLocalCurrencyCode("KWD");
				dataTable.setMtcn(viewObject.getMtcno());
				dataTable.setApplicationCountryId(viewObject.getApplicationCountryId());
				dataTable.setCompanyId(viewObject.getCompanyId());
				
				receiveTransactionList.add(dataTable);
			}
		} catch (Exception ex) {
			log.info("Problem in VIEW :" + ex);
			//setErrmsg(ex.getMessage());
			//equestContext.getCurrentInstance().execute("procedureErr.show();");
		}
	
	}
	
	private JasperPrint jasperPrint;
	public void whh2hSendMoneyReceiptReportInit() throws JRException {

		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(wuRemittanceReceiptSubreportList);
		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath = ctx.getRealPath("//");
		String reportPath = realPath +"//reports//design//wusendmainreport.jasper";
		System.out.println(reportPath);
		jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap(),beanCollectionDataSource);
	}
	
	public void generateWUSendReport(BigDecimal documentno,BigDecimal financeyear, BigDecimal documentcode, BigDecimal applicationCountryId, BigDecimal companyId){
		ServletOutputStream servletOutputStream=null;
		try {
				
			fetchWUSendReceiptReportData(documentno,financeyear,String.valueOf(documentcode),applicationCountryId,companyId);
			whh2hSendMoneyReceiptReportInit();
			jasperPrint.getPages().remove(1);
			byte[] pdfasbytes = JasperExportManager.exportReportToPdf(jasperPrint);
			
			HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			httpServletResponse.setHeader("Cache-Control","cache, must-revalidate");
			httpServletResponse.addHeader("Content-disposition","attachment; filename=WUSendMoneyReprintReceipt.pdf");
			servletOutputStream=httpServletResponse.getOutputStream();  
			JasperExportManager.exportReportToPdfStream(jasperPrint,servletOutputStream);
			FacesContext.getCurrentInstance().responseComplete();

		}catch (NullPointerException ne) {
			log.info("Exception in generateWUSendReport:" + ne.getMessage());
			setErrorMessage("Method Name::generateWUSendReport");
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
		} catch (Exception exception) {
			log.info("Exception in generateWUSendReport:" + exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}finally{
			if(servletOutputStream!=null){
				try {
					servletOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void report(WUH2HSendTxnDataTable dataTable){
		try{
			generateWUSendReport(dataTable.getCollectionDocumentNo(),dataTable.getDocumentFinanceYear(),dataTable.getCollectionDocumentCode(),dataTable.getApplicationCountryId(),dataTable.getCompanyId());
				
		}catch(Exception e){
			log.info(""+e.getMessage());			
			RequestContext.getCurrentInstance().execute("report.show();");
			return ;
		}
	} 

	private List<WURemittanceReceiptSubreport> wuRemittanceReceiptSubreportList;
	
	private void fetchWUSendReceiptReportData(BigDecimal documentNumber,BigDecimal finaceYear, String documentCode, BigDecimal applicationCountryId, BigDecimal companyId) throws Exception{ 

		wuRemittanceReceiptSubreportList = new CopyOnWriteArrayList<WURemittanceReceiptSubreport>();
		List<WURemittanceApplicationView> remittanceApplicationList = new ArrayList<WURemittanceApplicationView>();
		List<WURemittanceApplicationView> fcsaleList = new ArrayList<WURemittanceApplicationView>();
		List<WURemittanceReportBean> remittanceApplList = new ArrayList<WURemittanceReportBean>();
		HttpSession session = sessionStateManage.getSession();
		String  wucardno = (String)session.getAttribute("WUCardno");

		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath = ctx.getRealPath("//");
		String subReportPath = realPath + Constants.SUB_REPORT_PATH;
		String waterMark = realPath+Constants.REPORT_WATERMARK_LOGO;

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String currentDate = dateFormat.format(new Date());
		int noOfTransactions = 0;

		String currencyQuoteName = generalService.getCurrencyQuote(new BigDecimal(sessionStateManage.getCurrencyId()));
		List<Employee> employeeList = iPersonalRemittanceService.getEmployeeDetails(sessionStateManage.getUserName());

		List<WURemittanceApplicationView> remittanceViewlist = iwuh2hService.getRecordsForWURemittanceReceiptReport(documentNumber,finaceYear,documentCode,applicationCountryId,companyId);
		if (remittanceViewlist.size() > 0) {
			for (WURemittanceApplicationView remittanceAppview : remittanceViewlist) {
				remittanceApplicationList.add(remittanceAppview);
				noOfTransactions= noOfTransactions+1;
			}
			//remittance List
			for (WURemittanceApplicationView view : remittanceApplicationList) {

				WURemittanceReportBean obj = new WURemittanceReportBean();
								// setting customer reference	
				StringBuffer customerReff = new StringBuffer();
				if(view.getCustomerReference() != null){
					customerReff.append(view.getCustomerReference());
					customerReff.append(" / ");
				}
				StringBuffer customerName = new StringBuffer();
				if(view.getFirstName() != null){
					customerName.append(" ");
					customerName.append(view.getFirstName());
					customerReff.append(" ");
					customerReff.append(view.getFirstName());
				}
				if(view.getMiddleName() != null){
					customerName.append(" ");
					customerName.append(view.getMiddleName());
					customerReff.append(" ");
					customerReff.append(view.getMiddleName());
				}
				/*if(view.getLastName()!=null){
					customerName.append(" ");
					customerName.append(view.getLastName());
					customerReff.append(" ");
					customerReff.append(view.getLastName());
				}*/
				obj.setFirstName(customerReff.toString());				
				
				obj.setSenderPointsEarned(String.valueOf(view.getWuTotalPointsEarned()==null ? new Long(0)  : view.getWuTotalPointsEarned()));
				obj.setSenderNewPointsEarned(String.valueOf(view.getWuNewPointsEarned()==null ? new Long(0)  : view.getWuNewPointsEarned()));
				
				//setCustomerNameForReport(customerName.toString());

				if(view.getContactNumber()!=null && !view.getContactNumber().trim().equalsIgnoreCase("")){
					obj.setMobileNo(new BigDecimal(view.getContactNumber().trim()));
				}
				obj.setCivilId(view.getIdentityInt());

				Date sysdate = view.getIdentityExpiryDate();
				if(sysdate!=null){
					obj.setIdExpiryDate(new SimpleDateFormat("dd/MM/yyy").format(sysdate));
				}

				obj.setLocation(sessionStateManage.getLocation());

				//setting receipt Number
				StringBuffer receiptNo = new StringBuffer();
				if(view.getDocumentFinancialYear()!=null){
					receiptNo.append(view.getDocumentFinancialYear());
					receiptNo.append(" / ");
				}
				if(view.getCollectionDocumentNo()!=null){
					receiptNo.append(view.getCollectionDocumentNo());
				}
				obj.setReceiptNo(receiptNo.toString());

				// setting transaction Number
				StringBuffer transactionNo = new StringBuffer();
				if(view.getDocumentFinancialYear()!=null){
					transactionNo.append(view.getDocumentFinancialYear());
					transactionNo.append(" / ");
				}
				if(view.getDocumentNo()!=null){
					transactionNo.append(view.getDocumentNo());
				}
				obj.setTransactionNo(transactionNo.toString());

				
				Date txndate = view.getCollectDate();
                if (txndate != null) {
                    obj.setDate(new SimpleDateFormat("dd/MM/yyy").format(txndate));
                }
				
				String bnfName = view.getBeneficiaryName();
				bnfName  = bnfName.replace("(TEL NO:)", "");
				obj.setBeneficiaryName(bnfName);
				//obj.setBeneficiaryName(view.getBeneficiaryName());
				//obj.setBenefeciaryBankName(view.getBeneficiaryBank());
				//obj.setBenefeciaryBranchName(view.getBenefeciaryBranch());
				//obj.setBenefeciaryBranchName("Any Where");
				//obj.setBenefeciaryAccountNumber(view.getBenefeciaryAccountNo());
				obj.setNoOfTransaction(new BigDecimal(noOfTransactions));
				obj.setPhoneNumber(view.getPhoneNumber());
				obj.setUserName(sessionStateManage.getUserName());
				obj.setPinNo(view.getPinNo() );
				obj.setMtcn(view.getMtcno());
				
				obj.setWuCardNo(wucardno);
				
				if(view.getSecurityQuestion()!=null){
					obj.setQuestionLabel("Security Question");
					obj.setQuestion(": " +view.getSecurityQuestion());
				}
				if(view.getSecurityAnswer()!=null){
					obj.setAnswerLabel("Security Answer");
					obj.setAnswer(": " +view.getSecurityAnswer());
				}
				if(view.getSenderMessage()!=null){
					obj.setMessageLabel("Sender Message");
					obj.setMessage(view.getSenderMessage());
				}else{
					obj.setMessage(".");
				}
			
				
				if(view.getSecurityQuestion()!=null){
					obj.setQuestionLabel("Security Question");
					obj.setQuestion(": " +view.getSecurityQuestion());
				}
				if(view.getSecurityAnswer()!=null){
					obj.setAnswerLabel("Security Answer");
					obj.setAnswer(": " +view.getSecurityAnswer());
				}
			
				// setting beneficiary Address
				StringBuffer  address = new StringBuffer();
				if(view.getBeneStateName() != null){
					address.append(view.getBeneStateName());
					address.append(",  ");
				}
				if(view.getBeneCityName() != null){
					address.append(view.getBeneCityName());
					address.append(",  ");
				}
				if(view.getBeneDistrictName() != null){
					address.append(view.getBeneDistrictName());
				}
				obj.setAddress(address.toString());

				//setting payment channel 
				StringBuffer paymentChannel = new StringBuffer();
				if(view.getRemittanceDescription() != null){
					paymentChannel.append(view.getRemittanceDescription());
					//paymentChannel.append(" - ");
				}
		/*		if(view.getDeliveryDescription() != null){
					paymentChannel.append(view.getDeliveryDescription());
				}*/
				obj.setPaymentChannel(paymentChannel.toString());

				String currencyAndAmount=null;
				BigDecimal foreignTransationAmount=GetRound.roundBigDecimal((view.getForeignTransactionAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getForeignCurrencyId()));
				if(view.getCurrencyQuoteName()!=null && foreignTransationAmount!=null){
					currencyAndAmount = view.getCurrencyQuoteName()+"     ******"+foreignTransationAmount;
				}
				obj.setCurrencyAndAmount(currencyAndAmount);

				if(view.getCurrencyQuoteName()!=null && currencyQuoteName!=null && view.getExchangeRateApplied()!=null){
					obj.setExchangeRate(view.getCurrencyQuoteName()+" / "+currencyQuoteName+"     "+view.getExchangeRateApplied().toString());
				}

				if(view.getLocalTransactionAmount()!=null && view.getLocalTransactionCurrencyId()!=null){
					BigDecimal transationAmount=GetRound.roundBigDecimal((view.getLocalTransactionAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setLocalTransactionAmount(currencyQuoteName+"     ******"+transationAmount.toString());
				}

				if(view.getLocalCommissionAmount()!=null && view.getLocalTransactionCurrencyId()!=null){
					BigDecimal localCommitionAmount=GetRound.roundBigDecimal((view.getLocalCommissionAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setCommision(currencyQuoteName+"     ******"+localCommitionAmount.toString());
				}

				if(view.getLocalChargeAmount()!=null && view.getLocalTransactionCurrencyId()!=null){
					BigDecimal localChargeAmount=GetRound.roundBigDecimal((view.getLocalChargeAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setOtherCharges(currencyQuoteName+"     ******"+localChargeAmount.toString());
				}

				if(view.getLocalNetTransactionAmount()!=null && view.getLocalTransactionCurrencyId()!=null){
					BigDecimal netAmount=GetRound.roundBigDecimal((view.getLocalNetTransactionAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setTotalAmount(currencyQuoteName+"     ******"+netAmount.toString());
				}
				System.out.println("promo amount" +getSendPromoDiscountAmount());
				if(view.getWuPromoDiscount()!=null && view.getLocalTransactionCurrencyId()!=null){
					BigDecimal promoDiscount=GetRound.roundBigDecimal((view.getWuPromoDiscount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setPromotionDiscount(currencyQuoteName+"     ******"+promoDiscount.toString());
				}
				BigDecimal deliveryChargeAmount = GetRound.roundBigDecimal((BigDecimal.ZERO),
						foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
				obj.setDeliveryCharge(currencyQuoteName + "     ******" + deliveryChargeAmount.toString());
				
				

				if (view.getIdProofTypeId().compareTo(new BigDecimal("198")) == 0 || view.getIdProofTypeId().compareTo(new BigDecimal("2000")) == 0) {					
					obj.setIdType("CIVIL ID");
				} else if (view.getIdProofTypeId().compareTo(new BigDecimal("204")) == 0) {
					obj.setIdType("PASSPORT");
				}
				
				obj.setFutherInstructions(view.getInstructions());
				obj.setSourceOfIncome(view.getSourceOfIncomeDesc());
				obj.setIntermediataryBank(view.getBenefeciaryInterBank1());
				obj.setPurposeOfRemittance(view.getWuPurposeOfTransaction());
				obj.setBeneCountry(view.getBeneCountry());
				
				List<CollectionDetailView> collectionDetailList1= iPersonalRemittanceService.getCollectionDetailForRemittanceReceipt(view.getCompanyId(),view.getCollectionDocumentNo(),view.getCollectionDocFinanceYear(),view.getCollectionDocCode());
				CollectionDetailView collectionDetailView = collectionDetailList1.get(0);

				if(collectionDetailView.getNetAmount()!=null && view.getLocalTransactionCurrencyId()!=null){
					BigDecimal collectNetAmount=GetRound.roundBigDecimal((collectionDetailView.getNetAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setNetAmount(currencyQuoteName+"     ******"+collectNetAmount);
				}
				if(collectionDetailView.getPaidAmount()!=null && view.getLocalTransactionCurrencyId()!=null){
					BigDecimal collectPaidAmount=GetRound.roundBigDecimal((collectionDetailView.getPaidAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setPaidAmount(currencyQuoteName+"     ******"+collectPaidAmount);
				}
				if(collectionDetailView.getRefundedAmount()!=null && view.getLocalTransactionCurrencyId()!=null){
					BigDecimal collectRefundAmount=GetRound.roundBigDecimal((collectionDetailView.getRefundedAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setRefundedAmount(currencyQuoteName+"     ******"+collectRefundAmount);
				}

				obj.setSubReport(subReportPath);
				obj.setCollectionDetailList(calculateCollectionMode(view));

				BigDecimal lessLoyaltyEncash = BigDecimal.ZERO;
				BigDecimal amountPayable = BigDecimal.ZERO;
				List<CollectionPaymentDetailsView> collectionPmtDetailList= iPersonalRemittanceService.getCollectionPaymentDetailForRemittanceReceipt(view.getCompanyId(),view.getCollectionDocumentNo(),view.getCollectionDocFinanceYear(),view.getCollectionDocCode());
				for(CollectionPaymentDetailsView collPaymentDetailsView: collectionPmtDetailList){
					if(collPaymentDetailsView.getCollectionMode().equalsIgnoreCase(Constants.VOCHERCODE)){
						lessLoyaltyEncash = collPaymentDetailsView.getCollectAmount();
						amountPayable=amountPayable.add(collPaymentDetailsView.getCollectAmount());
					}else{
						amountPayable=amountPayable.add(collPaymentDetailsView.getCollectAmount());
					}
				}
				if(lessLoyaltyEncash.compareTo(BigDecimal.ZERO)==0){
					obj.setLessLoyaltyEncasement(null);					
				}else{
					BigDecimal loyaltyAmount=GetRound.roundBigDecimal((lessLoyaltyEncash),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setLessLoyaltyEncasement(currencyQuoteName+"     ******"+loyaltyAmount);
				}

				if(amountPayable!=null && currencyQuoteName!=null && view.getLocalTransactionCurrencyId()!=null){
					BigDecimal payable=GetRound.roundBigDecimal((amountPayable),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setAmountPayable(currencyQuoteName+"     ******"+payable);
				}

				try {
					if (view.getCustomerSignatureClob() != null) {
						obj.setSignature(view.getCustomerSignatureClob().getSubString(1,(int) view.getCustomerSignatureClob().length()));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}

				if(employeeList!=null && employeeList.size()>0){
					try {
						Employee employee = employeeList.get(0);
						if (employee.getSignatureSpecimenClob() != null) {
							obj.setCashierSignature(employee.getSignatureSpecimenClob().getSubString(1,(int) employee.getSignatureSpecimenClob().length()));
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				
				// Company Information
				List<CompanyMaster> companyMaster =	iPersonalRemittanceService.getCompanyMaster(sessionStateManage.getCompanyId());
				StringBuffer engCompanyInfo = null;	
				StringBuffer arabicCompanyInfo=null;
				if(companyMaster.size()>0){
					engCompanyInfo = new StringBuffer();				
					if(companyMaster.get(0).getAddress1()!=null&&companyMaster.get(0).getAddress1().length()>0){
						engCompanyInfo=engCompanyInfo.append(companyMaster.get(0).getAddress1()+",");
					}
					if(companyMaster.get(0).getAddress2()!=null&&companyMaster.get(0).getAddress2().length()>0){
						engCompanyInfo=engCompanyInfo.append(companyMaster.get(0).getAddress2()+",");
					}
					if(companyMaster.get(0).getAddress3()!=null&&companyMaster.get(0).getAddress3().length()>0){
						engCompanyInfo=engCompanyInfo.append(companyMaster.get(0).getAddress3()+",");
					}
					if(companyMaster.get(0).getRegistrationNumber()!=null&&companyMaster.get(0).getRegistrationNumber().length()>0){
						engCompanyInfo=engCompanyInfo.append("C.R. "+companyMaster.get(0).getRegistrationNumber()+",");
					}
					if(companyMaster.get(0).getCapitalAmount()!=null&&companyMaster.get(0).getCapitalAmount().length()>0){
						engCompanyInfo=engCompanyInfo.append("Share Capital-"+companyMaster.get(0).getCapitalAmount());
					}				
					obj.setEngCompanyInfo(engCompanyInfo.toString());
					
					arabicCompanyInfo = new StringBuffer();
					
					if(companyMaster.get(0).getArabicAddress1()!=null&&companyMaster.get(0).getArabicAddress1().length()>0){
						arabicCompanyInfo=arabicCompanyInfo.append(companyMaster.get(0).getArabicAddress1());
					}
					if(companyMaster.get(0).getArabicAddress2()!=null&&companyMaster.get(0).getArabicAddress2().length()>0){
						arabicCompanyInfo=arabicCompanyInfo.append(companyMaster.get(0).getArabicAddress2()+",");
					}
					if(companyMaster.get(0).getArabicAddress3()!=null&&companyMaster.get(0).getArabicAddress3().length()>0){
						arabicCompanyInfo=arabicCompanyInfo.append(companyMaster.get(0).getArabicAddress3()+",");
					}
					if(companyMaster.get(0).getRegistrationNumber()!=null&&companyMaster.get(0).getRegistrationNumber().length()>0){
						arabicCompanyInfo=arabicCompanyInfo.append(Constants.CR + " " +companyMaster.get(0).getRegistrationNumber()+",");
					}					
					if(companyMaster.get(0).getCapitalAmount()!=null&&companyMaster.get(0).getCapitalAmount().length()>0){
						arabicCompanyInfo=arabicCompanyInfo.append(Constants.Share_Capital + " " +companyMaster.get(0).getCapitalAmount());
					}
					obj.setArabicCompanyInfo(arabicCompanyInfo.toString());
				}
				
				if(obj.getFirstName() == null || obj.getFirstName().isEmpty()){
					List<CutomerDetailsView> customerList = iPersonalRemittanceService.getCustomerDetails(view.getCustomerId());

					if (customerList.size() > 0) {
						CutomerDetailsView cust = customerList.get(0);
						obj.setFirstName(cust.getCustomerName());
						if(cust.getContactNumber()!=null && !cust.getContactNumber().trim().equalsIgnoreCase("")){
							obj.setMobileNo(new BigDecimal(cust.getContactNumber()));
						}
						obj.setCivilId(cust.getIdNumber());
						Date sysdate1 = cust.getIdExp();
						if(sysdate1 != null){
							obj.setIdExpiryDate(new SimpleDateFormat("dd/MM/yyyy").format(sysdate1));
						}
					}
				}
				
				//Terms & conditions
				
				List<WUReportTermsAndConditions> engtcList = new ArrayList<WUReportTermsAndConditions>();
				List<WURemittanceReportBean> engtcList1 = new ArrayList<WURemittanceReportBean>();
				
				engtcList = iwuh2hService.getWUReportTermsAndConditions();
				for(WUReportTermsAndConditions wutc:engtcList){
					WURemittanceReportBean wubean = new WURemittanceReportBean();
					
					String replace1 =  wutc.getEnglishMessage().replace("XXXTRD", "®");
					String replace2 =  replace1.replace("XXXDQ", "\"");
					String replace3 =  replace2.replace("XXXSQ", "\'");

					wubean.setEnglishMessage(replace3);
					engtcList1.add(wubean);
				}
				
				List<WUReportTermsAndConditions> arabictcList = new ArrayList<WUReportTermsAndConditions>();
				List<WURemittanceReportBean> arabictcList1 = new ArrayList<WURemittanceReportBean>();
				
				//arabictcList = iwuh2hService.getWUReportTermsAndConditions();
				for(WUReportTermsAndConditions wutc1:engtcList){
					WURemittanceReportBean wubean1 = new WURemittanceReportBean();
					wubean1.setArabicMessage(wutc1.getArabicMessage());
					arabictcList1.add(wubean1);
				}
				
				String wulogo = realPath + Constants.WU_LOGO;
				obj.setWuLogoPath(wulogo);
				obj.setEnglishTCList(engtcList1);
				obj.setArabicTCList(arabictcList1);
				remittanceApplList.add(obj);
			}

			//for Main Remittance Receipt report (Remittance Receipt and Fc Sale Application)
			WURemittanceReceiptSubreport remittanceObj = new WURemittanceReceiptSubreport();
			remittanceObj.setWaterMarkLogoPath(waterMark);
			remittanceObj.setWaterMarkCheck(true);
			remittanceObj.setRemittanceApplList(remittanceApplList);
			remittanceObj.setSubReport(subReportPath);

			if(remittanceApplList!=null && remittanceApplList.size()>0){
				remittanceObj.setRemittanceReceiptCheck(true);
			}
			wuRemittanceReceiptSubreportList.add(remittanceObj);

		} else {
			setErrorMessage(currencyQuoteName);
			RequestContext.getCurrentInstance().execute("noDataForReport.show();");
			return;
		}

	}
	
	
	public void reportReceive(WUH2HReceiveTxnDataTable dataTable){
		try{
			generateWUReceiveReport(dataTable.getCollectionDocumentNo(),dataTable.getDocumentFinanceYear(),dataTable.getCollectionDocumentCode(),dataTable.getCompanyId(),dataTable.getApplicationCountryId());
				
		}catch(Exception e){
			log.info(""+e.getMessage());			
			RequestContext.getCurrentInstance().execute("report.show();");
			return ;
		}
	} 
	
	public void whh2hReceiveMoneyReceiptReportInit() throws JRException {

		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(remittanceReceiptSubreportList1);
		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath = ctx.getRealPath("//");
		String reportPath = realPath +"//reports//design//wureceivemainreport.jasper";
		System.out.println(reportPath);
		jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap(),beanCollectionDataSource);
	}
	
	public void generateWUReceiveReport(BigDecimal documentno, BigDecimal financeYear, BigDecimal documentCode,BigDecimal companyId,BigDecimal applicationCountryId){
		ServletOutputStream servletOutputStream=null;
			try {
				
			fetchWUReceiveReceiptReportData(documentno,financeYear,String.valueOf(documentCode),companyId,applicationCountryId);
			whh2hReceiveMoneyReceiptReportInit();
			jasperPrint.getPages().remove(1);
			byte[] pdfasbytes = JasperExportManager.exportReportToPdf(jasperPrint);
			

			HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			httpServletResponse.setHeader("Cache-Control","cache, must-revalidate");
			httpServletResponse.addHeader("Content-disposition","attachment; filename=WUReceiveMoneyReprintReceipt.pdf");
			servletOutputStream=httpServletResponse.getOutputStream();  
			JasperExportManager.exportReportToPdfStream(jasperPrint,servletOutputStream);
			FacesContext.getCurrentInstance().responseComplete();

		} catch (NullPointerException ne) {
			log.info("Exception in generateWUReceiveReport:" + ne.getMessage());
			setErrorMessage("Method Name::generateWUReceiveReport");
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
		} catch (Exception exception) {
			log.info("Exception in generateWUReceiveReport:" + exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}finally{
			if(servletOutputStream!=null){
				try {
					servletOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	private List<WURemittanceReceiptSubreport> remittanceReceiptSubreportList1;
private void fetchWUReceiveReceiptReportData(BigDecimal documentNumber,BigDecimal finaceYear, String documentCode, BigDecimal companyId, BigDecimal applicationCountryId) throws Exception{ 
		
	remittanceReceiptSubreportList1 = new CopyOnWriteArrayList<WURemittanceReceiptSubreport>();
		List<ReceiveReceiptView> remittanceApplicationList = new ArrayList<ReceiveReceiptView>();
		List<WURemittanceReportBean> remittanceApplList = new ArrayList<WURemittanceReportBean>();
		List<WURemittanceReportBean> fcsaleAppList = new ArrayList<WURemittanceReportBean>();
		HttpSession session = sessionStateManage.getSession();
		String  wucardno = (String)session.getAttribute("WUCardno");

		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath = ctx.getRealPath("//");
		String subReportPath = realPath + Constants.SUB_REPORT_PATH;
		String waterMark = realPath+Constants.REPORT_WATERMARK_LOGO;

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String currentDate = dateFormat.format(new Date());
		int noOfTransactions = 0;

		String currencyQuoteName = generalService.getCurrencyQuote(new BigDecimal(sessionStateManage.getCurrencyId()));
		List<Employee> employeeList = iPersonalRemittanceService.getEmployeeDetails(sessionStateManage.getUserName());

		List<ReceiveReceiptView> remittanceViewlist = iwuh2hService.getReceiveReceiptData(documentNumber, finaceYear, new BigDecimal(documentCode),companyId,applicationCountryId);
		if (remittanceViewlist.size() > 0) {
			for (ReceiveReceiptView remittanceAppview : remittanceViewlist) {
				remittanceApplicationList.add(remittanceAppview);
				noOfTransactions= noOfTransactions+1;
			}
			//remittance List
			for (ReceiveReceiptView view : remittanceApplicationList) {
				WURemittanceReportBean obj = new WURemittanceReportBean();

				// setting customer reference	
				StringBuffer customerReff = new StringBuffer();
				if(view.getCustomerReference() != null){
					customerReff.append(view.getCustomerReference());
					customerReff.append(" / ");
				}

				StringBuffer customerName = new StringBuffer();
			    List<Customer> customerList=	icustomerRegistrationService.getCustomerInfo(view.getCustomerId());			    
			    if(customerList.size()>0){			    	
			    	if(customerList.get(0).getFirstName() != null){
						customerName.append(" ");
						customerName.append(customerList.get(0).getFirstName());
						customerReff.append(" ");
						customerReff.append(customerList.get(0).getFirstName());
					}
					if(customerList.get(0).getMiddleName() != null){
						customerName.append(" ");
						customerName.append(customerList.get(0).getMiddleName());
						customerReff.append(" ");
						customerReff.append(customerList.get(0).getMiddleName());
					}
					if(customerList.get(0).getLastName()!=null){
						customerName.append(" ");
						customerName.append(customerList.get(0).getLastName());
						customerReff.append(" ");
						customerReff.append(customerList.get(0).getLastName());
					}						
					obj.setMobileNo(customerList.get(0).getContactNumber());
					obj.setCivilId(customerList.get(0).getCivilId());
					
					List<CustomerIdProof> idproofList = icustomerRegistrationService.getCustomerIdProofList(view.getCustomerId());
					
					if(idproofList.size()>0){
						if(idproofList.get(0).getIdentityInt().equals(customerList.get(0).getCivilId())){
							Date expdate = idproofList.get(0).getIdentityExpiryDate();
							obj.setIdExpiryDate(new SimpleDateFormat("dd/MM/yyy").format(expdate));
							
							
						if (idproofList.get(0).getFsBizComponentDataByIdentityTypeId().getComponentDataId().compareTo(new BigDecimal("198")) == 0 || idproofList.get(0).getFsBizComponentDataByIdentityTypeId().getComponentDataId().compareTo(new BigDecimal("2000")) == 0) {					
							obj.setIdType("CIVIL ID");
						} else if (idproofList.get(0).getFsBizComponentDataByIdentityTypeId().getComponentDataId().compareTo(new BigDecimal("204")) == 0) {
							obj.setIdType("PASSPORT");
						}
						}
					}
			    }			    
			    
				//obj.setFirstName(customerList.get(0).getCustomerId()+" /" +view.getCustomerName());
			    obj.setFirstName(view.getCustomerReference()+" /" +view.getCustomerName()); 
				//setCustomerNameForReport(view.getCustomerName());
				obj.setLocation(sessionStateManage.getLocation());

				//setting receipt Number
				StringBuffer receiptNo = new StringBuffer();
				if(view.getDocumentFinanceYear()!=null){
					receiptNo.append(view.getDocumentFinanceYear());
					receiptNo.append(" / ");
				}
				if(view.getCollectonDocumentNo()!=null){
					receiptNo.append(view.getCollectonDocumentNo());
				}
				obj.setReceiptNo(receiptNo.toString());

				// setting transaction Number
				StringBuffer transactionNo = new StringBuffer();
				if(view.getDocumentFinanceYear()!=null){
					transactionNo.append(view.getDocumentFinanceYear());
					transactionNo.append(" / ");
				}
				if(view.getDocumentNo()!=null){
					transactionNo.append(view.getDocumentNo());
				}
				obj.setTransactionNo(transactionNo.toString());
					
				Date txndate = view.getPaymentDate();
                if (txndate != null) {
                    obj.setDate(new SimpleDateFormat("dd/MM/yyy").format(txndate));
                }
				
				
				//Sender Details
								
				StringBuffer sendername = new StringBuffer();				
				if(view.getWuSenderFirstName()!=null){
					sendername.append(view.getWuSenderFirstName()+" ");
				}
				if(view.getWuSenderLastName()!=null){
					sendername.append(view.getWuSenderLastName());
				}
				obj.setBeneficiaryName(sendername.toString());
				
				obj.setSenderCountry(view.getWuSenderCountry());
				obj.setSenderCity(view.getWuSenderCity());
				obj.setSenderState(view.getWuSenderState());
				obj.setSenderMobileno(view.getWuSenderMobile());
				//obj.setSenderMessage(view.getWuSenderMessage());
				if(view.getWuSenderMessage()!=null){
					obj.setMessageLabel("Sender Message");
					obj.setMessage(view.getWuSenderMessage());
				}else{
					obj.setMessage(".");
				}
				
				StringBuffer address = new StringBuffer();
				/*if(getSendAddressLine1()!=null){
					address.append(getSendAddressLine1()+",");
				}
				if(getSendAddressLine2()!=null){
					address.append(getSendAddressLine2());
				}*/
				obj.setSenderAddress(address.toString());
				
				/*obj.setBenefeciaryBankName(view.getBeneficiaryBank());
				obj.setBenefeciaryBranchName(view.getBenefeciaryBranch());
				obj.setBenefeciaryAccountNumber(view.getBenefeciaryAccountNo());
				obj.setNoOfTransaction(new BigDecimal(noOfTransactions));
				obj.setPhoneNumber(view.getPhoneNumber());*/
				obj.setUserName(sessionStateManage.getUserName());
				//obj.setPinNo(view.getPinNo() );
				obj.setMtcn(view.getMtcno());				

				//setting payment channel 
				/*StringBuffer paymentChannel = new StringBuffer();
				if(view.getRemittanceDescription() != null){
					paymentChannel.append(view.getRemittanceDescription());
					paymentChannel.append(" - ");
				}
				if(view.getDeliveryDescription() != null){
					paymentChannel.append(view.getDeliveryDescription());
				}
				obj.setPaymentChannel(paymentChannel.toString());
*/
				String currencyAndAmount = null;
				BigDecimal foreignTransationAmount=GetRound.roundBigDecimal((view.getForeignTransactionAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getForeignCurrencyid()));
				if(view.getForeignCurrencyQuoteName()!=null && foreignTransationAmount!=null){
					currencyAndAmount = view.getForeignCurrencyQuoteName()+"     ******"+foreignTransationAmount;
				}
				obj.setCurrencyAndAmount(currencyAndAmount);

				if(view.getForeignCurrencyQuoteName()!=null && currencyQuoteName!=null && view.getExchangeRate()!=null){
					obj.setExchangeRate(view.getForeignCurrencyQuoteName()+" / "+currencyQuoteName+"     "+view.getExchangeRate().toString());
				}

				if(view.getLocalTransactionAmount()!=null && view.getLocalCurrencyId()!=null){
					BigDecimal transationAmount=GetRound.roundBigDecimal((view.getLocalTransactionAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalCurrencyId()));
					obj.setLocalTransactionAmount(currencyQuoteName+"     ******"+transationAmount.toString());
				}

				if(view.getLocalCommissionAmount()!=null && view.getLocalCurrencyId()!=null){
					BigDecimal localCommitionAmount=GetRound.roundBigDecimal((view.getLocalCommissionAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalCurrencyId()));
					obj.setCommision(currencyQuoteName+"     ******"+localCommitionAmount.toString());
				}

				/*if(view.getLocalChargeAmount()!=null && view.getLocalTransactionCurrencyId()!=null){
					BigDecimal localChargeAmount=GetRound.roundBigDecimal((view.getLocalChargeAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setOtherCharges(currencyQuoteName+"     ******"+localChargeAmount.toString());
				}*/

				if(view.getLocalNetAmount()!=null && view.getLocalCurrencyId()!=null){
					BigDecimal netAmount=GetRound.roundBigDecimal((view.getLocalNetAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalCurrencyId()));
					obj.setTotalAmount(currencyQuoteName+"     ******"+netAmount.toString());
				}

				if(view.getLocalNetAmount()!=null && view.getLocalCurrencyId()!=null){
					BigDecimal collectNetAmount=GetRound.roundBigDecimal((view.getLocalNetAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalCurrencyId()));
					obj.setNetAmount(currencyQuoteName+"     ******"+collectNetAmount);
				}

				if(view.getPaidAmount()!=null && view.getLocalCurrencyId()!=null){
					BigDecimal collectPaidAmount=GetRound.roundBigDecimal((view.getPaidAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalCurrencyId()));
					obj.setPaidAmount(currencyQuoteName+"     ******"+collectPaidAmount);
				}
				
				if(view.getPaidAmount()!=null && currencyQuoteName!=null && view.getLocalCurrencyId()!=null){
					BigDecimal payable=GetRound.roundBigDecimal((view.getPaidAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalCurrencyId()));
					obj.setAmountPayable(currencyQuoteName+"     ******"+payable);
				}
				
					BigDecimal localCommitionAmount=GetRound.roundBigDecimal((BigDecimal.ZERO),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalCurrencyId()));
					obj.setCommision(currencyQuoteName+"     ******"+localCommitionAmount.toString());
				
				
				
				/*if(getSendPlusChargeAmount()!=null){
					obj.setOtherCharges(currencyQuoteName+"     ******"+getSendPlusChargeAmount().toString());
				}
				
				if(getSendExchangeRate()!=null){
					obj.setExchangeRate(currencyQuoteName+"     "+getSendExchangeRate().toString());
				}*/
				obj.setPaymentMode("Cash");
				obj.setApprovalNo(view.getApprovalNo());				
				//obj.setPurposeOfRemittance("Family Maintenance / Savings");
				
				/*if(!getWuPurposeTransaction().equalsIgnoreCase("others")){
					obj.setPurposeOfRemittance(getWuPurposeTransaction());
				}else{
					obj.setPurposeOfRemittance(getOtherPurposeTransaction());
				}*/
				
				obj.setPurposeOfRemittance(view.getWuPurposeOfTransaction());
				
				obj.setMobileNo(new BigDecimal("7357500709"));
				obj.setSubReport(subReportPath);
				try {
					if (view.getCustomerSignatureClob() != null) {
						obj.setSignature(view.getCustomerSignatureClob().getSubString(1,(int) view.getCustomerSignatureClob().length()));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if(employeeList!=null && employeeList.size()>0){
					try {
						Employee employee = employeeList.get(0);
						if (employee.getSignatureSpecimenClob() != null) {
							obj.setCashierSignature(employee.getSignatureSpecimenClob().getSubString(1,(int) employee.getSignatureSpecimenClob().length()));
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				//For Company information ADDED BY VISWA --START
				List<CompanyMaster> companyMaster =	iPersonalRemittanceService.getCompanyMaster(sessionStateManage.getCompanyId());
				StringBuffer engCompanyInfo = null;	
				StringBuffer arabicCompanyInfo=null;
				if(companyMaster.size()>0){
					engCompanyInfo = new StringBuffer();				
					if(companyMaster.get(0).getAddress1()!=null&&companyMaster.get(0).getAddress1().length()>0){
						engCompanyInfo=engCompanyInfo.append(companyMaster.get(0).getAddress1()+",");
					}
					if(companyMaster.get(0).getAddress2()!=null&&companyMaster.get(0).getAddress2().length()>0){
						engCompanyInfo=engCompanyInfo.append(companyMaster.get(0).getAddress2()+",");
					}
					if(companyMaster.get(0).getAddress3()!=null&&companyMaster.get(0).getAddress3().length()>0){
						engCompanyInfo=engCompanyInfo.append(companyMaster.get(0).getAddress3()+",");
					}
					if(companyMaster.get(0).getRegistrationNumber()!=null&&companyMaster.get(0).getRegistrationNumber().length()>0){
						engCompanyInfo=engCompanyInfo.append("C.R. "+companyMaster.get(0).getRegistrationNumber()+",");
					}
					if(companyMaster.get(0).getCapitalAmount()!=null&&companyMaster.get(0).getCapitalAmount().length()>0){
						engCompanyInfo=engCompanyInfo.append("Share Capital-"+companyMaster.get(0).getCapitalAmount());
					}				
					obj.setEngCompanyInfo(engCompanyInfo.toString());
					
					arabicCompanyInfo = new StringBuffer();
					
					if(companyMaster.get(0).getArabicAddress1()!=null&&companyMaster.get(0).getArabicAddress1().length()>0){
						arabicCompanyInfo=arabicCompanyInfo.append(companyMaster.get(0).getArabicAddress1());
					}
					if(companyMaster.get(0).getArabicAddress2()!=null&&companyMaster.get(0).getArabicAddress2().length()>0){
						arabicCompanyInfo=arabicCompanyInfo.append(companyMaster.get(0).getArabicAddress2()+",");
					}
					if(companyMaster.get(0).getArabicAddress3()!=null&&companyMaster.get(0).getArabicAddress3().length()>0){
						arabicCompanyInfo=arabicCompanyInfo.append(companyMaster.get(0).getArabicAddress3()+",");
					}
					if(companyMaster.get(0).getRegistrationNumber()!=null&&companyMaster.get(0).getRegistrationNumber().length()>0){
						arabicCompanyInfo=arabicCompanyInfo.append(Constants.CR + " " +companyMaster.get(0).getRegistrationNumber()+",");
					}					
					if(companyMaster.get(0).getCapitalAmount()!=null&&companyMaster.get(0).getCapitalAmount().length()>0){
						arabicCompanyInfo=arabicCompanyInfo.append(Constants.Share_Capital + " " +companyMaster.get(0).getCapitalAmount());
					}
					obj.setArabicCompanyInfo(arabicCompanyInfo.toString());
				}
			
				if(obj.getFirstName() == null || obj.getFirstName().isEmpty()){
					List<CutomerDetailsView> customerList1 = iPersonalRemittanceService.getCustomerDetails(view.getCustomerId());
					if (customerList1.size() > 0) {
						CutomerDetailsView cust = customerList1.get(0);
						obj.setFirstName(cust.getCustomerName());
						if(cust.getContactNumber()!=null && !cust.getContactNumber().trim().equalsIgnoreCase("")){
							obj.setMobileNo(new BigDecimal(cust.getContactNumber()));
						}
						obj.setCivilId(cust.getIdNumber());
						Date sysdate1 = cust.getIdExp();
						if(sysdate1 != null){
							obj.setIdExpiryDate(new SimpleDateFormat("dd/MM/yyyy").format(sysdate1));
						}
					}
				}
				obj.setWuCardNo(wucardno);
				
				//Terms & conditions
				
				List<WUReportTermsAndConditions> engtcList = new ArrayList<WUReportTermsAndConditions>();
				List<WURemittanceReportBean> engtcList1 = new ArrayList<WURemittanceReportBean>();
				
				engtcList = iwuh2hService.getWUReportTermsAndConditions();
				for(WUReportTermsAndConditions wutc:engtcList){
					WURemittanceReportBean wubean = new WURemittanceReportBean();
					String replace1 =  wutc.getEnglishMessage().replace("XXXTRD", "®");
					String replace2 =  replace1.replace("XXXDQ", "\"");
					String replace3 =  replace2.replace("XXXSQ", "\'");

					wubean.setEnglishMessage(replace3);

					engtcList1.add(wubean);
				}
				
				List<WUReportTermsAndConditions> arabictcList = new ArrayList<WUReportTermsAndConditions>();
				List<WURemittanceReportBean> arabictcList1 = new ArrayList<WURemittanceReportBean>();
				
				//arabictcList = iwuh2hService.getWUReportTermsAndConditions();
				for(WUReportTermsAndConditions wutc1:engtcList){
					WURemittanceReportBean wubean1 = new WURemittanceReportBean();
					wubean1.setArabicMessage(wutc1.getArabicMessage());
					arabictcList1.add(wubean1);
				}
				
				String wulogo = realPath + Constants.WU_LOGO;
				obj.setWuLogoPath(wulogo);
				
				obj.setEnglishTCList(engtcList1);
				obj.setArabicTCList(arabictcList1);
				remittanceApplList.add(obj);
			}

			//for Main Remittance Receipt report (Remittance Receipt and Fc Sale Application)
			WURemittanceReceiptSubreport remittanceObj = new WURemittanceReceiptSubreport();

			remittanceObj.setWaterMarkLogoPath(waterMark);
			remittanceObj.setWaterMarkCheck(true);
			remittanceObj.setRemittanceApplList(remittanceApplList);
			remittanceObj.setSubReport(subReportPath);

			if(remittanceApplList!=null && remittanceApplList.size()>0){
				remittanceObj.setRemittanceReceiptCheck(true);
			}else{
				remittanceObj.setRemittanceReceiptCheck(false);
			}

			remittanceReceiptSubreportList1.add(remittanceObj);

		} else {
			RequestContext.getCurrentInstance().execute("noDataForReport.show();");
			return;
		}

	}
	
	public List<WURemittanceReportBean> calculateCollectionMode(WURemittanceApplicationView viewCollectionObj){	
		List<WURemittanceReportBean> collectionDetailList = new ArrayList<WURemittanceReportBean>();
		List<CollectionPaymentDetailsView> collectionPaymentDetailList= iPersonalRemittanceService.getCollectionPaymentDetailForRemittanceReceipt(viewCollectionObj.getCompanyId(),viewCollectionObj.getCollectionDocumentNo(),viewCollectionObj.getCollectionDocFinanceYear(),viewCollectionObj.getCollectionDocCode());

		int size = collectionPaymentDetailList.size();
		for(CollectionPaymentDetailsView viewObj: collectionPaymentDetailList){
			WURemittanceReportBean obj = new WURemittanceReportBean();
			if(viewObj.getCollectionMode()!=null && viewObj.getCollectionMode().equalsIgnoreCase("K")){
				obj.setCollectionMode(viewObj.getCollectionModeDesc());
				obj.setApprovalNo(viewObj.getApprovalNo());
				obj.setTransactionId(viewObj.getTransactionId());
				obj.setKnetreceiptDateTime(viewObj.getKnetReceiptDatenTime());
				obj.setKnetBooleanCheck(true);
				if(viewObj.getCollectAmount()!=null && viewCollectionObj.getLocalTransactionCurrencyId()!=null){
					BigDecimal collectAmount=GetRound.roundBigDecimal((viewObj.getCollectAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(viewCollectionObj.getLocalTransactionCurrencyId()));
					obj.setCollectAmount(collectAmount);
				}
			}else{
				obj.setCollectionMode(viewObj.getCollectionModeDesc());
				obj.setKnetBooleanCheck(false);
				if(viewObj.getCollectAmount()!=null && viewCollectionObj.getLocalTransactionCurrencyId()!=null){
					BigDecimal collectAmount=GetRound.roundBigDecimal((viewObj.getCollectAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(viewCollectionObj.getLocalTransactionCurrencyId()));
					obj.setCollectAmount(collectAmount);
				}
			}
			if(size>1){
				obj.setDrawLine(true);
			}else{
				obj.setDrawLine(false);
			}
			collectionDetailList.add(obj);
			size = size-1;
		}
		return collectionDetailList;
	}	
	
	
	private String sendPromoDiscountAmount;
	private String sendMtcno;
	private String sendWUcardNo;
	private BigDecimal senderPointsEarned;
	private BigDecimal senderNewPointsEarned;
	private String errorMessage;

	public String getSendPromoDiscountAmount() {
		return sendPromoDiscountAmount;
	}

	public void setSendPromoDiscountAmount(String sendPromoDiscountAmount) {
		this.sendPromoDiscountAmount = sendPromoDiscountAmount;
	}

	public BigDecimal getSenderPointsEarned() {
		return senderPointsEarned;
	}

	public void setSenderPointsEarned(BigDecimal senderPointsEarned) {
		this.senderPointsEarned = senderPointsEarned;
	}

	public String getSendMtcno() {
		return sendMtcno;
	}

	public void setSendMtcno(String sendMtcno) {
		this.sendMtcno = sendMtcno;
	}

	public String getSendWUcardNo() {
		return sendWUcardNo;
	}

	public void setSendWUcardNo(String sendWUcardNo) {
		this.sendWUcardNo = sendWUcardNo;
	}

	public BigDecimal getSenderNewPointsEarned() {
		return senderNewPointsEarned;
	}

	public void setSenderNewPointsEarned(BigDecimal senderNewPointsEarned) {
		this.senderNewPointsEarned = senderNewPointsEarned;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	private Date customerExpDate;


	public Date getCustomerExpDate() {
		return customerExpDate;
	}

	public void setCustomerExpDate(Date customerExpDate) {
		this.customerExpDate = customerExpDate;
	}
	
	private Date fromDate;
	private Date toDate;


	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	
	private Date currentDate = new Date();

	public Date getCurrentDate() {
	    return currentDate;
	}
	
	private String terminalAddress;
	
	
	public String getTerminalAddress() {
		return terminalAddress;
	}

	public void setTerminalAddress(String terminalAddress) {
		this.terminalAddress = terminalAddress;
	}
	
	@Autowired
	IForeignCurrencyPurchaseService<T> foreignCurrencyPurchaseService;
	
	public String fetchTerminalAddress() {

		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		setTerminalAddress(ipAddress);
		return ipAddress;
	}
	private BigDecimal financeYear;
	public BigDecimal getFinaceYear() {
		try {
			List<UserFinancialYear> financialYearList = foreignCurrencyPurchaseService.getUserFinancialYear(new Date());
			log.info("financialYearList :" + financialYearList.size());
			if (financialYearList != null) {
				financeYear = financialYearList.get(0).getFinancialYear();
				setFinanceYear(financeYear);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return financeYear;
	}


	public void setFinanceYear(BigDecimal financeYear) {
		this.financeYear = financeYear;
	}
	private List<JRPrintPage> removeBlankPage(List<JRPrintPage> pages) {
		List<JRPrintPage> pagenew = new ArrayList<>();
	      for (Iterator<JRPrintPage> i=pages.iterator(); i.hasNext();) {
	          JRPrintPage page = i.next();
	          if (page.getElements().size() == 0){
	              i.remove();
	          }
	          pagenew.add(page);
	      }
	      return pagenew;
	  }
	
	
}
