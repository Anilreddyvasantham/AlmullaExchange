package com.amg.exchange.jvprocess.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Author Nazish Ehsan Hashmi
 * Created Date - 03-09-2015
 */
@Entity
@Table(name="V_ACNT")
public class ViewActivitycenterAcnt implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	private BigDecimal acntcod;
	private String fudesc;
	private String shdesc;
	private BigDecimal loccod;
	private String acntcodAsString;
	
	
	@Id
	@Column(name="ACNTCOD")
	public BigDecimal getAcntcod() {
		return acntcod;
	}
	
	@Column(name="ACNTCOD", insertable=false, updatable=false)
	public String getAcntcodAsString() {
		return acntcodAsString;
	}



	public void setAcntcodAsString(String acntcodAsString) {
		this.acntcodAsString = acntcodAsString;
	}



	@Column(name="FUDESC")
	public String getFudesc() {
		return fudesc;
	}
	
	@Column(name="SHDESC")
	public String getShdesc() {
		return shdesc;
	}
	
	@Column(name="LOCCOD")
	public BigDecimal getLoccod() {
		return loccod;
	}
	public void setAcntcod(BigDecimal acntcod) {
		this.acntcod = acntcod;
	}
	public void setFudesc(String fudesc) {
		this.fudesc = fudesc;
	}
	public void setShdesc(String shdesc) {
		this.shdesc = shdesc;
	}
	public void setLoccod(BigDecimal loccod) {
		this.loccod = loccod;
	}
	
}


