package com.amg.exchange.intercompany.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.foreigncurrency.model.ReceiptPayment;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.intercompany.model.IntraTrnxModel;
import com.amg.exchange.intercompany.service.IntraCompanyService;
import com.amg.exchange.miscellaneous.model.Payment;
import com.amg.exchange.miscellaneous.service.IMiscellaneousReceiptPaymentService;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.remittance.model.PaymentModeDesc;
import com.amg.exchange.remittance.model.RemittanceApplicationView;
import com.amg.exchange.remittance.model.ViewBankDetails;
import com.amg.exchange.remittance.service.IPaymentService;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.SessionStateManage;


/**
 * @author Chiranjeevi
 * 
 */
@Component("intraCompanyBankPaymentBean")
@Scope("session")
public class IntraCompanyBankPaymentBean<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	SessionStateManage sessionStateManage = new SessionStateManage();
	Logger log = Logger.getLogger(IntraCompanyBankPaymentBean.class);

	// variables

	// panel 1 Variables
	private String senderCountryName;
	private String senderCountryCode;
	private BigDecimal senderCountryId;
	private String senderCompanyName;
	private String senderCompanyCode;
	private BigDecimal senderCompanyId;
	private BigDecimal paymentModeId;
	private String paymentModeCode;
	private String bankToTransfer;
	
	// extra variables
	private BigDecimal documentYear=null;
	private BigDecimal documentYearId=null;
	private String receiptpayment;
	private String errmsg;

	@Autowired
	IGeneralService<T> generalService;
	@Autowired
	IPaymentService ipaymentService;
	@Autowired
	IntraCompanyService intraCompanyService;
	@Autowired
	IMiscellaneousReceiptPaymentService<T> miscellaneousReceiptPaymentService;

	private List<PaymentModeDesc> lstFetchPaymentMode = new ArrayList<PaymentModeDesc>();
	private HashMap<BigDecimal, String> paymentModeMap = new HashMap<BigDecimal, String>();
	private List<ViewBankDetails> bankMasterList = new ArrayList<ViewBankDetails>();
	private List<CountryMasterDesc> lstFetchSenderCountry = new ArrayList<CountryMasterDesc>();
	private HashMap<String, String> senderCountryMap = new HashMap<String, String>();
	private List<RemittanceApplicationView> lstRemittanceTrnxDetails = new ArrayList<RemittanceApplicationView>();
	private List<IntraCompanyBankPaymentDataTable> lstBankPaymentIntraComp = new ArrayList<IntraCompanyBankPaymentDataTable>();
	private List<IntraCompanyBankPaymentDataTable> lstSelectedBankPaymentIntraComp = new CopyOnWriteArrayList<IntraCompanyBankPaymentDataTable>();
	private HashMap<String, Object> savingMap = new HashMap<String, Object>();

	// page navigation
	public void pageNavigation(){
		try {
			fetchBussinessCountry();
			callPaymentMode();
			getLocalBankList();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/IntraCompanyDetails.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// fetch business country apart of local country
	public void fetchBussinessCountry(){
		lstFetchSenderCountry.clear();
		senderCountryMap.clear();
		List<CountryMasterDesc> lstCountry = generalService.getCountryList(sessionStateManage.getLanguageId());
		if(lstCountry.size() != 0){
			for (CountryMasterDesc countryMasterDesc : lstCountry) {
				if(countryMasterDesc.getFsCountryMaster().getCountryAlpha2Code() != null && countryMasterDesc.getFsCountryMaster().getCountryId().compareTo(sessionStateManage.getCountryId()) != 0){
					if(countryMasterDesc.getFsCountryMaster().getCountryAlpha2Code().equalsIgnoreCase(Constants.KUWAIT_ALPHA_TWO_CODE) || countryMasterDesc.getFsCountryMaster().getCountryAlpha2Code().equalsIgnoreCase(Constants.BAHRAIN_ALPHA_TWO_CODE) || countryMasterDesc.getFsCountryMaster().getCountryAlpha2Code().equalsIgnoreCase(Constants.OMAN_ALPHA_TWO_CODE)){
						lstFetchSenderCountry.add(countryMasterDesc);
						senderCountryMap.put(countryMasterDesc.getFsCountryMaster().getCountryCode(), countryMasterDesc.getCountryName());
					}
				}
			}
		}
	}

	// list of payment mode
	public void callPaymentMode(){
		paymentModeMap.clear();
		List<PaymentModeDesc> paymentModeTempList = new ArrayList<PaymentModeDesc>();
		List<PaymentModeDesc> paymentModeList = ipaymentService.fetchPaymodeDesc(sessionStateManage.getLanguageId(), Constants.Yes);
		for(PaymentModeDesc paymentObj:paymentModeList){
			if(paymentObj.getPaymentMode().getPaymentCode().equalsIgnoreCase(Constants.ChequeCode) || paymentObj.getPaymentMode().getPaymentCode().equalsIgnoreCase(Constants.BankTransferCode)){
				paymentModeMap.put(paymentObj.getPaymentMode().getPaymentModeId(), paymentObj.getLocalPaymentName());
				paymentModeTempList.add(paymentObj);
			}

		}
		if(paymentModeTempList.size()>0){
			setLstFetchPaymentMode(paymentModeTempList);
		}
	}

	// to get the local bank list or customer bank list
	public void getLocalBankList() {
		bankMasterList.clear();
		List<ViewBankDetails> localbankList = generalService.getLocalBankListFromView(sessionStateManage.getCountryId());
		if (localbankList.size() > 0) {
			bankMasterList.addAll(localbankList);
		}
	}
	
	// to check null
	private String nullCheck(String custname) {
		return custname == null ? "" : custname;
	}
	
	// search records for 
	public void search(){
		
		lstBankPaymentIntraComp.clear();
		if(senderCountryMap != null && senderCountryMap.size() != 0){
			List<IntraTrnxModel> lstIntraTrnx =  intraCompanyService.fetchIntraTrnxBasedonCountry(senderCountryMap.get(getSenderCountryCode()));
			
			for (IntraTrnxModel intraTrnxModel : lstIntraTrnx) {
				
				IntraCompanyBankPaymentDataTable interPaymentList = new IntraCompanyBankPaymentDataTable();
				
				interPaymentList.setSendDocumentDate(intraTrnxModel.getSendDocumentDate());
				interPaymentList.setSendDocumentNo(intraTrnxModel.getSendDocumentNo());
				interPaymentList.setBeneficiaryName(nullCheck(intraTrnxModel.getRecvFirstName()) + nullCheck(intraTrnxModel.getRecvSecondName())
						+ nullCheck(intraTrnxModel.getRecvThirdName()) + nullCheck(intraTrnxModel.getRecvFourthName()) + nullCheck(intraTrnxModel.getRecvFifthName()));
				interPaymentList.setBeneBankName(intraTrnxModel.getBeneBankName());
				interPaymentList.setBeneBankBranchName(intraTrnxModel.getBeneBankBranchName());
				interPaymentList.setBeneBankAccountNumber(intraTrnxModel.getBeneBankAccountNumber());
				interPaymentList.setForeignCurrencyName(intraTrnxModel.getForeignCurrencyName());
				interPaymentList.setForeignTrnxAmount(intraTrnxModel.getForeignTrnxAmount());
				//interPaymentList.set
				
				lstBankPaymentIntraComp.add(interPaymentList);
			}
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
	
	public String getDocumentSerialIdNumberFromDB(String processIn) {
		log.info( "document seriality method called ===================");
		try{
			setReceiptpayment(Constants.DOCUMENT_CODE_FOR_PAYMENT);
			HashMap<String, String> outPutValues = generalService.getNextDocumentRefNumber(sessionStateManage.getCountryId().intValue() , sessionStateManage.getCompanyId().intValue(), Integer.parseInt(getReceiptpayment()) ,  getDocumentYear().intValue(),processIn,sessionStateManage.getCountryBranchCode());
			String proceErrorMsg=outPutValues.get("PROCE_ERORRMSG");
			if(proceErrorMsg!=null)
			{
				setErrmsg(proceErrorMsg );
				RequestContext.getCurrentInstance().execute("alertmsg.show();");
				return "0";
			}else{
				String documentSerialID=outPutValues.get("DOCNO");
				return documentSerialID;
			}
		}catch(NumberFormatException | AMGException e){
			setErrmsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
			return "0";
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

	// submit button
	public void saveAll(){
		System.out.println("SAVE");
		if(savingMap != null){
			savingMap.clear();
		}

		try {
			// get Doc Year
			getFinanceYearFromdb();
			savingMap.put("DOCUMENTCODE", Constants.DOCUMENT_CODE_FOR_PAYMENT);
			savingMap.put("USERNAME", sessionStateManage.getUserName());

			if(lstSelectedBankPaymentIntraComp != null && lstSelectedBankPaymentIntraComp.size() != 0){

				for (IntraCompanyBankPaymentDataTable bankPaymentDt : lstSelectedBankPaymentIntraComp) {

					ReceiptPayment receiptPaymentObj = new ReceiptPayment();

					receiptPaymentObj.setCustomerName(bankPaymentDt.getBeneficiaryName());
					receiptPaymentObj.setIsActive(Constants.Yes);
					receiptPaymentObj.setCreatedBy(sessionStateManage.getUserName());
					receiptPaymentObj.setCreatedDate(new Date());
					//receiptPaymentObj.setRemarks(getRemarks());

					SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
					Date acc_Month = null;
					try {
						acc_Month = DATE_FORMAT.parse(getCurrentDateWithFormat());
					} catch (ParseException e) {
						e.printStackTrace();
					}

					String saveDocumentSerialID =  getDocumentSerialIdNumberFromDB(Constants.U);
					if(saveDocumentSerialID.equalsIgnoreCase("0")){
						setErrmsg("Document Seriality Number Not Available");
						RequestContext.getCurrentInstance().execute("alertmsg.show();");
					}else{
						receiptPaymentObj.setDocumentNo(new BigDecimal(saveDocumentSerialID) );
					}
					log.info( "docnumber is=================="+saveDocumentSerialID);
					receiptPaymentObj.setAccountMMYYYY(acc_Month );
					receiptPaymentObj.setDocumentFinanceYear(getDocumentYear());
					receiptPaymentObj.setDocumentFinanceYearId(getDocumentYearId());
					receiptPaymentObj.setTransferFinanceYear(bankPaymentDt.getSendDocumentFinanceYear());
					receiptPaymentObj.setTransferReference(bankPaymentDt.getSendDocumentNo());
					//receiptPaymentObj.setCustomerReference(getCustomerrefno());
					receiptPaymentObj.setDocumentStatus(Constants.Yes);

					CurrencyMaster currencyMaster = new CurrencyMaster();
					currencyMaster.setCurrencyId(new BigDecimal(sessionStateManage.getCurrencyId()));								
					receiptPaymentObj.setLocalFsCountryMaster(currencyMaster);

					//date = formatter.parse(getDocumentDate());
					//receiptPaymentObj.setDocumentDate(date);
					receiptPaymentObj.setDocumentDate(bankPaymentDt.getSendDocumentDate());

					//receiptPaymentObj.setLocalChargeCurrencyId(viewRemitTranx.getLocalChargeCurrencyId());
					receiptPaymentObj.setLocalChargeCurrencyId(new BigDecimal(sessionStateManage.getCurrencyId()));

					/*if(getCharges()==null){
				receiptPaymentObj.setLocalChargeAmount(BigDecimal.ZERO);
			    }else{
				receiptPaymentObj.setLocalChargeAmount(getCharges());
			    }*/


					//receiptPaymentObj.setLocalCommisionCurrencyId(viewRemitTranx.getLocalCommissionCurrencyId());
					receiptPaymentObj.setLocalCommisionCurrencyId(new BigDecimal(sessionStateManage.getCurrencyId()));

					//receiptPaymentObj.setLocalCommisionAmoumnt(getCommision() );

					//receiptPaymentObj.setLocalDeliveryCurrencyId(viewRemitTranx.getLocalDeliveryCurrencyId() );

					receiptPaymentObj.setLocalDeliveryCurrencyId(new BigDecimal(sessionStateManage.getCurrencyId()));

					//receiptPaymentObj.setLocalDeliveryAmount( getDeliveryCharge());

					//	receiptPaymentObj.setLocalTrnxAmount(viewRemitTranx.getLocalTransactionAmount() );
					//receiptPaymentObj.setLocalOtherAdjAmount(getOtherAdj());
					//receiptPaymentObj.setLocalRateAmount(getRateAdj());
					//receiptPaymentObj.setLocalOtherAdjCurrencyId(viewRemitTranx.getLocalOtherAdjCurrencyId());

					receiptPaymentObj.setLocalOtherAdjCurrencyId(new BigDecimal(sessionStateManage.getCurrencyId()));

					//receiptPaymentObj.setLocalRateAdjCurrencyId(viewRemitTranx.getLocalNetCurrencyId());
					receiptPaymentObj.setLocalRateAdjCurrencyId(new BigDecimal(sessionStateManage.getCurrencyId()));
					receiptPaymentObj.setLocalNetAmount(bankPaymentDt.getForeignTrnxAmount());
					//	receiptPaymentObj.setForignTrnxAmount(viewRemitTranx.getForeignTransactionAmount());
					/*Clob signature=stopPaymentService.getSignatureOfRemitter(viewRemitTranx.getDocumentNo(),viewRemitTranx.getDocumentFinYear(),viewRemitTranx.getDocumentId().toPlainString(),viewRemitTranx.getCompanyId());
			    if(signature!=null){
				try {
					log.info("signature======"+signature );
					receiptPaymentObj.setSignature(signature.getSubString(1, (int) signature.length()));
				} catch (SQLException e) {

					e.printStackTrace();
				}
			    } */
					/*if(getCustomerId() != null){
					Customer custObj=new Customer();
					custObj.setCustomerId(getCustomerId());
					receiptPaymentObj.setFsCustomer(custObj );
				}*/


					if(sessionStateManage.getCompanyId() != null){
						CompanyMaster companyMasterObj=new CompanyMaster();
						companyMasterObj.setCompanyId(sessionStateManage.getCompanyId());
						receiptPaymentObj.setFsCompanyMaster(companyMasterObj);
					}

					if(sessionStateManage.getCountryId() != null){	
						CountryMaster countryMasterObj=new CountryMaster();
						countryMasterObj.setCountryId(sessionStateManage.getCountryId());
						receiptPaymentObj.setFsCountryMaster(countryMasterObj);
					}

					if(sessionStateManage.getCurrencyId() !=null){
						CurrencyMaster currencyObj=new CurrencyMaster();
						currencyObj.setCurrencyId(new BigDecimal(sessionStateManage.getCurrencyId()));
						receiptPaymentObj.setLocalFsCountryMaster(currencyObj);
					}

					if(sessionStateManage.getBranchId() !=null){
						CountryBranch countryBranch=new CountryBranch();
						countryBranch.setCountryBranchId(new BigDecimal(sessionStateManage.getBranchId()));
						receiptPaymentObj.setCountryBranch(countryBranch);
					}

					/*if(viewRemitTranx.getSourceOfIncome()!=null){
					SourceOfIncome sourceOfInc=new SourceOfIncome();
					sourceOfInc.setSourceId(viewRemitTranx.getSourceOfIncome());
					receiptPaymentObj.setSourceOfIncome(sourceOfInc);  
			    	}*/

					//		receiptPaymentObj.setTransactionType(viewRemitTranx.getTransactionType());
					receiptPaymentObj.setAccountMMYYYY(acc_Month);
					//	receiptPaymentObj.setTransactionActualRate(viewRemitTranx.getExchangeRateApplied() );

					//		receiptPaymentObj.setGeneralLegerDate( viewRemitTranx.getGeneralLedgerDate());
					receiptPaymentObj.setIsActive(Constants.Yes);
					BigDecimal locCode=BigDecimal.ZERO;

					List<CountryBranch> listCode= generalService.getCountryBranchLocCode(new BigDecimal(sessionStateManage.getBranchId() ) );
					if(listCode!=null && listCode.size()>0){
						locCode	=listCode.get(0).getBranchId();
					}
					receiptPaymentObj.setLocCode(locCode);

					List<Document> document = null;

					document = miscellaneousReceiptPaymentService.getDocumentId(new BigDecimal(Constants.DOCUMENT_CODE_FOR_PAYMENT));
					if(document.size()>0){
						receiptPaymentObj.setDocumentId(document.get(0).getDocumentID());
					}

					receiptPaymentObj.setDocumentCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_PAYMENT));
					receiptPaymentObj.setReceiptType("99");

					List<CompanyMaster> companyMaster = null;


					companyMaster = miscellaneousReceiptPaymentService.getCompanyCode(sessionStateManage.getCompanyId());
					if(companyMaster.size() >0){
						receiptPaymentObj.setCompanyCode(companyMaster.get(0).getCompanyCode());
					}

					savingMap.put("RECEIPTPAYMENT", receiptPaymentObj);

					Payment payment = savePayment(acc_Month,receiptPaymentObj);
					savingMap.put("PAYMENT", payment);

					// mapvalue
					System.out.println(savingMap.toString());

					intraCompanyService.saveRecords(savingMap);
					
					lstSelectedBankPaymentIntraComp.clear();

					RequestContext.getCurrentInstance().execute("intraAppSave.show();");
				}

			}else{
				setErrmsg("No Records selected to Save");
				RequestContext.getCurrentInstance().execute("alertmsg.show();");
			}

		} catch (AMGException e) {
			setErrmsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}

	}
	
	public Payment savePayment(Date acc_Month,ReceiptPayment receiptPaymentObj){

		Payment payment =new Payment();

		CountryMaster countryMaster = new CountryMaster();
		countryMaster.setCountryId(sessionStateManage.getCountryId());
		payment.setCountryId(countryMaster);

		payment.setCompanyId(sessionStateManage.getCompanyId());
		payment.setDocCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_PAYMENT));
		payment.setDocNumber(receiptPaymentObj.getDocumentNo());
		payment.setCountryBranchId(new BigDecimal(sessionStateManage.getBranchId()));
		payment.setReceiptType("99");
		//payment.setPaymentmode();
		//payment.setChequeReference(chequeReference);
		//payment.setChequekdate(chequekdate);
		//payment.setApprovalNo(approvalNo);
		payment.setCurrencyId(new BigDecimal(sessionStateManage.getCurrencyId()));
		//lstofPayment
		//payment.setPaidAmount(getTotalCashEntered());
		//payment.setIsCashDeposit(isCashDeposit);
		//payment.setCdepDoccod(cdepDoccod);
		//payment.setCdepDocfyr(cdepDocfyr);
		//payment.setCdepDocno(cdepDocno);
		//payment.setCdepDate(cdepDate);
		payment.setIsActive(Constants.Yes);
		payment.setAcyymm(acc_Month);
		payment.setCreatedBy(sessionStateManage.getUserName());
		payment.setCreatedDate(new Date());
		payment.setModifiedBy(null);
		payment.setModifiedDate(null);
		payment.setDocYear(getDocumentYear());
		//payment.setCustomerId(getCustomerId());

		BigDecimal documentId=BigDecimal.ZERO;
		BigDecimal companyCode=BigDecimal.ZERO;
		BigDecimal locCode=BigDecimal.ZERO;

		List<Document> listDocument = generalService.getDocument(Constants.DOCUMENT_CODE_FOR_STOPPAYMENT, sessionStateManage.getLanguageId());
		if(listDocument!=null && listDocument.size()>0){
			documentId=listDocument.get(0).getDocumentID();
		}

		List<CompanyMasterDesc> data=generalService.getCompanyList(sessionStateManage.getCompanyId(),new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"));
		if(data!=null && data.size()>0)
		{
			companyCode=data.get(0).getFsCompanyMaster().getCompanyCode();
		}

		payment.setCompanyCode(companyCode);
		payment.setDocumentId(documentId );

		List<CountryBranch> listCode= generalService.getCountryBranchLocCode(new BigDecimal(sessionStateManage.getBranchId()));
		if(listCode!=null && listCode.size()>0){
			locCode	= listCode.get( 0).getBranchId();
		}
		payment.setLocCod( locCode);
		payment.setCountryBranchId(new BigDecimal(sessionStateManage.getBranchId()));
		payment.setPaymentDate(new Date());
		payment.setPaymentmode(getPaymentModeCode());
		
		return payment;
	}

	// exit button
	public void exitButton(){
		try{
			if (sessionStateManage.getRoleId().equalsIgnoreCase("1")) {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
			} else {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
			}
		}catch(Exception exception){
			exception.printStackTrace();
		}
	}
	
	// clear data
	public void clear(){
		setSenderCompanyName(null);
		setSenderCountryName(null);
		setPaymentModeId(null);
		setPaymentModeCode(null);
		setBankToTransfer(null);
	}
	
	// calling procedure to insert in treasury for bank transfer and Cheque
	public void callIntraCompTrnx(Map<String, Object> lstIntraCompData){
		/*P_RECV_CNTRY_CD    IN  VARCHAR2,   --Selecting Country in the screen  
        P_APPL_CNTRY_ID    IN  NUMBER,     -- Login Application Country ID
        P_APPL_COMP_ID     IN  NUMBER,     --Login Company Id 
        P_CNTRY_BRANCH_ID  IN  NUMBER,     -- Login Country Branch 
        P_BANK_ACCT_DET_ID IN  NUMBER,     -- Selected Bank Account No ID 
        P_BANK_ID          IN  NUMBER,     -- Selecting Bank ID in the screen 
        P_PAYMENT_CD       IN  VARCHAR2,   -- Bank/Cheque 
        P_ERROR_MSG        OUT VARCHAR2*/
		
		//HashMap<String, Object> outPutData = 
		
		
	}
	
	// selected remittance records
	public void getSelectedRecordstoRemittance(IntraCompanyBankPaymentDataTable intraBank){
		lstSelectedBankPaymentIntraComp.clear();
		if(lstBankPaymentIntraComp != null && lstBankPaymentIntraComp.size() != 0){
			for (IntraCompanyBankPaymentDataTable lstSelect : lstBankPaymentIntraComp) {
				if(lstSelect.isSelectedRecords()){
					lstSelectedBankPaymentIntraComp.add(lstSelect);
				}
			}
		}
	}



	//	getters and setters	
	public String getSenderCountryName() {
		return senderCountryName;
	}
	public void setSenderCountryName(String senderCountryName) {
		this.senderCountryName = senderCountryName;
	}

	public String getSenderCompanyName() {
		return senderCompanyName;
	}
	public void setSenderCompanyName(String senderCompanyName) {
		this.senderCompanyName = senderCompanyName;
	}

	public BigDecimal getPaymentModeId() {
		return paymentModeId;
	}
	public void setPaymentModeId(BigDecimal paymentModeId) {
		this.paymentModeId = paymentModeId;
	}

	public String getPaymentModeCode() {
		return paymentModeCode;
	}
	public void setPaymentModeCode(String paymentModeCode) {
		this.paymentModeCode = paymentModeCode;
	}

	public String getBankToTransfer() {
		return bankToTransfer;
	}
	public void setBankToTransfer(String bankToTransfer) {
		this.bankToTransfer = bankToTransfer;
	}

	public List<PaymentModeDesc> getLstFetchPaymentMode() {
		return lstFetchPaymentMode;
	}
	public void setLstFetchPaymentMode(List<PaymentModeDesc> lstFetchPaymentMode) {
		this.lstFetchPaymentMode = lstFetchPaymentMode;
	}

	public List<ViewBankDetails> getBankMasterList() {
		return bankMasterList;
	}
	public void setBankMasterList(List<ViewBankDetails> bankMasterList) {
		this.bankMasterList = bankMasterList;
	}

	public List<CountryMasterDesc> getLstFetchSenderCountry() {
		return lstFetchSenderCountry;
	}
	public void setLstFetchSenderCountry(List<CountryMasterDesc> lstFetchSenderCountry) {
		this.lstFetchSenderCountry = lstFetchSenderCountry;
	}
	
	

	public List<RemittanceApplicationView> getLstRemittanceTrnxDetails() {
		return lstRemittanceTrnxDetails;
	}
	public void setLstRemittanceTrnxDetails(List<RemittanceApplicationView> lstRemittanceTrnxDetails) {
		this.lstRemittanceTrnxDetails = lstRemittanceTrnxDetails;
	}

	public String getSenderCountryCode() {
		return senderCountryCode;
	}
	public void setSenderCountryCode(String senderCountryCode) {
		this.senderCountryCode = senderCountryCode;
	}

	public BigDecimal getSenderCountryId() {
		return senderCountryId;
	}
	public void setSenderCountryId(BigDecimal senderCountryId) {
		this.senderCountryId = senderCountryId;
	}

	public String getSenderCompanyCode() {
		return senderCompanyCode;
	}
	public void setSenderCompanyCode(String senderCompanyCode) {
		this.senderCompanyCode = senderCompanyCode;
	}

	public BigDecimal getSenderCompanyId() {
		return senderCompanyId;
	}
	public void setSenderCompanyId(BigDecimal senderCompanyId) {
		this.senderCompanyId = senderCompanyId;
	}

	public List<IntraCompanyBankPaymentDataTable> getLstBankPaymentIntraComp() {
		return lstBankPaymentIntraComp;
	}
	public void setLstBankPaymentIntraComp(List<IntraCompanyBankPaymentDataTable> lstBankPaymentIntraComp) {
		this.lstBankPaymentIntraComp = lstBankPaymentIntraComp;
	}

	public List<IntraCompanyBankPaymentDataTable> getLstSelectedBankPaymentIntraComp() {
		return lstSelectedBankPaymentIntraComp;
	}
	public void setLstSelectedBankPaymentIntraComp(List<IntraCompanyBankPaymentDataTable> lstSelectedBankPaymentIntraComp) {
		this.lstSelectedBankPaymentIntraComp = lstSelectedBankPaymentIntraComp;
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

	public String getReceiptpayment() {
		return receiptpayment;
	}
	public void setReceiptpayment(String receiptpayment) {
		this.receiptpayment = receiptpayment;
	}
	
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

}
