package com.amg.exchange.remittance.model;

import java.math.BigDecimal;
import java.util.Date;
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
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.BankIndicator;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.DeliveryMode;
import com.amg.exchange.treasury.model.FundEstimationDays;
import com.amg.exchange.treasury.model.RemittanceModeMaster;
import com.amg.exchange.treasury.model.ServiceMaster;

@Entity
@Table(name = "EX_ROUTING_DETAILS")
public class RoutingDetails implements java.io.Serializable{
	
	private static final long serialVersionUID = -59155724624336535L;
	
	private BigDecimal routingDetailsId;
	private RoutingHeader exRountingHeaderId;
	private CountryMaster exApplicationCountry;
	private CountryMaster exCountryId;
	private CountryMaster exRoutingCountryId;
	private BankMaster exRoutingBankId;
	private CurrencyMaster exCurrenyId;
	private ServiceMaster exServiceId;
	private RemittanceModeMaster exRemittanceModeId;
	private DeliveryMode exDeliveryModeId;
	private BankIndicator exagentInd;
	private BankBranch exBankBranchId;
	private String branchApplicability;
	private String isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private BigDecimal agentBranchID;
	private BigDecimal agentBranchEmosMapCode;
	private BigDecimal agentBankId;
	
	public RoutingDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RoutingDetails(BigDecimal routingDetailsId,
			RoutingHeader exRountingHeaderId,
			CountryMaster exApplicationCountry, CountryMaster exCountryId,
			CountryMaster exRoutingCountryId, BankMaster exRoutingBankId,
			CurrencyMaster exCurrenyId, ServiceMaster exServiceId,
			RemittanceModeMaster exRemittanceModeId,
			DeliveryMode exDeliveryModeId, BankBranch exBankBranchId,
			String branchApplicability, String isActive, String createdBy,
			Date createdDate, String modifiedBy, Date modifiedDate,BigDecimal agentBranchID,BigDecimal agentBranchEmosMapCode,BigDecimal agentBankId) {
		super();
		this.routingDetailsId = routingDetailsId;
		this.exRountingHeaderId = exRountingHeaderId;
		this.exApplicationCountry = exApplicationCountry;
		this.exCountryId = exCountryId;
		this.exRoutingCountryId = exRoutingCountryId;
		this.exRoutingBankId = exRoutingBankId;
		this.exCurrenyId = exCurrenyId;
		this.exServiceId = exServiceId;
		this.exRemittanceModeId = exRemittanceModeId;
		this.exDeliveryModeId = exDeliveryModeId;
		this.exBankBranchId = exBankBranchId;
		this.branchApplicability = branchApplicability;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.agentBranchID=agentBranchID;
		this.agentBranchEmosMapCode=agentBranchEmosMapCode;
		this.agentBankId=agentBankId;
	}



	@Id
	@GeneratedValue(generator="ex_routing_details_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_routing_details_seq",sequenceName="EX_ROUTING_DETAILS_SEQ",allocationSize=1)
	@Column(name ="ROUTING_DETAILS_ID", unique=true, nullable=false, precision=22, scale=0)
	public BigDecimal getRoutingDetailsId() {
		return routingDetailsId;
	}

	public void setRoutingDetailsId(BigDecimal routingDetailsId) {
		this.routingDetailsId = routingDetailsId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROUTING_HEADER_ID")
	public RoutingHeader getExRountingHeaderId() {
		return exRountingHeaderId;
	}

	public void setExRountingHeaderId(RoutingHeader exRountingHeaderId) {
		this.exRountingHeaderId = exRountingHeaderId;
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AGENT_ID")
	public BankIndicator getExagentInd() {
		return exagentInd;
	}

	public void setExagentInd(BankIndicator exagentInd) {
		this.exagentInd = exagentInd;
	}
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_BRANCH_ID")
	public BankBranch getExBankBranchId() {
		return exBankBranchId;
	}

	public void setExBankBranchId(BankBranch exBankBranchId) {
		this.exBankBranchId = exBankBranchId;
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

	@Column(name="AGENT_BRANCH_ID")
	public BigDecimal getAgentBranchID() {
		return agentBranchID;
	}

	public void setAgentBranchID(BigDecimal agentBranchID) {
		this.agentBranchID = agentBranchID;
	}
	@Column(name="AGENT_BRCH_EMOS_MAP_CODE")
	public BigDecimal getAgentBranchEmosMapCode() {
		return agentBranchEmosMapCode;
	}

	public void setAgentBranchEmosMapCode(BigDecimal agentBranchEmosMapCode) {
		this.agentBranchEmosMapCode = agentBranchEmosMapCode;
	}
	@Column(name="AGENT_BANK_ID")
	public BigDecimal getAgentBankId() {
		return agentBankId;
	}

	public void setAgentBankId(BigDecimal agentBankId) {
		this.agentBankId = agentBankId;
	}
	
	
	
}
