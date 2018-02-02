package com.amg.exchange.promotion.bean;

import java.io.Serializable;

public class PromotionDeclarationReport implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String civilId;
	private String name;
	private String prizeAmount;
	private String signature;
	private String dateofIssue;
	
	private String logoPath;
	private Boolean waterMarkCheck =false;
	private String waterMarkLogoPath;
	
	
	public String getCivilId() {
		return civilId;
	}
	public void setCivilId(String civilId) {
		this.civilId = civilId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrizeAmount() {
		return prizeAmount;
	}
	public void setPrizeAmount(String prizeAmount) {
		this.prizeAmount = prizeAmount;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getDateofIssue() {
		return dateofIssue;
	}
	public void setDateofIssue(String dateofIssue) {
		this.dateofIssue = dateofIssue;
	}
	public String getLogoPath() {
		return logoPath;
	}
	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}
	public Boolean getWaterMarkCheck() {
		return waterMarkCheck;
	}
	public void setWaterMarkCheck(Boolean waterMarkCheck) {
		this.waterMarkCheck = waterMarkCheck;
	}
	public String getWaterMarkLogoPath() {
		return waterMarkLogoPath;
	}
	public void setWaterMarkLogoPath(String waterMarkLogoPath) {
		this.waterMarkLogoPath = waterMarkLogoPath;
	}
	
	

}
