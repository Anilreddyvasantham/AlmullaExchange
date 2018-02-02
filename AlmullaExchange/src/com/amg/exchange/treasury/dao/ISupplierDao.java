package com.amg.exchange.treasury.dao;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.treasury.deal.supplier.model.TreasuryCustomerSupplier;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.util.AMGException;

public interface ISupplierDao {

	public void save(TreasuryCustomerSupplier treasuryCustomerSupplier) ;
	public List<Customer> getCustomerDetails(BigDecimal customerId);
	public List<BankMaster> getBankCountryList(BigDecimal countryId); 
	public List<BankAccountDetails> getAccountNoList(BigDecimal bankId);
	public String getNationalityName(BigDecimal nationalityId);
	public List<BankApplicability> getBankListFromBankApplicability(BigDecimal applicationCountryId);
	
	//for getting all details of customer based on customer reference
	public List<Customer> getCustomerDetailsBasedOnReference(BigDecimal customerReference);
	
	//updating sundryDebtor Reference in Fs Customer
		public void sundryDebtorReference(String sundryDebtorRef,BigDecimal customerRef) throws AMGException;
		
		//for Checking AccountNumber 
		public List<TreasuryCustomerSupplier> getAccountNumberDetails(BigDecimal bankId,BigDecimal currencyId);
		
		//to get all treasury customer supplier data
		public List<TreasuryCustomerSupplier> getAllTreasuryCustomerSupplier(BigDecimal customerId);
		
		//Remove an Record From Treasury Customer 
		public void removeTreasuryCustomerRecord(BigDecimal customerPk,String userName);
		//get all customer Sundry Debtor References
		public List<Customer> getAllSundryDebtorRef(BigDecimal sundryDeptoref);
}
