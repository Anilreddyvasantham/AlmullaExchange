package com.amg.exchange.common.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.common.dao.IBankAccountTypeDao;
import com.amg.exchange.common.dto.BankAccountTypeDTO;
import com.amg.exchange.common.model.BankAccountType;
import com.amg.exchange.common.model.BankAccountTypeDesc;
import com.amg.exchange.common.service.IBankAccountTypeService;

@Service("bankAccountTypeService")
public class BankAccountTypeServiceImpl implements IBankAccountTypeService{
	
	@Autowired
	IBankAccountTypeDao bankAccountTypeDao;

	@Override
	@Transactional
	public List<BankAccountType> getAllRecordsForApproval() {
	return	bankAccountTypeDao.getAllRecordsForApproval();
	}
	
	@Override
	@Transactional
	public List<BankAccountTypeDesc> getallRecordsRelatedBankAccountType(BigDecimal bankAccountTypeId) {
	return	bankAccountTypeDao.getallRecordsRelatedBankAccountType(bankAccountTypeId);
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void saveRecords(BankAccountType bankAccountType,BankAccountTypeDesc bankAccountTypedesc1,BankAccountTypeDesc bankAccountTypedesc2) throws Exception{
	bankAccountTypeDao.saveRecords(bankAccountType,bankAccountTypedesc1,bankAccountTypedesc2);
	}
	
	@Override
	@Transactional
	public String approveReocrd(BigDecimal bankaccountTypePk,String userName) {
	 return bankAccountTypeDao.approveReocrd(bankaccountTypePk,userName);
	}

	@Override
	@Transactional
	public List<BankAccountType> viewAllRecords(){
	return bankAccountTypeDao.viewAllRecords();
	}
	
	@Override
	@Transactional
	public void deleteRecordPermanently(BigDecimal accountTypePk,BigDecimal accountTypedescPk,BigDecimal accountTypeLocalDescPk){
		bankAccountTypeDao.deleteRecordPermanently(accountTypePk,accountTypedescPk,accountTypeLocalDescPk);
	}
	
	@Override
	@Transactional
	public List<String> getServiceCodeList(String query){
		return bankAccountTypeDao.getServiceCodeList(query);
		
	}
	
	@Override
	@Transactional
	public List<BankAccountType> fetchAllRecords(String accountTypeCode){
		return bankAccountTypeDao.fetchAllRecords(accountTypeCode);
		
	}
	
	@Override
	@Transactional
	public void activateRecord(BigDecimal bankaccountTypePk,String userName){
		 bankAccountTypeDao.activateRecord(bankaccountTypePk,userName);
		
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void saveAllRecords(BankAccountTypeDTO bankAccountTypeVOList)
			throws Exception {
		bankAccountTypeDao.saveAllRecords(bankAccountTypeVOList);
		
	}

	@Override
	@Transactional
	public List<BankAccountTypeDTO> viewRecords() {
		
		return bankAccountTypeDao.viewRecords();
	}

	@Override
	@Transactional
	public List<BankAccountTypeDTO> getAllRecordsRelatedBankAccountType(
			BigDecimal bankAccountTypeId) {
		// TODO Auto-generated method stub
		return bankAccountTypeDao.getAllRecordsRelatedBankAccountType(bankAccountTypeId);
	}

	@Override
	@Transactional
	public List<BankAccountTypeDTO> fetchAccTypeCodeRecord(String accountTypeCode) {
		// TODO Auto-generated method stub
		return bankAccountTypeDao.fetchAccTypeCodeRecord(accountTypeCode);
	}

}
