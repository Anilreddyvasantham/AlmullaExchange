package com.amg.exchange.treasury.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "EX_BANK_INDICATOR")
public class BankIndicator implements java.io.Serializable{
 
	private static final long serialVersionUID = 1L;
	
	private BigDecimal bankIndicatorId;
	private String bankIndicatorCode;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;
	private String isActive;
	
  //private Set<BankApplicability> exBankApplicability = new HashSet<BankApplicability>(0);
	
	public BankIndicator() {
		
	}
	
	public BankIndicator(BigDecimal bankIndicatorId) {
		this.bankIndicatorId = bankIndicatorId;
	}

	public BankIndicator(BigDecimal bankIndicatorId, String bankIndicatorCode,
			String createdBy, Date createdDate, String modifiedBy,
			Date modifiedDate, String approvedBy, Date approvedDate,
			String remarks, String isActive,
			Set<BankApplicability> exBankApplicability) {
		super();
		this.bankIndicatorId = bankIndicatorId;
		this.bankIndicatorCode = bankIndicatorCode;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.remarks = remarks;
		this.isActive = isActive;
		//this.exBankApplicability = exBankApplicability;
	}

	/*public BankIndicator(BigDecimal bankIndicatorId, String bankIndicatorName,
			String bankIndicatorDesc) {
		super();
		this.bankIndicatorId = bankIndicatorId;
	 
		 
	}*/
	
	@Id
	@GeneratedValue(generator="ex_bank_indicator_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_bank_indicator_seq",sequenceName="EX_BANK_INDICATOR_SEQ",allocationSize=1)
	@Column(name = "BANK_INDICATOR_ID", unique = true, nullable = false, precision = 22, scale = 0)
	
	public BigDecimal getBankIndicatorId() {
		return this.bankIndicatorId;
	}
	public void setBankIndicatorId(BigDecimal bankIndicatorId) {
		this.bankIndicatorId = bankIndicatorId;
	}
	
	
	@Column(name ="BANK_IND" )
	public String getBankIndicatorCode() {
		return bankIndicatorCode;
	}

	public void setBankIndicatorCode(String bankIndicatorCode) {
	this.bankIndicatorCode = bankIndicatorCode;
	}
	@Column(name ="CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	@Column(name ="CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	@Column(name ="MODIFIED_BY")
	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	@Column(name ="MODIFIED_DATE")
	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	@Column(name ="APPROVED_BY")
	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	@Column(name ="APPROVED_DATE")
	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
	@Column(name ="REMARKS")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	@Column(name ="ISACTIVE")
	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	/*@OneToMany(fetch = FetchType.LAZY, mappedBy = "bankInd")
	public Set<BankApplicability> getExBankApplicability() {
		return exBankApplicability;
	}

	public void setExBankApplicability(Set<BankApplicability> exBankApplicability) {
		this.exBankApplicability = exBankApplicability;
	}*/

}
