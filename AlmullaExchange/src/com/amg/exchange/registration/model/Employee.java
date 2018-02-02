package com.amg.exchange.registration.model;

import java.math.BigDecimal;
import java.sql.Clob;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.RoleWiseCurrencyLimit;

@Entity
@Table(name = "FS_EMPLOYEE" )
public class Employee implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private BigDecimal employeeId;
	private String employeeNumber;
	private String employeeName;
	private String userName;
	private String password;
	private String location;
	private String telephoneNumber;
	private BigDecimal countryId;
	//private BigDecimal branchId;
	private RoleMaster fsRoleMaster;
	private CountryBranch fsCountryBranch;
	//private CurrencyMaster currencyMaster; 
	private CompanyMaster fsCompanyMaster;
	//private String status;
	private String sesionStatus;
	private String email;
	private Clob signatureSpecimenClob;
	private String allowFcTransaction;
	private String ipAddress;
	private String cashierOpt;
	private BigDecimal wuUsername;
	private String wuPassword;
	private String userType;
	private String designation;
	private String isActive;
	private String deletedUser;
	private String modifiedBy;
	private String createdBy;
	private Date createdDate;
	private Date modifiedDate;
	
	private String unlockIP;
	private String unLockBy;
	private Date unLockDate;
	private Date lockDate;
	private BigDecimal lockCount;
	
	private BigDecimal employeeCountryId;
	private String allocateInd;
	
	private String wunaId;
	private String wuAccountId;
	private String wuTerminalId;
	private String wuForeignTerminalId;
	
	private Date passwordDate;
	private String civilId;
	
    private Set<RoleWiseCurrencyLimit> fsRoleWiseCurrencyLimit=new HashSet<RoleWiseCurrencyLimit>();

	//private BigDecimal limitationAmt; 

	public Employee(BigDecimal employeeId) {
		this.employeeId = employeeId;
	}

	public Employee() {
		super();
	}

	

	public Employee(BigDecimal employeeId, String employeeNumber,
			String employeeName, String userName, String password,
			String location, String telephoneNumber, BigDecimal countryId,
			RoleMaster fsRoleMaster, CountryBranch fsCountryBranch,
			CompanyMaster fsCompanyMaster, String sesionStatus, String email,
			Clob signatureSpecimenClob, String allowFcTransaction,
			String ipAddress, String cashierOpt, BigDecimal wuUsername,
			String wuPassword, String userType, String designation,
			String isActive, String deletedUser, String modifiedBy,
			String createdBy, Date createdDate, Date modifiedDate,
			String unlockIP, String unLockBy, Date unLockDate, Date lockDate,
			BigDecimal lockCount, BigDecimal employeeCountryId,
			String allocateInd, String wunaId, String wuAccountId,
			String wuTerminalId, String wuForeignTerminalId,
			Set<RoleWiseCurrencyLimit> fsRoleWiseCurrencyLimit) {
		super();
		this.employeeId = employeeId;
		this.employeeNumber = employeeNumber;
		this.employeeName = employeeName;
		this.userName = userName;
		this.password = password;
		this.location = location;
		this.telephoneNumber = telephoneNumber;
		this.countryId = countryId;
		this.fsRoleMaster = fsRoleMaster;
		this.fsCountryBranch = fsCountryBranch;
		this.fsCompanyMaster = fsCompanyMaster;
		this.sesionStatus = sesionStatus;
		this.email = email;
		this.signatureSpecimenClob = signatureSpecimenClob;
		this.allowFcTransaction = allowFcTransaction;
		this.ipAddress = ipAddress;
		this.cashierOpt = cashierOpt;
		this.wuUsername = wuUsername;
		this.wuPassword = wuPassword;
		this.userType = userType;
		this.designation = designation;
		this.isActive = isActive;
		this.deletedUser = deletedUser;
		this.modifiedBy = modifiedBy;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.unlockIP = unlockIP;
		this.unLockBy = unLockBy;
		this.unLockDate = unLockDate;
		this.lockDate = lockDate;
		this.lockCount = lockCount;
		this.employeeCountryId = employeeCountryId;
		this.allocateInd = allocateInd;
		this.wunaId = wunaId;
		this.wuAccountId = wuAccountId;
		this.wuTerminalId = wuTerminalId;
		this.wuForeignTerminalId = wuForeignTerminalId;
		this.fsRoleWiseCurrencyLimit = fsRoleWiseCurrencyLimit;
	}

	@Id
	@GeneratedValue(generator="fs_employee_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="fs_employee_seq",sequenceName="FS_EMPLOYEE_SEQ",allocationSize=1)
	@Column(name = "EMPLOYEE_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getEmployeeId() {
		return this.employeeId;
	}
	public void setEmployeeId(BigDecimal employeeId) {
		this.employeeId = employeeId;
	}

	@Column(name = "EMPLOYEE_NAME", length = 50)
	public String getEmployeeName() {
		return this.employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	
	@Column(name = "USER_NAME", length = 20,unique = true)
	public String getUserName() {
		return this.userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "PASSWORD", length = 20)
	public String getPassword() {
		return this.password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "LOCATION")
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	@Column(name = "TELEPHONE")
	public String getTelephoneNumber() {
		return telephoneNumber;
	}
	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}
	
	@Column(name="COUNTRY_ID")
	public BigDecimal getCountryId() {
		return countryId;
	}
	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}

	/*@Column(name="BRANCH_ID")
	public BigDecimal getBranchId() {
		return branchId;
	}
	public void setBranchId(BigDecimal branchId) {
		this.branchId = branchId;
	}*/

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "ROLE_ID")
	public RoleMaster getFsRoleMaster() {
		return fsRoleMaster;
	}
	public void setFsRoleMaster(RoleMaster fsRoleMaster) {
		this.fsRoleMaster = fsRoleMaster;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_BRANCH_ID")
	public CountryBranch getFsCountryBranch() {
		return fsCountryBranch;
	}
	public void setFsCountryBranch(CountryBranch fsCountryBranch) {
		this.fsCountryBranch = fsCountryBranch;
	}
	
	@Column(name="Employee_Number")
	public String getEmployeeNumber() {
		return employeeNumber;
	}
	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	/*@Column(name="STATUS")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}*/

	@Column(name="SESSION_STATUS")
	public String getSesionStatus() {
		return sesionStatus;
	}
	public void setSesionStatus(String sesionStatus) {
		this.sesionStatus = sesionStatus;
	}
	
	/*@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "CURRENCY_ID")
	public CurrencyMaster getCurrencyMaster() {
		return currencyMaster;
	}
	public void setCurrencyMaster(CurrencyMaster currencyMaster) {
		this.currencyMaster = currencyMaster;
	}*/

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "COMPANY_ID")
	public CompanyMaster getFsCompanyMaster() {
		return fsCompanyMaster;
	}
	public void setFsCompanyMaster(CompanyMaster fsCompanyMaster) {
		this.fsCompanyMaster = fsCompanyMaster;
	}
	
	/*@Column(name="LIMITATION_AMT")
	public BigDecimal getLimitationAmt() {
		return limitationAmt;
	}
	public void setLimitationAmt(BigDecimal limitationAmt) {
		this.limitationAmt = limitationAmt;
	}*/
	
	@Column(name="EMAIL")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
     
	@OneToMany(fetch = FetchType.LAZY,mappedBy="fsEmployee")
	public Set<RoleWiseCurrencyLimit> getFsRoleWiseCurrencyLimit() {
		return fsRoleWiseCurrencyLimit;
	}
	public void setFsRoleWiseCurrencyLimit(Set<RoleWiseCurrencyLimit> fsRoleWiseCurrencyLimit) {
		this.fsRoleWiseCurrencyLimit = fsRoleWiseCurrencyLimit;
	}


	@Column(name = "SIGNATURE_SPECIMEN_CLOB")
	public Clob getSignatureSpecimenClob() {
		return signatureSpecimenClob;
	}
	public void setSignatureSpecimenClob(Clob signatureSpecimenClob) {
		this.signatureSpecimenClob = signatureSpecimenClob;
	}

	@Column(name="ALLOW_FC_TRNX")
	public String getAllowFcTransaction() {
		return allowFcTransaction;
	}
	public void setAllowFcTransaction(String allowFcTransaction) {
		this.allowFcTransaction = allowFcTransaction;
	}

	@Column(name="IP_ADDRESS")
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	@Column(name="CASHIER_OPTN")
	public String getCashierOpt() {
		return cashierOpt;
	}
	public void setCashierOpt(String cashierOpt) {
		this.cashierOpt = cashierOpt;
	}

	@Column(name="WU_USERNAME")
	public BigDecimal getWuUsername() {
		return wuUsername;
	}
	public void setWuUsername(BigDecimal wuUsername) {
		this.wuUsername = wuUsername;
	}

	@Column(name="WU_PASSWORD")
	public String getWuPassword() {
		return wuPassword;
	}
	public void setWuPassword(String wuPassword) {
		this.wuPassword = wuPassword;
	}

	@Column(name="USER_TYPE")
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}

	@Column(name="DESIGNATION")
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}

	@Column(name="ISACTIVE")
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
	@Column(name="DELETED_USER")
	public String getDeletedUser() {
		return deletedUser;
	}
	public void setDeletedUser(String deletedUser) {
		this.deletedUser = deletedUser;
	}
	
	@Column(name="MODIFIED_BY")
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name="CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name="MODIFIED_DATE")
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name="UNLOCK_IP")
	public String getUnlockIP() {
		return unlockIP;
	}
	public void setUnlockIP(String unlockIP) {
		this.unlockIP = unlockIP;
	}

	@Column(name="UNLOCK_BY")
	public String getUnLockBy() {
		return unLockBy;
	}
	public void setUnLockBy(String unLockBy) {
		this.unLockBy = unLockBy;
	}

	@Column(name="UNLOCK_DATE")
	public Date getUnLockDate() {
		return unLockDate;
	}
	public void setUnLockDate(Date unLockDate) {
		this.unLockDate = unLockDate;
	}

	@Column(name="LOCK_DATE")
	public Date getLockDate() {
		return lockDate;
	}
	public void setLockDate(Date lockDate) {
		this.lockDate = lockDate;
	}

	@Column(name="LOCK_CNT")
	public BigDecimal getLockCount() {
		return lockCount;
	}
	public void setLockCount(BigDecimal lockCount) {
		this.lockCount = lockCount;
	}
		
	@Column(name="EMPLOYEE_COUNTRY_ID")
	public BigDecimal getEmployeeCountryId() {
		return employeeCountryId;
	}
	public void setEmployeeCountryId(BigDecimal employeeCountryId) {
		this.employeeCountryId = employeeCountryId;
	}
	
	@Column(name="ALLOCATE_IND")
	public String getAllocateInd() {
		return allocateInd;
	}
	public void setAllocateInd(String allocateInd) {
		this.allocateInd = allocateInd;
	}
	
	@Column(name="WU_NA_ID")
	public String getWunaId() {
		return wunaId;
	}

	public void setWunaId(String wunaId) {
		this.wunaId = wunaId;
	}
	
	@Column(name="WU_ACCOUNT_ID")
	public String getWuAccountId() {
		return wuAccountId;
	}

	public void setWuAccountId(String wuAccountId) {
		this.wuAccountId = wuAccountId;
	}
	
	@Column(name="WU_TERMINAL_ID")
	public String getWuTerminalId() {
		return wuTerminalId;
	}

	public void setWuTerminalId(String wuTerminalId) {
		this.wuTerminalId = wuTerminalId;
	}
	
	@Column(name="WU_FOREIGN_TERMINAL_ID")
	public String getWuForeignTerminalId() {
		return wuForeignTerminalId;
	}

	public void setWuForeignTerminalId(String wuForeignTerminalId) {
		this.wuForeignTerminalId = wuForeignTerminalId;
	}

	@Column(name="PASSWORD_EXPIRY_DATE")
	public Date getPasswordDate() {
		return passwordDate;
	}

	public void setPasswordDate(Date passwordDate) {
		this.passwordDate = passwordDate;
	}

	@Column(name="CIVIL_ID")
	public String getCivilId() {
		return civilId;
	}

	public void setCivilId(String civilId) {
		this.civilId = civilId;
	}
	
	
	
	
}
