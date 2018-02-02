package com.amg.exchange.treasury.daoimpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.treasury.dao.ISupplierDao;
import com.amg.exchange.treasury.deal.supplier.model.TreasuryCustomerSupplier;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;


@Repository
public class SupplierDaoImpl<T> extends CommonDaoImpl<T> implements ISupplierDao{

	@SuppressWarnings("unchecked")
	@Override
	public void save(TreasuryCustomerSupplier treasuryCustomerSupplier) {
		saveOrUpdate((T)treasuryCustomerSupplier);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Customer> getCustomerDetails(BigDecimal customerId) {
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Customer.class,"customerDeatils");
		dCriteria.add(Restrictions.eq("customerDeatils.customerId", customerId));
		return (List<Customer>)findAll(dCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankMaster> getBankCountryList(BigDecimal countryId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankMaster.class,"bankMaster");
       return (List<BankMaster>) findAll(dCriteria);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankAccountDetails> getAccountNoList(BigDecimal bankId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankAccountDetails.class,"bankAccountDetails");
		dCriteria.setFetchMode("bankAccountDetails.exBankMaster",FetchMode.JOIN);
		dCriteria.createAlias("bankAccountDetails.exBankMaster", "exBankMaster", JoinType.INNER_JOIN);
		
		dCriteria.add(Restrictions.eq("exBankMaster.bankId", bankId));
		
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		return (List<BankAccountDetails>)findAll(dCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getNationalityName(BigDecimal nationalityId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CountryMasterDesc.class,"countryMasterDesc");
		
		dCriteria.setFetchMode("countryMasterDesc.fsCountryMaster",FetchMode.JOIN);
		dCriteria.createAlias("countryMasterDesc.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		
		dCriteria.add(Restrictions.eq("fsCountryMaster.countryId", nationalityId));
		List<CountryMasterDesc> data=(List<CountryMasterDesc>)findAll(dCriteria);
		if(data.size()>0){
		return data.get(0).getNationality();
		}else{
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankApplicability> getBankListFromBankApplicability( BigDecimal applicationCountryId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankApplicability.class,"bankApplicability");
		dCriteria.setFetchMode("bankApplicability.fsCountryMaster",FetchMode.JOIN);
		dCriteria.createAlias("bankApplicability.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCountryMaster.countryId", applicationCountryId));
		
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		return (List<BankApplicability>)findAll(dCriteria);
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<Customer> getCustomerDetailsBasedOnReference( BigDecimal customerReference) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Customer.class,"customer");
		
		dCriteria.setFetchMode("customer.fsBizComponentDataByCustomerTypeId",FetchMode.JOIN);
		dCriteria.createAlias("customer.fsBizComponentDataByCustomerTypeId", "fsBizComponentDataByCustomerTypeId", JoinType.INNER_JOIN);
		
		dCriteria.setFetchMode("customer.fsCountryMasterByNationality",FetchMode.JOIN);
		dCriteria.createAlias("customer.fsCountryMasterByNationality", "fsCountryMasterByNationality", JoinType.INNER_JOIN);
		
		dCriteria.add(Restrictions.eq("customer.customerReference", customerReference));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		return (List<Customer>)findAll(dCriteria);
	}


	@Override
	public void sundryDebtorReference(String sundryDebtorRef, BigDecimal customerRef) throws AMGException{
		try {
			Customer cusSpDeal=(Customer) getSession().get(Customer.class, customerRef);
			cusSpDeal.setSundryDebtorReference(sundryDebtorRef);
			getSession().update(cusSpDeal);
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new AMGException(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TreasuryCustomerSupplier> getAccountNumberDetails(BigDecimal bankId, BigDecimal currencyId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(TreasuryCustomerSupplier.class,"treasuryCustomerSupplier");
		
		dCriteria.setFetchMode("treasuryCustomerSupplier.bankMaster",FetchMode.JOIN);
		dCriteria.createAlias("treasuryCustomerSupplier.bankMaster", "bankMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("bankMaster.bankId", bankId));
		
		dCriteria.setFetchMode("treasuryCustomerSupplier.dealSupplierCurrency",FetchMode.JOIN);
		dCriteria.createAlias("treasuryCustomerSupplier.dealSupplierCurrency", "dealSupplierCurrency", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("dealSupplierCurrency.currencyId", currencyId));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<TreasuryCustomerSupplier>) findAll(dCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TreasuryCustomerSupplier> getAllTreasuryCustomerSupplier(BigDecimal customerid) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(TreasuryCustomerSupplier.class,"treasuryCustomerSupplier");
		
		dCriteria.setFetchMode("treasuryCustomerSupplier.dealSupplierCustomer",FetchMode.JOIN);
		dCriteria.createAlias("treasuryCustomerSupplier.dealSupplierCustomer", "dealSupplierCustomer", JoinType.INNER_JOIN);
		
		/*dCriteria.setFetchMode("treasuryCustomerSupplier.bankMaster",FetchMode.JOIN);
		dCriteria.createAlias("treasuryCustomerSupplier.bankMaster", "bankMaster", JoinType.INNER_JOIN);
		
		dCriteria.setFetchMode("treasuryCustomerSupplier.dealSupplierCurrency",FetchMode.JOIN);
		dCriteria.createAlias("treasuryCustomerSupplier.dealSupplierCurrency", "dealSupplierCurrency", JoinType.INNER_JOIN);*/
		
		dCriteria.add(Restrictions.eq("treasuryCustomerSupplier.isActive", Constants.Yes));
		dCriteria.add(Restrictions.eq("dealSupplierCustomer.customerId", customerid));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<TreasuryCustomerSupplier>) findAll(dCriteria);
	}

	@Override
	public void removeTreasuryCustomerRecord(BigDecimal customerPk,String userName) {
		TreasuryCustomerSupplier custSupp=(TreasuryCustomerSupplier) getSession().get(TreasuryCustomerSupplier.class, customerPk);
		custSupp.setIsActive(Constants.U);
		custSupp.setModifiedBy(userName);
		custSupp.setModifiedDate(new Date());
		getSession().update(custSupp);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Customer> getAllSundryDebtorRef(BigDecimal sundryDeptoref) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Customer.class,"customer");
		dCriteria.add(Restrictions.eq("customer.sundryDebtorReference", sundryDeptoref.toPlainString()));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<Customer>) findAll(dCriteria);
	}
}
