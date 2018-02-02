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
@Entity
@Table(name="EX_BENEFICARY_STATUS_MASTER")
public class BeneficaryStatus implements Serializable {
	
	private static final long serialVersionUID = 2315791709068216697L;
	
	private BigDecimal beneficaryStatusId;
	private String beneficaryStatusName;
	private Date createdDate;
	private String createdBy;
	private String modifiedBy;
	private Date modifiedDate;
	private String isActive;
	
	
	
	public BeneficaryStatus() {
		
	}

	public BeneficaryStatus(BigDecimal beneficaryStatusId,
			String beneficaryStatusName, Date createdDate, String createdBy,
			String modifiedBy, Date modifiedDate, String isActive) {
		super();
		this.beneficaryStatusId = beneficaryStatusId;
		this.beneficaryStatusName = beneficaryStatusName;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.isActive = isActive;
	}

	@Id
	@GeneratedValue(generator="ex_beneficary_status_seq" ,strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_beneficary_status_seq",sequenceName="EX_BENEFICARY_STATUS_SEQ",allocationSize=1)
	@Column(name="BENEFICARY_STATUS_ID",unique=true, nullable=false, precision=22, scale=0)
	public BigDecimal getBeneficaryStatusId() {
		return beneficaryStatusId;
	}
	
	public void setBeneficaryStatusId(BigDecimal beneficaryStatusId) {
		this.beneficaryStatusId = beneficaryStatusId;
	}
	
	@Column(name="BENEFICARY_STATUS_NAME")
	public String getBeneficaryStatusName() {
		return beneficaryStatusName;
	}
	public void setBeneficaryStatusName(String beneficaryStatusName) {
		this.beneficaryStatusName = beneficaryStatusName;
	}
	
	@Column(name="CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	@Column(name="CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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
	
	
	

}
