package com.amg.exchange.treasury.model;

// default package
// Generated May 23, 2014 5:18:25  Chennai ODC

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import com.amg.exchange.common.model.CityMaster;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.DistrictMaster;
import com.amg.exchange.common.model.StateMaster;
/*import com.amg.exchange.foreigncurrency.model.CollectDetail;
import com.amg.exchange.foreigncurrency.model.ForeignCurrencyAdjust;
import com.amg.exchange.foreigncurrency.model.Stock;*/
import com.amg.exchange.remittance.model.Remittance;
import com.amg.exchange.remittance.model.RemittanceApplication;

/**
 * BankBranch Created by Chennai ODC
 */
@Entity
@Table(name = "EX_BANK_BRANCH")
public class BankBranch implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal bankBranchId;
	private DistrictMaster fsDistrictMaster;
	private BankMaster exBankMaster;
	private StateMaster fsStateMaster;
	private CityMaster fsCityMaster;
	private CountryMaster fsCountryMaster;
	private BigDecimal branchCode;
	private String branchCodeAsString;
	private String branchFullName;
	private String branchShortName;
	private String address1;
	private String address2;
	private String location;
	private String zipCode;              //author is nagarjuna adding this property
	private String telephoneNo;
	private String faxNo;
	private String email;
	private String contactPerson;
	private String contactDesg;
	private String contactDept;
	private String micrCode;
	//private String recordStatus;
	private String branchFullNameAr;
	private String branchShortNameAr;
	private String address1Ar;
	private String address2Ar;
	private String address3Ar;
	private String contactPersonAr;
	private String contactDesgAr;
	private String contactDeptAr;
	private String swiftBic;
	private String debitBranchId;
	private String branchIfsc;
	private String routingId;
	//private BigDecimal ddPrintLocId;
/*	private Date createDate;
	private Date updateDate;*/
	/*private String createdby;
	private String modifier;*/
	private String mobile;
	private Date createdDate;
	private String createdBy;
	private Date modifiedDate;
	private String modifiedBy;
	private String isactive;
	private String remarks;
	private String ipAddress;
	//private List<BankAccount> exBankAccounts = new ArrayList<BankAccount>();
	private List<BankDdPrintLoc> exBankDdPrintLocs = new ArrayList<BankDdPrintLoc>();
	private Set<Deal> exDeals = new HashSet<Deal>(0);
	private Set<Remittance> exRemittance = new HashSet<Remittance>(0);
	private Set<RemittanceApplication> exRemittanceApplication = new HashSet<RemittanceApplication>(0);
	//private List<SwiftMaster> swiftMaster=new ArrayList<SwiftMaster>();
	
	/*private Set<CollectDetail> exCollectDetails = new HashSet<CollectDetail>(0);
	private Set<Stock> exStocks = new HashSet<Stock>(0);
	private Set<ForeignCurrencyAdjust> exForeignCurrencyAdjusts = new HashSet<ForeignCurrencyAdjust>(0);*/

	public BankBranch() {
	}

	public BankBranch(BigDecimal bankBranchId) {
		this.bankBranchId = bankBranchId;
	}



	public BankBranch(BigDecimal bankBranchId, DistrictMaster fsDistrictMaster,
			BankMaster exBankMaster, StateMaster fsStateMaster,
			CityMaster fsCityMaster, CountryMaster fsCountryMaster,
			BigDecimal branchCode, String branchFullName,
			String branchShortName, String address1, String address2,
			String location, String zipCode, String telephoneNo, String faxNo,
			String email, String contactPerson, String contactDesg,
			String contactDept, String micrCode, String branchFullNameAr,
			String branchShortNameAr, String address1Ar, String address2Ar,
			String address3Ar, String contactPersonAr, String contactDesgAr,
			String contactDeptAr, String swiftBic, String debitBranchId,
			String branchIfsc, String routingId, String mobile,
			Date createdDate, String createdBy, Date modifiedDate,
			String modifiedBy, String isactive,String ipAddress,
			//List<BankAccount> exBankAccounts,
			List<BankDdPrintLoc> exBankDdPrintLocs, Set<Deal> exDeals,
			Set<Remittance> exRemittance,
			Set<RemittanceApplication> exRemittanceApplication) {
		super();
		this.bankBranchId = bankBranchId;
		this.fsDistrictMaster = fsDistrictMaster;
		this.exBankMaster = exBankMaster;
		this.fsStateMaster = fsStateMaster;
		this.fsCityMaster = fsCityMaster;
		this.fsCountryMaster = fsCountryMaster;
		this.branchCode = branchCode;
 		this.branchFullName = branchFullName;
		this.branchShortName = branchShortName;
		this.address1 = address1;
		this.address2 = address2;
		this.location = location;
		this.zipCode = zipCode;
		this.telephoneNo = telephoneNo;
		this.faxNo = faxNo;
		this.email = email;
		this.contactPerson = contactPerson;
		this.contactDesg = contactDesg;
		this.contactDept = contactDept;
		this.micrCode = micrCode;
		this.branchFullNameAr = branchFullNameAr;
		this.branchShortNameAr = branchShortNameAr;
		this.address1Ar = address1Ar;
		this.address2Ar = address2Ar;
		this.address3Ar = address3Ar;
		this.contactPersonAr = contactPersonAr;
		this.contactDesgAr = contactDesgAr;
		this.contactDeptAr = contactDeptAr;
		this.swiftBic = swiftBic;
		this.debitBranchId = debitBranchId;
		this.branchIfsc = branchIfsc;
		this.routingId = routingId;
		this.mobile = mobile;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.modifiedDate = modifiedDate;
		this.modifiedBy = modifiedBy;
		this.isactive = isactive;
		//this.exBankAccounts = exBankAccounts;
		this.exBankDdPrintLocs = exBankDdPrintLocs;
		this.exDeals = exDeals;
		this.exRemittance = exRemittance;
		this.exRemittanceApplication = exRemittanceApplication;
		this.ipAddress = ipAddress; 
		 
	}

	@Id
	@GeneratedValue(generator="ex_bank_branch_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_bank_branch_seq",sequenceName="EX_BANK_BRANCH_SEQ",allocationSize=1)
	@Column(name = "BANK_BRANCH_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getBankBranchId() {
		return this.bankBranchId;
	}

	public void setBankBranchId(BigDecimal bankBranchId) {
		this.bankBranchId = bankBranchId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DISTRICT_ID")
	public DistrictMaster getFsDistrictMaster() {
		return this.fsDistrictMaster;
	}

	public void setFsDistrictMaster(DistrictMaster fsDistrictMaster) {
		this.fsDistrictMaster = fsDistrictMaster;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_ID")
	public BankMaster getExBankMaster() {
		return this.exBankMaster;
	}

	public void setExBankMaster(BankMaster exBankMaster) {
		this.exBankMaster = exBankMaster;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STATE_ID")
	public StateMaster getFsStateMaster() {
		return this.fsStateMaster;
	}

	public void setFsStateMaster(StateMaster fsStateMaster) {
		this.fsStateMaster = fsStateMaster;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CITY_ID")
	public CityMaster getFsCityMaster() {
		return this.fsCityMaster;
	}

	public void setFsCityMaster(CityMaster fsCityMaster) {
		this.fsCityMaster = fsCityMaster;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_ID")
	public CountryMaster getFsCountryMaster() {
		return this.fsCountryMaster;
	}

	public void setFsCountryMaster(CountryMaster fsCountryMaster) {
		this.fsCountryMaster = fsCountryMaster;
	}

	@Column(name = "BRANCH_CODE", precision = 8, scale = 0)
	public BigDecimal getBranchCode() {
		return this.branchCode;
	}

	public void setBranchCode(BigDecimal branchCode) {
		this.branchCode = branchCode;
	}

	@Column(name = "BRANCH_FULL_NAME", length = 60)
	public String getBranchFullName() {
		return this.branchFullName;
	}

	public void setBranchFullName(String branchFullName) {
		this.branchFullName = branchFullName;
	}

	@Column(name = "BRANCH_SHORT_NAME", length = 30)
	public String getBranchShortName() {
		return this.branchShortName;
	}

	public void setBranchShortName(String branchShortName) {
		this.branchShortName = branchShortName;
	}

	@Column(name = "ADDRESS1", length = 60)
	public String getAddress1() {
		return this.address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	@Column(name = "ADDRESS2", length = 60)
	public String getAddress2() {
		return this.address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	@Column(name = "LOCATION", length = 15)
	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Column(name = "ZIP_CODE", length = 15)
	public String getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Column(name = "TELEPHONE_NO", length = 30)
	public String getTelephoneNo() {
		return this.telephoneNo;
	}

	public void setTelephoneNo(String telephoneNo) {
		this.telephoneNo = telephoneNo;
	}

	@Column(name = "FAX_NO", length = 30)
	public String getFaxNo() {
		return this.faxNo;
	}

	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}

	@Column(name = "EMAIL", length = 30)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "CONTACT_PERSON", length = 60)
	public String getContactPerson() {
		return this.contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	@Column(name = "CONTACT_DESG", length = 30)
	public String getContactDesg() {
		return this.contactDesg;
	}

	public void setContactDesg(String contactDesg) {
		this.contactDesg = contactDesg;
	}

	@Column(name = "CONTACT_DEPT", length = 30)
	public String getContactDept() {
		return this.contactDept;
	}

	public void setContactDept(String contactDept) {
		this.contactDept = contactDept;
	}

	@Column(name = "MICR_CODE", length = 10)
	public String getMicrCode() {
		return this.micrCode;
	}

	public void setMicrCode(String micrCode) {
		this.micrCode = micrCode;
	}

	@Column(name = "BRANCH_FULL_NAME_AR", length = 120)
	public String getBranchFullNameAr() {
		return this.branchFullNameAr;
	}

	public void setBranchFullNameAr(String branchFullNameAr) {
		this.branchFullNameAr = branchFullNameAr;
	}

	@Column(name = "BRANCH_SHORT_NAME_AR", length = 60)
	public String getBranchShortNameAr() {
		return this.branchShortNameAr;
	}

	public void setBranchShortNameAr(String branchShortNameAr) {
		this.branchShortNameAr = branchShortNameAr;
	}

	@Column(name = "ADDRESS1_AR", length = 120)
	public String getAddress1Ar() {
		return this.address1Ar;
	}

	public void setAddress1Ar(String address1Ar) {
		this.address1Ar = address1Ar;
	}

	@Column(name = "ADDRESS2_AR", length = 120)
	public String getAddress2Ar() {
		return this.address2Ar;
	}

	public void setAddress2Ar(String address2Ar) {
		this.address2Ar = address2Ar;
	}

	@Column(name = "ADDRESS3_AR", length = 120)
	public String getAddress3Ar() {
		return this.address3Ar;
	}

	public void setAddress3Ar(String address3Ar) {
		this.address3Ar = address3Ar;
	}

	@Column(name = "CONTACT_PERSON_AR", length = 120)
	public String getContactPersonAr() {
		return this.contactPersonAr;
	}

	public void setContactPersonAr(String contactPersonAr) {
		this.contactPersonAr = contactPersonAr;
	}

	@Column(name = "CONTACT_DESG_AR", length = 60)
	public String getContactDesgAr() {
		return this.contactDesgAr;
	}

	public void setContactDesgAr(String contactDesgAr) {
		this.contactDesgAr = contactDesgAr;
	}

	@Column(name = "CONTACT_DEPT_AR", length = 60)
	public String getContactDeptAr() {
		return this.contactDeptAr;
	}

	public void setContactDeptAr(String contactDeptAr) {
		this.contactDeptAr = contactDeptAr;
	}

	@Column(name = "SWIFT_BIC", length = 11)
	public String getSwiftBic() {
		return this.swiftBic;
	}

	public void setSwiftBic(String swiftBic) {
		this.swiftBic = swiftBic;
	}

	@Column(name = "DEBIT_BRANCH_ID", length = 20)
	public String getDebitBranchId() {
		return this.debitBranchId;
	}

	public void setDebitBranchId(String debitBranchId) {
		this.debitBranchId = debitBranchId;
	}

	@Column(name = "BRANCH_IFSC", length = 40)
	public String getBranchIfsc() {
		return this.branchIfsc;
	}

	public void setBranchIfsc(String branchIfsc) {
		this.branchIfsc = branchIfsc;
	}

	@Column(name = "ROUTING_ID", length = 10)
	public String getRoutingId() {
		return this.routingId;
	}

	public void setRoutingId(String routingId) {
		this.routingId = routingId;
	}

	/*@OneToMany(fetch = FetchType.LAZY, mappedBy = "exBankBranch")
	public List<BankAccount> getExBankAccounts() {
		return exBankAccounts;
	}

	public void setExBankAccounts(List<BankAccount> exBankAccounts) {
		this.exBankAccounts = exBankAccounts;
	}*/

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exBankBranch")
	public List<BankDdPrintLoc> getExBankDdPrintLocs() {
		return exBankDdPrintLocs;
	}

	public void setExBankDdPrintLocs(List<BankDdPrintLoc> exBankDdPrintLocs) {
		this.exBankDdPrintLocs = exBankDdPrintLocs;
	}

	@Column(name = "MOBILE", length = 30)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	
	/*@OneToMany(fetch = FetchType.LAZY, mappedBy = "exBankBranch")
	public Set<ForeignCurrencyAdjust> getExForeignCurrencyAdjusts() {
		return this.exForeignCurrencyAdjusts;
	}

	public void setExForeignCurrencyAdjusts(Set<ForeignCurrencyAdjust> exForeignCurrencyAdjusts) {
		this.exForeignCurrencyAdjusts = exForeignCurrencyAdjusts;
	}*/

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exBankBranch")
	public Set<Deal> getExDeals() {
		return this.exDeals;
	}

	public void setExDeals(Set<Deal> exDeals) {
		this.exDeals = exDeals;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exBankBranch")
	public Set<Remittance> getExRemittance() {
		return exRemittance;
	}

	public void setExRemittance(Set<Remittance> exRemittance) {
		this.exRemittance = exRemittance;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exBankBranch")
	public Set<RemittanceApplication> getExRemittanceApplication() {
		return exRemittanceApplication;
	}

	public void setExRemittanceApplication(
			Set<RemittanceApplication> exRemittanceApplication) {
		this.exRemittanceApplication = exRemittanceApplication;
	}

	@Column(name = "CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	@Column(name = "CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	@Column(name = "MODIFIED_DATE")
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	@Column(name = "MODIFIED_BY")
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	@Column(name = "ISACTIVE")
	public String getIsactive() {
		return isactive;
	}
	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}
	
	/* START ADDED BY VISWA@@@ */ 
	
	private String approvedBy;
	private Date approvedDate;
	
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
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	@Column(name = "BRANCH_CODE",insertable=false,updatable=false)
	public String getBranchCodeAsString() {
		return branchCodeAsString;
	}

	public void setBranchCodeAsString(String branchCodeAsString) {
		this.branchCodeAsString = branchCodeAsString;
	}

	
	@Column(name = "IP_ADDRESS")
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	/* END ADDED BY VISWA@@@ */ 
	
	/*@OneToMany(fetch = FetchType.LAZY, mappedBy = "exBankBranch")
	public Set<CollectDetail> getExCollectDetails() {
		return this.exCollectDetails;
	}

	public void setExCollectDetails(Set<CollectDetail> exCollectDetails) {
		this.exCollectDetails = exCollectDetails;
	}*/
	
/*	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exBankBranch")
	public Set<Stock> getExStocks() {
		return this.exStocks;
	}

	public void setExStocks(Set<Stock> exStocks) {
		this.exStocks = exStocks;
	}*/
	
	
	
}
