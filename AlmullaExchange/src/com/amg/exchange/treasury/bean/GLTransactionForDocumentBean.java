package com.amg.exchange.treasury.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.foreigncurrency.service.IForeignCurrencyPurchaseService;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.treasury.service.IGLTransactionForADocument;
import com.amg.exchange.treasury.service.ISpecialCustomerDealRequestService;
import com.amg.exchange.treasury.viewModel.GLTransactionView;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("glTransactionForDocumentBean")
@Scope("session")
public class GLTransactionForDocumentBean<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final Logger log = Logger.getLogger(GLTransactionForDocumentBean.class);
	private BigDecimal serialNo;
	private BigDecimal applicationCountryId;
	private BigDecimal companyId = null;
	private BigDecimal documentId;
	private BigDecimal documentNo;
	private BigDecimal documentFinancialYear;
	private String acMMYY;
	private String documentDate;
	private String isActive;
	private String mctNo;
	// private String faAccountNumber;
	// private BigDecimal lineNumber;
	private BigDecimal currencyId;
	// private String itemDescription;
	// private String currencyDescription;
	// private BigDecimal foreignAmount;
	// private BigDecimal exchangeRate;
	// private BigDecimal localAmount;
	// private String specialDeal;
	private BigDecimal documentCode;
	private BigDecimal copmanyCode;
	private String companyName;
	private String status;
	private List<GLTransactionForADocumentDataTable> enquiryDTList = new ArrayList<GLTransactionForADocumentDataTable>();
	private List<CompanyMasterDesc> companyListFromDB = new ArrayList<CompanyMasterDesc>();
	private SessionStateManage sessionStateManage = new SessionStateManage();
	private List<UserFinancialYear> userFinancialYearList = new ArrayList<UserFinancialYear>();
	private List<Document> dcoumentList = new ArrayList<Document>();
	private Map<BigDecimal, String> comapnyMasterMap = new HashMap<BigDecimal, String>();
	@Autowired
	IGeneralService<T> igeneralService;
	@Autowired
	IForeignCurrencyPurchaseService<T> iforeigncurrencyservice;
	// @Autowired
	// IDocumentService idocumentService;
	@Autowired
	IGLTransactionForADocument iglTransactionService;
	@Autowired
	ISpecialCustomerDealRequestService<T> ispecialDealService;

	public void setCompanyListFromDB(List<CompanyMasterDesc> companyListFromDB) {
		this.companyListFromDB = companyListFromDB;
	}

	public BigDecimal getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(BigDecimal serialNo) {
		this.serialNo = serialNo;
	}

	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}

	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}

	public BigDecimal getCompanyId() {
		return companyId;
	}

	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}

	public BigDecimal getDocumentId() {
		return documentId;
	}

	public void setDocumentId(BigDecimal documentId) {
		this.documentId = documentId;
	}

	public BigDecimal getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}

	public BigDecimal getDocumentFinancialYear() {
		return documentFinancialYear;
	}

	public void setDocumentFinancialYear(BigDecimal documentFinancialYear) {
		this.documentFinancialYear = documentFinancialYear;
	}

	public BigDecimal getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	public String getAcMMYY() {
		return acMMYY;
	}

	public void setAcMMYY(String acMMYY) {
		this.acMMYY = acMMYY;
	}

	public String getDocumentDate() {
		return documentDate;
	}

	public void setDocumentDate(String documentDate) {
		this.documentDate = documentDate;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getMctNo() {
		return mctNo;
	}

	public void setMctNo(String mctNo) {
		this.mctNo = mctNo;
	}

	public BigDecimal getDocumentCode() {
		return documentCode;
	}

	public void setDocumentCode(BigDecimal documentCode) {
		this.documentCode = documentCode;
	}

	public BigDecimal getCopmanyCode() {
		return copmanyCode;
	}

	public void setCopmanyCode(BigDecimal copmanyCode) {
		this.copmanyCode = copmanyCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public List<UserFinancialYear> getUserFinancialYearList() {
		  List<UserFinancialYear> userFinancialYearList=null;
		  try{
		userFinancialYearList = iforeigncurrencyservice.getAllDocumentYear();
		  }catch(Exception exception){
		    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		    setErrorMessage(exception.getMessage()); 
		    }
		return userFinancialYearList;
	}

	public void setUserFinancialYearList(List<UserFinancialYear> userFinancialYearList) {
		this.userFinancialYearList = userFinancialYearList;
	}

	public List<Document> getDcoumentList() {
		return iglTransactionService.getAllDocumentTypeList();
	}

	public void setDcoumentList(List<Document> dcoumentList) {
		this.dcoumentList = dcoumentList;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<GLTransactionForADocumentDataTable> getEnquiryDTList() {
		return enquiryDTList;
	}

	public void setEnquiryDTList(List<GLTransactionForADocumentDataTable> enquiryDTList) {
		this.enquiryDTList = enquiryDTList;
	}

	public List<CompanyMasterDesc> getCompanyListFromDB() {
		  List<CompanyMasterDesc> data=null;
		  try{
		data = igeneralService.getCompanyList(sessionStateManage.getCompanyId(), new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"));
		for (CompanyMasterDesc comapnyMasterDesc : data) {
			comapnyMasterMap.put(comapnyMasterDesc.getFsCompanyMaster().getCompanyId(), comapnyMasterDesc.getCompanyName());
		}
		  }catch(Exception exception){
		    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		    setErrorMessage(exception.getMessage()); 
		    }
		return data;
	}

	@Autowired
	IGeneralService<T> igeneral;
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void pageNativigationToGLTransactionEnquiry() {
		clearAll();
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "gltransactionforadocumentenquiry.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/gltransactionforadocumentenquiry.xhtml");
		} catch(Exception exception){
		  log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		  setErrorMessage(exception.getMessage()); 
		  RequestContext.getCurrentInstance().execute("error.show();");
		  return;       
		  }
	}

	public void glTrnsactionEnquiry() {
		  try{
		enquiryDTList.clear();
		setAcMMYY(null);
		setDocumentDate(null);
		setStatus(null);
		log.info("====================COMPANYID= " + getCompanyId());
		log.info("====================DOCUMENTID= " + getDocumentId());
		log.info("====================FINANCIALYEAR= " + getDocumentFinancialYear());
		log.info("====================DOCUMENT NUMBER= " + getDocumentNo());
		if (getDocumentId() != null && getDocumentFinancialYear() != null && getCompanyId() != null && getDocumentNo() != null) {
			List<GLTransactionView> listFromView = iglTransactionService.getAllGlTransactionForADocument(sessionStateManage.getCountryId(), getCompanyId(), getDocumentId(), getDocumentFinancialYear(), getDocumentNo());
			if (listFromView.size() > 0) {
				log.info("=====================SIZE IS GREATER THAN ZERO==================" + listFromView.size());
				for (GLTransactionView glTransactionViewObj : listFromView) {
					GLTransactionForADocumentDataTable glTrnxDTObj = new GLTransactionForADocumentDataTable();
					SimpleDateFormat acmmYY = new SimpleDateFormat("MM/yyyy");
					setAcMMYY(acmmYY.format(glTransactionViewObj.getAcMMYY()));
					// setDocDate(glTransactionViewObj.getDocumentDate().getTimezoneOffset());
					SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
					setDocumentDate(format.format(glTransactionViewObj.getDocumentDate()));
					/*
					 * if(glTransactionViewObj.getIsActive().equalsIgnoreCase("T"
					 * )){ setStatus("TEMPORARY"); }else{ setStatus("APPROVED");
					 * }
					 */
					setStatus(glTransactionViewObj.getStatusDesc());
					glTrnxDTObj.setExchangeRate(glTransactionViewObj.getExchangeRate());
					glTrnxDTObj.setFaAccountNumber(glTransactionViewObj.getFaAccountNumber());
					glTrnxDTObj.setForeignAmount(glTransactionViewObj.getForeignAmount());
					glTrnxDTObj.setSpecialDeal(glTransactionViewObj.getSpecialDeal());
					glTrnxDTObj.setLineNumber(glTransactionViewObj.getLineNumber());
					glTrnxDTObj.setItemDescription(glTransactionViewObj.getItemDescription());
					glTrnxDTObj.setLocalAmount(glTransactionViewObj.getLocalAmount());
					// glTrnxDTObj.setCurrencyDescription(ispecialDealService.getCurrencyForUpdate(glTransactionViewObj.getCurrencyId()));
					glTrnxDTObj.setCurrencyDescription(iglTransactionService.getQuoteName(glTransactionViewObj.getCurrencyId()));
					glTrnxDTObj.setCopmanyCode(glTransactionViewObj.getCopmanyCode());
					glTrnxDTObj.setDocumentCode(glTransactionViewObj.getDocumentCode());
					if (glTransactionViewObj.getCuctomerRef() != null) {
						glTrnxDTObj.setCustomerRef(iglTransactionService.getCustomeReference(glTransactionViewObj.getCuctomerRef()));
					}
					enquiryDTList.add(glTrnxDTObj);
				}
			} else {
				RequestContext.getCurrentInstance().execute("nodatafound.show();");
			}
		}
		  }catch(NullPointerException ne){
		    log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
		    setErrorMessage("MethodName::glTrnsactionEnquiry");
		    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
		    return; 
		  }catch(Exception exception){
		    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		    setErrorMessage(exception.getMessage()); 
		    RequestContext.getCurrentInstance().execute("error.show();");
		    return;       
		  }
	}

	public void clearAll() {
		setCompanyId(null);
		setDocumentId(null);
		setCompanyName(null);
		setCopmanyCode(null);
		setDocumentDate(null);
		setAcMMYY(null);
		setStatus(null);
		setDocumentCode(null);
		setDocumentNo(null);
		setDocumentFinancialYear(null);
		enquiryDTList.clear();
	}
	/*
	 * private int docDate;
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * public int getDocDate() { return docDate; } public void setDocDate(int
	 * docDate) { this.docDate = docDate; } public void
	 * onSelectDateSelect(SelectEvent event) {
	 * 
	 * SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
	 * //setDocDate(format.format(event.getObject()));
	 * 
	 * }
	 */
	
	  private String errorMessage;

	  public String getErrorMessage() {
		    return errorMessage;
	  }

	  public void setErrorMessage(String errorMessage) {
		    this.errorMessage = errorMessage;
	  }
	
	
}
