
package com.amg.exchange.loyalty.model;
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

@Entity
@Table(name = "EX_LTY_PRIVILEGES")
public class LoyaltyPrivileges implements Serializable {
	private static final long serialVersionUID = 1L;
	private BigDecimal loyaltyPrivilegesId;
	private String userName;
	private Employee employeeId;
	private String loyaltyCategory;
	private String loyaltyType;
	private String loyaltyParameter;
	private String loyaltyPromotion;
	private String encashment;
	private String claimRequest;
	private String claimApproval;
	private String calculation;
	private String statementAccount;
	private String loyaltyPointReport;
	private String loyaltycumReport;
	private String customerReport;
	private String claimReport;
	private String setPrivileges;
	private String calcScheduler;
	private String logOptions;
	private String mailOptions;
	private String customerBalanceReport;
	private String isActive;
	private String remarks;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;

	@Id
	@GeneratedValue(generator = "ex_loyalty_privilege_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ex_loyalty_privilege_seq", sequenceName = "EX_LOYALTY_PRIVILEGE_SEQ", allocationSize = 1)
	@Column(name = "LTY_PRIVILEGES_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getLoyaltyPrivilegesId() {
		return loyaltyPrivilegesId;
	}

	public void setLoyaltyPrivilegesId(BigDecimal loyaltyPrivilegesId) {
		this.loyaltyPrivilegesId = loyaltyPrivilegesId;
	}

	@Column(name = "USER_NAME")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EMPLOYEE_ID")
	public Employee getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Employee employeeId) {
		this.employeeId = employeeId;
	}

	@Column(name = "LOYALTY_CATEGORY")
	public String getLoyaltyCategory() {
		return loyaltyCategory;
	}

	public void setLoyaltyCategory(String loyaltyCategory) {
		this.loyaltyCategory = loyaltyCategory;
	}

	@Column(name = "LOYALTY_TYPE")
	public String getLoyaltyType() {
		return loyaltyType;
	}

	public void setLoyaltyType(String loyaltyType) {
		this.loyaltyType = loyaltyType;
	}

	@Column(name = "LOYALTY_PARAMETER")
	public String getLoyaltyParameter() {
		return loyaltyParameter;
	}

	public void setLoyaltyParameter(String loyaltyParameter) {
		this.loyaltyParameter = loyaltyParameter;
	}

	@Column(name = "LOYALTY_PROMOTION")
	public String getLoyaltyPromotion() {
		return loyaltyPromotion;
	}

	public void setLoyaltyPromotion(String loyaltyPromotion) {
		this.loyaltyPromotion = loyaltyPromotion;
	}

	@Column(name = "ENCASHMENT")
	public String getEncashment() {
		return encashment;
	}

	public void setEncashment(String encashment) {
		this.encashment = encashment;
	}

	@Column(name = "CLAIM_REQUEST")
	public String getClaimRequest() {
		return claimRequest;
	}

	public void setClaimRequest(String claimRequest) {
		this.claimRequest = claimRequest;
	}

	@Column(name = "CLAIM_APPROVAL")
	public String getClaimApproval() {
		return claimApproval;
	}

	public void setClaimApproval(String claimApproval) {
		this.claimApproval = claimApproval;
	}

	@Column(name = "CALCULATION")
	public String getCalculation() {
		return calculation;
	}

	public void setCalculation(String calculation) {
		this.calculation = calculation;
	}

	@Column(name = "STATEMENT_ACCOUNT")
	public String getStatementAccount() {
		return statementAccount;
	}

	public void setStatementAccount(String statementAccount) {
		this.statementAccount = statementAccount;
	}

	@Column(name = "LOYALTY_POINT_REPORT")
	public String getLoyaltyPointReport() {
		return loyaltyPointReport;
	}

	public void setLoyaltyPointReport(String loyaltyPointReport) {
		this.loyaltyPointReport = loyaltyPointReport;
	}

	@Column(name = "LOYALTY_CUM_REPORT")
	public String getLoyaltycumReport() {
		return loyaltycumReport;
	}

	public void setLoyaltycumReport(String loyaltycumReport) {
		this.loyaltycumReport = loyaltycumReport;
	}

	@Column(name = "CUSTOMER_REPORT")
	public String getCustomerReport() {
		return customerReport;
	}

	public void setCustomerReport(String customerReport) {
		this.customerReport = customerReport;
	}

	@Column(name = "CLAIM_REPORT")
	public String getClaimReport() {
		return claimReport;
	}

	public void setClaimReport(String claimReport) {
		this.claimReport = claimReport;
	}

	@Column(name = "SET_PRIVILEGES")
	public String getSetPrivileges() {
		return setPrivileges;
	}

	public void setSetPrivileges(String setPrivileges) {
		this.setPrivileges = setPrivileges;
	}

	@Column(name = "CALC_SCHEDULER")
	public String getCalcScheduler() {
		return calcScheduler;
	}

	public void setCalcScheduler(String calcScheduler) {
		this.calcScheduler = calcScheduler;
	}

	@Column(name = "LOG_OPTIONS")
	public String getLogOptions() {
		return logOptions;
	}

	public void setLogOptions(String logOptions) {
		this.logOptions = logOptions;
	}

	@Column(name = "MAIL_OPTIONS")
	public String getMailOptions() {
		return mailOptions;
	}

	public void setMailOptions(String mailOptions) {
		this.mailOptions = mailOptions;
	}

	@Column(name = "CUSTOMER_BALANCE_REPORT")
	public String getCustomerBalanceReport() {
		return customerBalanceReport;
	}

	public void setCustomerBalanceReport(String customerBalanceReport) {
		this.customerBalanceReport = customerBalanceReport;
	}

	@Column(name = "ISACTIVE")
	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Column(name = "REMARKS")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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

	@Column(name = "APPROVED_BY")
	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	@Column(name = "APPROVED_DATE")
	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
	/**/
}
