/**
 * 
 */
package com.amg.exchange.bean;

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

import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.registration.model.IncomeRangeMaster;
import com.amg.exchange.registration.model.RoleMaster;
import com.amg.exchange.registration.service.IBranchPageService;
import com.amg.exchange.registration.service.IincomeRangeService;
import com.amg.exchange.treasury.model.ArticleDetails;
import com.amg.exchange.treasury.model.ArticleDetailsDesc;
import com.amg.exchange.treasury.model.ArticleMasterDesc;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

/**
 * @author Subramaniam
 * 
 */
@Component("incomeRangeBean")
@Scope("session")
public class IncomeRangeBean<T> implements Serializable {
	private static final Logger LOGGER = Logger.getLogger(IncomeRangeBean.class);
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private BigDecimal articleDetailId;
	private BigDecimal applicationCountry;
	private BigDecimal fromAmount;
	private BigDecimal toAmount;
	private String currencyCode;
	private BigDecimal incomeRangeId;
	private BigDecimal article = null;
	private BigDecimal level = null;
	private List<ArticleDetails> lstLevel = new ArrayList<ArticleDetails>();
	private List<IncomeRangeDatatable> incomeRangeList = new ArrayList<IncomeRangeDatatable>();
	private List<IncomeRangeMaster> lstFromDb = new ArrayList<IncomeRangeMaster>();
	private String articleName;
	private String levelName;
	private String createdBy;
	private String modifiedBy;
	private String approvedBy;
	private Date createdDate;
	private Date modifiedDate;
	private Date approvedDate;
	private String isActive;
	private String remarks;
	private String monthlyIncome;
	private String dialogToAmount;
	private String errMsg;
	private String dynamicLabelForActivateDeactivate;
	private Boolean booIncomeRangeDetails = false;
	private Boolean booIncomeRangeDetailsapproval = false;
	private Boolean booEdit = false;
	private boolean isdisable = false;
	private Boolean disableSubmitButton = false;
	private SessionStateManage session = new SessionStateManage();
	@Autowired
	IBranchPageService<T> branchpageService;
	@Autowired
	IincomeRangeService<T> iIncomeRangeService;
	@Autowired
	IGeneralService<T> generalService;
	SessionStateManage sessionStateManage = new SessionStateManage();

	public IncomeRangeBean() {
	}
	
private String errorMessage ;
	
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public BigDecimal getApplicationCountry() {
		return applicationCountry;
	}

	public void setApplicationCountry(BigDecimal applicationCountry) {
		this.applicationCountry = applicationCountry;
	}

	public BigDecimal getFromAmount() {
		return fromAmount;
	}

	public void setFromAmount(BigDecimal fromAmount) {
		this.fromAmount = fromAmount;
	}

	public BigDecimal getToAmount() {
		return toAmount;
	}

	public void setToAmount(BigDecimal toAmount) {
		this.toAmount = toAmount;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public BigDecimal getArticle() {
		return article;
	}

	public void setArticle(BigDecimal article) {
		this.article = article;
	}

	public BigDecimal getLevel() {
		return level;
	}

	public void setLevel(BigDecimal level) {
		this.level = level;
	}

	public List<ArticleDetails> getLevelData() {
		return lstLevel;
	}

	public String getArticleName() {
		return articleName;
	}

	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public Boolean getDisableSubmitButton() {
		return disableSubmitButton;
	}

	public void setDisableSubmitButton(Boolean disableSubmitButton) {
		this.disableSubmitButton = disableSubmitButton;
	}

	public List<IncomeRangeDatatable> getIncomeRangeList() {
		return incomeRangeList;
	}

	public void setIncomeRangeList(List<IncomeRangeDatatable> incomeRangeList) {
		this.incomeRangeList = incomeRangeList;
	}

	public List<IncomeRangeMaster> getLstFromDb() {
		return lstFromDb;
	}

	public void setLstFromDb(List<IncomeRangeMaster> lstFromDb) {
		this.lstFromDb = lstFromDb;
	}

	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}

	public BigDecimal getIncomeRangeId() {
		return incomeRangeId;
	}

	public void setIncomeRangeId(BigDecimal incomeRangeId) {
		this.incomeRangeId = incomeRangeId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public BigDecimal getArticleDetailId() {
		return articleDetailId;
	}

	public void setArticleDetailId(BigDecimal articleDetailId) {
		this.articleDetailId = articleDetailId;
	}

	public void setDynamicLabelForActivateDeactivate(String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}

	public IBranchPageService<T> getBranchpageService() {
		return branchpageService;
	}

	public void setBranchpageService(IBranchPageService<T> branchpageService) {
		this.branchpageService = branchpageService;
	}

	public IincomeRangeService<T> getiIncomeRangeService() {
		return iIncomeRangeService;
	}

	public void setiIncomeRangeService(IincomeRangeService<T> iIncomeRangeService) {
		this.iIncomeRangeService = iIncomeRangeService;
	}

	public void generateLevel() {
		lstLevel = getBranchpageService().getLevelData(getArticle(), sessionStateManage.getLanguageId());
	}

	public List<ArticleMasterDesc> getArticleData() {
		List<ArticleMasterDesc> lstArticles = getBranchpageService().getArtilces(session.getCountryId(), sessionStateManage.getLanguageId());
		return lstArticles;
	}

	public Boolean getBooIncomeRangeDetails() {
		return booIncomeRangeDetails;
	}

	public void setBooIncomeRangeDetails(Boolean booIncomeRangeDetails) {
		this.booIncomeRangeDetails = booIncomeRangeDetails;
	}

	public Boolean getBooEdit() {
		return booEdit;
	}

	public void setBooEdit(Boolean booEdit) {
		this.booEdit = booEdit;
	}

	public Boolean getBooIncomeRangeDetailsapproval() {
		return booIncomeRangeDetailsapproval;
	}

	public void setBooIncomeRangeDetailsapproval(Boolean booIncomeRangeDetailsapproval) {
		this.booIncomeRangeDetailsapproval = booIncomeRangeDetailsapproval;
	}

	public boolean getIsdisable() {
		return isdisable;
	}

	public void setIsdisable(boolean isdisable) {
		this.isdisable = isdisable;
	}

	public String getMonthlyIncome() {
		return monthlyIncome;
	}

	public void setMonthlyIncome(String monthlyIncome) {
		this.monthlyIncome = monthlyIncome;
	}

	public String getDialogToAmount() {
		return dialogToAmount;
	}

	public void setDialogToAmount(String dialogToAmount) {
		this.dialogToAmount = dialogToAmount;
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void pageNavigation() {
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "incomerangemaster.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/incomerangemaster.xhtml");
			setBooIncomeRangeDetails(false);
			clearAllFields();
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

	public void addRecordDataTable() {
		try{
		LOGGER.info("Enter in datatable add method ");
		setIsdisable(false);
		setBooEdit(false);
		ArticleDetails articleDetails = null;
		boolean duplicate = false;
		
		setCurrencyCode(getiIncomeRangeService().getCountryCurrencyCode(session.getCountryId()));
		String monthlyIncomeRange = "";
		int amount;
		amount = getFromAmount().compareTo(getToAmount());
		if (amount == 0) {
			monthlyIncomeRange = "above" + " " + getFromAmount();
			System.out.println("Monthly IncomeRance " + monthlyIncomeRange);
		} else {
			monthlyIncomeRange = getFromAmount().toPlainString() + "-" + getToAmount().toPlainString();
		}
		String articleName = getiIncomeRangeService().getArticleName(getArticle(), session.getLanguageId());
		String articleDetailName = getiIncomeRangeService().getArticleDetailsName(getLevel(), session.getLanguageId());
		for (IncomeRangeDatatable incomeRangeDatatable : incomeRangeList) {
			int result1 = 1, result2 = 1;
			if (incomeRangeDatatable.getFromAmount() != null) {
				result1 = incomeRangeDatatable.getFromAmount().compareTo(getFromAmount());
			}
			if (incomeRangeDatatable.getToAmount() != null) {
				result2 = incomeRangeDatatable.getToAmount().compareTo(getToAmount());
			}
			if (result1 == 0 && result2 == 0 && incomeRangeDatatable.getArticalDeatialName().equalsIgnoreCase(articleDetailName)) {
				duplicate = true;
			}
		}
		if (!duplicate) {
			IncomeRangeDatatable incomeRangeDatatable = new IncomeRangeDatatable();
			incomeRangeDatatable.setArticleName(articleName);
			incomeRangeDatatable.setArticalDeatialName(articleDetailName);
			incomeRangeDatatable.setArticleId(getArticle());
			incomeRangeDatatable.setArticleDetailId(getLevel());
			incomeRangeDatatable.setMonthlyIncome(monthlyIncomeRange);
			incomeRangeDatatable.setFromAmount(getFromAmount());
			incomeRangeDatatable.setToAmount(getToAmount());
			incomeRangeDatatable.setIncomeRangeId(getIncomeRangeId());
			articleDetails = new ArticleDetails();
			articleDetails.setArticleDetailId(getLevel());
			if (getIncomeRangeId() != null) {
				incomeRangeDatatable.setDynamicLabelForActivateDeactivate(getDynamicLabelForActivateDeactivate());
				incomeRangeDatatable.setCreatedBy(getCreatedBy());
				incomeRangeDatatable.setCreatedDate(getCreatedDate());
				incomeRangeDatatable.setIsActive(getIsActive());
				incomeRangeDatatable.setModifiedBy(getModifiedBy());
				incomeRangeDatatable.setModifiedDate(getModifiedDate());
				incomeRangeDatatable.setApprovedBy(getApprovedBy());
				incomeRangeDatatable.setApprovedDate(getApprovedDate());
				List<IncomeRangeMaster> dataList = getiIncomeRangeService().getIncomeFRangeListforDetail(session.getCountryId(), incomeRangeDatatable.getArticleDetailId());
				double fromAmount = incomeRangeDatatable.getFromAmount().doubleValue();
				double toAmount = incomeRangeDatatable.getToAmount().doubleValue();
				for (IncomeRangeMaster income : dataList) {
					if (income.getIncomeRangeId().doubleValue() != getIncomeRangeId().doubleValue()) {
						double lowerBound = income.getIncomeRangeFrom().doubleValue();
						double upperBound = income.getIncomeRangeTo().doubleValue();
						if (lowerBound <= fromAmount && fromAmount <= upperBound) {
							RequestContext context = RequestContext.getCurrentInstance();
							context.execute("isExist.show();");
							return;
						}
						if (lowerBound <= toAmount && toAmount <= upperBound) {
							RequestContext context = RequestContext.getCurrentInstance();
							context.execute("isExist.show();");
							return;
						}
						
						if(lowerBound==upperBound)
						{
							if(fromAmount > lowerBound ||   toAmount > lowerBound)
							{
								RequestContext context = RequestContext.getCurrentInstance();
								context.execute("isExist.show();");
								return;
							}
						}
					}
				}
			} else {
				List<IncomeRangeMaster> dataList = getiIncomeRangeService().getIncomeFRangeListforDetail(session.getCountryId(), incomeRangeDatatable.getArticleDetailId());
				double fromAmount = incomeRangeDatatable.getFromAmount().doubleValue();
				double toAmount = incomeRangeDatatable.getToAmount().doubleValue();
				for (IncomeRangeMaster income : dataList) {
					double lowerBound = income.getIncomeRangeFrom().doubleValue();
					double upperBound = income.getIncomeRangeTo().doubleValue();
					if (lowerBound <= fromAmount && fromAmount <= upperBound) {
						RequestContext context = RequestContext.getCurrentInstance();
						context.execute("isExist1.show();");
						return;
					}
					if (lowerBound <= toAmount && toAmount <= upperBound) {
						RequestContext context = RequestContext.getCurrentInstance();
						context.execute("isExist1.show();");
						return;
					}
					
					if(lowerBound==upperBound)
					{
						if(fromAmount > lowerBound ||   toAmount > lowerBound)
						{
							RequestContext context = RequestContext.getCurrentInstance();
							context.execute("isExist1.show();");
							return;
						}
					}
					
					incomeRangeDatatable.setCreatedBy(getCreatedBy());
					incomeRangeDatatable.setCreatedDate(getCreatedDate());
				}
				incomeRangeDatatable.setDynamicLabelForActivateDeactivate(Constants.REMOVE);
			}
			System.out.println("#############" + incomeRangeDatatable);
			// isExist =
			// getiIncomeRangeService().isExistIncomeRange(session.getCountryId(),incomeRangeDatatable.getArticleDetailId(),incomeRangeDatatable.getFromAmount(),incomeRangeDatatable.getToAmount());
			/*
			 * List<IncomeRangeMaster> dataList
			 * =getiIncomeRangeService().getIncomeFRangeListforDetail
			 * (session.getCountryId
			 * (),incomeRangeDatatable.getArticleDetailId());
			 * 
			 * double fromAmount
			 * =incomeRangeDatatable.getFromAmount().doubleValue();
			 * 
			 * double toAmount
			 * =incomeRangeDatatable.getToAmount().doubleValue();
			 * 
			 * for (IncomeRangeMaster income : dataList) {
			 * 
			 * double lowerBound = income.getIncomeRangeFrom().doubleValue();
			 * 
			 * double upperBound = income.getIncomeRangeTo().doubleValue();
			 * 
			 * if (lowerBound <= fromAmount && fromAmount <= upperBound) {
			 * RequestContext context = RequestContext.getCurrentInstance();
			 * context.execute("isExist.show();"); return; }
			 * 
			 * if (lowerBound <= toAmount && toAmount <= upperBound) {
			 * RequestContext context = RequestContext.getCurrentInstance();
			 * context.execute("isExist.show();"); return; }
			 * 
			 * 
			 * }
			 */
			/*
			 * if(!isExist) { RequestContext context =
			 * RequestContext.getCurrentInstance();
			 * context.execute("isExist.show();"); return; }
			 */
			// incomeRangeList.add(incomeRangeDatatable);
			if (!incomeRangeList.isEmpty()) {
				for (IncomeRangeDatatable dataTable : incomeRangeList) {
					if (dataTable.getArticleDetailId().equals(getLevel())) {
						double infromAmount = dataTable.getFromAmount().doubleValue();
						double intoAmount = dataTable.getToAmount().doubleValue();
						if (infromAmount <= getFromAmount().doubleValue() && getFromAmount().doubleValue() <= intoAmount) {
							RequestContext context = RequestContext.getCurrentInstance();
							context.execute("isExist1.show();");
							return;
						}
						if (infromAmount <= getToAmount().doubleValue() && getToAmount().doubleValue() <= intoAmount) {
							RequestContext context = RequestContext.getCurrentInstance();
							context.execute("isExist1.show();");
							return;
						}
					}
				}
			}
			incomeRangeList.add(incomeRangeDatatable);
			setBooIncomeRangeDetails(true);
			clearAllFields();
		} else {
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("duplicate.show();");
			setFromAmount(null);
			setToAmount(null);
		}
		LOGGER.info("Exit from datatable add method ");
		}catch (NullPointerException ne) {
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

	public void view() {
		LOGGER.info("Enter in view method ");
		clearAllFields();
		incomeRangeList.clear();
		try{
		lstFromDb = getiIncomeRangeService().getIncomeRangeList();
		generateLevel();
		getArticleData();
		String articleName = null;
		for (IncomeRangeMaster incomeRange : lstFromDb) {
			List<ArticleDetailsDesc> articleDetailDescList = getiIncomeRangeService().getArticleDetailsDesc(incomeRange.getArticleDetail().getArticleDetailId(), session.getLanguageId());
			IncomeRangeDatatable incomeRangeDataTable = new IncomeRangeDatatable();
			incomeRangeDataTable.setIncomeRangeId(incomeRange.getIncomeRangeId());
			String monthlyIncomeRange = "";
			int amount = 1;
			if (incomeRange.getIncomeRangeFrom() != null && incomeRange.getIncomeRangeTo() != null) {
				amount = incomeRange.getIncomeRangeFrom().compareTo(incomeRange.getIncomeRangeTo());
			}
			if (amount == 0) {
				monthlyIncomeRange = "above" + " " + incomeRange.getIncomeRangeFrom();
			} else {
				if (incomeRange.getIncomeRangeFrom() != null && incomeRange.getIncomeRangeTo() != null) {
					monthlyIncomeRange = incomeRange.getIncomeRangeFrom().toPlainString() + "-" + incomeRange.getIncomeRangeTo().toPlainString();
				}
			}
			incomeRangeDataTable.setMonthlyIncome(monthlyIncomeRange);
			incomeRangeDataTable.setFromAmount(incomeRange.getIncomeRangeFrom());
			incomeRangeDataTable.setToAmount(incomeRange.getIncomeRangeTo());
			if (articleDetailDescList != null) {
				incomeRangeDataTable.setArticleDetailId(articleDetailDescList.get(0).getArticleDetails().getArticleDetailId());
				incomeRangeDataTable.setArticalDeatialName(articleDetailDescList.get(0).getArticleDetailDesc());
				List<ArticleDetails> articleDetailList = getiIncomeRangeService().getArticleId(articleDetailDescList.get(0).getArticleDetails().getArticleDetailId());
				articleName = getiIncomeRangeService().getArticleName(articleDetailList.get(0).getFsArticleMaster().getArticleId(), session.getLanguageId());
				incomeRangeDataTable.setArticleName(articleName);
				incomeRangeDataTable.setArticleId(articleDetailList.get(0).getFsArticleMaster().getArticleId());
			}
			incomeRangeDataTable.setDynamicLabelForActivateDeactivate(setreverActiveStatus(incomeRange.getIsActive()));
			incomeRangeDataTable.setCreatedBy(incomeRange.getCreatedBy());
			incomeRangeDataTable.setCreatedDate(incomeRange.getCreatedDate());
			incomeRangeDataTable.setModifiedBy(incomeRange.getModifiedBy());
			incomeRangeDataTable.setModifiedDate(incomeRange.getModifiedDate());
			incomeRangeDataTable.setApprovedBy(incomeRange.getApprovedBy());
			incomeRangeDataTable.setApprovedDate(incomeRange.getApprovedDate());
			incomeRangeDataTable.setRemarks(incomeRange.getRemarks());
			incomeRangeDataTable.setApplicationCountryId(incomeRange.getFsCountryMaster().getCountryId());
			incomeRangeDataTable.setIsActive(incomeRange.getIsActive());
			incomeRangeList.add(incomeRangeDataTable);
			setBooIncomeRangeDetails(true);
		}
		LOGGER.info("Exit from view method ");
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
	}

	public void approve() {
		try{
		LOGGER.info("Enter in approve method ");
		IncomeRangeMaster incomeRangemaster = new IncomeRangeMaster();
		incomeRangemaster.setIncomeRangeId(getIncomeRangeId());
		CountryMaster countryMaster = new CountryMaster();
		countryMaster.setCountryId(getApplicationCountry());
		incomeRangemaster.setFsCountryMaster(countryMaster);
		ArticleDetails articleDetail = new ArticleDetails();
		articleDetail.setArticleDetailId(getArticleDetailId());
		incomeRangemaster.setArticleDetail(articleDetail);
		setCurrencyCode(getiIncomeRangeService().getCountryCurrencyCode(session.getCountryId()));
		String monthlyIncomeRance = "";
		int amount;
		amount = getFromAmount().compareTo(getToAmount());
		if (amount == 0) {
			monthlyIncomeRance = "above" + getFromAmount();
			System.out.println("Monthly IncomeRance " + monthlyIncomeRance);
			// incomeRangemaster.setMonthlyIncome(monthlyIncomeRance);
		} else {
			monthlyIncomeRance = getFromAmount().toPlainString() + "-" + getToAmount().toPlainString();
		}
		incomeRangemaster.setMonthlyIncome(monthlyIncomeRance);
		incomeRangemaster.setIncomeRangeFrom(getFromAmount());
		incomeRangemaster.setIncomeRangeTo(getToAmount());
		incomeRangemaster.setCreatedBy(getCreatedBy());
		incomeRangemaster.setCreatedDate(getCreatedDate());
		incomeRangemaster.setModifiedBy(getModifiedBy());
		incomeRangemaster.setModifiedDate(getModifiedDate());
		incomeRangemaster.setApprovedBy(session.getUserName());
		incomeRangemaster.setApprovedDate(new Date());
		incomeRangemaster.setIsActive(Constants.Yes);
		getiIncomeRangeService().save(incomeRangemaster);
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("complete.show();");
		LOGGER.info("Exit from approve method ");
		}catch (NullPointerException ne) {
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

	public void save() {
		LOGGER.info("Enter in save method ");
		if (incomeRangeList.isEmpty()) {
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("dataNotFund.show();");
		} else {
			try{
			for (IncomeRangeDatatable incomeDataTable : incomeRangeList) {
				IncomeRangeMaster incomeRangemaster = new IncomeRangeMaster();
				incomeRangemaster.setIncomeRangeId(incomeDataTable.getIncomeRangeId());
				System.out.println(incomeDataTable);
				System.out.println(incomeDataTable.getIncomeRangeId());
				CountryMaster countryMaster = new CountryMaster();
				countryMaster.setCountryId(session.getCountryId());
				incomeRangemaster.setFsCountryMaster(countryMaster);
				ArticleDetails articleDetail = new ArticleDetails();
				articleDetail.setArticleDetailId(incomeDataTable.getArticleDetailId());
				incomeRangemaster.setArticleDetail(articleDetail);
				incomeRangemaster.setMonthlyIncome(incomeDataTable.getMonthlyIncome());
				incomeRangemaster.setIncomeRangeFrom(incomeDataTable.getFromAmount());
				incomeRangemaster.setIncomeRangeTo(incomeDataTable.getToAmount());
				if (incomeRangemaster.getIncomeRangeId() != null) {
					incomeRangemaster.setCreatedBy(incomeDataTable.getCreatedBy());
					incomeRangemaster.setCreatedDate(incomeDataTable.getCreatedDate());
					incomeRangemaster.setModifiedBy(incomeDataTable.getModifiedBy());
					incomeRangemaster.setModifiedDate(incomeDataTable.getModifiedDate());
					incomeRangemaster.setIsActive(incomeDataTable.getIsActive());
				} else {
					incomeRangemaster.setCreatedBy(session.getUserName());
					incomeRangemaster.setCreatedDate(new Date());
					incomeRangemaster.setIsActive(Constants.U);
				}
				/*
				 * boolean isExist = false; isExist =
				 * getiIncomeRangeService().isExistIncomeRange
				 * (session.getCountryId
				 * (),incomeDataTable.getArticleDetailId(),incomeDataTable
				 * .getFromAmount(),incomeDataTable.getToAmount());
				 */
				
					getiIncomeRangeService().save(incomeRangemaster);
				
			}
			 }catch (NullPointerException ne) {
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
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("complete.show();");
		LOGGER.info("Exit from save method ");
	}

	public void clear() {
		LOGGER.info("Enter in clear method ");
		clearAllFields();
		setBooIncomeRangeDetails(false);
		setBooEdit(false);
		incomeRangeList.clear();
		LOGGER.info("Exit from clear method ");
	}

	public void clearAllFields() {
		LOGGER.info("Enter in clear all fields method ");
		setFromAmount(null);
		setToAmount(null);
		setArticle(null);
		setLevel(null);
		setRemarks(null);
		setIncomeRangeId(null);
		setBooEdit(false);
		LOGGER.info("Exit from clear all fields method ");
	}

	public void clearfromandtoAmount() {
		LOGGER.info("Enter in clear all fields method ");
		setFromAmount(null);
		setToAmount(null);
		setArticle(null);
		setLevel(null);
		setRemarks(null);
		setIncomeRangeId(null);
		setBooEdit(false);
		LOGGER.info("Exit from clear all fields method ");
	}

	public void clearDuplicate() {
		setFromAmount(null);
		setToAmount(null);
		view();
	}

	public void clearDuplicatewithoutDB() {
		setFromAmount(null);
		setToAmount(null);
	}

	public void editRecord(IncomeRangeDatatable bean) {
		LOGGER.info("Enter in record edit method ");
		setBooEdit(true);
		setIsdisable(true);
		if (bean.getIncomeRangeId() != null) {
			setModifiedBy(session.getUserName());
			setModifiedDate(new Date());
		}
		try{
		setIncomeRangeId(bean.getIncomeRangeId());
		setCreatedBy(bean.getCreatedBy());
		setCreatedDate(bean.getCreatedDate());
		setApprovedBy(bean.getApprovedBy());
		setApprovedDate(bean.getApprovedDate());
		setRemarks(bean.getRemarks());
		setDynamicLabelForActivateDeactivate(bean.getDynamicLabelForActivateDeactivate());
		setArticle(bean.getArticleId());
		generateLevel();
		setLevel(bean.getArticleDetailId());
		setFromAmount(bean.getFromAmount());
		setToAmount(bean.getToAmount());
		setMonthlyIncome(bean.getMonthlyIncome());
		setIsActive(Constants.U);
		setModifiedBy(session.getUserName());
		setModifiedDate(new Date());
		System.out.println(bean);
		incomeRangeList.remove(bean);
		LOGGER.info("Exit in clear method ");
		}catch (NullPointerException ne) {
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

	public void remove(IncomeRangeDatatable bean) {
		LOGGER.info("Enter in remove method ");
		try{
		if (bean.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.REMOVE)) {
			incomeRangeList.remove(bean);
		} else if (bean.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DELETE)) {
			if (bean.getModifiedBy() != null && bean.getModifiedDate() != null && bean.getApprovedBy() == null && bean.getRemarks() == null && bean.getIsActive().equalsIgnoreCase(Constants.U)) {
				// activate(bean);
				RequestContext.getCurrentInstance().execute("notDelete.show();");
			} else if (bean.getModifiedBy() == null && bean.getApprovedBy() == null && bean.getRemarks() == null && bean.getIsActive().equalsIgnoreCase(Constants.U)) {
				delete(bean);
				RequestContext.getCurrentInstance().execute("delete.show();");
			}
		} else if (bean.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)) {
			setApprovedBy(bean.getApprovedBy());
			setApprovedDate(bean.getApprovedDate());
			setArticleDetailId(bean.getArticleDetailId());
			setApplicationCountry(bean.getApplicationCountryId());
			setIncomeRangeId(bean.getIncomeRangeId());
			setCreatedBy(bean.getCreatedBy());
			setCreatedDate(bean.getCreatedDate());
			deactivate(bean);
		} else if (bean.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.ACTIVATE)) {
			System.out.println("Activate =============== > ");
			activate(bean);
		}}catch (NullPointerException ne) {
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
		LOGGER.info("Exit from remove method ");
	}

	public void exit() {
		LOGGER.info("Enter in exit method ");
		try {
			List<RoleMaster> rolList = generalService.getRoleList(new BigDecimal(sessionStateManage.getRoleId()));
			if (rolList.get(0).getRoleName().equalsIgnoreCase(Constants.USER_ROLE_MANAGER)) {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
				incomeRangeList.clear();
				setBooIncomeRangeDetails(false);
				clearAllFields();
			} else {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
				setBooIncomeRangeDetails(false);
				clearAllFields();
				incomeRangeList.clear();
			}
			setBooIncomeRangeDetails(false);
			setBooEdit(false);
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
		LOGGER.info("Exit from exit method ");
	}

	public void clickOnOK() {
		LOGGER.info("Enter in click ok method ");
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/incomerangemaster.xhtml");
			setBooIncomeRangeDetails(false);
			setBooEdit(false);
			clearAllFields();
			incomeRangeList.clear();
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
		LOGGER.info("Exit from click ok method ");
	}

	public void clickApproveOK() {
		LOGGER.info("Enter in approve ok method ");
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/incomerangemasterapproval.xhtml");
			setBooIncomeRangeDetails(true);
			setBooIncomeRangeDetailsapproval(false);
			approveView();
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
		LOGGER.info("Exit from approve ok method ");
	}

	private String setreverActiveStatus(String status) {
		LOGGER.info("Enter in active status method ");
		String strStatus = null;
		if (status.equalsIgnoreCase(Constants.U)) {
			strStatus = Constants.DELETE;
		} else if (status.equalsIgnoreCase(Constants.D)) {
			strStatus = Constants.ACTIVATE;
		} else if (status.equalsIgnoreCase(Constants.Yes)) {
			strStatus = Constants.DEACTIVATE;
		} else if (status.equalsIgnoreCase(Constants.U)) {
			strStatus = Constants.REMOVE;
		}
		LOGGER.info("Exit from active status method ");
		return strStatus;
	}

	public void pageNavigationApproval() {
		LOGGER.info("Enter in approval page navigation method ");
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "incomerangemasterapproval.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/incomerangemasterapproval.xhtml");
			setBooIncomeRangeDetails(true);
			setBooIncomeRangeDetailsapproval(false);
			approveView();
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
		LOGGER.info("Exit from approval page navigation method ");
	}

	public void approveView() {
		LOGGER.info("Enter in approve view method ");
		incomeRangeList.clear();
		try{
		lstFromDb = getiIncomeRangeService().getIncomeRangeList();
		for (IncomeRangeMaster incomeRange : lstFromDb) {
			if (incomeRange.getIsActive().equalsIgnoreCase(Constants.U)) {
				List<ArticleDetailsDesc> articleDetailDescList = getiIncomeRangeService().getArticleDetailsDesc(incomeRange.getArticleDetail().getArticleDetailId(), session.getLanguageId());
				IncomeRangeDatatable incomeRangeDataTable = new IncomeRangeDatatable();
				incomeRangeDataTable.setArticleDetailId(incomeRange.getArticleDetail().getArticleDetailId());
				incomeRangeDataTable.setIncomeRangeId(incomeRange.getIncomeRangeId());
				String monthlyIncomeRange = "";
				int amount = 1;
				if (incomeRange.getIncomeRangeFrom() != null && incomeRange.getIncomeRangeTo() != null) {
					amount = incomeRange.getIncomeRangeFrom().compareTo(incomeRange.getIncomeRangeTo());
				}
				if (amount == 0) {
					monthlyIncomeRange = "above" + " " + incomeRange.getIncomeRangeFrom();
				} else {
					if (incomeRange.getIncomeRangeFrom() != null && incomeRange.getIncomeRangeTo() != null) {
						monthlyIncomeRange = incomeRange.getIncomeRangeFrom().toPlainString() + "-" + incomeRange.getIncomeRangeTo().toPlainString();
					}
				}
				incomeRangeDataTable.setMonthlyIncome(monthlyIncomeRange);
				/*
				 * String monthlyIncomeRange =""; int amount ;
				 * 
				 * amount =
				 * incomeRange.getIncomeRangeFrom().compareTo(incomeRange
				 * .getIncomeRangeTo());
				 * 
				 * 
				 * if(amount == 0){
				 * 
				 * monthlyIncomeRange = "above"+getFromAmount();
				 * 
				 * System.out.println("Monthly IncomeRance "+monthlyIncomeRange);
				 * 
				 * 
				 * }else{ monthlyIncomeRange = getFromAmount().toPlainString() +
				 * "-" + getToAmount().toPlainString();
				 * 
				 * } incomeRangeDataTable.setMonthlyIncome(monthlyIncomeRange);
				 */
				incomeRangeDataTable.setFromAmount(incomeRange.getIncomeRangeFrom());
				incomeRangeDataTable.setToAmount(incomeRange.getIncomeRangeTo());
				incomeRangeDataTable.setDynamicLabelForActivateDeactivate(setreverActiveStatus(incomeRange.getIsActive()));
				if (articleDetailDescList != null) {
					incomeRangeDataTable.setArticleDetailId(articleDetailDescList.get(0).getArticleDetails().getArticleDetailId());
					incomeRangeDataTable.setArticalDeatialName(articleDetailDescList.get(0).getArticleDetailDesc());
					List<ArticleDetails> articleDetailList = getiIncomeRangeService().getArticleId(articleDetailDescList.get(0).getArticleDetails().getArticleDetailId());
					articleName = getiIncomeRangeService().getArticleName(articleDetailList.get(0).getFsArticleMaster().getArticleId(), session.getLanguageId());
					incomeRangeDataTable.setArticleName(articleName);
					incomeRangeDataTable.setArticleId(articleDetailList.get(0).getFsArticleMaster().getArticleId());
				}
				incomeRangeDataTable.setDynamicLabelForActivateDeactivate(setreverActiveStatus(incomeRange.getIsActive()));
				incomeRangeDataTable.setCreatedBy(incomeRange.getCreatedBy());
				incomeRangeDataTable.setCreatedDate(incomeRange.getCreatedDate());
				incomeRangeDataTable.setModifiedBy(incomeRange.getModifiedBy());
				incomeRangeDataTable.setModifiedDate(incomeRange.getModifiedDate());
				incomeRangeDataTable.setApprovedBy(incomeRange.getApprovedBy());
				incomeRangeDataTable.setApprovedDate(incomeRange.getApprovedDate());
				incomeRangeDataTable.setRemarks(incomeRange.getRemarks());
				incomeRangeDataTable.setApplicationCountryId(incomeRange.getFsCountryMaster().getCountryId());
				incomeRangeList.add(incomeRangeDataTable);
				setBooIncomeRangeDetails(true);
				clearAllFields();
			}
		}
		}catch (NullPointerException ne) {
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
		LOGGER.info("Exit from approve view method ");
	}
//	(dataTable.getModifiedBy()==null?dataTable.getCreatedBy():dataTable.getModifiedBy()).equalsIgnoreCase( session.getUserName())
	public void approve(IncomeRangeDatatable incomeRange) {
		LOGGER.info("Enter in approve validation view method ");
		try{
		//if (!incomeRange.getCreatedBy().equalsIgnoreCase(session.getUserName())) {
		if (!(incomeRange.getModifiedBy()==null?incomeRange.getCreatedBy():incomeRange.getModifiedBy()).equalsIgnoreCase( session.getUserName())) {
			setBooIncomeRangeDetails(false);
			setBooIncomeRangeDetailsapproval(true);
			setLevelName(incomeRange.getArticalDeatialName());
			setArticleName(incomeRange.getArticleName());
			setFromAmount(incomeRange.getFromAmount());
			setToAmount(incomeRange.getToAmount());
			setArticleDetailId(incomeRange.getArticleDetailId());
			setApplicationCountry(incomeRange.getApplicationCountryId());
			setCreatedBy(incomeRange.getCreatedBy());
			setCreatedDate(incomeRange.getCreatedDate());
			setModifiedBy(incomeRange.getModifiedBy());
			setModifiedDate(incomeRange.getModifiedDate());
			setIncomeRangeId(incomeRange.getIncomeRangeId());
		} else {
			RequestContext.getCurrentInstance().execute("notValidUser.show();");
		}
		}catch (NullPointerException ne) {
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
		LOGGER.info("Exit from approve validation view method ");
	}

	public void delete(IncomeRangeDatatable bean) {
		LOGGER.info("Enter in delete  method ");
		try{
		IncomeRangeMaster incomeRangeMaster = new IncomeRangeMaster();
		setIncomeRangeId(bean.getIncomeRangeId());
		incomeRangeMaster.setIncomeRangeId(getIncomeRangeId());
		getiIncomeRangeService().delete(incomeRangeMaster);
		incomeRangeList.remove(bean);
		}catch (NullPointerException ne) {
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
		LOGGER.info("Exit from delete method ");
	}

	public void deactivate(IncomeRangeDatatable bean) {
		LOGGER.info("Enter in deactivate method ");
		try{
		setFromAmount(bean.getFromAmount());
		setToAmount(bean.getToAmount());
		IncomeRangeMaster incomeRangemaster = new IncomeRangeMaster();
		incomeRangemaster.setIncomeRangeId(bean.getIncomeRangeId());
		CountryMaster countryMaster = new CountryMaster();
		countryMaster.setCountryId(bean.getApplicationCountryId());
		incomeRangemaster.setFsCountryMaster(countryMaster);
		ArticleDetails articleDetail = new ArticleDetails();
		articleDetail.setArticleDetailId(bean.getArticleDetailId());
		incomeRangemaster.setArticleDetail(articleDetail);
		setCurrencyCode(getiIncomeRangeService().getCountryCurrencyCode(session.getCountryId()));
		String monthlyIncomeRance = getFromAmount().toPlainString() + "-" + getToAmount().toPlainString();
		incomeRangemaster.setMonthlyIncome(monthlyIncomeRance);
		incomeRangemaster.setIncomeRangeFrom(getFromAmount());
		incomeRangemaster.setIncomeRangeTo(getToAmount());
		incomeRangemaster.setCreatedBy(bean.getCreatedBy());
		incomeRangemaster.setCreatedDate(bean.getCreatedDate());
		incomeRangemaster.setModifiedBy(session.getUserName());
		incomeRangemaster.setModifiedDate(new Date());
		incomeRangemaster.setRemarks(getRemarks());
		incomeRangemaster.setIsActive(Constants.D);
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("remarks.show();");
		
		}catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::deactivate");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
		LOGGER.info("Exit from deactivate method ");
		
	}

	public void activate(IncomeRangeDatatable bean) {
		LOGGER.info("Enter in activate method ");
		try{
		setFromAmount(bean.getFromAmount());
		setToAmount(bean.getToAmount());
		IncomeRangeMaster incomeRangemaster = new IncomeRangeMaster();
		incomeRangemaster.setIncomeRangeId(bean.getIncomeRangeId());
		CountryMaster countryMaster = new CountryMaster();
		countryMaster.setCountryId(bean.getApplicationCountryId());
		incomeRangemaster.setFsCountryMaster(countryMaster);
		ArticleDetails articleDetail = new ArticleDetails();
		articleDetail.setArticleDetailId(bean.getArticleDetailId());
		incomeRangemaster.setArticleDetail(articleDetail);
		setCurrencyCode(getiIncomeRangeService().getCountryCurrencyCode(session.getCountryId()));
		String monthlyIncomeRance = getFromAmount().toPlainString() + "-" + getToAmount().toPlainString();
		incomeRangemaster.setMonthlyIncome(monthlyIncomeRance);
		incomeRangemaster.setIncomeRangeFrom(getFromAmount());
		incomeRangemaster.setIncomeRangeTo(getToAmount());
		incomeRangemaster.setCreatedBy(bean.getCreatedBy());
		incomeRangemaster.setCreatedDate(bean.getCreatedDate());
		incomeRangemaster.setModifiedBy(session.getUserName());
		incomeRangemaster.setModifiedDate(new Date());
		incomeRangemaster.setIsActive(Constants.U);
		getiIncomeRangeService().save(incomeRangemaster);
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("active.show();");
		}catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::activate");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
		LOGGER.info("Exit from activate method ");
	}

	public void remarkSelectedRecord() {
		try{
		LOGGER.info("Enter in remark edit method ");
		System.out.println("" + getArticleDetailId());
		IncomeRangeMaster incomeRangemaster = new IncomeRangeMaster();
		incomeRangemaster.setIncomeRangeId(getIncomeRangeId());
		CountryMaster countryMaster = new CountryMaster();
		countryMaster.setCountryId(getApplicationCountry());
		incomeRangemaster.setFsCountryMaster(countryMaster);
		ArticleDetails articleDetail = new ArticleDetails();
		articleDetail.setArticleDetailId(getArticleDetailId());
		incomeRangemaster.setArticleDetail(articleDetail);
		setCurrencyCode(getiIncomeRangeService().getCountryCurrencyCode(session.getCountryId()));
		String monthlyIncomeRance = getFromAmount().toPlainString() + "-" + getToAmount().toPlainString();
		incomeRangemaster.setMonthlyIncome(monthlyIncomeRance);
		incomeRangemaster.setIncomeRangeFrom(getFromAmount());
		incomeRangemaster.setIncomeRangeTo(getToAmount());
		incomeRangemaster.setCreatedBy(getCreatedBy());
		incomeRangemaster.setCreatedDate(getCreatedDate());
		incomeRangemaster.setModifiedBy(session.getUserName());
		incomeRangemaster.setModifiedDate(new Date());
		incomeRangemaster.setRemarks(getRemarks());
		incomeRangemaster.setIsActive(Constants.D);
		getiIncomeRangeService().save(incomeRangemaster);
		clickOnOK();
		}catch (NullPointerException ne) {
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
		LOGGER.info("Exit from remark edit method ");
	}

	public void remarkCancel() {
		LOGGER.info("Enter in remark cancel method ");
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/incomerangemaster.xhtml");
			// setBooIncomeRangeDetails(false);
			setBooIncomeRangeDetails(true);
			setBooEdit(false);
			clearAllFields();
			// view();
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
		LOGGER.info("Exit from remark cancel method ");
	}

	public void checkMin() {
		LOGGER.info("Enter in minimum checking method ");
		if (getToAmount() != null && getFromAmount() != null) {
			if (getToAmount().intValue() < getFromAmount().intValue()) {
				setFromAmount(null);
				setToAmount(null);
				RequestContext.getCurrentInstance().execute("maxgreater.show();");
			}
		}
		LOGGER.info("Exit from minimum checking method ");
	}

	public void checkMax() {
		LOGGER.info("Enter in maximum checking method ");
		if (getToAmount() != null && getFromAmount() != null) {
			if (getToAmount().intValue() < getFromAmount().intValue()) {
				// setFromBankBranch(null);
				setToAmount(null);
				RequestContext.getCurrentInstance().execute("maxgreater.show();");
			} else if (getToAmount().intValue() == getFromAmount().intValue()) {
				setDialogToAmount(getFromAmount().toString());
				RequestContext.getCurrentInstance().execute("equaldialog.show();");
			}
		}
		LOGGER.info("Exit from maximum checking method ");
	}
}
