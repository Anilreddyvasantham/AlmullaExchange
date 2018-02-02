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

import com.amg.exchange.registration.model.ContactDetail;
import com.amg.exchange.registration.model.CustomerEmploymentInfo;

/*******************************************************************************************************************

		 File		: DistrictMaster.java
 
		 Project	: AlmullaExchange

		 Package	: com.amg.exchange.model
 
		 Created	:	
 						Date	: 29-May-2014 5:12:59 pm
		 				By		: Justin Vincent
 						Revision:
 
 		 Last Change:
 						Date	: 29-May-2014 5:12:59 pm
 						By		: Justin Vincent
		 				Revision:

		 Description: TODO 

********************************************************************************************************************/
@Cacheable  
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "FS_DISTRICT_MASTER" )
public class DistrictMaster implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private BigDecimal districtId;
	private StateMaster fsStateMaster;
	private String districtCode;
	private String districtActive;
	private String createdBy;
	private String updatedBy;
	private String approvedBy;
	private Date creationDate;
	private Date lastUpdated;
	private Date approvedDate;
	private String stateCode;
	private String remarks;
	private List<DistrictMasterDesc> fsDistrictMasterDescs = new ArrayList<DistrictMasterDesc>();
	private List<ContactDetail> fsContactDetails = new ArrayList<ContactDetail>();
	private List<CityMaster> fsCityMasters = new ArrayList<CityMaster>();
	private List<CustomerEmploymentInfo> fsCustomerEmploymentInfos = new ArrayList<CustomerEmploymentInfo>();

	public DistrictMaster() {
	}

	public DistrictMaster(BigDecimal districtId) {
		this.districtId = districtId;
	}

	

	public DistrictMaster(BigDecimal districtId, StateMaster fsStateMaster, String districtCode, String districtActive, String createdBy, String updatedBy, String approvedBy, Date creationDate, Date lastUpdated, Date approvedDate, String stateCode, String remarks,
			List<DistrictMasterDesc> fsDistrictMasterDescs, List<ContactDetail> fsContactDetails, List<CityMaster> fsCityMasters, List<CustomerEmploymentInfo> fsCustomerEmploymentInfos) {
		this.districtId = districtId;
		this.fsStateMaster = fsStateMaster;
		this.districtCode = districtCode;
		this.districtActive = districtActive;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.approvedBy = approvedBy;
		this.creationDate = creationDate;
		this.lastUpdated = lastUpdated;
		this.approvedDate = approvedDate;
		this.stateCode = stateCode;
		this.remarks = remarks;
		this.fsDistrictMasterDescs = fsDistrictMasterDescs;
		this.fsContactDetails = fsContactDetails;
		this.fsCityMasters = fsCityMasters;
		this.fsCustomerEmploymentInfos = fsCustomerEmploymentInfos;
	}

	/*@Id
	@TableGenerator(name = "districtid", table = "fs_districtidpk", pkColumnName = "districtidkey", pkColumnValue = "districtidvalue", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "districtid")*/
	
	@Id
	@GeneratedValue(generator="fs_district_master_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="fs_district_master_seq",sequenceName="FS_DISTRICT_MASTER_SEQ",allocationSize=1)
	@Column(name = "DISTRICT_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getDistrictId() {
		return this.districtId;
	}

	public void setDistrictId(BigDecimal districtId) {
		this.districtId = districtId;
	}

	@Column(name = "DISTRICT_CODE", precision = 3, scale = 0)
	public String getDistrictCode() {
		return this.districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}

	@Column(name = "ISACTIVE", length = 1)
	public String getDistrictActive() {
		return this.districtActive;
	}

	public void setDistrictActive(String districtActive) {
		this.districtActive = districtActive;
	}

	@Column(name = "CREATED_BY", length = 30)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "MODIFIED_BY", length = 30)
	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Column(name = "CREATED_DATE")
	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "MODIFIED_DATE")
	public Date getLastUpdated() {
		return this.lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsDistrictMaster")
	public List<DistrictMasterDesc> getFsDistrictMasterDescs() {
		return this.fsDistrictMasterDescs;
	}

	public void setFsDistrictMasterDescs(
			List<DistrictMasterDesc> fsDistrictMasterDescs) {
		this.fsDistrictMasterDescs = fsDistrictMasterDescs;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsDistrictMaster")
	public List<ContactDetail> getFsContactDetails() {
		return fsContactDetails;
	}

	public void setFsContactDetails(List<ContactDetail> fsContactDetails) {
		this.fsContactDetails = fsContactDetails;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsDistrictMaster")
	public List<CityMaster> getFsCityMasters() {
		return fsCityMasters;
	}

	public void setFsCityMasters(List<CityMaster> fsCityMasters) {
		this.fsCityMasters = fsCityMasters;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STATE_ID")
	public StateMaster getFsStateMaster() {
		return fsStateMaster;
	}
	
	

	public void setFsStateMaster(StateMaster fsStateMaster) {
		this.fsStateMaster = fsStateMaster;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsDistrictMaster")
	public List<CustomerEmploymentInfo> getFsCustomerEmploymentInfos() {
		return fsCustomerEmploymentInfos;
	}

	public void setFsCustomerEmploymentInfos(
			List<CustomerEmploymentInfo> fsCustomerEmploymentInfos) {
		this.fsCustomerEmploymentInfos = fsCustomerEmploymentInfos;
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
	@Column(name = "STATE_CODE")
	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	@Column(name = "REMARKS")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
}
