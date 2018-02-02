package com.amg.exchange.remittance.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.amg.exchange.treasury.model.ServiceMaster;

@Entity
@Table(name = "EX_EXCHANGE_RATE_MASTER_APRDET")
public class ExchangeRateApprovalDetModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal exchangeRateMasterAprDetId;
	private BigDecimal applicationCountryId;
	private String approvedBy;
	private Date approvedDate;
	private String authorisedBy;
	private Date authorisedDate;
	private BigDecimal bankId;
	private BigDecimal buyRateMax;
	private BigDecimal buyRateMin;
	private BigDecimal corporateRate;
	private BigDecimal countryBranchId;
	private BigDecimal countryId;
	private String createdBy;
	private Date createdDate;
	private BigDecimal currencyId;
	private BigDecimal deliveryModeId;
	private BigDecimal exchangeRateMasterId;
	private String isActive;
	private String modifiedBy;
	private Date modifiedDate;
	private String remarks;
	private BigDecimal remitanceModeId;
	private BigDecimal sellRateMin;
	private BigDecimal sellRateMax;
	private BigDecimal serviceId;
	private BigDecimal prvBuyRateMax;
	private BigDecimal prvBuyRateMin;
	private BigDecimal prvSellRateMin;
	private BigDecimal prvSellRateMax;
	
	public ExchangeRateApprovalDetModel() {
		super();
	}

	public ExchangeRateApprovalDetModel(BigDecimal exchangeRateMasterAprDetId,
			BigDecimal applicationCountryId, String approvedBy,
			Date approvedDate, String authorisedBy, Date authorisedDate,
			BigDecimal bankId, BigDecimal buyRateMax, BigDecimal buyRateMin,
			BigDecimal corporateRate, BigDecimal countryBranchId,
			BigDecimal countryId, String createdBy, Date createdDate,
			BigDecimal currencyId, BigDecimal deliveryModeId,
			BigDecimal exchangeRateMasterId, String isActive,
			String modifiedBy, Date modifiedDate, String remarks,
			BigDecimal remitanceModeId, BigDecimal sellRateMin,
			BigDecimal sellRateMax, BigDecimal serviceId,
			BigDecimal prvBuyRateMax, BigDecimal prvBuyRateMin,
			BigDecimal prvSellRateMin, BigDecimal prvSellRateMax) {
		super();
		this.exchangeRateMasterAprDetId = exchangeRateMasterAprDetId;
		this.applicationCountryId = applicationCountryId;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.authorisedBy = authorisedBy;
		this.authorisedDate = authorisedDate;
		this.bankId = bankId;
		this.buyRateMax = buyRateMax;
		this.buyRateMin = buyRateMin;
		this.corporateRate = corporateRate;
		this.countryBranchId = countryBranchId;
		this.countryId = countryId;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.currencyId = currencyId;
		this.deliveryModeId = deliveryModeId;
		this.exchangeRateMasterId = exchangeRateMasterId;
		this.isActive = isActive;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.remarks = remarks;
		this.remitanceModeId = remitanceModeId;
		this.sellRateMin = sellRateMin;
		this.sellRateMax = sellRateMax;
		this.serviceId = serviceId;
		this.prvBuyRateMax = prvBuyRateMax;
		this.prvBuyRateMin = prvBuyRateMin;
		this.prvSellRateMin = prvSellRateMin;
		this.prvSellRateMax = prvSellRateMax;
	}



	@Id
	@GeneratedValue(generator="ex_exchange_rate_master_ap_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_exchange_rate_master_ap_seq",sequenceName="EX_EXCHANGE_RATE_MASTER_AP_SEQ",allocationSize=1)
	@Column(name ="EXCHANGE_RATE_MASTER_APR_ID" , unique=true, nullable=false, precision=22, scale=0)
	public BigDecimal getExchangeRateMasterAprDetId() {
		return exchangeRateMasterAprDetId;
	}
	public void setExchangeRateMasterAprDetId(BigDecimal exchangeRateMasterAprDetId) {
		this.exchangeRateMasterAprDetId = exchangeRateMasterAprDetId;
	}

	@Column(name = "APPLICATION_COUNTRY_ID")
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}
	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
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

	@Column(name = "AUTHORISED_BY")
	public String getAuthorisedBy() {
		return authorisedBy;
	}
	public void setAuthorisedBy(String authorisedBy) {
		this.authorisedBy = authorisedBy;
	}

	@Column(name = "AUTHORISED_DATE")
	public Date getAuthorisedDate() {
		return authorisedDate;
	}
	public void setAuthorisedDate(Date authorisedDate) {
		this.authorisedDate = authorisedDate;
	}

	@Column(name = "BANK_ID")
	public BigDecimal getBankId() {
		return bankId;
	}
	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}

	@Column(name = "BUY_RATE_MAX")
	public BigDecimal getBuyRateMax() {
		return buyRateMax;
	}
	public void setBuyRateMax(BigDecimal buyRateMax) {
		this.buyRateMax = buyRateMax;
	}

	@Column(name = "BUY_RATE_MIN")
	public BigDecimal getBuyRateMin() {
		return buyRateMin;
	}
	public void setBuyRateMin(BigDecimal buyRateMin) {
		this.buyRateMin = buyRateMin;
	}

	@Column(name = "CORPORATE_RATE")
	public BigDecimal getCorporateRate() {
		return corporateRate;
	}
	public void setCorporateRate(BigDecimal corporateRate) {
		this.corporateRate = corporateRate;
	}

	@Column(name = "COUNTRY_BRANCH_ID")
	public BigDecimal getCountryBranchId() {
		return countryBranchId;
	}
	public void setCountryBranchId(BigDecimal countryBranchId) {
		this.countryBranchId = countryBranchId;
	}

	@Column(name = "COUNTRY_ID")
	public BigDecimal getCountryId() {
		return countryId;
	}
	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
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

	@Column(name = "CURRENCY_ID")
	public BigDecimal getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	@Column(name = "DELIVERY_MODE_ID")
	public BigDecimal getDeliveryModeId() {
		return deliveryModeId;
	}
	public void setDeliveryModeId(BigDecimal deliveryModeId) {
		this.deliveryModeId = deliveryModeId;
	}

	@Column(name = "EXCHANGE_RATE_MASTER_ID")
	public BigDecimal getExchangeRateMasterId() {
		return exchangeRateMasterId;
	}
	public void setExchangeRateMasterId(BigDecimal exchangeRateMasterId) {
		this.exchangeRateMasterId = exchangeRateMasterId;
	}

	@Column(name = "ISACTIVE")
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
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

	@Column(name = "REMARKS")
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name = "REMITTANCE_MODE_ID")
	public BigDecimal getRemitanceModeId() {
		return remitanceModeId;
	}
	public void setRemitanceModeId(BigDecimal remitanceModeId) {
		this.remitanceModeId = remitanceModeId;
	}

	@Column(name = "SELL_RATE_MIN")
	public BigDecimal getSellRateMin() {
		return sellRateMin;
	}
	public void setSellRateMin(BigDecimal sellRateMin) {
		this.sellRateMin = sellRateMin;
	}

	@Column(name = "SELL_RATE_MAX")
	public BigDecimal getSellRateMax() {
		return sellRateMax;
	}
	public void setSellRateMax(BigDecimal sellRateMax) {
		this.sellRateMax = sellRateMax;
	}

	@Column(name = "SERVICE_INDICATOR_ID")
	public BigDecimal getServiceId() {
		return serviceId;
	}
	public void setServiceId(BigDecimal serviceId) {
		this.serviceId = serviceId;
	}

	@Column(name = "PRV_BUY_RATE_MAX")
	public BigDecimal getPrvBuyRateMax() {
		return prvBuyRateMax;
	}
	public void setPrvBuyRateMax(BigDecimal prvBuyRateMax) {
		this.prvBuyRateMax = prvBuyRateMax;
	}

	@Column(name = "PRV_BUY_RATE_MIN")
	public BigDecimal getPrvBuyRateMin() {
		return prvBuyRateMin;
	}
	public void setPrvBuyRateMin(BigDecimal prvBuyRateMin) {
		this.prvBuyRateMin = prvBuyRateMin;
	}

	@Column(name = "PRV_SELL_RATE_MIN")
	public BigDecimal getPrvSellRateMin() {
		return prvSellRateMin;
	}
	public void setPrvSellRateMin(BigDecimal prvSellRateMin) {
		this.prvSellRateMin = prvSellRateMin;
	}

	@Column(name = "PRV_SELL_RATE_MAX")
	public BigDecimal getPrvSellRateMax() {
		return prvSellRateMax;
	}
	public void setPrvSellRateMax(BigDecimal prvSellRateMax) {
		this.prvSellRateMax = prvSellRateMax;
	}
	
}
