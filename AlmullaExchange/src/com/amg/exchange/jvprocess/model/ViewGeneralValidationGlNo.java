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
 * Created Date - 03-09-2015
 */
@Entity
@Table(name="V_GLNO")
public class ViewGeneralValidationGlNo implements Serializable{

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal comcod;
	private String actcod;
	private BigDecimal acntcod;
	private String aclscod;
	private String glno;
	private String gldes;
	private String glind;
	private Date gliDat;
	private String gliPrv;
	private Date gliPdt;
	private String subInd;
	private String subCre;
	private BigDecimal srcCod;
	private String curCod;
	
	@Id
	@Column(name="COMCOD")
	public BigDecimal getComcod() {
		return comcod;
	}
	
	@Column(name="ACTCOD")
	public String getActcod() {
		return actcod;
	}
	
	@Column(name="ACNTCOD")
	public BigDecimal getAcntcod() {
		return acntcod;
	}
	
	@Column(name="ACLSCOD")
	public String getAclscod() {
		return aclscod;
	}
	
	@Column(name="GLNO")
	public String getGlno() {
		return glno;
	}
	
	@Column(name="GLDES")
	public String getGldes() {
		return gldes;
	}
	
	@Column(name="GLIND")
	public String getGlind() {
		return glind;
	}
	
	@Column(name="GLIDAT")
	public Date getGliDat() {
		return gliDat;
	}
	
	@Column(name="GLIPRV")
	public String getGliPrv() {
		return gliPrv;
	}

	@Column(name="GLIPDT")
	public Date getGliPdt() {
		return gliPdt;
	}

	@Column(name="SUBIND")
	public String getSubInd() {
		return subInd;
	}
	
	@Column(name="SUBCRE")
	public String getSubCre() {
		return subCre;
	}
	
	@Column(name="SRCCOD")
	public BigDecimal getSrcCod() {
		return srcCod;
	}
	
	@Column(name="CURCOD")
	public String getCurCod() {
		return curCod;
	}
	public void setComcod(BigDecimal comcod) {
		this.comcod = comcod;
	}
	public void setActcod(String actcod) {
		this.actcod = actcod;
	}
	public void setAcntcod(BigDecimal acntcod) {
		this.acntcod = acntcod;
	}
	public void setAclscod(String aclscod) {
		this.aclscod = aclscod;
	}
	public void setGlno(String glno) {
		this.glno = glno;
	}
	public void setGldes(String gldes) {
		this.gldes = gldes;
	}
	public void setGlind(String glind) {
		this.glind = glind;
	}
	public void setGliDat(Date gliDat) {
		this.gliDat = gliDat;
	}
	public void setGliPrv(String gliPrv) {
		this.gliPrv = gliPrv;
	}

	public void setGliPdt(Date gliPdt) {
		this.gliPdt = gliPdt;
	}
	public void setSubInd(String subInd) {
		this.subInd = subInd;
	}
	public void setSubCre(String subCre) {
		this.subCre = subCre;
	}
	public void setSrcCod(BigDecimal srcCod) {
		this.srcCod = srcCod;
	}
	public void setCurCod(String curCod) {
		this.curCod = curCod;
	}
	
	
}
