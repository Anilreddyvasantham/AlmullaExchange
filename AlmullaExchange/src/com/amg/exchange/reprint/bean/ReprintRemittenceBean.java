package com.amg.exchange.reprint.bean;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
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

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CutomerDetailsView;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.foreigncurrency.service.ForeignLocalCurrencyDenominationService;
import com.amg.exchange.foreigncurrency.service.IForeignCurrencyPurchaseService;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.remittance.bean.PurposeOfRemittanceReportBean;
import com.amg.exchange.remittance.bean.RemittanceReceiptSubreport;
import com.amg.exchange.remittance.bean.RemittanceReportBean;
import com.amg.exchange.remittance.model.CollectionDetailView;
import com.amg.exchange.remittance.model.CollectionPaymentDetailsView;
import com.amg.exchange.remittance.model.PurposeOfRemittanceView;
import com.amg.exchange.remittance.model.RemittanceApplicationView;
import com.amg.exchange.remittance.service.IPersonalRemittanceService;
import com.amg.exchange.reprint.service.IReprintService;
import com.amg.exchange.stoppayment.bean.StoppaymentCollectionBean;
import com.amg.exchange.treasury.service.ISpecialCustomerDealRequestService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.GetRound;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

/**
 * @author Mohan
 * 
 */
@Component("reprintRemittenceBean")
@Scope("session")
public class ReprintRemittenceBean<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private static final Logger LOGGER = Logger.getLogger(StoppaymentCollectionBean.class);
	private BigDecimal documentNo;
	//private BigDecimal transactionNo;
	private BigDecimal docYear;
	private String errorMessage;
	private Boolean boodataNotFound=false;
	private List<UserFinancialYear> userFinancialYearList = new ArrayList<UserFinancialYear>();
	private SessionStateManage session = new SessionStateManage();
	private List<RemittanceReceiptSubreport> remittanceReceiptSubreportList;
	private List<CollectionDetailView> collectionViewList = new CopyOnWriteArrayList<CollectionDetailView>();
	private List<CollectionDetailView> paymentReportList = new CopyOnWriteArrayList<CollectionDetailView>();
	IForeignCurrencyPurchaseService<T> foreignCurrencyPurchaseService;
	IReprintService reprintService;
	IPersonalRemittanceService iPersonalRemittanceService;

	@Autowired
	ForeignLocalCurrencyDenominationService<T> foreignLocalCurrencyDenominationService;
	@Autowired
	IGeneralService<T> generalService;
	@Autowired
	ISpecialCustomerDealRequestService<T> specialCustomerDealRequestService;

	public BigDecimal getDocYear() {
		return docYear;
	}

	public void setDocYear(BigDecimal docYear) {
		this.docYear = docYear;
	}

	public IReprintService getReprintService() {
		return reprintService;
	}

	@Autowired
	public void setReprintService(IReprintService reprintService) {
		this.reprintService = reprintService;
	}

	public IPersonalRemittanceService getiPersonalRemittanceService() {
		return iPersonalRemittanceService;
	}

	@Autowired
	public void setiPersonalRemittanceService(IPersonalRemittanceService iPersonalRemittanceService) {
		this.iPersonalRemittanceService = iPersonalRemittanceService;
	}

	public List<RemittanceReceiptSubreport> getRemittanceReceiptSubreportList() {
		return remittanceReceiptSubreportList;
	}

	public void setRemittanceReceiptSubreportList(List<RemittanceReceiptSubreport> remittanceReceiptSubreportList) {
		this.remittanceReceiptSubreportList = remittanceReceiptSubreportList;
	}

	public List<CollectionDetailView> getCollectionViewList() {
		return collectionViewList;
	}

	public void setCollectionViewList(List<CollectionDetailView> collectionViewList) {
		this.collectionViewList = collectionViewList;
	}

	public List<CollectionDetailView> getPaymentReportList() {
		return paymentReportList;
	}

	public void setPaymentReportList(List<CollectionDetailView> paymentReportList) {
		this.paymentReportList = paymentReportList;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public BigDecimal getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}

	/*public BigDecimal getTransactionNo() {
		return transactionNo;
	}

	public void setTransactionNo(BigDecimal transactionNo) {
		this.transactionNo = transactionNo;
	}*/

	public List<UserFinancialYear> getUserFinancialYearList() {
		userFinancialYearList=	getForeignCurrencyPurchaseService().getAllDocumentYear();
		return userFinancialYearList;
	}

	public void setUserFinancialYearList(List<UserFinancialYear> userFinancialYearList) {
		this.userFinancialYearList = userFinancialYearList;
	}

	public IForeignCurrencyPurchaseService<T> getForeignCurrencyPurchaseService() {
		return foreignCurrencyPurchaseService;
	}

	@Autowired
	public void setForeignCurrencyPurchaseService(IForeignCurrencyPurchaseService<T> foreignCurrencyPurchaseService) {
		this.foreignCurrencyPurchaseService = foreignCurrencyPurchaseService;
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void pageNavigationforReprint() {
		LOGGER.info("Entering into pageNavigationforReprint method");
		setBoodataNotFound(false);
		clear();
		setUserFinancialYearList(getForeignCurrencyPurchaseService().getAllDocumentYear());
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "reprintRemittence.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../reprint/reprintRemittence.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("Exit into pageNavigationforReprint method");
	}

	public void clickOnExit() throws IOException {
		if (session.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}
	}

	public void generatePersonalRemittanceReceiptReport() throws JRException, IOException, ParseException {
		OutputStream outstream=null;
		try {
			if (getDocumentNo() == null) {
				setBoodataNotFound(true);
				RequestContext.getCurrentInstance().execute("csp.show();");
				setErrorMessage("Please enter Document number");
				return;
			}
			/*if (getDocumentNo() != null && getTransactionNo() != null) {
				setBoodataNotFound(true);
				RequestContext.getCurrentInstance().execute("csp.show();");
				setErrorMessage("Please enter Document number or Transaction Number - not both");
				return;
			}*/
			int i = fetchRemittanceReceiptReportData(getDocumentNo(), getDocYear());
			if (i == 0) {
				setBoodataNotFound(true);
				setErrorMessage("Data not found ");
				RequestContext.getCurrentInstance().execute("csp.show();");
				return;
			} else {
				// fetchRemittanceReceiptReportData(new BigDecimal(216));
				remittanceReceiptReportInit();
				HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
				httpServletResponse.setHeader("Cache-Control", "cache, must-revalidate");
				httpServletResponse.addHeader("Content-disposition", "attachment; filename=RemittanceReceiptReport.pdf");
				/*ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
				JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
				FacesContext.getCurrentInstance().responseComplete();*/

				byte[] pdfasbytes = JasperExportManager.exportReportToPdf(jasperPrint);

				outstream = httpServletResponse.getOutputStream();
				httpServletResponse.setContentType("application/pdf");
				httpServletResponse.setContentLength(pdfasbytes.length);
				String strSettings = "inline;filename=\"RemittanceReceiptReport.pdf\"";
				httpServletResponse.setHeader("Content-Disposition", strSettings);
				outstream.write(pdfasbytes, 0, pdfasbytes.length);
				outstream.flush();
				FacesContext.getCurrentInstance().responseComplete();


			}
		} catch (Exception e) {
			System.out.println("exception" + e.getMessage());
		}finally{
			if(outstream!=null){
				outstream.close();
			}
		}
	}

	private JasperPrint jasperPrint;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void remittanceReceiptReportInit() throws JRException {
		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(remittanceReceiptSubreportList);
		//String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("reports/design/RemittanceReceiptNewReport.jasper");
		//String realPath = ctx.getRealPath("//");
		//String reportPath = realPath +"\\reports\\design\\RemittanceReceiptNewReport.jasper";
		String realPath = ctx.getRealPath("//");
		String reportPath = realPath +"//reports//design//RemittanceReceiptNewReport.jasper";

		jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap(), beanCollectionDataSource);
	}
	
	// to check null
	private String nullCheck(String custname) {
		return custname == null ? "" : custname;
	}

	private int fetchRemittanceReceiptReportData(BigDecimal documentNumber, BigDecimal documentYear) throws Exception{
		LOGGER.info("Document Number=="+documentNumber);
		collectionViewList.clear();
		remittanceReceiptSubreportList = new ArrayList<RemittanceReceiptSubreport>();



		List<RemittanceApplicationView> remittanceApplicationList = new ArrayList<RemittanceApplicationView>();

		List<RemittanceApplicationView> fcsaleList = new ArrayList<RemittanceApplicationView>();

		List<RemittanceReportBean> remittanceApplList = new ArrayList<RemittanceReportBean>();

		List<RemittanceReportBean> fcsaleAppList = new ArrayList<RemittanceReportBean>();

		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath = ctx.getRealPath("//");
		String subReportPath = realPath + Constants.SUB_REPORT_PATH;
		String waterMark = realPath+Constants.REPORT_WATERMARK_LOGO;

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String currentDate = dateFormat.format(new Date());
		int noOfTransactions = 0;

		String currencyQuoteName = generalService.getCurrencyQuote(new BigDecimal(session.getCurrencyId()));


		List<Employee> employeeList = iPersonalRemittanceService.getEmployeeDetails(session.getUserName());

		List<RemittanceApplicationView> remittanceViewlist = iPersonalRemittanceService.getRecordsForRemittanceReceiptReport(documentNumber, documentYear, Constants.DOCUMENT_CODE_FOR_COLLECT_TRANSACTION);
		LOGGER.info("Remittance View List Size is======"+remittanceViewlist.size());
		if (remittanceViewlist.size() > 0) {

			for (RemittanceApplicationView remittanceAppview : remittanceViewlist) {

				if (remittanceAppview.getApplicationTypeDesc().equalsIgnoreCase("REMITTANCE")) {
					remittanceApplicationList.add(remittanceAppview);
					noOfTransactions= noOfTransactions+1;
				} else if (remittanceAppview.getApplicationTypeDesc().equalsIgnoreCase("FOREIGN CURRENCY SALE")) {
					fcsaleList.add(remittanceAppview);
					noOfTransactions= noOfTransactions+1;
				}
			}

			//remittance List
			for (RemittanceApplicationView view : remittanceApplicationList) {

				RemittanceReportBean obj = new RemittanceReportBean();

				if (view.getCustomerReference() != null && view.getFirstName() != null && view.getMiddleName() != null && view.getLastName()!=null) {
					obj.setFirstName(view.getCustomerReference().toString() + " / " + view.getFirstName() + " " + view.getMiddleName()+" "+view.getLastName());
				} else if (view.getCustomerReference() == null && view.getFirstName() != null && view.getMiddleName() != null && view.getLastName()!=null) {
					obj.setFirstName(view.getFirstName() + " "+ view.getMiddleName()+" "+view.getLastName());
				} else if (view.getCustomerReference() == null && view.getFirstName() == null && view.getMiddleName() != null && view.getLastName()!=null) {
					obj.setFirstName(view.getMiddleName()+" "+view.getLastName());
				} else if (view.getCustomerReference() != null && view.getFirstName() == null && view.getMiddleName() != null && view.getLastName()!=null) {
					obj.setFirstName(view.getCustomerReference().toString() + " / " + view.getMiddleName()+" "+view.getLastName());
				} else if (view.getCustomerReference() == null && view.getFirstName() != null && view.getMiddleName() == null && view.getLastName()!=null) {
					obj.setFirstName(view.getFirstName()+" "+view.getLastName());
				} else if (view.getCustomerReference() != null && view.getFirstName() == null && view.getMiddleName() == null && view.getLastName()==null) {
					obj.setFirstName(view.getCustomerReference().toString());
				} else if (view.getCustomerReference() != null && view.getFirstName() != null && view.getMiddleName() == null && view.getLastName()!=null) {
					obj.setFirstName(view.getCustomerReference().toString() + " " + view.getFirstName()+" "+view.getLastName());
				}else if (view.getCustomerReference() != null) {
					obj.setFirstName(view.getCustomerReference().toString() + " / " + nullCheck(view.getFirstName()) + " " + nullCheck(view.getMiddleName()) +" "+ nullCheck(view.getLastName()));
				}
				
				if(view.getContactNumber()!=null && !view.getContactNumber().trim().equalsIgnoreCase("")){
					obj.setMobileNo(new BigDecimal(view.getContactNumber().trim()));
				}
				obj.setCivilId(view.getIdentityInt());
				Date sysdate = view.getIdentityExpiryDate();
				if(sysdate != null){
					obj.setIdExpiryDate(new SimpleDateFormat("dd/MM/yyy").format(sysdate));
				}

				//obj.setLocation(sessionStateManage.getLocation());
				obj.setLocation(view.getCountryBranchName());

				if(view.getDocumentFinancialYear()!=null && view.getCollectionDocumentNo()!=null){
					obj.setReceiptNo(view.getDocumentFinancialYear()+" / "+view.getCollectionDocumentNo());
				}else if(view.getCollectionDocumentNo()!=null){
					obj.setReceiptNo(view.getCollectionDocumentNo().toString());
				}

				if(view.getDocumentFinancialYear()!=null && view.getDocumentNo()!=null){
					obj.setTransactionNo(view.getDocumentFinancialYear()+" / "+view.getDocumentNo());
				}else if(view.getDocumentNo()!=null){
					obj.setTransactionNo(view.getDocumentNo().toString());
				}

				//obj.setDate(currentDate);
				Date docDate = view.getDocumentDate();
				if(docDate != null){
					obj.setDate(new SimpleDateFormat("dd/MM/yyy").format(docDate));
				}

				obj.setBeneficiaryName(view.getBeneficiaryName());
				obj.setBenefeciaryBankName(view.getBeneficiaryBank());
				obj.setBenefeciaryBranchName(view.getBenefeciaryBranch());
				obj.setBenefeciaryAccountNumber(view.getBenefeciaryAccountNo());
				obj.setNoOfTransaction(new BigDecimal(noOfTransactions));
				obj.setPhoneNumber(view.getPhoneNumber());
				//obj.setUserName(sessionStateManage.getUserName());
				obj.setUserName(view.getCreatedBy());
				obj.setPinNo(view.getPinNo() );

				HashMap<String, String> loyaltiPoints  =iPersonalRemittanceService.getloyalityPointsFromProcedure(view.getCustomerReference(), view.getDocumentDate());

				String prLtyStr1 =loyaltiPoints.get("P_LTY_STR1");
				String prLtyStr2 =loyaltiPoints.get("P_LTY_STR2");
				String prInsStr1 =loyaltiPoints.get("P_INS_STR1");
				String prInsStr2 =loyaltiPoints.get("P_INS_STR2");
				String prInsStrAr1 =loyaltiPoints.get("P_INS_STR_AR1");
				String prInsStrAr2 =loyaltiPoints.get("P_INS_STR_AR2");

				if(!prLtyStr1.trim().equals("") && !prLtyStr2.trim().equals("")){
					obj.setLoyalityPointExpiring(prLtyStr1+"  \n"+prLtyStr2);
				}else if(!prLtyStr1.trim().equals("")){
					obj.setLoyalityPointExpiring(prLtyStr1);
				}else if(!prLtyStr2.trim().equals("")){
					obj.setLoyalityPointExpiring(prLtyStr2);
				}
				
				
				List<CutomerDetailsView> customerListD = iPersonalRemittanceService.getCustomerDetails(view.getCustomerId());

				if (customerListD != null && customerListD.size() > 0) {
					CutomerDetailsView cust = customerListD.get(0);
					if(cust.getIdType() != null && cust.getIdType().compareTo(new BigDecimal(Constants.CORPORATE_ID_TYPE))==0){
						obj.setLoyalityPointExpiring("");
					}
				}
				
				

				if(!prInsStr1.trim().equals("") && !prInsStrAr1.trim().equals("")){
					obj.setInsurence1(prInsStr1+"  \n"+prInsStrAr1);
				}else if(!prInsStr1.trim().equals("")){
					obj.setInsurence1(prInsStr1);
				}else if(!prInsStrAr1.trim().equals("")){
					obj.setInsurence1(prInsStrAr1);
				}

				if(!prInsStr2.trim().equals("") && !prInsStrAr2.trim().equals("")){
					obj.setInsurence2(prInsStr2+"  \n"+prInsStrAr2);
				}else if(!prInsStr2.trim().equals("")){
					obj.setInsurence2(prInsStr2);
				}else if(!prInsStrAr2.trim().equals("")){
					obj.setInsurence2(prInsStrAr2);
				}

				if (view.getBeneCityName() != null && view.getBeneDistrictName() != null && view.getBeneStateName() != null) {
					obj.setAddress(view.getBeneCityName() + "," + view.getBeneDistrictName() + "," + view.getBeneStateName());
				} else if (view.getBeneCityName() == null && view.getBeneDistrictName() != null && view.getBeneStateName() != null) {
					obj.setAddress(view.getBeneDistrictName() + "," + view.getBeneStateName());
				} else if (view.getBeneCityName() == null && view.getBeneDistrictName() == null && view.getBeneStateName() != null) {
					obj.setAddress(view.getBeneStateName());
				} else if (view.getBeneCityName() != null && view.getBeneDistrictName() == null && view.getBeneStateName() != null) {
					obj.setAddress(view.getBeneCityName() + ", " + view.getBeneStateName());
				} else if (view.getBeneCityName() != null && view.getBeneDistrictName() == null && view.getBeneStateName() == null) {
					obj.setAddress(view.getBeneCityName());
				} else if (view.getBeneCityName() == null && view.getBeneDistrictName() != null && view.getBeneStateName() == null) {
					obj.setAddress(view.getBeneDistrictName());
				} else if (view.getBeneCityName() != null && view.getBeneDistrictName() != null && view.getBeneStateName() == null) {
					obj.setAddress(view.getBeneCityName() + ", " + view.getBeneDistrictName());
				}

				if (view.getRemittanceDescription() != null && view.getDeliveryDescription() != null) {
					obj.setPaymentChannel(view.getRemittanceDescription()+ " - " + view.getDeliveryDescription());
				} else if (view.getRemittanceDescription() == null && view.getDeliveryDescription() != null) {
					obj.setPaymentChannel(view.getDeliveryDescription());
				} else if (view.getRemittanceDescription() != null && view.getDeliveryDescription() == null) {
					obj.setPaymentChannel(view.getRemittanceDescription());
				}

				String currencyAndAmount=null;
				BigDecimal foreignTransationAmount=GetRound.roundBigDecimal((view.getForeignTransactionAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getForeignCurrencyId()));
				if(view.getCurrencyQuoteName()!=null && foreignTransationAmount!=null){
					currencyAndAmount = view.getCurrencyQuoteName()+"     ******"+foreignTransationAmount;
				}
				obj.setCurrencyAndAmount(currencyAndAmount);
				List<PurposeOfRemittanceView>  purposeOfRemittanceList =   iPersonalRemittanceService.getPurposeOfRemittance(view.getDocumentNo(),view.getDocumentFinancialYear());

				List<PurposeOfRemittanceReportBean> purposeOfRemitTrList1=new ArrayList<PurposeOfRemittanceReportBean>( );
				for(PurposeOfRemittanceView purposeObj :purposeOfRemittanceList){
					PurposeOfRemittanceReportBean beanObj=new PurposeOfRemittanceReportBean();
					beanObj.setPurposeOfTrField(purposeObj.getFlexfieldName());
					beanObj.setPurposeOfTrfieldArabic(null);
					beanObj.setPurposeOfTrValue(purposeObj.getFlexiFieldValue() );
					purposeOfRemitTrList1.add(beanObj);
				}

				if(purposeOfRemitTrList1.size()>0){
					obj.setPurposeOfRemitTrList(purposeOfRemitTrList1);
				}

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

				obj.setFutherInstructions(view.getInstructions());
				obj.setSourceOfIncome(view.getSourceOfIncomeDesc());
				obj.setIntermediataryBank(view.getBenefeciaryInterBank1());


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

				//addedd new column
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


				//obj.setSignature(view.getCustomerSignature()); 
				// Rabil
				// Added by Rabil
				try {
					if (view.getCustomerSignatureClob() != null) {
						obj.setSignature(view.getCustomerSignatureClob().getSubString(1,(int) view.getCustomerSignatureClob().length()));
					}
				} catch (SQLException e) {
					LOGGER.info( "Exception Occured While Report2 "+e.getMessage());
					setErrorMessage("Exception Occured While Report "+e.getMessage());
				}

				if(employeeList!=null && employeeList.size()>0){
					try {
						Employee employee = employeeList.get(0);
						if (employee.getSignatureSpecimenClob() != null) {
							obj.setCashierSignature(employee.getSignatureSpecimenClob().getSubString(1,(int) employee.getSignatureSpecimenClob().length()));
						}
					} catch (SQLException e) {
						LOGGER.info( "Exception Occured While Report3 "+e.getMessage());
						setErrorMessage("Exception Occured While Report "+e.getMessage() );
					}
				}
				//For Company information ADDED BY VISWA --START
				List<CompanyMaster> companyMaster =	iPersonalRemittanceService.getCompanyMaster(session.getCompanyId());
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
						arabicCompanyInfo=arabicCompanyInfo.append(Constants.CR +companyMaster.get(0).getRegistrationNumber()+",");
					}						
					if(companyMaster.get(0).getCapitalAmount()!=null&&companyMaster.get(0).getCapitalAmount().length()>0){
						arabicCompanyInfo=arabicCompanyInfo.append(Constants.Share_Capital+companyMaster.get(0).getCapitalAmount());
					}
					obj.setArabicCompanyInfo(arabicCompanyInfo.toString());
				}
				//For Company information ADDED BY VISWA --END

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

				remittanceApplList.add(obj);
			}

			// for foreign currency Sale report
			for (RemittanceApplicationView view : fcsaleList) {

				RemittanceReportBean obj = new RemittanceReportBean();

				//For Company information ADDED BY VISWA --START
				List<CompanyMaster> companyMaster =	iPersonalRemittanceService.getCompanyMaster(session.getCompanyId());
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
						arabicCompanyInfo=arabicCompanyInfo.append(Constants.CR +companyMaster.get(0).getRegistrationNumber()+",");
					}					
					if(companyMaster.get(0).getCapitalAmount()!=null&&companyMaster.get(0).getCapitalAmount().length()>0){
						arabicCompanyInfo=arabicCompanyInfo.append(Constants.Share_Capital +companyMaster.get(0).getCapitalAmount());
					}
					obj.setArabicCompanyInfo(arabicCompanyInfo.toString());
				}

				//For Company information ADDED BY VISWA --END
				if (view.getCustomerReference() != null && view.getFirstName() != null && view.getMiddleName() != null) {
					obj.setFirstName(view.getCustomerReference().toString() + "/" + view.getFirstName() + " " + view.getMiddleName());
				} else if (view.getCustomerReference() == null && view.getFirstName() != null && view.getMiddleName() != null) {
					obj.setFirstName(view.getFirstName() + " " + view.getMiddleName());
				} else if (view.getCustomerReference() == null && view.getFirstName() == null && view.getMiddleName() != null) {
					obj.setFirstName(view.getMiddleName());
				} else if (view.getCustomerReference() != null && view.getFirstName() == null && view.getMiddleName() != null) {
					obj.setFirstName(view.getCustomerReference().toString() + " / " + view.getMiddleName());
				} else if (view.getCustomerReference() == null && view.getFirstName() != null && view.getMiddleName() == null) {
					obj.setFirstName(view.getFirstName());
				} else if (view.getCustomerReference() != null && view.getFirstName() == null && view.getMiddleName() == null) {
					obj.setFirstName(view.getCustomerReference().toString());
				} else if (view.getCustomerReference() != null && view.getFirstName() != null && view.getMiddleName() == null) {
					obj.setFirstName(view.getCustomerReference().toString() + " " + view.getFirstName());
				}

				if(view.getContactNumber()!=null){
					obj.setMobileNo(new BigDecimal(view.getContactNumber().trim()));
				}
				obj.setCivilId(view.getIdentityInt());
				Date sysdate = view.getIdentityExpiryDate();
				if(sysdate != null){
					obj.setIdExpiryDate(new SimpleDateFormat("dd/MM/yyy").format(sysdate));
				}

				HashMap<String, String> loyaltiPoints  =iPersonalRemittanceService.getloyalityPointsFromProcedure(view.getCustomerReference(), view.getDocumentDate());

				String prLtyStr1 =loyaltiPoints.get("P_LTY_STR1");
				String prLtyStr2 =loyaltiPoints.get("P_LTY_STR2");
				String prInsStr1 =loyaltiPoints.get("P_INS_STR1");
				String prInsStr2 =loyaltiPoints.get("P_INS_STR2");
				String prInsStrAr1 =loyaltiPoints.get("P_INS_STR_AR1");
				String prInsStrAr2 =loyaltiPoints.get("P_INS_STR_AR2");

				if(!prLtyStr1.trim().equals("") && !prLtyStr2.trim().equals("")){
					obj.setLoyalityPointExpiring(prLtyStr1+"  \n"+prLtyStr2);
				}else if(!prLtyStr1.trim().equals("")){
					obj.setLoyalityPointExpiring(prLtyStr1);
				}else if(!prLtyStr2.trim().equals("")){
					obj.setLoyalityPointExpiring(prLtyStr2);
				}
				
				List<CutomerDetailsView> customerListD = iPersonalRemittanceService.getCustomerDetails(view.getCustomerId());
				if (customerListD != null && customerListD.size() > 0) {
					CutomerDetailsView cust = customerListD.get(0);
					if(cust.getIdType() != null && cust.getIdType().compareTo(new BigDecimal(Constants.CORPORATE_ID_TYPE))==0){
						obj.setLoyalityPointExpiring("");
					}
				}
				
				
				
				

				String insurence1 ="";

				if(!prInsStr1.trim().equals("") && !prInsStrAr1.trim().equals("")){
					insurence1 = prInsStr1+"  \n"+prInsStrAr1;
				}else if(!prInsStr1.trim().equals("")){
					insurence1 = prInsStr1;
				}else if(!prInsStrAr1.trim().equals("")){
					insurence1 = prInsStrAr1;
				}

				String insurence2 ="";

				if(!prInsStr2.trim().equals("") && !prInsStrAr2.trim().equals("")){
					insurence2 = prInsStr2+"  \n"+prInsStrAr2;
				}else if(!prInsStr2.trim().equals("")){
					insurence2 = prInsStr2;
				}else if(!prInsStrAr2.trim().equals("")){
					insurence2 = prInsStrAr2;
				}

				if(!insurence1.trim().equals("") && !insurence2.trim().equals("")){
					obj.setInsurence1(insurence1+" \n"+insurence2);
				}else if(!insurence1.trim().equals("")){
					obj.setInsurence1(insurence1);
				}else if(!insurence2.trim().equals("")){
					obj.setInsurence1(insurence2);
				}


				obj.setLocation(view.getCountryBranchName());
				obj.setPhoneNumber(view.getPhoneNumber());
				obj.setDate(currentDate);
				obj.setUserName(view.getCreatedBy());

				if(view.getDocumentFinancialYear()!=null && view.getCollectionDocumentNo()!=null){
					obj.setReceiptNo(view.getDocumentFinancialYear()+" / "+view.getCollectionDocumentNo());
				}else if(view.getCollectionDocumentNo()!=null){
					obj.setReceiptNo(view.getCollectionDocumentNo().toString());
				}
				String saleCurrency = specialCustomerDealRequestService.getCurrencyForUpdate(view.getForeignCurrencyId());


				obj.setCurrencyQuoteName(saleCurrency);

				String saleCurrencyCode = generalService.getCurrencyQuote(view.getForeignCurrencyId());

				if(view.getDocumentFinancialYear()!=null && view.getDocumentNo()!=null){
					obj.setTransactionNo(view.getDocumentFinancialYear()+" / "+view.getDocumentNo());
				}else if(view.getDocumentNo()!=null){
					obj.setTransactionNo(view.getDocumentNo().toString());
				}

				if (view.getForeignTransactionAmount() != null && saleCurrencyCode != null) {
					BigDecimal foreignTransationAmount=GetRound.roundBigDecimal((view.getForeignTransactionAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getForeignCurrencyId()));	
					obj.setSaleAmount( saleCurrencyCode+"     ******"+foreignTransationAmount.toString());
				} 

				if( view.getLocalTransactionCurrencyId()!=null && currencyQuoteName!=null){
					BigDecimal transationAmount=GetRound.roundBigDecimal((view.getLocalTransactionAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setPurchageAmount(currencyQuoteName+"     ******"+transationAmount.toString());
				}

				if(saleCurrencyCode!=null && currencyQuoteName!=null && view.getExchangeRateApplied()!=null){
					obj.setExchangeRate(saleCurrencyCode+" / "+currencyQuoteName+"     "+view.getExchangeRateApplied().toString());
				}

				if(view.getLocalTransactionAmount()!=null && view.getLocalTransactionCurrencyId()!=null && currencyQuoteName!=null){
					BigDecimal transationAmount=GetRound.roundBigDecimal((view.getLocalTransactionAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setLocalTransactionAmount(currencyQuoteName+"     ******"+transationAmount.toString());
				}

				if(view.getLocalCommissionAmount()!=null && view.getLocalTransactionCurrencyId()!=null && currencyQuoteName!=null){
					BigDecimal localCommitionAmount=GetRound.roundBigDecimal((view.getLocalCommissionAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setCommision(currencyQuoteName+"     ******"+localCommitionAmount.toString());
				}

				List<PurposeOfRemittanceView>  purposeOfRemittanceList =   iPersonalRemittanceService.getPurposeOfRemittance(view.getDocumentNo(),view.getDocumentFinancialYear());

				obj.setPerposeOfRemittance(view.getPurposeOfTransaction());
				obj.setSourceOfIncome(view.getSourceOfIncomeDesc());

				List<CollectionDetailView> collectionDetailList1= iPersonalRemittanceService.getCollectionDetailForRemittanceReceipt(view.getCompanyId(),view.getCollectionDocumentNo(),view.getCollectionDocFinanceYear(),view.getCollectionDocCode());
				if(collectionDetailList1.size()>0){
					CollectionDetailView collectionDetailView = collectionDetailList1.get(0);
					if(collectionDetailView.getNetAmount()!=null && currencyQuoteName!=null && view.getLocalTransactionCurrencyId()!=null){
						BigDecimal collectNetAmount=GetRound.roundBigDecimal((collectionDetailView.getNetAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
						obj.setNetAmount(currencyQuoteName+"     ******"+collectNetAmount);
					}
					if(collectionDetailView.getPaidAmount()!=null && currencyQuoteName!=null && view.getLocalTransactionCurrencyId()!=null){
						BigDecimal collectPaidAmount=GetRound.roundBigDecimal((collectionDetailView.getPaidAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
						obj.setPaidAmount(currencyQuoteName+"     ******"+collectPaidAmount);
					}
					if(collectionDetailView.getRefundedAmount()!=null && currencyQuoteName!=null && view.getLocalTransactionCurrencyId()!=null){
						BigDecimal collectRefundAmount=GetRound.roundBigDecimal((collectionDetailView.getRefundedAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
						obj.setRefundedAmount(currencyQuoteName+"     ******"+collectRefundAmount);
					}
					obj.setCollectionDetailList(calculateCollectionMode(view));
				}

				obj.setSubReport(subReportPath);
				//obj.setUserName(sessionStateManage.getUserName());
				obj.setUserName(view.getCreatedBy());

				//		obj.setSignature(view.getCustomerSignature());

				//addedd new column
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
					LOGGER.info( "Exception Occured While Report4 "+e.getMessage());
					setErrorMessage("Exception Occured While Report "+e.getMessage() );
				}

				if(employeeList!=null && employeeList.size()>0){
					try {
						Employee employee = employeeList.get(0);
						if (employee.getSignatureSpecimenClob() != null) {
							obj.setCashierSignature(employee.getSignatureSpecimenClob().getSubString(1,(int) employee.getSignatureSpecimenClob().length()));
						}
					} catch (SQLException e) {
						LOGGER.info( "Exception Occured While Report 5"+e.getMessage());
						setErrorMessage("Exception Occured While Report "+e.getMessage() );
					}
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

				fcsaleAppList.add(obj);
			}

			//for Main Remittance Receipt report (Remittance Receipt and Fc Sale Application)
			RemittanceReceiptSubreport remittanceObj = new RemittanceReceiptSubreport();

			remittanceObj.setWaterMarkLogoPath(waterMark);
			remittanceObj.setWaterMarkCheck(true);
			remittanceObj.setFcsaleAppList(fcsaleAppList);
			remittanceObj.setRemittanceApplList(remittanceApplList);
			remittanceObj.setSubReport(subReportPath);

			if (fcsaleAppList!=null && fcsaleAppList.size()>0) {
				remittanceObj.setFcsaleApplicationCheck(true);
			}

			if(remittanceApplList!=null && remittanceApplList.size()>0){
				remittanceObj.setRemittanceReceiptCheck(true);
			}

			remittanceReceiptSubreportList.add(remittanceObj);
			return 1;
		} else {
			RequestContext.getCurrentInstance().execute("noDataForReport.show();");
			return 0;
		}

	}

	/*private int fetchRemittanceReceiptReportData(BigDecimal documentNumber, BigDecimal documentYear) {
		collectionViewList.clear();
		remittanceReceiptSubreportList = new ArrayList<RemittanceReceiptSubreport>();
		List<RemittanceApplicationView> remittanceApplicationList = new ArrayList<RemittanceApplicationView>();
		List<RemittanceApplicationView> fcsaleList = new ArrayList<RemittanceApplicationView>();
		List<RemittanceReportBean> remittanceApplList = new ArrayList<RemittanceReportBean>();
		List<RemittanceReportBean> fcsaleAppList = new ArrayList<RemittanceReportBean>();
		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath = ctx.getRealPath("//");
		String subReportPath = realPath + Constants.SUB_REPORT_PATH;
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String currentDate = dateFormat.format(new Date());
		int noOfTransactions = 0;


		 String currencyQuoteName = generalService.getCurrencyQuote(new BigDecimal(session.getCurrencyId()));
		List<RemittanceApplicationView> remittanceViewlist = new ArrayList<RemittanceApplicationView>();
		if (getTransactionNo() != null) {
			remittanceViewlist = getReprintService().getRemittancefromCollectionNumber(getTransactionNo(), REMITTANCE_DOCUMENT_CODE, documentYear, session.getCountryId());
		} else {
			remittanceViewlist = getReprintService().getRecordsRemittanceReceipt(documentNumber, documentYear, session.getCountryId());
		}



		List<Employee> employeeList = iPersonalRemittanceService.getEmployeeDetails(session.getUserName());


		List<RemittanceApplicationView> remittanceViewlist = iPersonalRemittanceService.getRecordsRemittanceReceipt(documentNumber);
		if (remittanceViewlist.size() > 0) {

			for (RemittanceApplicationView remittanceAppview : remittanceViewlist) {

				if (remittanceAppview.getApplicationTypeDesc().equalsIgnoreCase("REMITTANCE")) {
					remittanceApplicationList.add(remittanceAppview);
					noOfTransactions= noOfTransactions+1;
				} else if (remittanceAppview.getApplicationTypeDesc().equalsIgnoreCase("FOREIGN CURRENCY SALE")) {
					fcsaleList.add(remittanceAppview);
					noOfTransactions= noOfTransactions+1;
				}
			}

			//remittance List
			for (RemittanceApplicationView view : remittanceApplicationList) {

				RemittanceReportBean obj = new RemittanceReportBean();

				if (view.getCustomerReference() != null && view.getFirstName() != null && view.getMiddleName() != null) {
					obj.setFirstName(view.getCustomerReference().toString() + " / " + view.getFirstName() + " " + view.getMiddleName());
				} else if (view.getCustomerReference() == null && view.getFirstName() != null && view.getMiddleName() != null) {
					obj.setFirstName(view.getFirstName() + " "+ view.getMiddleName());
				} else if (view.getCustomerReference() == null && view.getFirstName() == null && view.getMiddleName() != null) {
					obj.setFirstName(view.getMiddleName());
				} else if (view.getCustomerReference() != null && view.getFirstName() == null && view.getMiddleName() != null) {
					obj.setFirstName(view.getCustomerReference().toString() + " / " + view.getMiddleName());
				} else if (view.getCustomerReference() == null && view.getFirstName() != null && view.getMiddleName() == null) {
					obj.setFirstName(view.getFirstName());
				} else if (view.getCustomerReference() != null && view.getFirstName() == null && view.getMiddleName() == null) {
					obj.setFirstName(view.getCustomerReference().toString());
				} else if (view.getCustomerReference() != null && view.getFirstName() != null && view.getMiddleName() == null) {
					obj.setFirstName(view.getCustomerReference().toString() + " " + view.getFirstName());
				}
				if(view.getContactNumber() != null){
					obj.setMobileNo(new BigDecimal(view.getContactNumber().trim()));
				}

				obj.setCivilId(view.getIdentityInt());
				Date sysdate = view.getIdentityExpiryDate();
				obj.setIdExpiryDate(new SimpleDateFormat("dd/MM/yyy").format(sysdate));
				obj.setLocation(view.getBenefeciaryBranch());

				if(view.getDocumentFinancialYear()!=null && view.getCollectionDocumentNo()!=null){
				obj.setReceiptNo(view.getDocumentFinancialYear()+" / "+view.getCollectionDocumentNo());
				}else if(view.getCollectionDocumentNo()!=null){
					obj.setReceiptNo(view.getCollectionDocumentNo().toString());
				}


				if(view.getDocumentFinancialYear()!=null && view.getDocumentNo()!=null){
					obj.setTransactionNo(view.getDocumentFinancialYear()+" / "+view.getDocumentNo());
					}else if(view.getDocumentNo()!=null){
						obj.setTransactionNo(view.getDocumentNo().toString());
				}

				obj.setDate(currentDate);
				obj.setBeneficiaryName(view.getBeneficiaryName());
				obj.setBenefeciaryBankName(view.getBeneficiaryBank());
				obj.setBenefeciaryBranchName(view.getBenefeciaryBranch());
				obj.setBenefeciaryAccountNumber(view.getBenefeciaryAccountNo());
				obj.setNoOfTransaction(new BigDecimal(noOfTransactions));
				obj.setPhoneNumber(view.getPhoneNumber());
				obj.setUserName(session.getUserName());


				obj.setLoyalityPointExpiring(null);
				obj.setPinNo(view.getPinNo());
				obj.setInsurence1(" ");
				obj.setInsurence2(" ");

				if (view.getBeneCityName() != null && view.getBeneDistrictName() != null && view.getBeneStateName() != null) {
					obj.setAddress(view.getBeneCityName() + "," + view.getBeneDistrictName() + "," + view.getBeneStateName());
				} else if (view.getBeneCityName() == null && view.getBeneDistrictName() != null && view.getBeneStateName() != null) {
					obj.setAddress(view.getBeneDistrictName() + "," + view.getBeneStateName());
				} else if (view.getBeneCityName() == null && view.getBeneDistrictName() == null && view.getBeneStateName() != null) {
					obj.setAddress(view.getBeneStateName());
				} else if (view.getBeneCityName() != null && view.getBeneDistrictName() == null && view.getBeneStateName() != null) {
					obj.setAddress(view.getBeneCityName() + ", " + view.getBeneStateName());
				} else if (view.getBeneCityName() != null && view.getBeneDistrictName() == null && view.getBeneStateName() == null) {
					obj.setAddress(view.getBeneCityName());
				} else if (view.getBeneCityName() == null && view.getBeneDistrictName() != null && view.getBeneStateName() == null) {
					obj.setAddress(view.getBeneDistrictName());
				} else if (view.getBeneCityName() != null && view.getBeneDistrictName() != null && view.getBeneStateName() == null) {
					obj.setAddress(view.getBeneCityName() + ", " + view.getBeneDistrictName());
				}



				if (view.getRemittanceDescription() != null && view.getDeliveryDescription() != null) {
					obj.setPaymentChannel(view.getRemittanceDescription()+ " - " + view.getDeliveryDescription());
				} else if (view.getRemittanceDescription() == null && view.getDeliveryDescription() != null) {
					obj.setPaymentChannel(view.getDeliveryDescription());
				} else if (view.getRemittanceDescription() != null && view.getDeliveryDescription() == null) {
					obj.setPaymentChannel(view.getRemittanceDescription());
				}

				String currencyAndAmount=null;
				BigDecimal foreignTransationAmount=round((view.getForeignTransactionAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getForeignCurrencyId()));
				if(view.getCurrencyQuoteName()!=null && foreignTransationAmount!=null){
				 currencyAndAmount = view.getCurrencyQuoteName()+"     ******"+foreignTransationAmount;
				}
				obj.setCurrencyAndAmount(currencyAndAmount);
				List<PurposeOfRemittanceView>  purposeOfRemittanceList =   iPersonalRemittanceService.getPurposeOfRemittance(view.getDocumentNo(),view.getDocumentFinancialYear());

				if(purposeOfRemittanceList!=null && purposeOfRemittanceList.size()>0){
					obj.setPerposeOfRemittance(purposeOfRemittanceList.get(0).getAmiecDescription());
				}
				String purposeOfRemittance = "";
				for(PurposeOfRemittanceView purposeObj :purposeOfRemittanceList){
					if(purposeOfRemittance.equals("")){
						purposeOfRemittance  = purposeObj.getAmiecDescription();
					}else{
						purposeOfRemittance  = purposeOfRemittance+", "+purposeObj.getAmiecDescription();
					}
				}

				if(purposeOfRemittance != null && !purposeOfRemittance.equalsIgnoreCase("")){
					obj.setPerposeOfRemittance(purposeOfRemittance);
				}

				List<PurposeOfRemittanceReportBean> purposeOfRemitTrList1=new ArrayList<PurposeOfRemittanceReportBean>( );
				for(PurposeOfRemittanceView purposeObj :purposeOfRemittanceList){
					PurposeOfRemittanceReportBean beanObj=new PurposeOfRemittanceReportBean();
					beanObj.setPurposeOfTrField(purposeObj.getFlexfieldName());
					beanObj.setPurposeOfTrfieldArabic(null);
					beanObj.setPurposeOfTrValue(purposeObj.getFlexiFieldValue() );
					purposeOfRemitTrList1.add(beanObj);
				}

				if(purposeOfRemitTrList1.size()>0){
					obj.setPurposeOfRemitTrList(purposeOfRemitTrList1);
				}
				obj.setExchangeRate(view.getCurrencyQuoteName()+" / "+currencyQuoteName+"     "+view.getExchangeRateApplied().toString());

				BigDecimal transationAmount=round((view.getLocalTransactionAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
				obj.setLocalTransactionAmount(currencyQuoteName+"     ******"+transationAmount.toString());

				BigDecimal localCommitionAmount=round((view.getLocalCommissionAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
				obj.setCommision(currencyQuoteName+"     ******"+localCommitionAmount.toString());

				BigDecimal localChargeAmount=round((view.getLocalChargeAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
				obj.setOtherCharges(currencyQuoteName+"     ******"+localChargeAmount.toString());

				BigDecimal netAmount=round((view.getLocalNetTransactionAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
				obj.setTotalAmount(currencyQuoteName+"     ******"+netAmount.toString());

				obj.setFutherInstructions(view.getInstructions());
				obj.setSourceOfIncome(view.getSourceOfIncomeDesc());
				obj.setIntermediataryBank(view.getBenefeciaryInterBank1());


				List<CollectionDetailView> collectionDetailList1= iPersonalRemittanceService.getCollectionDetailForRemittanceReceipt(view.getCompanyId(),view.getCollectionDocumentNo(),view.getCollectionDocFinanceYear(),view.getCollectionDocCode());

				CollectionDetailView collectionDetailView = collectionDetailList1.get(0);

				BigDecimal collectNetAmount=round((collectionDetailView.getNetAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
				obj.setNetAmount(currencyQuoteName+"     ******"+collectNetAmount);

				BigDecimal collectPaidAmount=round((collectionDetailView.getPaidAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
				obj.setPaidAmount(currencyQuoteName+"     ******"+collectPaidAmount);

				BigDecimal collectRefundAmount=round((collectionDetailView.getRefundedAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
				obj.setRefundedAmount(currencyQuoteName+"     ******"+collectRefundAmount);

				obj.setSubReport(subReportPath);
				obj.setCollectionDetailList(calculateCollectionMode(view));

				//obj.setSignature(view.getCustomerSignature()); 
				// Rabil

				// Added by Rabil
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



				remittanceApplList.add(obj);
			}

			// for foreign currency Sale report
			for (RemittanceApplicationView view : fcsaleList) {

				RemittanceReportBean obj = new RemittanceReportBean();

				if (view.getCustomerReference() != null && view.getFirstName() != null && view.getMiddleName() != null) {
					obj.setFirstName(view.getCustomerReference().toString() + "/" + view.getFirstName() + " " + view.getMiddleName());
				} else if (view.getCustomerReference() == null && view.getFirstName() != null && view.getMiddleName() != null) {
					obj.setFirstName(view.getFirstName() + " " + view.getMiddleName());
				} else if (view.getCustomerReference() == null && view.getFirstName() == null && view.getMiddleName() != null) {
					obj.setFirstName(view.getMiddleName());
				} else if (view.getCustomerReference() != null && view.getFirstName() == null && view.getMiddleName() != null) {
					obj.setFirstName(view.getCustomerReference().toString() + " / " + view.getMiddleName());
				} else if (view.getCustomerReference() == null && view.getFirstName() != null && view.getMiddleName() == null) {
					obj.setFirstName(view.getFirstName());
				} else if (view.getCustomerReference() != null && view.getFirstName() == null && view.getMiddleName() == null) {
					obj.setFirstName(view.getCustomerReference().toString());
				} else if (view.getCustomerReference() != null && view.getFirstName() != null && view.getMiddleName() == null) {
					obj.setFirstName(view.getCustomerReference().toString() + " " + view.getFirstName());
				}

				if(view.getContactNumber() != null){
					obj.setMobileNo(new BigDecimal(view.getContactNumber().trim()));
				}

				obj.setCivilId(view.getIdentityInt());
				Date sysdate = view.getIdentityExpiryDate();
				obj.setIdExpiryDate(new SimpleDateFormat("dd/MM/yyyy").format(sysdate));
				obj.setInsurence1(" ");
				obj.setInsurence2(" ");
				obj.setLocation(view.getCountryBranchName());
				obj.setPhoneNumber(view.getPhoneNumber());
				obj.setCurrencyQuoteName(view.getCurrencyQuoteName());
				obj.setDate(currentDate);
				obj.setUserName(session.getUserName());

				if(view.getDocumentFinancialYear()!=null && view.getCollectionDocumentNo()!=null){
					obj.setReceiptNo(view.getDocumentFinancialYear()+" / "+view.getCollectionDocumentNo());
					}else if(view.getCollectionDocumentNo()!=null){
						obj.setReceiptNo(view.getCollectionDocumentNo().toString());
					}

					if(view.getDocumentFinancialYear()!=null && view.getDocumentNo()!=null){
						obj.setTransactionNo(view.getDocumentFinancialYear()+" / "+view.getDocumentNo());
						}else if(view.getDocumentNo()!=null){
							obj.setTransactionNo(view.getDocumentNo().toString());
					}
				obj.setBeneficiaryName(view.getBeneficiaryName());
				obj.setBenefeciaryBankName(view.getBeneficiaryBank());
				obj.setBenefeciaryBranchName(view.getBenefeciaryBranch());
				obj.setBenefeciaryAccountNumber(view.getBenefeciaryAccountNo());

				if (view.getForeignTransactionAmount() != null && view.getCurrencyQuoteName() != null) {
					obj.setSaleAmount(view.getForeignTransactionAmount().toString() + " " + view.getCurrencyQuoteName());
				} else if (view.getForeignTransactionAmount() == null) {
					obj.setSaleAmount(null);
				} else if (view.getForeignTransactionAmount() != null && view.getCurrencyQuoteName() == null) {
					obj.setSaleAmount(view.getForeignTransactionAmount().toString());
				}

				if (view.getBeneCityName() != null && view.getBeneDistrictName() != null && view.getBeneStateName() != null) {
					obj.setAddress(view.getBeneCityName() + "," + view.getBeneDistrictName() + "," + view.getBeneStateName());
				} else if (view.getBeneCityName() == null && view.getBeneDistrictName() != null && view.getBeneStateName() != null) {
					obj.setAddress(view.getBeneDistrictName() + "," + view.getBeneStateName());
				} else if (view.getBeneCityName() == null && view.getBeneDistrictName() == null && view.getBeneStateName() != null) {
					obj.setAddress(view.getBeneStateName());
				} else if (view.getBeneCityName() != null && view.getBeneDistrictName() == null && view.getBeneStateName() != null) {
					obj.setAddress(view.getBeneCityName() + ", " + view.getBeneStateName());
				} else if (view.getBeneCityName() != null && view.getBeneDistrictName() == null && view.getBeneStateName() == null) {
					obj.setAddress(view.getBeneCityName());
				} else if (view.getBeneCityName() == null && view.getBeneDistrictName() != null && view.getBeneStateName() == null) {
					obj.setAddress(view.getBeneDistrictName());
				} else if (view.getBeneCityName() != null && view.getBeneDistrictName() != null && view.getBeneStateName() == null) {
					obj.setAddress(view.getBeneCityName() + ", "+ view.getBeneDistrictName());
				}



				if (view.getRemittanceDescription() != null && view.getDeliveryDescription() != null) {
					obj.setPaymentChannel(view.getRemittanceDescription()+ " - " + view.getDeliveryDescription());
				} else if (view.getRemittanceDescription() == null && view.getDeliveryDescription() != null) {
					obj.setPaymentChannel(view.getDeliveryDescription());
				} else if (view.getRemittanceDescription() != null && view.getDeliveryDescription() == null) {
					obj.setPaymentChannel(view.getRemittanceDescription());
				}

				String currencyAndAmount=null;
				if(view.getCurrencyQuoteName()!=null && view.getForeignTransactionAmount()!=null){
				 currencyAndAmount = view.getCurrencyQuoteName()+"     ******"+view.getForeignTransactionAmount();
				}
				obj.setCurrencyAndAmount(currencyAndAmount);
				List<PurposeOfRemittanceView>  purposeOfRemittanceList =   iPersonalRemittanceService.getPurposeOfRemittance(view.getDocumentNo(),view.getDocumentFinancialYear());

				if(purposeOfRemittanceList!=null && purposeOfRemittanceList.size()>0){
					obj.setPerposeOfRemittance(purposeOfRemittanceList.get(0).getAmiecDescription());
				}

				obj.setPerposeOfRemittance(view.getPurposeOfTransaction());
				obj.setExchangeRate(view.getCurrencyQuoteName()+" / "+currencyQuoteName+"     "+view.getExchangeRateApplied().toString());

				BigDecimal transationAmount=round((view.getLocalTransactionAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
				obj.setLocalTransactionAmount(currencyQuoteName+"     ******"+transationAmount.toString());
				 if(view.getLocalCommissionAmount()!=null){
				BigDecimal localCommitionAmount=round((view.getLocalCommissionAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
				obj.setCommision(currencyQuoteName+"     ******"+localCommitionAmount.toString());
				 }
				 if(view.getLocalChargeAmount()!=null){
				BigDecimal localChargeAmount=round((view.getLocalChargeAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
				obj.setOtherCharges(currencyQuoteName+"     ******"+localChargeAmount.toString());
				 }
				 if(view.getLocalNetTransactionAmount()!=null){
				BigDecimal netAmount=round((view.getLocalNetTransactionAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
				obj.setTotalAmount(currencyQuoteName+"     ******"+netAmount.toString());
				 }

				obj.setFutherInstructions(view.getInstructions());
				obj.setSourceOfIncome(view.getSourceOfIncomeDesc());
				obj.setIntermediataryBank(view.getBenefeciaryInterBank1());


				List<CollectionDetailView> collectionDetailList1= iPersonalRemittanceService.getCollectionDetailForRemittanceReceipt(view.getCompanyId(),view.getCollectionDocumentNo(),view.getCollectionDocFinanceYear(),view.getCollectionDocCode());

				CollectionDetailView collectionDetailView = collectionDetailList1.get(0);

				BigDecimal collectNetAmount=round((collectionDetailView.getNetAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
				obj.setNetAmount(currencyQuoteName+"     ******"+collectNetAmount);

				BigDecimal collectPaidAmount=round((collectionDetailView.getPaidAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
				obj.setPaidAmount(currencyQuoteName+"     ******"+collectPaidAmount);

				BigDecimal collectRefundAmount=round((collectionDetailView.getRefundedAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
				obj.setRefundedAmount(currencyQuoteName+"     ******"+collectRefundAmount);

				obj.setCollectionDetailList(calculateCollectionMode(view));
				obj.setSubReport(subReportPath);

			//	obj.setSignature(view.getCustomerSignature());

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



				fcsaleAppList.add(obj);
			}

			//for Main Remittance Receipt report (Remittance Receipt and Fc Sale Application)
			RemittanceReceiptSubreport remittanceObj = new RemittanceReceiptSubreport();

			remittanceObj.setFcsaleAppList(fcsaleAppList);
			remittanceObj.setRemittanceApplList(remittanceApplList);
			remittanceObj.setSubReport(subReportPath);

				if (fcsaleAppList!=null && fcsaleAppList.size()>0) {
					remittanceObj.setFcsaleApplicationCheck(true);
				} else {
					remittanceObj.setFcsaleApplicationCheck(false);
				}
				if(remittanceApplList!=null && remittanceApplList.size()>0){
					remittanceObj.setRemittanceReceiptCheck(true);
				}else{
					remittanceObj.setRemittanceReceiptCheck(false);
				}

			remittanceReceiptSubreportList.add(remittanceObj);
			return 1;
		} else {
			return 0;
		}
	}*/


	public List<RemittanceReportBean> calculateCollectionMode(RemittanceApplicationView viewCollectionObj){
		List<RemittanceReportBean> collectionDetailList = new ArrayList<RemittanceReportBean>();
		List<CollectionPaymentDetailsView> collectionPaymentDetailList= iPersonalRemittanceService.getCollectionPaymentDetailForRemittanceReceipt(viewCollectionObj.getCompanyId(),viewCollectionObj.getCollectionDocumentNo(),viewCollectionObj.getCollectionDocFinanceYear(),viewCollectionObj.getCollectionDocCode());

		for(CollectionPaymentDetailsView viewObj: collectionPaymentDetailList){
			RemittanceReportBean obj = new RemittanceReportBean();

			obj.setCollectionMode(viewObj.getCollectionModeDesc());
			BigDecimal collectAmount=round((viewObj.getCollectAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(viewCollectionObj.getLocalTransactionCurrencyId()));
			obj.setCollectAmount(collectAmount);

			collectionDetailList.add(obj);
		}
		return collectionDetailList;
	}

	public static BigDecimal round(BigDecimal value, int places) {
		if (places < 0) throw new IllegalArgumentException();

		BigDecimal bd = value;
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd;
	}

	public void clear() {
		setDocumentNo(null);
		//setTransactionNo(null);
		setDocYear(null);
	}

	public Boolean getBoodataNotFound() {
		return boodataNotFound;
	}

	public void setBoodataNotFound(Boolean boodataNotFound) {
		this.boodataNotFound = boodataNotFound;
	}
}
