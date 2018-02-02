package com.amg.exchange.remittance.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="VW_EX_SERVICE_REMITTANCE")
public class ViewServiceRemittance implements Serializable{

	  /**
	   * 
	   */
	  private static final long serialVersionUID = 1L;
	  
	  
	  private BigDecimal idNo;
	  private BigDecimal serviceGroupId;
	  private BigDecimal serviceMasterId;
	  private BigDecimal remittanceModeId;
	  private String remittanceModeDesc;
	  
	  @Id
	  @Column(name = "IDNO")
	  public BigDecimal getIdNo() {
	  	  return idNo;
	  }
	  public void setIdNo(BigDecimal idNo) {
	  	  this.idNo = idNo;
	  }
	  @Column(name = "SERVICE_GROUP_ID")
	  public BigDecimal getServiceGroupId() {
	  	  return serviceGroupId;
	  }
	  public void setServiceGroupId(BigDecimal serviceGroupId) {
	  	  this.serviceGroupId = serviceGroupId;
	  }
	  @Column(name = "SERVICE_MASTER_ID")
	  public BigDecimal getServiceMasterId() {
	  	  return serviceMasterId;
	  }
	  public void setServiceMasterId(BigDecimal serviceMasterId) {
	  	  this.serviceMasterId = serviceMasterId;
	  }
	  @Column(name = "REMITTANCE_MODE_ID")
	  public BigDecimal getRemittanceModeId() {
	  	  return remittanceModeId;
	  }
	  public void setRemittanceModeId(BigDecimal remittanceModeId) {
	  	  this.remittanceModeId = remittanceModeId;
	  }
	  @Column(name = "REMITTANCE_MODE_DESC")
	  public String getRemittanceModeDesc() {
	  	  return remittanceModeDesc;
	  }
	  public void setRemittanceModeDesc(String remittanceModeDesc) {
	  	  this.remittanceModeDesc = remittanceModeDesc;
	  }
	  
	  

}
