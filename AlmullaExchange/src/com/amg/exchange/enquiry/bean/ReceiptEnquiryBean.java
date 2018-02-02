package com.amg.exchange.enquiry.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.context.FacesContext;
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

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CutomerDetailsView;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.enquiry.service.IReceiptEnquiryService;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.foreigncurrency.service.ForeignLocalCurrencyDenominationService;
import com.amg.exchange.foreigncurrency.service.IForeignCurrencyPurchaseService;
import com.amg.exchange.miscellaneous.model.ViewVwRemittanceTransaction;
import com.amg.exchange.registration.model.CustomerIdproofView;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.registration.service.ICustomerRegistrationBranchService;
import com.amg.exchange.remittance.bean.FCSaleEnquiryBean;
import com.amg.exchange.remittance.bean.PurposeOfRemittanceReportBean;
import com.amg.exchange.remittance.bean.RemittanceReceiptSubreport;
import com.amg.exchange.remittance.bean.RemittanceReportBean;
import com.amg.exchange.remittance.model.CollectionDetailView;
import com.amg.exchange.remittance.model.CollectionPaymentDetailsView;
import com.amg.exchange.remittance.model.PurposeOfRemittanceView;
import com.amg.exchange.remittance.model.RemittanceApplicationView;
import com.amg.exchange.remittance.service.IPersonalRemittanceService;
import com.amg.exchange.treasury.service.ISpecialCustomerDealRequestService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.GetRound;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("receiptEnquiryBean")
@Scope("session")
public class ReceiptEnquiryBean<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log=Logger.getLogger(ReceiptEnquiryBean.class);

	private BigDecimal documentFinancialYear;
	private BigDecimal documentNo;
	private BigDecimal customerRef;
	private String customerName;
	private String tranctionType;
	private String coresponingBank;
	private String beneficiaryBAnk;
	private BigDecimal fcAmount;
	private BigDecimal localAmount;
	private Date tranctionDate;
	private String status;
	private String errorMessage;
	private BigDecimal remitDocNo;
	private Boolean booRenderDataTable=false;
	private SessionStateManage sessionStateManage =new SessionStateManage();

	private List<UserFinancialYear> userFinancialYearList= new ArrayList<UserFinancialYear>();
	private List<ReceiptEnquiryDataTable> lstreceiptEnquiryDTList= new ArrayList<ReceiptEnquiryDataTable>();

	@Autowired
	IForeignCurrencyPurchaseService<T> iforeigncurrencyservice;
	
	@Autowired
	ForeignLocalCurrencyDenominationService<T> foreignLocalCurrencyDenominationService;

	@Autowired
	IReceiptEnquiryService receiptEnquiryService;

	@Autowired
	IPersonalRemittanceService iPersonalRemittanceService;

	@Autowired
	ISpecialCustomerDealRequestService<T> specialCustomerDealRequestService;
	
	@Autowired
	IGeneralService<T> generalService;

	@Autowired
	RemittanceInquiryBean remittanceEnquiryBean;

	@Autowired
	FCSaleEnquiryBean fcSaleEnquiryBean;
	
	@Autowired
	ICustomerRegistrationBranchService<T> icustomerRegistrationService;


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
	public String getTranctionType() {
		return tranctionType;
	}
	public void setTranctionType(String tranctionType) {
		this.tranctionType = tranctionType;
	}
	public String getCoresponingBank() {
		return coresponingBank;
	}
	public void setCoresponingBank(String coresponingBank) {
		this.coresponingBank = coresponingBank;
	}
	public String getBeneficiaryBAnk() {
		return beneficiaryBAnk;
	}
	public void setBeneficiaryBAnk(String beneficiaryBAnk) {
		this.beneficiaryBAnk = beneficiaryBAnk;
	}
	public BigDecimal getFcAmount() {
		return fcAmount;
	}
	public void setFcAmount(BigDecimal fcAmount) {
		this.fcAmount = fcAmount;
	}
	public BigDecimal getLocalAmount() {
		return localAmount;
	}
	public void setLocalAmount(BigDecimal localAmount) {
		this.localAmount = localAmount;
	}
	public Date getTranctionDate() {
		return tranctionDate;
	}
	public void setTranctionDate(Date tranctionDate) {
		this.tranctionDate = tranctionDate;
	}

	public List<UserFinancialYear> getUserFinancialYearList() {
		return userFinancialYearList;
	}
	public void setUserFinancialYearList(List<UserFinancialYear> userFinancialYearList) {
		this.userFinancialYearList = userFinancialYearList;
	}

	public List<ReceiptEnquiryDataTable> getLstreceiptEnquiryDTList() {
		return lstreceiptEnquiryDTList;
	}
	public void setLstreceiptEnquiryDTList(List<ReceiptEnquiryDataTable> lstreceiptEnquiryDTList) {
		this.lstreceiptEnquiryDTList = lstreceiptEnquiryDTList;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public BigDecimal getRemitDocNo() {
		return remitDocNo;
	}
	public void setRemitDocNo(BigDecimal remitDocNo) {
		this.remitDocNo = remitDocNo;
	}
	public Boolean getBooRenderDataTable() {
		return booRenderDataTable;
	}
	public void setBooRenderDataTable(Boolean booRenderDataTable) {
		this.booRenderDataTable = booRenderDataTable;
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void receiptEnquiryPageNavigation(){
		lstreceiptEnquiryDTList.clear();
		setUserFinancialYearList(null);
		clearAll();
		toFetchUserFinanicialYear();
		setBooRenderDataTable(false);
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "ReceiptEnquiry.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/ReceiptEnquiry.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void toFetchUserFinanicialYear(){
		List<UserFinancialYear> lstFinancialYear=iforeigncurrencyservice.getAllDocumentYear();
		if(lstFinancialYear != null && lstFinancialYear.size() !=0){
			setUserFinancialYearList(lstFinancialYear);
		}
	}

	public void exit(){
		lstreceiptEnquiryDTList.clear();
		setBooRenderDataTable(false);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clearAll(){
		setDocumentFinancialYear(null);
		setDocumentNo(null);
		lstreceiptEnquiryDTList.clear();
		setBooRenderDataTable(false);
	}

	/*public void receiptEnquiry(){
		  try{
		    lstreceiptEnquiryDTList.clear();
		    setBooRenderDataTable(false);
		  if(getDocumentFinancialYear() != null && getDocumentNo() != null){

			    List<ViewVwRemittanceTransaction> lstRemittanceTransactions=receiptEnquiryService.toFetchAllDetils(getDocumentFinancialYear(),getDocumentNo());
			    if(lstRemittanceTransactions.size() != 0){
				      for (ViewVwRemittanceTransaction viewVwRemittanceTransaction : lstRemittanceTransactions) {
						 ReceiptEnquiryDataTable dataTable=new ReceiptEnquiryDataTable();
						  dataTable.setDocumentFinancialYear(viewVwRemittanceTransaction.getDocumentFinYear());
						  dataTable.setDocumentNo(viewVwRemittanceTransaction.getApplicationDocumentNo());
						  dataTable.setTranctionType(viewVwRemittanceTransaction.getApplicationTypeDesc());
						  dataTable.setCustomerRef(viewVwRemittanceTransaction.getCustomerReference());
						  dataTable.setCustomerName(viewVwRemittanceTransaction.getFirstName());
						  if(viewVwRemittanceTransaction.getBenificiaryBank() != null){
						  dataTable.setBeneficiaryBAnk(viewVwRemittanceTransaction.getBenificiaryBank());
						  }
						  dataTable.setFcAmount(viewVwRemittanceTransaction.getForeignTrxAmount());
						  dataTable.setLocalAmount(viewVwRemittanceTransaction.getLocalTrxAmount());
						  dataTable.setTranctionDate(viewVwRemittanceTransaction.getDocumentDate());
						  if(viewVwRemittanceTransaction.getTransctionStatusDesc() != null){
						  dataTable.setStatus(viewVwRemittanceTransaction.getTransctionStatusDesc());
						  }
						  dataTable.setRemitDocNo(viewVwRemittanceTransaction.getDocumentNo());
						  dataTable.setReceiptNo(viewVwRemittanceTransaction.getCollectionDocNo() );
						  lstreceiptEnquiryDTList.add(dataTable);
						  setBooRenderDataTable(true);

			      }
			    }else{
				      setBooRenderDataTable(false);
				      RequestContext.getCurrentInstance().execute("noRecord.show();");
				      return;
			    }

		  }
		  }catch(NullPointerException  e){ 
				 	setErrorMessage("receiptEnquiry :");
 					RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
				}
			catch (Exception e) {
				setErrorMessage(e.getMessage());
				RequestContext.getCurrentInstance().execute("csp.show();");
				}
	  }*/

	public void receiptEnquiry(){
		try{
			lstreceiptEnquiryDTList.clear();
			setBooRenderDataTable(false);
			setPaidAmount(null);
			setNetAmount(null);
			setRefundAmount(null);
			
			if(getDocumentFinancialYear() != null && getDocumentNo() != null){

				List<ViewVwRemittanceTransaction> lstRemittanceTransactions = receiptEnquiryService.getCustomerReceiptDetails(getDocumentFinancialYear(),getDocumentNo());
				if(lstRemittanceTransactions.size() != 0){
					for (ViewVwRemittanceTransaction viewVwRemittanceTransaction : lstRemittanceTransactions) {
						ReceiptEnquiryDataTable dataTable=new ReceiptEnquiryDataTable();
						dataTable.setDocumentFinancialYear(viewVwRemittanceTransaction.getDocumentFinYear());
						dataTable.setDocumentNo(viewVwRemittanceTransaction.getDocumentNo());
						dataTable.setTranctionType(viewVwRemittanceTransaction.getApplicationTypeDesc());
						dataTable.setCustomerRef(viewVwRemittanceTransaction.getCustomerReference());
						dataTable.setCustomerName(nullCheck(viewVwRemittanceTransaction.getFirstName()) + nullCheck(viewVwRemittanceTransaction.getMiddleName()) + nullCheck(viewVwRemittanceTransaction.getLastName()));
						if(viewVwRemittanceTransaction.getBenificiaryBank() != null){
							dataTable.setBeneficiaryBAnk(viewVwRemittanceTransaction.getBenificiaryBank());
						}
						dataTable.setFcAmount(GetRound.roundBigDecimal(viewVwRemittanceTransaction.getForeignTrxAmount(),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(viewVwRemittanceTransaction.getForeignCurrencyId())));
						dataTable.setLocalAmount(GetRound.roundBigDecimal(viewVwRemittanceTransaction.getLocalTrxAmount(),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(viewVwRemittanceTransaction.getLocalTrxCurrencyId())));
						dataTable.setTranctionDate(viewVwRemittanceTransaction.getDocumentDate());
						if(viewVwRemittanceTransaction.getTransctionStatusDesc() != null){
							dataTable.setStatus(viewVwRemittanceTransaction.getTransctionStatusDesc());
						}
						dataTable.setRemitDocNo(viewVwRemittanceTransaction.getDocumentNo());
						dataTable.setReceiptNo(viewVwRemittanceTransaction.getCollectionDocNo());
						
						dataTable.setBeneficiaryName(nullCheck(viewVwRemittanceTransaction.getBenificiaryName()));
						dataTable.setBeneficiaryAccountNumber(nullCheck(viewVwRemittanceTransaction.getBenificiaryAccountNo()));
						
						setPaidAmount(viewVwRemittanceTransaction.getPaidAmount());
						setNetAmount(viewVwRemittanceTransaction.getNetAmount());
						setRefundAmount(viewVwRemittanceTransaction.getRefundAmount());
						
						lstreceiptEnquiryDTList.add(dataTable);
						setBooRenderDataTable(true);
					}
				}else{
					setBooRenderDataTable(false);
					RequestContext.getCurrentInstance().execute("noRecord.show();");
					return;
				}

			}
		}catch(NullPointerException  e){ 
			setErrorMessage("receiptEnquiry :");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch (Exception e) {
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}
	
	// to check null
	private String nullCheck(String custname) {
		return custname == null ? "" : custname.trim().concat(" ");
	}


	public void redircttoEnquiry(ReceiptEnquiryDataTable dataTable){
		setDocumentFinancialYear(dataTable.getDocumentFinancialYear());
		setRemitDocNo(dataTable.getRemitDocNo());
		setTranctionType(dataTable.getTranctionType());
		if(dataTable.getTranctionType().equalsIgnoreCase("REMITTANCE")){
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../enquiry/remittanceinquiry.xhtml");
				remittanceEnquiryBean.setDocYear(dataTable.getDocumentFinancialYear());
				remittanceEnquiryBean.setDocNumber(dataTable.getRemitDocNo());
				remittanceEnquiryBean.setRenderPanel(false);
				remittanceEnquiryBean.fetchData();
				remittanceEnquiryBean.setRenderForReceiptEnquiry(true);
				remittanceEnquiryBean.setRenderForHIGHVALUETrnxForFC(false);
				remittanceEnquiryBean.setRenderForHIGHVALUETrnx(false);
				remittanceEnquiryBean.setRenderForRemittanceBranchWiseEnquiry(false);
				remittanceEnquiryBean.setBackButtonRender(false);
				remittanceEnquiryBean.setExitButtonRender(false);
			} catch (Exception e) {
				log.error("Problem Occured in remittancePageNavigation() method");
			}
		}else if (dataTable.getTranctionType().equalsIgnoreCase("FOREIGN CURRENCY SALE")) {
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/foreigncurrencysaleenquiry.xhtml");
				fcSaleEnquiryBean.setDocumentFinancialYear(dataTable.getDocumentFinancialYear());
				fcSaleEnquiryBean.setDocumentNo(dataTable.getRemitDocNo());
				fcSaleEnquiryBean.setRenderPanel(false);
				fcSaleEnquiryBean.setRenderForRemittanceBranchWiseEnquiry(false);
				fcSaleEnquiryBean.setRenderBackButtonForRemittanceBranchWiseEnquiry(false);
				fcSaleEnquiryBean.setRenderBackButtonForReceiptEnquiry(true);
				fcSaleEnquiryBean.fcsaleEnquiry();
			} catch (Exception e) {
				log.error("Problem Occured in FCSALEPageNavigation() method");
			}      
		}
	}




	public void report(){
		if(getDocumentFinancialYear() != null && getDocumentNo() != null){
			generatePersonalRemittanceReceiptReport(getDocumentNo(),getDocumentFinancialYear());
		}
	}


	// Jasper Report generation
	private JasperPrint jasperPrint;
	private List<CollectionDetailView> collectionViewList = new CopyOnWriteArrayList<CollectionDetailView>();
	private List<RemittanceReceiptSubreport> remittanceReceiptSubreportList = new ArrayList<RemittanceReceiptSubreport>(); 
	public void generatePersonalRemittanceReceiptReport(BigDecimal doecumentNumber,BigDecimal DocumentYear){
		try {
			fetchRemittanceReceiptReportData(doecumentNumber,DocumentYear);
			remittanceReceiptReportInit();
			HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			httpServletResponse.setHeader("Cache-Control","cache, must-revalidate");
			httpServletResponse.addHeader("Content-disposition","attachment; filename=RemittanceReceiptReport.pdf");
			ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint,servletOutputStream);
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception e) {

		}

	}

	public void remittanceReceiptReportInit() throws JRException {
		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(remittanceReceiptSubreportList);
		//String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("reports/design/RemittanceReceiptNewReport.jasper");
		//String realPath = ctx.getRealPath("//");
		//String reportPath = realPath + "\\reports\\design\\RemittanceReceiptNewReport.jasper";
		String realPath = ctx.getRealPath("//");
		String reportPath = realPath +"//reports//design//RemittanceReceiptNewReport.jasper";
		jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap(),beanCollectionDataSource);
	}

	public List<RemittanceReportBean> calculateCollectionMode(RemittanceApplicationView viewCollectionObj){	
		List<RemittanceReportBean> collectionDetailList = new ArrayList<RemittanceReportBean>();
		List<CollectionPaymentDetailsView> collectionPaymentDetailList= iPersonalRemittanceService.getCollectionPaymentDetailForRemittanceReceipt(viewCollectionObj.getCompanyId(),viewCollectionObj.getCollectionDocumentNo(),viewCollectionObj.getCollectionDocFinanceYear(),viewCollectionObj.getCollectionDocCode());

		for(CollectionPaymentDetailsView viewObj: collectionPaymentDetailList){
			RemittanceReportBean obj = new RemittanceReportBean();

			obj.setCollectionMode(viewObj.getCollectionModeDesc());

			BigDecimal collectAmount=GetRound.roundBigDecimal((viewObj.getCollectAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(viewCollectionObj.getLocalTransactionCurrencyId()));
			obj.setCollectAmount(collectAmount);

			collectionDetailList.add(obj);
		}
		return collectionDetailList;
	}

	private void fetchRemittanceReceiptReportData(BigDecimal documentNumber, BigDecimal docYear) throws Exception{ 

		collectionViewList.clear();
		remittanceReceiptSubreportList = new CopyOnWriteArrayList<RemittanceReceiptSubreport>();



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

		String currencyQuoteName = generalService.getCurrencyQuote(new BigDecimal(sessionStateManage.getCurrencyId()));

		List<Employee> employeeList = iPersonalRemittanceService.getEmployeeDetails(sessionStateManage.getUserName());

		List<RemittanceApplicationView> remittanceViewlist = iPersonalRemittanceService.getRecordsForRemittanceReceiptReport(documentNumber,docYear,Constants.DOCUMENT_CODE_FOR_COLLECT_TRANSACTION);
				

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

				List<PurposeOfRemittanceView>  purposeOfRemittanceList =   iPersonalRemittanceService.getPurposeOfRemittance(view.getDocumentNo(),view.getDocumentFinancialYear());

				/*if(purposeOfRemittanceList!=null && purposeOfRemittanceList.size()>0){
					obj.setPerposeOfRemittance(purposeOfRemittanceList.get(0).getAmiecDescription());
				}*/
				/*String purposeOfRemittance = "";
				for(PurposeOfRemittanceView purposeObj :purposeOfRemittanceList){
					if(purposeOfRemittance.equals("")){
						purposeOfRemittance  = purposeObj.getAmiecDescription();
					}else{
						purposeOfRemittance  = purposeOfRemittance+", "+purposeObj.getAmiecDescription();
					}
				}

				if(purposeOfRemittance != null && !purposeOfRemittance.equalsIgnoreCase("")){
					obj.setPerposeOfRemittance(purposeOfRemittance);
				}*/

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
				if(view.getLastName()!=null){
					customerName.append(" ");
					customerName.append(view.getLastName());
					customerReff.append(" ");
					customerReff.append(view.getLastName());
				}

				obj.setFirstName(customerReff.toString());
				setCustomerNameForReport(customerName.toString());

				if(view.getContactNumber()!=null && !view.getContactNumber().trim().equalsIgnoreCase("")){
					obj.setMobileNo(new BigDecimal(view.getContactNumber().trim()));
				}

				obj.setCivilId(view.getIdentityInt());

				Date sysdate = view.getIdentityExpiryDate();

				if(sysdate!=null){
					obj.setIdExpiryDate(new SimpleDateFormat("dd/MM/yyy").format(sysdate));
				}

				//obj.setLocation(sessionStateManage.getLocation());
				obj.setLocation(view.getCountryBranchName());
				

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
				
			

				obj.setDate(dateFormat.format(view.getDocumentDate()));
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


				StringBuffer loyaltyPoint = new StringBuffer();
				if(!prLtyStr1.trim().equals("")){
					loyaltyPoint.append(prLtyStr1);
				}
				if(!prLtyStr2.trim().equals("")){
					loyaltyPoint.append("\n");
					loyaltyPoint.append(prLtyStr2);
				}
				
				List<CutomerDetailsView> customerListD = iPersonalRemittanceService.getCustomerDetails(view.getCustomerId());

				if (customerListD != null && customerListD.size() > 0) {
					CutomerDetailsView cust = customerListD.get(0);
					if(cust.getIdType()!=null && cust.getIdType().compareTo(new BigDecimal(Constants.CORPORATE_ID_TYPE))==0){
						obj.setLoyalityPointExpiring("");
					}else{
						obj.setLoyalityPointExpiring(loyaltyPoint.toString());
					}
				}
				
				//obj.setLoyalityPointExpiring(loyaltyPoint.toString());

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
					paymentChannel.append(" - ");
				}
				if(view.getDeliveryDescription() != null){
					paymentChannel.append(view.getDeliveryDescription());
				}
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
				
				//For AMTB COUPON added by Rabil
				BigDecimal lessAmtbCouponEncash = BigDecimal.ZERO;
				
				
				List<CollectionPaymentDetailsView> collectionPmtDetailList= iPersonalRemittanceService.getCollectionPaymentDetailForRemittanceReceipt(view.getCompanyId(),view.getCollectionDocumentNo(),view.getCollectionDocFinanceYear(),view.getCollectionDocCode());
				for(CollectionPaymentDetailsView collPaymentDetailsView: collectionPmtDetailList){
					if(collPaymentDetailsView.getCollectionMode().equalsIgnoreCase(Constants.VOCHERCODE)){
						lessLoyaltyEncash = collPaymentDetailsView.getCollectAmount();
						amountPayable=amountPayable.add(collPaymentDetailsView.getCollectAmount());
					}else if(collPaymentDetailsView.getCollectionMode().equalsIgnoreCase(Constants.AMTBC)){ //FOR AMTB COUPON 
						lessAmtbCouponEncash = collPaymentDetailsView.getCollectAmount();
						amountPayable=amountPayable.add(collPaymentDetailsView.getCollectAmount());
					}	else{
						amountPayable=amountPayable.add(collPaymentDetailsView.getCollectAmount());
					}
				}
				if(lessLoyaltyEncash.compareTo(BigDecimal.ZERO)==0){
					obj.setLessLoyaltyEncasement(null);					
				}else{
					BigDecimal loyaltyAmount=GetRound.roundBigDecimal((lessLoyaltyEncash),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setLessLoyaltyEncasement(currencyQuoteName+"     ******"+loyaltyAmount);
				}
				
				
				
				//For AMTB AMOUT
				if(lessAmtbCouponEncash.compareTo(BigDecimal.ZERO)==0){
					obj.setLessAmtbCouponEncasement(null);					
				}else{
					BigDecimal amtbAmount=GetRound.roundBigDecimal((lessAmtbCouponEncash),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setLessAmtbCouponEncasement(currencyQuoteName+"     ******"+amtbAmount);
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
				//For Company information ADDED BY VISWA --END
				
				if(obj.getFirstName() == null || obj.getFirstName().isEmpty()){
					List<CutomerDetailsView> customerList = iPersonalRemittanceService.getCustomerDetails(view.getCustomerId());

					if (customerList.size() > 0) {
						CutomerDetailsView cust = customerList.get(0);
						obj.setFirstName(cust.getCustomerName());
						if(cust.getContactNumber()!=null && !cust.getContactNumber().trim().equalsIgnoreCase("")){
							obj.setMobileNo(new BigDecimal(cust.getContactNumber().trim()));
						}
						obj.setCivilId(cust.getIdNumber());
						Date sysdate1 = cust.getIdExp();
						if(sysdate1 != null){
							obj.setIdExpiryDate(new SimpleDateFormat("dd/MM/yyyy").format(sysdate1));
						}
						
						if(cust.getIdType()!=null && cust.getIdType().compareTo(new BigDecimal(Constants.CORPORATE_ID_TYPE))==0){
							obj.setLoyalityPointExpiring("");
						}
					}
				}
				
				
				// Logo for Go Green added by Viswa
				
				ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
				realPath = ctx.getRealPath("//");
				//String logoPath = realPath + Constants.LOGO_PATH;
				
				String logoPath =null;
				if(sessionStateManage.getCountryName().equalsIgnoreCase(Constants.KUWAIT)){
					logoPath = realPath+Constants.LOGO_PATH;
				}else if(sessionStateManage.getCountryName().equalsIgnoreCase(Constants.OMAN)){
					logoPath = realPath+Constants.LOGO_PATH_OM;
				}else{
					logoPath =realPath+Constants.LOGO_PATH;
				}
				/*List<String> customerList = icustomerRegistrationService.getCustomerEmailDetails(view.getCustomerId());
				
				String customerEmail=null;
				String customerEmailVerifiedOn=null;
				if(customerList!=null && customerList.size()>0)
				{
				   	customerEmail=customerList.get(0);
				   	
					if(customerList.size()>1)
					{
						customerEmailVerifiedOn=customerList.get(1);
					}
					
				}
				if(customerEmail!=null && customerEmailVerifiedOn!=null)
				{
					obj.setSendEmail(true);
					obj.setLogoPath(logoPath);
				}else{
					obj.setSendEmail(false);
				}*/
				obj.setSendEmail(false);
				
				// End 


				remittanceApplList.add(obj);
			}

			// for foreign currency Sale report
			for (RemittanceApplicationView view : fcsaleList) {

				RemittanceReportBean obj = new RemittanceReportBean();
				
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
						arabicCompanyInfo=arabicCompanyInfo.append(Constants.CR + " "+companyMaster.get(0).getRegistrationNumber()+",");
					}						
					if(companyMaster.get(0).getCapitalAmount()!=null&&companyMaster.get(0).getCapitalAmount().length()>0){
						arabicCompanyInfo=arabicCompanyInfo.append(Constants.Share_Capital + " " +companyMaster.get(0).getCapitalAmount());
					}
					obj.setArabicCompanyInfo(arabicCompanyInfo.toString());
				}
				//For Company information ADDED BY VISWA --END

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
				if(view.getLastName()!=null){
					customerName.append(" ");
					customerName.append(view.getLastName());
					customerReff.append(" ");
					customerReff.append(view.getLastName());
				}

				obj.setFirstName(customerReff.toString());
				setCustomerNameForReport(customerName.toString());

				if(view.getContactNumber()!=null && !view.getContactNumber().trim().equalsIgnoreCase("")){
					obj.setMobileNo(new BigDecimal(view.getContactNumber().trim()));
				}
				
				
				
				
				if(view.getIdentityInt() != null && view.getIdentityExpiryDate() != null){
					obj.setCivilId(view.getIdentityInt());
					Date sysdate = view.getIdentityExpiryDate();
					if(sysdate!=null){
						obj.setIdExpiryDate(new SimpleDateFormat("dd/MM/yyyy").format(sysdate));
					}
				}else{
					// civil id not available then it is crno
					List<CustomerIdproofView> lstcustProof = generalService.getCustomerIdProofDetailsFromView(view.getCustomerId());
					if(lstcustProof != null && lstcustProof.size() != 0){
						CustomerIdproofView custDet = lstcustProof.get(0);
						
						if(custDet != null){
							if(custDet.getIdProofInt() != null){
								obj.setCivilId(custDet.getIdProofInt());
							}
							
							if(custDet.getIdProofExpiredDate() != null){
								obj.setIdExpiryDate(new SimpleDateFormat("dd/MM/yyyy").format(custDet.getIdProofExpiredDate()));
							}
							
						}
						
					}
				}


				HashMap<String, String> loyaltiPoints  =iPersonalRemittanceService.getloyalityPointsFromProcedure(view.getCustomerReference(), view.getDocumentDate());

				String prLtyStr1 =loyaltiPoints.get("P_LTY_STR1");
				String prLtyStr2 =loyaltiPoints.get("P_LTY_STR2");
				String prInsStr1 =loyaltiPoints.get("P_INS_STR1");
				String prInsStr2 =loyaltiPoints.get("P_INS_STR2");
				String prInsStrAr1 =loyaltiPoints.get("P_INS_STR_AR1");
				String prInsStrAr2 =loyaltiPoints.get("P_INS_STR_AR2");


				StringBuffer loyaltyPoint = new StringBuffer();

				if(!prLtyStr1.trim().equals("")){
					loyaltyPoint.append(prLtyStr1);
				}
				if(!prLtyStr2.trim().equals("")){
					loyaltyPoint.append("\n");
					loyaltyPoint.append(prLtyStr2);
				}
				
				List<CutomerDetailsView> customerListD = iPersonalRemittanceService.getCustomerDetails(view.getCustomerId());

				if (customerListD != null && customerListD.size() > 0) {
					CutomerDetailsView cust = customerListD.get(0);
					if(cust.getIdType()!=null && cust.getIdType().compareTo(new BigDecimal(Constants.CORPORATE_ID_TYPE))==0){
						obj.setLoyalityPointExpiring("");
					}else{
						obj.setLoyalityPointExpiring(loyaltyPoint.toString());
					}
				}
				
				
				
				//obj.setLoyalityPointExpiring(loyaltyPoint.toString());

				StringBuffer insurence = new StringBuffer();

				if(!prInsStr1.trim().equals("")){
					insurence.append(prInsStr1);
				}
				if(prInsStrAr1.trim().equals("")){
					insurence.append("\n");
					insurence.append(prInsStrAr1);
				}

				if(!prInsStr2.trim().equals("")){
					insurence.append("\n");
					insurence.append(prInsStr2);
				}
				if(!prInsStrAr2.trim().equals("")){
					insurence.append("\n");
					insurence.append(prInsStrAr2);
				}
				if(!insurence.equals("")){
					obj.setInsurence1(insurence.toString());
				}
				

				obj.setLocation(view.getCountryBranchName());
				obj.setPhoneNumber(view.getPhoneNumber());
				obj.setDate(dateFormat.format(view.getDocumentDate()));
				//obj.setUserName(sessionStateManage.getUserName());
				obj.setUserName(view.getCreatedBy());
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

				if(view.getForeignCurrencyId()!=null){
					String saleCurrency = specialCustomerDealRequestService.getCurrencyForUpdate(view.getForeignCurrencyId());
					obj.setCurrencyQuoteName(saleCurrency);
				}
				String saleCurrencyCode =null;

				if(view.getForeignCurrencyId()!=null){
					saleCurrencyCode = generalService.getCurrencyQuote(view.getForeignCurrencyId());
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

				/*List<PurposeOfRemittanceView>  purposeOfRemittanceList =   iPersonalRemittanceService.getPurposeOfRemittance(view.getDocumentNo(),view.getDocumentFinancialYear());

				if(purposeOfRemittanceList!=null && purposeOfRemittanceList.size()>0){
					obj.setPerposeOfRemittance(purposeOfRemittanceList.get(0).getAmiecDescription());
				}*/
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
				
				if(obj.getFirstName() == null || obj.getFirstName().isEmpty()){
					List<CutomerDetailsView> customerList = iPersonalRemittanceService.getCustomerDetails(view.getCustomerId());

					if (customerList.size() > 0) {
						CutomerDetailsView cust = customerList.get(0);
						obj.setFirstName(cust.getCustomerName());
						if(cust.getContactNumber()!=null && !cust.getContactNumber().trim().equalsIgnoreCase("")){
							obj.setMobileNo(new BigDecimal(cust.getContactNumber().trim()));
						}
						obj.setCivilId(cust.getIdNumber());
						Date sysdate1 = cust.getIdExp();
						if(sysdate1 != null){
							obj.setIdExpiryDate(new SimpleDateFormat("dd/MM/yyyy").format(sysdate1));
						}
					}
				}
				
				// Logo for Go Green added by Viswa
				
				ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
				realPath = ctx.getRealPath("//");
				//String logoPath = realPath + Constants.LOGO_PATH;
				
				String logoPath =null;
				if(sessionStateManage.getCountryName().equalsIgnoreCase(Constants.KUWAIT)){
					logoPath = realPath+Constants.LOGO_PATH;
				}else if(sessionStateManage.getCountryName().equalsIgnoreCase(Constants.OMAN)){
					logoPath = realPath+Constants.LOGO_PATH_OM;
				}else{
					logoPath =realPath+Constants.LOGO_PATH;
				}
				/*List<String> customerList = icustomerRegistrationService.getCustomerEmailDetails(view.getCustomerId());
				
				String customerEmail=null;
				String customerEmailVerifiedOn=null;
				if(customerList!=null && customerList.size()>0)
				{
				   	customerEmail=customerList.get(0);
				   	
					if(customerList.size()>1)
					{
						customerEmailVerifiedOn=customerList.get(1);
					}
					
				}
				if(customerEmail!=null && customerEmailVerifiedOn!=null)
				{
					obj.setSendEmail(true);
					obj.setLogoPath(logoPath);
				}else{
					obj.setSendEmail(false);
				}*/
				obj.setSendEmail(false);

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

		} else {
			RequestContext.getCurrentInstance().execute("noRecord.show();");
			return;
		}


	}
	
	private String customerNameForReport;
	private BigDecimal paidAmount;
	private BigDecimal netAmount;
	private BigDecimal refundAmount;

	public String getCustomerNameForReport() {
		return customerNameForReport;
	}
	public void setCustomerNameForReport(String customerNameForReport) {
		this.customerNameForReport = customerNameForReport;
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
	
	

}
