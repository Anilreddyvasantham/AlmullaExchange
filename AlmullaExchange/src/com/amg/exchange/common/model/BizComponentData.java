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
import com.amg.exchange.registration.model.CustCorporateAddlDetail;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerEmploymentInfo;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.registration.model.CustomerLogin;
import com.amg.exchange.remittance.model.BankCharges;

/*******************************************************************************************************************

		 File		: BizComponentData.java
 
		 Project	: AlmullaExchange

		 Package	: com.amg.exchange.model
 
		 Created	:	
 						Date	: 29-May-2014 3:37:30 pm
		 				By		: Justin Vincent
 						Revision:
 
 		 Last Change:
 						Date	: 29-May-2014 3:37:30 pm
 						By		: Justin Vincent
		 				Revision:

		 Description: TODO 

********************************************************************************************************************/
@SuppressWarnings("serial")
@Cacheable  
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "FS_BIZ_COMPONENT_DATA")
public class BizComponentData implements java.io.Serializable {

	private BigDecimal componentDataId;
	private BusinessComponent fsBusinessComponent;
	private String componentCode;
	private String active;
	private String createdBy;
	private Date createDate;
	private String modifiedBy;
	private Date modifiedDate;
	private List<CustomerLogin> fsCustomerLoginsForSecurityQuestion1 = new ArrayList<CustomerLogin>();
	private List<CustomerLogin> fsCustomerLoginsForSecurityQuestion2 = new ArrayList<CustomerLogin>();
	private List<CustomerLogin> fsCustomerLoginsForSecurityQuestion3 = new ArrayList<CustomerLogin>();
	private List<CustomerLogin> fsCustomerLoginsForSecurityQuestion4 = new ArrayList<CustomerLogin>();
	private List<CustomerLogin> fsCustomerLoginsForSecurityQuestion5 = new ArrayList<CustomerLogin>();
	private List<CustomerIdProof> fsCustomerIdProofsForIdentityTypeId = new ArrayList<CustomerIdProof>();
	private List<CustomerIdProof> fsCustomerIdProofsForCustomerTypeId = new ArrayList<CustomerIdProof>();
	private List<CustomerIdProof> fsCustomerIdProofsForIdentityFor = new ArrayList<CustomerIdProof>();
	private List<BizComponentDataDesc> fsBizComponentDataDescs = new ArrayList<BizComponentDataDesc>();
	private List<BizComponentDataRef> fsBizComponentDataRefs = new ArrayList<BizComponentDataRef>();
	private List<Customer> fsCustomersForCustomerTypeId = new ArrayList<Customer>();
	private List<Customer> fsCustomersForGroupId = new ArrayList<Customer>();
	private List<CustomerEmploymentInfo> fsCustomerEmploymentInfosForOccupationId = new ArrayList<CustomerEmploymentInfo>();
	private List<Customer> fsCustomersForObjectiveId = new ArrayList<Customer>();
	private List<ContactDetail> fsContactDetails = new ArrayList<ContactDetail>();
	private List<CustomerEmploymentInfo> fsCustomerEmploymentInfosForEmploymentTypeId = new ArrayList<CustomerEmploymentInfo>();
	private List<CustCorporateAddlDetail> fsCustCorporateAddlDetails = new ArrayList<CustCorporateAddlDetail>();
	private List<BankCharges> exbankcharge = new ArrayList<BankCharges>();

	public BizComponentData() {
	}
	
	public BizComponentData(BigDecimal componentDataId) {
		this.componentDataId = componentDataId;
	}

	public BizComponentData(BigDecimal componentDataId,
			BusinessComponent fsBusinessComponent, Date createDate,
			Date modifiedDate) {
		this.componentDataId = componentDataId;
		this.fsBusinessComponent = fsBusinessComponent;
		this.createDate = createDate;
		this.modifiedDate = modifiedDate;
	}

	public BizComponentData(BigDecimal componentDataId, BusinessComponent fsBusinessComponent, String componentCode, String active, String createdBy, Date createDate, String modifiedBy, Date modifiedDate, List<CustomerLogin> fsCustomerLoginsForSecurityQuestion1, List<CustomerLogin> fsCustomerLoginsForSecurityQuestion2, List<CustomerLogin> fsCustomerLoginsForSecurityQuestion3, List<CustomerLogin> fsCustomerLoginsForSecurityQuestion4, List<CustomerLogin> fsCustomerLoginsForSecurityQuestion5, List<CustomerIdProof> fsCustomerIdProofsForIdentityTypeId, List<CustomerIdProof> fsCustomerIdProofsForCustomerTypeId, List<CustomerIdProof> fsCustomerIdProofsForIdentityFor, List<BizComponentDataDesc> fsBizComponentDataDescs, List<BizComponentDataRef> fsBizComponentDataRefs,
			List<Customer> fsCustomersForCustomerTypeId, List<Customer> fsCustomersForGroupId, List<CustomerEmploymentInfo> fsCustomerEmploymentInfosForOccupationId, List<Customer> fsCustomersForObjectiveId, List<ContactDetail> fsContactDetails, List<CustomerEmploymentInfo> fsCustomerEmploymentInfosForEmploymentTypeId, List<CustCorporateAddlDetail> fsCustCorporateAddlDetails,
			List<BankCharges> exbankcharge) {
		this.componentDataId = componentDataId;
		this.fsBusinessComponent = fsBusinessComponent;
		this.componentCode = componentCode;
		this.active = active;
		this.createdBy = createdBy;
		this.createDate = createDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.fsCustomerLoginsForSecurityQuestion1 = fsCustomerLoginsForSecurityQuestion1;
		this.fsCustomerLoginsForSecurityQuestion2 = fsCustomerLoginsForSecurityQuestion2;
		this.fsCustomerLoginsForSecurityQuestion3 = fsCustomerLoginsForSecurityQuestion3;
		this.fsCustomerLoginsForSecurityQuestion4 = fsCustomerLoginsForSecurityQuestion4;
		this.fsCustomerLoginsForSecurityQuestion5 = fsCustomerLoginsForSecurityQuestion5;
		this.fsCustomerIdProofsForIdentityTypeId = fsCustomerIdProofsForIdentityTypeId;
		this.fsCustomerIdProofsForCustomerTypeId = fsCustomerIdProofsForCustomerTypeId;
		this.fsCustomerIdProofsForIdentityFor = fsCustomerIdProofsForIdentityFor;
		this.fsBizComponentDataDescs = fsBizComponentDataDescs;
		this.fsBizComponentDataRefs = fsBizComponentDataRefs;
		this.fsCustomersForCustomerTypeId = fsCustomersForCustomerTypeId;
		this.fsCustomersForGroupId = fsCustomersForGroupId;
		this.fsCustomerEmploymentInfosForOccupationId = fsCustomerEmploymentInfosForOccupationId;
		this.fsCustomersForObjectiveId = fsCustomersForObjectiveId;
		this.fsContactDetails = fsContactDetails;
		this.fsCustomerEmploymentInfosForEmploymentTypeId = fsCustomerEmploymentInfosForEmploymentTypeId;
		this.fsCustCorporateAddlDetails = fsCustCorporateAddlDetails;
		this.exbankcharge = exbankcharge;
	}

	@Id
/*	@TableGenerator(name="componentDataId",table="fs_componentDataIdpk",pkColumnName="componentDataIdkey",pkColumnValue="componentDataIdValue",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.TABLE,generator="componentDataId")
	*/
	@GeneratedValue(generator="fs_biz_component_data_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="fs_biz_component_data_seq" ,sequenceName="FS_BIZ_COMPONENT_DATA_SEQ",allocationSize=1)		
	@Column(name = "COMPONENT_DATA_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getComponentDataId() {
		return this.componentDataId;
	}

	public void setComponentDataId(BigDecimal componentDataId) {
		this.componentDataId = componentDataId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPONENT_ID", nullable = false)
	public BusinessComponent getFsBusinessComponent() {
		return this.fsBusinessComponent;
	}

	public void setFsBusinessComponent(BusinessComponent fsBusinessComponent) {
		this.fsBusinessComponent = fsBusinessComponent;
	}

	@Column(name = "COMPONENT_CODE", length = 30)
	public String getComponentCode() {
		return this.componentCode;
	}

	public void setComponentCode(String componentCode) {
		this.componentCode = componentCode;
	}

	@Column(name = "ACTIVE", length = 1)
	public String getActive() {
		return this.active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	@Column(name = "CREATED_BY", length = 50)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "CREATE_DATE", nullable = false)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "MODIFIED_BY", length = 50)
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "MODIFIED_DATE", nullable = false)
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsBizComponentDataBySecurityQuestion4")
	public List<CustomerLogin> getFsCustomerLoginsForSecurityQuestion4() {
		return this.fsCustomerLoginsForSecurityQuestion4;
	}

	public void setFsCustomerLoginsForSecurityQuestion4(List<CustomerLogin> fsCustomerLoginsForSecurityQuestion4) {
		this.fsCustomerLoginsForSecurityQuestion4 = fsCustomerLoginsForSecurityQuestion4;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsBizComponentDataBySecurityQuestion5")
	public List<CustomerLogin> getFsCustomerLoginsForSecurityQuestion5() {
		return this.fsCustomerLoginsForSecurityQuestion5;
	}

	public void setFsCustomerLoginsForSecurityQuestion5(List<CustomerLogin> fsCustomerLoginsForSecurityQuestion5) {
		this.fsCustomerLoginsForSecurityQuestion5 = fsCustomerLoginsForSecurityQuestion5;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsBizComponentDataByIdentityTypeId")
	public List<CustomerIdProof> getFsCustomerIdProofsForIdentityTypeId() {
		return this.fsCustomerIdProofsForIdentityTypeId;
	}

	public void setFsCustomerIdProofsForIdentityTypeId(List<CustomerIdProof> fsCustomerIdProofsForIdentityTypeId) {
		this.fsCustomerIdProofsForIdentityTypeId = fsCustomerIdProofsForIdentityTypeId;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsBizComponentDataByCustomerTypeId")
	public List<CustomerIdProof> getFsCustomerIdProofsForCustomerTypeId() {
		return this.fsCustomerIdProofsForCustomerTypeId;
	}

	public void setFsCustomerIdProofsForCustomerTypeId(List<CustomerIdProof> fsCustomerIdProofsForCustomerTypeId) {
		this.fsCustomerIdProofsForCustomerTypeId = fsCustomerIdProofsForCustomerTypeId;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsBizComponentDataBySecurityQuestion1")
	public List<CustomerLogin> getFsCustomerLoginsForSecurityQuestion1() {
		return this.fsCustomerLoginsForSecurityQuestion1;
	}

	public void setFsCustomerLoginsForSecurityQuestion1(List<CustomerLogin> fsCustomerLoginsForSecurityQuestion1) {
		this.fsCustomerLoginsForSecurityQuestion1 = fsCustomerLoginsForSecurityQuestion1;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsBizComponentDataBySecurityQuestion2")
	public List<CustomerLogin> getFsCustomerLoginsForSecurityQuestion2() {
		return this.fsCustomerLoginsForSecurityQuestion2;
	}

	public void setFsCustomerLoginsForSecurityQuestion2(List<CustomerLogin> fsCustomerLoginsForSecurityQuestion2) {
		this.fsCustomerLoginsForSecurityQuestion2 = fsCustomerLoginsForSecurityQuestion2;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsBizComponentDataByIdentityFor")
	public List<CustomerIdProof> getFsCustomerIdProofsForIdentityFor() {
		return this.fsCustomerIdProofsForIdentityFor;
	}

	public void setFsCustomerIdProofsForIdentityFor(List<CustomerIdProof> fsCustomerIdProofsForIdentityFor) {
		this.fsCustomerIdProofsForIdentityFor = fsCustomerIdProofsForIdentityFor;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsBizComponentDataBySecurityQuestion3")
	public List<CustomerLogin> getFsCustomerLoginsForSecurityQuestion3() {
		return this.fsCustomerLoginsForSecurityQuestion3;
	}

	public void setFsCustomerLoginsForSecurityQuestion3(List<CustomerLogin> fsCustomerLoginsForSecurityQuestion3) {
		this.fsCustomerLoginsForSecurityQuestion3 = fsCustomerLoginsForSecurityQuestion3;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsBizComponentData")
	public List<BizComponentDataDesc> getFsBizComponentDataDescs() {
		return this.fsBizComponentDataDescs;
	}

	public void setFsBizComponentDataDescs(List<BizComponentDataDesc> fsBizComponentDataDescs) {
		this.fsBizComponentDataDescs = fsBizComponentDataDescs;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsBizComponentData")
	public List<BizComponentDataRef> getFsBizComponentDataRefs() {
		return this.fsBizComponentDataRefs;
	}

	public void setFsBizComponentDataRefs(List<BizComponentDataRef> fsBizComponentDataRefs) {
		this.fsBizComponentDataRefs = fsBizComponentDataRefs;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsBizComponentDataByCustomerTypeId")
	public List<Customer> getFsCustomersForCustomerTypeId() {
		return this.fsCustomersForCustomerTypeId;
	}

	public void setFsCustomersForCustomerTypeId(List<Customer> fsCustomersForCustomerTypeId) {
		this.fsCustomersForCustomerTypeId = fsCustomersForCustomerTypeId;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsBizComponentDataByGroupId")
	public List<Customer> getFsCustomersForGroupId() {
		return this.fsCustomersForGroupId;
	}

	public void setFsCustomersForGroupId(List<Customer> fsCustomersForGroupId) {
		this.fsCustomersForGroupId = fsCustomersForGroupId;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsBizComponentDataByOccupationId")
	public List<CustomerEmploymentInfo> getFsCustomerEmploymentInfosForOccupationId() {
		return this.fsCustomerEmploymentInfosForOccupationId;
	}

	public void setFsCustomerEmploymentInfosForOccupationId(List<CustomerEmploymentInfo> fsCustomerEmploymentInfosForOccupationId) {
		this.fsCustomerEmploymentInfosForOccupationId = fsCustomerEmploymentInfosForOccupationId;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsBizComponentDataByObjectiveId")
	public List<Customer> getFsCustomersForObjectiveId() {
		return this.fsCustomersForObjectiveId;
	}

	public void setFsCustomersForObjectiveId(List<Customer> fsCustomersForObjectiveId) {
		this.fsCustomersForObjectiveId = fsCustomersForObjectiveId;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsBizComponentDataByContactTypeId")
	public List<ContactDetail> getFsContactDetails() {
		return this.fsContactDetails;
	}

	public void setFsContactDetails(List<ContactDetail> fsContactDetails) {
		this.fsContactDetails = fsContactDetails;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsBizComponentDataByEmploymentTypeId")
	public List<CustomerEmploymentInfo> getFsCustomerEmploymentInfosForEmploymentTypeId() {
		return this.fsCustomerEmploymentInfosForEmploymentTypeId;
	}

	public void setFsCustomerEmploymentInfosForEmploymentTypeId(List<CustomerEmploymentInfo> fsCustomerEmploymentInfosForEmploymentTypeId) {
		this.fsCustomerEmploymentInfosForEmploymentTypeId = fsCustomerEmploymentInfosForEmploymentTypeId;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsBizComponentDataByObjectiveId")
	public List<CustCorporateAddlDetail> getFsCustCorporateAddlDetails() {
		return this.fsCustCorporateAddlDetails;
	}

	public void setFsCustCorporateAddlDetails(List<CustCorporateAddlDetail> fsCustCorporateAddlDetails) {
		this.fsCustCorporateAddlDetails = fsCustCorporateAddlDetails;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "chargeFor")
	public List<BankCharges> getExbankcharge() {
		return exbankcharge;
	}

	public void setExbankcharge(List<BankCharges> exbankcharge) {
		this.exbankcharge = exbankcharge;
	}

}
