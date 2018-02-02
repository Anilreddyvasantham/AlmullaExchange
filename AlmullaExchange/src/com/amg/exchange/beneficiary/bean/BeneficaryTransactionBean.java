package com.amg.exchange.beneficiary.bean;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;

import org.apache.log4j.Logger;
import org.codehaus.groovy.tools.shell.commands.SetCommand;
import org.primefaces.context.RequestContext;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.amg.exchange.beneficiary.model.CustomerRemittanceTransactionView;
import com.amg.exchange.cancelreissue.model.ViewRemiitanceInfo;
import com.amg.exchange.cashtransfer.service.ICashTransferLToLService;
import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.common.model.CutomerDetailsView;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.enquiry.bean.RemittanceInquiryBean;
import com.amg.exchange.foreigncurrency.bean.DenominationBean;
import com.amg.exchange.foreigncurrency.bean.ForeignCurrencyPurchageReport;
import com.amg.exchange.foreigncurrency.bean.ForeignCurrencyPurchaseBeanEnquiry;
import com.amg.exchange.foreigncurrency.model.Collect;
import com.amg.exchange.foreigncurrency.model.ForeignCurrencyPurchaseReport;
import com.amg.exchange.foreigncurrency.model.PurposeOfTransaction;
import com.amg.exchange.foreigncurrency.model.ReceiptPaymentView;
import com.amg.exchange.foreigncurrency.model.SourceOfIncome;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.foreigncurrency.service.ForeignLocalCurrencyDenominationService;
import com.amg.exchange.foreigncurrency.service.IForeignCurrencyPurchaseService;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerEmployeeInfoView;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.registration.model.CustomerIdproofView;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.registration.service.ICustomerRegistrationBranchService;
import com.amg.exchange.remittance.bean.CustomerInquiryDataTable;
import com.amg.exchange.remittance.bean.DeclerationReportBean;
import com.amg.exchange.remittance.bean.FCSaleEnquiryBean;
import com.amg.exchange.remittance.bean.PersonalRemittanceCollectionDataTable;
import com.amg.exchange.remittance.bean.PurposeOfRemittanceReportBean;
import com.amg.exchange.remittance.bean.RemittanceDeclarationMainReportBean;
import com.amg.exchange.remittance.bean.RemittanceReceiptSubreport;
import com.amg.exchange.remittance.bean.RemittanceReportBean;
import com.amg.exchange.remittance.model.CollectionDetailView;
import com.amg.exchange.remittance.model.CollectionPaymentDetailsView;
import com.amg.exchange.remittance.model.CustomerDeclerationView;
import com.amg.exchange.remittance.model.PurposeOfRemittanceView;
import com.amg.exchange.remittance.model.RemittanceApplicationView;
import com.amg.exchange.remittance.service.IPersonalRemittanceService;
import com.amg.exchange.stoppayment.service.IStopPaymentService;
import com.amg.exchange.treasury.service.ISpecialCustomerDealRequestService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.GetRound;
import com.amg.exchange.util.SessionStateManage;
import com.amg.exchange.wuh2h.bean.WURemittanceReceiptSubreport;
import com.amg.exchange.wuh2h.bean.WURemittanceReportBean;
import com.amg.exchange.wuh2h.model.WURemittanceApplicationView;
import com.amg.exchange.wuh2h.model.WUReportTermsAndConditions;
import com.amg.exchange.wuh2h.service.IWUH2HService;

@Component("beneficaryTransactionBean")
@Scope("session")
public class BeneficaryTransactionBean<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final Logger log = Logger.getLogger(BeneficaryTransactionBean.class);
	SessionStateManage sessionStateManage = new SessionStateManage();

	private String id = null;
	private String name = null;
	private String mobile = null;
	private int customerId;
	private BigDecimal customerrefno;
	private BigDecimal countryCurrencyId;
	private BigDecimal currencyId;
	private BigDecimal saleAmountA;
	private BigDecimal totalPurchaseAmount;
	private BigDecimal totsaleAmount;
	private BigDecimal avgExchageRate;
	private String sourceOfIncomes;
	private String purposeOfTransactions;
	/** Responsible to manage session */

	/* Responsible to Date Management */
	private int financeMonth;
	private String totalValue = null;
	private String signatureSpecimen;
	private int documentId = Integer.parseInt(Constants.DOCUMENT_CODE_FOR_FCSALE);
	private String processIn = Constants.Yes;



	private String msgs = null;
	List<Employee> list = new ArrayList<>();
	private String remarks;
	private String digitalSign;// new property for digital sign
	private StreamedContent myImage;
	private String docSerialIdNumberForSave;
	private Boolean firstTime = null;
	private String remitRequest;
	private String errmsg;
	private String civilId;
	private String passportNo;
	private String mobileNo;
	private BigDecimal officeTelephone;
	private BigDecimal residenceTelephone;
	private String nickName;
	private String status;
	private String aceamlStatus;
	private String userName;
	private String crNo;
	private BigDecimal numberOfHits;
	private String   amlDate;
	private Boolean booDialogCall=false;
	private String createdBy;
	private Boolean booRenderData=false;
	private  String exceptionMessageForReport;
	private String sendPromoDiscountAmount;

	private List<DenominationBean> denominationBeanList = new ArrayList<DenominationBean>();
	private List<PurposeOfTransaction> lstPurposeOfTransaction = new ArrayList<>();
	private List<SourceOfIncome> lstSourceOfIncome = new ArrayList<>();
	private List<UserFinancialYear> financialYearList = new ArrayList<>();
	private ArrayList<CustomerInquiryDataTable> customerInquiryDataList = new ArrayList<CustomerInquiryDataTable>();



	
	
	@Autowired
	IGeneralService<T> igeneralService;

	@Autowired
	IForeignCurrencyPurchaseService<T> foreignCurrencyPurchaseService;

	@Autowired
	IPersonalRemittanceService iPersonalRemittanceService;

	@Autowired
	ICustomerRegistrationBranchService<T> customerService;
	
	@Autowired
	IWUH2HService iwuh2hService;
	
	public String getSendPromoDiscountAmount() {
		return sendPromoDiscountAmount;
	}

	public void setSendPromoDiscountAmount(String sendPromoDiscountAmount) {
		this.sendPromoDiscountAmount = sendPromoDiscountAmount;
	}

	public BigDecimal getCustomerrefno() {
		return customerrefno;
	}

	public void setCustomerrefno(BigDecimal customerrefno) {
		this.customerrefno = customerrefno;
	}

	public ArrayList<CustomerInquiryDataTable> getCustomerInquiryDataList() {
		return customerInquiryDataList;
	}

	public void setCustomerInquiryDataList(ArrayList<CustomerInquiryDataTable> customerInquiryDataList) {
		this.customerInquiryDataList = customerInquiryDataList;
	}

	public BigDecimal getNumberOfHits() {
		return numberOfHits;
	}

	public void setNumberOfHits(BigDecimal numberOfHits) {
		this.numberOfHits = numberOfHits;
	}

	public String getCrNo() {
		return crNo;
	}

	public void setCrNo(String crNo) {
		this.crNo = crNo;
	}
	public BigDecimal getResidenceTelephone() {
		return residenceTelephone;
	}

	public void setResidenceTelephone(BigDecimal residenceTelephone) {
		this.residenceTelephone = residenceTelephone;
	}

	public String getCivilId() {
		return civilId;
	}

	public void setCivilId(String civilId) {
		this.civilId = civilId;
	}

	public String getPassportNo() {
		return passportNo;
	}

	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public BigDecimal getOfficeTelephone() {
		return officeTelephone;
	}

	public void setOfficeTelephone(BigDecimal officeTelephone) {
		this.officeTelephone = officeTelephone;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAceamlStatus() {
		return aceamlStatus;
	}

	public void setAceamlStatus(String aceamlStatus) {
		this.aceamlStatus = aceamlStatus;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public String getSignatureSpecimen() {
		return signatureSpecimen;
	}

	public void setSignatureSpecimen(String signatureSpecimen) {
		this.signatureSpecimen = signatureSpecimen;
	}

	public String getRemitRequest() {
		return remitRequest;
	}

	public void setRemitRequest(String remitRequest) {
		this.remitRequest = remitRequest;
	}

	public Boolean getFirstTime() {
		return firstTime;
	}

	public void setFirstTime(Boolean firstTime) {
		this.firstTime = firstTime;
	}

	public StreamedContent getMyImage() {
		return null;
	}
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public IForeignCurrencyPurchaseService<T> getForeignCurrencyPurchaseService() {
		return foreignCurrencyPurchaseService;
	}

	public void setForeignCurrencyPurchaseService(IForeignCurrencyPurchaseService<T> foreignCurrencyPurchaseService) {
		this.foreignCurrencyPurchaseService = foreignCurrencyPurchaseService;
	}

	public BeneficaryTransactionBean() {
	}


	public BigDecimal getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	public int getDocumentId() {
		return documentId;
	}

	public void setDocumentId(int documentId) {
		this.documentId = documentId;
	}

	public String getProcessIn() {
		return processIn;
	}

	public void setProcessIn(String processIn) {
		this.processIn = processIn;
	}

	public BigDecimal getTotsaleAmount() {
		return totsaleAmount;
	}

	public void setTotsaleAmount(BigDecimal totsaleAmount) {
		this.totsaleAmount = totsaleAmount;
	}

	public BigDecimal getSaleAmountA() {
		return saleAmountA;
	}

	public void setSaleAmountA(BigDecimal saleAmountA) {
		this.saleAmountA = saleAmountA;
	}

	public BigDecimal getTotalPurchaseAmount() {
		return totalPurchaseAmount;
	}

	public void setTotalPurchaseAmount(BigDecimal totalPurchaseAmount) {
		this.totalPurchaseAmount = totalPurchaseAmount;
	}

	public List<DenominationBean> getDenominationBeanList() {
		return denominationBeanList;
	}

	public void setDenominationBeanList(List<DenominationBean> denominationBeanList) {
		this.denominationBeanList = denominationBeanList;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getMsgs() {
		return msgs;
	}

	public void setMsgs(String msgs) {
		this.msgs = msgs;
	}

	public List<PurposeOfTransaction> getLstPurposeOfTransaction() {
		return lstPurposeOfTransaction;
	}

	public void setLstPurposeOfTransaction(List<PurposeOfTransaction> lstPurposeOfTransaction) {
		this.lstPurposeOfTransaction = lstPurposeOfTransaction;
	}

	public List<SourceOfIncome> getLstSourceOfIncome() {
		return lstSourceOfIncome;
	}

	public void setLstSourceOfIncome(List<SourceOfIncome> lstSourceOfIncome) {
		this.lstSourceOfIncome = lstSourceOfIncome;
	}

	public String getPurposeOfTransactions() {
		return purposeOfTransactions;
	}

	public void setPurposeOfTransactions(String purposeOfTransactions) {
		this.purposeOfTransactions = purposeOfTransactions;
	}

	public String getSourceOfIncomes() {
		return sourceOfIncomes;
	}

	public void setSourceOfIncomes(String sourceOfIncomes) {
		this.sourceOfIncomes = sourceOfIncomes;
	}

	public BigDecimal getCountryCurrencyId() {
		return countryCurrencyId;
	}

	public void setCountryCurrencyId(BigDecimal countryCurrencyId) {
		this.countryCurrencyId = countryCurrencyId;
	}

	/******************************************************* Date Calculation End *********************************************************/
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getFinanceMonth() {
		return financeMonth;
	}

	public void setFinanceMonth(int financeMonth) {
		this.financeMonth = financeMonth;
	}

	public String getLocation() {
		return sessionStateManage.getLocation();
	}

	public BigDecimal getAvgExchageRate() {
		return avgExchageRate;
	}

	public void setAvgExchageRate(BigDecimal avgExchageRate) {
		this.avgExchageRate = avgExchageRate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public List<UserFinancialYear> getFinancialYearList() {
		return financialYearList;
	}

	public void setFinancialYearList(List<UserFinancialYear> financialYearList) {
		this.financialYearList = financialYearList;
	}

	private String varToKeepSerial = null;

	public String getVarToKeepSerial() {
		return varToKeepSerial;
	}

	public void setVarToKeepSerial(String varToKeepSerial) {
		this.varToKeepSerial = varToKeepSerial;
	}

	public String getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(String totalValue) {
		this.totalValue = totalValue;
	}

	private String validNoOFQuantity = null;

	public void acceptFromPurchase() {
	}

	public void backFromSalePanel() {
	}


	/**
	 * Check for Stock Availability
	 * 
	 * @param value
	 * @param denomId
	 */

	private String validNoNotes = null;
	@SuppressWarnings("unused")
	private Date Date;



	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getValidNoNotes() {
		return validNoNotes;
	}

	public void setValidNoNotes(String validNoNotes) {
		this.validNoNotes = validNoNotes;
	}

	public String getValidNoOFQuantity() {
		return validNoOFQuantity;
	}

	public void setValidNoOFQuantity(String validNoOFQuantity) {
		this.validNoOFQuantity = validNoOFQuantity;
	}


	private String email;
	private String emailId;
	@SuppressWarnings("unused")
	private String password;
	private String location1;
	private double limitationAmount;

	public double getLimitationAmount() {
		return limitationAmount;
	}

	public void setLimitationAmount(double limitationAmount) {
		this.limitationAmount = limitationAmount;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getEmail1() {
		return email1;
	}

	public void setEmail1(String email1) {
		this.email1 = email1;
	}

	String email1 = null;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	@Autowired(required = true)
	JavaMailSender mailSender1;

	public JavaMailSender getMailSender1() {
		return mailSender1;
	}

	public void setMailSender1(JavaMailSender mailSender1) {
		this.mailSender1 = mailSender1;
	}

	public String getDigitalSign() {
		return digitalSign;
	}

	public void setDigitalSign(String digitalSign) {
		this.digitalSign = digitalSign;
	}

	public String getDocSerialIdNumberForSave() {
		return docSerialIdNumberForSave;
	}

	public void setDocSerialIdNumberForSave(String docSerialIdNumberForSave) {
		this.docSerialIdNumberForSave = docSerialIdNumberForSave;
	}

	public void cleraFields() {
		// setSignaturePanel(false);
		setFinanceMonth(0);
		setId(null);
		setName(null);
		setMobile(null);
		setCurrencyId(null);
		denominationBeanList.clear();
		setTotsaleAmount(null);
		setAvgExchageRate(null);
		setRemarks(null);
		setTotalPurchaseAmount(null);
		setSourceOfIncomes(null);
		setPurposeOfTransactions(null);
		// setForNextPanels(false);
		setVarToKeepSerial(null);
		// setNeededPurchaseAmount(0.0);
		// setMyImage(null);
		setDigitalSign(null);
		setSignatureSpecimen(null);
		setCrNo(null);
		setCivilId(null);
		// lstRefundData.clear();
	}

	// to check null
	private String nullCheck(String custname) {
		return custname == null ? "" : custname.trim().concat(" ");
	}

	public void callFromRemittance(BigDecimal customerId, String idNumber)// 22/01/2015
	{
		setCustomerIdNumber(customerId);
		try {
			cleraFields();
			List<CustomerIdProof> data = getForeignCurrencyPurchaseService().dataCust(idNumber);
			@SuppressWarnings("unused")
			InputStream stream = null;
			@SuppressWarnings("unused")
			Blob blob = null;
			if (data != null && data.size() > 0) {
				if(data.get(0).getFsCustomer().getFirstName() != null || data.get(0).getFsCustomer().getMiddleName() != null || data.get(0).getFsCustomer().getLastName() != null){
					//setName(data.get(0).getFsCustomer().getFirstName()+""+data.get(0).getFsCustomer().getMiddleName() +""+data.get(0).getFsCustomer().getLastName());
					//setName(data.get(0).getFsCustomer().getFirstName() == null ? " ":data.get(0).getFsCustomer().getFirstName()+""+data.get(0).getFsCustomer().getMiddleName() == null ? " " :data.get(0).getFsCustomer().getMiddleName() +""+data.get(0).getFsCustomer().getLastName()==null  ? " ":data.get(0).getFsCustomer().getLastName());
					setName(nullCheck(data.get(0).getFsCustomer().getFirstName()) + nullCheck(data.get(0).getFsCustomer().getMiddleName()) + nullCheck(data.get(0).getFsCustomer().getLastName()));
				}else if (data.get(0).getFsCustomer().getCompanyName() != null) {
					setName(data.get(0).getFsCustomer().getCompanyName());
				}
				setCustomerId(Integer.parseInt(data.get(0).getFsCustomer().getCustomerId().toString()));
				setMobile(data.get(0).getFsCustomer().getMobile());
				setId(idNumber);
				setDigitalSign(null);
				setSignatureSpecimen(data.get(0).getFsCustomer().getSignatureSpecimenClob().getSubString(1, (int) data.get(0).getFsCustomer().getSignatureSpecimenClob().length()));
			} else {
				setName("");
				setMobile("");
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("idNotF.show();");
			}
		} catch (Exception e) {
		}
	}

	public void backToRemitance() {
		log.info("Entering into backToRemitance method");
		setFirstTime(null);
		removeAttributeFromSession();
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		try {
			HttpSession session = sessionStateManage.getSession();
			session.setAttribute("backToFC", "yes");
			if (getRemitRequest() != null && getRemitRequest().equals("C")) {
				setBooDialogCall(false);
				context.redirect("../remittance/CorporateRemittance.xhtml");
				setFirstTime(null);
				removeAttributeFromSession();
			}
			if (getRemitRequest() != null && getRemitRequest().equals("P")) {
				setBooDialogCall(false);
				setFirstTime(null);
				removeAttributeFromSession();

				context.redirect("../remittance/PersonalRemittance.xhtml");
			}
			// personalRemittanceBean.nextrenderingLastPanel();
		} catch (Exception e) {
			log.info("Problem to redirect:" + e);
		}
		log.info("Exit into backToRemitance method");
	}

	public void backToRemitanceFirstPanel() {
		setBooRenderData( false);
		setBooRenderReport( false);
		log.info("Entering into backToRemitanceFirstPanel method");
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		HttpSession session = sessionStateManage.getSession();
		try {
			setBooDialogCall(false);
			session.setAttribute("fromBeneficaryTransactions", "yes");
			context.redirect("../remittance/PersonalRemittance.xhtml");
			// personalRemittanceBean.fromAccountExistDialogToBeneficaryTelephone();
		} catch (Exception e) {
			log.info("Problem to redirect:" + e);
		}
		setFirstTime(null);
		removeAttributeFromSession();
		log.info("Exit into backToRemitanceFirstPanel method");
	}



	private Boolean successPanel = false;
	private Boolean booRenderFcSalePanel = false;

	public Boolean getBooRenderFcSalePanel() {
		return booRenderFcSalePanel;
	}

	public void setBooRenderFcSalePanel(Boolean booRenderFcSalePanel) {
		this.booRenderFcSalePanel = booRenderFcSalePanel;
	}

	public Boolean getSuccessPanel() {
		return successPanel;
	}

	public void setSuccessPanel(Boolean successPanel) {
		this.successPanel = successPanel;
	}

	// Report Generation By Rahamathali Shaik............
	private BigDecimal customerIdNumber;

	public BigDecimal getCustomerIdNumber() {
		return customerIdNumber;
	}

	public void setCustomerIdNumber(BigDecimal customerIdNumber) {
		this.customerIdNumber = customerIdNumber;
	}

	public void populateValue() {
		setBooRenderData(false);
		if (firstTime == null || FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("remit") != null) {

			customerInquiryDataList.clear();
			/* setFirstTime(true); */
			HttpSession session = sessionStateManage.getSession();
			BigDecimal customerNo = (BigDecimal) session.getAttribute("customerRefNo");

			setCustomerrefno(customerNo);
			String IdNumber = (String) session.getAttribute("IdNumber");
			log.info( ":::::::::::::::::::::::::::::::::::::::civilidd"+IdNumber);
			log.info(":::::::::::::::::::::::::::::::::::::::customerNo="+ customerNo);
			String remitRequest = (String) session.getAttribute("remit");
			setRemitRequest(remitRequest);
			setBooRenderFcSalePanel(true);
			setBooRenderFcSalePanel(true);
			setSuccessPanel(false);
			callFromRemittance(customerNo, IdNumber);
			setFirstTime(true);
			removeAttributeFromSession();
			session.setAttribute("IdNumber", IdNumber);

			List<Customer> customerList = igeneralService.getCustomerDeatilsBasedOnRef(customerNo );
			log.info(":::::::::::::::::::::::::::::::::::::::customerList.size()="+ customerList.size());
			if(customerList.size()>0){
				log.info(":::::::::::::::::::::::::::::::::::::::customerList.size()="+ customerList.size());
				log.info(":::::::::::::::::::::::::::::::::::::::cicvilidBELOW="+IdNumber);
				setStatus(customerList.get(0).getAmlStatus());
				setAceamlStatus(customerList.get(0).getAmlStatus());
				if(customerList.get(0).getCrNo() != null){
					setCrNo(customerList.get(0).getCrNo());
				}else{
					setCivilId(IdNumber);
				}
				setMobileNo(customerList.get(0).getMobile());
				setNumberOfHits(customerList.get(0).getNumberOfHits());
				setUserName(sessionStateManage.getUserName());

				log.info(":::::::::::::::::::::::::::::::::::::::amldate"+customerList.get(0).getVerificationDate());
				Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				if(customerList.get(0).getVerificationDate()!=null){
					String s = formatter.format( customerList.get(0).getVerificationDate());
					setAmlDate(s);
				}
			}

			List<CustomerRemittanceTransactionView> customerInquiryList = new ArrayList<CustomerRemittanceTransactionView>();
			try {
				customerInquiryList = iPersonalRemittanceService.getBeneficaryTxnInquiryList(customerNo);
			} catch (Exception ex) {
				log.info("Problem in VIEW :" + ex);
				setErrmsg(ex.getMessage());
				RequestContext.getCurrentInstance().execute("procedureErr.show();");
			}

			CustomerInquiryDataTable dataTable =null;

			for (CustomerRemittanceTransactionView viewObject : customerInquiryList) {

				dataTable = new CustomerInquiryDataTable();
				//Added by Rabil From SVN 
				dataTable.setCustomerReference(viewObject.getCustomerReference()); 
				//	End Here
				if(viewObject.getBeneficaryBankName()==null)
				{
					viewObject.setBeneficaryBankName("");
				}

				if(viewObject.getBeneficaryBranchName()==null)
				{
					viewObject.setBeneficaryBranchName("");
				}

				dataTable.setBankPayable(viewObject.getBeneficaryBankName() +"  " + viewObject.getBeneficaryBranchName());

				StringBuffer benedetails = new StringBuffer();

				if (viewObject.getBeneficaryName() != null) {
					benedetails.append(viewObject.getBeneficaryName());
					benedetails.append(" ");
				}
				if (viewObject.getBeneficaryAccountNumber() != null) {
					benedetails.append(viewObject.getBeneficaryAccountNumber());
					benedetails.append(" ");
				}
				if (viewObject.getBeneficaryBankName() != null) {
					benedetails.append(viewObject.getBeneficaryBankName());
					benedetails.append(" ");
				}

				if (viewObject.getBeneficaryBranchName() != null) {
					benedetails.append(viewObject.getBeneficaryBranchName());
					benedetails.append(" ");
				}


				dataTable.setBeneDetails(benedetails.toString());

				dataTable.setCurrencyName(viewObject.getCurrencyQuoteName());
				dataTable.setDocumentFinanceYear(viewObject.getDocumentFinanceYear());
				dataTable.setTransferDate(viewObject.getDocumentDate());
				dataTable.setTransactionNumber(viewObject.getDocumentNumber());
				dataTable.setProduct(viewObject.getTransactionTypeDesc());
				dataTable.setStatus(viewObject.getTransactionStatusDesc());
				if(viewObject.getForeignTransactionAmount()!=null&&viewObject.getCurrencyQuoteName()!=null){
					BigDecimal   trnxAmountWithDecimal= GetRound.roundBigDecimal(viewObject.getForeignTransactionAmount(),foreignLocalCurrencyDenominationService.getDecimalPerCurrencyBasedOnQuoteName(viewObject.getCurrencyQuoteName()));
					dataTable.setTransactionAmount(trnxAmountWithDecimal);
					dataTable.setTransferDate(viewObject.getDocumentDate() );
				}
				dataTable.setCollectionDocumentNo(viewObject.getCollectionDocumentNo() );
				dataTable.setCollectionDocCode( viewObject.getCollectionDocumentCode());
				dataTable.setCollectionDocYear(viewObject.getCollectionDocumentFinYear());
				dataTable.setTransactionTypeDesc(viewObject.getTransactionTypeDesc() );
				//THIS CODE ADDED FOR DECLARATION REPORT PURPOSE
				List<Collect>     listCollect=iPersonalRemittanceService.checkDeclarationIndicatorsInCollectionTable(viewObject.getCollectionDocumentNo() ,viewObject.getCollectionDocumentFinYear());
				if(listCollect.size()>0){
					Collect collect=	listCollect.get( 0);
					if(collect.getCashDeclarationIndicator()!=null&&collect.getCashDeclarationIndicator().equalsIgnoreCase(Constants.Yes)||collect.getTotalAmountDeclarationIndicator()!=null&&collect.getTotalAmountDeclarationIndicator().equalsIgnoreCase(Constants.Yes )){
						dataTable.setDeclarationReport(true);
					}else{
						dataTable.setDeclarationReport( false);
					}

				}

				dataTable.setCorrespondingBankCode(viewObject.getBeneficaryCorespondingBankName());

				customerInquiryDataList.add(dataTable);
				Collections.sort(customerInquiryDataList, new MyDateComp());
			}
		}
	}

	public void removeAttributeFromSession() {
		HttpSession session = sessionStateManage.getSession();
		session.removeAttribute("customerRefNo");
		session.removeAttribute("IdNumber");
		session.removeAttribute("remit");
	}

	public void gotoCustomerEnquiry(CustomerInquiryDataTable customerData) {
		log.info("Entering into gotoCustomerEnquiry method");
		System.out.println("customerDatacustomerDatacustomerDatacustomerData" + customerData);
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		HttpSession session = sessionStateManage.getSession();
		try {
			setBooDialogCall(false);
			session.setAttribute("customerData", customerData);
			context.redirect("../beneficary/customerRemittenceEnquiry.xhtml");
			// personalRemittanceBean.fromAccountExistDialogToBeneficaryTelephone();
		} catch (Exception e) {
			log.info("Problem to redirect:" + e);
		}
		setFirstTime(null);
		removeAttributeFromSession();
		log.info("Exit into gotoCustomerEnquiry method");
	}


	@Autowired
	RemittanceInquiryBean remittanceInquiryBean;

	@Autowired
	IStopPaymentService<T> stopPaymentService;

	@Autowired
	FCSaleEnquiryBean FCSaleInquiry;

	@Autowired
	IForeignCurrencyPurchaseService<T> iForeignCurrencyPurchaseService;

	@Autowired
	ForeignCurrencyPurchaseBeanEnquiry   foreignCurrencyPurchaseEnquiryBean;

	public void gotoCustonmerInquiry(CustomerInquiryDataTable customerData){

		ViewRemiitanceInfo viewRemittanceInfo=null;
		//ViewRemiitanceInfo viewRemittanceInfo=null;
		//viewRemittanceInfo=stopPaymentService.getRemittanceTrnxDetailsFromView(customerData.getTransactionNumber(),customerData.getDocumentFinanceYear(),Constants.DOCUMENT_CODE_FOR_CANCEL_REISSUE,sessionStateManage.getCompanyId());
		//if(viewRemittanceInfo != null){
		//if(!viewRemittanceInfo.getCreatedBy().equalsIgnoreCase(sessionStateManage.getUserName())){
		if( customerData.getTransactionTypeDesc().equalsIgnoreCase("REMITTANCE")||customerData.getTransactionTypeDesc().equalsIgnoreCase("WESTERN UNION")) {
			viewRemittanceInfo=stopPaymentService.getRemittanceTrnxDetailsFromView(customerData.getTransactionNumber(), customerData.getDocumentFinanceYear() , Constants.DOCUMENT_CODE_FOR_CANCEL_REISSUE, sessionStateManage.getCompanyId());
			if(viewRemittanceInfo!=null){
				setBooRenderData(false);
				remittanceInquiryBean.clearAll();
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("../enquiry/remittanceinquiry.xhtml");
				} catch (Exception e) {
					e.printStackTrace();
				}

				remittanceInquiryBean.setRenderPanel(false);
				remittanceInquiryBean.setBackButtonRender(true);
				remittanceInquiryBean.setExitButtonRender(false);
				remittanceInquiryBean.setRenderForRemittanceBranchWiseEnquiry(false);
				remittanceInquiryBean.setRenderForHIGHVALUETrnx(false);
				remittanceInquiryBean.setDocNumber(customerData.getTransactionNumber() );
				log.info("Year Is  ===="+customerData.getDocumentFinanceYear());
				remittanceInquiryBean.setDocYear(customerData.getDocumentFinanceYear() );
				remittanceInquiryBean.fetchData();
			}else{
				setBooRenderData(true);
				RequestContext.getCurrentInstance().execute("noDatafound.show();");
				return;
			}
		}
		else  if(customerData.getTransactionTypeDesc().equalsIgnoreCase("FC SALE")){
			List<ReceiptPaymentView>  receiptDetailList=iForeignCurrencyPurchaseService.getReceiptPaymentView(customerData.getTransactionNumber(), customerData.getDocumentFinanceYear() ,  sessionStateManage.getCompanyId(), new BigDecimal( Constants.DOCUMENT_CODE_FOR_FCSALE) );
			if(receiptDetailList.size()>0){
				setBooRenderData(false);
				FCSaleInquiry.fcsaleEnquiryPageNavigationredirect();
				FCSaleInquiry.setRenderForRemittanceBranchWiseEnquiry(false);
				FCSaleInquiry.setDocumentNo(customerData.getTransactionNumber()  );
				FCSaleInquiry.setDocumentFinancialYear(customerData.getDocumentFinanceYear() );
				FCSaleInquiry.fcsaleEnquiry();
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("../enquiry/remittanceinquiry.xhtml");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				setBooRenderData(true);
				RequestContext.getCurrentInstance().execute("noDatafound.show();");
				return;
			}
		}else if(customerData.getTransactionTypeDesc().equalsIgnoreCase("FC PURCHASE")){
			setBooRenderData(false);
			foreignCurrencyPurchaseEnquiryBean.foreignCurrencyEnquiryNavigationReirect();
			foreignCurrencyPurchaseEnquiryBean.setDocumentNo(customerData.getTransactionNumber());
			foreignCurrencyPurchaseEnquiryBean.setDocumentFinanceYear( customerData.getDocumentFinanceYear());
			foreignCurrencyPurchaseEnquiryBean.enquiry();
			/*try {
				FacesContext.getCurrentInstance().getExternalContext()
				.redirect("../treasury/ForeigncurrencyPurchaseEnquiry.xhtml");
			} catch (Exception e) {
				e.printStackTrace();
			}*/

		}



		/*else  if(customerData.getTransactionTypeDesc().equalsIgnoreCase("FC PURCHASE")){

		foreignCurrencyPurchaseEnquiryBean.fcsaleEnquiryPageNavigationredirect();
		foreignCurrencyPurchaseEnquiryBean.setRenderForRemittanceBranchWiseEnquiry(false);
		foreignCurrencyPurchaseEnquiryBean.setDocumentNo(customerData.getTransactionNumber()  );
		foreignCurrencyPurchaseEnquiryBean.setDocumentFinancialYear(customerData.getDocumentFinanceYear() );
		foreignCurrencyPurchaseEnquiryBean.fcsaleEnquiry();
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../enquiry/remittanceinquiry.xhtml");
			} catch (Exception e) {
				e.printStackTrace();
			}*/


	}





	public String getAmlDate() {
		return amlDate;
	}

	public void setAmlDate(String amlDate) {
		this.amlDate = amlDate;
	}


	public void report(CustomerInquiryDataTable dataTable){
		setBooRenderData(false);
		if(dataTable.getTransactionNumber() != null){
			try{
				if(dataTable.getCorrespondingBankCode() != null && dataTable.getCorrespondingBankCode().equalsIgnoreCase(Constants.WU_BANK_CODE)){
					generateWUSendReport(dataTable.getCollectionDocumentNo(),dataTable.getDocumentFinanceYear(),dataTable.getCollectionDocCode(),sessionStateManage.getCountryId(),sessionStateManage.getCompanyId());
				}else{
					if(dataTable.getTransactionTypeDesc().equalsIgnoreCase("FC PURCHASE")){
						setBooRenderReport(false);
						genarateForeignCurrencyReports(dataTable.getCollectionDocumentNo(),dataTable.getCollectionDocYear());
						log.info("FC PURCHASE : "+dataTable.getCollectionDocumentNo());
					}else{
						log.info("REMITTANCE : "+dataTable.getCollectionDocumentNo());
						setBooRenderReport(false);
						generatePersonalRemittanceReceiptReport(dataTable.getCollectionDocumentNo(),dataTable.getCollectionDocCode(),dataTable.getCollectionDocYear());
					}
				}
			}catch(Exception e){
				log.info(""+e.getMessage());
				setBooDialogCall(true);
				setBooRenderReport( false);
				RequestContext.getCurrentInstance().execute("report.show();");
				return ;
			}
		}
	}

	public void generateWUSendReport(BigDecimal documentno,BigDecimal financeyear, BigDecimal documentcode, BigDecimal applicationCountryId, BigDecimal companyId){
		ServletOutputStream servletOutputStream=null;
		try {

			fetchWUSendReceiptReportData(documentno,financeyear,String.valueOf(documentcode),applicationCountryId,companyId);
			whh2hSendMoneyReceiptReportInit();
			
			String fileName = "WUSendMoneyReprintReceipt.pdf";
			printJasperDirectToPrinter(fileName);

			/*jasperPrint.getPages().remove(1);
			byte[] pdfasbytes = JasperExportManager.exportReportToPdf(jasperPrint);
			HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			httpServletResponse.setHeader("Cache-Control","cache, must-revalidate");
			httpServletResponse.addHeader("Content-disposition","attachment; filename=WUSendMoneyReprintReceipt.pdf");
			servletOutputStream=httpServletResponse.getOutputStream();  
			JasperExportManager.exportReportToPdfStream(jasperPrint,servletOutputStream);
			FacesContext.getCurrentInstance().responseComplete();*/

		}catch (NullPointerException ne) {
			log.info("Exception in generateWUSendReport:" + ne.getMessage());
			setErrmsg("Method Name::generateWUSendReport");
			RequestContext.getCurrentInstance().execute("report.show();");
		} catch (Exception exception) {
			log.info("Exception in generateWUSendReport:" + exception.getMessage());
			setErrmsg(exception.getMessage());
			RequestContext.getCurrentInstance().execute("report.show();");
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
	
	public void whh2hSendMoneyReceiptReportInit() throws JRException {

		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(wuRemittanceReceiptSubreportList);
		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath = ctx.getRealPath("//");
		String reportPath = realPath +"//reports//design//wusendmainreport.jasper";
		System.out.println(reportPath);
		jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap(),beanCollectionDataSource);
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

		String currencyQuoteName = igeneralService.getCurrencyQuote(new BigDecimal(sessionStateManage.getCurrencyId()));
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
				if(view.getLastName()!=null){
					customerName.append(" ");
					customerName.append(view.getLastName());
					customerReff.append(" ");
					customerReff.append(view.getLastName());
				}
				obj.setFirstName(customerReff.toString());				
				
				obj.setSenderPointsEarned(String.valueOf(view.getWuTotalPointsEarned()==null ? new Long(0)  : view.getWuTotalPointsEarned()));
				obj.setSenderNewPointsEarned(String.valueOf(view.getWuNewPointsEarned()==null ? new Long(0)  : view.getWuNewPointsEarned()));
				
				//setCustomerNameForReport(customerName.toString());

				if(view.getContactNumber()!=null && !view.getContactNumber().trim().equalsIgnoreCase("")){
					obj.setMobileNo(new BigDecimal(view.getContactNumber().trim()));
				}

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
				
				

				/*if (view.getIdProofTypeId().compareTo(new BigDecimal("198")) == 0 || view.getIdProofTypeId().compareTo(new BigDecimal("2000")) == 0) {					
					obj.setIdType("CIVIL ID");
				} else if (view.getIdProofTypeId().compareTo(new BigDecimal("204")) == 0) {
					obj.setIdType("PASSPORT");
				}*/
				
				// civil id not available
				List<CustomerIdproofView> lstcustProof = igeneralService.getCustomerIdProofDetailsFromView(view.getCustomerId());
				if(lstcustProof != null && lstcustProof.size() != 0){
					CustomerIdproofView custDet = lstcustProof.get(0);
					if(custDet != null){
						if(custDet.getIdProofTypeDesc() != null){
							obj.setIdType(custDet.getIdProofTypeDesc());
						}
						if(custDet.getIdProofInt() != null){
							obj.setCivilId(custDet.getIdProofInt());
						}
					}
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
						if(cust.getIdType() != null && cust.getIdType().compareTo(new BigDecimal(Constants.CORPORATE_ID_TYPE))==0){
							obj.setLoyalityPointExpiring("");
						}
					}
				}
				
				//Terms & conditions
				
				List<WUReportTermsAndConditions> engtcList = new ArrayList<WUReportTermsAndConditions>();
				List<WURemittanceReportBean> engtcList1 = new ArrayList<WURemittanceReportBean>();
				
				engtcList = iwuh2hService.getWUReportTermsAndConditions();
				for(WUReportTermsAndConditions wutc:engtcList){
					WURemittanceReportBean wubean = new WURemittanceReportBean();
					
					String replace1 =  wutc.getEnglishMessage().replace("XXXTRD", "�");
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
			setErrmsg(currencyQuoteName);
			RequestContext.getCurrentInstance().execute("error.show();");
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

	private Boolean booRenderReport=false;

	public Boolean getBooRenderReport() {
		return booRenderReport;
	}

	public void setBooRenderReport(Boolean booRenderReport) {
		this.booRenderReport = booRenderReport;
	}

	/////////////////////////////////////////////////// REPORT CODE  ///////////////////////////////////////////////
	//Jasper Report generation
	private JasperPrint jasperPrint;
	@Autowired
	ISpecialCustomerDealRequestService<T> specialCustomerDealRequestService;
	@Autowired
	ForeignLocalCurrencyDenominationService<T> foreignLocalCurrencyDenominationService;

	/*@Autowired
	IPersonalRemittanceService iPersonalRemittanceService;*/
	private List<RemittanceReceiptSubreport> remittanceReceiptSubreportList;
	private List<CollectionDetailView> collectionViewList = new CopyOnWriteArrayList<CollectionDetailView>();
	public void generatePersonalRemittanceReceiptReport( BigDecimal documentNo,BigDecimal collectionDocCode,BigDecimal collectionYear ) throws Exception{
		ServletOutputStream servletOutputStream=null;
		try {
			fetchRemittanceReceiptReportData(documentNo,collectionDocCode,collectionYear);
			remittanceReceiptReportInit();

			String fileName = "RemittanceReceiptReport.pdf";
			printJasperDirectToPrinter(fileName);

			/*HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			httpServletResponse.setHeader("Cache-Control","cache, must-revalidate");
			httpServletResponse.addHeader("Content-disposition","attachment; filename=RemittanceReceiptReport.pdf");
			servletOutputStream = httpServletResponse.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint,servletOutputStream);
			FacesContext.getCurrentInstance().responseComplete();*/

		} catch (Exception e) {
			log.info("EXCEPTION OCCURED WHILE REPORT GENERATION :"+e.getMessage());
			setErrmsg("Error While Report Generating1:"+e.getMessage());
			throw e;
		}finally{
			if(servletOutputStream!=null){
				servletOutputStream.close();
			}
		}

	}

	public void remittanceReceiptReportInit() throws JRException {
		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath = ctx.getRealPath("//");

		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(remittanceReceiptSubreportList);
		//String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("reports/design/RemittanceReceiptNewReport.jasper");
		String reportPath = realPath + "//reports//design//RemittanceReceiptNewReport.jasper";
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

	private void fetchRemittanceReceiptReportData(BigDecimal documentNumber,BigDecimal docCode,BigDecimal docYear) throws Exception{
		log.info("Document Number=="+documentNumber);
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

		String currencyQuoteName = igeneralService.getCurrencyQuote(new BigDecimal(sessionStateManage.getCurrencyId()));


		List<Employee> employeeList = iPersonalRemittanceService.getEmployeeDetails(sessionStateManage.getUserName());

		List<RemittanceApplicationView> remittanceViewlist = iPersonalRemittanceService.getRecordsForRemittanceReceiptReport(documentNumber, docYear, docCode.toPlainString());
		log.info("Remittance View List Size is======"+remittanceViewlist.size());
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

				List<CutomerDetailsView> customerListR = iPersonalRemittanceService.getCustomerDetails(view.getCustomerId());

				if (customerListR != null && customerListR.size() > 0) {
					CutomerDetailsView cust = customerListR.get(0);
					//obj.setFirstName(cust.getCustomerName());
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
	}
				 */

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
					log.info( "Exception Occured While Report2 "+e.getMessage());
					setErrmsg("Exception Occured While Report "+e.getMessage() );

				}

				if(employeeList!=null && employeeList.size()>0){
					try {
						Employee employee = employeeList.get(0);
						if (employee.getSignatureSpecimenClob() != null) {
							obj.setCashierSignature(employee.getSignatureSpecimenClob().getSubString(1,(int) employee.getSignatureSpecimenClob().length()));
						}
					} catch (SQLException e) {
						log.info( "Exception Occured While Report3 "+e.getMessage());
						setErrmsg("Exception Occured While Report "+e.getMessage() );
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
						arabicCompanyInfo=arabicCompanyInfo.append(Constants.CR +companyMaster.get(0).getRegistrationNumber()+",");
					}						
					if(companyMaster.get(0).getCapitalAmount()!=null&&companyMaster.get(0).getCapitalAmount().length()>0){
						arabicCompanyInfo=arabicCompanyInfo.append(Constants.Share_Capital +companyMaster.get(0).getCapitalAmount());
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
						System.out.println("cust.getIdType() :"+cust.getIdType()+"\t Constant :"+Constants.CORPORATE_ID_TYPE);
						if(cust.getIdType() != null && cust.getIdType().compareTo(new BigDecimal(Constants.CORPORATE_ID_TYPE))==0){
							obj.setLoyalityPointExpiring("");
						}
					}
				}

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
						arabicCompanyInfo=arabicCompanyInfo.append(Constants.CR +companyMaster.get(0).getRegistrationNumber()+",");
					}					
					if(companyMaster.get(0).getCapitalAmount()!=null&&companyMaster.get(0).getCapitalAmount().length()>0){
						arabicCompanyInfo=arabicCompanyInfo.append(Constants.Share_Capital+companyMaster.get(0).getCapitalAmount());
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

				if(view.getIdentityInt() != null && view.getIdentityExpiryDate() != null){
					obj.setCivilId(view.getIdentityInt());
					Date sysdate = view.getIdentityExpiryDate();
					if(sysdate != null){
						obj.setIdExpiryDate(new SimpleDateFormat("dd/MM/yyy").format(sysdate));
					}
				}else{
					// civil id not available then it is crno
					List<CustomerIdproofView> lstcustProof = igeneralService.getCustomerIdProofDetailsFromView(view.getCustomerId());
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
					//obj.setFirstName(cust.getCustomerName());
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
				//obj.setUserName(sessionStateManage.getUserName());
				obj.setUserName(view.getCreatedBy());

				if(view.getDocumentFinancialYear()!=null && view.getCollectionDocumentNo()!=null){
					obj.setReceiptNo(view.getDocumentFinancialYear()+" / "+view.getCollectionDocumentNo());
				}else if(view.getCollectionDocumentNo()!=null){
					obj.setReceiptNo(view.getCollectionDocumentNo().toString());
				}
				String saleCurrency = specialCustomerDealRequestService.getCurrencyForUpdate(view.getForeignCurrencyId());


				obj.setCurrencyQuoteName(saleCurrency);

				String saleCurrencyCode = igeneralService.getCurrencyQuote(view.getForeignCurrencyId());

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
					log.info( "Exception Occured While Report4 "+e.getMessage());
					setErrmsg("Exception Occured While Report "+e.getMessage() );
				}

				if(employeeList!=null && employeeList.size()>0){
					try {
						Employee employee = employeeList.get(0);
						if (employee.getSignatureSpecimenClob() != null) {
							obj.setCashierSignature(employee.getSignatureSpecimenClob().getSubString(1,(int) employee.getSignatureSpecimenClob().length()));
						}
					} catch (SQLException e) {
						log.info( "Exception Occured While Report 5"+e.getMessage());
						setErrmsg("Exception Occured While Report "+e.getMessage() );
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
			} /*else {
	remittanceObj.setFcsaleApplicationCheck(false);
	}*/
			if(remittanceApplList!=null && remittanceApplList.size()>0){
				remittanceObj.setRemittanceReceiptCheck(true);
			}/*else{
	remittanceObj.setRemittanceReceiptCheck(false);
	}*/

			remittanceReceiptSubreportList.add(remittanceObj);

		} else {
			RequestContext.getCurrentInstance().execute("noDataForReport.show();");
			return;
		}


	}

	public Boolean getBooDialogCall() {
		return booDialogCall;
	}

	public void setBooDialogCall(Boolean booDialogCall) {
		this.booDialogCall = booDialogCall;
	}

	// ############## Report Genaration By RAHAMATHALI SHAIK ##############
	//@Autowired
	//IForeignCurrencyPurchaseService<T> foreignCurrencyPurchaseService;
	private String errorMsg;

	//private JasperPrint jasperPrint1;
	private List<ForeignCurrencyPurchageReport> foreignCurrencyPurchageList = new CopyOnWriteArrayList<ForeignCurrencyPurchageReport>();

	public void init() throws JRException {
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(foreignCurrencyPurchageList);
		String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("reports/design/fc_salereport.jasper");
		jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap(), beanCollectionDataSource);
	}

	public void genarateForeignCurrencyReports(BigDecimal  documentNo, BigDecimal DocYear) throws JRException, IOException {
		ServletOutputStream servletOutputStream=null;
		try{
			fetchForeignCurrencyPurchageDetails(documentNo, DocYear);
			init();

			String fileName = "ForeignCurrencyPurchageReport.pdf";
			printJasperDirectToPrinter(fileName);

			/*HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			httpServletResponse.addHeader("Content-disposition", "attachment; filename=ForeignCurrencyPurchageReport.pdf");
			httpServletResponse.setContentType("application/pdf");
			servletOutputStream = httpServletResponse.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint1, servletOutputStream);
			FacesContext.getCurrentInstance().responseComplete();*/

		}catch(Exception e){
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("err.show();");
			return;
		}finally{
			if(servletOutputStream!=null){
				servletOutputStream.close();
			}
		}

	}



	/*public void fetchForeignCurrencyPurchageDetails(BigDecimal documentNo, BigDecimal DocYear) {
				foreignCurrencyPurchageList.clear();

				List<ForeignCurrencyPurchaseReport> objList = new ArrayList<ForeignCurrencyPurchaseReport>();

				if(documentNo != null){

					objList = foreignCurrencyPurchaseService.getFcPurchaseReportList(sessionStateManage.getCountryId(), sessionStateManage.getCompanyId(),new BigDecimal(Constants.DOCUMENT_CODE_FOR_FCPURCHAGE) ,DocYear , documentNo);
				}

				ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
				String realPath = ctx.getRealPath("//");
				String logoPath = realPath + Constants.LOGO_PATH;

				for (ForeignCurrencyPurchaseReport foreignCurrencyPurchaseReport : objList) {

					ForeignCurrencyPurchageReport fCPurchageReport = new ForeignCurrencyPurchageReport();

					fCPurchageReport.setCustomerId(foreignCurrencyPurchaseReport.getCustomerId());
					fCPurchageReport.setCustomerName(foreignCurrencyPurchaseReport.getFirstName());
					if(foreignCurrencyPurchaseReport.getMobileNo()!=null){
						fCPurchageReport.setContactNo(new BigDecimal(foreignCurrencyPurchaseReport.getMobileNo()));
					}
					fCPurchageReport.setIdExpiryDate(foreignCurrencyPurchaseReport.getIdentityExpiryDate());
					if(foreignCurrencyPurchaseReport.getIdentityInt()!=null){
						fCPurchageReport.setCivilIdNo(new BigDecimal(foreignCurrencyPurchaseReport.getIdentityInt()));
					}
					fCPurchageReport.setDate(foreignCurrencyPurchaseReport.getCollectionDate());
					fCPurchageReport.setForeignCurrency(foreignCurrencyPurchaseReport.getQuoteName());
					fCPurchageReport.setSourceOfIncome(foreignCurrencyPurchaseReport.getSourceDesc());
					fCPurchageReport.setPurpose(foreignCurrencyPurchaseReport.getPurposeDesc());
					fCPurchageReport.setNetAmount(foreignCurrencyPurchaseReport.getNetAmount());
					fCPurchageReport.setRefundAmount(foreignCurrencyPurchaseReport.getRefundAmount());
					if(foreignCurrencyPurchaseReport.getTelephoneNo() != null){
						fCPurchageReport.setMobileNo(new BigDecimal(foreignCurrencyPurchaseReport.getTelephoneNo()));
					}
					fCPurchageReport.setEmployeeName(foreignCurrencyPurchaseReport.getEmployeeName());
					fCPurchageReport.setLocation(foreignCurrencyPurchaseReport.getLocation());
					fCPurchageReport.setCustomerRefNo(foreignCurrencyPurchaseReport.getCustomerReference());
					fCPurchageReport.setAmountPaid(foreignCurrencyPurchaseReport.getPaidAmount());
					fCPurchageReport.setPaymentMode(foreignCurrencyPurchaseReport.getPaymentMode());
					fCPurchageReport.setSaleAmount(foreignCurrencyPurchaseReport.getSaleAmount());
					fCPurchageReport.setPurchageRate(foreignCurrencyPurchaseReport.getForeignTranxAmount());
					// fCPurchageReport.setExchangeRate(foreignCurrencyPurchaseReport.getExchangeRate());
					fCPurchageReport.setExchangeRate(foreignCurrencyPurchaseReport.getTranxActualRate());
					fCPurchageReport.setReceiptNo(foreignCurrencyPurchaseReport.getDocumentNo());
					fCPurchageReport.setDocFynYr(foreignCurrencyPurchaseReport.getDocFynYear());
					fCPurchageReport.setInsurence("");
					// fCPurchageReport.setCommision(new BigDecimal(""));
					// fCPurchageReport.setNetPaidAmount(null);
					fCPurchageReport.setLogoPath(logoPath);

					foreignCurrencyPurchageList.add(fCPurchageReport);

				}

			}*/
	public void fetchForeignCurrencyPurchageDetails(BigDecimal documentNo, BigDecimal DocYear) {
		foreignCurrencyPurchageList.clear();

		List<ForeignCurrencyPurchaseReport> objList = new ArrayList<ForeignCurrencyPurchaseReport>();

		if(documentNo != null){
			objList = foreignCurrencyPurchaseService.getFcPurchaseReportList(sessionStateManage.getCountryId(), sessionStateManage.getCompanyId(),new BigDecimal(Constants.DOCUMENT_CODE_FOR_FCPURCHAGE) , DocYear, documentNo);
		}


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

		for (ForeignCurrencyPurchaseReport foreignCurrencyPurchaseReport : objList) {

			ForeignCurrencyPurchageReport fCPurchageReport = new ForeignCurrencyPurchageReport();

			fCPurchageReport.setCustomerId(foreignCurrencyPurchaseReport.getCustomerId());

			String customerName = igeneralService.getCustomerNameCustomerId(foreignCurrencyPurchaseReport.getCustomerId());
			if(customerName != null){
				fCPurchageReport.setCustomerName(customerName);
			}

			if(foreignCurrencyPurchaseReport.getMobileNo() != null && !foreignCurrencyPurchaseReport.getMobileNo().trim().equalsIgnoreCase("")){
				fCPurchageReport.setContactNo(new BigDecimal(foreignCurrencyPurchaseReport.getMobileNo()));
			}

			if(foreignCurrencyPurchaseReport.getIdentityInt()!=null && foreignCurrencyPurchaseReport.getIdentityExpiryDate() != null){
				fCPurchageReport.setCivilIdNo(foreignCurrencyPurchaseReport.getIdentityInt());
				fCPurchageReport.setIdExpiryDate(foreignCurrencyPurchaseReport.getIdentityExpiryDate());
			}else{
				// civil id not available then it is crno
				List<CustomerIdproofView> lstcustProof = igeneralService.getCustomerIdProofDetailsFromView(foreignCurrencyPurchaseReport.getCustomerId());
				if(lstcustProof != null && lstcustProof.size() != 0){
					CustomerIdproofView custDet = lstcustProof.get(0);

					if(custDet != null){
						if(custDet.getIdProofInt() != null){
							fCPurchageReport.setCivilIdNo(custDet.getIdProofInt());
						}

						if(custDet.getIdProofExpiredDate() != null){
							fCPurchageReport.setIdExpiryDate(custDet.getIdProofExpiredDate());
						}

					}

				}
			}

			fCPurchageReport.setDate(foreignCurrencyPurchaseReport.getCollectionDate());
			fCPurchageReport.setForeignCurrency(foreignCurrencyPurchaseReport.getQuoteName());
			fCPurchageReport.setSourceOfIncome(foreignCurrencyPurchaseReport.getSourceDesc());
			fCPurchageReport.setPurpose(foreignCurrencyPurchaseReport.getPurposeDesc());
			fCPurchageReport.setNetAmount(GetRound.roundBigDecimal(foreignCurrencyPurchaseReport.getNetAmount(), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(foreignCurrencyPurchaseReport.getCurrencyId())));//GetRound.roundBigDecimal(foreignCurrencyPurchaseReport.getNetAmount(), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId())))
			fCPurchageReport.setRefundAmount(GetRound.roundBigDecimal(foreignCurrencyPurchaseReport.getRefundAmount(), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(foreignCurrencyPurchaseReport.getCurrencyId())));
			fCPurchageReport.setCommision(GetRound.roundBigDecimal(new BigDecimal(0) , foreignLocalCurrencyDenominationService.getDecimalPerCurrency(foreignCurrencyPurchaseReport.getCurrencyId())) );
			if(foreignCurrencyPurchaseReport.getTelephoneNo() != null){
				fCPurchageReport.setMobileNo(new BigDecimal(foreignCurrencyPurchaseReport.getTelephoneNo()));
			}
			fCPurchageReport.setEmployeeName(foreignCurrencyPurchaseReport.getEmployeeName());
			fCPurchageReport.setLocation(foreignCurrencyPurchaseReport.getLocation());
			fCPurchageReport.setCustomerRefNo(foreignCurrencyPurchaseReport.getCustomerReference());
			fCPurchageReport.setAmountPaid(GetRound.roundBigDecimal(foreignCurrencyPurchaseReport.getPaidAmount(), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(foreignCurrencyPurchaseReport.getCurrencyId())));
			fCPurchageReport.setPaymentMode(foreignCurrencyPurchaseReport.getPaymentMode());
			fCPurchageReport.setSaleAmount(GetRound.roundBigDecimal(foreignCurrencyPurchaseReport.getSaleAmount(), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
			fCPurchageReport.setPurchageRate(GetRound.roundBigDecimal(foreignCurrencyPurchaseReport.getForeignTranxAmount(), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(foreignCurrencyPurchaseReport.getCurrencyId())));
			// fCPurchageReport.setExchangeRate(foreignCurrencyPurchaseReport.getExchangeRate());
			fCPurchageReport.setExchangeRate(GetRound.roundBigDecimal(foreignCurrencyPurchaseReport.getTranxActualRate(), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
			fCPurchageReport.setReceiptNo(foreignCurrencyPurchaseReport.getDocumentNo());
			fCPurchageReport.setDocFynYr(foreignCurrencyPurchaseReport.getDocFynYear());
			fCPurchageReport.setLocCurrency(igeneralService.getCurrencyQuote(new BigDecimal(sessionStateManage.getCurrencyId() )));
			fCPurchageReport.setBothCurrency(foreignCurrencyPurchaseReport.getQuoteName()+"/"+igeneralService.getCurrencyQuote(new BigDecimal(sessionStateManage.getCurrencyId() )));


			try {
				if(foreignCurrencyPurchaseReport.getEmployeeSignature()!=null){
					fCPurchageReport.setCashierSignature(foreignCurrencyPurchaseReport.getEmployeeSignature().getSubString(1,(int) foreignCurrencyPurchaseReport.getEmployeeSignature().length())) ;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				if(foreignCurrencyPurchaseReport.getSignatureSpecimenClob()!=null){
					fCPurchageReport.setSignature(foreignCurrencyPurchaseReport.getSignatureSpecimenClob().getSubString(1,(int)foreignCurrencyPurchaseReport.getSignatureSpecimenClob().length()) );
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//GetRound.roundBigDecimal((amountPayable),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));


			/*HashMap<String, String> loyaltiPoints  =iPersonalRemittanceService.getloyalityPointsFromProcedure(foreignCurrencyPurchaseReport.getCustomerReference(), foreignCurrencyPurchaseReport.getDocumentDate());

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
			fCPurchageReport.setLoyaltyPointsExp(loyaltyPoint.toString() );
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
			fCPurchageReport.setInsurence(insurence.toString());*/

			HashMap<String, String> loyaltiPoints  =iPersonalRemittanceService.getloyalityPointsFromProcedure(foreignCurrencyPurchaseReport.getCustomerReference(), foreignCurrencyPurchaseReport.getDocumentDate());

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
			
			//Remove Loyalty Point message for Corporate Customer
			
			List<CutomerDetailsView> customerList = iPersonalRemittanceService.getCustomerDetails(fCPurchageReport.getCustomerId());
			if (customerList != null && customerList.size() > 0) {
				CutomerDetailsView cust = customerList.get(0);
				
				if(cust.getIdType().compareTo(new BigDecimal(Constants.CORPORATE_ID_TYPE))==0){
					fCPurchageReport.setLoyaltyPointsExp("");
				}else{
					fCPurchageReport.setLoyaltyPointsExp(loyaltyPoint.toString());
				}
			}
			
			
			//Commened by Rabil on 22 Aug 2017
			//fCPurchageReport.setLoyaltyPointsExp(loyaltyPoint.toString());

			StringBuffer insurence1 = new StringBuffer();
			if(!prInsStr1.trim().equals("")){
				insurence1.append(prInsStr1);
			}
			if(!prInsStrAr1.trim().equals("")){
				insurence1.append("\n");
				insurence1.append(prInsStrAr1);
			}
			fCPurchageReport.setInsurence1(insurence1.toString());

			StringBuffer insurence2 = new StringBuffer();
			if(!prInsStr2.trim().equals("")){
				insurence2.append(prInsStr2);
			}
			if(!prInsStrAr2.trim().equals("")){
				insurence2.append("\n");
				insurence2.append(prInsStrAr2);
			}
			fCPurchageReport.setInsurence2(insurence2.toString());



			//sessionStateManage
			//fCPurchageReport.setInsurence("");
			// fCPurchageReport.setCommision(new BigDecimal(""));
			// fCPurchageReport.setNetPaidAmount(null);
			fCPurchageReport.setLogoPath(logoPath);

			foreignCurrencyPurchageList.add(fCPurchageReport);

		}

	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Boolean getBooRenderData() {
		return booRenderData;
	}

	public void setBooRenderData(Boolean booRenderData) {
		this.booRenderData = booRenderData;
	}

	/*      DECLERATION REPORT      STARTED HERE         */

	/*      DECLERATION REPORT               */
	private RemittanceDeclarationMainReportBean remitDeclarionMainReport=new RemittanceDeclarationMainReportBean();
	private String reportFileName;
	private boolean booDeclarationReportDisplay;
	private Boolean visableExceptionDailogForReceipt=false;
	private Boolean visableExceptionDailogForApplication=false;
	private CopyOnWriteArrayList<PersonalRemittanceCollectionDataTable> coldatatablevaluesListForReport = new CopyOnWriteArrayList<PersonalRemittanceCollectionDataTable>();

	@Autowired
	ICustomerRegistrationBranchService<T> icustomerRegistrationService;

	@Autowired
	ICashTransferLToLService cashTransferLToLService;

	public CopyOnWriteArrayList<PersonalRemittanceCollectionDataTable> getColdatatablevaluesListForReport() {
		return coldatatablevaluesListForReport;
	}
	public void setColdatatablevaluesListForReport(
			CopyOnWriteArrayList<PersonalRemittanceCollectionDataTable> coldatatablevaluesListForReport) {
		this.coldatatablevaluesListForReport = coldatatablevaluesListForReport;
	}
	public Boolean getVisableExceptionDailogForApplication() {
		return visableExceptionDailogForApplication;
	}
	public void setVisableExceptionDailogForApplication(
			Boolean visableExceptionDailogForApplication) {
		this.visableExceptionDailogForApplication = visableExceptionDailogForApplication;
	}
	public Boolean getVisableExceptionDailogForReceipt() {
		return visableExceptionDailogForReceipt;
	}
	public void setVisableExceptionDailogForReceipt(
			Boolean visableExceptionDailogForReceipt) {
		this.visableExceptionDailogForReceipt = visableExceptionDailogForReceipt;
	}

	private List<CustomerDeclerationView> lstDeclarationView = new ArrayList<CustomerDeclerationView>();

	private List<DeclerationReportBean> lstDispDeclarationView = new ArrayList<DeclerationReportBean>();


/*	
 * Old method
 * public void generateDeclerationReports(BigDecimal  collectiionDocNo,BigDecimal  CollectionDocYear, Date transactionDate) throws IOException{
		setVisableExceptionDailogForReceipt(false);
		setExceptionMessageForReport(null);
		List<RemittanceDeclarationMainReportBean> remitDeclartionMainReportList = new CopyOnWriteArrayList<RemittanceDeclarationMainReportBean>();
		OutputStream outstream=null;
		try{
			lstDeclarationView.clear();
			lstDispDeclarationView.clear();

			fetchDeclatationReports(collectiionDocNo,CollectionDocYear,transactionDate);
			remitDeclartionMainReportList.add(remitDeclarionMainReport);
			initDeclerationReports(remitDeclartionMainReportList);

			String fileName = "DeclarationReport.pdf";
			printJasperDirectToPrinter(fileName);

			

		}catch(Exception e){
			setExceptionMessageForReport(null);
			setVisableExceptionDailogForReceipt(true);
			if(e.getMessage()!=null){
				setExceptionMessageForReport(e.getMessage());	
			}else{
				setExceptionMessageForReport("Exception  :"+e);
			}
			RequestContext.getCurrentInstance().execute("exceptioninReceipt.show();");
		}finally{
			if(outstream!=null){
				outstream.close();
			}
		}
	}*/
	
	/** New Declaration method */
	
	public void generateDeclerationReports(BigDecimal  collectiionDocNo,BigDecimal  CollectionDocYear, Date transactionDate, BigDecimal customerReference) throws IOException{
		ServletOutputStream servletOutputStream = null;
		setVisableExceptionDailogForReceipt(false);
		setExceptionMessageForReport(null);
		List<RemittanceDeclarationMainReportBean> remitDeclartionMainReportList = new CopyOnWriteArrayList<RemittanceDeclarationMainReportBean>();
		OutputStream outstream=null;
		try{
			lstDeclarationView.clear();
			lstDispDeclarationView.clear();

			fetchDeclatationReports(collectiionDocNo,CollectionDocYear,transactionDate,customerReference);
			remitDeclartionMainReportList.add(remitDeclarionMainReport);
			initDeclerationReports(remitDeclartionMainReportList);
			
			String fileName = "DeclarationReport.pdf";
			printJasperDirectToPrinter(fileName);
			
			

		}catch(Exception e){
			setExceptionMessageForReport(null);
			setVisableExceptionDailogForReceipt(true);
			if(e.getMessage()!=null){
				setExceptionMessageForReport(e.getMessage());	
			}else{
				setExceptionMessageForReport("Exception  :"+e);
			}
			RequestContext.getCurrentInstance().execute("exceptioninReceipt.show();");
		}finally{
			if(outstream!=null){
				outstream.close();
			}
		}
	}

	public void printJasperDirectToPrinter(String fileName){
		try{
			JRExporter exporter = null;
			ByteArrayOutputStream out = null;
			ByteArrayInputStream input = null;
			BufferedOutputStream output = null; 

			FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = facesContext.getExternalContext();
			HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

			facesContext = FacesContext.getCurrentInstance();
			externalContext = facesContext.getExternalContext();
			response = (HttpServletResponse) externalContext.getResponse();

			out = new ByteArrayOutputStream(); 

			exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRPdfExporterParameter.PDF_JAVASCRIPT, "this.print();");
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);

			exporter.exportReport();

			input = new ByteArrayInputStream(out.toByteArray());

			response.reset();
			response.setHeader("Content-Type", "application/pdf");
			response.setHeader("Content-Length", String.valueOf(out.toByteArray().length));
			response.setHeader("Content-Disposition", "inline; filename="+fileName);
			output = new BufferedOutputStream(response.getOutputStream(), org.apache.jasper.Constants.DEFAULT_BUFFER_SIZE);
			byte[] buffer = new byte[org.apache.jasper.Constants.DEFAULT_BUFFER_SIZE];
			int length;
			while ((length = input.read(buffer)) > 0) {
				output.write(buffer, 0, length);
			}
			output.flush();
			facesContext.responseComplete();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

/*	public void initDeclerationReports(List<RemittanceDeclarationMainReportBean> remitDeclartionMainReportList) throws JRException {
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(remitDeclartionMainReportList);
		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath = ctx.getRealPath("//");
		
		String reportPath;
		if (sessionStateManage.getLanguageId().equals(new BigDecimal("1"))) {
			reportPath = realPath +"//reports//design//RemittanceDeclarationMainReport.jasper";
		}else{
			reportPath = realPath +"//reports//design//RemittanceDeclationArabicReport.jasper";
		}
		jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap(),beanCollectionDataSource);
	}*/
	
	/** NEw declaration Method */
	public void initDeclerationReports(List<RemittanceDeclarationMainReportBean> remitDeclartionMainReportList) throws JRException {
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(remitDeclartionMainReportList);
		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath = ctx.getRealPath("//");
		String reportPath;		
		reportPath = realPath +"//reports//design//declarationmainrpt.jasper";
		jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap(),beanCollectionDataSource);
	}



	/*public void fetchDeclatationReports(BigDecimal  collectionDocNo,BigDecimal  collectionDocYear,Date transactionDate)throws Exception {

		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath = ctx.getRealPath("//");

		String logoPath =null;
		if(sessionStateManage.getCountryName().equalsIgnoreCase(Constants.KUWAIT)){
			logoPath = realPath+Constants.LOGO_PATH;
		}else if(sessionStateManage.getCountryName().equalsIgnoreCase(Constants.OMAN)){
			logoPath = realPath+Constants.LOGO_PATH_OM;
		}else{
			logoPath =realPath+Constants.LOGO_PATH;
		}



		String subReportPath = realPath + Constants.SUB_REPORT_PATH;
		String waterMark = realPath+Constants.REPORT_WATERMARK_LOGO;
		remitDeclarionMainReport.setWaterMarkCheck(true);
		remitDeclarionMainReport.setWaterMarkLogoPath(waterMark);
		remitDeclarionMainReport.setSubReport(subReportPath);

		lstDeclarationView = iPersonalRemittanceService.getCustomerDeclerationData(sessionStateManage.getCountryId(), collectionDocYear, new BigDecimal(Constants.DOCUMENT_CODE_FOR_COLLECT_TRANSACTION),  collectionDocNo);

		String paymentMode = "C";


		if (lstDeclarationView !=null && lstDeclarationView.size() > 0) {

			String reportType = iPersonalRemittanceService.declartionLimitCheck(sessionStateManage.getCountryId(), sessionStateManage.getCompanyId(), new BigDecimal(getCustomerId()),transactionDate, paymentMode);
			System.out.println("========2500report true"+ reportType);
			if (reportType.trim()!="") {
				if (reportType.equalsIgnoreCase("Y")) {
					remitDeclarionMainReport.setDeclaration2500Check(true);
					System.out.println("========2500report true"+transactionDate);
				}
			}
			String reportType1 = iPersonalRemittanceService.declartionLimitCheck(sessionStateManage.getCountryId(), sessionStateManage.getCompanyId(), new BigDecimal(getCustomerId()), transactionDate,"");
			System.out.println("========10000report true"+ reportType1);
			if (reportType.trim()!="") {
				if (reportType1.equalsIgnoreCase("Y")) {
					remitDeclarionMainReport.setDeclaration10000Check(true);
					System.out.println("========10000report true"+transactionDate);
				}

			}



		}
		if (lstDeclarationView !=null && lstDeclarationView.size() > 0) {
			for (CustomerDeclerationView customerDeclerationView : lstDeclarationView) {

				DeclerationReportBean declerationReportBean = new DeclerationReportBean();
				//set branch name
				String branchName =new String();
				String companyName =new String();
				StringBuffer toAddress =new StringBuffer();
				StringBuffer customerName=new StringBuffer();
				String countryName =new String();

				List<CountryBranch> branchList = cashTransferLToLService.getBranchName(new BigDecimal(sessionStateManage.getBranchId()));
				if(branchList.size()>0){
					branchName = branchList.get(0).getBranchName();
				}

				List<CompanyMasterDesc> companyDesc =	igeneralService.getCompanyList(sessionStateManage.getCompanyId(), sessionStateManage.getLanguageId());
				countryName =  igeneralService.getCountryName(sessionStateManage.getCountryId());
				CompanyMasterDesc companyDesObj = companyDesc.get(0);

				//set company address
				if(companyDesObj!=null){
					companyName	 = companyDesObj.getCompanyName();
				}

				// to set to address
				toAddress.append("THE BRANCH MANAGER");
				toAddress.append("\n");
				toAddress.append(branchName==null?"":branchName.toUpperCase());
				toAddress.append("\n");
				toAddress.append(companyName==null?"":companyName.toUpperCase());
				toAddress.append("\n");
				toAddress.append(countryName==null?"":countryName.toUpperCase());

				// tp set Customer Name
				customerName.append(customerDeclerationView.getFirstName()==null?"":customerDeclerationView.getFirstName());
				customerName.append(" ");
				customerName.append(customerDeclerationView.getMiddleName()==null?"":customerDeclerationView.getMiddleName());
				customerName.append(" ");
				customerName.append(customerDeclerationView.getLastName()==null?"":customerDeclerationView.getLastName());

				//to set Employee ment details
				StringBuffer employer = new StringBuffer();
				List<CustomerEmployeeInfoView> customerEmployeeInfo = icustomerRegistrationService.findCustomerEmployeeInfo(customerDeclerationView.getCustomerId());
				if(customerEmployeeInfo.size()>0){
					CustomerEmployeeInfoView customerEmployee = customerEmployeeInfo.get(0);
					employer.append(customerEmployee.getEmployeeName()==null?"":customerEmployee.getEmployeeName());
					employer.append(" ");
					employer.append(customerEmployee.getEmployeeProofDesc()==null?"":customerEmployee.getEmployeeProofDesc());
				}

				// to set Purpose of transcation
				String purpose ="";
				List<PurposeOfRemittanceView>  purposeOfRemittanceList =   iPersonalRemittanceService.getPurposeOfRemittance(customerDeclerationView.getDocumentNo(),customerDeclerationView.getDocumentFinanceYear());
				for(PurposeOfRemittanceView purposeObj :purposeOfRemittanceList){
					if(purpose.equals("")){
						purpose  = purposeObj.getFlexiFieldValue();
					}else{
						purpose  = purpose+", "+purposeObj.getFlexiFieldValue();
					}
				}
				if(purpose != null && !purpose.equalsIgnoreCase("")){
					declerationReportBean.setPurpose(purpose.toUpperCase());
				}

				declerationReportBean.setCustomerName(customerName==null?"":customerName.toString().toUpperCase());
				declerationReportBean.setCivilId(customerDeclerationView.getCivilId()==null?"":customerDeclerationView.getCivilId().toPlainString());
				declerationReportBean.setSourceOfIncome(customerDeclerationView.getSourceOfIncome()==null?" ":customerDeclerationView.getSourceOfIncome().toUpperCase());
				declerationReportBean.setEmployeer(employer==null?" ":employer.toString().toUpperCase());
				declerationReportBean.setCompanyName(companyName==null?" ":companyName.toUpperCase());

				declerationReportBean.setTitle(customerDeclerationView.getTitle());
				declerationReportBean.setNationality(customerDeclerationView.getNationality().toUpperCase());
				declerationReportBean.setContactNo(customerDeclerationView.getContactNo()==null?"":customerDeclerationView.getContactNo().toString());
				declerationReportBean.setToAddress(toAddress.toString());
				declerationReportBean.setLogoPath(logoPath);

				StringBuffer receiptRef = new StringBuffer();
				receiptRef.append(customerDeclerationView.getCollectionDocumentFinanceYear()==null?"":customerDeclerationView.getCollectionDocumentFinanceYear());
				receiptRef.append(" / ");
				receiptRef.append(customerDeclerationView.getCollectionDocumentNo()==null?"":customerDeclerationView.getCollectionDocumentNo());
				declerationReportBean.setReceiptReferenceNo(receiptRef.toString());

				StringBuffer transactionRef = new StringBuffer();
				transactionRef.append(customerDeclerationView.getCollectionDocumentFinanceYear()==null?"":customerDeclerationView.getCollectionDocumentFinanceYear());
				transactionRef.append(" / ");
				transactionRef.append(customerDeclerationView.getDocumentNo()==null?"":customerDeclerationView.getDocumentNo());
				declerationReportBean.setTransactionRefNo(transactionRef.toString());

				declerationReportBean.setCashierName(sessionStateManage.getUserName().toUpperCase());

				if (customerDeclerationView.getSignatureSpecimenClob() != null) {
					declerationReportBean.setSignatutre(customerDeclerationView.getSignatureSpecimenClob().getSubString(1,(int) customerDeclerationView.getSignatureSpecimenClob().length()));
				}

				// currency and amount based on parameter
				String localCurrencyQuote = igeneralService.getCurrencyQuote(new BigDecimal(sessionStateManage.getCurrencyId()));

				List<AuthicationLimitCheckView> declarationReportTotal = iPersonalRemittanceService.parameterDeTails_AUTH_View(Constants.DeclarationReportTotalAmt);
				BigDecimal declarationTotalAmt = BigDecimal.ZERO;
				if(declarationReportTotal.size() != 0){
					AuthicationLimitCheckView authRemitAmtLimit = declarationReportTotal.get(0);
					declarationTotalAmt = authRemitAmtLimit.getAuthLimit();
				}

				declerationReportBean.setCurrencyAmount(localCurrencyQuote + " " + declarationTotalAmt);

				List<AuthicationLimitCheckView> declarationReportCashAmt = iPersonalRemittanceService.parameterDeTails_AUTH_View(Constants.DeclarationReportforCash);

				BigDecimal declarationCashAmt = BigDecimal.ZERO;
				if(declarationReportCashAmt.size() != 0){
					AuthicationLimitCheckView authRemitAmtLimit = declarationReportCashAmt.get(0);
					declarationCashAmt = authRemitAmtLimit.getAuthLimit();
				}
				declerationReportBean.setCurrencyAmountCash(localCurrencyQuote + " " + declarationCashAmt);


				lstDispDeclarationView.add(declerationReportBean);


			}
		}
		remitDeclarionMainReport.setRemittanceDeclaration2500List(lstDispDeclarationView);
		remitDeclarionMainReport.setRemittanceDeclaration10000List(lstDispDeclarationView);

	}*/

	

/*	public void reportDeclaration(CustomerInquiryDataTable dataTable){
		if(dataTable.getTransactionNumber() != null){
			try{
				generateDeclerationReports(dataTable.getCollectionDocumentNo(), dataTable.getCollectionDocYear(),dataTable.getTransferDate()); 

			}catch(Exception e){
				log.info(""+e.getMessage());
				RequestContext.getCurrentInstance().execute("report.show();");
				return ;
			}
		}
	} 
	*/
	
	public void reportDeclaration(CustomerInquiryDataTable dataTable){
		if(dataTable.getTransactionNumber() != null){
			try{
				generateDeclerationReports(dataTable.getCollectionDocumentNo(), dataTable.getCollectionDocYear(),dataTable.getTransferDate(),dataTable.getCustomerReference()); 

			}catch(Exception e){
				log.info(""+e.getMessage());
				RequestContext.getCurrentInstance().execute("report.show();");
				return ;
			}
		}
	} 

	
	
	
	/** New Declarion Method */ 
	public void fetchDeclatationReports(BigDecimal  collectionDocNo,BigDecimal  collectionDocYear,Date transactionDate, BigDecimal customerReference)throws Exception {

		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath = ctx.getRealPath("//");
		
		String logoPath =null;
		if(sessionStateManage.getCountryName().equalsIgnoreCase(Constants.KUWAIT)){
			logoPath = realPath+Constants.LOGO_PATH;
		}else if(sessionStateManage.getCountryName().equalsIgnoreCase(Constants.OMAN)){
			logoPath = realPath+Constants.LOGO_PATH_OM;
		}else{
			logoPath =realPath+Constants.LOGO_PATH;
		}	
		
		String subReportPath = realPath + Constants.SUB_REPORT_PATH;
		String waterMark = realPath+Constants.REPORT_WATERMARK_LOGO;
		remitDeclarionMainReport.setWaterMarkCheck(true);
		remitDeclarionMainReport.setWaterMarkLogoPath(waterMark);
		remitDeclarionMainReport.setSubReport(subReportPath);

		lstDeclarationView = iPersonalRemittanceService.getCustomerDeclerationData(sessionStateManage.getCountryId(), collectionDocYear, new BigDecimal(Constants.DOCUMENT_CODE_FOR_COLLECT_TRANSACTION),  collectionDocNo);

		String paymentMode = Constants.C ;
		String selectImagePath = realPath + Constants.WUH2H_SELECT_DECLARATION;
		String unselectImagePath = realPath + Constants.WUH2H_UNSELECT_DECLARATION;

		if (lstDeclarationView !=null && lstDeclarationView.size() > 0) {
			for (CustomerDeclerationView customerDeclerationView : lstDeclarationView) {
				
				DeclerationReportBean declerationReportBean = new DeclerationReportBean();				
				/*
				if (lstDeclarationView !=null && lstDeclarationView.size() > 0) {

					String reportType = iPersonalRemittanceService.declartionLimitCheck(sessionStateManage.getCountryId(), sessionStateManage.getCompanyId(), new BigDecimal(getCustomerId()),transactionDate, paymentMode);
					if (reportType.trim()!="") {
						if (reportType.equalsIgnoreCase("Y")) {
							declerationReportBean.setTxnTypeSelect("Y");
							declerationReportBean.setDeclImagePath1(unselectImagePath);
							declerationReportBean.setDeclImagePath2(unselectImagePath);
							declerationReportBean.setDeclImagePath3(unselectImagePath);
							declerationReportBean.setDeclImagePath4(unselectImagePath);
						}
					}
					String reportType1 = iPersonalRemittanceService.declartionLimitCheck(sessionStateManage.getCountryId(), sessionStateManage.getCompanyId(), new BigDecimal(getCustomerId()), transactionDate,"");
					if (reportType.trim()!="") {
						if (reportType1.equalsIgnoreCase("Y")) {
							declerationReportBean.setTxnTypeSelect("Y");
							declerationReportBean.setDeclImagePath1(unselectImagePath);
							declerationReportBean.setDeclImagePath2(unselectImagePath);
							declerationReportBean.setDeclImagePath3(unselectImagePath);
							declerationReportBean.setDeclImagePath4(unselectImagePath);
						}

					}

				}*/
				
				declerationReportBean.setTxnTypeSelect("Y");
				declerationReportBean.setDeclImagePath1(unselectImagePath);
				declerationReportBean.setDeclImagePath2(unselectImagePath);
				declerationReportBean.setDeclImagePath3(unselectImagePath);
				declerationReportBean.setDeclImagePath4(unselectImagePath);

				
				//set branch name
				String branchName =new String();
				String companyName =new String();
				StringBuffer toAddress =new StringBuffer();
				StringBuffer customerName=new StringBuffer();
				String countryName =new String();

				List<CountryBranch> branchList = cashTransferLToLService.getBranchName(new BigDecimal(sessionStateManage.getBranchId()));
				if(branchList.size()>0){
					branchName = branchList.get(0).getBranchName();
				}

				List<CompanyMasterDesc> companyDesc =	igeneralService.getCompanyList(sessionStateManage.getCompanyId(), sessionStateManage.getLanguageId());
				countryName =  igeneralService.getCountryName(sessionStateManage.getCountryId());
				CompanyMasterDesc companyDesObj = companyDesc.get(0);

			
				//set company address
				/*if(companyDesObj!=null){
					companyName	 = companyDesObj.getCompanyName();
				}*/

				// tp set Customer Name
				customerName.append(customerDeclerationView.getFirstName()==null?"":customerDeclerationView.getFirstName());
				customerName.append(" ");
				customerName.append(customerDeclerationView.getMiddleName()==null?"":customerDeclerationView.getMiddleName());
				customerName.append(" ");
				customerName.append(customerDeclerationView.getLastName()==null?"":customerDeclerationView.getLastName());

				//to set Employee ment details
				StringBuffer employer = new StringBuffer();
				List<CustomerEmployeeInfoView> customerEmployeeInfo = icustomerRegistrationService.findCustomerEmployeeInfo(customerDeclerationView.getCustomerId());
				if(customerEmployeeInfo.size()>0){
					CustomerEmployeeInfoView customerEmployee = customerEmployeeInfo.get(0);
					companyName = customerEmployee.getEmployeeName()==null?"":customerEmployee.getEmployeeName();
					//employer.append(customerEmployee.getEmployeeName()==null?"":customerEmployee.getEmployeeName());
					//employer.append(" ");
					employer.append(customerEmployee.getEmployeeProofDesc()==null?"":customerEmployee.getEmployeeProofDesc());
				}

				// to set Purpose of transcation
				String purpose ="";
				List<PurposeOfRemittanceView>  purposeOfRemittanceList =   iPersonalRemittanceService.getPurposeOfRemittance(customerDeclerationView.getDocumentNo(),customerDeclerationView.getDocumentFinanceYear());
				for(PurposeOfRemittanceView purposeObj :purposeOfRemittanceList){
					if(purpose.equals("")){
						purpose  = purposeObj.getFlexiFieldValue();
					}else{
						purpose  = purpose+", "+purposeObj.getFlexiFieldValue();
					}
				}
				System.out.println("purpose="+purpose);
				if(purpose != null && !purpose.equalsIgnoreCase("")){
					declerationReportBean.setPurpose(purpose.toUpperCase());
				}

				declerationReportBean.setCustomerName(customerName==null?"":customerName.toString().toUpperCase());
				declerationReportBean.setCivilId(customerDeclerationView.getCivilId()==null?"":customerDeclerationView.getCivilId().toPlainString());
				declerationReportBean.setSourceOfIncome(customerDeclerationView.getSourceOfIncome()==null?" ":customerDeclerationView.getSourceOfIncome().toUpperCase());
				declerationReportBean.setEmployeer(employer==null?" ":employer.toString().toUpperCase());
				declerationReportBean.setCompanyName(companyName==null?" ":companyName.toUpperCase());
				declerationReportBean.setTitle(customerDeclerationView.getTitle());
				declerationReportBean.setNationality(customerDeclerationView.getNationality().toUpperCase());
				declerationReportBean.setContactNo(customerDeclerationView.getContactNo()==null?"":customerDeclerationView.getContactNo().toString());
				declerationReportBean.setToAddress(toAddress.toString());
				declerationReportBean.setLogoPath(logoPath);

				StringBuffer receiptRef = new StringBuffer();
				receiptRef.append(customerDeclerationView.getCollectionDocumentFinanceYear()==null?"":customerDeclerationView.getCollectionDocumentFinanceYear());
				receiptRef.append(" / ");
				receiptRef.append(customerDeclerationView.getCollectionDocumentNo()==null?"":customerDeclerationView.getCollectionDocumentNo());
				declerationReportBean.setReceiptReferenceNo(receiptRef.toString());

				StringBuffer transactionRef = new StringBuffer();
				transactionRef.append(customerDeclerationView.getCollectionDocumentFinanceYear()==null?"":customerDeclerationView.getCollectionDocumentFinanceYear());
				transactionRef.append(" / ");
				transactionRef.append(customerDeclerationView.getDocumentNo()==null?"":customerDeclerationView.getDocumentNo());
				declerationReportBean.setTransactionRefNo(transactionRef.toString());
				declerationReportBean.setCashierName(sessionStateManage.getUserName().toUpperCase());

				if (customerDeclerationView.getSignatureSpecimenClob() != null) {
					declerationReportBean.setSignatutre(customerDeclerationView.getSignatureSpecimenClob().getSubString(1,(int) customerDeclerationView.getSignatureSpecimenClob().length()));
				}
				declerationReportBean.setBranchName(branchName);
				declerationReportBean.setCustomerReference(customerReference);
				
				
				declerationReportBean.setBeneRelation(customerDeclerationView.getRelationDescription());
				
				//declerationReportBean.setPurpose("FAMILY MAINTANENCE");
				
				lstDispDeclarationView.add(declerationReportBean);
			}
		}
		remitDeclarionMainReport.setDeclarationList(lstDispDeclarationView);

	}


	public List<CustomerDeclerationView> getLstDeclarationView() {
		return lstDeclarationView;
	}

	public void setLstDeclarationView(List<CustomerDeclerationView> lstDeclarationView) {
		this.lstDeclarationView = lstDeclarationView;
	}

	public String getReportFileName() {
		return reportFileName;
	}

	public void setReportFileName(String reportFileName) {
		this.reportFileName = reportFileName;
	}
	public boolean isBooDeclarationReportDisplay() {
		return booDeclarationReportDisplay;
	}
	public void setBooDeclarationReportDisplay(boolean booDeclarationReportDisplay) {
		this.booDeclarationReportDisplay = booDeclarationReportDisplay;
	}

	public String getExceptionMessageForReport() {
		return exceptionMessageForReport;
	}

	public void setExceptionMessageForReport(
			String exceptionMessageForReport) {
		this.exceptionMessageForReport = exceptionMessageForReport;
	}

}








class MyDateComp<T> implements Comparator<CustomerInquiryDataTable>{

	@Override
	public int compare(CustomerInquiryDataTable e1, CustomerInquiryDataTable e2) {


		int comparison = e1.getTransferDate().compareTo(e2.getTransferDate());

		if(comparison < 1){
			return 1;
		} else {
			return -1;
		}
	}


	////////////






}
