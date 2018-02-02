package com.amg.exchange.treasury.serviceimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.treasury.dao.IFXDealSupplierDao;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.service.IFXDealSupplierService;

@SuppressWarnings("serial")
@Service("fXDealSupplierServiceImpl")
public class FXDealSupplierServiceImpl<T> implements IFXDealSupplierService<T>, Serializable{

	@Autowired
	IFXDealSupplierDao<T> fXDealSupplierDao;

	@Override
	@Transactional
	public List<CompanyMasterDesc> getCompanyList(BigDecimal languageId) {

		return fXDealSupplierDao.getCompanyList(languageId);
	}

	@Override
	@Transactional
	public List<CurrencyMaster> getCurrencyList() {
		
		return fXDealSupplierDao.getCurrencyList();
	}

	
	@Override
	@Transactional
	public List<Customer> getAllCustomerList(BigDecimal customerReference) {

		return fXDealSupplierDao.getAllCustomerList(customerReference);
	}

	@Override
	@Transactional
	public List<Customer> getAllComponentOreder(String query) {
		return fXDealSupplierDao.getAllComponentOreder(query);
	}

	  @Override
	  @Transactional
	  public String getAllCustomerName(BigDecimal dealWithCustomer) {
		    return fXDealSupplierDao.getAllCustomerName(dealWithCustomer);
	  }
}
