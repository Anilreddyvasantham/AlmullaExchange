package com.amg.exchange.treasury.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.treasury.dao.ISupplierDao;
import com.amg.exchange.treasury.deal.supplier.model.TreasuryCustomerSupplier;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.service.ISupplierService;
import com.amg.exchange.util.AMGException;


@SuppressWarnings("rawtypes")
@Service("SupplierService")
public class SupplierServiceImpl <T> implements ISupplierService {
	@Autowired
	ISupplierDao iSupplierDao;

	@Override
	@Transactional
	public void save(TreasuryCustomerSupplier treasuryCustomerSupplier) {
		iSupplierDao.save(treasuryCustomerSupplier);
	}

	@Override
	@Transactional
	public List<Customer> getCustomerDetails(BigDecimal customerId) {
		return iSupplierDao.getCustomerDetails(customerId);
	}

	@Override
	@Transactional
	public List<BankMaster> getBankCountryList(BigDecimal countryId) {
		return iSupplierDao.getBankCountryList(countryId);
	}

	@Override
	@Transactional
	public List<BankAccountDetails> getAccountNoList(BigDecimal bankId) {
		return iSupplierDao.getAccountNoList(bankId);
	}

	@Override
	@Transactional
	public String getNationalityName(BigDecimal nationalityId) {
		return iSupplierDao.getNationalityName(nationalityId);
	}

	@Override
	@Transactional
	public List<BankApplicability> getBankListFromBankApplicability( BigDecimal applicationCountryId) {
		return iSupplierDao.getBankListFromBankApplicability(applicationCountryId);
	}
	
	@Override
	@Transactional
	public List<Customer> getCustomerDetailsBasedOnReference( BigDecimal customerReference) {
		return iSupplierDao.getCustomerDetailsBasedOnReference(customerReference);
	}
	
	@Override
	@Transactional
	public void sundryDebtorReference( String sundryDebtorRef,BigDecimal customerRef) throws AMGException{
		 iSupplierDao.sundryDebtorReference(sundryDebtorRef,customerRef);
	}
	
	@Override
	@Transactional
	public List<TreasuryCustomerSupplier> getAccountNumberDetails(BigDecimal bankId,BigDecimal currencyId) {
		return iSupplierDao.getAccountNumberDetails(bankId,currencyId);
	}
	
	@Override
	@Transactional
	public List<TreasuryCustomerSupplier> getAllTreasuryCustomerSupplier(BigDecimal customerid) {
		return iSupplierDao.getAllTreasuryCustomerSupplier(customerid);
	}
	
	@Override
	@Transactional
	public void removeTreasuryCustomerRecord(BigDecimal customerPk,String userName) {
		 iSupplierDao.removeTreasuryCustomerRecord(customerPk,userName);
	}
	
	@Override
	@Transactional
	public List<Customer>  getAllSundryDebtorRef(BigDecimal sundryDeptoref) {
		return iSupplierDao.getAllSundryDebtorRef(sundryDeptoref);
	}
	
}
