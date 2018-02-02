package com.amg.exchange.registration.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * @ Author : Nazish Ehsan Hashmi
 */
@Entity
@Table(name="V_EX_MGRT")
public class ViewMgrt implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BigDecimal seqId;
	private String mgrTyp;
	
	private String fuDesc;
	
	private String shDesc;

	@Id
	@Column(name="SEQ_ID")
	public BigDecimal getSeqId() {
		return seqId;
	}

	public void setSeqId(BigDecimal seqId) {
		this.seqId = seqId;
	}
	
	@Column(name="MGRTYP")
	public String getMgrTyp() {
		return mgrTyp;
	}
	public void setMgrTyp(String mgrTyp) {
		this.mgrTyp = mgrTyp;
	}

	@Column(name="FUDESC")
	public String getFuDesc() {
		return fuDesc;
	}

	public void setFuDesc(String fuDesc) {
		this.fuDesc = fuDesc;
	}

   @Column(name="SHDESC")
	public String getShDesc() {
		return shDesc;
	}

	public void setShDesc(String shDesc) {
		this.shDesc = shDesc;
	}
	
	


}


