package com.amg.exchange.foreigncurrency.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import javax.persistence.TableGenerator;

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;

@Entity
@Table(name = "EX_PURPOSE_OF_TRANSACTION" )
public class PurposeOfTransaction implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private BigDecimal purposeId;
	private CountryMaster fsCountryMaster;
	private CompanyMaster fsCompanyMaster;
	private String purposeFullDesc;
	private String purposeShortDesc;
	private String localFullDesc;
	private String localShortDesc;
	private String createdBy;
	private Date creationDate;
	private String modifiedBy;
	private Date modifiedDate;
	
	private List<ReceiptPayment> receiptPayment = new ArrayList<ReceiptPayment>();

	public PurposeOfTransaction() {
	}

	public PurposeOfTransaction(BigDecimal purposeId) {
		this.purposeId = purposeId;
	}

	

	/**
	 * @param purposeId
	 * @param fsCountryMaster
	 * @param fsCompanyMaster
	 * @param purposeFullDesc
	 * @param purposeShortDesc
	 * @param localFullDesc
	 * @param localShortDesc
	 * @param createdBy
	 * @param creationDate
	 * @param modifiedBy
	 * @param modifiedDate
	 * @param receiptPayment
	 */
	public PurposeOfTransaction(BigDecimal purposeId,
			CountryMaster fsCountryMaster, CompanyMaster fsCompanyMaster,
			String purposeFullDesc, String purposeShortDesc,
			String localFullDesc, String localShortDesc, String createdBy,
			Date creationDate, String modifiedBy, Date modifiedDate,
			List<ReceiptPayment> receiptPayment) {

		this.purposeId = purposeId;
		this.fsCountryMaster = fsCountryMaster;
		this.fsCompanyMaster = fsCompanyMaster;
		this.purposeFullDesc = purposeFullDesc;
		this.purposeShortDesc = purposeShortDesc;
		this.localFullDesc = localFullDesc;
		this.localShortDesc = localShortDesc;
		this.createdBy = createdBy;
		this.creationDate = creationDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.receiptPayment = receiptPayment;
	}

	@Id
	@GeneratedValue(generator="ex_purpose_of_transaction_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_purpose_of_transaction_seq",sequenceName="EX_PURPOSE_OF_TRANSACTION_SEQ",allocationSize=1)
	@Column(name = "PURPOSE_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getPurposeId() {
		return this.purposeId;
	}
	public void setPurposeId(BigDecimal purposeId) {
		this.purposeId = purposeId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_ID")
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
	
	@Column(name = "PURPOSE_FULL_DESC")
	public String getPurposeFullDesc() {
		return purposeFullDesc;
	}
	public void setPurposeFullDesc(String purposeFullDesc) {
		this.purposeFullDesc = purposeFullDesc;
	}
	
	@Column(name = "PURPOSE_SHORT_DESC")
	public String getPurposeShortDesc() {
		return purposeShortDesc;
	}
	public void setPurposeShortDesc(String purposeShortDesc) {
		this.purposeShortDesc = purposeShortDesc;
	}
	
	@Column(name = "LOCAL_FULL_DESC")
	public String getLocalFullDesc() {
		return localFullDesc;
	}
	public void setLocalFullDesc(String localFullDesc) {
		this.localFullDesc = localFullDesc;
	}

	@Column(name = "LOCAL_SHORT_DESC")
	public String getLocalShortDesc() {
		return localShortDesc;
	}
	public void setLocalShortDesc(String localShortDesc) {
		this.localShortDesc = localShortDesc;
	}
	
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
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "purposeOfTransaction")
	public List<ReceiptPayment> getReceiptPayment() {
		return receiptPayment;
	}
	
	public void setReceiptPayment(List<ReceiptPayment> receiptPayment) {
		this.receiptPayment = receiptPayment;
	}
	
}
