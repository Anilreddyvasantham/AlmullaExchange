package com.amg.exchange.remittance.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="V_EX_PIPS_TYPE")
public class ViewPIPSType implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private BigDecimal srNo;
	
	private String pipsTypeCode;
	
	private BigDecimal pipsTypeId;
	
	private String fullDesc;
	
	private String shortDesc;
	
	private String ArFullDesc;
	
	private String arShortDesc;

	@Id
	@Column(name="SRNO")
	public BigDecimal getSrNo() {
		return srNo;
	}

	public void setSrNo(BigDecimal srNo) {
		this.srNo = srNo;
	}

	@Column(name="PIPS_TYPE_CODE")
	public String getPipsTypeCode() {
		return pipsTypeCode;
	}

	public void setPipsTypeCode(String pipsTypeCode) {
		this.pipsTypeCode = pipsTypeCode;
	}

	@Column(name="PIPS_TYPE_ID")
	public BigDecimal getPipsTypeId() {
		return pipsTypeId;
	}

	public void setPipsTypeId(BigDecimal pipsTypeId) {
		this.pipsTypeId = pipsTypeId;
	}

	@Column(name="FULL_DESC")
	public String getFullDesc() {
		return fullDesc;
	}

	public void setFullDesc(String fullDesc) {
		this.fullDesc = fullDesc;
	}

	@Column(name="SHORT_DESC")
	public String getShortDesc() {
		return shortDesc;
	}

	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}

	@Column(name="AR_FULL_DESC")
	public String getArFullDesc() {
		return ArFullDesc;
	}

	public void setArFullDesc(String arFullDesc) {
		ArFullDesc = arFullDesc;
	}

	@Column(name="AR_SHORT_DESC")
	public String getArShortDesc() {
		return arShortDesc;
	}

	public void setArShortDesc(String arShortDesc) {
		this.arShortDesc = arShortDesc;
	}
	
	
}
	
	