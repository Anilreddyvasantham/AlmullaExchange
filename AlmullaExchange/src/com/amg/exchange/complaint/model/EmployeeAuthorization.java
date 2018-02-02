package com.amg.exchange.complaint.model;

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

import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.registration.model.RoleMaster;
@Entity
@Table(name = "FS_EMPLOYEE_AUTHORIZATION")
public class EmployeeAuthorization implements Serializable {

	  /**
	   * 
	   */
	  private static final long serialVersionUID = 1L;

	  private BigDecimal employeeAuthorizationId;
	  private Employee fsEmployee;
	  private RoleMaster fsRole;
	  private String complaintType;
	  private String module;
	  private String privillage;
	  private String createdBy;
	  private Date createdDate;
	  private String modifiedBy;
	  private Date modifiedDate;
	  private String isActive;

	  public EmployeeAuthorization(BigDecimal employeeAuthorizationId, Employee fsEmployee, RoleMaster fsRole, String complaintType, String module, String privillage, String createdBy, Date createdDate, String modifiedBy, Date modifiedDate, String isActive) {
		    super();
		    this.employeeAuthorizationId = employeeAuthorizationId;
		    this.fsEmployee = fsEmployee;
		    this.fsRole = fsRole;
		    this.complaintType = complaintType;
		    this.module = module;
		    this.privillage = privillage;
		    this.createdBy = createdBy;
		    this.createdDate = createdDate;
		    this.modifiedBy = modifiedBy;
		    this.modifiedDate = modifiedDate;
		    this.isActive = isActive;
	  }
	  
	  

	  public EmployeeAuthorization() {
		    super();
	  }



	  @Id
	  @GeneratedValue(generator = "fs_employee_authorization_seq", strategy = GenerationType.SEQUENCE)
	  @SequenceGenerator(name = "fs_employee_authorization_seq", sequenceName = "FS_EMPLOYEE_AUTHORIZATION_SEQ", allocationSize = 1)
	  @Column(name = "EMPLOYEE_AUTHORIZATION_ID", nullable = false)
	  public BigDecimal getEmployeeAuthorizationId() {
		    return employeeAuthorizationId;
	  }

	  public void setEmployeeAuthorizationId(BigDecimal employeeAuthorizationId) {
		    this.employeeAuthorizationId = employeeAuthorizationId;
	  }

	  @ManyToOne(fetch = FetchType.LAZY)
	  @JoinColumn(name = "EMPLOYEE_ID")
	  public Employee getFsEmployee() {
		    return fsEmployee;
	  }

	  public void setFsEmployee(Employee fsEmployee) {
		    this.fsEmployee = fsEmployee;
	  }

	  @ManyToOne(fetch = FetchType.LAZY)
	  @JoinColumn(name = "ROLE_ID")
	  public RoleMaster getFsRole() {
		    return fsRole;
	  }

	  public void setFsRole(RoleMaster fsRole) {
		    this.fsRole = fsRole;
	  }

	  @Column(name = "COMPLAINT_TYPE")
	  public String getComplaintType() {
		    return complaintType;
	  }

	  public void setComplaintType(String complaintType) {
		    this.complaintType = complaintType;
	  }

	  @Column(name = "MODULE")
	  public String getModule() {
		    return module;
	  }

	  public void setModule(String module) {
		    this.module = module;
	  }

	  @Column(name = "PRIVILAGE")
	  public String getPrivillage() {
		    return privillage;
	  }

	  public void setPrivillage(String privillage) {
		    this.privillage = privillage;
	  }

	  @Column(name = "CREATED_BY")
	  public String getCreatedBy() {
		    return createdBy;
	  }

	  public void setCreatedBy(String createdBy) {
		    this.createdBy = createdBy;
	  }

	  @Column(name = "CREATED_DATE")
	  public Date getCreatedDate() {
		    return createdDate;
	  }

	  public void setCreatedDate(Date createdDate) {
		    this.createdDate = createdDate;
	  }

	  @Column(name = "MODIFIED_BY")
	  public String getModifiedBy() {
		    return modifiedBy;
	  }

	  public void setModifiedBy(String modifiedBy) {
		    this.modifiedBy = modifiedBy;
	  }

	  @Column(name = "MODIFIED_DATE")
	  public Date getModifiedDate() {
		    return modifiedDate;
	  }

	  public void setModifiedDate(Date modifiedDate) {
		    this.modifiedDate = modifiedDate;
	  }

	  @Column(name = "ISACTIVE")
	  public String getIsActive() {
		    return isActive;
	  }

	  public void setIsActive(String isActive) {
		    this.isActive = isActive;
	  }

}
