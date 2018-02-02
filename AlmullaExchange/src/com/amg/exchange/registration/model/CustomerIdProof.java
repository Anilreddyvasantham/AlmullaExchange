package com.amg.exchange.registration.model;

import java.math.BigDecimal;
import java.sql.Clob;
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

import com.amg.exchange.common.model.BizComponentData;
import com.amg.exchange.common.model.LanguageType;

/*******************************************************************************************************************

		 File		: CustomerIdProof.java
 
		 Project	: AlmullaExchange

		 Package	: com.amg.exchange.model
 
		 Created	:	
 						Date	: 29-May-2014 5:07:36 pm
		 				By		: Justin Vincent
 						Revision:
 
 		 Last Change:
 						Date	: 6-March-2015
 						By		: Nazish Ehsan Hashmi
		 				Revision:

		 Description: TODO 

********************************************************************************************************************/

@Entity
@Table(name = "FS_CUSTOMER_ID_PROOF" )
public class CustomerIdProof implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private BigDecimal custProofId;
	private Customer fsCustomer;
	private LanguageType fsLanguageType;
	//private DocumentImg fsDocumentImg;
	private String name;
	private String identityInt;
	private Date identityExpiryDate;
	private Date identityEffDate;
	private Date identityEndDate;
	private String approvedBy;
	private Date approvedDate;
	private String createdBy;
	private String updatedBy;
	private Date creationDate;
	private Date lastUpdatedDate;
	private String identityStatus;
	private BizComponentData fsBizComponentDataByIdentityTypeId;
	private BizComponentData fsBizComponentDataByCustomerTypeId;
	private BizComponentData fsBizComponentDataByIdentityFor;
	private String scanReq;
	//private Clob scanImage;
	private String scanSystem;
	//private Customer fsCustomerByRefCustomerId;
	//private Date imgUploadDate;

	public CustomerIdProof() {
	}

	public CustomerIdProof(BigDecimal custProofId) {
		this.custProofId = custProofId;
	}

	

	public CustomerIdProof(BigDecimal custProofId, Customer fsCustomer,
			LanguageType fsLanguageType,
			String name, String identityInt, Date identityExpiryDate,
			Date identityEffDate, Date identityEndDate, String approvedBy,
			Date approvedDate, String createdBy, String updatedBy,
			Date creationDate, Date lastUpdatedDate, String identityStatus,
			BizComponentData fsBizComponentDataByIdentityTypeId,
			BizComponentData fsBizComponentDataByCustomerTypeId,
			BizComponentData fsBizComponentDataByIdentityFor,String scanReq,String scanSystem) {
		
		this.custProofId = custProofId;
		this.fsCustomer = fsCustomer;
		this.fsLanguageType = fsLanguageType;
		//this.fsDocumentImg = fsDocumentImg;
		this.name = name;
		this.identityInt = identityInt;
		this.identityExpiryDate = identityExpiryDate;
		this.identityEffDate = identityEffDate;
		this.identityEndDate = identityEndDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.creationDate = creationDate;
		this.lastUpdatedDate = lastUpdatedDate;
		this.identityStatus = identityStatus;
		this.fsBizComponentDataByIdentityTypeId = fsBizComponentDataByIdentityTypeId;
		this.fsBizComponentDataByCustomerTypeId = fsBizComponentDataByCustomerTypeId;
		this.fsBizComponentDataByIdentityFor = fsBizComponentDataByIdentityFor;
		//this.fsCustomerByRefCustomerId = fsCustomerByRefCustomerId;
		//this.imgUploadDate = imgUploadDate;
		this.scanReq=scanReq;
		//this.scanImage=scanImage;
		this.scanSystem=scanSystem;
	}

	@Id
	/*@TableGenerator(name="custproofid",table="fs_custproofidpk",pkColumnName="custproofidkey",pkColumnValue="custproofidvalue",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.TABLE,generator="custproofid")
	*/
	@GeneratedValue(generator="fs_customer_Id_proof_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="fs_customer_Id_proof_seq" ,sequenceName="FS_CUSTOMER_ID_PROOF_SEQ",allocationSize=1)	
	@Column(name = "CUST_PROOF_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getCustProofId() {
		return this.custProofId;
	}

	public void setCustProofId(BigDecimal custProofId) {
		this.custProofId = custProofId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMER_ID")
	public Customer getFsCustomer() {
		return this.fsCustomer;
	}

	public void setFsCustomer(Customer fsCustomer) {
		this.fsCustomer = fsCustomer;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LANGUAGE_ID")
	public LanguageType getFsLanguageType() {
		return this.fsLanguageType;
	}

	public void setFsLanguageType(LanguageType fsLanguageType) {
		this.fsLanguageType = fsLanguageType;
	}

	@Column(name = "NAME", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "IDENTITY_INT", length = 15)
	public String getIdentityInt() {
		return this.identityInt;
	}

	public void setIdentityInt(String identityInt) {
		this.identityInt = identityInt;
	}

	@Column(name = "IDENTITY_EXPIRY_DATE")
	public Date getIdentityExpiryDate() {
		return this.identityExpiryDate;
	}

	public void setIdentityExpiryDate(Date identityExpiryDate) {
		this.identityExpiryDate = identityExpiryDate;
	}

	@Column(name = "IDENTITY_EFF_DATE")
	public Date getIdentityEffDate() {
		return this.identityEffDate;
	}

	public void setIdentityEffDate(Date identityEffDate) {
		this.identityEffDate = identityEffDate;
	}

	@Column(name = "IDENTITY_END_DATE")
	public Date getIdentityEndDate() {
		return this.identityEndDate;
	}

	public void setIdentityEndDate(Date identityEndDate) {
		this.identityEndDate = identityEndDate;
	}

	@Column(name = "APPROVED_BY", length = 20)
	public String getApprovedBy() {
		return this.approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	@Column(name = "APPROVED_DATE")
	public Date getApprovedDate() {
		return this.approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	@Column(name = "CREATED_BY", length = 30)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "UPDATED_BY", length = 30)
	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Column(name = "CREATION_DATE")
	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "LAST_UPDATED_DATE")
	public Date getLastUpdatedDate() {
		return this.lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	/*@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IMG_ID")
	public DocumentImg getFsDocumentImg() {
		return fsDocumentImg;
	}

	public void setFsDocumentImg(DocumentImg fsDocumentImg) {
		this.fsDocumentImg = fsDocumentImg;
	}*/

	@Column(name = "ISACTIVE", length = 1)
	public String getIdentityStatus() {
		return this.identityStatus;
	}

	public void setIdentityStatus(String identityStatus) {
		this.identityStatus = identityStatus;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IDENTITY_TYPE_ID")
	public BizComponentData getFsBizComponentDataByIdentityTypeId() {
		return fsBizComponentDataByIdentityTypeId;
	}

	public void setFsBizComponentDataByIdentityTypeId(BizComponentData fsBizComponentDataByIdentityTypeId) {
		this.fsBizComponentDataByIdentityTypeId = fsBizComponentDataByIdentityTypeId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMER_TYPE_ID")
	public BizComponentData getFsBizComponentDataByCustomerTypeId() {
		return fsBizComponentDataByCustomerTypeId;
	}

	public void setFsBizComponentDataByCustomerTypeId(BizComponentData fsBizComponentDataByCustomerTypeId) {
		this.fsBizComponentDataByCustomerTypeId = fsBizComponentDataByCustomerTypeId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IDENTITY_FOR")
	public BizComponentData getFsBizComponentDataByIdentityFor() {
		return fsBizComponentDataByIdentityFor;
	}

	public void setFsBizComponentDataByIdentityFor(BizComponentData fsBizComponentDataByIdentityFor) {
		this.fsBizComponentDataByIdentityFor = fsBizComponentDataByIdentityFor;
	}
	@Column(name = "SCAN_REQ")
	public String getScanReq() {
		return scanReq;
	}

	public void setScanReq(String scanReq) {
		this.scanReq = scanReq;
	}
	
	@Column(name = "SCAN_SYSTEM")
	public String getScanSystem() {
		return scanSystem;
	}

	public void setScanSystem(String scanSystem) {
		this.scanSystem = scanSystem;
	}
	
	/*@Column(name = "SCAN_SYSTEM")
	public String getScanType() {
		return scanType;
	}

	public void setScanType(String scanType) {
		this.scanType = scanType;
	}*/

	/*@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REF_CUSTOMER_ID")
	public Customer getFsCustomerByRefCustomerId() {
		return fsCustomerByRefCustomerId;
	}

	public void setFsCustomerByRefCustomerId(Customer fsCustomerByRefCustomerId) {
		this.fsCustomerByRefCustomerId = fsCustomerByRefCustomerId;
	}

	@Column(name = "IMG_UPLOAD_DATE")
	public Date getImgUploadDate() {
		return imgUploadDate;
	}

	public void setImgUploadDate(Date imgUploadDate) {
		this.imgUploadDate = imgUploadDate;
	}
	*/
	
}
