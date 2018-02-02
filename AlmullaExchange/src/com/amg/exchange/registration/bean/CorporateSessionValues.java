package com.amg.exchange.registration.bean;

import java.math.BigDecimal;
import java.util.Date;

public class CorporateSessionValues {

	private String pidno;
	private String partName;
	private String partNameLocal;
	private Date partnerCustExpDate;
	private String partnerContactNumber;
	private String partnerEmail;
	private String occupationName;
	private BigDecimal occupationId;
	
	
	
	
	public String getOccupationName() {
		return occupationName;
	}
	public BigDecimal getOccupationId() {
		return occupationId;
	}
	public void setOccupationName(String occupationName) {
		this.occupationName = occupationName;
	}
	public void setOccupationId(BigDecimal occupationId) {
		this.occupationId = occupationId;
	}
	public String getPidno() {
		return pidno;
	}
	public String getPartName() {
		return partName;
	}
	public String getPartNameLocal() {
		return partNameLocal;
	}
	public Date getPartnerCustExpDate() {
		return partnerCustExpDate;
	}
	public String getPartnerContactNumber() {
		return partnerContactNumber;
	}
	public String getPartnerEmail() {
		return partnerEmail;
	}
	public void setPidno(String pidno) {
		this.pidno = pidno;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}
	public void setPartNameLocal(String partNameLocal) {
		this.partNameLocal = partNameLocal;
	}
	public void setPartnerCustExpDate(Date partnerCustExpDate) {
		this.partnerCustExpDate = partnerCustExpDate;
	}
	public void setPartnerContactNumber(String partnerContactNumber) {
		this.partnerContactNumber = partnerContactNumber;
	}
	public void setPartnerEmail(String partnerEmail) {
		this.partnerEmail = partnerEmail;
	}

	
}
