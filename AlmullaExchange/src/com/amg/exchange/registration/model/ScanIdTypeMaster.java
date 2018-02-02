package com.amg.exchange.registration.model;

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

/*******************************************************************************************************************

File		: ArcmateScanMaster.java

Project	: AlmullaExchange

Package	: com.amg.exchange.registration.model

Created	:	
				Date	: 02-Feb-2016
				By		: Nazish Ehsan Hashmi
				Revision:

 Last Change:
				Date	: 02-Feb-2016
				By		: Nazish Ehsan Hashmi
				Revision:



********************************************************************************************************************/
@Entity
@Table(name = "FS_SCAN_ID_TYPE_MASTER" )
public class ScanIdTypeMaster implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal scanIdTypeId;
	private BigDecimal applicationCountryId;
	private BigDecimal companyId;
	private BigDecimal idTypeId;
	private String idTypeValue;
	private String fileCategoryId;
	private String documentId;
	private String isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;
	private String idTypeDesc;
	private String userName;
	private String password;
	private String folderId;
	
	
	public ScanIdTypeMaster() {
	
	}

	public ScanIdTypeMaster(BigDecimal scanIdTypeId,
			BigDecimal applicationCountryId, BigDecimal companyId,
			BigDecimal idTypeId, String idTypeValue, String fileCategoryId,
			String documentId, String isActive, String createdBy,
			Date createdDate, String modifiedBy, Date modifiedDate,
			String approvedBy, Date approvedDate, String remarks,
			String idTypeDesc, String userName, String password, String folderId) {
		super();
		this.scanIdTypeId = scanIdTypeId;
		this.applicationCountryId = applicationCountryId;
		this.companyId = companyId;
		this.idTypeId = idTypeId;
		this.idTypeValue = idTypeValue;
		this.fileCategoryId = fileCategoryId;
		this.documentId = documentId;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.remarks = remarks;
		this.idTypeDesc = idTypeDesc;
		this.userName = userName;
		this.password = password;
		this.folderId = folderId;
	}
	
	@Id
	@GeneratedValue(generator="fs_scan_id_type_master_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="fs_scan_id_type_master_seq" ,sequenceName="FS_SCAN_ID_TYPE_MASTER_SEQ",allocationSize=1)	
	@Column(name = "SCAN_ID_TYPE_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getScanIdTypeId() {
		return scanIdTypeId;
	}
	public void setScanIdTypeId(BigDecimal scanIdTypeId) {
		this.scanIdTypeId = scanIdTypeId;
	}
	
	@Column(name = "APPLICATION_COUNTRY_ID")
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}
	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}
	
	@Column(name = "COMPANY_ID")
	public BigDecimal getCompanyId() {
		return companyId;
	}
	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}
	
	@Column(name = "ID_TYPE_ID")
	public BigDecimal getIdTypeId() {
		return idTypeId;
	}

	public void setIdTypeId(BigDecimal idTypeId) {
		this.idTypeId = idTypeId;
	}

	@Column(name = "ID_TYPE_VALUE")
	public String getIdTypeValue() {
		return idTypeValue;
	}

	public void setIdTypeValue(String idTypeValue) {
		this.idTypeValue = idTypeValue;
	}

	@Column(name = "FILE_CATEGORY_ID")
	public String getFileCategoryId() {
		return fileCategoryId;
	}

	public void setFileCategoryId(String fileCategoryId) {
		this.fileCategoryId = fileCategoryId;
	}

	@Column(name = "DOCUMENT_TYPE_ID")
	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	@Column(name = "IS_ACTIVE")
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
	@Column(name = "CREATED_BY")
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
	
	@Column(name = "MODIFIED_BY")
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	@Column(name = "MODIFIED_DATE")
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	@Column(name = "APPROVED_BY")
	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	
	@Column(name = "APPROVED_DATE")
	public Date getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
	
	@Column(name = "REMARKS")
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name = "ID_TYPE_DESC")
	public String getIdTypeDesc() {
		return idTypeDesc;
	}

	public void setIdTypeDesc(String idTypeDesc) {
		this.idTypeDesc = idTypeDesc;
	}
	
	@Column(name = "USER_NAME")
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Column(name = "PASSWORD")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "FOLDER_ID")
	public String getFolderId() {
		return folderId;
	}

	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}
	
	
	
}
