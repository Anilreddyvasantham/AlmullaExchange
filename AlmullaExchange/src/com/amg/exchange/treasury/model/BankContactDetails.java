package com.amg.exchange.treasury.model;

// default package
// Generated May 23, 2014 5:18:25  Created by Chennai ODC

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
import javax.persistence.TableGenerator;

/**
 * BankContactDetails Created by Chennai ODC
 */
@Entity
@Table(name = "EX_BANK_CONTACT_DETAILS")
public class BankContactDetails implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal bankContactId;
	private BankMaster exBankMaster;
	private String region;
	private String contactPerson;
	private String contactDept;
	private String contactDesg;
	private String contactPersonAr;
	private String contactDeptAr;
	private String contactDesgAr;
	private String recordStatus;
	private String mobile;
	private Date createDate;
	private Date updateDate;
	private String creator;
	private String modifier;
	private Zone exZone;

	public BankContactDetails() {
	}

	public BankContactDetails(BigDecimal bankContactId) {
		this.bankContactId = bankContactId;
	}

	public BankContactDetails(BigDecimal bankContactId, BankMaster exBankMaster,
			String region, String contactPerson, String contactDept,
			String contactDesg, String contactPersonAr, String contactDeptAr,
			String contactDesgAr, String recordStatus, Date createDate,
			Date updateDate, String creator, String modifier, String mobile,Zone exZone, String approvedBy, Date approvedDate) {
		this.bankContactId = bankContactId;
		this.exBankMaster = exBankMaster;
		this.region = region;
		this.contactPerson = contactPerson;
		this.contactDept = contactDept;
		this.contactDesg = contactDesg;
		this.contactPersonAr = contactPersonAr;
		this.contactDeptAr = contactDeptAr;
		this.contactDesgAr = contactDesgAr;
		this.recordStatus = recordStatus;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.creator = creator;
		this.modifier = modifier;
		this.mobile = mobile;
		this.exZone = exZone;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
	}

	@Id
	@GeneratedValue(generator="ex_bank_contact_details_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_bank_contact_details_seq",sequenceName="EX_BANK_CONTACT_DETAILS_SEQ",allocationSize=1)	
	@Column(name = "BANK_CONTACT_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getBankContactId() {
		return this.bankContactId;
	}

	public void setBankContactId(BigDecimal bankContactId) {
		this.bankContactId = bankContactId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_ID")
	public BankMaster getExBankMaster() {
		return this.exBankMaster;
	}

	public void setExBankMaster(BankMaster exBankMaster) {
		this.exBankMaster = exBankMaster;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ZONE_ID")
	public Zone getExZone() {
		return exZone;
	}

	public void setExZone(Zone exZone) {
		this.exZone = exZone;
	}

	@Column(name = "REGION", length = 150)
	public String getRegion() {
		return this.region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	@Column(name = "CONTACT_PERSON", length = 150)
	public String getContactPerson() {
		return this.contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	@Column(name = "CONTACT_DEPT", length = 100)
	public String getContactDept() {
		return this.contactDept;
	}

	public void setContactDept(String contactDept) {
		this.contactDept = contactDept;
	}

	@Column(name = "CONTACT_DESG", length = 100)
	public String getContactDesg() {
		return this.contactDesg;
	}

	public void setContactDesg(String contactDesg) {
		this.contactDesg = contactDesg;
	}

	@Column(name = "CONTACT_PERSON_AR", length = 120)
	public String getContactPersonAr() {
		return this.contactPersonAr;
	}

	public void setContactPersonAr(String contactPersonAr) {
		this.contactPersonAr = contactPersonAr;
	}

	@Column(name = "CONTACT_DEPT_AR", length = 60)
	public String getContactDeptAr() {
		return this.contactDeptAr;
	}

	public void setContactDeptAr(String contactDeptAr) {
		this.contactDeptAr = contactDeptAr;
	}

	@Column(name = "CONTACT_DESG_AR", length = 60)
	public String getContactDesgAr() {
		return this.contactDesgAr;
	}

	public void setContactDesgAr(String contactDesgAr) {
		this.contactDesgAr = contactDesgAr;
	}

	@Column(name = "ISACTIVE", length = 1)
	public String getRecordStatus() {
		return this.recordStatus;
	}

	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}

	@Column(name = "CREATED_DATE")
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "MODIFIED_DATE")
	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Column(name = "CREATED_BY", length = 15)
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name = "MODIFIED_BY", length = 15)
	public String getModifier() {
		return this.modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	
	@Column(name = "MOBILE", length = 10)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
/* START ADDED BY VISWA@@@ */ 
	
	private String approvedBy;
	private Date approvedDate;
	
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
	
	/* END ADDED BY VISWA@@@ */ 

}
