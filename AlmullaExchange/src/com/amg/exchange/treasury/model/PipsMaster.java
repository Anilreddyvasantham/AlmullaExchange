package com.amg.exchange.treasury.model;

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

import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.registration.model.CountryBranch;

/**
 * @author Rahamathali Shaik 
 */    

@Entity
@Table(name = "EX_PIPS_MASTER")
public class PipsMaster {
	private BigDecimal pipsMasterId;
	private CountryBranch countryBranch;
	private CountryMaster countryMaster;
	private BankMaster bankMaster;
	private CurrencyMaster currencyMaster;
	/*private ServiceIndicator serviceIndicator;*/
	private ServiceMaster serviceMaster;
	private BigDecimal pipsNo;
	private String isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private BigDecimal fromAmount;
	private BigDecimal toAmount;
	private String approvedBy;
	private Date approvedDate;
	
	private String pipsTypeCode;
	
	
	public PipsMaster() {
	}

	public PipsMaster(BigDecimal pipsMasterId) {
		this.pipsMasterId = pipsMasterId;
	}

	public PipsMaster(BigDecimal pipsMasterId, BigDecimal fromAmount,
			BigDecimal toAmount, CountryBranch countryBranch,
			CountryMaster countryMaster, BankMaster bankMaster,
			CurrencyMaster currencyMaster, ServiceMaster serviceMaster,
			BigDecimal pipsNo, String isActive, String createdBy,
			Date createdDate, String modifiedBy, Date modifiedDate,String approvedBy,Date approvedDate,String pipsTypeCode) {
		super();
		this.pipsMasterId = pipsMasterId;
		this.fromAmount = fromAmount;
		this.toAmount = toAmount;
		this.countryBranch = countryBranch;
		this.countryMaster = countryMaster;
		this.bankMaster = bankMaster;
		this.currencyMaster = currencyMaster;
		this.serviceMaster=serviceMaster;
		this.pipsNo = pipsNo;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.approvedBy=approvedBy;
		this.approvedDate=approvedDate;
		this.pipsTypeCode=pipsTypeCode;
	}
	@Id
	@GeneratedValue(generator="ex_pips_master_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_pips_master_seq",sequenceName="EX_PIPS_MASTER_SEQ",allocationSize=1)
	@Column(name ="PIPS_MASTER_ID" , unique=true, nullable=false, precision=22, scale=0)
	public BigDecimal getPipsMasterId() {
		return pipsMasterId;
	}
	public void setPipsMasterId(BigDecimal pipsMasterId) {
		this.pipsMasterId = pipsMasterId;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_BRANCH_ID")
	public CountryBranch getCountryBranch() {
		return countryBranch;
	}
	public void setCountryBranch(CountryBranch countryBranch) {
		this.countryBranch = countryBranch;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_ID")
	public CountryMaster getCountryMaster() {
		return countryMaster;
	}
	public void setCountryMaster(CountryMaster countryMaster) {
		this.countryMaster = countryMaster;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_ID")
	public BankMaster getBankMaster() {
		return bankMaster;
	}
	public void setBankMaster(BankMaster bankMaster) {
		this.bankMaster = bankMaster;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CURRENCY_ID")
	public CurrencyMaster getCurrencyMaster() {
		return currencyMaster;
	}
	public void setCurrencyMaster(CurrencyMaster currencyMaster) {
		this.currencyMaster = currencyMaster;
	}
	/*@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SERVICE_INDICATOR_ID")
	public ServiceIndicator getServiceIndicator() {
		return serviceIndicator;
	}
	public void setServiceIndicator(ServiceIndicator serviceIndicator) {
		this.serviceIndicator = serviceIndicator;
	}*/
	@Column(name = "PIPS_NO")
	public BigDecimal getPipsNo() {
		return pipsNo;
	}
	public void setPipsNo(BigDecimal pipsNo) {
		this.pipsNo = pipsNo;
	}
	@Column(name = "ISACTIVE")
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
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
	@Column(name = "FROM_AMOUNT")
	public BigDecimal getFromAmount() {
		return fromAmount;
	}
	public void setFromAmount(BigDecimal fromAmount) {
		this.fromAmount = fromAmount;
	}
	@Column(name = "TO_AMOUNT")
	public BigDecimal getToAmount() {
		return toAmount;
	}
	public void setToAmount(BigDecimal toAmount) {
		this.toAmount = toAmount;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SERVICE_INDICATOR_ID")
	public ServiceMaster getserviceMaster() {
		return serviceMaster;
	}

	public void setserviceMaster(ServiceMaster serviceMaster) {
		this.serviceMaster = serviceMaster;
	}
	
	@Column(name = "PIPS_TYPE_CODE")
	public String getPipsTypeCode() {
		return pipsTypeCode;
	}

	public void setPipsTypeCode(String pipsTypeCode) {
		this.pipsTypeCode = pipsTypeCode;
	}
	
	

}
