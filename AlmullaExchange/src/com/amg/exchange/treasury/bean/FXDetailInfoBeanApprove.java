package com.amg.exchange.treasury.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
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
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.LanguageType;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.foreigncurrency.service.ForeignLocalCurrencyDenominationService;
import com.amg.exchange.foreigncurrency.service.IForeignCurrencyPurchaseService;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.registration.service.ICustomerRegistrationBranchService;
import com.amg.exchange.remittance.model.RoutingHeader;
import com.amg.exchange.stoppayment.service.IStopPaymentService;
import com.amg.exchange.treasury.model.AccountBalance;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankIndicator;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.CustomerSpecialDealRequest;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.treasury.model.StandardInstruction;
import com.amg.exchange.treasury.model.StandardInstructionDetails;
import com.amg.exchange.treasury.model.TreasuryDealDetail;
import com.amg.exchange.treasury.model.TreasuryDealHeader;
import com.amg.exchange.treasury.model.TreasuryStandardInstruction;
import com.amg.exchange.treasury.model.ViewTreasuryDeal;
import com.amg.exchange.treasury.service.IBankIndicatorService;
import com.amg.exchange.treasury.service.IFXDetailInformationService;
import com.amg.exchange.treasury.service.IFundEstimationService;
import com.amg.exchange.treasury.service.ISpecialCustomerDealRequestService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.GetRound;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;


/**
 * @author Nazish ehsan Hashmi
 * @Date
 */

@Component("fXDetailInfoBeanApprove")
@Scope("session")
public class FXDetailInfoBeanApprove<T> implements Serializable {

	Logger log = Logger.getLogger(FXDetailInfoBeanApprove.class);

	private static final long serialVersionUID = 1L;
	//Fx Deal With bank
	private BigDecimal teasuryDealHeaderId = null; // PK
	private BigDecimal company;
	private String document;
	private BigDecimal documentNo;
	private String dealReference;
	private BigDecimal dealYear;
	private String dealDate;
	private BigDecimal dealWith;
	private String contact;
	private String concludedBy;
	private String reuterReference;
	private String remark;
	private BigDecimal dealYearId;
	private String processIn=Constants.Yes;
	//private int documentId= 2;
	//private String lineType="PD";
	private BigDecimal docSerialIdNumberForSave;

	//CR Deal year by User
	private BigDecimal userDealYear;
	private BigDecimal userDealYearId;
	private Date userDealDate;
	private boolean renderdealdate=true;
	private boolean renderdealedit;
	private BigDecimal saveDocumentSerialID = null;
	private Boolean successRender=false;

	private Boolean renderMainPanel=false;

	//Purchase Details
	private BigDecimal purchaseDealDetId = null; // PK
	private BigDecimal purchaseTreasuryInstructionId = null; // PK
	private BigDecimal purchaseBank;
	private BigDecimal purchaseCurrency;
	private String purchaseCurrencyName;
	private BigDecimal purchaseAccountNumber;
	private Date purchaseValueDate;
	private BigDecimal purchaseExchangeRate;
	private String purchaseMultipleDivision;
	private BigDecimal purchaseInstrunction;
	private String purchaseInstructionDesc;
	//CR added
	private String isActiveforPurchase=Constants.Yes;
	private boolean purchaseCheckbox;
	private boolean purchaseCheckboxDisable=true;
	private BigDecimal purchaseBankAccountId;
	//private String lineTypeSale="SD";
	private int purchaseInstrnsize;
	private int bankcurrencySize;
	private boolean booRenderBankId=false;
	private boolean booRenderBankIdForUpdate=true;
	private BigDecimal lineNumberforPurchase=new BigDecimal(0);
	//	private BigDecimal purlocalExchangeRate;


	//sales Details
	private BigDecimal saleDealDetId = null; // PK
	private BigDecimal saleTreasuryInstructionId = null; // PK
	private BigDecimal saleBank;
	private BigDecimal saleCurrency;
	private String saleCurrencyName;
	private String saleAccountNumber;
	private BigDecimal saleAccountNoId;
	private BigDecimal saleBalance;
	private BigDecimal saleAccountBalanceId;
	private Date saleValueDate;
	private BigDecimal saleAmount;
	private BigDecimal saleAvgRate;
	private BigDecimal saleLocalAmount;
	private BigDecimal salePurchaseInstrunction;
	private String salePurchaseInstructionDesc;
	//CR added
	private String isActiveforSales=Constants.Yes;
	private boolean salesCheckbox;
	private boolean saleCheckboxDisable=true;
	private BigDecimal diffLocalAmount;
	private BigDecimal saleBankAccountId;
	private int saleInstrnsize;
	private boolean booRenderCurrencyId=false;
	private boolean booRenderCurrencySingle=true;
	private BigDecimal lineNumberforSale=new BigDecimal(0);
	private boolean bankBalError = true;
	private boolean fcAmountRequired = true;

	private boolean renderEftTtCashPanel = false;
	private BigDecimal ttAmount;
	private BigDecimal eftAmount;
	private BigDecimal cashAmount;

	//Purchase Requirement for Special pool
	private String fundingOptionSpecial= "S";
	private String fundingOptionCommon= "C";
	private BigDecimal sumOfFcAmount=new BigDecimal(0);
	private BigDecimal dataFcAmount=new BigDecimal(0);
	private BigDecimal substractOfFcAmount;
	private boolean splCustMsg = false;
	private BigDecimal sumSplSaleAmount=new BigDecimal(0);

	//	private double totalFcPurcAmount=0.0;
	//Purchase Requirement for common pool
	private BigDecimal fcAmount;
	private BigDecimal fcLocalAmount;
	private int sumOfFCCommonPool;

	//Total Purchase Requirement
	private BigDecimal localExchangeRate;
	private BigDecimal totalFcPurchaseAmount;
	private BigDecimal totallocalAmount;

	// Ali Added
	private boolean blue=false;
	//	private int accNumberSize;
	private boolean booRenderStandInsID=false;
	private boolean booRenderStandInsIDForUpdate=true;
	private BigDecimal salePurchaseInstrunctionForUpdate=null;
	private BigDecimal purchaseInstrunctionForUpdate=null;
	private boolean booRenderPurchaseInstrunction=false;
	private boolean booRenderPurchaseInstrunctionForUpdate=true;
	public BigDecimal differenceAmount;
	public Boolean booRenderDiffAmount;
	//save or update 
	private boolean booRenderSavePanel = true;
	private boolean booRenderUpdatePanel = false;


	private BigDecimal standardInstructionPkForSaleDetail;
	private BigDecimal standardInstructionPkForPurchageDetail;

	//Added for Total sale amount
	private BigDecimal fcSaleAmount;

	private String errorMsg;

	private List<UserFinancialYear> trnxYearList = new ArrayList<UserFinancialYear>();

	public List<UserFinancialYear> getTrnxYearList() {
		return trnxYearList;
	}
	public void setTrnxYearList(List<UserFinancialYear> trnxYearList) {
		this.trnxYearList = trnxYearList;
	}

	public boolean isRenderEftTtCashPanel() {
		return renderEftTtCashPanel;
	}

	public BigDecimal getTtAmount() {
		return ttAmount;
	}
	public BigDecimal getEftAmount() {
		return eftAmount;
	}

	public BigDecimal getCashAmount() {
		return cashAmount;
	}

	public void setRenderEftTtCashPanel(boolean renderEftTtCashPanel) {
		this.renderEftTtCashPanel = renderEftTtCashPanel;
	}

	public void setTtAmount(BigDecimal ttAmount) {
		this.ttAmount = ttAmount;
	}

	public void setEftAmount(BigDecimal eftAmount) {
		this.eftAmount = eftAmount;
	}

	public void setCashAmount(BigDecimal cashAmount) {
		this.cashAmount = cashAmount;
	}

	public BigDecimal getStandardInstructionPkForSaleDetail() {
		return standardInstructionPkForSaleDetail;
	}

	public BigDecimal getStandardInstructionPkForPurchageDetail() {
		return standardInstructionPkForPurchageDetail;
	}

	public void setStandardInstructionPkForSaleDetail(
			BigDecimal standardInstructionPkForSaleDetail) {
		this.standardInstructionPkForSaleDetail = standardInstructionPkForSaleDetail;
	}

	public void setStandardInstructionPkForPurchageDetail(
			BigDecimal standardInstructionPkForPurchageDetail) {
		this.standardInstructionPkForPurchageDetail = standardInstructionPkForPurchageDetail;
	}

	public boolean isSplCustMsg() {
		return splCustMsg;
	}

	public void setSplCustMsg(boolean splCustMsg) {
		this.splCustMsg = splCustMsg;
	}

	public boolean isFcAmountRequired() {
		return fcAmountRequired;
	}


	public Boolean getRenderMainPanel() {
		return renderMainPanel;
	}

	public void setRenderMainPanel(Boolean renderMainPanel) {
		this.renderMainPanel = renderMainPanel;
	}

	public Boolean getSuccessRender() {
		return successRender;
	}

	public void setSuccessRender(Boolean successRender) {
		this.successRender = successRender;
	}

	public void setFcAmountRequired(boolean fcAmountRequired) {
		this.fcAmountRequired = fcAmountRequired;
	}

	public BigDecimal getFcSaleAmount() {
		return fcSaleAmount;
	}

	public void setFcSaleAmount(BigDecimal fcSaleAmount) {
		this.fcSaleAmount = fcSaleAmount;
	}

	public BigDecimal getSumSplSaleAmount() {
		return sumSplSaleAmount;
	}

	public void setSumSplSaleAmount(BigDecimal sumSplSaleAmount) {
		this.sumSplSaleAmount = sumSplSaleAmount;
	}


	public BigDecimal getDataFcAmount() {
		return dataFcAmount;
	}

	public void setDataFcAmount(BigDecimal dataFcAmount) {
		this.dataFcAmount = dataFcAmount;
	}


	public BigDecimal getDifferenceAmount() {
		return differenceAmount;
	}

	public void setDifferenceAmount(BigDecimal differenceAmount) {
		this.differenceAmount = differenceAmount;
	}

	public Boolean getBooRenderDiffAmount() {
		return booRenderDiffAmount;
	}

	public void setBooRenderDiffAmount(Boolean booRenderDiffAmount) {
		this.booRenderDiffAmount = booRenderDiffAmount;
	}


	@Autowired
	IFXDetailInformationService<T> fXDetailInformationService;

	@Autowired
	IGeneralService<T> generalService;

	@Autowired
	IForeignCurrencyPurchaseService<T> foreignCurrencyPurchaseService;

	@Autowired
	IFundEstimationService<T> fundEstimationService;

	@Autowired
	ForeignLocalCurrencyDenominationService<T> foreignLocalCurrencyDenominationService;

	@Autowired
	ISpecialCustomerDealRequestService<T> ispecialCustomerDealRequestService;

	@Autowired
	IBankIndicatorService bankIndicatorService;

	@Autowired
	ICustomerRegistrationBranchService<T> iCustomerRegistrationBranchService;

	@Autowired
	IStopPaymentService<T> stopPaymentService;

	public IForeignCurrencyPurchaseService<T> getForeignCurrencyPurchaseService() {
		return foreignCurrencyPurchaseService;
	}

	public void setForeignCurrencyPurchaseService(IForeignCurrencyPurchaseService<T> foreignCurrencyPurchaseService) {
		this.foreignCurrencyPurchaseService = foreignCurrencyPurchaseService;
	}


	public Boolean getBankBalError() {
		return bankBalError;
	}

	public void setBankBalError(Boolean bankBalError) {
		this.bankBalError = bankBalError;
	}

	public BigDecimal getLineNumberforPurchase() {
		return lineNumberforPurchase;
	}

	public void setLineNumberforPurchase(BigDecimal lineNumberforPurchase) {
		this.lineNumberforPurchase = lineNumberforPurchase;
	}

	public BigDecimal getLineNumberforSale() {
		return lineNumberforSale;
	}

	public void setLineNumberforSale(BigDecimal lineNumberforSale) {
		this.lineNumberforSale = lineNumberforSale;
	}

	public BigDecimal getSumOfFcAmount() {
		return sumOfFcAmount;
	}

	public void setSumOfFcAmount(BigDecimal sumOfFcAmount) {
		this.sumOfFcAmount = sumOfFcAmount;
	}

	public boolean isBooRenderBankId() {
		return booRenderBankId;
	}

	public void setBooRenderBankId(boolean booRenderBankId) {
		this.booRenderBankId = booRenderBankId;
	}

	public boolean isBooRenderBankIdForUpdate() {
		return booRenderBankIdForUpdate;
	}

	public void setBooRenderBankIdForUpdate(boolean booRenderBankIdForUpdate) {
		this.booRenderBankIdForUpdate = booRenderBankIdForUpdate;
	}

	public boolean isBooRenderCurrencyId() {
		return booRenderCurrencyId;
	}

	public void setBooRenderCurrencyId(boolean booRenderCurrencyId) {
		this.booRenderCurrencyId = booRenderCurrencyId;
	}

	public boolean isBooRenderCurrencySingle() {
		return booRenderCurrencySingle;
	}

	public void setBooRenderCurrencySingle(boolean booRenderCurrencySingle) {
		this.booRenderCurrencySingle = booRenderCurrencySingle;
	}

	public boolean isSaleCheckboxDisable() {
		return saleCheckboxDisable;
	}

	public void setSaleCheckboxDisable(boolean saleCheckboxDisable) {
		this.saleCheckboxDisable = saleCheckboxDisable;
	}

	public String getPurchaseCurrencyName() {
		return purchaseCurrencyName;
	}

	public void setPurchaseCurrencyName(String purchaseCurrencyName) {
		this.purchaseCurrencyName = purchaseCurrencyName;
	}

	public String getSaleCurrencyName() {
		return saleCurrencyName;
	}

	public void setSaleCurrencyName(String saleCurrencyName) {
		this.saleCurrencyName = saleCurrencyName;
	}


	private SessionStateManage sessionManage = new SessionStateManage();
	private List<CompanyMasterDesc> lstCompany = new ArrayList<CompanyMasterDesc>();
	private List<CurrencyMaster> lstCurrency = new ArrayList<CurrencyMaster>();
	private List<Document> lstDocument=new ArrayList<Document>();
	private List<UserFinancialYear> DealYearList= new ArrayList<UserFinancialYear>();
	private List<BankMaster> dealListBank = new ArrayList<BankMaster>();
	private List<TreasuryDealHeader> treasuryDealHeaderList = new ArrayList<TreasuryDealHeader>();
	private List<BankAccountDetails> accountNumberList = new ArrayList<BankAccountDetails>();
	private List<BankAccountDetails> accountNumberListforSale = new ArrayList<BankAccountDetails>();
	private List<BankAccountDetails> currencylistforPurchase = new ArrayList<BankAccountDetails>();
	private List<BankAccountDetails> currencylistforsale = new ArrayList<BankAccountDetails>();
	private List<AccountBalance> accountBalalnceList=new ArrayList<AccountBalance>();
	private List<RoutingHeader> bankActiveinRoutingSetUp=new ArrayList<RoutingHeader>();
	private List<BankApplicability> lstAllBankApplicabity = new ArrayList<BankApplicability>();
	private List<TreasuryStandardInstruction> lstTresSIPur = new ArrayList<TreasuryStandardInstruction>();
	private List<TreasuryStandardInstruction> lstTresSISale = new ArrayList<TreasuryStandardInstruction>();
	List<TreasuryDealHeader> lstofUnApproved = new ArrayList<TreasuryDealHeader>();
	CopyOnWriteArrayList<BigDecimal> lstofDeleteSplPool = new CopyOnWriteArrayList<BigDecimal>();
	CopyOnWriteArrayList<BigDecimal> lstofClearSplPool = new CopyOnWriteArrayList<BigDecimal>();



	//rahamath
	private List<BankAccountDetails> bankCountryCurrencyList=new ArrayList<BankAccountDetails>();

	//private Constants constants = new Constants();

	//CR getting all Instruction Numbers from DB
	private List<StandardInstruction> pstandardInstrnforPurchase = new ArrayList<StandardInstruction>();
	private List<StandardInstructionDetails> cstandardInstrnDetailsPurchase = new ArrayList<StandardInstructionDetails>();

	private List<StandardInstruction> pstandardInstrnforSales = new ArrayList<StandardInstruction>();
	private List<StandardInstructionDetails> cstandardInstrnDetailsforSales = new ArrayList<StandardInstructionDetails>();

	//CR getting Purchase Requirement for Special Pool

	//private List<PurchaseReqSplPoolDataTable> purchaseReqSplPoolDataTableList = new ArrayList<PurchaseReqSplPoolDataTable>();
	private List<CustomerSpecialDealRequest> purchaseCommonPoolDataTable = new ArrayList<CustomerSpecialDealRequest>();
	List<CustomerSpecialDealRequest> purchaseSpecialPoolDataTable = new ArrayList<CustomerSpecialDealRequest>();
	CopyOnWriteArrayList<PurchaseReqSplPoolDataTable> purchaseReqSplPoolDataTableList = new CopyOnWriteArrayList<PurchaseReqSplPoolDataTable>();
	List<PurchaseReqSplPoolDataTable> lstofSplPoolDealReq = new ArrayList<PurchaseReqSplPoolDataTable>();
	List<CustomerSpecialDealRequest> purchaseSPSelectDataTableForSave = new ArrayList<CustomerSpecialDealRequest>();
	// for account number populations and validation
	private Boolean booSelectbankPAcc = true;
	private Boolean multiselectPAcc = false;
	private String singlacc;
	private Boolean oneAccNoSal = true;
	private Boolean mulAccNosal = false;
	private String saleAcctNo;
	//for to get the calenderDate
	//validate valueDate in PurchseDetails,SalesDeatils Screen 
	private Date date3;
	private String purchaseDate;

	private String faAccountNumberForPurchase;
	private String faAccountNumberForSale;

	public String getFaAccountNumberForPurchase() {
		return faAccountNumberForPurchase;
	}

	public void setFaAccountNumberForPurchase(String faAccountNumberForPurchase) {
		this.faAccountNumberForPurchase = faAccountNumberForPurchase;
	}

	public String getFaAccountNumberForSale() {
		return faAccountNumberForSale;
	}

	public void setFaAccountNumberForSale(String faAccountNumberForSale) {
		this.faAccountNumberForSale = faAccountNumberForSale;
	}

	//rahamath
	public List<BankAccountDetails> getBankCountryCurrencyList() {
		return bankCountryCurrencyList;
	}

	public void setBankCountryCurrencyList(
			List<BankAccountDetails> bankCountryCurrencyList) {
		this.bankCountryCurrencyList = bankCountryCurrencyList;
	}

	public BigDecimal getPurchaseInstrunctionForUpdate() {
		return purchaseInstrunctionForUpdate;
	}

	public void setPurchaseInstrunctionForUpdate(
			BigDecimal purchaseInstrunctionForUpdate) {
		this.purchaseInstrunctionForUpdate = purchaseInstrunctionForUpdate;
	}

	public CopyOnWriteArrayList<PurchaseReqSplPoolDataTable> getPurchaseReqSplPoolDataTableList() {
		return purchaseReqSplPoolDataTableList;
	}

	public void setPurchaseReqSplPoolDataTableList(CopyOnWriteArrayList<PurchaseReqSplPoolDataTable> purchaseReqSplPoolDataTableList) {
		this.purchaseReqSplPoolDataTableList = purchaseReqSplPoolDataTableList;
	}

	public List<BankAccountDetails> getCurrencylistforPurchase() {
		return currencylistforPurchase;
	}

	public void setCurrencylistforPurchase(
			List<BankAccountDetails> currencylistforPurchase) {
		this.currencylistforPurchase = currencylistforPurchase;
	}

	public List<BankAccountDetails> getCurrencylistforsale() {
		return currencylistforsale;
	}

	public void setCurrencylistforsale(List<BankAccountDetails> currencylistforsale) {
		this.currencylistforsale = currencylistforsale;
	}

	public Boolean getBooRenderPurchaseInstrunction() {
		return booRenderPurchaseInstrunction;
	}

	public void setBooRenderPurchaseInstrunction(
			Boolean booRenderPurchaseInstrunction) {
		this.booRenderPurchaseInstrunction = booRenderPurchaseInstrunction;
	}

	public Boolean getBooRenderPurchaseInstrunctionForUpdate() {
		return booRenderPurchaseInstrunctionForUpdate;
	}

	public void setBooRenderPurchaseInstrunctionForUpdate(
			Boolean booRenderPurchaseInstrunctionForUpdate) {
		this.booRenderPurchaseInstrunctionForUpdate = booRenderPurchaseInstrunctionForUpdate;
	}

	public BigDecimal getSalePurchaseInstrunctionForUpdate() {
		return salePurchaseInstrunctionForUpdate;
	}

	public void setSalePurchaseInstrunctionForUpdate(
			BigDecimal salePurchaseInstrunctionForUpdate) {
		this.salePurchaseInstrunctionForUpdate = salePurchaseInstrunctionForUpdate;
	}

	public Boolean getBooRenderStandInsID() {
		return booRenderStandInsID;
	}

	public void setBooRenderStandInsID(Boolean booRenderStandInsID) {
		this.booRenderStandInsID = booRenderStandInsID;
	}

	public Boolean getBooRenderStandInsIDForUpdate() {
		return booRenderStandInsIDForUpdate;
	}

	public void setBooRenderStandInsIDForUpdate(Boolean booRenderStandInsIDForUpdate) {
		this.booRenderStandInsIDForUpdate = booRenderStandInsIDForUpdate;
	}

	public List<BankApplicability> getLstAllBankApplicabity() {
		return lstAllBankApplicabity;
	}

	public void setLstAllBankApplicabity(List<BankApplicability> lstAllBankApplicabity) {
		this.lstAllBankApplicabity = lstAllBankApplicabity;
	}

	public boolean isBlue() {
		return blue;
	}

	public void setBlue(boolean blue) {
		this.blue = blue;
	}

	/*	public String getLineType() {
		return lineType;
	}

	public void setLineType(String lineType) {
		this.lineType = lineType;
	}*/

	public CopyOnWriteArrayList<BigDecimal> getLstofClearSplPool() {
		return lstofClearSplPool;
	}

	public void setLstofClearSplPool(
			CopyOnWriteArrayList<BigDecimal> lstofClearSplPool) {
		this.lstofClearSplPool = lstofClearSplPool;
	}

	public CopyOnWriteArrayList<BigDecimal> getLstofDeleteSplPool() {
		return lstofDeleteSplPool;
	}

	public void setLstofDeleteSplPool(
			CopyOnWriteArrayList<BigDecimal> lstofDeleteSplPool) {
		this.lstofDeleteSplPool = lstofDeleteSplPool;
	}

	public boolean isRenderdealdate() {
		return renderdealdate;
	}

	public void setRenderdealdate(boolean renderdealdate) {
		this.renderdealdate = renderdealdate;
	}

	public boolean isRenderdealedit() {
		return renderdealedit;
	}

	public void setRenderdealedit(boolean renderdealedit) {
		this.renderdealedit = renderdealedit;
	}

	public BigDecimal getUserDealYearId() {
		return userDealYearId;
	}

	public Date getUserDealDate() {
		return userDealDate;
	}

	public void setUserDealDate(Date userDealDate) {
		this.userDealDate = userDealDate;
	}

	public void setUserDealYearId(BigDecimal userDealYearId) {
		this.userDealYearId = userDealYearId;
	}

	public BigDecimal getUserDealYear() {
		return userDealYear;
	}

	public void setUserDealYear(BigDecimal userDealYear) {
		this.userDealYear = userDealYear;
	}

	/*public String getLineTypeSale() {
		return lineTypeSale;
	}

	public void setLineTypeSale(String lineTypeSale) {
		this.lineTypeSale = lineTypeSale;
	}*/

	public BigDecimal getCompany() {
		return company;
	}

	public int getSumOfFCCommonPool() {
		return sumOfFCCommonPool;
	}

	public void setSumOfFCCommonPool(int sumOfFCCommonPool) {
		this.sumOfFCCommonPool = sumOfFCCommonPool;
	}

	public BigDecimal getTotallocalAmount() {
		return totallocalAmount;
	}

	public void setTotallocalAmount(BigDecimal totallocalAmount) {
		this.totallocalAmount = totallocalAmount;
	}

	public BigDecimal getPurchaseBankAccountId() {
		return purchaseBankAccountId;
	}

	public void setPurchaseBankAccountId(BigDecimal purchaseBankAccountId) {
		this.purchaseBankAccountId = purchaseBankAccountId;
	}

	public BigDecimal getSaleBankAccountId() {
		return saleBankAccountId;
	}

	public void setSaleBankAccountId(BigDecimal saleBankAccountId) {
		this.saleBankAccountId = saleBankAccountId;
	}

	public Boolean getBooSelectbankPAcc() {
		return booSelectbankPAcc;
	}

	public void setBooSelectbankPAcc(Boolean booSelectbankPAcc) {
		this.booSelectbankPAcc = booSelectbankPAcc;
	}

	public Boolean getMultiselectPAcc() {
		return multiselectPAcc;
	}

	public void setMultiselectPAcc(Boolean multiselectPAcc) {
		this.multiselectPAcc = multiselectPAcc;
	}

	public String getSinglacc() {
		return singlacc;
	}

	public void setSinglacc(String singlacc) {
		this.singlacc = singlacc;
	}

	public Boolean getOneAccNoSal() {
		return oneAccNoSal;
	}

	public void setOneAccNoSal(Boolean oneAccNoSal) {
		this.oneAccNoSal = oneAccNoSal;
	}

	public Boolean getMulAccNosal() {
		return mulAccNosal;
	}

	public void setMulAccNosal(Boolean mulAccNosal) {
		this.mulAccNosal = mulAccNosal;
	}

	public String getSaleAcctNo() {
		return saleAcctNo;
	}

	public void setSaleAcctNo(String saleAcctNo) {
		this.saleAcctNo = saleAcctNo;
	}

	public BigDecimal getDiffLocalAmount() {
		return diffLocalAmount;
	}

	public void setDiffLocalAmount(BigDecimal diffLocalAmount) {
		this.diffLocalAmount = diffLocalAmount;
	}

	public BigDecimal getTeasuryDealHeaderId() {
		return teasuryDealHeaderId;
	}

	public void setTeasuryDealHeaderId(BigDecimal teasuryDealHeaderId) {
		this.teasuryDealHeaderId = teasuryDealHeaderId;
	}

	public List<CustomerSpecialDealRequest> getPurchaseCommonPoolDataTable() {
		return purchaseCommonPoolDataTable;
	}

	public void setPurchaseCommonPoolDataTable(
			List<CustomerSpecialDealRequest> purchaseCommonPoolDataTable) {
		this.purchaseCommonPoolDataTable = purchaseCommonPoolDataTable;
	}

	public boolean isPurchaseCheckbox() {
		return purchaseCheckbox;
	}

	public void setPurchaseCheckbox(boolean purchaseCheckbox) {
		this.purchaseCheckbox = purchaseCheckbox;
	}

	public boolean isPurchaseCheckboxDisable() {
		return purchaseCheckboxDisable;
	}

	public void setPurchaseCheckboxDisable(boolean purchaseCheckboxDisable) {
		this.purchaseCheckboxDisable = purchaseCheckboxDisable;
	}

	public List<CustomerSpecialDealRequest> getPurchaseSpecialPoolDataTable() {
		return purchaseSpecialPoolDataTable;
	}

	public void setPurchaseSpecialPoolDataTable(
			List<CustomerSpecialDealRequest> purchaseSpecialPoolDataTable) {
		this.purchaseSpecialPoolDataTable = purchaseSpecialPoolDataTable;
	}

	public List<CustomerSpecialDealRequest> getPurchaseSPSelectDataTableForSave() {
		return purchaseSPSelectDataTableForSave;
	}

	public void setPurchaseSPSelectDataTableForSave(
			List<CustomerSpecialDealRequest> purchaseSPSelectDataTableForSave) {
		this.purchaseSPSelectDataTableForSave = purchaseSPSelectDataTableForSave;
	}

	public boolean isSalesCheckbox() {
		return salesCheckbox;
	}

	public void setSalesCheckbox(boolean salesCheckbox) {
		this.salesCheckbox = salesCheckbox;
	}

	public List<StandardInstruction> getPstandardInstrnforPurchase() {
		return pstandardInstrnforPurchase;
	}

	public void setPstandardInstrnforPurchase(
			List<StandardInstruction> pstandardInstrnforPurchase) {
		this.pstandardInstrnforPurchase = pstandardInstrnforPurchase;
	}

	public List<StandardInstructionDetails> getCstandardInstrnDetailsPurchase() {
		return cstandardInstrnDetailsPurchase;
	}

	public void setCstandardInstrnDetailsPurchase(
			List<StandardInstructionDetails> cstandardInstrnDetailsPurchase) {
		this.cstandardInstrnDetailsPurchase = cstandardInstrnDetailsPurchase;
	}

	public List<StandardInstruction> getPstandardInstrnforSales() {
		return pstandardInstrnforSales;
	}

	public void setPstandardInstrnforSales(
			List<StandardInstruction> pstandardInstrnforSales) {
		this.pstandardInstrnforSales = pstandardInstrnforSales;
	}

	public List<StandardInstructionDetails> getCstandardInstrnDetailsforSales() {
		return cstandardInstrnDetailsforSales;
	}

	public void setCstandardInstrnDetailsforSales(
			List<StandardInstructionDetails> cstandardInstrnDetailsforSales) {
		this.cstandardInstrnDetailsforSales = cstandardInstrnDetailsforSales;
	}

	public void setCompany(BigDecimal company) {
		this.company = company;
	}

	public String getDealReference() {
		String value=Integer.toString(getDocumentSerialId());
		return value;
	}

	public void setDealReference(String dealReference) {
		this.dealReference = dealReference;
	}


	public void setDocument(String document) {
		this.document = document;
	}

	public String getDocument() {
		return document;
	}


	public BigDecimal getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}


	public String getDealDate() {
		return returnFormattedDate(new Date());
	}

	public void setDealDate(String dealDate) {
		this.dealDate = dealDate;
	}

	public BigDecimal getDealWith() {
		return dealWith;
	}

	public void setDealWith(BigDecimal dealWith) {
		this.dealWith = dealWith;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getConcludedBy() {
		return concludedBy;
	}

	public void setConcludedBy(String concludedBy) {
		this.concludedBy = concludedBy;
	}

	public String getReuterReference() {
		return reuterReference;
	}

	public void setReuterReference(String reuterReference) {
		this.reuterReference = reuterReference;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


	public BigDecimal getDealYearId() {
		return dealYearId;
	}

	public void setDealYearId(BigDecimal dealYearId) {
		this.dealYearId = dealYearId;
	}


	public String getProcessIn() {
		return processIn;
	}

	public void setProcessIn(String processIn) {
		this.processIn = processIn;
	}


	public BigDecimal getPurchaseDealDetId() {
		return purchaseDealDetId;
	}

	public void setPurchaseDealDetId(BigDecimal purchaseDealDetId) {
		this.purchaseDealDetId = purchaseDealDetId;
	}


	public BigDecimal getPurchaseTreasuryInstructionId() {
		return purchaseTreasuryInstructionId;
	}

	public void setPurchaseTreasuryInstructionId(
			BigDecimal purchaseTreasuryInstructionId) {
		this.purchaseTreasuryInstructionId = purchaseTreasuryInstructionId;
	}

	public BigDecimal getPurchaseBank() {
		return purchaseBank;
	}

	public void setPurchaseBank(BigDecimal purchaseBank) {
		this.purchaseBank = purchaseBank;
	}

	public BigDecimal getPurchaseCurrency() {
		return purchaseCurrency;
	}

	public void setPurchaseCurrency(BigDecimal purchaseCurrency) {
		this.purchaseCurrency = purchaseCurrency;
	}

	public BigDecimal getPurchaseAccountNumber() {
		return purchaseAccountNumber;
	}

	public void setPurchaseAccountNumber(BigDecimal purchaseAccountNumber) {
		this.purchaseAccountNumber = purchaseAccountNumber;
	}

	public Date getPurchaseValueDate() {
		return purchaseValueDate;
	}

	public void setPurchaseValueDate(Date purchaseValueDate) {
		this.purchaseValueDate = purchaseValueDate;
	}


	public String getPurchaseMultipleDivision() {
		return purchaseMultipleDivision;
	}

	public void setPurchaseMultipleDivision(String purchaseMultipleDivision) {
		this.purchaseMultipleDivision = purchaseMultipleDivision;
	}

	public BigDecimal getPurchaseExchangeRate() {
		return purchaseExchangeRate;
	}

	public void setPurchaseExchangeRate(BigDecimal purchaseExchangeRate) {
		this.purchaseExchangeRate = purchaseExchangeRate;
	}

	public BigDecimal getPurchaseInstrunction() {
		return purchaseInstrunction;
	}

	public void setPurchaseInstrunction(BigDecimal purchaseInstrunction) {
		this.purchaseInstrunction = purchaseInstrunction;
	}

	public String getPurchaseInstructionDesc() {
		return purchaseInstructionDesc;
	}

	public void setPurchaseInstructionDesc(String purchaseInstructionDesc) {
		this.purchaseInstructionDesc = purchaseInstructionDesc;
	}


	public BigDecimal getSaleDealDetId() {
		return saleDealDetId;
	}

	public void setSaleDealDetId(BigDecimal saleDealDetId) {
		this.saleDealDetId = saleDealDetId;
	}


	public BigDecimal getSaleTreasuryInstructionId() {
		return saleTreasuryInstructionId;
	}

	public void setSaleTreasuryInstructionId(BigDecimal saleTreasuryInstructionId) {
		this.saleTreasuryInstructionId = saleTreasuryInstructionId;
	}

	public BigDecimal getSaleBank() {
		return saleBank;
	}

	public void setSaleBank(BigDecimal saleBank) {
		this.saleBank = saleBank;
	}

	public BigDecimal getSaleCurrency() {
		return saleCurrency;
	}

	public void setSaleCurrency(BigDecimal saleCurrency) {
		this.saleCurrency = saleCurrency;
	}

	public String getSaleAccountNumber() {
		return saleAccountNumber;
	}


	public BigDecimal getSaleAccountNoId() {
		return saleAccountNoId;
	}

	public void setSaleAccountNoId(BigDecimal saleAccountNoId) {
		this.saleAccountNoId = saleAccountNoId;
	}

	public void setSaleAccountNumber(String saleAccountNumber) {
		this.saleAccountNumber = saleAccountNumber;
	}

	/*public BigDecimal getPurlocalExchangeRate() {
		return purlocalExchangeRate;
	}

	public void setPurlocalExchangeRate(BigDecimal purlocalExchangeRate) {
		this.purlocalExchangeRate = purlocalExchangeRate;
	}*/

	public List<TreasuryStandardInstruction> getLstTresSIPur() {
		return lstTresSIPur;
	}

	public void setLstTresSIPur(List<TreasuryStandardInstruction> lstTresSIPur) {
		this.lstTresSIPur = lstTresSIPur;
	}

	public List<TreasuryStandardInstruction> getLstTresSISale() {
		return lstTresSISale;
	}

	public void setLstTresSISale(List<TreasuryStandardInstruction> lstTresSISale) {
		this.lstTresSISale = lstTresSISale;
	}

	public BigDecimal getSaleBalance() {
		/*accountBalalnceList = fXDetailInformationService.getBankBalance(getSaleBank(), getSaleCurrency(), getSaleAccountNumber());
		for(AccountBalance lstBal:accountBalalnceList)
		{
		//setSaleAccountBalanceId(lstBal.getAccountId());
		setSaleAccountNoId(lstBal.getAccountId());
		setSaleAccountNumber(lstBal.getAccountNo());
		}*/
		return saleBalance;
	}

	public void setSaleBalance(BigDecimal saleBalance) {
		this.saleBalance = saleBalance;
	}


	public BigDecimal getSaleAccountBalanceId() {
		return saleAccountBalanceId;
	}

	public void setSaleAccountBalanceId(BigDecimal saleAccountBalanceId) {
		this.saleAccountBalanceId = saleAccountBalanceId;
	}

	public Date getSaleValueDate() {
		return saleValueDate;
	}

	public void setSaleValueDate(Date saleValueDate) {
		this.saleValueDate = saleValueDate;
	}

	public BigDecimal getSaleAmount() {
		return saleAmount;
	}

	public void setSaleAmount(BigDecimal saleAmount) {
		this.saleAmount = saleAmount;
	}

	public BigDecimal getSaleAvgRate() {
		return saleAvgRate;
	}

	public void setSaleAvgRate(BigDecimal saleAvgRate) {
		this.saleAvgRate = saleAvgRate;
	}

	public BigDecimal getSaleLocalAmount() {
		return saleLocalAmount;
	}

	public void setSaleLocalAmount(BigDecimal saleLocalAmount) {
		this.saleLocalAmount = saleLocalAmount;
	}

	public BigDecimal getSalePurchaseInstrunction() {
		return salePurchaseInstrunction;
	}

	public void setSalePurchaseInstrunction(BigDecimal salePurchaseInstrunction) {
		this.salePurchaseInstrunction = salePurchaseInstrunction;
	}

	public String getSalePurchaseInstructionDesc() {
		return salePurchaseInstructionDesc;
	}

	public void setSalePurchaseInstructionDesc(String salePurchaseInstructionDesc) {
		this.salePurchaseInstructionDesc = salePurchaseInstructionDesc;
	}

	public BigDecimal getFcAmount() {
		//return getDataTableForPurchaseCommonPool();
		return fcAmount;
	}

	public void setFcAmount(BigDecimal fcAmount) {
		this.fcAmount = fcAmount;
	}

	public BigDecimal getFcLocalAmount() {
		return fcLocalAmount;
	}

	public void setFcLocalAmount(BigDecimal fcLocalAmount) {
		this.fcLocalAmount = fcLocalAmount;
	}

	public BigDecimal getLocalExchangeRate() {
		return localExchangeRate;
	}

	public void setLocalExchangeRate(BigDecimal localExchangeRate) {
		this.localExchangeRate = localExchangeRate;
	}

	public BigDecimal getTotalFcPurchaseAmount() {
		return totalFcPurchaseAmount;
	}

	public void setTotalFcPurchaseAmount(BigDecimal totalFcPurchaseAmount) {
		this.totalFcPurchaseAmount = totalFcPurchaseAmount;
	}


	public List<PurchaseReqSplPoolDataTable> getLstofSplPoolDealReq() {
		return lstofSplPoolDealReq;
	}

	public void setLstofSplPoolDealReq(
			List<PurchaseReqSplPoolDataTable> lstofSplPoolDealReq) {
		this.lstofSplPoolDealReq = lstofSplPoolDealReq;
	}

	public Boolean getBooRenderSavePanel() {
		return booRenderSavePanel;
	}

	public void setBooRenderSavePanel(Boolean booRenderSavePanel) {
		this.booRenderSavePanel = booRenderSavePanel;
	}

	public Boolean getBooRenderUpdatePanel() {
		return booRenderUpdatePanel;
	}

	public void setBooRenderUpdatePanel(Boolean booRenderUpdatePanel) {
		this.booRenderUpdatePanel = booRenderUpdatePanel;
	}

	public List<CurrencyMaster> getLstCurrency() {
		return lstCurrency;
	}

	public void setLstCurrency(List<CurrencyMaster> lstCurrency) {
		this.lstCurrency = lstCurrency;
	}


	//get company list from database

	public List<TreasuryDealHeader> getLstofUnApproved() {
		return lstofUnApproved;
	}

	public void setLstofUnApproved(List<TreasuryDealHeader> lstofUnApproved) {
		this.lstofUnApproved = lstofUnApproved;
	}

	public List<BankAccountDetails> getAccountNumberList() {
		return accountNumberList;
	}

	public void setAccountNumberList(List<BankAccountDetails> accountNumberList) {
		this.accountNumberList = accountNumberList;
	}



	public List<BankAccountDetails> getAccountNumberListforSale() {
		return accountNumberListforSale;
	}

	public void setAccountNumberListforSale(
			List<BankAccountDetails> accountNumberListforSale) {
		this.accountNumberListforSale = accountNumberListforSale;
	}

	public String getLstCompany() {
		List<CompanyMasterDesc> data=generalService.getCompanyList(sessionManage.getCompanyId(), sessionManage.getLanguageId());
		setCompany(data.get(0).getFsCompanyMaster().getCompanyId());
		return data.get(0).getCompanyName();
	}

	public int getDocumentSerialId() {
		return Integer.parseInt(getDocumentSerialID(processIn));
	}

	public String getDocumentNumber() {
		lstDocument=generalService.getDocument(new BigDecimal(Constants.DOCUMENT_CODE_FOR_FX_DEAL_WITH_BANK),new BigDecimal(sessionManage.isExists("languageId") ? sessionManage.getSessionValue("languageId") : "1"));
		for(Document lstdoc:lstDocument)
		{
			setDocumentNo(lstdoc.getDocumentID());

			setDocument(lstdoc.getDocumentDesc());
		}
		return document;
	}


	public String getDocumentSerialID(String processIn){

		String documentSerialID = generalService.getNextDocumentReferenceNumber(sessionManage.getCountryId().intValue() , sessionManage.getCompanyId().intValue(), Integer.parseInt(Constants.DOCUMENT_CODE_FOR_FX_DEAL_WITH_BANK) , getDealYear().intValue(),processIn,sessionManage.getCountryBranchCode());
		return documentSerialID;
	}

	/*//get currency list from database
	public List<CurrencyMaster> getCurrencyList(){

		setLstCurrency(fXDetailInformationService.getCurrencyList());
		return getLstCurrency();
	}*/

	public BigDecimal getDocSerialIdNumberForSave() {
		return docSerialIdNumberForSave;
	}

	public void setDocSerialIdNumberForSave(BigDecimal docSerialIdNumberForSave) {
		this.docSerialIdNumberForSave = docSerialIdNumberForSave;
	}

	public BigDecimal getDealYear() {
		return dealYear;
	}

	public void setDealYear(BigDecimal dealYear) {
		this.dealYear = dealYear;
	}

	public void exit() {
		if(sessionManage.getRoleId().equalsIgnoreCase("1")){
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void fxdealpage(){
		clearCache();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/fxdealbank.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// fetch deal year
	public void fetchDealYear(){
		try{
			DealYearList = generalService.getDealYear(new Date());
			if(DealYearList!=null){
				if(getUserDealYear()==null){
					dealYear = DealYearList.get(0).getFinancialYear();
					dealYearId=DealYearList.get(0).getFinancialYearID();
					setDealYearId(dealYearId);
					setDealYear(dealYear);
				}else{
					setDealYear(getUserDealYear());
					setDealYearId(getUserDealYearId());
				}
			}
		}catch(Exception e){
			e.printStackTrace();	
		}
	}


	public List<BankMaster> getDealListBank() {

		List<BankMaster> lstlocalbankList = null;

		List<BankIndicator> bankIndlist = bankIndicatorService.getRecordFromDB(Constants.BANK_INDICATOR_LOCAL_BANK);

		if(bankIndlist.size() != 0){
			BigDecimal pkBankInd = bankIndlist.get(0).getBankIndicatorId();
			lstlocalbankList = fXDetailInformationService.getDealBank(pkBankInd);
		}

		return lstlocalbankList;
	}

	public void setDealListBank(List<BankMaster> dealListBank) {
		this.dealListBank = dealListBank;
	}

	private String returnFormattedDate(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return simpleDateFormat.format(date);
	}
	public Date getDate3() {
		return date3;
	}

	public void setDate3(Date date3) {
		this.date3 = date3;
	}

	public void onDateSelect(SelectEvent event) {

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

		setPurchaseDate(format.format(event.getObject()));

	}
	public String getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public void clearFxDeal(){
		setTeasuryDealHeaderId(null);
		setPurchaseTreasuryInstructionId(null);
		setPurchaseDealDetId(null);
		setSaleDealDetId(null);
		setSaleTreasuryInstructionId(null);
		setEffectiveMinDate(new Date());
		setDealYear(null);
		setDealReference("");
		setDealDate("");
		setDealDateStr(null);
		setDealWith(null);
		setContact("");
		setConcludedBy("");
		setReuterReference("");
		setRemark("");
		setRenderRef(true);
		setRenderRefEditable(false);
		setRenderdealdate(true);
		setRenderdealedit(false);
		setUserDealDate(new Date());
		//setPurlocalExchangeRate(null);

	}

	public void clearPurchaseDetails(){

		setPurchaseBank(null);
		setPurchaseCurrency(null);
		setPurchaseCurrencyName(null);
		if(bankCountryCurrencyList!=null){
			bankCountryCurrencyList.clear();
		}
		setPurchaseValueDate(null);
		setDate3(null);
		setValueDateStr(null);
		setPurchaseAccountNumber(null);
		setPurchaseMultipleDivision("");
		setPurchaseExchangeRate(null);
		setPurchaseInstrunction(null);
		setPurchaseInstructionDesc("");
		setPurchaseBankAccountId(null);
		setPurchaseAccountNumber(null);
		setSinglacc(null);
		setPurchaseInstrunctionForUpdate(null);
		setBooRenderPurchaseInstrunctionForUpdate(true);
		setBooRenderPurchaseInstrunction(false);
		setPurchaseCheckbox(false);
		setBooRenderBankId(false);
		setBooRenderBankIdForUpdate(true);
		accountNumberList.clear();
		purchaseReqSplPoolDataTableList.clear();
		purchaseSpecialPoolDataTable.clear();
		purchaseCommonPoolDataTable.clear();
		pstandardInstrnforPurchase.clear();
		cstandardInstrnDetailsPurchase.clear();
		lstofDeleteSplPool.clear();
		lstofClearSplPool.clear();
		lstTresSIPur.clear();
		setFaAccountNumberForPurchase(null);
		setFcSaleAmount(null);

	}

	public void clearPurchaseRequirmentforSpecial(){
		setSplCustMsg(false);
		setFcAmountRequired(true);
	}

	public void clearPurchaseRequirment(){
		setFcAmount(null);
		setFcLocalAmount(null);
		setTotalFcPurchaseAmount(null);
		setTotallocalAmount(null);
		setLocalExchangeRate(null);
	}

	public void clearSalesPurchase(){

		setSaleBank(null);
		setSaleCurrency(null);
		setSaleCurrencyName(null);
		setSalesCheckbox(false);
		setSaleBalance(null);
		setSaleValueDate(null);
		setSaleValueDateStr(null);
		setSaleAmount(null);
		setSaleAvgRate(null);
		setSalePurchaseInstrunction(null);
		setSalePurchaseInstructionDesc("");
		setSaleAccountBalanceId(null);
		setSaleAccountNoId(null);
		setSaleAccountNumber(null);
		setSaleAcctNo(null);
		setSaleLocalAmount(null);
		setSalePurchaseInstrunctionForUpdate(null);
		setOneAccNoSal(true);
		setMulAccNosal(false);
		setBooRenderCurrencyId(false);
		setBooRenderCurrencySingle(true);
		setBooRenderStandInsID(false);
		setBooRenderStandInsIDForUpdate(true);
		accountNumberListforSale.clear();
		pstandardInstrnforSales.clear();
		cstandardInstrnDetailsforSales.clear();
		lstTresSISale.clear();
		setFaAccountNumberForSale(null);

	}

	public void clear(){
		setStandardInstructionPkForPurchageDetail(null);
		setStandardInstructionPkForSaleDetail(null);
		clearFxDeal();
		clearPurchaseDetails();
		clearPurchaseRequirment();
		clearPurchaseRequirmentforSpecial();
		clearSalesPurchase();
		setEftAmount(null);
		setTtAmount(null);
		setCashAmount(null);
	}

	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;

	public void clearCache(){

		fetchfinanceYear();
		getDocumentNumber();
		clear();
		fetchDealYear();
		getDocumentNoDropDown();
		setEditaleRefId(null);
		setRenderRefEditable(false);
		setBlue(false);
		setBooRenderSavePanel(true);
		setBooRenderUpdatePanel(false);
		dataTableForPurchaseSplPool().clear();
		bankListFoSale();
		setRenderMainPanel(true);	
		setSuccessRender(false);
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionManage.getCountryId(), sessionManage.getUserType(), sessionManage.getUserName(), "FXDetailInfoBeanApprove.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/FXDetailInfoBeanApprove.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void checkingCheckBoxVerification(){
		if(isSalesCheckbox()!=false && isPurchaseCheckbox()!=false){
			if(getDealWith().compareTo(getPurchaseBank()) == 0 || getDealWith().compareTo(getSaleBank()) == 0) {
				if(isSplCustMsg()){
					if(getTotalFcPurchaseAmount() != null && getFcLocalAmount() != null && getTotalFcPurchaseAmount().intValue() > 0 && getFcLocalAmount().intValue() > 0){
						saveTreasuryHeader();
					}else{
						RequestContext.getCurrentInstance().execute("specialCommonMandatory.show();");
					}
				}else{
					saveTreasuryHeader();
				}

			}else{
				RequestContext.getCurrentInstance().execute("dealBankNotMatch.show();");
			}

		}else{
			RequestContext.getCurrentInstance().execute("checkBoxVerify.show();");
		}
	}

	public void saveTreasuryHeader(){

		if(getBankBalError()){
			try{

				TreasuryDealHeader treasuryDealHeader = new TreasuryDealHeader();

				treasuryDealHeader.setTreasuryDealHeaderId(getTeasuryDealHeaderId());

				// Save Application Country
				CountryMaster applicationCountry = new CountryMaster();
				applicationCountry.setCountryId(sessionManage.getCountryId());
				treasuryDealHeader.setFsCountryMaster(applicationCountry);

				// save Company Master
				CompanyMaster companyMaster = new CompanyMaster();
				companyMaster.setCompanyId(getCompany());
				treasuryDealHeader.setFsCompanyMaster(companyMaster);

				// save Document
				Document document = new Document();
				document.setDocumentID(getDocumentNo());
				treasuryDealHeader.setExDocument(document);

				// save Document Finanace year
				/*UserFinancialYear documentRefyear = new UserFinancialYear();
			    documentRefyear.setFinancialYearID(getDealYearId());*/
				treasuryDealHeader.setUserFinanceYear(getDealYear());

				// save Bank
				BankMaster bankMaster = new BankMaster();
				bankMaster.setBankId(getDealWith());
				treasuryDealHeader.setExBankMaster(bankMaster);

				// save Language Type
				LanguageType langType = new LanguageType();
				langType.setLanguageId(sessionManage.getLanguageId());
				treasuryDealHeader.setFsLanguageType(langType);

				treasuryDealHeader.setDocumentDate(new SimpleDateFormat("dd/MM/yyyy").parse(getDealDate()));
				treasuryDealHeader.setContactName(getContact());
				treasuryDealHeader.setConcludedBy(getConcludedBy());
				treasuryDealHeader.setReutersReference(getReuterReference());
				treasuryDealHeader.setRemarks(getRemark());
				treasuryDealHeader.setAccyymm(new SimpleDateFormat("dd/MM/yyyy").parse(getCurrentDateWithFormat()));

				treasuryDealHeader.setDealWithType(Constants.Fx_BankDealType);// By Default Bank "B" Hard coded - Nashish

				//treasuryDealHeader.setDealWithCustomer(); // Deal with Customer want to add ID of Customer

				// Save Purchase Details
				treasuryDealHeader.setValueDate(getPurchaseValueDate());
				treasuryDealHeader.setMultiplicationDivision(getPurchaseMultipleDivision());
				treasuryDealHeader.setPurchaseExchangeRate(getPurchaseExchangeRate());

				// save purchase Requirement getPurchaseExchangeRate 
				treasuryDealHeader.setTotalPurchaseFCAmt(getTotalFcPurchaseAmount());//totalFcPurchaseAmount
				treasuryDealHeader.setPurchaseLocalRate(getLocalExchangeRate()); ///localExchangeRate
				treasuryDealHeader.setTotalPurchaseLocalAmt(getTotallocalAmount());//totallocalAmount
				//treasuryDealHeader.setSaleAmount(getSaleAmount()); //CR Functionality changed 
				//treasuryDealHeader.setSaleAmount(getPurchaseExchangeRate());
				treasuryDealHeader.setSaleAmount(getFcLocalAmount());//Total Sale Amount Ram


				if (getTeasuryDealHeaderId() != null && getTeasuryDealHeaderId().intValue() != 0) {
					treasuryDealHeader.setModifiedBy(sessionManage.getUserName());
					treasuryDealHeader.setModifiedDate(new Date());
					treasuryDealHeader.setCreatedBy(getCreatedByHeader());
					treasuryDealHeader.setCreatedDate(getCreatedDateHeader());
				} else {
					saveDocumentSerialID= new BigDecimal(getDocumentSerialID(Constants.U));
					setDocSerialIdNumberForSave(saveDocumentSerialID);
					treasuryDealHeader.setCreatedBy(sessionManage.getUserName());
					treasuryDealHeader.setCreatedDate(new Date());
				}

				treasuryDealHeader.setTreasuryDocumentNumber(getDocSerialIdNumberForSave());
				treasuryDealHeader.setValueDate(getDate3());
				treasuryDealHeader.setIsActive(Constants.U);

				HashMap<String,Object> mapAllDetailForSave = new HashMap<String,Object>();

				mapAllDetailForSave.put("TreasuryHeader", treasuryDealHeader);

				if(getFcAmount() != null ){
					mapAllDetailForSave.put("skipPurchaseTreasuryDetails", new Boolean(true));

					TreasuryDealDetail savePurchaseTDetails = saveTreasuryDetailsForPurchase(treasuryDealHeader);
					mapAllDetailForSave.put("PurchaseTreasuryDetails", savePurchaseTDetails);

					List<TreasuryDealDetail> lstPurchaseSplCustDt = saveSpecialCustomerDetails(treasuryDealHeader);
					mapAllDetailForSave.put("ListPurchaseSplCustomer", lstPurchaseSplCustDt);

					/*TreasuryStandardInstruction savePurchaseSI = savePurchaseStandardInstruction(treasuryDealHeader, savePurchaseTDetails);
					mapAllDetailForSave.put("PurchaseStandInstrn", savePurchaseSI);*/

					List<TreasuryStandardInstruction> lstPurchaseTSI = saveStandardInstrnForPurchase(treasuryDealHeader,savePurchaseTDetails);
					mapAllDetailForSave.put("ListPurchaseTreasurySI", lstPurchaseTSI);

				}else{
					mapAllDetailForSave.put("skipPurchaseTreasuryDetails", new Boolean(false));

					List<TreasuryDealDetail> lstPurchaseSplCustDt = saveSpecialCustomerDetails(treasuryDealHeader);
					mapAllDetailForSave.put("ListPurchaseSplCustomer", lstPurchaseSplCustDt);

					if(lstPurchaseSplCustDt.size() != 0){
						TreasuryDealDetail pkPurchaseTreDealDetails = (TreasuryDealDetail) lstPurchaseSplCustDt.get(0);

						/*TreasuryStandardInstruction savePurchaseSI = savePurchaseStandardInstruction(treasuryDealHeader, pkPurchaseTreDealDetails);
						mapAllDetailForSave.put("PurchaseStandInstrn", savePurchaseSI);*/

						List<TreasuryStandardInstruction> lstPurchaseTSI = saveStandardInstrnForPurchase(treasuryDealHeader,pkPurchaseTreDealDetails);
						mapAllDetailForSave.put("ListPurchaseTreasurySI", lstPurchaseTSI);
					}

				}

				if(lstofDeleteSplPool.size() != 0){
					mapAllDetailForSave.put("skipDeleteSplPoolTrDls", new Boolean(true));
					mapAllDetailForSave.put("deleteCusSplPoolDetails", lstofDeleteSplPool);
					mapAllDetailForSave.put("clearCusSplPoolDetails", lstofClearSplPool);
				}else{
					mapAllDetailForSave.put("skipDeleteSplPoolTrDls", new Boolean(false));
				}



				if(getEditaleRefId()!=null){
					for (TreasuryStandardInstruction lstPurchaseSI : getLstTresSIPur()) {
						if(lstPurchaseSI.getStandardInstructionNumber().compareTo(getPurchaseInstrunction())==0){
							mapAllDetailForSave.put("skipUpdatingTresSIPurchase", new Boolean(false));
							mapAllDetailForSave.put("lstTresSIToDeletePurchase", getLstTresSIPur());
						}else{
							mapAllDetailForSave.put("skipUpdatingTresSIPurchase", new Boolean(true));
							mapAllDetailForSave.put("lstTresSIToDeletePurchase", getLstTresSIPur());
						}
					}
				}else{
					mapAllDetailForSave.put("skipUpdatingTresSIPurchase", new Boolean(false));
				}

				TreasuryDealDetail saveSaleTDetails = saveTreasuryDetailsForSale(treasuryDealHeader);
				mapAllDetailForSave.put("SaleTreasuryDetails", saveSaleTDetails);

				/*TreasuryStandardInstruction saveSaleSI = saveSaleTStandardInstruction(treasuryDealHeader,saveSaleTDetails);
				mapAllDetailForSave.put("SaleTreasurySInstrn", saveSaleSI);*/

				if(getEditaleRefId()!=null){
					for (TreasuryStandardInstruction lstSaleSI : getLstTresSISale()) {
						if(lstSaleSI.getStandardInstructionNumber().compareTo(getSalePurchaseInstrunction())==0){
							mapAllDetailForSave.put("skipUpdatingTresSISale", new Boolean(false));
							mapAllDetailForSave.put("lstTresSIToDeleteSale", getLstTresSISale());
						}else{
							mapAllDetailForSave.put("skipUpdatingTresSISale", new Boolean(true));
							mapAllDetailForSave.put("lstTresSIToDeleteSale", getLstTresSISale());
						}
					}
				}else{
					mapAllDetailForSave.put("skipUpdatingTresSISale", new Boolean(false));
				}

				List<TreasuryStandardInstruction> lstSaleTSI = saveStandardInstrnForSale(treasuryDealHeader,saveSaleTDetails);
				mapAllDetailForSave.put("ListSaleTreasurySI", lstSaleTSI);

				fXDetailInformationService.saveAllFXDealBank(mapAllDetailForSave);

				if(getEditaleRefId()==null){
					RequestContext.getCurrentInstance().execute("complete.show();");
				}else{
					RequestContext.getCurrentInstance().execute("updatecomplete.show();");
				}	

			}catch(NullPointerException  e){ 
				setErrorMsg("saveTreasuryHeader :");
				RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
			}
			catch (Exception e) {
				setErrorMsg(e.getMessage());
				RequestContext.getCurrentInstance().execute("csp.show();");
			}
		}else{
			RequestContext.getCurrentInstance().execute("bankBalerror.show();");
		}
	}

	public TreasuryDealDetail saveTreasuryDetailsForPurchase(TreasuryDealHeader treasuryDealHeader){

		TreasuryDealDetail treasuryDealDetail = new TreasuryDealDetail();

		try{

			// Save PK
			treasuryDealDetail.setTreasuryDealDetailId(getPurchaseDealDetId());

			treasuryDealHeader.setTreasuryDealHeaderId(treasuryDealHeader.getTreasuryDealHeaderId());
			treasuryDealDetail.setTreasuryDealHeader(treasuryDealHeader);
			// save Bank
			BankMaster bankMaster = new BankMaster();
			bankMaster.setBankId(getPurchaseBank());
			treasuryDealDetail.setTreasuryDealBankMaster(bankMaster);

			/*AccountBalance accBal = new AccountBalance();
		      accBal.setAccountId(getSaleAccountBalanceId());
		      treasuryDealDetail.setAccountBalance(accBal);*/

			BankAccountDetails bankAccountDetails = new BankAccountDetails();
			bankAccountDetails.setBankAcctDetId(getPurchaseAccountNumber());
			treasuryDealDetail.setTreasuryDealDetailBankAccountDetails(bankAccountDetails);

			// save Currency
			CurrencyMaster currencyMaster = new CurrencyMaster();
			currencyMaster.setCurrencyId(getPurchaseCurrency());
			treasuryDealDetail.setTreasuryDealDetailCurrencyMaster(currencyMaster);

			CountryMaster applicationCountry = new CountryMaster();
			applicationCountry.setCountryId(sessionManage.getCountryId());
			treasuryDealDetail.setTreasuryDealCountryMaster(applicationCountry);

			treasuryDealDetail.setValueDate(getDate3());
			System.out.println("Value Date for Puchase"+getDate3());

			treasuryDealDetail.setMultiplicationDivision(getPurchaseMultipleDivision());

			CompanyMaster companyMasters = new CompanyMaster();
			companyMasters.setCompanyId(sessionManage.getCompanyId());
			treasuryDealDetail.setTreasuryDealCompanyMaster(companyMasters);

			treasuryDealDetail.setTreasuryDealUserFinanceYear(getDealYear());
			treasuryDealDetail.setDocumentNumber(treasuryDealHeader.getTreasuryDocumentNumber());
			treasuryDealDetail.setIsActive(Constants.U);

			treasuryDealDetail.setLineNumber(new BigDecimal(1));// Hard coded


			treasuryDealDetail.setLocalExchangeRate(getLocalExchangeRate());
			treasuryDealDetail.setExchange(getPurchaseExchangeRate());

			if(getFcAmount() != null){
				// save purchase Requirment for Common Pool
				treasuryDealDetail.setFcAmount(getFcAmount());
				//treasuryDealDetail.setLocalAmount(getTotallocalAmount());
				treasuryDealDetail.setLocalAmount(getFcAmount().multiply(getLocalExchangeRate()));
				treasuryDealDetail.setSaleAmount(getFcSaleAmount());
			}

			/*Document documents = new Document();
		      documents.setDocumentID(getDocumentNo());*/
			treasuryDealDetail.setTreasuryDealDocument(treasuryDealHeader.getExDocument());
			treasuryDealDetail.setFaAccountNo(getFaAccountNumberForPurchase());

			/*StandardInstruction standInst = new StandardInstruction();
		      standInst.setStandardInstructionId(getPurchaseInstrunction());*/
			treasuryDealDetail.setStandardInstruction(getPurchaseInstrunction());
			treasuryDealDetail.setLineType(Constants.PD);

			if (getPurchaseDealDetId() != null && getPurchaseDealDetId().intValue() != 0) {
				treasuryDealDetail.setModifiedBy(sessionManage.getUserName());
				treasuryDealDetail.setModifiedDate(new Date());
				treasuryDealDetail.setCreatedBy(getCreatedByDetailPurchase());
				treasuryDealDetail.setCreatedDate(getCreatedDateDetailPurchase());
			} else {
				treasuryDealDetail.setCreatedBy(sessionManage.getUserName());
				treasuryDealDetail.setCreatedDate(new Date());
			}

		}catch(NullPointerException  e){ 
			setErrorMsg("saveTreasuryDetailsForPurchase :");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}

		return treasuryDealDetail;
	}

	public TreasuryStandardInstruction savePurchaseStandardInstruction(TreasuryDealHeader treasuryDealHeader,TreasuryDealDetail treasuryDealDetail){

		TreasuryStandardInstruction standardInstruct = new TreasuryStandardInstruction();

		try{

			standardInstruct.setTreasuryStandardInstructionId(getPurchaseTreasuryInstructionId());

			// save Standard instruction
			standardInstruct.setStandardInstructionNumber(getPurchaseInstrunction());
			standardInstruct.setMessageDescription(getPurchaseInstructionDesc());

			// Save Application Country
			CountryMaster applicationCountry = new CountryMaster();
			applicationCountry.setCountryId(sessionManage.getCountryId());
			standardInstruct.setTreasuryCountryMaster(applicationCountry);

			// save Company Master
			CompanyMaster companyMasters = new CompanyMaster();
			companyMasters.setCompanyId(sessionManage.getCompanyId());
			standardInstruct.setTreasurycomCompanyMaster(companyMasters);

			//saving Treasury details Id
			standardInstruct.setTreasuryDealDetail(treasuryDealDetail);

			// save Document
			standardInstruct.setTreasurydocDocument(treasuryDealHeader.getExDocument());

			// save Document Finance year
			standardInstruct.setTreasDocumentFinancialYear(getDealYear());

			standardInstruct.setDucumentNumber(treasuryDealHeader.getTreasuryDocumentNumber());
			standardInstruct.setLineType(Constants.PD); 
			standardInstruct.setMessageLineNumber(getLineNumberforPurchase()); 
			standardInstruct.setIsActive(Constants.U); 

			// save Language Type
			standardInstruct.setTreasuryLanguageType(treasuryDealHeader.getFsLanguageType());

			if (getPurchaseTreasuryInstructionId() != null	&& getPurchaseTreasuryInstructionId().intValue() != 0) {
				standardInstruct.setModifiedBy(sessionManage.getUserName());
				standardInstruct.setModifiedDate(new Date());
				standardInstruct.setCreatedBy(getCreatedByInstPurchase());
				standardInstruct.setCreatedDate(getCreatedDateInstPurchase());
			} else {
				standardInstruct.setCreatedBy(sessionManage.getUserName());
				standardInstruct.setCreatedDate(new Date());
			}

		}catch(NullPointerException  e){ 
			setErrorMsg("savePurchaseStandardInstruction :");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}

		return standardInstruct;
	}

	public List<TreasuryStandardInstruction> saveStandardInstrnForPurchase(TreasuryDealHeader treasuryDealHeader,TreasuryDealDetail treasuryDealDetail){

		List<TreasuryStandardInstruction> lstTreasurySI = new ArrayList<TreasuryStandardInstruction>();
		try{
			//CR Adding Instruction Number and Description into TreasuryStandardInstruction for Purchase

			int lineNumber = 1;

			for (StandardInstructionDetails stndInstrnDetails : cstandardInstrnDetailsPurchase) {
				if (purchaseCheckbox) {

					TreasuryStandardInstruction stndInstrnforDataTable = new TreasuryStandardInstruction();

					//stndInstrnforDataTable.setTreasuryStandardInstructionId(getPurchaseTreasuryInstructionId());

					// Save Application Country
					CountryMaster applicationCountry = new CountryMaster();
					applicationCountry.setCountryId(sessionManage.getCountryId());
					stndInstrnforDataTable.setTreasuryCountryMaster(applicationCountry);

					// save Company Master
					CompanyMaster companyMasters = new CompanyMaster();
					companyMasters.setCompanyId(sessionManage.getCompanyId());
					stndInstrnforDataTable.setTreasurycomCompanyMaster(companyMasters);

					stndInstrnforDataTable.setStandardInstructionNumber(getPurchaseInstrunction());

					// save Document
					stndInstrnforDataTable.setTreasurydocDocument(treasuryDealHeader.getExDocument());

					// save Document Finanace year
					stndInstrnforDataTable.setTreasDocumentFinancialYear(getDealYear());

					stndInstrnforDataTable.setDucumentNumber(treasuryDealHeader.getTreasuryDocumentNumber());

					stndInstrnforDataTable.setLineType(Constants.PD); // Hard Coded

					// save Standard instruction

					stndInstrnforDataTable.setMessageLineNumber(new BigDecimal(lineNumber++));
					stndInstrnforDataTable.setMessageDescription(stndInstrnDetails.getLineDescription());

					stndInstrnforDataTable.setIsActive(Constants.U); // Hard Coded

					// save Language Type
					stndInstrnforDataTable.setTreasuryLanguageType(treasuryDealHeader.getFsLanguageType());


					/*if (getPurchaseTreasuryInstructionId() != null	&& getPurchaseTreasuryInstructionId().intValue() != 0) {
					stndInstrnforDataTable.setModifiedBy(sessionManage.getUserName());
					stndInstrnforDataTable.setModifiedDate(new Date());
				} else {*/
					stndInstrnforDataTable.setCreatedBy(sessionManage.getUserName());
					stndInstrnforDataTable.setCreatedDate(new Date());
					//}

					stndInstrnforDataTable.setTreasuryDealDetail(treasuryDealDetail);

					lstTreasurySI.add(stndInstrnforDataTable);

				}

			}
		}catch(NullPointerException  e){ 
			setErrorMsg("Method:saveStandardInstrnForPurchase:");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}

		return lstTreasurySI;
	}

	public List<TreasuryDealDetail> saveSpecialCustomerDetails(TreasuryDealHeader treasuryDealHeader){

		List<TreasuryDealDetail> lstSplCustDt = new ArrayList<TreasuryDealDetail>();
		try{
			int lineNumberpurchase = 1;

			for (PurchaseReqSplPoolDataTable purchaseSplPool : purchaseReqSplPoolDataTableList) {

				if (purchaseSplPool.getSelectRecord()) {

					TreasuryDealDetail treasuryDealDetails = new TreasuryDealDetail();

					treasuryDealDetails.setTreasuryDealHeader(treasuryDealHeader);

					CustomerSpecialDealRequest custSplDealReq = new CustomerSpecialDealRequest();
					custSplDealReq.setCustomerSpecialDealReqId(purchaseSplPool.getCustomerSpecialDealReqId());
					treasuryDealDetails.setCustomerSpecialDealRequest(custSplDealReq);

					// custSplDealReq.setCustomerSpeacialDealReqCustomer(purchaseSplPool.getCustomerId().toPlainString());
					// save customer Id

					//customer id is replaced with First name @ chiru
					/*Customer customer = new Customer();
				customer.setCustomerId(purchaseSplPool.getCustomerId());*/
					treasuryDealDetails.setCustomerReference(purchaseSplPool.getCustomerReference());

					/*Customer customer = new Customer();
				customer.setFirstName(purchaseSplPool.getCustomerFirstName());
				treasuryDealDetails.setCustomerReference(customer);*/

					// save Doc
					/* Document doc = new Document();
				 doc.setDocumentID(purchaseSplPool.getSplReqDocCode());*/
					treasuryDealDetails.setSpecialRequestDocumentCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_SPL_CUSTOMER_DEAL_REQUEST));

					/*// save user Finance Year
			    UserFinancialYear userFinYear = new UserFinancialYear();
				userFinYear.setFinancialYear(purchaseSplPool.getSplReqFinanceYear());
				treasuryDealDetails.setTreasuryDealUserFinanceYear(userFinYear);*/

					treasuryDealDetails.setSpecialRequestFinanceYear(purchaseSplPool.getSplReqFinanceYear());

					treasuryDealDetails.setDocumentNumber(treasuryDealHeader.getTreasuryDocumentNumber());

					treasuryDealDetails.setTreasuryDealUserFinanceYear(getDealYear());

					treasuryDealDetails.setStandardInstruction(getPurchaseInstrunction());

					BankMaster bankMaster = new BankMaster();
					bankMaster.setBankId(getPurchaseBank());
					treasuryDealDetails.setTreasuryDealBankMaster(bankMaster);

					CurrencyMaster currencyMaster = new CurrencyMaster();
					currencyMaster.setCurrencyId(getPurchaseCurrency());
					treasuryDealDetails.setTreasuryDealDetailCurrencyMaster(currencyMaster);

					BankAccountDetails bankAccountDetails = new BankAccountDetails();
					bankAccountDetails.setBankAcctDetId(getPurchaseAccountNumber());
					treasuryDealDetails.setTreasuryDealDetailBankAccountDetails(bankAccountDetails);

					treasuryDealDetails.setValueDate(getDate3());
					System.out.println("Value Date for Puchase"+getDate3());

					treasuryDealDetails.setMultiplicationDivision(getPurchaseMultipleDivision());

					// save company
					/* CompanyMaster compMaster = new CompanyMaster();
				 compMaster.setCompanyId(purchaseSplPool.getSplReqCompanyId());*/
					treasuryDealDetails.setSpecialRequestCompanyId(purchaseSplPool.getSplReqCompanyId());

					// save SP Customer Company ID
					CompanyMaster companyMast1 = new CompanyMaster();
					companyMast1.setCompanyId(sessionManage.getCompanyId());
					treasuryDealDetails.setTreasuryDealCompanyMaster(companyMast1);


					treasuryDealDetails.setFcAmount(purchaseSplPool.getForeignCurrencyAmount());
					//treasuryDealDetails.setLocalAmount(purchaseSplPool.getLocalAmount());
					treasuryDealDetails.setLocalAmount(purchaseSplPool.getForeignCurrencyAmount().multiply(getLocalExchangeRate()));
					treasuryDealDetails.setLineType(Constants.PD);

					CountryMaster applicationCountry1 = new CountryMaster();
					applicationCountry1.setCountryId(sessionManage.getCountryId());
					treasuryDealDetails.setTreasuryDealCountryMaster(applicationCountry1);

					treasuryDealDetails.setIsActive(Constants.U);
					treasuryDealDetails.setLineNumber(new BigDecimal(lineNumberpurchase++));
					//treasuryDealDetails.setLocalExchangeRate(new BigDecimal(0.091));// Hard Coded

					Document doc1 = new Document();
					doc1.setDocumentID(getDocumentNo());
					treasuryDealDetails.setTreasuryDealDocument(doc1);

					/*  UserFinancialYear userFinYear1 = new UserFinancialYear();
				userFinYear1.setFinancialYear(new BigDecimal(13));
				treasuryDealDetails.setTreasuryDealUserFinanceYear(userFinYear1);*/

					treasuryDealDetails.setSpecialRequestDocNumber(purchaseSplPool.getDocumentNumber());

					treasuryDealDetails.setLocalExchangeRate(getLocalExchangeRate());//localExchangeRate
					treasuryDealDetails.setExchange(getPurchaseExchangeRate());
					treasuryDealDetails.setTreasuryDealDetailId(purchaseSplPool.getSplPoolPurchaseDetId());
					treasuryDealDetails.setSaleAmount(purchaseSplPool.getLocalAmount());

					//treasuryDealDetails.setLocalAmount(getTotallocalAmount());


					if (purchaseSplPool.getSplPoolPurchaseDetId() != null && purchaseSplPool.getSplPoolPurchaseDetId().intValue() != 0) {
						treasuryDealDetails.setModifiedBy(sessionManage.getUserName());
						treasuryDealDetails.setModifiedDate(new Date());
						treasuryDealDetails.setCreatedBy(getCreatedByDetailPurchase());
						treasuryDealDetails.setCreatedDate(getCreatedDateDetailPurchase());
					} else {
						treasuryDealDetails.setCreatedBy(sessionManage.getUserName());
						treasuryDealDetails.setCreatedDate(new Date());
					}

					lstSplCustDt.add(treasuryDealDetails);

				}

			}
		}catch(NullPointerException  e){ 
			setErrorMsg("Method:saveSpecialCustomerDetails :");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
		return lstSplCustDt;

	}

	/*public List<CustomerSpecialDealRequest> updateSpecialCustomerDetails(List<TreasuryDealDetail> lstPurchaseSplCustDt){

		List<CustomerSpecialDealRequest> lstofSplCustomer = new ArrayList<CustomerSpecialDealRequest>();

		for (TreasuryDealDetail treasuryDealDetail : lstPurchaseSplCustDt) {

			CustomerSpecialDealRequest lstCustSplDlReq = new CustomerSpecialDealRequest();

			lstCustSplDlReq.setCustomerSpecialDealReqId(treasuryDealDetail.getCustomerSpecialDealRequest().getCustomerSpecialDealReqId());
			lstCustSplDlReq.setDealCompanyId(treasuryDealDetail.getTreasuryDealCompanyMaster().getCompanyId());
			lstCustSplDlReq.setDealId(treasuryDealDetail.getTreasuryDealDocument().getDocumentCode());
			lstCustSplDlReq.setDealFinanceYear(treasuryDealDetail.getTreasuryDealUserFinanceYear());
			lstCustSplDlReq.setDealApplicationNumber(treasuryDealDetail.getDocumentNumber());

			lstofSplCustomer.add(lstCustSplDlReq);
		}
		return lstofSplCustomer;
	}*/

	public TreasuryDealDetail saveTreasuryDetailsForSale(TreasuryDealHeader treasuryDealHeader){

		TreasuryDealDetail treasuryDealDetsale = new TreasuryDealDetail();

		try{

			// save Bank
			BankMaster bankMaster = new BankMaster();
			bankMaster.setBankId(getSaleBank());
			treasuryDealDetsale.setTreasuryDealBankMaster(bankMaster);

			// save Currency
			CurrencyMaster currencyMaster = new CurrencyMaster();
			currencyMaster.setCurrencyId(getSaleCurrency());
			treasuryDealDetsale.setTreasuryDealDetailCurrencyMaster(currencyMaster);

			treasuryDealDetsale.setDocumentNumber(treasuryDealHeader.getTreasuryDocumentNumber());

			// Save Application Country
			CountryMaster applicationCountry = new CountryMaster();
			applicationCountry.setCountryId(sessionManage.getCountryId());
			treasuryDealDetsale.setTreasuryDealCountryMaster(applicationCountry);

			// Save Account Number
			BankAccountDetails bankAccountDetails = new BankAccountDetails();
			bankAccountDetails.setBankAcctDetId(new BigDecimal(getSaleAccountNumber()));
			treasuryDealDetsale.setTreasuryDealDetailBankAccountDetails(bankAccountDetails);

			treasuryDealDetsale.setMultiplicationDivision(getPurchaseMultipleDivision());

			treasuryDealDetsale.setTreasuryDealHeader(treasuryDealHeader);

			//UserFinancialYear documentRefyear = new UserFinancialYear();
			//documentRefyear.setFinancialYearID(getDealYearId());
			treasuryDealDetsale.setTreasuryDealUserFinanceYear(getDealYear());

			// StandardInstruction standInst = new StandardInstruction();
			//standInst.setStandardInstructionId(getSalePurchaseInstrunction());
			treasuryDealDetsale.setStandardInstruction(getSalePurchaseInstrunction());
			treasuryDealDetsale.setLineType(Constants.SD);
			treasuryDealDetsale.setFaAccountNo(getFaAccountNumberForSale());

			AccountBalance accBal = new AccountBalance();
			accBal.setAccountId(getSaleAccountBalanceId());
			treasuryDealDetsale.setAccountBalance(accBal);

			CompanyMaster compMaster = new CompanyMaster();
			compMaster.setCompanyId(sessionManage.getCompanyId());
			treasuryDealDetsale.setTreasuryDealCompanyMaster(compMaster);

			Document doc = new Document();
			doc.setDocumentID(getDocumentNo());
			treasuryDealDetsale.setTreasuryDealDocument(doc);

			treasuryDealDetsale.setLineNumber(new BigDecimal(1));// Hard coded
			treasuryDealDetsale.setLocalExchangeRate(getSaleAvgRate());
			//treasuryDealDetsale.setExchange(getPurchaseExchangeRate());

			// treasuryDealDetsale.setAccountBalance(accountBalance);

			// treasuryDealDetsale.setValueDate(new
			// SimpleDateFormat("dd/MM/yyyy").parse(getSaleValueDate()));
			treasuryDealDetsale.setValueDate(getSaleValueDate());
			treasuryDealDetsale.setLocalAmount(getSaleLocalAmount());//saleLocalAmount
			treasuryDealDetsale.setFcAmount(getSaleAmount());//saleAmount
			treasuryDealDetsale.setAvgRate(getSaleAvgRate());//saleAvgRate
			treasuryDealDetsale.setIsActive(Constants.U);
			treasuryDealDetsale.setSaleAmount(getSaleAmount());////saleAmount*** Ram

			treasuryDealDetsale.setTreasuryDealDetailId(getSaleDealDetId());

			if (getSaleDealDetId() != null && getSaleDealDetId().intValue() != 0) {
				treasuryDealDetsale.setModifiedBy(sessionManage.getUserName());
				treasuryDealDetsale.setModifiedDate(new Date());
				treasuryDealDetsale.setCreatedBy(getCreatedByDetailSale());
				treasuryDealDetsale.setCreatedDate(getCreatedDateDetailSale());

			} else {
				treasuryDealDetsale.setCreatedBy(sessionManage.getUserName());
				treasuryDealDetsale.setCreatedDate(new Date());
			}

		}catch(NullPointerException  e){ 
			setErrorMsg("saveTreasuryDetailsForSale :");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
		return treasuryDealDetsale;
	}

	public TreasuryStandardInstruction saveSaleTStandardInstruction(TreasuryDealHeader treasuryDealHeader,TreasuryDealDetail treasuryDealDetsale){
		// save Standard instruction
		TreasuryStandardInstruction treasurystandardInstruction = new TreasuryStandardInstruction();

		try{
			treasurystandardInstruction.setStandardInstructionNumber(getSalePurchaseInstrunction());
			treasurystandardInstruction.setMessageDescription(getSalePurchaseInstructionDesc());
			treasurystandardInstruction.setTreasuryStandardInstructionId(getSaleTreasuryInstructionId());

			treasurystandardInstruction.setTreasuryDealDetail(treasuryDealDetsale);

			// save Language Type
			// LanguageType langType = new LanguageType();
			// langType.setLanguageId(sessionManage.getLanguageId());
			treasurystandardInstruction.setTreasuryLanguageType(treasuryDealHeader.getFsLanguageType());

			treasurystandardInstruction.setIsActive(Constants.U);

			// Document doc = new Document();
			// doc.setDocumentID(getDocumentNo());
			treasurystandardInstruction.setTreasurydocDocument(treasuryDealHeader.getExDocument());

			treasurystandardInstruction.setDucumentNumber(treasuryDealHeader.getTreasuryDocumentNumber());

			treasurystandardInstruction.setLineType(Constants.SD); // Hard Coded

			treasurystandardInstruction.setTreasDocumentFinancialYear(getDealYear());


			// Save Application Country
			CountryMaster applicationCountry = new CountryMaster();
			applicationCountry.setCountryId(sessionManage.getCountryId());
			treasurystandardInstruction.setTreasuryCountryMaster(applicationCountry);

			// save Company Master
			CompanyMaster compMaster = new CompanyMaster();
			compMaster.setCompanyId(sessionManage.getCompanyId());
			treasurystandardInstruction.setTreasurycomCompanyMaster(compMaster);
			treasurystandardInstruction.setMessageLineNumber(getLineNumberforSale()); // Hard Coded

			if (getSaleTreasuryInstructionId() != null && getSaleTreasuryInstructionId().intValue() != 0) {
				treasurystandardInstruction.setModifiedBy(sessionManage.getUserName());
				treasurystandardInstruction.setModifiedDate(new Date());
				treasurystandardInstruction.setCreatedBy(getCreatedByInstSale());
				treasurystandardInstruction.setCreatedDate(getCreatedDateInstSale());

			} else {
				treasurystandardInstruction.setCreatedBy(sessionManage.getUserName());
				treasurystandardInstruction.setCreatedDate(new Date());
			}

		}catch(NullPointerException  e){ 
			setErrorMsg("saveSaleTStandardInstruction :");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}

		return treasurystandardInstruction;
	}

	public List<TreasuryStandardInstruction> saveStandardInstrnForSale(TreasuryDealHeader treasuryDealHeader,TreasuryDealDetail treasuryDealDetsale){

		List<TreasuryStandardInstruction> lstStndInstrnSale = new ArrayList<TreasuryStandardInstruction>();

		//CR Adding Instruction Number and Description into TreasuryStandardInstruction for Purchase
		try{
			int lineNumberSale = 1;

			for (StandardInstructionDetails stndInstrnDetails : cstandardInstrnDetailsforSales) {
				if (salesCheckbox) {
					System.out.println("===========salesCheckbox==========="	+ salesCheckbox);
					//for (CustomerSpecialDealRequest custSplDealReq : purchaseSpecialPoolDataTable) {

					TreasuryStandardInstruction stndInstrnforDataTableforSale = new TreasuryStandardInstruction();

					//stndInstrnforDataTableforSale.setTreasuryStandardInstructionId(getSaleTreasuryInstructionId());

					stndInstrnforDataTableforSale.setTreasuryDealDetail(treasuryDealDetsale);

					// Save Application Country
					CountryMaster applicationCountry = new CountryMaster();
					applicationCountry.setCountryId(sessionManage.getCountryId());
					stndInstrnforDataTableforSale.setTreasuryCountryMaster(applicationCountry);

					// save Company Master
					CompanyMaster companyMasters = new CompanyMaster();
					companyMasters.setCompanyId(sessionManage.getCompanyId());
					stndInstrnforDataTableforSale.setTreasurycomCompanyMaster(companyMasters);

					stndInstrnforDataTableforSale.setStandardInstructionNumber(getSalePurchaseInstrunction());

					// save Document

					stndInstrnforDataTableforSale.setTreasurydocDocument(treasuryDealHeader.getExDocument());

					// save Document Finanace year

					stndInstrnforDataTableforSale.setTreasDocumentFinancialYear(getDealYear());

					stndInstrnforDataTableforSale.setDucumentNumber(treasuryDealHeader.getTreasuryDocumentNumber());

					stndInstrnforDataTableforSale.setLineType(Constants.SD); // Hard Coded

					// save Standard instruction

					stndInstrnforDataTableforSale.setMessageLineNumber(new BigDecimal(lineNumberSale++));
					stndInstrnforDataTableforSale.setMessageDescription(stndInstrnDetails.getLineDescription());

					stndInstrnforDataTableforSale.setIsActive(Constants.U); // Hard Coded

					// save Language Type

					stndInstrnforDataTableforSale.setTreasuryLanguageType(treasuryDealHeader.getFsLanguageType());



					/*if (getSaleTreasuryInstructionId() != null	&& getSaleTreasuryInstructionId().intValue() != 0) {
					stndInstrnforDataTableforSale.setModifiedBy(sessionManage.getUserName());
					stndInstrnforDataTableforSale.setModifiedDate(new Date());
				} else {*/
					stndInstrnforDataTableforSale.setCreatedBy(sessionManage.getUserName());
					stndInstrnforDataTableforSale.setCreatedDate(new Date());
					//	}

					lstStndInstrnSale.add(stndInstrnforDataTableforSale);

					// fXDetailInformationService.saveSaleStandardInst(stndInstrnforDataTableforSale);
				}

			}
		}catch(NullPointerException  e){ 
			setErrorMsg("method :saveStandardInstrnForSale:");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
		return lstStndInstrnSale;
	}



	/*
	 * Save EX_TREASURY_DEAL_
	 */



	private BigDecimal editaleRefId=null;
	private Boolean renderRefEditable= false;
	private Boolean renderRef= true;

	private String createdByHeader;
	private Date createdDateHeader;

	private String createdByDetailPurchase;
	private Date createdDateDetailPurchase;

	private String createdByDetailSplCustomer;
	private Date createdDateDetailSplCustomer;

	private String createdByDetailSale;
	private Date createdDateDetailSale;

	private String createdByInstPurchase;
	private Date createdDateInstPurchase;

	private String createdByInstSale;
	private Date createdDateInstSale;



	public String getCreatedByInstPurchase() {
		return createdByInstPurchase;
	}

	public void setCreatedByInstPurchase(String createdByInstPurchase) {
		this.createdByInstPurchase = createdByInstPurchase;
	}

	public Date getCreatedDateInstPurchase() {
		return createdDateInstPurchase;
	}

	public void setCreatedDateInstPurchase(Date createdDateInstPurchase) {
		this.createdDateInstPurchase = createdDateInstPurchase;
	}

	public String getCreatedByInstSale() {
		return createdByInstSale;
	}

	public void setCreatedByInstSale(String createdByInstSale) {
		this.createdByInstSale = createdByInstSale;
	}

	public Date getCreatedDateInstSale() {
		return createdDateInstSale;
	}

	public void setCreatedDateInstSale(Date createdDateInstSale) {
		this.createdDateInstSale = createdDateInstSale;
	}

	public String getCreatedByHeader() {
		return createdByHeader;
	}

	public void setCreatedByHeader(String createdByHeader) {
		this.createdByHeader = createdByHeader;
	}

	public Date getCreatedDateHeader() {
		return createdDateHeader;
	}

	public void setCreatedDateHeader(Date createdDateHeader) {
		this.createdDateHeader = createdDateHeader;
	}

	public String getCreatedByDetailPurchase() {
		return createdByDetailPurchase;
	}

	public void setCreatedByDetailPurchase(String createdByDetailPurchase) {
		this.createdByDetailPurchase = createdByDetailPurchase;
	}

	public Date getCreatedDateDetailPurchase() {
		return createdDateDetailPurchase;
	}

	public void setCreatedDateDetailPurchase(Date createdDateDetailPurchase) {
		this.createdDateDetailPurchase = createdDateDetailPurchase;
	}

	public String getCreatedByDetailSplCustomer() {
		return createdByDetailSplCustomer;
	}

	public void setCreatedByDetailSplCustomer(String createdByDetailSplCustomer) {
		this.createdByDetailSplCustomer = createdByDetailSplCustomer;
	}

	public Date getCreatedDateDetailSplCustomer() {
		return createdDateDetailSplCustomer;
	}

	public void setCreatedDateDetailSplCustomer(Date createdDateDetailSplCustomer) {
		this.createdDateDetailSplCustomer = createdDateDetailSplCustomer;
	}

	public String getCreatedByDetailSale() {
		return createdByDetailSale;
	}

	public void setCreatedByDetailSale(String createdByDetailSale) {
		this.createdByDetailSale = createdByDetailSale;
	}

	public Date getCreatedDateDetailSale() {
		return createdDateDetailSale;
	}

	public void setCreatedDateDetailSale(Date createdDateDetailSale) {
		this.createdDateDetailSale = createdDateDetailSale;
	}

	public BigDecimal getEditaleRefId() {
		return editaleRefId;
	}

	public void setEditaleRefId(BigDecimal editaleRefId) {
		this.editaleRefId = editaleRefId;
	}

	public Boolean getRenderRefEditable() {
		return renderRefEditable;
	}

	public void setRenderRefEditable(Boolean renderRefEditable) {
		this.renderRefEditable = renderRefEditable;
	}

	public Boolean getRenderRef() {
		return renderRef;
	}

	public void setRenderRef(Boolean renderRef) {
		this.renderRef = renderRef;
	}

	public void editableDealDate(){
		clearCache();
		setRenderdealdate(false);
		setRenderdealedit(true);

	}

	public void editableRefereneceNo(){
		clearCache();
		setRenderRef(false);
		setBooRenderSavePanel(false);
		setBooRenderUpdatePanel(true);
		setRenderRefEditable(true);
		setDealReference(null);
		dataTableForPurchaseSplPool().clear();
		purchaseReqSplPoolDataTableList.clear();
		lstofUnApproved.clear();
		List<TreasuryDealHeader> lstofUnApprovedTDlHeader = fXDetailInformationService.fetchDocumentNumberFromTreasDealheader(Constants.Fx_BankDealType ,Constants.U,getDealYear());
		if(lstofUnApprovedTDlHeader.size()!=0){
			setLstofUnApproved(lstofUnApprovedTDlHeader);
		}
	}

	public String modifiedByHeader;
	public Date modifiedDateHeader;

	public String getModifiedByHeader() {
		return modifiedByHeader;
	}

	public void setModifiedByHeader(String modifiedByHeader) {
		this.modifiedByHeader = modifiedByHeader;
	}

	public Date getModifiedDateHeader() {
		return modifiedDateHeader;
	}

	public void setModifiedDateHeader(Date modifiedDateHeader) {
		this.modifiedDateHeader = modifiedDateHeader;
	}

	public void fetchingAllRecordsFromDB() {

		try{

			//Details From Treasury Header Details
			List<TreasuryDealHeader> treasuryDealDetailsList = fXDetailInformationService.getHeaderDetails(getDocumentNo(),getEditaleRefId(),Constants.Fx_BankDealType,getDealYear(),sessionManage.getCompanyId());
			if(!(treasuryDealDetailsList.get(0).getModifiedBy() == null ? treasuryDealDetailsList.get(0).getCreatedBy() : treasuryDealDetailsList.get(0).getModifiedBy()).equalsIgnoreCase(sessionManage.getUserName())){
				if(treasuryDealDetailsList.size()!=0){
					if(treasuryDealDetailsList.get(0).getApprovedBy() !=null && treasuryDealDetailsList.get(0).getApprovedDate()!=null){
						RequestContext.getCurrentInstance().execute("docapprvdone.show();");
						setEditaleRefId(null);
					}else{
						//First Panel Details
						BigDecimal trStandInsIdPurchase= BigDecimal.ZERO;
						BigDecimal trStandInsIdSale= BigDecimal.ZERO;
						BigDecimal commomnTrDetalId=BigDecimal.ZERO;
						setTeasuryDealHeaderId(treasuryDealDetailsList.get(0).getTreasuryDealHeaderId());
						//setEditaleRefId(getEditaleRefId());
						setDealReference(getEditaleRefId().toPlainString());
						//setTeasuryDealHeaderId(treasuryDealDetailsList.get(0).getTreasuryDealHeaderId());
						setDocSerialIdNumberForSave(getEditaleRefId());

						setCompany(treasuryDealDetailsList.get(0).getFsCompanyMaster().getCompanyId());
						setDocument(treasuryDealDetailsList.get(0).getExDocument().getDocumentDesc());
						setDocumentNo(treasuryDealDetailsList.get(0).getExDocument().getDocumentID());
						setDealYear(treasuryDealDetailsList.get(0).getUserFinanceYear());
						setDealDate(treasuryDealDetailsList.get(0).getDocumentDate().toString());
						setDealDateStr(new SimpleDateFormat("dd/MM/yyyy").format(treasuryDealDetailsList.get(0).getDocumentDate()));
						setDealWith(treasuryDealDetailsList.get(0).getExBankMaster().getBankId());
						setContact(treasuryDealDetailsList.get(0).getContactName());
						setConcludedBy(treasuryDealDetailsList.get(0).getConcludedBy());
						setReuterReference(treasuryDealDetailsList.get(0).getReutersReference());
						setRemark(treasuryDealDetailsList.get(0).getRemarks());
						setCreatedByHeader(treasuryDealDetailsList.get(0).getCreatedBy());
						setCreatedDateHeader(treasuryDealDetailsList.get(0).getCreatedDate());
						setModifiedByHeader(treasuryDealDetailsList.get(0).getModifiedBy());
						setModifiedDateHeader(treasuryDealDetailsList.get(0).getModifiedDate());

						//Second Panel - Purchase Details Bank

						List<TreasuryDealDetail> treasuryDealDetailList = fXDetailInformationService.getDetailsPurchase(getTeasuryDealHeaderId(), Constants.PD);

						List<PurchaseReqSplPoolDataTable> lstofSPlPool = dataTableForPurchaseSplPool();
						List<TreasuryDealDetail> treasuryDealDetailListForsplPool = fXDetailInformationService.getDetailsSpecialPool(getEditaleRefId(), Constants.PD,getDocumentNo(),getDealYear(),sessionManage.getCompanyId());

						if(treasuryDealDetailList.size()!=0){
							setPurchaseBank(treasuryDealDetailList.get(0).getTreasuryDealBankMaster().getBankId());

							if(getPurchaseBank()!=null){
								String splitIndicatorCheck = 	fXDetailInformationService.getSplitIndicatorFromBankMaster(getPurchaseBank());

								if(splitIndicatorCheck!=null && splitIndicatorCheck.equalsIgnoreCase(Constants.Yes)){
									setEftAmount(treasuryDealDetailsList.get(0).getEftAmount());
									setCashAmount(treasuryDealDetailsList.get(0).getCashAmount());
									setTtAmount(treasuryDealDetailsList.get(0).getTtAmount());
									setRenderEftTtCashPanel(true);
								}else{
									setEftAmount(null);
									setCashAmount(null);
									setTtAmount(null);
									setRenderEftTtCashPanel(false);
								}
							}



							setPurchaseCurrency(treasuryDealDetailList.get(0).getTreasuryDealDetailCurrencyMaster().getCurrencyId());
							setPurchaseCurrencyName(treasuryDealDetailList.get(0).getTreasuryDealDetailCurrencyMaster().getCurrencyName());
							//populateAccountNumber();
							setSinglacc(treasuryDealDetailList.get(0).getTreasuryDealDetailBankAccountDetails().getBankAcctNo());
							setPurchaseAccountNumber(treasuryDealDetailList.get(0).getTreasuryDealDetailBankAccountDetails().getBankAcctDetId());
							setPurchaseDealDetId(treasuryDealDetailList.get(0).getTreasuryDealDetailId());
							commomnTrDetalId=treasuryDealDetailList.get(0).getTreasuryDealDetailId();
							setFaAccountNumberForPurchase(treasuryDealDetailList.get(0).getFaAccountNo());
							/*setBooSelectbankPAcc(true);
    			      setMultiselectPAcc(false);*/
							setFcAmount(treasuryDealDetailList.get(0).getFcAmount());
							setFcSaleAmount(treasuryDealDetailList.get(0).getSaleAmount());
							setFcLocalAmount(treasuryDealDetailList.get(0).getLocalAmount());
							setCreatedByDetailPurchase(treasuryDealDetailList.get(0).getCreatedBy());
							setCreatedDateDetailPurchase(treasuryDealDetailList.get(0).getCreatedDate());
							trStandInsIdPurchase= treasuryDealDetailList.get(0).getStandardInstruction();
							setStandardInstructionPkForPurchageDetail(trStandInsIdPurchase);
							//setSumOfFCCommonPool(treasuryDealDetailList.get(0).getFcAmount().intValue());
							/*setBooRenderBankId(false);
    			      setBooRenderBankIdForUpdate(true);*/
						}else
						{
							if(treasuryDealDetailListForsplPool.size()!=0)
							{
								TreasuryDealDetail treasuryDealDetail=treasuryDealDetailListForsplPool.get(0);

								setPurchaseBank(treasuryDealDetail.getTreasuryDealBankMaster().getBankId());
								setPurchaseCurrency(treasuryDealDetail.getTreasuryDealDetailCurrencyMaster().getCurrencyId());
								setPurchaseCurrencyName(treasuryDealDetail.getTreasuryDealDetailCurrencyMaster().getCurrencyName());
								//populateAccountNumber();
								setSinglacc(treasuryDealDetail.getTreasuryDealDetailBankAccountDetails().getBankAcctNo());
								setPurchaseAccountNumber(treasuryDealDetail.getTreasuryDealDetailBankAccountDetails().getBankAcctDetId());
								//setPurchaseDealDetId(treasuryDealDetail.getTreasuryDealDetailId());
								commomnTrDetalId=treasuryDealDetail.getTreasuryDealDetailId();
								setFaAccountNumberForPurchase(treasuryDealDetail.getFaAccountNo());
								/*setBooSelectbankPAcc(true);
        			      setMultiselectPAcc(false);*/
								//setFcAmount(treasuryDealDetail.getFcAmount());
								//setFcLocalAmount(treasuryDealDetail.getLocalAmount());
								trStandInsIdPurchase= treasuryDealDetail.getStandardInstruction();
								setCreatedByDetailPurchase(treasuryDealDetail.getCreatedBy());
								setCreatedDateDetailPurchase(treasuryDealDetail.getCreatedDate());
								setFcAmount(BigDecimal.ZERO);
								setFcSaleAmount(BigDecimal.ZERO);
								setSplCustMsg(true);
								setFcAmountRequired(false);
							}

						}

						if(getPurchaseBank()!=null && getPurchaseCurrency()!=null && getPurchaseAccountNumber()!=null ){
							List<StandardInstruction> lstStandardInstruction=fXDetailInformationService.getInstrnNumberDesc(getPurchaseBank(),getPurchaseCurrency(),getPurchaseAccountNumber(),Constants.PD);

							if(lstStandardInstruction.size()>0){
								StandardInstruction standardInstruction=lstStandardInstruction.get(0);
								//			setPurchaseInstrunction(standardInstruction.getStandardInsructionNumber());
								//			setPurchaseInstrunctionForUpdate(standardInstruction.getStandardInsructionNumber());
								setPurchaseInstructionDesc(standardInstruction.getInstructionDescription());
								setBooRenderPurchaseInstrunction(false);
								setBooRenderPurchaseInstrunctionForUpdate(true);
								setPurchaseCheckboxDisable(false);
								setPurchaseCheckbox(false);
								/*if(lstStandardInstruction.size()>0){
        				setPurchaseCheckboxDisable(true);
        				setPurchaseCheckbox(true);
        			}else{
        				setPurchaseCheckboxDisable(false);
        				setPurchaseCheckbox(false);
        			}*/

							}
						}


						setDate3(treasuryDealDetailsList.get(0).getValueDate());
						setValueDateStr(new SimpleDateFormat("dd/MM/yyyy").format(treasuryDealDetailsList.get(0).getValueDate()));
						setPurchaseMultipleDivision(treasuryDealDetailsList.get(0).getMultiplicationDivision());
						setPurchaseExchangeRate(treasuryDealDetailsList.get(0).getPurchaseExchangeRate());
						setFcLocalAmount(treasuryDealDetailsList.get(0).getSaleAmount());
						//Fetching All Instructions For Purchase 

						lstTresSIPur.clear();
						cstandardInstrnDetailsPurchase.clear();

						List<TreasuryStandardInstruction> treasuryStndInstrnPurchase = fXDetailInformationService.getTreasuryStandardInstructionsForPurchase(commomnTrDetalId, Constants.PD ,new Boolean(false));

						if(treasuryStndInstrnPurchase.size()!=0){
							setLstTresSIPur(treasuryStndInstrnPurchase);
						}

						//Fetching Special Customer Details

						lstofSplPoolDealReq.clear();
						purchaseSpecialPoolDataTable.clear();
						purchaseReqSplPoolDataTableList.clear();





						if(lstofSPlPool.size() != 0){

							for (PurchaseReqSplPoolDataTable puchaseReqSplDeal : lstofSPlPool) {

								PurchaseReqSplPoolDataTable purchaseSplPool = new PurchaseReqSplPoolDataTable();
								purchaseSplPool.setCustomerSpecialDealReqId(puchaseReqSplDeal.getCustomerSpecialDealReqId());
								purchaseSplPool.setCustomerFirstName(puchaseReqSplDeal.getCustomerFirstName());
								purchaseSplPool.setDocumentNumber(puchaseReqSplDeal.getDocumentNumber());
								purchaseSplPool.setForeignCurrencyAmount(puchaseReqSplDeal.getForeignCurrencyAmount());
								purchaseSplPool.setLocalAmount(puchaseReqSplDeal.getLocalAmount());
								purchaseSplPool.setSplReqFinanceYear(puchaseReqSplDeal.getSplReqFinanceYear());
								purchaseSplPool.setSelectRecord(false);

								lstofSplPoolDealReq.add(purchaseSplPool);
							}

						}

						if(treasuryDealDetailListForsplPool.size() != 0){

							BigDecimal sumOfSpFcAmt=BigDecimal.ZERO;
							BigDecimal sumOfSpSaleAmt=BigDecimal.ZERO;
							setSumSplSaleAmount(BigDecimal.ZERO);
							setSumOfFcAmount(BigDecimal.ZERO);
							setDataFcAmount(BigDecimal.ZERO);
							for(TreasuryDealDetail treasuryDealDetl:treasuryDealDetailListForsplPool){

								if(treasuryDealDetl.getLineType().equalsIgnoreCase(Constants.PD) && treasuryDealDetl.getLineNumber().intValue()!=0){

									PurchaseReqSplPoolDataTable purchaseSplPool = new PurchaseReqSplPoolDataTable();
									purchaseSplPool.setCustomerSpecialDealReqId(treasuryDealDetl.getCustomerSpecialDealRequest().getCustomerSpecialDealReqId());
									List<CustomerIdProof> lstofCustRef = iCustomerRegistrationBranchService.getCivilID(treasuryDealDetl.getCustomerReference());
									if (lstofCustRef.size() != 0) {
										purchaseSplPool.setCustomerFirstName(lstofCustRef.get(0).getFsCustomer().getFirstName());
									}
									purchaseSplPool.setCustomerReference(treasuryDealDetl.getCustomerReference());

									purchaseSplPool.setDocumentNumber(treasuryDealDetl.getSpecialRequestDocNumber());
									purchaseSplPool.setForeignCurrencyAmount(treasuryDealDetl.getFcAmount());
									purchaseSplPool.setLocalAmount(treasuryDealDetl.getSaleAmount());
									purchaseSplPool.setSplReqFinanceYear(treasuryDealDetl.getSpecialRequestFinanceYear());
									purchaseSplPool.setSplPoolPurchaseDetId(treasuryDealDetl.getTreasuryDealDetailId());
									purchaseSplPool.setSelectRecord(true);

									sumOfSpFcAmt=sumOfSpFcAmt.add(treasuryDealDetl.getFcAmount());
									sumOfSpSaleAmt=sumOfSpSaleAmt.add(treasuryDealDetl.getSaleAmount());
									lstofSplPoolDealReq.add(purchaseSplPool);
								}

							}
							setDataFcAmount(sumOfSpFcAmt);
							setSumSplSaleAmount(sumOfSpSaleAmt);
							setSumOfFcAmount(sumOfSpFcAmt);
							setSplCustMsg(true);
							setFcAmountRequired(false);
						}

						purchaseReqSplPoolDataTableList.clear();
						purchaseReqSplPoolDataTableList.addAll(lstofSplPoolDealReq);


						List<TreasuryDealDetail> treasuryDealDetailListForSale = fXDetailInformationService.getDetailsSale(getTeasuryDealHeaderId(), Constants.SD);

						if(treasuryDealDetailListForSale.size()>0){

							setSaleBank(treasuryDealDetailListForSale.get(0).getTreasuryDealBankMaster().getBankId());
							setSaleCurrency(treasuryDealDetailListForSale.get(0).getTreasuryDealDetailCurrencyMaster().getCurrencyId());
							setSaleCurrencyName(treasuryDealDetailListForSale.get(0).getTreasuryDealDetailCurrencyMaster().getCurrencyName());
							//populateAccountNumberForSales();
							setSaleAcctNo(treasuryDealDetailListForSale.get(0).getTreasuryDealDetailBankAccountDetails().getBankAcctNo());
							setSaleAccountNumber(treasuryDealDetailListForSale.get(0).getTreasuryDealDetailBankAccountDetails().getBankAcctDetId().toPlainString());
							setSaleBalance(treasuryDealDetailListForSale.get(0).getAccountBalance().getForeignBalance());
							setSaleAccountBalanceId(treasuryDealDetailListForSale.get(0).getAccountBalance().getAccountId());//getSaleAccountBalanceId
							setSaleValueDate(treasuryDealDetailListForSale.get(0).getValueDate());
							setSaleValueDateStr(new SimpleDateFormat("dd/MM/yyyy").format(treasuryDealDetailListForSale.get(0).getValueDate()));
							setSaleAmount(treasuryDealDetailListForSale.get(0).getFcAmount());
							setSaleAvgRate(treasuryDealDetailListForSale.get(0).getAvgRate());
							setSaleLocalAmount(treasuryDealDetailListForSale.get(0).getLocalAmount());
							setSaleDealDetId(treasuryDealDetailListForSale.get(0).getTreasuryDealDetailId());

							setCreatedByDetailSale(treasuryDealDetailListForSale.get(0).getCreatedBy());
							setCreatedDateDetailSale(treasuryDealDetailListForSale.get(0).getCreatedDate());
							/*setMulAccNosal(false);
    				setOneAccNoSal(true);*/
							/*setBooRenderCurrencySingle(true);
    				setBooRenderCurrencyId(false);*/
							setFaAccountNumberForSale(treasuryDealDetailListForSale.get(0).getFaAccountNo());
							trStandInsIdSale=treasuryDealDetailListForSale.get(0).getStandardInstruction();
							setStandardInstructionPkForSaleDetail(trStandInsIdSale);
						}

						//Fetch Purchase Details only Details Set    			
						setLocalExchangeRate(treasuryDealDetailsList.get(0).getPurchaseLocalRate());
						setTotalFcPurchaseAmount(treasuryDealDetailsList.get(0).getTotalPurchaseFCAmt());
						setTotallocalAmount(treasuryDealDetailsList.get(0).getTotalPurchaseLocalAmt());

						if(getSaleLocalAmount().compareTo(getTotallocalAmount()) != 0){
							setBooRenderDiffAmount(true);
							setDifferenceAmount(getSaleLocalAmount().subtract(getTotallocalAmount()));
						}else{
							setBooRenderDiffAmount(false);
							setDifferenceAmount(null);
						}

						/*//fetch Sale Instruction
    			List<TreasuryStandardInstruction> treasuryStandardInsListSale = fXDetailInformationService.getTreasuryStandardInstructionsForSale(getSaleDealDetId(),Constants.SD,new Boolean(true));

    			if(treasuryStandardInsListSale.size()>0){
    				setSalePurchaseInstrunction(treasuryStandardInsListSale.get(0).getStandardInstructionNumber());
    				setSalePurchaseInstrunctionForUpdate(treasuryStandardInsListSale.get(0).getStandardInstructionNumber());
    				setSalePurchaseInstructionDesc(treasuryStandardInsListSale.get(0).getMessageDescription());
    				setSaleTreasuryInstructionId(treasuryStandardInsListSale.get(0).getTreasuryStandardInstructionId());
    				setCreatedByInstSale(treasuryStandardInsListSale.get(0).getCreatedBy());
    				setCreatedDateInstSale(treasuryStandardInsListSale.get(0).getCreatedDate());
    				setLineNumberforSale(treasuryStandardInsListSale.get(0).getMessageLineNumber());
    				setBooRenderStandInsID(false);
					setBooRenderStandInsIDForUpdate(true);
    				if(treasuryStandardInsListSale.get(0).getCreatedBy()!=null){
    					setSaleCheckboxDisable(true);
    					setSalesCheckbox(true);
    					//saleCommandLinkDisable
    				}else{
    					setSaleCheckboxDisable(false);
    					setSalesCheckbox(false);
    				}
    			}*/

						if(getSaleBank()!=null  && getSaleCurrency()!=null && getSaleAccountNumber()!=null){
							List<StandardInstruction> lstStandardInstructionSale=fXDetailInformationService.getInstrnNumberDesc(getSaleBank(),getSaleCurrency(), new BigDecimal(getSaleAccountNumber()),Constants.SD);

							if(lstStandardInstructionSale.size()>0){
								StandardInstruction standardInstruction=lstStandardInstructionSale.get(0);

								//		setSalePurchaseInstrunction(standardInstruction.getStandardInsructionNumber());
								//		setSalePurchaseInstrunctionForUpdate(standardInstruction.getStandardInsructionNumber());
								setSalePurchaseInstructionDesc(standardInstruction.getInstructionDescription());
								//setSaleTreasuryInstructionId(standardInstruction.getTreasuryStandardInstructionId());
								//setCreatedByInstSale(standardInstruction.getCreatedBy());
								//setCreatedDateInstSale(standardInstruction.getCreatedDate());
								//setLineNumberforSale(treasuryStandardInsListSale.get(0).getMessageLineNumber());
								setBooRenderStandInsID(false);
								setBooRenderStandInsIDForUpdate(true);
								setSaleCheckboxDisable(false);
								setSalesCheckbox(false);
								/*if(lstStandardInstructionSale.size()>0){
    					setSaleCheckboxDisable(true);
    					setSalesCheckbox(true);
    					//saleCommandLinkDisable
    				}else{
    					setSaleCheckboxDisable(false);
    					setSalesCheckbox(false);
    				}*/

							}
						}

						lstTresSISale.clear();
						cstandardInstrnDetailsforSales.clear();

						List<TreasuryStandardInstruction> treasuryStndInstrnSale = fXDetailInformationService.getTreasuryStandardInstructionsForSale(getSaleDealDetId(),Constants.SD,new Boolean(false));

						if(treasuryStndInstrnSale.size()!=0){
							setLstTresSISale(treasuryStndInstrnSale);
						}


					}
				}else{
					RequestContext.getCurrentInstance().execute("DocNoNotFound.show();");
					setEditaleRefId(null);
				}
			}else{
				RequestContext.getCurrentInstance().execute("notabletoApprove.show();");
				setEditaleRefId(null);
			}
		}catch(NullPointerException  e){ 
			setErrorMsg("method :fetchingAllRecordsFromDB :");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}

	}



	/*//CR get Bank list from Bank Master DB 
	public List<BankMaster> getBankListFromDB() {
		return generalService.getAllBankListFromBankMaster();
	}*/

	//CR get currency list from database
	public List<CurrencyMaster> getCurrencyListFromDB() {
		return generalService.getCurrencyList();
	}

	/*//CR get Bank list from Bank Master DB 
		public List<BankMaster> getBankListFoSale() {
			return generalService.getAllBankListFromBankMaster();
		}*/

	/*//CR get Bank list from Bank Applicability local Banks  
			public List<BankApplicability> getBankListFoSale() {
				return generalService.getLocalBankList(sessionManage.getCountryId());
			}*/

	//CR get Bank list from Bank Applicability local Banks , Funding Banks and Corresponding Bank 
	public void bankListFoSale() {
		try{
			lstAllBankApplicabity.clear();

			List<BigDecimal> duplicateCheck = new ArrayList<BigDecimal>();

			List<BankApplicability> lstBankApplicabity = fXDetailInformationService.getCorrespondingLocalFundingBanks(sessionManage.getCountryId());

			for (BankApplicability bankApplicability : lstBankApplicabity) {
				if(!duplicateCheck.contains(bankApplicability.getBankMaster().getBankId())){
					duplicateCheck.add(bankApplicability.getBankMaster().getBankId());
					lstAllBankApplicabity.add(bankApplicability);
				}
			}
		}catch(NullPointerException  e){ 
			setErrorMsg("method:bankListFoSale :");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}

	}

	//CR get currency list from database
	public List<CurrencyMaster> getCurrencyListForSale() {
		return generalService.getCurrencyList();
	}

	public void populateAccountNumber() {
		try{
			List<BankAccountDetails> ptabledata = generalService.getAccountNumber(getPurchaseBank(), getPurchaseCurrency());

			if(ptabledata.size()==0)
			{
				setBooSelectbankPAcc(true);
				setMultiselectPAcc(false);
				RequestContext.getCurrentInstance().execute("dataNotFund.show();");
			}else if(ptabledata.size()==1){
				for(BankAccountDetails bankAcount :ptabledata)
				{
					setPurchaseBankAccountId(bankAcount.getBankAcctDetId());
					setSinglacc(bankAcount.getBankAcctNo());
					setFaAccountNumberForPurchase(bankAcount.getGlno());
				}
				setPurchaseAccountNumber(getPurchaseBankAccountId());
				setBooSelectbankPAcc(true);
				setMultiselectPAcc(false);
				// populateAccountBalance();
			}else{
				setPurchaseAccountNumber(null);
				setBooSelectbankPAcc(false);
				setMultiselectPAcc(true);
				setAccountNumberList(ptabledata);
			}
		}catch(NullPointerException  e){ 
			setErrorMsg("populateAccountNumber :");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}

	public void fetchFaAccNum(){
		if(getAccountNumberList()!=null){
			for(BankAccountDetails lstaccdt : getAccountNumberList()){
				if(lstaccdt.getBankAcctDetId().compareTo(getPurchaseAccountNumber())==0){
					setFaAccountNumberForPurchase(lstaccdt.getGlno());
				}
			}
		}
	}

	public void populateAccountBalance() {
		try{
			fetchFaAccNumForSale();
			List<AccountBalance> accBalan = generalService.getBankBalance(getSaleAccountNumber());
			System.out.println("=============ACCOUNT NO IDDDDDDDDDDDDDDDD"+getSaleAccountNumber());
			System.out.println("============================Size =================="+accBalan.size());

			if(accBalan.size()!=0){
				for(AccountBalance accBalance:accBalan){
					if(accBalance.getForeignBalance()!=null){
						setSaleBalance(round(accBalance.getForeignBalance(),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(getSaleCurrency())));
						setSaleAccountBalanceId(accBalance.getAccountId());
						checkingSalesAmountandBankBal();
					}else{
						RequestContext.getCurrentInstance().execute("nobalance.show();");
					}
				}
				getAvgRateFromAccBal();
			}else{
				RequestContext.getCurrentInstance().execute("nobalance.show();");
			}
			/*		for(AccountBalance accBalance:accountBalalnceList){
		System.out.println("====Sale ACccount No======"+getSaleAccountNumber());
		System.out.println("====Balance===="+accBal);

		setSaleAccountBalanceId(accBalance.getAccountId());
		}*/
		}catch(NullPointerException  e){ 
			setErrorMsg("populateAccountBalance :");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}


	public void populateAccountNumberForSales() {
		try{
			setSaleAvgRate(null);
			setTotallocalAmount(null);
			setLocalExchangeRate(null);
			setSaleLocalAmount(null);
			List<BankAccountDetails> stabledata = generalService.getAccountNumber(getSaleBank(), getSaleCurrency());
			if(stabledata.size()==0)
			{
				setBooSelectbankPAcc(true);
				setMultiselectPAcc(false);
				RequestContext.getCurrentInstance().execute("dataNotFund.show();");
			}else if(stabledata.size()==1)
			{

				for(BankAccountDetails bankAcount :stabledata)
				{
					setSaleAccountBalanceId(bankAcount.getBankAcctDetId());
					setSaleAcctNo(bankAcount.getBankAcctNo());
					setFaAccountNumberForSale(bankAcount.getGlno());
				}
				setSaleAccountNumber(getSaleAccountBalanceId().toString());
				setOneAccNoSal(true);
				setMulAccNosal(false);
				populateAccountBalance();
			}else{
				setOneAccNoSal(false);
				setMulAccNosal(true);
				setAccountNumberListforSale(stabledata);
			}
		}catch(NullPointerException  e){ 
			setErrorMsg("populateAccountNumberForSales :");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}

	public void fetchFaAccNumForSale(){
		if(getAccountNumberListforSale()!=null){
			for(BankAccountDetails lstaccdtsale : getAccountNumberListforSale()){
				if(lstaccdtsale.getBankAcctDetId().compareTo(new BigDecimal(getSaleAccountNumber()))==0){
					setFaAccountNumberForSale(lstaccdtsale.getGlno());
				}
			}
		}
	}

	public void getAvgRateFromAccBal(){
		try{
			List<AccountBalance> stabledata = fXDetailInformationService.getSaleAvgRate(getSaleBank(), getSaleCurrency(),getSaleAccountNumber());
			if(stabledata.size()!=0){
				for (AccountBalance accountBalance : stabledata) {
					setSaleAvgRate(accountBalance.getAverageRate());
				}
				calculateSalesLocalAmount();
			}else{
				RequestContext.getCurrentInstance().execute("noavgrate.show();");
			}
			/*if(getSaleCurrency().compareTo(new BigDecimal(sessionManage.getCurrencyId()))==0){
			setSaleAvgRate(round(new BigDecimal(1),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId()))));
			calculateSalesLocalAmount();
			//round((customerSpecialDREQ.getForeignCurrencyAmount().multiply(getPurchaseExchangeRate())),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId()))));
		}else{
			if(stabledata.size()!=0){
				for (AccountBalance accountBalance : stabledata) {
					setSaleAvgRate(accountBalance.getAverageRate());
					System.out.println("!!!!!!!!!getSaleAvgRate!!!!!!!!!"+getSaleAvgRate());
				}
				calculateSalesLocalAmount();
			}else{
				RequestContext.getCurrentInstance().execute("noavgrate.show();");
			}
		}*/
		}catch(NullPointerException  e){ 
			setErrorMsg("getAvgRateFromAccBal :");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}

	}

	//CR Clearing Instruction Number and Description acc to bank and currency
	public void purchaseStandardInstruction(){
		try{	
			setPurchaseInstrunction(null);
			setPurchaseInstructionDesc(null);
			setPurchaseAccountNumber(null);
			setPurchaseBankAccountId(null);
			setPurchaseMultipleDivision("");
			setSinglacc(null);
			setPurchaseInstrunctionForUpdate(null);
			purchaseSpecialPoolDataTable.clear();
			purchaseReqSplPoolDataTableList.clear();
			//	setBankCountryCurrencyList(fXDetailInformationService.getCurrencyBasedOnBankCountry(getPurchaseBank()));

			if(getPurchaseBank()==null || getPurchaseCurrency()==null){
				setPurchaseInstrunction(null);
				setPurchaseInstructionDesc(null);
			}else{
				if(getSaleCurrency() != null ){
					if(getPurchaseCurrency().compareTo(getSaleCurrency())==0){
						RequestContext.getCurrentInstance().execute("currencyCheck.show();");
						clearPurchaseAfterSameCurrencyMatch();
					}else{
						dataTableForPurchaseSplPool();
						populateAccountNumber();
						resetExchangeRate();
						if(getPurchaseBank()!=null && getPurchaseCurrency()!=null && getPurchaseBankAccountId()!=null){
							List<StandardInstruction> standardInstruction = fXDetailInformationService.getInstrnNumber(getPurchaseBank(),getPurchaseCurrency(),getPurchaseBankAccountId());
							purchaseInstrnsize = standardInstruction.size();
							if(purchaseInstrnsize==0){
								setPurchaseCheckbox(false);
								setPurchaseCheckboxDisable(true);
								setBooRenderPurchaseInstrunctionForUpdate(true);
								setBooRenderPurchaseInstrunction(false);
								RequestContext.getCurrentInstance().execute("nointrndetails.show();");
							}else if(purchaseInstrnsize==1){
								for (StandardInstruction element : standardInstruction){	
									//		setPurchaseInstrunction(element.getStandardInsructionNumber());
									//		setPurchaseInstrunctionForUpdate(element.getStandardInsructionNumber());
									setPurchaseInstructionDesc(element.getInstructionDescription());
								}
								setBooRenderPurchaseInstrunctionForUpdate(true);
								setBooRenderPurchaseInstrunction(false);
								setPurchaseCheckbox(false);
								setPurchaseCheckboxDisable(false);
							}else{
								setPstandardInstrnforPurchase(standardInstruction);
								setBooRenderPurchaseInstrunctionForUpdate(false);
								setBooRenderPurchaseInstrunction(true);
								setPurchaseCheckbox(false);
								setPurchaseCheckboxDisable(true);
							}

						}
					}
				}else{
					dataTableForPurchaseSplPool();
					populateAccountNumber();
					resetExchangeRate();
					if(getPurchaseBank()!=null && getPurchaseCurrency()!=null && getPurchaseBankAccountId()!=null){
						List<StandardInstruction> standardInstruction = fXDetailInformationService.getInstrnNumber(getPurchaseBank(),getPurchaseCurrency(),getPurchaseBankAccountId());
						purchaseInstrnsize = standardInstruction.size();
						if(purchaseInstrnsize==0){
							setPurchaseCheckbox(false);
							setPurchaseCheckboxDisable(true);
							setBooRenderPurchaseInstrunctionForUpdate(true);
							setBooRenderPurchaseInstrunction(false);
							RequestContext.getCurrentInstance().execute("nointrndetails.show();");
						}else if(purchaseInstrnsize==1){
							for (StandardInstruction element : standardInstruction){	
								//	setPurchaseInstrunction(element.getStandardInsructionNumber());
								//	setPurchaseInstrunctionForUpdate(element.getStandardInsructionNumber());
								setPurchaseInstructionDesc(element.getInstructionDescription());
							}
							setBooRenderPurchaseInstrunctionForUpdate(true);
							setBooRenderPurchaseInstrunction(false);
							setPurchaseCheckbox(false);
							setPurchaseCheckboxDisable(false);
						}else{
							setPstandardInstrnforPurchase(standardInstruction);
							setBooRenderPurchaseInstrunctionForUpdate(false);
							setBooRenderPurchaseInstrunction(true);
							setPurchaseCheckbox(false);
							setPurchaseCheckboxDisable(true);
						}

					}
				}
			}
		}catch(NullPointerException  e){ 
			setErrorMsg("purchaseStandardInstruction :");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}


	//CR getting Instruction Description from DB
	public void standardInstrunctionDescription()
	{
		try{
			if(getPurchaseBank()==null || getPurchaseCurrency()==null){
				RequestContext.getCurrentInstance().execute("purchasecdialog.show();");
				setPurchaseInstrunction(null);
				setPurchaseInstructionDesc(null);
			}else{

				List<StandardInstruction> purchaseStandrdInstrnDesc = fXDetailInformationService.getInstrnNumberDesc(getPurchaseBank(),getPurchaseCurrency(),getPurchaseAccountNumber(),Constants.PD);

				if(purchaseStandrdInstrnDesc.size() != 0){

					for (StandardInstruction standardInstruction : purchaseStandrdInstrnDesc) {
						if(getPurchaseInstrunction().equals(standardInstruction.getIntructionType())){
							setPurchaseCheckbox(false);
							setPurchaseInstructionDesc(standardInstruction.getInstructionDescription());
							setPurchaseCheckboxDisable(false);
						}
					} 	

				}else{
					setPurchaseInstructionDesc(null);
				}
			}
		}catch(NullPointerException  e){ 
			setErrorMsg("standardInstrunctionDescription :");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}

	//CR getting Instruction and Line Number from DB
	public void getDataTableForPurchaseDetails() {
		try{

			cstandardInstrnDetailsPurchase.clear();

			if(getEditaleRefId() != null){
				if(lstTresSIPur.size() != 0){
					for (TreasuryStandardInstruction lstofSIPurchase : lstTresSIPur) {

						StandardInstructionDetails lstStndInstrn = new StandardInstructionDetails();

						lstStndInstrn.setLineNumber(lstofSIPurchase.getMessageLineNumber());
						lstStndInstrn.setLineDescription(lstofSIPurchase.getMessageDescription());

						cstandardInstrnDetailsPurchase.add(lstStndInstrn);
					}
				}

				if(isPurchaseCheckbox()){
					setPurchaseCheckboxDisable(true);
					RequestContext.getCurrentInstance().execute("puchasedetails.show();");
				}else{
					System.out.println("********else*********"+purchaseCheckbox);
				}

			}else{
				if(getPurchaseBank()!=null && getPurchaseCurrency()!=null && getPurchaseInstrunction()!=null){

					List<StandardInstructionDetails> cinstrndetailsfromDB = fXDetailInformationService.getInstructionsFromDetails(getPurchaseBank(),getPurchaseCurrency(),isActiveforPurchase,getStandardInstructionPkForPurchageDetail(),getPurchaseAccountNumber(),Constants.PD);

					if(cinstrndetailsfromDB.size() != 0){
						setCstandardInstrnDetailsPurchase(cinstrndetailsfromDB);
						setPurchaseCheckboxDisable(false);
					}

					if(purchaseCheckbox){
						setPurchaseCheckboxDisable(true);
						RequestContext.getCurrentInstance().execute("puchasedetails.show();");
					}else{
						System.out.println("********else*********"+purchaseCheckbox);
					}
				}else{
					setPurchaseCheckboxDisable(true);
				}
			}
		}catch(NullPointerException  e){ 
			setErrorMsg("method:getDataTableForPurchaseDetails :");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}

	//CR Clearing Instruction Number and Description acc to bank and currency - Sales Details
	public void salesStandardInstruction(){
		try{
			setSalePurchaseInstrunction(null);
			setSalePurchaseInstructionDesc(null);
			setSaleAccountBalanceId(null);
			setSaleAccountNoId(null);
			setSaleAccountNumber(null);
			setSaleAcctNo(null);
			setSaleBalance(null);
			setSalePurchaseInstrunctionForUpdate(null);
			setSaleAvgRate(null);
			setTotallocalAmount(null);
			setLocalExchangeRate(null);
			setSaleLocalAmount(null);

			if(getSaleBank()==null || getSaleCurrency()==null){
				setSalePurchaseInstrunction(null);
				setSalePurchaseInstructionDesc(null);
			}else{

				if(getPurchaseCurrency().compareTo(getSaleCurrency())==0){
					RequestContext.getCurrentInstance().execute("currencyCheck.show();");
					clearSalesAfterSameCurrencyMatch();
				}else{
					populateAccountNumberForSales();

					if(getSaleBank()!=null && getSaleCurrency()!=null && getSaleAccountBalanceId()!=null){
						List<StandardInstruction> standardInstruction=fXDetailInformationService.getInstrnNumber(getSaleBank(),getSaleCurrency(),getSaleAccountBalanceId());
						saleInstrnsize = standardInstruction.size();
						if(saleInstrnsize==0){
							setSalesCheckbox(false);
							setSaleCheckboxDisable(true);
							setBooRenderStandInsIDForUpdate(true);
							setBooRenderStandInsID(false);
							RequestContext.getCurrentInstance().execute("nointrndetails.show();");
						}else if(saleInstrnsize==1){

							for (StandardInstruction element : standardInstruction) {	
								//				setSalePurchaseInstrunction(element.getStandardInsructionNumber());
								//				setSalePurchaseInstrunctionForUpdate(element.getStandardInsructionNumber());
								setSalePurchaseInstructionDesc(element.getInstructionDescription());
							}
							setBooRenderStandInsIDForUpdate(true);
							setBooRenderStandInsID(false);
							setSalesCheckbox(false);
							setSaleCheckboxDisable(false);

						}else{
							setPstandardInstrnforSales(standardInstruction);
							setBooRenderStandInsIDForUpdate(false);
							setBooRenderStandInsID(true);
							setSalesCheckbox(false);
							setSaleCheckboxDisable(true);
						}

					}
				}

			}
		}catch(NullPointerException  e){ 
			setErrorMsg("method:salesStandardInstruction :");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}


	//CR getting Instruction Description from DB - Sales Details
	public void salesStandardInstrunctionDescription()
	{
		try{
			if(getSaleBank()==null || getSaleCurrency()==null){
				RequestContext.getCurrentInstance().execute("salescdialog.show();");
				setSalePurchaseInstrunction(null);
				setSalePurchaseInstructionDesc(null);
			}else{

				List<StandardInstruction> salesStandrdInstrnDesc = fXDetailInformationService.getInstrnNumberDesc(getSaleBank(),getSaleCurrency(), new BigDecimal(getSaleAccountNumber()),Constants.SD);

				if(salesStandrdInstrnDesc.size() != 0){

					for (StandardInstruction standardInstruction : salesStandrdInstrnDesc) {
						if(getSalePurchaseInstrunction().equals(standardInstruction.getIntructionType())){
							setSalesCheckbox(false);
							setSalePurchaseInstructionDesc(standardInstruction.getInstructionDescription());
							setSaleCheckboxDisable(false);
						}
					} 	

				}else{
					setPurchaseInstructionDesc(null);
				}
			}
		}catch(NullPointerException  e){ 
			setErrorMsg("method:salesStandardInstrunctionDescription :");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}

	//CR getting Instruction and Line Number from DB - Sales Details
	public void getDataTableForSalesDetails() {
		try{
			cstandardInstrnDetailsforSales.clear();

			if(getEditaleRefId() != null){
				if(lstTresSISale.size() != 0){
					for (TreasuryStandardInstruction lstofSISale : lstTresSISale) {

						StandardInstructionDetails lstStndInstrn = new StandardInstructionDetails();

						lstStndInstrn.setLineNumber(lstofSISale.getMessageLineNumber());
						lstStndInstrn.setLineDescription(lstofSISale.getMessageDescription());

						cstandardInstrnDetailsforSales.add(lstStndInstrn);
					}
				}

				if(isSalesCheckbox()){
					setSaleCheckboxDisable(true);
					RequestContext.getCurrentInstance().execute("salesdetails.show();");
				}else{
					System.out.println("********else*********"+salesCheckbox);
				}

			}else{

				if(getSaleBank()!=null && getSaleCurrency()!=null && getSalePurchaseInstrunction()!=null){
					List<StandardInstructionDetails> cinstrndetailsfromDB = fXDetailInformationService.getInstructionsFromDetails(getSaleBank(),getSaleCurrency(),isActiveforSales,getStandardInstructionPkForSaleDetail(),new BigDecimal(getSaleAccountNumber()),Constants.SD);

					if(cinstrndetailsfromDB.size() != 0){
						setCstandardInstrnDetailsforSales(cinstrndetailsfromDB);
						setSaleCheckboxDisable(false);
					}

					if(salesCheckbox){
						setSaleCheckboxDisable(true);
						RequestContext.getCurrentInstance().execute("salesdetails.show();");
					}else{
						System.out.println("********else*********"+salesCheckbox);
					}
				}else{
					setSaleCheckboxDisable(true);
				}
			}

		}catch(NullPointerException  e){ 
			setErrorMsg("method:getDataTableForSalesDetails:");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}

	}

	//CR getting Purchase Requirements for Special pool from EX_CUSTOMER_SPECIAL_DEAL_REQ Table
	public List<PurchaseReqSplPoolDataTable> dataTableForPurchaseSplPool() {

		//CR To Remove Duplicates in DataTable if Condition Used
		if(purchaseSpecialPoolDataTable.size()==0){

			if(getPurchaseBank()!=null){

				//To Get Spl Pool Data Routing Setup Bank Must be IsActive "Y" else No records
				bankActiveinRoutingSetUp = fXDetailInformationService.checkBankisActiveinRoutingSetupMaster(fXDetailInformationService.getBankCountryBasedOnBank(getPurchaseBank()).get(0).getFsCountryMaster().getCountryId(),getPurchaseBank(),getPurchaseCurrency());

				if(bankActiveinRoutingSetUp.size()!=0){
					//	purchaseSpecialPoolDataTable = fXDetailInformationService.getDataTableFromCustomerSpecialDealReq(fundingOptionSpecial,getPurchaseBank(),getPurchaseCurrency());

					try {
						purchaseSpecialPoolDataTable = fXDetailInformationService.getDataTableFromCustomerSpecialDealReqAccCurrency(fundingOptionSpecial,getPurchaseCurrency());

						if(purchaseSpecialPoolDataTable.size() != 0){
							setSplCustMsg(true);
							setFcAmountRequired(false);

							for (CustomerSpecialDealRequest customerSpecialDREQ : purchaseSpecialPoolDataTable) {

								// separate class for the set into PurchaseReqSplPoolDataTable in FX Deal Bank 
								PurchaseReqSplPoolDataTable purchaseReqSplPoolDataTable = new PurchaseReqSplPoolDataTable();

								purchaseReqSplPoolDataTable.setCustomerSpecialDealReqId(customerSpecialDREQ.getCustomerSpecialDealReqId());
								purchaseReqSplPoolDataTable.setCustomerId(customerSpecialDREQ.getCustomerSpeacialDealReqCustomer().getCustomerId());
								purchaseReqSplPoolDataTable.setSplReqDocCode(customerSpecialDREQ.getCustomerSpeacialDealReqDocument().getDocumentID());
								purchaseReqSplPoolDataTable.setSplReqCompanyId(customerSpecialDREQ.getCustomerSpeacialDealReqCompanyMaster().getCompanyId());			
								//purchaseReqSplPoolDataTable.setSplReqFinanceYear(customerSpecialDREQ.getDocumentFinancialYear().getFinancialYearID());
								purchaseReqSplPoolDataTable.setSplReqFinanceYear(customerSpecialDREQ.getDocumentFinancialYear().getFinancialYear());
								purchaseReqSplPoolDataTable.setSplReqDocNo(customerSpecialDREQ.getDocumentNumber());
								purchaseReqSplPoolDataTable.setCustomerFirstName(customerSpecialDREQ.getCustomerSpeacialDealReqCustomer().getFirstName());
								purchaseReqSplPoolDataTable.setDocumentNumber(customerSpecialDREQ.getDocumentNumber());
								purchaseReqSplPoolDataTable.setProjectionDate(customerSpecialDREQ.getProjectionDate());
								purchaseReqSplPoolDataTable.setForeignCurrencyAmount(customerSpecialDREQ.getForeignCurrencyAmount());
								if(getPurchaseMultipleDivision().equals("1") && getPurchaseExchangeRate()!=null)
								{
									purchaseReqSplPoolDataTable.setLocalAmount(round((customerSpecialDREQ.getForeignCurrencyAmount().multiply(getPurchaseExchangeRate())),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId()))));
								}else if(getPurchaseMultipleDivision().equals("2") && getPurchaseExchangeRate()!=null){
									int decimalvalue = foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId()));
									purchaseReqSplPoolDataTable.setLocalAmount((customerSpecialDREQ.getForeignCurrencyAmount().divide(getPurchaseExchangeRate(),decimalvalue,BigDecimal.ROUND_HALF_UP)));
								}else{
									purchaseReqSplPoolDataTable.setLocalAmount(null);
								}

								purchaseReqSplPoolDataTableList.add(purchaseReqSplPoolDataTable);

							}

						}else{
							setSplCustMsg(false);
							setFcAmountRequired(true);
						}
					} catch(NullPointerException  e){ 
						setErrorMsg("method:getDataTableForSalesDetails:");
						RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
					}
					catch (Exception e) {
						setErrorMsg(e.getMessage());
						RequestContext.getCurrentInstance().execute("csp.show();");
					}

				}else{
					setSplCustMsg(false);
					setFcAmountRequired(true);
				}

			}else{
				setSplCustMsg(false);
				setFcAmountRequired(true);
			}
		}else{
			setSplCustMsg(false);
			setFcAmountRequired(true);
		}

		return purchaseReqSplPoolDataTableList;
	}	


	//CR get Local Amount in Purchase Requirement for Common Pool
	public void localValue(){
		try{
			setSumOfFcAmount(new BigDecimal(0));
			setSumSplSaleAmount(new BigDecimal(0));
			setDataFcAmount(new BigDecimal(0));
			purchaseSpecialPoolDataTable.clear();
			purchaseReqSplPoolDataTableList.clear();
			dataTableForPurchaseSplPool();
			if(getPurchaseMultipleDivision()==null || getPurchaseMultipleDivision().equals("")){
				RequestContext.getCurrentInstance().execute("selectmuldiv.show();");
				setPurchaseExchangeRate(null);
			}else{
				if(getPurchaseMultipleDivision().equals("1") && getFcAmount()!=null){
					setFcLocalAmount(round((getFcAmount().multiply(getPurchaseExchangeRate())),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId()))));
					setSaleAmount(getFcLocalAmount());
					setTotalFcPurchaseAmount(round(getFcAmount(),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId()))));
					setFcSaleAmount(round((getFcAmount().multiply(getPurchaseExchangeRate())),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId()))));
				}else if(getPurchaseMultipleDivision().equals("2") && getFcAmount()!=null){
					int decimalvalue = foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId()));
					setFcLocalAmount((getFcAmount().divide(getPurchaseExchangeRate(),decimalvalue,BigDecimal.ROUND_HALF_UP)));
					setSaleAmount(getFcLocalAmount());
					setTotalFcPurchaseAmount(round(getFcAmount(),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId()))));
					setFcSaleAmount((getFcAmount().divide(getPurchaseExchangeRate(),decimalvalue,BigDecimal.ROUND_HALF_UP)));
				}else{
					//RequestContext.getCurrentInstance().execute("fcamountnotexist.show();");
					System.out.println("!!!!!!Local Value Method!!!!!!!!!");
				}
				calculateSalesLocalAmount();
				checkingSalesAmountandBankBal();
			}	
		}catch(NullPointerException  e){ 
			setErrorMsg("method:localValue:");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}

	}

	//CR to get Total FC Purchase Amount
	/*			public void calFcAmountFromDataTable(PurchaseReqSplPoolDataTable purcReqObj){

				if(!purcReqObj.getSelectRecord()){
					if(purcReqObj.getSplPoolPurchaseDetId()!=null){
						lstofDeleteSplPool.add(purcReqObj.getSplPoolPurchaseDetId());
						lstofClearSplPool.add(purcReqObj.getCustomerSpecialDealReqId());
					}
				}else{
					if(lstofDeleteSplPool.size()!=0){
						for (int i = 0; i < lstofDeleteSplPool.size(); i++) {
							BigDecimal pklocaltreDtl = lstofDeleteSplPool.get(i);
							if(purcReqObj.getSplPoolPurchaseDetId().compareTo(pklocaltreDtl)==0){
								lstofDeleteSplPool.remove(i);
							}
						}
					}
					if(lstofClearSplPool.size()!=0){
						for (int i = 0; i < lstofClearSplPool.size(); i++) {
							BigDecimal pklocalCustSpl = lstofClearSplPool.get(i);
							if(purcReqObj.getCustomerSpecialDealReqId().compareTo(pklocalCustSpl)==0){
								lstofClearSplPool.remove(i);
							}
						}
					}
				}

				List<CustomerSpecialDealRequest> lstLocalSPDetails=getPurchaseSpecialPoolDataTable();

				if(purcReqObj.getSelectRecord()==true && getFcAmount()==null){

					for(CustomerSpecialDealRequest custSPDealRewquest:lstLocalSPDetails)
					{
						if(custSPDealRewquest.getCustomerSpecialDealReqId().compareTo(purcReqObj.getCustomerSpecialDealReqId())==0)
						{
							purchaseSPSelectDataTableForSave.add(custSPDealRewquest);
						}
					}
				}else
				{
					if(purchaseSPSelectDataTableForSave.size()>0)
					{
						for(CustomerSpecialDealRequest custSPDealRewquest:lstLocalSPDetails)
						{
							if(custSPDealRewquest.getCustomerSpecialDealReqId().compareTo(purcReqObj.getCustomerSpecialDealReqId())==0)
							{
								purchaseSPSelectDataTableForSave.remove(custSPDealRewquest);
							}
						}
					}

				}

				if(purcReqObj.getSelectRecord()==true && getFcAmount()==null){
					setSumOfFcAmount(getSumOfFcAmount().add(purcReqObj.getForeignCurrencyAmount()));
				}else if(purcReqObj.getSelectRecord()==false && getFcAmount()==null){
					setSumOfFcAmount(getSumOfFcAmount().subtract(purcReqObj.getForeignCurrencyAmount()));
			    }else{
			    	System.out.println("**********calFcAmountFromDataTable last***********");
			    }

				if(purcReqObj.getSelectRecord()==true && getFcAmount()!=null){
					setSumOfFcAmount(getSumOfFcAmount().add(purcReqObj.getForeignCurrencyAmount()));
					setSumOfFcAmount(getTotalFcPurchaseAmount().add(purcReqObj.getForeignCurrencyAmount()));
					setTotalFcPurchaseAmount(round(getSumOfFcAmount(),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId()))));
					setSumOfFcAmount(getTotalFcPurchaseAmount().subtract(getFcAmount()));
					//added to mulitple when totalfcPurchaseamount get changed . calucaltion totalfcPurchaseamount * getPurchaseExchangeRate = FcLocalAmount
					if(getPurchaseMultipleDivision().equals("1") && getPurchaseExchangeRate()!=null ){
						setFcLocalAmount(round((getTotalFcPurchaseAmount().multiply(getPurchaseExchangeRate())),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId()))));
						setSaleAmount(getFcLocalAmount());
					}else if(getPurchaseMultipleDivision().equals("2") && getPurchaseExchangeRate()!=null){
						int decimalvalue = foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId()));
						setFcLocalAmount((getTotalFcPurchaseAmount().divide(getPurchaseExchangeRate(),decimalvalue,BigDecimal.ROUND_HALF_UP)));
						setSaleAmount(getFcLocalAmount());
					}else{
						//RequestContext.getCurrentInstance().execute("selectmuldiv.show();");
						setPurchaseExchangeRate(null);
					}

				}else if(purcReqObj.getSelectRecord()==false && getFcAmount()!=null){
					setSumOfFcAmount(getSumOfFcAmount().subtract(purcReqObj.getForeignCurrencyAmount()));
					setSumOfFcAmount(getTotalFcPurchaseAmount().subtract(purcReqObj.getForeignCurrencyAmount()));
			    	setTotalFcPurchaseAmount(round(getSumOfFcAmount(),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId()))));
			    	setSumOfFcAmount(getTotalFcPurchaseAmount().subtract(getFcAmount()));
			    	//added to mulitple when totalfcPurchaseamount get changed . calucaltion totalfcPurchaseamount * getPurchaseExchangeRate = FcLocalAmount
					if(getPurchaseMultipleDivision().equals("1") && getPurchaseExchangeRate()!=null ){
						setFcLocalAmount(round((getTotalFcPurchaseAmount().multiply(getPurchaseExchangeRate())),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId()))));
						setSaleAmount(getFcLocalAmount());
					}else if(getPurchaseMultipleDivision().equals("2") && getPurchaseExchangeRate()!=null){
						int decimalvalue = foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId()));
						setFcLocalAmount((getTotalFcPurchaseAmount().divide(getPurchaseExchangeRate(),decimalvalue,BigDecimal.ROUND_HALF_UP)));
						setSaleAmount(getFcLocalAmount());
					}else{
						//RequestContext.getCurrentInstance().execute("selectmuldiv.show();");
						setPurchaseExchangeRate(null);
					}
			    }else{
			    	System.out.println("**********calFcAmountFromDataTable last***********");
			    }
				calculateSalesLocalAmount();
				checkingSalesAmountandBankBal();
			}*/

	//CR to get Total FC Purchase Amount
	public void calFcAmountFromDataTable(PurchaseReqSplPoolDataTable purcReqObj){
		try{
			if(purcReqObj.getLocalAmount() != null){
				if(!purcReqObj.getSelectRecord()){
					if(purcReqObj.getSplPoolPurchaseDetId()!=null){
						lstofDeleteSplPool.add(purcReqObj.getSplPoolPurchaseDetId());
						lstofClearSplPool.add(purcReqObj.getCustomerSpecialDealReqId());
					}
				}else{
					if(lstofDeleteSplPool.size()!=0){
						for (int i = 0; i < lstofDeleteSplPool.size(); i++) {
							BigDecimal pklocaltreDtl = lstofDeleteSplPool.get(i);
							if(purcReqObj.getSplPoolPurchaseDetId().compareTo(pklocaltreDtl)==0){
								lstofDeleteSplPool.remove(i);
							}
						}
					}
					if(lstofClearSplPool.size()!=0){
						for (int i = 0; i < lstofClearSplPool.size(); i++) {
							BigDecimal pklocalCustSpl = lstofClearSplPool.get(i);
							if(purcReqObj.getCustomerSpecialDealReqId().compareTo(pklocalCustSpl)==0){
								lstofClearSplPool.remove(i);
							}
						}
					}
				}

				List<CustomerSpecialDealRequest> lstLocalSPDetails=getPurchaseSpecialPoolDataTable();

				if(purcReqObj.getSelectRecord()==true && getFcAmount()==null){

					for(CustomerSpecialDealRequest custSPDealRewquest:lstLocalSPDetails)
					{
						if(custSPDealRewquest.getCustomerSpecialDealReqId().compareTo(purcReqObj.getCustomerSpecialDealReqId())==0)
						{
							purchaseSPSelectDataTableForSave.add(custSPDealRewquest);
						}
					}
				}else
				{
					if(purchaseSPSelectDataTableForSave.size()>0)
					{
						for(CustomerSpecialDealRequest custSPDealRewquest:lstLocalSPDetails)
						{
							if(custSPDealRewquest.getCustomerSpecialDealReqId().compareTo(purcReqObj.getCustomerSpecialDealReqId())==0)
							{
								purchaseSPSelectDataTableForSave.remove(custSPDealRewquest);
							}
						}
					}

				}

				if(purcReqObj.getSelectRecord()){
					setDataFcAmount(getDataFcAmount().add(purcReqObj.getForeignCurrencyAmount()));
					setSumOfFcAmount(getSumOfFcAmount().add(purcReqObj.getForeignCurrencyAmount()));
					setSumOfFcAmount((getTotalFcPurchaseAmount() == null ? BigDecimal.ZERO : getTotalFcPurchaseAmount()).add(purcReqObj.getForeignCurrencyAmount()));
					setTotalFcPurchaseAmount(round(getSumOfFcAmount(),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId()))));
					setFcLocalAmount(GetRound.roundBigDecimal((getFcLocalAmount() == null ? BigDecimal.ZERO : getFcLocalAmount()).add(purcReqObj.getLocalAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId()))));
					setSaleAmount(getFcLocalAmount());
					setSumSplSaleAmount(getSumSplSaleAmount().add(purcReqObj.getLocalAmount()));
				}else{
					setDataFcAmount(getDataFcAmount().subtract(purcReqObj.getForeignCurrencyAmount()));
					setSumOfFcAmount(getSumOfFcAmount().subtract(purcReqObj.getForeignCurrencyAmount()));
					setSumOfFcAmount((getTotalFcPurchaseAmount() == null ? BigDecimal.ZERO : getTotalFcPurchaseAmount()).subtract(purcReqObj.getForeignCurrencyAmount()));
					setTotalFcPurchaseAmount(round(getSumOfFcAmount(),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId()))));
					setFcLocalAmount(GetRound.roundBigDecimal((getFcLocalAmount() == null ? BigDecimal.ZERO : getFcLocalAmount()).subtract(purcReqObj.getLocalAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId()))));
					setSaleAmount(getFcLocalAmount());
					setSumSplSaleAmount(getSumSplSaleAmount().subtract(purcReqObj.getLocalAmount()));
				}

				calculateSalesLocalAmount();
				checkingSalesAmountandBankBal();
			}else{
				RequestContext.getCurrentInstance().execute("exchangeError.show();");
				purcReqObj.setSelectRecord(false);
			}

		}catch(NullPointerException  e){ 
			setErrorMsg("method:calFcAmountFromDataTable:");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}

	//CR getting Purchase Requirements for Common pool from Form
	public void DataTableForPurchaseCommonPool(){
		try{
			if(isFcAmountRequired()){
				if(getFcAmount() != null){
					sumOfFCCommonPool=0;
					setSumOfFCCommonPool(sumOfFCCommonPool);
					sumOfFCCommonPool = getFcAmount().intValue();
					setFcAmount(round(new BigDecimal(sumOfFCCommonPool),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId()))));
					setSumOfFCCommonPool(round(new BigDecimal(sumOfFCCommonPool),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId()))).intValue());

					if(getPurchaseMultipleDivision().equals("1") && getPurchaseExchangeRate()!=null ){
						setFcSaleAmount(round((getFcAmount().multiply(getPurchaseExchangeRate())),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId()))));
						setSaleAmount(getFcSaleAmount());
						setFcLocalAmount(getFcSaleAmount());
						setTotalFcPurchaseAmount(round(getFcAmount(),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId()))).add(getSumOfFcAmount()));

					}else if(getPurchaseMultipleDivision().equals("2") && getPurchaseExchangeRate()!=null){
						int decimalvalue = foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId()));
						setFcSaleAmount((getFcAmount().divide(getPurchaseExchangeRate(),decimalvalue,BigDecimal.ROUND_HALF_UP)));
						setSaleAmount(getFcSaleAmount());
						setFcLocalAmount(getFcSaleAmount());
						setTotalFcPurchaseAmount(round(getFcAmount(),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId()))).add(getSumOfFcAmount()));

					}else{
						//RequestContext.getCurrentInstance().execute("selectmuldiv.show();");
						setPurchaseExchangeRate(null);
					}
					calculateSalesLocalAmount();
					checkingSalesAmountandBankBal();
				}else{
					setFcSaleAmount(null);
					setTotalFcPurchaseAmount(null);
					setFcLocalAmount(null);
					setSaleAmount(null);
				}
			}else{
				if(getFcAmount() != null){
					sumOfFCCommonPool=0;
					setSumOfFCCommonPool(sumOfFCCommonPool);
					sumOfFCCommonPool = (getFcAmount() == null ? BigDecimal.ZERO : getFcAmount()) .intValue();
					setFcAmount(round(new BigDecimal(sumOfFCCommonPool),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId()))));
					setSumOfFCCommonPool(round(new BigDecimal(sumOfFCCommonPool),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId()))).intValue());

					if(getPurchaseMultipleDivision().equals("1") && getPurchaseExchangeRate()!=null ){
						setFcSaleAmount(round((getFcAmount().multiply(getPurchaseExchangeRate())),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId()))));
						//setSaleAmount((getSaleAmount() == null ? BigDecimal.ZERO : getSaleAmount()).add(getFcSaleAmount()));
						//setFcLocalAmount((getFcLocalAmount() == null ? BigDecimal.ZERO : getFcLocalAmount()).add(getFcSaleAmount()));
						setSaleAmount((getFcSaleAmount() == null ? BigDecimal.ZERO : getFcSaleAmount()).add(getSumSplSaleAmount()));
						setFcLocalAmount((getFcSaleAmount() == null ? BigDecimal.ZERO : getFcSaleAmount()).add(getSumSplSaleAmount()));
						setTotalFcPurchaseAmount(round(getFcAmount(),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId()))).add(getDataFcAmount()));

					}else if(getPurchaseMultipleDivision().equals("2") && getPurchaseExchangeRate()!=null){
						int decimalvalue = foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId()));
						setFcSaleAmount((getFcAmount().divide(getPurchaseExchangeRate(),decimalvalue,BigDecimal.ROUND_HALF_UP)));
						//setSaleAmount((getSaleAmount() == null ? BigDecimal.ZERO : getSaleAmount()).add(getFcSaleAmount()));
						//setFcLocalAmount((getFcLocalAmount() == null ? BigDecimal.ZERO : getFcLocalAmount()).add(getFcSaleAmount()));
						setSaleAmount((getFcSaleAmount() == null ? BigDecimal.ZERO : getFcSaleAmount()).add(getSumSplSaleAmount()));
						setFcLocalAmount((getFcSaleAmount() == null ? BigDecimal.ZERO : getFcSaleAmount()).add(getSumSplSaleAmount()));
						setTotalFcPurchaseAmount(round(getFcAmount(),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId()))).add(getDataFcAmount()));

					}else{
						//RequestContext.getCurrentInstance().execute("selectmuldiv.show();");
						setPurchaseExchangeRate(null);
					}
					calculateSalesLocalAmount();
					checkingSalesAmountandBankBal();
				}else{
					setTotalFcPurchaseAmount(getTotalFcPurchaseAmount().subtract(new BigDecimal(getSumOfFCCommonPool())));
					setFcLocalAmount(getFcLocalAmount().subtract(getFcSaleAmount()));
					setSaleAmount(getSaleAmount().subtract(getFcSaleAmount()));
					setFcSaleAmount(null);
				}
			}

		}catch(NullPointerException  e){ 
			setErrorMsg("method:DataTableForPurchaseCommonPool:");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}

	}

	//CR validating sales Amount in Sales Details
	public void checkingSalesAmountandBankBal(){
		if(getSaleAmount()!=null && getSaleBalance()!=null){
			if(getSaleCurrency().compareTo(new BigDecimal(sessionManage.getCurrencyId()))==0){
				setBankBalError(true);
				RequestContext.getCurrentInstance().execute("greatersales.show();");
			}else{
				if(getSaleAmount().compareTo(getSaleBalance())>0){
					setBankBalError(false);
					RequestContext.getCurrentInstance().execute("greatersales.show();");
				}else{
					setBankBalError(true);
				}
			}
		}
	}

	//CR Calculating Local Exchange Rate and Sale Local Amount and Total Local Amount
	public void calculateSalesLocalAmount(){
		try{
			BigDecimal totalLAmount=new BigDecimal(0);
			setTotallocalAmount(null);
			setLocalExchangeRate(null);
			setSaleLocalAmount(null);

			if(getSaleAmount()==null || getSaleAvgRate()==null){
				//RequestContext.getCurrentInstance().execute("entersales.show();");
			}else{
				setSaleLocalAmount(round((getSaleAmount().multiply(getSaleAvgRate())),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId()))));
				setTotallocalAmount(getSaleLocalAmount());
				try{
					setLocalExchangeRate(getSaleLocalAmount().divide(getTotalFcPurchaseAmount(),Integer.parseInt(Constants.FX_DEAL_WITH_SUPPLIER_DEAL_DECIMAL_PLACES),BigDecimal.ROUND_HALF_UP));
				}catch(Exception e){
					System.out.println(e.getMessage());
					System.out.println("!!!Catch!!!!getLocalExchangeRate!!!!!!!!"+getLocalExchangeRate()); 
				}

				for (PurchaseReqSplPoolDataTable purchaseReqSplPoolDataTable : purchaseReqSplPoolDataTableList) {
					if(purchaseReqSplPoolDataTable.getSelectRecord()==true){
						totalLAmount = totalLAmount.add(purchaseReqSplPoolDataTable.getForeignCurrencyAmount().multiply(getLocalExchangeRate()));
					}
				}
			}
		}catch(NullPointerException  e){ 
			setErrorMsg("method:calculateSalesLocalAmount:");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}


	// Current Date with the Format 
	public static String getCurrentDateWithFormat() {
		Map<Integer, String> data = new HashMap<Integer, String>();
		for (int i = 0; i < 12; i++) {
			if(i<9){
				data.put(i, "0"+String.valueOf(i+1));
			} else {
				data.put(i, String.valueOf(i+1)); 
			}
		}
		String year = String.valueOf(new Date().getYear()).substring(1, 3);
		return "01/"+data.get(Calendar.getInstance().get(Calendar.MONTH))+"/"+year;
	}	

	//CR MultipleDivision Accordingly Exchange Rate Get Changed
	public void resetExchangeRate(){
		purchaseSpecialPoolDataTable.clear();
		purchaseReqSplPoolDataTableList.clear();
		setSumOfFcAmount(new BigDecimal(0));
		setPurchaseExchangeRate(null);
		setFcLocalAmount(null);
		setSaleAmount(null);
		setFcSaleAmount(null);
		if(getFcAmount()!=null){
			setTotalFcPurchaseAmount(round(getFcAmount(),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId()))));
			calculateSalesLocalAmount();
		}
		dataTableForPurchaseSplPool();
	}

	//CR Deal Year Changing from User Financial year Table 
	public void getDealYearChange(AjaxBehaviorEvent event){

		DealYearList = generalService.getDealYear(getUserDealDate());
		if(DealYearList!=null){
			dealYear = DealYearList.get(0).getFinancialYear();
			dealYearId=DealYearList.get(0).getFinancialYearID();
			setUserDealYear(dealYear);
			setUserDealYearId(dealYearId);				
		}

	}

	//validate for  valueDate in PurchseDetails,SalesDeatils Screen 
	public void popValudate()
	{
		if(getPurchaseDate()!=null){
			String pucahseDate=getPurchaseDate();
			String saleDate =(new SimpleDateFormat("dd/MM/yyyy").format(getSaleValueDate()).toString());
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			Date d1 = null;
			Date d2 = null;
			try {
				d1 = format.parse(saleDate);
				d2 = format.parse(pucahseDate);

				long diff =  d1.getTime() - d2.getTime();
				long diffDays = diff / (24 * 60 * 60 * 1000);
				if(diffDays>=3)
				{
					RequestContext.getCurrentInstance().execute("PF('pdays').show();");	
					setSaleValueDate(null);
				}else if(diffDays<=-2)
				{
					RequestContext.getCurrentInstance().execute("PF('mdays').show();");
					setSaleValueDate(null);
				}
			}
			catch(NullPointerException  e){ 
				setErrorMsg("method:popValudate");
				RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
			}
			catch (Exception e) {
				setErrorMsg(e.getMessage());
				RequestContext.getCurrentInstance().execute("csp.show();");
			}
		}else{
			RequestContext.getCurrentInstance().execute("valuedayid.show();");
		}
	}

	public void clearbyBankSelectPurchase(){
		setTotalFcPurchaseAmount(null);
		setPurchaseInstrunction(null);
		setPurchaseInstructionDesc(null);
		setPurchaseAccountNumber(null);
		setPurchaseBankAccountId(null);
		setSinglacc(null);
		setDate3(null);
		setPurchaseCurrency(null);
		setPurchaseCurrencyName(null);
		setPurchaseInstrunctionForUpdate(null);
		setBooRenderPurchaseInstrunctionForUpdate(true);
		setBooRenderPurchaseInstrunction(false);
		setBooRenderBankId(false);
		setBooRenderBankIdForUpdate(true);
		setBooSelectbankPAcc(true);
		setMultiselectPAcc(false);
		pstandardInstrnforPurchase.clear();
		purchaseSpecialPoolDataTable.clear();
		purchaseReqSplPoolDataTableList.clear();
		lstofDeleteSplPool.clear();
		lstofClearSplPool.clear();
		bankCountryCurrencyList.clear();
		accountNumberList.clear();
		setPurchaseMultipleDivision(null);
		setPurchaseExchangeRate(null);
		setFcLocalAmount(null);
		setSaleAmount(null);
		if(getFcAmount()!=null){
			setTotalFcPurchaseAmount(round(getFcAmount(),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId()))));
			calculateSalesLocalAmount();
			setFcSaleAmount(null);
		}
	}

	//CR to get Currency In Multiple and Single Panel 
	public void populateCurrencyBasedOnBankForPurchase() {
		try{
			setSplCustMsg(false);
			setFcAmountRequired(true);
			List<BankAccountDetails> purchaseCurrency = new ArrayList<BankAccountDetails>();
			List<BigDecimal> duplicatesCheck = new ArrayList<BigDecimal>();

			// List<BankAccountDetails> purchaseCurrency = fundEstimationService.getCurrencyOfBank(getPurchaseBank());
			//removed Duplicates from Bank Currency 
			List<BankAccountDetails> lstAllpurchaseCurrency = fXDetailInformationService.getCurrencyBasedOnBankCountry(getPurchaseBank());
			for (BankAccountDetails bankAccountDetails : lstAllpurchaseCurrency) {
				if(!duplicatesCheck.contains(bankAccountDetails.getFsCurrencyMaster().getCurrencyId())){
					duplicatesCheck.add(bankAccountDetails.getFsCurrencyMaster().getCurrencyId());
					purchaseCurrency.add(bankAccountDetails);
				}
			}

			//Clearing all Purchase Details while Bank Selection
			clearbyBankSelectPurchase();

			bankcurrencySize = purchaseCurrency.size();
			if(bankcurrencySize==0){
				RequestContext.getCurrentInstance().execute("nodata.show();");
			}else if(bankcurrencySize==1){
				for (BankAccountDetails element : purchaseCurrency) {
					setPurchaseCurrency(element.getFsCurrencyMaster().getCurrencyId());
					setPurchaseCurrencyName(element.getFsCurrencyMaster().getCurrencyName());
				}
				setBooRenderBankId(false);
				setBooRenderBankIdForUpdate(true);
				purchaseStandardInstruction();
			}else{
				setBooRenderBankId(true);
				setBooRenderBankIdForUpdate(false);
				setBankCountryCurrencyList(purchaseCurrency);
			}
		}catch(NullPointerException  e){ 
			setErrorMsg("method:populateCurrencyBasedOnBankForPurchase:");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}

	//CR to get Currency In Multiple and Single Panel for Sale
	public void populateCurrencyBasedOnBankForSale(){
		try{
			setSalePurchaseInstrunction(null);
			setSalePurchaseInstructionDesc(null);
			setSaleAccountBalanceId(null);
			setSaleAccountNoId(null);
			setSaleAccountNumber(null);
			setSaleAcctNo(null);
			setSaleBalance(null);
			setSaleCurrency(null);
			setSaleCurrencyName(null);
			setSalePurchaseInstrunctionForUpdate(null);
			pstandardInstrnforSales.clear();
			currencylistforsale.clear();
			accountNumberListforSale.clear();
			setBooRenderStandInsIDForUpdate(true);
			setBooRenderStandInsID(false);
			setBooRenderCurrencySingle(true);
			setBooRenderCurrencyId(false);
			setSaleAvgRate(null);
			setTotallocalAmount(null);
			setLocalExchangeRate(null);
			setSaleLocalAmount(null);
			setOneAccNoSal(true);
			setMulAccNosal(false);

			List<BankAccountDetails> saleCurrency = new ArrayList<BankAccountDetails>();
			List<BigDecimal> duplicatesCheck = new ArrayList<BigDecimal>();

			List<BankAccountDetails> lstAllSaleCurrency = fXDetailInformationService.getCurrencyBasedOnBankCountry(getSaleBank());
			for (BankAccountDetails bankAccountDetails : lstAllSaleCurrency) {
				if(!duplicatesCheck.contains(bankAccountDetails.getFsCurrencyMaster().getCurrencyId())){
					duplicatesCheck.add(bankAccountDetails.getFsCurrencyMaster().getCurrencyId());
					saleCurrency.add(bankAccountDetails);
				}
			}

			bankcurrencySize = saleCurrency.size();
			if(bankcurrencySize==0){
				RequestContext.getCurrentInstance().execute("nodata.show();");
			}else if(bankcurrencySize==1){

				for (BankAccountDetails element : saleCurrency) {

					setSaleCurrency(element.getFsCurrencyMaster().getCurrencyId());
					setSaleCurrencyName(element.getFsCurrencyMaster().getCurrencyName());
				}
				salesStandardInstruction();
				setBooRenderCurrencySingle(true);
				setBooRenderCurrencyId(false);

			}else{
				setBooRenderCurrencySingle(false);
				setBooRenderCurrencyId(true);
				setCurrencylistforsale(saleCurrency);
			}
		}catch(NullPointerException  e){ 
			setErrorMsg("method:populateCurrencyBasedOnBankForPurchase:");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}

	/**
	 * Responsible to make the round value
	 * 
	 * @param value
	 * @param places
	 * @return
	 */

	public static BigDecimal round(BigDecimal value, int places) {
		System.out.println("!!round value!!! "+value+" !!places!! "+places);
		if (places < 0) throw new IllegalArgumentException();

		BigDecimal bd = value;
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd;
	}

	private Date effectiveMinDate = new Date();
	private Date effectiveMaxDate = new Date();
	public Date getEffectiveMinDate() {
		return effectiveMinDate;
	}

	public void setEffectiveMinDate(Date effectiveMinDate) {
		this.effectiveMinDate = effectiveMinDate;
	}

	public void onDateSelectForDealDate(SelectEvent event) {

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String dealDate=format.format(event.getObject());

		try {
			setUserDealDate(format.parse(dealDate));
			setEffectiveMinDate(format.parse(dealDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	public Date getEffectiveMaxDate() {

		Date now = new Date();

		Calendar cal = Calendar.getInstance();

		cal.setTime(now);

		cal.add(Calendar.DAY_OF_YEAR, Integer.parseInt(Constants.FX_DEAL_WITH_SUPPLIER_DEAL_DATE_ALLOW));

		Date tomorrow = cal.getTime();
		effectiveMaxDate=tomorrow;
		return effectiveMaxDate;
	}

	public void setEffectiveMaxDate(Date effectiveMaxDate) {
		this.effectiveMaxDate = effectiveMaxDate;
	}

	public void clearSalesAfterSameCurrencyMatch(){

		setSaleBank(null);
		setSaleCurrency(null);
		setSaleCurrencyName(null);
		setSalesCheckbox(false);
		setSaleBalance(null);
		setSaleValueDate(null);
		setSaleAvgRate(null);
		setSalePurchaseInstrunction(null);
		setSalePurchaseInstructionDesc(null);
		setSaleAccountBalanceId(null);
		setSaleAccountNoId(null);
		setSaleAccountNumber(null);
		setSaleAcctNo(null);
		setSaleLocalAmount(null);
		setSalePurchaseInstrunctionForUpdate(null);
		setBooRenderCurrencyId(false);
		setBooRenderCurrencySingle(true);
		setOneAccNoSal(true);
		setMulAccNosal(false);
		setBooRenderStandInsID(false);
		setBooRenderStandInsIDForUpdate(true);
		accountNumberListforSale.clear();
		pstandardInstrnforSales.clear();
		cstandardInstrnDetailsforSales.clear();
		lstTresSISale.clear();
		setFaAccountNumberForSale(null);

	}

	public void clearPurchaseAfterSameCurrencyMatch(){
		setPurchaseBank(null);
		setPurchaseCurrency(null);
		setPurchaseCurrencyName(null);
		if(bankCountryCurrencyList!=null){
			bankCountryCurrencyList.clear();
		}
	}


	public void getDocumentNoDropDown(){
		try{
			setLstofUnApproved(null);
			//List<TreasuryDealHeader> lstofUnApprovedTDlHeader = fXDetailInformationService.fetchDocumentNumber();
			List<TreasuryDealHeader> lstofUnApprovedTDlHeader = fXDetailInformationService.fetchDocumentNumberFromTreasDealheader(Constants.Fx_BankDealType ,Constants.U,getDealYear());
			if(lstofUnApprovedTDlHeader.size()!=0){
				setLstofUnApproved(lstofUnApprovedTDlHeader);
			}else{
				setTeasuryDealHeaderId(null);
				setPurchaseTreasuryInstructionId(null);
				setPurchaseDealDetId(null);
				setSaleDealDetId(null);
				setSaleTreasuryInstructionId(null);
				setEffectiveMinDate(new Date());
				setDealReference("");
				setDealDate("");
				setDealDateStr(null);
				setDealWith(null);
				setContact("");
				setConcludedBy("");
				setReuterReference("");
				setRemark("");
				setRenderRef(true);
				setRenderRefEditable(false);
				setRenderdealdate(true);
				setRenderdealedit(false);
				setUserDealDate(new Date());

				setStandardInstructionPkForPurchageDetail(null);
				setStandardInstructionPkForSaleDetail(null);
				clearPurchaseDetails();
				clearPurchaseRequirment();
				clearPurchaseRequirmentforSpecial();
				clearSalesPurchase();
				setEftAmount(null);
				setTtAmount(null);
				setCashAmount(null);


			}
		}catch(NullPointerException  e){ 
			setErrorMsg("method:getDocumentNoDropDown:");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}

	}

	public void approve(){

		//if(isSalesCheckbox()!=false && isPurchaseCheckbox()!=false){
		try{
			if(!(getModifiedByHeader() == null ? getCreatedByHeader() :getModifiedByHeader()).equalsIgnoreCase(sessionManage.getUserName())){


				fXDetailInformationService.approveHeader(getTeasuryDealHeaderId(), sessionManage.getUserName(), new Date());
				fXDetailInformationService.approveDetailsPurchase(getPurchaseDealDetId(), sessionManage.getUserName(), new Date());
				fXDetailInformationService.approveDetailsSale(getSaleDealDetId(),sessionManage.getUserName(), new Date());
				//fXDetailInformationService.callApproveProcedure(sessionManage.getCountryId(), sessionManage.getCompanyId(),getDocumentNo() , new BigDecimal(getDealYear()), getDocSerialIdNumberForSave());

				for (TreasuryStandardInstruction stndInstrnDetails : lstTresSIPur){
					fXDetailInformationService.approveStandardInstructionForPurchase(stndInstrnDetails.getTreasuryStandardInstructionId(), sessionManage.getUserName(), new Date());


				}
				for (TreasuryStandardInstruction lstSaleSI :lstTresSISale) {
					fXDetailInformationService.approveStandardInstructionForsale(lstSaleSI.getTreasuryStandardInstructionId(), sessionManage.getUserName(), new Date());
				}



				setRenderMainPanel(false);	
				setSuccessRender(true);

				//RequestContext.getCurrentInstance().execute("approvedsuccess.show();");


			}else{
				RequestContext.getCurrentInstance().execute("notabletoApprove.show();");	
				setRenderMainPanel(true);	
				setSuccessRender(false);
			}	
		}catch(Exception e){
			log.error(e.getMessage());
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("approveException.show();");

		}
		/*}else{
					RequestContext.getCurrentInstance().execute("checkBoxVerify.show();");
					setRenderMainPanel(true);	
					setSuccessRender(false);

				}*/
	}

	private String valueDateStr = null;
	private String saleValueDateStr = null;
	private String dealDateStr = null;
	private String exceptionMessage = null;

	public String getValueDateStr() {
		return valueDateStr;
	}

	public void setValueDateStr(String valueDateStr) {
		this.valueDateStr = valueDateStr;
	}

	public String getSaleValueDateStr() {
		return saleValueDateStr;
	}

	public void setSaleValueDateStr(String saleValueDateStr) {
		this.saleValueDateStr = saleValueDateStr;
	}

	public String getDealDateStr() {
		return dealDateStr;
	}

	public void setDealDateStr(String dealDateStr) {
		this.dealDateStr = dealDateStr;
	}



	/*
	 * Report Generation
	 */

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}


	private List<TreasuryDealInfo> treasuryDealInfoList=new CopyOnWriteArrayList<TreasuryDealInfo>();

	JasperPrint jasperPrint;

	public void dealTicketReportInit() throws JRException {
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(treasuryDealInfoList);
		String reportPath = FacesContext.getCurrentInstance() .getExternalContext().getRealPath("reports/design/dealticket.jasper");
		jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap(), beanCollectionDataSource);
	}

	public void generateDealTicketReport(ActionEvent actionEvent) throws JRException, IOException, ParseException {

		try {

			fetchTreasurydealInfo(getDealYear() ,new BigDecimal(getDocumentNo().toString()),getDocSerialIdNumberForSave());
			dealTicketReportInit();
			HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			httpServletResponse.addHeader("Content-disposition","attachment; filename=dealticket.pdf");

			ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint,servletOutputStream);
			FacesContext.getCurrentInstance().responseComplete();
		} catch(NullPointerException  e){ 
			setErrorMsg("method:generateDealTicketReport:");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}

	}

	public void fetchTreasurydealInfo(BigDecimal documentYear,BigDecimal documentId,BigDecimal documentNumber) {
		try{
			treasuryDealInfoList.clear();
			List<TreasuryDealInfo> treasuryDealInfoList1=new CopyOnWriteArrayList<TreasuryDealInfo>();
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

			String subReportPath = realPath + Constants.SUB_REPORT_PATH;
			List<ViewTreasuryDeal> treasuryDealInfo1 = new ArrayList<ViewTreasuryDeal>();
			List<ViewTreasuryDeal> saleList = new ArrayList<ViewTreasuryDeal>();


			treasuryDealInfo1 = fXDetailInformationService.viewTreauryDealwithBank(documentYear,documentNumber, "PD", "B");
			saleList = fXDetailInformationService.viewTreauryDealwithBank(documentYear,documentNumber, "SD","B");

			TreasuryDealInfo treasuryDealInfoPurchase = null;

			int i = 0;

			for (ViewTreasuryDeal viewTreasuryDeal : treasuryDealInfo1) {

				if(i==1){
					break;
				}

				treasuryDealInfoPurchase = new TreasuryDealInfo();
				treasuryDealInfoPurchase.setTreasuryDealHeaderId(viewTreasuryDeal.getTreasuryDealHeaderId());
				treasuryDealInfoPurchase.setTreasuryDealDetailsId(viewTreasuryDeal.getTreasuryDealDetailsId());
				treasuryDealInfoPurchase.setDealWith(viewTreasuryDeal.getBankAddress());
				treasuryDealInfoPurchase.setBankAddress(viewTreasuryDeal.getBankAddress());
				treasuryDealInfoPurchase.setCurrencyname(viewTreasuryDeal.getCurrencyname());
				treasuryDealInfoPurchase.setDealConcludedBy(viewTreasuryDeal.getDealConcludedBy());
				treasuryDealInfoPurchase.setDealConcludedWith(viewTreasuryDeal.getDealConcludedWith());
				treasuryDealInfoPurchase.setDealDescription(viewTreasuryDeal.getDealDescription());
				treasuryDealInfoPurchase.setDocumentDate(viewTreasuryDeal.getDocumentDate());
				treasuryDealInfoPurchase.setDocumentFinanceYear(viewTreasuryDeal.getDocumentFinanceYear());
				treasuryDealInfoPurchase.setApplicationCountryId(viewTreasuryDeal.getApplicationCountryId());
				treasuryDealInfoPurchase.setDealWithType(viewTreasuryDeal.getDealWithType());
				treasuryDealInfoPurchase.setCompanyid(viewTreasuryDeal.getCompanyid());
				treasuryDealInfoPurchase.setDealNo(viewTreasuryDeal.getDealNo());
				BigDecimal totalPurchaseFcAmount = round((viewTreasuryDeal.getTotalPurchaseFcAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(viewTreasuryDeal.getCurrencyId()));
				treasuryDealInfoPurchase.setTotalPurchaseFcAmount(totalPurchaseFcAmount);
				treasuryDealInfoPurchase.setPurchaseExchangeRate(viewTreasuryDeal.getPurchaseExchangeRate());
				treasuryDealInfoPurchase.setFaAccountNo(viewTreasuryDeal.getFaAccountNo());
				treasuryDealInfoPurchase.setFcAmount(viewTreasuryDeal.getFcAmount());
				treasuryDealInfoPurchase.setLocalExchangeRate(viewTreasuryDeal.getLocalExchangeRate());
				treasuryDealInfoPurchase.setLocalAmount(viewTreasuryDeal.getLocalAmount());
				treasuryDealInfoPurchase.setLineType(viewTreasuryDeal.getLineType());
				treasuryDealInfoPurchase.setValuedatePD(viewTreasuryDeal.getValuedate());
				treasuryDealInfoPurchase.setCurrencyCode(viewTreasuryDeal.getCurrencyCode());
				treasuryDealInfoPurchase.setLogoPath(logoPath);
				treasuryDealInfoPurchase.setRemarks(viewTreasuryDeal.getRemarks()==null ? "" :viewTreasuryDeal.getRemarks());

				List<Employee> empList = generalService.getEmployeeDetail(sessionManage.getEmployeeId());
				if(empList.size()>0){
					treasuryDealInfoPurchase.setAuthorizedBy(empList.get(0).getEmployeeName());
				}
				treasuryDealInfoPurchase.setDealer(viewTreasuryDeal.getDealConcludedBy());
				treasuryDealInfoPurchase.setPdAcNo(viewTreasuryDeal.getAccountNumber());
				treasuryDealInfoPurchase.setPdBankCode(viewTreasuryDeal.getBankCode());
				treasuryDealInfoPurchase.setFaxNo("1");
				BigDecimal denominationId = generalService.getDenomiationId(Constants.KUWAIT_QUOTE_NAME);
				BigDecimal totalFaAmount = round((viewTreasuryDeal.getTotalPurchaseFcAmount().multiply(viewTreasuryDeal.getLocalExchangeRate())),denominationId.intValue());
				treasuryDealInfoPurchase.setTotalFaAmount(totalFaAmount);
				treasuryDealInfoPurchase.setTotalPdAmount(totalFaAmount);

				//	treasuryDealInfoPurchase.setPurchaseStandardInstruction(viewTreasuryDeal.getStandardInstruction());

				treasuryDealInfoPurchase.setPurchaseCurrencyCode(generalService.getCurrencyQuote(viewTreasuryDeal.getCurrencyId()));

				for (ViewTreasuryDeal viewTreasuryDeal1 : saleList) {
					treasuryDealInfoPurchase.setCurrencynameSD(viewTreasuryDeal1.getCurrencyname());
					treasuryDealInfoPurchase.setSdBankCode(viewTreasuryDeal1.getBankCode());
					treasuryDealInfoPurchase.setSdAcNo(viewTreasuryDeal1.getAccountNumber());
					treasuryDealInfoPurchase.setSoldAccountNo(viewTreasuryDeal1.getFaAccountNo());
					BigDecimal saleAmount=round((viewTreasuryDeal1.getSaleAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(viewTreasuryDeal1.getCurrencyId()));
					treasuryDealInfoPurchase.setSaleAmount(saleAmount);
					treasuryDealInfoPurchase.setSaleExchangeRate(viewTreasuryDeal1.getLocalExchangeRate());
					BigDecimal totalSoldAmount=round((viewTreasuryDeal1.getSaleAmount().multiply(viewTreasuryDeal1.getLocalExchangeRate())),denominationId.intValue());
					treasuryDealInfoPurchase.setTotalSoldAmount(totalSoldAmount);
					treasuryDealInfoPurchase.setSaleCurrencyCode(generalService.getCurrencyQuote(viewTreasuryDeal1.getCurrencyId()));
					treasuryDealInfoPurchase.setValuedateSD(viewTreasuryDeal1.getValuedate());
					//		treasuryDealInfoPurchase.setSalesStandardInstruction(viewTreasuryDeal1.getStandardInstruction());


				}
				treasuryDealInfoPurchase.setSubReportPath(subReportPath);
				treasuryDealInfoList1.add(treasuryDealInfoPurchase);
				treasuryDealInfoPurchase.setTreasuryDealInfoList(treasuryDealInfoList1);

				treasuryDealInfoList.add(treasuryDealInfoPurchase);
				i++;
			}
		}catch(NullPointerException  e){ 
			setErrorMsg("method:fetchTreasurydealInfo:");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}

	}

	public void dealPaymentReportInit() throws JRException {
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(treasuryDealInfoList);
		String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("reports/design/dealpayment.jasper");
		jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap(),beanCollectionDataSource);
	}

	public void generateDealPaymentReport(ActionEvent actionEvent) throws JRException, IOException, ParseException {

		try {

			fetchTreasurydealInfo(getDealYear() ,new BigDecimal(getDocumentNo().toString()),getDocSerialIdNumberForSave());
			dealPaymentReportInit();
			HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			httpServletResponse.addHeader("Content-disposition","attachment; filename=dealpayment.pdf");

			ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint,servletOutputStream);
			FacesContext.getCurrentInstance().responseComplete();

		} catch(NullPointerException  e){ 
			setErrorMsg("method:generateDealPaymentReport:");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public void fetchfinanceYear() {

		trnxYearList.clear();

		try {

			List<UserFinancialYear> lstFinancialYear = stopPaymentService.getTransferYearList();
			if (lstFinancialYear.size() != 0) {
				trnxYearList.addAll(lstFinancialYear);
			}

		} catch (NullPointerException ne) {
			setErrorMsg(ne.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
			return;
		} catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
			return;
		}

	}


}


