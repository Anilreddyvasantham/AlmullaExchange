package com.amg.exchange.common.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import com.amg.exchange.registration.model.ContactDetail;
import com.amg.exchange.registration.model.CustomerEmploymentInfo;

/*******************************************************************************************************************

		 File		: StateMaster.java
 
		 Project	: AlmullaExchange

		 Package	: com.amg.exchange.model
 
		 Created	:	
 						Date	: 29-May-2014 5:17:48 pm
		 				By		: Justin Vincent
 						Revision:
 
 		 Last Change:
 						Date	: 29-May-2014 5:17:48 pm
 						By		: Justin Vincent
		 				Revision:

		 Description: TODO 

********************************************************************************************************************/
@Cacheable  
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "FS_STATE_MASTER" )
public class StateMaster implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private BigDecimal stateId;
	private String stateCode;
	private CountryMaster fsCountryMaster;
	private String createdBy;
	private Date createdDate;
	/*private String updatedBy;
	 * private String stateActive;
	private Date lastUpdated;*/
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String Remarks;
	private String isActive;
	private List<StateMasterDesc> fsStateMasterDescs = new ArrayList<StateMasterDesc>();
	private List<ContactDetail> fsContactDetails = new ArrayList<ContactDetail>();
	private List<DistrictMaster> fsDistrictMasters = new ArrayList<DistrictMaster>();
	private List<CustomerEmploymentInfo> fsCustomerEmploymentInfos = new ArrayList<CustomerEmploymentInfo>();

	public StateMaster() {
	}

	public StateMaster(BigDecimal stateId) {
		this.stateId = stateId;
	}

	

	public StateMaster(BigDecimal stateId, String stateCode, CountryMaster fsCountryMaster, String createdBy, Date createdDate, String modifiedBy, Date modifiedDate, String approvedBy, Date approvedDate, String remarks, String isActive, List<StateMasterDesc> fsStateMasterDescs,
			List<ContactDetail> fsContactDetails, List<DistrictMaster> fsDistrictMasters, List<CustomerEmploymentInfo> fsCustomerEmploymentInfos) {
		super();
		this.stateId = stateId;
		this.stateCode = stateCode;
		this.fsCountryMaster = fsCountryMaster;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		Remarks = remarks;
		this.isActive = isActive;
		this.fsStateMasterDescs = fsStateMasterDescs;
		this.fsContactDetails = fsContactDetails;
		this.fsDistrictMasters = fsDistrictMasters;
		this.fsCustomerEmploymentInfos = fsCustomerEmploymentInfos;
	}

	/*@Id
	@TableGenerator(name = "stateid", table = "fs_stateidpk", pkColumnName = "stateidkey", pkColumnValue = "stateidvalue", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "stateid")*/
	
	@Id
	@GeneratedValue(generator="fs_state_master_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="fs_state_master_seq",sequenceName="FS_STATE_MASTER_SEQ",allocationSize=1)
	@Column(name = "STATE_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getStateId() {
		return this.stateId;
	}

	public void setStateId(BigDecimal stateId) {
		this.stateId = stateId;
	}

	@Column(name = "STATE_CODE", precision = 3, scale = 0)
	public String getStateCode() {
		return this.stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	/*@Column(name = "STATE_ACTIVE", length = 1)
	public String getStateActive() {
		return this.stateActive;
	}

	public void setStateActive(String stateActive) {
		this.stateActive = stateActive;
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
	}*/
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
		return Remarks;
	}
	
	public void setRemarks(String remarks) {
		Remarks = remarks;
	}
	@Column(name = "ISACTIVE")
	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	 
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsStateMaster")
	public List<StateMasterDesc> getFsStateMasterDescs() {
		return this.fsStateMasterDescs;
	}

	

	public void setFsStateMasterDescs(List<StateMasterDesc> fsStateMasterDescs) {
		this.fsStateMasterDescs = fsStateMasterDescs;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsStateMaster")
	public List<DistrictMaster> getFsDistrictMasters() {
		return fsDistrictMasters;
	}

	public void setFsDistrictMasters(List<DistrictMaster> fsDistrictMasters) {
		this.fsDistrictMasters = fsDistrictMasters;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsStateMaster")
	public List<ContactDetail> getFsContactDetails() {
		return fsContactDetails;
	}

	public void setFsContactDetails(List<ContactDetail> fsContactDetails) {
		this.fsContactDetails = fsContactDetails;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_ID")
	public CountryMaster getFsCountryMaster() {
		return this.fsCountryMaster;
	}

	public void setFsCountryMaster(CountryMaster fsCountryMaster) {
		this.fsCountryMaster = fsCountryMaster;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsStateMaster")
	public List<CustomerEmploymentInfo> getFsCustomerEmploymentInfos() {
		return fsCustomerEmploymentInfos;
	}

	public void setFsCustomerEmploymentInfos(
			List<CustomerEmploymentInfo> fsCustomerEmploymentInfos) {
		this.fsCustomerEmploymentInfos = fsCustomerEmploymentInfos;
	}
	
}
