package com.amg.exchange.registration.bean;

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
import com.amg.exchange.registration.dao.IArticleLevelService;
import com.amg.exchange.treasury.model.ArticleDetails;
import com.amg.exchange.treasury.model.ArticleDetailsDesc;
import com.amg.exchange.treasury.model.ArticleMaster;
import com.amg.exchange.treasury.model.ArticleMasterDesc;
import com.amg.exchange.util.CommonUtil;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("articleLevelApproval")
@Scope("session")
public class ArticleLevelApprovalBean<T> implements Serializable {

	@Autowired
	IGeneralService<T> generalService;

	@Autowired
	IArticleLevelService articleLevelService;

	private static final Logger LOG = Logger.getLogger(ArticleLevelBean.class);

	private static final long serialVersionUID = 1L;
	private BigDecimal articleDetailId;
	private BigDecimal articleId;
	private String articleDesc;

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

	private Boolean booDatatableEnabled;

	private BigDecimal languageEnglishId;
	private BigDecimal languageArabicId;

	private BigDecimal articleDetailsEnglishDescId;
	private BigDecimal articleDetailsArabicDescId;
	private BigDecimal articleDetailsDescId;

	// Create Session
	SessionStateManage session = new SessionStateManage();
	private List<ArticleLevelDataTable> articleLevelApprovalList = new ArrayList<ArticleLevelDataTable>();
	private List<ArticleDetailsDesc> viewList = new ArrayList<ArticleDetailsDesc>();
	private List<ArticleDetails> viewMasterList = new ArrayList<ArticleDetails>();
	private List<ArticleMasterDesc> articleList = new ArrayList<ArticleMasterDesc>();

	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	// Article level Navigation
	public void articleLevelApprovalNavigation() {
		try {
			editFalg = false;
			setBooDatatableEnabled(false);
			articleLevelApprovalList.clear();
			view();
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "articlelevelapprovallist.xhtml");
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../registration/articlelevelapprovallist.xhtml");
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
		articleList.addAll(articleLevelService.getArticles(session
				.getLanguageId()));
		return articleList;
	}

	public void setArticleList(List<ArticleMasterDesc> articleList) {
		this.articleList = articleList;
	}

	public void view() {
		String articleName = null;
		articleLevelApprovalList.clear();
		viewMasterList = articleLevelService.viewMasterRecords();

		for (ArticleDetails tempObj : viewMasterList) {

			if (tempObj.getArticleDetailId() != null) {
				setBooDatatableEnabled(true);
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
				dataTable
						.setDynamicLabelForActivateDeactivate(setStatus(tempObj
								.getIsActive()));
				dataTable.setCreatedDate(tempObj.getCreatedDate());
				dataTable.setCreatedBy(tempObj.getCreatedBy());

				viewList = articleLevelService.viewById(tempObj
						.getArticleDetailId());

				if (viewList != null && viewList.size() != 0) {
					for (ArticleDetailsDesc articleDetailsDesc : viewList) {

						if (articleDetailsDesc.getLanguageId().getLanguageId()
								.equals(new BigDecimal(1))) {
							dataTable.setArticleDesc(articleDetailsDesc
									.getArticleDetailDesc());

						} else if (articleDetailsDesc.getLanguageId()
								.getLanguageId().equals(new BigDecimal(2))) {
							dataTable.setArticleLocalDesc(articleDetailsDesc
									.getArticleDetailDesc());
						}
					}
				} /*else {
					dataTable.setArticleDesc(tempObj.getArticleDesc());
				}*/
				if (dataTable.getIsActive()!=null&&dataTable.getIsActive().equals(Constants.U)) {
					articleLevelApprovalList.add(dataTable);
				}
			}
		}
		setBooDatatableEnabled(true);

	}

	public void gotoApproval(ArticleLevelDataTable dataTable) {

		try {

			if (CommonUtil.validateApprovedBy(session.getUserName(),
					dataTable.getCreatedBy())) {
				RequestContext.getCurrentInstance().execute("notAuth.show();");
			} else {
				setArticleDetailId(dataTable.getArticleDetailId());
				setArticleId(dataTable.getArticleId());

				setArticleDesc(dataTable.getArticleDesc());
				setArticleLocalDesc(dataTable.getArticleLocalDesc());
				setArticleDetailCode(dataTable.getArticleDetailCode());
				setCreatedBy(dataTable.getCreatedBy());
				setCreatedDate(dataTable.getCreatedDate());
				setRemarks(dataTable.getRemarks());
				setModifiedBy(dataTable.getModifiedBy());
				setModifiedDate(dataTable.getModifiedDate());

				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("../registration/articlelevelapproval.xhtml");
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("Exception occured in gotoApproval:", e);
		}
	}

	public void approve() {

		ArticleDetails articleDetails = null;
		ArticleMaster articleMaster = null;
		try {
			articleDetails = new ArticleDetails();

			articleMaster = new ArticleMaster();
			articleMaster.setArticleId(getArticleId());

			articleDetails.setFsArticleMaster(articleMaster);
			articleDetails.setArticleDetailId(getArticleDetailId());
			articleDetails.setCreatedBy(getCreatedBy());
			articleDetails.setModifiedBy(getModifiedBy());
			articleDetails.setCreatedDate(getCreatedDate());
			articleDetails.setModifiedDate(getModifiedDate());
			articleDetails.setApprovedBy(session.getUserName());
			articleDetails.setApprovedDate(new Date());
			articleDetails.setRemarks(getRemarks());
			articleDetails.setIsActive(Constants.Yes);
			articleDetails.setArticleDetailCode(getArticleDetailCode());
			articleLevelService.saveArticleDetail(articleDetails);

			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("approve.show();");

		} catch (Exception e) {
			LOG.error("Exception occured in approval:", e);
		}

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

	public BigDecimal getArticleDetailId() {
		return articleDetailId;
	}

	public void setArticleDetailId(BigDecimal articleDetailId) {
		this.articleDetailId = articleDetailId;
	}

	public BigDecimal getArticleId() {
		return articleId;
	}

	public void setArticleId(BigDecimal articleId) {
		this.articleId = articleId;
	}

	public String getArticleDesc() {
		return articleDesc;
	}

	public void setArticleDesc(String articleDesc) {
		this.articleDesc = articleDesc;
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

	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}

	public void setDynamicLabelForActivateDeactivate(
			String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
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

	public Boolean getEditFalg() {
		return editFalg;
	}

	public void setEditFalg(Boolean editFalg) {
		this.editFalg = editFalg;
	}

	public List<ArticleLevelDataTable> getArticleLevelApprovalList() {
		return articleLevelApprovalList;
	}

	public void setArticleLevelApprovalList(
			List<ArticleLevelDataTable> articleLevelApprovalList) {
		this.articleLevelApprovalList = articleLevelApprovalList;
	}

	public List<ArticleDetailsDesc> getViewList() {
		return viewList;
	}

	public void setViewList(List<ArticleDetailsDesc> viewList) {
		this.viewList = viewList;
	}

	public List<ArticleDetails> getViewMasterList() {
		return viewMasterList;
	}

	public void setViewMasterList(List<ArticleDetails> viewMasterList) {
		this.viewMasterList = viewMasterList;
	}

}
