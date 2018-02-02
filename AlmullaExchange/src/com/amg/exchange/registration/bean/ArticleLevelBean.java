package com.amg.exchange.registration.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
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

import com.amg.exchange.common.model.LanguageType;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.masters.ArticleMasterDataTable;
import com.amg.exchange.registration.dao.IArticleLevelService;
import com.amg.exchange.treasury.model.ArticleDetails;
import com.amg.exchange.treasury.model.ArticleDetailsDesc;
import com.amg.exchange.treasury.model.ArticleMaster;
import com.amg.exchange.treasury.model.ArticleMasterDesc;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("articleLevel")
@Scope("session")
public class ArticleLevelBean<T> implements Serializable {

	@Autowired
	IGeneralService<T> generalService;

	@Autowired
	IArticleLevelService articleLevelService;
	
	private static final Logger LOG = Logger.getLogger(ArticleLevelBean.class);

	private static final long serialVersionUID = 1L;
	private BigDecimal articleDetailId;
	private BigDecimal articleId;
	private String articleDesc;
	private BigDecimal weekly;
	private BigDecimal monthly;
	private BigDecimal yearly;

	private String articleDetailCode;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;
	private String articleLocalDesc;
	private String dynamicLabelForActivateDeactivate;
	private String isActive;

	private Boolean editFalg;
	private boolean hideEdit;
	private Boolean booArticleDetailCodeReadOnly;
	private Boolean booDatatableEnabled;
	private Boolean booSubmitButton;

	private BigDecimal languageEnglishId;
	private BigDecimal languageArabicId;

	private BigDecimal articleDetailsEnglishDescId;
	private BigDecimal articleDetailsArabicDescId;
	private BigDecimal articleDetailsDescId;

	// Create Session
	SessionStateManage session = new SessionStateManage();
	
	private String errorMessage;
	
	

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void addArticleDetailToDataTable() {
		String articleName = null;

		boolean alreadyDT = false;
		// Data table check
		if (articleDataTableList.size() != 0) {
			for (ArticleLevelDataTable articleLevelDataTable : articleDataTableList) {
	
				if (articleLevelDataTable.getArticleDetailCode() != null
						&& articleLevelDataTable.getArticleDetailCode().equals(
								getArticleDetailCode())) {
					RequestContext context = RequestContext
							.getCurrentInstance();
					context.execute("articleCodeExist.show();");
					alreadyDT = true;
					break;
				}
				if (articleLevelDataTable.getArticleDesc() != null && articleLevelDataTable.getArticleDetailCode() != null
						&& articleLevelDataTable.getArticleDetailCode().equals(
								getArticleDetailCode())
						&& articleLevelDataTable.getArticleDesc()
								.equalsIgnoreCase(getArticleDesc())) {
					RequestContext context = RequestContext
							.getCurrentInstance();
					context.execute("EnglishDescriptionExist.show();");
					alreadyDT = true;
					break;
				}
				if (articleLevelDataTable.getArticleLocalDesc() != null  && articleLevelDataTable.getArticleDetailCode() != null
						&& articleLevelDataTable.getArticleDetailCode().equals(
								getArticleDetailCode())
						&& articleLevelDataTable.getArticleLocalDesc()
								.equalsIgnoreCase(getArticleLocalDesc())) {
					RequestContext context = RequestContext
							.getCurrentInstance();
					context.execute("LocalDescriptionExist.show();");
					alreadyDT = true;
					break;
				}
			}
		}
	
		boolean alreadyDB = false;
		if (!alreadyDT) {
			if (getArticleId() == null) {
				ArticleDetails exist = articleLevelService
						.viewByCode(getArticleDetailCode());
				if (exist != null && exist.getArticleDetailId() != null) {
					alreadyDB = true;
					RequestContext context = RequestContext
							.getCurrentInstance();
					context.execute("articleCodeExistdb.show();");
				} else {
					List<ArticleDetailsDesc> local = articleLevelService
							.checkDesciption(getArticleDesc());
					List<ArticleDetailsDesc> english = articleLevelService
							.checkDesciption(getArticleLocalDesc());
					if (local != null && local.size() > 0) {
						alreadyDB = true;
						RequestContext context = RequestContext
								.getCurrentInstance();
						context.execute("LocalDescriptionExistdb.show();");
					} else if (english != null && english.size() > 0) {
	
						alreadyDB = true;
						RequestContext context = RequestContext
								.getCurrentInstance();
						context.execute("EnglishDescriptionExistdb.show();");
					}
	
				}
			} else {
				alreadyDB = false;
			}
		}
	
		if (!alreadyDT && !alreadyDB) {
			ArticleLevelDataTable dataTable = new ArticleLevelDataTable();
			dataTable.setArticleDetailId(getArticleDetailId());
			dataTable.setArticleId(getArticleId());

			if(getArticleId() !=null){
				articleName = articleLevelService.getArticleName(getArticleId(),session.getLanguageId());
				dataTable.setArticleName(articleName);
				dataTable.setArticleId(getArticleId());
			}

			dataTable.setArticleDesc(getArticleDesc());
			dataTable.setArticleLocalDesc(getArticleLocalDesc());
			// dataTable.setArticleName(getEnglishArticleDescription());
			dataTable.setArticleDetailCode(getArticleDetailCode());
			dataTable
					.setDynamicLabelForActivateDeactivate(setStatus(getIsActive()));
	
			// In first Time
			if (!editFalg) {
				dataTable.setLanguageEnglishId(new BigDecimal(1));
				dataTable.setLanguageArabicId((new BigDecimal(2)));
				dataTable.setCreatedBy(session.getUserName());
				dataTable.setCreatedDate(new Timestamp(System
						.currentTimeMillis()));
				dataTable.setIsActive(Constants.U);
				dataTable.setDynamicLabelForActivateDeactivate(setStatus(null));
	
			} else {
				dataTable.setLanguageEnglishId(getLanguageEnglishId());
				dataTable.setLanguageArabicId(getLanguageArabicId());
	
				dataTable.setCreatedBy(getCreatedBy());
				dataTable.setCreatedDate(getCreatedDate());
				dataTable.setIsActive(getIsActive());
				dataTable
						.setDynamicLabelForActivateDeactivate(setStatus(getIsActive()));
			}
			
			
			
			dataTable.setModifiedBy(getModifiedBy());
			dataTable.setModifiedDate(getModifiedDate());
			dataTable.setApprovedDate(getApprovedDate());
			dataTable.setApprovedBy(getApprovedBy());
			dataTable.setRemarks(getRemarks());
			dataTable
					.setArticleDetailsEnglishDescId(getArticleDetailsEnglishDescId());
			dataTable
					.setArticleDetailsArabicDescId(getArticleDetailsArabicDescId());
	
			dataTable
					.setArticleDetailsEnglishDescId(getArticleDetailsEnglishDescId());
			dataTable
					.setArticleDetailsArabicDescId(getArticleDetailsArabicDescId());
	
			articleDataTableList.add(dataTable);
			setBooDatatableEnabled(true);
			setBooSubmitButton(true);
	
			clearAll();
		}
		if(articleDataTableList.size()>0){
			setBooSubmitButton(true);
		}
		setHideEdit(false);
	}

	private List<ArticleMasterDesc> articleList = new ArrayList<ArticleMasterDesc>();
	private List<ArticleLevelDataTable> articleDataTableList = new CopyOnWriteArrayList<ArticleLevelDataTable>();

	private List<ArticleDetailsDesc> viewList = new ArrayList<ArticleDetailsDesc>();

	private List<ArticleDetails> viewMasterList = new ArrayList<ArticleDetails>();
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	// Article level Navigation
	public void articleLevelNavigation() {
		try {
			editFalg = false;
			setBooDatatableEnabled(false);
			setBooSubmitButton(false);
			clearAll();
			articleDataTableList.clear();
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "articleleveldetails.xhtml");
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../registration/articleleveldetails.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Exit from this module
	public void exit() throws IOException {
		if (session.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../registration/employeehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../registration/branchhome.xhtml");
		}
	}

	public List<ArticleMasterDesc> getArticleList() {
		articleList.clear();
		try {
			articleList.addAll(articleLevelService.getArticles(session
					.getLanguageId()));
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::getArticleList");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return null;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return null;
		}
		
		return articleList;
	}

	public void setArticleList(List<ArticleMasterDesc> articleList) {
		this.articleList = articleList;
	}

	public void saveArticlelevel() {

		ArticleDetails articleDetails = null;
		ArticleDetailsDesc articleDetailsDesc = null;
		LanguageType languageType = null;

		try {

			if (articleDataTableList.size() > 0) {
				for (ArticleLevelDataTable articleLevelDataObj : articleDataTableList) {
					articleDetails = new ArticleDetails();
					ArticleMaster articleMaster = new ArticleMaster();
					
					articleMaster.setArticleId(articleLevelDataObj
							.getArticleId());
					
					if(articleLevelDataObj.getArticleDetailId()!=null){
						articleDetails.setFsArticleMaster(articleMaster);
						articleDetails.setArticleDetailId(articleLevelDataObj.getArticleDetailId());
						articleDetails.setCreatedBy(articleLevelDataObj.getCreatedBy());
						articleDetails.setCreatedDate(articleLevelDataObj.getCreatedDate());
						articleDetails.setRemarks(articleLevelDataObj.getRemarks());
						articleDetails.setApprovedBy(articleLevelDataObj.getApprovedBy());
						articleDetails.setApprovedDate(articleLevelDataObj.getApprovedDate());
						articleDetails.setModifiedBy(articleLevelDataObj.getModifiedBy());
						articleDetails.setModifiedDate(articleLevelDataObj.getModifiedDate());
						articleDetails.setIsActive(articleLevelDataObj.getIsActive());
						articleDetails.setArticleDetailCode(articleLevelDataObj
								.getArticleDetailCode());
					}else{
					
						articleDetails.setFsArticleMaster(articleMaster);
						articleDetails.setCreatedBy(session.getUserName());
						articleDetails.setCreatedDate(new Date());
						articleDetails.setRemarks(null);
						articleDetails.setApprovedBy(null);
						articleDetails.setApprovedDate(null);
						articleDetails.setModifiedBy(null);
						articleDetails.setModifiedDate(null);
						articleDetails.setIsActive(Constants.U);
						articleDetails.setArticleDetailCode(articleLevelDataObj
								.getArticleDetailCode());
					}
					articleLevelService.saveArticleDetail(articleDetails);

					for (int i = 0; i <= 1; i++) {
						articleDetails.setArticleDetailId(articleDetails
								.getArticleDetailId());
						
						articleDetailsDesc = new ArticleDetailsDesc();
						articleDetailsDesc.setArticleDetails(articleDetails);
						
						if (i == 0) {
							
							if(articleLevelDataObj.getArticleDetailsEnglishDescId()!=null){
								articleDetailsDesc.setArticleDetailsDescId(articleLevelDataObj.getArticleDetailsEnglishDescId());
							}							
							languageType = new LanguageType();
							languageType.setLanguageId(new BigDecimal(1));
							articleDetailsDesc.setLanguageId(languageType);
							articleDetailsDesc
									.setArticleDetailDesc(articleLevelDataObj
											.getArticleDesc());
						}
						if (i == 1) {
							if(articleLevelDataObj.getArticleDetailsArabicDescId()!=null){
								articleDetailsDesc.setArticleDetailsDescId(articleLevelDataObj.getArticleDetailsArabicDescId());
							}
							languageType = new LanguageType();
							languageType.setLanguageId(new BigDecimal(2));
							articleDetailsDesc.setLanguageId(languageType);
							articleDetailsDesc
									.setArticleDetailDesc(articleLevelDataObj
											.getArticleLocalDesc());
						}
						articleLevelService
								.saveArticleDetailDesc(articleDetailsDesc);
					}
				}

				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("succsses.show();");
			}

		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::saveArticlelevel");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return ;
		}catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return ;
		}

	}

	public void view() {
		String articleName = null;
		articleDataTableList.clear();
		
		try{
			viewMasterList = articleLevelService.viewMasterRecords();
			for (ArticleDetails tempObj : viewMasterList) {
				if (tempObj.getArticleDetailId() != null) {
					
					ArticleLevelDataTable dataTable = new ArticleLevelDataTable();
					
					if (tempObj.getFsArticleMaster()!= null) {
						articleName = articleLevelService.getArticleName(tempObj.getFsArticleMaster().getArticleId(), session.getLanguageId());
						dataTable.setArticleName(articleName);
						dataTable.setArticleId(tempObj.getFsArticleMaster().getArticleId());
					}
					dataTable.setArticleDetailId(tempObj.getArticleDetailId());
					dataTable.setApprovedBy(tempObj.getApprovedBy());
					dataTable.setApprovedDate(tempObj.getApprovedDate());
					dataTable.setArticleDetailCode(tempObj.getArticleDetailCode());
					dataTable.setModifiedBy(tempObj.getModifiedBy());
					dataTable.setModifiedDate(tempObj.getModifiedDate());
					dataTable.setRemarks(tempObj.getRemarks());
					dataTable.setIsActive(tempObj.getIsActive());
					dataTable.setDynamicLabelForActivateDeactivate(setStatus(tempObj.getIsActive()));
					
					if(tempObj.getModifiedBy()!=null)
					{
						dataTable.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
					}
					dataTable.setCreatedDate(tempObj.getCreatedDate());
					dataTable.setCreatedBy(tempObj.getCreatedBy());
					viewList = articleLevelService.viewById(tempObj.getArticleDetailId());
					if (viewList != null && viewList.size() != 0) {
						for (ArticleDetailsDesc articleDetailsDesc : viewList) {
							if (articleDetailsDesc.getArticleDetailsDescId() != null) {
								dataTable.setArticleDetailsDescId(articleDetailsDesc.getArticleDetailsDescId());
							}
							if (articleDetailsDesc.getLanguageId().getLanguageId().equals(new BigDecimal(1))) {
								dataTable.setArticleDesc(articleDetailsDesc.getArticleDetailDesc());
								dataTable.setArticleDetailsEnglishDescId(articleDetailsDesc.getArticleDetailsDescId());
							} else if (articleDetailsDesc.getLanguageId().getLanguageId().equals(new BigDecimal(2))) {
								dataTable.setArticleDetailsArabicDescId(articleDetailsDesc.getArticleDetailsDescId());
								dataTable.setArticleLocalDesc(articleDetailsDesc.getArticleDetailDesc());
							}
						}
					} /*
					 * else { dataTable.setArticleDesc(tempObj.getArticleDesc()); }
					 */
					articleDataTableList.add(dataTable);
				}else{
					setBooDatatableEnabled(false);
					setHideEdit(false);
					//setBooSubmitButton(false);
				}
			}
			
		}catch (NullPointerException ne) {
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
		
		setBooDatatableEnabled(true);
		setBooSubmitButton(true);
	}

	public void editRecord(ArticleLevelDataTable dataTable) {
		clearAll();
		setEditFalg(true);

		setHideEdit(true);
		setArticleId(dataTable.getArticleId());
		setArticleDetailId(dataTable.getArticleDetailId());
		setApprovedBy(dataTable.getApprovedBy());
		setApprovedDate(dataTable.getApprovedDate());
		setArticleDetailCode(dataTable.getArticleDetailCode());
		setModifiedBy(session.getUserName());
		setModifiedDate(new Date());
		setRemarks(dataTable.getRemarks());
		if (dataTable.getArticleDetailId() == null) {
			setEditFalg(false);
		} else {
			setBooArticleDetailCodeReadOnly(true);
		}
		setIsActive(dataTable.getIsActive());
		setCreatedDate(dataTable.getCreatedDate());
		setCreatedBy(dataTable.getCreatedBy());
		setArticleDesc(dataTable.getArticleDesc());
		setArticleLocalDesc(dataTable.getArticleLocalDesc());
		setArticleDetailsDescId(dataTable.getArticleDetailsDescId());

		setArticleDetailsEnglishDescId(dataTable
				.getArticleDetailsEnglishDescId());
		setArticleDetailsArabicDescId(dataTable.getArticleDetailsArabicDescId());

		setLanguageEnglishId(dataTable.getLanguageEnglishId());
		setLanguageArabicId(dataTable.getLanguageArabicId());
		articleDataTableList.remove(dataTable);
		setHideEdit(true);

	}

	private String setStatus(String status) {
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
		return strStatus;
	}

	public void checkStatus(ArticleLevelDataTable articleLevelDataTable) {

		if (articleLevelDataTable.getDynamicLabelForActivateDeactivate()
				.equalsIgnoreCase(Constants.DEACTIVATE)) {
			articleLevelDataTable.setRemarksCheck(true);

			setApprovedBy(articleLevelDataTable.getApprovedBy());
			setApprovedDate(articleLevelDataTable.getApprovedDate());
			RequestContext.getCurrentInstance().execute("remarks.show();");
		}else if (articleLevelDataTable.getDynamicLabelForActivateDeactivate()
				.equalsIgnoreCase(Constants.REMOVE)) {
			articleDataTableList.remove(articleLevelDataTable);
		}else if(articleLevelDataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.ACTIVATE)){
			articleLevelDataTable.setActivateRecordCheck(true);
			RequestContext.getCurrentInstance().execute("activateRecord.show();");
			}else if(articleLevelDataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.PENDING_FOR_APPROVE)){
				RequestContext.getCurrentInstance().execute("pending.show();");
				} else {
			removefromDataTable(articleLevelDataTable);
		}
	}

	public void removefromDataTable(ArticleLevelDataTable dataTable) {
		ArticleDetails articleDetails = null;
		ArticleDetailsDesc articleDetailsDesc = null;
		ArticleDetails saveArticleDetails = null;
		LanguageType languageType = null;
		if ((dataTable.getDynamicLabelForActivateDeactivate() != null && (dataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DELETE)) || dataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.REMOVE))) {
			ArticleMaster articleMaster = new ArticleMaster();
			articleMaster.setArticleId(dataTable.getArticleId());
			if (dataTable.getArticleId() != null) {
				articleDetails = new ArticleDetails();
				articleDetails.setFsArticleMaster(articleMaster);
				articleDetails.setArticleDetailId(dataTable.getArticleDetailId());
				if (dataTable.getModifiedBy() == null && dataTable.getModifiedDate() == null && dataTable.getApprovedBy() == null && dataTable.getApprovedDate() == null && dataTable.getRemarks() == null) {
					articleDataTableList.remove(dataTable);
					try {
						List<ArticleDetailsDesc> list = articleLevelService.viewById(articleDetails.getArticleDetailId());
						for (ArticleDetailsDesc articledetailDesc : list) {
							articleLevelService.deleteDesc(articledetailDesc);
						}
						articleLevelService.delete(articleDetails);
						articleDetailsDesc = new ArticleDetailsDesc();
						RequestContext.getCurrentInstance().execute("deleteSuccess.show();");
					}  catch (NullPointerException ne) {
						// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
						setErrorMessage("Method Name::removefromDataTable");
						RequestContext.getCurrentInstance().execute("nullPointerId.show();");
						return;
					}catch (Exception exception) {
						// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
						setErrorMessage(exception.getMessage());
						RequestContext.getCurrentInstance().execute("error.show();");
						return ;
					}
				} else {
					try {
					saveArticleDetails = new ArticleDetails();
					// articleDetails.setArticleDesc(dataTable.getArticleDesc());
					articleDetails.setCreatedBy(dataTable.getCreatedBy());
					articleDetails.setModifiedBy(session.getUserName());
					articleDetails.setCreatedDate(dataTable.getCreatedDate());
					articleDetails.setModifiedDate(new Date());
					articleDetails.setApprovedBy(dataTable.getApprovedBy());
					articleDetails.setApprovedDate(dataTable.getApprovedDate());
					articleDetails.setRemarks(dataTable.getRemarks());
					articleDetails.setIsActive(Constants.D);
					articleDetails.setArticleDetailCode(dataTable.getArticleDetailCode());
					
						articleLevelService.saveArticleDetail(saveArticleDetails);
						for (int i = 0; i <= 1; i++) {
							articleDetailsDesc = new ArticleDetailsDesc();
							articleDetailsDesc.setArticleDetailsDescId(getArticleDetailsDescId());
							articleDetailsDesc.setArticleDetails(articleDetails);
							if (i == 0) {
								languageType = new LanguageType();
								languageType.setLanguageId(new BigDecimal(1));
								articleDetailsDesc.setLanguageId(languageType);
								articleDetailsDesc.setArticleDetailDesc(dataTable.getArticleDesc());
							}
							if (i == 1) {
								languageType = new LanguageType();
								languageType.setLanguageId(new BigDecimal(2));
								articleDetailsDesc.setLanguageId(languageType);
								articleDetailsDesc.setArticleDetailDesc(dataTable.getArticleLocalDesc());
							}
							articleLevelService.saveArticleDetailDesc(articleDetailsDesc);
						}
						RequestContext context = RequestContext.getCurrentInstance();
						context.execute("succsses.show();");
						articleDataTableList.remove(dataTable);
					} catch (NullPointerException ne) {
						// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
						setErrorMessage("Method Name::removefromDataTable");
						RequestContext.getCurrentInstance().execute("nullPointerId.show();");
						return;
					}catch (Exception exception) {
						// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
						setErrorMessage(exception.getMessage());
						RequestContext.getCurrentInstance().execute("error.show();");
						return ;
					}
				}
			} else {
				articleDataTableList.remove(dataTable);
				if (articleDataTableList.size() == 0) {
					setBooDatatableEnabled(false);
					clearAll();
				}
			}
		} else if ((dataTable.getDynamicLabelForActivateDeactivate() != null && (dataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.ACTIVATE)) || dataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE))) {
			try {
			ArticleDetails saveArticleDetail = null;
			ArticleDetailsDesc articledetailsDesc = null;
			saveArticleDetail = new ArticleDetails();
			ArticleMaster articleMaster = new ArticleMaster();
			articleMaster.setArticleId(dataTable.getArticleId());
			saveArticleDetail.setFsArticleMaster(articleMaster);
			saveArticleDetail.setArticleDetailId(dataTable.getArticleDetailId());
			saveArticleDetail.setCreatedBy(dataTable.getCreatedBy());
			saveArticleDetail.setModifiedBy(dataTable.getModifiedBy());
			saveArticleDetail.setCreatedDate(dataTable.getCreatedDate());
			saveArticleDetail.setModifiedDate(dataTable.getModifiedDate());
			saveArticleDetail.setApprovedBy(dataTable.getApprovedBy());
			saveArticleDetail.setApprovedDate(dataTable.getApprovedDate());
			saveArticleDetail.setRemarks(dataTable.getRemarks());
			saveArticleDetail.setIsActive(dataTable.getIsActive());
			saveArticleDetail.setArticleDetailCode(dataTable.getArticleDetailCode());
			if (dataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.ACTIVATE)) {
				dataTable.setIsActive(Constants.U);
				saveArticleDetail.setIsActive(Constants.U);
				//articleDataTableList.remove(dataTable);
			} else if (dataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)) {
				dataTable.setIsActive(Constants.D);
				saveArticleDetail.setIsActive(Constants.D);
				// beneCountryServiceData.setRenderEditButton(true);
				dataTable.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
			}
		
				articleLevelService.saveArticleDetail(saveArticleDetail);
				for (int i = 0; i <= 1; i++) {
					articledetailsDesc = new ArticleDetailsDesc();
					// articleMasterDesc.setArticleDescId(getArticleDescId());
					articledetailsDesc.setArticleDetailsDescId(getArticleDetailsDescId());
					articledetailsDesc.setArticleDetails(saveArticleDetail);
					if (i == 0) {
						languageType = new LanguageType();
						languageType.setLanguageId(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID));
						articledetailsDesc.setLanguageId(languageType);
						articledetailsDesc.setArticleDetailDesc(dataTable.getArticleDesc());
					}
					if (i == 1) {
						languageType = new LanguageType();
						languageType.setLanguageId(new BigDecimal(Constants.ARABIC_LANGUAGE_ID));
						articledetailsDesc.setLanguageId(languageType);
						articledetailsDesc.setArticleDetailDesc(dataTable.getArticleLocalDesc());
					}
					articleLevelService.saveArticleDetailDesc(articledetailsDesc);
				}
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("succsses.show();");
			}catch (NullPointerException ne) {
					// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
					setErrorMessage("Method Name::removefromDataTable");
					RequestContext.getCurrentInstance().execute("nullPointerId.show();");
					return;
				}catch (Exception exception) {
					// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
					setErrorMessage(exception.getMessage());
					RequestContext.getCurrentInstance().execute("error.show();");
					return ;
				}
		}
	}

	public void clickOK() throws IOException {
		if (getRemarks() != null && !getRemarks().equals("")) {
			for (ArticleLevelDataTable dataTable : articleDataTableList) {
				if (dataTable.getRemarksCheck() != null) {
					if (dataTable.getRemarksCheck().equals(true)) {
						dataTable.setRemarks(getRemarks());
						removefromDataTable(dataTable);
						setRemarks(null);
					}
				}
			}
			try {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("../registration/articleleveldetails.xhtml");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			RequestContext.getCurrentInstance().execute("remarksEmpty.show();");
		}
	}

	public void clearAll() {
		setArticleDetailId(null);
		setArticleId(null);
		setArticleDetailCode(null);
		setArticleDesc(null);
		setArticleLocalDesc(null);

	}

	public BigDecimal getArticleDetailId() {
		return articleDetailId;
	}

	public void setArticleDetailId(BigDecimal articleDetailId) {
		this.articleDetailId = articleDetailId;
	}

	public String getArticleDesc() {
		return articleDesc;
	}

	public void setArticleDesc(String articleDesc) {
		this.articleDesc = articleDesc;
	}

	public BigDecimal getWeekly() {
		return weekly;
	}

	public void setWeekly(BigDecimal weekly) {
		this.weekly = weekly;
	}

	public BigDecimal getMonthly() {
		return monthly;
	}

	public void setMonthly(BigDecimal monthly) {
		this.monthly = monthly;
	}

	public BigDecimal getYearly() {
		return yearly;
	}

	public void setYearly(BigDecimal yearly) {
		this.yearly = yearly;
	}

	public String getArticleDetailCode() {
		return articleDetailCode;
	}

	public void setArticleDetailCode(String articleDetailCode) {
		this.articleDetailCode = articleDetailCode;
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

	public String getArticleLocalDesc() {
		return articleLocalDesc;
	}

	public void setArticleLocalDesc(String articleLocalDesc) {
		this.articleLocalDesc = articleLocalDesc;
	}

	public List<ArticleLevelDataTable> getArticleDataTableList() {
		return articleDataTableList;
	}

	public void setArticleDataTableList(
			List<ArticleLevelDataTable> articleDataTableList) {
		this.articleDataTableList = articleDataTableList;
	}

	public BigDecimal getArticleId() {
		return articleId;
	}

	public void setArticleId(BigDecimal articleId) {
		this.articleId = articleId;
	}

	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}

	public void setDynamicLabelForActivateDeactivate(
			String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}

	public Boolean getEditFalg() {
		return editFalg;
	}

	public void setEditFalg(Boolean editFalg) {
		this.editFalg = editFalg;
	}

	public boolean isHideEdit() {
		return hideEdit;
	}

	public void setHideEdit(boolean hideEdit) {
		this.hideEdit = hideEdit;
	}

	public Boolean getBooArticleDetailCodeReadOnly() {
		return booArticleDetailCodeReadOnly;
	}

	public void setBooArticleDetailCodeReadOnly(
			Boolean booArticleDetailCodeReadOnly) {
		this.booArticleDetailCodeReadOnly = booArticleDetailCodeReadOnly;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public Boolean getBooDatatableEnabled() {
		return booDatatableEnabled;
	}

	public void setBooDatatableEnabled(Boolean booDatatableEnabled) {
		this.booDatatableEnabled = booDatatableEnabled;
	}

	public Boolean getBooSubmitButton() {
		return booSubmitButton;
	}

	public void setBooSubmitButton(Boolean booSubmitButton) {
		this.booSubmitButton = booSubmitButton;
	}

	public BigDecimal getLanguageEnglishId() {
		return languageEnglishId;
	}

	public void setLanguageEnglishId(BigDecimal languageEnglishId) {
		this.languageEnglishId = languageEnglishId;
	}

	public BigDecimal getLanguageArabicId() {
		return languageArabicId;
	}

	public void setLanguageArabicId(BigDecimal languageArabicId) {
		this.languageArabicId = languageArabicId;
	}

	public BigDecimal getArticleDetailsEnglishDescId() {
		return articleDetailsEnglishDescId;
	}

	public void setArticleDetailsEnglishDescId(
			BigDecimal articleDetailsEnglishDescId) {
		this.articleDetailsEnglishDescId = articleDetailsEnglishDescId;
	}

	public BigDecimal getArticleDetailsArabicDescId() {
		return articleDetailsArabicDescId;
	}

	public void setArticleDetailsArabicDescId(
			BigDecimal articleDetailsArabicDescId) {
		this.articleDetailsArabicDescId = articleDetailsArabicDescId;
	}

	public BigDecimal getArticleDetailsDescId() {
		return articleDetailsDescId;
	}

	public void setArticleDetailsDescId(BigDecimal articleDetailsDescId) {
		this.articleDetailsDescId = articleDetailsDescId;
	}
	
	public void cancelRemarks() {
		LOG.info("Entering into cancelRemarks method");
		setRemarks(null);
		try {
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("../registration/articleleveldetails.xhtml");
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::removefromDataTable");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		}catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return ;
		}
		LOG.info("Exit into cancelRemarks method");
	}
	
	public void activateRecord() {

		if (articleDataTableList.size() > 0) {
			for (ArticleLevelDataTable dataTable : articleDataTableList) {
				if (dataTable.getActivateRecordCheck() != null) {
					if (dataTable.getActivateRecordCheck().equals(true)) {

						removefromDataTable(dataTable);

					}
				}
			}
		}

	}

}
