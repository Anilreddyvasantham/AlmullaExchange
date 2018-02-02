package com.amg.exchange.jvprocess.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class JVReasonDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal parmeterDetailsId;
	private String recordId;
	private String paramCodeDef;
	private String paramFullDesc;
	
	public BigDecimal getParmeterDetailsId() {
		return parmeterDetailsId;
	}
	public void setParmeterDetailsId(BigDecimal parmeterDetailsId) {
		this.parmeterDetailsId = parmeterDetailsId;
	}
	public String getRecordId() {
		return recordId;
	}
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	public String getParamCodeDef() {
		return paramCodeDef;
	}
	public void setParamCodeDef(String paramCodeDef) {
		this.paramCodeDef = paramCodeDef;
	}
	public String getParamFullDesc() {
		return paramFullDesc;
	}
	public void setParamFullDesc(String paramFullDesc) {
		this.paramFullDesc = paramFullDesc;
	}
	
	
}
