package com.amg.exchange.treasury.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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
import javax.persistence.UniqueConstraint;

import com.amg.exchange.remittance.model.BankServiceRule;
import com.amg.exchange.remittance.model.Remittance;
import com.amg.exchange.remittance.model.RemittanceApplication;
import com.amg.exchange.remittance.model.ServiceApplicabilityRule;

/**
 * Author Rahamathali Shaik
 */
@Entity
@Table(name="EX_REMITTANCE_MODE", uniqueConstraints = @UniqueConstraint(columnNames = {"REMITTANCE_CODE" }))
public class RemittanceModeMaster {
	
	private  BigDecimal remittanceModeId;
	private ServiceMaster serviceMaster;
	private String remittance;
/*	private String remittanceDesEnglish;
	private String remittanceDesArabic;*/
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String isActive;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;
	private List<BeneCountryService> beneCountryServiceMaster=new ArrayList<BeneCountryService>();
	private List<RemittanceModeDescription> remittanceModeDescription=new ArrayList<RemittanceModeDescription>();
	private List<ServiceApplicabilityRule> serviceApplicabilityRule=new ArrayList<ServiceApplicabilityRule>();
	private List<BankServiceRule> bankServiceRule=new ArrayList<BankServiceRule>();
	private Set<Remittance> exRemittance = new HashSet<Remittance>(0);
	private Set<RemittanceApplication> exRemittanceApplication = new HashSet<RemittanceApplication>(0);
	
	
	
	public RemittanceModeMaster(BigDecimal remittanceModeId) {
		super();
		this.remittanceModeId = remittanceModeId;
	}
	public RemittanceModeMaster() {
		super();
	}
	public RemittanceModeMaster(BigDecimal remittanceModeId,
			ServiceMaster serviceMaster, String remittance,
			String createdBy, Date createdDate, String modifiedBy,
			Date modifiedDate, String isActive,
			List<BeneCountryService> beneCountryServiceMaster,List<RemittanceModeDescription> remittanceModeDescription,
			List<ServiceApplicabilityRule> serviceApplicabilityRule,
			List<BankServiceRule> bankServiceRule,
			Set<Remittance> exRemittance,
			Set<RemittanceApplication> exRemittanceApplication, String approvedBy,Date approvedDate,String remarks) {
			
		super();
		this.remittanceModeId = remittanceModeId;
		this.serviceMaster = serviceMaster;
		this.remittance = remittance;
		
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.isActive = isActive;
		this.beneCountryServiceMaster=beneCountryServiceMaster;
		this.remittanceModeDescription=remittanceModeDescription;
		this.serviceApplicabilityRule = serviceApplicabilityRule;
		this.bankServiceRule = bankServiceRule;
		this.exRemittance = exRemittance;
		this.exRemittanceApplication = exRemittanceApplication;
		this.approvedBy=approvedBy;
		this.approvedDate=approvedDate;
		this.remarks=remarks;
	}
	
	
	@Id
	@GeneratedValue(generator="ex_remittance_mode_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_remittance_mode_seq",sequenceName="EX_REMITTANCE_MODE_SEQ",allocationSize=1)
	@Column(name ="REMITTANCE_MODE_ID" , unique=true, nullable=false, precision=22, scale=0)
	public BigDecimal getRemittanceModeId() {
		return remittanceModeId;
	}
	public void setRemittanceModeId(BigDecimal remittanceModeId) {
		this.remittanceModeId = remittanceModeId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SERVICE_MASTER_ID")
	public ServiceMaster getServiceMaster() {
		return serviceMaster;
	}
	public void setServiceMaster(ServiceMaster serviceMaster) {
		this.serviceMaster = serviceMaster;
	}
	@Column(name="REMITTANCE_CODE")
	public String getRemittance() {
		return remittance;
	}
	public void setRemittance(String remittance) {
		this.remittance = remittance;
	}
	
	/*@Column(name="REMITTANCE_DESC_ENGLISH")
	public String getRemittanceDesEnglish() {
		return remittanceDesEnglish;
	}
	public void setRemittanceDesEnglish(String remittanceDesEnglish) {
		this.remittanceDesEnglish = remittanceDesEnglish;
	}
	
	@Column(name="REMITTANCE_DESC_ARABIC")
	public String getRemittanceDesArabic() {
		return remittanceDesArabic;
	}
	public void setRemittanceDesArabic(String remittanceDesArabic) {
		this.remittanceDesArabic = remittanceDesArabic;
	}*/
	
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
	@Column(name= "MODIFIED_DATE")
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	@Column(name= "ISACTIVE")
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "remitanceId")
	public List<BeneCountryService> getBeneCountryServiceMaster() {
		return beneCountryServiceMaster;
	}
	public void setBeneCountryServiceMaster(
			List<BeneCountryService> beneCountryServiceMaster) {
		this.beneCountryServiceMaster = beneCountryServiceMaster;
	}
	
	
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "remittanceModeMaster")
	public List<RemittanceModeDescription> getRemittanceModeDescription() {
		return remittanceModeDescription;
	}
	public void setRemittanceModeDescription(
			List<RemittanceModeDescription> remittanceModeDescription) {
		this.remittanceModeDescription = remittanceModeDescription;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "remittanceModeId")
	public List<ServiceApplicabilityRule> getServiceApplicabilityRule() {
		return serviceApplicabilityRule;
	}
	public void setServiceApplicabilityRule(
			List<ServiceApplicabilityRule> serviceApplicabilityRule) {
		this.serviceApplicabilityRule = serviceApplicabilityRule;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "remittanceModeId")
	public List<BankServiceRule> getBankServiceRule() {
		return bankServiceRule;
	}
	public void setBankServiceRule(List<BankServiceRule> bankServiceRule) {
		this.bankServiceRule = bankServiceRule;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exRemittanceMode")
	public Set<Remittance> getExRemittance() {
		return exRemittance;
	}
	public void setExRemittance(Set<Remittance> exRemittance) {
		this.exRemittance = exRemittance;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exRemittanceMode")
	public Set<RemittanceApplication> getExRemittanceApplication() {
		return exRemittanceApplication;
	}
	public void setExRemittanceApplication(
			Set<RemittanceApplication> exRemittanceApplication) {
		this.exRemittanceApplication = exRemittanceApplication;
	}
	
	@Column(name= "APPROVED_BY")
	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	@Column(name= "APPROVED_DATE")
	public Date getApprovedDate() {
		return approvedDate;
	}
	
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
	
	@Column(name= "REMARKS")
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
	
}