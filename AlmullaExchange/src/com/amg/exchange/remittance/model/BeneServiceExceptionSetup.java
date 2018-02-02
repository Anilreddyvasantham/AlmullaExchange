/**
 * 
 */
package com.amg.exchange.remittance.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.DeliveryMode;
import com.amg.exchange.treasury.model.RemittanceModeMaster;

/**
 * @author Subramaniam
 *
 */
@Entity
@Table(name="EX_BENE_SERVICE_EXCEP_SETUP")
public class BeneServiceExceptionSetup implements Serializable {

	private static final long serialVersionUID = 2315791709068216697L;
	/**
	 * 
	 */
	
	private BigDecimal beneServiceExcepSetup;
	private CountryMaster appCountryId;
	private CountryMaster countryId;
	private String countryCode;
	private BankMaster bankId;
	private String bankCode;
	private BankBranch bankBranchId;
	private String bankBranchCode;
	private RemittanceModeMaster remittanceModeId;
	private String remittanceCode;
	private DeliveryMode deliveryId;
	private String deliveryModeCode;
	private String recordStatus;
	private String isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private CurrencyMaster currency;
	private String currencyCode;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;
	private String branchCodeNo;
	
	public BeneServiceExceptionSetup() {
		// TODO Auto-generated constructor stub
	}
	
	
	public BeneServiceExceptionSetup(BigDecimal beneServiceExcepSetup,CountryMaster appCountryId,
			CountryMaster countryId, String countryCode, BankMaster bankId,
			String bankCode, BankBranch bankBranchId, String bankBranchCode,
			RemittanceModeMaster remittanceModeId, String remittanceCode,
			DeliveryMode deliveryId, String deliveryModeCode,
			String recordStatus, String isActive, String createdBy,
			Date createdDate, String modifiedBy, Date modifiedDate,CurrencyMaster currency,String currencyCode,String approvedBy,Date approvedDate,String remarks,String branchCodeNo) {
		this.beneServiceExcepSetup = beneServiceExcepSetup;
		this.countryId = countryId;
		this.countryCode = countryCode;
		this.bankId = bankId;
		this.bankCode = bankCode;
		this.bankBranchId = bankBranchId;
		this.bankBranchCode = bankBranchCode;
		this.remittanceModeId = remittanceModeId;
		this.remittanceCode = remittanceCode;
		this.deliveryId = deliveryId;
		this.deliveryModeCode = deliveryModeCode;
		this.recordStatus = recordStatus;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.currency = currency;
		this.currencyCode =currencyCode;
		this.appCountryId=appCountryId;
		this.approvedBy= approvedBy;
		this.approvedDate =approvedDate;
		this.remarks=remarks;
		this.branchCodeNo=branchCodeNo;
		
	}

	@Id
	@GeneratedValue(generator="ex_bene_ser_excep_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_bene_ser_excep_seq",sequenceName="EX_BENE_SER_EXCEP_SETUP_SEQ",allocationSize=1)
	@Column(name="BENE_SERVICE_EXCEP_SETUP_ID",unique=true, nullable=false, precision=22, scale=0)
	public BigDecimal getBeneServiceExcepSetup() {
		return beneServiceExcepSetup;
	}
	public void setBeneServiceExcepSetup(BigDecimal beneServiceExcepSetup) {
		this.beneServiceExcepSetup = beneServiceExcepSetup;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="APPLICATION_COUNTRY_ID")
	public CountryMaster getAppCountryId() {
		return appCountryId;
	}
	public void setAppCountryId(CountryMaster appCountryId) {
		this.appCountryId = appCountryId;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="COUNTRY_ID")
	public CountryMaster getCountryId() {
		return countryId;
	}
	public void setCountryId(CountryMaster countryId) {
		this.countryId = countryId;
	}
	@Column(name="COUNTRY_CODE")
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="BANK_ID")
	public BankMaster getBankId() {
		return bankId;
	}
	public void setBankId(BankMaster bankId) {
		this.bankId = bankId;
	}
	@Column(name="BANK_CODE")
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="BANK_BRANCH_ID")
	public BankBranch getBankBranchId() {
		return bankBranchId;
	}
	public void setBankBranchId(BankBranch bankBranchId) {
		this.bankBranchId = bankBranchId;
	}
	//@Formula(value="to_number(BANK_BRANCH_CODE)")
	@Column(name="BANK_BRANCH_CODE")	
	public String getBankBranchCode() {
		return bankBranchCode;
	}
	public void setBankBranchCode(String bankBranchCode) {
		this.bankBranchCode = bankBranchCode;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="REMITTANCE_MODE_ID")
	public RemittanceModeMaster getRemittanceModeId() {
		return remittanceModeId;
	}
	public void setRemittanceModeId(RemittanceModeMaster remittanceModeId) {
		this.remittanceModeId = remittanceModeId;
	}
	@Column(name="REMITTANCE_CODE")
	public String getRemittanceCode() {
		return remittanceCode;
	}
	public void setRemittanceCode(String remittanceCode) {
		this.remittanceCode = remittanceCode;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="DELIVERY_MODE_ID")
	public DeliveryMode getDeliveryId() {
		return deliveryId;
	}
	public void setDeliveryId(DeliveryMode deliveryId) {
		this.deliveryId = deliveryId;
	}
	@Column(name="DELIVERY_MODE_CODE")
	public String getDeliveryModeCode() {
		return deliveryModeCode;
	}
	public void setDeliveryModeCode(String deliveryModeCode) {
		this.deliveryModeCode = deliveryModeCode;
	}
	@Column(name="RECORD_STATUS")
	public String getRecordStatus() {
		return recordStatus;
	}
	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
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
	@Column(name="CREATION_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	@Column(name="UPDATED_BY")
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	@Column(name="UPDATED_DATE")
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CURRENCY_ID")
	public CurrencyMaster getCurrency() {
		return currency;
	}


	public void setCurrency(CurrencyMaster currency) {
		this.currency = currency;
	}

	@Column(name="CURRENCY_CODE")
	public String getCurrencyCode() {
		return currencyCode;
	}


	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
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

	@Formula(value="to_number(BANK_BRANCH_CODE)")
	public String getBranchCodeNo() {
		return branchCodeNo;
	}


	public void setBranchCodeNo(String branchCodeNo) {
		this.branchCodeNo = branchCodeNo;
	}
	
	

}
