package com.amg.exchange.telemarketing.daoimpl;


import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.telemarketing.bean.TelemarketingBranchWiseTable;
import com.amg.exchange.telemarketing.dao.TelemarketingDAO;
import com.amg.exchange.telemarketing.model.TelemartCustomer;
import com.amg.exchange.telemarketing.model.TelemartFolwUp;
import com.amg.exchange.telemarketing.model.VwExTelemartFolwUpCode;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.SessionStateManage;

@Repository
@SuppressWarnings("unchecked")
public class TelemarketingDAOImpl<T> extends CommonDaoImpl<T> implements
		TelemarketingDAO {	
	
	SessionStateManage sessionState = new SessionStateManage();
	
	@Override
	public List<Customer> getCustomersList() {
		
		Query query = getSession()
				.createSQLQuery(
						"select * from FS_CUSTOMER  c where c.LAST_TRANSACTION_DATE  < ADD_MONTHS(SYSDATE,-3) and c.ISACTIVE = 'Y' ORDER BY c.LAST_TRANSACTION_DATE ASC")
				.addEntity(Customer.class);
		List<Customer> result = query.list();
		return result;
	}
	
	public boolean checkDuplicateCusId(BigDecimal customerId) {
		boolean checkCustomerExist=false;
				
		DetachedCriteria criteria = DetachedCriteria.forClass(
				TelemartCustomer.class, "telemartCustomer");
		criteria.add(Restrictions.eq("telemartCustomer.customerId", customerId));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		List<TelemartCustomer> telemartCustomerList = (List<TelemartCustomer>) findAll(criteria);
		if(telemartCustomerList!=null && telemartCustomerList.size()>0)
		{
			checkCustomerExist=true;
		}
		return checkCustomerExist;
	}
	
	@Override
	public void saveTelemarkCustomers(TelemartCustomer telemarketing) { 
		boolean checkCustomerExist=checkDuplicateCusId(telemarketing.getCustomerId());
		if(!checkCustomerExist)
		{
			getSession().saveOrUpdate(telemarketing);
		}				
	}
	
	@Override
	public String getNationality(BigDecimal languageId,BigDecimal coutryId){
		String natonalityId = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(CountryMasterDesc.class,"countryMasterDesc");
		
		criteria.setFetchMode("countryMasterDesc.fsLanguageType",FetchMode.JOIN);
		criteria.createAlias("countryMasterDesc.fsLanguageType","fsLanguageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsLanguageType.languageId",languageId));
		
		criteria.setFetchMode("countryMasterDesc.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("countryMasterDesc.fsCountryMaster","fsCountryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCountryMaster.countryId", coutryId));
		
		//criteria.add(Restrictions.isNotNull("countryMasterDesc.nationality"));
		
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		
		List<CountryMasterDesc> countryDesc = (List<CountryMasterDesc>) findAll(criteria);
		
		if(countryDesc != null && countryDesc.size() != 0){
			CountryMasterDesc country = countryDesc.get(0);
			natonalityId = country.getNationality();
		}
		
		return natonalityId;
	}	
	
	@Override
	public List<Employee> getEmployeesSameNationality(BigDecimal countryId,BigDecimal countryBranchId) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(Employee.class,"employee");
		
		criteria.setFetchMode("employee.fsCountryBranch", FetchMode.JOIN);
		criteria.createAlias("employee.fsCountryBranch","fsCountryBranch", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCountryBranch.countryBranchId", countryBranchId));
		
		criteria.add(Restrictions.eq("employee.employeeCountryId", countryId));
		
		criteria.add(Restrictions.eq("employee.isActive", Constants.Y));
		
		criteria.add(Restrictions.not(Restrictions.ilike("employee.userName", "ARMS%")));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		
		return (List<Employee>) findAll(criteria);
	}
	
	@Override
	public List<Employee> getEmployeesDiffNationality(BigDecimal countryId,BigDecimal countryBranchId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Employee.class,"employee");
		
		//criteria.setFetchMode("employee.fsCountryBranch", FetchMode.JOIN);
		//criteria.createAlias("employee.fsCountryBranch","fsCountryBranch", JoinType.INNER_JOIN);
		//criteria.add(Restrictions.eq("employee.employeeCountryId", countryId));
		
		criteria.add(Restrictions.eq("fsCountryBranch.countryBranchId", countryBranchId));
		criteria.add(Restrictions.eq("employee.isActive", Constants.Y));
		
		criteria.add(Restrictions.not(Restrictions.ilike("employee.userName", "ARMS%")));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (List<Employee>) findAll(criteria);
	}
	
	/*public boolean checkDuplicateCustomerId(BigDecimal customerId) {
		boolean checkCustomerExist=false;
				
		DetachedCriteria criteria = DetachedCriteria.forClass(
				TelemartEmployee.class, "telemartEmployee");
		criteria.add(Restrictions.eq("telemartEmployee.customerId", customerId));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		List<TelemartEmployee> telemartEmployeeList = (List<TelemartEmployee>) findAll(criteria);
		if(telemartEmployeeList!=null && telemartEmployeeList.size()>0)
		{
			checkCustomerExist=true;
		}
		return checkCustomerExist;
	}*/
	
	/*@Override
	public void saveTelemartEmployee(TelemartEmployee telemartEmployee,
			BigDecimal telmartBranchwiseTableId,BigDecimal telemartEmpPK) {

		boolean checkCustomerExist = checkDuplicateCustomerId(telemartEmployee.getCustomerId());

		if (!checkCustomerExist) {
			getSession().saveOrUpdate(telemartEmployee);
		} else {					
			TelemartEmployee telemartEmployeeList = (TelemartEmployee) getSession().get(TelemartEmployee.class,telemartEmpPK);			
			telemartEmployeeList.setEmployeeId(telemartEmployee.getEmployeeId());
			telemartEmployeeList.setAllocatedDate(telemartEmployee.getAllocatedDate());		
			telemartEmployeeList.setModifiedBy(telemartEmployee.getModifiedBy());
			telemartEmployeeList.setModifiedDate(telemartEmployee.getModifiedDate());
			getSession().saveOrUpdate(telemartEmployeeList);				
		}
		
		TelemartCustomer telemartCustomer = (TelemartCustomer) getSession().get(TelemartCustomer.class, telmartBranchwiseTableId);
		telemartCustomer.setEmployeeId(telemartEmployee.getEmployeeId());
		telemartCustomer.setAllocatedDate(telemartEmployee.getAllocatedDate());

		getSession().saveOrUpdate(telemartCustomer);

	}*/
	
	@Override
	public List<TelemartCustomer> getTelemartEmpPk(BigDecimal cusId){
		DetachedCriteria criteria = DetachedCriteria.forClass(TelemartCustomer.class,"telemartCustomer");
		criteria.add(Restrictions.eq("telemartCustomer.customerId", cusId));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (List<TelemartCustomer>) findAll(criteria);
	}
	
	@Override
	public List<Employee> getTelemartEmployeeName(BigDecimal employeeId){
		DetachedCriteria criteria = DetachedCriteria.forClass(Employee.class,"employee");
		criteria.add(Restrictions.eq("employee.employeeId", employeeId));
		criteria.add(Restrictions.eq("employee.isActive", Constants.Y));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (List<Employee>) findAll(criteria);
	}
	
	@Override
	public List<Customer> getCustomerList(BigDecimal cusId){
		DetachedCriteria criteria = DetachedCriteria.forClass(Customer.class,"customer");
		criteria.add(Restrictions.eq("customer.customerId", cusId));
		criteria.add(Restrictions.eq("customer.isActive", Constants.Yes));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (List<Customer>) findAll(criteria);
	}
	
	/*@Override
	public List<TelemartEmployee> getTelemartEmployeeList(){
		DetachedCriteria criteria = DetachedCriteria.forClass(TelemartEmployee.class,"telemartEmployee");		
		
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (List<TelemartEmployee>) findAll(criteria);
	}*/
	
	@Override
	public List<TelemartCustomer> getTelemartCustomerList(BigDecimal countryBranchId){
		DetachedCriteria criteria = DetachedCriteria.forClass(TelemartCustomer.class,"telemartCustomer");
		
		criteria.add(Restrictions.eq("telemartCustomer.countryBranchId", countryBranchId));
		//criteria.add(Restrictions.isNull("telemartCustomer.employeeId"));
		criteria.add(Restrictions.eq("telemartCustomer.employeeId",BigDecimal.ZERO));
		
		Disjunction lstjunction = Restrictions.disjunction();
		
		lstjunction.add(Restrictions.isNull("telemartCustomer.folwUpCode"));
		lstjunction.add(Restrictions.lt("telemartCustomer.folwUpCode", Constants.TeleFollowUpCodeClose));
		criteria.add(lstjunction);
		
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (List<TelemartCustomer>) findAll(criteria);
	}
	
	@Override
	public BigDecimal getCountryBranchId(BigDecimal loginBranchId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(
				CountryBranch.class, "countryBranch");
		criteria.add(Restrictions.eq("countryBranch.branchId", loginBranchId));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		List<CountryBranch> countryBranchList = (List<CountryBranch>) findAll(criteria);
		return countryBranchList.get(0).getCountryBranchId();
	}	
	
	@Override
	public List<TelemartCustomer> getTelCusList(BigDecimal empId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(
				TelemartCustomer.class, "telemartCustomer");

		criteria.add(Restrictions.eq("telemartCustomer.employeeId", empId));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (List<TelemartCustomer>) findAll(criteria);
	}
	
	@Override
	public List<TelemartCustomer> getFollowUpTableList(BigDecimal branchId,BigDecimal empId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(
				TelemartCustomer.class, "telemartCustomer");

		criteria.add(Restrictions.eq("telemartCustomer.countryBranchId", branchId));
		criteria.add(Restrictions.eq("telemartCustomer.employeeId", empId));
		
		Disjunction lstjunction = Restrictions.disjunction();
		lstjunction.add(Restrictions.isNull("telemartCustomer.folwUpCode"));
		lstjunction.add(Restrictions.lt("telemartCustomer.folwUpCode", Constants.TeleFollowUpCodeClose));
		criteria.add(lstjunction);
		
		Disjunction lstjunction1 = Restrictions.disjunction();
		lstjunction1.add(Restrictions.isNull("telemartCustomer.nextFolwUpDate"));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date today = new Date();
		Date currenctDate = null;
		try {
			currenctDate = sdf.parse(sdf.format(today));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Date nextFollowDate = sdf.parse(sdf.format(telemartCustomer.getNextFolwUpDate()));
		if(currenctDate != null){
			lstjunction1.add(Restrictions.le("telemartCustomer.nextFolwUpDate", currenctDate));//lt("telemartCustomer.nextFolwUpDate",currenctDate));
			criteria.add(lstjunction1);
		}
		
		//criteria.addOrder(Order.desc("telemartCustomer.folwUpDate"));
		criteria.addOrder(Order.asc("telemartCustomer.nextFolwUpDate"));
		
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		List<TelemartCustomer> lstTeleCustomer = (List<TelemartCustomer>) findAll(criteria);
		System.out.println(lstTeleCustomer.size());
		
		return lstTeleCustomer;
	}
	
	@Override
	public void saveTelemartFolwup(TelemartFolwUp telemartFolwUp,BigDecimal telemartCusPK) {

		getSession().saveOrUpdate(telemartFolwUp);

		TelemartCustomer telemartCustomer = (TelemartCustomer) getSession().get(TelemartCustomer.class, telemartCusPK);
		telemartCustomer.setFolwUpCode(telemartFolwUp.getFolwUpCode());
		telemartCustomer.setFolwUpDate(telemartFolwUp.getFolwUpDate());
		telemartCustomer.setRemarks(telemartFolwUp.getRemarks());
		telemartCustomer.setNextFolwUpDate(telemartFolwUp.getNextFolwUpDate());
		
		getSession().saveOrUpdate(telemartCustomer);
		
		//for follow up code is 003 then customer need to deactivate - need to check
		/*if(telemartFolwUp.getFolwUpCode() != null && telemartFolwUp.getFolwUpCode().equalsIgnoreCase("003")){

			Customer customer= (Customer)getSession().get(Customer.class,telemartFolwUp.getCustomerId());
			customer.setIsActive(Constants.D);
			customer.setDeactivatedBy(sessionState.getUserName());
			customer.setDeactivatedDate(new Date());
			customer.setRemarks(telemartFolwUp.getRemarks());
			customer.setUpdatedBy(sessionState.getUserName());
			customer.setLastUpdated(new Date());
			getSession().update(customer);

			if(customer.getCustomerReference()!=null){
				String hqlSqlQry  = "UPDATE CUSMAS set RECSTS ='"+Constants.D+"' where CUSREF="+customer.getCustomerReference()+" ";
				SQLQuery query = getSession().createSQLQuery(hqlSqlQry);
				int i = query.executeUpdate();
			}
			
			//De-active id proof also
			DetachedCriteria criteria = DetachedCriteria.forClass(CustomerIdProof.class, "customerIdProof");
			
			criteria.setFetchMode("customerIdProof.fsCustomer", FetchMode.JOIN);
			criteria.createAlias("customerIdProof.fsCustomer", "fsCustomer", JoinType.LEFT_OUTER_JOIN);
			criteria.add(Restrictions.eq("fsCustomer.customerId", telemartFolwUp.getCustomerId()));
			
			criteria.add(Restrictions.eq("customerIdProof.identityStatus", Constants.Yes));
			
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List<CustomerIdProof> lstCustProofID = (List<CustomerIdProof>) findAll(criteria);
			
			for (CustomerIdProof custIdProof : lstCustProofID) {
				CustomerIdProof customerIdProof=(CustomerIdProof) getSession().get(CustomerIdProof.class, custIdProof.getCustProofId());
				customerIdProof.setIdentityStatus(Constants.D);
				customerIdProof.setUpdatedBy(sessionState.getUserName());
				customerIdProof.setLastUpdatedDate(new Date());
				getSession().update(customerIdProof);
			}
		}*/
		
	}
	
	@Override
	public List<TelemartFolwUp> getTelemartFollowupPk(BigDecimal cusId){
		DetachedCriteria criteria = DetachedCriteria.forClass(TelemartFolwUp.class,"telemartFolwUp");
		criteria.add(Restrictions.eq("telemartFolwUp.customerId", cusId));
		criteria.addOrder(Order.desc("telemartFolwUp.folwUpDate"));
		return (List<TelemartFolwUp>) findAll(criteria);
	}
	
	@Override
	public List<Employee> getEmployeeNames(BigDecimal countryBranchId){
		DetachedCriteria criteria = DetachedCriteria.forClass(Employee.class, "employee");

		criteria.add(Restrictions.eq("employee.fsCountryBranch.countryBranchId", countryBranchId));
		criteria.add(Restrictions.not(Restrictions.ilike("employee.userName", "ARMS%")));
		criteria.add(Restrictions.eq("employee.isActive", Constants.Y));
		
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (List<Employee>) findAll(criteria);
	}
	
	@Override
	public List<VwExTelemartFolwUpCode> getTelemartFollowUpCodes() {
		DetachedCriteria criteria = DetachedCriteria.forClass(VwExTelemartFolwUpCode.class, "vwExTelemartFolwUpCode");
		criteria.add(Restrictions.ne("vwExTelemartFolwUpCode.tmFollowUpCOde", "901"));  // transaction done by customer need not to view staff
		criteria.addOrder(Order.asc("vwExTelemartFolwUpCode.tmFollowUpCOde"));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (List<VwExTelemartFolwUpCode>) findAll(criteria);
	}
	
	@Override
	public String getTelemartFollowUpCodes(String followUpCode) {
		
		String followupDesc = null;
		VwExTelemartFolwUpCode lstTeleMartket = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(VwExTelemartFolwUpCode.class, "vwExTelemartFolwUpCode");
		
		criteria.add(Restrictions.eq("vwExTelemartFolwUpCode.tmFollowUpCOde", followUpCode));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		
		List<VwExTelemartFolwUpCode> lstTeleMart = (List<VwExTelemartFolwUpCode>) findAll(criteria);
		
		
		if(lstTeleMart != null && lstTeleMart.size() != 0){
			lstTeleMartket = lstTeleMart.get(0);
			followupDesc = lstTeleMartket.getTmFollowUpDescription();
		}
		
		return followupDesc;
	}
	
	@Override
	public List<Object []> getCustomersCount(BigDecimal branchId) {
		String hql = "select  teleCus.employeeId ,count(teleCus.customerId) from TelemartCustomer teleCus where teleCus.countryBranchId = :cusBranchId and (teleCus.folwUpCode < '"+Constants.TeleFollowUpCodeClose+"' or teleCus.folwUpCode is null) group by teleCus.employeeId ";
		Query query = getSession().createQuery(hql);
		query.setParameter("cusBranchId", branchId);
		
		List<Object []> results = query.list();		
		return results;		
	}

	@Override
	public List<TelemartCustomer> getFollowUpTableListInquiry(BigDecimal branchId, BigDecimal empId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TelemartCustomer.class, "telemartCustomer");

		criteria.add(Restrictions.eq("telemartCustomer.countryBranchId", branchId));
		criteria.add(Restrictions.eq("telemartCustomer.employeeId", empId));
		
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (List<TelemartCustomer>) findAll(criteria);
	}

	@Override
	public void saveTelemartEmployee(List<TelemarketingBranchWiseTable> lstTeleCust,BigDecimal empId) {
		for (TelemarketingBranchWiseTable telemartCustomer : lstTeleCust) {
			TelemartCustomer telemartCustomer1 = (TelemartCustomer) getSession().get(TelemartCustomer.class, telemartCustomer.getTelmartBranchwiseTableId());
			telemartCustomer1.setEmployeeId(empId);
			telemartCustomer1.setAllocatedDate(new Date());

			getSession().saveOrUpdate(telemartCustomer1);
		}
	}

	@Override
	public List<TelemartCustomer> getTelemartCustomerDataByStaff(BigDecimal countryBranchId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TelemartCustomer.class,"telemartCustomer");
		
		criteria.add(Restrictions.eq("telemartCustomer.countryBranchId", countryBranchId));
		
		Disjunction lstjunction = Restrictions.disjunction();
		
		lstjunction.add(Restrictions.isNull("telemartCustomer.folwUpCode"));
		lstjunction.add(Restrictions.lt("telemartCustomer.folwUpCode", Constants.TeleFollowUpCodeClose));
		criteria.add(lstjunction);
		
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (List<TelemartCustomer>) findAll(criteria);
	}
	
	@Override
	public void saveBranchTransfer(TelemartCustomer telemartCustomer,BigDecimal toBranchId,BigDecimal fromBranchId){
		String updateQuery = "UPDATE TelemartCustomer teleCus set teleCus.countryBranchId= :toBranchId WHERE teleCus.countryBranchId = :fromBranchId";
		Query query = getSession().createQuery(updateQuery);
		query.setParameter("toBranchId",toBranchId);
		query.setParameter("fromBranchId",fromBranchId);
		
		query.executeUpdate();
	}
	
	@Override
	public BigDecimal branchTransferProcedure(BigDecimal toBranchId,BigDecimal fromBranchId,BigDecimal nationality) {
		BigDecimal branchTrans=null;
		Connection connection = null;
		CallableStatement cs;		
		try {
			connection = getDataSourceFromHibernateSession();
			String call = " { call EX_TELEMART_BRANCH_TRANSFER (?, ?, ?)} ";
			cs = connection.prepareCall(call);
			cs.setBigDecimal(1, fromBranchId);
			cs.setBigDecimal(2, toBranchId);
			cs.setBigDecimal(3, nationality);
			cs.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return branchTrans;
	}
}
