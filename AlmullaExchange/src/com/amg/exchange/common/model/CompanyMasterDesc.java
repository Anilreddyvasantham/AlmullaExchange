package com.amg.exchange.common.model;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
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

/*******************************************************************************************************************

		 File		: CompanyMasterDesc.java
 
		 Project	: AlmullaExchange

		 Package	: com.amg.exchange.model
 
		 Created	:	
 						Date	: 29-May-2014 4:44:28 pm
		 				By		: Justin Vincent
 						Revision:
 
 		 Last Change:
 						Date	: 29-May-2014 4:44:28 pm
 						By		: Justin Vincent
		 				Revision:

		 Description: TODO 

********************************************************************************************************************/
@Entity
@Table(name = "FS_COMPANY_MASTER_DESC" )
public class CompanyMasterDesc implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private BigDecimal companyMasterId;
	private LanguageType fsLanguageType;
	private String companyName;
	private CompanyMaster fsCompanyMaster;
	//private String companyShortName;
	
	
	public CompanyMasterDesc() {
	}

	public CompanyMasterDesc(BigDecimal companyMasterId) {
		this.companyMasterId = companyMasterId;
	}

	public CompanyMasterDesc(BigDecimal companyMasterId,
			LanguageType fsLanguageType, CompanyMaster fsCompanyMaster,
			String companyName,String companyShortName) {
		this.companyMasterId = companyMasterId;
		this.fsLanguageType = fsLanguageType;
		this.fsCompanyMaster = fsCompanyMaster;
		this.companyName = companyName;
		//this.companyShortName = companyShortName;
	}
	
	@Id
	//@TableGenerator(name="companymasterid",table="fs_companymasteridpk",pkColumnName="companymasteridkey",pkColumnValue="companymasteridvalue",allocationSize=1)
	//@GeneratedValue(strategy=GenerationType.TABLE,generator="companymasterid")
	@GeneratedValue(generator = "fs_company_master_desc_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "fs_company_master_desc_seq", sequenceName = "FS_COMPANY_MASTER_DESC_SEQ", allocationSize = 1)
	@Column(name = "COMPANY_MASTER_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getCompanyMasterId() {
		return this.companyMasterId;
	}

	public void setCompanyMasterId(BigDecimal companyMasterId) {
		this.companyMasterId = companyMasterId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LANGUAGE_ID")
	public LanguageType getFsLanguageType() {
		return this.fsLanguageType;
	}

	public void setFsLanguageType(LanguageType fsLanguageType) {
		this.fsLanguageType = fsLanguageType;
	}

	/*@Column(name = "COMPANY_ID", precision = 22, scale = 0)
	public BigDecimal getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}*/

	@Column(name = "COMPANY_NAME", length = 245)
	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPANY_ID")
	public CompanyMaster getFsCompanyMaster() {
		return fsCompanyMaster;
	}

	public void setFsCompanyMaster(CompanyMaster fsCompanyMaster) {
		this.fsCompanyMaster = fsCompanyMaster;
	}

	/*@Column(name="COMPANY_SHORT_NAME")
	public String getCompanyShortName() {
		return companyShortName;
	}

	public void setCompanyShortName(String companyShortName) {
		this.companyShortName = companyShortName;
	}*/
	
	
	
	
}
