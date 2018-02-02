package com.amg.exchange.masters;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;


import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.registration.service.IArticleMasterservice;
import com.amg.exchange.treasury.model.ArticleMaster;
import com.amg.exchange.treasury.model.ArticleMasterDesc;
import com.amg.exchange.util.CommonUtil;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;
@Component("articlemasterApproval")
@Scope("session")
public class ArticleMasterApprovalBean<T> implements Serializable {
	private static final long serialVersionUID = 1L;
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
	
	
	
	
	
	//desc Table
	//private BigDecimal articleDescId;
	
	private BigDecimal articleEnglishDescId;
	private BigDecimal articleLocalDescId;
	private BigDecimal englishLanguageId;
	private BigDecimal localLanguageId;
	//private String articleDescption;
	
	private SessionStateManage session = new SessionStateManage();
	private Boolean editFalg;
	private Boolean booDatatableEnabled;
	private Boolean booSubmitButton;
	private String  remarks;
	private String dynamicLabelForActivateDeactivate;
	private List<ArticleMasterDataTable> articleMasterDataTableList = new ArrayList<ArticleMasterDataTable>();
	private List<ArticleMasterDataTable> articleMasterDataTableApprovalList = new ArrayList<ArticleMasterDataTable>();
	
	private List<ArticleMasterDesc> viewList = new ArrayList<ArticleMasterDesc>();
	
	private List<ArticleMaster> viewMasterList = new ArrayList<ArticleMaster>();
	ArticleMaster articleMaster  = null;
	ArticleMasterDesc articleMasterDesc = null;
	
	@Autowired
	IArticleMasterservice iArticleMasterservice;
	
	
	
	
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

	public List<ArticleMasterDataTable> getArticleMasterDataTableApprovalList() {
		return articleMasterDataTableApprovalList;
	}

	public void setArticleMasterDataTableApprovalList(List<ArticleMasterDataTable> articleMasterDataTableApprovalList) {
		this.articleMasterDataTableApprovalList = articleMasterDataTableApprovalList;
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

	
	
	
	public void clickOnExit() throws IOException{
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
		System.out.println("Entering into serviceBenificiaryApprovalNavigation");
		setBooDatatableEnabled(false);
		articleMasterDataTableApprovalList.clear();
		
		view();
		if (session.getUserName() == null) {
		}
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "articlemasterapproval.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../customermaster/articlemasterapproval.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Exit  into serviceBenificiaryApprovalNavigation");
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
	
	

	
	public void gotoFinalApproval(){
		

		ArticleMaster saveArticleMaster = null;
		/*ArticleMasterDesc articleMasterDesc = null;*/
			
		
			saveArticleMaster = new ArticleMaster();
			saveArticleMaster.setArticleId(getArticleId());
			/*saveArticleMaster.setArticleName(getEnglishArticleDescription());*/
			CountryMaster fsCountryMaster = new CountryMaster();
			fsCountryMaster.setCountryId(getCountryId());
			saveArticleMaster.setFsCountryMaster(fsCountryMaster);
			CompanyMaster fsCompanyMaster = new CompanyMaster();
			fsCompanyMaster.setCompanyId(getCompanyId());
			saveArticleMaster.setFsCompanyMaster(fsCompanyMaster);
			saveArticleMaster.setCreator(getCreatedBy());
			saveArticleMaster.setModifier(session.getUserName());
			saveArticleMaster.setCreateDate(getCreateDate());
			saveArticleMaster.setUpdateDate(new Date());
			saveArticleMaster.setCustomerType(updatedCustomerType(getCustomerType()));
			saveArticleMaster.setApprovedBy(session.getUserName());
			saveArticleMaster.setApprovedDate(new Date());
			saveArticleMaster.setRemarks(getRemarks());
			saveArticleMaster.setIsActive(Constants.Yes);
			saveArticleMaster.setArticleCode(getArticleCode());
			
			
			try {
				iArticleMasterservice.save(saveArticleMaster);
				/*for (int i = 0; i < 2; i++) {
					articleMasterDesc = new ArticleMasterDesc();
					
					articleMasterDesc.setArticleMaster(saveArticleMaster);
					if (i == 0) {
						articleMasterDesc.setArticleDescId(getArticleEnglishDescId());
						articleMasterDesc.setArticleeDescription(getEnglishArticleDescription());
						LanguageType ltype = new LanguageType();
						ltype.setLanguageId(getEnglishLanguageId());
						articleMasterDesc.setLanguageType(ltype);
						;
					}
					if (i == 1) {
						articleMasterDesc.setArticleDescId(getArticleLocalDescId());
						LanguageType ltype = new LanguageType();
						ltype.setLanguageId(getLocalLanguageId());
						articleMasterDesc.setLanguageType(ltype);
						articleMasterDesc.setArticleeDescription(getLocalArticleDescription());
					}
					iArticleMasterservice.saveDescription(articleMasterDesc);
				}*/
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("approve.show();");
				
			} catch (Exception e) {
				System.out.println("Exception occured while insert data in ArticleMasterBean" + e);
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("failure.show();");
			}
			
			
		
	
		
		
	}
	
	public void gotoApproval(ArticleMasterDataTable dataTable) {
		if (CommonUtil.validateApprovedBy(session.getUserName(), dataTable.getCreatedBy())) {
			RequestContext.getCurrentInstance().execute("notAuth.show();");
		} else {
			setArticleId(dataTable.getArticleId());
			setCompanyId(dataTable.getCompanyId());
			setCountryId(dataTable.getCountryId());
			setApprovedBy(dataTable.getApprovedBy());
			setApprovedDate(dataTable.getApprovedDate());
			setArticleCode(dataTable.getArticleCode());
			setModifiedBy(dataTable.getModifiedBy());
			setModifiedDate(dataTable.getModifiedDate());
			setCustomerType(dataTable.getCustomerType());
			setRemarks(dataTable.getRemarks());
			setIsActive(dataTable.getIsActive());
			// dataTable.setDynamicLabelForActivateDeactivate(setreverActiveStatus(tempObj.getIsActive()));
			setCreateDate(dataTable.getCreateDate());
			setCreatedBy(dataTable.getCreatedBy());
			setEnglishLanguageId(dataTable.getEnglishLanguageId());
			setEnglishArticleDescription(dataTable.getEnglishArticleDescription());
			//setArticleDescId(dataTable.getArticleDescId());
			setArticleEnglishDescId(dataTable.getArticleEnglishDescId());
			setArticleLocalDescId(dataTable.getArticleLocalDescId());
			setLocalLanguageId(dataTable.getLocalLanguageId());
			setLocalArticleDescription(dataTable.getLocalArticleDescription());
			
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../customermaster/articlemasterFinalApproval.xhtml");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void approveSelectedRecord()
	{
		
	}
	
	
	public void view() {
		
		articleMasterDataTableApprovalList.clear();
		System.out.println("Enter into view method");
		
		viewMasterList =  iArticleMasterservice.viewMasterRecordsforApproval(session.getCountryId());
		
		for (ArticleMaster tempObj : viewMasterList) {
			
			System.out.println(tempObj.getFsCountryMaster().getCountryId());
			System.out.println(tempObj.getFsCompanyMaster().getCompanyId());
			System.out.println(tempObj.getArticleId());
			System.out.println(tempObj.getArticleCode());
			
			if (tempObj.getArticleId() != null) {
				setBooDatatableEnabled(true);
				ArticleMasterDataTable dataTable = new ArticleMasterDataTable();
				dataTable.setArticleId(tempObj.getArticleId());
				dataTable.setCompanyId(tempObj.getFsCompanyMaster().getCompanyId());
				dataTable.setCountryId(tempObj.getFsCountryMaster().getCountryId());
				dataTable.setApprovedBy(tempObj.getApprovedBy());
				dataTable.setApprovedDate(tempObj.getApprovedDate());
				dataTable.setArticleCode(tempObj.getArticleCode());
				dataTable.setModifiedBy(tempObj.getModifier());
				dataTable.setModifiedDate(tempObj.getUpdateDate());
				dataTable.setCustomerType(updatedCustomerType(tempObj.getCustomerType()));
				dataTable.setRemarks(tempObj.getRemarks());
				dataTable.setIsActive(tempObj.getIsActive());
				dataTable.setDynamicLabelForActivateDeactivate(setreverActiveStatus(tempObj.getIsActive()));
				dataTable.setCreateDate(tempObj.getCreateDate());
				dataTable.setCreatedBy(tempObj.getCreator());
				System.out.println("Article Id " + tempObj.getArticleId());
				viewList = iArticleMasterservice.viewById(tempObj.getArticleId());
				
				System.out.println(viewList==null);
				if (viewList != null && viewList.size() != 0) {
					for (ArticleMasterDesc articleMasterDesc : viewList) {
						if (articleMasterDesc.getLanguageType().getLanguageId().equals(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID))) {
							dataTable.setEnglishLanguageId(articleMasterDesc.getLanguageType().getLanguageId());
							dataTable.setEnglishArticleDescription(articleMasterDesc.getArticleeDescription());
							dataTable.setArticleEnglishDescId(articleMasterDesc.getArticleDescId());
							//dataTable.setArticleDescId(articleMasterDesc.getArticleDescId());
						} else if (articleMasterDesc.getLanguageType().getLanguageId().equals(new BigDecimal(Constants.ARABIC_LANGUAGE_ID))) {
							dataTable.setLocalLanguageId(articleMasterDesc.getLanguageType().getLanguageId());
							dataTable.setLocalArticleDescription(articleMasterDesc.getArticleeDescription());
							//dataTable.setArticleDescId(articleMasterDesc.getArticleDescId());
							dataTable.setArticleLocalDescId(articleMasterDesc.getArticleDescId());
						}
					}
				} else {
					/*dataTable.setEnglishArticleDescription(tempObj.getArticleName());*/
					System.out.println("Child table data not available for this id "+ tempObj.getArticleId());
				}
				System.out.println(dataTable);
				if(dataTable.getIsActive().equals(Constants.U))
				{
					articleMasterDataTableApprovalList.add(dataTable);
				}
			}
			
			
		}
		
	
		setBooDatatableEnabled(true);
		
		System.out.println("Exit into view method");
	}

	@Override
	public String toString() {
		return "ArticleMasterBean [articleId=" + articleId + ", articleCode=" + articleCode + ", articleName=" + articleName + ", englishArticleDescription=" + englishArticleDescription + ", localArticleDescription=" + localArticleDescription + ", companyId=" + companyId + ", countryId="
				+ countryId + ", createDate=" + createDate + ", createdBy=" + createdBy + ", customerType=" + customerType + ", modifiedDate=" + modifiedDate + ", modifiedBy=" + modifiedBy + ", isActive=" + isActive + ", approvedDate=" + approvedDate + ", approvedBy=" + approvedBy
				+ ",  englishLanguageId=" + englishLanguageId + ", localLanguageId=" + localLanguageId + ", editFalg=" + editFalg + ", booDatatableEnabled=" + booDatatableEnabled + ", booSubmitButton=" + booSubmitButton + ", remarks=" + remarks
				+ ", dynamicLabelForActivateDeactivate=" + dynamicLabelForActivateDeactivate + "]";
	}
	
	
	private String setreverActiveStatus(String status) {
		System.out.println("Entering into setreverActiveStatus method");
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
		System.out.println("strStatus "+strStatus);
		System.out.println("Exit into setreverActiveStatus method");
		
		return strStatus;
	}
	
	
}
