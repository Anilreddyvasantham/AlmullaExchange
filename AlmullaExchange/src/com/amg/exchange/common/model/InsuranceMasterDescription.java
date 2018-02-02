package com.amg.exchange.common.model;

import java.io.Serializable;
import java.math.BigDecimal;

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
@Entity
@Table(name="EX_INSURANCE_MASTER_DESC")
public class InsuranceMasterDescription implements Serializable {

	 
	private static final long serialVersionUID = 1L;
	
	private BigDecimal insuranceMasterDescId;
	private LanguageType languageType;
	private InsuranceMaster insuranceMasterId;
	private String insurancePrimaryMessage;
	private String insuranceSecondaryMessage;
	
	@Id
	@GeneratedValue(generator="ex_insurance_master_desc_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_insurance_master_desc_seq",sequenceName="EX_INSURANCE_MASTER_DESC_SEQ",allocationSize=1)
	@Column(name = "INSURANCE_MASTER_DESC_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getInsuranceMasterDescId() {
		return insuranceMasterDescId;
	}
	public void setInsuranceMasterDescId(BigDecimal insuranceMasterDescId) {
		this.insuranceMasterDescId = insuranceMasterDescId;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LANGUAGE_ID")
	public LanguageType getLanguageType() {
		return languageType;
	}
	public void setLanguageType(LanguageType languageType) {
		this.languageType = languageType;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "INSURANCE_MASTER_ID")
	public InsuranceMaster getInsuranceMasterId() {
		return insuranceMasterId;
	}
	public void setInsuranceMasterId(InsuranceMaster insuranceMasterId) {
		this.insuranceMasterId = insuranceMasterId;
	}
	@Column(name="INSURANCE_PRIMARY_MESSAGE")
	public String getInsurancePrimaryMessage() {
		return insurancePrimaryMessage;
	}
	public void setInsurancePrimaryMessage(String insurancePrimaryMessage) {
		this.insurancePrimaryMessage = insurancePrimaryMessage;
	}
	@Column(name="INSURANCE_SECONDARY_MESSAGE")
	public String getInsuranceSecondaryMessage() {
		return insuranceSecondaryMessage;
	}
	public void setInsuranceSecondaryMessage(String insuranceSecondaryMessage) {
		this.insuranceSecondaryMessage = insuranceSecondaryMessage;
	}
	
	
	

}
