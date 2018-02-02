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
@Entity
@Table(name = "EX_LTY_MST_ENCASHMENT")
public class LoyaltyMasterEncashment implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private BigDecimal encashmentId;
	private BigDecimal points;
	private BigDecimal equivalantAmount;
	private String description;
	private Date effectiveDateFrom;
	private Date effectiveDateTo;
	private String activeFlag;
	private String createdBy;
	private Date createdDate;
	private String isActive;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;
	private CountryMaster applicationCountryId;
	public LoyaltyMasterEncashment()
	{
	}
	public LoyaltyMasterEncashment(BigDecimal encashmentId, BigDecimal points,
			BigDecimal equivalantAmount, String description,
			Date effectiveDateFrom, Date effectiveDateTo, String activeFlag,
			String createdBy, Date createdDate, String isActive,
			String modifiedBy, Date modifiedDate, String approvedBy,
			Date approvedDate, String remarks ,CountryMaster applicationCountryId) {
		super();
		this.encashmentId = encashmentId;
		this.points = points;
		this.equivalantAmount = equivalantAmount;
		this.description = description;
		this.effectiveDateFrom = effectiveDateFrom;
		this.effectiveDateTo = effectiveDateTo;
		this.activeFlag = activeFlag;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.isActive = isActive;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.remarks = remarks;
		this.applicationCountryId = applicationCountryId;
	}
	@Id
	@GeneratedValue(generator="ex_lty_mst_master_encashment_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_lty_mst_master_encashment_seq",sequenceName="EX_LTY_MST_ENCASHMENT_SEQ",allocationSize=1)
	@Column(name = "LTY_MST_ENCASHMENT_ID")
	public BigDecimal getEncashmentId() {
		return encashmentId;
	}
	public void setEncashmentId(BigDecimal encashmentId) {
		this.encashmentId = encashmentId;
	}
	@Column(name = "POINTS")
	public BigDecimal getPoints() {
		return points;
	}
	public void setPoints(BigDecimal points) {
		this.points = points;
	}
	@Column(name = "EQUIVALANT_AMOUNT")
	public BigDecimal getEquivalantAmount() {
		return equivalantAmount;
	}
	public void setEquivalantAmount(BigDecimal equivalantAmount) {
		this.equivalantAmount = equivalantAmount;
	}
	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Column(name = "EFFECTIVE_DATE_FROM")
	public Date getEffectiveDateFrom() {
		return effectiveDateFrom;
	}
	public void setEffectiveDateFrom(Date effectiveDateFrom) {
		this.effectiveDateFrom = effectiveDateFrom;
	}
	@Column(name = "EFFECTIVE_DATE_TO")
	public Date getEffectiveDateTo() {
		return effectiveDateTo;
	}
	public void setEffectiveDateTo(Date effectiveDateTo) {
		this.effectiveDateTo = effectiveDateTo;
	}
	@Column(name = "ACTIVE_FLAG")
	public String getActiveFlag() {
		return activeFlag;
	}
	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
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
	@Column(name = "ISACTIVE")
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
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
