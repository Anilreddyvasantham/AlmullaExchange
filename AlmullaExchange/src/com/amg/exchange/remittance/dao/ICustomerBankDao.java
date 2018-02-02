package com.amg.exchange.remittance.dao;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.registration.model.ViewRemarks;
import com.amg.exchange.remittance.model.BankDebitCardLengthViewModel;
import com.amg.exchange.remittance.model.CustomerBank;
import com.amg.exchange.remittance.model.CustomerDBCardDetailsView;
import com.amg.exchange.remittance.model.ViewBankDetails;
import com.amg.exchange.treasury.bean.BankPrefixDataTable;
import com.amg.exchange.treasury.model.BankPrefix;

public interface ICustomerBankDao {
	public List<CustomerBank> customerBanksAvailable(BigDecimal customerId);
	public List<CustomerDBCardDetailsView> customerBanksView(BigDecimal customerId);
	public void save(CustomerBank customerDetails);
	public  Boolean isExist(BigDecimal customerId,BigDecimal bankId,String cardno);
	public List<BankDebitCardLengthViewModel> bankCardLengthView(String bankcode);
	//Added by Rabil on 06102015 to get the Local bank
	//public List<ViewBankDetails> getCustomerLocalBankListFromView(BigDecimal countryId,BigDecimal bankCode);
	public List<CustomerBank> customerBanks(BigDecimal customerId,BigDecimal bankId);
	public List<ViewBankDetails> getCustomerLocalBankListFromView(BigDecimal countryId,String bankCode);
	public List<ViewBankDetails> customerBanks(BigDecimal customerId,String bankCode);
	public List<CustomerBank> fetchcustomerBanksDetails(BigDecimal customerId,String bankCode);
	
	public List<ViewBankDetails>  getChequeBnakIdFromView(String bankCode);
	public void saveOrUpdate(CustomerBank customerDetails);
	public List<BankPrefix> getBankPrefix(String bankCode,BigDecimal bankId);
	public boolean checkDebitCardWithDeActiveStatus(String debitcardNo);
	public void deactivateCustomerBank(BigDecimal customerBankPkId,String userName,String remarks);
	public List<BankPrefix> checkBankprefixExist(String bankPrefix);
	
	public List<CustomerIdProof> getCustomerBasedOnId(BigDecimal customerId);
	
	public List<BankPrefix> getBankPrefix(String  bankCode);
	public List<BankPrefix> getBankPrefixBasedOnPrefix(String  bankCode,String bankPrefix);
	
	public void saveOrUpdate(BankPrefix bankPrefix);
	public void updateBankPrefix(BankPrefixDataTable bankPrefixDataTable);
	
	public void deleteFromDb(BigDecimal bankPrefixpkId);
	public String approve(BigDecimal bankPrefixPkId,String userName) ;
	
	public List<BankPrefix> getBankPrefixApproval(String bankCode);
	
	public List<ViewRemarks> getViewRemarks();
	
	public List<CustomerBank> checkDebitCardNoAlreadyExist(String debitcardNo);
	
	public void saveAllDebitCardsBanks(List<CustomerBank> lstBankCards);
	
	public List<BigDecimal> getCustomerbankIdForDeactivatee(String debitcardNo,String prefix,String suffix,BigDecimal bankId);
	
	public List<BigDecimal> checkDebitCardWithActiveStatus(String debitcardNo,String prefix,String suffix,BigDecimal bankId);
	
	public List<CustomerBank> fetchcustomerBanksDetails(String debitcardNo,String prefix,String suffix,BigDecimal bankId);
	
}
