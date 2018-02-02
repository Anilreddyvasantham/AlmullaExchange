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

		 File		: CityMaster.java
 
		 Project	: AlmullaExchange

		 Package	: com.amg.exchange.model
 
		 Created	:	
 						Date	: 29-May-2014 4:43:07 pm
		 				By		: Justin Vincent
 						Revision:
 
 		 Last Change:
 						Date	: 29-May-2014 4:43:07 pm
 						By		: Justin Vincent
		 				Revision:

		 Description: TODO 

********************************************************************************************************************/
@Cacheable  
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "FS_CITY_MASTER" )
public class CityMaster implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private BigDecimal cityId;
	private String cityCode;
	private DistrictMaster fsDistrictMaster;
	private String createdBy;
	private String modifiedBy;
	private Date createdDate;
	private Date modifiedDate;
	private String countryCode;
	private String stateCode;
	private String districtCode;
	private String isActive;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;
	private List<CityMasterDesc> fsCityMasterDescs = new ArrayList<CityMasterDesc>();
	private List<ContactDetail> fsContactDetails = new ArrayList<ContactDetail>();
	private List<CustomerEmploymentInfo> fsCustomerEmploymentInfos = new ArrayList<CustomerEmploymentInfo>();

	public CityMaster() {
	}

	public CityMaster(BigDecimal cityId) {
		this.cityId = cityId;
	}

	public CityMaster(BigDecimal cityId, String cityCode,
			DistrictMaster fsDistrictMaster, String createdBy,
			String modifiedBy, Date createdDate, Date modifiedDate,
			String countryCode, String stateCode, String districtCode,
			String isActive, String approvedBy, Date approvedDate,
			String remarks, List<CityMasterDesc> fsCityMasterDescs,
			List<ContactDetail> fsContactDetails,
			List<CustomerEmploymentInfo> fsCustomerEmploymentInfos) {
		super();
		this.cityId = cityId;
		this.cityCode = cityCode;
		this.fsDistrictMaster = fsDistrictMaster;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.countryCode = countryCode;
		this.stateCode = stateCode;
		this.districtCode = districtCode;
		this.isActive = isActive;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.remarks = remarks;
		this.fsCityMasterDescs = fsCityMasterDescs;
		this.fsContactDetails = fsContactDetails;
		this.fsCustomerEmploymentInfos = fsCustomerEmploymentInfos;
	}

	@Id
	@GeneratedValue(generator="fs_city_master_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="fs_city_master_seq",sequenceName="FS_CITY_MASTER_SEQ",allocationSize=1)
	@Column(name = "CITY_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getCityId() {
		return this.cityId;
	}

	public void setCityId(BigDecimal cityId) {
		this.cityId = cityId;
	}

	@Column(name = "CITY_CODE", precision = 3, scale = 0)
	public String getCityCode() {
		return this.cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	@Column(name = "CREATED_BY")
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "MODIFIED_BY")
	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCityMaster")
	public List<CityMasterDesc> getFsCityMasterDescs() {
		return this.fsCityMasterDescs;
	}

	public void setFsCityMasterDescs(List<CityMasterDesc> fsCityMasterDescs) {
		this.fsCityMasterDescs = fsCityMasterDescs;
	}
	

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCityMaster")
	public List<ContactDetail> getFsContactDetails() {
		return fsContactDetails;
	}

	public void setFsContactDetails(List<ContactDetail> fsContactDetails) {
		this.fsContactDetails = fsContactDetails;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DISTRICT_ID")
	public DistrictMaster getFsDistrictMaster() {
		return this.fsDistrictMaster;
	}

	public void setFsDistrictMaster(DistrictMaster fsDistrictMaster) {
		this.fsDistrictMaster = fsDistrictMaster;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCityMaster")
	public List<CustomerEmploymentInfo> getFsCustomerEmploymentInfos() {
		return fsCustomerEmploymentInfos;
	}

	public void setFsCustomerEmploymentInfos(
			List<CustomerEmploymentInfo> fsCustomerEmploymentInfos) {
		this.fsCustomerEmploymentInfos = fsCustomerEmploymentInfos;
	}

	@Column(name = "COUNTRY_CODE")
	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	@Column(name = "STATE_CODE")
	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	@Column(name = "DISTRICT_CODE")
	public String getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}

	@Column(name = "ISACTIVE")
	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Column(name = "REMARKS")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	

}
