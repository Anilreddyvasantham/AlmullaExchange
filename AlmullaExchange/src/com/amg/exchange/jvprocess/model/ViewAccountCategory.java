package com.amg.exchange.jvprocess.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="V_ACAC")
public class ViewAccountCategory implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal acacod;
	private BigDecimal atyp;
	private String acades;
	
	
	
	@Id
	@Column(name="ACACOD")
	public BigDecimal getAcacod() {
		return acacod;
	}
	public void setAcacod(BigDecimal acacod) {
		this.acacod = acacod;
	}
	
	@Column(name="ATYP")
	public BigDecimal getAtyp() {
		return atyp;
	}
	public void setAtyp(BigDecimal atyp) {
		this.atyp = atyp;
	}
	
	@Column(name="ACADES")
	public String getAcades() {
		return acades;
	}
	public void setAcades(String acades) {
		this.acades = acades;
	}
	
	
	

}
