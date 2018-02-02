package com.amg.exchange.loyalty.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.loyalty.model.LoyaltyPrivileges;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.loyalty.service.ILoyaltyPrivilegesService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("loyaltyPrivilegesBean")
@Scope("session")
public class LoyaltyPrivilegesBean<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(LoyaltyPrivilegesBean.class);
	private static final String N = "N";
	private BigDecimal loyaltyPrivilegesId;
	private String userName;
	private BigDecimal employeeId;
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
	private SessionStateManage sessionmanage = new SessionStateManage();
	private boolean isPopulate;
	private String errorMessage;
	private List<Employee> employeeList = new ArrayList<Employee>();
	
	@Autowired
	ILoyaltyPrivilegesService loaytyPrivileges;
	

	public String getErrorMessage() {
		return errorMessage;
	}



	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}



	public void setDefaultValues() {
		setEmployeeId(null);
		setLoyaltyPrivilegesId(null);
		setLoyaltyCategory(N);
		setLoyaltyType(N);
		setLoyaltyParameter(N);
		setLoyaltyPromotion(N);
		setEncashment(N);
		setClaimRequest(N);
		setClaimApproval(N);
		setCalculation(N);
		setStatementAccount(N);
		setLoyaltyPointReport(N);
		setLoyaltycumReport(N);
		setCustomerReport(N);
		setClaimReport(N);
		setSetPrivileges(N);
		setCalcScheduler(N);
		setLogOptions(N);
		setMailOptions(N);
		setCustomerBalanceReport(N);
	}

	

	public List<Employee> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(List<Employee> employeeList) {
		this.employeeList = employeeList;
	}

	public BigDecimal getLoyaltyPrivilegesId() {
		return loyaltyPrivilegesId;
	}

	public void setLoyaltyPrivilegesId(BigDecimal loyaltyPrivilegesId) {
		this.loyaltyPrivilegesId = loyaltyPrivilegesId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public BigDecimal getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(BigDecimal employeeId) {
		this.employeeId = employeeId;
	}

	public String getLoyaltyCategory() {
		return loyaltyCategory;
	}

	public void setLoyaltyCategory(String loyaltyCategory) {
		this.loyaltyCategory = loyaltyCategory;
	}

	public String getLoyaltyType() {
		return loyaltyType;
	}

	public void setLoyaltyType(String loyaltyType) {
		this.loyaltyType = loyaltyType;
	}

	public String getLoyaltyParameter() {
		return loyaltyParameter;
	}

	public void setLoyaltyParameter(String loyaltyParameter) {
		this.loyaltyParameter = loyaltyParameter;
	}

	public String getLoyaltyPromotion() {
		return loyaltyPromotion;
	}

	public void setLoyaltyPromotion(String loyaltyPromotion) {
		this.loyaltyPromotion = loyaltyPromotion;
	}

	public String getEncashment() {
		return encashment;
	}

	public void setEncashment(String encashment) {
		this.encashment = encashment;
	}

	public String getClaimRequest() {
		return claimRequest;
	}

	public void setClaimRequest(String claimRequest) {
		this.claimRequest = claimRequest;
	}

	public String getClaimApproval() {
		return claimApproval;
	}

	public void setClaimApproval(String claimApproval) {
		this.claimApproval = claimApproval;
	}

	public String getCalculation() {
		return calculation;
	}

	public void setCalculation(String calculation) {
		this.calculation = calculation;
	}

	public String getStatementAccount() {
		return statementAccount;
	}

	public void setStatementAccount(String statementAccount) {
		this.statementAccount = statementAccount;
	}

	public String getLoyaltyPointReport() {
		return loyaltyPointReport;
	}

	public void setLoyaltyPointReport(String loyaltyPointReport) {
		this.loyaltyPointReport = loyaltyPointReport;
	}

	public String getLoyaltycumReport() {
		return loyaltycumReport;
	}

	public void setLoyaltycumReport(String loyaltycumReport) {
		this.loyaltycumReport = loyaltycumReport;
	}

	public String getCustomerReport() {
		return customerReport;
	}

	public void setCustomerReport(String customerReport) {
		this.customerReport = customerReport;
	}

	public String getClaimReport() {
		return claimReport;
	}

	public void setClaimReport(String claimReport) {
		this.claimReport = claimReport;
	}

	public String getSetPrivileges() {
		return setPrivileges;
	}

	public void setSetPrivileges(String setPrivileges) {
		this.setPrivileges = setPrivileges;
	}

	public String getCalcScheduler() {
		return calcScheduler;
	}

	public void setCalcScheduler(String calcScheduler) {
		this.calcScheduler = calcScheduler;
	}

	public String getLogOptions() {
		return logOptions;
	}

	public void setLogOptions(String logOptions) {
		this.logOptions = logOptions;
	}

	public String getMailOptions() {
		return mailOptions;
	}

	public void setMailOptions(String mailOptions) {
		this.mailOptions = mailOptions;
	}

	public String getCustomerBalanceReport() {
		return customerBalanceReport;
	}

	public void setCustomerBalanceReport(String customerBalanceReport) {
		this.customerBalanceReport = customerBalanceReport;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public SessionStateManage getSessionmanage() {
		return sessionmanage;
	}

	public void setSessionmanage(SessionStateManage sessionmanage) {
		this.sessionmanage = sessionmanage;
	}

	public ILoyaltyPrivilegesService getLoaytyPrivileges() {
		return loaytyPrivileges;
	}

	public void setLoaytyPrivileges(ILoyaltyPrivilegesService loaytyPrivileges) {
		this.loaytyPrivileges = loaytyPrivileges;
	}

	public boolean isPopulate() {
		return isPopulate;
	}

	public void setPopulate(boolean isPopulate) {
		this.isPopulate = isPopulate;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
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

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void pageNavigation() {
		clearAll();
		setDefaultValues();
		employeeList = loaytyPrivileges.getEmployeeList(sessionmanage.getCountryId());
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionmanage.getCountryId(), sessionmanage.getUserType(), sessionmanage.getUserName(), "loyaltyPrivileges.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../loyalty/loyaltyPrivileges.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("Exit  into pageNavigation");
	}

	public void clearAll() {
		setPopulate(false);
		setUserName(null);
		setDefaultValues();
	}

	public void save() {
		try {
		LoyaltyPrivileges saveLoyaltyPrivileges = new LoyaltyPrivileges();
		
		saveLoyaltyPrivileges.setUserName(getUserName());
		
		Employee employee = new Employee();
		employee.setEmployeeId(getEmployeeId());	
		saveLoyaltyPrivileges.setEmployeeId(employee);
		
		saveLoyaltyPrivileges.setLoyaltyCategory(getLoyaltyCategory());
		saveLoyaltyPrivileges.setLoyaltyType(getLoyaltyType());
		saveLoyaltyPrivileges.setLoyaltyParameter(getLoyaltyParameter());
		saveLoyaltyPrivileges.setLoyaltyPromotion(getLoyaltyPromotion());
		saveLoyaltyPrivileges.setEncashment(getEncashment());
		saveLoyaltyPrivileges.setClaimRequest(getClaimRequest());
		saveLoyaltyPrivileges.setClaimApproval(getClaimApproval());
		saveLoyaltyPrivileges.setCalculation(getCalculation());
		saveLoyaltyPrivileges.setStatementAccount(getStatementAccount());
		saveLoyaltyPrivileges.setLoyaltyPointReport(getLoyaltyPointReport());
		saveLoyaltyPrivileges.setLoyaltycumReport(getLoyaltycumReport());
		saveLoyaltyPrivileges.setCustomerReport(getCustomerReport());
		saveLoyaltyPrivileges.setClaimReport(getClaimReport());
		saveLoyaltyPrivileges.setSetPrivileges(getSetPrivileges());
		saveLoyaltyPrivileges.setCalcScheduler(getCalcScheduler());
		saveLoyaltyPrivileges.setLogOptions(getLogOptions());
		saveLoyaltyPrivileges.setMailOptions(getMailOptions());
		saveLoyaltyPrivileges.setCustomerBalanceReport(getCustomerBalanceReport());
		saveLoyaltyPrivileges.setIsActive(Constants.Yes);
		saveLoyaltyPrivileges.setLoyaltyPrivilegesId(getLoyaltyPrivilegesId());
		if (isPopulate()) {
			saveLoyaltyPrivileges.setCreatedBy(getCreatedBy());
			saveLoyaltyPrivileges.setCreatedDate(getCreatedDate());
			saveLoyaltyPrivileges.setModifiedBy(sessionmanage.getUserName());
			saveLoyaltyPrivileges.setModifiedDate(new Date());
		} else {
			saveLoyaltyPrivileges.setCreatedBy(sessionmanage.getUserName());
			saveLoyaltyPrivileges.setCreatedDate(new Date());
			saveLoyaltyPrivileges.setModifiedBy(null);
			saveLoyaltyPrivileges.setModifiedDate(null);
		}
		
		
		//try {
			getLoaytyPrivileges().save(saveLoyaltyPrivileges);
			RequestContext.getCurrentInstance().execute("save.show();");
		 }catch(NullPointerException ne){
	    	   LOGGER.info("Method Name::save"+ne.getMessage());
	    	   setErrorMessage("Method Name::save"); 
	    	   RequestContext.getCurrentInstance().execute("nullPointerId.show();");
	    	   return; 
	       } catch (Exception exception) {
	    	   LOGGER.info("Method Name::save"+exception.getMessage());
	    	   setErrorMessage(exception.getMessage());
	    	   RequestContext.getCurrentInstance().execute("exception.show();");
	    	   return;
	       }
	}

	public void populateData() {
		LOGGER.info("Entering into populateData method");
		LOGGER.info(getUserName());
		LOGGER.info(sessionmanage.getCountryId());
		try{
		LoyaltyPrivileges loyaltyPrivileges = getLoaytyPrivileges().populateValues(getUserName());
		if (loyaltyPrivileges != null) {
			
			setEmployeeId(loyaltyPrivileges.getEmployeeId().getEmployeeId());
			setCreatedBy(loyaltyPrivileges.getCreatedBy());
			setCreatedDate(loyaltyPrivileges.getCreatedDate());
			setModifiedBy(sessionmanage.getUserName());
			setModifiedDate(new Date());
			setLoyaltyCategory(loyaltyPrivileges.getLoyaltyCategory());
			setLoyaltyType(loyaltyPrivileges.getLoyaltyType());
			setLoyaltyParameter(loyaltyPrivileges.getLoyaltyParameter());
			setLoyaltyPromotion(loyaltyPrivileges.getLoyaltyPromotion());
			setEncashment(loyaltyPrivileges.getEncashment());
			setClaimRequest(loyaltyPrivileges.getClaimRequest());
			setClaimApproval(loyaltyPrivileges.getClaimApproval());
			setCalculation(loyaltyPrivileges.getCalculation());
			setStatementAccount(loyaltyPrivileges.getStatementAccount());
			setLoyaltyPointReport(loyaltyPrivileges.getLoyaltyPointReport());
			setLoyaltycumReport(loyaltyPrivileges.getLoyaltycumReport());
			setCustomerReport(loyaltyPrivileges.getCustomerReport());
			setClaimReport(loyaltyPrivileges.getClaimReport());
			setSetPrivileges(loyaltyPrivileges.getSetPrivileges());
			setCalcScheduler(loyaltyPrivileges.getCalcScheduler());
			setLogOptions(loyaltyPrivileges.getLogOptions());
			setMailOptions(loyaltyPrivileges.getMailOptions());
			setCustomerBalanceReport(loyaltyPrivileges.getCustomerBalanceReport());
			setPopulate(true);
			setLoyaltyPrivilegesId(loyaltyPrivileges.getLoyaltyPrivilegesId());
		} else {
			
			setDefaultValues();
			setEmployeeId(getLoaytyPrivileges().getEmployeeId(getUserName()));
			LOGGER.info("No mathching record found");
		}
		LOGGER.info("Exit into populateData method");
		}catch(NullPointerException ne){
	    	   LOGGER.info("Method Name::populateData"+ne.getMessage());
	    	   setErrorMessage("Method Name::populateData"); 
	    	   RequestContext.getCurrentInstance().execute("nullPointerId.show();");
	    	   return; 
	       } catch (Exception exception) {
	    	   LOGGER.info("Method Name::populateData"+exception.getMessage());
	    	   setErrorMessage(exception.getMessage());
	    	   RequestContext.getCurrentInstance().execute("exception.show();");
	    	   return;
	       }
	}

	public void clickOnExit() throws IOException {
		if (sessionmanage.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}
	}
}
