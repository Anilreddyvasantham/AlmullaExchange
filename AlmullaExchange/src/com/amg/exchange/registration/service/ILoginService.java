package com.amg.exchange.registration.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.amg.exchange.common.model.UserSession;
import com.amg.exchange.mail.ApplicationEmailLog;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.CustomerLogin;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.registration.model.LoginLogoutHistory;
import com.amg.exchange.send_sms.EoExOwsLoginCredentials;
import com.amg.exchange.send_sms.EoOwsParamRespcode;
import com.amg.exchange.send_sms.SmsLogModel;

public interface ILoginService <T>{
	
	public List<CustomerLogin> getLoginInfoForCustomer(String userName, String password);
	public List<Employee> getLoginInfoForEmployee(String userName, String password);
	public void updateSessioStatus(Employee employee);
	public void updateSessionReset(String userName);
	public List<Employee> getLoginInfoForEmployees(BigDecimal countryId,String userName, String password);
	public List<CustomerLogin> getLoginInfoForCustomers(BigDecimal countryId,String userName, String password);
	
	public void saveLoginLogoutDetails(LoginLogoutHistory loginLogoutHistory);
	public List<LoginLogoutHistory> getLoginLogoutDetails(BigDecimal loginid);
	public List<LoginLogoutHistory> getLastLoginLogoutDetails(String userName, BigDecimal loginId);
	//added by nazish for 3 step security
	public List<CustomerLogin> getPerosnalLogin(BigDecimal countryId, String userName);
	public List<CustomerLogin> getLoginInfoForSecurityAnswer(String userName);
	
	public void updateLoginDetailsLock(BigDecimal customerLoginId, BigDecimal lockCount,Date lockDate,String status);
	public void updateLoginDetailsUnLock(BigDecimal customerLoginId,String ipAddress,Date unLockDate, String unLockBy,String status);
	public void updateLoginDetails(BigDecimal customerLoginId);
	
	public void updateSecurityDetailsLock(BigDecimal customerLoginId, BigDecimal lockCount,Date lockDate,String status);
	
	/**
	 * Added by Rabil on 10/04/2016 *
	  Purpose :To kill partiulcar user session.
	 */
	public void killUserSession(String userName); 
	
	public List<Employee> getLoginInfoForEmployeeForChangePassword(BigDecimal countryId,String userName,String password,String type);
	public void updatePasswordLastUpdated(BigDecimal employeeId, Timestamp timestamp);

	public String checkUserToAccessURL(BigDecimal applicationCountryId , String userName, String requestUrl);
	
	public void lockEmployeeLoginFail(String username,String ipaddress);
	
	public boolean lockEmployeeCheck(String username);
	
	public void unLockEmployeeLogin(String username,String ipaddress,BigDecimal employeeId,boolean lockUnlock);
	
	//To Store Session Value 
    public void dbSaveToken(UserSession userSession);
    
    public List<UserSession> getDBToken(String userName, String ipaddress);
    
    public List<CountryBranch> fetchBranchesForEmployee(BigDecimal employeeId);
    
    public boolean checkIpAddressAllocated(String username,String systemname,String ipaddress,BigDecimal branchId,BigDecimal employeeId);
	
	public List<Employee> getLoginDetailsForEmployee(String civilId);
	
	public EoExOwsLoginCredentials fetchLoginCreditials(String otpBankCode);
	
	public List<EoOwsParamRespcode> fetchResponseCode(String otpBankCode,String responseCode,String callType);
	
	public void saveSmsLog(SmsLogModel smslog);
	
	public void saveApplicationEmailLog(ApplicationEmailLog emaillog);
	
}
