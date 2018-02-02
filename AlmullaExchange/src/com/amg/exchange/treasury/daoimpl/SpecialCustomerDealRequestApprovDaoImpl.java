package com.amg.exchange.treasury.daoimpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.swing.text.DefaultEditorKit.CutAction;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.treasury.dao.ISpecialCustomerDealRequestApprovDao;
import com.amg.exchange.treasury.model.CustomerSpecialDealRequest;

@Repository
public class SpecialCustomerDealRequestApprovDaoImpl<T> extends CommonDaoImpl<T> implements ISpecialCustomerDealRequestApprovDao<T>{  

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerSpecialDealRequest> CustomerSpecialDealRequest(BigDecimal countryId) {
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CustomerSpecialDealRequest.class, "customerSpecialDealRequest");
		dCriteria.setFetchMode("customerSpecialDealRequest.applicationCountryCountryMaster",FetchMode.SELECT);
		dCriteria.createAlias("customerSpecialDealRequest.applicationCountryCountryMaster", "applicationCountryCountryMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("applicationCountryCountryMaster.countryId", countryId));
		
		dCriteria.setFetchMode("customerSpecialDealRequest.customerSpeacialDealReqCountryMaster",FetchMode.SELECT);
		dCriteria.createAlias("customerSpecialDealRequest.customerSpeacialDealReqCountryMaster", "customerSpeacialDealReqCountryMaster", JoinType.INNER_JOIN);
		
		dCriteria.setFetchMode("customerSpecialDealRequest.customerSpeacialDealReqBankMaster",FetchMode.SELECT);
		dCriteria.createAlias("customerSpecialDealRequest.customerSpeacialDealReqBankMaster", "customerSpeacialDealReqBankMaster", JoinType.INNER_JOIN);
		
		dCriteria.setFetchMode("customerSpecialDealRequest.customerSpeacialDealReqCurrencyMaster", FetchMode.SELECT);
		dCriteria.createAlias("customerSpecialDealRequest.customerSpeacialDealReqCurrencyMaster", "customerSpeacialDealReqCurrencyMaster", JoinType.INNER_JOIN);
		dCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (List<CustomerSpecialDealRequest>)findAll(dCriteria);
	
	}

	@Override
	public void updateApprove(List<BigDecimal> lstApproved, String userName) {
		
		for (BigDecimal bigDecimal : lstApproved) {
			CustomerSpecialDealRequest customerSpecialDealRequest = (CustomerSpecialDealRequest)getSession().get(CustomerSpecialDealRequest.class, bigDecimal);
			customerSpecialDealRequest.setApprovedBy(userName);
			customerSpecialDealRequest.setApprovedDate(new Date());
			getSession().update(customerSpecialDealRequest);
		}
	}
	
	
	@Override
	public String getCountryName(BigDecimal countryId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CountryMasterDesc.class, "countryMasterDesc");
		
		dCriteria.setFetchMode("countryMasterDesc.fsCountryMaster", FetchMode.JOIN);
		dCriteria.createAlias("countryMasterDesc.fsCountryMaster", "fsCountryMaster",  JoinType.INNER_JOIN);
		
		dCriteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		
		return ((CountryMasterDesc) dCriteria.getExecutableCriteria(getSession()).list().get(0)).getCountryName();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerSpecialDealRequest> getCustName(Customer customer) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CustomerSpecialDealRequest.class, "customerSpecialDealRequest");
		
		
		dCriteria.setFetchMode("customerSpecialDealRequest.customerSpeacialDealReqCustomer", FetchMode.JOIN);
		dCriteria.createAlias("customerSpecialDealRequest.customerSpeacialDealReqCustomer", "customerSpeacialDealReqCustomer",  JoinType.INNER_JOIN);
		
		dCriteria.add(Restrictions.eq("customerSpeacialDealReqCustomer.customerId", customer.getCustomerId()));
		
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+customer.getCustomerId());
		
		dCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		List<CustomerSpecialDealRequest> listCustomers=(List<CustomerSpecialDealRequest>)findAll(dCriteria);
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%"+listCustomers.size());
		return listCustomers;
	}

	@Override
	public String countryName(BigDecimal countryId, BigDecimal languageId) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				CountryMasterDesc.class, "countryMasterDesc");

		dCriteria.setFetchMode("countryMasterDesc.fsCountryMaster",
				FetchMode.JOIN);
		dCriteria.createAlias("countryMasterDesc.fsCountryMaster",
				"fsCountryMaster", JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));

		dCriteria.add(Restrictions.eq(
				"countryMasterDesc.fsLanguageType.languageId", languageId));

		return ((CountryMasterDesc) dCriteria
				.getExecutableCriteria(getSession()).list().get(0))
				.getCountryName();

	}
	
	
	
}
	

