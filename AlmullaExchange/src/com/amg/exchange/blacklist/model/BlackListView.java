package com.amg.exchange.blacklist.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "V_BLACK_LIST_VIEW")
public class BlackListView implements Serializable
{
	private static final long serialVersionUID = 1L;
	private BigDecimal idNo;
	private String englishName;
	private String arabicFullName;
	private BigDecimal seqNo;
	public BlackListView() {
		
	}
	public BlackListView(BigDecimal idNo, String englishName,
			String arabicFullName, BigDecimal seqNo) {
		super();
		this.idNo = idNo;
		this.englishName = englishName;
		this.arabicFullName = arabicFullName;
		this.seqNo = seqNo;
	}
	@Id
	@Column(name = "IDNO")
	public BigDecimal getIdNo() {
		return idNo;
	}
	public void setIdNo(BigDecimal idNo) {
		this.idNo = idNo;
	}
	@Column(name = "ENGLISH_NAME")
	public String getEnglishName() {
		return englishName;
	}
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	@Column(name = "ARABIC_FULL_NAME")
	public String getArabicFullName() {
		return arabicFullName;
	}
	public void setArabicFullName(String arabicFullName) {
		this.arabicFullName = arabicFullName;
	}
	@Column(name = "SEQ_NO")
	public BigDecimal getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(BigDecimal seqNo) {
		this.seqNo = seqNo;
	}
}