/*package com.amg.exchange.remittance.model;

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
@Table(name="EX_BENEFICARY_MASTER_LOG")
public class BeneficaryMasterLog implements Serializable {

	*//**
	 * 
	 *//*
	private static final long serialVersionUID = 1L;
	
	private BigDecimal applicationCountryId;
	private String bankAccountNumber;
	private BigDecimal beneficaryMasterLogId;
	private BigDecimal beneficaryMasterSeqId;
	private BigDecimal countryBranchId;
	private String createdBy;
	private Date createdDate;
	private BigDecimal customerId;
	private String oldFirstName;
	private String oldFirstNameLocal;
	private String oldSecondName;
	private String oldSecondNameLocal;
	private String oldThirdName;
	private String oldThirdNameLocal;
	private String oldFourthName;
	private String oldFourthNameLocal;
	private String oldFifthName;
	private String newFirstName;
	private String newFirstNameLocal;
	private String newSecondName;
	private String newSecondNameLocal;
	private String newThirdName;
	private String newThirdNameLocal;
	private String newFourthName;
	private String newFourthNameLocal;
	private String newFifthName;
	
	public BeneficaryMasterLog() {
		super();
	}

	public BeneficaryMasterLog(BigDecimal applicationCountryId,
			String bankAccountNumber, BigDecimal beneficaryMasterLogId,
			BigDecimal beneficaryMasterSeqId, BigDecimal countryBranchId,
			String createdBy, Date createdDate, BigDecimal customerId,
			String oldFirstName, String oldFirstNameLocal,
			String oldSecondName, String oldSecondNameLocal,
			String oldThirdName, String oldThirdNameLocal,
			String oldFourthName, String oldFourthNameLocal,
			String oldFifthName, String newFirstName, String newFirstNameLocal,
			String newSecondName, String newSecondNameLocal,
			String newThirdName, String newThirdNameLocal,
			String newFourthName, String newFourthNameLocal, String newFifthName) {
		super();
		this.applicationCountryId = applicationCountryId;
		this.bankAccountNumber = bankAccountNumber;
		this.beneficaryMasterLogId = beneficaryMasterLogId;
		this.beneficaryMasterSeqId = beneficaryMasterSeqId;
		this.countryBranchId = countryBranchId;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.customerId = customerId;
		this.oldFirstName = oldFirstName;
		this.oldFirstNameLocal = oldFirstNameLocal;
		this.oldSecondName = oldSecondName;
		this.oldSecondNameLocal = oldSecondNameLocal;
		this.oldThirdName = oldThirdName;
		this.oldThirdNameLocal = oldThirdNameLocal;
		this.oldFourthName = oldFourthName;
		this.oldFourthNameLocal = oldFourthNameLocal;
		this.oldFifthName = oldFifthName;
		this.newFirstName = newFirstName;
		this.newFirstNameLocal = newFirstNameLocal;
		this.newSecondName = newSecondName;
		this.newSecondNameLocal = newSecondNameLocal;
		this.newThirdName = newThirdName;
		this.newThirdNameLocal = newThirdNameLocal;
		this.newFourthName = newFourthName;
		this.newFourthNameLocal = newFourthNameLocal;
		this.newFifthName = newFifthName;
	}
	
	@Id
	@GeneratedValue(generator="ex_beneficary_master_log_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_beneficary_master_log_seq",sequenceName="EX_BENEFICARY_MASTER_LOG_SEQ",allocationSize=1)
	@Column(name="BENEFICARY_MASTER_LOG_ID", unique=true, nullable=false, precision=22, scale=0)
	public BigDecimal getBeneficaryMasterLogId() {
		return beneficaryMasterLogId;
	}
	public void setBeneficaryMasterLogId(BigDecimal beneficaryMasterLogId) {
		this.beneficaryMasterLogId = beneficaryMasterLogId;
	}

	@Column(name="APPLICATION_COUNTRY_ID")
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}
	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}

	@Column(name="BANK_ACCOUNT_NUMBER")
	public String getBankAccountNumber() {
		return bankAccountNumber;
	}
	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}

	@Column(name="BENEFICARY_MASTER_SEQ_ID")
	public BigDecimal getBeneficaryMasterSeqId() {
		return beneficaryMasterSeqId;
	}
	public void setBeneficaryMasterSeqId(BigDecimal beneficaryMasterSeqId) {
		this.beneficaryMasterSeqId = beneficaryMasterSeqId;
	}

	@Column(name="COUNTRY_BRANCH_ID")
	public BigDecimal getCountryBranchId() {
		return countryBranchId;
	}
	public void setCountryBranchId(BigDecimal countryBranchId) {
		this.countryBranchId = countryBranchId;
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

	@Column(name="CUSTOMER_ID")
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	@Column(name="OLD_FIRST_NAME")
	public String getOldFirstName() {
		return oldFirstName;
	}
	public void setOldFirstName(String oldFirstName) {
		this.oldFirstName = oldFirstName;
	}
	
	@Column(name="OLD_FIRST_NAME_LOCAL")
	public String getOldFirstNameLocal() {
		return oldFirstNameLocal;
	}
	public void setOldFirstNameLocal(String oldFirstNameLocal) {
		this.oldFirstNameLocal = oldFirstNameLocal;
	}

	@Column(name="OLD_SECOND_NAME")
	public String getOldSecondName() {
		return oldSecondName;
	}
	public void setOldSecondName(String oldSecondName) {
		this.oldSecondName = oldSecondName;
	}

	@Column(name="OLD_SECOND_NAME_LOCAL")
	public String getOldSecondNameLocal() {
		return oldSecondNameLocal;
	}
	public void setOldSecondNameLocal(String oldSecondNameLocal) {
		this.oldSecondNameLocal = oldSecondNameLocal;
	}

	@Column(name="OLD_THIRD_NAME")
	public String getOldThirdName() {
		return oldThirdName;
	}
	public void setOldThirdName(String oldThirdName) {
		this.oldThirdName = oldThirdName;
	}

	@Column(name="OLD_THIRD_NAME_LOCAL")
	public String getOldThirdNameLocal() {
		return oldThirdNameLocal;
	}
	public void setOldThirdNameLocal(String oldThirdNameLocal) {
		this.oldThirdNameLocal = oldThirdNameLocal;
	}

	@Column(name="OLD_FOURTH_NAME")
	public String getOldFourthName() {
		return oldFourthName;
	}
	public void setOldFourthName(String oldFourthName) {
		this.oldFourthName = oldFourthName;
	}

	@Column(name="OLD_FOURTH_NAME_LOCAL")
	public String getOldFourthNameLocal() {
		return oldFourthNameLocal;
	}
	public void setOldFourthNameLocal(String oldFourthNameLocal) {
		this.oldFourthNameLocal = oldFourthNameLocal;
	}

	@Column(name="OLD_FIFTH_NAME")
	public String getOldFifthName() {
		return oldFifthName;
	}
	public void setOldFifthName(String oldFifthName) {
		this.oldFifthName = oldFifthName;
	}

	@Column(name="NEW_FIRST_NAME")
	public String getNewFirstName() {
		return newFirstName;
	}
	public void setNewFirstName(String newFirstName) {
		this.newFirstName = newFirstName;
	}

	@Column(name="NEW_FIRST_NAME_LOCAL")
	public String getNewFirstNameLocal() {
		return newFirstNameLocal;
	}
	public void setNewFirstNameLocal(String newFirstNameLocal) {
		this.newFirstNameLocal = newFirstNameLocal;
	}

	@Column(name="NEW_SECOND_NAME")
	public String getNewSecondName() {
		return newSecondName;
	}
	public void setNewSecondName(String newSecondName) {
		this.newSecondName = newSecondName;
	}

	@Column(name="NEW_SECOND_NAME_LOCAL")
	public String getNewSecondNameLocal() {
		return newSecondNameLocal;
	}
	public void setNewSecondNameLocal(String newSecondNameLocal) {
		this.newSecondNameLocal = newSecondNameLocal;
	}

	@Column(name="NEW_THIRD_NAME")
	public String getNewThirdName() {
		return newThirdName;
	}
	public void setNewThirdName(String newThirdName) {
		this.newThirdName = newThirdName;
	}

	@Column(name="NEW_THIRD_NAME_LOCAL")
	public String getNewThirdNameLocal() {
		return newThirdNameLocal;
	}
	public void setNewThirdNameLocal(String newThirdNameLocal) {
		this.newThirdNameLocal = newThirdNameLocal;
	}

	@Column(name="NEW_FOURTH_NAME")
	public String getNewFourthName() {
		return newFourthName;
	}
	public void setNewFourthName(String newFourthName) {
		this.newFourthName = newFourthName;
	}

	@Column(name="NEW_FOURTH_NAME_LOCAL")
	public String getNewFourthNameLocal() {
		return newFourthNameLocal;
	}
	public void setNewFourthNameLocal(String newFourthNameLocal) {
		this.newFourthNameLocal = newFourthNameLocal;
	}

	@Column(name="NEW_FIFTH_NAME")
	public String getNewFifthName() {
		return newFifthName;
	}
	public void setNewFifthName(String newFifthName) {
		this.newFifthName = newFifthName;
	}

}
*/