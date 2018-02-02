package com.amg.exchange.cancelreissue.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.cancelreissue.model.ViewRemiitanceInfo;
import com.amg.exchange.cancelreissue.service.ICancelReissueService;
import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.foreigncurrency.service.ForeignLocalCurrencyDenominationService;
import com.amg.exchange.remittance.model.AdditionalInstructionData;
import com.amg.exchange.remittance.model.PurposeOfRemittanceView;
import com.amg.exchange.remittance.model.RemitApplAml;
import com.amg.exchange.remittance.model.RemittanceAppBenificiary;
import com.amg.exchange.remittance.model.RemittanceApplication;
import com.amg.exchange.remittance.model.RemittanceApplicationPurpose;
import com.amg.exchange.remittance.service.IPersonalRemittanceService;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component(value = "cancelReissue")
@Scope("session")
public class CancelReissueBean<T> implements Serializable {
	Logger log = Logger.getLogger(CancelReissueBean.class);
	private static final long serialVersionUID = 1L;

	public BigDecimal appTranxId= null;
	public BigDecimal remitTranxId= null;
	public BigDecimal remitComplaintId= null;
	public BigDecimal receiptPaymentIdnull;
	public BigDecimal collectId= null;
	public BigDecimal collectDetId= null;
	private BigDecimal companyId;
	private String companyName;
	public BigDecimal transferNo = null;
	public BigDecimal remittanceYear = null;
	public String telephone = null;
	public String systemDate = null;
	public BigDecimal applicationRef= null;
	public String applicationYear = null;
	public BigDecimal applicationYearId= null;
	public BigDecimal customerRef= null;
	public BigDecimal customerId= null;
	public String product= null;
	public BigDecimal productId= null;
	public BigDecimal sale= null;
	public BigDecimal purchase= null;
	public String amountFixtType = null;
	public BigDecimal amount = null;

	public BigDecimal saleAmount = null;
	public BigDecimal exchangeRate = null;
	public BigDecimal purchaseAmount = null;
	public BigDecimal commission = null;
	public BigDecimal charges = null;
	public BigDecimal deliveryAmount = null;
	public BigDecimal netAmount = null;

	public String corsbankCode= null;
	public String corsBankName = null;
	public BigDecimal corsBranchId = null;
	public String corsBranchName = null;
	public String benificiaryName = null;
	public BigDecimal branchId= null;
	public String branchName = null;
	public String account = null;
	public String accountPayee = null;
	public String payTo= null;
	public String furtherInst = null;
	public String sourceOfIncome = null;
	public String purposeOfTrnx = null;
	public String sourceOfIncomeName = null;
	public String tel = null;
	public String zip = null;

	private BigDecimal remittanceTransactionId;
	private BigDecimal documentId;
	private Date documentDate;
	private BigDecimal applicationFinYear;
	private BigDecimal applicationDocumentNo;
	private BigDecimal remittanceModeId;
	private BigDecimal deliveryModeId;
	private BigDecimal saleCurrencyId;
	private String saleCurrencyCode;
	private String saleCurrencyDesc;
	private BigDecimal purchaseCurrencyId;
	private String purchaseCurrencyCode;
	private String purchaseCurrencyDesc;
	private BigDecimal exchangeRateApplied;
	private BigDecimal localCurrnecyCommisionId;
	private BigDecimal localChargeCurrnecyId;
	private BigDecimal localChargeAmount;
	private BigDecimal localDeliveryCurrnecyId;
	private BigDecimal localDeliveryAmount;
	private BigDecimal localNetCurrnecyId;
	private BigDecimal bankId;

	private String benificiaryBank;
	private String benificiaryBranch;

	private String benificiaryAccountNo;
	private String instruction;
	private String benificiaryInterBank1;
	private String benificiaryInterBank2;
	private String benificiarySwiftBank1;
	private String benificiarySwiftBank2;
	private BigDecimal remittanceAppId;//Primary key


	private String processIn=Constants.Yes;

	//Old Data variable
	private BigDecimal oldRemittanceCompanyId;
	private BigDecimal oldRemittanceFinyearId;
	private BigDecimal oldRemittanceDocumentId;
	private BigDecimal oldRemittanceDocNo;
	private String intBank =null;
	private BigDecimal pkRemittanceApplicationId =null;
	private BigDecimal pkRemittanceAppBenificiaryId = null;
	private BigDecimal pkRemitApplAmlId =null;
	private BigDecimal pkAdditionalInstructionDataId=null;
	private String errorMsg;
	private Boolean booProcedureDialog=false;
	private BigDecimal transactionNoForDisplay;
	

	@Autowired
	ICancelReissueService<T> cancelReissueSevice;
	@Autowired
	IGeneralService<T> generalService;
	@Autowired
	ForeignLocalCurrencyDenominationService<T> foreignLocalCurrencyDenominationService;
	@Autowired
	IPersonalRemittanceService iPersonalRemittanceService;

	SessionStateManage sessionManage = new SessionStateManage();
	private List<ViewRemiitanceInfo> remittanceDetailsList;
	private String dateFormat = "dd/MM/yyyy";

	public BigDecimal getAppTranxId() {
		return appTranxId;
	}
	public void setAppTranxId(BigDecimal appTranxId) {
		this.appTranxId = appTranxId;
	}
	public BigDecimal getRemitTranxId() {
		return remitTranxId;
	}
	public void setRemitTranxId(BigDecimal remitTranxId) {
		this.remitTranxId = remitTranxId;
	}
	public BigDecimal getRemitComplaintId() {
		return remitComplaintId;
	}
	public void setRemitComplaintId(BigDecimal remitComplaintId) {
		this.remitComplaintId = remitComplaintId;
	}
	public BigDecimal getReceiptPaymentIdnull() {
		return receiptPaymentIdnull;
	}
	public void setReceiptPaymentIdnull(BigDecimal receiptPaymentIdnull) {
		this.receiptPaymentIdnull = receiptPaymentIdnull;
	}
	public BigDecimal getCollectId() {
		return collectId;
	}
	public void setCollectId(BigDecimal collectId) {
		this.collectId = collectId;
	}
	public BigDecimal getCollectDetId() {
		return collectDetId;
	}
	public void setCollectDetId(BigDecimal collectDetId) {
		this.collectDetId = collectDetId;
	}

	public BigDecimal getCompanyId() {
		return companyId;
	}
	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public BigDecimal getTransferNo() {
		return transferNo;
	}
	public void setTransferNo(BigDecimal transferNo) {
		this.transferNo = transferNo;
	}

	public BigDecimal getRemittanceYear() {
		return remittanceYear;
	}
	public void setRemittanceYear(BigDecimal remittanceYear) {
		this.remittanceYear = remittanceYear;
	}
	public String getApplicationYear() {
		return applicationYear;
	}
	public void setApplicationYear(String applicationYear) {
		this.applicationYear = applicationYear;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getSystemDate() {
		return systemDate;
	}
	public void setSystemDate(String systemDate) {
		this.systemDate = systemDate;
	}
	public BigDecimal getApplicationRef() {
		return applicationRef;
	}
	public void setApplicationRef(BigDecimal applicationRef) {
		this.applicationRef = applicationRef;
	}
	public BigDecimal getApplicationYearId() {
		return applicationYearId;
	}
	public void setApplicationYearId(BigDecimal applicationYearId) {
		this.applicationYearId = applicationYearId;
	}
	public BigDecimal getCustomerRef() {
		return customerRef;
	}
	public void setCustomerRef(BigDecimal customerRef) {
		this.customerRef = customerRef;
	}

	public BigDecimal getCustomerId() {
		return customerId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public BigDecimal getProductId() {
		return productId;
	}
	public void setProductId(BigDecimal productId) {
		this.productId = productId;
	}
	public BigDecimal getSale() {
		return sale;
	}
	public void setSale(BigDecimal sale) {
		this.sale = sale;
	}
	public BigDecimal getPurchase() {
		return purchase;
	}
	public void setPurchase(BigDecimal purchase) {
		this.purchase = purchase;
	}
	public String getAmountFixtType() {
		return amountFixtType;
	}
	public void setAmountFixtType(String amountFixtType) {
		this.amountFixtType = amountFixtType;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getSaleAmount() {
		return saleAmount;
	}
	public void setSaleAmount(BigDecimal saleAmount) {
		this.saleAmount = saleAmount;
	}
	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	public BigDecimal getPurchaseAmount() {
		return purchaseAmount;
	}
	public void setPurchaseAmount(BigDecimal purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
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
	public BigDecimal getDeliveryAmount() {
		return deliveryAmount;
	}
	public void setDeliveryAmount(BigDecimal deliveryAmount) {
		this.deliveryAmount = deliveryAmount;
	}
	public BigDecimal getNetAmount() {
		return netAmount;
	}
	public void setNetAmount(BigDecimal netAmount) {
		this.netAmount = netAmount;
	}
	public String getCorsbankCode() {
		return corsbankCode;
	}
	public void setCorsbankCode(String corsbankCode) {
		this.corsbankCode = corsbankCode;
	}
	public BigDecimal getCorsBranchId() {
		return corsBranchId;
	}
	public void setCorsBranchId(BigDecimal corsBranchId) {
		this.corsBranchId = corsBranchId;
	}
	public String getBenificiaryName() {
		return benificiaryName;
	}
	public void setBenificiaryName(String benificiaryName) {
		this.benificiaryName = benificiaryName;
	}
	public BigDecimal getBranchId() {
		return branchId;
	}
	public void setBranchId(BigDecimal branchId) {
		this.branchId = branchId;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getAccountPayee() {
		return accountPayee;
	}
	public void setAccountPayee(String accountPayee) {
		this.accountPayee = accountPayee;
	}
	public String getPayTo() {
		return payTo;
	}
	public void setPayTo(String payTo) {
		this.payTo = payTo;
	}
	public String getFurtherInst() {
		return furtherInst;
	}
	public void setFurtherInst(String furtherInst) {
		this.furtherInst = furtherInst;
	}
	public String getSourceOfIncome() {
		return sourceOfIncome;
	}
	public void setSourceOfIncome(String sourceOfIncome) {
		this.sourceOfIncome = sourceOfIncome;
	}
	public String getPurposeOfTrnx() {
		return purposeOfTrnx;
	}
	public void setPurposeOfTrnx(String purposeOfTrnx) {
		this.purposeOfTrnx = purposeOfTrnx;
	}
	public String getSourceOfIncomeName() {
		return sourceOfIncomeName;
	}
	public void setSourceOfIncomeName(String sourceOfIncomeName) {
		this.sourceOfIncomeName = sourceOfIncomeName;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCorsBankName() {
		return corsBankName;
	}
	public void setCorsBankName(String corsBankName) {
		this.corsBankName = corsBankName;
	}
	public String getCorsBranchName() {
		return corsBranchName;
	}
	public void setCorsBranchName(String corsBranchName) {
		this.corsBranchName = corsBranchName;
	}
	public BigDecimal getRemittanceTransactionId() {
		return remittanceTransactionId;
	}
	public void setRemittanceTransactionId(BigDecimal remittanceTransactionId) {
		this.remittanceTransactionId = remittanceTransactionId;
	}
	public BigDecimal getDocumentId() {
		return documentId;
	}
	public void setDocumentId(BigDecimal documentId) {
		this.documentId = documentId;
	}
	public Date getDocumentDate() {
		return documentDate;
	}
	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}
	public BigDecimal getApplicationFinYear() {
		return applicationFinYear;
	}
	public void setApplicationFinYear(BigDecimal applicationFinYear) {
		this.applicationFinYear = applicationFinYear;
	}
	public BigDecimal getApplicationDocumentNo() {
		return applicationDocumentNo;
	}
	public void setApplicationDocumentNo(BigDecimal applicationDocumentNo) {
		this.applicationDocumentNo = applicationDocumentNo;
	}
	public BigDecimal getRemittanceModeId() {
		return remittanceModeId;
	}
	public void setRemittanceModeId(BigDecimal remittanceModeId) {
		this.remittanceModeId = remittanceModeId;
	}
	public BigDecimal getDeliveryModeId() {
		return deliveryModeId;
	}
	public void setDeliveryModeId(BigDecimal deliveryModeId) {
		this.deliveryModeId = deliveryModeId;
	}
	public BigDecimal getSaleCurrencyId() {
		return saleCurrencyId;
	}
	public void setSaleCurrencyId(BigDecimal saleCurrencyId) {
		this.saleCurrencyId = saleCurrencyId;
	}
	public String getSaleCurrencyCode() {
		return saleCurrencyCode;
	}
	public void setSaleCurrencyCode(String saleCurrencyCode) {
		this.saleCurrencyCode = saleCurrencyCode;
	}
	public String getSaleCurrencyDesc() {
		return saleCurrencyDesc;
	}
	public void setSaleCurrencyDesc(String saleCurrencyDesc) {
		this.saleCurrencyDesc = saleCurrencyDesc;
	}
	public BigDecimal getPurchaseCurrencyId() {
		return purchaseCurrencyId;
	}
	public void setPurchaseCurrencyId(BigDecimal purchaseCurrencyId) {
		this.purchaseCurrencyId = purchaseCurrencyId;
	}
	public String getPurchaseCurrencyCode() {
		return purchaseCurrencyCode;
	}
	public void setPurchaseCurrencyCode(String purchaseCurrencyCode) {
		this.purchaseCurrencyCode = purchaseCurrencyCode;
	}
	public String getPurchaseCurrencyDesc() {
		return purchaseCurrencyDesc;
	}
	public void setPurchaseCurrencyDesc(String purchaseCurrencyDesc) {
		this.purchaseCurrencyDesc = purchaseCurrencyDesc;
	}
	public BigDecimal getExchangeRateApplied() {
		return exchangeRateApplied;
	}
	public void setExchangeRateApplied(BigDecimal exchangeRateApplied) {
		this.exchangeRateApplied = exchangeRateApplied;
	}
	public BigDecimal getLocalCurrnecyCommisionId() {
		return localCurrnecyCommisionId;
	}
	public void setLocalCurrnecyCommisionId(BigDecimal localCurrnecyCommisionId) {
		this.localCurrnecyCommisionId = localCurrnecyCommisionId;
	}
	public BigDecimal getLocalChargeCurrnecyId() {
		return localChargeCurrnecyId;
	}
	public void setLocalChargeCurrnecyId(BigDecimal localChargeCurrnecyId) {
		this.localChargeCurrnecyId = localChargeCurrnecyId;
	}
	public BigDecimal getLocalChargeAmount() {
		return localChargeAmount;
	}
	public void setLocalChargeAmount(BigDecimal localChargeAmount) {
		this.localChargeAmount = localChargeAmount;
	}
	public BigDecimal getLocalDeliveryCurrnecyId() {
		return localDeliveryCurrnecyId;
	}
	public void setLocalDeliveryCurrnecyId(BigDecimal localDeliveryCurrnecyId) {
		this.localDeliveryCurrnecyId = localDeliveryCurrnecyId;
	}
	public BigDecimal getLocalDeliveryAmount() {
		return localDeliveryAmount;
	}
	public void setLocalDeliveryAmount(BigDecimal localDeliveryAmount) {
		this.localDeliveryAmount = localDeliveryAmount;
	}
	public BigDecimal getLocalNetCurrnecyId() {
		return localNetCurrnecyId;
	}
	public void setLocalNetCurrnecyId(BigDecimal localNetCurrnecyId) {
		this.localNetCurrnecyId = localNetCurrnecyId;
	}
	public BigDecimal getBankId() {
		return bankId;
	}
	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}
	public String getBenificiaryBank() {
		return benificiaryBank;
	}
	public void setBenificiaryBank(String benificiaryBank) {
		this.benificiaryBank = benificiaryBank;
	}
	public String getBenificiaryBranch() {
		return benificiaryBranch;
	}
	public void setBenificiaryBranch(String benificiaryBranch) {
		this.benificiaryBranch = benificiaryBranch;
	}
	public String getBenificiaryAccountNo() {
		return benificiaryAccountNo;
	}
	public void setBenificiaryAccountNo(String benificiaryAccountNo) {
		this.benificiaryAccountNo = benificiaryAccountNo;
	}
	public String getInstruction() {
		return instruction;
	}
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
	public String getBenificiaryInterBank1() {
		return benificiaryInterBank1;
	}
	public void setBenificiaryInterBank1(String benificiaryInterBank1) {
		this.benificiaryInterBank1 = benificiaryInterBank1;
	}
	public String getBenificiaryInterBank2() {
		return benificiaryInterBank2;
	}
	public void setBenificiaryInterBank2(String benificiaryInterBank2) {
		this.benificiaryInterBank2 = benificiaryInterBank2;
	}
	public String getBenificiarySwiftBank1() {
		return benificiarySwiftBank1;
	}
	public void setBenificiarySwiftBank1(String benificiarySwiftBank1) {
		this.benificiarySwiftBank1 = benificiarySwiftBank1;
	}
	public String getBenificiarySwiftBank2() {
		return benificiarySwiftBank2;
	}
	public void setBenificiarySwiftBank2(String benificiarySwiftBank2) {
		this.benificiarySwiftBank2 = benificiarySwiftBank2;
	}
	public ICancelReissueService<T> getCancelReissueSevice() {
		return cancelReissueSevice;
	}
	public void setCancelReissueSevice(ICancelReissueService<T> cancelReissueSevice) {
		this.cancelReissueSevice = cancelReissueSevice;
	}
	public IGeneralService<T> getGeneralService() {
		return generalService;
	}
	public void setGeneralService(IGeneralService<T> generalService) {
		this.generalService = generalService;
	}
	public SessionStateManage getSessionManage() {
		return sessionManage;
	}
	public void setSessionManage(SessionStateManage sessionManage) {
		this.sessionManage = sessionManage;
	}
	public List<ViewRemiitanceInfo> getRemittanceDetailsList() {
		return remittanceDetailsList;
	}
	public void setRemittanceDetailsList(List<ViewRemiitanceInfo> remittanceDetailsList) {
		this.remittanceDetailsList = remittanceDetailsList;
	}
	public String getDateFormat() {
		return dateFormat;
	}
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
	public BigDecimal getRemittanceAppId() {
		return remittanceAppId;
	}
	public void setRemittanceAppId(BigDecimal remittanceAppId) {
		this.remittanceAppId = remittanceAppId;
	}

	public BigDecimal getOldRemittanceCompanyId() {
		return oldRemittanceCompanyId;
	}
	public void setOldRemittanceCompanyId(BigDecimal oldRemittanceCompanyId) {
		this.oldRemittanceCompanyId = oldRemittanceCompanyId;
	}
	public BigDecimal getOldRemittanceFinyearId() {
		return oldRemittanceFinyearId;
	}
	public void setOldRemittanceFinyearId(BigDecimal oldRemittanceFinyearId) {
		this.oldRemittanceFinyearId = oldRemittanceFinyearId;
	}
	public BigDecimal getOldRemittanceDocumentId() {
		return oldRemittanceDocumentId;
	}
	public void setOldRemittanceDocumentId(BigDecimal oldRemittanceDocumentId) {
		this.oldRemittanceDocumentId = oldRemittanceDocumentId;
	}
	public BigDecimal getOldRemittanceDocNo() {
		return oldRemittanceDocNo;
	}
	public void setOldRemittanceDocNo(BigDecimal oldRemittanceDocNo) {
		this.oldRemittanceDocNo = oldRemittanceDocNo;
	}

	public String getIntBank() {
		return intBank;
	}
	public void setIntBank(String intBank) {
		this.intBank = intBank;
	}

	public BigDecimal getPkRemittanceApplicationId() {
		return pkRemittanceApplicationId;
	}
	public void setPkRemittanceApplicationId(BigDecimal pkRemittanceApplicationId) {
		this.pkRemittanceApplicationId = pkRemittanceApplicationId;
	}
	public BigDecimal getPkRemittanceAppBenificiaryId() {
		return pkRemittanceAppBenificiaryId;
	}
	public void setPkRemittanceAppBenificiaryId(
			BigDecimal pkRemittanceAppBenificiaryId) {
		this.pkRemittanceAppBenificiaryId = pkRemittanceAppBenificiaryId;
	}
	public BigDecimal getPkRemitApplAmlId() {
		return pkRemitApplAmlId;
	}
	public void setPkRemitApplAmlId(BigDecimal pkRemitApplAmlId) {
		this.pkRemitApplAmlId = pkRemitApplAmlId;
	}
	public BigDecimal getPkAdditionalInstructionDataId() {
		return pkAdditionalInstructionDataId;
	}
	public void setPkAdditionalInstructionDataId(
			BigDecimal pkAdditionalInstructionDataId) {
		this.pkAdditionalInstructionDataId = pkAdditionalInstructionDataId;
	}

	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public void nextApplicationNo() {
		String docNo=getDocumentSerialID(processIn);
		if(docNo.equalsIgnoreCase("0")){
			setApplicationRef(BigDecimal.ZERO);
		}else{
			setApplicationRef(new BigDecimal(docNo));
		}


	}

	public String getDocumentSerialID(String processIn){

		try{
			HashMap<String, String> outPutValues = generalService.getNextDocumentRefNumber(sessionManage.getCountryId().intValue() , sessionManage.getCompanyId().intValue(), Integer.parseInt(Constants.DOCUMENT_CODE_FOR_CANCELLATION_REISSUE) , new BigDecimal(getApplicationYear()).intValue(),processIn,sessionManage.getCountryBranchCode());
			log.info( "====docno==="+outPutValues.get("DOCNO"));
			String proceErrorMsg=outPutValues.get("PROCE_ERORRMSG");
			if(proceErrorMsg!=null)
			{
				setBooProcedureDialog(true);
				setErrorMsg(proceErrorMsg );
				RequestContext.getCurrentInstance().execute("proceErr.show();");
				return "0";

			}else{
				setBooProcedureDialog(false);
				String documentSerialID=outPutValues.get("DOCNO");
				return documentSerialID;

			}


		}catch(NumberFormatException | AMGException e){
			setErrorMsg(e.getMessage() );
			RequestContext.getCurrentInstance().execute("proceErr.show();");
			return "0";
		}



		//String documentSerialID = generalService.getNextDocumentReferenceNumber(sessionManage.getCountryId().intValue() , sessionManage.getCompanyId().intValue(), Integer.parseInt(Constants.DOCUMENT_CODE_FOR_CANCELLATION_REISSUE) , new BigDecimal(getApplicationYear()).intValue(),processIn,sessionManage.getCountryBranchCode());
		//return documentSerialID;
	}


	public void getApplicationYearFromdb() {

		try{
			List<UserFinancialYear> applicationYearList = generalService.getDealYear(new Date());
			if(applicationYearList.size()>0){
				setApplicationYear(applicationYearList.get(0).getFinancialYear().toString());
				setApplicationYearId(applicationYearList.get(0).getFinancialYearID());
			}
		}catch(Exception e){
			e.printStackTrace();	
		}
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void redirectToPage(){
		clear();
 
		setCompanyName(null);
		setCompanyId(null);
		setSystemDate(null);
		setApplicationYear(null);
		setCompanyName(cancelReissueSevice.getCompanyName(sessionManage.getCompanyId(), sessionManage.getLanguageId()));
		setSystemDate(new SimpleDateFormat(dateFormat).format(new Date()));
		getApplicationYearFromdb();
		nextApplicationNo();

		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionManage.getCountryId(), sessionManage.getUserType(), sessionManage.getUserName(), "cancelreissue.xhtml");
			ExternalContext context = FacesContext.getCurrentInstance()
					.getExternalContext();
			context.redirect("../cancelreissue/cancelreissue.xhtml");
		} catch (Exception e) {
			log.info("Problem to redirect: " + e);
		}
	}

	public void clear(){
		setTransferNo(null);
		setAppTranxId(null);
		setRemitTranxId(null);
		setRemitComplaintId(null);
		setReceiptPaymentIdnull(null);
		setCollectId(null);
		setCollectDetId(null);
		setTransferNo(null);
		setRemittanceYear(null);
		setTelephone(null);
		setProduct(null);
		setProductId(null);
		setSale(null);
		setCustomerId(null);
		setCustomerRef(null);
		setSaleCurrencyCode(null);
		setSaleCurrencyDesc(null);
		setSaleCurrencyId(null);
		setPurchaseCurrencyId(null);
		setPurchaseCurrencyCode(null);
		setPurchaseCurrencyDesc(null);
		setPurchase(null);
		setAmountFixtType(null);
		setAmount(null);
		setSaleAmount(null);
		setExchangeRate(null);
		setPurchaseAmount(null);
		setCommission(null);
		setCharges(null);
		setDeliveryAmount(null);
		setNetAmount(null);
		setCorsbankCode(null);
		setCorsBranchId(null);
		setBenificiaryName(null);
		setBranchId(null);
		setBranchName(null);
		setAccount(null);
		setAccountPayee(null);
		setPayTo(null);
		setFurtherInst(null);
		setSourceOfIncome(null);
		setPurposeOfTrnx(null);
		setSourceOfIncomeName(null);
		setTel(null);
		setZip(null);
		setOldRemittanceCompanyId(null);
		setOldRemittanceDocNo(null);
		setOldRemittanceDocumentId(null);
		setOldRemittanceFinyearId(null);
		setIntBank(null);
		setCorsbankCode(null);
		setCorsBranchName(null);
		setApplicationDocumentNo(null);
		setApplicationFinYear(null);
		setPkAdditionalInstructionDataId(null);
		setPkRemitApplAmlId(null);
		setPkRemittanceAppBenificiaryId(null);
		setPkRemittanceApplicationId(null);
		setErrorMsg(null);
		setCorsBankName(null);
		setBenificiaryBank(null);
	 
		
	}

	public void fetchRemiitanceView(){
		try{
			
			String purposeOfRemittance = "";
	 
			remittanceDetailsList = cancelReissueSevice.fetchRemiitanceDetails(sessionManage.getCountryId(), sessionManage.getCompanyId(), new BigDecimal(Constants.DOCUMENT_CODE_FOR_CANCEL_REISSUE), getRemittanceYear(), getTransferNo());
			if(remittanceDetailsList.size()>0){
				ViewRemiitanceInfo getViewData = remittanceDetailsList.get(0);
		 
				setRemittanceTransactionId(getViewData.getRemittanceTransactionId());
				setCustomerRef(getViewData.getCustomerRefNo());
				setTelephone(getViewData.getBeneficiaryTelephone());
				setCustomerRef(getViewData.getCustomerRefNo());
				setCustomerId(getViewData.getCustomerId());
				setProduct(getViewData.getServiceDroupDesc());
				setProductId(getViewData.getServiceGroupID());
				//setSale(getViewData.gets);
				setSaleCurrencyCode(getViewData.getLocalCurrencyCode());
				setSaleCurrencyDesc(getViewData.getLocalCurrencyDesc());
				setSaleCurrencyId(getViewData.getLocalCurrnecyId());

				setPurchaseCurrencyId(getViewData.getForeignCurrencyId());
				setPurchaseCurrencyCode(getViewData.getForeignCurrencyCode());
				setPurchaseCurrencyDesc(getViewData.getForeignCurrencyDesc());
				/*setPurchase(null);
			      setAmountFixtType(null);*/
				setAmount(getViewData.getForeignTrxAmount());
				//setSaleAmount(getViewData.getSaleAmount());
				setSaleAmount(round(getViewData.getForeignTrxAmount(),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(getSaleCurrencyId())));
				setExchangeRate(getViewData.getExchangeRateApplied());
				//setPurchaseAmount(getViewData.getPurchaseAmount());
				setPurchaseAmount(round(getViewData.getLocalAmount(),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(getPurchaseCurrencyId())));
				//setCommission(getViewData.getLocalCommisionAmount());
				setCommission(round(getViewData.getLocalCommisionAmount(),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(getPurchaseCurrencyId())));
				//setCharges(getViewData.getLocalChargeAmount());
				setCharges(round(getViewData.getLocalChargeAmount(),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(getPurchaseCurrencyId())));
				setDeliveryAmount(getViewData.getLocalDeliveryAmount());
				//setNetAmount(getViewData.getLocalNetTrxAmount());
				setNetAmount(round(getViewData.getLocalNetTrxAmount(),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(getPurchaseCurrencyId())));
				setCorsbankCode(getViewData.getBankCode());
				setCorsBankName(getViewData.getCorrespondingBankName());
				setCorsBranchId(getViewData.getBankBranchCode());
				setCorsBranchName(getViewData.getBranchName());
				setBenificiaryName(getViewData.getBenificiaryName());
				//setBranchId(getViewData.getbe);
				setBranchName(getViewData.getBenificiaryBranch());
				setBenificiaryBank(getViewData.getBenificiaryBank());
				setAccount(getViewData.getBenificiaryAccountNo());
				setPayTo(getViewData.getPayTo());
				try {
					String instruction=cancelReissueSevice.getFutherInstruction(getViewData.getRemittanceTransactionId());
					setFurtherInst(instruction);
				} catch (AMGException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				setBenificiarySwiftBank1(getViewData.getBenificiarySwiftBank1());
				setBenificiaryInterBank1(getViewData.getBenificiaryInterBank1());
				setBenificiarySwiftBank2(getViewData.getBenificiarySwiftBank2());
				setBenificiaryInterBank2(getViewData.getBenificiaryInterBank2());
				//setIntBank(getViewData.);
				if(getViewData.getSourceOfIncome()!=null){
					setSourceOfIncome(getViewData.getSourceOfIncome().toPlainString());
					setSourceOfIncomeName(getViewData.getSourceOfIncomeDesc());
				}
				
				List<PurposeOfRemittanceView>  purposeOfRemittanceList =   iPersonalRemittanceService.getPurposeOfRemittance(getViewData.getDocumentNo(),getViewData.getDocumentFinYear());

				for(PurposeOfRemittanceView purposeObj :purposeOfRemittanceList){
					if(purposeOfRemittance.equals("")){
						purposeOfRemittance  = purposeObj.getFlexiFieldValue();
					}else{
						purposeOfRemittance  = purposeOfRemittance+", "+purposeObj.getFlexiFieldValue();
					}
				}
				
				setPurposeOfTrnx(purposeOfRemittance);
				setOldRemittanceCompanyId(getViewData.getCompanyId());
				setOldRemittanceDocNo(getViewData.getDocumentNo());
				setOldRemittanceDocumentId(getViewData.getDocumentId());
				setOldRemittanceFinyearId(getViewData.getDocumentFinYear());
				setTel(getViewData.getBeneficiaryTelephone());
				setIntBank(getViewData.getBeneficiaryAddress());
				setApplicationDocumentNo(getViewData.getApplicationDocumentNo());
				setApplicationFinYear(getViewData.getApplicationFinYear());
				setZip(null);

			}else{
				clearCall();
				RequestContext.getCurrentInstance().execute("datanotfound.show();");
			}
		}catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMsg("Method Name::fetchRemiitanceView"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 

		} catch (Exception e) {
			log.error("Method Name::fetchRemiitanceView", e);
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("saveerror.show();");
			return;
		}
	}

	public void saveRemitTansaction(){
		try{
			if(getOldRemittanceDocNo()!=null&&getOldRemittanceDocumentId()!=null&&getCustomerRef()!=null){
			HashMap<String, String> mapProcedureData = new HashMap<String,String>();
			
			mapProcedureData.put("ApplicationCountryID", sessionManage.getCountryId().toString());
 			mapProcedureData.put("OldRemittanceCompanyID", getOldRemittanceCompanyId().toString());
			mapProcedureData.put("OldRemittanceDocumentID", getOldRemittanceDocumentId().toString());
			mapProcedureData.put("OldRemittanceDocumentFinancialYear", getOldRemittanceFinyearId().toString());
			mapProcedureData.put("OldRemittanceDocumentNo", getOldRemittanceDocNo().toString());
 			mapProcedureData.put("CountryBranchID",  sessionManage.getBranchId());
 			mapProcedureData.put("loginUser",  sessionManage.getUserName());
				
 			BigDecimal docno=cancelReissueSevice.procedureCallForSave(mapProcedureData);
 			setTransactionNoForDisplay(docno);
 			System.out.println("============================"+docno);
 			clear();
			RequestContext.getCurrentInstance().execute("complete.show();");
 
			}else{
				RequestContext.getCurrentInstance().execute("notavailabledata.show();");
			}
			}catch(NullPointerException ne){
				ne.printStackTrace();
				log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				setErrorMsg("Method Name::saveRemitTansaction"); 
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return; 

			} catch (Exception e) {
				log.error("Method Name::saveRemitTansaction", e);
				setErrorMsg(e.getMessage());
				RequestContext.getCurrentInstance().execute("saveerror.show();");
				return;
			}

		

	}

	public AdditionalInstructionData saveRemitAdditionalData(){

		List<AdditionalInstructionData> additionalList = cancelReissueSevice.remittanceAppAdditionalDataList(getRemittanceAppId());
		AdditionalInstructionData additionalData = new AdditionalInstructionData();
		if(additionalList.size()>0){
			//try{
			AdditionalInstructionData getData = additionalList.get(0);
			additionalData.setAdditionalBankFieldsId(getData.getAdditionalBankFieldsId());
			additionalData.setApprovedBy(sessionManage.getUserName());
			additionalData.setApprovedDate(new Date());
			additionalData.setCreatedBy(sessionManage.getUserName());
			additionalData.setCreatedDate(new Date());
			/*additionalData.setModifiedBy(sessionManage.getUserName());
			additionalData.setModifiedDate(new Date());*/
			additionalData.setDocumentFinanceYear(getData.getDocumentFinanceYear());
			additionalData.setDocumentNo(getApplicationRef());
			additionalData.setAmiecCode(getData.getAmiecCode() );

			if(getData.getExDocument()!=null){
				Document document = new Document();
				document.setDocumentID(getData.getExDocument().getDocumentID());
				additionalData.setExDocument(document);
			}

			RemittanceApplication remiApp = new RemittanceApplication();
			remiApp.setRemittanceApplicationId(getRemittanceAppId());
			additionalData.setExRemittanceApplication(remiApp);
			if(getData.getExUserFinancialYear()!=null){
				UserFinancialYear userFinyear = new UserFinancialYear();
				userFinyear.setFinancialYearID(getData.getExUserFinancialYear().getFinancialYearID());
				additionalData.setExUserFinancialYear(userFinyear);
			}
			additionalData.setFlexField(getData.getFlexField());
			additionalData.setFlexFieldValue(getData.getFlexFieldValue());
			additionalData.setIsactive(getData.getIsactive());
			if(getData.getFsCompanyMaster()!=null){
				CompanyMaster compMaster = new CompanyMaster();
				compMaster.setCompanyId(sessionManage.getCompanyId());
				additionalData.setFsCompanyMaster(compMaster);
			}
			CountryMaster countryMaster = new CountryMaster();
			countryMaster.setCountryId(getData.getFsCountryMaster().getCountryId());
			additionalData.setFsCountryMaster(countryMaster);

			/*generalService.saveOrUpdate((T) additionalData);
			} catch (Exception e) {
				log.error("Exception Occured While saving Data", e);
				RequestContext.getCurrentInstance().execute("saveerror.show();");
			} */
		}
		return additionalData;
	}

	public RemitApplAml saveRemitApplAml(){

		List<RemitApplAml> listRemitAml = cancelReissueSevice.remittanceAppAmlList(getRemittanceAppId());
		RemitApplAml remitAml = new RemitApplAml();
		if(listRemitAml.size()>0){
			//try{

			RemitApplAml getAmlData = listRemitAml.get(0);
			remitAml.setBlackClearedUser(getAmlData.getBlackClearedUser());
			remitAml.setBlackListClear(getAmlData.getBlackListClear());
			remitAml.setBlackListDate(getAmlData.getBlackListDate());
			remitAml.setBlackListReason(getAmlData.getBlackListReason());
			remitAml.setBlackListRemarks(getAmlData.getBlackListRemarks());
			remitAml.setBlackListUser(getAmlData.getBlackListUser());
			remitAml.setCreatedBy(sessionManage.getUserName());
			remitAml.setCreatedDate(new Date());
			remitAml.setCustomerSignature(getAmlData.getCustomerSignature());
			remitAml.setIsactive(getAmlData.getIsactive());
			RemittanceApplication remitApplication = new RemittanceApplication();
			remitApplication.setRemittanceApplicationId(getRemittanceAppId());
			remitAml.setExRemittanceAppfromAml(remitApplication);
			CompanyMaster companyMaster = new CompanyMaster();
			companyMaster.setCompanyId(sessionManage.getCompanyId());
			remitAml.setFsCompanyMaster(companyMaster);
			CountryMaster countryMaster = new CountryMaster();
			countryMaster.setCountryId(getAmlData.getFsCountryMaster().getCountryId());
			remitAml.setFsCountryMaster(countryMaster);
			remitAml.setAuthorizedBy(getAmlData.getAuthorizedBy());
			remitAml.setAuthType(getAmlData.getAuthType());

			/*	generalService.saveOrUpdate((T) remitAml);	
			} catch (Exception e) {
				log.error("Exception Occured While saving Data", e);
				RequestContext.getCurrentInstance().execute("saveerror.show();");
			}*/

		}
		return remitAml;
	}


	public RemittanceAppBenificiary saveRemittanceAppBenificiary(){
		List<RemittanceAppBenificiary> listRemitAml = cancelReissueSevice.remittanceApplicationBeneficiaryList(getRemittanceAppId());
		RemittanceAppBenificiary remiBene = new RemittanceAppBenificiary();
		if(listRemitAml.size()>0){
			//try{

			RemittanceAppBenificiary getBeneData = listRemitAml.get(0);
			remiBene.setBeneficiaryAccountNo(getBeneData.getBeneficiaryAccountNo());
			remiBene.setBeneficiaryBank(getBeneData.getBeneficiaryBank());
			remiBene.setBeneficiaryBranch(getBeneData.getBeneficiaryBranch());
			remiBene.setBeneficiaryFirstName(getBeneData.getBeneficiaryFirstName());
			remiBene.setBeneficiaryFourthName(getBeneData.getBeneficiaryFourthName());
			remiBene.setBeneficiarySwiftAddr1(getBeneData.getBeneficiarySwiftAddr1());
			remiBene.setBeneficiarySwiftAddr1(getBeneData.getBeneficiarySwiftAddr2());
			remiBene.setBeneficiaryName(getBeneData.getBeneficiaryName());
			remiBene.setBeneficiarySecondName(getBeneData.getBeneficiarySecondName());
			remiBene.setBeneficiarySwiftBank1(getBeneData.getBeneficiarySwiftBank1());
			remiBene.setBeneficiarySwiftBank2(getBeneData.getBeneficiarySwiftBank2());
			remiBene.setBeneficiaryThirdName(getBeneData.getBeneficiaryThirdName());
			remiBene.setCompanyCode(getBeneData.getCompanyCode());
			remiBene.setCreatedBy(sessionManage.getUserName());
			remiBene.setCreatedDate(new Date());
			//remiBene.setModifiedBy(sessionManage.getUserName());
			//remiBene.setModifiedDate(new Date());
			remiBene.setDocumentCode(getBeneData.getDocumentCode());
			remiBene.setBeneficiaryId(getBeneData.getBeneficiaryId());

			if(getBeneData.getExDocument()!=null){
				Document document = new Document();
				document.setDocumentID(getBeneData.getExDocument().getDocumentID());
				remiBene.setExDocument(document);
			}

			RemittanceApplication remiApp = new RemittanceApplication();
			remiApp.setRemittanceApplicationId(getRemittanceAppId());
			remiBene.setExRemittanceAppfromBenfi(remiApp);
			if(getBeneData.getExUserFinancialYear()!=null){
				UserFinancialYear userFinyear = new UserFinancialYear();
				userFinyear.setFinancialYearID(getBeneData.getExUserFinancialYear().getFinancialYearID());
				remiBene.setExUserFinancialYear(userFinyear);
			}
			CompanyMaster companyMaster = new CompanyMaster();
			companyMaster.setCompanyId(sessionManage.getCompanyId());
			remiBene.setFsCompanyMaster(companyMaster);
			remiBene.setIsactive(getBeneData.getIsactive());
			remiBene.setBeneficiaryBankId(getBeneData.getBeneficiaryBankId());
			remiBene.setBeneficiaryBankBranchId(getBeneData.getBeneficiaryBankBranchId());
			remiBene.setBeneficiaryBranchStateId(getBeneData.getBeneficiaryBranchStateId());
			remiBene.setBeneficiaryBranchDistrictId(getBeneData.getBeneficiaryBranchDistrictId());
			remiBene.setBeneficiaryBranchCityId(getBeneData.getBeneficiaryBranchCityId());
			remiBene.setBeneficiaryBankSwift(getBeneData.getBeneficiaryBankSwift());
			remiBene.setBeneficiaryFifthName(getBeneData.getBeneficiaryFifthName());
			remiBene.setBeneficiarySwiftBank1Id(getBeneData.getBeneficiarySwiftBank1Id());
			remiBene.setBeneficiarySwiftBank2Id(getBeneData.getBeneficiarySwiftBank2Id());
			remiBene.setBeneficiaryAccountSeqId(getBeneData.getBeneficiaryAccountSeqId());
			remiBene.setBeneficiaryRelationShipSeqId(getBeneData.getBeneficiaryRelationShipSeqId());
			remiBene.setBeneficiaryBankCountryId(getBeneData.getBeneficiaryBankCountryId());
			remiBene.setDocumentNo( getApplicationRef());
			remiBene.setBeneficiaryTelephoneNumber(getBeneData.getBeneficiaryTelephoneNumber() );
			/*generalService.saveOrUpdate((T) remiBene);
			} catch (Exception e) {
				log.error("Exception Occured While saving Data", e);
				RequestContext.getCurrentInstance().execute("saveerror.show();");
			}*/
		}
		return remiBene;
	}

	public void exit() throws IOException {
		if (sessionManage.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}
	}

	public void clearCall(){
		setAppTranxId(null);
		setRemitTranxId(null);
		setRemitComplaintId(null);
		setReceiptPaymentIdnull(null);
		setCollectId(null);
		setCollectDetId(null);
		setTelephone(null);
		setProduct(null);
		setProductId(null);
		setSale(null);
		setCustomerId(null);
		setCustomerRef(null);
		setSaleCurrencyCode(null);
		setSaleCurrencyDesc(null);
		setSaleCurrencyId(null);
		setPurchaseCurrencyId(null);
		setPurchaseCurrencyCode(null);
		setPurchaseCurrencyDesc(null);
		setPurchase(null);
		setAmountFixtType(null);
		setAmount(null);
		setSaleAmount(null);
		setExchangeRate(null);
		setPurchaseAmount(null);
		setCommission(null);
		setCharges(null);
		setDeliveryAmount(null);
		setNetAmount(null);
		setCorsbankCode(null);
		setCorsBranchId(null);
		setBenificiaryName(null);
		setBranchId(null);
		setBranchName(null);
		setAccount(null);
		setAccountPayee(null);
		setPayTo(null);
		setFurtherInst(null);
		setSourceOfIncome(null);
		setPurposeOfTrnx(null);
		setSourceOfIncomeName(null);
		setTel(null);
		setZip(null);
		setOldRemittanceCompanyId(null);
		setOldRemittanceDocNo(null);
		setOldRemittanceDocumentId(null);
		setOldRemittanceFinyearId(null);
		setIntBank(null);
		setCorsbankCode(null);
		setCorsBranchName(null);
		setApplicationDocumentNo(null);
		setApplicationFinYear(null);
		setRemittanceDetailsList(null);
		setPkAdditionalInstructionDataId(null);
		setPkRemitApplAmlId(null);
		setPkRemittanceAppBenificiaryId(null);
		setPkRemittanceApplicationId(null);
		setBooProcedureDialog(false);
		setCorsBankName(null);
		setBenificiaryBank(null);
	}

	public void tranferNoClear()
	{
		setTransferNo(null);
	}

	public static BigDecimal round(BigDecimal value, int places) {
		if (places < 0) throw new IllegalArgumentException();

		BigDecimal bd = value;
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd;
	}
	public Boolean getBooProcedureDialog() {
		return booProcedureDialog;
	}
	public void setBooProcedureDialog(Boolean booProcedureDialog) {
		this.booProcedureDialog = booProcedureDialog;
	}
	public BigDecimal getTransactionNoForDisplay() {
		return transactionNoForDisplay;
	}
	public void setTransactionNoForDisplay(BigDecimal transactionNoForDisplay) {
		this.transactionNoForDisplay = transactionNoForDisplay;
	}

}
