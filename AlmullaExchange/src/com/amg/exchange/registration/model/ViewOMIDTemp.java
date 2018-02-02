package com.amg.exchange.registration.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="VW_EX_ID_TEMP")
public class ViewOMIDTemp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String civilID;
	private String cardSerialNo;
	private String cardLFName;
	private String cardName;
	private String cardNtlcdAr;
	private String cardNtCd;
	private String cardGenderAr;
	private String cardGender;
	private String cardCurrAddress;
	private String cardSponsName;
	private String cardSponsNameAr;
	private String cardPassNo;
	private String cardpassCountry;
	private String cardHldrType;
	private Date cardExpireDate;
	private Date cardBirthDate;
	private Date visaExpiryDate;
	private Date passportExpiryDate;
	private String createBy;
	private BigDecimal cardNtcdId;
	
	@Id
	@Column(name="CIVIL_ID")
	public String getCivilID() {
		return civilID;
	}
	public void setCivilID(String civilID) {
		this.civilID = civilID;
	}
	
	@Column(name="CARD_SERIALNO")
	public String getCardSerialNo() {
		return cardSerialNo;
	}
	public void setCardSerialNo(String cardSerialNo) {
		this.cardSerialNo = cardSerialNo;
	}
	
	@Column(name="CARD_LFUNAME")
	public String getCardLFName() {
		return cardLFName;
	}
	public void setCardLFName(String cardLFName) {
		this.cardLFName = cardLFName;
	}
	
	@Column(name="CARD_NAME")
	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	
	@Column(name="CARD_NTCD_AR")
	public String getCardNtlcdAr() {
		return cardNtlcdAr;
	}
	public void setCardNtlcdAr(String cardNtlcdAr) {
		this.cardNtlcdAr = cardNtlcdAr;
	}
	
	@Column(name="CARD_NTCD")
	public String getCardNtCd() {
		return cardNtCd;
	}
	public void setCardNtCd(String cardNtCd) {
		this.cardNtCd = cardNtCd;
	}
	
	@Column(name="CARD_GENDER_AR")
	public String getCardGenderAr() {
		return cardGenderAr;
	}
	public void setCardGenderAr(String cardGenderAr) {
		this.cardGenderAr = cardGenderAr;
	}
	
	@Column(name="CARD_GENDER")
	public String getCardGender() {
		return cardGender;
	}
	public void setCardGender(String cardGender) {
		this.cardGender = cardGender;
	}
	
	@Column(name="CARD_CURRADDR")
	public String getCardCurrAddress() {
		return cardCurrAddress;
	}
	public void setCardCurrAddress(String cardCurrAddress) {
		this.cardCurrAddress = cardCurrAddress;
	}
	
	@Column(name="CARD_SPONSNAME")
	public String getCardSponsName() {
		return cardSponsName;
	}
	public void setCardSponsName(String cardSponsName) {
		this.cardSponsName = cardSponsName;
	}
	
	@Column(name="CARD_SPONSNAME_AR")
	public String getCardSponsNameAr() {
		return cardSponsNameAr;
	}
	public void setCardSponsNameAr(String cardSponsNameAr) {
		this.cardSponsNameAr = cardSponsNameAr;
	}
	
	@Column(name="CARD_PASSNO")
	public String getCardPassNo() {
		return cardPassNo;
	}
	public void setCardPassNo(String cardPassNo) {
		this.cardPassNo = cardPassNo;
	}
	
	@Column(name="CARD_PASSCNTRY")
	public String getCardpassCountry() {
		return cardpassCountry;
	}
	public void setCardpassCountry(String cardpassCountry) {
		this.cardpassCountry = cardpassCountry;
	}
	
	@Column(name="CARD_HLDRTYP")
	public String getCardHldrType() {
		return cardHldrType;
	}
	public void setCardHldrType(String cardHldrType) {
		this.cardHldrType = cardHldrType;
	}
	
	@Column(name="CARD_EXPIRE_DT")
	public Date getCardExpireDate() {
		return cardExpireDate;
	}
	public void setCardExpireDate(Date cardExpireDate) {
		this.cardExpireDate = cardExpireDate;
	}
	
	@Column(name="CARD_BIRTHDAT")
	public Date getCardBirthDate() {
		return cardBirthDate;
	}
	public void setCardBirthDate(Date cardBirthDate) {
		this.cardBirthDate = cardBirthDate;
	}
	
	@Column(name="CARD_VISAEXP")
	public Date getVisaExpiryDate() {
		return visaExpiryDate;
	}
	public void setVisaExpiryDate(Date visaExpiryDate) {
		this.visaExpiryDate = visaExpiryDate;
	}
	
	@Column(name="CARD_PASSEXPDAT")
	public Date getPassportExpiryDate() {
		return passportExpiryDate;
	}
	public void setPassportExpiryDate(Date passportExpiryDate) {
		this.passportExpiryDate = passportExpiryDate;
	}
	
	@Column(name="CREATOR")
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	
	@Column(name="CARD_NTCD_ID")
	public BigDecimal getCardNtcdId() {
		return cardNtcdId;
	}
	public void setCardNtcdId(BigDecimal cardNtcdId) {
		this.cardNtcdId = cardNtcdId;
	}

}
