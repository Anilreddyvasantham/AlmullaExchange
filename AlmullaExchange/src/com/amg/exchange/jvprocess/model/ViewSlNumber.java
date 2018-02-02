package com.amg.exchange.jvprocess.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Author Nazish Ehsan Hashmi
 * Created Date - 09-09-2015
 */
@Entity
@Table(name="V_GLNO")
public class ViewSlNumber implements Serializable{

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal ptyRef;
	private String glNo;
	private String slNo;
	private String slDesc;
	private String slInd;
	private Date sliDat;
	private String slpid;
	

	@Id
	@Column(name="PTYREF")
   public BigDecimal getPtyRef() {
		return ptyRef;
	}
	@Column(name="GLNO")
	public String getGlNo() {
		return glNo;
	}
	
	@Column(name="SLNO")
	public String getSlNo() {
		return slNo;
	}
	
	@Column(name="SLDES")
	public String getSlDesc() {
		return slDesc;
	}
	
	@Column(name="SLIND")
	public String getSlInd() {
		return slInd;
	}
	@Column(name="SLIDAT")
	public Date getSliDat() {
		return sliDat;
	}
	
	@Column(name="SLPID")
	public String getSlpid() {
		return slpid;
	}
	public void setPtyRef(BigDecimal ptyRef) {
		this.ptyRef = ptyRef;
	}
	public void setGlNo(String glNo) {
		this.glNo = glNo;
	}
	public void setSlNo(String slNo) {
		this.slNo = slNo;
	}
	public void setSlDesc(String slDesc) {
		this.slDesc = slDesc;
	}
	public void setSlInd(String slInd) {
		this.slInd = slInd;
	}
	public void setSliDat(Date sliDat) {
		this.sliDat = sliDat;
	}
	public void setSlpid(String slpid) {
		this.slpid = slpid;
	}
}
