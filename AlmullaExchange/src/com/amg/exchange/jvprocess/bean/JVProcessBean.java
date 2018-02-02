package com.amg.exchange.jvprocess.bean;

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

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.dto.BankMasterDTO;
import com.amg.exchange.common.dto.CurrencyMasterDTO;
import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.foreigncurrency.model.CurrencyWiseDenomination;
import com.amg.exchange.foreigncurrency.model.ForeignCurrencyAdjust;
import com.amg.exchange.foreigncurrency.model.Stock;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.foreigncurrency.service.ForeignLocalCurrencyDenominationService;
import com.amg.exchange.jvprocess.service.IJVProcessService;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.remittance.bean.KIOSKDenominationDataTable;
import com.amg.exchange.stock.service.IStockAdjustmentService;
import com.amg.exchange.treasury.dao.ISpecialCustomerDealRequestDao;
import com.amg.exchange.treasury.deal.supplier.model.DayBookDetails;
import com.amg.exchange.treasury.deal.supplier.model.DayBookHeader;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.treasury.model.TreasuryDealHeader;
import com.amg.exchange.treasury.service.IFundEstimationService;
import com.amg.exchange.treasury.service.ISpecialCustomerDealRequestService;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("jVProcessBean")
@Scope("session")
public class JVProcessBean<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final Logger log = Logger.getLogger(JVProcessBean.class);

	private String companyName;
	private BigDecimal finaceYear;
	private BigDecimal documentSerialIdNumber;
	private BigDecimal documentId;
	private String jvReason;
	private String jvDescription;
	private BigDecimal totalDebits;
	private BigDecimal totalCredit;
	private BigDecimal netAmount;
	private String subDescription;
	private BigDecimal jvBankId;
	private BigDecimal companyId;

	private List<BankMasterDTO> jvBankLst;
	private List<BankAccountDetails> lstjvAccountNumber;

	private String jvAccNumber;
	private BigDecimal jvCurrencyId;
	private BigDecimal jvAccNoForUpdate;
	private String jvPerticulars;
	private String jvGlAcNo;
	private String subCode;
	private BigDecimal foreignAmount;
	private BigDecimal exchangeRate;
	private BigDecimal kwdAmount;
	private Date hdCalValueDate;
	private Date effectiveMinDate = new Date();
	private Date effectiveMaxDate = new Date();;
	private List<CurrencyMasterDTO> currencyListFromDB;
	private BigDecimal financialYearId;
	private String processIn=Constants.Yes;
	private Boolean booRead=false;
	private BigDecimal totalNoofNotes;
	private BigDecimal totalNewBalance;
	private BigDecimal jvDocumentNumber;
	private ArrayList<KIOSKDenominationDataTable> lstDenominationDataTable = null;
	private ArrayList<KIOSKDenominationDataTable> lstDenominationForDailog=null;

	private List<JVProcessDataTableBean> lstJVProcessDataTableBean;
	SessionStateManage sessionStateManage=new SessionStateManage();

	private List<JVProcessDataTableBean> lstJVDetails;
	private List<JVReasonDTO> jvReasonList;


	private Boolean renderDenominationDataTable=false;

	private Boolean renderDocumentSelectMenu=false;
	private Boolean renderDocumentInput=false;

	private List<DayBookHeader> dayBookList=null;



	public List<DayBookHeader> getDayBookList() {
		return dayBookList;
	}

	public void setDayBookList(List<DayBookHeader> dayBookList) {
		this.dayBookList = dayBookList;
	}

	private String jvGlAcNoOne;
	private String jvGlAcNoTwo;
	private String jvGlAcNoThree;
	private String jvGlAcNoFour;
	private String jvGlAcNoFive;
	private String jvGlAcNoSix;
	private String jvGlAcNoSeven;

	private BigDecimal daybookHeaderId;
	private BigDecimal dayBookDetailsId;
	private boolean renderSavePanel;

	private boolean subCodeReadOnly;
	private BigDecimal dayBooDtlLineNo;




	public BigDecimal getDayBooDtlLineNo() {
		return dayBooDtlLineNo;
	}

	public void setDayBooDtlLineNo(BigDecimal dayBooDtlLineNo) {
		this.dayBooDtlLineNo = dayBooDtlLineNo;
	}

	public boolean isSubCodeReadOnly() {
		return subCodeReadOnly;
	}

	public void setSubCodeReadOnly(boolean subCodeReadOnly) {
		this.subCodeReadOnly = subCodeReadOnly;
	}

	public List<JVReasonDTO> getJvReasonList() {
		return jvReasonList;
	}

	public void setJvReasonList(List<JVReasonDTO> jvReasonList) {
		this.jvReasonList = jvReasonList;
	}

	public boolean isRenderSavePanel() {
		return renderSavePanel;
	}

	public void setRenderSavePanel(boolean renderSavePanel) {
		this.renderSavePanel = renderSavePanel;
	}

	public BigDecimal getDayBookDetailsId() {
		return dayBookDetailsId;
	}

	public void setDayBookDetailsId(BigDecimal dayBookDetailsId) {
		this.dayBookDetailsId = dayBookDetailsId;
	}

	public BigDecimal getDocumentId() {
		return documentId;
	}

	public void setDocumentId(BigDecimal documentId) {
		this.documentId = documentId;
	}

	public BigDecimal getDaybookHeaderId() {
		return daybookHeaderId;
	}

	public void setDaybookHeaderId(BigDecimal daybookHeaderId) {
		this.daybookHeaderId = daybookHeaderId;
	}

	public String getJvGlAcNoOne() {
		return jvGlAcNoOne;
	}

	public void setJvGlAcNoOne(String jvGlAcNoOne) {
		this.jvGlAcNoOne = jvGlAcNoOne;
	}

	public String getJvGlAcNoTwo() {
		return jvGlAcNoTwo;
	}

	public void setJvGlAcNoTwo(String jvGlAcNoTwo) {
		this.jvGlAcNoTwo = jvGlAcNoTwo;
	}

	public String getJvGlAcNoThree() {
		return jvGlAcNoThree;
	}

	public void setJvGlAcNoThree(String jvGlAcNoThree) {
		this.jvGlAcNoThree = jvGlAcNoThree;
	}

	public String getJvGlAcNoFour() {
		return jvGlAcNoFour;
	}

	public void setJvGlAcNoFour(String jvGlAcNoFour) {
		this.jvGlAcNoFour = jvGlAcNoFour;
	}

	public String getJvGlAcNoFive() {
		return jvGlAcNoFive;
	}

	public void setJvGlAcNoFive(String jvGlAcNoFive) {
		this.jvGlAcNoFive = jvGlAcNoFive;
	}

	public String getJvGlAcNoSix() {
		return jvGlAcNoSix;
	}

	public void setJvGlAcNoSix(String jvGlAcNoSix) {
		this.jvGlAcNoSix = jvGlAcNoSix;
	}

	public String getJvGlAcNoSeven() {
		return jvGlAcNoSeven;
	}

	public void setJvGlAcNoSeven(String jvGlAcNoSeven) {
		this.jvGlAcNoSeven = jvGlAcNoSeven;
	}

	public BigDecimal getJvDocumentNumber() {
		return jvDocumentNumber;
	}

	public void setJvDocumentNumber(BigDecimal jvDocumentNumber) {
		this.jvDocumentNumber = jvDocumentNumber;
	}

	public Boolean getRenderDocumentSelectMenu() {
		return renderDocumentSelectMenu;
	}

	public void setRenderDocumentSelectMenu(Boolean renderDocumentSelectMenu) {
		this.renderDocumentSelectMenu = renderDocumentSelectMenu;
	}

	public Boolean getRenderDocumentInput() {
		return renderDocumentInput;
	}

	public void setRenderDocumentInput(Boolean renderDocumentInput) {
		this.renderDocumentInput = renderDocumentInput;
	}

	public Boolean getRenderDenominationDataTable() {
		return renderDenominationDataTable;
	}

	public void setRenderDenominationDataTable(Boolean renderDenominationDataTable) {
		this.renderDenominationDataTable = renderDenominationDataTable;
	}

	public List<JVProcessDataTableBean> getLstJVProcessDataTableBean() {
		return lstJVProcessDataTableBean;
	}

	public void setLstJVProcessDataTableBean(
			List<JVProcessDataTableBean> lstJVProcessDataTableBean) {
		this.lstJVProcessDataTableBean = lstJVProcessDataTableBean;
	}

	private boolean booRenderJvAccNo;

	private boolean booRenderJvAccNoForUpdate;
	private boolean renderJVDetailsDataTable;


	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public BigDecimal getFinaceYear() {
		return finaceYear;
	}

	public void setFinaceYear(BigDecimal finaceYear) {
		this.finaceYear = finaceYear;
	}

	public BigDecimal getDocumentSerialIdNumber() {
		return documentSerialIdNumber;
	}

	public void setDocumentSerialIdNumber(BigDecimal documentSerialIdNumber) {
		this.documentSerialIdNumber = documentSerialIdNumber;
	}

	public String getJvReason() {
		return jvReason;
	}

	public void setJvReason(String jvReason) {
		this.jvReason = jvReason;
	}

	public String getJvDescription() {
		return jvDescription;
	}

	public void setJvDescription(String jvDescription) {
		this.jvDescription = jvDescription;
	}

	public BigDecimal getTotalDebits() {
		return totalDebits;
	}

	public void setTotalDebits(BigDecimal totalDebits) {
		this.totalDebits = totalDebits;
	}

	public BigDecimal getTotalCredit() {
		return totalCredit;
	}

	public void setTotalCredit(BigDecimal totalCredit) {
		this.totalCredit = totalCredit;
	}

	public BigDecimal getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(BigDecimal netAmount) {
		this.netAmount = netAmount;
	}



	public String getSubDescription() {
		return subDescription;
	}

	public void setSubDescription(String subDescription) {
		this.subDescription = subDescription;
	}

	public BigDecimal getJvBankId() {
		return jvBankId;
	}

	public void setJvBankId(BigDecimal jvBankId) {
		this.jvBankId = jvBankId;
	}

	public List<BankMasterDTO> getJvBankLst() {
		return jvBankLst;
	}

	public void setJvBankLst(List<BankMasterDTO> jvBankLst) {
		this.jvBankLst = jvBankLst;
	}

	public String getJvAccNumber() {
		return jvAccNumber;
	}

	public void setJvAccNumber(String jvAccNumber) {
		this.jvAccNumber = jvAccNumber;
	}

	public BigDecimal getJvCurrencyId() {
		return jvCurrencyId;
	}

	public void setJvCurrencyId(BigDecimal jvCurrencyId) {
		this.jvCurrencyId = jvCurrencyId;
	}

	public BigDecimal getJvAccNoForUpdate() {
		return jvAccNoForUpdate;
	}

	public void setJvAccNoForUpdate(BigDecimal jvAccNoForUpdate) {
		this.jvAccNoForUpdate = jvAccNoForUpdate;
	}

	public String getJvPerticulars() {
		return jvPerticulars;
	}

	public void setJvPerticulars(String jvPerticulars) {
		this.jvPerticulars = jvPerticulars;
	}

	public String getJvGlAcNo() {
		return jvGlAcNo;
	}

	public void setJvGlAcNo(String jvGlAcNo) {
		this.jvGlAcNo = jvGlAcNo;
	}

	public String getSubCode() {
		return subCode;
	}

	public void setSubCode(String subCode) {
		this.subCode = subCode;
	}

	public BigDecimal getForeignAmount() {
		return foreignAmount;
	}

	public void setForeignAmount(BigDecimal foreignAmount) {
		this.foreignAmount = foreignAmount;
	}

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public BigDecimal getKwdAmount() {
		return kwdAmount;
	}

	public void setKwdAmount(BigDecimal kwdAmount) {
		this.kwdAmount = kwdAmount;
	}

	public boolean isBooRenderJvAccNo() {
		return booRenderJvAccNo;
	}

	public void setBooRenderJvAccNo(boolean booRenderJvAccNo) {
		this.booRenderJvAccNo = booRenderJvAccNo;
	}


	public Date getHdCalValueDate() {
		return hdCalValueDate;
	}

	public void setHdCalValueDate(Date hdCalValueDate) {
		this.hdCalValueDate = hdCalValueDate;
	}

	public Date getEffectiveMinDate() {
		return effectiveMinDate;
	}

	public void setEffectiveMinDate(Date effectiveMinDate) {
		this.effectiveMinDate = effectiveMinDate;
	}

	public Date getEffectiveMaxDate() {
		return effectiveMaxDate;
	}

	public void setEffectiveMaxDate(Date effectiveMaxDate) {
		this.effectiveMaxDate = effectiveMaxDate;
	}

	public BigDecimal getFinancialYearId() {
		return financialYearId;
	}

	public void setFinancialYearId(BigDecimal financialYearId) {
		this.financialYearId = financialYearId;
	}

	public List<BankAccountDetails> getLstjvAccountNumber() {
		return lstjvAccountNumber;
	}

	public void setLstjvAccountNumber(List<BankAccountDetails> lstjvAccountNumber) {
		this.lstjvAccountNumber = lstjvAccountNumber;
	}

	public List<CurrencyMasterDTO> getCurrencyListFromDB() {
		return currencyListFromDB;
	}

	public void setCurrencyListFromDB(List<CurrencyMasterDTO> currencyListFromDB) {
		this.currencyListFromDB = currencyListFromDB;
	}


	public BigDecimal getCompanyId() {
		return companyId;
	}

	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}

	public BigDecimal getTotalNoofNotes() {
		return totalNoofNotes;
	}

	public void setTotalNoofNotes(BigDecimal totalNoofNotes) {
		this.totalNoofNotes = totalNoofNotes;
	}

	public BigDecimal getTotalNewBalance() {
		return totalNewBalance;
	}

	public void setTotalNewBalance(BigDecimal totalNewBalance) {
		this.totalNewBalance = totalNewBalance;
	}

	public boolean isBooRenderJvAccNoForUpdate() {
		return booRenderJvAccNoForUpdate;
	}

	public void setBooRenderJvAccNoForUpdate(boolean booRenderJvAccNoForUpdate) {
		this.booRenderJvAccNoForUpdate = booRenderJvAccNoForUpdate;
	}




	public static Logger getLog() {
		return log;
	}


	public ArrayList<KIOSKDenominationDataTable> getLstDenominationDataTable() {
		return lstDenominationDataTable;
	}

	public void setLstDenominationDataTable(
			ArrayList<KIOSKDenominationDataTable> lstDenominationDataTable) {
		this.lstDenominationDataTable = lstDenominationDataTable;
	}

	public ArrayList<KIOSKDenominationDataTable> getLstDenominationForDailog() {
		return lstDenominationForDailog;
	}

	public void setLstDenominationForDailog(
			ArrayList<KIOSKDenominationDataTable> lstDenominationForDailog) {
		this.lstDenominationForDailog = lstDenominationForDailog;
	}

	public Boolean getBooRead() {
		return booRead;
	}

	public void setBooRead(Boolean booRead) {
		this.booRead = booRead;
	}

	public boolean isRenderJVDetailsDataTable() {
		return renderJVDetailsDataTable;
	}

	public void setRenderJVDetailsDataTable(boolean renderJVDetailsDataTable) {
		this.renderJVDetailsDataTable = renderJVDetailsDataTable;
	}

	@Autowired
	IGeneralService<T> igeneralService;

	@Autowired
	IJVProcessService<T> iJVProcessService;

	@Autowired
	IFundEstimationService<T> fundEstimationService;

	@Autowired
	ForeignLocalCurrencyDenominationService<T> foreignLocalCurrencyDenominationService;

	@Autowired
	ISpecialCustomerDealRequestService<T> iSpecialCustomerDealRequestService;

	@Autowired
	IStockAdjustmentService iStockAdjustmentService;

	public void onDateSelect(SelectEvent event) {

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String dealDate=format.format(event.getObject());

		/*try {
				setHdCalValueDate(format.parse(dealDate));
				setMinDealDate(format.parse(dealDate));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 */
	}

	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void jvProcessNavigation()
	{
		clear();
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "jvprocess.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../jvprocess/jvprocess.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}

		getCompanyListFromDB();
		getFinaceYearFromDB();
		getDocumentSerialIdNumberFromDB();
		loadBankList();
		loadCurrenyMaster();
		getDocumentIdfromDb();
		populateJvDescription();
	}

	private void  clear()
	{
		setJvReason(null);
		setJvDescription(null);
		setTotalDebits(BigDecimal.ZERO);
		setTotalCredit(BigDecimal.ZERO);
		setNetAmount(BigDecimal.ZERO);
		setSubDescription(null);
		setJvBankId(null);
		setJvBankLst(null);
		setJvAccNumber(null);
		setJvCurrencyId(null);
		setJvAccNoForUpdate(null);
		setJvPerticulars(null);
		setJvGlAcNo(null);
		setSubCode(null);
		setForeignAmount(null);
		setExchangeRate(null);
		setKwdAmount(null);
		setRenderJVDetailsDataTable(false);
		setRenderDocumentSelectMenu(false);
		setRenderDocumentInput(true);
		setRenderDenominationDataTable(false);
		lstJVProcessDataTableBean1.clear();
		setRenderSavePanel(false);
		setHdCalValueDate(new Date());
		setEffectiveMinDate(new Date());
		clearGlAcNo();
		setJvReasonList(null);
		setSubCodeReadOnly(false);
		setDaybookHeaderId(null);
		setDayBookDetailsId(null);
		setDayBooDtlLineNo(null);
	}

	private void  jvDetailsClear()
	{

		setSubDescription(null);
		setJvBankId(null);
		setJvAccNumber(null);
		setJvCurrencyId(null);
		setJvAccNoForUpdate(null);
		setJvPerticulars(null);
		setJvGlAcNo(null);
		setSubCode(null);
		setForeignAmount(null);
		setExchangeRate(null);
		setKwdAmount(null);
		setLstjvAccountNumber(null);
		loadCurrenyMaster();
		clearGlAcNo();

	}

	private void  jvCurrencyClear()
	{
		setJvPerticulars(null);
		setJvGlAcNo(null);
		setSubCode(null);
		setForeignAmount(null);
		setExchangeRate(null);
		setKwdAmount(null);
		clearGlAcNo();

	}

	public String getCompanyListFromDB() {

		List<CompanyMasterDesc> data=igeneralService.getCompanyList(sessionStateManage.getCompanyId(),new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"));
		setCompanyId(data.get(0).getFsCompanyMaster().getCompanyId());
		setCompanyName(data.get(0).getCompanyName());
		return data.get(0).getCompanyName();
	}

	public void getFinaceYearFromDB() {
		try{
			List<UserFinancialYear> financialYearList = igeneralService.getDealYear(new Date());
			log.info("financialYearList :"+financialYearList.size());
			BigDecimal localFinaceYear = null;
			if(financialYearList!=null && financialYearList.size()>0)
				localFinaceYear = financialYearList.get(0).getFinancialYear();
			BigDecimal financialYearId=financialYearList.get(0).getFinancialYearID();
			setFinancialYearId(financialYearId);
			setFinaceYear(localFinaceYear);
		}catch(Exception e){
			e.printStackTrace();	
		}

	}

	public BigDecimal getDocumentSerialIdNumberFromDB() {
		String documentSerialIdNumber =igeneralService.getNextDocumentReferenceNumber(sessionStateManage.getCountryId().intValue() ,sessionStateManage.getCompanyId().intValue(), Integer.parseInt(Constants.DOCUMENT_CODE_FOR_JVPROCESS), finaceYear.intValue(),processIn,sessionStateManage.getCountryBranchCode());
		setDocumentSerialIdNumber(new BigDecimal(documentSerialIdNumber));
		return new BigDecimal(documentSerialIdNumber);
	}

	public void loadBankList()
	{
		List<BankMasterDTO>  banksList= igeneralService.getCoresBankListForApplicationCountry(sessionStateManage.getCountryId());
		setJvBankLst(banksList);
	}
	public void jvbankSelection()
	{
		setJvCurrencyId(null);
		setJvAccNumber(null);
		if(getJvBankId()!=null)
		{
			List <CurrencyMasterDTO> lstCurrencyMasterDTO=iJVProcessService.getCurrencyOfBank(getJvBankId());
			setCurrencyListFromDB(lstCurrencyMasterDTO);
			jvCurrencyClear();
		}else
		{
			loadCurrenyMaster();
		}


	}
	public void  populateAccountNumber()
	{
		setJvAccNumber(null);
		if(getJvBankId()!=null)
		{
			List<BankAccountDetails> ptabledata = fundEstimationService.getAccountNumber(getJvBankId(), getJvCurrencyId());
			setLstjvAccountNumber(ptabledata);
			setRenderDenominationDataTable(false);
			setRenderJVDetailsDataTable(true);
			setRenderSavePanel(false);
		}else
		{
			setRenderJVDetailsDataTable(false);
			setRenderDenominationDataTable(true);
			displayDenomination();
			setTotalNoofNotes(null);
			setTotalNewBalance(null);
			setRenderSavePanel(false);
		}
		jvCurrencyClear();

	}

	private void loadCurrenyMaster()
	{
		List <CurrencyMasterDTO> lstCurrencyMasterDTO=iJVProcessService.getCurrencyMaster();
		setCurrencyListFromDB(lstCurrencyMasterDTO);
	}
	List<JVProcessDataTableBean> lstJVProcessDataTableBean1 = new ArrayList <JVProcessDataTableBean>();


	public void addRecordsToDataTable()
	{

		if(getJvBankId()!=null)
		{
			if(getJvAccNumber()==null)
			{
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("accounNoManadatory.show();"); 
				return;
			}

		}


		/*HashMap<String, String> rtnValues=jvGlAcNoValidation();

		String pErrorMesg=rtnValues.get("pErrorMesg");
		if(pErrorMesg!=null && !pErrorMesg.equalsIgnoreCase(""))
		{
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("invalidGlNumber.show();"); 
			return;
		}*/
		addRecords();

		setLstJVProcessDataTableBean(lstJVProcessDataTableBean1);
		if(lstJVProcessDataTableBean1.size()>0)
		{
			clatotalAmt();
			setRenderJVDetailsDataTable(true);
			setRenderSavePanel(true);
		}
		jvDetailsClear();
	}

	private void addRecords()
	{
		JVProcessDataTableBean jVProcessDataTableBean= new JVProcessDataTableBean();

		jVProcessDataTableBean.setSubDescription(getSubDescription());
		jVProcessDataTableBean.setBankId(getJvBankId());
		jVProcessDataTableBean.setCurrencyId(getJvCurrencyId());
		jVProcessDataTableBean.setAccountNumber(getJvAccNumber());
		jVProcessDataTableBean.setParticularsDesc(getJvPerticulars());

		StringBuilder sb = new StringBuilder();
		sb.append(getJvGlAcNoOne()==null ? "00":getJvGlAcNoOne());
		sb.append(getJvGlAcNoTwo()==null ? "00":getJvGlAcNoTwo());
		sb.append(getJvGlAcNoThree()==null ? "00":getJvGlAcNoThree());
		sb.append(getJvGlAcNoFour()==null ? "00":getJvGlAcNoFour());
		sb.append(getJvGlAcNoFive()==null ? "00":getJvGlAcNoFive());
		sb.append(getJvGlAcNoSix()==null ? "00":getJvGlAcNoSix());
		sb.append(getJvGlAcNoSeven()==null ? "00":getJvGlAcNoSeven());
		
		jVProcessDataTableBean.setJvGlAcNoOne(getJvGlAcNoOne()==null ? "00":getJvGlAcNoOne());
		jVProcessDataTableBean.setJvGlAcNoTwo(getJvGlAcNoTwo()==null ? "00":getJvGlAcNoTwo());
		jVProcessDataTableBean.setJvGlAcNoThree(getJvGlAcNoThree()==null ? "00":getJvGlAcNoThree());
		jVProcessDataTableBean.setJvGlAcNoFour(getJvGlAcNoFour()==null ? "00":getJvGlAcNoFour());
		jVProcessDataTableBean.setJvGlAcNoFive(getJvGlAcNoFive()==null ? "00":getJvGlAcNoFive());
		jVProcessDataTableBean.setJvGlAcNoSix(getJvGlAcNoSix()==null ? "00":getJvGlAcNoSix());
		jVProcessDataTableBean.setJvGlAcNoSeven(getJvGlAcNoSeven()==null ? "00":getJvGlAcNoSeven());


		jVProcessDataTableBean.setGlAccountNo(sb.toString());
		jVProcessDataTableBean.setSubCode(getSubCode());
		jVProcessDataTableBean.setForeignAmt(getForeignAmount());
		jVProcessDataTableBean.setExchangeRate(getExchangeRate());
		jVProcessDataTableBean.setKwdAmount(getKwdAmount());
		jVProcessDataTableBean.setCurrencyNormal(true);
		jVProcessDataTableBean.setCurrencyWithDenomination(false);
		if(getJvBankId()!=null)
		{
			jVProcessDataTableBean.setBankCode(igeneralService.getBankCode(getJvBankId()));
			jVProcessDataTableBean.setBankName(igeneralService.getBankName( getJvBankId()));
		}
		if(getJvCurrencyId()!=null)
		{
			jVProcessDataTableBean.setCurrencyCode(igeneralService.getCurrencyCode(getJvCurrencyId()));
			jVProcessDataTableBean.setCurrencyName(iSpecialCustomerDealRequestService.getCurrencyForUpdate(getJvCurrencyId()));
		}
		jVProcessDataTableBean.setDayBookDetailsID(getDayBookDetailsId());
		jVProcessDataTableBean.setLineNo(getDayBooDtlLineNo());
		
		lstJVProcessDataTableBean1.add(jVProcessDataTableBean);
		setDayBookDetailsId(null);
		setDayBooDtlLineNo(null);
	}

	public void calKwdAmt()
	{

		BigDecimal foreignAmt=getForeignAmount();
		BigDecimal exchgeRate=getExchangeRate();

		if(foreignAmt!=null && exchgeRate!=null && getJvCurrencyId()!=null)
		{
			BigDecimal localKwdAmt=foreignAmt.multiply(exchgeRate);
			setKwdAmount(round(localKwdAmt,foreignLocalCurrencyDenominationService.getDecimalPerCurrency(getJvCurrencyId())));
		}
	}

	public void calulateLocalAmt()
	{
		BigDecimal foreignAmt=getForeignAmount();
		BigDecimal exchgeRate=getExchangeRate();

		if(foreignAmt!=null && exchgeRate!=null && getJvCurrencyId()!=null)
		{
			BigDecimal localKwdAmt=foreignAmt.multiply(exchgeRate);
			setKwdAmount(round(localKwdAmt,foreignLocalCurrencyDenominationService.getDecimalPerCurrency(getJvCurrencyId())));
		}
	}


	public static BigDecimal round(BigDecimal bd, int places) {
		if (places < 0) throw new IllegalArgumentException();

		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd;
	}

	public void clatotalAmt()
	{
		BigDecimal localForeignAmt=getKwdAmount();

		if(localForeignAmt.signum()==-1)
		{
			BigDecimal localTotCredits= getTotalCredit();

			BigDecimal absValue=localForeignAmt.abs();
			BigDecimal tempTot=localTotCredits.add(absValue);
			setTotalCredit(tempTot);


		}else
		{
			BigDecimal localTotDebits=getTotalDebits();
			BigDecimal tempTot=localTotDebits.add(localForeignAmt);
			setTotalDebits(tempTot);
		}

		BigDecimal totNet=getTotalDebits().subtract(getTotalCredit());
		setNetAmount(totNet);
	}



	public void editableRefereneceNo(){
		clear();

	}

	public void editableDocumentNo()
	{
		setRenderDocumentSelectMenu(true);
		setRenderDocumentInput(false);
		populateUnapprovalList();
	}

	public void addDenaminationDetails(){

		if(getKwdAmount()!=null && getTotalNewBalance()!=null)
		{
			if(getKwdAmount().abs().compareTo(getTotalNewBalance())!=0)
			{
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("balanceNotMatch.show();"); 
				return;
			}

		}else
		{
			if(getTotalNewBalance()==null)
			{
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("noDenomination.show();");
				return;
			}
		}

		ArrayList<KIOSKDenominationDataTable> lstDenominationDataTable=getLstDenominationDataTable();

		ArrayList<KIOSKDenominationDataTable> lstDenominationTemp= new ArrayList<KIOSKDenominationDataTable>();

		for(KIOSKDenominationDataTable kIOSKDenominationDataTable: lstDenominationDataTable)
		{
			if(kIOSKDenominationDataTable.getNoOfNotes()!=null)
			{
				lstDenominationTemp.add(kIOSKDenominationDataTable);

			}
		}

		JVProcessDataTableBean jVProcessDataTableBean= new JVProcessDataTableBean();

		jVProcessDataTableBean.setSubDescription(getSubDescription());
		jVProcessDataTableBean.setBankId(getJvBankId());
		jVProcessDataTableBean.setCurrencyId(getJvCurrencyId());
		jVProcessDataTableBean.setAccountNumber(getJvAccNumber());
		jVProcessDataTableBean.setParticularsDesc(getJvPerticulars());

		/*String jvLocalGlAccNo=getJvGlAcNoOne()==null ? "00":getJvGlAcNoOne()+getJvGlAcNoTwo()==null ? "00":getJvGlAcNoTwo()+getJvGlAcNoThree()==null ? "00":getJvGlAcNoThree();
		String jvLocalGlAccNo1=jvLocalGlAccNo+getJvGlAcNoFour()==null ? "00":getJvGlAcNoFour()+getJvGlAcNoFive()==null ? "00":getJvGlAcNoFive()+getJvGlAcNoSix()==null ? "00":getJvGlAcNoSix();
		String jvLocalGlAccNo2=jvLocalGlAccNo1+getJvGlAcNoSeven()==null ? "00":getJvGlAcNoSeven();
		 */

		StringBuilder sb = new StringBuilder();
		sb.append(getJvGlAcNoOne()==null ? "00":getJvGlAcNoOne());
		sb.append(getJvGlAcNoTwo()==null ? "00":getJvGlAcNoTwo());
		sb.append(getJvGlAcNoThree()==null ? "00":getJvGlAcNoThree());
		sb.append(getJvGlAcNoFour()==null ? "00":getJvGlAcNoFour());
		sb.append(getJvGlAcNoFive()==null ? "00":getJvGlAcNoFive());
		sb.append(getJvGlAcNoSix()==null ? "00":getJvGlAcNoSix());
		sb.append(getJvGlAcNoSeven()==null ? "00":getJvGlAcNoSeven());

		jVProcessDataTableBean.setJvGlAcNoOne(getJvGlAcNoOne()==null ? "00":getJvGlAcNoOne());
		jVProcessDataTableBean.setJvGlAcNoTwo(getJvGlAcNoTwo()==null ? "00":getJvGlAcNoTwo());
		jVProcessDataTableBean.setJvGlAcNoThree(getJvGlAcNoThree()==null ? "00":getJvGlAcNoThree());
		jVProcessDataTableBean.setJvGlAcNoFour(getJvGlAcNoFour()==null ? "00":getJvGlAcNoFour());
		jVProcessDataTableBean.setJvGlAcNoFive(getJvGlAcNoFive()==null ? "00":getJvGlAcNoFive());
		jVProcessDataTableBean.setJvGlAcNoSix(getJvGlAcNoSix()==null ? "00":getJvGlAcNoSix());
		jVProcessDataTableBean.setJvGlAcNoSeven(getJvGlAcNoSeven()==null ? "00":getJvGlAcNoSeven());

		
		jVProcessDataTableBean.setGlAccountNo(sb.toString());
		jVProcessDataTableBean.setSubCode(getSubCode());
		jVProcessDataTableBean.setForeignAmt(getForeignAmount());
		jVProcessDataTableBean.setExchangeRate(getExchangeRate());
		jVProcessDataTableBean.setKwdAmount(getKwdAmount());
		jVProcessDataTableBean.setCurrencyNormal(false);
		jVProcessDataTableBean.setCurrencyWithDenomination(true);
		jVProcessDataTableBean.setLstDenominationDataTable(lstDenominationTemp);
		if(getJvBankId()!=null)
		{
			jVProcessDataTableBean.setBankCode(igeneralService.getBankCode(getJvBankId()));
			jVProcessDataTableBean.setBankName(igeneralService.getBankName( getJvBankId()));
		}
		if(getJvCurrencyId()!=null)
		{
			jVProcessDataTableBean.setCurrencyCode(igeneralService.getCurrencyCode(getJvCurrencyId()));
			jVProcessDataTableBean.setCurrencyName(iSpecialCustomerDealRequestService.getCurrencyForUpdate(getJvCurrencyId()));
		}

		jVProcessDataTableBean.setDayBookDetailsID(getDayBookDetailsId());
		jVProcessDataTableBean.setLineNo(getDayBooDtlLineNo());
		
		lstJVProcessDataTableBean1.add(jVProcessDataTableBean);

		setDayBookDetailsId(null);
		setDayBooDtlLineNo(null);
		setRenderDenominationDataTable(false);

		setLstJVProcessDataTableBean(lstJVProcessDataTableBean1);
		if(lstJVProcessDataTableBean1.size()>0)
		{
			clatotalAmt();
			setRenderJVDetailsDataTable(true);
			setRenderSavePanel(true);
		}
		jvDetailsClear();
	}

	public void onCellEdit(KIOSKDenominationDataTable kioskDtObj){

		BigDecimal newBalance = null;
		BigDecimal noOfNotes = null;

		if(kioskDtObj.getNoOfNotes()!=null){
			newBalance=kioskDtObj.getNoOfNotes().multiply(kioskDtObj.getDenominationNo());
			kioskDtObj.setCashAmount(newBalance);
		}else{
			kioskDtObj.setNoOfNotes(null);
			kioskDtObj.setCashAmount(null);
		}

		setTotalNoofNotes(null);
		setTotalNewBalance(null);
		newBalance =null;
		noOfNotes = null;
		for(KIOSKDenominationDataTable kioskDtObj1:getLstDenominationDataTable()){	
			if(kioskDtObj1.getNoOfNotes()!=null ){
				noOfNotes= (noOfNotes==null?BigDecimal.ZERO:noOfNotes).add(kioskDtObj1.getNoOfNotes());
			}
			if(kioskDtObj1.getCashAmount()!=null ){
				newBalance= (newBalance==null?BigDecimal.ZERO:newBalance).add(kioskDtObj1.getCashAmount());
			}
		}
		setTotalNoofNotes(noOfNotes);
		setTotalNewBalance(newBalance);
	}

	private void displayDenomination()
	{
		int currenyDecimal=foreignLocalCurrencyDenominationService.getDecimalPerCurrency(getJvCurrencyId());

		ArrayList<KIOSKDenominationDataTable> localLstData =new  ArrayList<KIOSKDenominationDataTable>();

		//List<Stock> dataFromDb = foreignLocalCurrencyDenominationService.getLocalCurrencyDenominationFromStock(sessionStateManage.getCountryId(),sessionStateManage.getUserName(),"56",sessionStateManage.getCompanyId(),getJvCurrencyId().toPlainString());
		List<Object[]> lstObject=iJVProcessService.getCurrencyDenomination(new BigDecimal(sessionStateManage.getCurrencyId()), sessionStateManage.getUserName(),getHdCalValueDate());


		int i = 0;

		for(Object [] obj:lstObject)
		{
			KIOSKDenominationDataTable kIOSKDenominationDataTable= new KIOSKDenominationDataTable();

			kIOSKDenominationDataTable.setStock(new BigDecimal(obj[4]==null ? "00":obj[4].toString()));
			kIOSKDenominationDataTable.setDenominationDesc(obj[2]==null ? "":obj[2].toString());
			kIOSKDenominationDataTable.setDenominationNo(round(new BigDecimal(obj[3]==null ? "00":obj[3].toString()),currenyDecimal));
			kIOSKDenominationDataTable.setSerial(++i);
			kIOSKDenominationDataTable.setDenominationId(new BigDecimal(obj[1]==null ? "00":obj[1].toString()));
			localLstData.add(kIOSKDenominationDataTable);
		}

		/*for (Stock element : dataFromDb) {
			int stock = element.getOpenQuantity() + element.getPurchaseQuantity()	+ element.getReceivedQuantity() - (element.getSaleQuantity() + element.getTransactionQuantity());

			KIOSKDenominationDataTable kIOSKDenominationDataTable= new KIOSKDenominationDataTable();

			kIOSKDenominationDataTable.setStock(new BigDecimal(stock));

			kIOSKDenominationDataTable.setDenominationDesc(element.getDenominationId().getDenominationDesc());

			kIOSKDenominationDataTable.setDenominationNo(round(element.getDenominationId().getDenominationAmount(),currenyDecimal));

			kIOSKDenominationDataTable.setSerial(++i);

			kIOSKDenominationDataTable.setDenominationId(element.getDenominationId().getDenominationId());

			localLstData.add(kIOSKDenominationDataTable);


		}*/
		setLstDenominationDataTable(localLstData);

	}

	public void checkStatusType(JVProcessDataTableBean jVProcessDataTableBean)
	{

		ArrayList<KIOSKDenominationDataTable> localLstData =jVProcessDataTableBean.getLstDenominationDataTable();

		setLstDenominationForDailog(localLstData);

		RequestContext.getCurrentInstance().execute("locaDena.show();");

	}

	public List<String> autoCompletejvGlAcNoOne(String inputValue){

		return iJVProcessService.getGlACNOAutoComplete("One", inputValue);
	}
	public List<String> autoCompletejvGlAcNoTwo(String inputValue){
		return iJVProcessService.getGlACNOAutoComplete("Two", inputValue);
	}
	public List<String> autoCompletejvGlAcNoThree(String inputValue){
		return iJVProcessService.getGlACNOAutoComplete("Three", inputValue);
	}
	public List<String> autoCompletejvGlAcNoFour(String inputValue){
		return iJVProcessService.getGlACNOAutoComplete("Four", inputValue);
	}

	public List<String> autoCompletejvGlAcNoFive(String inputValue){
		return iJVProcessService.getGlACNOAutoComplete("Five", inputValue);
	}

	public List<String> autoCompletejvGlAcNoSix(String inputValue){
		return iJVProcessService.getGlACNOAutoComplete("Six", inputValue);
	}

	public List<String> autoCompletejvGlAcNoSeven(String inputValue){
		return iJVProcessService.getGlACNOAutoComplete("Seven", inputValue);
	}

	public void cancelDenaminationDetails()
	{

		setRenderDenominationDataTable(false);
		setRenderJVDetailsDataTable(true);
		jvDetailsClear();
		if(lstJVProcessDataTableBean1.size()>0)
		{
			setRenderSavePanel(true);
		}else
		{
			setRenderSavePanel(false);
		}
		if(getDayBookDetailsId()!=null)
		{
			iJVProcessService.deleteDayBookDetails(getDayBookDetailsId());
		}
	}

	public HashMap<String, String> jvGlAcNoValidation()
	{
		String jvGlAccNo=getJvGlAcNoOne()==null ? "00":getJvGlAcNoOne()+getJvGlAcNoTwo()==null ? "00":getJvGlAcNoTwo()+getJvGlAcNoThree()==null ? "00":getJvGlAcNoThree();
		jvGlAccNo=jvGlAccNo+getJvGlAcNoFour()==null ? "00":getJvGlAcNoFour()+getJvGlAcNoFive()==null ? "00":getJvGlAcNoFive()+getJvGlAcNoSix()==null ? "00":getJvGlAcNoSix();
		jvGlAccNo=jvGlAccNo+getJvGlAcNoSeven()==null ? "00":getJvGlAcNoSeven();
		HashMap<String, String> rtnValues=new HashMap<String, String>();
		HashMap<String, String> inputValues = new HashMap<String, String>();
		inputValues.put("PApplCntyId",sessionStateManage.getCountryId().toPlainString());
		inputValues.put("PComcod",getJvGlAcNoOne()==null ? "00":getJvGlAcNoOne());
		inputValues.put("PAl1cod",getJvGlAcNoTwo()==null ? "00":getJvGlAcNoTwo());
		inputValues.put("PAl2cod",getJvGlAcNoThree()==null ? "00":getJvGlAcNoThree());
		inputValues.put("PAl3cod",getJvGlAcNoFour()==null ? "00":getJvGlAcNoFour());
		inputValues.put("PAl4cod",getJvGlAcNoFive()==null ? "00":getJvGlAcNoFive());
		inputValues.put("PAcntcod",getJvGlAcNoSix()==null ? "00":getJvGlAcNoSix());
		inputValues.put("PAclscod",getJvGlAcNoSeven()==null ? "00":getJvGlAcNoSeven());
		inputValues.put("PBankid",getJvBankId()==null ? "00" : getJvBankId().toPlainString());
		inputValues.put("PCurrencyid",getJvCurrencyId().toPlainString());
		inputValues.put("PBankAccountNo",getJvAccNumber()==null ? "00": getJvAccNumber());
		Date jvDate=getHdCalValueDate();
		String strDate=new SimpleDateFormat("dd/MM/yyyy").format(jvDate);
		inputValues.put("p_jv_date",strDate);
		inputValues.put("PBankCashId",getJvBankId()==null ? "C":"B");

		try {
			rtnValues=iJVProcessService.glAccNoValidation(inputValues);
			System.out.println(""+rtnValues.toString());

			String pGldes=rtnValues.get("pGldes");
			String pSlno =rtnValues.get("pSlno");
			String pSlid=rtnValues.get("pSlid");
			String pSubcre=rtnValues.get("pSubcre");
			String pErrorMesg=rtnValues.get("pErrorMesg");
			if(pErrorMesg!=null && !pErrorMesg.equalsIgnoreCase(""))
			{
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("invalidGlNumber.show();"); 

			}
			if(pSlid!=null && pSlid.equalsIgnoreCase("0"))
			{
				setSubCodeReadOnly(true);
				setSubDescription(pGldes);
				setJvGlAcNo(pSlno);
			}else
			{
				setSubCodeReadOnly(false);
				setSubDescription(null);
			}

		} catch (AMGException e) {

		}
		return rtnValues;
	}

	public HashMap<String, String> jvSubCodeValidation()
	{
		HashMap<String, String> rtnValues=new HashMap<String, String>();
		HashMap<String, String> inputValues = new HashMap<String, String>();
		inputValues.put("P_APPL_CNTY_ID",sessionStateManage.getCountryId().toPlainString());
		inputValues.put("P_COMCOD",getJvGlAcNoOne()==null ? "00":getJvGlAcNoOne());
		inputValues.put("P_AL1COD",getJvGlAcNoTwo()==null ? "00":getJvGlAcNoTwo());
		inputValues.put("P_AL2COD",getJvGlAcNoThree()==null ? "00":getJvGlAcNoThree());
		inputValues.put("P_AL3COD",getJvGlAcNoFour()==null ? "00":getJvGlAcNoFour());
		inputValues.put("P_AL4COD",getJvGlAcNoFive()==null ? "00":getJvGlAcNoFive());
		inputValues.put("P_ACNTCOD",getJvGlAcNoSix()==null ? "00":getJvGlAcNoSix());
		inputValues.put("P_ACLSCOD",getJvGlAcNoSeven()==null ? "00":getJvGlAcNoSeven());
		inputValues.put("P_SUBCOD",getSubCode());
		Date jvDate=getHdCalValueDate();
		String strDate=new SimpleDateFormat("dd/MM/yyyy").format(jvDate);
		inputValues.put("P_JV_DATE",strDate);
		inputValues.put("P_CURRENCY_CODE",igeneralService.getCurrencyCode(getJvCurrencyId()));

		try {
			rtnValues=iJVProcessService.jvSubCodeValidation(inputValues);
			System.out.println(""+rtnValues.toString());
		} catch (AMGException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rtnValues;
	}
	public String getDocumentSerialIDForSupplier(String processIn){

		String documentSerialID = igeneralService.getNextDocumentReferenceNumber(sessionStateManage.getCountryId().intValue() ,sessionStateManage.getCompanyId().intValue(), Integer.parseInt(Constants.DOCUMENT_CODE_FOR_JVPROCESS), finaceYear.intValue(),processIn,sessionStateManage.getCountryBranchCode());
		return documentSerialID;
	}

	public void save()
	{

		if(getNetAmount()!=null && getNetAmount().compareTo(BigDecimal.ZERO)!=0)
		{
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("netAmountNotMatch.show();"); 
			return;
		}
		BigDecimal saveDocumentSerialIDForJVProcess=null;
		if(getDaybookHeaderId()!=null)
		{
			saveDocumentSerialIDForJVProcess=getJvDocumentNumber();
		}else
		{
			saveDocumentSerialIDForJVProcess= new BigDecimal(getDocumentSerialIDForSupplier(Constants.U));
		}
		
		DayBookHeader dayBookHeader=saveDayBookHeader(saveDocumentSerialIDForJVProcess);
		List<DayBookDetailsWithDenomination> lstDayBookDetailsWithDenomination=saveDayBookDetails(dayBookHeader,saveDocumentSerialIDForJVProcess);
		HashMap<String, Object> saveMap=new HashMap<String, Object>();

		saveMap.put("DayBookHeader", dayBookHeader);
		saveMap.put("DayBookDetailsWithDenomination", lstDayBookDetailsWithDenomination);

		
		try {
			iJVProcessService.saveJVProcess(saveMap);
			setDaybookHeaderId(null);
			setDayBookDetailsId(null);
			setJvDocumentNumber(null);
			setDayBooDtlLineNo(null);
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("complete.show();"); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void getDocumentIdfromDb(){
		List<Document> listDocument = igeneralService.getDocument(new BigDecimal(Constants.DOCUMENT_CODE_FOR_JVPROCESS), sessionStateManage.getLanguageId());
		if(listDocument.size()>0){
			setDocumentId(listDocument.get(0).getDocumentID());
		}
	}

	private DayBookHeader saveDayBookHeader(BigDecimal saveDocumentSerialIDForJVProcess)
	{
		DayBookHeader dayBookHeader=null;
		try {

			//setDocSerialIdNumberForSave(saveDocumentSerialID);


			dayBookHeader = new DayBookHeader();

			dayBookHeader.setDaybookHeaderId(getDaybookHeaderId());
			// save Company Master
			CompanyMaster companyMaster = new CompanyMaster();
			companyMaster.setCompanyId(getCompanyId());
			dayBookHeader.setDayBookCompanyMaster(companyMaster);

			dayBookHeader.setCurrencyId(getJvCurrencyId());

			CountryMaster daybookCountry = new CountryMaster();
			daybookCountry.setCountryId(sessionStateManage.getCountryId());
			dayBookHeader.setDayBookCountryMaster(daybookCountry);

			Document document = new Document();
			document.setDocumentID(getDocumentId());
			dayBookHeader.setDoucDocumentId(document);

			dayBookHeader.setDocumentNumber(saveDocumentSerialIDForJVProcess);

			dayBookHeader.setDocumentFinancialYear(getFinaceYear());

			dayBookHeader.setDocumentDate(getHdCalValueDate());
			dayBookHeader.setAcyymm(new SimpleDateFormat("dd/MM/yyyy").parse(getCurrentDateWithFormat()));
			dayBookHeader.setReason(getJvReason());

			//dayBookHeader.setDealedWithCustomer(getFxDlwithSupplierDealWith());
			//dayBookHeader.setContact(getFxDlwithSupplierConttract());


			//dayBookHeader.setConcludedBy(getFxDlSupplierConcludedby());
			//dayBookHeader.setReuterReference(getFxDlSupplierReutersRef());
			dayBookHeader.setRemarks(getJvDescription());


			if (getDaybookHeaderId() != null
					&& getDaybookHeaderId().intValue() != 0) {

				dayBookHeader.setModifiedBy(sessionStateManage.getUserName());
				dayBookHeader.setModifiedDate(new Date());
			} else {
				dayBookHeader.setCreatedBy(sessionStateManage.getUserName());
				dayBookHeader.setCreatedDate(new Date());

			}
			//CR 13-12-2014
			//dayBookHeader.setDealedWithCustomer(getCustomerReference()==null ?  "":  getCustomerReference().toPlainString());

			//dayBookHeader.setFaAccountNo(getFxDlSalesAccNo()==null ? "":getFxDlSalesAccNo().toPlainString());


			//dayBookHeader.setFcAmount(getForeignAmount());
			//dayBookHeader.setExchangeRate(getExchangeRate());
			dayBookHeader.setLocalAmount(getTotalDebits());
			dayBookHeader.setBranchNumber(new BigDecimal(sessionStateManage.getBranchId()));
			dayBookHeader.setIsActive(Constants.U);
			//CR Hard Coded  
			//dayBookHeader.setSubledgerInd("1");
			//dayBookHeader.setOpenItemId("1");
			//dayBookHeader.setOpenItemRef(sessionStateManage.getCompanyId()+""+new BigDecimal(Constants.DOCUMENT_CODE_FOR_FX_DEAL_WITH_BANK)+""+treasuryDealHeader.getUserFinanceYear()+""+treasuryDealHeader.getTreasuryDocumentNumber());

			//ref numbers storing
			//dayBookHeader.setRefCompanyId(sessionStateManage.getCompanyId());
			//CR Changed to Set orginal ID 
			//dayBookHeader.setRefDocumentId(treasuryDealHeader.getExDocument().getDocumentID());
			//dayBookHeader.setRefFinYear(treasuryDealHeader.getUserFinanceYear());
			//dayBookHeader.setRefNumber(treasuryDealHeader.getTreasuryDocumentNumber());


			//fxDealwithSupplierService.saveOrUpdateDayBook(dayBookHeader);
		} catch (Exception e) {
			e.printStackTrace();
		}



		return dayBookHeader;
	}

	private List<DayBookDetailsWithDenomination> saveDayBookDetails(DayBookHeader dayBookHeader ,BigDecimal saveDocumentSerialID)
	{
		List<DayBookDetailsWithDenomination> lstDayBookDetailsWithDenomination = new ArrayList<DayBookDetailsWithDenomination>();


		BigDecimal lineNumber=BigDecimal.ONE;
		try {
			for(JVProcessDataTableBean jVProcessDataTableBean :getLstJVProcessDataTableBean())
			{
				DayBookDetails dayBookSdDetails = new DayBookDetails();
				
				dayBookSdDetails.setDayBookDetailsId(jVProcessDataTableBean.getDayBookDetailsID());
				dayBookSdDetails.setDayBookAccyymm(new SimpleDateFormat("dd/MM/yyyy").parse(getCurrentDateWithFormat()));
				dayBookSdDetails.setDayBookHeaderId(dayBookHeader);

				if(jVProcessDataTableBean.getAccountNumber()!=null)
				{
					BankMaster bankMaster = new BankMaster();
					bankMaster.setBankId(jVProcessDataTableBean.getBankId());
					dayBookSdDetails.setDayBookDetailsBankMaster(bankMaster);
				}


				if(jVProcessDataTableBean.getAccountNumber()!=null)
				{
					BankAccountDetails bankAccountDetails = new BankAccountDetails();
					BigDecimal bankaccountDeatailsId= iJVProcessService.getBanlAccDtlsIDBasedOnAccountNo(jVProcessDataTableBean.getAccountNumber());
					if(bankaccountDeatailsId!=null){
						bankAccountDetails.setBankAcctDetId(bankaccountDeatailsId);
					}
					dayBookSdDetails.setDayBookDetailsBankAccountDetails(bankAccountDetails);

				}

				// save Company Master
				CompanyMaster dayBkDlsSdcompanyMaster = new CompanyMaster();
				dayBkDlsSdcompanyMaster.setCompanyId(getCompanyId());
				dayBookSdDetails.setDayBookCompanyMaster(dayBkDlsSdcompanyMaster);

				CountryMaster daybookDlsSdCountry = new CountryMaster();
				daybookDlsSdCountry.setCountryId(sessionStateManage.getCountryId());
				dayBookSdDetails.setDayBookCountryMaster(daybookDlsSdCountry);

				CurrencyMaster dayBookDlsSdCurrencyMaster= new CurrencyMaster();
				dayBookDlsSdCurrencyMaster.setCurrencyId(jVProcessDataTableBean.getCurrencyId());
				dayBookSdDetails.setDayBookCurrencyId(dayBookDlsSdCurrencyMaster);


				dayBookSdDetails.setValueDate(getHdCalValueDate());


				Document dayBkDlsSddocument = new Document();
				dayBkDlsSddocument.setDocumentID(getDocumentId());
				dayBookSdDetails.setDayBookDocumentId(dayBkDlsSddocument);


				dayBookSdDetails.setDayBookdocumentDate(getHdCalValueDate());
				dayBookSdDetails.setDayBookDocumentNumber(saveDocumentSerialID);

				dayBookSdDetails.setDayBookExchangeRate(jVProcessDataTableBean.getExchangeRate());

				dayBookSdDetails.setDayBookFaAccountNumber(jVProcessDataTableBean.getGlAccountNo());

				dayBookSdDetails.setDayBookFcAmount(jVProcessDataTableBean.getForeignAmt());//

				dayBookSdDetails.setDayBookFinanceYear(getFinaceYear());

				dayBookSdDetails.setDayBookLocalAmount(jVProcessDataTableBean.getKwdAmount());//

				if(jVProcessDataTableBean.getDayBookDetailsID()!=null)
				{
					dayBookSdDetails.setDayBookLineNumber(jVProcessDataTableBean.getLineNo());//
				}else
				{
					dayBookSdDetails.setDayBookLineNumber(lineNumber);//
				}
				
				dayBookSdDetails.setDayBookFaAccountNumber(jVProcessDataTableBean.getGlAccountNo());
				//dayBookSdDetails.setDayBookLineType(lineType);//

				if (getDayBookDetailsId() != null
						&& getDayBookDetailsId().intValue() != 0) {

					dayBookSdDetails.setModifiedBy(sessionStateManage.getUserName());
					dayBookSdDetails.setModifiedDate(new Date());
				} else {
					dayBookSdDetails.setCreatedBy(sessionStateManage.getUserName());
					dayBookSdDetails.setCreatedDate(new Date());

				}
				dayBookSdDetails.setParticulars(jVProcessDataTableBean.getParticularsDesc());
				if(isSubCodeReadOnly())
				{
					dayBookSdDetails.setDayBookSubLedgerIndicator(Constants.No);
				}else
				{
					dayBookSdDetails.setDayBookSubLedgerIndicator(Constants.Yes);
				}


				DayBookDetailsWithDenomination dayBookDetailsWithDenomination= new DayBookDetailsWithDenomination();
				dayBookDetailsWithDenomination.setDayBookDetails(dayBookSdDetails);

				ArrayList<KIOSKDenominationDataTable> lstDataForForeignCurrencyAdjust=jVProcessDataTableBean.getLstDenominationDataTable();
				if(lstDataForForeignCurrencyAdjust!=null && lstDataForForeignCurrencyAdjust.size()>0)
				{
					if(jVProcessDataTableBean.getDayBookDetailsID()!=null)
					{
						iJVProcessService.deleteCurrencyAdjust(sessionStateManage.getCompanyId(), sessionStateManage.getCountryId(), new BigDecimal(Constants.DOCUMENT_CODE_FOR_JVPROCESS), getFinaceYear(), getJvDocumentNumber(), jVProcessDataTableBean.getLineNo());
						List <ForeignCurrencyAdjust> lstForeignCurrencyAdjust=saveforeignCurrencyAdjustList(lstDataForForeignCurrencyAdjust,jVProcessDataTableBean.getLineNo(),saveDocumentSerialID);
						dayBookDetailsWithDenomination.setLstForeignCurrencyAdjust(lstForeignCurrencyAdjust);
					}else
					{
						List <ForeignCurrencyAdjust> lstForeignCurrencyAdjust=saveforeignCurrencyAdjustList(lstDataForForeignCurrencyAdjust,lineNumber,saveDocumentSerialID);
						dayBookDetailsWithDenomination.setLstForeignCurrencyAdjust(lstForeignCurrencyAdjust);
					}
					
				}else
				{
					if(getDayBooDtlLineNo()!=null)
					{
						iJVProcessService.deleteCurrencyAdjust(sessionStateManage.getCompanyId(), sessionStateManage.getCountryId(), new BigDecimal(Constants.DOCUMENT_CODE_FOR_JVPROCESS), getFinaceYear(), getJvDocumentNumber(), getDayBooDtlLineNo());
					}
				}
				
				
				lineNumber=lineNumber.add(BigDecimal.ONE);
				lstDayBookDetailsWithDenomination.add(dayBookDetailsWithDenomination);
			}

			//fxDealwithSupplierService.saveOrUpdateDayBook(dayBookSdDetails);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lstDayBookDetailsWithDenomination;
	}

	private List <ForeignCurrencyAdjust> saveforeignCurrencyAdjustList(ArrayList<KIOSKDenominationDataTable> lstDataForForeignCurrencyAdjust,BigDecimal linNumber ,BigDecimal saveDocumentSerialID)
	{
		HashMap<BigDecimal, BigDecimal> denominationMap= getDenominationMap(new BigDecimal(sessionStateManage.getCurrencyId()));
		List <ForeignCurrencyAdjust> foreignCurrencyAdjustList=new ArrayList <ForeignCurrencyAdjust>();
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
		Date acc_Month = null;

		try {
			acc_Month = DATE_FORMAT.parse(getCurrentDateWithFormat());
		} catch (ParseException e) {
			e.printStackTrace();
		}


		for(KIOSKDenominationDataTable kioskDtObj : lstDataForForeignCurrencyAdjust){	
			if(kioskDtObj.getCashAmount()!=null && kioskDtObj.getCashAmount().intValue()>0){
				ForeignCurrencyAdjust foreignCurrencyAdjust=new ForeignCurrencyAdjust();

				CompanyMaster companyMaster = new CompanyMaster();
				companyMaster.setCompanyId(sessionStateManage.getCompanyId());
				foreignCurrencyAdjust.setFsCompanyMaster(companyMaster);

				CountryMaster countryMaster = new CountryMaster();
				countryMaster.setCountryId(sessionStateManage.getCountryId());
				foreignCurrencyAdjust.setFsCountryMaster(countryMaster);

				foreignCurrencyAdjust.setDocumentCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_JVPROCESS));
				foreignCurrencyAdjust.setDocumentFinanceYear(getFinaceYear());
				foreignCurrencyAdjust.setDocumentNo(saveDocumentSerialID);
				foreignCurrencyAdjust.setDocumentDate(getHdCalValueDate());

				CountryBranch countryBranch=new CountryBranch();
				countryBranch.setCountryBranchId(new BigDecimal(sessionStateManage.getBranchId()));
				foreignCurrencyAdjust.setCountryBranch(countryBranch);

				CurrencyMaster currencyMaster = new CurrencyMaster();
				currencyMaster.setCurrencyId(new BigDecimal(sessionStateManage.getCurrencyId()));		
				foreignCurrencyAdjust.setFsCurrencyMaster(currencyMaster);


				BigDecimal balance = denominationMap.get(kioskDtObj.getDenominationId()).multiply(kioskDtObj.getNoOfNotes());

				foreignCurrencyAdjust.setAdjustmentAmount(balance);
				foreignCurrencyAdjust.setNotesQuantity(kioskDtObj.getNoOfNotes());

				CurrencyWiseDenomination denominationMaster = new CurrencyWiseDenomination();
				denominationMaster.setDenominationId(kioskDtObj.getDenominationId());
				foreignCurrencyAdjust.setFsDenominationId(denominationMaster);

				foreignCurrencyAdjust.setProgNumber("JV");

				if((kioskDtObj.getCashAmount()==null?BigDecimal.ZERO:kioskDtObj.getCashAmount()).signum()==-1){
					foreignCurrencyAdjust.setTransactionType(Constants.O);
				}else {
					foreignCurrencyAdjust.setTransactionType(Constants.P);
				}

				foreignCurrencyAdjust.setDocumentStatus(Constants.Yes);
				foreignCurrencyAdjust.setOracleUser(sessionStateManage.getUserName());
				foreignCurrencyAdjust.setStockUpdated(Constants.Yes);
				foreignCurrencyAdjust.setDocumentLineNumber(linNumber);
				foreignCurrencyAdjust.setAccountmmyyyy(acc_Month);

				foreignCurrencyAdjust.setDenaminationAmount(denominationMap.get(kioskDtObj.getDenominationId()));
				foreignCurrencyAdjust.setCreatedDate(new Date());
				foreignCurrencyAdjust.setCreatedBy(sessionStateManage.getSessionValue("userName"));

				foreignCurrencyAdjustList.add(foreignCurrencyAdjust);
			}

		}
		return foreignCurrencyAdjustList;
	}
	public HashMap<BigDecimal, BigDecimal> getDenominationMap(BigDecimal currencyId){
		HashMap<BigDecimal, BigDecimal> denominationMap=new HashMap<BigDecimal, BigDecimal>();
		List<CurrencyWiseDenomination> denominationList=iStockAdjustmentService.currencyWiseDenominations(currencyId);
		for(CurrencyWiseDenomination denomination:denominationList){
			denominationMap.put(denomination.getDenominationId(), denomination.getDenominationAmount());
		}
		return denominationMap;
	}
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

	private void clearGlAcNo()
	{
		StringBuilder sb = new StringBuilder();
		setJvGlAcNoOne(null);
		setJvGlAcNoTwo(null);
		setJvGlAcNoThree(null);
		setJvGlAcNoFour(null);
		setJvGlAcNoFive(null);
		setJvGlAcNoSix(null);
		setJvGlAcNoSeven(null);
	}


	public void populateJvDescription(){
		List<JVReasonDTO> jvReasonList = iJVProcessService.getReasonDetails();
		setJvReasonList(jvReasonList);
	}

	public void fetchRecordfromDb(){
		//clearData();
		clearHeader();
		clearDetails();
		loadBankList();
		loadCurrenyMaster();
		lstJVProcessDataTableBean1.clear();
		populateJvDescription();
		List<DayBookHeader> listDayHeader = iJVProcessService.getDayBookHeaderRecord(getDocumentId(), getJvDocumentNumber());
		if(listDayHeader.size()>0){
			DayBookHeader getHeaderData = listDayHeader.get(0);
			setDaybookHeaderId(getHeaderData.getDaybookHeaderId());
			setHdCalValueDate(getHeaderData.getDocumentDate());
			setJvDescription(getHeaderData.getRemarks());
			setJvReason(getHeaderData.getReason());
			/* if(getHeaderData.getReason()!=null){
			 setJvReason(getHeaderData.getReason());
			 setJvReasonDesc(mapReson.get(getHeaderData.getReason()));
			 setHiphon("-");
			 }*/
			List<DayBookDetails> listBookDetails = iJVProcessService.getDayBookDetailRecord(getDaybookHeaderId());
			if(listBookDetails.size()>0){
				BigDecimal totDBDebits=BigDecimal.ZERO;
				BigDecimal totDBCredits=BigDecimal.ZERO;
				for(DayBookDetails dayBookDetList:listBookDetails){
					JVProcessDataTableBean jVProcessDataTableBean= new JVProcessDataTableBean();

					jVProcessDataTableBean.setLineNo(dayBookDetList.getDayBookLineNumber());
					jVProcessDataTableBean.setDayBookDetailsID(dayBookDetList.getDayBookDetailsId());
					if(dayBookDetList.getDayBookDetailsBankMaster()!=null){
						jVProcessDataTableBean.setBankId(dayBookDetList.getDayBookDetailsBankMaster().getBankId());
						jVProcessDataTableBean.setBankName(igeneralService.getBankName(dayBookDetList.getDayBookDetailsBankMaster().getBankId()));
						jVProcessDataTableBean.setCurrencyNormal(true);
						jVProcessDataTableBean.setCurrencyWithDenomination(false);
						
					}else{
						jVProcessDataTableBean.setCurrencyNormal(false);
						jVProcessDataTableBean.setCurrencyWithDenomination(true);
						ArrayList<KIOSKDenominationDataTable> lstDenominationTemp=loadDenominatioonFormodify(dayBookDetList.getDayBookLineNumber());
						jVProcessDataTableBean.setLstDenominationDataTable(lstDenominationTemp);
					}
					if(dayBookDetList.getDayBookCurrencyId() !=null){
						jVProcessDataTableBean.setCurrencyId(dayBookDetList.getDayBookCurrencyId().getCurrencyId());
						jVProcessDataTableBean.setCurrencyName(igeneralService.getCurrencyName(dayBookDetList.getDayBookCurrencyId().getCurrencyId()));
					}
					if(dayBookDetList.getDayBookDetailsBankMaster()!=null && dayBookDetList.getDayBookCurrencyId()!=null){
						jVProcessDataTableBean.setAccountNoId(dayBookDetList.getDayBookDetailsBankAccountDetails().getBankAcctDetId());
						jVProcessDataTableBean.setAccountNumber(iJVProcessService.getBankAccountNo(dayBookDetList.getDayBookDetailsBankAccountDetails().getBankAcctDetId()));
					}
					jVProcessDataTableBean.setParticularsDesc(dayBookDetList.getParticulars());
					jVProcessDataTableBean.setGlAccountNo(dayBookDetList.getDayBookFaAccountNumber());
					
					String faAccNo=dayBookDetList.getDayBookFaAccountNumber();
					jVProcessDataTableBean.setJvGlAcNoOne(faAccNo.substring(0,2));
					jVProcessDataTableBean.setJvGlAcNoTwo(faAccNo.substring(2,4));
					jVProcessDataTableBean.setJvGlAcNoThree(faAccNo.substring(4,6));
					jVProcessDataTableBean.setJvGlAcNoFour(faAccNo.substring(6,8));
					jVProcessDataTableBean.setJvGlAcNoFive(faAccNo.substring(8,10));
					jVProcessDataTableBean.setJvGlAcNoSix(faAccNo.substring(10,13));
					jVProcessDataTableBean.setJvGlAcNoSeven(faAccNo.substring(13,17));
					
					if(dayBookDetList.getDayBookFaAccountNumber() !=null && dayBookDetList.getDayBookFaAccountNumber().length()>17){
						String subCodedesc = iJVProcessService.getSlDescription(dayBookDetList.getDayBookFaAccountNumber());	
						String faAccountNo = dayBookDetList.getDayBookFaAccountNumber();
						int accountNoLength = faAccountNo.length();
						String subCode = faAccountNo.substring(accountNoLength - 10);
						jVProcessDataTableBean.setSubCode(subCode);
						if(subCodedesc !=null){
							jVProcessDataTableBean.setSubDescription(subCodedesc);
						}
					}else if(dayBookDetList.getDayBookFaAccountNumber() !=null && dayBookDetList.getDayBookFaAccountNumber().length()<=17){
						String subCodeDesc1 = iJVProcessService.getGlDescription(dayBookDetList.getDayBookFaAccountNumber());
						if(subCodeDesc1 !=null){
							jVProcessDataTableBean.setSubDescription(subCodeDesc1);
						}
					}
					jVProcessDataTableBean.setForeignAmt(dayBookDetList.getDayBookFcAmount());
					jVProcessDataTableBean.setExchangeRate(dayBookDetList.getDayBookExchangeRate());
					jVProcessDataTableBean.setKwdAmount(dayBookDetList.getDayBookLocalAmount());
					jVProcessDataTableBean.setGlAccountNo(dayBookDetList.getDayBookFaAccountNumber());
					
					if(dayBookDetList.getDayBookLocalAmount().signum()==-1)
					{
						totDBCredits=totDBCredits.add(dayBookDetList.getDayBookLocalAmount());
					}else
					{
						totDBDebits=totDBDebits.add(dayBookDetList.getDayBookLocalAmount());
					}

					lstJVProcessDataTableBean1.add(jVProcessDataTableBean);
				}
				setTotalDebits(totDBDebits);			
				setTotalCredit(totDBCredits.abs());
				setLstJVProcessDataTableBean(lstJVProcessDataTableBean1);
				setRenderJVDetailsDataTable(true);
				if(lstJVProcessDataTableBean1.size()>0)
				{
					setRenderSavePanel(true);
				}else
				{
					setRenderSavePanel(false);
				}
				
			}
		}
	}

	public void populateUnapprovalList(){
		List<DayBookHeader> listDayBook = iJVProcessService.getDocumentNo(getDocumentId());
		if(listDayBook.size()>0){
			setDayBookList(listDayBook);

		}
	}
	
	private ArrayList<KIOSKDenominationDataTable> loadDenominatioonFormodify(BigDecimal lineNo)
	{
		ArrayList<KIOSKDenominationDataTable> lstDenominationTemp= new ArrayList<KIOSKDenominationDataTable>();
		List<ForeignCurrencyAdjust> listCurrencyAdjust = iJVProcessService.getCurrencyAdjustRecord(sessionStateManage.getCompanyId(), sessionStateManage.getCountryId(), new BigDecimal(Constants.DOCUMENT_CODE_FOR_JVPROCESS), getFinaceYear(), getJvDocumentNumber(), lineNo);
		if(listCurrencyAdjust.size()>0){
			for(ForeignCurrencyAdjust currenctAdjust:listCurrencyAdjust){
				KIOSKDenominationDataTable kisoskdataTable = new KIOSKDenominationDataTable();  
				kisoskdataTable.setCashAmount(currenctAdjust.getAdjustmentAmount());
				// kisoskdataTable.setDenominationDesc(currenctAdjust.getd);
				kisoskdataTable.setDenominationId(currenctAdjust.getFsDenominationId().getDenominationId());
				List<CurrencyWiseDenomination> lsitDenomination = iJVProcessService.getDenominationList(currenctAdjust.getFsDenominationId().getDenominationId());
				if(lsitDenomination.size()>0){
					kisoskdataTable.setDenominationDesc(lsitDenomination.get(0).getDenominationDesc());
				}
				//kisoskdataTable.setDenominationNo(currenctAdjust.getd);
				// kisoskdataTable.setItem(currenctAdjust.get);
				kisoskdataTable.setNoOfNotes(currenctAdjust.getNotesQuantity());
				if(currenctAdjust.getTransactionType()!=null && currenctAdjust.getTransactionType().equalsIgnoreCase(Constants.P)){
					kisoskdataTable.setPlustminus(new BigDecimal(1));
				}else if(currenctAdjust.getTransactionType()!=null && currenctAdjust.getTransactionType().equalsIgnoreCase(Constants.R)){

					kisoskdataTable.setPlustminus(new BigDecimal(2));
				}
				//kisoskdataTable.setSerial(currenctAdjust.get);
				//kisoskdataTable.setStock(new BigDecimal(currenctAdjust.getStockUpdated()));
				lstDenominationTemp.add(kisoskdataTable);
			}
		}
		return lstDenominationTemp;
	}
		
	public void updateRecord(JVProcessDataTableBean jVProcessDataTableBean)
	{
		setJvCurrencyId(jVProcessDataTableBean.getCurrencyId());
		if(jVProcessDataTableBean.getBankId()!=null)
		{
			setJvBankId(jVProcessDataTableBean.getBankId());
		}else
		{
			setRenderJVDetailsDataTable(false);
			setRenderDenominationDataTable(true);
			displayDenomination();
			setTotalNoofNotes(null);
			setTotalNewBalance(null);
			setRenderSavePanel(false);
		}
		
		if(jVProcessDataTableBean.getAccountNumber()!=null)
		{
			populateAccountNumber();
			setJvAccNumber(jVProcessDataTableBean.getAccountNumber());
		}
		setDayBookDetailsId(jVProcessDataTableBean.getDayBookDetailsID());
		setDayBooDtlLineNo(jVProcessDataTableBean.getLineNo());
		setJvPerticulars(jVProcessDataTableBean.getParticularsDesc());
		
		setJvGlAcNoOne(jVProcessDataTableBean.getJvGlAcNoOne());
		setJvGlAcNoTwo(jVProcessDataTableBean.getJvGlAcNoTwo());
		setJvGlAcNoThree(jVProcessDataTableBean.getJvGlAcNoThree());
		setJvGlAcNoFour(jVProcessDataTableBean.getJvGlAcNoFour());
		setJvGlAcNoFive(jVProcessDataTableBean.getJvGlAcNoFive());
		setJvGlAcNoSix(jVProcessDataTableBean.getJvGlAcNoSix());
		setJvGlAcNoSeven(jVProcessDataTableBean.getJvGlAcNoSeven());
		
		StringBuilder sb = new StringBuilder();
		sb.append(getJvGlAcNoOne()==null ? "00":getJvGlAcNoOne());
		sb.append(getJvGlAcNoTwo()==null ? "00":getJvGlAcNoTwo());
		sb.append(getJvGlAcNoThree()==null ? "00":getJvGlAcNoThree());
		sb.append(getJvGlAcNoFour()==null ? "00":getJvGlAcNoFour());
		sb.append(getJvGlAcNoFive()==null ? "00":getJvGlAcNoFive());
		sb.append(getJvGlAcNoSix()==null ? "00":getJvGlAcNoSix());
		sb.append(getJvGlAcNoSeven()==null ? "00":getJvGlAcNoSeven());

		setJvGlAcNo(sb.toString());
		setSubCode(jVProcessDataTableBean.getSubCode());
		
		setForeignAmount(jVProcessDataTableBean.getForeignAmt());
		setExchangeRate(jVProcessDataTableBean.getExchangeRate());
		setKwdAmount(jVProcessDataTableBean.getKwdAmount());
		
		lstJVProcessDataTableBean1.remove(jVProcessDataTableBean);
		if(lstJVProcessDataTableBean1.size()>0)
		{
			if(jVProcessDataTableBean.getBankId()!=null)
			{
				setRenderSavePanel(true);
			}else
			{
				setRenderSavePanel(false);
			}
			
		}else
		{
			setRenderSavePanel(false);
		}
		if(jVProcessDataTableBean.getKwdAmount().signum()==-1)
		{
			BigDecimal totTemp= getTotalCredit().abs();
			BigDecimal totTemp1=jVProcessDataTableBean.getKwdAmount().abs();
			
			BigDecimal tempTot=totTemp.subtract(totTemp1);
			setTotalCredit(tempTot.abs());
			
		}else
		{
			BigDecimal totTemp=getTotalDebits();
			BigDecimal totTemp1=jVProcessDataTableBean.getKwdAmount();
			BigDecimal tempTot=totTemp.subtract(totTemp1);
			setTotalDebits(tempTot);
		}
		
	}
	
	private void displayDenominationForModify()
	{
		int currenyDecimal=foreignLocalCurrencyDenominationService.getDecimalPerCurrency(getJvCurrencyId());

		ArrayList<KIOSKDenominationDataTable> localLstData =new  ArrayList<KIOSKDenominationDataTable>();

		//List<Stock> dataFromDb = foreignLocalCurrencyDenominationService.getLocalCurrencyDenominationFromStock(sessionStateManage.getCountryId(),sessionStateManage.getUserName(),"56",sessionStateManage.getCompanyId(),getJvCurrencyId().toPlainString());
		List<Object[]> lstObject=iJVProcessService.getCurrencyDenomination(new BigDecimal(sessionStateManage.getCurrencyId()), sessionStateManage.getUserName(),getHdCalValueDate());


		int i = 0;

		for(Object [] obj:lstObject)
		{
			KIOSKDenominationDataTable kIOSKDenominationDataTable= new KIOSKDenominationDataTable();

			kIOSKDenominationDataTable.setStock(new BigDecimal(obj[4]==null ? "00":obj[4].toString()));
			kIOSKDenominationDataTable.setDenominationDesc(obj[2]==null ? "":obj[2].toString());
			kIOSKDenominationDataTable.setDenominationNo(round(new BigDecimal(obj[3]==null ? "00":obj[3].toString()),currenyDecimal));
			kIOSKDenominationDataTable.setSerial(++i);
			kIOSKDenominationDataTable.setDenominationId(new BigDecimal(obj[1]==null ? "00":obj[1].toString()));
			localLstData.add(kIOSKDenominationDataTable);
		}
		setLstDenominationDataTable(localLstData);

	}
	
	private void  clearHeader()
	{
		setJvReason(null);
		setJvDescription(null);
		setJvReasonList(null);
		setTotalDebits(BigDecimal.ZERO);
		setTotalCredit(BigDecimal.ZERO);
		setNetAmount(BigDecimal.ZERO);
		
		
	}
	
	private void clearDetails()
	{
		setJvBankId(null);
		setJvBankLst(null);
		setJvAccNumber(null);
		setJvCurrencyId(null);
		setJvAccNoForUpdate(null);
		setJvPerticulars(null);
		setJvGlAcNo(null);
		setSubCode(null);
		setSubDescription(null);
		setForeignAmount(null);
		setExchangeRate(null);
		setKwdAmount(null);
		setRenderJVDetailsDataTable(false);
		setRenderDocumentSelectMenu(true);
		setRenderDocumentInput(false);
		setRenderDenominationDataTable(false);
		lstJVProcessDataTableBean1.clear();
		setRenderSavePanel(false);
		setHdCalValueDate(new Date());
		setEffectiveMinDate(new Date());
		clearGlAcNo();
		
		setSubCodeReadOnly(false);
		setDaybookHeaderId(null);
		setDayBookDetailsId(null);
		setDayBooDtlLineNo(null);
	}
	public void exit() throws IOException {
        if(sessionStateManage.getRoleId().equalsIgnoreCase("1")){
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		}else{
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}
	}
}
