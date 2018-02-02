package com.amg.exchange.treasury.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.ServiceMasterDesc;

/*
 * Author Rahamathali Shaik
*/
public class RatesUpdateBeanDataTable {
	
	private BigDecimal countryId;
	private String countryName;
	private BigDecimal currencyId;
	private String currencyName;
	private BigDecimal bankId;
	private String bankName;
	private BigDecimal serviceId;
	private String serviceName;
	private BigDecimal branchId;
	private String branchName;
	private BigDecimal sellingRateMin;
	private BigDecimal sellingRateMax;
	private BigDecimal buyingRateMin;
	private BigDecimal buyingRateMax;
	private BigDecimal corporateRate;
	private BigDecimal remittanceId;
	private String remittanceName;
	private BigDecimal deliveryId;
	private String deliveryName;
	private BigDecimal ratesUpdatePk;
	private String createdBy;
	private Date CreatedDate;
	private BigDecimal appcountryId;
	private String isActive;
	private BigDecimal prvSellMinRate;
	private BigDecimal prvSellMaxRate;
	private BigDecimal prvBuyMinRate;
	private BigDecimal prvBuyMaxRate;
	private BigDecimal amount;
	
	private List<CurrencyMaster> currencyList = new ArrayList<CurrencyMaster>();
	private List<CountryMasterDesc> countryList = new ArrayList<CountryMasterDesc>();
	private List<BankMaster> bankList = new ArrayList<BankMaster>();
	private List<ServiceMasterDesc> serviceMasters = new ArrayList<ServiceMasterDesc>();
	private List<CountryBranch> countryBranchList = new ArrayList<CountryBranch>();
	
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getRemittanceName() {
		return remittanceName;
	}
	public void setRemittanceName(String remittanceName) {
		this.remittanceName = remittanceName;
	}

	public String getDeliveryName() {
		return deliveryName;
	}
	public void setDeliveryName(String deliveryName) {
		this.deliveryName = deliveryName;
	}

	public BigDecimal getRemittanceId() {
		return remittanceId;
	}
	public void setRemittanceId(BigDecimal remittanceId) {
		this.remittanceId = remittanceId;
	}

	public BigDecimal getDeliveryId() {
		return deliveryId;
	}
	public void setDeliveryId(BigDecimal deliveryId) {
		this.deliveryId = deliveryId;
	}

	public BigDecimal getCountryId() {
		return countryId;
	}
	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}

	public BigDecimal getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	public BigDecimal getBankId() {
		return bankId;
	}
	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}

	public BigDecimal getServiceId() {
		return serviceId;
	}
	public void setServiceId(BigDecimal serviceId) {
		this.serviceId = serviceId;
	}

	public BigDecimal getBranchId() {
		return branchId;
	}
	public void setBranchId(BigDecimal branchId) {
		this.branchId = branchId;
	}

	public BigDecimal getSellingRateMin() {
		return sellingRateMin;
	}
	public void setSellingRateMin(BigDecimal sellingRateMin) {
		this.sellingRateMin = sellingRateMin;
	}

	public BigDecimal getSellingRateMax() {
		return sellingRateMax;
	}
	public void setSellingRateMax(BigDecimal sellingRateMax) {
		this.sellingRateMax = sellingRateMax;
	}

	public BigDecimal getBuyingRateMin() {
		return buyingRateMin;
	}
	public void setBuyingRateMin(BigDecimal buyingRateMin) {
		this.buyingRateMin = buyingRateMin;
	}

	public BigDecimal getBuyingRateMax() {
		return buyingRateMax;
	}
	public void setBuyingRateMax(BigDecimal buyingRateMax) {
		this.buyingRateMax = buyingRateMax;
	}

	public BigDecimal getCorporateRate() {
		return corporateRate;
	}
	public void setCorporateRate(BigDecimal corporateRate) {
		this.corporateRate = corporateRate;
	}

	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public BigDecimal getRatesUpdatePk() {
		return ratesUpdatePk;
	}
	public void setRatesUpdatePk(BigDecimal ratesUpdatePk) {
		this.ratesUpdatePk = ratesUpdatePk;
	}

	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return CreatedDate;
	}
	public void setCreatedDate(Date createdDate) {
		CreatedDate = createdDate;
	}

	public BigDecimal getAppcountryId() {
		return appcountryId;
	}
	public void setAppcountryId(BigDecimal appcountryId) {
		this.appcountryId = appcountryId;
	}

	public BigDecimal getPrvSellMinRate() {
		return prvSellMinRate;
	}
	public void setPrvSellMinRate(BigDecimal prvSellMinRate) {
		this.prvSellMinRate = prvSellMinRate;
	}

	public BigDecimal getPrvSellMaxRate() {
		return prvSellMaxRate;
	}
	public void setPrvSellMaxRate(BigDecimal prvSellMaxRate) {
		this.prvSellMaxRate = prvSellMaxRate;
	}

	public BigDecimal getPrvBuyMinRate() {
		return prvBuyMinRate;
	}
	public void setPrvBuyMinRate(BigDecimal prvBuyMinRate) {
		this.prvBuyMinRate = prvBuyMinRate;
	}

	public BigDecimal getPrvBuyMaxRate() {
		return prvBuyMaxRate;
	}
	public void setPrvBuyMaxRate(BigDecimal prvBuyMaxRate) {
		this.prvBuyMaxRate = prvBuyMaxRate;
	}

	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public List<CurrencyMaster> getCurrencyList() {
		return currencyList;
	}
	public void setCurrencyList(List<CurrencyMaster> currencyList) {
		this.currencyList = currencyList;
	}
	
	public List<CountryMasterDesc> getCountryList() {
		return countryList;
	}
	public void setCountryList(List<CountryMasterDesc> countryList) {
		this.countryList = countryList;
	}
	
	public List<BankMaster> getBankList() {
		return bankList;
	}
	public void setBankList(List<BankMaster> bankList) {
		this.bankList = bankList;
	}
	
	public List<ServiceMasterDesc> getServiceMasters() {
		return serviceMasters;
	}
	public void setServiceMasters(List<ServiceMasterDesc> serviceMasters) {
		this.serviceMasters = serviceMasters;
	}
	
	public List<CountryBranch> getCountryBranchList() {
		return countryBranchList;
	}
	public void setCountryBranchList(List<CountryBranch> countryBranchList) {
		this.countryBranchList = countryBranchList;
	}
		
}
