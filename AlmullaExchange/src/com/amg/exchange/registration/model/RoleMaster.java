package com.amg.exchange.registration.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.amg.exchange.common.model.RoleWiseCurrencyLimit;
@Entity
@Table(name ="FS_ROLE_MASTER")
public class RoleMaster implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public RoleMaster(){
		
	}
	
	
	/**
	 * @param roleId
	 * @param roleName
	 * @param roleDesc
	 * @param createdBy
	 * @param createdDate
	 * @param modifiedBy
	 * @param modifiedDate
	 * @param fsEmployee
	 */
	public RoleMaster(BigDecimal roleId, String roleName, String roleDesc,
			String createdBy, Date createdDate, String modifiedBy,
			Date modifiedDate, Set<Employee> fsEmployee) {
	
		this.roleId = roleId;
		this.roleName = roleName;
		this.roleDesc = roleDesc;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.fsEmployee = fsEmployee;
	}


	@Id
	//@GeneratedValue
	@GeneratedValue(generator = "fs_role_master_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "fs_role_master_seq", sequenceName = "FS_ROLE_MASTER_SEQ", allocationSize = 1)
	@Column(name = "ROLE_ID", unique=true, nullable=false, precision=22, scale=0)
	private BigDecimal roleId;
	@Column(name ="ROLE_NAME")
	private String roleName;
	@Column(name ="ROLE_DESC")
	private String roleDesc;
	@Column(name ="CREATED_BY")
	private String createdBy;
	@Column(name ="CREATED_DATE")
	private Date createdDate;
	@Column(name ="MODIFIED_BY")
	private String modifiedBy;
	@Column(name ="MODIFIED_DATE")
	private Date modifiedDate;
	@OneToMany(mappedBy="fsRoleMaster")
	private Set<Employee> fsEmployee;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsRolemaster")
	private Set<RoleWiseCurrencyLimit> roleWiseCurrencyLimit=new HashSet<RoleWiseCurrencyLimit>();
	
	public BigDecimal getRoleId() {
		return roleId;
	}
	public void setRoleId(BigDecimal roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleDesc() {
		return roleDesc;
	}
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public Set<Employee> getFsEmployee() {
		return fsEmployee;
	}
	public void setFsEmployee(Set<Employee> fsEmployee) {
		this.fsEmployee = fsEmployee;
	}

	
	public Set<RoleWiseCurrencyLimit> getRoleWiseCurrencyLimit() {
		return roleWiseCurrencyLimit;
	}


	public void setRoleWiseCurrencyLimit(
			Set<RoleWiseCurrencyLimit> roleWiseCurrencyLimit) {
		this.roleWiseCurrencyLimit = roleWiseCurrencyLimit;
	}
	
	
	
}
