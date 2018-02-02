package com.amg.exchange.registration.serviceimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.model.RoleWiseCurrencyLimit;
import com.amg.exchange.registration.dao.IEmployeeDao;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.registration.model.RoleMaster;
import com.amg.exchange.registration.model.ViewArea;
import com.amg.exchange.registration.model.ViewMgrt;
import com.amg.exchange.registration.model.ViewUserType;
import com.amg.exchange.registration.service.IEmployeeService;
import com.amg.exchange.util.AMGException;

@Service("employeeServiceImpl")
public class EmployeeServiceImpl implements IEmployeeService,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	IEmployeeDao employeeDao; 

	@Override
	@Transactional
	public void addEmployee(Employee employee) {
		employeeDao.addEmployee(employee);
	}

	@Override
	@Transactional
	public void deleteEmployee(Employee employee) {
		employeeDao.deleteEmployee(employee);

	}

	@Override
	@Transactional
	public void updateEmployee(Employee employee) {
		employeeDao.updateEmployee(employee);

	}

	@Override
	@Transactional
	public List<Employee> getEmployees() {
		return employeeDao.getEmployees();
	}

	@Override
	@Transactional
	public Employee getEmployee(String employeeNumber,String userName) {
		return employeeDao.getEmployee(employeeNumber,userName);
	}

	@Override
	@Transactional
	public void savePassword(BigDecimal id, String passord,String type) {
		employeeDao.savePassword(id, passord, type);
	}

	@Override
	@Transactional
	public RoleWiseCurrencyLimit getEmployeeLimit(BigDecimal emproleId) {
		// TODO Auto-generated method stub
		return employeeDao.getEmployeeLimit(emproleId);
	}

	@Override
	@Transactional
	public List<Employee> searchUser(String userName,String employeeNumber) {
		return employeeDao.searchUser(userName,employeeNumber);
	}
	@Override
	@Transactional
	public  BigDecimal getEmployeeLimitFromCashLimit(String limitType,BigDecimal applicationCountryId,BigDecimal limitId) {

		return employeeDao.getEmployeeLimitFromCashLimit(limitType,applicationCountryId,limitId);
	}

	@Override
	@Transactional
	public  RoleMaster getRoleName(BigDecimal roleId) {

		return employeeDao.getRoleName(roleId);
	}

	@Override
	@Transactional
	public BigDecimal getLimitType(String limitDesc) {

		return employeeDao.getLimitType(limitDesc);
	}

	@Override
	@Transactional
	public void updateChangePassword(BigDecimal employeeidPk, String password) {
		employeeDao.updateChangePassword(employeeidPk, password);	

	}

	@Override
	@Transactional
	public void activateEmployee(Employee employee) {
		employeeDao.activateEmployee(employee);
	}

	@Override
	@Transactional
	public List<ViewArea> getArea() {
		return employeeDao.getArea();
	}

	@Override
	@Transactional
	public List<ViewMgrt> getDesignation() {
		return employeeDao.getDesignation();
	}

	@Override
	@Transactional
	public List<Employee> getEmployeesList(String employeeNo,
			String userName, String employeeName, BigDecimal countryId,
			BigDecimal countrybranchId, BigDecimal roleId, String area,String telephone) {
		return employeeDao.getEmployeesList(employeeNo, userName, employeeName, countryId, countrybranchId, roleId, area,telephone);
	}

	@Override
	@Transactional
	public String searchWUUser(BigDecimal wuuserName,String userName) {
		return employeeDao.searchWUUser(wuuserName,userName);
	}

	@Override
	@Transactional
	public String checkEmail(String email) {
		return employeeDao.checkEmail(email);
	}

	//Employment Assignment Services
	@Override
	@Transactional
	public List<CountryMasterDesc> getBusinessCounty(BigDecimal languageId, BigDecimal countryId) {
		return employeeDao.getBusinessCounty(languageId,countryId);
	}

	@Override
	@Transactional
	public List<Employee> toFetchEmployeeDetils(BigDecimal countryId, BigDecimal locationId, BigDecimal employeeId) {
		return employeeDao.toFetchEmployeeDetils(countryId,locationId,employeeId);
	}

	@Override
	@Transactional
	public List<CountryBranch> getAllBranchDetails(BigDecimal countryId, BigDecimal locationId) {
		return employeeDao.getAllBranchDetails(countryId,locationId);
	}

	@Override
	@Transactional
	public void saveAllValues(BigDecimal employeePk, BigDecimal toLocationId, BigDecimal webServiceUserName, String webServicePassword, String toLocationIpAddress) {
		employeeDao.saveAllValues(employeePk,toLocationId,webServiceUserName,webServicePassword,toLocationIpAddress);
	}

	@Override
	@Transactional
	public void updateChangePasswordforChangePassword(BigDecimal employeeidPk, String password, String type) {
		employeeDao.updateChangePasswordforChangePassword(employeeidPk, password, type);
	}

	@Override
	@Transactional
	public void updateIpAddress(BigDecimal employeeidPk, String ipAddress) {
		employeeDao.updateIpAddress(employeeidPk, ipAddress);
	}

	//added koti 15/09/2016 to Fecth area code
	@Override
	@Transactional
	public BigDecimal toFetchAreaCode(BigDecimal branchId) {
		return employeeDao.toFetchAreaCode(branchId);
	}

	@Override
	@Transactional
	public String toFetchAreaName(BigDecimal areaCode) {
		return employeeDao.toFetchAreaName(areaCode);
	}

	@Override
	@Transactional
	public String toFetchDesignationName(String designation) {
		return employeeDao.toFetchDesignationName(designation);
	}

	@Override
	@Transactional
	public List<ViewUserType> getUserTyperFromView() {
		
		return employeeDao.getUserTyperFromView();
	}
	
	@Override
	@Transactional
	public List<Employee> toCheckEmpCodeExist(String employeeNumber) {
		return employeeDao.toCheckEmpCodeExist(employeeNumber);
	}

	@Override
	@Transactional
	public String checkECNUMBERwithHRView(String ecNo) {
		return employeeDao.checkECNUMBERwithHRView(ecNo);
	}

	@Override
	@Transactional
	public List<Employee> toCheckEmpNoUserNameExist(String employeeNumber,	String userName) {
		return employeeDao.toCheckEmpNoUserNameExist(employeeNumber, userName);
	}

	@Override
	@Transactional
	public void callProcForTeleMarketingUpdate(HashMap<String, BigDecimal> inputForTeleMarketing) throws AMGException{
		employeeDao.callProcForTeleMarketingUpdate(inputForTeleMarketing);
	}
	
	@Override
	@Transactional
	public String searchWUForeignTerminalId(String wuForeignTerminalId,String userName) {
		return employeeDao.searchWUForeignTerminalId(wuForeignTerminalId,userName);
	}

	@Override
	@Transactional
	public List<Employee> fetchEmployeesByBranch(BigDecimal branchId) {
		return employeeDao.fetchEmployeesByBranch(branchId);
	}

	@Override
	@Transactional
	public List<Employee> checkTelephoneNumber(String empTelephoneNumber,String userName) {
		return employeeDao.checkTelephoneNumber(empTelephoneNumber,userName);
	}

}
