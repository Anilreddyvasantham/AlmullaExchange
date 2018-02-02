package com.amg.exchange.common.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/*******************************************************************************************************************

		 File		: RuleApplicationMaster.java
 
		 Project	: AlmullaExchange

		 Package	: com.amg.exchange.model
 
		 Created	:	
 						Date	: 29-May-2014 5:17:17 pm
		 				By		: Justin Vincent
 						Revision:
 
 		 Last Change:
 						Date	: 29-May-2014 5:17:17 pm
 						By		: Justin Vincent
		 				Revision:

		 Description: TODO 

********************************************************************************************************************/
@Cacheable  
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "FS_RULE_APPLICATION_MASTER" )
public class RuleApplicationMaster implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private BigDecimal applicationId;
	private String applicationCode;
	private String applicationName;
	private List<RulePageMaster> fsRulePageMasters = new ArrayList<RulePageMaster>();
	private List<RuleApplicationDesc> fsRuleApplicationDescs = new ArrayList<RuleApplicationDesc>();
	private List<BusinessComponentConf> fsBusinessComponentConfs = new ArrayList<BusinessComponentConf>();

	public RuleApplicationMaster() {
	}

	public RuleApplicationMaster(BigDecimal applicationId) {
		this.applicationId = applicationId;
	}

	public RuleApplicationMaster(BigDecimal applicationId,
			String applicationCode, String applicationName,
			List<RulePageMaster> fsRulePageMasters,
			List<RuleApplicationDesc> fsRuleApplicationDescs,
			List<BusinessComponentConf> fsBusinessComponentConfs) {
		super();
		this.applicationId = applicationId;
		this.applicationCode = applicationCode;
		this.applicationName = applicationName;
		this.fsRulePageMasters = fsRulePageMasters;
		this.fsRuleApplicationDescs = fsRuleApplicationDescs;
		this.fsBusinessComponentConfs = fsBusinessComponentConfs;
	}

	@Id
	@GeneratedValue(generator="fs_rule_application_master_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="fs_rule_application_master_seq",sequenceName="FS_RULE_APPLICATION_MASTER_SEQ",allocationSize=1)
	@Column(name = "APPLICATION_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getApplicationId() {
		return this.applicationId;
	}

	public void setApplicationId(BigDecimal applicationId) {
		this.applicationId = applicationId;
	}

	@Column(name = "APPLICATION_CODE", length = 10)
	public String getApplicationCode() {
		return this.applicationCode;
	}

	public void setApplicationCode(String applicationCode) {
		this.applicationCode = applicationCode;
	}

	@Column(name = "APPLICATION_NAME", length = 45)
	public String getApplicationName() {
		return this.applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsRuleApplicationMaster")
	public List<RulePageMaster> getFsRulePageMasters() {
		return this.fsRulePageMasters;
	}

	public void setFsRulePageMasters(List<RulePageMaster> fsRulePageMasters) {
		this.fsRulePageMasters = fsRulePageMasters;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsRuleApplicationMaster")
	public List<RuleApplicationDesc> getFsRuleApplicationDescs() {
		return this.fsRuleApplicationDescs;
	}

	public void setFsRuleApplicationDescs(List<RuleApplicationDesc> fsRuleApplicationDescs) {
		this.fsRuleApplicationDescs = fsRuleApplicationDescs;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsRuleApplicationMaster")
	public List<BusinessComponentConf> getFsBusinessComponentConfs() {
		return fsBusinessComponentConfs;
	}

	public void setFsBusinessComponentConfs(
			List<BusinessComponentConf> fsBusinessComponentConfs) {
		this.fsBusinessComponentConfs = fsBusinessComponentConfs;
	}

}
