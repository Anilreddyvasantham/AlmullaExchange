package com.amg.exchange.remittance.daoimpl;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.remittance.dao.IParameterSecurityMaintanceDao;
import com.amg.exchange.remittance.model.ParameterGrant;
import com.amg.exchange.remittance.model.ParameterMaster;
import com.amg.exchange.util.Constants;
@Repository
public class ParameterSecurityMaintainceDaoimpl<T> extends CommonDaoImpl<T> implements IParameterSecurityMaintanceDao {

	  @SuppressWarnings("unchecked")
	  @Override
	  public List<ParameterMaster> getParameterRuleId(BigDecimal parameterid) {
		    DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ParameterMaster.class, "parameterMaster");
			detachedCriteria.add(Restrictions.eq("parameterMaster.parameterMasterId", parameterid));
			detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
			return (List<ParameterMaster>) findAll(detachedCriteria);
	  }

	  @SuppressWarnings("unchecked")
	  @Override
	  public List<Employee> getAllBranchList(BigDecimal branchid) {
		    DetachedCriteria criteria = DetachedCriteria.forClass(Employee.class, "employee"); 
		    	criteria.setFetchMode("employee.fsCountryBranch", FetchMode.JOIN);
			criteria.createAlias("employee.fsCountryBranch", "fsCountryBranch", JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("fsCountryBranch.countryBranchId", branchid));
			criteria.add(Restrictions.eq("employee.isActive", Constants.Yes));
			criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
			return (List<Employee>) findAll(criteria);
	  }

	  @Override
	  public void save(ParameterGrant parameterGrant) {
		  getSession().saveOrUpdate(parameterGrant);
		    
	  }

	  @SuppressWarnings("unchecked")
	  @Override
	  public List<ParameterGrant> getAllDetailsFromDBAll(BigDecimal countryBranchid, BigDecimal userId, String userName) {
		    List<ParameterGrant> lstParameterGrants=null;
		    try{
		    DetachedCriteria criteria = DetachedCriteria.forClass(ParameterGrant.class, "parameterGrant"); 
		    	criteria.setFetchMode("parameterGrant.fsCountryBranch", FetchMode.JOIN);
			criteria.createAlias("parameterGrant.fsCountryBranch", "fsCountryBranch", JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("fsCountryBranch.countryBranchId", countryBranchid));
			criteria.setFetchMode("parameterGrant.userId", FetchMode.JOIN);
			criteria.createAlias("parameterGrant.userId", "userId", JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("userId.employeeId", userId));
			criteria.add(Restrictions.eq("parameterGrant.userName", userName));
			criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
			lstParameterGrants=(List<ParameterGrant>) findAll(criteria);
			return lstParameterGrants;
		    }catch(Exception exception){
			     System.out.println("exception ocuured while fetching time from parameter grant:::::::::"+exception.getMessage());
			   
		    }
		    return lstParameterGrants;
			   
	  }
	  @SuppressWarnings("unchecked")
	  @Override
	  public List<String> getParameterCode(String query) {
		    DetachedCriteria criteria = DetachedCriteria.forClass(ParameterMaster.class, "parameterMaster");
			criteria.add(Restrictions.like("parameterMaster.recordId", query, MatchMode.ANYWHERE).ignoreCase());
			criteria.setProjection(Projections.property("parameterMaster.recordId"));
			criteria.addOrder(Order.asc("parameterMaster.recordId"));
			criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		    List<String> lstParameterMaster = (List<String>) findAll(criteria);
			return lstParameterMaster;
	  }
	  @SuppressWarnings("unchecked")
	  @Override
	  public List<ParameterMaster> tofetchAllRecordsFromParameterMaster() {
		    DetachedCriteria criteria = DetachedCriteria.forClass(ParameterMaster.class, "parameterMaster");
		    criteria.add(Restrictions.eq("parameterMaster.isActive", Constants.Yes));
		    criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		    List<ParameterMaster> lstParameterMaster = (List<ParameterMaster>) findAll(criteria);
			return lstParameterMaster;
	  }

	  @Override
	  public String toFetchRecordIdFromParameterMaster(BigDecimal parameterId) {
		    String recordId= null;
		    DetachedCriteria criteria = DetachedCriteria.forClass(ParameterMaster.class, "parameterMaster");
		    criteria.add(Restrictions.eq("parameterMaster.parameterMasterId", parameterId));
		    criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		    List<ParameterMaster> lstParameterMaster = (List<ParameterMaster>) findAll(criteria);
		    if(lstParameterMaster.size() !=0 && lstParameterMaster != null){
			      recordId=lstParameterMaster.get(0).getRecordId();    
		    }
			return recordId;
	  }

	  @Override
	  public List<Employee> toFetchEmployeeRecords(String userName, BigDecimal branchid) {
		    DetachedCriteria criteria = DetachedCriteria.forClass(Employee.class, "employee");
		    criteria.setFetchMode("employee.fsCountryBranch", FetchMode.JOIN);
		    criteria.createAlias("employee.fsCountryBranch", "fsCountryBranch", JoinType.INNER_JOIN);
		    criteria.add(Restrictions.eq("fsCountryBranch.countryBranchId", branchid));
		    criteria.add(Restrictions.eq("employee.isActive", Constants.Yes));
		    criteria.add(Restrictions.ilike("employee.userName", userName, MatchMode.ANYWHERE));
		    criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		    List<Employee> lstEmployee = (List<Employee>) findAll(criteria);
		    return lstEmployee;
	  }

	@Override
	public List<ParameterGrant> getAllDetailsFromDBAll(BigDecimal countryBranchid, BigDecimal userId, String userName,String parameterMaster) {
		List<ParameterGrant> lstParameterGrants=null;
		try{
			DetachedCriteria criteria = DetachedCriteria.forClass(ParameterGrant.class, "parameterGrant"); 
			criteria.setFetchMode("parameterGrant.fsCountryBranch", FetchMode.JOIN);
			criteria.createAlias("parameterGrant.fsCountryBranch", "fsCountryBranch", JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("fsCountryBranch.countryBranchId", countryBranchid));
			criteria.setFetchMode("parameterGrant.userId", FetchMode.JOIN);
			criteria.createAlias("parameterGrant.userId", "userId", JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("userId.employeeId", userId));
			criteria.add(Restrictions.eq("parameterGrant.userName", userName));
			if(parameterMaster!=null){
				criteria.add(Restrictions.eq("parameterGrant.recordId", parameterMaster));
			}
			criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
			lstParameterGrants=(List<ParameterGrant>) findAll(criteria);
		}catch(Exception exception){
			System.out.println("exception ocuured while fetching time from parameter grant:::::::::"+exception.getMessage());
		}
		
		return lstParameterGrants;

	}

	

}
