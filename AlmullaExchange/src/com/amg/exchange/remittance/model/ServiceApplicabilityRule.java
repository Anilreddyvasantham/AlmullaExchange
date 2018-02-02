package com.amg.exchange.remittance.model;

import java.io.Serializable;
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
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.DeliveryMode;
import com.amg.exchange.treasury.model.RemittanceModeMaster;

/*******************************************************************************************************************

File		: ServiceApplicabilityRule.java

Project	: AlmullaExchange

Package	: com.amg.exchange.remittance.model

Created	:	
				Date	: 29-Dec-2014 5:03:37 pm
				By		: Nazish Ehsan Hashmi
				Revision:

 Last Change:
				Date	: 29-Dec-2014 
				By		: Nazish Ehsan Hashmi
				Revision:

Description: TODO 

********************************************************************************************************************/
@Entity
@Table(name="EX_SERVICE_APPLICABILITY_RULE")
public class ServiceApplicabilityRule implements Serializable{
	
	 private static final long serialVersionUID = 1L;
		

		private BigDecimal serviceApplicabilityRuleId;
		private CountryMaster applicationCountryId;	
		private CurrencyMaster currencyId;
		private RemittanceModeMaster remittanceModeId;
		private DeliveryMode deliveryModeId;
		private String fieldName;
		private String fieldDesc;
		private String fieldType;
		private String navicable;
		private String mandatory;
		private BigDecimal minLenght;
		private BigDecimal maxLenght;
		private String validate;
		private String createdBy;
		private Date createdDate;
		private String modifiedBy;
		private Date modifiedDate;
		private String isActive;
		private BigDecimal fieldLength;
		private String approvedBy;
		private Date approvedDate;
		private CountryMaster countryId;
		private BigDecimal languageId;
		private Set<Remittance> exRemittance = new HashSet<Remittance>(0);
		
		public ServiceApplicabilityRule() {
			
			
			
		}
		
		public ServiceApplicabilityRule(BigDecimal serviceApplicabilityRuleId,
				CountryMaster applicationCountryId, CurrencyMaster currencyId,
				RemittanceModeMaster remittanceModeId,
				DeliveryMode deliveryModeId, String fieldName,
				String fieldDesc, String fieldType, String navicable,
				String mandatory, BigDecimal minLenght, BigDecimal maxLenght,
				String validate, String createdBy, Date createdDate,
				String modifiedBy, Date modifiedDate, String isActive,BigDecimal fieldLength,
				Set<Remittance> exRemittance,String approvedBy,Date approvedDate,CountryMaster countryId,BigDecimal languageId) {
			super();
			this.serviceApplicabilityRuleId = serviceApplicabilityRuleId;
			this.applicationCountryId = applicationCountryId;
			this.currencyId = currencyId;
			this.remittanceModeId = remittanceModeId;
			this.deliveryModeId = deliveryModeId;
			this.fieldName = fieldName;
			this.fieldDesc = fieldDesc;
			this.fieldType = fieldType;
			this.navicable = navicable;
			this.mandatory = mandatory;
			this.minLenght = minLenght;
			this.maxLenght = maxLenght;
			this.validate = validate;
			this.createdBy = createdBy;
			this.createdDate = createdDate;
			this.modifiedBy = modifiedBy;
			this.modifiedDate = modifiedDate;
			this.isActive = isActive;
			this.fieldLength = fieldLength;
			this.exRemittance = exRemittance;
			this.approvedBy = approvedBy;
			this.approvedDate = approvedDate;
			this.countryId = countryId;
			this.languageId= languageId;
		}

        @Id
		@GeneratedValue(generator="ex_service_applty_rule_seq",strategy=GenerationType.SEQUENCE)
		@SequenceGenerator(name="ex_service_applty_rule_seq",sequenceName="EX_SERVICE_APPLTY_RULE_SEQ",allocationSize=1)
		@Column(name="SERVICE_APPLICABILITY_RULE_ID", unique=true, nullable=false, precision=22, scale=0)
		public BigDecimal getServiceApplicabilityRuleId() {
			return serviceApplicabilityRuleId;
		}

		public void setServiceApplicabilityRuleId(BigDecimal serviceApplicabilityRuleId) {
			this.serviceApplicabilityRuleId = serviceApplicabilityRuleId;
		}

		@ManyToOne(fetch=FetchType.LAZY)
		@JoinColumn(name="APPLICATION_COUNTRY_ID")
		public CountryMaster getApplicationCountryId() {
			return applicationCountryId;
		}

		public void setApplicationCountryId(CountryMaster applicationCountryId) {
			this.applicationCountryId = applicationCountryId;
		}

		@ManyToOne(fetch=FetchType.LAZY)
		@JoinColumn(name="CURRENCY_ID")
		public CurrencyMaster getCurrencyId() {
			return currencyId;
		}

		public void setCurrencyId(CurrencyMaster currencyId) {
			this.currencyId = currencyId;
		}

		@ManyToOne(fetch=FetchType.LAZY)
		@JoinColumn(name="REMITTANCE_MODE_ID")
		public RemittanceModeMaster getRemittanceModeId() {
			return remittanceModeId;
		}

		public void setRemittanceModeId(RemittanceModeMaster remittanceModeId) {
			this.remittanceModeId = remittanceModeId;
		}

		@ManyToOne(fetch=FetchType.LAZY)
		@JoinColumn(name="DELIVERY_MODE_ID")
		public DeliveryMode getDeliveryModeId() {
			return deliveryModeId;
		}

		public void setDeliveryModeId(DeliveryMode deliveryModeId) {
			this.deliveryModeId = deliveryModeId;
		}

		@Column(name="FIELD_NAME")
		public String getFieldName() {
			return fieldName;
		}

		public void setFieldName(String fieldName) {
			this.fieldName = fieldName;
		}

		@Column(name="FIELD_DESC")
		public String getFieldDesc() {
			return fieldDesc;
		}

		public void setFieldDesc(String fieldDesc) {
			this.fieldDesc = fieldDesc;
		}

		@Column(name="FIELD_TYPE")
		public String getFieldType() {
			return fieldType;
		}

		public void setFieldType(String fieldType) {
			this.fieldType = fieldType;
		}

		@Column(name="NAVICABLE")
		public String getNavicable() {
			return navicable;
		}

		public void setNavicable(String navicable) {
			this.navicable = navicable;
		}

		@Column(name="MANDATORY")
		public String getMandatory() {
			return mandatory;
		}

		public void setMandatory(String mandatory) {
			this.mandatory = mandatory;
		}

		@Column(name="MIN_LENGTH")
		public BigDecimal getMinLenght() {
			return minLenght;
		}

		public void setMinLenght(BigDecimal minLenght) {
			this.minLenght = minLenght;
		}

		@Column(name="MAX_LENGTH")
		public BigDecimal getMaxLenght() {
			return maxLenght;
		}

		public void setMaxLenght(BigDecimal maxLenght) {
			this.maxLenght = maxLenght;
		}

		@Column(name="VALIDATIONS")
		public String getValidate() {
			return validate;
		}

		public void setValidate(String validate) {
			this.validate = validate;
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

		@Column(name="FIELD_LENGTH")
		public BigDecimal getFieldLength() {
			return fieldLength;
		}

		public void setFieldLength(BigDecimal fieldLength) {
			this.fieldLength = fieldLength;
		}

		@OneToMany(fetch = FetchType.LAZY, mappedBy = "exServiceApplicabilityRule")
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

		@ManyToOne(fetch=FetchType.LAZY)
		@JoinColumn(name="COUNTRY_ID")
		public CountryMaster getCountryId() {
			return countryId;
		}

		public void setCountryId(CountryMaster countryId) {
			this.countryId = countryId;
		}

		@Column(name="LANGUAGE_ID")
	  public BigDecimal getLanguageId() {
	  	  return languageId;
	  }

	  public void setLanguageId(BigDecimal languageId) {
	  	  this.languageId = languageId;
	  }
		
		

}

