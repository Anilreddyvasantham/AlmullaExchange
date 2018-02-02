package com.amg.exchange.remittance.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Employee;
@Entity
@Table(name = "EX_PARAMETER_GRANT" )
public class ParameterGrant implements Serializable{

	  /**
	   * 
	   */
	  private static final long serialVersionUID = 1L;
	  
	  private CountryMaster fsCountryMaster;
	  private CountryBranch fsCountryBranch;
	  private BigDecimal parameterGrentId;
	  private String recordId;
	  private String recordDescription;
	  private Employee userId;
	  private String userName;
	  private String createdBy;
	  private Date createdDate;
	  private String modifiedBy;
	  private Date modifiedDate;
	  private String remarks;
	  private String isActive;
	 
	  
	  public ParameterGrant() {
		    super();
	  }


	  public ParameterGrant(CountryMaster fsCountryMaster,
			      CountryBranch fsCountryBranch,
			      BigDecimal parameterGrentId, String recordId,
			      String recordDescription, Employee userId,
			      String userName, String createdBy,
			      Date createdDate, String modifiedBy,
			      Date modifiedDate, String remarks, String isActive) {
		    super();
		    this.fsCountryMaster = fsCountryMaster;
		    this.fsCountryBranch = fsCountryBranch;
		    this.parameterGrentId = parameterGrentId;
		    this.recordId = recordId;
		    this.recordDescription = recordDescription;
		    this.userId = userId;
		    this.userName = userName;
		    this.createdBy = createdBy;
		    this.createdDate = createdDate;
		    this.modifiedBy = modifiedBy;
		    this.modifiedDate = modifiedDate;
		    this.remarks = remarks;
		    this.isActive = isActive;
	  }

	  @Id
	  @GeneratedValue(generator="ex_parameter_grant_seq",strategy=GenerationType.SEQUENCE)
	  @SequenceGenerator(name="ex_parameter_grant_seq",sequenceName="EX_PARAMETER_GRANT_SEQ",allocationSize=1)
	  @Column(name ="PARMETER_GRANT_ID" , nullable=false)
	  public BigDecimal getParameterGrentId() {
		  	  return parameterGrentId;
		  }

	  public void setParameterGrentId(BigDecimal parameterGrentId) {
		  	  this.parameterGrentId = parameterGrentId;
		  }
	  @ManyToOne(fetch = FetchType.LAZY)
	  @JoinColumn(name = "APPLICATION_COUNTRY_ID")
	  public CountryMaster getFsCountryMaster() {
	  	  return fsCountryMaster;
	  }

	  public void setFsCountryMaster(CountryMaster fsCountryMaster) {
	  	  this.fsCountryMaster = fsCountryMaster;
	  }

	  @ManyToOne(fetch = FetchType.LAZY)
	  @JoinColumn(name = "COUNTRY_BRANCH_ID")
	  public CountryBranch getFsCountryBranch() {
	  	  return fsCountryBranch;
	  }


	  public void setFsCountryBranch(CountryBranch fsCountryBranch) {
	  	  this.fsCountryBranch = fsCountryBranch;
	  }

	  @Column(name ="RECORD_ID")
	  public String getRecordId() {
	  	  return recordId;
	  }

	  public void setRecordId(String recordId) {
	  	  this.recordId = recordId;
		  }
	  
	  @Column(name ="RECORD_DESC")
	  public String getRecordDescription() {
	  	  return recordDescription;
	  }


	  public void setRecordDescription(String recordDescription) {
	  	  this.recordDescription = recordDescription;
	  }

	 
	  @ManyToOne(fetch = FetchType.LAZY)
	  @JoinColumn(name = "USER_ID")  
	  public Employee getUserId() {
	  	  return userId;
	  }


	  public void setUserId(Employee userId) {
	  	  this.userId = userId;
	  }

	  @Column(name ="USER_NAME")
	  public String getUserName() {
	  	  return userName;
	  }


	  public void setUserName(String userName) {
	  	  this.userName = userName;
	  }

	  @Column(name ="CREATED_BY")
	  public String getCreatedBy() {
	  	  return createdBy;
	  }


	  public void setCreatedBy(String createdBy) {
	  	  this.createdBy = createdBy;
	  }

	  @Column(name ="CREATED_DATE")
	  public Date getCreatedDate() {
	  	  return createdDate;
	  }


	  public void setCreatedDate(Date createdDate) {
	  	  this.createdDate = createdDate;
	  }

	  @Column(name ="MODIFIED_BY")
	  public String getModifiedBy() {
	  	  return modifiedBy;
	  }


	  public void setModifiedBy(String modifiedBy) {
	  	  this.modifiedBy = modifiedBy;
	  }

	  @Column(name ="MODIFIED_DATE")
	  public Date getModifiedDate() {
	  	  return modifiedDate;
	  }


	  public void setModifiedDate(Date modifiedDate) {
	  	  this.modifiedDate = modifiedDate;
	  }

	  @Column(name ="REMARKS")
	  public String getRemarks() {
	  	  return remarks;
	  }


	  public void setRemarks(String remarks) {
	  	  this.remarks = remarks;
	  }

	  @Column(name ="ISACTIVE")
	  public String getIsActive() {
	  	  return isActive;
	  }


	  public void setIsActive(String isActive) {
	  	  this.isActive = isActive;
	  }
	  
	  
	 
}
