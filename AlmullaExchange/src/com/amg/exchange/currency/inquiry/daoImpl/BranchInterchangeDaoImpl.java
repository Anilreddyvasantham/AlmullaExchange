package com.amg.exchange.currency.inquiry.daoImpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.currency.inquiry.dao.IBranchInterchangeDao;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.util.Constants;
@SuppressWarnings("rawtypes")
@Repository
public class BranchInterchangeDaoImpl<T> extends CommonDaoImpl<T> implements IBranchInterchangeDao, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1147603872261428592L;

	
	@Override
	public void saveOrUpdate(Employee employee) {
		// TODO Auto-generated method stub
		getSession().saveOrUpdate(employee);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateRecord(BigDecimal employeeId,BigDecimal countryBranchId,String location){
		
		Employee employee=(Employee) getSession().get(Employee.class, employeeId);
		
		CountryBranch countryBranch = new CountryBranch();
		countryBranch.setCountryBranchId(countryBranchId);
		employee.setFsCountryBranch(countryBranch);
		employee.setLocation(location);
		
		
		
		getSession().saveOrUpdate(employee);

	}
	
	@Override
	public List<CountryBranch> getCountryBranchList(BigDecimal appCountryId,BigDecimal countryBranchId) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(CountryBranch.class, "countryBranch");

		criteria.setFetchMode("countryBranch.countryMaster", FetchMode.JOIN);
		criteria.createAlias("countryBranch.countryMaster", "countryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("countryMaster.countryId", appCountryId));
		criteria.add(Restrictions.eq("countryBranch.isActive", Constants.Yes));
		
		criteria.add(Restrictions.ne("countryBranch.countryBranchId", countryBranchId));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<CountryBranch> objList = (List<CountryBranch>) findAll(criteria);

		System.out.println("" + objList.size());

		if (objList.isEmpty())
			return null;

		return objList;
	}
	
	@Override
	public List<Employee> getAllEmployeeList(BigDecimal appCountryId) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(Employee.class, "employee");
		
		
		criteria.add(Restrictions.eq("employee.countryId", appCountryId));
		//criteria.add(Restrictions.eq("countryBranch.branchId", branchId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<Employee> objList = (List<Employee>) findAll(criteria);

		System.out.println("" + objList.size());

		if (objList.isEmpty())
			return null;

		return objList;
	}
	
	@Override
	public List<Employee> getEmployeeList(BigDecimal employeeId) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(Employee.class, "employee");
		criteria.add(Restrictions.eq("employee.employeeId", employeeId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<Employee> objList = (List<Employee>) findAll(criteria);

		System.out.println("" + objList.size());

		if (objList.isEmpty())
			return null;

		return objList;
	}


}
