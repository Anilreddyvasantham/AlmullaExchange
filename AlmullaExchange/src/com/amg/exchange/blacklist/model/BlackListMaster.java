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

/***
 * |
 * 
 * 
 * Purpose of this model to display all the black list
 * 
 * 
 * @author nazish
 * 
 */

@Entity
@Table(name = "EX_BLACK_LIST_MASTER")
public class BlackListMaster implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal blackListMasterId;
	private BigDecimal applicationCountryId;
	private String fullName;
	private String arabicFullName;
	private String address;
	private String arabicAddress;
	private Date dateOfDate;
	private String pob;
	private String partyType;
	private String cbkRefferenceNo;
	private Date cbkRefferenceDate;
	private String isactive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;
	private String seqNo;


	public BlackListMaster() {

	}

	public BlackListMaster(BigDecimal blackListMasterId,
			BigDecimal applicationCountryId, String fullName,
			String arabicFullName, String address, String arabicAddress,
			Date dateOfDate, String pob, String partyType,
			String cbkRefferenceNo, Date cbkRefferenceDate, String isactive,
			String createdBy, Date createdDate, String modifiedBy,
			Date modifiedDate, String approvedBy, Date approvedDate,
			String remarks, String seqNo) {
		super();
		this.blackListMasterId = blackListMasterId;
		this.applicationCountryId = applicationCountryId;
		this.fullName = fullName;
		this.arabicFullName = arabicFullName;
		this.address = address;
		this.arabicAddress = arabicAddress;
		this.dateOfDate = dateOfDate;
		this.pob = pob;
		this.partyType = partyType;
		this.cbkRefferenceNo = cbkRefferenceNo;
		this.cbkRefferenceDate = cbkRefferenceDate;
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
	@GeneratedValue(generator = "ex_black_list_master_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ex_black_list_master_seq", sequenceName = "EX_BLACK_LIST_MASTER_SEQ", allocationSize = 1)
	@Column(name = "BLACK_LIST_MASTER_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getBlackListMasterId() {
		return blackListMasterId;
	}

	public void setBlackListMasterId(BigDecimal blackListMasterId) {
		this.blackListMasterId = blackListMasterId;
	}

	
	@Column(name = "APPLICATION_COUNTRY_ID")
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}

	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}

	@Column(name = "FULL_NAME")
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Column(name = "ARABIC_FULL_NAME")
	public String getArabicFullName() {
		return arabicFullName;
	}

	public void setArabicFullName(String arabicFullName) {
		this.arabicFullName = arabicFullName;
	}

	@Column(name = "ADDRESS")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "ARABIC_ADDRESS")
	public String getArabicAddress() {
		return arabicAddress;
	}

	public void setArabicAddress(String arabicAddress) {
		this.arabicAddress = arabicAddress;
	}

	@Column(name = "DATE_OF_DATE")
	public Date getDateOfDate() {
		return dateOfDate;
	}

	public void setDateOfDate(Date dateOfDate) {
		this.dateOfDate = dateOfDate;
	}

	@Column(name = "POB")
	public String getPob() {
		return pob;
	}

	public void setPob(String pob) {
		this.pob = pob;
	}

	@Column(name = "PARTY_TYPE")
	public String getPartyType() {
		return partyType;
	}

	public void setPartyType(String partyType) {
		this.partyType = partyType;
	}

	@Column(name = "CBK_REFERENE_NO")
	public String getCbkRefferenceNo() {
		return cbkRefferenceNo;
	}

	public void setCbkRefferenceNo(String cbkRefferenceNo) {
		this.cbkRefferenceNo = cbkRefferenceNo;
	}

	@Column(name = "CBK_REFERENCE_DATE")
	public Date getCbkRefferenceDate() {
		return cbkRefferenceDate;
	}

	public void setCbkRefferenceDate(Date cbkRefferenceDate) {
		this.cbkRefferenceDate = cbkRefferenceDate;
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
	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	

}
