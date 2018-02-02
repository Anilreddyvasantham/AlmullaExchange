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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
* Nazish Ehsan Hashmi
*/
@Entity
@Table(name = "DMS_ID_MAS")
public class DmsMas implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal dmsIdMasId;
	private String appnCountry;
	private String docType;
	private String idno;
	private String eFuname;
	private String lFuname;
	private String lFstName;
	private String lFatherName;
	private String lGfName;
	private String lSurname;
	private Date expireDt;
	private String gender;
	private String ntcdId;
	private Date birthdat;
	private Date issuedat;
	private String documentNo;
	private String serialNo;
	private String district;
	private String blockNo;
	private String street;
	private String floorno;
	private String bloodTyp;
	private String guardIdno;
	private String tel1;
	private String tel2;
	private String emailId;
	private String addrUniqKey;
	private String srceTyp;
	private String bldgno;
	private String barcode;
	private Date crtdat;
	private String creApplId;
	private String creator;
	private Date upddat;
	private String modifier;
	private String updApplId;
	private String progno;
	private String lAddr;
	private String eAddr;
	private String cardCountry;
	private String lLastName;
	private String lMiddleName1;
	private String lMiddleName2;
	private String lMiddleName3;
	private String lMiddleName4;
	private String cprno;
	private String eFirstName;
	private String eLastName;
	private String eMiddleName1;
	private String eMiddleName2;
	private String eMiddleName3;
	private String eMiddleName4;
	private String flatno;
	private String bldgAlpha;
	private String lBldgAlpha;
	private String roadno;
	private String roadName;
	private String lRoadName;
	private String blockName;
	private String lBlockName;
	private String governateNo;
	private String eGovernateName;
	private String lGovernateName;
	private String lEmployerName1;
	private String lEmployerName2;
	private String lEmployerName3;
	private String lEmployerName4;
	private String lLatestEducdegree;
	private String eLatestEducdegree;
	private String lOccupDescr1;
	private String lOccupDescr2;
	private String lOccupDescr3;
	private String lOccupDescr4;
	private String eOccupDescr1;
	private String eOccupDescr2;
	private String eOccupDescr3;
	private String eOccupDescr4;
	private String lSponsorName;
	private String clearingAgentInd;
	private String employerName1;
	private String employerName2;
	private String employerName3;
	private String employerName4;
	private String employerNo1;
	private String employerNo2;
	private String employerNo3;
	private String employerNo4;
	private String employerFlag1;
	private String employerFlag2;
	private String employerFlag3;
	private String employerFlag4;
	private String laborForceParticip;
	private String sponsorCprnoorUnitno;
	private String sponsorFlag;
	private String sponsorName;
	private String eLfpName;
	private String lLfpName;
	private String eCountryName;
	private String lCountryName;
	private String iacoCode;
	private String ePlaceOfBirth;
	private String lPlaceOfBirth;
	private String countryOfBirth;
	private String passportNo;
	private String passportType;
	private String passportSeqNo;
	private String passportExpiryDate;
	private String passportIssueDate;
	private String visaNo;
	private String visaExpiryDate;
	private String visaType;
	private String residencePermitNo;
	private String residencePermitType;
	private String residentType;
	private String cardVerifStatus;
	private String employmentFlag1;
	private String employmentFlag2;
	private String employmentFlag3;
	private String employmentFlag4;

	public DmsMas() {
	}

	public DmsMas(BigDecimal dmsIdMasId) {
		this.dmsIdMasId = dmsIdMasId;
	
	}

	
	public DmsMas(BigDecimal dmsIdMasId, String appnCountry, String docType,
			String idno, String eFuname, String lFuname, String lFstName,
			String lFatherName, String lGfName, String lSurname, Date expireDt,
			String gender, String ntcdId, Date birthdat, Date issuedat,
			String documentNo, String serialNo, String district,
			String blockNo, String street, String floorno, String bloodTyp,
			String guardIdno, String tel1, String tel2, String emailId,
			String addrUniqKey, String srceTyp, String bldgno, String barcode,
			Date crtdat, String creApplId, String creator, Date upddat,
			String modifier, String updApplId, String progno, String lAddr,
			String eAddr, String cardCountry, String lLastName,
			String lMiddleName1, String lMiddleName2, String lMiddleName3,
			String lMiddleName4, String cprno, String eFirstName,
			String eLastName, String eMiddleName1, String eMiddleName2,
			String eMiddleName3, String eMiddleName4, String flatno,
			String bldgAlpha, String lBldgAlpha, String roadno,
			String roadName, String lRoadName, String blockName,
			String lBlockName, String governateNo, String eGovernateName,
			String lGovernateName, String lEmployerName1,
			String lEmployerName2, String lEmployerName3,
			String lEmployerName4, String lLatestEducdegree,
			String eLatestEducdegree, String lOccupDescr1, String lOccupDescr2,
			String lOccupDescr3, String lOccupDescr4, String eOccupDescr1,
			String eOccupDescr2, String eOccupDescr3, String eOccupDescr4,
			String lSponsorName, String clearingAgentInd, String employerName1,
			String employerName2, String employerName3, String employerName4,
			String employerNo1, String employerNo2, String employerNo3,
			String employerNo4, String employerFlag1, String employerFlag2,
			String employerFlag3, String employerFlag4,
			String laborForceParticip, String sponsorCprnoorUnitno,
			String sponsorFlag, String sponsorName, String eLfpName,
			String lLfpName, String eCountryName, String lCountryName,
			String iacoCode, String ePlaceOfBirth, String lPlaceOfBirth,
			String countryOfBirth, String passportNo, String passportType,
			String passportSeqNo, String passportExpiryDate,
			String passportIssueDate, String visaNo, String visaExpiryDate,
			String visaType, String residencePermitNo,
			String residencePermitType, String residentType,
			String cardVerifStatus, String employmentFlag1,
			String employmentFlag2, String employmentFlag3,
			String employmentFlag4) {
		super();
		this.dmsIdMasId = dmsIdMasId;
		this.appnCountry = appnCountry;
		this.docType = docType;
		this.idno = idno;
		this.eFuname = eFuname;
		this.lFuname = lFuname;
		this.lFstName = lFstName;
		this.lFatherName = lFatherName;
		this.lGfName = lGfName;
		this.lSurname = lSurname;
		this.expireDt = expireDt;
		this.gender = gender;
		this.ntcdId = ntcdId;
		this.birthdat = birthdat;
		this.issuedat = issuedat;
		this.documentNo = documentNo;
		this.serialNo = serialNo;
		this.district = district;
		this.blockNo = blockNo;
		this.street = street;
		this.floorno = floorno;
		this.bloodTyp = bloodTyp;
		this.guardIdno = guardIdno;
		this.tel1 = tel1;
		this.tel2 = tel2;
		this.emailId = emailId;
		this.addrUniqKey = addrUniqKey;
		this.srceTyp = srceTyp;
		this.bldgno = bldgno;
		this.barcode = barcode;
		this.crtdat = crtdat;
		this.creApplId = creApplId;
		this.creator = creator;
		this.upddat = upddat;
		this.modifier = modifier;
		this.updApplId = updApplId;
		this.progno = progno;
		this.lAddr = lAddr;
		this.eAddr = eAddr;
		this.cardCountry = cardCountry;
		this.lLastName = lLastName;
		this.lMiddleName1 = lMiddleName1;
		this.lMiddleName2 = lMiddleName2;
		this.lMiddleName3 = lMiddleName3;
		this.lMiddleName4 = lMiddleName4;
		this.cprno = cprno;
		this.eFirstName = eFirstName;
		this.eLastName = eLastName;
		this.eMiddleName1 = eMiddleName1;
		this.eMiddleName2 = eMiddleName2;
		this.eMiddleName3 = eMiddleName3;
		this.eMiddleName4 = eMiddleName4;
		this.flatno = flatno;
		this.bldgAlpha = bldgAlpha;
		this.lBldgAlpha = lBldgAlpha;
		this.roadno = roadno;
		this.roadName = roadName;
		this.lRoadName = lRoadName;
		this.blockName = blockName;
		this.lBlockName = lBlockName;
		this.governateNo = governateNo;
		this.eGovernateName = eGovernateName;
		this.lGovernateName = lGovernateName;
		this.lEmployerName1 = lEmployerName1;
		this.lEmployerName2 = lEmployerName2;
		this.lEmployerName3 = lEmployerName3;
		this.lEmployerName4 = lEmployerName4;
		this.lLatestEducdegree = lLatestEducdegree;
		this.eLatestEducdegree = eLatestEducdegree;
		this.lOccupDescr1 = lOccupDescr1;
		this.lOccupDescr2 = lOccupDescr2;
		this.lOccupDescr3 = lOccupDescr3;
		this.lOccupDescr4 = lOccupDescr4;
		this.eOccupDescr1 = eOccupDescr1;
		this.eOccupDescr2 = eOccupDescr2;
		this.eOccupDescr3 = eOccupDescr3;
		this.eOccupDescr4 = eOccupDescr4;
		this.lSponsorName = lSponsorName;
		this.clearingAgentInd = clearingAgentInd;
		this.employerName1 = employerName1;
		this.employerName2 = employerName2;
		this.employerName3 = employerName3;
		this.employerName4 = employerName4;
		this.employerNo1 = employerNo1;
		this.employerNo2 = employerNo2;
		this.employerNo3 = employerNo3;
		this.employerNo4 = employerNo4;
		this.employerFlag1 = employerFlag1;
		this.employerFlag2 = employerFlag2;
		this.employerFlag3 = employerFlag3;
		this.employerFlag4 = employerFlag4;
		this.laborForceParticip = laborForceParticip;
		this.sponsorCprnoorUnitno = sponsorCprnoorUnitno;
		this.sponsorFlag = sponsorFlag;
		this.sponsorName = sponsorName;
		this.eLfpName = eLfpName;
		this.lLfpName = lLfpName;
		this.eCountryName = eCountryName;
		this.lCountryName = lCountryName;
		this.iacoCode = iacoCode;
		this.ePlaceOfBirth = ePlaceOfBirth;
		this.lPlaceOfBirth = lPlaceOfBirth;
		this.countryOfBirth = countryOfBirth;
		this.passportNo = passportNo;
		this.passportType = passportType;
		this.passportSeqNo = passportSeqNo;
		this.passportExpiryDate = passportExpiryDate;
		this.passportIssueDate = passportIssueDate;
		this.visaNo = visaNo;
		this.visaExpiryDate = visaExpiryDate;
		this.visaType = visaType;
		this.residencePermitNo = residencePermitNo;
		this.residencePermitType = residencePermitType;
		this.residentType = residentType;
		this.cardVerifStatus = cardVerifStatus;
		this.employmentFlag1 = employmentFlag1;
		this.employmentFlag2 = employmentFlag2;
		this.employmentFlag3 = employmentFlag3;
		this.employmentFlag4 = employmentFlag4;
	}

	@Id
	@GeneratedValue(generator="fs_dms_mas_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="fs_dms_mas_seq",sequenceName="FS_DMS_MAS_SEQ",allocationSize=1)
	@Column(name = "ID_SEQ", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getDmsIdMasId() {
		return this.dmsIdMasId;
	}

	public void setDmsIdMasId(BigDecimal dmsIdMasId) {
		this.dmsIdMasId = dmsIdMasId;
	}

	@Column(name = "APPN_COUNTRY", length = 3)
	public String getAppnCountry() {
		return this.appnCountry;
	}

	public void setAppnCountry(String appnCountry) {
		this.appnCountry = appnCountry;
	}

	@Column(name = "DOC_TYPE", nullable = false, length = 3)
	public String getDocType() {
		return this.docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	@Column(name = "IDNO", length = 12)
	public String getIdno() {
		return this.idno;
	}

	public void setIdno(String idno) {
		this.idno = idno;
	}

	@Column(name = "E_FUNAME", length = 120)
	public String getEFuname() {
		return this.eFuname;
	}

	public void setEFuname(String eFuname) {
		this.eFuname = eFuname;
	}

	@Column(name = "L_FUNAME", length = 200)
	public String getLFuname() {
		return this.lFuname;
	}

	public void setLFuname(String lFuname) {
		this.lFuname = lFuname;
	}

	@Column(name = "L_FST_NAME", length = 200)
	public String getLFstName() {
		return this.lFstName;
	}

	public void setLFstName(String lFstName) {
		this.lFstName = lFstName;
	}

	@Column(name = "L_FATHER_NAME", length = 200)
	public String getLFatherName() {
		return this.lFatherName;
	}

	public void setLFatherName(String lFatherName) {
		this.lFatherName = lFatherName;
	}

	@Column(name = "L_GF_NAME", length = 200)
	public String getLGfName() {
		return this.lGfName;
	}

	public void setLGfName(String lGfName) {
		this.lGfName = lGfName;
	}

	@Column(name = "L_SURNAME", length = 200)
	public String getLSurname() {
		return this.lSurname;
	}

	public void setLSurname(String lSurname) {
		this.lSurname = lSurname;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "EXPIRE_DT", length = 7)
	public Date getExpireDt() {
		return this.expireDt;
	}

	public void setExpireDt(Date expireDt) {
		this.expireDt = expireDt;
	}

	@Column(name = "GENDER", length = 1)
	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Column(name = "NTCD_ID", length = 3)
	public String getNtcdId() {
		return this.ntcdId;
	}

	public void setNtcdId(String ntcdId) {
		this.ntcdId = ntcdId;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "BIRTHDAT", length = 7)
	public Date getBirthdat() {
		return this.birthdat;
	}

	public void setBirthdat(Date birthdat) {
		this.birthdat = birthdat;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "ISSUEDAT", length = 7)
	public Date getIssuedat() {
		return this.issuedat;
	}

	public void setIssuedat(Date issuedat) {
		this.issuedat = issuedat;
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

	@Column(name = "FLOORNO", length = 100)
	public String getFloorno() {
		return this.floorno;
	}

	public void setFloorno(String floorno) {
		this.floorno = floorno;
	}

	@Column(name = "BLOOD_TYP", length = 10)
	public String getBloodTyp() {
		return this.bloodTyp;
	}

	public void setBloodTyp(String bloodTyp) {
		this.bloodTyp = bloodTyp;
	}

	@Column(name = "GUARD_IDNO", length = 20)
	public String getGuardIdno() {
		return this.guardIdno;
	}

	public void setGuardIdno(String guardIdno) {
		this.guardIdno = guardIdno;
	}

	@Column(name = "TEL1", length = 100)
	public String getTel1() {
		return this.tel1;
	}

	public void setTel1(String tel1) {
		this.tel1 = tel1;
	}

	@Column(name = "TEL2", length = 100)
	public String getTel2() {
		return this.tel2;
	}

	public void setTel2(String tel2) {
		this.tel2 = tel2;
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

	@Column(name = "SRCE_TYP", length = 2)
	public String getSrceTyp() {
		return this.srceTyp;
	}

	public void setSrceTyp(String srceTyp) {
		this.srceTyp = srceTyp;
	}

	@Column(name = "BLDGNO", length = 30)
	public String getBldgno() {
		return this.bldgno;
	}

	public void setBldgno(String bldgno) {
		this.bldgno = bldgno;
	}

	@Column(name = "BARCODE", length = 1000)
	public String getBarcode() {
		return this.barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CRTDAT", length = 7)
	public Date getCrtdat() {
		return this.crtdat;
	}

	public void setCrtdat(Date crtdat) {
		this.crtdat = crtdat;
	}

	@Column(name = "CRE_APPL_ID", length = 20)
	public String getCreApplId() {
		return this.creApplId;
	}

	public void setCreApplId(String creApplId) {
		this.creApplId = creApplId;
	}

	@Column(name = "CREATOR", length = 20)
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "UPDDAT", length = 7)
	public Date getUpddat() {
		return this.upddat;
	}

	public void setUpddat(Date upddat) {
		this.upddat = upddat;
	}

	@Column(name = "MODIFIER", length = 20)
	public String getModifier() {
		return this.modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	@Column(name = "UPD_APPL_ID", length = 4)
	public String getUpdApplId() {
		return this.updApplId;
	}

	public void setUpdApplId(String updApplId) {
		this.updApplId = updApplId;
	}

	@Column(name = "PROGNO", length = 20)
	public String getProgno() {
		return this.progno;
	}

	public void setProgno(String progno) {
		this.progno = progno;
	}

	@Column(name = "L_ADDR", length = 1000)
	public String getLAddr() {
		return this.lAddr;
	}

	public void setLAddr(String lAddr) {
		this.lAddr = lAddr;
	}

	@Column(name = "E_ADDR", length = 1000)
	public String getEAddr() {
		return this.eAddr;
	}

	public void setEAddr(String eAddr) {
		this.eAddr = eAddr;
	}

	@Column(name = "CARD_COUNTRY", length = 20)
	public String getCardCountry() {
		return this.cardCountry;
	}

	public void setCardCountry(String cardCountry) {
		this.cardCountry = cardCountry;
	}

	@Column(name = "L_LAST_NAME", length = 300)
	public String getLLastName() {
		return this.lLastName;
	}

	public void setLLastName(String lLastName) {
		this.lLastName = lLastName;
	}

	@Column(name = "L_MIDDLE_NAME1", length = 300)
	public String getLMiddleName1() {
		return this.lMiddleName1;
	}

	public void setLMiddleName1(String lMiddleName1) {
		this.lMiddleName1 = lMiddleName1;
	}

	@Column(name = "L_MIDDLE_NAME2", length = 300)
	public String getLMiddleName2() {
		return this.lMiddleName2;
	}

	public void setLMiddleName2(String lMiddleName2) {
		this.lMiddleName2 = lMiddleName2;
	}

	@Column(name = "L_MIDDLE_NAME3", length = 300)
	public String getLMiddleName3() {
		return this.lMiddleName3;
	}

	public void setLMiddleName3(String lMiddleName3) {
		this.lMiddleName3 = lMiddleName3;
	}

	@Column(name = "L_MIDDLE_NAME4", length = 300)
	public String getLMiddleName4() {
		return this.lMiddleName4;
	}

	public void setLMiddleName4(String lMiddleName4) {
		this.lMiddleName4 = lMiddleName4;
	}

	@Column(name = "CPRNO", length = 50)
	public String getCprno() {
		return this.cprno;
	}

	public void setCprno(String cprno) {
		this.cprno = cprno;
	}

	@Column(name = "E_FIRST_NAME", length = 300)
	public String getEFirstName() {
		return this.eFirstName;
	}

	public void setEFirstName(String eFirstName) {
		this.eFirstName = eFirstName;
	}

	@Column(name = "E_LAST_NAME", length = 300)
	public String getELastName() {
		return this.eLastName;
	}

	public void setELastName(String eLastName) {
		this.eLastName = eLastName;
	}

	@Column(name = "E_MIDDLE_NAME1", length = 300)
	public String getEMiddleName1() {
		return this.eMiddleName1;
	}

	public void setEMiddleName1(String eMiddleName1) {
		this.eMiddleName1 = eMiddleName1;
	}

	@Column(name = "E_MIDDLE_NAME2", length = 300)
	public String getEMiddleName2() {
		return this.eMiddleName2;
	}

	public void setEMiddleName2(String eMiddleName2) {
		this.eMiddleName2 = eMiddleName2;
	}

	@Column(name = "E_MIDDLE_NAME3", length = 300)
	public String getEMiddleName3() {
		return this.eMiddleName3;
	}

	public void setEMiddleName3(String eMiddleName3) {
		this.eMiddleName3 = eMiddleName3;
	}

	@Column(name = "E_MIDDLE_NAME4", length = 300)
	public String getEMiddleName4() {
		return this.eMiddleName4;
	}

	public void setEMiddleName4(String eMiddleName4) {
		this.eMiddleName4 = eMiddleName4;
	}

	@Column(name = "FLATNO", length = 100)
	public String getFlatno() {
		return this.flatno;
	}

	public void setFlatno(String flatno) {
		this.flatno = flatno;
	}

	@Column(name = "BLDG_ALPHA", length = 100)
	public String getBldgAlpha() {
		return this.bldgAlpha;
	}

	public void setBldgAlpha(String bldgAlpha) {
		this.bldgAlpha = bldgAlpha;
	}

	@Column(name = "L_BLDG_ALPHA", length = 100)
	public String getLBldgAlpha() {
		return this.lBldgAlpha;
	}

	public void setLBldgAlpha(String lBldgAlpha) {
		this.lBldgAlpha = lBldgAlpha;
	}

	@Column(name = "ROADNO", length = 50)
	public String getRoadno() {
		return this.roadno;
	}

	public void setRoadno(String roadno) {
		this.roadno = roadno;
	}

	@Column(name = "ROAD_NAME", length = 100)
	public String getRoadName() {
		return this.roadName;
	}

	public void setRoadName(String roadName) {
		this.roadName = roadName;
	}

	@Column(name = "L_ROAD_NAME", length = 100)
	public String getLRoadName() {
		return this.lRoadName;
	}

	public void setLRoadName(String lRoadName) {
		this.lRoadName = lRoadName;
	}

	@Column(name = "BLOCK_NAME", length = 100)
	public String getBlockName() {
		return this.blockName;
	}

	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}

	@Column(name = "L_BLOCK_NAME", length = 100)
	public String getLBlockName() {
		return this.lBlockName;
	}

	public void setLBlockName(String lBlockName) {
		this.lBlockName = lBlockName;
	}

	@Column(name = "GOVERNATE_NO", length = 100)
	public String getGovernateNo() {
		return this.governateNo;
	}

	public void setGovernateNo(String governateNo) {
		this.governateNo = governateNo;
	}

	@Column(name = "E_GOVERNATE_NAME", length = 200)
	public String getEGovernateName() {
		return this.eGovernateName;
	}

	public void setEGovernateName(String eGovernateName) {
		this.eGovernateName = eGovernateName;
	}

	@Column(name = "L_GOVERNATE_NAME", length = 200)
	public String getLGovernateName() {
		return this.lGovernateName;
	}

	public void setLGovernateName(String lGovernateName) {
		this.lGovernateName = lGovernateName;
	}

	@Column(name = "L_EMPLOYER_NAME1", length = 200)
	public String getLEmployerName1() {
		return this.lEmployerName1;
	}

	public void setLEmployerName1(String lEmployerName1) {
		this.lEmployerName1 = lEmployerName1;
	}

	@Column(name = "L_EMPLOYER_NAME2", length = 200)
	public String getLEmployerName2() {
		return this.lEmployerName2;
	}

	public void setLEmployerName2(String lEmployerName2) {
		this.lEmployerName2 = lEmployerName2;
	}

	@Column(name = "L_EMPLOYER_NAME3", length = 200)
	public String getLEmployerName3() {
		return this.lEmployerName3;
	}

	public void setLEmployerName3(String lEmployerName3) {
		this.lEmployerName3 = lEmployerName3;
	}

	@Column(name = "L_EMPLOYER_NAME4", length = 200)
	public String getLEmployerName4() {
		return this.lEmployerName4;
	}

	public void setLEmployerName4(String lEmployerName4) {
		this.lEmployerName4 = lEmployerName4;
	}

	@Column(name = "L_LATEST_EDUCDEGREE", length = 200)
	public String getLLatestEducdegree() {
		return this.lLatestEducdegree;
	}

	public void setLLatestEducdegree(String lLatestEducdegree) {
		this.lLatestEducdegree = lLatestEducdegree;
	}

	@Column(name = "E_LATEST_EDUCDEGREE", length = 200)
	public String getELatestEducdegree() {
		return this.eLatestEducdegree;
	}

	public void setELatestEducdegree(String eLatestEducdegree) {
		this.eLatestEducdegree = eLatestEducdegree;
	}

	@Column(name = "L_OCCUP_DESCR1", length = 200)
	public String getLOccupDescr1() {
		return this.lOccupDescr1;
	}

	public void setLOccupDescr1(String lOccupDescr1) {
		this.lOccupDescr1 = lOccupDescr1;
	}

	@Column(name = "L_OCCUP_DESCR2", length = 200)
	public String getLOccupDescr2() {
		return this.lOccupDescr2;
	}

	public void setLOccupDescr2(String lOccupDescr2) {
		this.lOccupDescr2 = lOccupDescr2;
	}

	@Column(name = "L_OCCUP_DESCR3", length = 200)
	public String getLOccupDescr3() {
		return this.lOccupDescr3;
	}

	public void setLOccupDescr3(String lOccupDescr3) {
		this.lOccupDescr3 = lOccupDescr3;
	}

	@Column(name = "L_OCCUP_DESCR4", length = 200)
	public String getLOccupDescr4() {
		return this.lOccupDescr4;
	}

	public void setLOccupDescr4(String lOccupDescr4) {
		this.lOccupDescr4 = lOccupDescr4;
	}

	@Column(name = "E_OCCUP_DESCR1", length = 200)
	public String getEOccupDescr1() {
		return this.eOccupDescr1;
	}

	public void setEOccupDescr1(String eOccupDescr1) {
		this.eOccupDescr1 = eOccupDescr1;
	}

	@Column(name = "E_OCCUP_DESCR2", length = 200)
	public String getEOccupDescr2() {
		return this.eOccupDescr2;
	}

	public void setEOccupDescr2(String eOccupDescr2) {
		this.eOccupDescr2 = eOccupDescr2;
	}

	@Column(name = "E_OCCUP_DESCR3", length = 200)
	public String getEOccupDescr3() {
		return this.eOccupDescr3;
	}

	public void setEOccupDescr3(String eOccupDescr3) {
		this.eOccupDescr3 = eOccupDescr3;
	}

	@Column(name = "E_OCCUP_DESCR4", length = 200)
	public String getEOccupDescr4() {
		return this.eOccupDescr4;
	}

	public void setEOccupDescr4(String eOccupDescr4) {
		this.eOccupDescr4 = eOccupDescr4;
	}

	@Column(name = "L_SPONSOR_NAME", length = 200)
	public String getLSponsorName() {
		return this.lSponsorName;
	}

	public void setLSponsorName(String lSponsorName) {
		this.lSponsorName = lSponsorName;
	}

	@Column(name = "CLEARING_AGENT_IND", length = 10)
	public String getClearingAgentInd() {
		return this.clearingAgentInd;
	}

	public void setClearingAgentInd(String clearingAgentInd) {
		this.clearingAgentInd = clearingAgentInd;
	}

	@Column(name = "EMPLOYER_NAME1", length = 100)
	public String getEmployerName1() {
		return this.employerName1;
	}

	public void setEmployerName1(String employerName1) {
		this.employerName1 = employerName1;
	}

	@Column(name = "EMPLOYER_NAME2", length = 100)
	public String getEmployerName2() {
		return this.employerName2;
	}

	public void setEmployerName2(String employerName2) {
		this.employerName2 = employerName2;
	}

	@Column(name = "EMPLOYER_NAME3", length = 100)
	public String getEmployerName3() {
		return this.employerName3;
	}

	public void setEmployerName3(String employerName3) {
		this.employerName3 = employerName3;
	}

	@Column(name = "EMPLOYER_NAME4", length = 100)
	public String getEmployerName4() {
		return this.employerName4;
	}

	public void setEmployerName4(String employerName4) {
		this.employerName4 = employerName4;
	}

	@Column(name = "EMPLOYER_NO1", length = 100)
	public String getEmployerNo1() {
		return this.employerNo1;
	}

	public void setEmployerNo1(String employerNo1) {
		this.employerNo1 = employerNo1;
	}

	@Column(name = "EMPLOYER_NO2", length = 100)
	public String getEmployerNo2() {
		return this.employerNo2;
	}

	public void setEmployerNo2(String employerNo2) {
		this.employerNo2 = employerNo2;
	}

	@Column(name = "EMPLOYER_NO3", length = 100)
	public String getEmployerNo3() {
		return this.employerNo3;
	}

	public void setEmployerNo3(String employerNo3) {
		this.employerNo3 = employerNo3;
	}

	@Column(name = "EMPLOYER_NO4", length = 100)
	public String getEmployerNo4() {
		return this.employerNo4;
	}

	public void setEmployerNo4(String employerNo4) {
		this.employerNo4 = employerNo4;
	}

	@Column(name = "EMPLOYER_FLAG1", length = 100)
	public String getEmployerFlag1() {
		return this.employerFlag1;
	}

	public void setEmployerFlag1(String employerFlag1) {
		this.employerFlag1 = employerFlag1;
	}

	@Column(name = "EMPLOYER_FLAG2", length = 100)
	public String getEmployerFlag2() {
		return this.employerFlag2;
	}

	public void setEmployerFlag2(String employerFlag2) {
		this.employerFlag2 = employerFlag2;
	}

	@Column(name = "EMPLOYER_FLAG3", length = 100)
	public String getEmployerFlag3() {
		return this.employerFlag3;
	}

	public void setEmployerFlag3(String employerFlag3) {
		this.employerFlag3 = employerFlag3;
	}

	@Column(name = "EMPLOYER_FLAG4", length = 100)
	public String getEmployerFlag4() {
		return this.employerFlag4;
	}

	public void setEmployerFlag4(String employerFlag4) {
		this.employerFlag4 = employerFlag4;
	}

	@Column(name = "LABOR_FORCE_PARTICIP", length = 50)
	public String getLaborForceParticip() {
		return this.laborForceParticip;
	}

	public void setLaborForceParticip(String laborForceParticip) {
		this.laborForceParticip = laborForceParticip;
	}

	@Column(name = "SPONSOR_CPRNOOR_UNITNO", length = 50)
	public String getSponsorCprnoorUnitno() {
		return this.sponsorCprnoorUnitno;
	}

	public void setSponsorCprnoorUnitno(String sponsorCprnoorUnitno) {
		this.sponsorCprnoorUnitno = sponsorCprnoorUnitno;
	}

	@Column(name = "SPONSOR_FLAG", length = 50)
	public String getSponsorFlag() {
		return this.sponsorFlag;
	}

	public void setSponsorFlag(String sponsorFlag) {
		this.sponsorFlag = sponsorFlag;
	}

	@Column(name = "SPONSOR_NAME", length = 100)
	public String getSponsorName() {
		return this.sponsorName;
	}

	public void setSponsorName(String sponsorName) {
		this.sponsorName = sponsorName;
	}

	@Column(name = "E_LFP_NAME", length = 200)
	public String getELfpName() {
		return this.eLfpName;
	}

	public void setELfpName(String eLfpName) {
		this.eLfpName = eLfpName;
	}

	@Column(name = "L_LFP_NAME", length = 200)
	public String getLLfpName() {
		return this.lLfpName;
	}

	public void setLLfpName(String lLfpName) {
		this.lLfpName = lLfpName;
	}

	@Column(name = "E_COUNTRY_NAME", length = 200)
	public String getECountryName() {
		return this.eCountryName;
	}

	public void setECountryName(String eCountryName) {
		this.eCountryName = eCountryName;
	}

	@Column(name = "L_COUNTRY_NAME", length = 200)
	public String getLCountryName() {
		return this.lCountryName;
	}

	public void setLCountryName(String lCountryName) {
		this.lCountryName = lCountryName;
	}

	@Column(name = "IACO_CODE", length = 100)
	public String getIacoCode() {
		return this.iacoCode;
	}

	public void setIacoCode(String iacoCode) {
		this.iacoCode = iacoCode;
	}

	@Column(name = "E_PLACE_OF_BIRTH", length = 100)
	public String getEPlaceOfBirth() {
		return this.ePlaceOfBirth;
	}

	public void setEPlaceOfBirth(String ePlaceOfBirth) {
		this.ePlaceOfBirth = ePlaceOfBirth;
	}

	@Column(name = "L_PLACE_OF_BIRTH", length = 100)
	public String getLPlaceOfBirth() {
		return this.lPlaceOfBirth;
	}

	public void setLPlaceOfBirth(String lPlaceOfBirth) {
		this.lPlaceOfBirth = lPlaceOfBirth;
	}

	@Column(name = "COUNTRY_OF_BIRTH", length = 100)
	public String getCountryOfBirth() {
		return this.countryOfBirth;
	}

	public void setCountryOfBirth(String countryOfBirth) {
		this.countryOfBirth = countryOfBirth;
	}

	@Column(name = "PASSPORT_NO", length = 100)
	public String getPassportNo() {
		return this.passportNo;
	}

	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}

	@Column(name = "PASSPORT_TYPE", length = 100)
	public String getPassportType() {
		return this.passportType;
	}

	public void setPassportType(String passportType) {
		this.passportType = passportType;
	}

	@Column(name = "PASSPORT_SEQ_NO", length = 20)
	public String getPassportSeqNo() {
		return this.passportSeqNo;
	}

	public void setPassportSeqNo(String passportSeqNo) {
		this.passportSeqNo = passportSeqNo;
	}

	@Column(name = "PASSPORT_EXPIRY_DATE", length = 20)
	public String getPassportExpiryDate() {
		return this.passportExpiryDate;
	}

	public void setPassportExpiryDate(String passportExpiryDate) {
		this.passportExpiryDate = passportExpiryDate;
	}

	@Column(name = "PASSPORT_ISSUE_DATE", length = 20)
	public String getPassportIssueDate() {
		return this.passportIssueDate;
	}

	public void setPassportIssueDate(String passportIssueDate) {
		this.passportIssueDate = passportIssueDate;
	}

	@Column(name = "VISA_NO", length = 100)
	public String getVisaNo() {
		return this.visaNo;
	}

	public void setVisaNo(String visaNo) {
		this.visaNo = visaNo;
	}

	@Column(name = "VISA_EXPIRY_DATE", length = 20)
	public String getVisaExpiryDate() {
		return this.visaExpiryDate;
	}

	public void setVisaExpiryDate(String visaExpiryDate) {
		this.visaExpiryDate = visaExpiryDate;
	}

	@Column(name = "VISA_TYPE", length = 100)
	public String getVisaType() {
		return this.visaType;
	}

	public void setVisaType(String visaType) {
		this.visaType = visaType;
	}

	@Column(name = "RESIDENCE_PERMIT_NO", length = 100)
	public String getResidencePermitNo() {
		return this.residencePermitNo;
	}

	public void setResidencePermitNo(String residencePermitNo) {
		this.residencePermitNo = residencePermitNo;
	}

	@Column(name = "RESIDENCE_PERMIT_TYPE", length = 100)
	public String getResidencePermitType() {
		return this.residencePermitType;
	}

	public void setResidencePermitType(String residencePermitType) {
		this.residencePermitType = residencePermitType;
	}

	@Column(name = "RESIDENT_TYPE", length = 100)
	public String getResidentType() {
		return this.residentType;
	}

	public void setResidentType(String residentType) {
		this.residentType = residentType;
	}

	@Column(name = "CARD_VERIF_STATUS", length = 100)
	public String getCardVerifStatus() {
		return this.cardVerifStatus;
	}

	public void setCardVerifStatus(String cardVerifStatus) {
		this.cardVerifStatus = cardVerifStatus;
	}

	@Column(name = "EMPLOYEMENT_FLAG1", length = 100)
	public String getEmploymentFlag1() {
		return employmentFlag1;
	}

	public void setEmploymentFlag1(String employmentFlag1) {
		this.employmentFlag1 = employmentFlag1;
	}

	@Column(name = "EMPLOYEMENT_FLAG2", length = 100)
	public String getEmploymentFlag2() {
		return employmentFlag2;
	}

	public void setEmploymentFlag2(String employmentFlag2) {
		this.employmentFlag2 = employmentFlag2;
	}

	@Column(name = "EMPLOYEMENT_FLAG3", length = 100)
	public String getEmploymentFlag3() {
		return employmentFlag3;
	}

	public void setEmploymentFlag3(String employmentFlag3) {
		this.employmentFlag3 = employmentFlag3;
	}

	@Column(name = "EMPLOYEMENT_FLAG4", length = 100)
	public String getEmploymentFlag4() {
		return employmentFlag4;
	}

	public void setEmploymentFlag4(String employmentFlag4) {
		this.employmentFlag4 = employmentFlag4;
	}

	
}