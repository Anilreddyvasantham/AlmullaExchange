package com.amg.exchange.treasury.model;

import java.io.Serializable;
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
import javax.persistence.UniqueConstraint;

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.remittance.model.RemittanceApplication;


@Entity
@Table(name = "EX_CUSTOMER_SPECIAL_DEAL_REQ")
public class CustomerSpecialDealRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	private BigDecimal customerSpecialDealReqId;
	private BankMaster customerSpeacialDealReqBankMaster;
	private BigDecimal customerSpeacialDealReqBankAccountDetails;
	private CurrencyMaster customerSpeacialDealReqCurrencyMaster;
	private CountryMaster customerSpeacialDealReqCountryMaster;
	private CountryMaster applicationCountryCountryMaster;
	private CompanyMaster customerSpeacialDealReqCompanyMaster;
	private Customer customerSpeacialDealReqCustomer;
	private UserFinancialYear documentFinancialYear;
	private Document customerSpeacialDealReqDocument;
	private BigDecimal documentNumber;
	private Date validUpto;
	//	private String bankAccountNumber;
	private String financeAccountNumber;
	private BigDecimal foreignCurrencyAmount;
	private String highValueTrnx;
	private String documentStatus;
	private String fundingOption;
	private BigDecimal dealCompanyId;
	private BigDecimal dealId;
	private BigDecimal dealFinanceYear;
	private BigDecimal dealApplicationNumber;
	private BigDecimal utilizedAmount;
	private BigDecimal sellRate;
	private BigDecimal buyRate;
	private Date createdDate;
	private String createdBy;
	private Date modifiedDate;
	private String modifiedBy;
	private Date approvedDate;
	private String approvedBy;
	private Date projectionDate;
	private BigDecimal avarageRate;
	private String valueDateOption;
	private BigDecimal tentiveSaleRate;
	private Date valueDate;
	private BigDecimal documentFinanYear;

	private Set<TreasuryDealDetail> treasuryDealDetail = new HashSet<TreasuryDealDetail>(0);

	@Id
	@GeneratedValue(generator="ex_customer_spl_deal_req_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_customer_spl_deal_req_seq",sequenceName="EX_CUSTOMER_SPL_DEAL_REQ_SEQ",allocationSize=1)
	@Column(name ="CUSTOMER_SPECIAL_DEAL_REQ_ID" , unique=true, nullable=false, precision=22, scale=0)
	public BigDecimal getCustomerSpecialDealReqId() {
		return customerSpecialDealReqId;
	}
	public void setCustomerSpecialDealReqId(BigDecimal customerSpecialDealReqId) {
		this.customerSpecialDealReqId = customerSpecialDealReqId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_ID")
	public BankMaster getCustomerSpeacialDealReqBankMaster() {
		return customerSpeacialDealReqBankMaster;
	}
	public void setCustomerSpeacialDealReqBankMaster(BankMaster customerSpeacialDealReqBankMaster) {
		this.customerSpeacialDealReqBankMaster = customerSpeacialDealReqBankMaster;
	}

	@Column(name="BANK_ACCT_DET_ID")
	public BigDecimal getCustomerSpeacialDealReqBankAccountDetails() {
		return customerSpeacialDealReqBankAccountDetails;
	}
	public void setCustomerSpeacialDealReqBankAccountDetails(BigDecimal customerSpeacialDealReqBankAccountDetails) {
		this.customerSpeacialDealReqBankAccountDetails = customerSpeacialDealReqBankAccountDetails;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CURRENCY_ID")
	public CurrencyMaster getCustomerSpeacialDealReqCurrencyMaster() {
		return customerSpeacialDealReqCurrencyMaster;
	}
	public void setCustomerSpeacialDealReqCurrencyMaster(CurrencyMaster customerSpeacialDealReqCurrencyMaster) {
		this.customerSpeacialDealReqCurrencyMaster = customerSpeacialDealReqCurrencyMaster;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_ID")
	public CountryMaster getCustomerSpeacialDealReqCountryMaster() {
		return customerSpeacialDealReqCountryMaster;
	}
	public void setCustomerSpeacialDealReqCountryMaster(CountryMaster customerSpeacialDealReqCountryMaster) {
		this.customerSpeacialDealReqCountryMaster = customerSpeacialDealReqCountryMaster;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "APPLICATION_COUNTRY_ID")
	public CountryMaster getApplicationCountryCountryMaster() {
		return applicationCountryCountryMaster;
	}
	public void setApplicationCountryCountryMaster(CountryMaster applicationCountryCountryMaster) {
		this.applicationCountryCountryMaster = applicationCountryCountryMaster;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPANY_ID")
	public CompanyMaster getCustomerSpeacialDealReqCompanyMaster() {
		return customerSpeacialDealReqCompanyMaster;
	}
	public void setCustomerSpeacialDealReqCompanyMaster(CompanyMaster customerSpeacialDealReqCompanyMaster) {
		this.customerSpeacialDealReqCompanyMaster = customerSpeacialDealReqCompanyMaster;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMER_ID")
	public Customer getCustomerSpeacialDealReqCustomer() {
		return customerSpeacialDealReqCustomer;
	}
	public void setCustomerSpeacialDealReqCustomer(Customer customerSpeacialDealReqCustomer) {
		this.customerSpeacialDealReqCustomer = customerSpeacialDealReqCustomer;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FINANCIAL_YEAR_ID")
	public UserFinancialYear getDocumentFinancialYear() {
		return documentFinancialYear;
	}
	public void setDocumentFinancialYear(UserFinancialYear documentFinancialYear) {
		this.documentFinancialYear = documentFinancialYear;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="DOCUMENT_ID")
	public Document getCustomerSpeacialDealReqDocument() {
		return customerSpeacialDealReqDocument;
	}
	public void setCustomerSpeacialDealReqDocument(
			Document customerSpeacialDealReqDocument) {
		this.customerSpeacialDealReqDocument = customerSpeacialDealReqDocument;
	}

	@Column(name="DOCUMENT_NUMBER")
	public BigDecimal getDocumentNumber() {
		return documentNumber;
	}
	public void setDocumentNumber(BigDecimal documentNumber) {
		this.documentNumber = documentNumber;
	}

	@Column(name="VALID_UPTO")
	public Date getValidUpto() {
		return validUpto;
	}
	public void setValidUpto(Date validUpto) {
		this.validUpto = validUpto;
	}

	/*@Column(name="BANK_ACCOUNT_NUMBER")
	public String getBankAccountNumber() {
		return bankAccountNumber;
	}

	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}*/

	@Column(name="FINANCE_ACCOUNT_NUMBER")
	public String getFinanceAccountNumber() {
		return financeAccountNumber;
	}
	public void setFinanceAccountNumber(String financeAccountNumber) {
		this.financeAccountNumber = financeAccountNumber;
	}

	@Column(name="CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name="CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="MODIFIED_DATE")
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name="FOREIGN_CURRENCY_AMOUNT")
	public BigDecimal getForeignCurrencyAmount() {
		return foreignCurrencyAmount;
	}
	public void setForeignCurrencyAmount(BigDecimal foreignCurrencyAmount) {
		this.foreignCurrencyAmount = foreignCurrencyAmount;
	}

	@Column(name="HIGH_VALUE_TRNX")
	public String getHighValueTrnx() {
		return highValueTrnx;
	}
	public void setHighValueTrnx(String highValueTrnx) {
		this.highValueTrnx = highValueTrnx;
	}

	@Column(name="DOCUMENT_STATUS")
	public String getDocumentStatus() {
		return documentStatus;
	}
	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}

	@Column(name="MODIFIED_BY")
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name="APPROVED_DATE")
	public Date getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	@Column(name="APPROVED_BY")
	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	@Column(name="FUNDING_OPTION")
	public String getFundingOption() {
		return fundingOption;
	}
	public void setFundingOption(String fundingOption) {
		this.fundingOption = fundingOption;
	}

	@Column(name="DEAL_COMPANY_ID")
	public BigDecimal getDealCompanyId() {
		return dealCompanyId;
	}
	public void setDealCompanyId(BigDecimal dealCompanyId) {
		this.dealCompanyId = dealCompanyId;
	}

	@Column(name="DEAL_ID")
	public BigDecimal getDealId() {
		return dealId;
	}
	public void setDealId(BigDecimal dealId) {
		this.dealId = dealId;
	}

	@Column(name="DEAL_FINANCE_YEAR")
	public BigDecimal getDealFinanceYear() {
		return dealFinanceYear;
	}
	public void setDealFinanceYear(BigDecimal dealFinanceYear) {
		this.dealFinanceYear = dealFinanceYear;
	}

	@Column(name="DEAL_APPLICATION_NUMBER")
	public BigDecimal getDealApplicationNumber() {
		return dealApplicationNumber;
	}
	public void setDealApplicationNumber(BigDecimal dealApplicationNumber) {
		this.dealApplicationNumber = dealApplicationNumber;
	}

	@Column(name="UTILIZED_AMOUNT")
	public BigDecimal getUtilizedAmount() {
		return utilizedAmount;
	}
	public void setUtilizedAmount(BigDecimal utilizedAmount) {
		this.utilizedAmount = utilizedAmount;
	}

	@Column(name="SELL_RATE")
	public BigDecimal getSellRate() {
		return sellRate;
	}
	public void setSellRate(BigDecimal sellRate) {
		this.sellRate = sellRate;
	}

	@Column(name="BUY_RATE")
	public BigDecimal getBuyRate() {
		return buyRate;
	}
	public void setBuyRate(BigDecimal buyRate) {
		this.buyRate = buyRate;
	}

	@Column(name="PROJECTION_DATE")
	public Date getProjectionDate() {
		return projectionDate;
	}
	public void setProjectionDate(Date projectionDate) {
		this.projectionDate = projectionDate;
	}

	@Column(name="AVERAGE_RATE")
	public BigDecimal getAvarageRate() {
		return avarageRate;
	}
	public void setAvarageRate(BigDecimal avarageRate) {
		this.avarageRate = avarageRate;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customerSpecialDealRequest")
	public Set<TreasuryDealDetail> getTreasuryDealDetail() {
		return treasuryDealDetail;
	}
	public void setTreasuryDealDetail(Set<TreasuryDealDetail> treasuryDealDetail) {
		this.treasuryDealDetail = treasuryDealDetail;
	}

	@Column(name="VALUE_DATE_OPTION")
	public String getValueDateOption() {
		return valueDateOption;
	}
	public void setValueDateOption(String valueDateOption) {
		this.valueDateOption = valueDateOption;
	}

	@Column(name="TENTIVE_SALE_RATE")
	public BigDecimal getTentiveSaleRate() {
		return tentiveSaleRate;
	}
	public void setTentiveSaleRate(BigDecimal tentiveSaleRate) {
		this.tentiveSaleRate = tentiveSaleRate;
	}

	@Column(name="VALUE_DATE")
	public Date getValueDate() {
		return valueDate;
	}
	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}
	
	@Column(name="DOCUMENT_FINANCE_YEAR")
	public BigDecimal getDocumentFinanYear() {
		return documentFinanYear;
	}
	public void setDocumentFinanYear(BigDecimal documentFinanYear) {
		this.documentFinanYear = documentFinanYear;
	}


}