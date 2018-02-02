package com.amg.exchange.blacklist.model;

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



@Entity
@Table(name = "EX_BLACK_LIST_DETAIL")
public class BlackListDetails implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal  blackListDetailId;   
	private BlackListMaster  blackListMasterId;   
	private CountryMaster  applicatonCountryId; 
	private String fullName;
	private String nationalityCode;
	private String idType;
	private String idNo;
	private String isactive;
    private String createdBy;
    private Date createdDate;
    private String modifiedBy;
    private Date modifiedDate;
    private String approvedBy;
    private Date approvedDate;
    private String remarks;
	private BigDecimal seqNo;
 
public BlackListDetails()
{
	
}

public BlackListDetails(BigDecimal blackListDetailId,
		BlackListMaster blackListMasterId,CountryMaster applicatonCountryId,
		String fullName, String nationalityCode, String idType, String idNo,
		String isactive, String createdBy, Date createdDate, String modifiedBy,
		Date modifiedDate, String approvedBy, Date approvedDate, String remarks,BigDecimal seqNo) {
	super();
	this.blackListDetailId = blackListDetailId;
	this.blackListMasterId = blackListMasterId;
	this.applicatonCountryId = applicatonCountryId;
	this.fullName = fullName;
	this.nationalityCode = nationalityCode;
	this.idType = idType;
	this.idNo = idNo;
	this.isactive = isactive;
	this.createdBy = createdBy;
	this.createdDate = createdDate;
	this.modifiedBy = modifiedBy;
	this.modifiedDate = modifiedDate;
	this.approvedBy = approvedBy;
	this.approvedDate = approvedDate;
	this.remarks = remarks;
	this.seqNo =seqNo;
}

@Id
@GeneratedValue(generator="ex_black_list_details_seq",strategy=GenerationType.SEQUENCE)
@SequenceGenerator(name="ex_black_list_details_seq",sequenceName="EX_BLACK_LIST_DETAILS_SEQ",allocationSize=1)
@Column(name = "BLACK_LIST_DETAIL_ID", unique = true, nullable = false, precision = 22, scale = 0)

public BigDecimal getBlackListDetailId() {
	return blackListDetailId;
}

public void setBlackListDetailId(BigDecimal blackListDetailId) {
	this.blackListDetailId = blackListDetailId;
}
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "BLACK_LIST_MASTER_ID")
@Column(name = "BLACK_LIST_MASTER_ID")
public BlackListMaster getBlackListMasterId() {
	return blackListMasterId;
}

public void setBlackListMasterId(BlackListMaster blackListMasterId) {
	this.blackListMasterId = blackListMasterId;
}
@Column(name = "APPLICATION_COUNTRY_ID")
public CountryMaster getApplicatonCountryId() {
	return applicatonCountryId;
}

public void setApplicatonCountryId(CountryMaster applicatonCountryId) {
	this.applicatonCountryId = applicatonCountryId;
}
@Column(name = "FULL_NAME")
public String getFullName() {
	return fullName;
}

public void setFullName(String fullName) {
	this.fullName = fullName;
}
@Column(name = "NATIONALITY_CODE")
public String getNationalityCode() {
	return nationalityCode;
}

public void setNationalityCode(String nationalityCode) {
	this.nationalityCode = nationalityCode;
}
@Column(name = "ID_TYPE")
public String getIdType() {
	return idType;
}

public void setIdType(String idType) {
	this.idType = idType;
}
@Column(name = "ID_NO")
public String getIdNo() {
	return idNo;
}

public void setIdNo(String idNo) {
	this.idNo = idNo;
}
@Column(name = "ISACTIVE")
public String getIsactive() {
	return isactive;
}

public void setIsactive(String isactive) {
	this.isactive = isactive;
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

@Column(name = "SEQ_NO")
public BigDecimal getSeqNo() {
	return seqNo;
}

public void setSeqNo(BigDecimal seqNo) {
	this.seqNo = seqNo;
}
}
