package com.amg.exchange.registration.model;

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

import com.amg.exchange.common.model.LanguageType;

	/*******************************************************************************************************************

	 File		: PartnerAuthorized.java

	 Project	: AlmullaExchange

	 Package	: com.amg.exchange.registration.model

	 Created	:	
					Date	: 11-Nov-2014 
	 				By		: Nazish Ehsan Hashmi
					Revision:

	 Last Change:
					Date	: 11-Nov-2014 
	 				By		: Nazish Ehsan Hashmi
	 				Revision:

	 Description:
********************************************************************************************************************/
	@Entity
	@Table(name = "FS_PARTNER_AUTHORIZED" )
	public class PartnerAuthorized implements java.io.Serializable {

		private static final long serialVersionUID = 1L;
		
		private BigDecimal partnerAuthorizedId;
		private Customer fsCustomer;
		private LanguageType fsLanguageType;
		//private DocumentImg fsDocumentImg;
		private BigDecimal partnerPercentage;
		private Date validUpto;
		private Date effectiveDate;
		private String createdBy;
		private Date createdDate;
		private String updatedBy;
		private Date updatedDate;
		private Customer fsCustomerByRefCustomerId;
		private String status;
		private String transactionBehalf;

		
		public PartnerAuthorized() {
		
		}

		public PartnerAuthorized(BigDecimal partnerAuthorizedId,
				Customer fsCustomer, LanguageType fsLanguageType, BigDecimal partnerPercentage,
				Date validUpto, Date effectiveDate, String createdBy,
				Date createdDate, String updatedBy, Date updatedDate,
				Customer fsCustomerByRefCustomerId, String status,
				String transactionBehalf) {
			super();
			this.partnerAuthorizedId = partnerAuthorizedId;
			this.fsCustomer = fsCustomer;
			this.fsLanguageType = fsLanguageType;
			//this.fsDocumentImg = fsDocumentImg;
			this.partnerPercentage = partnerPercentage;
			this.validUpto = validUpto;
			this.effectiveDate = effectiveDate;
			this.createdBy = createdBy;
			this.createdDate = createdDate;
			this.updatedBy = updatedBy;
			this.updatedDate = updatedDate;
			this.fsCustomerByRefCustomerId = fsCustomerByRefCustomerId;
			this.status = status;
			this.transactionBehalf = transactionBehalf;
		}

		@Id
		//@TableGenerator(name="partnerAuthorizedId",table="fs_partnerauthorizedpk",pkColumnName="partnerauthorizedkey",pkColumnValue="partnerauthorizedvalue",allocationSize=1)
		//@GeneratedValue(strategy=GenerationType.TABLE,generator="partnerAuthorizedId")
		
		@GeneratedValue(generator = "fs_partner_authorized_seq", strategy = GenerationType.SEQUENCE)
		@SequenceGenerator(name = "fs_partner_authorized_seq", sequenceName = "FS_PARTNER_AUTHORIZED_SEQ", allocationSize = 1)
		@Column(name = "CORPORATE_PARTNER_ID", unique = true, nullable = false, precision = 22, scale = 0)
		public BigDecimal getPartnerAuthorizedId() {
			return partnerAuthorizedId;
		}

		public void setPartnerAuthorizedId(BigDecimal partnerAuthorizedId) {
			this.partnerAuthorizedId = partnerAuthorizedId;
		}

		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "CUSTOMER_ID")
		public Customer getFsCustomer() {
			return fsCustomer;
		}

		public void setFsCustomer(Customer fsCustomer) {
			this.fsCustomer = fsCustomer;
		}

		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "LANGUAGE_ID")
		public LanguageType getFsLanguageType() {
			return fsLanguageType;
		}

		public void setFsLanguageType(LanguageType fsLanguageType) {
			this.fsLanguageType = fsLanguageType;
		}

	/*	@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "IMG_ID")
		public DocumentImg getFsDocumentImg() {
			return fsDocumentImg;
		}

		public void setFsDocumentImg(DocumentImg fsDocumentImg) {
			this.fsDocumentImg = fsDocumentImg;
		}*/
		@Column(name = "PARTNER_PERCENTAGE", length = 25)
		public BigDecimal getPartnerPercentage() {
			return partnerPercentage;
		}

		public void setPartnerPercentage(BigDecimal partnerPercentage) {
			this.partnerPercentage = partnerPercentage;
		}
		@Column(name = "VALID_UPTO")
		public Date getValidUpto() {
			return validUpto;
		}

		public void setValidUpto(Date validUpto) {
			this.validUpto = validUpto;
		}

		@Column(name = "EFFECTIVE_DATE")
		public Date getEffectiveDate() {
			return effectiveDate;
		}

		public void setEffectiveDate(Date effectiveDate) {
			this.effectiveDate = effectiveDate;
		}

		@Column(name = "CREATED_BY", length = 30)
		public String getCreatedBy() {
			return createdBy;
		}

		public void setCreatedBy(String createdBy) {
			this.createdBy = createdBy;
		}

		@Column(name = "CREATED_DATE")
		public Date getCreatedDate() {
			return createdDate;
		}

		public void setCreatedDate(Date createdDate) {
			this.createdDate = createdDate;
		}

		@Column(name = "UPDATED_BY", length = 30)
		public String getUpdatedBy() {
			return updatedBy;
		}

		public void setUpdatedBy(String updatedBy) {
			this.updatedBy = updatedBy;
		}

		@Column(name = "UPDATED_DATE")
		public Date getUpdatedDate() {
			return updatedDate;
		}

		public void setUpdatedDate(Date updatedDate) {
			this.updatedDate = updatedDate;
		}

		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "REF_CUSTOMER_ID")
		public Customer getFsCustomerByRefCustomerId() {
			return fsCustomerByRefCustomerId;
		}

		
		public void setFsCustomerByRefCustomerId(Customer fsCustomerByRefCustomerId) {
			this.fsCustomerByRefCustomerId = fsCustomerByRefCustomerId;
		}

		@Column(name = "ISACTIVE", length = 1)
		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		@Column(name = "TRANSACTION_BEHALF", length = 1)
		public String getTransactionBehalf() {
			return transactionBehalf;
		}

		public void setTransactionBehalf(String transactionBehalf) {
			this.transactionBehalf = transactionBehalf;
		}
		
		
		
		
}
