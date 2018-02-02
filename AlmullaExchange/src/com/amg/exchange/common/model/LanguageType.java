package com.amg.exchange.common.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.amg.exchange.registration.model.ContactDetail;
import com.amg.exchange.registration.model.CustCorporateAddlDetail;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerEmploymentInfo;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.registration.model.CustomerLogin;
import com.amg.exchange.registration.model.PartnerAuthorized;
import com.amg.exchange.remittance.model.PaymentModeDesc;
import com.amg.exchange.remittance.model.RelationsDescription;
import com.amg.exchange.treasury.model.DealConclusionTypeDetail;
import com.amg.exchange.treasury.model.DeliveryModeDesc;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.treasury.model.ServiceMasterDesc;
import com.amg.exchange.treasury.model.SupplierDetails;
import com.amg.exchange.treasury.model.TreasuryDealHeader;
import com.amg.exchange.treasury.model.TreasuryStandardInstruction;

/*******************************************************************************************************************
 * File : LanguageType.java
 * 
 * Project : AlmullaExchange
 * 
 * Package : com.amg.exchange.model
 * 
 * Created : Date : 29-May-2014 5:16:19 pm By : Justin Vincent Revision:
 * 
 * Last Change: Date : 19-Nov-2014 12:43:19 pm By : Nazish Ehsan Hashmi
 * 
 * Description: TODO
 ********************************************************************************************************************/
@Entity
@Table(name = "FS_LANGUAGE_TYPE")
public class LanguageType implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private BigDecimal languageId;
	private String languageCode;
	private String languageName;
	private String direction;
	private List<Customer> fsCustomers = new ArrayList<Customer>();
	private List<CustomerIdProof> fsCustomerIdProofs = new ArrayList<CustomerIdProof>();
	private List<CustomerLogin> fsCustomerLogins = new ArrayList<CustomerLogin>();
	private List<CountryMasterDesc> fsCountryMasterDescs = new ArrayList<CountryMasterDesc>();
	private List<StateMasterDesc> fsStateMasterDescs = new ArrayList<StateMasterDesc>();
	private List<DistrictMasterDesc> fsDistrictMasterDescs = new ArrayList<DistrictMasterDesc>();
	private List<CustomerEmploymentInfo> fsCustomerEmploymentInfos = new ArrayList<CustomerEmploymentInfo>();
	private List<ContactDetail> fsContactDetails = new ArrayList<ContactDetail>();
	private List<CityMasterDesc> fsCityMasterDescs = new ArrayList<CityMasterDesc>();
	private List<CompanyMasterDesc> fsCompanyMasterDescs = new ArrayList<CompanyMasterDesc>();
	private List<CustCorporateAddlDetail> fsCustCorporateAddlDetails = new ArrayList<CustCorporateAddlDetail>();
	private List<BizComponentDataDesc> fsBizComponentDataDescs = new ArrayList<BizComponentDataDesc>();
	private Set<SupplierDetails> exSupplierDetailses = new HashSet<SupplierDetails>(0);
	private Set<DealConclusionTypeDetail> exDealConclusionTypeDetails = new HashSet<DealConclusionTypeDetail>(0);
//	private List<ZoneMasterDesc> fsZones = new ArrayList<ZoneMasterDesc>();
	private List<Document> exDocument = new ArrayList<Document>();
	private List<PartnerAuthorized> fsPartnerAuthorized = new ArrayList<PartnerAuthorized>();
	private Set<TreasuryDealHeader> exDealHeader = new HashSet<TreasuryDealHeader>(0);
	private Set<TreasuryStandardInstruction> exTreasuryStandardIns = new HashSet<TreasuryStandardInstruction>(0);
	private List<MarketingData> exMarkdata=new ArrayList<MarketingData>();
	private List<DeliveryModeDesc> deliveryModeDesc=new ArrayList<DeliveryModeDesc>();
	private List<ServiceMasterDesc> serviceMasterDescs=new ArrayList<ServiceMasterDesc>();
	private List<RelationsDescription> relationsDescription=new ArrayList<RelationsDescription>();
	private List<PaymentModeDesc> paymentModeDesc=new ArrayList<PaymentModeDesc>();
	private List<InsuranceMasterDescription> insuranceDesc=new ArrayList<InsuranceMasterDescription>();
	 

	public LanguageType() {
	}

	public LanguageType(BigDecimal languageId, String languageName) {
		this.languageId = languageId;
		this.languageName = languageName;
	}

	public LanguageType(BigDecimal languageId, String languageName,
			String direction) {
		this.languageId = languageId;
		this.languageName = languageName;
		this.direction = direction;
	}

	public LanguageType(BigDecimal languageId, String languageCode,
			String languageName, List<Customer> fsCustomers,
			List<CustomerIdProof> fsCustomerIdProofs,
			List<CustomerLogin> fsCustomerLogins,
			List<CountryMasterDesc> fsCountryMasterDescs,
			List<StateMasterDesc> fsStateMasterDescs,
			List<DistrictMasterDesc> fsDistrictMasterDescs,
			List<CustomerEmploymentInfo> fsCustomerEmploymentInfos,
			List<ContactDetail> fsContactDetails,
			List<CityMasterDesc> fsCityMasterDescs,
			List<CompanyMasterDesc> fsCompanyMasterDescs,
			List<CustCorporateAddlDetail> fsCustCorporateAddlDetails,
			List<BizComponentDataDesc> fsBizComponentDataDescs,
			Set<SupplierDetails> exSupplierDetailses,
			Set<DealConclusionTypeDetail> exDealConclusionTypeDetails,List<Document> exDocument,
			List<PartnerAuthorized> fsPartnerAuthorized,
			Set<TreasuryDealHeader> exDealHeader,
			Set<TreasuryStandardInstruction> exTreasuryStandardIns,
			List<MarketingData> exMarkdata,List<ServiceMasterDesc> serviceMasterDescs,List<DeliveryModeDesc> deliveryModeDesc,List<RelationsDescription> relationsDescription,List<PaymentModeDesc> paymentModeDesc,List<InsuranceMasterDescription> insuranceDesc) {

		this.languageId = languageId;
		this.languageCode = languageCode;
		this.languageName = languageName;
		this.fsCustomers = fsCustomers;
		this.fsCustomerIdProofs = fsCustomerIdProofs;
		this.fsCustomerLogins = fsCustomerLogins;
		this.fsCountryMasterDescs = fsCountryMasterDescs;
		this.fsStateMasterDescs = fsStateMasterDescs;
		this.fsDistrictMasterDescs = fsDistrictMasterDescs;
		this.fsCustomerEmploymentInfos = fsCustomerEmploymentInfos;
		this.fsContactDetails = fsContactDetails;
		this.fsCityMasterDescs = fsCityMasterDescs;
		this.fsCompanyMasterDescs = fsCompanyMasterDescs;
		this.fsCustCorporateAddlDetails = fsCustCorporateAddlDetails;
		this.fsBizComponentDataDescs = fsBizComponentDataDescs;
		this.exSupplierDetailses = exSupplierDetailses;
		this.exDealConclusionTypeDetails=exDealConclusionTypeDetails;
		this.exDocument = exDocument;
		this.fsPartnerAuthorized = fsPartnerAuthorized;
		this.exDealHeader = exDealHeader;
		this.exTreasuryStandardIns = exTreasuryStandardIns;
		this.exMarkdata=exMarkdata;
		this.deliveryModeDesc=deliveryModeDesc;
		this.serviceMasterDescs=serviceMasterDescs;
		this.relationsDescription=relationsDescription;
		this.paymentModeDesc=paymentModeDesc;
		this.insuranceDesc=insuranceDesc;
	}


	@Id
	/*@TableGenerator(name = "languageid", table = "fs_languageidpk", pkColumnName = "languageidkey", pkColumnValue = "languageidvalue", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "languageid")
	*/
	@GeneratedValue(generator="fs_language_type_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="fs_language_type_seq" ,sequenceName="FS_LANGUAGE_TYPE_SEQ",allocationSize=1)
	@Column(name = "LANGUAGE_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getLanguageId() {
		return this.languageId;
	}

	public void setLanguageId(BigDecimal languageId) {
		this.languageId = languageId;
	}

	@Column(name = "LANGUAGE_CODE", length = 3)
	public String getLanguageCode() {
		return this.languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	@Column(name = "DIRECTION", length = 3)
	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	@Column(name = "LANGUAGE_NAME", nullable = false, length = 50)
	public String getLanguageName() {
		return this.languageName;
	}

	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsLanguageType")
	public List<Customer> getFsCustomers() {
		return this.fsCustomers;
	}

	public void setFsCustomers(List<Customer> fsCustomers) {
		this.fsCustomers = fsCustomers;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsLanguageType")
	public List<CustomerIdProof> getFsCustomerIdProofs() {
		return this.fsCustomerIdProofs;
	}

	public void setFsCustomerIdProofs(List<CustomerIdProof> fsCustomerIdProofs) {
		this.fsCustomerIdProofs = fsCustomerIdProofs;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsLanguageType")
	public List<CustomerLogin> getFsCustomerLogins() {
		return this.fsCustomerLogins;
	}

	public void setFsCustomerLogins(List<CustomerLogin> fsCustomerLogins) {
		this.fsCustomerLogins = fsCustomerLogins;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsLanguageType")
	public List<CountryMasterDesc> getFsCountryMasterDescs() {
		return this.fsCountryMasterDescs;
	}

	public void setFsCountryMasterDescs(
			List<CountryMasterDesc> fsCountryMasterDescs) {
		this.fsCountryMasterDescs = fsCountryMasterDescs;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsLanguageType")
	public List<StateMasterDesc> getFsStateMasterDescs() {
		return this.fsStateMasterDescs;
	}

	public void setFsStateMasterDescs(List<StateMasterDesc> fsStateMasterDescs) {
		this.fsStateMasterDescs = fsStateMasterDescs;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsLanguageType")
	public List<DistrictMasterDesc> getFsDistrictMasterDescs() {
		return this.fsDistrictMasterDescs;
	}

	public void setFsDistrictMasterDescs(
			List<DistrictMasterDesc> fsDistrictMasterDescs) {
		this.fsDistrictMasterDescs = fsDistrictMasterDescs;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsLanguageType")
	public List<CustomerEmploymentInfo> getFsCustomerEmploymentInfos() {
		return this.fsCustomerEmploymentInfos;
	}

	public void setFsCustomerEmploymentInfos(
			List<CustomerEmploymentInfo> fsCustomerEmploymentInfos) {
		this.fsCustomerEmploymentInfos = fsCustomerEmploymentInfos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsLanguageType")
	public List<ContactDetail> getFsContactDetails() {
		return this.fsContactDetails;
	}

	public void setFsContactDetails(List<ContactDetail> fsContactDetails) {
		this.fsContactDetails = fsContactDetails;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsLanguageType")
	public List<CityMasterDesc> getFsCityMasterDescs() {
		return this.fsCityMasterDescs;
	}

	public void setFsCityMasterDescs(List<CityMasterDesc> fsCityMasterDescs) {
		this.fsCityMasterDescs = fsCityMasterDescs;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsLanguageType")
	public List<CompanyMasterDesc> getFsCompanyMasterDescs() {
		return this.fsCompanyMasterDescs;
	}

	public void setFsCompanyMasterDescs(
			List<CompanyMasterDesc> fsCompanyMasterDescs) {
		this.fsCompanyMasterDescs = fsCompanyMasterDescs;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsLanguageType")
	public List<CustCorporateAddlDetail> getFsCustCorporateAddlDetails() {
		return this.fsCustCorporateAddlDetails;
	}

	public void setFsCustCorporateAddlDetails(
			List<CustCorporateAddlDetail> fsCustCorporateAddlDetails) {
		this.fsCustCorporateAddlDetails = fsCustCorporateAddlDetails;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsLanguageType")
	public List<BizComponentDataDesc> getFsBizComponentDataDescs() {
		return fsBizComponentDataDescs;
	}

	public void setFsBizComponentDataDescs(
			List<BizComponentDataDesc> fsBizComponentDataDescs) {
		this.fsBizComponentDataDescs = fsBizComponentDataDescs;
	}
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsLanguageType")
	public Set<SupplierDetails> getExSupplierDetailses() {
		return this.exSupplierDetailses;
	}

	public void setExSupplierDetailses(Set<SupplierDetails> exSupplierDetailses) {
		this.exSupplierDetailses = exSupplierDetailses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsLanguageType")
	public Set<DealConclusionTypeDetail> getExDealConclusionTypeDetails() {
		return this.exDealConclusionTypeDetails;
	}

	public void setExDealConclusionTypeDetails(Set<DealConclusionTypeDetail> exDealConclusionTypeDetails) {
		this.exDealConclusionTypeDetails = exDealConclusionTypeDetails;
	}
/*	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsLanguageType")
	public List<ZoneMasterDesc> getFsZones() {
		return fsZones;
	}

	public void setFsZones(List<ZoneMasterDesc> fsZones) {
		this.fsZones = fsZones;
	}*/
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsLanguageType")
	public List<Document> getExDocument() {
		return exDocument;
	}

	public void setExDocument(List<Document> exDocument) {
		this.exDocument = exDocument;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsLanguageType")
	public List<PartnerAuthorized> getFsPartnerAuthorized() {
		return fsPartnerAuthorized;
	}

	public void setFsPartnerAuthorized(List<PartnerAuthorized> fsPartnerAuthorized) {
		this.fsPartnerAuthorized = fsPartnerAuthorized;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsLanguageType")
	public Set<TreasuryDealHeader> getExDealHeader() {
		return exDealHeader;
	}

	public void setExDealHeader(Set<TreasuryDealHeader> exDealHeader) {
		this.exDealHeader = exDealHeader;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "treasuryLanguageType")
	public Set<TreasuryStandardInstruction> getExTreasuryStandardIns() {
		return exTreasuryStandardIns;
	}

	public void setExTreasuryStandardIns(
			Set<TreasuryStandardInstruction> exTreasuryStandardIns) {
		this.exTreasuryStandardIns = exTreasuryStandardIns;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "marklangtype")
	public List<MarketingData> getExMarkdata() {
		return exMarkdata;
	}

	public void setExMarkdata(List<MarketingData> exMarkdata) {
		this.exMarkdata = exMarkdata;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "languageType")
	public List<DeliveryModeDesc> getDeliveryModeDesc() {
		return deliveryModeDesc;
	}

	public void setDeliveryModeDesc(List<DeliveryModeDesc> deliveryModeDesc) {
		this.deliveryModeDesc = deliveryModeDesc;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "languageType")
	public List<ServiceMasterDesc> getServiceMasterDescs() {
		return serviceMasterDescs;
	}

	public void setServiceMasterDescs(List<ServiceMasterDesc> serviceMasterDescs) {
		this.serviceMasterDescs = serviceMasterDescs;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "languageType")
	public List<RelationsDescription> getRelationsDescription() {
		return relationsDescription;
	}

	public void setRelationsDescription(
			List<RelationsDescription> relationsDescription) {
		this.relationsDescription = relationsDescription;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "languageType")
	public List<PaymentModeDesc> getPaymentModeDesc() {
		return paymentModeDesc;
	}

	public void setPaymentModeDesc(List<PaymentModeDesc> paymentModeDesc) {
		this.paymentModeDesc = paymentModeDesc;
	}
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "languageType")
	public List<InsuranceMasterDescription> getInsuranceDesc() {
		return insuranceDesc;
	}

	public void setInsuranceDesc(List<InsuranceMasterDescription> insuranceDesc) {
		this.insuranceDesc = insuranceDesc;
	}
	
	 
	
}
