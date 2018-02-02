package com.amg.exchange.jvprocess.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.foreigncurrency.model.CurrencyWiseDenomination;
import com.amg.exchange.foreigncurrency.model.ForeignCurrencyAdjust;
import com.amg.exchange.foreigncurrency.model.Stock;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.foreigncurrency.service.ForeignLocalCurrencyDenominationService;
import com.amg.exchange.jvprocess.service.IJVProcessService;
import com.amg.exchange.remittance.bean.KIOSKDenominationDataTable;
import com.amg.exchange.treasury.deal.supplier.model.DayBookDetails;
import com.amg.exchange.treasury.deal.supplier.model.DayBookHeader;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.treasury.service.IFundEstimationService;
import com.amg.exchange.util.CommonUtil;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("jVProcessApprovalBean")
@Scope("session")
public class JVProcessApprovalBean<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final Logger log = Logger.getLogger(JVProcessApprovalBean.class);

	private BigDecimal dayBookHeaderId=null;
	private BigDecimal documentId=null;
	private String companyName;
	private BigDecimal finaceYear;
	private BigDecimal documentSerialIdNumber;

	private String jvReason;
	private String jvReasonDesc;
	private String hiphon = null;
	private String jvDescription;
	private BigDecimal totalDebits;
	private BigDecimal totalCredit;
	private BigDecimal netAmount;
	private BigDecimal lineNo;
	private BigDecimal jvBankId;
	private BigDecimal companyId;

	private List<BankMasterDTO> jvBankLst;
	private List<BankAccountDetails> lstjvAccountNumber;
	private List<JVReasonDTO> jvReasonList;

	private BigDecimal jvAccNumber;
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
	private String createdBy;
	
	private ArrayList<KIOSKDenominationDataTable> lstDenominationDataTable = null;
	private ArrayList<KIOSKDenominationDataTable> lstDenominationForDailog=null;
	 ArrayList<KIOSKDenominationDataTable> lstDenominationAdjustment= new ArrayList<KIOSKDenominationDataTable>();
	 Map<String, String> mapReson = new HashMap<String, String>();
	
	private List<JVProcessDataTableBean> lstJVProcessDataTableBean;
	SessionStateManage sessionStateManage=new SessionStateManage();

	private List<JVProcessDataTableBean> lstJVDetails;
	 private List<DayBookHeader> dayBookList=new ArrayList<DayBookHeader>();
	
	private Boolean renderJvDetailsDataTable=false;
	private Boolean renderDenominationDataTable=false;
	
	
	
	public Boolean getRenderJvDetailsDataTable() {
		return renderJvDetailsDataTable;
	}

	public Boolean getRenderDenominationDataTable() {
		return renderDenominationDataTable;
	}

	public void setRenderJvDetailsDataTable(Boolean renderJvDetailsDataTable) {
		this.renderJvDetailsDataTable = renderJvDetailsDataTable;
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
	

	public ArrayList<KIOSKDenominationDataTable> getLstDenominationAdjustment() {
		return lstDenominationAdjustment;
	}

	public void setLstDenominationAdjustment(
			ArrayList<KIOSKDenominationDataTable> lstDenominationAdjustment) {
		this.lstDenominationAdjustment = lstDenominationAdjustment;
	}



	private boolean booRenderJvAccNo;

	private boolean booRenderJvAccNoForUpdate;
	
	private boolean renderJVDetailsDataTable;

	
	public BigDecimal getDayBookHeaderId() {
		return dayBookHeaderId;
	}

	public void setDayBookHeaderId(BigDecimal dayBookHeaderId) {
		this.dayBookHeaderId = dayBookHeaderId;
	}

	
	public BigDecimal getDocumentId() {
		return documentId;
	}

	public void setDocumentId(BigDecimal documentId) {
		this.documentId = documentId;
	}

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

	
	public String getJvReasonDesc() {
		return jvReasonDesc;
	}

	public void setJvReasonDesc(String jvReasonDesc) {
		this.jvReasonDesc = jvReasonDesc;
	}

	
	public String getHiphon() {
		return hiphon;
	}

	public void setHiphon(String hiphon) {
		this.hiphon = hiphon;
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

	public BigDecimal getLineNo() {
		return lineNo;
	}

	public void setLineNo(BigDecimal lineNo) {
		this.lineNo = lineNo;
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

	public BigDecimal getJvAccNumber() {
		return jvAccNumber;
	}

	public void setJvAccNumber(BigDecimal jvAccNumber) {
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

	
	public List<JVReasonDTO> getJvReasonList() {
		return jvReasonList;
	}

	public void setJvReasonList(List<JVReasonDTO> jvReasonList) {
		this.jvReasonList = jvReasonList;
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

	public boolean isBooRenderJvAccNoForUpdate() {
		return booRenderJvAccNoForUpdate;
	}

	public void setBooRenderJvAccNoForUpdate(boolean booRenderJvAccNoForUpdate) {
		this.booRenderJvAccNoForUpdate = booRenderJvAccNoForUpdate;
	}

	public static Logger getLog() {
		return log;
	}
	

	public Boolean getBooRead() {
		return booRead;
	}

	public void setBooRead(Boolean booRead) {
		this.booRead = booRead;
	}
	

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public boolean isRenderJVDetailsDataTable() {
		return renderJVDetailsDataTable;
	}

	public void setRenderJVDetailsDataTable(boolean renderJVDetailsDataTable) {
		this.renderJVDetailsDataTable = renderJVDetailsDataTable;
	}

	public List<DayBookHeader> getDayBookList() {
		return dayBookList;
	}

	public void setDayBookList(List<DayBookHeader> dayBookList) {
		this.dayBookList = dayBookList;
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

	@Autowired
	IGeneralService<T> igeneralService;

	@Autowired
	IJVProcessService<T> iJVProcessService;
	
	@Autowired
	IFundEstimationService<T> fundEstimationService;
	
	@Autowired
	ForeignLocalCurrencyDenominationService<T> foreignLocalCurrencyDenominationService;
	

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
	public void jvProcessApprovalNavigation()
	{
		clear();
		setDocumentId(null);
		setDocumentSerialIdNumber(null);
		setHdCalValueDate(null);
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "jvprocessapproval.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../jvprocess/jvprocessapproval.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
		getCompanyListFromDB();
		getFinaceYearFromDB();
		getDocumentIdfromDb();
		populateUnapprovalList();
	}

	public void  clear()
	{
		
		setDayBookHeaderId(null);
		setJvReason(null);
		setJvDescription(null);
		setTotalDebits(BigDecimal.ZERO);
		setTotalCredit(BigDecimal.ZERO);
		setNetAmount(BigDecimal.ZERO);
		setLineNo(null);
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
		setRenderDenominationDataTable(false);
		setCreatedBy(null);
		setDocumentSerialIdNumber(null);
		setHdCalValueDate(null);
		lstJVProcessDataTableBean1.clear();
		setJvReasonDesc(null);
		setHiphon(null);
	}
	
	public void  jvDetailsClear()
	{
		
		setLineNo(null);
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
	
	/*public BigDecimal getDocumentSerialIdNumberFromDB() {
		String documentSerialIdNumber =igeneralService.getNextDocumentReferenceNumber(sessionStateManage.getCountryId().intValue() ,sessionStateManage.getCompanyId().intValue(), Integer.parseInt(Constants.DOCUMENT_CODE_FOR_FX_DEAL_WITH_BANK), finaceYear.intValue(),processIn);
		setDocumentSerialIdNumber(new BigDecimal(documentSerialIdNumber));
		return new BigDecimal(documentSerialIdNumber);
	}*/

	public void loadBankList()
	{
		List<BankMasterDTO>  banksList= igeneralService.getCoresBankListForApplicationCountry(sessionStateManage.getCountryId());
		setJvBankLst(banksList);
	}
	public void jvbankSelection()
	{
		setJvCurrencyId(null);
		setJvAccNumber(null);
		List <CurrencyMasterDTO> lstCurrencyMasterDTO=iJVProcessService.getCurrencyOfBank(getJvBankId());
		setCurrencyListFromDB(lstCurrencyMasterDTO);
		

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
		}else
		{
			setRenderJVDetailsDataTable(false);
			setRenderDenominationDataTable(true);
			displayDenomination();
		}
	}
	
	private void loadCurrenyMaster()
	{
		List <CurrencyMasterDTO> lstCurrencyMasterDTO=iJVProcessService.getCurrencyMaster();
		setCurrencyListFromDB(lstCurrencyMasterDTO);
	}
	List<JVProcessDataTableBean> lstJVProcessDataTableBean1 = new ArrayList <JVProcessDataTableBean>();
	
	
	public void addRecordsToDataTable()
	{
		
		addRecords();
		
		setLstJVProcessDataTableBean(lstJVProcessDataTableBean1);
		clatotalAmt();
	}
	
	private void addRecords()
	{
		JVProcessDataTableBean jVProcessDataTableBean= new JVProcessDataTableBean();
		
		jVProcessDataTableBean.setLineNo(getLineNo());
		jVProcessDataTableBean.setBankId(getJvBankId());
		jVProcessDataTableBean.setCurrencyId(getJvCurrencyId());
		jVProcessDataTableBean.setAccountNoId(getJvAccNumber());
		jVProcessDataTableBean.setParticularsDesc(getJvPerticulars());
		jVProcessDataTableBean.setGlAccountNo(getJvGlAcNo());
		jVProcessDataTableBean.setSubCode(getSubCode());
		jVProcessDataTableBean.setForeignAmt(getForeignAmount());
		jVProcessDataTableBean.setExchangeRate(getExchangeRate());
		jVProcessDataTableBean.setKwdAmount(getKwdAmount());
		
		lstJVProcessDataTableBean1.add(jVProcessDataTableBean);
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
	
	public void populateUnapprovalList(){
		List<DayBookHeader> listDayBook = iJVProcessService.getDocumentNo(getDocumentId());
		if(listDayBook.size()>0){
		setDayBookList(listDayBook);
		
		}
	}
	
	public void getDocumentIdfromDb(){
		List<Document> listDocument = igeneralService.getDocument(new BigDecimal(Constants.DOCUMENT_CODE_FOR_JVPROCESS), sessionStateManage.getLanguageId());
		if(listDocument.size()>0){
			setDocumentId(listDocument.get(0).getDocumentID());
		}
	}
	
	  
	  public void addDenaminationDetails(){
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
			
			jVProcessDataTableBean.setLineNo(getLineNo());
			jVProcessDataTableBean.setBankId(getJvBankId());
			jVProcessDataTableBean.setCurrencyId(getJvCurrencyId());
			jVProcessDataTableBean.setAccountNoId(getJvAccNumber());
			jVProcessDataTableBean.setParticularsDesc(getJvPerticulars());
			jVProcessDataTableBean.setGlAccountNo(getJvGlAcNo());
			jVProcessDataTableBean.setSubCode(getSubCode());
			jVProcessDataTableBean.setForeignAmt(getForeignAmount());
			jVProcessDataTableBean.setExchangeRate(getExchangeRate());
			jVProcessDataTableBean.setKwdAmount(getKwdAmount());
			jVProcessDataTableBean.setCurrencyNormal(false);
			jVProcessDataTableBean.setCurrencyWithDenomination(true);
			jVProcessDataTableBean.setLstDenominationDataTable(lstDenominationTemp);
			lstJVProcessDataTableBean1.add(jVProcessDataTableBean);
		  
		
		setRenderDenominationDataTable(false);
		
		setLstJVProcessDataTableBean(lstJVProcessDataTableBean1);
		if(lstJVProcessDataTableBean1.size()>0)
		{
			clatotalAmt();
			setRenderJVDetailsDataTable(true);
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
			
			List<Stock> dataFromDb = foreignLocalCurrencyDenominationService.getLocalCurrencyDenominationFromStock(sessionStateManage.getCountryId(),sessionStateManage.getUserName(),"56",sessionStateManage.getCompanyId(),getJvCurrencyId().toPlainString());

			int i = 0;
			
			for (Stock element : dataFromDb) {
				int stock = element.getOpenQuantity() + element.getPurchaseQuantity()	+ element.getReceivedQuantity() - (element.getSaleQuantity() + element.getTransactionQuantity());
				
				KIOSKDenominationDataTable kIOSKDenominationDataTable= new KIOSKDenominationDataTable();
				
				kIOSKDenominationDataTable.setStock(new BigDecimal(stock));
				
				kIOSKDenominationDataTable.setDenominationDesc(element.getDenominationId().getDenominationDesc());
				
				kIOSKDenominationDataTable.setDenominationNo(round(element.getDenominationId().getDenominationAmount(),currenyDecimal));
				
				kIOSKDenominationDataTable.setSerial(++i);
			
				kIOSKDenominationDataTable.setDenominationId(element.getDenominationId().getDenominationId());
				
				localLstData.add(kIOSKDenominationDataTable);
				

				}
			setLstDenominationDataTable(localLstData);
			
		}
	  
	/*  public void checkStatusType(JVProcessDataTableBean jVProcessDataTableBean)
	  {
		  System.out.println("dd locaDena");
		  
		  ArrayList<KIOSKDenominationDataTable> localLstData =jVProcessDataTableBean.getLstDenominationDataTable();
		  
		  setLstDenominationForDailog(localLstData);
		  
		  RequestContext.getCurrentInstance().execute("locaDena.show();");
		  
	  }*/
	  
	 public void clearData(){
		 setDayBookHeaderId(null);
			setJvReason(null);
			setJvDescription(null);
			setTotalDebits(BigDecimal.ZERO);
			setTotalCredit(BigDecimal.ZERO);
			setNetAmount(BigDecimal.ZERO);
			setLineNo(null);
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
			setRenderDenominationDataTable(false);
			setCreatedBy(null);
			setHdCalValueDate(null);
			lstJVProcessDataTableBean1.clear();

		 setLstJVProcessDataTableBean(null);
		 setJvReasonDesc(null);
		 setHiphon(null);
	 }
	 public void fetchRecordfromDb(){
	    	clearData();
		/*	loadBankList();
			loadCurrenyMaster();*/
		 populateJvDescription();
		 List<DayBookHeader> listDayHeader = iJVProcessService.getDayBookHeaderRecord(getDocumentId(), getDocumentSerialIdNumber());
		 if(listDayHeader.size()>0){
			 DayBookHeader getHeaderData = listDayHeader.get(0);
			 setDayBookHeaderId(getHeaderData.getDaybookHeaderId());
			 setHdCalValueDate(getHeaderData.getDocumentDate());
			 setTotalDebits(getHeaderData.getLocalAmount());
			 setJvDescription(getHeaderData.getRemarks());
			 setTotalCredit(getHeaderData.getLocalAmount());
			 if(getHeaderData.getReason()!=null){
			 setJvReason(getHeaderData.getReason());
			 setJvReasonDesc(mapReson.get(getHeaderData.getReason()));
			 setHiphon("-");
			 }
			List<DayBookDetails> listBookDetails = iJVProcessService.getDayBookDetailRecord(getDayBookHeaderId());
			if(listBookDetails.size()>0){
				for(DayBookDetails dayBookDetList:listBookDetails){
					JVProcessDataTableBean jVProcessDataTableBean= new JVProcessDataTableBean();
					
					jVProcessDataTableBean.setLineNo(dayBookDetList.getDayBookLineNumber());
					if(dayBookDetList.getDayBookDetailsBankMaster()!=null){
					jVProcessDataTableBean.setBankId(dayBookDetList.getDayBookDetailsBankMaster().getBankId());
					jVProcessDataTableBean.setBankName(igeneralService.getBankName(dayBookDetList.getDayBookDetailsBankMaster().getBankId()));
					jVProcessDataTableBean.setCurrencyNormal(true);
					jVProcessDataTableBean.setCurrencyWithDenomination(false);
					}else{
						jVProcessDataTableBean.setCurrencyNormal(false);
						jVProcessDataTableBean.setCurrencyWithDenomination(true);
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
					
					lstJVProcessDataTableBean1.add(jVProcessDataTableBean);
				}
				setLstJVProcessDataTableBean(lstJVProcessDataTableBean1);
				setRenderJVDetailsDataTable(true);
			}
		 }
	 }
	 
	 public void  populateAccountNumber(BigDecimal bankId,BigDecimal currencyId){
		{
			
				List<BankAccountDetails> ptabledata = fundEstimationService.getAccountNumber(bankId, currencyId);
				setLstjvAccountNumber(ptabledata);
		}
		}
	 
		public void exit() throws IOException {
			if (sessionStateManage.getRoleId().equalsIgnoreCase("1")) {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
			} else {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
			}
		}
		
		public void approval(){
			try{
			if(CommonUtil.validateApprovedBy(sessionStateManage.getUserName(), getCreatedBy())!= true){
				
				iJVProcessService.approve(getDayBookHeaderId(),sessionStateManage.getUserName(),new Date());
				RequestContext.getCurrentInstance().execute("approvedsuccess.show();");
				
			}else{
				RequestContext.getCurrentInstance().execute("notabletoApprove.show();");	
				
			}
		}catch(Exception e){
			log.info("Approval time exception :"+e);
		}
			
		}
			
		
		  public void checkStatusType(JVProcessDataTableBean jVProcessDataTableBean)
		  {
			  setLstDenominationForDailog(null);
			  if(lstDenominationAdjustment!=null && lstDenominationAdjustment.size()>0){
				  lstDenominationAdjustment.clear();
			  }
			  
			  List<ForeignCurrencyAdjust> listCurrencyAdjust = iJVProcessService.getCurrencyAdjustRecord(sessionStateManage.getCompanyId(), sessionStateManage.getCountryId(), new BigDecimal(Constants.DOCUMENT_CODE_FOR_JVPROCESS), getFinaceYear(), getDocumentSerialIdNumber(), jVProcessDataTableBean.getLineNo());
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
				  lstDenominationAdjustment.add(kisoskdataTable);
			  }
			  setLstDenominationForDailog(lstDenominationAdjustment);
			  
			  RequestContext.getCurrentInstance().execute("locaDena.show();");
			 
			  }
		  }
		  
		  public void clickOk(){
			  clearData();
			  setHdCalValueDate(null);
			  populateUnapprovalList();
			  RequestContext.getCurrentInstance().execute("approvedsuccess.hide();");
		  }
		  
		  public void populateJvDescription(){
				List<JVReasonDTO> jvReasonList = iJVProcessService.getReasonDetails();
				setJvReasonList(jvReasonList);
				for(JVReasonDTO jvResonDtoList:jvReasonList){
					mapReson.put(jvResonDtoList.getParamCodeDef(), jvResonDtoList.getParamFullDesc());
				}
				}
	}

		

