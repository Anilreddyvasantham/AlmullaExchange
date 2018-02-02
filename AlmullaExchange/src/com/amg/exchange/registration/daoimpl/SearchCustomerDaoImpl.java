package com.amg.exchange.registration.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amg.exchange.bean.CreateSearchTable;
import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.registration.bean.SearchEntityBean;
import com.amg.exchange.registration.dao.ISearchCustomerDao;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.SessionStateManage;




@SuppressWarnings("serial")
@Repository
public class SearchCustomerDaoImpl<T> extends CommonDaoImpl<T> implements ISearchCustomerDao<T>, Serializable {

	@SuppressWarnings("rawtypes")
	protected Class genericClassName;


	@SuppressWarnings("rawtypes")
	public Class getGenericClassName() {
		return genericClassName;
	}

	@SuppressWarnings("rawtypes")
	public void setGenericClassName(Class genericClassName) {
		this.genericClassName = genericClassName;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Customer> getCustomer() {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Customer.class);


		return (List<Customer>)findAll(dCriteria);
	}

	SessionStateManage sessionState = new SessionStateManage();
	@Autowired 
	IGeneralService<T> generalService;

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerIdProof> getCustomerIdProof() {
		DetachedCriteria criteria = DetachedCriteria.forClass(CustomerIdProof.class);
		return (List<CustomerIdProof>)findAll(criteria);
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<CustomerIdProof> searchAllCustomer(SearchEntityBean searchEntityBean) {

		DetachedCriteria criteria = DetachedCriteria.forClass(CustomerIdProof.class, "customerIdProof");

		DateFormat formatter = new SimpleDateFormat("dd/MM/yy");

		Date currentDate = null;
		try {
			currentDate = formatter.parse(formatter.format(new Date()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Today : " + currentDate);

		// Join Customer Table
		criteria.setFetchMode("customerIdProof.fsCustomer", FetchMode.JOIN);
		criteria.createAlias("customerIdProof.fsCustomer", "fsCustomer", JoinType.LEFT_OUTER_JOIN);
		//Addede by nazish for only fully registered customer on 15-sep-2015
		criteria.add(Restrictions.eq("fsCustomer.isActive", Constants.Yes));
		criteria.add(Restrictions.eq("fsCustomer.activatedInd", Constants.Yes));

		criteria.setFetchMode("customerIdProof.fsBizComponentDataByIdentityTypeId",  FetchMode.JOIN);
		criteria.createAlias("customerIdProof.fsBizComponentDataByIdentityTypeId", "fsBizComponentDataByIdentityTypeId",JoinType.LEFT_OUTER_JOIN);

		criteria.setFetchMode("customerIdProof.fsBizComponentDataByCustomerTypeId",  FetchMode.JOIN);
		criteria.createAlias("customerIdProof.fsBizComponentDataByCustomerTypeId", "fsBizComponentDataByCustomerTypeId",JoinType.LEFT_OUTER_JOIN);

		//System.out.println("999999999999999999999999999========="+searchEntityBean.getNationalityId());
		//Restriction for identity type

		if(searchEntityBean.getIdType() !=null && searchEntityBean.getIdType().intValue()!=0 &&  searchEntityBean.getIdNumber() ==null){
			criteria.add(Restrictions.eq("fsBizComponentDataByIdentityTypeId.componentDataId", new BigDecimal(01)));
		}

		//Identity Number checking
		if(searchEntityBean.getIdType() !=null && searchEntityBean.getIdType().intValue()!=0 && searchEntityBean.getIdNumber() !=null && searchEntityBean.getIdNumber() != null){
			BigDecimal idtypeCivilId = generalService.getComponentId(Constants.CIVILID, sessionState.getLanguageId())
					.getFsBizComponentData().getComponentDataId();

			BigDecimal idtypeCivilIdnew = generalService.getComponentId(Constants.CIVIL_ID_NEW, sessionState.getLanguageId())
					.getFsBizComponentData().getComponentDataId();

			if(searchEntityBean.getIdType().equals(idtypeCivilId) || searchEntityBean.getIdType().equals(idtypeCivilIdnew)){
			}else{
				criteria.add(Restrictions.eq("fsBizComponentDataByIdentityTypeId.componentDataId", searchEntityBean.getIdType()));
			}
			criteria.add(Restrictions.eq("customerIdProof.identityInt", searchEntityBean.getIdNumber()));
			//criteria.add(Restrictions.gt("customerIdProof.identityExpiryDate", new Date()));
			criteria.add(Restrictions.ge("customerIdProof.identityExpiryDate", currentDate));
		}

		//Date of birth checking
		if(searchEntityBean.getDob() !=null){
			criteria.add(Restrictions.eq("fsCustomer.dateOfBirth", searchEntityBean.getDob()));
			//criteria.add(Restrictions.gt("customerIdProof.identityExpiryDate", new Date()));
			criteria.add(Restrictions.ge("customerIdProof.identityExpiryDate", currentDate));
		}

		//Mobile Number checking
		if(searchEntityBean.getMobileNumber() !=null && !searchEntityBean.getMobileNumber().trim().equals("")){
			criteria.add(Restrictions.eq("fsCustomer.mobile", searchEntityBean.getMobileNumber()));
			//criteria.add(Restrictions.gt("customerIdProof.identityExpiryDate", new Date()));
			criteria.add(Restrictions.ge("customerIdProof.identityExpiryDate", currentDate));
		}

		//Customer first name matching
		if(searchEntityBean.getFirstName()!=null && !searchEntityBean.getFirstName().trim().equals("") && searchEntityBean.getNationalityId() != null && searchEntityBean.getNationalityId().intValue()!=0){
			criteria.add(Restrictions.eq("fsCountryMasterByNationality.countryId", searchEntityBean.getNationalityId()));
			criteria.add(Restrictions.like("fsCustomer.firstName", searchEntityBean.getFirstName(), MatchMode.START));
			//criteria.add(Restrictions.gt("customerIdProof.identityExpiryDate", new Date()));
			criteria.add(Restrictions.ge("customerIdProof.identityExpiryDate", currentDate));
			//criteria.add(Restrictions.like("fsCustomer.firstName", searchEntityBean.getFirstName()).ignoreCase());

		}

		//Customer last name matching
		if(searchEntityBean.getLastName()!=null && !searchEntityBean.getLastName().trim().equals("") && searchEntityBean.getNationalityId() != null && searchEntityBean.getNationalityId().intValue()!=0){
			criteria.add(Restrictions.eq("fsCountryMasterByNationality.countryId", searchEntityBean.getNationalityId()));
			criteria.add(Restrictions.like("fsCustomer.lastName", searchEntityBean.getLastName(), MatchMode.START));
			//criteria.add(Restrictions.gt("customerIdProof.identityExpiryDate", new Date()));
			criteria.add(Restrictions.ge("customerIdProof.identityExpiryDate", currentDate));
		} 

		if(searchEntityBean.getNationalityId() != null &&  searchEntityBean.getFirstName() != null && !searchEntityBean.getFirstName().trim().equals("") && searchEntityBean.getNationalityId().intValue()==0){
			criteria.add(Restrictions.eq("fsCustomer.firstName", "xxyyoo"));

		}
		if(searchEntityBean.getNationalityId() != null && searchEntityBean.getLastName() != null && !searchEntityBean.getLastName().trim().equals("") && searchEntityBean.getNationalityId().intValue()==0){
			criteria.add(Restrictions.eq("fsCustomer.lastName", "xxyyoo"));
		}

		//Join Country Master Table
		criteria.setFetchMode("fsCustomer.fsCountryMasterByNationality", FetchMode.JOIN);
		criteria.createAlias("fsCustomer.fsCountryMasterByNationality", "fsCountryMasterByNationality", CriteriaSpecification.INNER_JOIN);	

		if(searchEntityBean.getNationalityId() !=null && searchEntityBean.getNationalityId().intValue()!=0){
			criteria.add(Restrictions.eq("fsCountryMasterByNationality.countryId", searchEntityBean.getNationalityId()));
		}

		//criteria.add(Restrictions.eq("fsCustomer.activatedInd", "1"));
		criteria.addOrder(Order.desc("customerIdProof.identityExpiryDate"));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		return (List<CustomerIdProof>)findAll(criteria);
	}
	
	@SuppressWarnings("unchecked")
	@Override	
	public List<CustomerIdProof> searchCustomerEnquiryForAllCustomer(SearchEntityBean searchEntityBean) {

		DetachedCriteria criteria = DetachedCriteria.forClass(CustomerIdProof.class, "customerIdProof");

		DateFormat formatter = new SimpleDateFormat("dd/MM/yy");

		Date currentDate = null;
		try {
			currentDate = formatter.parse(formatter.format(new Date()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println("Today : " + currentDate);

		// Join Customer Table
		criteria.setFetchMode("customerIdProof.fsCustomer", FetchMode.JOIN);
		criteria.createAlias("customerIdProof.fsCustomer", "fsCustomer", JoinType.LEFT_OUTER_JOIN);
		//Addede by nazish for only fully registered customer on 15-sep-2015
		//criteria.add(Restrictions.eq("fsCustomer.isActive", Constants.Yes));
		criteria.add(Restrictions.eq("fsCustomer.activatedInd", Constants.Yes));

		criteria.setFetchMode("customerIdProof.fsBizComponentDataByIdentityTypeId",  FetchMode.JOIN);
		criteria.createAlias("customerIdProof.fsBizComponentDataByIdentityTypeId", "fsBizComponentDataByIdentityTypeId",JoinType.LEFT_OUTER_JOIN);

		criteria.setFetchMode("customerIdProof.fsBizComponentDataByCustomerTypeId",  FetchMode.JOIN);
		criteria.createAlias("customerIdProof.fsBizComponentDataByCustomerTypeId", "fsBizComponentDataByCustomerTypeId",JoinType.LEFT_OUTER_JOIN);

		//Restriction for identity type
		if(searchEntityBean.getIdType() !=null && searchEntityBean.getIdType().intValue()!=0 &&  searchEntityBean.getIdNumber() ==null){
			criteria.add(Restrictions.eq("fsBizComponentDataByIdentityTypeId.componentDataId", new BigDecimal(01)));
		}

		//Identity Number checking
		if(searchEntityBean.getIdType() !=null && searchEntityBean.getIdType().intValue()!=0 && searchEntityBean.getIdNumber() !=null && searchEntityBean.getIdNumber() != null){
			BigDecimal idtypeCivilId = generalService.getComponentId(Constants.CIVILID, sessionState.getLanguageId())
					.getFsBizComponentData().getComponentDataId();

			BigDecimal idtypeCivilIdnew = generalService.getComponentId(Constants.CIVIL_ID_NEW, sessionState.getLanguageId())
					.getFsBizComponentData().getComponentDataId();

			if(searchEntityBean.getIdType().equals(idtypeCivilId) || searchEntityBean.getIdType().equals(idtypeCivilIdnew)){
			}else{
				criteria.add(Restrictions.eq("fsBizComponentDataByIdentityTypeId.componentDataId", searchEntityBean.getIdType()));
			}
			criteria.add(Restrictions.eq("customerIdProof.identityInt", searchEntityBean.getIdNumber()));
			//criteria.add(Restrictions.ge("customerIdProof.identityExpiryDate", currentDate));
		}

		//Date of birth checking
		if(searchEntityBean.getDob() !=null){
			criteria.add(Restrictions.eq("fsCustomer.dateOfBirth", searchEntityBean.getDob()));
			//criteria.add(Restrictions.ge("customerIdProof.identityExpiryDate", currentDate));
		}

		//Mobile Number checking
		if(searchEntityBean.getMobileNumber() !=null && !searchEntityBean.getMobileNumber().trim().equals("")){
			criteria.add(Restrictions.eq("fsCustomer.mobile", searchEntityBean.getMobileNumber()));
			//criteria.add(Restrictions.ge("customerIdProof.identityExpiryDate", currentDate));
		}

		//Customer first name matching
		if(searchEntityBean.getFirstName()!=null && !searchEntityBean.getFirstName().trim().equals("") && searchEntityBean.getNationalityId() != null && searchEntityBean.getNationalityId().intValue()!=0){
			criteria.add(Restrictions.eq("fsCountryMasterByNationality.countryId", searchEntityBean.getNationalityId()));
			criteria.add(Restrictions.like("fsCustomer.firstName", searchEntityBean.getFirstName(), MatchMode.START));
			//criteria.add(Restrictions.ge("customerIdProof.identityExpiryDate", currentDate));
		}

		//Customer last name matching
		if(searchEntityBean.getLastName()!=null && !searchEntityBean.getLastName().trim().equals("") && searchEntityBean.getNationalityId() != null && searchEntityBean.getNationalityId().intValue()!=0){
			criteria.add(Restrictions.eq("fsCountryMasterByNationality.countryId", searchEntityBean.getNationalityId()));
			criteria.add(Restrictions.like("fsCustomer.lastName", searchEntityBean.getLastName(), MatchMode.START));
			//criteria.add(Restrictions.ge("customerIdProof.identityExpiryDate", currentDate));
		} 

		if(searchEntityBean.getNationalityId() != null &&  searchEntityBean.getFirstName() != null && !searchEntityBean.getFirstName().trim().equals("") && searchEntityBean.getNationalityId().intValue()==0){
			criteria.add(Restrictions.eq("fsCustomer.firstName", "xxyyoo"));
		}
		
		if(searchEntityBean.getNationalityId() != null && searchEntityBean.getLastName() != null && !searchEntityBean.getLastName().trim().equals("") && searchEntityBean.getNationalityId().intValue()==0){
			criteria.add(Restrictions.eq("fsCustomer.lastName", "xxyyoo"));
		}

		//Join Country Master Table
		criteria.setFetchMode("fsCustomer.fsCountryMasterByNationality", FetchMode.JOIN);
		criteria.createAlias("fsCustomer.fsCountryMasterByNationality", "fsCountryMasterByNationality", CriteriaSpecification.INNER_JOIN);	

		if(searchEntityBean.getNationalityId() !=null && searchEntityBean.getNationalityId().intValue()!=0){
			criteria.add(Restrictions.eq("fsCountryMasterByNationality.countryId", searchEntityBean.getNationalityId()));
		}

		criteria.addOrder(Order.desc("customerIdProof.identityExpiryDate"));
		
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		return (List<CustomerIdProof>)findAll(criteria);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Customer> getCustomerInfo(BigDecimal customerId) {	
		DetachedCriteria criteria = DetachedCriteria.forClass(Customer.class, "customerDetails");

		criteria.add(Restrictions.eq("customerId", customerId));
		criteria.add(Restrictions.ne("emosCustomer", Constants.ONE.toString()));
		//LOG.info("Exit into getCustomerInfo method");
		return (List<Customer>) findAll(criteria);
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<CustomerIdProof> searchAllCustomers(SearchEntityBean searchEntityBean) {

		DetachedCriteria criteria = DetachedCriteria.forClass(CustomerIdProof.class, "customerIdProof");

		// Join Customer Table
		criteria.setFetchMode("customerIdProof.fsCustomer", FetchMode.JOIN);
		criteria.createAlias("customerIdProof.fsCustomer", "fsCustomer", JoinType.LEFT_OUTER_JOIN);


		criteria.setFetchMode("customerIdProof.fsBizComponentDataByIdentityTypeId",  FetchMode.JOIN);
		criteria.createAlias("customerIdProof.fsBizComponentDataByIdentityTypeId", "fsBizComponentDataByIdentityTypeId",JoinType.LEFT_OUTER_JOIN);

		criteria.setFetchMode("customerIdProof.fsBizComponentDataByCustomerTypeId",  FetchMode.JOIN);
		criteria.createAlias("customerIdProof.fsBizComponentDataByCustomerTypeId", "fsBizComponentDataByCustomerTypeId",JoinType.LEFT_OUTER_JOIN);

		if(searchEntityBean.getCustomerRef() != null){
			criteria.add(Restrictions.eq("fsCustomer.customerReference", searchEntityBean.getCustomerRef()));
		}

		BigDecimal idtypeCivilId = generalService.getComponentId(Constants.CIVILID, sessionState.getLanguageId()).getFsBizComponentData().getComponentDataId();

		BigDecimal idtypeCivilIdnew = generalService.getComponentId(Constants.CIVIL_ID_NEW, sessionState.getLanguageId()).getFsBizComponentData().getComponentDataId();

		if(searchEntityBean.getIdType() != null){
			if((searchEntityBean.getIdType().equals(idtypeCivilId) || searchEntityBean.getIdType().equals(idtypeCivilIdnew))){
				// no matter what type it is civil id or civil new
			}else{
				criteria.add(Restrictions.eq("fsBizComponentDataByIdentityTypeId.componentDataId", searchEntityBean.getIdType()));
			}
		}
		

		System.out.println("IDDDDDNUM===="+searchEntityBean.getIdNum());

		if(searchEntityBean.getIdNum() != null && !searchEntityBean.getIdNum().equalsIgnoreCase("")){
			criteria.add(Restrictions.eq("customerIdProof.identityInt", searchEntityBean.getIdNum()));
		}
		//criteria.add(Restrictions.gt("customerIdProof.identityExpiryDate", new Date()));

		//Join Country Master Table
		if(searchEntityBean.getNationalityId()!=null){
			criteria.setFetchMode("fsCustomer.fsCountryMasterByNationality", FetchMode.JOIN);
			criteria.createAlias("fsCustomer.fsCountryMasterByNationality", "fsCountryMasterByNationality", CriteriaSpecification.INNER_JOIN);
			criteria.add(Restrictions.eq("fsCountryMasterByNationality.countryId", searchEntityBean.getNationalityId()));
		}
		criteria.addOrder(Order.desc("customerIdProof.identityExpiryDate"));
		
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		List<CustomerIdProof> lstcustIdProof = (List<CustomerIdProof>)findAll(criteria);

		return lstcustIdProof;
	}





	@Override
	public void update(CreateSearchTable createSearchTable) {

		if(createSearchTable.getIsActive() != null && createSearchTable.getIsActive().equalsIgnoreCase(Constants.D)){
			Customer customer= (Customer)getSession().get(Customer.class,createSearchTable.getCustomerPk());
			customer.setIsActive(createSearchTable.getIsActive());
			customer.setDeactivatedBy(createSearchTable.getDeactvatedBy());
			customer.setDeactivatedDate(createSearchTable.getDeactivatedDate());
			customer.setRemarks(createSearchTable.getRemarks());
			customer.setUpdatedBy(createSearchTable.getModifiedBy());
			customer.setLastUpdated(new Date());
			getSession().update(customer);
			
			if(customer.getCustomerReference()!=null){
				String hqlSqlQry  = "UPDATE CUSMAS set RECSTS ='"+createSearchTable.getIsActive()+"' where CUSREF="+customer.getCustomerReference()+" ";
				SQLQuery query = getSession().createSQLQuery(hqlSqlQry);
				int i = query.executeUpdate();
			}
		}

		//De-active id proof also
		if(createSearchTable.getCustIdProofPk()!=null && createSearchTable.getCustomerPk()!=null){
			CustomerIdProof customerIdProof=(CustomerIdProof) getSession().get(CustomerIdProof.class, createSearchTable.getCustIdProofPk());
			customerIdProof.setIdentityStatus(createSearchTable.getIdStatus());
			customerIdProof.setUpdatedBy(createSearchTable.getModifiedBy() );
			customerIdProof.setLastUpdatedDate(new Date() );
			getSession().update(customerIdProof);
		}
		
	}

	@Override
	public Boolean checkAuthorizationOfEmp(BigDecimal employeeId) {

		Boolean authUser=false;
		DetachedCriteria criteria = DetachedCriteria.forClass(Employee.class, "employee");
		criteria.add(Restrictions.eq("employee.employeeId", employeeId));		 

		Criterion criteria1=Restrictions.eq("employee.deletedUser", Constants.No);
		Criterion criteria2=Restrictions.isNull("employee.deletedUser");
		LogicalExpression andExp = Restrictions.or(criteria1, criteria2);
		criteria.add( andExp );



		List<Employee> empList=(List<Employee>)findAll(criteria);

		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		if(empList.size()>0){
			authUser=true;
			return authUser;
		}else{
			return  authUser;
		}

	}

	@Override
	public void ActivateRecord(CreateSearchTable createSearchTable) {
		Customer  customer=(Customer) getSession().get(Customer.class, createSearchTable.getCustomerPk());
		customer.setIsActive( createSearchTable.getIsActive());
		customer.setUpdatedBy(createSearchTable.getModifiedBy());
		customer.setRemarks(createSearchTable.getRemarks());
		customer.setLastUpdated(new Date());
		customer.setDeactivatedBy(null);
		customer.setDeactivatedDate(null);
		getSession().update(customer);
		
		//Deactive id proof also
		if(createSearchTable.getCustIdProofPk()!=null && createSearchTable.getCustomerPk()!=null){
			CustomerIdProof customerIdProof=(CustomerIdProof) getSession().get(CustomerIdProof.class, createSearchTable.getCustIdProofPk());
			customerIdProof.setIdentityStatus(createSearchTable.getIdStatus());
			customerIdProof.setUpdatedBy(createSearchTable.getModifiedBy());
			customerIdProof.setLastUpdatedDate(new Date());
			getSession().update(customerIdProof);
		}
		
		if(customer.getCustomerReference()!=null){
		String hqlSqlQry  = "UPDATE CUSMAS set RECSTS =null where CUSREF="+customer.getCustomerReference()+" ";
		SQLQuery query = getSession().createSQLQuery(hqlSqlQry);
		int i = query.executeUpdate();
		}
		
		
		
		
		
	}

	@Override
	public List<CustomerIdProof> getRecentExpiryDate(
			SearchEntityBean searchEntityBean) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CustomerIdProof.class, "customerIdProof");

		DateFormat formatter = new SimpleDateFormat("dd/MM/yy");

		Date currentDate = null;
		try {
			currentDate = formatter.parse(formatter.format(new Date()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Today : " + currentDate);

		// Join Customer Table
		criteria.setFetchMode("customerIdProof.fsCustomer", FetchMode.JOIN);
		criteria.createAlias("customerIdProof.fsCustomer", "fsCustomer", JoinType.LEFT_OUTER_JOIN);


		criteria.setFetchMode("customerIdProof.fsBizComponentDataByIdentityTypeId",  FetchMode.JOIN);
		criteria.createAlias("customerIdProof.fsBizComponentDataByIdentityTypeId", "fsBizComponentDataByIdentityTypeId",JoinType.LEFT_OUTER_JOIN);

		criteria.setFetchMode("customerIdProof.fsBizComponentDataByCustomerTypeId",  FetchMode.JOIN);
		criteria.createAlias("customerIdProof.fsBizComponentDataByCustomerTypeId", "fsBizComponentDataByCustomerTypeId",JoinType.LEFT_OUTER_JOIN);



		BigDecimal idtypeCivilId = generalService.getComponentId(Constants.CIVILID, sessionState.getLanguageId()).getFsBizComponentData().getComponentDataId();

		BigDecimal idtypeCivilIdnew = generalService.getComponentId(Constants.CIVIL_ID_NEW, sessionState.getLanguageId()).getFsBizComponentData().getComponentDataId();

		if(searchEntityBean.getIdType().equals(idtypeCivilId) || searchEntityBean.getIdType().equals(idtypeCivilIdnew)){
		}
		else{
			criteria.add(Restrictions.eq("fsBizComponentDataByIdentityTypeId.componentDataId", searchEntityBean.getIdType()));
		}

		System.out.println("IDDDDDNUM===="+searchEntityBean.getIdNum());


		criteria.add(Restrictions.eq("customerIdProof.identityInt", searchEntityBean.getIdNum()));
		//criteria.add(Restrictions.gt("customerIdProof.identityExpiryDate", new Date()));
		criteria.add(Restrictions.ge("customerIdProof.identityExpiryDate", currentDate));


		criteria.setProjection( Projections.max( "identityExpiryDate" ) );



		//Join Country Master Table
		if(searchEntityBean.getNationalityId()!=null){
			criteria.setFetchMode("fsCustomer.fsCountryMasterByNationality", FetchMode.JOIN);
			criteria.createAlias("fsCustomer.fsCountryMasterByNationality", "fsCountryMasterByNationality", CriteriaSpecification.INNER_JOIN);
			criteria.add(Restrictions.eq("fsCountryMasterByNationality.countryId", searchEntityBean.getNationalityId()));
		}
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		return (List<CustomerIdProof>)findAll(criteria);

	}


}
