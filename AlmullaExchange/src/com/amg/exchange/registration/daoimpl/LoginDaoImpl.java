package com.amg.exchange.registration.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.common.model.UserSession;
import com.amg.exchange.mail.ApplicationEmailLog;
import com.amg.exchange.registration.dao.ILoginDao;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.CustomerLogin;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.registration.model.LoginLogoutHistory;
import com.amg.exchange.remittance.model.SystemAllocationViewModel;
import com.amg.exchange.send_sms.EoExOwsLoginCredentials;
import com.amg.exchange.send_sms.EoOwsParamRespcode;
import com.amg.exchange.send_sms.SmsLogModel;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.SessionStateManage;

@SuppressWarnings("serial")
@Repository
public class LoginDaoImpl<T> extends CommonDaoImpl<T> implements ILoginDao<T>, Serializable {

	SessionStateManage sessionStateManage = new SessionStateManage();
	Logger LOGGER = Logger.getLogger(LoginDaoImpl.class);

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<CustomerLogin> getLoginInfoForCustomer(String userName, String password) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CustomerLogin.class, "customerLogin");
		criteria.add(Restrictions.eq("userName", userName));
		criteria.add(Restrictions.eq("password", password));

		criteria.setFetchMode("customerLogin.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("customerLogin.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);

		criteria.setFetchMode("customerLogin.fsCustomer", FetchMode.JOIN);
		criteria.createAlias("customerLogin.fsCustomer", "fsCustomer", JoinType.LEFT_OUTER_JOIN);

		criteria.setFetchMode("customerLogin.fsCompanyMaster", FetchMode.JOIN);
		criteria.createAlias("customerLogin.fsCompanyMaster", "fsCompanyMaster", JoinType.LEFT_OUTER_JOIN);

		return (List<CustomerLogin>) findAll(criteria);
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<Employee> getLoginInfoForEmployee(String userName, String password) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Employee.class, "employee");
		criteria.add(Restrictions.eq("userName", userName));
		criteria.add(Restrictions.eq("password", password));

		criteria.setFetchMode("employee.fsRoleMaster", FetchMode.JOIN);
		criteria.createAlias("employee.fsRoleMaster", "fsRoleMaster", JoinType.LEFT_OUTER_JOIN);

		criteria.setFetchMode("employee.fsCountryBranch", FetchMode.JOIN);
		criteria.createAlias("employee.fsCountryBranch", "fsCountryBranch", JoinType.LEFT_OUTER_JOIN);

		criteria.setFetchMode("employee.currencyMaster", FetchMode.JOIN);
		criteria.createAlias("employee.currencyMaster", "currencyMaster", JoinType.LEFT_OUTER_JOIN);

		criteria.setFetchMode("employee.fsCompanyMaster", FetchMode.JOIN);
		criteria.createAlias("employee.fsCompanyMaster", "fsCompanyMaster", JoinType.LEFT_OUTER_JOIN);

		return (List<Employee>) findAll(criteria);
	}

	@Override
	public void updateSessioStatus(Employee employee) {
		employee.setSesionStatus(Constants.ACTIVEEMPLOYEE);
		getSession().update(employee);
	}

	@Override
	public void updateSessionReset(String userName) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Employee.class, "employee");
		dCriteria.add(Restrictions.eq("employee.userName", userName));

		@SuppressWarnings({ "unchecked" })
		Employee employee = ((List<Employee>) findAll(dCriteria)).get(0);
		employee.setSesionStatus(Constants.INACTIVEEMPLOYEE);
		getSession().update(employee);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Employee> getLoginInfoForEmployees(BigDecimal countryId, String userName, String password) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Employee.class, "employee");
		criteria.add(Restrictions.eq("countryId", countryId));
		criteria.add(Restrictions.eq("userName", userName));
		criteria.add(Restrictions.eq("password", password));
		//criteria.add(Restrictions.ne("password", password));
		//criteria.add(Restrictions.eq("status", Constants.ACTIVEEMPLOYEE));
		criteria.add(Restrictions.eq("isActive", Constants.Yes));

		criteria.setFetchMode("employee.fsRoleMaster", FetchMode.JOIN);
		criteria.createAlias("employee.fsRoleMaster", "fsRoleMaster", JoinType.LEFT_OUTER_JOIN);

		criteria.setFetchMode("employee.fsCountryBranch", FetchMode.JOIN);
		criteria.createAlias("employee.fsCountryBranch", "fsCountryBranch", JoinType.LEFT_OUTER_JOIN);
		/*
		criteria.setFetchMode("employee.currencyMaster", FetchMode.JOIN);
		criteria.createAlias("employee.currencyMaster", "currencyMaster", JoinType.LEFT_OUTER_JOIN);
		 */
		criteria.setFetchMode("employee.fsCompanyMaster", FetchMode.JOIN);
		criteria.createAlias("employee.fsCompanyMaster", "fsCompanyMaster", JoinType.LEFT_OUTER_JOIN);

		return (List<Employee>) findAll(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerLogin> getLoginInfoForCustomers(BigDecimal countryId, String userName, String password) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CustomerLogin.class, "customerLogin");
		criteria.add(Restrictions.eq("userName", userName));
		criteria.add(Restrictions.eq("password", password));

		criteria.setFetchMode("customerLogin.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("customerLogin.fsCountryMaster", "fsCountryMaster", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));

		criteria.setFetchMode("customerLogin.fsCustomer", FetchMode.JOIN);
		criteria.createAlias("customerLogin.fsCustomer", "fsCustomer", JoinType.LEFT_OUTER_JOIN);

		criteria.setFetchMode("customerLogin.fsCompanyMaster", FetchMode.JOIN);
		criteria.createAlias("customerLogin.fsCompanyMaster", "fsCompanyMaster", JoinType.LEFT_OUTER_JOIN);
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<CustomerLogin>) findAll(criteria);
	}

	@Override
	public void saveLoginLogoutDetails(LoginLogoutHistory loginLogoutHistory) {
		getSession().saveOrUpdate(loginLogoutHistory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoginLogoutHistory> getLoginLogoutDetails(BigDecimal loginId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(LoginLogoutHistory.class, "loginLogoutHistory");
		criteria.add(Restrictions.eq("loginLogoutHistory.loginLogoutId", loginId));
		return (List<LoginLogoutHistory>) findAll(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoginLogoutHistory> getLastLoginLogoutDetails(String userName, BigDecimal loginId) {

		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(LoginLogoutHistory.class, "loginLogoutHistory");
		detachedCriteria.add(Restrictions.eq("loginLogoutHistory.loginLogoutId", loginId));

		return (List<LoginLogoutHistory>) findAll(detachedCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerLogin> getPerosnalLogin(BigDecimal countryId, String userName) {

		DetachedCriteria criteria = DetachedCriteria.forClass(CustomerLogin.class, "customerLogin");
		criteria.add(Restrictions.eq("userName", userName));

		criteria.setFetchMode("customerLogin.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("customerLogin.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);

		criteria.setFetchMode("customerLogin.fsCustomer", FetchMode.JOIN);
		criteria.createAlias("customerLogin.fsCustomer", "fsCustomer", JoinType.LEFT_OUTER_JOIN);

		criteria.setFetchMode("customerLogin.fsCompanyMaster", FetchMode.JOIN);
		criteria.createAlias("customerLogin.fsCompanyMaster", "fsCompanyMaster", JoinType.LEFT_OUTER_JOIN);

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<CustomerLogin>) findAll(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerLogin> getLoginInfoForSecurityAnswer(String userName) {

		DetachedCriteria criteria = DetachedCriteria.forClass(CustomerLogin.class, "customerLogin");
		criteria.add(Restrictions.eq("userName", userName));

		criteria.setFetchMode("customerLogin.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("customerLogin.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);

		criteria.setFetchMode("customerLogin.fsCustomer", FetchMode.JOIN);
		criteria.createAlias("customerLogin.fsCustomer", "fsCustomer", JoinType.LEFT_OUTER_JOIN);

		criteria.setFetchMode("customerLogin.fsCompanyMaster", FetchMode.JOIN);
		criteria.createAlias("customerLogin.fsCompanyMaster", "fsCompanyMaster", JoinType.LEFT_OUTER_JOIN);

		criteria.setFetchMode("customerLogin.fsBizComponentDataBySecurityQuestion1", FetchMode.JOIN);
		criteria.createAlias("customerLogin.fsBizComponentDataBySecurityQuestion1", "fsBizComponentDataBySecurityQuestion1", JoinType.LEFT_OUTER_JOIN);

		criteria.setFetchMode("customerLogin.fsBizComponentDataBySecurityQuestion2", FetchMode.JOIN);
		criteria.createAlias("customerLogin.fsBizComponentDataBySecurityQuestion2", "fsBizComponentDataBySecurityQuestion2", JoinType.LEFT_OUTER_JOIN);

		criteria.setFetchMode("customerLogin.fsBizComponentDataBySecurityQuestion3", FetchMode.JOIN);
		criteria.createAlias("customerLogin.fsBizComponentDataBySecurityQuestion3", "fsBizComponentDataBySecurityQuestion3", JoinType.LEFT_OUTER_JOIN);

		criteria.setFetchMode("customerLogin.fsBizComponentDataBySecurityQuestion4", FetchMode.JOIN);
		criteria.createAlias("customerLogin.fsBizComponentDataBySecurityQuestion4", "fsBizComponentDataBySecurityQuestion4", JoinType.LEFT_OUTER_JOIN);

		criteria.setFetchMode("customerLogin.fsBizComponentDataBySecurityQuestion5", FetchMode.JOIN);
		criteria.createAlias("customerLogin.fsBizComponentDataBySecurityQuestion5", "fsBizComponentDataBySecurityQuestion5", JoinType.LEFT_OUTER_JOIN);

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<CustomerLogin>) findAll(criteria);

	}

	@Override
	public void updateLoginDetailsLock(BigDecimal customerLoginId,
			BigDecimal lockCount, Date lockDate, String status) {
		CustomerLogin customerLogin=(CustomerLogin) getSession().get(CustomerLogin.class, customerLoginId);
		customerLogin.setLockCnt(lockCount);
		if(lockCount!=null && lockCount.intValue() >= 3){
			customerLogin.setLockDt(lockDate);
			customerLogin.setStatus(status);
			customerLogin.setLockCnt(new BigDecimal(3));
		}
		getSession().update(customerLogin);


	}

	@Override
	public void updateLoginDetailsUnLock(BigDecimal customerLoginId,
			String ipAddress, Date unLockDate, String unLockBy, String status) {
		CustomerLogin customerLogin=(CustomerLogin) getSession().get(CustomerLogin.class, customerLoginId);
		customerLogin.setLockCnt(null);
		customerLogin.setLockDt(null);
		customerLogin.setUnLockIp(ipAddress);
		customerLogin.setUnlockBy(unLockBy);
		customerLogin.setUnlockDt(unLockDate);
		customerLogin.setStatus(status);
		getSession().update(customerLogin);


	}

	@Override
	public void updateLoginDetails(BigDecimal customerLoginId) {
		CustomerLogin customerLogin=(CustomerLogin) getSession().get(CustomerLogin.class, customerLoginId);
		customerLogin.setLockCnt(null);
		getSession().update(customerLogin);



	}

	@Override
	public void updateSecurityDetailsLock(BigDecimal customerLoginId,
			BigDecimal lockCount, Date lockDate, String status) {

		CustomerLogin customerLogin=(CustomerLogin) getSession().get(CustomerLogin.class, customerLoginId);
		customerLogin.setLockCnt(lockCount);
		if(lockCount!=null && lockCount.intValue() >= 5){
			customerLogin.setLockDt(lockDate);
			customerLogin.setStatus(status);
			customerLogin.setLockCnt(new BigDecimal(5));
		}
		getSession().update(customerLogin);

	}

	@Override
	public void killUserSession(String userName)  {
		Connection connection=null;
		try {

			connection =getDataSourceFromHibernateSession();
		} catch (Exception e) {
			e.printStackTrace();
		}
		CallableStatement cs;
		try {
			System.out.println("APPL_SET_ACTION :"+userName);
			String call = " { call APPL_SET_ACTION(?) } ";
			cs = connection.prepareCall(call);
			cs.setString(1, userName);
			cs.execute();
			connection.close();
		}catch (SQLException e) {
			try {
				throw new AMGException(e.getMessage());
			} catch (AMGException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

	@Override
	public List<Employee> getLoginInfoForEmployeeForChangePassword(BigDecimal countryId, String userName, String password, String type) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Employee.class, "employee");
		criteria.add(Restrictions.eq("countryId", countryId));
		criteria.add(Restrictions.eq("userName", userName));
		if(type.equalsIgnoreCase(Constants.LoginPassword)){
			criteria.add(Restrictions.eq("password", password));
		}else if(type.equalsIgnoreCase(Constants.WesternUnionPassword)){
			criteria.add(Restrictions.eq("wuPassword", password));
		}

		criteria.add(Restrictions.eq("isActive", Constants.Yes));

		criteria.setFetchMode("employee.fsRoleMaster", FetchMode.JOIN);
		criteria.createAlias("employee.fsRoleMaster", "fsRoleMaster", JoinType.LEFT_OUTER_JOIN);

		criteria.setFetchMode("employee.fsCountryBranch", FetchMode.JOIN);
		criteria.createAlias("employee.fsCountryBranch", "fsCountryBranch", JoinType.LEFT_OUTER_JOIN);

		criteria.setFetchMode("employee.fsCompanyMaster", FetchMode.JOIN);
		criteria.createAlias("employee.fsCompanyMaster", "fsCompanyMaster", JoinType.LEFT_OUTER_JOIN);

		return (List<Employee>) findAll(criteria);
	}

	@Override
	public void updatePasswordLastUpdated(BigDecimal employeeId, Timestamp timestamp) {
		Employee employee = (Employee) getSession().get(Employee.class, employeeId);
		//employee.setPasswordLastUpdated(timestamp);
		getSession().update(employee);
	}


	public String checkUserToAccessURL(BigDecimal applicationCountryId , String userName, String requestUrl){
		String checkAcess=null;
		Connection connection = null; 
		connection = getDataSourceFromHibernateSession();
		CallableStatement cs;
		try {
			cs = connection.prepareCall(" { call EX_P_VALIDATE_USER_LINK(?,?,?,?)}");
			cs.setBigDecimal(1, applicationCountryId);
			cs.setString(2, userName);
			cs.setString(3, requestUrl);
			cs.registerOutParameter(4, java.sql.Types.VARCHAR);
			cs.execute();
			String status = cs.getString(4);
			if(status!=null && !status.isEmpty()){
				checkAcess = status;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return checkAcess;
	}

	@Override
	public void lockEmployeeLoginFail(String username,String ipaddress) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Employee.class, "employee");
		criteria.add(Restrictions.eq("employee.userName", username));
		criteria.add(Restrictions.eq("employee.isActive",Constants.Yes));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<Employee> lstemp = (List<Employee>) findAll(criteria);

		if(lstemp != null && lstemp.size() != 0){
			for (Employee employee : lstemp) {
				Employee employeeId =(Employee) getSession().get(Employee.class, employee.getEmployeeId());
				if(employeeId.getLockCount() != null){
					employeeId.setLockCount(employeeId.getLockCount().add(BigDecimal.ONE));
				}else{
					employeeId.setLockCount(BigDecimal.ONE);
				}
				if(employeeId.getCreatedBy() == null){
					employeeId.setCreatedBy(sessionStateManage.getUserName());
					employeeId.setCreatedDate(new Date());
				}

				employeeId.setLockDate(new Date());
				employeeId.setUnlockIP(ipaddress);
				employeeId.setUnLockBy(null);
				employeeId.setUnLockDate(null);

				getSession().update(employeeId);
			}
		}

	}

	@Override
	public boolean lockEmployeeCheck(String username) {
		boolean employeechk = false;

		DetachedCriteria criteria = DetachedCriteria.forClass(Employee.class, "employee");
		criteria.add(Restrictions.eq("employee.userName", username));
		criteria.add(Restrictions.isNull("employee.unLockBy"));
		criteria.add(Restrictions.isNull("employee.unLockDate"));
		criteria.add(Restrictions.isNotNull("employee.lockDate"));
		criteria.add(Restrictions.eq("employee.isActive",Constants.Yes));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<Employee> lstemp = (List<Employee>)findAll(criteria);

		if(lstemp != null && lstemp.size() != 0){
			if(lstemp.size()==1){
				Employee emp = lstemp.get(0);
				if(emp.getLockCount() != null && emp.getLockCount().compareTo(new BigDecimal(4)) > 0){
					employeechk = true;
				}
			}
		}


		return employeechk;
	}

	@Override
	public void unLockEmployeeLogin(String username,String ipaddress,BigDecimal employeeId,boolean lockUnlock) {

		if(lockUnlock){

			Employee employee = (Employee) getSession().get(Employee.class, employeeId);

			employee.setUnlockIP(ipaddress);
			employee.setUnLockBy(username);
			employee.setUnLockDate(new Date());
			employee.setModifiedBy(username);
			employee.setModifiedDate(new Date());
			employee.setLockCount(BigDecimal.ZERO);

			getSession().update(employee);

		}else{

			Employee employee1 =(Employee) getSession().get(Employee.class, employeeId);

			employee1.setLockCount(new BigDecimal(6));
			employee1.setLockDate(new Date());
			employee1.setUnlockIP(ipaddress);
			employee1.setUnLockBy(null);
			employee1.setUnLockDate(null);

			getSession().update(employee1);

		}	

	}

	@Override
	public void dbSaveToken(UserSession userSession) {
		// save or update
		getSession().saveOrUpdate(userSession);
		
		// update all ip address token
		String sql = " Update USER_SESSION A set A.USER_TOKEN = '" + userSession.getUserToken() + "'  WHERE A.IP_ADDRESS='" + userSession.getIpaddress() + "' ";
		LOGGER.info("dbSaveToken query=" + sql);
		int query = getSession().createSQLQuery(sql).executeUpdate();
		
	}

	@Override
	public List<UserSession> getDBToken(String userName, String ipaddress) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(UserSession.class, "userSession");
		//Disjunction obj = Restrictions.disjunction();
		//obj.add(Restrictions.eq("userSession.userName", userName));
		//obj.add(Restrictions.eq("userSession.ipaddress", ipaddress));
		//dCriteria.add(obj);
		dCriteria.add(Restrictions.eq("userSession.userName", userName));
		//dCriteria.add(Restrictions.eq("userSession.ipaddress", ipaddress));
		@SuppressWarnings("unchecked")
		List<UserSession> user = (List<UserSession>)findAll(dCriteria);
	
		return user;
	}

	@Override
	public List<CountryBranch> fetchBranchesForEmployee(BigDecimal employeeId) {
		List<CountryBranch> lstBranch = new ArrayList<CountryBranch>();
		DetachedCriteria criteria = DetachedCriteria.forClass(SystemAllocationViewModel.class, "systemAllocationViewModel");
		criteria.add(Restrictions.eq("systemAllocationViewModel.employeeId", employeeId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<SystemAllocationViewModel> lstSysAlloc = (List<SystemAllocationViewModel>) findAll(criteria);

		if(lstSysAlloc != null && lstSysAlloc.size() != 0){
			DetachedCriteria criteria1 = DetachedCriteria.forClass(CountryBranch.class, "countryBranch");

			Disjunction lstjunction = Restrictions.disjunction();
			for (SystemAllocationViewModel systemAllocationViewModel : lstSysAlloc) {
				lstjunction.add(Restrictions.eq("countryBranch.countryBranchId", systemAllocationViewModel.getBranchId()));
			}
			criteria1.add(lstjunction);
			criteria1.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
			lstBranch = (List<CountryBranch>) findAll(criteria1);
		}

		return lstBranch;
	}

	@Override
	public boolean checkIpAddressAllocated(String username,String systemname,String ipaddress,BigDecimal branchId,BigDecimal employeeId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(SystemAllocationViewModel.class, "systemAllocationViewModel");
		criteria.add(Restrictions.eq("systemAllocationViewModel.employeeId", employeeId));
		criteria.add(Restrictions.eq("systemAllocationViewModel.employeeName", username));
		//criteria.add(Restrictions.eq("systemAllocationViewModel.systemName", systemname));
		criteria.add(Restrictions.eq("systemAllocationViewModel.ipAddress", ipaddress));
		criteria.add(Restrictions.eq("systemAllocationViewModel.branchId", branchId));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<SystemAllocationViewModel> lstSysAlloc = (List<SystemAllocationViewModel>) findAll(criteria);

		if(lstSysAlloc != null && lstSysAlloc.size() != 0){
			return true;
		}

		return false;
	}

	@Override
	public List<Employee> getLoginDetailsForEmployee(String civilId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Employee.class, "employee");
		criteria.add(Restrictions.eq("employee.civilId", civilId));
		criteria.add(Restrictions.eq("employee.isActive", Constants.Yes));

		criteria.setFetchMode("employee.fsCountryBranch", FetchMode.JOIN);
		criteria.createAlias("employee.fsCountryBranch", "fsCountryBranch", JoinType.LEFT_OUTER_JOIN);

		criteria.add(Restrictions.not(Restrictions.ilike("employee.userName", "ARMS%")));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<Employee> lstSysAlloc = (List<Employee>) findAll(criteria);
		return (List<Employee>) findAll(criteria);
	}
	
	@Override
	public EoExOwsLoginCredentials fetchLoginCreditials(String otpBankCode) {
		
		EoExOwsLoginCredentials loginCredObj = null;
		
		String sql = " SELECT A.WS_PIN,A.WS_USER_NAME,A.WS_PASSWORD,A.WS_AGENT_ID,A.FLEXIFIELD1 FROM EX_OWS_LOGIN_CREDENTIALS A WHERE BANK_CODE='" + otpBankCode + "' ";

		LOGGER.info("loginCredentials query=" + sql);
		SQLQuery query = getSession().createSQLQuery(sql);
		//List<EoExOwsLoginCredentials> loginCredentials = query.list();
		List<Object> rows = query.list();
		Iterator iterator = rows.iterator();
		while (iterator.hasNext()) {
			loginCredObj = new EoExOwsLoginCredentials();
			Object[] row = (Object[]) iterator.next();
			for (int col = 0; col < row.length; col++) {
				loginCredObj.setWsPin(row[0] == null ? "" : row[0].toString());
				loginCredObj.setWsUserName(row[1] == null ? "" : row[1].toString());
				loginCredObj.setWsPwd(row[2] == null ? "" : row[2].toString());
				loginCredObj.setWsAgentId(row[3] == null ? "" : row[3].toString());
				loginCredObj.setFlexiFiled1(row[4] == null ? "" : row[4].toString());
			}
		}
		
		return loginCredObj;
	}

	@Override
	public List<EoOwsParamRespcode> fetchResponseCode(String otpBankCode,String responseCode, String callType) {
		List<EoOwsParamRespcode> lstRespCode = new ArrayList<EoOwsParamRespcode>();
		
		String sql = "SELECT ACTION_IND,BNKCOD,RSP_CODE,RSP_DESC FROM OWS_PARAM_RESPCODE WHERE BNKCOD='" + otpBankCode + "' AND RSP_CODE='" + responseCode + "' AND (WS_CALL_TYPE='" + callType + "' OR WS_CALL_TYPE='99')";

		LOGGER.info("loginCredentials query=" + sql);
		SQLQuery query = getSession().createSQLQuery(sql);
		List<Object> rows = query.list();
		Iterator iterator = rows.iterator();
		while (iterator.hasNext()) {
			EoOwsParamRespcode respCode = new EoOwsParamRespcode();
			Object[] row = (Object[]) iterator.next();
			for (int col = 0; col < row.length; col++) {
				respCode.setActionInd(row[0] == null ? "" : row[0].toString());
				respCode.setBnkcod(row[1] == null ? "" : row[1].toString());
				respCode.setRspCode(row[2] == null ? "" : row[2].toString());
				respCode.setRspDesc(row[3] == null ? "" : row[3].toString());
			}
			lstRespCode.add(respCode);
		}
		
		return lstRespCode;
	}

	@Override
	public void saveSmsLog(SmsLogModel smslog) {
		getSession().save(smslog);
	}
	
	@Override
	public void saveApplicationEmailLog(ApplicationEmailLog emaillog) {
		getSession().save(emaillog);
	}

}
