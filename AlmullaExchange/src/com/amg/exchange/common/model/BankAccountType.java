package com.amg.exchange.common.model;

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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.amg.exchange.treasury.model.BankAccountDetails;

/*
 *  Author Rahamathali Shaik
*/

@Entity
@Table(name="EX_BANK_ACCOUNT_TYPE")
public class BankAccountType {
	
	private BigDecimal bankAccountTypeId;
	private String bankAccountTypeCode;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String isActive;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;
	private List<BankAccountDetails> BankAccountDetails=new ArrayList<BankAccountDetails>();
	private List<BankAccountTypeDesc> bankAccountTypeDescList=new ArrayList<BankAccountTypeDesc>();
	
	
	
		public BankAccountType() {
		}
		public BankAccountType(BigDecimal bankAccountTypeId) {
			this.bankAccountTypeId = bankAccountTypeId;
		}
		
		
		
		public BankAccountType(
				BigDecimal bankAccountTypeId,
				String bankAccountTypeCode,
				String createdBy,
				Date createdDate,
				String modifiedBy,
				Date modifiedDate,
				String isActive,
				String approvedBy,
				Date approvedDate,
				String remarks,
				List<com.amg.exchange.treasury.model.BankAccountDetails> bankAccountDetails,
				List<BankAccountTypeDesc> bankAccountTypeDescList) {
			super();
			this.bankAccountTypeId = bankAccountTypeId;
			this.bankAccountTypeCode = bankAccountTypeCode;
			this.createdBy = createdBy;
			this.createdDate = createdDate;
			this.modifiedBy = modifiedBy;
			this.modifiedDate = modifiedDate;
			this.isActive = isActive;
			this.approvedBy = approvedBy;
			this.approvedDate = approvedDate;
			this.remarks = remarks;
			BankAccountDetails = bankAccountDetails;
			this.bankAccountTypeDescList = bankAccountTypeDescList;
		}
		@Id
		@GeneratedValue(generator="ex_bank_account_type_seq",strategy=GenerationType.SEQUENCE)
		@SequenceGenerator(name="ex_bank_account_type_seq",sequenceName="EX_BANK_ACCOUNT_TYPE_SEQ",allocationSize=1)
		@Column(name ="BANK_ACCOUNT_TYPE_ID" , unique=true, nullable=false, precision=22, scale=0)
		public BigDecimal getBankAccountTypeId() {
			return bankAccountTypeId;
		}
		public void setBankAccountTypeId(BigDecimal bankAccountTypeId) {
			this.bankAccountTypeId = bankAccountTypeId;
		}

		@Column(name="BANK_ACCOUNT_TYPE_CODE")
		public String getBankAccountTypeCode() {
			return bankAccountTypeCode;
		}
		public void setBankAccountTypeCode(String bankAccountTypeCode) {
			this.bankAccountTypeCode = bankAccountTypeCode;
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
		@Column(name="ISACTIVE")
		public String getIsActive() {
			return isActive;
		}
		public void setIsActive(String isActive) {
			this.isActive = isActive;
		}
		
		@OneToMany(fetch = FetchType.LAZY, mappedBy = "bankAccountType")
		public List<BankAccountDetails> getBankAccountDetails() {
			return BankAccountDetails;
		}
		public void setBankAccountDetails(List<BankAccountDetails> bankAccountDetails) {
			BankAccountDetails = bankAccountDetails;
		}
		
		@OneToMany(fetch = FetchType.LAZY, mappedBy = "bankAccountTypeId")
		public List<BankAccountTypeDesc> getBankAccountTypeDescList() {
			return bankAccountTypeDescList;
		}
		public void setBankAccountTypeDescList(
				List<BankAccountTypeDesc> bankAccountTypeDescList) {
			this.bankAccountTypeDescList = bankAccountTypeDescList;
		}
	
		
		
	
	
	

}
