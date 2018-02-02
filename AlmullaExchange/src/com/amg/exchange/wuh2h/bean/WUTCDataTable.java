package com.amg.exchange.wuh2h.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class WUTCDataTable implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal lineNo;
	private String englishDescription;
	private String arabicDescription;
	
	public BigDecimal getLineNo() {
		return lineNo;
	}
	public void setLineNo(BigDecimal lineNo) {
		this.lineNo = lineNo;
	}
	public String getEnglishDescription() {
		return englishDescription;
	}
	public void setEnglishDescription(String englishDescription) {
		this.englishDescription = englishDescription;
	}
	public String getArabicDescription() {
		return arabicDescription;
	}
	public void setArabicDescription(String arabicDescription) {
		this.arabicDescription = arabicDescription;
	}
	
		
}
