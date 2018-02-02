/**
 * 
 */
package com.amg.exchange.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Subramaniam
 * 
 */
public class IncomeRangeDatatable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private BigDecimal incomeRangeId;
	private BigDecimal applicationCountryId;
	private BigDecimal articleId;
	private BigDecimal articleDetailId;
	private String articleName;
	private String articalDeatialName;
	private BigDecimal fromAmount;
	private BigDecimal toAmount;
	private String monthlyIncome;
	private String createdBy;
	private String modifiedBy;
	private String approvedBy;
	private Date createdDate;
	private Date modifiedDate;
	private Date approvedDate;
	private String isActive;
	private String remarks;

	private String dynamicLabelForActivateDeactivate;

	public IncomeRangeDatatable() {
		// TODO Auto-generated constructor stub
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

	public BigDecimal getArticleId() {
		return articleId;
	}

	public void setArticleId(BigDecimal articleId) {
		this.articleId = articleId;
	}

	public IncomeRangeDatatable(BigDecimal incomeRangeId, BigDecimal articleId, BigDecimal articleDetailId, String articleName, String articalDeatialName, BigDecimal fromAmount, BigDecimal toAmount, String monthlyIncome, String createdBy, String modifiedBy, String approvedBy, Date createdDate,
			Date modifiedDate, Date approvedDate, String isActive, String remarks, String dynamicLabelForActivateDeactivate) {
		this.incomeRangeId = incomeRangeId;
		this.articleId = articleId;
		this.articleDetailId = articleDetailId;
		this.articleName = articleName;
		this.articalDeatialName = articalDeatialName;
		this.fromAmount = fromAmount;
		this.toAmount = toAmount;
		this.monthlyIncome = monthlyIncome;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.approvedBy = approvedBy;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.approvedDate = approvedDate;
		this.isActive = isActive;
		this.remarks = remarks;
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}


	public BigDecimal getArticleDetailId() {
		return articleDetailId;
	}

	public void setArticleDetailId(BigDecimal articleDetailId) {
		this.articleDetailId = articleDetailId;
	}

	public String getArticleName() {
		return articleName;
	}

	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}

	public String getArticalDeatialName() {
		return articalDeatialName;
	}

	public void setArticalDeatialName(String articalDeatialName) {
		this.articalDeatialName = articalDeatialName;
	}

	public String getMonthlyIncome() {
		return monthlyIncome;
	}

	public void setMonthlyIncome(String monthlyIncome) {
		this.monthlyIncome = monthlyIncome;
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

	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}

	public void setDynamicLabelForActivateDeactivate(String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}

	public BigDecimal getIncomeRangeId() {
		return incomeRangeId;
	}

	public void setIncomeRangeId(BigDecimal incomeRangeId) {
		this.incomeRangeId = incomeRangeId;
	}


	public String getRemarks() {
		return remarks;
	}


	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}


	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}


	@Override
	public String toString() {
		return "IncomeRangeDatatable [incomeRangeId=" + incomeRangeId + ", applicationCountryId=" + applicationCountryId + ", articleId=" + articleId + ", articleDetailId=" + articleDetailId + ", articleName=" + articleName + ", articalDeatialName=" + articalDeatialName + ", fromAmount="
				+ fromAmount + ", toAmount=" + toAmount + ", monthlyIncome=" + monthlyIncome + ", createdBy=" + createdBy + ", modifiedBy=" + modifiedBy + ", approvedBy=" + approvedBy + ", createdDate=" + createdDate + ", modifiedDate=" + modifiedDate + ", approvedDate=" + approvedDate
				+ ", isActive=" + isActive + ", remarks=" + remarks + ", dynamicLabelForActivateDeactivate=" + dynamicLabelForActivateDeactivate + "]";
	}
	
	
	
	

}
