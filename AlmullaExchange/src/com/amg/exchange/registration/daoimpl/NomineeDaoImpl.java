package com.amg.exchange.registration.daoimpl;



import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.bean.NomineeBean;
import com.amg.exchange.common.daoimpl.CustomHibernateDaoSupport;
import com.amg.exchange.common.model.CityMaster;
import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.DistrictMaster;
import com.amg.exchange.common.model.StateMaster;
import com.amg.exchange.registration.dao.INomineeDao;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.treasury.model.Nominee;
import com.amg.exchange.util.Constants;


/**
 * @author exim07
 *
 */
@Repository
public class NomineeDaoImpl extends CustomHibernateDaoSupport implements INomineeDao,Serializable {

	/* (non-Javadoc)
	 * @see com.amg.exchange.dao.IMutualDao#getNationality()
	 */


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see com.amg.exchange.dao.IMutualDao#getCity(java.math.BigDecimal)
	 */
	@Override
	public List<CityMaster> getCity(BigDecimal districtId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.amg.exchange.dao.IMutualDao#getCountry()
	 */
	@Override
	public List<CountryMaster> getCountry() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.amg.exchange.dao.IMutualDao#getState()
	 */
	@Override
	public List<StateMaster> getState() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.amg.exchange.dao.IMutualDao#getCompany(java.math.BigDecimal)
	 */
	@Override
	public List<CompanyMaster> getCompany(BigDecimal countryId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.amg.exchange.dao.IMutualDao#getState(java.math.BigDecimal)
	 */
	@Override
	public List<StateMaster> getState(BigDecimal countryId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.amg.exchange.dao.IMutualDao#getDistrict(java.math.BigDecimal)
	 */
	@Override
	public List<DistrictMaster> getDistrict(BigDecimal stateId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.amg.exchange.dao.INomineeDao#getNomineeDetail(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Customer> getNomineeDetail(String civilID) {
	/*	System.out.println("getNomineeDetail  ..");
		List<Customer> result=null;
		result=getSession().createQuery("from Customer as c, CustomerIdProof as cip where c.customerId=c..customerId AND cip.identityInt="+civilID).list();
		System.out.println("getNomineeDetail  size"+result.size());*/
		DetachedCriteria criteria = DetachedCriteria.forClass(Customer.class, "customer");
		
		criteria.setFetchMode("customer.fsCustomerIdProofs", FetchMode.JOIN);
		criteria.createAlias("customer.fsCustomerIdProofs", "fsCustomerIdProofs", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("fsCustomerIdProofs.identityInt", civilID));
		
		criteria.setFetchMode("customer.fsCountryMasterByNationality", FetchMode.JOIN); 
		criteria.createAlias("customer.fsCountryMasterByNationality", "fsCountryMasterByNationality", JoinType.INNER_JOIN);
		
		//criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		return (List<Customer>)criteria.getExecutableCriteria(getSession()).list();
	}

	/* (non-Javadoc)
	 * @see com.amg.exchange.dao.INomineeDao#saveNomineeDetail(com.amg.exchange.model.Nominee)
	 */
	@Override
	public void saveNomineeDetail(Nominee nominee) {
		getSession().saveOrUpdate(nominee);
		
	}

	/* (non-Javadoc)
	 * @see com.amg.exchange.dao.INomineeDao#getNomineeList()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Nominee> getNomineeList(BigDecimal nominatorId) {
		DetachedCriteria criteria=DetachedCriteria.forClass(Nominee.class,"nominee");
		criteria.add(Restrictions.eq("nominee.status","Y"));
		
		criteria.setFetchMode("nominee.fsCustomerByNominatorCustId", FetchMode.JOIN); 
		criteria.createAlias("nominee.fsCustomerByNominatorCustId", "fsCustomerByNominatorCustId", JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("fsCustomerByNominatorCustId.customerId", nominatorId));
		
		criteria.setFetchMode("nominee.fsCustomerByNomineeCustId", FetchMode.JOIN); 
		criteria.createAlias("nominee.fsCustomerByNomineeCustId", "fsCustomerByNomineeCustId", JoinType.INNER_JOIN);
		
		return criteria.getExecutableCriteria(getSession()).list();
	}

	/* (non-Javadoc)
	 * @see com.amg.exchange.dao.INomineeDao#deleteNominee(com.amg.exchange.model.Nominee)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void deleteNominee(NomineeBean nominee) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Nominee.class,"nominee");
		
		/*System.out.println("Nominator : "+nominee.getNominatorId()+"Nominee : "+nominee.getNomineeId());
		
		criteria.setFetchMode("nominee.fsCustomerByNominatorCustId", FetchMode.JOIN); 
		criteria.createAlias("nominee.fsCustomerByNominatorCustId", "fsCustomerByNominatorCustId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCustomerByNominatorCustId.customerId", nominee.getNominatorId()));
		
		criteria.setFetchMode("nominee.fsCustomerByNomineeCustId", FetchMode.JOIN); 
		criteria.createAlias("nominee.fsCustomerByNomineeCustId", "fsCustomerByNomineeCustId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCustomerByNomineeCustId.customerId", nominee.getNomineeId()));*/
		
		criteria.add(Restrictions.eq("nomineeId", nominee.getPkNominee()));
		
		List<Nominee> obj =  criteria.getExecutableCriteria(getSession()).list();
		if(obj.size() > 0){
			obj.get(0).setStatus(Constants.No);
			obj.get(0).setNomineeId(nominee.getPkNominee());
			getSession().update(obj.get(0));
		} 
	}
	
}
