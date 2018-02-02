package com.amg.exchange.registration.model;

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

import com.amg.exchange.common.model.BizComponentData;
import com.amg.exchange.common.model.LanguageType;

/*******************************************************************************************************************

		 File		: CustCorporateAddlDetail.java
 
		 Project	: AlmullaExchange

		 Package	: com.amg.exchange.model
 
		 Created	:	
 						Date	: 29-May-2014 5:01:25 pm
		 				By		: Justin Vincent
 						Revision:
 
 		 Last Change:
 						Date	: 29-May-2014 5:01:25 pm
 						By		: Justin Vincent
		 				Revision:

		 Description: TODO 

********************************************************************************************************************/

@Entity
@Table(name = "FS_CUST_CORPORATE_ADDL_DETAIL" )
public class CustCorporateAddlDetail implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private BigDecimal corpAddlId;
	private BizComponentData fsBizComponentDataByObjectiveId;
	private LanguageType fsLanguageType;
	private Customer fsCustomer;
	private String createdBy;
	private String updatedBy;
	private Date creationDate;
	private Date lastUpdated;
	private String addlStatus;
	private String objectiveType;

	public CustCorporateAddlDetail() {
	}

	public CustCorporateAddlDetail(BigDecimal corpAddlId) {
		this.corpAddlId = corpAddlId;
	}
	
	public CustCorporateAddlDetail(BigDecimal corpAddlId, BizComponentData fsBizComponentDataByObjectiveId, LanguageType fsLanguageType, Customer fsCustomer, String createdBy, String updatedBy, Date creationDate, Date lastUpdated, String addlStatus) {

		this.corpAddlId = corpAddlId;
		this.fsBizComponentDataByObjectiveId = fsBizComponentDataByObjectiveId;
		this.fsLanguageType = fsLanguageType;
		this.fsCustomer = fsCustomer;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.creationDate = creationDate;
		this.lastUpdated = lastUpdated;
		this.addlStatus = addlStatus;
	}

	@Id
	/*@TableGenerator(name="corpaddlid",table="fs_corpaddlidpk",pkColumnName="corpaddlidkey",pkColumnValue="corpaddlidvalue",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.TABLE,generator="corpaddlid")*/
	@GeneratedValue(generator="fs_cust_corp_addl_detail_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="fs_cust_corp_addl_detail_seq" ,sequenceName="FS_CUST_CORP_ADDL_DETAIL_SEQ",allocationSize=1)		
	@Column(name = "CORP_ADDL_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getCorpAddlId() {
		return this.corpAddlId;
	}

	public void setCorpAddlId(BigDecimal corpAddlId) {
		this.corpAddlId = corpAddlId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OBJECTIVE_ID")
	public BizComponentData getFsBizComponentDataByObjectiveId() {
		return fsBizComponentDataByObjectiveId;
	}
	
	public void setFsBizComponentDataByObjectiveId(BizComponentData fsBizComponentDataByObjectiveId) {
		this.fsBizComponentDataByObjectiveId = fsBizComponentDataByObjectiveId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LANGUAGE_ID")
	public LanguageType getFsLanguageType() {
		return this.fsLanguageType;
	}

	public void setFsLanguageType(LanguageType fsLanguageType) {
		this.fsLanguageType = fsLanguageType;
	}

	@Column(name = "CREATED_BY", length = 30)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "UPDATED_BY", length = 30)
	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Column(name = "CREATION_DATE")
	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "LAST_UPDATED")
	public Date getLastUpdated() {
		return this.lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMER_ID")
	public Customer getFsCustomer() {
		return fsCustomer;
	}

	public void setFsCustomer(Customer fsCustomer) {
		this.fsCustomer = fsCustomer;
	}

	@Column(name = "ADDL_STATUS", length = 1)
	public String getAddlStatus() {
		return this.addlStatus;
	}

	public void setAddlStatus(String addlStatus) {
		this.addlStatus = addlStatus;
	}

	@Column(name = "OBJECTIVE_TYPE", length = 50)
	public String getObjectiveType() {
		return objectiveType;
	}

	public void setObjectiveType(String objectiveType) {
		this.objectiveType = objectiveType;
	}
	
}
