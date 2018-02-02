package com.amg.exchange.highvalue.client.daoImpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.complaint.model.ComplaintPendingEnquiryView;
import com.amg.exchange.highvalue.client.dao.IHighValueClientDao;
import com.amg.exchange.highvalue.client.model.HighValueClient;
import com.amg.exchange.highvalue.client.model.HighValueTransDetailView;
import com.amg.exchange.highvalue.client.model.HighValueTransView;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.testkey.model.TestKeyValues;
import com.amg.exchange.util.Constants;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Repository
public class HighValueClientDaoImpl extends CommonDaoImpl implements IHighValueClientDao {

	  @Override
	  public List<Employee> toFetchBasedOnLocationFromEmployee(BigDecimal locationId) {
		    DetachedCriteria criteria = DetachedCriteria.forClass(Employee.class, "employee");
		    criteria.setFetchMode("employee.fsCountryBranch", FetchMode.JOIN);
		    criteria.createAlias("employee.fsCountryBranch", "fsCountryBranch", JoinType.INNER_JOIN);
		    criteria.add(Restrictions.eq("fsCountryBranch.countryBranchId", locationId));
		    criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		    List<Employee> lstEmployee = (List<Employee>) findAll(criteria);
		    return lstEmployee;
	  }

	  @Override
	  public List<HighValueTransView> toFetchAllDetilsBasedOnLocationId(BigDecimal locationId) {
		    DetachedCriteria criteria = DetachedCriteria.forClass(HighValueTransView.class, "highValueTransView");
		    criteria.add(Restrictions.eq("highValueTransView.locationId", locationId));
		    criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		    List<HighValueTransView> lstHighValueTransView = (List<HighValueTransView>) findAll(criteria);
		    return lstHighValueTransView;
	  }

	  @Override
	  public void save(HighValueClient highValueClient) {
		   getSession().saveOrUpdate(highValueClient);
	  }

	  @Override
	  public BigDecimal toFetchHighValuePkBasedOnCustomerReferenceNo(BigDecimal customerReferenceNo) {
		    DetachedCriteria criteria = DetachedCriteria.forClass(HighValueClient.class, "highValueClient");
		    criteria.add(Restrictions.eq("highValueClient.customerReferenceNo", customerReferenceNo));
		    criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		    List<HighValueClient> lstHighValueClient = (List<HighValueClient>) findAll(criteria);
		    return lstHighValueClient.get(0).getHighValueClientId();
	  }

	  @Override
	  public void saveOrUpdate(BigDecimal highValueClientPk,Date visitDate,String visitBy) {
		    HighValueClient highValueClient = (HighValueClient) getSession().get(HighValueClient.class, highValueClientPk);
		    highValueClient.setVisitBy(visitBy);
		    highValueClient.setVisitDate(visitDate);
		    getSession().saveOrUpdate(highValueClient);
	  }

	  @Override
	  public String toFetchBasedOnIdTogetName(BigDecimal visitBy) {
		    DetachedCriteria criteria = DetachedCriteria.forClass(Employee.class, "employee");
		    criteria.add(Restrictions.eq("employee.employeeId", visitBy));
		    criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		    List<Employee> lstEmployee = (List<Employee>) findAll(criteria);
		    return lstEmployee.get(0).getUserName();
	  }

	  /*Scheduled Customer Visit Service Statred*/
	  @Override
	  public List<HighValueTransDetailView> toFetchAllDetailsFromView(BigDecimal locationId, Date visitDate, BigDecimal countryId) {
		    DetachedCriteria criteria = DetachedCriteria.forClass(HighValueTransDetailView.class, "highValueTransDetailView");
		    criteria.add(Restrictions.eq("highValueTransDetailView.locationId", locationId));
		    criteria.add(Restrictions.eq("highValueTransDetailView.visitDate", visitDate));
		    criteria.add(Restrictions.eq("highValueTransDetailView.applicationCountryId", countryId));
		    criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		    List<HighValueTransDetailView> lstHighValueTransDetailView = (List<HighValueTransDetailView>) findAll(criteria);
		    return lstHighValueTransDetailView;
	  }

	  @Override
	  public String toFetchnationalityNameBasedOnNationalityId(BigDecimal nationality) {
		    DetachedCriteria criteria = DetachedCriteria.forClass(CountryMasterDesc.class, "countryMasterDesc");
		    criteria.setFetchMode("countryMasterDesc.fsCountryMaster", FetchMode.JOIN);
		    criteria.createAlias("countryMasterDesc.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		    criteria.add(Restrictions.eq("fsCountryMaster.countryId", nationality));
		    criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		    List<CountryMasterDesc> lstCountryMasterDesc = (List<CountryMasterDesc>) findAll(criteria);
		    if(lstCountryMasterDesc.get(0).getNationality() != null){
			      return lstCountryMasterDesc.get(0).getNationality();      
		    }else{
		    return "";
		    }
		    
	  }

}
