package com.amg.exchange.remittance.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "V_EX_SWIFT_MASTER")
public class SwiftMasterView implements Serializable {

	  /**
	   * 
	   */
	  private static final long serialVersionUID = 1L;

	  private BigDecimal serialNumber;
	  private BigDecimal swiftId;
	  private String swiftCountry;
	  private String swiftBank;
	  private String swiftLocation;
	  private String swiftBranch;
	  private String bankName;
	  private String cityName;
	  private String region;
	  private String swiftBIC;
	  private String chipsUID;
	  private String acronymId;
	  private String fedwireId;
	  private String abaId;
	  private String address1;
	  private String address2;
	  private String address3;
	  private BigDecimal countryId;
	  private String countryCode;

	  public SwiftMasterView() {
		    super();
	  }

	  public SwiftMasterView(BigDecimal serialNumber, BigDecimal swiftId, String swiftCountry, String swiftBank, String swiftLocation, String swiftBranch, String bankName, String cityName, String region, String swiftBIC, String chipsUID, String acronymId, String fedwireId, String abaId,
			      String address1, String address2, String address3, BigDecimal countryId, String countryCode) {
		    super();
		    this.serialNumber = serialNumber;
		    this.swiftId = swiftId;
		    this.swiftCountry = swiftCountry;
		    this.swiftBank = swiftBank;
		    this.swiftLocation = swiftLocation;
		    this.swiftBranch = swiftBranch;
		    this.bankName = bankName;
		    this.cityName = cityName;
		    this.region = region;
		    this.swiftBIC = swiftBIC;
		    this.chipsUID = chipsUID;
		    this.acronymId = acronymId;
		    this.fedwireId = fedwireId;
		    this.abaId = abaId;
		    this.address1 = address1;
		    this.address2 = address2;
		    this.address3 = address3;
		    this.countryId = countryId;
		    this.countryCode = countryCode;
	  }

	  @Id
	  @Column(name = "SRNO")
	  public BigDecimal getSerialNumber() {
		    return serialNumber;
	  }

	  public void setSerialNumber(BigDecimal serialNumber) {
		    this.serialNumber = serialNumber;
	  }

	  @Column(name = "SWIFT_ID")
	  public BigDecimal getSwiftId() {
		    return swiftId;
	  }

	  public void setSwiftId(BigDecimal swiftId) {
		    this.swiftId = swiftId;
	  }

	  @Column(name = "SWIFT_COUNTRY")
	  public String getSwiftCountry() {
		    return swiftCountry;
	  }

	  public void setSwiftCountry(String swiftCountry) {
		    this.swiftCountry = swiftCountry;
	  }

	  @Column(name = "SWIFT_BANK")
	  public String getSwiftBank() {
		    return swiftBank;
	  }

	  public void setSwiftBank(String swiftBank) {
		    this.swiftBank = swiftBank;
	  }

	  @Column(name = "SWIFT_LOCATION")
	  public String getSwiftLocation() {
		    return swiftLocation;
	  }

	  public void setSwiftLocation(String swiftLocation) {
		    this.swiftLocation = swiftLocation;
	  }

	  @Column(name = "SWIFT_BRANCH")
	  public String getSwiftBranch() {
		    return swiftBranch;
	  }

	  public void setSwiftBranch(String swiftBranch) {
		    this.swiftBranch = swiftBranch;
	  }

	  @Column(name = "BANK_NAME")
	  public String getBankName() {
		    return bankName;
	  }

	  public void setBankName(String bankName) {
		    this.bankName = bankName;
	  }

	  @Column(name = "CITY_NAME")
	  public String getCityName() {
		    return cityName;
	  }

	  public void setCityName(String cityName) {
		    this.cityName = cityName;
	  }

	  @Column(name = "REGION")
	  public String getRegion() {
		    return region;
	  }

	  public void setRegion(String region) {
		    this.region = region;
	  }

	  @Column(name = "SWIFT_BIC")
	  public String getSwiftBIC() {
		    return swiftBIC;
	  }

	  public void setSwiftBIC(String swiftBIC) {
		    this.swiftBIC = swiftBIC;
	  }

	  @Column(name = "CHIPS_UID")
	  public String getChipsUID() {
		    return chipsUID;
	  }

	  public void setChipsUID(String chipsUID) {
		    this.chipsUID = chipsUID;
	  }

	  @Column(name = "ACRONYM_ID")
	  public String getAcronymId() {
		    return acronymId;
	  }

	  public void setAcronymId(String acronymId) {
		    this.acronymId = acronymId;
	  }

	  @Column(name = "FEDWIRE_ID")
	  public String getFedwireId() {
		    return fedwireId;
	  }

	  public void setFedwireId(String fedwireId) {
		    this.fedwireId = fedwireId;
	  }

	  @Column(name = "ABA_ID")
	  public String getAbaId() {
		    return abaId;
	  }

	  public void setAbaId(String abaId) {
		    this.abaId = abaId;
	  }

	  @Column(name = "ADDRESS1")
	  public String getAddress1() {
		    return address1;
	  }

	  public void setAddress1(String address1) {
		    this.address1 = address1;
	  }

	  @Column(name = "ADDRESS2")
	  public String getAddress2() {
		    return address2;
	  }

	  public void setAddress2(String address2) {
		    this.address2 = address2;
	  }

	  @Column(name = "ADDRESS3")
	  public String getAddress3() {
		    return address3;
	  }

	  public void setAddress3(String address3) {
		    this.address3 = address3;
	  }

	  @Column(name = "COUNTRY_ID")
	  public BigDecimal getCountryId() {
		    return countryId;
	  }

	  public void setCountryId(BigDecimal countryId) {
		    this.countryId = countryId;
	  }

	  @Column(name = "COUNTRY_CODE")
	  public String getCountryCode() {
		    return countryCode;
	  }

	  public void setCountryCode(String countryCode) {
		    this.countryCode = countryCode;
	  }

}
