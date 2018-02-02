package com.amg.exchange.registration.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@Table(name="CUSMAS")
public class EMOSCustomer implements Serializable {
	private static final long serialVersionUID = 1L;

	private String actcod;

	@Column(name="AML_ACE_STS")
	private String amlAceSts;

	@Column(name="AML_CUST_SCAN_STS")
	private String amlCustScanSts;

	@Column(name="AML_ORAUSER")
	private String amlOrauser;

	@Column(name="AML_REMARKS")
	private String amlRemarks;

	@Column(name="AML_STR")
	private BigDecimal amlStr;

	@Column(name="AML_STS")
	private String amlSts;

	@Temporal(TemporalType.DATE)
	@Column(name="AML_UPDDAT")
	private Date amlUpddat;

	@Column(name="ANNUAL_INC")
	private BigDecimal annualInc;

	@Column(name="AR_FUNAME")
	private String arFuname;

	private String areano;

	private BigDecimal assocod;

	private String barcode;

	@Column(name="BCO_REMARKS")
	private String bcoRemarks;

	@Temporal(TemporalType.DATE)
	private Date birthdat;

	private String bldgno;

	private String blist;

	@Column(name="\"BLOCK\"")
	private String block;

	private String civilid;

	@Column(name="CORP_CUSREF")
	private BigDecimal corpCusref;

	private String creator;

	private String crno;

	@Temporal(TemporalType.DATE)
	private Date crtdat;

	@Column(name="CUS_COMP_PROF")
	private String cusCompProf;

	@Column(name="CUS_SIGN")
	private String cusSign;

	@Id
	private BigDecimal cusref;

	private String custyp;

	@Column(name="DESIG_CODE")
	private String desigCode;

	/*@Column(name="DEVICE_ID")
	private String deviceId;*/

	/*@Column(name="DEVICE_TYPE")
	private String deviceType;*/

	private String email;

	@Column(name="EMPL_GEN")
	private String emplGen;

	@Column(name="EMPL_NXTNUM")
	private BigDecimal emplNxtnum;

	private String emplnm;

	private String emplty;

	private String emplyn;

	@Column(name="EVNT_ADDR")
	private String evntAddr;

	@Temporal(TemporalType.DATE)
	@Column(name="F_REMTDT")
	private Date fRemtdt;

	private String fax;

	/*@Column(name="FING_IMG")
	private Object fingImg;*/

	@Column(name="FING_IMG_SIZE")
	private BigDecimal fingImgSize;

	private String flatno;

	private String funame;

	private String gender;

	@Column(name="HOME_CITY")
	private String homeCity;

	@Column(name="HOME_CNTY")
	private String homeCnty;

	@Column(name="HOME_DIST")
	private String homeDist;

	@Column(name="HOME_STATE")
	private String homeState;

	private String hpin;

	@Temporal(TemporalType.DATE)
	@Column(name="IDEXP_MDAT")
	private Date idexpMdat;

	@Column(name="IDEXP_MUSR")
	private String idexpMusr;

	@Temporal(TemporalType.DATE)
	private Date idexpdt;

	
	@Column(name="IDNO")
	private String idno;

	@Column(name="IDTYP")
	private String idtyp;

	@Column(name="IMG_VERFBY")
	private String imgVerfby;

	@Temporal(TemporalType.DATE)
	@Column(name="IMG_VERFDAT")
	private Date imgVerfdat;

	@Column(name="INTR_COMCOD")
	private BigDecimal intrComcod;

	@Column(name="INTR_CUSREF")
	private BigDecimal intrCusref;

	@Temporal(TemporalType.DATE)
	@Column(name="INTR_DATE")
	private Date intrDate;

	@Column(name="INTR_TRNFYR")
	private BigDecimal intrTrnfyr;

	@Column(name="INTR_TRNREF")
	private BigDecimal intrTrnref;

	@Temporal(TemporalType.DATE)
	@Column(name="JAVA_CUST")
	private Date javaCust;

	@Column(name="KIOSK_PIN")
	private String kioskPin;

	@Temporal(TemporalType.DATE)
	@Column(name="L_REMTDT")
	private Date lRemtdt;

	@Temporal(TemporalType.DATE)
	@Column(name="LAST_TRNDAT")
	private Date lastTrndat;

	@Column(name="LCARD_LOCCOD")
	private BigDecimal lcardLoccod;

	private BigDecimal loccod;

	@Column(name="LOYALTY_POINTS")
	private BigDecimal loyaltyPoints;

	@Column(name="MED_INS")
	private String medIns;

	private String mobno;

	private String modifier;

	@Column(name="MOT_MAIDEN")
	private String motMaiden;

	private String natcod;

	private String nickname;

	@Column(name="OFF_EXT")
	private BigDecimal offExt;

	@Column(name="OFF_PHONE")
	private String offPhone;

	@Temporal(TemporalType.DATE)
	@Column(name="OLD_IDEXPDT")
	private Date oldIdexpdt;

	@Column(name="OWNER_CIVILID")
	private String ownerCivilid;

	@Temporal(TemporalType.DATE)
	@Column(name="OWNER_DOB")
	private Date ownerDob;

	@Column(name="OWNER_MOBNO")
	private String ownerMobno;

	@Column(name="OWNER_NAME")
	private String ownerName;

	@Column(name="OWNER_NATCOD")
	private String ownerNatcod;

	@Column(name="OWNER_OFF_PHONE")
	private String ownerOffPhone;

	@Column(name="OWNER_PASSPORT")
	private String ownerPassport;

	private String passport;

	@Column(name="PEPS_IND")
	private String pepsInd;

	@Column(name="PEPS_USER")
	private String pepsUser;

	private String pinno;

	private String pono;

	private BigDecimal posicod;

	@Column(name="POST_AREA")
	private String postArea;

	private BigDecimal profcod;

	private String progno;

	private String recsts;

	private String remarks;

	@Column(name="RES_PHONE")
	private String resPhone;

	private String rombrch;

	private String romlocd;

	@Column(name="SCAN_REQ")
	private String scanReq;

	@Column(name="SD_CUSREF")
	private BigDecimal sdCusref;

	@Temporal(TemporalType.DATE)
	@Column(name="SMART_CARD")
	private Date smartCard;

	@Column(name="SMS_SEQ")
	private BigDecimal smsSeq;

	@Column(name="SRCE_TYP")
	private String srceTyp;

	private String strtno;

	private String title;

	@Temporal(TemporalType.DATE)
	private Date upddat;

	@Column(name="WS_CBKNO")
	private String wsCbkno;

	@Temporal(TemporalType.DATE)
	@Column(name="WS_EFFDAT")
	private Date wsEffdat;

	@Column(name="WS_IND")
	private String wsInd;

	public EMOSCustomer() {
	}

	public String getActcod() {
		return this.actcod;
	}

	public void setActcod(String actcod) {
		this.actcod = actcod;
	}

	public String getAmlAceSts() {
		return this.amlAceSts;
	}

	public void setAmlAceSts(String amlAceSts) {
		this.amlAceSts = amlAceSts;
	}

	public String getAmlCustScanSts() {
		return this.amlCustScanSts;
	}

	public void setAmlCustScanSts(String amlCustScanSts) {
		this.amlCustScanSts = amlCustScanSts;
	}

	public String getAmlOrauser() {
		return this.amlOrauser;
	}

	public void setAmlOrauser(String amlOrauser) {
		this.amlOrauser = amlOrauser;
	}

	public String getAmlRemarks() {
		return this.amlRemarks;
	}

	public void setAmlRemarks(String amlRemarks) {
		this.amlRemarks = amlRemarks;
	}

	public BigDecimal getAmlStr() {
		return this.amlStr;
	}

	public void setAmlStr(BigDecimal amlStr) {
		this.amlStr = amlStr;
	}

	public String getAmlSts() {
		return this.amlSts;
	}

	public void setAmlSts(String amlSts) {
		this.amlSts = amlSts;
	}

	public Date getAmlUpddat() {
		return this.amlUpddat;
	}

	public void setAmlUpddat(Date amlUpddat) {
		this.amlUpddat = amlUpddat;
	}

	public BigDecimal getAnnualInc() {
		return this.annualInc;
	}

	public void setAnnualInc(BigDecimal annualInc) {
		this.annualInc = annualInc;
	}

	public String getArFuname() {
		return this.arFuname;
	}

	public void setArFuname(String arFuname) {
		this.arFuname = arFuname;
	}

	public String getAreano() {
		return this.areano;
	}

	public void setAreano(String areano) {
		this.areano = areano;
	}

	public BigDecimal getAssocod() {
		return this.assocod;
	}

	public void setAssocod(BigDecimal assocod) {
		this.assocod = assocod;
	}

	public String getBarcode() {
		return this.barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getBcoRemarks() {
		return this.bcoRemarks;
	}

	public void setBcoRemarks(String bcoRemarks) {
		this.bcoRemarks = bcoRemarks;
	}

	public Date getBirthdat() {
		return this.birthdat;
	}

	public void setBirthdat(Date birthdat) {
		this.birthdat = birthdat;
	}

	public String getBldgno() {
		return this.bldgno;
	}

	public void setBldgno(String bldgno) {
		this.bldgno = bldgno;
	}

	public String getBlist() {
		return this.blist;
	}

	public void setBlist(String blist) {
		this.blist = blist;
	}

	public String getBlock() {
		return this.block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public String getCivilid() {
		return this.civilid;
	}

	public void setCivilid(String civilid) {
		this.civilid = civilid;
	}

	public BigDecimal getCorpCusref() {
		return this.corpCusref;
	}

	public void setCorpCusref(BigDecimal corpCusref) {
		this.corpCusref = corpCusref;
	}

	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCrno() {
		return this.crno;
	}

	public void setCrno(String crno) {
		this.crno = crno;
	}

	public Date getCrtdat() {
		return this.crtdat;
	}

	public void setCrtdat(Date crtdat) {
		this.crtdat = crtdat;
	}

	public String getCusCompProf() {
		return this.cusCompProf;
	}

	public void setCusCompProf(String cusCompProf) {
		this.cusCompProf = cusCompProf;
	}

	public String getCusSign() {
		return this.cusSign;
	}

	public void setCusSign(String cusSign) {
		this.cusSign = cusSign;
	}

	public BigDecimal getCusref() {
		return this.cusref;
	}

	public void setCusref(BigDecimal cusref) {
		this.cusref = cusref;
	}

	public String getCustyp() {
		return this.custyp;
	}

	public void setCustyp(String custyp) {
		this.custyp = custyp;
	}

	public String getDesigCode() {
		return this.desigCode;
	}

	public void setDesigCode(String desigCode) {
		this.desigCode = desigCode;
	}
/*
	public String getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}*/

	/*public String getDeviceType() {
		return this.deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}*/

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmplGen() {
		return this.emplGen;
	}

	public void setEmplGen(String emplGen) {
		this.emplGen = emplGen;
	}

	public BigDecimal getEmplNxtnum() {
		return this.emplNxtnum;
	}

	public void setEmplNxtnum(BigDecimal emplNxtnum) {
		this.emplNxtnum = emplNxtnum;
	}

	public String getEmplnm() {
		return this.emplnm;
	}

	public void setEmplnm(String emplnm) {
		this.emplnm = emplnm;
	}

	public String getEmplty() {
		return this.emplty;
	}

	public void setEmplty(String emplty) {
		this.emplty = emplty;
	}

	public String getEmplyn() {
		return this.emplyn;
	}

	public void setEmplyn(String emplyn) {
		this.emplyn = emplyn;
	}

	public String getEvntAddr() {
		return this.evntAddr;
	}

	public void setEvntAddr(String evntAddr) {
		this.evntAddr = evntAddr;
	}

	public Date getFRemtdt() {
		return this.fRemtdt;
	}

	public void setFRemtdt(Date fRemtdt) {
		this.fRemtdt = fRemtdt;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	/*public Object getFingImg() {
		return this.fingImg;
	}

	public void setFingImg(Object fingImg) {
		this.fingImg = fingImg;
	}*/

	public BigDecimal getFingImgSize() {
		return this.fingImgSize;
	}

	public void setFingImgSize(BigDecimal fingImgSize) {
		this.fingImgSize = fingImgSize;
	}

	public String getFlatno() {
		return this.flatno;
	}

	public void setFlatno(String flatno) {
		this.flatno = flatno;
	}

	public String getFuname() {
		return this.funame;
	}

	public void setFuname(String funame) {
		this.funame = funame;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getHomeCity() {
		return this.homeCity;
	}

	public void setHomeCity(String homeCity) {
		this.homeCity = homeCity;
	}

	public String getHomeCnty() {
		return this.homeCnty;
	}

	public void setHomeCnty(String homeCnty) {
		this.homeCnty = homeCnty;
	}

	public String getHomeDist() {
		return this.homeDist;
	}

	public void setHomeDist(String homeDist) {
		this.homeDist = homeDist;
	}

	public String getHomeState() {
		return this.homeState;
	}

	public void setHomeState(String homeState) {
		this.homeState = homeState;
	}

	public String getHpin() {
		return this.hpin;
	}

	public void setHpin(String hpin) {
		this.hpin = hpin;
	}

	public Date getIdexpMdat() {
		return this.idexpMdat;
	}

	public void setIdexpMdat(Date idexpMdat) {
		this.idexpMdat = idexpMdat;
	}

	public String getIdexpMusr() {
		return this.idexpMusr;
	}

	public void setIdexpMusr(String idexpMusr) {
		this.idexpMusr = idexpMusr;
	}

	public Date getIdexpdt() {
		return this.idexpdt;
	}

	public void setIdexpdt(Date idexpdt) {
		this.idexpdt = idexpdt;
	}

	public String getIdno() {
		return this.idno;
	}

	public void setIdno(String idno) {
		this.idno = idno;
	}

	public String getIdtyp() {
		return this.idtyp;
	}

	public void setIdtyp(String idtyp) {
		this.idtyp = idtyp;
	}

	public String getImgVerfby() {
		return this.imgVerfby;
	}

	public void setImgVerfby(String imgVerfby) {
		this.imgVerfby = imgVerfby;
	}

	public Date getImgVerfdat() {
		return this.imgVerfdat;
	}

	public void setImgVerfdat(Date imgVerfdat) {
		this.imgVerfdat = imgVerfdat;
	}

	public BigDecimal getIntrComcod() {
		return this.intrComcod;
	}

	public void setIntrComcod(BigDecimal intrComcod) {
		this.intrComcod = intrComcod;
	}

	public BigDecimal getIntrCusref() {
		return this.intrCusref;
	}

	public void setIntrCusref(BigDecimal intrCusref) {
		this.intrCusref = intrCusref;
	}

	public Date getIntrDate() {
		return this.intrDate;
	}

	public void setIntrDate(Date intrDate) {
		this.intrDate = intrDate;
	}

	public BigDecimal getIntrTrnfyr() {
		return this.intrTrnfyr;
	}

	public void setIntrTrnfyr(BigDecimal intrTrnfyr) {
		this.intrTrnfyr = intrTrnfyr;
	}

	public BigDecimal getIntrTrnref() {
		return this.intrTrnref;
	}

	public void setIntrTrnref(BigDecimal intrTrnref) {
		this.intrTrnref = intrTrnref;
	}

	public Date getJavaCust() {
		return this.javaCust;
	}

	public void setJavaCust(Date javaCust) {
		this.javaCust = javaCust;
	}

	public String getKioskPin() {
		return this.kioskPin;
	}

	public void setKioskPin(String kioskPin) {
		this.kioskPin = kioskPin;
	}

	public Date getLRemtdt() {
		return this.lRemtdt;
	}

	public void setLRemtdt(Date lRemtdt) {
		this.lRemtdt = lRemtdt;
	}

	public Date getLastTrndat() {
		return this.lastTrndat;
	}

	public void setLastTrndat(Date lastTrndat) {
		this.lastTrndat = lastTrndat;
	}

	public BigDecimal getLcardLoccod() {
		return this.lcardLoccod;
	}

	public void setLcardLoccod(BigDecimal lcardLoccod) {
		this.lcardLoccod = lcardLoccod;
	}

	public BigDecimal getLoccod() {
		return this.loccod;
	}

	public void setLoccod(BigDecimal loccod) {
		this.loccod = loccod;
	}

	public BigDecimal getLoyaltyPoints() {
		return this.loyaltyPoints;
	}

	public void setLoyaltyPoints(BigDecimal loyaltyPoints) {
		this.loyaltyPoints = loyaltyPoints;
	}

	public String getMedIns() {
		return this.medIns;
	}

	public void setMedIns(String medIns) {
		this.medIns = medIns;
	}

	public String getMobno() {
		return this.mobno;
	}

	public void setMobno(String mobno) {
		this.mobno = mobno;
	}

	public String getModifier() {
		return this.modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public String getMotMaiden() {
		return this.motMaiden;
	}

	public void setMotMaiden(String motMaiden) {
		this.motMaiden = motMaiden;
	}

	public String getNatcod() {
		return this.natcod;
	}

	public void setNatcod(String natcod) {
		this.natcod = natcod;
	}

	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public BigDecimal getOffExt() {
		return this.offExt;
	}

	public void setOffExt(BigDecimal offExt) {
		this.offExt = offExt;
	}

	public String getOffPhone() {
		return this.offPhone;
	}

	public void setOffPhone(String offPhone) {
		this.offPhone = offPhone;
	}

	public Date getOldIdexpdt() {
		return this.oldIdexpdt;
	}

	public void setOldIdexpdt(Date oldIdexpdt) {
		this.oldIdexpdt = oldIdexpdt;
	}

	public String getOwnerCivilid() {
		return this.ownerCivilid;
	}

	public void setOwnerCivilid(String ownerCivilid) {
		this.ownerCivilid = ownerCivilid;
	}

	public Date getOwnerDob() {
		return this.ownerDob;
	}

	public void setOwnerDob(Date ownerDob) {
		this.ownerDob = ownerDob;
	}

	public String getOwnerMobno() {
		return this.ownerMobno;
	}

	public void setOwnerMobno(String ownerMobno) {
		this.ownerMobno = ownerMobno;
	}

	public String getOwnerName() {
		return this.ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getOwnerNatcod() {
		return this.ownerNatcod;
	}

	public void setOwnerNatcod(String ownerNatcod) {
		this.ownerNatcod = ownerNatcod;
	}

	public String getOwnerOffPhone() {
		return this.ownerOffPhone;
	}

	public void setOwnerOffPhone(String ownerOffPhone) {
		this.ownerOffPhone = ownerOffPhone;
	}

	public String getOwnerPassport() {
		return this.ownerPassport;
	}

	public void setOwnerPassport(String ownerPassport) {
		this.ownerPassport = ownerPassport;
	}

	public String getPassport() {
		return this.passport;
	}

	public void setPassport(String passport) {
		this.passport = passport;
	}

	public String getPepsInd() {
		return this.pepsInd;
	}

	public void setPepsInd(String pepsInd) {
		this.pepsInd = pepsInd;
	}

	public String getPepsUser() {
		return this.pepsUser;
	}

	public void setPepsUser(String pepsUser) {
		this.pepsUser = pepsUser;
	}

	public String getPinno() {
		return this.pinno;
	}

	public void setPinno(String pinno) {
		this.pinno = pinno;
	}

	public String getPono() {
		return this.pono;
	}

	public void setPono(String pono) {
		this.pono = pono;
	}

	public BigDecimal getPosicod() {
		return this.posicod;
	}

	public void setPosicod(BigDecimal posicod) {
		this.posicod = posicod;
	}

	public String getPostArea() {
		return this.postArea;
	}

	public void setPostArea(String postArea) {
		this.postArea = postArea;
	}

	public BigDecimal getProfcod() {
		return this.profcod;
	}

	public void setProfcod(BigDecimal profcod) {
		this.profcod = profcod;
	}

	public String getProgno() {
		return this.progno;
	}

	public void setProgno(String progno) {
		this.progno = progno;
	}

	public String getRecsts() {
		return this.recsts;
	}

	public void setRecsts(String recsts) {
		this.recsts = recsts;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getResPhone() {
		return this.resPhone;
	}

	public void setResPhone(String resPhone) {
		this.resPhone = resPhone;
	}

	public String getRombrch() {
		return this.rombrch;
	}

	public void setRombrch(String rombrch) {
		this.rombrch = rombrch;
	}

	public String getRomlocd() {
		return this.romlocd;
	}

	public void setRomlocd(String romlocd) {
		this.romlocd = romlocd;
	}

	public String getScanReq() {
		return this.scanReq;
	}

	public void setScanReq(String scanReq) {
		this.scanReq = scanReq;
	}

	public BigDecimal getSdCusref() {
		return this.sdCusref;
	}

	public void setSdCusref(BigDecimal sdCusref) {
		this.sdCusref = sdCusref;
	}

	public Date getSmartCard() {
		return this.smartCard;
	}

	public void setSmartCard(Date smartCard) {
		this.smartCard = smartCard;
	}

	public BigDecimal getSmsSeq() {
		return this.smsSeq;
	}

	public void setSmsSeq(BigDecimal smsSeq) {
		this.smsSeq = smsSeq;
	}

	public String getSrceTyp() {
		return this.srceTyp;
	}

	public void setSrceTyp(String srceTyp) {
		this.srceTyp = srceTyp;
	}

	public String getStrtno() {
		return this.strtno;
	}

	public void setStrtno(String strtno) {
		this.strtno = strtno;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getUpddat() {
		return this.upddat;
	}

	public void setUpddat(Date upddat) {
		this.upddat = upddat;
	}

	public String getWsCbkno() {
		return this.wsCbkno;
	}

	public void setWsCbkno(String wsCbkno) {
		this.wsCbkno = wsCbkno;
	}

	public Date getWsEffdat() {
		return this.wsEffdat;
	}

	public void setWsEffdat(Date wsEffdat) {
		this.wsEffdat = wsEffdat;
	}

	public String getWsInd() {
		return this.wsInd;
	}

	public void setWsInd(String wsInd) {
		this.wsInd = wsInd;
	}

}