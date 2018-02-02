package com.amg.exchange.remittance.daoimpl;
import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.remittance.dao.ICorporateRemittanceDao;
import com.amg.exchange.remittance.model.BeneficaryMaster;
import com.amg.exchange.remittance.model.BeneficaryStatus;
import com.amg.exchange.remittance.model.Relations;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.util.Constants;

@SuppressWarnings("rawtypes")
@Repository
public class CorporateRemittanceDaoImpl extends CommonDaoImpl implements ICorporateRemittanceDao {
	
	private static final Logger LOG = Logger.getLogger(CorporateRemittanceDaoImpl.class);
	@Override
	public CustomerIdProof getCustomerList(String identityType) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CustomerIdProof.class, "customerIdProof");
		detachedCriteria.add(Restrictions.eq("customerIdProof.identityInt", "485852360725"));
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return ((CustomerIdProof) detachedCriteria.getExecutableCriteria(getSession()).list().get(0));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BeneficaryStatus> getBeneficaryStatusList() {
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryStatus.class);
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<BeneficaryStatus>) findAll(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerIdProof> getCustomerDetails(String customerId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CustomerIdProof.class, "customerIdProof");
		criteria.add(Restrictions.eq("customerIdProof.identityInt", customerId));
		criteria.setFetchMode("customerIdProof.fsCustomer", FetchMode.JOIN);
		criteria.createAlias("customerIdProof.fsCustomer", "fsCustomer", JoinType.INNER_JOIN);
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<CustomerIdProof>) findAll(criteria);
	}

	@Override
	public List<Relations> getRelationsList() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Relations.class);
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<Relations>) findAll(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankMaster> getbanklist(BigDecimal currencyId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BankMaster.class, "bankMaster");
		criteria.setFetchMode("bankMaster.currencyMaster", FetchMode.JOIN);
		criteria.createAlias("bankMaster.currencyMaster", "currencyMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("currencyMaster.currencyId", currencyId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<BankMaster>) findAll(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankBranch> getBankbranchlist(BigDecimal bankId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BankBranch.class, "bankBranch");
		criteria.setFetchMode("bankBranch.exBankMaster", FetchMode.JOIN);
		criteria.createAlias("bankBranch.exBankMaster", "exBankMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("exBankMaster.bankId", bankId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<BankBranch>) findAll(criteria);
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<CountryMasterDesc> getCountryList() {
		DetachedCriteria criteria = DetachedCriteria.forClass(CountryMasterDesc.class, "countryMasterDesc");
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<CountryMasterDesc>) findAll(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CurrencyMaster> getCurrencyList(BigDecimal countryId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CurrencyMaster.class, "currencyMaster");
		criteria.setFetchMode("currencyMaster.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("currencyMaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<CurrencyMaster>) findAll(criteria);
	}

	@Override
	public List<CustomerIdProof> checkForCorporateCustomer(BigDecimal customerId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CustomerIdProof.class, "customerIdProof");
		criteria.setFetchMode("customerIdProof.fsCustomer", FetchMode.JOIN);
		criteria.createAlias("customerIdProof.fsCustomer", "fsCustomer", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCustomer.customerId", customerId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return findAll(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Customer> getCorporateCustomerDetails(String crno) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Customer.class, "customer");
		detachedCriteria.add(Restrictions.eq("customer.crNo", crno));
		return (List<Customer>) findAll(detachedCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BeneficaryMaster> getCorporateBenificiaryList(BigDecimal customerId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryMaster.class, "benificiaryMaster");
		criteria.setFetchMode("benificiaryMaster.fsCustomer", FetchMode.JOIN);
		criteria.createAlias("benificiaryMaster.fsCustomer", "fsCustomer", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCustomer.customerId", customerId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<BeneficaryMaster>) findAll(criteria);
	}

	@Override
	public List<CustomerIdProof> getRegisterId(String crno, BigDecimal countryId) {
		LOG.info("Entering into saveOrUpdateSecondaryObjective method");
		LOG.info("crno == "+crno);
		LOG.info("countryId == "+countryId);
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CustomerIdProof.class, "customerIdProof");
		dCriteria.setFetchMode("customerIdProof.fsCustomer", FetchMode.JOIN);
		dCriteria.createAlias("customerIdProof.fsCustomer", "fsCustomer", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCustomer.fsCountryMasterByCountryId.countryId", countryId));
		dCriteria.add(Restrictions.eq("fsCustomer.isActive", Constants.Yes)); // constatns yes =Y
		dCriteria.add(Restrictions.eq("fsCustomer.activatedInd", Constants.Yes)); 
		/*dCriteria.add(Restrictions.isNotNull("fsCustomer.signatureSpecimen")); */
		dCriteria.add(Restrictions.eq("identityInt", crno));
		dCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		LOG.info("Exit into saveOrUpdateSecondaryObjective method");
		return (List<CustomerIdProof>) findAll(dCriteria);
	}
}
