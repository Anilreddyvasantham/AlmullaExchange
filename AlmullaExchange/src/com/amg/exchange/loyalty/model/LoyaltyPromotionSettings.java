package com.amg.exchange.loyalty.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.registration.model.CountryBranch;

@Entity
@Table(name = "EX_LTY_PROMOTION_SETTINGS")
public class LoyaltyPromotionSettings implements Serializable {
	  private static final long serialVersionUID = 1L;
	  private BigDecimal loyaltyPromotionId;
	  private LoyaltyParameterSetting loyaltyParameterId;
	  private Date startDate;
	  private Date endDate;
	  private String templateCode;
	  private String templateDescription;
	  private CountryBranch branchId;
	  private String isActive;
	  private String releaseFlag;
	  private String createdBy;
	  private Date createdDate;
	  private String modifiedBy;
	  private Date modifiedDate;
	  private String approvedBy;
	  private Date approvedDate;
	  private String remarks;
	  private CountryMaster applicationCountryId;

	  public LoyaltyPromotionSettings() {

	  }

	  public LoyaltyPromotionSettings(BigDecimal loyaltyPromotionId, LoyaltyParameterSetting loyaltyParameterId, Date startDate, Date endDate, String templateCode, String templateDescription, CountryBranch branchId, String isActive, String releaseFlag, String createdBy, Date createdDate,
			      String modifiedBy, Date modifiedDate, String approvedBy, Date approvedDate, String remarks, CountryMaster applicationCountryId) {
		    super();
		    this.loyaltyPromotionId = loyaltyPromotionId;
		    this.loyaltyParameterId = loyaltyParameterId;
		    this.startDate = startDate;
		    this.endDate = endDate;
		    this.templateCode = templateCode;
		    this.templateDescription = templateDescription;
		    this.branchId = branchId;
		    this.isActive = isActive;
		    this.releaseFlag = releaseFlag;
		    this.createdBy = createdBy;
		    this.createdDate = createdDate;
		    this.modifiedBy = modifiedBy;
		    this.modifiedDate = modifiedDate;
		    this.approvedBy = approvedBy;
		    this.approvedDate = approvedDate;
		    this.remarks = remarks;
		    this.applicationCountryId = applicationCountryId;
	  }

	  @Id
	  @GeneratedValue(generator = "ex_lty_promotion_setting_seq", strategy = GenerationType.SEQUENCE)
	  @SequenceGenerator(name = "ex_lty_promotion_setting_seq", sequenceName = "EX_LTY_PROMOTION_SETTINGS_SEQ", allocationSize = 1)
	  @Column(name = "LTY_PROMOTION_ID")
	  public BigDecimal getLoyaltyPromotionId() {
		    return loyaltyPromotionId;
	  }

	  public void setLoyaltyPromotionId(BigDecimal loyaltyPromotionId) {
		    this.loyaltyPromotionId = loyaltyPromotionId;
	  }

	  @ManyToOne(fetch = FetchType.LAZY)
	  @JoinColumn(name = "LTY_PARAMETER_ID")
	  public LoyaltyParameterSetting getLoyaltyParameterId() {
		    return loyaltyParameterId;
	  }

	  public void setLoyaltyParameterId(LoyaltyParameterSetting loyaltyParameterId) {
		    this.loyaltyParameterId = loyaltyParameterId;
	  }

	  @Column(name = "START_DATE")
	  public Date getStartDate() {
		    return startDate;
	  }

	  public void setStartDate(Date startDate) {
		    this.startDate = startDate;
	  }

	  @Column(name = "END_DATE")
	  public Date getEndDate() {
		    return endDate;
	  }

	  public void setEndDate(Date endDate) {
		    this.endDate = endDate;
	  }

	  @Column(name = "TEMPLATE_CODE")
	  public String getTemplateCode() {
		    return templateCode;
	  }

	  public void setTemplateCode(String templateCode) {
		    this.templateCode = templateCode;
	  }

	  @Column(name = "TEMPLATE_DESCRIPTION")
	  public String getTemplateDescription() {
		    return templateDescription;
	  }

	  public void setTemplateDescription(String templateDescription) {
		    this.templateDescription = templateDescription;
	  }

	  @ManyToOne(fetch = FetchType.LAZY)
	  @JoinColumn(name = "COUNTRY_BRANCH_ID")
	  public CountryBranch getBranchId() {
		    return branchId;
	  }

	  public void setBranchId(CountryBranch branchId) {
		    this.branchId = branchId;
	  }

	  @Column(name = "ISACTIVE")
	  public String getIsActive() {
		    return isActive;
	  }

	  public void setIsActive(String isActive) {
		    this.isActive = isActive;
	  }

	  @Column(name = "RELEASE_FLAG")
	  public String getReleaseFlag() {
		    return releaseFlag;
	  }

	  public void setReleaseFlag(String releaseFlag) {
		    this.releaseFlag = releaseFlag;
	  }

	  @Column(name = "CREATED_BY")
	  public String getCreatedBy() {
		    return createdBy;
	  }

	  public void setCreatedBy(String createdBy) {
		    this.createdBy = createdBy;
	  }

	  @Column(name = "CREATED_DATE")
	  public Date getCreatedDate() {
		    return createdDate;
	  }

	  public void setCreatedDate(Date createdDate) {
		    this.createdDate = createdDate;
	  }

	  @Column(name = "MODIFIED_BY")
	  public String getModifiedBy() {
		    return modifiedBy;
	  }

	  public void setModifiedBy(String modifiedBy) {
		    this.modifiedBy = modifiedBy;
	  }

	  @Column(name = "MODIFIED_DATE")
	  public Date getModifiedDate() {
		    return modifiedDate;
	  }

	  public void setModifiedDate(Date modifiedDate) {
		    this.modifiedDate = modifiedDate;
	  }

	  @Column(name = "APPROVED_BY")
	  public String getApprovedBy() {
		    return approvedBy;
	  }

	  public void setApprovedBy(String approvedBy) {
		    this.approvedBy = approvedBy;
	  }

	  @Column(name = "APPROVED_DATE")
	  public Date getApprovedDate() {
		    return approvedDate;
	  }

	  public void setApprovedDate(Date approvedDate) {
		    this.approvedDate = approvedDate;
	  }

	  @Column(name = "REMARKS")
	  public String getRemarks() {
		    return remarks;
	  }

	  public void setRemarks(String remarks) {
		    this.remarks = remarks;
	  }

	  @ManyToOne(fetch = FetchType.LAZY)
	  @JoinColumn(name = "APPLICATION_COUNTRY_ID")
	  public CountryMaster getApplicationCountryId() {
		    return applicationCountryId;
	  }

	  public void setApplicationCountryId(CountryMaster applicationCountryId) {
		    this.applicationCountryId = applicationCountryId;
	  }
}
