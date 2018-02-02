package com.amg.exchange.common.model;

import java.io.Serializable;
import java.math.BigDecimal;

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
import com.amg.exchange.treasury.model.CurrencyMaster;

@Entity
@Table(name="FS_ROLEWISE_CURRENCY_LIMIT")
public class RoleWiseCurrencyLimit implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private BigDecimal roleWiseCurrencyLmtId;
	private RoleMaster fsRolemaster;
	private Employee fsEmployee;
	private BigDecimal limitationAmount;
	private CurrencyMaster fsCurrencyMaster;
	private String isActive;
	
	RoleWiseCurrencyLimit()
	{
		
	}
	
	RoleWiseCurrencyLimit(BigDecimal roleWiseCurrencyLmtId)
	{
		this.roleWiseCurrencyLmtId=roleWiseCurrencyLmtId;
	}
	
	RoleWiseCurrencyLimit(BigDecimal roleWiseCurrencyLmtId,
			RoleMaster fsRolemaster, Employee fsEmployee,
			BigDecimal limitationAmount, CurrencyMaster fsCurrencyMaster,
			String isActive) {

		this.roleWiseCurrencyLmtId = roleWiseCurrencyLmtId;
		this.fsRolemaster = fsRolemaster;
		this.fsEmployee = fsEmployee;
		this.limitationAmount = limitationAmount;
		this.fsCurrencyMaster = fsCurrencyMaster;
		this.isActive = isActive;
	}
	
	
	
	@Id
	@GeneratedValue(generator="fs_rolewise_currency_limit_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="fs_rolewise_currency_limit_seq",sequenceName="FS_ROLEWISE_CURRENCY_LIMIT_SEQ",allocationSize=1)
	@Column(name = "ROLEWISE_CURRENCY_LIMIT_ID", unique = true, nullable = false)
	public BigDecimal getRoleWiseCurrencyLmtId() {
		return roleWiseCurrencyLmtId;
	}

	public void setRoleWiseCurrencyLmtId(BigDecimal roleWiseCurrencyLmtId) {
		this.roleWiseCurrencyLmtId = roleWiseCurrencyLmtId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ROLE_ID")
	public RoleMaster getFsRolemaster() {
		return fsRolemaster;
	}

	public void setFsRolemaster(RoleMaster fsRolemaster) {
		this.fsRolemaster = fsRolemaster;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EMPLOYEE_ID")
	public Employee getFsEmployee() {
		return fsEmployee;
	}

	public void setFsEmployee(Employee fsEmployee) {
		this.fsEmployee = fsEmployee;
	}
    
	@Column(name = "LIMITATION_AMOUNT")
	public BigDecimal getLimitationAmount() {
		return limitationAmount;
	}

	public void setLimitationAmount(BigDecimal limitationAmount) {
		this.limitationAmount = limitationAmount;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CURRENCY_ID")
	public CurrencyMaster getFsCurrencyMaster() {
		return fsCurrencyMaster;
	}

	public void setFsCurrencyMaster(CurrencyMaster fsCurrencyMaster) {
		this.fsCurrencyMaster = fsCurrencyMaster;
	}

	@Column(name = "ISACTIVE")
	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
	
	

}
