package com.amg.exchange.loyalty.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.loyalty.model.LoyaltyPrivileges;
import com.amg.exchange.loyalty.dao.ILoyaltyPrivilegesDao;
import com.amg.exchange.registration.model.Employee;

@Repository
@SuppressWarnings("unchecked")
public class LoyaltyPrivilegeDaoImpl<T> extends CommonDaoImpl<T> implements ILoyaltyPrivilegesDao, Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(LoyaltyPrivilegeDaoImpl.class);


	@Override
	public void save(LoyaltyPrivileges LoyaltyPrivileges) {
		getSession().saveOrUpdate(LoyaltyPrivileges);
	}

	@Override
	public List<Employee> getEmployeeList(BigDecimal countryId) {
		LOGGER.info("Entering into getEmployeeList method");
		LOGGER.info("countryId -" + countryId);
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Employee.class, "employee");
		dCriteria.add(Restrictions.eq("employee.countryId", countryId));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exit into getEmployeeList method");
		return (List<Employee>) findAll(dCriteria);
	}

	@Override
	public LoyaltyPrivileges populateValues(String userName) {
		DetachedCriteria criteria = DetachedCriteria.forClass(LoyaltyPrivileges.class, "loyaltyPrivileges");
		criteria.add(Restrictions.eq("loyaltyPrivileges.userName", userName));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		System.out.println("Entering into populateData method");
		List<LoyaltyPrivileges> loyaltyPrivilegesList = (List<LoyaltyPrivileges>) findAll(criteria);
		if (null != loyaltyPrivilegesList) {
			LOGGER.info("^^^^^^" + loyaltyPrivilegesList.size());
			if (!loyaltyPrivilegesList.isEmpty()) {
				return loyaltyPrivilegesList.get(0);
			} else {
				return null;
			}
		}
		return null;
	}

	@Override
	public BigDecimal getEmployeeId(String userName) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Employee.class, "employee");
		criteria.add(Restrictions.eq("employee.userName", userName));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		System.out.println("Entering into populateData method");
		List<Employee> employeeList = (List<Employee>) findAll(criteria);
		if (null != employeeList) {
			LOGGER.info("^^^^^^" + employeeList.size());
			if (!employeeList.isEmpty()) {
				return employeeList.get(0).getEmployeeId();
			} else {
				return null;
			}
		}
		return null;
	}
	
}
