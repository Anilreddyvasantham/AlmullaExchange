package com.amg.exchange.foreigncurrency.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import com.amg.exchange.common.service.IDocumentSerialityService;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.registration.model.RoleMaster;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.treasury.model.DocumentSeriality;
import com.amg.exchange.treasury.model.RemittanceModeDescription;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("documentSerialityMasterBean")
@Scope("session")
public class DocumentSerialityMasterBean<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final Logger log = Logger.getLogger(ForeignCurrencyPurchaseBean.class);

	private BigDecimal documentSerialityId;
	private BigDecimal country;
	private BigDecimal company;
	private BigDecimal document;
	private BigDecimal userFinancialYear;
	private BigDecimal startNo;
	private BigDecimal endNo;
	private BigDecimal nextNo;
	private String isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String remarks = "";

	private String dynamicLabelForActivateDeactivate;

	private Boolean booDocumentSerialityDetails = false;
	private Boolean booDocumentSeriality = false;
	private Boolean booEdit = false;
	private Boolean isdisable = false;
	private Boolean booBtnClear = false;

	private String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	SessionStateManage sessionStateManage = new SessionStateManage();

	public DocumentSerialityMasterBean() {
		// TODO Auto-generated constructor stub
	}

	@Autowired
	IGeneralService<T> generalService;

	@Autowired
	IDocumentSerialityService<T> documentSerialityService;

	public IGeneralService<T> getGeneralService() {
		return generalService;
	}

	public void setGeneralService(IGeneralService<T> generalService) {
		this.generalService = generalService;
	}

	public IDocumentSerialityService<T> getDocumentSerialityService() {
		return documentSerialityService;
	}

	public void setDocumentSerialityService(IDocumentSerialityService<T> documentSerialityService) {
		this.documentSerialityService = documentSerialityService;
	}

	public BigDecimal getDocumentSerialityId() {
		return documentSerialityId;
	}

	public void setDocumentSerialityId(BigDecimal documentSerialityId) {
		this.documentSerialityId = documentSerialityId;
	}

	public BigDecimal getCountry() {
		return country;
	}

	public void setCountry(BigDecimal country) {
		this.country = country;
	}

	public BigDecimal getCompany() {
		return company;
	}

	public void setCompany(BigDecimal company) {
		this.company = company;
	}

	public BigDecimal getDocument() {
		return document;
	}

	public void setDocument(BigDecimal document) {
		this.document = document;
	}

	public BigDecimal getUserFinancialYear() {
		return userFinancialYear;
	}

	public void setUserFinancialYear(BigDecimal userFinancialYear) {
		this.userFinancialYear = userFinancialYear;
	}

	public BigDecimal getStartNo() {
		return startNo;
	}

	public void setStartNo(BigDecimal startNo) {
		this.startNo = startNo;
	}

	public BigDecimal getEndNo() {
		return endNo;
	}

	public void setEndNo(BigDecimal endNo) {
		this.endNo = endNo;
	}

	public BigDecimal getNextNo() {
		return nextNo;
	}

	public void setNextNo(BigDecimal nextNo) {
		this.nextNo = nextNo;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public SessionStateManage getSessionStateManage() {
		return sessionStateManage;
	}

	public void setSessionStateManage(SessionStateManage sessionStateManage) {
		this.sessionStateManage = sessionStateManage;
	}

	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}

	public void setDynamicLabelForActivateDeactivate(String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}

	public Boolean getBooDocumentSerialityDetails() {
		return booDocumentSerialityDetails;
	}

	public void setBooDocumentSerialityDetails(Boolean booDocumentSerialityDetails) {
		this.booDocumentSerialityDetails = booDocumentSerialityDetails;
	}

	public Boolean getBooDocumentSeriality() {
		return booDocumentSeriality;
	}

	public void setBooDocumentSeriality(Boolean booDocumentSeriality) {
		this.booDocumentSeriality = booDocumentSeriality;
	}

	public Boolean getBooEdit() {
		return booEdit;
	}

	public void setBooEdit(Boolean booEdit) {
		this.booEdit = booEdit;
	}

	public Boolean getIsdisable() {
		return isdisable;
	}

	public void setIsdisable(Boolean isdisable) {
		this.isdisable = isdisable;
	}

	public Boolean getBooBtnClear() {
		return booBtnClear;
	}

	public void setBooBtnClear(Boolean booBtnClear) {
		this.booBtnClear = booBtnClear;
	}

	private List<CountryMasterDesc> lstofbussinesscountry = new ArrayList<CountryMasterDesc>();
	private List<CompanyMasterDesc> lstofCompanyMasterDesc = new ArrayList<CompanyMasterDesc>();
	private List<Document> lstofDocument = new ArrayList<Document>();
	private List<UserFinancialYear> lstofUserFinancialYear = new ArrayList<UserFinancialYear>();

	private List<DocumentSeriality> lstFromDb = new ArrayList<DocumentSeriality>();
	private List<DocumentSerialityDataTableBean> lstofDataTable = new CopyOnWriteArrayList<DocumentSerialityDataTableBean>();

	DocumentSerialityDataTableBean documentSerialityDataTableBean = null;

	public List<CountryMasterDesc> getLstofbussinesscountry() {
		return lstofbussinesscountry;
	}

	public void setLstofbussinesscountry(List<CountryMasterDesc> lstofbussinesscountry) {
		this.lstofbussinesscountry = lstofbussinesscountry;
	}

	public List<CompanyMasterDesc> getLstofCompanyMasterDesc() {
		return lstofCompanyMasterDesc;
	}

	public void setLstofCompanyMasterDesc(List<CompanyMasterDesc> lstofCompanyMasterDesc) {
		this.lstofCompanyMasterDesc = lstofCompanyMasterDesc;
	}

	public List<Document> getLstofDocument() {
		return lstofDocument;
	}

	public void setLstofDocument(List<Document> lstofDocument) {
		this.lstofDocument = lstofDocument;
	}

	public List<UserFinancialYear> getLstofUserFinancialYear() {
		return lstofUserFinancialYear;
	}

	public void setLstofUserFinancialYear(List<UserFinancialYear> lstofUserFinancialYear) {
		this.lstofUserFinancialYear = lstofUserFinancialYear;
	}

	public List<DocumentSeriality> getLstFromDb() {
		return lstFromDb;
	}

	public void setLstFromDb(List<DocumentSeriality> lstFromDb) {
		this.lstFromDb = lstFromDb;
	}

	public List<DocumentSerialityDataTableBean> getLstofDataTable() {
		return lstofDataTable;
	}

	public void setLstofDataTable(List<DocumentSerialityDataTableBean> lstofDataTable) {
		this.lstofDataTable = lstofDataTable;
	}

	public DocumentSerialityDataTableBean getDocumentSerialityDataTableBean() {
		return documentSerialityDataTableBean;
	}

	public void setDocumentSerialityDataTableBean(DocumentSerialityDataTableBean documentSerialityDataTableBean) {
		this.documentSerialityDataTableBean = documentSerialityDataTableBean;
	}

	/*
	 * method to get the country Name name and country code from dataBase
	 */
	public List<CountryMasterDesc> getCountryNameList() {
		log.info("Entering into getCountryNameList method");
		try {
			lstofbussinesscountry = getGeneralService().getBusinessCountryList(new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"));
			log.info("Exit into getCountryNameList method");
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::getCountryNameList");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return null;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return null;
		}
		return lstofbussinesscountry;
	}

	public List<CompanyMasterDesc> getCompanyNameList() {

		log.info("Entering into getCompanyNameList method");
		try {
			lstofCompanyMasterDesc = getGeneralService().getAllCompanyList(new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"));
			log.info("Exit into getCompanyNameList method");
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::getCompanyNameList");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return null;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return null;
		}

		return lstofCompanyMasterDesc;

	}

	public List<Document> getDocumentNameList() {

		log.info("Entering into getDocumentNameList method");
		try {
			lstofDocument = getDocumentSerialityService().getDocumentList(new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"));
			log.info("Exit into getDocumentNameList method");
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::getDocumentNameList");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return null;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return null;
		}
		return lstofDocument;
	}

	public List<UserFinancialYear> getAllUserFinancialYearList() {
		log.info("Entering into getAllUserFinancialYears method");
		try {
			lstofUserFinancialYear = getDocumentSerialityService().getAllUserFinancialYear(new Date());
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::getAllUserFinancialYearList");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return null;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return null;
		}
		log.info("Exit into getAllUserFinancialYears method");

		return lstofUserFinancialYear;
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void pageNavigation() {
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "documentserialitymaster.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../foreigncurrency/documentserialitymaster.xhtml");
			setBooDocumentSeriality(true);
			setBooDocumentSerialityDetails(false);
			setBooEdit(false);
			setIsdisable(false);
			setBooBtnClear(true);
			lstofDataTable.clear();
			clear();

		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::pageNavigation");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	public void approvePageNavigation() {
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "documentserialityapproval.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../foreigncurrency/documentserialityapproval.xhtml");
			setBooDocumentSeriality(false);
			setBooDocumentSerialityDetails(true);
			approveView();

		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::approvePageNavigation");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	public void approve(DocumentSerialityDataTableBean documentSerialityDataTableBean) {
		if (!(documentSerialityDataTableBean.getModifiedBy() == null ? documentSerialityDataTableBean.getCreatedBy() : documentSerialityDataTableBean.getModifiedBy()).equalsIgnoreCase(sessionStateManage.getUserName())) {
			// if
			// (!documentSerialityDataTableBean.getCreatedBy().equalsIgnoreCase(sessionStateManage.getUserName()))
			// {
			try {

				FacesContext.getCurrentInstance().getExternalContext().redirect("../foreigncurrency/documentserialityapproval.xhtml");
				setBooDocumentSeriality(true);
				setBooDocumentSerialityDetails(false);

				List<Document> documentCodeList = getDocumentSerialityService().getDocumentCode(documentSerialityDataTableBean.getExDocument());

				if (documentCodeList != null) {

					setDocument(documentCodeList.get(0).getDocumentCode());
				}

				setDocumentSerialityId(documentSerialityDataTableBean.getDocumentSerialityId());

				// setDocument(documentSerialityDataTableBean.getExDocument());
				setCountry(documentSerialityDataTableBean.getDocCountry());
				setCompany(documentSerialityDataTableBean.getDocCompany());
				setUserFinancialYear(documentSerialityDataTableBean.getDocFinancialYear());
				setStartNo(documentSerialityDataTableBean.getStartNo());
				setEndNo(documentSerialityDataTableBean.getEndNo());
				setNextNo(documentSerialityDataTableBean.getStartNo());
				setCreatedBy(documentSerialityDataTableBean.getCreatedBy());
				setCreatedDate(documentSerialityDataTableBean.getCreatedDate());

				setModifiedBy(documentSerialityDataTableBean.getModifiedBy());
				setModifiedDate(documentSerialityDataTableBean.getModifiedDate());
				setApprovedBy(documentSerialityDataTableBean.getApprovedBy());
				setApprovedDate(documentSerialityDataTableBean.getApprovedDate());
				setRemarks(documentSerialityDataTableBean.getRemarks());
				setIsActive(documentSerialityDataTableBean.getIsActive());

			} catch (NullPointerException ne) {
				// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				setErrorMessage("Method Name::approve");
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return;
			} catch (Exception exception) {
				// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
				setErrorMessage(exception.getMessage());
				RequestContext.getCurrentInstance().execute("error.show();");
				return;
			}

		} else {
			RequestContext.getCurrentInstance().execute("notValidUser.show();");

		}
	}

	public void approve() {
		try {
			DocumentSeriality documentSeriality = new DocumentSeriality();

			documentSeriality.setDocumentSerialityId(getDocumentSerialityId());

			CountryMaster countryMaster = new CountryMaster();
			countryMaster.setCountryId(getCountry());
			documentSeriality.setDocCountry(countryMaster);

			CompanyMaster companyMaster = new CompanyMaster();
			companyMaster.setCompanyId(getCompany());
			documentSeriality.setDocCompany(companyMaster);

			documentSeriality.setDocFinancialYear(getUserFinancialYear());

			documentSeriality.setExDocument(getDocument());

			documentSeriality.setStartNo(getStartNo());
			documentSeriality.setEndNo(getEndNo());
			documentSeriality.setNextNo(getStartNo());

			documentSeriality.setCreatedBy(getCreatedBy());
			documentSeriality.setCreatedDate(getCreatedDate());
			documentSeriality.setModifiedBy(getModifiedBy());
			documentSeriality.setModifiedDate(getModifiedDate());
			documentSeriality.setApprovedBy(sessionStateManage.getUserName());
			documentSeriality.setApprovedDate(new Date());
			documentSeriality.setRemarks(null);
			documentSeriality.setIsActive(Constants.Yes);

			getDocumentSerialityService().save(documentSeriality);

			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("complete.show();");
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::approve");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	public void clear() {

		setCountry(null);
		setCompany(null);
		setDocument(null);
		setUserFinancialYear(null);
		setStartNo(null);
		setEndNo(null);
		setDocumentSerialityId(null);
		setIsActive(null);
		setModifiedBy(null);
		setModifiedDate(null);
		setApprovedBy(null);
		setApprovedBy(null);
		setApprovedDate(null);
		setCreatedBy(null);
		setRemarks(null);
		setCreatedDate(null);

		setBooEdit(false);
		setIsdisable(false);
		setBooBtnClear(true);

	}

	public void view() {
		lstofDataTable.clear();
		try {
			lstFromDb = getDocumentSerialityService().getAllDocumentSerialityList();

			if (lstFromDb != null) {
				for (DocumentSeriality documentSeriality : lstFromDb) {

					DocumentSerialityDataTableBean documentSerialityDataTableBean = new DocumentSerialityDataTableBean();

					documentSerialityDataTableBean.setDocumentSerialityId(documentSeriality.getDocumentSerialityId());

					documentSerialityDataTableBean.setDocCompany(documentSeriality.getDocCompany().getCompanyId());
					List<CompanyMasterDesc> companyNameList = getDocumentSerialityService().getCompanyList(documentSeriality.getDocCompany().getCompanyId(), sessionStateManage.getLanguageId());
					if (companyNameList != null) {

						documentSerialityDataTableBean.setCompanyName(companyNameList.get(0).getCompanyName());
					}

					documentSerialityDataTableBean.setDocCountry(documentSeriality.getDocCountry().getCountryId());

					List<CountryMasterDesc> countryNameList = getDocumentSerialityService().getCountryListAppCountry(sessionStateManage.getLanguageId(), documentSeriality.getDocCountry().getCountryId());

					if (countryNameList != null) {

						documentSerialityDataTableBean.setCountryName(countryNameList.get(0).getCountryName());
					}

					documentSerialityDataTableBean.setDocFinancialYear(documentSeriality.getDocFinancialYear());

					documentSerialityDataTableBean.setExDocument(documentSeriality.getExDocument());

					documentSerialityDataTableBean.setStartNo(documentSeriality.getStartNo());
					documentSerialityDataTableBean.setEndNo(documentSeriality.getEndNo());
					documentSerialityDataTableBean.setNextNo(documentSeriality.getNextNo());
					documentSerialityDataTableBean.setIsActive(documentSeriality.getIsActive());
					documentSerialityDataTableBean.setCreatedBy(documentSeriality.getCreatedBy());
					documentSerialityDataTableBean.setCreatedDate(documentSeriality.getCreatedDate());
					documentSerialityDataTableBean.setApprovedBy(documentSeriality.getApprovedBy());
					documentSerialityDataTableBean.setApprovedDate(documentSeriality.getApprovedDate());
					if (documentSeriality.getModifiedBy() != null)
						documentSerialityDataTableBean.setModifiedBy(documentSeriality.getModifiedBy());
					if (documentSeriality.getModifiedDate() != null)
						documentSerialityDataTableBean.setModifiedDate(documentSeriality.getModifiedDate());
					// documentSerialityDataTableBean.setIsActive(documentSeriality.getIsActive());

					if (documentSeriality.getIsActive().equalsIgnoreCase(Constants.Yes)) {
						documentSerialityDataTableBean.setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
						documentSerialityDataTableBean.setApprovedBy(documentSeriality.getApprovedBy());
						documentSerialityDataTableBean.setApprovedDate(documentSeriality.getApprovedDate());

						documentSerialityDataTableBean.setBooCheckDelete(false);

					} else if (documentSeriality.getIsActive().equalsIgnoreCase(Constants.D)) {
						documentSerialityDataTableBean.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);

					} else if (documentSeriality.getIsActive().equalsIgnoreCase(Constants.U) && documentSeriality.getModifiedBy() == null && documentSeriality.getModifiedDate() == null && documentSeriality.getApprovedBy() == null && documentSeriality.getRemarks() == null) {
						documentSerialityDataTableBean.setDynamicLabelForActivateDeactivate(Constants.DELETE);

						documentSerialityDataTableBean.setBooCheckDelete(false);

					} else {
						documentSerialityDataTableBean.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);

						documentSerialityDataTableBean.setBooCheckDelete(false);
					}
					// documentSerialityDataTableBean.setDynamicLabelForActivateDeactivate(setreverActiveStatus(documentSeriality.getIsActive()));

					setBooDocumentSeriality(true);
					setBooDocumentSerialityDetails(true);
					setBooEdit(false);
					setIsdisable(false);
					setBooBtnClear(true);
					lstofDataTable.add(documentSerialityDataTableBean);
					clear();

				}
			}
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::view");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	private String setreverActiveStatus(String status) {
		log.info("Entering into setreverActiveStatus method");
		String strStatus = null;
		if (status == null) {
			strStatus = Constants.REMOVE;
		} else if (status.equalsIgnoreCase(Constants.U)) {
			strStatus = Constants.DELETE;
		} else if (status.equalsIgnoreCase(Constants.D)) {
			strStatus = Constants.ACTIVATE;
		} else if (status.equalsIgnoreCase(Constants.Yes)) {
			strStatus = Constants.DEACTIVATE;
		}
		log.info("strStatus " + strStatus);
		log.info("Exit into setreverActiveStatus method");

		return strStatus;
	}

	public void editRecord(DocumentSerialityDataTableBean documentSerialityDataTableBean) {

		try {
			setBooEdit(true);
			setIsdisable(true);
			setBooBtnClear(true);

			List<Document> documentCodeList = getDocumentSerialityService().getDocumentCode(documentSerialityDataTableBean.getExDocument());

			if (documentCodeList != null) {

				setDocument(documentCodeList.get(0).getDocumentCode());
			}

			setDocumentSerialityId(documentSerialityDataTableBean.getDocumentSerialityId());

			// setDocument(documentSerialityDataTableBean.getExDocument());
			setCountry(documentSerialityDataTableBean.getDocCountry());
			setCompany(documentSerialityDataTableBean.getDocCompany());
			setUserFinancialYear(documentSerialityDataTableBean.getDocFinancialYear());
			setStartNo(documentSerialityDataTableBean.getStartNo());
			setEndNo(documentSerialityDataTableBean.getEndNo());
			setNextNo(documentSerialityDataTableBean.getStartNo());
			setCreatedBy(documentSerialityDataTableBean.getCreatedBy());
			setCreatedDate(documentSerialityDataTableBean.getCreatedDate());

			if (documentSerialityDataTableBean.getDocumentSerialityId() == null) {
				setModifiedBy(null);
				setModifiedDate(null);

			} else {
				setModifiedBy(documentSerialityDataTableBean.getModifiedBy());
				setModifiedDate(documentSerialityDataTableBean.getModifiedDate());

			}

			setApprovedBy(documentSerialityDataTableBean.getApprovedBy());
			setApprovedDate(documentSerialityDataTableBean.getApprovedDate());
			setRemarks(documentSerialityDataTableBean.getRemarks());
			setIsActive(documentSerialityDataTableBean.getIsActive());

			lstofDataTable.remove(documentSerialityDataTableBean);
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::editRecord");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}

	}

	private Boolean isExist = false;

	public void duplicateChekingComplaintTypeCode() {
		if (lstofDataTable.size() > 0) {
			for (DocumentSerialityDataTableBean dataTable : lstofDataTable) {
				if (dataTable.getDocCountry().equals(getCountry()) && dataTable.getDocCompany().equals(getCompany()) && dataTable.getExDocument().equals(getDocument()) && dataTable.getDocFinancialYear().equals(getUserFinancialYear())) {
					clear();
					RequestContext.getCurrentInstance().execute("datatable.show();");
					return;
				}
			}
		} else {
			try {
				isExist = getDocumentSerialityService().isExist(getCountry(), getCompany(), getDocument(), getUserFinancialYear(), Constants.Yes);
				if (isExist) {
					clear();
					RequestContext.getCurrentInstance().execute("datatable.show();");
					return;
				}
			} catch (NullPointerException ne) {
				// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				setErrorMessage("Method Name::duplicateChekingComplaintTypeCode");
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return;
			} catch (Exception exception) {
				// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
				setErrorMessage(exception.getMessage());
				RequestContext.getCurrentInstance().execute("error.show();");
				return;
			}
		}

		if (getCountry() != null && getCompany() != null && getDocument() != null && getUserFinancialYear() != null) {

			addRecordDataTable();
		}
	}

	public void addRecordDataTable() {

		DocumentSerialityDataTableBean documentSerialityDataTableBean = new DocumentSerialityDataTableBean();
		try {
			documentSerialityDataTableBean.setDocCompany(getCompany());
			List<CompanyMasterDesc> companyNameList = getDocumentSerialityService().getCompanyList(getCompany(), sessionStateManage.getLanguageId());
			if (companyNameList != null) {

				documentSerialityDataTableBean.setCompanyName(companyNameList.get(0).getCompanyName());
			}

			documentSerialityDataTableBean.setDocCountry(getCountry());

			List<CountryMasterDesc> countryNameList = getDocumentSerialityService().getCountryListAppCountry(sessionStateManage.getLanguageId(), getCountry());

			if (countryNameList != null) {

				documentSerialityDataTableBean.setCountryName(countryNameList.get(0).getCountryName());
			}
			documentSerialityDataTableBean.setDocFinancialYear(getUserFinancialYear());

			documentSerialityDataTableBean.setExDocument(getDocument());
			documentSerialityDataTableBean.setStartNo(getStartNo());
			documentSerialityDataTableBean.setEndNo(getEndNo());
			documentSerialityDataTableBean.setDocumentSerialityId(getDocumentSerialityId());

			if (getDocumentSerialityId() != null) {

				if (getIsActive().equals(Constants.U) && getModifiedBy() == null && getModifiedDate() == null) {
					documentSerialityDataTableBean.setDynamicLabelForActivateDeactivate(Constants.DELETE);
					documentSerialityDataTableBean.setIsActive(Constants.U);
					documentSerialityDataTableBean.setModifiedBy(sessionStateManage.getUserName());
					documentSerialityDataTableBean.setModifiedDate(new Date());
				} else if (getIsActive().equals(Constants.D)) {

					if (getRemarks() != null) {
						documentSerialityDataTableBean.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
						documentSerialityDataTableBean.setIsActive(Constants.U);
						documentSerialityDataTableBean.setModifiedBy(sessionStateManage.getUserName());
						documentSerialityDataTableBean.setModifiedDate(new Date());
					}
				} else if (getIsActive().equals(Constants.Yes) && getApprovedBy() != null && getApprovedDate() != null) {

					documentSerialityDataTableBean.setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
					documentSerialityDataTableBean.setIsActive(Constants.U);
					documentSerialityDataTableBean.setModifiedBy(sessionStateManage.getUserName());
					documentSerialityDataTableBean.setModifiedDate(new Date());

				} else {
					if (getIsActive().equals(Constants.U) && getModifiedBy() != null && getModifiedDate() != null) {
						documentSerialityDataTableBean.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
						documentSerialityDataTableBean.setIsActive(Constants.U);
						documentSerialityDataTableBean.setModifiedBy(sessionStateManage.getUserName());
						documentSerialityDataTableBean.setModifiedDate(new Date());
					}
				}

			} else {

				documentSerialityDataTableBean.setDynamicLabelForActivateDeactivate("REMOVE");
				documentSerialityDataTableBean.setCreatedBy(sessionStateManage.getUserName());
				documentSerialityDataTableBean.setCreatedDate(new Date());
				documentSerialityDataTableBean.setIsActive(Constants.U);
			}
			setBooDocumentSeriality(true);
			setBooDocumentSerialityDetails(true);
			setBooEdit(false);
			setIsdisable(false);
			setBooBtnClear(true);

			lstofDataTable.add(documentSerialityDataTableBean);
			clear();
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::addRecordDataTable");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	public void save() {

		for (DocumentSerialityDataTableBean documentSerialityDataTableBean : lstofDataTable) {

			DocumentSeriality documentSeriality = new DocumentSeriality();
			try {

				documentSeriality.setDocumentSerialityId(documentSerialityDataTableBean.getDocumentSerialityId());

				CountryMaster countryMaster = new CountryMaster();
				countryMaster.setCountryId(documentSerialityDataTableBean.getDocCountry());
				documentSeriality.setDocCountry(countryMaster);

				CompanyMaster companyMaster = new CompanyMaster();
				companyMaster.setCompanyId(documentSerialityDataTableBean.getDocCompany());
				documentSeriality.setDocCompany(companyMaster);

				documentSeriality.setDocFinancialYear(documentSerialityDataTableBean.getDocFinancialYear());

				documentSeriality.setExDocument(documentSerialityDataTableBean.getExDocument());

				documentSeriality.setStartNo(documentSerialityDataTableBean.getStartNo());
				documentSeriality.setEndNo(documentSerialityDataTableBean.getEndNo());
				documentSeriality.setNextNo(documentSerialityDataTableBean.getStartNo());

				documentSeriality.setCreatedBy(documentSerialityDataTableBean.getCreatedBy());
				documentSeriality.setCreatedDate(documentSerialityDataTableBean.getCreatedDate());
				documentSeriality.setModifiedBy(documentSerialityDataTableBean.getModifiedBy());
				documentSeriality.setModifiedDate(documentSerialityDataTableBean.getModifiedDate());
				documentSeriality.setApprovedBy(documentSerialityDataTableBean.getApprovedBy());
				documentSeriality.setApprovedDate(documentSerialityDataTableBean.getApprovedDate());
				documentSeriality.setRemarks(documentSerialityDataTableBean.getRemarks());
				documentSeriality.setIsActive(documentSerialityDataTableBean.getIsActive());

				getDocumentSerialityService().save(documentSeriality);

				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("complete.show();");
			} catch (NullPointerException ne) {
				// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				setErrorMessage("Method Name::save");
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return;
			} catch (Exception exception) {
				// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
				setErrorMessage(exception.getMessage());
				RequestContext.getCurrentInstance().execute("error.show();");
				return;
			}
		}

	}

	public void clickOnOK() {
		try {

			FacesContext.getCurrentInstance().getExternalContext().redirect("../foreigncurrency/documentserialitymaster.xhtml");
			setBooDocumentSeriality(true);
			setBooDocumentSerialityDetails(false);
			setBooEdit(false);
			setIsdisable(false);
			setBooBtnClear(true);
			lstofDataTable.clear();
			clear();

		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::clickOnOK");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	public void approveOK() {
		try {

			FacesContext.getCurrentInstance().getExternalContext().redirect("../foreigncurrency/documentserialityapproval.xhtml");
			setBooDocumentSeriality(false);
			setBooDocumentSerialityDetails(true);
			approveView();

		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::approveOK");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	public void approveView() {
		lstofDataTable.clear();
		try {
			lstFromDb = getDocumentSerialityService().getAllDocumentSerialityList();

			if (lstFromDb != null) {
				for (DocumentSeriality documentSeriality : lstFromDb) {

					if (documentSeriality.getIsActive().equalsIgnoreCase(Constants.U)) {
						DocumentSerialityDataTableBean documentSerialityDataTableBean = new DocumentSerialityDataTableBean();

						documentSerialityDataTableBean.setDocumentSerialityId(documentSeriality.getDocumentSerialityId());
						documentSerialityDataTableBean.setDocCompany(documentSeriality.getDocCompany().getCompanyId());
						List<CompanyMasterDesc> companyNameList = getDocumentSerialityService().getCompanyList(sessionStateManage.getLanguageId(), documentSeriality.getDocCompany().getCompanyId());
						if (companyNameList != null) {

							documentSerialityDataTableBean.setCompanyName(companyNameList.get(0).getCompanyName());
						}

						documentSerialityDataTableBean.setDocCountry(documentSeriality.getDocCountry().getCountryId());

						List<CountryMasterDesc> countryNameList = getDocumentSerialityService().getCountryListAppCountry(sessionStateManage.getLanguageId(), documentSeriality.getDocCountry().getCountryId());

						if (countryNameList != null) {

							documentSerialityDataTableBean.setCountryName(countryNameList.get(0).getCountryName());
						}

						documentSerialityDataTableBean.setDocFinancialYear(documentSeriality.getDocFinancialYear());

						documentSerialityDataTableBean.setExDocument(documentSeriality.getExDocument());

						documentSerialityDataTableBean.setStartNo(documentSeriality.getStartNo());
						documentSerialityDataTableBean.setEndNo(documentSeriality.getEndNo());
						documentSerialityDataTableBean.setNextNo(documentSeriality.getNextNo());
						documentSerialityDataTableBean.setIsActive(documentSeriality.getIsActive());
						documentSerialityDataTableBean.setCreatedBy(documentSeriality.getCreatedBy());
						documentSerialityDataTableBean.setCreatedDate(documentSeriality.getCreatedDate());
						// documentSerialityDataTableBean.setDynamicLabelForActivateDeactivate(setreverActiveStatus(null));

						lstofDataTable.add(documentSerialityDataTableBean);
					}

				}
			}
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::approveView");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}

	}

	public void exit() {
		log.info("Enter in exit method ");

		try {
			List<RoleMaster> rolList = generalService.getRoleList(new BigDecimal(sessionStateManage.getRoleId()));

			if (rolList.get(0).getRoleName().equalsIgnoreCase(Constants.USER_ROLE_MANAGER)) {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
				lstofDataTable.clear();
				clear();
			} else {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");

				clear();
				lstofDataTable.clear();
			}

			setBooEdit(false);
			setIsdisable(false);
			setBooBtnClear(true);
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::exit");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}

		log.info("Exit from exit method ");
	}

	/*
	 * public void clickApproveOK(){ try {
	 * 
	 * FacesContext.getCurrentInstance().getExternalContext().redirect(
	 * "../foreigncurrency/documentserialityapproval.xhtml");
	 * setBooDocumentSeriality(false); setBooDocumentSerialityDetails(true);
	 * approveView();
	 * 
	 * } catch (Exception e) { // TODO: handle exception } }
	 */

	public void remarkCancel() {

	}

	public void remove(DocumentSerialityDataTableBean documentSerialityDataTableBean) {

		if (documentSerialityDataTableBean.getModifiedBy() == null && documentSerialityDataTableBean.getApprovedBy() == null && documentSerialityDataTableBean.getRemarks() == null && documentSerialityDataTableBean.getIsActive().equalsIgnoreCase(Constants.U)) {
			try {
				delete(documentSerialityDataTableBean);
				lstofDataTable.remove(documentSerialityDataTableBean);
			} catch (NullPointerException ne) {
				// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				setErrorMessage("Method Name::remove");
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return;
			} catch (Exception exception) {
				// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
				setErrorMessage(exception.getMessage());
				RequestContext.getCurrentInstance().execute("error.show();");
				return;
			}

			// RequestContext.getCurrentInstance().execute("delete.show();");

			return;
		}

	}

	public void delete(DocumentSerialityDataTableBean documentSerialityDataTableBean) {
		try {
			getDocumentSerialityService().delete(documentSerialityDataTableBean.getDocumentSerialityId());
			RequestContext.getCurrentInstance().execute("delete.show();");
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::delete");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	public void checkStatusType(DocumentSerialityDataTableBean documentSerialityDataTableBean) throws IOException {

		if (documentSerialityDataTableBean.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.PENDING_FOR_APPROVE)) {
			// setBooEdit(t);
			RequestContext.getCurrentInstance().execute("pending.show();");
			return;
		} else if (documentSerialityDataTableBean.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)) {
			// documentSerialityDataTableBean.setRemarkCheck(true);
			documentSerialityDataTableBean.setRemarksCheck(true);

			setRemarks("");
			setApprovedBy(documentSerialityDataTableBean.getApprovedBy());
			setApprovedDate(documentSerialityDataTableBean.getApprovedDate());
			RequestContext.getCurrentInstance().execute("remarks.show();");
			return;
		} else if (documentSerialityDataTableBean.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DELETE)) {
			documentSerialityDataTableBean.setBooCheckDelete(true);
			RequestContext.getCurrentInstance().execute("permanentDelete.show();");
			return;
		} else if (documentSerialityDataTableBean.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.ACTIVATE)) {
			documentSerialityDataTableBean.setActivateRecordCheck(true);
			RequestContext.getCurrentInstance().execute("activateRecord.show();");
			return;
		} else if (documentSerialityDataTableBean.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.REMOVE)) {
			lstofDataTable.remove(documentSerialityDataTableBean);

		}
	}

	public void clearRemarks() {
		setRemarks(null);
	}

	public void confirmPermanentDelete() {
		if (lstofDataTable.size() > 0) {
			for (DocumentSerialityDataTableBean documentSerialityDataTableBean : lstofDataTable) {
				if (documentSerialityDataTableBean.getBooCheckDelete() != null) {
					if (documentSerialityDataTableBean.getBooCheckDelete().equals(true)) {
						delete(documentSerialityDataTableBean);
						lstofDataTable.remove(documentSerialityDataTableBean);
						cancel();
						view();
					}
				}
			}
		}

	}

	public void activateRecord() {
		try {
			if (lstofDataTable.size() > 0) {
				for (DocumentSerialityDataTableBean documentSerialityDataTableBean : lstofDataTable) {
					if (documentSerialityDataTableBean.getActivateRecordCheck() != null) {
						if (documentSerialityDataTableBean.getActivateRecordCheck().equals(true)) {

							getDocumentSerialityService().activateRecord(documentSerialityDataTableBean.getDocumentSerialityId(), sessionStateManage.getUserName());
							cancel();
							view();

							return;
						}
					}
				}
			}
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::activateRecord");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}

	}

	public void remarkSelectedRecord() throws IOException {

		for (DocumentSerialityDataTableBean documentSerialityDataTableBean : lstofDataTable) {
			try {
				if (documentSerialityDataTableBean.getRemarksCheck().equals(true)) {
					if (!getRemarks().equals("")) {
						documentSerialityDataTableBean.setRemarks(getRemarks());
						documentSerialityDataTableBean.setApprovedBy(null);
						documentSerialityDataTableBean.setApprovedDate(null);
						documentSerialityDataTableBean.setRemarksCheck(true);
						update(documentSerialityDataTableBean);
						setBooDocumentSeriality(true);
						setBooDocumentSerialityDetails(false);
						setBooEdit(false);
						setIsdisable(false);
						setBooBtnClear(false);
						view();
						cancel();
					} else {
						RequestContext.getCurrentInstance().execute("remarksEmpty.show();");
						return;

					}
				}
			} catch (NullPointerException ne) {
				// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				setErrorMessage("Method Name::remarkSelectedRecord");
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return;
			} catch (Exception exception) {
				// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
				setErrorMessage(exception.getMessage());
				RequestContext.getCurrentInstance().execute("error.show();");
				return;
			}
		}

	}

	public void update(DocumentSerialityDataTableBean documentSerialityDataTableBean) throws IOException {
		DocumentSeriality documentSeriality = new DocumentSeriality();
		try {

			documentSeriality.setDocumentSerialityId(documentSerialityDataTableBean.getDocumentSerialityId());

			CountryMaster countryMaster = new CountryMaster();
			countryMaster.setCountryId(documentSerialityDataTableBean.getDocCountry());
			documentSeriality.setDocCountry(countryMaster);

			CompanyMaster companyMaster = new CompanyMaster();
			companyMaster.setCompanyId(documentSerialityDataTableBean.getDocCompany());
			documentSeriality.setDocCompany(companyMaster);

			// UserFinancialYear userFinancialYear = new UserFinancialYear();
			// userFinancialYear.setFinancialYearID(documentSerialityDataTableBean.getDocFinancialYear());
			documentSeriality.setDocFinancialYear(documentSerialityDataTableBean.getDocFinancialYear());

			documentSeriality.setExDocument(documentSerialityDataTableBean.getExDocument());
			documentSeriality.setExDocument(documentSerialityDataTableBean.getExDocument());

			documentSeriality.setStartNo(documentSerialityDataTableBean.getStartNo());
			documentSeriality.setEndNo(documentSerialityDataTableBean.getEndNo());
			documentSeriality.setNextNo(documentSerialityDataTableBean.getStartNo());

			documentSeriality.setCreatedBy(documentSerialityDataTableBean.getCreatedBy());
			documentSeriality.setCreatedDate(documentSerialityDataTableBean.getCreatedDate());
			documentSeriality.setModifiedBy(sessionStateManage.getUserName());
			documentSeriality.setModifiedDate(new Date());
			documentSeriality.setApprovedBy(null);
			documentSeriality.setApprovedDate(null);
			documentSeriality.setRemarks(documentSerialityDataTableBean.getRemarks());
			documentSeriality.setIsActive(Constants.D);
			documentSeriality.setCreatedBy(documentSerialityDataTableBean.getCreatedBy());
			documentSeriality.setCreatedDate(documentSerialityDataTableBean.getCreatedDate());

			getDocumentSerialityService().save(documentSeriality);

		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::update");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	public void checkMin() {
		if (getEndNo() != null && getStartNo() != null) {

			if (getEndNo().intValue() < getStartNo().intValue()) {
				setStartNo(null);
				// set
				RequestContext.getCurrentInstance().execute("maxgreater.show();");
			}

		}
	}

	public void checkMax() {

		if (getEndNo() != null && getStartNo() != null) {

			if (getEndNo().intValue() < getStartNo().intValue()) {
				// setFromBankBranch(null);
				setEndNo(null);

				RequestContext.getCurrentInstance().execute("maxgreater.show();");
			}

		}
	}

	public void cancel() {
		setRemarks("");
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../foreigncurrency/documentserialitymaster.xhtml");

		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::cancel");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}
}
