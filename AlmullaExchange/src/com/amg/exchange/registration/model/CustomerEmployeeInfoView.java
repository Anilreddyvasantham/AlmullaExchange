package com.amg.exchange.registration.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/*
 * Author Rahamathali Shaik
*/
@Entity
@Table(name= "VW_EX_CUSTOMER_EMPLOYEE_INFO")
public class CustomerEmployeeInfoView implements Serializable{

	
	
	private BigDecimal customerId;
	private BigDecimal employeeInfoId;
	private BigDecimal employeeCountryId;
	private BigDecimal employeeCompanyId;
	private BigDecimal employeeLanguageId;
	private BigDecimal occupationId;
	private BigDecimal employeementId;
	private String employeeName;
	private String department;
	private BigDecimal employeeState;
	private BigDecimal employeeDistrict;
	private BigDecimal employeeCity;
	private String employeeArea;
	private String employeeBlock;
	private String employeeStreet;
	private String employeePostal;
	private String employeeTelePhone;
	private String employeeProofDesc;
	private String employeeTypeName;
	private String cityName;
	private String stateName;
	private String distcritName;
	
	
	@Id
	@Column(name = "CUSTOMERID")
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	@Column(name = "EMPINFOID")
	public BigDecimal getEmployeeInfoId() {
		return employeeInfoId;
	}
	public void setEmployeeInfoId(BigDecimal employeeInfoId) {
		this.employeeInfoId = employeeInfoId;
	}
	@Column(name = "EMPCOUNTRYID")
	public BigDecimal getEmployeeCountryId() {
		return employeeCountryId;
	}
	public void setEmployeeCountryId(BigDecimal employeeCountryId) {
		this.employeeCountryId = employeeCountryId;
	}
	@Column(name = "EMPCOMPANYID")
	public BigDecimal getEmployeeCompanyId() {
		return employeeCompanyId;
	}
	public void setEmployeeCompanyId(BigDecimal employeeCompanyId) {
		this.employeeCompanyId = employeeCompanyId;
	}
	@Column(name = "EMPLANGUAGEID")
	public BigDecimal getEmployeeLanguageId() {
		return employeeLanguageId;
	}
	public void setEmployeeLanguageId(BigDecimal employeeLanguageId) {
		this.employeeLanguageId = employeeLanguageId;
	}
	@Column(name = "OCCUPATIONID")
	public BigDecimal getOccupationId() {
		return occupationId;
	}
	public void setOccupationId(BigDecimal occupationId) {
		this.occupationId = occupationId;
	}
	@Column(name = "EMPLOYMENTID")
	public BigDecimal getEmployeementId() {
		return employeementId;
	}
	public void setEmployeementId(BigDecimal employeementId) {
		this.employeementId = employeementId;
	}
	@Column(name = "EMPLOYERNAME")
	public String getEmployeeName() {
		return employeeName;
	}
	
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	@Column(name = "DEPARTMENT")
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	@Column(name = "EMPSTATE")
	public BigDecimal getEmployeeState() {
		return employeeState;
	}
	public void setEmployeeState(BigDecimal employeeState) {
		this.employeeState = employeeState;
	}
	@Column(name = "EMPDISTRICT")
	public BigDecimal getEmployeeDistrict() {
		return employeeDistrict;
	}
	public void setEmployeeDistrict(BigDecimal employeeDistrict) {
		this.employeeDistrict = employeeDistrict;
	}
	@Column(name = "EMPCITY")
	public BigDecimal getEmployeeCity() {
		return employeeCity;
	}
	public void setEmployeeCity(BigDecimal employeeCity) {
		this.employeeCity = employeeCity;
	}
	@Column(name = "EMPAREA")
	public String getEmployeeArea() {
		return employeeArea;
	}
	public void setEmployeeArea(String employeeArea) {
		this.employeeArea = employeeArea;
	}
	@Column(name = "EMPBLOCK")
	public String getEmployeeBlock() {
		return employeeBlock;
	}
	public void setEmployeeBlock(String employeeBlock) {
		this.employeeBlock = employeeBlock;
	}
	@Column(name = "EMPSTREET")
	public String getEmployeeStreet() {
		return employeeStreet;
	}
	public void setEmployeeStreet(String employeeStreet) {
		this.employeeStreet = employeeStreet;
	}
	@Column(name = "EMPPOSTAL")
	public String getEmployeePostal() {
		return employeePostal;
	}
	public void setEmployeePostal(String employeePostal) {
		this.employeePostal = employeePostal;
	}
	@Column(name = "EMPOFFICETELEPHONE")
	public String getEmployeeTelePhone() {
		return employeeTelePhone;
	}
	public void setEmployeeTelePhone(String employeeTelePhone) {
		this.employeeTelePhone = employeeTelePhone;
	}
	@Column(name = "EMP_PROF_DESC")
	public String getEmployeeProofDesc() {
		return employeeProofDesc;
	}
	public void setEmployeeProofDesc(String employeeProofDesc) {
		this.employeeProofDesc = employeeProofDesc;
	}
	@Column(name = "EMPTYPENAME")
	public String getEmployeeTypeName() {
		return employeeTypeName;
	}
	public void setEmployeeTypeName(String employeeTypeName) {
		this.employeeTypeName = employeeTypeName;
	}
	@Column(name = "CITYNAME")
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	@Column(name = "STATENAME")
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	@Column(name = "DISTRICTNAME")
	public String getDistcritName() {
		return distcritName;
	}
	public void setDistcritName(String distcritName) {
		this.distcritName = distcritName;
	}
	
	
	
}
