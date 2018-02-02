package com.amg.exchange.masters;

import java.io.Serializable;

public class ArticleMasterDataTable extends ArticleMasterBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Boolean remarksCheck;

	public Boolean getRemarksCheck() {
		return remarksCheck;
	}

	public void setRemarksCheck(Boolean remarksCheck) {
		this.remarksCheck = remarksCheck;
	}
	

	private Boolean activateRecordCheck = false;
	
	

	public Boolean getActivateRecordCheck() {
		return activateRecordCheck;
	}

	public void setActivateRecordCheck(Boolean activateRecordCheck) {
		this.activateRecordCheck = activateRecordCheck;
	}

	@Override
	public String toString() {
		return "ArticleMasterDataTable [remarksCheck=" + remarksCheck + ", getBooArticleCodeReadOnly()=" + getBooArticleCodeReadOnly() + ", isHideEdit()=" + isHideEdit() + ", getArticleEnglishDescId()=" + getArticleEnglishDescId() + ", getArticleLocalDescId()=" + getArticleLocalDescId()
				+ ", getEnglishLanguageId()=" + getEnglishLanguageId() + ", getLocalLanguageId()=" + getLocalLanguageId() + ", getArticleName()=" + getArticleName() + ", getArticleDescId()=" + getArticleDescId() + ", getArticleCode()=" + getArticleCode() + ", getEnglishArticleDescription()="
				+ getEnglishArticleDescription() + ", getLocalArticleDescription()=" + getLocalArticleDescription() + ", getDynamicLabelForActivateDeactivate()=" + getDynamicLabelForActivateDeactivate() + ", getRemarks()=" + getRemarks() + ", getBooSubmitButton()=" + getBooSubmitButton()
				+ ", getArticleMasterDataTableList()=" + getArticleMasterDataTableList() + ", getBooDatatableEnabled()=" + getBooDatatableEnabled() + ", getEditFalg()=" + getEditFalg() + ", getArticleId()=" + getArticleId() + ", getCompanyId()=" + getCompanyId() + ", getCountryId()="
				+ getCountryId() + ", getCreateDate()=" + getCreateDate() + ", getCreatedBy()=" + getCreatedBy() + ", getCustomerType()=" + getCustomerType() + ", getModifiedBy()=" + getModifiedBy() + ", getIsActive()=" + getIsActive() + ", getModifiedDate()=" + getModifiedDate()
				+ ", getApprovedBy()=" + getApprovedBy() + ", getApprovedDate()=" + getApprovedDate() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

	
	
}
