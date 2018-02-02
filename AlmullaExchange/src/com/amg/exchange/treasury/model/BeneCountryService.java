package com.amg.exchange.treasury.model;

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
import com.amg.exchange.remittance.model.Remittance;


@Entity
@Table(name ="EX_BENE_COUNTRY_SERVICE")
public class BeneCountryService implements java.io.Serializable {
	
	private static final long serialVersionUID = -59155724624336535L;
    private BigDecimal beneCountrySeerviceId;
    private CountryMaster applicationCountryId;
    private CurrencyMaster currencyId;
    private CountryMaster beneCountryId;
    private DeliveryMode deliveryId;
    private ServiceMaster serviceId;
    private RemittanceModeMaster remitanceId;
    private String createBy;
    private Date createDate;
    private String modifiedBy;
    private Date modifiedDate;
    private String isActive;
    private String approvedBy;
	private Date approvedDate;
	private String remarks;
	
    
    
    private Set<Remittance> exRemittance = new HashSet<Remittance>(0);
    
	public BeneCountryService(){
		
	}
	

	public BeneCountryService(BigDecimal beneCountrySeerviceId) {
		super();
		this.beneCountrySeerviceId = beneCountrySeerviceId;
	}
    
    
    
    
    public BeneCountryService( BigDecimal beneCountrySeerviceId,
    CountryMaster applicationCountryId,
    CountryMaster beneCountryId,
    CurrencyMaster currencyId,
    DeliveryMode deliveryId,
    ServiceMaster serviceId,
    RemittanceModeMaster remitanceId,
    String createBy,
    Date createDate,
    String modifiedBy,
    Date modifiedDate,
    String isActive,
    String approvedBy,
    Date approvedDate,
    String remarks,
    Set<Remittance> exRemittance)
    {
    	this.beneCountrySeerviceId=beneCountrySeerviceId;
    	this.applicationCountryId=applicationCountryId;
    	this.beneCountryId=beneCountryId;
    	this.currencyId=currencyId;
    	this.deliveryId=deliveryId;
    	this.serviceId=serviceId;
    	this.remitanceId=remitanceId;
    	this.createBy=createBy;
    	this.createDate=createDate;
    	this.modifiedBy=modifiedBy;
    	this.modifiedDate=modifiedDate;
    	this.isActive=isActive;
    	
    	this.approvedBy=approvedBy;
    	this.approvedDate=approvedDate;
    	this.remarks=remarks;
    	    	
    	this.exRemittance = exRemittance;
    }

    
    @Id
	@GeneratedValue(generator="ex_bene_country_service_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_bene_country_service_seq",sequenceName="EX_BENE_COUNTRY_SERVICE_SEQ",allocationSize=1)
	@Column(name ="BENE_COUNTRY_SERVICE_ID" , unique=true, nullable=false, precision=22, scale=0)
	public BigDecimal getBeneCountrySeerviceId() {
		return beneCountrySeerviceId;
	}

   
	public void setBeneCountrySeerviceId(BigDecimal beneCountrySeerviceId) {
		this.beneCountrySeerviceId = beneCountrySeerviceId;
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
	@JoinColumn(name = "CURRENCY_ID")
	public CurrencyMaster getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(CurrencyMaster currencyId) {
		this.currencyId = currencyId;
	}
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "BENE_COUNTRY_ID")
	public CountryMaster getBeneCountryId() {
		return beneCountryId;
	}

	public void setBeneCountryId(CountryMaster beneCountryId) {
		this.beneCountryId = beneCountryId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DELIVERY_MODE_ID")
	public DeliveryMode getDeliveryId() {
		return deliveryId;
	}


	public void setDeliveryId(DeliveryMode deliveryId) {
		this.deliveryId = deliveryId;
	}


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SERVICE_MASTER_ID")
	public ServiceMaster getServiceId() {
		return serviceId;
	}

	public void setServiceId(ServiceMaster serviceId) {
		this.serviceId = serviceId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REMITTANCE_MODE_ID")
	public RemittanceModeMaster getRemitanceId() {
		return remitanceId;
	}

	public void setRemitanceId(RemittanceModeMaster remitanceId) {
		this.remitanceId = remitanceId;
	}
    
	@Column(name="CREATED_BY")
	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	@Column(name="CREATED_DATE")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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

	@Column(name="ISACTIVE")
	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exBeneCountryService")
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


	@Column(name="REMARKS")
	public String getRemarks() {
		return remarks;
	}


	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	
	
    
}
