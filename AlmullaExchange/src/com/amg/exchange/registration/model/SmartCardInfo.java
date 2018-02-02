package com.amg.exchange.registration.model;

// Generated Jun 6, 2014 6:13:24  Created by Chennai ODC

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/*******************************************************************************************************************

File		: Customer.java

Project	: AlmullaExchange

Package	: com.amg.exchange.model

Created	:	
				Date	: 16-Dec-2014
				By		: Nazish Ehsan Hashmi
				Revision:

 
Description: TODO 

********************************************************************************************************************/

@Entity
@Table(name = "FS_SMART_CARD_INFO")
public class SmartCardInfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal smartCardId;
	private String civilId;
	private Date expireDate;
	private String fullNameAr;
	private String firstNameAr;
	private String fatherNameAr;
	private String grandFatherNameAr;
	private String surnameAr;
	private String gender;
	private String fullName;
	private String nationalityId;
	private Date birthDate;
	private Date issueDate;
	private String documentNo;
	private String serialNo;
	private String district;
	private String blockNo;
	private String street;
	private String floorNo;
	private String bloodType;
	private String guardIdNo;
	private String telephone1;
	private String telephone2;
	private String emailId;
	private String addrUniqKey;
	private String creatorApplId;
	private String updateApplId;
	private String sourceType;
	private String bldgNo;
	private String nationalityAr;
	private String genderAr;
	private String firstName;
	private String fatherName;
	private String middleName;
	private String grandFatherName;
	private String surname;
	private String moiRef;
	private String moiRefIndic;
	private String unitType;
	private String unitNo;
	private String addlFld1;
	private String addlFld2;
	private Date createDate;
	private Date updateDate;
	private String modifier;
	private String programNo;
	private String creator;
	private Customer fsCustomer;

	public SmartCardInfo() {
	}

	public SmartCardInfo(BigDecimal smartCardId) {
		this.smartCardId = smartCardId;
	}

	public SmartCardInfo(BigDecimal smartCardId, String civilId,
			Date expireDate, String fullNameAr, String firstNameAr,
			String fatherNameAr, String grandFatherNameAr, String surnameAr,
			String gender, String fullName, String nationalityId,
			Date birthDate, Date issueDate, String documentNo, String serialNo,
			String district, String blockNo, String street, String floorNo,
			String bloodType, String guardIdNo, String telephone1,
			String telephone2, String emailId, String addrUniqKey,
			String creatorApplId, String updateApplId, String sourceType,
			String bldgNo, String nationalityAr, String genderAr,
			String firstName, String fatherName, String middleName,
			String grandFatherName, String surname, String moiRef,
			String moiRefIndic, String unitType, String unitNo,
			String addlFld1, String addlFld2, Date createDate, Date updateDate,
			String modifier, String programNo, String creator,
			Customer fsCustomer) {
		this.smartCardId = smartCardId;
		this.civilId = civilId;
		this.expireDate = expireDate;
		this.fullNameAr = fullNameAr;
		this.firstNameAr = firstNameAr;
		this.fatherNameAr = fatherNameAr;
		this.grandFatherNameAr = grandFatherNameAr;
		this.surnameAr = surnameAr;
		this.gender = gender;
		this.fullName = fullName;
		this.nationalityId = nationalityId;
		this.birthDate = birthDate;
		this.issueDate = issueDate;
		this.documentNo = documentNo;
		this.serialNo = serialNo;
		this.district = district;
		this.blockNo = blockNo;
		this.street = street;
		this.floorNo = floorNo;
		this.bloodType = bloodType;
		this.guardIdNo = guardIdNo;
		this.telephone1 = telephone1;
		this.telephone2 = telephone2;
		this.emailId = emailId;
		this.addrUniqKey = addrUniqKey;
		this.creatorApplId = creatorApplId;
		this.updateApplId = updateApplId;
		this.sourceType = sourceType;
		this.bldgNo = bldgNo;
		this.nationalityAr = nationalityAr;
		this.genderAr = genderAr;
		this.firstName = firstName;
		this.fatherName = fatherName;
		this.middleName = middleName;
		this.grandFatherName = grandFatherName;
		this.surname = surname;
		this.moiRef = moiRef;
		this.moiRefIndic = moiRefIndic;
		this.unitType = unitType;
		this.unitNo = unitNo;
		this.addlFld1 = addlFld1;
		this.addlFld2 = addlFld2;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.modifier = modifier;
		this.programNo = programNo;
		this.creator = creator;
		this.fsCustomer = fsCustomer;
	}

	@Id
	/*@TableGenerator(name = "smartcardid", table = "fs_smartcardidpk", pkColumnName = "smartcardidkey", pkColumnValue = "smartcardidvalue", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "smartcardid")
	*/
	@GeneratedValue(generator="fs_smart_card_info_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="fs_smart_card_info_seq" ,sequenceName="FS_SMART_CARD_INFO_SEQ",allocationSize=1)		
	@Column(name = "SMART_CARD_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getSmartCardId() {
		return this.smartCardId;
	}

	public void setSmartCardId(BigDecimal smartCardId) {
		this.smartCardId = smartCardId;
	}

	@Column(name = "CIVIL_ID", length = 12)
	public String getCivilId() {
		return this.civilId;
	}

	public void setCivilId(String civilId) {
		this.civilId = civilId;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "EXPIRE_DATE", length = 7)
	public Date getExpireDate() {
		return this.expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	@Column(name = "FULL_NAME_AR", length = 200)
	public String getFullNameAr() {
		return this.fullNameAr;
	}

	public void setFullNameAr(String fullNameAr) {
		this.fullNameAr = fullNameAr;
	}

	@Column(name = "FIRST_NAME_AR", length = 200)
	public String getFirstNameAr() {
		return this.firstNameAr;
	}

	public void setFirstNameAr(String firstNameAr) {
		this.firstNameAr = firstNameAr;
	}

	@Column(name = "FATHER_NAME_AR", length = 200)
	public String getFatherNameAr() {
		return this.fatherNameAr;
	}

	public void setFatherNameAr(String fatherNameAr) {
		this.fatherNameAr = fatherNameAr;
	}

	@Column(name = "GRAND_FATHER_NAME_AR", length = 200)
	public String getGrandFatherNameAr() {
		return this.grandFatherNameAr;
	}

	public void setGrandFatherNameAr(String grandFatherNameAr) {
		this.grandFatherNameAr = grandFatherNameAr;
	}

	@Column(name = "SURNAME_AR", length = 200)
	public String getSurnameAr() {
		return this.surnameAr;
	}

	public void setSurnameAr(String surnameAr) {
		this.surnameAr = surnameAr;
	}

	@Column(name = "GENDER", length = 1)
	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Column(name = "FULL_NAME", length = 120)
	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Column(name = "NATIONALITY_ID", length = 3)
	public String getNationalityId() {
		return this.nationalityId;
	}

	public void setNationalityId(String nationalityId) {
		this.nationalityId = nationalityId;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "BIRTH_DATE", length = 7)
	public Date getBirthDate() {
		return this.birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "ISSUE_DATE", length = 7)
	public Date getIssueDate() {
		return this.issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	@Column(name = "DOCUMENT_NO", length = 50)
	public String getDocumentNo() {
		return this.documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	@Column(name = "SERIAL_NO", length = 100)
	public String getSerialNo() {
		return this.serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	@Column(name = "DISTRICT", length = 100)
	public String getDistrict() {
		return this.district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	@Column(name = "BLOCK_NO", length = 100)
	public String getBlockNo() {
		return this.blockNo;
	}

	public void setBlockNo(String blockNo) {
		this.blockNo = blockNo;
	}

	@Column(name = "STREET", length = 100)
	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	@Column(name = "FLOOR_NO", length = 100)
	public String getFloorNo() {
		return this.floorNo;
	}

	public void setFloorNo(String floorNo) {
		this.floorNo = floorNo;
	}

	@Column(name = "BLOOD_TYPE", length = 10)
	public String getBloodType() {
		return this.bloodType;
	}

	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}

	@Column(name = "GUARD_ID_NO", length = 20)
	public String getGuardIdNo() {
		return this.guardIdNo;
	}

	public void setGuardIdNo(String guardIdNo) {
		this.guardIdNo = guardIdNo;
	}

	@Column(name = "TELEPHONE1", length = 100)
	public String getTelephone1() {
		return this.telephone1;
	}

	public void setTelephone1(String telephone1) {
		this.telephone1 = telephone1;
	}

	@Column(name = "TELEPHONE2", length = 100)
	public String getTelephone2() {
		return this.telephone2;
	}

	public void setTelephone2(String telephone2) {
		this.telephone2 = telephone2;
	}

	@Column(name = "EMAIL_ID", length = 100)
	public String getEmailId() {
		return this.emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	@Column(name = "ADDR_UNIQ_KEY", length = 100)
	public String getAddrUniqKey() {
		return this.addrUniqKey;
	}

	public void setAddrUniqKey(String addrUniqKey) {
		this.addrUniqKey = addrUniqKey;
	}

	@Column(name = "CREATOR_APPL_ID", length = 20)
	public String getCreatorApplId() {
		return this.creatorApplId;
	}

	public void setCreatorApplId(String creatorApplId) {
		this.creatorApplId = creatorApplId;
	}

	@Column(name = "UPDATE_APPL_ID", length = 4)
	public String getUpdateApplId() {
		return this.updateApplId;
	}

	public void setUpdateApplId(String updateApplId) {
		this.updateApplId = updateApplId;
	}

	@Column(name = "SOURCE_TYPE", length = 2)
	public String getSourceType() {
		return this.sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	@Column(name = "BLDG_NO", length = 30)
	public String getBldgNo() {
		return this.bldgNo;
	}

	public void setBldgNo(String bldgNo) {
		this.bldgNo = bldgNo;
	}

	@Column(name = "NATIONALITY_AR", length = 10)
	public String getNationalityAr() {
		return this.nationalityAr;
	}

	public void setNationalityAr(String nationalityAr) {
		this.nationalityAr = nationalityAr;
	}

	@Column(name = "GENDER_AR", length = 50)
	public String getGenderAr() {
		return this.genderAr;
	}

	public void setGenderAr(String genderAr) {
		this.genderAr = genderAr;
	}

	@Column(name = "FIRST_NAME", length = 200)
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "FATHER_NAME", length = 200)
	public String getFatherName() {
		return this.fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	@Column(name = "MIDDLE_NAME", length = 200)
	public String getMiddleName() {
		return this.middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	@Column(name = "GRAND_FATHER_NAME", length = 200)
	public String getGrandFatherName() {
		return this.grandFatherName;
	}

	public void setGrandFatherName(String grandFatherName) {
		this.grandFatherName = grandFatherName;
	}

	@Column(name = "SURNAME", length = 200)
	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@Column(name = "MOI_REF", length = 200)
	public String getMoiRef() {
		return this.moiRef;
	}

	public void setMoiRef(String moiRef) {
		this.moiRef = moiRef;
	}

	@Column(name = "MOI_REF_INDIC", length = 200)
	public String getMoiRefIndic() {
		return this.moiRefIndic;
	}

	public void setMoiRefIndic(String moiRefIndic) {
		this.moiRefIndic = moiRefIndic;
	}

	@Column(name = "UNIT_TYPE", length = 200)
	public String getUnitType() {
		return this.unitType;
	}

	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}

	@Column(name = "UNIT_NO", length = 200)
	public String getUnitNo() {
		return this.unitNo;
	}

	public void setUnitNo(String unitNo) {
		this.unitNo = unitNo;
	}

	@Column(name = "ADDL_FLD1", length = 200)
	public String getAddlFld1() {
		return this.addlFld1;
	}

	public void setAddlFld1(String addlFld1) {
		this.addlFld1 = addlFld1;
	}

	@Column(name = "ADDL_FLD2", length = 200)
	public String getAddlFld2() {
		return this.addlFld2;
	}

	public void setAddlFld2(String addlFld2) {
		this.addlFld2 = addlFld2;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATE_DATE", length = 7)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "UPDATE_DATE", length = 7)
	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Column(name = "MODIFIER", length = 20)
	public String getModifier() {
		return this.modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	@Column(name = "PROGRAM_NO", length = 20)
	public String getProgramNo() {
		return this.programNo;
	}

	public void setProgramNo(String programNo) {
		this.programNo = programNo;
	}

	@Column(name = "CREATOR", length = 20)
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMER_ID")
	public Customer getFsCustomer() {
		return fsCustomer;
	}

	public void setFsCustomer(Customer fsCustomer) {
		this.fsCustomer = fsCustomer;
	}

	
}
