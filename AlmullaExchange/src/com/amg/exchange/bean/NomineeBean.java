package com.amg.exchange.bean;

import java.math.BigDecimal;

public class NomineeBean {
	
	private BigDecimal pkNominee = null;
	
	private String nomineeName = null;
	private String effectiveDate = null;
	private String endDate = null;
	private BigDecimal  nominatorId = null;
	private  BigDecimal nomineeId = null;
	
	public NomineeBean(String nomineeName, String effectiveDate, String endDate, BigDecimal nominatorId, BigDecimal nomineeId, BigDecimal pkNominee) {
		this.nomineeName = nomineeName;
		this.effectiveDate = effectiveDate;
		this.endDate = endDate;
		this.nominatorId = nominatorId;
		this.nomineeId = nomineeId;
		this.pkNominee = pkNominee;
	}
	
	public NomineeBean(String nomineeName,String effectiveDate,String endDate, BigDecimal nomineeId, BigDecimal pkNominee) {
		this.nomineeName = nomineeName;
		this.effectiveDate = effectiveDate;
		this.endDate = endDate;
		this.nomineeId = nomineeId;
		this.pkNominee = pkNominee;
	}

	public String getNomineeName() {
		return nomineeName;
	}

	public void setNomineeName(String nomineeName) {
		this.nomineeName = nomineeName;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	public BigDecimal getNominatorId() {
		return nominatorId;
	}

	public void setNominatorId(BigDecimal nominatorId) {
		this.nominatorId = nominatorId;
	}

	public BigDecimal getNomineeId() {
		return nomineeId;
	}

	public void setNomineeId(BigDecimal nomineeId) {
		this.nomineeId = nomineeId;
	}

	public BigDecimal getPkNominee() {
		return pkNominee;
	}

	public void setPkNominee(BigDecimal pkNominee) {
		this.pkNominee = pkNominee;
	}
	
}
