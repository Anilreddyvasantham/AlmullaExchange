package com.amg.exchange.treasury.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.treasury.dao.IFXDealSupplierDao;
import com.amg.exchange.treasury.model.CurrencyMaster;

@SuppressWarnings("serial")
@Repository
public class FXDealSupplierImpl<T> extends CommonDaoImpl<T> implements
		IFXDealSupplierDao<T>, Serializable {

	@SuppressWarnings("unchecked")
	@Override
	public List<CompanyMasterDesc> getCompanyList(BigDecimal languageId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(
				CompanyMasterDesc.class, "companyMasterDesc");
		criteria.setFetchMode("companyMasterDesc.fsCompanyMaster",
				FetchMode.JOIN);
		criteria.createAlias("companyMasterDesc.fsCompanyMaster",
				"fsCompanyMaster", JoinType.INNER_JOIN);

		criteria.setFetchMode("companyMasterDesc.fsLanguageType",
				FetchMode.JOIN);
		criteria.createAlias("companyMasterDesc.fsLanguageType",
				"fsLanguageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));

		criteria.addOrder(Order.asc("companyMasterDesc.companyName"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<CompanyMasterDesc>) findAll(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CurrencyMaster> getCurrencyList() {
		DetachedCriteria criteria = DetachedCriteria.forClass(
				CurrencyMaster.class, "currencyMaster");

		criteria.addOrder(Order.asc("currencyMaster.currencyCode"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<CurrencyMaster>) findAll(criteria);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Customer> getAllComponentOreder(String query) {

		String sql = "select * from FS_CUSTOMER where CUSTOMER_REFERENCE like '%"
				+ query + "%' order by CUSTOMER_REFERENCE";
		SQLQuery squery = getSession().createSQLQuery(sql).addEntity(
				Customer.class);
		
		List<Customer> list = squery.list();
		List<Customer> newCustomerList =new ArrayList<Customer>();
		for(Customer cust:list){
			if(cust.getSundryDebtorReference()!=null){
				newCustomerList.add(cust);
			}
		}
		return newCustomerList;

		
/*		DetachedCriteria criteria = DetachedCriteria.forClass(Customer.class, "customer");
		criteria.add(Restrictions.like("customer.customerReference",new BigDecimal(query) , MatchMode.ANYWHERE).ignoreCase());
		 
		criteria.setProjection(Projections.property("customer.customerReference"));
		criteria.addOrder(Order.asc("customer.customerReference"));
		criteria.add(Restrictions.isNotNull("customer.sundryDebtorReference"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		return (List<Customer>) findAll(criteria);*/
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Customer> getAllCustomerList(BigDecimal customerReference) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Customer.class, "customer");
		detachedCriteria.add(Restrictions.eq("customer.customerReference",customerReference));

		return (List<Customer>) findAll(detachedCriteria);
	}

	  @Override
	  public String getAllCustomerName(BigDecimal dealWithCustomer) {
		    String customerName = null;
		    DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Customer.class, "customer");
			detachedCriteria.add(Restrictions.eq("customer.customerReference",dealWithCustomer));
			List<Customer> lstCustomer=(List<Customer>) findAll(detachedCriteria);
			if(lstCustomer.size() !=0 && lstCustomer != null){
				  customerName=lstCustomer.get(0).getFirstName();
			}
			return customerName;
	  }
}
