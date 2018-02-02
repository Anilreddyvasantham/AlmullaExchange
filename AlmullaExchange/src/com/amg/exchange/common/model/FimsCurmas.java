package com.amg.exchange.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FIMS_CURMAS")
public class FimsCurmas implements Serializable {
 
	private static final long serialVersionUID = 1L;
	 
	@Id
	@Column(name="CURCOD")   
	private String currencyCode;
	
	
	@Column(name="FUNAME")                           
	private String fuName;
	
	@Column(name="SHNAME")
	private String shName;
	
	@Column(name="CURDEC")
	private BigDecimal curDec;
	
	@Column(name="EXRATE")
	private BigDecimal exRate;
	
	@Column(name="EXDATE")
	private Date exDate;
	
	@Column(name="RECSTS")
	private String   recSts;
	
	@Column(name="CRTDAT")
	private Date createdDate;
	
	@Column(name="UPDDAT")
	private Date updatedDate;
	
	@Column(name="DECNAME")
	private String decimalName;
	
	@Column(name="BUYRATE")
	private BigDecimal buyRate;
	
	@Column(name="SALERATE")
	private BigDecimal sellRate;
	
	@Column(name="CREATOR")
	private String createdBy;
	
	@Column(name="MODIFIER")
	private String  modifiedBy;
	
	/*@Column(name="MAXVARPER")
	private BigDecimal maxVarPer;*/
	
/*	@Column(name="MINVARPER")
	private BigDecimal  minVarPer;*/
	
	@Column(name="CALDAYS")
	private BigDecimal calDays;
	
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getFuName() {
		return fuName;
	}
	public void setFuName(String fuName) {
		this.fuName = fuName;
	}
	public String getShName() {
		return shName;
	}
	public void setShName(String shName) {
		this.shName = shName;
	}
	public BigDecimal getCurDec() {
		return curDec;
	}
	public void setCurDec(BigDecimal curDec) {
		this.curDec = curDec;
	}
	public BigDecimal getExRate() {
		return exRate;
	}
	public void setExRate(BigDecimal exRate) {
		this.exRate = exRate;
	}
	public Date getExDate() {
		return exDate;
	}
	public void setExDate(Date exDate) {
		this.exDate = exDate;
	}
	public String getRecSts() {
		return recSts;
	}
	public void setRecSts(String recSts) {
		this.recSts = recSts;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getDecimalName() {
		return decimalName;
	}
	public void setDecimalName(String decimalName) {
		this.decimalName = decimalName;
	}
	public BigDecimal getBuyRate() {
		return buyRate;
	}
	public void setBuyRate(BigDecimal buyRate) {
		this.buyRate = buyRate;
	}
	public BigDecimal getSellRate() {
		return sellRate;
	}
	public void setSellRate(BigDecimal sellRate) {
		this.sellRate = sellRate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
/*	public BigDecimal getMaxVarPer() {
		return maxVarPer;
	}
	public void setMaxVarPer(BigDecimal maxVarPer) {
		this.maxVarPer = maxVarPer;
	}*/
	/*public BigDecimal getMinVarPer() {
		return minVarPer;
	}
	public void setMinVarPer(BigDecimal minVarPer) {
		this.minVarPer = minVarPer;
	}*/
	public BigDecimal getCalDays() {
		return calDays;
	}
	public void setCalDays(BigDecimal calDays) {
		this.calDays = calDays;
	}
	
 
 
 
 
 
  
 
 
 
	

}
