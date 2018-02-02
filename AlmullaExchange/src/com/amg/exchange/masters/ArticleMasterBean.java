package com.amg.exchange.masters;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.LanguageType;
import com.amg.exchange.complaint.bean.ComplaintTypeDataTable;
import com.amg.exchange.registration.service.IArticleMasterservice;
import com.amg.exchange.treasury.model.ArticleMaster;
import com.amg.exchange.treasury.model.ArticleMasterDesc;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;
@Component("articlemaster")
@Scope("session")
public class ArticleMasterBean<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(ArticleMasterBean.class);
	private BigDecimal articleId;
	private String articleCode;
	private String articleName;
	private String englishArticleDescription;
	private String localArticleDescription;
	private BigDecimal companyId;
	private BigDecimal countryId;
	private Date createDate;
	private String createdBy;
	private String customerType;
	private Date modifiedDate;
	private String modifiedBy;
	private String isActive;
	private Date approvedDate;
	private String approvedBy;
	private Boolean booArticleCodeReadOnly;
	// desc Table
	private BigDecimal articleDescId;
	private BigDecimal articleEnglishDescId;
	private BigDecimal articleLocalDescId;
	private BigDecimal englishLanguageId;
	private BigDecimal localLanguageId;
	private SessionStateManage session = new SessionStateManage();
	private Boolean editFalg;
	private Boolean booDatatableEnabled;
	private Boolean booSubmitButton;
	private String remarks;
	private String dynamicLabelForActivateDeactivate;
	private boolean hideEdit;
	private boolean ispopulate;
	private List<ArticleMasterDataTable> articleMasterDataTableList = new ArrayList<ArticleMasterDataTable>();
	private List<ArticleMasterDesc> viewList = new ArrayList<ArticleMasterDesc>();
	private List<ArticleMaster> viewMasterList = new ArrayList<ArticleMaster>();
	ArticleMaster articleMaster = null;
	ArticleMasterDesc articleMasterDesc = null;
	@Autowired
	IArticleMasterservice iArticleMasterservice;
	
	private String errorMessage;
	
	
	

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public boolean isIspopulate() {
		return ispopulate;
	}

	public void setIspopulate(boolean ispopulate) {
		this.ispopulate = ispopulate;
	}

	public Boolean getBooArticleCodeReadOnly() {
		return booArticleCodeReadOnly;
	}

	public boolean isHideEdit() {
		return hideEdit;
	}

	public void setHideEdit(boolean hideEdit) {
		this.hideEdit = hideEdit;
	}

	public void setBooArticleCodeReadOnly(Boolean booArticleCodeReadOnly) {
		this.booArticleCodeReadOnly = booArticleCodeReadOnly;
	}

	public BigDecimal getArticleEnglishDescId() {
		return articleEnglishDescId;
	}

	public void setArticleEnglishDescId(BigDecimal articleEnglishDescId) {
		this.articleEnglishDescId = articleEnglishDescId;
	}

	public BigDecimal getArticleLocalDescId() {
		return articleLocalDescId;
	}

	public void setArticleLocalDescId(BigDecimal articleLocalDescId) {
		this.articleLocalDescId = articleLocalDescId;
	}

	public BigDecimal getEnglishLanguageId() {
		return englishLanguageId;
	}

	public void setEnglishLanguageId(BigDecimal englishLanguageId) {
		this.englishLanguageId = englishLanguageId;
	}

	public BigDecimal getLocalLanguageId() {
		return localLanguageId;
	}

	public void setLocalLanguageId(BigDecimal localLanguageId) {
		this.localLanguageId = localLanguageId;
	}

	public String getArticleName() {
		return articleName;
	}

	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}

	public BigDecimal getArticleDescId() {
		return articleDescId;
	}

	public void setArticleDescId(BigDecimal articleDescId) {
		this.articleDescId = articleDescId;
	}

	public String getArticleCode() {
		return articleCode;
	}

	public void setArticleCode(String articleCode) {
		this.articleCode = articleCode;
	}

	public String getEnglishArticleDescription() {
		return englishArticleDescription;
	}

	public void setEnglishArticleDescription(String englishArticleDescription) {
		this.englishArticleDescription = englishArticleDescription;
	}

	public String getLocalArticleDescription() {
		return localArticleDescription;
	}

	public void setLocalArticleDescription(String localArticleDescription) {
		this.localArticleDescription = localArticleDescription;
	}

	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}

	public void setDynamicLabelForActivateDeactivate(String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Boolean getBooSubmitButton() {
		return booSubmitButton;
	}

	public void setBooSubmitButton(Boolean booSubmitButton) {
		this.booSubmitButton = booSubmitButton;
	}

	public List<ArticleMasterDataTable> getArticleMasterDataTableList() {
		return articleMasterDataTableList;
	}

	public void setArticleMasterDataTableList(List<ArticleMasterDataTable> articleMasterDataTableList) {
		this.articleMasterDataTableList = articleMasterDataTableList;
	}

	public Boolean getBooDatatableEnabled() {
		return booDatatableEnabled;
	}

	public void setBooDatatableEnabled(Boolean booDatatableEnabled) {
		this.booDatatableEnabled = booDatatableEnabled;
	}

	public Boolean getEditFalg() {
		return editFalg;
	}

	public void setEditFalg(Boolean editFalg) {
		this.editFalg = editFalg;
	}

	public BigDecimal getArticleId() {
		return articleId;
	}

	public void setArticleId(BigDecimal articleId) {
		this.articleId = articleId;
	}

	public BigDecimal getCompanyId() {
		return companyId;
	}

	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}

	public BigDecimal getCountryId() {
		return countryId;
	}

	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getIsActive() {
		return isActive;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
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

	public void add() {
		LOGGER.info("Entering into add method");
		boolean alreadyDT = false;
		// Data table duplicatecheck
		if (articleMasterDataTableList.size() != 0) {
			for (ArticleMasterDataTable rticleMasterDataTable : articleMasterDataTableList) {
				// Data table duplicate check - article code check
				if (rticleMasterDataTable.getArticleCode() != null && rticleMasterDataTable.getArticleCode().equals(getArticleCode())) {
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("articleCodeExist.show();");
					alreadyDT = true;
					break;
				}
				// Data table duplicate check - English description code check
				if (rticleMasterDataTable.getEnglishArticleDescription() != null && rticleMasterDataTable.getEnglishArticleDescription().equalsIgnoreCase(getEnglishArticleDescription())) {
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("EnglishDescriptionExist.show();");
					alreadyDT = true;
					setEnglishArticleDescription(null);
					setLocalArticleDescription(null);
					break;
				}
				// Data table duplicate check - local description code check
				if (rticleMasterDataTable.getLocalArticleDescription() != null && rticleMasterDataTable.getLocalArticleDescription().equalsIgnoreCase(getLocalArticleDescription())) {
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("LocalDescriptionExist.show();");
					setEnglishArticleDescription(null);
					setLocalArticleDescription(null);
					alreadyDT = true;
					break;
				}
			}
		}
		boolean alreadyDB = false;
		// Data table duplicatecheck
		if (!alreadyDT) {
			if (getArticleId() == null) {
				ArticleMaster exist = iArticleMasterservice.viewByCode(getArticleCode());
				if (exist != null && exist.getArticleId() != null) {
					alreadyDB = true;
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("articleCodeExist.show();");
				} else {
					List<ArticleMasterDesc> local = iArticleMasterservice.checkDesciption(getLocalArticleDescription());
					List<ArticleMasterDesc> english = iArticleMasterservice.checkDesciption(getEnglishArticleDescription());
					if (local != null && local.size() > 0) {
						alreadyDB = true;
						RequestContext context = RequestContext.getCurrentInstance();
						context.execute("EnglishDescriptionExist.show();");
						setEnglishArticleDescription(null);
						setLocalArticleDescription(null);
					} else if (english != null && english.size() > 0) {
						alreadyDB = true;
						RequestContext context = RequestContext.getCurrentInstance();
						context.execute("LocalDescriptionExist.show();");
						setEnglishArticleDescription(null);
						setLocalArticleDescription(null);
					}
				}
			} else {
				alreadyDB = false;
			}
		}
		if (!alreadyDT && !alreadyDB) {
			ArticleMasterDataTable dataTable = new ArticleMasterDataTable();
		
			dataTable.setArticleId(getArticleId());
			dataTable.setCountryId(session.getCountryId());
			dataTable.setCompanyId(session.getCompanyId());
			LOGGER.info("code" + getArticleCode());
			LOGGER.info("English" + getEnglishArticleDescription());
			dataTable.setCustomerType(getCustomerType());
			dataTable.setEnglishArticleDescription(getEnglishArticleDescription());
			dataTable.setLocalArticleDescription(getLocalArticleDescription());
			dataTable.setArticleName(getEnglishArticleDescription());
			dataTable.setArticleCode(getArticleCode());
			dataTable.setDynamicLabelForActivateDeactivate(setreverActiveStatus(getIsActive()));
			// In first Time
			if (!editFalg && !ispopulate) {
				dataTable.setEnglishLanguageId(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID ));
				dataTable.setLocalLanguageId((new BigDecimal(Constants.ARABIC_LANGUAGE_ID)));
				dataTable.setCreatedBy(session.getUserName());
				dataTable.setCreateDate(new Timestamp(System.currentTimeMillis()));
				dataTable.setIsActive(Constants.U);
				dataTable.setDynamicLabelForActivateDeactivate(setreverActiveStatus(null));
			} else {
				dataTable.setEnglishLanguageId(getEnglishLanguageId());
				dataTable.setLocalLanguageId(getLocalLanguageId());
				dataTable.setCreatedBy(getCreatedBy());
				dataTable.setCreateDate(getCreateDate());
				dataTable.setIsActive(getIsActive());
				dataTable.setDynamicLabelForActivateDeactivate(setreverActiveStatus(getIsActive()));
			}
			dataTable.setModifiedBy(getModifiedBy());
			dataTable.setModifiedDate(getModifiedDate());
			dataTable.setApprovedDate(getApprovedDate());
			dataTable.setApprovedBy(getApprovedBy());
			dataTable.setRemarks(getRemarks());
			// dataTable.setArticleDescId(getArticleDescId());
			dataTable.setArticleEnglishDescId(getArticleEnglishDescId());
			dataTable.setArticleLocalDescId(getArticleLocalDescId());
			LOGGER.info("Entered value is ");
			LOGGER.info(dataTable);
			articleMasterDataTableList.add(dataTable);
			setBooDatatableEnabled(true);
			setBooSubmitButton(false);
			// setBooArticleCodeReadOnly(false);
			clearAll();
		}
		setHideEdit(false);
		LOGGER.info("Exit into add method");
	}

	public void save() {
		LOGGER.info("Entering into save method");
		ArticleMaster saveArticleMaster = null;
		ArticleMasterDesc articleMasterDesc = null;
		for (ArticleMasterDataTable dataTable : articleMasterDataTableList) {
			try {
			saveArticleMaster = null;
			articleMasterDesc = null;
			LOGGER.info("Input values *************************************************");
			LOGGER.info(dataTable);
			LOGGER.info("Input values *************************************************");
			saveArticleMaster = new ArticleMaster();
			saveArticleMaster.setArticleId(dataTable.getArticleId());
			/*saveArticleMaster.setArticleName(dataTable.getEnglishArticleDescription());*/
			CountryMaster fsCountryMaster = new CountryMaster();
			fsCountryMaster.setCountryId(dataTable.getCountryId());
			saveArticleMaster.setFsCountryMaster(fsCountryMaster);
			CompanyMaster fsCompanyMaster = new CompanyMaster();
			fsCompanyMaster.setCompanyId(dataTable.getCompanyId());
			saveArticleMaster.setFsCompanyMaster(fsCompanyMaster);
			saveArticleMaster.setCreator(dataTable.getCreatedBy());
			saveArticleMaster.setModifier(dataTable.getModifiedBy());
			saveArticleMaster.setCreateDate(dataTable.getCreateDate());
			saveArticleMaster.setUpdateDate(dataTable.getModifiedDate());
			saveArticleMaster.setCustomerType(updatedCustomerType(dataTable.getCustomerType()));
			saveArticleMaster.setApprovedBy(dataTable.getApprovedBy());
			saveArticleMaster.setApprovedDate(dataTable.getApprovedDate());
			saveArticleMaster.setRemarks(dataTable.getRemarks());
			saveArticleMaster.setIsActive(dataTable.getIsActive());
			saveArticleMaster.setArticleCode(dataTable.getArticleCode());
			
			ArticleMasterDesc articleMasterEngDesc = new ArticleMasterDesc();

			LanguageType englishLanguageType = new LanguageType();
			englishLanguageType.setLanguageId(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID));
			articleMasterEngDesc.setLanguageType(englishLanguageType);
			articleMasterEngDesc.setArticleeDescription(dataTable.getEnglishArticleDescription());
			
			articleMasterEngDesc.setArticleDescId(dataTable.getArticleEnglishDescId());
			articleMasterEngDesc.setArticleMaster(saveArticleMaster);

		
			ArticleMasterDesc articleMasterArbDesc = new ArticleMasterDesc();
			
			LanguageType arbicLanguageType = new LanguageType();
			arbicLanguageType.setLanguageId(new BigDecimal(Constants.ARABIC_LANGUAGE_ID));
			articleMasterArbDesc.setLanguageType(arbicLanguageType);
			articleMasterArbDesc.setArticleeDescription(dataTable.getLocalArticleDescription());
			articleMasterArbDesc.setArticleDescId(dataTable.getArticleLocalDescId());
			articleMasterArbDesc.setArticleMaster(saveArticleMaster); 
			
			Set<ArticleMasterDesc>  desc  = new HashSet<ArticleMasterDesc>();
			desc.add(articleMasterEngDesc);
			desc.add(articleMasterArbDesc);
			
		
			
			
				iArticleMasterservice.saveOrUpdate(saveArticleMaster, desc);
				
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("succsses.show();");
			}catch (NullPointerException ne) {
				// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				setErrorMessage("Method Name::save");
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return;
			} catch (Exception e) {
				LOGGER.info("Exception occured while insert data in ArticleMasterBean" + e);
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("csp.show();");
				setErrmsg(e.getMessage());
			}
		}
		LOGGER.info("Exit into save method");
	}

	private String errmsg;
	
	
	
	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	
	public void clearAll() {
		setArticleId(null);
		setCompanyId(null);
		setCountryId(null);
		setCreateDate(null);
		setCreatedBy(null);
		setCustomerType(null);
		setModifiedBy(null);
		setModifiedDate(null);
		setIsActive(null);
		setApprovedDate(null);
		setApprovedBy(null);
		setArticleCode(null);
		setLocalArticleDescription(null);
		setEnglishArticleDescription(null);
		setRemarks(null);
		setEnglishArticleDescription(null);
		setLocalArticleDescription(null);
		setLocalLanguageId(null);
		setEnglishLanguageId(null);
		setBooArticleCodeReadOnly(null);
		setArticleEnglishDescId(null);
		setArticleLocalDescId(null);
		ispopulate = false;
	}

	public void checkStatus(ArticleMasterDataTable articleMasterDataTable) {
		LOGGER.info("Entering into checkStatus method");
		if (articleMasterDataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase("DeActivate")) {
			articleMasterDataTable.setRemarksCheck(true);
			LOGGER.info("Approved By" + articleMasterDataTable.getApprovedBy());
			LOGGER.info("Approved Date" + articleMasterDataTable.getApprovedDate());
			setApprovedBy(articleMasterDataTable.getApprovedBy());
			setApprovedDate(articleMasterDataTable.getApprovedDate());
			RequestContext.getCurrentInstance().execute("remarksMsg.show();");
		}else if(articleMasterDataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase("PENDING FOR APPROVE")){
			RequestContext.getCurrentInstance().execute("pending.show();");
			}else if(articleMasterDataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase("Activate")){
				articleMasterDataTable.setActivateRecordCheck(true);
				RequestContext.getCurrentInstance().execute("activateRecord.show();");
				}else {
			removefromDataTable(articleMasterDataTable);
		}
		LOGGER.info("Exit into checkStatus method");
	}

	public void removefromDataTable(ArticleMasterDataTable dataTable) {
		LOGGER.info("Entering into removefromDataTable method");
		
		if ((dataTable.getDynamicLabelForActivateDeactivate() != null && (dataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DELETE)) || dataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.REMOVE))) {
			if (dataTable.getArticleId() != null) {
				articleMaster = new ArticleMaster();
				articleMaster.setArticleId(dataTable.getArticleId());
				if (dataTable.getModifiedBy() == null && dataTable.getModifiedDate() == null && dataTable.getApprovedBy() == null && dataTable.getApprovedDate() == null && dataTable.getRemarks() == null) {
					articleMasterDataTableList.remove(dataTable);
					try {
						List<ArticleMasterDesc> list = iArticleMasterservice.viewById(articleMaster.getArticleId());
						for (ArticleMasterDesc articleMasterDesc : list) {
							LOGGER.info(articleMasterDesc.getArticleDescId());
							iArticleMasterservice.deleteDesc(articleMasterDesc);
						}
						iArticleMasterservice.delete(articleMaster);
						articleMasterDesc = new ArticleMasterDesc();
						RequestContext.getCurrentInstance().execute("deleteSuccess.show();");
					}catch (NullPointerException ne) {
						// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
						setErrorMessage("Method Name::removefromDataTable");
						RequestContext.getCurrentInstance().execute("nullPointerId.show();");
						return;
					} catch (Exception e) {
						LOGGER.info("Exception occured while deleting the record " + e);
						RequestContext.getCurrentInstance().execute("deleteFailure.show();");
					}
				} else {/*
					LOGGER.info("Input values *************************************************");
					LOGGER.info(dataTable);
					LOGGER.info("Input values *************************************************");
					articleMaster.setArticleName(dataTable.getEnglishArticleDescription());
					CountryMaster fsCountryMaster = new CountryMaster();
					fsCountryMaster.setCountryId(dataTable.getCountryId());
					articleMaster.setFsCountryMaster(fsCountryMaster);
					CompanyMaster fsCompanyMaster = new CompanyMaster();
					fsCompanyMaster.setCompanyId(dataTable.getCompanyId());
					articleMaster.setFsCompanyMaster(fsCompanyMaster);
					articleMaster.setCreator(dataTable.getCreatedBy());
					articleMaster.setModifier(session.getUserName());
					articleMaster.setCreateDate(dataTable.getCreateDate());
					articleMaster.setUpdateDate(new Date());
					articleMaster.setCustomerType(updatedCustomerType(dataTable.getCustomerType()));
					articleMaster.setApprovedBy(dataTable.getApprovedBy());
					articleMaster.setApprovedDate(dataTable.getApprovedDate());
					articleMaster.setRemarks(dataTable.getRemarks());
					if (dataTable.getIsActive().equalsIgnoreCase(Constants.U)) {
						articleMaster.setIsActive(dataTable.getIsActive());
						articleMaster.setRemarks(null);
						articleMaster.setApprovedBy(null);
						articleMaster.setApprovedDate(null);
					} else {
						articleMaster.setIsActive(Constants.D);
					}
					articleMaster.setArticleCode(dataTable.getArticleCode());
					try {
						iArticleMasterservice.save(articleMaster);
						for (int i = 0; i < 2; i++) {
							articleMasterDesc = new ArticleMasterDesc();
							articleMasterDesc.setArticleDescId(getArticleDescId());
							articleMasterDesc.setArticleMaster(articleMaster);
							if (i == 0) {
								articleMasterDesc.setArticleeDescription(dataTable.getEnglishArticleDescription());
								LanguageType ltype = new LanguageType();
								ltype.setLanguageId(dataTable.getEnglishLanguageId());
								articleMasterDesc.setLanguageType(ltype);
								articleMasterDesc.setArticleDescId(dataTable.getArticleEnglishDescId());
							}
							if (i == 1) {
								LanguageType ltype = new LanguageType();
								ltype.setLanguageId(dataTable.getLocalLanguageId());
								articleMasterDesc.setLanguageType(ltype);
								articleMasterDesc.setArticleeDescription(dataTable.getLocalArticleDescription());
								articleMasterDesc.setArticleDescId(dataTable.getArticleLocalDescId());
							}
							iArticleMasterservice.saveDescription(articleMasterDesc);
						}
						articleMasterDataTableList.remove(dataTable);
					} catch (Exception e) {
						LOGGER.info("Exception occured while insert data in ArticleMasterBean" + e);
						RequestContext context = RequestContext.getCurrentInstance();
						context.execute("failure.show();");
					}
				*/
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("alreadymodifed.show();");
				
				
				}
			} else {
				articleMasterDataTableList.remove(dataTable);
				if (articleMasterDataTableList.size() == 0) {
					setBooDatatableEnabled(false);
					clearAll();
				}
			}
		} else if ((dataTable.getDynamicLabelForActivateDeactivate() != null && (dataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.ACTIVATE)) || dataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE))) {
			try {
			ArticleMaster saveArticleMaster = null;
			ArticleMasterDesc articleMasterDesc = null;
			LOGGER.info("Input values *************************************************");
			LOGGER.info(dataTable);
			LOGGER.info("Input values *************************************************");
			saveArticleMaster = new ArticleMaster();
			saveArticleMaster.setArticleId(dataTable.getArticleId());
			/*saveArticleMaster.setArticleName(dataTable.getEnglishArticleDescription());*/
			CountryMaster fsCountryMaster = new CountryMaster();
			fsCountryMaster.setCountryId(dataTable.getCountryId());
			saveArticleMaster.setFsCountryMaster(fsCountryMaster);
			CompanyMaster fsCompanyMaster = new CompanyMaster();
			fsCompanyMaster.setCompanyId(dataTable.getCompanyId());
			saveArticleMaster.setFsCompanyMaster(fsCompanyMaster);
			saveArticleMaster.setCreator(dataTable.getCreatedBy());
			saveArticleMaster.setModifier(dataTable.getModifiedBy());
			saveArticleMaster.setCreateDate(dataTable.getCreateDate());
			saveArticleMaster.setUpdateDate(dataTable.getModifiedDate());
			saveArticleMaster.setCustomerType(updatedCustomerType(dataTable.getCustomerType()));
			saveArticleMaster.setApprovedBy(dataTable.getApprovedBy());
			saveArticleMaster.setApprovedDate(dataTable.getApprovedDate());
			saveArticleMaster.setRemarks(dataTable.getRemarks());
			saveArticleMaster.setIsActive(dataTable.getIsActive());
			saveArticleMaster.setArticleCode(dataTable.getArticleCode());
			if (dataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.ACTIVATE)) {
				dataTable.setIsActive(Constants.U);
				saveArticleMaster.setIsActive(Constants.U);
				saveArticleMaster.setRemarks(null);
				saveArticleMaster.setApprovedBy(null);
				saveArticleMaster.setApprovedDate(null);
			//	articleMasterDataTableList.remove(dataTable);
				dataTable.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
			} else if (dataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)) {
				dataTable.setIsActive(Constants.D);
				saveArticleMaster.setIsActive(Constants.D);
				saveArticleMaster.setApprovedBy(null);
				saveArticleMaster.setApprovedDate(null);
				dataTable.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
			}
			
				iArticleMasterservice.save(saveArticleMaster);
				for (int i = 0; i < 2; i++) {
					articleMasterDesc = new ArticleMasterDesc();
					articleMasterDesc.setArticleDescId(getArticleDescId());
					articleMasterDesc.setArticleMaster(saveArticleMaster);
					if (i == 0) {
						articleMasterDesc.setArticleeDescription(dataTable.getEnglishArticleDescription());
						articleMasterDesc.setArticleDescId(dataTable.getArticleEnglishDescId());
						LanguageType ltype = new LanguageType();
						ltype.setLanguageId(dataTable.getEnglishLanguageId());
						articleMasterDesc.setLanguageType(ltype);
						;
					}
					if (i == 1) {
						LanguageType ltype = new LanguageType();
						ltype.setLanguageId(dataTable.getLocalLanguageId());
						articleMasterDesc.setLanguageType(ltype);
						articleMasterDesc.setArticleeDescription(dataTable.getLocalArticleDescription());
						articleMasterDesc.setArticleDescId(dataTable.getArticleLocalDescId());
					}
					iArticleMasterservice.saveDescription(articleMasterDesc);
				}
			}catch (NullPointerException ne) {
				// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				setErrorMessage("Method Name::removefromDataTable");
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return;
			} catch (Exception e) {
				LOGGER.info("Exception occured while insert data in ArticleMasterBean" + e);
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("failure.show();");
			}
		}
	}

	public void clickOnExit() throws IOException {
		if (session.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void pageNavigation() {
		editFalg = false;
		setBooArticleCodeReadOnly(null);
		LOGGER.info("Entering into pageNavigation");
		clearAll();
		setBooDatatableEnabled(false);
		articleMasterDataTableList.clear();
		if (session.getUserName() == null) {
		}
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "articlemaster.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../customermaster/articlemaster.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("Exit  into pageNavigation");
	}

	public String updatedCustomerType(String customerType) {
		String updatedCustomerType = null;
		if (customerType != null && customerType.equalsIgnoreCase(Constants.INDIVIDUAL)) {
			updatedCustomerType = Constants.Individual;
		} else if (customerType != null && customerType.equalsIgnoreCase(Constants.Corporate)) {
			updatedCustomerType = Constants.NonIndividual;
		}
		if (customerType != null && customerType.equalsIgnoreCase(Constants.Individual)) {
			updatedCustomerType = Constants.INDIVIDUAL;
		} else if (customerType != null && customerType.equalsIgnoreCase(Constants.NonIndividual)) {
			updatedCustomerType = Constants.Corporate;
		}
		else if (customerType != null && customerType.equalsIgnoreCase(Constants.DEPENDANT)) {
			updatedCustomerType = Constants.Dependant;
		}
		else if (customerType != null && customerType.equalsIgnoreCase(Constants.Dependant)) {
			updatedCustomerType = Constants.DEPENDANT;
		}
		return updatedCustomerType;
	}

	// TO POPULATE THE RELATION CODE BASED ON GIVEN ENTRY
	public List<String> autoCompleteData(String query) {/*
		if (query.length() > 0) {
			List<ArticleMaster> articleMasterList = iArticleMasterservice.getAllComponent(query);
			List<String> list = new ArrayList<String>();
			for (ArticleMaster objArticleMaster : articleMasterList) {
				list.add(objArticleMaster.getArticleCode() );
			}
			return list;
		} else {
			return null;
		}
	*/
		
	
	


		

		if (query.length() > 0) {
			return iArticleMasterservice.getAllComponent(query);

		} else {
			
			setEnglishArticleDescription(null);
			setCustomerType(null);
			setLocalArticleDescription(null);
			return null;
		}

	
	
	}

	public void populateArticleMaster() {
		
		ispopulate = true;
		LOGGER.info("Enter into populateArticleMaster method : ArticleMasterBean ");
		LOGGER.info("Article Code " + getArticleCode());
		if (getArticleCode() != null) {
			//try{
			articleMaster = iArticleMasterservice.viewByCode(getArticleCode());
			if (articleMaster.getArticleId() != null) {/*
				ArticleMasterDataTable dataTable = new ArticleMasterDataTable();
				setArticleId(articleMaster.getArticleId());
				setCompanyId(articleMaster.getFsCompanyMaster().getCompanyId());
				setCountryId(articleMaster.getFsCountryMaster().getCountryId());
				setApprovedBy(articleMaster.getApprovedBy());
				setApprovedDate(articleMaster.getApprovedDate());
				setArticleCode(articleMaster.getArticleCode());
				setModifiedBy(articleMaster.getModifier());
				setModifiedDate(articleMaster.getUpdateDate());
				setCustomerType(updatedCustomerType(articleMaster.getCustomerType()));
				setRemarks(articleMaster.getRemarks());
				setIsActive(articleMaster.getIsActive());
				setCreateDate(articleMaster.getCreateDate());
				setCreatedBy(articleMaster.getCreator());
				dataTable.setIsActive(articleMaster.getIsActive());
				LOGGER.info("Article Id " + articleMaster.getArticleId());
				viewList = iArticleMasterservice.viewById(articleMaster.getArticleId());
				LOGGER.info(viewList == null);
				if (viewList != null && viewList.size() != 0) {
					for (ArticleMasterDesc articleMasterDesc : viewList) {
						if (articleMasterDesc.getLanguageType().getLanguageId().equals(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID))) {
							setEnglishLanguageId(articleMasterDesc.getLanguageType().getLanguageId());
							setEnglishArticleDescription(articleMasterDesc.getArticleeDescription());
							setArticleEnglishDescId(articleMasterDesc.getArticleDescId());
						} else if (articleMasterDesc.getLanguageType().getLanguageId().equals(new BigDecimal(Constants.ARABIC_LANGUAGE_ID))) {
							setLocalLanguageId(articleMasterDesc.getLanguageType().getLanguageId());
							setLocalArticleDescription(articleMasterDesc.getArticleeDescription());
							setArticleLocalDescId(articleMasterDesc.getArticleDescId());
						}
					}
				} else {
					LOGGER.info("Child table data not available for this id " + articleMaster.getArticleId());
				}
				LOGGER.info(dataTable);
				// articleMasterDataTableList.add(dataTable);
			*/
				setArticleCode(null);
				RequestContext.getCurrentInstance().execute("articleCodeExist.show();")	;
			
			}
		/*}catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::populateArticleMaster");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}*/
	} 
		LOGGER.info("Exit into populateArticleMaster method : ArticleMasterBean ");
	}

	public void editRecord(ArticleMasterDataTable dataTable) {
		setEditFalg(true);
		setHideEdit(true);
		setArticleId(dataTable.getArticleId());
		setCompanyId(dataTable.getCompanyId());
		setCountryId(dataTable.getCountryId());
		setApprovedBy(null);
		setApprovedDate(null);
		setArticleCode(dataTable.getArticleCode());
		
		setCustomerType(dataTable.getCustomerType());
		setRemarks(null);
		if (dataTable.getArticleId() == null) {
			setModifiedBy(null);
			setModifiedDate(null);
			editFalg = false;
		} else {
			setModifiedBy(session.getUserName());
			setModifiedDate(new Date());
			setBooArticleCodeReadOnly(true);
		}
		setIsActive(Constants.U);
		setCreateDate(dataTable.getCreateDate());
		setCreatedBy(dataTable.getCreatedBy());
		setEnglishLanguageId(dataTable.getEnglishLanguageId());
		setEnglishArticleDescription(dataTable.getEnglishArticleDescription());
		setArticleEnglishDescId(dataTable.getArticleEnglishDescId());
		setArticleLocalDescId(dataTable.getArticleLocalDescId());
		setLocalLanguageId(dataTable.getLocalLanguageId());
		setLocalArticleDescription(dataTable.getLocalArticleDescription());
		articleMasterDataTableList.remove(dataTable);
		setHideEdit(true);
	}

	public void view() {
		try {
			LOGGER.info("Entering into view method");
			articleMasterDataTableList.clear();
			LOGGER.info("Enter into view method");
			setBooSubmitButton(true);
			viewMasterList = iArticleMasterservice.viewMasterRecords(session.getCountryId());
			for (ArticleMaster tempObj : viewMasterList) {
				
				if (tempObj.getArticleId() != null) {
					setBooDatatableEnabled(true);
					ArticleMasterDataTable dataTable = new ArticleMasterDataTable();
					dataTable.setArticleId(tempObj.getArticleId());
					dataTable.setCompanyId(tempObj.getFsCompanyMaster().getCompanyId());
					dataTable.setCountryId(tempObj.getFsCountryMaster().getCountryId());
					dataTable.setApprovedBy(tempObj.getApprovedBy());
					dataTable.setApprovedDate(tempObj.getApprovedDate());
					dataTable.setArticleCode(tempObj.getArticleCode());
				
					dataTable.setCustomerType(updatedCustomerType(tempObj.getCustomerType()));
					dataTable.setRemarks(tempObj.getRemarks());
					dataTable.setIsActive(tempObj.getIsActive());
					dataTable.setDynamicLabelForActivateDeactivate(setreverActiveStatus(tempObj.getIsActive()));
					
					dataTable.setModifiedBy(tempObj.getModifier());
					dataTable.setModifiedDate(tempObj.getUpdateDate());
					
					if(tempObj.getModifier()!=null && tempObj.getIsActive().equals(Constants.U))
					{
						dataTable.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
					}
					dataTable.setCreateDate(tempObj.getCreateDate());
					dataTable.setCreatedBy(tempObj.getCreator());
					LOGGER.info("Article Id " + tempObj.getArticleId());
					viewList = iArticleMasterservice.viewById(tempObj.getArticleId());
					LOGGER.info(viewList == null);
					if (viewList != null && viewList.size() != 0) {
						for (ArticleMasterDesc articleMasterDesc : viewList) {
							if (articleMasterDesc.getLanguageType().getLanguageId().equals(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID))) {
								dataTable.setEnglishLanguageId(articleMasterDesc.getLanguageType().getLanguageId());
								dataTable.setEnglishArticleDescription(articleMasterDesc.getArticleeDescription());
								dataTable.setArticleEnglishDescId(articleMasterDesc.getArticleDescId());
							} else if (articleMasterDesc.getLanguageType().getLanguageId().equals(new BigDecimal(Constants.ARABIC_LANGUAGE_ID))) {
								dataTable.setLocalLanguageId(articleMasterDesc.getLanguageType().getLanguageId());
								dataTable.setLocalArticleDescription(articleMasterDesc.getArticleeDescription());
								dataTable.setArticleLocalDescId(articleMasterDesc.getArticleDescId());
							}
						}
					} else {
						/*dataTable.setEnglishArticleDescription(tempObj.getArticleName());*/
						LOGGER.info("Child table data not available for this id " + tempObj.getArticleId());
					}
					LOGGER.info(dataTable);
					articleMasterDataTableList.add(dataTable);
				}
			}
			LOGGER.info(viewList.size());
			setBooDatatableEnabled(true);
			setHideEdit(false);
			LOGGER.info("Exit into view method");
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
		LOGGER.info("Entering into setreverActiveStatus method");
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
		LOGGER.info("strStatus "+strStatus);
		LOGGER.info("Exit into setreverActiveStatus method");
		
		return strStatus;
	}

	public void clickOK() throws IOException {
		LOGGER.info("Entering into clickOK method");
		LOGGER.info(getRemarks());
		if (getRemarks() != null && !getRemarks().equals("")) {
			for (ArticleMasterDataTable dataTable : articleMasterDataTableList) {
				if (dataTable.getRemarksCheck() != null) {
					if (dataTable.getRemarksCheck().equals(true)) {
						dataTable.setRemarks(getRemarks());
						removefromDataTable(dataTable);
						setRemarks(null);
					}
				}
			}
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../customermaster/articlemaster.xhtml");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			RequestContext.getCurrentInstance().execute("remarksEmpty.show();");
		}
		LOGGER.info("Exit into clickOK method");
	}

	@Override
	public String toString() {
		return "ArticleMasterBean [articleId=" + articleId + ", articleCode=" + articleCode + ", articleName=" + articleName + ", englishArticleDescription=" + englishArticleDescription + ", localArticleDescription=" + localArticleDescription + ", companyId=" + companyId + ", countryId="
				+ countryId + ", createDate=" + createDate + ", createdBy=" + createdBy + ", customerType=" + customerType + ", modifiedDate=" + modifiedDate + ", modifiedBy=" + modifiedBy + ", isActive=" + isActive + ", approvedDate=" + approvedDate + ", approvedBy=" + approvedBy
				+ ", booArticleCodeReadOnly=" + booArticleCodeReadOnly + ", articleDescId=" + articleDescId + ", articleEnglishDescId=" + articleEnglishDescId + ", articleLocalDescId=" + articleLocalDescId + ", englishLanguageId=" + englishLanguageId + ", localLanguageId=" + localLanguageId
				+ ", editFalg=" + editFalg + ", booDatatableEnabled=" + booDatatableEnabled + ", booSubmitButton=" + booSubmitButton + ", remarks=" + remarks + ", dynamicLabelForActivateDeactivate=" + dynamicLabelForActivateDeactivate + ", hideEdit=" + hideEdit + "]";
	}
	
	
	public void cancelRemarks() {
		LOGGER.info("Entering into cancelRemarks method");
		setRemarks(null);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../customermaster/articlemaster.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
		LOGGER.info("Exit into cancelRemarks method");
	}
	
	public void activateRecord() {

		if (articleMasterDataTableList.size() > 0) {
			for (ArticleMasterDataTable dataTable : articleMasterDataTableList) {
				if (dataTable.getActivateRecordCheck() != null) {
					if (dataTable.getActivateRecordCheck().equals(true)) {

						removefromDataTable(dataTable);

					}
				}
			}
		}

	}
}
