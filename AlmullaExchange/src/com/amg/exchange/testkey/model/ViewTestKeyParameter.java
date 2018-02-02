package com.amg.exchange.testkey.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="VW_TKYP")
public class ViewTestKeyParameter implements Serializable {

	  /**
	   * 
	   */
	  private static final long serialVersionUID = 1L;
	  private String recordId;
	  private String paramCodeDef;
	  private String fullDesc;
	  private String shortDesc;
	  private String tkyval;
	  private String tkytyp;
	  private String recordStstus;
	  private String isActive;
	  
	  @Column(name="RECORD_ID")
	  public String getRecordId() {
	  	  return recordId;
	  }
	  public void setRecordId(String recordId) {
	  	  this.recordId = recordId;
	  }
	  @Id
	  @Column(name="PARAM_CODE_DEF")
	  public String getParamCodeDef() {
		  	  return paramCodeDef;
		  }
	  public void setParamCodeDef(String paramCodeDef) {
	  	  this.paramCodeDef = paramCodeDef;
	  }
	  @Column(name="FULL_DESC")
	  public String getFullDesc() {
	  	  return fullDesc;
	  }
	  public void setFullDesc(String fullDesc) {
	  	  this.fullDesc = fullDesc;
	  }
	  @Column(name="SHORT_DESC")
	  public String getShortDesc() {
	  	  return shortDesc;
	  }
	  public void setShortDesc(String shortDesc) {
	  	  this.shortDesc = shortDesc;
	  }
	  @Column(name="ISACTIVE")
	  public String getIsActive() {
	  	  return isActive;
	  }
	  public void setIsActive(String isActive) {
	  	  this.isActive = isActive;
	  }
	  @Column(name="TKYVAL")
	  public String getTkyval() {
	  	  return tkyval;
	  }
	  public void setTkyval(String tkyval) {
	  	  this.tkyval = tkyval;
	  }
	  @Column(name="TKYTYP")
	  public String getTkytyp() {
	  	  return tkytyp;
	  }
	  public void setTkytyp(String tkytyp) {
	  	  this.tkytyp = tkytyp;
	  }
	  @Column(name="RECSTS_DES")
	  public String getRecordStstus() {
	  	  return recordStstus;
	  }
	  public void setRecordStstus(String recordStstus) {
	  	  this.recordStstus = recordStstus;
	  }
	  
	  
	  

}
