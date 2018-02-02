package com.amg.exchange.wuh2h.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "V_BRNC")
public class VwBrnc implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private BigDecimal acntCod;
	private BigDecimal areaLocCod;
	private BigDecimal areaMgrEcNo;
	private String brnctyp;
	private String denoInd;
	private String email;
	private String fuDesc;
	private BigDecimal grpLocCod;
	private BigDecimal hoind;
	private String hoindDes;
	private BigDecimal locCod;
	private String newKiosk;
	private String newPrint;
	private BigDecimal oAcntCod;
	private String routerIp;
	private String scanInd;
	private String shDesc;
	private String sigChk;
	private String sigInd;
	private String telNo;
	private String wuBrncId;
	
	
	
	
	//Getters and setters.
	
	@Column(name = "ACNTCOD")
	public BigDecimal getAcntCod() {
		return acntCod;
	}
	public void setAcntCod(BigDecimal acntCod) {
		this.acntCod = acntCod;
	}
	
	@Column(name = "AREA_LOCCOD")
	public BigDecimal getAreaLocCod() {
		return areaLocCod;
	}
	public void setAreaLocCod(BigDecimal areaLocCod) {
		this.areaLocCod = areaLocCod;
	}
	
	@Column(name = "AREA_MGR_ECNO")
	public BigDecimal getAreaMgrEcNo() {
		return areaMgrEcNo;
	}
	public void setAreaMgrEcNo(BigDecimal areaMgrEcNo) {
		this.areaMgrEcNo = areaMgrEcNo;
	}
	
	@Column(name = "BRNCTYP")
	public String getBrnctyp() {
		return brnctyp;
	}
	public void setBrnctyp(String brnctyp) {
		this.brnctyp = brnctyp;
	}
	
	@Column(name = "DENO_IND")
	public String getDenoInd() {
		return denoInd;
	}
	public void setDenoInd(String denoInd) {
		this.denoInd = denoInd;
	}
	
	@Column(name = "EMAIL")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(name = "FUDESC")
	public String getFuDesc() {
		return fuDesc;
	}
	public void setFuDesc(String fuDesc) {
		this.fuDesc = fuDesc;
	}
	
	@Column(name = "GRP_LOCCOD")
	public BigDecimal getGrpLocCod() {
		return grpLocCod;
	}
	public void setGrpLocCod(BigDecimal grpLocCod) {
		this.grpLocCod = grpLocCod;
	}
	
	@Column(name = "HOIND")
	public BigDecimal getHoind() {
		return hoind;
	}
	public void setHoind(BigDecimal hoind) {
		this.hoind = hoind;
	}
	
	@Column(name = "HOIND_DES")
	public String getHoindDes() {
		return hoindDes;
	}
	public void setHoindDes(String hoindDes) {
		this.hoindDes = hoindDes;
	}
	
	@Id
	@Column(name = "LOCCOD")
	public BigDecimal getLocCod() {
		return locCod;
	}
	public void setLocCod(BigDecimal locCod) {
		this.locCod = locCod;
	}
	
	@Column(name = "NEW_KIOSK")
	public String getNewKiosk() {
		return newKiosk;
	}
	public void setNewKiosk(String newKiosk) {
		this.newKiosk = newKiosk;
	}
	
	@Column(name = "NEW_PRINT")
	public String getNewPrint() {
		return newPrint;
	}
	public void setNewPrint(String newPrint) {
		this.newPrint = newPrint;
	}
	
	@Column(name = "O_ACNTCOD")
	public BigDecimal getoAcntCod() {
		return oAcntCod;
	}
	public void setoAcntCod(BigDecimal oAcntCod) {
		this.oAcntCod = oAcntCod;
	}
	
	@Column(name = "ROUTER_IP")
	public String getRouterIp() {
		return routerIp;
	}
	public void setRouterIp(String routerIp) {
		this.routerIp = routerIp;
	}
	
	@Column(name = "SCAN_IND")
	public String getScanInd() {
		return scanInd;
	}
	public void setScanInd(String scanInd) {
		this.scanInd = scanInd;
	}
	
	@Column(name = "SHDESC")
	public String getShDesc() {
		return shDesc;
	}
	public void setShDesc(String shDesc) {
		this.shDesc = shDesc;
	}
	
	@Column(name = "SIG_CHK")
	public String getSigChk() {
		return sigChk;
	}
	public void setSigChk(String sigChk) {
		this.sigChk = sigChk;
	}
	
	@Column(name = "SIG_IND")
	public String getSigInd() {
		return sigInd;
	}
	public void setSigInd(String sigInd) {
		this.sigInd = sigInd;
	}
	
	@Column(name = "TELNO")
	public String getTelNo() {
		return telNo;
	}
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}
	
	@Column(name = "WU_BRNC_ID")
	public String getWuBrncId() {
		return wuBrncId;
	}
	public void setWuBrncId(String wuBrncId) {
		this.wuBrncId = wuBrncId;
	}	

}
