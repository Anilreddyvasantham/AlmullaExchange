package com.amg.exchange.foreigncurrency.model;

import java.io.Serializable;
import java.math.BigDecimal;

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
import com.amg.exchange.registration.model.RoleMaster;
import com.amg.exchange.treasury.model.CurrencyMaster;


@Entity
@Table(name ="FS_ROLEWISE_EXCHANGE_RATE")
public class RoleWiseExchangeRate implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal roleWiseExchangeRateId;
	private CountryMaster country;
	private CurrencyMaster currency;
	private RoleMaster role;
	private BigDecimal roleMinRate;
	private BigDecimal roleMaxRate;
	private BigDecimal roleMinSaleRate;
	private BigDecimal roleMaxSaleRate;
	private String isActive;
	
	
	
	
	
	@Id
	@GeneratedValue(generator="fs_rolewise_exchange_rate_seq", strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="fs_rolewise_exchange_rate_seq", sequenceName="FS_ROLEWISE_EXCHANGE_RATE_SEQ",allocationSize=1)
	@Column(name = "ROLEWISE_EXCHANGE_RATE_ID", unique = true, nullable = false, precision = 22, scale = 0)
	
	public BigDecimal getRoleWiseExchangeRateId() {
		return roleWiseExchangeRateId;
	}
	public void setRoleWiseExchangeRateId(BigDecimal roleWiseExchangeRateId) {
		this.roleWiseExchangeRateId = roleWiseExchangeRateId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_ID")	
	public CountryMaster getCountry() {
		return country;
	}
	public void setCountry(CountryMaster country) {
		this.country = country;
	}
	
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CURRENCY_ID")		
	public CurrencyMaster getCurrency() {
		return currency;
	}
	public void setCurrency(CurrencyMaster currency) {
		this.currency = currency;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROLE_ID")	
	public RoleMaster getRole() {
		return role;
	}
	public void setRole(RoleMaster role) {
		this.role = role;
	}
	
	@Column(name = "ROLE_MIN_RATE", precision = 22, scale = 0)
	public BigDecimal getRoleMinRate() {
		return roleMinRate;
	}
	public void setRoleMinRate(BigDecimal roleMinRate) {
		this.roleMinRate = roleMinRate;
	}
	
	@Column(name = "ROLE_MAX_RATE", precision = 22, scale = 0)
	public BigDecimal getRoleMaxRate() {
		return roleMaxRate;
	}
	public void setRoleMaxRate(BigDecimal roleMaxRate) {
		this.roleMaxRate = roleMaxRate;
	}
	
	@Column(name = "ISACTIVE", length = 1)
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
	@Column(name = "SALE_MIN_RAT", precision = 22, scale = 0)
	public BigDecimal getRoleMinSaleRate() {
		return roleMinSaleRate;
	}
	public void setRoleMinSaleRate(BigDecimal roleMinSaleRate) {
		this.roleMinSaleRate = roleMinSaleRate;
	}
	
	@Column(name = "SALE_MAX_RAT", precision = 22, scale = 0)
	public BigDecimal getRoleMaxSaleRate() {
		return roleMaxSaleRate;
	}
	public void setRoleMaxSaleRate(BigDecimal roleMaxSaleRate) {
		this.roleMaxSaleRate = roleMaxSaleRate;
	}
	
	
	
	
	
}
