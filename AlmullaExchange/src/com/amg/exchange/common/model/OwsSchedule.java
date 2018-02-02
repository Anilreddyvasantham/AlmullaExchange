/**
 * 
 */
package com.amg.exchange.common.model;

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

import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.treasury.model.BankMaster;

/**
 * @author Subramaniam
 *
 */
@Entity
@Table(name = "EX_OWS_SCHEDULE")
public class OwsSchedule implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private BigDecimal owsScheduleId;
	private CountryMaster applicationCountry;
	private CountryMaster bankCountry;
	private BankMaster bank;
	private BigDecimal repeatInterval;
	private String liveIndicator;
	private String neftIndicator;
	private String rtgsIndicator;
	private String xmlCreation;
	private BigDecimal neftStartTime;
	private BigDecimal neftEndTime;
	private BigDecimal rtgsStartTime;
	private BigDecimal rtgsEndTime;
	private BigDecimal weekendStartTime;
	private BigDecimal weekendEndTime;
	private Date lastRunDate;
	private String owsReportFlag;
	private String isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	
	 
	public OwsSchedule() {
	
	}
	
	public OwsSchedule(BigDecimal owsScheduleId, CountryMaster applicationCountry, CountryMaster bankCountry, BankMaster bank, BigDecimal repeatInterval, String liveIndicator, String neftIndicator, String rtgsIndicator, String xmlCreation, BigDecimal neftStartTime, BigDecimal neftEndTime,
			BigDecimal rtgsStartTime, BigDecimal rtgsEndTime, BigDecimal weekendStartTime, BigDecimal weekendEndTime, Date lastRunDate, String owsReportFlag, String isActive, String createdBy, Date createdDate, String modifiedBy, Date modifiedDate, String approvedBy, Date approvedDate) {
		super();
		this.owsScheduleId = owsScheduleId;
		this.applicationCountry = applicationCountry;
		this.bankCountry = bankCountry;
		this.bank = bank;
		this.repeatInterval = repeatInterval;
		this.liveIndicator = liveIndicator;
		this.neftIndicator = neftIndicator;
		this.rtgsIndicator = rtgsIndicator;
		this.xmlCreation = xmlCreation;
		this.neftStartTime = neftStartTime;
		this.neftEndTime = neftEndTime;
		this.rtgsStartTime = rtgsStartTime;
		this.rtgsEndTime = rtgsEndTime;
		this.weekendStartTime = weekendStartTime;
		this.weekendEndTime = weekendEndTime;
		this.lastRunDate = lastRunDate;
		this.owsReportFlag = owsReportFlag;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
	}

	@Id
	@GeneratedValue(generator="ex_ows_schedule_seq", strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_ows_schedule_seq",sequenceName="EX_OWS_SCHEDULE_SEQ",allocationSize=1)
	@Column(name = "OWS_SCHEDULE_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getOwsScheduleId() {
		return owsScheduleId;
	}

	public void setOwsScheduleId(BigDecimal owsScheduleId) {
		this.owsScheduleId = owsScheduleId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "APPLICATION_COUNTRY_ID")	
	public CountryMaster getApplicationCountry() {
		return applicationCountry;
	}

	public void setApplicationCountry(CountryMaster applicationCountry) {
		this.applicationCountry = applicationCountry;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_COUNTRY_ID")	
	public CountryMaster getBankCountry() {
		return bankCountry;
	}

	public void setBankCountry(CountryMaster bankCountry) {
		this.bankCountry = bankCountry;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_ID")
	public BankMaster getBank() {
		return bank;
	}

	public void setBank(BankMaster bank) {
		this.bank = bank;
	}

	@Column(name="REPEAT_INTERVAL")
	public BigDecimal getRepeatInterval() {
		return repeatInterval;
	}

	public void setRepeatInterval(BigDecimal repeatInterval) {
		this.repeatInterval = repeatInterval;
	}

	@Column(name="LIVE_INDICATOR")
	public String getLiveIndicator() {
		return liveIndicator;
	}

	public void setLiveIndicator(String liveIndicator) {
		this.liveIndicator = liveIndicator;
	}

	@Column(name="NEFT_INDICATOR")
	public String getNeftIndicator() {
		return neftIndicator;
	}

	public void setNeftIndicator(String neftIndicator) {
		this.neftIndicator = neftIndicator;
	}

	@Column(name="RTGS_INDICATOR")
	public String getRtgsIndicator() {
		return rtgsIndicator;
	}

	public void setRtgsIndicator(String rtgsIndicator) {
		this.rtgsIndicator = rtgsIndicator;
	}

	@Column(name="XML_CREATION")
	public String getXmlCreation() {
		return xmlCreation;
	}

	
	public void setXmlCreation(String xmlCreation) {
		this.xmlCreation = xmlCreation;
	}
	
	@Column(name="NEFT_START_TIME")
	public BigDecimal getNeftStartTime() {
		return neftStartTime;
	}

	public void setNeftStartTime(BigDecimal neftStartTime) {
		this.neftStartTime = neftStartTime;
	}

	@Column(name="NEFT_END_TIME")
	public BigDecimal getNeftEndTime() {
		return neftEndTime;
	}

	public void setNeftEndTime(BigDecimal neftEndTime) {
		this.neftEndTime = neftEndTime;
	}

	@Column(name="RTGS_START_TIME")
	public BigDecimal getRtgsStartTime() {
		return rtgsStartTime;
	}

	public void setRtgsStartTime(BigDecimal rtgsStartTime) {
		this.rtgsStartTime = rtgsStartTime;
	}

	@Column(name="RTGS_END_TIME")
	public BigDecimal getRtgsEndTime() {
		return rtgsEndTime;
	}

	public void setRtgsEndTime(BigDecimal rtgsEndTime) {
		this.rtgsEndTime = rtgsEndTime;
	}

	@Column(name="WKEND_START_TIME")
	public BigDecimal getWeekendStartTime() {
		return weekendStartTime;
	}

	public void setWeekendStartTime(BigDecimal weekendStartTime) {
		this.weekendStartTime = weekendStartTime;
	}

	@Column(name="WKEND_END_TIME")
	public BigDecimal getWeekendEndTime() {
		return weekendEndTime;
	}

	public void setWeekendEndTime(BigDecimal weekendEndTime) {
		this.weekendEndTime = weekendEndTime;
	}

	@Column(name="LAST_RUN_TIME")
	public Date getLastRunDate() {
		return lastRunDate;
	}

	public void setLastRunDate(Date lastRunDate) {
		this.lastRunDate = lastRunDate;
	}

	@Column(name="OWS_REPORT_FLAG")
	public String getOwsReportFlag() {
		return owsReportFlag;
	}

	public void setOwsReportFlag(String owsReportFlag) {
		this.owsReportFlag = owsReportFlag;
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




/*


	@Id
	@GeneratedValue(generator="ex_bank_external_ref_head_seq", strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_bank_external_ref_head_seq",sequenceName="EX_BANK_EXTERNAL_REF_HEAD_SEQ",allocationSize=1)
	@Column(name = "BANK_EXT_REF_HEAD_SEQ_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getBankExtRefSeqId() {
		return bankExtRefSeqId;
	}

	public void setBankExtRefSeqId(BigDecimal bankExtRefSeqId) {
		this.bankExtRefSeqId = bankExtRefSeqId;
	}



	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "APPLICATION_COUNTRY")	
	public CountryMaster getApplicationCountry() {
		return applicationCountry;
	}
	public void setApplicationCountry(CountryMaster applicationCountry) {
		this.applicationCountry = applicationCountry;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_ID")
	public BankMaster getBank() {
		return bank;
	}
	public void setBank(BankMaster bank) {
		this.bank = bank;
	}
	@Column(name="BANK_CODE")
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
	@Column(name="BANK_EXTERNAL_ID")
	public String getBankExternalId() {
		return bankExternalId;
	}

	public void setBankExternalId(String bankExternalId) {
		this.bankExternalId = bankExternalId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BENEFICARY_BANK")
	public BankMaster getBeneficaryBank() {
		return beneficaryBank;
	}
	
	public void setBeneficaryBank(BankMaster beneficaryBank) {
		this.beneficaryBank = beneficaryBank;
	}
	@Column(name="BENEFICARY_BANK_CODE")
	public String getBeneficaryBankCode() {
		return beneficaryBankCode;
	}

	public void setBeneficaryBankCode(String beneficaryBankCode) {
		this.beneficaryBankCode = beneficaryBankCode;
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_ID")	
	public CountryMaster getBankCountry() {
		return bankCountry;
	}


	public void setBankCountry(CountryMaster bankCountry) {
		this.bankCountry = bankCountry;
	}

	@Column(name="REMARKS")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


*/

}
