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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/*******************************************************************************************************************

		 File		: RulePageMaster.java
 
		 Project	: AlmullaExchange

		 Package	: com.amg.exchange.model
 
		 Created	:	
 						Date	: 29-May-2014 5:17:32 pm
		 				By		: Justin Vincent
 						Revision:
 
 		 Last Change:
 						Date	: 29-May-2014 5:17:32 pm
 						By		: Justin Vincent
		 				Revision:

		 Description: TODO 

********************************************************************************************************************/
@Cacheable  
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "FS_RULE_PAGE_MASTER" )
public class RulePageMaster implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private BigDecimal pageId;
	private RuleApplicationMaster fsRuleApplicationMaster;
	private String pageCode;
	private String pageName;
	private String pageActive;
	private List<BusinessComponentConf> fsBusinessComponentConfs = new ArrayList<BusinessComponentConf>();

	public RulePageMaster() {
	}

	public RulePageMaster(BigDecimal pageId) {
		this.pageId = pageId;
	}

	public RulePageMaster(BigDecimal pageId,
			RuleApplicationMaster fsRuleApplicationMaster, String pageCode,
			String pageName, String pageActive,
			List<BusinessComponentConf> fsBusinessComponentConfs) {
		this.pageId = pageId;
		this.fsRuleApplicationMaster = fsRuleApplicationMaster;
		this.pageCode = pageCode;
		this.pageName = pageName;
		this.pageActive = pageActive;
		this.fsBusinessComponentConfs = fsBusinessComponentConfs;
	}

	/*@Id
	@TableGenerator(name="pageid",table="fs_pageidpk",pkColumnName="pageidkey",pkColumnValue="pageidvalue",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.TABLE,generator="pageid")*/
	
	@Id
	@GeneratedValue(generator="fs_rule_page_master_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="fs_rule_page_master_seq",sequenceName="FS_RULE_PAGE_MASTER_SEQ",allocationSize=1)
	@Column(name = "PAGE_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getPageId() {
		return this.pageId;
	}

	public void setPageId(BigDecimal pageId) {
		this.pageId = pageId;
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

	@Column(name = "PAGE_CODE", length = 100)
	public String getPageCode() {
		return this.pageCode;
	}

	public void setPageCode(String pageCode) {
		this.pageCode = pageCode;
	}

	@Column(name = "PAGE_NAME", length = 45)
	public String getPageName() {
		return this.pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	@Column(name = "PAGE_ACTIVE", length = 1)
	public String getPageActive() {
		return this.pageActive;
	}

	public void setPageActive(String pageActive) {
		this.pageActive = pageActive;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsRulePageMaster")
	public List<BusinessComponentConf> getFsBusinessComponentConfs() {
		return fsBusinessComponentConfs;
	}

	public void setFsBusinessComponentConfs(
			List<BusinessComponentConf> fsBusinessComponentConfs) {
		this.fsBusinessComponentConfs = fsBusinessComponentConfs;
	}

}
