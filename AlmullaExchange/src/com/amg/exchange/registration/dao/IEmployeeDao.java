package com.amg.exchange.registration.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.model.RoleWiseCurrencyLimit;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.registration.model.RoleMaster;
import com.amg.exchange.registration.model.ViewArea;
import com.amg.exchange.registration.model.ViewMgrt;
import com.amg.exchange.registration.model.ViewUserType;
import com.amg.exchange.util.AMGException;

public interface IEmployeeDao {
	
	public void addEmployee(Employee employee);
	
	public void deleteEmployee(Employee employee);
	
	public void updateEmployee(Employee employee);
	
	public List<Employee> getEmployees();
	
	public Employee getEmployee(String employeeNumber,String userName);
	
	public void savePassword(BigDecimal id, String passord,String type);

	public RoleWiseCurrencyLimit getEmployeeLimit(BigDecimal emproleId);
	
	public List<Employee>  searchUser(String userName,String employeeNumber);
	
	public BigDecimal getEmployeeLimitFromCashLimit(String limitType,BigDecimal applicationCountryId,BigDecimal limitId);
	
	public  RoleMaster getRoleName(BigDecimal roleId);
	
	public BigDecimal getLimitType(String limitDesc);
	
	public void updateChangePassword(BigDecimal employeeidPk, String password);
	
	public void activateEmployee(Employee employee);
	
	public List<ViewArea> getArea();
	
	public List<ViewMgrt> getDesignation();
	
	public List<Employee> getEmployeesList(String employeeNo ,String userName, String employeeName,BigDecimal countryId,BigDecimal countrybranchId,BigDecimal roleId,String area,String telephone);
	
	public String searchWUUser(BigDecimal wuuserName,String userName);
	
	public String  checkEmail(String email);
	
	public List<CountryMasterDesc> getBusinessCounty(BigDecimal languageId, BigDecimal countryId);

	public List<Employee> toFetchEmployeeDetils(BigDecimal countryId, BigDecimal locationId, BigDecimal employeeId);

	public List<CountryBranch> getAllBranchDetails(BigDecimal countryId, BigDecimal locationId);

	public void saveAllValues(BigDecimal employeePk, BigDecimal toLocationId, BigDecimal webServiceUserName, String webServicePassword, String toLocationIpAddress);

	public void updateChangePasswordforChangePassword(BigDecimal employeeidPk, String password, String type);
	
	public void updateIpAddress(BigDecimal employeeidPk, String ipAddress);
	
	public BigDecimal toFetchAreaCode(BigDecimal branchId);
	
	public String toFetchAreaName(BigDecimal areaCode);
	
	public String toFetchDesignationName(String designation);
	
	public List<ViewUserType> getUserTyperFromView();
	
	public List<Employee> toCheckEmpCodeExist(String employeeNumber);
	
	public String checkECNUMBERwithHRView(String ecNo);
	
	public List<Employee> toCheckEmpNoUserNameExist(String employeeNumber,String userName);
	
	public void callProcForTeleMarketingUpdate(HashMap<String, BigDecimal> inputForTeleMarketing) throws AMGException;
	
	public String searchWUForeignTerminalId(String wuForeignTerminalId,String userName);
	
	public List<Employee> fetchEmployeesByBranch(BigDecimal branchId);
	
	public List<Employee> checkTelephoneNumber(String empTelephoneNumber,String userName);
	
}
