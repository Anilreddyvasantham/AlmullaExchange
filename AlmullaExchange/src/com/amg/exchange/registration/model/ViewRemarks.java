package com.amg.exchange.registration.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="VW_EX_REMARKS")
public class ViewRemarks implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BigDecimal seqNo;
	private  String remarkCode;
	private String remarkDesc;
	
	@Id
	@Column(name="SLNO")
	public BigDecimal getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(BigDecimal seqNo) {
		this.seqNo = seqNo;
	}
	@Column(name="REMARKS_CODE")
	public String getRemarkCode() {
		return remarkCode;
	}
	public void setRemarkCode(String remarkCode) {
		this.remarkCode = remarkCode;
	}
	@Column(name="REMARKS")
	public String getRemarkDesc() {
		return remarkDesc;
	}
	public void setRemarkDesc(String remarkDesc) {
		this.remarkDesc = remarkDesc;
	}
	
	
}
