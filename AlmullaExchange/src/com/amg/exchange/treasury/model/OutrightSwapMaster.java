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

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;

/**
 * ExOutrightSwapMaster Created by Chennai ODC
 */
@Entity
@Table(name = "EX_OUTRIGHT_SWAP_MASTER")
public class OutrightSwapMaster implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5215136067420784969L;
	private BigDecimal outrightSwapId;
	private CompanyMaster fsCompanyMaster;
	private CountryMaster fsCountryMaster;
	private String outrightSwapCode;
	private String isactive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private Set<Deal> exDeals = new HashSet<Deal>(0);

	public OutrightSwapMaster() {
	}

	public OutrightSwapMaster(BigDecimal outrightSwapId) {
		this.outrightSwapId = outrightSwapId;
	}

	public OutrightSwapMaster(BigDecimal outrightSwapId,
			CompanyMaster fsCompanyMaster, CountryMaster fsCountryMaster,
			String outrightSwapCode, String isactive, String createdBy,
			Date createdDate, String modifiedBy, Date modifiedDate,
			Set<Deal> exDeals) {
		this.outrightSwapId = outrightSwapId;
		this.fsCompanyMaster = fsCompanyMaster;
		this.fsCountryMaster = fsCountryMaster;
		this.outrightSwapCode = outrightSwapCode;
		this.isactive = isactive;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.exDeals = exDeals;
	}

	
/*	@Id
	@TableGenerator(name="outrightswapid",table="ex_outrightswapidpk",pkColumnName="outrightswapidkey",pkColumnValue="outrightswapidvalue",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.TABLE,generator="outrightswapid")
	@Column(name = "OUTRIGHT_SWAP_ID", unique = true, nullable = false, precision = 22, scale = 0)*/
	
	//Added by kani begin
	
	@Id
	@GeneratedValue(generator="ex_outright_swap_master_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_outright_swap_master_seq",sequenceName="EX_OUTRIGHT_SWAP_MASTER_SEQ",allocationSize=1)
	@Column(name = "OUTRIGHT_SWAP_ID", unique = true, nullable = false, precision = 22, scale = 0)
	
	//Added by kani end
	
	public BigDecimal getOutrightSwapId() {
		return this.outrightSwapId;
	}

	public void setOutrightSwapId(BigDecimal outrightSwapId) {
		this.outrightSwapId = outrightSwapId;
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

	@Column(name = "OUTRIGHT_SWAP_CODE", length = 50)
	public String getOutrightSwapCode() {
		return this.outrightSwapCode;
	}

	public void setOutrightSwapCode(String outrightSwapCode) {
		this.outrightSwapCode = outrightSwapCode;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exOutrightSwapMaster")
	public Set<Deal> getExDeals() {
		return this.exDeals;
	}

	public void setExDeals(Set<Deal> exDeals) {
		this.exDeals = exDeals;
	}

}
