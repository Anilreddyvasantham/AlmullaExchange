package com.amg.exchange.treasury.model;

// default package
// Generated Jul 10, 2014 5:34:26  Created by Chennai ODC

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;

/**
 * ExDealConclusionType Created by Chennai ODC
 */
@Entity
@Table(name = "EX_DEAL_CONCLUSION_TYPE")
public class DealConclusionType implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5562929093441267112L;
	private BigDecimal dealConclusionTypeId;
	private CompanyMaster fsCompanyMaster;
	private CountryMaster fsCountryMaster;
	private String isactive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private Set<DealConclusionTypeDetail> exDealConclusionTypeDetails = new HashSet<DealConclusionTypeDetail>(
			0);

	public DealConclusionType() {
	}

	public DealConclusionType(BigDecimal dealConclusionTypeId) {
		this.dealConclusionTypeId = dealConclusionTypeId;
	}

	public DealConclusionType(BigDecimal dealConclusionTypeId,
			CompanyMaster fsCompanyMaster, CountryMaster fsCountryMaster,
			String isactive, String createdBy, Date createdDate,
			String modifiedBy, Date modifiedDate,
			Set<DealConclusionTypeDetail> exDealConclusionTypeDetails) {
		this.dealConclusionTypeId = dealConclusionTypeId;
		this.fsCompanyMaster = fsCompanyMaster;
		this.fsCountryMaster = fsCountryMaster;
		this.isactive = isactive;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.exDealConclusionTypeDetails = exDealConclusionTypeDetails;
	}
	
	
	@Id
	@GeneratedValue(generator="ex_deal_conclusion_type_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_deal_conclusion_type_seq",sequenceName="EX_DEAL_CONCLUSION_TYPE_SEQ",allocationSize=1)
	@Column(name = "DEAL_CONCLUSION_TYPE_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getDealConclusionTypeId() {
		return this.dealConclusionTypeId;
	}

	public void setDealConclusionTypeId(BigDecimal dealConclusionTypeId) {
		this.dealConclusionTypeId = dealConclusionTypeId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPANY_ID")
	public CompanyMaster getFsCompanyMaster() {
		return this.fsCompanyMaster;
	}

	public void setFsCompanyMaster(CompanyMaster fsCompanyMaster) {
		this.fsCompanyMaster = fsCompanyMaster;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_ID")
	public CountryMaster getFsCountryMaster() {
		return this.fsCountryMaster;
	}

	public void setFsCountryMaster(CountryMaster fsCountryMaster) {
		this.fsCountryMaster = fsCountryMaster;
	}

	@Column(name = "ISACTIVE", length = 1)
	public String getIsactive() {
		return this.isactive;
	}

	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}

	@Column(name = "CREATED_BY", length = 15)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "CREATED_DATE")
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "MODIFIED_BY", length = 15)
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "MODIFIED_DATE")
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exDealConclusionType")
	public Set<DealConclusionTypeDetail> getExDealConclusionTypeDetails() {
		return this.exDealConclusionTypeDetails;
	}

	public void setExDealConclusionTypeDetails(
			Set<DealConclusionTypeDetail> exDealConclusionTypeDetails) {
		this.exDealConclusionTypeDetails = exDealConclusionTypeDetails;
	}

}
