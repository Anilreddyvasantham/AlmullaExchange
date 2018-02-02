package com.amg.exchange.remittance.model;

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

import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.DeliveryMode;
import com.amg.exchange.treasury.model.FundEstimationDays;
import com.amg.exchange.treasury.model.RemittanceModeMaster;
import com.amg.exchange.treasury.model.ServiceMaster;

@Entity
@Table(name = "EX_ROUTING_HEADER")
public class RoutingHeader implements java.io.Serializable{
	
	private static final long serialVersionUID = -59155724624336535L;
	
	private BigDecimal routingHeaderId;
	private CountryMaster exApplicationCountry;
	private CountryMaster exCountryId;
	private CountryMaster exRoutingCountryId;
	private BankMaster exRoutingBankId;
	private CurrencyMaster exCurrenyId;
	private ServiceMaster exServiceId;
	private RemittanceModeMaster exRemittanceModeId;
	private DeliveryMode exDeliveryModeId;
	private String branchApplicability;
	private String isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private Set<RoutingDetails> exroutingDetailsId = new HashSet<RoutingDetails>(0);
	private Set<Remittance> exRemittance = new HashSet<Remittance>(0);
	
	public RoutingHeader() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public RoutingHeader(BigDecimal routingHeaderId,
			CountryMaster exApplicationCountry, CountryMaster exCountryId,
			CountryMaster exRoutingCountryId, BankMaster exRoutingBankId,
			CurrencyMaster exCurrenyId, ServiceMaster exServiceId,
			RemittanceModeMaster exRemittanceModeId,
			DeliveryMode exDeliveryModeId, String branchApplicability,
			String isActive, String createdBy, Date createdDate,
			String modifiedBy, Date modifiedDate,Set<Remittance> exRemittance
			) {
		super();
		this.routingHeaderId = routingHeaderId;
		this.exApplicationCountry = exApplicationCountry;
		this.exCountryId = exCountryId;
		this.exRoutingCountryId = exRoutingCountryId;
		this.exRoutingBankId = exRoutingBankId;
		this.exCurrenyId = exCurrenyId;
		this.exServiceId = exServiceId;
		this.exRemittanceModeId = exRemittanceModeId;
		this.exDeliveryModeId = exDeliveryModeId;
		this.branchApplicability = branchApplicability;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.exRemittance = exRemittance;
	}


	@Id
	@GeneratedValue(generator="ex_routing_header_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_routing_header_seq",sequenceName="EX_ROUTING_HEADER_SEQ",allocationSize=1)
	@Column(name ="ROUTING_HEADER_ID" , unique=true, nullable=false, precision=22, scale=0)
	public BigDecimal getRoutingHeaderId() {
		return routingHeaderId;
	}
	
	public void setRoutingHeaderId(BigDecimal routingHeaderId) {
		this.routingHeaderId = routingHeaderId;
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
	@JoinColumn(name = "COUNTRY_ID")
	public CountryMaster getExCountryId() {
		return exCountryId;
	}

	public void setExCountryId(CountryMaster exCountryId) {
		this.exCountryId = exCountryId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROUTING_COUNTRY_ID")
	public CountryMaster getExRoutingCountryId() {
		return exRoutingCountryId;
	}

	public void setExRoutingCountryId(CountryMaster exRoutingCountryId) {
		this.exRoutingCountryId = exRoutingCountryId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROUTING_BANK_ID")
	public BankMaster getExRoutingBankId() {
		return exRoutingBankId;
	}

	public void setExRoutingBankId(BankMaster exRoutingBankId) {
		this.exRoutingBankId = exRoutingBankId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CURRENCY_ID")
	public CurrencyMaster getExCurrenyId() {
		return exCurrenyId;
	}

	public void setExCurrenyId(CurrencyMaster exCurrenyId) {
		this.exCurrenyId = exCurrenyId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SERVICE_MASTER_ID")
	public ServiceMaster getExServiceId() {
		return exServiceId;
	}

	public void setExServiceId(ServiceMaster exServiceId) {
		this.exServiceId = exServiceId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REMITTANCE_MODE_ID")
	public RemittanceModeMaster getExRemittanceModeId() {
		return exRemittanceModeId;
	}

	public void setExRemittanceModeId(RemittanceModeMaster exRemittanceModeId) {
		this.exRemittanceModeId = exRemittanceModeId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DELIVERY_MODE_ID")
	public DeliveryMode getExDeliveryModeId() {
		return exDeliveryModeId;
	}

	public void setExDeliveryModeId(DeliveryMode exDeliveryModeId) {
		this.exDeliveryModeId = exDeliveryModeId;
	}

	@Column(name="BRANCH_APPLICABILITY")
	public String getBranchApplicability() {
		return branchApplicability;
	}

	public void setBranchApplicability(String branchApplicability) {
		this.branchApplicability = branchApplicability;
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
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "routingDetailsId")
	public Set<RoutingDetails> getExroutingDetailsId() {
		return exroutingDetailsId;
	}

	public void setExroutingDetailsId(Set<RoutingDetails> exroutingDetailsId) {
		this.exroutingDetailsId = exroutingDetailsId;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exRoutingHeader")
	public Set<Remittance> getExRemittance() {
		return exRemittance;
	}

	public void setExRemittance(Set<Remittance> exRemittance) {
		this.exRemittance = exRemittance;
	}
	@Column(name="APPROVED_BY")
	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	@Column(name="APPROVED_DATE")
	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
	
	
}
