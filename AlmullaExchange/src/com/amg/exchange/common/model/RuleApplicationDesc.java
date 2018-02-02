package com.amg.exchange.common.model;

import java.math.BigDecimal;

import javax.persistence.Cacheable;
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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/*******************************************************************************************************************

		 File		: RuleApplicationDesc.java
 
		 Project	: AlmullaExchange

		 Package	: com.amg.exchange.model
 
		 Created	:	
 						Date	: 29-May-2014 5:16:33 pm
		 				By		: Justin Vincent
 						Revision:
 
 		 Last Change:
 						Date	: 29-May-2014 5:16:33 pm
 						By		: Justin Vincent
		 				Revision:

		 Description: TODO 

********************************************************************************************************************/
@Cacheable  
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "FS_RULE_APPLICATION_DESC" )
public class RuleApplicationDesc implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private BigDecimal applicationDescId;
	private RuleApplicationMaster ruleApplicationMaster;
	private CompanyMaster fsCompanyMaster;
	private CountryMaster countryMaster;

	public RuleApplicationDesc() {
	}

	public RuleApplicationDesc(BigDecimal applicationDescId) {
		this.applicationDescId = applicationDescId;
	}

	public RuleApplicationDesc(BigDecimal applicationDescId,
			RuleApplicationMaster ruleApplicationMaster,
			CompanyMaster fsCompanyMaster, CountryMaster countryMaster) {
		this.applicationDescId = applicationDescId;
		this.ruleApplicationMaster = ruleApplicationMaster;
		this.setFsCompanyMaster(fsCompanyMaster);
		this.countryMaster = countryMaster;
	}

	@Id
	@GeneratedValue(generator="fs_rule_application_desc_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="fs_rule_application_desc_seq",sequenceName="FS_RULE_APPLICATION_DESC_SEQ",allocationSize=1)
	@Column(name = "APPLICATION_DESC_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getApplicationDescId() {
		return this.applicationDescId;
	}

	public void setApplicationDescId(BigDecimal applicationDescId) {
		this.applicationDescId = applicationDescId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "APPLICATION_ID")
	public RuleApplicationMaster getFsRuleApplicationMaster() {
		return this.ruleApplicationMaster;
	}

	public void setFsRuleApplicationMaster(
			RuleApplicationMaster ruleApplicationMaster) {
		this.ruleApplicationMaster = ruleApplicationMaster;
	}

	/*@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPANY_ID")
	public CompanyMaster getFscompanyMaster() {
		return this.companyMaster;
	}

	public void setFscompanyMaster(CompanyMaster companyMaster) {
		this.companyMaster = companyMaster;
	}*/
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPANY_ID")
	public CompanyMaster getFsCompanyMaster() {
		return fsCompanyMaster;
	}

	public void setFsCompanyMaster(CompanyMaster fsCompanyMaster) {
		this.fsCompanyMaster = fsCompanyMaster;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_ID")
	public CountryMaster getFsCountryMaster() {
		return this.countryMaster;
	}

	public void setFsCountryMaster(CountryMaster countryMaster) {
		this.countryMaster = countryMaster;
	}

}
