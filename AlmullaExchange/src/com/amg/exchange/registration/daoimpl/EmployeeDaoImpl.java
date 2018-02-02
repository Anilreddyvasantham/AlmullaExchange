package com.amg.exchange.registration.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.model.RoleWiseCurrencyLimit;
import com.amg.exchange.registration.dao.IEmployeeDao;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.registration.model.RoleMaster;
import com.amg.exchange.registration.model.ViewArea;
import com.amg.exchange.registration.model.ViewMgrt;
import com.amg.exchange.registration.model.ViewUserType;
import com.amg.exchange.remittance.model.CashLimit;
import com.amg.exchange.remittance.model.CashLimitType;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.SessionStateManage;

@SuppressWarnings("rawtypes")
@Repository
public class EmployeeDaoImpl  extends CommonDaoImpl implements IEmployeeDao,Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	SessionStateManage sessionStateManage = new SessionStateManage();

	@Override
	public void addEmployee(Employee employee) {
		getSession().saveOrUpdate(employee);
	}

	@Override
	public void deleteEmployee(Employee employee) {
		Employee employeeToBeDelete = (Employee)getSession().get(Employee.class, employee.getEmployeeId());
		//employeeToBeDelete.setStatus(Constants.INACTIVEEMPLOYEE);
		employeeToBeDelete.setIsActive(Constants.No);
		employeeToBeDelete.setModifiedBy(sessionStateManage.getUserName());
		employeeToBeDelete.setModifiedDate(new Date());
		getSession().saveOrUpdate(employeeToBeDelete);
	}

	@Override
	public void updateEmployee(Employee employee) {
		getSession().update(employee);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Employee> getEmployees() {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(Employee.class, "employee");
		//dCriteria.add(Restrictions.eq("employee.status", "1"));

		dCriteria.setFetchMode("employee.fsRoleMaster", FetchMode.JOIN);
		dCriteria.createAlias("employee.fsRoleMaster", "fsRoleMaster", JoinType.INNER_JOIN);

		dCriteria.setFetchMode("employee.fsCountryBranch", FetchMode.JOIN);
		dCriteria.createAlias("employee.fsCountryBranch", "fsCountryBranch", JoinType.INNER_JOIN);



		return findAll(dCriteria);
	}

	@Override
	public Employee getEmployee(String employeeNumber,String userName) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(Employee.class, "employee");

		dCriteria.add(Restrictions.eq("employee.employeeNumber", employeeNumber));
		dCriteria.add(Restrictions.eq("employee.userName", userName).ignoreCase());
		//dCriteria.add(Restrictions.eq("employee.status", "1"));


		dCriteria.setFetchMode("employee.fsRoleMaster", FetchMode.JOIN);
		dCriteria.createAlias("employee.fsRoleMaster", "fsRoleMaster", JoinType.INNER_JOIN);

		dCriteria.setFetchMode("employee.fsCountryBranch", FetchMode.JOIN);
		dCriteria.createAlias("employee.fsCountryBranch", "fsCountryBranch", JoinType.INNER_JOIN);

		List<Employee> lstEmployee = findAll(dCriteria);

		if(lstEmployee.size()>0){
			return lstEmployee.get(0);
		} else{
			return null;
		}
	}

	@Override
	public void savePassword(BigDecimal id, String passord,String type) {

		Employee employee = (Employee)getSession().get(Employee.class, id);
		if(type!=null && type.equalsIgnoreCase(Constants.PASSWORD)){
			employee.setPassword(passord);
			if(employee.getCreatedBy() != null){
				employee.setCreatedBy(employee.getCreatedBy());
			}else{
				employee.setCreatedBy(sessionStateManage.getUserName());
			}
			if(employee.getCreatedDate() != null){
				employee.setCreatedDate(employee.getCreatedDate());
			}else{
				employee.setCreatedDate(new Date());
			}
			employee.setPasswordDate(new Date());
			employee.setModifiedBy(sessionStateManage.getUserName());
			employee.setModifiedDate(new Date());
		}else if(type!=null && type.equalsIgnoreCase(Constants.WUPASSWORD)){
			employee.setWuPassword(passord);
			if(employee.getCreatedBy() != null){
				employee.setCreatedBy(employee.getCreatedBy());
			}else{
				employee.setCreatedBy(sessionStateManage.getUserName());
			}
			if(employee.getCreatedDate() != null){
				employee.setCreatedDate(employee.getCreatedDate());
			}else{
				employee.setCreatedDate(new Date());
			}
			employee.setPasswordDate(new Date());
			employee.setModifiedBy(sessionStateManage.getUserName());
			employee.setModifiedDate(new Date());
		}

		getSession().update(employee);
	}

	@Override
	public RoleWiseCurrencyLimit getEmployeeLimit(BigDecimal emproleId) {
		RoleWiseCurrencyLimit checkCurrencyLimit = null;
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(RoleWiseCurrencyLimit.class, "roleWiseCurrencyLimit");

		/*	dCriteria.setFetchMode("employee.fsRoleMaster", FetchMode.JOIN);
		dCriteria.createAlias("employee.fsRoleMaster", "fsRoleMaster", JoinType.INNER_JOIN);*/
		dCriteria.add(Restrictions.eq("roleWiseCurrencyLimit.fsRolemaster.roleId", emproleId));
		
		List<RoleWiseCurrencyLimit> currencylimit = ((List<RoleWiseCurrencyLimit>) findAll(dCriteria));
		
		if(currencylimit !=null && currencylimit.size() != 0){
			checkCurrencyLimit = currencylimit.get(0);
		}

		return checkCurrencyLimit;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Employee> searchUser(String userName,String emmployeeNumber) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Employee.class);
		dCriteria.add(Restrictions.eq("userName", userName).ignoreCase() );
		if(emmployeeNumber!=null){
			dCriteria.add(Restrictions.eq("employeeNumber", emmployeeNumber));
		}
		return (List<Employee>)findAll(dCriteria);
	}

	@Override
	public BigDecimal getEmployeeLimitFromCashLimit(String limitCodeType, BigDecimal applicationCountryId, BigDecimal limitId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CashLimit.class, "cashLimit");

		dCriteria.setFetchMode("cashLimit.limitType", FetchMode.JOIN);
		dCriteria.createAlias("cashLimit.limitType", "lt", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("lt.limitTypeId", limitId));

		dCriteria.setFetchMode("cashLimit.country", FetchMode.JOIN);
		dCriteria.createAlias("cashLimit.country", "country", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("country.countryId", applicationCountryId));

		dCriteria.add(Restrictions.eq("cashLimit.roleLimitType", limitCodeType));

		/*dCriteria.add(Restrictions.eq("cashLimit.limitTypeCode", limitCodeType).ignoreCase());*/
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<CashLimit> cashLimitList = ((List<CashLimit>) findAll(dCriteria));

		BigDecimal limitAmount = BigDecimal.ZERO;
		if (cashLimitList.size() > 0) {
			return cashLimitList.get(0).getLimit2();
		} else {
			return limitAmount;
		}
		// return ((List<CashLimit>) findAll(dCriteria)).get(0).getLimit1();
	}

	@Override
	public BigDecimal getLimitType(String limitDesc) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CashLimitType.class,"cashLimitType");
		dCriteria.add(Restrictions.eq("cashLimitType.limitTypeDesc", limitDesc));  
		return   ((List<CashLimitType>) findAll(dCriteria)).get(0).getLimitTypeId();
	}
	@Override
	public  RoleMaster getRoleName(BigDecimal roleId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(RoleMaster.class,"roleMaster");
		dCriteria.add(Restrictions.eq("roleMaster.roleId", roleId));
		return  (RoleMaster) findAll(dCriteria).get(0);
	}

	@Override
	public void updateChangePassword(BigDecimal employeeidPk, String password) {
		Employee employee=(Employee) getSession().get(Employee.class, employeeidPk);
		employee.setPassword(password);
		getSession().update(employee);
	}

	@Override
	public void activateEmployee(Employee employee) {
		Employee employeeToBeActivate = (Employee)getSession().get(Employee.class, employee.getEmployeeId());
		//employeeToBeActivate.setStatus(Constants.ACTIVEEMPLOYEE);
		employeeToBeActivate.setIsActive(Constants.Yes);
		employeeToBeActivate.setModifiedBy(sessionStateManage.getUserName());
		employeeToBeActivate.setModifiedDate(new Date());
		getSession().saveOrUpdate(employeeToBeActivate);	
	}

	@Override
	public List<ViewArea> getArea() {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(ViewArea.class, "viewArea");
		return (List<ViewArea>)findAll(dCriteria);
	}

	@Override
	public List<ViewMgrt> getDesignation() {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(ViewMgrt.class, "viewMgrt");


		return (List<ViewMgrt>)findAll(dCriteria);

	}

	@Override
	public List<Employee> getEmployeesList(String employeeNo, String userName,
			String employeeName, BigDecimal countryId,
			BigDecimal countrybranchId, BigDecimal roleId, String area,String telephone) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(Employee.class, "employee");
		dCriteria.add(Restrictions.ne("employee.isActive", "D"));

		dCriteria.setFetchMode("employee.fsRoleMaster", FetchMode.JOIN);
		dCriteria.createAlias("employee.fsRoleMaster", "fsRoleMaster", JoinType.INNER_JOIN);

		dCriteria.setFetchMode("employee.fsCountryBranch", FetchMode.JOIN);
		dCriteria.createAlias("employee.fsCountryBranch", "fsCountryBranch", JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq("employee.countryId", countryId));
		if(employeeNo !=null && employeeNo !="" && employeeNo.length()!=0){
			dCriteria.add(Restrictions.eq("employee.employeeNumber", employeeNo));
		}

		if(userName !=null && userName != "" && userName.length() != 0){
			//dCriteria.add(Restrictions.eq("employee.userName", userName));
			dCriteria.add(Restrictions.like("employee.userName", userName, MatchMode.START).ignoreCase());
		}
		if(employeeName != null && employeeName != "" && employeeName.length() != 0){
			//dCriteria.add(Restrictions.eq("employee.employeeName", employeeName));
			dCriteria.add(Restrictions.like("employee.employeeName", employeeName, MatchMode.START).ignoreCase());
		}

		if(countrybranchId != null){
			dCriteria.add(Restrictions.eq("fsCountryBranch.countryBranchId", countrybranchId));
		}

		if(roleId != null){
			dCriteria.add(Restrictions.eq("fsRoleMaster.roleId", roleId));
		}

		if(area != null && area != "" && area.length() != 0){
			dCriteria.add(Restrictions.eq("employee.location", area));
		}
		
		if(telephone != null && telephone != "" && telephone.length() != 0){
			dCriteria.add(Restrictions.eq("employee.telephoneNumber", telephone));
		}

		return findAll(dCriteria);
	}

	@Override
	public String searchWUUser(BigDecimal wuuserName,String userName) {

		String hqlQuery="select a.userName from  Employee a where a.wuUsername =  :wuuserName and a.userName != :userName";
		Query query = getSession().createQuery(hqlQuery);
		query.setParameter("wuuserName", wuuserName);
		query.setParameter("userName", userName);

		List<String> lstwuuserName = query.list();

		String wuuser = "";
		if(lstwuuserName != null && lstwuuserName.size() != 0)
		{
			for (String name : lstwuuserName) {
				if(name != null){
					wuuser = wuuser == "" ? wuuser.concat(name) : wuuser.concat(",").concat(name);
				}
			}
		}
		return wuuser;
	}

	@Override
	public String checkEmail(String email) {
		String hqlQuery="select a.email from  Employee a where a.email =  :email";
		Query query = getSession().createQuery(hqlQuery);
		query.setParameter("email", email);

		List<String> lstEmail =query.list();

		String emailCheck="";
		if(lstEmail.size()>0)
		{
			emailCheck=lstEmail.get(0);
		}

		return emailCheck;
	}

	@Override
	public List<CountryMasterDesc> getBusinessCounty(BigDecimal languageId, BigDecimal countryId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CountryMasterDesc.class, "countryMasterDesc");
		dCriteria.setFetchMode("countryMasterDesc.fsCountryMaster", FetchMode.JOIN);
		dCriteria.createAlias("countryMasterDesc.fsCountryMaster", "fsCountryMaster",  JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCountryMaster.businessCountry", Constants.Yes));
		dCriteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));

		dCriteria.setFetchMode("countryMasterDesc.fsLanguageType", FetchMode.JOIN);
		dCriteria.createAlias("countryMasterDesc.fsLanguageType", "fsLanguageType",  JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));

		dCriteria.addOrder(Order.asc("countryMasterDesc.countryName"));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<CountryMasterDesc> countryMasterDescList=(List<CountryMasterDesc>)findAll(dCriteria); 
		return countryMasterDescList;
	}

	@Override
	public List<Employee> toFetchEmployeeDetils(BigDecimal countryId, BigDecimal locationId, BigDecimal employeeId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Employee.class, "employee");
		dCriteria.setFetchMode("employee.fsCountryBranch", FetchMode.JOIN);
		dCriteria.createAlias("employee.fsCountryBranch", "fsCountryBranch", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("employee.countryId", countryId));
		dCriteria.add(Restrictions.eq("fsCountryBranch.countryBranchId", locationId));
		dCriteria.add(Restrictions.eq("employee.employeeId", employeeId));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<Employee> employeeList = (List<Employee>) findAll(dCriteria);
		return employeeList;
	}

	@Override
	public List<CountryBranch> getAllBranchDetails(BigDecimal countryId, BigDecimal locationId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CountryBranch.class, "countryBranch");
		dCriteria.setFetchMode("countryBranch.countryMaster", FetchMode.JOIN);
		dCriteria.createAlias("countryBranch.countryMaster", "countryMaster",JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("countryMaster.countryId", countryId));
		dCriteria.add(Restrictions.eq("countryBranch.isActive", Constants.Yes));
		dCriteria.add(Restrictions.ne("countryBranch.countryBranchId", locationId));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		dCriteria.addOrder(Order.asc("countryBranch.branchName"));
		List<CountryBranch> countryBranchList = (List<CountryBranch>) findAll(dCriteria);
		return countryBranchList;
	}

	@Override
	public void saveAllValues(BigDecimal employeePk, BigDecimal toLocationId, BigDecimal webServiceUserName, String webServicePassword, String toLocationIpAddress) {
		Employee employee= (Employee) getSession().get(Employee.class, employeePk);
		CountryBranch branch=new CountryBranch();
		branch.setCountryBranchId(toLocationId);
		employee.setFsCountryBranch(branch);
		employee.setWuUsername(webServiceUserName);
		employee.setWuPassword(webServicePassword);
		employee.setIpAddress(toLocationIpAddress);
		getSession().saveOrUpdate(employee);    
	}

	@Override
	public void updateChangePasswordforChangePassword(BigDecimal employeeidPk,String password, String type) {
		Employee employee=(Employee) getSession().get(Employee.class, employeeidPk);
		
		if(type.equalsIgnoreCase(Constants.LoginPassword)){
			employee.setPassword(password);
		}else if(type.equalsIgnoreCase(Constants.WesternUnionPassword)){
			employee.setWuPassword(password);
		}
		
		employee.setPasswordDate(new Date());
		employee.setModifiedBy(sessionStateManage.getUserName());
		employee.setModifiedDate(new Date());
		
		getSession().update(employee);
	}

	@Override
	public void updateIpAddress(BigDecimal employeeidPk, String ipAddress) {
		Employee employee=(Employee) getSession().get(Employee.class, employeeidPk);
		employee.setIpAddress(ipAddress);
		getSession().update(employee);
	}

	@Override
	public BigDecimal toFetchAreaCode(BigDecimal branchId) {
		BigDecimal areCode=null;
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CountryBranch.class, "countryBranch");
		dCriteria.add(Restrictions.eq("countryBranch.countryBranchId", branchId));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<CountryBranch> countryBranchList = (List<CountryBranch>) findAll(dCriteria);
		if(countryBranchList.size()>0){
			areCode=countryBranchList.get(0).getAreaCode();
		}
		
		return areCode;
	}

	@Override
	public String toFetchAreaName(BigDecimal areaCode) {
		String areaFullName=null;
		List<ViewArea> lstAllrea=toFetchAllArea();
		if(lstAllrea.size()>0){
			for (ViewArea viewArea : lstAllrea) {
				if((new BigDecimal(viewArea.getAreaNo())).compareTo(areaCode) ==0){
					areaFullName=viewArea.getAreaDesc();
				}
			}
		}
		/*DetachedCriteria dCriteria = DetachedCriteria.forClass(ViewArea.class, "viewArea");
		dCriteria.add(Restrictions.eq("viewArea.areaNo", areaCode));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<ViewArea> viewAreaList = (List<ViewArea>) findAll(dCriteria);
		if(viewAreaList.size()>0){
			areaFullName=viewAreaList.get(0).getAreaDesc();
		}*/
		return areaFullName;
	}

	

	private List<ViewArea> toFetchAllArea() {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(ViewArea.class, "viewArea");
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<ViewArea> viewAreaList = (List<ViewArea>) findAll(dCriteria);
		return viewAreaList;
	}

	@Override
	public String toFetchDesignationName(String designation) {
		String designName=null;
		DetachedCriteria dCriteria = DetachedCriteria.forClass(ViewMgrt.class, "viewMgrt");
		dCriteria.add(Restrictions.eq("viewMgrt.mgrTyp", designation));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<ViewMgrt> viewAreaList = (List<ViewMgrt>) findAll(dCriteria);
		if(viewAreaList.size()>0){
			designName=viewAreaList.get(0).getFuDesc();
		}
		return designName;
	}

	@Override
	public List<ViewUserType> getUserTyperFromView() {
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(ViewUserType.class, "viewUserType");
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<ViewUserType> viewUserType = (List<ViewUserType>) findAll(dCriteria);
		
		return viewUserType;
	}
	
	@Override
	public List<Employee> toCheckEmpCodeExist(String employeeNumber) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Employee.class,"employee");
		dCriteria.add(Restrictions.eq("employee.employeeNumber", employeeNumber));
		dCriteria.setFetchMode("employee.fsRoleMaster", FetchMode.JOIN);
		dCriteria.createAlias("employee.fsRoleMaster", "fsRoleMaster", JoinType.LEFT_OUTER_JOIN);
		dCriteria.setFetchMode("employee.fsCountryBranch", FetchMode.JOIN);
		dCriteria.createAlias("employee.fsCountryBranch", "fsCountryBranch", JoinType.LEFT_OUTER_JOIN);
		dCriteria.setFetchMode("employee.fsCompanyMaster", FetchMode.JOIN);
		dCriteria.createAlias("employee.fsCompanyMaster", "fsCompanyMaster", JoinType.LEFT_OUTER_JOIN);
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<Employee> lstEmp=(List<Employee>)findAll(dCriteria);
		return lstEmp;
	}

	@Override
	public String checkECNUMBERwithHRView(String ecNo) {
		
		String EmployeeName = null;
		String queryString = "Select NAME from V_HR_EMPLOYEE where EMPNO = ? ";
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(queryString).setBigDecimal(0, new BigDecimal(ecNo));
		
		List list = query.list();
		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			String row = (String) iterator.next();
			if(row != null) {			
				EmployeeName = row;
			}
		}
			
		return EmployeeName;
	}

	@Override
	public List<Employee> toCheckEmpNoUserNameExist(String employeeNumber,String userName) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Employee.class,"employee");
		dCriteria.add(Restrictions.eq("employee.employeeNumber", employeeNumber));
		dCriteria.add(Restrictions.eq("employee.userName", userName));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<Employee> lstEmp=(List<Employee>)findAll(dCriteria);
		return lstEmp;
	}

	@Override
	public void callProcForTeleMarketingUpdate(HashMap<String, BigDecimal> inputForTeleMarketing) throws AMGException {
		
		Connection connection = null;
		CallableStatement cs = null;
		
		try {
			connection = getDataSourceFromHibernateSession();
			String call = " { call EX_TELEMART_ALLOCATE (?,?,?) } ";
			cs = connection.prepareCall(call);
			
			cs.setBigDecimal(1, inputForTeleMarketing.get("EmployeeId"));
			cs.setBigDecimal(2, inputForTeleMarketing.get("OldBranchId"));
			cs.setString(3, sessionStateManage.getUserName());
			
			cs.executeUpdate();
			
		} catch (Exception e) {
			String erromsg = "EX_TELEMART_ALLOCATE" + " : " + e.getMessage();
			throw new AMGException(erromsg);
		} finally {
			try {
				cs.close();
				connection.close();
			} catch (SQLException e) {
				String erromsg = "EX_TELEMART_ALLOCATE" + " : " + e.getMessage();
				throw new AMGException(erromsg);
			}
		}
		
	}
	
	@Override
	public String searchWUForeignTerminalId(String wuForeignTerminalId,String userName) {
		String hqlQuery="select a.userName from  Employee a where a.wuForeignTerminalId =  :wuForeignTerminalId and a.userName != :userName";
		Query query = getSession().createQuery(hqlQuery);
		query.setParameter("wuForeignTerminalId", wuForeignTerminalId);
		query.setParameter("userName", userName);

		List<String> lstwuuserName = query.list();

		String wuuser = "";
		if(lstwuuserName != null && lstwuuserName.size() != 0)
		{
			for (String name : lstwuuserName) {
				if(name != null){
					wuuser = wuuser == "" ? wuuser.concat(name) : wuuser.concat(",").concat(name);
				}
			}
		}
		return wuuser;
	}

	@Override
	public List<Employee> fetchEmployeesByBranch(BigDecimal branchId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Employee.class, "employee");
		dCriteria.add(Restrictions.eq("fsCountryBranch.countryBranchId", branchId));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<Employee> employeeList = (List<Employee>) findAll(dCriteria);
		return employeeList;
	}

	@Override
	public List<Employee> checkTelephoneNumber(String empTelephoneNumber,String userName) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Employee.class, "employee");
		dCriteria.add(Restrictions.eq("employee.telephoneNumber", empTelephoneNumber));
		dCriteria.add(Restrictions.ne("employee.userName", userName));
		dCriteria.add(Restrictions.ne("employee.isActive", "D"));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<Employee> employeeList = (List<Employee>) findAll(dCriteria);
		return employeeList;
	}
	
}

