package com.amg.exchange.treasury.model;

import java.io.Serializable;
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
@Table(name = "EX_EXCHANGE_RATE_MASTER_APR")
public class ExchangeRateApproval implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal exchangeRateMasterAprId;
	private BigDecimal exchangeRateMasterId;
	private CountryBranch countryBranchId;
	private CountryMaster applicationCountryId;
	private CountryMaster countryId;
	private BankMaster bankMaster;
	private CurrencyMaster currencyId;
	private ServiceMaster serviceId;
	private RemittanceModeMaster remitanceMode;
	private DeliveryMode deliveryMode;
	private BigDecimal sellrateMin;
	private BigDecimal sellrateMax;
	private BigDecimal buyrateMin;
	private BigDecimal buyrateMax;
	private BigDecimal corporateRate;
	private String recStatus;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;

	public ExchangeRateApproval() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ExchangeRateApproval(BigDecimal exchangeRateMasterAprId,
			BigDecimal exchangeRateMasterId, CountryBranch countryBranchId,
			CountryMaster applicationCountryId, CountryMaster countryId,
			BankMaster bankMaster, CurrencyMaster currencyId,
			ServiceMaster serviceId, RemittanceModeMaster remitanceMode,
			DeliveryMode deliveryMode, BigDecimal sellrateMin,
			BigDecimal sellrateMax, BigDecimal buyrateMin,
			BigDecimal buyrateMax, BigDecimal corporateRate, String recStatus,
			String createdBy, Date createdDate, String modifiedBy,
			Date modifiedDate, String approvedBy, Date approvedDate,
			String remarks) {
		super();
		this.exchangeRateMasterAprId = exchangeRateMasterAprId;
		this.exchangeRateMasterId = exchangeRateMasterId;
		this.countryBranchId = countryBranchId;
		this.applicationCountryId = applicationCountryId;
		this.countryId = countryId;
		this.bankMaster = bankMaster;
		this.currencyId = currencyId;
		this.serviceId = serviceId;
		this.remitanceMode = remitanceMode;
		this.deliveryMode = deliveryMode;
		this.sellrateMin = sellrateMin;
		this.sellrateMax = sellrateMax;
		this.buyrateMin = buyrateMin;
		this.buyrateMax = buyrateMax;
		this.corporateRate = corporateRate;
		this.recStatus = recStatus;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.remarks = remarks;
	}

	@Id
	@GeneratedValue(generator="ex_exchange_rate_master_ap_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_exchange_rate_master_ap_seq",sequenceName="EX_EXCHANGE_RATE_MASTER_AP_SEQ",allocationSize=1)
	@Column(name ="EXCHANGE_RATE_MASTER_APR_ID" , unique=true, nullable=false, precision=22, scale=0)
	public BigDecimal getExchangeRateMasterAprId() {
		return exchangeRateMasterAprId;
	}

	public void setExchangeRateMasterAprId(BigDecimal exchangeRateMasterAprId) {
		this.exchangeRateMasterAprId = exchangeRateMasterAprId;
	}
	
	@Column(name = "EXCHANGE_RATE_MASTER_ID")
	public BigDecimal getExchangeRateMasterId() {
		return exchangeRateMasterId;
	}

	public void setExchangeRateMasterId(BigDecimal exchangeRateMasterId) {
		this.exchangeRateMasterId = exchangeRateMasterId;
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
	@JoinColumn(name = "COUNTRY_BRANCH_ID")
	public CountryBranch getCountryBranchId() {
		return countryBranchId;
	}

	public void setCountryBranchId(CountryBranch countryBranchId) {
		this.countryBranchId = countryBranchId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "APPLICATION_COUNTRY_ID")
	public CountryMaster getApplicationCountryId() {
		return applicationCountryId;
	}

	public void setApplicationCountryId(CountryMaster applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_ID")
	public CountryMaster getCountryId() {
		return countryId;
	}

	public void setCountryId(CountryMaster countryId) {
		this.countryId = countryId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CURRENCY_ID")
	public CurrencyMaster getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(CurrencyMaster currencyId) {
		this.currencyId = currencyId;
	}
/*
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SERVICE_INDICATOR_ID")
	public ServiceIndicator getServiceIndicatorId() {
		return serviceIndicatorId;
	}

	public void setServiceIndicatorId(ServiceIndicator serviceIndicatorId) {
		this.serviceIndicatorId = serviceIndicatorId;
	}*/
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REMITTANCE_MODE_ID")
	public RemittanceModeMaster getRemitanceMode() {
		return remitanceMode;
	}

	public void setRemitanceMode(RemittanceModeMaster remitanceMode) {
		this.remitanceMode = remitanceMode;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DELIVERY_MODE_ID")
	public DeliveryMode getDeliveryMode() {
		return deliveryMode;
	}

	public void setDeliveryMode(DeliveryMode deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	@Column(name = "SELL_RATE_MIN")
	public BigDecimal getSellrateMin() {
		return sellrateMin;
	}

	public void setSellrateMin(BigDecimal sellrateMin) {
		this.sellrateMin = sellrateMin;
	}

	@Column(name = "SELL_RATE_MAX")
	public BigDecimal getSellrateMax() {
		return sellrateMax;
	}

	public void setSellrateMax(BigDecimal sellrateMax) {
		this.sellrateMax = sellrateMax;
	}

	@Column(name = "BUY_RATE_MIN")
	public BigDecimal getBuyrateMin() {
		return buyrateMin;
	}

	public void setBuyrateMin(BigDecimal buyrateMin) {
		this.buyrateMin = buyrateMin;
	}

	@Column(name = "BUY_RATE_MAX")
	public BigDecimal getBuyrateMax() {
		return buyrateMax;
	}

	public void setBuyrateMax(BigDecimal buyrateMax) {
		this.buyrateMax = buyrateMax;
	}

	@Column(name = "ISACTIVE")
	public String getRecStatus() {
		return recStatus;
	}

	public void setRecStatus(String recStatus) {
		this.recStatus = recStatus;
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

	@Column(name = "CORPORATE_RATE")
	public BigDecimal getCorporateRate() {
		return corporateRate;
	}

	public void setCorporateRate(BigDecimal corporateRate) {
		this.corporateRate = corporateRate;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SERVICE_INDICATOR_ID")
	public ServiceMaster getServiceId() {
		return serviceId;
	}

	public void setServiceId(ServiceMaster serviceId) {
		this.serviceId = serviceId;
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
	
}