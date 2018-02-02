package com.amg.exchange.remittance.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.registration.model.ViewRemarks;
import com.amg.exchange.remittance.dao.ICustomerBankDao;
import com.amg.exchange.remittance.model.BankDebitCardLengthViewModel;
import com.amg.exchange.remittance.model.CustomerBank;
import com.amg.exchange.remittance.model.CustomerDBCardDetailsView;
import com.amg.exchange.remittance.model.ViewBankDetails;
import com.amg.exchange.remittance.service.ICustomerBankService;
import com.amg.exchange.treasury.bean.BankPrefixDataTable;
import com.amg.exchange.treasury.model.BankPrefix;

@Service("customerBankServiceImpl")
public class CustomerBankServiceImpl implements ICustomerBankService {

	@Autowired
	ICustomerBankDao icustomerBankDao;
	
	@Override
	@Transactional
	public List<CustomerBank> customerBanksAvailable(BigDecimal customerId) {
		return icustomerBankDao.customerBanksAvailable(customerId);
	}

	@Override
	@Transactional
	public void save(CustomerBank customerDetails) {
		icustomerBankDao.save(customerDetails);
	}
	
	@Override
	@Transactional
	public  Boolean isExist(BigDecimal customerId,BigDecimal bankId,String cardno){
		return icustomerBankDao.isExist(customerId,bankId,cardno);
	}

	@Override
	@Transactional
	public List<CustomerDBCardDetailsView> customerBanksView(BigDecimal customerId) {
		// TODO Auto-generated method stub
		return icustomerBankDao.customerBanksView(customerId);
	}

	@Override
	@Transactional
	public List<BankDebitCardLengthViewModel> bankCardLengthView(String bankcode) {
		// TODO Auto-generated method stub
		return icustomerBankDao.bankCardLengthView(bankcode);
	}
	
	@Override
	@Transactional
	public List<CustomerBank> customerBanks(BigDecimal customerId, BigDecimal bankId) {	 
		return icustomerBankDao.customerBanks(customerId,bankId);
	}
	
	@Override
	@Transactional
	public List<ViewBankDetails> customerBanks(BigDecimal customerId, String bankCode) {	 
		return icustomerBankDao.customerBanks(customerId,bankCode);
	}

	@Override
	@Transactional
	public List<ViewBankDetails> getCustomerLocalBankListFromView(BigDecimal countryId, String bankCode) {
		return icustomerBankDao.getCustomerLocalBankListFromView(countryId,bankCode);
	}

	@Override
	@Transactional
	public List<CustomerBank> fetchcustomerBanksDetails(BigDecimal customerId,String bankCode) {
		return icustomerBankDao.fetchcustomerBanksDetails(customerId,bankCode);
	}

	@Override
	@Transactional
	public List<ViewBankDetails> getChequeBnakIdFromView(String bankCode) {
		
		return icustomerBankDao.getChequeBnakIdFromView(bankCode);
	}

	@Override
	@Transactional
	public void saveOrUpdate(CustomerBank customerDetails) {
		icustomerBankDao.saveOrUpdate(customerDetails);
		
	}

	@Override
	@Transactional
	public List<BankPrefix> getBankPrefix(String bankCode,BigDecimal bankId) {
		
		return icustomerBankDao.getBankPrefix(bankCode,bankId);
	}

	@Override
	@Transactional
	public boolean checkDebitCardWithDeActiveStatus(
			String debitcardNo) {
		
		return icustomerBankDao.checkDebitCardWithDeActiveStatus(debitcardNo);
	}

	@Override
	@Transactional
	public void deactivateCustomerBank(BigDecimal customerBankPkId,
			String userName,String remarks) {
		icustomerBankDao.deactivateCustomerBank(customerBankPkId, userName,remarks);
		
	}

	@Override
	@Transactional
	public List<BankPrefix> checkBankprefixExist(String bankPrefix) {
	
		return icustomerBankDao.checkBankprefixExist(bankPrefix);
	}

	@Override
	@Transactional
	public List<BigDecimal> checkDebitCardWithActiveStatus(String debitcardNo,String prefix,String suffix,BigDecimal bankId) {
		
		return icustomerBankDao.checkDebitCardWithActiveStatus(debitcardNo,prefix,suffix,bankId);
	}

	@Override
	@Transactional
	public List<CustomerIdProof> getCustomerBasedOnId(BigDecimal customerId) {
		
		return icustomerBankDao.getCustomerBasedOnId(customerId);
	}

	@Override
	@Transactional
	public List<BigDecimal> getCustomerbankIdForDeactivatee(String debitcardNo,String prefix,String suffix,BigDecimal bankId) {
		return icustomerBankDao.getCustomerbankIdForDeactivatee(debitcardNo,prefix,suffix,bankId);
	}

	@Override
	@Transactional
	public List<BankPrefix> getBankPrefix(String bankCode) {
		
		return icustomerBankDao.getBankPrefix(bankCode);
	}

	@Override
	@Transactional
	public List<BankPrefix> getBankPrefixBasedOnPrefix(String bankCode,
			String bankPrefix) {
		
		return icustomerBankDao.getBankPrefixBasedOnPrefix(bankCode, bankPrefix);
	}

	@Override
	@Transactional
	public void saveOrUpdate(BankPrefix bankPrefix) {
		icustomerBankDao.saveOrUpdate(bankPrefix);
	}

	@Override
	@Transactional
	public void updateBankPrefix(BankPrefixDataTable bankPrefixDataTable) {
		icustomerBankDao.updateBankPrefix(bankPrefixDataTable);
		
	}

	@Override
	@Transactional
	public void deleteFromDb(BigDecimal bankPrefixpkId) {
		icustomerBankDao.deleteFromDb(bankPrefixpkId);
		
	}

	@Override
	@Transactional
	public String approve(BigDecimal bankPrefixPkId, String userName) {
		
		return icustomerBankDao.approve(bankPrefixPkId, userName);
	}

	@Override
	@Transactional
	public List<BankPrefix> getBankPrefixApproval(String bankCode) {
	
		return icustomerBankDao.getBankPrefixApproval(bankCode);
	}

	@Override
	@Transactional
	public List<ViewRemarks> getViewRemarks() {
		
		return icustomerBankDao.getViewRemarks();
	}

	@Override
	@Transactional
	public List<CustomerBank> checkDebitCardNoAlreadyExist(String debitcardNo) {
		return icustomerBankDao.checkDebitCardNoAlreadyExist(debitcardNo);
	}

	@Override
	@Transactional
	public void saveAllDebitCardsBanks(List<CustomerBank> lstBankCards) {
		icustomerBankDao.saveAllDebitCardsBanks(lstBankCards);
	}

	@Override
	@Transactional
	public List<CustomerBank> fetchcustomerBanksDetails(String debitcardNo,	String prefix, String suffix, BigDecimal bankId) {
		return icustomerBankDao.fetchcustomerBanksDetails(debitcardNo, prefix, suffix, bankId);
	}

}
