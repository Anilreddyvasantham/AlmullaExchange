package com.amg.exchange.registration.serviceimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.bean.CreateSearchTable;
import com.amg.exchange.registration.bean.SearchEntityBean;
import com.amg.exchange.registration.dao.ISearchCustomerDao;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.registration.service.ISearchCustomerService;

@SuppressWarnings("serial")
@Service("isearchCustomerService")
public class SearchCustomerServiceImpl<T>  implements ISearchCustomerService<T>, Serializable {
	
	@Autowired
	ISearchCustomerDao<T> iSearchCustomerDao;
	

	public ISearchCustomerDao<T> getiSearchCustomerDao() {
		return iSearchCustomerDao;
	}

	public void setiSearchCustomerDao(ISearchCustomerDao<T> iSearchCustomerDao) {
		this.iSearchCustomerDao = iSearchCustomerDao;
	}

	@Override
	@Transactional
	public List<Customer> getCustomer() {
		return getiSearchCustomerDao().getCustomer();
	}
	 
	@Override
	@Transactional
	public List<CustomerIdProof> getCustomerIdProof() {
		return getiSearchCustomerDao().getCustomerIdProof();
	}

	@Transactional
	@Override
	public List<CustomerIdProof> searchAllCustomer(SearchEntityBean searchEntityBean) {
		return getiSearchCustomerDao().searchAllCustomer(searchEntityBean);
	}
	
	@Transactional
	@Override
	public List<CustomerIdProof> searchCustomerEnquiryForAllCustomer(SearchEntityBean searchEntityBean){
		return getiSearchCustomerDao().searchCustomerEnquiryForAllCustomer(searchEntityBean);
	}
	
	@Transactional
	@Override
	public List<Customer> getCustomerInfo(BigDecimal customerId){
		return getiSearchCustomerDao().getCustomerInfo(customerId);
	}

	@Override
	@Transactional
	public List<CustomerIdProof> searchAllCustomers(
			SearchEntityBean searchEntityBean) {
		// TODO Auto-generated method stub
		return getiSearchCustomerDao().searchAllCustomers(searchEntityBean);
	}

	@Override
	@Transactional
	public void update(CreateSearchTable createSearchTable) {
		getiSearchCustomerDao().update(createSearchTable);
		
	}

	@Override
	@	Transactional
	public Boolean checkAuthorizationOfEmp(BigDecimal employeeId) {
		 
		return getiSearchCustomerDao(). checkAuthorizationOfEmp(employeeId);
	}

	@Override
	@	Transactional
	public void ActivateRecord(CreateSearchTable createSearchTable) {
		getiSearchCustomerDao().ActivateRecord(createSearchTable);
		
	}

	@Override
	@	Transactional
	public List<CustomerIdProof> getRecentExpiryDate(SearchEntityBean searchEntityBean) {
		return getiSearchCustomerDao().getRecentExpiryDate(searchEntityBean);
		
	}
}



