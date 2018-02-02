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

/*******************************************************************************************************************

		 File		: CustomerImageVerification.java
 
		 Project	: AlmullaExchange

		 Package	: com.amg.exchange.registration.model
 
		 Created	:	
 						Date	: 31-JAN-2016
		 				By		: Nazish Ehsan Hashmi
 						Revision:
 
 		 Last Change:
 						Date	: 31-JAN-2016
 						By		: Nazish Ehsan Hashmi
		 				Revision:

	

********************************************************************************************************************/

@Entity
@Table(name = "FS_CUSTOMER_IMAGE_VERIFICATION" )
public class CustomerImageVerification implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private BigDecimal customerImageVerificationIdId;
	private Customer customer;
	private String idNumber;
	private Date idExpiryDate;
	private String fileId;
	private String fileScanStatus;
	private String verifiedBy;
	private Date verifiedDate;
	private BigDecimal countryBranchId;
	private Date creationDate;
	private String createdBy;
	private BigDecimal idType;
	private String complianceStatus;
	
	public CustomerImageVerification() {
	
	}

	public CustomerImageVerification(BigDecimal customerImageVerificationIdId,
			Customer customer, String idNumber, Date idExpiryDate,
			String fileId, String fileScanStatus, String verifiedBy,
			Date verifiedDate, BigDecimal countryBranchId, Date creationDate,
			String createdBy,BigDecimal idType,String complianceStatus) {
		super();
		this.customerImageVerificationIdId = customerImageVerificationIdId;
		this.customer = customer;
		this.idNumber = idNumber;
		this.idExpiryDate = idExpiryDate;
		this.fileId = fileId;
		this.fileScanStatus = fileScanStatus;
		this.verifiedBy = verifiedBy;
		this.verifiedDate = verifiedDate;
		this.countryBranchId = countryBranchId;
		this.creationDate = creationDate;
		this.createdBy = createdBy;
		this.idType = idType;
		this.complianceStatus = complianceStatus;
	}

	@Id
	@GeneratedValue(generator="fs_customer_image_verif_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="fs_customer_image_verif_seq" ,sequenceName="FS_CUSTOMER_IMAGE_VERIF_SEQ",allocationSize=1)	
	@Column(name = "CUSTOMER_IMAGE_VERIFICATION_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getCustomerImageVerificationIdId() {
		return customerImageVerificationIdId;
	}

	public void setCustomerImageVerificationIdId(
			BigDecimal customerImageVerificationIdId) {
		this.customerImageVerificationIdId = customerImageVerificationIdId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMER_ID")

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Column(name = "ID_NUMBER")
	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	@Column(name = "ID_EXPIRY_DATE")
	public Date getIdExpiryDate() {
		return idExpiryDate;
	}

	public void setIdExpiryDate(Date idExpiryDate) {
		this.idExpiryDate = idExpiryDate;
	}

	@Column(name = "FILE_ID")
	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	@Column(name = "FILE_SCAN_STATUS")
	public String getFileScanStatus() {
		return fileScanStatus;
	}

	public void setFileScanStatus(String fileScanStatus) {
		this.fileScanStatus = fileScanStatus;
	}

	@Column(name = "VERIFIED_BY")
	public String getVerifiedBy() {
		return verifiedBy;
	}

	public void setVerifiedBy(String verifiedBy) {
		this.verifiedBy = verifiedBy;
	}

	@Column(name = "VERIFIED_DATE")
	public Date getVerifiedDate() {
		return verifiedDate;
	}

	public void setVerifiedDate(Date verifiedDate) {
		this.verifiedDate = verifiedDate;
	}

	@Column(name = "COUNTRY_BRANCH_ID")
	public BigDecimal getCountryBranchId() {
		return countryBranchId;
	}

	public void setCountryBranchId(BigDecimal countryBranchId) {
		this.countryBranchId = countryBranchId;
	}

	@Column(name = "CREATION_DATE")
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "ID_TYPE")
	public BigDecimal getIdType() {
		return idType;
	}

	public void setIdType(BigDecimal idType) {
		this.idType = idType;
	}

	@Column(name = "COMPLIANCE_STATUS")
	public String getComplianceStatus() {
		return complianceStatus;
	}

	public void setComplianceStatus(String complianceStatus) {
		this.complianceStatus = complianceStatus;
	}
	
}

