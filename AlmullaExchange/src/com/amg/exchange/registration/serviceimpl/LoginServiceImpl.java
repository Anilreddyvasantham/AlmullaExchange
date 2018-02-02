package com.amg.exchange.registration.serviceimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.common.model.UserSession;
import com.amg.exchange.mail.ApplicationEmailLog;
import com.amg.exchange.registration.dao.ILoginDao;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.CustomerLogin;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.registration.model.LoginLogoutHistory;
import com.amg.exchange.registration.service.ILoginService;
import com.amg.exchange.send_sms.EoExOwsLoginCredentials;
import com.amg.exchange.send_sms.EoOwsParamRespcode;
import com.amg.exchange.send_sms.SmsLogModel;

@SuppressWarnings("serial")
@Service("loginServiceImpl")
public class LoginServiceImpl<T> implements ILoginService<T>, Serializable{
	
	@Autowired
	ILoginDao<T> loginDao;
	
	@Override
	@Transactional
	public List<CustomerLogin> getLoginInfoForCustomer(String userName, String password) {
		return loginDao.getLoginInfoForCustomer(userName, password);
	}

	@Override
	@Transactional
	public List<Employee> getLoginInfoForEmployee(String userName, String password) {
		return loginDao.getLoginInfoForEmployee(userName, password);
	}

	@Override
	@Transactional
	public void updateSessioStatus(Employee employee) {
		loginDao.updateSessioStatus(employee);
	}

	@Override
	@Transactional
	public void updateSessionReset(String userName) {
		loginDao.updateSessionReset(userName);
	}

	@Override
	@Transactional
	public List<Employee> getLoginInfoForEmployees(BigDecimal countryId,String userName, String password) {
		return loginDao.getLoginInfoForEmployees(countryId, userName, password);
	}
	
	@Override
	@Transactional
	public List<CustomerLogin> getLoginInfoForCustomers(BigDecimal countryId,String userName, String password) {
		return loginDao.getLoginInfoForCustomers(countryId, userName, password);
	}
	
	@Override
	@Transactional
	public void saveLoginLogoutDetails(LoginLogoutHistory loginLogoutHistory) {
		loginDao.saveLoginLogoutDetails(loginLogoutHistory);
	}
	
	@Override
	@Transactional
	public List<LoginLogoutHistory> getLoginLogoutDetails(BigDecimal loginid){
		return loginDao.getLoginLogoutDetails(loginid);
	}
	
	@Override
	@Transactional
	public List<LoginLogoutHistory> getLastLoginLogoutDetails(String userName, BigDecimal loginId){
		return loginDao.getLastLoginLogoutDetails(userName, loginId);
	}

	@Override
	@Transactional
	public List<CustomerLogin> getPerosnalLogin(BigDecimal countryId, String userName) {
		return loginDao.getPerosnalLogin(countryId,userName);
	}

	@Override
	@Transactional
	public List<CustomerLogin> getLoginInfoForSecurityAnswer(String userName) {
		return loginDao.getLoginInfoForSecurityAnswer(userName);
	}

	@Override
	@Transactional
	public void updateLoginDetailsLock(BigDecimal customerLoginId,BigDecimal lockCount, Date lockDate, String status) {
		loginDao.updateLoginDetailsLock(customerLoginId, lockCount, lockDate, status);
	}

	@Override
	@Transactional
	public void updateLoginDetailsUnLock(BigDecimal customerLoginId,String ipAddress, Date unLockDate, String unLockBy, String status) {
		loginDao.updateLoginDetailsUnLock(customerLoginId, ipAddress, unLockDate, unLockBy, status);
	}

	@Override
	@Transactional
	public void updateLoginDetails(BigDecimal customerLoginId) {
		loginDao.updateLoginDetails(customerLoginId);
	}

	@Override
	@Transactional
	public void updateSecurityDetailsLock(BigDecimal customerLoginId,BigDecimal lockCount, Date lockDate, String status) {
		loginDao.updateSecurityDetailsLock(customerLoginId, lockCount, lockDate, status);
		
	}
	
	@Override
	@Transactional
	public void killUserSession(String userName) {
		loginDao.killUserSession(userName);
	}

	@Override
	@Transactional
	public List<Employee> getLoginInfoForEmployeeForChangePassword(BigDecimal countryId, String userName, String password, String type) {
		return loginDao.getLoginInfoForEmployeeForChangePassword(countryId, userName, password, type);
	}

	@Override 
	public void updatePasswordLastUpdated(BigDecimal employeeId, Timestamp timestamp) {
		loginDao.updatePasswordLastUpdated(employeeId,timestamp);
		
	}

	@Override
	@Transactional
	public String checkUserToAccessURL(BigDecimal applicationCountryId , String userName, String requestUrl){
		return loginDao.checkUserToAccessURL(applicationCountryId, userName, requestUrl);
	}

	@Override
	@Transactional
	public void lockEmployeeLoginFail(String username,String ipaddress) {
		loginDao.lockEmployeeLoginFail(username,ipaddress);
	}

	@Override
	@Transactional
	public boolean lockEmployeeCheck(String username) {
		return loginDao.lockEmployeeCheck(username);
	}

	@Override
	@Transactional
	public void unLockEmployeeLogin(String username,String ipaddress,BigDecimal employeeId,boolean lockUnlock) {
		loginDao.unLockEmployeeLogin(username, ipaddress, employeeId,lockUnlock);
	}

	@Override
	@Transactional
	public void dbSaveToken(UserSession userSession) {
		loginDao.dbSaveToken(userSession);
		
	}

	@Override
	@Transactional
	public List<UserSession> getDBToken(String userName, String ipaddress) {
		return loginDao.getDBToken(userName,ipaddress);
	}
	
	@Override
	@Transactional
	public List<CountryBranch> fetchBranchesForEmployee(BigDecimal employeeId) {
		return loginDao.fetchBranchesForEmployee(employeeId);
	}
	
	@Override
	@Transactional
	public boolean checkIpAddressAllocated(String username,String systemname,String ipaddress,BigDecimal branchId,BigDecimal employeeId) {
		return loginDao.checkIpAddressAllocated(username, systemname, ipaddress, branchId,employeeId);
	}

	@Override
	@Transactional
	public List<Employee> getLoginDetailsForEmployee(String civilId) {
		return loginDao.getLoginDetailsForEmployee(civilId);
	}
	
	@Override
	@Transactional
	public EoExOwsLoginCredentials fetchLoginCreditials(String otpBankCode) {
		return loginDao.fetchLoginCreditials(otpBankCode);
	}

	@Override
	@Transactional
	public List<EoOwsParamRespcode> fetchResponseCode(String otpBankCode,String responseCode, String callType) {
		return loginDao.fetchResponseCode(otpBankCode, responseCode, callType);
	}

	@Override
	@Transactional
	public void saveSmsLog(SmsLogModel smslog) {
		loginDao.saveSmsLog(smslog);
	}

	@Override
	@Transactional
	public void saveApplicationEmailLog(ApplicationEmailLog emaillog) {
		loginDao.saveApplicationEmailLog(emaillog);
	}
	
}
