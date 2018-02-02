package com.amg.exchange.registration.model;

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
@Table(name = "EX_DMS_APPL_MAP" )
public class DMSApplMap implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private BigDecimal docSeqNo;
	private BigDecimal applicationCountryId;
	private BigDecimal docFinYear;
	private BigDecimal docBlobId;
	private BigDecimal customerId;
	private BigDecimal identityTypeId;
	private String identityInt;
	private Date identityExpiryDate;
	private String createdBy;
	private Date createdDate;
	
	
	public DMSApplMap()
	{

	}
	
	public DMSApplMap(BigDecimal docSeqNo, BigDecimal applicationCountryId,
			BigDecimal docFinYear, BigDecimal docBlobId, BigDecimal customerId,
			BigDecimal identityTypeId, String identityInt,
			Date identityExpiryDate, String createdBy, Date createdDate) {
		super();
		this.docSeqNo = docSeqNo;
		this.applicationCountryId = applicationCountryId;
		this.docFinYear = docFinYear;
		this.docBlobId = docBlobId;
		this.customerId = customerId;
		this.identityTypeId = identityTypeId;
		this.identityInt = identityInt;
		this.identityExpiryDate = identityExpiryDate;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
	}

	@Id
	@GeneratedValue(generator="ex_dms_appl_map_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_dms_appl_map_seq",sequenceName="EX_DMS_APPL_MAP_SEQ",allocationSize=1)
	@Column(name = "DOC_SEQNO", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getDocSeqNo() {
		return docSeqNo;
	}


	public void setDocSeqNo(BigDecimal docSeqNo) {
		this.docSeqNo = docSeqNo;
	}

	@Column(name = "APPLICATION_COUNTRY_ID")
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}


	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}

	@Column(name = "DOC_FIN_YR")
	public BigDecimal getDocFinYear() {
		return docFinYear;
	}


	public void setDocFinYear(BigDecimal docFinYear) {
		this.docFinYear = docFinYear;
	}

	@Column(name = "DOC_BLOB_ID")
	public BigDecimal getDocBlobId() {
		return docBlobId;
	}


	public void setDocBlobId(BigDecimal docBlobId) {
		this.docBlobId = docBlobId;
	}

	@Column(name = "CUSTOMER_ID")
	public BigDecimal getCustomerId() {
		return customerId;
	}


	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	@Column(name = "IDENTITY_TYPE_ID")
	public BigDecimal getIdentityTypeId() {
		return identityTypeId;
	}


	public void setIdentityTypeId(BigDecimal identityTypeId) {
		this.identityTypeId = identityTypeId;
	}

	@Column(name = "IDENTITY_INT")
	public String getIdentityInt() {
		return identityInt;
	}


	public void setIdentityInt(String identityInt) {
		this.identityInt = identityInt;
	}

	@Column(name = "IDENTITY_EXPIRY_DATE")
	public Date getIdentityExpiryDate() {
		return identityExpiryDate;
	}


	public void setIdentityExpiryDate(Date identityExpiryDate) {
		this.identityExpiryDate = identityExpiryDate;
	}

	@Column(name = "CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "CREATION_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	
	
}
