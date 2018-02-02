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

@Entity
@Table(name = "EX_ROUTING_SETUP_MASTER")
public class RoutingSetupMaster implements java.io.Serializable{
	
	private static final long serialVersionUID = -59155724624336535L;
	
	private BigDecimal routingSetupMasterid;
	private CountryMaster exApplicationCountry;
	private CountryMaster exCountryMaster;
	private CurrencyMaster exCurrenyMaster;
	private BankMaster exBankMaster;
	private String isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	
	public RoutingSetupMaster() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RoutingSetupMaster(BigDecimal routingSetupMasterid,CountryMaster exApplicationCountry,
			CountryMaster exCountryMaster, CurrencyMaster exCurrenyMaster,
			BankMaster exBankMaster,String isActive, String createdBy, Date createdDate,
			String modifiedBy, Date modifiedDate) {
		super();
		this.routingSetupMasterid = routingSetupMasterid;
		this.exApplicationCountry = exApplicationCountry;
		this.exCountryMaster = exCountryMaster;
		this.exCurrenyMaster = exCurrenyMaster;
		this.exBankMaster = exBankMaster;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}


	@Id
	@GeneratedValue(generator="ex_routing_setup_master_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_routing_setup_master_seq",sequenceName="EX_ROUTING_SETUP_MASTER_SEQ",allocationSize=1)
	@Column(name ="ROUTING_SETUP_MASTER_ID" , unique=true, nullable=false, precision=22, scale=0)
	public BigDecimal getRoutingSetupMasterid() {
		return routingSetupMasterid;
	}

	public void setRoutingSetupMasterid(BigDecimal routingSetupMasterid) {
		this.routingSetupMasterid = routingSetupMasterid;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "APPLICATION_COUNTRY_ID")
	public CountryMaster getExApplicationCountry() {
		return exApplicationCountry;
	}

	public void setExApplicationCountry(CountryMaster exApplicationCountry) {
		this.exApplicationCountry = exApplicationCountry;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_COUNTRY_ID")
	public CountryMaster getExCountryMaster() {
		return exCountryMaster;
	}

	public void setExCountryMaster(CountryMaster exCountryMaster) {
		this.exCountryMaster = exCountryMaster;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CURRENCY_ID")
	public CurrencyMaster getExCurrenyMaster() {
		return exCurrenyMaster;
	}

	public void setExCurrenyMaster(CurrencyMaster exCurrenyMaster) {
		this.exCurrenyMaster = exCurrenyMaster;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_ID")
	public BankMaster getExBankMaster() {
		return exBankMaster;
	}

	public void setExBankMaster(BankMaster exBankMaster) {
		this.exBankMaster = exBankMaster;
	}
	
	@Column(name="ISACTIVE")
	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
	@Column(name="CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	@Column(name="CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	@Column(name="MODIFIED_BY")
	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	@Column(name="MODIFIED_DATE")
	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
}
