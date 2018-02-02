package com.amg.exchange.common.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/*******************************************************************************************************************

		 File		: BusinessComponentConf.java
 
		 Project	: AlmullaExchange

		 Package	: com.amg.exchange.model
 
		 Created	:	
 						Date	: 29-May-2014 4:41:35 pm
		 				By		: Justin Vincent
 						Revision:
 
 		 Last Change:
 						Date	: 29-May-2014 4:41:35 pm
 						By		: Justin Vincent
		 				Revision:

		 Description: TODO 

********************************************************************************************************************/
@Cacheable  
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "FS_BUSINESS_COMPONENT_CONF")
public class BusinessComponentConf implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private BigDecimal componentConfId;
	private BusinessComponent fsBusinessComponent;
	private RuleApplicationMaster fsRuleApplicationMaster;
	private RulePageMaster fsRulePageMaster;
	private CompanyMaster fsCompanyMaster;
	private CountryMaster fsCountryMaster;
	private BigDecimal levelId;
	private String createdBy;
	private Date createdTime;
	private String updatedBy;
	private Date updatedTime;
	private List<BizComponentConfDetail> fsBizComponentConfDetails = new ArrayList<BizComponentConfDetail>();
	private List<BizComponentDataRef> fsBizComponentDataRefs = new ArrayList<BizComponentDataRef>();

	public BusinessComponentConf() {
	}

	public BusinessComponentConf(BigDecimal componentConfId,
			Date createdTime, Date updatedTime) {
		this.componentConfId = componentConfId;
		this.createdTime = createdTime;
		this.updatedTime = updatedTime;
	}
	
	

	public BusinessComponentConf(BigDecimal componentConfId, BusinessComponent fsBusinessComponent, RuleApplicationMaster fsRuleApplicationMaster, RulePageMaster fsRulePageMaster, CompanyMaster fsCompanyMaster, CountryMaster fsCountryMaster, BigDecimal levelId, String createdBy, Date createdTime, String updatedBy, Date updatedTime, List<BizComponentConfDetail> fsBizComponentConfDetails, List<BizComponentDataRef> fsBizComponentDataRefs) {
		this.componentConfId = componentConfId;
		this.fsBusinessComponent = fsBusinessComponent;
		this.fsRuleApplicationMaster = fsRuleApplicationMaster;
		this.fsRulePageMaster = fsRulePageMaster;
		this.fsCompanyMaster = fsCompanyMaster;
		this.fsCountryMaster = fsCountryMaster;
		this.levelId = levelId;
		this.createdBy = createdBy;
		this.createdTime = createdTime;
		this.updatedBy = updatedBy;
		this.updatedTime = updatedTime;
		this.fsBizComponentConfDetails = fsBizComponentConfDetails;
		this.fsBizComponentDataRefs = fsBizComponentDataRefs;
	}

	@Id
	/*@TableGenerator(name="componentconfid",table="fs_componentconfidpk",pkColumnName="componentconfidkey",pkColumnValue="componentconfidvale",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.TABLE,generator="componentconfid")
	*/
	@GeneratedValue(generator="fs_business_component_conf_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="fs_business_component_conf_seq" ,sequenceName="FS_BUSINESS_COMPONENT_CONF_SEQ",allocationSize=1)			
	@Column(name = "COMPONENT_CONF_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getComponentConfId() {
		return this.componentConfId;
	}

	public void setComponentConfId(BigDecimal componentConfId) {
		this.componentConfId = componentConfId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPONENT_ID")
	public BusinessComponent getFsBusinessComponent() {
		return this.fsBusinessComponent;
	}

	public void setFsBusinessComponent(BusinessComponent fsBusinessComponent) {
		this.fsBusinessComponent = fsBusinessComponent;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "APPLICATION_ID")
	public RuleApplicationMaster getFsRuleApplicationMaster() {
		return this.fsRuleApplicationMaster;
	}

	public void setFsRuleApplicationMaster(
			RuleApplicationMaster fsRuleApplicationMaster) {
		this.fsRuleApplicationMaster = fsRuleApplicationMaster;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PAGE_ID")
	public RulePageMaster getFsRulePageMaster() {
		return this.fsRulePageMaster;
	}

	public void setFsRulePageMaster(RulePageMaster fsRulePageMaster) {
		this.fsRulePageMaster = fsRulePageMaster;
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

	@Column(name = "LEVEL_ID", precision = 22, scale = 0)
	public BigDecimal getLevelId() {
		return this.levelId;
	}

	public void setLevelId(BigDecimal levelId) {
		this.levelId = levelId;
	}

	@Column(name = "CREATED_BY", length = 30)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "CREATED_TIME", nullable = false)
	public Date getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	@Column(name = "UPDATED_BY", length = 30)
	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Column(name = "UPDATED_TIME", nullable = false)
	public Date getUpdatedTime() {
		return this.updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsBusinessComponentConf")
	public List<BizComponentConfDetail> getFsBizComponentConfDetails() {
		return fsBizComponentConfDetails;
	}
	
	public void setFsBizComponentConfDetails(List<BizComponentConfDetail> fsBizComponentConfDetails) {
		this.fsBizComponentConfDetails = fsBizComponentConfDetails;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsBusinessComponentConf")
	public List<BizComponentDataRef> getFsBizComponentDataRefs() {
		return fsBizComponentDataRefs;
	}

	public void setFsBizComponentDataRefs(List<BizComponentDataRef> fsBizComponentDataRefs) {
		this.fsBizComponentDataRefs = fsBizComponentDataRefs;
	}
}
