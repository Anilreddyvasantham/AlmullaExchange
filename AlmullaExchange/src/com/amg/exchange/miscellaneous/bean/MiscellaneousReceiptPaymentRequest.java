package com.amg.exchange.miscellaneous.bean;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.ServletContext;
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

import com.amg.exchange.cancelreissue.model.RemittanceTrnxViewStopMiscModel;
import com.amg.exchange.cancelreissue.model.ViewRemiitanceInfo;
import com.amg.exchange.cancelreissue.service.ICancelReissueService;
import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CutomerDetailsView;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.common.service.ISourceOfIncomeService;
import com.amg.exchange.foreigncurrency.model.ReceiptPayment;
import com.amg.exchange.foreigncurrency.model.SourceOfIncome;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.foreigncurrency.service.ForeignLocalCurrencyDenominationService;
import com.amg.exchange.miscellaneous.service.IMiscellaneousReceiptPaymentService;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerInfoView;
import com.amg.exchange.registration.service.ICompanyMasterservice;
import com.amg.exchange.remittance.model.PaymentModeDesc;
import com.amg.exchange.remittance.service.IPaymentService;
import com.amg.exchange.remittance.service.IPersonalRemittanceService;
import com.amg.exchange.stoppayment.model.RemittanceTransaction;
import com.amg.exchange.stoppayment.service.IStopPaymentService;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.GetRound;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("misReceiptPayment")
@Scope("session")
public class MiscellaneousReceiptPaymentRequest<T>  implements Serializable{
 
	Logger log = Logger.getLogger(MiscellaneousReceiptPaymentRequest.class);

	private static final long serialVersionUID = 1L;

	private BigDecimal receiptPaymentId=null;
	private BigDecimal paymentId=null;
	private BigDecimal paymentDetailId=null;
	private BigDecimal collectId=null;
	private BigDecimal collectDetailId=null;
	private String companyName;
	private String receiptpayment;
	private BigDecimal documentYear=null;
	private BigDecimal documentYearId=null;
	private  String documentNo=null;
	private String documentDate=null;
	private BigDecimal transferRefYear;


	private BigDecimal transferNo;
	private String transactionDate;
	private BigDecimal customerRef;
	private String customerName;
	private String telephone;
	private BigDecimal currencyId;
	private BigDecimal commision=BigDecimal.ZERO;
	private BigDecimal tempcommision=BigDecimal.ZERO;
	private BigDecimal charges=BigDecimal.ZERO;
	private BigDecimal deliveryCharge;
	private BigDecimal rateAdj;
	private BigDecimal otherAdj;
	private BigDecimal netAmount;
	private String remarks;
	private String processIn=Constants.Yes;
	private String dateFormat = "dd/MM/yyyy";
	private List<CurrencyMaster> currencyList;
	private BigDecimal existedDocumentNo;
	private BigDecimal colpaymentmodeId;
	private BigDecimal customerId;
	private BigDecimal receptPaymentPk;
	private String editableDocumentRef;

	private BigDecimal remitTrnxId;

	//rendering properties

	private Boolean renderDocumentNoList=false;
	private Boolean booDocumentNo=true;
	private Boolean   booReadOnly=false;

	private Boolean booCommisionReadOnly;
	private Boolean booChargesReadOnly;
	private Boolean booDelveryChargesReadOnly;
	private Boolean saveButton=true;
	private Boolean booCustRef=false;
	private Boolean booTransferNo=true;

	private boolean booProcedureDialog=false;
	private String  errorMessage;
	private Boolean disableCurrency=false;

	private Boolean renderInputText=true;
	private Boolean renderDocumentNumList=false;
	private Boolean documentNumberReadOnly=true;
	private Boolean booRenderDoc=true;
	private Boolean booRenderEditButton=true;
	private Boolean readOnlyTransferNo=false;
	private Boolean booUpdate=false;

	private BigDecimal  commisionAmountForDisplay;
	private BigDecimal   deliveryChargeForDisplay;
	private String errMsg;

	public BigDecimal getBeforeUpdateCommision() {
		return beforeUpdateCommision;
	}

	public void setBeforeUpdateCommision(BigDecimal beforeUpdateCommision) {
		this.beforeUpdateCommision = beforeUpdateCommision;
	}

	public BigDecimal getBeforeUpdateDelivery() {
		return beforeUpdateDelivery;
	}

	public void setBeforeUpdateDelivery(BigDecimal beforeUpdateDelivery) {
		this.beforeUpdateDelivery = beforeUpdateDelivery;
	}



	public BigDecimal getRemitTrnxId() {
		return remitTrnxId;
	}

	public void setRemitTrnxId(BigDecimal remitTrnxId) {
		this.remitTrnxId = remitTrnxId;
	}



	private BigDecimal beforeUpdateCommision=BigDecimal.ZERO;
	private BigDecimal beforeUpdateDelivery=BigDecimal.ZERO;


	@Autowired
	IPersonalRemittanceService iPersonalRemittanceService;
	@Autowired
	IStopPaymentService<T> stopPaymentService;
	@Autowired
	ICompanyMasterservice iCompanyMasterservice;



	//private List<ReceiptPayment> listOfReceiptPayment=new ArrayList<ReceiptPayment>();

	public BigDecimal getCommisionAmountForDisplay() {
		return commisionAmountForDisplay;
	}

	public void setCommisionAmountForDisplay(BigDecimal commisionAmountForDisplay) {
		this.commisionAmountForDisplay = commisionAmountForDisplay;
	}

	public BigDecimal getDeliveryChargeForDisplay() {
		return deliveryChargeForDisplay;
	}

	public void setDeliveryChargeForDisplay(BigDecimal deliveryChargeForDisplay) {
		this.deliveryChargeForDisplay = deliveryChargeForDisplay;
	}

	public Boolean getRenderInputText() {
		return renderInputText;
	}

	public void setRenderInputText(Boolean renderInputText) {
		this.renderInputText = renderInputText;
	}

	public Boolean getRenderDocumentNumList() {
		return renderDocumentNumList;
	}

	public void setRenderDocumentNumList(Boolean renderDocumentNumList) {
		this.renderDocumentNumList = renderDocumentNumList;
	}


	public Boolean getBooCustRef() {
		return booCustRef;
	}

	public void setBooCustRef(Boolean booCustRef) {
		this.booCustRef = booCustRef;
	}

	public Boolean getBooTransferNo() {
		return booTransferNo;
	}

	public void setBooTransferNo(Boolean booTransferNo) {
		this.booTransferNo = booTransferNo;
	}


	public boolean isBooProcedureDialog() {
		return booProcedureDialog;
	}

	public void setBooProcedureDialog(boolean booProcedureDialog) {
		this.booProcedureDialog = booProcedureDialog;
	}

	public Boolean getBooCommisionReadOnly() {
		return booCommisionReadOnly;
	}

	public void setBooCommisionReadOnly(Boolean booCommisionReadOnly) {
		this.booCommisionReadOnly = booCommisionReadOnly;
	}

	public Boolean getBooChargesReadOnly() {
		return booChargesReadOnly;
	}

	public void setBooChargesReadOnly(Boolean booChargesReadOnly) {
		this.booChargesReadOnly = booChargesReadOnly;
	}

	public Boolean getBooDelveryChargesReadOnly() {
		return booDelveryChargesReadOnly;
	}

	public void setBooDelveryChargesReadOnly(Boolean booDelveryChargesReadOnly) {
		this.booDelveryChargesReadOnly = booDelveryChargesReadOnly;
	}
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}



	//Auto Wiring Section Started 

	@Autowired
	ICancelReissueService<T> cancelReissueSevice;

	@Autowired
	IMiscellaneousReceiptPaymentService<T> miscellaneousReceiptPaymentService;

	@Autowired
	IGeneralService<T> generalService;

	@Autowired
	IPersonalRemittanceService ipersonelRemittanceService;

	@Autowired
	IPaymentService ipaymentService;

	@Autowired
	ISourceOfIncomeService isourceOfIncomeService;

	//Auto Wiring Section  Ended


	SessionStateManage sessionManage = new SessionStateManage();


	private List<ReceiptPayment> miscellaneosReceptPaymentList =new ArrayList<ReceiptPayment>();
	private List<ReceiptPayment> miscPayRecList =new ArrayList<ReceiptPayment>();
	private List<PaymentModeDesc> lstFetchAllPayMode=new ArrayList<PaymentModeDesc>();
	private List<RemittanceTrnxViewStopMiscModel> listRemitTransaction=new ArrayList<RemittanceTrnxViewStopMiscModel>();
	private List<ViewRemiitanceInfo>  miscellaneousList=new ArrayList<ViewRemiitanceInfo>();
	public List<PaymentModeDesc> getLstFetchAllPayMode() {
		return ipaymentService.fetchPaymodeDesc(new BigDecimal(sessionManage.isExists("languageId") ? sessionManage.getSessionValue("languageId") : "1"), Constants.Yes);
	}

	public void setLstFetchAllPayMode(List<PaymentModeDesc> lstFetchAllPayMode) {
		this.lstFetchAllPayMode = lstFetchAllPayMode;
	}

	public BigDecimal getReceiptPaymentId() {
		return receiptPaymentId;
	}
	public void setReceiptPaymentId(BigDecimal receiptPaymentId) {
		this.receiptPaymentId = receiptPaymentId;
	}
	public BigDecimal getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(BigDecimal paymentId) {
		this.paymentId = paymentId;
	}
	public BigDecimal getPaymentDetailId() {
		return paymentDetailId;
	}
	public void setPaymentDetailId(BigDecimal paymentDetailId) {
		this.paymentDetailId = paymentDetailId;
	}
	public BigDecimal getCollectId() {
		return collectId;
	}
	public void setCollectId(BigDecimal collectId) {
		this.collectId = collectId;
	}
	public BigDecimal getCollectDetailId() {
		return collectDetailId;
	}
	public void setCollectDetailId(BigDecimal collectDetailId) {
		this.collectDetailId = collectDetailId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getReceiptpayment() {
		return receiptpayment;
	}
	public void setReceiptpayment(String receiptpayment) {
		this.receiptpayment = receiptpayment;
	}
	public BigDecimal getDocumentYear() {
		return documentYear;
	}
	public void setDocumentYear(BigDecimal documentYear) {
		this.documentYear = documentYear;
	}
	public BigDecimal getDocumentYearId() {
		return documentYearId;
	}
	public void setDocumentYearId(BigDecimal documentYearId) {
		this.documentYearId = documentYearId;
	}
	public String getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}
	public String getDocumentDate() {
		return documentDate;
	}
	public void setDocumentDate(String documentDate) {
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
	public String getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(String transactionDate) {
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
	public BigDecimal getCommision() {
		return commision;
	}
	public void setCommision(BigDecimal commision) {
		this.commision = commision;
	}
	
	public BigDecimal getTempcommision() {
		return tempcommision;
	}
	public void setTempcommision(BigDecimal tempcommision) {
		this.tempcommision = tempcommision;
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

	public List<CurrencyMaster> getCurrencyList() {
		return currencyList;
	}
	public void setCurrencyList(List<CurrencyMaster> currencyList) {
		this.currencyList = currencyList;
	}
	public Boolean getBooDocumentNo() {
		return booDocumentNo;
	}
	public void setBooDocumentNo(Boolean booDocumentNo) {
		this.booDocumentNo = booDocumentNo;
	}

	public BigDecimal getExistedDocumentNo() {
		return existedDocumentNo;
	}
	public void setExistedDocumentNo(BigDecimal existedDocumentNo) {
		this.existedDocumentNo = existedDocumentNo;
	}
	public List<ReceiptPayment> getMiscellaneosReceptPaymentList() {
		return miscellaneosReceptPaymentList;
	}
	public void setMiscellaneosReceptPaymentList(
			List<ReceiptPayment> miscellaneosReceptPaymentList) {
		this.miscellaneosReceptPaymentList = miscellaneosReceptPaymentList;
	}
	public Boolean getRenderDocumentNoList() {
		return renderDocumentNoList;
	}
	public void setRenderDocumentNoList(Boolean renderDocumentNoList) {
		this.renderDocumentNoList = renderDocumentNoList;
	}
	public BigDecimal getColpaymentmodeId() {
		return colpaymentmodeId;
	}
	public void setColpaymentmodeId(BigDecimal colpaymentmodeId) {
		this.colpaymentmodeId = colpaymentmodeId;
	}
	public Boolean getBooReadOnly() {
		return booReadOnly;
	}
	public void setBooReadOnly(Boolean booReadOnly) {
		this.booReadOnly = booReadOnly;
	}
	public List<ReceiptPayment> getMiscPayRecList() {
		return miscPayRecList;
	}
	public void setMiscPayRecList(List<ReceiptPayment> miscPayRecList) {
		this.miscPayRecList = miscPayRecList;
	}
	public Boolean getSaveButton() {
		return saveButton;
	}
	public void setSaveButton(Boolean saveButton) {
		this.saveButton = saveButton;
	}
	public BigDecimal getCustomerId() {
		return customerId;
	}

	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	public Boolean getDisableCurrency() {
		return disableCurrency;
	}

	public void setDisableCurrency(Boolean disableCurrency) {
		this.disableCurrency = disableCurrency;
	}

	public BigDecimal getReceptPaymentPk() {
		return receptPaymentPk;
	}

	public void setReceptPaymentPk(BigDecimal receptPaymentPk) {
		this.receptPaymentPk = receptPaymentPk;
	}

	public String getEditableDocumentRef() {
		return editableDocumentRef;
	}

	public void setEditableDocumentRef(String editableDocumentRef) {
		this.editableDocumentRef = editableDocumentRef;
	}

	public Boolean getDocumentNumberReadOnly() {
		return documentNumberReadOnly;
	}

	public void setDocumentNumberReadOnly(Boolean documentNumberReadOnly) {
		this.documentNumberReadOnly = documentNumberReadOnly;
	}

	/*public List<ReceiptPayment> getListOfReceiptPayment() {
		return listOfReceiptPayment;
	}

	public void setListOfReceiptPayment(List<ReceiptPayment> listOfReceiptPayment) {
		this.listOfReceiptPayment = listOfReceiptPayment;
	}*/

	public Boolean getBooRenderDoc() {
		return booRenderDoc;
	}

	public void setBooRenderDoc(Boolean booRenderDoc) {
		this.booRenderDoc = booRenderDoc;
	}

	public Boolean getBooRenderEditButton() {
		return booRenderEditButton;
	}

	public void setBooRenderEditButton(Boolean booRenderEditButton) {
		this.booRenderEditButton = booRenderEditButton;
	}

	public Boolean getReadOnlyTransferNo() {
		return readOnlyTransferNo;
	}

	public void setReadOnlyTransferNo(Boolean readOnlyTransferNo) {
		this.readOnlyTransferNo = readOnlyTransferNo;
	}

	public Boolean getBooUpdate() {
		return booUpdate;
	}

	public void setBooUpdate(Boolean booUpdate) {
		this.booUpdate = booUpdate;
	}


	public void clear(){
		log.info("=================clear() method called ============="); 
		miscellaneosReceptPaymentList.clear();
		setCharges(BigDecimal.ZERO);
		setNetAmount(BigDecimal.ZERO);
		setOtherAdj(BigDecimal.ZERO);
		setCommision(BigDecimal.ZERO);
		setTempcommision(BigDecimal.ZERO);
		setRateAdj(BigDecimal.ZERO); 
		setCharges(null);
		setNetAmount(null);
		setOtherAdj(null);
		//setCommision(null);
		setRateAdj(null);
		setCollectDetailId(null);
		setCollectId(null);
		setDocumentNoForReport(null);
		setCompanyName(null);
		setCurrencyId(null);
		setCustomerName(null);
		setCustomerRef(null);
		setDeliveryCharge(null);
		//setDocumentDate(null);
		setDocumentNo(null);
		//setDocumentYear(null);
		//setDocumentYearId(null);
		setCustomerNameForReport(null);
		setPaymentDetailId(null);
		setPaymentId(null);
		setReceiptpayment(null);
		setReceiptPaymentId(null);
		setRemarks(null);
		setTelephone(null);
		setTransactionDate(null);
		setTransferNo(null);
		setTransferRefYear(null);
		setColpaymentmodeId(null);
		setBooChargesReadOnly( false);
		setBooCommisionReadOnly( false);
		setBooDelveryChargesReadOnly( false);
		setBooProcedureDialog(false);
		setReceptPaymentPk( null);
		setEditableDocumentRef( null);
		setBooRenderEditButton(true);
		setBooProcedureDialog(false);
		setDeleteButtonShow(false);

	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void receiptPaymentNavigation()
	{
		setBooReadOnly(false);
		setSaveButton(false);
		setRenderDocumentNumList( false);
		setBooRenderDoc(true);
		setReceptPaymentPk(null);
		setBooUpdate(false);
		setDisableCurrency(true);

		setVisableExceptionDailogForReceipt(false);
		setExceptionMessageForReport(null);
		setBeforeUpdateCommision(BigDecimal.ZERO);
		setBeforeUpdateDelivery(BigDecimal.ZERO);
		setReadOnlyTransferNo(false);
		setBooCustRef(false);
		//listOfReceiptPayment.clear();
		try {
			clear();
			setCompanyName(cancelReissueSevice.getCompanyName(sessionManage.getCompanyId(), sessionManage.getLanguageId()));
			getFinanceYearFromdb();
			setDocumentDate(new SimpleDateFormat(dateFormat).format(new Date()));
			populateCurrecyList();
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionManage.getCountryId(), sessionManage.getUserType(), sessionManage.getUserName(), "miscellaneouspaymentrquest.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../miscellaneous/miscellaneouspaymentrquest.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}

	public void getFinanceYearFromdb() {

		try{
			List<UserFinancialYear> applicationYearList = generalService.getDealYear(new Date());
			if(applicationYearList.size()>0){
				setDocumentYear(applicationYearList.get(0).getFinancialYear());
				setDocumentYearId(applicationYearList.get(0).getFinancialYearID());
			}
		}catch(Exception e){
			e.printStackTrace();	
		}
	}

	public  String getDocumentSerialIdNumberFromDB(String processIn) {


		log.info( "document seriality method called ===================");
		
		try{
			HashMap<String, String> outPutValues = generalService.getNextDocumentRefNumber(sessionManage.getCountryId().intValue() , sessionManage.getCompanyId().intValue(), Integer.parseInt(getReceiptpayment()) ,  getDocumentYear().intValue(),processIn,sessionManage.getCountryBranchCode());

			String proceErrorMsg=outPutValues.get("PROCE_ERORRMSG");
			if(proceErrorMsg!=null)
			{
				setBooProcedureDialog(true);
				setErrMsg(proceErrorMsg );
				RequestContext.getCurrentInstance().execute("proceErr.show();");
				return "0";

			}else{
				setBooProcedureDialog(false);
				String documentSerialID=outPutValues.get("DOCNO");
				return documentSerialID;

			}



		}catch(NumberFormatException | AMGException e){
			setErrMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("proceErr.show();");
			return "0";
		}


		/*String documentSerial=generalService.getNextDocumentReferenceNumber(sessionManage.getCountryId().intValue(), sessionManage.getCompanyId().intValue(),  Integer.parseInt(getReceiptpayment()), documentYear.intValue(), processIn, sessionManage.getCountryBranchCode());

		return documentSerial;*/
	}

	public void checkRecord(){
		log.info("check record called======== "+processIn);
		String docNum=getDocumentSerialIdNumberFromDB(processIn);
		if(docNum.equalsIgnoreCase("0")){
			setErrorMessage("DOCUMENT SERIALITY ERROR");
		}
		setDocumentNo(docNum);


	}


	public void populateCurrecyList(){
		List<CurrencyMaster> lstCurrencyMaster= generalService.getCurrencyList();
		if(lstCurrencyMaster.size()>0){
			setCurrencyList(lstCurrencyMaster);
		}
	}


	public void localChargeCheck(){
		if(getTransferRefYear()!=null&&getTransferNo()!=null){
			BigDecimal localChargeAmountRecPay=BigDecimal.ZERO;
			BigDecimal  witoutselectedCharges=BigDecimal.ZERO;
			log.info("=================== local chargecheck===============");
			if(listRemitTransaction!=null){
				RemittanceTrnxViewStopMiscModel remitTranx =listRemitTransaction.get(0);
				miscellaneosReceptPaymentList = miscellaneousReceiptPaymentService.fetchReceiptPayment(sessionManage.getCountryId(), sessionManage.getCompanyId(),   getTransferRefYear(), getTransferNo(),getReceiptpayment());
				if(miscellaneosReceptPaymentList.size()>0){
					for(ReceiptPayment receptPayObj:miscellaneosReceptPaymentList){
						if(receptPayObj.getLocalChargeAmount()!=null){
							localChargeAmountRecPay= localChargeAmountRecPay.add( receptPayObj.getLocalChargeAmount());

						}
					}
					witoutselectedCharges=localChargeAmountRecPay.subtract(getBeforeUpdateDelivery());
					if(remitTranx.getLocalChargeAmount()!=null)
					{
						setDeliveryChargeForDisplay( remitTranx.getLocalChargeAmount().subtract(witoutselectedCharges));
					}else
					{
						setDeliveryChargeForDisplay(witoutselectedCharges);
					}
					

					log.info( "charge amount is==========="+localChargeAmountRecPay);
					if(getCharges()!=null){
						if(remitTranx.getLocalChargeAmount()!=null&&remitTranx.getLocalChargeAmount()!=BigDecimal.ZERO){
							if(remitTranx.getLocalChargeAmount().compareTo(getCharges().add(witoutselectedCharges))==1){
								log.info( "Fisrt Loop charges"+getCharges());
							}else if(remitTranx.getLocalChargeAmount().compareTo(getCharges().add(witoutselectedCharges))==0){
								log.info( " Secon Loop charges"+getCharges());
							}else if(remitTranx.getLocalChargeAmount().compareTo(getCharges().add(witoutselectedCharges))==-1){
								log.info( "  Third Loop charges"+getCharges());
								setCharges(null);
								RequestContext.getCurrentInstance().execute("chargescheck.show();");  
							}
						}else{  	
							setCharges(null);
							RequestContext.getCurrentInstance().execute("chargeschecknotavailable.show();");     }
					}
				}
				else{
					log.info( "NO RECORDS IN EX_RECEIPT_PAYMENT");
					if(getCharges()!=null){
						if(remitTranx.getLocalChargeAmount()!=null&&remitTranx.getLocalChargeAmount()!=BigDecimal.ZERO){

							if(remitTranx.getLocalChargeAmount().compareTo(getCharges())==1){
								log.info( "first loop");
							}else if(remitTranx.getLocalChargeAmount().compareTo(getCharges())==0){
								log.info( " second loop");
							}else if(remitTranx.getLocalChargeAmount().compareTo(getCharges())==-1){
								setCharges(null);
								RequestContext.getCurrentInstance().execute("chargescheck.show();");  
							}
						}else{
							setCharges(null);
							RequestContext.getCurrentInstance().execute("chargeschecknotavailable.show();");  }
					}
				}

			}
		} else{
			calculateNetAmount();
		}

	}


	public void commisionCheck() {
		if(getTransferRefYear()!=null&&getTransferNo()!=null){
			//&& getCommision().compareTo(BigDecimal.ZERO)!=0
			if(getCommision()!=null){
				BigDecimal commsionAmont=BigDecimal.ZERO;
				BigDecimal witoutselectedCommisssion=BigDecimal.ZERO;
				log.info("::::::::::::::::::commisionCheck() method excuted :::::::::::::::::::::::::");
				if (listRemitTransaction != null) {
					
					if(getCommision().compareTo(BigDecimal.ZERO) != 0 && getTempcommision().compareTo(BigDecimal.ZERO) != 0){
						if(getCommision().compareTo(getTempcommision())==0){
							// continue
						}else{
							setCommision(getTempcommision());
							// stop
							setErrMsg("Commission amount should pay full amount or Zero, cannot pay less amount or more amount than Commission");
							RequestContext.getCurrentInstance().execute("proceErr.show();");
						}
					}
					
					RemittanceTrnxViewStopMiscModel remitTransact =listRemitTransaction.get(0);
					log.info(" VIEW LIST SIZE ::::::::::::::::::::::::::"+ listRemitTransaction.size());
					miscellaneosReceptPaymentList = miscellaneousReceiptPaymentService.fetchReceiptPayment(sessionManage.getCountryId(),sessionManage.getCompanyId() , getTransferRefYear(),getTransferNo(),getReceiptpayment());
					log.info("receipt payment size===="+ miscellaneosReceptPaymentList.size());
					if (miscellaneosReceptPaymentList.size() > 0) {
						for (ReceiptPayment receptPayObj : miscellaneosReceptPaymentList) {
							if(receptPayObj.getLocalCommisionAmoumnt()!=null){
								commsionAmont = commsionAmont.add(receptPayObj.getLocalCommisionAmoumnt());
							}
						}
						if(getBeforeUpdateCommision() != null){
							witoutselectedCommisssion=commsionAmont.subtract(getBeforeUpdateCommision());
						}
						if(remitTransact.getLocalCommisionAmount()!=null)
						{
							setCommisionAmountForDisplay(remitTransact.getLocalCommisionAmount().subtract(witoutselectedCommisssion));
						}else
						{
							setCommisionAmountForDisplay(witoutselectedCommisssion);
						}
						

						if (getCommision() != null) {

							if(remitTransact.getLocalCommisionAmount()!=null&&remitTransact.getLocalCommisionAmount()!=BigDecimal.ZERO){

								if (remitTransact.getLocalCommisionAmount().compareTo(getCommision().add(witoutselectedCommisssion)) == 1) {
									log.info( "Fisrt Loop"+getCommision());
									calculateNetAmount();
								} else if (remitTransact.getLocalCommisionAmount().compareTo(getCommision().add(witoutselectedCommisssion)) == 0) {
									log.info(" Second Loop"+getCommision());
									calculateNetAmount();
								} else if (remitTransact.getLocalCommisionAmount().compareTo(getCommision().add(witoutselectedCommisssion)) == -1) {
									log.info( " Third Loop"+getCommision());
									setCommision(null);
									calculateNetAmount();
									/*RequestContext.getCurrentInstance().execute(
									"commisionchecknotavailable.show();"); */
									//RequestContext.getCurrentInstance().execute("commisioncheck.show();");
									return;
								}
							}else{	
								//setCommision(null);
								//RequestContext.getCurrentInstance().execute("commisioncheck.show();");
								calculateNetAmount();
								RequestContext.getCurrentInstance().execute(
										"commisionchecknotavailable.show();"); 	
							}
						}else
						{
							calculateNetAmount();
						}
					} else {
						if (getCommision() != null) {
							setCommisionAmountForDisplay(remitTransact.getLocalCommisionAmount());
							if(remitTransact.getLocalCommisionAmount()!=null&&remitTransact.getLocalCommisionAmount()!=BigDecimal.ZERO){
								if (remitTransact.getLocalCommisionAmount().compareTo(getCommision()) == 1) {
									calculateNetAmount();
								} else if (remitTransact.getLocalCommisionAmount().compareTo(getCommision()) == 0) {
									calculateNetAmount();
								} else if (remitTransact.getLocalCommisionAmount().compareTo(getCommision()) == -1) {
									calculateNetAmount();
									//RequestContext.getCurrentInstance().execute("commisioncheck.show();");
								}
							}else{	
								calculateNetAmount();
								//setCommision(null);	 
								RequestContext.getCurrentInstance().execute("commisionchecknotavailable.show();");}
						}else{
							//setCommision(null);	 
							calculateNetAmount();
						}

					}

				}
			}else{
				//setCommision(null);	 
				setNetAmount(null);
				RequestContext.getCurrentInstance().execute("commisionchecknotavailable.show();");
			}
		}else{
			calculateNetAmount();
		}
	}

	public void calculateNetAmount()
	{
		
		setNetAmount(BigDecimal.ZERO);
		log.info("::::::::::::::::::::::::Calculate Net Amount method called ::::::::::::::: ");
		if(getCommision()!=null){
			setNetAmount(getCommision().add(getDeliveryCharge()==null?BigDecimal.ZERO:getDeliveryCharge()).add(getRateAdj()==null?BigDecimal.ZERO:getRateAdj()).add(getOtherAdj()==null?BigDecimal.ZERO:getOtherAdj()).add(getCharges()==null?BigDecimal.ZERO:getCharges()));
		}else if(getCharges()!=null){
			setNetAmount(getCharges().add(getCommision()==null?BigDecimal.ZERO:getCommision()).add(getDeliveryCharge()==null?BigDecimal.ZERO:getDeliveryCharge()).add(getRateAdj()==null?BigDecimal.ZERO:getRateAdj()).add(getOtherAdj()==null?BigDecimal.ZERO:getOtherAdj()));
		}else if(getDeliveryCharge()!=null){		
			setNetAmount(getDeliveryCharge().add(getCommision()==null?BigDecimal.ZERO:getCommision()).add(getRateAdj()==null?BigDecimal.ZERO:getRateAdj()).add(getOtherAdj()==null?BigDecimal.ZERO:getOtherAdj()).add(getCharges()==null?BigDecimal.ZERO:getCharges()));
		}else if(getRateAdj()!=null){
			setNetAmount(getRateAdj().add(getCommision()==null?BigDecimal.ZERO:getCommision()).add(getDeliveryCharge()==null?BigDecimal.ZERO:getDeliveryCharge()).add(getOtherAdj()==null?BigDecimal.ZERO:getOtherAdj()).add(getCharges()==null?BigDecimal.ZERO:getCharges()));
		}else if(getOtherAdj()!=null){
			setNetAmount(getOtherAdj().add(getCommision()==null?BigDecimal.ZERO:getCommision()).add(getDeliveryCharge()==null?BigDecimal.ZERO:getDeliveryCharge()).add(getRateAdj()==null?BigDecimal.ZERO:getRateAdj()).add(getCharges()==null?BigDecimal.ZERO:getCharges()));
		}else if(getCommision()==null&&getDeliveryCharge()==null&&getRateAdj()==null&&getOtherAdj()==null&&getCharges()==null){
			setNetAmount(null);
		}
	}


	/*public void firstComDelOtherAdj(FacesContext context,
			UIComponent component, Object value) throws ValidatorException{
		if(getTransferNo()!=null){
		if (getCommision() == null) {
			setCommision(BigDecimal.ZERO);

		}else if(getDeliveryCharge() == null){
			FacesMessage msg = new FacesMessage("Please Enter Delivery ","Please Enter Delivery Charge");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}else if( getRateAdj() == null){
			FacesMessage msg = new FacesMessage("Please Enter rate ","Please Enter Rate Adj");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
		}
		}*/

	public void validateFinYear(FacesContext context,UIComponent component, Object value) throws ValidatorException{
		if (value.equals(getDocumentYear())) {

		}else{
			FacesMessage msg = new FacesMessage("Please","Transfer Year Incorrect");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
	}
	
	// to check null
	private String nullCheck(String custname) {
		return custname == null ? "" : custname;
	}
	
	public void checkTransferNo(){

		try {

			setCustomerName(null);
			setCustomerRef(null);
			setTelephone(null);
			setTransactionDate(null);
			setCommision(null);
			setTempcommision(null);
			setCurrencyId(null);
			setCharges(null);
			setDeliveryCharge(null);
			setRateAdj(null);
			setRemarks(null);
			setOtherAdj(null);
			setNetAmount(null);

			listRemitTransaction.clear();
			//listRemitTrx.clear();
			miscellaneosReceptPaymentList.clear();

			log.info("======================================= checkTransferNo() called");
			if(getReceiptpayment()!=null&&getDocumentNo()!=null){
				if(getTransferRefYear()!=null){

					if(getTransferNo()!=null){
						setBooChargesReadOnly(false);
						setBooCommisionReadOnly(false);
						setBooDelveryChargesReadOnly(false);
						setSaveButton(false);

						// listRemitTrx = miscellaneousReceiptPaymentService.fetchTransactionalDetails(sessionManage.getCountryId(), sessionManage.getCompanyId(),   new BigDecimal(Constants.DOCUMENT_CODE_FOR_CANCEL_REISSUE ), getTransferRefYear(), getTransferNo());
						//listRemitTransaction = miscellaneousReceiptPaymentService.fetchTransactionalDetail(sessionManage.getCountryId(), sessionManage.getCompanyId(),   new BigDecimal(Constants.DOCUMENT_CODE_FOR_CANCEL_REISSUE ), getTransferRefYear(), getTransferNo());
						listRemitTransaction = miscellaneousReceiptPaymentService.fetchTransactionalDetailForMisc(sessionManage.getCountryId(), sessionManage.getCompanyId(),   new BigDecimal(Constants.DOCUMENT_CODE_FOR_CANCEL_REISSUE ), getTransferRefYear(), getTransferNo());

						if(listRemitTransaction == null || listRemitTransaction.size() == 0){

							// company code
							BigDecimal companyCode = null;
							List<CompanyMasterDesc> lstcompanymaster = iCompanyMasterservice.viewById(sessionManage.getCompanyId());
							if(lstcompanymaster.size() != 0){
								CompanyMasterDesc companycode = lstcompanymaster.get(0);
								if(companycode.getFsCompanyMaster().getCompanyCode() != null){
									companyCode = companycode.getFsCompanyMaster().getCompanyCode();
								}
							}

							String stateForm; 
							if(getReceiptpayment() != null && getReceiptpayment().equalsIgnoreCase(Constants.P)){
								stateForm = Constants.MiscelleaneousPaymentForm;
							}else if(getReceiptpayment() != null && getReceiptpayment().equalsIgnoreCase(Constants.R)){
								stateForm = Constants.MiscelleaneousReceiptForm;
							}else{
								stateForm = Constants.MiscelleaneousPaymentForm;
							}

							HashMap<String, Object> fetchTransferToRemits = stopPaymentService.fetchTransferForStopPayment(companyCode, new BigDecimal(Constants.DOCUMENT_CODE_FOR_REMITTANCE_TRANSACTION), getTransferRefYear(), getTransferNo(),stateForm);

							if((String)fetchTransferToRemits.get("ERRMSG") != null && !((String) fetchTransferToRemits.get("ERRMSG")).equalsIgnoreCase("")){
								setTransferNo(null);
								setErrorMessage((String) fetchTransferToRemits.get("ERRMSG"));
								RequestContext.getCurrentInstance().execute("csp.show();");
								clear();
								return;
							}else{
								listRemitTransaction = miscellaneousReceiptPaymentService.fetchTransactionalDetailForMisc(sessionManage.getCountryId(), sessionManage.getCompanyId(), new BigDecimal(Constants.DOCUMENT_CODE_FOR_CANCEL_REISSUE ), getTransferRefYear(), getTransferNo());
							}
						}

						log.info("=======================================  size====== "+listRemitTransaction.size()+"type="+getReceiptpayment());	
						if(listRemitTransaction.size()>0){
							log.info(" Entered into   ===============================");
							setBooCustRef(true);
							setDisableCurrency(true);
							RemittanceTrnxViewStopMiscModel getViewData = listRemitTransaction.get(0);
							setCustomerRef(getViewData.getCustomerRefNo());
							setCustomerName(nullCheck(getViewData.getFirstName()) + " " + nullCheck(getViewData.getMiddleName()) + " " + nullCheck(getViewData.getLastName()));

							StringBuffer customerName =new StringBuffer();
							if(getViewData.getCustomerRefNo()!=null){
								customerName.append(getViewData.getCustomerRefNo());
								customerName.append(" / ");
							}
							if(getViewData.getFirstName()!=null){
								customerName.append(getViewData.getFirstName());
							}
							if(getViewData.getMiddleName()!=null){
								customerName.append(" ");
								customerName.append(getViewData.getMiddleName());
							}
							if(getViewData.getLastName()!=null){
								customerName.append(" ");
								customerName.append(getViewData.getLastName());
							}

							if(getViewData.getContactNumber()!=null){
								customerName.append(", Tel :");
								customerName.append(getViewData.getContactNumber());
							}
							setCustomerNameForReport(customerName.toString());

							setTransactionDate(new SimpleDateFormat(dateFormat).format(getViewData.getDocumentDate()));
							setTelephone(getViewData.getContactNumber());
							/*	if(getViewData.getLocalCurrnecyCommisionId()!=null){
							setCurrencyId(getViewData.getLocalCurrnecyCommisionId());
						    }*/
							setCurrencyId(  new BigDecimal(sessionManage.getCurrencyId()));
							setCommision(getViewData.getLocalCommisionAmount());
							setTempcommision(getViewData.getLocalCommisionAmount());

							commisionCheck();

						}	else{ 
							setBooCustRef(false);
							setTransferNo(null);
							RequestContext.getCurrentInstance().execute("transfernodoesnotexist.show();");  
						}
					}else{ 
						setBooCustRef(false);
						setTransferNo(null);
						RequestContext.getCurrentInstance().execute("pleaseenteryear.show();"); 
					}		
				}else{
					setBooCustRef(false);
					setTransferNo(null);
				}			
			}else{
				setBooCustRef(false);
				RequestContext.getCurrentInstance().execute("pleasetransfernoselect.show();");  
				return;
			}

		} catch (Exception e) {
			setErrorMessage("Exception occurred " + e);
			RequestContext.getCurrentInstance().execute("errormsg.show();");
			return;
		}
	}

	/*public void checkTransferNo(){

		try {

			setCustomerName(null);
			setCustomerRef(null);
			setTelephone(null);
			setTransactionDate(null);
			setCommision(null);
			setCurrencyId(null);
			setCharges(null);
			setDeliveryCharge(null);
			setRateAdj(null);
			setRemarks(null);
			setOtherAdj(null);
			setNetAmount(null);

			listRemitTransaction.clear();
			//listRemitTrx.clear();
			miscellaneosReceptPaymentList.clear();

			log.info("======================================= checkTransferNo() called");
			if(getReceiptpayment()!=null&&getDocumentNo()!=null){
				if(getTransferRefYear()!=null){

					if(getTransferNo()!=null){
						setBooChargesReadOnly(false);
						setBooCommisionReadOnly(false);
						setBooDelveryChargesReadOnly(false);
						setSaveButton(false);

						// listRemitTrx = miscellaneousReceiptPaymentService.fetchTransactionalDetails(sessionManage.getCountryId(), sessionManage.getCompanyId(),   new BigDecimal(Constants.DOCUMENT_CODE_FOR_CANCEL_REISSUE ), getTransferRefYear(), getTransferNo());
						listRemitTransaction= miscellaneousReceiptPaymentService.fetchTransactionalDetail(sessionManage.getCountryId(), sessionManage.getCompanyId(),   new BigDecimal(Constants.DOCUMENT_CODE_FOR_CANCEL_REISSUE ), getTransferRefYear(), getTransferNo());

						log.info("=======================================  size====== "+listRemitTransaction.size()+"type="+getReceiptpayment());	
						if(listRemitTransaction.size()>0){
							log.info(" Entered into   ===============================");
							setBooCustRef(true);
							setDisableCurrency(true);
							ViewRemiitanceInfo getViewData = listRemitTransaction.get(0);
							setCustomerRef(getViewData.getCustomerRefNo());
							setCustomerName(getViewData.getFirstName()+" "+getViewData.getMiddleName()+" "+getViewData.getLastName());

							StringBuffer customerName =new StringBuffer();
							if(getViewData.getCustomerRefNo()!=null){
								customerName.append(getViewData.getCustomerRefNo());
								customerName.append(" / ");
							}
							if(getViewData.getFirstName()!=null){
								customerName.append(getViewData.getFirstName());
							}
							if(getViewData.getMiddleName()!=null){
								customerName.append(" ");
								customerName.append(getViewData.getMiddleName());
							}
							if(getViewData.getLastName()!=null){
								customerName.append(" ");
								customerName.append(getViewData.getLastName());
							}

							if(getViewData.getContactNumber()!=null){
								customerName.append(", Tel :");
								customerName.append(getViewData.getContactNumber());
							}
							setCustomerNameForReport(customerName.toString());

							setTransactionDate(new SimpleDateFormat(dateFormat).format(getViewData.getDocumentDate()));
							setTelephone(getViewData.getContactNumber());
								if(getViewData.getLocalCurrnecyCommisionId()!=null){
							        setCurrencyId(getViewData.getLocalCurrnecyCommisionId());
							    }   
							setCurrencyId(  new BigDecimal(sessionManage.getCurrencyId()));
							setCommision(getViewData.getLocalCommisionAmount());

							commisionCheck();

						}	else{ 
							// company code
							BigDecimal companyCode = null;
							List<CompanyMasterDesc> lstcompanymaster = iCompanyMasterservice.viewById(sessionManage.getCompanyId());
							if(lstcompanymaster.size() != 0){
								CompanyMasterDesc companycode = lstcompanymaster.get(0);
								if(companycode.getFsCompanyMaster().getCompanyCode() != null){
									companyCode = companycode.getFsCompanyMaster().getCompanyCode();
								}
							}
							
							String stateForm; 
							if(getReceiptpayment() != null && getReceiptpayment().equalsIgnoreCase(Constants.P)){
								stateForm = Constants.MiscelleaneousPaymentForm;
							}else if(getReceiptpayment() != null && getReceiptpayment().equalsIgnoreCase(Constants.R)){
								stateForm = Constants.MiscelleaneousReceiptForm;
							}else{
								stateForm = Constants.MiscelleaneousPaymentForm;
							}

							HashMap<String, Object> fetchTransferToRemits = stopPaymentService.fetchTransferForStopPayment(companyCode, new BigDecimal(Constants.DOCUMENT_CODE_FOR_REMITTANCE_TRANSACTION), getTransferRefYear(), getTransferNo(),stateForm);

							if((String)fetchTransferToRemits.get("ERRMSG") != null && !((String) fetchTransferToRemits.get("ERRMSG")).equalsIgnoreCase("")){

								setTransferNo(null);
								setErrorMessage((String) fetchTransferToRemits.get("ERRMSG"));
								RequestContext.getCurrentInstance().execute("csp.show();");
								clear();
								return;

							}else{

								if((RemittanceTxnOLDView)fetchTransferToRemits.get("TRANSFER") != null){

									RemittanceTxnOLDView remitOldTrnx = (RemittanceTxnOLDView) fetchTransferToRemits.get("TRANSFER");
									if(remitOldTrnx != null){

										log.info(" Entered into   ===============================");
										setBooCustRef(true);
										setDisableCurrency(true);
										setCustomerRef(remitOldTrnx.getCustomerReference());
										setCustomerName(remitOldTrnx.getBeneficaryName());

										StringBuffer customerName = new StringBuffer();
										if(remitOldTrnx.getCustomerReference() != null){
											customerName.append(remitOldTrnx.getCustomerReference());
											customerName.append(" / ");
										}
										if(remitOldTrnx.getBeneficaryName() != null){
											customerName.append(remitOldTrnx.getBeneficaryName());
										}
										
										if(remitOldTrnx.getContactNumber() != null){
											customerName.append(", Tel :");
											customerName.append(getViewData.getContactNumber());
										}
										//setTelephone(getViewData.getContactNumber());
										
										setCustomerNameForReport(customerName.toString());
										setTransactionDate(new SimpleDateFormat(dateFormat).format(remitOldTrnx.getDocumentDate()));
										setCurrencyId(new BigDecimal(sessionManage.getCurrencyId()));
										setCommision(remitOldTrnx.getLocalCommisionAmount());

										commisionCheck();

									}
								}else{
									setBooCustRef(false);
									setTransferNo(null);
									RequestContext.getCurrentInstance().execute("transfernodoesnotexist.show();");
								}
							}
						}
					}else{ 
						setBooCustRef(false);
						setTransferNo(null);
						RequestContext.getCurrentInstance().execute("pleaseenteryear.show();"); 
					}		
				}else{
					setBooCustRef(false);
					setTransferNo(null);
				}			
			}else{
				setBooCustRef(false);
				RequestContext.getCurrentInstance().execute("pleasetransfernoselect.show();");  
				return;
			}
		} catch (Exception e) {
			setErrorMessage("Exception occurred " + e);
			RequestContext.getCurrentInstance().execute("errormsg.show();");
			return;
		}
	}*/



	public void detailasBasedOnCustomerRef(){

		log.info( "==============detailasBasedOnCustomerRef() method called ============");	
		setCustomerName( null);
		setTelephone( null);
		setCurrencyId( null);
		setTransactionDate(null);
		setCommision( null);
		setCharges( null);
		setDeliveryCharge( null);
		setRateAdj( null);
		setRemarks( null);
		setOtherAdj( null);
		setNetAmount( null);
		setSaveButton(false);
		
		listRemitTransaction.clear();

		if(getDocumentNo()!=null&&getDocumentYear()!=null){
			if(getCustomerRef()!=null){
				setTransferRefYear( null);
				setTransferNo(null);
				//miscellaneousList=miscellaneousReceiptPaymentService.getAllTransactionsList(getCustomerRef());
				List<CustomerInfoView> custList = miscellaneousReceiptPaymentService.getCustomerDetailsBasedOnRef(getCustomerRef());
				//List<Customer> custList=	generalService.getCustomerDeatilsBasedOnRef(getCustomerRef());
				log.info("size is=============================="+miscellaneousList.size());
				if(custList.size()>0){
					setBooChargesReadOnly( false);
					setBooCommisionReadOnly(  false);
					setBooDelveryChargesReadOnly(  false);

					setSaveButton(false);
					setDisableCurrency(true);
					CustomerInfoView getViewData = custList.get(0);

					setCustomerRef(getViewData.getCustomerReference());
					setTelephone(getViewData.getMobile());
					//setCustomerName(getViewData.getFirstName());
					setCustomerId(getViewData.getCustomerId());
					setCurrencyId(new BigDecimal(sessionManage.getCurrencyId()));
					//setTransactionDate(new SimpleDateFormat(dateFormat).format(getViewData.getDocumentDate()) );
					StringBuffer customerName =new StringBuffer();
					//setCurrencyId(getViewData.getLocalCurrnecyCommisionId());

					if(getViewData.getCustomerReference()!=null){
						customerName.append(getViewData.getCustomerReference());
						customerName.append(" / ");
					}
					if(getViewData.getFirstName()!=null){
						customerName.append(getViewData.getFirstName());
					}
					if(getViewData.getMiddleName()!=null){
						customerName.append(" ");
						customerName.append(getViewData.getMiddleName());
					}
					if(getViewData.getLastName()!=null){
						customerName.append(" ");
						customerName.append(getViewData.getLastName());
					}

					if(getViewData.getMobile()!=null){
						customerName.append(", Tel :");
						customerName.append(getViewData.getMobile());
					}
					setCustomerName(getViewData.getFirstName()+" "+getViewData.getMiddleName()+" "+getViewData.getLastName());
					setCustomerNameForReport(customerName.toString());

				} else{  
					setCustomerRef(null);
					RequestContext.getCurrentInstance().execute("customerdoesnotexist.show();");  
				}
			}
		}else{
			setCustomerRef(null);
			RequestContext.getCurrentInstance().execute("pleasetransfernoselect.show();");  
		}
	}
	
	@SuppressWarnings("deprecation")
	public static String getCurrentDateWithFormat() {
		Map<Integer, String> data = new HashMap<Integer, String>();
		for (int i = 0; i < 12; i++) {
			if (i < 9) {
				data.put(i, "0" + String.valueOf(i + 1));
			} else {
				data.put(i, String.valueOf(i + 1));
			}
		}
		String year = String.valueOf(new Date().getYear()).substring(1, 3);
		return "01/" + data.get(Calendar.getInstance().get(Calendar.MONTH)) + "/" + year;
	}

	public void acceptButton()
	{
		if(getNetAmount()==null || getNetAmount().compareTo(BigDecimal.ZERO)==0){
			RequestContext.getCurrentInstance().execute("pleaseenteranyamount.show();"); 
			return;
		}
		if(listRemitTransaction.size()>0)
		{
			RemittanceTrnxViewStopMiscModel remitTransact =listRemitTransaction.get(0);
			BigDecimal witoutselectedCommisssion=BigDecimal.ZERO;
			BigDecimal commsionAmont=BigDecimal.ZERO;
			miscellaneosReceptPaymentList = miscellaneousReceiptPaymentService.fetchReceiptPayment(sessionManage.getCountryId(),sessionManage.getCompanyId() , getTransferRefYear(),getTransferNo(),getReceiptpayment());
			log.info("receipt payment size===="+ miscellaneosReceptPaymentList.size());
			if (miscellaneosReceptPaymentList.size() > 0) {
				for (ReceiptPayment receptPayObj : miscellaneosReceptPaymentList) {
					if(receptPayObj.getLocalCommisionAmoumnt()!=null){
						commsionAmont = commsionAmont.add(receptPayObj.getLocalCommisionAmoumnt());
					}
				}
				if(getBeforeUpdateCommision() != null){
					witoutselectedCommisssion=commsionAmont.subtract(getBeforeUpdateCommision());
				}
				
				if ((remitTransact.getLocalCommisionAmount() !=null && getCommision() != null && remitTransact.getLocalCommisionAmount().compareTo(getCommision().add(witoutselectedCommisssion)) == -1)) {
					RequestContext.getCurrentInstance().execute("commisioncheckSave.show();");
					return;
				}else
				{
					acceptButtonSave();
				}
			}else
			{
				if (getCommision() != null) {
					
					if(remitTransact.getLocalCommisionAmount()!=null && remitTransact.getLocalCommisionAmount().compareTo(BigDecimal.ZERO)!=0){
						
						/*if (remitTransact.getLocalCommisionAmount().compareTo(getCommision()) == 0)
						{
							acceptButtonSave();
						}*/
						if (getCommision() != null && remitTransact.getLocalCommisionAmount().compareTo(getCommision()) == -1) {
							RequestContext.getCurrentInstance().execute("commisioncheckSave.show();");
							return;
						}else{
							acceptButtonSave();
						}
					}else
					{
						RequestContext.getCurrentInstance().execute("commisioncheckSave.show();");
						return;
					}
				}else{
					// without commission they can other amount less than commission
					acceptButtonSave();
				}
			}
		}else
		{
			acceptButtonSave();
		}
		
	}
	
	
	public String acceptButtonSave(){
		String returnString = "";
		if(getReceptPaymentPk()==null&&getBooUpdate()==false){
			log.info( "================"+getBooUpdate());
			if(getReceiptpayment()!=null){
				if(getTransferRefYear()!=null&&getTransferNo()!=null){

					log.info( "==================Accept Button Clicked=============== ");
					try{
						if(getNetAmount()!=null&&getNetAmount().compareTo(BigDecimal.ZERO)!=0){
							String saveDocumentSerialID =  getDocumentSerialIdNumberFromDB(Constants.U);
							ReceiptPayment receiptPaymentObj=new ReceiptPayment();

							for(RemittanceTrnxViewStopMiscModel viewRemitTranx:listRemitTransaction){

								receiptPaymentObj.setCustomerName(getCustomerName());
								receiptPaymentObj.setIsActive(Constants.U);
								receiptPaymentObj.setCreatedBy(sessionManage.getUserName());
								receiptPaymentObj.setCreatedDate(new Date());
								receiptPaymentObj.setRemarks(getRemarks());

								SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
								Date acc_Month = null;
								try {
									acc_Month = DATE_FORMAT.parse(getCurrentDateWithFormat());
								} catch (ParseException e) {
									e.printStackTrace();
								}
								
								if(saveDocumentSerialID.equalsIgnoreCase("0")){

								}else{
									receiptPaymentObj.setDocumentNo(new BigDecimal(saveDocumentSerialID) );
									setDocumentNoForReport(saveDocumentSerialID);
								}
								log.info( "docnumber is=================="+saveDocumentSerialID);
								receiptPaymentObj.setAccountMMYYYY(acc_Month );
								receiptPaymentObj.setDocumentFinanceYear( getDocumentYear());
								receiptPaymentObj.setDocumentFinanceYearId(viewRemitTranx.getDocumentFinYearId());

								//	receiptPaymentObj.setApplicationDocumentNo(viewRemitTranx.getApplicationDocumentNo());
								//	receiptPaymentObj.setApplicationFinanceYear( viewRemitTranx.getApplicationFinancialYear());
								receiptPaymentObj.setTransferFinanceYear( getTransferRefYear());
								receiptPaymentObj.setTransferReference( getTransferNo());
								receiptPaymentObj.setCustomerReference( getCustomerRef());
								receiptPaymentObj.setDocumentStatus(Constants.U);

								CurrencyMaster currencyMaster = new CurrencyMaster();
								currencyMaster.setCurrencyId(new BigDecimal(sessionManage.getCurrencyId()));								
								receiptPaymentObj.setLocalFsCountryMaster(currencyMaster);

								SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
								Date date;
								try {
									date = formatter.parse(getDocumentDate());
									receiptPaymentObj.setDocumentDate(date);
									log.info( "documntdate=================="+date);
								} catch (ParseException e) {
									log.info( "========While date coversiontime problem occured in accept Button=======");
								}

								//receiptPaymentObj.setLocalChargeCurrencyId(viewRemitTranx.getLocalChargeCurrencyId());
								receiptPaymentObj.setLocalChargeCurrencyId(new BigDecimal(sessionManage.getCurrencyId()));

								if(getCharges()==null){
									receiptPaymentObj.setLocalChargeAmount(BigDecimal.ZERO);

								}else{
									receiptPaymentObj.setLocalChargeAmount(getCharges());
								}
								log.info( "localchargescurrencyid========="+viewRemitTranx.getLocalChargeCurrnecyId());
								log.info( "localcharges=================="+getCharges());

								//receiptPaymentObj.setLocalCommisionCurrencyId(viewRemitTranx.getLocalCommissionCurrencyId());
								receiptPaymentObj.setLocalCommisionCurrencyId(new BigDecimal(sessionManage.getCurrencyId()));

								receiptPaymentObj.setLocalCommisionAmoumnt(getCommision() );
								log.info( " commisioncurrencyid=================="+viewRemitTranx.getLocalCurrnecyCommisionId());
								log.info( " commision=================="+getCommision() );

								//receiptPaymentObj.setLocalDeliveryCurrencyId(viewRemitTranx.getLocalDeliveryCurrencyId() );

								receiptPaymentObj.setLocalDeliveryCurrencyId(new BigDecimal(sessionManage.getCurrencyId()));

								receiptPaymentObj.setLocalDeliveryAmount( getDeliveryCharge());

								log.info( "localdeliverycurrencyid==========="+viewRemitTranx.getLocalDeliveryCurrnecyId());
								log.info( "deliverycharge=================="+getDeliveryCharge() );

								//	receiptPaymentObj.setLocalTrnxAmount(viewRemitTranx.getLocalTransactionAmount() );
								receiptPaymentObj.setLocalOtherAdjAmount(getOtherAdj());
								receiptPaymentObj.setLocalRateAmount(getRateAdj());



								//receiptPaymentObj.setLocalOtherAdjCurrencyId(viewRemitTranx.getLocalOtherAdjCurrencyId());

								receiptPaymentObj.setLocalOtherAdjCurrencyId(new BigDecimal(sessionManage.getCurrencyId()));

								//receiptPaymentObj.setLocalRateAdjCurrencyId(viewRemitTranx.getLocalNetCurrencyId());

								receiptPaymentObj.setLocalRateAdjCurrencyId(new BigDecimal(sessionManage.getCurrencyId()));


								log.info( "localrateadjcurid============"+viewRemitTranx.getLocalNetCurrnecyId());
								receiptPaymentObj.setLocalNetAmount(getNetAmount());
								log.info( " NetAmount================="+getNetAmount() );
								//	receiptPaymentObj.setForignTrnxAmount(viewRemitTranx.getForeignTransactionAmount());

								/*Clob signature=stopPaymentService.getSignatureOfRemitter(viewRemitTranx.getDocumentNo(),viewRemitTranx.getDocumentFinYear(),viewRemitTranx.getDocumentId().toPlainString(),viewRemitTranx.getCompanyId());
								if(signature!=null){
									try {
										log.info("signature======"+signature );
										receiptPaymentObj.setSignature(signature.getSubString(1, (int) signature.length()));
									} catch (SQLException e) {

										e.printStackTrace();
									}
								}


								 */
								if(viewRemitTranx.getCustomerId()!=null){
									Customer custObj=new Customer();
									custObj.setCustomerId(viewRemitTranx.getCustomerId());
									receiptPaymentObj.setFsCustomer(custObj );
								}
								if(viewRemitTranx.getCompanyId()!=null){
									CompanyMaster companyMasterObj=new CompanyMaster();
									companyMasterObj.setCompanyId(viewRemitTranx.getCompanyId());
									receiptPaymentObj.setFsCompanyMaster(companyMasterObj);
								}

								if(viewRemitTranx.getApplicationCountryId()!=null){	
									CountryMaster countryMasterObj=new CountryMaster();
									countryMasterObj.setCountryId(viewRemitTranx.getApplicationCountryId());
									receiptPaymentObj.setFsCountryMaster(countryMasterObj);
								}

								/*if(viewRemitTranx.getForeignCurrencyId()!=null){	
										CurrencyMaster currencyMasterObj=new CurrencyMaster();
										currencyMasterObj.setCurrencyId(viewRemitTranx.getForeignCurrencyId());
										receiptPaymentObj.setForeignFsCountryMaster(currencyMasterObj );
								}
								 */
								if(viewRemitTranx.getLocalCurrnecyId()!=null){
									CurrencyMaster currencyObj=new CurrencyMaster();
									currencyObj.setCurrencyId(viewRemitTranx.getLocalCurrnecyId());
									receiptPaymentObj.setLocalFsCountryMaster(currencyObj );
								}
								//if( viewRemitTranx.getBranchId()!=null){
								if( sessionManage.getBranchId()!=null && !sessionManage.getBranchId().isEmpty()){
									CountryBranch countryBranch=new CountryBranch();
									//countryBranch.setCountryBranchId( viewRemitTranx.getBranchId());
									countryBranch.setCountryBranchId(new BigDecimal(sessionManage.getBranchId()));
									receiptPaymentObj.setCountryBranch( countryBranch);
									//receiptPaymentObj.setFsCountryBranch(countryBranch );
								}

								/*if(viewRemitTranx.getBenefeciaryBranch()!=null){
									CountryBranch countryBranch=new CountryBranch();
									countryBranch.setCountryBranchId( ipersonelRemittanceService.toFetchBankBranchIdBasedOnBankBranchName( viewRemitTranx.getBenefeciaryBranch()));
									receiptPaymentObj.setCountryBranch( countryBranch);
									//receiptPaymentObj.setFsCountryBranch(countryBranch );
									}*/

								if(viewRemitTranx.getSourceOfIncome()!=null){
									SourceOfIncome sourceOfInc=new SourceOfIncome();
									sourceOfInc.setSourceId(viewRemitTranx.getSourceOfIncome());
									receiptPaymentObj.setSourceOfIncome(sourceOfInc);  
								}


								/*if(viewRemitTranx.getPurposeOfTransaction()!=null){
									PurposeOfTransaction purposeOfTrnx=new PurposeOfTransaction();
									log.info("viewRemitTranx.getPurposeOfTransaction()"+viewRemitTranx.getPurposeOfTransaction());
									purposeOfTrnx.setPurposeId(new BigDecimal( viewRemitTranx.getPurposeOfTransaction()));
									receiptPaymentObj.setPurposeOfTransaction(purposeOfTrnx);
								}*/


								//		receiptPaymentObj.setTransactionType(viewRemitTranx.getTransactionType());
								receiptPaymentObj.setAccountMMYYYY(viewRemitTranx.getAccmmYY());
								//	receiptPaymentObj.setTransactionActualRate(viewRemitTranx.getExchangeRateApplied() );

								//		receiptPaymentObj.setGeneralLegerDate( viewRemitTranx.getGeneralLedgerDate());
								receiptPaymentObj.setIsActive(Constants.U);
								BigDecimal locCode=BigDecimal.ZERO;

								List<CountryBranch> listCode= generalService.getCountryBranchLocCode(new BigDecimal(sessionManage.getBranchId() ) );
								if(listCode!=null && listCode.size()>0){
									locCode	=listCode.get( 0).getBranchId();
								}

								receiptPaymentObj.setLocCode(locCode);

								/*	if(viewRemitTranx.getGeneralLedgerErr()!=null){
									receiptPaymentObj.setGeneralLegerErr( viewRemitTranx.getGeneralLedgerErr());
								} */

								if(getReceiptpayment().equals(Constants.DOCUMENT_CODE_FOR_RECEIVE)){
									receiptPaymentObj.setDocumentCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_RECEIVE));
									receiptPaymentObj.setReceiptType(Constants.ReceiptType_FOR_RECEIVE);
								}else {
									receiptPaymentObj.setDocumentCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_PAYMENT));
									receiptPaymentObj.setReceiptType(Constants.ReceiptType_FOR_PAYMENT);
								}
								List<Document> document = null;

								if(getReceiptpayment().equals( Constants.DOCUMENT_CODE_FOR_RECEIVE )){
									document = miscellaneousReceiptPaymentService.getDocumentId(new BigDecimal(Constants.DOCUMENT_CODE_FOR_RECEIVE  ));
								}else {
									document = miscellaneousReceiptPaymentService.getDocumentId(new BigDecimal(Constants.DOCUMENT_CODE_FOR_PAYMENT  ));
								}

								if(document.size()>0){
									receiptPaymentObj.setDocumentId(document.get(0).getDocumentID());
								}

								List<CompanyMaster> companyMaster = null;


								companyMaster = miscellaneousReceiptPaymentService.getCompanyCode(sessionManage.getCompanyId());
								if(companyMaster.size() >0){
									receiptPaymentObj.setCompanyCode(companyMaster.get(0).getCompanyCode());

								}



								setRemitTrnxId(viewRemitTranx.getRemittanceTransactionId());
							}

							miscellaneousReceiptPaymentService.saveOrUpdate(receiptPaymentObj);
							// Move to OLD System
							List<ReceiptPayment> receiptPaymetList = miscellaneousReceiptPaymentService.getReceiptPaymentListById(receiptPaymentObj.getReceiptId());
							List<RemittanceTransaction> remitTxn = miscellaneousReceiptPaymentService.getRemitTxnDetailsById(getRemitTrnxId());

							if(receiptPaymetList.size()>0 && remitTxn.size()>0){
								if(getReceiptpayment().equals( Constants.DOCUMENT_CODE_FOR_RECEIVE )){
									miscellaneousReceiptPaymentService.moveToOldEmosSystem(sessionManage.getCountryId(), 
											sessionManage.getCompanyId(), 
											new BigDecimal(Constants.DOCUMENT_CODE_FOR_RECEIVE),
											receiptPaymetList.get(0).getDocumentFinanceYear(), 
											receiptPaymetList.get(0).getDocumentNo(), 
											remitTxn.get(0).getCompanyId().getCompanyId(), 
											remitTxn.get(0).getDocumentId().getDocumentID(), 
											remitTxn.get(0).getDocumentFinanceYear(), 
											remitTxn.get(0).getDocumentNo());
								}else {
									miscellaneousReceiptPaymentService.moveToOldEmosSystem(sessionManage.getCountryId(), 
											sessionManage.getCompanyId(), 
											new BigDecimal(Constants.DOCUMENT_CODE_FOR_PAYMENT),
											receiptPaymetList.get(0).getDocumentFinanceYear(), 
											receiptPaymetList.get(0).getDocumentNo(), 
											remitTxn.get(0).getCompanyId().getCompanyId(), 
											remitTxn.get(0).getDocumentId().getDocumentID(), 
											remitTxn.get(0).getDocumentFinanceYear(), 
											remitTxn.get(0).getDocumentNo());
								}

							}

							//returnString = "miscellaneousSuccessPage";
							FacesContext.getCurrentInstance().getExternalContext()
							.redirect("../miscellaneous/miscelleneousReceiptSuccesspage.xhtml");
							return "";
							//RequestContext.getCurrentInstance().execute("complete.show();");
						}else{ RequestContext.getCurrentInstance().execute("pleaseenteranyamount.show();"); }
					}catch(Exception e){       
						e.printStackTrace();
						setErrorMessage( "Error While Saving:"+e.getMessage());
						RequestContext.getCurrentInstance().execute("errormsg.show();");

					}
				}else{	
					if( getCustomerRef()!=null){
						log.info( "===========else block called for =============");
						try{
							if(getNetAmount()!=null&&getNetAmount()!=BigDecimal.ZERO){
								ReceiptPayment receiptPaymentobj=new ReceiptPayment();

								receiptPaymentobj.setRemarks( getRemarks());
								String saveDocumentSerialID =  getDocumentSerialIdNumberFromDB(Constants.U);
								if(saveDocumentSerialID.equalsIgnoreCase("0")){

								}else{
									receiptPaymentobj.setDocumentNo(new BigDecimal(saveDocumentSerialID));
									setDocumentNoForReport(saveDocumentSerialID);
								}


								if(getCharges()==null){
									receiptPaymentobj.setLocalChargeAmount(BigDecimal.ZERO);

								}else{
									receiptPaymentobj.setLocalChargeAmount(getCharges());
								}
								receiptPaymentobj.setLocalChargeCurrencyId(new BigDecimal(sessionManage.getCurrencyId()));

								//receiptPaymentObj.setLocalCommisionCurrencyId(viewRemitTranx.getLocalCommissionCurrencyId());
								receiptPaymentobj.setLocalCommisionCurrencyId(new BigDecimal(sessionManage.getCurrencyId()));

								receiptPaymentobj.setLocalCommisionAmoumnt(getCommision() );

								log.info( " commision=================="+getCommision() );

								//receiptPaymentObj.setLocalDeliveryCurrencyId(viewRemitTranx.getLocalDeliveryCurrencyId() );

								receiptPaymentobj.setLocalDeliveryCurrencyId(new BigDecimal(sessionManage.getCurrencyId()));

								receiptPaymentobj.setLocalDeliveryAmount( getDeliveryCharge());


								receiptPaymentobj.setDocumentFinanceYear(getDocumentYear());

								receiptPaymentobj.setCreatedBy( sessionManage.getUserName());
								receiptPaymentobj.setCreatedDate( new Date());
								receiptPaymentobj.setCustomerName(getCustomerName());
								receiptPaymentobj.setCustomerReference( getCustomerRef());

								receiptPaymentobj.setLocalRateAdjCurrencyId( getCurrencyId());
								receiptPaymentobj.setLocalOtherAdjCurrencyId( getCurrencyId());

								receiptPaymentobj.setLocalRateAmount(getRateAdj());
								receiptPaymentobj.setLocalNetAmount(getNetAmount() );
								receiptPaymentobj.setLocalOtherAdjAmount(getOtherAdj());

								CompanyMaster companymaster=new CompanyMaster();
								companymaster.setCompanyId(sessionManage.getCompanyId());
								receiptPaymentobj.setFsCompanyMaster(companymaster );

								receiptPaymentobj.setDocumentStatus( Constants.U);
								SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
								Date acc_Month = null;
								try {
									acc_Month = DATE_FORMAT.parse(getCurrentDateWithFormat());
								} catch (ParseException e) {
									e.printStackTrace();
								}
								receiptPaymentobj.setAccountMMYYYY(acc_Month );

								CountryBranch countryBranch=new CountryBranch();
								log.info("CountryBranch::::::::::::::::::::::::::"+sessionManage.getBranchId());
								countryBranch.setCountryBranchId(new BigDecimal( sessionManage.getBranchId()));

								receiptPaymentobj.setCountryBranch(countryBranch);
								//receiptPaymentObj.setFsCountryBranch(countryBranch );
								BigDecimal    currency=new BigDecimal(sessionManage.getCurrencyId() ) ;
								if(getCurrencyId() !=null){
									if(currency.compareTo( getCurrencyId())!=0){
										CurrencyMaster currencyMaster=new CurrencyMaster();
										currencyMaster.setCurrencyId( getCurrencyId());
										receiptPaymentobj.setForeignFsCountryMaster(currencyMaster );
									}
								}

								if( getCurrencyId()!=null){
									CurrencyMaster currencyObj=new CurrencyMaster();
									currencyObj.setCurrencyId( new BigDecimal(sessionManage.getCurrencyId()) );
									receiptPaymentobj.setLocalFsCountryMaster(currencyObj );
								}

								Customer customerObj=new Customer();
								customerObj.setCustomerId( getCustomerId());
								receiptPaymentobj.setFsCustomer( customerObj);

								CountryMaster countryObj=new CountryMaster();
								countryObj.setCountryId(sessionManage.getCountryId());
								receiptPaymentobj.setFsCountryMaster(countryObj );
								receiptPaymentobj.setIsActive( Constants.U);

								if(getReceiptpayment().equals( Constants.DOCUMENT_CODE_FOR_RECEIVE )){
									receiptPaymentobj.setDocumentCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_RECEIVE  ));
									receiptPaymentobj.setReceiptType(Constants.ReceiptType_FOR_RECEIVE);
								}else {
									receiptPaymentobj.setDocumentCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_PAYMENT ));
									receiptPaymentobj.setReceiptType(Constants.ReceiptType_FOR_PAYMENT);
								}
								List <UserFinancialYear>   userFinList=miscellaneousReceiptPaymentService.getFinanacilYearId(getDocumentYear() );
								if(userFinList.size()>0){
									receiptPaymentobj.setDocumentFinanceYearId(userFinList.get( 0).getFinancialYearID());
								}

								SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
								Date date;
								try {
									date = formatter.parse(getDocumentDate());
									receiptPaymentobj.setDocumentDate(date);
									log.info( "documntdate=================="+date);
								} catch (ParseException e) {
									log.info( "========While date coversiontime problem occured in accept Button=======");
								} 


								List<CompanyMaster> companyMaster = null;

								companyMaster = miscellaneousReceiptPaymentService.getCompanyCode(sessionManage.getCompanyId());
								if(companyMaster.size() >0){
									receiptPaymentobj.setCompanyCode(companyMaster.get(0).getCompanyCode());

								}
								BigDecimal locCode=BigDecimal.ZERO;

								List<CountryBranch> listCode= generalService.getCountryBranchLocCode(new BigDecimal(sessionManage.getBranchId() ) );
								if(listCode!=null && listCode.size()>0){
									locCode	=listCode.get( 0).getBranchId();
								}
								receiptPaymentobj.setLocCode(locCode);
								List<Document> document = null;

								if(getReceiptpayment().equals( Constants.DOCUMENT_CODE_FOR_RECEIVE )){

									document = miscellaneousReceiptPaymentService.getDocumentId(new BigDecimal(Constants.DOCUMENT_CODE_FOR_RECEIVE  ));


								}else {
									document = miscellaneousReceiptPaymentService.getDocumentId(new BigDecimal(Constants.DOCUMENT_CODE_FOR_PAYMENT  ));

								}

								if(document.size()>0){
									receiptPaymentobj.setDocumentId(document.get(0).getDocumentID());
								}



								setSaveButton(false);
								miscellaneousReceiptPaymentService.saveOrUpdate(receiptPaymentobj);

								/*//MOVED DATA TO OLD EMOS
							List<ReceiptPayment> receiptPaymetList = miscellaneousReceiptPaymentService.getReceiptPaymentListById(receiptPaymentobj.getReceiptId());
							List<RemittanceTransaction> remitTxn = miscellaneousReceiptPaymentService.getRemitTxnDetailsById(getRemitTrnxId());
							if(receiptPaymetList.size()>0 && remitTxn.size()>0){
								if(getReceiptpayment().equals( Constants.DOCUMENT_CODE_FOR_RECEIVE )){
									miscellaneousReceiptPaymentService.moveToOldEmosSystem(sessionManage.getCountryId(), 
											sessionManage.getCompanyId(), 
											new BigDecimal(Constants.DOCUMENT_CODE_FOR_RECEIVE),
											receiptPaymetList.get(0).getDocumentFinanceYear(), 
											receiptPaymetList.get(0).getDocumentNo(), 
											remitTxn.get(0).getCompanyId().getCompanyId(), 
											remitTxn.get(0).getDocumentId().getDocumentID(), 
											remitTxn.get(0).getDocumentFinanceYear(), 
											remitTxn.get(0).getDocumentNo());
								}else {
									miscellaneousReceiptPaymentService.moveToOldEmosSystem(sessionManage.getCountryId(), 
											sessionManage.getCompanyId(), 
											new BigDecimal(Constants.DOCUMENT_CODE_FOR_PAYMENT),
											receiptPaymetList.get(0).getDocumentFinanceYear(), 
											receiptPaymetList.get(0).getDocumentNo(), 
											remitTxn.get(0).getCompanyId().getCompanyId(), 
											remitTxn.get(0).getDocumentId().getDocumentID(), 
											remitTxn.get(0).getDocumentFinanceYear(), 
											remitTxn.get(0).getDocumentNo());
								}

							}*/

								// Move to OLD System
								List<ReceiptPayment> receiptPaymetList = miscellaneousReceiptPaymentService.getReceiptPaymentListById(receiptPaymentobj.getReceiptId());
								List<RemittanceTransaction> remitTxn = miscellaneousReceiptPaymentService.getRemitTxnDetailsById(getRemitTrnxId());

								BigDecimal applicationCountryId = null; 
								BigDecimal companyId= null; 
								BigDecimal documentCode= null;
								BigDecimal documentFinanceYr= null;
								BigDecimal documentNo= null;
								BigDecimal oldRemitComId= null;
								BigDecimal oldRemitDocCode= null;
								BigDecimal oldRemitDocFinyr= null;
								BigDecimal oldRemitDocNo= null;

								if (receiptPaymetList.size() > 0) {

									applicationCountryId= receiptPaymetList.get(0).getFsCountryMaster().getCountryId();
									companyId = receiptPaymetList.get(0).getFsCompanyMaster().getCompanyId();
									documentCode = receiptPaymetList.get(0).getDocumentCode();
									documentFinanceYr = receiptPaymetList.get(0).getDocumentFinanceYear();
									documentNo = receiptPaymetList.get(0).getDocumentNo();								
								}
								if(remitTxn != null){
									if (remitTxn.size() > 0) {
										oldRemitComId = remitTxn.get(0).getCompanyId().getCompanyId();
										oldRemitDocCode = remitTxn.get(0).getDocumentId().getDocumentID(); 
										oldRemitDocFinyr = remitTxn.get(0).getDocumentFinanceYear();
										oldRemitDocNo = remitTxn.get(0).getDocumentNo();
									}
								}


								miscellaneousReceiptPaymentService.moveToOldEmosSystem(applicationCountryId, 
										companyId, 
										documentCode,
										documentFinanceYr, 
										documentNo, 
										oldRemitComId, 
										oldRemitDocCode, 
										oldRemitDocFinyr, 
										oldRemitDocNo); 

								//returnString = "miscellaneousSuccessPage";
								
								FacesContext.getCurrentInstance().getExternalContext()
								.redirect("../miscellaneous/miscelleneousReceiptSuccesspage.xhtml");
								return"";
							}else{ 
								RequestContext.getCurrentInstance().execute("pleaseenteranyamount.show();"); 
							}
							//	RequestContext.getCurrentInstance().execute("complete.show();");
						}catch(Exception e){
							setErrorMessage( "Error While Saving:"+e.getMessage());
							RequestContext.getCurrentInstance().execute("errormsg.show();");
						}
					}	 else{
						RequestContext.getCurrentInstance().execute("pleaseentercust.show();"); 
					}
				}
			}else{
				RequestContext.getCurrentInstance().execute("pleaseselectrecorpay1.show();"); 

			}

		}else{
			if(getNetAmount()!=null&&getNetAmount()!=BigDecimal.ZERO){
				log.info("UPDATE CALLED::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
				log.info("pkvalue======"+ getReceptPaymentPk());
				setDocumentNoForReport(getEditableDocumentRef());
				miscellaneousReceiptPaymentService.updateRecord( getReceptPaymentPk(),  getCommision(),  getCharges(),  getDeliveryCharge(),  getRateAdj(),  getOtherAdj(),  getNetAmount(),  getRemarks());          
				//returnString = "miscellaneousSuccessPage";
				try {
					FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../miscellaneous/miscelleneousReceiptSuccesspage.xhtml");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return "";
			}else{RequestContext.getCurrentInstance().execute("pleaseenteranyamount.show();"); }
		}


		setVisableExceptionDailogForReceipt(false);
		return returnString;
	}

	public void exitButton(){
		setVisableExceptionDailogForReceipt(false);
		log.info( "=========exit() method called =============");
		try {
			/*if (sessionManage.getRoleId().equalsIgnoreCase("1")) {
				FacesContext.getCurrentInstance().getExternalContext()
				.redirect("../registration/employeehome.xhtml");
			} else {
				FacesContext.getCurrentInstance().getExternalContext()
				.redirect("../registration/branchhome.xhtml");
			}*/
			setDeleteButtonShow(false);
			String roleNameDesc=iPersonalRemittanceService.toFetchRoleName(new BigDecimal(sessionManage.getRoleId()));
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
		} catch (Exception e) {
			log.error("======Problem Ocuured in Exit Button=====");
		} 
	}
	public void clearAll(){
		log.info( " ==============clear method called  ============");
		setCustomerName( null);
		setCommision(null);
		setDeliveryCharge(null);
		setCompanyName(null);
		setCharges( null);
		setCollectDetailId(null);
		setCollectId(null);
		setPaymentId(null);
		setPaymentDetailId(null);
		setCustomerRef( null);
		setOtherAdj(null);
		setRateAdj( null);
		setTransferRefYear(null);
		setTelephone(null);
		setTransactionDate( null);
		setTransferNo( null);
		setDocumentYearId( null);
		setCurrencyId(null);
		setNetAmount(null);
		setReceiptpayment(null);
		setReceiptPaymentId(null);
		setRemarks(null);
		setDocumentNo(null);
		setBooChargesReadOnly(false);
		setBooCommisionReadOnly(false);
		setBooDelveryChargesReadOnly(false);
		setCustomerId( null);
		setBooCustRef(false);
		setBooDocumentNo(true);
		setDisableCurrency(false);
		setDocumentNumberReadOnly(true);


		setBooRenderDoc(true);
		setRenderDocumentNumList(false);

		setBooRenderEditButton(true);
		setReadOnlyTransferNo( false);
		setReceptPaymentPk(null);
		setBooUpdate( false);
		setEditableDocumentRef(null);
		//listOfReceiptPayment.clear();
		setBeforeUpdateCommision(BigDecimal.ZERO);
		setBeforeUpdateDelivery(BigDecimal.ZERO);
		listRemitTransaction.clear();
		setDeleteButtonShow(false);
	}


	public void clearForEdit(){
		log.info( "==================clearForEdit() method called ===================");
		setCustomerName(null);
		setCommision(null);
		setDeliveryCharge(null);
		setCompanyName( null);
		setCharges(null);
		setCollectDetailId( null);
		setCollectId( null);
		setPaymentId( null);
		setPaymentDetailId( null);
		setCustomerRef( null);
		setOtherAdj(null);
		setRateAdj( null);
		setTransferRefYear(null);
		setTelephone(null);
		setTransactionDate( null);
		setTransferNo(null);
		setDocumentYearId( null);
		setCurrencyId(null);
		setNetAmount(null);
		setReceiptPaymentId( null);
		setRemarks(null);

	}

	public void editClicked(){
		log.info( ":::::::::::::::::::::EDIT  CLICKED CALLED:::::::::::::::::::::::");
		if(getReceiptpayment()!=null){
			setDocumentNumberReadOnly(false);
			setBooRenderDoc(false);
			setRenderDocumentNumList(true);
			setBooRenderEditButton(false);
			setReadOnlyTransferNo( true);
			setBooUpdate(true);
			setDeleteButtonShow(true);
			//listOfReceiptPayment.clear();
			//listOfReceiptPayment=miscellaneousReceiptPaymentService.fetchReceiptPaymentUnApprovedRecords(sessionManage.getCountryId(), sessionManage.getCompanyId(), new BigDecimal(getReceiptpayment()) , getDocumentYear());
			//log.info( "::::::::::::::::::::LISTDOCNO======="+listOfReceiptPayment.size());
		}else{
			RequestContext.getCurrentInstance().execute("pleaseselectrecorpay1.show();"); 
		}
	}



	//This Method called when edit the existing Transaction	
	public void editTransaction(){
		log.info( ":::::::::::::::::::::EDIT METHOD CALLED:::::::::::::::::::::::");

		List<ReceiptPayment> receptPayment  =miscellaneousReceiptPaymentService.fetchReceiptPaymentForUpdate(sessionManage.getCountryId(), sessionManage.getCompanyId(), new BigDecimal(getReceiptpayment()) , getDocumentYear(),new BigDecimal(getEditableDocumentRef()));
		log.info( "Rec/Pay::::::::::::::::::::"+getReceiptpayment());
		log.info( "Document Number::::::::::::::::::::"+getDocumentNo());

		log.info( ":::::::::::::::::::: rec/pay size ::::::::::::::::::::::::::::::"+receptPayment.size());
		if(receptPayment.size() != 0){


			ReceiptPayment reciptPayRec = receptPayment.get(0);

			if(reciptPayRec.getIsActive().equalsIgnoreCase(Constants.D))
			{
				clearForEdit();
				setReceptPaymentPk(null);
				setErrorMessage("Document number already deleted");
				RequestContext.getCurrentInstance().execute("errormsg.show();");
				return;
			}
			if(reciptPayRec.getDocumentStatus() != null && reciptPayRec.getDocumentStatus().equalsIgnoreCase(Constants.P)){
				clearForEdit();
				setReceptPaymentPk(null);
				setErrorMessage("Transaction Already Paid");
				RequestContext.getCurrentInstance().execute("errormsg.show();");
			}else{
				setDisableCurrency(true);
				setBooCustRef(true);
				setSaveButton(false);
				setBeforeUpdateCommision(BigDecimal.ZERO);
				setBeforeUpdateDelivery(BigDecimal.ZERO);
				for(ReceiptPayment reciptPay:receptPayment){
					setTransferNo(reciptPay.getTransferReference() );
					setTransferRefYear(reciptPay.getTransferFinanceYear() );
					listRemitTransaction = miscellaneousReceiptPaymentService.fetchTransactionalDetailForMisc(sessionManage.getCountryId(), sessionManage.getCompanyId(), new BigDecimal(Constants.DOCUMENT_CODE_FOR_CANCEL_REISSUE ), getTransferRefYear(), getTransferNo());
					setReceptPaymentPk(reciptPay.getReceiptId());
					setDeliveryCharge( reciptPay.getLocalDeliveryAmount());
					setCommision(reciptPay.getLocalCommisionAmoumnt() );
					setRemarks(reciptPay.getRemarks() );
					setCharges( reciptPay.getLocalChargeAmount());
					if(reciptPay.getDocumentDate()!=null){
						setTransactionDate(new SimpleDateFormat(dateFormat).format(reciptPay.getDocumentDate()) );
					}
					setNetAmount(reciptPay.getLocalNetAmount() );
					setRateAdj(reciptPay.getLocalRateAmount());
					setOtherAdj( reciptPay.getLocalOtherAdjAmount());
					setCustomerRef(reciptPay.getCustomerReference() );
					setCustomerName(reciptPay.getCustomerName());
					setCurrencyId( reciptPay.getLocalCommisionCurrencyId());
					setBeforeUpdateCommision(reciptPay.getLocalCommisionAmoumnt() );
					setBeforeUpdateDelivery( reciptPay.getLocalChargeAmount());
					StringBuffer customerName =new StringBuffer();
					BigDecimal customerId  = reciptPay.getFsCustomer().getCustomerId();
					List<CutomerDetailsView> customerList = iPersonalRemittanceService.getCustomerDetails(customerId);
					if(customerList!=null && customerList.size()>0)
					{
						CutomerDetailsView customerview = customerList.get(0);

						if(reciptPay.getCustomerReference()!=null){
							customerName.append(reciptPay.getCustomerReference());
							customerName.append(" / ");
						}
						if(customerview.getCustomerName()!=null){
							customerName.append(customerview.getCustomerName());
						}

						if(customerview.getContactNumber()!=null){
							customerName.append(", Tel :");
							customerName.append(customerview.getContactNumber());
						}
					}
					
					setCustomerNameForReport(customerName.toString());


					setTelephone(miscellaneousReceiptPaymentService.getTelephoneNumber(reciptPay.getCustomerReference()) );

				}
			}
			
		}else{
			clearForEdit();
			RequestContext.getCurrentInstance().execute("norecfound.show();"); 
		}

	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}


	////////////////////////////////////////////////////////  GENERATE MISCELLIOUS RECEIPT / PAYMENT  REPORT ////////////////////////////////////////////

	private JasperPrint jasperPrint;
	private String miscelleneousType;
	private String exceptionMessageForReport;
	private Boolean visableExceptionDailogForReceipt=false;
	private String documentNoForReport;
	private String customerNameForReport;

	@Autowired
	ForeignLocalCurrencyDenominationService foreignLocalCurrencyDenominationService;



	public String getCustomerNameForReport() {
		return customerNameForReport;
	}

	public void setCustomerNameForReport(String customerNameForReport) {
		this.customerNameForReport = customerNameForReport;
	}

	public String getDocumentNoForReport() {
		return documentNoForReport;
	}

	public void setDocumentNoForReport(String documentNoForReport) {
		this.documentNoForReport = documentNoForReport;
	}

	public Boolean getVisableExceptionDailogForReceipt() {
		return visableExceptionDailogForReceipt;
	}

	public void setVisableExceptionDailogForReceipt(
			Boolean visableExceptionDailogForReceipt) {
		this.visableExceptionDailogForReceipt = visableExceptionDailogForReceipt;
	}

	public String getExceptionMessageForReport() {
		return exceptionMessageForReport;
	}

	public void setExceptionMessageForReport(String exceptionMessageForReport) {
		this.exceptionMessageForReport = exceptionMessageForReport;
	}



	public String getMiscelleneousType() {
		return miscelleneousType;
	}

	public void setMiscelleneousType(String miscelleneousType) {
		this.miscelleneousType = miscelleneousType;
	}



	private List<MiscelleneousReportBean> miscelleneousReportList;
	public void generateMiscelleneousPaymentOrReceiveReport() throws IOException{
		setVisableExceptionDailogForReceipt(false);
		setExceptionMessageForReport(null);
		OutputStream outstream=null;
		try{
			fetchDataforReport();
			JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(miscelleneousReportList);
			ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
			//String realPath = ctx.getRealPath("//");
			//String reportPath = realPath +"\\reports\\design\\miscpaymentrequest.jasper"; // only for windows
			
			String realPath = ctx.getRealPath("//");
			String reportPath = realPath +"//reports//design//miscpaymentrequest.jasper";
			
			jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap(),beanCollectionDataSource);

			HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			httpServletResponse.setHeader("Cache-Control","cache, must-revalidate");
			httpServletResponse.addHeader("Content-disposition","attachment; filename=Miscelleneous"+getMiscelleneousType()+".pdf");

			byte[] pdfasbytes = JasperExportManager.exportReportToPdf(jasperPrint);

			outstream = httpServletResponse.getOutputStream();
			httpServletResponse.setContentType("application/pdf");
			httpServletResponse.setContentLength(pdfasbytes.length);
			String strSettings = "inline;filename=\"Miscelleneous"+getMiscelleneousType()+".pdf\"";
			httpServletResponse.setHeader("Content-Disposition", strSettings);
			outstream.write(pdfasbytes, 0, pdfasbytes.length);
			outstream.flush();
			FacesContext.getCurrentInstance().responseComplete();
		}catch(Exception ecception){
			setExceptionMessageForReport(null);
			setVisableExceptionDailogForReceipt(true);
			if(ecception.getMessage()!=null){
				setExceptionMessageForReport(ecception.getMessage());	
			}else{
				setExceptionMessageForReport("Exception  :"+ecception);
			}
			RequestContext.getCurrentInstance().execute("exceptioninReceipt.show();");
		}finally{
			if(outstream!=null){
				outstream.close();
			}
		}
	}

	public void fetchDataforReport(){
		miscelleneousReportList = new ArrayList<MiscelleneousReportBean>();

		MiscelleneousReportBean misceObj= new MiscelleneousReportBean();

		List<CompanyMasterDesc> companyDesc =	generalService.getCompanyList(sessionManage.getCompanyId(), sessionManage.getLanguageId());
		CompanyMasterDesc companyDesObj = companyDesc.get(0);

		StringBuffer companyAdds = new StringBuffer();
		if(companyDesObj.getFsCompanyMaster().getAddress1()!=null){
			companyAdds.append(companyDesObj.getFsCompanyMaster().getAddress1());
		}
		if(companyDesObj.getFsCompanyMaster().getAddress2()!=null){
			companyAdds.append(", ");
			companyAdds.append(companyDesObj.getFsCompanyMaster().getAddress2());
		}
		if(companyDesObj.getFsCompanyMaster().getAddress3()!=null){
			companyAdds.append(", ");
			companyAdds.append(companyDesObj.getFsCompanyMaster().getAddress3());
		}

		//set company share capital
		if(companyDesObj.getFsCompanyMaster().getCapitalAmount()!=null && companyDesObj.getFsCompanyMaster().getCurrencyId()!=null){
			String currencyQuoteName = generalService.getCurrencyQuote(companyDesObj.getFsCompanyMaster().getCurrencyId());
			Integer d =  Integer.parseInt(companyDesObj.getFsCompanyMaster().getCapitalAmount());
			NumberFormat format = NumberFormat.getNumberInstance(new Locale("en", "in"));
			String capitalAmount = format.format(d).toString();
			companyAdds.append(", Share Capital :");
			companyAdds.append(currencyQuoteName+" "+capitalAmount);
		}

		misceObj.setCompanyAddress(companyAdds.toString());
		misceObj.setCompanyName(companyDesObj.getCompanyName());
		if(getReceiptpayment()!=null){
			if(getReceiptpayment().equals("9")){
				setMiscelleneousType("Payment");
			}else{
				setMiscelleneousType("Receive");
			}
		}
		misceObj.setMiscellinoiusReportType("Miscelleneous "+getMiscelleneousType());

		StringBuffer paymentno = new StringBuffer();
		if(getDocumentYear()!=null){
			paymentno.append(getDocumentYear());
		}
		if(getDocumentNoForReport()!=null){
			paymentno.append(" / ");
			paymentno.append(getDocumentNoForReport());
		}
		misceObj.setPaymentNo(paymentno.toString());

		StringBuffer transaNo = new StringBuffer();
		if(getTransferRefYear()!=null){
			transaNo.append(getTransferRefYear());
		}
		if(getTransferNo()!=null){
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
		BigDecimal netPayment= BigDecimal.ZERO;

		if(getCommision()!=null){
			commission = GetRound.roundBigDecimal((getCommision()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId())));
		}else{
			commission = GetRound.roundBigDecimal((BigDecimal.ZERO),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId())));	
		}
		if(getCharges()!=null){
			charges = GetRound.roundBigDecimal((getCharges()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId())));
		}else{
			charges = GetRound.roundBigDecimal((BigDecimal.ZERO),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId())));
		}
		if(getDeliveryCharge()!=null){
			delvCharges = GetRound.roundBigDecimal((getDeliveryCharge()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId())));
		}else{
			delvCharges = GetRound.roundBigDecimal((BigDecimal.ZERO),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId())));
		}
		if(getRateAdj()!=null){
			rateAdjust = GetRound.roundBigDecimal((getRateAdj()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId())));
		}else{
			rateAdjust = GetRound.roundBigDecimal((BigDecimal.ZERO),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId())));
		}
		if(getOtherAdj()!=null){
			otherAdjust = GetRound.roundBigDecimal((getOtherAdj()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId())));
		}else{
			otherAdjust = GetRound.roundBigDecimal((BigDecimal.ZERO),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId())));
		}
		if(getNetAmount()!=null){
			netPayment = GetRound.roundBigDecimal((getNetAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId())));
		}else{
			netPayment = GetRound.roundBigDecimal((BigDecimal.ZERO),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId())));	
		}
		misceObj.setCommission("*********"+commission.toString());
		misceObj.setCharges("*********"+charges.toString());
		misceObj.setDelvCharges("*********"+delvCharges.toString());
		misceObj.setRateAdjust("*********"+rateAdjust.toString());
		misceObj.setOtherAdjust("*********"+otherAdjust.toString());
		misceObj.setNetPayment("*********"+netPayment.toString());

		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath = ctx.getRealPath("//");
		//String logoPath = realPath + Constants.LOGO_PATH;
		
		String logoPath =null;
		if(sessionManage.getCountryName().equalsIgnoreCase(Constants.KUWAIT)){
			logoPath = realPath+Constants.LOGO_PATH;
		}else if(sessionManage.getCountryName().equalsIgnoreCase(Constants.OMAN)){
			logoPath = realPath+Constants.LOGO_PATH_OM;
		}else{
			logoPath =realPath+Constants.LOGO_PATH;
		}
		
		
		
		misceObj.setLogoPath(logoPath);
		miscelleneousReportList.add(misceObj);

	}




	public void generateMiscelleneousPaymentOrReceiveReportOffice() throws IOException{
		setVisableExceptionDailogForReceipt(false);
		setExceptionMessageForReport(null);
		OutputStream outstream=null;
		try{
			fetchDataforReportForOffice();
			JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(miscelleneousReportList);
			ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
			String realPath = ctx.getRealPath("//");
			//String reportPath = realPath +"\\reports\\design\\miscpaymentrequestoffice.jasper";
			String reportPath = realPath +"//reports//design//miscpaymentrequestoffice.jasper";
			jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap(),beanCollectionDataSource);

			HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			httpServletResponse.setHeader("Cache-Control","cache, must-revalidate");
			httpServletResponse.addHeader("Content-disposition","attachment; filename=Miscelleneous"+getMiscelleneousType()+".pdf");

			byte[] pdfasbytes = JasperExportManager.exportReportToPdf(jasperPrint);

			outstream = httpServletResponse.getOutputStream();
			httpServletResponse.setContentType("application/pdf");
			httpServletResponse.setContentLength(pdfasbytes.length);
			String strSettings = "inline;filename=\"Miscelleneous"+getMiscelleneousType()+".pdf\"";
			httpServletResponse.setHeader("Content-Disposition", strSettings);
			outstream.write(pdfasbytes, 0, pdfasbytes.length);
			outstream.flush();
			FacesContext.getCurrentInstance().responseComplete();
		}catch(Exception ecception){
			setExceptionMessageForReport(null);
			setVisableExceptionDailogForReceipt(true);
			if(ecception.getMessage()!=null){
				setExceptionMessageForReport(ecception.getMessage());	
			}else{
				setExceptionMessageForReport("Exception  :"+ecception);
			}
			RequestContext.getCurrentInstance().execute("exceptioninReceipt.show();");
		}finally{
			if(outstream!=null){
				outstream.close();
			}
		}
	}

	public void fetchDataforReportForOffice(){
		miscelleneousReportList = new ArrayList<MiscelleneousReportBean>();

		MiscelleneousReportBean misceObj= new MiscelleneousReportBean();

		List<CompanyMasterDesc> companyDesc =	generalService.getCompanyList(sessionManage.getCompanyId(), sessionManage.getLanguageId());
		CompanyMasterDesc companyDesObj = companyDesc.get(0);

		StringBuffer companyAdds = new StringBuffer();
		if(companyDesObj.getFsCompanyMaster().getAddress1()!=null){
			companyAdds.append(companyDesObj.getFsCompanyMaster().getAddress1());
		}
		if(companyDesObj.getFsCompanyMaster().getAddress2()!=null){
			companyAdds.append(", ");
			companyAdds.append(companyDesObj.getFsCompanyMaster().getAddress2());
		}
		if(companyDesObj.getFsCompanyMaster().getAddress3()!=null){
			companyAdds.append(", ");
			companyAdds.append(companyDesObj.getFsCompanyMaster().getAddress3());
		}

		//set company share capital
		if(companyDesObj.getFsCompanyMaster().getCapitalAmount()!=null && companyDesObj.getFsCompanyMaster().getCurrencyId()!=null){
			String currencyQuoteName = generalService.getCurrencyQuote(companyDesObj.getFsCompanyMaster().getCurrencyId());
			Integer d =  Integer.parseInt(companyDesObj.getFsCompanyMaster().getCapitalAmount());
			NumberFormat format = NumberFormat.getNumberInstance(new Locale("en", "in"));
			String capitalAmount = format.format(d).toString();
			companyAdds.append(", Share Capital :");
			companyAdds.append(currencyQuoteName+" "+capitalAmount);
		}

		misceObj.setCompanyAddress(companyAdds.toString());
		misceObj.setCompanyName(companyDesObj.getCompanyName());
		if(getReceiptpayment()!=null){
			if(getReceiptpayment().equals("9")){
				setMiscelleneousType("Payment");
			}else{
				setMiscelleneousType("Receive");
			}
		}
		misceObj.setMiscellinoiusReportType("Miscelleneous "+getMiscelleneousType());

		StringBuffer paymentno = new StringBuffer();
		if(getDocumentYear()!=null){
			paymentno.append(getDocumentYear());
		}
		if(getDocumentNoForReport()!=null){
			paymentno.append(" / ");
			paymentno.append(getDocumentNoForReport());
		}
		misceObj.setPaymentNo(paymentno.toString());

		StringBuffer transaNo = new StringBuffer();
		if(getTransferRefYear()!=null){
			transaNo.append(getTransferRefYear());
		}
		if(getTransferNo()!=null){
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
		BigDecimal netPayment= BigDecimal.ZERO;

		if(getCommision()!=null){
			commission = GetRound.roundBigDecimal((getCommision()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId())));
		}else{
			commission = GetRound.roundBigDecimal((BigDecimal.ZERO),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId())));	
		}
		if(getCharges()!=null){
			charges = GetRound.roundBigDecimal((getCharges()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId())));
		}else{
			charges = GetRound.roundBigDecimal((BigDecimal.ZERO),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId())));
		}
		if(getDeliveryCharge()!=null){
			delvCharges = GetRound.roundBigDecimal((getDeliveryCharge()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId())));
		}else{
			delvCharges = GetRound.roundBigDecimal((BigDecimal.ZERO),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId())));
		}
		if(getRateAdj()!=null){
			rateAdjust = GetRound.roundBigDecimal((getRateAdj()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId())));
		}else{
			rateAdjust = GetRound.roundBigDecimal((BigDecimal.ZERO),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId())));
		}
		if(getOtherAdj()!=null){
			otherAdjust = GetRound.roundBigDecimal((getOtherAdj()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId())));
		}else{
			otherAdjust = GetRound.roundBigDecimal((BigDecimal.ZERO),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId())));
		}
		if(getNetAmount()!=null){
			netPayment = GetRound.roundBigDecimal((getNetAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId())));
		}else{
			netPayment = GetRound.roundBigDecimal((BigDecimal.ZERO),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId())));	
		}
		misceObj.setCommission("*********"+commission.toString());
		misceObj.setCharges("*********"+charges.toString());
		misceObj.setDelvCharges("*********"+delvCharges.toString());
		misceObj.setRateAdjust("*********"+rateAdjust.toString());
		misceObj.setOtherAdjust("*********"+otherAdjust.toString());
		misceObj.setNetPayment("*********"+netPayment.toString());

		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath = ctx.getRealPath("//");
		//String logoPath = realPath + Constants.LOGO_PATH;
		
		String logoPath =null;
		if(sessionManage.getCountryName().equalsIgnoreCase(Constants.KUWAIT)){
			logoPath = realPath+Constants.LOGO_PATH;
		}else if(sessionManage.getCountryName().equalsIgnoreCase(Constants.OMAN)){
			logoPath = realPath+Constants.LOGO_PATH_OM;
		}else{
			logoPath =realPath+Constants.LOGO_PATH;
		}
		
		
		
		misceObj.setLogoPath(logoPath);
		miscelleneousReportList.add(misceObj);

	}

	private Boolean deleteButtonShow;
	
	
	public Boolean getDeleteButtonShow() {
		return deleteButtonShow;
	}

	public void setDeleteButtonShow(Boolean deleteButtonShow) {
		this.deleteButtonShow = deleteButtonShow;
	}

	public void deleteRecord()
	{
		try{
			if(getReceptPaymentPk()!=null&&getBooUpdate()){
				
				RequestContext.getCurrentInstance().execute("complete.show();");
			}else
			{
				RequestContext.getCurrentInstance().execute("norecfound.show();"); 
			}
			
		}catch (Exception e) {
			setErrorMessage("Exception occurred " + e.getMessage());
			RequestContext.getCurrentInstance().execute("errormsg.show();");
			return;
		}
		
	}
	
	public void afterDeleteCall()
	{
		
		miscellaneousReceiptPaymentService.deactivateRecord(getReceptPaymentPk(), sessionManage.getUserName());
		setBooReadOnly(false);
		setSaveButton(false);
		setRenderDocumentNumList( false);
		setBooRenderDoc(true);
		setReceptPaymentPk(null);
		setBooUpdate(false);
		setDisableCurrency(true);

		setVisableExceptionDailogForReceipt(false);
		setExceptionMessageForReport(null);
		setBeforeUpdateCommision(BigDecimal.ZERO);
		setBeforeUpdateDelivery(BigDecimal.ZERO);
		setReadOnlyTransferNo(false);
		setBooCustRef(false);
		//listOfReceiptPayment.clear();
		try {
			clear();
			
			FacesContext.getCurrentInstance().getExternalContext().redirect("../miscellaneous/miscellaneouspaymentrquest.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
		setCompanyName(cancelReissueSevice.getCompanyName(sessionManage.getCompanyId(), sessionManage.getLanguageId()));
		getFinanceYearFromdb();
		setDocumentDate(new SimpleDateFormat(dateFormat).format(new Date()));
		populateCurrecyList();

	}
}



