package com.amg.exchange.treasury.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.stoppayment.service.IStopPaymentService;
import com.amg.exchange.treasury.deal.supplier.model.DayBookDetails;
import com.amg.exchange.treasury.deal.supplier.model.DayBookHeader;
import com.amg.exchange.treasury.model.TreasuryDealDetail;
import com.amg.exchange.treasury.model.TreasuryDealHeader;
import com.amg.exchange.treasury.service.IBankTransferService;
import com.amg.exchange.treasury.service.IFXDetailInformationService;
import com.amg.exchange.treasury.service.IFundEstimationService;
import com.amg.exchange.treasury.service.ISpecialCustomerDealRequestService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("fXDealSupplierApproveBean")
@Scope("session")
public class FxDealWithSupplierApproveBean<T> implements Serializable{

	private static final long serialVersionUID = 1L;
	private static final Logger log=Logger.getLogger(FxDealWithSupplierApproveBean.class);
	private BigDecimal companyId=null;
	private String companyName;
	private String documentDescription=null;
	private BigDecimal documentNo=null;
	private BigDecimal finaceYear;
	private Date hdCalValueDate;
	private BigDecimal customerReference;
	private String fxDlwithSupplierConttract;
	private String fxDlSupplierConcludedby;
	private String fxDlSupplierReutersRef;
	private String fxDlSupplierRemarks;
	private String pdBankName="";
	private BigDecimal pdBankId=null;
	private BigDecimal pdCurrencyId;
	private String pdCurrencyName;
	private String pdAccNoForUpdate;
	private Date pdCalValueDate;
	private BigDecimal pdFCAmount;
	private String pdMultipleDivision;
	private BigDecimal pdExchangeRate;
	private BigDecimal pdAmount;
	private BigDecimal pdLocalExchangeRate;
	private BigDecimal pdLocalAmount;
	private String pdPayableAccountNumber;
	private BigDecimal sdBankId=null;
	private BigDecimal sdCurrencyId;
	private String sdBankName;
	private String sdCurrencyName;
	private String fxDlSalesAccNo;
	private BigDecimal sdBankBalance;
	private Date sdCalValueDate;
	private BigDecimal sdSaleAmount;
	private BigDecimal sdAverageRate;
	private BigDecimal sdLocalAmount;
	private BigDecimal dealBookHeaderPk;
	private BigDecimal treasuryHeaderPk;
	private String errorMessage;
	
	private SessionStateManage session= new SessionStateManage();
	
	private List<BigDecimal> treasuryDealDetailList = new ArrayList<BigDecimal>();
	private List<BigDecimal> dealBookDetailList = new ArrayList<BigDecimal>();
	private List<TreasuryDealHeader> documentNumberList= new ArrayList<TreasuryDealHeader>();
	
	@Autowired
	IFXDetailInformationService<T> fXDetailInformationService;
	@Autowired
	IGeneralService<T> generalService;
	
	@Autowired
	ISpecialCustomerDealRequestService<T> specialCustomerDealRequestService;
	
	@Autowired
	IFundEstimationService<T> fundEstimationService;
	
	@Autowired
	IBankTransferService<T> bankTransferService;


	public List<TreasuryDealHeader> getDocumentNumberList() {
		return documentNumberList;
	}

	public void setDocumentNumberList(List<TreasuryDealHeader> documentNumberList) {
		this.documentNumberList = documentNumberList;
	}

	public BigDecimal getTreasuryHeaderPk() {
		return treasuryHeaderPk;
	}

	public void setTreasuryHeaderPk(BigDecimal treasuryHeaderPk) {
		this.treasuryHeaderPk = treasuryHeaderPk;
	}

	public BigDecimal getDealBookHeaderPk() {
		return dealBookHeaderPk;
	}

	public void setDealBookHeaderPk(BigDecimal dealBookHeaderPk) {
		this.dealBookHeaderPk = dealBookHeaderPk;
	}

	
	public BigDecimal getFinaceYear() {
		return finaceYear;
	}

	public void setFinaceYear(BigDecimal finaceYear) {
		this.finaceYear = finaceYear;
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

	public String getDocumentDescription() {
		return documentDescription;
	}

	public void setDocumentDescription(String documentDescription) {
		this.documentDescription = documentDescription;
	}

	public BigDecimal getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}

	public Date getHdCalValueDate() {
		return hdCalValueDate;
	}

	public void setHdCalValueDate(Date hdCalValueDate) {
		this.hdCalValueDate = hdCalValueDate;
	}

	public BigDecimal getCustomerReference() {
		return customerReference;
	}

	public void setCustomerReference(BigDecimal customerReference) {
		this.customerReference = customerReference;
	}

	public String getFxDlwithSupplierConttract() {
		return fxDlwithSupplierConttract;
	}

	public void setFxDlwithSupplierConttract(String fxDlwithSupplierConttract) {
		this.fxDlwithSupplierConttract = fxDlwithSupplierConttract;
	}

	public String getFxDlSupplierConcludedby() {
		return fxDlSupplierConcludedby;
	}

	public void setFxDlSupplierConcludedby(String fxDlSupplierConcludedby) {
		this.fxDlSupplierConcludedby = fxDlSupplierConcludedby;
	}

	public String getFxDlSupplierReutersRef() {
		return fxDlSupplierReutersRef;
	}

	public void setFxDlSupplierReutersRef(String fxDlSupplierReutersRef) {
		this.fxDlSupplierReutersRef = fxDlSupplierReutersRef;
	}

	public String getFxDlSupplierRemarks() {
		return fxDlSupplierRemarks;
	}

	public void setFxDlSupplierRemarks(String fxDlSupplierRemarks) {
		this.fxDlSupplierRemarks = fxDlSupplierRemarks;
	}

	public String getPdBankName() {
		return pdBankName;
	}

	public void setPdBankName(String pdBankName) {
		this.pdBankName = pdBankName;
	}

	public BigDecimal getPdBankId() {
		return pdBankId;
	}

	public void setPdBankId(BigDecimal pdBankId) {
		this.pdBankId = pdBankId;
	}

	public BigDecimal getPdCurrencyId() {
		return pdCurrencyId;
	}

	public void setPdCurrencyId(BigDecimal pdCurrencyId) {
		this.pdCurrencyId = pdCurrencyId;
	}

	public String getPdCurrencyName() {
		return pdCurrencyName;
	}

	public void setPdCurrencyName(String pdCurrencyName) {
		this.pdCurrencyName = pdCurrencyName;
	}

	public String getPdAccNoForUpdate() {
		return pdAccNoForUpdate;
	}

	public void setPdAccNoForUpdate(String pdAccNoForUpdate) {
		this.pdAccNoForUpdate = pdAccNoForUpdate;
	}

	public Date getPdCalValueDate() {
		return pdCalValueDate;
	}

	public void setPdCalValueDate(Date pdCalValueDate) {
		this.pdCalValueDate = pdCalValueDate;
	}

	public BigDecimal getPdFCAmount() {
		return pdFCAmount;
	}

	public void setPdFCAmount(BigDecimal pdFCAmount) {
		this.pdFCAmount = pdFCAmount;
	}

	public String getPdMultipleDivision() {
		return pdMultipleDivision;
	}

	public void setPdMultipleDivision(String pdMultipleDivision) {
		this.pdMultipleDivision = pdMultipleDivision;
	}

	public BigDecimal getPdExchangeRate() {
		return pdExchangeRate;
	}

	public void setPdExchangeRate(BigDecimal pdExchangeRate) {
		this.pdExchangeRate = pdExchangeRate;
	}

	public BigDecimal getPdAmount() {
		return pdAmount;
	}

	public void setPdAmount(BigDecimal pdAmount) {
		this.pdAmount = pdAmount;
	}

	public BigDecimal getPdLocalExchangeRate() {
		return pdLocalExchangeRate;
	}

	public void setPdLocalExchangeRate(BigDecimal pdLocalExchangeRate) {
		this.pdLocalExchangeRate = pdLocalExchangeRate;
	}

	public BigDecimal getPdLocalAmount() {
		return pdLocalAmount;
	}

	public void setPdLocalAmount(BigDecimal pdLocalAmount) {
		this.pdLocalAmount = pdLocalAmount;
	}

	public String getPdPayableAccountNumber() {
		return pdPayableAccountNumber;
	}

	public void setPdPayableAccountNumber(String pdPayableAccountNumber) {
		this.pdPayableAccountNumber = pdPayableAccountNumber;
	}

	public BigDecimal getSdBankId() {
		return sdBankId;
	}

	public void setSdBankId(BigDecimal sdBankId) {
		this.sdBankId = sdBankId;
	}

	public BigDecimal getSdCurrencyId() {
		return sdCurrencyId;
	}

	public void setSdCurrencyId(BigDecimal sdCurrencyId) {
		this.sdCurrencyId = sdCurrencyId;
	}

	public String getSdBankName() {
		return sdBankName;
	}

	public void setSdBankName(String sdBankName) {
		this.sdBankName = sdBankName;
	}

	public String getSdCurrencyName() {
		return sdCurrencyName;
	}

	public void setSdCurrencyName(String sdCurrencyName) {
		this.sdCurrencyName = sdCurrencyName;
	}

	public String getFxDlSalesAccNo() {
		return fxDlSalesAccNo;
	}

	public void setFxDlSalesAccNo(String fxDlSalesAccNo) {
		this.fxDlSalesAccNo = fxDlSalesAccNo;
	}

	public BigDecimal getSdBankBalance() {
		return sdBankBalance;
	}

	public void setSdBankBalance(BigDecimal sdBankBalance) {
		this.sdBankBalance = sdBankBalance;
	}

	public Date getSdCalValueDate() {
		return sdCalValueDate;
	}

	public void setSdCalValueDate(Date sdCalValueDate) {
		this.sdCalValueDate = sdCalValueDate;
	}

	public BigDecimal getSdSaleAmount() {
		return sdSaleAmount;
	}

	public void setSdSaleAmount(BigDecimal sdSaleAmount) {
		this.sdSaleAmount = sdSaleAmount;
	}

	public BigDecimal getSdAverageRate() {
		return sdAverageRate;
	}

	public void setSdAverageRate(BigDecimal sdAverageRate) {
		this.sdAverageRate = sdAverageRate;
	}

	public BigDecimal getSdLocalAmount() {
		return sdLocalAmount;
	}

	public void setSdLocalAmount(BigDecimal sdLocalAmount) {
		this.sdLocalAmount = sdLocalAmount;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void navigatetoFxDealWithSupplierApprovePage(){
		setAllValuestoNull();
		setTrnxYearList(null);
		setFinaceYear(null);
		setLstofUnApproved(null);
		fetchfinanceYear();
		setDocumentNo(null);
		
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "fxDealWithSupplierApprove.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/fxDealWithSupplierApprove.xhtml");
		} catch(Exception exception){
			  log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			  setErrorMessage(exception.getMessage()); 
			  RequestContext.getCurrentInstance().execute("error.show();");
			  return;       
			  }
	}
	
	public void getUnApprovedDocumentNoList(){
		  try{
		List<TreasuryDealHeader> documentNumberList = fXDetailInformationService.getfxDealWithSupplierUnApprovedRecords();
		if(documentNumberList.size()>0){
		setDocumentNumberList(documentNumberList);
		}
		  } catch(Exception exception){
				  log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
				  setErrorMessage(exception.getMessage()); 
				  RequestContext.getCurrentInstance().execute("error.show();");
				  return;       
				  }
	}

	public void fetchSupplierRecords(){
		  
		setAllValuestoNull();
		List<TreasuryDealHeader> trasuryHeaderList = null;
		List<TreasuryDealDetail> treasuryDetailPDList =null;
		List<TreasuryDealDetail> treasuryDetailPYList =null;
		List<DayBookHeader> dayBookHeaderList = null;
		List<DayBookDetails> dayBookDetailsSDList = null;
		List<DayBookDetails> dayBookDetailsPYList = null;
		try{
		trasuryHeaderList = fXDetailInformationService.fecthTreasurydealHeaderRecordsForSupplierApprove(getDocumentNo(),Constants.Fx_SupplierDealType ,Constants.U ,getFinaceYear());
		
		if(!(trasuryHeaderList.get(0).getModifiedBy() == null ? trasuryHeaderList.get(0).getCreatedBy() :trasuryHeaderList.get(0).getModifiedBy()).equalsIgnoreCase(session.getUserName())){

			if(trasuryHeaderList.size()>0){
				treasuryDetailPDList = fXDetailInformationService.fecthTreasuryDealDetailRecordsForPD(trasuryHeaderList.get(0).getTreasuryDealHeaderId());
				treasuryDetailPYList = fXDetailInformationService.fecthTreasuryDealDetailRecordsForPY(trasuryHeaderList.get(0).getTreasuryDealHeaderId());
				dayBookHeaderList= fXDetailInformationService.fecthDayBookHeaderForSupplierApprove(trasuryHeaderList.get(0).getPaymentVoucherNumber());
			}
			if(dayBookHeaderList.size()>0){
				dayBookDetailsSDList= fXDetailInformationService.fecthDayBookDetailsRecordsForSD(dayBookHeaderList.get(0).getDaybookHeaderId());
				dayBookDetailsPYList= fXDetailInformationService.fecthDayBookDetailsRecordsForPY(dayBookHeaderList.get(0).getDaybookHeaderId());
			}
			if(trasuryHeaderList.size()>0){
				setTreasuryHeaderPk(trasuryHeaderList.get(0).getTreasuryDealHeaderId());
			}
			if(dayBookHeaderList.size()>0){
				setDealBookHeaderPk(dayBookHeaderList.get(0).getDaybookHeaderId());
			}

			for(TreasuryDealHeader headerObj :trasuryHeaderList){
				String companyName=null;
				String documentDesc = null;
				setCompanyId(headerObj.getFsCompanyMaster().getCompanyId());
				if(headerObj.getFsCompanyMaster().getCompanyId()!=null){
					companyName= specialCustomerDealRequestService.getCompanyNameForUpdate(headerObj.getFsCompanyMaster().getCompanyId());
				}
				if(companyName!=null){	
					setCompanyName(companyName);
				}
				if(headerObj.getExDocument().getDocumentID()!=null){
					documentDesc = specialCustomerDealRequestService.getDocumentNameForUpdate(headerObj.getExDocument().getDocumentID());
				}
				if(documentDesc!=null){
					setDocumentDescription(documentDesc);
				}
				setFinaceYear(headerObj.getUserFinanceYear());
				setHdCalValueDate(headerObj.getValueDate());
				setCustomerReference(headerObj.getDealWithCustomer());
				setFxDlwithSupplierConttract(headerObj.getContactName());
				setFxDlSupplierConcludedby(headerObj.getConcludedBy());
				setFxDlSupplierReutersRef(headerObj.getReutersReference());
				setFxDlSupplierRemarks(headerObj.getRemarks());
			}
			for(TreasuryDealDetail dealDetailObj:treasuryDetailPDList){
				treasuryDealDetailList.add(dealDetailObj.getTreasuryDealDetailId());
				if(dealDetailObj.getLineType().equalsIgnoreCase(Constants.PD)){
					setPdBankId(dealDetailObj.getTreasuryDealBankMaster().getBankId());
					String bankName = generalService.getBankName(dealDetailObj.getTreasuryDealBankMaster().getBankId());
					setPdBankName(bankName);
					setPdCurrencyId(dealDetailObj.getTreasuryDealDetailCurrencyMaster().getCurrencyId());
					String currencyName = generalService.getCurrencyName(dealDetailObj.getTreasuryDealDetailCurrencyMaster().getCurrencyId());
					setPdCurrencyName(currencyName);
					setPdAccNoForUpdate(dealDetailObj.getTreasuryDealDetailBankAccountDetails().getBankAcctNo());
					setPdCalValueDate(dealDetailObj.getValueDate());
					setPdFCAmount(dealDetailObj.getFcAmount());
					if(dealDetailObj.getMultiplicationDivision().equalsIgnoreCase("1")){
						setPdMultipleDivision("Multiplication");
					}else{
						setPdMultipleDivision("Division");
					}
					setPdExchangeRate(dealDetailObj.getExchange());
					setPdAmount(dealDetailObj.getSaleAmount());
					setPdLocalExchangeRate(dealDetailObj.getLocalExchangeRate());
					setPdLocalAmount(dealDetailObj.getFcAmount());
				}
			}

			for(TreasuryDealDetail dealDetailObj:treasuryDetailPYList){
				treasuryDealDetailList.add(dealDetailObj.getTreasuryDealDetailId());
			}

			for(DayBookDetails detailsObj:dayBookDetailsSDList){

				dealBookDetailList.add(detailsObj.getDayBookDetailsId());
				if(detailsObj.getDayBookLineType().equalsIgnoreCase(Constants.SD)){
					setSdBankId(detailsObj.getDayBookDetailsBankMaster().getBankId());
					String bankName = generalService.getBankName(detailsObj.getDayBookDetailsBankMaster().getBankId());
					setSdBankName(bankName);
					setSdCurrencyId(detailsObj.getDayBookCurrencyId().getCurrencyId());
					String currencyName = generalService.getCurrencyName(detailsObj.getDayBookCurrencyId().getCurrencyId());
					setSdCurrencyName(currencyName);
					String accountNo = detailsObj.getDayBookDetailsBankAccountDetails().getFundGlno();
					/*		BigDecimal accountDetailsId = detailsObj.getDayBookDetailsBankAccountDetails().getBankAcctDetId() ;
			String glslno= fXDetailInformationService.getAccountNoBasedOnAccDetId(accountDetailsId);*/
					BigDecimal accountBanlance =new BigDecimal(fundEstimationService.getCurrentBalance(accountNo));
					String AccountNo = bankTransferService.getAccountNoBasedOnGlSlNumber(accountNo);
					setFxDlSalesAccNo(AccountNo);
					setSdBankBalance(accountBanlance);
					setSdCalValueDate(detailsObj.getValueDate());
					setSdSaleAmount(detailsObj.getDayBookFcAmount());
					setSdAverageRate(detailsObj.getDayBookExchangeRate());
					setSdLocalAmount(detailsObj.getDayBookLocalAmount());
				}

			}

			for(DayBookDetails detailsObj:dayBookDetailsPYList){
				dealBookDetailList.add(detailsObj.getDayBookDetailsId());
				setPdPayableAccountNumber(detailsObj.getDayBookFaAccountNumber());   
			}


		}else{
			RequestContext.getCurrentInstance().execute("unapprove.show();");
			setAllValuestoNull();
		}
		} catch(Exception exception){
			  log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			  setErrorMessage(exception.getMessage()); 
			  RequestContext.getCurrentInstance().execute("error.show();");
			  return;       
			  }
	}
	
	
	public void approveRecord(){
		try {
			fXDetailInformationService.approveRecords(session.getUserName(), getTreasuryHeaderPk(), getDealBookHeaderPk(), treasuryDealDetailList, dealBookDetailList);
			RequestContext.getCurrentInstance().execute("approve.show();");
		} catch (Exception e) {
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("sqlexception.show();");
			return;
		}

	}
	
	public void exit() throws IOException{
		setAllValuestoNull();
		setDocumentNo(null);
        if(session.getRoleId().equalsIgnoreCase("1")){
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		}else{
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}
	}
	
	public void setAllValuestoNull(){
		setCompanyId(null);
		setCompanyName(null);
		setDocumentDescription(null);
		//setFinaceYear(null);
		setHdCalValueDate(null);
		setCustomerReference(null);
		setFxDlwithSupplierConttract(null);
		setFxDlSupplierConcludedby(null);
		setFxDlSupplierReutersRef(null);
		setFxDlSupplierRemarks(null);
		setPdBankName(null);
		setPdBankId(null);
		setPdCurrencyId(null);
		setPdCurrencyName(null);
		setPdAccNoForUpdate(null);
		setPdCalValueDate(null);
		setPdFCAmount(null);
		setPdMultipleDivision(null);
		setPdExchangeRate(null);
		setPdAmount(null);
		setPdLocalExchangeRate(null);
		setPdLocalAmount(null);
		setPdPayableAccountNumber(null);
		setSdBankId(null);
		setSdCurrencyId(null);
		setSdBankName(null);
		setSdCurrencyName(null);
		setFxDlSalesAccNo(null);
		setSdBankBalance(null);
		setSdCalValueDate(null);
		setSdSaleAmount(null);
		setSdAverageRate(null);
		setSdLocalAmount(null);
		setTreasuryHeaderPk(null);
		setDealBookHeaderPk(null);
		treasuryDealDetailList.clear();
		dealBookDetailList.clear();
	}
	
	private List<UserFinancialYear> trnxYearList;
	private String errorMsg;
	List<TreasuryDealHeader> lstofUnApproved;
	

	public List<UserFinancialYear> getTrnxYearList() {
		return trnxYearList;
	}

	public void setTrnxYearList(List<UserFinancialYear> trnxYearList) {
		this.trnxYearList = trnxYearList;
	}
	
	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	public List<TreasuryDealHeader> getLstofUnApproved() {
		return lstofUnApproved;
	}

	public void setLstofUnApproved(List<TreasuryDealHeader> lstofUnApproved) {
		this.lstofUnApproved = lstofUnApproved;
	}
	@Autowired
	IStopPaymentService<T> stopPaymentService;
	
	public void fetchfinanceYear() {

		
		try {

			List<UserFinancialYear> lstFinancialYear = stopPaymentService.getTransferYearList();
			if (lstFinancialYear!=null && lstFinancialYear.size() != 0) {
				setTrnxYearList(lstFinancialYear);
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

	public void getDocumentNoDropDown(){
		try{
			setLstofUnApproved(null);
			setDocumentNo(null);
			setAllValuestoNull();
		//List<TreasuryDealHeader> lstofUnApprovedTDlHeader = fXDetailInformationService.fetchDocumentNumber();
		List<TreasuryDealHeader> lstofUnApprovedTDlHeader = fXDetailInformationService.fetchDocumentNumberFromTreasDealheaderForSupplier(Constants.Fx_SupplierDealType ,Constants.U ,getFinaceYear());
		if(lstofUnApprovedTDlHeader.size()!=0){
			setLstofUnApproved(lstofUnApprovedTDlHeader);
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
	
}
