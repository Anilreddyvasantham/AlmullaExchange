package com.amg.exchange.treasury.model;

// default package
// Generated May 23, 2014 5:18:25   Chennai ODC

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
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
import javax.persistence.TableGenerator;

import com.amg.exchange.common.model.BankAccountType;
import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.treasury.deal.supplier.model.TreasuryCustomerSupplier;

/**
 * BankAccountDetails Created by Chennai ODC
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "EX_BANK_ACCOUNT_DETAILS")
public class BankAccountDetails implements java.io.Serializable {

	private BigDecimal bankAcctDetId;
	private BankAccount bankAccountId;
	private BankMaster exBankMaster;
	private CompanyMaster fsCompanyMaster;
	private CountryMaster fsCountryMaster;
	private BankAccountType bankAccountType;
	private String bankAcctNo;
	private String acctType;
	private CurrencyMaster fsCurrencyMaster;
	private BigDecimal minBal;
	private BigDecimal odlmt;
	private String glno;
	private String recordStatus;
	private String telephoneNo;
	private String faxno;
	private String email;
	private String contactPersonAr;
	private String contactDesgAr;
	private String contactDeptAr;
	private String fundGlno;
	private BigDecimal intMinbal;
	private Date createDate;
	private Date updateDate;
	private String creator;
	private String modifier;
	private String contactPerson;
	private String contactDesg;
	private String contactDept;
	private String mobile;
	
	private String approvedBy;
	private Date approvedDate;
	private String remarks;
	// Added Application Country Id as per CR
	private BigDecimal applicationCountryId;

	private String ctrlGlNo;
	
	
	private Set<AccountBalance> exBankAccountDetails = new HashSet<AccountBalance>(0);
	private Set<FundEstimationDetails> fundEstimationDetails = new HashSet<FundEstimationDetails>(0);
	//private Set<CustomerSpecialDealRequest> customerSpecialDealRequest = new HashSet<CustomerSpecialDealRequest>(0);
	private Set<TreasuryDealDetail> exDealDetail = new HashSet<TreasuryDealDetail>(0);
	
	private Set<TreasuryCustomerSupplier> treasuryCustomerSupplierList = new HashSet<TreasuryCustomerSupplier>(0); 
	
	public BankAccountDetails() {
	}

	public BankAccountDetails(BigDecimal bankAcctDetId) {
		this.bankAcctDetId = bankAcctDetId;
	}

	/**
	 * @param bankAcctDetId
	 * @param bankAccountId
	 * @param exBankMaster
	 * @param fsCompanyMaster
	 * @param fsCountryMaster
	 * @param bankAccountType
	 * @param bankAcctNo
	 * @param acctType
	 * @param fsCurrencyMaster
	 * @param minBal
	 * @param odlmt
	 * @param glno
	 * @param recordStatus
	 * @param telephoneNo
	 * @param faxno
	 * @param email
	 * @param contactPersonAr
	 * @param contactDesgAr
	 * @param contactDeptAr
	 * @param fundGlno
	 * @param intMinbal
	 * @param createDate
	 * @param updateDate
	 * @param creator
	 * @param modifier
	 * @param contactPerson
	 * @param contactDesg
	 * @param contactDept
	 * @param mobile
	 * @param approvedBy
	 * @param approvedDate
	 * @param remarks
	 * @param applicationCountryId
	 * @param exBankAccountDetails
	 * @param fundEstimationDetails
	 * @param exDealDetail
	 * @param treasuryCustomerSupplierList
	 */
	public BankAccountDetails(BigDecimal bankAcctDetId,
			BankAccount bankAccountId, BankMaster exBankMaster,
			CompanyMaster fsCompanyMaster, CountryMaster fsCountryMaster,
			BankAccountType bankAccountType, String bankAcctNo,
			String acctType, CurrencyMaster fsCurrencyMaster,
			BigDecimal minBal, BigDecimal odlmt, String glno,
			String recordStatus, String telephoneNo, String faxno,
			String email, String contactPersonAr, String contactDesgAr,
			String contactDeptAr, String fundGlno, BigDecimal intMinbal,
			Date createDate, Date updateDate, String creator, String modifier,
			String contactPerson, String contactDesg, String contactDept,
			String mobile, String approvedBy, Date approvedDate,
			String remarks, BigDecimal applicationCountryId,
			Set<AccountBalance> exBankAccountDetails,
			Set<FundEstimationDetails> fundEstimationDetails,
			Set<TreasuryDealDetail> exDealDetail,
			Set<TreasuryCustomerSupplier> treasuryCustomerSupplierList,String ctrlGlNo) {
		super();
		this.bankAcctDetId = bankAcctDetId;
		this.bankAccountId = bankAccountId;
		this.exBankMaster = exBankMaster;
		this.fsCompanyMaster = fsCompanyMaster;
		this.fsCountryMaster = fsCountryMaster;
		this.bankAccountType = bankAccountType;
		this.bankAcctNo = bankAcctNo;
		this.acctType = acctType;
		this.fsCurrencyMaster = fsCurrencyMaster;
		this.minBal = minBal;
		this.odlmt = odlmt;
		this.glno = glno;
		this.recordStatus = recordStatus;
		this.telephoneNo = telephoneNo;
		this.faxno = faxno;
		this.email = email;
		this.contactPersonAr = contactPersonAr;
		this.contactDesgAr = contactDesgAr;
		this.contactDeptAr = contactDeptAr;
		this.fundGlno = fundGlno;
		this.intMinbal = intMinbal;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.creator = creator;
		this.modifier = modifier;
		this.contactPerson = contactPerson;
		this.contactDesg = contactDesg;
		this.contactDept = contactDept;
		this.mobile = mobile;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.remarks = remarks;
		this.applicationCountryId = applicationCountryId;
		this.exBankAccountDetails = exBankAccountDetails;
		this.fundEstimationDetails = fundEstimationDetails;
		this.exDealDetail = exDealDetail;
		this.treasuryCustomerSupplierList = treasuryCustomerSupplierList;
		this.ctrlGlNo= ctrlGlNo;
	}


	@Id
	@GeneratedValue(generator="ex_bank_account_details_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_bank_account_details_seq",sequenceName="EX_BANK_ACCOUNT_DETAILS_SEQ",allocationSize=1)
	@Column(name = "BANK_ACCT_DET_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getBankAcctDetId() {
		return this.bankAcctDetId;
	}

	
	public void setBankAcctDetId(BigDecimal bankAcctDetId) {
		this.bankAcctDetId = bankAcctDetId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_ACCOUNT_ID")
	public BankAccount getBankAccountId() {
		return bankAccountId;
	}

	public void setBankAccountId(BankAccount bankAccountId) {
		this.bankAccountId = bankAccountId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_ACCOUNT_TYPE_ID")
	public BankAccountType getBankAccountType() {
		return bankAccountType;
	}

	public void setBankAccountType(BankAccountType bankAccountType) {
		this.bankAccountType = bankAccountType;
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
	@JoinColumn(name = "COMPANY_ID")
	public CompanyMaster getFsCompanyMaster() {
		return this.fsCompanyMaster;
	}

	public void setFsCompanyMaster(CompanyMaster fsCompanyMaster) {
		this.fsCompanyMaster = fsCompanyMaster;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_ID")
	public CountryMaster getFsCountryMaster() {
		return this.fsCountryMaster;
	}

	public void setFsCountryMaster(CountryMaster fsCountryMaster) {
		this.fsCountryMaster = fsCountryMaster;
	}

	@Column(name = "ACCOUNT_NUMBER", length = 20)
	public String getBankAcctNo() {
		return this.bankAcctNo;
	}

	public void setBankAcctNo(String bankAcctNo) {
		this.bankAcctNo = bankAcctNo;
	}

	@Column(name = "ACCOUNT_TYPE", length = 1)
	public String getAcctType() {
		return this.acctType;
	}

	public void setAcctType(String acctType) {
		this.acctType = acctType;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CURRENCY_ID")
	public CurrencyMaster getFsCurrencyMaster() {
		return this.fsCurrencyMaster;
	}

	public void setFsCurrencyMaster(CurrencyMaster fsCurrencyMaster) {
		this.fsCurrencyMaster = fsCurrencyMaster;
	}


	@Column(name = "MINIMUM_BALANCE", precision = 18, scale = 3)
	public BigDecimal getMinBal() {
		return this.minBal;
	}

	public void setMinBal(BigDecimal minBal) {
		this.minBal = minBal;
	}

	@Column(name = "OVER_DRAFT_LIMIT", precision = 18, scale = 3)
	public BigDecimal getOdlmt() {
		return this.odlmt;
	}

	public void setOdlmt(BigDecimal odlmt) {
		this.odlmt = odlmt;
	}

	@Column(name = "FA_ACCOUNT_NUMBER", length = 33)
	public String getGlno() {
		return this.glno;
	}

	public void setGlno(String glno) {
		this.glno = glno;
	}

	@Column(name = "ISACTIVE", length = 1)
	public String getRecordStatus() {
		return this.recordStatus;
	}

	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}

	@Column(name = "TELEPHONE_NO", length = 30)
	public String getTelephoneNo() {
		return this.telephoneNo;
	}

	public void setTelephoneNo(String telephoneNo) {
		this.telephoneNo = telephoneNo;
	}

	@Column(name = "FAXNO", length = 30)
	public String getFaxno() {
		return this.faxno;
	}

	public void setFaxno(String faxno) {
		this.faxno = faxno;
	}

	@Column(name = "EMAIL", length = 30)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "CONTACT_PERSON_AR", length = 60)
	public String getContactPersonAr() {
		return this.contactPersonAr;
	}

	public void setContactPersonAr(String contactPersonAr) {
		this.contactPersonAr = contactPersonAr;
	}

	@Column(name = "CONTACT_DESG_AR", length = 120)
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

	@Column(name = "FA_FUND_ACCOUNT_NUMBER", length = 33)
	public String getFundGlno() {
		return this.fundGlno;
	}

	public void setFundGlno(String fundGlno) {
		this.fundGlno = fundGlno;
	}

	@Column(name = "INT_MINBAL", precision = 18, scale = 3)
	public BigDecimal getIntMinbal() {
		return this.intMinbal;
	}

	public void setIntMinbal(BigDecimal intMinbal) {
		this.intMinbal = intMinbal;
	}

	@Column(name = "CREATED_DATE")
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "MODIFIED_DATE")
	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Column(name = "CREATED_BY", length = 15)
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name = "MODIFIED_BY", length = 15)
	public String getModifier() {
		return this.modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	@Column(name = "CONTACT_PERSON", length = 60)
	public String getContactPerson() {
		return this.contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	@Column(name = "CONTACT_DESG", length = 60)
	public String getContactDesg() {
		return this.contactDesg;
	}

	public void setContactDesg(String contactDesg) {
		this.contactDesg = contactDesg;
	}

	@Column(name = "CONTACT_DEPT", length = 60)
	public String getContactDept() {
		return this.contactDept;
	}

	public void setContactDept(String contactDept) {
		this.contactDept = contactDept;
	}

	@Column(name = "MOBILE", length = 30)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exBankAccountDetails")
	public Set<AccountBalance> getExBankAccountDetails() {
		return exBankAccountDetails;
	}

	public void setExBankAccountDetails(Set<AccountBalance> exBankAccountDetails) {
		this.exBankAccountDetails = exBankAccountDetails;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exFundEstimationDetails")
	public Set<FundEstimationDetails> getFundEstimationDetails() {
		return fundEstimationDetails;
	}
	public void setFundEstimationDetails(Set<FundEstimationDetails> fundEstimationDetails) {
		this.fundEstimationDetails = fundEstimationDetails;
	}

/*	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customerSpeacialDealReqBankAccountDetails")
	public Set<CustomerSpecialDealRequest> getCustomerSpecialDealRequest() {
		return customerSpecialDealRequest;
	}

	public void setCustomerSpecialDealRequest(
			Set<CustomerSpecialDealRequest> customerSpecialDealRequest) {
		this.customerSpecialDealRequest = customerSpecialDealRequest;
	}*/

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "treasuryDealDetailBankAccountDetails")
	public Set<TreasuryDealDetail> getExDealDetail() {
		return exDealDetail;
	}

	public void setExDealDetail(Set<TreasuryDealDetail> exDealDetail) {
		this.exDealDetail = exDealDetail;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bankAccountNo")
	public Set<TreasuryCustomerSupplier> getTreasuryCustomerSupplierList() {
		return treasuryCustomerSupplierList;
	}

	public void setTreasuryCustomerSupplierList(
			Set<TreasuryCustomerSupplier> treasuryCustomerSupplierList) {
		this.treasuryCustomerSupplierList = treasuryCustomerSupplierList;
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
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name = "APPLICATION_COUNTRY_ID")
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}

	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}
	@Column(name = "CTRL_GLNO")
	public String getCtrlGlNo() {
		return ctrlGlNo;
	}

	public void setCtrlGlNo(String ctrlGlNo) {
		this.ctrlGlNo = ctrlGlNo;
	}
	
	
	
}
