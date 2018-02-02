package com.amg.exchange.foreigncurrency.model;

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

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.remittance.model.Remittance;

@Entity
@Table(name = "EX_SOURCE_OF_INCOME" )
public class SourceOfIncome implements java.io.Serializable {

	private static final long serialVersionUID = 1L;


	private BigDecimal sourceId;
	private CountryMaster fsCountryMaster;
	private CompanyMaster fsCompanyMaster;
	private String  sourceCode;
	private String createdBy;
	private Date creationDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String isActive;
	private String remarks;
	private String approvedBy;
	private Date approvedDate;
	
	private List<ReceiptPayment> receiptPayment = new ArrayList<ReceiptPayment>();
	private Set<Remittance> exRemittance = new HashSet<Remittance>(0);
	private List<SourceOfIncomeDescription> exSourceOfIncome=new ArrayList<SourceOfIncomeDescription>() ;

	public SourceOfIncome() {
	}
	public SourceOfIncome(BigDecimal sourceId, CountryMaster fsCountryMaster,
			CompanyMaster fsCompanyMaster, String sourceCode, String createdBy,
			Date creationDate, String modifiedBy, Date modifiedDate,
			String isActive, String remarks, String approvedBy,
			Date approvedDate, List<ReceiptPayment> receiptPayment,
			Set<Remittance> exRemittance,List<SourceOfIncomeDescription> exSourceOfIncome) {
		super();
		this.sourceId = sourceId;
		this.fsCountryMaster = fsCountryMaster;
		this.fsCompanyMaster = fsCompanyMaster;
		this.sourceCode = sourceCode;
		this.createdBy = createdBy;
		this.creationDate = creationDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.isActive = isActive;
		this.remarks = remarks;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.receiptPayment = receiptPayment;
		this.exRemittance = exRemittance;
		this.exSourceOfIncome=exSourceOfIncome;
	}

	
	/**
	 * @param sourceId
	 * @param fsCountryMaster
	 * @param fsCompanyMaster
	 * @param sourceFullDesc
	 * @param sourceShortDesc
	 * @param localFullDesc
	 * @param localShortDesc
	 * @param createdBy
	 * @param creationDate
	 * @param modifiedBy
	 * @param modifiedDate
	 * @param receiptPayment
	 */
	/*public SourceOfIncome(BigDecimal sourceId, CountryMaster fsCountryMaster,
			CompanyMaster fsCompanyMaster, String sourceFullDesc,
			String sourceShortDesc, String localFullDesc,
			String localShortDesc, String createdBy, Date creationDate,
			String modifiedBy, Date modifiedDate,
			List<ReceiptPayment> receiptPayment,String isActive,String remarks) {
		this.sourceId = sourceId;
		this.fsCountryMaster = fsCountryMaster;
		this.fsCompanyMaster = fsCompanyMaster;
		this.sourceCode  = sourceCode;
		this.createdBy = createdBy;
		this.creationDate = creationDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.receiptPayment = receiptPayment;
		this.exRemittance = exRemittance;
		this.isActive= isActive;
		this.remarks= remarks;
	}*/

	@Id
	@GeneratedValue(generator="ex_source_of_income_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_source_of_income_seq",sequenceName="EX_SOURCE_OF_INCOME_SEQ",allocationSize=1)
	@Column(name = "SOURCE_OF_INCOME_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getSourceId() {
		return this.sourceId;
	}
	public void setSourceId(BigDecimal sourceId) {
		this.sourceId = sourceId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "APPLICATION_COUNTRY_ID")
	public CountryMaster getFsCountryMaster() {
		return this.fsCountryMaster;
	}
	public void setFsCountryMaster(CountryMaster fsCountryMaster) {
		this.fsCountryMaster = fsCountryMaster;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPANY_ID")
	public CompanyMaster getFsCompanyMaster() {
		return this.fsCompanyMaster;
	}
	public void setFsCompanyMaster(CompanyMaster fsCompanyMaster) {
		this.fsCompanyMaster = fsCompanyMaster;
	}
	
/*	@Column(name = "SOURCE_FULL_DESC")
	public String getSourceFullDesc() {
		return sourceFullDesc;
	}
	public void setSourceFullDesc(String sourceFullDesc) {
		this.sourceFullDesc = sourceFullDesc;
	}*/
	
	@Column(name = "CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "CREATED_DATE")
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
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
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "sourceOfIncome")
	public List<ReceiptPayment> getReceiptPayment() {
		return receiptPayment;
	}
	
	public void setReceiptPayment(List<ReceiptPayment> receiptPayment) {
		this.receiptPayment = receiptPayment;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exSourceOfIncome")
	public Set<Remittance> getExRemittance() {
		return exRemittance;
	}

	public void setExRemittance(Set<Remittance> exRemittance) {
		this.exRemittance = exRemittance;
	}
	
	@Column(name = "ISACTIVE")
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Column(name = "REMARKS")
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name = "SOURCE_CODE")
	public String getSourceCode() {
		return sourceCode;
	}


	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
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
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "sourceOfIncomeId")
	public List<SourceOfIncomeDescription> getExSourceOfIncome() {
		return exSourceOfIncome;
	}
	public void setExSourceOfIncome(List<SourceOfIncomeDescription> exSourceOfIncome) {
		this.exSourceOfIncome = exSourceOfIncome;
	}
	
}
